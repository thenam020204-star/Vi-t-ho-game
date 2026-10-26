# -*- coding: utf-8 -*-
"""Dat access_flags cua <clinit> trong 1 file .class (ghi de) cho giong ban goc.
Ban goc (obfuscator) de <clinit> = 0x0009 (PUBLIC|STATIC); ecj sinh 0x0008. JVM bo qua co nay,
chi sua de jar_check so descriptor/flag khop hoan toan.   python3 -I set_clinit_flags.py X.class 0x0009
"""
import struct, sys

def main(path, flags):
    b = bytearray(open(path, 'rb').read())
    p = 8; n = struct.unpack('>H', b[p:p + 2])[0]; p += 2
    utf = {}; i = 1
    while i < n:
        t = b[p]
        if t == 1:
            ln = struct.unpack('>H', b[p + 1:p + 3])[0]; utf[i] = bytes(b[p + 3:p + 3 + ln]); p += 3 + ln
        elif t in (3, 4, 9, 10, 11, 12): p += 5
        elif t in (5, 6): p += 9; i += 1
        elif t in (7, 8, 16): p += 3
        elif t == 15: p += 4
        else: raise ValueError('tag %d' % t)
        i += 1
    p += 6
    ic = struct.unpack('>H', b[p:p + 2])[0]; p += 2 + 2 * ic
    def skip_members(p):
        cnt = struct.unpack('>H', b[p:p + 2])[0]; p += 2; out = []
        for _ in range(cnt):
            out.append(p)
            ac = struct.unpack('>H', b[p + 6:p + 8])[0]; p += 8
            for _ in range(ac):
                p += 6 + struct.unpack('>I', b[p + 2:p + 6])[0]
        return p, out
    p, _ = skip_members(p)
    p, meths = skip_members(p)
    hit = 0
    for m in meths:
        if utf[struct.unpack('>H', b[m + 2:m + 4])[0]] == b'<clinit>':
            b[m:m + 2] = struct.pack('>H', flags); hit += 1
    assert hit == 1, hit
    open(path, 'wb').write(b)
    print('%s: <clinit> flags = 0x%04x' % (path, flags))

if __name__ == '__main__':
    main(sys.argv[1], int(sys.argv[2], 0))
