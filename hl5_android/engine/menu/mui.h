/* T17c0: thư viện menu mới (đoạn PT_LOAD 0x171000). Thiết kế: BAO-CAO-RE.md §14.
 * Quy tắc: không biến toàn cục có con trỏ khởi tạo (không reloc), không cấp phát, không gọi API ngoài guest.
 * Mọi hàm/biến guest khai báo `extern H` và được ld.lld gán địa chỉ tuyệt đối bằng --defsym (build.sh, từ nm của .so). */
typedef unsigned char u8;
typedef signed char s8;
typedef unsigned short u16;
typedef short s16;
typedef unsigned int u32;
#define H __attribute__((visibility("hidden")))
#define NULL ((void *)0)

/* ---------------- engine chữ (vn.c, cave A) ---------------- */
typedef struct {
    int status; const u8 *buf; int nwords, nch; const u16 *widx; const u8 *words; const u8 *font[3];
    int hd; int k, k0; u32 t10; int force0;
} vn_state_t;
extern H vn_state_t vn_state[];
extern H int vn_ensure(void);

/* ---------------- guest ---------------- */
extern H u32 _lcdG;                    /* Graphic* (giá trị con trỏ) */
extern H u8 gv[];                      /* biến toàn cục game (cấu trúc lớn); CommonUi* = *(u8**)(gv+5572) */
#define COMMONUI() (*(u8 **)(gv + 5572))
#define ITEM_ICON(i) (COMMONUI() + 180 + 24 * (i))   /* GbmImage của icon vật phẩm i<=225 (CommonUi::DrawIcon 0x7c864) */
extern H u32 SKIP_COLOR;               /* màu trong suốt của GbmImage (RGB565) */

/* ---- guest (c1): văn bản, Graphic, HERO, StateInGameMenu (địa chỉ gán bởi build.sh --defsym) ---- */
extern H u32 _pTextMgr;                /* biến chứa TextMgr* */
extern H const u8 *TextMgr_GetCommonText(u32 tm, int i);
extern H const u8 *TextMgr_GetHelpTitleString(u32 tm, int i);
extern H const u8 *TextMgr_DT_GetHelpInfoString(u32 tm, int i);
extern H int Graphic_GetFB(u32 g);
extern H int Graphic_GetFBOff(u32 g);
extern H void Graphic_CopyFrameBuffer(u32 g, int fb, int fboff);
extern H void HERO_CalcStatusComputation(u8 *hero, int a);
extern H void StateInGameMenu_SetState(void *self, int s);
extern H void StateInGameMenu_SetSubState(void *self, int s);
extern H int BagItem_GetMoney(void *bag);
extern H void *HERO_GetBagItemPtr(void *hero);
/* c1-fix: tên khu vực, sprite nhân vật thật, clip */
extern H const u8 *TextMgr_GetInGameText(u32 tm, int i);
extern H int HERO_IsNoneEquipWeapon(u8 *hero);
extern H int HERO_IsNoneEquipShield(u8 *hero);
extern H void *CHAR_GetSpritePtr(u8 *hero);
extern H void SPRITE_DrawSprite(void *spr, int fb, int x, int y, int a, int b, int frame, int d);
extern H void Graphic_SetClip(u32 g, int x, int y, int w, int h);
extern H void Graphic_ResetClip(u32 g);
#define HEROP() (*(u8 **)(gv + 5180))
#define CT(i) TextMgr_GetCommonText(_pTextMgr, (i))

/* ---- guest (c2): Túi đồ / Trang bị / belt ---- */
extern H int BagItem_GetSize(void *bag);
extern H int BagItem_GetCount(void *bag);
extern H u8 *BagItem_GetBagItemPtr(void *bag, int cat, int idx);
extern H int BagItem_GetBunch(void *bag, int cat, int id);
extern H int EquipItemInfo_IsEquipIng(void *item);
extern H void *HERO_GetEquipItemPtr(void *hero);
extern H u8 *EquipItem_GetEquipItem(void *eq, int slot);
extern H int HERO_GetHotKeyToggle(void *hero);
extern H int HERO_GetHotKeyBeltType(void *hero, int slot, int bank);
extern H int HERO_GetHotKeyBeltSkillIdx(void *hero, int slot, int bank);
extern H void ItemTable_GetItemTableInfo(void *tbl, void *out, int cat, int id);
extern H void CommonUi_DrawIcon(void *cui, int fb, int x, int y, int icon, int a, int b, int c, int d);
extern H void CommonUi_DrawItemDiscription(void *cui, int fb, int x, int y, void *item, int a, int b);
extern H void BFont_DrawTextLine(void *f, int a1, int a2, int a3, int a4, int a5, const u8 *buf, int a7);
#define ITEMTABLE() (*(u8 **)(gv + 5132))

