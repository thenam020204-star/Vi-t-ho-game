/* layout(W,H): công thức co giãn theo ui_new/menu/LAYOUT.md §1 (nguồn số đo duy nhất; ví dụ 400x240 -> cửa sổ 384x224). */
#include "mui.h"

void mui_layout(mui_m *m, int W, int Ht) {
    int s = Ht / 180;
    if (s < 1) s = 1;
    if (s > 3) s = 3;
    m->W = W; m->Ht = Ht; m->s = s;
    m->hd = Ht >= 480;
    m->px = m->hd ? 18 : 15;
    m->spx = m->hd ? 15 : 10;
    m->lp = m->px + 2; m->slp = m->spx + 2;
    m->mg = 8 * s;
    m->winH = Ht - 2 * m->mg;
    int ww = (172 * m->winH + 50) / 100;                   /* round(1.72*winH) */
    m->winW = W - 2 * m->mg < ww ? W - 2 * m->mg : ww;
    m->wx = (W - m->winW) / 2; m->wy = (Ht - m->winH) / 2;
    m->bt = s;
    m->pad = 3 * s + 3;
    m->gap = 2 * s + 2;
    m->titleH = 6 * s + 14;
    m->tabH = 12 * s + 12;
    m->kb = (m->titleH - 4) / 13;
    if (m->kb < 1) m->kb = 1;
    m->btnH = m->lp + 6 * s;
    m->cell = 20 * s + 10; m->cg = 2 * s; m->pitch = m->cell + m->cg;
    m->gridW = 4 * m->pitch - m->cg;
    m->barH = m->spx + 3;
    m->rowH = m->lp + 4 * s;
    m->sbw = 3 * s + 1;
    m->rad = 2 * s + 1;
}
