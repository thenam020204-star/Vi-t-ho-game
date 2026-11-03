/* T17c2: Túi đồ (InGameMenu state 2) + Trang bị (state 3) + popup chi tiết vật phẩm (StatePopup loại 10).
 * Số đo theo ui_new/menu/LAYOUT.md §7 + §11 và mock_menu.py / mock_b2a.py (screen_bag, screen_equip, screen_picker).
 * Kiến trúc A (BAO-CAO §13.4): Paint + Touch mới; KeyPressed/Process/SetPopupState GỐC (chạm = ghi trường con trỏ gốc rồi KeyPressed gốc).
 * Trường StateInGameMenu (đọc từ KeyBagMenu/KeyEquipMenu/DrawBagMenu/DrawEquipMenu gốc, BAO-CAO §16): +0xd SubState, +0xe trang túi / ô trang bị,
 * +0xf ô trong trang / con trỏ danh sách lọc của Trang bị, +0x10 con trỏ ô nhanh (Túi sub 2) / con trỏ danh sách lọc (Túi sub>=3).
 * Túi: sub 0 chọn trang, 1 chọn ô 4x4, 2 chọn ô nhanh, >=3 danh sách lọc (5/trang). Trang bị: sub 0 lưới 4x2, 1 danh sách lọc (OpenEquipFilter).
 * Văn bản vật phẩm: KHÔNG dựng lại luật chỉ số; gọi CommonUi::DrawItemDiscription gốc ở chế độ "bắt" (mui_cap_on): móc call-site BFont::DrawTextLine
 * (hooks.def) sao chép bộ đệm văn bản (mô tả + dòng chỉ số ngăn bằng ';') thay vì vẽ, rồi ta tự dàn trang. */
#include "mui.h"

#define S8(self, o) (*((s8 *)(self) + (o)))
#define I8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define I16(p, o) (*(s16 *)((u8 *)(p) + (o)))
#define BAGP() ((u8 *)HERO_GetBagItemPtr(HEROP()))
#define CUI_SPR(k) (COMMONUI() + 5964 + 24 * (k))

/* id vùng chạm */
#define H_SORT 0x70
#define H_PAGE 0x80       /* +i (0..7) */
#define H_CELL 0xA0       /* +k (0..15 ô túi, 0..7 ô trang bị) */
#define H_BELT 0xB0       /* +i (0..4) */
#define H_SWAP 0xB5
#define H_CAND 0xC0       /* +i (0..4) */
#define H_PREV 0xC5
#define H_NEXT 0xC6
#define H_HSCR 0xC7
#define H_OK 0xC8
#define H_CANCEL 0xC9
#define H_DETAIL 0xE0

/* ================= bắt văn bản DrawItemDiscription ================= */
int mui_cap_on;
static u8 cap_buf[768];
static int cap_len;
void mui_cap_line(void *f, int a1, int a2, int a3, int a4, int a5, const u8 *buf, int a7) {
    if (mui_cap_on) {
        if (!cap_len) { int n = 0; while (buf[n] && n < 767) { cap_buf[n] = buf[n]; n++; } cap_buf[n] = 0; cap_len = n ? n : -1; }
        return;
    }
    BFont_DrawTextLine(f, a1, a2, a3, a4, a5, buf, a7);
}
static int cap_text(u8 *item) {
    u32 g = _lcdG;
    cap_len = 0; cap_buf[0] = 0; mui_cap_on = 1;
    Graphic_SetClip(g, 0, 0, 0, 0);
    CommonUi_DrawItemDiscription(COMMONUI(), Graphic_GetFB(g), -3000, -3000, item, -1, 0);   /* ngoài màn: mọi nét vẽ (thanh cuộn...) bị clip */
    Graphic_ResetClip(g);
    mui_cap_on = 0;
    return cap_len > 0 ? cap_len : 0;
}

typedef struct { u8 kind; u16 a, la, b, lb; } ent;      /* kind 0 đoạn chữ (a,la) ngắt dòng; 1 hàng nhãn(a,la)-giá trị(b,lb); 2 chữ xanh lá CT[a] */
static ent EN[32];
static int nen, meta_n;
static const u8 *meta_p;

/* nhãn đã biết của DrawItemDiscription (CommonUi, các CT được gọi trong hàm gốc) -> tách "nhãn giá-trị" */
static const u16 LABS[] = {215, 216, 217, 218, 219, 220, 221, 222, 225, 228, 242, 243, 284, 28, 36, 37, 105, 26, 27, 25, 33, 34, 35};
static int find_label(const u8 *s, int len) {
    int best = 0;
    for (unsigned i = 0; i < sizeof(LABS) / sizeof(LABS[0]); i++) {
        const u8 *lab = CT(LABS[i]);
        if (!lab) continue;
        int ll = mui_cslen(lab), ok = ll > 0 && ll <= len;
        for (int j = 0; ok && j < ll; j++) if (s[j] != lab[j]) ok = 0;
        if (ok && (ll == len || s[ll] == ' ') && ll > best) best = ll;
    }
    return best;
}
static void add_ent(int kind, int a, int la, int b, int lb) { if (nen < 32) { ent *e = &EN[nen++]; e->kind = kind; e->a = a; e->la = la; e->b = b; e->lb = lb; } }

