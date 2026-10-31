#!/usr/bin/env python3
"""Them file dex vao APK (chua ky) duoi ten cho truoc; tu choi neu ten da ton tai."""
import sys, zipfile
apk, dex, name = sys.argv[1:4]
with zipfile.ZipFile(apk) as z:
    if name in z.namelist():
        raise SystemExit('%s da co trong apk' % name)
with zipfile.ZipFile(apk, 'a', compression=zipfile.ZIP_DEFLATED) as z:
    z.write(dex, name)
print('them %s (%s)' % (name, dex))
