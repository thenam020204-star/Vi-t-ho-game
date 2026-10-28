#!/usr/bin/env python3
"""T9-ảnh: dựng bìa/logo ngang 320x240 từ ảnh dọc 240x320 (không kéo giãn méo).
Thu về cao 240 giữ tỉ lệ (180x240), đặt giữa; hai bên 70px lấp bằng phản chiếu mép ảnh rồi làm mờ,
mép ghép làm mềm. Ra res_vi_320/Image/menu.png và namp.png (P 256 màu như ảnh gốc)."""
import os
from PIL import Image, ImageFilter, ImageEnhance

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
OUT = os.path.join(ROOT, 'res_vi_320/Image')
W, H = 320, 240

def landscape(src, darken=0.92, blur=7, feather=10, edge=False):
    im = src.convert('RGB')
    sw = round(im.width * H / im.height)            # 180
    core = im.resize((sw, H), Image.LANCZOS)
    x0 = (W - sw) // 2                              # 70
    canvas = Image.new('RGB', (W, H))
    canvas.paste(core, (x0, 0))
    # phản chiếu mép trái/phải
    if edge:   # nền gần đồng nhất: kéo dài mép (trung bình 4 cột ngoài cùng)
        left = core.crop((0, 0, 4, H)).resize((1, H), Image.BOX).resize((x0, H), Image.NEAREST)
        right = core.crop((sw - 4, 0, sw, H)).resize((1, H), Image.BOX).resize((W - x0 - sw, H), Image.NEAREST)
    else:
        left = core.crop((0, 0, x0, H)).transpose(Image.FLIP_LEFT_RIGHT)
        right = core.crop((sw - (W - x0 - sw), 0, sw, H)).transpose(Image.FLIP_LEFT_RIGHT)
    canvas.paste(left, (0, 0)); canvas.paste(right, (x0 + sw, 0))
    bg = canvas.filter(ImageFilter.GaussianBlur(blur))
    bg = ImageEnhance.Brightness(bg).enhance(darken)
    # lõi sắc nét + mép mờ dần
    mask = Image.new('L', (W, H), 0)
    px = mask.load()
    for x in range(W):
        if x < x0 or x >= x0 + sw:
            v = 0
        else:
            d = min(x - x0, x0 + sw - 1 - x)
            v = 255 if d >= feather else int(255 * d / feather)
        for y in range(H):
            px[x, y] = v
    out = Image.composite(core_on(canvas, core, x0), bg, mask)
    return out

def core_on(canvas, core, x0):
    c = Image.new('RGB', (W, H)); c.paste(core, (x0, 0)); return c

def save_p(im, path):
    os.makedirs(os.path.dirname(path), exist_ok=True)
    im.quantize(256, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.FLOYDSTEINBERG).save(path)

def main():
    menu = Image.open(os.path.join(ROOT, 'work/x/Image/menu.png'))
    save_p(landscape(menu), os.path.join(OUT, 'menu.png'))
    namp = Image.open(os.path.join(ROOT, 'goc/logo_namp_240x320.png'))
    save_p(landscape(namp, darken=1.0, blur=10, feather=14, edge=True), os.path.join(OUT, 'namp.png'))
    print('ok', W, H)

if __name__ == '__main__':
    main()
