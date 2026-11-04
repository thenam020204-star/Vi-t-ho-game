/* T17c3c: Kỹ năng (InGameMenu state 4): 5 tab con, lưới 4x4 cuộn, hộp mô tả, nút Gán phím tắt, popup chọn ô nhanh (2 bộ, nút đổi bộ).
 * Số đo: ui_new/menu/LAYOUT.md §11 + mock_b2a.py (skill_geom, screen_skill, w_belt_popup). Kiến trúc A (BAO-CAO §13.4): Paint + Touch mới,
 * KeyPressed/Process/SetPopupState GỐC (chạm = ghi trường con trỏ gốc rồi KeyPressed(-5) gốc). BAO-CAO-17c.md: trường, dữ liệu, bằng chứng.
 * Trường StateInGameMenu (KeySkillMenu 0x1039b0 / DrawSkillMenu 0xf115c): +0xd SubState (0 chọn tab con, 1..5 lưới của tab con 0..4, 6 chọn ô nhanh,
 * 7..10 mua/ghép sách: GIỮ đường gốc), +0xe tab con 0..4, +0xf ô trong cửa sổ 0..15, +0x10 chỉ số tuyệt đối (cửa sổ = ([0x10]-[0xf])/4 hàng),
 * +0x11 con trỏ ô nhanh (sub 6), +0x1a/0x1b/0x1c món đang cầm (loại/chỉ số/biểu tượng). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define I8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define I16(p, o) (*(s16 *)((u8 *)(p) + (o)))
#define BAGP() ((u8 *)HERO_GetBagItemPtr(HEROP()))

extern H void CommonUi_GetSkillDiscription(void *cui, u8 *buf, int a, void *info);   /* 0x76c08: dựng mô tả (mã màu } { |, ';' xuống dòng) */
extern H void *ig_self;

#define H_SUB 0x40       /* +i 0..4 */
#define H_GSCR 0x71
#define H_ASSIGN 0x72
#define H_CELL 0xA0      /* +k 0..15 */
#define H_BELT 0xB0      /* +i 0..4 */
#define H_SWAP 0xB5
#define H_CANCEL 0xC9
#define H_DETAIL 0xE0
#define H_DESC 0xE1

/* ================= dữ liệu ================= */
typedef struct { int icon, skill; } sent;      /* skill < 0: ô đặc biệt (icon 172 Tinh Linh, 278/279) */
static int sk_count(const u8 *h) { int n = I8(h, 8128); return n < 0 ? 0 : n > 24 ? 24 : n; }
static int sk_total(const u8 *h, int tab) {
    int c = sk_count(h);
    return tab == 0 ? 11 + c : tab == 1 ? 10 : tab == 2 ? c : tab == 3 ? 1 : 2;     /* "Tất cả" = 10 + danh sách đặc tính + 1 ô Tinh Linh */
}
static void sk_ent(const u8 *h, int tab, int p, sent *e) {
    int sk = -1, c = sk_count(h);
    if (tab == 0) { if (p < 10) sk = p; else if (p - 10 < c) sk = I8(h, 8120 + p - 10); }
    else if (tab == 1) sk = p;
    else if (tab == 2) sk = I8(h, 8120 + p);
    if (sk >= 0) { e->skill = sk; e->icon = I8(h, 0x22c) * 43 + sk; }              /* GMenu::DrawSkillBeltIcon 0x7d6c0 (icon <= 42): lớp*43 + chỉ số */
    else { e->skill = -1; e->icon = tab == 4 ? 278 + p : 172; }                      /* DrawSkillMenu 0xf222c/0xf22d8/0xf2f2c */
}
static u8 *sk_info(const u8 *h, int sk) { return (u8 *)h + 840 + 88 * sk; }           /* HERO::GetHeroSkillInfoPtr 0x85064 */

