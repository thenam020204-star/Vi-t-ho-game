/* T18b: nội suy khung hình (mốc 1: camera + hero). Thiết kế: T18-THIET-KE.md; kết quả dò: BAO-CAO-T18.md (T18a, T18b).
 *
 * Nhịp logic GIỮ NGUYÊN (22 Hz). Khi cờ bật, chỉ trong StateGame chơi thường:
 *   - tick thật chạy nguyên vẹn (KeyPressed -> StateMgr -> Process -> Paint -> EndProc -> Flush); chỉ có Paint của tick được vẽ với hero ở vị trí
 *     tick TRƯỚC (alpha = 0) và camera dẫn xuất từ vị trí đó ("hiển thị trễ 1 tick" của kiến trúc Fix Your Timestep);
 *   - timer được đặt lại tối đa 16 ms (vsync) thay vì tới hạn tick; mỗi lần TimerCb chạy mà chưa tới hạn tick = "khung trung gian":
 *     hero = lerp(prev, curr, alpha), Map::MapMove() (camera + bộ đệm cuộn nền tự dẫn xuất từ hero, đúng cách game vẫn làm), mainPaint, Flush,
 *     rồi khôi phục hero thật + Map::MapMove() lần nữa (bộ đệm nền trở về đúng cửa sổ của vị trí thật). Không phím/logic/âm thanh/EndProc.
 *   - thích nghi CPU: chỉ vẽ khi now + 1.3*t_paint < hạn tick; máy chậm tự rơi về hành vi gốc.
 * Cờ tắt (mặc định): interp_settimer chuyển thẳng tham số cho CUSTOM_extSetTimer gốc, armed luôn 0 => interp_entry trả 0 ngay (đường gốc).
 * Không cấp phát, không dữ liệu khởi tạo (bss). */
#include "mui.h"

extern H long long MC_knlCurrentTime(void);
extern H int CUSTOM_extSetTimer(void *t, long long ms, void *ctx);
extern H void CUSTOM_grpFlushLcd(int a, int fb, int b, int c, int w, int h);
extern H void mainPaint(void);
extern H void Map_MapMove(void *map);
extern H void Monster_IncreaseTempFrame(void *m);
extern H int HERO_IsShake(void *hero);
extern H int HERO_IsShakeHit(void *hero);
extern H int Graphic_GetWidth(u32 g);
extern H int Graphic_GetHeight(u32 g);
extern H u8 internalTimerNanoCheck;
extern H u8 StateGame_vtable[];
extern H void *host_memcpy(void *d, const void *s, u32 n);
typedef struct { int w, h, stride, depth; u8 *data; } gfb_t;
static int imoved;
extern H int StaticUtil_FileRead(const char *name, char *buf, int n);
extern H int StaticUtil_FileWrite(const char *name, const char *buf, int n);

#define GV_TOP 0x244          /* gv+0x244: state trên cùng */
#define GV_MAP 5200           /* gv+5200: Map* (Battle::Paint: [gv+5184+16]) */
#define GV_EVENT 5204         /* gv+5204: EventProc* ([+0x75] != 0: hội thoại/hoạt cảnh) */
#define GV_TICK0 7584         /* gv+7584: thời điểm bắt đầu tick (TimerCb lưu MC_knlCurrentTime, 64 bit) */
#define MAXLERP 96            /* |delta| lớn hơn (px thế giới) => dịch chuyển tức thời: không nội suy */
#define SLICE 16              /* ms: trễ timer giữa các khung trung gian (1 vsync) */

enum { F_UNK = 0, F_OFF = 1, F_ON = 2 };
typedef struct {
    u32 flag;                 /* F_* (0 = chưa đọc interp.txt) */
    u32 armed;                /* 1 = đang trong khoảng giữa 2 tick với timer 16 ms */
    u32 lag_pending;          /* tick này đã vẽ ở chế độ trễ (do interp_tick_paint) -> settimer được phép bật armed */
    u32 mid;                  /* 1 trong khung trung gian: chặn Monster::IncreaseTempFrame */
    u32 D;                    /* hạn tick kế (ms, 32 bit thấp) */
    u32 T;                    /* thời điểm khung tick vừa vẽ xong */
    u32 P;                    /* D - T (ms) */
    u32 Pexp;                 /* chu kỳ tick dự kiến (từ tick trước) để quyết định bật chế độ trễ */
    u32 tp;                   /* t_paint (ms, trung bình mũ) */
    u32 valid;                /* p/c hợp lệ */
    void *hero, *map;
    int p[2], c[2];           /* hero (x,y) tick trước / tick này */
    u32 fx130;                /* gv+0x130 lúc Paint của tick (EndProc tăng nó sau đó) */
    /* thống kê (harness) */
    u32 n_mid, n_tick_lag, n_skip;
} interp_t;
H interp_t interp_st;
#define I interp_st

