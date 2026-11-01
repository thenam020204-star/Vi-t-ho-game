/* Engine chữ Việt cho libHeroesLore5.so (guest ARM32). Thiết kế: BAO-CAO-RE.md §9.
 *
 * Mã Việt = 2 byte (lead 0xC9.., trail 0xA1..) chỉ số từ k=(lead-0xC9)*94+(trail-0xA1) trong vi.bin.
 *  - vn_w   : thay lệnh `ldrsb rD,[font,#70]` (bề rộng Hangul-1) ở 5 chỗ trong BFont (qua thunk): trả (bề rộng từ - 1).
 *  - vn_draw: thay `bl MX_fntDrawString` ở 0x41804 (đường vẽ 2 byte của BFont::DrawText).
 * Không biến toàn cục, không GOT, không relocation: mọi tham chiếu là PC-relative đã giải ở lúc link (code đặt trong cave
 * chết, image link đúng tại vaddr cave). Trạng thái ở bss 0x1704c0 (vn_state, 64 B).
 * Hàm/biến guest khai báo `hidden extern` và được ld.lld gán địa chỉ tuyệt đối bằng --defsym (build.sh).
 */
#include "vn_state.h"

extern H int MX_fntDrawString(int fb, int gc, int fid, int x, int y, const u8 *s, int len);
extern H void _midas_funcFntDrawPixel(int x, int y, int color, int mode);
extern H u8 *StaticUtil_LoadRes(const char *name, int *size);
extern H u32 vn_got_mxfnt[];           /* ô GOT của _mxfnt_prt: giá trị = &_mxfnt_prt (đã reloc lúc nạp) */

static const char vn_name[] = "/c/vi/vi.bin";

static u32 rd32(const u8 *p) { return p[0] | p[1] << 8 | p[2] << 16 | (u32)p[3] << 24; }
static u32 rd16(const u8 *p) { return p[0] | p[1] << 8; }

static void vn_load(vn_state_t *s) {
    int size = 0;
    const u8 *b = StaticUtil_LoadRes(vn_name, &size);
    s->status = -1;
    if (!b || size < 32 || b[0] != 'H' || b[1] != 'L' || b[2] != 'V' || b[3] != 'I' || rd16(b + 4) != 1 || b[10] != 3)
        return;
    u32 nw = rd16(b + 6), nc = rd16(b + 8);
    u32 oi = rd32(b + 12), ow = rd32(b + 16);
    if (oi > (u32)size || ow > (u32)size || oi + 2 * (nw + 1) > (u32)size) return;
    for (int i = 0; i < 3; i++) {
        u32 o = rd32(b + 20 + 4 * i);
        if (o + 8 > (u32)size) return;
        s->font[i] = b + o;
    }
    s->t10 = 0;                             /* khối chuỗi T10 (header >= 36 B: u32 off ở +32) */
    if (oi >= 36) { u32 t = rd32(b + 32); if (t && t < (u32)size) s->t10 = t; }
    s->buf = b; s->nwords = nw; s->nch = (nc + 3) & ~3;
    s->widx = (const u16 *)(b + oi);        /* oi căn 4 nên đọc u16 được; vẫn đọc từng byte để chắc */
    s->words = b + ow;
    s->status = 1;
}

/* mã Việt tại p: trả k hoặc -1 (không phải mã Việt / chưa có dữ liệu) */
static int vn_code(const u8 *p) {
    vn_state_t *s = vn_state;
    if (p[0] < 0xC9) return -1;
    if (s->status == 0) vn_load(s);
    if (s->status < 0) return -1;
    u32 t = p[1];
    if (t < 0xA1) return -1;
    u32 k = (p[0] - 0xC9) * 94u + (t - 0xA1);
    return k < (u32)s->nwords ? (int)k : -1;
}

static const u8 *vn_word(vn_state_t *s, int k) {
    return s->words + rd16((const u8 *)s->widx + 2 * k);
}

/* font (0=15px,1=10px,2=18px) và độ nén của một từ; 18 px bỏ mode 1/2 (điều kiện (d)) */
static void vn_sel(vn_state_t *s, const u8 *w, int *f, int *tight) {
    int m = w[0];
    if (s->hd) { *f = 2; *tight = 0; }
    else if (m == 2) { *f = 1; *tight = 1; }
    else { *f = 0; *tight = m == 1; }
}

static int vn_width(vn_state_t *s, int k) {
    const u8 *w = vn_word(s, k);
    int f, t, sum = 0, n = w[1];
    vn_sel(s, w, &f, &t);
    const s8 *adv = (const s8 *)(s->font[f] + 8 + s->nch * (1 + t));
    for (int i = 0; i < n; i++) {
        int a = adv[w[2 + i]];
        if (a > 0) sum += a;
    }
    return sum;
}

