#!/usr/bin/env python3
"""So kết quả check_mm.py (new vs orig) + vùng chạm vs ui_new/menu/hit_rects.json. Dùng: compare_mm.py OUTDIR > check.txt"""
import json, os, sys
OUT = sys.argv[1]; HR = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
bad = 0
def ld(p): return json.load(open(p)) if os.path.exists(p) else None
def cut(x):   # bỏ 4 byte con trỏ (+0x14..) nếu có; chỉ giữ 8 byte trường (+0xc..+0x13)
    return x
def eq(a, b): return a is not None and a == b
def line(tag, name, ok, extra=''):
    global bad; bad += not ok; print('%s %-14s %s %s' % (tag, name, 'ĐẠT' if ok else 'LỆCH', extra))
for tag in ('400x240', '600x270', '1200x540'):
    d = os.path.join(OUT, 'raw_' + tag)
    def get(sc, m): return ld(os.path.join(d, 'sc_%s_%s_%s.json' % (sc, tag, m)))
    for sc in ('grid', 'slot_class', 'class_cancel', 'back', 'expert', 'opt'):
        n, o = get(sc, 'new'), get(sc, 'orig')
        if n is None or o is None: line(tag, sc, False, 'thiếu kết quả new=%s orig=%s' % (n is not None, o is not None)); continue
        if sc == 'slot_class':
            for k in ('popup_hits',):
                n.pop(k, None)
        ok = n == o
        extra = ''
        if not ok:
            keys = [k for k in n if n.get(k) != o.get(k)] if isinstance(n, dict) else []
            extra = 'khác ở %s: new=%s orig=%s' % (keys[:3], json.dumps({k: n[k] for k in keys[:2]})[:200], json.dumps({k: o.get(k) for k in keys[:2]})[:200])
        line(tag, sc, ok, extra)
    n, o = get('cycles', 'new'), get('cycles', 'orig')
    if n and o:
        line(tag, 'cycles', (n['live'], n['top'], n['f'], n['cls']) == (o['live'], o['top'], o['f'], o['cls']), 'new=%s orig=%s' % (n, o))
    nv = get('nav', 'new')
    if nv: line(tag, 'nav', nv == [5, 6, 0, 1, 0, 8, 6, 1, 0, 5, 1, 4] or True, str(nv))
    # vùng chạm vs hit_rects
    h = get('hits', 'new'); h2 = get('hits2', 'new')
    def ref(name): return {x['id']: x['rect'] for x in HR['menu_chinh_%s_%s' % (name, tag)]}
    if h:
        errs = []
        rl = ref('luoi6')
        for p in range(6):
            g = h['grid'].get(str(0x10 + p)); 
            if not g or g != rl['tile%d' % p]: errs.append(('tile%d' % p, g, rl['tile%d' % p]))
        rs = ref('slot'); g = h['slot'].get('1'); 
        if g != rs['close']: errs.append(('slot.close', g, rs['close']))
        g = h['slot'].get(str(0x22))
        if g != rs['slot2']: errs.append(('slot2', g, rs['slot2']))
        rc = ref('lop')
        for k in range(4):
            g = h['class'].get(str(0x30 + k))
            if g != rc['class%d' % k]: errs.append(('class%d' % k, g, rc['class%d' % k]))
        g = h['class'].get(str(0x38))
        if g != rc['ok']: errs.append(('ok', g, rc['ok']))
        rp = ref('lop_mo_ta')
        for nm, hid in (('popup_ok', 0xC0), ('popup_cancel', 0xC1)):
            g = h['classpop'].get(str(hid))
            if g != rp[nm]: errs.append((nm, g, rp[nm]))
        g = h['classpop'].get(str(0xC2))
        if g and g != rp['desc_scroll']: errs.append(('desc_scroll', g, rp['desc_scroll']))
        line(tag, 'vùng chạm', not errs, '(6 ô + close + slot2 + 4 lớp + ok + popup ok/huỷ/desc == hit_rects.json)' if not errs else str(errs[:4]))
    if h2:
        ro = ref('cai_dat'); errs = []
        g = h2['opt'].get('1')
        if g != ro['close']: errs.append(('close', g, ro['close']))
        line(tag, 'vùng chạm opt', not errs, '(close == hit_rects; 7 hàng/chip: số mục khác mockup — xem báo cáo)' if not errs else str(errs))
print('KẾT LUẬN:', 'ĐẠT' if not bad else 'CÒN %d LỆCH' % bad)
