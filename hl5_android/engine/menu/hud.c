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
    int bpl, nac, pool_used, itab_s, dpD;     /* T14c: thêm CUỐI struct để giữ offset (check_t14 đọc cx@84, d@196, old@252, pm@364) */
    u8 *fb;
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
    S.nac = 0; S.pool_used = 0; S.dpD = 0; S.itab_s = 0;          /* T14c: đổi cỡ -> làm mới mọi bộ đệm ảnh phóng */
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
        if (S.fb && c >= 0 && d >= 0 && c + SLOT <= S.W && d + SLOT <= S.Ht) {     /* T14c: đọc thẳng framebuffer */
            for (int y = 0; y < SLOT; y++) {
                const u16 *p = (const u16 *)(S.fb + (d + y) * S.bpl) + c; u16 *q = &S.snap[i][y * SLOT];
                for (int x = 0; x < SLOT; x++) q[x] = p[x];
            }
        } else for (int y = 0; y < SLOT; y++) for (int x = 0; x < SLOT; x++) S.snap[i][y * SLOT + x] = (u16)mui_getpx(c + x, d + y);
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
        if (S.fb && x0 >= 0 && y0 >= 0 && x0 + SLOT <= S.W && y0 + SLOT <= S.Ht) {
            for (int y = 0; y < SLOT; y++) {
                u16 *p = (u16 *)(S.fb + (y0 + y) * S.bpl) + x0; const u16 *sn = &S.snap[i][y * SLOT]; u16 *sp = &S.spr[i][y * SLOT];
                for (int x = 0; x < SLOT; x++) { u16 cur = p[x], o = sn[x]; sp[x] = cur != o ? cur : (u16)TR; p[x] = o; }
            }
            continue;
        }
        for (int y = 0; y < SLOT; y++) for (int x = 0; x < SLOT; x++) {
            u16 cur = (u16)mui_getpx(x0 + x, y0 + y), sn = S.snap[i][y * SLOT + x];
            S.spr[i][y * SLOT + x] = cur != sn ? cur : (u16)TR;
            mui_put16(x0 + x, y0 + y, sn);
        }
    }
    return r;
}

/* ---------------- vẽ ---------------- */
/* T14c: ảnh phóng dựng MỘT lần khi đổi (id ảnh, cỡ D) rồi chỉ chép/trộn mỗi khung. Bộ nhớ: POOL tĩnh (bss), cấp phát bump, xoá khi đổi cỡ màn (lay_apply).
 * Mỗi mục: col (RGB565 đã nội suy, nhân alpha) + al (alpha 8 bit) + [xs,xe) khoảng alpha>0 của từng hàng. Công thức trộn giữ NGUYÊN so với bản cũ (bit-exact). */