static inline u32 now32(void) { return (u32)MC_knlCurrentTime(); }
static inline int sdiff(u32 a, u32 b) { return (int)(a - b); }

static void flag_load(void) {
    char b[4] = {0, 0, 0, 0};
    int n = StaticUtil_FileRead("interp.txt", b, 2);
    /* người dùng 06/11: nội suy BẬT mặc định. Chỉ tệp ghi '0' mới tắt; không có tệp / nội dung khác = bật. */
    I.flag = (n > 0 && b[0] == '0' && (n < 2 || (u8)b[1] <= ' ')) ? F_OFF : F_ON;
}
/* cho mục Cài đặt (đề xuất): bật/tắt + ghi tệp. Có hiệu lực từ tick kế. */
H void interp_set(int on) {
    I.flag = on ? F_ON : F_OFF;
    StaticUtil_FileWrite("interp.txt", on ? "1" : "0", 1);
}
H int interp_get(void) { if (!I.flag) flag_load(); return I.flag == F_ON; }

/* StateGame chơi thường, không hội thoại/hoạt cảnh/rung màn hình. */
static int eligible(void) {
    u32 *top = *(u32 **)(gv + GV_TOP);
    u8 *hero = HEROP(), *map = *(u8 **)(gv + GV_MAP), *ev = *(u8 **)(gv + GV_EVENT);
    if (!top || !hero || !map) return 0;
    if (top[0] != (u32)StateGame_vtable + 8) return 0;
    if (*((s8 *)top + 0xc) != 0) return 0;
    /* T25: hoạt cảnh/hội thoại ([EventProc+0x75]) ĐƯỢC nội suy (camera sự kiện xử lý trong render) */
    if (map[0x115]) return 0;
    if (HERO_IsShake(hero) || HERO_IsShakeHit(hero)) return 0;
    return 1;
}

static inline int lerp(int p, int c, int a) { return p + (((c - p) * a + 128) >> 8); }

/* T25: nội suy MỌI đối tượng trong 5 danh sách vẽ (Battle::Paint: LinkedList* tại gv+5536..5552; node: next=[+4], obj=[+8]; OBJECT x,y = s32 [+4]/[+8])
 * + camera. Chơi thường ([EP+0x75]==0): camera bám hero (Map::MapMove nhánh 0xb0198) => chỉ cần đặt hero nội suy rồi MapMove (như T18).
 * Sự kiện ([EP+0x75]!=0): Map::MapMove 0xafcb0..: [EP+0x686]==0 -> đích = ([EP+0x68c],[EP+0x690]) (0xaffb8), ==1 && [EP+0x688]==255 -> đích = hero (0xb0814),
 * khác -> đối tượng sự kiện; tâm camera = [Map+0xd0] + W/2 dịch về đích tối đa s16 [Map+184] mỗi lần. Khung nội suy: đặt tạm 686=0, đích = tâm nội suy,
 * [Map+184]=255 -> MapMove; trả về: đích = tâm thật -> MapMove; rồi khôi phục 686/68c/690/[Map+184]/[Map+0xc0]/[Map+0xc4].
 * Khung trung gian (I.mid) còn khôi phục: Map+0x14c (fade, Map::MapEffect), Map+0x160 (Map::RepaintMapEffect), CHAR+0xe0..e7 (CHAR::CharSprEffect,
 * ghi lại qua HOOK_B 0x48344), ParticleMgr +0xf8/+0x100 (HOOK_BL 0x45804). Bằng chứng: harness/t25/pdiff.py + wtrace.py (BAO-CAO-T25.md). */
