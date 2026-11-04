#!/usr/bin/env python3
"""Mockup T16 v2: chân dung NGUYÊN VẸN (ảnh cắt nền trong suốt của game, không khung vuông, không cắt),
đứng TRƯỚC hộp thoại, chân dung chạm đáy màn, phóng số nguyên s (NEAREST) theo chiều cao khung.
Hai phương án: A = chỉ bóng đổ nhẹ; B = thêm quầng tối mềm sau chân dung (đọc rõ trên nền sáng).
Mỗi phương án vẽ ở 1200x540 (HD) và 600x270 / 400x240 (màn nhỏ), cả người nói bên trái và bên phải.
Dùng: WORK=~/hl5work python3 mock_dialog_v2.py FACE.png OUTDIR"""
import sys, os
from PIL import Image, ImageDraw, ImageFilter
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'menu'))
import viui

HERE = os.path.dirname(os.path.abspath(__file__))
BG = Image.open(os.path.join(HERE, 'ref_dialog_v02hd.jpg')).convert('RGB').crop((260, 30, 1960, 795))   # cảnh sạch (bỏ hộp gốc + chân dung gốc)
OR = (232, 137, 43); FILL = (14, 20, 34, 225); GOLD = (255, 214, 120); WHITE = (240, 240, 240)
TXT = 'Ngươi từng là cánh tay phải của Kefnes, từng là tôi tớ lâu đời nhất của Cổ Thần, sao ta lại có thể tin ngươi được?'


def scene(W, H):
    return BG.resize((W, H), Image.BILINEAR).convert('RGBA')


def panel(d, x, y, w, h, b):
    d.rectangle((x, y, x + w - 1, y + h - 1), fill=FILL)
    for i in range(b): d.rectangle((x + i, y + i, x + w - 1 - i, y + h - 1 - i), outline=OR)


def render(face, W, H, side, variant):
    s = max(1, min(3, (H * 82 // 100) // face.height))          # 270 -> 1, 540 -> 2 (Tir 220 cao)
    px = 18 if H >= 480 else 15
    b = 2 if H >= 480 else 1
    lh = px + (6 if H >= 480 else 3)
    im = scene(W, H)
    fw, fh = face.width * s, face.height * s
    f = face.resize((fw, fh), Image.NEAREST)
    m = 8 * s                                                    # lề màn
    ph = lh * 3 + (24 if H >= 480 else 14)                       # hộp: 3 dòng + đệm
    py = H - ph - m // 2
    over = fw * 30 // 100                                        # hộp lùi sau chân dung 30% bề rộng ảnh
    if side == 0:
        fx = m; px0 = fx + fw - over; pw = W - px0 - m
    else:
        fx = W - m - fw; px0 = m; pw = fx + over - m
    fy = H - fh
    ov = Image.new('RGBA', (W, H), (0, 0, 0, 0)); d = ImageDraw.Draw(ov)
    panel(d, px0, py, pw, ph, b)
    # bảng tên: gắn mép trên hộp, phía chân dung
    name = 'Tir'; nw = viui.sw(px, name) + 24 * b; nh = px + 8 * b
    nx = px0 + over + 6 * b if side == 0 else px0 + pw - over - 6 * b - nw
    ny = py - nh + b
    panel(d, nx, ny, nw, nh, b)
    im = Image.alpha_composite(im, ov)
    # chữ: lề phía chân dung = sau phần hộp bị chân dung che
    tx0 = (px0 + over + 12 * b) if side == 0 else (px0 + 12 * b)
    tw = pw - over - 24 * b
    viui.text(im, nx + 12 * b, ny + 4 * b, name, px, GOLD, shadow=(0, 0, 0))
    for i, ln in enumerate(viui.wrap(px, TXT, tw)[:3]):
        viui.text(im, tx0, py + 10 * b + i * lh, ln, px, WHITE, shadow=(0, 0, 0))
    # mũi tên tiếp
    ax = (px0 + pw - 14 * b) if side == 0 else (px0 + pw - over - 14 * b)
    ay = py + ph - 10 * b
    ImageDraw.Draw(im).polygon([(ax - 5 * b, ay - 5 * b), (ax + 5 * b, ay - 5 * b), (ax, ay + 1 * b)], fill=OR)
    # nút Bỏ qua (góc trên phải)
    sk = 'Bỏ qua ▶▶'.replace('▶▶', '>>'); kw = viui.sw(px, sk) + 20 * b; kh = px + 10 * b
    ov2 = Image.new('RGBA', (W, H), (0, 0, 0, 0)); panel(ImageDraw.Draw(ov2), W - kw - m, m, kw, kh, b)
    im = Image.alpha_composite(im, ov2); viui.text(im, W - kw - m + 10 * b, m + 5 * b, sk, px, WHITE)
    # quầng / bóng sau chân dung
    if side == 1: f = f.transpose(Image.FLIP_LEFT_RIGHT)
    a = f.split()[3]
    sh = Image.new('RGBA', (W, H), (0, 0, 0, 0))
    if variant == 'B':
        g = Image.new('L', (W, H), 0)
        ImageDraw.Draw(g).ellipse((fx - fw // 4, fy + fh // 8, fx + fw + fw // 4, H + fh // 3), fill=150)
        g = g.filter(ImageFilter.GaussianBlur(fw // 6))
        sh = Image.merge('RGBA', (Image.new('L', (W, H), 8),) * 3 + (g,))
    k = Image.new('L', (W, H), 0); k.paste(a.point(lambda v: 120 if v else 0), (fx + 3 * s, fy + 2 * s))
    k = k.filter(ImageFilter.GaussianBlur(3 * s))
    drop = Image.merge('RGBA', (Image.new('L', (W, H), 0),) * 3 + (k,))
    im = Image.alpha_composite(Image.alpha_composite(im, sh), drop)
    im.alpha_composite(f, (fx, fy))
    return im.convert('RGB'), s


def main():
    face = Image.open(sys.argv[1]).convert('RGBA'); out = sys.argv[2]; os.makedirs(out, exist_ok=True)
    for v in 'AB':
        rows = []
        for (W, H) in [(1200, 540), (600, 270), (400, 240)]:
            for side in (0, 1):
                im, s = render(face, W, H, side, v)
                im.save(os.path.join(out, 'v2%s_%dx%d_%s.png' % (v, W, H, 'trai' if side == 0 else 'phai')))
                rows.append(im)
        # tổng hợp: HD trái/phải + nhỏ
        sheet = Image.new('RGB', (1200 * 2 + 20, 540 + 290 + 20), (40, 40, 40))
        sheet.paste(rows[0], (0, 0)); sheet.paste(rows[1], (1220, 0))
        sheet.paste(rows[2], (0, 560)); sheet.paste(rows[3], (620, 560)); sheet.paste(rows[4], (1240, 560)); sheet.paste(rows[5], (1660, 560))
        sheet.save(os.path.join(out, 'tong_hop_v2%s.png' % v))


main()
