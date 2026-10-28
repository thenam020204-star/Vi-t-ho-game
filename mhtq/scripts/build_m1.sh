#!/usr/bin/env bash
# Dựng lại Mốc 1 (hook BitmapFont f13c + vài chuỗi Việt thử): chạy từ bất kỳ đâu.
#   bash mhtq/scripts/build_m1.sh [--no-shots]
set -e
R="$(cd "$(dirname "$0")/.." && pwd)"
cd "$R"
source work/tools/env.sh
python3 -I scripts/text_extract.py --merge work/text_can_dich.tsv > /dev/null   # TSV gốc (giữ bản dịch nếu có)
python3 -I scripts/m1_make_tsv.py
python3 -I scripts/text_pack.py --tsv work/build/m1_test.tsv --out work/build/m1_text
python3 -I scripts/patch_font.py --overlay work/build/m1_text --out-jar work/build/MHTQ_K790_moc1.jar
if [ "$1" != "--no-shots" ]; then
  rm -rf logs/shots_m1; mkdir -p logs/shots_m1
  python3 -I scripts/run_emu.py --fj "$FJ" --jar work/build/MHTQ_K790_moc1.jar --out logs/shots_m1 --size 240x320 \
    --steps "wait 16; shot 01_nhac; key w; wait 14; shot 02_menu_chinh; key Return; wait 6; shot 03_thoai_1; wait 4; shot 04_thoai_2"
  python3 -I scripts/run_emu.py --fj "$FJ" --jar work/build/MHTQ_K790_moc1.jar --out logs/shots_m1/h --size 240x320 \
    --steps "wait 12; key w; wait 14; key Right 2; wait 1; key Return; wait 3; shot 06_tro_giup; key Down 3; wait 2; shot 07_tro_giup_cuon"
  mv logs/shots_m1/h/06_tro_giup.png logs/shots_m1/h/07_tro_giup_cuon.png logs/shots_m1/
  mv logs/shots_m1/h/emu.log logs/shots_m1/emu_help.log; rmdir logs/shots_m1/h
fi
python3 -I -c "
from PIL import Image
d='logs/shots_m1/'
ims=[Image.open(d+n+'.png') for n in ('01_nhac','02_menu_chinh','04_thoai_2','07_tro_giup_cuon')]
s=Image.new('RGB',(960,315))
[s.paste(i,(k*240,0)) for k,i in enumerate(ims)]
s.save(d+'tong_hop.png')"
mkdir -p out
rm -rf work/build/m1_zip && mkdir -p work/build/m1_zip/anh
cp work/build/MHTQ_K790_moc1.jar work/build/MHTQ_K790_moc1.jad work/build/m1_zip/
cp logs/shots_m1/*.png work/build/m1_zip/anh/ 2>/dev/null || true
cp src/patch/BAO_CAO_M1.md work/build/m1_zip/BAO_CAO.md 2>/dev/null || true
(cd work/build/m1_zip && rm -f "$R/out/MHTQ_K790_moc1.zip" && python3 -I -c "
import zipfile,os
z=zipfile.ZipFile('$R/out/MHTQ_K790_moc1.zip','w',zipfile.ZIP_DEFLATED)
for dp,_,fs in os.walk('.'):
    for f in sorted(fs): z.write(os.path.join(dp,f),os.path.join(dp,f)[2:])
z.close()")
ls -la out
