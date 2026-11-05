#!/bin/bash
# L1b: chạy riêng kịch bản cycles (có so ảnh mặt nạ) của check_q/sp/cash/gpop, 3 cỡ x new/orig. Dùng: WORK=~/hl5work_l1b run_cycles.sh OUTDIR [PAR]
HERE="$(cd "$(dirname "$0")" && pwd)"; OUT="${1:-$HERE/../../shots/L1b/cyc}"; P="${2:-2}"; mkdir -p "$OUT"
for ck in q sp cash gpop; do for sz in "400 240" "600 270" "1200 540"; do for m in new $([ $ck = gpop ] && echo off || echo orig); do
  echo "SC=cycles python3 -I $HERE/check_$ck.py $sz $m $OUT/$ck"; done; done; done | xargs -P "$P" -I{} bash -c "{}" >"$OUT/run.log" 2>&1
for ck in q sp cash gpop; do python3 -I "$HERE/compare_$ck.py" "$OUT/$ck" > "$OUT/check_$ck.txt" 2>&1; done
echo DONE >> "$OUT/run.log"
