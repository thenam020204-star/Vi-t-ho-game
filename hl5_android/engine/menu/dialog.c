/* T16: khung hội thoại mới (cốt truyện/NPC): hộp mờ viền cam, chân dung ×q/4 trong khung cam chồng mép hộp, bảng tên, nút "Bỏ qua", mũi tên "tiếp".
 * Thiết kế: ui_new/dialog/mockup_dialog_hd.jpg; số đo: BAO-CAO-T16.md §3 (theo tỉ lệ khung logic W x H, mọi cỡ >= 400x240).
 * GIỮ NGUYÊN logic gốc: BFont::DrawTextField (xuống dòng/trang/chữ chạy), Event_Key (lật trang/skip), tiến độ [D+12]; chỉ đổi:
 *   (1) hình học ghi vào DIALOG_INFO sau SetFacePosition (hook bl 0x6f5c4): hộp x/y/w/h (+20..+26), vùng chữ x/y/w (+14,+16,+18), số dòng (+8)
 *       => mọi hàm gốc đọc cùng các trường đó (xuống dòng, IsTextFieldDrawComplete, GetTextFieldPageLen) tự dùng bề rộng mới;
 *   (2) vẽ: Event_DialogWindow (hộp+chữ+tên), Draw_Face (chân dung), EventSkipDraw (2 ảnh "#: Skip") thay bằng hàm ở đây;
 *   (3) chạm (hàng GAME của states.def, chỉ khi đang trong sự kiện): nút Bỏ qua -> KeyPressed(35), chạm khác -> KeyPressed(-5), như StateGame::TouchPressed gốc.
 * DIALOG_INFO (60 byte, EventProc+176+60*i; i = slot 0/1): +7 id tên, +8 số dòng, +9 trang, +10 tốc độ (ký tự/khung), +12 tiến độ, +14/16/18 chữ x/y/w,
 * +20/22/24/26 hộp x/y/w/h, +32 chuỗi, +36 GbmImage* chân dung, +40 phía (0 trái, 1 phải), +43 trạng thái (2 = đang nói), +45 alpha, +46 cờ vẽ, +48/50 vị trí gốc,
 * +52/54 lệch hiệu ứng, +56/58 rộng/cao ảnh. EventProc: +172 slot đang nói, +173/174 chỉ số slot, +0x75 cờ "đang trong sự kiện", +0x6e0/0x6e1 trạng thái skip. */
#include "mui.h"
#include "dlg_text.h"

extern H u32 _pCFont;
extern H int BFont_DrawTextField(u32 f, int fb, int x, int y, int w, int lines, const u8 *text, int page, int prog, int speed, int z);
extern H int BFont_IsTextFieldDrawComplete(u32 f, int w, int lines, const u8 *text, int page, int prog, int speed);
extern H int BFont_HanFontHeight(u32 f);
extern H int BFont_GetTextFieldPageLen(u32 f, int w, int lines, const u8 *text);
extern H void BFont_SetColor(u32 f, int c);
extern H void BFont_SetColorFixed(u32 f, int v);
extern H const u8 *TextMgr_GetNpcNameText(u32 tm, int id);
extern H void EventProc_Event_DialogWindow(u8 *ep, int a);
extern H void DIALOG_INFO_Draw_Face(u8 *d);
extern H void DIALOG_INFO_SetFacePosition(u8 *d, int a, int b);
extern H void StateGame_Paint(void *self);
extern H void Graphic_DrawImageBlending(void);

#define DS8(d, o) (*(s8 *)((u8 *)(d) + (o)))
#define DU8(d, o) (*(u8 *)((u8 *)(d) + (o)))
#define DS16(d, o) (*(s16 *)((u8 *)(d) + (o)))
#define DPTR(d, o) (*(u8 **)((u8 *)(d) + (o)))
#define EVENT() (*(u8 **)(gv + 5204))
#define DLG_LINES 3

static struct { int tick, skip_vis, sx0, sy0, sx1, sy1; s16 xs[704]; } dlg_S;