/* dựng danh sách dòng của hộp thông tin; equip = trang bị (dòng đầu = loại -> meta, còn lại = hàng chỉ số) */
static void build_info(u8 *item, int flag) {
    int equip = I8(item, 20) >= 0 && I8(item, 20) <= 10, n = cap_text(item), p = 0;
    const u8 *desc = item + 52; int dl = mui_cslen(desc);
    nen = 0; meta_p = NULL; meta_n = 0;
    if (n && dl > 0 && dl <= n) { int ok = 1; for (int j = 0; j < dl; j++) if (cap_buf[j] != desc[j]) { ok = 0; break; } if (ok) p = dl; }
    if (p) {
        int blank = 1;
        for (int j = 0; j < dl; j++) if (desc[j] != ' ') { blank = 0; break; }
        if (equip && !blank) { meta_p = cap_buf; meta_n = p; }
        else if (equip) {                                   /* mô tả trống: dòng loại là đoạn đầu sau mô tả (vd "Giáp") */
            int e = p; while (e < n && cap_buf[e] != ';') e++;
            meta_p = cap_buf + p; meta_n = e - p; p = e;
        } else add_ent(0, 0, p, 0, 0);
        if (cap_buf[p] == ';') p++;
    }
    while (p < n) {
        int e = p; while (e < n && cap_buf[e] != ';') e++;
        if (e > p) {
            int ll = find_label(cap_buf + p, e - p);
            if (ll && ll < e - p) add_ent(1, p, ll, p + ll + 1, e - p - ll - 1);
            else add_ent(0, p, e - p, 0, 0);
        }
        p = e + 1;
    }
    if (flag && equip) add_ent(2, 120, 0, 0, 0);
}

static void info_frame(const mui_r *b) { mui_frame(b->x0, b->y0, b->x1, b->y1, L.rad, 0, 1, RGB(10, 16, 26), 235, NULL); }

/* hộp thông tin vật phẩm: tên (màu mã) + [loại] + vạch + hàng chỉ số + mô tả (cuộn dọc, kéo = HK_DRAG scr, đơn vị px) */
static void draw_item_info(const mui_r *box, u8 *item, int flag, int scr, const u8 *meta_other) {
    mui_m *m = &L;
    static u8 *last_it[MUI_NSCROLL]; static u8 last_cid[MUI_NSCROLL];
    if (!item) { info_frame(box); return; }
    build_info(item, flag);
    if (!meta_p && meta_other) { meta_p = meta_other; meta_n = mui_cslen(meta_other); }
    int y = mui_descbox2(box, item + 24, -1, -1, meta_p, meta_n);
    int p = m->pad / 2 + 1, ix0 = box->x0 + 3, ix1 = box->x1 - 3, tw = (ix1 - ix0) - 2 * p - m->sbw - 2;
    int view = box->y1 - 3 - y, total = 0;
    if (view < 1) view = 1;
    u16 st[24], ln[24];
    for (int i = 0; i < nen; i++) {
        if (EN[i].kind == 0) total += m->lp * mui_wrap(m->px, cap_buf + EN[i].a, EN[i].la, tw, st, ln, 24);
        else total += m->slp;
    }
    u8 cid = (u8)(I8(item, 20) * 31 + I8(item, 21));
    if (last_it[scr] != item || last_cid[scr] != cid) { last_it[scr] = item; last_cid[scr] = cid; mui_sc[scr].pos = 0; }
    int pos = mui_sc[scr].pos, mx = total > view ? total - view : 0;
    if (pos > mx) pos = mx;
    if (pos < 0) pos = 0;
    mui_sc[scr].pos = pos; mui_sc[scr].total = total; mui_sc[scr].view = view;
    mui_clip(ix0, y, ix1, box->y1 - 3);
    int yy = y - pos;
    for (int i = 0; i < nen; i++) {
        ent *e = &EN[i];
        if (e->kind == 0) {
            int nl = mui_wrap(m->px, cap_buf + e->a, e->la, tw, st, ln, 24);
            for (int k = 0; k < nl; k++, yy += m->lp)
                if (yy + m->lp > y && yy < y + view) mui_text_line(ix0 + p, yy, cap_buf + e->a, st[k], ln[k], m->px, C_WHITE, 0);
        } else {
            if (yy + m->slp > y && yy < y + view) {
                if (e->kind == 2) mui_text(ix0 + p, yy, CT(e->a), -1, m->spx, C_GREEN, 0);
                else {
                    int vw = mui_sw(m->spx, cap_buf + e->b, e->lb);
                    mui_clip(ix0 + p, y, ix1 - p - vw - 6, box->y1 - 3);
                    mui_text(ix0 + p, yy, cap_buf + e->a, e->la, m->spx, C_DIM, 0);
                    mui_clip(ix0, y, ix1, box->y1 - 3);
                    mui_text(ix1 - p, yy, cap_buf + e->b, e->lb, m->spx, C_WHITE, TF_RIGHT);
                }
            }
            yy += m->slp;
        }
    }
    mui_clip_reset();
    if (total > view) mui_scrollbar(ix1 - m->sbw - 1, y, box->y1 - 3, pos, total, view);
    mui_hit(H_DETAIL, ix0, y, ix1, box->y1 - 3, HK_DRAG, scr, 1);
}

