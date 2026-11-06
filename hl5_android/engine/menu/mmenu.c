/* L3/T17c7: StateMainMenu (menu chính) theo ui_new/menu/LAYOUT.md §13 + mock_b2c.py. Kiến trúc A: Paint + Touch mới; KeyPressed/Process/SetPopupState GỐC.
 * Trường StateMainMenu (đo động harness/l3, so với gốc): +0xc state (2 logo, 3 tiêu đề, 4 menu chính 3x3, 5 slot/lớp, 6 Chuyên gia, 9 Cài đặt, 10 Thành tựu, 11/12 Trợ giúp/Hỗ trợ),
 *  +0xd sub, +0xe con trỏ (lưới 9 ô 0..8; slot 0..2; chế độ Chuyên gia 0..2; Cài đặt: hàng trong cửa sổ 0..5), +0xf (lớp 0..3 khi state 5 sub 1; Cài đặt: chỉ số mục tuyệt đối 0..9).
 * Ô lưới gốc (KeyMainMenu 0x11fbf0, đọc từ mã máy): 0 Bắt đầu->state 5, 1 Chuyên Gia->6, 2 Vào kho->7 (mạng), 3 Cập nhật->popup (mạng), 4 Cài đặt->9, 5 Thành tựu->10, 6 Trợ giúp->11,
 *  7 Hỗ trợ->12, 8 Thoát->popup 8; nhãn CommonText(166+ô), icon menu.mgr #(19+ô) = gv+5640+24*(19+ô) (DrawMainMenu 0x11d4b0). Lưới 6 ô (T7) = ô gốc {0,1,4,5,6,8}.
 * Slot: SlotInfo* = *(u32*)(gv + 4*(1286+slot)); GetHeroClass()==-1: trống. Cài đặt (DrawOption 0x11a1ec/KeyOption 0x11eb3c): 10 mục, loại (rodata 0x1553f0)
 *  {1,1,2,0,4,0,0,3,-1} + mục 9 = Phân giải (T10); giá trị gv[132+i] (mục 7: gv[142]); nhãn CT(204..209), CT(288), CT(341), MenuText(0), "Phân giải". */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define RES(self) (*(s8 **)((u8 *)(self) + 0x14))
#define MIMG(k) ((const u8 *)(gv + 5640 + 24 * (k)))        /* menu.mgr ảnh k (lưu trong CommonUi/gv) */
extern H int SlotInfo_GetHeroClass(void *slot);
extern H int SlotInfo_GetHeroLevel(void *slot);
extern H int SlotInfo_GetPlayTime(void *slot);
extern H const u8 *TextMgr_GetMenuText(u32 tm, int i);
extern H int StaticUtil_NumKeyToNaviKey(int k);
extern H int StaticUtil_FileRead(const char *name, char *buf, int len);
#define MT(i) TextMgr_GetMenuText(_pTextMgr, (i))
#ifndef MM_DEFAULT_K
#define MM_DEFAULT_K 2
#endif

enum { H_TILE = 0x10, H_SLOT = 0x20, H_DEL = 0x28, H_CLS = 0x30, H_OK = 0x38, H_MODE = 0x40, H_START = 0x48, H_ROW = 0x50, H_L = 0x58, H_R = 0x60, H_CHIP = 0x70, H_UP = 0x90, H_DN = 0x91,
       H_PYES = 0xC0, H_PNO = 0xC1, H_PBOX = 0xC2 };
static const u8 T6[6] = {0, 1, 4, 5, 6, 8};                   /* ô lưới gốc của 6 ô */
static int g_in_pop;

