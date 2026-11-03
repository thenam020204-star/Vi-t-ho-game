/* Chữ cho menu mới: đọc vi.bin qua vn_state (engine T6), đo/ngắt dòng/vẽ MODE 0 (không nén) theo bộ font 15/10/18 px.
 * Chuỗi = byte game: mã từ 2 byte (0xC9.., 0xA1..) hoặc ASCII. ';' coi như dấu cách. Không cấp phát. */
#include "mui.h"

static struct { int ok; const u8 *chars; int nchars; u8 asc[128]; } T;

static u32 r16(const u8 *p) { return p[0] | p[1] << 8; }

int mui_text_init(void) {
    if (T.ok) return T.ok > 0;
    if (vn_ensure() <= 0 || !vn_state->buf) { T.ok = -1; return 0; }
    const u8 *b = vn_state->buf, *F2 = vn_state->font[2];
    int N = vn_state->nch, nch = r16(b + 8);
    u32 off = (u32)(F2 - b) + 8 + 3 * N + 2 * N + r16(F2 + 4);
    off = (off + 3) & ~3u;
    T.chars = b + off; T.nchars = nch;
    for (int i = 0; i < 128; i++) T.asc[i] = 0xFF;
    for (int i = 0; i < nch; i++) { u32 cp = r16(T.chars + 2 * i); if (cp < 128) T.asc[cp] = i; }
    T.asc[';'] = T.asc[' '];
    T.ok = 1;
    return 1;
}

/* mã màu trong chuỗi: BFont gốc (đo ở runtime: &|${}'"`^<[ -> màu) KHÔNG vẽ ký tự mã, chỉ đổi màu cho phần chữ sau */
static const u8 RC_CODE[11] = {'&', '|', '$', '{', '}', '\'', '"', '`', '^', '<', '['};
static const u32 RC_COL[11] = {RGB(255, 156, 0), RGB(128, 128, 128), RGB(0, 0, 0), RGB(255, 255, 255), RGB(0, 103, 243), RGB(255, 204, 0),
                               RGB(0, 176, 255), RGB(106, 59, 143), RGB(223, 0, 37), RGB(132, 221, 16), RGB(64, 64, 64)};
int mui_code_color(u8 b) { for (int i = 0; i < 11; i++) if (RC_CODE[i] == b) return (int)RC_COL[i]; return -1; }
static int fidx(int px) { return px >= 18 ? 2 : px <= 10 ? 1 : 0; }

typedef struct { const u8 *F, *adv, *go, *atlas, *raw; int h, aw; } fnt_t;
static void fnt(fnt_t *f, int px) {
    const u8 *F = vn_state->font[fidx(px)];
    int N = vn_state->nch;
    f->F = F; f->raw = F + 8; f->adv = F + 8 + N; f->go = F + 8 + 3 * N; f->atlas = f->go + 2 * N;
    f->h = F[0]; f->aw = r16(F + 2);
}

/* một đơn vị chuỗi: trả số byte; *g = danh sách glyph, *n = số glyph (0 = bỏ qua) */
static int unit(const u8 *s, int i, int len, u8 *tmp, const u8 **g, int *n) {
    u8 b = s[i];
    if (b < 0x80 && mui_code_color(b) >= 0) { *n = 0; return 1; }
    if (b >= 0xC9 && i + 1 < len && s[i + 1] >= 0xA1) {
        u32 k = (b - 0xC9) * 94u + (s[i + 1] - 0xA1);
        if (k < (u32)vn_state->nwords) {
            const u8 *w = vn_state->words + r16((const u8 *)vn_state->widx + 2 * k);
            *g = w + 2; *n = w[1];
            return 2;
        }
    }
    if (b == '~' && T.asc['~'] == 0xFF) b = '-';          /* bộ font Việt không có '~' (khoảng "20~36"): thay '-' */
    if (b < 0x80 && T.asc[b] != 0xFF) { tmp[0] = T.asc[b]; *g = tmp; *n = 1; return 1; }
    *n = 0;
    return 1;
}
static int cslen(const u8 *s) { int n = 0; while (s[n]) n++; return n; }
static int gadv(const fnt_t *f, int g) { int a = (s8)f->adv[g]; return a > 0 ? a : 0; }
static int is_space(int g) { return g == T.asc[' ']; }

/* bề rộng đơn vị; skip: bỏ các dấu cách đầu */
static int uwidth(const fnt_t *f, const u8 *g, int n, int skip) {
    int w = 0;
    for (int i = 0; i < n; i++) {
        if (skip && is_space(g[i])) continue;
        skip = 0;
        w += gadv(f, g[i]);
    }
    return w;
}

int mui_sw(int px, const u8 *s, int len) {
    if (!mui_text_init()) return 0;
    if (len < 0) len = cslen(s);
    fnt_t f; fnt(&f, px);
    int w = 0; u8 tmp[1]; const u8 *g; int n;
    for (int i = 0; i < len;) { i += unit(s, i, len, tmp, &g, &n); w += uwidth(&f, g, n, 0); }
    return w;
}

