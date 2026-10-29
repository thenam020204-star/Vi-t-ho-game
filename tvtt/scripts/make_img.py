#!/usr/bin/env python3
"""Ve lai anh co chu -> res_vi[_<ban>]/ (de len jar).  Dung: make_img.py X_DIR OUT_DIR MENU_DATA_X_DIR
Chu ve bang pixeltext (font bo tool NAM_P), vien den 1px 8 huong (quy uoc NAMP), giu kich thuoc/trong suot."""
import sys, os, struct
from PIL import Image, ImageEnhance
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'tools', 'pixeltext_kit'))
from pixeltext.paint import draw, patch, render

X, OUT = sys.argv[1], sys.argv[2]
V = sys.argv[3] if len(sys.argv) > 3 else ('360' if os.path.exists(os.path.join(sys.argv[1], 'map', '130.png')) else '240')
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
im = load('menu/2.png') if V != '360' else None; mods = modules('data/menu.data')
for i in (range(9) if V != '360' else []):
    m = mods[i]; box = (m[1], m[2], m[3], m[4])
    clear(im, box)
    draw(im, box, MENU[i], size=18, fill=FILL[i].split(','), outline='#000000', outline_diag=True)
if im is not None: save(im, 'menu/2.png')

# 1b) ban 360: menu/1.png (module 6-14 dong chu Han 194x38, 26 = "点击屏幕继续"), chu f18 phong 2
if V == '360':
    MENU360 = ['Trò chơi mới', 'Chơi tiếp', 'Cài đặt', 'Trợ giúp', 'Giới thiệu', 'Thoát game', 'Cộng đồng QQ', 'Bảng điểm', 'Thêm game']
    im = load('menu/1.png')
    for i in range(9):
        m = mods[6 + i]; box = (m[1], m[2], m[3], m[4])
        clear(im, box)
        draw(im, box, MENU360[i], size=18, scale=2, fill=FILL[[1, 0, 2, 3, 4, 5, 6, 7, 8][i]].split(','), outline='#000000', outline_diag=True)
    m = mods[26]; box = (m[1], m[2], m[3], m[4]); clear(im, box)
    draw(im, box, 'Chạm màn hình để tiếp tục', size=18, fill=['#ffffff', '#9fd8ff'], outline='#000000', outline_diag=True)
    save(im, 'menu/1.png')

# 2) menu/1.png: logo tieu de
im = load('menu/1.png') if V != '360' else None
W, H = im.size if im else (1, 1)
new = Image.new('RGBA', (W, H), (0, 0, 0, 0))
y2 = 52 if H >= 100 else 38
draw(new, (0, 4 if H >= 100 else 0, W, 37), 'TINH VÂN', size=18, scale=2, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
draw(new, (0, y2, W, 37), 'TRUYỀN THUYẾT', size=18, scale=2, fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)
if V != '360': save(new, 'menu/1.png')

# 3) bang ten thanh pho map/122-126
for n, t in zip(range(122, 127) if V != '360' else range(126, 131), ['New Zion', 'Ngũ Quốc', 'Hailar', 'Astana', 'Belt']):
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
elif V == '360':                                               # ban 360: menu/0 module 0-4 (manh tieu de phong to)
    # module 0,1,2 (khung 1-6 cua menu.data) deu ve DE LEN nen (module 5) -> lam trong suot, chi giu chu tieu de
    im = load('menu/0.png'); mods = modules('data/menu.data')
    for k, sc in ((0, 3), (1, 3), (2, 2)):
        m = mods[k]; box = (m[1], m[2], m[3], m[4])
        clear(im, box)
        lh = 18 * sc; top = m[2] + (m[4] - 2 * lh - 6) // 2
        draw(im, (m[1], top, m[3], lh + 2), 'TINH VÂN', size=18, scale=sc, fill=['#ffffff', '#7fd0ff'], outline='#06243c', outline_diag=True)
        draw(im, (m[1], top + lh + 6, m[3], lh + 2), 'TRUYỀN THUYẾT', size=18, scale=sc if 89 * sc <= m[3] else sc - 1, fill=['#d8f4ff', '#3f90e0'], outline='#06243c', outline_diag=True)
    save(im, 'menu/0.png')
else:                                                          # ban 240: menu/0 ben phai (module 27, 28)
    im = load('menu/0.png'); mods = modules('data/menu.data')
    for k in (27, 28):
        m = mods[k]
        if m[0] == 0: retitle(im, (m[1], m[2], m[3], m[4]))
    save(im, 'menu/0.png')

# 8) ui/popnum.png: "未命中" (vung 227,5,42x15 cua popnum.data) ve bang drawRegion truc tiep (khong qua VH.mod) -> sua anh
im = load('ui/popnum.png')
clear(im, (227, 5, 42, 15))
draw(im, (227, 5, 42, 15), 'Trượt', size=12, fill=['#ffffff', '#ffd040'], outline='#000000', outline_diag=True)
save(im, 'ui/popnum.png')

# 9) ui/debuff.png: "狂暴" (trang thai cuong bao, 2 khung hinh 58,93,20x15 va 30,92,28x13) -> "Nộ" (cuong no) do (sua anh, khong phu thuoc duong ve)
im = load('ui/debuff.png')
for box in ((30, 92, 28, 13), (58, 93, 20, 15)):
    clear(im, box)
draw(im, (30, 92, 28, 13), 'Nộ', size=12, fill=['#ffd0a0', '#ff2000'])
draw(im, (58, 93, 20, 15), 'Nộ', size=12, fill=['#ffd0a0', '#ff2000'])
save(im, 'ui/debuff.png')
