#!/usr/bin/env bash
# Dựng jar việt hoá cho một phần:  build_part.sh <N> <res_dir> <out.jar> [class_tsv]
#   res_dir: thư mục resource đã repack (rỗng/không có = dùng nguyên gốc)
#   class_tsv: bản dịch chuỗi trong .class (cột dich), vá bằng class_text.py trước khi chuyển hướng font
# Cần: source work/tools/env.sh (FJ, PROGUARD, BITMAPFONT_CLASS, FONT_DIR), WS, P.
set -e
N=$1; RES=$2; OUT=$3; CT=$4
: "${WS:?}"; : "${P:?}"
SK=${SK:-/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa}
T=$WS/build/p$N; rm -rf "$T"; mkdir -p "$T/jar" "$T/cls" "$T/src"
cp -r "$WS/x/$N/." "$T/jar/"
if [ -n "$RES" ] && [ -d "$RES" ]; then cp -r "$RES/." "$T/jar/"; fi
# 0) class nguồn = gốc, đè class đã vá chuỗi (nếu có bản dịch class)
(cd "$WS/x/$N" && find . -name '*.class' -exec cp --parents {} "$T/src" \;)
if [ -n "$CT" ] && [ -f "$CT" ]; then python3 -I "$P/scripts/class_text.py" patch "$WS/x/$N" "$CT" "$T/src"; fi
# 1) chuyển hướng lời gọi font/ngắt dòng
python3 -I "$P/scripts/cp_redirect.py" "$P/p$N/font_rules.tsv" "$T/src" "$T/cls" --report "$T/redirect.txt"
cp -r "$T/src/." "$T/jar/"; cp -r "$T/cls/." "$T/jar/"
if [ -d "$P/p$N/classes" ]; then cp -r "$P/p$N/classes/." "$T/jar/"; fi
# 2) class mới + font
env -u JAVA_TOOL_OPTIONS java -cp "$WS/work/lib/ecj-3.26.0.jar" org.eclipse.jdt.internal.compiler.batch.Main \
  -source 1.3 -target 1.3 -encoding UTF-8 -nowarn -bootclasspath "$WS/work/lib/jbase/classes" \
  -cp "$(dirname "$BITMAPFONT_CLASS"):$FJ" -d "$T/jar" "$P/java/VnFont.java"
cp "$BITMAPFONT_CLASS" "$T/jar/"
mkdir -p "$T/jar/font"; cp "$FONT_DIR/f15c.bin" "$T/jar/font/"
# 3) manifest: MIDP-2.0 (BitmapFont dùng drawRegion/createRGBImage)
sed -i 's/^MicroEdition-Profile: MIDP-1.0/MicroEdition-Profile: MIDP-2.0/' "$T/jar/META-INF/MANIFEST.MF"
if [ -f "$P/p$N/manifest_override.txt" ]; then python3 -I "$P/scripts/manifest_edit.py" "$T/jar/META-INF/MANIFEST.MF" "$P/p$N/manifest_override.txt"; fi
# 4) đóng jar (manifest đứng đầu), preverify, kiểm
rm -f "$T/raw.jar"
(cd "$T/jar" && zip -q -X -D "$T/raw.jar" META-INF/MANIFEST.MF && zip -q -X -D -r "$T/raw.jar" . -x META-INF/MANIFEST.MF)
bash "$SK/scripts/preverify.sh" "$T/raw.jar" "$OUT"
python3 -I "$SK/scripts/jar_check.py" "$WS/jars/hs$N.jar" "$OUT" | tee "$T/jar_check.txt" | tail -25
