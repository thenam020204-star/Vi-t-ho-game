#!/usr/bin/env python3
"""Ghi lai APK chua ky: .so va resources.arsc luu STORED; .so can 16384 (hop ca may trang 4K/16K), con lai can 4.
    python3 fix_zip.py in.apk out.apk"""
import sys, zipfile
src, dst = sys.argv[1:3]
zin = zipfile.ZipFile(src)
with open(dst, 'wb') as f:
    zout = zipfile.ZipFile(f, 'w')
    for i in zin.infolist():
        data = zin.read(i.filename)
        store = i.filename.endswith('.so') or i.filename == 'resources.arsc' or i.compress_type == zipfile.ZIP_STORED
        zi = zipfile.ZipInfo(i.filename, date_time=i.date_time)
        zi.external_attr = i.external_attr
        zi.compress_type = zipfile.ZIP_STORED if store else zipfile.ZIP_DEFLATED
        if store:
            align = 16384 if i.filename.endswith('.so') else 4
            hdr = f.tell() + 30 + len(i.filename.encode())
            pad = (-hdr) % align
            if 0 < pad < 4:
                pad += align
            zi.extra = (b'\xd9\xd9' + (pad - 4).to_bytes(2, 'little') + b'\x00' * (pad - 4)) if pad else b''
        zout.writestr(zi, data)
    zout.close()
print('fix_zip: %s -> %s' % (src, dst))
