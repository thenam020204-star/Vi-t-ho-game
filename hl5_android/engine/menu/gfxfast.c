/* T24b (quản đốc tự làm): thay đường chậm của hàm vẽ GỐC bằng đường nhanh tương đương (BAO-CAO-T23 P2).
 *
 * MC_grpFillRect 0x1456a4 (fb, x, y, w, h, ctx) — đọc từ mã máy 0x1456a4..0x1458c0:
 *   w<0: x+=w, w=-w; h<0: y+=h, h=-h; x<0: w+=x, x=0; y<0: h+=y, y=0; ye = min(y+h, fb.h); xe = min(x+w, fb.w);
 *   mỗi điểm (x,y) trong [x,xe)x[y,ye) có ctx[4] <= x < ctx[0xc] và ctx[8] <= y < ctx[0x10]:
 *     độ sâu 16: alpha = s32 ctx[0x20] > 254 và callback ctx[0x2c] == 0 -> *(u16*) = (u16)ctx[0x14]; độ sâu 32: *(u32*) = ctx[0x14];
 *     alpha <= 254 -> _midas_funcGrpPixelOpProc; callback != 0 -> gọi callback. Độ sâu khác: không ghi gì.
 *   fb: +0 w, +4 h, +8 stride byte, +0xc độ sâu, +0x10 dữ liệu. Hai lời gọi MIDASKernelManager::getInstance/getKernel đầu hàm chỉ đọc (kết quả bỏ đi).
 * Đường nhanh (alpha > 254, không callback, độ sâu 16/32): cùng hình chữ nhật đã cắt -> hàng đầu bằng vòng ngắn, các hàng sau host memcpy.
 * Còn lại: chạy lại lệnh đầu gốc `push {r4-r11, lr}` rồi nhảy 0x1456a8 (đường gốc nguyên vẹn).
 * Gắn: HOOK_B(0x1456a4, 0xe92d4ff0, fillrect_entry). Không cấp phát. */
#include "mui.h"

extern H void *host_memcpy(void *d, const void *s, u32 n);
typedef struct { int w, h, stride, depth; u8 *data; } gfb_t;
H u32 gfxfast_st[4];          /* harness: [0] fill nhanh, [1] fill gốc, [2] số điểm nhanh */

H int fillrect_fast(gfb_t *fb, int x, int y, int w, int h, const int *ctx) {
    int depth = fb->depth;
    if (ctx[8] <= 254 || ctx[11] != 0 || (depth != 16 && depth != 32)) { gfxfast_st[1]++; return 0; }
    if (w < 0) { x += w; w = -w; }
    if (h < 0) { y += h; h = -h; }
    if (x < 0) { w += x; x = 0; }
    if (y < 0) { h += y; y = 0; }
    int ye = y + h; if (ye >= fb->h) ye = fb->h;
    int xe = x + w; if (xe >= fb->w) xe = fb->w;
    int x0 = x > ctx[1] ? x : ctx[1], y0 = y > ctx[2] ? y : ctx[2];
    int x1 = xe < ctx[3] ? xe : ctx[3], y1 = ye < ctx[4] ? ye : ctx[4];
    gfxfast_st[0]++;
    if (x0 >= x1 || y0 >= y1) return 1;
    u32 n = (u32)(x1 - x0), es = (u32)depth >> 3, nb = n * es;
    u8 *row0 = fb->data + (u32)y0 * (u32)fb->stride + (u32)x0 * es;
    if (es == 2) { u16 c = (u16)ctx[5], *p = (u16 *)row0; for (u32 i = 0; i < n; i++) p[i] = c; }
    else { u32 c = (u32)ctx[5], *p = (u32 *)row0; for (u32 i = 0; i < n; i++) p[i] = c; }
    u8 *r = row0;
    for (int yy = y0 + 1; yy < y1; yy++) { r += fb->stride; host_memcpy(r, row0, nb); }
    gfxfast_st[2] += n * (u32)(y1 - y0);
    return 1;
}
/* điểm vào tại 0x1456a4: r0..r3 = fb,x,y,w; [sp] = h, [sp+4] = ctx */
__asm__(
".text\n.arm\n.global fillrect_entry\nfillrect_entry:\n"
"  push {r0, r1, r2, r3, r4, lr}\n  sub sp, sp, #8\n"
"  ldr r4, [sp, #32]\n  str r4, [sp]\n  ldr r4, [sp, #36]\n  str r4, [sp, #4]\n"
"  bl fillrect_fast\n  add sp, sp, #8\n  cmp r0, #0\n  pop {r0, r1, r2, r3, r4, lr}\n  bxne lr\n"
"  push {r4, r5, r6, r7, r8, r9, r10, r11, lr}\n  b MC_grpFillRect_cont\n"
);

