#!/usr/bin/env bash
# T9c: kiem tam nhin / HUD / pool bang unidbg (khong can toi man choi).
#   bash emu/view_check.sh <libgame.so goc> <libgame.so da va du nhom> [thu_muc_ra=work/viewcheck] [--title]
# Moi lan chay: Inotia4Emu voi -Demu.w/-Demu.h, -Demu.poke=0x72f78c=P (gia lap Java ghi P trong onResume truoc startClet),
# -Demu.dump (GRP_nDisplayW/H, man logic), -Demu.pool=true (MEM_GetUsedPoolSize/TotalPoolSize), -Demu.hudcall=-1 (goi truc tiep UIPlay_Create).
# Roi scripts/re/view_xcheck.py doi chieu ket qua voi cong thuc; dong cuoi phai la `TONG KET: TAT CA DAT`.
# --title: them 3 lan chay dai (~2-3 phut) chup man tieu de + tap START (khung 620) o lib goc, P = 100, P = 120 va so anh.
set -uo pipefail
cd "$(dirname "$0")/.."
ARGS=(); TITLE=0
for a in "$@"; do if [ "$a" = "--title" ]; then TITLE=1; else ARGS+=("$a"); fi; done
[ ${#ARGS[@]} -ge 2 ] || { echo "dung: bash emu/view_check.sh <lib goc> <lib da va> [thu_muc_ra] [--title]"; exit 2; }
ORIG=$(realpath "${ARGS[0]}"); NEW=$(realpath "${ARGS[1]}"); OUTD=$(realpath -m "${ARGS[2]:-work/viewcheck}"); rm -rf "$OUTD"; mkdir -p "$OUTD/runs"
APK=${APK:-$(realpath test_builds/Inotia4_TEST4_vi_anh_offline.apk)}
RESXML=${RESXML:-/opt/work/dec4/res/xml}
JOBS="$OUTD/jobs.txt"; : > "$JOBS"
job() {   # name lib frames script opts...
  local n=$1 l=$2 f=$3 sc=$4; shift 4
  echo "mkdir -p $OUTD/runs/$n; LIB=$l APK=$APK OUT=$OUTD/runs/$n EMU_OPTS='-Demu.shot=0 -Demu.resxml=$RESXML -Demu.script=$sc $*' timeout 590 bash emu/run.sh $f > $OUTD/runs/$n.log 2>&1" >> "$JOBS"
}
DUMP="-Demu.dump=0x2d6090:4,0x2d7ab4:2,0x72f780:4 -Demu.pool=true"
job base_orig "$ORIG" 120 "" -Demu.w=2400 -Demu.h=1080 $DUMP
for P in unset 100 105 110 115 120 125 130 135 140 145 150 99 151 0 -1; do
  PK=""; [ $P != unset ] && PK="-Demu.poke=0x72f78c=$P"
  job p_2400x1080_$P "$NEW" 120 "" -Demu.w=2400 -Demu.h=1080 $DUMP $PK
done
for SZ in 960x640 1920x1080 800x480 799x480 600x360 479x320; do
  for P in unset 100 105 120 150; do
    PK=""; [ $P != unset ] && PK="-Demu.poke=0x72f78c=$P"
    job p_${SZ}_$P "$NEW" 120 "" -Demu.w=${SZ%x*} -Demu.h=${SZ#*x} $DUMP $PK
  done
done
for SZ in 2400x1080 960x640; do
  job hud_orig_$SZ "$ORIG" 3 "" -Demu.w=${SZ%x*} -Demu.h=${SZ#*x} -Demu.hudcall=-1
  job hud_new_$SZ "$NEW" 3 "" -Demu.w=${SZ%x*} -Demu.h=${SZ#*x} -Demu.hudcall=-1
done
if [ $TITLE = 1 ]; then
  SC="400:shot:t400;610:shot:t610;620:tap:240,277;640:shot:s640;690:shot:s690;720:shot:s720"
  job title_orig "$ORIG" 760 "$SC" -Demu.w=2400 -Demu.h=1080 $DUMP -Demu.poolevery=20
  job title_P100 "$NEW" 760 "$SC" -Demu.w=2400 -Demu.h=1080 $DUMP -Demu.poolevery=20 -Demu.poke=0x72f78c=100
  job title_P120 "$NEW" 760 "$SC" -Demu.w=2400 -Demu.h=1080 $DUMP -Demu.poolevery=20 -Demu.poke=0x72f78c=120
fi
echo "$(wc -l < "$JOBS") lan chay, song song 3 ..."
xargs -d '\n' -P 3 -I{} bash -c {} < "$JOBS"
python3 -I scripts/re/view_xcheck.py "$OUTD" $([ $TITLE = 1 ] && echo --title)
