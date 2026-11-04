/* L1b/T17c8: StateMap (bản đồ lớn state 0 / bản đồ khu vực state 1) theo ui_new/menu/LAYOUT.md (ban_do_lon_*, ban_do_khu_vuc_*, hit_rects.json: close, toggle, minimap).
 * Kiến trúc A: Paint + Touch mới; KeyPressed/Process/SetMapMode gốc (phím '#' = 35 đổi chế độ, '*' = 42 bật/tắt Minimap, CLR = đóng). Chạm = đúng phím gốc (HK_KEY).
 * Trường (đọc từ mã máy, BAO-CAO-L1 mục 11): +0xc chế độ (0 bản đồ lớn / 1 khu vực), +0xd tham số ctor (1 = chỉ vẽ ảnh bản đồ: giữ đường gốc), +0xe/+0x10 s16 camera,
 *   +0x1a s8 khu vực người chơi đang ở (0..7), +0x1b s8 con trỏ khu vực (0..7, phím trái/phải đổi), +0x46/+0x48 s16 = 480x320 kích thước ảnh thế giới (SetMapMode),
 *   ảnh thế giới = 26 GbmImage 24 B tại +76 (0,1 = dải tiêu đề "THE ORBIS ISLANDS"; 2..25 = lưới 6 cột x 4 hàng ô 80x80, DrawWorldMap 0x123618),
 *   bảng 8 điểm khu vực (SetMapMode(0), stride 8 tại +0x2e8 + 8*i): s16 x @+0, s16 y @+2 (toạ độ trong ảnh 480x320, gốc ở góc trên trái ô hàng 0), s8 bề rộng nhãn @-1; nhãn = InGameText(33+i).
 *   Cờ Minimap = s8 [*(gv+5200)+4224] (0/1; '*' đảo): nhãn gốc InGameText(151 - cờ) (cờ 1 -> 150 "Bật Minimap"; cờ 0 gốc trỏ 151 = câu chào, lỗi gốc: dùng 149 "Tắt Minimap").
 * Chế độ khu vực: ảnh thế giới làm tối (alpha 204 như gốc) + DrawZone gốc (các mảnh sprite 67x37 vẽ ở tỉ lệ 1:1, cắt vào vùng bản đồ). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define S16(self, o) (*(s16 *)((u8 *)(self) + (o)))
#define H_STAR 0x30
#define H_HASH 0x31
extern H void StateMap_DrawZone(void *self);

typedef struct { const u16 *pal; const u8 *pix; int stride, nib, w, h; } mtile;

/* ảnh thế giới 480x320 phóng NEAREST vào (x0,y0,dw,dh), cắt theo clip hiện hành */
static void world_blit(void *self, int x0, int y0, int dw, int dh) {
    mtile T[24]; int WW = S16(self, 70), WH = S16(self, 72);
    if (WW <= 0 || WH <= 0 || dw <= 0 || dh <= 0) return;
    for (int i = 0; i < 24; i++) {
        const u8 *g = (const u8 *)self + 76 + 24 * (2 + i);
        T[i].w = *(const u16 *)(g + 2); T[i].h = *(const u16 *)(g + 4);
        T[i].pal = *(const u16 *const *)(g + 8); T[i].pix = *(const u8 *const *)(g + 0xc);
        T[i].nib = (g[0] & 15) == 4; T[i].stride = T[i].nib ? (T[i].w + 1) >> 1 : T[i].w;
    }
    int fw = mui_fb_w(), fh = mui_fb_h(); u32 skip = SKIP_COLOR & 0xffff;
    for (int dy = 0; dy < dh; dy++) {
        int yy = y0 + dy; if (yy < 0 || yy >= fh) continue;
        int sy = dy * WH / dh, row = sy / 80, ly = sy - row * 80; if (row > 3) row = 3;
        u16 *dst = 0;
        for (int dx = 0; dx < dw; dx++) {
            int xx = x0 + dx; if (xx < 0 || xx >= fw) continue;
            int sx = dx * WW / dw, col = sx / 80, lx = sx - col * 80; if (col > 5) col = 5;
            const mtile *t = &T[row * 6 + col];
            if (!t->pal || !t->pix || lx >= t->w || ly >= t->h) continue;
            const u8 *rw = t->pix + ly * t->stride;
            int idx = t->nib ? ((lx & 1) ? rw[lx >> 1] & 15 : rw[lx >> 1] >> 4) : rw[lx];
            u32 c = t->pal[idx]; if (c == skip) continue;
            (void)dst; mui_put16(xx, yy, c);
        }
    }
}

static void red_arrow(int cx, int by, int s) {          /* tam giác đỏ chỉ xuống, đỉnh ở (cx, by) */
    int hgt = 6 * s;
    for (int j = 0; j < hgt; j++) {
        int half = 3 * s * (hgt - j) / hgt;
        mui_fill(cx - half - 1, by - hgt + j, cx + half + 2, by - hgt + j + 1, C_INK);
    }
    for (int j = 1; j < hgt; j++) {
        int half = 3 * s * (hgt - j) / hgt - 1; if (half < 0) half = 0;
        mui_fill(cx - half, by - hgt + j, cx + half + 1, by - hgt + j + 1, RGB(230, 56, 48));
    }
}

