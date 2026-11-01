#!/usr/bin/env bash
# Dung lai Inotia 4 Viet hoa + offline (tuong duong TEST12, KHONG co chan doan) tu bo split goc.
#
#   bash scripts/rebuild.sh <thu muc chua base.apk + split_config.*.apk> [--out <apk>] [--diag [--internet]]
#   bash scripts/rebuild.sh --base-apk <APK nen> [--out <apk>] [--diag [--internet]]
#   bash scripts/rebuild.sh --versions
#
# Mac dinh (= ban phat hanh, T6): ra out/Inotia4_vi_offline.apk, KHONG chan doan (diag), KHONG quyen INTERNET.
#   --out <apk>   : duong dan APK ra (mac dinh out/Inotia4_vi_offline.apk).
#   --diag        : them ban chan doan (classes4.dex = com.namp.vh.diag + LAUNCHER -> LaunchActivity) = ban TEST12 de gui nguoi dung thu.
#   --internet    : (chi cung --diag, chi de thu) tra lai quyen INTERNET.
#   --base-apk    : khi KHONG co bo split goc: xuat phat tu mot APK da gop + da lam buoc nen (vi du
#                   test_builds/Inotia4_TEST4_vi_anh_offline.apk = split goc + smali font,lang,offline + go quyen manifest + text + anh).
#                   Bo cac buoc 0-1 (kiem sha, gop split) va 3a-3d (manifest, plist, text, anh), chi ap nhom smali con thieu (net,mod).
#                   Cac buoc con lai GIONG HET duong split. Day la cach kiem tra T5 trong container khong co split (xem PROGRESS.md "Phien 8").
#   --versions    : in phien ban cac tool roi thoat (xem tools/VERSIONS.md).
#
# Cac nhom va (T5, dong bo voi chuoi build TEST12 cua scripts/build_from_apk.sh):
#   smali : font,lang,offline,net,mod    (scripts/patch_smali.py)
#   dex   : com.namp.vh (VNFont, FontData, FontData24, Offline, ModMenu, ModMem) -> classes3.dex  (= build_from_apk.sh --vnfont)
#   lib   : base,p1,mod,view,hud,pool,hudbtn  (scripts/patch_lib.py)
#   atlas : scripts/hud_swap.py (UI_014/UI_015 <- assets_user/hud/), luon di cung nhom lib hudbtn
#
# Tool (bien moi truong ghi de; thu tu tim: bien -> tools/ -> /opt/re, /opt/android). Phien ban: tools/VERSIONS.md
#   APKTOOL      apktool_2.12.1.jar                       APKEDITOR  APKEditor-*.jar (chi duong split)
#   BT           build-tools 35.0.0 (d8.jar, apksigner.jar, zipalign)       ANDROID_JAR  platforms/android-34/android.jar
#   INOTIA_KS, INOTIA_KS_PASS  keystore ky (mac dinh keystore/namp-inotia4.jks, alias namp)
#   WORK         thu muc tam (mac dinh work/rebuild; xoa sach moi lan chay). Log o $WORK/logs.
set -euo pipefail
cd "$(dirname "$0")/.."
ROOT=$PWD

SMALI_ALL=font,lang,offline,net,mod
SMALI_BASE_HAS=font,lang,offline      # APK nen (TEST4) da mang san 3 nhom nay
SMALI_BASE_ADD=net,mod
LIB_GROUPS=base,p1,mod,view,hud,pool,hudbtn

SPLITS=""; BASE_APK=""; OUT=out/Inotia4_vi_offline.apk; DIAG=0; MAN_OPTS=(); SHOW_VERSIONS=0
while [ $# -gt 0 ]; do
  case "$1" in
    --out) OUT=${2:?"--out can duong dan"}; shift;;
    --diag) DIAG=1;;
    --internet) MAN_OPTS+=(--internet);;
    --base-apk) BASE_APK=${2:?"--base-apk can duong dan"}; shift;;
    --versions) SHOW_VERSIONS=1;;
    -h|--help) sed -n '2,/^set -euo/p' "$0" | sed '$d' | sed 's/^# \{0,1\}//'; exit 0;;
    -*) echo "tham so la: $1"; exit 2;;
    *) [ -z "$SPLITS" ] || { echo "chi nhan mot thu muc split"; exit 2; }; SPLITS=$1;;
  esac; shift
