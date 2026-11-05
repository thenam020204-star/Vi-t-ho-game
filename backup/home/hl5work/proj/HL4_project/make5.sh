#!/bin/bash
# Build Heroes Lore 5 (LGT WIPI) -> J2ME jar.   usage: ./make5.sh [suffix]
# Result: dist5/HL5_LGT<suffix>.jar/.jad
set -e
W=/home/claude/work; cd $W
export JAVA_TOOL_OPTIONS=
[ -d hl5/jar ] || { mkdir -p hl5/orig hl5/jar; unzip -q -o HL5.zip -d hl5/orig; (cd hl5/jar && unzip -q -o ../orig/*.jar); }
(cd hl5j && ./tbuild.sh build/t 2>&1 | grep -v JAVA_TOOL || true)
rm -rf hl5/overlay && mkdir -p hl5/overlay/res/c/snd
java -cp hl5j/build/t SndConvert5 hl5/jar/res/c/snd hl5/overlay/res/c/snd /res/c/snd/
# Ảnh thay thế (logo NSX -> logo người dùng: img5/mk_ealogo.py)
[ -d img5/overlay ] && cp -r img5/overlay/. hl5/overlay/
# Việt hoá: VI=1 ./make5.sh _vi  (overlay từ vi5/build/overlay, tạo bằng vi5/build_vi5.py)
[ -n "$VI" ] && cp -r vi5/build/overlay/. hl5/overlay/
(cd hl5j && OVERLAY=$W/hl5/overlay SUFFIX=$1 ./build.sh $W/HL5.zip $W/dist5)
ls -l dist5
