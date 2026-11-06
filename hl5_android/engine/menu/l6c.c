/* L6c: các menu con còn lại chuyển sang giao diện cảm ứng mới (file riêng của L6c).
 * (1) InGameMenu state 8 (Nhiệm vụ) sub 5/6 = bản đồ thế giới (DrawQuestMenu 0xe8b34 nhánh `[+0xd]-5 <= 1` -> 0xe9998: làm tối alpha 204, `[+0x2e]`==0 DrawWorldMap / ==1 DrawWorldMap + làm tối + DrawZone,
 *     rồi DrawHalfBox 69x17 góc trái dưới (nhãn InGameText(151 - cờ Minimap [*(gv+5200)+4224]), luôn vẽ) và, chỉ khi `[+0x2e]==1`, góc phải dưới (nhãn InGameText(149 - [+0x2e]) = 148)).
 *     TouchQuestMenu 0xfb3c0 (nhánh sub 5/6 = 0xfb42c): chế độ 1: IRect(0,H-17,69,H) -> MapModeKeyPressed(42); IRect(W-69,H-17,W,H) -> MapModeKeyPressed(35); dải trên/dưới (40..W-40) -> MapModeKeyPressed(-1/-2);
 *     chế độ 0: 8 điểm 60x60 -> (đang chọn ? MapModeKeyPressed(-5) : TouchWorldMapPoint(i)); mọi chế độ: ô 17x17 góc trái trên cửa sổ 176x208 -> KeyPressed(-16).
 *   Thiết kế: Paint = Paint GỐC (cảnh + bản đồ thế giới/DrawZone vẽ bằng sprite gốc, tọa độ cam [+52]/[+54] không đổi) rồi đè hai thanh nút ở góc dưới (phủ kín 69x17 gốc) + nút "<" góc trái trên;
 *   chạm: nút mới -> đúng hàm gốc như vùng gốc (MapModeKeyPressed(42/35), KeyPressed(-16)); chạm khác -> TouchPressed GỐC (điểm bản đồ, dải cuộn) nên trạng thái y hệt gốc. */
#include "l1ui.h"

extern H void StateInGameMenu_Paint(void *self);
extern H int StateInGameMenu_TouchPressed(void *self, int x, int y, int z);
extern H int StateInGameMenu_MapModeKeyPressed(void *self, int key);
#define IGT(i) TextMgr_GetInGameText(_pTextMgr, (i))
#define BAGP() ((u8 *)HERO_GetBagItemPtr(HEROP()))
enum { QM_STAR = 0xC0, QM_HASH, QM_BACK };

void qmap_paint(void *self) {
    mui_m *m = &L; int mode = S8(self, 0x2e);
    StateInGameMenu_Paint(self);
    if (!mui_begin()) return;
    int W = m->W, Ht = m->Ht, g = m->gap, bh = m->btnH + 2 * g, n;
    if (bh < 17) bh = 17;
    int fl = *(s8 *)(*(u8 **)(gv + 5200) + 4224);
    const u8 *t = mui_hint(IGT(fl ? 150 : 149), &n);                         /* cờ 0 gốc trỏ 151 = câu chào (lỗi gốc, như map.c): dùng 149 */
    int bw = mui_sw(m->px, t, n) + 4 * m->pad + 2 * g; if (bw < 69) bw = 69;
    mui_fill(0, Ht - bh, bw, Ht, C_NAVY);
    { mui_r b = {g, Ht - bh + g, bw - g, Ht - g}; mui_button(&b, t, n, 0, 0); mui_hit(QM_STAR, 0, Ht - bh, bw, Ht, HK_CB, 0, 0); }
    if (mode == 1) {
        t = mui_hint(IGT(148), &n);
        bw = mui_sw(m->px, t, n) + 4 * m->pad + 2 * g; if (bw < 69) bw = 69;
        mui_fill(W - bw, Ht - bh, W, Ht, C_NAVY);
        mui_r b = {W - bw + g, Ht - bh + g, W - g, Ht - g}; mui_button(&b, t, n, 1, 0); mui_hit(QM_HASH, W - bw, Ht - bh, W, Ht, HK_CB, 0, 0);
    }
    { int s = m->btnH + g, in[4], k = m->kb;                                  /* nút "<" (sprite ui 25 / chevron như mui_window) */
      mui_frame(0, 0, s, s, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 176, 44), RGB(206, 104, 12));
      int cx = s / 2, cy = s / 2; const u8 *gb = mui_icon_back();
      if (gb) mui_gbm(gb, cx - (*(const u16 *)(gb + 2) * k) / 2, cy - (*(const u16 *)(gb + 4) * k) / 2, k, -1);
      else for (int i = 0; i < 5; i++) { mui_fill(cx - 2 * k + i * k, cy - i * k, cx - k + i * k + k, cy - i * k + k, C_WHITE); if (i) mui_fill(cx - 2 * k + i * k, cy + i * k, cx - k + i * k + k, cy + i * k + k, C_WHITE); }
      mui_hit(QM_BACK, 0, 0, s, s, HK_CB, 0, 0); }
}
int qmap_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    if (k != HK_CB) return StateInGameMenu_TouchPressed(self, x, y, 0);
    if (id == QM_STAR) StateInGameMenu_MapModeKeyPressed(self, 42);
    else if (id == QM_HASH) StateInGameMenu_MapModeKeyPressed(self, 35);
    else if (id == QM_BACK) mui_key(self, MUI_CLS_INGAME, -16);
    return 0;
}

