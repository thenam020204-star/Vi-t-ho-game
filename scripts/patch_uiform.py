# -*- coding: utf-8 -*-
"""Sua bo cuc uiform.bin (w.a/w.b, w.java:89-115): moi phan tu 18 short, [9..12] = x,y,w,h, [14] = che do
(-1 ve thuong, 0 chu chay cb.a(bn), 1 cuon doc). Form 19 = bang chon ai (y.java:446-490):
  ten ai (phan tu 8..12): x 86, w 97 -> w 84, che do 0 (chu chay khi dai hon o, vh.m)
  trang thai (13..17):    x 149, w 29 -> x 170, w 28 (chu ngan Mo/Khoa/Xong/Qua)
  python3 -I patch_uiform.py <uiform.bin goc> <out>"""
import sys, struct
d = bytearray(open(sys.argv[1], 'rb').read()); p = 0
def s2(): 
    global p; v = struct.unpack('>h', d[p:p + 2])[0]; p += 2; return v
n = s2()
for _ in range(n):
    k = s2(); p += 2 * k; L = struct.unpack('>H', d[p:p + 2])[0]; p += 2 + L
nf = s2(); pos = {}
for f in range(nf):
    m = s2()
    for e in range(m):
        pos[(f, e)] = p; p += 36
assert p == len(d)
def put(f, e, idx, v):
    q = pos[(f, e)] + 2 * idx; d[q:q + 2] = struct.pack('>h', v)
def get(f, e, idx):
    q = pos[(f, e)] + 2 * idx; return struct.unpack('>h', d[q:q + 2])[0]
for e in range(8, 13):
    assert (get(19, e, 9), get(19, e, 11), get(19, e, 14)) == (86, 97, -1)
    put(19, e, 11, 84); put(19, e, 14, 0)
for e in range(13, 18):
    assert (get(19, e, 9), get(19, e, 11)) == (149, 29)
    put(19, e, 9, 170); put(19, e, 11, 28)
open(sys.argv[2], 'wb').write(bytes(d)); print('uiform: form 19 da sua 10 phan tu')
