#!/bin/bash
# T18b: dựng .so dev (vá v0.2-hd + engine/menu hiện tại) vào $WORK/t16/<tên> (mặc định dev) để chạy harness T18 (t14lib.mk đọc $WORK/t16/$HUD_DEV).
# Dùng: WORK=~/hl5work_t18 build_dev.sh [tên] [--noimterp]   --nointerp: bỏ interp.c + các dòng hooks.def có nhãn T18 (bản đối chứng "cờ không tồn tại")
# KHÔNG đụng so_patches.tsv / build_apk. Tạo bản sao engine/ trong $WORK/t18src để build (không ghi vào engine/out của repo).
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"; REPO="$(cd "$HERE/../.." && pwd)"
: "${WORK:=$HOME/hl5work_t18}"; export WORK
NAME="${1:-dev}"; NOI=0; [ "$2" = "--nointerp" ] && NOI=1
S="$WORK/t18src/$NAME"; rm -rf "$S"; mkdir -p "$S"
cp -r "$REPO/engine" "$S/engine"; mkdir -p "$S/patch"; cp -r "$REPO/patch/." "$S/patch/"; for d in ui_new vi tools harness; do ln -s "$REPO/$d" "$S/$d"; done
if [ $NOI = 1 ]; then
  rm -f "$S/engine/menu/interp.c" "$S/engine/menu/interp.h"
  sed -i '/T18/d' "$S/engine/menu/hooks.def"
  sed -i 's/ interp\b//; s/for f in interp; do.*$//' "$S/engine/menu/build.sh"
fi
D="$WORK/t16/$NAME"; mkdir -p "$D"
(cd "$S/engine" && sh build.sh >/dev/null)
"$S/engine/menu/build.sh" "$D/menu"
python3 - "$REPO/patch/so_patches.tsv" "$D/sp.tsv" <<'PY'
import sys
L = open(sys.argv[1], encoding='utf-8').read().split('\n'); out = []; skip = False
for l in L:
    if l.startswith('# >>> T17c menu-vtable'): skip = True
    if not skip: out.append(l)
    if l.startswith('# <<< T17c menu-vtable'): skip = False
open(sys.argv[2], 'w', encoding='utf-8').write('\n'.join(out))
PY
python3 -I "$S/patch/apply_so.py" "$WORK/apk/assets/libHeroesLore5.so" "$D/p.so" "$D/sp.tsv" >/dev/null
python3 -I "$S/engine/menu/inject_menu.py" "$D/p.so" "$D/menu"
python3 -I "$S/patch/apply_so.py" "$D/p.so" "$D/p2.so" "$D/menu/menu_patches.tsv" >/dev/null
python3 -I "$S/patch/mk_menu_segment.py" "$D/p2.so" "$D/dev.so" "$D/menu"
if [ ! -f "$D/data.vfs.mp3" ]; then
  python3 "$S/engine/mk_vibin.py" "$WORK/vi_android" "$D/vi.bin"
  python3 "$REPO/tools/vfs_pack.py" "$WORK/vi_android/data.vfs.mp3" "$D/data.vfs.mp3" --replace "$WORK/build_apk/ovl" --add c/vi/vi.bin="$D/vi.bin" >/dev/null
fi
echo "dev .so: $D/dev.so"