#define GV_LISTS 5536
#define MAXOBJ 192
typedef struct { u8 *o; int p[2], c[2]; } iobj_t;
static iobj_t OB[MAXOBJ], OB2[MAXOBJ];
static int nob, camp[2], camc[2], evm;
static struct { u8 *o; u8 b[8]; } CSR[64];
static int ncsr, rec;
static int fbw(void) { return Graphic_GetWidth(_lcdG); }
static int fbh(void) { return Graphic_GetHeight(_lcdG); }
static int in_event(void) { u8 *ev = *(u8 **)(gv + GV_EVENT); return ev && ev[0x75]; }
/* ghi vị trí thật của tick (gọi ở Paint của tick, trước khi vẽ) */
static void capture(void) {
    int n = 0;
    for (int l = 0; l < 5; l++) {
        u8 *L = *(u8 **)(gv + GV_LISTS + 4 * l); if (!L) continue;
        u8 *node = *(u8 **)L; int guard = 0;
        while (node && n < MAXOBJ && guard++ < 1024) {
            u8 *o = *(u8 **)(node + 8);
            if (o) {
                int x = *(int *)(o + 4), y = *(int *)(o + 8), k;
                iobj_t *e = &OB2[n]; e->o = o; e->c[0] = x; e->c[1] = y; e->p[0] = x; e->p[1] = y;
                k = (n < nob && OB[n].o == o) ? n : -1;
                if (k < 0) for (int j = 0; j < nob; j++) if (OB[j].o == o) { k = j; break; }
                if (k >= 0) { int dx = x - OB[k].c[0], dy = y - OB[k].c[1];
                    if (dx < MAXLERP && -dx < MAXLERP && dy < MAXLERP && -dy < MAXLERP) { e->p[0] = OB[k].c[0]; e->p[1] = OB[k].c[1]; } }
                n++;
            }
            node = *(u8 **)(node + 4);
        }
    }
    for (int i = 0; i < n; i++) OB[i] = OB2[i];
    nob = n;
    u8 *map = *(u8 **)(gv + GV_MAP);
    int cx = *(int *)(map + 0xd0) + fbw() / 2, cy = *(int *)(map + 0xd4) + fbh() / 2;
    int ev = in_event();
    if (ev == evm && (cx - camc[0]) < 256 && (camc[0] - cx) < 256 && (cy - camc[1]) < 256 && (camc[1] - cy) < 256) { camp[0] = camc[0]; camp[1] = camc[1]; }
    else { camp[0] = cx; camp[1] = cy; }
    camc[0] = cx; camc[1] = cy; evm = ev;
}
static void cam_event_to(u8 *map, u8 *ev, int x, int y) {   /* 686=0: MapMove đi tới đích (68c,690) với bước 255 */
    ev[0x686] = 0; *(int *)(ev + 0x68c) = x; *(int *)(ev + 0x690) = y; *(short *)(map + 184) = 255;
    Map_MapMove(map);
}
/* Mọi lần vẽ nội suy (khung trễ alpha=0 ở tick và khung trung gian) đều chạy với I.mid=1 và KHÔI PHỤC TOÀN BỘ sau khi vẽ:
 * struct Map (4284 B: operator new trước Map::Map, 0xdf944) + EventProc (1788 B, 0xdf96c) + điểm ảnh FBOff (bộ đệm vòng nền; MapMove vẽ ô vào đó)
 * bằng host memcpy, vị trí đối tượng, CHAR+0xe0..e7, ParticleMgr +0xf8/+0x100; Monster::IncreaseTempFrame bị chặn. Paint THẬT của tick luôn chạy nguyên bản. */
