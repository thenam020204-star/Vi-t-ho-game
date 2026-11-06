/* L1/T17c4: Nhiệm vụ (InGameMenu state 8). Số đo: ui_new/menu/LAYOUT.md §11 + mock_b2a.screen_quest; hit_rects.json nhiem_vu / nhiem_vu_nhanh.
 * Kiến trúc A: Paint + Touch mới; KeyQuestMenu/Process gốc. Điều kiện bật: sub 0..3 (xem states.def); sub 4 (xem phần thưởng) và 5/6 (bản đồ lớn/MapMode) giữ đường gốc.
 * Trường gốc (KeyQuestMenu 0x101260, dò động harness/l1/probe_q*.py): +0xd sub: 0 hàng tab con, 1 danh sách (tab 0), 2 cây nhánh (tab 1), 3 chi tiết (setQuestViewInfo), 4 phần thưởng, 5/6 bản đồ;
 *   +0xe tab con (RIGHT/LEFT ở sub 0 đảo 0/1; DOWN ở sub 0 -> sub = tab + 1); +0xf hàng trong cửa sổ (0..6), +0x10 chỉ số tuyệt đối; +0x11 nút ở chi tiết (0 = phần thưởng, 1 = bản đồ lớn; OK: sub 3 -> 4 hoặc 5);
 *   +0x12 cuộn chi tiết; cây: lưới *(this+0x3fc) 5 cột x 72 hàng (255 = trống), ô 59x31 điểm gốc, +0x38/+0x3a (s16) vị trí vẽ hiện tại, +0x3c/+0x3e đích (UP/DOWN +-31, LEFT/RIGHT +-59), OK về nhà.
 * Dữ liệu: QuestMgr = *(gv+5580): số nhiệm vụ s8 [+12468]; bản ghi i (368 B) tại +1428+368*i: +0 id, +4 tên, +0x20 mô tả (';' = ngắt), +0xe8 tên NPC, +0x16d chỉ số khu vực (InGameText);
 *   trạng thái u8 [+0x488+id] (0,1,6 khoá "???"; 2 đang làm; 3,4,5 xong). Tên nút cây = InGameText(id+43); nhãn tab = InGameText(41)/(42); nút chi tiết = InGameText(132)/(147).
 * Chạm: tab con = (Back về sub 0 nếu cần) rồi LEFT/RIGHT gốc; hàng i = phím DOWN/UP gốc (sub 0 -> DOWN trước) tới hàng đó, chạm lần 2 vào hàng đang chọn = OK gốc (vào chi tiết);
 *   nút chi tiết = [+0x11]=0/1 rồi OK gốc; kéo dọc danh sách/cây = UP/DOWN gốc (mỗi hàng/nút), kéo dọc mô tả = cuộn (không đổi trường gốc). */
#include "l1ui.h"

#define QM() (*(u8 **)(gv + 5580))
#define Q_CNT(q) I8(q, 12468)
#define Q_REC(q, i) ((q) + 1428 + 368 * (i))
#define IGT(i) TextMgr_GetInGameText(_pTextMgr, (i))
#define K_UP -1
#define K_DOWN -2
#define K_LEFT -3
#define K_RIGHT -4
#define K_BACK -16
#define H_SUB 0x30      /* +0,1 */
#define H_ROW 0x20      /* +0..11 */
#define H_NODE 0x40     /* +0..11 */
#define H_DETAIL 0x3D
#define H_BTN 0x50      /* +0,1 */
static int g_lastsub;                                   /* L6c: sub của khung trước (đặt lại cuộn mô tả khi đổi mô tả <-> phần thưởng) */
static int qdrag_y, qdrag_n, qdrag_kind, qdrag_p;       /* kéo: kind 1 = danh sách, 2 = cây; n = số phím đã gửi; p = bước (px) */

static int half_even(int cw) { int f = cw / 2; return (cw & 1) && (f & 1) ? f + 1 : f; }     /* round(cw*0.5) kiểu Python */

static int qstatus(u8 *q, int id) { return *(u8 *)(q + 0x488 + id); }
static u32 st_col(int st) { return st == 2 ? C_GREEN : (st >= 3 && st <= 5) ? C_GOLD : C_DISABLED; }

/* số hàng danh sách hiển thị */
static int list_geo(int ty, int y1, int *rowh) { *rowh = L.rowH + 2 * L.s; return (y1 - ty + 2) / (*rowh + 2); }

