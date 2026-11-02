#!/usr/bin/env python3
"""Ghép các ảnh fNNNNN.png trong thư mục thành một ảnh lưới: sheet.py DIR [cột] [mỗi_n]"""
import sys, os, glob, zlib, struct
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from hl5libc import save_png

def rd(p):
    d = open(p, 'rb').read(); i = 8; idat = b''
    while i < len(d):
        n = struct.unpack('>I', d[i:i+4])[0]; t = d[i+4:i+8]
        if t == b'IHDR': w, h = struct.unpack('>II', d[i+8:i+16])
        if t == b'IDAT': idat += d[i+8:i+8+n]
        i += 12 + n
    r = zlib.decompress(idat)
    return w, h, [r[y*(w*3+1)+1:(y+1)*(w*3+1)] for y in range(h)]

D = sys.argv[1]; C = int(sys.argv[2]) if len(sys.argv) > 2 else 5
step = int(sys.argv[3]) if len(sys.argv) > 3 else 1
fs = sorted(glob.glob(os.path.join(D, 'f*.png')))[::step]
ims = [rd(f) for f in fs]; w, h = ims[0][0], ims[0][1]
R = (len(ims) + C - 1) // C
blank = b'\x40' * (w * 3)
rows = []
for r in range(R):
    for y in range(h):
        rows.append(b''.join(ims[r*C+c][2][y] if r*C+c < len(ims) else blank for c in range(C)))
save_png(os.path.join(D, 'sheet.png'), w*C, h*R, b''.join(rows))
print(len(ims), 'frames ->', os.path.join(D, 'sheet.png'))
