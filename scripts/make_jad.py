#!/usr/bin/env python3
"""Sinh JAD tu manifest cua jar, MIDlet-Jar-Size/URL khop jar."""
import sys, os, zipfile
jar, jad = sys.argv[1], sys.argv[2]
m = zipfile.ZipFile(jar).read('META-INF/MANIFEST.MF').decode('latin-1').replace('\r\n', '\n')
lines = [l for l in m.split('\n') if l.strip() and not l.startswith(('Manifest-Version', 'Created-By'))]
lines += ['MIDlet-Jar-URL: %s' % os.path.basename(jar), 'MIDlet-Jar-Size: %d' % os.path.getsize(jar)]
open(jad, 'w', encoding='utf-8', newline='\r\n').write('\n'.join(lines) + '\n')
print('JAD', jad, os.path.getsize(jar))
