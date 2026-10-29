#!/usr/bin/env python3
"""Ve lai anh co chu -> res_vi[_<ban>]/ (de len jar).  Dung: make_img.py X_DIR OUT_DIR MENU_DATA_X_DIR
Chu ve bang pixeltext (font bo tool NAM_P), vien den 1px 8 huong (quy uoc NAMP), giu kich thuoc/trong suot."""
import sys, os, struct
from PIL import Image, ImageEnhance
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'tools', 'pixeltext_kit'))
from pixeltext.paint import draw, patch, render

X, OUT = sys.argv[1], sys.argv[2]
ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..')


def load(rel):
    return Image.open(os.path.join(X, rel)).convert('RGBA')


def save(im, rel):
    p = os.path.join(OUT, rel); os.makedirs(os.path.dirname(p), exist_ok=True)
    im.save(p, optimize=True); print('  ', rel, im.size)


def modules(rel):
    d = open(os.path.join(X, rel), 'rb').read(); p = 0
    s2, n2 = struct.unpack_from('>hh', d, 0); p = 4
    a = struct.unpack_from('>%dh' % (s2 * n2), d, p)
    return [a[i * n2:(i + 1) * n2] for i in range(s2)]


def clear(im, box):
    x0, y0, w, h = box; px = im.load()
    for y in range(y0, y0 + h):
        for x in range(x0, x0 + w):
            px[x, y] = (0, 0, 0, 0)


# 1) menu/2.png: 9 dong chu Han (module 0-8 cua menu.data), dong tieng Anh giu nguyen
MENU = ['Chơi tiếp', 'Trò chơi mới', 'Cài đặt', 'Trợ giúp', 'Giới thiệu', 'Thoát game', 'Cộng đồng QQ', 'Bảng điểm', 'Thêm game']
FILL = ['#ffffff,#9fb0d8'] * 6 + ['#ffd040,#ff7a10'] * 2 + ['#ffe060,#ff2010']
im = load('menu/2.png'); mods = modules('data/menu.data')
for i in range(9):
    m = mods[i]; box = (m[1], m[2], m[3], m[4])
    clear(im, box)
    draw(im, box, MENU[i], size=18, fill=FILL[i].split(','), outline='#000000', outline_diag=True)
save(im, 'menu/2.png')

# 2) menu/1.png: logo tieu de
im = load('menu/1.png'); W, H = im.size
new = Image.new('RGBA', (W, H), (0, 0, 0, 0))
draw(new, (0, 4, W, 44), 'TINH VÂN', size=18, scale=2, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
draw(new, (0, 52, W, 44), 'TRUYỀN THUYẾT', size=18, scale=2, fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)
save(new, 'menu/1.png')

# 3) bang ten thanh pho map/122-126
for n, t in zip(range(122, 127), ['New Zion', 'Ngũ Quốc', 'Hailar', 'Astana', 'Belt']):
    im = load('map/%d.png' % n)
    patch(im, (2, 2, im.width - 4, im.height - 4), t, size='15c', outline='#000000', outline_diag=True)
    save(im, 'map/%d.png' % n)

# 4) head/play.png: "▶播放" -> giu tam giac, chu "Phát"
im = load('head/play.png')
patch(im, (8, 0, im.width - 8, im.height), 'Phát', size=12)
save(im, 'head/play.png')

# 5) logo/exit.png: nen man thoat co logo nha phat hanh -> logo NAMP lam toi
lg = Image.open(os.path.join(ROOT, '..', 'logo0.png')).convert('RGBA')
ex = load('logo/exit.png'); W, H = ex.size
bg = Image.new('RGBA', (W, H), (0, 0, 0, 255))
lg = ImageEnhance.Brightness(lg).enhance(0.35)
bg.alpha_composite(lg, ((W - lg.width) // 2, (H - lg.height) // 2))
save(bg.convert('RGB').convert('RGBA'), 'logo/exit.png')
