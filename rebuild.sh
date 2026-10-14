#!/usr/bin/env bash
# Tai tao toan bo jar Viet hoa tu jar goc: font 17px -> splash class -> text/df -> jar_check -> FreeJ2ME.
# Can: JDK 9+ (co jmods), python3 + Pillow, Xvfb/ImageMagick (cho buoc chay emulator).
set -e
cd "$(dirname "$0")"
TOOLS=work/tools
[ -f $TOOLS/env.sh ] || bash scripts/setup_tools.sh Tool_Viet_Hoa_NAM_P.zip work
source $TOOLS/env.sh
mkdir -p work/res work/build out logs
python3 -I scripts/dfpack.py goc/cnc.jar work/res > logs/df_list.txt
python3 -I scripts/make_f17.py $TOOLS work/f17.bin
python3 -I scripts/make_font.py work/res/009_df0.bin work/res/000_df0.png work/f17.bin $TOOLS/pixeltext-kit work/font_chunk.bin work/font_atlas.png
# lop splash: ecj 1.3 -> preverify rieng (khong dung toi class goc)
ECJ=work/build/ecj.jar
[ -f $ECJ ] || curl -sSfL -o $ECJ https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.26.0/ecj-3.26.0.jar
JH=$(dirname $(dirname $(readlink -f $(which javac))))
[ -d work/build/jbase ] || jmod extract --dir work/build/jbase $JH/jmods/java.base.jmod
rm -rf work/build/vh work/build/vh_pv work/build/vh_raw.jar work/build/vh_pv.jar
env -u JAVA_TOOL_OPTIONS java -cp $ECJ org.eclipse.jdt.internal.compiler.batch.Main -source 1.3 -target 1.3 -encoding UTF-8 -nowarn \
  -bootclasspath work/build/jbase/classes -cp $FJ:goc/cnc.jar -d work/build/vh src_vh/NampMIDlet.java
(cd work/build/vh && zip -q ../vh_raw.jar *.class)
bash scripts/preverify.sh work/build/vh_raw.jar work/build/vh_pv.jar $FJ goc/cnc.jar
mkdir work/build/vh_pv && (cd work/build/vh_pv && unzip -q ../vh_pv.jar '*.class')
python3 -I scripts/textpack.py goc/cnc.jar work/text_goc.tsv
python3 -I scripts/build_vi.py goc/cnc.jar out/text_can_dich.tsv work/font_chunk.bin work/font_atlas.png work/build/vh_pv logo0.png out/cnc_vi.jar
python3 -I scripts/make_jad.py out/cnc_vi.jar out/cnc_vi.jad
python3 scripts/jar_check.py goc/cnc.jar out/cnc_vi.jar | tee logs/jar_check.txt
grep -q "RESULT: PASS" logs/jar_check.txt
if [ "$1" != "--no-emu" ]; then
  rm -rf out/shots rms config freej2me_system   # save/cau hinh FreeJ2ME tu lan chay truoc
  python3 scripts/run_emu.py --fj $FJ --jar out/cnc_vi.jar --out out/shots --size 240x320 \
    --steps "wait 2; shot 00_splash; wait 15.5; shot 01_bat_am_thanh; ekey ls; wait 6; shot 02_tieu_de; ekey kc; wait 4; shot 03_menu_chinh; ekey kc; wait 4; shot 04_choi_moi; ekey kc; wait 4; shot 05_chon_chuong; ekey kc; wait 5; shot 06_briefing; ekey kc; wait 4; shot 07_briefing2; ekey kc 6; wait 15; shot 08_trong_tran; ekey rs; wait 3; shot 10_tam_dung; ekey kd; ekey kc; wait 3; shot 11_tuy_chon" | tee logs/run_emu.json
fi
