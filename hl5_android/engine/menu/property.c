/* T17c3b: InGameMenu state 5 = Đặc tính (đầu tư điểm). Số đo theo ui_new/menu/LAYOUT.md §11 + mock_b2a.py (screen_prop/prop_geom/w_subtabs/pips/w_info).
 * Kiến trúc A (BAO-CAO §13.4): Paint + Touch mới; KeyPressed/Process/SetPopupState GỐC. Chạm = ghi trường con trỏ gốc rồi KeyPressed(-5).
 * Trạng thái gốc của StateInGameMenu ở tab 5 (dò động + KeyPropertyMenu 0x102bc4, BAO-CAO-RE.md §17.2):
 *   +0xd sub: 0 = hàng tab nhánh (TRÁI/PHẢI đổi nhánh), 1..3 = lưới nhánh (sub = nhánh+1; vào bằng PHÍM XUỐNG = SetSubState(nhánh+1) + con trỏ 0)
 *   +0xe nhánh 0..2; +0xf vị trí con trỏ TRONG cửa sổ nhìn (0..11, = 3*hàng+cột); +0x10 chỉ số TUYỆT ĐỐI trong nhánh (0..17); hàng đầu cửa sổ = (+0x10 - +0xf) / 3 (0..2)
 *   OK (-5) ở lưới = KeyPropertyMenu tự kiểm điều kiện rồi SetPopupState(16/17/18) (xác nhận/không đủ điều kiện); đầu tư thật nằm trong Process gốc.
 * Dữ liệu: HeroSkillInfo(i) i=10..42 (HERO::GetHeroSkillInfoPtr): +4 con trỏ tên, +8 cấp tối đa, +9 vị trí = nhánh*18 + ô, +10 điểm nhánh tối thiểu, +11 kỹ năng tiền đề (cấp > 0);
 * cấp hiện tại = HERO[588+i]; điểm còn lại = s16 HERO+754; điểm đã đầu tư nhánh b = s16 HERO+756+2b; icon = HERO vtable +0x24(i);
 * mô tả = CommonUi::GetSkillDiscription(buf, -1, info) (đã thay #NN bằng số, còn mã màu BFont) */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define I8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define I16(p, o) (*(s16 *)((u8 *)(p) + (o)))

extern H void CommonUi_GetSkillDiscription(void *cui, u8 *out, int a, u8 *info);

#define H_BR 0x30        /* +i (0..3): tab nhánh (3 = ô "Điểm", chỉ nuốt chạm) */
#define H_GRIDSCR 0x6F   /* vùng kéo cuộn hàng */
#define H_NODE 0xA0      /* +3*hàng_cửa_sổ+cột (0..11) */
#define H_INVEST 0xC8
#define H_DESC 0xE0      /* kéo cuộn mô tả */
#define H_DETAIL 0xE1

#define SK_FIRST 10
#define SK_LAST 42
#define NROW 6
#define NVIS 4

static void reset_desc_view(void) { gv[6464 + 19] = 0; gv[6464 + 32] = 0; }   /* như KeyPropertyMenu gốc khi con trỏ đổi */

static void prelude(void) {
    u32 g = _lcdG;
    int fb = Graphic_GetFB(g);
    Graphic_CopyFrameBuffer(g, fb, Graphic_GetFBOff(g));
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 150);
}

/* ---- dữ liệu ---- */
static int vt_icon(u8 *h, int k) {
    int (*f)(u8 *, int) = (*(int (***)(u8 *, int))h)[0x24 / 4];
    return (s8)f(h, k);
}
static void build_tab(u8 *h, int e, s8 *tab) {          /* ô (0..17) của nhánh e -> chỉ số kỹ năng, -1 = trống */
    for (int i = 0; i < 18; i++) tab[i] = -1;
    for (int k = SK_FIRST; k <= SK_LAST; k++) {
        u8 *in = HERO_GetHeroSkillInfoPtr(h, k);
        if (!in) continue;
        int pos = I8(in, 9) - 18 * e;
        if (pos >= 0 && pos < 18) tab[pos] = (s8)k;
    }
}
static int can_invest(u8 *h, u8 *in, int k, int e) {    /* như KeyPropertyMenu/DrawPropertyMenu gốc */
    return I16(h, 754) > 0 && I16(h, 756 + 2 * e) >= I8(in, 10) && I8(h, 588 + I8(in, 11)) > 0 && I8(in, 8) > I8(h, 588 + k);
}

