/* T14b: HUD chiến đấu mới (StateGame state 0, không hội thoại) + chế độ sửa bố cục nút.
 * Kiến trúc (mã máy gốc, xem BAO-CAO-T14.md): Paint GỐC của StateGame chạy bình thường, nhưng các điểm vẽ nút cảm ứng của guest bị bắt (hooks.def, cờ S.on chỉ bật trong hud_paint):
 *   - DrawTouchUi (0xdc1ac): nút tấn công (2 DrawImageBlending 0xdc29c/0xdc304), DASH (0xdc394/0xdc3f4), thanh bản đồ/cửa hàng/lưu (DrawInfoRect 0xdcf9c + DrawText 0xdcff0, 3 lần/khung);
 *   - CommonUi::DrawTouchCancelUi (0x73ce8) -> DrawImageBlending 0x73dc4: nút tạm dừng/menu;
 *   - GMenu::DrawSkillBelt (0x7f9f8, gọi ở 0x4492c trong Battle::Paint): 6 ô kỹ năng (khung DrawEffect 0x7fc84, nền 0x7fc1c/0x7fcd0) và nút đổi bộ (0x7fd34).
 *   Ô kỹ năng: chụp nền trước, để guest vẽ biểu tượng/hồi chiêu/số, so sánh -> lấy điểm đổi, khôi phục nền; sau Paint vẽ khung mới + biểu tượng phóng.
 * Chạm: nút mới -> toạ độ nút GỐC (tâm vùng chạm gốc) rồi gọi TouchPressed/Moved/Released GỐC (cùng phím/hành động); chạm trúng vùng gốc nhưng không trúng nút mới bị nuốt.
 * Sửa bố cục: nhấn giữ nút tạm dừng ~1,2 s (HOLD_N khung); kéo thả, Lưu/Mặc định/Huỷ; lưu hud_layout.dat (tâm theo phần nghìn màn hình). Không cấp phát trong vòng vẽ. */
#include "mui.h"
#include "hud_art.h"
#include "hud_text.h"

extern H void StateGame_Paint(void *self);
extern H int StateGame_TouchPressed(void *self, int x, int y, int z);
extern H int StateGame_TouchMoved(void *self, int x, int y, int z);
extern H int StateGame_TouchReleased(void *self, int x, int y, int z);
#define FW int a, int b, int c, int d, int e, int f, int g, int h, int i, int j, int k
#define FA a, b, c, d, e, f, g, h, i, j, k
extern H int GMenu_DrawSkillBelt(void *hero, int b, int c, int d, int e, int f, int g);
extern H int Graphic_DrawEffect(FW);
extern H int Graphic_DrawImageBlending(FW);
extern H int CommonUi_DrawInfoRect(FW);
extern H int BFont_DrawText(FW);
extern H int StaticUtil_FileRead(const char *name, char *buf, int len);
extern H int StaticUtil_FileWrite(const char *name, const char *buf, int len);
extern H int mui_tz;
extern H int StateGame_KeyReleased(void *self, int code);

#define NB 14
enum { B_ATT, B_S0, B_S1, B_S2, B_S3, B_S4, B_S5, B_SWAP, B_DASH, B_PAUSE, B_MAP, B_SHOP, B_SAVE, B_DPAD };
#define HOLD_N 24
#define TR 0xF81Fu
#define SLOT 30

typedef struct { s16 x0, y0, x1, y1; } rr;
static struct {
    u8 on, want[NB], nslot, ntab, edit, hold, loaded, custom, pad;
    s8 cap[4];
    u8 dmode, dmode_sv, dk, fvis;       /* dmode 0 = dpad cố định, 1 = theo ngón; dk = bit phím đang giữ (1 lên 2 xuống 4 trái 8 phải); fvis = dpad theo ngón đang hiện */
    int fcx, fcy, dd;
    int pc, t0, hx, hy, W, Ht, f16, sel, gx, gy;
    int cx[NB], cy[NB], d[NB];
    rr old[NB];
    s16 pm[NB][2];
    u16 snap[6][SLOT * SLOT], spr[6][SLOT * SLOT];
} S;

