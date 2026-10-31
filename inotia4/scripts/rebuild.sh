#!/usr/bin/env bash
# Dung lai Inotia 4 Viet hoa + offline tu APKS goc.
#   bash scripts/rebuild.sh <thu muc chua base.apk + split_config.*.apk>
# Ket qua: out/Inotia4_vi_offline.apk
set -euo pipefail
cd "$(dirname "$0")/.."
ROOT=$PWD
SPLITS=${1:?"can thu muc chua base.apk va split_config.*.apk"}
W=$ROOT/work
T=$ROOT/tools
J="env -u JAVA_TOOL_OPTIONS java"
PY="python3 -I"
mkdir -p "$W" out logs

echo "== 0. kiem file goc"
( cd "$SPLITS" && sha256sum base.apk split_config.*.apk ) > "$W/sha_in.txt"
grep -v Inotia4_1.4.0.apks goc/SHA256.txt | sed 's#apks/##' | sort -k2 > "$W/sha_ref.txt"
sort -k2 "$W/sha_in.txt" | diff - "$W/sha_ref.txt" && echo "sha256 khop ban goc"

echo "== 1. gop split -> 1 APK (APKEditor)"
rm -f "$W/merged.apk"
$J -jar $T/APKEditor-1.4.1.jar m -i "$SPLITS" -o "$W/merged.apk" > logs/merge.log 2>&1

echo "== 2. apktool decode"
rm -rf "$W/dec"
$J -jar $T/apktool_2.10.0.jar d -f -o "$W/dec" "$W/merged.apk" > logs/decode.log 2>&1

echo "== 3. va smali"
$PY scripts/patch_smali.py "$W/dec" --group font,lang,offline | tee logs/patch_smali.log
echo "== 3b. va libgame.so (offline)"
$PY scripts/patch_lib.py "$W/dec/lib/arm64-v8a/libgame.so" "$W/libgame_patched.so" | tee logs/patch_lib.log
cp "$W/libgame_patched.so" "$W/dec/lib/arm64-v8a/libgame.so"
if [ -f scripts/patch_manifest.py ]; then $PY scripts/patch_manifest.py "$W/dec/AndroidManifest.xml" | tee logs/patch_manifest.log; fi

echo "== 4. text tieng Viet -> moi slot memorytext"
G="$W/dec/assets/common/game_res"
$PY scripts/memtext.py unwrap "$G/memorytext_en.dat.jpg" "$W/en.dat"
$PY scripts/memtext.py check "$W/en.dat" dich/text_can_dich.tsv --charset font/charset.txt | tail -1
$PY scripts/memtext.py repack "$W/en.dat" dich/text_can_dich.tsv "$W/vi.dat"
$PY scripts/memtext.py wrap "$W/vi.dat" "$W/vi.dat.jpg"
for f in "$G"/memorytext.dat.jpg "$G"/memorytext_{en,de,fr,jp,zhhans,zhhant}.dat.jpg; do cp "$W/vi.dat.jpg" "$f"; done
if [ -d res_vi ] && [ -n "$(ls -A res_vi 2>/dev/null)" ]; then cp -v res_vi/* "$G"/ | tee logs/res_vi.log; fi

echo "== 5. VNFont -> classes3.dex"
rm -rf "$W/vnf" && mkdir -p "$W/vnf/cls" "$W/vnf/stub" "$W/vnf/dex"
$PY scripts/gen_fontdata.py font/f18vn.bin java/src/com/namp/vh/FontData.java
env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -d "$W/vnf/stub" $(find java/stubs -name '*.java')
env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -cp "$W/vnf/stub" -d "$W/vnf/cls" java/src/com/namp/vh/*.java
# chi dong goi com/namp/vh (stub KHONG vao dex)
$J -cp $T/r8.jar com.android.tools.r8.D8 --release --min-api 23 --output "$W/vnf/dex" "$W"/vnf/cls/com/namp/vh/*.class

echo "== 6. apktool build"
$J -jar $T/apktool_2.10.0.jar b -o "$W/unsigned.apk" "$W/dec" > logs/build.log 2>&1
$PY scripts/add_dex.py "$W/unsigned.apk" "$W/vnf/dex/classes.dex" classes3.dex
$PY scripts/fix_zip.py "$W/unsigned.apk" "$W/unsigned_fixed.apk"

echo "== 7. zipalign + ky"
KS=${INOTIA_KS:-$ROOT/keystore/namp-inotia4.jks}
KSPASS=${INOTIA_KS_PASS:-namp-inotia4}
if [ ! -f "$KS" ]; then
  echo "chua co keystore -> tao moi $KS (KHONG commit; giu lai de ban cap nhat cai de duoc)"
  mkdir -p "$(dirname "$KS")"
  env -u JAVA_TOOL_OPTIONS keytool -genkeypair -keystore "$KS" -alias namp -keyalg RSA -keysize 2048 \
     -validity 20000 -storepass "$KSPASS" -keypass "$KSPASS" -dname "CN=NAMP Viet hoa, O=NAMP, C=VN"
fi
rm -rf "$W/signed" && mkdir -p "$W/signed"
$J -jar $T/uber-apk-signer-1.3.0.jar -a "$W/unsigned_fixed.apk" -o "$W/signed" --skipZipAlign \
   --ks "$KS" --ksAlias namp --ksPass "$KSPASS" --ksKeyPass "$KSPASS" > logs/sign.log 2>&1
cp "$W"/signed/*-signed.apk out/Inotia4_vi_offline.apk

echo "== 8. kiem tra"
$PY scripts/check_apk.py out/Inotia4_vi_offline.apk | tee logs/check_apk.log
ls -la out/Inotia4_vi_offline.apk
