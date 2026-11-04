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
extern H int HERO_IsCollectionComplete(void *hero, int cls, int idx);
extern H int HERO_GetCollectionCompleteCount(void *hero, int cls);
extern H int BagItem_GetBagItemTotalBunchCount(void *bag, int cat, int id);

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

/* ---------------- 7 (lưới): Bộ sưu tập [0xf] 1..2 = hàng tab (Vũ khí 4 tab / Giáp 5 tab), 3 = Trang sức (không tab, lưới), 4..5 = con trỏ trong lưới (Vũ khí/Giáp)
 * [0x10] loại 0..2, [0x11] tab; [0x12] con trỏ trong cửa sổ 0..15, [0x13] chỉ số tuyệt đối; đỉnh cửa sổ = [0x13]-[0x12].
 * lớp bảng tidx = tab (Vũ khí) / 4+tab (Giáp) / 9; loại vật phẩm cat = tab / 5+tab / 10; số mục = (s8)gv[6498+42*tidx]; id = (s8)gv[6499+42*tidx+idx];
 * đã đăng ký = HERO::IsCollectionComplete(tidx, idx); có trong túi = BagItem::GetBagItemTotalBunchCount(cat, id) > 0 (đăng ký được). ---------------- */
static void coll_idx(void *self, int *tidx, int *cat, int *count, int *ntab) {
    int c = S8(self, 0x10), t = S8(self, 0x11);
    if (c == 0) { *tidx = t; *cat = t; *ntab = 4; }
    else if (c == 1) { *tidx = 4 + t; *cat = 5 + t; *ntab = 5; }
    else { *tidx = 9; *cat = 10; *ntab = 0; }
    if (*tidx < 0 || *tidx > 9) *tidx = 0;
    *count = (s8)gv[6498 + 42 * *tidx];
}
static int coll_item(int tidx, int cat, int idx, int *owned, int *done) {      /* ItemInfo vào ib; trả id */
    int id = (s8)gv[6499 + 42 * tidx + idx];
    clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, cat, id);
    *owned = BagItem_GetBagItemTotalBunchCount(BAGP(), cat, id) > 0;
    *done = HERO_IsCollectionComplete(HEROP(), tidx, idx) & 255;
    return id;
}
static void paint_collgrid(void *self) {
    mui_m *m = &L; int f = S8(self, 0xf), cat = S8(self, 0x10), tab = S8(self, 0x11), cur = S8(self, 0x12), ab = S8(self, 0x13);
    int tidx, icat, count, ntab, in[4]; u8 t[24]; int n, foc = f >= 3;
    coll_idx(self, &tidx, &icat, &count, &ntab);
    frame_money(self, CT(269 + (cat < 0 || cat > 2 ? 0 : cat)), 0);
    int sh = m->lp + 2 * m->s + 2;
    mui_text(G.x0 + 2, G.y0 + 1, CT(284), -1, m->px, C_DIM, 0);
    n = mui_itoa(HERO_GetCollectionCompleteCount(HEROP(), tidx), t); t[n++] = '/'; n += mui_itoa(count, t + n);
    mui_text(G.x1 - 2, G.y0 + 1, t, n, m->px, C_GOLD, TF_RIGHT);
    mui_fill(G.x0, G.y0 + sh - 1, G.x1, G.y0 + sh, C_ORANGE_DK);
    G.ty = G.y0 + sh + m->gap;
    if (ntab) {
        const u8 *lab[5];
        for (int i = 0; i < ntab; i++) lab[i] = CT((cat == 0 ? 178 : 277) + i);
        subtabs(self, ntab, tab, lab);
    }
    grid_geo(1);
    int rows = (count + 3) / 4, top = ab - cur;
    if (top < 0) top = 0;
    mui_scrollbar(G.sbx, G.gy, G.gy + 4 * m->pitch - m->cg, top / 4, rows < 4 ? 4 : rows, 4);
    for (int k = 0; k < 16; k++) {
        int idx = top + k, x = G.gx + (k % 4) * m->pitch, y = G.gy + (k / 4) * m->pitch, sel = foc && k == cur;
        if (idx >= count) { mui_cell_i(x, y, -1, 0, -1, 0, 0, H_CELL + k); continue; }
        int owned, done; coll_item(tidx, icat, idx, &owned, &done);
        if (done || owned) {
            mui_cell_i(x, y, I16(ib, 22), 0, -1, 0, sel, H_CELL + k);
            if (!done) mui_fill_a(x + 1, y + 1, x + m->cell - 1, y + m->cell - 1, C_CELL, 150);
        } else {
            mui_cell_i(x, y, -1, 0, -1, 0, sel, H_CELL + k);
            int w = mui_sw(m->px, (const u8 *)"?", 1);
            mui_text(x + (m->cell - w) / 2, y + (m->cell - m->px) / 2, (const u8 *)"?", 1, m->px, sel ? C_WHITE : C_DIM, 0);
        }
    }
    int ok = 0; u8 *sel = NULL;
    if (foc && ab >= 0 && ab < count) {
        int owned, done; coll_item(tidx, icat, ab, &owned, &done);
        if (owned || done) sel = ib;
        ok = owned && !done;
    }
    price_button(self, CT(272), 0, ok, 0);
    mui_r box = {G.dx, G.gy, G.x1, G.by - m->gap};
    npc_item_info(&box, sel, 0, 0, NULL);
    (void)in;
}

