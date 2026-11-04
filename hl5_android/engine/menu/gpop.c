/* L1/T17c6: popup StateGame (state 1 = hồi sinh Có/Không, 4 = hồi sinh thành công). Số đo: ui_new/menu/LAYOUT.md (game_hoi_sinh*, hit_rects.json) + mock_b2b.scr_game.
 * Kiến trúc A: gọi Paint GỐC của StateGame (cảnh) rồi vẽ popup đè; KeyPressed/Process GỐC. Trường: +0xc state, +0xe con trỏ (0 = Có, 1 = Không).
 * State 2 (Expert: kết quả thắng/thua), DrawExpert 0xda9c8 / KeyExpert 0xdbcc8 (đọc mã máy): [+0xd] sub 0 = biểu ngữ "Success" (ảnh game, GIỮ gốc), 1 = hộp 170x88
 *   (tiêu đề IGT(329) thắng nếu [+0xe]==0, IGT(330) thua; dòng IGT(326) + " " + IGT(322+chế độ) + IGT(327 thắng / 328 thua), chế độ = s8 gv[6457] 1..3), 2 = hộp 170x172 kết quả:
 *   tiêu đề IGT(337); nhãn hàng i = IGT(base[chế độ]+i), base = {1:331, 2:332, 3:334}, số hàng = chế độ; giá trị "%d": chế độ 1 {gv[6460]}, 2 {gv[6468], gv[6464]}, 3 {gv[6472], gv[6468], gv[6464]};
 *   dòng điểm cuối IGT(338) = gv[5156 + 4*(chế độ-1)] (chèn dấu phẩy hàng nghìn). Hoạt hình: Paint gốc tăng s8 [+0xf] mỗi khung (trần 40), hàng i hiện khi [+0xf] > 3+6i, điểm hiện khi > 6n+6
 *   và số thật khi [+0xf] >= 40 (trước đó chữ số xáo trộn); KeyExpert OK ở sub 2: [+0xf] <= 39 -> đặt 40, ngược lại rời màn. Paint mới giữ nguyên bộ đếm [+0xf] (không gọi Rand).
 * Chạm: Có = [+0xe]=0 + KeyPressed(-5); Không = [+0xe]=1 + KeyPressed(-5); OK (state 4) = KeyPressed(-5). Offline: hồi sinh luôn miễn phí (patch T7). */
#include "l1ui.h"

extern H void Battle_Paint(void *battle);      /* DrawGameOver/DrawOverYesNo/DrawRivivalOk gốc: Battle::Paint(*(gv+5504)) + làm tối + chữ cũ; vẽ cảnh một mình để không lộ chữ cũ */

static const u8 *strip_key(const u8 *s, int *n) {          /* bỏ gợi ý phím đầu nhãn nút: "(#)Có" / "#Có" / "1.Có" */
    int l = mui_cslen(s);
    if (s[0] == '(' && s[2] == ')') { s += 3; l -= 3; }
    else return mui_hint(s, n);
    *n = l; return s;
}
static int find(const u8 *s, u8 ch) { for (int i = 0; s[i]; i++) if (s[i] == ch) return i; return -1; }
extern H int BagItem_GetBagItemTotalBunchCount(void *bag, int cat, int id);
static int rfind(const u8 *s, u8 ch) { int r = -1; for (int i = 0; s[i]; i++) if (s[i] == ch) r = i; return r; }

static void box(const u8 *l1, int n1, const u8 *l2, int yes_no, const u8 *b1, int nb1, const u8 *b2, int nb2, int sel) {
    mui_m *m = &L; int s = m->s, in[4], pw, ph, pw1;
    u16 st[6], ln[6];
    pw = m->hd ? 480 : (70 * m->W + 50) / 100;
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    int n = mui_wrap(m->px, l1, n1, pw - 2 * (m->pad + 2 * s + 4), st, ln, 6);
    mui_popup_geom(n + (l2 ? 1 : 0), &pw1, &ph);
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int y = in[1] + m->pad + 2 * s, cx = x0 + pw / 2;
    for (int i = 0; i < n; i++) { mui_text_skip(cx, y, l1 + st[i], ln[i], m->px, C_WHITE, TF_CENTER); y += m->lp; }
    if (l2) mui_text(cx, y, l2, -1, m->px, C_GREEN, TF_CENTER);
    int by = in[3] - m->pad - m->btnH, bx0 = in[0] + m->pad + 2 * s, bx1 = in[2] - m->pad - 2 * s;
    mui_r b;
    if (yes_no) {
        int mid = (bx0 + bx1) / 2, gg = m->pad;
        b.x0 = bx0; b.y0 = by; b.x1 = mid - gg / 2; b.y1 = by + m->btnH; mui_button(&b, b1, nb1, sel == 0 ? 1 : 0, 0xF0);
        b.x0 = mid + gg / 2 + 1; b.x1 = bx1; mui_button(&b, b2, nb2, sel == 1 ? 1 : 0, 0xF1);
    } else {
        int bw = mui_sw(m->px, b1, nb1) + 4 * m->pad;
        if (bw < (bx1 - bx0) / 3) bw = (bx1 - bx0) / 3;
        b.x0 = (bx0 + bx1 - bw) / 2; b.y0 = by; b.x1 = (bx0 + bx1 + bw) / 2; b.y1 = by + m->btnH; mui_button(&b, b1, nb1, 1, 0xF2);
    }
}

