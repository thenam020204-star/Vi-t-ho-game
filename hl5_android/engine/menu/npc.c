/* L2 (T17c5): StateInNpcMenu (8 cửa sổ) + popup NPC (StatePopup loại 4 có giá). Số đo: ui_new/menu/LAYOUT.md §12 + mock_b2b.py (shelf, w_slot, w_statrows).
 * Kiến trúc A: Paint + Touch mới; KeyPressed/Process/SetPopupState GỐC. Chạm = ghi trường con trỏ gốc rồi KeyPressed(-5) gốc.
 * Trường StateInNpcMenu (dò động bằng harness/t17c5/q2.py, so với gốc): +0xc shopId, +0xe state (0 Mua kỹ năng, 1 Mua vật phẩm, 2 Bán, 3 Tôi luyện thường,
 * 4 Tôi luyện cao cấp, 5 Orb, 6 Chế biến, 7 Bộ sưu tập), +0xf chế độ con (GetSubState), +0x10.. con trỏ (xem từng cửa sổ).
 *  0: [0x10] ô 0..15 (lưới 4x4, UP/DOWN +-4 vòng 16, LEFT/RIGHT vòng trong hàng); sách = GetSearchSkillBook(buf) -> id[ô], ItemTable cat 16+lớp/2.
 *  1: [0xf] 0 chọn tab / 1 lưới; [0x10] tab 0..2; [0x11]=[0x12] ô 0..15; vật phẩm = NpcShop::GetItemInfo(shop=[gv+5140], out, slot), tab theo loại (cat<=4 / 5..10 / 11).
 *  2: [0xf] 0 chọn trang / 1 lưới; [0x10] trang; [0x11] ô 0..15; giá bán = ItemInfo+0x30 (0 = không bán được).
 * Popup NPC = StatePopup loại 4 (giá int tại +0x20, thông điệp +0x24, kết quả *[+0x14], nút +0x43c+48i). */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define I8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define I16(p, o) (*(s16 *)((u8 *)(p) + (o)))
#define I32(p, o) (*(int *)((u8 *)(p) + (o)))
#define BAGP() ((u8 *)HERO_GetBagItemPtr(HEROP()))
#define CUI_SPR(k) (COMMONUI() + 5964 + 24 * (k))
#define SHOP() (*(u8 **)(gv + 5140))

extern H int NPC_GetSearchSkillBook(void *self, s8 *out);
extern H int NpcShop_GetItemInfo(void *shop, void *out, int slot);

enum { H_CELL = 0x40, H_TAB = 0x70, H_PAGE = 0x78, H_BUY = 0x90, H_DETAIL = 0x91, H_SCR = 0x92 };
static u8 ib[400];                                     /* ItemInfo tạm (kích thước ItemInfo 312) */

static void clr(void) { for (int i = 0; i < 400; i++) ib[i] = 0; }

/* ---------------- khung chung ---------------- */
typedef struct { int x0, y0, x1, y1, ty, gx, gy, sbx, dx, by, py; } geo_t;
static geo_t G;

static void coin_num(int xr, int y, int v, u32 col) {                 /* [xu] + số, căn phải tại xr */
    mui_m *m = &L; u8 t[16]; int n = mui_money(v, t), w = mui_sw(m->px, t, n), s = m->s;
    const u8 *coin = CUI_SPR(16); int cw = *(const u16 *)(coin + 2), ch = *(const u16 *)(coin + 4);
    mui_text(xr, y, t, n, m->px, col, TF_RIGHT);
    mui_gbm(coin, xr - w - 3 - cw * s, y + (m->px - ch * s) / 2 + (s == 1 ? 1 : 0), s, -1);
}
static int coin_k(void) { return L.s; }

