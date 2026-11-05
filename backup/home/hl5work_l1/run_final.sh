#!/bin/bash
export WORK=$HOME/hl5work_l1
H=/home/user/Vi-t-ho-game/hl5_android/harness/l1
R=$WORK/final; rm -rf $R; mkdir -p $R
bash $WORK/run_all.sh check_q.py $R/q; echo q done > $R/q.done
bash $WORK/run_all.sh check_sp.py $R/sp; echo sp done > $R/sp.done
bash $WORK/run_all.sh check_cash.py $R/cash; echo cash done > $R/cash.done
mkdir -p $R/gpop
for sz in "400 240" "600 270" "1200 540"; do for m in new off; do
  (python3 $H/check_gpop.py $sz $m $R/gpop > $R/gpop/log_${sz// /x}_$m.txt 2>&1) &
done; done; wait; echo gpop done > $R/gpop.done
mkdir -p $R/t7
for sz in "400 240" "600 270" "1200 540"; do (python3 $H/check_t7.py $sz $R/t7 > $R/t7/log_${sz// /x}.txt 2>&1) & done; wait
echo ALL > $R/DONE
