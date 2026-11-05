#!/bin/bash
export WORK=$HOME/hl5work_l1
H=/home/user/Vi-t-ho-game/hl5_android/harness
for s in t17c1/check_c1_all.py t17c2/check_c2_all.py t17c3/check_c3a_all.py t17c3b/check_c3b_all.py t17c3c/check_c3c_all.py; do
  echo "=== $s $(date +%T)"; python3 -I $H/$s > $WORK/reg_$(basename $s .py).log 2>&1; echo "rc=$?"; tail -3 $WORK/reg_$(basename $s .py).log
done
echo ALLDONE