int dlg_on(void) {
    if (mui_flags & MUI_F_DISABLE) return 0;
    if (!mui_fb_begin()) return 0;
    if (L.W != mui_fb_w() || L.Ht != mui_fb_h()) mui_layout(&L, mui_fb_w(), mui_fb_h());
    return 1;
}

/* ---------------- hình học (tỉ lệ theo khung logic) ---------------- */
typedef struct {
    int bx, by, bw, bh;            /* hộp */
    int tx, ty, tw;                /* vùng chữ */
    int fx, fy, fw, fh, sc;        /* chân dung nguyên ảnh, hệ số nguyên sc (fw = rộng ảnh * sc); fw = 0: không có */
    int over;                      /* phần hộp bị chân dung che (30% bề rộng ảnh) */
    int plate_h, nx0, nx1, ny0;    /* bảng tên: x0 (phía trái) hoặc x1 (phía phải) + y0 */
    int b;                         /* độ dày viền cam */
} g_t;

/* Bố cục v2 (mock_dialog_v2.py): chân dung nguyên ảnh sc = clamp(82%H / cao ảnh, 1, 3), đáy chạm đáy màn, lề 8*sc; hộp vẽ trước, chân dung đè sau;
 * mép hộp phía chân dung lùi vào sau ảnh 30% bề rộng ảnh; hộp chiếm phần còn lại tới lề màn; chữ sau phần bị che + 12b, rộng = hộp - che - 24b. */
static void geom(g_t *g, int has, int side, int lines, int lp, int pw, int ph) {
    mui_m *m = &L;
    int W = m->W, Hh = m->Ht, px = m->px;
    int b = Hh >= 480 ? 2 : 1;
    g->b = b;
    int sc = 1, mg;
    if (has && pw > 0 && ph > 0) { sc = (Hh * 82 / 100) / ph; if (sc < 1) sc = 1; if (sc > 3) sc = 3; mg = 8 * sc; }
    else { has = 0; mg = 8 * m->s; }
    int bh = lines * lp + (Hh >= 480 ? 24 : 14);
    int by = Hh - bh - mg / 2;
    g->by = by; g->bh = bh; g->ty = by + (Hh >= 480 ? 12 : 7);
    g->plate_h = px + 8 * b;
    g->ny0 = by - g->plate_h + b;
    g->nx0 = g->nx1 = 0;
    if (!has) {
        g->fw = g->fh = g->fx = g->fy = 0; g->over = 0; g->sc = 1;
        g->bx = mg; g->bw = W - 2 * mg;
        g->tx = g->bx + 12 * b; g->tw = g->bw - 24 * b;
        g->nx0 = g->bx + 6 * b;
        return;
    }
    g->sc = sc; g->fw = pw * sc; g->fh = ph * sc; g->fy = Hh - g->fh;
    int over = g->fw * 30 / 100; g->over = over;
    if (side == 0) {
        g->fx = mg; g->bx = g->fx + g->fw - over; g->bw = W - g->bx - mg;
        g->tx = g->bx + over + 12 * b; g->nx0 = g->bx + over + 6 * b;
    } else {
        g->fx = W - mg - g->fw; g->bx = mg; g->bw = g->fx + over - mg;
        g->tx = g->bx + 12 * b; g->nx1 = g->bx + g->bw - over - 6 * b;
    }
    g->tw = g->bw - over - 24 * b;
}

