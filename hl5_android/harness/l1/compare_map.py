#!/usr/bin/env python3
"""So check_map.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_map.py OUTDIR > check_map.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('toggle', 'star', 'cursor', 'close', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o: ok = (n['live'], n['top'], n['cls']) == (o['live'], o['top'], o['cls'])   # ảnh cuối không so (hai bản khác giao diện)
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:400], json.dumps(o)[:400]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    for hf, key, spec in (('hits_mz', 'ban_do_khu_vuc', [('close', '1'), ('minimap', '48'), ('toggle', '49')]), ('hits_mb', 'ban_do_lon', [('close', '1'), ('toggle', '49')])):
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag))); ref = {x['id']: x['rect'] for x in HR['%s_%s' % (key, tag)]}
        if not h: print(tag, hf, 'THIẾU'); bad += 1; continue
        errs = [(nm, h.get(hid) and h[hid][:4], ref[nm]) for nm, hid in spec if not h.get(hid) or list(h[hid][:4]) != ref[nm]]
        print('%s vùng chạm %s: %s' % (tag, key, 'ĐẠT (%d vùng == hit_rects.json)' % len(spec) if not errs else 'LỆCH %s' % errs)); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
