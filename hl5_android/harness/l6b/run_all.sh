#!/bin/bash
# L6b: chạy check_l6b cho 3 cỡ x (new orig off) x (menu game), song song theo cỡ. Dùng: run_all.sh OUTDIR
OUT=${1:-/tmp/l6b_chk}; mkdir -p "$OUT"; cd "$(dirname "$0")/.."
for sz in "400 240" "600 270" "1200 540"; do for m in new orig off; do for part in menu game; do
  echo "$sz $m $part"
done; done; done | xargs -P 4 -L 1 bash -c 'timeout 1500 python3 -I l6b/check_l6b.py $0 $1 $2 '"$OUT"' $3 > '"$OUT"'/log_$0x$1_$2_$3.txt 2>&1'
echo finished > "$OUT/finished"