static void paint_detail(void *self, u8 *q, int sel, const mui_r *box, int sub) {
    mui_m *m = &L; u8 meta[96]; int mn = 0, y;
    if (sel < 0 || sel >= Q_CNT(q)) { mui_frame(box->x0, box->y0, box->x1, box->y1, m->rad, 0, 1, RGB(10, 16, 26), 235, NULL); mui_hit(H_DETAIL, box->x0, box->y0, box->x1, box->y1, HK_NONE, 0, 0); return; }
    u8 *rc = Q_REC(q, sel); int id = I8(rc, 0), st = qstatus(q, id & 255);
    const u8 *lab = IGT(st >= 3 && st <= 5 ? 137 : 135), *ar = IGT(I8(rc, 0x16d));
    for (int i = 0; lab[i] && mn < 40; i++) meta[mn++] = lab[i];
    meta[mn++] = ' '; meta[mn++] = '-'; meta[mn++] = ' ';
    for (int i = 0; ar[i] && mn < 94; i++) meta[mn++] = ar[i];
    y = mui_descbox2(box, rc + 4, -1, -1, meta, mn);
    mui_hit(H_DETAIL, box->x0, box->y0, box->x1, box->y1, HK_NONE, 0, 0);
    mui_r in = *box;
    if (sub >= 3) in.y1 = box->y1 - m->btnH - m->gap;
    if (sub == 4) {                                   /* L6c: phần thưởng = văn bản QuestMgr::setQuestViewInfo(1) ở qm+28 (như drawQuestInfo gốc); nền tối: mã màu $ -> vàng, | -> trắng (như qpop) */
        static u8 rwd[520]; const u8 *d = q + 28; int n = mui_cslen(d); while (n > 0 && d[n - 1] == ';') n--; if (n > 512) n = 512;
        for (int i = 0; i < n; i++) rwd[i] = d[i] == '$' ? '\'' : d[i] == '|' ? '{' : d[i];
        rwd[n] = 0; l1_desc(&in, y, rwd, 0, g_lastsub != 4);
    } else l1_desc(&in, y, rc + 0x20, 0, g_lastsub == 4);
    g_lastsub = sub;
    if (sub >= 3) {
        int g = m->gap, bw = (box->x1 - box->x0 - 6 - g) / 2, by = box->y1 - 3 - m->btnH, bn;
        mui_r b = {box->x0 + 3, by, box->x0 + 3 + bw, by + m->btnH};
        const u8 *t0 = mui_hint(IGT(sub == 4 ? 133 : 132), &bn);
        mui_button(&b, t0, bn, S8(self, 0x11) == 0 ? 1 : 0, 0); mui_hit(H_BTN, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
        b.x0 = b.x1 + g; b.x1 = box->x1 - 3;
        const u8 *t1 = mui_hint(IGT(147), &bn);
        mui_button(&b, t1, bn, S8(self, 0x11) == 1 ? 1 : 0, 0); mui_hit(H_BTN + 1, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
    }
}

static void paint_list(void *self, int x0, int ty, int x1, int y1) {
    mui_m *m = &L; u8 *q = QM(); int cnt = Q_CNT(q), cw = x1 - x0, lw = half_even(cw), sub = S8(self, 0xd);
    int rowh, nfit = list_geo(ty, y1, &rowh), sel = S8(self, 0x10), f = S8(self, 0xf), first = sel - f;
    if (nfit > 12) nfit = 12;
    if (first < 0) first = 0;
    if (sel - first >= nfit) first = sel - nfit + 1;
    if (first < 0) first = 0;
    for (int i = 0; i < nfit; i++) {
        int ry = ty + i * (rowh + 2), a = first + i; if (ry + rowh > y1) break;
        mui_r r = {x0, ry, x0 + lw - m->sbw - 3, ry + rowh};
        if (a >= cnt) { mui_hit(H_ROW + i, r.x0, r.y0, r.x1, r.y1, HK_CB, 0, 0); continue; }
        u8 *rc = Q_REC(q, a); int st = qstatus(q, I8(rc, 0) & 255), on = (a == sel && sub >= 1) || (a == sel && sub == 0 && 0);
        mui_fill(r.x0, r.y0, r.x1, r.y1, C_INK);
        if (a == sel) mui_vgrad(r.x0 + 1, r.y0 + 1, r.x1 - 1, r.y1 - 1, RGB(250, 160, 36), RGB(196, 96, 12)); else mui_vgrad(r.x0 + 1, r.y0 + 1, r.x1 - 1, r.y1 - 1, C_CELL, C_CELL2);
        int d = 2 * m->s + 4; if (d < 5) d = 5;
        mui_fill(r.x0 + 4, r.y0 + (rowh - d) / 2, r.x0 + 4 + d, r.y0 + (rowh - d) / 2 + d, st_col(st));
        mui_clip(r.x0 + 4 + d + 4, r.y0, r.x1 - 2, r.y1);
        mui_text(r.x0 + 4 + d + 4, r.y0 + (rowh - m->px) / 2, rc + 4, -1, m->px, C_WHITE, a == sel ? TF_SHADOW : 0);
        mui_clip_reset();
        mui_hit(H_ROW + i, r.x0, r.y0, r.x1, r.y1, HK_CB, 0, 0); (void)on;
    }
    if (cnt > nfit) mui_scrollbar(x0 + lw - m->sbw, ty, y1, first, cnt, nfit);
    mui_r box = {x0 + lw + m->gap, ty, x1, y1};
    paint_detail(self, q, sel, &box, sub);
}

static void paint_tree(void *self, int x0, int ty, int x1, int y1) {
    mui_m *m = &L; int cw = x1 - x0, bw = 2 * (m->px * 14), pn = S8(self, 0x4c), rn = S8(self, 0x4d);
    if (bw > cw - 2 * m->pad) bw = cw - 2 * m->pad;
    int bx = (x0 + x1 - bw) / 2, nh = m->lp + 2 * m->s + 6, gp = m->gap + 2, P = nh + gp, pany = I16(self, 0x3a);
    u8 *grid = *(u8 **)((u8 *)self + 0x3fc), *q = QM(); int vi = 0;
    if (!grid || pn <= 0) return;
    mui_clip(x0, ty, x1, y1);
    for (int r = 0; r < rn; r++) {
        int y = ty + r * P + (pany * P) / 31;
        if (y + nh <= ty || y >= y1) continue;
        int idc = grid[r * pn + 2];
        if (idc != 255) {
            int st = qstatus(q, idc), cx = (bx + bx + bw) / 2;
            mui_r rr = {bx, y, bx + bw, y + nh}; int in[4];
            if (y + nh + gp > ty && r + 1 < rn && grid[(r + 1) * pn + 2] != 255) mui_fill(cx, y + nh, cx + (m->s > 1 ? m->s : 1), y + nh + gp, C_ORANGE);
            if (st == 2) { mui_frame(rr.x0, rr.y0, rr.x1, rr.y1, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12)); }
            else if (st >= 3 && st <= 5) { mui_frame(rr.x0, rr.y0, rr.x1, rr.y1, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 60, 52), RGB(22, 40, 36)); }
            else { mui_frame(rr.x0, rr.y0, rr.x1, rr.y1, m->rad, 0, 1, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(30, 36, 48), RGB(20, 24, 34)); }
            const u8 *nm = (st == 0 || st == 1 || st == 6) ? IGT(136) : IGT(idc + 43);
            mui_clip(bx + m->bt + 4, y < ty ? ty : y, bx + bw - m->bt - 4, y + nh > y1 ? y1 : y + nh);
            mui_text(cx, y + (nh - m->px) / 2, nm, -1, m->px, (st == 0 || st == 1 || st == 6) ? C_DISABLED : C_WHITE, TF_CENTER | (st == 2 ? TF_SHADOW : 0));
            mui_clip(x0, ty, x1, y1);
            if (y >= ty && y + nh <= y1 && vi < 12) mui_hit(H_NODE + vi, rr.x0, rr.y0, rr.x1, rr.y1, HK_CB, 0, 0);
            vi++;
        }
        for (int c = 0; c < pn; c++) {            /* nhánh phụ: ô nhỏ ở hai đầu hàng */
            if (c == 2) continue;
            int id = grid[r * pn + c]; if (id == 255) continue;
            int st = qstatus(q, id), sz = nh - 4, xx = c < 2 ? bx + 2 + (c == 1 ? sz + 2 : 0) : bx + bw - 2 - sz - (c == 3 ? sz + 2 : 0);
            mui_fill(xx, y + 2, xx + sz, y + 2 + sz, C_INK); mui_fill(xx + 1, y + 3, xx + sz - 1, y + 1 + sz, st_col(st));
        }
    }
    mui_clip_reset();
}