#define SOFF_MAX (2400u * 1024u)
static u8 SMAP[4284], SEP[1788], SOFF[SOFF_MAX];
static int render(int a) {
    u8 *map = *(u8 **)(gv + GV_MAP), *ev = *(u8 **)(gv + GV_EVENT);
    const gfb_t *off = (const gfb_t *)Graphic_GetFBOff(_lcdG);
    if (!map || !off || !off->data || off->stride <= 0 || off->h <= 0) return 0;
    u32 n = (u32)off->stride * (u32)off->h; if (n > SOFF_MAX) return 0;
    int event = ev && ev[0x75];
    host_memcpy(SMAP, map, sizeof SMAP); if (ev) host_memcpy(SEP, ev, sizeof SEP); host_memcpy(SOFF, off->data, n);
    for (int i = 0; i < nob; i++) { int *q = (int *)(OB[i].o + 4); q[0] = lerp(OB[i].p[0], OB[i].c[0], a); q[1] = lerp(OB[i].p[1], OB[i].c[1], a); }
    if (event) cam_event_to(map, ev, lerp(camp[0], camc[0], a), lerp(camp[1], camc[1], a));
    else Map_MapMove(map);
    ncsr = 0; rec = 1;
    mainPaint();
    rec = 0;
    for (int i = ncsr - 1; i >= 0; i--) host_memcpy(CSR[i].o + 0xe0, CSR[i].b, 8);
    for (int i = 0; i < nob; i++) { int *q = (int *)(OB[i].o + 4); q[0] = OB[i].c[0]; q[1] = OB[i].c[1]; }
    host_memcpy(map, SMAP, sizeof SMAP); if (ev) host_memcpy(ev, SEP, sizeof SEP); host_memcpy(off->data, SOFF, n);
    return 1;
}
/* vẽ nội suy + giữ các byte gv mà Paint/EndProc dùng (như T18) */
static int render_mid(int a) {
    u8 b243 = gv[0x243], b130 = gv[0x130]; u32 w6492 = *(u32 *)(gv + 6492);
    gv[0x130] = (u8)I.fx130;
    I.mid = 1; int ok = render(a); I.mid = 0;
    gv[0x243] = b243; gv[0x130] = b130; *(u32 *)(gv + 6492) = w6492;
    return ok;
}
/* HOOK_B 0x48344 (CHAR::CharSprEffect lệnh đầu push {r4,r5,lr}): trong khung trung gian ghi lại CHAR+0xe0..e7 để khôi phục; luôn chạy hàm gốc. */
H void interp_csr_note(u8 *ch) { if (rec && ncsr < 64) { CSR[ncsr].o = ch; host_memcpy(CSR[ncsr].b, ch + 0xe0, 8); ncsr++; } }
__asm__(".text\n.arm\n.global interp_csr_entry\ninterp_csr_entry:\n  push {r0, r1, r2, r3, r4, r5, r12, lr}\n  bl interp_csr_note\n  pop {r0, r1, r2, r3, r4, r5, r12, lr}\n  push {r4, r5, lr}\n  b CharSprEffect_cont\n");
extern H void ParticleMgr_paintParticles(void *pm, int fb, int x, int y);
/* HOOK_BL 0x45804 (Battle::Paint -> ParticleMgr::paintParticles): khung trung gian giữ +0xf8/+0x100 */
H void interp_particles(u8 *pm, int fb, int x, int y) {
    if (!I.mid) { ParticleMgr_paintParticles(pm, fb, x, y); return; }
    int a = *(int *)(pm + 0xf8), b = *(int *)(pm + 0x100);
    ParticleMgr_paintParticles(pm, fb, x, y);
    *(int *)(pm + 0xf8) = a; *(int *)(pm + 0x100) = b;
}

static void tp_update(u32 dt) { if (dt < 1) dt = 1; I.tp = I.tp ? (3 * I.tp + dt + 2) >> 2 : dt; }

/* HOOK_BL 0x99fa4 (thay `bl mainPaint` trong TimerCb): Paint của tick thật. */
H void interp_tick_paint(void) {
    if (I.flag != F_ON || !eligible()) { I.valid = 0; nob = 0; I.lag_pending = 0; mainPaint(); return; }
    u8 *hero = HEROP(), *map = *(u8 **)(gv + GV_MAP);
    u32 t0 = now32();
    if (!I.valid || I.map != map || I.hero != hero) { nob = 0; evm = -1; }
    capture();
    I.hero = hero; I.map = map; I.valid = 1;
    I.fx130 = gv[0x130];
    int moved = (camp[0] != camc[0]) || (camp[1] != camc[1]);
    for (int i = 0; i < nob && !moved; i++) if (OB[i].p[0] != OB[i].c[0] || OB[i].p[1] != OB[i].c[1]) moved = 1;
    imoved = moved;
    /* còn đủ chỗ cho >= 1 khung trung gian trong phần còn lại của chu kỳ tick? */
    int rem = (int)I.Pexp - sdiff(t0, *(u32 *)(gv + GV_TICK0));
    int lag = moved && I.tp && I.Pexp && rem * 10 > (int)(13 * I.tp);
    mainPaint();                                   /* Paint THẬT, nguyên bản */
    if (lag && render_mid(0)) { I.lag_pending = 1; I.n_tick_lag++; } else I.lag_pending = 0;
    tp_update(sdiff(now32(), t0));
}

