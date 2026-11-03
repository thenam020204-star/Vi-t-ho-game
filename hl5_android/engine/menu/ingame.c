/* T17c1: StateInGameMenu: lưới 3x3 (state 0), Nhân vật (1), Hệ thống (9). Số đo theo ui_new/menu/LAYOUT.md (§5, §6, §11) và mock_menu.py/mock_b2a.py.
 * Kiến trúc A (BAO-CAO §13.4): Paint + Touch mới, KeyPressed/Process/SetPopupState GỐC. Chạm = ghi trường con trỏ gốc rồi KeyPressed(-5) gốc.
 * Dữ liệu thật: HERO (gv+5180) qua offset đọc từ DrawCharacterMenu gốc (BAO-CAO §15), chuỗi qua TextMgr::GetCommonText. */
#include "mui.h"

#define SELF8(self, o) (*((s8 *)(self) + (o)))
#define F16(p, o) (*(s16 *)((p) + (o)))
#define F32(p, o) (*(int *)((p) + (o)))
#define CUI_SPR(k) (COMMONUI() + 5964 + 24 * (k))     /* GbmImage của ui.mgr #k (DrawMainMenu gốc: CommonUi + 5952 + 12 + 24*k) */

const u8 *mui_tab_icon(int i) { return i >= 0 && i < MUI_TAB_ICONS ? CUI_SPR(7 + i) : NULL; }
const u8 *mui_icon_back(void) { return CUI_SPR(25); }

int mui_cslen(const u8 *s) { int n = 0; while (s[n]) n++; return n; }
static int rnd(int a, int b) {                                /* round(a/b) kiểu Python (nửa làm tròn về chẵn) như mockup */
    int q = (2 * a + b) / (2 * b);
    if ((2 * a + b) % (2 * b) == 0 && (q & 1)) q--;
    return q;
}
const u8 *mui_hint(const u8 *s, int *n) {                 /* bỏ gợi ý phím đầu chuỗi: "#", "*" hoặc "d." */
    int l = mui_cslen(s);
    if (s[0] == '#' || s[0] == '*') { s++; l--; }
    else if (s[0] >= '0' && s[0] <= '9' && s[1] == '.') { s += 2; l -= 2; }
    *n = l; return s;
}
int mui_money(int v, u8 *o) {                         /* 1,234,567 */
    u8 t[16]; int n = mui_itoa(v < 0 ? 0 : v, t), k = 0;
    for (int i = 0; i < n; i++) { if (i && (n - i) % 3 == 0) o[k++] = ','; o[k++] = t[i]; }
    o[k] = 0; return k;
}

/* tên khu vực hiện tại: UI gốc (DrawZone/SetMapMode 0xe553c) = GetInGameText(33 + byte [*(gv+5204)+0x8d]) */
static const u8 *zone_title(void) {
    u8 *mp = *(u8 **)(gv + 5204);
    const u8 *t = mp ? TextMgr_GetInGameText(_pTextMgr, 33 + mp[0x8d]) : NULL;
    return t ? t : CT(9);
}
/* ---------------- phần chung ---------------- */
static void prelude(void) {                                  /* như InGameMenu::Paint gốc: khôi phục cảnh + làm tối */
    u32 g = _lcdG;
    int fb = Graphic_GetFB(g);
    Graphic_CopyFrameBuffer(g, fb, Graphic_GetFBOff(g));
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 150);
}
static void panel(int x0, int y0, int x1, int y1) { mui_frame(x0, y0, x1, y1, L.rad, 0, 1, RGB(14, 22, 34), 230, NULL); }