/* ---------------- 3/4: Tôi luyện thường/cao cấp; 5: Kết hợp Orb; 6: Chế biến thường (L2/T17c5c) ----------------
 * Tôi luyện/Orb: [0xf] 0 = hàng chính ([0x10] 0..2 / 0..3), 1.. = picker. Vật phẩm chọn = s8 gv[6918] -> mục túi = *(u32*)(bag+4*(idx+122)) (+0x14 = ItemInfo*, +0x12 = s16 chỉ số).
 * Tôi luyện: [0x18] s8 = số đá dùng; đá = (11,10) thường / (18,8) cao cấp; tỉ lệ = min(1000, s16 ItemTable[lvl*10 + 212 | 312] + 2*[0x18]) nghìn phân, lvl = s8 item[0x165] (+1 sau khi thành công);
 *   chi phí = RefineItem::GetItemRefineMoney(mục+0x12). Orb: [0x19] id Orb (cat 12), [0x1a] id vật hiến tế (cat 18, id 9 nhân đôi tỉ lệ); tỉ lệ = min(1000, s16 ItemTable[180 + 6*cnt] (x2 nếu hiến tế 9)),
 *   cnt = số ô Orb trong item[0x169..] bằng loại ((id-1)%13)+1, số ô = s8 item[0x168].
 * Chế biến: công thức = HERO::GetMixSmithTableInfoPtr(s16 [0xc]*8 + [0x10]) (lưới logic 2 hàng x 4 cột = 8 công thức, [0x10] 0..7); kết quả cat s8 [+0x126] (<0 = ô trống) id [+0x127]; n = [+0x11c] nguyên liệu,
 *   nguyên liệu i: cat [+0x11d+i], id [+0x120+i], cần [+0x123+i]. */
extern H void *HERO_GetRefineItemPtr(void *hero);
extern H int RefineItem_GetItemRefineMoney(void *ri, int id);
extern H u8 *HERO_GetMixSmithTableInfoPtr(void *hero, int i);
enum { H_ROW = 0xB0, H_CLEAR = 0x94 };
static u8 ib2[400];

