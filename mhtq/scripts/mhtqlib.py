# -*- coding: utf-8 -*-
"""Thư viện dùng chung của dự án MHTQ: đọc/ghi .dat, đọc/vá constant pool của .class.

Chạy bằng `python3 -I` (không phụ thuộc thư mục hiện hành). Chỉ dùng thư viện chuẩn.
"""
import os
import re
import struct

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))   # .../mhtq

# ----------------------------------------------------------------- modified UTF-8
def mutf8_encode(s):
    out = bytearray()
    for ch in s:
        c = ord(ch)
        if c == 0:
            out += b'\xc0\x80'
        elif c < 0x80:
            out.append(c)
        elif c < 0x800:
            out += bytes([0xC0 | (c >> 6), 0x80 | (c & 0x3F)])
        elif c < 0x10000:
            out += bytes([0xE0 | (c >> 12), 0x80 | ((c >> 6) & 0x3F), 0x80 | (c & 0x3F)])
        else:
            c -= 0x10000
            for sur in (0xD800 | (c >> 10), 0xDC00 | (c & 0x3FF)):
                out += bytes([0xE0 | (sur >> 12), 0x80 | ((sur >> 6) & 0x3F), 0x80 | (sur & 0x3F)])
    return bytes(out)


def mutf8_decode(b):
    return b.replace(b'\xc0\x80', b'\x00').decode('utf-8', 'surrogatepass')


CJK_RE = re.compile(u'[\u3000-\u303f\u3400-\u9fff\uf900-\ufaff\uff00-\uffef]')
HAN_RE = re.compile(u'[\u3400-\u9fff\uf900-\ufaff]')


def has_cjk(s):
    return bool(CJK_RE.search(s))


# ----------------------------------------------------------------- escape cho TSV
def esc(s):
    return s.replace('\\', '\\\\').replace('\t', '\\t').replace('\n', '\\n').replace('\r', '\\r')


def unesc(s):
    out = []
    i = 0
    while i < len(s):
        c = s[i]
        if c == '\\' and i + 1 < len(s):
            n = s[i + 1]
            m = {'\\': '\\', 't': '\t', 'n': '\n', 'r': '\r'}.get(n)
            if m is None:
                raise ValueError('escape lạ: \\%s' % n)
            out.append(m)
            i += 2
        else:
            out.append(c)
            i += 1
    return ''.join(out)


# ----------------------------------------------------------------- script.dat
class Arg(object):
    """Tham số lệnh script: type + value (value là str với type 13/14)."""
    __slots__ = ('t', 'v')

    def __init__(self, t, v):
        self.t = t
        self.v = v


def parse_script(d):
    p = [0]

    def u16():
        v = struct.unpack_from('>H', d, p[0])[0]; p[0] += 2; return v

    def s16():
        v = struct.unpack_from('>h', d, p[0])[0]; p[0] += 2; return v

    def u8():
        v = d[p[0]]; p[0] += 1; return v

    H = u16(); hdr = d[p[0]:p[0] + H]; p[0] += H
    N = u16(); o = p[0]
    offs = struct.unpack_from('>%dI' % N, d, o)
    chunks = []

    def arg():
        t = u8()
        if t in (10, 11, 12, 17):
            v = s16()
        elif t in (13, 14):
            L = u16(); v = d[p[0]:p[0] + 2 * L].decode('utf-16-be'); p[0] += 2 * L
        elif t in (15, 16, 18):
            v = u8()
        else:
            v = None
        return Arg(t, v)

    for i in range(N):
        p[0] = o + 4 * (i + 1) + offs[i]
        M = u16(); ents = []
        for _ in range(M):
            s = s16(); n = u8(); args = []
            for _ in range(n):
                if s == -1:
                    a = arg(); op = u8(); b = arg(); args.append((a, op, b))
                else:
                    args.append(arg())
            jmp = s16() if s == -1 else None
            ents.append([s, args, jmp])
        chunks.append(ents)
    return bytes(hdr), chunks


