/* T20: tăng tốc internalGrpCopyFrameBuffer (0x144cdc). Phân tích mã máy + chứng minh tương đương: BAO-CAO-T20.md.
 *
 * Hàm gốc (10 đối số: dst FbInfo*, dx, dy, w, h, src FbInfo*, sx, sy, ctx, key) chép TỪNG ĐIỂM ẢNH theo thứ tự hàng-trước, mỗi điểm qua ~40 lệnh kiểm tra
 * (cắt theo FB nguồn/đích và hình chữ nhật clip của ctx), rồi: key màu (bỏ qua nếu bằng ctx[0x1c]), pha trộn alpha (ctx[0x20] <= 254 -> _midas_funcGrpPixelOpProc),
 * hàm gọi lại (ctx[0x2c]). Nó chỉ được gọi từ MC_grpCopyFrameBuffer 0x1450a4 (bl duy nhất; key = 0), mà MC_grpCopyFrameBuffer chỉ được gọi từ
 * Map::CopyFrameBuffer 0xb1090 (4 lần/Map::Update, nguồn = FBOff, đích = FB, 16 bit, alpha 255, không callback).
 *
 * Bản này (gắn bằng HOOK_BL 0x1450a4): khi key == 0, alpha > 254 (có dấu), không callback, độ sâu đích 16/32, và vùng nguồn/vùng đích KHÔNG chồng nhau
 * (so bao địa chỉ), thì kết quả của vòng gốc = chép nguyên từng hàng đoạn điểm hợp lệ => tính khoảng hàng/cột hợp lệ bằng công thức đóng (cùng các phép so
 * với bản gốc) rồi host memcpy (một lần cho cả khối nếu các hàng liền nhau). Mọi trường hợp khác (key, alpha, callback, độ sâu lạ, chồng vùng) -> gọi nguyên hàm gốc.
 * Không cấp phát, không đổi trạng thái nào ngoài vùng đích. */
#include "mui.h"

extern H void internalGrpCopyFrameBuffer(int dst, int dx, int dy, int w, int h, int src, int sx, int sy, void *ctx, u8 key);
extern H void *host_memcpy(void *d, const void *s, u32 n);   /* PLT memcpy của guest (0x30110, R_ARM_JUMP_SLOT 0x15e7a4) -> memcpy host của bridge */

typedef struct { int w, h, stride, depth; u8 *data; } fb_t;    /* FrameBufferInfo: +0 w, +4 h, +8 stride (byte), +0xc độ sâu bit, +0x10 con trỏ điểm ảnh */
typedef struct { u32 n_fast, n_blocks, n_rows, n_empty, n_orig_kind, n_orig_overlap; } fbcopy_st_t;
H fbcopy_st_t fbcopy_st;     /* thống kê cho harness */

static inline int imax(int a, int b) { return a > b ? a : b; }
static inline int imin(int a, int b) { return a < b ? a : b; }

H void fbcopy(int dst, int dx, int dy, int w, int h, int src, int sx, int sy, int *ctx, u8 key) {
    const fb_t *d = (const fb_t *)dst, *s = (const fb_t *)src;
    int depth = d->depth;
    /* ctx: [1]=x0 [2]=y0 [3]=x1 [4]=y1 (clip, loại trừ biên trên), [7]=key màu, [8]=alpha, [11]=callback, [12]=đối số callback (byte +4,+8,+0xc,+0x10,+0x1c,+0x20,+0x2c,+0x30) */
    if (key || ctx[8] <= 254 || ctx[11] || (depth != 16 && depth != 32) || s->stride <= 0 || d->stride <= 0) { fbcopy_st.n_orig_kind++; internalGrpCopyFrameBuffer(dst, dx, dy, w, h, src, sx, sy, ctx, key); return; }
    int sx0 = sx, sy0 = sy, wc = w, hc = h;      /* bản sao: các đường gọi lại hàm gốc phải nhận đối số NGUYÊN BẢN (hàm gốc tự cắt) */
    /* cắt đầu hàm gốc 0x144cf8..0x144d78 (kể cả chỗ sy<0 trừ vào w, đúng như mã máy) */
    if (sx0 < 0) { wc += sx0; sx0 = 0; }
    if (sy0 < 0) { wc += sy0; sy0 = 0; }
    if (wc + dx > d->w) wc = d->w - dx;
    if (hc + dy > d->h) hc = d->h - dy;
    if (hc <= 0 || wc <= 0) { fbcopy_st.n_empty++; return; }
    /* hàng k hợp lệ: sy0+k < s->h, dy+k >= 0, dy+k < d->h, ctx.y0 <= dy+k < ctx.y1 (sy0+k >= 0 luôn đúng sau khi cắt) */
    int klo = imax(imax(0, -dy), ctx[2] - dy);
    int khi = imin(imin(hc, s->h - sy0), imin(d->h - dy, ctx[4] - dy));
    /* cột i hợp lệ: sx0+i < s->w, dx+i >= 0, dx+i < d->w, ctx.x0 <= dx+i < ctx.x1 (sx0+i >= 0 luôn đúng) */
    int ilo = imax(imax(0, -dx), ctx[1] - dx);
    int ihi = imin(imin(wc, s->w - sx0), imin(d->w - dx, ctx[3] - dx));
    if (klo >= khi || ilo >= ihi) { fbcopy_st.n_empty++; return; }
    u32 es = (u32)(depth >> 3), nb = (u32)(ihi - ilo) * es, nr = (u32)(khi - klo);
    u8 *sp = s->data + (u32)(sy0 + klo) * (u32)s->stride + (u32)(sx0 + ilo) * es;
    u8 *dp = d->data + (u32)(dy + klo) * (u32)d->stride + (u32)(dx + ilo) * es;
    u8 *sp1 = sp + (nr - 1) * (u32)s->stride, *dp1 = dp + (nr - 1) * (u32)d->stride;     /* hàng cuối */
    u32 slo = (u32)(sp < sp1 ? sp : sp1), shi = (u32)(sp < sp1 ? sp1 : sp) + nb;
    u32 dlo = (u32)(dp < dp1 ? dp : dp1), dhi = (u32)(dp < dp1 ? dp1 : dp) + nb;
    /* hàm gốc đọc lại d->w/h, s->w/h, ctx[...] ở MỖI điểm: nếu vùng ghi (bao địa chỉ) chạm FbInfo nguồn/đích hoặc ctx thì kết quả phụ thuộc thứ tự => giữ đường gốc */
    u32 ps = (u32)s, pd = (u32)d, pc = (u32)ctx;
    int hit = !(dhi <= ps || ps + sizeof(fb_t) <= dlo) || !(dhi <= pd || pd + sizeof(fb_t) <= dlo) || !(dhi <= pc || pc + 0x34 <= dlo);
    if (hit || !(dhi <= slo || shi <= dlo)) { fbcopy_st.n_orig_overlap++; internalGrpCopyFrameBuffer(dst, dx, dy, w, h, src, sx, sy, ctx, key); return; }
    fbcopy_st.n_fast++;
    if (nr > 1 && nb == (u32)s->stride && nb == (u32)d->stride) { fbcopy_st.n_blocks++; host_memcpy(dp, sp, nb * nr); return; }   /* hàng liền nhau: một lần chép */
    for (u32 k = 0; k < nr; k++) { host_memcpy(dp, sp, nb); sp += (u32)s->stride; dp += (u32)d->stride; }
    fbcopy_st.n_rows += nr;
}
