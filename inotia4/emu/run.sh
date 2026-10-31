#!/usr/bin/env bash
# Chay harness unidbg. Can: UNIDBG_CP (classpath unidbg-android + deps), LIB (libgame.so), APK (apk can chay).
set -e
cd "$(dirname "$0")"
CP=${UNIDBG_CP:-$(cat .cp_local)}
OUT=${OUT:-../work/emuout}
mkdir -p ../work/emucls "$OUT"
env -u JAVA_TOOL_OPTIONS javac -nowarn -encoding UTF-8 -cp "$CP" -d ../work/emucls $(find src -name "*.java") ../java/src/com/namp/vh/*.java
env -u JAVA_TOOL_OPTIONS java -Xss8m $EMU_OPTS -cp "../work/emucls:$CP" Inotia4Emu "$LIB" "$APK" "$OUT" "$@" < "${EMU_STDIN:-/dev/null}" 2>&1 \
  | grep -avE '^[0-9A-F]{4}: |^size: |md5=|^>-|^\^-|CallInitFunction|log4j:WARN'