/* chỉ số/hộp thông tin */
static u8 dbuf[700];
static int desc_len;
static const u8 *cat2(int a, int b) {                                                  /* CT(a) ';' CT(b) vào dbuf */
    int n = 0; const u8 *s = CT(a);
    while (s[n] && n < 340) { dbuf[n] = s[n]; n++; }
    dbuf[n++] = ';';
    s = CT(b);
    for (int i = 0; s[i] && n < 690; i++) dbuf[n++] = s[i];
    dbuf[n] = 0; return dbuf;
}
static void draw_info(const mui_r *box, const u8 *name, const u8 *meta, int mlen, const u8 *txt, int tl, int key) {
    mui_m *m = &L; static int last = -1;
    mui_hit(H_DETAIL, box->x0, box->y0, box->x1, box->y1, HK_NONE, 0, 0);
    int y = mui_descbox2(box, name, -1, -1, meta, mlen);
    int p = m->pad / 2 + 1, ix0 = box->x0 + 3, ix1 = box->x1 - 3, tw = (ix1 - ix0) - 2 * p - m->sbw - 2, view = box->y1 - 3 - y;
    u16 st[48], ln[48];
    if (view < 1) view = 1;
    int nl = mui_wrap(m->px, txt, tl, tw, st, ln, 48), total = nl * m->lp;
    if (last != key) { last = key; mui_sc[1].pos = 0; }
    int mx = total > view ? total - view : 0, pos = mui_sc[1].pos;
    if (pos > mx) pos = mx;
    if (pos < 0) pos = 0;
    mui_sc[1].pos = pos; mui_sc[1].total = total; mui_sc[1].view = view;
    mui_clip(ix0, y, ix1, box->y1 - 3);
    for (int i = 0; i < nl; i++) {
        int yy = y - pos + i * m->lp;
        if (yy + m->lp > y && yy < y + view) mui_text_line(ix0 + p, yy, txt, st[i], ln[i], m->px, C_WHITE, 0);
    }
    mui_clip_reset();
    if (total > view) mui_scrollbar(ix1 - m->sbw - 1, y, box->y1 - 3, pos, total, view);
    mui_hit(H_DESC, ix0, y, ix1, box->y1 - 3, HK_DRAG, 1, 1);
}