/* ---------------- vẽ ---------------- */
static int isq(int v) { int r = 0; while ((r + 1) * (r + 1) <= v) r++; return r; }
static int inset(int rad, int d) {
    if (rad <= 0 || d >= rad) return 0;
    int tt = 2 * (rad - d) - 1, q = isq(4 * rad * rad - tt * tt), ix = rad - ((q + 1) >> 1);
    return ix < 0 ? 0 : ix;
}
static int ext(int x0, int y0, int x1, int y1, int rad, int y, int *xl, int *xr) {
    if (y < y0 || y >= y1) return 0;
    int d = y - y0, e = y1 - 1 - y;
    if (e < d) d = e;
    int i = inset(rad, d);
    *xl = x0 + i; *xr = x1 - i;
    return *xl < *xr;
}
static void ring(int x0, int y0, int x1, int y1, int rad, int a0, int b0, int a1, int b1, int rad2, u32 c) {
    for (int y = y0; y < y1; y++) {
        int xl, xr, il, ir;
        if (!ext(x0, y0, x1, y1, rad, y, &xl, &xr)) continue;
        if (ext(a0, b0, a1, b1, rad2, y, &il, &ir)) {
            if (il < xl) il = xl;
            if (ir > xr) ir = xr;
            mui_fill(xl, y, il, y + 1, c); mui_fill(ir, y, xr, y + 1, c);
        } else mui_fill(xl, y, xr, y + 1, c);
    }
}
/* hộp nền tối bán trong + viền cam b px */
static void panel(int x0, int y0, int x1, int y1, int b, u32 fill, int alpha) {
    mui_fill_a(x0, y0, x1, y1, fill, alpha);
    mui_fill(x0, y0, x1, y0 + b, C_ORANGE); mui_fill(x0, y1 - b, x1, y1, C_ORANGE);
    mui_fill(x0, y0, x0 + b, y1, C_ORANGE); mui_fill(x1 - b, y0, x1, y1, C_ORANGE);
}
static void tri_down(int cx, int y, int w, int h, u32 c) {
    for (int k = 0; k < h; k++) { int ww = w * (h - k) / h; mui_fill(cx - ww / 2, y + k, cx - ww / 2 + ww, y + k + 1, c); }
}
static void tri_right(int x, int cy, int w, int h, u32 c) {
    for (int k = 0; k < w; k++) { int hh = h * (w - k) / w; mui_fill(x + k, cy - hh / 2, x + k + 1, cy - hh / 2 + hh, c); }
}
static void plate(int x0, int y0, int x1, int y1, int b, const u8 *s, int len, int px, u32 col) {
    panel(x0, y0, x1, y1, b, C_PANEL, 235);
    mui_text((x0 + x1) / 2, y0 + (y1 - y0 - px) / 2, s, len, px, col, TF_CENTER | TF_SHADOW);
}

/* chân dung GbmImage: cắt (sx0,sy0,sw,sh) -> (dw,dh) NEAREST, lật ngang, alpha 0..255, kẹp (cx0,cy0,cx1,cy1) */
static void blit(const u8 *gbm, int dx0, int dy0, int dw, int dh, int sx0, int sy0, int sw, int sh, int flip, int alpha,
                 int cx0, int cy0, int cx1, int cy1) {
    if (!gbm || dw <= 0 || dh <= 0) return;
    int w = *(const u16 *)(gbm + 2), h = *(const u16 *)(gbm + 4);
    const u16 *pal = *(const u16 *const *)(gbm + 8);
    const u8 *pix = *(const u8 *const *)(gbm + 0xc);
    if (!pal || !pix || dw > 700) return;
    int nib = (gbm[0] & 15) == 4, stride = nib ? (w + 1) >> 1 : w;
    u32 skip = SKIP_COLOR & 0xffff;
    int a5 = (alpha + 4) >> 3;
    for (int x = 0; x < dw; x++) {
        int sx = ((2 * x + 1) * sw) / (2 * dw);
        if (flip) sx = sw - 1 - sx;
        dlg_S.xs[x] = (s16)(sx0 + sx);
    }
    for (int y = 0; y < dh; y++) {
        int yy = dy0 + y;
        if (yy < cy0 || yy >= cy1) continue;
        int sy = sy0 + ((2 * y + 1) * sh) / (2 * dh);
        if (sy < 0 || sy >= h) continue;
        const u8 *row = pix + sy * stride;
        for (int x = 0; x < dw; x++) {
            int xx = dx0 + x;
            if (xx < cx0 || xx >= cx1) continue;
            int sx = dlg_S.xs[x];
            if (sx < 0 || sx >= w) continue;
            int idx = nib ? ((sx & 1) ? row[sx >> 1] & 15 : row[sx >> 1] >> 4) : row[sx];
            u32 c = pal[idx];
            if (c == skip) continue;
            if (a5 < 32) {
                u32 d = mui_getpx(xx, yy), M = 0x07E0F81Fu;
                u32 v = (((c | (c << 16)) & M) * (u32)a5 + ((d | (d << 16)) & M) * (u32)(32 - a5)) >> 5;
                v &= M; c = (v | (v >> 16)) & 0xffff;
            }
            mui_put16(xx, yy, c);
        }
    }
}

