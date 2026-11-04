#!/usr/bin/env python3
"""So kết quả check_cash.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_cash.py OUTDIR > check_cash.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p):
    if not os.path.exists(p): return None
    d = json.load(open(p))
    def cut(x):   # bỏ 4 byte con trỏ heap (+0x14..0x17) trong dump trường: khác nhau giữa bản dựng
        if isinstance(x, dict): return {k: ([v[:8]] if k == 'f' else cut(v)) for k, v in x.items()}
        return x
    return cut(d)
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('sel', 'drag', 'popup', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o: ok = (n['live'], n['top'], n['cls']) == (o['live'], o['top'], o['cls']) and n['img'] == o['img']
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:160], json.dumps(o)[:160]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    b = ld(os.path.join(d, 'sc_buy_%s_new.json' % tag))
    if b:
        ok = b['after'] - b['before'] == (b['rec'][9] or 1) and not b['net'] and b['popup3']['cls'] == '0x15e0b0'
        print('%s buy     %s (id %d, trước %d, sau %d, NET=%s)' % (tag, 'ĐẠT' if ok else 'LỖI', b['rec'][8], b['before'], b['after'], b['net'])); bad += not ok
    h = ld(os.path.join(OUT, 'hits_cash_%s.json' % tag)); ref = {x['id']: x['rect'] for x in HR['cash_shop_' + tag]}
    if h:
        errs = []
        for k in range(16):
            r = ref.get('cell%d' % k)
            if r is None: continue
            g = h.get(str(0x10 + k))
            if not g or list(g[:4]) != r: errs.append(('cell%d' % k, g and g[:4], r))
        g = h.get(str(0x42)); r = ref.get('grid_scroll')
        if r and (not g or list(g[:4]) != r): errs.append(('grid_scroll', g and g[:4], r))
        g = h.get('1'); r = ref.get('close')
        if r and (not g or list(g[:4]) != r): errs.append(('close', g and g[:4], r))
        print('%s vùng chạm: %s' % (tag, 'ĐẠT (%d ô + grid_scroll + close == hit_rects.json)' % len([k for k in ref if k.startswith('cell')]) if not errs else 'LỆCH %s' % errs[:4])); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