/* ================= ô nhanh (copy của bag.c belt_slot, sửa icon kỹ năng đúng theo DrawSkillBeltIcon) ================= */
static void belt_slot(int slot, int *icon, int *count) {
    u8 *h = HEROP(), buf[400];
    int bank = (s8)HERO_GetHotKeyToggle(h), type = HERO_GetHotKeyBeltType(h, slot, bank) & 255, idx = (s8)HERO_GetHotKeyBeltSkillIdx(h, slot, bank);
    *icon = -1; *count = 0;
    if (type == 0 && idx >= 0) {
        int (*f)(u8 *, int) = (*(int (***)(u8 *, int))h)[0x24 / 4];
        int sk = (s8)f(h, idx);
        *icon = sk > 42 ? sk + 130 : I8(h, 0x22c) * 43 + sk;
    } else if (type == 2) {
        for (int i = 0; i < 400; i++) buf[i] = 0;
        ItemTable_GetItemTableInfo(ITEMTABLE(), buf, 11, idx);
        *icon = I16(buf, 22); *count = BagItem_GetBunch(BAGP(), 11, idx);
    }
}
static int qsin(int d) { static const u16 T[10] = {0, 44, 87, 128, 164, 196, 222, 241, 252, 256}; int q = d / 10, f = d % 10; return q >= 9 ? 256 : T[q] + (T[q + 1] - T[q]) * f / 10; }
static int isin(int d) { d %= 360; if (d < 0) d += 360; if (d <= 90) return qsin(d); if (d <= 180) return qsin(180 - d); if (d <= 270) return -qsin(d - 180); return -qsin(360 - d); }
static int icos(int d) { return isin(d + 90); }
static void fill_tri(int ax, int ay, int bx, int by, int cx, int cy, u32 rgb) {
    int x0 = ax < bx ? (ax < cx ? ax : cx) : (bx < cx ? bx : cx), x1 = ax > bx ? (ax > cx ? ax : cx) : (bx > cx ? bx : cx);
    int y0 = ay < by ? (ay < cy ? ay : cy) : (by < cy ? by : cy), y1 = ay > by ? (ay > cy ? ay : cy) : (by > cy ? by : cy);
    for (int y = y0; y <= y1; y++)
        for (int x = x0; x <= x1; x++) {
            int d1 = (x - bx) * (ay - by) - (ax - bx) * (y - by), d2 = (x - cx) * (by - cy) - (bx - cx) * (y - cy), d3 = (x - ax) * (cy - ay) - (cx - ax) * (y - ay);
            if (!((d1 < 0 || d2 < 0 || d3 < 0) && (d1 > 0 || d2 > 0 || d3 > 0))) mui_pix(x, y, rgb);
        }
}
static void swap_icon(int x, int y, int c) {                       /* ui_new/07_swap: vòng cam + 2 mũi tên tròn trắng (vẽ bằng nguyên thuỷ) */
    int cx = x + c / 2, cy = y + c / 2, R = c / 2, band = c / 10 < 2 ? 2 : c / 10;
    mui_disc(cx, cy, R, C_INK); mui_disc(cx, cy, R - 1, C_ORANGE_DK); mui_disc(cx, cy, R - 2, C_ORANGE);
    mui_disc(cx, cy, R - 2 - band, C_ORANGE_DK); mui_disc(cx, cy, R - 3 - band, RGB(22, 30, 42));
    int rho = (R - 3 - band) * 62 / 100, th = c / 14 < 1 ? 1 : c / 14, k = th * 2 + 2;
    for (int arc = 0; arc < 2; arc++) {
        int a0 = arc ? 330 : 150, a1 = arc ? 220 : 40;
        for (int a = a0; a >= a1; a -= 5) {
            int px_ = cx + rho * icos(a) / 256, py_ = cy - rho * isin(a) / 256;
            mui_fill(px_ - th / 2, py_ - th / 2, px_ - th / 2 + th, py_ - th / 2 + th, C_WHITE);
        }
        int ex = cx + rho * icos(a1) / 256, ey = cy - rho * isin(a1) / 256, tx = isin(a1), ty = icos(a1), rx = icos(a1), ry = -isin(a1);
        fill_tri(ex + tx * k * 3 / 2 / 256, ey + ty * k * 3 / 2 / 256, ex + rx * k / 256, ey + ry * k / 256, ex - rx * k / 256, ey - ry * k / 256, C_WHITE);
    }
}

/* ================= bố cục (mock_b2a.skill_geom) ================= */
typedef struct { int x0, y0, x1, y1, sh, gy, gx, gw, sbx, dx, dy1; } kgeo;
static void geo(kgeo *g) {
    mui_m *m = &L;
    g->x0 = m->wx + m->pad; g->y0 = m->wy + m->titleH + m->pad; g->x1 = m->wx + m->winW - m->pad; g->y1 = m->wy + m->winH - m->tabH - m->pad;
    g->sh = m->px + 2 * (m->bt + 3) + 2;
    g->gy = g->y0 + g->sh + m->gap; g->gx = g->x0 + m->sbw + 4; g->gw = 4 * m->pitch - m->cg;
    g->sbx = g->gx + g->gw + 3; g->dx = g->sbx + m->sbw + m->gap + 2; g->dy1 = g->y1 - m->btnH - m->gap;
}
static void prelude(void) {                                          /* như InGameMenu::Paint gốc: khôi phục cảnh + làm tối */
    u32 g = _lcdG; int fb = Graphic_GetFB(g);
    Graphic_CopyFrameBuffer(g, fb, Graphic_GetFBOff(g));
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 150);
}
static void reset_view(void) { gv[6464 + 19] = 0; gv[6464 + 32] = 0; }
static int active_tab(void *self) {
    int sub = S8(self, 0xd), t = S8(self, 0xe);
    if (sub >= 1 && sub <= 5) t = sub - 1;
    return t < 0 ? 0 : t > 4 ? 4 : t;
}
static int win_off(void *self) { int o = (S8(self, 0x10) - S8(self, 0xf)) / 4; return o < 0 ? 0 : o; }