/* ================= dữ liệu ô ================= */
static int rarity(const u8 *name) {                       /* màu mã phẩm chất đầu tên (BFont gốc): chỉ mã màu (không phải '{' '|' '$' '[') */
    u8 c = name[0];
    if (c == '&' || c == '}' || c == '`' || c == '^' || c == '<' || c == '\'' || c == '"') return mui_code_color(c);
    return -1;
}
static int item_flag(u8 *bag, u8 *it) {                    /* cờ 'e' như DrawBagMenu: đang mặc, hoặc (loại 11) đã gán ô nhanh */
    int cat = I8(it, 20);
    (void)bag;
    if (cat >= 0 && cat <= 10) return EquipItemInfo_IsEquipIng(it);
    if (cat == 11) {
        u8 *h = HEROP();
        for (int bank = 0; bank < 2; bank++)
            for (int slot = 0; slot < 5; slot++)
                if ((HERO_GetHotKeyBeltType(h, slot, bank) & 255) == 2 && (HERO_GetHotKeyBeltSkillIdx(h, slot, bank) & 255) == (u8)I8(it, 21)) return 1;
    }
    return 0;
}
static void belt_slot(int slot, int *icon, int *count) {
    u8 *h = HEROP(), buf[400];
    int bank = (s8)HERO_GetHotKeyToggle(h), type = HERO_GetHotKeyBeltType(h, slot, bank) & 255, idx = (s8)HERO_GetHotKeyBeltSkillIdx(h, slot, bank);
    *icon = -1; *count = 0;
    if (type == 0 && idx >= 0) {
        int (*f)(u8 *, int) = (*(int (***)(u8 *, int))h)[0x24 / 4];
        *icon = (s8)f(h, idx);
    } else if (type == 2) {
        for (int i = 0; i < 400; i++) buf[i] = 0;
        ItemTable_GetItemTableInfo(ITEMTABLE(), buf, 11, idx);
        *icon = I16(buf, 22); *count = BagItem_GetBunch(BAGP(), 11, idx);
    }
}
static u8 *filt_item(u8 *bag, int idx) {                   /* mục danh sách lọc: BagItem[(idx+122)*4] -> +0x14 = ItemInfo* */
    u8 *e = *(u8 **)(bag + (idx + 122) * 4);
    return e ? *(u8 **)(e + 0x14) : NULL;
}
static void reset_filter_view(void) { gv[6464 + 19] = 0; gv[6464 + 32] = 0; }

/* ================= bố cục ================= */
typedef struct { int x0, y0, x1, y1, sy1, gx, gy, dx, dy, dx1, dy1; } bgeo;
static void bag_geo(const mui_r *body, bgeo *g) {
    mui_m *m = &L;
    g->x0 = body->x0 + m->pad; g->y0 = body->y0 + m->pad; g->x1 = body->x1 - m->pad; g->y1 = body->y1 - m->pad;
    g->sy1 = g->y0 + m->lp + 2 * m->s + 2;
    int top = g->sy1 + m->gap;
    g->gx = g->x0 + m->pitch + m->gap + 1; g->gy = top;
    g->dx = g->gx + m->gridW + m->gap + 1; g->dy = top; g->dx1 = g->x1; g->dy1 = g->y1;
}

