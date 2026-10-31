#!/usr/bin/env python3
"""Nhung font/f18vn.bin vao java/src/com/namp/vh/FontData.java (String, moi char = 1 byte)."""
import sys
b = open(sys.argv[1], 'rb').read()
s = ''.join('\\%03o' % x for x in b)
open(sys.argv[2], 'w').write(
    'package com.namp.vh;\n\n/** Sinh tu font/f18vn.bin boi scripts/gen_fontdata.py - KHONG sua tay. */\n'
    'final class FontData {\n    private FontData() { }\n    static final String DATA = "%s";\n}\n' % s)
print('FontData: %d byte' % len(b))
