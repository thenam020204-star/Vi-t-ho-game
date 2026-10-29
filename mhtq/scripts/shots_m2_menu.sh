#!/usr/bin/env bash
# Chụp trang trạng thái/túi đồ/kỹ năng/nhiệm vụ: shots_m2_menu.sh JAR OUTDIR (mode: items|tabs)
R="$(cd "$(dirname "$0")/.." && pwd)"; cd "$R"; source work/tools/env.sh
JAR="$1"; OUT="$2"; MODE="$3"
S="wait 16; key w; wait 14; key Return; wait 5"
for i in $(seq 1 17); do S="$S; key Return; wait 2"; done
S="$S; wait 12; key w; wait 6"
for i in $(seq 1 40); do S="$S; key Return; wait 1.8"; done
S="$S; key w; wait 2; shot status"
if [ "$MODE" = items ]; then
  S="$S; key Right; wait 1.5; key Down; wait 1; key Return; wait 1.5; shot item_a; wait 1.5; shot item_b; wait 1.5; shot item_c; key Right; wait 1; shot item_d"
else
  S="$S; key Right 3; wait 2; shot skill; key Down; wait 1.5; shot skill_down; key Right; wait 1.5; shot map; key Right; wait 1.5; shot quest"
fi
python3 -I scripts/run_emu.py --fj "$FJ" --jar "$JAR" --out "$OUT" --size 240x320 --steps "$S" > "$OUT.json" 2>&1