static void glyph(const fnt_t *f, int x, int y, int g, u32 col) {
    int r = (s8)f->raw[g];
    if (r <= 0) return;
    int o = r16(f->go + 2 * g);
    for (int yy = 0; yy < f->h; yy++)
        for (int xx = 0; xx < r; xx++) {
            int bit = yy * f->aw + o + xx;
            if (f->atlas[bit >> 3] >> (bit & 7) & 1) mui_pix(x + xx, y + yy, col);
        }
}

static int run(int x, int y, const u8 *s, int len, int px, u32 col, int flags, int skip, const u8 *ctx) {
    if (!mui_text_init()) return 0;
    if (len < 0) len = cslen(s);
    fnt_t f; fnt(&f, px);
    u8 tmp[1]; const u8 *g; int n, w = 0;
    for (int i = 0, sk = skip; i < len;) {                 /* đo (bỏ dấu cách đầu nếu skip) */
        i += unit(s, i, len, tmp, &g, &n);
        w += uwidth(&f, g, n, sk);
        if (sk) { for (int j = 0; j < n; j++) if (!is_space(g[j])) { sk = 0; break; } }
    }
    if (flags & TF_CENTER) x -= w >> 1;
    else if (flags & TF_RIGHT) x -= w;
    u32 c0 = col;
    if (ctx) for (const u8 *q = ctx; q < s; q++) { int cc = q[0] < 0x80 ? mui_code_color(q[0]) : -1; if (cc >= 0) c0 = (u32)cc; }
    for (int pass = (flags & TF_SHADOW) ? 0 : 1; pass < 2; pass++) {
        int cx = x + (pass == 0), cy = y + (pass == 0), sk = skip;
        u32 cur = c0;
        for (int i = 0; i < len;) {
            u8 b0 = s[i];
            i += unit(s, i, len, tmp, &g, &n);
            if (!n && b0 < 0x80) { int cc = mui_code_color(b0); if (cc >= 0) cur = (u32)cc; }
            for (int j = 0; j < n; j++) {
                if (sk && is_space(g[j])) continue;
                sk = 0;
                if (!is_space(g[j])) glyph(&f, cx, cy, g[j], pass ? cur : C_INK);
                cx += gadv(&f, g[j]);
            }
        }
    }
    return w;
}
int mui_text(int x, int y, const u8 *s, int len, int px, u32 col, int flags) { return run(x, y, s, len, px, col, flags, 0, NULL); }
int mui_text_skip(int x, int y, const u8 *s, int len, int px, u32 col, int flags) { return run(x, y, s, len, px, col, flags, 1, NULL); }
/* dòng con của chuỗi `base` (s = base + start): màu khởi đầu = màu mã cuối cùng trước start */
int mui_text_line(int x, int y, const u8 *base, int start, int len, int px, u32 col, int flags) { return run(x, y, base + start, len, px, col, flags, 1, base); }

/* ngắt dòng theo từ như mockup (U.wrap): token = dấu cách đầu + chữ tới dấu cách kế (dấu cách = byte ' '/';' hoặc glyph đầu của mã từ mode 1/2).
 * Token được thêm vào dòng nếu (rộng dòng + rộng token gồm dấu cách đầu) <= maxw, ngược lại xuống dòng mới (bỏ dấu cách đầu khi vẽ: mui_text_skip).
 * Token dài hơn hộp: nằm riêng một dòng (tràn, có clip). */
int mui_wrap(int px, const u8 *s, int len, int maxw, u16 *st, u16 *ln, int maxl) {
    if (!mui_text_init()) return 0;
    if (len < 0) len = cslen(s);
    fnt_t f; fnt(&f, px);
    int nl = 0, lw = 0, start = -1;
    u8 tmp[1]; const u8 *g; int n;
    for (int i = 0; i < len;) {
        int j = i, lead = 0, body = 0, bs = 0;
        while (j < len) {
            int ub = unit(s, j, len, tmp, &g, &n);
            int w = uwidth(&f, g, n, 0), wl = w - uwidth(&f, g, n, 1);
            int ns = 0;
            for (int k = 0; k < n; k++) if (!is_space(g[k])) ns = 1;
            if (bs && n > 0 && is_space(g[0])) break;
            if (!bs) { if (ns) { lead += wl; body += w - wl; bs = 1; } else lead += w; }
            else body += w;
            j += ub;
        }
        if (start < 0) { start = i; lw = body; }
        else if (lw + lead + body <= maxw) lw += lead + body;
        else {
            if (nl < maxl) { st[nl] = start; ln[nl] = i - start; nl++; }
            start = i; lw = body;
        }
        i = j;
    }
    if (start >= 0 && nl < maxl) { st[nl] = start; ln[nl] = len - start; nl++; }
    return nl;
}
int mui_ascii(const char *a, u8 *out) { int n = 0; while (a[n]) { out[n] = a[n]; n++; } out[n] = 0; return n; }