void quest_paint(void *self) {
    mui_m *m = &L; int e = S8(self, 0xe);
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(7), -1, MUI_TAB_ICONS, NULL, 7, &body);
    int x0, y0, x1, y1; l1_geo(&x0, &y0, &x1, &y1);
    int sh = l1_subtabs(x0, y0, x1, IGT(41), -1, IGT(42), -1, e == 1, H_SUB), ty = y0 + sh + m->gap;
    if (e == 1) paint_tree(self, x0, ty, x1, y1); else paint_list(self, x0, ty, x1, y1);
}

/* chuyển con trỏ danh sách tới hàng tuyệt đối t bằng phím gốc */
static void goto_row(void *self, int t) {
    int guard = 0;
    while (S8(self, 0x10) != t && guard++ < 64) mui_key(self, MUI_CLS_INGAME, S8(self, 0x10) < t ? K_DOWN : K_UP);
}
int quest_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    qdrag_kind = 0;
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 8) StateInGameMenu_SetState(self, id - 0x10 + 1); return 0; }
    int sub = S8(self, 0xd), e = S8(self, 0xe);
    if (id == H_SUB || id == H_SUB + 1) {
        int want = id - H_SUB;
        if (want == e) return 0;
        if (sub != 0) { if (sub >= 3) mui_key(self, MUI_CLS_INGAME, K_BACK); if (S8(self, 0xd) != 0) mui_key(self, MUI_CLS_INGAME, K_BACK); }
        mui_key(self, MUI_CLS_INGAME, want ? K_RIGHT : K_LEFT);
    } else if (id >= H_ROW && id < H_ROW + 12) {
        u8 *q = QM(); int cnt = Q_CNT(q), nfit, rowh, x0, y0, x1, y1; (void)x0; (void)y0;
        if (e != 0) return 0;
        l1_geo(&x0, &y0, &x1, &y1);
        nfit = list_geo(y0 + l1_subh() + L.gap, y1, &rowh); if (nfit > 12) nfit = 12;
        int sel = S8(self, 0x10), f = S8(self, 0xf), first = sel - f;
        if (first < 0) first = 0;
        if (sel - first >= nfit) first = sel - nfit + 1;
        if (first < 0) first = 0;
        int a = first + (id - H_ROW);
        qdrag_kind = 1; qdrag_y = y; qdrag_n = 0; qdrag_p = rowh + 2;
        if (a >= cnt) return 0;
        if (sub >= 3) mui_key(self, MUI_CLS_INGAME, K_BACK), sub = S8(self, 0xd);
        if (sub == 0) mui_key(self, MUI_CLS_INGAME, K_DOWN);
        else if (sub == 1 && a == S8(self, 0x10)) { mui_key(self, MUI_CLS_INGAME, -5); return 0; }
        if (S8(self, 0xd) == 1) goto_row(self, a);
    } else if (id >= H_NODE && id < H_NODE + 12) {
        qdrag_kind = 2; qdrag_y = y; qdrag_n = 0; qdrag_p = L.lp + 2 * L.s + 6 + L.gap + 2;
    } else if (id == H_BTN || id == H_BTN + 1) {
        if (sub < 3) return 0;
        S8(self, 0x11) = id - H_BTN; mui_key(self, MUI_CLS_INGAME, -5);
    } else if (id == 0xE0 && 0) { }
    return 0;
}
int quest_move(void *self, int x, int y) {
    int r = mui_touch_move(self, MUI_CLS_INGAME, x, y);
    if (qdrag_kind == 1 || qdrag_kind == 2) {
        int rows = (y - qdrag_y) / (qdrag_p > 0 ? qdrag_p : 1);      /* kéo xuống = lên trên (nội dung đi xuống): UP */
        if (qdrag_kind == 2) {
            if (S8(self, 0xd) == 0) mui_key(self, MUI_CLS_INGAME, K_DOWN);
            if (S8(self, 0xd) != 2) return r;
        } else if (S8(self, 0xd) == 0 || S8(self, 0xd) >= 3) return r;
        while (qdrag_n < rows) { mui_key(self, MUI_CLS_INGAME, K_UP); qdrag_n++; }
        while (qdrag_n > rows) { mui_key(self, MUI_CLS_INGAME, K_DOWN); qdrag_n--; }
    }
    return r;
}