static const s16 DOX[NB] = {31, 145, 113, 81, 50, 31, 21, 190, 17, 17, 0, 0, 0, 0};
static const s16 DOY[NB] = {40, 27, 40, 61, 86, 119, 155, 27, 86, -45, 0, 0, 0, 0};
static const s16 DD[NB] = {66, 40, 40, 40, 40, 40, 40, 34, 36, 32, 28, 28, 28, 96};
static const s16 OSZ[NB] = {60, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30, 30};   /* cỡ vùng chạm gốc (để co giãn kéo) */

/* ---------------- bố cục ---------------- */
static int clampi(int v, int lo, int hi) { return v < lo ? lo : v > hi ? hi : v; }
static void lay_default(void) {
    int W = S.W, Ht = S.Ht, f = S.f16;
    for (int i = 0; i < NB; i++) {
        S.d[i] = DD[i] * f / 16;
        if (i == B_DPAD) { S.cx[i] = 78 * f / 16; S.cy[i] = Ht - 78 * f / 16; }
        else if (i >= B_MAP) { S.cx[i] = W / 2 + (i - B_SHOP) * 34 * f / 16; S.cy[i] = 17 * f / 16; }
        else {
            S.cx[i] = W - DOX[i] * 115 / 100 * f / 16;
            S.cy[i] = DOY[i] < 0 ? -DOY[i] * f / 16 : Ht - DOY[i] * 115 / 100 * f / 16;
        }
    }
}
static void lay_clamp(void) {
    for (int i = 0; i < NB; i++) {
        int r = S.d[i] / 2;
        S.cx[i] = clampi(S.cx[i], r, S.W - r); S.cy[i] = clampi(S.cy[i], r, S.Ht - r);
    }
}
static void lay_apply(void) {
    S.W = mui_fb_w(); S.Ht = mui_fb_h();
    int f = S.Ht * 16 / 270; S.f16 = f < 8 ? 8 : f;
    lay_default();
    if (S.custom) for (int i = 0; i < NB; i++) { S.cx[i] = S.pm[i][0] * S.W / 1000; S.cy[i] = S.pm[i][1] * S.Ht / 1000; }
    lay_clamp();
}
static void lay_load(void) {
    char b[72]; S.loaded = 1; S.custom = 0; S.dmode = S.dmode_sv = 0;
    int n = StaticUtil_FileRead("hud_layout.dat", b, 72);
    if (n != 6 + 4 * NB || b[0] != 'H' || b[1] != 'L' || b[2] != '5' || b[3] != 'H' || b[4] != 2) return;
    for (int i = 0; i < NB; i++) {
        int x = (u8)b[5 + 4 * i] | ((u8)b[6 + 4 * i] << 8), y = (u8)b[7 + 4 * i] | ((u8)b[8 + 4 * i] << 8);
        if (x > 1000 || y > 1000) return;
        S.pm[i][0] = (s16)x; S.pm[i][1] = (s16)y;
    }
    S.custom = 1; S.dmode = S.dmode_sv = b[5 + 4 * NB] == 1;
}
static void lay_save(void) {
    char b[72]; b[0] = 'H'; b[1] = 'L'; b[2] = '5'; b[3] = 'H'; b[4] = 2;
    for (int i = 0; i < NB; i++) {
        int x = S.cx[i] * 1000 / S.W, y = S.cy[i] * 1000 / S.Ht;
        b[5 + 4 * i] = (char)x; b[6 + 4 * i] = (char)(x >> 8); b[7 + 4 * i] = (char)y; b[8 + 4 * i] = (char)(y >> 8);
        S.pm[i][0] = (s16)x; S.pm[i][1] = (s16)y;
    }
    S.custom = 1; S.dmode_sv = S.dmode; b[5 + 4 * NB] = (char)S.dmode;
    StaticUtil_FileWrite("hud_layout.dat", b, 6 + 4 * NB);
}