/* ---------------- layout (LAYOUT.md §1) ---------------- */
typedef struct {
    int W, Ht, s, hd, px, spx, lp, slp, mg, winH, winW, wx, wy, bt, pad, gap, titleH, tabH, kb,
        btnH, cell, cg, pitch, gridW, barH, rowH, sbw, rad;
} mui_m;
extern H mui_m L;                      /* layout hiện hành: mui_begin() cập nhật khi (W,H) đổi */
H void mui_layout(mui_m *m, int W, int Ht);

/* ---------------- màu: 0xRRGGBB ---------------- */
#define RGB(r, g, b) (((r) << 16) | ((g) << 8) | (b))
#define C_INK RGB(0, 0, 0)
#define C_NAVY RGB(13, 19, 30)
#define C_PANEL RGB(22, 32, 48)
#define C_CELL RGB(30, 46, 68)
#define C_CELL2 RGB(24, 37, 56)
#define C_LINE RGB(70, 92, 120)
#define C_ORANGE RGB(240, 138, 22)
#define C_ORANGE_HI RGB(255, 192, 56)
#define C_ORANGE_DK RGB(150, 72, 10)
#define C_ORANGE_IN RGB(120, 64, 16)
#define C_WHITE RGB(246, 240, 228)
#define C_DIM RGB(150, 164, 184)
#define C_GOLD RGB(255, 214, 90)
#define C_GREEN RGB(104, 220, 112)
#define C_BLUE RGB(60, 140, 236)
#define C_DISABLED RGB(88, 98, 112)
#define C_HP RGB(214, 52, 48)
#define C_SP RGB(52, 124, 226)
#define C_EXP RGB(226, 188, 48)
#define C_TRACK RGB(10, 15, 24)

/* ---------------- gfx.c: vẽ thẳng vào khung hình RGB565 của guest ---------------- */
H int mui_fb_begin(void);              /* đọc FB/Graphic; 1 = dùng được (RGB565), 0 = bỏ qua (không vẽ) */
H void mui_clip(int x0, int y0, int x1, int y1);
H void mui_clip_reset(void);
H int mui_fb_w(void);
H int mui_fb_h(void);
H void mui_fill(int x0, int y0, int x1, int y1, u32 rgb);
H void mui_fill_a(int x0, int y0, int x1, int y1, u32 rgb, int a);
H void mui_vgrad(int x0, int y0, int x1, int y1, u32 top, u32 bot);
H void mui_rr(int x0, int y0, int x1, int y1, int rad, u32 rgb, int a);          /* hình chữ nhật góc cắt; a=255 đặc */
H void mui_frame(int x0, int y0, int x1, int y1, int rad, int orange, int thick, u32 fill, int fa, int *in4); /* fill<0: không tô */
H void mui_gbm(const u8 *gbm, int x, int y, int s, int tint);                     /* GbmImage x s NEAREST; tint<0: màu gốc */
H void mui_pix(int x, int y, u32 rgb);
H u32 mui_getpx(int x, int y);                                    /* điểm RGB565 thô (không clip) */
H void mui_put16(int x, int y, u32 c565);
H void mui_disc(int cx, int cy, int r, u32 rgb);

/* ---------------- text.c ---------------- */
#define TF_SHADOW 1
#define TF_CENTER 2                    /* x = tâm */
#define TF_RIGHT 4                     /* x = mép phải */
H int mui_text_init(void);             /* 1 = có vi.bin */
H int mui_sw(int px, const u8 *s, int len);                                        /* bề rộng (len<0: tới NUL) */
H int mui_text(int x, int y, const u8 *s, int len, int px, u32 rgb, int flags);   /* trả bề rộng; px 10/15/18 */
H int mui_wrap(int px, const u8 *s, int len, int maxw, u16 *st, u16 *ln, int maxl); /* số dòng; (st,ln)=byte; dòng đầu bỏ dấu cách đầu */
H int mui_text_skip(int x, int y, const u8 *s, int len, int px, u32 rgb, int flags); /* như mui_text nhưng bỏ dấu cách đầu dòng */
H int mui_text_line(int x, int y, const u8 *base, int start, int len, int px, u32 col, int flags);
H int mui_code_color(u8 b);                                                       /* màu mã BFont (-1 nếu không phải mã) */
H int mui_ascii(const char *a, u8 *out);                                           /* ASCII -> byte game (chép) */