def serialize_script(hdr, chunks):
    def arg(a):
        r = bytes([a.t])
        if a.t in (10, 11, 12, 17):
            r += struct.pack('>h', a.v)
        elif a.t in (13, 14):
            r += struct.pack('>H', len(a.v)) + a.v.encode('utf-16-be')
        elif a.t in (15, 16, 18):
            r += bytes([a.v])
        return r

    cb = []
    for ents in chunks:
        b = bytearray(struct.pack('>H', len(ents)))
        for s, args, jmp in ents:
            b += struct.pack('>hB', s, len(args))
            for a in args:
                if s == -1:
                    b += arg(a[0]) + bytes([a[1]]) + arg(a[2])
                else:
                    b += arg(a)
            if s == -1:
                b += struct.pack('>h', jmp)
        cb.append(bytes(b))
    N = len(chunks)
    out = bytearray(struct.pack('>H', len(hdr)) + hdr + struct.pack('>H', N))
    pos = 0
    offs = []
    for i in range(N):
        offs.append(4 * N + pos - 4 * (i + 1))
        pos += len(cb[i])
    out += struct.pack('>%dI' % N, *offs)
    for c in cb:
        out += c
    return bytes(out)


# ----------------------------------------------------------------- item/mission/skill.dat
SEQ = {
    'item': 'S Str S Str' + ' S' * 9,
    'mission': 'Str Str' + ' S' * 7 + ' Str Str' + ' S' * 5 + ' Str Str Str' + ' S' * 5,
    'skill': 'Str Str' + ' S' * 9 + ' Str S S SS S S SS S',
}


def parse_arrays(name, d):
    """Trả list [(kiểu, giá trị)]; Str -> list[str], S -> list[int], SS -> list[list[int]]."""
    p = [0]

    def u16():
        v = struct.unpack_from('>H', d, p[0])[0]; p[0] += 2; return v

    def i16():
        v = struct.unpack_from('>h', d, p[0])[0]; p[0] += 2; return v

    res = []
    for t in SEQ[name].split():
        n = u16()
        if t == 'S':
            res.append((t, [i16() for _ in range(n)]))
        elif t == 'SS':
            res.append((t, [[i16() for _ in range(u16())] for _ in range(n)]))
        else:
            arr = []
            for _ in range(n):
                L = u16(); arr.append(mutf8_decode(d[p[0]:p[0] + L])); p[0] += L
            res.append((t, arr))
    if p[0] != len(d):
        raise ValueError('%s.dat: dư %d byte' % (name, len(d) - p[0]))
    return res


def serialize_arrays(res):
    o = bytearray()
    for t, v in res:
        o += struct.pack('>H', len(v))
        if t == 'S':
            o += b''.join(struct.pack('>h', x) for x in v)
        elif t == 'SS':
            for x in v:
                o += struct.pack('>H', len(x)) + b''.join(struct.pack('>h', y) for y in x)
        else:
            for s in v:
                b = mutf8_encode(s)
                if len(b) > 65535:
                    raise ValueError('chuỗi quá dài (>65535 byte)')
                o += struct.pack('>H', len(b)) + b
    return bytes(o)


# ----------------------------------------------------------------- mate.dat
def parse_mate(d):
    """14 short + readUTF (tên nhân vật chính)."""
    shorts = d[:28]
    L = struct.unpack_from('>H', d, 28)[0]
    s = mutf8_decode(d[30:30 + L])
    if 30 + L != len(d):
        raise ValueError('mate.dat: dư byte')
    return shorts, s


def serialize_mate(shorts, s):
    b = mutf8_encode(s)
    return shorts + struct.pack('>H', len(b)) + b


# ----------------------------------------------------------------- .class
_LEN = [1] * 256
for _op in (0x10, 0x12, 0x15, 0x16, 0x17, 0x18, 0x19, 0x36, 0x37, 0x38, 0x39, 0x3a, 0xa9, 0xbc):
    _LEN[_op] = 2
