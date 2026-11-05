/* L6b: StateMainMenu còn lại: state 6 sub 1 (Chuyên Gia chọn chế độ), state 10 (Thành tựu), state 11 (Trợ giúp). Kiến trúc A như mmenu.c: Paint + Touch mới; KeyPressed/Process GỐC.
 * Chuyên Gia sub 1 (DrawExpert 0x11baf4 nhánh sub==1, KeyExpert 0x11f290): 4 chế độ, nhãn CT(196+i), con trỏ [+0xf] 0..3; LEFT/RIGHT đổi 0..2 (3 giữ), UP/DOWN đổi 3 <-> 1;
 *   OK: i<3 -> gv[6457]=i+1 + popup xác nhận (SetPopupState(9+i)); i==3 -> popup BXH (loại 12); Back (-16) -> sub 0. Chạm = ghi [+0xf]=i rồi OK gốc (cùng trạng thái phím).
 * Thành tựu (DrawMission 0x11940c, KeyMission 0x11e8e4, TouchMission 0x11915c): Mission = *(gv+0x80); GetLength() (short), GetMissionTablePtr(i): [+0] char* tên, [+40] s8 điểm; IsCompleteMission(i).
 *   +0xe hàng cửa sổ 0..6 (7 hàng), +0xf chỉ số tuyệt đối; điểm = tổng điểm các mục hoàn tất / tổng, % = 100*số hoàn tất/GetLength; chip CT(261) hoàn tất / CT(262) chưa.
 *   OK trên mục hoàn tất = popup nhận thưởng (gốc). Chạm hàng: như TouchMission gốc (khác hàng: ghi +0xe/+0xf; đúng hàng: OK). Mũi tên ▲▼: như gốc (dịch cửa sổ 1 hàng, giữ con trỏ nếu còn trong cửa sổ). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
extern H int Mission_GetLength(void *ms);
extern H u8 *Mission_GetMissionTablePtr(void *ms, int i);
extern H int Mission_IsCompleteMission(void *ms, int i);
#define MS() (*(u8 **)(gv + 0x80))
#define MLEN() ((int)(s16)Mission_GetLength(MS()))
enum { H2_MODE = 0x40, H2_ROW = 0x50, H2_CHIP = 0x60, H2_UP = 0x70, H2_DN = 0x71, H2_LIST = 0x80, H2_BOX = 0x90 };
static int rnd2(int a, int b) { return (2 * a + b) / (2 * b); }

/* nhãn nhiều dòng (tối đa 2 dòng cỡ px, nếu dài hơn: cỡ nhỏ spx) căn giữa trong ô (x,y,w,h) */
static void tile_label(int x, int y, int w, int h, const u8 *lb, u32 col, int shadow) {
    mui_m *m = &L; u16 st[4], ln[4]; int px = m->px, lp = m->lp, n = mui_wrap(px, lb, -1, w - 2 * m->pad, st, ln, 4);
    if (n > 2) { px = m->spx; lp = m->slp; n = mui_wrap(px, lb, -1, w - 2 * m->pad, st, ln, 4); }
    int yy = y + (h - n * lp + (lp - px)) / 2;
    mui_clip(x + 1, y, x + w - 1, y + h);
    for (int i = 0; i < n; i++) mui_text_line(x + w / 2, yy + i * lp, lb, st[i], ln[i], px, col, TF_CENTER | (shadow ? TF_SHADOW : 0));
    mui_clip_reset();
}

