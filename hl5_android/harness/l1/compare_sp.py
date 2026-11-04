#!/usr/bin/env python3
"""So kết quả check_sp.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_sp.py OUTDIR > check_sp.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('tab', 'equip', 'skill', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o: ok = (n['live'], n['top'], n['cls']) == (o['live'], o['top'], o['cls']) and n.get('mimg') == o.get('mimg')   # so ảnh có mặt nạ 2 vùng hộp thoại T16 (img = hash toàn ảnh, chỉ tham khảo)
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:300], json.dumps(o)[:300]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    for hf, key, spec in (('hits_sp', 'tinh_linh', [('close', '1'), ('tab', 0x10, 9), ('subtab', 0x30, 2), ('slot', 0x34, 2), ('desc_scroll', None), ('detail', 0x3D)]),
                          ('hits_sp1', 'tinh_linh_ky_nang', [('close', '1'), ('subtab', 0x30, 2), ('spskill', 0x38, 4), ('use', 0x3C), ('desc_scroll', str(0xE1)), ('detail', 0x3D)])):
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag))); ref = {x['id']: x['rect'] for x in HR['%s_%s' % (key, tag)]}
        if not h: continue
        errs = []; cnt = 0
        for sp in spec:
            if sp[0] == 'close': ids = [('close', '1')]
            elif len(sp) == 3: ids = [('%s%d' % (sp[0], i), str(sp[1] + i)) for i in range(sp[2])]
            elif sp[1] is None: continue
            else: ids = [(sp[0], str(sp[1]))]
            for nm, hid in ids:
                r = ref.get(nm); g = h.get(hid); cnt += 1
                if r is None: continue
                if not g or list(g[:4]) != r: errs.append((nm, g and g[:4], r))
        print('%s vùng chạm %s: %s' % (tag, key, 'ĐẠT (%d vùng == hit_rects.json)' % cnt if not errs else 'LỆCH %s' % errs[:6])); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
