/* L6b: StatePopup loại 9 = popup nhiệm vụ (QuestMgr::QuestStatusList 0xd0a68 -> ctor StatePopup; Paint gốc 0x138ca0 nhánh 9 -> QuestMgr::drawQuestInfo 0xce604). Paint + Touch mới; KeyPressed/Process GỐC.
 * Trường popup (đọc từ KeyPressed/ctor/drawQuestInfo gốc, dò động harness/l6b/q1,q2): +0x10 số nút (1..2), +0x14 con trỏ byte kết quả (-1 = đã đóng), +0x43c+48*i nhãn nút,
 *  +0xc20 u8 chỉ số bản ghi nhiệm vụ, +0xc29 u8 nút đang chọn (OK gốc: 0 = đổi chế độ xem mô tả <-> phần thưởng, 1 = đóng), +0xc2a cuộn (phím), +0xc2b u8 chế độ (3 mô tả, 4 phần thưởng).
 * Dữ liệu QuestMgr = *(gv+5580): bản ghi i (368 B) tại +1428+368*i: +0 id, +4 tên, +0xe8 chuỗi loại (Nhiệm vụ chính...), +0x16c/+0x16d chỉ số khu vực (InGameText; trạng thái 2 dùng +0x16c, khác dùng +0x16d, <0 = InGameText(136)),
 *  trạng thái u8 [+0x488+id] (2 đang làm -> icon CommonUi+6372 + InGameText(135); khác -> icon +6348 + InGameText(137)); văn bản mô tả/phần thưởng của chế độ hiện tại ở qm+28 (setQuestViewInfo, ';' xuống dòng, mã màu $ |).
 *  Chân dung: QuestMgr::DrawNpcBody(qm, x, y) (sprite gốc, neo chân giữa).
 * Chạm: nút i = ghi [+0xc29]=i rồi OK gốc (nút 1 và không phải 2 nút: chỉ OK gốc); X = KeyPressed(-16) như TouchPressed gốc; kéo hộp văn bản = cuộn (trường gốc +0xc2a không đổi). */
#include "l1ui.h"

extern H void QuestMgr_DrawNpcBody(void *qm, int x, int y);
#define QM() (*(u8 **)(gv + 5580))
#define IGT(i) TextMgr_GetInGameText(_pTextMgr, (i))
#define BTN(self, i) ((u8 *)(self) + 0x43c + 48 * (i))
#define RES(self) (*(s8 **)((u8 *)(self) + 0x14))
enum { QH_X = 0xB0, QH_BTN = 0xB2 };
static int g_lastmode = -1, g_lastq = -1;

static void parent_paint(void *self) {
    u32 par = *(u32 *)((u8 *)self + 8);
    if (!par) return;
    void (**vt)(void *) = *(void (***)(void *))par;
    vt[0x40 / 4]((void *)par);
}

