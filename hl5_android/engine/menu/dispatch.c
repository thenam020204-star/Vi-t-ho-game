/* Bộ điều phối: ô vtable (Paint, TouchPressed, TouchMoved, TouchReleased) của mỗi lớp state -> thunk ở đây (chỉ lớp có dòng trong states.def).
 * Thunk tra states.def: khớp -> hàm mới; không khớp (hoặc cờ MUI_F_DISABLE, hoặc FB không RGB565) -> hàm GỐC của guest (gọi trực tiếp, bl PC-relative).
 * Không bảng con trỏ (mọi thứ là switch/if sinh từ X-macro) nên không relocation. */
#include "mui.h"

volatile u32 mui_flags;
mui_m L;
int mui_tz;                                   /* T14: chỉ số con trỏ (tham số z) của chạm hiện tại, cho hud.c */
H void hud_release(void *self, int x, int y, int z);

H void mui_nop_paint(void *self);
H int mui_nop_touch(void *self, int x, int y);
void mui_nop_paint(void *self) { (void)self; }
int mui_nop_touch(void *self, int x, int y) { (void)self; (void)x; (void)y; return 0; }

/* hàm gốc của guest, địa chỉ gán bởi build.sh (--defsym Name_Method) */
#define MUI_CLASS(I, N, E) \
    extern H void N##_Paint(void *); extern H int N##_KeyPressed(void *, int); \
    extern H int N##_TouchPressed(void *, int, int, int); extern H int N##_TouchMoved(void *, int, int, int); \
    extern H int N##_TouchReleased(void *, int, int, int);
#include "classes.def"
#undef MUI_CLASS

int mui_key(void *self, int cls, int code) {
    switch (cls) {
#define MUI_CLASS(I, N, E) case MUI_CLS_##I: return N##_KeyPressed(self, code);
#include "classes.def"
#undef MUI_CLASS
    }
    return 0;
}

int mui_begin(void) {
    mui_hits_reset();
    if (!mui_fb_begin()) return 0;
    if (L.W != mui_fb_w() || L.Ht != mui_fb_h()) mui_layout(&L, mui_fb_w(), mui_fb_h());
    return 1;
}

/* khai báo hàm trong states.def */
#define MUI_STATE(C, N, COND, P, T, M) H void P(void *self); H int T(void *self, int x, int y); H int M(void *self, int x, int y);
#include "states.def"
#undef MUI_STATE

enum {
#define MUI_STATE(C, N, COND, P, T, M) ST_##N,
#include "states.def"
#undef MUI_STATE
    ST__count
};

/* chọn dòng states.def cho (lớp, self): 0 = không, i+1 = dòng thứ i */
static int sel(int cls, void *self) {
    (void)cls; (void)self;
#define MUI_STATE(C, N, COND, P, T, M) if (cls == MUI_CLS_##C && (COND)) return ST_##N + 1;
#include "states.def"
#undef MUI_STATE
    return 0;
}

static int call_paint(int id, void *self) {
    switch (id - 1) {
#define MUI_STATE(C, N, COND, P, T, M) case ST_##N: P(self); return 1;
#include "states.def"
#undef MUI_STATE
    }
    return 0;
}
static int call_touch(int id, void *self, int x, int y) {
    switch (id - 1) {
#define MUI_STATE(C, N, COND, P, T, M) case ST_##N: return T(self, x, y);
#include "states.def"
#undef MUI_STATE
    }
    return 0;
}
static int call_move(int id, void *self, int x, int y) {
    switch (id - 1) {
#define MUI_STATE(C, N, COND, P, T, M) case ST_##N: return M(self, x, y);
#include "states.def"
#undef MUI_STATE
    }
    return 0;
}

/* thunk cho từng lớp (chỉ gắn vào vtable những lớp có dòng; xem inject_menu.py) */
#define MUI_CLASS(I, N, E) \
    void mui_paint_##I(void *self) { \
        if (!(mui_flags & MUI_F_DISABLE)) { int id = sel(MUI_CLS_##I, self); if (id && mui_begin()) { call_paint(id, self); return; } } \
        N##_Paint(self); } \
    int mui_tp_##I(void *self, int x, int y, int z) { mui_tz = z; \
        if (!(mui_flags & MUI_F_DISABLE)) { int id = sel(MUI_CLS_##I, self); if (id && mui_fb_w() > 0) { call_touch(id, self, x, y); return 0; } } \
        return N##_TouchPressed(self, x, y, z); } \
    int mui_tm_##I(void *self, int x, int y, int z) { mui_tz = z; \
        if (!(mui_flags & MUI_F_DISABLE)) { int id = sel(MUI_CLS_##I, self); if (id && mui_fb_w() > 0) { call_move(id, self, x, y); return 0; } } \
        return N##_TouchMoved(self, x, y, z); } \
    int mui_tr_##I(void *self, int x, int y, int z) { \
        if (!(mui_flags & MUI_F_DISABLE)) { int id = sel(MUI_CLS_##I, self); if (id) { if (MUI_CLS_##I == MUI_CLS_GAME && id == ST_hud + 1) { hud_release(self, x, y, z); return 0; } mui_touch_release(); return 0; } } \
        return N##_TouchReleased(self, x, y, z); }
#include "classes.def"
#undef MUI_CLASS
