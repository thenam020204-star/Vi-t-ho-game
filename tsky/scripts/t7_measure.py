#!/usr/bin/env python3
"""T7: do do rong px (font f12) cua chuoi dich. Dung: t7_measure.py <tsv> [loc]"""
import struct, sys, csv
def load(path='work/tools/fonts/f12.bin'):
    b = open(path, 'rb').read()
    n = struct.unpack('>H', b[:2])[0]
    cs = b[2:2+n].decode('utf-8')
    h = b[2+n]
    w = b[3+n:3+n+len(cs)]
    return {c: w[i] for i, c in enumerate(cs)}, h
W, H = load()
def sw(s):
    return sum(W.get(c, 12 if ord(c) >= 0x2E80 else W.get(' ', 4)) for c in s)
if __name__ == '__main__':
    rows = list(csv.reader(open(sys.argv[1], encoding='utf-8'), delimiter='\t', quoting=csv.QUOTE_NONE))
    hdr = rows[0]
    for r in rows[1:]:
        f = r[1]
        if len(sys.argv) > 2 and sys.argv[2] not in f: continue
        d = r[4]
        print(r[0], f, r[2], max(sw(x) for x in d.replace('^','\n').split('\n')) if d else 0, d[:60])
