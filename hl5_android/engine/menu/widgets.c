/* Bộ widget chung (LAYOUT.md §5): cửa sổ, nút, thanh cuộn, thanh HP/SP/EXP, ô lưới, hàng, hộp mô tả, popup. Số đo = L (mui_layout).
 * Mỗi widget vẽ thẳng vào khung hình (gfx.c) và (nếu hid>0) đăng ký vùng chạm HK_CB để state xử lý. */
#include "mui.h"

#define HID_CLOSE 1
#define HID_TAB0 0x10
#define MUI_KEY_BACK (-16)             /* CLR; GIẢ ĐỊNH, c1 xác nhận bằng KeyPressed gốc của InGameMenu */

__attribute__((weak)) const u8 *mui_tab_icon(int i) { (void)i; return NULL; }
__attribute__((weak)) const u8 *mui_icon_back(void) { return NULL; }

int mui_popup_sel;
static int rnd(int a, int b) { return (2 * a + b) / (2 * b); }   /* round(a/b), a,b > 0 */

void mui_window(const mui_r *r, const u8 *title, int tlen, int ntabs, const u8 *const *unused, int active, mui_r *body) {
    mui_m *m = &L; int s = m->s, in[4];
    (void)unused;
    mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 1, m->bt, C_NAVY, 240, in);
    int cx0 = in[0], cy0 = in[1], cx1 = in[2], cy1 = in[3], th = m->titleH;
    mui_vgrad(cx0, cy0, cx1, cy0 + th, RGB(252, 176, 44), RGB(206, 104, 12));
    mui_fill(cx0, cy0 + th - 1, cx1, cy0 + th, C_INK);
    int bw = th - 2, bx1 = cx0 + 1 + bw + 4 * s;
    mui_clip(cx0 + bx1 - cx0, cy0, cx1, cy0 + th);
    mui_text((cx0 + cx1) / 2, cy0 + (th - m->px) / 2, title, tlen, m->px, C_WHITE, TF_CENTER | TF_SHADOW);
    mui_clip_reset();
    mui_fill(cx0 + 1, cy0 + 1, bx1, cy0 + th - 1, RGB(120, 56, 8));
    mui_fill(bx1 - 1, cy0 + 1, bx1, cy0 + th - 1, C_INK);
    {   /* nút "<": sprite ui 25 x kb; chưa có sprite -> chevron vẽ bằng điểm */
        int cx = (cx0 + 1 + bx1) / 2, cy = (cy0 + 1 + cy0 + th - 1) / 2, k = m->kb;
        const u8 *g = mui_icon_back();
        if (g) mui_gbm(g, cx - (*(const u16 *)(g + 2) * k) / 2, cy - (*(const u16 *)(g + 4) * k) / 2, k, -1);
        else for (int i = 0; i < 5; i++) {
            mui_fill(cx - 2 * k + i * k, cy - i * k, cx - k + i * k + k, cy - i * k + k, RGB(255, 168, 40));
            if (i) mui_fill(cx - 2 * k + i * k, cy + i * k, cx - k + i * k + k, cy + i * k + k, RGB(255, 168, 40));
        }
    }
    mui_hit(HID_CLOSE, cx0, cy0, bx1 + 2 * s, cy0 + th, HK_KEY, MUI_KEY_BACK, 0);
    body->x0 = cx0; body->y0 = cy0 + th; body->x1 = cx1; body->y1 = cy1;
    if (ntabs > 0) {
        int tb = m->tabH, ty = cy1 - tb, dx = cx1 - cx0;
        mui_fill(cx0, ty, cx1, cy1, RGB(7, 11, 18));
        mui_fill(cx0, ty, cx1, ty + 1, C_INK);
        mui_fill(cx0, ty + 1, cx1, ty + 1 + (s > 1 ? s : 1), C_ORANGE_DK);
        for (int i = 0; i < ntabs; i++) {
            int x0 = cx0 + rnd(i * dx, ntabs), x1 = cx0 + rnd((i + 1) * dx, ntabs);
            if (i == active) {
                mui_vgrad(x0 + 1, ty + 2 + s, x1 - 1, cy1 - 1, RGB(250, 160, 36), RGB(200, 100, 14));
                mui_fill(x0, ty + 2 + s, x0 + 1, cy1 - 1, C_INK); mui_fill(x1 - 1, ty + 2 + s, x1, cy1 - 1, C_INK);
            }
            const u8 *g = mui_tab_icon(i);
            int cx = (x0 + x1) / 2, cy = cy1 - tb / 2 + (1 + s) / 2;
            u32 tc = i == active ? C_WHITE : C_DIM;
            if (g) mui_gbm(g, cx - (*(const u16 *)(g + 2) * s) / 2, cy - (*(const u16 *)(g + 4) * s) / 2, s, tc);
            else mui_fill(cx - 3 * s, cy - 3 * s, cx + 3 * s, cy + 3 * s, tc);
            mui_hit(HID_TAB0 + i, x0, ty, x1, cy1, HK_CB, 0, 0);
        }
        body->y1 = ty;
    }
}

