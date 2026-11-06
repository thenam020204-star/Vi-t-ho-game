#!/bin/bash
# L6c: chạy check_l6c cho 3 cỡ x (new orig off), song song. Dùng: run.sh OUTDIR PART
OUT=${1:-/tmp/l6c_chk}; PART=${2:-quest}; mkdir -p "$OUT"; cd "$(dirname "$0")/.."
for sz in "400 240" "600 270" "1200 540"; do for m in new orig off; do echo "$sz $m"; done; done | \
  xargs -P 4 -L 1 bash -c 'timeout 1500 python3 -I l6c/check_l6c.py $0 $1 $2 '"$OUT"' '"$PART"' > '"$OUT"'/log_$0x$1_$2_'"$PART"'.txt 2>&1'
echo finished > "$OUT/finished_$PART"
