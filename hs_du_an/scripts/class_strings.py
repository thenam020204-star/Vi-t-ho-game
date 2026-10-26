"""Liệt kê chuỗi CJK trong constant pool các .class và nơi dùng (theo bytecode).

  python3 class_strings.py <dir class giải nén> <ra.tsv>

Mỗi lần `ldc/ldc_w` nạp chuỗi CJK: ghi class, method, pc và tối đa 8 lệnh gọi/field kế tiếp
trong cùng method (đến lệnh return/athrow). Phân loại tự động:
  KHOA  — chuỗi đi vào equals/Hashtable/openRecordStore/getResourceAsStream/getAppProperty/platformRequest/…
  HIEN  — chỉ đi vào vẽ/đo/ghép chuỗi/gán field (an toàn dịch nếu quản đốc xác nhận)
  ?     — chưa rõ
"""
import os, struct, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from cp_redirect import CF, walk

KEY_CALLS = ('equals', 'Hashtable.get', 'Hashtable.put', 'Hashtable.containsKey', 'Hashtable.remove',
             'openRecordStore', 'deleteRecordStore', 'getResourceAsStream', 'getAppProperty',
             'platformRequest', 'Connector.open', 'startsWith', 'endsWith', 'indexOf', 'compareTo',
             'equalsIgnoreCase', 'parseInt', 'getTitle')
SHOW_CALLS = ('drawString', 'VnFont', 'stringWidth', 'StringBuffer.append', 'setTitle', 'Alert.<init>',
              'StringItem', 'TextBox', 'Form.<init>', 'List.<init>', 'append', 'Command.<init>', 'setText', 'split')


def cjk(s):
    return any(0x2E80 <= ord(c) <= 0x9FFF or 0xFF00 <= ord(c) <= 0xFFEF or 0x3000 <= ord(c) <= 0x303F for c in s)


def ref_name(cf, idx):
    e = cf.cp[idx]
    if e is None or e[0] not in (9, 10, 11):
        return None
    cls = cf.utf(cf.cp[e[1]][1])
    nt = cf.cp[e[2]]
    return '%s.%s%s' % (cls.split('/')[-1] if '/' in cls and cls.startswith('java') else cls, cf.utf(nt[1]), cf.utf(nt[2]))


def analyse(path, rel):
    data = open(path, 'rb').read()
    cf = CF(data)
    rows = []
    for mname, start, ln in cf.code_ranges():
        code = data[start:start + ln]
        ins = list(walk(code))
        for k, (pc, op) in enumerate(ins):
            if op == 0x12:
                idx = code[pc + 1]
            elif op == 0x13:
                idx = struct.unpack('>H', code[pc + 1:pc + 3])[0]
            else:
                continue
            e = cf.cp[idx]
            if not e or e[0] != 8:
                continue
            s = cf.utf(e[1])
            if not cjk(s):
                continue
            nxt = []
            for pc2, op2 in ins[k + 1:k + 40]:
                if op2 in (0xAC, 0xAD, 0xAE, 0xAF, 0xB0, 0xB1, 0xBF):
                    nxt.append('return'); break
                if op2 in (0xB2, 0xB3, 0xB4, 0xB5, 0xB6, 0xB7, 0xB8, 0xB9):
                    r = ref_name(cf, struct.unpack('>H', code[pc2 + 1:pc2 + 3])[0])
                    nxt.append({0xB2: 'gs ', 0xB3: 'ps ', 0xB4: 'gf ', 0xB5: 'pf '}.get(op2, '') + (r or '?'))
                    if len(nxt) >= 8:
                        break
            first = ' | '.join(nxt[:3])
            if any(c in first for c in KEY_CALLS):
                kind = 'KHOA'
            elif any(c in ' | '.join(nxt[:2]) for c in SHOW_CALLS):
                kind = 'HIEN'
            else:
                kind = '?'
            rows.append((rel, mname, pc, s, kind, ' | '.join(nxt)))
    return rows


def main():
    src, out = sys.argv[1], sys.argv[2]
    rows = []
    for root, _, files in os.walk(src):
        for f in sorted(files):
            if f.endswith('.class'):
                p = os.path.join(root, f)
                rows += analyse(p, os.path.relpath(p, src)[:-6])
    esc = lambda s: s.replace('\\', '\\\\').replace('\n', '\\n').replace('\t', '\\t')
    with open(out, 'w', encoding='utf-8') as fo:
        fo.write('class\tmethod\tpc\tchuoi\tphan_loai\tlenh_ke_tiep\n')
        for r in sorted(rows):
            fo.write('%s\t%s\t%d\t%s\t%s\t%s\n' % (r[0], r[1], r[2], esc(r[3]), r[4], r[5]))
    from collections import Counter
    print(out, len(rows), dict(Counter(r[4] for r in rows)))


if __name__ == '__main__':
    main()
