#!/usr/bin/env python3 -I
"""Giai ma / ma hoa .xng (PNG cat + XOR w/h + byte rac). Xem logs/text_path.md (e).
Dung:  xng_codec.py roundtrip DIR [--log F]   |  decode IN.xng OUT.png  |  encode IN.png OUT.xng --like ORIG.xng
"""
import sys, struct, glob, os
A = bytes([137,80,78,71,13,10,26,10,0,0,0,13,73,72,68,82])
B = bytes([0,0,0,0,73,69,78,68,174,66,96,130])
C = bytes([0xbe,0xcd,0xb2,0xbb,0xb8,0xe6,0xcb,0xdf,0xc4,0xe3,0xc4,0xf3,0xb9,0xfe,0xb9,0xfe])

def decode(d):
    """-> (png_bytes, meta) ; meta = dict(flag,k1,k2,n3)"""
    flag = d[0] == 1
    i = 1; out = bytearray(A); n4 = 16; n3 = -1; meta = {'flag': int(flag), 'k1': None, 'k2': None, 'n3': None}
    while i < len(d):
        b = d[i]; i += 1
        if n4 in (16, 21):
            out += bytes(d[i+k] ^ C[b+k] for k in range(4)); i += 4
            meta['k1' if n4 == 16 else 'k2'] = b; n4 += 5; continue
        if not flag:
            if n4 == 35:
                n3 = (b << 8) + d[i]; i += 1; n4 += 2; meta['n3'] = n3; continue
            if n3 != -1 and n4 == n3:
                n4 += 1; meta['junk'] = b; continue
        out.append(b); n4 += 1
    out += B
    return bytes(out), meta

def encode(png, meta):
    assert png[:16] == A and png[-12:] == B, 'PNG khong dung dang (IHDR dau/IEND cuoi)'
    flag = meta['flag']; k1, k2 = meta['k1'], meta['k2']
    out = bytearray([flag])
    out.append(k1); out += bytes(png[16+k] ^ C[k1+k] for k in range(4))
    out.append(k2); out += bytes(png[20+k] ^ C[k2+k] for k in range(4))
    body = png[24:-12]           # tu byte 24 (sau height) toi truoc IEND ; vi tri png = 24 + j
    n3 = meta.get('n3')
    if flag:
        out += body
    else:
        # n4 tai png-pos p (p>=35) = p+2 ; 9 byte dau (24..32?) -> kiem: n4==35 <=> png-pos 33... xem duoi
        # sau height n4=26 ; moi byte png tang 1 => png-pos p ung n4=p+2 (p>=24); n3 doc khi n4==35 => p=33
        p0 = 33
        out += body[:p0-24]
        out += bytes([n3 >> 8, n3 & 255])
        rest = body[p0-24:]
        jpos = n3 - 4 - p0          # n4==n3 <=> p = n3-4
        if 0 <= jpos <= len(rest):
            out += rest[:jpos] + bytes([n3 & 255]) + rest[jpos:]
        else:
            out += rest
    return bytes(out)

def roundtrip(dirname, log=None):
    ok = bad = 0; lines = []
    for f in sorted(glob.glob(os.path.join(dirname, '*.xng'))):
        d = open(f, 'rb').read()
        png, meta = decode(d)
        e = encode(png, meta)
        r = e == d
        ok += r; bad += (not r)
        lines.append('%s %s flag=%d k=%s,%s n3=%s' % ('OK ' if r else 'BAD', os.path.basename(f), meta['flag'], meta['k1'], meta['k2'], meta['n3']))
    lines.append('TONG: %d/%d khop tung byte' % (ok, ok+bad))
    txt = '\n'.join(lines)
    if log: open(log, 'w').write(txt + '\n')
    print(lines[-1]); return bad == 0

if __name__ == '__main__':
    a = sys.argv[1:]
    if a[0] == 'roundtrip':
        log = a[a.index('--log')+1] if '--log' in a else None
        sys.exit(0 if roundtrip(a[1], log) else 1)
    if a[0] == 'decode':
        png, m = decode(open(a[1], 'rb').read()); open(a[2], 'wb').write(png); print(m)
    if a[0] == 'encode':
        _, m = decode(open(a[a.index('--like')+1], 'rb').read())
        open(a[2], 'wb').write(encode(open(a[1], 'rb').read(), m))