for _op in list(range(0x99, 0xa9)) + [0x11, 0x13, 0x14, 0x84, 0xb2, 0xb3, 0xb4, 0xb5, 0xb6, 0xb7,
                                      0xb8, 0xbb, 0xbd, 0xc0, 0xc1, 0xc6, 0xc7]:
    _LEN[_op] = 3
_LEN[0xc5] = 4
for _op in (0xb9, 0xba, 0xc8, 0xc9):
    _LEN[_op] = 5


def iter_insns(code):
    """Sinh (offset, opcode, độ dài lệnh)."""
    i = 0
    n = len(code)
    while i < n:
        op = code[i]
        if op == 0xc4:                                  # wide
            ln = 6 if code[i + 1] == 0x84 else 4
        elif op == 0xaa:                                # tableswitch
            a = (i + 4) & ~3
            lo, hi = struct.unpack_from('>ii', code, a + 4)
            ln = a + 12 + 4 * (hi - lo + 1) - i
        elif op == 0xab:                                # lookupswitch
            a = (i + 4) & ~3
            np_ = struct.unpack_from('>i', code, a + 4)[0]
            ln = a + 8 + 8 * np_ - i
        else:
            ln = _LEN[op]
        yield i, op, ln
        i += ln


class ClassFile(object):
    """Đọc constant pool (giữ nguyên byte thô từng entry) để vá chuỗi Utf8 tại chỗ."""

    def __init__(self, data):
        self.data = data
        d = data
        if struct.unpack_from('>I', d, 0)[0] != 0xCAFEBABE:
            raise ValueError('không phải class')
        self.minor, self.major = struct.unpack_from('>HH', d, 4)
        n = struct.unpack_from('>H', d, 8)[0]
        self.n = n
        self.cp = [None] * n         # (tag, ...) ; Utf8: ('utf', bytes)
        self.spans = [None] * n      # (start, end) trong data
        o = 10
        i = 1
        while i < n:
            st = o
            t = d[o]
            if t == 1:
                L = struct.unpack_from('>H', d, o + 1)[0]
                self.cp[i] = (1, d[o + 3:o + 3 + L]); o += 3 + L
            elif t in (3, 4):
                self.cp[i] = (t,); o += 5
            elif t in (5, 6):
                self.cp[i] = (t,); o += 9
                self.spans[i] = (st, o); i += 2; continue
            elif t in (7, 8, 16, 19, 20):
                self.cp[i] = (t, struct.unpack_from('>H', d, o + 1)[0]); o += 3
            elif t in (9, 10, 11, 12, 17, 18):
                self.cp[i] = (t,) + struct.unpack_from('>HH', d, o + 1); o += 5
            elif t == 15:
                self.cp[i] = (t,); o += 4
            else:
                raise ValueError('cp tag lạ %d' % t)
            self.spans[i] = (st, o)
            i += 1
        self.cp_end = o
        acc, this, sup = struct.unpack_from('>HHH', d, o)
        self.name = mutf8_decode(self.cp[self.cp[this][1]][1])
        o += 6
        ni = struct.unpack_from('>H', d, o)[0]
        o += 2 + 2 * ni
        self.methods = []            # (name, desc, code_bytes, code_start_in_data)
        for kind in ('f', 'm'):
            cnt = struct.unpack_from('>H', d, o)[0]; o += 2
            for _ in range(cnt):
                _, ni_, di_, ac = struct.unpack_from('>HHHH', d, o); o += 8
                for _ in range(ac):
                    an, al = struct.unpack_from('>HI', d, o)
                    body = o + 6
                    if kind == 'm' and mutf8_decode(self.cp[an][1]) == 'Code':
                        cl = struct.unpack_from('>I', d, body + 4)[0]
                        self.methods.append((mutf8_decode(self.cp[ni_][1]), mutf8_decode(self.cp[di_][1]),
                                             d[body + 8:body + 8 + cl], body + 8))
                    o = body + al
        # chuỗi: cp index của entry String
        self.string_idx = [i for i in range(1, n) if self.cp[i] and self.cp[i][0] == 8]

    def utf(self, i):
        return mutf8_decode(self.cp[i][1])

    def string_text(self, i):
        return self.utf(self.cp[i][1])

    def utf_users(self):
        """utf index -> list tag của entry tham chiếu tới nó."""
        u = {}
        for i in range(1, self.n):
            e = self.cp[i]
            if not e:
                continue
            if e[0] in (7, 8, 16, 19, 20):
                u.setdefault(e[1], []).append(e[0])
            elif e[0] in (9, 10, 11, 12, 17, 18) and len(e) > 2:
                if e[0] == 12:
                    u.setdefault(e[1], []).append(12); u.setdefault(e[2], []).append(12)
        return u

    def usages(self):
        """cp-index String -> list (method, desc, offset) nơi ldc/ldc_w."""
        res = {}
        for name, desc, code, _ in self.methods:
            for off, op, ln in iter_insns(code):
                idx = None
                if op == 0x12:
                    idx = code[off + 1]
                elif op == 0x13:
                    idx = struct.unpack_from('>H', code, off + 1)[0]
                if idx and self.cp[idx] and self.cp[idx][0] == 8:
                    res.setdefault(idx, []).append((name, desc, off))
        return res

    def patch(self, repl):
        """repl: {utf_index: str mới}. Trả bytes class mới (các entry khác giữ nguyên byte)."""
        if not repl:
            return self.data
        out = bytearray(self.data[:10])
        for i in range(1, self.n):
            sp = self.spans[i]
            if sp is None:
                continue
            if i in repl:
                b = mutf8_encode(repl[i])
                if len(b) > 65535:
                    raise ValueError('%s: Utf8 #%d quá dài' % (self.name, i))
                out += b'\x01' + struct.pack('>H', len(b)) + b
            else:
                out += self.data[sp[0]:sp[1]]
        out += self.data[self.cp_end:]
        return bytes(out)