/* ---------------- widgets.c ---------------- */
typedef struct { int x0, y0, x1, y1; } mui_r;
H void mui_window(const mui_r *r, const u8 *title, int tlen, int ntabs, const u8 *const *unused, int active, mui_r *body); /* khung + tiêu đề + nút "<" [+ thanh tab] */
H void mui_button(const mui_r *r, const u8 *label, int len, int kind, int hid);  /* kind 0 thường 1 chính 2 nhấn 3 vô hiệu */
H void mui_scrollbar(int x, int y0, int y1, int pos, int total, int view);
H void mui_bar(int x, int y, int w, const u8 *label, int llen, int val, int mx, u32 col);
H void mui_cell(int x, int y, const u8 *gbm, int count, int rarity_rgb, int equipped, int selected, int hid);
H void mui_row(const mui_r *r, const u8 *label, int llen, const u8 *value, int vlen, int selected, int hid);
H int mui_descbox(const mui_r *r, const u8 *name, int nlen, int name_rgb); /* khung + tên + vạch; trả y bắt đầu nội dung */
H int mui_descbox2(const mui_r *r, const u8 *name, int nlen, int name_rgb, const u8 *meta, int mlen); /* + dòng phụ (font nhỏ, mờ) giữa tên và vạch */
H void mui_icon(int idx, int x, int y, int s, int tint);          /* icon vật phẩm/kỹ năng theo chỉ số (0..225 sprite, >225 qua guest DrawIcon) x s NEAREST */
H void mui_cell_i(int x, int y, int icon, int count, int rarity_rgb, int flag, int selected, int hid); /* như mui_cell nhưng theo chỉ số icon (-1 = ô trống) */
H void mui_arrow(const mui_r *r, int right, int hid);             /* nút mũi tên ◀ ▶ (widget c1 nâng lên dùng chung) */
H void mui_hscroll(int x0, int y, int x1, int pos, int total, int view);
H void mui_popup_geom(int nlines, int *pw, int *ph);
H void mui_popup(const u8 *text, int tlen, int yes_no, const u8 *l1, int n1, const u8 *l2, int n2, mui_r *out);
#define MUI_TAB_ICONS 9
H const u8 *mui_tab_icon(int i);       /* weak: c1 ghi đè, trả GbmImage* biểu tượng tab i (NULL = ô vuông) */
H const u8 *mui_icon_back(void);       /* weak: GbmImage* của mũi tên "<" (ui.mgr 25); NULL = vẽ bằng điểm */
H int mui_itoa(int v, u8 *out);
H void mui_popup_box(int x0, int y0, int pw, const u8 *text, int tlen, int yes_no, const u8 *l1, int n1, const u8 *l2, int n2, int *ph_out);

/* ---------------- touch.c ---------------- */
#define HK_NONE 0                      /* chỉ nuốt chạm */
#define HK_OK 1                        /* ghi s8 [self+a]=b rồi KeyPressed(-5) gốc */
#define HK_SET 2                       /* ghi s8 [self+a]=b (không phím) */
#define HK_KEY 3                       /* KeyPressed(a) gốc */
#define HK_CB 4                        /* gọi callback của state: id */
#define HK_DRAG 5                      /* kéo dọc cuộn scroller a (px/dòng b) */
#define MUI_MAXHIT 96
H void mui_hits_reset(void);
H void mui_hit(int id, int x0, int y0, int x1, int y1, int kind, int a, int b);
H int mui_hit_find(int x, int y);      /* chỉ số hit trên cùng chứa (x,y), -1 nếu không */
typedef struct { s16 x0, y0, x1, y1; s16 id; u8 kind; s8 a; s16 b; } mui_hitrec;
extern H mui_hitrec mui_hits[MUI_MAXHIT];
extern H int mui_nhit;
typedef struct { s16 pos, total, view, start_pos, start_y, step; } mui_scroll;
#define MUI_NSCROLL 4
extern H mui_scroll mui_sc[MUI_NSCROLL];
H int mui_touch_press(void *self, int cls, int x, int y);   /* HK_OK/SET/KEY xử lý sẵn; trả kind (hoặc -1); *mui_last_hit = id */
H int mui_touch_move(void *self, int cls, int x, int y);
H int mui_touch_release(void);
H int mui_key(void *self, int cls, int code);               /* KeyPressed gốc của lớp cls */
extern H int mui_last_id;              /* id hit gần nhất được chạm */
extern H int mui_tx, mui_ty;           /* toạ độ chạm gần nhất (press/move) */
extern H int mui_drag;                 /* scroller đang kéo (-1 không) */

