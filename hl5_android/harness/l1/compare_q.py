#!/usr/bin/env python3
"""So kết quả check_q.py (new vs orig) + vùng chạm vs hit_rects.json. Dùng: compare_q.py OUTDIR > check_q.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    for sc in ('tab', 'list', 'map', 'drag', 'tree', 'cycles'):
        n, o = ld(os.path.join(d, 'sc_%s_%s_new.json' % (sc, tag))), ld(os.path.join(d, 'sc_%s_%s_orig.json' % (sc, tag)))
        ok = n is not None and n == o
        if sc == 'cycles' and n and o: ok = n['live'] == o['live'] and n['top'] <= o['top'] and n['cls'] == o['cls'] and n.get('mimg') == o.get('mimg')   # so ảnh có mặt nạ 2 vùng hộp thoại T16 (img = hash toàn ảnh, chỉ tham khảo)   # gốc tự rò khi mở chi tiết (LoadNpcBody); mới phải không rò hơn gốc
        print('%s %-7s %s' % (tag, sc, 'ĐẠT' if ok else 'LỆCH new=%s orig=%s' % (json.dumps(n)[:400], json.dumps(o)[:400]))); bad += not ok
        if sc == 'cycles' and n: print('   cycles: new', n, 'orig', o)
    for hf, key, spec in (('hits_q', 'nhiem_vu', [('subtab', 0x30, 2), ('quest', 0x20, 5), ('desc_scroll', 0xE0), ('detail', 0x3D)]),
                          ('hits_qt', 'nhiem_vu_nhanh', [('subtab', 0x30, 2), ('node', 0x40, 4)])):
        h = ld(os.path.join(OUT, '%s_%s.json' % (hf, tag))); ref = {x['id']: x['rect'] for x in HR['%s_%s' % (key, tag)]}
        if not h: print(tag, 'THIẾU', hf); bad += 1; continue
        errs = []; cnt = 0
        for sp in spec:
            ids = [('%s%d' % (sp[0], i), str(sp[1] + i)) for i in range(sp[2])] if len(sp) == 3 else [(sp[0], str(sp[1]))]
            for nm, hid in ids:
                rr = ref.get(nm); gg = h.get(hid); cnt += 1
                if rr is None: continue
                if not gg or list(gg[:4]) != rr: errs.append((nm, gg and gg[:4], rr))
        print('%s vùng chạm %s: %s' % (tag, key, 'ĐẠT (%d vùng == hit_rects.json)' % cnt if not errs else 'LỆCH %s' % errs[:6])); bad += bool(errs)
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
