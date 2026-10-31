#!/usr/bin/env python3
"""Go quyen mang khoi AndroidManifest.xml (apktool, dang text) -> HE DIEU HANH chan moi ket noi mang cua app.

Giu ACCESS_NETWORK_STATE / ACCESS_WIFI_STATE: code goi ConnectivityManager/WifiManager de doc trang thai,
thieu quyen se nem SecurityException (sap). Doc trang thai khong mo ket noi nao.
"""
import re, sys

REMOVE = [
    'android.permission.INTERNET',
    'com.android.vending.BILLING',
    'com.google.android.c2dm.permission.RECEIVE',
    'com.google.android.finsky.permission.BIND_GET_INSTALL_REFERRER_SERVICE',
]

p = sys.argv[1]
s = open(p, encoding='utf-8').read()
for perm in REMOVE:
    pat = re.compile(r'\s*<uses-permission[^>]*android:name="%s"[^>]*/>' % re.escape(perm))
    n = len(pat.findall(s))
    if n != 1:
        raise SystemExit('quyen %s: khop %d lan (can 1)' % (perm, n))
    s = pat.sub('', s)
    print('go quyen', perm)
open(p, 'w', encoding='utf-8').write(s)
