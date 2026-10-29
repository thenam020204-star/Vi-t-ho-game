#!/bin/bash
# Build jar Viet hoa cho MOT ban: giai nen goc -> repack text -> patch class -> + VH + font + anh -> zip -> preverify -> jar_check
#   ./build.sh <240|320|360>      TSV=... (mac dinh dich/dp_work/final/text_can_dich.tsv, chua co thi dich/text_can_dich.tsv)
set -e -o pipefail
cd "$(dirname "$0")"
source scripts/env.sh
V=${1:?ban: 240|320|360}
case $V in 240) J=240x320;; 320) J=320;; 360) J=360x640;; esac
TSV=${TSV:-dich/dp_work/final/text_can_dich.tsv}; [ -f "$TSV" ] || TSV=dich/text_can_dich.tsv
GOC=goc/tvtt_$J.jar
OUT=${OUT:-out/TinhVanTruyenThuyet_${J}_vi.jar}
L="$LIBS"; B=build/$V
mkdir -p logs out
[ -f build/hook/src/VH.class ] && [ build/hook/src/VH.class -nt hook/VH.java ] && [ build/patcher/Patch.class -nt patcher/Patch.java ] || ./build_hook.sh
rm -rf $B && mkdir -p $B/x $B/rp $B/patched $B/stage
(cd $B/x && unzip -oq "../../../$GOC")
python3 -I scripts/repack.py $B/x "$TSV" $B/rp | tee logs/repack_$V.txt
cp -r $B/x/. $B/stage/ && cp -r $B/rp/. $B/stage/
env -u JAVA_TOOL_OPTIONS java -cp $L/asm-9.7.1.jar:$L/asm-tree-9.7.1.jar:build/patcher Patch $V $B/stage $B/patched logs/patch_$V.log
cp -r $B/patched/. $B/stage/
cp build/hook/src/VH.class $B/stage/src/
mkdir -p $B/stage/font && cp "$FONT_DIR/f12.bin" $B/stage/font/
MT=docs/module_chu.tsv; [ -f docs/module_chu_$V.tsv ] && MT="$MT docs/module_chu_$V.tsv"
python3 -I scripts/build_vhm.py $B/x $B/stage/font/vhm.dat $MT
[ -d res_vi ] && cp -r res_vi/. $B/stage/
[ -d res_vi_$V ] && cp -r res_vi_$V/. $B/stage/
[ -x scripts/post_stage.sh ] && scripts/post_stage.sh $V $B/stage
python3 -I scripts/make_manifest.py $B/x/META-INF/MANIFEST.MF $B/stage/META-INF/MANIFEST.MF
rm -f $B/raw.jar
(cd $B/stage && zip -q -X -9 ../raw.jar META-INF/MANIFEST.MF && zip -q -X -9 -r ../raw.jar . -x META-INF/MANIFEST.MF)
bash $S/preverify.sh $B/raw.jar "$OUT"
python3 -I $S/jar_check.py "$GOC" "$OUT" | tee logs/jar_check_$V.txt | tail -4
python3 -I scripts/make_jad.py "$OUT" > /dev/null 2>&1 || true