static void money_strip(const bgeo *g, u8 *bag) {
    mui_m *m = &L; int s = m->s;
    mui_text(g->x0 + 2, g->y0 + 1, CT(19), -1, m->px, C_DIM, 0);
    u8 mt[20]; int mn = mui_money(BagItem_GetMoney(bag), mt), gw = mui_sw(m->px, mt, mn);
    mui_text(g->x1 - 2, g->y0 + 1, mt, mn, m->px, C_GOLD, TF_RIGHT);
    const u8 *coin = CUI_SPR(16); int cw = *(const u16 *)(coin + 2), ch = *(const u16 *)(coin + 4);
    mui_gbm(coin, g->x1 - 2 - gw - 3 - cw * s, s == 1 ? g->y0 + 1 + (m->px - ch * s) / 2 + 1 : g->y0 + (g->sy1 - g->y0 - ch * s) / 2, s, -1);
    mui_fill(g->x0, g->sy1 - 1, g->x1, g->sy1, C_ORANGE_DK);
}

static int qsin(int d) { static const u16 T[10] = {0, 44, 87, 128, 164, 196, 222, 241, 252, 256}; int q = d / 10, f = d % 10; return q >= 9 ? 256 : T[q] + (T[q + 1] - T[q]) * f / 10; }
static int isin(int d) { d %= 360; if (d < 0) d += 360; if (d <= 90) return qsin(d); if (d <= 180) return qsin(180 - d); if (d <= 270) return -qsin(d - 180); return -qsin(360 - d); }
static int icos(int d) { return isin(d + 90); }
static void fill_tri(int ax, int ay, int bx, int by, int cx, int cy, u32 rgb) {
    int x0 = ax < bx ? (ax < cx ? ax : cx) : (bx < cx ? bx : cx), x1 = ax > bx ? (ax > cx ? ax : cx) : (bx > cx ? bx : cx);
    int y0 = ay < by ? (ay < cy ? ay : cy) : (by < cy ? by : cy), y1 = ay > by ? (ay > cy ? ay : cy) : (by > cy ? by : cy);
    for (int y = y0; y <= y1; y++)
        for (int x = x0; x <= x1; x++) {
            int d1 = (x - bx) * (ay - by) - (ax - bx) * (y - by), d2 = (x - cx) * (by - cy) - (bx - cx) * (y - cy), d3 = (x - ax) * (cy - ay) - (cx - ax) * (y - ay);
            if (!((d1 < 0 || d2 < 0 || d3 < 0) && (d1 > 0 || d2 > 0 || d3 > 0))) mui_pix(x, y, rgb);
        }
}
/* nút đổi bộ ô nhanh (ui_new/07: vòng cam + 2 mũi tên tròn trắng) vẽ bằng nguyên thuỷ: 2 cung 120 độ + đầu mũi tên tam giác */
static void swap_icon(int x, int y, int c) {
    int cx = x + c / 2, cy = y + c / 2, R = c / 2, band = c / 10 < 2 ? 2 : c / 10;
    mui_disc(cx, cy, R, C_INK); mui_disc(cx, cy, R - 1, C_ORANGE_DK); mui_disc(cx, cy, R - 2, C_ORANGE);
    mui_disc(cx, cy, R - 2 - band, C_ORANGE_DK); mui_disc(cx, cy, R - 3 - band, RGB(22, 30, 42));
    int rho = (R - 3 - band) * 62 / 100, th = c / 14 < 1 ? 1 : c / 14, k = th * 2 + 2;
    for (int arc = 0; arc < 2; arc++) {
        int a0 = arc ? 330 : 150, a1 = arc ? 220 : 40;
        for (int a = a0; a >= a1; a -= 5) {
            int px_ = cx + rho * icos(a) / 256, py_ = cy - rho * isin(a) / 256;
            mui_fill(px_ - th / 2, py_ - th / 2, px_ - th / 2 + th, py_ - th / 2 + th, C_WHITE);
        }
        int ex = cx + rho * icos(a1) / 256, ey = cy - rho * isin(a1) / 256, tx = isin(a1), ty = icos(a1), rx = icos(a1), ry = -isin(a1);
        fill_tri(ex + tx * k * 3 / 2 / 256, ey + ty * k * 3 / 2 / 256, ex + rx * k / 256, ey + ry * k / 256, ex - rx * k / 256, ey - ry * k / 256, C_WHITE);
    }
}

/* ================= Túi đồ ================= */
static void page_tile(int i, int n, int x0, int y0, int x1, int y1, int sel) {
    mui_m *m = &L; int in[4], s = m->s, h = y1 - y0;
    mui_frame(x0, y0, x1, y1, m->rad, sel, sel ? m->bt : 1, (u32)-1, 0, in);
    if (sel) mui_vgrad(in[0], in[1], in[2], in[3], RGB(252, 168, 40), RGB(200, 98, 12));
    else mui_vgrad(in[0], in[1], in[2], in[3], RGB(34, 50, 74), RGB(20, 30, 46));
    const u8 *ic = CUI_SPR(1); int k = (h - 2) / 22; if (k > s) k = s; if (k < 1) k = 1;
    mui_gbm(ic, (x0 + x1) / 2 - (*(const u16 *)(ic + 2) * k) / 2, (y0 + y1) / 2 - (*(const u16 *)(ic + 4) * k) / 2 - (m->hd ? s : 0), k, -1);
    u8 t[4]; int tn = mui_itoa(i + 1, t), tw = mui_sw(m->spx, t, tn);
    mui_text(x1 - 3 - tw, y1 - 3 - m->spx, t, tn, m->spx, C_WHITE, TF_SHADOW);
    mui_hit(H_PAGE + i, x0, y0, x1, y1, HK_CB, 0, 0);
    (void)n;
}

