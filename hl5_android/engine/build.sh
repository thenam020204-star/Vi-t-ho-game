#!/bin/sh
# Biên dịch + link engine chữ Việt tại địa chỉ cave A (Map::MapMove_back 0xaef38). Ra: OUT/vn.bin, OUT/vn.sym, OUT/vn.elf
# Dùng: build.sh [OUTDIR]     (OUTDIR mặc định: engine/out)
set -e
cd "$(dirname "$0")"
OUT=${1:-out}; mkdir -p "$OUT"
CAVE=0xaef38
clang-18 --target=armv7a-none-eabi -marm -march=armv7-a -mfloat-abi=soft -Os -ffreestanding -fPIC -fno-jump-tables \
  -fno-builtin -fno-stack-protector -fno-unwind-tables -fno-asynchronous-unwind-tables -fvisibility=hidden \
  -nostdlib -Wall -c vn.c -o "$OUT/vn.o"
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
echo "vn.bin $(wc -c < "$OUT/vn.bin") byte (cave A: 2620 B); relocs còn lại trong .o: $(llvm-objdump -r "$OUT/vn.o" | grep -c R_ARM) (sau link: $(llvm-readelf -r "$OUT/vn.elf" | grep -c R_ARM))"
