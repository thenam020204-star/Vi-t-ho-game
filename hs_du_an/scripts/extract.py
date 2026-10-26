"""Trích text: extract.py <thư mục giải nén> <out.tsv> [--prefix pN:] [--phan N] [--cfg json]
Cột: id, file, offset, goc, dich, ghi_chu, gioi_han  (xuống dòng = \\n, tab = \\t, \\ = \\\\). dich để trống."""
import sys, os, json
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import textfmt

def main():
    a = sys.argv[1:]; opt = {}
    while '--prefix' in a or '--cfg' in a:
        for k in ('--prefix', '--cfg'):
            if k in a: i = a.index(k); opt[k] = a[i + 1]; del a[i:i + 2]
    d, outp = a[0], a[1]
    cfg = json.load(open(opt['--cfg'])) if '--cfg' in opt else None
    pre = opt.get('--prefix', '')
    rows = ['\t'.join(['id', 'file', 'offset', 'goc', 'dich', 'ghi_chu', 'gioi_han'])]
    for rel, kind in textfmt.units(d, cfg):
        b = open(os.path.join(d, rel), 'rb').read()
        for o, t, note in textfmt.strings(b, kind):
            rows.append('\t'.join([pre + '%s@%d' % (rel, o), rel, str(o), textfmt.esc(t), '', textfmt.esc(note), '']))
    open(outp, 'w', encoding='utf-8').write('\n'.join(rows) + '\n')
    print('%d chuỗi, %d file' % (len(rows) - 1, len(set(r.split('\t')[1] for r in rows[1:]))))
main()
