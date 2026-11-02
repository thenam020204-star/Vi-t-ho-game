#!/bin/bash
# Bien dich VH.java (CLDC: -source 1.3 -target 1.3) va Patch.java (JDK)
set -e
cd "$(dirname "$0")"
mkdir -p build/hook build/patcher
env -u JAVA_TOOL_OPTIONS java -cp work/lib/ecj.jar org.eclipse.jdt.internal.compiler.batch.Main -source 1.3 -target 1.3 -encoding UTF-8 -nowarn \
  -bootclasspath work/jbase/classes -cp work/tools/freej2me_plus.jar:jar_x -d build/hook hook/VH.java
env -u JAVA_TOOL_OPTIONS javac -nowarn -cp work/lib/asm-9.8.jar:work/lib/asm-tree-9.7.1.jar -d build/patcher patcher/Patch.java