void mui_button(const mui_r *r, const u8 *label, int len, int kind, int hid) {
    mui_m *m = &L; int in[4]; u32 col = C_WHITE; int sh = 0;
    if (kind == 3) { mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 0, m->bt, RGB(20, 26, 36), 255, in); col = C_DISABLED; }
    else {
        mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 1, m->bt, (u32)-1, 0, in);
        if (kind == 1) { mui_vgrad(in[0], in[1], in[2], in[3], RGB(250, 164, 40), RGB(204, 100, 12)); sh = 1; }
        else if (kind == 2) { mui_vgrad(in[0], in[1], in[2], in[3], RGB(150, 78, 12), RGB(110, 52, 8)); col = C_GOLD; sh = 1; }
        else mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
    }
    mui_clip(r->x0 + m->pad, r->y0, r->x1 - m->pad, r->y1);
    mui_text((r->x0 + r->x1) / 2, r->y0 + (r->y1 - r->y0 - m->px) / 2, label, len, m->px, col, TF_CENTER | (sh ? TF_SHADOW : 0));
    mui_clip_reset();
    if (hid > 0) mui_hit(hid, r->x0, r->y0, r->x1, r->y1, HK_CB, 0, 0);
}

void mui_scrollbar(int x, int y0, int y1, int pos, int total, int view) {
    mui_m *m = &L; int w = m->sbw;
    mui_fill(x, y0, x + w, y1, C_TRACK); mui_fill(x, y0, x + 1, y1, C_INK);
    if (total < 1) total = 1;
    int th = view >= total ? y1 - y0 : rnd((y1 - y0) * view, total), mn = 2 * m->s + 6;
    if (th < mn) th = mn;
    int d = total - view < 1 ? 1 : total - view, ty = y0 + rnd((y1 - y0 - th) * pos, d);
    if (m->s > 1) mui_rr(x + 1, ty, x + w, ty + th, 1, C_ORANGE, 255); else mui_fill(x + 1, ty, x + w, ty + th, C_ORANGE);
    mui_fill(x + 1, ty, x + 2, ty + th, C_ORANGE_HI);
}

static int itoa_(int v, u8 *o) {
    u8 t[12]; int n = 0, k = 0;
    if (v < 0) { o[k++] = '-'; v = -v; }
    do { t[n++] = '0' + v % 10; v /= 10; } while (v);
    while (n) o[k++] = t[--n];
    return k;
}
int mui_itoa(int v, u8 *o) { return itoa_(v, o); }

