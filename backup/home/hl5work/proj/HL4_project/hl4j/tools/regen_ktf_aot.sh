#!/bin/bash
# Regenerate src/aot/hl4/aot/KtfAot.java from the stored KTF profiles.
# Loop regions (hot loops, up to 1000 insns, cold paths cut) + function regions (hot non-loop code,
# exits at BL/BX/POP pc). Addresses patched at run time by the HLE text engine are excluded.
cd "$(dirname "$0")/.."
P=profiles
python3 tools/aotgen.py --bin ../vi/build/ktf/client.bin387872 --base 0x100000 --class KtfAot \
  --out src/aot/hl4/aot/KtfAot.java --maxinsns 1000 --coldcut 0.001 --funcregions --funcmin 50000 --maxregions 3000 \
  --exclude 133f74-133f7c --exclude 13495c-134964 --exclude 134840-134848 --exclude 12277c-122780 --exclude 108500-108508 \
  $P/ktf_gameplay.txt@0x100000 $P/ktf_walk.txt@0x100000 $P/ktf_menus_residual.txt@0x100000 \
  $P/ktf_shop_des.txt@0x100000 $P/ktf_old1.txt@0x100000 $P/ktf_old2.txt@0x100000
