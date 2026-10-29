# -*- coding: utf-8 -*-
"""Đổi tên MIDlet trong manifest sang ASCII (an toàn cho máy thật) rồi sinh lại .jad (Jar-Size khớp).

  python3 -I mhtq/scripts/set_manifest.py --in-jar A.jar --out-jar B.jar [--name "Mong Hoi Tam Quoc"]
Sửa MIDlet-Name, MIDlet-Description (chữ Hán -> ASCII), MIDlet-1 (tên hiển thị; giữ icon + class).
"""
import argparse
import os
import sys
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import jar_tools as J        # noqa: E402


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--in-jar', required=True)
    ap.add_argument('--out-jar', required=True)
    ap.add_argument('--name', default='Mong Hoi Tam Quoc')
    a = ap.parse_args(argv)
    z = zipfile.ZipFile(a.in_jar)
    txt = z.read('META-INF/MANIFEST.MF').decode('utf-8').replace('\r\n', '\n')
    out = []
    for ln in txt.split('\n'):
        if ln.startswith('MIDlet-Name:'):
            ln = 'MIDlet-Name: ' + a.name
        elif ln.startswith('MIDlet-Description:'):
            ln = 'MIDlet-Description: Role playing,mostar,' + a.name
        elif ln.startswith('MIDlet-1:'):
            parts = ln[len('MIDlet-1:'):].strip().split(',')
            ln = 'MIDlet-1: %s,%s' % (a.name, ','.join(parts[1:]))
        out.append(ln)
    man = '\r\n'.join(out).encode('utf-8')
    assert all(ord(c) < 128 for c in man.decode('utf-8')), 'manifest còn ký tự ngoài ASCII'
    J.overlay(a.in_jar, a.out_jar, {'META-INF/MANIFEST.MF': man})
    J.make_jad(a.out_jar, os.path.splitext(a.out_jar)[0] + '.jad')
    print('manifest ASCII, jar %s (%d byte)' % (a.out_jar, os.path.getsize(a.out_jar)))


if __name__ == '__main__':
    main()
