"""libc / GLES / JNI giả cho hl5run.py (ABI armeabi soft-float)."""
import os, struct, math, time, zlib
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *

VFS_FD = 3
FRAME_MS = 50


def _f(x):  # u32 -> float
    return struct.unpack('<f', struct.pack('<I', x & 0xffffffff))[0]


def _d(lo, hi):
    return struct.unpack('<d', struct.pack('<II', lo, hi))[0]


def _dret(v):
    lo, hi = struct.unpack('<II', struct.pack('<d', v))
    return (lo, hi)


class VA:
    """Đọc tham số biến đổi: lần lượt từ các thanh ghi còn lại rồi stack, hoặc từ va_list."""
    def __init__(self, g, first=None, va_list=None):
        self.g = g
        if va_list is not None:
            self.mode = 'mem'; self.p = va_list
        else:
            self.mode = 'reg'; self.i = first

    def u32(self):
        if self.mode == 'mem':
            v = self.g.rd32(self.p); self.p += 4; return v
        v = self.g.arg(self.i); self.i += 1; return v

    def u64(self):
        if self.mode == 'mem':
            self.p = (self.p + 7) & ~7
            lo = self.g.rd32(self.p); hi = self.g.rd32(self.p + 4); self.p += 8
            return lo | hi << 32
        if self.i < 4 and self.i % 2: self.i += 1
        elif self.i >= 4:
            # stack slots: 8-byte align relative to sp
            if (self.i - 4) % 2: self.i += 1
        lo = self.g.arg(self.i); hi = self.g.arg(self.i + 1); self.i += 2
        return lo | hi << 32


def cformat(g, fmt, va):
    out = bytearray(); i = 0
    while i < len(fmt):
        c = fmt[i]
        if c != 0x25:
            out.append(c); i += 1; continue
        j = i + 1
        while j < len(fmt) and fmt[j] in b'-+ #0123456789.*hlLqjzt': j += 1
        if j >= len(fmt): break
        spec = fmt[i + 1:j]; conv = chr(fmt[j]); i = j + 1
        if conv == '%': out += b'%'; continue
        flags = spec.replace(b'l', b'').replace(b'h', b'').replace(b'z', b'').replace(b'j', b'').replace(b't', b'')
        if b'*' in flags:
            flags = flags.replace(b'*', str(va.u32()).encode(), 1)
        pf = '%' + flags.decode()
        if conv in 'di':
            v = va.u64() if b'll' in spec else va.u32()
            bits = 64 if b'll' in spec else 32
            if v >> (bits - 1): v -= 1 << bits
            out += ((pf + 'd') % v).encode()
        elif conv in 'uxXo':
            v = va.u64() if b'll' in spec else va.u32()
            out += ((pf + conv) % v).encode()
        elif conv == 'c':
            out.append(va.u32() & 0xff)
        elif conv == 's':
            s = g.cstr(va.u32())
            if b'.' in flags:
                prec = int(flags.split(b'.')[1] or 0); s = s[:prec]
                flags = flags.split(b'.')[0]
            w = flags.decode().lstrip('-0')
            if w.isdigit():
                s = s.ljust(int(w)) if flags.startswith(b'-') else s.rjust(int(w))
            out += s
        elif conv == 'p':
            out += b'0x%x' % va.u32()
        elif conv in 'fFeEgG':
            v = va.u64(); out += ((pf + conv) % struct.unpack('<d', struct.pack('<Q', v))[0]).encode()
        else:
            out += b'?'
    return bytes(out)


