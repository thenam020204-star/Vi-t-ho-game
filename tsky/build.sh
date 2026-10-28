#!/bin/bash
# Build jar Viet hoa (240x320): giai nen goc -> repack text -> patch class -> + VH + font -> zip -> preverify -> jar_check
#   TSV=duong_dan.tsv ./build.sh [ten_jar_ra]   (mac dinh dich/dp_work/final/text_can_dich.tsv, out/TienSungKyDuyen_240x320_vi.jar)
set -e -o pipefail
cd "$(dirname "$0")"
source work/tools/env.sh
S=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa/scripts
TSV=${TSV:-dich/dp_work/final/text_can_dich.tsv}
GOC=goc/TienSungKyDuyen_240x320.jar
OUT=${1:-out/TienSungKyDuyen_240x320_vi.jar}
L=work/lib
mkdir -p build logs "$(dirname "$OUT")"
./build_hook.sh
rm -rf build/x build/rp build/xr build/patched build/stage && mkdir -p build/x build/patched build/stage
(cd build/x && unzip -oq "../../$GOC")
# 1) text -> pak/t/class da Viet hoa (chua dich: giong tung byte)   2) overlay len ban giai nen   3) patch call-site tren ban do
python3 -I scripts/repack.py build/x "$TSV" build/rp
cp -r build/x build/xr && cp -r build/rp/. build/xr/
env -u JAVA_TOOL_OPTIONS java -cp $L/asm-9.7.1.jar:$L/asm-tree-9.7.1.jar:build/patcher Patch build/xr build/patched logs/patch.log "$TSV"
cp -r build/xr/. build/stage/ && rm -rf build/stage/META-INF
cp -r build/patched/. build/stage/
cp build/hook/VH.class build/stage/
mkdir -p build/stage/font && cp work/tools/fonts/f12.bin build/stage/font/
# logo nha phat hanh + hoat hinh khoi dong goc: chi Main/f.c() tham chieu (da bo, QD 9)
for f in bg logo0 logo1 logo2 logo3 logo4 logo5 logo51 logo52; do rm -f build/stage/Image/$f.png; done
[ -d res_vi ] && cp -r res_vi/. build/stage/
# 2 ban: 240x320 (manifest goc, rpg,N73) va 320x240 (rpg,E62, res_vi_320 de len res_vi). Chung class da va.
make_jar() {   # $1=ten bien the  $2=ra  $3=mo ta  $4=thu muc res de len (hoac rong)
  rm -rf build/stage_$1 && cp -r build/stage build/stage_$1
  [ -n "$4" ] && [ -d "$4" ] && cp -r "$4"/. build/stage_$1/
  mkdir -p build/stage_$1/META-INF
  unzip -p "$GOC" META-INF/MANIFEST.MF | sed "s|^MIDlet-Description: .*|MIDlet-Description: $3\r|" > build/stage_$1/META-INF/MANIFEST.MF
  rm -f build/raw_$1.jar
  (cd build/stage_$1 && zip -q -X -9 ../raw_$1.jar META-INF/MANIFEST.MF && zip -q -X -9 -r ../raw_$1.jar . -x META-INF/MANIFEST.MF)
  bash $S/preverify.sh build/raw_$1.jar "$2"
  python3 -I $S/jar_check.py "$GOC" "$2" | tee logs/jar_check_$1.txt | tail -3
}
make_jar 240 "$OUT" "rpg,N73" ""
[ "${ONLY240:-0}" = 1 ] || make_jar 320 "${OUT320:-out/TienSungKyDuyen_320x240_vi.jar}" "rpg,E62" res_vi_320
cp logs/jar_check_240.txt logs/jar_check.txt