static void paint_picker(void *self, const bgeo *g, int curoff, const u8 *hint, const u8 *l_ok, const u8 *l_no, int belt) {
    mui_m *m = &L; int c = m->cell, cg = m->cg, aw = c;
    u8 *bag = BAGP(), *item = NULL;
    int x0 = g->x0, y0 = g->y0, x1 = g->x1, y1 = g->y1, cur = S8(self, curoff), n;
    mui_text((x0 + x1) / 2, y0 + 1, hint, -1, m->px, C_GOLD, TF_CENTER | TF_SHADOW);
    int hy = y0 + m->lp + m->gap + 2, ty;
    if (belt) {
        int inw = 5 * c + 4 * cg + m->gap + c, bx = (x0 + x1 - inw) / 2;
        for (int i = 0; i < 5; i++) {
            int icon, cnt; belt_slot(i, &icon, &cnt);
            mui_cell_i(bx + i * (c + cg), hy, icon, cnt, -1, 0, i == cur, H_BELT + i);
            u8 t[2]; t[0] = '1' + i; mui_text(bx + i * (c + cg) + 2, hy + 1, t, 1, m->spx, i == cur ? C_WHITE : C_DIM, TF_SHADOW);
        }
        int sx = bx + 5 * c + 4 * cg + m->gap, bank = (s8)HERO_GetHotKeyToggle(HEROP());
        swap_icon(sx, hy, c);
        u8 t[4]; t[0] = '1' + (bank & 1); t[1] = '/'; t[2] = '2'; mui_text(sx + c / 2, hy + c + 1, t, 3, m->spx, C_WHITE, TF_CENTER | TF_SHADOW);
        mui_hit(H_SWAP, sx, hy, sx + c, hy + c + m->spx + 2, HK_KEY, 35, 0);
        ty = hy + c + m->gap + 2;
        mui_text((x0 + x1) / 2, ty, CT(54), -1, m->px, C_DIM, TF_CENTER); ty += m->lp + m->gap;
        item = S8(self, 0xe) * 16 + S8(self, 0xf) < BagItem_GetCount(bag) ? BagItem_GetBagItemPtr(bag, S8(self, 0xe), S8(self, 0xf)) : NULL;
    } else {
        int total = I16(bag, 872), rowW = aw + m->gap + 5 * c + 4 * cg + m->gap + aw, hx = x0 + (x1 - x0 - rowW) / 2, cx = hx + aw + m->gap;
        int page = cur / 5;
        if (total < 1) total = 1;
        mui_r pa = {hx, hy, hx + aw, hy + c}; mui_arrow(&pa, 0, H_PREV);
        for (int i = 0; i < 5; i++) {
            int idx = page * 5 + i; u8 *it = idx < total ? filt_item(bag, idx) : NULL;
            mui_cell_i(cx + i * (c + cg), hy, it ? I16(it, 22) : -1, 0, it ? rarity(it + 24) : -1, it ? item_flag(bag, it) : 0, idx == cur, H_CAND + i);
        }
        int ax = cx + 5 * c + 4 * cg + m->gap;
        mui_r pb = {ax, hy, ax + aw, hy + c}; mui_arrow(&pb, 1, H_NEXT);
        int sy = hy + c + m->gap, sw = 5 * c + 4 * cg;
        mui_hscroll(cx, sy, cx + sw, cur, total, 5);
        mui_hit(H_HSCR, cx, sy - 2, cx + sw, sy + m->sbw + 2, HK_CB, 0, 0);
        u8 pt[16]; n = mui_itoa(page + 1, pt); pt[n++] = '/'; n += mui_itoa((total + 4) / 5, pt + n);
        mui_text(x1 - 2, y0 + 1 + (m->lp - m->spx) / 2 + 1, pt, n, m->spx, C_DIM, TF_RIGHT);
        ty = sy + m->sbw + m->gap + 1;
        item = cur < I16(bag, 872) ? filt_item(bag, cur) : NULL;
    }
    int by = y1 - m->btnH, hw = (x1 - x0 - m->gap) / 2;
    mui_r box = {x0, ty, x1, by - m->gap};
    draw_item_info(&box, item, item ? item_flag(bag, item) : 0, 0, belt ? CT(96) : NULL);
    mui_r b1 = {x0, by, x0 + hw, by + m->btnH}, b2 = {x1 - hw, by, x1, by + m->btnH};
    mui_button(&b1, l_ok, -1, 1, 0); mui_hit(H_OK, b1.x0, b1.y0, b1.x1, b1.y1, HK_KEY, -5, 0);
    mui_button(&b2, l_no, -1, 0, 0); mui_hit(H_CANCEL, b2.x0, b2.y0, b2.x1, b2.y1, HK_KEY, -16, 0);
}