/* cửa sổ + dải tiền; trả hình học. lbl = nhãn bên trái dải (NULL = CT(19) Tiền hiện có); val<0 không vẽ tiền */
static void frame_money(void *self, const u8 *title, int strip) {
    mui_m *m = &L; mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    Graphic_CopyFrameBuffer(_lcdG, Graphic_GetFB(_lcdG), Graphic_GetFBOff(_lcdG));
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 150);
    mui_window(&wr, title, -1, 0, NULL, 0, &body);
    int ins = m->bt + 2;     /* khay b2b: lề nội dung = content_rect + (bt+2) mỗi phía (khớp hit_rects) */
    G.x0 = m->wx + m->pad + ins; G.y0 = m->wy + m->titleH + m->pad + ins; G.x1 = m->wx + m->winW - m->pad - ins; G.y1 = m->wy + m->winH - m->pad - ins;
    G.ty = G.y0; (void)self;
    if (strip) {
        int sh = m->lp + 2 * m->s + 2;
        mui_text(G.x0 + 2, G.y0 + 1, CT(19), -1, m->px, C_DIM, 0);
        coin_num(G.x1 - 2, G.y0 + 1, BagItem_GetMoney(BAGP()), C_GOLD);
        mui_fill(G.x0, G.y0 + sh - 1, G.x1, G.y0 + sh, C_ORANGE_DK);
        G.ty = G.y0 + sh + m->gap;
    }
    G.by = G.y1 - m->btnH;
}

static void subtabs(void *self, int n, int active, const u8 *const *lab) {
    mui_m *m = &L; int sh = m->px + 2 * (m->bt + 3) + 2, g2 = 2, cw = G.x1 - G.x0, w = (cw - (n - 1) * g2) / n, ox = (cw - n * w - (n - 1) * g2) / 2;
    for (int i = 0; i < n; i++) {
        mui_r r = {G.x0 + ox + i * (w + g2), G.ty, G.x0 + ox + i * (w + g2) + w, G.ty + sh};
        mui_button(&r, lab[i], -1, i == active ? 1 : 0, H_TAB + i);
    }
    G.ty += sh + m->gap; (void)self;
}

/* lưới 4x4 + thanh cuộn (tuỳ chọn) ; trả (gx,gy), dx = x bắt đầu hộp chi tiết */
static void grid_geo(int scroll) {
    mui_m *m = &L; G.gx = G.x0; G.gy = G.ty;
    if (scroll) { G.sbx = G.gx + m->gridW + 3; G.dx = G.sbx + m->sbw + m->gap + 2; }
    else { G.gx = G.x0 + m->pitch + m->gap + 1; G.dx = G.gx + m->gridW + m->gap + 1; }
}
static void price_button(void *self, const u8 *label, int price, int ok, int hasprice) {
    mui_m *m = &L; int prow = m->lp + 2;
    const u8 *coin = CUI_SPR(16); int chh = *(const u16 *)(coin + 4) * coin_k() + 2;
    if (chh > prow) prow = chh;
    G.py = G.by - m->gap - prow;
    if (hasprice) {
        int in[4];
        mui_frame(G.dx, G.py, G.x1, G.py + prow, m->rad, 0, 1, RGB(10, 16, 26), 235, in);
        coin_num(G.x1 - m->pad, G.py + (prow - m->px) / 2, price, ok ? C_GOLD : RGB(255, 96, 80));
    }
    mui_r b = {G.dx, G.by, G.x1, G.y1};
    mui_button(&b, label, -1, ok ? 1 : 3, H_BUY);
    (void)self;
}

/* ---------------- 0: Mua kỹ năng ---------------- */
static u8 *skill_item(void *self, int cell, s8 *ids) {
    if (cell < 0 || cell > 9 || ids[cell] < 0) return NULL;
    clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, 16 + (HEROP()[0x22c] >> 1), ids[cell]); (void)self;
    return ib;
}
static void paint_skillbuy(void *self) {
    mui_m *m = &L; s8 ids[40]; int cur = S8(self, 0x10), money = BagItem_GetMoney(BAGP());
    for (int i = 0; i < 40; i++) ids[i] = -1;
    NPC_GetSearchSkillBook(self, ids);
    frame_money(self, CT(83), 1); grid_geo(1);
    int rows = 4;
    mui_scrollbar(G.sbx, G.gy, G.gy + 4 * m->pitch - m->cg, 0, rows, 4);
    for (int k = 0; k < 16; k++) {
        u8 *it = skill_item(self, k, ids);
        mui_cell_i(G.gx + (k % 4) * m->pitch, G.gy + (k / 4) * m->pitch, it ? I16(it, 22) : -1, 0, -1, 0, k == cur, H_CELL + k);
    }
    u8 *it = skill_item(self, cur, ids);
    int price = it ? I32(it, 0x30) : 0, ok = it && money >= price;
    price_button(self, CT(83), price, ok, 1);
    mui_r box = {G.dx, G.gy, G.x1, G.py - m->gap};
    if (it) npc_item_info(&box, it, 0, 0, NULL);
    else npc_item_info(&box, NULL, 0, 0, NULL);
}