/* ---------------- ảnh (menu.mgr / title.mgr) ---------------- */
static const u8 *timg(int i) {                                 /* SPRITE_IMAGE logo (gv+5632): [+0xc] mảng GbmImage 24 B của tờ 0, [+0x34] mảng số ảnh; NULL nếu chưa nạp */
    u8 *si = *(u8 **)(gv + 5632);
    if (!si) return NULL;
    u8 *arr = *(u8 **)(si + 0xc); int *cnt = *(int **)(si + 0x34);
    if (!arr || !cnt || i >= cnt[0]) return NULL;
    return arr + 24 * i;
}
static int gw(const u8 *g) { return g ? *(const u16 *)(g + 2) : 0; }
static int gh(const u8 *g) { return g ? *(const u16 *)(g + 4) : 0; }
static void blit(const u8 *g, int x, int y, int k, int flipv, int alpha) {   /* GbmImage x k NEAREST, tuỳ chọn lật dọc / alpha 0..255 */
    if (!g || k < 1) return;
    int w = gw(g), h = gh(g);
    const u16 *pal = *(const u16 *const *)(g + 8); const u8 *pix = *(const u8 *const *)(g + 0xc);
    if (!pal || !pix) return;
    int nib = (g[0] & 15) == 4, stride = nib ? (w + 1) >> 1 : w;
    u32 skip = SKIP_COLOR & 0xffff;
    for (int sy = 0; sy < h; sy++) {
        const u8 *row = pix + (flipv ? h - 1 - sy : sy) * stride;
        for (int sx = 0; sx < w; sx++) {
            int idx = nib ? ((sx & 1) ? row[sx >> 1] & 15 : row[sx >> 1] >> 4) : row[sx];
            u32 c = pal[idx];
            if (c == skip) continue;
            for (int dy = 0; dy < k; dy++)
                for (int dx = 0; dx < k; dx++) {
                    int xx = x + sx * k + dx, yy = y + sy * k + dy;
                    if (alpha >= 255) mui_put16(xx, yy, c);
                    else {
                        u32 d = mui_getpx(xx, yy);
                        int r = (((c >> 11) & 31) * alpha + ((d >> 11) & 31) * (255 - alpha)) / 255, gg = (((c >> 5) & 63) * alpha + ((d >> 5) & 63) * (255 - alpha)) / 255,
                            b = ((c & 31) * alpha + (d & 31) * (255 - alpha)) / 255;
                        mui_put16(xx, yy, (r << 11) | (gg << 5) | b);
                    }
                }
        }
    }
}
static int logo_k(void) { return L.Ht < 300 ? 1 : 2; }
static void bg(void) {                                         /* nền: đen + dải dung nham menu.mgr #8 lát ngang, lật dọc ở đáy (mm_bg) */
    mui_m *m = &L; const u8 *lava = MIMG(8);
    mui_fill(0, 0, m->W, m->Ht, C_INK);
    int k = m->hd ? 2 : 1, lw = gw(lava) * k;
    if (lw <= 0) return;
    int x = (m->W - lw) / 2; x -= (x / lw + 1) * lw;
    for (; x < m->W; x += lw) { blit(lava, x, 0, k, 0, 255); blit(lava, x, m->Ht - gh(lava) * k, k, 1, 255); }
}
static void logo(int cx, int y, int k) {
    const u8 *hl = timg(5), *v = timg(3), *eye = timg(1);
    if (!hl || !v) return;
    int gap = 2 * k, tw = (gw(hl) + gw(v)) * k + gap, x = cx - tw / 2;
    if (eye) blit(eye, cx - gw(eye) * k / 2, y + (gh(hl) * k - gh(eye) * k) / 2 + 4 * k, k, 0, 150);
    blit(hl, x, y, k, 0, 255);
    blit(v, x + gw(hl) * k + gap, y + (gh(hl) - gh(v)) * k / 2, k, 0, 255);
}
static void copyright(void) {
    const u8 *c = timg(7); int k = L.Ht < 360 ? 1 : 2;
    if (c) blit(c, (L.W - gw(c) * k) / 2, L.Ht - gh(c) * k - (L.mg / 2 > 2 ? L.mg / 2 : 2), k, 0, 255);
}
static void dimwin(const u8 *title, mui_r *c) {                 /* nền + làm tối + cửa sổ cam (mmwin) */
    mui_m *m = &L; mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    bg(); mui_fill_a(0, 0, m->W, m->Ht, C_INK, 95);
    mui_window(&wr, title, -1, 0, NULL, 0, &body);
    int o = m->pad + m->bt + 2;                                    /* vùng nội dung như content_rect của mockup (400x240: 17,37..383,223) */
    c->x0 = m->wx + o; c->y0 = m->wy + m->titleH + o; c->x1 = m->wx + m->winW - o; c->y1 = m->wy + m->winH - o;
}
static void panel(int x0, int y0, int x1, int y1, int *in) { mui_frame(x0, y0, x1, y1, L.rad, 0, 1, RGB(10, 16, 26), 235, in); }
static void tilebox(int x0, int y0, int x1, int y1, int sel) {
    int in[4];
    mui_frame(x0, y0, x1, y1, L.rad, sel, L.bt, (u32)-1, 0, in);
    if (sel) mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12));
    else mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46));
}
static int rnd(int a, int b) { return (2 * a + b) / (2 * b); }

