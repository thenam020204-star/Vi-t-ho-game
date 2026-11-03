/* T17c3a: Liên kích (InGameMenu state 6). Số đo: ui_new/menu/LAYOUT.md §11 + mock_b2a.screen_combo. Kiến trúc A (BAO-CAO §17.1).
 * Trường StateInGameMenu (KeyComboMenu 0x1025b8 / DrawComboMenu 0xebac8): +0xd sub (0 chọn ô chuỗi, 1 chọn ứng viên), +0xe ô chuỗi, +0xf con trỏ ứng viên
 * (0 = "Tháo" = 0xff, 1.. = kỹ năng). Bảng ứng viên theo lớp HERO+0x22c (đo bằng hook lúc KeyComboMenu chạy); lớp 1 với HERO+0x267>0 có ô thứ 5 (bảng riêng). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define H_COMBO 0xA0
#define H_CAND 0xC0
#define H_PREV 0xC5
#define H_NEXT 0xC6
#define H_OK 0xC8
#define H_CLEAR 0xCA

static const u8 T0[] = {0xff, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 16, 17, 18, 19};
static const u8 T1[] = {0xff, 4, 6, 7, 10, 11, 13, 14, 15, 16, 17, 18, 19, 20};
static const u8 T1B[] = {0xff, 5, 9, 12};
static const u8 T2[] = {0xff, 1, 2, 3, 4, 6, 7, 10, 11, 12, 13, 14, 15, 16};
static const u8 T3[] = {0xff, 3, 4, 7, 8, 10, 12, 13, 14, 15, 16, 17, 18};

static int nslots(void) { u8 *h = HEROP(); return h[0x22c] == 1 && h[0x267] > 0 && h[0x267] < 128 ? 5 : 4; }
static const u8 *tab(int e, int *n) {
    u8 *h = HEROP();
    switch (h[0x22c]) {
    case 0: *n = sizeof T0; return T0;
    case 1: if (nslots() == 5 && e == 4) { *n = sizeof T1B; return T1B; } *n = sizeof T1; return T1;
    case 2: *n = sizeof T2; return T2;
    default: *n = sizeof T3; return T3;
    }
}
static int sk_idx(int id) { u8 *h = HEROP(); int (*f)(u8 *, int) = (*(int (***)(u8 *, int))h)[0x24 / 4]; return (s8)f(h, id); }
static int sk_icon(int idx) { return idx > 42 ? idx + 130 : idx + 43 * HEROP()[0x22c]; }   /* GMenu::DrawSkillBeltIcon 0x7d6c0 */
static int learned(int idx) { return idx >= 0 && idx < 200 && *(s8 *)(HEROP() + 584 + idx + 4) > 0; }


