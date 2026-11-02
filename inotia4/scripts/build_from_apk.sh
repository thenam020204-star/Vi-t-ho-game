#!/usr/bin/env bash
# Dung APK tu mot APK da build san (vd test_builds/Inotia4_TEST4_vi_anh_offline.apk) khi khong co bo split goc:
#   bash scripts/build_from_apk.sh <base.apk> <out.apk> [--smali nhom1,nhom2] [--vnfont] [--lib [--libgroup g1,g2]] [--hud] [--diag] [--internet]
#   --smali  : decode smali day du, ap scripts/patch_smali.py --group <nhom> (vd. net);
#              khong co --smali: apktool -s, giu nguyen tung byte moi dex cua base;
#   --vnfont : bien dich lai com.namp.vh (VNFont/FontData/FontData24/Offline/ModMenu/ModMem) tu java/src -> thay classes3.dex
#              (javac can android.jar cho ModMenu). Nhom smali `mod` BAT BUOC di cung --vnfont (ModMenu nam trong classes3.dex);
#   --lib    : va lib/arm64-v8a/libgame.so bang scripts/patch_lib.py (patch nao lib nen da co thi bao "da ap"),
#              giu nguyen kich thuoc/STORED/can 16 KB (fix_zip.py lo phan can);
#              mac dinh ap cac nhom base + p1 + mod + view + hud + pool (KHONG gom hudbtn); --libgroup base,p1 de ra y het TEST8/TEST9,
#              --libgroup base,p1,mod = TEST10; ban T9/TEST11 = base,p1,mod,view,hud,pool (view/hud/pool: docs/tam_nhin_hud.md); ban T10 = ...,hudbtn + --hud;
#   --hud    : (T10) thay anh nut HUD: scripts/hud_swap.py dan 7 anh assets_user/hud/ vao assets/common/game_res/UI_014.png.jpg + UI_015.png.jpg
#              (kiem SHA256 atlas goc va anh nguoi dung; doi chieu 7 rect voi bang loc cua libgame.so truoc khi dan). Phai di cung --lib va nhom
#              lib `hudbtn` (--libgroup ...,hudbtn: 3 lenh kieu ve `mov w5,#2` -> `#1` cho lop "tan cong bam"), va nguoc lai; xem docs/hud_thay_nut.md.
#   --diag   : them classes4.dex = com.namp.vh.diag (LaunchActivity, DiagProvider, CrashLog) + sua manifest;
#   --internet: tra lai quyen INTERNET (chi de thu, ban phat hanh KHONG dung).
# Can: APKTOOL (jar), BT (build-tools co d8/apksigner/zipalign), ANDROID_JAR (android.jar API >= 30).
set -euo pipefail
cd "$(dirname "$0")/.."
BASE=$(realpath "$1"); OUT=$2; shift 2
MAN_OPTS=(); SMALI=""; VNFONT=0; DIAG=0; LIB=0; LIBGROUP=""; HUD=0
while [ $# -gt 0 ]; do
  case "$1" in
    --internet) MAN_OPTS+=(--internet);;
    --smali) SMALI=$2; shift;;
    --vnfont) VNFONT=1;;
    --lib) LIB=1;;
    --libgroup) LIBGROUP=$2; shift;;
    --hud) HUD=1;;
    --diag) DIAG=1;;
    *) echo "tham so la: $1"; exit 2;;
  esac; shift