/* ---------------- state 4: lưới 6 ô ---------------- */
void mm_grid_paint(void *self) {
    mui_m *m = &L; int s = m->s, k = logo_k(), cur = S8(self, 0xe);
    bg(); logo(m->W / 2, m->mg, k); copyright();
    const u8 *hl = timg(5), *cp = timg(7);
    int top = m->mg + gh(hl) * k + 2 * k + m->lp + m->gap, ckk = m->Ht < 360 ? 1 : 2;
    int bot = m->Ht - gh(cp) * ckk - (m->mg / 2 > 2 ? m->mg / 2 : 2) - m->gap, g = m->gap + 2, rows = 2;
    int th = (bot - top - (rows - 1) * g) / rows, tw = (m->W - 2 * m->mg - 2 * g) / 3, cap = rnd(24 * th, 10);
    if (tw > cap) tw = cap;
    int gx = (m->W - (3 * tw + 2 * g)) / 2;
    for (int p = 0; p < 6; p++) {
        int o = T6[p], c = p % 3, rw = p / 3, x = gx + c * (tw + g), y = top + rw * (th + g), sel = cur == o;
        tilebox(x, y, x + tw, y + th, sel);
        const u8 *ic = MIMG(19 + o); u32 col = sel ? C_WHITE : RGB(210, 220, 236);
        int kk = 1; for (int cand = (s + 1 > 3 ? 3 : s + 1); cand >= 1; cand--) { if (cand > 2 && cand != (s + 1 > 3 ? 3 : s + 1)) continue; if (gh(ic) * cand + m->px + 3 * s + 4 <= th) { kk = cand; break; } }
        const u8 *lb = CT(166 + o);
        mui_clip(x + s + 1, y, x + tw - s - 1, y + th);
        if (gh(ic) * kk + m->px + 3 * s + 4 <= th) {
            int cy = y + (th - m->px - 2 * s) / 2 - 1;
            mui_gbm(ic, x + tw / 2 - gw(ic) * kk / 2, cy - gh(ic) * kk / 2, kk, -1);
            mui_text(x + tw / 2, y + th - m->px - 2 * s - 1, lb, -1, m->px, col, TF_CENTER | TF_SHADOW);
        } else {
            kk = (th - 6) / gh(ic); if (kk < 1) kk = 1; if (kk > s + 1) kk = s + 1;
            int iw = gw(ic) * kk, tw2 = mui_sw(m->px, lb, -1), x0 = x + (tw - (iw + m->pad + tw2)) / 2;
            mui_gbm(ic, x0, y + (th - gh(ic) * kk) / 2, kk, -1);
            mui_text(x0 + iw + m->pad, y + (th - m->px) / 2, lb, -1, m->px, col, TF_SHADOW);
        }
        mui_clip_reset();
        mui_hit(H_TILE + p, x, y, x + tw, y + th, HK_OK, 0xe, o);
    }
}

/* Phím điều hướng của KeyMainMenu: lưới 6 ô 3x2 (ô gốc {0,1,4,5,6,8}); điểm móc call-site `bl NumKeyToNaviKey` 0x11fc00 (hooks.def). Phím khác / ngoài state 4: giữ nguyên.
 * Trả mã navi mới; hướng đã xử lý -> trả 0 (KeyMainMenu: 0+5=5 > 4 -> thoát không làm gì). */
int mm_navkey(int key) {
    int n = StaticUtil_NumKeyToNaviKey(key);
    if (mui_flags & MUI_F_DISABLE) return n;
    if (n < -4 || n > -1) return n;
    u8 *t = *(u8 **)(gv + 0x244);
    if (!t || *(u32 *)t != (u32)MAIN_vtable + 8 || S8(t, 0xc) != 4) return n;
    int p = 0; for (int i = 0; i < 6; i++) if (T6[i] == S8(t, 0xe)) p = i;
    int c = p % 3, r = p / 3;
    if (n == -3) c = (c + 2) % 3;        /* TRÁI */
    else if (n == -4) c = (c + 1) % 3;   /* PHẢI */
    else r = 1 - r;                      /* LÊN/XUỐNG */
    S8(t, 0xe) = T6[r * 3 + c];
    return 0;
}

