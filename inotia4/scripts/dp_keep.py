#!/usr/bin/env python3
"""Ghep lo cho `dp add`: dong dich tay (stdin, 'k<TAB>dich') + cac k GIU NGUYEN TIENG ANH do nguoi dich chi dinh.

    python3 dp_keep.py 1,3,24-86,88-300 < dich_tay.tsv | python3 dp.py add

Chi chep nguyen van cot goc (work.tsv) cho cac k duoc liet ke (ten class/ky nang/vat pham/ten rieng
theo quyet dinh nguoi dung) - KHONG sinh ban dich nao. Bao loi neu mot k vua co dich tay vua nam trong danh sach giu.
"""
import sys

def ks(spec):
    out = set()
    for part in filter(None, spec.split(',')):
        a, _, b = part.partition('-')
        out.update(range(int(a), int(b or a) + 1))
    return out

keep = ks(sys.argv[1]) if len(sys.argv) > 1 else set()
src = {}
for l in open('dp_work/work.tsv', encoding='utf-8'):
    k, _, t = l.rstrip('\n').partition('\t')
    if k.isdigit():
        src[int(k)] = t
rows = {}
for l in sys.stdin.read().split('\n'):
    if not l.strip():
        continue
    k, _, t = l.partition('\t')
    k = int(k)
    if k in keep:
        sys.exit('k=%d vua dich vua giu' % k)
    rows[k] = t
for k in keep:
    rows[k] = src[k]
for k in sorted(rows):
    print('%d\t%s' % (k, rows[k]))
