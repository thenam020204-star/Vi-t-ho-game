#!/usr/bin/env python3
"""So check_gpop.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_gpop.py OUTDIR"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('yes', 'no', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o: ok = (n['live'], n['top'], n['cls']) == (o['live'], o['top'], o['cls']) and n['img'] == o['img']
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:300], json.dumps(o)[:300]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    for hf, key, spec in (('hits_gp1', 'game_hoi_sinh', [('popup_yes', '240'), ('popup_no', '241'), ('popup_modal', '254')]),
                          ('hits_gp4', 'game_hoi_sinh_xong', [('popup_ok', '242'), ('popup_modal', '254')])):
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag))); ref = {x['id']: x['rect'] for x in HR['%s_%s' % (key, tag)]}
        if not h: print(tag, hf, 'THIẾU'); bad += 1; continue
        errs = [(nm, h.get(hid) and h[hid][:4], ref[nm]) for nm, hid in spec if not h.get(hid) or list(h[hid][:4]) != ref[nm]]
        print('%s vùng chạm %s: %s' % (tag, key, 'ĐẠT (%d vùng == hit_rects.json)' % len(spec) if not errs else 'LỆCH %s' % errs)); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
