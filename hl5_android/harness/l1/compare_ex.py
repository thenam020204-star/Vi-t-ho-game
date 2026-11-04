#!/usr/bin/env python3
"""So kết quả check_ex.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_ex.py OUTDIR > check_ex.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('ready', 'char', 'shop', 'buy', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o and 'live' in n and 'live' in o: ok = n['live'] == o['live'] and n['top'] <= o['top'] and n['cls'] == o['cls']   # ảnh cuối không so (giao diện mới != gốc ở màn Chuyên gia)
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:500], json.dumps(o)[:500]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    for hf, key, spec in (('hits_ex', 'chuyen_gia_chuan_bi', [('start', 0x41), ('char', 0x42), ('shop', 0x43), ('close', 1)]),
                          ('hits_exs', 'chuyen_gia_cua_hang', [('cell%d' % i, 0x10 + i) for i in range(16)] + [('grid_scroll', 0x42), ('detail', 0x40), ('buy', 0x41), ('close', 1)])):
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag))); ref = {x['id']: x['rect'] for x in HR['%s_%s' % (key, tag)]}
        if not h: print(tag, 'THIẾU', hf); bad += 1; continue
        errs = []
        for nm, hid in spec:
            rr = ref.get(nm); gg = h.get(str(hid))
            if rr is None: continue
            if not gg or list(gg[:4]) != rr: errs.append((nm, gg and gg[:4], rr))
        print('%s vùng chạm %s: %s' % (tag, key, 'ĐẠT (%d vùng == hit_rects.json)' % len(spec) if not errs else 'LỆCH %s' % errs[:6])); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
