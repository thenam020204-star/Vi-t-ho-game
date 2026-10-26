"""Đóng gói lại: repack.py <thư mục gốc> <tsv> <thư mục ra> [--cfg json]
Sao chép toàn bộ thư mục gốc, ghi lại các file text với cột `dich` (rỗng = giữ gốc), cập nhật bảng độ dài .m / offset i.dat."""
import sys, os, json, shutil, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import textfmt

def main():
    a = sys.argv[1:]; cfg = None
    if '--cfg' in a: i = a.index('--cfg'); cfg = json.load(open(a[i + 1])); del a[i:i + 2]
    d, tsv, out = a
    if os.path.exists(out): shutil.rmtree(out)
    shutil.copytree(d, out)
    repl = collections.defaultdict(dict); n = 0
    for ln in open(tsv, encoding='utf-8').read().split('\n')[1:]:
        if not ln: continue
        c = ln.split('\t')
        assert len(c) == 7, ln[:80]
        if c[4] != '':
            repl[c[1]][int(c[2])] = textfmt.unesc(c[4]); n += 1
    for rel, kind in textfmt.units(d, cfg):
        b = open(os.path.join(d, rel), 'rb').read()
        known = set(o for o, _, _ in textfmt.strings(b, kind))
        bad = set(repl.get(rel, {})) - known
        assert not bad, (rel, sorted(bad)[:3])
        nb = textfmt.rebuild(b, kind, repl.get(rel, {}))
        if nb != b: open(os.path.join(out, rel), 'wb').write(nb)
    print('đã áp %d bản dịch' % n)
main()
