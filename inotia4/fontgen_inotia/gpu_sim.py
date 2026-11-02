#!/usr/bin/env python3
"""Mo phong "GPU phong 1080/640 = 1,6875" cho anh dem chu that luu boi `-Demu.dumpfont=DIR` (emu/src/Inotia4Emu.java).

    python3 gpu_sim.py <out.png> <nhan1>=<dump1.png> [<nhan2>=<dump2.png> ...] [--zoom 1.6875]

Moi dump -> hai khoi: NEAREST (khong loc) va BILINEAR (GL_LINEAR: SGL_Texture::Create dat MIN/MAG = 0x2601),
xep chong len nhau. Dem chu that: chu = 0xFFFFFFFF, nen = 0 (anh dump: chu trang tren nen xam dam).
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from PIL import Image
from preview_f24 import gl_bilinear, gl_nearest, label, stack, to_rgb


def load_alpha(path):
    im = np.array(Image.open(path).convert('RGB'))
    return np.where((im == 255).all(axis=2), 255, 0).astype(np.uint8)


def main(a):
    zoom = 1.6875
    if '--zoom' in a:
        i = a.index('--zoom')
        zoom = float(a[i + 1])
        del a[i:i + 2]
    out = a[0]
    items = [x.split('=', 1) for x in a[1:]]
    blocks = []
    w = 0
    for name, path in items:
        al = load_alpha(path)
        n = to_rgb(gl_nearest(al, zoom))
        b = to_rgb(gl_bilinear(al, zoom))
        w = max(w, n.shape[1])
        blocks += [(name + ' - NEAREST x%g (khong loc)' % zoom, n), (name + ' - BILINEAR x%g (GL_LINEAR, nhu may that)' % zoom, b)]
    img = stack([x for t, p in blocks for x in (label(t, w), p)])
    Image.fromarray(img).save(out)
    print('anh:', out, img.shape[1], 'x', img.shape[0])


if __name__ == '__main__':
    main(sys.argv[1:])
