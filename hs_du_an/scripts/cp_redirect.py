"""Chuyển hướng lời gọi method trong file .class (patch bytecode tĩnh, không đổi độ dài code).

Mỗi luật: (op_cũ, owner, name, desc) -> (op_mới, owner_mới, name_mới, desc_mới).
invokevirtual X.m(A)R -> invokestatic Y.m(LX;A)R giữ nguyên bố cục stack, nên
max_stack, StackMap và offset nhảy không đổi. Chỉ thêm entry mới vào CUỐI constant pool
(chỉ số cũ không đổi) và sửa 3 byte của lệnh gọi.

Dùng:  python3 cp_redirect.py <rules.tsv> <in_dir> <out_dir> [--report file]
rules.tsv: op_cu  owner  name  desc  op_moi  owner_moi  name_moi  desc_moi  (op: invokevirtual|invokestatic)
"""
import os, struct, sys

OPS = {'invokevirtual': 0xB6, 'invokespecial': 0xB7, 'invokestatic': 0xB8}

# độ dài lệnh JVM (trừ tableswitch/lookupswitch/wide xử lý riêng)
LEN = [1] * 256
for op in list(range(0x10, 0x11)) + [0x12, 0x15, 0x16, 0x17, 0x18, 0x19, 0x36, 0x37, 0x38, 0x39, 0x3A, 0xA9, 0xBC]:
    LEN[op] = 2
for op in [0x11, 0x13, 0x14, 0x84] + list(range(0x99, 0xA9)) + [0xB2, 0xB3, 0xB4, 0xB5, 0xB6, 0xB7, 0xB8, 0xBB, 0xBD, 0xC0, 0xC1, 0xC6, 0xC7]:
    LEN[op] = 3
LEN[0xC5] = 4
for op in [0xB9, 0xC8, 0xC9]:
    LEN[op] = 5
LEN[0xBA] = 5


class CF(object):
    def __init__(self, data):
        self.d = data
        n = struct.unpack('>H', data[8:10])[0]
        self.cp = [None] * n
        p = 10
        i = 1
        while i < n:
            t = data[p]
            if t == 1:
                L = struct.unpack('>H', data[p + 1:p + 3])[0]
                self.cp[i] = (1, data[p + 3:p + 3 + L].decode('utf-8', 'surrogatepass'))
                p += 3 + L
            elif t in (3, 4):
                self.cp[i] = (t, data[p + 1:p + 5]); p += 5
            elif t in (5, 6):
                self.cp[i] = (t, data[p + 1:p + 9]); p += 9; i += 1
            elif t in (7, 8):
                self.cp[i] = (t, struct.unpack('>H', data[p + 1:p + 3])[0]); p += 3
            elif t in (9, 10, 11, 12):
                self.cp[i] = (t,) + struct.unpack('>HH', data[p + 1:p + 5]); p += 5
            else:
                raise ValueError('tag CP lạ %d' % t)
            i += 1
        self.cp_end = p
        self.new = []          # bytes entry mới
        self.ncount = n

    def utf(self, i):
        return self.cp[i][1]

    def methodref(self, i):
        e = self.cp[i]
        if e is None or e[0] not in (10, 11):
            return None
        cls = self.utf(self.cp[e[1]][1])
        nt = self.cp[e[2]]
        return cls, self.utf(nt[1]), self.utf(nt[2])

    def _add(self, tag, payload):
        idx = self.ncount
        self.new.append(bytes([tag]) + payload)
        self.ncount += 1
        self.cp.append((tag, payload))
        return idx

    def add_utf(self, s):
        for i, e in enumerate(self.cp):
            if e and e[0] == 1 and e[1] == s:
                return i
        r = s.encode('utf-8')
        idx = self._add(1, struct.pack('>H', len(r)) + r)
        self.cp[idx] = (1, s)
        return idx

    def add_class(self, name):
        u = self.add_utf(name)
        for i, e in enumerate(self.cp):
            if e and e[0] == 7 and e[1] == u:
                return i
        idx = self._add(7, struct.pack('>H', u))
        self.cp[idx] = (7, u)
        return idx

    def add_methodref(self, owner, name, desc):
        c = self.add_class(owner)
        n = self.add_utf(name)
        t = self.add_utf(desc)
        nt = None
        for i, e in enumerate(self.cp):
            if e and e[0] == 12 and e[1] == n and e[2] == t:
                nt = i
        if nt is None:
            nt = self._add(12, struct.pack('>HH', n, t))
            self.cp[nt] = (12, n, t)
        for i, e in enumerate(self.cp):
            if e and e[0] == 10 and e[1] == c and e[2] == nt:
                return i
        idx = self._add(10, struct.pack('>HH', c, nt))
        self.cp[idx] = (10, c, nt)
        return idx

    def code_ranges(self):
        """Trả về list (method_name, code_start, code_len) theo offset trong file gốc."""
        d = self.d
        p = self.cp_end + 6
        ni = struct.unpack('>H', d[p:p + 2])[0]
        p += 2 + 2 * ni
        out = []
        for kind in ('field', 'method'):
            cnt = struct.unpack('>H', d[p:p + 2])[0]; p += 2
            for _ in range(cnt):
                _, ni_, di_, ac = struct.unpack('>HHHH', d[p:p + 8]); p += 8
                for _ in range(ac):
                    an, al = struct.unpack('>HI', d[p:p + 6])
                    if kind == 'method' and self.utf(an) == 'Code':
                        cl = struct.unpack('>I', d[p + 10:p + 14])[0]
                        out.append((self.utf(ni_) + self.utf(di_), p + 14, cl))
                    p += 6 + al
        return out


