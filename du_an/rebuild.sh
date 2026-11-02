#!/bin/bash
# Dung lai toan bo ban Viet hoa tu jar goc:  bash rebuild.sh [--no-emu]
#   goc/game.jar -> giai nen jar_x/ -> sinh tai nguyen (res_vi/) -> patch bytecode -> dong jar -> preverify
#   -> jar_check -> out/ (jar + jad) -> chay thu FreeJ2ME headless (logs/rebuild_emu/)
# Can: bash, JDK 9+ day du (javac, jmod, jmods/java.base.jmod), python3 + Pillow (+ opencv-python-headless,
#      numpy cho ban do), zip/unzip; chay thu can Xvfb, ImageMagick, python-xlib.
set -e -o pipefail
cd "$(dirname "$0")"
mkdir -p work logs out
ln -sfn ../tools work/tools
ln -sfn ../tools/lib work/lib
JH=$(dirname "$(dirname "$(readlink -f "$(which javac)")")")
[ -d work/jbase ] || jmod extract --dir work/jbase "$JH/jmods/java.base.jmod"
rm -rf jar_x && mkdir jar_x && (cd jar_x && unzip -q ../goc/game.jar)
cp goc/game.jar game.jar
./make_res.sh 2>&1 | tee logs/make_res.log
./build.sh 2>&1 | tee logs/build.log
cp build/jar_check.txt logs/jar_check.txt
cp build/patch.log logs/patch.log
grep -q "RESULT: PASS" logs/jar_check.txt || { echo "jar_check KHONG PASS"; exit 1; }
NAME="Di_The_Ta_Quan_VH_240x320"
cp out_game.jar out/$NAME.jar
SIZE=$(stat -c %s out/$NAME.jar)
python3 - "$SIZE" "$NAME" <<'PY'
import sys
size, name = sys.argv[1], sys.argv[2]
m = open('manifest/MANIFEST.MF', encoding='utf-8').read().splitlines()
keep = [l for l in m if l.split(':')[0] in ('MIDlet-Name', 'MIDlet-Vendor', 'MIDlet-Version', 'MIDlet-1', 'MIDlet-Icon',
                                           'MicroEdition-Configuration', 'MicroEdition-Profile')]
keep += ['MIDlet-Jar-URL: %s.jar' % name, 'MIDlet-Jar-Size: %s' % size]
open('out/%s.jad' % name, 'w', encoding='utf-8').write('\n'.join(keep) + '\n')
PY
cp dich/text_da_dich.tsv dich/dp_work/thuat-ngu.tsv dich/dp_work/vai.md out/
echo "OUT: out/$NAME.jar ($SIZE byte) + out/$NAME.jad"
if [ "$1" != "--no-emu" ]; then
  rm -rf logs/rebuild_emu rms
  python3 scripts/run_emu.py --fj tools/freej2me_plus.jar --jar out/$NAME.jar --out logs/rebuild_emu \
    --steps "wait 3; shot logo; wait 9; shot menu; key Return; wait 6; shot intro1; key Return; wait 2; key Return; wait 2; shot intro2" \
    | tee logs/rebuild_emu.json
fi
