# -*- coding: utf-8 -*-
"""Dong goi lai jar: giu thu tu + ten entry cua jar goc, thay/them file tu cac thu muc overlay.

  python3 -I repack.py <goc.jar> <out.jar> <overlay_dir> [overlay_dir ...]
File trong overlay (duong dan tuong doi = ten entry) thay entry cung ten; file moi duoc them cuoi.
"""
import sys, os, zipfile

src, out, overlays = sys.argv[1], sys.argv[2], sys.argv[3:]
rep = {}
for od in overlays:
    for root, _, files in os.walk(od):
        for fn in files:
            p = os.path.join(root, fn)
            rep[os.path.relpath(p, od).replace(os.sep, '/')] = p
zi = zipfile.ZipFile(src)
zo = zipfile.ZipFile(out, 'w', zipfile.ZIP_DEFLATED)
seen = set(); n_rep = 0
for info in zi.infolist():
    name = info.filename; seen.add(name)
    if name in rep:
        data = open(rep[name], 'rb').read(); n_rep += 1
    else:
        data = zi.read(name)
    zo.writestr(zipfile.ZipInfo(name, info.date_time), data,
                zipfile.ZIP_STORED if name.endswith('/') else zipfile.ZIP_DEFLATED)
new = sorted(set(rep) - seen)
for name in new:
    zo.writestr(zipfile.ZipInfo(name, (2012, 6, 21, 0, 0, 0)), open(rep[name], 'rb').read(), zipfile.ZIP_DEFLATED)
zo.close()
print('thay %d, them %d: %s' % (n_rep, len(new), ', '.join(new)))