static u8 *sel_entry(void) {                                             /* mục túi của vật phẩm đã chọn (gv[6918]) hoặc NULL */
    int idx = (s8)gv[6918]; if (idx < 0) return NULL;
    return *(u8 **)(BAGP() + 4 * (idx + 122));
}
static void panel(int x0, int y0, int x1, int y1) { int in[4]; mui_frame(x0, y0, x1, y1, L.rad, 0, 1, RGB(14, 22, 34), 230, in); }
/* hàng ô (mock w_slot): ô + nhãn nhỏ + tên; hid = vùng chạm cả hàng; trả y dưới hàng */
static int slot_row(int y, const u8 *label, const u8 *name, int icon, int rar, int sel, int dim, int hid) {
    mui_m *m = &L; int rh = m->cell + 6, x0 = G.x0, x1 = G.x1;
    panel(x0, y, x1, y + rh);
    mui_cell_i(x0 + 3, y + 3, icon, 0, rar, 0, sel, 0);
    int tx = x0 + 3 + m->cell + m->pad, ty = y + (rh - (m->spx + m->px + 2)) / 2;
    mui_clip(tx, y, x1 - m->pad, y + rh);
    mui_text(tx, ty, label, -1, m->spx, C_DIM, 0);
    mui_text(tx, ty + m->spx + 2, name, -1, m->px, dim ? C_DIM : C_WHITE, rar >= 0 && !dim ? TF_SHADOW : 0);
    mui_clip_reset();
    mui_hit(hid, x0, y, x1, y + rh, HK_CB, 0, 0);
    return y + rh;
}
static void coin_numf(int xr, int y, int v, u32 col, int f) {
    u8 t[16]; int n = mui_money(v, t), w = mui_sw(f, t, n);                  /* mock w_statrows: xu luôn x1 */
    const u8 *coin = CUI_SPR(16); int cw = *(const u16 *)(coin + 2), ch = *(const u16 *)(coin + 4);
    mui_text(xr, y, t, n, f, col, TF_RIGHT);
    mui_gbm(coin, xr - w - 3 - cw, y + (f - ch) / 2, 1, -1);
}
/* bảng nhãn-giá trị (mock w_statrows): rows[i] = nhãn, giá trị chữ (hoặc coin >=0), màu; trả y dưới bảng */
typedef struct { const u8 *lab; const u8 *val; int nval, coin; u32 col; } srow_t;
static int statrows(int y0, const srow_t *rows, int n) {
    mui_m *m = &L; int f = m->hd ? m->px : m->spx, step = f + (m->hd ? 6 : 5), p = m->pad / 2 + 1, x0 = G.x0, x1 = G.x1, h = 2 * p + 2 + n * step;
    panel(x0, y0, x1, y0 + h);
    for (int i = 0; i < n; i++) {
        int y = y0 + p + 1 + i * step;
        mui_clip(x0 + p + 2, y, x0 + (x1 - x0) / 2 + p, y + step);
        mui_text(x0 + p + 2, y, rows[i].lab, -1, f, C_DIM, 0);
        mui_clip_reset();
        if (rows[i].coin >= 0) coin_numf(x1 - p - 2, y, rows[i].coin, rows[i].col, f);
        else mui_text(x1 - p - 2, y, rows[i].val, rows[i].nval, f, rows[i].col, TF_RIGHT);
    }
    return y0 + h;
}
static int pct_str(int v, u8 *t) { int n = mui_itoa(v / 10, t); t[n++] = '.'; t[n++] = '0' + v % 10; t[n++] = '%'; return n; }

