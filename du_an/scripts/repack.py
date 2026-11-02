"""Dong goi string.pak tu TSV da dich.  python3 repack.py <goc.pak> <dich.tsv> <ra.pak>
Cot 'dich' rong -> giu nguyen goc. Kiem: so trunk, so chuoi, do dai byte < 10240 (s_trunkBuffer1),
tong (2*count) < 10240 (s_trunkBuffer0), so chuoi trunk 0 khong doi (s_commonStringCount)."""
import sys, csv
sys.path.insert(0, 'scripts')
import pak
src, tsv, out = sys.argv[1:4]
d = open(src, 'rb').read()
tr, tail = pak.read_pak(d)
tl = {}
with open(tsv, encoding='utf-8', newline='') as f:
    r = csv.reader(f, delimiter='\t', quoting=csv.QUOTE_NONE)
    hdr = next(r)
    ci, cd = hdr.index('id'), hdr.index('dich')
    for row in r:
        if len(row) > cd and row[cd] != '':
            tl[row[ci]] = row[cd]
new = []
used = 0
for t, data in enumerate(tr):
    strs, rest = pak.read_strings(data)
    o = []
    for i, s in enumerate(strs):
        k = 't%d_%d' % (t, i)
        if k in tl:
            o.append(tl[k]); used += 1
        else:
            o.append(s)
    assert len(o) * 2 < 10240
    for x in o:
        assert len(x.encode('utf-8')) < 10240, k
    new.append(pak.write_strings(o, rest))
res = pak.write_pak(new, tail)
open(out, 'wb').write(res)
print('repack: %d chuoi da thay, %d byte (goc %d)' % (used, len(res), len(d)))
