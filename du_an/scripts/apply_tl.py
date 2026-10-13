"""Ghep ban dich cuoi: dp merge + overrides.tsv + chuan hoa dong KHONG DICH (dau cau Han -> ASCII).
  python3 apply_tl.py <merged.tsv> <overrides.tsv> <charset.txt> <ra.tsv>"""
import sys, csv, re
mer, ovr, cs, out = sys.argv[1:5]
charset = set(open(cs, encoding='utf-8').read())
PUNCT = {'…': '...', '？': '?', '！': '!', '，': ', ', '。': '.', '：': ':', '；': ';', '（': '(', '）': ')',
         '“': '"', '”': '"', '、': ', ', '～': '~', '—': '-', '２': '2', '【': '[', '】': ']'}
rows = list(csv.reader(open(mer, encoding='utf-8', newline=''), delimiter='\t', quoting=csv.QUOTE_NONE))
h = rows[0]; ci, cg, cd = h.index('id'), h.index('goc'), h.index('dich')
ov = {}
for r in list(csv.reader(open(ovr, encoding='utf-8', newline=''), delimiter='\t', quoting=csv.QUOTE_NONE))[1:]:
    ov[r[0]] = r[1]
bad = []
for r in rows[1:]:
    while len(r) <= cd: r.append('')
    if r[ci] in ov:
        r[cd] = ov[r[ci]]
    elif (r[cd] == '' or r[cd] == r[cg]) and r[cg] != '':
        s = re.sub('…+', '…', r[cg])
        for k, v in PUNCT.items(): s = s.replace(k, v)
        r[cd] = s
    miss = sorted(set(c for c in r[cd] if c not in charset))
    if miss: bad.append((r[ci], ''.join(miss), r[cd][:50]))
with open(out, 'w', encoding='utf-8', newline='') as f:
    for r in rows:
        assert not any('\t' in x or '\n' in x for x in r), r[0]
        f.write('\t'.join(r) + '\n')
print('dong:', len(rows) - 1, '| ghi de:', len(ov), '| thieu glyph:', len(bad))
for b in bad[:30]: print('  THIEU', b)
