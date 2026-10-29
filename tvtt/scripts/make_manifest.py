#!/usr/bin/env python3
"""Manifest ban Viet hoa: giu cac khoa ky thuat cua ban goc, thay ten/nha cung cap/mo ta (goc co chu Han,
quang cao wap), bo MIDlet-Delete-Confirm (quang cao). Code game khong goi getAppProperty (da kiem).
Dung: make_manifest.py GOC_MANIFEST OUT_MANIFEST"""
import sys
src = open(sys.argv[1], 'rb').read().decode('utf-8').replace('\r\n', '\n')
keys = []; cur = None
for l in src.split('\n'):
    if l.startswith(' ') and cur is not None:
        keys[-1][1] += l[1:]; continue
    if ':' in l:
        k, v = l.split(':', 1); keys.append([k.strip(), v.strip()]); cur = k
NEW = {'MIDlet-1': 'Tinh Van Truyen Thuyet,/icon.png,src.GameMidlet', 'MIDlet-Name': 'Tinh Van Truyen Thuyet',
       'MIDlet-Vendor': 'NAMP', 'MIDlet-Description': 'Tinh Van Truyen Thuyet - Viet hoa boi NAMP'}
DROP = {'MIDlet-Delete-Confirm', 'Ant-Version', 'Created-By'}
out = []
for k, v in keys:
    if k in DROP: continue
    out.append('%s: %s' % (k, NEW.get(k, v)))
for k in NEW:
    if k not in [x[0] for x in keys]: out.append('%s: %s' % (k, NEW[k]))
open(sys.argv[2], 'wb').write(('\r\n'.join(out) + '\r\n').encode('ascii'))