void mui_bar(int x, int y, int w, const u8 *label, int llen, int val, int mx, u32 col) {
    mui_m *m = &L;
    static const u8 EXP_[] = {'E', 'X', 'P'};
    int lw = mui_sw(m->px, EXP_, 3) + 2 * m->s + 2;
    mui_text(x, y + (m->barH - m->px) / 2 + (m->hd ? 0 : 1), label, llen, m->px, C_DIM, 0);
    int bx = x + lw, x1 = x + w, y1 = y + m->barH;
    mui_fill(bx, y, x1, y1, C_INK); mui_fill(bx + 1, y + 1, x1 - 1, y1 - 1, C_TRACK);
    int fw = rnd((x1 - bx - 2) * (val < 0 ? 0 : val), mx < 1 ? 1 : mx);
    int r = (col >> 16) & 255, g = (col >> 8) & 255, b = col & 255;
    mui_vgrad(bx + 1, y + 1, bx + 1 + fw, y1 - 1, RGB(r + 40 > 255 ? 255 : r + 40, g + 40 > 255 ? 255 : g + 40, b + 40 > 255 ? 255 : b + 40), col);
    u8 t[24]; int n = itoa_(val, t); t[n++] = '/'; n += itoa_(mx, t + n);
    mui_text((bx + x1) / 2, y + (m->barH - m->spx) / 2, t, n, m->spx, C_WHITE, TF_CENTER | (m->hd ? TF_SHADOW : 0));
}

void mui_cell(int x, int y, const u8 *gbm, int count, int rarity_rgb, int equipped, int selected, int hid) {
    mui_m *m = &L; int s = m->s, c = m->cell;
    mui_fill(x, y, x + c, y + c, C_INK);
    if (selected) mui_vgrad(x + 1, y + 1, x + c - 1, y + c - 1, RGB(250, 160, 36), RGB(196, 96, 12));
    else mui_vgrad(x + 1, y + 1, x + c - 1, y + c - 1, C_CELL, C_CELL2);
    if (gbm) {
        int w = *(const u16 *)(gbm + 2), h = *(const u16 *)(gbm + 4);
        mui_gbm(gbm, x + (c - w * s) / 2, y + (c - h * s) / 2, s, -1);
        int vs = s > 1 ? s : 1;
        if (rarity_rgb >= 0) mui_fill(x + 1, y + 1, x + c - 1, y + 1 + vs, (u32)rarity_rgb);
        if (equipped) {
            int fw = 5 * s + 1;
            mui_fill(x + 1, y + 1 + vs, x + 1 + fw, y + 1 + vs + fw, C_INK);
            mui_fill(x + 2, y + 2 + vs, x + fw, y + vs + fw, C_GOLD);
        }
        if (count > 1) {
            u8 t[12]; int n = itoa_(count, t);
            int tw = mui_sw(m->spx, t, n);
            mui_text(x + c - 2 - tw, y + c - 2 - m->spx, t, n, m->spx, C_WHITE, TF_SHADOW);
        }
    }
    if (selected) {
        mui_fill(x, y, x + c, y + 1, C_ORANGE_HI); mui_fill(x, y + c - 1, x + c, y + c, C_ORANGE_HI);
        mui_fill(x, y, x + 1, y + c, C_ORANGE_HI); mui_fill(x + c - 1, y, x + c, y + c, C_ORANGE_HI);
        mui_fill(x - 1, y - 1, x + c + 1, y, C_INK); mui_fill(x - 1, y + c, x + c + 1, y + c + 1, C_INK);
        mui_fill(x - 1, y, x, y + c, C_INK); mui_fill(x + c, y, x + c + 1, y + c, C_INK);
    }
    if (hid > 0) mui_hit(hid, x, y, x + c, y + c, HK_CB, 0, 0);
}

