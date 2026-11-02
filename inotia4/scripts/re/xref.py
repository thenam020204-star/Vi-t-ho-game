#!/usr/bin/env python3
"""Tra cuu caller tu file llvm-objdump -d (ARM64).  xref.py <dis> <ham> [ham...]"""
import re, sys
dis = sys.argv[1]
targets = set(sys.argv[2:])
fn = None
callers = {t: set() for t in targets}
rx = re.compile(r'\s(?:bl|b|b\.\w+)\s+0x[0-9a-f]+ <([^>@+]+)(?:@plt)?>')
for line in open(dis):
    if line[:1] != ' ' and line.rstrip().endswith('>:'):
        fn = line.split('<', 1)[1][:-3]
        continue
    m = rx.search(line)
    if m and m.group(1) in targets and fn != m.group(1):
        callers[m.group(1)].add(fn)
for t in sys.argv[2:]:
    print(t, '<-', ', '.join(sorted(callers[t])) or '(khong co)')