/* Graphic::FillRectAlpha 0x4e684 (this, fb, x, y, w, h, u8 alpha, color 0xRRGGBB) — mã máy 0x4e684..0x4e928:
 *   alpha == 0 -> thoát. FrameBufferInfo(fb): w=[fb], h=[fb+4], bpl=[fb+8], ptr=[fb+0x10], byte/điểm = bpl / w (__divsi3).
 *   x<0: w+=x, x=0; y<0: h+=y, y=0; x+w > W: w = W-x; y+h > H: h = H-y. p = ptr + y*bpl + x*(bpl/W).
 *   [this+0x5c]==1 (16 bit): mỗi điểm R=(inv*r8+a*Rc)&0xF800, G=((inv*g8+a*Gc)>>5)&0x7E0, B=((inv*b8+a*Bc)&0xFFFF)>>11,
 *     r8=(p&0xF800)>>8, g8=(p&0x7E0)>>3, b8=(p<<3)&0xFF, inv=255-a. ==2 (32 bit) -> đường gốc. Khác -> không vẽ.
 * Đường nhanh 16 bit: mỗi kênh ra chỉ phụ thuộc kênh vào -> 3 bảng (32/64/32) dựng theo đúng công thức trên (trùng từng bit);
 * alpha==255 -> inv=0 -> mọi điểm cùng màu -> hàng đầu + host memcpy. Gắn HOOK_B(0x4e684, 0xe92d4ff0, fillalpha_entry). */
H int fillalpha_fast(u8 *g, gfb_t *fb, int x, int y, int w, int h, int alpha, u32 color) {
    alpha &= 255;
    if (g[0x5c] != 1) return 0;                 /* 32 bit / khác: để hàm gốc làm (gồm cả trường hợp không vẽ) */
    if (!alpha) return 1;
    int W = fb->w, Hh = fb->h, bpl = fb->stride;
    if (W <= 0) return 0;
    if (x < 0) { w += x; x = 0; }
    if (y < 0) { h += y; y = 0; }
    if (x + w > W) w = W - x;
    if (y + h > Hh) h = Hh - y;
    if (h <= 0 || w <= 0) return 1;
    int bpp = bpl / W;
    u8 *p = fb->data + y * bpl + x * bpp;
    u32 inv = 255u - (u32)alpha, aR = (u32)alpha * ((color >> 16) & 255), aG = (u32)alpha * ((color >> 8) & 255), aB = (u32)alpha * (color & 255);
    static u16 TR[32], TG[64], TB[32];
    for (u32 i = 0; i < 32; i++) { TR[i] = (u16)((inv * (i << 3) + aR) & 0xF800); TB[i] = (u16)(((inv * (i << 3) + aB) & 0xFFFF) >> 11); }
    for (u32 i = 0; i < 64; i++) TG[i] = (u16)(((inv * (i << 2) + aG) >> 5) & 0x7E0);
    gfxfast_st[3]++;
    if (alpha == 255) {
        u16 c = (u16)(TR[0] | TG[0] | TB[0]), *r0 = (u16 *)p;
        for (int i = 0; i < w; i++) r0[i] = c;
        for (int k = 1; k < h; k++) host_memcpy(p + k * bpl, r0, (u32)w * 2);
        return 1;
    }
    for (int k = 0; k < h; k++) {
        u16 *q = (u16 *)(p + k * bpl);
        for (int i = 0; i < w; i++) { u32 v = q[i]; q[i] = (u16)(TR[v >> 11] | TG[(v >> 5) & 63] | TB[v & 31]); }
    }
    return 1;
}
/* điểm vào 0x4e684: r0=this r1=fb r2=x r3=y; [sp]=w [sp+4]=h [sp+8]=alpha [sp+0xc]=màu */
__asm__(
".text\n.arm\n.global fillalpha_entry\nfillalpha_entry:\n"
"  push {r0, r1, r2, r3, r4, lr}\n  sub sp, sp, #16\n"
"  ldr r4, [sp, #40]\n  str r4, [sp]\n  ldr r4, [sp, #44]\n  str r4, [sp, #4]\n  ldr r4, [sp, #48]\n  str r4, [sp, #8]\n  ldr r4, [sp, #52]\n  str r4, [sp, #12]\n"
"  bl fillalpha_fast\n  add sp, sp, #16\n  cmp r0, #0\n  pop {r0, r1, r2, r3, r4, lr}\n  bxne lr\n"
"  push {r4, r5, r6, r7, r8, r9, r10, r11, lr}\n  b FillRectAlpha_cont\n"
);