void gpop_paint(void *self) {
    int st = GS(self);
    Battle_Paint(*(void **)(gv + 5560));
    if (!mui_begin()) return;
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 200);
    if (st == 1) {
        const u8 *off = CT(304); int n1, n2;
        if (off[0] == '^') off++;
        const u8 *yes = strip_key(CT(50), &n1), *no = strip_key(CT(51), &n2);
        if (BagItem_GetBagItemTotalBunchCount(HERO_GetBagItemPtr(HEROP()), 18, 23) > 0)      /* gốc DrawGameOver 0xdbbac: có Bùa Hồi Sinh (loại 18, id 23) -> CT(292+1), không có -> DrawOverText (260 + giá) */
            box(CT(293), -1, NULL, 1, yes, n1, no, n2, S8(self, 0xe) == 1);          /* popup xác nhận dùng Bùa (game_hoi_sinh_xac_nhan) */
        else box(CT(260), -1, off, 1, yes, n1, no, n2, S8(self, 0xe) == 1);
    } else {
        const u8 *t = CT(350); int k = find(t, ';'), k2 = rfind(t, ';'), n1;      /* "thông báo;;&Nhãn nút" */
        const u8 *lab = k2 >= 0 ? t + k2 + 1 : CT(56);
        if (lab[0] == '&') lab++;
        n1 = mui_cslen(lab);
        box(t, k >= 0 ? k : -1, NULL, 0, lab, n1, lab, n1, 0);
    }
}
int gpop_touch(void *self, int x, int y) {
    int k = mui_touch_press(self, MUI_CLS_GAME, x, y), id = mui_last_id;
    if (k != HK_CB) return 0;
    if (id == 0xF0 || id == 0xF1) { S8(self, 0xe) = id & 1; mui_key(self, MUI_CLS_GAME, -5); }
    else if (id == 0xF2) mui_key(self, MUI_CLS_GAME, -5);
    return 0;
}
int gpop_move(void *self, int x, int y) { return mui_touch_move(self, MUI_CLS_GAME, x, y); }