/* ---------------- điểm bắt hook (chỉ khi S.on) ---------------- */
static void setold(int i, int x0, int y0, int x1, int y1) { S.old[i].x0 = (s16)x0; S.old[i].y0 = (s16)y0; S.old[i].x1 = (s16)x1; S.old[i].y1 = (s16)y1; S.want[i] = 1; }
int hud_blend_att(FW) { if (!S.on) return Graphic_DrawImageBlending(FA); setold(B_ATT, c - 30, d - 30, c + 30, d + 30); return 0; }
int hud_blend_dash(FW) { if (!S.on) return Graphic_DrawImageBlending(FA); setold(B_DASH, c - 35, d - 15, c, d + 15); return 0; }
int hud_blend_pause(FW) { if (!S.on) return Graphic_DrawImageBlending(FA); setold(B_PAUSE, c - 35, d, c, d + 30); return 0; }
int hud_rect_tab(FW) {
    if (!S.on) return CommonUi_DrawInfoRect(FA);
    if (S.ntab < 3) setold(B_MAP + S.ntab, c, d, c + 30, d + 30);
    S.ntab++; return 0;
}
int hud_text_tab(FW) { if (!S.on) return BFont_DrawText(FA); return 0; }
int hud_fx_frame(FW) {                                  /* khung 30x30 của ô i: chụp nền trước khi guest vẽ biểu tượng */
    if (!S.on) return Graphic_DrawEffect(FA);
    if (S.nslot < 6) {
        int i = S.nslot++; setold(B_S0 + i, c, d, c + SLOT, d + SLOT);
        for (int y = 0; y < SLOT; y++) for (int x = 0; x < SLOT; x++) S.snap[i][y * SLOT + x] = (u16)mui_getpx(c + x, d + y);
    }
    return 0;
}
int hud_fx_swap(FW) { if (!S.on) return Graphic_DrawEffect(FA); setold(B_SWAP, S.W - 191, S.Ht - 42, S.W - 161, S.Ht - 12); return 0; }
int hud_belt(void *hero, int b, int c, int d, int e, int f, int g) {
    if (!S.on) return GMenu_DrawSkillBelt(hero, b, c, d, e, f, g);
    S.nslot = 0;
    int r = GMenu_DrawSkillBelt(hero, b, c, d, e, f, g);
    for (int i = 0; i < S.nslot; i++) {
        int x0 = S.old[B_S0 + i].x0, y0 = S.old[B_S0 + i].y0;
        for (int y = 0; y < SLOT; y++) for (int x = 0; x < SLOT; x++) {
            u16 cur = (u16)mui_getpx(x0 + x, y0 + y), sn = S.snap[i][y * SLOT + x];
            S.spr[i][y * SLOT + x] = cur != sn ? cur : (u16)TR;
            mui_put16(x0 + x, y0 + y, sn);
        }
    }
    return r;
}