/* sprite nhân vật THẬT như DrawCharacterMenu gốc (0xf88b8..0xfa168): cùng nhánh (vũ khí/khiên/lớp) -> cùng tham số DrawSprite (a,b,y bù) */
static void hero_sprite(int cx, int cy, int r) {
    u8 *h = HEROP(); u32 g = _lcdG;
    void *spr; int a = 0, b = 1, dy = 0, cls = h[0x22c] & 3;
    extern H void *ig_self;
    if (!ig_self) return;
    if (!HERO_IsNoneEquipWeapon(h)) {
        a = 2; b = cls >= 2 ? 2 : 1; dy = cls == 1 ? 3 : 0;
    } else if (cls == 3 && !HERO_IsNoneEquipShield(h)) { a = 2; b = 2; }
    spr = CHAR_GetSpritePtr(h);
    if (!spr) return;
    int fr = *((s8 *)ig_self + 0x28);
    Graphic_SetClip(g, cx - r, cy - r, 2 * r, 2 * r);
    SPRITE_DrawSprite(spr, Graphic_GetFB(g), cx, cy + r * 55 / 100 + dy, a, b, fr, 0);
    Graphic_ResetClip(g);
}
void *ig_self;
u32 ig_vt;
void mui_portrait(int x, int y, int pr) {
    mui_m *m = &L; int cx = x + pr / 2, cy = y + pr / 2, R = pr / 2, band = pr / 12 < 2 ? 2 : pr / 12;
    mui_disc(cx, cy, R, C_INK); mui_disc(cx, cy, R - 1, C_ORANGE_DK); mui_disc(cx, cy, R - 2, C_ORANGE);
    mui_disc(cx, cy, R - 2 - band, C_ORANGE_DK); mui_disc(cx, cy, R - 3 - band, C_PANEL);
    hero_sprite(cx, cy, R - 2 - band);
}

/* cột tóm tắt (nhân vật): vòng chân dung + tên/LV/lớp + 3 thanh. Trả y cuối. */
static int summary(int x0, int y0, int x1, int y1, int lv_first) {
    mui_m *m = &L; u8 *h = HEROP(); int s = m->s, pr = m->hd ? 84 : 22 * s + 20;
    panel(x0, y0, x1, y1);
    int px_ = x0 + m->pad, yy = y0 + m->pad;
    mui_portrait(px_, yy, pr);
    int nx = px_ + pr + m->pad / 2 + 2, ty = yy + ((pr - 3 * m->lp) / 2 > 0 ? (pr - 3 * m->lp) / 2 : 0), mw = x1 - nx - m->pad;
    u8 lv[40]; int n = 0; const u8 *l13 = CT(13);
    while (l13[n] && n < 30) { lv[n] = l13[n]; n++; }
    lv[n++] = ' '; n += mui_itoa(h[0x22d], lv + n);
    int cls = h[0x22c] & 3;
    mui_clip(nx, 0, nx + mw, m->Ht);
    mui_text(nx, ty, CT(9), -1, m->px, C_GOLD, TF_SHADOW);
    mui_text(nx, ty + m->lp * (lv_first ? 1 : 2), lv, n, m->px, C_WHITE, 0);
    mui_text(nx, ty + m->lp * (lv_first ? 2 : 1), CT(178 + cls), -1, m->px, C_DIM, 0);
    mui_clip_reset();
    yy += (pr > 3 * m->lp ? pr : 3 * m->lp) + m->pad;
    int bw = x1 - x0 - 2 * m->pad, sp = F16(h, 0x248) / 10, spm = F16(h, 0x2e6) / 10;
    mui_bar(px_, yy, bw, CT(10), -1, F32(h, 0xf0), F32(h, 0x180), C_HP); yy += m->barH + m->pad - 1;
    mui_bar(px_, yy, bw, CT(11), -1, sp, spm, C_SP); yy += m->barH + m->pad - 1;
    mui_bar(px_, yy, bw, CT(12), -1, F32(h, 0x230), F32(h, 0x2d8), C_EXP); yy += m->barH + m->pad - 1;
    (void)y1;
    return yy;
}