void bag_paint(void *self, const mui_r *body) {
    mui_m *m = &L; bgeo g; bag_geo(body, &g);
    u8 *bag = BAGP();
    int sub = S8(self, 0xd), page = S8(self, 0xe), cell = S8(self, 0xf), size = BagItem_GetSize(bag), count = BagItem_GetCount(bag);
    int npg = size / 16;
    if (npg < 1) npg = 1;
    if (npg > 6) npg = 6;
    if (sub >= 3) { paint_picker(self, &g, 0x10, CT(155), CT(56), CT(57), 0); return; }
    if (sub == 2) { paint_picker(self, &g, 0x10, CT(155), CT(56), CT(57), 1); return; }
    money_strip(&g, bag);
    /* cột trang: n <= 4 ô vuông cách pitch; n > 4 chia đều theo chiều cao lưới */
    int gh = 4 * m->pitch - m->cg, stride = npg <= 4 ? m->pitch : (gh + m->cg) / npg, th = npg <= 4 ? m->cell : stride - m->cg;
    for (int i = 0; i < npg; i++) page_tile(i, npg, g.x0, g.gy + i * stride, g.x0 + m->pitch, g.gy + i * stride + th, i == page);
    for (int k = 0; k < 16; k++) {
        int x = g.gx + (k % 4) * m->pitch, y = g.gy + (k / 4) * m->pitch, idx = page * 16 + k;
        u8 *it = idx < count ? BagItem_GetBagItemPtr(bag, page, k) : NULL;
        int cnt = it ? *(u8 *)(bag + 0x188 + idx) : 0;
        mui_cell_i(x, y, it ? I16(it, 22) : -1, cnt, it ? rarity(it + 24) : -1, it ? item_flag(bag, it) : 0, k == cell, H_CELL + k);
    }
    int by = g.dy1 - m->btnH; mui_r sb = {g.dx, by, g.dx1, g.dy1}, box = {g.dx, g.dy, g.dx1, by - m->gap};
    u8 *sel = (page * 16 + cell < count) ? BagItem_GetBagItemPtr(bag, page, cell) : NULL;
    draw_item_info(&box, sel, sel ? item_flag(bag, sel) : 0, 0, NULL);
    int n; const u8 *t = mui_hint(CT(22), &n);
    mui_button(&sb, t, n, 0, 0); mui_hit(H_SORT, sb.x0, sb.y0, sb.x1, sb.y1, HK_KEY, 48, 0);
}

static int pick_cb(void *self, int id, int curoff) {       /* chạm danh sách lọc (chung Túi sub>=3 và Trang bị sub 1) */
    u8 *bag = BAGP(); int total = I16(bag, 872), cur = S8(self, curoff);
    if (id >= H_CAND && id < H_CAND + 5) {
        int idx = (cur / 5) * 5 + (id - H_CAND);
        if (idx >= total) return 1;
        if (idx == cur) mui_key(self, MUI_CLS_INGAME, -5);
        else { S8(self, curoff) = idx; reset_filter_view(); }
    } else if (id == H_PREV) mui_key(self, MUI_CLS_INGAME, -3);
    else if (id == H_NEXT) mui_key(self, MUI_CLS_INGAME, -4);
    else if (id == H_HSCR && total > 0) {
        int c = L.cell, x0 = 0, w = 5 * c + 4 * L.cg, idx;
        for (int i = mui_nhit - 1; i >= 0; i--) if (mui_hits[i].id == H_HSCR) { x0 = mui_hits[i].x0; break; }
        idx = (mui_tx - x0) * total / w;
        if (idx < 0) idx = 0;
        if (idx >= total) idx = total - 1;
        S8(self, curoff) = idx; reset_filter_view();
    } else return 0;
    return 1;
}

