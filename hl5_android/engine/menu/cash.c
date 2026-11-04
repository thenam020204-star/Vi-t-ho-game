/* L1/T17c6: Cash shop (StateCashShop state 0, 4x4 + cuộn) theo ui_new/menu/LAYOUT.md §12 (cash_shop_*, hit_rects.json: close, grid_scroll, cell0..15).
 * Kiến trúc A: Paint + Touch mới; KeyPressed/Process/SetPopupState gốc. Popup (xác nhận 91+304, đã nhận 93, túi đầy 92, khu vực cấm 331) = StatePopup (popup.c, loại 2/1).
 * Trường: +0xe ô trong cửa sổ (0..15), +0xf chỉ số tuyệt đối; hàng đầu cửa sổ = (+0xf - +0xe)/4; data = *(this+0x14): u16 [+2] = số mục, mảng 24 B tại *(data+4):
 *   +2 u16 icon, +4 ptr mô tả, +8 id (loại 18), +9 số lượng, +12 ptr tên, +16/+18/+20 giá (offline: 0, nhãn "Miễn phí"). Mua: t7.c (cấp vật phẩm không mạng). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define H_CELL 0x10
#define H_DET 0x40
#define H_BUY 0x41
#define H_SCR 0x42
extern H const u8 t7_free[];                          /* "Miễn phí" (t7.c) */
#define FREE_TXT t7_free

static int nitems(void *self) { return *(u16 *)(*(u8 **)((u8 *)self + 0x14) + 2); }
static u8 *rec(void *self, int i) { return *(u8 **)(*(u8 **)((u8 *)self + 0x14) + 4) + 24 * i; }
static int top_row(void *self) { int t = (S8(self, 0xf) - S8(self, 0xe)) / 4; return t < 0 ? 0 : t; }

void cash_paint(void *self) {
    mui_m *m = &L; u32 g = _lcdG; int n = nitems(self), win = S8(self, 0xe), ab = S8(self, 0xf), top = top_row(self);
    Graphic_CopyFrameBuffer(g, Graphic_GetFB(g), Graphic_GetFBOff(g));
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 150);
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(245), -1, 0, NULL, 0, &body);
    int x0 = m->wx + m->pad, y0 = m->wy + m->titleH + m->pad, x1 = m->wx + m->winW - m->pad, y1 = m->wy + m->winH - m->pad;
    int gx = x0 + m->bt + 2, gy = y0 + m->bt + 2, rows = (n + 3) / 4;
    mui_sc[0].pos = top; mui_sc[0].total = rows; mui_sc[0].view = 4;
    mui_hit(H_SCR, gx, gy, gx + m->gridW + 3 + m->sbw, gy + m->gridW, HK_DRAG, 0, m->pitch);
    for (int r = 0; r < 4; r++)
        for (int c = 0; c < 4; c++) {
            int k = 4 * r + c, a = (top + r) * 4 + c; if (a >= n) continue;
            mui_cell_i(gx + c * m->pitch, gy + r * m->pitch, *(u16 *)(rec(self, a) + 2), rec(self, a)[9], -1, 0, k == win, H_CELL + k);
        }
    mui_scrollbar(gx + m->gridW + 3, gy, gy + m->gridW, top, rows < 4 ? 4 : rows, 4);
    /* cột chi tiết */
    int dx0 = gx + m->gridW + 3 + m->sbw + m->gap - 1, by = y1 - m->btnH, pr = m->lp + 4, py = by - m->gap - pr;
    mui_r box = {dx0, y0, x1, py - m->gap};
    if (ab < 0 || ab >= n) ab = 0;
    u8 *rc = rec(self, ab); u8 meta[20]; int mn = 0;
    if (rc[9] > 1) { meta[mn++] = 'x'; mn += mui_itoa(rc[9], meta + mn); }
    const u8 *nm = *(const u8 **)(rc + 4), *ds = *(const u8 **)(rc + 12);   /* +4 tên, +12 mô tả */
    int y = mui_descbox2(&box, nm ? nm : (const u8 *)"", -1, -1, meta, mn);
    mui_hit(H_DET, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
    if (ds && *ds) {
        int p = m->pad / 2 + 1, ix0 = box.x0 + 3, ix1 = box.x1 - 3, tw = (ix1 - ix0) - 2 * p - 2, view = box.y1 - 3 - y;
        u16 st[24], ln[24]; int nl = mui_wrap(m->px, ds, -1, tw, st, ln, 24);
        mui_clip(ix0, y, ix1, box.y1 - 3);
        for (int i = 0; i < nl; i++) { int yy = y + i * m->lp; if (yy + m->lp <= y + view + m->lp) mui_text_line(ix0 + p, yy, ds, st[i], ln[i], m->px, C_WHITE, 0); }
        mui_clip_reset();
    }
    mui_frame(dx0, py, x1, py + pr, m->rad, 0, 1, RGB(10, 16, 26), 235, NULL);
    mui_text(x1 - m->pad, py + (pr - m->px) / 2, FREE_TXT, -1, m->px, C_GREEN, TF_RIGHT | TF_SHADOW);
    mui_r b = {dx0, by, x1, by + m->btnH};
    int bn; const u8 *bt = mui_hint(CT(261), &bn);
    mui_button(&b, bt, bn, 1, 0); mui_hit(H_BUY, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}
static void set_top(void *self, int nt) {
    int n = nitems(self), rows = (n + 3) / 4, win = S8(self, 0xe);
    if (nt > rows - 4) nt = rows - 4;
    if (nt < 0) nt = 0;
    int a = nt * 4 + win; if (a >= n) { a = n - 1; win = a - nt * 4; if (win < 0) win = 0; }
    S8(self, 0xf) = a; S8(self, 0xe) = win;
}
int cash_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_CASH, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id >= H_CELL && id < H_CELL + 16) {
        int w = id - H_CELL, a = top_row(self) * 4 + w;
        mui_drag = 0; mui_sc[0].start_pos = mui_sc[0].pos; mui_sc[0].start_y = y; mui_sc[0].step = L.pitch;   /* kéo từ ô cũng cuộn */
        if (a >= nitems(self)) return 0;
        if (w == S8(self, 0xe)) mui_key(self, MUI_CLS_CASH, -5);       /* ô đang chọn: chạm lần 2 = OK gốc */
        else { S8(self, 0xe) = w; S8(self, 0xf) = a; }
    } else if (id == H_BUY) mui_key(self, MUI_CLS_CASH, -5);
    return 0;
}
int cash_move(void *self, int x, int y) {
    int r = mui_touch_move(self, MUI_CLS_CASH, x, y);
    if (mui_drag == 0) set_top(self, mui_sc[0].pos);
    return r;
}
