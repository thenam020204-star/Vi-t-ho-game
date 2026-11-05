#!/bin/bash
# chạy riêng phần game (popup nhiệm vụ). Dùng: run_game.sh OUTDIR
OUT=${1:-/tmp/l6b_chk2}; mkdir -p "$OUT"; cd "$(dirname "$0")/.."
for sz in "400 240" "600 270" "1200 540"; do for m in new orig off; do echo "$sz $m game"; done; done | xargs -P 4 -L 1 bash -c 'timeout 1500 python3 -I l6b/check_l6b.py $0 $1 $2 '"$OUT"' $3 > '"$OUT"'/log_$0x$1_$2_$3.txt 2>&1'
echo finished > "$OUT/finished"