void mui_row(const mui_r *r, const u8 *label, int llen, const u8 *value, int vlen, int selected, int hid) {
    mui_m *m = &L;
    mui_fill(r->x0, r->y0, r->x1, r->y1, C_INK);
    if (selected) mui_vgrad(r->x0 + 1, r->y0 + 1, r->x1 - 1, r->y1 - 1, RGB(250, 160, 36), RGB(196, 96, 12));
    else mui_vgrad(r->x0 + 1, r->y0 + 1, r->x1 - 1, r->y1 - 1, C_CELL, C_CELL2);
    int ty = r->y0 + (r->y1 - r->y0 - m->px) / 2;
    int vw = vlen ? mui_sw(m->px, value, vlen) : 0;
    mui_clip(r->x0, r->y0, r->x1 - vw - 2 * m->pad, r->y1);
    mui_text(r->x0 + m->pad, ty, label, llen, m->px, C_WHITE, selected ? TF_SHADOW : 0);
    mui_clip_reset();
    if (vlen) mui_text(r->x1 - m->pad, ty, value, vlen, m->px, selected ? C_WHITE : C_GOLD, TF_RIGHT | (selected ? TF_SHADOW : 0));
    if (hid > 0) mui_hit(hid, r->x0, r->y0, r->x1, r->y1, HK_CB, 0, 0);
}

/* khung hộp mô tả + tên (tối đa 2 dòng) + [dòng phụ spx mờ] + vạch; trả y bắt đầu vùng nội dung. */
int mui_descbox2(const mui_r *r, const u8 *name, int nlen, int name_rgb, const u8 *meta, int mlen) {
    mui_m *m = &L; int in[4];
    mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 0, 1, RGB(10, 16, 26), 235, in);
    int p = m->pad / 2 + 1, y = in[1] + p;
    u16 st[2], ln[2];
    int n = mui_wrap(m->px, name, nlen, in[2] - in[0] - 2 * p - m->sbw - 2, st, ln, 2);
    for (int i = 0; i < n; i++) { mui_text_line(in[0] + p, y, name, st[i], ln[i], m->px, name_rgb < 0 ? C_WHITE : (u32)name_rgb, TF_SHADOW); y += m->lp; }
    if (meta) {
        mui_clip(in[0] + p, y, in[2] - p, y + m->slp);
        mui_text(in[0] + p, y, meta, mlen, m->spx, C_DIM, 0); mui_clip_reset(); y += m->slp;
    }
    y += 2;
    mui_fill(in[0] + p, y, in[2] - p, y + 1, C_LINE);
    return y + 3;
}
int mui_descbox(const mui_r *r, const u8 *name, int nlen, int name_rgb) { return mui_descbox2(r, name, nlen, name_rgb, NULL, 0); }

void mui_icon(int idx, int x, int y, int s, int tint) {
    static u16 bg[400], px[400];
    if (idx < 0) return;
    if (idx <= 225) { mui_gbm(ITEM_ICON(idx), x, y, s, tint); return; }
    u32 g = _lcdG; int fb = Graphic_GetFB(g);                 /* icon > 225: guest tự vẽ (palette/HSB riêng): vẽ x1 ngay tại chỗ, đọc lại điểm thay đổi, khôi phục nền, phóng x s */
    for (int i = 0; i < 400; i++) bg[i] = mui_getpx(x + i % 20, y + i / 20);
    CommonUi_DrawIcon(COMMONUI(), fb, x + 10, y + 10, idx, 0, 255, 100, 18);   /* như DrawBagMenu gốc (0xf7df4): tham số sau icon = 0, 80, 100, 18 */
    for (int i = 0; i < 400; i++) { px[i] = mui_getpx(x + i % 20, y + i / 20); mui_put16(x + i % 20, y + i / 20, bg[i]); }
    for (int i = 0; i < 400; i++) {
        if (px[i] == bg[i]) continue;
        u32 c = px[i], r = (c >> 11 & 31), gg = (c >> 5 & 63), b = (c & 31);
        mui_fill(x + (i % 20) * s, y + (i / 20) * s, x + (i % 20) * s + s, y + (i / 20) * s + s, RGB(r << 3 | r >> 2, gg << 2 | gg >> 4, b << 3 | b >> 2));
    }
}

