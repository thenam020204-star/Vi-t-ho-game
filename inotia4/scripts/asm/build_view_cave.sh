#!/usr/bin/env bash
# Dung cave "tam nhin" (scripts/asm/view_cave.s) bang llvm-mc + ld.lld tai dung dia chi 0x133454.
#   bash scripts/asm/build_view_cave.sh [thu_muc_ra=/opt/work/t9]
# Ra: view_cave.bin (13 lenh = 52 B), view_cave.o, view_cave.elf, view_cave.dis (llvm-objdump).
set -euo pipefail
cd "$(dirname "$0")"
OUT=${1:-/opt/work/t9}; mkdir -p "$OUT"
llvm-mc -triple=aarch64-linux-gnu -mattr=+v8a -filetype=obj -o "$OUT/view_cave.o" view_cave.s
ld.lld -Ttext=0x133454 --image-base=0 --no-rosegment -z norelro -e cave_view \
  --defsym=MOD_PAGE=0x72f000 --defsym=BACK_VIEW=0x1470a0 \
  -o "$OUT/view_cave.elf" "$OUT/view_cave.o"
llvm-objcopy -O binary -j .text "$OUT/view_cave.elf" "$OUT/view_cave.bin"
llvm-objdump -d --no-show-raw-insn "$OUT/view_cave.elf" > "$OUT/view_cave.dis"
python3 - "$OUT/view_cave.bin" <<'PY'
import sys, hashlib
b = open(sys.argv[1], 'rb').read()
print('view_cave.bin: %d B (%d lenh)  sha256 %s' % (len(b), len(b) // 4, hashlib.sha256(b).hexdigest()))
assert len(b) == 52, 'cave phai dung 52 B (0x133454..0x133488)'
PY