/* ---------------- state 5 sub 0: chọn slot ---------------- */
static u8 *slot_info(int i) { return *(u8 **)(gv + 4 * (1286 + i)); }
static void ring(int cx, int cy, int r) { mui_disc(cx, cy, r, C_ORANGE); mui_disc(cx, cy, r - 2 * L.s, RGB(20, 28, 42)); }
void mm_slot_paint(void *self) {
    mui_m *m = &L; int s = m->s, sel = S8(self, 0xe); mui_r c;
    dimwin(CT(175), &c);                                         /* state 6 (Chuyên Gia) sub 0 = cùng màn chọn slot (DrawExpert vẽ "Chọn Slot"; đo động) */
    int g = m->gap, rh = (c.y1 - c.y0 - 2 * g) / 3;
    for (int i = 0; i < 3; i++) {
        int y = c.y0 + i * (rh + g), x0 = c.x0, x1 = c.x1, in[4], cls = SlotInfo_GetHeroClass(slot_info(i)) << 24 >> 24;
        if (i == sel) { mui_frame(x0, y, x1, y + rh, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(66, 44, 20), RGB(46, 30, 14)); }
        else panel(x0, y, x1, y + rh, in);
        int pr = rh - 2 * (m->bt + 3); if (pr > (m->hd ? 84 : 22 * s + 20)) pr = m->hd ? 84 : 22 * s + 20;
        int px_ = x0 + m->bt + 4, py = y + (rh - pr) / 2, tx = px_ + pr + m->pad;
        ring(px_ + pr / 2, py + pr / 2, pr / 2);
        if (cls < 0) {
            mui_clip(tx, y, x1 - m->pad, y + rh);
            mui_text(tx, y + (rh - 2 * m->lp) / 2, CT(182), -1, m->px, C_DIM, 0);
            mui_text(tx, y + (rh - 2 * m->lp) / 2 + m->lp, CT(200), -1, m->px, C_DIM, 0);
            mui_clip_reset();
            mui_hit(H_SLOT + i, x0, y, x1, y + rh, HK_CB, 0, 0);
        } else {
            const u8 *ic = MIMG(29 + (cls & 3)); int kk = (pr / 2) / (gh(ic) ? gh(ic) : 1); if (kk < 1) kk = 1;
            mui_gbm(ic, px_ + pr / 2 - gw(ic) * kk / 2, py + pr / 2 - gh(ic) * kk / 2, kk, -1);
            int n; const u8 *dl = mui_hint(CT(194), &n); int dw = mui_sw(m->px, dl, n) + 4 * m->pad, bh = m->btnH;
            int bx0 = x1 - m->bt - 3 - dw, by0 = y + (rh - bh) / 2, ty = y + (rh - 3 * m->lp) / 2;
            u8 t[40]; int tn = 0; const u8 *lv = CT(13);
            mui_clip(tx, y, bx0 - m->pad, y + rh);
            mui_text(tx, ty, CT(178 + (cls & 3)), -1, m->px, C_GOLD, TF_SHADOW);
            { const u8 *q = lv; while (*q && tn < 20) t[tn++] = *q++; t[tn++] = ' '; tn += mui_itoa(SlotInfo_GetHeroLevel(slot_info(i)), t + tn); }
            mui_text(tx, ty + m->lp, t, tn, m->px, C_WHITE, 0);
            { int sec = SlotInfo_GetPlayTime(slot_info(i)); tn = 0; const u8 *q = CT(183); while (*q && tn < 20) t[tn++] = *q++; t[tn++] = ' ';
              int h = sec / 3600, mi = sec / 60 % 60, se = sec % 60;
              tn += mui_itoa(h, t + tn); t[tn++] = ':'; t[tn++] = '0' + mi / 10; t[tn++] = '0' + mi % 10; t[tn++] = ':'; t[tn++] = '0' + se / 10; t[tn++] = '0' + se % 10; }
            mui_text(tx, ty + 2 * m->lp, t, tn, m->px, C_DIM, 0);
            mui_clip_reset();
            mui_r b = {bx0, by0, bx0 + dw, by0 + bh}; if (GS(self) == 5) mui_button(&b, dl, n, 0, 0);
            mui_hit(H_SLOT + i, x0, y, bx0 - 2, y + rh, HK_CB, 0, 0);
            if (GS(self) == 5) mui_hit(H_DEL + i, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
        }
    }
}
static void goto_cur(void *self, int off, int tgt, int mod, int key) {   /* đưa con trỏ [self+off] tới tgt bằng phím gốc (vòng mod) */
    for (int i = 0; i < mod && S8(self, off) != tgt; i++) mui_key(self, MUI_CLS_MAIN, key);
}

/* ---------------- state 5 sub 1: chọn lớp ---------------- */
static int paras(const u8 *s, int n, u16 *st, u16 *ln, int maxp) {  /* ';;' = ngắt đoạn */
    int np = 0, i = 0;
    while (i < n && np < maxp) {
        while (i < n && s[i] == ';') i++;
        if (i >= n) break;
        int j = i; while (j < n && !(s[j] == ';' && j + 1 < n && s[j + 1] == ';')) j++;
        st[np] = i; ln[np] = j - i; np++; i = j;
    }
    return np;
}
static int desc_lines(const u8 *s, int maxw, u16 *st, u16 *ln, int maxl, int *blank_before) { /* đoạn -> dòng (dòng trống giữa các đoạn: ln=0) */
    u16 ps[8], pl[8]; int np = paras(s, mui_cslen(s), ps, pl, 8), nl = 0;
    for (int p = 0; p < np; p++) {
        if (p && nl < maxl) { st[nl] = 0; ln[nl] = 0; nl++; }
        u16 s2[24], l2[24]; int k = mui_wrap(L.px, s + ps[p], pl[p], maxw, s2, l2, 24);
        for (int i = 0; i < k && nl < maxl; i++) { st[nl] = ps[p] + s2[i]; ln[nl] = l2[i]; nl++; }
    }
    (void)blank_before; return nl;
}
void mm_class_paint(void *self) {
    mui_m *m = &L; int s = m->s, sel = S8(self, 0xf) & 3; mui_r c;
    if (!g_in_pop) mui_sc[2].pos = 0;
    dimwin(CT(176), &c);
    int g = m->gap + 2, cw = c.x1 - c.x0, lw = rnd(cw * 48, 100), ht = (c.y1 - c.y0 - g) / 2, tw = (lw - g) / 2, rx0 = c.x0 + lw + g;
    for (int i = 0; i < 4; i++) {
        int cc = i % 2, rw = i / 2, x = c.x0 + cc * (tw + g), y = c.y0 + rw * (ht + g);
        tilebox(x, y, x + tw, y + ht, i == sel);
        const u8 *ic = MIMG(29 + i); int kk = s + 2; int mx = (ht - m->px - 3 * s - 8) / (gh(ic) ? gh(ic) : 1); if (kk > mx) kk = mx; if (kk < 1) kk = 1;
        mui_gbm(ic, x + tw / 2 - gw(ic) * kk / 2, y + (ht - m->px - 2 * s) / 2 - 1 - gh(ic) * kk / 2, kk, -1);
        mui_clip(x + s + 1, y, x + tw - s - 1, y + ht);
        mui_text(x + tw / 2, y + ht - m->px - 2 * s - 2, CT(178 + i), -1, m->px, C_WHITE, TF_CENTER | TF_SHADOW);
        mui_clip_reset();
        mui_hit(H_CLS + i, x, y, x + tw, y + ht, HK_CB, 0, 0);
    }
    int in[4], p = m->pad / 2 + 2; panel(rx0, c.y0, c.x1, c.y1, in);
    int ix0 = rx0 + 3 + p, ix1 = c.x1 - 3 - p, yy = c.y0 + 3 + p;
    mui_clip(ix0, c.y0, ix1, c.y1); mui_text(ix0, yy, CT(178 + sel), -1, m->px, C_GOLD, TF_SHADOW); mui_clip_reset();
    yy += m->lp + 2; mui_fill(ix0, yy, ix1, yy + 1, C_LINE); yy += 4;
    int by = c.y1 - 3 - p - m->btnH; const u8 *d = MT(24 + sel);
    static u16 st[40], ln[40]; int nl = desc_lines(d, ix1 - ix0 - m->sbw - 2, st, ln, 40, NULL), view = (by - m->gap - yy) / m->lp; if (view < 1) view = 1;
    mui_clip(ix0, yy, ix1, by - m->gap);
    for (int i = 0; i < nl && i < view; i++) if (ln[i]) mui_text_line(ix0, yy + i * m->lp, d, st[i], ln[i], m->px, C_WHITE, 0);
    mui_clip_reset();
    if (nl > view) mui_scrollbar(ix1 - m->sbw, yy, by - m->gap, 0, nl, view);
    mui_r b = {ix0, by, ix1, by + m->btnH}; int bn; const u8 *bt = mui_hint(CT(56), &bn);
    mui_button(&b, bt, bn, 1, 0); mui_hit(H_OK, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}

/* ---------------- popup lớp (StatePopup loại 8, cha = MainMenu): mô tả đầy đủ + OK/Huỷ ---------------- */
static void parent_paint(void *self) {
    u32 par = *(u32 *)((u8 *)self + 8);
    if (!par) return;
    void (**vt)(void *) = *(void (***)(void *))par;
    g_in_pop = 1; vt[0x40 / 4]((void *)par); g_in_pop = 0;
}
void mmpop_paint(void *self) {
    mui_m *m = &L; int s = m->s;
    parent_paint(self);
    if (!mui_begin()) return;
    s8 *res = RES(self); if (res && *res == -1) return;
    u32 par = *(u32 *)((u8 *)self + 8); int cls = par ? S8((void *)par, 0xf) & 3 : 0, cur = res ? *res : 0, n1, n2;
    int pw = m->W - 2 * m->mg; { int c2 = m->hd ? 640 : rnd(82 * m->W, 100); if (pw > c2) pw = c2; }
    int iw = pw - 2 * (m->pad + 2 * s + 4), tw = iw - m->sbw - 3, pr = 2 * m->lp + 8;
    const u8 *d = (u8 *)self + 0x24, *l1 = mui_hint((u8 *)self + 0x43c, &n1), *l2 = mui_hint((u8 *)self + 0x46c, &n2);
    static u16 st[60], ln[60]; int nl = desc_lines(d, tw, st, ln, 60, NULL);
    int fixed = 2 * m->pad + 4 * s + pr + m->pad + m->pad + m->btnH + 2 + 4, maxview = (m->Ht - 2 * m->mg - fixed) / m->lp, view = nl < maxview ? nl : maxview;
    if (view < 1) view = 1;
    int ph = fixed + view * m->lp, x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2, in[4];
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int ix0 = in[0] + m->pad + 2 * s, ix1 = in[2] - m->pad - 2 * s, y = in[1] + m->pad + 2 * s;
    ring(ix0 + pr / 2, y + pr / 2, pr / 2);
    { const u8 *ic = MIMG(29 + cls); int kk = (pr / 2) / (gh(ic) ? gh(ic) : 1); if (kk < 1) kk = 1; mui_gbm(ic, ix0 + pr / 2 - gw(ic) * kk / 2, y + pr / 2 - gh(ic) * kk / 2, kk, -1); }
    mui_text(ix0 + pr + m->pad, y + (pr - m->px) / 2, (u8 *)self + 0x49c, -1, m->px, C_GOLD, TF_SHADOW);
    y += pr + m->pad / 2 + 1;
    int bx1 = ix1; mui_hit(H_PBOX, ix0, y, bx1, y + view * m->lp + 4, HK_DRAG, 2, m->lp);
    panel(ix0, y, bx1, y + view * m->lp + 4, NULL);
    mui_sc[2].total = nl; mui_sc[2].view = view; { int mx = nl - view; if (mx < 0) mx = 0; if (mui_sc[2].pos > mx) mui_sc[2].pos = mx; }
    mui_clip(ix0 + 2, y + 2, bx1 - m->sbw - 2, y + 2 + view * m->lp);
    for (int i = 0; i < view; i++) { int j = mui_sc[2].pos + i; if (j < nl && ln[j]) mui_text_line(ix0 + 4, y + 2 + i * m->lp, d, st[j], ln[j], m->px, C_WHITE, 0); }
    mui_clip_reset();
    if (nl > view) mui_scrollbar(bx1 - m->sbw - 2, y + 2, y + 2 + view * m->lp, mui_sc[2].pos, nl, view);
    int by = in[3] - m->pad - m->btnH, mid = (ix0 + ix1) / 2, gg = m->pad;
    mui_r b1 = {ix0, by, mid - gg / 2, by + m->btnH}, b2 = {mid + gg / 2 + 1, by, ix1, by + m->btnH};
    mui_button(&b1, l1, n1, cur == 0 ? 1 : 0, H_PYES); mui_button(&b2, l2, n2, cur == 1 ? 1 : 0, H_PNO);
}
int mmpop_touch(void *self, int x, int y) {
    s8 *res = RES(self);
    int k = mui_touch_press(self, MUI_CLS_POPUP, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if ((id == H_PYES || id == H_PNO) && res) { *res = id - H_PYES; mui_key(self, MUI_CLS_POPUP, -5); }
    return 0;
}
int mmpop_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_POPUP, x, y); }

/* ---------------- state 9 sub 0: Cài đặt (10 mục thật) ---------------- */
static int g_orh = 20;                                          /* bước hàng (px) của danh sách Cài đặt lần vẽ cuối (kéo dọc) */
static const s8 OTYPE[10] = {1, 1, 2, 0, 4, 0, 0, 3, -1, 5};
static const u8 *olabel(int i, int *n) {
    *n = -1;
    if (i < 6) return CT(204 + i);
    if (i == 6) return CT(288);
    if (i == 7) return CT(341);
    if (i == 8) return MT(0);
    return CT(53);
}
static int cur_k(void) {
    vn_state_t *v = vn_state;
    if (!v->k) { char b[4] = {0, 0, 0, 0}; int n = StaticUtil_FileRead("hd_scale.txt", b, 2), k = MM_DEFAULT_K; if (n > 0 && b[0] >= '1' && b[0] <= '4' && (n < 2 || (u8)b[1] <= ' ')) k = b[0] - '0'; return k; }
    return v->k;
}
static int oval(int i) { return i == 7 ? S8(gv, 142) : S8(gv, 132 + i); }
static void ochip(int x, int y, int w, int h, const u8 *l, int on, int hid) {
    mui_m *m = &L; int in[4];
    if (on) { mui_frame(x, y, x + w, y + h, m->rad, 1, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 190, 60), RGB(214, 118, 14)); }
    else { mui_frame(x, y, x + w, y + h, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(28, 40, 58), RGB(18, 26, 40)); }
    mui_clip(x + 1, y, x + w - 1, y + h);
    mui_text(x + w / 2, y + (h - m->px) / 2, l, -1, m->px, on ? C_WHITE : RGB(170, 184, 204), TF_CENTER | (on ? TF_SHADOW : 0));
    mui_clip_reset();
    mui_hit(hid, x, y, x + w, y + h, HK_CB, 0, 0);
}
void mm_option_paint(void *self) {
    mui_m *m = &L; mui_r c; int row = S8(self, 0xe), ab = S8(self, 0xf), top = ab - row, g = 2, s = m->s;
    dimwin(CT(170), &c);
    if (top < 0) top = 0;
    int note_h = m->slp + 2, rh = m->lp + 10, avail = (c.y1 - c.y0 - note_h - 2 - 6 * g) / 7;
    if (rh > avail) rh = avail; else if (avail > rh) rh = avail < rh + 12 ? avail : rh + 12;   /* lấp đầy cửa sổ (tối đa +12 px/hàng) */
    g_orh = rh + g;
    int y = c.y0, x0 = c.x0, x1 = c.x1;
    for (int r = 0; r < 7 && top + r < 10; r++, y += rh + g) {
        int i = top + r, sel = r == row, ty = OTYPE[i], n, ch = rh - 4, cy = y + 2, used = 0;
        mui_fill(x0, y, x1, y + rh, C_INK);
        if (sel) mui_vgrad(x0 + 1, y + 1, x1 - 1, y + rh - 1, RGB(250, 160, 36), RGB(196, 96, 12)); else mui_vgrad(x0 + 1, y + 1, x1 - 1, y + rh - 1, C_CELL, C_CELL2);
        const u8 *lb = olabel(i, &n);
        mui_hit(H_ROW + r, x0, y, x1, y + rh, HK_CB, 0, 0);
        if (ty == 0 || ty == 1) {                                  /* BẬT/TẮT: 2 chip */
            const u8 *on = CT(191), *off = CT(190); int cw = mui_sw(m->px, on, -1), c2 = mui_sw(m->px, off, -1); if (c2 > cw) cw = c2; cw += 2 * m->pad;
            int tot = 2 * cw + 2, sx = x1 - m->pad - tot, v = oval(i) != 0;
            ochip(sx, cy, cw, ch, on, v, H_CHIP + 8 * r + 0); ochip(sx + cw + 2, cy, cw, ch, off, !v, H_CHIP + 8 * r + 1); used = tot + 2 * m->pad;
        } else if (ty == 2 || ty == 3 || ty == 4) {              /* ◄ giá trị ► */
            u8 t[12]; const u8 *vs; int vn, v = oval(i), vw = 0;
            if (ty == 2) { vn = mui_itoa(v + 1, t); vs = t; } else { vs = ty == 4 ? CT(321 + v) : CT(342 + v); vn = -1; }
            for (int q = 0; q < 3; q++) { const u8 *z = ty == 4 ? CT(321 + q) : ty == 3 ? CT(342 + (q & 1)) : (const u8 *)"10"; int w = mui_sw(m->px, z, -1); if (w > vw) vw = w; }
            vw += 2 * m->pad; int rx = x1 - m->pad - ch, lx = rx - vw - ch;
            mui_r a1 = {lx, cy, lx + ch, cy + ch}, a2 = {rx, cy, rx + ch, cy + ch};
            mui_arrow(&a1, 0, H_L + r); mui_arrow(&a2, 1, H_R + r);
            mui_clip(lx + ch, y, rx, y + rh); mui_text(lx + ch + vw / 2, y + (rh - m->px) / 2, vs, vn, m->px, sel ? C_WHITE : C_GOLD, TF_CENTER | (sel ? TF_SHADOW : 0)); mui_clip_reset();
            used = x1 - lx + m->pad;
        } else if (ty == 5) {                                       /* Phân giải: 1/1..1/4 */
            static const u8 K[4][4] = {{'1', '/', '1', 0}, {'1', '/', '2', 0}, {'1', '/', '3', 0}, {'1', '/', '4', 0}};
            int cw = mui_sw(m->px, K[0], -1) + 2 * m->pad, tot = 4 * cw + 3 * 2, sx = x1 - m->pad - tot, k = cur_k() - 1;
            for (int q = 0; q < 4; q++) ochip(sx + q * (cw + 2), cy, cw, ch, K[q], q == k, H_CHIP + 8 * r + q);
            used = tot + 2 * m->pad;
        } else if (ty == -1) used = 0;
        mui_clip(x0, y, x1 - used, y + rh);
        mui_text(x0 + m->pad, y + (rh - m->px) / 2, lb, -1, m->px, C_WHITE, sel ? TF_SHADOW : 0);
        mui_clip_reset();
        (void)s;
    }
    mui_scrollbar(c.x1 - m->sbw, c.y0, c.y1 - note_h - 2, top, 10, 7);
    mui_hit(H_UP, c.x1 - m->sbw - 2, c.y0, c.x1, (c.y0 + c.y1 - note_h) / 2, HK_CB, 0, 0);
    mui_hit(H_DN, c.x1 - m->sbw - 2, (c.y0 + c.y1 - note_h) / 2, c.x1, c.y1 - note_h - 2, HK_CB, 0, 0);
    if (ab == 9 && vn_state->status > 0 && vn_state->t10) {         /* ghi chú khởi động lại (chuỗi T10 trong vi.bin) */
        static u8 note[64]; const u8 *p = vn_state->buf + vn_state->t10; int cnt = *p++, ln = 0;
        if (cnt > 0) { ln = *p++; if (ln > 62) ln = 62; for (int i = 0; i < ln; i++) note[i] = p[i]; note[ln] = 0; mui_text(c.x0 + m->pad, y + 1, note, ln, m->spx, C_GOLD, 0); }
    }
}
static int g_move_acc, g_move_y0, g_move_id;
static int opt_do(void *self, int id) {
    if (id >= H_ROW && id < H_ROW + 7) { int r = id - H_ROW, nab = S8(self, 0xf) - S8(self, 0xe) + r; S8(self, 0xe) = r > 5 ? 5 : r; S8(self, 0xf) = nab; return 1; }   /* như phím: hàng cửa sổ gốc 0..5 (hàng 7 hiển thị = hàng 5 + cuộn) */
    return 0;
}
int mm_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_MAIN, x, y), id = mui_last_id; int st = S8(self, 0xc), sub = S8(self, 0xd);
    g_move_y0 = y; g_move_acc = 0; g_move_id = -1;
    if (k != HK_CB) return 0;
    if ((st == 5 || st == 6) && sub == 0) {
        if (id >= H_SLOT && id < H_SLOT + 3) { goto_cur(self, 0xe, id - H_SLOT, 3, -2); mui_key(self, MUI_CLS_MAIN, -5); }
        else if (st == 5 && id >= H_DEL && id < H_DEL + 3) { goto_cur(self, 0xe, id - H_DEL, 3, -2); mui_key(self, MUI_CLS_MAIN, 42); }
    } else if (st == 5 && sub == 1) {
        if (id >= H_CLS && id < H_CLS + 4) {
            int t = id - H_CLS, c = S8(self, 0xf) & 3;
            if (c == t) mui_key(self, MUI_CLS_MAIN, -5);
            else { if ((c ^ t) & 1) mui_key(self, MUI_CLS_MAIN, -4); if (((S8(self, 0xf) & 3) ^ t) & 2) mui_key(self, MUI_CLS_MAIN, -2); }
        } else if (id == H_OK) mui_key(self, MUI_CLS_MAIN, -5);
    } else if (st == 9) {
        if (id == H_UP) mui_key(self, MUI_CLS_MAIN, -1);
        else if (id == H_DN) mui_key(self, MUI_CLS_MAIN, -2);
        else if (id >= H_ROW && id < H_ROW + 7) { int same = id - H_ROW == S8(self, 0xe) && OTYPE[S8(self, 0xf)] == -1; opt_do(self, id); g_move_id = 1; if (same) mui_key(self, MUI_CLS_MAIN, -5); }   /* L6c: hàng nhãn (Quản lý dữ liệu) đang chọn + chạm lại = OK gốc (TouchOption 0x118a08) */
        else if (id >= H_L && id < H_L + 7) { opt_do(self, H_ROW + (id - H_L)); mui_key(self, MUI_CLS_MAIN, -3); }
        else if (id >= H_R && id < H_R + 7) { opt_do(self, H_ROW + (id - H_R)); mui_key(self, MUI_CLS_MAIN, -4); }
        else if (id >= H_CHIP && id < H_CHIP + 56) {
            int r = (id - H_CHIP) / 8, q = (id - H_CHIP) % 8, ab; opt_do(self, H_ROW + r); ab = S8(self, 0xf);
            if (ab == 9) { for (int i = 0; i < 4 && cur_k() != q + 1; i++) mui_key(self, MUI_CLS_MAIN, -4); }
            else if (OTYPE[ab] <= 1 && (oval(ab) != 0) != (q == 0)) mui_key(self, MUI_CLS_MAIN, -4);
        }
    }
    return 0;
}
int mm_move(void *self, int x, int y) {
    (void)x;
    if (S8(self, 0xc) == 9 && g_move_id == 1) {                     /* kéo dọc danh sách Cài đặt = phím LÊN/XUỐNG theo hàng */
        int rh = g_orh > 0 ? g_orh : L.lp + 10, d = (g_move_y0 - y) / rh;
        while (g_move_acc < d) { mui_key(self, MUI_CLS_MAIN, -2); g_move_acc++; }
        while (g_move_acc > d) { mui_key(self, MUI_CLS_MAIN, -1); g_move_acc--; }
    }
    return mui_touch_move(self, MUI_CLS_MAIN, x, y);
}

/* L6b: xuất khung chung cho mm2.c (Chuyên Gia chọn chế độ / Thành tựu / Trợ giúp) */
void mm_dimwin(const u8 *title, mui_r *c) { dimwin(title, c); }
void mm_tilebox(int x0, int y0, int x1, int y1, int sel) { tilebox(x0, y0, x1, y1, sel); }
void mm_panel(int x0, int y0, int x1, int y1) { panel(x0, y0, x1, y1, NULL); }
