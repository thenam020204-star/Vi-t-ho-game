#!/usr/bin/env bash
# Dung lai toan bo ban Viet hoa tu jar goc:  bash rebuild.sh [TOOLS_DIR]
#   TOOLS_DIR (tuy chon) = thu muc co freej2me_plus.jar, proguard.jar, fonts/f12.bin (vd work/tools cua skill).
#   Khong truyen: tu giai nen Tool_Viet_Hoa_NAM_P.zip vao lib/ va tai ProGuard 7.4.2, ecj 3.26.0, ASM 9.7.
#   Can: JDK 11+ day du (javac, jmod), python3, zip/unzip, curl.
# Ket qua: out/HungBaTamQuoc_N73_viet.jar + .jad, logs/jar_check.txt
set -e
cd "$(dirname "$0")"
D=$(pwd)
LIB="$D/lib"; mkdir -p "$LIB"
get() { [ -s "$LIB/$1" ] || curl -sSfL --retry 3 -o "$LIB/$1" "$2"; }
if [ -n "$1" ]; then
  TOOLS=$(realpath "$1"); FJ="$TOOLS/freej2me_plus.jar"; F12="$F12"; export PROGUARD="$TOOLS/proguard.jar"
else   # tu dung tu Tool_Viet_Hoa_NAM_P.zip trong du an + ProGuard 7.4.2
  T="$LIB/NAM_P"; [ -d "$T" ] || (cd "$LIB" && unzip -q "$D/Tool_Viet_Hoa_NAM_P.zip")
  FJ="$T/freej2me/freej2me_plus.jar"; F12="$T/fonts/f12.bin"
  if [ ! -s "$LIB/proguard.jar" ]; then
    curl -sSfL --retry 3 -o "$LIB/pg.zip" https://github.com/Guardsquare/proguard/releases/download/v7.4.2/proguard-7.4.2.zip
    unzip -q -o -j "$LIB/pg.zip" proguard-7.4.2/lib/proguard.jar -d "$LIB"; rm -f "$LIB/pg.zip"
  fi
  export PROGUARD="$LIB/proguard.jar"
fi
get ecj.jar https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.26.0/ecj-3.26.0.jar
get asm.jar https://repo1.maven.org/maven2/org/ow2/asm/asm/9.7/asm-9.7.jar
get asm-tree.jar https://repo1.maven.org/maven2/org/ow2/asm/asm-tree/9.7/asm-tree-9.7.jar
B="$D/build"; rm -rf "$B"; mkdir -p "$B"/{jx,ov/bin,str,cin,patched,cls,ptool,code} out logs
PY="python3 -I"; JAVA="env -u JAVA_TOOL_OPTIONS java"; JAVAC="env -u JAVA_TOOL_OPTIONS javac"
(cd "$B/jx" && unzip -q "$D/goc/game.jar")

echo "== 1. font 12px"
$PY scripts/build_font12.py "$B/jx/bin/font.bin" "$F12" "$B/ov/bin"

echo "== 2. text resource"
if [ -f text/text_da_dich.tsv ]; then $PY scripts/text/textpack.py pack "$B/jx/bin" text/text_da_dich.tsv "$B/ov/bin"
else echo "  (chua co text/text_da_dich.tsv -> giu goc)"; fi

echo "== 2b. bo cuc uiform (bang chon ai)"
$PY scripts/patch_uiform.py "$B/jx/bin/uiform.bin" "$B/ov/bin/uiform.bin"

echo "== 3. anh"
cp -r assets/img/bin/. "$B/ov/bin/"
[ -d assets/img2/bin ] && cp -r assets/img2/bin/. "$B/ov/bin/"

echo "== 4. chuoi trong .class"
cp "$B"/jx/*.class "$B/cin/"
if [ -f text/class_da_dich.tsv ]; then
  $PY scripts/text/classstr.py patch "$B/jx" text/class_da_dich.tsv "$B/str"
  cp "$B"/str/*.class "$B/cin/" 2>/dev/null || true
fi

echo "== 5. sua bytecode (ASM) + class moi (ecj 1.3)"
$JAVAC -nowarn -cp "$LIB/asm.jar:$LIB/asm-tree.jar" -d "$B/ptool" tools_src/Patcher.java
$JAVA -cp "$LIB/asm.jar:$LIB/asm-tree.jar:$B/ptool" Patcher "$B/cin" "$B/patched" | tee logs/patcher.txt
JH=$(dirname "$(dirname "$(readlink -f "$(which javac)")")")
[ -d "$B/jbase" ] || jmod extract --dir "$B/jbase" "$JH/jmods/java.base.jmod"
$JAVA -cp "$LIB/ecj.jar" org.eclipse.jdt.internal.compiler.batch.Main -source 1.3 -target 1.3 -encoding UTF-8 -nowarn \
  -bootclasspath "$B/jbase/classes" -cp "$B/cin:$FJ" -d "$B/cls" src_patch/bs.java src_patch/vh.java
$PY scripts/set_clinit_flags.py "$B/cls/bs.class" 0x0009

echo "== 6. preverify class da sua bytecode"
cp "$B"/patched/*.class "$B"/cls/*.class "$B/code/"
(cd "$B/code" && zip -q ../code_in.jar *.class)
bash scripts/preverify.sh "$B/code_in.jar" "$B/code_pv.jar" goc/game.jar "$FJ"
mkdir -p "$B/ovc" && (cd "$B/ovc" && unzip -q ../code_pv.jar)
# class chi doi chuoi (khong sua bytecode) giu nguyen StackMap goc
mkdir -p "$B/ovs"; for f in "$B"/str/*.class; do [ -e "$f" ] || continue; b=$(basename "$f"); [ -e "$B/ovc/$b" ] || cp "$f" "$B/ovs/"; done

echo "== 7. dong jar"
J=out/HungBaTamQuoc_N73_viet.jar
$PY scripts/repack.py goc/game.jar "$J" "$B/ov" "$B/ovs" "$B/ovc"
$PY - "$J" <<'P'
import zipfile, os, sys
j = sys.argv[1]
m = zipfile.ZipFile(j).read('META-INF/MANIFEST.MF').decode('utf-8').replace('\r', '').split('\n')
keep = [l for l in m if l.split(':')[0] in ('MIDlet-Name', 'MIDlet-Vendor', 'MIDlet-Version', 'MIDlet-1', 'MIDlet-Icon',
                                            'MicroEdition-Configuration', 'MicroEdition-Profile')]
keep += ['MIDlet-Jar-URL: ' + os.path.basename(j), 'MIDlet-Jar-Size: %d' % os.path.getsize(j)]
open(j[:-4] + '.jad', 'w', encoding='utf-8').write('\n'.join(keep) + '\n')
P
echo "== 8. jar_check"
$PY scripts/jar_check.py goc/game.jar "$J" | tee logs/jar_check.txt | tail -6
