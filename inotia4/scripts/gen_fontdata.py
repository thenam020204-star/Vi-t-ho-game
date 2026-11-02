#!/usr/bin/env python3
"""Nhung font .bin vao ma Java (String, moi char = 1 byte, ma bat phan 8 \\ooo).

    gen_fontdata.py <font.bin> <out.java>                 -> FontData:   static final String DATA (f18vn.bin)
    gen_fontdata.py <font.bin> <out.java> --parts [N]     -> FontData24: static final String[] PARTS, moi phan <= N byte
                                                             (mac dinh 16000: moi hang String UTF8 trong class <= 2N byte < 64 KB)
Ten lop lay tu ten file out (FontData.java -> FontData).
"""
import os
import sys

a = sys.argv[1:]
parts = None
if '--parts' in a:
    i = a.index('--parts')
    if i + 1 < len(a) and a[i + 1].isdigit():
        parts = int(a[i + 1])
        del a[i:i + 2]
    else:
        parts = 16000
        del a[i]
b = open(a[0], 'rb').read()
cls = os.path.splitext(os.path.basename(a[1]))[0]
head = ('package com.namp.vh;\n\n/** Sinh tu %s boi scripts/gen_fontdata.py - KHONG sua tay. */\n'
        'final class %s {\n    private %s() { }\n' % (a[0], cls, cls))
if parts is None:
    body = '    static final String DATA = "%s";\n' % ''.join('\\%03o' % x for x in b)
    n = 1
else:
    chunks = [b[i:i + parts] for i in range(0, len(b), parts)] or [b'']
    n = len(chunks)
    body = '    static final String[] PARTS = {\n' + ''.join(
        '        "%s"%s\n' % (''.join('\\%03o' % x for x in c), ',' if k + 1 < n else '') for k, c in enumerate(chunks)) + '    };\n'
open(a[1], 'w').write(head + body + '}\n')
print('%s: %d byte, %d phan' % (cls, len(b), n))
