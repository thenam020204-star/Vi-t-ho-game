#!/bin/bash
# Dựng lại thư mục làm việc $WORK từ 3 file người dùng tải lên (chạy lại được, không phá dữ liệu đã có).
# Dùng: WORK=/đường/dẫn UP=/root/.claude/uploads/<id> ./setup_work.sh
set -e
: "${WORK:?cần WORK}"; : "${UP:?cần UP (thư mục chứa các file upload)}"
HERE="$(cd "$(dirname "$0")" && pwd)"; mkdir -p "$WORK" && cd "$WORK"
[ -d apk ] || { mkdir apk && unzip -q "$UP"/*heroes-lore-5*.apk -d apk; }
[ -d proj ] || { mkdir proj && unzip -q "$UP"/*HL_project*.zip -d proj; }
[ -d namp ] || { mkdir namp && unzip -q "$UP"/*Tool_Viet_Hoa_NAM_P*.zip -d namp; }
if [ ! -d lgt5/j ]; then
  mkdir -p lgt5 && (cd lgt5 && unzip -q -o ../proj/HL4_project/HL5.zip && mkdir -p j && cd j && unzip -q -o ../00032870.jar)
fi
P=proj/HL4_project
# sửa đường dẫn cứng của extract5.py (bản đã sửa lưu ở repo: hl5_android/vi_lgt/extract5_patched.py)
cp "$HERE/vi_lgt/extract5_patched.py" $P/vi5/extract5.py
mkdir -p $P/hl5 && [ -d $P/hl5/jar ] || cp -r lgt5/j $P/hl5/jar
[ -f $P/vi5/catalog5.json ] || python3 $P/vi5/extract5.py
[ -f $P/vi5/ids5.json ] || python3 $P/vi5/export5.py
[ -d $P/vi5/build/overlay ] || python3 $P/vi5/build_vi5.py $P/vi5/tr_in/HL5_dich_FULL.tsv $P/vi5/override.tsv
[ -f so.dis ] || llvm-objdump -d -C --no-show-raw-insn --triple=armv7 apk/assets/libHeroesLore5.so > so.dis
[ -f br.dis ] || llvm-objdump -d --no-show-raw-insn apk/lib/arm64-v8a/libhl5_arm64_bridge.so > br.dis
echo "WORK sẵn sàng: $WORK"
