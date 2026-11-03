#!/bin/bash
# Dựng HL5-VI APK từ $WORK (end-to-end, tái lập). Dùng: WORK=... [ORIG_APK=...] ./make_apk.sh [VERSION=0.1]
# Cần: python3 (pyelftools), zipalign/apksigner (build-tools 34), sign/hl5vi.keystore (+ sign/README.md).
set -euo pipefail
HERE="$(cd "$(dirname "$0")" && pwd)"
: "${WORK:=$HOME/hl5work}"
export WORK
DIAG=""   # --diag A|B|C|C1 : APK chẩn đoán T13 (xem diag/ và PHAT-HANH-diag.md); mặc định rỗng = build v0.1 y hệt cũ
PTL=0     # --test-ptload : T17p, thêm đoạn PT_LOAD thử vào guest .so (chỉ dùng được với --diag C hoặc bản thường); ra dist/test/
while [ $# -gt 0 ]; do
  case "$1" in
    --diag) DIAG="${2:?--diag cần A|B|C|C1}"; shift 2;;
    --test-ptload) PTL=1; shift;;
    *) break;;
  esac
done
if [ "$PTL" = 1 ]; then case "$DIAG" in ""|C) ;; *) echo "--test-ptload chỉ đi với --diag C (hoặc không diag)"; exit 1;; esac; fi
case "$DIAG" in ""|A|B|C|C1) ;; *) echo "--diag phải là A|B|C|C1"; exit 1;; esac
VER="${1:-0.2-hd}"   # v0.2-hd: có patch T9 (texture > 1024); v0.1.1 = commit trước T9
BT=${BT:-/opt/asdk/android-14}
ORIG_APK="${ORIG_APK:-$HERE/env/inputs/heroes-lore-5-v1.0.5.apk}"
[ -f "$WORK/orig.apk" ] || cp "$ORIG_APK" "$WORK/orig.apk"
B="$WORK/build_apk"; [ -n "$DIAG" ] && B="$WORK/build_diag_$DIAG"; [ "$PTL" = 1 ] && B="$WORK/build_ptload${DIAG:+_diag}"; rm -rf "$B"; mkdir -p "$B"
KS="$HERE/sign/hl5vi.keystore"
PW=$(sed -n 's/^- mật khẩu (store và key): //p' "$HERE/sign/README.md")
[ -n "$PW" ] && [ -f "$KS" ] || { echo "thiếu khoá ký"; exit 1; }

if [ "$DIAG" != "A" ]; then
echo "[1] vá guest .so + bridge arm64"
python3 -I "$HERE/patch/apply_so.py" "$WORK/apk/assets/libHeroesLore5.so" "$B/libHeroesLore5.so"
if [ "$PTL" != 1 ]; then   # T17c0: đoạn PT_LOAD thật của menu mới (engine/menu; bản thử T17p dùng cùng vaddr nên loại trừ nhau)
  echo "[1b] đoạn PT_LOAD menu (engine/menu/build.sh + inject_menu.py + mk_menu_segment.py)"
  WORK="$WORK" "$HERE/engine/menu/build.sh" "$B/menu" >"$B/menu_build.log" 2>&1 || { cat "$B/menu_build.log"; exit 1; }
  python3 -I "$HERE/engine/menu/inject_menu.py" "$B/libHeroesLore5.so" "$B/menu"
  python3 -I "$HERE/patch/apply_so.py" "$B/libHeroesLore5.so" "$B/libHeroesLore5.v.so" "$B/menu/menu_patches.tsv" >/dev/null
  python3 -I "$HERE/patch/mk_menu_segment.py" "$B/libHeroesLore5.v.so" "$B/libHeroesLore5.so" "$B/menu"
  rm -f "$B/libHeroesLore5.v.so"
fi
if [ "$PTL" = 1 ]; then   # T17p: + đoạn PT_LOAD thử (script tái lập, kiểm byte gốc)
  python3 -I "$HERE/patch/mk_ptload_test.py" "$B/libHeroesLore5.so" "$B/libHeroesLore5.ptl.so" "$B/ptload_test.tsv"
  mv "$B/libHeroesLore5.ptl.so" "$B/libHeroesLore5.so"
fi
if [ "$DIAG" != "B" ]; then   # B = giữ bridge gốc
BTSV="$HERE/patch/bridge_patches.tsv"
case "$DIAG" in
  C1) # bỏ NEAREST + viewport/chạm (e1e38 e1f84 cc6b8 e3770 cfdd0); giữ tắt hook chữ + logic ÷k
      BTSV="$B/bridge_C1.tsv"; grep -Ev '^(e1e38|e1f84|cc6b8|e3770|cfdd0)	' "$HERE/patch/bridge_patches.tsv" >"$BTSV";;
