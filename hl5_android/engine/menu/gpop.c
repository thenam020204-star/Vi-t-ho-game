/* L1/T17c6: popup StateGame (state 1 = hồi sinh Có/Không, 4 = hồi sinh thành công). Số đo: ui_new/menu/LAYOUT.md (game_hoi_sinh*, hit_rects.json) + mock_b2b.scr_game.
 * Kiến trúc A: gọi Paint GỐC của StateGame (cảnh) rồi vẽ popup đè; KeyPressed/Process GỐC. Trường: +0xc state, +0xe con trỏ (0 = Có, 1 = Không).
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
        box(CT(260), -1, off, 1, yes, n1, no, n2, S8(self, 0xe) == 1);
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
