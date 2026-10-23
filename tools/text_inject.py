#!/usr/bin/env python3
"""Ghep ban dich vao 6 file eng_Dictionary_*.txt.
Dung: python3 -I tools/text_inject.py [--out DIR] [--identity] [--vi-dir DIR] [--verify]
  doc text/vi/*.tsv (cot key, vi); thieu ban dich -> giu EN; chi doi phan `core`, moi byte khac giu nguyen (BOM, CRLF, thu tu cot);
  o co dau `,` sau dich ma chua boc -> boc "..." (D5).
  --identity : vi = en  (kiem tra round-trip, so sanh byte voi text/eng)
  --verify   : so sanh byte output voi text/eng (chi hop le khi vi = en / khong co ban dich)
Mac dinh --out = work/build/text"""
import sys, os, argparse
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from text_common import *

def inject_group(g, tr):
    lines, header = iter_cells(g)
    out = [lines[0]]
    used = set(); changed = 0
    for i in range(1, len(lines) - 1):
        cells = SPLIT_RE.split(lines[i])
        new = list(cells)
        for j in range(len(cells)):
            if not cell_translatable(i, j, cells, header): continue
            qpre, qsuf, parts = parse_cell(cells[j])
            for p in parts:
                if p[0] == 'txt' and p[4] and p[2] in tr: used.add(p[2])
            nc = build_cell(qpre, qsuf, parts, tr)
            if nc != cells[j]: changed += 1
            new[j] = nc
        out.append(','.join(new))
    out.append('')
    return out, used, changed

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--out', default=os.path.join(ROOT, 'work', 'build', 'text'))
    ap.add_argument('--vi-dir', default=VI_DIR)
    ap.add_argument('--identity', action='store_true')
    ap.add_argument('--verify', action='store_true')
    a = ap.parse_args()
    if a.identity:
        _, segs = load_segments()
        tr = {s['key']: s['key'] for s in segs}; warn = []
    else:
        tr, warn = load_vi(a.vi_dir)
    for w in warn: print('canh bao:', w)
    for k, v in tr.items():
        bad = [c for c in v if c in '"\\\t\r\n']
        if bad: sys.exit('LOI: ban dich co ky tu cam %r: %r' % (bad[0], k[:60]))
    os.makedirs(a.out, exist_ok=True)
    allused = set(); bad = 0
    for g in GROUPS:
        lines, used, changed = inject_group(g, tr)
        data = write_bytes(lines)
        open(os.path.join(a.out, fname(g)), 'wb').write(data)
        allused |= used
        msg = '%-9s %d o doi' % (g, changed)
        if a.verify or a.identity:
            same = data == open(os.path.join(ENG_DIR, fname(g)), 'rb').read()
            msg += '  byte-giong goc: %s' % ('OK' if same else 'KHAC'); bad += (not same)
        print(msg)
    print('ban dich dung: %d/%d key' % (len(allused), len(tr)))
    unused = [k for k in tr if k not in allused]
    if unused: print('canh bao: %d key khong khop o nao (vd %r)' % (len(unused), unused[0][:50]))
    sys.exit(1 if bad else 0)

main()