void map_paint(void *self) {
    mui_m *m = &L; u32 g = _lcdG; int mode = GS(self), s = m->s;
    mui_fill(0, 0, L.W, L.Ht, C_NAVY);
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body; int tn;
    const u8 *ti = mui_hint(TextMgr_GetInGameText(_pTextMgr, mode ? 148 : 147), &tn);
    mui_window(&wr, ti, tn, 0, NULL, 0, &body);
    int x0 = body.x0 + m->pad, y0 = body.y0 + m->pad, x1 = body.x1 - m->pad, y1 = body.y1 - m->pad;   /* như content_rect của mockup */
    int by = y1 - m->btnH; mui_r rg = {x0, y0, x1, by - m->gap};
    int rw = rg.x1 - rg.x0, rh = rg.y1 - rg.y0, WW = S16(self, 70), WH = S16(self, 72);
    if (WW <= 0) WW = 480;
    if (WH <= 0) WH = 320;
    /* ảnh vừa vùng, giữ tỉ lệ, căn giữa */
    int dw = rw, dh = rw * WH / WW;
    if (dh > rh) { dh = rh; dw = rh * WW / WH; }
    int ix = rg.x0 + (rw - dw) / 2, iy = rg.y0 + (rh - dh) / 2;
    mui_fill(rg.x0 - 1, rg.y0 - 1, rg.x1 + 1, rg.y1 + 1, C_INK);
    mui_fill(rg.x0, rg.y0, rg.x1, rg.y1, RGB(8, 20, 44));
    mui_clip(rg.x0, rg.y0, rg.x1, rg.y1);
    world_blit(self, ix, iy, dw, dh);
    if (mode == 0) {
        int cur = S8(self, 0x1a), sel = S8(self, 0x1b);
        for (int i = 0; i < 8; i++) {                    /* điểm + nhãn; vẽ con trỏ sau cùng để nằm trên nhãn */
            u8 *e = (u8 *)self + 0x2e8 + 8 * i;
            int cx = ix + *(s16 *)e * dw / WW, cy = iy + *(s16 *)(e + 2) * dh / WH;
            const u8 *nm = TextMgr_GetInGameText(_pTextMgr, 33 + i);
            int tw = mui_sw(m->spx, nm, -1) + 6, lx = cx - tw / 2, ly = cy + 2 * s + 3;
            if (lx < rg.x0 + 2) lx = rg.x0 + 2;
            if (lx + tw > rg.x1 - 2) lx = rg.x1 - 2 - tw;
            mui_disc(cx, cy, 2 * s + 2, i == cur ? RGB(120, 236, 240) : RGB(200, 206, 214));
            mui_disc(cx, cy, 2 * s, C_INK);
            mui_fill_a(lx, ly, lx + tw, ly + m->spx + 2, C_INK, 190);
            mui_text(lx + 3, ly + 1, nm, -1, m->spx, i == sel ? C_GOLD : C_WHITE, 0);
        }
        if (sel >= 0 && sel < 8) { u8 *e = (u8 *)self + 0x2e8 + 8 * sel; red_arrow(ix + *(s16 *)e * dw / WW, iy + *(s16 *)(e + 2) * dh / WH - 2 * s - 3, s); }
    } else {
        mui_fill_a(rg.x0, rg.y0, rg.x1, rg.y1, C_INK, 204);
        Graphic_SetClip(g, rg.x0, rg.y0, rw, rh);
        StateMap_DrawZone(self);                         /* gốc: tiêu đề khu vực + lưới mảnh sprite + mũi tên vị trí (tỉ lệ 1:1) */
        Graphic_ResetClip(g);
    }
    mui_clip_reset();
    /* hàng nút dưới */
    int n; const u8 *t;
    if (mode == 1) {
        int hw = (x1 - x0 - m->gap) / 2; mui_r a = {x0, by, x0 + hw, by + m->btnH}, b = {x1 - hw, by, x1, by + m->btnH};
        int fl = *(s8 *)(*(u8 **)(gv + 5200) + 4224);
        t = mui_hint(TextMgr_GetInGameText(_pTextMgr, fl ? 150 : 149), &n);
        mui_button(&a, t, n, 0, 0); mui_hit(H_STAR, a.x0, a.y0, a.x1, a.y1, HK_KEY, 42, 0);
        t = mui_hint(TextMgr_GetInGameText(_pTextMgr, 147), &n);
        mui_button(&b, t, n, 1, 0); mui_hit(H_HASH, b.x0, b.y0, b.x1, b.y1, HK_KEY, 35, 0);
    } else {
        t = mui_hint(TextMgr_GetInGameText(_pTextMgr, 148), &n);
        int bw = mui_sw(m->px, t, n) + 4 * m->pad; if (bw < (x1 - x0) / 3) bw = (x1 - x0) / 3;
        mui_r b = {x1 - bw, by, x1, by + m->btnH};
        mui_button(&b, t, n, 1, 0); mui_hit(H_HASH, b.x0, b.y0, b.x1, b.y1, HK_KEY, 35, 0);
    }
}
int map_touch(void *self, int x, int y) { mui_touch_press(self, MUI_CLS_MAP, x, y); return 0; }
int map_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_MAP, x, y); }