static void pips(int x, int y, int cw, int lv, int n) {
    mui_m *m = &L; int d = m->s + 1 < 2 ? 2 : m->s + 1, g = m->s < 1 ? 1 : m->s;
    if (n < 1) return;
    if (n > 9) n = 9;
    if (n * d + (n - 1) * g > cw - 4) g = 1;
    int w = n * d + (n - 1) * g, x0 = x + (cw - w) / 2;
    for (int i = 0; i < n; i++) mui_fill(x0 + i * (d + g), y, x0 + i * (d + g) + d, y + d, i < lv ? C_ORANGE_HI : RGB(52, 66, 92));
}

void sk_paint(void *self) {
    mui_m *m = &L; int s = m->s, sub = S8(self, 0xd), tab = active_tab(self);
    u8 *h = HEROP(); kgeo g; geo(&g);
    prelude();
    ig_self = self; ig_vt = *(u32 *)self;
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(3), -1, MUI_TAB_ICONS, NULL, 3, &body);
    /* 5 tab con */
    static const u8 LAB[5] = {46, 3, 4, 6, 47};                       /* CT thật của DrawSkillMenu 0xf1424 (đo bằng hook): Tất cả, Kỹ năng, Đặc tính, Tinh Linh, Thường */
    int n5 = 5, tw = (g.x1 - g.x0 - (n5 - 1) * 2) / n5, off0 = (g.x1 - g.x0 - (n5 * tw + (n5 - 1) * 2)) / 2;
    for (int i = 0; i < 5; i++) {
        int xa = g.x0 + off0 + i * (tw + 2), in[4]; mui_r r = {xa, g.y0, xa + tw, g.y0 + g.sh};
        if (i == tab) { mui_frame(r.x0, r.y0, r.x1, r.y1, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12)); }
        else { mui_frame(r.x0, r.y0, r.x1, r.y1, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46)); }
        mui_clip(r.x0 + m->bt + 3, r.y0, r.x1 - m->bt - 3, r.y1);
        mui_text((r.x0 + r.x1) / 2, g.y0 + (g.sh - m->px) / 2, CT(LAB[i]), -1, m->px, i == tab ? C_WHITE : RGB(190, 204, 224), TF_CENTER | (i == tab ? TF_SHADOW : 0));
        mui_clip_reset();
        mui_hit(H_SUB + i, r.x0, r.y0, r.x1, r.y1, HK_CB, 0, 0);
    }
    /* lưới 4x4 cuộn */
    int N = sk_total(h, tab), R = (N + 3) / 4, off = sub >= 1 ? win_off(self) : 0, cur = sub >= 1 ? S8(self, 0x10) : -1, gh = 4 * m->pitch - m->cg;
    if (off > R - 4) off = R > 4 ? R - 4 : 0;
    mui_hit(H_GSCR, g.gx, g.gy, g.sbx + m->sbw, g.gy + gh, HK_DRAG, 0, m->pitch);
    for (int i = 0; i < 16; i++) {
        int p = (off + i / 4) * 4 + i % 4, x = g.gx + (i % 4) * m->pitch, y = g.gy + (i / 4) * m->pitch;
        sent e;
        if (p >= N) { mui_cell_i(x, y, -1, 0, -1, 0, 0, 0); continue; }
        sk_ent(h, tab, p, &e);
        mui_cell_i(x, y, e.icon, 0, -1, 0, p == cur, H_CELL + i);
        if (e.skill >= 0) {
            int lv = I8(h, 588 + e.skill), mx = I8(sk_info(h, e.skill), 8);
            if (lv <= 0) mui_fill_a(x + 1, y + 1, x + m->cell - 1, y + m->cell - 1, RGB(6, 10, 18), 150);
            pips(x, y + m->cell - 2 - (s + 1 < 2 ? 2 : s + 1), m->cell, lv, mx);
        }
    }
    mui_scrollbar(g.sbx, g.gy, g.gy + gh, off, R, 4);
    mui_sc[0].pos = off; mui_sc[0].total = R; mui_sc[0].view = 4;
    /* hộp mô tả + nút Gán phím tắt */
    {
        int p = cur >= 0 && cur < N ? cur : 0; sent e; sk_ent(h, tab, p, &e);
        mui_r box = {g.dx, g.gy, g.x1, g.dy1};
        u8 meta[48]; int mn = 0;
        const u8 *name = CT(6), *txt;
        if (e.skill >= 0) {
            u8 *inf = sk_info(h, e.skill); const u8 *l13 = CT(13);
            name = *(const u8 **)(inf + 4);
            while (l13[mn] && mn < 20) { meta[mn] = l13[mn]; mn++; }
            meta[mn++] = ' '; mn += mui_itoa(I8(h, 588 + e.skill) < 0 ? 0 : I8(h, 588 + e.skill), meta + mn); meta[mn++] = '/'; mn += mui_itoa(I8(inf, 8), meta + mn);
            for (int i = 0; i < 700; i++) dbuf[i] = 0;
            CommonUi_GetSkillDiscription(COMMONUI(), dbuf, -1, inf);
            txt = dbuf;
        } else {
            name = e.icon == 278 ? CT(149) : e.icon == 279 ? CT(150) : CT(6);
            txt = e.icon == 278 ? CT(263) : e.icon == 279 ? CT(264) : cat2(49, 305);
        }
        desc_len = mui_cslen(txt);
        draw_info(&box, name, e.skill >= 0 ? meta : NULL, mn, txt, desc_len, tab * 64 + p + (sub >= 1 ? 1000 : 0));
        int by = g.y1 - m->btnH; mui_r b = {g.dx, by, g.x1, by + m->btnH};
        int bn; const u8 *bt = mui_hint(CT(54), &bn);
        mui_button(&b, bt, bn, 1, 0); mui_hit(H_ASSIGN, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
    }
    /* popup chọn ô nhanh (sub 6) */
    if (sub == 6) {
        int c = m->cell, cg = m->cg, inw = 5 * c + 4 * cg + m->gap + c, in[4];
        int pw = inw + 2 * (m->pad + 2 * s + 4), minw = m->hd ? 420 : (55 * m->W + 50) / 100;
        if (pw < minw) pw = minw;
        if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
        int ph = 2 * m->pad + 4 * s + m->lp + m->pad + m->lp + 4 + c + m->pad + m->btnH + 2, x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
        mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
        mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
        mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
        sent e; sk_ent(h, tab, cur >= 0 && cur < N ? cur : 0, &e);
        int y = in[1] + m->pad + 2 * s, mw = pw - 2 * (m->pad + 2 * s + 4);
        mui_clip(m->W / 2 - mw / 2, y, m->W / 2 + mw / 2, y + m->lp);
        mui_text(m->W / 2, y, e.skill >= 0 ? *(const u8 **)(sk_info(h, e.skill) + 4) : CT(6), -1, m->px, C_GOLD, TF_CENTER | TF_SHADOW);
        mui_clip_reset(); y += m->lp + 2;
        int tn; const u8 *tt = mui_hint(CT(54), &tn);
        mui_clip(m->W / 2 - mw / 2, y, m->W / 2 + mw / 2, y + m->lp);
        mui_text(m->W / 2, y, tt, tn, m->px, C_DIM, TF_CENTER); mui_clip_reset(); y += m->lp + 4;
        int bx = (m->W - inw) / 2, sel = S8(self, 0x11);
        for (int i = 0; i < 5; i++) {
            int icon, cnt; belt_slot(i, &icon, &cnt);
            mui_cell_i(bx + i * (c + cg), y, icon, cnt, -1, 0, i == sel, H_BELT + i);
            u8 t[2]; t[0] = '1' + i; mui_text(bx + i * (c + cg) + 2, y + 1, t, 1, m->spx, i == sel ? C_WHITE : C_DIM, TF_SHADOW);
        }
        int sx = bx + 5 * c + 4 * cg + m->gap, bank = (s8)HERO_GetHotKeyToggle(h);
        swap_icon(sx, y, c);
        u8 t[4]; t[0] = '1' + (bank & 1); t[1] = '/'; t[2] = '2'; mui_text(sx + c / 2, y + c + 1, t, 3, m->spx, C_WHITE, TF_CENTER | TF_SHADOW);
        mui_hit(H_SWAP, sx, y, sx + c, y + c + m->spx + 2, HK_KEY, 35, 0);
        y += c + m->pad;
        int bw = mui_sw(m->px, CT(57), -1) + 4 * m->pad;
        if (bw < inw / 3) bw = inw / 3;
        mui_r cb = {(m->W - bw) / 2, y, (m->W + bw) / 2, y + m->btnH};
        mui_button(&cb, CT(57), -1, 0, 0); mui_hit(H_CANCEL, cb.x0, cb.y0, cb.x1, cb.y1, HK_KEY, -16, 0);
    }
}