static void paint_refine(void *self) {
    mui_m *m = &L; int st = S8(self, 0xe), hi = st == 4, row = S8(self, 0x10), qty = S8(self, 0x18), money = BagItem_GetMoney(BAGP());
    u8 *e = sel_entry(), *it = e ? *(u8 **)(e + 0x14) : NULL;
    if (!it) e = NULL;
    frame_money(self, CT(86 + hi), 0);
    int cat = hi ? 18 : 11, sid = hi ? 8 : 10, have = BagItem_GetBagItemTotalBunchCount(BAGP(), cat, sid), y = G.y0;
    y = slot_row(y, CT(101), it ? it + 24 : CT(155), it ? I16(it, 22) : -1, it ? npc_rarity(it + 24) : -1, row == 0, !it, H_ROW) + m->gap;
    u8 nm[64]; int nn;
    clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, cat, sid);
    if (qty > 0) {
        const u8 *sn = ib + 24; nn = 0;
        while (sn[nn] && nn < 40) { nm[nn] = sn[nn]; nn++; }
        nm[nn++] = ' '; nm[nn++] = 'x'; nn += mui_itoa(qty, nm + nn); nm[nn++] = '/'; nn += mui_itoa(have, nm + nn); nm[nn] = 0;
        y = slot_row(y, CT(102 + hi), nm, I16(ib, 22), -1, row == 1, 0, H_ROW + 1) + m->gap;
    } else y = slot_row(y, CT(102 + hi), have > 0 ? CT(110) : CT(111), -1, -1, row == 1, 1, H_ROW + 1) + m->gap;
    u8 rt[24], lv[32]; int rn, ln, lvl = it ? (s8)it[0x165] : 0, rate = 0;
    if (it && qty > 0) { rate = I16(ITEMTABLE(), lvl * 10 + (hi ? 312 : 212)) + 2 * qty; if (rate > 1000) rate = 1000; }
    rn = pct_str(rate, rt);
    if (it) {                                                     /* gốc: "%d/10>%d/10" (cùng quy ước "Độ tôi luyện n/10" trong hộp thông tin vật phẩm) */
        ln = mui_itoa(lvl, lv); lv[ln++] = '/'; lv[ln++] = '1'; lv[ln++] = '0'; lv[ln++] = ' '; lv[ln++] = '>'; lv[ln++] = ' ';
        ln += mui_itoa(lvl + 1, lv + ln); lv[ln++] = '/'; lv[ln++] = '1'; lv[ln++] = '0';
    } else { lv[0] = '-'; ln = 1; }
    srow_t rows[4] = {{CT(104), rt, rn, -1, rate > 0 ? C_GREEN : C_DIM}, {CT(105), lv, ln, -1, it ? C_WHITE : C_DIM}, {CT(19), 0, 0, money, C_GOLD},
                      {CT(106), 0, 0, e ? RefineItem_GetItemRefineMoney(HERO_GetRefineItemPtr(HEROP()), I16(e, 0x12)) : 0, C_GOLD}};
    statrows(y, rows, 4);
    mui_r b = {G.x0, G.by, G.x1, G.y1};
    mui_button(&b, CT(107), -1, 1, H_BUY);
}

static void paint_orb(void *self) {
    mui_m *m = &L; int row = S8(self, 0x10), orb = S8(self, 0x19), sac = S8(self, 0x1a);
    u8 *e = sel_entry(), *it = e ? *(u8 **)(e + 0x14) : NULL;
    frame_money(self, CT(88), 0);
    int y = G.y0;
    y = slot_row(y, CT(122), it ? it + 24 : CT(155), it ? I16(it, 22) : -1, it ? npc_rarity(it + 24) : -1, row == 0, !it, H_ROW) + m->gap;
    if (orb >= 0) { clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, 12, orb); y = slot_row(y, CT(123), ib + 24, I16(ib, 22), -1, row == 1, 0, H_ROW + 1) + m->gap; }
    else y = slot_row(y, CT(123), CT(155), -1, -1, row == 1, 1, H_ROW + 1) + m->gap;
    if (sac >= 0) { clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, 18, sac); y = slot_row(y, CT(124), ib + 24, I16(ib, 22), -1, row == 2, 0, H_ROW + 2) + m->gap; }
    else y = slot_row(y, CT(124), CT(127), -1, -1, row == 2, 1, H_ROW + 2) + m->gap;
    int rate = 0;
    if (it && orb >= 0) {
        int n = (s8)it[0x168], cnt = 0, ty = (orb - 1) % 13 + 1;
        for (int k = 0; k < n; k++) if ((s8)it[0x169 + k] == ty) cnt++;
        rate = I16(ITEMTABLE(), 180 + 6 * cnt);
        if (sac == 9) rate *= 2;
        if (rate > 1000) rate = 1000;
    }
    u8 rt[24]; int rn = pct_str(rate, rt);
    srow_t rows[1] = {{CT(104), rt, rn, -1, rate > 0 ? C_GREEN : C_DIM}};
    statrows(y, rows, 1);
    int hw = (G.x1 - G.x0 - m->gap) / 2, n1; const u8 *l1 = mui_hint(CT(130), &n1);
    mui_r b0 = {G.x0, G.by, G.x0 + hw, G.y1}, b1 = {G.x1 - hw, G.by, G.x1, G.y1};
    mui_button(&b0, l1, n1, sac >= 0 ? 0 : 3, H_CLEAR);
    mui_button(&b1, CT(88), -1, 1, H_BUY);
}