/* ---------------- state 6 sub 1: Chuyên Gia chọn chế độ ---------------- */
void mm_expert_paint(void *self) {
    mui_m *m = &L; mui_r c; int cur = S8(self, 0xf), s = m->s;
    mm_dimwin(CT(167), &c);
    int g = m->gap + 2, W = c.x1 - c.x0, Hh = c.y1 - c.y0, th = (Hh - g) / 2, cap = 44 * s + 16;
    if (th > cap) th = cap;
    int y0 = c.y0 + (Hh - (2 * th + g)) / 2, tw = (W - 2 * g) / 3;
    for (int i = 0; i < 4; i++) {
        int x, y, w, sel = cur == i;
        if (i < 3) { x = c.x0 + i * (tw + g); y = y0; w = tw; } else { x = c.x0; y = y0 + th + g; w = W; }
        mm_tilebox(x, y, x + w, y + th, sel);
        tile_label(x, y, w, th, CT(196 + i), sel ? C_WHITE : RGB(210, 220, 236), 1);
        mui_hit(H2_MODE + i, x, y, x + w, y + th, HK_CB, 0, 0);
    }
}

/* ---------------- state 10: Thành tựu ---------------- */
static int g_y0, g_acc, g_on, g_rh = 20;
static void tri(int cx, int cy, int k, int up, u32 col) {
    for (int i = 0; i <= k; i++) { int w = up ? i : k - i, y = cy - k / 2 + i; mui_fill(cx - w, y, cx + w + 1, y + 1, col); }
}
void mm_mission_paint(void *self) {
    mui_m *m = &L; mui_r c; int s = m->s, row = S8(self, 0xe), ab = S8(self, 0xf);
    mm_dimwin(CT(171), &c);
    u8 *ms = MS(); int len = ms ? MLEN() : 0, earned = 0, total = 0, done = 0;
    for (int i = 0; i < len; i++) { int p = Mission_GetMissionTablePtr(ms, i)[40]; if (p > 127) p -= 256; total += p; if (Mission_IsCompleteMission(ms, i)) { done++; earned += p; } }
    int pct = len > 0 ? 100 * done / len : 0, g = 2, pd = m->pad;
    /* hàng đầu: Điểm a/b  [thanh]  n% */
    u8 t[48]; int tn = 0; { const u8 *q = CT(14); while (*q && tn < 24) t[tn++] = *q++; } t[tn++] = ' '; tn += mui_itoa(earned, t + tn); t[tn++] = '/'; tn += mui_itoa(total, t + tn);
    u8 pt[8]; int pn = mui_itoa(pct, pt); pt[pn++] = '%';
    int hh = m->lp + 4, ty = c.y0 + (hh - m->px) / 2;
    mui_text(c.x0 + pd, ty, t, tn, m->px, C_GOLD, TF_SHADOW);
    int pw = mui_sw(m->px, pt, pn); mui_text(c.x1 - pd, ty, pt, pn, m->px, pct > 0 ? C_GREEN : C_DIM, TF_RIGHT | TF_SHADOW);
    { int bx0 = c.x0 + pd + mui_sw(m->px, t, tn) + 2 * pd, bx1 = c.x1 - pd - pw - 2 * pd, by = c.y0 + (hh - m->barH) / 2;
      if (bx1 - bx0 > 8) {
          mui_fill(bx0, by, bx1, by + m->barH, C_INK); mui_fill(bx0 + 1, by + 1, bx1 - 1, by + m->barH - 1, C_TRACK);
          int fw = rnd2((bx1 - bx0 - 2) * pct, 100); if (fw > 0) mui_vgrad(bx0 + 1, by + 1, bx0 + 1 + fw, by + m->barH - 1, C_ORANGE_HI, C_ORANGE);
      } }
    /* danh sách 7 hàng + cột mũi tên/thanh cuộn */
    int ly0 = c.y0 + hh + g, ly1 = c.y1, rh = (ly1 - ly0 - 6 * g) / 7, cw = m->lp + 2, lx1 = c.x1 - cw - g, top = ab - row;
    if (rh < 8) rh = 8;
    g_rh = rh + g;
    if (top < 0) top = 0;
    int numw = rh - 2; if (numw < m->px + 2) numw = m->px + 2;
    int on = mui_sw(m->px, CT(261), -1), off = mui_sw(m->px, CT(262), -1), chw = (on > off ? on : off) + 2 * pd;
    for (int r = 0; r < 7; r++) {
        int idx = top + r, y = ly0 + r * (rh + g), sel = r == row;
        if (idx >= len) break;
        int comp = Mission_IsCompleteMission(ms, idx), in[4];
        mui_fill(c.x0, y, lx1, y + rh, C_INK);
        if (sel) mui_vgrad(c.x0 + 1, y + 1, lx1 - 1, y + rh - 1, RGB(250, 160, 36), RGB(196, 96, 12)); else mui_vgrad(c.x0 + 1, y + 1, lx1 - 1, y + rh - 1, C_CELL, C_CELL2);
        mui_hit(H2_ROW + r, c.x0, y, lx1, y + rh, HK_CB, 0, 0);
        /* số thứ tự */
        mui_fill(c.x0 + 2, y + 2, c.x0 + 2 + numw, y + rh - 2, RGB(10, 16, 26));
        u8 nb[8]; int nn = mui_itoa(idx + 1, nb);
        mui_text(c.x0 + 2 + numw / 2, y + (rh - m->px) / 2, nb, nn, m->px, sel ? C_WHITE : C_DIM, TF_CENTER);
        /* chip trạng thái */
        int cx1 = lx1 - 2, cx0 = cx1 - chw;
        mui_fill(cx0, y + 2, cx1, y + rh - 2, RGB(10, 16, 26));
        mui_clip(cx0, y, cx1, y + rh);
        mui_text((cx0 + cx1) / 2, y + (rh - m->px) / 2, comp ? CT(261) : CT(262), -1, m->px, comp ? RGB(110, 240, 210) : RGB(150, 60, 60), TF_CENTER | (comp ? TF_SHADOW : 0));
        mui_clip_reset();
        mui_hit(H2_CHIP + r, cx0, y, cx1, y + rh, HK_CB, 0, 0);
        /* tên */
        const u8 *nm = *(const u8 **)Mission_GetMissionTablePtr(ms, idx);
        mui_clip(c.x0 + 2 + numw + pd, y, cx0 - pd, y + rh);
        mui_text(c.x0 + 2 + numw + pd, y + (rh - m->px) / 2, nm, -1, m->px, sel ? C_WHITE : (comp ? C_WHITE : C_DIM), sel ? TF_SHADOW : 0);
        mui_clip_reset();
        (void)in; (void)s;
    }
    /* cột phải: ▲ / thanh cuộn / ▼ */
    int ax0 = c.x1 - cw, ay1 = ly0 + cw, by0 = ly1 - cw;
    for (int k = 0; k < 2; k++) {
        int y0 = k ? by0 : ly0, in[4];
        mui_frame(ax0, y0, c.x1, y0 + cw, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
        tri((ax0 + c.x1) / 2, y0 + cw / 2, cw / 4 > 2 ? cw / 4 : 3, k == 0, C_ORANGE_HI);
        mui_hit(k ? H2_DN : H2_UP, ax0, y0, c.x1, y0 + cw, HK_CB, 0, 0);
    }
    mui_scrollbar(ax0 + (cw - m->sbw) / 2, ay1 + g, by0 - g, top, len > 7 ? len : 7, 7);
    (void)row;
}

/* như TouchMission/TouchHelp gốc: dịch cửa sổ 1 hàng (up=1: lên); rows = số hàng cửa sổ (7 Thành tựu, 6 Trợ giúp), len = số mục */
static void list_arrow(void *self, int up, int rows, int len) {
    int row = S8(self, 0xe), ab = S8(self, 0xf), top = ab - row;
    if (up) {
        int t = top - 1;
        if (t < 0) { S8(self, 0xf) = len - 1; t = len - rows; ab = len - 1; }
        int r = ab - t;
        if (r > rows - 1) { r = rows - 1; ab = t + rows - 1; }
        S8(self, 0xe) = r; S8(self, 0xf) = ab;
    } else {
        int t = top + 1;
        if (t >= len - rows + 1) { S8(self, 0xe) = 0; S8(self, 0xf) = 0; }
        else { int r = ab - t; if (r < 0) { r = 0; ab = t; } S8(self, 0xe) = r; S8(self, 0xf) = ab; }
    }
}

/* ---------------- state 11: Trợ giúp (DrawHelp 0x118d9c, KeyHelp 0x11e598, TouchHelp 0x1182c0) ----------------
 * sub 0: danh sách tiêu đề (cửa sổ 6 hàng: +0xe 0..5, +0xf tuyệt đối; tổng = s16 [TextMgr+260]); OK -> sub 1 (nội dung, cuộn gốc [+0x10]); Back sub 1 -> sub 0, sub 0 -> menu (state 4). */
static int help_total(void) { int t = *(s16 *)((u8 *)_pTextMgr + 260); return t > 0 && t <= 64 ? t : 0; }
void mm_help_paint(void *self) {
    mui_m *m = &L; mui_r c; int sub = S8(self, 0xd), cur = S8(self, 0xf), total = help_total(), g = 2, pd = m->pad;
    mm_dimwin(CT(172), &c);
    if (total <= 0 || cur < 0 || cur >= total) return;                  /* chuỗi trợ giúp chưa nạp */
    if (sub == 0) {
        int row = S8(self, 0xe), top = cur - row, hh = m->lp + 4, ly0 = c.y0 + hh + g, rh = (c.y1 - ly0 - 5 * g) / 6, cw = m->lp + 2, lx1 = c.x1 - cw - g;
        if (top < 0) top = 0;
        if (rh < 8) rh = 8;
        g_rh = rh + g;
        /* hàng đầu: n/tổng (vùng chạm ↺ gốc của game = x >= W-36, 30 <= y <= 60 vẫn là Back: không đặt nút điều khiển ở đó) */
        { u8 t[24]; int tn = mui_itoa(cur + 1, t); t[tn++] = '/'; tn += mui_itoa(total, t + tn); mui_text(c.x0 + pd, c.y0 + (hh - m->px) / 2, t, tn, m->px, C_GOLD, TF_SHADOW); }
        for (int r = 0; r < 6 && top + r < total; r++) {
            int y = ly0 + r * (rh + g), sel = r == row;
            mui_fill(c.x0, y, lx1, y + rh, C_INK);
            if (sel) mui_vgrad(c.x0 + 1, y + 1, lx1 - 1, y + rh - 1, RGB(250, 160, 36), RGB(196, 96, 12)); else mui_vgrad(c.x0 + 1, y + 1, lx1 - 1, y + rh - 1, C_CELL, C_CELL2);
            const u8 *tt = TextMgr_GetHelpTitleString(_pTextMgr, top + r); if (!tt) tt = (const u8 *)"";
            mui_clip(c.x0, y, lx1 - pd, y + rh);
            mui_text(c.x0 + pd, y + (rh - m->px) / 2, tt, -1, m->px, C_WHITE, sel ? TF_SHADOW : 0);
            mui_clip_reset();
            mui_hit(H2_ROW + r, c.x0, y, lx1, y + rh, HK_CB, 0, 0);
        }
        int ax0 = c.x1 - cw, ay1 = ly0 + cw, by0 = c.y1 - cw;
        for (int k = 0; k < 2; k++) {
            int y0 = k ? by0 : ly0, in[4];
            mui_frame(ax0, y0, c.x1, y0 + cw, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
            tri((ax0 + c.x1) / 2, y0 + cw / 2, cw / 4 > 2 ? cw / 4 : 3, k == 0, C_ORANGE_HI);
            mui_hit(k ? H2_DN : H2_UP, ax0, y0, c.x1, y0 + cw, HK_CB, 0, 0);
        }
        mui_scrollbar(ax0 + (cw - m->sbw) / 2, ay1 + g, by0 - g, top, total > 6 ? total : 6, 6);
    } else {
        static int lastc = -1; if (cur != lastc) { mui_sc[1].pos = 0; lastc = cur; }
        const u8 *tt = TextMgr_GetHelpTitleString(_pTextMgr, cur), *body = TextMgr_DT_GetHelpInfoString(_pTextMgr, cur);
        mui_r box = {c.x0, c.y0, c.x1, c.y1};
        if (!tt) tt = (const u8 *)""; if (!body) body = (const u8 *)"";
        int ty = mui_descbox(&box, tt, -1, -1), p = m->pad / 2 + 1, ix0 = box.x0 + 3 + p, ix1 = box.x1 - 3;
        static u16 st[96], ln[96]; int nl = mui_wrap(m->px, body, -1, ix1 - ix0 - p - m->sbw - 2, st, ln, 96);
        int view = (box.y1 - 3 - ty) / m->lp, top = mui_sc[1].pos;
        if (view < 1) view = 1;
        if (top > nl - view) top = nl - view;
        if (top < 0) top = 0;
        mui_sc[1].pos = top; mui_sc[1].total = nl; mui_sc[1].view = view;
        mui_clip(ix0, ty, ix1, box.y1 - 3);
        for (int i = 0; i < view && top + i < nl; i++) mui_text_skip(ix0, ty + i * m->lp, body + st[top + i], ln[top + i], m->px, C_WHITE, 0);
        mui_clip_reset();
        if (nl > view) mui_scrollbar(ix1 - m->sbw - 1, ty, box.y1 - 3, top, nl, view);
        mui_hit(H2_BOX, box.x0, ty, box.x1, box.y1, HK_DRAG, 1, m->lp);
    }
}

/* ---------------- chạm ---------------- */
int mm2_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_MAIN, x, y), id = mui_last_id, st = S8(self, 0xc), sub = S8(self, 0xd);
    g_y0 = y; g_acc = 0; g_on = 0;
    if (k != HK_CB) return 0;
    if (st == 6 && sub == 1) {
        if (id >= H2_MODE && id < H2_MODE + 4) { S8(self, 0xf) = id - H2_MODE; mui_key(self, MUI_CLS_MAIN, -5); }
    } else if (st == 10) {
        if (id >= H2_ROW && id < H2_ROW + 7) {
            int r = id - H2_ROW, row = S8(self, 0xe), top = S8(self, 0xf) - row;
            if (row != r) { S8(self, 0xf) = top + r; S8(self, 0xe) = r; } else mui_key(self, MUI_CLS_MAIN, -5);
            g_on = 1;
        } else if (id >= H2_CHIP && id < H2_CHIP + 7) {
            int r = id - H2_CHIP, row = S8(self, 0xe), top = S8(self, 0xf) - row;
            if (row != r) { S8(self, 0xf) = top + r; S8(self, 0xe) = r; }
            mui_key(self, MUI_CLS_MAIN, -5);
        } else if (id == H2_UP) list_arrow(self, 1, 7, MLEN());
        else if (id == H2_DN) list_arrow(self, 0, 7, MLEN());
    }
    else if (st == 11 && sub == 0) {
        int total = help_total();
        if (id >= H2_ROW && id < H2_ROW + 6) {
            int r = id - H2_ROW, row = S8(self, 0xe), top = S8(self, 0xf) - row;
            if (row != r) { S8(self, 0xf) = top + r; S8(self, 0xe) = r; } else mui_key(self, MUI_CLS_MAIN, -5);
            g_on = 1;
        } else if (id == H2_UP) list_arrow(self, 1, 6, total);
        else if (id == H2_DN) list_arrow(self, 0, 6, total);
    }
    return 0;
}
int mm2_move(void *self, int x, int y) {
    if ((S8(self, 0xc) == 10 || S8(self, 0xc) == 11) && g_on) {                              /* kéo dọc danh sách = phím LÊN/XUỐNG gốc theo hàng */
        int rh = g_rh > 0 ? g_rh : L.lp + 6, d = (g_y0 - y) / rh;
        while (g_acc < d) { mui_key(self, MUI_CLS_MAIN, -2); g_acc++; }
        while (g_acc > d) { mui_key(self, MUI_CLS_MAIN, -1); g_acc--; }
    }
    return mui_touch_move(self, MUI_CLS_MAIN, x, y);
}