int bag_cb(void *self, int id) {
    int sub = S8(self, 0xd);
    if (sub >= 3) return pick_cb(self, id, 0x10);
    if (sub == 2) {
        if (id >= H_BELT && id < H_BELT + 5) { S8(self, 0x10) = id - H_BELT; mui_key(self, MUI_CLS_INGAME, -5); return 1; }
        return 0;
    }
    if (id >= H_PAGE && id < H_PAGE + 8) { S8(self, 0xe) = id - H_PAGE; S8(self, 0xf) = 0; reset_filter_view(); return 1; }
    if (id >= H_CELL && id < H_CELL + 16) {
        int k = id - H_CELL;
        if (sub == 0) { StateInGameMenu_SetSubState(self, 1); S8(self, 0xf) = k; }
        else if (S8(self, 0xf) == k) mui_key(self, MUI_CLS_INGAME, -5);
        else S8(self, 0xf) = k;
        return 1;
    }
    return 0;
}

/* ================= Trang bị ================= */
void equip_paint(void *self, const mui_r *body) {
    mui_m *m = &L; int s = m->s, sub = S8(self, 0xd);
    int x0 = body->x0 + m->pad, y0 = body->y0 + m->pad, x1 = body->x1 - m->pad, y1 = body->y1 - m->pad;
    u8 *h = HEROP(), *bag = BAGP(); void *eq = HERO_GetEquipItemPtr(h);
    if (sub == 1) { bgeo g = {x0, y0, x1, y1, 0, 0, 0, 0, 0, 0, 0}; paint_picker(self, &g, 0xf, CT(112), mui_hint(CT(66), &(int){0}), mui_hint(CT(32), &(int){0}), 0); return; }
    static const u8 EMPTY[8] = {35, 31, 32, 33, 34, 37, 36, 36};    /* ui.mgr: icon ô trống theo ô (bảng 8 byte +31 ở DrawEquipMenu gốc 0x154ae8+0xf0) */
    int pr = m->hd ? 84 : 22 * s + 20, pw = m->hd ? pr + m->pad : pr + 2 * m->pad, sx = x0 + pw + m->gap, sy = y0;
    int gridw = 4 * m->pitch - m->cg, gridh = 2 * m->pitch - m->cg, dx = sx + gridw + m->gap + 1;
    int dh = 3 + (m->pad / 2 + 1) + 2 * m->lp + m->slp + 5 + 4 * m->slp + 2 + 3;
    if (dh < gridh) dh = gridh;
    int ty = y0 + dh + m->gap, cur = S8(self, 0xe);
    mui_frame(x0, y0, x0 + pw, y0 + dh, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL);
    mui_portrait(x0 + (pw - pr) / 2, y0 + m->pad / 2 + 1, pr);
    u8 lv[40]; int n = 0; const u8 *l13 = CT(13);
    while (l13[n] && n < 30) { lv[n] = l13[n]; n++; }
    lv[n++] = ' '; n += mui_itoa(h[0x22d], lv + n);
    mui_text(x0 + pw / 2, y0 + dh - m->lp - 2, lv, n, m->px, C_GOLD, TF_CENTER | TF_SHADOW);
    for (int i = 0; i < 8; i++) {
        int x = sx + (i % 4) * m->pitch, y = sy + (i / 4) * m->pitch;
        u8 *it = EquipItem_GetEquipItem(eq, i);
        mui_cell_i(x, y, it ? I16(it, 22) : -1, 0, it ? rarity(it + 24) : -1, it != NULL, i == cur, H_CELL + i);
        if (!it) mui_gbm(CUI_SPR(EMPTY[i]), x + (m->cell - *(const u16 *)(CUI_SPR(EMPTY[i]) + 2) * s) / 2, y + (m->cell - *(const u16 *)(CUI_SPR(EMPTY[i]) + 4) * s) / 2, s, RGB(52, 66, 92));
    }
    mui_r box = {dx, y0, x1, y0 + dh};
    draw_item_info(&box, cur >= 0 && cur < 8 ? EquipItem_GetEquipItem(eq, cur) : NULL, 1, 0, NULL);
    /* bảng chỉ số 2 cột (font nhỏ), nguồn: DrawEquipMenu gốc f5710..f57e8 (+0x180 HP, +0x18a/+0x18c ATK, +0x190/+0x192 DEF, +0x1a2 Kháng, +0x2e6/10 SP, +0x194/+0x196 Trúng, +0x198 Né, +0x196 Chính xác, +0x19e Chí mạng) */
    int colw = (x1 - x0 - m->gap) / 2, step = m->slp + 1, cls = h[0x22c];
    int A[5] = {*(int *)(h + 0x180) & 0xffff, I16(h, cls == 2 ? 0x18c : 0x18a), I16(h, 0x190), I16(h, 0x192), I16(h, 0x1a2)};
    int B[5] = {I16(h, 0x2e6) / 10, I16(h, cls == 2 ? 0x196 : 0x194), I16(h, 0x198), I16(h, 0x196), I16(h, 0x19e)};
    static const u16 LA[5] = {291, 25, 26, 27, 28}, LB[5] = {292, 29, 30, 31, 44};
    int avail = y1 - ty - 2 * (m->pad / 2 + 1), nrow = avail / step;
    if (nrow < 1) nrow = 1;
    if (nrow > 5) nrow = 5;
    if (nrow * step < avail && nrow <= 5) { int st2 = avail / nrow; if (st2 > m->lp + 2) st2 = m->lp + 2; if (st2 > step) step = st2; }
    mui_frame(x0, ty, x0 + colw, y1, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL);
    mui_frame(x1 - colw, ty, x1, y1, m->rad, 0, 1, RGB(14, 22, 34), 230, NULL);
    for (int i = 0; i < nrow; i++) {
        int yy = ty + m->pad / 2 + 1 + i * step;
        u8 t[16]; int tn;
        tn = mui_itoa(A[i], t); mui_clip(x0, ty, x0 + colw - m->pad - mui_sw(m->spx, t, tn) - 6, y1); mui_text(x0 + m->pad, yy, CT(LA[i]), -1, m->spx, C_DIM, 0); mui_clip_reset();
        mui_text(x0 + colw - m->pad, yy, t, tn, m->spx, C_WHITE, TF_RIGHT); mui_fill(x0 + m->pad, yy + step - 2, x0 + colw - m->pad, yy + step - 1, RGB(40, 54, 76));
        tn = mui_itoa(B[i], t); mui_clip(x1 - colw, ty, x1 - m->pad - mui_sw(m->spx, t, tn) - 6, y1); mui_text(x1 - colw + m->pad, yy, CT(LB[i]), -1, m->spx, C_DIM, 0); mui_clip_reset();
        mui_text(x1 - m->pad, yy, t, tn, m->spx, C_WHITE, TF_RIGHT); mui_fill(x1 - colw + m->pad, yy + step - 2, x1 - m->pad, yy + step - 1, RGB(40, 54, 76));
    }
    (void)bag;
}

