#!/usr/bin/env python3
"""T17b2 lô b2c: mockup StateMainMenu (13 trạng thái ~ 8 bố cục, BAO-CAO §13 mục G): Logo/Tiêu đề, Menu chính (lưới 3x3 gốc 9 ô
+ bản 6 ô theo kế hoạch T7), Chọn Slot (+2 popup), Chọn lớp (+ popup mô tả lớp), Chuyên Gia (chọn chế độ), Cài đặt (gồm "Phân giải"
1/1..1/4 + ghi chú khởi động lại), Thành tựu, Trợ giúp (danh sách + nội dung), Hỗ trợ (thông tin thẩm định), popup thoát/chờ.

NỀN: MainMenu vẽ NỀN TOÀN MÀN (đen + dải dung nham `menu.mgr` #8 lật trên/dưới + logo `sp/imgcom/title.mgr`); mockup dùng đúng các ảnh thật đó.
Dùng lại viui.py + mock_menu.py + mock_b2a.py + mock_b2b.py (KHÔNG sửa ảnh đã có). CHẠY SAU mock_b2b.py:
    python3 -I mock_menu.py [OUT] && python3 -I mock_b2a.py [OUT] && python3 -I mock_b2b.py [OUT] && python3 -I mock_b2c.py [OUT]
Ghi ảnh b2c rồi GỘP (idempotent) vào hit_rects.json / verify.txt / LAYOUT.md (phần sau dấu <!-- B2C -->).
Chuỗi Việt, tên lớp, mô tả lớp, thành tựu, trợ giúp = dữ liệu THẬT ($WORK/vi_android); số mẫu: LV, thời gian chơi, điểm, giá trị cài đặt.
"""
import os, sys, json, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import viui as U
import mock_menu as M
import mock_b2a as B
import mock_b2b as B2
from mock_menu import C, Cv, metrics, FRAMES, MAIN, ct, content_rect, w_window, w_button, w_scrollbar, w_popup
from mock_b2a import w_panel, w_arrow, recs
from PIL import Image, ImageDraw
import mgr as MGR

OUT = sys.argv[1] if len(sys.argv) > 1 else os.path.dirname(os.path.abspath(__file__))

# ---------------------------------------------------------------- dữ liệu thật
MT = {ri: U.plain(t) for ri, o, t in U.dat_strings('c/csv/menu_text.dat')}
MLIST = {ri: U.plain(t) for ri, o, t in U.dat_strings('c/csv/mission_list.dat')}
MTEXT = {ri: U.plain(t) for ri, o, t in U.dat_strings('c/csv/mission_text.dat')}
HELPD = recs('c/csv2/help_lgt.dat')                      # ri -> [tiêu đề, nội dung]
CLS = [ct(178), ct(179), ct(180), ct(181)]               # Warrior, Rogue, Gunslinger, Knight
CLS_ICON = [29, 30, 31, 32]                              # menu.mgr (GIẢ ĐỊNH: 5 icon lớp nằm ở 28..32, thứ tự theo lớp)
CLS_SHORT = [MT[24], MT[25], MT[26], MT[27]]
CLS_LONG = [MT[20], MT[21], MT[22], MT[23]]
GRID_ICON = [19, 20, 21, 22, 23, 24, 25, 26, 27]        # menu.mgr: 9 icon ô lưới gốc (Bắt đầu .. Thoát)
GRID9 = [ct(i) for i in range(166, 175)]                 # Bắt đầu, Chuyên Gia, Vào kho đồ, Cập nhật, Cài đặt, Thành tựu, Trợ giúp, Cộng đồng, Thoát
GRID6 = [(0, 0), (1, 1), (4, 4), (5, 5), (6, 6), (8, 8)]  # (chỉ số trong lưới 9 gốc, chỉ số trong GRID9): bản 6 ô T7
NOTE_RESTART = 'Khởi động lại để áp dụng'                # chuỗi T10 (khối cuối vi.bin), BAO-CAO §11
OPT_LABELS = [ct(204), ct(205), ct(206), ct(207), ct(208), ct(209), ct(53)]   # 53 = "Phân giải" (T10 ghi vào rec 210)
SUBTITLE = ct(310).split(';')[1]                         # "Khế Ước Hắc Ám"

def paras(s):
    """chuỗi dữ liệu -> list đoạn (';;' = ngắt đoạn, ';' = dấu cách)"""
    return [p.replace(';', ' ').strip() for p in s.split(';;') if p.strip()]

def wrap_paras(px, ps, maxw):
    out = []
    for i, p in enumerate(ps):
        if i and out: out.append('')
        out += U.wrap(px, p, maxw)
    return out

# ---------------------------------------------------------------- nền thật
_tmgr = None
def title_img(i):
    global _tmgr
    if _tmgr is None: _tmgr = [MGR.decode(d)[2] for o, d in MGR.parse(U.vfs_get('c/sp/imgcom/title.mgr'))]
    return _tmgr[i]

def put(im, spr, x, y, k=1, alpha=None):
    if k != 1: spr = spr.resize((spr.width * k, spr.height * k), Image.NEAREST)
    if alpha is not None:
        spr = spr.copy(); a = spr.getchannel('A').point(lambda v: v * alpha // 255); spr.putalpha(a)
    im.alpha_composite(spr, (x, y)); return spr.size

_bg = {}
def mm_bg(m):
    """nền MainMenu: đen + dải dung nham (menu.mgr #8, x1 / x2 ở HD) lát ngang, lật dọc ở đáy (như ảnh gốc `shots/T10`)"""
    key = (m.W, m.H)
    if key not in _bg:
        im = Image.new('RGBA', (m.W, m.H), (0, 0, 0, 255)); lava = U.mgr_img('menu', 8)
        k = 2 if m.hd else 1; lw = lava.width * k
        x = (m.W - lw) // 2 - ((m.W - lw) // 2 // lw + 1) * lw
        flip = lava.transpose(Image.FLIP_TOP_BOTTOM)
        while x < m.W:
            put(im, lava, x, 0, k); put(im, flip, x, m.H - lava.height * k, k); x += lw
        _bg[key] = im
    return _bg[key].copy()

def mm_cv(m, dim=0):
    cv = Cv(m, bg=False); cv.im = mm_bg(m)
    if dim: cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, dim))
    return cv

def mmwin(m, title, dim=95):
    cv = mm_cv(m, dim); body = w_window(cv, title, tabs=None)
    return cv, content_rect(m, body)

def logo_k(m): return 1 if m.H < 300 else 2