static void kv(int x0, int y, int x1, const u8 *lab, int llen, const u8 *val, int vlen, int px) {
    int vw = mui_sw(px, val, vlen);
    mui_clip(x0, 0, x1 - vw - 6, L.Ht); mui_text(x0, y, lab, llen, px, C_DIM, 0); mui_clip_reset();
    mui_text(x1, y, val, vlen, px, C_WHITE, TF_RIGHT);
}
static void kv_num(int x0, int y, int x1, const u8 *lab, int v, int px, int step) {
    u8 t[16]; int n = mui_itoa(v, t);
    kv(x0, y, x1, lab, -1, t, n, px);
    mui_fill(x0, y + step - 2, x1, y + step - 1, RGB(40, 54, 76));
}
static void arrow_btn(const mui_r *r, int right, int hid) {      /* nút mũi tên ◀ ▶ (mock w_arrow): khung cam, nền xanh, tam giác cam sáng */
    mui_m *m = &L; int in[4], cx = (r->x0 + r->x1) / 2, cy = (r->y0 + r->y1) / 2, wd = r->x1 - r->x0, ht = r->y1 - r->y0;
    int k = (wd < ht ? wd : ht) / 4;
    if (k < 3) k = 3;
    mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 1, m->bt, (u32)-1, 0, in);
    mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
    int xt = right ? cx + k / 2 + 1 : cx - k / 2 - 1, xb = right ? cx - k / 2 : cx + k / 2, dx = xb > xt ? 1 : -1, n = (xb - xt) * dx;
    for (int i = 0; i <= n; i++) {                         /* từ đỉnh (nửa cao 0) tới đáy (nửa cao k) */
        int x = xt + dx * i, hh = n ? k * i / n : k;
        mui_fill(x, cy - hh, x + 1, cy + hh + 1, C_ORANGE_HI);
    }
    if (hid) mui_hit(hid, r->x0, r->y0, r->x1, r->y1, HK_CB, 0, 0);
}
static void sign_btn(const mui_r *r, int plus, int enabled, int hid, int kind, int a, int b) {
    mui_m *m = &L; int in[4], cx = (r->x0 + r->x1) / 2, cy = (r->y0 + r->y1) / 2, t = m->s;
    u32 col;
    if (enabled) { mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(250, 164, 40), RGB(204, 100, 12)); col = C_WHITE; }
    else { mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 0, 1, RGB(20, 26, 36), 255, in); col = C_DISABLED; }
    int k = (r->x1 - r->x0) / 4; if (k < 3) k = 3;
    mui_fill(cx - k, cy - t / 2, cx + k + 1, cy - t / 2 + t, col);
    if (plus) mui_fill(cx - t / 2, cy - k, cx - t / 2 + t, cy + k + 1, col);
    if (enabled && hid) mui_hit(hid, r->x0, r->y0, r->x1, r->y1, kind, a, b);
}

