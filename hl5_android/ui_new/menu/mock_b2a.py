#!/usr/bin/env python3
"""T17b2 lô b2a: mockup các tab InGameMenu còn lại (Nhân vật, Trang bị, Kỹ năng, Đặc tính, Liên kích, Tinh linh,
Nhiệm vụ, Hệ thống), chế độ con Túi đồ (SubState>1) và popup đặc thù InGameMenu.

Dùng lại viui.py + mock_menu.py (KHÔNG sửa: chạy lại mock_menu.py vẫn ra ảnh T17b1 giống từng byte). Script này CHẠY SAU mock_menu.py:
    python3 -I mock_menu.py [OUT]  &&  python3 -I mock_b2a.py [OUT]
Nó ghi ảnh b2a, rồi GỘP (idempotent) vào hit_rects.json / verify.txt / LAYOUT.md (phần sau dấu <!-- B2A -->).
"""
import os, sys, json, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import viui as U
import mock_menu as M
from mock_menu import C, Cv, metrics, FRAMES, MAIN, ct, TAB_NAMES, TAB_ICON, ITEMS, item_name, content_rect, \
    w_window, w_button, w_scrollbar, w_bar, w_cell, w_row, w_descbox, w_popup, popup_geometry, LONG_NAME, LONG_DESC, bag_geometry
from PIL import Image, ImageDraw

OUT = sys.argv[1] if len(sys.argv) > 1 else os.path.dirname(os.path.abspath(__file__))

# ---------------------------------------------------------------- dữ liệu thật bổ sung
def recs(fn):
    d = {}
    for ri, o, t in U.dat_strings(fn): d.setdefault(ri, []).append(U.plain(t))
    return d

SKILL_FILES = {'war': 'c/csv/skill_00.dat', 'rog': 'c/csv/skill_01.dat', 'gun': 'c/csv/skill_02.dat', 'kni': 'c/csv/skill_03.dat', 'spi': 'c/csv/skill_05.dat'}
SKILLS = {k: recs(v) for k, v in SKILL_FILES.items()}            # ri -> [tên, mô tả]
ALL_SKILLS = [(k, ri, v[0], v[1] if len(v) > 1 else '') for k, d in SKILLS.items() for ri, v in d.items()]
QUESTS = recs('c/csv/quest_0.dat')                               # ri -> [tên, mô tả, loại]
HELP = recs('c/csv2/help_lgt.dat')                               # ri -> [tiêu đề, nội dung]
CLASSES = [v[0] for v in recs('c/csv/class.dat').values()]
SPIRIT_NAME = ct(160); SPIRIT_POWER = ct(161)

def parse_rich(s):
    """chuỗi dữ liệu -> (text thuần, danh sách màu theo ký tự). `;` = dấu cách; `}` vàng, `{` xanh, `|` hết màu (GIẢ ĐỊNH màu: tra BFont khi implement);
    `#NN`: chữ số giữ chỗ -> '100' nếu theo sau không phải chữ, ngược lại gạch đầu dòng."""
    s = s.replace(';', ' ')
    s = re.sub(r'#\d\d(?=[^\W\d_])', '• ', s)
    s = re.sub(r'#\d\d', '100', s)
    out = []; cols = []; cur = None
    for ch in s:
        if ch == '}': cur = 'gold'; continue
        if ch == '{': cur = 'blue'; continue
        if ch == '|': cur = None; continue
        out.append(ch); cols.append(cur)
    return ''.join(out), cols

RC = {'gold': C['gold'][:3] + (255,), 'blue': (130, 196, 255, 255)}

def rich_lines(px, plain, maxw):
    lines = []; cur = None; j = 0; words = []
    for w in plain.split(' '):
        words.append((j, j + len(w))); j += len(w) + 1
    for a, b in words:
        if a == b: continue
        if cur is None: cur = [a, b]
        elif U.sw(px, plain[cur[0]:b]) <= maxw: cur[1] = b
        else: lines.append(tuple(cur)); cur = [a, b]
        while U.sw(px, plain[cur[0]:cur[1]]) > maxw and cur[1] - cur[0] > 1:
            k = cur[1]
            while k > cur[0] + 1 and U.sw(px, plain[cur[0]:k]) > maxw: k -= 1
            lines.append((cur[0], k)); cur = [k, cur[1]]
    if cur: lines.append(tuple(cur))
    return lines

def draw_rich(cv, x, y, plain, cols, a, b, px, base=None):
    base = base or C['white']; i = a
    while i < b:
        j = i
        while j < b and cols[j] == cols[i]: j += 1
        x += U.draw_text(cv.im, x, y, [(0, plain[i:j])], px, RC.get(cols[i], base)); i = j

# ---------------------------------------------------------------- widget mới
def inset(m):                      # khoảng từ mép khung hộp viền 1px tới nội dung (frame thick=1 -> 3)
    return 3

