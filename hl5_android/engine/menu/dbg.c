/* CHỈ bản harness (-DMUI_DEBUG): trang thử widget = cùng bố cục ui_new/menu/widgets_400x240.png / widgets_1200x540.png (mock_menu.sheet_widgets).
 * Harness đặt mui_dbg_mw/mh = khung mockup (400x240 hoặc 1200x540: quyết định cỡ widget qua mui_layout), chạy guest ở cỡ ảnh trang
 * (800x368 / 1500x604) và bật mui_flags|=MUI_F_DBG khi state InGameMenu đang mở. Chuỗi: dbg_text.h (gen_dbg_text.py). */
#ifdef MUI_DEBUG
#include "mui.h"
#include "dbg_text.h"

volatile int mui_dbg_mw, mui_dbg_mh, mui_dbg_hit, mui_dbg_cnt;       /* bss (không .data): mw/mh = 0 -> 400x240 */

extern H int Graphic_DrawImage(u32 g, u32 fb, int x, int y, const u8 *gbm, int flag);

static const u8 *icon(int i) {                     /* GbmImage của icon vật phẩm i nếu hợp lệ */
    const u8 *p = ITEM_ICON(i);
    int w = *(const u16 *)(p + 2), h = *(const u16 *)(p + 4);
    return (w > 0 && w <= 64 && h > 0 && h <= 64) ? p : NULL;
}
/* chọn chuỗi bằng switch: mảng con trỏ khởi tạo sẽ sinh R_ARM_ABS32 (địa chỉ tuyệt đối link, sai khi nạp ở base khác) */
static const u8 *S1(int i) { switch (i) { case 0: return T_B0; case 1: return T_YES; case 2: return T_NO; default: return T_B3; } }
static int N1(int i) { switch (i) { case 0: return T_B0_N; case 1: return T_YES_N; case 2: return T_NO_N; default: return T_B3_N; } }
static const u8 *S2(int i) { switch (i) { case 0: return T_HP; case 1: return T_SP; default: return T_EXP; } }
static int N2(int i) { switch (i) { case 0: return T_HP_N; case 1: return T_SP_N; default: return T_EXP_N; } }
static const u8 *S4(int i) { switch (i) { case 0: return T_R0; case 1: return T_R1; case 2: return T_R2; case 3: return T_R3; default: return T_R4; } }
static int N4(int i) { switch (i) { case 0: return T_R0_N; case 1: return T_R1_N; case 2: return T_R2_N; case 3: return T_R3_N; default: return T_R4_N; } }
static const u8 LBL[8][3] = {{'1', 0}, {'2', 0}, {'3', 0}, {'4', 0}, {'5', 0}, {'6', 0}, {'7', 'a'}, {'7', 'b'}};

int dbg_touch(void *self, int x, int y) {
    mui_touch_press(self, MUI_CLS_MAIN, x, y);
    mui_dbg_hit = mui_last_id; mui_dbg_cnt++;
    return 0;
}

int dbg_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_MAIN, x, y); }

