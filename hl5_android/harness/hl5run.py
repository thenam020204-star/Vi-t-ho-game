#!/usr/bin/env python3
"""Headless harness: chạy libHeroesLore5.so (ARM32) bằng Unicorn trên máy host.

Mô phỏng tối thiểu những gì runner/bridge Android cung cấp: libc, GLES 1.1 (chỉ ghi
lại texture màn hình), JNIEnv giả, VFS (data.vfs.mp3), lưu game vào thư mục tạm.

Dùng:  python3 -I hl5run.py --so libHeroesLore5.so --vfs data.vfs.mp3 --out OUTDIR
           [--w 400 --h 240] [--frames 300] [--shot 30] [--keys "frame:key:down,..."]
"""
import argparse, os, struct, sys, time, math, collections
from unicorn import *
from unicorn.arm_const import *
from elftools.elf.elffile import ELFFile
from elftools.elf.relocation import RelocationSection

BASE = 0x10000000
STUB = 0x08000000          # vùng stub cho hàm import / JNI
HEAP = 0x20000000
HEAP_SZ = 0x08000000       # 128 MB
STACK = 0x70000000
STACK_SZ = 0x00400000
MISC = 0x60000000          # dữ liệu host (JNIEnv, chuỗi...)
MISC_SZ = 0x00400000

R_ARM_ABS32, R_ARM_GLOB_DAT, R_ARM_JUMP_SLOT, R_ARM_RELATIVE = 2, 21, 22, 23


class Heap:
    def __init__(self, uc):
        self.uc = uc
        self.top = HEAP + 0x1000
        self.sizes = {}
        self.free = collections.defaultdict(list)

    def alloc(self, n):
        n = (max(n, 1) + 15) & ~15
        if self.free[n]:
            a = self.free[n].pop()
        else:
            a = self.top
            self.top += n
            if self.top > HEAP + HEAP_SZ:
                raise MemoryError('guest heap exhausted')
        self.sizes[a] = n
        return a

    def release(self, a):
        n = self.sizes.pop(a, None)
        if n:
            self.free[n].append(a)