def walk(code):
    """Sinh (pc, opcode) cho từng lệnh."""
    pc = 0
    n = len(code)
    while pc < n:
        op = code[pc]
        yield pc, op
        if op == 0xAA:  # tableswitch
            q = (pc + 4) & ~3
            lo, hi = struct.unpack('>ii', code[q + 4:q + 12])
            pc = q + 12 + 4 * (hi - lo + 1)
        elif op == 0xAB:  # lookupswitch
            q = (pc + 4) & ~3
            np_ = struct.unpack('>i', code[q + 4:q + 8])[0]
            pc = q + 8 + 8 * np_
        elif op == 0xC4:  # wide
            pc += 6 if code[pc + 1] == 0x84 else 4
        else:
            pc += LEN[op]


def patch_class(data, rules):
    cf = CF(data)
    buf = bytearray(data)
    hits = []
    for mname, start, ln in cf.code_ranges():
        code = data[start:start + ln]
        for pc, op in walk(code):
            if op not in (0xB6, 0xB7, 0xB8):
                continue
            idx = struct.unpack('>H', code[pc + 1:pc + 3])[0]
            ref = cf.methodref(idx)
            if ref is None:
                continue
            for r in rules:
                if OPS[r[0]] == op and (r[1], r[2], r[3]) == ref:
                    ni = cf.add_methodref(r[5], r[6], r[7])
                    buf[start + pc] = OPS[r[4]]
                    buf[start + pc + 1:start + pc + 3] = struct.pack('>H', ni)
                    hits.append((mname, pc, '%s.%s%s' % ref))
    if not hits:
        return data, hits
    out = bytes(buf[:8]) + struct.pack('>H', cf.ncount) + bytes(buf[10:cf.cp_end]) + b''.join(cf.new) + bytes(buf[cf.cp_end:])
    return out, hits


def load_rules(path):
    rules = []
    for line in open(path, encoding='utf-8'):
        line = line.rstrip('\n')
        if not line or line.startswith('#'):
            continue
        p = line.split('\t')
        assert len(p) == 8, line
        rules.append(tuple(p))
    return rules


def main():
    rules = load_rules(sys.argv[1])
    src, dst = sys.argv[2], sys.argv[3]
    rep = sys.argv[5] if len(sys.argv) > 5 and sys.argv[4] == '--report' else None
    lines = []
    for root, _, files in os.walk(src):
        for f in files:
            if not f.endswith('.class'):
                continue
            p = os.path.join(root, f)
            rel = os.path.relpath(p, src)
            data = open(p, 'rb').read()
            out, hits = patch_class(data, rules)
            if hits:
                q = os.path.join(dst, rel)
                os.makedirs(os.path.dirname(q) or '.', exist_ok=True)
                open(q, 'wb').write(out)
                for h in hits:
                    lines.append('%s\t%s\t%d\t%s' % (rel, h[0], h[1], h[2]))
    lines.sort()
    txt = '\n'.join(lines) + '\n'
    if rep:
        open(rep, 'w', encoding='utf-8').write(txt)
    print('%d lời gọi đã chuyển hướng trong %d class' % (len(lines), len(set(l.split('\t')[0] for l in lines))))


if __name__ == '__main__':
    main()
