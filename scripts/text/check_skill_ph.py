# -*- coding: utf-8 -*-
"""Kiem placeholder mo ta ky nang: bl.i[*][1] (gameData.bin, bl.e) duoc by.java thay CHU HOA DAU TIEN
cua tung ky tu trong M N K Y D X E Z B L G P S bang so (by.java:16, 231-279). Ban dich phai co dung
tap chu hoa do theo cung thu tu nhu goc, khong them chu hoa nao khac trong tap.
  python3 -I check_skill_ph.py <gameData.bin goc> <text_da_dich.tsv>"""
import sys, os, csv
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from binutf import R
SET = "MNKYDXEZBLGPS"
d = open(sys.argv[1], 'rb').read(); r = R(d)
c = r.s1(); [r.s4() for _ in range(c + 1)]
r.s1(); n = r.s1(); [r.utf() for _ in range(n)]
n = r.s1(); [r.utf() for _ in range(n)]
n = r.s1(); [r.utf() for _ in range(n)]
n = r.s1()
for _ in range(n): r.utf(); [r.s2() for _ in range(4)]
n = r.s1() & 0xFF; offs = []
for _ in range(n):
    r.utf(); offs.append(r.p); r.utf(); r.utf(); [r.s2() for _ in range(12)]
    k = r.s1(); [r.s2() for _ in range(k)]
    k = r.s1() + 1; [r.s2() for _ in range(k - 1)]
    k = r.s1(); [r.s2() for _ in range(2 * k)]
rows = {r_['key']: r_ for r_ in csv.DictReader(open(sys.argv[2], encoding='utf-8'), delimiter='\t',
        quoting=csv.QUOTE_NONE, escapechar='\\') if r_['file'] == 'gameData.bin'}
bad = 0
for o in offs:
    row = rows.get(str(o))
    if not row or not row['dich']: continue
    a = [ch for ch in row['goc'] if ch in SET]; b = [ch for ch in row['dich'] if ch in SET]
    if a != b:
        bad += 1; print('LECH %d: %s => %s' % (o, row['goc'], row['dich']))
print('%d mo ta ky nang, %d lech' % (len(offs), bad)); sys.exit(1 if bad else 0)
