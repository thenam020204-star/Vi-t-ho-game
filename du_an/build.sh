#!/bin/bash
# Build jar Viet hoa: patch -> dong jar -> preverify -> jar_check
set -e -o pipefail
cd "$(dirname "$0")"
source work/tools/env.sh
S=scripts_skill
./build_hook.sh
rm -rf build/patched build/stage && mkdir -p build/patched build/stage
env -u JAVA_TOOL_OPTIONS java -cp work/lib/asm-9.8.jar:work/lib/asm-tree-9.7.1.jar:build/patcher Patch jar_x build/patched build/patch.log
# stage: jar goc, bo source .java (khong dung luc chay)
cp -r jar_x/. build/stage/ && rm -f build/stage/*.java && rm -rf build/stage/META-INF
cp build/patched/*.class build/stage/
cp build/hook/VH.class build/stage/
mkdir -p build/stage/font && cp work/tools/fonts/f12.bin build/stage/font/
[ -d res_vi ] && cp -r res_vi/. build/stage/
# go tai nguyen nha mang khong con dung: anh hoi am thanh, logo 移动圈圈, may quay so CMCC/Sohu, nhac hieu, logo link, SDK
rm -f build/stage/p1 build/stage/p2 build/stage/p3 build/stage/logo_G build/stage/BG build/stage/m0 build/stage/m1 build/stage/m2 build/stage/m3 build/stage/m4 build/stage/m5 build/stage/m6 build/stage/sound.mid
rm -rf build/stage/res
mkdir -p build/stage/META-INF && cp manifest/MANIFEST.MF build/stage/META-INF/MANIFEST.MF
rm -f build/raw.jar out_game.jar
(cd build/stage && zip -q -X -9 ../raw.jar META-INF/MANIFEST.MF && zip -q -X -9 -r ../raw.jar . -x META-INF/MANIFEST.MF)
bash $S/preverify.sh build/raw.jar out_game.jar
python3 -I $S/jar_check.py game.jar out_game.jar > build/jar_check.txt || true
tail -5 build/jar_check.txt
