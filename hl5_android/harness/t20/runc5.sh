#!/bin/bash
# T20-C5: mô hình thời gian interp BẬT, 4 cỡ x {base, dev} (CALL_US=0.3 us/lần memcpy host, BW 4000 MB/s) + dev bi quan (CALL_US=2). Tham số lấy từ m1.py (byte khối/4 = lệnh).
# dùng: runc5.sh W   (400|600|800|1200)  -> $WORK/out2/c5_<W>_{base,dev,devpess}.log
cd "$(dirname "$0")/../.."; : "${WORK:=$HOME/hl5work_t20}"; export WORK; O=$WORK/out2; SPD=100,200,400,800,1600
case $1 in
 400)  H=240; SEC=4; B="9.241e6 0.338e6 135e3 2989 0.01";  D="1.466e6 0.338e6 135e3 3469 0.21";;
 600)  H=270; SEC=3; B="14.832e6 0.361e6 152e3 2989 0.01"; D="1.711e6 0.361e6 152e3 3529 0.34";;
 800)  H=360; SEC=3; B="25.697e6 0.187e6 202e3 2989 0.01"; D="2.364e6 0.187e6 202e3 3063 0.59";;
 1200) H=540; SEC=3; B="56.319e6 0.162e6 304e3 2989 0.01"; D="3.833e6 0.162e6 304e3 4069 1.31";;
esac
CALL_US=0.3 python3 harness/t20/c5b.py $1 $H dev $SPD $SEC $D > $O/c5_$1_dev.log 2>&1
CALL_US=0.3 python3 harness/t20/c5b.py $1 $H base $SPD $SEC $B > $O/c5_$1_base.log 2>&1
CALL_US=2 python3 harness/t20/c5b.py $1 $H dev $SPD $SEC $D > $O/c5_$1_devpess.log 2>&1
