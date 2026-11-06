#!/bin/bash
# T24a: hồi quy check_l6b (400x240 new), check_t21 (600x270), check_t22 (400x240) cho dev base và dev new; so kết quả (JSON + ảnh) giữa hai bản.
# Dùng: WORK=~/hl5work_t24a harness/t24a/run_reg.sh BASEDEV NEWDEV OUTDIR
BASE=$1; NEW=$2; OUT=${3:-$HOME/hl5work_t24a/reg}; export WORK=${WORK:-$HOME/hl5work_t24a}; cd "$(dirname "$0")/.."; mkdir -p "$OUT"
for v in base new; do
  D=$BASE; [ $v = new ] && D=$NEW
  mkdir -p $OUT/$v/l6b $OUT/$v/t21 $OUT/$v/t22
  ( DEVDIR=$D python3 -I l6b/check_l6b.py 400 240 new $OUT/$v/l6b > $OUT/$v/l6b.log 2>&1;
    DEVDIR=$D python3 -I l6b/check_l6b.py 400 240 new $OUT/$v/l6b game > $OUT/$v/l6b_game.log 2>&1 ) &
  ( DEVDIR=$D python3 -I t21/check_t21.py 600 270 $OUT/$v/t21 > $OUT/$v/t21.log 2>&1 ) &
  ( DEVDIR=$D python3 -I t22/check_t22.py 400 240 $OUT/$v/t22 > $OUT/$v/t22.log 2>&1 ) &
done
wait
echo finished > $OUT/finished
