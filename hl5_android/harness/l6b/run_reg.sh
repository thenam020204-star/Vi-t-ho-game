#!/bin/bash
# L6b: hồi quy check_mm (harness/l3) x3 cỡ x (new orig off). Dùng: run_reg.sh OUTDIR
OUT=${1:-/tmp/l6b_mm}; mkdir -p "$OUT"; cd "$(dirname "$0")/.."
for sz in "400 240" "600 270" "1200 540"; do for m in new orig off; do echo "$sz $m"; done; done | xargs -P 4 -L 1 bash -c 'timeout 2400 python3 -I l3/check_mm.py $0 $1 $2 '"$OUT"' > '"$OUT"'/log_$0x$1_$2.txt 2>&1'
echo finished > "$OUT/finished"