/* ---------------- 1: Mua vật phẩm ---------------- */
static int tab_of(int cat) { return cat <= 4 ? 0 : cat <= 10 ? 1 : 2; }
/* mục thứ k của tab t trong cửa hàng -> ItemInfo ib (NULL nếu hết) */
static u8 *shop_item(int tab, int k) {
    int n = 0;
    for (int slot = 0; slot < 64; slot++) {
        clr();
        if (!(NpcShop_GetItemInfo(SHOP(), ib, slot) & 255)) { if (slot >= 40) break; continue; }
        if (tab_of(I8(ib, 20)) != tab) continue;
        if (n++ == k) return ib;
    }
    return NULL;
}
static void paint_itembuy(void *self) {
    mui_m *m = &L; int sub = S8(self, 0xf), tab = S8(self, 0x10), cur = S8(self, 0x11), money = BagItem_GetMoney(BAGP());
    frame_money(self, CT(84), 1);
    const u8 *lab[3] = {CT(95), CT(242), CT(96)};
    subtabs(self, 3, tab, lab); grid_geo(1);
    mui_scrollbar(G.sbx, G.gy, G.gy + 4 * m->pitch - m->cg, 0, 4, 4);
    for (int k = 0; k < 16; k++) {
        u8 *it = shop_item(tab, k);
        mui_cell_i(G.gx + (k % 4) * m->pitch, G.gy + (k / 4) * m->pitch, it ? I16(it, 22) : -1, 0, it ? npc_rarity(it + 24) : -1, 0, sub == 1 && k == cur, H_CELL + k);
    }
    u8 *it = sub == 1 ? shop_item(tab, cur) : shop_item(tab, 0);
    int price = it ? I32(it, 0x30) : 0;
    price_button(self, CT(84), price, it && money >= price, 1);
    mui_r box = {G.dx, G.gy, G.x1, G.py - m->gap};
    npc_item_info(&box, it, 0, 0, NULL);
}

/* ---------------- 2: Bán vật phẩm ---------------- */
static void paint_sale(void *self) {
    mui_m *m = &L; u8 *bag = BAGP(); int sub = S8(self, 0xf), page = S8(self, 0x10), cur = S8(self, 0x11);
    int size = BagItem_GetSize(bag), count = BagItem_GetCount(bag), npg = size / 16;
    if (npg < 1) npg = 1;
    if (npg > 4) npg = 4;
    frame_money(self, CT(85), 1); grid_geo(0);
    for (int i = 0; i < npg; i++) {
        int y = G.gy + i * m->pitch, in[4];
        mui_frame(G.x0, y, G.x0 + m->pitch, y + m->cell, m->rad, i == page, i == page ? m->bt : 1, (u32)-1, 0, in);
        if (i == page) mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12));
        else mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46));
        const u8 *ic = CUI_SPR(1); int k = (m->cell - 2) / 22; if (k > m->s) k = m->s; if (k < 1) k = 1;
        mui_gbm(ic, G.x0 + m->pitch / 2 - (*(const u16 *)(ic + 2) * k) / 2, y + m->cell / 2 - (*(const u16 *)(ic + 4) * k) / 2 - (m->hd ? m->s : 0), k, -1);
        u8 t[4]; int tn = mui_itoa(i + 1, t), tw = mui_sw(m->spx, t, tn);
        mui_text(G.x0 + m->pitch - 3 - tw, y + m->cell - 3 - m->spx, t, tn, m->spx, C_WHITE, TF_SHADOW);
        mui_hit(H_PAGE + i, G.x0, y, G.x0 + m->pitch, y + m->cell, HK_CB, 0, 0);
    }
    u8 *sel = NULL;
    for (int k = 0; k < 16; k++) {
        int idx = page * 16 + k; u8 *it = idx < count ? BagItem_GetBagItemPtr(bag, page, k) : NULL;
        if (k == cur && sub == 1) sel = it;
        int cnt = it ? *(u8 *)(bag + 0x188 + idx) : 0;
        int fl = it && I8(it, 20) >= 0 && I8(it, 20) <= 10 && EquipItemInfo_IsEquipIng(it);
        mui_cell_i(G.gx + (k % 4) * m->pitch, G.gy + (k / 4) * m->pitch, it ? I16(it, 22) : -1, cnt, it ? npc_rarity(it + 24) : -1, fl, sub == 1 && k == cur, H_CELL + k);
    }
    int price = sel ? I32(sel, 0x30) : 0;
    price_button(self, CT(85), price, sel && price > 0, 1);
    mui_r box = {G.dx, G.gy, G.x1, G.py - m->gap};
    npc_item_info(&box, sel, 0, 0, NULL);
}

