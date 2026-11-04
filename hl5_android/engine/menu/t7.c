/* L1/T7: logic offline (không gọi NET/MTX). Điểm móc = HOOK_BL trong hooks.def (thay lời gọi netConnect/SetPopupState tại call-site; PLAN T7 mục 2, 3).
 * Cash shop: Process 0xd9218 sau xác nhận "Có" (popup 2) gọi netConnect(3) -> server trả vật phẩm (ProcPacket_Shop) -> NewItemToBag.
 *   Offline: hook tại 0xd9330 (netConnect(3)) cấp vật phẩm ngay: ItemTable::NewItemToBag(loại 18, id = rec[+8], số lượng = rec[+9] hoặc 1, cờ 1),
 *   hook tại 0xd9350 (SetPopupState(0) đóng popup) -> mở popup "Item purchased." (CT 93) nếu vừa mua.
 * Hồi sinh: StateGame::Process 0xdc188 (netConnect(5) sau xác nhận mua hồi sinh) -> HERO::Revival() + StateGame::SetState(4).
 *   (KeyGameOver 0xdbf40: `bgt` -> `b 0xdbf88`: không cần Bùa hồi sinh, không trừ, vá byte trong so_patches.tsv khối T7.) */
#include "mui.h"

extern H int ItemTable_NewItemToBag(void *tbl, int cat, int id, int cnt, int flag);
extern H void StateCashShop_SetPopupState(void *self, int type, const u8 *text, int a, int b, int c);
extern H void HERO_Revival(void *hero);
extern H void StateGame_SetState(void *self, int s);
extern H void NETWORK_netConnect(void *net, int mode);

const u8 t7_free[] = {0xCA, 0xAB, ' ', 0xCE, 0xB0, 0};   /* "Miễn phí": từ điển idx 104 ("Miễn"), 485 ("phí") */
int t7_bought;                         /* 1 = vừa cấp vật phẩm cash, popup đóng kế tiếp thành "đã nhận" */
int t7_rev;                            /* đếm số lần hồi sinh offline (harness) */

static void *top_state(void) { return *(void **)(gv + 0x244); }

void t7_cash_netconnect(void *net, int mode) {
    if (mode != 3) { NETWORK_netConnect(net, mode); return; }
    u8 *self = top_state();
    u8 *recs = *(u8 **)(*(u8 **)(self + 0x14) + 4);
    u8 *rec = recs + 24 * (*(s8 *)(self + 15));
    int cnt = rec[9]; if (cnt <= 0) cnt = 1;
    ItemTable_NewItemToBag(ITEMTABLE(), 18, (s8)rec[8], cnt, 1);
    t7_bought = 1;
}
void t7_cash_popup(void *self, int type, const u8 *text, int a, int b, int c) {
    if (type == 0 && t7_bought) { t7_bought = 0; StateCashShop_SetPopupState(self, 1, CT(93), 0, 0, 9); return; }
    StateCashShop_SetPopupState(self, type, text, a, b, c);
}
void t7_game_revive(void *net, int mode) {
    if (mode != 5) { NETWORK_netConnect(net, mode); return; }
    HERO_Revival(HEROP()); t7_rev++;
    StateGame_SetState(top_state(), 4);
}
/* 0xd9a24: StateCashShop::KeyPressed -> SetPopupState(2, text): thay đoạn "Giá gốc ... won (Có tính cước gọi)" của bản mạng bằng "Bạn có muốn mua?;Miễn phí" */
void t7_cash_confirm(void *self, int type, const u8 *text, int a, int b, int c) {
    static u8 buf[96];
    if (type == 2) {
        int n = 0; const u8 *q = CT(91);
        while (q[n] && n < 60) { buf[n] = q[n]; n++; }
        buf[n++] = ';';
        for (int i = 0; t7_free[i]; i++) buf[n++] = t7_free[i];
        buf[n] = 0; text = buf;
    }
    StateCashShop_SetPopupState(self, type, text, a, b, c);
}