void dbg_paint(void *self) {
    (void)self;
    mui_m *m = &L;
    mui_layout(m, mui_dbg_mw ? mui_dbg_mw : 400, mui_dbg_mh ? mui_dbg_mh : 240);          /* widget dùng cỡ mockup; khung thật = cỡ trang */
    int PW = mui_fb_w(), PH = mui_fb_h(), s = m->s, u = m->hd ? 2 : 1, pad = 8 * s, lh = m->slp + 4;
    const short *tw = m->hd ? TITLEW_HD : TITLEW_SD;
    mui_fill(0, 0, PW, PH, RGB(30, 34, 46));
    /* kích thước từng khối (như sheet_widgets): w, h */
    int bw[8], bh[8], pw1, ph1, pw2, ph2;
    u16 st[8], ln[8];
    int popw = m->hd ? 480 : (70 * m->W + 50) / 100;
    if (popw > m->W - 2 * m->mg) popw = m->W - 2 * m->mg;
    int n1 = mui_wrap(m->px, T_POP, T_POP_N, popw - 2 * (m->pad + 2 * s + 4), st, ln, 8);
    mui_popup_geom(n1, &pw1, &ph1); pw1 = popw;
    pw2 = 200 * u;
    int n2 = mui_wrap(m->px, T_POP2, T_POP2_N, pw2 - 2 * (m->pad + 2 * s + 4), st, ln, 8);
    mui_popup_geom(n2, &(int){0}, &ph2);
    int bwid = mui_sw(m->px, T_NO, T_NO_N) + 6 * m->pad;
    int lw5 = 150 * u, dh = 4 * m->lp + 10;
    bw[0] = 180 * u + 2; bh[0] = m->titleH + m->tabH + 36 * u;
    bw[1] = 2 * bwid + m->pad; bh[1] = 2 * m->btnH + m->pad;
    bw[2] = 130 * u; bh[2] = 3 * m->barH + 2 * m->pad;
    bw[3] = 6 * (m->cell + m->cg + 2) + 2; bh[3] = m->cell + 2;
    bw[4] = lw5 + m->sbw + 4; bh[4] = 5 * (m->rowH + 1);
    bw[5] = 230 * u; bh[5] = dh;
    bw[6] = pw1; bh[6] = ph1; bw[7] = pw2; bh[7] = ph2;
    int x = pad, y = pad, rowh = 0;
    for (int b = 0; b < 8; b++) {
        int w = bw[b] > tw[b] ? bw[b] : tw[b], h = bh[b] + lh;
        if (x + w > PW - pad && x > pad) { x = pad; y += rowh + pad; rowh = 0; }
        mui_text(x, y, LBL[b], LBL[b][1] ? 2 : 1, m->spx, RGB(120, 200, 255), 0);
        int ox = x, oy = y + lh;
        switch (b) {
        case 0: { mui_r r = {ox, oy, ox + bw[0], oy + bh[0]}, body;
            mui_window(&r, T_TITLE, T_TITLE_N, 9, NULL, 1, &body); break; }
        case 1: {
            static const int K[4] = {0, 1, 2, 3};
            for (int i = 0; i < 4; i++) {
                mui_r r; r.x0 = ox + (i % 2) * (bwid + m->pad); r.y0 = oy + (i / 2) * (m->btnH + m->pad);
                r.x1 = r.x0 + bwid; r.y1 = r.y0 + m->btnH;
                mui_button(&r, S1(i), N1(i), K[i], 0x20 + i);
            }
            break; }
        case 2: {
            static const int V[3] = {1842, 391, 7120}, MX[3] = {2176, 520, 9800}; const u32 CL[3] = {C_HP, C_SP, C_EXP};
            for (int i = 0; i < 3; i++) mui_bar(ox, oy + i * (m->barH + m->pad), 130 * u, S2(i), N2(i), V[i], MX[i], CL[i]);
            break; }
        case 3: {
            static const int IC[6] = {-1, 206, 226, 203, 207, 228}, CT[6] = {0, 0, 5, 0, 0, 99};
            static const int RC[6] = {-1, -1, -1, RGB(255, 150, 55), RGB(205, 130, 255), -1}; static const u8 EQ[6] = {0, 0, 0, 1, 0, 0}, SE[6] = {0, 0, 0, 1, 0, 0};
            for (int i = 0; i < 6; i++)
                mui_cell(ox + i * (m->cell + m->cg + 2) + 1, oy + 1, IC[i] < 0 ? NULL : icon(IC[i]), CT[i], RC[i], EQ[i], SE[i], 0x30 + i);
            /* thử cầu chạm: ô đầu = HK_OK (ghi [self+0x14]=0 rồi KeyPressed(-5) gốc của StateMainMenu: màn tiêu đề -> menu chính) */
            mui_hit(0x3F, ox + 1, oy + 1, ox + 1 + m->cell, oy + 1 + m->cell, HK_OK, 0x14, 0);
            break; }
        case 4: {
            static const u8 VAL[] = {'1', ',', '2', '0', '0'};
            for (int i = 0; i < 5; i++) {
                mui_r r = {ox, oy + i * (m->rowH + 1), ox + lw5, oy + i * (m->rowH + 1) + m->rowH};
                mui_row(&r, S4(i), N4(i), VAL, (i % 2 == 0) ? 5 : 0, i == 1, 0x40 + i);
            }
            mui_scrollbar(ox + lw5 + 2, oy, oy + 5 * (m->rowH + 1) - 1, 1, 12, 5);
            break; }
        case 5: {
            mui_r r = {ox, oy, ox + bw[5], oy + dh};
            int yy = mui_descbox(&r, T_NAME, T_NAME_N, -1);
            int p = m->pad / 2 + 1, ix0 = r.x0 + 2 + m->bt, ix1 = r.x1 - 2 - m->bt;
            u16 s2[12], l2[12];
            int nl = mui_wrap(m->px, T_DESC, T_DESC_N, ix1 - ix0 - 2 * p - m->sbw - 2, s2, l2, 12);
            int view = (r.y1 - 3 - yy) / m->lp;
            if (view < 1) view = 1;
            mui_sc[0].total = nl; mui_sc[0].view = view;
            if (mui_sc[0].pos > nl - view) mui_sc[0].pos = nl > view ? nl - view : 0;
            int pos = mui_sc[0].pos;
            mui_clip(ix0, yy, ix1, r.y1 - 3);
            for (int i = 0; i + pos < nl && i < view; i++) mui_text_skip(ix0 + p, yy + i * m->lp, T_DESC + s2[i + pos], l2[i + pos], m->px, C_WHITE, 0);
            mui_clip_reset();
            if (nl > view) mui_scrollbar(ix1 - m->sbw - 1, yy, r.y1 - 3, pos, nl, view);
            mui_hit(0x60, ix0, yy, ix1, r.y1 - 3, HK_DRAG, 0, m->lp);       /* thử kéo dọc: scroller 0, lp px / dòng */
            break; }
        case 6: { int ph; mui_popup_box(ox, oy, pw1, T_POP, T_POP_N, 1, T_YES, T_YES_N, T_NO, T_NO_N, &ph); break; }
        case 7: { int ph; mui_popup_box(ox, oy, pw2, T_POP2, T_POP2_N, 0, T_OK, T_OK_N, NULL, 0, &ph); break; }
        }
        x += w + pad * 2; if (h > rowh) rowh = h;
    }
    /* so sánh pixel-đúng sprite: guest DrawImage (trái) vs mui_gbm x1 (phải), góc dưới phải */
    const u8 *g = icon(203);
    if (g) {
        u32 gfx = _lcdG;
        Graphic_DrawImage(gfx, *(u32 *)gfx, PW - 60, PH - 28, g, 0);
        mui_gbm(g, PW - 30, PH - 28, 1, -1);
    }
}
#endif
