"""so vùng chạm NPC (hits_<màn>_<WxH>.json do check_c5 ghi) với ui_new/menu/hit_rects.json. cmp_hits.py DIR(new) WxH"""
import json, sys, os
d, tag = sys.argv[1], sys.argv[2]
J = json.load(open(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'ui_new', 'menu', 'hit_rects.json')))
MAP = {'skill_buy': 'npc_mua_ky_nang', 'item_buy': 'npc_mua_vat_pham', 'sale': 'npc_ban', 'coll_menu': 'npc_bo_suu_tap', 'coll_grid': 'npc_bo_suu_tap_luoi'}
def idname(hid, scr):
    if hid == 1: return 'close'
    if 64 <= hid < 80: return 'cell%d' % (hid - 64)
    if 112 <= hid < 117: return 'subtab%d' % (hid - 112)
    if 120 <= hid < 124: return 'page%d' % (hid - 120)
    if hid == 144: return 'sell' if scr == 'sale' else 'register' if scr == 'coll_grid' else 'buy'
    return None
tot = bad = 0
for scr, key in MAP.items():
    f = os.path.join(d, '%s_%s_%s.json' % ('hitsend' if scr == 'coll_grid' else 'hits', scr, tag))
    if not os.path.exists(f): print('THIEU', scr); continue
    mine = {}
    for k, v in json.load(open(f)).items():
        n = idname(int(k), scr)
        if scr == 'coll_menu' and 64 <= int(k) < 67: n = 'cat%d' % (int(k) - 64)
        if n: mine[n] = v[:4]
    ref = {h['id']: h['rect'] for h in J['%s_%s' % (key, tag)]}
    if scr == 'item_buy':
        for i, h in enumerate([h for h in J['%s_%s' % (key, tag)] if h['id'].startswith('subtab')]): ref['subtab%d' % i] = h['rect']
    for n, r in mine.items():
        tot += 1
        if n not in ref: print(scr, n, 'khong co trong hit_rects', r); bad += 1
        elif ref[n] != r: print(scr, n, 'LECH moi', r, 'mau', ref[n]); bad += 1
    print(scr, 'so vung:', len(mine))
print('TONG', tot, 'LECH', bad)