/* (2) InGameMenu state 4 (Kỹ năng) sub 8 / 10 (DrawSkillMenu 0xf115c: DrawSubjectBox CommonText(149) "Hợp thành" cho sub 8 và 10; sub 9 = CommonText(150) "Chế biến").
 *   Sub 8 (KeySkillMenu 0x1039b0, bảng khoá sub 8 tại 0x103fa4, idx = phím+16): +0x11 con trỏ 0/1 = ô A/B, 2 = nút; s16 [+0x2a]/[+0x2c] = chỉ số vật phẩm trong túi (-1 trống; BagItem::GetBagItemPtr(short));
 *     OK(-5): con trỏ<=1 -> OpenComposeFilter()+[+0x12]=0 (sang sub 10), con trỏ==2 -> thiếu ô: thông báo, đủ: popup 19 xác nhận; Back -> sub 5; '0'(48): xoá hai ô; UP -> con trỏ 0 (nếu 2); DOWN -> 2; LEFT/RIGHT đảo 0/1.
 *     TouchSkillMenu 0xfe108 (nhánh sub 8: 0xfec38..0xfed84): chạm ô i = (i != con trỏ ? con trỏ = i : KeyPressed(-5)); nút = tương tự; '*'(42)/'#'(35) cho các vùng góc.
 *   Sub 10: danh sách lọc 5/trang (BagItem+(122+i)*4, tổng s16 [bag+872]): con trỏ s8 [+0x12]; OK chọn vào ô [+0x11]; Back (-16) -> sub 8; LEFT/RIGHT +-1: picker của Túi (bag.c sk_pick_*).
 *   Chạm mới: ô i -> con trỏ = i rồi OK gốc (mở danh sách), nút -> con trỏ 2 rồi OK, "Bỏ chọn" -> phím '0', như quest.c dùng phím gốc; sub 10: pick_cb (chạm lần 1 chọn, lần 2 = OK như TouchSkillMenu gốc). */
/* (3) InGameMenu state 4 (Kỹ năng) sub 9 "Chế biến" (DrawSkillMenu 0xf2640; KeySkillMenu 0x1040a8/OK 0x104d90; TouchSkillMenu 0xfea68):
 *   Công thức = ItemTable::GetItemTableInfo(.., 15, id) (id 0..N-1, N = GetItemTableLength(15)); HERO+0x308 (u8*) mảng "đã biết" (>0), u8 HERO+0x30c = số phần tử.
 *   Trong info: s8 [+0x13e]/[+0x13f] = (loại, id) vật phẩm tạo ra (loại < 0: ô trống); 3 nguyên liệu i: loại [+0x135+i] (<0 bỏ), id [+0x138+i], số cần [+0x13b+i]; có = BagItem::GetBagItemTotalBunchCount.
 *   Trường: +0x12 chỉ số tuyệt đối, +0x11 ô trong cửa sổ 0..7 (lưới 2 hàng x 4 cột, cửa sổ = ([+0x12]-[+0x11])/4 hàng). OK gốc: chỉ chạy khi công thức đã biết.
 *   Chạm gốc: ô i (26x26): khác con trỏ -> [+0x11]=i, [+0x12]=i+cửa sổ, gv[6464+19]=0; đúng con trỏ -> KeyPressed(-5). Mũi tên ▲/▼ (chỉ khi N làm tròn lên bội 4 > 8): dịch cửa sổ 1 hàng (có vòng), copy nguyên văn 0xfeb6c..0xfefb0. */
