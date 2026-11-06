/* T21 (quản đốc tự làm): camera luôn đặt hero ở giữa + "map đệm" ngoài mép map + vòng sáng dưới chân hero.
 *
 * Bằng chứng (so.dis + harness/t21/p1.py, 600x270, giữ LEFT 120 tick):
 *  - Map* = [gv+5200]; camera trái/trên = s32 [Map+0xd0]/[Map+0xd4] (Map::MapMove 0xafc54 ghi 0xafe6c/0xafe80);
 *    kích thước map px = s16 [Map+112]/[Map+114]; hero x,y = s32 [HERO+4]/[HERO+8].
 *  - Giữa map: hero_x - cam_x = W/2 và hero_y - cam_y = H/2 ĐÚNG (không trễ camera). Ở mép trái: cam_x = 0, hero lệch (72 thay vì 300).
 *  - Battle::Paint 0x4512c: Map::Paint (bl 0x451c4; chép FBOff -> FB cả màn mỗi khung, xem BAO-CAO-T20) -> vật thể/lớp map -> hạt
 *    -> HERO::RestoreColorBack (bl 0x4581c) = hết phần vẽ THẾ GIỚI; sau đó mới tới UI (chữ, DrawGameUI, ...). StateGame touch không đọc camera.
 * Cách làm (KHÔNG đổi logic/camera của game):
 *  - 0x4581c: sau RestoreColorBack, nếu camera đang chạm mép (cam <= 0 hoặc cam >= map - màn) thì dịch cả khung hình
 *    (dx = cam_x - (hero_x - W/2), dy tương tự) để hero về giữa, phần lộ ra vẽ nền tối + đom đóm (toạ độ thế giới, cuộn theo camera).
 *    Giữa map dx = dy = 0 => khung hình giữ nguyên từng byte.
 *  - 0x451c4: sau Map::Paint vẽ vòng sáng elip dưới chân hero (trước vật thể/lớp map => bị vật đứng trước che đúng thứ tự).
 * Không cấp phát (bộ đệm static). Không ghi biến game nào. Bỏ qua khi đang hội thoại/hoạt cảnh ([EventProc+0x75] != 0). */
#include "mui.h"

extern H void Map_Paint(void *map);
extern H void HERO_RestoreColorBack(void *hero);
extern H void *host_memcpy(void *d, const void *s, u32 n);

typedef struct { int w, h, stride, depth; u8 *data; } fbi_t;   /* Graphic::GetFB: +0 w, +4 h, +8 stride byte, +0xc bit, +0x10 điểm ảnh (xem fbcopy.c) */

#define GV_HERO 5180
#define GV_MAP 5200
#define GV_EVENT 5204
#define MAXW 2048
#define BG565 0x0862u                 /* nền tối (≈ #080C10) */

H int camc_on = 1;                     /* 1 = bật (mặc định) */
H int camc_last_dx, camc_last_dy;      /* harness */
static u16 rowbuf[MAXW];               /* hàng tạm khi dịch ngang cùng hàng */
static u16 bgrow[MAXW];                /* hàng nền tối dựng sẵn */
static int bgrow_ok;

static inline int s32at(const u8 *p, int o) { return *(const int *)(p + o); }
static inline int s16at(const u8 *p, int o) { return *(const short *)(p + o); }