static void portrait(const g_t *g, u8 *D, int clip_y1) {
    u8 *gbm = DPTR(D, 0x24);
    if (!gbm || g->iw <= 0) return;
    int t = g->t, in[4];
    panel(g->fx0, g->fy0, g->fx1, g->fy1, L.rad, t, C_NAVY, 238, in);
    int ox = DS16(D, 52), oy = DS16(D, 54), q = g->iw * 4 / (g->sw ? g->sw : 1);
    int y1 = in[3] < clip_y1 ? in[3] : clip_y1;
    blit(gbm, in[0] + ox * q / 4, in[1] + oy * q / 4, g->iw, g->ih, g->sx0, g->sy0, g->sw, g->sh, DU8(D, 46) == 8, DU8(D, 45), in[0], in[1], in[2], y1);
}

/* HD (H >= 480): chữ thoại luôn mode 0 (18 px thường): bỏ cỡ nén/10 px mà T4 gài sẵn để vừa hộp cũ hẹp; cờ vn_state.force0 (đã duyệt ở T17c0) phải bật CẢ khi vẽ lẫn khi
 * Event_Key hỏi IsTextFieldDrawComplete/GetTextFieldPageLen (hook hooks.def) để xuống dòng/trang đo cùng một cách. Ở màn nhỏ giữ nguyên cỡ nén (vừa 3 dòng). */
static int f0_on(void) { return L.hd && !(mui_flags & MUI_F_DISABLE); }
int dlg_iscomplete(u32 f, int w, int lines, const u8 *text, int page, int prog, int speed) {
    int old = vn_state->force0, on = f0_on();
    if (on) vn_state->force0 = 1;
    int r = BFont_IsTextFieldDrawComplete(f, w, lines, text, page, prog, speed);
    vn_state->force0 = old;
    return r;
}
int dlg_pagelen(u32 f, int w, int lines, const u8 *text) {
    int old = vn_state->force0, on = f0_on();
    if (on) vn_state->force0 = 1;
    int r = BFont_GetTextFieldPageLen(f, w, lines, text);
    vn_state->force0 = old;
    return r;
}

/* ---------------- các điểm móc ---------------- */
void dlg_setface(u8 *D, int a, int b) {
    DIALOG_INFO_SetFacePosition(D, a, b);
    if (!dlg_on()) return;
    g_t g;
    int lp = (s8)BFont_HanFontHeight(_pCFont) + 2;
    geom(&g, DPTR(D, 0x24) != NULL, DS8(D, 0x28), DLG_LINES, lp, DS16(D, 56), DS16(D, 58));
    DS8(D, 8) = DLG_LINES;
    DS16(D, 14) = g.tx; DS16(D, 16) = g.ty; DS16(D, 18) = g.tw;
    DS16(D, 20) = g.bx; DS16(D, 22) = g.by; DS16(D, 24) = g.bw; DS16(D, 26) = g.bh;
}