extern H int ItemTable_GetItemTableLength(void *tbl, int cat);
enum { CK_CELL = 0xD8, CK_UP = 0xE8, CK_DN = 0xE9, CK_OK = 0xEA };
static int ck_n4(int n) { return (n & 3) ? ((n < 0 ? n + 3 : n) & ~3) + 4 : n; }
static void ck_info(u8 *buf, int idx) { for (int i = 0; i < 400; i++) buf[i] = 0; ItemTable_GetItemTableInfo(ITEMTABLE(), buf, 15, idx); }
static void ck_tri(int cx, int cy, int r, int up, u32 rgb) { for (int i = 0; i < r; i++) { int w = up ? i : r - 1 - i; mui_fill(cx - w, cy - r / 2 + i, cx + w + 1, cy - r / 2 + i + 1, rgb); } }
static void ck_paint(void *self, int x0, int y0, int x1, int y1) {
    mui_m *m = &L; u8 *h = HEROP(), rb[400], ib[400], xb[400];
    int n = ItemTable_GetItemTableLength(ITEMTABLE(), 15) & 0xffff, n4 = ck_n4(n), cur = S8(self, 0x12), win = cur - S8(self, 0x11);
    int cnt = h[0x30c]; const u8 *arr = *(const u8 **)(h + 0x308), *t; int tn, g = m->gap, c = m->cell;
    t = CT(135); mui_text((x0 + x1) / 2, y0 + 1, t, -1, m->spx, C_DIM, TF_CENTER);
    int gx = x0, gy = y0 + m->slp + g + 2, aw = c / 2 + 4 < 14 ? 14 : c / 2 + 4, gw = 4 * m->pitch - m->cg, gh = 2 * m->pitch - m->cg, ax = gx + gw + 3;
    ck_info(rb, cur >= 0 && cur < n ? cur : 0);
    for (int k = 0; k < 8; k++) {
        int idx = win + k, cx = gx + (k % 4) * m->pitch, cy = gy + (k / 4) * m->pitch, icon = -1, kn = 0;
        if (idx >= 0 && idx < n) {
            ck_info(ib, idx); int rc = (s8)ib[0x13e], rid = (s8)ib[0x13f];
            if (rc >= 0) { for (int i = 0; i < 400; i++) xb[i] = 0; ItemTable_GetItemTableInfo(ITEMTABLE(), xb, rc, rid); icon = I16(xb, 22); kn = idx < cnt && arr && (s8)arr[idx] > 0; }
        }
        mui_cell_i(cx, cy, icon, 0, -1, 0, k == S8(self, 0x11), CK_CELL + k);
        if (icon >= 0 && !kn) mui_fill_a(cx + 1, cy + 1, cx + c - 1, cy + c - 1, RGB(6, 10, 18), 150);
        mui_hit(CK_CELL + k, cx, cy, cx + c, cy + c, HK_CB, 0, 0);
    }
    if (n4 > 8) {                                                           /* ▲ / thanh cuộn / ▼ */
        for (int k = 0; k < 2; k++) {
            int ay0 = k ? gy + gh - aw : gy, in[4];
            mui_frame(ax, ay0, ax + aw, ay0 + aw, m->rad, 1, m->bt, (u32)-1, 0, in); mui_vgrad(in[0], in[1], in[2], in[3], RGB(38, 54, 78), RGB(22, 32, 48));
            ck_tri(ax + aw / 2, ay0 + aw / 2, aw / 4 > 2 ? aw / 4 : 3, k == 0, C_ORANGE_HI);
            mui_hit(k ? CK_DN : CK_UP, ax, ay0, ax + aw, ay0 + aw, HK_CB, 0, 0);
        }
        mui_scrollbar(ax + (aw - m->sbw) / 2, gy + aw + 2, gy + gh - aw - 2, win / 4, n4 / 4 > 2 ? n4 / 4 : 2, 2);
    }
    int rc = (s8)rb[0x13e], rid = (s8)rb[0x13f], known = cur >= 0 && cur < cnt && arr && (s8)arr[cur] > 0, ok = 0;
    int lx1 = ax + aw, y = gy + 2 * m->pitch + 1, s = m->s, rh = 20 * s + 4;
    mui_r box = {lx1 + g, y0 + m->slp + g + 2, x1, y1 - m->btnH - g};
    if (n4 <= 8) { lx1 = gx + gw; box.x0 = lx1 + g; }
    if (cur >= 0 && cur < n && rc >= 0) {
        for (int i = 0; i < 400; i++) xb[i] = 0; ItemTable_GetItemTableInfo(ITEMTABLE(), xb, rc, rid);
        npc_item_info(&box, xb, 0, 0, NULL);
        if (known) {
            ok = 1;
            for (int i = 0; i < 3; i++) {
                int ty = (s8)rb[0x135 + i], id = (s8)rb[0x138 + i], need = (s8)rb[0x13b + i];
                if (ty < 0 || y + rh > y1) continue;
                for (int j = 0; j < 400; j++) ib[j] = 0; ItemTable_GetItemTableInfo(ITEMTABLE(), ib, ty, id);
                int have = BagItem_GetBunch(BAGP(), ty, id); u8 tx[16]; int tl = mui_itoa(have, tx); tx[tl++] = '/'; tl += mui_itoa(need, tx + tl);
                mui_r rr = {gx, y, lx1, y + rh};
                mui_frame(rr.x0, rr.y0, rr.x1, rr.y1, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL);
                mui_icon(I16(ib, 22), gx + 2, y + 2, s, -1);
                int vw = mui_sw(m->px, tx, tl), nx = gx + 20 * s + 6, rar = npc_rarity(ib + 24);
                mui_clip(nx, y, lx1 - vw - 6, y + rh);
                mui_text(nx, y + (rh - m->px) / 2, ib + 24, -1, m->px, rar >= 0 ? (u32)rar : C_WHITE, 0);
                mui_clip_reset();
                mui_text(lx1 - 4, y + (rh - m->px) / 2, tx, tl, m->px, have < need ? C_HP : C_GREEN, TF_RIGHT);
                if (have < need) ok = 0;
                y += rh + 2;
            }
        }
    } else mui_frame(box.x0, box.y0, box.x1, box.y1, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL);
    { mui_r b = {box.x0, y1 - m->btnH, x1, y1}; t = mui_hint(CT(150), &tn); mui_button(&b, t, tn, known ? (ok ? 1 : 0) : 3, 0); mui_hit(CK_OK, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0); }
}
static void ck_cb(void *self, int id) {
    int n = ItemTable_GetItemTableLength(ITEMTABLE(), 15) & 0xffff, n4 = ck_n4(n);
    if (id >= CK_CELL && id < CK_CELL + 8) {
        int k = id - CK_CELL, win = S8(self, 0x12) - S8(self, 0x11);
        if (S8(self, 0x11) == k) mui_key(self, MUI_CLS_INGAME, -5);
        else { S8(self, 0x11) = k; S8(self, 0x12) = k + win; gv[6464 + 19] = 0; }
    } else if (id == CK_OK) mui_key(self, MUI_CLS_INGAME, -5);
    else if (id == CK_UP && n4 > 8) {
        int win = S8(self, 0x12) - S8(self, 0x11), nt = win - 4, cur = (u8)S8(self, 0x12);
        if (nt < 0) { cur = n4 - 1; nt = n4 - 8; S8(self, 0x12) = cur; }
        int c11 = (u8)(cur - nt); S8(self, 0x11) = c11;
        if ((s8)c11 > 7) { c11 = (u8)(c11 - 4); S8(self, 0x12) = c11 + nt; S8(self, 0x11) = c11; }
        gv[6464 + 19] = 0;
    } else if (id == CK_DN && n4 > 8) {
        int win2 = (S8(self, 0x12) - S8(self, 0x11)) + 4;
        if (win2 >= n4 - 7) { S8(self, 0x11) = 0; S8(self, 0x12) = 0; }
        else { int c11 = (u8)((u8)S8(self, 0x12) - win2); S8(self, 0x11) = c11; if (c11 & 128) { c11 = (u8)(c11 + 4); S8(self, 0x12) = c11 + win2; S8(self, 0x11) = c11; } }
        gv[6464 + 19] = 0;
    }
}

