// T8a: cave cua mod menu (Mdmg/Mdef/Mexp) cho libgame.so ARM64.
// Dat tai 0x18bb00..0x18bbec (236 B = 59 lenh) = than 2 ham chet CHAR_AddDamageEffect (0x18bb00) + CHAR_AddDamageByDefaultCheck (0x18bb34).
// Dung: scripts/asm/build_mod_cave.sh  (llvm-mc + ld.lld, -Ttext=0x18bb00) -> /opt/work/t8/mod_cave.bin
//
// Vung nho (trang 0x72f000, doi .bss):  [0x72f780]=Mdmg  [0x72f784]=Mdef  [0x72f788]=Mexp   ([0x72f78c]: danh cho T9b, T8 khong ghi)
// Chi nhan he so 2..50; gia tri khac (ke ca 0) = 1 = nhu game goc.
// Thanh ghi dung: x9..x13 (tam, caller-saved; da chung minh tu do tai tung diem vao trong docs/mod_menu.md).
// Khong ghi bo nho nao ngoai thanh ghi; khong dung stack; co the lam hong co NZCV (khong ai doc co truoc lenh cmp ke tiep).
    .text
    .globl  cave_A, cave_B, cave_C

// ============================================================ A. CHAR_AddDamage(x0=ke tan cong|NULL, x1=muc tieu, w2=sat thuong int32, w3, w4)
// diem vao: 0x18b3e0  (lenh goc `sub sp,sp,#0x70` bi thay bang `b cave_A`; cave tu lam lai no)
cave_A:
    sub     sp, sp, #0x70               // lenh goc bi thay (giu nguyen y het, sp thay doi dung 1 lan)
    cmp     w2, #1
    b.lt    A_ret                       // dmg < 1: ham goc se thoat (cmp w2,#1 / b.lt o 0x18b404); khong doc [x1] (giu nguyen thu tu cua ham goc)
    ldrb    w9, [x1, #0x311]            // phe cua MUC TIEU (0 = nguoi choi/dong minh, 1 = quai, 2 = NPC); ham goc cung doc [x1,#0x2c9] khong kiem NULL
    adrp    x10, MOD_PAGE               // MOD_PAGE = 0x72f000: trang chua 3 he so (ld.lld --defsym)
    cbnz    w9, A_atk                   // muc tieu khong phai phe 0 -> xet ke tan cong
    // ---- muc tieu phe 0 (phe nguoi choi NHAN sat thuong): chia Mdef, toi thieu 1
    ldr     w11, [x10, #0x784]          // Mdef
    sub     w12, w11, #2
    cmp     w12, #48
    b.hi    A_ret                       // Mdef ngoai 2..50 (ke ca 0, 1, am, > 50) -> khong doi
    udiv    w12, w2, w11                // w12 = floor(dmg / Mdef)   (Mdef >= 2, khong chia 0)
    cmp     w12, #0
    csinc   w2, w12, wzr, ne            // w2 = (w12 != 0) ? w12 : 1     -> toi thieu 1
    b       A_ret
A_atk:
    cbz     x0, A_ret                   // khong co ke tan cong (DoT/script) -> khong nhan
    ldrb    w9, [x0, #0x311]            // phe cua KE TAN CONG
    cbnz    w9, A_ret                   // khong phai phe 0 -> khong nhan
    ldr     w11, [x10, #0x780]          // Mdmg
    sub     w12, w11, #2
    cmp     w12, #48
    b.hi    A_ret                       // Mdmg ngoai 2..50 -> khong doi
    mov     w13, #0x423f
    movk    w13, #0xf, lsl #16          // w13 = 999999 (tran sat thuong)
    cmp     w2, w13
    b.ge    A_ret                       // sat thuong goc da >= tran: giu nguyen (mod khong bao gio lam yeu hon game goc)
    mul     w12, w2, w11                // dmg < 999999, Mdmg <= 50  =>  tich <= 49.999.900 < 2^31: khong tran
    cmp     w12, w13
    csel    w2, w12, w13, lt            // w2 = min(tich, 999999)
A_ret:
    b       BACK_A                      // BACK_A = 0x18b3e4: quay lai lenh thu 2 cua ham goc (stp x29,x30,[sp,#0x20])

// ============================================================ B. MERCENARYSYSTEM_AddExperience, w19 = EXP co so (da tinh xong), 0x1c7860 `mov w8,#0x6e`
cave_B:
    mov     w8, #0x6e                   // lenh goc bi thay
    adrp    x9, MOD_PAGE                // 0x72f000
    ldr     w10, [x9, #0x788]           // Mexp
    sub     w11, w10, #2
    cmp     w11, #48
    b.hi    B_ret                       // ngoai 2..50 -> khong doi
    mov     w12, #0xffff
    movk    w12, #0xff, lsl #16         // w12 = 0xFFFFFF (tran EXP; w19*110 @0x1c7868 con < 2^31)
    cmp     w19, w12
    b.ge    B_ret                       // EXP goc da >= tran: giu nguyen
    mul     w11, w19, w10               // w19 < 0xFFFFFF, Mexp <= 50 => tich < 838.860.750: khong tran
    cmp     w11, w12
    csel    w19, w11, w12, lt           // w19 = min(tich, 0xFFFFFF)  (tiep theo ham goc tinh w25 = w19*110/100 tu w19 moi)
B_ret:
    b       BACK_B                      // BACK_B = 0x1c7864 (lenh ke tiep cua ham goc)

// ============================================================ C. MERCENARYSYSTEM_AddExpFixed(w0 = EXP co dinh), 0x1c7a44 `mov w19,w0`; x8 = adrp dang SONG -> chi dung x9..x12
cave_C:
    mov     w19, w0                     // lenh goc bi thay
    cmp     w19, #1
    b.lt    C_ret                       // EXP <= 0: khong doi
    adrp    x9, MOD_PAGE                // 0x72f000
    ldr     w10, [x9, #0x788]           // Mexp
    sub     w11, w10, #2
    cmp     w11, #48
    b.hi    C_ret
    mov     w12, #0xffff
    movk    w12, #0xff, lsl #16         // 0xFFFFFF
    cmp     w19, w12
    b.ge    C_ret
    mul     w11, w19, w10
    cmp     w11, w12
    csel    w19, w11, w12, lt
C_ret:
    b       BACK_C                      // BACK_C = 0x1c7a48