/* ---------------- dispatch.c ---------------- */
#define MUI_CLS_GAME 0
#define MUI_CLS_POPUP 1
#define MUI_CLS_INGAME 2
#define MUI_CLS_NPC 3
#define MUI_CLS_MAIN 4
#define MUI_CLS_EXPERT 5
#define MUI_CLS_MAP 6
#define MUI_CLS_CASH 7
#define MUI_NCLS 8
#define MUI_F_DISABLE 1                /* cờ [mui_flags]: tắt mọi menu mới (so sánh với gốc) */
#define MUI_F_DBG 2                    /* cờ debug (chỉ bản harness -DMUI_DEBUG): vẽ trang widget */
extern H volatile u32 mui_flags;
H int mui_begin(void);                 /* đầu Paint: kiểm FB, cập nhật layout, reset hits, clip */

/* ---------------- ingame.c / popup.c (c1) ---------------- */
H void ig_paint(void *self);
H int ig_touch(void *self, int x, int y);
H int ig_move(void *self, int x, int y);
H void pop_paint(void *self);
H int pop_touch(void *self, int x, int y);
H int mui_cslen(const u8 *s);
H int mui_money(int v, u8 *out);                    /* 1,234,567 */
H void mui_portrait(int x, int y, int pr);          /* vòng chân dung + sprite nhân vật thật */
H const u8 *mui_hint(const u8 *s, int *n);       /* bỏ gợi ý phím đầu chuỗi ("#", "*", "d.") */
extern H int mui_popup_sel;                       /* nút nổi bật của popup Có/Không (0/1) */
#define GS(self) (*(s8 *)((u8 *)(self) + 0xc))
#define POP_TYPE(self) (*(s8 *)((u8 *)(self) + 0xe))
#define POP_NBTN(self) (*(s8 *)((u8 *)(self) + 0x10))

/* ---------------- bag.c (c2) ---------------- */
H void bag_paint(void *self, const mui_r *body);
H int bag_cb(void *self, int id);
H void equip_paint(void *self, const mui_r *body);
H int equip_cb(void *self, int id);
H u8 *mui_cur_item(void *igs);                    /* vật phẩm dưới con trỏ (Túi/Trang bị) hoặc NULL */
extern H u32 ig_vt;                                /* vtable của StateInGameMenu (đặt khi ig_paint chạy) */
H void pop_item(void *self);                      /* StatePopup loại 10: chi tiết vật phẩm */
extern H int mui_cap_on;
H void mui_cap_line(void *f, int a1, int a2, int a3, int a4, int a5, const u8 *buf, int a7);

/* ---------------- combo.c (c3a) ---------------- */
extern H int HERO_GetComboSkillBelt(void *hero, int i);
extern H u8 *HERO_GetHeroSkillInfoPtr(void *hero, int i);
H void combo_paint(void *self, const mui_r *body);
H int combo_cb(void *self, int id);

/* ---------------- cash.c (L1/T17c6), t7.c ---------------- */
H void cash_paint(void *self);
H int cash_touch(void *self, int x, int y);
H int cash_move(void *self, int x, int y);

/* ---------------- dbg.c (chỉ -DMUI_DEBUG) ---------------- */
#ifdef MUI_DEBUG
extern H volatile int mui_dbg_mw, mui_dbg_mh;   /* khung mockup mô phỏng (400x240 / 1200x540) do harness ghi */
extern H volatile int mui_dbg_hit;
H void dbg_paint(void *self);
H int dbg_touch(void *self, int x, int y);
H int dbg_move(void *self, int x, int y);
#endif

/* ---------------- npc.c (L2: StateInNpcMenu 8 cửa sổ + popup NPC) ---------------- */
H void npc_paint(void *self);
H int npc_touch(void *self, int x, int y);
H int npc_move(void *self, int x, int y);
H void npcpop_paint(void *self);
H int npcpop_touch(void *self, int x, int y);
H void npc_item_info(const mui_r *box, u8 *item, int flag, int scr, const u8 *meta_other);
H int npc_rarity(const u8 *name);
H void npc_pick_paint(void *self, int x0, int y0, int x1, int y1, int curoff, const u8 *hint);
H int npc_pick_cb(void *self, int id, int curoff);
extern H u8 NPC_vtable[];                      /* "vtable for StateInNpcMenu" (nm); ô hàm = +8 */
#define NPC_POPUP5(self) (POP_TYPE(self) == 5 && *(u32 *)((u8 *)(self) + 8) && *(u32 *)(*(u32 *)((u8 *)(self) + 8)) == (u32)NPC_vtable + 8)   /* L2: nhập số lượng */
H void qty_paint(void *self);
H int qty_touch(void *self, int x, int y);
#define NPC_POPUP(self) (POP_TYPE(self) == 4 && *(u32 *)(*(u32 *)((u8 *)(self) + 8)) == (u32)NPC_vtable + 8)