enum { SK_SLOT = 0xD0, SK_START = 0xD2, SK_CLR = 0xD3 };
void skc_paint(void *self) {
    mui_m *m = &L; int sub = S8(self, 0xd), n;
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(sub == 9 ? 150 : 149), -1, MUI_TAB_ICONS, NULL, 3, &body);
    int x0, y0, x1, y1; l1_geo(&x0, &y0, &x1, &y1);
    if (sub == 10) { sk_pick_paint(self, x0, y0, x1, y1, CT(155)); return; }
    if (sub == 9) { ck_paint(self, x0, y0, x1, y1); return; }
    int c = m->cell, g = m->gap, cur = S8(self, 0x11), lw = (x1 - x0) * 11 / 20, y = y0 + g;
    u8 *bag = (u8 *)HERO_GetBagItemPtr(HEROP()), *it[2] = {NULL, NULL};
    for (int i = 0; i < 2; i++) { int ix = I16(self, 0x2a + 2 * i); it[i] = ix >= 0 ? BagItem_GetBagItemPtrS(bag, ix) : NULL; }
    int bx = x0 + (lw - (2 * c + 3 * g + m->px)) / 2; if (bx < x0) bx = x0;
    for (int i = 0; i < 2; i++) {
        int cx = bx + i * (c + 2 * g + m->px);
        mui_cell_i(cx, y, it[i] ? I16(it[i], 22) : -1, 0, -1, 0, cur == i, SK_SLOT + i);
        mui_hit(SK_SLOT + i, cx, y, cx + c, y + c, HK_CB, 0, 0);
    }
    mui_text(bx + c + g + m->px / 2, y + (c - m->px) / 2, (const u8 *)"+", 1, m->px, C_WHITE, TF_CENTER | TF_SHADOW);
    y += c + g;
    { int mx = bx + (c + 2 * g + m->px) / 2; mui_text(mx + c / 2, y, (const u8 *)"?", 1, m->px, C_GOLD, TF_CENTER | TF_SHADOW); }     /* ô kết quả: gốc cũng chỉ vẽ "?" */
    y += m->lp + g;
    { int ready = it[0] && it[1]; const u8 *t = mui_hint(CT(151), &n); mui_r b = {x0, y, x0 + lw, y + m->btnH};
      mui_button(&b, t, n, cur == 2 ? 1 : (ready ? 0 : 3), 0); mui_hit(SK_START, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0); y += m->btnH + g; }
    if (it[0] || it[1]) { const u8 *t = mui_hint(CT(153), &n); mui_r b = {x0, y, x0 + lw, y + m->btnH}; mui_button(&b, t, n, 0, 0); mui_hit(SK_CLR, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0); }
    { mui_r box = {x0 + lw + g, y0 + g, x1, y1}; u8 *ci = cur <= 1 ? it[cur] : NULL;
      if (ci) npc_item_info(&box, ci, 0, 0, NULL); else mui_frame(box.x0, box.y0, box.x1, box.y1, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL); }
}
int skc_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id, sub = S8(self, 0xd);
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 4) StateInGameMenu_SetState(self, id - 0x10 + 1); return 0; }
    if (sub == 10) { sk_pick_cb(self, id); return 0; }
    if (sub == 9) { ck_cb(self, id); return 0; }
    if (id == SK_SLOT || id == SK_SLOT + 1) { S8(self, 0x11) = id - SK_SLOT; mui_key(self, MUI_CLS_INGAME, -5); }
    else if (id == SK_START) { S8(self, 0x11) = 2; mui_key(self, MUI_CLS_INGAME, -5); }
    else if (id == SK_CLR) mui_key(self, MUI_CLS_INGAME, 48);
    return 0;
}

