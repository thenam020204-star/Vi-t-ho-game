#!/bin/bash
# Bien dich hook/VH.java (CLDC: ecj -source 1.3 -target 1.3) va patcher/Patch.java (JDK) vao build/
set -e
cd "$(dirname "$0")"
source scripts/env.sh
L="$LIBS"
JB=build/jbase
[ -d $JB/classes ] || env -u JAVA_TOOL_OPTIONS jmod extract --dir $JB "$(dirname "$(dirname "$(readlink -f "$(which javac)")")")/jmods/java.base.jmod"
rm -rf build/hook build/patcher; mkdir -p build/hook build/patcher
env -u JAVA_TOOL_OPTIONS java -cp $L/ecj-3.33.0.jar org.eclipse.jdt.internal.compiler.batch.Main -source 1.3 -target 1.3 -encoding UTF-8 -nowarn \
  -bootclasspath $JB/classes -cp "$FJ" -d build/hook hook/VH.java
env -u JAVA_TOOL_OPTIONS javac -nowarn -cp $L/asm-9.7.1.jar:$L/asm-tree-9.7.1.jar -d build/patcher patcher/Patch.java
echo "build_hook OK"
