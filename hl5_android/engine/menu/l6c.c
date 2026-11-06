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
enum { SK_SLOT = 0xD0, SK_START = 0xD2, SK_CLR = 0xD3 };
void skc_paint(void *self) {
    mui_m *m = &L; int sub = S8(self, 0xd), n;
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(149), -1, MUI_TAB_ICONS, NULL, 3, &body);
    int x0, y0, x1, y1; l1_geo(&x0, &y0, &x1, &y1);
    if (sub == 10) { sk_pick_paint(self, x0, y0, x1, y1, CT(155)); return; }
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
    if (id == SK_SLOT || id == SK_SLOT + 1) { S8(self, 0x11) = id - SK_SLOT; mui_key(self, MUI_CLS_INGAME, -5); }
    else if (id == SK_START) { S8(self, 0x11) = 2; mui_key(self, MUI_CLS_INGAME, -5); }
    else if (id == SK_CLR) mui_key(self, MUI_CLS_INGAME, 48);
    return 0;
}
