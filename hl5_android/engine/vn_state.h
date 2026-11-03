/* Kiểu dùng chung cho vn.c (engine chữ) và t10.c (menu ÷k + HD). Trạng thái ở bss guest 0x1704c0 (64 B, p_memsz +0x100). */
typedef unsigned char u8;
typedef signed char s8;
typedef unsigned short u16;
typedef unsigned int u32;

#define H __attribute__((visibility("hidden")))
typedef struct {
    int status;                       /* 0 chưa nạp, 1 sẵn sàng, -1 lỗi/không có vi.bin */
    const u8 *buf;
    int nwords, nch;                  /* nch: số ký tự đã căn 4 */
    const u16 *widx;
    const u8 *words;
    const u8 *font[3];
    int hd;                           /* 1: dùng bộ 18 px (T10: onStartApp đặt khi logic cao >= HD_MIN_H) */
    int k, k0;                        /* T10: ÷k chọn trong Cài đặt (0 = chưa đọc hd_scale.txt), k0 = giá trị lúc đọc đầu */
    u32 t10;                          /* offset khối chuỗi T10 trong vi.bin (0 = không có) */
    int force0;                       /* T17c0: 1 = bỏ qua mode nén/10 px baked trong từ (menu mới vẽ chữ mode 0); mặc định 0 */
} vn_state_t;                         /* 60 B (bss 0x1704c0..0x170500 = 64 B) */
extern H vn_state_t vn_state[];