done
if [ ${#MAN_OPTS[@]} -gt 0 ] && [ $DIAG = 0 ]; then echo "--internet chi dung cung --diag"; exit 2; fi
if [ -n "$LIBGROUP" ] && [ $LIB = 0 ]; then echo "--libgroup chi dung cung --lib"; exit 2; fi
# --hud (atlas moi) va nhom lib `hudbtn` (kieu ve cong -> thuong cho lop "tan cong bam") phai di cung nhau
case ",$LIBGROUP," in *,hudbtn,*) HUDBTN=1;; *) HUDBTN=0;; esac
if [ $HUD = 1 ] && { [ $LIB = 0 ] || [ $HUDBTN = 0 ]; }; then echo "--hud can --lib --libgroup <...>,hudbtn (nhom hudbtn khong nam trong nhom mac dinh)"; exit 2; fi
if [ $HUD = 0 ] && [ $HUDBTN = 1 ]; then echo "nhom lib hudbtn chi dung cung --hud (atlas HUD moi)"; exit 2; fi
case ",$SMALI," in *,mod,*)
  if [ $VNFONT = 0 ]; then echo "nhom smali 'mod' goi com.namp.vh.ModMenu (classes3.dex) => can --vnfont"; exit 2; fi
  if [ $LIB = 0 ]; then echo "CANH BAO: nhom smali 'mod' ma khong co --lib: nut MOD se ghi vao vung nho khong ai doc" >&2; fi;;
esac
APKTOOL=${APKTOOL:-tools/apktool_2.12.1.jar}
BT=${BT:-/opt/android/build-tools/35.0.0}
ANDROID_JAR=${ANDROID_JAR:-/opt/android/platforms/android-34/android.jar}
KS=${INOTIA_KS:-keystore/namp-inotia4.jks}; KSPASS=${INOTIA_KS_PASS:-namp-inotia4}
J="env -u JAVA_TOOL_OPTIONS java"
D8="$J -cp $BT/lib/d8.jar com.android.tools.r8.D8 --release --min-api 23 --lib $ANDROID_JAR"
W=work/fromapk; rm -rf "$W"; mkdir -p "$W"

if [ -z "$SMALI" ]; then
  echo "== 1. apktool d -s (giu nguyen dex)"
  $J -jar "$APKTOOL" d -f -s -o "$W/dec" "$BASE" > "$W/decode.log" 2>&1
else
  echo "== 1. apktool d (smali) + va nhom $SMALI"
  $J -jar "$APKTOOL" d -f -o "$W/dec" "$BASE" > "$W/decode.log" 2>&1
  python3 -I scripts/patch_smali.py "$W/dec" --group "$SMALI"
fi

