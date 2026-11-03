#!/usr/bin/env python3
"""T17b1: mockup bộ widget menu mới + 3 màn mẫu (menu 3x3, Túi đồ, popup xác nhận), vẽ bằng PIL từ
sprite THẬT (VFS đã build: img/ui.mgr, img/icon.mgr), font bitmap THẬT (font.bin 15px, font10.bin, f18.bin) và chuỗi Việt THẬT
(dữ liệu đã build ở $WORK/vi_android). Phong cách viền cam/đen của ui_new/.

  python3 -I mock_menu.py [OUT_DIR]        (mặc định: thư mục chứa script)

Sinh: <màn>_<WxH>.png (khung logic), <màn>_400x240_x3.png (màn thật = logic x3), widgets_*.png, trung_gian/*, tong_hop.png,
hit_rects.json, verify.txt, LAYOUT.md.  Mọi số đo nằm trong hàm metrics(W,H) — LAYOUT.md sinh từ chính hàm đó.
"""
import os, sys, json, glob
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import viui as U
from PIL import Image, ImageDraw

OUT = sys.argv[1] if len(sys.argv) > 1 else os.path.dirname(os.path.abspath(__file__))
FRAMES = [(400, 240), (600, 270), (800, 360), (1200, 540)]
MAIN = [(400, 240), (1200, 540)]

# ---------------------------------------------------------------- bảng màu
C = dict(
    ink=(0, 0, 0, 255), navy=(13, 19, 30, 240), panel=(22, 32, 48, 255), cell=(30, 46, 68, 255), cell2=(24, 37, 56, 255),
    line=(70, 92, 120, 255), orange=(240, 138, 22, 255), orange_hi=(255, 192, 56, 255), orange_dk=(150, 72, 10, 255),
    orange_in=(120, 64, 16, 255), white=(246, 240, 228, 255), dim=(150, 164, 184, 255), gold=(255, 214, 90, 255),
    red=(236, 72, 60, 255), blue=(60, 140, 236, 255), green=(104, 220, 112, 255), disabled=(88, 98, 112, 255),
    hp=(214, 52, 48, 255), sp=(52, 124, 226, 255), exp=(226, 188, 48, 255), track=(10, 15, 24, 255))

# ---------------------------------------------------------------- số đo theo khung logic (W,H)
class M: pass

