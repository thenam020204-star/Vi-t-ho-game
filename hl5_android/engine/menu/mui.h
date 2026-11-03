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

/* ---------------- text.c ---------------- */
#define TF_SHADOW 1
#define TF_CENTER 2                    /* x = tâm */
#define TF_RIGHT 4                     /* x = mép phải */
H int mui_text_init(void);             /* 1 = có vi.bin */
H int mui_sw(int px, const u8 *s, int len);                                        /* bề rộng (len<0: tới NUL) */
H int mui_text(int x, int y, const u8 *s, int len, int px, u32 rgb, int flags);   /* trả bề rộng; px 10/15/18 */
H int mui_wrap(int px, const u8 *s, int len, int maxw, u16 *st, u16 *ln, int maxl); /* số dòng; (st,ln)=byte; dòng đầu bỏ dấu cách đầu */
H int mui_text_skip(int x, int y, const u8 *s, int len, int px, u32 rgb, int flags); /* như mui_text nhưng bỏ dấu cách đầu dòng */
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

/* ---------------- dbg.c (chỉ -DMUI_DEBUG) ---------------- */
#ifdef MUI_DEBUG
extern H volatile int mui_dbg_mw, mui_dbg_mh;   /* khung mockup mô phỏng (400x240 / 1200x540) do harness ghi */
extern H volatile int mui_dbg_hit;
H void dbg_paint(void *self);
H int dbg_touch(void *self, int x, int y);
H int dbg_move(void *self, int x, int y);
#endif