int sk_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id, sub = S8(self, 0xd);
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 4) StateInGameMenu_SetState(self, id - 0x10 + 1); mui_sc[0].pos = 0; return 0; }
    if (sub == 6) {
        if (id >= H_BELT && id < H_BELT + 5) { S8(self, 0x11) = id - H_BELT; mui_key(self, MUI_CLS_INGAME, -5); }
        return 0;
    }
    if (sub > 6) return 0;
    u8 *h = HEROP();
    if (id >= H_SUB && id < H_SUB + 5) {                              /* như (Back, TRÁI/PHẢI * i, XUỐNG) của gốc: vào lưới của tab con i, con trỏ ô 0 */
        S8(self, 0xe) = id - H_SUB; StateInGameMenu_SetSubState(self, id - H_SUB + 1);
        S8(self, 0xf) = 0; S8(self, 0x10) = 0; reset_view();
    } else if (id >= H_CELL && id < H_CELL + 16) {
        int kk = id - H_CELL, tab = active_tab(self), N = sk_total(h, tab), off = sub >= 1 ? win_off(self) : 0, p = (off + kk / 4) * 4 + kk % 4;
        if (p >= N) return 0;
        if (sub == 0) { S8(self, 0xe) = tab; StateInGameMenu_SetSubState(self, tab + 1); reset_view(); S8(self, 0xf) = kk; S8(self, 0x10) = p; }
        else if (S8(self, 0x10) == p) mui_key(self, MUI_CLS_INGAME, -5);
        else { S8(self, 0xf) = kk; S8(self, 0x10) = p; reset_view(); }
    } else if (id == H_ASSIGN) {
        if (sub >= 1) mui_key(self, MUI_CLS_INGAME, -5);
    }
    return 0;
}

int sk_move(void *self, int x, int y) {
    int r = mui_touch_move(self, MUI_CLS_INGAME, x, y), sub = S8(self, 0xd);
    if (mui_drag == 0 && sub >= 1 && sub <= 5) {                        /* kéo lưới: đổi cửa sổ hàng, con trỏ giữ cột và kẹp vào cửa sổ */
        int tab = sub - 1, N = sk_total(HEROP(), tab), pos = mui_sc[0].pos, off = win_off(self);
        if (pos != off && pos >= 0) {
            int a = S8(self, 0x10), col = a & 3, rr = a / 4 - pos;
            if (rr < 0) rr = 0;
            if (rr > 3) rr = 3;
            a = (pos + rr) * 4 + col;
            if (a >= N) { a = N - 1; rr = a / 4 - pos; if (rr < 0) rr = 0; if (rr > 3) rr = 3; }
            S8(self, 0xf) = rr * 4 + (a & 3); S8(self, 0x10) = a; reset_view();
        }
    }
    return r;
}
