#!/usr/bin/env bash
# Dung cave mod (scripts/asm/mod_cave.s) bang llvm-mc + ld.lld tai dung dia chi 0x18bb00.
#   bash scripts/asm/build_mod_cave.sh [thu_muc_ra=/opt/work/t8]
# Ra: mod_cave.bin (59 lenh = 236 B), mod_cave.o, mod_cave.elf, mod_cave.dis (llvm-objdump).
set -euo pipefail
cd "$(dirname "$0")"
OUT=${1:-/opt/work/t8}; mkdir -p "$OUT"
llvm-mc -triple=aarch64-linux-gnu -mattr=+v8a -filetype=obj -o "$OUT/mod_cave.o" mod_cave.s
ld.lld -Ttext=0x18bb00 --image-base=0 --no-rosegment -z norelro -e cave_A \
  --defsym=MOD_PAGE=0x72f000 --defsym=BACK_A=0x18b3e4 --defsym=BACK_B=0x1c7864 --defsym=BACK_C=0x1c7a48 \
  -o "$OUT/mod_cave.elf" "$OUT/mod_cave.o"
llvm-objcopy -O binary -j .text "$OUT/mod_cave.elf" "$OUT/mod_cave.bin"
llvm-objdump -d --no-show-raw-insn "$OUT/mod_cave.elf" > "$OUT/mod_cave.dis"
python3 - "$OUT/mod_cave.bin" <<'PY'
import sys, hashlib
b = open(sys.argv[1], 'rb').read()
print('mod_cave.bin: %d B (%d lenh)  sha256 %s' % (len(b), len(b) // 4, hashlib.sha256(b).hexdigest()))
assert len(b) == 236, 'cave phai dung 236 B (0x18bb00..0x18bbec)'
PY