void combo_paint(void *self, const mui_r *body) {
    mui_m *m = &L; int s = m->s, c = m->cell; (void)body;
    int x0 = m->wx + m->pad, y0 = m->wy + m->titleH + m->pad, x1 = m->wx + m->winW - m->pad, y1 = m->wy + m->winH - m->tabH - m->pad;
    int sub = S8(self, 0xd), e = S8(self, 0xe), f = S8(self, 0xf), ns = nslots(), tn;
    const u8 *tb; u8 *h = HEROP();
    if (e < 0 || e >= ns) e = 0;
    tb = tab(e, &tn);
    if (f < 0 || f >= tn) f = 0;
    int gapx = m->gap > c / 2 ? m->gap : c / 2, rowW = ns * c + (ns - 1) * gapx, hx = (x0 + x1 - rowW) / 2, ly = y0 + 1;
    for (int i = 0; i < ns; i++) {
        int x = hx + i * (c + gapx);
        mui_clip(x - gapx / 2 + 1, ly - 1, x + c + gapx / 2 - 1, ly + m->lp + 1);
        mui_text(x + c / 2, ly, CT(140 + i), -1, m->px, i == e ? C_GOLD : C_DIM, TF_CENTER | (i == e ? TF_SHADOW : 0));
        mui_clip_reset();
    }
    int sy = ly + m->lp + 2;
    for (int i = 0; i < ns; i++) {
        int x = hx + i * (c + gapx), v = HERO_GetComboSkillBelt(h, i), icon = -1;
        if (!(v & 0x80)) icon = sk_icon(sk_idx((s8)v));
        mui_cell_i(x, sy, icon, 0, -1, 0, i == e, H_COMBO + i);
        if (i < ns - 1) {
            int ax = x + c + gapx / 2, ay = sy + c / 2, k = s * 2 + 1 > 3 ? s * 2 + 1 : 3;
            for (int d = 0; d <= k; d++) { int hh = k - d * k / (k + 1); mui_fill(ax - k / 2 + d, ay - hh, ax - k / 2 + d + 1, ay + hh + 1, C_ORANGE_HI); }   /* tam giác chỉ phải như mock */
        }
    }
    int cxm = hx + e * (c + gapx) + c / 2, cy0 = sy + c + 1, cy1 = cy0 + m->gap + 4;
    mui_fill(cxm, cy0, cxm + (s > 1 ? s : 1), cy1, C_ORANGE);
    int py = cy1 + 1, aw = c, prw = aw + m->gap + 5 * c + 4 * m->cg + m->gap + aw, px0 = (x0 + x1 - prw) / 2;
    int total = tn - 1, cur = f, page = cur ? (cur - 1) / 5 : 0;
    mui_r pa = {px0, py, px0 + aw, py + c}; mui_arrow(&pa, 0, H_PREV);
    for (int i = 0; i < 5; i++) {
        int j = page * 5 + i, x = px0 + aw + m->gap + i * (c + m->cg), icon = -1, dim = 0;
        if (j < total) { int idx = sk_idx(tb[j + 1]); icon = sk_icon(idx); dim = !learned(idx); }
        mui_cell_i(x, py, icon, 0, -1, 0, sub == 1 && cur == j + 1, H_CAND + i);
        if (dim && icon >= 0) mui_fill_a(x + 1, py + 1, x + c - 1, py + c - 1, C_NAVY, 150);
    }
    int ax2 = px0 + aw + m->gap + 5 * c + 4 * m->cg + m->gap;
    mui_r pb = {ax2, py, ax2 + aw, py + c}; mui_arrow(&pb, 1, H_NEXT);
    int ny = py + c + m->gap, cx0 = px0 + aw + m->gap, cx1 = cx0 + 5 * c + 4 * m->cg;
    if (cur >= 1) {
        u8 *info = HERO_GetHeroSkillInfoPtr(h, sk_idx(tb[cur])); const u8 *nm = info ? *(const u8 **)(info + 4) : NULL;
        if (nm) mui_text((x0 + x1) / 2, ny, nm, -1, m->px, learned(sk_idx(tb[cur])) ? C_WHITE : C_DISABLED, TF_CENTER | TF_SHADOW);
    }
    ny += m->lp;
    mui_hscroll(cx0, ny + 1, cx1, cur ? cur - 1 : 0, total, 5);
    ny += m->sbw + m->gap + 2;
    int by = y1 - m->btnH, hw = (x1 - x0 - m->gap) / 2, n;
    mui_r b1 = {x0, by, x0 + hw, by + m->btnH}, b2 = {x1 - hw, by, x1, by + m->btnH};
    const u8 *t = mui_hint(CT(153), &n);
    mui_button(&b1, t, n, sub == 1 && cur == 0 ? 2 : 0, 0); mui_hit(H_CLEAR, b1.x0, b1.y0, b1.x1, b1.y1, HK_CB, 0, 0);
    mui_button(&b2, CT(56), -1, 1, 0); mui_hit(H_OK, b2.x0, b2.y0, b2.x1, b2.y1, HK_KEY, -5, 0);
    (void)ny;
}

int combo_cb(void *self, int id) {
    int sub = S8(self, 0xd);
    if (id >= H_COMBO && id < H_COMBO + 5) {
        if (sub == 1) StateInGameMenu_SetSubState(self, 0);
        S8(self, 0xe) = id - H_COMBO; mui_key(self, MUI_CLS_INGAME, -5);      /* như OK trên ô chuỗi: sub 1, con trỏ ứng viên 0 */
        return 1;
    }
    if (id >= H_CAND && id < H_CAND + 5) {
        int tn, e = S8(self, 0xe), f = S8(self, 0xf), page, j;
        tab(e, &tn);
        if (sub == 0) { mui_key(self, MUI_CLS_INGAME, -5); f = 0; }
        page = f ? (f - 1) / 5 : 0; j = page * 5 + (id - H_CAND);
        if (j + 1 >= tn) return 1;                                              /* ô trống */
        S8(self, 0xf) = (s8)(j + 1); mui_key(self, MUI_CLS_INGAME, -5);
        return 1;
    }
    if (id == H_PREV || id == H_NEXT) {
        if (sub == 0) mui_key(self, MUI_CLS_INGAME, -5);
        mui_key(self, MUI_CLS_INGAME, id == H_PREV ? -3 : -4);
        return 1;
    }
    if (id == H_CLEAR) {
        if (sub == 0) mui_key(self, MUI_CLS_INGAME, -5);
        S8(self, 0xf) = 0; mui_key(self, MUI_CLS_INGAME, -5);
        return 1;
    }
    return 0;
}
