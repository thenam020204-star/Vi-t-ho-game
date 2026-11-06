/* Vẽ thẳng vào khung hình RGB565 của guest (Graphic::GetFB: struct {w,h,bpl,bpp,ptr}). Không gọi hàm vẽ guest, không cấp phát. */
#include "mui.h"

mui_fbinfo mui_F;
#define F mui_F

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

/* T24a: ghi n điểm cùng màu (n > 0): căn 4 byte, 8 điểm/lần bằng stm (1 lệnh guest = 16 byte), phần đuôi từng u32/u16. Kết quả y hệt vòng cũ. */
static inline void fill16(u16 *p, int n, u32 c) {
    u32 cc = c | (c << 16), a = cc, b = cc, d = cc;
    if ((u32)p & 2) { *p++ = c; n--; }
    __asm__("" : "+r"(a), "+r"(b), "+r"(d));          /* 4 thanh ghi riêng biệt cho stm */
    while (n >= 8) { __asm__ volatile("stmia %0!, {%1, %2, %3, %4}" : "+r"(p) : "r"(cc), "r"(a), "r"(b), "r"(d) : "memory"); n -= 8; }
    while (n >= 2) { *(u32 *)p = cc; p += 2; n -= 2; }
    if (n) *p = c;
}
static void span(int y, int xl, int xr, u32 c) {
    if (y < F.cy0 || y >= F.cy1) return;
    if (xl < F.cx0) xl = F.cx0;
    if (xr > F.cx1) xr = F.cx1;
    int n = xr - xl;
    if (n <= 0) return;
    fill16((u16 *)(F.base + y * F.bpl) + xl, n, c);
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
/* T24a: vùng lớn pha màu phẳng (lớp tối nền menu, hộp hội thoại): bảng tra 65536 mục (kết quả y hệt công thức span_a cho từng giá trị điểm ảnh) dựng một lần cho mỗi
 * (màu, a5); 3 khe LRU. 3 x 128 KB bss. Mỗi điểm: ldrh + ldrh + strh thay vì ~10 lệnh. */
#define NLUT 3
static struct { u16 t[65536]; u32 c, a5; u32 used; } lut[NLUT];
static u32 lut_tick;
static u16 *lut_get(u32 c, int a5) {
    int k = -1, old = 0;
    for (int i = 0; i < NLUT; i++) {
        if (lut[i].used && lut[i].c == c && lut[i].a5 == (u32)a5) { k = i; break; }
        if (lut[i].used < lut[old].used) old = i;
    }
    lut_tick++;
    if (k < 0) {
        k = old;
        u32 s = ((c | (c << 16)) & MASK) * (u32)a5, inv = 32 - a5;
        for (u32 d = 0; d < 65536; d++) {
            u32 x = (((d | (d << 16)) & MASK) * inv + s);
            x = (x >> 5) & MASK;
            lut[k].t[d] = (u16)(x | (x >> 16));
        }
        lut[k].c = c; lut[k].a5 = (u32)a5;
    }
    lut[k].used = lut_tick;
    return lut[k].t;
}
/* băm 64 bit (2 x 32) của n byte: phát hiện nội dung ảnh nguồn đổi (không dùng cho bảo mật) */
void mui_hash(const u8 *p, int n, u32 *h1, u32 *h2) {
    u32 a = 0x811c9dc5u, b = 0x9e3779b9u;
    while (n > 0 && ((u32)p & 3)) { a = (a ^ *p) * 16777619u; b += *p; p++; n--; }
    const u32 *q = (const u32 *)p; int nw = n >> 2, i = 0;
    for (; i + 4 <= nw; i += 4) {
        u32 w0 = q[i], w1 = q[i + 1], w2 = q[i + 2], w3 = q[i + 3];
        a = (a ^ w0) * 0x01000193u; b = ((b << 5) | (b >> 27)) + w0;
        a = (a ^ w1) * 0x01000193u; b = ((b << 5) | (b >> 27)) + w1;
        a = (a ^ w2) * 0x01000193u; b = ((b << 5) | (b >> 27)) + w2;
        a = (a ^ w3) * 0x01000193u; b = ((b << 5) | (b >> 27)) + w3;
    }
    for (; i < nw; i++) { u32 w = q[i]; a = (a ^ w) * 0x01000193u; b = ((b << 5) | (b >> 27)) + w; }
    for (p += nw * 4, n -= nw * 4; n > 0; n--, p++) { a = (a ^ *p) * 16777619u; b += *p; }
    *h1 = a; *h2 = b;
}
u16 mui_cache[MUI_CACHE_PX];      /* bộ đệm dùng chung (dialog.c chân dung / mmenu.c nền): 896 KB bss; mỗi bên tự vô hiệu khi mui_cache_owner đổi */
int mui_cache_owner;
/* chỉ dựng bảng khi cùng khoá (c, a5) đã được hỏi trước đó (vòng 4 khoá gần nhất): fade-in đổi a5 mỗi khung không làm đuổi các khe */
const u16 *mui_lut(u32 c, int a5) {
    static u32 pc_[4], pa_[4]; static int pn;
    for (int i = 0; i < NLUT; i++) if (lut[i].used && lut[i].c == c && lut[i].a5 == (u32)a5) return lut_get(c, a5);
    for (int i = 0; i < 4; i++) if (pc_[i] == c && pa_[i] == (u32)a5 + 1) return lut_get(c, a5);
    pc_[pn & 3] = c; pa_[pn & 3] = (u32)a5 + 1; pn++;
    return NULL;
}
static void span_lut(int y, int xl, int xr, const u16 *t) {
    int n = xr - xl;
    u16 *p = (u16 *)(F.base + y * F.bpl) + xl;
    while (n >= 4) {
        u32 d0 = p[0], d1 = p[1], d2 = p[2], d3 = p[3];
        p[0] = t[d0]; p[1] = t[d1]; p[2] = t[d2]; p[3] = t[d3];
        p += 4; n -= 4;
    }
    while (n-- > 0) { *p = t[*p]; p++; }
}
void mui_pix(int x, int y, u32 rgb) {
    if (x < F.cx0 || x >= F.cx1 || y < F.cy0 || y >= F.cy1) return;
    *((u16 *)(F.base + y * F.bpl) + x) = c565(rgb);
}
void mui_fill(int x0, int y0, int x1, int y1, u32 rgb) {
    u32 c = c565(rgb);
    int ya = y0 < F.cy0 ? F.cy0 : y0, yb = y1 > F.cy1 ? F.cy1 : y1, xl = x0 < F.cx0 ? F.cx0 : x0, xr = x1 > F.cx1 ? F.cx1 : x1;
    int n = xr - xl, rows = yb - ya;
    if (n <= 0 || rows <= 0) return;
    u16 *p0 = (u16 *)(F.base + ya * F.bpl) + xl;
    fill16(p0, n, c);                                         /* hàng đầu bằng vòng; các hàng sau chép bằng host memcpy khi đủ dài */
    if (rows == 1) return;
    if (F.bpl == 2 * n && n * rows >= 256) {                  /* các hàng liền nhau: nhân đôi khối đã có (không chồng vùng) */
        for (int have = 1; have < rows;) { int k = rows - have < have ? rows - have : have; host_memcpy(p0 + have * n, p0, (u32)(k * n * 2)); have += k; }
    } else if (n >= 160) {
        for (int y = 1; y < rows; y++) host_memcpy((u16 *)((u8 *)p0 + y * F.bpl), p0, (u32)n * 2);
    } else {
        for (int y = 1; y < rows; y++) fill16((u16 *)((u8 *)p0 + y * F.bpl), n, c);
    }
}
void mui_fill_a(int x0, int y0, int x1, int y1, u32 rgb, int a) {
    if (a >= 255) { mui_fill(x0, y0, x1, y1, rgb); return; }
    int a5 = (a + 4) >> 3;
    u32 c = c565(rgb);
    if (a5 >= 32) { mui_fill(x0, y0, x1, y1, rgb); return; }
    if (a5 <= 0) return;
    int ya = y0 < F.cy0 ? F.cy0 : y0, yb = y1 > F.cy1 ? F.cy1 : y1, xl = x0 < F.cx0 ? F.cx0 : x0, xr = x1 > F.cx1 ? F.cx1 : x1;
    if (xr - xl > 0 && yb - ya > 0 && (xr - xl) * (yb - ya) >= 30000) {
        const u16 *t = mui_lut(c, a5);
        if (t) { for (int y = ya; y < yb; y++) span_lut(y, xl, xr, t); return; }
    }
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
    /* T24a: chỉ duyệt phần trong clip, con trỏ hàng, không kiểm biên từng điểm (kết quả y hệt vòng cũ) */
    int sxa = x >= F.cx0 ? 0 : (F.cx0 - x) / s, sxb = F.cx1 - x <= 0 ? 0 : (F.cx1 - x + s - 1) / s;
    if (sxb > w) sxb = w;
    for (int sy = 0; sy < h; sy++) {
        int yy0 = y + sy * s;
        if (yy0 >= F.cy1 || yy0 + s <= F.cy0) continue;
        int dya = yy0 < F.cy0 ? F.cy0 - yy0 : 0, dyb = yy0 + s > F.cy1 ? F.cy1 - yy0 : s, nr = dyb - dya;
        u8 *r0 = F.base + (yy0 + dya) * F.bpl;
        const u8 *row = pix + sy * stride;
        for (int sx = sxa; sx < sxb; sx++) {
            int idx = nib ? ((sx & 1) ? row[sx >> 1] & 15 : row[sx >> 1] >> 4) : row[sx];
            u32 c = pal[idx];
            if (c == skip) continue;
            if (tint >= 0) c = tc;
            int xx0 = x + sx * s, dxa = xx0 < F.cx0 ? F.cx0 - xx0 : 0, dxb = xx0 + s > F.cx1 ? F.cx1 - xx0 : s;
            u16 *p = (u16 *)r0 + xx0;
            for (int r = 0; r < nr; r++, p = (u16 *)((u8 *)p + F.bpl)) for (int i = dxa; i < dxb; i++) p[i] = (u16)c;
        }
    }
}
