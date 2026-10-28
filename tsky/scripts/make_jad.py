#!/usr/bin/env python3
"""Sinh .jad tu manifest cua jar: giu MIDlet-Name/Vendor/Version/..., them MIDlet-Jar-URL va MIDlet-Jar-Size (khop jar).
  python3 make_jad.py game.jar [game.jad]"""
import os, sys, zipfile
jar = sys.argv[1]
jad = sys.argv[2] if len(sys.argv) > 2 else os.path.splitext(jar)[0] + '.jad'
mf = zipfile.ZipFile(jar).read('META-INF/MANIFEST.MF').decode('utf-8')
lines = [l for l in mf.replace('\r\n', '\n').split('\n') if l.strip() and not l.startswith(('Manifest-Version', 'MIDlet-Jar-URL', 'MIDlet-Jar-Size'))]
lines.append('MIDlet-Jar-URL: ' + os.path.basename(jar))
lines.append('MIDlet-Jar-Size: %d' % os.path.getsize(jar))
open(jad, 'w', encoding='utf-8', newline='').write('\r\n'.join(lines) + '\r\n')
print(jad, os.path.getsize(jar))
