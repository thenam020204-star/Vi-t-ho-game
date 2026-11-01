#!/bin/bash
# Dựng lớp chẩn đoán: Diag.java -> classes3.dex, và vá smali của runner (classes.dex, classes2.dex).
# Ra: $OUT/{classes.dex,classes2.dex,classes3.dex}. Dùng: WORK=... ./build_diag.sh ORIG_APK OUT
set -euo pipefail
HERE="$(cd "$(dirname "$0")" && pwd)"
: "${WORK:?}"
ORIG="$1"; OUT="$2"; BT=${BT:-/opt/asdk/android-14}
T="$WORK/tools_dl"; mkdir -p "$T" "$OUT"
G=https://dl.google.com/dl/android/maven2/com/android/tools/smali; M=https://repo1.maven.org/maven2
get() { [ -s "$T/$(basename "$2")" ] || curl -fsSL -m 120 -o "$T/$(basename "$2")" "$1/$2"; }
for a in smali-baksmali smali smali-dexlib2 smali-util; do get $G $a/3.0.9/$a-3.0.9.jar; done
get $M com/google/guava/guava/32.1.3-jre/guava-32.1.3-jre.jar
get $M com/google/guava/failureaccess/1.0.1/failureaccess-1.0.1.jar
get $M com/beust/jcommander/1.82/jcommander-1.82.jar
get $M org/antlr/antlr-runtime/3.5.2/antlr-runtime-3.5.2.jar
get $M org/antlr/stringtemplate/3.2.1/stringtemplate-3.2.1.jar
get $M com/google/code/findbugs/jsr305/3.0.2/jsr305-3.0.2.jar
get $M com/google/android/android/4.1.1.4/android-4.1.1.4.jar   # chỉ để javac/d8 (stub API cũ; API mới dùng reflection)
CP=$(ls "$T"/*.jar | grep -v 'android-4' | tr '\n' ':')
JV="java -cp $CP"
X="$OUT/x"; rm -rf "$X"; mkdir -p "$X/apk" "$X/s1" "$X/s2" "$X/cls"
unzip -oq "$ORIG" classes.dex classes2.dex -d "$X/apk"
$JV com.android.tools.smali.baksmali.Main d "$X/apk/classes.dex" -o "$X/s1" 2>&1 | grep -v JAVA_TOOL || true
$JV com.android.tools.smali.baksmali.Main d "$X/apk/classes2.dex" -o "$X/s2" 2>&1 | grep -v JAVA_TOOL || true
python3 -I "$HERE/patch_smali.py" "$X/s1" "$X/s2"
# api 21 -> dex 035 như bản gốc
$JV com.android.tools.smali.smali.Main a -a 21 "$X/s1" -o "$OUT/classes.dex" 2>&1 | grep -v JAVA_TOOL || true
$JV com.android.tools.smali.smali.Main a -a 21 "$X/s2" -o "$OUT/classes2.dex" 2>&1 | grep -v JAVA_TOOL || true
javac -nowarn --release 8 -cp "$T/android-4.1.1.4.jar" -d "$X/cls" "$HERE"/src/co/kr/eamobile/hl5/diag/*.java 2>&1 | grep -v JAVA_TOOL || true
"$BT/d8" --no-desugaring --min-api 21 --lib "$T/android-4.1.1.4.jar" --output "$X" $(find "$X/cls" -name '*.class') 2>&1 | grep -v JAVA_TOOL || true
mv "$X/classes.dex" "$OUT/classes3.dex"
ls -l "$OUT"/classes*.dex
