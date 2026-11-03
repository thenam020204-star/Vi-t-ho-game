/* Vẽ thẳng vào khung hình RGB565 của guest (Graphic::GetFB: struct {w,h,bpl,bpp,ptr}). Không gọi hàm vẽ guest, không cấp phát. */
#include "mui.h"

static struct { u8 *base; int bpl, w, h, cx0, cy0, cx1, cy1, ok; } F;

int mui_fb_begin(void) {
    u32 g = _lcdG;
    F.ok = 0;
    if (!g) return 0;
    if (*(volatile u8 *)(g + 0x5c) != 1) return 0;          /* 1 = RGB565 (kiểm ở harness: [Graphic+0x5c]) */
    u32 fb = *(volatile u32 *)g;
    if (!fb) return 0;
    F.w = *(int *)(fb + 0); F.h = *(int *)(fb + 4); F.bpl = *(int *)(fb + 8); F.base = *(u8 **)(fb + 0x10);
    if (!F.base || F.bpl < 2 * F.w || F.w <= 0 || F.h <= 0) return 0;
    F.ok = 1; mui_clip_reset();
    return 1;
}
int mui_fb_w(void) { return F.w; }
int mui_fb_h(void) { return F.h; }
void mui_clip_reset(void) { F.cx0 = F.cy0 = 0; F.cx1 = F.w; F.cy1 = F.h; }
void mui_clip(int x0, int y0, int x1, int y1) {
    F.cx0 = x0 < 0 ? 0 : x0; F.cy0 = y0 < 0 ? 0 : y0; F.cx1 = x1 > F.w ? F.w : x1; F.cy1 = y1 > F.h ? F.h : y1;
}

static inline u32 c565(u32 rgb) { return ((rgb >> 8) & 0xF800) | ((rgb >> 5) & 0x07E0) | ((rgb >> 3) & 0x1F); }
#define MASK 0x07E0F81Fu

static void span(int y, int xl, int xr, u32 c) {
    if (y < F.cy0 || y >= F.cy1) return;
    if (xl < F.cx0) xl = F.cx0;
    if (xr > F.cx1) xr = F.cx1;
    int n = xr - xl;
    if (n <= 0) return;
    u16 *p = (u16 *)(F.base + y * F.bpl) + xl;
    if (xl & 1) { *p++ = c; n--; }
    u32 cc = c | (c << 16);
    while (n >= 2) { *(u32 *)p = cc; p += 2; n -= 2; }
    if (n) *p = c;
}
static void span_a(int y, int xl, int xr, u32 c, int a5) {   /* a5 = 0..32 */
    if (y < F.cy0 || y >= F.cy1) return;
    if (xl < F.cx0) xl = F.cx0;
    if (xr > F.cx1) xr = F.cx1;
    int n = xr - xl;
    if (n <= 0 || a5 <= 0) return;
    u16 *p = (u16 *)(F.base + y * F.bpl) + xl;
    u32 s = ((c | (c << 16)) & MASK) * (u32)a5, inv = 32 - a5;
    while (n-- > 0) {
        u32 d = *p, x = ((d | (d << 16)) & MASK) * inv + s;
        x = (x >> 5) & MASK;
        *p++ = x | (x >> 16);
    }
}
void mui_pix(int x, int y, u32 rgb) {
    if (x < F.cx0 || x >= F.cx1 || y < F.cy0 || y >= F.cy1) return;
    *((u16 *)(F.base + y * F.bpl) + x) = c565(rgb);
}
void mui_fill(int x0, int y0, int x1, int y1, u32 rgb) {
    u32 c = c565(rgb);
    for (int y = y0; y < y1; y++) span(y, x0, x1, c);
}
void mui_fill_a(int x0, int y0, int x1, int y1, u32 rgb, int a) {
    if (a >= 255) { mui_fill(x0, y0, x1, y1, rgb); return; }
    int a5 = (a + 4) >> 3;
    u32 c = c565(rgb);
    if (a5 >= 32) { mui_fill(x0, y0, x1, y1, rgb); return; }
    for (int y = y0; y < y1; y++) span_a(y, x0, x1, c, a5);
}
void mui_vgrad(int x0, int y0, int x1, int y1, u32 top, u32 bot) {
    int n = y1 - y0 - 1;
    if (n < 1) n = 1;
    int r0 = (top >> 16) & 255, g0 = (top >> 8) & 255, b0 = top & 255;
    int dr = (((int)((bot >> 16) & 255) - r0) << 16) / n, dg = (((int)((bot >> 8) & 255) - g0) << 16) / n,
        db = (((int)(bot & 255) - b0) << 16) / n;
    int r = (r0 << 16) + 32768, g = (g0 << 16) + 32768, b = (b0 << 16) + 32768;
    for (int y = y0; y < y1; y++, r += dr, g += dg, b += db)
        span(y, x0, x1, c565(((r >> 16) << 16) | ((g >> 16) << 8) | (b >> 16)));
}