/* ---------------- spirit.c (L1/T17c4) ---------------- */
H void spirit_paint(void *self);
H int spirit_touch(void *self, int x, int y);
H int spirit_move(void *self, int x, int y);

/* ---------------- gpop.c (L1/T17c6: popup StateGame) ---------------- */
H void gpop_paint(void *self);
H int gpop_touch(void *self, int x, int y);
H int gpop_move(void *self, int x, int y);
H void gres_paint(void *self);   /* StateGame state 2 sub 1..2 (kết quả Expert) */

/* ---------------- quest.c (L1/T17c4: Nhiệm vụ) ---------------- */
H void quest_paint(void *self);
H int quest_touch(void *self, int x, int y);
H int quest_move(void *self, int x, int y);

/* ---------------- expert.c (L1/T17c6: Chuyên gia) ---------------- */
H void expert_paint(void *self);
H int expert_touch(void *self, int x, int y);
H int expert_move(void *self, int x, int y);
extern H u8 EXPERT_vtable[];                   /* "vtable for StateInExpertMenu" (nm); ô hàm = +8 */
#define EXPERT_POPUP(self, t) (POP_TYPE(self) == (t) && *(u32 *)((u8 *)(self) + 8) && *(u32 *)(*(u32 *)((u8 *)(self) + 8)) == (u32)EXPERT_vtable + 8)   /* L1: popup của Chuyên gia (loại 4 Có/Không, 5 nhập số lượng): dùng lại widget npcpop/qty của L2 */

/* ---------------- mmenu.c (L3/T17c7: StateMainMenu) ---------------- */
H void mm_grid_paint(void *self);
H void mm_slot_paint(void *self);
H void mm_class_paint(void *self);
H void mm_option_paint(void *self);
H int mm_touch(void *self, int x, int y);
H int mm_move(void *self, int x, int y);
H void mmpop_paint(void *self);
H int mmpop_touch(void *self, int x, int y);
H int mmpop_move(void *self, int x, int y);
H int mm_navkey(int key);
extern H u8 MAIN_vtable[];                     /* "vtable for StateMainMenu" (nm); ô hàm = +8 */
#define MAIN_POPUP8(self) (POP_TYPE(self) == 8 && *(u32 *)((u8 *)(self) + 8) && *(u32 *)(*(u32 *)((u8 *)(self) + 8)) == (u32)MAIN_vtable + 8)   /* L3: popup mô tả lớp (loại 8, cha = MainMenu) */
/* ---------------- map.c (L1b/T17c8: StateMap) ---------------- */
H void map_paint(void *self);
H int map_touch(void *self, int x, int y);
H int map_move(void *self, int x, int y);
/* ---------------- L6b: mm2.c (MainMenu: Chuyên Gia chọn chế độ, Thành tựu, Trợ giúp), qpop.c (popup nhiệm vụ loại 9), cui.c (ẩn ↺ + vòng xám) ---------------- */
H void mm_dimwin(const u8 *title, mui_r *c);
H void mm_tilebox(int x0, int y0, int x1, int y1, int sel);
H void mm_panel(int x0, int y0, int x1, int y1);
H void mm_expert_paint(void *self);
H void mm_mission_paint(void *self);
H void mm_help_paint(void *self);
H int mm2_touch(void *self, int x, int y);
H int mm2_move(void *self, int x, int y);
H void qpop_paint(void *self);
H int qpop_touch(void *self, int x, int y);
extern H volatile u32 mui_drawn;                  /* 1 = Paint của state trên cùng khung này do menu mới vẽ (đặt ở thunk Paint ngoài cùng) */
H void l6_hide_fb(void *cui);
H void l6_hide_cancel(void *cui);

/* ---------------- L6c: l6c.c ---------------- */
H void qmap_paint(void *self);
H int qmap_touch(void *self, int x, int y);
H void sk_pick_paint(void *self, int x0, int y0, int x1, int y1, const u8 *hint);
H int sk_pick_cb(void *self, int id);
H void skc_paint(void *self);
H int skc_touch(void *self, int x, int y);
extern H u8 *BagItem_GetBagItemPtrS(void *bag, int idx);
H void sp4_paint(void *self);
H int sp4_touch(void *self, int x, int y);
H void mmd_paint(void *self);
H int mmd_touch(void *self, int x, int y);
