#!/bin/bash
# compile headless test harness: tbuild.sh [outdir]
cd "$(dirname "$0")"
OUT=${1:-build/test}
rm -rf "$OUT.tmp"; mkdir -p "$OUT.tmp"
javac -nowarn -encoding UTF-8 -d "$OUT.tmp" $(find src/core src/aot -name '*.java') test/src/RunHeadless.java test/src/HL4Tool.java test/src/KtfDesCheck.java test/src/SndConvert.java 2>&1 | grep -v "^Note:" 
rm -rf "$OUT"; mv "$OUT.tmp" "$OUT"
