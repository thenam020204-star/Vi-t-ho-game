/* L1 (T17c4/T17c6): helper dùng chung cho spirit.c / quest.c / gpop.c / expert.c / map.c (file riêng của L1; không có trong mui.h để khỏi đụng L2). */
#include "mui.h"
#define S8(self, o) (*((s8 *)(self) + (o)))
#define I8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define I16(p, o) (*(s16 *)((u8 *)(p) + (o)))
#define I32(p, o) (*(int *)((u8 *)(p) + (o)))

static inline void l1_prelude(void) {                 /* như InGameMenu::Paint gốc: khôi phục cảnh + làm tối */
    u32 g = _lcdG;
    Graphic_CopyFrameBuffer(g, Graphic_GetFB(g), Graphic_GetFBOff(g));
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 150);
}
static inline int l1_subh(void) { return L.px + 2 * (L.bt + 3) + 2; }
/* tab con dạng chữ (như property.c::subtab) */
static inline void l1_subtab(int x0, int y0, int x1, int y1, const u8 *label, int n, int active, int hid) {
    mui_m *m = &L; int in[4]; u32 col;
    if (active) { mui_frame(x0, y0, x1, y1, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12)); col = C_WHITE; }
    else { mui_frame(x0, y0, x1, y1, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46)); col = RGB(190, 204, 224); }
    int pd = m->bt + 3 + 1;
    mui_clip(x0 + pd, y0, x1 - pd, y1);
    mui_text((x0 + x1) / 2, y0 + (y1 - y0 - m->px) / 2, label, n, m->px, col, TF_CENTER | (active ? TF_SHADOW : 0));
    mui_clip_reset();
    mui_hit(hid, x0, y0, x1, y1, HK_CB, 0, 0);
}
/* thanh 2 tab con đều nhau (w_subtabs trong mock_b2a.py): trả chiều cao; hit id = hid0 + i */
static inline int l1_subtabs(int x0, int y, int x1, const u8 *l0, int n0, const u8 *l1, int n1, int active, int hid0) {
    int h = l1_subh(), g = 2, tw = (x1 - x0 - g) / 2, off = (x1 - x0 - (2 * tw + g)) / 2;
    l1_subtab(x0 + off, y, x0 + off + tw, y + h, l0, n0, active == 0, hid0);
    l1_subtab(x0 + off + tw + g, y, x0 + off + 2 * tw + g, y + h, l1, n1, active == 1, hid0 + 1);
    return h;
}
static inline void l1_panel(int x0, int y0, int x1, int y1) { mui_frame(x0, y0, x1, y1, L.rad, 0, 1, RGB(14, 22, 34), 230, NULL); }
/* vùng nội dung thân cửa sổ có tab dưới (như combo.c / raw_geo) */
static inline void l1_geo(int *x0, int *y0, int *x1, int *y1) {
    mui_m *m = &L;
    *x0 = m->wx + m->pad; *y0 = m->wy + m->titleH + m->pad; *x1 = m->wx + m->winW - m->pad; *y1 = m->wy + m->winH - m->tabH - m->pad;
}
/* hộp mô tả cuộn bằng chữ đã wrap (desc: chuỗi game); scroller sc (0..3); trả không gì. box = hộp ngoài đã vẽ bởi mui_descbox2; y = đầu vùng chữ */
static inline void l1_desc(const mui_r *box, int y, const u8 *desc, int sc, int reset) {
    mui_m *m = &L;
    int p = m->pad / 2 + 1, ix0 = box->x0 + 3, ix1 = box->x1 - 3, tw = (ix1 - ix0) - 2 * p - m->sbw - 2, view = box->y1 - 3 - y;
    u16 st[48], ln[48];
    int nl = mui_wrap(m->px, desc, -1, tw, st, ln, 48), total = nl * m->lp;
    if (view < 1) view = 1;
    if (reset) mui_sc[sc].pos = 0;
    int pos = mui_sc[sc].pos, mx = total > view ? total - view : 0;
    if (pos > mx) pos = mx;
    if (pos < 0) pos = 0;
    mui_sc[sc].pos = pos; mui_sc[sc].total = total; mui_sc[sc].view = view;
    mui_clip(ix0, y, ix1, box->y1 - 3);
    for (int i = 0; i < nl; i++) {
        int yy = y - pos + i * m->lp;
        if (yy + m->lp > y && yy < y + view) mui_text_line(ix0 + p, yy, desc, st[i], ln[i], m->px, C_WHITE, 0);
    }
    mui_clip_reset();
    if (total > view) mui_scrollbar(ix1 - m->sbw - 1, y, box->y1 - 3, pos, total, view);
    mui_hit(0xE0 + sc, ix0, y, ix1, box->y1 - 3, HK_DRAG, sc, 1);
}
