#!/usr/bin/env python3
"""T10c: dung anh MO PHONG HUD man choi (khong phai anh chup man hinh that: T2 chua chay duoc game toi man choi).

    python3 -I scripts/hud_mophong.py <game_res goc> <game_res moi> <anh.png>

Man logic 1422x640 (may 2400x1080), HUD da lui vao 24 px ngang / 16 px doc theo T9 (docs/tam_nhin_hud.md muc 6).
Vi tri lay tu UIPlay_Create (rect cua control) va UIPlayPorting_Draw (draw tai tam rect, GRPX_DrawPart neo 2):
  ID 1,2,3 (luu, gio, banh rang)  rect (25, 155|232|309, 75, 75)         -> UI_015 loc1, loc2, loc0
  ID 7 (doi)                       rect (W-102, H-343, 75, 75)             -> UI_015 loc3
  ID 8..13 (6 o ky nang)           rect (W-27-tx[i], H-14-ty[i], 80, 80), tx = 462,362,262,249,181,87, ty = 89,89,89,179,242,245 (bang trong rodata 0xac5e0/0xac410/0xac1e0, 0xac280/0xac1d0 qua x27 = x29-0x40)
                                    ve tai TAM rect: nen loc5 (94x94) -> vong loc11 (94x94) -> icon 75x75 do 80%
  ID 0x14 (tan cong)               rect (W-186, H-176, 159, 160)           -> UI_014 loc2, lop bam loc3 ve DE len; nen ban nguyet UI_014 loc1 (W-300, H-283)
Cac control con lai chi ve khung gach dut de biet cho (chan dung 78x81 va thanh muc tieu 458x123 dung kich thuoc UIPlay_Create; minimap va can dieu khien:
vi tri that, kich thuoc MINH HOA vi chua doc). Dang ve o 1:1 don vi logic.
Phan 3 cua anh: lop "tan cong bam" ve CONG (kieu 2, goc) va ve ALPHA THUONG (kieu 1, sau nhom hudbtn) voi anh goc / anh moi.
"""
import os
import sys

import numpy as np
from PIL import Image, ImageDraw, ImageFont

W, H = 1422, 640
BG = (96, 102, 96, 255)
LOC15 = {0: (2, 2, 75, 75), 1: (80, 2, 75, 75), 2: (158, 2, 75, 75), 3: (236, 2, 75, 75), 5: (416, 2, 94, 94), 11: (416, 99, 94, 94), 17: (416, 196, 94, 94)}
LOC14 = {1: (2, 128, 276, 267), 2: (281, 128, 159, 160), 3: (281, 291, 159, 160)}
TX = (462, 362, 262, 249, 181, 87)
TY = (89, 89, 89, 179, 242, 245)
ICON_CELLS = [(2, 2), (80, 2), (158, 2), (236, 2), (314, 2), None]            # o 6 de trong: xem o ky nang trong


def font(size):
    for f in ('/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf', '/usr/share/fonts/truetype/liberation/LiberationSans-Regular.ttf'):
        if os.path.exists(f):
            return ImageFont.truetype(f, size)
    return ImageFont.load_default()


def crop(atlas, r):
    x, y, w, h = r
    return atlas.crop((x, y, x + w, y + h))


def add_blend(dst, src, pos):
    """GL (SRC_ALPHA, ONE): rgb += src.rgb * src.a (kieu 2 cua GRPX_DrawPart)."""
    x, y = pos
    d = np.asarray(dst, dtype=np.float64).copy()
    s = np.asarray(src, dtype=np.float64)
    h, w = s.shape[:2]
    d[y:y + h, x:x + w, :3] = np.clip(d[y:y + h, x:x + w, :3] + s[..., :3] * (s[..., 3:4] / 255.0), 0, 255)
    return Image.fromarray(d.astype(np.uint8), 'RGBA')


def dashed(d, r, label, f):
    x, y, w, h = r
    c = (200, 205, 200, 255)
    for i in range(0, w, 10):
        d.line((x + i, y, min(x + i + 5, x + w), y), fill=c)
        d.line((x + i, y + h, min(x + i + 5, x + w), y + h), fill=c)
    for j in range(0, h, 10):
        d.line((x, y + j, x, min(y + j + 5, y + h)), fill=c)
        d.line((x + w, y + j, x + w, min(y + j + 5, y + h)), fill=c)
    d.text((x + 4, y + 3), label, fill=c, font=f)


