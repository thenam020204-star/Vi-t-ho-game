/* Thư viện chạy tối thiểu (không libgcc/libc): chia nguyên (shift-subtract), memcpy/memset. Thuần asm, không reloc. */
__asm__(
".text\n.arm\n"
".global __aeabi_uidivmod\n__aeabi_uidivmod:\n"      /* r0=n r1=d -> r0=q r1=r (d=0: q=0 r=n) */
"  mov r2, r1\n  mov r1, r0\n  mov r0, #0\n  cmp r2, #0\n  bxeq lr\n  mov r3, #1\n"
"1: cmp r2, #0x80000000\n  bhs 2f\n  cmp r2, r1\n  bhs 2f\n  lsl r2, r2, #1\n  lsl r3, r3, #1\n  b 1b\n"
"2: cmp r1, r2\n  subhs r1, r1, r2\n  orrhs r0, r0, r3\n  lsrs r3, r3, #1\n  lsr r2, r2, #1\n  bne 2b\n  bx lr\n"
".global __aeabi_uidiv\n__aeabi_uidiv:\n  b __aeabi_uidivmod\n"
".global __aeabi_idivmod\n__aeabi_idivmod:\n"
"  push {r4, lr}\n  mov r4, #0\n  cmp r0, #0\n  rsblt r0, r0, #0\n  movlt r4, #3\n  cmp r1, #0\n  rsblt r1, r1, #0\n  eorlt r4, r4, #1\n"
"  bl __aeabi_uidivmod\n  tst r4, #1\n  rsbne r0, r0, #0\n  tst r4, #2\n  rsbne r1, r1, #0\n  pop {r4, pc}\n"
".global __aeabi_idiv\n__aeabi_idiv:\n  b __aeabi_idivmod\n"
".global memcpy\nmemcpy:\n"
".global __aeabi_memcpy\n__aeabi_memcpy:\n"
".global __aeabi_memcpy4\n__aeabi_memcpy4:\n"
".global __aeabi_memcpy8\n__aeabi_memcpy8:\n"
"  mov r3, r0\n1: cmp r2, #0\n  bxeq lr\n  ldrb r12, [r1], #1\n  strb r12, [r3], #1\n  sub r2, r2, #1\n  b 1b\n"
".global memset\nmemset:\n"                           /* r0=dst r1=val r2=n -> r0 */
"  mov r3, r0\n1: cmp r2, #0\n  bxeq lr\n  strb r1, [r3], #1\n  sub r2, r2, #1\n  b 1b\n"
".global __aeabi_memset\n__aeabi_memset:\n"           /* r0=dst r1=n r2=c */
".global __aeabi_memset4\n__aeabi_memset4:\n"
".global __aeabi_memset8\n__aeabi_memset8:\n"
"  mov r3, r0\n1: cmp r1, #0\n  bxeq lr\n  strb r2, [r3], #1\n  sub r1, r1, #1\n  b 1b\n"
".global __aeabi_memclr\n__aeabi_memclr:\n"
".global __aeabi_memclr4\n__aeabi_memclr4:\n"
".global __aeabi_memclr8\n__aeabi_memclr8:\n"
"  mov r3, r0\n  mov r2, #0\n1: cmp r1, #0\n  bxeq lr\n  strb r2, [r3], #1\n  sub r1, r1, #1\n  b 1b\n"
);