static fbi_t *fb_get(void) {
    fbi_t *f = (fbi_t *)Graphic_GetFB(_lcdG);
    if (!f || f->depth != 16 || f->w <= 0 || f->h <= 0 || f->w > MAXW || f->stride < f->w * 2) return 0;
    return f;
}
static int playing(void) {
    u8 *ev = *(u8 **)(gv + GV_EVENT);
    if (ev && ev[0x75]) return 0;
    return *(u8 **)(gv + GV_MAP) && *(u8 **)(gv + GV_HERO);
}
static u32 hsh(u32 a, u32 b) { u32 h = a * 0x9E3779B1u ^ (b + 0x7F4A7C15u) * 0x85EBCA77u; h ^= h >> 15; h *= 0x2C1B3C6Du; h ^= h >> 12; return h; }
static inline u16 blend565(u16 d, u32 r, u32 g, u32 b, int a5) {   /* a5 0..32 */
    u32 dr = d >> 11, dg = (d >> 5) & 63, db = d & 31;
    dr += ((int)r - (int)dr) * a5 / 32; dg += ((int)g - (int)dg) * a5 / 32; db += ((int)b - (int)db) * a5 / 32;
    return (u16)((dr << 11) | (dg << 5) | db);
}
static void put_a(fbi_t *f, int x, int y, int a5, int x0, int y0, int x1, int y1) {
    if (x < x0 || y < y0 || x >= x1 || y >= y1 || a5 <= 0) return;
    u16 *p = (u16 *)(f->data + y * f->stride) + x;
    *p = blend565(*p, 28, 62, 12, a5 > 32 ? 32 : a5);   /* vàng-xanh đom đóm (RGB565 ≈ #E0F860) */
}
/* tô vùng [x0,x1)x[y0,y1) (toạ độ màn sau khi dịch) bằng nền tối + đom đóm; wx0/wy0 = toạ độ thế giới của điểm màn (0,0) */
static void fill_buffer(fbi_t *f, int x0, int y0, int x1, int y1, int wx0, int wy0, u32 t) {
    if (x0 < 0) x0 = 0; if (y0 < 0) y0 = 0; if (x1 > f->w) x1 = f->w; if (y1 > f->h) y1 = f->h;
    if (x0 >= x1 || y0 >= y1) return;
    if (!bgrow_ok) { for (int i = 0; i < MAXW; i++) bgrow[i] = BG565; bgrow_ok = 1; }
    for (int y = y0; y < y1; y++) host_memcpy((u16 *)(f->data + y * f->stride) + x0, bgrow, (u32)(x1 - x0) * 2);
    /* đom đóm: lưới thế giới 40 px, mỗi ô ~1/3 có một con; trôi nhẹ + nhấp nháy theo bộ đếm khung t */
    const int C = 32;
    int cx0 = (wx0 + x0) / C - 1, cx1 = (wx0 + x1) / C + 1, cy0 = (wy0 + y0) / C - 1, cy1 = (wy0 + y1) / C + 1;
    if (wx0 + x0 < 0) cx0 = (wx0 + x0 - C + 1) / C - 1;
    if (wy0 + y0 < 0) cy0 = (wy0 + y0 - C + 1) / C - 1;
    for (int cy = cy0; cy <= cy1; cy++) for (int cx = cx0; cx <= cx1; cx++) {
        u32 h = hsh((u32)cx, (u32)cy);
        if ((h % 3u) != 0) continue;
        int ph = (int)((h >> 8) & 63), per = 48 + (int)((h >> 14) & 31);
        int k = (int)((t + (u32)ph) % (u32)per), tri = k < per / 2 ? k : per - k;     /* 0..per/2 */
        int a = tri * 2 * 40 / (per / 2) - 12; if (a <= 0) continue;                    /* tắt một phần chu kỳ */
        int dxw = ((int)((t / 3 + (h >> 20)) % 24) - 12), dyw = ((int)((t / 4 + (h >> 25)) % 16) - 8);
        dxw = dxw < 0 ? -dxw - 6 : dxw - 6; dyw = dyw < 0 ? -dyw - 4 : dyw - 4;            /* sóng tam giác */
        int sx = cx * C + (int)((h >> 2) % (u32)C) + dxw - wx0, sy = cy * C + (int)((h >> 16) % (u32)C) + dyw - wy0;
        put_a(f, sx, sy, a, x0, y0, x1, y1);
        put_a(f, sx - 1, sy, a / 2, x0, y0, x1, y1); put_a(f, sx + 1, sy, a / 2, x0, y0, x1, y1);
        put_a(f, sx, sy - 1, a / 2, x0, y0, x1, y1); put_a(f, sx, sy + 1, a / 2, x0, y0, x1, y1);
        put_a(f, sx - 1, sy - 1, a / 5, x0, y0, x1, y1); put_a(f, sx + 1, sy + 1, a / 5, x0, y0, x1, y1); put_a(f, sx + 1, sy - 1, a / 5, x0, y0, x1, y1); put_a(f, sx - 1, sy + 1, a / 5, x0, y0, x1, y1);
    }
}
/* Chế độ 1: vẽ lại CHÍNH MAP (nền, không vật thể) phản chiếu qua mép, tối 25%.
 * Nguồn = FBOff (bộ đệm vòng nền map; vật thể vẽ vào FB, không vào FBOff). Ánh xạ theo Map::Update 0xb10bc:
 * ox = [Map+0xd8] mod W, oy = [Map+0xdc] mod H, rung s16 [Map+282]/[Map+284]; điểm màn (sx,sy) trước khi dịch = FBOff[(sx - r282 + ox) mod W][(sy - r284 + oy) mod H]. */
