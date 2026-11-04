/* L1/T17c6: Chuyên gia (StateInExpertMenu): state 0 = chuẩn bị (3 nút), state 1 = cửa hàng (lưới 4x4, 3 vật phẩm). Số đo: ui_new/menu/LAYOUT.md §12 (chuyen_gia_*), hit_rects.json.
 * Kiến trúc A: Paint + Touch mới; KeyPressed/Process/SetPopupState gốc (popup = StatePopup loại 1/2: popup.c).
 * Trường gốc (KeyReady 0xe1818, KeyShop 0xe16b0, KeyCommonShop 0xe14a4, dò probe_ex*.py): +0xc state;
 *   state 0: +0xe nút (0 Nhân vật, 1 Cửa hàng, 2 Bắt đầu; OK gốc tương ứng); chế độ = s8 gv[6457] (1 Cầm cự, 2 Đua Thời Gian, 3 Đột phá; tên = CT(195+chế độ)).
 *   state 1: +0xf ô trong lưới (0..15, 4 cột, KHÔNG cuộn), +0x10 chỉ số tuyệt đối (= +0xf); chỉ 3 ô đầu là hàng: ItemTable::GetItemTableInfo(cat 11, id 11+chỉ số), giá = ItemInfo+0x30 (TRẢ bằng tiền game: Process 0xe275c trừ tiền,
 *   không đủ -> popup CT(90)); OK ở ô >= 3 không làm gì.
 * Chạm: nút = đặt +0xe rồi KeyPressed(-5); ô lưới đang chọn hoặc nút mua = KeyPressed(-5), ô khác = đặt +0xf/+0x10. */
#include "l1ui.h"

#define H_START 0x41
#define H_CHAR 0x42
#define H_SHOP 0x43
#define H_CELL 0x10
#define H_DET 0x40
#define H_BUY 0x41
#define H_SCR 0x42
#define NSHOP 3
static u8 ib[512];                                      /* ItemInfo tạm (kích thước ItemInfo 312) */
static u8 *shop_item(int idx) {
    if (idx < 0 || idx >= NSHOP) return NULL;
    for (int i = 0; i < 512; i++) ib[i] = 0;
    ItemTable_GetItemTableInfo(ITEMTABLE(), ib, 11, 11 + idx);
    return ib;
}
static void ring_text(int x, int y, const u8 *s, int len, u32 col, int fl) { mui_text(x, y, s, len, L.px, col, fl); }

/* sprite nhân vật trong vòng chân dung (như hero_sprite của ingame.c, nhưng không cần InGameMenu: khung 0) */
static void hero_sprite0(int x, int y, int pr) {
    u8 *h = HEROP(); u32 g = _lcdG; void *spr; int a = 0, b = 1, dy = 0, cls = h[0x22c] & 3;
    int R = pr / 2, band = pr / 12 < 2 ? 2 : pr / 12, r = R - 2 - band, cx = x + R, cy = y + R;
    if (!HERO_IsNoneEquipWeapon(h)) { a = 2; b = cls >= 2 ? 2 : 1; dy = cls == 1 ? 3 : 0; }
    else if (cls == 3 && !HERO_IsNoneEquipShield(h)) { a = 2; b = 2; }
    spr = CHAR_GetSpritePtr(h);
    if (!spr) return;
    Graphic_SetClip(g, cx - r, cy - r, 2 * r, 2 * r);
    SPRITE_DrawSprite(spr, Graphic_GetFB(g), cx, cy + r * 55 / 100 + dy, a, b, 0, 0);
    Graphic_ResetClip(g);
}