void qpop_paint(void *self) {
    mui_m *m = &L; int s = m->s;
    parent_paint(self);
    if (!mui_begin()) return;
    s8 *res = RES(self); if (res && *res == -1) return;
    u8 *qm = QM(), *pu = (u8 *)self; if (!qm) return;
    int qi = pu[0xc20], mode = pu[0xc2b], sel = pu[0xc29], nb = POP_NBTN(self);
    u8 *rc = qm + 1428 + 368 * qi; int id = rc[0], st = *(u8 *)(qm + 0x488 + id);
    if (qi != g_lastq || mode != g_lastmode) { mui_sc[3].pos = 0; g_lastq = qi; g_lastmode = mode; }
    int pad = m->pad, p = pad + 2 * s, g = m->gap, lp = m->lp;
    int pw = m->W - 2 * m->mg; { int c2 = m->hd ? 640 : ((144 * m->W + 100) / 200); if (c2 < 280) c2 = 280; if (pw > c2) pw = c2; }
    int th = m->btnH, pcs = 3 * lp > 54 ? 3 * lp : 54, bh = m->btnH;
    int fixed = 2 * p + th + g + pcs + g + g + bh + 6, avail = m->Ht - 2 * m->mg - fixed, view = avail / lp, nl;
    if (view > 8) view = 8;
    if (view < 2) view = 2;
    static u8 tmp[520]; static u16 wst[48], wln[48];                /* chuỗi mô tả (qm+28) cắt ';' cuối; số dòng để thu hộp cho vừa chữ (tối thiểu 3 dòng) */
    { const u8 *d = qm + 28; int n = mui_cslen(d); while (n > 0 && d[n - 1] == ';') n--; if (n > 512) n = 512; for (int i = 0; i < n; i++) tmp[i] = d[i] == '$' ? '\'' : d[i] == '|' ? '{' : d[i]; tmp[n] = 0;   /* nền tối: mã màu đen $ -> vàng, xám | -> trắng */
      nl = mui_wrap(m->px, tmp, -1, pw - 2 * (m->bt + p) - 6 - 2 * (pad / 2 + 1) - m->sbw - 2, wst, wln, 48); }
    if (nl < 3) nl = 3;
    if (view > nl) view = nl;
    int dh = view * lp + 6, ph = fixed + dh, x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2, in[4];
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int ix0 = in[0] + p, ix1 = in[2] - p, y = in[1] + p;
    /* tiêu đề + X */
    mui_r xb = {ix1 - th, y, ix1, y + th};
    mui_clip(ix0 + th, y, ix1 - th, y + th);
    mui_text((ix0 + ix1) / 2, y + (th - m->px) / 2, rc + 4, -1, m->px, C_GOLD, TF_CENTER | TF_SHADOW);
    mui_clip_reset();
    { int in2[4]; mui_frame(xb.x0, xb.y0, xb.x1, xb.y1, m->rad, 1, m->bt, (u32)-1, 0, in2); mui_vgrad(in2[0], in2[1], in2[2], in2[3], RGB(38, 54, 78), RGB(22, 32, 48));
      int cx = (xb.x0 + xb.x1) / 2, cy = (xb.y0 + xb.y1) / 2, k = th / 5 > 2 ? th / 5 : 2, t = s > 1 ? 2 : 1;
      for (int i = -k; i <= k; i++) { mui_fill(cx + i - t / 2, cy + i - t / 2, cx + i + t, cy + i + t, C_ORANGE_HI); mui_fill(cx + i - t / 2, cy - i - t / 2, cx + i + t, cy - i + t, C_ORANGE_HI); }
      mui_hit(QH_X, xb.x0, xb.y0, xb.x1, xb.y1, HK_CB, 0, 0); }
    y += th + g;
    /* chân dung + 3 dòng thông tin */
    { mui_r pr = {ix0, y, ix0 + pcs, y + pcs}; mm_panel(pr.x0, pr.y0, pr.x1, pr.y1);
      Graphic_SetClip(_lcdG, pr.x0 + 1, pr.y0 + 1, pcs - 2, pcs - 2);
      QuestMgr_DrawNpcBody(qm, (pr.x0 + pr.x1) / 2, pr.y1 - 6);
      Graphic_ResetClip(_lcdG);
      int tx = pr.x1 + pad, ty = y + (pcs - 3 * lp) / 2; if (ty < y) ty = y;
      const u8 *gb = (const u8 *)(COMMONUI() + (st == 2 ? 6372 : 6348));
      int ih0 = *(const u16 *)(gb + 4), is = ih0 > 0 ? lp / ih0 : 1; if (is < 1) is = 1; if (is > s) is = s;
      int iw = *(const u16 *)(gb + 2) * is;
      mui_gbm(gb, tx, ty + (lp - ih0 * is) / 2, is, -1);
      mui_clip(tx, y, ix1, y + pcs);
      mui_text(tx + iw + pad, ty, IGT(st == 2 ? 135 : 137), -1, m->px, RGB(90, 226, 206), TF_SHADOW);
      int ar = I8(rc, st == 2 ? 0x16c : 0x16d); const u8 *at = st != 2 && ar < 0 ? IGT(136) : (ar >= 0 ? IGT(ar) : NULL);
      if (at) mui_text(tx, ty + lp, at, -1, m->px, C_WHITE, 0);
      mui_text(tx, ty + 2 * lp, rc + 0xe8, -1, m->px, C_GOLD, 0);
      mui_clip_reset(); }
    y += pcs + g; mui_fill(ix0, y, ix1, y + 1, C_LINE); y += g;
    /* hộp mô tả / phần thưởng cuộn (qm+28) */
    { mui_r box = {ix0, y, ix1, y + dh}; l1_panel(box.x0, box.y0, box.x1, box.y1);
      l1_desc(&box, box.y0 + 3, tmp, 3, 0); }
    y += dh + g;
    /* nút */
    { int bn1, bn2; const u8 *l1 = mui_hint(BTN(self, 0), &bn1), *l2 = nb > 1 ? mui_hint(BTN(self, 1), &bn2) : l1; if (nb < 2) bn2 = 0;
      if (nb >= 2) {
          int mid = (ix0 + ix1) / 2; mui_r b1 = {ix0, y, mid - g / 2, y + bh}, b2 = {mid + g / 2 + 1, y, ix1, y + bh};
          mui_button(&b1, l1, bn1, sel == 0 ? 1 : 0, QH_BTN); mui_button(&b2, l2, bn2, sel == 1 ? 1 : 0, QH_BTN + 1);
      } else {
          int bw = mui_sw(m->px, l1, bn1) + 4 * pad; if (bw < (ix1 - ix0) / 3) bw = (ix1 - ix0) / 3;
          mui_r b = {(ix0 + ix1 - bw) / 2, y, (ix0 + ix1 + bw) / 2, y + bh}; mui_button(&b, l1, bn1, 1, QH_BTN);
      } }
}

int qpop_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_POPUP, x, y), id = mui_last_id, nb = POP_NBTN(self);
    if (k != HK_CB) return 0;
    if (id == QH_X) mui_key(self, MUI_CLS_POPUP, -16);
    else if (id >= QH_BTN && id < QH_BTN + 2) { if (nb >= 2) *((u8 *)self + 0xc29) = id - QH_BTN; mui_key(self, MUI_CLS_POPUP, -5); }
    return 0;
}