def hud(a14, a15, icons, pressed=None):
    """Mot khung HUD 1422x640. pressed: None | 'add' | 'alpha' (ve loc3 de len loc2)."""
    im = Image.new('RGBA', (W, H), BG)
    d = ImageDraw.Draw(im)
    f = font(13)
    # control chua thay (khung gach dut)
    for k, y in enumerate((26, 126, 226)):
        dashed(d, (W - 117, y, 78, 81), 'chan dung %d' % (101 + k), f)
    dashed(d, ((W - 458) // 2, 16, 458, 123), 'thanh muc tieu (ID 4)', f)
    dashed(d, (31, 23, 120, 120), 'minimap ID 5/6 (kich thuoc minh hoa)', f)
    dashed(d, (34, H - 268, 229, 228), 'can dieu khien 0x15 (kich thuoc minh hoa)', f)
    # cot trai: luu, gio, banh rang
    for cid, loc, y in ((1, 1, 155), (2, 2, 232), (3, 0, 309)):
        im.alpha_composite(crop(a15, LOC15[loc]), (25, y))
    # nut doi
    im.alpha_composite(crop(a15, LOC15[3]), (W - 102, H - 343))
    # nen ban nguyet + tan cong
    im.alpha_composite(crop(a14, LOC14[1]), (W - 300, H - 283))
    atk = (W - 186, H - 176)
    im.alpha_composite(crop(a14, LOC14[2]), atk)
    if pressed == 'add':
        im = add_blend(im, crop(a14, LOC14[3]), atk)
    elif pressed == 'alpha':
        im.alpha_composite(crop(a14, LOC14[3]), atk)
    # 6 o ky nang: nen -> vong -> icon 80 %
    for i in range(6):
        cx, cy = W - 27 - TX[i] + 40, H - 14 - TY[i] + 40
        im.alpha_composite(crop(a15, LOC15[5]), (cx - 47, cy - 47))
        im.alpha_composite(crop(a15, LOC15[11]), (cx - 47, cy - 47))
        if ICON_CELLS[i]:
            ic = crop(icons, (ICON_CELLS[i][0], ICON_CELLS[i][1], 75, 75))
            a = np.asarray(ic).copy()
            a[..., 3] = (a[..., 3] * 0.8).astype(np.uint8)
            im.alpha_composite(Image.fromarray(a, 'RGBA'), (cx - 37, cy - 37))
    return im


def main(argv):
    if len(argv) != 3:
        print(__doc__)
        return 2
    gdir, mdir, out = argv
    ld = lambda d, n: Image.open(os.path.join(d, n)).convert('RGBA')
    g14, g15 = ld(gdir, 'UI_014.png.jpg'), ld(gdir, 'UI_015.png.jpg')
    m14, m15 = ld(mdir, 'UI_014.png.jpg'), ld(mdir, 'UI_015.png.jpg')
    icons = ld(gdir, 'UI_iconskill75_001.png.jpg')
    F, Fs = font(18), font(14)

    panels = [('MOI (T10): HUD binh thuong, man logic 1422x640 (may 2400x1080), 1 don vi = 1 px', hud(m14, m15, icons)),
              ('GOC (TEST11, de so sanh): cung bo cuc', hud(g14, g15, icons))]
    # phan 3: vung nut tan cong, 5 trang thai
    def corner(a14, a15, mode):
        im = hud(a14, a15, icons, mode)
        return im.crop((W - 330, H - 300, W, H))
    states = [('goc, thuong', corner(g14, g15, None)),
              ('goc, bam (cong, kieu 2)', corner(g14, g15, 'add')),
              ('moi, thuong', corner(m14, m15, None)),
              ('moi, bam, ve CONG (khi KHONG va hudbtn)', corner(m14, m15, 'add')),
              ('moi, bam, ve ALPHA (nhom hudbtn)', corner(m14, m15, 'alpha'))]
    cw, ch = states[0][1].size
    gap = 8
    row_w = len(states) * cw + (len(states) + 1) * gap
    total_w = max(W, row_w)
    total_h = sum(p[1].height + 28 for p in panels) + 28 + ch + 28 + 20
    sheet = Image.new('RGBA', (total_w, total_h), (22, 22, 28, 255))
    d = ImageDraw.Draw(sheet)
    y = 6
    for title, im in panels:
        d.text((8, y), title, fill=(255, 255, 255, 255), font=F)
        sheet.alpha_composite(im, (0, y + 24))
        y += im.height + 28
    d.text((8, y), 'Vung nut tan cong o 5 trang thai (nen ban nguyet UI_014 loc1 + nut + lop bam loc3)', fill=(255, 255, 255, 255), font=F)
    y += 26
    for i, (t, im) in enumerate(states):
        x = gap + i * (cw + gap)
        sheet.alpha_composite(im, (x, y + 18))
        d.text((x, y), t, fill=(255, 255, 0, 255) if t.startswith('moi') else (255, 255, 255, 255), font=Fs)
    sheet.convert('RGB').save(out, optimize=True)
    print('da ghi', out, sheet.size)
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