/* Graphic::ChangeFrameBufferBlending565 0x500e8 (this, fb, color 0xRRGGBB, u8 k, u8 alpha) — mã máy 0x500e8..0x502c0:
 *   alpha == 0 -> thoát. c565 = ((color>>8)&0xF800)|((color>>5)&0x7E0)|((color&0xFF)>>3); cr = c565>>11, cg = (c565&0x7E0)>>6, cb = c565&31.
 *   bảng byte T_c[v] = this[0x6d + (k*32 + c)*32 + v]. a7 = alpha>>3, a8 = (alpha&248)>>2.
 *   mỗi điểm (quét LIỀN W*H u16 từ ptr): r=p>>11, g=(p&0x7E0)>>6, b=p&31; R=T_cr[r], G2=2*T_cg[g], B=T_cb[b];
 *   a7 > 30: ra = R<<11 | (G2<<5 & 0x7E0) | (B&31); ngược lại R=(R*a7 + r*(31-a7))>>5, B=(B*a7 + b*(31-a7))>>5, G2=(G2*a8 + 2g*(62-a8))>>6.
 * Mỗi kênh ra chỉ phụ thuộc kênh vào -> 3 bảng 32 mục dựng theo đúng công thức (trùng từng bit). HOOK_B(0x500e8, 0xe92d4ff0, cfbb_entry). */
H int cfbb_fast(u8 *g, gfb_t *fb, u32 color, int k, int alpha) {
    alpha &= 255; k &= 255;
    if (!alpha) return 1;
    int W = fb->w, Hh = fb->h;
    if (Hh <= 0 || W <= 0) return 1;
    u32 c565 = ((color >> 8) & 0xF800) | ((color >> 5) & 0x7E0) | ((color & 0xFF) >> 3);
    u32 cr = c565 >> 11, cg = (c565 & 0x7E0) >> 6, cb = c565 & 31;
    const u8 *Tr = g + 0x6d + ((u32)k * 32 + cr) * 32, *Tg = g + 0x6d + ((u32)k * 32 + cg) * 32, *Tb = g + 0x6d + ((u32)k * 32 + cb) * 32;
    int a7 = alpha >> 3, a8 = (alpha & 248) >> 2;
    static u16 LR[32], LG[32], LB[32];
    for (int v = 0; v < 32; v++) {
        int R = Tr[v], G2 = Tg[v] << 1, B = Tb[v];
        if (a7 <= 30) { R = (R * a7 + v * (31 - a7)) >> 5; B = (B * a7 + v * (31 - a7)) >> 5; G2 = (G2 * a8 + (v << 1) * (62 - a8)) >> 6; }
        LR[v] = (u16)(R << 11); LG[v] = (u16)((G2 << 5) & 0x7E0); LB[v] = (u16)(B & 31);
    }
    gfxfast_st[3] += 0x10000;
    u16 *p = (u16 *)fb->data; u32 n = (u32)W * (u32)Hh;
    for (u32 i = 0; i < n; i++) { u32 v = p[i]; p[i] = (u16)(LR[v >> 11] | LG[(v >> 6) & 31] | LB[v & 31]); }
    return 1;
}
/* điểm vào 0x500e8: r0=this r1=fb r2=color r3=k; [sp]=alpha */
__asm__(
".text\n.arm\n.global cfbb_entry\ncfbb_entry:\n"
"  push {r0, r1, r2, r3, r4, lr}\n  sub sp, sp, #8\n  ldr r4, [sp, #32]\n  str r4, [sp]\n"
"  bl cfbb_fast\n  add sp, sp, #8\n  cmp r0, #0\n  pop {r0, r1, r2, r3, r4, lr}\n  bxne lr\n"
"  push {r4, r5, r6, r7, r8, r9, r10, r11, lr}\n  b CFBB565_cont\n"
);