/* ---------------- vẽ ---------------- */
static void art(int id, int cx, int cy, int D, int bright) {
    const u16 *C; const u8 *A; int s;
    switch (id) {
    case 0: C = ART_EMB_C; A = ART_EMB_A; s = ART_EMB_S; break;
    case 1: C = ART_GLOW_C; A = ART_GLOW_A; s = ART_GLOW_S; break;
    case 2: C = ART_FRAME_C; A = ART_FRAME_A; s = ART_FRAME_S; break;
    case 3: C = ART_PAUSE_C; A = ART_PAUSE_A; s = ART_PAUSE_S; break;
    case 4: C = ART_SAVE_C; A = ART_SAVE_A; s = ART_SAVE_S; break;
    case 5: C = ART_SHOP_C; A = ART_SHOP_A; s = ART_SHOP_S; break;
    default: C = ART_SWAP_C; A = ART_SWAP_A; s = ART_SWAP_S; break;
    }
    if (D < 4) return;
    int x0 = cx - D / 2, y0 = cy - D / 2, step = ((s - 1) << 16) / (D - 1), sy = 0;
    for (int dy = 0; dy < D; dy++, sy += step) {
        int y = y0 + dy;
        if (y < 0 || y >= S.Ht) continue;
        int iy = sy >> 16, fy = (sy >> 8) & 255, iy1 = iy + 1 < s ? iy + 1 : iy, sx = 0;
        for (int dx = 0; dx < D; dx++, sx += step) {
            int x = x0 + dx;
            if (x < 0 || x >= S.W) continue;
            int ix = sx >> 16, fx = (sx >> 8) & 255, ix1 = ix + 1 < s ? ix + 1 : ix;
            int p00 = iy * s + ix, p01 = iy * s + ix1, p10 = iy1 * s + ix, p11 = iy1 * s + ix1;
            int w00 = (256 - fx) * (256 - fy) >> 8, w01 = fx * (256 - fy) >> 8, w10 = (256 - fx) * fy >> 8, w11 = 256 - w00 - w01 - w10;
            int a = (A[p00] * w00 + A[p01] * w01 + A[p10] * w10 + A[p11] * w11) >> 8;
            if (a <= 0) continue;
            u32 c0 = C[p00], c1 = C[p01], c2 = C[p10], c3 = C[p11];
            int r = ((c0 >> 11) * w00 + (c1 >> 11) * w01 + (c2 >> 11) * w10 + (c3 >> 11) * w11) >> 8;
            int g = (((c0 >> 5) & 63) * w00 + ((c1 >> 5) & 63) * w01 + ((c2 >> 5) & 63) * w10 + ((c3 >> 5) & 63) * w11) >> 8;
            int bl = ((c0 & 31) * w00 + (c1 & 31) * w01 + (c2 & 31) * w10 + (c3 & 31) * w11) >> 8;
            u32 dp = mui_getpx(x, y);
            int ia = 255 - a;
            int rr_ = r + (((dp >> 11) * ia) >> 8), gg = g + ((((dp >> 5) & 63) * ia) >> 8), bb = bl + (((dp & 31) * ia) >> 8);
            if (bright) { rr_ += (31 - rr_) * bright * a >> 16; gg += (63 - gg) * bright * a >> 16; bb += (31 - bb) * bright * a >> 16; }
            mui_put16(x, y, (u32)((clampi(rr_, 0, 31) << 11) | (clampi(gg, 0, 63) << 5) | clampi(bb, 0, 31)));
        }
    }
}
static void ring(int cx, int cy, int r, u32 rgb) {         /* vòng tròn mảnh (điểm giữa) */
    int x = r, y = 0, e = 1 - r;
    while (x >= y) {
        mui_pix(cx + x, cy + y, rgb); mui_pix(cx - x, cy + y, rgb); mui_pix(cx + x, cy - y, rgb); mui_pix(cx - x, cy - y, rgb);
        mui_pix(cx + y, cy + x, rgb); mui_pix(cx - y, cy + x, rgb); mui_pix(cx + y, cy - x, rgb); mui_pix(cx - y, cy - x, rgb);
        y++; if (e < 0) e += 2 * y + 1; else { x--; e += 2 * (y - x) + 1; }
    }
}
static void icon(int i, int cx, int cy, int D) {           /* biểu tượng guest (26x26 trong ô 30x30) phóng NEAREST */
    int s = D * 60 / 100;
    if (s < 8) return;
    int x0 = cx - s / 2, y0 = cy - s / 2;
    for (int dy = 0; dy < s; dy++) for (int dx = 0; dx < s; dx++) {
        u16 c = S.spr[i][(2 + dy * 26 / s) * SLOT + 2 + dx * 26 / s];
        if (c != TR) mui_put16(x0 + dx, y0 + dy, c);
    }
}
static void mapicon(int cx, int cy, int D) {
    int w = D * 46 / 100, h = D * 34 / 100, x0 = cx - w / 2, y0 = cy - h / 2, t = w / 3;
    mui_fill(x0, y0, x0 + t, y0 + h, RGB(222, 200, 138)); mui_fill(x0 + t, y0 + 1, x0 + 2 * t, y0 + h - 1, RGB(186, 160, 96)); mui_fill(x0 + 2 * t, y0, x0 + w, y0 + h, RGB(222, 200, 138));
    mui_fill(x0, y0, x0 + w, y0 + 1, RGB(70, 46, 16)); mui_fill(x0, y0 + h - 1, x0 + w, y0 + h, RGB(70, 46, 16));
    mui_fill(x0 + t, y0 + h / 3, x0 + t + 1 + D / 40, y0 + 2 * h / 3, RGB(180, 40, 30));
}
static void tri(int x0, int y0, int x1, int y1, int x2, int y2, u32 rgb) {
    int mnx = x0 < x1 ? (x0 < x2 ? x0 : x2) : (x1 < x2 ? x1 : x2), mxx = x0 > x1 ? (x0 > x2 ? x0 : x2) : (x1 > x2 ? x1 : x2);
    int mny = y0 < y1 ? (y0 < y2 ? y0 : y2) : (y1 < y2 ? y1 : y2), mxy = y0 > y1 ? (y0 > y2 ? y0 : y2) : (y1 > y2 ? y1 : y2);
    for (int y = mny; y <= mxy; y++) for (int x = mnx; x <= mxx; x++) {
        int e0 = (x1 - x0) * (y - y0) - (y1 - y0) * (x - x0), e1 = (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1), e2 = (x0 - x2) * (y - y2) - (y0 - y2) * (x - x2);
        if ((e0 >= 0 && e1 >= 0 && e2 >= 0) || (e0 <= 0 && e1 <= 0 && e2 <= 0)) mui_pix(x, y, rgb);
    }
}
static const s16 UX[8] = {0, 707, 1000, 707, 0, -707, -1000, -707}, UY[8] = {-1000, -707, 0, 707, 1000, 707, 0, -707};
static const u8 DKM[8] = {1, 9, 8, 10, 2, 6, 4, 5};               /* hướng 0..7 (N,NE,E,SE,S,SW,W,NW) -> bit phím */
static void draw_dpad(int cx, int cy, int D) {
    mui_fill_a(cx - D / 2, cy - D / 2, cx + D / 2, cy + D / 2, 0, 0);
    ring(cx, cy, D / 2, RGB(240, 150, 30)); ring(cx, cy, D / 2 - 1, RGB(240, 150, 30)); ring(cx, cy, D / 8, RGB(150, 90, 20));
    for (int k = 0; k < 8; k++) {
        int on = S.dk == DKM[k], tip = D * 46 / 100, bs = D * 25 / 100, hw = D * (k & 1 ? 8 : 11) / 100;
        int ux = UX[k], uy = UY[k], px = -uy, py = ux;                  /* vuông góc */
        int tx = cx + ux * tip / 1000, ty = cy + uy * tip / 1000, bx = cx + ux * bs / 1000, by = cy + uy * bs / 1000;
        u32 col = on ? RGB(255, 214, 80) : RGB(34, 46, 70);
        tri(tx, ty, bx + px * hw / 1000, by + py * hw / 1000, bx - px * hw / 1000, by - py * hw / 1000, col);
        if (!on) { ring(tx, ty, 1, RGB(210, 130, 30)); }
    }
}
static int dir_of(int dx, int dy, int D) {              /* -1 vùng chết; 0..7 */
    int ax = dx < 0 ? -dx : dx, ay = dy < 0 ? -dy : dy;
    if (ax * ax + ay * ay < D * D * 3 / 100) return -1;
    int k = (ay * 5 < ax * 2) ? 2 : (ax * 5 < ay * 2) ? 0 : 1;      /* 1 = chéo */
    if (k == 2) return dx > 0 ? 2 : 6;
    if (k == 0) return dy > 0 ? 4 : 0;
    return dx > 0 ? (dy > 0 ? 3 : 1) : (dy > 0 ? 5 : 7);
}
static void dkeys(void *self, int nm) {
    static const s8 KC[4] = {-1, -2, -3, -4};
    for (int b = 0; b < 4; b++) {
        int was = (S.dk >> b) & 1, now = (nm >> b) & 1;
        if (now && !was) mui_key(self, MUI_CLS_GAME, KC[b]); else if (!now && was) StateGame_KeyReleased(self, KC[b]);
    }
    S.dk = (u8)nm;
}
static void dpad_dir(void *self, int x, int y) {
    int cx = S.dmode ? S.fcx : S.cx[B_DPAD], cy = S.dmode ? S.fcy : S.cy[B_DPAD], d = dir_of(x - cx, y - cy, S.d[B_DPAD]);
    dkeys(self, d < 0 ? 0 : DKM[d]);
}
static int pressed(int i) { for (int k = 0; k < 4; k++) if (S.cap[k] == i + 1) return 1; return 0; }
static void draw_btn(int i) {
    int cx = S.cx[i], cy = S.cy[i], D = S.d[i], br = pressed(i) ? 70 : 0;
    switch (i) {
    case B_ATT: art(pressed(i) ? 1 : 0, cx, cy, D, 0); break;
    case B_DPAD: draw_dpad(cx, cy, D); break;
    case B_SWAP: art(6, cx, cy, D, br); break;
    case B_PAUSE: art(3, cx, cy, D, br); break;
    case B_SHOP: art(5, cx, cy, D, br); break;
    case B_SAVE: art(4, cx, cy, D, br); break;
    case B_MAP: art(2, cx, cy, D, br); mapicon(cx, cy, D); break;
    case B_DASH: {
        u8 t[8]; int px = S.f16 >= 28 ? 22 : S.f16 >= 22 ? 15 : 10;
        art(2, cx, cy, D, br); mui_ascii("DASH", t);
        mui_text(cx, cy - px / 2, t, -1, px, RGB(246, 200, 70), TF_CENTER | TF_SHADOW); break;
    }
    default: art(2, cx, cy, D, br); icon(i - B_S0, cx, cy, D); break;
    }
}

