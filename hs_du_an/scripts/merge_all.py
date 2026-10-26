"""Gộp pN/text_can_dich.tsv (N=1..5) -> all/text_can_dich.tsv: thêm cột `phan`, id có tiền tố `pN:`.
Dùng: python3 -I merge_all.py <hs_du_an>"""
import sys, os
P = sys.argv[1]; out = []; hdr = None; seen = {}
for n in range(1, 6):
    rows = open(os.path.join(P, 'p%d' % n, 'text_can_dich.tsv'), encoding='utf-8').read().split('\n')
    hdr = rows[0].split('\t')
    for ln in rows[1:]:
        if not ln: continue
        c = ln.split('\t'); assert len(c) == 7
        c[0] = 'p%d:%s' % (n, c[0]); c.append(str(n)); out.append(c)
os.makedirs(os.path.join(P, 'all'), exist_ok=True)
ids = [c[0] for c in out]; assert len(ids) == len(set(ids))
with open(os.path.join(P, 'all', 'text_can_dich.tsv'), 'w', encoding='utf-8') as f:
    f.write('\t'.join(hdr + ['phan']) + '\n')
    for c in out: f.write('\t'.join(c) + '\n')
uniq = len(set(c[3] for c in out))
print('%d dòng, %d chuỗi gốc duy nhất' % (len(out), uniq))
