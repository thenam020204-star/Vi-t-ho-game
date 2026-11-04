#!/usr/bin/env python3
"""So kết quả check_res.py (new vs orig) + vùng chạm popup_ok vs hit_rects.json (mode 3 = 4 hàng như mockup). Dùng: compare_res.py OUTDIR"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('sub1', 'sub2', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o and 'live' in n and 'live' in o: ok = n['live'] == o['live'] and n['top'] <= o['top'] and n['cls'] == o['cls']
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:600], json.dumps(o)[:600]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    ref = {x['id']: x['rect'] for x in HR['game_chien_thang_' + tag]}
    for hf in ('hits_res2',):    # sub 1 (hộp thắng/thua 1 dòng) thấp hơn mockup 4 hàng nên nút OK nằm cao hơn: chỉ so sub 2 mode 3 (4 hàng = mockup)
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag)))
        if not h: print(tag, 'THIẾU', hf); bad += 1; continue
        gg = h.get(str(0xF2)); rr = ref['popup_ok']
        e = not gg or list(gg[:4]) != rr
        print('%s vùng chạm %s popup_ok: %s' % (tag, hf, 'ĐẠT (== hit_rects.json game_chien_thang)' if not e else 'LỆCH %s vs %s' % (gg and gg[:4], rr))); bad += e
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