/* ---------------- state 0: lưới 3x3 ---------------- */
static void paint_main(void *self, mui_r *body) {
    mui_m *m = &L; int s = m->s, x0 = body->x0 + m->pad, y0 = body->y0 + m->pad, x1 = body->x1 - m->pad, y1 = body->y1 - m->pad;
    int cw = x1 - x0, ch = y1 - y0, sumw = rnd(cw * 36, 100), g = m->gap + 2, gx = x0 + sumw + g, gw = x1 - gx;
    int tw = (gw - 2 * g) / 3, th = (ch - 2 * g) / 3, sel = SELF8(self, 0xe), in[4];
    if (sel < 0 || sel > 8) sel = 0;
    for (int i = 0; i < 9; i++) {
        int c = i % 3, rw = i / 3, tx = gx + c * (tw + g) + (gw - 3 * tw - 2 * g) / 2, ty = y0 + rw * (th + g) + (ch - 3 * th - 2 * g) / 2;
        if (i == sel) { mui_frame(tx, ty, tx + tw, ty + th, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12)); }
        else { mui_frame(tx, ty, tx + tw, ty + th, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46)); }
        int k = s + 1 > 3 ? 3 : s + 1; const u8 *ic = mui_tab_icon(i);
        int icx = tx + tw / 2, icy = ty + (th - m->px - 3 * s) / 2 + 1;
        if (ic) mui_gbm(ic, icx - (*(const u16 *)(ic + 2) * k) / 2, icy - (*(const u16 *)(ic + 4) * k) / 2, k, i == sel ? C_WHITE : RGB(190, 204, 224));
        mui_clip(tx + s + 1, ty, tx + tw - s - 1, ty + th);
        mui_text(tx + tw / 2, ty + th - m->px - 2 * s - 1, CT(i), -1, m->px, i == sel ? C_WHITE : RGB(210, 220, 236), TF_CENTER | TF_SHADOW);
        mui_clip_reset();
        mui_hit(0x40 + i, tx, ty, tx + tw, ty + th, HK_OK, 0xe, i);
    }
    int yy = summary(x0, y0, x0 + sumw, y1, 0);
    u8 *h = HEROP(); int px_ = x0 + m->pad, bw = sumw - 2 * m->pad;
    yy += 1; mui_fill(px_, yy, x0 + sumw - m->pad, yy + 1, C_LINE); yy += m->pad - 1;
    mui_text(px_, yy, CT(19), -1, m->px, C_DIM, 0); yy += m->lp + 1;
    const u8 *coin = CUI_SPR(16); int ch2 = *(const u16 *)(coin + 4) * s, rh = m->lp > ch2 ? m->lp : ch2, cwid = *(const u16 *)(coin + 2) * s;
    u8 mt[20]; int mn = mui_money(BagItem_GetMoney(HERO_GetBagItemPtr(h)), mt);
    mui_gbm(coin, px_, yy + (rh - ch2) / 2, s, -1);
    mui_clip(px_ + cwid + 4, 0, px_ + bw, m->Ht);
    mui_text(x0 + sumw - m->pad, yy + (rh - m->px) / 2, mt, mn, m->px, C_GOLD, TF_RIGHT);
    mui_clip_reset();
}

