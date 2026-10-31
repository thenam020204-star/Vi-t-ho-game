#!/usr/bin/env python3
"""Kiem tra APK dau ra: dex VNFont, .so luu khong nen + can trang 4096, manifest, text."""
import sys, zipfile, struct
apk = sys.argv[1]
bad = 0
def fail(m):
    global bad; bad += 1; print('FAIL', m)
z = zipfile.ZipFile(apk)
names = z.namelist()
for need in ['classes.dex', 'classes2.dex', 'classes3.dex', 'lib/arm64-v8a/libgame.so',
             'lib/arm64-v8a/libHercules.so', 'assets/common/game_res/memorytext_en.dat.jpg']:
    if need not in names: fail('thieu ' + need)
raw = open(apk, 'rb').read()
for i in z.infolist():
    if i.filename.endswith('.so'):
        if i.compress_type != zipfile.ZIP_STORED: fail(i.filename + ' bi nen (extractNativeLibs=false can STORED)')
        n, e = struct.unpack('<HH', raw[i.header_offset + 26:i.header_offset + 30])
        data = i.header_offset + 30 + n + e
        if data % 4096: fail('%s khong can trang 4096 (offset %d)' % (i.filename, data))
        else: print('ok  %s STORED, can 4096' % i.filename)
man = z.read('AndroidManifest.xml')
for perm in ['android.permission.INTERNET', 'android.permission.ACCESS_NETWORK_STATE', 'com.android.vending.BILLING']:
    present = perm.encode('utf-16-le') in man or perm.encode() in man
    print('%-5s quyen %s' % ('CO' if present else 'KHONG', perm))
if b'r\x00e\x00q\x00u\x00i\x00r\x00e\x00d\x00S\x00p\x00l\x00i\x00t\x00' in man: fail('manifest con requiredSplitTypes')
print('META-INF:', [n for n in names if n.startswith('META-INF/') and n.split('/')[-1].split('.')[-1] in ('RSA', 'SF', 'EC')])
print('RESULT:', 'PASS' if bad == 0 else 'FAIL (%d)' % bad)
sys.exit(1 if bad else 0)