/* ---------------- 7: Bộ sưu tập (chỉ màn menu 3 nút, [0xf]==0; lưới/đăng ký giữ đường gốc) ---------------- */
extern H int HERO_GetCollectionCompleteTotalCount(void *hero);
static void paint_collmenu(void *self) {
    mui_m *m = &L; int cur = S8(self, 0x10), tot = 0, n, in[4]; u8 t[24];
    frame_money(self, CT(284), 0);
    for (int i = 0; i < 10; i++) tot += (s8)gv[6498 + 42 * i];
    int sh = m->lp + 2 * m->s + 2;
    mui_text(G.x0 + 2, G.y0 + 1, CT(283), -1, m->px, C_DIM, 0);
    n = mui_itoa(HERO_GetCollectionCompleteTotalCount(HEROP()), t); t[n++] = '/'; n += mui_itoa(tot, t + n);
    mui_text(G.x1 - 2, G.y0 + 1, t, n, m->px, C_GOLD, TF_RIGHT);
    mui_fill(G.x0, G.y0 + sh - 1, G.x1, G.y0 + sh, C_ORANGE_DK);
    int bw = ((G.x1 - G.x0) * 62 + 50) / 100 > 420 ? 420 : ((G.x1 - G.x0) * 62 + 50) / 100, bh = m->btnH + 2 * m->s, gp = m->gap + m->gap * 3 / 4, th = 3 * bh + 2 * gp, y = G.y0 + sh + (G.y1 - G.y0 - sh - th) / 2;
    for (int i = 0; i < 3; i++) {
        mui_r b = {(G.x0 + G.x1 - bw) / 2, y + i * (bh + gp), (G.x0 + G.x1 + bw) / 2, y + i * (bh + gp) + bh};
        mui_button(&b, CT(269 + i), -1, i == cur ? 1 : 0, H_CELL + i);
    }
    (void)in;
}

/* ---------------- Paint / Touch ---------------- */
void npc_paint(void *self) {
    switch (S8(self, 0xe)) {
    case 0: paint_skillbuy(self); break;
    case 1: paint_itembuy(self); break;
    case 2: paint_sale(self); break;
    case 7: paint_collmenu(self); break;
    }
}
int npc_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_NPC, x, y); }

