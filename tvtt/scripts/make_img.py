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
y2 = 52 if H >= 100 else 38
draw(new, (0, 4 if H >= 100 else 0, W, 37), 'TINH VÂN', size=18, scale=2, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
draw(new, (0, y2, W, 37), 'TRUYỀN THUYẾT', size=18, scale=2, fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)
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
LG = Image.open(os.path.join(ROOT, '..', 'logo0.png')).convert('RGBA')


def fit_logo(W, H, bright):
    k = min(W / LG.width, H / LG.height)
    lg = LG if k >= 1 else LG.resize((int(LG.width * k), int(LG.height * k)), Image.LANCZOS)
    if bright != 1: lg = ImageEnhance.Brightness(lg).enhance(bright)
    bg = Image.new('RGBA', (W, H), (0, 0, 0, 255)); bg.alpha_composite(lg, ((W - lg.width) // 2, (H - lg.height) // 2))
    return bg


ex = load('logo/exit.png'); W, H = ex.size
save(fit_logo(W, H, 0.35), 'logo/exit.png')
# 6) logo khoi dong NAMP (VH.boot) vua man hinh (ban ngang: thu nho giu ti le, khong keo gian)
save(fit_logo(W, H, 1) if (W, H) != LG.size else LG, 'logo/namp.png')


# 7) anh tieu de dinh nen (240: menu/0 vung 27/28 cua menu.data; 320: menu/6,7,8): lam mo chu goc thanh quang sang, ve tieu de Viet
from PIL import ImageFilter


def retitle(im, box, two_lines=True):
    x0, y0, w, h = box
    reg = im.crop((x0, y0, x0 + w, y0 + h))
    bl = ImageEnhance.Brightness(reg.filter(ImageFilter.GaussianBlur(7))).enhance(0.75)
    im.paste(bl, (x0, y0))
    sc = 2 if h >= 70 else 1
    if h >= 140: sc = 3
    lh = 18 * sc
    top = y0 + (h - 2 * lh - 4) // 2
    draw(im, (x0, top, w, lh + 2), 'TINH VÂN', size=18, scale=sc, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
    draw(im, (x0, top + lh + 4, w, lh + 2), 'TRUYỀN THUYẾT', size=18, scale=sc if 89 * sc <= w else max(1, w // 89), fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)


if os.path.exists(os.path.join(X, 'menu', '8.png')):          # ban 320
    im = load('menu/6.png'); retitle(im, (0, 0, im.width, im.height)); save(im, 'menu/6.png')
    for n in (7, 8):          # tieu de tinh ve de len nen menu/0: chi giu chu (nen trong suot)
        W, H = load('menu/%d.png' % n).size
        im = Image.new('RGBA', (W, H), (0, 0, 0, 0))
        draw(im, (0, 2, W, 38), 'TINH VÂN', size=18, scale=2, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
        draw(im, (0, 42, W, 38), 'TRUYỀN THUYẾT', size=18, scale=2 if W >= 182 else 1, fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)
        save(im, 'menu/%d.png' % n)
else:                                                          # ban 240: menu/0 ben phai (module 27, 28)
    im = load('menu/0.png'); mods = modules('data/menu.data')
    for k in (27, 28):
        m = mods[k]
        if m[0] == 0: retitle(im, (m[1], m[2], m[3], m[4]))
    save(im, 'menu/0.png')