class Guest:
    def __init__(self, a):
        self.a = a
        self.uc = Uc(UC_ARCH_ARM, UC_MODE_ARM)
        uc = self.uc
        for base, size in ((STUB, 0x100000), (HEAP, HEAP_SZ), (STACK, STACK_SZ), (MISC, MISC_SZ)):
            uc.mem_map(base, size)
        self.heap = Heap(uc)
        self.misc_top = MISC
        self.handlers = {}       # addr -> (name, fn)
        self.stub_top = STUB
        self.syms = {}
        self.fds = {}
        self.next_fd = 10
        self.files = {}          # FILE* -> fd
        self.vfs = open(a.vfs, 'rb').read()
        self.tex = {}            # texture id -> (w, h, fmt, type, bytearray)
        self.bound = 0
        self.next_tex = 1
        self.vclock = 1_600_000_000_000
        self.frame = 0
        self.log = open(os.path.join(a.out, 'run.log'), 'w')
        self.load_elf(a.so)
        self.setup_jni()
        uc.hook_add(UC_HOOK_CODE, self.on_stub, begin=STUB, end=STUB + 0x100000)
        uc.hook_add(UC_HOOK_MEM_UNMAPPED, self.on_fault)

    # ---------- bộ nhớ tiện ích ----------
    def rd32(self, a): return struct.unpack('<I', self.uc.mem_read(a, 4))[0]
    def wr32(self, a, v): self.uc.mem_write(a, struct.pack('<I', v & 0xffffffff))
    def cstr(self, a, n=4096):
        if not a: return b''
        out = bytearray()
        while len(out) < n:
            c = self.uc.mem_read(a + len(out), 64)
            i = c.find(b'\0')
            if i >= 0:
                out += c[:i]; break
            out += c
        return bytes(out)

    def misc_alloc(self, n):
        a = self.misc_top
        self.misc_top += (n + 15) & ~15
        return a

    def new_str(self, s):
        if isinstance(s, str): s = s.encode()
        a = self.heap.alloc(len(s) + 1)
        self.uc.mem_write(a, s + b'\0')
        return a

    def stub(self, name, fn):
        a = self.stub_top
        self.stub_top += 8
        self.uc.mem_write(a, struct.pack('<I', 0xe12fff1e))  # bx lr
        self.handlers[a] = (name, fn)
        return a

    # ---------- ELF ----------
    def load_elf(self, path):
        f = open(path, 'rb')
        elf = ELFFile(f)
        hi = 0
        for seg in elf.iter_segments():
            if seg['p_type'] == 'PT_LOAD':
                hi = max(hi, seg['p_vaddr'] + seg['p_memsz'])
        self.uc.mem_map(BASE, (hi + 0xfff) & ~0xfff)
        for seg in elf.iter_segments():
            if seg['p_type'] == 'PT_LOAD':
                self.uc.mem_write(BASE + seg['p_vaddr'], seg.data())
        dsym = elf.get_section_by_name('.dynsym')
        for s in elf.get_section_by_name('.symtab').iter_symbols():
            if s['st_value'] and s.name:
                self.syms[s.name] = BASE + s['st_value']
        self.addr2sym = sorted((v, k) for k, v in self.syms.items())
        imports = {}
        for sec in elf.iter_sections():
            if not isinstance(sec, RelocationSection): continue
            for r in sec.iter_relocations():
                t = r['r_info_type']; off = BASE + r['r_offset']
                if t == R_ARM_RELATIVE:
                    self.wr32(off, self.rd32(off) + BASE)
                elif t in (R_ARM_GLOB_DAT, R_ARM_JUMP_SLOT, R_ARM_ABS32):
                    sym = dsym.get_symbol(r['r_info_sym'])
                    if sym['st_shndx'] != 'SHN_UNDEF' and sym['st_value']:
                        v = BASE + sym['st_value']
                    else:
                        v = imports.get(sym.name)
                        if v is None:
                            v = imports[sym.name] = self.import_addr(sym.name)
                    if t == R_ARM_ABS32:
                        v += self.rd32(off)
                    self.wr32(off, v)
        self.init_array = []
        ia = elf.get_section_by_name('.init_array')
        if ia:
            d = ia.data()
            for i in range(0, len(d), 4):
                p = self.rd32(BASE + ia['sh_addr'] + i)
                if p not in (0, 0xffffffff): self.init_array.append(p)

    def sym_of(self, a):
        import bisect
        i = bisect.bisect_right(self.addr2sym, (a, '￿')) - 1
        if i >= 0:
            v, k = self.addr2sym[i]
            return '%s+0x%x' % (k, a - v)
        return hex(a)

    def import_addr(self, name):
        from hl5libc import make_handler
        if name == '__stack_chk_guard':
            a = self.misc_alloc(4); self.wr32(a, 0xdeadbeef); return a
        return self.stub(name, make_handler(self, name))

    # ---------- JNI giả ----------
    def setup_jni(self):
        from hl5libc import jni_handler
        n = 240
        self.jni_table = self.misc_alloc(n * 4)
        for i in range(n):
            self.wr32(self.jni_table + i * 4, self.stub('JNI_%d' % i, jni_handler(self, i)))
        self.env = self.misc_alloc(4)
        self.wr32(self.env, self.jni_table)
        self.jobjs = {}
        self.next_obj = 0x1000

    def jobj(self, v):
        h = self.next_obj; self.next_obj += 4
        self.jobjs[h] = v
        return h

    # ---------- chạy ----------
    def on_stub(self, uc, addr, size, _):
        h = self.handlers.get(addr)
        if not h: return
        name, fn = h
        try:
            r = fn()
        except Exception as e:
            self.log.write('EXC in %s: %r\n' % (name, e)); self.log.flush()
            raise
        if r is not None:
            if isinstance(r, tuple):
                uc.reg_write(UC_ARM_REG_R0, r[0] & 0xffffffff)
                uc.reg_write(UC_ARM_REG_R1, r[1] & 0xffffffff)
            else:
                uc.reg_write(UC_ARM_REG_R0, r & 0xffffffff)

    def on_fault(self, uc, access, addr, size, value, _):
        pc = uc.reg_read(UC_ARM_REG_PC)
        lr = uc.reg_read(UC_ARM_REG_LR)
        self.log.write('FAULT access=%d addr=%#x pc=%s lr=%s\n' % (access, addr, self.sym_of(pc), self.sym_of(lr)))
        self.log.flush()
        return False

    def arg(self, i):
        if i < 4:
            return self.uc.reg_read(UC_ARM_REG_R0 + i)
        sp = self.uc.reg_read(UC_ARM_REG_SP)
        return self.rd32(sp + (i - 4) * 4)

    def call(self, addr, *args, max_insn=0):
        uc = self.uc
        sp = STACK + STACK_SZ - 0x100
        reg_args = list(args[:4]); stk = list(args[4:])
        sp -= len(stk) * 4; sp &= ~7
        for i, v in enumerate(stk):
            if isinstance(v, float): v = struct.unpack('<I', struct.pack('<f', v))[0]
            self.wr32(sp + i * 4, v)
        for i, v in enumerate(reg_args):
            if isinstance(v, float): v = struct.unpack('<I', struct.pack('<f', v))[0]
            uc.reg_write(UC_ARM_REG_R0 + i, v & 0xffffffff)
        uc.reg_write(UC_ARM_REG_SP, sp)
        ret = self.stub_top  # một stub "dừng"
        self.uc.mem_write(ret, struct.pack('<I', 0xe12fff1e))
        uc.reg_write(UC_ARM_REG_LR, ret)
        thumb = addr & 1
        uc.emu_start(addr, ret, count=max_insn)
        return uc.reg_read(UC_ARM_REG_R0)

    def sym(self, name):
        return self.syms[name]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--so', required=True)
    ap.add_argument('--vfs', required=True)
    ap.add_argument('--out', required=True)
    ap.add_argument('--w', type=int, default=400)
    ap.add_argument('--h', type=int, default=240)
    ap.add_argument('--frames', type=int, default=200)
    ap.add_argument('--shot', type=int, default=20)
    ap.add_argument('--keys', default='')
    ap.add_argument('--touch', default='')
    ap.add_argument('--storage', default=None)
    ap.add_argument('--reslog', action='store_true')
    a = ap.parse_args()
    os.makedirs(a.out, exist_ok=True)
    a.storage = a.storage or os.path.join(a.out, 'storage')
    os.makedirs(a.storage, exist_ok=True)
    sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
    g = Guest(a)
    from hl5libc import boot, run_frames
    boot(g)
    run_frames(g)


if __name__ == '__main__':
    main()