done
if [ ${#MAN_OPTS[@]} -gt 0 ] && [ $DIAG = 0 ]; then echo "--internet chi dung cung --diag"; exit 2; fi

pick() {  # pick <bien da dat> <ung vien...> -> in duong dan dau tien ton tai
  local v=$1; shift
  if [ -n "$v" ]; then echo "$v"; return; fi
  local c; for c in "$@"; do if [ -e "$c" ]; then echo "$c"; return; fi; done
  echo "${1}"
}
APKTOOL=$(pick "${APKTOOL:-}" tools/apktool_2.12.1.jar /opt/re/apktool_2.12.1.jar)
APKEDITOR=$(pick "${APKEDITOR:-}" tools/APKEditor-1.4.1.jar tools/APKEditor-1.4.5.jar /opt/re/APKEditor-1.4.1.jar /opt/re/APKEditor-1.4.5.jar)
BT=$(pick "${BT:-}" tools/build-tools/35.0.0 /opt/android/build-tools/35.0.0)
ANDROID_JAR=$(pick "${ANDROID_JAR:-}" tools/android-34/android.jar /opt/android/platforms/android-34/android.jar)
KS=${INOTIA_KS:-keystore/namp-inotia4.jks}; KSPASS=${INOTIA_KS_PASS:-namp-inotia4}
J="env -u JAVA_TOOL_OPTIONS java"
JAVAC="env -u JAVA_TOOL_OPTIONS javac"
PY="python3 -I"
D8="$J -cp $BT/lib/d8.jar com.android.tools.r8.D8 --release --min-api 23 --lib $ANDROID_JAR"

tool_versions() {
  echo "java       : $($J -version 2>&1 | head -1)"
  echo "javac      : $($JAVAC -version 2>&1 | tail -1)"
  echo "apktool    : $($J -jar "$APKTOOL" --version 2>&1 | tail -1)   ($APKTOOL)"
  echo "d8         : $($J -cp "$BT/lib/d8.jar" com.android.tools.r8.D8 --version 2>&1 | head -1)   ($BT/lib/d8.jar)"
  echo "apksigner  : $($J -jar "$BT/lib/apksigner.jar" --version 2>&1 | tail -1)   ($BT/lib/apksigner.jar)"
  echo "build-tools: $(sed -n 's/^Pkg.Revision=//p' "$BT/source.properties" | head -1)   ($BT; zipalign)"
  echo "android.jar: API $(sed -n 's/^AndroidVersion.ApiLevel=//p' "$(dirname "$ANDROID_JAR")/source.properties" 2>/dev/null | head -1)   ($ANDROID_JAR)"
  echo "APKEditor  : $($J -jar "$APKEDITOR" -h 2>&1 | grep -m1 'APKEditor -' | sed 's/ *$//')   ($APKEDITOR)"
  echo "python3    : $(python3 --version 2>&1)   Pillow $(python3 -I -c 'import PIL; print(PIL.__version__)' 2>&1)"
  echo "llvm (chi cho patch_lib.py --check-asm, khong can de dung APK): $(llvm-mc --version 2>/dev/null | sed -n 's/.*LLVM version/LLVM/p' | head -1)"
}
if [ $SHOW_VERSIONS = 1 ]; then tool_versions; exit 0; fi

if [ -n "$BASE_APK" ] && [ -n "$SPLITS" ]; then echo "chon MOT trong: thu muc split HOAC --base-apk"; exit 2; fi
if [ -z "$BASE_APK" ] && [ -z "$SPLITS" ]; then echo "can <thu muc split> hoac --base-apk <apk>   (bash scripts/rebuild.sh -h)"; exit 2; fi
for f in "$APKTOOL" "$BT/lib/d8.jar" "$BT/lib/apksigner.jar" "$BT/zipalign" "$ANDROID_JAR"; do
  [ -e "$f" ] || { echo "thieu tool: $f  (xem tools/VERSIONS.md; ghi de bang bien APKTOOL/BT/ANDROID_JAR)"; exit 1; }
done
if [ -n "$BASE_APK" ] && [ ! -f "$BASE_APK" ]; then echo "khong thay --base-apk $BASE_APK"; exit 1; fi
if [ -n "$SPLITS" ] && [ ! -e "$APKEDITOR" ]; then echo "thieu APKEditor: $APKEDITOR"; exit 1; fi
if [ -n "$SPLITS" ] && [ ! -d "$SPLITS" ]; then echo "khong thay thu muc split $SPLITS"; exit 1; fi

W=$(realpath -m "${WORK:-$ROOT/work/rebuild}")
LOGS=$W/logs
OUT=$(realpath -m "$OUT")
rm -rf "$W"; mkdir -p "$W" "$LOGS" "$(dirname "$OUT")"
tool_versions | tee "$LOGS/tools.txt"
D=$W/dec

if [ -n "$SPLITS" ]; then
  SPLITS=$(realpath "$SPLITS")
  echo "== 0. kiem file goc"
  ( cd "$SPLITS" && sha256sum base.apk split_config.*.apk ) > "$W/sha_in.txt"
  grep -v Inotia4_1.4.0.apks goc/SHA256.txt | sed 's#apks/##' | sort -k2 > "$W/sha_ref.txt"
  sort -k2 "$W/sha_in.txt" | diff - "$W/sha_ref.txt" && echo "sha256 khop ban goc"

  echo "== 1. gop split -> 1 APK (APKEditor)"
  $J -jar "$APKEDITOR" m -i "$SPLITS" -o "$W/merged.apk" > "$LOGS/merge.log" 2>&1
  MERGED=$W/merged.apk
  SMALI=$SMALI_ALL
else
  BASE_APK=$(realpath "$BASE_APK")
  echo "== 0-1. bo qua kiem sha + gop split: xuat phat tu APK nen $BASE_APK"
  echo "   sha256 $(sha256sum "$BASE_APK" | cut -d' ' -f1)"
  MERGED=$BASE_APK
  SMALI=$SMALI_BASE_ADD
fi

echo "== 2. apktool decode (smali day du)"
$J -jar "$APKTOOL" d -f -o "$D" "$MERGED" > "$LOGS/decode.log" 2>&1

echo "== 3. va smali: nhom $SMALI"
$PY scripts/patch_smali.py "$D" --group "$SMALI" | tee "$LOGS/patch_smali.log"

if [ -n "$SPLITS" ]; then
  echo "== 3a. manifest: go quyen mang"
  $PY scripts/patch_manifest.py "$D/AndroidManifest.xml" | tee "$LOGS/patch_manifest.log"
  echo "== 3b. text UI res/xml"
  $PY scripts/plist_text.py apply "$D/res/xml" dich/xml_text.tsv | tee "$LOGS/plist_text.log"

  echo "== 3c. text tieng Viet -> moi slot memorytext"
  G=$D/assets/common/game_res
  $PY scripts/memtext.py unwrap "$G/memorytext_en.dat.jpg" "$W/en.dat"
  $PY scripts/memtext.py check "$W/en.dat" dich/text_can_dich.tsv --charset font/charset.txt | tail -1
  $PY scripts/memtext.py repack "$W/en.dat" dich/text_can_dich.tsv "$W/vi.dat"
  $PY scripts/memtext.py wrap "$W/vi.dat" "$W/vi.dat.jpg"
  for f in "$G"/memorytext.dat.jpg "$G"/memorytext_{en,de,fr,jp,zhhans,zhhant}.dat.jpg; do cp "$W/vi.dat.jpg" "$f"; done

  echo "== 3d. anh co chu: ve lai nhan bang f18 (res_vi/labels.json)"
  mkdir -p "$W/img_goc" "$W/img_vi"
  for n in $($PY -c "import json;print(' '.join(k for k in json.load(open('res_vi/labels.json')) if not k.startswith('_')))"); do
    # da so la PNG tran (duoi .jpg gia); neu la container 01 00 thi giai nen
    if [ "$(head -c 2 "$G/$n.jpg" | od -An -tx1 | tr -d ' ')" = "0100" ]; then
      $PY scripts/memtext.py unwrap "$G/$n.jpg" "$W/img_goc/$n" >/dev/null; echo "$n" >> "$W/img_goc/.lzma"
    else cp "$G/$n.jpg" "$W/img_goc/$n"; fi
  done
  $PY scripts/img_vi.py res_vi/labels.json "$W/img_goc" "$W/img_vi" --preview "$LOGS/anh_vi_preview.png" > "$LOGS/img_vi.log"
  for f in "$W"/img_vi/*.png; do n=$(basename "$f")
    if grep -qx "$n" "$W/img_goc/.lzma" 2>/dev/null; then $PY scripts/memtext.py wrap "$f" "$G/$n.jpg" >/dev/null; else cp "$f" "$G/$n.jpg"; fi
  done
  echo "anh: $(ls "$W"/img_vi | wc -l) file"
else
  echo "== 3a-3d. bo qua (APK nen da co manifest, text, anh): chi kiem nhanh"
  $PY - "$D" <<'PYEOF'
import os, sys
d = sys.argv[1]; m = open(os.path.join(d, 'AndroidManifest.xml'), encoding='utf-8').read()
bad = [p for p in ('android.permission.INTERNET', 'com.android.vending.BILLING') if p in m]
if bad: raise SystemExit('APK nen con quyen %s: day khong phai ban nen (TEST4)' % bad)
print('manifest nen: khong con INTERNET/BILLING')
PYEOF
fi

echo "== 4. com.namp.vh -> classes3.dex (javac --release 8, d8 --lib android.jar)"
mkdir -p "$W/vnf/stub" "$W/vnf/cls" "$W/vnf/dex"
$PY scripts/gen_fontdata.py font/f18vn.bin java/src/com/namp/vh/FontData.java
$PY scripts/gen_fontdata.py font/f24vn.bin java/src/com/namp/vh/FontData24.java --parts
$JAVAC -encoding UTF-8 --release 8 -nowarn -d "$W/vnf/stub" $(find java/stubs -name '*.java')
$JAVAC -encoding UTF-8 --release 8 -nowarn -cp "$W/vnf/stub:$ANDROID_JAR" -d "$W/vnf/cls" java/src/com/namp/vh/*.java
# chi dong goi com/namp/vh (stub KHONG vao dex); stub la --lib thu hai cho d8
$D8 --lib "$W/vnf/stub" --output "$W/vnf/dex" "$W"/vnf/cls/com/namp/vh/*.class
# APK nen (TEST4) mang san VNFont cu o smali_classes3 / classes3.dex: bo di, classes3.dex moi them sau apktool b (add_dex.py)
rm -rf "$D/smali_classes3" "$D/classes3.dex"

echo "== 5. va libgame.so: nhom $LIB_GROUPS"
LIBF=$D/lib/arm64-v8a/libgame.so
cp "$LIBF" "$W/libgame_orig.so"
$PY scripts/patch_lib.py "$LIBF" "$W/libgame_patched.so" --group "$LIB_GROUPS" | tee "$LOGS/patch_lib.log"
$PY scripts/patch_lib.py --diff "$LIBF" "$W/libgame_patched.so" --group "$LIB_GROUPS" | tee "$LOGS/patch_lib_diff.log"
cp "$W/libgame_patched.so" "$LIBF"

echo "== 6. thay nut HUD (scripts/hud_swap.py -> UI_014.png.jpg, UI_015.png.jpg)"
GR=$D/assets/common/game_res
$PY scripts/hud_swap.py --selftest "$GR" --lib "$W/libgame_orig.so" | tee "$LOGS/hud_selftest.log" | tail -1
grep -q "KET LUAN: TAT CA DAT" "$LOGS/hud_selftest.log" || { echo "hud_swap --selftest KHONG DAT"; exit 1; }
$PY scripts/hud_swap.py "$GR" "$W/hud_out" --lib "$W/libgame_orig.so" | tee "$LOGS/hud_swap.log"
cp "$W/hud_out/UI_014.png.jpg" "$GR/UI_014.png.jpg"; cp "$W/hud_out/UI_015.png.jpg" "$GR/UI_015.png.jpg"

if [ $DIAG = 1 ]; then
  echo "== 7. chan doan com.namp.vh.diag -> classes4.dex + manifest"
  mkdir -p "$W/diag/cls" "$W/diag/dex"
  $JAVAC -encoding UTF-8 --release 8 -nowarn -cp "$ANDROID_JAR" -d "$W/diag/cls" java/diag/com/namp/vh/diag/*.java
  $D8 --output "$W/diag/dex" $(find "$W/diag/cls" -name '*.class')
  $PY scripts/patch_diag_manifest.py "$D/AndroidManifest.xml" ${MAN_OPTS[@]+"${MAN_OPTS[@]}"}
else
  echo "== 7. KHONG chan doan (ban phat hanh): LAUNCHER giu MainActivity, khong classes4.dex"
fi

echo "== 8. apktool build + them dex + can zip"
$J -jar "$APKTOOL" b -o "$W/unsigned.apk" "$D" > "$LOGS/build.log" 2>&1
$PY scripts/add_dex.py "$W/unsigned.apk" "$W/vnf/dex/classes.dex" classes3.dex
if [ $DIAG = 1 ]; then $PY scripts/add_dex.py "$W/unsigned.apk" "$W/diag/dex/classes.dex" classes4.dex; fi
$PY scripts/fix_zip.py "$W/unsigned.apk" "$W/aligned.apk"
"$BT/zipalign" -c -P 16 4 "$W/aligned.apk" && echo "zipalign -c -P 16 4: OK"

echo "== 9. ky v1/v2/v3 (apksigner)"
if [ ! -f "$KS" ]; then
  echo "chua co keystore -> tao moi $KS (KHONG commit; giu lai de ban cap nhat cai de duoc)"
  mkdir -p "$(dirname "$KS")"
  env -u JAVA_TOOL_OPTIONS keytool -genkeypair -keystore "$KS" -alias namp -keyalg RSA -keysize 2048 \
     -validity 20000 -storepass "$KSPASS" -keypass "$KSPASS" -dname "CN=NAMP Viet hoa, O=NAMP, C=VN"
fi
$J -jar "$BT/lib/apksigner.jar" sign --ks "$KS" --ks-key-alias namp --ks-pass "pass:$KSPASS" --key-pass "pass:$KSPASS" \
   --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true --out "$OUT" "$W/aligned.apk"
rm -f "$OUT.idsig"

echo "== 10. kiem tra"
$J -jar "$BT/lib/apksigner.jar" verify "$OUT" && echo "apksigner verify: OK"
$J -jar "$BT/lib/apksigner.jar" verify --print-certs "$OUT" | grep -E "SHA-256 digest" | head -1
$PY scripts/check_apk.py "$OUT" | tee "$LOGS/check_apk.log"
$PY - "$OUT" "$W/libgame_patched.so" "$W/hud_out" <<'PYEOF'
import sys, os, zipfile, hashlib
z = zipfile.ZipFile(sys.argv[1]); ok = True
got = z.read('lib/arm64-v8a/libgame.so'); want = open(sys.argv[2], 'rb').read()
print('libgame.so trong APK == ban da va:', got == want, hashlib.sha256(got).hexdigest()); ok = ok and got == want
for n in ('UI_014.png.jpg', 'UI_015.png.jpg'):
    got = z.read('assets/common/game_res/' + n); want = open(os.path.join(sys.argv[3], n), 'rb').read()
    print('%s trong APK == ban hud_swap: %s %s' % (n, got == want, hashlib.sha256(got).hexdigest())); ok = ok and got == want
names = z.namelist()
print('classes3.dex (com.namp.vh):', 'classes3.dex' in names, '| classes4.dex (diag):', 'classes4.dex' in names)
sys.exit(0 if ok else 1)
PYEOF
ls -la "$OUT"; echo "sha256 $(sha256sum "$OUT" | cut -d' ' -f1)"
