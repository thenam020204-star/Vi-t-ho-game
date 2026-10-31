#!/usr/bin/env python3
"""Anh xem truoc font lon: f18 x1,5 (cach TEST7: q/16, nearest) so voi f24vn (1:1), moi loai co ban
nearest va ban bilinear x1,6875 (mo phong GL_LINEAR + GPU phong 1080/640 tren may that 2400x1080).

    python3 preview_f24.py <f18vn.bin> <f24vn.bin> <out.png> [--glyphs <glyphs.png>]

Luu y: day la mo phong Python (khong phai Java VNFont). Phan bo cuc/ngat dong don gian (mot dong, chia dong theo chieu rong).
"""
import os
import sys

sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import f18io
import numpy as np
from PIL import Image, ImageDraw, ImageFont

GPU = 1.6875
SAMPLES = [
    "Ầ Ẫ Ẩ Ấ Ậ Ể Ỗ Ữ Ự Ỹ Đ ầ ẫ ẩ ấ ậ ể ỗ ữ ự ỹ đ — Thiên đường ở phía trước!",
    "Em tin... Thật ra, người có thể giúp ta ngay lúc này. Hãy đi tìm pháp sư Grumman ở phía đông khu rừng.",
    "à á ả ã ạ ă ắ ằ ẳ ẵ ặ â ấ ầ ẩ ẫ ậ è é ẻ ẽ ẹ ê ế ề ể ễ ệ ì í ỉ ĩ ị",
    "ò ó ỏ õ ọ ô ố ồ ổ ỗ ộ ơ ớ ờ ở ỡ ợ ù ú ủ ũ ụ ư ứ ừ ử ữ ự ỳ ý ỷ ỹ ỵ",
    "À Á Ả Ã Ạ Ă Ắ Ằ Ẳ Ẵ Ặ Â Ấ Ầ Ẩ Ẫ Ậ È É Ẻ Ẽ Ẹ Ê Ế Ề Ể Ễ Ệ Ì Í Ỉ Ĩ Ị",
    "Ò Ó Ỏ Õ Ọ Ô Ố Ồ Ổ Ỗ Ộ Ơ Ớ Ờ Ở Ỡ Ợ Ù Ú Ủ Ũ Ụ Ư Ứ Ừ Ử Ữ Ự Ỳ Ý Ỷ Ỹ Ỵ",
]


class Font:
    def __init__(self, path):
        self.cs, self.h, gl = f18io.load(path)
        self.gl = gl
        self.w = {c: len(gl[c][0]) for c in self.cs}
        self.q = self.gl.get('?')

    def row(self, c):
        return self.gl.get(c) or self.gl['?']


def sc(v, q, d=16):
    return (v * q) // d


def render_clean(font, text, q, den, width):
    """Ve nhieu dong (ngat tai dau cach) nhu VNFont: x cong don theo px nguon roi sc() (nearest)."""
    H = font.h
    lh = sc(H + 2, q, den)
    adv = lambda c: len(font.row(c)[0])
    lines, cur = [], ''
    for wd in text.split(' '):
        t = (cur + ' ' + wd) if cur else wd
        w = sc(sum(adv(c) for c in t), q, den) + 1
        if w > width and cur:
            lines.append(cur)
            cur = wd
        else:
            cur = t
    lines.append(cur)
    buf = np.zeros((lh * len(lines), width), np.uint8)
    for li, ln in enumerate(lines):
        y = li * lh + (lh - sc(H, q, den)) // 2
        sx = 0
        for c in ln:
            g = font.row(c)
            gw = len(g[0])
            for r in range(H):
                y0, y1 = y + sc(r, q, den), y + sc(r + 1, q, den)
                for col in range(gw):
                    if g[r][col] == '#':
                        x0, x1 = sc(sx + col, q, den), sc(sx + col + 1, q, den)
                        buf[max(0, y0):max(0, y1), max(0, x0):min(width, x1)] = 255
            sx += gw
    return buf


def gl_bilinear(buf, k=GPU):
    h, w = buf.shape
    im = Image.fromarray(buf)
    return np.array(im.resize((int(w * k + 0.5), int(h * k + 0.5)), Image.BILINEAR))


def gl_nearest(buf, k=GPU):
    h, w = buf.shape
    im = Image.fromarray(buf)
    return np.array(im.resize((int(w * k + 0.5), int(h * k + 0.5)), Image.NEAREST))


def to_rgb(alpha, fg=(255, 255, 255), bg=(24, 28, 40)):
    a = alpha.astype(np.float32)[..., None] / 255.0
    out = np.array(fg, np.float32) * a + np.array(bg, np.float32) * (1 - a)
    return out.astype(np.uint8)


def stack(blocks, gap=6, bg=(24, 28, 40)):
    w = max(b.shape[1] for b in blocks)
    h = sum(b.shape[0] for b in blocks) + gap * (len(blocks) - 1)
    out = np.zeros((h, w, 3), np.uint8)
    out[:] = bg
    y = 0
    for b in blocks:
        out[y:y + b.shape[0], :b.shape[1]] = b
        y += b.shape[0] + gap
    return out


def label(text, w, hgt=18, fg=(240, 200, 80)):
    im = Image.new('RGB', (w, hgt), (46, 50, 64))
    ImageDraw.Draw(im).text((6, 3), text, fill=fg, font=ImageFont.load_default())
    return np.array(im)


def panel(font, q, den, width_buf, samples):
    bufs = [render_clean(font, s, q, den, width_buf) for s in samples]
    big = np.vstack([np.vstack([b, np.zeros((6, width_buf), np.uint8)]) for b in bufs])
    return big


def main(a):
    f18 = Font(a[0])
    f24 = Font(a[1])
    WB = 640                                  # be rong hop thoai o day (logic ~565..660)
    p18 = panel(f18, 24, 16, WB, SAMPLES)    # TEST7: q = 24 => x1,5
    p24 = panel(f24, 24, 24, WB, SAMPLES)    # f24vn 1:1
    cols = []
    for title, p in (("f18 x1,5 (TEST7, q/16)", p18), ("f24vn (size 24, 1:1)", p24)):
        n = to_rgb(gl_nearest(p))
        b = to_rgb(gl_bilinear(p))
        cols.append((title, n, b))
    w = max(c[1].shape[1] for c in cols)
    blocks = []
    for title, n, b in cols:
        blocks.append(label(title + " - NEAREST x1,6875 (khong loc)", w))
        blocks.append(n)
        blocks.append(label(title + " - BILINEAR x1,6875 (GL_LINEAR, nhu may that)", w))
        blocks.append(b)
    img = stack(blocks)
    Image.fromarray(img).save(a[2])
    print('anh:', a[2], img.shape[1], 'x', img.shape[0])
    if '--glyphs' in a:
        out = a[a.index('--glyphs') + 1]
        sheet(f24, out)


def sheet(font, out, Z=4, cols=24):
    chars = font.cs
    cw = (13) * Z
    chh = (font.h + 2) * Z
    rows = (len(chars) + cols - 1) // cols
    im = Image.new('RGB', (cols * cw, rows * chh), (20, 22, 30))
    px = im.load()
    for i, c in enumerate(chars):
        gx, gy = (i % cols) * cw, (i // cols) * chh
        g = font.gl[c]
        for y in range(font.h):
            for x in range(len(g[0])):
                if g[y][x] == '#':
                    for dy in range(Z - 1):
                        for dx in range(Z - 1):
                            px[gx + x * Z + dx, gy + y * Z + dy] = (255, 255, 255)
    im.save(out)
    print('glyphs:', out, im.size)


if __name__ == '__main__':
    main(sys.argv[1:])