# ----------------------------------------------------------------- sửa class (thêm CP, thay thân method)
class ClassEditor(object):
    """Đọc toàn bộ class thành cấu trúc sửa được; ghi lại giữ nguyên mọi phần không đụng tới.

    Dùng cho patch_font.py: thêm entry constant pool (Class/NameAndType/Methodref), thay thân
    Code của method (giữ nguyên access flag/tên/descriptor), vá byte trong Code.
    """

    def __init__(self, data):
        d = data
        self.head = d[:8]                                  # magic, minor, major
        n = struct.unpack_from('>H', d, 8)[0]
        self.cp = [None]                                   # entry thô (bytes); 8-byte entry chiếm 2 chỗ
        o = 10
        i = 1
        while i < n:
            t = d[o]
            if t == 1:
                ln = 3 + struct.unpack_from('>H', d, o + 1)[0]
            elif t in (3, 4, 9, 10, 11, 12, 17, 18):
                ln = 5
            elif t in (5, 6):
                ln = 9
            elif t in (7, 8, 16, 19, 20):
                ln = 3
            elif t == 15:
                ln = 4
            else:
                raise ValueError('cp tag lạ %d' % t)
            self.cp.append(d[o:o + ln])
            if t in (5, 6):
                self.cp.append(None)
                i += 1
            o += ln
            i += 1
        self.acc_this_super = d[o:o + 6]
        o += 6
        ni = struct.unpack_from('>H', d, o)[0]
        self.interfaces = d[o:o + 2 + 2 * ni]
        o += 2 + 2 * ni
        # fields: giữ thô
        fs = o
        nf = struct.unpack_from('>H', d, o)[0]
        o += 2
        for _ in range(nf):
            ac = struct.unpack_from('>H', d, o + 6)[0]
            o += 8
            for _ in range(ac):
                al = struct.unpack_from('>I', d, o + 2)[0]
                o += 6 + al
        self.fields_raw = d[fs:o]
        nm = struct.unpack_from('>H', d, o)[0]
        o += 2
        self.methods = []        # dict(acc, name, desc, attrs=[(name_idx, bytes)])
        for _ in range(nm):
            acc, ni_, di_, ac = struct.unpack_from('>HHHH', d, o)
            o += 8
            attrs = []
            for _ in range(ac):
                an, al = struct.unpack_from('>HI', d, o)
                attrs.append((an, d[o + 6:o + 6 + al]))
                o += 6 + al
            self.methods.append({'acc': acc, 'name': ni_, 'desc': di_, 'attrs': attrs})
        self.tail = d[o:]        # class attributes

    # --- constant pool
    def _utf(self, i):
        e = self.cp[i]
        return mutf8_decode(e[3:]) if e and e[0] == 1 else None

    def add(self, raw):
        if raw in self.cp:
            return self.cp.index(raw)
        self.cp.append(raw)
        return len(self.cp) - 1

    def utf8(self, s):
        b = mutf8_encode(s)
        return self.add(b'\x01' + struct.pack('>H', len(b)) + b)

    def klass(self, name):
        return self.add(b'\x07' + struct.pack('>H', self.utf8(name)))

    def nat(self, name, desc):
        return self.add(b'\x0c' + struct.pack('>HH', self.utf8(name), self.utf8(desc)))

    def methodref(self, cls, name, desc):
        return self.add(b'\x0a' + struct.pack('>HH', self.klass(cls), self.nat(name, desc)))

    # --- methods
    def find(self, name, desc):
        for m in self.methods:
            if self._utf(m['name']) == name and self._utf(m['desc']) == desc:
                return m
        raise KeyError('%s%s' % (name, desc))

    def code_attr_index(self, m):
        for k, (an, body) in enumerate(m['attrs']):
            if self._utf(an) == 'Code':
                return k
        raise KeyError('không có Code')

    def get_code(self, m):
        body = m['attrs'][self.code_attr_index(m)][1]
        cl = struct.unpack_from('>I', body, 4)[0]
        return body[8:8 + cl]

    def set_code(self, m, code, max_stack, max_locals, keep_exc=False):
        """Thay hẳn thân method: bỏ bảng ngoại lệ và các attribute con (StackMap, LineNumberTable...)."""
        k = self.code_attr_index(m)
        an = m['attrs'][k][0]
        body = struct.pack('>HHI', max_stack, max_locals, len(code)) + code + struct.pack('>HH', 0, 0)
        m['attrs'][k] = (an, body)

    def patch_code(self, m, off, old, new):
        """Sửa byte tại offset của Code (kiểm old). Giữ nguyên độ dài và mọi attribute."""
        assert len(old) == len(new)
        k = self.code_attr_index(m)
        an, body = m['attrs'][k]
        pos = 8 + off
        if body[pos:pos + len(old)] != old:
            raise ValueError('byte tại +%d là %s, mong đợi %s' % (off, body[pos:pos + len(old)].hex(), old.hex()))
        body = body[:pos] + new + body[pos + len(new):]
        m['attrs'][k] = (an, body)

    def serialize(self):
        out = bytearray(self.head)
        out += struct.pack('>H', len(self.cp))
        for e in self.cp[1:]:
            if e is not None:
                out += e
        out += self.acc_this_super + self.interfaces + self.fields_raw
        out += struct.pack('>H', len(self.methods))
        for m in self.methods:
            out += struct.pack('>HHHH', m['acc'], m['name'], m['desc'], len(m['attrs']))
            for an, body in m['attrs']:
                out += struct.pack('>HI', an, len(body)) + body
        out += self.tail
        return bytes(out)
