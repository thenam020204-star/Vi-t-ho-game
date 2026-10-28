#!/bin/bash
# Dung lai TOAN BO tu jar goc: goc/ -> repack text -> patch class (ASM) -> VH + font -> preverify -> jar_check -> jad (-> emu).
#   ./rebuild.sh            dung sach (xoa build/ va jar/jad cu) ra out/TienSungKyDuyen_{240x320,320x240}_vi.{jar,jad}
#   RUN_EMU=1 ./rebuild.sh  them buoc chay FreeJ2ME (logo -> menu -> CG) cho tung ban, anh vao out/emu_rebuild/
#   TSV=file.tsv ./rebuild.sh   dung ban dich khac (mac dinh dich/dp_work/final/text_can_dich.tsv)
# Can: JDK day du (javac, jmod), python3 + Pillow (+ python-xlib, Xvfb, ImageMagick neu RUN_EMU=1), zip/unzip.
# Tool: tools/ (neu co, ban nen goi) hoac work/tools/ (xem tools/VERSIONS.md); thu vien ASM/ecj o <tools>/lib.
set -e -o pipefail
cd "$(dirname "$0")"
source scripts/env.sh
rm -rf build out/*.jar out/*.jad
mkdir -p out logs
./build.sh
for v in 240x320 320x240; do python3 scripts/make_jad.py out/TienSungKyDuyen_${v}_vi.jar out/TienSungKyDuyen_${v}_vi.jad; done
python3 scripts/make_jad.py goc/TienSungKyDuyen_240x320.jar goc/TienSungKyDuyen_240x320.jad >/dev/null
if [ "${RUN_EMU:-0}" = 1 ]; then
  mkdir -p out/emu_rebuild
  for v in 240x320 320x240; do
    python3 scripts/run_emu.py --fj "$FJ" --jar out/TienSungKyDuyen_${v}_vi.jar --out out/emu_rebuild/$v --size $v \
      --steps "shot logo; wait 4; shot menu; key Return; wait 5; shot cg" | tee logs/run_emu_rebuild_$v.json | grep -i "exceptions" -A2 | head -4
  done
fi
sha256sum out/*.jar out/*.jad | tee logs/rebuild_sha256.txt
echo "REBUILD OK"
