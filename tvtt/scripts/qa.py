#!/usr/bin/env python3
"""Kiem ban dich trong dich/dp_work (work.tsv goc <-> out.tsv dich): so dau ：, $, |, ky tu ngoai f12.
  qa.py            in loi
  qa.py --fixcolon thay ':' ASCII -> '：' (tu trai) cho du so ： nhu ban goc (khoi phuc ma tach ten, khong phai dich)"""
import sys, os, re
D = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'dich', 'dp_work')
cs = set(open(os.path.join(D, '..', 'charset_f12.txt'), encoding='utf-8').read())


def load(p):
    r = {}
    for l in open(p, encoding='utf-8').read().split('\n'):
        if '\t' in l:
            k, v = l.split('\t', 1); r[k] = v
    return r


w = load(os.path.join(D, 'work.tsv')); o = load(os.path.join(D, 'out.tsv'))
fix = '--fixcolon' in sys.argv; nfix = 0; err = 0
for k, d in o.items():
    g = w[k]
    if fix and g.count('：') > d.count('：'):
        need = g.count('：') - d.count('：')
        while need and ':' in d:
            d = d.replace(':', '：', 1); need -= 1; nfix += 1
        o[k] = d
    e = []
    if g.count('：') != d.count('：'): e.append('so ： %d/%d' % (g.count('：'), d.count('：')))
    if g.count('$') != d.count('$') or re.findall(r'\$\d', g) != re.findall(r'\$\d', d): e.append('$ lech')
    if g.count('|') != d.count('|'): e.append('| lech')
    bad = sorted({c for c in d if c not in cs})
    if bad: e.append('ngoai f12: ' + ''.join(bad))
    if e:
        err += 1; print(k, '; '.join(e), '|', d[:60])
if fix:
    with open(os.path.join(D, 'out.tsv'), 'w', encoding='utf-8') as f:
        for k in sorted(o, key=int):
            f.write(k + '\t' + o[k] + '\n')
    print('fixcolon:', nfix)
print('QA:', 'OK' if not err else '%d loi' % err, '(%d dong)' % len(o))