#define POOL_B (5 << 19)    /* 2,5 MiB bss; dùng thật ~1,3 MB ở 2400x1080 */
#define NAC 16
/* T24a: mỗi điểm 1 từ u32: bit 0..15 = màu nhân alpha (565), bit 16..23 = ia = 255 - alpha, bit 24 = 1 nếu cần chặn tràn (clamp) hoặc alpha == 0 (đường chậm). Nút HUD hầu như không có đoạn alpha == 255 (đo: 0-0,2%) nên không dùng memcpy. */
typedef struct { s16 id, D; u32 *w; s16 *xs, *xe; } ac_t;
static u8 POOL[POOL_B] __attribute__((aligned(4)));
static ac_t AC[NAC];
static void *pal(int n) { n = (n + 3) & ~3; if (S.pool_used + n > POOL_B) return NULL; void *p = POOL + S.pool_used; S.pool_used += n; return p; }
static void art_src(int id, const u8 **P, int *s) {
    switch (id) {
    case 0: *P = ART_EMB_P; *s = ART_EMB_S; break;
    case 1: *P = ART_GLOW_P; *s = ART_GLOW_S; break;
    case 2: *P = ART_FRAME_P; *s = ART_FRAME_S; break;
    case 3: *P = ART_PAUSE_P; *s = ART_PAUSE_S; break;
    case 4: *P = ART_SAVE_P; *s = ART_SAVE_S; break;
    case 5: *P = ART_SHOP_P; *s = ART_SHOP_S; break;
    default: *P = ART_SWAP_P; *s = ART_SWAP_S; break;
    }
}
/* điểm (dx,dy) của ảnh D x D từ nguồn s x s (RGBA8 nhân alpha): D < s lọc trung bình vùng (thu nhỏ), D == s chép, D > s bilinear (chỉ khi màn rất lớn / dự phòng) -> o[0..3] = r,g,b,a 0..255 */
static void sample(const u8 *P, int s, int D, int dx, int dy, int *o) {
    if (D == s) { const u8 *q = P + (dy * s + dx) * 4; o[0] = q[0]; o[1] = q[1]; o[2] = q[2]; o[3] = q[3]; return; }
    if (D < s) {
        int x0 = dx * s / D, x1 = ((dx + 1) * s + D - 1) / D, y0 = dy * s / D, y1 = ((dy + 1) * s + D - 1) / D;
        if (x1 > s) x1 = s;
        if (y1 > s) y1 = s;
        int n = (x1 - x0) * (y1 - y0), a0 = 0, a1 = 0, a2 = 0, a3 = 0;
        for (int y = y0; y < y1; y++) for (int x = x0; x < x1; x++) { const u8 *q = P + (y * s + x) * 4; a0 += q[0]; a1 += q[1]; a2 += q[2]; a3 += q[3]; }
        o[0] = (a0 + n / 2) / n; o[1] = (a1 + n / 2) / n; o[2] = (a2 + n / 2) / n; o[3] = (a3 + n / 2) / n; return;
    }
    int step = ((s - 1) << 16) / (D - 1), sx = dx * step, sy = dy * step;
    int ix = sx >> 16, fx = (sx >> 8) & 255, iy = sy >> 16, fy = (sy >> 8) & 255, ix1 = ix + 1 < s ? ix + 1 : ix, iy1 = iy + 1 < s ? iy + 1 : iy;
    int w00 = (256 - fx) * (256 - fy) >> 8, w01 = fx * (256 - fy) >> 8, w10 = (256 - fx) * fy >> 8, w11 = 256 - w00 - w01 - w10;
    const u8 *q0 = P + (iy * s + ix) * 4, *q1 = P + (iy * s + ix1) * 4, *q2 = P + (iy1 * s + ix) * 4, *q3 = P + (iy1 * s + ix1) * 4;
    for (int k = 0; k < 4; k++) o[k] = (q0[k] * w00 + q1[k] * w01 + q2[k] * w10 + q3[k] * w11) >> 8;
}
static const u8 BAY[16] = {0, 8, 2, 10, 12, 4, 14, 6, 3, 11, 1, 9, 15, 7, 13, 5};
static ac_t *ac_get(int id, int D) {
    for (int i = 0; i < S.nac; i++) if (AC[i].id == id && AC[i].D == D) return &AC[i];
    if (D < 4 || D > 2000 || S.nac >= NAC) return NULL;
    int sv = S.pool_used;
    ac_t *q = &AC[S.nac];
    q->w = pal(D * D * 4); q->xs = pal(D * 2); q->xe = pal(D * 2);
    if (!q->w || !q->xs || !q->xe) { S.pool_used = sv; return NULL; }
    q->id = (s16)id; q->D = (s16)D; S.nac++;
    const u8 *P; int s; art_src(id, &P, &s);
    for (int dy = 0; dy < D; dy++) {
        int xs = D, xe = 0;
        for (int dx = 0; dx < D; dx++) {
            int o[4], t = BAY[(dy & 3) * 4 + (dx & 3)];
            sample(P, s, D, dx, dy, o);
            int r = (o[0] + (t >> 1)) >> 3, g = (o[1] + (t >> 2)) >> 2, bl = (o[2] + (t >> 1)) >> 3;     /* dither Bayer 4x4 khi lượng tử hoá 565 (1 lần) */
            if (r > 31) r = 31;
            if (g > 63) g = 63;
            if (bl > 31) bl = 31;
            { int a = o[3], ia = 255 - a, unsafe = a == 0 || r + ((31 * ia) >> 8) > 31 || g + ((63 * ia) >> 8) > 63 || bl + ((31 * ia) >> 8) > 31;
              q->w[dy * D + dx] = (u32)((r << 11) | (g << 5) | bl) | ((u32)ia << 16) | ((u32)unsafe << 24); }
            if (o[3] > 0) { if (dx < xs) xs = dx; xe = dx + 1; }
        }
        q->xs[dy] = (s16)xs; q->xe[dy] = (s16)xe;
    }
    return q;
}
static void art_slow(int id, int cx, int cy, int D, int bright) {   /* đường không bộ đệm: chỉ khi POOL hết chỗ (màn cực lớn) */
    const u8 *P; int s; art_src(id, &P, &s);
    if (D < 4) return;
    int x0 = cx - D / 2, y0 = cy - D / 2;
    for (int dy = 0; dy < D; dy++) {
        int y = y0 + dy;
        if (y < 0 || y >= S.Ht) continue;
        for (int dx = 0; dx < D; dx++) {
            int x = x0 + dx, o[4];
            if (x < 0 || x >= S.W) continue;
            sample(P, s, D, dx, dy, o);
            int a = o[3];
            if (a <= 0) continue;
            int r = o[0] >> 3, g = o[1] >> 2, bl = o[2] >> 3;
            u32 dp = mui_getpx(x, y);
            int ia = 255 - a;
            int rr_ = r + (((dp >> 11) * ia) >> 8), gg = g + ((((dp >> 5) & 63) * ia) >> 8), bb = bl + (((dp & 31) * ia) >> 8);
            if (bright) { rr_ += (31 - rr_) * bright * a >> 16; gg += (63 - gg) * bright * a >> 16; bb += (31 - bb) * bright * a >> 16; }
            mui_put16(x, y, (u32)((clampi(rr_, 0, 31) << 11) | (clampi(gg, 0, 63) << 5) | clampi(bb, 0, 31)));
        }
    }
}
static void art_row(u16 *d, const u32 *wp, int xs, int xe) {      /* điểm [xs,xe) của một hàng: bit-exact với công thức cũ; điểm "an toàn" pha màu song song 2 làn (r,b) + g, cộng thẳng vào 565 */
    const u32 M = 0x001F001Fu;
    for (int x = xs; x < xe; x++) {
        u32 w = wp[x];
        u32 dp = d[x];
        if (w >> 24) {
            u32 a = 255 - ((w >> 16) & 255);
            if (!a) continue;
            u32 s = w & 0xffff, ia = 255 - a;
            u32 r = (s >> 11) + (((dp >> 11) * ia) >> 8), g = ((s >> 5) & 63) + ((((dp >> 5) & 63) * ia) >> 8), b = (s & 31) + (((dp & 31) * ia) >> 8);
            if (r > 31) r = 31;
            if (g > 63) g = 63;
            if (b > 31) b = 31;
            d[x] = (u16)((r << 11) | (g << 5) | b);
            continue;
        }
        u32 ia = (w >> 16) & 255, rb = ((dp >> 11) | ((dp & 31) << 16)) * ia, g = ((dp >> 5) & 63) * ia;
        rb = (rb >> 8) & M;
        d[x] = (u16)((rb << 11) + (rb >> 16) + (((g >> 8) & 63) << 5) + w);
    }
}
static void art(int id, int cx, int cy, int D, int bright) {
    if (D < 4) return;
    ac_t *q = S.fb ? ac_get(id, D) : NULL;
    if (!q) { art_slow(id, cx, cy, D, bright); return; }
    if (id == 0) ac_get(1, D);                                  /* dựng sẵn ảnh sáng (nhấn) để không giật khi chạm */
    int x0 = cx - D / 2, y0 = cy - D / 2, W = S.W;
    int dy0 = y0 < 0 ? -y0 : 0, dy1 = y0 + D > S.Ht ? S.Ht - y0 : D;
    for (int dy = dy0; dy < dy1; dy++) {
        int xs = q->xs[dy], xe = q->xe[dy];
        if (xs < -x0) xs = -x0;
        if (xe > W - x0) xe = W - x0;
        if (xs >= xe) continue;
        u16 *d = (u16 *)(S.fb + (y0 + dy) * S.bpl) + x0; const u32 *wp = q->w + dy * D;
        if (!bright) {
            art_row(d, wp, xs, xe);
        } else {
            for (int x = xs; x < xe; x++) {
                int ia = (wp[x] >> 16) & 255, a = 255 - ia;
                if (!a) continue;
                u32 s = wp[x] & 0xffff, dp = d[x];
                int rr_ = (int)(s >> 11) + (((dp >> 11) * ia) >> 8), gg = (int)((s >> 5) & 63) + ((((dp >> 5) & 63) * ia) >> 8), bb = (int)(s & 31) + (((dp & 31) * ia) >> 8);
                rr_ += (31 - rr_) * bright * a >> 16; gg += (63 - gg) * bright * a >> 16; bb += (31 - bb) * bright * a >> 16;
                d[x] = (u16)((clampi(rr_, 0, 31) << 11) | (clampi(gg, 0, 63) << 5) | clampi(bb, 0, 31));
            }
        }
    }
}
/* đích vẽ của ring/tri: T.p == NULL -> màn hình (mui_pix); khác -> bộ đệm u16 (TR = trong suốt), toạ độ x-ox, y-oy */
static struct { u16 *p; int ox, oy, w, h; } T;
static inline u32 c565(u32 rgb) { return ((rgb >> 8) & 0xF800) | ((rgb >> 5) & 0x07E0) | ((rgb >> 3) & 0x1F); }
static void px(int x, int y, u32 rgb) {
    if (!T.p) { mui_pix(x, y, rgb); return; }
    x -= T.ox; y -= T.oy;
    if ((u32)x >= (u32)T.w || (u32)y >= (u32)T.h) return;
    T.p[y * T.w + x] = (u16)c565(rgb);
}
static void ring(int cx, int cy, int r, u32 rgb) {         /* vòng tròn mảnh (điểm giữa) */
    int x = r, y = 0, e = 1 - r;
    while (x >= y) {
        px(cx + x, cy + y, rgb); px(cx - x, cy + y, rgb); px(cx + x, cy - y, rgb); px(cx - x, cy - y, rgb);
        px(cx + y, cy + x, rgb); px(cx - y, cy + x, rgb); px(cx + y, cy - x, rgb); px(cx - y, cy - x, rgb);
        y++; if (e < 0) e += 2 * y + 1; else { x--; e += 2 * (y - x) + 1; }
    }
}
static u8 IXT[256];
static void icon(int i, int cx, int cy, int D) {           /* biểu tượng guest (26x26 trong ô 30x30) phóng NEAREST; bảng chỉ số dựng khi D đổi */
    int s = D * 60 / 100;
    if (s < 8) return;
    int x0 = cx - s / 2, y0 = cy - s / 2;
    if (!S.fb || s > 256) {
        for (int dy = 0; dy < s; dy++) for (int dx = 0; dx < s; dx++) {
            u16 c = S.spr[i][(2 + dy * 26 / s) * SLOT + 2 + dx * 26 / s];
            if (c != TR) mui_put16(x0 + dx, y0 + dy, c);
        }
        return;
    }
    if (S.itab_s != s) { for (int k = 0; k < s; k++) IXT[k] = (u8)(2 + k * 26 / s); S.itab_s = s; }
    int dx0 = x0 < 0 ? -x0 : 0, dx1 = x0 + s > S.W ? S.W - x0 : s;
    for (int dy = 0; dy < s; dy++) {
        int y = y0 + dy;
        if (y < 0 || y >= S.Ht) continue;
        const u16 *row = &S.spr[i][IXT[dy] * SLOT]; u16 *d = (u16 *)(S.fb + y * S.bpl) + x0;
        for (int dx = dx0; dx < dx1; dx++) { u16 c = row[IXT[dx]]; if (c != TR) d[dx] = c; }
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
    if (T.p) {                                              /* chỉ duyệt phần giao với bộ đệm đích */
        if (mnx < T.ox) mnx = T.ox;
        if (mny < T.oy) mny = T.oy;
        if (mxx > T.ox + T.w - 1) mxx = T.ox + T.w - 1;
        if (mxy > T.oy + T.h - 1) mxy = T.oy + T.h - 1;
    }
    for (int y = mny; y <= mxy; y++) for (int x = mnx; x <= mxx; x++) {
        int e0 = (x1 - x0) * (y - y0) - (y1 - y0) * (x - x0), e1 = (x2 - x1) * (y - y1) - (y2 - y1) * (x - x1), e2 = (x0 - x2) * (y - y2) - (y0 - y2) * (x - x2);
        if ((e0 >= 0 && e1 >= 0 && e2 >= 0) || (e0 <= 0 && e1 <= 0 && e2 <= 0)) px(x, y, rgb);
    }
}
static const s16 UX[8] = {0, 707, 1000, 707, 0, -707, -1000, -707}, UY[8] = {-1000, -707, 0, 707, 1000, 707, 0, -707};
static const u8 DKM[8] = {1, 9, 8, 10, 2, 6, 4, 5};               /* hướng 0..7 (N,NE,E,SE,S,SW,W,NW) -> bit phím */
static void arrow_pts(int k, int cx, int cy, int D, int *p) {     /* 3 đỉnh (x,y)x3 của mũi tên k */
    int tip = D * 46 / 100, bs = D * 25 / 100, hw = D * (k & 1 ? 8 : 11) / 100;
    int ux = UX[k], uy = UY[k], px_ = -uy, py = ux;                 /* vuông góc */
    int tx = cx + ux * tip / 1000, ty = cy + uy * tip / 1000, bx = cx + ux * bs / 1000, by = cy + uy * bs / 1000;
    p[0] = tx; p[1] = ty; p[2] = bx + px_ * hw / 1000; p[3] = by + py * hw / 1000; p[4] = bx - px_ * hw / 1000; p[5] = by - py * hw / 1000;
}
static void render_dpad(int cx, int cy, int D, int pk) {          /* pk = hướng đang nhấn (0..7) hoặc -1; (mui_fill_a(...,0,0) cũ là no-op vì a5=0 nên bỏ) */
    ring(cx, cy, D / 2, RGB(240, 150, 30)); ring(cx, cy, D / 2 - 1, RGB(240, 150, 30)); ring(cx, cy, D / 8, RGB(150, 90, 20));
    for (int k = 0; k < 8; k++) {
        int on = k == pk, p[6];
        arrow_pts(k, cx, cy, D, p);
        u32 col = on ? RGB(255, 214, 80) : RGB(34, 46, 70);
        tri(p[0], p[1], p[2], p[3], p[4], p[5], col);
        if (!on) { ring(p[0], p[1], 1, RGB(210, 130, 30)); }
    }
}
/* bộ đệm dpad: nền (không mũi tên nhấn) + 8 lớp phủ (hình chữ nhật quanh mũi tên k, đã vẽ ĐỦ thứ tự với k nhấn). Lúc nhấn: chép nền trừ HCN k, rồi chép lớp phủ. */
static struct { int D, R, w, ok; u16 *p; s16 *xs, *xe; int ix[8], iy[8], iw[8], ih[8]; u16 *op[8]; } DP;
static int dp_build(int D) {
    int R = D / 2 + 2, w = 2 * R + 1, sv = S.pool_used;
    DP.ok = 0; DP.D = D; DP.R = R; DP.w = w;
    DP.p = pal(w * w * 2); DP.xs = pal(w * 2); DP.xe = pal(w * 2);
    if (!DP.p || !DP.xs || !DP.xe) { S.pool_used = sv; return 0; }
    for (int i = 0; i < w * w; i++) DP.p[i] = TR;
    T.p = DP.p; T.ox = -R; T.oy = -R; T.w = w; T.h = w;
    render_dpad(0, 0, D, -1);
    for (int r = 0; r < w; r++) {
        int xs = w, xe = 0;
        for (int x = 0; x < w; x++) if (DP.p[r * w + x] != TR) { if (x < xs) xs = x; xe = x + 1; }
        DP.xs[r] = (s16)xs; DP.xe[r] = (s16)xe;
    }
    for (int k = 0; k < 8; k++) {
        int p[6]; arrow_pts(k, 0, 0, D, p);
        int x0 = p[0], x1 = p[0], y0 = p[1], y1 = p[1];
        for (int j = 2; j < 6; j += 2) { if (p[j] < x0) x0 = p[j]; if (p[j] > x1) x1 = p[j]; if (p[j + 1] < y0) y0 = p[j + 1]; if (p[j + 1] > y1) y1 = p[j + 1]; }
        x0 = clampi(x0 - 2, -R, R); y0 = clampi(y0 - 2, -R, R); x1 = clampi(x1 + 3, -R, R + 1); y1 = clampi(y1 + 3, -R, R + 1);
        DP.ix[k] = x0 + R; DP.iy[k] = y0 + R; DP.iw[k] = x1 - x0; DP.ih[k] = y1 - y0;
        DP.op[k] = pal(DP.iw[k] * DP.ih[k] * 2);
        if (!DP.op[k]) { S.pool_used = sv; T.p = NULL; return 0; }
        for (int i = 0; i < DP.iw[k] * DP.ih[k]; i++) DP.op[k][i] = TR;
        T.p = DP.op[k]; T.ox = x0; T.oy = y0; T.w = DP.iw[k]; T.h = DP.ih[k];
        render_dpad(0, 0, D, k);
    }
    T.p = NULL; DP.ok = 1; return 1;
}
static void cpy(u16 *d, const u16 *s, int a, int b) { for (int i = a; i < b; i++) { u16 c = s[i]; if (c != TR) d[i] = c; } }
static void draw_dpad(int cx, int cy, int D) {
    int pk = -1;
    for (int k = 0; k < 8; k++) if (S.dk == DKM[k]) pk = k;
    if (S.fb && S.dpD != D) { S.dpD = D; dp_build(D); }
    if (!S.fb || S.dpD != D || !DP.ok) { render_dpad(cx, cy, D, pk); return; }
    int R = DP.R, w = DP.w, bx = cx - R, by = cy - R;
    int r0 = by < 0 ? -by : 0, r1 = by + w > S.Ht ? S.Ht - by : w;
    int ca = -bx, cb = S.W - bx;                             /* khoảng cột hợp lệ (chỉ số bộ đệm) */
    for (int r = r0; r < r1; r++) {
        int xs = DP.xs[r], xe = DP.xe[r];
        if (xs < ca) xs = ca;
        if (xe > cb) xe = cb;
        if (xs >= xe) continue;
        u16 *d = (u16 *)(S.fb + (by + r) * S.bpl) + bx; const u16 *s = DP.p + r * w;
        if (pk >= 0 && r >= DP.iy[pk] && r < DP.iy[pk] + DP.ih[pk]) {
            int l = DP.ix[pk], rr2 = l + DP.iw[pk];
            cpy(d, s, xs, xe < l ? xe : l);
            cpy(d, s, xs > rr2 ? xs : rr2, xe);
        } else cpy(d, s, xs, xe);
    }
    if (pk >= 0) {
        int ix = DP.ix[pk], iw = DP.iw[pk];
        int a = ix < ca ? ca : ix, b = ix + iw > cb ? cb : ix + iw;
        for (int r = 0; r < DP.ih[pk]; r++) {
            int ry = DP.iy[pk] + r;
            if (ry < r0 || ry >= r1 || a >= b) continue;
            cpy((u16 *)(S.fb + (by + ry) * S.bpl) + bx, DP.op[pk] + r * iw - ix, a, b);
        }
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
    int cx = S.cx[i], cy = S.cy[i], D = S.d[i], br = pressed(i) ? 40 : 0;
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
static void fbget(void) {                                /* T14c: con trỏ + stride framebuffer một lần mỗi khung (cùng điều kiện mui_fb_begin đã kiểm) */
    u32 g = _lcdG; S.fb = NULL;
    if (!g) return;
    u32 fb = *(volatile u32 *)g;
    if (!fb) return;
    int bpl = *(int *)(fb + 8);
    u8 *base = *(u8 **)(fb + 0x10);
    if (base && bpl >= 2 * mui_fb_w()) { S.fb = base; S.bpl = bpl; }
}
void hud_paint(void *self) {
    fbget();
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
