#!/usr/bin/env bash
# Chạy run_emu ở nền: emu_bg.sh OUTDIR "steps sau tiền tố vào game" ; chờ xong rồi in kết quả.
# Tiền tố: boot -> bỏ nhạc -> mới -> bỏ thoại mở đầu -> bỏ dẫn truyện (w) -> vào bản đồ.
R="$(cd "$(dirname "$0")/.." && pwd)"; cd "$R"; source work/tools/env.sh
JAR="${JAR:-work/build/MHTQ_K790_moc2.jar}"
P="wait 16; key w; wait 14; key Return; wait 5"
for i in $(seq 1 17); do P="$P; key Return; wait 2"; done
P="$P; wait 12; key w; wait 6"
python3 -I scripts/run_emu.py --fj "$FJ" --jar "$JAR" --out "$1" --size 240x320 --steps "$P; $2" > "$1.json" 2>&1
