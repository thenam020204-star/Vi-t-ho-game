#!/bin/bash
# usage: run_all.sh script outdir
S=$1; O=$2; mkdir -p $O
for sz in "400 240" "600 270" "1200 540"; do for m in new off; do
  (WORK=~/hl5work_l1 python3 /home/user/Vi-t-ho-game/hl5_android/harness/l1/$S $sz $m $O > $O/log_${sz// /x}_$m.txt 2>&1) &
done; done; wait; echo done > $O/DONE
