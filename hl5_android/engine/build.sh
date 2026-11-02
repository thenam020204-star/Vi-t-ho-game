#!/bin/sh
# Biên dịch + link (1) engine chữ Việt tại cave A (Map::MapMove_back 0xaef38) -> OUT/vn.bin|vn.sym|vn.elf
#                  (2) T10 (menu ÷k + HD) tại cave B (AndroidService::AndroidService C2 0x14afb4) -> OUT/t10.bin|t10.sym|t10.elf
# Dùng: build.sh [OUTDIR]     (OUTDIR mặc định: engine/out). Hằng số HD lấy từ patch/hd_cfg.py.
set -e
cd "$(dirname "$0")"
OUT=${1:-out}; mkdir -p "$OUT"
CAVE=0xaef38
CAVE_B=0x14afb4
eval "$(python3 -c "
import sys; sys.path.insert(0, '../patch'); import hd_cfg as c
print('DK=%d HMIN=%d HLINE=%d' % (c.DEFAULT_K, c.HD_MIN_H, c.HD_LINE))")"
CC="clang-18 --target=armv7a-none-eabi -marm -march=armv7-a -mfloat-abi=soft -Os -ffreestanding -fPIC -fno-jump-tables \
  -fno-builtin -fno-stack-protector -fno-unwind-tables -fno-asynchronous-unwind-tables -fvisibility=hidden \
  -nostdlib -Wall -DDEFAULT_K=$DK -DHD_MIN_H=$HMIN -DHD_LINE=$HLINE"
$CC -c vn.c -o "$OUT/vn.o"
$CC -c t10.c -o "$OUT/t10.o"
cat > "$OUT/vn.ld" <<L
ENTRY(vn_th0)
SECTIONS {
  . = $CAVE;
  .text : { *(.text) *(.text.*) *(.rodata) *(.rodata.*) }
  /DISCARD/ : { *(.ARM.exidx*) *(.ARM.extab*) *(.comment) *(.note*) *(.ARM.attributes) }
}
L
ld.lld -m armelf -static -T "$OUT/vn.ld" --no-rosegment \
  --defsym=MX_fntDrawString=0x3cf00 --defsym=_midas_funcFntDrawPixel=0x376e4 --defsym=StaticUtil_LoadRes=0x1417a4 \
  --defsym=vn_got_mxfnt=0x15eb54 --defsym=vn_state=0x1704c0 \
  "$OUT/vn.o" -o "$OUT/vn.elf"
llvm-objcopy -O binary -j .text "$OUT/vn.elf" "$OUT/vn.bin"
llvm-nm "$OUT/vn.elf" | grep -E ' [tTrR] ' > "$OUT/vn.sym"
echo "vn.bin $(wc -c < "$OUT/vn.bin") byte (cave A: 2620 B); relocs trong .o: $(llvm-objdump -r "$OUT/vn.o" | grep -c R_ARM) (sau link: $(llvm-readelf -r "$OUT/vn.elf" | grep -c R_ARM))"
sed "s/vn_th0/t10_R/; s/$CAVE/$CAVE_B/" "$OUT/vn.ld" > "$OUT/t10.ld"
ld.lld -m armelf -static -T "$OUT/t10.ld" --no-rosegment \
  --defsym=StaticUtil_FileRead=0x1416e0 --defsym=StaticUtil_FileWrite=0x141620 --defsym=Graphic_GetFB=0x4e4f8 \
  --defsym=BFont_DrawText=0x41d78 --defsym=MX_fntGetStringHeight=0x31af4 --defsym=vn_state=0x1704c0 \
  --defsym=ko_right=0x11edb8 --defsym=ko_left=0x11ee0c --defsym=ko_tail=0x11ec1c --defsym=ko_ret=0x11ec84 \
  --defsym=ko_ok_cont=0x11ed74 --defsym=do_loop=0x11a5d8 \
  "$OUT/t10.o" -o "$OUT/t10.elf"
llvm-objcopy -O binary -j .text "$OUT/t10.elf" "$OUT/t10.bin"
llvm-nm "$OUT/t10.elf" | grep -E ' [tTrR] ' > "$OUT/t10.sym"
echo "t10.bin $(wc -c < "$OUT/t10.bin") byte (cave B: 2436 B); relocs trong .o: $(llvm-objdump -r "$OUT/t10.o" | grep -c R_ARM) (sau link: $(llvm-readelf -r "$OUT/t10.elf" | grep -c R_ARM))"
