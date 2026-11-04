/* L1/T17c4: Tinh linh (InGameMenu state 7). Số đo: ui_new/menu/LAYOUT.md §11 + mock_b2a.screen_spirit; hit_rects.json tinh_linh / tinh_linh_ky_nang.
 * Kiến trúc A: Paint + Touch mới; KeySpiritMenu/Process/SetPopupState GỐC. Trường gốc (dò động harness/l1/probe_sp2.py, BAO-CAO-L1 §5):
 *   +0xd sub: 0 hàng tab con, 1 chọn ô trang bị (tab 0), 2 bộ lọc trang bị (OpenSpiritEquipFilter, con trỏ +0x10, 5/trang), 3 chọn kỹ năng (tab 1), 4 (giữ đường gốc)
 *   +0xe tab con (0 Cơ bản/Trang bị, 1 Kỹ năng); +0xf: sub 1 = ô trang bị 0/1; tab 1 = kỹ năng 0..3 (UP=0 DOWN=1 LEFT=2 RIGHT=3; -1 khi chưa chọn); +0x28/+0x29 khung hoạt hình
 * Dữ liệu: HERO::GetSpiritInfoPtr = HERO+6032: s8 +1 cấp, +2..+5 kỹ năng đã đăng ký (>42 = kỹ năng Tinh linh), int +8 EXP, int +0x10 EXP tối đa, s16 +0x14 sức mạnh phép;
 * trang bị: HERO::GetEquipItemSpiritPtr + EquipItemSpirit::GetEquipItemSpirit(ô) = ItemInfo*; SP: HERO s8 [8064+37]/[8064+38]; kỹ năng: HERO::GetHeroSkillInfoPtr(idx), cấp HERO[588+idx]. */
#include "l1ui.h"

extern H u8 *HERO_GetSpiritInfoPtr(void *hero);
extern H void *HERO_GetEquipItemSpiritPtr(void *hero);
extern H u8 *EquipItemSpirit_GetEquipItemSpirit(void *eq, int slot);
extern H void CommonUi_GetSkillDiscription(void *cui, u8 *out, int a, u8 *info);
extern H void spirit_pick_paint(void *self);
extern H int spirit_pick_cb(void *self, int id);

#define H_SUB 0x30      /* +0,1 */
#define H_SLOT 0x34     /* +0,1 */
#define H_SKILL 0x38    /* +0..3 */
#define H_USE 0x3C
#define H_DETAIL 0x3D
#define K_UP -1
#define K_DOWN -2
#define K_LEFT -3
#define K_RIGHT -4

static int sk_icon(int idx) { return idx > 42 ? idx + 130 : idx + 43 * (HEROP()[0x22c] & 3); }

static void spirit_sprite(void *self, int cx, int cy, int r) {
    u8 *ent = *(u8 **)(gv + 5196); u32 g = _lcdG;
    if (!ent) return;
    void *spr = CHAR_GetSpritePtr(ent);
    if (!spr) return;
    Graphic_SetClip(g, cx - r, cy - r, 2 * r, 2 * r);
    SPRITE_DrawSprite(spr, Graphic_GetFB(g), cx, cy + r * 55 / 100, 0, 1, *((s8 *)self + 0x29), 0);
    Graphic_ResetClip(g);
}
static void ring(int x, int y, int pr) {
    mui_m *m = &L; int cx = x + pr / 2, cy = y + pr / 2, R = pr / 2, band = pr / 12 < 2 ? 2 : pr / 12;
    mui_disc(cx, cy, R, C_INK); mui_disc(cx, cy, R - 1, C_ORANGE_DK); mui_disc(cx, cy, R - 2, C_ORANGE);
    mui_disc(cx, cy, R - 2 - band, C_ORANGE_DK); mui_disc(cx, cy, R - 3 - band, C_PANEL); (void)m;
}