void mui_cell_i(int x, int y, int icon, int count, int rarity_rgb, int flag, int selected, int hid) {
    mui_m *m = &L; int s = m->s, c = m->cell;
    mui_fill(x, y, x + c, y + c, C_INK);
    if (selected) mui_vgrad(x + 1, y + 1, x + c - 1, y + c - 1, RGB(250, 160, 36), RGB(196, 96, 12));
    else mui_vgrad(x + 1, y + 1, x + c - 1, y + c - 1, C_CELL, C_CELL2);
    if (icon >= 0) {
        mui_icon(icon, x + (c - 20 * s) / 2, y + (c - 20 * s) / 2, s, -1);
        int vs = s > 1 ? s : 1;
        if (rarity_rgb >= 0) mui_fill(x + 1, y + 1, x + c - 1, y + 1 + vs, (u32)rarity_rgb);
        if (flag) {
            int fw = 5 * s + 1;
            mui_fill(x + 1, y + 1 + vs, x + 1 + fw, y + 1 + vs + fw, C_INK);
            mui_fill(x + 2, y + 2 + vs, x + fw, y + vs + fw, C_GOLD);
        }
        if (count > 1) {
            u8 t[12]; int n = itoa_(count, t);
            int tw = mui_sw(m->spx, t, n);
            mui_text(x + c - 2 - tw, y + c - 2 - m->spx, t, n, m->spx, C_WHITE, TF_SHADOW);
        }
    }
    if (selected) {
        mui_fill(x, y, x + c, y + 1, C_ORANGE_HI); mui_fill(x, y + c - 1, x + c, y + c, C_ORANGE_HI);
        mui_fill(x, y, x + 1, y + c, C_ORANGE_HI); mui_fill(x + c - 1, y, x + c, y + c, C_ORANGE_HI);
        mui_fill(x - 1, y - 1, x + c + 1, y, C_INK); mui_fill(x - 1, y + c, x + c + 1, y + c + 1, C_INK);
        mui_fill(x - 1, y, x, y + c, C_INK); mui_fill(x + c, y, x + c + 1, y + c, C_INK);
    }
    if (hid > 0) mui_hit(hid, x, y, x + c, y + c, HK_CB, 0, 0);
}

void mui_arrow(const mui_r *r, int right, int hid) {      /* nút mũi tên ◀ ▶ (mock w_arrow): khung cam, nền xanh, tam giác cam sáng */
    mui_m *m = &L; int in[4], cx = (r->x0 + r->x1) / 2, cy = (r->y0 + r->y1) / 2, wd = r->x1 - r->x0, ht = r->y1 - r->y0;
    int k = (wd < ht ? wd : ht) / 4;
    if (k < 3) k = 3;
    mui_frame(r->x0, r->y0, r->x1, r->y1, m->rad, 1, m->bt, (u32)-1, 0, in);
    mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
    int xt = right ? cx + k / 2 + 1 : cx - k / 2 - 1, xb = right ? cx - k / 2 : cx + k / 2, dx = xb > xt ? 1 : -1, n = (xb - xt) * dx;
    for (int i = 0; i <= n; i++) {
        int x = xt + dx * i, hh = n ? k * i / n : k;
        mui_fill(x, cy - hh, x + 1, cy + hh + 1, C_ORANGE_HI);
    }
    if (hid) mui_hit(hid, r->x0, r->y0, r->x1, r->y1, HK_CB, 0, 0);
}

void mui_hscroll(int x0, int y, int x1, int pos, int total, int view) {
    mui_m *m = &L; int h = m->sbw;
    mui_fill(x0, y, x1, y + h, C_TRACK); mui_fill(x0, y, x1, y + 1, C_INK);
    if (total < 1) total = 1;
    int tw = view >= total ? x1 - x0 : rnd((x1 - x0) * view, total), mn = 2 * m->s + 6;
    if (tw < mn) tw = mn;
    int d = total - view < 1 ? 1 : total - view, tx = x0 + rnd((x1 - x0 - tw) * pos, d);
    if (m->s > 1) mui_rr(tx, y + 1, tx + tw, y + h, 1, C_ORANGE, 255); else mui_fill(tx, y + 1, tx + tw, y + h, C_ORANGE);
    mui_fill(tx, y + 1, tx + tw, y + 2, C_ORANGE_HI);
}

