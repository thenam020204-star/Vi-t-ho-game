#!/usr/bin/env python3
"""T14: sinh hud_art.h từ ui_new/0*.png: mỗi ảnh thu về SxS bằng LANCZOS (premultiplied alpha), lưu RGB565 nhân alpha (u16) + alpha (u8).
Dùng: gen_hud_art.py OUT.h   (ui_new = ../../ui_new)"""
import os, sys
from PIL import Image
H = os.path.dirname(os.path.abspath(__file__)); UI = os.path.join(H, '..', '..', 'ui_new')
# T14c: nhúng ở cỡ LỚN NHẤT cần dùng (D tối đa ở H=1080: f16=64; D = DD*64/16 theo hud.c DD[]), RGBA 8 bit nhân alpha (4 B/điểm).
# Trong game chỉ thu nhỏ (lọc vùng) khi dựng bộ đệm; không phóng to. Nguồn nhỏ hơn cỡ cần (03 và 04: 116 px) được LANCZOS phóng 1 lần ở đây (giới hạn bởi nguồn).
items = [('EMB', '01_emblem.png', 264), ('GLOW', '02_emblem_glow.png', 264), ('FRAME', '03_frame_empty.png', 160), ('PAUSE', '04_pause.png', 128),
         ('SAVE', '05_save.png', 112), ('SHOP', '06_shop.png', 112), ('SWAP', '07_swap.png', 136)]
out = ['/* sinh bởi gen_hud_art.py (không sửa tay): ảnh ui_new, RGBA8 nhân alpha, cỡ SxS = D tối đa ở 1080p */']
for n, f, s in items:
    im = Image.open(os.path.join(UI, f)).convert('RGBA')
    px = im.load(); w, h = im.size
    pm = Image.new('RGBA', (w, h)); pp = pm.load()
    for y in range(h):
        for x in range(w):
            r, g, b, a = px[x, y]; pp[x, y] = (r * a // 255, g * a // 255, b * a // 255, a)
    if (w, h) != (s, s): pm = pm.resize((s, s), Image.LANCZOS)
    sys.stderr.write('%s: nguon %dx%d -> nhung %d%s\n' % (n, w, h, s, ' (PHONG TO tu nguon nho)' if w < s else ''))
    d = pm.tobytes()
    out.append('#define ART_%s_S %d' % (n, s))
    out.append('static const u8 ART_%s_P[] = {%s};' % (n, ','.join(map(str, d))))
open(sys.argv[1], 'w').write('\n'.join(out) + '\n')
