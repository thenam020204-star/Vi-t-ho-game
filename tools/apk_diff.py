#!/usr/bin/env python3
"""So 2 APK theo entry (ten, method, size, CRC). Dung: python3 -I tools/apk_diff.py GOC MOI [--allow ten1,ten2]
In cac entry them/mat/doi; entry META-INF/* va stamp-cert-sha256 (chu ky) tach rieng. Exit 1 neu co entry doi ngoai --allow + chu ky."""
import sys, zipfile, argparse
ap = argparse.ArgumentParser(); ap.add_argument('a'); ap.add_argument('b'); ap.add_argument('--allow', default='assets/bin/Data/data.unity3d')
x = ap.parse_args()
allow = set(x.allow.split(',')) if x.allow else set()
A = {i.filename: i for i in zipfile.ZipFile(x.a).infolist()}; B = {i.filename: i for i in zipfile.ZipFile(x.b).infolist()}
sig = lambda n: n.startswith('META-INF/') or n == 'stamp-cert-sha256'
key = lambda i: (i.compress_type, i.file_size, i.CRC)
added = sorted(n for n in B if n not in A); gone = sorted(n for n in A if n not in B)
chg = sorted(n for n in A if n in B and key(A[n]) != key(B[n]))
print('entry goc %d, moi %d' % (len(A), len(B)))
for n in chg: print('  DOI  %s  (method %d->%d, %d -> %d B)' % (n, A[n].compress_type, B[n].compress_type, A[n].file_size, B[n].file_size))
for n in added: print('  THEM', n)
for n in gone: print('  MAT ', n)
bad = [n for n in chg if n not in allow] + [n for n in added + gone if not sig(n)]
print('KET QUA:', 'OK (chi %s + chu ky)' % ','.join(sorted(allow)) if not bad else 'LOI entry ngoai du kien: %s' % bad[:8])
sys.exit(1 if bad else 0)