static u8 *mix_rec(void *self, int cell) {                              /* bản ghi công thức của ô (0..7) hoặc NULL (ô ngoài lưới logic) */
    if (cell < 0 || cell > 7) return NULL;
    return HERO_GetMixSmithTableInfoPtr(HEROP(), (s8)(I16(self, 0xc) * 8 + cell));
}
static void paint_mix(void *self) {
    mui_m *m = &L; int cur = S8(self, 0x10);
    frame_money(self, CT(89), 0);
    int sh = m->lp + 2 * m->s + 2;
    mui_text(G.x0 + 2, G.y0 + 1, CT(135), -1, m->px, C_GOLD, 0);
    mui_fill(G.x0, G.y0 + sh - 1, G.x1, G.y0 + sh, C_ORANGE_DK);
    G.ty = G.y0 + sh + m->gap; grid_geo(1);
    mui_scrollbar(G.sbx, G.gy, G.gy + 4 * m->pitch - m->cg, 0, 4, 4);
    for (int k = 0; k < 16; k++) {
        u8 *r = mix_rec(self, k); int icon = -1, rar = -1;
        if (r && (s8)r[0x126] >= 0) { clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, (s8)r[0x126], (s8)r[0x127]); icon = I16(ib, 22); rar = npc_rarity(ib + 24); }
        mui_cell_i(G.gx + (k % 4) * m->pitch, G.gy + (k / 4) * m->pitch, icon, 0, rar, 0, k == cur, H_CELL + k);
    }
    u8 *r = mix_rec(self, cur); int valid = r && (s8)r[0x126] >= 0, n = valid ? r[0x11c] : 0, by = G.by;
    if (n > 3) n = 3;
    mui_r box = {G.dx, G.gy, G.x1, by - m->gap};
    if (valid) {
        int f = m->hd ? m->px : m->spx, step = f + (m->hd ? 6 : 5), p = m->pad / 2 + 1, h = 2 * p + 2 + n * step, y0 = box.y1 - h;
        clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib, (s8)r[0x126], (s8)r[0x127]);
        if (n > 0) {
            mui_r ib_box = {box.x0, box.y0, box.x1, y0 - m->gap};
            npc_item_info(&ib_box, ib, 0, 0, NULL);
            panel(box.x0, y0, box.x1, box.y1);
            for (int i = 0; i < n; i++) {
                int cat = (s8)r[0x11d + i], id = (s8)r[0x120 + i], need = r[0x123 + i], have = BagItem_GetBagItemTotalBunchCount(BAGP(), cat, id), y = y0 + p + 1 + i * step;
                clr(); ItemTable_GetItemTableInfo(ITEMTABLE(), ib2, cat, id);
                u8 t[16]; int tn = mui_itoa(have, t); t[tn++] = '/'; tn += mui_itoa(need, t + tn);
                int vw = mui_sw(f, t, tn);
                mui_clip(box.x0 + p + 2, y, box.x1 - p - vw - 6, y + step);
                mui_text(box.x0 + p + 2, y, ib2 + 24, -1, f, C_DIM, 0);
                mui_clip_reset();
                mui_text(box.x1 - p - 2, y, t, tn, f, have >= need ? C_GREEN : RGB(255, 96, 80), TF_RIGHT);
            }
        } else npc_item_info(&box, ib, 0, 0, NULL);
    } else npc_item_info(&box, NULL, 0, 0, NULL);
    mui_r b = {G.dx, by, G.x1, G.y1};
    mui_button(&b, CT(150), -1, valid ? 1 : 3, H_BUY);
}