static void paint_basic(void *self, int x0, int ty, int x1, int y1) {
    mui_m *m = &L; u8 *h = HEROP(), *inf = HERO_GetSpiritInfoPtr(h);
    int sub = S8(self, 0xd), f = S8(self, 0xf), cw = x1 - x0, lw = (cw * 46 + 50) / 100;
    l1_panel(x0, ty, x0 + lw, y1);
    int pr = m->hd ? 84 : 22 * m->s + 20, px_ = x0 + m->pad, yy = ty + m->pad;
    ring(px_, yy, pr); spirit_sprite(self, px_ + pr / 2, yy + pr / 2, pr / 2 - 2 - (pr / 12 < 2 ? 2 : pr / 12));
    int nx = px_ + pr + m->pad / 2 + 2, ny = yy + ((pr - 3 * m->lp) / 2 > 0 ? (pr - 3 * m->lp) / 2 : 0), mw = x0 + lw - nx - m->pad;
    u8 lv[40]; int n = 0; const u8 *l13 = CT(13);
    while (l13[n] && n < 30) { lv[n] = l13[n]; n++; }
    lv[n++] = ' '; n += mui_itoa(inf[1], lv + n);
    mui_clip(nx, 0, nx + mw, m->Ht);
    mui_text(nx, ny, CT(160), -1, m->px, C_GOLD, TF_SHADOW);
    mui_text(nx, ny + m->lp, lv, n, m->px, C_WHITE, 0);
    mui_text(nx, ny + 2 * m->lp, CT(6), -1, m->px, C_DIM, 0);
    mui_clip_reset();
    yy += (pr > 3 * m->lp ? pr : 3 * m->lp) + m->pad;
    { u8 t[16]; int tn = mui_itoa(I16(inf, 0x14), t), vw = mui_sw(m->px, t, tn);
      mui_clip(x0 + m->pad, 0, x0 + lw - m->pad - vw - 4, m->Ht); mui_text(x0 + m->pad, yy, CT(161), -1, m->px, C_DIM, 0); mui_clip_reset();
      mui_text(x0 + lw - m->pad, yy, t, tn, m->px, C_GOLD, TF_RIGHT | TF_SHADOW); }
    yy += m->lp + 4;
    mui_bar(x0 + m->pad, yy, lw - 2 * m->pad, CT(12), -1, I32(inf, 8), I32(inf, 0x10), C_EXP);
    /* phải: 2 ô trang bị + chi tiết */
    int rx0 = x0 + lw + m->gap + 2, ry = ty;
    mui_text(rx0, ry, CT(163), -1, m->px, C_DIM, 0); ry += m->lp + m->gap;
    void *eq = HERO_GetEquipItemSpiritPtr(h); u8 *cur = NULL;
    for (int i = 0; i < 2; i++) {
        int x = rx0 + i * m->pitch; u8 *it = EquipItemSpirit_GetEquipItemSpirit(eq, i);
        if (i == (sub > 0 ? f : 0)) cur = it;
        mui_cell_i(x, ry, it ? I16(it, 22) : -1, 0, it ? npc_rarity(it + 24) : -1, 0, sub > 0 ? i == f : i == 0, H_SLOT + i);
    }
    ry += m->cell + m->gap;
    mui_r box = {rx0, ry, x1, y1};
    npc_item_info(&box, cur, 0, 0, NULL);
    mui_hit(H_DETAIL, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
}

static void paint_skills(void *self, int x0, int ty, int x1, int y1) {
    mui_m *m = &L; u8 *h = HEROP(), *inf = HERO_GetSpiritInfoPtr(h);
    int sub = S8(self, 0xd), f = S8(self, 0xf), c = m->cell, rowW = 4 * c + 3 * m->cg;
    for (int i = 0; i < 4; i++) {
        int x = x0 + i * (c + m->cg), idx = I8(inf, 2 + i);
        mui_cell_i(x, ty, idx > 0 ? sk_icon(idx) : -1, 0, -1, 0, sub >= 3 && i == f, H_SKILL + i);
        u8 t[2]; t[0] = '1' + i; mui_text(x + 2, ty + 1, t, 1, m->spx, C_WHITE, TF_SHADOW);
    }
    int nx = x0 + rowW + m->gap + 2, mx = I8(h, 8064 + 38);
    static const u8 SP_[] = {'S', 'P'};
    mui_bar(nx, ty + (c - m->barH) / 2, x1 - nx, SP_, 2, I8(h, 8064 + 37), mx < 1 ? 5 : mx, C_GREEN);
    int ty2 = ty + c + m->gap, by = y1 - m->btnH;
    mui_r box = {x0, ty2, x1, by - m->gap};
    int ci = f >= 0 && f < 4 ? f : 0, idx = I8(inf, 2 + ci);
    u8 *in = idx > 0 ? HERO_GetHeroSkillInfoPtr(h, idx) : NULL;
    mui_hit(H_DETAIL, box.x0, box.y0, box.x1, box.y1, HK_NONE, 0, 0);
    if (!in) mui_frame(box.x0, box.y0, box.x1, box.y1, m->rad, 0, 1, RGB(10, 16, 26), 235, NULL);
    else {
        static u8 desc[600]; static int last = -1;
        u8 meta[40]; int mn = 0; const u8 *l13 = CT(13);
        while (l13[mn] && mn < 20) { meta[mn] = l13[mn]; mn++; }
        meta[mn++] = ' '; mn += mui_itoa(I8(h, 588 + idx), meta + mn);
        const u8 *nm = *(u8 **)(in + 4);
        int y = mui_descbox2(&box, nm ? nm : (const u8 *)"", -1, -1, meta, mn);
        for (int i = 0; i < 600; i++) desc[i] = 0;
        CommonUi_GetSkillDiscription(COMMONUI(), desc, -1, in); desc[599] = 0;
        l1_desc(&box, y, desc, 1, last != idx); last = idx;
    }
    mui_r b = {x0, by, x1, y1};
    int bn; const u8 *bt = mui_hint(CT(55), &bn);
    mui_button(&b, bt, bn, idx > 0 ? 1 : 3, 0); mui_hit(H_USE, b.x0, b.y0, b.x1, b.y1, HK_CB, 0, 0);
}

void spirit_paint(void *self) {
    mui_m *m = &L; int e = S8(self, 0xe), sub = S8(self, 0xd);
    l1_prelude();
    mui_r wr = {m->wx, m->wy, m->wx + m->winW, m->wy + m->winH}, body;
    mui_window(&wr, CT(6), -1, MUI_TAB_ICONS, NULL, 6, &body);
    if (sub == 2) { spirit_pick_paint(self); return; }
    int x0, y0, x1, y1; l1_geo(&x0, &y0, &x1, &y1);
    int sh = l1_subtabs(x0, y0, x1, CT(158), -1, CT(159), -1, e == 1, H_SUB), ty = y0 + sh + m->gap;
    if (e == 1) paint_skills(self, x0, ty, x1, y1); else paint_basic(self, x0, ty, x1, y1);
}

static void goto_sub0(void *self) { if (S8(self, 0xd) > 0) StateInGameMenu_SetSubState(self, 0); }
int spirit_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_INGAME, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id >= 0x10 && id < 0x10 + MUI_TAB_ICONS) { if (id - 0x10 + 1 != 7) StateInGameMenu_SetState(self, id - 0x10 + 1); mui_sc[0].pos = 0; return 0; }
    int sub = S8(self, 0xd);
    if (sub == 2) { spirit_pick_cb(self, id); return 0; }
    if (id == H_SUB || id == H_SUB + 1) {
        int want = id - H_SUB, e = S8(self, 0xe);
        if (want == e) return 0;
        goto_sub0(self);
        mui_key(self, MUI_CLS_INGAME, want ? K_RIGHT : K_LEFT);
    } else if (id == H_SLOT || id == H_SLOT + 1) {
        if (S8(self, 0xe) != 0) return 0;
        if (sub != 1) StateInGameMenu_SetSubState(self, 1);
        S8(self, 0xf) = id - H_SLOT; mui_key(self, MUI_CLS_INGAME, -5);
    } else if (id >= H_SKILL && id < H_SKILL + 4) {
        if (S8(self, 0xe) != 1) return 0;
        if (sub < 3) mui_key(self, MUI_CLS_INGAME, K_DOWN);
        mui_key(self, MUI_CLS_INGAME, -(1 + (id - H_SKILL)));
    } else if (id == H_USE) {
        if (S8(self, 0xe) != 1) return 0;
        if (sub < 3) mui_key(self, MUI_CLS_INGAME, K_DOWN);
        mui_key(self, MUI_CLS_INGAME, -5);
    }
    return 0;
}
int spirit_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_INGAME, x, y); }
