#!/bin/bash
# Build HL4 J2ME jars (LGT and/or KTF) from the original archives.
# usage: build.sh <LGT|KTF> <original.zip> <out_dir>
set -e
HERE=$(cd "$(dirname "$0")" && pwd)
TOOLS=$HERE/../tools
PLAT=$1; ZIP=$2; OUT=$3
BOOT=$TOOLS/cldc-1.1-stub-1.0.jar:$TOOLS/midp-2.0-stub-1.0.jar
W=$(mktemp -d)
mkdir -p $W/classes $W/pre $W/jar $W/orig "$OUT"
# 1) compile (Java 1.3 source, class files for CLDC)
AOTNAME=$( [ "$PLAT" = KTF ] && echo KtfAot || echo LgtAot )
SRC="$(find $HERE/src/core $HERE/src/midp -name '*.java' | grep -v '/core/Boot.java') $HERE/src/aot/hl4/aot/$AOTNAME.java"
java -jar $TOOLS/ecj-4.6.1.jar -source 1.3 -target 1.2 -nowarn -bootclasspath $BOOT -d $W/classes $SRC
# 2) preverify with ProGuard
cat > $W/pg.pro <<PRO
-injars $W/classes
-outjars $W/pre
-libraryjars $TOOLS/cldc-1.1-stub-1.0.jar
-libraryjars $TOOLS/midp-2.0-stub-1.0.jar
-microedition
-dontobfuscate
-dontoptimize
-dontshrink
-dontnote
-dontwarn
-keep class hl4.** { *; }
PRO
java -jar $TOOLS/proguard-base-6.2.2.jar @$W/pg.pro > $W/pg.log 2>&1 || { cat $W/pg.log; exit 1; }
# 3) original archive contents
cd $W/orig && unzip -q -o "$ZIP"
JAR=$(ls $W/orig/*.jar | head -1)
cd $W/jar && unzip -q -o "$JAR" && rm -rf META-INF
cp -r $W/pre/* $W/jar/
# 3b) optional overlay (Vietnamese resources)
if [ -n "$OVERLAY" ]; then cp -r "$OVERLAY"/. $W/jar/; fi
# 4) manifest
NAME="HeroesLore4 $PLAT${SUFFIX}"
cat > $W/MANIFEST.MF <<MF
Manifest-Version: 1.0
MIDlet-1: $NAME,/icon.png,hl4.midp.HL4Midlet
MIDlet-Name: $NAME
MIDlet-Vendor: EA Mobile Korea (port)
MIDlet-Version: 1.0.0
MicroEdition-Configuration: CLDC-1.1
MicroEdition-Profile: MIDP-2.0
HL4-Platform: $PLAT
Nokia-MIDlet-Canvas-Scaling-Orientation-Switch: false
MIDlet-Touch-Support: false
HL4-FPS: 27
MF
[ -n "$HL4_SAVEDBG" ] && echo "HL4-SAVEDBG: 1" >> $W/MANIFEST.MF
if [ "$PLAT" = "LGT" ]; then
  echo "HL4-AID: $(grep -a '^AID:' $W/orig/app_info | cut -d: -f2 | tr -d '\r\n')" >> $W/MANIFEST.MF
  echo "HL4-PID: $(grep -a '^PID:' $W/orig/app_info | cut -d: -f2 | tr -d '\r\n')" >> $W/MANIFEST.MF
  cp $W/orig/small.png $W/jar/icon.png 2>/dev/null || true
else
  echo "HL4-AID: $(grep -a '^AID:' $W/orig/__adf__ | cut -d: -f2 | tr -d '\r\n')" >> $W/MANIFEST.MF
  echo "HL4-PID: $(grep -a '^PID:' $W/orig/__adf__ | cut -d: -f2 | tr -d '\r\n')" >> $W/MANIFEST.MF
  echo "HL4-Binary: $(cd $W/jar && ls client.bin*)" >> $W/MANIFEST.MF
  # KTF icons are PNG despite the .icon extension
  cp $W/orig/small.icon $W/jar/icon.png 2>/dev/null || true
fi
BASE=HL4_$PLAT${SUFFIX}
cd $W/jar && rm -f "$OUT/$BASE.jar" && jar cfm "$OUT/$BASE.jar" $W/MANIFEST.MF . 
SIZE=$(stat -c %s "$OUT/$BASE.jar")
{ cat $W/MANIFEST.MF | grep -v '^Manifest-Version'; echo "MIDlet-Jar-URL: $BASE.jar"; echo "MIDlet-Jar-Size: $SIZE"; } > "$OUT/$BASE.jad"
echo "built $OUT/$BASE.jar ($SIZE bytes)"
rm -rf $W