def w_subtabs(cv, x0, y, x1, labels, active, hid='subtab', action='đổi tab con: ghi trường tab con gốc rồi KeyPressed(trái/phải)'):
    """thanh tab con (chữ): n ô đều nhau, cao subH. Trả chiều cao."""
    m = cv.m; n = len(labels); g = 2
    h = m.px + 2 * (m.bt + 3) + 2
    tw = (x1 - x0 - (n - 1) * g) // n; off = (x1 - x0 - (n * tw + (n - 1) * g)) // 2
    for i, l in enumerate(labels):
        xa = x0 + off + i * (tw + g); r = (xa, y, xa + tw, y + h)
        if i == active:
            inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255)); col = C['white']; sh = True
        else:
            inner = cv.frame(r, rad=m.rad, orange=False, thick=1); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255)); col = (190, 204, 224, 255); sh = False
        cv.text((r[0] + r[2]) // 2, y + (h - m.px) // 2, l, m.px, col, shadow=sh, anchor='c', maxw=tw - 2 * (m.bt + 3) - 2)
        cv.hit('%s%d' % (hid, i), r, action)
    return h

def w_arrow(cv, r, d, enabled=True, hid=None, action=''):
    """nút mũi tên (d: 'l','r','u','d')"""
    m = cv.m; x0, y0, x1, y1 = r
    inner = cv.frame(r, rad=m.rad, orange=False, thick=1) if not enabled else cv.frame(r, rad=m.rad)
    cv.vgrad(inner, (38, 54, 78, 255), (22, 32, 48, 255)) if enabled else None
    cx, cy = (x0 + x1) // 2, (y0 + y1) // 2; k = max(3, min(x1 - x0, y1 - y0) // 4)
    col = C['orange_hi'] if enabled else C['disabled']
    d_ = ImageDraw.Draw(cv.im)
    pts = {'l': [(cx + k // 2, cy - k), (cx + k // 2, cy + k), (cx - k // 2 - 1, cy)], 'r': [(cx - k // 2, cy - k), (cx - k // 2, cy + k), (cx + k // 2 + 1, cy)],
           'u': [(cx - k, cy + k // 2), (cx + k, cy + k // 2), (cx, cy - k // 2 - 1)], 'd': [(cx - k, cy - k // 2), (cx + k, cy - k // 2), (cx, cy + k // 2 + 1)]}[d]
    d_.polygon(pts, fill=col)
    if hid: cv.hit(hid, r, action)

def w_sign(cv, r, sign, enabled=True, hid=None, action=''):
    """nút vuông +/- (vẽ bằng khối, không phụ thuộc glyph)"""
    m = cv.m; x0, y0, x1, y1 = r
    if enabled:
        inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (250, 164, 40, 255), (204, 100, 12, 255)); col = C['white']
    else:
        inner = cv.frame(r, rad=m.rad, orange=False, thick=1); cv.rect(inner, (20, 26, 36, 255)); col = C['disabled']
    cx, cy = (x0 + x1) // 2, (y0 + y1) // 2; k = max(3, (x1 - x0) // 4); t = max(1, m.s)
    cv.rect((cx - k, cy - t // 2 - (t % 2 == 0) * 0, cx + k + 1, cy - t // 2 + t), col)
    if sign == '+': cv.rect((cx - t // 2, cy - k, cx - t // 2 + t, cy + k + 1), col)
    if hid: cv.hit(hid, r, action)

def w_hscroll(cv, x0, y, x1, pos, total, view):
    """thanh cuộn ngang (con trượt cam), cao sbw"""
    m = cv.m; h = m.sbw
    cv.rect((x0, y, x1, y + h), C['track']); cv.rect((x0, y, x1, y + 1), C['ink'])
    tw = max(2 * m.s + 6, round((x1 - x0) * min(1, view / max(total, 1))))
    tx = x0 + round((x1 - x0 - tw) * (pos / max(total - view, 1)))
    cv.rect((tx, y + 1, tx + tw, y + h), C['orange'], rad=1 if m.s > 1 else 0); cv.rect((tx, y + 1, tx + tw, y + 2), C['orange_hi'])

def w_portrait(cv, x, y, pr, icon=('ui', 7), k=None):
    ring = Image.open(os.path.join(U.REPO, 'ui_new/03_frame_empty.png')).convert('RGBA').resize((pr, pr), Image.LANCZOS)
    cv.im.alpha_composite(ring, (x, y))
    k = k or min(cv.m.s + 1, 3)
    cv.icon(icon[0], icon[1], 0, 0, k, tint=C['white'] if icon[0] == 'ui' else None, center=(x + pr // 2, y + pr // 2))

def w_kv(cv, x0, y, x1, label, value, px=None, vcol=None, lcol=None, bar=False):
    """cặp nhãn (mờ) trái + giá trị phải, đường kẻ mảnh dưới; chiều cao do người gọi cấp (step)"""
    m = cv.m; px = px or m.px
    vw = U.sw(px, value)
    cv.text(x0, y, label, px, lcol or C['dim'], maxw=x1 - x0 - vw - 6)
    cv.text(x1, y, value, px, vcol or C['white'], anchor='r')

def w_panel(cv, r, fill=(14, 22, 34, 230)):
    return cv.frame(r, fill=fill, rad=cv.m.rad, orange=False, thick=1)

def w_summary(cv, x0, y0, x1, y1, bars=True, spirit=False):
    """cột tóm tắt nhân vật/Tinh linh: vòng chân dung + tên/lớp/LV + (3 thanh). Trả y cuối."""
    m = cv.m; s = m.s
    w_panel(cv, (x0, y0, x1, y1))
    px_ = x0 + m.pad; yy = y0 + m.pad
    pr = 22 * s + 20 if not m.hd else 84
    if spirit: w_portrait(cv, px_, yy, pr, ('icon', 172), k=s)
    else: w_portrait(cv, px_, yy, pr)
    nx = px_ + pr + m.pad // 2 + 2; ty_ = yy + max(0, (pr - 3 * m.lp) // 2); mw = x1 - nx - m.pad
    cv.text(nx, ty_, SPIRIT_NAME if spirit else 'Schurtz', m.px, C['gold'], shadow=True, maxw=mw)
    cv.text(nx, ty_ + m.lp, ct(13) + ' 48', m.px, C['white'], maxw=mw)
    cv.text(nx, ty_ + 2 * m.lp, ct(6) if spirit else ct(178), m.px, C['dim'], maxw=mw)
    yy += max(pr, 3 * m.lp) + m.pad
    if bars:
        bw = x1 - x0 - 2 * m.pad
        for lab, v, mx_, col in ((ct(10), 1842, 2176, C['hp']), (ct(11), 391, 520, C['sp']), (ct(12), 7120, 9800, C['exp'])):
            w_bar(cv, px_, yy, bw, lab, v, mx_, col); yy += m.barH + m.pad - 1
    return yy

def kv_rows(cv, x0, y, x1, rows, step, px=None):
    for i, (l, v) in enumerate(rows):
        w_kv(cv, x0, y + i * step, x1, l, v, px)
        cv.rect((x0, y + i * step + step - 2, x1, y + i * step + step - 1), (40, 54, 76, 255))
    return y + len(rows) * step

def info_geom(m, box):
    """hình học chữ trong hộp mô tả (khớp w_descbox: viền 1 => inner 3)"""
    p = m.pad // 2 + 1
    ix0, ix1 = box[0] + inset(m), box[2] - inset(m)
    return ix0, ix1, p, (ix1 - ix0) - 2 * p - m.sbw - 2

def w_info(cv, box, name, meta, desc_raw, pos=0, rows=None, extra=None):
    """hộp thông tin: tên (≤2 dòng) / meta nhỏ / vạch / [hàng kv nhỏ] / mô tả cuộn. Trả (tổng dòng, dòng hiển thị)."""
    m = cv.m
    y = w_descbox(cv, box, name, [], meta=meta)
    ix0, ix1, p, tw = info_geom(m, box)
    cv.last_info = dict(rows_total=len(rows or []), rows_shown=0, extra_total=len(extra or []), extra_shown=0)
    if rows:
        navail = (box[3] - 3 - y) // m.slp - len(extra or [])
        for l, v in rows[:max(0, navail)]:
            cv.text(ix0 + p, y, l, m.spx, C['dim'], maxw=(ix1 - ix0) // 2 + 10)
            cv.text(ix1 - p, y, v, m.spx, C['white'], anchor='r'); y += m.slp; cv.last_info['rows_shown'] += 1
        y += 1
    for t, col in (extra or []):
        if y + m.spx <= box[3] - 3:
            cv.text(ix0 + p, y, t, m.spx, col, maxw=ix1 - ix0 - 2 * p); y += m.slp; cv.last_info['extra_shown'] += 1
    total = view = 0
    if desc_raw:
        plain, cols = parse_rich(desc_raw)
        ls = rich_lines(m.px, plain, tw); view = max(1, (box[3] - 3 - y) // m.lp); total = len(ls)
        for i, (a, b) in enumerate(ls[pos:pos + view]): draw_rich(cv, ix0 + p, y + i * m.lp, plain, cols, a, b, m.px)
        if total > view: w_scrollbar(cv, ix1 - m.sbw - 1, y, box[3] - 3, pos, total, view)
        cv.hit('desc_scroll', (ix0, y, ix1, box[3] - 3), 'kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc')
    return total, view

SWAP_IMG = None
def swap_img(sz):
    """ui_new/07_swap.png (186x186) thu LANCZOS về sz x sz (sz = cell = 20*s+10: 30/30/50/70)"""
    global SWAP_IMG
    if SWAP_IMG is None: SWAP_IMG = Image.open(os.path.join(U.REPO, 'ui_new/07_swap.png')).convert('RGBA')
    return SWAP_IMG.resize((sz, sz), Image.LANCZOS)

def belt_row_w(m, n=5, cellsz=None):
    """bề rộng cả hàng ô nhanh gồm n ô + nút đổi bộ (ảnh 07, vuông = ô)"""
    c = cellsz or m.cell
    return n * c + (n - 1) * m.cg + m.gap + c

def w_belt(cv, x, y, n=5, sel=None, icons=(), label=None, cellsz=None, swap=True, bank=1):
    """thanh ô nhanh n ô (+ nút đổi bộ ảnh ui_new/07: gốc có 2 bộ x 5 ô), trả (x1,y1)"""
    m = cv.m; c = cellsz or m.cell; g = m.cg
    for i in range(n):
        w_cell(cv, x + i * (c + g), y, (icons[i], 0, '', False) if i < len(icons) and icons[i] is not None else None, selected=(i == sel))
        t = str(i + 1); cv.text(x + i * (c + g) + 2, y + 1, t, m.spx, C['dim'] if i != sel else C['white'], shadow=True)
        cv.hit('belt%d' % i, (x + i * (c + g), y, x + i * (c + g) + c, y + c), 'đặt con trỏ ô nhanh [this+0x10]=%d rồi KeyPressed(-5) (SubState 2 -> HERO::SetHotKeyBelt)' % i)
    x1 = x + n * (c + g) - g
    if swap:
        sx = x1 + m.gap
        cv.im.alpha_composite(swap_img(c), (sx, y))
        t = '%d/2' % bank; tw = U.sw(m.spx, t)
        cv.text(sx + c // 2, y + c + 1, t, m.spx, C['white'], shadow=True, anchor='c', maxw=c)        # nhãn DƯỚI nút (không đè icon)
        cv.hit('belt_swap', (sx, y, sx + c, y + c + m.spx + 2), 'đổi bộ ô nhanh 1<->2 (gốc có 2 bộ x 5 ô; phím/hàm đổi bộ T17c xác nhận, vd HERO::ChangeHotKeyToggle) rồi vẽ lại')
        x1 = sx + c
    return x1, y + c

def w_menu_popup(cv, title, labels, sel=0, disabled=()):
    """popup menu hành động (SubState/Popup dạng danh sách): nền tối + hộp, tiêu đề (tên vật phẩm), mỗi lựa chọn là 1 nút rộng"""
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw = round(0.55 * m.W) if not m.hd else 420; pw = min(pw, m.W - 2 * m.mg)
    inw = pw - 2 * (m.pad + 2 * s + 4)
    tl = U.wrap(m.px, U.clean_name(title)[0], inw) if title else []
    assert len(tl) <= 2, ('tiêu đề popup > 2 dòng', title)
    bh = m.btnH; gg = m.gap
    ph = 2 * m.pad + 4 * s + len(tl) * m.lp + (m.pad if tl else 0) + len(labels) * bh + (len(labels) - 1) * gg + 2
    assert ph <= m.H - 2 * m.mg, ('popup menu cao quá', ph, m.H)
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248))
    y = inner[1] + m.pad + 2 * s
    code = U.clean_name(title)[1] if title else ''
    for l in tl:
        cv.text(m.W // 2, y, l, m.px, U.RARITY.get(code, C['gold'][:3]) + (255,), shadow=True, anchor='c', maxw=inw); y += m.lp
    if tl: y += m.pad
    for i, l in enumerate(labels):
        r = (inner[0] + m.pad + 2 * s, y, inner[2] - m.pad - 2 * s, y + bh)
        w_button(cv, r, l, 'disabled' if i in disabled else ('primary' if i == sel else 'normal'), 'menu%d' % i,
                 'đặt con trỏ menu=%d rồi KeyPressed(-5) (SubState 1 / SetPopupState gốc)' % i)
        y += bh + gg
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua (không đóng)')

def new_screen(m, title, active, tabs=True):
    cv = Cv(m); body = w_window(cv, title, tabs=tabs, active=active)
    return cv, content_rect(m, body)

def pips(cv, x, y, w, cur, mx):
    """chấm cấp kỹ năng (khớp ý DrawSkillLevelHole): mx ô nhỏ, cur ô sáng"""
    m = cv.m; sz = max(2, m.s + 1); g = 1; tot = mx * sz + (mx - 1) * g; x += (w - tot) // 2
    for i in range(mx):
        cv.rect((x + i * (sz + g), y, x + i * (sz + g) + sz, y + sz), C['gold'] if i < cur else (48, 60, 82, 255))

# ---------------------------------------------------------------- dữ liệu mẫu (tất cả tên/mô tả THẬT)
HERO = dict(name='Schurtz', cls=ct(178), lv=48, hp=(1842, 2176), sp=(391, 520), exp=(7120, 9800), pts=5)
STATS = [(ct(15), 42), (ct(16), 18), (ct(17), 30), (ct(18), 11)]
def equip_item(f, r): return ITEMS[('c/csv/item_%02d.dat' % f, r)][2]
EQUIP = [  # (icon.mgr idx, tên, phẩm chất) cho 8 ô: Vũ khí, Mũ, Giáp, Găng, Giày, Khiên, Trang sức 1, Trang sức 2
    (203, U.clean_name(ITEMS[LONG_NAME][2])[0], '`'), (205, equip_item(5, 0), '&'), (206, equip_item(6, 0), ''), (207, equip_item(7, 0), '}'),
    (208, equip_item(8, 0), ''), (209, equip_item(9, 0), ''), None, None]
POTION = ('c/csv/item_11.dat', 0)
EQUIP_EMPTY_ICON = [203, 205, 206, 207, 208, 209, 210, 211]
SK_LIST = SKILLS['war']                                                # skill_00: 43 kỹ năng, 4 cột -> 11 hàng cuộn
LONGD = max(range(len(SK_LIST)), key=lambda i: len(SK_LIST[i][1]))
LONGN = max(range(len(SK_LIST)), key=lambda i: U.sw(18, SK_LIST[i][0]))

# ---------------------------------------------------------------- màn: Nhân vật
def screen_char(m, page=0, pending=True):
    cv, (x0, y0, x1, y1) = new_screen(m, TAB_NAMES[0], 0); s = m.s; cw = x1 - x0
    g = m.gap + 2
    if page == 0:
        lw = round(cw * 0.40); sx1 = x0 + lw
        yy = w_summary(cv, x0, y0, sx1, y1)
        bw = sx1 - x0 - 2 * m.pad
        by = y1 - m.pad - m.btnH
        assert yy <= by - 2, ('cột trái nhân vật tràn', yy, by)
        bwd = (bw - m.pad) // 2
        w_button(cv, (x0 + m.pad, by, x0 + m.pad + bwd, by + m.btnH), U.strip_hint(ct(20)), 'normal', 'page_toggle', 'phím # -> KeyCharacterMenu: đổi trang Chi tiết/Cơ bản')
        w_button(cv, (sx1 - m.pad - bwd, by, sx1 - m.pad, by + m.btnH), U.strip_hint(ct(32)), 'normal' if pending else 'disabled', 'cancel', 'phím * -> StateInGameMenu::CancelApplayStat')
        # phải: điểm còn lại + 4 hàng cộng điểm + chỉ số dẫn xuất
        rx0 = sx1 + g; ah = m.rowH + m.s; ry = y0
        step = m.slp + 2
        ex = y1 - (y0 + (m.lp + 2) + 4 * (ah + m.cg + 1) + 1 + 3 + 2 * step)    # chỗ dư (khung lớn): dãn đều hàng cộng điểm + hàng chỉ số
        add = min(max(0, ex) // 6, 4 * s); ah += add; step += add
        cv.text(rx0 + 2, ry, ct(14), m.px, C['dim']); cv.text(x1 - 2, ry, str(HERO['pts'] - (1 if pending else 0)), m.px, C['gold'], anchor='r', shadow=True)
        ry += m.lp + 2
        for i, (lab, v) in enumerate(STATS):
            sel = (i == 0); r = (rx0, ry, x1, ry + ah)
            cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), *(((250, 160, 36, 255), (196, 96, 12, 255)) if sel else (C['cell'], C['cell2'])))
            bs = ah; mx_ = x1 - bs; mn = mx_ - m.cg - bs
            vt = str(v + (1 if (sel and pending) else 0))
            cv.text(rx0 + m.pad, ry + (ah - m.px) // 2, lab, m.px, C['white'], shadow=sel, maxw=mn - rx0 - 40)
            vw = U.sw(m.px, vt); cv.text(mn - m.pad, ry + (ah - m.px) // 2, vt, m.px, C['gold'] if not sel else C['white'], shadow=sel, anchor='r')
            if sel and pending:
                cv.text(mn - m.pad - vw - 4, ry + (ah - m.spx) // 2, '+1', m.spx, C['green'], shadow=True, anchor='r')
            w_sign(cv, (mn, ry, mn + bs, ry + ah), '-', enabled=(sel and pending), hid='minus%d' % i, action='đặt con trỏ=%d + KeyPressed(TRÁI) (giảm điểm tạm)' % i)
            w_sign(cv, (mx_, ry, mx_ + bs, ry + ah), '+', enabled=True, hid='plus%d' % i, action='đặt con trỏ=%d + KeyPressed(PHẢI) (tăng điểm, tiêu 1 điểm)' % i)
            cv.hit('stat%d' % i, (rx0, ry, mn, ry + ah), 'đặt con trỏ hàng [this+0xe]=%d' % i)
            ry += ah + m.cg + 1
        ry += 1; cv.rect((rx0, ry, x1, ry + 1), C['line']); ry += 3
        rows = [(ct(25), '248'), (ct(26), '112'), (ct(27), '64'), (ct(44), '31')]
        hw2 = (x1 - rx0 - 6) // 2
        e1 = kv_rows(cv, rx0 + 2, ry, rx0 + hw2, rows[:2], step, m.spx)
        e2 = kv_rows(cv, x1 - hw2, ry, x1 - 2, rows[2:], step, m.spx); end = max(e1, e2)
        assert end <= y1, ('cột phải nhân vật tràn', end, y1)
    else:
        colw = (cw - g) // 2; step = m.lp + 2 * s - 1
        L = [(ct(33), '312'), (ct(34), '287'), (ct(35), '248'), (ct(36), '112'), (ct(37), '64'), (ct(38), '95'), (ct(39), '88')]
        R = [(ct(42), '71'), (ct(40), '12'), (ct(41), '8'), (ct(43), '13'), (ct(44), '31'), (ct(45), '24')]
        w_panel(cv, (x0, y0, x0 + colw, y0 + 7 * step + 2 * m.pad - 2)); w_panel(cv, (x1 - colw, y0, x1, y0 + 7 * step + 2 * m.pad - 2))
        e1 = kv_rows(cv, x0 + m.pad, y0 + m.pad - 1, x0 + colw - m.pad, L, step)
        e2 = kv_rows(cv, x1 - colw + m.pad, y0 + m.pad - 1, x1 - m.pad, R, step)
        by = y1 - m.btnH
        assert e1 + m.pad <= by - 2, ('bảng chỉ số tràn', e1, by)
        w_button(cv, (x0, by, x0 + (cw - g) // 2, by + m.btnH), U.strip_hint(ct(21)), 'normal', 'page_toggle', 'phím # -> đổi trang Cơ bản/Chi tiết')
    return cv

# ---------------------------------------------------------------- màn: Trang bị
def equip_geom(m, body):
    x0, y0, x1, y1 = content_rect(m, body); s = m.s
    g = {'x0': x0, 'y0': y0, 'x1': x1, 'y1': y1}
    pr = 22 * s + 20 if not m.hd else 84
    g['pr'] = pr; g['pw'] = pr + 2 * m.pad if not m.hd else pr + m.pad
    g['sx'] = x0 + g['pw'] + m.gap; g['sy'] = y0
    g['gridw'] = 4 * m.pitch - m.cg; g['gridh'] = 2 * m.pitch - m.cg
    g['dx'] = g['sx'] + g['gridw'] + m.gap + 1; g['dy'] = y0
    g['dh'] = max(g['gridh'], 3 + (m.pad // 2 + 1) + 2 * m.lp + m.slp + 5 + 4 * m.slp + 2 + 3)
    g['ty'] = y0 + g['dh'] + m.gap
    return g

def screen_equip(m, popup=False):
    cv, _ = new_screen(m, TAB_NAMES[2], 2); body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    g = equip_geom(m, body); s = m.s
    x0, y0, x1, y1 = g['x0'], g['y0'], g['x1'], g['y1']
    # cột chân dung
    pw = g['pw']; w_panel(cv, (x0, y0, x0 + pw, y0 + g['dh']))
    w_portrait(cv, x0 + (pw - g['pr']) // 2, y0 + m.pad // 2 + 1, g['pr'])
    cv.text(x0 + pw // 2, y0 + g['dh'] - m.lp - 2, ct(13) + ' 48', m.px, C['gold'], shadow=True, anchor='c', maxw=pw - 2)
    # 8 ô trang bị 4x2
    sel = 0
    for i in range(8):
        c, r = i % 4, i // 4; x = g['sx'] + c * m.pitch; y = g['sy'] + r * m.pitch
        e = EQUIP[i]
        w_cell(cv, x, y, (e[0], 0, e[2], True) if e else None, selected=(i == sel))
        if not e: cv.icon('icon', EQUIP_EMPTY_ICON[i], x + (m.cell - 20 * s) // 2, y + (m.cell - 20 * s) // 2, s, tint=(52, 66, 92))
        cv.hit('slot%d' % i, (x, y, x + m.cell, y + m.cell), 'đặt con trỏ ô trang bị=%d rồi KeyPressed(-5) (ô có đồ: mở menu Trang bị/Tháo; ô trống: OpenEquipFilter)' % i)
    # hộp chi tiết (vũ khí dài nhất)
    box = (g['dx'], g['dy'], x1, g['dy'] + g['dh'])
    w_info(cv, box, EQUIP[0][1], ct(222).split()[0] + ' ' + ITEMS[LONG_NAME][24].rstrip(';'), None,
           rows=[(ct(221), '35'), (ct(228), '248'), (ct(225), ct(227))], extra=[(ct(120), C['green'])])
    assert cv.last_info['rows_shown'] == 3 and cv.last_info['extra_shown'] == 1, ('chi tiết trang bị cắt', cv.last_info)
    cv.hit('detail', box, 'chỉ hiển thị')
    # bảng chỉ số 2 cột (font nhỏ)
    ty = g['ty']; colw = (x1 - x0 - m.gap) // 2; step = m.slp + 1
    w_panel(cv, (x0, ty, x0 + colw, y1)); w_panel(cv, (x1 - colw, ty, x1, y1))
    A = [(ct(291), '2176'), (ct(228), '248'), (ct(26), '112'), (ct(30), '12'), (ct(28), '13')]
    B = [(ct(292), '520'), (ct(225), ct(227)), (ct(27), '64'), (ct(44), '31'), (ct(45), '24')]
    nrow = max(1, (y1 - ty - 2 * (m.pad // 2 + 1)) // step); A = A[:nrow]; B = B[:nrow]
    step = max(step, min((y1 - ty - 2 * (m.pad // 2 + 1)) // max(1, len(A)), m.lp + 2))
    kv_rows(cv, x0 + m.pad, ty + m.pad // 2 + 1, x0 + colw - m.pad, A, step, m.spx)
    kv_rows(cv, x1 - colw + m.pad, ty + m.pad // 2 + 1, x1 - m.pad, B, step, m.spx)
    assert ty + m.pad // 2 + 1 + len(A) * step <= y1, ('bảng trang bị tràn', ty, y1)
    if popup: w_menu_popup(cv, EQUIP[0][1], [ct(66), ct(67)], sel=0)
    return cv

# ---------------------------------------------------------------- Túi đồ: chế độ con (SubState 1 menu, 2 ô nhanh, >=3 lọc chọn)
def picker_geom(m, body):
    x0, y0, x1, y1 = content_rect(m, body)
    n = 5; c = m.cell; g = m.cg
    aw = c  # nút mũi tên vuông
    rowW = aw + m.gap + n * c + (n - 1) * g + m.gap + aw
    hx = x0 + (x1 - x0 - rowW) // 2
    return dict(x0=x0, y0=y0, x1=x1, y1=y1, hx=hx, aw=aw, c=c, g=g, cx=hx + aw + m.gap, rowW=rowW, hy=y0 + m.lp + m.gap + 2)

def screen_picker(m, tabi=1, title_hint=None, mode='filter'):
    """mode: 'filter' (SubState>=3: 1 hàng 5 vật phẩm/trang + cuộn ngang) | 'belt' (SubState 2: chọn ô nhanh)"""
    cv, _ = new_screen(m, TAB_NAMES[tabi], tabi); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    g = picker_geom(m, body); x0, y0, x1, y1 = g['x0'], g['y0'], g['x1'], g['y1']
    hint = title_hint or (ct(112) if tabi == 2 else ct(155))
    cv.text((x0 + x1) // 2, y0 + 1, hint, m.px, C['gold'], shadow=True, anchor='c', maxw=x1 - x0)
    hy = g['hy']
    if mode == 'filter':
        items = [(203, 0, '`', False), (205, 0, '&', False), (206, 0, '', False), (207, 0, '}', False), (208, 0, '', False)]
        names = [equip_item(0, 0), equip_item(5, 0), equip_item(6, 0), equip_item(7, 0), equip_item(8, 0)]
        sel = 1; total, pos = 23, 5 + sel                       # 23 vật phẩm phù hợp, con trỏ tuyệt đối 6 -> trang 2 (5/trang)
        w_arrow(cv, (g['hx'], hy, g['hx'] + g['aw'], hy + g['c']), 'l', True, 'prev', 'KeyPressed(TRÁI): lùi 1 vật phẩm ([this+0x10]-1), qua trang khi hết 5')
        for i in range(5):
            xx = g['cx'] + i * (g['c'] + g['g'])
            w_cell(cv, xx, hy, items[i], selected=(i == sel))
            cv.hit('cand%d' % i, (xx, hy, xx + g['c'], hy + g['c']), 'đặt [this+0x10] = trang*5+%d rồi KeyPressed(-5) (chạm lần 2 = chọn)' % i)
        ax = g['cx'] + 5 * g['c'] + 4 * g['g'] + m.gap
        w_arrow(cv, (ax, hy, ax + g['aw'], hy + g['c']), 'r', True, 'next', 'KeyPressed(PHẢI): tiến 1 vật phẩm')
        # cuộn ngang + số trang
        sy = hy + g['c'] + m.gap
        w_hscroll(cv, g['cx'], sy, g['cx'] + 5 * g['c'] + 4 * g['g'], pos, total, 5)
        cv.hit('hscroll', (g['cx'], sy - 2, g['cx'] + 5 * g['c'] + 4 * g['g'], sy + m.sbw + 2), 'chạm/kéo ngang: [this+0x10] theo vị trí (Touch mới) hoặc nút mũi tên')
        pgt = '%d/%d' % (pos // 5 + 1, (total + 4) // 5)
        cv.text(x1 - 2, y0 + 1 + (m.lp - m.spx) // 2 + 1, pgt, m.spx, C['dim'], anchor='r')
        ty = sy + m.sbw + m.gap + 1
    else:
        inw = belt_row_w(m, 5, g['c'])
        bx1, by1 = w_belt(cv, (x0 + x1 - inw) // 2, hy, 5, sel=2, icons=[226, 227, None, 211, None], cellsz=g['c'])
        ty = by1 + m.gap + 2
        cv.text((x0 + x1) // 2, ty, ct(54), m.px, C['dim'], anchor='c', maxw=x1 - x0); ty += m.lp + m.gap
    # hộp chi tiết (vật phẩm đang trỏ) + nút
    by = y1 - m.btnH
    box = (x0, ty, x1, by - m.gap)
    nm = names[sel] if mode == 'filter' else item_name(*POTION)
    if mode == 'filter':
        w_info(cv, box, nm, ct(222).split()[0] + ' ' + equip_item(5, 0), None, rows=[(ct(221), '20'), (ct(26), '+18'), (ct(30), '+4')],
               extra=[(ct(287).lstrip('+'), U.RARITY['&'] + (255,))])
        left, right = (U.strip_hint(ct(66)), U.strip_hint(ct(32)))
    else:
        w_info(cv, box, nm, ct(96), ITEMS[POTION][12])
        left, right = (ct(56), ct(57))
    cv.hit('detail', box, 'chỉ hiển thị')
    hw = (x1 - x0 - m.gap) // 2
    w_button(cv, (x0, by, x0 + hw, by + m.btnH), left, 'primary', 'ok', 'KeyPressed(-5) = chọn vật phẩm / gán ô (Process gốc: SetEquipItem, ApplyItemCompose, SetHotKeyBelt...)')
    w_button(cv, (x1 - hw, by, x1, by + m.btnH), right, 'normal', 'cancel', 'phím * / back: thoát chế độ con (SetSubState(0))')
    return cv

def screen_bag_menu(m, kind='act'):
    """Túi đồ SubState 1: menu hành động trên vật phẩm; kind 'act' hoặc 'detail' (hộp chi tiết dạng popup)"""
    cv = M.screen_bag(m)
    if kind == 'act':
        w_menu_popup(cv, item_name(*LONG_NAME), [ct(55), ct(54), ct(52), ct(53)], sel=1, disabled=(3,))
    return cv

# ---------------------------------------------------------------- Kỹ năng
SUB5 = [ct(46), ct(68), ct(4), ct(6), ct(47)]

def skill_geom(m, body):
    x0, y0, x1, y1 = content_rect(m, body)
    g = dict(x0=x0, y0=y0, x1=x1, y1=y1)
    g['sh'] = m.px + 2 * (m.bt + 3) + 2
    g['gy'] = y0 + g['sh'] + m.gap
    g['gx'] = x0 + m.sbw + 4
    g['gw'] = 4 * m.pitch - m.cg; g['gh'] = 4 * m.pitch - m.cg
    g['sbx'] = g['gx'] + g['gw'] + 3
    g['dx'] = g['sbx'] + m.sbw + m.gap + 2
    g['btn_h'] = m.btnH
    g['dy1'] = y1 - m.btnH - m.gap
    return g

def screen_skill(m, sel=None, assign=False, scrollpos=0):
    sel = LONGD if sel is None else sel
    cv, _ = new_screen(m, TAB_NAMES[3], 3); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    g = skill_geom(m, body); x0, y0, x1, y1 = g['x0'], g['y0'], g['x1'], g['y1']
    w_subtabs(cv, x0, y0, x1, SUB5, 0, 'subtab', 'đổi tab con (5): ghi trường tab con của KeySkillMenu (ví dụ [this+0x11]) rồi vẽ lại')
    first = sel // 4 - 1 if sel // 4 >= 1 else 0
    first = min(first, 11 - 4) if scrollpos == 0 else scrollpos
    lv_cycle = [3, 1, 5, 2, 0, 4, 1, 3]
    for i in range(16):
        k = (first + i // 4) * 4 + i % 4
        if k >= len(SK_LIST): break
        x = g['gx'] + (i % 4) * m.pitch; y = g['gy'] + (i // 4) * m.pitch
        lv = lv_cycle[k % 8]
        w_cell(cv, x, y, (k, 0, '', False), selected=(k == sel))
        if lv == 0: cv.alpha_rect((x + 1, y + 1, x + m.cell - 1, y + m.cell - 1), (6, 10, 18, 150))
        pips(cv, x, y + m.cell - 2 - max(2, s + 1), m.cell, lv, 5)
        cv.hit('skill%d' % i, (x, y, x + m.cell, y + m.cell), 'con trỏ ô=%d (+ hàng cuộn) rồi KeyPressed(-5): chọn; chạm lần 2 = mở menu Gán phím tắt' % i)
    w_scrollbar(cv, g['sbx'], g['gy'], g['gy'] + g['gh'], first, 11, 4)
    cv.hit('grid_scroll', (g['gx'], g['gy'], g['sbx'] + m.sbw, g['gy'] + g['gh']), 'kéo dọc = cuộn hàng (Touch mới; gốc: phím lên/xuống ở hàng 0/3)')
    w_arrow(cv, (g['sbx'] - m.sbw - 2, g['gy'] - 1, g['sbx'] - 2, g['gy'] - 1), 'u', False) if False else None
    nm, ds = SK_LIST[sel]
    box = (g['dx'], g['gy'], x1, g['dy1'])
    lvs = lv_cycle[sel % 8]
    w_info(cv, box, nm, '%s %d/5' % (ct(13), lvs), ds)
    cv.hit('detail', box, 'mô tả: kéo dọc để cuộn')
    by = y1 - m.btnH; bx = g['dx']
    w_button(cv, (bx, by, x1, by + m.btnH), ct(54), 'primary', 'assign', 'KeyPressed(-5) -> popup/SubState gán ô nhanh (HERO::SetHotKeyBelt)')
    if assign:
        w_belt_popup(cv, nm)
    return cv

def w_belt_popup(cv, name):
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    c = m.cell; n = 5; inw = belt_row_w(m, n, c)
    pw = inw + 2 * (m.pad + 2 * s + 4); pw = max(pw, round(0.55 * m.W) if not m.hd else 420)
    pw = min(pw, m.W - 2 * m.mg)
    ph = 2 * m.pad + 4 * s + m.lp + m.pad + m.lp + 4 + c + m.pad + m.btnH + 2
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248))
    y = inner[1] + m.pad + 2 * s
    cv.text(m.W // 2, y, U.clean_name(name)[0], m.px, C['gold'], shadow=True, anchor='c', maxw=pw - 2 * (m.pad + 2 * s + 4)); y += m.lp + 2
    cv.text(m.W // 2, y, ct(54), m.px, C['dim'], anchor='c', maxw=pw - 2 * (m.pad + 2 * s + 4)); y += m.lp + 4
    w_belt(cv, (m.W - inw) // 2, y, 5, sel=1, icons=[0, None, 226, None, 211], cellsz=c)
    y += c + m.pad
    bw = max(U.sw(m.px, ct(57)) + 4 * m.pad, inw // 3)
    w_button(cv, ((m.W - bw) // 2, y, (m.W + bw) // 2, y + m.btnH), ct(57), 'normal', 'popup_cancel', 'back: thoát SubState 2')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua')

# ---------------------------------------------------------------- Đặc tính (3 nhánh + tab Điểm; lưới 3 cột x 4 hàng, hàng yêu cầu 0/4/8/12 điểm)
def prop_geom(m, body):
    x0, y0, x1, y1 = content_rect(m, body); s = m.s
    g = dict(x0=x0, y0=y0, x1=x1, y1=y1)
    g['sh'] = m.px + 2 * (m.bt + 3) + 2
    g['gy'] = y0 + g['sh'] + m.gap
    g['lab_w'] = U.sw(m.spx, '12') + m.gap
    g['colstep'] = m.cell + m.cell // 2 + m.gap
    g['gx'] = x0 + g['lab_w'] + m.gap
    g['gx1'] = g['gx'] + 2 * g['colstep'] + m.cell
    g['sbx'] = g['gx1'] + m.gap
    g['rowh'] = min((y1 - g['gy']) // 4, m.pitch + 2 * s + 2)
    g['rx0'] = g['sbx'] + m.sbw + m.gap + 2
    return g

def screen_prop(m, sel=(1, 1)):
    cv, _ = new_screen(m, TAB_NAMES[4], 4); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    g = prop_geom(m, body); x0, y0, x1, y1 = g['x0'], g['y0'], g['x1'], g['y1']
    w_subtabs(cv, x0, y0, x1, [ct(68), ct(69), ct(70), ct(14)], 0, 'branch', 'đổi nhánh: KeyPropertyMenu (trái/phải ở hàng tab)')
    gy, rowh, gx, cs = g['gy'], g['rowh'], g['gx'], g['colstep']
    nodes = [[None, 14, 15], [18, 19, None], [26, 33, None], [40, 41, 47]]      # chỉ số icon kỹ năng; None = ô trống
    plv = [[0, 2, 0], [1, 3, 0], [0, 4, 0], [2, 0, 1]]
    for r in range(4):
        y = gy + r * rowh
        cv.text(x0 + 1, y + (m.cell - m.spx) // 2, ('0', '4', '8', '12')[r], m.spx, C['dim'])
        for c in range(3):
            x = gx + c * cs; k = nodes[r][c]
            if k is None:
                cv.rect((x, y, x + m.cell, y + m.cell), (18, 26, 40, 255)); cv.rect((x + 1, y + 1, x + m.cell - 1, y + m.cell - 1), (12, 18, 30, 255)); continue
            w_cell(cv, x, y, (k, 0, '', False), selected=((r, c) == sel))
            if plv[r][c] == 0: cv.alpha_rect((x + 1, y + 1, x + m.cell - 1, y + m.cell - 1), (6, 10, 18, 150))
            pips(cv, x, y + m.cell - 2 - max(2, s + 1), m.cell, plv[r][c], 5)
            cv.hit('node_%d_%d' % (r, c), (x, y, x + m.cell, y + m.cell), 'con trỏ nút (hàng %d, cột %d) rồi KeyPressed(-5)' % (r, c))
        if r < 3 and rowh > m.cell:                                       # đường nối tiền đề giữa các hàng
            cx = gx + cs + m.cell // 2
            cv.rect((cx, y + m.cell, cx + max(1, s), y + rowh), C['line'])
    w_scrollbar(cv, g['sbx'], gy, gy + 3 * rowh + m.cell, 0, 5, 4)
    cv.hit('grid_scroll', (gx, gy, g['sbx'] + m.sbw, gy + 3 * rowh + m.cell), 'kéo dọc = cuộn hàng (Touch mới)')
    # cột phải: Đầu tư nhánh + chi tiết nút + nút đầu tư
    rx0 = g['rx0']; ty = gy
    cv.text(rx0, ty, ct(81), m.px, C['dim'], maxw=x1 - rx0 - 24); cv.text(x1, ty, '0', m.px, C['gold'], anchor='r', shadow=True)
    ty += m.lp + 2
    by = y1 - m.btnH
    box = (rx0, ty, x1, by - m.gap)
    nm, ds = SK_LIST[22]
    w_info(cv, box, nm, '%s 3/5' % ct(13), ds)
    cv.hit('detail', box, 'mô tả: kéo dọc cuộn')
    w_button(cv, (rx0, by, x1, by + m.btnH), ct(82), 'primary', 'invest', 'KeyPressed(-5): đầu tư 1 điểm vào nút (HeroSkillInfo.level++ + CalcStatusComputation)')
    return cv

# ---------------------------------------------------------------- Liên kích (4 ô chuỗi + 5 ứng viên/trang, cuộn ngang)
def screen_combo(m, sel=1):
    cv, _ = new_screen(m, TAB_NAMES[5], 5); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    x0, y0, x1, y1 = content_rect(m, body)
    n = 4; c = m.cell
    gapx = max(m.gap, c // 2)
    rowW = n * c + (n - 1) * gapx; hx = (x0 + x1 - rowW) // 2
    ly = y0 + 1
    for i in range(n):
        x = hx + i * (c + gapx)
        cv.text(x + c // 2, ly, ct(140 + i), m.px, C['gold'] if i == sel else C['dim'], anchor='c', shadow=(i == sel), maxw=c + gapx - 2)
    sy = ly + m.lp + 2
    ic = [0, 1, None, None]
    for i in range(n):
        x = hx + i * (c + gapx)
        w_cell(cv, x, sy, (ic[i], 0, '', False) if ic[i] is not None else None, selected=(i == sel))
        if ic[i] is None: cv.icon('ui', 22 if False else 16, 0, 0, 1, tint=(52, 66, 92), center=(x + c // 2, sy + c // 2)) if False else None
        if i < n - 1:                                                   # mũi tên chuỗi
            ax = x + c + gapx // 2; ay = sy + c // 2; k = max(3, s * 2 + 1)
            ImageDraw.Draw(cv.im).polygon([(ax - k // 2, ay - k), (ax - k // 2, ay + k), (ax + k // 2 + 1, ay)], fill=C['orange_hi'])
        cv.hit('combo%d' % i, (x, sy, x + c, sy + c), 'đặt ô liên kích [this+0xe]=%d rồi KeyPressed(-5): mở dãy ứng viên (HERO::SetComboSkillBelt khi chọn)' % i)
    # đường nối xuống dãy ứng viên
    cxm = hx + sel * (c + gapx) + c // 2
    cy0 = sy + c + 1; cy1 = cy0 + m.gap + 4
    cv.rect((cxm, cy0, cxm + max(1, s), cy1), C['orange'])
    py = cy1 + 1
    aw = c
    prw = aw + m.gap + 5 * c + 4 * m.cg + m.gap + aw; px0 = (x0 + x1 - prw) // 2
    pick = [0, 1, 2, 3, 4]
    w_arrow(cv, (px0, py, px0 + aw, py + c), 'l', True, 'prev', 'KeyPressed(TRÁI): lùi ứng viên')
    for i in range(5):
        x = px0 + aw + m.gap + i * (c + m.cg)
        w_cell(cv, x, py, (pick[i], 0, '', False), selected=(i == 0))
        cv.hit('cand%d' % i, (x, py, x + c, py + c), 'đặt con trỏ ứng viên = trang*5+%d rồi KeyPressed(-5) = HERO::SetComboSkillBelt(slot,skill)' % i)
    ax = px0 + aw + m.gap + 5 * c + 4 * m.cg + m.gap
    w_arrow(cv, (ax, py, ax + aw, py + c), 'r', True, 'next', 'KeyPressed(PHẢI): tiến ứng viên')
    ny = py + c + m.gap
    cv.text((x0 + x1) // 2, ny, SK_LIST[0][0], m.px, C['white'], shadow=True, anchor='c', maxw=x1 - x0); ny += m.lp
    w_hscroll(cv, px0 + aw + m.gap, ny + 1, px0 + aw + m.gap + 5 * c + 4 * m.cg, 0, 12, 5); ny += m.sbw + m.gap + 2
    by = y1 - m.btnH
    hw = (x1 - x0 - m.gap) // 2
    w_button(cv, (x0, by, x0 + hw, by + m.btnH), U.strip_hint(ct(153)), 'normal', 'clear', 'phím 0 -> bỏ chọn ô liên kích')
    w_button(cv, (x1 - hw, by, x1, by + m.btnH), ct(56), 'primary', 'ok', 'KeyPressed(-5)')
    assert ny <= by, ('liên kích tràn', ny, by)
    return cv

# ---------------------------------------------------------------- Tinh linh (2 tab con: Cơ bản/Trang bị | Kỹ năng Tinh Linh)
def screen_spirit(m, tab=0):
    cv, _ = new_screen(m, TAB_NAMES[6], 6); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    x0, y0, x1, y1 = content_rect(m, body); cw = x1 - x0
    sh = w_subtabs(cv, x0, y0, x1, [ct(158), ct(159)], tab, 'subtab', 'đổi tab con (2): KeySpiritMenu trái/phải ở hàng tab')
    ty = y0 + sh + m.gap
    if tab == 0:
        lw = round(cw * 0.46)
        yy = w_summary(cv, x0, ty, x0 + lw, y1, bars=False, spirit=True)
        cv.text(x0 + m.pad, yy, SPIRIT_POWER, m.px, C['dim'], maxw=lw - 2 * m.pad - 30)
        cv.text(x0 + lw - m.pad, yy, '50', m.px, C['gold'], anchor='r', shadow=True); yy += m.lp + 4
        w_bar(cv, x0 + m.pad, yy, lw - 2 * m.pad, ct(12), 0, 315, C['exp']); yy += m.barH + m.pad
        assert yy <= y1, ('cột Tinh linh tràn', yy, y1)
        # phải: 2 ô trang bị Tinh linh
        rx0 = x0 + lw + m.gap + 2; ry = ty
        cv.text(rx0, ry, ct(163), m.px, C['dim'], maxw=x1 - rx0); ry += m.lp + m.gap
        for i in range(2):
            x = rx0 + i * (m.pitch); 
            w_cell(cv, x, ry, (232, 0, '&', True) if i == 0 else None, selected=(i == 0))
            if i == 1: cv.icon('icon', 224, x + (m.cell - 20 * s) // 2, ry + (m.cell - 20 * s) // 2, s, tint=(52, 66, 92))
            cv.hit('slot%d' % i, (x, ry, x + m.cell, ry + m.cell), 'đặt con trỏ ô trang bị Tinh linh=%d + KeyPressed(-5) (gắn: OpenSpiritEquipFilter; gỡ: Process -> ClearEquipItemSpirit)' % i)
        ry += m.cell + m.gap
        box = (rx0, ry, x1, y1)
        w_info(cv, box, equip_item(10, 0), ct(163), ITEMS[('c/csv/item_10.dat', 0)].get(12, ''))
        cv.hit('detail', box, 'chỉ hiển thị')
    else:
        # 4 kỹ năng Tinh linh đã đăng ký (chọn bằng 4 phím hướng) + mô tả
        sp = SKILLS['spi']; c = m.cell
        rowW = 4 * c + 3 * m.cg; hx = x0
        icons = [150, 151, 152, 153]
        for i in range(4):
            x = hx + i * (c + m.cg)
            w_cell(cv, x, ty, (icons[i], 0, '', False), selected=(i == 1))
            cv.text(x + 2, ty + 1, ('▲', '◀', '▶', '▼')[i] if False else str(i + 1), m.spx, C['white'], shadow=True)
            cv.hit('spskill%d' % i, (x, ty, x + c, ty + c), 'chạm = chọn kỹ năng Tinh linh %d (4 phím hướng gốc)' % i)
        nx = hx + rowW + m.gap + 2
        cv.text(nx, ty, ct(305).split(';')[0][:0] + ct(161), m.px, C['dim'], maxw=x1 - nx) if False else None
        # thanh năng lượng Tinh linh + chú thích
        w_bar(cv, nx, ty + (c - m.barH) // 2, x1 - nx, ct(162)[:0] + 'SP', 3, 5, C['green'] if 'green' in C else C['exp'])
        ty2 = ty + c + m.gap
        box = (x0, ty2, x1, y1 - m.btnH - m.gap)
        nm, ds = sp[1]
        w_info(cv, box, nm, ct(13) + ' 3', ds)
        cv.hit('detail', box, 'chỉ hiển thị')
        w_button(cv, (x0, y1 - m.btnH, x1, y1), ct(55), 'primary', 'use', 'KeyPressed(-5) -> HERO::LearnSpiritSkill / dùng kỹ năng Tinh linh')
    return cv

# ---------------------------------------------------------------- Nhiệm vụ (2 tab con: hiện tại | nhánh)
QSEL = max(QUESTS, key=lambda r: len(QUESTS[r][1]))
QLONGN = max(QUESTS, key=lambda r: U.sw(18, QUESTS[r][0]))

def quest_rows(sel_rec):
    ids = [0, 1, QLONGN, sel_rec, 7, 9]; seen = []
    for i in ids:
        if i not in seen: seen.append(i)
    return seen

def screen_quest(m, tab=0):
    cv, _ = new_screen(m, TAB_NAMES[7], 7); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    x0, y0, x1, y1 = content_rect(m, body); cw = x1 - x0
    sh = w_subtabs(cv, x0, y0, x1, ['Nhiệm vụ hiện tại', 'Nhánh nhiệm vụ'], tab, 'subtab', 'đổi tab con (2): KeyQuestMenu trái/phải')
    ty = y0 + sh + m.gap
    ids = quest_rows(QSEL)
    if tab == 0:
        lw = round(cw * 0.50); rowh = m.rowH + 2 * s
        rows = ids; sel = rows.index(QSEL)
        for i, ri in enumerate(rows):
            ry = ty + i * (rowh + 2)
            if ry + rowh > y1: break
            nm = QUESTS[ri][0]
            r = (x0, ry, x0 + lw - m.sbw - 3, ry + rowh)
            dot = (130, 220, 120, 255) if i != 2 else (230, 200, 60, 255)
            cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), *(((250, 160, 36, 255), (196, 96, 12, 255)) if i == sel else (C['cell'], C['cell2'])))
            d = max(5, 2 * s + 4); cv.rect((r[0] + 4, r[1] + (rowh - d) // 2, r[0] + 4 + d, r[1] + (rowh - d) // 2 + d), dot)
            cv.text(r[0] + 4 + d + 4, r[1] + (rowh - m.px) // 2, nm, m.px, C['white'], shadow=(i == sel), maxw=r[2] - r[0] - d - 12)
            cv.hit('quest%d' % i, r, 'đặt con trỏ nhiệm vụ=%d (KeyQuestMenu) rồi KeyPressed(-5) = theo dõi (SetMapMode/QuestSetStatus)' % i)
        w_scrollbar(cv, x0 + lw - m.sbw, ty, y1, 0, 12, 6)
        box = (x0 + lw + m.gap, ty, x1, y1)
        nm, ds, kind = QUESTS[QSEL]
        w_info(cv, box, nm, kind, ds)
        cv.hit('detail', box, 'chi tiết: kéo dọc cuộn')
    else:
        # cây nhánh: chuỗi nút nối dọc
        bw = min(cw - 2 * m.pad, 2 * (m.px * 14)); bw = min(bw, cw - 2 * m.pad); bx = (x0 + x1 - bw) // 2
        nh = m.lp + 2 * s + 6; gp = m.gap + 2
        chain = [0, 1, QLONGN, QSEL]
        for i, ri in enumerate(chain):
            y = ty + i * (nh + gp)
            if y + nh > y1: break
            r = (bx, y, bx + bw, y + nh)
            st = ('done', 'done', 'cur', 'lock')[i]
            if st == 'cur': inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
            elif st == 'done': inner = cv.frame(r, rad=m.rad, orange=False, thick=1); cv.vgrad(inner, (34, 60, 52, 255), (22, 40, 36, 255))
            else: inner = cv.frame(r, rad=m.rad, orange=False, thick=1); cv.vgrad(inner, (30, 36, 48, 255), (20, 24, 34, 255))
            col = C['white'] if st != 'lock' else C['disabled']
            cv.text((r[0] + r[2]) // 2, y + (nh - m.px) // 2, QUESTS[ri][0], m.px, col, shadow=(st == 'cur'), anchor='c', maxw=bw - 2 * (m.bt + 3) - 4)
            cv.hit('node%d' % i, r, 'đặt con trỏ nút nhánh=%d (KeyQuestMenu) + KeyPressed(-5): xem chi tiết' % i)
            if i < len(chain) - 1 and y + nh + gp < y1:
                cv.rect(((r[0] + r[2]) // 2, y + nh, (r[0] + r[2]) // 2 + max(1, s), y + nh + gp), C['orange'])
    return cv

# ---------------------------------------------------------------- Hệ thống
OPT = [(204, 321), (205, 322), (206, 321), (207, 190), (208, 321), (209, 191)]

def screen_system(m, mode='main', popup=None):
    cv, _ = new_screen(m, TAB_NAMES[8], 8); s = m.s
    body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)
    x0, y0, x1, y1 = content_rect(m, body); cw = x1 - x0
    if mode == 'main':
        labs = [ct(189), ct(170), ct(172), ct(192)]
        bw = min(cw, round(cw * 0.62) if not m.hd else 420); bx = (x0 + x1 - bw) // 2
        bh = m.btnH + 2 * s; gp = m.gap + m.pad // 2
        tot = 4 * bh + 3 * gp; by = y0 + (y1 - y0 - tot) // 2
        for i, l in enumerate(labs):
            w_button(cv, (bx, by + i * (bh + gp), bx + bw, by + i * (bh + gp) + bh), l, 'primary' if i == 0 else 'normal', ('save', 'option', 'help', 'title')[i],
                     'đặt con trỏ=%d + KeyPressed(-5): %s' % (i, ('popup xác nhận -> Process: HERO::SaveAll', 'SetSubState(1)', 'SetSubState(2/3)', 'popup xác nhận -> về màn hình chính')[i]))
        if popup == 'save': w_popup(cv, ct(186), yes_no=True)
        if popup == 'title': w_popup(cv, ct(187), yes_no=True)
    elif mode == 'option':
        rowh = m.rowH + 2 * s; gp = 3
        bs = rowh; vw = max(U.sw(m.px, ct(i)) for i in (321, 322, 323, 190, 191)) + 2 * m.pad
        for i, (li, vi) in enumerate(OPT):
            ry = y0 + i * (rowh + gp)
            r = (x0, ry, x1, ry + rowh)
            sel = (i == 1)
            cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), *(((250, 160, 36, 255), (196, 96, 12, 255)) if sel else (C['cell'], C['cell2'])))
            cv.text(r[0] + m.pad, ry + (rowh - m.px) // 2, ct(li), m.px, C['white'], shadow=sel, maxw=cw - 2 * bs - vw - 4 * m.pad)
            rx = x1 - bs - m.cg; lx = rx - m.cg - vw - m.cg - bs
            w_arrow(cv, (lx, ry, lx + bs, ry + rowh), 'l', True, 'opt%d_prev' % i, 'đặt con trỏ=%d + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn)' % i)
            cv.rect((lx + bs + m.cg, ry + 1, rx - m.cg, ry + rowh - 1), (10, 16, 26, 255))
            cv.text((lx + bs + m.cg + rx - m.cg) // 2, ry + (rowh - m.px) // 2, ct(vi), m.px, C['gold'], anchor='c', maxw=vw)
            w_arrow(cv, (rx, ry, rx + bs, ry + rowh), 'r', True, 'opt%d_next' % i, 'đặt con trỏ=%d + KeyPressed(PHẢI)' % i)
            cv.hit('opt%d' % i, (x0, ry, lx, ry + rowh), 'đặt con trỏ hàng tuỳ chọn=%d' % i)
        assert y0 + 6 * (rowh + gp) <= y1, ('cài đặt tràn', y0 + 6 * (rowh + gp), y1)
    else:  # help: danh sách tiêu đề + nội dung
        titles = [v[0] for v in HELP.values()]
        lw = round(cw * 0.42); rowh = m.rowH; sel = 5
        nrow = (y1 - y0) // (rowh + 2)
        first = 3
        for i in range(nrow):
            k = first + i
            if k >= len(titles): break
            ry = y0 + i * (rowh + 2); r = (x0, ry, x0 + lw - m.sbw - 3, ry + rowh)
            cv.rect(r, C['ink']); cv.vgrad((r[0] + 1, r[1] + 1, r[2] - 1, r[3] - 1), *(((250, 160, 36, 255), (196, 96, 12, 255)) if k == sel else (C['cell'], C['cell2'])))
            t = titles[k].replace(';', ' ').replace('/', ' ')
            cv.text(r[0] + m.pad, ry + (rowh - m.px) // 2, t, m.px, C['white'], shadow=(k == sel), maxw=r[2] - r[0] - 2 * m.pad)
            cv.hit('help%d' % i, r, 'đặt con trỏ danh sách trợ giúp=%d (KeySystemMenu) + KeyPressed(-5)' % k)
        w_scrollbar(cv, x0 + lw - m.sbw, y0, y1, first, len(titles), nrow)
        box = (x0 + lw + m.gap, y0, x1, y1)
        w_info(cv, box, titles[sel].replace('/', ' '), None, list(HELP.values())[sel][1])
        cv.hit('detail', box, 'nội dung: kéo dọc cuộn (gốc: phím lên/xuống)')
    return cv

# ---------------------------------------------------------------- popup đặc thù InGameMenu
def screen_popup_detail(m):
    """popup 'chi tiết vật phẩm' (SetPopupState kèm ItemInfo*): tên, loại, chỉ số, mô tả cuộn, OK"""
    cv = M.screen_bag(m); s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw = round(0.78 * m.W) if not m.hd else 640; pw = min(pw, m.W - 2 * m.mg)
    ph = m.H - 2 * m.mg - (0 if not m.hd else 40)
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 250))
    by = inner[3] - m.pad - m.btnH
    box = (inner[0] + m.pad, inner[1] + m.pad, inner[2] - m.pad, by - m.gap)
    w_info(cv, box, item_name(*LONG_DESC), ct(96), ITEMS[LONG_DESC][15])
    cv.hit('detail', box, 'mô tả: kéo dọc cuộn')
    bw = max(U.sw(m.px, ct(56)) + 4 * m.pad, (inner[2] - inner[0]) // 3)
    w_button(cv, ((m.W - bw) // 2, by, (m.W + bw) // 2, by + m.btnH), ct(56), 'primary', 'popup_ok', 'KeyPressed(-5) -> SetPopupState(0)')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua')
    return cv

def screen_popup_class(m):
    """popup chọn lớp (StateInGameMenu::ChangeHeroClass, từ Process Nhiệm vụ): danh sách lớp trong class.dat"""
    cv = screen_quest(m, 0)
    labs = CLASSES
    w_menu_popup(cv, ct(176), labs, sel=1, disabled=(0,))
    return cv

def screen_popup_ok(m):
    """popup chỉ OK dài nhất (common_text 273) trên Hệ thống: kiểm cao ≤ khung"""
    cv = screen_system(m, 'main')
    w_popup(cv, ct(273), yes_no=False)
    return cv

# ---------------------------------------------------------------- danh sách màn
SCREENS = [  # (tên file, hàm vẽ, nhãn)
    ('nhan_vat', lambda m: screen_char(m, 0), 'Nhân vật: cộng điểm (STR/AGI/VIT/INT), tóm tắt'),
    ('nhan_vat_co_ban', lambda m: screen_char(m, 1), 'Nhân vật: trang chi tiết chỉ số (13 hàng)'),
    ('trang_bi', lambda m: screen_equip(m), 'Trang bị: 8 ô 4x2 + chi tiết + chỉ số'),
    ('trang_bi_menu', lambda m: screen_equip(m, True), 'Trang bị: menu Trang bị/Tháo (popup đặc thù)'),
    ('tui_do_chon', lambda m: screen_picker(m, 2, mode='filter'), 'Chế độ con Túi đồ SubState>=3: chọn vật phẩm lọc (5/trang, cuộn ngang)'),
    ('tui_do_phim_tat', lambda m: screen_picker(m, 1, mode='belt'), 'Chế độ con Túi đồ SubState 2: gán ô nhanh (5 ô)'),
    ('tui_do_menu', lambda m: screen_bag_menu(m, 'act'), 'Chế độ con Túi đồ SubState 1: menu hành động'),
    ('ky_nang', lambda m: screen_skill(m), 'Kỹ năng: 5 tab con, lưới 4x4 cuộn, mô tả dài nhất'),
    ('ky_nang_phim_tat', lambda m: screen_skill(m, assign=True), 'Kỹ năng: gán ô nhanh'),
    ('dac_tinh', lambda m: screen_prop(m), 'Đặc tính: 3 nhánh + Điểm, lưới 3x4'),
    ('lien_kich', lambda m: screen_combo(m), 'Liên kích: 4 ô chuỗi + 5 ứng viên/trang'),
    ('tinh_linh', lambda m: screen_spirit(m, 0), 'Tinh linh: Cơ bản/Trang bị'),
    ('tinh_linh_ky_nang', lambda m: screen_spirit(m, 1), 'Tinh linh: kỹ năng Tinh Linh'),
    ('nhiem_vu', lambda m: screen_quest(m, 0), 'Nhiệm vụ: danh sách + chi tiết (mô tả dài nhất)'),
    ('nhiem_vu_nhanh', lambda m: screen_quest(m, 1), 'Nhiệm vụ: nhánh nhiệm vụ'),
    ('he_thong', lambda m: screen_system(m, 'main'), 'Hệ thống: 4 nút'),
    ('he_thong_cai_dat', lambda m: screen_system(m, 'option'), 'Hệ thống: Cài đặt (6 tuỳ chọn)'),
    ('he_thong_tro_giup', lambda m: screen_system(m, 'help'), 'Hệ thống: Trợ giúp (danh sách + nội dung)'),
    ('he_thong_popup_luu', lambda m: screen_system(m, 'main', 'save'), 'Hệ thống: popup Lưu (popup chung)'),
    ('popup_chi_tiet', screen_popup_detail, 'Popup đặc thù: chi tiết vật phẩm'),
    ('popup_chon_lop', screen_popup_class, 'Popup đặc thù: chọn lớp (danh sách)'),
    ('popup_ok_dai', screen_popup_ok, 'Popup chỉ OK, chuỗi dài nhất'),
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
def tabbody(m): return (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH - m.tabH)

def verify_b2a(errors):
    L = []
    ok = lambda b: 'ĐẠT' if b else 'TRÀN'
    for (W, H) in FRAMES:
        m = metrics(W, H); body = tabbody(m); x0, y0, x1, y1 = content_rect(m, body); cw = x1 - x0; s = m.s
        L.append('== %dx%d  font %dpx / nhỏ %dpx, icon x%d ==' % (W, H, m.px, m.spx, m.s))
        scratch = lambda: Cv(m, size=(W, H))
        # --- tab con
        for nm, labs in (('Kỹ năng 5 tab', SUB5), ('Đặc tính 4 tab', [ct(68), ct(69), ct(70), ct(14)]), ('Tinh linh 2 tab', [ct(158), ct(159)]),
                         ('Nhiệm vụ 2 tab', ['Nhiệm vụ hiện tại', 'Nhánh nhiệm vụ'])):
            n = len(labs); tw = (cw - (n - 1) * 2) // n - 2 * (m.bt + 3) - 2
            wm = max(U.sw(m.px, l) for l in labs)
            L.append('  tab con %s: nhãn dài nhất %d px <= %d px: %s' % (nm, wm, tw, ok(wm <= tw)))
        # --- nhân vật
        stat_lbl = [ct(i) for i in range(33, 46)]; colw = (cw - (m.gap + 2)) // 2
        wm = max(U.sw(m.px, l) for l in stat_lbl); val = U.sw(m.px, '9999')
        L.append('  nhân vật trang chi tiết (13 nhãn): rộng nhất %d px + giá trị %d px <= cột %d px: %s' % (wm, val, colw - 2 * m.pad, ok(wm + val + 6 <= colw - 2 * m.pad)))
        # --- trang bị: tên vật phẩm trong hộp chi tiết, mọi vật phẩm trang bị (item_00..09)
        g = equip_geom(m, body); box = (g['dx'], g['dy'], x1, g['dy'] + g['dh']); ix0, ix1, p, tw = info_geom(m, box)
        eq = [(U.clean_name(v[2])[0]) for (f, r), v in ITEMS.items() if f in ['c/csv/item_%02d.dat' % i for i in range(10)]]
        mx = max(eq, key=lambda t: len(U.wrap(m.px, t, tw)) * 10000 + U.sw(m.px, t))
        L.append('  trang bị (%d tên): nhiều dòng nhất %d (giới hạn 2) "%s", hộp chữ %d px: %s' % (len(eq), len(U.wrap(m.px, mx, tw)), mx, tw, ok(len(U.wrap(m.px, mx, tw)) <= 2)))
        # --- kỹ năng
        g = skill_geom(m, body); box = (g['dx'], g['gy'], x1, g['dy1']); ix0, ix1, p, tw = info_geom(m, box)
        nl = [len(U.wrap(m.px, U.clean_name(a[2])[0], tw)) for a in ALL_SKILLS]; wmn = max(ALL_SKILLS, key=lambda a: U.sw(m.px, a[2]))
        c = scratch(); tot, view = w_info(c, box, SK_LIST[LONGD][0], 'LV 5/5', SK_LIST[LONGD][1])
        cnt = {}
        for a in ALL_SKILLS:
            pl, _ = parse_rich(a[3]); n = len(rich_lines(m.px, pl, tw)); cnt[n] = cnt.get(n, 0) + 1
        over = sum(v for k, v in cnt.items() if k > view)
        L.append('  kỹ năng (%d): tên rộng nhất %d px "%s" (<=2 dòng: %s); mô tả: phân bố số dòng %s, hộp hiện %d dòng, %d mô tả dài hơn -> cuộn dọc có thanh cuộn, không cắt' % (
            len(ALL_SKILLS), U.sw(m.px, wmn[2]), wmn[2], ok(max(nl) <= 2), ', '.join('%d:%d' % (k, cnt[k]) for k in sorted(cnt)), view, over))
        c = scratch(); w_info(c, box, SK_LIST[LONGN][0], 'LV 5/5', SK_LIST[LONGN][1])
        # --- đặc tính (hộp hẹp hơn)
        pg = prop_geom(m, body); pbox = (pg['rx0'], pg['gy'] + m.lp + 2, x1, y1 - m.btnH - m.gap); ix0, ix1, p, tw2 = info_geom(m, pbox)
        pl = max(len(rich_lines(m.px, parse_rich(a[3])[0], tw2)) for a in ALL_SKILLS)
        c = scratch(); _, view2 = w_info(c, pbox, SK_LIST[22][0], 'LV 3/5', SK_LIST[22][1])
        L.append('  đặc tính: hộp mô tả rộng %d px, hiện %d dòng; mô tả dài nhất %d dòng -> %s' % (tw2, view2, pl, 'vừa' if pl <= view2 else 'cuộn dọc (thanh cuộn)'))
        # --- nhiệm vụ
        sh = m.px + 2 * (m.bt + 3) + 2; ty = y0 + sh + m.gap; lw = round(cw * 0.50); d = max(5, 2 * s + 4)
        lmax = (lw - m.sbw - 3) - d - 12; qn = max(QUESTS.values(), key=lambda v: U.sw(m.px, v[0]))[0]
        L.append('  nhiệm vụ (%d): tên rộng nhất %d px "%s" <= hàng danh sách %d px: %s' % (len(QUESTS), U.sw(m.px, qn), qn, lmax, ok(U.sw(m.px, qn) <= lmax)))
        qbox = (x0 + lw + m.gap, ty, x1, y1); ix0, ix1, p, tw3 = info_geom(m, qbox)
        c = scratch(); _, qv = w_info(c, qbox, QUESTS[QSEL][0], QUESTS[QSEL][2], QUESTS[QSEL][1])
        cntq = {}
        for v in QUESTS.values():
            n = len(rich_lines(m.px, parse_rich(v[1])[0], tw3)); cntq[n] = cntq.get(n, 0) + 1
        L.append('  nhiệm vụ mô tả: hộp hiện %d dòng (rộng %d px); phân bố số dòng %s; %d mô tả -> cuộn dọc' % (qv, tw3, ', '.join('%d:%d' % (k, cntq[k]) for k in sorted(cntq)), sum(v for k, v in cntq.items() if k > qv)))
        qnl = max(len(U.wrap(m.px, v[0], tw3)) for v in QUESTS.values())
        L.append('  nhiệm vụ tên trong hộp chi tiết: tối đa %d dòng (giới hạn 2): %s' % (qnl, ok(qnl <= 2)))
        # --- hệ thống: tuỳ chọn + trợ giúp
        rowh = m.rowH + 2 * s; vw = max(U.sw(m.px, ct(i)) for i in (321, 322, 323, 190, 191)) + 2 * m.pad
        om = cw - 2 * rowh - vw - 4 * m.pad; ol = max(U.sw(m.px, ct(i)) for i, _ in OPT)
        L.append('  hệ thống cài đặt: nhãn dài nhất %d px <= %d px: %s; 6 hàng cao %d <= %d: %s' % (ol, om, ok(ol <= om), 6 * (rowh + 3), y1 - y0, ok(6 * (rowh + 3) <= y1 - y0)))
        titles = [v[0].replace(';', ' ').replace('/', ' ') for v in HELP.values()]
        hl = (round(cw * 0.42) - m.sbw - 3) - 2 * m.pad; tm = max(titles, key=lambda t: U.sw(m.px, t))
        L.append('  hệ thống trợ giúp (%d tiêu đề): rộng nhất %d px "%s" <= %d px: %s' % (len(titles), U.sw(m.px, tm), tm, hl, ok(U.sw(m.px, tm) <= hl)))
        hbox = (x0 + round(cw * 0.42) + m.gap, y0, x1, y1); ix0, ix1, p, tw4 = info_geom(m, hbox)
        c = scratch(); _, hv = w_info(c, hbox, titles[5], None, list(HELP.values())[5][1])
        hover = sum(1 for v in HELP.values() if len(rich_lines(m.px, parse_rich(v[1])[0], tw4)) > hv)
        L.append('  trợ giúp nội dung: hộp hiện %d dòng, %d bài dài hơn -> cuộn dọc' % (hv, hover))
        # --- popup menu hành động / chọn lớp
        pw = round(0.55 * W) if not m.hd else 420; pw = min(pw, W - 2 * m.mg); bw = pw - 2 * (m.bt + 3) - 2 * (m.pad + 2 * s) - 2 * m.pad
        lab = max([ct(55), ct(54), ct(52), ct(53), ct(66), ct(67)] + CLASSES, key=lambda t: U.sw(m.px, t))
        L.append('  popup menu: nhãn dài nhất %d px "%s" <= nút %d px: %s' % (U.sw(m.px, lab), lab, bw, ok(U.sw(m.px, lab) <= bw)))
        ph = 2 * m.pad + 4 * s + m.lp + m.pad + 5 * m.btnH + 4 * m.gap + 2
        L.append('  popup chọn lớp (5 lựa chọn) cao %d <= khung %d: %s' % (ph, H - 2 * m.mg, ok(ph <= H - 2 * m.mg)))
    L.append('== lỗi bố cục khi dựng ảnh: %s ==' % (', '.join(errors) if errors else 'không'))
    return L

# ---------------------------------------------------------------- tổng hợp có nhãn
def contact_sheet(tiles, out):
    pad = 16; lab = 26; tw = 600
    rows = []
    for name, fn, label in SCREENS:
        a = tiles[(name, 400)].resize((600, 360), Image.NEAREST); b = tiles[(name, 1200)].resize((600, 270), Image.LANCZOS)
        rows.append((name, label, a, b))
    per = 2; cellw = 2 * tw + pad
    nr = (len(rows) + per - 1) // per
    H = pad + nr * (lab + 360 + pad)
    sheet = Image.new('RGB', (per * cellw + (per + 1) * pad, H), (22, 24, 32)); d = ImageDraw.Draw(sheet)
    for k, (name, label, a, b) in enumerate(rows):
        x = pad + (k % per) * (cellw + pad); y = pad + (k // per) * (lab + 360 + pad)
        d.text((x, y + 2), '%d. %s  --  %s    [400x240 hiện x1.5 | 1200x540 thu 1/2]' % (k + 1, name, label), fill=(255, 210, 120))
        sheet.paste(a, (x, y + lab)); sheet.paste(b, (x + tw + pad, y + lab))
    sheet.save(out)

# ---------------------------------------------------------------- gộp vào hit_rects.json / verify.txt / LAYOUT.md
MARK = '<!-- B2A -->'

def merge_hits(out, hits):
    p = os.path.join(out, 'hit_rects.json'); old = json.load(open(p)) if os.path.exists(p) else {}
    names = [n for n, _, _ in SCREENS]
    old = {k: v for k, v in old.items() if not any(k.startswith(n + '_') and re.fullmatch(r'\d+x\d+', k[len(n) + 1:]) for n in names)}
    old.update(hits)
    json.dump(old, open(p, 'w'), ensure_ascii=False, indent=1)

def merge_text(out, fname, block, marker):
    p = os.path.join(out, fname); t = open(p, encoding='utf-8').read() if os.path.exists(p) else ''
    if marker in t: t = t[:t.index(marker)]
    if t and not t.endswith('\n'): t += '\n'
    open(p, 'w', encoding='utf-8').write(t + marker + '\n' + block)

def main():
    hits, tiles, errors = render_all(OUT)
    merge_hits(OUT, hits)
    vl = verify_b2a(errors)
    merge_text(OUT, 'verify.txt', '\n'.join(['Kiểm trên TOÀN BỘ dữ liệu thật, 4 khung; "cuộn" = thanh cuộn, không cắt chữ.'] + vl) + '\n', '# ===== b2a (mock_b2a.py) =====')
    contact_sheet(tiles, os.path.join(OUT, 'tong_hop_b2a.png'))
    import layout_b2a
    layout_b2a.write(OUT, hits, SCREENS, metrics, FRAMES)
    print('\n'.join(vl))

if __name__ == '__main__':
    main()