def make_handler(g, name):
    A = g.arg
    uc = g.uc

    def ret0(): return 0

    def unimpl():
        g.log.write('UNIMPL %s\n' % name); g.log.flush(); return 0

    # ---- bộ nhớ ----
    def malloc(): return g.heap.alloc(A(0))
    def calloc():
        n = A(0) * A(1); p = g.heap.alloc(n); uc.mem_write(p, b'\0' * n); return p
    def free(): g.heap.release(A(0))
    def memcpy():
        d, s, n = A(0), A(1), A(2)
        if n: uc.mem_write(d, bytes(uc.mem_read(s, n)))
        return d
    def memset():
        d, c, n = A(0), A(1), A(2)
        if n: uc.mem_write(d, bytes([c & 0xff]) * n)
        return d
    # ---- chuỗi ----
    def strlen(): return len(g.cstr(A(0), 1 << 20))
    def strcpy():
        s = g.cstr(A(1), 1 << 20); uc.mem_write(A(0), s + b'\0'); return A(0)
    def strncpy():
        d, s, n = A(0), A(1), A(2)
        b = g.cstr(s, n)[:n]; b = b + b'\0' * (n - len(b)); uc.mem_write(d, b); return d
    def strcat():
        d = A(0); a = g.cstr(d, 1 << 20); uc.mem_write(d + len(a), g.cstr(A(1), 1 << 20) + b'\0'); return d
    def strcmp():
        a, b = g.cstr(A(0), 1 << 20), g.cstr(A(1), 1 << 20)
        return (a > b) - (a < b)
    def strncmp():
        n = A(2); a, b = g.cstr(A(0), n)[:n], g.cstr(A(1), n)[:n]
        return (a > b) - (a < b)
    def strstr():
        h = A(0); a = g.cstr(h, 1 << 20); i = a.find(g.cstr(A(1)))
        return h + i if i >= 0 else 0
    def strdup(): return g.new_str(g.cstr(A(0), 1 << 20))
    def strerror(): return g.new_str(b'err')
    def vsnprintf():
        buf, n, fmt, va = A(0), A(1), g.cstr(A(2)), A(3)
        s = cformat(g, fmt, VA(g, va_list=va))
        if n: uc.mem_write(buf, s[:n - 1] + b'\0')
        return len(s)
    def android_log():
        s = cformat(g, g.cstr(A(2)), VA(g, first=3))
        g.log.write('LOG[%d] %s\n' % (A(0), s.decode('latin1').rstrip())); g.log.flush(); return 0
    # ---- toán (soft-float) ----
    def floor_(): return _dret(math.floor(_d(A(0), A(1))))
    def ceil_(): return _dret(math.ceil(_d(A(0), A(1))))
    def pow_():
        try: return _dret(math.pow(_d(A(0), A(1)), _d(A(2), A(3))))
        except Exception: return _dret(float('nan'))
    # ---- thời gian ----
    def ftime():
        g.vclock += 1
        t = g.vclock
        uc.mem_write(A(0), struct.pack('<IHhh', t // 1000, t % 1000, 0, 0)); return 0
    # ---- tệp ----
    def host_path(p):
        p = p.decode('latin1')
        root = g.a.storage
        rel = p.replace(g.storage_int or '\0', '').replace(g.storage_ext or '\0', '').lstrip('/')
        return os.path.join(root, rel)
    def open_():
        p = g.cstr(A(0)); fl = A(1)
        hp = host_path(p)
        mode = {0: 'rb', 1: 'wb', 2: 'r+b'}[fl & 3]
        if fl & 0x40 and not os.path.exists(hp):  # O_CREAT
            open(hp, 'wb').close()
        if fl & 0x200 and (fl & 3):  # O_TRUNC
            open(hp, 'wb').close()
        if mode != 'wb' and not os.path.exists(hp):
            g.log.write('open %s -> ENOENT\n' % hp); g.errno = 2; return -1
        if mode == 'wb': mode = 'r+b'
        f = open(hp, mode)
        if fl & 0x400: f.seek(0, 2)
        fd = g.next_fd; g.next_fd += 1; g.fds[fd] = f
        g.log.write('open %s fl=%#x -> %d\n' % (hp, fl, fd)); return fd
    def read_():
        fd, buf, n = A(0), A(1), A(2)
        if fd == VFS_FD:
            d = g.vfs[g.vfs_pos:g.vfs_pos + n]; g.vfs_pos += len(d)
        else:
            f = g.fds.get(fd)
            if not f: return -1
            d = f.read(n)
        if d: uc.mem_write(buf, d)
        return len(d)
    def write_():
        fd, buf, n = A(0), A(1), A(2)
        f = g.fds.get(fd)
        if not f: return n
        f.write(bytes(uc.mem_read(buf, n))); f.flush(); return n
    def lseek():
        fd, off, wh = A(0), A(1), A(2)
        if off >> 31: off -= 1 << 32
        f = g.fds.get(fd)
        if not f: return -1
        f.seek(off, wh); return f.tell()
    def close_():
        f = g.fds.pop(A(0), None)
        if f: f.close()
        return 0
    def stat_():
        hp = host_path(g.cstr(A(0)))
        if not os.path.exists(hp): g.errno = 2; return -1
        st = os.stat(hp)
        b = bytearray(104)
        mode = 0o40755 if os.path.isdir(hp) else 0o100644
        struct.pack_into('<I', b, 16, mode)          # st_mode (bionic arm stat)
        struct.pack_into('<q', b, 48, st.st_size)    # st_size
        uc.mem_write(A(1), bytes(b)); return 0
    def access_():
        return 0 if os.path.exists(host_path(g.cstr(A(0)))) else -1
    def mkdir_():
        os.makedirs(host_path(g.cstr(A(0))), exist_ok=True); return 0
    def unlink_():
        hp = host_path(g.cstr(A(0)))
        if os.path.exists(hp): os.remove(hp); return 0
        return -1
    def rename_():
        a, b = host_path(g.cstr(A(0))), host_path(g.cstr(A(1)))
        if os.path.exists(a): os.replace(a, b); return 0
        return -1
    def dup_():
        fd = A(0)
        if fd == VFS_FD: return VFS_FD
        return fd
    def fdopen():
        fd = A(0)
        h = g.heap.alloc(16); g.files[h] = fd; return h
    def fread():
        buf, sz, n, fp = A(0), A(1), A(2), A(3)
        fd = g.files.get(fp)
        tot = sz * n
        if fd == VFS_FD:
            d = g.vfs[g.vfs_pos:g.vfs_pos + tot]; g.vfs_pos += len(d)
        else:
            d = g.fds[fd].read(tot)
        if d: uc.mem_write(buf, d)
        return len(d) // sz if sz else 0
    def fseek():
        fp, off, wh = A(0), A(1), A(2)
        if off >> 31: off -= 1 << 32
        fd = g.files.get(fp)
        if fd == VFS_FD:
            g.vfs_pos = {0: off, 1: g.vfs_pos + off, 2: len(g.vfs) + off}[wh]; return 0
        g.fds[fd].seek(off, wh); return 0
    def fclose():
        g.files.pop(A(0), None); return 0
    def errno_():
        if not hasattr(g, 'errno_addr'):
            g.errno_addr = g.misc_alloc(4)
        g.wr32(g.errno_addr, getattr(g, 'errno', 0)); return g.errno_addr
    def qsort_():
        base, n, sz, cmp = A(0), A(1), A(2), A(3)
        items = [bytes(uc.mem_read(base + i * sz, sz)) for i in range(n)]
        tmp_a = g.heap.alloc(sz); tmp_b = g.heap.alloc(sz)
        import functools
        def c(x, y):
            uc.mem_write(tmp_a, x); uc.mem_write(tmp_b, y)
            r = g.nested_call(cmp, tmp_a, tmp_b)
            return r - (1 << 32) if r >> 31 else r
        items.sort(key=functools.cmp_to_key(c))
        uc.mem_write(base, b''.join(items)); return 0
    def abort_():
        raise RuntimeError('guest abort()')
    # ---- mạng: luôn thất bại ----
    def neg1(): return 0xffffffff

    def netlog(nm, ret=0xffffffff):   # T7: mọi lời gọi mạng libc đều ghi 'NET <tên>' vào run.log
        def h():
            g.log.write('NET %s\n' % nm); g.log.flush(); return ret
        return h
    # ---- GLES ----
    def glGenTextures():
        n, p = A(0), A(1)
        for i in range(n):
            g.wr32(p + i * 4, g.next_tex); g.next_tex += 1
        return 0
    def glBindTexture(): g.bound = A(1); return 0
    def glTexImage2D():
        w, h, fmt, typ, px = A(3), g.arg(4), g.arg(6), g.arg(7), g.arg(8)
        bpp = 2 if typ in (0x8363, 0x8033, 0x8034) else 4
        data = bytearray(uc.mem_read(px, w * h * bpp)) if px else bytearray(w * h * bpp)
        g.tex[g.bound] = [w, h, fmt, typ, data]
        g.log.write('glTexImage2D tex=%d %dx%d fmt=%#x type=%#x\n' % (g.bound, w, h, fmt, typ)); return 0
    def glTexSubImage2D():
        x, y, w, h, typ, px = A(2), A(3), g.arg(4), g.arg(5), g.arg(7), g.arg(8)
        t = g.tex.get(g.bound)
        if not t: return 0
        bpp = 2 if typ in (0x8363, 0x8033, 0x8034) else 4
        TW = t[0]
        src = bytes(uc.mem_read(px, w * h * bpp))
        for r in range(h):
            o = ((y + r) * TW + x) * bpp
            t[4][o:o + w * bpp] = src[r * w * bpp:(r + 1) * w * bpp]
        g.screen_tex = g.bound; return 0
    def glViewport():
        g.viewport = (A(0), A(1), A(2), A(3)); return 0
    def glOrthof():
        g.ortho = tuple(_f(x) for x in (A(0), A(1), A(2), A(3))); return 0
    def glTexParameterf():
        g.texparam[(A(1))] = _f(A(2)); return 0

    table = {
        'malloc': malloc, 'calloc': calloc, 'free': free, 'memcpy': memcpy, 'memmove': memcpy,
        'memset': memset, 'strlen': strlen, 'strcpy': strcpy, 'strncpy': strncpy, 'strcat': strcat,
        'strcmp': strcmp, 'strncmp': strncmp, 'strstr': strstr, 'strdup': strdup,
        'strerror': strerror, 'vsnprintf': vsnprintf, '__android_log_print': android_log,
        'floor': floor_, 'ceil': ceil_, 'pow': pow_, 'ftime': ftime,
        'open': open_, 'read': read_, 'write': write_, 'lseek': lseek, 'close': close_,
        'stat': stat_, 'access': access_, 'mkdir': mkdir_, 'unlink': unlink_, 'rmdir': ret0,
        'rename': rename_, 'chdir': ret0, 'dup': dup_, 'fdopen': fdopen, 'fread': fread,
        'fseek': fseek, 'fclose': fclose, '__errno': errno_, 'qsort': qsort_, 'abort': abort_,
        'fcntl': ret0, 'pthread_mutex_lock': ret0, 'pthread_mutex_unlock': ret0,
        'socket': netlog('socket'), 'connect': netlog('connect'), 'select': netlog('select'),
        'recv': netlog('recv'), 'setsockopt': netlog('setsockopt'), 'getsockopt': netlog('getsockopt'),
        'shutdown': netlog('shutdown', 0), 'inet_addr': netlog('inet_addr'),
        '__stack_chk_fail': abort_,
        'glGenTextures': glGenTextures, 'glBindTexture': glBindTexture,
        'glTexImage2D': glTexImage2D, 'glTexSubImage2D': glTexSubImage2D,
        'glViewport': glViewport, 'glOrthof': glOrthof, 'glTexParameterf': glTexParameterf,
    }
    if name.startswith('gl') and name not in table:
        return ret0
    return table.get(name, unimpl)


# ---------------- JNI ----------------
JNI_NAMES = {6: 'FindClass', 17: 'ExceptionClear', 15: 'ExceptionOccurred', 19: 'PushLocalFrame',
             20: 'PopLocalFrame', 21: 'NewGlobalRef', 22: 'DeleteGlobalRef', 23: 'DeleteLocalRef',
             31: 'GetObjectClass', 33: 'GetMethodID', 34: 'CallObjectMethod', 49: 'CallIntMethod',
             61: 'CallVoidMethod', 94: 'GetFieldID', 100: 'GetIntField', 113: 'GetStaticMethodID',
             114: 'CallStaticObjectMethod', 117: 'CallStaticBooleanMethod', 129: 'CallStaticIntMethod',
             141: 'CallStaticVoidMethod', 144: 'GetStaticFieldID', 167: 'NewStringUTF',
             169: 'GetStringUTFChars', 170: 'ReleaseStringUTFChars', 171: 'GetArrayLength',
             176: 'NewByteArray', 184: 'GetByteArrayElements', 192: 'ReleaseByteArrayElements',
             200: 'GetByteArrayRegion', 208: 'SetByteArrayRegion', 228: 'ExceptionCheck',
             164: 'GetStringLength', 168: 'GetStringUTFLength'}


def static_call(g, name, va):
    """Các hàm Java tĩnh mà MIDAS gọi. Trả về giá trị (int hoặc handle)."""
    g.log.write('JNIC %s\n' % name)   # T7: ghi mọi lời gọi Java tĩnh (để chứng minh không có lời gọi mạng/MTX)
    if name == 'getUniqueAssetNameFromID':
        i = va.u32()
        return g.jobj(('str', g.asset_names.get(i, b'')))
    if name == 'getUniqueNumberFromStrings':
        o = g.jobjs.get(va.u32(), ('str', b''))
        s = o[1]
        if s not in g.asset_ids:
            g.asset_ids[s] = len(g.asset_ids) + 1
            g.asset_names[g.asset_ids[s]] = s
        if g.a.reslog: g.log.write('RES %s\n' % s.decode('latin1'))
        return g.asset_ids[s]
    if name == 'getVolume': return 3
    if name == 'freeMemory': return 64 << 20
    if name == 'getModel': return g.jobj(('str', b'SHW-M250S'))
    g.log.write('JNI static call %s (unhandled)\n' % name); g.log.flush()
    return 0


def jni_handler(g, idx):
    A = g.arg
    nm = JNI_NAMES.get(idx, 'JNI_%d' % idx)

    def h():
        if idx == 6:
            return g.jobj(('class', g.cstr(A(1))))
        if idx in (94, 33, 113, 144):
            return g.jobj(('id', g.cstr(A(2)), g.cstr(A(3))))
        if idx == 100:  # GetIntField(env, obj, fid)
            o = g.jobjs.get(A(1))
            return o[1] if o and o[0] == 'int' else 0
        if idx in (114, 117, 129, 141):
            m = g.jobjs.get(A(2), ('id', b'?', b''))
            return static_call(g, m[1].decode(), VA(g, first=3))
        if idx in (115, 118, 130, 142):   # CallStatic*MethodV (va_list)
            m = g.jobjs.get(A(2), ('id', b'?', b''))
            return static_call(g, m[1].decode(), VA(g, va_list=A(3)))
        if idx in (34, 49, 61):
            m = g.jobjs.get(A(2), ('id', b'?', b''))
            g.log.write('JNI call %s\n' % m[1]); return 0
        if idx == 167:
            return g.jobj(('str', g.cstr(A(1))))
        if idx == 169:
            o = g.jobjs.get(A(1), ('str', b''))
            if A(2): g.wr32(A(2), 0)
            return g.new_str(o[1])
        if idx == 168:
            return len(g.jobjs.get(A(1), ('str', b''))[1])
        if idx in (21, 31):
            return A(1)
        if idx in (17, 22, 23, 170, 192):
            return 0
        if idx in (15, 228):
            return 0
        if idx == 219:  # GetJavaVM
            g.wr32(A(1), g.javavm); return 0
        if idx == 4:
            return 0x00010006
        if idx == 19:
            return 0
        if idx == 20:
            return A(1)
        g.log.write('JNI %s (unhandled)\n' % nm); g.log.flush()
        return 0
    return h


# ---------------- boot / vòng lặp ----------------
def boot(g):
    a = g.a
    g.vfs_pos = 0
    g.texparam = {}
    g.asset_names = {}
    g.asset_ids = {}
    # JavaVM giả: bảng invoke 8 mục; 4 = AttachCurrentThread, 6 = GetEnv
    vt = g.misc_alloc(8 * 4)
    def vm_env():
        g.wr32(g.arg(1), g.env); return 0
    for i in range(8):
        g.wr32(vt + i * 4, g.stub('JVM_%d' % i, vm_env if i in (4, 6, 7) else (lambda: 0)))
    g.javavm = g.misc_alloc(4); g.wr32(g.javavm, vt)
    # T10: AndroidService (JNI Java) không có trong harness nên getAvailableStorageSpace = 0 -> MC_fsOpen trả -13 (mọi lần ghi tệp
    # thất bại, cả LOCAL.sav). Giả lập còn dung lượng để thử ghi hd_scale.txt.
    sp = g.syms.get('_ZN18MIDASKernelManager24getAvailableStorageSpaceEi')
    if sp:
        def avail(uc, addr, size, ud):
            uc.reg_write(UC_ARM_REG_R0, 0x40000000); uc.reg_write(UC_ARM_REG_PC, uc.reg_read(UC_ARM_REG_LR))
        g.uc.hook_add(UC_HOOK_CODE, avail, begin=sp, end=sp)
    g.storage_int = '/data/hl5/int'
    g.storage_ext = '/data/hl5/ext'

    def nested_call(addr, *args):
        uc = g.uc
        saved = {r: uc.reg_read(r) for r in range(UC_ARM_REG_R0, UC_ARM_REG_R12 + 1)}
        sp0 = uc.reg_read(UC_ARM_REG_SP); lr0 = uc.reg_read(UC_ARM_REG_LR); pc0 = uc.reg_read(UC_ARM_REG_PC)
        for i, v in enumerate(args): uc.reg_write(UC_ARM_REG_R0 + i, v)
        uc.reg_write(UC_ARM_REG_SP, (sp0 - 0x400) & ~7)
        stop = g.nested_stop
        uc.reg_write(UC_ARM_REG_LR, stop)
        uc.emu_start(addr, stop)
        r = uc.reg_read(UC_ARM_REG_R0)
        for k, v in saved.items(): uc.reg_write(k, v)
        uc.reg_write(UC_ARM_REG_SP, sp0); uc.reg_write(UC_ARM_REG_LR, lr0)
        return r
    g.nested_stop = g.stub('nested_stop', lambda: None)
    g.nested_call = nested_call

    for f in g.init_array:
        g.call(f)
    env = g.env
    this = g.jobj(('obj', b'CletEntry'))
    S = g.sym
    g.log.write('BOOT 1 nativeInitKernel\n')
    g.call(S('Java_co_kr_eamobile_CletEntry_nativeInitKernel'), env, this)
    W, H = a.w, a.h
    g.log.write('BOOT 2 nativeInitDisplay %dx%d\n' % (W, H))
    # (env,this, targetW,targetH, r,g,b,a, wipiW,wipiH, marginX,marginY, colorType, mag(f), filter, viewMode, useSub, subPix)
    g.autoMarginY = g.call(S('Java_co_kr_eamobile_CletEntry_nativeInitDisplay'), env, this,
                           W, H, 5, 6, 5, 0, W, H, 0, 0, 0, 1.0, 0x2600, 0, 0, 0)
    g.log.write('BOOT 3 storage\n')
    si = g.jobj(('str', g.storage_int.encode())); se = g.jobj(('str', g.storage_ext.encode()))
    g.call(S('Java_co_kr_eamobile_CletEntry_nativeInitFileStorage'), env, this, si, se)
    g.log.write('BOOT 4 VFS\n')
    fdo = g.jobj(('int', 3))
    g.call(S('Java_co_kr_eamobile_CletEntry_nativeInitVFS'), env, this, fdo, 0, 0, 0, len(g.vfs), 0)
    g.log.write('BOOT 5 audio\n')
    g.call(S('Java_co_kr_eamobile_CletEntry_nativeInitAudioConfiguration'), env, this, 0, 0)
    g.log.write('BOOT 6 start\n')
    g.call(S('Java_co_kr_eamobile_CletEntry_nativeStartApp'), env, this)
    g.log.flush()


def save_png(path, w, h, rgb):
    raw = b''.join(b'\0' + rgb[y * w * 3:(y + 1) * w * 3] for y in range(h))
    def chunk(t, d):
        c = struct.pack('>I', len(d)) + t + d
        return c + struct.pack('>I', zlib.crc32(t + d) & 0xffffffff)
    png = b'\x89PNG\r\n\x1a\n' + chunk(b'IHDR', struct.pack('>IIBBBBB', w, h, 8, 2, 0, 0, 0)) + \
        chunk(b'IDAT', zlib.compress(raw, 6)) + chunk(b'IEND', b'')
    open(path, 'wb').write(png)


def screen_rgb(g):
    tid = getattr(g, 'screen_tex', None)
    if tid is None or tid not in g.tex: return None
    TW, TH, fmt, typ, data = g.tex[tid]
    W, H = g.a.w, g.a.h
    out = bytearray(W * H * 3)
    if typ == 0x8363:
        for y in range(H):
            row = struct.unpack_from('<%dH' % W, data, y * TW * 2)
            o = y * W * 3
            for x, p in enumerate(row):
                out[o] = (p >> 11) << 3; out[o + 1] = ((p >> 5) & 63) << 2; out[o + 2] = (p & 31) << 3; o += 3
    else:
        for y in range(H):
            for x in range(W):
                s = (y * TW + x) * 4; d = (y * W + x) * 3
                out[d:d + 3] = data[s:s + 3]
    return bytes(out)


KEYS = {'UP': -1, 'DOWN': -2, 'LEFT': -3, 'RIGHT': -4, 'OK': -5, 'CLR': -16, 'LSK': -6, 'RSK': -7,
        **{str(i): 48 + i for i in range(10)}, 'STAR': 42, 'HASH': 35}


def run_frames(g):
    a = g.a
    S = g.sym
    loop = S('Java_co_kr_eamobile_CletEntry_nativeLoop')
    keyf = S('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
    events = {}
    for item in filter(None, a.keys.split(',')):
        fr, k = item.split(':')[:2]
        events.setdefault(int(fr), []).append(k)
    for fr in range(a.frames):
        g.frame = fr
        for k in events.get(fr, []):
            code = KEYS.get(k, None)
            if code is None: code = int(k)
            g.call(keyf, g.env, 0, 2, code & 0xffffffff, 0)   # 2 = nhấn (CletEntry.onKeyDown)
            g.call(keyf, g.env, 0, 3, code & 0xffffffff, 0)   # 3 = nhả
        g.vclock += FRAME_MS
        try:
            g.call(loop, g.env, 0)
        except Exception as e:
            g.log.write('frame %d: %r pc=%s\n' % (fr, e, g.sym_of(g.uc.reg_read(UC_ARM_REG_PC))))
            g.log.flush()
            raise
        if a.shot and fr % a.shot == a.shot - 1:
            rgb = screen_rgb(g)
            if rgb: save_png(os.path.join(a.out, 'f%05d.png' % fr), a.w, a.h, rgb)
    g.log.flush()
