#!/usr/bin/env bash
# Dựng jar việt hoá cho một phần:  build_part.sh <N> <res_dir> <out.jar>
#   res_dir: thư mục resource đã repack (rỗng/không có = dùng nguyên gốc)
# Cần: source work/tools/env.sh (FJ, PROGUARD, BITMAPFONT_CLASS, FONT_DIR), WS, P.
set -e
N=$1; RES=$2; OUT=$3
: "${WS:?}"; : "${P:?}"
SK=${SK:-/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa}
T=$WS/build/p$N; rm -rf "$T"; mkdir -p "$T/jar" "$T/cls"
cp -r "$WS/x/$N/." "$T/jar/"
[ -n "$RES" ] && [ -d "$RES" ] && cp -r "$RES/." "$T/jar/"
# 1) chuyển hướng lời gọi font/ngắt dòng
python3 -I "$P/scripts/cp_redirect.py" "$P/p$N/font_rules.tsv" "$WS/x/$N" "$T/cls" --report "$T/redirect.txt"
cp -r "$T/cls/." "$T/jar/"
[ -d "$P/p$N/classes" ] && cp -r "$P/p$N/classes/." "$T/jar/"
# 2) class mới + font
env -u JAVA_TOOL_OPTIONS java -cp "$WS/work/lib/ecj-3.26.0.jar" org.eclipse.jdt.internal.compiler.batch.Main \
  -source 1.3 -target 1.3 -encoding UTF-8 -nowarn -bootclasspath "$WS/work/lib/jbase/classes" \
  -cp "$(dirname "$BITMAPFONT_CLASS"):$FJ" -d "$T/jar" "$P/java/VnFont.java"
cp "$BITMAPFONT_CLASS" "$T/jar/"
mkdir -p "$T/jar/font"; cp "$FONT_DIR/f15c.bin" "$T/jar/font/"
# 3) manifest: MIDP-2.0 (BitmapFont dùng drawRegion/createRGBImage)
sed -i 's/^MicroEdition-Profile: MIDP-1.0/MicroEdition-Profile: MIDP-2.0/' "$T/jar/META-INF/MANIFEST.MF"
# 4) đóng jar (manifest đứng đầu), preverify, kiểm
rm -f "$T/raw.jar"
(cd "$T/jar" && zip -q -X -D "$T/raw.jar" META-INF/MANIFEST.MF && zip -q -X -D -r "$T/raw.jar" . -x META-INF/MANIFEST.MF)
bash "$SK/scripts/preverify.sh" "$T/raw.jar" "$OUT"
python3 -I "$SK/scripts/jar_check.py" "$WS/jars/hs$N.jar" "$OUT" | tee "$T/jar_check.txt" | tail -25