/* ---------------- state 1: Nhân vật ---------------- */
/* hàng trang Chi tiết theo lớp (0 chiến binh, 1 sát thủ, 2 xạ thủ, 3 hiệp sĩ): đo bằng DrawCharacterMenu gốc (probe3: dãy CT/giá trị mỗi lớp) */
static int det_rows(int cls, const u8 **L_, const u8 **R_) {
    static const u8 LC[4][7] = {{33, 35, 36, 37, 38, 0, 0}, {33, 34, 35, 36, 37, 38, 39}, {34, 35, 36, 37, 39, 0, 0}, {33, 35, 36, 37, 38, 0, 0}};
    static const u8 RC[6] = {40, 41, 42, 43, 44, 45};
    *L_ = LC[cls & 3]; *R_ = RC; return 0;
}
static int detail_val(u8 *h, int ct) {                       /* offset trong HERO (BackupStatPerformance gốc + dò) */
    static const u16 OFF[13] = {0x18a, 0x18c, 0x18e, 0x190, 0x192, 0x194, 0x196, 0x198, 0x19c, 0x1a4, 0x1a2, 0x19e, 0x1a0};
    return F16(h, OFF[ct - 33]);
}
static void paint_char(void *self, mui_r *body) {
    mui_m *m = &L; int s = m->s, x0 = body->x0 + m->pad, y0 = body->y0 + m->pad, x1 = body->x1 - m->pad, y1 = body->y1 - m->pad, cw = x1 - x0, g = m->gap + 2;
    u8 *h = HEROP(); int sub = SELF8(self, 0xd), pts = F16(h, 0x2dc);
    HERO_CalcStatusComputation(h, 0);                         /* cập nhật chỉ số dẫn xuất (gốc gọi trong preview +1/-1) */
    if (sub == 0) {
        int lw = rnd(cw * 40, 100), sx1 = x0 + lw, yy = summary(x0, y0, sx1, y1, 1), bw = sx1 - x0 - 2 * m->pad, by = y1 - m->pad - m->btnH, bwd = (bw - m->pad) / 2;
        int pend = 0; for (int i = 0; i < 4; i++) pend += *(s16 *)((u8 *)self + 0x20 + 2 * i);
        mui_r b; int n; const u8 *t;
        b.x0 = x0 + m->pad; b.y0 = by; b.x1 = b.x0 + bwd; b.y1 = by + m->btnH;
        t = mui_hint(CT(20), &n); mui_button(&b, t, n, 0, 0); mui_hit(0x20, b.x0, b.y0, b.x1, b.y1, HK_KEY, 35, 0);
        b.x1 = sx1 - m->pad; b.x0 = b.x1 - bwd;
        t = mui_hint(CT(32), &n); mui_button(&b, t, n, pend > 0 ? 0 : 3, 0); if (pend > 0) mui_hit(0x21, b.x0, b.y0, b.x1, b.y1, HK_KEY, 42, 0);
        (void)yy;
        int rx0 = sx1 + g, ah = m->rowH + s, ry = y0, step = m->slp + 2;
        int ex = y1 - (y0 + (m->lp + 2) + 4 * (ah + m->cg + 1) + 1 + 3 + 2 * step), add = ex > 0 ? ex / 6 : 0;
        if (add > 4 * s) add = 4 * s;
        ah += add; step += add;
        mui_text(rx0 + 2, ry, CT(14), -1, m->px, C_DIM, 0);
        u8 t2[16]; n = mui_itoa(pts, t2); mui_text(x1 - 2, ry, t2, n, m->px, C_GOLD, TF_RIGHT | TF_SHADOW);
        ry += m->lp + 2;
        int cur = SELF8(self, 0xe);
        for (int i = 0; i < 4; i++) {
            int sel = i == cur, bs = ah, mx_ = x1 - bs, mn_ = mx_ - m->cg - bs, in[4], pe = *(s16 *)((u8 *)self + 0x20 + 2 * i);
            mui_fill(rx0, ry, x1, ry + ah, C_INK);
            if (sel) mui_vgrad(rx0 + 1, ry + 1, x1 - 1, ry + ah - 1, RGB(250, 160, 36), RGB(196, 96, 12));
            else mui_vgrad(rx0 + 1, ry + 1, x1 - 1, ry + ah - 1, C_CELL, C_CELL2);
            (void)in;
            mui_clip(rx0, ry, mn_ - 40, ry + ah);
            mui_text(rx0 + m->pad, ry + (ah - m->px) / 2, CT(15 + i), -1, m->px, C_WHITE, sel ? TF_SHADOW : 0);
            mui_clip_reset();
            u8 vt[16]; int vn = mui_itoa(F16(h, 0x2de + 2 * i), vt), vw = mui_sw(m->px, vt, vn);
            mui_text(mn_ - m->pad, ry + (ah - m->px) / 2, vt, vn, m->px, sel ? C_WHITE : C_GOLD, TF_RIGHT | (sel ? TF_SHADOW : 0));
            if (pe > 0) { u8 pt[16]; int pn = 0; pt[pn++] = '+'; pn += mui_itoa(pe, pt + pn); mui_text(mn_ - m->pad - vw - 4, ry + (ah - m->spx) / 2, pt, pn, m->spx, C_GREEN, TF_RIGHT | TF_SHADOW); }
            mui_r rm = {mn_, ry, mn_ + bs, ry + ah}, rp = {mx_, ry, mx_ + bs, ry + ah};
            sign_btn(&rm, 0, 0, 0, 0, 0, 0);                  /* gốc không có "giảm từng điểm": chỉ Huỷ-tất-cả (nút Huỷ) */
            sign_btn(&rp, 1, pts > 0, 0x30 + i, HK_OK, 0xe, i);
            if (pts <= 0) mui_hit(0x30 + i, rp.x0, rp.y0, rp.x1, rp.y1, HK_OK, 0xe, i);   /* gốc kiểm điểm trong KeyCharacterMenu */
            mui_hit(0x28 + i, rx0, ry, mn_, ry + ah, HK_SET, 0xe, i);
            ry += ah + m->cg + 1;
        }
        ry += 1; mui_fill(rx0, ry, x1, ry + 1, C_LINE); ry += 3;
        int hw2 = (x1 - rx0 - 6) / 2;
        kv_num(rx0 + 2, ry, rx0 + hw2, CT(25), F16(h, h[0x22c] == 2 ? 0x18c : 0x18a), m->spx, step);
        kv_num(rx0 + 2, ry + step, rx0 + hw2, CT(26), F16(h, 0x190), m->spx, step);
        kv_num(x1 - hw2, ry, x1 - 2, CT(27), F16(h, 0x192), m->spx, step);
        kv_num(x1 - hw2, ry + step, x1 - 2, CT(44), F16(h, 0x19e), m->spx, step);
    } else {
        int colw = (cw - g) / 2, step = m->lp + 2 * s - 1, ph = 7 * step + 2 * m->pad - 2, by = y1 - m->btnH, n; const u8 *t;
        panel(x0, y0, x0 + colw, y0 + ph); panel(x1 - colw, y0, x1, y0 + ph);
        int yl = y0 + m->pad - 1, yr = yl;
        const u8 *Ls, *Rs; det_rows(h[0x22c], &Ls, &Rs);
        for (int i = 0; i < 7 && Ls[i]; i++) { kv_num(x0 + m->pad, yl, x0 + colw - m->pad, CT(Ls[i]), detail_val(h, Ls[i]), m->px, step); yl += step; }
        for (int i = 0; i < 6; i++) { kv_num(x1 - colw + m->pad, yr, x1 - m->pad, CT(Rs[i]), detail_val(h, Rs[i]), m->px, step); yr += step; }
        mui_r b = {x0, by, x0 + (cw - g) / 2, by + m->btnH};
        t = mui_hint(CT(21), &n); mui_button(&b, t, n, 0, 0); mui_hit(0x20, b.x0, b.y0, b.x1, b.y1, HK_KEY, 35, 0);
    }
}

