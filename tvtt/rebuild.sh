#!/bin/bash
# Dung lai TOAN BO tu jar goc (goc/) -> out/*.jar + .jad cho 3 ban.
#   ./rebuild.sh            (can tools/ hoac work/tools/: xem tools/VERSIONS.md)
#   RUN_EMU=1 ./rebuild.sh  them buoc chay FreeJ2ME (menu -> vao game), anh o logs/rebuild_<ban>/
set -e -o pipefail
cd "$(dirname "$0")"
source scripts/env.sh
rm -rf build out/*.jar out/*.jad
mkdir -p out logs work
./build_hook.sh
for p in 240:240x320 320:320 360:360x640; do
  V=${p%%:*}; J=${p#*:}
  rm -rf work/x_$J && mkdir -p work/x_$J && (cd work/x_$J && unzip -oq ../../goc/tvtt_$J.jar)
  rm -rf res_vi_$V && python3 -I scripts/make_img.py work/x_$J res_vi_$V > logs/make_img_$V.txt
  ./build.sh $V
done
sha256sum out/*.jar | tee logs/rebuild_sha256.txt
if [ "${RUN_EMU:-0}" = 1 ]; then
  for p in 240x320:240x320 320x240:320x240 360x640:640x360; do
    n=${p%%:*}; sz=${p#*:}
    S="wait 9; key Num_Lock; shot menu; key Return; wait 6; key KP_0; wait 2; key KP_0; wait 2; key KP_0; wait 3"
    for i in $(seq 1 14); do S="$S; key KP_0; key KP_5"; done
    python3 scripts/run_emu.py --fj $FJ --jar out/TinhVanTruyenThuyet_${n}_vi.jar --size $sz --out logs/rebuild_$n --steps "$S; wait 1; shot game" | tee logs/run_emu_rebuild_$n.json
  done
fi
echo "rebuild OK"
