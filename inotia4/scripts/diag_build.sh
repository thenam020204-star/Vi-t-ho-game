#!/usr/bin/env bash
# Dung BAN CHAN DOAN tu mot APK da build (vd test_builds/Inotia4_TEST4_vi_anh_offline.apk):
#   bash scripts/diag_build.sh <base.apk> <out.apk> [--internet] [--smali nhom1,nhom2]
# - khong co --smali: giu nguyen moi dex/lib/asset cua base (apktool -s: khong decode smali);
# - --smali: decode smali day du va ap scripts/patch_smali.py --group <nhom> (vd. net) len base;
# - them classes4.dex = com.namp.vh.diag (LaunchActivity, DiagProvider, CrashLog);
# - manifest: LAUNCHER -> LaunchActivity, them DiagProvider; --internet tra lai quyen INTERNET.
# Can: APKTOOL (jar), BT (build-tools co d8/apksigner/zipalign), ANDROID_JAR (android.jar API >= 30).
set -euo pipefail
cd "$(dirname "$0")/.."
BASE=$(realpath "$1"); OUT=$2; shift 2
MAN_OPTS=(); SMALI=""
while [ $# -gt 0 ]; do
  case "$1" in
    --internet) MAN_OPTS+=(--internet);;
    --smali) SMALI=$2; shift;;
    *) echo "tham so la: $1"; exit 2;;
  esac; shift
done
APKTOOL=${APKTOOL:-tools/apktool_2.12.1.jar}
BT=${BT:-/opt/android/build-tools/35.0.0}
ANDROID_JAR=${ANDROID_JAR:-/opt/android/platforms/android-34/android.jar}
KS=${INOTIA_KS:-keystore/namp-inotia4.jks}; KSPASS=${INOTIA_KS_PASS:-namp-inotia4}
J="env -u JAVA_TOOL_OPTIONS java"
W=work/diag; rm -rf "$W"; mkdir -p "$W/cls" "$W/dex"

echo "== 1. bien dich com.namp.vh.diag (javac --release 8, d8 --min-api 23)"
env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -cp "$ANDROID_JAR" -d "$W/cls" java/diag/com/namp/vh/diag/*.java
$J -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 --release --min-api 23 --lib "$ANDROID_JAR" --output "$W/dex" $(find "$W/cls" -name '*.class')

if [ -z "$SMALI" ]; then
  echo "== 2. apktool d -s (giu nguyen dex)"
  $J -jar "$APKTOOL" d -f -s -o "$W/dec" "$BASE" > "$W/decode.log" 2>&1
else
  echo "== 2. apktool d (smali) + va nhom $SMALI"
  $J -jar "$APKTOOL" d -f -o "$W/dec" "$BASE" > "$W/decode.log" 2>&1
  python3 -I scripts/patch_smali.py "$W/dec" --group "$SMALI"
fi
python3 -I scripts/patch_diag_manifest.py "$W/dec/AndroidManifest.xml" "${MAN_OPTS[@]}"

echo "== 3. apktool b + them classes4.dex + can zip"
$J -jar "$APKTOOL" b -o "$W/unsigned.apk" "$W/dec" > "$W/build.log" 2>&1
python3 -I scripts/add_dex.py "$W/unsigned.apk" "$W/dex/classes.dex" classes4.dex
python3 -I scripts/fix_zip.py "$W/unsigned.apk" "$W/aligned.apk"
"$BT/zipalign" -c -P 16 4 "$W/aligned.apk" && echo "zipalign -c: OK"

echo "== 4. ky v1/v2/v3 (keystore NAMP, cai de duoc len ban TEST truoc)"
$J -jar "$BT/lib/apksigner.jar" sign --ks "$KS" --ks-key-alias namp --ks-pass "pass:$KSPASS" --key-pass "pass:$KSPASS" \
   --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true --out "$OUT" "$W/aligned.apk"
$J -jar "$BT/lib/apksigner.jar" verify --verbose "$OUT" | head -5
python3 -I scripts/check_apk.py "$OUT"
ls -la "$OUT"