/* ---------------- state 9: Hệ thống ---------------- */
#define SYS_NOPT 6
static int opt_type(int i) { static const s8 T[SYS_NOPT] = {0, 0, 2, 0, 4, 0}; return T[i]; }
static void opt_value(int i, u8 *o, int *n, const u8 **sp) {
    s8 v = (s8)gv[132 + i];
    *sp = NULL; *n = 0;
    switch (opt_type(i)) {
    case 2: *n = mui_itoa(v + 1, o); break;
    case 4: *sp = CT(321 + v); break;
    default: if (v == 0) *sp = CT(190); else if (v == 1) *sp = CT(191); else *n = mui_itoa(v, o); break;
    }
}
static void paint_sys(void *self, mui_r *body) {
    mui_m *m = &L; int s = m->s, sub = SELF8(self, 0xd);
    int x0 = m->wx + m->pad, y0 = m->wy + m->titleH + m->pad, x1 = m->wx + m->winW - m->pad, y1 = m->wy + m->winH - m->tabH - m->pad;   /* như mock_b2a.screen_system: content_rect của (wx, wy+titleH, ..., winH-tabH), KHÔNG trừ viền */
    (void)body;
    mui_r b;
    if (sub == 0) {                                           /* 4 nút dọc: Lưu, Cài đặt, Trợ giúp, Màn hình chính (mock_b2a.screen_system) */
        static const u16 ct[4] = {189, 170, 172, 192};
        int cw_ = x1 - x0, bw = m->hd ? 420 : rnd(cw_ * 62, 100), bh = m->btnH + 2 * s, gp = m->gap + m->pad / 2, tot = 4 * bh + 3 * gp, cur = SELF8(self, 0xe);
        if (bw > cw_) bw = cw_;
        int bx = (x0 + x1 - bw) / 2, by = y0 + (y1 - y0 - tot) / 2;
        for (int i = 0; i < 4; i++) {
            b.x0 = bx; b.x1 = bx + bw; b.y0 = by + i * (bh + gp); b.y1 = b.y0 + bh;
            mui_button(&b, CT(ct[i]), -1, i == cur ? 1 : 0, 0);
            mui_hit(0x40 + i, b.x0, b.y0, b.x1, b.y1, HK_OK, 0xe, i);
        }
    } else if (sub == 1) {                                    /* Cài đặt: 6 hàng, ◀ giá trị ▶ (mock) */
        int cur = SELF8(self, 0x10), rowh = m->rowH + 2 * s, gp = 3, bs = rowh, cw_ = x1 - x0, vw = 0;
        static const u16 VS[5] = {321, 322, 323, 190, 191};
        for (int i = 0; i < 5; i++) { int w_ = mui_sw(m->px, CT(VS[i]), -1); if (w_ > vw) vw = w_; }
        vw += 2 * m->pad;
        for (int i = 0; i < SYS_NOPT; i++) {
            int sel = i == cur, ry = y0 + i * (rowh + gp), rx = x1 - bs - m->cg, lx = rx - m->cg - vw - m->cg - bs;
            mui_fill(x0, ry, x1, ry + rowh, C_INK);
            if (sel) mui_vgrad(x0 + 1, ry + 1, x1 - 1, ry + rowh - 1, RGB(250, 160, 36), RGB(196, 96, 12));
            else mui_vgrad(x0 + 1, ry + 1, x1 - 1, ry + rowh - 1, C_CELL, C_CELL2);
            mui_clip(x0, ry, x0 + cw_ - 2 * bs - vw - 4 * m->pad + m->pad, ry + rowh);
            mui_text(x0 + m->pad, ry + (rowh - m->px) / 2, CT(204 + i), -1, m->px, C_WHITE, sel ? TF_SHADOW : 0);
            mui_clip_reset();
            u8 vt[16]; const u8 *vs; int vn; opt_value(i, vt, &vn, &vs);
            mui_fill(lx + bs + m->cg, ry + 1, rx - m->cg, ry + rowh - 1, RGB(10, 16, 26));
            mui_clip(lx + bs + m->cg, ry, rx - m->cg, ry + rowh);
            mui_text((lx + bs + m->cg + rx - m->cg) / 2, ry + (rowh - m->px) / 2, vs ? vs : vt, vs ? -1 : vn, m->px, C_GOLD, TF_CENTER);
            mui_clip_reset();
            mui_r rp = {lx, ry, lx + bs, ry + rowh}, rn = {rx, ry, rx + bs, ry + rowh};
            arrow_btn(&rp, 0, 0x50 + i); arrow_btn(&rn, 1, 0x60 + i);
            mui_hit(0x40 + i, x0, ry, lx, ry + rowh, HK_CB, 0, 0);
        }
    } else if (sub == 2 || sub == 4) {                        /* Trợ giúp: danh sách (trái, lw = 0.42 cw) + hộp nội dung (phải), mock; con trỏ gốc [this+0x10]; sub 2/4 vẽ giống nhau */
        int total = F16((u8 *)_pTextMgr, 260), cur = SELF8(self, 0x10), rh = m->rowH;
        int cw_ = x1 - x0, lw = rnd(cw_ * 42, 100), vis = (y1 - y0) / (rh + 2), top = mui_sc[0].pos;
        static int last_cur = -1;
        if (total <= 0 || total > 64 || cur < 0 || cur >= total) return;      /* chuỗi trợ giúp chưa nạp */
        if (vis < 1) vis = 1;
        if (cur != last_cur) { if (cur < top) top = cur; if (cur >= top + vis) top = cur - vis + 1; last_cur = cur; mui_sc[1].pos = 0; }
        if (top > total - vis) top = total - vis;
        if (top < 0) top = 0;
        mui_sc[0].pos = top; mui_sc[0].total = total; mui_sc[0].view = vis;
        for (int i = 0; i < vis && top + i < total; i++) {
            b.x0 = x0; b.x1 = x0 + lw - m->sbw - 3; b.y0 = y0 + i * (rh + 2); b.y1 = b.y0 + rh;
            const u8 *tt = TextMgr_GetHelpTitleString(_pTextMgr, top + i);
            mui_row(&b, tt ? tt : (const u8 *)"", -1, NULL, 0, top + i == cur, 0x70 + i);
        }
        mui_scrollbar(x0 + lw - m->sbw, y0, y1, top, total, vis);
        mui_hit(0x6F, x0 + lw - 3 * m->sbw, y0, x0 + lw, y1, HK_DRAG, 0, rh + 2);
        mui_r box = {x0 + lw + m->gap, y0, x1, y1};
        const u8 *tt = TextMgr_GetHelpTitleString(_pTextMgr, cur), *body_ = TextMgr_DT_GetHelpInfoString(_pTextMgr, cur);
        if (!tt) tt = (const u8 *)"";
        if (!body_) body_ = (const u8 *)"";
        int ty = mui_descbox(&box, tt, -1, -1), p = m->pad / 2 + 1, ix0 = box.x0 + 3 + p, ix1 = box.x1 - 3;
        u16 st[64], ln[64]; int nl = mui_wrap(m->px, body_, -1, ix1 - ix0 - p - m->sbw - 2, st, ln, 64);
        int vis2 = (box.y1 - 3 - ty) / m->lp, top2 = mui_sc[1].pos;
        if (vis2 < 1) vis2 = 1;
        if (top2 > nl - vis2) top2 = nl - vis2;
        if (top2 < 0) top2 = 0;
        mui_sc[1].pos = top2; mui_sc[1].total = nl; mui_sc[1].view = vis2;
        mui_clip(ix0, ty, ix1, box.y1 - 3);
        for (int i = 0; i < vis2 && top2 + i < nl; i++) mui_text_skip(ix0, ty + i * m->lp, body_ + st[top2 + i], ln[top2 + i], m->px, C_WHITE, 0);
        mui_clip_reset();
        if (nl > vis2) mui_scrollbar(ix1 - m->sbw - 1, ty, box.y1 - 3, top2, nl, vis2);
        mui_hit(0x6E, box.x0, ty, box.x1, box.y1, HK_DRAG, 1, m->lp);
    }
}

