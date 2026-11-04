#!/bin/bash
# T16: dựng nhanh libHeroesLore5 (vá v0.2-hd + engine/menu hiện tại) vào $WORK/t16/dev để chạy harness (không đụng $WORK/build_apk, không đụng so_patches.tsv).
# Dùng: build_dev.sh [--debug]    Ra: dev/dev.so, dev/data.vfs.mp3 (VFS có vi.bin: lấy từ $WORK/t17c0 nếu chưa có thì dựng)
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"; REPO="$(cd "$HERE/../.." && pwd)"
: "${WORK:=$HOME/hl5work_t16}"; export WORK
D="$WORK/t16/dev"; mkdir -p "$D"
DBG=""; [ "$1" = "--debug" ] && DBG="--debug"
(cd "$REPO/engine" && sh build.sh >/dev/null)
"$REPO/engine/menu/build.sh" "$D/menu" $DBG
python3 - "$REPO/patch/so_patches.tsv" "$D/sp.tsv" <<'PY'
import sys
L = open(sys.argv[1], encoding='utf-8').read().split('\n'); out = []; skip = False
for l in L:
    if l.startswith('# >>> T17c menu-vtable'): skip = True
    if not skip: out.append(l)
    if l.startswith('# <<< T17c menu-vtable'): skip = False
open(sys.argv[2], 'w', encoding='utf-8').write('\n'.join(out))
PY
python3 -I "$REPO/patch/apply_so.py" "$WORK/apk/assets/libHeroesLore5.so" "$D/p.so" "$D/sp.tsv" >/dev/null
python3 -I "$REPO/engine/menu/inject_menu.py" "$D/p.so" "$D/menu"
python3 -I "$REPO/patch/apply_so.py" "$D/p.so" "$D/p2.so" "$D/menu/menu_patches.tsv" >/dev/null
python3 -I "$REPO/patch/mk_menu_segment.py" "$D/p2.so" "$D/dev.so" "$D/menu"
if [ ! -f "$D/data.vfs.mp3" ]; then
  python3 "$REPO/engine/mk_vibin.py" "$WORK/vi_android" "$D/vi.bin"
  python3 "$REPO/tools/vfs_pack.py" "$WORK/vi_android/data.vfs.mp3" "$D/data.vfs.mp3" --replace "$WORK/build_apk/ovl" --add c/vi/vi.bin="$D/vi.bin" >/dev/null
fi