/* ---- widget cục bộ ---- */
static void subtab(int x0, int y0, int x1, int y1, const u8 *label, int n, int active, int hid, int kind) {
    mui_m *m = &L; int in[4];
    u32 col;
    if (active) { mui_frame(x0, y0, x1, y1, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12)); col = C_WHITE; }
    else { mui_frame(x0, y0, x1, y1, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46)); col = RGB(190, 204, 224); }
    int pd = m->bt + 3 + 1;
    mui_clip(x0 + pd, y0, x1 - pd, y1);
    mui_text((x0 + x1) / 2, y0 + (y1 - y0 - m->px) / 2, label, n, m->px, col, TF_CENTER | (active ? TF_SHADOW : 0));
    mui_clip_reset();
    mui_hit(hid, x0, y0, x1, y1, kind, 0, 0);
}
static void pips(int x, int y, int w, int cur, int mx) {
    mui_m *m = &L; int sz = m->s + 1 > 2 ? m->s + 1 : 2, tot = mx * sz + (mx - 1);
    x += (w - tot) / 2;
    for (int i = 0; i < mx; i++) mui_fill(x + i * (sz + 1), y, x + i * (sz + 1) + sz, y + sz, i < cur ? C_GOLD : RGB(48, 60, 82));
}
static void plus_mark(int x, int y, int cell) {          /* "+" khi còn đầu tư được (nét ngắn góc dưới phải) */
    mui_m *m = &L; int t = m->s, a = 2 * m->s + 1, cx = x + cell - 3 - a, cy = y + cell - 3 - a - (m->s + 3);
    mui_fill(cx - 1, cy - a - 1, cx + t + 1, cy + a + t + 1, C_INK); mui_fill(cx - a - 1, cy - 1, cx + a + t + 1, cy + t + 1, C_INK);
    mui_fill(cx, cy - a, cx + t, cy + a + t, C_WHITE); mui_fill(cx - a, cy, cx + a + t, cy + t, C_WHITE);
}

typedef struct { int x0, y0, x1, y1, gy, gx, rowh, cs, sbx, rx0, sh; } pgeo;
static void geo(pgeo *g) {
    mui_m *m = &L;
    g->x0 = m->wx + m->pad; g->y0 = m->wy + m->titleH + m->pad; g->x1 = m->wx + m->winW - m->pad; g->y1 = m->wy + m->winH - m->tabH - m->pad;
    g->sh = m->px + 2 * (m->bt + 3) + 2;
    g->gy = g->y0 + g->sh + m->gap;
    static const u8 T12[3] = {'1', '2', 0};
    int labw = mui_sw(m->spx, T12, 2) + m->gap;
    g->cs = m->cell + m->cell / 2 + m->gap;
    g->gx = g->x0 + labw + m->gap;
    int gx1 = g->gx + 2 * g->cs + m->cell;
    g->sbx = gx1 + m->gap;
    int rh = (g->y1 - g->gy) / 4, mx = m->pitch + 2 * m->s + 2;
    g->rowh = rh < mx ? rh : mx;
    g->rx0 = g->sbx + m->sbw + m->gap + 2;
}

static int win_top(void *self) {                         /* hàng đầu cửa sổ nhìn của lưới (0 khi đang ở hàng tab) */
    if (S8(self, 0xd) <= 0) return 0;
    int t = (S8(self, 0x10) - S8(self, 0xf)) / 3;
    return t < 0 ? 0 : t > NROW - NVIS ? NROW - NVIS : t;
}

