#!/bin/sh
# Build both release variants (240x320 original layout, 320x240 landscape),
# resources encrypted with key 56958410 (see src/rc.java, tools/encrypt.py).
# usage: build.sh <original.jar> <tools-dir>
#   tools-dir: asm-9.7*.jar, ecj-4.6.1.jar, proguard-base-7.6.1.jar,
#              proguard-core-9.1.6.jar, log4j-api/core-2.23.1.jar,
#              cldcapi10-2.0.4.jar, midpapi20-2.0.4.jar, microemu-jsr-120-2.0.4.jar
set -e
ORIG=$(readlink -f "$1"); T=$(readlink -f "$2")
M=$(cd "$(dirname "$0")" && pwd); B=$M/build; D=$M/dist
VERSION=${VERSION:-1.1}
GAME=SungVatThienHa
LIBS=$T/cldcapi10-2.0.4.jar:$T/midpapi20-2.0.4.jar:$T/microemu-jsr-120-2.0.4.jar
ASM=$T/asm-9.7.jar:$T/asm-tree-9.7.jar:$T/asm-analysis-9.7.jar:$T/asm-util-9.7.jar:$T/asm-commons-9.7.jar

rm -rf "$B" && mkdir -p "$B/base" "$B/cls" "$B/tools" "$D"
(cd "$B/base" && unzip -q "$ORIG")

# 1. tools
javac -nowarn -cp "$ASM" -d "$B/tools" "$M/tools/Patch.java" "$M/tools/Verify.java" 2>&1 | grep -v JAVA_TOOL || true

# 2. rc.class (class 45.3, CLDC 1.0 / MIDP 2.0 API only)
java -jar "$T/ecj-4.6.1.jar" -source 1.3 -target 1.1 -nowarn -bootclasspath "$LIBS" -d "$B/cls" "$M/src/rc.java" 2>&1 | grep -v JAVA_TOOL || true

# 3. redirect getResourceAsStream in w and av
cp "$B/base/w.class" "$B/base/av.class" "$B/cls/"
java -cp "$B/tools:$ASM" Patch "$B/cls/w.class" "$B/cls/av.class" 2>&1 | grep -v JAVA_TOOL

# 4. preverify (StackMap) with ProGuard; only w, av, rc are taken from its output
mkdir -p "$B/pg_in"
(cd "$B/base" && cp *.class "$B/pg_in/") && cp "$B/cls/"*.class "$B/pg_in/"
(cd "$B/pg_in" && python3 -c "
import os,zipfile
with zipfile.ZipFile('../unverified.jar','w',zipfile.ZIP_DEFLATED) as z:
    for f in sorted(os.listdir('.')): z.write(f,f)")
cat > "$B/pg.pro" <<PG
-injars $B/unverified.jar
-outjars $B/preverified.jar
-libraryjars $T/cldcapi10-2.0.4.jar
-libraryjars $T/midpapi20-2.0.4.jar
-libraryjars $T/microemu-jsr-120-2.0.4.jar
-microedition
-dontshrink
-dontoptimize
-dontobfuscate
-dontwarn
-dontnote
-keepattributes *
PG
java -cp "$T/proguard-base-7.6.1.jar:$T/proguard-core-9.1.6.jar:$T/log4j-api-2.23.1.jar:$T/log4j-core-2.23.1.jar" proguard.ProGuard @"$B/pg.pro" > "$B/proguard.log" 2>&1
mkdir -p "$B/pv" && (cd "$B/pv" && unzip -q -o "$B/preverified.jar" w.class av.class rc.class)

# 5. variants
for V in 240x320 320x240; do
  S=$B/stage_$V; rm -rf "$S"; cp -r "$B/base" "$S"; cp "$B/pv/"*.class "$S/"
  if [ $V = 320x240 ]; then
    sed -i 's/^screenSize=240,320/screenSize=320,240/' "$S/bin/config.txt"
    grep -q '^screenSize=320,240' "$S/bin/config.txt"
    cp "$M/res320/battlepos.txt" "$S/bin/battlepos.txt"
  fi
  python3 -I "$M/tools/encrypt.py" "$S"
  sed -i "s/^MIDlet-Version: [^\r]*/MIDlet-Version: $VERSION/" "$S/META-INF/MANIFEST.MF"
  OUT=$D/${GAME}_$V.jar
  python3 -I "$M/tools/mkjar.py" "$S" "$OUT"
  java -cp "$B/tools:$ASM" Verify "$OUT" "$LIBS" --require-stackmap 2>&1 | grep -v JAVA_TOOL
  SIZE=$(stat -c %s "$OUT")
  { grep -v '^\(Manifest-Version\|Ant-Version\|Created-By\):' "$S/META-INF/MANIFEST.MF" | tr -d '\r' | sed '/^$/d'
    echo "MIDlet-Jar-URL: ${GAME}_$V.jar"; echo "MIDlet-Jar-Size: $SIZE"; } | sed 's/$/\r/' > "$D/${GAME}_$V.jad"
  echo "built $OUT ($SIZE bytes)"
done