void dlg_window(u8 *ep) {
    if (!dlg_on()) { EventProc_Event_DialogWindow(ep, 0); return; }
    u8 *D = ep + 176 + 60 * DS8(ep, 172);
    u32 f = _pCFont;
    int fb = Graphic_GetFB(_lcdG), lines = DS8(D, 8), lp = (s8)BFont_HanFontHeight(f) + 2;
    g_t g;
    geom(&g, DPTR(D, 0x24) != NULL, DS8(D, 0x28), lines, lp, DS16(D, 56), DS16(D, 58));
    dlg_S.tick++;
    mui_m *m = &L;
    panel(g.bx, g.by, g.bx + g.bw, g.by + g.bh, m->rad, g.t, C_NAVY, 218, NULL);
    portrait(&g, D, m->Ht);
    int id = DU8(D, 7);
    if ((u8)(id - 1) <= 253) {
        const u8 *nm = TextMgr_GetNpcNameText(_pTextMgr, id);
        if (nm && nm[0]) {
            int w = mui_sw(m->px, nm, -1) + 4 * m->pad;
            if (w < 6 * m->px) w = 6 * m->px;
            int x0 = g.nx0 ? g.nx0 : g.nx1 - w;
            plate(x0, g.ny0, x0 + w, g.ny0 + g.plate_h, m->rad, g.t, nm, -1, m->px, C_GOLD);
        }
    }
    const u8 *text = DPTR(D, 32);
    int page = DS8(D, 9), speed = DS16(D, 10);
    int old0 = vn_state->force0;
    if (f0_on()) vn_state->force0 = 1;
    BFont_SetColor(f, 0x404040); BFont_SetColorFixed(f, 1);
    BFont_DrawTextField(f, fb, DS16(D, 14), DS16(D, 16), DS16(D, 18), lines, text, page, 30000, speed, 0);
    BFont_SetColorFixed(f, 0); BFont_SetColor(f, 0xFFFFFF);
    int r = BFont_DrawTextField(f, fb, DS16(D, 14), DS16(D, 16), DS16(D, 18), lines, text, page, DS16(D, 12), speed, 0);
    if (DS16(D, 12) <= 29999) DS16(D, 12) = (s16)(DS16(D, 12) + r);
    /* mũi tên "tiếp": khi trang đã hiện đủ (nhấp nháy) */
    int done = BFont_IsTextFieldDrawComplete(f, DS16(D, 18), lines, text, page, DS16(D, 12), speed);
    vn_state->force0 = old0;
    if (done && ((dlg_S.tick >> 3) & 1) == 0) {
        int aw = m->px * 2 / 3 + 2, ah = aw * 3 / 4 + 1;
        tri_down(g.tx + g.tw - aw / 2 - 2, g.by + g.bh - m->pad - g.t - ah - 1, aw, ah, C_ORANGE_HI);
    }
}

void dlg_face(u8 *D) {
    if (!dlg_on()) { DIALOG_INFO_Draw_Face(D); return; }
    u8 *ep = EVENT();
    if (!ep || (u8)(DS8(D, 43) - 1) > 6) return;
    int slot = (int)(D - (ep + 176)) / 60;
    u8 *C = ep + 176 + 60 * DS8(ep, 172);
    if (slot == DS8(ep, 172) && DS8(D, 43) == 2) return;           /* vẽ cùng hộp ở dlg_window (chồng lên hộp) */
    int lines = DS8(D, 8) > 0 ? DS8(D, 8) : DLG_LINES, lp = (s8)BFont_HanFontHeight(_pCFont) + 2;
    g_t g;
    geom(&g, DPTR(D, 0x24) != NULL, DS8(D, 0x28), lines, lp, DS16(D, 56), DS16(D, 58));
    portrait(&g, D, DS8(C, 43) == 2 ? g.by : L.Ht);                 /* đang có hộp: không che chữ của người đang nói */
}

/* "#: Skip" gốc = 2 ảnh vẽ ở (0,0): ảnh thứ nhất -> nút "Bỏ qua ▶▶" mới, ảnh thứ hai -> bỏ.
 * (Trampoline asm: cờ tắt thì nhảy thẳng tới Graphic::DrawImageBlending gốc với nguyên tham số.) */