/* ---------------- Paint / Touch ---------------- */
/* picker: trả offset con trỏ (0 = không phải picker) */
static int pick_off(void *self, const u8 **title) {
    int st = S8(self, 0xe), f = S8(self, 0xf);
    if ((st == 3 || st == 4) && f == 1) { *title = CT(86 + st - 3); return 0x11; }
    if (st == 5 && f >= 1 && f <= 3) { *title = CT(88); return 0x11; }
    if (st == 7 && (f == 6 || f == 7 || f == 8)) { int c = S8(self, 0x10); *title = CT(269 + (c < 0 || c > 2 ? 0 : c)); return 0x14; }
    return 0;
}
void npc_paint(void *self) {
    const u8 *tt; int po = pick_off(self, &tt);
    if (po) { frame_money(self, tt, 0); npc_pick_paint(self, G.x0, G.y0, G.x1, G.y1, po, CT(155)); return; }
    switch (S8(self, 0xe)) {
    case 0: paint_skillbuy(self); break;
    case 1: paint_itembuy(self); break;
    case 2: paint_sale(self); break;
    case 3: case 4: paint_refine(self); break;
    case 5: paint_orb(self); break;
    case 6: paint_mix(self); break;
    case 7: if (S8(self, 0xf) == 0) paint_collmenu(self); else paint_collgrid(self); break;
    }
}
int npc_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_NPC, x, y); }

