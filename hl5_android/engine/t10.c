/* T10: tuỳ chọn "Độ phân giải ÷k" trong Cài đặt (StateMainMenu Option) + chữ 18 px / khoảng dòng ở HD.
 * Code đặt ở cave B (AndroidService::AndroidService C2 0x14afb4, chết). Không GOT, không relocation (xem build.sh).
 * Giá trị k lưu trong filesDir/hd_scale.txt (1 chữ số '1'..'4') bằng API tệp của chính guest (StaticUtil::FileRead/Write ->
 * MC_fsOpen attr 1 = thư mục nội bộ = <filesDir>/hd_scale.txt); bridge đọc cùng tệp lúc khởi động (patch/mk_bridge_patches.py).
 * Hằng số: DEFAULT_K, HD_MIN_H, HD_LINE (patch/hd_cfg.py -> build.sh -D).
 */
#include "vn_state.h"

extern H int StaticUtil_FileRead(const char *name, char *buf, int len);
extern H int StaticUtil_FileWrite(const char *name, const char *buf, int len);
extern H int Graphic_GetFB(u32 gfx);
extern H int BFont_DrawText(u32 font, int fb, int x, int y, const u8 *s, int align, int color);
extern H int MX_fntGetStringHeight(int fnt);

static const char hd_name[] = "hd_scale.txt";

/* k hiện chọn (1..4); đọc hd_scale.txt một lần. Quy tắc phân tích giống bridge: ký tự đầu '1'..'4' và ký tự sau (nếu có) <= ' ' */
static int t10_k(void) {
    vn_state_t *s = vn_state;
    if (!s->k) {
        char b[4] = {0, 0, 0, 0};
        int n = StaticUtil_FileRead(hd_name, b, 2);
        int k = DEFAULT_K;
        if (n > 0 && b[0] >= '1' && b[0] <= '4' && (n < 2 || (u8)b[1] <= ' ')) k = b[0] - '0';
        s->k = s->k0 = k;
    }
    return s->k;
}

/* LEFT/RIGHT/OK trên mục "Độ phân giải": dir = +1/-1; ghi hd_scale.txt */
void t10_step(int dir) {
    int k = t10_k() + dir;
    if (k > 4) k = 1;
    if (k < 1) k = 4;
    vn_state->k = k;
    char c = '0' + k;
    StaticUtil_FileWrite(hd_name, &c, 1);
}

/* chuỗi T10 thứ i trong vi.bin (khối: u8 n; n x (u8 len, bytes)); 0 nếu thiếu */
static const u8 *t10_str(int i) {
    vn_state_t *s = vn_state;
    if (s->status <= 0 || !s->t10) return 0;
    const u8 *p = s->buf + s->t10;
    int n = *p++;
    for (int j = 0; j < n; j++) {
        if (j == i) return p;                 /* p -> u8 len, rồi byte; BFont::DrawText cần NUL: dựng bản sao ở nơi gọi */
        p += 1 + *p;
    }
    return 0;
}

/* Vẽ giá trị (ô giá trị 31x13) và, khi dòng đang chọn, ghi chú khởi động lại. Gọi từ DrawOption (loại 5 = chỉ idx 9).
 * fr = sp của DrawOption (các biến cục bộ: +0x2c this, +0x38 chỉ số GOT của BFont*, +0x48 = halfH-102); got = r4;
 * y = r5 (y dòng), idx = r7, gidx = r10 (chỉ số GOT của Graphic*), x11 = r11 (halfW-88). */
void t10_val(u32 *fr, u32 got, int y, int idx, u32 gidx, int x11) {
    if (idx != 9) return;
    u32 gfx = **(u32 **)(got + gidx);
    u32 font = **(u32 **)(got + fr[0x38 / 4]);
    int fb = Graphic_GetFB(gfx);
    u8 v[4] = {'/', '0' + t10_k(), 0, 0};
    BFont_DrawText(font, fb, x11 + 118, y, v, 1, 0xFFFFFF);
    const u8 *this_ = (const u8 *)fr[0x2c / 4];
    if ((s8)this_[15] == 9) {
        const u8 *n = t10_str(0);
        if (n) {
            u8 buf[64];
            int len = n[0];
            if (len > 62) len = 62;
            for (int i = 0; i < len; i++) buf[i] = n[1 + i];
            buf[len] = 0;
            BFont_DrawText(font, fb, x11 + 88, (int)fr[0x48 / 4] + 178, buf, 1, 0xFFFFFF);
        }
    }
}

