#!/usr/bin/env python3
"""docs/module_chu.tsv + anh goc -> font/vhm.dat (bang o chu cua atlas cho VH.mod).
Dung: build_vhm.py X_DIR OUT.dat [TSV...]
Dinh dang (DataInputStream): short n; moi muc: UTF anh, short sx sy w h, byte kieu, short dx, int mau, UTF chu.
Mau = trung binh cac diem sang (>= phan vi 70) cua o goc -> giu tong mau (sang/toi, vang, xanh...)."""
import sys, os, struct
from PIL import Image
X, outp = sys.argv[1], sys.argv[2]
tsvs = sys.argv[3:] or [os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'docs', 'module_chu.tsv')]
rows = []
for t in tsvs:
    for l in open(t, encoding='utf-8'):
        if l.startswith('#') or not l.strip():
            continue
        rows.append(l.rstrip('\n').split('\t'))
cs = set(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'dich', 'charset_f12.txt'), encoding='utf-8').read())


def color(path, sx, sy, w, h):
    im = Image.open(os.path.join(X, path.lstrip('/'))).convert('RGBA').crop((sx, sy, sx + w, sy + h))
    b = im.tobytes(); px = [tuple(b[i:i + 4]) for i in range(0, len(b), 4) if b[i + 3] > 128]
    if not px:
        return 0xFFFFFF
    px.sort(key=lambda p: p[0] * 3 + p[1] * 6 + p[2])
    top = px[len(px) * 6 // 10:]
    r = sum(p[0] for p in top) // len(top); g = sum(p[1] for p in top) // len(top); b = sum(p[2] for p in top) // len(top)
    return (r << 16) | (g << 8) | b


def utf(s):
    e = s.encode('utf-8'); return struct.pack('>H', len(e)) + e


out = bytearray(struct.pack('>h', len(rows)))
for r in rows:
    p, sx, sy, w, h, kind, dx, goc, dich = r[0], *map(int, r[1:7]), r[7], r[8] if len(r) > 8 else ''
    bad = [c for c in dich if c not in cs]
    if bad:
        sys.exit('ky tu ngoai f12 trong "%s": %s' % (dich, ''.join(bad)))
    out += utf(p) + struct.pack('>hhhhbhi', sx, sy, w, h, kind, dx, color(p, sx, sy, w, h)) + utf(dich)
open(outp, 'wb').write(out)
print('vhm: %d o chu' % len(rows))