if [ $VNFONT = 1 ]; then
  echo "== 2. VNFont -> classes3.dex (javac --release 8, d8 --lib android.jar)"
  mkdir -p "$W/vnf/stub" "$W/vnf/cls" "$W/vnf/dex"
  python3 -I scripts/gen_fontdata.py font/f18vn.bin java/src/com/namp/vh/FontData.java
  python3 -I scripts/gen_fontdata.py font/f24vn.bin java/src/com/namp/vh/FontData24.java --parts
  env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -d "$W/vnf/stub" $(find java/stubs -name '*.java')
  env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -cp "$W/vnf/stub:$ANDROID_JAR" -d "$W/vnf/cls" java/src/com/namp/vh/*.java
  $D8 --lib "$W/vnf/stub" --output "$W/vnf/dex" "$W"/vnf/cls/com/namp/vh/*.class
  rm -rf "$W/dec/smali_classes3"; cp "$W/vnf/dex/classes.dex" "$W/dec/classes3.dex"
fi

if [ $LIB = 1 ]; then
  echo "== 2b. va libgame.so (scripts/patch_lib.py)"
  LIBF="$W/dec/lib/arm64-v8a/libgame.so"
  LIBORIG="$W/libgame_orig.so"; cp "$LIBF" "$LIBORIG"
  python3 -I scripts/patch_lib.py "$LIBF" "$W/libgame_patched.so" ${LIBGROUP:+--group "$LIBGROUP"} | tee "$W/patch_lib.log"
  python3 -I scripts/patch_lib.py --diff "$LIBF" "$W/libgame_patched.so" ${LIBGROUP:+--group "$LIBGROUP"} | tee "$W/patch_lib_diff.log"
  cp "$W/libgame_patched.so" "$LIBF"
fi

if [ $HUD = 1 ]; then
  echo "== 2c. thay nut HUD (scripts/hud_swap.py -> UI_014.png.jpg, UI_015.png.jpg)"
  GR="$W/dec/assets/common/game_res"
  python3 -I scripts/hud_swap.py --selftest "$GR" --lib "$LIBORIG" | tee "$W/hud_selftest.log" | tail -1
  grep -q "KET LUAN: TAT CA DAT" "$W/hud_selftest.log" || { echo "hud_swap --selftest KHONG DAT"; exit 1; }
  python3 -I scripts/hud_swap.py "$GR" "$W/hud_out" --lib "$LIBORIG" | tee "$W/hud_swap.log"
  cp "$W/hud_out/UI_014.png.jpg" "$GR/UI_014.png.jpg"; cp "$W/hud_out/UI_015.png.jpg" "$GR/UI_015.png.jpg"
fi

if [ $DIAG = 1 ]; then
  echo "== 3. chan doan com.namp.vh.diag -> classes4.dex + manifest"
  mkdir -p "$W/diag/cls" "$W/diag/dex"
  env -u JAVA_TOOL_OPTIONS javac -encoding UTF-8 --release 8 -nowarn -cp "$ANDROID_JAR" -d "$W/diag/cls" java/diag/com/namp/vh/diag/*.java
  $D8 --output "$W/diag/dex" $(find "$W/diag/cls" -name '*.class')
  python3 -I scripts/patch_diag_manifest.py "$W/dec/AndroidManifest.xml" "${MAN_OPTS[@]}"
fi

echo "== 4. apktool b + can zip"
$J -jar "$APKTOOL" b -o "$W/unsigned.apk" "$W/dec" > "$W/build.log" 2>&1
if [ $DIAG = 1 ]; then python3 -I scripts/add_dex.py "$W/unsigned.apk" "$W/diag/dex/classes.dex" classes4.dex; fi
python3 -I scripts/fix_zip.py "$W/unsigned.apk" "$W/aligned.apk"
"$BT/zipalign" -c -P 16 4 "$W/aligned.apk" && echo "zipalign -c: OK"

echo "== 5. ky v1/v2/v3 (keystore NAMP, cai de duoc len ban TEST truoc)"
$J -jar "$BT/lib/apksigner.jar" sign --ks "$KS" --ks-key-alias namp --ks-pass "pass:$KSPASS" --key-pass "pass:$KSPASS" \
   --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true --out "$OUT" "$W/aligned.apk"
rm -f "$OUT.idsig"
$J -jar "$BT/lib/apksigner.jar" verify "$OUT" && echo "apksigner verify: OK"
python3 -I scripts/check_apk.py "$OUT"
if [ $LIB = 1 ]; then
  python3 -I - "$OUT" "$W/libgame_patched.so" <<'PYEOF'
import sys, zipfile, hashlib
got = zipfile.ZipFile(sys.argv[1]).read('lib/arm64-v8a/libgame.so')
want = open(sys.argv[2], 'rb').read()
print('libgame.so trong APK == ban da va:', got == want, hashlib.sha256(got).hexdigest())
sys.exit(0 if got == want else 1)
PYEOF
fi
if [ $HUD = 1 ]; then
  python3 -I - "$OUT" "$W/hud_out" <<'PYEOF'
import sys, os, zipfile, hashlib
z = zipfile.ZipFile(sys.argv[1]); ok = True
for n in ('UI_014.png.jpg', 'UI_015.png.jpg'):
    got = z.read('assets/common/game_res/' + n); want = open(os.path.join(sys.argv[2], n), 'rb').read()
    print('%s trong APK == ban hud_swap: %s %s (%s)' % (n, got == want, hashlib.sha256(got).hexdigest(), 'STORED' if z.getinfo('assets/common/game_res/' + n).compress_type == 0 else 'DEFLATED'))
    ok = ok and got == want
sys.exit(0 if ok else 1)
PYEOF
fi
ls -la "$OUT"
