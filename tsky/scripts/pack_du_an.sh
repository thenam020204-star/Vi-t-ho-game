#!/bin/bash
# Gom goi ban giao out/TienSungKyDuyen_du_an.zip (cau truc theo skill j2me-viet-hoa, muc "Ban giao").
set -e
cd "$(dirname "$0")/.."
source scripts/env.sh
N=TienSungKyDuyen_du_an
P=build/pack/$N
rm -rf build/pack && mkdir -p $P/out $P/goc/anh_goc $P/src $P/logs $P/tools
cp README_DU_AN.md PROGRESS.md BAO_CAO.md PLAN.md rebuild.sh build.sh build_hook.sh $P/
cp -r scripts hook patcher res_vi res_vi_320 docs $P/
rm -rf $P/scripts/__pycache__
mkdir -p $P/dich/dp_work/final && cp dich/text_can_dich.tsv dich/charset_f12.txt $P/dich/ && cp dich/dp_work/final/text_can_dich.tsv $P/dich/dp_work/final/ && cp dich/dp_work/thuat-ngu.tsv dich/dp_work/vai.md dich/dp_work/ghi-chu.txt $P/dich/dp_work/
# out/: jar + jad cuoi, tsv da dich, anh chup
cp out/TienSungKyDuyen_*_vi.jar out/TienSungKyDuyen_*_vi.jad $P/out/
cp dich/dp_work/final/text_can_dich.tsv $P/out/text_can_dich_da_dich.tsv
cp -r out/emu_rebuild $P/out/ 2>/dev/null || true
# goc/: jar, jad, tsv goc, anh goc cua cac file da ve lai
cp goc/*.jar goc/*.jad goc/*.png $P/goc/ 2>/dev/null || true
cp dich/text_can_dich.tsv $P/goc/text_can_dich_goc.tsv
python3 - <<PY
import os, zipfile
z = zipfile.ZipFile('goc/TienSungKyDuyen_240x320.jar')
names = set(z.namelist())
for d in ('res_vi', 'res_vi_320'):
    for r, _, fs in os.walk(d):
        for f in fs:
            rel = os.path.relpath(os.path.join(r, f), d).replace(os.sep, '/')
            if rel in names:
                o = os.path.join('$P/goc/anh_goc' + ('_320' if d.endswith('320') else ''), rel)
                os.makedirs(os.path.dirname(o), exist_ok=True)
                open(o, 'wb').write(z.read(rel))
PY
# src/: CFR cua cac class da vo
for c in Main/a Main/e Main/f Main/g Main/j Main/k rf/b rf/c rf/e; do mkdir -p $P/src/$(dirname $c); cp src/$c.java $P/src/$c.java; done
cp -r logs/* $P/logs/ 2>/dev/null; rm -rf $P/logs/shots_T4/*chuadich* 2>/dev/null || true
# tools/
T=$TOOLS
for x in fonts fontgen java pixeltext_kit lib; do cp -r $T/$x $P/tools/; done
cp $T/check_all.sh $T/freej2me_plus.jar $T/cfr.jar $T/proguard.jar $P/tools/
cp tools_VERSIONS.md $P/tools/VERSIONS.md
printf '# moi truong cua bo tool (tuong doi): source tools/env.sh\nsource "$(dirname "${BASH_SOURCE[0]:-$0}")/../scripts/env.sh"\n' > $P/tools/env.sh
find $P -name __pycache__ -type d -prune -exec rm -rf {} + ; rm -rf $P/tools/java/build_tmp
(cd build/pack && rm -f ../../out/$N.zip && zip -q -r -9 ../../out/$N.zip $N)
ls -l out/$N.zip