/* (4) InGameMenu state 7 (Tinh linh) sub 4 = chọn kỹ năng Tinh linh để đăng ký vào ô [+0xf] (DrawSpiritMenu 0xef194; KeySpiritMenu sub 4 = 0x101c78, OK 0x102238, Back 0x1022b4; TouchSpiritMenu 0xfc9d4):
 *   lưới 4x4 = 16 kỹ năng Tinh linh (chỉ số kỹ năng 43+i, icon 173+i như DrawSkillBeltIcon); i "đã học" khi s8 HERO::GetSpiritInfoPtr()[22+i] > 0 (ô chưa học vẽ mờ); con trỏ s8 [+0x10] (0..15).
 *   Chạm gốc: ô i (26x26): khác con trỏ -> [+0x10]=i, gv[6464+19]=0; đúng con trỏ -> KeyPressed(-5). OK gốc: chỉ chạy khi đã học; nút OK/Back mới = phím gốc (-5/-16). */
extern H u8 *HERO_GetSpiritInfoPtr(void *hero);
extern H void CommonUi_GetSkillDiscription(void *cui, u8 *out, int a, u8 *info);
enum { SP_CELL = 0xB0, SP_OK = 0xC4 };
void sp4_paint(void *self) {
    mui_m *m = &L; int x0, y0, x1, y1, g = m->gap, c = m->cell, cur = S8(self, 0x10), n;
    u8 *h = HEROP(), *sp = HERO_GetSpiritInfoPtr(h);
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(6), -1, MUI_TAB_ICONS, NULL, 6, &body);
    l1_geo(&x0, &y0, &x1, &y1);
    int gy = y0 + g, gw = 4 * m->pitch - m->cg;
    for (int i = 0; i < 16; i++) {
        int x = x0 + (i % 4) * m->pitch, y = gy + (i / 4) * m->pitch, learned = sp && (s8)sp[22 + i] > 0;
        mui_cell_i(x, y, 173 + i, 0, -1, 0, i == cur, SP_CELL + i);
        if (!learned) mui_fill_a(x + 1, y + 1, x + c - 1, y + c - 1, RGB(6, 10, 18), 150);
        mui_hit(SP_CELL + i, x, y, x + c, y + c, HK_CB, 0, 0);
    }
    int ci = cur >= 0 && cur < 16 ? cur : 0, learned = sp && (s8)sp[22 + ci] > 0; u8 *in = HERO_GetHeroSkillInfoPtr(h, 43 + ci);
    mui_r box = {x0 + gw + g, gy, x1, y1 - m->btnH - g};
    {
        static u8 desc[600]; static int last = -1; u8 meta[40]; int mn = 0; const u8 *l13 = CT(13);
        while (l13[mn] && mn < 20) { meta[mn] = l13[mn]; mn++; }
        meta[mn++] = ' '; mn += mui_itoa(I8(h, 588 + 43 + ci) < 0 ? 0 : I8(h, 588 + 43 + ci), meta + mn);
        const u8 *nm = *(u8 **)(in + 4);
        mui_hit(0xC5, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
        int y = mui_descbox2(&box, nm ? nm : (const u8 *)"", -1, -1, meta, mn);
        for (int i = 0; i < 600; i++) desc[i] = 0;
        CommonUi_GetSkillDiscription(COMMONUI(), desc, -1, in); desc[599] = 0;
        l1_desc(&box, y, desc, 1, last != ci); last = ci;
    }
    mui_r b = {box.x0, y1 - m->btnH, x1, y1}; const u8 *bt = mui_hint(CT(56), &n);
    mui_button(&b, bt, n, learned ? 1 : 3, 0); mui_hit(SP_OK, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}
int sp4_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 7) StateInGameMenu_SetState(self, id - 0x10 + 1); return 0; }
    if (id >= SP_CELL && id < SP_CELL + 16) {
        if (S8(self, 0x10) == id - SP_CELL) mui_key(self, MUI_CLS_INGAME, -5);
        else { S8(self, 0x10) = id - SP_CELL; gv[6464 + 19] = 0; }
    } else if (id == SP_OK) mui_key(self, MUI_CLS_INGAME, -5);
    return 0;
}

