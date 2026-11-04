#!/usr/bin/env python3
"""T14: sinh hud_art.h từ ui_new/0*.png: mỗi ảnh thu về SxS bằng LANCZOS (premultiplied alpha), lưu RGB565 nhân alpha (u16) + alpha (u8).
Dùng: gen_hud_art.py OUT.h   (ui_new = ../../ui_new)"""
import os, sys
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); UI = os.path.join(H, '..', '..', 'ui_new')
items = [('EMB', '01_emblem.png', 80), ('GLOW', '02_emblem_glow.png', 80), ('FRAME', '03_frame_empty.png', 64), ('PAUSE', '04_pause.png', 64),
         ('SAVE', '05_save.png', 64), ('SHOP', '06_shop.png', 64), ('SWAP', '07_swap.png', 64)]
out = ['/* sinh bởi gen_hud_art.py (không sửa tay): ảnh ui_new thu nhỏ, RGB565 đã nhân alpha + alpha */']
for n, f, s in items:
    im = Image.open(os.path.join(UI, f)).convert('RGBA')
    # nhân alpha trước khi thu nhỏ (tránh viền sáng)
    px = im.load(); w, h = im.size
    pm = Image.new('RGBA', (w, h))
    pp = pm.load()
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]; pp[x, y] = (r * a // 255, g * a // 255, b * a // 255, a)
    im = pm.resize((s, s), Image.LANCZOS); px = im.load()
    cs, al = [], []
    for y in range(s):
        for x in range(s):
            r, g, b, a = px[x, y]
            cs.append(((r >> 3) << 11) | ((g >> 2) << 5) | (b >> 3)); al.append(a)
    out.append('#define ART_%s_S %d' % (n, s))
    out.append('static const u16 ART_%s_C[] = {%s};' % (n, ','.join(map(str, cs))))
    out.append('static const u8 ART_%s_A[] = {%s};' % (n, ','.join(map(str, al))))
open(sys.argv[1], 'w').write('\n'.join(out) + '\n')