esac
python3 -I "$HERE/patch/apply_bridge.py" "$WORK/apk/lib/arm64-v8a/libhl5_arm64_bridge.so" "$B/bridge_arm64.so" "$BTSV"
fi

echo "[2] VFS Việt + vi.bin"
python3 "$HERE/vi/build_android_vi.py" >"$B/build_vi.log" 2>&1 || { tail "$B/build_vi.log"; exit 1; }
python3 "$HERE/engine/mk_vibin.py" "$WORK/vi_android" "$B/vi.bin"
python3 "$HERE/patch/t10_data.py" "$WORK/vi_android" "$B/ovl"   # T10: nhãn "Phân giải" -> common_text rec 210
python3 "$HERE/tools/vfs_pack.py" "$WORK/vi_android/data.vfs.mp3" "$B/data.vfs.mp3" --replace "$B/ovl" --add c/vi/vi.bin="$B/vi.bin"
fi
[ -n "$DIAG" ] && { echo "[2b] lớp chẩn đoán"; "$HERE/diag/build_diag.sh" "$WORK/orig.apk" "$B"; }

echo "[3-4] target-assets.json + zip (giữ thứ tự và kiểu nén của APK gốc)"
python3 -I - "$WORK/orig.apk" "$B" "$DIAG" <<'PY'
import sys, zipfile, hashlib, json
orig, B, diag = sys.argv[1:4]
rd = lambda n: open(f'{B}/{n}', 'rb').read()
new = {}
if diag != 'A':
    new = {'assets/libHeroesLore5.so': rd('libHeroesLore5.so'),
           'assets/data.vfs.mp3': rd('data.vfs.mp3')}
    if diag != 'B': new['lib/arm64-v8a/libhl5_arm64_bridge.so'] = rd('bridge_arm64.so')
extra = []
if diag:   # lớp chẩn đoán: classes.dex/classes2.dex đã chèn lời gọi, classes3.dex = Diag
    new['classes.dex'] = rd('classes.dex'); new['classes2.dex'] = rd('classes2.dex')
    extra = [('classes3.dex', rd('classes3.dex'), 8), ('assets/diag-variant.txt', ('diag-' + diag).encode(), 0)]
zi = zipfile.ZipFile(orig)
ta = json.loads(zi.read('assets/target-assets.json'))
for k in ta:   # TargetAssets: sha256 của 2 file asset
    if 'assets/' + k in new: ta[k] = hashlib.sha256(new['assets/' + k]).hexdigest()
if diag != 'A': new['assets/target-assets.json'] = json.dumps(ta).encode()
with zipfile.ZipFile(f'{B}/unsigned.apk', 'w') as zo:
    for i in zi.infolist():
        if i.filename.startswith('META-INF/'): continue
        zf = zipfile.ZipInfo(i.filename, (1980, 1, 1, 0, 0, 0))
        zf.compress_type = i.compress_type; zf.external_attr = i.external_attr; zf.create_system = 0
        zo.writestr(zf, new.get(i.filename, zi.read(i.filename)))
    for name, data, ct in extra:
        zf = zipfile.ZipInfo(name, (1980, 1, 1, 0, 0, 0)); zf.compress_type = ct; zf.external_attr = 0o100644 << 16; zf.create_system = 0
        zo.writestr(zf, data)
print(ta)
PY

echo "[5] zipalign + apksigner"
"$BT/zipalign" -f -p 4 "$B/unsigned.apk" "$B/aligned.apk"
OUT="$B/HL5-VI-v$VER.apk"
"$BT/apksigner" sign --ks "$KS" --ks-key-alias hl5vi --ks-pass pass:"$PW" --key-pass pass:"$PW" \
  --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true --out "$OUT" "$B/aligned.apk"
"$BT/apksigner" verify --verbose "$OUT" | head -8
"$BT/zipalign" -c -p 4 "$OUT" && echo "zipalign OK"
sha256sum "$OUT"; ls -l "$OUT"
if [ "$PTL" = 1 ]; then
  mkdir -p "$HERE/dist/test"
  N="HL5-test-ptload${DIAG:+-diag}.apk"; cp "$OUT" "$HERE/dist/test/$N"; echo "-> dist/test/$N"
elif [ -n "$DIAG" ]; then
  mkdir -p "$HERE/dist/diag"
  case "$DIAG" in C) N="HL5-diag-v$VER.apk";; *) N="HL5-diag-$DIAG-v$VER.apk";; esac   # C = bản đầy đủ + lớp chẩn đoán
  cp "$OUT" "$HERE/dist/diag/$N"; echo "-> dist/diag/$N"
else mkdir -p "$HERE/dist"; cp "$OUT" "$HERE/dist/HL5-VI-v$VER.apk"; echo "-> dist/HL5-VI-v$VER.apk"; fi