__asm__(
".text\n.arm\n"
".global dlg_skip_a\ndlg_skip_a:\n"
"  push {r0-r3, r4, lr}\n  bl dlg_on\n  cmp r0, #0\n  pop {r0-r3, r4, lr}\n  bne dlg_skip_a_new\n  b Graphic_DrawImageBlending\n"
".global dlg_skip_b\ndlg_skip_b:\n"
"  push {r0-r3, r4, lr}\n  bl dlg_on\n  cmp r0, #0\n  pop {r0-r3, r4, lr}\n  bxne lr\n  b Graphic_DrawImageBlending\n"
);
H void dlg_skip_a_new(void);
void dlg_skip_a_new(void) {
    u8 *ep = EVENT();
    mui_m *m = &L;
    int px = m->px, pad = m->pad, t = m->s + 1;
    int w = mui_sw(px, T_SKIP, T_SKIP_N) + 4 * pad + px, h = (px + 2 * (m->bt + 2)) * 7 / 8, x1, y0;
    int lp = (s8)BFont_HanFontHeight(_pCFont) + 2;
    u8 *C = ep ? ep + 176 + 60 * DS8(ep, 172) : NULL;
    if (C && DS8(C, 43) == 2) {          /* gắn mép trên hộp, phía đối diện chân dung (phải; chân dung bên phải -> trái) */
        g_t g;
        geom(&g, DPTR(C, 0x24) != NULL, DS8(C, 0x28), DS8(C, 8) > 0 ? DS8(C, 8) : DLG_LINES, lp, DS16(C, 56), DS16(C, 58));
        int ins = pad + t + 2;
        if (DPTR(C, 0x24) && DS8(C, 0x28) == 1) x1 = g.bx + ins + w; else x1 = g.bx + g.bw - ins;
        y0 = g.by - h * 5 / 8;
    } else { x1 = m->W - m->mg; y0 = m->mg; }
    int x0 = x1 - w, y1 = y0 + h, in[4];
    panel(x0, y0, x1, y1, m->rad, t > 2 ? t - 1 : t, C_PANEL, 235, in);
    mui_clip(x0 + 1, y0 + 1, x1 - 1, y1 - 1);
    int tw = mui_sw(px, T_SKIP, T_SKIP_N), ah = px * 3 / 5 + 1, aw = ah * 3 / 4 + 1;
    int cx = x0 + (w - (tw + px / 3 + 2 * aw + 1)) / 2;
    mui_text(cx, y0 + (h - px) / 2, T_SKIP, T_SKIP_N, px, C_GOLD, TF_SHADOW);
    int ax = cx + tw + px / 3;
    tri_right(ax, y0 + h / 2, aw, ah, C_ORANGE_HI); tri_right(ax + aw + 1, y0 + h / 2, aw, ah, C_ORANGE_HI);
    mui_clip_reset();
    int sl = pad / 2 + 2;
    dlg_S.skip_vis = 1; dlg_S.sx0 = x0 - sl; dlg_S.sy0 = y0 - sl; dlg_S.sx1 = x1 + sl; dlg_S.sy1 = y1 + sl;
}

/* ---------------- hàng GAME (states.def): đang trong sự kiện ---------------- */
void dlg_paint(void *self) {
    dlg_S.skip_vis = 0;
    StateGame_Paint(self);
}
int dlg_touch(void *self, int x, int y) {
    int sk = dlg_S.skip_vis && x >= dlg_S.sx0 && x < dlg_S.sx1 && y >= dlg_S.sy0 && y < dlg_S.sy1;
    mui_key(self, MUI_CLS_GAME, sk ? 35 : -5);
    gv[0x127] = 0;                      /* như cuối StateGame::TouchPressed gốc (0xde5a0) */
    return 0;
}
int dlg_move(void *self, int x, int y) { (void)self; (void)x; (void)y; return 0; }   /* gốc: TouchMoved thoát ngay khi [EventProc+0x75] != 0 */
