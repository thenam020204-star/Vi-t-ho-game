#!/usr/bin/env python3
"""T17b2 lô b2b: mockup NPC (8 cửa sổ + popup), Cash shop (+4 popup), Chuyên gia (2 màn + popup),
popup StateGame (hồi sinh/chiến thắng/thất bại), StateMap (2 màn).

Dùng lại viui.py + mock_menu.py + mock_b2a.py (KHÔNG sửa các ảnh đã có). CHẠY SAU mock_b2a.py:
    python3 -I mock_menu.py [OUT] && python3 -I mock_b2a.py [OUT] && python3 -I mock_b2b.py [OUT]
Ghi ảnh b2b rồi GỘP (idempotent) vào hit_rects.json / verify.txt / LAYOUT.md (phần sau dấu <!-- B2B -->).
Chữ Việt, tên vật phẩm, mô tả cash, tên khu vực, vật liệu chế biến đều là dữ liệu THẬT ($WORK/vi_android); số tiền/giá/cấp là số mẫu.
"""
import os, sys, json, re, random
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import viui as U
import mock_menu as M
import mock_b2a as B
from mock_menu import C, Cv, metrics, FRAMES, MAIN, ct, ITEMS, item_name, content_rect, w_window, w_button, w_scrollbar, w_bar, \
    w_cell, w_descbox, w_popup, popup_geometry, LONG_NAME
from mock_b2a import w_subtabs, w_info, w_panel, w_summary, recs, parse_rich, rich_lines
from PIL import Image, ImageDraw

OUT = sys.argv[1] if len(sys.argv) > 1 else os.path.dirname(os.path.abspath(__file__))

# ---------------------------------------------------------------- dữ liệu thật
ING = {ri: U.plain(t) for ri, o, t in U.dat_strings('c/csv/ingame_text.dat')}
def gt(i): return ING[i]
def nm(fn, ri): return ITEMS[('c/csv/' + fn, ri)][2]
def dsc(fn, ri):
    d = ITEMS[('c/csv/' + fn, ri)]; o = [k for k in d if k > 2]
    return d[max(o, key=lambda k: len(d[k]))] if o else None
CASH = {k: recs('c/csv/%s.dat' % k) for k in ('cash_single', 'cash_expert')}   # ri -> [tên, mô tả]
SMITH = recs('c/csv/smith_0.dat')                                              # ri -> [tên, vật liệu]
ZONES = {i: gt(i).replace(';', ' ') for i in range(0, 41)}
MONEY = 1234567
WEAP_ICON = [203, 204, 205, 206, 207, 208, 209, 210]

def fmt(n): return '{:,}'.format(n)
def strip_plain(s): return U.clean_name(s)[0]

# ---------------------------------------------------------------- khung chung
def win(m, title, dim=150):
    cv = Cv(m, dim=dim); body = w_window(cv, title, tabs=None)
    return cv, content_rect(m, body)

def coin_k(m): return max(1, m.s - 1) if m.hd else m.s