/* (5) MainMenu state 9 (Cài đặt) sub 1 = Quản lý dữ liệu (DrawOption 0x11a984: DrawSubjectBox(MenuText(0)) + nút 138x17 giữa màn (halfW-69, halfH-7) nhãn MenuText(1);
 *   KeyOption: OK(-5) -> SetPopupState(13, MenuText(2)+CommonText(329)) (xác nhận lưu dữ liệu), Back(-16) -> 0x11f004; TouchOption 0x1188d0: x(HW-70,HW+69] y(HH-8,HH+10] -> KeyPressed(-5)). Nút mới = KeyPressed(-5) gốc. */
extern H const u8 *TextMgr_GetMenuText(u32 tm, int i);
void mmd_paint(void *self) {
    mui_m *m = &L; mui_r c; int n; (void)self;
    mm_dimwin(TextMgr_GetMenuText(_pTextMgr, 0), &c);
    const u8 *t = mui_hint(TextMgr_GetMenuText(_pTextMgr, 1), &n);
    int bw = mui_sw(m->px, t, n) + 8 * m->pad, bh = m->btnH + 2 * m->gap, cw = c.x1 - c.x0;
    if (bw > cw) bw = cw; if (bw < cw / 2) bw = cw / 2;
    int x = (c.x0 + c.x1 - bw) / 2, y = (c.y0 + c.y1 - bh) / 2;
    mui_r b = {x, y, x + bw, y + bh};
    mui_button(&b, t, n, 1, 0); mui_hit(0xD0, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}
int mmd_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_MAIN, x, y), id = mui_last_id;
    if (k == HK_CB && id == 0xD0) mui_key(self, MUI_CLS_MAIN, -5);
    return 0;
}
