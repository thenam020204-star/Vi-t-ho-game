/* T17c1: StatePopup (popup chung của game; mọi popup của InGameMenu: SetPopupState -> CallPopupState). Paint + Touch mới; KeyPressed/Process GỐC.
 * Trường StatePopup (đọc từ PopupSet/PopupInit/Paint/KeyPressed gốc): +0x08 state cha, +0x0e loại (0..15), +0x10 số nút, +0x14 con trỏ byte KẾT QUẢ (= con trỏ nút, -1 = đã đóng),
 * +0x24 thông điệp (chuỗi mã game), +0x43c + 48*i chuỗi nút i. Gốc: loại 1,2,3 = thông điệp + 1 hoặc 2 nút (Có/Không: LEFT/RIGHT đổi *kết quả, OK xác nhận, Back = -1),
 * loại 7 = menu dọc (UP/DOWN đổi *kết quả). Loại khác (0 thông báo tự đóng, 4-6, 8-15) chưa làm: điều kiện states.def từ chối -> đường gốc. */
#include "mui.h"

#define P8(p, o) (*(s8 *)((u8 *)(p) + (o)))
#define MSG(self) ((u8 *)(self) + 0x24)
#define BTN(self, i) ((u8 *)(self) + 0x43c + 48 * (i))
#define RES(self) (*(s8 **)((u8 *)(self) + 0x14))

static void parent_paint(void *self) {                    /* như gốc: Paint của state cha (vtable +0x40) trước */
    u32 par = *(u32 *)((u8 *)self + 8);
    if (!par) return;
    void (**vt)(void *) = *(void (***)(void *))par;
    vt[0x40 / 4]((void *)par);
}

void pop_paint(void *self) {
    mui_m *m;
    parent_paint(self);
    if (!mui_begin()) return;                              /* cha đã vẽ (và đặt lại lưới/vùng chạm): vẽ popup lên trên */
    m = &L;
    s8 *res = RES(self);
    if (res && *res == -1) return;                         /* đã đóng (như gốc) */
    int t = POP_TYPE(self), nb = POP_NBTN(self), cur = res ? *res : 0, n1, n2;
    mui_r out;
    if (t == 10) { pop_item(self); return; }                /* chi tiết vật phẩm (c2) */
    if (t == 7) {
        int pw = m->hd ? 420 : (55 * m->W + 50) / 100;
        if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
        u32 par = *(u32 *)((u8 *)self + 8);
        const u8 *title = NULL; u16 ts[2], tl[2]; int tn = 0;
        if (par && ig_vt && *(u32 *)par == ig_vt) title = (par && (GS(par) == 2 || GS(par) == 3)) ? mui_cur_item((void *)par) : NULL;
        if (title) { title += 24; tn = mui_wrap(m->px, title, -1, pw - 2 * (m->pad + 2 * m->s + 4), ts, tl, 2); }
        int bh = m->btnH, gp = m->gap, ph = 2 * m->pad + nb * bh + (nb - 1) * gp + 4 * m->s + (tn ? tn * m->lp + m->pad : 0) + (tn ? 2 : 0);
        int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2, in[4];
        mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
        mui_hit(0xFD, 0, 0, m->W, m->Ht, HK_CB, 0, 0);     /* chạm ngoài menu = Back (menu dọc không có nút Huỷ) */
        mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
        mui_hit(0xFE, x0, y0, x0 + pw, y0 + ph, HK_NONE, 0, 0);
        int yy = in[1] + m->pad + 2 * m->s;
        for (int i = 0; i < tn; i++) { mui_text_line((m->W) / 2, yy, title, ts[i], tl[i], m->px, C_GOLD, TF_CENTER | TF_SHADOW); yy += m->lp; }
        if (tn) yy += m->pad;
        for (int i = 0; i < nb; i++) {
            mui_r b = {in[0] + m->pad + 2 * m->s, yy + i * (bh + gp), in[2] - m->pad - 2 * m->s, 0};
            b.y1 = b.y0 + bh;
            const u8 *l = mui_hint(BTN(self, i), &n1);
            mui_button(&b, l, n1, i == cur ? 1 : 0, 0xA0 + i);
        }
        return;
    }
    const u8 *msg = MSG(self), *l1 = mui_hint(BTN(self, 0), &n1), *l2 = nb > 1 ? mui_hint(BTN(self, 1), &n2) : l1;
    if (nb < 2) n2 = 0;
    mui_popup_sel = cur == 1 ? 1 : 0;
    mui_popup(msg, mui_cslen(msg), nb >= 2, l1, n1, l2, n2, &out);
    mui_popup_sel = 0;
}

int pop_touch(void *self, int x, int y) {
    s8 *res = RES(self); int t = POP_TYPE(self), nb = POP_NBTN(self);
    int k = mui_touch_press(self, MUI_CLS_POPUP, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id == 0xF0 || id == 0xF1) { if (res && t == 2) *res = id & 1; mui_key(self, MUI_CLS_POPUP, -5); }
    else if (id == 0xF2) { if (res && t == 2) *res = 0; mui_key(self, MUI_CLS_POPUP, -5); }
    else if (id >= 0xA0 && id < 0xA0 + nb && res) { *res = id - 0xA0; mui_key(self, MUI_CLS_POPUP, -5); }
    else if (id == 0xFD) mui_key(self, MUI_CLS_POPUP, -16);
    return 0;
}
