#!/bin/bash
# Build toàn bộ: overlay tiếng Việt -> jar J2ME.
# usage: ./make.sh [KTF|LGT|all] [15|10]     (mặc định: all 15)
# Kết quả: dist/HL4_<PLAT>_VI.jar/.jad  (+ dist/HL4_<PLAT>.jar bản gốc Hàn nếu ORIG=1)
set -e
W=/home/claude/work; cd $W
PLAT=${1:-all}; FS=${2:-15}
if [ "$FS" = 10 ]; then export VI_FONT=$W/vi/font/font_10px; else export VI_FONT=$W/vi/font/font_15px.bin; fi
(cd vi && python3 build_vi.py | tail -3)
# audio: SMAF -> plain .mid / .wav (+ stubs under the original names)
java -cp hl4j/build/test2 SndConvert ktfjar vi/build/ktf 2>&1 | grep -v JAVA_TOOL
java -cp hl4j/build/test2 SndConvert lgtjar/res vi/build/lgt res/ 2>&1 | grep -v JAVA_TOOL
for P in KTF LGT; do
  [ "$PLAT" = all ] || [ "$PLAT" = $P ] || continue
  p=$(echo $P | tr A-Z a-z)
  (cd hl4j && OVERLAY=$W/vi/build/$p SUFFIX=_VI ./build.sh $P $W/$P.zip $W/dist)
  [ -n "$ORIG" ] && (cd hl4j && ./build.sh $P $W/$P.zip $W/dist)
done
ls -l dist