/* ---- hình chữ nhật góc cắt (nửa chiều rộng 'bán kính' pixel-art) ---- */
static int isqrt(int v) { int r = 0; while ((r + 1) * (r + 1) <= v) r++; return r; }
static int inset(int rad, int d) {                       /* d = khoảng cách tới cạnh trên/dưới (0 = hàng sát cạnh) */
    if (rad <= 0 || d >= rad) return 0;
    int t = 2 * (rad - d) - 1, q = isqrt(4 * rad * rad - t * t);
    int ix = rad - ((q + 1) >> 1);
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
/* vành: (ngoài) \ (trong); trong có thể rỗng (ix1<=ix0) */
static void ring(int x0, int y0, int x1, int y1, int rad, int a0, int b0, int a1, int b1, int rad2, u32 c, int a5) {
    for (int y = y0; y < y1; y++) {
        int xl, xr, il, ir;
        if (!ext(x0, y0, x1, y1, rad, y, &xl, &xr)) continue;
        if (a1 > a0 && b1 > b0 && ext(a0, b0, a1, b1, rad2, y, &il, &ir)) {
            if (il < xl) il = xl;
            if (ir > xr) ir = xr;
            if (a5 >= 32) { span(y, xl, il, c); span(y, ir, xr, c); } else { span_a(y, xl, il, c, a5); span_a(y, ir, xr, c, a5); }
        } else if (a5 >= 32) span(y, xl, xr, c);
        else span_a(y, xl, xr, c, a5);
    }
}
void mui_rr(int x0, int y0, int x1, int y1, int rad, u32 rgb, int a) {
    int a5 = a >= 255 ? 32 : (a + 4) >> 3;
    ring(x0, y0, x1, y1, rad, 0, 0, 0, 0, 0, c565(rgb), a5);
}
/* khung viền đen / cam (hoặc xám xanh) `thick` / cam tối (hoặc đen) 1 / nền; in4 = vùng trong (x0,y0,x1,y1).
 * Như mockup (Cv.frame): các lớp vẽ ĐẶC chồng nhau rồi nền `fill` (alpha fa) hợp lên lớp trong cùng, nên vùng trong
 * là một màu đặc (không lộ cảnh nền) => mỗi điểm chỉ ghi 1 lần. fill<0: để nguyên màu lớp trong cùng. */
void mui_frame(int x0, int y0, int x1, int y1, int rad, int orange, int thick, u32 fill, int fa, int *in4) {
    int i0 = 1 + thick, j0 = i0 + 1, r2 = rad > 1 ? rad - 1 : 0, r3 = rad > 2 ? rad - 2 : 0;
    u32 inner = orange ? C_ORANGE_IN : C_INK;
    ring(x0, y0, x1, y1, rad + 1, x0 + 1, y0 + 1, x1 - 1, y1 - 1, rad, c565(C_INK), 32);
    ring(x0 + 1, y0 + 1, x1 - 1, y1 - 1, rad, x0 + i0, y0 + i0, x1 - i0, y1 - i0, r2, c565(orange ? C_ORANGE : C_LINE), 32);
    if ((int)fill >= 0 && fa > 0) {
        if (fa > 255) fa = 255;
        int r = (((fill >> 16) & 255) * fa + ((inner >> 16) & 255) * (255 - fa)) / 255;
        int g = (((fill >> 8) & 255) * fa + ((inner >> 8) & 255) * (255 - fa)) / 255;
        int b = ((fill & 255) * fa + (inner & 255) * (255 - fa)) / 255;
        ring(x0 + i0, y0 + i0, x1 - i0, y1 - i0, r2, x0 + j0, y0 + j0, x1 - j0, y1 - j0, r3, c565(inner), 32);
        ring(x0 + j0, y0 + j0, x1 - j0, y1 - j0, r3, 0, 0, 0, 0, 0, c565(RGB(r, g, b)), 32);
    } else ring(x0 + i0, y0 + i0, x1 - i0, y1 - i0, r2, 0, 0, 0, 0, 0, c565(inner), 32);
    if (in4) { in4[0] = x0 + j0; in4[1] = y0 + j0; in4[2] = x1 - j0; in4[3] = y1 - j0; }
}

void mui_disc(int cx, int cy, int r, u32 rgb) {
    u32 c = c565(rgb);
    for (int dy = -r; dy <= r; dy++) { int q = isqrt(r * r - dy * dy + r); span(cy + dy, cx - q, cx + q + 1, c); }
}

/* GbmImage (guest): [0]&15 = 4 (4 bit) hoặc 8; u16 w @2, h @4; paleta u16[] @8; điểm ảnh @0xc. Màu = SKIP_COLOR: trong suốt.
 * Vẽ phóng s lần NEAREST; tint>=0: mọi điểm không trong suốt vẽ màu phẳng tint. */
void mui_gbm(const u8 *gbm, int x, int y, int s, int tint) {
    if (!gbm || s < 1) return;
    int w = *(const u16 *)(gbm + 2), h = *(const u16 *)(gbm + 4);
    const u16 *pal = *(const u16 *const *)(gbm + 8);
    const u8 *pix = *(const u8 *const *)(gbm + 0xc);
    if (!pal || !pix) return;
    int nib = (gbm[0] & 15) == 4, stride = nib ? (w + 1) >> 1 : w;
    u32 skip = SKIP_COLOR & 0xffff, tc = tint >= 0 ? c565((u32)tint) : 0;
    for (int sy = 0; sy < h; sy++) {
        const u8 *row = pix + sy * stride;
        for (int sx = 0; sx < w; sx++) {
            int idx = nib ? ((sx & 1) ? row[sx >> 1] & 15 : row[sx >> 1] >> 4) : row[sx];
            u32 c = pal[idx];
            if (c == skip) continue;
            if (tint >= 0) c = tc;
            for (int dy = 0; dy < s; dy++) {
                int yy = y + sy * s + dy;
                if (yy < F.cy0 || yy >= F.cy1) continue;
                for (int dx = 0; dx < s; dx++) {
                    int xx = x + sx * s + dx;
                    if (xx < F.cx0 || xx >= F.cx1) continue;
                    *((u16 *)(F.base + yy * F.bpl) + xx) = c;
                }
            }
        }
    }
}
