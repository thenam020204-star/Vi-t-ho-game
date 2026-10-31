"""Ghi bản dịch (TSV id/src/dst/ctx do dp merge xuất) vào slot tiếng Anh của khối localization.
python3 import_text.py <2f6496cc.bin gốc> <out.bin> <tsv>...   (dst rỗng -> giữ tiếng Anh)"""
import sys, os, csv
sys.path.insert(0, os.path.dirname(__file__)); import locfmt
EN = 1
def unesc(s): return s.replace('\\n', '\n')
L = locfmt.parse(open(sys.argv[1], 'rb').read())
idx = {k: i for i, k in L['keys'].items()}
n = miss = 0
for t in sys.argv[3:]:
    rows = list(csv.reader(open(t, encoding='utf-8'), delimiter='\t', quoting=csv.QUOTE_NONE))
    hd = rows[0]; ci, cs, cd = hd.index('id'), hd.index('src'), hd.index('dst')
    for r in rows[1:]:
        k = r[ci]; rec = L['recs'][idx[k]]
        assert unesc(r[cs]) == rec['S'][EN], k
        if len(r) > cd and r[cd].strip(): rec['S'][EN] = unesc(r[cd]); n += 1
        else: miss += 1
open(sys.argv[2], 'wb').write(locfmt.build(L))
print('đã ghi', n, 'dòng; còn tiếng Anh', miss)