static void cell_tap(void *self, int k) {
    int st = S8(self, 0xe);
    if (st == 6) { if (k > 7) return; if (S8(self, 0x10) == k) mui_key(self, MUI_CLS_NPC, -5); else S8(self, 0x10) = k; return; }
    if (st == 7 && S8(self, 0xf) != 0) {
        int f = S8(self, 0xf), cat = S8(self, 0x10), cur = S8(self, 0x12), top = S8(self, 0x13) - cur, tidx, icat, count, ntab;
        coll_idx(self, &tidx, &icat, &count, &ntab);
        if (top + k >= count) return;
        if (f >= 3 && cur == k) { mui_key(self, MUI_CLS_NPC, -5); return; }
        if (f < 3) S8(self, 0xf) = 3 + f;
        S8(self, 0x12) = k; S8(self, 0x13) = top + k; (void)cat; return;
    }
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
    { const u8 *tt; int po = pick_off(self, &tt); if (po) { npc_pick_cb(self, id, po); return 0; } }
    if (id >= H_CELL && id < H_CELL + 16) cell_tap(self, id - H_CELL);
    else if (id >= H_TAB && id < H_TAB + 3 && st == 1) { S8(self, 0xf) = 0; S8(self, 0x10) = id - H_TAB; }
    else if (id >= H_TAB && id < H_TAB + 5 && st == 7 && S8(self, 0xf) != 0 && S8(self, 0x10) < 2) { S8(self, 0xf) = 1 + S8(self, 0x10); S8(self, 0x11) = id - H_TAB; }
    else if (id >= H_PAGE && id < H_PAGE + 4 && st == 2) { S8(self, 0xf) = 0; S8(self, 0x10) = id - H_PAGE; }
    else if (id >= H_ROW && id < H_ROW + 3 && (st == 3 || st == 4 || st == 5)) { S8(self, 0x10) = id - H_ROW; mui_key(self, MUI_CLS_NPC, -5); }
    else if (id == H_CLEAR && st == 5) mui_key(self, MUI_CLS_NPC, 48);
    else if (id == H_BUY && (st == 3 || st == 4)) { S8(self, 0x10) = 2; mui_key(self, MUI_CLS_NPC, -5); }
    else if (id == H_BUY && st == 5) { S8(self, 0x10) = 3; mui_key(self, MUI_CLS_NPC, -5); }
    else if (id == H_BUY && st == 6) mui_key(self, MUI_CLS_NPC, -5);
    else if (id == H_BUY) {
        if (st == 1 && S8(self, 0xf) == 0) S8(self, 0xf) = 1;
        if (st == 2 && S8(self, 0xf) == 0) S8(self, 0xf) = 1;
        if (st == 7 && S8(self, 0xf) >= 1 && S8(self, 0xf) <= 2) S8(self, 0xf) += 3;
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

/* ---------------- popup NPC loại 5 = nhập số lượng (StatePopup, cha = NPC): *[+0x14] = số lượng hiện tại 1..max, max = int [+0x20]; gốc: PHẢI/TRÁI +-1 (vòng), LÊN/XUỐNG +-10, OK xác nhận
 * (ghi NPC[0x18] / dùng ở Process), CLR huỷ. Chạm = phím gốc tương ứng (không có mockup riêng: bố cục theo ngôn ngữ popup chung; số đo ghi ở LAYOUT.md "npc_so_luong"). ---------------- */
enum { Q_OK = 0xA0, Q_NO = 0xA1, Q_M10 = 0xA2, Q_M1 = 0xA3, Q_P1 = 0xA4, Q_P10 = 0xA5 };
void qty_paint(void *self) {
    mui_m *m = &L; u32 par = *(u32 *)((u8 *)self + 8);
    if (par) { void (**vt)(void *) = *(void (***)(void *))par; vt[0x40 / 4]((void *)par); }
    if (!mui_begin()) return;
    s8 *res = *(s8 **)((u8 *)self + 0x14);
    if (!res) return;
    int v = *res, mx = I32(self, 0x20), n1, st = par ? S8((void *)par, 0xe) : -1;
    const u8 *l1 = mui_hint((u8 *)self + 0x43c, &n1), *cap = st == 1 ? CT(84) : st == 2 ? CT(85) : st == 3 ? CT(102) : st == 4 ? CT(103) : NULL;
    int sq = m->btnH, pw = m->hd ? 460 : (66 * m->W + 50) / 100, in[4], tx = m->pad + 2 * m->s + 4;
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    int ph = 2 * m->pad + 4 * m->s + (cap ? m->lp + m->gap : 0) + sq + m->pad + sq;
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int yy = in[1] + m->pad + 2 * m->s, xl = in[0] + tx - m->pad, xr = in[2] - tx + m->pad;
    if (cap) { mui_text((in[0] + in[2]) / 2, yy, cap, -1, m->px, C_DIM, TF_CENTER); yy += m->lp + m->gap; }
    int bw = 2 * sq, g = m->gap, x = xl;
    mui_r r;
    r = (mui_r){x, yy, x + bw, yy + sq}; mui_button(&r, (const u8 *)"-10", 3, 0, Q_M10); x += bw + g;
    r = (mui_r){x, yy, x + sq, yy + sq}; mui_arrow(&r, 0, Q_M1); x += sq + g;
    int fx1 = xr - bw - g - sq - g;
    { int fin[4]; mui_frame(x, yy, fx1, yy + sq, m->rad, 0, 1, RGB(10, 16, 26), 235, fin);
      u8 t[24]; int n = mui_itoa(v, t); t[n++] = '/'; n += mui_itoa(mx, t + n);
      mui_text((x + fx1) / 2, yy + (sq - m->px) / 2, t, n, m->px, C_GOLD, TF_CENTER | TF_SHADOW); }
    x = fx1 + g;
    r = (mui_r){x, yy, x + sq, yy + sq}; mui_arrow(&r, 1, Q_P1); x += sq + g;
    r = (mui_r){x, yy, xr, yy + sq}; mui_button(&r, (const u8 *)"+10", 3, 0, Q_P10);
    yy += sq + m->pad;
    int hw = (xr - xl - g) / 2;
    r = (mui_r){xl, yy, xl + hw, yy + sq}; mui_button(&r, l1, n1, 1, Q_OK);
    r = (mui_r){xr - hw, yy, xr, yy + sq}; mui_button(&r, CT(57), -1, 0, Q_NO);
}
int qty_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_POPUP, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    switch (id) {
    case Q_OK: mui_key(self, MUI_CLS_POPUP, -5); break;
    case Q_NO: mui_key(self, MUI_CLS_POPUP, -16); break;
    case Q_M10: mui_key(self, MUI_CLS_POPUP, -2); break;
    case Q_M1: mui_key(self, MUI_CLS_POPUP, -3); break;
    case Q_P1: mui_key(self, MUI_CLS_POPUP, -4); break;
    case Q_P10: mui_key(self, MUI_CLS_POPUP, -1); break;
    }
    return 0;
}