static int refl(int v, int n) { int p = 2 * n; int m = v % p; if (m < 0) m += p; return m < n ? m : p - 1 - m; }
static inline int wrapi(int v, int n) { v %= n; return v < 0 ? v + n : v; }
static void fill_mirror(fbi_t *f, const fbi_t *off, const u8 *map, int x0, int y0, int x1, int y1, int wx0, int wy0, int camx, int camy, u32 t) {
    if (x0 < 0) x0 = 0; if (y0 < 0) y0 = 0; if (x1 > f->w) x1 = f->w; if (y1 > f->h) y1 = f->h;
    if (x0 >= x1 || y0 >= y1) return;
    int W = f->w, Hh = f->h, mw = s16at(map, 112), mh = s16at(map, 114);
    if (!off || off->depth != 16 || off->w != W || off->h != Hh || mw <= 0 || mh <= 0) { fill_buffer(f, x0, y0, x1, y1, wx0, wy0, t); return; }
    int ox = wrapi(s32at(map, 0xd8), W), oy = wrapi(s32at(map, 0xdc), Hh), rx = s16at(map, 282), ry = s16at(map, 284);
    if (!bgrow_ok) { for (int i = 0; i < MAXW; i++) bgrow[i] = BG565; bgrow_ok = 1; }
    for (int y = y0; y < y1; y++) {
        u16 *dst = (u16 *)(f->data + y * f->stride);
        int sy = refl(wy0 + y, mh) - camy;                       /* hàng màn (trước dịch) chứa hàng thế giới phản chiếu */
        if (sy < 0 || sy >= Hh) { host_memcpy(dst + x0, bgrow, (u32)(x1 - x0) * 2); continue; }
        const u16 *src = (const u16 *)(off->data + wrapi(sy - ry + oy, Hh) * off->stride);
        int wx = wx0 + x0, r = refl(wx, mw), dir = (wrapi(wx, 2 * mw) < mw) ? 1 : -1;
        for (int x = x0; x < x1; x++) {
            int sx = r - camx;
            u16 p = (sx >= 0 && sx < W) ? src[wrapi(sx - rx + ox, W)] : (u16)BG565;
            dst[x] = (u16)(p - ((p >> 2) & 0x39E7));              /* tối 25% (từng kênh) */
            r += dir; if (r >= mw) { r = mw - 1; dir = -1; } else if (r < 0) { r = 0; dir = 1; }
        }
    }
}
H int camc_mode = 1;                   /* 0 = nền tối + đom đóm, 1 = phản chiếu chính map (không vật thể) — người dùng chọn 06/11 */
/* dịch khung hình (dx>0: sang phải, dy>0: xuống) */
static void shift_fb(fbi_t *f, int dx, int dy) {
    int w = f->w, h = f->h, n = w - (dx < 0 ? -dx : dx);
    if (n <= 0 || (dy < 0 ? -dy : dy) >= h) return;
    int sxo = dx > 0 ? 0 : -dx, dxo = dx > 0 ? dx : 0;
    if (dy > 0) {
        for (int y = h - 1; y >= dy; y--) host_memcpy((u16 *)(f->data + y * f->stride) + dxo, (u16 *)(f->data + (y - dy) * f->stride) + sxo, (u32)n * 2);
    } else if (dy < 0) {
        for (int y = 0; y < h + dy; y++) host_memcpy((u16 *)(f->data + y * f->stride) + dxo, (u16 *)(f->data + (y - dy) * f->stride) + sxo, (u32)n * 2);
    } else if (dx) {
        for (int y = 0; y < h; y++) {
            u16 *row = (u16 *)(f->data + y * f->stride);
            host_memcpy(rowbuf, row + sxo, (u32)n * 2); host_memcpy(row + dxo, rowbuf, (u32)n * 2);
        }
    }
}
/* 0x4581c: bl HERO::RestoreColorBack (hết phần vẽ thế giới trong Battle::Paint) */
void camc_world_done(void *hero) {
    HERO_RestoreColorBack(hero);
    camc_last_dx = camc_last_dy = 0;
    if (!camc_on || !playing()) return;
    fbi_t *f = fb_get(); if (!f) return;
    u8 *map = *(u8 **)(gv + GV_MAP), *hp = *(u8 **)(gv + GV_HERO);
    int camx = s32at(map, 0xd0), camy = s32at(map, 0xd4), mw = s16at(map, 112), mh = s16at(map, 114);
    int hx = s32at(hp, 4), hy = s32at(hp, 8), SW = f->w, SH = f->h;
    int dx = camx - (hx - SW / 2), dy = camy - (hy - SH / 2);
    if (!(camx <= 0 || camx >= mw - SW)) dx = 0;          /* chỉ bù phần camera bị kẹp ở mép */
    if (!(camy <= 0 || camy >= mh - SH)) dy = 0;
    if (dx > SW / 2) dx = SW / 2; if (dx < -SW / 2) dx = -SW / 2;
    if (dy > SH / 2) dy = SH / 2; if (dy < -SH / 2) dy = -SH / 2;
    if (!dx && !dy) return;
    camc_last_dx = dx; camc_last_dy = dy;
    shift_fb(f, dx, dy);
    u32 t = *(u32 *)(gv + 0x130);
    int wx0 = camx - dx, wy0 = camy - dy;                /* thế giới của điểm màn (0,0) sau khi dịch */
    /* mọi điểm màn nằm NGOÀI biên map (toạ độ thế giới < 0 hoặc >= kích thước map) = map đệm; gồm cả dải đen bản gốc tự tô khi map hẹp hơn màn (1200x540) */
    int lx = -wx0, rx = mw - wx0, ty = -wy0, by = mh - wy0;
    if (camc_mode == 1) {
        const fbi_t *off = (const fbi_t *)Graphic_GetFBOff(_lcdG);
        if (lx > 0) fill_mirror(f, off, map, 0, 0, lx, SH, wx0, wy0, camx, camy, t);
        if (rx < SW) fill_mirror(f, off, map, rx, 0, SW, SH, wx0, wy0, camx, camy, t);
        if (ty > 0) fill_mirror(f, off, map, lx > 0 ? lx : 0, 0, rx < SW ? rx : SW, ty, wx0, wy0, camx, camy, t);
        if (by < SH) fill_mirror(f, off, map, lx > 0 ? lx : 0, by, rx < SW ? rx : SW, SH, wx0, wy0, camx, camy, t);
        return;
    }
    if (lx > 0) fill_buffer(f, 0, 0, lx, SH, wx0, wy0, t);
    if (rx < SW) fill_buffer(f, rx, 0, SW, SH, wx0, wy0, t);
    if (ty > 0) fill_buffer(f, 0, 0, SW, ty, wx0, wy0, t);
    if (by < SH) fill_buffer(f, 0, by, SW, SH, wx0, wy0, t);
}
/* 0x451c4: bl Map::Paint -> vòng sáng dưới chân hero (elip, vàng nhạt, nhịp chậm) */
H int ring_on = 1;
void camc_map_paint(void *map) {
    Map_Paint(map);
    if (!ring_on || !playing()) return;
    fbi_t *f = fb_get(); if (!f) return;
    u8 *m = *(u8 **)(gv + GV_MAP), *hp = *(u8 **)(gv + GV_HERO);
    int cx = s32at(hp, 4) - s32at(m, 0xd0), cy = s32at(hp, 8) - s32at(m, 0xd4);
    const int RX = 15, RY = 6;
    u32 t = *(u32 *)(gv + 0x130); int k = (int)(t % 40), tri = k < 20 ? k : 40 - k;   /* 0..20 */
    int aring = 18 + tri / 2, afill = 5 + tri / 5;                                      /* /32 */
    for (int y = -RY - 1; y <= RY + 1; y++) {
        int py = cy + y; if (py < 0 || py >= f->h) continue;
        u16 *row = (u16 *)(f->data + py * f->stride);
        for (int x = -RX - 1; x <= RX + 1; x++) {
            int px = cx + x; if (px < 0 || px >= f->w) continue;
            int d = (x * x * RY * RY + y * y * RX * RX) * 64 / (RX * RX * RY * RY);    /* 64 = trên biên elip */
            if (d > 80) continue;
            int e = d - 64; if (e < 0) e = -e;
            int a = (e < 16 ? aring * (16 - e) / 16 : 0) + (d < 64 ? afill : 0);          /* viền mềm quanh biên + lõi mờ */
            if (a <= 0) continue;
            row[px] = blend565(row[px], 31, 54, 12, a > 32 ? 32 : a);                  /* vàng ấm ≈ #F8D860 */
        }
    }
}