/* ---------------- Paint / Touch ---------------- */
void ig_paint(void *self) {
    mui_m *m = &L; int st = SELF8(self, 0xc);
    prelude();
    ig_self = self; ig_vt = *(u32 *)self;
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    if (st == 0) { mui_window(&wr, zone_title(), -1, 0, NULL, 0, &body); paint_main(self, &body); }
    else {
        mui_window(&wr, CT(st - 1), -1, MUI_TAB_ICONS, NULL, st - 1, &body);
        if (st == 1) paint_char(self, &body); else if (st == 2) bag_paint(self, &body); else if (st == 3) equip_paint(self, &body); else if (st == 6) combo_paint(self, &body); else paint_sys(self, &body);
    }
}

int ig_touch(void *self, int x, int y) {
    int st = SELF8(self, 0xc), k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != st) StateInGameMenu_SetState(self, id - 0x10 + 1); mui_sc[0].pos = 0; return 0; }
    if (st == 2) { bag_cb(self, id); return 0; }
    if (st == 3) { equip_cb(self, id); return 0; }
    if (st == 6) { combo_cb(self, id); return 0; }
    if (st == 9) {
        int sub = SELF8(self, 0xd);
        if (sub == 1 && id >= 0x40 && id < 0x70) {                 /* con trỏ tuỳ chọn gốc: [+0x10] = hàng tuyệt đối, [+0xf] = hàng trong cửa sổ */
            int i = id & 15; SELF8(self, 0xf) = i; SELF8(self, 0x10) = i;
            if (id >= 0x50) mui_key(self, MUI_CLS_INGAME, id >= 0x60 ? -4 : -3);
        }
        else if ((sub == 2 || sub == 4) && id >= 0x70 && id < 0x7f) {
            int i = mui_sc[0].pos + (id - 0x70);
            SELF8(self, 0xf) = 0; SELF8(self, 0x10) = i;
        }
    }
    return 0;
}
int ig_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_INGAME, x, y); }
