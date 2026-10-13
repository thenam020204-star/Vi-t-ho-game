#!/bin/bash
# Sinh lai toan bo tai nguyen Viet hoa vao res_vi/ tu jar goc (jar_x/) + ban dich
set -e
cd "$(dirname "$0")"
DP="python3 $(realpath ${SKILL_SCRIPTS:-scripts_skill})/dp.py"
(cd dich && $DP check | tail -1 && $DP merge > /dev/null)
python3 -I scripts/apply_tl.py dich/dp_work/final/text_can_dich.tsv dich/overrides.tsv dich/charset_f12.txt dich/text_da_dich.tsv
python3 -I scripts/repack.py jar_x/string.pak dich/text_da_dich.tsv res_vi/string.pak
python3 -I scripts/make_menu_strip.py jar_x/splashAnim jar_x/anim.bin build/menu_strip.png build/anim_s1.bin > /dev/null
python3 -I scripts/make_splash.py jar_x/splashAnim build/menu_strip.png res_vi/splashAnim
python3 -I scripts/make_png7.py jar_x/png7 build/anim_s1.bin res_vi/png7 res_vi/anim.bin
python3 -I scripts/make_small.py jar_x res_vi/anim.bin res_vi build/anim_s3.bin && cp build/anim_s3.bin res_vi/anim.bin
python3 -I scripts/make_map.py jar_x/png64 res_vi/png64