int equip_cb(void *self, int id) {
    int sub = S8(self, 0xd);
    if (sub == 1) return pick_cb(self, id, 0xf);
    if (id >= H_CELL && id < H_CELL + 8) {
        int k = id - H_CELL;
        if (S8(self, 0xe) == k) mui_key(self, MUI_CLS_INGAME, -5); else S8(self, 0xe) = k;
        return 1;
    }
    return 0;
}

/* ================= popup chi tiết vật phẩm (StatePopup loại 10; ctor 0x13df50: +0x24 thông điệp, +0x814 ItemInfo*, +0x14 con trỏ kết quả) ================= */
void pop_item(void *self) {
    mui_m *m = &L; int in[4];
    u8 *it = *(u8 **)((u8 *)self + 0x814);
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    int pw = m->hd ? 640 : (78 * m->W + 50) / 100, ph = m->Ht - 2 * m->mg - (m->hd ? 40 : 0);
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 250, in);
    int by = in[3] - m->pad - m->btnH, top = in[1] + m->pad;
    const u8 *msg = (u8 *)self + 0x24;
    if (msg[0]) {                                              /* thông điệp của popup (vd "Mở rộng socket") */
        mui_clip(in[0] + m->pad, top, in[2] - m->pad, top + m->lp);
        mui_text((in[0] + in[2]) / 2, top, msg, -1, m->px, C_GOLD, TF_CENTER | TF_SHADOW); mui_clip_reset();
        top += m->lp + m->gap;
    }
    mui_r box = {in[0] + m->pad, top, in[2] - m->pad, by - m->gap};
    if (it) draw_item_info(&box, it, 0, 1, CT(96));
    int bw = mui_sw(m->px, CT(56), -1) + 4 * m->pad;
    if (bw < (in[2] - in[0]) / 3) bw = (in[2] - in[0]) / 3;
    mui_r ok = {(m->W - bw) / 2, by, (m->W + bw) / 2, by + m->btnH};
    mui_button(&ok, CT(56), -1, 1, 0xF2);
}

/* vật phẩm dưới con trỏ của Túi đồ (sub 0/1) / Trang bị (sub 0): dùng làm tiêu đề menu hành động (popup loại 7) */
u8 *mui_cur_item(void *igs) {
    int st = S8(igs, 0xc);
    if (st == 2 && S8(igs, 0xd) <= 1) {
        u8 *bag = BAGP(); int pg = S8(igs, 0xe), cl = S8(igs, 0xf);
        return pg * 16 + cl < BagItem_GetCount(bag) ? BagItem_GetBagItemPtr(bag, pg, cl) : NULL;
    }
    if (st == 3 && S8(igs, 0xd) == 0 && S8(igs, 0xe) >= 0 && S8(igs, 0xe) < 8) return EquipItem_GetEquipItem(HERO_GetEquipItemPtr(HEROP()), S8(igs, 0xe));
    return NULL;
}