/* ---------------- state 0: chuẩn bị ---------------- */
static void paint_ready(void *self) {
    mui_m *m = &L; u8 *h = HEROP(); int s = m->s, in[4];
    (void)self;
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(195), -1, 0, NULL, 0, &body);
    int x0 = m->wx + m->pad, y0 = m->wy + m->titleH + m->pad, x1 = m->wx + m->winW - m->pad - (2 * m->bt + 1), y1 = m->wy + m->winH - m->pad - (2 * m->bt + 1);   /* khớp mockup (hit_rects.json): nội dung thu 2bt+1 so với cash.c */
    int cw = x1 - x0 + 2 * m->bt + 1, lw = (cw * 46 + 50) / 100, g = m->gap + 2 + (m->hd && m->s >= 2);
    /* cột trái: tóm tắt nhân vật (như InGameMenu: vòng chân dung, tên, LV, lớp, 3 thanh) */
    l1_panel(x0, y0, x0 + lw, y1);
    int pr = m->hd ? 84 : 22 * s + 20, px_ = x0 + m->pad, yy = y0 + m->pad;
    mui_portrait(px_, yy, pr); hero_sprite0(px_, yy, pr);
    int nx = px_ + pr + m->pad / 2 + 2, ty = yy + ((pr - 3 * m->lp) / 2 > 0 ? (pr - 3 * m->lp) / 2 : 0), mw = x0 + lw - nx - m->pad;
    u8 lv[40]; int n = 0; const u8 *l13 = CT(13);
    while (l13[n] && n < 30) { lv[n] = l13[n]; n++; }
    lv[n++] = ' '; n += mui_itoa(h[0x22d], lv + n);
    mui_clip(nx, 0, nx + mw, m->Ht);
    mui_text(nx, ty, CT(9), -1, m->px, C_GOLD, TF_SHADOW);
    mui_text(nx, ty + m->lp, lv, n, m->px, C_WHITE, 0);
    mui_text(nx, ty + 2 * m->lp, CT(178 + (h[0x22c] & 3)), -1, m->px, C_DIM, 0);
    mui_clip_reset();
    yy += (pr > 3 * m->lp ? pr : 3 * m->lp) + m->pad;
    int bw = lw - 2 * m->pad;
    mui_bar(px_, yy, bw, CT(10), -1, I32(h, 0xf0), I32(h, 0x180), C_HP); yy += m->barH + m->pad - 1;
    mui_bar(px_, yy, bw, CT(11), -1, I16(h, 0x248) / 10, I16(h, 0x2e6) / 10, C_SP); yy += m->barH + m->pad - 1;
    mui_bar(px_, yy, bw, CT(12), -1, I32(h, 0x230), I32(h, 0x2d8), C_EXP);
    /* cột phải: 2 hàng k-v + 3 nút */
    int rx0 = x0 + lw + g, rh = m->hd ? m->px + 6 : m->spx + 5, py = y0;
    int mode = (s8)gv[6457];
    const u8 *mt = mode >= 1 && mode <= 3 ? CT(195 + mode) : CT(196);
    u8 lvs[12]; int ln = mui_itoa(h[0x22d], lvs);
    l1_panel(rx0, py, x1, py + 2 * rh + 2 * m->pad);
    const u8 *lab[2] = {CT(299), CT(185)};
    for (int i = 0; i < 2; i++) {
        int ry = py + m->pad + i * rh, ty2 = ry + (rh - m->px) / 2;
        mui_text(rx0 + m->pad, ty2, lab[i], -1, m->px, C_DIM, 0);
        if (i == 0) mui_text(x1 - m->pad, ty2, mt, -1, m->px, C_GOLD, TF_RIGHT | TF_SHADOW);
        else mui_text(x1 - m->pad, ty2, lvs, ln, m->px, C_WHITE, TF_RIGHT);
        if (i == 0) mui_fill(rx0 + m->pad, ry + rh - 1, x1 - m->pad, ry + rh, RGB(40, 54, 76));
    }
    int by = y1 - m->btnH, b1 = by - m->gap - m->btnH, hw = (x1 - rx0 - m->gap) / 2, bn;
    mui_r b = {rx0, by, x1, by + m->btnH};
    const u8 *t = mui_hint(CT(166), &bn);
    mui_button(&b, t, bn, 1, 0); mui_hit(H_START, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
    b.y0 = b1; b.y1 = b1 + m->btnH; b.x0 = rx0; b.x1 = rx0 + hw;
    t = mui_hint(CT(0), &bn); mui_button(&b, t, bn, 0, 0); mui_hit(H_CHAR, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
    b.x0 = x1 - hw; b.x1 = x1;
    t = mui_hint(CT(244), &bn); mui_button(&b, t, bn, 0, 0); mui_hit(H_SHOP, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
    (void)in; (void)ring_text;
}

/* ---------------- state 1: cửa hàng ---------------- */
static void paint_shop(void *self) {
    mui_m *m = &L; int win = S8(self, 0xf), ab = S8(self, 0x10);
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(244), -1, 0, NULL, 0, &body);
    int x0 = m->wx + m->pad, y0 = m->wy + m->titleH + m->pad, x1 = m->wx + m->winW - m->pad - (2 * m->bt + 1), y1 = m->wy + m->winH - m->pad - (2 * m->bt + 1);   /* khớp mockup (hit_rects.json): nội dung thu 2bt+1 so với cash.c */
    int gx = x0 + m->bt + 2, gy = y0 + m->bt + 2;
    mui_hit(H_SCR, gx, gy, gx + m->gridW + 3 + m->sbw, gy + m->gridW, HK_NONE, 0, 0);
    for (int r = 0; r < 4; r++)
        for (int c = 0; c < 4; c++) {
            int k = 4 * r + c; u8 *it = shop_item(k);
            mui_cell_i(gx + c * m->pitch, gy + r * m->pitch, it ? I16(it, 22) : -1, 0, -1, 0, k == win, H_CELL + k);
        }
    mui_scrollbar(gx + m->gridW + 3, gy, gy + m->gridW, 0, 4, 4);
    int dx0 = gx + m->gridW + 3 + m->sbw + m->gap + 2, by = y1 - m->btnH, pr = m->lp + 2, py = by - m->gap - pr;
    mui_r box = {dx0, gy, x1, py - m->gap};
    u8 *it = shop_item(ab);
    npc_item_info(&box, it, 0, 0, NULL);
    mui_hit(H_DET, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
    int money = BagItem_GetMoney(HERO_GetBagItemPtr(HEROP())), price = it ? I32(it, 0x30) : 0;
    mui_frame(dx0, py, x1, py + pr, m->rad, 0, 1, RGB(10, 16, 26), 235, NULL);
    u8 num[24]; int n;
    n = mui_money(money, num);
    mui_text(dx0 + m->pad, py + (pr - m->spx) / 2, num, n, m->spx, C_DIM, 0);
    if (it) { n = mui_money(price, num); mui_text(x1 - m->pad, py + (pr - m->px) / 2, num, n, m->px, money >= price ? C_GOLD : RGB(255, 96, 80), TF_RIGHT | TF_SHADOW); }
    mui_r b = {dx0, by, x1, by + m->btnH};
    int bn; const u8 *bt = mui_hint(CT(84), &bn);
    mui_button(&b, bt, bn, it && money >= price ? 1 : 3, 0); mui_hit(H_BUY, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}

void expert_paint(void *self) { if (GS(self) == 1) paint_shop(self); else paint_ready(self); }

int expert_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_EXPERT, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (GS(self) == 0) {
        if (id == H_START) S8(self, 0xe) = 2;
        else if (id == H_CHAR) S8(self, 0xe) = 0;
        else if (id == H_SHOP) S8(self, 0xe) = 1;
        else return 0;
        mui_key(self, MUI_CLS_EXPERT, -5);
    } else {
        if (id >= H_CELL && id < H_CELL + 16) {
            int w = id - H_CELL;
            if (w == S8(self, 0xf)) mui_key(self, MUI_CLS_EXPERT, -5);       /* ô đang chọn: chạm lần 2 = OK gốc */
            else { S8(self, 0xf) = w; S8(self, 0x10) = w; }
        } else if (id == H_BUY) mui_key(self, MUI_CLS_EXPERT, -5);
    }
    return 0;
}
int expert_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_EXPERT, x, y); }
