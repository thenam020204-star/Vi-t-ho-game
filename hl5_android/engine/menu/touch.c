/* Cầu chạm: bảng vùng chạm đăng ký khi vẽ (Paint) -> TouchPressed: ghi con trỏ gốc rồi gọi KeyPressed(-5) gốc / kéo cuộn (TouchMoved). */
#include "mui.h"

mui_hitrec mui_hits[MUI_MAXHIT];
int mui_nhit;
mui_scroll mui_sc[MUI_NSCROLL];
int mui_last_id = -1;
int mui_drag = -1;

void mui_hits_reset(void) { mui_nhit = 0; }
void mui_hit(int id, int x0, int y0, int x1, int y1, int kind, int a, int b) {
    if (mui_nhit >= MUI_MAXHIT) return;
    mui_hitrec *h = &mui_hits[mui_nhit++];
    h->x0 = x0; h->y0 = y0; h->x1 = x1; h->y1 = y1; h->id = id; h->kind = kind; h->a = a; h->b = b;
}
int mui_hit_find(int x, int y) {
    for (int i = mui_nhit - 1; i >= 0; i--) {            /* đăng ký sau = vẽ sau = nằm trên */
        mui_hitrec *h = &mui_hits[i];
        if (x >= h->x0 && x < h->x1 && y >= h->y0 && y < h->y1) return i;
    }
    return -1;
}
int mui_touch_press(void *self, int cls, int x, int y) {
    mui_drag = -1;
    int i = mui_hit_find(x, y);
    if (i < 0) { mui_last_id = -1; return -1; }
    mui_hitrec *h = &mui_hits[i];
    mui_last_id = h->id;
    switch (h->kind) {
    case HK_OK: *((s8 *)self + h->a) = (s8)h->b; mui_key(self, cls, -5); break;
    case HK_SET: *((s8 *)self + h->a) = (s8)h->b; break;
    case HK_KEY: mui_key(self, cls, h->a); break;
    case HK_DRAG:
        if (h->a >= 0 && h->a < MUI_NSCROLL) { mui_drag = h->a; mui_sc[h->a].start_pos = mui_sc[h->a].pos; mui_sc[h->a].start_y = y; mui_sc[h->a].step = h->b > 0 ? h->b : 1; }
        break;
    default: break;
    }
    return h->kind;
}
int mui_touch_move(void *self, int cls, int x, int y) {
    (void)self; (void)cls; (void)x;
    if (mui_drag < 0) return 0;
    mui_scroll *s = &mui_sc[mui_drag];
    int d = (s->start_y - y) / (s->step > 0 ? s->step : 1), mx = s->total - s->view, p = s->start_pos + d;
    if (mx < 0) mx = 0;
    s->pos = p < 0 ? 0 : p > mx ? mx : p;
    return 1;
}
int mui_touch_release(void) { int d = mui_drag; mui_drag = -1; return d; }