/* ---------------- sửa bố cục ---------------- */
static void ed_rects(mui_r *r3) {
    int bh = L.btnH, bw = 4 * bh, gap = bh / 2, x = S.W / 2 - (3 * bw + 2 * gap) / 2, y = S.Ht / 2 - bh / 2;
    for (int i = 0; i < 3; i++) { r3[i].x0 = x + i * (bw + gap); r3[i].x1 = r3[i].x0 + bw; r3[i].y0 = y; r3[i].y1 = y + bh; }
    r3[3].x0 = S.W / 2 - bw; r3[3].x1 = S.W / 2 + bw; r3[3].y0 = y + bh + gap; r3[3].y1 = r3[3].y0 + bh;   /* chế độ dpad */
}
static void edit_paint(void) {
    mui_fill_a(0, 0, S.W, S.Ht, 0, 120);
    for (int i = 0; i < NB; i++) { draw_btn(i); ring(S.cx[i], S.cy[i], S.d[i] / 2 + 1, i == S.sel ? RGB(255, 220, 60) : RGB(200, 220, 255)); }
    mui_r r3[4]; ed_rects(r3);
    mui_text(S.W / 2, r3[0].y0 - 2 * L.px - 6, T_TITLE, T_TITLE_N, L.px, RGB(255, 220, 90), TF_CENTER | TF_SHADOW);
    mui_text(S.W / 2, r3[0].y0 - L.px - 3, T_HINT, T_HINT_N, L.spx > 0 ? L.spx : L.px, RGB(220, 230, 245), TF_CENTER | TF_SHADOW);
    if (S.dmode) mui_button(&r3[3], T_FOLLOW, T_FOLLOW_N, 2, 0); else mui_button(&r3[3], T_FIX, T_FIX_N, 2, 0);
    mui_button(&r3[0], T_SAVE, T_SAVE_N, 1, 0); mui_button(&r3[1], T_DEF, T_DEF_N, 0, 0); mui_button(&r3[2], T_CANCEL, T_CANCEL_N, 0, 0);
}
static int overlap_ok(int i, int nx, int ny) {
    for (int j = 0; j < NB; j++) {
        if (j == i) continue;
        int dx = nx - S.cx[j], dy = ny - S.cy[j], rs = (S.d[i] + S.d[j]) / 2;
        if ((dx * dx + dy * dy) * 100 < 49 * rs * rs) return 0;
    }
    return 1;
}
static void edit_press(int x, int y) {
    mui_r r3[4]; ed_rects(r3);
    for (int i = 0; i < 4; i++) if (x >= r3[i].x0 && x < r3[i].x1 && y >= r3[i].y0 && y < r3[i].y1) {
        if (i == 3) { S.dmode ^= 1; S.sel = -1; return; }
        if (i == 0) { lay_save(); S.edit = 0; lay_apply(); }
        else if (i == 1) { lay_default(); lay_clamp(); }
        else { S.edit = 0; S.dmode = S.dmode_sv; lay_apply(); }
        S.sel = -1; return;
    }
    int best = -1, bd = 1 << 30;
    for (int i = 0; i < NB; i++) {
        int dx = x - S.cx[i], dy = y - S.cy[i], q = dx * dx + dy * dy, r = S.d[i] / 2 + 3;
        if (q <= r * r && q < bd) { bd = q; best = i; }
    }
    S.sel = best;
    if (best >= 0) { S.gx = x - S.cx[best]; S.gy = y - S.cy[best]; }
}
static void edit_move(int x, int y) {
    int i = S.sel;
    if (i < 0) return;
    int r = S.d[i] / 2, nx = clampi(x - S.gx, r, S.W - r), ny = clampi(y - S.gy, r, S.Ht - r);
    if (overlap_ok(i, nx, ny)) { S.cx[i] = nx; S.cy[i] = ny; }
    else if (overlap_ok(i, nx, S.cy[i])) S.cx[i] = nx;
    else if (overlap_ok(i, S.cx[i], ny)) S.cy[i] = ny;
}