static void cell_tap(void *self, int k) {
    int st = S8(self, 0xe);
    if (st == 7) { S8(self, 0x10) = k; mui_key(self, MUI_CLS_NPC, -5); return; }
    if (st == 0) { if (S8(self, 0x10) == k) mui_key(self, MUI_CLS_NPC, -5); else S8(self, 0x10) = k; }
    else if (st == 1) {
        if (S8(self, 0xf) == 1 && S8(self, 0x11) == k) mui_key(self, MUI_CLS_NPC, -5);
        else { S8(self, 0xf) = 1; S8(self, 0x11) = k; S8(self, 0x12) = k; }
    } else if (st == 2) {
        if (S8(self, 0xf) == 1 && S8(self, 0x11) == k) mui_key(self, MUI_CLS_NPC, -5);
        else { S8(self, 0xf) = 1; S8(self, 0x11) = k; }
    }
}
int npc_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_NPC, x, y), id = mui_last_id, st = S8(self, 0xe);
    if (k != HK_CB) return 0;
    if (id >= H_CELL && id < H_CELL + 16) cell_tap(self, id - H_CELL);
    else if (id >= H_TAB && id < H_TAB + 3 && st == 1) { S8(self, 0xf) = 0; S8(self, 0x10) = id - H_TAB; }
    else if (id >= H_PAGE && id < H_PAGE + 4 && st == 2) { S8(self, 0xf) = 0; S8(self, 0x10) = id - H_PAGE; }
    else if (id == H_BUY) {
        if (st == 1 && S8(self, 0xf) == 0) S8(self, 0xf) = 1;
        if (st == 2 && S8(self, 0xf) == 0) S8(self, 0xf) = 1;
        mui_key(self, MUI_CLS_NPC, -5);
    }
    return 0;
}

/* ---------------- popup NPC (StatePopup loại 4: giá + thông điệp + 1..2 nút) ---------------- */
void npcpop_paint(void *self) {
    mui_m *m = &L; u32 par = *(u32 *)((u8 *)self + 8);
    if (par) { void (**vt)(void *) = *(void (***)(void *))par; vt[0x40 / 4]((void *)par); }
    if (!mui_begin()) return;
    s8 *res = *(s8 **)((u8 *)self + 0x14);
    if (res && *res == -1) return;
    int nb = POP_NBTN(self), cur = res ? *res : 0, price = I32(self, 0x20), n1, n2;
    const u8 *msg = (u8 *)self + 0x24, *l1 = mui_hint((u8 *)self + 0x43c, &n1), *l2 = nb > 1 ? mui_hint((u8 *)self + 0x43c + 48, &n2) : l1;
    if (nb < 2) n2 = 0;
    int pw = m->hd ? 420 : (55 * m->W + 50) / 100, inw, in[4], tx = m->pad + 2 * m->s + 4;
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    inw = pw - 2 * tx;
    u16 st[4], ln[4]; int nl = mui_wrap(m->px, msg, mui_cslen(msg), inw, st, ln, 4);
    int hp = price > 0 ? m->lp + 2 : 0, ph = 2 * m->pad + 4 * m->s + hp + nl * m->lp + m->pad + m->btnH;
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int yy = in[1] + m->pad + 2 * m->s;
    if (price > 0) { coin_num((m->W) / 2 + 24 * m->s, yy, price, C_GOLD); yy += hp; }
    for (int i = 0; i < nl; i++, yy += m->lp) mui_text_line(m->W / 2, yy, msg, st[i], ln[i], m->px, C_WHITE, TF_CENTER);
    yy += m->pad;
    int bw = nb > 1 ? (in[2] - in[0] - 2 * m->pad - m->gap) / 2 : in[2] - in[0] - 2 * m->pad;
    for (int i = 0; i < nb; i++) {
        mui_r b = {in[0] + m->pad + i * (bw + m->gap), yy, in[0] + m->pad + i * (bw + m->gap) + bw, yy + m->btnH};
        mui_button(&b, i ? l2 : l1, i ? n2 : n1, i == cur ? 1 : 0, 0xA0 + i);
    }
}
int npcpop_touch(void *self, int x, int y) {
    s8 *res = *(s8 **)((u8 *)self + 0x14); int nb = POP_NBTN(self);
    int k = mui_touch_press(self, MUI_CLS_POPUP, x, y), id = mui_last_id;
    if (k == HK_CB && id >= 0xA0 && id < 0xA0 + nb && res) { *res = id - 0xA0; mui_key(self, MUI_CLS_POPUP, -5); }
    return 0;
}
