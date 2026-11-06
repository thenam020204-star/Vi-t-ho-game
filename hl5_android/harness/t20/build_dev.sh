#!/bin/bash
# T20: dựng .so dev vào $WORK/t16/<tên> (WORK mặc định ~/hl5work_t20) từ BẢN SAO engine/ của repo.
#   build_dev.sh dev              : cây hiện tại (có fbcopy.c + hook T20)
#   build_dev.sh base --nofbcopy  : cùng cây, bỏ fbcopy.c + mọi dòng nhãn "# T20" (đối chứng trước T20; vẫn có interp T18)
# KHÔNG đụng so_patches.tsv / build_apk / engine/out của repo. Dựa trên harness/t18/build_dev.sh.
set -e
HERE="$(cd "$(dirname "$0")" && pwd)"; REPO="$(cd "$HERE/../.." && pwd)"
: "${WORK:=$HOME/hl5work_t20}"; export WORK
NAME="${1:-dev}"; NOF=0; [ "$2" = "--nofbcopy" ] && NOF=1
S="$WORK/t18src/$NAME"; rm -rf "$S"; mkdir -p "$S"
cp -r "$REPO/engine" "$S/engine"; mkdir -p "$S/patch"; cp -r "$REPO/patch/." "$S/patch/"; for d in ui_new vi tools harness; do ln -s "$REPO/$d" "$S/$d"; done
if [ $NOF = 1 ]; then
  rm -f "$S/engine/menu/fbcopy.c"
  sed -i '/# T20/d' "$S/engine/menu/hooks.def" "$S/engine/menu/build.sh"
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