/* Thay cho `ldrsb rD,[font,#70]`: bề rộng từ - 1. cur==0 (đầu dòng): kẹp để từ rộng hơn hộp vẫn vừa một dòng trống
 * (điều kiện (a): vòng GetTextFieldPageLen / DrawText phải luôn tiến). orig = giá trị gốc (mã Hangul thật). */
int vn_w(const u8 *p, int orig, int maxw, int cur) {
    int k = vn_code(p);
    if (k < 0) return orig;
    int w = vn_width(vn_state, k) - 1;
    if (w < 0) w = 0;
    if (w > 127) w = 127;
    if (cur == 0) {
        int lim = maxw - 1;
        if (lim < 0) lim = 0;
        if (w > lim) w = lim;
    }
    return w;
}

/* Thay cho `bl MX_fntDrawString` ở 0x41804 (cùng ABI: r0-r3, [sp]=y, [sp+4]=str, [sp+8]=len) */
int vn_draw(int fb, int gc, int fid, int x, int y, const u8 *str, int len) {
    int k = -1;
    if (len == 2) k = vn_code(str);
    if (k < 0) return MX_fntDrawString(fb, gc, fid, x, y, str, len);
    /* chạy phần thiết lập của MX_fntDrawString (biến toàn cục fb/clip, font hiện tại) mà không vẽ gì (len=0) */
    MX_fntDrawString(fb, gc, fid, x, y, str, 0);
    const u32 *obj = *(const u32 **)vn_got_mxfnt[0];     /* *_mxfnt_prt: màu +0x40, mode +0x24 */
    int color = obj[0x40 / 4], mode = obj[0x24 / 4];
    vn_state_t *S = vn_state;
    const u8 *w = vn_word(S, k);
    int f, t, n = w[1];
    vn_sel(S, w, &f, &t);
    const u8 *F = S->font[f];
    int h = F[0], yo = 9 - F[1], aw = rd16(F + 2), N = S->nch;
    const u8 *raw = F + 8, *adv = raw + N * (1 + t), *go = F + 8 + 3 * N, *atlas = go + 2 * N;
    for (int i = 0; i < n; i++) {
        int g = w[2 + i], r = (s8)raw[g], a = (s8)adv[g];
        if (r > 0) {
            int o = rd16(go + 2 * g);
            for (int yy = 0; yy < h; yy++)
                for (int xx = 0; xx < r; xx++) {
                    int bit = yy * aw + o + xx;
                    if (atlas[bit >> 3] >> (bit & 7) & 1) _midas_funcFntDrawPixel(x + xx, y + yo + yy, color, mode);
                }
        }
        if (a > 0) x += a;
    }
    return 1;
}

/* ---- thunk: thay `ldrsb rD,[F,#70]`. Giữ nguyên mọi thanh ghi trừ rD và lr; gọi vn_w(p, orig, maxw, cur). ---- */
__asm__(
".text\n.arm\n"
".macro THUNK name, F, D, A, B, CUR, MAXLD\n"
".global \\name\n\\name:\n"
"  push {r0-r4, r10, r12, lr}\n"
"  add r0, \\A, \\B\n"
"  ldrsb r1, [\\F, #70]\n"
"  \\MAXLD\n"
"  mov r3, \\CUR\n"
"  mov r4, sp\n"
"  bic sp, sp, #7\n"
"  bl vn_w\n"
"  mov sp, r4\n"
"  str r0, [sp, #(4*\\D)]\n"
"  pop {r0-r4, r10, r12, lr}\n"
"  bx lr\n"
".endm\n"
/* 0x40ee8 GetWidth: rD=r0 F=r9 p=r10+r5; không có hộp/cur */
"THUNK vn_th0, r9, 0, r10, r5, #1, \"mvn r2, #0x80000000\"\n"
/* 0x40f6c GetByteLenPrintable: rD=r3 F=r6 p=r5+r8 cur=r4 maxw=[sp_orig+0x30] (sp_orig = sp+32) */
"THUNK vn_th1, r6, 3, r5, r8, r4, \"ldr r2, [sp, #0x50]\"\n"
/* 0x41510 DrawText: rD=r3 F=r5 p=r6+r4 cur=r7 maxw=[sp_orig+0x58] */
"THUNK vn_th2, r5, 3, r6, r4, r7, \"ldr r2, [sp, #0x78]\"\n"
/* 0x4170c */
"THUNK vn_th3, r5, 3, r6, r4, r7, \"ldr r2, [sp, #0x78]\"\n"
/* 0x4178c: rD=r10 */
"THUNK vn_th4, r5, 5, r6, r4, r7, \"ldr r2, [sp, #0x78]\"\n"
);
