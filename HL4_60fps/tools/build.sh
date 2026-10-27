#!/bin/bash
# Build dist/HL4.jar from base/HL4_1.01.jar: bytecode patches (tools/src/Patch.java) +
# classes compiled from src/ (Java 1.3 source, class 45.3, CLDC 1.1 / MIDP 2.0 stubs),
# compatibility scan (Verify), ProGuard -microedition preverify, Verify --require-stackmap.
# Toolchain dir: $T (JDK 8, ASM 9.7, ProGuard 7.6.1, microemu cldcapi11/midpapi20 2.0.4).
set -e
P=$(cd "$(dirname "$0")/.." && pwd)
T=${T:-/tmp/claude-0/w/tools}
VERSION=${VERSION:-1.0.2}
B=$P/build
BASE=$P/base/HL4_1.01.jar
LIBS=$T/cldcapi11-2.0.4.jar:$T/midpapi20-2.0.4.jar
ASM=$T/asm-9.7.jar:$T/asm-tree-9.7.jar:$T/asm-analysis-9.7.jar:$T/asm-util-9.7.jar:$T/asm-commons-9.7.jar
JAVAC8=$T/jdk8u504-b01/bin/javac

rm -rf "$B" && mkdir -p "$B/tools" "$B/patched" "$B/classes" "$P/dist"
javac -nowarn -cp "$ASM" -d "$B/tools" "$P/tools/src/Patch.java" "$P/tools/src/Verify.java" 2>&1 | grep -v "^Picked up" || true
java -cp "$B/tools:$ASM" Patch "$BASE" "$B/patched" 2>&1 | grep -v "^Picked up"

find "$P/src" -name "*.java" > "$B/sources.txt"
$JAVAC8 -nowarn -source 1.3 -target 1.1 -encoding UTF-8 -bootclasspath "$LIBS" \
    -cp "$B/patched:$BASE" -implicit:none -d "$B/classes" @"$B/sources.txt" 2>&1 | grep -v "^Picked up" || true
test -f "$B/classes/hl4/game/Interp.class"

python3 -I - "$BASE" "$B/patched" "$B/classes" "$B/unverified.jar" "$VERSION" <<'PY'
import sys, os, zipfile
base, patched, classes, out, ver = sys.argv[1:]
repl = {}
for d in (patched, classes):
    for r, _, fs in os.walk(d):
        for f in fs:
            p = os.path.join(r, f)
            repl[os.path.relpath(p, d).replace(os.sep, '/')] = open(p, 'rb').read()
zin = zipfile.ZipFile(base)
names = zin.namelist()
with zipfile.ZipFile(out, 'w', zipfile.ZIP_DEFLATED) as z:
    for n in names:
        if n.endswith('/'):
            continue
        data = zin.read(n)
        if n == 'META-INF/MANIFEST.MF':
            lines = data.decode('utf-8').split('\r\n' if b'\r\n' in data else '\n')
            lines = ['MIDlet-Version: ' + ver if l.startswith('MIDlet-Version:') else l for l in lines]
            data = '\n'.join(lines).encode('utf-8')
            if not data.endswith(b'\n'):
                data += b'\n'
        elif n in repl:
            data = repl.pop(n)
        z.writestr(n, data)
    for n in sorted(repl):          # new classes (none expected to collide with resources)
        z.writestr(n, repl[n])
print('assembled', out, len(names), 'entries in base')
PY

java -cp "$B/tools:$ASM" Verify "$B/unverified.jar" "$LIBS" 2>&1 | grep -v "^Picked up"
cat > "$B/pg.pro" <<PG
-injars $B/unverified.jar
-outjars $B/preverified.jar
-libraryjars $T/cldcapi11-2.0.4.jar
-libraryjars $T/midpapi20-2.0.4.jar
-microedition
-dontshrink
-dontoptimize
-dontobfuscate
-dontwarn
-dontnote
-keepattributes *
PG
java -jar "$T/proguard-7.6.1/lib/proguard.jar" @"$B/pg.pro" > "$B/proguard.log" 2>&1
java -cp "$B/tools:$ASM" Verify "$B/preverified.jar" "$LIBS" --require-stackmap 2>&1 | grep -v "^Picked up"
cp "$B/preverified.jar" "$P/dist/HL4.jar"
SIZE=$(stat -c %s "$P/dist/HL4.jar")
unzip -p "$P/dist/HL4.jar" META-INF/MANIFEST.MF | tr -d '\r' | grep -v '^$' | grep -v '^Manifest-Version' > "$P/dist/HL4.jad"
echo "MIDlet-Jar-URL: HL4.jar" >> "$P/dist/HL4.jad"
echo "MIDlet-Jar-Size: $SIZE" >> "$P/dist/HL4.jad"
echo "built dist/HL4.jar $SIZE bytes, MIDlet-Version $VERSION"