/* ---------------- state 2: kết quả Expert (sub 1 hộp thắng/thua, sub 2 bảng số liệu) ---------------- */
#define IGT(i) TextMgr_GetInGameText(_pTextMgr, (i))
static void result_box(const u8 *title, u32 tcol, const u8 *body, int nrow, const u8 **lab, const u8 **val, int *vlen, int showval) {
    mui_m *m = &L; int s = m->s, in[4], pw, ph, nl = 0, nrows;
    u16 st[6], ln[6];
    pw = m->hd ? 480 : (70 * m->W + 50) / 100;
    if (pw > m->W - 2 * m->mg) pw = m->W - 2 * m->mg;
    int ix0w = pw - 2 * (m->pad + 2 * s + 4);
    if (body) nl = mui_wrap(m->px, body, -1, ix0w, st, ln, 6);
    nrows = body ? nl : nrow;
    ph = 2 * m->pad + 4 * s + m->lp + 4 + nrows * (m->lp + 2) + m->pad + m->btnH + 2;
    mui_fill_a(0, 0, m->W, m->Ht, C_INK, 120);
    mui_hit(0xFE, 0, 0, m->W, m->Ht, HK_NONE, 0, 0);
    int x0 = (m->W - pw) / 2, y0 = (m->Ht - ph) / 2;
    mui_frame(x0, y0, x0 + pw, y0 + ph, m->rad, 1, m->bt, RGB(14, 22, 34), 248, in);
    int y = in[1] + m->pad + 2 * s, ix0 = in[0] + m->pad + 2 * s, ix1 = in[2] - m->pad - 2 * s;
    mui_text(m->W / 2, y, title, -1, m->px, tcol, TF_CENTER | TF_SHADOW); y += m->lp + 2;
    mui_fill(ix0, y - 1, ix1, y, RGB(40, 54, 76)); y += 2;
    if (body) for (int i = 0; i < nl; i++) { mui_text_skip(m->W / 2, y, body + st[i], ln[i], m->px, C_WHITE, TF_CENTER); y += m->lp + 2; }
    else for (int i = 0; i < nrow; i++) {
        if (showval >= 0 && i < showval) {
            mui_text(ix0, y, lab[i], -1, m->px, C_DIM, 0);
            if (val[i]) mui_text(ix1, y, val[i], vlen[i], m->px, i == nrow - 1 ? C_GOLD : C_WHITE, TF_RIGHT);
        }
        y += m->lp + 2;
    }
    int by = in[3] - m->pad - m->btnH, bn; const u8 *ok = mui_hint(CT(56), &bn);
    int bw = mui_sw(m->px, ok, bn) + 4 * m->pad;
    if (bw < (ix1 - ix0) / 3) bw = (ix1 - ix0) / 3;
    mui_r b = {(m->W - bw) / 2, by, (m->W + bw) / 2, by + m->btnH};
    mui_button(&b, ok, bn, 1, 0xF2);
}

void gres_paint(void *self) {
    int sub = S8(self, 0xd), mode = (s8)gv[6457];
    Battle_Paint(*(void **)(gv + 5560));
    if (!mui_begin()) return;
    mui_fill_a(0, 0, L.W, L.Ht, C_INK, 90);
    if (mode < 1 || mode > 3) mode = 1;
    if (sub == 1) {
        int win = S8(self, 0xe) == 0, n = 0; u8 buf[200];
        const u8 *a = IGT(326), *b = IGT(322 + mode), *c = IGT(win ? 327 : 328);
        for (const u8 *p = a; *p && n < 190; ) buf[n++] = *p++;
        buf[n++] = ' ';
        for (const u8 *p = b; *p && n < 190; ) buf[n++] = *p++;
        buf[n++] = ' ';
        for (const u8 *p = c; *p && n < 196; ) buf[n++] = *p++;
        buf[n] = 0;
        result_box(IGT(win ? 329 : 330), win ? C_GOLD : RGB(255, 96, 80), buf, 0, NULL, NULL, NULL, 0);
    } else {
        int cnt = S8(self, 0xf) + 1;                              /* như DrawExpert gốc: tăng bộ đếm, trần 40 */
        if (cnt > 39) cnt = 40;
        S8(self, 0xf) = cnt;
        static const int base[4] = {0, 331, 332, 334};
        static u8 vb[4][20]; const u8 *lab[4], *val[4]; int vl[4], vals[4];
        vals[0] = mode == 1 ? I32(gv, 6460) : mode == 2 ? I32(gv, 6468) : I32(gv, 6472);
        vals[1] = mode == 2 ? I32(gv, 6464) : I32(gv, 6468);
        vals[2] = I32(gv, 6464);
        for (int i = 0; i < mode; i++) { lab[i] = IGT(base[mode] + i); vl[i] = mui_itoa(vals[i], vb[i]); val[i] = vb[i]; }
        lab[mode] = IGT(338);
        int score = I32(gv, 5156 + 4 * (mode - 1));
        if (cnt >= 40) vl[mode] = mui_money(score, vb[3]);
        else {                                                    /* xáo trộn: chữ số giả xác định theo bộ đếm (không gọi Rand) */
            int k = mui_money(score, vb[3]);
            for (int i = 0; i < k; i++) if (vb[3][i] >= '0' && vb[3][i] <= '9') vb[3][i] = '0' + (cnt * 7 + i * 3) % 10;
            vl[mode] = k;
        }
        val[mode] = vb[3];
        int nvis = 0;
        for (int i = 0; i < mode; i++) if (cnt > 3 + 6 * i) nvis = i + 1;
        if (cnt > 6 * mode + 6) nvis = mode + 1;
        else if (cnt > 6 * mode + 3) nvis = mode + 1, val[mode] = NULL;
        result_box(IGT(337), C_GOLD, NULL, mode + 1, lab, val, vl, nvis);
    }
}