def draw_logo(cv, cx, y, k, glow=True, sub=True):
    """logo thật: 'HEROES LORE' + 'V' (+ mắt dung nham sau lưng) + phụ đề (chữ, T11 vẽ lại ảnh); trả y đáy"""
    m = cv.m; hl = title_img(5); v = title_img(3); gap = 2 * k
    tw = (hl.width + v.width) * k + gap; x = cx - tw // 2
    if glow:
        eye = title_img(1); put(cv.im, eye, cx - eye.width * k // 2, y + (hl.height * k - eye.height * k) // 2 + 4 * k, k, alpha=150)
    put(cv.im, hl, x, y, k); put(cv.im, v, x + hl.width * k + gap, y + (hl.height - v.height) * k // 2, k)
    yb = y + hl.height * k
    if sub:
        yb += 2 * k; cv.text(cx, yb, SUBTITLE, m.px, C['gold'], shadow=True, anchor='c'); yb += m.lp
    return yb

def draw_copyright(cv, k):
    c = title_img(7); put(cv.im, c, (cv.m.W - c.width * k) // 2, cv.m.H - c.height * k - max(2, cv.m.mg // 2), k); return cv.m.H - c.height * k - max(2, cv.m.mg // 2)

# ---------------------------------------------------------------- Logo + Tiêu đề
def scr_logo(m):
    cv = mm_cv(m); k = 1 if m.H < 360 else 2
    b = U.mgr_img('menu', 12); put(cv.im, b, (m.W - b.width * k) // 2, (m.H - b.height * k) // 2, k)
    cv.hit('logo', (0, 0, m.W, m.H), 'chạm = bỏ qua logo (DrawLogo, StateMainMenu state 2)')
    return cv

def scr_title(m):
    cv = mm_cv(m); k = max(1, m.H // 180)
    yb = draw_logo(cv, m.W // 2, int(m.H * 0.16), k)
    tap = title_img(8); ty = max(yb + 6 * k, int(m.H * 0.66))
    put(cv.im, tap, (m.W - tap.width * k) // 2, ty, k)
    draw_copyright(cv, 1 if m.H < 360 else 2)
    cv.hit('tap', (0, 0, m.W, m.H), 'chạm bất kỳ = tiếp tục (DrawTitle: KeyPressed(-5)) -> state 4 menu chính; "Tap to Continue" là ẢNH (T11 vẽ lại tiếng Việt)')
    return cv

# ---------------------------------------------------------------- Menu chính (lưới)
def grid_geom(m, n):
    k = logo_k(m); hl = title_img(5)
    top = m.mg + hl.height * k + 2 * k + m.lp + m.gap
    cop = title_img(7).height * (1 if m.H < 360 else 2)
    bot = m.H - cop - max(2, m.mg // 2) - m.gap
    rows = n // 3; g = m.gap + 2
    ah = bot - top; th = (ah - (rows - 1) * g) // rows
    aw = m.W - 2 * m.mg; tw = (aw - 2 * g) // 3
    tw = min(tw, round(2.4 * th))
    gx = (m.W - (3 * tw + 2 * g)) // 2
    return dict(top=top, bot=bot, g=g, th=th, tw=tw, gx=gx, rows=rows, k=k)

def scr_grid(m, n=6, popup=None):
    cv = mm_cv(m); g = grid_geom(m, n); s = m.s; k = g['k']
    draw_logo(cv, m.W // 2, m.mg, k)
    draw_copyright(cv, 1 if m.H < 360 else 2)
    items = [(i, i) for i in range(9)] if n == 9 else GRID6
    ik = None
    for pos, (orig, li) in enumerate(items):
        c, rw = pos % 3, pos // 3
        x = g['gx'] + c * (g['tw'] + g['g']); y = g['top'] + rw * (g['th'] + g['g']); r = (x, y, x + g['tw'], y + g['th'])
        sel = pos == 0
        if sel: inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
        else: inner = cv.frame(r, rad=m.rad, orange=False); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255))
        label = GRID9[li]; ic = U.mgr_img('menu', GRID_ICON[li]); col = C['white'] if sel else (210, 220, 236, 255)
        stack_h = ic.height + m.px + 3 * s + 2                         # icon x1 + chữ
        kk = 1
        for cand in (min(m.s + 1, 3), 2, 1):
            if ic.height * cand + m.px + 3 * s + 4 <= g['th']: kk = cand; break
        if ic.height * kk + m.px + 3 * s + 4 <= g['th']:               # xếp dọc: icon trên, chữ dưới (như menu 3x3 trong game)
            cv.icon('menu', GRID_ICON[li], 0, 0, kk, tint=col if False else None, center=(x + g['tw'] // 2, y + (g['th'] - m.px - 2 * s) // 2 - 1))
            cv.text(x + g['tw'] // 2, y + g['th'] - m.px - 2 * s - 1, label, m.px, col, shadow=True, anchor='c', maxw=g['tw'] - 2 * s - 2)
        else:                                                           # ô thấp (9 ô ở 400x240): icon trái + chữ
            kk = max(1, min(m.s + 1, (g['th'] - 6) // ic.height))
            iw = ic.width * kk
            tw = U.sw(m.px, label); total = iw + m.pad + tw; x0 = x + (g['tw'] - total) // 2
            cv.icon('menu', GRID_ICON[li], x0, y + (g['th'] - ic.height * kk) // 2, kk)
            cv.text(x0 + iw + m.pad, y + (g['th'] - m.px) // 2, label, m.px, col, shadow=True, maxw=x + g['tw'] - 2 * s - 2 - (x0 + iw + m.pad))
        cv.hit('tile%d' % pos, r, 'đặt con trỏ lưới gốc = %d (%s) rồi KeyPressed(-5) (StateMainMenu state 4, DrawMainMenu/KeyMainMenu)' % (orig, label))
    if popup == 'thoat':
        w_popup(cv, ct(203), yes_no=True)
    elif popup == 'cho':
        w_wait(cv, ct(202))
    return cv

def w_wait(cv, text_s):
    """popup chờ (không nút): dùng cho state 0/1/7/8 mạng (CheckEvent/UserLogReg/Network/Update) nếu còn hiện"""
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw = round(0.70 * m.W) if not m.hd else 480; pw = min(pw, m.W - 2 * m.mg); tw = pw - 2 * (m.pad + 2 * s + 4)
    ls = U.wrap(m.px, text_s, tw); ph = 2 * m.pad + 4 * s + len(ls) * m.lp + m.pad + 2 * s + 4
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248)); y = inner[1] + m.pad + 2 * s
    for l in ls: cv.text(m.W // 2, y, l, m.px, C['white'], anchor='c', maxw=tw); y += m.lp
    y += m.pad // 2
    d = 2 * s + 2; n = 8; tot = n * d + (n - 1) * s; x = (m.W - tot) // 2
    for i in range(n): cv.rect((x + i * (d + s), y, x + i * (d + s) + d, y + d), C['orange'] if i < 5 else (48, 60, 82, 255))
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'popup chờ: chặn chạm')

# ---------------------------------------------------------------- Chọn Slot
SLOTS = [dict(cls=0, lv=48, t='12:34:56', area=None), dict(cls=1, lv=12, t='03:21:09', area=None), None]

def slot_geom(m, rect):
    x0, y0, x1, y1 = rect; n = 3; g = m.gap; rh = (y1 - y0 - (n - 1) * g) // n
    return rh, g

def scr_slot(m, popup=None):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(175)); s = m.s
    rh, g = slot_geom(m, (x0, y0, x1, y1)); sel = 2 if popup == 'moi' else 0
    if popup == 'xoa': sel = 1
    for i, sd in enumerate(SLOTS):
        r = (x0, y0 + i * (rh + g), x1, y0 + i * (rh + g) + rh)
        if i == sel:
            inner = cv.frame(r, rad=m.rad, fill=None); cv.vgrad(inner, (66, 44, 20, 255), (46, 30, 14, 255))
        else: w_panel(cv, r)
        pr = min(rh - 2 * (m.bt + 3), 22 * s + 20 if not m.hd else 84)
        px_ = x0 + m.bt + 3 + 1; py = r[1] + (rh - pr) // 2
        if sd is None:
            B.w_portrait(cv, px_, py, pr, icon=('menu', 6), k=1) if False else None
            ring = Image.open(os.path.join(U.REPO, 'ui_new/03_frame_empty.png')).convert('RGBA').resize((pr, pr), Image.LANCZOS); cv.im.alpha_composite(ring, (px_, py))
            tx = px_ + pr + m.pad
            cv.text(tx, r[1] + (rh - 2 * m.lp) // 2, ct(182), m.px, C['dim'], maxw=x1 - tx - m.pad)
            cv.text(tx, r[1] + (rh - 2 * m.lp) // 2 + m.lp, ct(200), m.px, C['dim'], maxw=x1 - tx - m.pad)
            cv.hit('slot%d' % i, r, 'đặt con trỏ slot=%d + KeyPressed(-5): slot trống -> popup tạo nhân vật mới (NewGame) -> chọn lớp' % i)
        else:
            B.w_portrait(cv, px_, py, pr, icon=('menu', CLS_ICON[sd['cls']]), k=max(1, round(pr * 0.5 / 16)))
            dw = U.sw(m.px, U.strip_hint(ct(194))) + 4 * m.pad; bh = m.btnH
            bx0 = x1 - m.bt - 3 - dw; by0 = r[1] + (rh - bh) // 2
            tx = px_ + pr + m.pad; mw = bx0 - tx - m.pad
            ty = r[1] + (rh - 3 * m.lp) // 2
            cv.text(tx, ty, ct(9), m.px, C['gold'], shadow=True, maxw=mw)
            cv.text(tx, ty + m.lp, '%s  %s %d' % (CLS[sd['cls']], ct(13), sd['lv']), m.px, C['white'], maxw=mw)
            cv.text(tx, ty + 2 * m.lp, '%s %s' % (ct(183), sd['t']), m.px, C['dim'], maxw=mw)
            w_button(cv, (bx0, by0, bx0 + dw, by0 + bh), U.strip_hint(ct(194)), 'normal', 'del%d' % i, 'phím * (%s): popup xoá slot %d (193) -> Có = xoá dữ liệu slot' % (ct(194), i))
            cv.hit('slot%d' % i, (r[0], r[1], bx0 - 2, r[3]), 'đặt con trỏ slot=%d + KeyPressed(-5): slot có dữ liệu -> ContinueGame (LoadSlotData)' % i)
    if popup == 'moi': w_popup(cv, ct(177), yes_no=True)
    elif popup == 'xoa': w_popup(cv, ct(193), yes_no=True)
    return cv

# ---------------------------------------------------------------- Chọn lớp
def cls_geom(m, rect):
    x0, y0, x1, y1 = rect; cw = x1 - x0; g = m.gap + 2
    lw = round(cw * 0.48); ht = (y1 - y0 - g) // 2; tw = (lw - g) // 2
    rx0 = x0 + lw + g
    return dict(lw=lw, ht=ht, tw=tw, g=g, rx0=rx0)

def scr_class(m):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(176)); s = m.s; q = cls_geom(m, (x0, y0, x1, y1)); sel = 0
    k = m.s + 2
    for i in range(4):
        c, rw = i % 2, i // 2; x = x0 + c * (q['tw'] + q['g']); y = y0 + rw * (q['ht'] + q['g']); r = (x, y, x + q['tw'], y + q['ht'])
        if i == sel: inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
        else: inner = cv.frame(r, rad=m.rad, orange=False); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255))
        ic = U.mgr_img('menu', CLS_ICON[i]); kk = max(1, min(k, (q['ht'] - m.px - 3 * s - 8) // ic.height))
        cv.icon('menu', CLS_ICON[i], 0, 0, kk, center=(x + q['tw'] // 2, y + (q['ht'] - m.px - 2 * s) // 2 - 1))
        cv.text(x + q['tw'] // 2, y + q['ht'] - m.px - 2 * s - 2, CLS[i], m.px, C['white'], shadow=True, anchor='c', maxw=q['tw'] - 2 * s - 4)
        cv.hit('class%d' % i, r, 'đặt con trỏ lớp (2x2 gốc)=%d; chạm lần 2 hoặc OK = KeyPressed(-5) -> popup mô tả lớp' % i)
    rx0 = q['rx0']; r = (rx0, y0, x1, y1); w_panel(cv, r); p = m.pad // 2 + 2
    ix0, ix1 = rx0 + 3 + p, x1 - 3 - p
    cv.text(ix0, y0 + 3 + p, CLS[sel], m.px, C['gold'], shadow=True, maxw=ix1 - ix0)
    yy = y0 + 3 + p + m.lp + 2; cv.rect((ix0, yy, ix1, yy + 1), C['line']); yy += 4
    by = y1 - 3 - p - m.btnH
    ls = wrap_paras(m.px, paras(CLS_SHORT[sel]), ix1 - ix0 - m.sbw - 2); view = max(1, (by - m.gap - yy) // m.lp)
    for i, l in enumerate(ls[:view]): cv.text(ix0, yy + i * m.lp, l, m.px, C['white'])
    if len(ls) > view: w_scrollbar(cv, ix1 - m.sbw, yy, by - m.gap, 0, len(ls), view)
    w_button(cv, (ix0, by, ix1, by + m.btnH), ct(56), 'primary', 'ok', 'KeyPressed(-5) -> popup mô tả lớp (StatePopup) rồi OK = NewGame(lớp)')
    return cv

def popup_class_geom(m, i):
    s = m.s; pw = min(m.W - 2 * m.mg, round(0.82 * m.W) if not m.hd else 640)
    iw = pw - 2 * (m.pad + 2 * s + 4); tw = iw - m.sbw - 3
    ls = wrap_paras(m.px, paras(CLS_LONG[i]), tw)
    pr = 2 * m.lp + 8
    fixed = 2 * m.pad + 4 * s + pr + m.pad + m.pad + m.btnH + 2 + 4
    maxview = (m.H - 2 * m.mg - fixed) // m.lp
    view = min(len(ls), maxview)
    return pw, iw, tw, ls, pr, view, fixed + view * m.lp

def scr_class_desc(m):
    cv = scr_class(m); s = m.s; i = 0
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw, iw, tw, ls, pr, view, ph = popup_class_geom(m, i)
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248))
    ix0 = inner[0] + m.pad + 2 * s; ix1 = inner[2] - m.pad - 2 * s
    y = inner[1] + m.pad + 2 * s
    B.w_portrait(cv, ix0, y, pr, icon=('menu', CLS_ICON[i]), k=max(1, round(pr * 0.5 / 16)))
    cv.text(ix0 + pr + m.pad, y + (pr - m.px) // 2, CLS[i], m.px, C['gold'], shadow=True, maxw=ix1 - ix0 - pr - m.pad)
    y += pr + m.pad // 2 + 1
    box = (ix0, y, ix1, y + view * m.lp + 4); w_panel(cv, box, fill=(8, 14, 24, 240))
    for k_, l in enumerate(ls[:view]): cv.text(ix0 + 4, y + 2 + k_ * m.lp, l, m.px, C['white'])
    if len(ls) > view: w_scrollbar(cv, ix1 - m.sbw - 2, y + 2, y + 2 + view * m.lp, 0, len(ls), view)
    cv.hit('desc_scroll', box, 'kéo dọc = cuộn mô tả (TouchMoved mới); mô tả vẽ trong hộp CÓ CLIP (sửa lỗi tràn chữ ở DrawStart 0x11d390)')
    by = inner[3] - m.pad - m.btnH; bx0 = ix0; bx1 = ix1; mid = (bx0 + bx1) // 2; gg = m.pad
    w_button(cv, (bx0, by, mid - gg // 2, by + m.btnH), ct(56), 'primary', 'popup_ok', 'KeyPressed(-5) = NewGame với lớp đã chọn')
    w_button(cv, (mid + gg // 2 + 1, by, bx1, by + m.btnH), ct(57), 'normal', 'popup_cancel', 'huỷ: về chọn lớp')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua')
    return cv

# ---------------------------------------------------------------- Chuyên Gia: chọn chế độ
EXP_P = paras(HELPD[15][1])           # [Chế độ thưởng., Cầm Cự: ..., Đua Thời Gian: ..., Đột Phá: ..., ghi chú Expert]
def scr_expert(m):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(167)); s = m.s; cw = x1 - x0; g = m.gap + 2
    lw = round(cw * 0.40); sel = 0
    names = [ct(196), ct(197), ct(198)]; n = 3
    bh = (y1 - y0 - (n - 1) * g) // n; bh = min(bh, 3 * m.btnH)
    ty = y0 + ((y1 - y0) - (n * bh + (n - 1) * g)) // 2
    for i, nme in enumerate(names):
        r = (x0, ty + i * (bh + g), x0 + lw, ty + i * (bh + g) + bh)
        w_button(cv, r, nme, 'primary' if i == sel else 'normal', 'mode%d' % i, 'đặt con trỏ chế độ=%d + KeyPressed(-5) (DrawExpert/KeyExpert gốc)' % i)
    rx0 = x0 + lw + g; r = (rx0, y0, x1, y1); w_panel(cv, r); p = m.pad // 2 + 2
    ix0, ix1 = rx0 + 3 + p, x1 - 3 - p
    cv.text(ix0, y0 + 3 + p, names[sel], m.px, C['gold'], shadow=True, maxw=ix1 - ix0)
    yy = y0 + 3 + p + m.lp + 2; cv.rect((ix0, yy, ix1, yy + 1), C['line']); yy += 4
    by = y1 - 3 - p - m.btnH
    body = EXP_P[1].split(':', 1)[1].strip()
    ls = U.wrap(m.px, body, ix1 - ix0 - m.sbw - 2) + [''] + U.wrap(m.spx if False else m.px, EXP_P[4], ix1 - ix0 - m.sbw - 2)
    view = max(1, (by - m.gap - yy) // m.lp)
    for i, l in enumerate(ls[:view]): cv.text(ix0, yy + i * m.lp, l, m.px, C['white'] if i < len(ls) - len(U.wrap(m.px, EXP_P[4], ix1 - ix0 - m.sbw - 2)) else C['dim'])
    if len(ls) > view: w_scrollbar(cv, ix1 - m.sbw, yy, by - m.gap, 0, len(ls), view)
    cv.hit('desc_scroll', (ix0, yy, ix1, by - m.gap), 'kéo dọc = cuộn mô tả')
    w_button(cv, (ix0, by, ix1, by + m.btnH), ct(166), 'primary', 'start', 'KeyPressed(-5) -> StateInExpertMenu (màn chuẩn bị, b2b). Bỏ "Xem bảng xếp hạng" (199, cần mạng)')
    return cv

# ---------------------------------------------------------------- Cài đặt
def opt_geom(m, rect):
    x0, y0, x1, y1 = rect; n = 7; g = 2
    note_h = m.slp + 2
    rh = min(m.lp + 10, (y1 - y0 - note_h - 2 - (n - 1) * g) // n)
    return dict(n=n, g=g, rh=rh, note_h=note_h)

def seg_chip_w(m, labels): return max(U.sw(m.px, l) for l in labels) + 2 * m.pad

def w_opt_row(cv, x0, y, x1, rh, label, kind, val, sel, hid, act):
    m = cv.m
    r = (x0, y, x1, y + rh)
    if sel:
        cv.rect(r, C['ink']); cv.vgrad((x0 + 1, y + 1, x1 - 1, y + rh - 1), (250, 160, 36, 255), (196, 96, 12, 255)); col = C['white']
    else:
        cv.rect(r, C['ink']); cv.vgrad((x0 + 1, y + 1, x1 - 1, y + rh - 1), C['cell'], C['cell2']); col = C['white']
    ch = rh - 4; cy = y + 2
    if kind == 'step':                        # ◄ giá trị ►
        vw = max(U.sw(m.px, v) for v in val[1]) + 2 * m.pad
        rx = x1 - m.pad - ch; lx = rx - vw - ch
        w_arrow(cv, (lx, cy, lx + ch, cy + ch), 'l', True, hid + '_l', 'KeyPressed(TRÁI) của mục: giảm giá trị')
        w_arrow(cv, (rx, cy, rx + ch, cy + ch), 'r', True, hid + '_r', 'KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4)')
        cv.text(lx + ch + vw // 2, y + (rh - m.px) // 2, val[0], m.px, C['gold'] if not sel else C['white'], shadow=sel, anchor='c', maxw=vw)
        used = x1 - lx + m.pad
    else:                                     # chip phân đoạn: [1/1][1/2][1/3][1/4] hoặc [BẬT|TẮT]
        labels, cur = val; cw = seg_chip_w(m, labels); gx = 2
        tot = len(labels) * cw + (len(labels) - 1) * gx; sx = x1 - m.pad - tot
        for i, l in enumerate(labels):
            rr = (sx + i * (cw + gx), cy, sx + i * (cw + gx) + cw, cy + ch)
            if i == cur: inner = cv.frame(rr, rad=m.rad, thick=1); cv.vgrad(inner, (252, 190, 60, 255), (214, 118, 14, 255)); tc = C['white']; sh = True
            else: inner = cv.frame(rr, rad=m.rad, orange=False, thick=1); cv.vgrad(inner, (28, 40, 58, 255), (18, 26, 40, 255)); tc = (170, 184, 204, 255); sh = False
            cv.text((rr[0] + rr[2]) // 2, y + (rh - m.px) // 2, l, m.px, tc, shadow=sh, anchor='c', maxw=cw)
            cv.hit('%s_%d' % (hid, i), rr, 'chạm = đặt giá trị %s (lặp phím PHẢI tới khi khớp)' % l)
        used = tot + 2 * m.pad
    cv.text(x0 + m.pad, y + (rh - m.px) // 2, label, m.px, col, shadow=sel, maxw=x1 - x0 - used - m.pad)
    cv.hit(hid, (x0, y, x1 - used, y + rh), 'đặt con trỏ mục=%s (KeyPressed(-5) = đổi giá trị với mục bật/tắt)' % hid)

def scr_option(m, sel=0, k=2):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(170)); q = opt_geom(m, (x0, y0, x1, y1))
    vals = [('step', ('3', ['0', '1', '2', '3', '4', '5'])), ('step', ('3', ['0', '1', '2', '3', '4', '5'])), ('step', (ct(322), [ct(321), ct(322), ct(323)])),
            ('seg', ([ct(191), ct(190)], 0)), ('step', (ct(321), [ct(321), ct(322), ct(323)])), ('seg', ([ct(191), ct(190)], 1)),
            ('seg', (['1/1', '1/2', '1/3', '1/4'], k - 1))]
    y = y0
    for i in range(q['n']):
        kind, v = vals[i]
        w_opt_row(cv, x0, y, x1, q['rh'], OPT_LABELS[i], kind, v, i == sel, 'opt%d' % i, '')
        y += q['rh'] + q['g']
    cv.text(x0 + m.pad, y + 1, NOTE_RESTART, m.spx, C['gold'] if sel == 6 else C['dim'], maxw=x1 - x0 - 2 * m.pad)
    cv.hit('note_restart', (x0, y, x1, y + q['note_h']), 'chỉ hiển thị: đổi Phân giải ghi hd_scale.txt, có hiệu lực SAU khi khởi động lại')
    return cv

# ---------------------------------------------------------------- Thành tựu
def ach_geom(m, rect):
    x0, y0, x1, y1 = rect; cw = x1 - x0; g = m.gap + 2
    strip = m.lp + 2 * m.s + 2
    lw = round(cw * 0.56); rh = 2 * m.lp + 4; top = y0 + strip + m.gap
    n = max(1, (y1 - top + 2) // (rh + 2))
    return dict(lw=lw, rh=rh, top=top, n=n, g=g, strip=strip, sbx=x0 + lw - m.sbw, tw=lw - m.sbw - 2 - 2 * m.pad - 12 - 2 * m.s)

def scr_achieve(m, sel=3):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(171)); s = m.s; q = ach_geom(m, (x0, y0, x1, y1))
    done = {0, 1, 2, 3, 5, 8, 9}
    B2.strip(cv, x0, y0, x1, ct(14), '%d/%d' % (2150, 5000), coin=False)
    y = q['top']
    for i in range(q['n']):
        r = (x0, y, x0 + q['lw'] - m.sbw - 2, y + q['rh'])
        if i == sel: cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), (250, 160, 36, 255), (196, 96, 12, 255))
        else: cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), C['cell'], C['cell2'])
        mk = 6 + 2 * s; mx = r[0] + m.pad - 1; my = r[1] + (q['rh'] - mk) // 2
        cv.rect((mx, my, mx + mk, my + mk), C['ink']); cv.rect((mx + 1, my + 1, mx + mk - 1, my + mk - 1), C['green'] if i in done else (40, 54, 76, 255))
        ls = U.wrap(m.px, MLIST[i], q['tw']); assert len(ls) <= 2, ('tên thành tựu > 2 dòng', MLIST[i])
        ty = r[1] + (q['rh'] - len(ls) * m.lp) // 2 + 1
        for k_, l in enumerate(ls): cv.text(mx + mk + m.pad - 1, ty + k_ * m.lp, l, m.px, C['white'], shadow=(i == sel))
        cv.hit('row%d' % i, r, 'đặt con trỏ thành tựu=%d (DrawMission/KeyMission); chạm hàng đã chọn = không có hành động (chỉ xem)' % i)
        y += q['rh'] + 2
    w_scrollbar(cv, q['sbx'], q['top'], y - 2, 0, len(MLIST), q['n'])
    cv.hit('list_scroll', (x0, q['top'], x0 + q['lw'], y - 2), 'kéo dọc/mũi tên: cuộn danh sách (con trỏ gốc lên/xuống)')
    rx0 = x0 + q['lw'] + q['g']; r = (rx0, q['top'], x1, y1); w_panel(cv, r); p = m.pad // 2 + 2
    ix0, ix1 = rx0 + 3 + p, x1 - 3 - p
    yy = q['top'] + 3 + p
    nl = U.wrap(m.px, MLIST[sel], ix1 - ix0); assert len(nl) <= 3, ('tên chi tiết > 3 dòng', MLIST[sel])
    for l in nl: cv.text(ix0, yy, l, m.px, C['gold'], shadow=True, maxw=ix1 - ix0); yy += m.lp
    st = MT[44] if sel in done else ct(190)
    cv.text(ix0, yy, st if sel in done else '-', m.spx, C['green'] if sel in done else C['dim'], maxw=ix1 - ix0); yy += m.slp + 1
    cv.rect((ix0, yy, ix1, yy + 1), C['line']); yy += 4
    ls = U.wrap(m.px, MTEXT[sel], ix1 - ix0 - m.sbw - 2); view = max(1, (y1 - 3 - p - yy) // m.lp)
    for i, l in enumerate(ls[:view]): cv.text(ix0, yy + i * m.lp, l, m.px, C['white'])
    if len(ls) > view: w_scrollbar(cv, ix1 - m.sbw, yy, y1 - 3 - p, 0, len(ls), view)
    cv.hit('desc_scroll', (ix0, yy, ix1, y1 - 3 - p), 'kéo dọc = cuộn mô tả')
    return cv

# ---------------------------------------------------------------- Trợ giúp
HELP_N = len(HELPD)
def help_geom(m, rect):
    x0, y0, x1, y1 = rect; rh = m.lp + 8; g = 2
    n = max(1, (y1 - y0 + g) // (rh + g))
    return dict(rh=rh, g=g, n=n, tw=(x1 - x0) - m.sbw - 2 - 2 * m.pad)

def scr_help(m, sel=2):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(172)); q = help_geom(m, (x0, y0, x1, y1)); y = y0
    first = 0
    for i in range(first, first + q['n']):
        r = (x0, y, x1 - m.sbw - 2, y + q['rh'])
        w_row_h(cv, r, HELPD[i][0], i == sel)
        cv.hit('row%d' % i, r, 'đặt con trỏ trợ giúp=%d + KeyPressed(-5) -> trang nội dung (DrawHelp)' % i); y += q['rh'] + q['g']
    w_scrollbar(cv, x1 - m.sbw, y0, y - q['g'], first, HELP_N, q['n'])
    cv.hit('list_scroll', (x0, y0, x1, y - q['g']), 'kéo dọc/mũi tên: cuộn danh sách')
    return cv

def w_row_h(cv, r, label, selected):
    m = cv.m; x0, y0, x1, y1 = r
    cv.rect(r, C['ink'])
    if selected: cv.vgrad((x0 + 1, y0 + 1, x1 - 1, y1 - 1), (250, 160, 36, 255), (196, 96, 12, 255))
    else: cv.vgrad((x0 + 1, y0 + 1, x1 - 1, y1 - 1), C['cell'], C['cell2'])
    cv.text(x0 + m.pad, y0 + (y1 - y0 - m.px) // 2, label, m.px, C['white'], shadow=selected, maxw=x1 - x0 - 2 * m.pad)

def help_page_geom(m, rect):
    x0, y0, x1, y1 = rect; by = y1 - m.btnH
    return dict(by=by, tw=(x1 - x0) - 2 * (m.pad // 2 + 4) - m.sbw - 2)

def scr_help_page(m, i=13):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(172)); q = help_page_geom(m, (x0, y0, x1, y1)); p = m.pad // 2 + 2
    box = (x0, y0, x1, q['by'] - m.gap); w_panel(cv, box, fill=(10, 16, 26, 235))
    cv.text(x0 + 3 + p, y0 + 3 + p // 2, HELPD[i][0], m.px, C['gold'], shadow=True, maxw=x1 - x0 - 6 - 2 * p)
    yy = y0 + 3 + p // 2 + m.lp + 2; cv.rect((x0 + 3 + p, yy, x1 - 3 - p, yy + 1), C['line']); yy += 4
    ls = wrap_paras(m.px, paras(HELPD[i][1]), q['tw']); view = max(1, (box[3] - 3 - yy) // m.lp)
    for k, l in enumerate(ls[:view]): cv.text(x0 + 3 + p, yy + k * m.lp, l, m.px, C['white'])
    if len(ls) > view: w_scrollbar(cv, x1 - 3 - p - m.sbw, yy, box[3] - 3, 0, len(ls), view)
    cv.hit('page_scroll', (x0, yy, x1, box[3] - 3), 'kéo dọc = cuộn trang (TouchMoved mới)')
    hw = (x1 - x0 - 2 * m.gap - U.sw(m.px, '%d/%d' % (HELP_N, HELP_N)) - 2 * m.pad) // 2
    w_button(cv, (x0, q['by'], x0 + hw, q['by'] + m.btnH), ct(211), 'normal', 'prev', 'KeyPressed(TRÁI): trang trước (DrawHelp)')
    w_button(cv, (x1 - hw, q['by'], x1, q['by'] + m.btnH), ct(212), 'primary', 'next', 'KeyPressed(PHẢI): trang sau')
    cv.text((x0 + hw + x1 - hw) // 2, q['by'] + (m.btnH - m.px) // 2, '%d/%d' % (i + 1, HELP_N), m.px, C['dim'], anchor='c')
    return cv

# ---------------------------------------------------------------- Hỗ trợ: thông tin thẩm định
INFO = [(309, 310), (311, 312), (313, 314), (315, 316), (317, 318), (319, 320)]
def info_geom(m, rect):
    x0, y0, x1, y1 = rect; k = 1 if m.H < 360 else 2
    b = U.mgr_img('menu', 12); bw = b.width * k
    # huy hiệu 12+ (ảnh gốc 103x121) co theo chiều cao khả dụng
    while k > 1 and b.height * k > y1 - y0: k -= 1
    bw = b.width * k
    return dict(k=k, bw=bw, rx0=x0 + bw + m.gap + 2)

def scr_support(m):
    cv, (x0, y0, x1, y1) = mmwin(m, ct(173)); q = info_geom(m, (x0, y0, x1, y1)); b = U.mgr_img('menu', 12); k = q['k']
    put(cv.im, b, x0, y0 + (y1 - y0 - b.height * k) // 2, k)
    r = (q['rx0'], y0, x1, y1); w_panel(cv, r); p = m.pad // 2 + 2; ix0, ix1 = q['rx0'] + 3 + p, x1 - 3 - p
    rows = []
    for a, bb in INFO:
        vs = U.wrap(m.px, ct(bb).replace(';', ' '), ix1 - ix0 - U.sw(m.px, ct(a)) - 2 * m.pad)
        rows.append((ct(a), vs))
    tot = sum(max(1, len(v)) for _, v in rows) * m.lp + len(rows) * 4
    yy = y0 + (y1 - y0 - tot) // 2
    for lab, vs in rows:
        cv.text(ix0, yy, lab, m.px, C['dim'])
        for l in vs: cv.text(ix1, yy, l, m.px, C['white'], anchor='r'); yy += m.lp
        yy += 2; cv.rect((ix0, yy, ix1, yy + 1), (40, 54, 76, 255)); yy += 2
    assert yy <= y1, ('thẩm định tràn', yy, y1)
    return cv

# ---------------------------------------------------------------- danh sách màn
SCREENS = [
    ('menu_chinh_logo', scr_logo, 'MainMenu state 2 (DrawLogo): huy hiệu 12+ trên nền dung nham'),
    ('menu_chinh_tieu_de', scr_title, 'MainMenu state 3 (DrawTitle): logo + "Tap to Continue" (ảnh)'),
    ('menu_chinh_luoi6', lambda m: scr_grid(m, 6), 'MainMenu state 4: menu chính BẢN 6 Ô (theo kế hoạch T7)'),
    ('menu_chinh_luoi9', lambda m: scr_grid(m, 9), 'MainMenu state 4: menu chính lưới 3x3 GỐC (9 ô, trước T7)'),
    ('menu_chinh_thoat', lambda m: scr_grid(m, 6, 'thoat'), 'MainMenu popup: "Bạn có muốn thoát?" (Có/Không)'),
    ('menu_chinh_cho', lambda m: scr_grid(m, 6, 'cho'), 'MainMenu state 0/1/7/8 (mạng): popup chờ (Đang kiểm tra cập nhật)'),
    ('menu_chinh_slot', scr_slot, 'MainMenu state 5 (DrawStart): Chọn Slot, 3 slot (2 có dữ liệu, 1 trống)'),
    ('menu_chinh_slot_moi', lambda m: scr_slot(m, 'moi'), 'Chọn Slot: popup tạo nhân vật mới (Có/Không)'),
    ('menu_chinh_slot_xoa', lambda m: scr_slot(m, 'xoa'), 'Chọn Slot: popup xoá slot (Có/Không)'),
    ('menu_chinh_lop', scr_class, 'MainMenu state 5 (DrawStart): Chọn lớp nhân vật (lưới 2x2 gốc + mô tả ngắn)'),
    ('menu_chinh_lop_mo_ta', scr_class_desc, 'Chọn lớp: popup mô tả lớp đầy đủ (hộp cuộn có clip) + OK/Huỷ'),
    ('menu_chinh_chuyen_gia', scr_expert, 'MainMenu state 6 (DrawExpert): chọn chế độ Chuyên Gia'),
    ('menu_chinh_cai_dat', lambda m: scr_option(m, 0, 2), 'MainMenu state 9 (DrawOption): Cài đặt, 7 mục'),
    ('menu_chinh_cai_dat_phan_giai', lambda m: scr_option(m, 6, 3), 'Cài đặt: mục Phân giải 1/1..1/4 (đang chọn 1/3) + ghi chú khởi động lại'),
    ('menu_chinh_thanh_tuu', scr_achieve, 'MainMenu state 10 (DrawMission): Thành tựu (danh sách + chi tiết)'),
    ('menu_chinh_tro_giup', scr_help, 'MainMenu state 11 (DrawHelp): Trợ giúp, danh sách 20 bài'),
    ('menu_chinh_tro_giup_noi_dung', scr_help_page, 'MainMenu state 11: trang nội dung Trợ giúp (bài dài nhất) + Trước/Tiếp'),
    ('menu_chinh_ho_tro', scr_support, 'MainMenu state 12: Hỗ trợ / thông tin thẩm định game'),
]

def render_all(out):
    os.makedirs(os.path.join(out, 'trung_gian'), exist_ok=True)
    hits = {}; tiles = {}; errors = []
    for name, fn, lab in SCREENS:
        for (W, H) in FRAMES:
            m = metrics(W, H)
            try: cv = fn(m)
            except AssertionError as e:
                errors.append('%s %dx%d: %s' % (name, W, H, e)); continue
            im = cv.im.convert('RGB')
            if (W, H) in MAIN: M.save_pair(cv, name, m, out)
            else: im.save(os.path.join(out, 'trung_gian', '%s_%dx%d.png' % (name, W, H)))
            hits['%s_%dx%d' % (name, W, H)] = cv.hits; tiles[(name, W)] = im
    return hits, tiles, errors

# ---------------------------------------------------------------- kiểm không tràn trên dữ liệu thật
def verify_b2c(errors):
    L = []; ok = lambda b: 'ĐẠT' if b else 'TRÀN'
    for (W, H) in FRAMES:
        m = metrics(W, H); s = m.s
        body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH); rect = content_rect(m, body); x0, y0, x1, y1 = rect; cw = x1 - x0
        L.append('== %dx%d  font %dpx / nhỏ %dpx, icon x%d ==' % (W, H, m.px, m.spx, m.s))
        # tiêu đề cửa sổ
        tmax = m.winW - 2 * m.bt - 4 - 2 * (m.titleH + m.pad)
        titles = [ct(i) for i in (167, 170, 171, 172, 173, 175, 176)]
        wt = max(titles, key=lambda t: U.sw(m.px, t))
        L.append('  tiêu đề cửa sổ (%d): rộng nhất %d px "%s" <= %d px: %s' % (len(titles), U.sw(m.px, wt), wt, tmax, ok(U.sw(m.px, wt) <= tmax)))
        # lưới menu chính
        for n in (6, 9):
            g = grid_geom(m, n); lab = max(GRID9, key=lambda t: U.sw(m.px, t))
            ic = U.mgr_img('menu', 19); stack = any(ic.height * c + m.px + 3 * s + 4 <= g['th'] for c in (min(m.s + 1, 3), 2, 1))
            kk = 1
            for c in (min(m.s + 1, 3), 2, 1):
                if ic.height * c + m.px + 3 * s + 4 <= g['th']: kk = c; break
            avail = g['tw'] - 2 * s - 2 - (0 if stack else ic.width * max(1, min(m.s + 1, (g['th'] - 6) // ic.height)) + m.pad + 2)
            L.append('  lưới menu chính %d ô: ô %dx%d px (%s); nhãn dài nhất %d px "%s" <= %d px (chừa %d): %s' % (
                n, g['tw'], g['th'], 'icon trên chữ dưới' if stack else 'icon trái chữ phải', U.sw(m.px, lab), lab, avail, avail - U.sw(m.px, lab), ok(U.sw(m.px, lab) <= avail)))
        gt_ = grid_geom(m, 9)
        L.append('  lưới 9 ô: vùng lưới y=%d..%d (cao %d), logo x%d + phụ đề: ĐẠT' % (gt_['top'], gt_['bot'], gt_['bot'] - gt_['top'], gt_['k']))
        # slot
        rh, g_ = slot_geom(m, rect); pr = min(rh - 2 * (m.bt + 3), 22 * s + 20 if not m.hd else 84)
        dw = U.sw(m.px, U.strip_hint(ct(194))) + 4 * m.pad; mw = cw - (m.bt + 4 + pr + m.pad) - dw - m.bt - 3 - m.pad * 2
        sl = max(['%s  %s 888' % (c, ct(13)) for c in CLS] + ['%s 123:59:59' % ct(183)], key=lambda t: U.sw(m.px, t))
        L.append('  slot: hàng cao %d, chữ dài nhất %d px "%s" <= %d px: %s; 3 dòng x %d = %d <= %d: %s' % (
            rh, U.sw(m.px, sl), sl, mw, ok(U.sw(m.px, sl) <= mw), m.lp, 3 * m.lp, rh - 2, ok(3 * m.lp <= rh - 2)))
        # chọn lớp
        q = cls_geom(m, rect); cn = max(CLS, key=lambda t: U.sw(m.px, t))
        L.append('  chọn lớp: ô %dx%d, tên lớp dài nhất %d px "%s" <= %d px: %s' % (q['tw'], q['ht'], U.sw(m.px, cn), cn, q['tw'] - 2 * s - 4, ok(U.sw(m.px, cn) <= q['tw'] - 2 * s - 4)))
        p = m.pad // 2 + 2; pw_ = x1 - (q['rx0']) - 6 - 2 * p - m.sbw - 2; yy = m.lp + 2 * p + 8
        by = (y1 - y0) - 3 - p - m.btnH; view = max(1, (by - m.gap - (3 + p + m.lp + 2 + 4)) // m.lp)
        sl_ = [len(wrap_paras(m.px, paras(t), pw_)) for t in CLS_SHORT]
        L.append('  mô tả ngắn lớp (4): số dòng %s, hộp hiện %d dòng: %s' % (sl_, view, 'vừa, không cần cuộn' if max(sl_) <= view else 'cuộn (thanh cuộn), không cắt'))
        # popup mô tả lớp (lỗi tràn người dùng đã thấy)
        res = []
        for i in range(4):
            pw, iw, tw, ls, pr_, view2, ph = popup_class_geom(m, i); res.append((len(ls), view2, ph))
        worst = max(res, key=lambda t: t[2])
        fitall = all(a == b for a, b, c in res)
        L.append('  popup mô tả lớp (4 chuỗi dài nhất menu_text 20-23): số dòng %s, hiện %s, cao popup lớn nhất %d <= khung %d: %s; %s' % (
            [a for a, b, c in res], [b for a, b, c in res], worst[2], H - 2 * m.mg, ok(worst[2] <= H - 2 * m.mg),
            'MỌI mô tả hiện đủ, không cuộn, không tràn' if fitall else 'có mô tả dài hơn hộp -> cuộn (thanh cuộn + clip)'))
        # chuyên gia
        lw = round(cw * 0.40); g2 = m.gap + 2; ix = cw - lw - g2 - 6 - 2 * p - m.sbw - 2
        modes = [len(U.wrap(m.px, EXP_P[i].split(':', 1)[1].strip(), ix)) for i in (1, 2, 3)]; note = len(U.wrap(m.px, EXP_P[4], ix))
        by = (y1 - y0) - 3 - p - m.btnH; view = max(1, (by - m.gap - (3 + p + m.lp + 2 + 4)) // m.lp)
        L.append('  Chuyên Gia: nút chế độ rộng %d px, nhãn dài nhất %d px: %s; mô tả %s + ghi chú %d dòng, hộp hiện %d: %s' % (
            lw, max(U.sw(m.px, ct(i)) for i in (196, 197, 198)), ok(max(U.sw(m.px, ct(i)) for i in (196, 197, 198)) <= lw - 2 * m.pad),
            modes, note, view, 'vừa' if max(modes) + 1 + note <= view else 'cuộn (thanh cuộn)'))
        # cài đặt
        oq = opt_geom(m, rect); chip = seg_chip_w(m, ['1/1', '1/2', '1/3', '1/4']); segw = 4 * chip + 3 * 2 + 2 * m.pad
        stw = max(U.sw(m.px, v) for v in (ct(321), ct(322), ct(323))) + 2 * m.pad + 2 * (oq['rh'] - 4) + 2 * m.pad
        bw_ = max(segw, 2 * seg_chip_w(m, [ct(191), ct(190)]) + 2 + 2 * m.pad, stw)
        lab = max(OPT_LABELS, key=lambda t: U.sw(m.px, t)); avail = cw - bw_ - 2 * m.pad
        L.append('  cài đặt: %d mục, hàng cao %d, nhãn dài nhất %d px "%s" <= %d px (điều khiển rộng nhất %d): %s' % (oq['n'], oq['rh'], U.sw(m.px, lab), lab, avail, bw_, ok(U.sw(m.px, lab) <= avail)))
        nh = oq['n'] * (oq['rh'] + oq['g']) + oq['note_h']
        L.append('  cài đặt: ghi chú "%s" %d px <= %d px: %s; tổng cao %d <= %d: %s' % (NOTE_RESTART, U.sw(m.spx, NOTE_RESTART), cw - 2 * m.pad, ok(U.sw(m.spx, NOTE_RESTART) <= cw - 2 * m.pad), nh, y1 - y0, ok(nh <= y1 - y0)))
        # thành tựu
        aq = ach_geom(m, rect)
        mn = [(len(U.wrap(m.px, MLIST[i], aq['tw'])), MLIST[i]) for i in MLIST]; mx = max(mn)
        rx = x1 - (x0 + aq['lw'] + aq['g']) - 6 - 2 * p
        dn = max((len(U.wrap(m.px, MLIST[i], rx)), MLIST[i]) for i in MLIST)
        dl = {}
        for i in MTEXT:
            if i == 0: continue
            n_ = len(U.wrap(m.px, MTEXT[i], rx - m.sbw - 2)); dl[n_] = dl.get(n_, 0) + 1
        viewd = max(1, ((y1 - 3 - p) - (aq['top'] + 3 + p + 2 * m.lp + m.slp + 1 + 4)) // m.lp)
        L.append('  thành tựu: %d tên, nhiều dòng nhất %d (giới hạn 2) "%s" (chữ %d px): %s; hàng cao %d, hiện %d hàng/%d' % (len(MLIST), mx[0], mx[1], aq['tw'], ok(mx[0] <= 2), aq['rh'], aq['n'], len(MLIST)))
        L.append('  thành tựu chi tiết: tên nhiều dòng nhất %d (giới hạn 3) "%s": %s; mô tả (%d) phân bố dòng %s, hộp hiện %d dòng, %d mô tả dài hơn -> cuộn' % (
            dn[0], dn[1], ok(dn[0] <= 3), sum(dl.values()), ', '.join('%d:%d' % (k_, dl[k_]) for k_ in sorted(dl)), viewd, sum(v for k_, v in dl.items() if k_ > viewd)))
        # trợ giúp
        hq = help_geom(m, rect); ht = max((U.sw(m.px, HELPD[i][0]), HELPD[i][0]) for i in HELPD)
        L.append('  trợ giúp danh sách: %d bài, tiêu đề dài nhất %d px "%s" <= %d px: %s; hiện %d hàng (cuộn)' % (HELP_N, ht[0], ht[1], hq['tw'], ok(ht[0] <= hq['tw']), hq['n']))
        pq = help_page_geom(m, rect); pp = m.pad // 2 + 2
        view = max(1, ((pq['by'] - m.gap - 3) - (y0 + 3 + pp // 2 + m.lp + 2 + 4)) // m.lp)
        cn = {}
        for i in HELPD:
            n_ = len(wrap_paras(m.px, paras(HELPD[i][1]), pq['tw'])); cn[n_] = cn.get(n_, 0) + 1
        L.append('  trợ giúp nội dung (%d bài): phân bố dòng %s, hộp hiện %d dòng, %d bài dài hơn -> cuộn; nút Trước/Tiếp %d px: %s' % (
            HELP_N, ', '.join('%d:%d' % (k_, cn[k_]) for k_ in sorted(cn)), view, sum(v for k_, v in cn.items() if k_ > view),
            (cw - 2 * m.gap - U.sw(m.px, '20/20') - 2 * m.pad) // 2, ok((cw - 2 * m.gap - U.sw(m.px, '20/20') - 2 * m.pad) // 2 >= max(U.sw(m.px, ct(211)), U.sw(m.px, ct(212))) + 2 * m.pad)))
        # hỗ trợ
        iq = info_geom(m, rect); ix0 = iq['rx0'] + 3 + p; iw = x1 - 3 - p - ix0
        tot = 0; worst = 0
        for a, bb in INFO:
            n_ = len(U.wrap(m.px, ct(bb).replace(';', ' '), iw - U.sw(m.px, ct(a)) - 2 * m.pad)); tot += max(1, n_) * m.lp + 4; worst = max(worst, n_)
        L.append('  hỗ trợ/thẩm định: %d hàng, cao %d <= %d, giá trị tối đa %d dòng: %s' % (len(INFO), tot, y1 - y0, worst, ok(tot <= y1 - y0)))
        # popup
        pw = round(0.70 * W) if not m.hd else 480; pw = min(pw, W - 2 * m.mg); ptw = pw - 2 * (m.pad + 2 * s + 4)
        pops = [ct(i) for i in (177, 193, 203, 202, 184)]
        wn = max(len(U.wrap(m.px, t, ptw)) for t in pops); ph = 2 * m.pad + 4 * s + wn * m.lp + m.pad + m.btnH + 2
        L.append('  popup MainMenu (%d chuỗi): dài nhất %d dòng -> cao %d <= %d: %s' % (len(pops), wn, ph, H - 2 * m.mg, ok(ph <= H - 2 * m.mg)))
    L.append('== lỗi bố cục khi dựng ảnh: %s ==' % (', '.join(errors) if errors else 'không'))
    return L

# ---------------------------------------------------------------- tổng hợp có nhãn
def contact_sheet(tiles, out):
    pad = 16; lab = 26; tw = 600; per = 2
    rows = []
    for name, fn, label in SCREENS:
        a = tiles[(name, 400)].resize((600, 360), Image.NEAREST); b = tiles[(name, 1200)].resize((600, 270), Image.LANCZOS)
        rows.append((name, label, a, b))
    cellw = 2 * tw + pad; nr = (len(rows) + per - 1) // per
    sheet = Image.new('RGB', (per * cellw + (per + 1) * pad, pad + nr * (lab + 360 + pad)), (22, 24, 32)); d = ImageDraw.Draw(sheet)
    for k, (name, label, a, b) in enumerate(rows):
        x = pad + (k % per) * (cellw + pad); y = pad + (k // per) * (lab + 360 + pad)
        d.text((x, y + 2), '%d. %s  --  %s    [400x240 hiện x1.5 | 1200x540 thu 1/2]' % (k + 1, name, label), fill=(255, 210, 120))
        sheet.paste(a, (x, y + lab)); sheet.paste(b, (x + tw + pad, y + lab))
    sheet.save(out)

def merge_hits(out, hits):
    p = os.path.join(out, 'hit_rects.json'); old = json.load(open(p)) if os.path.exists(p) else {}
    names = [n for n, _, _ in SCREENS]
    old = {k: v for k, v in old.items() if not any(k.startswith(n + '_') and re.fullmatch(r'\d+x\d+', k[len(n) + 1:]) for n in names)}
    old.update(hits)
    json.dump(old, open(p, 'w'), ensure_ascii=False, indent=1)

def main():
    hits, tiles, errors = render_all(OUT)
    merge_hits(OUT, hits)
    vl = verify_b2c(errors)
    B.merge_text(OUT, 'verify.txt', '\n'.join(['Kiểm trên TOÀN BỘ dữ liệu thật, 4 khung; "cuộn" = thanh cuộn, không cắt chữ.'] + vl) + '\n', '# ===== b2c (mock_b2c.py) =====')
    contact_sheet(tiles, os.path.join(OUT, 'tong_hop_b2c.png'))
    import layout_b2c
    layout_b2c.write(OUT, hits, SCREENS, metrics, FRAMES, dict(grid_geom=grid_geom, cls_geom=cls_geom, opt_geom=opt_geom, ach_geom=ach_geom, help_geom=help_geom, slot_geom=slot_geom, popup_class_geom=popup_class_geom))
    print('\n'.join(vl))

if __name__ == '__main__':
    main()