void mui_popup_geom(int nlines, int *pw, int *ph) {
    mui_m *m = &L;
    int w = m->hd ? 480 : (70 * m->W + 50) / 100;
    if (w > m->W - 2 * m->mg) w = m->W - 2 * m->mg;
    *pw = w;
    *ph = 2 * m->pad + 4 * m->s + nlines * m->lp + m->pad + m->btnH + 2;
}

/* hộp popup tại (x0,y0) rộng pw (không nền tối, không modal); trả chiều cao qua ph_out. Dùng cho mui_popup và trang widget. */
void mui_popup_box(int x0, int y0, int pw, const u8 *text, int tlen, int yes_no, const u8 *l1, int n1, const u8 *l2, int n2, int *ph_out) {
    mui_m *m = &L; int s = m->s, ph, in[4], pw2;
    u16 st[8], ln[8];
    int tw = pw - 2 * (m->pad + 2 * s + 4);
    int n = mui_wrap(m->px, text, tlen, tw, st, ln, 8);
    mui_popup_geom(n, &pw2, &ph);
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int y = in[1] + m->pad + 2 * s, cx = x0 + pw / 2;
    for (int i = 0; i < n; i++) { mui_text_skip(cx, y, text + st[i], ln[i], m->px, C_WHITE, TF_CENTER); y += m->lp; }
    int by = in[3] - m->pad - m->btnH, bx0 = in[0] + m->pad + 2 * s, bx1 = in[2] - m->pad - 2 * s;
    mui_r b;
    if (yes_no) {
        int mid = (bx0 + bx1) / 2, gg = m->pad;
        b.x0 = bx0; b.y0 = by; b.x1 = mid - gg / 2; b.y1 = by + m->btnH; mui_button(&b, l1, n1, mui_popup_sel == 0 ? 1 : 0, 0xF0);
        b.x0 = mid + gg / 2 + 1; b.x1 = bx1; mui_button(&b, l2, n2, mui_popup_sel == 1 ? 1 : 0, 0xF1);
    } else {
        int bw = mui_sw(m->px, l1, n1) + 4 * m->pad;
        if (bw < (bx1 - bx0) / 3) bw = (bx1 - bx0) / 3;
        b.x0 = (bx0 + bx1 - bw) / 2; b.y0 = by; b.x1 = (bx0 + bx1 + bw) / 2; b.y1 = by + m->btnH; mui_button(&b, l1, n1, 1, 0xF2);
    }
    if (ph_out) *ph_out = ph;
}

/* popup chung: nền tối thêm + hộp giữa màn + nuốt chạm ngoài. Hit: modal 0xFE, yes 0xF0, no 0xF1, ok 0xF2 */
void mui_popup(const u8 *text, int tlen, int yes_no, const u8 *l1, int n1, const u8 *l2, int n2, mui_r *out) {
    mui_m *m = &L; int pw, ph, pw1;
    pw = m->hd ? 480 : (70 * m->W + 50) / 100;
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    u16 st[8], ln[8];
    int n = mui_wrap(m->px, text, tlen, pw - 2 * (m->pad + 2 * m->s + 4), st, ln, 8);
    mui_popup_geom(n, &pw1, &ph);
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);       /* nuốt chạm ngoài hộp: đăng ký TRƯỚC các nút (hit đăng ký sau thắng) */
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_popup_box(x0, y0, pw, text, tlen, yes_no, l1, n1, l2, n2, &ph);
    if (out) { out->x0 = x0; out->y0 = y0; out->x1 = x0 + pw; out->y1 = y0 + ph; }
}