def money_at(cv, x1, y, text, col, h):
    """[xu] + số, căn phải tại x1, giữa theo chiều cao h"""
    m = cv.m; coin = U.mgr_img('ui', 16); k = coin_k(m); w = U.sw(m.px, text)
    cv.text(x1, y + (h - m.px) // 2, text, m.px, col, anchor='r')
    cv.icon('ui', 16, x1 - w - 3 - coin.width * k, y + (h - coin.height * k) // 2, k)

def strip(cv, x0, y0, x1, left, right=None, coin=True, lcol=None):
    m = cv.m; h = m.lp + 2 * m.s + 2
    cv.text(x0 + 2, y0 + 1, left, m.px, lcol or C['dim'], maxw=x1 - x0 - 4 - (U.sw(m.px, right) + 30 if right else 0))
    if right:
        if coin: money_at(cv, x1 - 2, y0, right, C['gold'], h - 1)
        else: cv.text(x1 - 2, y0 + 1, right, m.px, C['gold'], anchor='r')
    cv.rect((x0, y0 + h - 1, x1, y0 + h), C['orange_dk'])
    return y0 + h

def w_popup_c(cv, lines, yes_no=False, ok_label=None, title=None):
    """popup chung nhưng mỗi dòng có màu: lines = [(text, color)]; cùng công thức kích thước với popup_geometry"""
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw = round(0.70 * m.W) if not m.hd else 480; pw = min(pw, m.W - 2 * m.mg)
    tw = pw - 2 * (m.pad + 2 * s + 4)
    wl = []
    for t, col in lines:
        for l in U.wrap(m.px, t, tw): wl.append((l, col))
    ph = 2 * m.pad + 4 * s + len(wl) * m.lp + m.pad + m.btnH + 2
    assert ph <= m.H - 2 * m.mg, ('popup cao quá', ph, m.H)
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248))
    y = inner[1] + m.pad + 2 * s
    for l, col in wl:
        cv.text(m.W // 2, y, l, m.px, col, anchor='c', maxw=tw); y += m.lp
    by = inner[3] - m.pad - m.btnH; bx0 = inner[0] + m.pad + 2 * s; bx1 = inner[2] - m.pad - 2 * s
    if yes_no:
        mid = (bx0 + bx1) // 2; gg = m.pad
        w_button(cv, (bx0, by, mid - gg // 2, by + m.btnH), ct(50), 'primary', 'popup_yes', 'KeyPressed(-5) với con trỏ popup=Có')
        w_button(cv, (mid + gg // 2 + 1, by, bx1, by + m.btnH), ct(51), 'normal', 'popup_no', 'con trỏ popup=Không + KeyPressed(-5)')
    else:
        lab = ok_label or ct(56); bw = max(U.sw(m.px, lab) + 4 * m.pad, (bx1 - bx0) // 3)
        w_button(cv, ((bx0 + bx1 - bw) // 2, by, (bx0 + bx1 + bw) // 2, by + m.btnH), lab, 'primary', 'popup_ok', 'KeyPressed(-5)')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua (không đóng)')

# ---------------------------------------------------------------- khay vật phẩm chung (mua kỹ năng/vật phẩm, bán, bộ sưu tập, chế biến, cash, expert shop)
def entry(name, icon, rar=None, meta=None, desc=None, rows=None, price=None, banner=None, cnt=0, unknown=False, extra=None):
    clean, code = U.clean_name(name)
    return dict(name=name, icon=icon, rar=code if rar is None else rar, meta=meta, desc=desc, rows=rows, price=price, banner=banner, cnt=cnt,
                unknown=unknown, extra=extra)

def shelf(m, title, entries, sel=1, st=None, tabs=None, tab_sel=0, btn=None, btn_kind='primary', btn_hid='buy', btn_act='', pages=0,
          scroll_rows=5, dim=150, cell_act='đặt con trỏ ô=%d rồi KeyPressed(-5) (chạm lần 2 = OK gốc)'):
    cv, (x0, y0, x1, y1) = win(m, title, dim); s = m.s
    top = y0
    if st:
        top = strip(cv, x0, y0, x1, st[0], st[1] if len(st) > 1 else None, coin=(st[2] if len(st) > 2 else True), lcol=st[3] if len(st) > 3 else None) + m.gap
    if tabs:
        sh = w_subtabs(cv, x0, top, x1, tabs, tab_sel, 'subtab', 'đổi tab: ghi trường tab của Key* gốc rồi vẽ lại'); top += sh + m.gap
    gy = top; gh = 4 * m.pitch - m.cg
    assert gy + gh <= y1, ('lưới tràn', gy + gh, y1)
    if pages:
        gx = x0 + m.pitch + m.gap + 1
        for i in range(pages):
            r = (x0, gy + i * m.pitch, x0 + m.pitch, gy + i * m.pitch + m.cell)
            if i == 0: inner = cv.frame(r, rad=m.rad); cv.vgrad(inner, (252, 168, 40, 255), (200, 98, 12, 255))
            else: inner = cv.frame(r, rad=m.rad, orange=False); cv.vgrad(inner, (34, 50, 74, 255), (20, 30, 46, 255))
            cv.icon('ui', 1, 0, 0, s, center=((r[0] + r[2]) // 2, (r[1] + r[3]) // 2 - (s if m.hd else 0)))
            n = str(i + 1); cv.text(r[2] - 3 - U.sw(m.spx, n), r[3] - 3 - m.spx, n, m.spx, C['white'], shadow=True)
            cv.hit('page%d' % i, r, '[this+0xe]=%d (trang), con trỏ [this+0xf]=0' % i)
        dx = gx + m.gridW + m.gap + 1
    else:
        gx = x0; sbx = gx + m.gridW + 3
        w_scrollbar(cv, sbx, gy, gy + gh, 0, scroll_rows, 4)
        cv.hit('grid_scroll', (gx, gy, sbx + m.sbw, gy + gh), 'chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống)')
        dx = sbx + m.sbw + m.gap + 2
    for i in range(16):
        x = gx + (i % 4) * m.pitch; y = gy + (i // 4) * m.pitch
        e = entries[i] if i < len(entries) else None
        if e is None or e['unknown']:
            w_cell(cv, x, y, None, selected=(i == sel))
            if e is not None: cv.text(x + m.cell // 2, y + (m.cell - m.px) // 2, '?', m.px, C['dim'] if i != sel else C['white'], anchor='c', shadow=(i == sel))
        else:
            w_cell(cv, x, y, (e['icon'], e['cnt'], e['rar'], False), selected=(i == sel))
        cv.hit('cell%d' % i, (x, y, x + m.cell, y + m.cell), cell_act % i)
    e = entries[sel]
    by = y1 - m.btnH
    coin = U.mgr_img('ui', 16); prow = max(m.lp + 2, coin.height * coin_k(m) + 2)
    has_p = e['price'] is not None or e['banner']
    py = by - m.gap - prow
    box = (dx, gy, x1, (py - m.gap) if has_p else (by - m.gap))
    if e['unknown']:
        w_info(cv, box, '???', None, None)
    else:
        B.w_info(cv, box, e['name'], e['meta'], e['desc'], rows=e['rows'], extra=e['extra'])
    cv.hit('detail', box, 'chỉ hiển thị (chạm = chọn ô tương ứng)')
    if has_p:
        w_panel(cv, (dx, py, x1, py + prow))
        if e['banner']:
            cv.text((dx + x1) // 2, py + (prow - m.px) // 2, e['banner'], m.px, C['green'], anchor='c', maxw=x1 - dx - 6)
        else:
            money_at(cv, x1 - 4, py, fmt(e['price']), C['gold'] if e['price'] <= MONEY else C['red'], prow)
    if btn:
        w_button(cv, (dx, by, x1, by + m.btnH), btn, btn_kind, btn_hid, btn_act)
    cv.geo = dict(gx=gx, gy=gy, dx=dx, box=box)
    return cv

# ---------------------------------------------------------------- dữ liệu mẫu cho khay
SKILL_BOOKS = [entry(nm('item_16.dat', r), 225, '', ct(3), dsc('item_16.dat', r),
                     rows=[(ct(23), '%d' % (10 + 5 * (r // 3)))], price=[2500, 4000, 7500, 12000, 20000, 33000, 50000, 90000, 150000, 2500000][r % 10] if r < 10 else 5000 * (r + 1))
               for r in range(20)]
WEAP = []
for r in range(20):
    nme = nm('item_03.dat', r); WEAP.append(entry(nme, WEAP_ICON[r % 8], None, ct(95), None,
        rows=[(ct(221), str(5 + r)), (ct(228), str(40 + 11 * r)), (ct(225), ct(227) if r % 2 else ct(226))], price=800 * (r + 1) * (r + 1)))
WEAP[9] = entry(nm('item_03.dat', 62), 203, None, ct(95), None, rows=[(ct(221), '35'), (ct(228), '248'), (ct(225), ct(227))], price=95000)
POTS = [entry(nm('item_11.dat', r), 226 + r % 4, '', ct(96), dsc('item_11.dat', r), price=300 * (r + 1), cnt=0) for r in range(12)]
SALE = []
for i, b in enumerate(M.BAG):
    if i < 8:
        e = WEAP[i]; SALE.append(entry(e['name'], b[0], b[2], e['meta'], None, rows=e['rows'], price=max(100, e['price'] // 4), cnt=b[1]))
    else:
        p = POTS[i - 8]; SALE.append(entry(p['name'], b[0], b[2], p['meta'], p['desc'], price=max(10, p['price'] // 4), cnt=b[1]))
CASH_ICON = [226, 227, 228, 229, 230, 231, 223, 224, 225]
OFFLINE = ct(304).lstrip('^').rstrip('.')                        # "Bản offline: miễn phí"
def cash_entries(fn, n=None):
    d = CASH[fn]; out = []
    for ri in sorted(d):
        if n and ri >= n: break
        v = d[ri]
        if len(v) < 2 or v[0] in ('Cánh Thiên Thần(5)',) and False: continue
        out.append(entry(v[0], CASH_ICON[ri % 9], '', None, v[1], banner=OFFLINE))
    return out
CASH_S = cash_entries('cash_single'); CASH_E = [e for e in cash_entries('cash_expert') if e['desc'] and len(e['desc']) > 20][:8]

def mix_entries():
    out = []
    for ri in sorted(SMITH):
        v = SMITH[ri]
        if len(v) < 2 or v[0] == 'KHÔNG': continue
        mats = []
        for part in v[1].replace(';', ' ').split('/'):
            mm = re.match(r'^\s*(.*?)\s*(\d+)\s*$', part)
            if mm: mats.append((mm.group(1), int(mm.group(2))))
        rows = [(a, '%d/%d' % (min(b, (3 + ri + k) % 9), b)) for k, (a, b) in enumerate(mats[:3])]
        out.append(entry(v[0], WEAP_ICON[ri % 8], None, ct(150), None, rows=rows, price=None))
        if len(out) >= 16: break
    return out
MIX = mix_entries()

COLLECT = []
for r in range(16):
    e = WEAP[r]
    COLLECT.append(entry(e['name'], e['icon'], e['rar'], e['meta'], None, rows=e['rows'], unknown=(r not in (0, 1, 2, 4, 5))))

# ---------------------------------------------------------------- 8 cửa sổ NPC
def scr_skill_buy(m, popup=None):
    cv = shelf(m, ct(83), SKILL_BOOKS, sel=1, st=(ct(19), fmt(MONEY)), btn=ct(83), btn_hid='buy',
               btn_act='đặt con trỏ=ô + KeyPressed(-5) -> popup xác nhận -> Process: NpcShop::NewShopItemToBag + BagItem::IncreaseMoney(-giá)')
    if popup == 'tien':
        # ô chọn là sách đắt hơn số tiền: dựng lại với ô 9 (giá 2,500,000 > tiền)
        cv = shelf(m, ct(83), SKILL_BOOKS, sel=9, st=(ct(19), fmt(MONEY)), btn=ct(83), btn_kind='disabled', btn_hid='buy',
                   btn_act='đặt con trỏ=ô + KeyPressed(-5) -> popup (không đủ tiền)')
        w_popup(cv, ct(90), yes_no=False)
    return cv

def scr_item_buy(m, popup=None):
    cv = shelf(m, ct(84), WEAP, sel=9, st=(ct(19), fmt(MONEY)), tabs=[ct(95), ct(242), ct(96)], btn=ct(84), btn_hid='buy',
               btn_act='đặt con trỏ=ô + KeyPressed(-5) -> popup xác nhận -> Process: NpcShop::NewShopItemToBag / ItemTable::NewItemToBag')
    if popup == 'mua':
        e = WEAP[9]
        w_popup_c(cv, [(strip_plain(e['name']), C['gold']), (ct(91), C['white']), (fmt(e['price']), C['gold'])], yes_no=True)
    return cv

def scr_item_sale(m, popup=None):
    return shelf(m, ct(85), SALE, sel=2, st=(ct(19), fmt(MONEY)), pages=3, btn=ct(85), btn_hid='sell',
                 btn_act='KeyPressed(-5) -> popup xác nhận -> Process: BagItem::DeleteBagItemPosIndex + IncreaseMoney(+giá)')

def w_slot(cv, x0, y, x1, label, name, icon, rar, sel, hid, action, name_col=None):
    m = cv.m; rh = m.cell + 6
    w_panel(cv, (x0, y, x1, y + rh))
    w_cell(cv, x0 + 3, y + 3, (icon, 0, rar, False) if icon is not None else None, selected=sel)
    tx = x0 + 3 + m.cell + m.pad
    ty = y + (rh - (m.spx + m.px + 2)) // 2
    cv.text(tx, ty, label, m.spx, C['dim'], maxw=x1 - tx - m.pad)
    code = U.clean_name(name)[1]
    col = name_col or (U.RARITY[code] + (255,) if code in U.RARITY else C['white'])
    cv.text(tx, ty + m.spx + 2, strip_plain(name), m.px, col, shadow=name_col is None and bool(code), maxw=x1 - tx - m.pad)
    cv.hit(hid, (x0, y, x1, y + rh), action)
    return y + rh

def w_statrows(cv, x0, y0, x1, rows):
    """bảng k-v trong panel; rows = (nhãn, giá trị, màu, có xu). Trả y dưới panel"""
    m = cv.m; f = m.px if m.hd else m.spx; step = f + (6 if m.hd else 5); p = m.pad // 2 + 1
    h = 2 * p + 2 + len(rows) * step
    w_panel(cv, (x0, y0, x1, y0 + h))
    for i, (l, v, col, coin) in enumerate(rows):
        y = y0 + p + 1 + i * step
        cv.text(x0 + p + 2, y, l, f, C['dim'], maxw=(x1 - x0) // 2)
        if coin:
            c = U.mgr_img('ui', 16); k = 1
            w = U.sw(f, v); cv.text(x1 - p - 2, y, v, f, col, anchor='r'); cv.icon('ui', 16, x1 - p - 2 - w - 3 - c.width, y + (f - c.height) // 2, 1)
        else:
            cv.text(x1 - p - 2, y, v, f, col, anchor='r')
    return y0 + h

def scr_refine(m, high=False, popup=None):
    cv, (x0, y0, x1, y1) = win(m, ct(87) if high else ct(86)); s = m.s
    y = w_slot(cv, x0, y0, x1, ct(101), WEAP[9]['name'], 203, '`', True, 'slot_item',
               'chạm = mở chế độ chọn vật phẩm (OpenBagFilter/OpenEquipFilter) -> KeyRefine gốc') + m.gap
    y = w_slot(cv, x0, y, x1, ct(103) if high else ct(102), ct(262) + '3', 230, '', False, 'slot_stone',
               'chạm = mở chế độ chọn đá tôi luyện (OpenBagFilter)', name_col=C['white']) + m.gap
    y = w_statrows(cv, x0, y, x1, [(ct(104), '3.0%', C['green'], False), (ct(105), '+3 > +4', C['white'], False),
                                    (ct(19), fmt(MONEY), C['gold'], True), (ct(106), fmt(48000), C['gold'], True)]) + m.gap
    by = y1 - m.btnH
    assert y <= by, ('tôi luyện tràn', y, by)
    w_button(cv, (x0, by, x1, by + m.btnH), ct(107), 'primary', 'start', 'KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyItemRefine')
    if popup == 'ketqua': w_popup_c(cv, [(ct(117), C['red'])], yes_no=False)
    return cv

def scr_orb(m, popup=None):
    cv, (x0, y0, x1, y1) = win(m, ct(88)); s = m.s
    y = w_slot(cv, x0, y0, x1, ct(122), WEAP[9]['name'], 203, '`', True, 'slot_item', 'chạm = chọn vật phẩm cần kết hợp (OpenBagFilter)') + m.gap
    y = w_slot(cv, x0, y, x1, ct(123), nm('item_12.dat', 1), 230, '', False, 'slot_orb', 'chạm = chọn Orb (OpenBagFilter)', name_col=C['white']) + m.gap
    y = w_slot(cv, x0, y, x1, ct(124), ct(127), None, '', False, 'slot_sacrifice', 'chạm = chọn vật hiến tế (OpenBagFilter)', name_col=C['dim']) + m.gap
    y = w_statrows(cv, x0, y, x1, [(ct(104), '10.4%', C['green'], False)]) + m.gap
    by = y1 - m.btnH; hw = (x1 - x0 - m.gap) // 2
    assert y <= by, ('orb tràn', y, by)
    w_button(cv, (x0, by, x0 + hw, by + m.btnH), U.strip_hint(ct(130)), 'normal', 'clear', 'phím 0 -> bỏ vật hiến tế (KeyOrbCombine)')
    w_button(cv, (x1 - hw, by, x1, by + m.btnH), ct(88), 'primary', 'start', 'KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyOrbCombine')
    return cv

def scr_mix(m, popup=None):
    return shelf(m, ct(89), MIX, sel=max(range(len(MIX)), key=lambda i: len(MIX[i]['rows'] or [])), st=(ct(135), None, False, C['gold']), btn=ct(150), btn_hid='mix',
                 btn_act='KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyNormalMix (+ItemTable::NewItem/DeleteItem)', scroll_rows=4)

def scr_collection_menu(m):
    cv, (x0, y0, x1, y1) = win(m, ct(284)); s = m.s; cw = x1 - x0
    h = strip(cv, x0, y0, x1, ct(283), '12/338', coin=False)
    labs = [ct(269), ct(270), ct(271)]
    bw = min(cw, round(cw * 0.62) if not m.hd else 420); bx = (x0 + x1 - bw) // 2
    bh = m.btnH + 2 * s; gp = m.gap + m.pad // 2; tot = 3 * bh + 2 * gp; by = h + (y1 - h - tot) // 2
    for i, l in enumerate(labs):
        w_button(cv, (bx, by + i * (bh + gp), bx + bw, by + i * (bh + gp) + bh), l, 'primary' if i == 0 else 'normal', 'cat%d' % i,
                 'đặt con trỏ=%d + KeyPressed(-5) -> vào danh sách Đồ Giám %s' % (i, ('Vũ khí', 'Giáp', 'Trang sức')[i]))
    return cv

def scr_collection(m, popup=None):
    cv = shelf(m, ct(269), COLLECT, sel=1, st=(ct(284), '5/41', False), tabs=[ct(178), ct(179), ct(180), ct(181)], btn=ct(272), btn_hid='register',
               btn_act='KeyPressed(-5) -> popup xác nhận (273) -> Process: HERO::SetCollectionComplete + Mission::CheckCollection', scroll_rows=6)
    if popup == 'dang_ky': w_popup(cv, ct(273), yes_no=True)
    return cv

def scr_picker(m):
    """chế độ chọn vật phẩm trong NPC (OpenBagFilter): hàng 5 vật phẩm/trang + chi tiết (như b2a nhưng không có thanh tab)"""
    cv, (x0, y0, x1, y1) = win(m, ct(86)); s = m.s
    c = m.cell; g = m.cg; aw = c; n = 5
    rowW = aw + m.gap + n * c + (n - 1) * g + m.gap + aw; hx = x0 + (x1 - x0 - rowW) // 2
    cv.text((x0 + x1) // 2, y0 + 1, ct(112), m.px, C['gold'], shadow=True, anchor='c', maxw=x1 - x0)
    hy = y0 + m.lp + m.gap + 2; cx = hx + aw + m.gap
    B.w_arrow(cv, (hx, hy, hx + aw, hy + c), 'l', True, 'prev', 'KeyPressed(TRÁI): lùi 1 vật phẩm ([this+0x10]-1)')
    for i in range(5):
        xx = cx + i * (c + g); e = WEAP[i]
        w_cell(cv, xx, hy, (e['icon'], 0, e['rar'], False), selected=(i == 1))
        cv.hit('cand%d' % i, (xx, hy, xx + c, hy + c), 'đặt [this+0x10] = trang*5+%d rồi KeyPressed(-5) (chạm lần 2 = chọn)' % i)
    ax = cx + 5 * c + 4 * g + m.gap
    B.w_arrow(cv, (ax, hy, ax + aw, hy + c), 'r', True, 'next', 'KeyPressed(PHẢI): tiến 1 vật phẩm')
    sy = hy + c + m.gap
    B.w_hscroll(cv, cx, sy, cx + 5 * c + 4 * g, 6, 23, 5)
    cv.hit('hscroll', (cx, sy - 2, cx + 5 * c + 4 * g, sy + m.sbw + 2), 'chạm/kéo ngang: [this+0x10] theo vị trí (Touch mới)')
    ty = sy + m.sbw + m.gap + 1; by = y1 - m.btnH
    box = (x0, ty, x1, by - m.gap); e = WEAP[1]
    w_info(cv, box, e['name'], e['meta'], None, rows=e['rows'])
    cv.hit('detail', box, 'chỉ hiển thị')
    hw = (x1 - x0 - m.gap) // 2
    w_button(cv, (x0, by, x0 + hw, by + m.btnH), ct(56), 'primary', 'ok', 'KeyPressed(-5) = chọn vật phẩm (Process gốc)')
    w_button(cv, (x1 - hw, by, x1, by + m.btnH), U.strip_hint(ct(32)), 'normal', 'cancel', 'phím * / back: thoát chế độ chọn')
    return cv

# ---------------------------------------------------------------- Cash shop + Chuyên gia
def scr_cash(m, popup=None):
    cv = shelf(m, ct(245), CASH_S, sel=0, btn=ct(261).rstrip('!') + '!', btn_hid='buy', scroll_rows=8,
               btn_act='KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag')
    e = CASH_S[0]
    if popup == 'mua':
        w_popup_c(cv, [(strip_plain(e['name']), C['gold']), (ct(91), C['white']), (OFFLINE + '.', C['green'])], yes_no=True)
    elif popup == 'xong':
        w_popup_c(cv, [(strip_plain(e['name']), C['gold']), (ct(93), C['white'])], yes_no=False)
    elif popup == 'day':
        w_popup_c(cv, [(ct(92), C['white'])], yes_no=False)
    elif popup == 'khu_vuc':
        w_popup(cv, ct(331), yes_no=False)
    return cv

def scr_expert_ready(m, popup=None):
    cv, (x0, y0, x1, y1) = win(m, ct(195)); s = m.s; cw = x1 - x0; g = m.gap + 2
    lw = round(cw * 0.46)
    w_summary(cv, x0, y0, x0 + lw, y1)
    rx0 = x0 + lw + g
    yy = w_statrows(cv, rx0, y0, x1, [(ct(299), ct(196), C['gold'], False), (ct(185), '48', C['white'], False)])
    by = y1 - m.btnH
    w_button(cv, (rx0, by, x1, by + m.btnH), ct(166), 'primary', 'start', 'KeyPressed(-5) -> bắt đầu chế độ Chuyên gia (KeyReady/Process gốc)')
    b1 = by - m.gap - m.btnH; hw = (x1 - rx0 - m.gap) // 2
    assert yy <= b1, ('chuyen gia tràn', yy, b1)
    w_button(cv, (rx0, b1, rx0 + hw, b1 + m.btnH), ct(0), 'normal', 'char', 'đặt con trỏ=0 + KeyPressed(-5) (KeyReady): mở Nhân vật')
    w_button(cv, (x1 - hw, b1, x1, b1 + m.btnH), ct(244), 'normal', 'shop', 'đặt con trỏ=1 + KeyPressed(-5) (KeyReady): SetState(1) Cửa hàng')
    if popup == 'khong_dung':
        w_popup(cv, ct(246), yes_no=False)
    return cv

def scr_expert_shop(m, popup=None):
    cv = shelf(m, ct(244), CASH_E, sel=1, btn=ct(261).rstrip('!') + '!', btn_hid='buy', scroll_rows=3,
               btn_act='KeyPressed(-5) -> popup xác nhận -> Process: ItemTable::NewItemToBag (miễn phí offline)')
    e = CASH_E[1]
    if popup == 'mua':
        w_popup_c(cv, [(strip_plain(e['name']), C['gold']), (ct(91), C['white']), (OFFLINE + '.', C['green'])], yes_no=True)
    return cv

# ---------------------------------------------------------------- StateGame: popup trên cảnh chơi + kết quả
def scr_game(m, kind):
    dark = 200 if kind in ('hoi_sinh', 'hoi_sinh_xn', 'hoi_sinh_ok') else 90
    cv = Cv(m, dim=dark)
    if kind == 'hoi_sinh':
        w_popup_c(cv, [(ct(260), C['white']), (OFFLINE + '.', C['green'])], yes_no=True)
    elif kind == 'hoi_sinh_xn':
        w_popup(cv, ct(293), yes_no=True)
    elif kind == 'hoi_sinh_ok':
        w_popup_c(cv, [(ct(350).split(';')[0], C['white'])], yes_no=False, ok_label=ct(350).split(';')[-1].lstrip('&'))
    else:
        win_ = kind == 'chien_thang'
        w_result(cv, gt(327) if win_ else gt(328), C['gold'] if win_ else C['red'], [(gt(334), '12'), (gt(332), '01:24'), (gt(333), '63%'), (gt(338), fmt(12480))])
    return cv

def result_geometry(m, rows):
    pw = round(0.70 * m.W) if not m.hd else 480; pw = min(pw, m.W - 2 * m.mg)
    ph = 2 * m.pad + 4 * m.s + m.lp + 4 + len(rows) * (m.lp + 2) + m.pad + m.btnH + 2
    return pw, ph

def w_result(cv, title, tcol, rows):
    m = cv.m; s = m.s
    cv.alpha_rect((0, 0, m.W, m.H), (0, 0, 0, 120))
    pw, ph = result_geometry(m, rows); assert ph <= m.H - 2 * m.mg
    x0 = (m.W - pw) // 2; y0 = (m.H - ph) // 2
    inner = cv.frame((x0, y0, x0 + pw, y0 + ph), fill=(14, 22, 34, 248))
    y = inner[1] + m.pad + 2 * s; ix0 = inner[0] + m.pad + 2 * s; ix1 = inner[2] - m.pad - 2 * s
    cv.text(m.W // 2, y, title, m.px, tcol, shadow=True, anchor='c', maxw=ix1 - ix0); y += m.lp + 2
    cv.rect((ix0, y - 1, ix1, y), C['line']); y += 2
    for l, v in rows:
        cv.text(ix0, y, l, m.px, C['dim'], maxw=(ix1 - ix0) - U.sw(m.px, v) - 8); cv.text(ix1, y, v, m.px, C['white'], anchor='r'); y += m.lp + 2
    by = inner[3] - m.pad - m.btnH
    bw = max(U.sw(m.px, ct(56)) + 4 * m.pad, (ix1 - ix0) // 3)
    w_button(cv, ((m.W - bw) // 2, by, (m.W + bw) // 2, by + m.btnH), ct(56), 'primary', 'popup_ok', 'KeyPressed(-5) -> thoát màn kết quả (KeyGameOver/KeyExpert)')
    cv.hit('popup_modal', (0, 0, m.W, m.H), 'chạm ngoài hộp: bỏ qua')

# ---------------------------------------------------------------- StateMap
def blob(d, cx, cy, rx, ry, rnd, s):
    for k in range(7):
        ox = rnd.uniform(-0.5, 0.5) * rx; oy = rnd.uniform(-0.5, 0.5) * ry
        ex = rx * rnd.uniform(0.45, 0.8); ey = ry * rnd.uniform(0.45, 0.8)
        d.ellipse((cx + ox - ex - s, cy + oy - ey - s, cx + ox + ex + s, cy + oy + ey + s), fill=(30, 52, 64, 255))
    for k in range(7):
        rnd2 = random.Random(k * 7 + int(cx))
    return

ISL = [  # (id tên khu vực ingame_text, cx, cy, rx, ry) theo toạ độ tỉ lệ của ảnh gốc (kiemke ô 60)
    (33, .22, .45, .17, .26), (34, .40, .82, .10, .10), (38, .52, .30, .16, .18), (36, .80, .20, .13, .14),
    (39, .66, .62, .09, .15), (35, .86, .64, .11, .15), (37, .40, .55, .05, .06), (40, .62, .84, .07, .08)]

def world_art(cv, r, rnd):
    """NỀN MINH HOẠ bản đồ lớn (game dùng ảnh bản đồ gốc, vẽ vừa khung giữ tỉ lệ): biển + đảo; đủ để đặt nhãn/chấm vị trí"""
    m = cv.m; s = m.s; x0, y0, x1, y1 = r; w = x1 - x0; h = y1 - y0
    cv.vgrad(r, (30, 70, 140, 255), (16, 44, 100, 255))
    ov = Image.new('RGBA', (w, h), (0, 0, 0, 0)); d = ImageDraw.Draw(ov)
    for i, (z, fx, fy, frx, fry) in enumerate(ISL):
        cx, cy, rx, ry = fx * w, fy * h, frx * w, fry * h
        for k in range(9):
            ox = rnd.uniform(-.55, .55) * rx; oy = rnd.uniform(-.55, .55) * ry; ex = rx * rnd.uniform(.4, .7); ey = ry * rnd.uniform(.4, .7)
            d.ellipse((cx + ox - ex - 1, cy + oy - ey - 1, cx + ox + ex + 1, cy + oy + ey + 1), fill=(20, 40, 60, 255))
        for k in range(9):
            ox = rnd.uniform(-.55, .55) * rx; oy = rnd.uniform(-.55, .55) * ry; ex = rx * rnd.uniform(.4, .7); ey = ry * rnd.uniform(.4, .7)
            col = rnd.choice([(86, 134, 60, 255), (120, 150, 70, 255), (176, 156, 96, 255), (70, 112, 66, 255)])
            d.ellipse((cx + ox - ex, cy + oy - ey, cx + ox + ex, cy + oy + ey), fill=col)
    cv.im.alpha_composite(ov, (x0, y0))

def scr_map_big(m):
    cv, (x0, y0, x1, y1) = win(m, gt(147).lstrip('#')); s = m.s
    by = y1 - m.btnH; r = (x0, y0, x1, by - m.gap); w = r[2] - r[0]; h = r[3] - r[1]
    world_art(cv, r, random.Random(7))
    d = ImageDraw.Draw(cv.im); d.rectangle((r[0] - 1, r[1] - 1, r[2], r[3]), outline=C['ink'])
    for z, fx, fy, frx, fry in ISL:
        cx, cy = r[0] + round(fx * w), r[1] + round(fy * h)
        lab = ZONES[z]; tw = U.sw(m.spx, lab) + 6
        d.ellipse((cx - 2 * s - 1, cy - 2 * s - 1, cx + 2 * s + 1, cy + 2 * s + 1), fill=C['ink'])
        lx = min(max(cx - tw // 2, r[0] + 2), r[2] - tw - 2); ly = cy + 2 * s + 3
        cv.alpha_rect((lx, ly, lx + tw, ly + m.spx + 2), (0, 0, 0, 190))
        cv.text(lx + 3, ly + 1, lab, m.spx, C['white'], maxw=tw - 4)
        cv.hit('zone_%d' % z, (cx - 4 * s, cy - 4 * s, cx + 4 * s, ly + m.spx + 2), 'chỉ hiển thị (bản đồ lớn: ảnh + tên khu vực)')
    px_, py_ = r[0] + round(.22 * w), r[1] + round(.40 * h)
    d.polygon([(px_, py_), (px_ - 3 * s, py_ - 6 * s), (px_ + 3 * s, py_ - 6 * s)], fill=C['red'], outline=C['ink'])
    cv.text(x1, by + (m.btnH - m.px) // 2, '', m.px, C['dim']) if False else None
    bw = max(U.sw(m.px, U.strip_hint(gt(148))) + 4 * m.pad, (x1 - x0) // 3)
    w_button(cv, (x1 - bw, by, x1, by + m.btnH), U.strip_hint(gt(148)), 'primary', 'toggle', 'phím # -> StateMap: chuyển sang bản đồ khu vực (lưới điểm)')
    return cv

NODES = [(1, 0, 0), (0, 1, 1), (1, 1, 2), (2, 1, 3), (0, 2, 4), (1, 2, 5), (1, 3, 6)]       # (cột, hàng, id khu vực ingame_text)
EDGES = [(0, 2), (1, 2), (2, 3), (1, 4), (4, 5), (2, 5), (5, 6)]

def scr_map_grid(m):
    cv, (x0, y0, x1, y1) = win(m, gt(148).lstrip('#')); s = m.s
    by = y1 - m.btnH; r = (x0, y0, x1, by - m.gap); w = r[2] - r[0]; h = r[3] - r[1]
    world_art(cv, r, random.Random(7)); cv.alpha_rect(r, (6, 12, 24, 190))
    ImageDraw.Draw(cv.im).rectangle((r[0] - 1, r[1] - 1, r[2], r[3]), outline=C['ink'])
    ns = 10 * s + 12; colw = w // 3; rowh = h // 4
    lab_w = colw - ns - 6
    pos = {}
    for k, (c, rw, z) in enumerate(NODES):
        pos[k] = (r[0] + c * colw + 4 + ns // 2, r[1] + rw * rowh + rowh // 2)
    d = ImageDraw.Draw(cv.im)
    for a, b in EDGES:
        (ax, ay), (bx, bY) = pos[a], pos[b]
        d.line((ax, ay, bx, bY), fill=C['orange_dk'], width=max(2, s + 1))
    for k, (c, rw, z) in enumerate(NODES):
        cx, cy = pos[k]; cur = (k == 2)
        rr = (cx - ns // 2, cy - ns // 2, cx + ns // 2, cy + ns // 2)
        inner = cv.frame(rr, rad=m.rad, orange=cur); cv.vgrad(inner, (110, 220, 120, 255) if cur else (44, 62, 88, 255), (50, 150, 70, 255) if cur else (24, 34, 50, 255))
        lab = ZONES[z]; ls = U.wrap(m.spx, lab, lab_w)
        assert len(ls) <= 2, ('nhãn khu vực > 2 dòng', lab)
        ty = cy - len(ls) * m.slp // 2
        lw_ = max(U.sw(m.spx, l) for l in ls); cv.alpha_rect((cx + ns // 2 + 2, ty - 1, cx + ns // 2 + 6 + lw_ + 2, ty + len(ls) * m.slp), (6, 12, 24, 215))
        for i, l in enumerate(ls): cv.text(cx + ns // 2 + 4, ty + i * m.slp, l, m.spx, C['white'] if not cur else C['gold'], shadow=True, maxw=lab_w)
        cv.hit('node%d' % k, rr, 'chỉ hiển thị (điểm khu vực %s)%s' % (lab, ', vị trí hiện tại' if cur else ''))
    hw = (x1 - x0 - m.gap) // 2
    w_button(cv, (x0, by, x0 + hw, by + m.btnH), U.strip_hint(gt(149)), 'normal', 'minimap', 'phím * -> bật/tắt Minimap (StateMap KeyPressed)')
    w_button(cv, (x1 - hw, by, x1, by + m.btnH), U.strip_hint(gt(147)), 'primary', 'toggle', 'phím # -> StateMap: chuyển sang bản đồ lớn (ảnh)')
    return cv

# ---------------------------------------------------------------- danh sách màn
SCREENS = [
    ('npc_mua_ky_nang', scr_skill_buy, 'NPC Mua kỹ năng (SkillBuy): lưới sách kỹ năng + giá'),
    ('npc_mua_ky_nang_het_tien', lambda m: scr_skill_buy(m, 'tien'), 'NPC Mua kỹ năng: popup không đủ tiền (OK)'),
    ('npc_mua_vat_pham', scr_item_buy, 'NPC Mua vật phẩm (ItemBuy): 3 tab hàng + lưới 4x4 cuộn'),
    ('npc_mua_vat_pham_xac_nhan', lambda m: scr_item_buy(m, 'mua'), 'NPC Mua vật phẩm: popup xác nhận Có/Không'),
    ('npc_ban', scr_item_sale, 'NPC Bán vật phẩm (ItemSale): túi đồ theo trang + giá bán'),
    ('npc_toi_luyen', lambda m: scr_refine(m, False), 'NPC Tôi luyện thường (LowRefine)'),
    ('npc_toi_luyen_cao_cap', lambda m: scr_refine(m, True), 'NPC Tôi luyện cao cấp (HighRefine)'),
    ('npc_toi_luyen_ket_qua', lambda m: scr_refine(m, False, 'ketqua'), 'NPC Tôi luyện: popup kết quả (OK)'),
    ('npc_chon_vat_pham', scr_picker, 'NPC chế độ chọn vật phẩm (OpenBagFilter, 5/trang)'),
    ('npc_orb', scr_orb, 'NPC Kết hợp Orb (OrbCombine): 3 ô + tỉ lệ'),
    ('npc_che_bien', scr_mix, 'NPC Chế biến thường (NormalMix): công thức + nguyên liệu'),
    ('npc_bo_suu_tap', scr_collection_menu, 'NPC Bộ sưu tập: chọn Đồ Giám (3 nút)'),
    ('npc_bo_suu_tap_luoi', scr_collection, 'NPC Bộ sưu tập: Đồ Giám Vũ Khí (4 lớp, lưới 4x4)'),
    ('npc_bo_suu_tap_dang_ky', lambda m: scr_collection(m, 'dang_ky'), 'NPC Bộ sưu tập: popup đăng ký Có/Không (273)'),
    ('cash_shop', scr_cash, 'Cash shop: lưới 4x4 cuộn + nhãn giá offline miễn phí'),
    ('cash_popup_mua', lambda m: scr_cash(m, 'mua'), 'Cash shop popup 1/4: xác nhận mua (Có/Không)'),
    ('cash_popup_xong', lambda m: scr_cash(m, 'xong'), 'Cash shop popup 2/4: đã nhận (OK)'),
    ('cash_popup_day', lambda m: scr_cash(m, 'day'), 'Cash shop popup 3/4: túi đồ đầy (OK)'),
    ('cash_popup_khu_vuc', lambda m: scr_cash(m, 'khu_vuc'), 'Cash shop popup 4/4: khu vực không dùng được (OK)'),
    ('chuyen_gia_chuan_bi', scr_expert_ready, 'Chuyên gia: Màn hình chuẩn bị'),
    ('chuyen_gia_cua_hang', scr_expert_shop, 'Chuyên gia: Cửa hàng'),
    ('chuyen_gia_popup_mua', lambda m: scr_expert_shop(m, 'mua'), 'Chuyên gia popup: xác nhận mua (Có/Không)'),
    ('chuyen_gia_popup_khong_dung', lambda m: scr_expert_ready(m, 'khong_dung'), 'Chuyên gia popup: không thể dùng ở chế độ Chuyên gia (OK)'),
    ('game_hoi_sinh', lambda m: scr_game(m, 'hoi_sinh'), 'StateGame popup: hồi sinh bằng Bùa (miễn phí offline)'),
    ('game_hoi_sinh_xac_nhan', lambda m: scr_game(m, 'hoi_sinh_xn'), 'StateGame popup: xác nhận dùng Bùa Hồi Sinh'),
    ('game_hoi_sinh_xong', lambda m: scr_game(m, 'hoi_sinh_ok'), 'StateGame popup: hồi sinh thành công'),
    ('game_chien_thang', lambda m: scr_game(m, 'chien_thang'), 'StateGame popup: Chiến thắng! (kết quả Chuyên gia)'),
    ('game_that_bai', lambda m: scr_game(m, 'that_bai'), 'StateGame popup: Thất bại! (kết quả Chuyên gia)'),
    ('ban_do_khu_vuc', scr_map_grid, 'StateMap: bản đồ khu vực (lưới điểm)'),
    ('ban_do_lon', scr_map_big, 'StateMap: bản đồ lớn (ảnh các đảo)'),
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
def verify_b2b(errors):
    L = []; ok = lambda b: 'ĐẠT' if b else 'TRÀN'
    for (W, H) in FRAMES:
        m = metrics(W, H); s = m.s
        body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH); x0, y0, x1, y1 = content_rect(m, body); cw = x1 - x0
        L.append('== %dx%d  font %dpx / nhỏ %dpx, icon x%d ==' % (W, H, m.px, m.spx, m.s))
        # tiêu đề cửa sổ
        tmax = m.winW - 2 * m.bt - 4 - 2 * (m.titleH + m.pad)
        titles = [ct(i) for i in (83, 84, 85, 86, 87, 88, 89, 195, 244, 245, 269, 270, 271, 284)] + [gt(147).lstrip('#'), gt(148).lstrip('#')]
        wt = max(titles, key=lambda t: U.sw(m.px, t))
        L.append('  tiêu đề cửa sổ (%d): rộng nhất %d px "%s" <= %d px: %s' % (len(titles), U.sw(m.px, wt), wt, tmax, ok(U.sw(m.px, wt) <= tmax)))
        # hình học khay
        g = dict(strip=m.lp + 2 * s + 2); gtop = y0 + g['strip'] + m.gap; sh = m.px + 2 * (m.bt + 3) + 2
        gy_tab = gtop + sh + m.gap; gh = 4 * m.pitch - m.cg
        L.append('  khay có thanh tiền + tab con: lưới kết thúc y=%d <= %d: %s' % (gy_tab + gh, y1, ok(gy_tab + gh <= y1)))
        sbx = x0 + m.gridW + 3; dx = sbx + m.sbw + m.gap + 2; dxp = x0 + m.pitch + m.gap + 1 + m.gridW + m.gap + 1
        # hộp chi tiết: tên (≤2 dòng) cho mọi vật phẩm có thể xuất hiện ở khay
        for lab, names, dxx in (('tên đồ NPC (item_00..10, bán/mua)', [U.clean_name(v[2])[0] for (f, r), v in ITEMS.items() if f.endswith(tuple('item_%02d.dat' % i for i in range(0, 11)))], dx),
                                ('tên sách kỹ năng (item_16/17)', [U.clean_name(v[2])[0] for (f, r), v in ITEMS.items() if f.endswith(('item_16.dat', 'item_17.dat'))], dx),
                                ('tên cash (single+expert)', [U.clean_name(v[0])[0] for d in CASH.values() for v in d.values()], dx),
                                ('tên bán (có cột trang)', [U.clean_name(v[2])[0] for (f, r), v in ITEMS.items() if f.endswith(tuple('item_%02d.dat' % i for i in range(0, 14)))], dxp)):
            tw = (x1 - dxx) - 2 * 3 - 2 * (m.pad // 2 + 1) - m.sbw - 2
            mx = max(names, key=lambda t: len(U.wrap(m.px, t, tw)) * 10000 + U.sw(m.px, t))
            L.append('  %s (%d): nhiều dòng nhất %d (giới hạn 2) "%s", hộp chữ %d px: %s' % (lab, len(names), len(U.wrap(m.px, mx, tw)), mx, tw, ok(len(U.wrap(m.px, mx, tw)) <= 2)))
        # mô tả cash: số dòng vs hiển thị
        c = Cv(m, size=(W, H)); tw = (x1 - dx) - 2 * 3 - 2 * (m.pad // 2 + 1) - m.sbw - 2
        by = y1 - m.btnH; prow = max(m.lp + 2, U.mgr_img('ui', 16).height * coin_k(m) + 2)
        boxh = (by - m.gap - prow - m.gap) - gtop
        view = max(1, (boxh - 3 - (2 * m.lp + 4)) // m.lp)
        cnt = {}
        for d in CASH.values():
            for v in d.values():
                if len(v) > 1:
                    n = len(rich_lines(m.px, parse_rich(v[1])[0], tw)); cnt[n] = cnt.get(n, 0) + 1
        L.append('  mô tả cash (%d): phân bố số dòng %s; hộp hiện %d dòng; %d mô tả dài hơn -> cuộn dọc có thanh cuộn, không cắt' % (
            sum(cnt.values()), ', '.join('%d:%d' % (k, cnt[k]) for k in sorted(cnt)), view, sum(v for k, v in cnt.items() if k > view)))
        # nhãn giá offline
        L.append('  nhãn giá offline "%s": %d px <= %d px: %s' % (OFFLINE, U.sw(m.px, OFFLINE), x1 - dx - 6, ok(U.sw(m.px, OFFLINE) <= x1 - dx - 6)))
        # vật liệu chế biến: mọi công thức
        f = m.spx; lw = (x1 - dx - 2 * 3 - 2 * (m.pad // 2 + 1)) // 2 + 10; bad = 0; worst = (0, '')
        for ri, v in SMITH.items():
            if len(v) < 2 or v[0] == 'KHÔNG': continue
            for part in v[1].replace(';', ' ').split('/'):
                mm = re.match(r'^\s*(.*?)\s*(\d+)\s*$', part)
                if mm:
                    w = U.sw(f, mm.group(1)); worst = max(worst, (w, mm.group(1)))
                    if w > lw: bad += 1
        L.append('  vật liệu chế biến (smith_0): nhãn rộng nhất %d px "%s" <= %d px (font nhỏ), %d vượt: %s' % (worst[0], worst[1], lw, bad, ok(bad == 0)))
        nm_ = max(((v[0] if v else ''), v[0]) for ri, v in SMITH.items() if v and v[0] != 'KHÔNG')[1]
        # nhãn ô: tôi luyện / orb / kết quả
        sl = cw - (m.cell + 6 + m.pad) - m.pad - 3
        longest = max([strip_plain(WEAP[9]['name']), ct(101), ct(102), ct(103), ct(122), ct(123), ct(124), ct(127), nm('item_12.dat', 1)] + [U.clean_name(v[2])[0] for (f_, r), v in ITEMS.items() if f_.endswith('item_12.dat')],
                      key=lambda t: U.sw(m.px, t))
        L.append('  ô tôi luyện/Orb: chữ dài nhất %d px "%s" <= %d px: %s' % (U.sw(m.px, longest), longest, sl, ok(U.sw(m.px, longest) <= sl)))
        # popup: mọi chuỗi liên quan
        pw = round(0.70 * W) if not m.hd else 480; pw = min(pw, W - 2 * m.mg); ptw = pw - 2 * (m.pad + 2 * s + 4)
        pops = [ct(i) for i in list(range(90, 140)) + [92, 93, 94, 97, 98, 99, 125, 246, 247, 260, 273, 293, 302, 303, 304, 331, 350]] + [OFFLINE + '.']
        worst = (0, 0, '')
        for t in pops:
            n = len(U.wrap(m.px, t, ptw)); ph = 2 * m.pad + 4 * s + (n + 3) * m.lp + m.pad + m.btnH + 2
            if (n, ph, t[:40]) > worst: worst = (n, ph, t[:40])
        L.append('  popup NPC/cash/game (%d chuỗi): dài nhất %d dòng (+3 dòng tên/giá) -> cao %d <= khung %d: %s ("%s...")' % (len(pops), worst[0], worst[1], H - 2 * m.mg, ok(worst[1] <= H - 2 * m.mg), worst[2]))
        # kết quả Chuyên gia / khu vực
        res = [gt(i) for i in (327, 328, 331, 332, 333, 334, 335, 337, 338)]
        rm = max(res, key=lambda t: U.sw(m.px, t)); pwr = pw - 2 * (m.pad + 2 * s + 4) - U.sw(m.px, fmt(12480)) - 8
        L.append('  kết quả Chuyên gia: nhãn dài nhất %d px "%s" <= %d px: %s' % (U.sw(m.px, rm), rm, pwr, ok(U.sw(m.px, rm) <= pwr)))
        ns = 10 * s + 12; lab_w = (cw // 3) - ns - 6
        zl = max(range(0, 41), key=lambda z: (len(U.wrap(m.spx, ZONES[z], lab_w)), U.sw(m.spx, ZONES[z])))
        L.append('  nhãn khu vực (41): nhiều dòng nhất %d (giới hạn 2) "%s", chữ %d px: %s' % (len(U.wrap(m.spx, ZONES[zl], lab_w)), ZONES[zl], lab_w, ok(all(len(U.wrap(m.spx, ZONES[z], lab_w)) <= 2 for z in range(7)))))
        zw = max(U.sw(m.spx, ZONES[z]) for z in (33, 34, 35, 36, 37, 38, 39, 40))
        L.append('  nhãn đảo (bản đồ lớn, 8): rộng nhất %d px (nhãn tự co theo chữ, kẹp vào khung bản đồ): ĐẠT' % zw)
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
    vl = verify_b2b(errors)
    B.merge_text(OUT, 'verify.txt', '\n'.join(['Kiểm trên TOÀN BỘ dữ liệu thật, 4 khung; "cuộn" = thanh cuộn, không cắt chữ.'] + vl) + '\n', '# ===== b2b (mock_b2b.py) =====')
    contact_sheet(tiles, os.path.join(OUT, 'tong_hop_b2b.png'))
    import layout_b2b
    layout_b2b.write(OUT, hits, SCREENS, metrics, FRAMES)
    print('\n'.join(vl))

if __name__ == '__main__':
    main()
