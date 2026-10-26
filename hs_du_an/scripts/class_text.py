"""Trích / vá chuỗi hiển thị CJK trong constant pool .class.

  python3 class_text.py extract <dir class> <N> <ra.tsv>       cột như text_can_dich.tsv
  python3 class_text.py patch   <dir class> <tsv> <dir ra>     ghi class đã vá (chỉ class có đổi)

Chỉ đụng CONSTANT_Utf8 được tham chiếu DUY NHẤT bởi CONSTANT_String (không phải tên class/field/method,
descriptor, NameAndType). Thay độ dài một Utf8 không làm lệch gì: constant pool đọc tuần tự, chỉ số entry giữ
nguyên, code/attribute nằm sau CP và không chứa offset tuyệt đối trong file.
Chuỗi là khoá thật (so với dữ liệu ngoài) nằm trong KEEP: không trích, không vá.
"""
import os, struct, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from cp_redirect import CF
from class_strings import cjk, analyse

KEEP = {'付费成功\n', '）'}


def esc(s):
    return s.replace('\\', '\\\\').replace('\n', '\\n').replace('\t', '\\t')


def unesc(s):
    out = []; i = 0
    while i < len(s):
        if s[i] == '\\' and i + 1 < len(s):
            out.append({'n': '\n', 't': '\t', '\\': '\\'}.get(s[i + 1], s[i + 1])); i += 2
        else:
            out.append(s[i]); i += 1
    return ''.join(out)


def string_utfs(cf):
    """Utf8 index -> True nếu chỉ được CONSTANT_String tham chiếu."""
    by_string, other = set(), set()
    for e in cf.cp:
        if not e:
            continue
        if e[0] == 8:
            by_string.add(e[1])
        elif e[0] == 7:
            other.add(e[1])
        elif e[0] == 12:
            other.add(e[1]); other.add(e[2])
    # Utf8 còn được dùng bởi tên field/method/attribute: quét phần sau CP
    d = cf.d; p = cf.cp_end + 6
    ni = struct.unpack('>H', d[p:p + 2])[0]; p += 2 + 2 * ni

    def attrs(p):
        c = struct.unpack('>H', d[p:p + 2])[0]; p += 2
        for _ in range(c):
            an, al = struct.unpack('>HI', d[p:p + 6]); other.add(an); p += 6 + al
        return p
    for _ in range(2):
        c = struct.unpack('>H', d[p:p + 2])[0]; p += 2
        for _ in range(c):
            _, n_, ds, = struct.unpack('>HHH', d[p:p + 6]); other.add(n_); other.add(ds); p = attrs(p + 6)
    return by_string - other


def extract(src, n, out):
    rows = []
    for root, _, files in os.walk(src):
        for f in sorted(files):
            if not f.endswith('.class'):
                continue
            path = os.path.join(root, f); rel = os.path.relpath(path, src)[:-6]
            cf = CF(open(path, 'rb').read())
            ok = string_utfs(cf)
            use = {}
            for r in analyse(path, rel):
                use.setdefault(r[3], '%s %s@%d → %s' % (r[4], r[1].split('(')[0], r[2], r[5][:90]))
            for i in sorted(ok):
                s = cf.utf(i)
                if cjk(s) and s not in KEEP:
                    rows.append(('p%s:class/%s#%d' % (n, rel, i), 'class/%s.class' % rel, str(i), esc(s), '',
                                 'code: ' + esc(use.get(s, 'không thấy ldc (hằng tĩnh?)')), ''))
    with open(out, 'w', encoding='utf-8') as fo:
        fo.write('id\tfile\toffset\tgoc\tdich\tghi_chu\tgioi_han\n')
        for r in rows:
            fo.write('\t'.join(r) + '\n')
    print(out, len(rows))


def patch_bytes(data, repl):
    cf = CF(data)
    ok = string_utfs(cf)
    out = bytearray(data[:10]); p = 10; i = 1
    while i < len(cf.cp):
        t = data[p]
        if t == 1:
            L = struct.unpack('>H', data[p + 1:p + 3])[0]
            if i in repl:
                assert i in ok, i
                r = repl[i].encode('utf-8')
                out += b'\x01' + struct.pack('>H', len(r)) + r
            else:
                out += data[p:p + 3 + L]
            p += 3 + L
        elif t in (3, 4):
            out += data[p:p + 5]; p += 5
        elif t in (5, 6):
            out += data[p:p + 9]; p += 9; i += 1
        elif t in (7, 8):
            out += data[p:p + 3]; p += 3
        else:
            out += data[p:p + 5]; p += 5
        i += 1
    assert p == cf.cp_end
    out += data[p:]
    return bytes(out)


def patch(src, tsv, dst):
    repl = {}
    for ln in open(tsv, encoding='utf-8').read().split('\n')[1:]:
        if not ln:
            continue
        c = ln.split('\t')
        if not c[1].startswith('class/') or c[4] == '':
            continue
        repl.setdefault(c[1][6:], {})[int(c[2])] = unesc(c[4])
    n = 0
    for rel, rp in repl.items():
        data = open(os.path.join(src, rel), 'rb').read()
        nb = patch_bytes(data, rp)
        q = os.path.join(dst, rel); os.makedirs(os.path.dirname(q) or dst, exist_ok=True)
        open(q, 'wb').write(nb); n += len(rp)
    print('đã vá %d chuỗi trong %d class' % (n, len(repl)))


if __name__ == '__main__':
    if sys.argv[1] == 'extract':
        extract(sys.argv[2], sys.argv[3], sys.argv[4])
    else:
        patch(sys.argv[2], sys.argv[3], sys.argv[4])