/* HOOK_BL 0x99f90 (thay `bl CUSTOM_extSetTimer` cuối tick): đặt hạn tick kế. ms = trễ gốc (64 bit, chỉ dùng 32 bit thấp). */
H int interp_settimer(void *t, long long ms, void *ctx) {
    if (!I.flag) flag_load();
    if (I.flag != F_ON) { I.armed = 0; I.lag_pending = 0; return CUSTOM_extSetTimer(t, ms, ctx); }   /* cờ tắt: đúng lời gọi gốc, không gọi thêm MC_knlCurrentTime */
    u32 now = now32(), slack = (internalTimerNanoCheck == 1) ? 20 : 10;   /* extSetTimer chế độ bảng: hạn = now+ms-20; MC_knlSetTimer: hạn = now+ms, bắn khi now+10 > hạn */
    u32 D = now + (u32)ms - slack;
    I.Pexp = D - *(u32 *)(gv + GV_TICK0);
    if (I.flag == F_ON && I.lag_pending && ms > SLICE && sdiff(D, now) * 10 > (int)(13 * I.tp)) {
        I.armed = 1; I.D = D; I.T = now; I.P = D - now; I.lag_pending = 0;
        return CUSTOM_extSetTimer(t, SLICE, ctx);
    }
    I.armed = 0; I.lag_pending = 0;
    return CUSTOM_extSetTimer(t, ms, ctx);
}

/* HOOK_BL 0xbee5c (thay `bl Monster::IncreaseTempFrame` trong Monster::Draw): khung trung gian không được tăng bộ đếm. */
H void interp_inc_temp(void *m) { if (!I.mid) Monster_IncreaseTempFrame(m); }

/* Gọi từ interp_entry (đầu TimerCb, thay `bl MC_knlCurrentTime` thứ nhất). Trả 1 = đã xử lý khung trung gian, TimerCb phải return. */
H int interp_mid(void *timer, void *ctx) {
    if (!I.armed) return 0;
    u32 now = now32();
    if (sdiff(now, I.D) > 0) { I.armed = 0; return 0; }          /* tới hạn tick: chạy TimerCb nguyên vẹn */
    if (I.flag == F_ON && sdiff(I.D, now) * 10 > (int)(13 * I.tp) && I.valid && imoved && HEROP() == I.hero && *(u8 **)(gv + GV_MAP) == I.map) {
        int a = I.P ? (int)(((now - I.T) << 8) / I.P) : 256;
        if (a < 0) a = 0; if (a > 256) a = 256;
        render_mid(a);
        u32 g = _lcdG;
        CUSTOM_grpFlushLcd(0, Graphic_GetFB(g), 0, 0, Graphic_GetWidth(g), Graphic_GetHeight(g));
        tp_update(sdiff(now32(), now));
        I.n_mid++;
    } else I.n_skip++;
    CUSTOM_extSetTimer(timer, SLICE, ctx);
    return 1;
}

/* Điểm vào đặt tại TimerCb 0x99dfc (thay `bl MC_knlCurrentTime`): khi chưa armed -> tail-call MC_knlCurrentTime (đường gốc);
 * khi trả 1 -> thoát TimerCb (add sp,#92; pop {r4-r11,pc}: đúng epilogue của TimerCb 0x99f94). r7 = timer, r6 = ctx lúc này. */
__asm__(
".text\n.arm\n.global interp_entry\ninterp_entry:\n"
"  push {r4, lr}\n  mov r0, r7\n  mov r1, r6\n  bl interp_mid\n  cmp r0, #0\n  pop {r4, lr}\n  bne 1f\n  b MC_knlCurrentTime\n"
"1: add sp, sp, #92\n  pop {r4, r5, r6, r7, r8, r9, r10, r11, pc}\n"
);
