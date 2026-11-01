// T9a/T9b: cave "Tam nhin" (nhom `view`) cho libgame.so ARM64.
// Dat tai 0x133454..0x133488 (52 B = 13 lenh) = 52 byte dau than ham chet SGL_grpResume (0x133454, 1156 B; 0 caller, 0 reloc, khong PLT).
// Dung: scripts/asm/build_view_cave.sh (llvm-mc + ld.lld, -Ttext=0x133454).
//
// Diem vao: 0x14709c, lenh `strb w20,[x19]` trong OriginalDisplay_ConfigInfo (0x147030) bi thay bang `b cave_view`; cave tu lam lai no.
// Truoc diem nay: w8 = cfg.W, w9 = cfg.H (ca nhanh W < 480 di qua 0x147090 cung hoi tu o day). Sau cave chi con
//   strh w8,[x19,#2]; strh w9,[x19,#4]; stp s1,s0,[x19,#8]; epilogue  => w10, w11 chet; NZCV khong con duoc doc.
//
// Vung nho (trang 0x72f000):  [0x72f78c] = P (phan tram, int32). Chi nhan P trong 100..150; ngoai khoang (ke ca 0 = chua ghi) => giu nguyen cfg.W/H.
//   W' = floor(W*P/100) & ~1   (W chan: pitch FB = 2W, upload GL can 4 byte)
//   H' = floor(H*P/100)
    .text
    .globl  cave_view
cave_view:
    strb    w20, [x19]                  // lenh goc bi thay (0x14709c)
    adrp    x10, MOD_PAGE               // MOD_PAGE = 0x72f000 (ld.lld --defsym)
    ldr     w10, [x10, #0x78c]          // P
    sub     w11, w10, #100
    cmp     w11, #50
    b.hi    V_ret                       // P - 100 > 50 (khong dau) <=> P ngoai [100,150] (ke ca 0, am) -> khong doi
    mov     w11, #100
    mul     w8, w8, w10                 // W*P  (W <= 512, P <= 150 => < 2^31)
    udiv    w8, w8, w11                 // W*P/100
    and     w8, w8, #0xfffffffe         // ep W chan
    mul     w9, w9, w10                 // H*P
    udiv    w9, w9, w11                 // H*P/100
V_ret:
    b       BACK_VIEW                   // 0x1470a0 (strh w8,[x19,#2])