void prop_paint(void *self) {
    mui_m *m = &L; int s = m->s, sub = S8(self, 0xd), e = S8(self, 0xe);
    u8 *h = HEROP();
    prelude();
    if (e < 0 || e > 2) e = 0;
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(4), -1, MUI_TAB_ICONS, NULL, 4, &body);
    pgeo g; geo(&g);
    int x0 = g.x0, y0 = g.y0, x1 = g.x1, y1 = g.y1, cls = h[0x22c] & 3, pts = I16(h, 754), inv = I16(h, 756 + 2 * e);
    /* tab nhánh (3) + "Điểm N" */
    {
        int tw = (x1 - x0 - 6) / 4, off = (x1 - x0 - (4 * tw + 6)) / 2;
        for (int i = 0; i < 4; i++) {
            int xa = x0 + off + i * (tw + 2);
            if (i < 3) subtab(xa, y0, xa + tw, y0 + g.sh, CT(68 + 3 * cls + i), -1, i == e, H_BR + i, HK_CB);
            else {
                u8 lab[40]; int n = 0; const u8 *l = CT(14);
                while (l[n] && n < 24) { lab[n] = l[n]; n++; }
                lab[n++] = ' '; n += mui_itoa(pts, lab + n);
                subtab(xa, y0, xa + tw, y0 + g.sh, lab, n, 0, H_BR + 3, HK_NONE);
            }
        }
    }
    s8 tab[18]; build_tab(h, e, tab);
    int top = win_top(self), ab = S8(self, 0x10), gh = 3 * g.rowh + m->cell, cell = m->cell;
    if (ab < 0 || ab > 17) ab = 0;
    mui_sc[0].pos = top; mui_sc[0].total = NROW; mui_sc[0].view = NVIS;
    mui_hit(H_GRIDSCR, g.gx, g.gy, g.sbx + m->sbw, g.gy + gh, HK_DRAG, 0, g.rowh);
    /* đường nối tiền đề (vẽ trước ô) */
    for (int r = 0; r < NVIS; r++)
        for (int c = 0; c < 3; c++) {
            int k = tab[3 * (top + r) + c];
            if (k < 0) continue;
            u8 *in = HERO_GetHeroSkillInfoPtr(h, k);
            int pk = I8(in, 11), pp = -1;
            if (pk < SK_FIRST || pk > SK_LAST) continue;
            for (int q = 0; q < 18; q++) if (tab[q] == pk) pp = q;
            if (pp < 0) continue;
            int pr = pp / 3 - top, pc = pp % 3, t = s > 1 ? s : 1;
            if (pr < 0 || pr >= r) continue;
            int xp = g.gx + pc * g.cs + cell / 2, xd = g.gx + c * g.cs + cell / 2, yp = g.gy + pr * g.rowh + cell, yd = g.gy + r * g.rowh, ym = yd - (g.rowh - cell + 1) / 2;
            mui_fill(xp, yp, xp + t, ym + t, C_LINE);
            mui_fill(xp < xd ? xp : xd, ym, (xp < xd ? xd : xp) + t, ym + t, C_LINE);
            mui_fill(xd, ym, xd + t, yd, C_LINE);
        }
    /* nhãn hàng + ô */
    for (int r = 0; r < NVIS; r++) {
        int y = g.gy + r * g.rowh, need = 4 * (top + r);
        u8 t[6]; int n = mui_itoa(need, t);
        mui_text(x0 + 1, y + (cell - m->spx) / 2, t, n, m->spx, inv >= need ? C_GOLD : C_DIM, 0);
        for (int c = 0; c < 3; c++) {
            int x = g.gx + c * g.cs, a = 3 * (top + r) + c, k = tab[a], sel = sub > 0 && a == ab, hid = H_NODE + 3 * r + c;
            if (k < 0) {
                mui_fill(x, y, x + cell, y + cell, RGB(18, 26, 40)); mui_fill(x + 1, y + 1, x + cell - 1, y + cell - 1, RGB(12, 18, 30));
                if (sel) { mui_fill(x, y, x + cell, y + 1, C_ORANGE_HI); mui_fill(x, y + cell - 1, x + cell, y + cell, C_ORANGE_HI); mui_fill(x, y, x + 1, y + cell, C_ORANGE_HI); mui_fill(x + cell - 1, y, x + cell, y + cell, C_ORANGE_HI); }
                mui_hit(hid, x, y, x + cell, y + cell, HK_CB, 0, 0);
                continue;
            }
            u8 *in = HERO_GetHeroSkillInfoPtr(h, k);
            int lv = I8(h, 588 + k), mx = I8(in, 8);
            mui_cell_i(x, y, vt_icon(h, k), 0, -1, 0, sel, hid);
            if (lv <= 0) mui_fill_a(x + 1, y + 1, x + cell - 1, y + cell - 1, RGB(6, 10, 18), 150);
            pips(x, y + cell - 2 - (s + 1 > 2 ? s + 1 : 2), cell, lv, mx < 1 ? 1 : mx > 5 ? 5 : mx);
            if (can_invest(h, in, k, e)) plus_mark(x, y, cell);
        }
    }
    mui_scrollbar(g.sbx, g.gy, g.gy + gh, top, NROW, NVIS);
    /* cột phải: đầu tư nhánh + chi tiết + nút */
    int rx0 = g.rx0, ty = g.gy, by = y1 - m->btnH;
    mui_clip(rx0, ty, x1 - 24, ty + m->lp);
    mui_text(rx0, ty, CT(81), -1, m->px, C_DIM, 0);
    mui_clip_reset();
    { u8 t[8]; int n = mui_itoa(inv, t); mui_text(x1, ty, t, n, m->px, C_GOLD, TF_RIGHT | TF_SHADOW); }
    ty += m->lp + 2;
    mui_r box = {rx0, ty, x1, by - m->gap};
    mui_hit(H_DETAIL, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
    int ksel = sub > 0 ? tab[ab] : -1, canb = 0;
    if (ksel < 0) mui_frame(box.x0, box.y0, box.x1, box.y1, m->rad, 0, 1, RGB(10, 16, 26), 235, NULL);
    else {
        static u8 desc[600];
        static int last_k = -1, last_e = -1;
        u8 *in = HERO_GetHeroSkillInfoPtr(h, ksel);
        u8 meta[40]; int mn = 0; const u8 *l13 = CT(13);
        while (l13[mn] && mn < 20) { meta[mn] = l13[mn]; mn++; }
        meta[mn++] = ' '; mn += mui_itoa(I8(h, 588 + ksel), meta + mn); meta[mn++] = '/'; mn += mui_itoa(I8(in, 8), meta + mn);
        const u8 *nm = *(u8 **)(in + 4);
        int y = mui_descbox2(&box, nm ? nm : (const u8 *)"", -1, -1, meta, mn);
        for (int i = 0; i < 600; i++) desc[i] = 0;
        CommonUi_GetSkillDiscription(COMMONUI(), desc, -1, in);
        desc[599] = 0;
        int p = m->pad / 2 + 1, ix0 = box.x0 + 3, ix1 = box.x1 - 3, tw = (ix1 - ix0) - 2 * p - m->sbw - 2, view = box.y1 - 3 - y;
        u16 st[40], ln[40];
        int nl = mui_wrap(m->px, desc, -1, tw, st, ln, 40), total = nl * m->lp;
        if (view < 1) view = 1;
        if (ksel != last_k || e != last_e) { last_k = ksel; last_e = e; mui_sc[1].pos = 0; }
        int pos = mui_sc[1].pos, mx = total > view ? total - view : 0;
        if (pos > mx) pos = mx;
        if (pos < 0) pos = 0;
        mui_sc[1].pos = pos; mui_sc[1].total = total; mui_sc[1].view = view;
        mui_clip(ix0, y, ix1, box.y1 - 3);
        for (int i = 0; i < nl; i++) {
            int yy = y - pos + i * m->lp;
            if (yy + m->lp > y && yy < y + view) mui_text_line(ix0 + p, yy, desc, st[i], ln[i], m->px, C_WHITE, 0);
        }
        mui_clip_reset();
        if (total > view) mui_scrollbar(ix1 - m->sbw - 1, y, box.y1 - 3, pos, total, view);
        mui_hit(H_DESC, ix0, y, ix1, box.y1 - 3, HK_DRAG, 1, 1);
        canb = can_invest(h, in, ksel, e);
    }
    mui_r b = {rx0, by, x1, by + m->btnH};
    int bn; const u8 *bt = mui_hint(CT(82), &bn);
    mui_button(&b, bt, bn, canb ? 1 : 3, 0);
    mui_hit(H_INVEST, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}

/* ---- chạm ---- */
static void enter_grid(void *self) {                      /* như phím XUỐNG ở hàng tab: SetSubState(nhánh+1), con trỏ = kỹ năng đầu tiên của hàng 0 (ô 0, 1, 2) */
    int e = S8(self, 0xe), c0 = 0;
    if (e < 0 || e > 2) e = 0;
    for (int k = SK_FIRST; k <= SK_LAST; k++) {            /* như KeyPropertyMenu: kỹ năng có id nhỏ nhất ở ô 0..2 của nhánh */
        u8 *in = HERO_GetHeroSkillInfoPtr(HEROP(), k);
        int pos = in ? I8(in, 9) - 18 * e : -1;
        if (pos >= 0 && pos <= 2) { c0 = pos; break; }
    }
    StateInGameMenu_SetSubState(self, e + 1);
    S8(self, 0xf) = c0; S8(self, 0x10) = c0;
    reset_desc_view();
}
static void set_top(void *self, int nt) {                 /* kéo cuộn: dời cửa sổ, kẹp con trỏ vào cửa sổ mới */
    if (S8(self, 0xd) <= 0) enter_grid(self);
    int top = win_top(self), ab = S8(self, 0x10);
    if (nt < 0) nt = 0;
    if (nt > NROW - NVIS) nt = NROW - NVIS;
    if (nt == top) return;
    int row = ab / 3, col = ab % 3;
    if (row < nt) row = nt;
    if (row > nt + NVIS - 1) row = nt + NVIS - 1;
    ab = row * 3 + col;
    S8(self, 0x10) = ab; S8(self, 0xf) = ab - 3 * nt;
    reset_desc_view();
}
static void node_tap(void *self, int w) {
    int sub = S8(self, 0xd), r = w / 3, c = w % 3;
    if (sub <= 0) { enter_grid(self); S8(self, 0xf) = w; S8(self, 0x10) = w; return; }
    int ab = 3 * (win_top(self) + r) + c;
    if (ab == S8(self, 0x10)) mui_key(self, MUI_CLS_INGAME, -5);
    else { S8(self, 0xf) = w; S8(self, 0x10) = ab; reset_desc_view(); }
}

int prop_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 5) StateInGameMenu_SetState(self, id - 0x10 + 1); mui_sc[0].pos = 0; return 0; }
    if (id >= H_BR && id < H_BR + 3) {
        int e = id - H_BR;
        if (S8(self, 0xd) > 0) StateInGameMenu_SetSubState(self, 0);
        S8(self, 0xe) = e; S8(self, 0xf) = 0; S8(self, 0x10) = 0;
        reset_desc_view();
    } else if (id >= H_NODE && id < H_NODE + 12) node_tap(self, id - H_NODE);
    else if (id == H_INVEST) { if (S8(self, 0xd) > 0) mui_key(self, MUI_CLS_INGAME, -5); }
    return 0;
}
int prop_move(void *self, int x, int y) {
    int r = mui_touch_move(self, MUI_CLS_INGAME, x, y);
    if (mui_drag == 0) set_top(self, mui_sc[0].pos);
    return r;
}
