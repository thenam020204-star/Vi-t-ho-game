#!/bin/sh
# Build lại libtwommod.so + smali của menu mod từ mã nguồn.
#   NDK=<android-ndk-r27c> SDK_JAR=<android.jar> D8=<build-tools/d8> BAKSMALI=<baksmali.jar> sh build_mod.sh
# Kết quả: build/libtwommod.so, build/smali/com/twommod/*.smali
set -e
cd "$(dirname "$0")"
: "${NDK:?đặt NDK}" "${SDK_JAR:?đặt SDK_JAR}" "${D8:?đặt D8}" "${BAKSMALI:?đặt BAKSMALI}"
CC=$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/aarch64-linux-android21-clang   # minSdk của game = 21
mkdir -p build
$CC -O2 -fPIC -shared -Wall -Wextra -fvisibility=hidden -Wl,--build-id=sha1 -Wl,-z,max-page-size=16384 \
    -o build/libtwommod.so native/twommod.c -llog -ldl
rm -rf build/cls build/dex build/smali; mkdir -p build/cls build/dex
javac --release 8 -nowarn -cp "$SDK_JAR" -d build/cls java/com/twommod/ModMenu.java
"$D8" --min-api 21 --release --lib "$SDK_JAR" --output build/dex build/cls/com/twommod/*.class
java -jar "$BAKSMALI" d build/dex/classes.dex -o build/smali
rm -rf build/cls build/dex
echo "OK: build/libtwommod.so, build/smali/com/twommod/"