/* ---------------- hàng GAME (states.def) ---------------- */
void hud_paint(void *self) {
    if (!S.loaded) lay_load();
    if (S.W != mui_fb_w() || S.Ht != mui_fb_h() || !S.W) lay_apply();
    S.pc++;
    if (S.hold && S.pc - S.t0 >= HOLD_N) { S.hold = 0; S.edit = 1; S.sel = -1; for (int k = 0; k < 4; k++) if (S.cap[k] == B_PAUSE + 1) S.cap[k] = -2; }
    for (int i = 0; i < NB; i++) S.want[i] = 0;
    S.want[B_DPAD] = !S.dmode;
    S.ntab = 0; S.nslot = 0; S.on = 1;
    StateGame_Paint(self);
    S.on = 0;
    mui_clip_reset();
    if (S.edit) { edit_paint(); return; }
    for (int i = 0; i < NB; i++) if (S.want[i]) draw_btn(i);
    if (S.dmode && S.fvis) draw_dpad(S.fcx, S.fcy, S.d[B_DPAD]);
}
static int hit_new(int x, int y) {
    int best = -1, bd = 1 << 30;
    for (int i = 0; i < NB; i++) {
        if (!S.want[i]) continue;
        int dx = x - S.cx[i], dy = y - S.cy[i], q = dx * dx + dy * dy, r = S.d[i] / 2 + 2;
        if (q <= r * r && q < bd) { bd = q; best = i; }
    }
    return best;
}
static int in_old(int x, int y) {
    for (int i = 0; i < NB; i++) if (S.want[i] && x >= S.old[i].x0 && x < S.old[i].x1 && y >= S.old[i].y0 && y < S.old[i].y1) return 1;
    return 0;
}
static void xlat(int i, int x, int y, int *ox, int *oy) {   /* điểm trên nút mới -> điểm tương ứng trên vùng gốc */
    rr *o = &S.old[i];
    int ocx = (o->x0 + o->x1) / 2, ocy = (o->y0 + o->y1) / 2;
    *ox = clampi(ocx + (x - S.cx[i]) * OSZ[i] / S.d[i], o->x0, o->x1 - 1);
    *oy = clampi(ocy + (y - S.cy[i]) * OSZ[i] / S.d[i], o->y0, o->y1 - 1);
}
/* T14b: đường chạm thứ hai của guest = mainKeyPressed 0x98af0: sau vtable TouchPressed (0x98be4) đọc lại gv+0xf4/0xf8 (x,y đã lưu bởi BaseState::TouchPress 0x42d80)
 * rồi so với vùng cố định (W>240: x>=W-36, y 30..60 => KeyPressed(-16) menu, 0x98d8c; x>=W-40, y 70..95 + gv[0x243] => KeyPressed(35), 0x98c94).
 * Cách chặn: khi hud xử lý chạm, ghi đè gv+0xf4/0xf8 = toạ độ đã dịch (nút mới) hoặc -9999 (nuốt). Cờ MUI_F_DISABLE => hud_touch không chạy => nguyên vẹn. */