def metrics(W, H):
    m = M(); m.W, m.H = W, H
    m.s = max(1, min(3, H // 180))              # hệ số icon NEAREST: 240->1, 270->1, 360->2, 540->3
    m.hd = H >= 480
    m.px = 18 if m.hd else 15                   # font thân
    m.spx = 15 if m.hd else 10                  # font nhỏ
    m.lp = m.px + 2; m.slp = m.spx + 2          # bước dòng
    s = m.s
    m.mg = 8 * s                                # lề cửa sổ
    m.winH = H - 2 * m.mg
    m.winW = min(W - 2 * m.mg, round(1.72 * m.winH))
    m.wx = (W - m.winW) // 2; m.wy = (H - m.winH) // 2
    m.bt = s                                    # dày viền cam
    m.pad = 3 * s + 3
    m.gap = 2 * s + 2
    m.titleH = 6 * s + 14                       # 20 / 26 / 32
    m.tabH = 12 * s + 12                        # 24 / 36 / 48
    m.kb = max(1, (m.titleH - 4) // 13)         # hệ số icon nút '<' (15x13)
    m.btnH = m.lp + 6 * s
    m.cell = 20 * s + 10; m.cg = 2 * s; m.pitch = m.cell + m.cg
    m.gridW = 4 * m.pitch - m.cg
    m.barH = m.spx + 3
    m.rowH = m.lp + 4 * s                       # hàng danh sách
    m.sbw = 3 * s + 1                           # thanh cuộn
    m.rad = 2 * s + 1                           # bán kính góc cắt
    return m

# ---------------------------------------------------------------- dữ liệu thật
CT = {ri: U.plain(t) for ri, o, t in U.dat_strings('c/csv/common_text.dat')}
def ct(i): return CT[i]
TAB_NAMES = [ct(i) for i in range(9)]
TAB_ICON = [7, 8, 9, 10, 11, 12, 13, 14, 15]          # ui.mgr
ITEMS = {}
for f in sorted(glob.glob(os.path.join(U.VI, 'res/c/csv/item_*.dat'))):
    fn = 'c/csv/' + os.path.basename(f)
    for ri, o, t in U.dat_strings(fn):
        ITEMS.setdefault((fn, ri), {})[o] = U.plain(t)
def item_name(fn, ri): return ITEMS[(fn, ri)][2]
ZONES = [U.plain(t) for ri, o, t in U.dat_strings('c/csv/ingame_text.dat')[:40]]
ZONE = 'Đảo Coldcreek - Trung Tâm'                     # dài nhất trong ZONES khi gộp ';'
LONG_NAME = ('c/csv/item_03.dat', 62)                  # 'Trường Thương Trừ Khử Mặt Trời' (195 px @18, dài nhất của đồ trang bị)
LONG_DESC = ('c/csv/item_14.dat', 2)                   # mô tả nhiều dòng nhất (6 dòng gốc)

# 16 ô trang 1 của túi: (icon.mgr idx, số lượng, phẩm chất, đang trang bị)
BAG = [(203, 0, '`', True), (205, 0, '&', True), (206, 0, '', False), (207, 0, '}', False), (208, 0, '', False), (209, 0, '', False),
       (210, 0, '&', False), (211, 0, '', False), (226, 5, '', False), (227, 12, '', False), (228, 99, '', False), (224, 3, '', False),
       (225, 1, '', False), (231, 27, '', False), (230, 4, '', False), (223, 0, '', False)]

# ---------------------------------------------------------------- canvas + primitive
def scene(W, H):
    ref = Image.open(os.path.join(U.REPO, 'ui_new/ref_ingame_v011.jpg')).convert('RGBA')
    k = max(W / ref.width, H / ref.height)
    r = ref.resize((max(W, round(ref.width * k)), max(H, round(ref.height * k))), Image.LANCZOS)
    x0, y0 = (r.width - W) // 2, (r.height - H) // 2
    return r.crop((x0, y0, x0 + W, y0 + H))

class Cv:
    def __init__(self, m, bg=True, size=None, dim=150):
        self.m = m; W, H = size or (m.W, m.H)
        self.im = scene(W, H) if (bg and not size) else Image.new('RGBA', (W, H), (36, 40, 54, 255))
        if bg and not size: self.alpha_rect((0, 0, W, H), (0, 0, 0, dim))
        self.hits = []

    def alpha_rect(self, r, col, rad=0):
        x0, y0, x1, y1 = r
        ov = Image.new('RGBA', (x1 - x0, y1 - y0), (0, 0, 0, 0)); d = ImageDraw.Draw(ov)
        if rad: d.rounded_rectangle((0, 0, x1 - x0 - 1, y1 - y0 - 1), rad, fill=col)
        else: d.rectangle((0, 0, x1 - x0 - 1, y1 - y0 - 1), fill=col)
        self.im.alpha_composite(ov, (x0, y0))

    def rect(self, r, col, rad=0):
        x0, y0, x1, y1 = r; d = ImageDraw.Draw(self.im)
        if rad: d.rounded_rectangle((x0, y0, x1 - 1, y1 - 1), rad, fill=col)
        else: d.rectangle((x0, y0, x1 - 1, y1 - 1), fill=col)

    def vgrad(self, r, top, bot):
        x0, y0, x1, y1 = r; d = ImageDraw.Draw(self.im); n = max(1, y1 - y0 - 1)
        for i in range(y1 - y0):
            t = i / n; d.line((x0, y0 + i, x1 - 1, y0 + i), fill=tuple(round(top[k] + (bot[k] - top[k]) * t) for k in range(4)))

    def frame(self, r, fill=None, rad=None, orange=True, thick=None):
        """khung viền đen / cam / cam tối (phong cách ui_new/03): đen 1 ngoài, cam `bt`, cam tối 1 trong"""
        m = self.m; rad = m.rad if rad is None else rad; bt = thick or m.bt
        x0, y0, x1, y1 = r; d = ImageDraw.Draw(self.im)
        d.rounded_rectangle((x0, y0, x1 - 1, y1 - 1), rad + 1, fill=C['ink'])
        d.rounded_rectangle((x0 + 1, y0 + 1, x1 - 2, y1 - 2), rad, fill=C['orange'] if orange else C['line'])
        i0 = 1 + bt
        d.rounded_rectangle((x0 + i0, y0 + i0, x1 - 1 - i0, y1 - 1 - i0), max(0, rad - 1), fill=C['orange_in'] if orange else C['ink'])
        j0 = i0 + 1
        if fill is not None:
            self.alpha_rect((x0 + j0, y0 + j0, x1 - j0, y1 - j0), fill, max(0, rad - 2))
        return (x0 + j0, y0 + j0, x1 - j0, y1 - j0)

    def icon(self, bank, i, x, y, k=1, tint=None, center=None):
        im = U.mgr_img(bank, i)
        if k != 1: im = im.resize((im.width * k, im.height * k), Image.NEAREST)
        if tint:
            px = im.load()
            for yy in range(im.height):
                for xx in range(im.width):
                    a = px[xx, yy]
                    if a[3]: px[xx, yy] = (tint[0] * a[0] // 255, tint[1] * a[1] // 255, tint[2] * a[2] // 255, 255) if False else (tint[0], tint[1], tint[2], a[3])
        if center: x, y = center[0] - im.width // 2, center[1] - im.height // 2
        self.im.alpha_composite(im, (x, y)); return im.size

    def text(self, x, y, s, px=None, col=None, shadow=False, anchor='l', maxw=None):
        px = px or self.m.px; col = col or C['white']; w = U.sw(px, s)
        if maxw is not None: assert w <= maxw, ('TRÀN', s, w, maxw)
        if anchor == 'c': x -= w // 2
        elif anchor == 'r': x -= w
        U.text(self.im, x, y, s, px, col, C['ink'] if shadow else None); return w

    def hit(self, id, r, action):
        self.hits.append(dict(id=id, rect=list(r), action=action))

# ---------------------------------------------------------------- widget
def w_window(cv, title, tabs=None, active=None):
    """khung cửa sổ + thanh tiêu đề + nút quay lại (<) [+ thanh tab dưới]. Trả vùng nội dung."""
    m = cv.m; s = m.s
    r = (m.wx, m.wy, m.wx + m.winW, m.wy + m.winH)
    cx0, cy0, cx1, cy1 = cv.frame(r, fill=C['navy'])
    # tiêu đề: dải cam chuyển sắc, viền đen dưới
    th = m.titleH
    cv.vgrad((cx0, cy0, cx1, cy0 + th), (252, 176, 44, 255), (206, 104, 12, 255))
    cv.rect((cx0, cy0 + th - 1, cx1, cy0 + th), C['ink'])
    cv.text((cx0 + cx1) // 2, cy0 + (th - m.px) // 2, title, m.px, C['white'], shadow=True, anchor='c', maxw=cx1 - cx0 - 2 * (th + m.pad))
    bw = th - 2
    bk = (cx0 + 1, cy0 + 1, cx0 + 1 + bw + 4 * s, cy0 + th - 1)   # nút < rộng titleH+4s-2
    cv.rect(bk, (120, 56, 8, 255))
    cv.rect((bk[2] - 1, bk[1], bk[2], bk[3]), C['ink'])
    cv.icon('ui', 25, 0, 0, m.kb, center=((bk[0] + bk[2]) // 2, (bk[1] + bk[3]) // 2))
    cv.hit('close', (cx0, cy0, bk[2] + 2 * s, cy0 + th), 'KeyPressed(Back) -> state gốc')
    body = [cx0, cy0 + th, cx1, cy1]
    if tabs is not None:
        tb = m.tabH
        cv.rect((cx0, cy1 - tb, cx1, cy1), (7, 11, 18, 255))
        cv.rect((cx0, cy1 - tb, cx1, cy1 - tb + 1), C['ink'])
        cv.rect((cx0, cy1 - tb + 1, cx1, cy1 - tb + 1 + max(1, s - 0)), C['orange_dk'])
        n = len(TAB_ICON); tw = (cx1 - cx0) / n
        for i in range(n):
            x0 = cx0 + round(i * tw); x1 = cx0 + round((i + 1) * tw)
            if i == active:
                cv.vgrad((x0 + 1, cy1 - tb + 2 + s, x1 - 1, cy1 - 1), (250, 160, 36, 255), (200, 100, 14, 255))
                cv.rect((x0, cy1 - tb + 2 + s, x0 + 1, cy1 - 1), C['ink']); cv.rect((x1 - 1, cy1 - tb + 2 + s, x1, cy1 - 1), C['ink'])
            ic = U.mgr_img('ui', TAB_ICON[i])
            cv.icon('ui', TAB_ICON[i], 0, 0, s, tint=C['white'] if i == active else C['dim'],
                    center=((x0 + x1) // 2, cy1 - tb // 2 + (1 + s) // 2))
            cv.hit('tab%d' % i, (x0, cy1 - tb, x1, cy1), 'đổi tab: SetState(%d)' % i)
        body[3] = cy1 - tb
    return tuple(body)

def w_button(cv, r, label, kind='normal', hid=None, action=''):
    """nút: normal | primary (cam) | pressed | disabled"""
    m = cv.m; x0, y0, x1, y1 = r
    if kind == 'primary':
        inner = cv.frame(r, fill=None, rad=m.rad); cv.vgrad(inner, (250, 164, 40, 255), (204, 100, 12, 255)); col = C['white']; sh = True
    elif kind == 'pressed':
        inner = cv.frame(r, fill=None, rad=m.rad); cv.vgrad(inner, (150, 78, 12, 255), (110, 52, 8, 255)); col = C['gold']; sh = True
    elif kind == 'disabled':
        inner = cv.frame(r, fill=(20, 26, 36, 255), rad=m.rad, orange=False); col = C['disabled']; sh = False
    else:
        inner = cv.frame(r, fill=None, rad=m.rad); cv.vgrad(inner, (38, 54, 78, 255), (22, 32, 48, 255)); col = C['white']; sh = False
    cv.text((x0 + x1) // 2, y0 + (y1 - y0 - m.px) // 2, label, m.px, col, shadow=sh, anchor='c', maxw=x1 - x0 - 2 * m.pad)
    if hid: cv.hit(hid, r, action)

def w_scrollbar(cv, x, y0, y1, pos, total, view):
    """thanh cuộn dọc: pos = dòng đầu, total = tổng dòng, view = số dòng hiển thị"""
    m = cv.m; w = m.sbw
    cv.rect((x, y0, x + w, y1), C['track']); cv.rect((x, y0, x + 1, y1), C['ink'])
    th = max(2 * m.s + 6, round((y1 - y0) * min(1, view / max(total, 1))))
    ty = y0 + round((y1 - y0 - th) * (pos / max(total - view, 1)))
    cv.rect((x + 1, ty, x + w, ty + th), C['orange'], rad=1 if m.s > 1 else 0)
    cv.rect((x + 1, ty, x + 2, ty + th), C['orange_hi'])

def w_bar(cv, x, y, w, label, val, mx, col):
    """thanh HP/SP/EXP: nhãn (font thân) + thanh có số ở giữa (font nhỏ)"""
    m = cv.m
    lw = U.sw(m.px, 'EXP') + 2 * m.s + 2
    cv.text(x, y + (m.barH - m.px) // 2 + (1 if not m.hd else 0), label, m.px, C['dim'])
    bx = x + lw; r = (bx, y, x + w, y + m.barH)
    cv.rect(r, C['ink']); cv.rect((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), C['track'])
    fw = round((r[2] - r[0] - 2) * val / mx)
    cv.vgrad((r[0] + 1, r[1] + 1, r[0] + 1 + fw, r[3] - 1), tuple(min(255, c + 40) for c in col[:3]) + (255,), col)
    t = '%d/%d' % (val, mx)
    cv.text((r[0] + r[2]) // 2, y + (m.barH - m.spx) // 2, t, m.spx, C['white'], shadow=m.hd, anchor='c', maxw=r[2] - r[0] - 2)

def w_cell(cv, x, y, item=None, selected=False, rarity=None):
    """ô lưới vật phẩm: (icon, count, rarity, equipped). Ô vuông cell x cell; icon gốc x s NEAREST"""
    m = cv.m; s = m.s; c = m.cell
    r = (x, y, x + c, y + c)
    cv.rect(r, C['ink'])
    if selected: cv.vgrad((x + 1, y + 1, x + c - 1, y + c - 1), (250, 160, 36, 255), (196, 96, 12, 255))
    else: cv.vgrad((x + 1, y + 1, x + c - 1, y + c - 1), C['cell'], C['cell2'])
    if item is not None:
        ic, cnt, rar, eq = item
        cv.icon('icon', ic, x + (c - 20 * s) // 2, y + (c - 20 * s) // 2, s)
        if rar in U.RARITY:
            col = U.RARITY[rar] + (255,)
            cv.rect((x + 1, y + 1, x + c - 1, y + 1 + max(1, s)), col)           # vạch phẩm chất trên cùng
        if eq:                                                                    # cờ "đang trang bị": cờ vàng góc trái trên
            fw = 5 * s + 1
            cv.rect((x + 1, y + 1 + max(1, s), x + 1 + fw, y + 1 + max(1, s) + fw), C['ink'])
            cv.rect((x + 2, y + 2 + max(1, s), x + fw, y + max(1, s) + fw), C['gold'])
        if cnt > 1 or cnt == 0 and False:
            t = str(cnt); tw = U.sw(m.spx, t)
            cv.text(x + c - 2 - tw, y + c - 2 - m.spx, t, m.spx, C['white'], shadow=True)
    if selected:
        cv.rect((x, y, x + c, y + 1), C['orange_hi']); cv.rect((x, y + c - 1, x + c, y + c), C['orange_hi'])
        cv.rect((x, y, x + 1, y + c), C['orange_hi']); cv.rect((x + c - 1, y, x + c, y + c), C['orange_hi'])
        cv.rect((x - 1, y - 1, x + c + 1, y), C['ink']); cv.rect((x - 1, y + c, x + c + 1, y + c + 1), C['ink'])
        cv.rect((x - 1, y, x, y + c), C['ink']); cv.rect((x + c, y, x + c + 1, y + c), C['ink'])

def w_row(cv, r, label, value=None, icon=None, selected=False):
    """hàng danh sách: [icon] nhãn ........ giá trị"""
    m = cv.m; x0, y0, x1, y1 = r
    if selected:
        cv.rect((x0, y0, x1, y1), C['ink']); cv.vgrad((x0 + 1, y0 + 1, x1 - 1, y1 - 1), (250, 160, 36, 255), (196, 96, 12, 255))
    else:
        cv.rect((x0, y0, x1, y1), C['ink']); cv.vgrad((x0 + 1, y0 + 1, x1 - 1, y1 - 1), C['cell'], C['cell2'])
    tx = x0 + m.pad
    if icon is not None:
        iw = cv.icon('icon', icon, x0 + 2, y0 + (y1 - y0 - 20 * (1 if m.s == 1 else 1)) // 2, 1)[0]
        tx += iw
    vw = U.sw(m.px, value) if value else 0
    cv.text(tx, y0 + (y1 - y0 - m.px) // 2, label, m.px, C['white'], shadow=selected, maxw=x1 - tx - vw - 2 * m.pad)
    if value: cv.text(x1 - m.pad, y0 + (y1 - y0 - m.px) // 2, value, m.px, C['gold'] if not selected else C['white'], shadow=selected, anchor='r')

def w_descbox(cv, r, name, lines, pos=0, meta=None, scroll=True):
    """hộp mô tả: tên (tối đa 2 dòng) + dòng phụ + vùng cuộn có thanh cuộn nếu tràn. Trả số dòng mô tả."""
    m = cv.m; x0, y0, x1, y1 = r
    inner = cv.frame((x0, y0, x1, y1), fill=(10, 16, 26, 235), orange=False, rad=m.rad, thick=1)
    ix0, iy0, ix1, iy1 = inner; p = m.pad // 2 + 1
    y = iy0 + p
    nm, code = U.clean_name(name)
    col = U.RARITY.get(code, C['white'][:3]) + (255,)
    nl = U.wrap(m.px, nm, ix1 - ix0 - 2 * p - m.sbw - 2)
    assert len(nl) <= 2, ('tên > 2 dòng', nm)
    for l in nl:
        cv.text(ix0 + p, y, l, m.px, col, shadow=True); y += m.lp
    if meta:
        cv.text(ix0 + p, y, meta, m.spx, C['dim'], maxw=ix1 - ix0 - 2 * p); y += m.slp
    y += 2
    cv.rect((ix0 + p, y, ix1 - p, y + 1), C['line']); y += 3
    return y

# ---------------------------------------------------------------- màn mẫu
def content_rect(m, body):
    x0, y0, x1, y1 = body
    return (x0 + m.pad, y0 + m.pad, x1 - m.pad, y1 - m.pad)

def screen_menu3x3(m):
    cv = Cv(m); s = m.s
    zone = ZONE
    body = w_window(cv, zone, tabs=None)
    x0, y0, x1, y1 = content_rect(m, body)
    cw = x1 - x0; ch = y1 - y0
    sumw = round(cw * 0.36); g = m.gap + 2
    gx = x0 + sumw + g; gw = x1 - gx
    tw = (gw - 2 * g) // 3; th = (ch - 2 * g) // 3
    sel = 1
    for i in range(9):
        c, rw = i % 3, i // 3
        tx = gx + c * (tw + g) + (gw - 3 * tw - 2 * g) // 2; ty = y0 + rw * (th + g) + (ch - 3 * th - 2 * g) // 2
        r = (tx, ty, tx + tw, ty + th)
        if i == sel:
            inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
        else:
            inner = cv.frame(r, rad=m.rad, orange=False); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255))
        k = min(m.s + 1, 3)
        ic = U.mgr_img('ui', TAB_ICON[i])
        cv.icon('ui', TAB_ICON[i], 0, 0, k, tint=C['white'] if i == sel else (190, 204, 224),
                center=((tx + tw // 2), ty + (th - m.px - 3 * s) // 2 + 1))
        cv.text(tx + tw // 2, ty + th - m.px - 2 * s - 1, TAB_NAMES[i], m.px, C['white'] if i == sel else (210, 220, 236, 255), shadow=True,
                anchor='c', maxw=tw - 2 * s - 2)
        cv.hit('tile%d' % i, r, 'đặt con trỏ=%d rồi KeyPressed(-5) gốc (SetState(tab %d))' % (i, i))
    # cột tóm tắt nhân vật
    sx0, sx1 = x0, x0 + sumw
    cv.frame((sx0, y0, sx1, y1), fill=(14, 22, 34, 230), rad=m.rad, orange=False, thick=1)
    px_ = sx0 + m.pad; yy = y0 + m.pad
    pr = 22 * s + 20 if not m.hd else 84
    ring = Image.open(os.path.join(U.REPO, 'ui_new/03_frame_empty.png')).convert('RGBA').resize((pr, pr), Image.LANCZOS)
    cv.im.alpha_composite(ring, (px_, yy))
    cv.icon('ui', 7, 0, 0, min(s + 1, 3), tint=C['white'], center=(px_ + pr // 2, yy + pr // 2))
    nx = px_ + pr + m.pad // 2 + 2
    ty_ = yy + max(0, (pr - 3 * m.lp) // 2)
    cv.text(nx, ty_, 'Schurtz', m.px, C['gold'], shadow=True, maxw=sx1 - nx - m.pad)
    cv.text(nx, ty_ + m.lp, ct(178), m.px, C['dim'], maxw=sx1 - nx - m.pad)                 # Warrior
    cv.text(nx, ty_ + 2 * m.lp, ct(13) + ' 48', m.px, C['white'], maxw=sx1 - nx - m.pad)    # LV 48
    yy += max(pr, 3 * m.lp) + m.pad
    bw = sx1 - sx0 - 2 * m.pad
    for lab, v, mx_, col in ((ct(10), 1842, 2176, C['hp']), (ct(11), 391, 520, C['sp']), (ct(12), 7120, 9800, C['exp'])):
        w_bar(cv, px_, yy, bw, lab, v, mx_, col); yy += m.barH + m.pad - 1
    yy += 1
    cv.rect((px_, yy, sx1 - m.pad, yy + 1), C['line']); yy += m.pad - 1
    cv.text(px_, yy, ct(19), m.px, C['dim'], maxw=bw); yy += m.lp + 1
    coin = U.mgr_img('ui', 16); gv = '1,234,567'; rh = max(m.lp, coin.height * s)
    cv.icon('ui', 16, px_, yy + (rh - coin.height * s) // 2, s)
    cv.text(sx1 - m.pad, yy + (rh - m.px) // 2, gv, m.px, C['gold'], anchor='r', maxw=bw - coin.width * s - 4)
    assert yy + rh <= y1 - 2, ('cột tóm tắt tràn', yy + rh, y1)
    return cv

def bag_geometry(m, body):
    x0, y0, x1, y1 = content_rect(m, body)
    g = {}
    g['strip'] = (x0, y0, x1, y0 + m.lp + 2 * m.s + 2)
    top = g['strip'][3] + m.gap
    g['pgx'] = x0; g['pgw'] = m.pitch
    g['gx'] = x0 + m.pitch + m.gap + 1; g['gy'] = top
    g['dx'] = g['gx'] + m.gridW + m.gap + 1; g['dx1'] = x1; g['dy'] = top; g['dy1'] = y1
    return g

def screen_bag(m, popup=False, ring=False):
    cv = Cv(m); s = m.s
    body = w_window(cv, TAB_NAMES[1], tabs=True, active=1)
    g = bag_geometry(m, body)
    sx0, sy0, sx1, sy1 = g['strip']
    # dải tiền
    cv.text(sx0 + 2, sy0 + 1, ct(19), m.px, C['dim'])
    coin = U.mgr_img('ui', 16); gv = '1,234,567'
    gw = U.sw(m.px, gv)
    cv.text(sx1 - 2, sy0 + 1, gv, m.px, C['gold'], anchor='r')
    cv.icon('ui', 16, sx1 - 2 - gw - 3 - coin.width * s, sy0 + 1 + (m.px - coin.height * s) // 2 + 1 if s == 1 else sy0 + (sy1 - sy0 - coin.height * s) // 2, s)
    cv.rect((sx0, sy1 - 1, sx1, sy1), C['orange_dk'])
    # cột trang (3 trang) — cao bằng 1 hàng ô
    npg = 3; ph = m.pitch
    for i in range(npg):
        r = (g['pgx'], g['gy'] + i * ph, g['pgx'] + m.pitch, g['gy'] + i * ph + m.cell)
        if i == 0:
            inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
        else:
            inner = cv.frame(r, rad=m.rad, orange=False); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255))
        ic = U.mgr_img('ui', 1)
        cv.icon('ui', 1, 0, 0, s, center=((r[0] + r[2]) // 2, (r[1] + r[3]) // 2 - (s if m.hd else 0)))
        n = str(i + 1); cv.text(r[2] - 3 - U.sw(m.spx, n), r[3] - 3 - m.spx, n, m.spx, C['white'], shadow=True)
        cv.hit('page%d' % i, r, '[this+0xe]=%d (trang), con trỏ [this+0xf]=0' % i)
    # lưới 4x4
    sel = 6 if ring else 0
    for i in range(16):
        c, rw = i % 4, i // 4
        x = g['gx'] + c * m.pitch; y = g['gy'] + rw * m.pitch
        w_cell(cv, x, y, BAG[i], selected=(i == sel))
        cv.hit('cell%d' % i, (x, y, x + m.cell, y + m.cell), 'con trỏ [this+0xf]=%d rồi KeyPressed(-5) (chạm lần 2 = OK)' % i)
    # hộp chi tiết (đồ trang bị dài nhất tên)
    dx, dy, dx1, dy1 = g['dx'], g['dy'], g['dx1'], g['dy1']
    btn_y0 = dy1 - m.btnH
    w_button(cv, (dx, btn_y0, dx1, dy1), U.strip_hint(ct(22)), 'normal', 'sort', 'phím 0 -> KeyBagMenu(0x30) = BagItem::SortBagItem')
    box = (dx, dy, dx1, btn_y0 - m.gap)
    if ring:                                    # vật phẩm có mô tả dài: hộp mô tả cuộn
        y = w_descbox(cv, box, item_name(*LONG_DESC), [], meta=None)
        ix0 = box[0] + 2 + m.bt; ix1 = box[2] - 2 - m.bt; p = m.pad // 2 + 1
        ls = U.wrap(m.px, ITEMS[LONG_DESC][15], ix1 - ix0 - 2 * p - m.sbw - 2)
        view = max(1, (box[3] - 3 - y) // m.lp)
        for i, l in enumerate(ls[:view]): cv.text(ix0 + p, y + i * m.lp, l, m.px, C['white'])
        if len(ls) > view: w_scrollbar(cv, ix1 - m.sbw - 1, y, box[3] - 3, 0, len(ls), view)
    else:
        y = w_descbox(cv, box, item_name(*LONG_NAME), [], meta=ct(222).split()[0] + ' ' + ITEMS[LONG_NAME][24].rstrip(';'))
        ix0 = box[0] + 2 + m.bt; ix1 = box[2] - 2 - m.bt; p = m.pad // 2 + 1
        rows = [(ct(221), '35'), (ct(228), '248'), (ct(225), ct(227))]
        sp, sl = m.spx, m.slp
        for lab, val in rows:
            cv.text(ix0 + p, y, lab, sp, C['dim'], maxw=(ix1 - ix0) // 2 + 20)
            cv.text(ix1 - p, y, val, sp, C['white'], anchor='r'); y += sl
        cv.text(ix0 + p, y, ct(120), sp, C['green'], maxw=ix1 - ix0 - 2 * p); y += sl
        cv.text(ix0 + p, y, ct(287).lstrip('+'), sp, U.RARITY['&'] + (255,), maxw=ix1 - ix0 - 2 * p); y += sl
        assert y <= box[3] - 2, ('chi tiết tràn hộp', y, box[3])
    cv.hit('detail', box, 'chỉ hiển thị (chạm = chọn ô tương ứng)')
    if popup:
        w_popup(cv, ct(58), yes_no=True)
    return cv

def popup_geometry(m, text_s, yes_no):
    pw = round(0.70 * m.W) if not m.hd else 480
    pw = min(pw, m.W - 2 * m.mg)
    tw = pw - 2 * (m.pad + 2 * m.s + 4)
    lines = U.wrap(m.px, text_s, tw)
    ph = 2 * m.pad + 4 * m.s + len(lines) * m.lp + m.pad + m.btnH + 2
    return pw, ph, lines, tw

def w_popup(cv, text_s, yes_no=True, ok_label=None):
    """popup chung: nền làm tối + hộp giữa màn; chữ tự xuống dòng; nút Có/Không (hoặc OK)"""
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw, ph, lines, tw = popup_geometry(m, text_s, yes_no)
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    r = (x0, y0, x0 + pw, y0 + ph)
    inner = cv.frame(r, fill=(14, 22, 34, 248))
    y = inner[1] + m.pad + 2 * s
    for l in lines:
        cv.text(m.W // 2, y, l, m.px, C['white'], anchor='c', maxw=tw); y += m.lp
    by = inner[3] - m.pad - m.btnH
    bx0 = inner[0] + m.pad + 2 * s; bx1 = inner[2] - m.pad - 2 * s
    if yes_no:
        mid = (bx0 + bx1) // 2; gg = m.pad
        w_button(cv, (bx0, by, mid - gg // 2, by + m.btnH), ct(50), 'primary', 'popup_yes', 'KeyPressed(-5) với con trỏ popup=Có')
        w_button(cv, (mid + gg // 2 + 1, by, bx1, by + m.btnH), ct(51), 'normal', 'popup_no', 'con trỏ popup=Không + KeyPressed(-5)')
    else:
        bw = max(U.sw(m.px, ok_label or ct(56)) + 4 * m.pad, (bx1 - bx0) // 3)
        w_button(cv, ((bx0 + bx1 - bw) // 2, by, (bx0 + bx1 + bw) // 2, by + m.btnH), ok_label or ct(56), 'primary', 'popup_ok', 'KeyPressed(-5)')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua (không đóng)')
    return r

def screen_popup(m):
    return screen_bag(m, popup=True)

# ---------------------------------------------------------------- bảng widget
def sheet_widgets(m):
    """catalog widget: mỗi widget vẽ vào ảnh riêng (nhãn + mẫu) rồi xếp dòng tự xuống hàng"""
    s = m.s; u = 1 if not m.hd else 2
    W = 800 if not m.hd else 1500; pad = 8 * s
    blocks = []
    lh = m.slp + 4
    def block(title, w, h, fn):
        c = Cv(m, size=(max(w, U.sw(m.spx, title) + 2), h + lh)); c.im.paste((30, 34, 46, 255), (0, 0, c.im.width, c.im.height))
        c.text(0, 0, title, m.spx, (120, 200, 255, 255)); fn(c, 0, lh); blocks.append(c.im)
    # 1 cửa sổ: khung + tiêu đề + nút < + thanh tab
    def f1(c, ox, oy):
        r = (ox, oy, ox + 180 * u, oy + m.titleH + m.tabH + 36 * u)
        inner = c.frame(r, fill=C['navy']); th = m.titleH
        c.vgrad((inner[0], inner[1], inner[2], inner[1] + th), (252, 176, 44, 255), (206, 104, 12, 255)); c.rect((inner[0], inner[1] + th - 1, inner[2], inner[1] + th), C['ink'])
        c.text((inner[0] + inner[2]) // 2, inner[1] + (th - m.px) // 2, TAB_NAMES[1], m.px, C['white'], shadow=True, anchor='c')
        bk = (inner[0] + 1, inner[1] + 1, inner[0] + 1 + th - 2 + 4 * s, inner[1] + th - 1)
        c.rect(bk, (120, 56, 8, 255)); c.rect((bk[2] - 1, bk[1], bk[2], bk[3]), C['ink']); c.icon('ui', 25, 0, 0, m.kb, center=((bk[0] + bk[2]) // 2, (bk[1] + bk[3]) // 2))
        tb = m.tabH; ty0 = inner[3] - tb
        c.rect((inner[0], ty0, inner[2], inner[3]), (7, 11, 18, 255)); c.rect((inner[0], ty0, inner[2], ty0 + 1), C['ink'])
        tw = (inner[2] - inner[0]) / 9
        for i in range(9):
            x0 = inner[0] + round(i * tw); x1 = inner[0] + round((i + 1) * tw)
            if i == 1: c.vgrad((x0 + 1, ty0 + 2 + s, x1 - 1, inner[3] - 1), (250, 160, 36, 255), (200, 100, 14, 255))
            c.icon('ui', TAB_ICON[i], 0, 0, s, tint=C['white'] if i == 1 else C['dim'], center=((x0 + x1) // 2, inner[3] - tb // 2 + (1 + s) // 2))
    block('1. cửa sổ: khung + tiêu đề + nút < + thanh tab', 180 * u + 2, m.titleH + m.tabH + 36 * u, f1)
    # 2 nút
    bw = U.sw(m.px, 'Không') + 6 * m.pad
    def f2(c, ox, oy):
        for i, (k, lab) in enumerate((('normal', ct(52)), ('primary', ct(50)), ('pressed', ct(51)), ('disabled', ct(57)))):
            x = ox + (i % 2) * (bw + m.pad); y = oy + (i // 2) * (m.btnH + m.pad)
            w_button(c, (x, y, x + bw, y + m.btnH), lab, k)
    block('2. nút: thường / chính / nhấn / vô hiệu', 2 * bw + m.pad, 2 * m.btnH + m.pad, f2)
    # 3 thanh
    def f3(c, ox, oy):
        for i, (lab, v, mx_, col) in enumerate(((ct(10), 1842, 2176, C['hp']), (ct(11), 391, 520, C['sp']), (ct(12), 7120, 9800, C['exp']))):
            w_bar(c, ox, oy + i * (m.barH + m.pad), 130 * u, lab, v, mx_, col)
    block('3. thanh HP / SP / EXP', 130 * u, 3 * m.barH + 2 * m.pad, f3)
    # 4 ô lưới
    states = [(None, False), (BAG[2], False), (BAG[8], False), (BAG[0], True), (BAG[3], False), (BAG[10], False)]
    def f4(c, ox, oy):
        for i, (it, sel) in enumerate(states): w_cell(c, ox + i * (m.cell + m.cg + 2) + 1, oy + 1, it, sel)
    block('4. ô lưới: trống / thường / chọn / trang bị+phẩm chất / số lượng', 6 * (m.cell + m.cg + 2) + 2, m.cell + 2, f4)
    # 5 hàng + cuộn
    lw = 150 * u
    def f5(c, ox, oy):
        for i, nme in enumerate([ct(83), ct(84), ct(86), ct(87), ct(88)]):
            y = oy + i * (m.rowH + 1); w_row(c, (ox, y, ox + lw, y + m.rowH), nme, '1,200' if i % 2 == 0 else None, selected=(i == 1))
        w_scrollbar(c, ox + lw + 2, oy, oy + 5 * (m.rowH + 1) - 1, 1, 12, 5)
    block('5. hàng danh sách + thanh cuộn', lw + m.sbw + 4, 5 * (m.rowH + 1), f5)
    # 6 hộp mô tả
    dw = 230 * u; dh = 4 * m.lp + 10
    def f6(c, ox, oy):
        box = (ox, oy, ox + dw, oy + dh)
        y = w_descbox(c, box, item_name(*LONG_DESC), [], meta=None)
        p = m.pad // 2 + 1; ix0 = box[0] + 2 + m.bt; ix1 = box[2] - 2 - m.bt
        ls = U.wrap(m.px, ITEMS[LONG_DESC][15], ix1 - ix0 - 2 * p - m.sbw - 2); view = max(1, (box[3] - 3 - y) // m.lp)
        for i, l in enumerate(ls[:view]): c.text(ix0 + p, y + i * m.lp, l, m.px, C['white'])
        if len(ls) > view: w_scrollbar(c, ix1 - m.sbw - 1, y, box[3] - 3, 0, len(ls), view)
    block('6. hộp mô tả (mô tả dài nhất; cuộn khi tràn)', dw, dh, f6)
    # 7 popup
    def popup_block(t, yn, ok_only=False):
        pw, ph, lines, tw2 = popup_geometry(m, t, yn)
        if ok_only: pw = 200 * u; lines = U.wrap(m.px, t, pw - 2 * (m.pad + 2 * s + 4)); tw2 = pw - 2 * (m.pad + 2 * s + 4); ph = 2 * m.pad + 4 * s + len(lines) * m.lp + m.pad + m.btnH + 2
        def f(c, ox, oy):
            r = (ox, oy, ox + pw, oy + ph); inner = c.frame(r, fill=(14, 22, 34, 255)); y = inner[1] + m.pad + 2 * s
            for l in lines: c.text((r[0] + r[2]) // 2, y, l, m.px, C['white'], anchor='c', maxw=tw2); y += m.lp
            by = inner[3] - m.pad - m.btnH; bx0 = inner[0] + m.pad + 2 * s; bx1 = inner[2] - m.pad - 2 * s
            if yn:
                mid = (bx0 + bx1) // 2
                w_button(c, (bx0, by, mid - m.pad // 2, by + m.btnH), ct(50), 'primary'); w_button(c, (mid + m.pad // 2 + 1, by, bx1, by + m.btnH), ct(51), 'normal')
            else:
                bw2 = (bx1 - bx0) // 3; w_button(c, ((bx0 + bx1 - bw2) // 2, by, (bx0 + bx1 + bw2) // 2, by + m.btnH), ct(56), 'primary')
        return pw, ph, f
    pw, ph, f = popup_block(ct(273), True); block('7a. popup chung có/không (chuỗi popup dài nhất)', pw, ph, f)
    pw, ph, f = popup_block(ct(188), False, True); block('7b. popup chỉ OK', pw, ph, f)
    # xếp dòng
    x = y = pad; rowh = 0; place = []
    for im in blocks:
        if x + im.width > W - pad and x > pad: x = pad; y += rowh + pad; rowh = 0
        place.append((im, x, y)); x += im.width + pad * 2; rowh = max(rowh, im.height)
    H = y + rowh + pad
    cv = Cv(m, size=(W, H)); cv.im.paste((30, 34, 46, 255), (0, 0, W, H))
    for im, px_, py_ in place: cv.im.paste(im, (px_, py_))
    return cv

# ---------------------------------------------------------------- kiểm không tràn trên dữ liệu thật
def verify(out):
    """kiểm không tràn trên MỌI chuỗi thật liên quan 3 màn mẫu, ở cả 4 khung. Ghi verify.txt."""
    L = []
    for (W, H) in FRAMES:
        m = metrics(W, H)
        body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
        g = bag_geometry(m, body)
        tw = g['dx1'] - g['dx'] - 2 * (m.pad // 2 + 1) - 2 * (1 + 1 + 1) - m.sbw - 2     # chữ trong hộp chi tiết
        L.append('== %dx%d  font %dpx / nhỏ %dpx, icon x%d ==' % (W, H, m.px, m.spx, m.s))
        # tên vật phẩm
        wmax = (0, ''); lmax = (0, '')
        for k, v in ITEMS.items():
            nm = U.clean_name(v[2])[0]; w = U.sw(m.px, nm); n = len(U.wrap(m.px, nm, tw))
            wmax = max(wmax, (w, nm)); lmax = max(lmax, (n, nm))
        L.append('  tên vật phẩm (%d): rộng nhất %d px "%s" (hộp chữ %d px); nhiều dòng nhất %d dòng (giới hạn 2): %s' % (len(ITEMS), wmax[0], wmax[1], tw, lmax[0], 'ĐẠT' if lmax[0] <= 2 else 'TRÀN'))
        # mô tả
        cnt = {}
        for k, v in ITEMS.items():
            for o, t in v.items():
                if o == 2 or len(t) < 25: continue
                n = len(U.wrap(m.px, t, tw)); cnt[n] = cnt.get(n, 0) + 1
        view = (g['dy1'] - g['dy'] - m.btnH - m.gap - 6 - m.lp - m.slp) // m.lp
        over = sum(v for k, v in cnt.items() if k > view)
        L.append('  mô tả (chuỗi >=25 ký tự): phân bố số dòng %s; hộp mô tả hiện %d dòng; %d chuỗi dài hơn -> cuộn (thanh cuộn), không cắt' % (
            ', '.join('%d:%d' % (k, cnt[k]) for k in sorted(cnt)), view, over))
        # nhãn tab / tile
        gx_tile_w = None
        wl = max((U.sw(m.px, t), t) for t in TAB_NAMES)
        cw = m.winW - 2 * m.bt - 2 * 2; tabw = cw // 9
        sumw = round((m.winW - 2 * (m.bt + 2) - 2 * m.pad) * 0.36)
        gw = (m.winW - 2 * (m.bt + 2) - 2 * m.pad) - sumw - (m.gap + 2)
        tilew = (gw - 2 * (m.gap + 2)) // 3
        L.append('  nhãn 9 mục: dài nhất %d px "%s" <= ô menu 3x3 %d px (chừa %d), tab %d px (chỉ icon)' % (wl[0], wl[1], tilew, tilew - wl[0], tabw))
        # popup: mọi chuỗi common_text
        pw = round(0.70 * m.W) if not m.hd else 480; pw = min(pw, m.W - 2 * m.mg); ptw = pw - 2 * (m.pad + 2 * m.s + 4)
        worst = (0, 0, ''); allh = 0
        for i, t in CT.items():
            if len(t) < 12: continue
            n = len(U.wrap(m.px, t, ptw)); ph = 2 * m.pad + 4 * m.s + n * m.lp + m.pad + m.btnH + 2
            if n > worst[0]: worst = (n, ph, t[:44])
        L.append('  popup rộng %d (chữ %d px): chuỗi common_text dài nhất theo dòng = %d dòng -> popup cao %d <= khung %d: %s ("%s...")' % (
            pw, ptw, worst[0], worst[1], m.H - 2 * m.mg, 'ĐẠT' if worst[1] <= m.H - 2 * m.mg else 'CẦN TÁCH TRANG', worst[2]))
        # nhãn nút
        L.append('  nút popup %d px rộng; nhãn dài nhất "Không" %d px' % (((pw - 2 * (m.pad + 2 * m.s + 4)) - m.pad) // 2, U.sw(m.px, 'Không')))
    open(os.path.join(out, 'verify.txt'), 'w', encoding='utf-8').write('\n'.join(L) + '\n')
    return L

# ---------------------------------------------------------------- xuất
def save_pair(cv, name, m, out):
    cv.im.convert('RGB').save(os.path.join(out, '%s_%dx%d.png' % (name, m.W, m.H)))
    if (m.W, m.H) == (400, 240):
        cv.im.resize((m.W * 3, m.H * 3), Image.NEAREST).convert('RGB').save(os.path.join(out, '%s_400x240_x3.png' % name))

def main():
    os.makedirs(OUT, exist_ok=True); os.makedirs(os.path.join(OUT, 'trung_gian'), exist_ok=True)
    hits = {}
    tiles = []
    for (W, H) in FRAMES:
        m = metrics(W, H)
        for name, fn in (('menu3x3', screen_menu3x3), ('tui_do', screen_bag), ('popup_xac_nhan', screen_popup), ('tui_do_mo_ta', lambda mm: screen_bag(mm, ring=True))):
            cv = fn(m)
            if (W, H) in MAIN: save_pair(cv, name, m, OUT)
            else: cv.im.convert('RGB').save(os.path.join(OUT, 'trung_gian', '%s_%dx%d.png' % (name, W, H)))
            hits['%s_%dx%d' % (name, W, H)] = cv.hits
            tiles.append((name, W, H, cv.im.convert('RGB')))
        if (W, H) in MAIN:
            wc = sheet_widgets(m)
            wc.im.convert('RGB').save(os.path.join(OUT, 'widgets_%dx%d.png' % (W, H)))
            tiles.append(('widgets', W, H, wc.im.convert('RGB')))
    json.dump(hits, open(os.path.join(OUT, 'hit_rects.json'), 'w'), ensure_ascii=False, indent=1)
    for l in verify(OUT): print(l)
    # tổng hợp: lưới 4 cột x hàng, nhãn
    cols = [(400, 240), (600, 270), (800, 360), (1200, 540)]
    names = ['menu3x3', 'tui_do', 'tui_do_mo_ta', 'popup_xac_nhan']
    cellw = 1200 // 2; pad = 16; lab = 26
    # mỗi ô thu về rộng 600 (400x240 phóng x1.5; 1200x540 thu 1/2 -> 600x270) để so cùng tầm nhìn
    th = {}
    for n, W, H, im in tiles:
        if n == 'widgets': continue
        sc = 600 / W if W != 400 else 600 / 400
        th[(n, W)] = im.resize((600, round(H * sc)), Image.NEAREST if W == 400 else Image.LANCZOS)
    rowh = {n: max(th[(n, W)].height for W, _ in cols) for n in names}
    sheetW = 2 * pad + 4 * 600 + 3 * pad; sheetH = pad + lab + sum(rowh[n] + lab + pad for n in names) + 2 * pad
    sheet = Image.new('RGB', (sheetW, sheetH), (22, 24, 32)); d = ImageDraw.Draw(sheet)
    y = pad
    for ci, (W, H) in enumerate(cols):
        d.text((pad + ci * (600 + pad), y + 4), 'logic %dx%d  (font %s, icon x%d)%s' % (W, H, '18' if H >= 480 else '15/10', max(1, min(3, H // 180)),
                                                                                        '  [hiển thị x1.5, thật x3]' if W == 400 else ('  [thu 1/2]' if W == 1200 else '  [thu/phóng thị giác]')), fill=(255, 210, 120))
    y += lab
    titles = {'menu3x3': 'Menu 3x3', 'tui_do': 'Túi đồ', 'tui_do_mo_ta': 'Túi đồ: vật phẩm có mô tả dài (hộp mô tả cuộn)', 'popup_xac_nhan': 'Popup xác nhận'}
    for n in names:
        d.text((pad, y), titles[n], fill=(120, 200, 255)); y += lab - 8
        for ci, (W, H) in enumerate(cols):
            im = th[(n, W)]; sheet.paste(im, (pad + ci * (600 + pad), y))
        y += rowh[n] + pad
    # hàng widgets (2 mốc) ở cuối
    sheet.save(os.path.join(OUT, 'tong_hop.png'))
    # bảng widget đưa vào tong_hop2 (rộng): ghép 2 ảnh widgets
    wa = Image.open(os.path.join(OUT, 'widgets_400x240.png')); wb = Image.open(os.path.join(OUT, 'widgets_1200x540.png'))
    sc_a = 1500 / wa.width; sc_b = 1500 / wb.width
    wa2 = wa.resize((1500, round(wa.height * sc_a)), Image.NEAREST); wb2 = wb.resize((1500, round(wb.height * sc_b)), Image.LANCZOS)
    sh2 = Image.new('RGB', (1500 + 2 * pad, wa2.height + wb2.height + 4 * pad + 2 * lab), (22, 24, 32)); d2 = ImageDraw.Draw(sh2)
    d2.text((pad, pad), 'widgets @400x240 (logic, phóng x1.875 để xem)', fill=(255, 210, 120)); sh2.paste(wa2, (pad, pad + lab))
    d2.text((pad, 2 * pad + lab + wa2.height), 'widgets @1200x540 (thu 1500/%d)' % wb.width, fill=(255, 210, 120)); sh2.paste(wb2, (pad, 2 * pad + 2 * lab + wa2.height))
    sh2.save(os.path.join(OUT, 'tong_hop_widgets.png'))
    import layout_doc
    layout_doc.write(OUT, metrics, FRAMES, bag_geometry, popup_geometry, ct, ZONE, item_name, LONG_NAME)

if __name__ == '__main__':
    main()