/* onStartApp 0x9987c (ngay trước `bl BFont::BFont`): sp = sp của onStartApp; [sp+8] = đối số [69] của BFont (11);
 * [sp+0x1c] = ô GOT-giá-trị của Graphic*. Logic cao >= HD_MIN_H -> chữ 18 px + chiều cao dòng HD_LINE. */
void t10_h1(u32 *sp) {
    u32 gfx = *(u32 *)sp[0x1c / 4];
    int h = *(int *)(gfx + 0x58);
    if (h >= HD_MIN_H) {
        vn_state->hd = 1;
        sp[2] = HD_LINE;
    }
}

/* thay `bl MX_fntGetStringHeight` ở 0x414d8 (BFont::DrawText, ký tự ';' xuống dòng): HD dùng [69] thay chiều cao phông gốc */
int t10_gsh(int fnt, const u8 *bfont) {
    if (vn_state->hd) return (s8)bfont[69];
    return MX_fntGetStringHeight(fnt);
}

/* ---- thunk (vị trí vá: engine/inject_t10.py) ---- */
__asm__(
".text\n.arm\n"
/* KeyOption: bảng nhảy RIGHT/LEFT/OK trỏ tới đây; mục 9 -> t10_step, ngược lại chạy tiếp mã gốc */
".global t10_R\nt10_R:\n"
"  ldrsb r12, [r5, #15]\n  cmp r12, #9\n  bne ko_right\n  mov r0, #1\n  bl t10_step\n  b ko_tail\n"
".global t10_L\nt10_L:\n"
"  ldrsb r12, [r5, #15]\n  cmp r12, #9\n  bne ko_left\n  mvn r0, #0\n  bl t10_step\n  b ko_tail\n"
".global t10_OK\nt10_OK:\n"
"  ldrsb r3, [r5, #15]\n  cmp r3, #9\n  beq 1f\n  cmp r3, #8\n  bne ko_ret\n  b ko_ok_cont\n"
"1: mov r0, #1\n  bl t10_step\n  b ko_ret\n"
/* DrawOption: id nhãn (ldrsh) cho mục 9 = 210 ("Độ phân giải" ghi vào common_text rec 210) */
".global t10_lblu\nt10_lblu:\n  ldrsh r1, [r9]\n  cmp r7, #9\n  moveq r1, #210\n  bx lr\n"
".global t10_lbls\nt10_lbls:\n  ldrsh r1, [r1, #-28]\n  cmp r7, #9\n  moveq r1, #210\n  bx lr\n"
/* DrawOption: loại của mục 9 = 5 (ngoài bảng 0..4) */
".global t10_type\nt10_type:\n  ldrsb r3, [r3, #-12]\n  cmp r7, #9\n  moveq r3, #5\n  bx lr\n"
/* DrawOption: nhánh loại > 4 (thay `b 0x11a5d8`): vẽ giá trị rồi về cuối vòng */
".global t10_valth\nt10_valth:\n"
"  mov r0, sp\n  mov r1, r4\n  mov r2, r5\n  mov r3, r7\n  push {r10, r11}\n  bl t10_val\n  add sp, sp, #8\n  b do_loop\n"
/* onStartApp: thay `stmib sp,{r7,r12}` ngay trước ctor BFont */
".global t10_h1th\nt10_h1th:\n"
"  stmib sp, {r7, r12}\n  push {r0-r3, r12, lr}\n  add r0, sp, #24\n  bl t10_h1\n  pop {r0-r3, r12, lr}\n  bx lr\n"
/* BFont::DrawText: thay `bl MX_fntGetStringHeight` (r5 = this) */
".global t10_gshth\nt10_gshth:\n  mov r1, r5\n  b t10_gsh\n"
);