static void legacy_xy(int x, int y) { *(int *)(gv + 0xf4) = x; *(int *)(gv + 0xf8) = y; }
static int hud_touch_in(void *self, int x, int y);
int hud_touch(void *self, int x, int y) { legacy_xy(-9999, -9999); return hud_touch_in(self, x, y); }
static int hud_touch_in(void *self, int x, int y) {
    int z = mui_tz & 3;
    if (S.edit) { S.cap[z] = -2; edit_press(x, y); return 0; }
    int i = hit_new(x, y);
    if (i < 0 && S.dmode && x < S.W / 2 && !in_old(x, y)) { S.cap[z] = B_DPAD + 1; S.fcx = x; S.fcy = y; S.fvis = 1; dpad_dir(self, x, y); return 0; }
    if (i == B_DPAD) { S.cap[z] = B_DPAD + 1; dpad_dir(self, x, y); return 0; }
    if (i >= 0) {
        S.cap[z] = (s8)(i + 1);
        if (i == B_PAUSE) { S.hold = 1; S.t0 = S.pc; S.hx = x; S.hy = y; return 0; }
        int ox, oy; xlat(i, x, y, &ox, &oy);
        legacy_xy(ox, oy); StateGame_TouchPressed(self, ox, oy, mui_tz); return 0;
    }
    if (in_old(x, y)) { S.cap[z] = -2; return 0; }
    S.cap[z] = -1; legacy_xy(x, y);
    StateGame_TouchPressed(self, x, y, mui_tz); return 0;
}
int hud_move(void *self, int x, int y) {
    int z = mui_tz & 3, c = S.cap[z];
    if (S.edit) { if (c == -2) edit_move(x, y); return 0; }
    if (c > 0) {
        c--;
        if (c == B_DPAD) { dpad_dir(self, x, y); return 0; }
        if (c == B_PAUSE) { int dx = x - S.hx, dy = y - S.hy; if (S.hold && dx * dx + dy * dy > 14 * 14) { S.hold = 0; S.cap[z] = -2; } return 0; }
        int ox, oy; xlat(c, x, y, &ox, &oy);
        StateGame_TouchMoved(self, ox, oy, mui_tz); return 0;
    }
    if (c == -2) return 0;
    StateGame_TouchMoved(self, x, y, mui_tz); return 0;
}
void hud_release(void *self, int x, int y, int z) {
    int zp = z & 3, c = S.cap[zp];
    S.cap[zp] = 0;
    if (S.edit) { S.sel = -1; return; }
    if (c > 0) {
        int ox, oy; c--;
        if (c == B_DPAD) { dkeys(self, 0); S.fvis = 0; return; }
        if (c == B_PAUSE) {
            if (!S.hold) return;                              /* đã vào sửa bố cục / kéo ra ngoài */
            S.hold = 0; xlat(c, x, y, &ox, &oy);
            StateGame_TouchPressed(self, ox, oy, z); StateGame_TouchReleased(self, ox, oy, z);
            gv[0x241] = 1; mui_key(self, MUI_CLS_GAME, -16); return;   /* bản gốc: mainKeyPressed 0x98d8c (g[0x241]=1, KeyPressed(-16)) ngay sau TouchPressed khi chạm trong x>=W-36, y 30..60 */
        }
        xlat(c, x, y, &ox, &oy);
        StateGame_TouchReleased(self, ox, oy, z); return;
    }
    if (c == -2) return;
    StateGame_TouchReleased(self, x, y, z);
}
