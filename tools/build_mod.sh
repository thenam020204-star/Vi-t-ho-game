#!/bin/bash
# Build mod T14: work/mod/libmsmod.so (NDK r27c + Dobby, arm64) va work/mod/classes_mod.dex (javac --release 8 + d8).
# Cai toolchain vao work/tools neu thieu (NDK r27c, Dobby, android.jar platform-36). Dung: tools/build_mod.sh [native|java|all]
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
T="$ROOT/work/tools"; B="$ROOT/work/build"; OUT="$ROOT/work/mod"
NDK="$T/android-ndk-r27c"
DOBBY="$T/dobby"
DOBBY_COMMIT=e9fe7fb   # 2023-04-21; master (5dfc854, 2024) dang do refactor, khong build duoc tren Android
AJAR="$T/p36/android-36/android.jar"
D8="$T/android-15/d8"
WHAT="${1:-all}"
mkdir -p "$T" "$B" "$OUT"

setup() {
  if [ ! -d "$NDK" ]; then
    echo "[setup] tai NDK r27c"; curl -fSL -o "$T/ndk.zip" https://dl.google.com/android/repository/android-ndk-r27c-linux.zip
    unzip -q -o "$T/ndk.zip" -d "$T"; rm -f "$T/ndk.zip"
  fi
  if [ ! -f "$AJAR" ]; then
    echo "[setup] tai platform-36"; curl -fSL -o "$T/p36.zip" https://dl.google.com/android/repository/platform-36_r02.zip
    unzip -q -o "$T/p36.zip" -d "$T/p36"; rm -f "$T/p36.zip"
  fi
  if [ ! -d "$DOBBY/.git" ]; then
    echo "[setup] git clone Dobby"; git clone https://github.com/jmpews/Dobby "$DOBBY"
  fi
  git -C "$DOBBY" checkout -q -f "$DOBBY_COMMIT" 2>/dev/null || { git -C "$DOBBY" fetch -q --unshallow || true; git -C "$DOBBY" checkout -q -f "$DOBBY_COMMIT"; }
  # Dobby e9fe7fb: logging.h dinh nghia Logger::Shared() trong header khong `inline` => trung ky hieu khi link static.
  sed -i 's/^Logger \*Logger::Shared() {/inline Logger *Logger::Shared() {/' "$DOBBY/external/logging/logging/logging.h"
  [ -x "$D8" ] || { echo "thieu d8 ($D8): chay tools/setup_tools.sh"; exit 1; }
}

native() {
  # Dobby static (tat plugin khong can: SymbolResolver, ImportTableReplace, BionicLinkerUtil, debug log)
  if [ ! -f "$B/dobby/libdobby.a" ]; then
    cmake -S "$DOBBY" -B "$B/dobby" -G Ninja -DCMAKE_TOOLCHAIN_FILE="$NDK/build/cmake/android.toolchain.cmake" \
      -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 -DCMAKE_BUILD_TYPE=Release \
      -DPlugin.SymbolResolver=OFF -DPlugin.ImportTableReplace=OFF -DPlugin.Android.BionicLinkerUtil=OFF -DDOBBY_DEBUG=OFF \
      -DCMAKE_C_FLAGS="-fvisibility=hidden" -DCMAKE_CXX_FLAGS="-fvisibility=hidden" >/dev/null
    cmake --build "$B/dobby" --target dobby_static
  fi
  python3 -I "$ROOT/tools/check_hooks.py"
  cmake -S "$ROOT/mods/native" -B "$B/msmod" -G Ninja -DCMAKE_TOOLCHAIN_FILE="$NDK/build/cmake/android.toolchain.cmake" \
    -DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24 -DCMAKE_BUILD_TYPE=Release \
    -DDOBBY_DIR="$DOBBY" -DDOBBY_LIB="$B/dobby/libdobby.a" >/dev/null
  cmake --build "$B/msmod"
  "$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-strip" --strip-unneeded -o "$OUT/libmsmod.so" "$B/msmod/libmsmod.so"
  echo "== libmsmod.so"; ls -l "$OUT/libmsmod.so"
  "$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-readelf" -d "$OUT/libmsmod.so" | grep -E "NEEDED|SONAME" || true
}

java() {
  local J="$B/java"; rm -rf "$J"; mkdir -p "$J/classes"
  javac --release 8 -encoding UTF-8 -Xlint:-options -cp "$AJAR" -d "$J/classes" $(find "$ROOT/mods/java" -name '*.java')
  (cd "$J/classes" && jar cf ../mod.jar .)
  "$D8" --release --min-api 24 --lib "$AJAR" --output "$J" "$J/mod.jar"
  cp "$J/classes.dex" "$OUT/classes_mod.dex"
  echo "== classes_mod.dex"; ls -l "$OUT/classes_mod.dex"
  "$T/android-15/dexdump" -f "$OUT/classes_mod.dex" | grep -E "Class descriptor|Magic|DEX version" | head
}

verify() {
  # moi ham `native` trong dex phai co ky hieu JNI Java_com_msmod_Mod_<ten> xuat trong libmsmod.so (va nguoc lai)
  local NM="$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-nm"
  local a b
  a=$("$T/android-15/dexdump" -f "$OUT/classes_mod.dex" | awk "/Class descriptor.*'Lcom\/msmod\/Mod;'/{c=1} c&&/name  +:/{n=\$3} c&&/access  +:.*NATIVE/{print n}" | tr -d "'" | sort -u)
  b=$("$NM" -D --defined-only "$OUT/libmsmod.so" | awk '/ T Java_com_msmod_Mod_/{sub("Java_com_msmod_Mod_","",$3);print $3}' | sort -u)
  if [ "$a" != "$b" ]; then echo "LOI: JNI khong khop"; echo "dex: $a"; echo "so : $b"; exit 1; fi
  echo "== JNI khop (dex native == export .so):"; echo "$a" | tr '\n' ' '; echo
  local ne; ne=$("$NDK/toolchains/llvm/prebuilt/linux-x86_64/bin/llvm-readelf" -d "$OUT/libmsmod.so" | awk '/NEEDED/{print $5}' | tr -d '[]' | sort | tr '\n' ' ')
  echo "== NEEDED: $ne"
  case " $ne" in *" libc.so"*) ;; *) echo "LOI: thieu libc"; exit 1;; esac
  for l in $ne; do case "$l" in libc.so|libm.so|libdl.so|liblog.so|libandroid.so) ;; *) echo "LOI: NEEDED la: $l"; exit 1;; esac; done
}

setup
case "$WHAT" in native) native;; java) java;; all) native; java; verify;; *) echo "dung: $0 [native|java|all]"; exit 2;; esac
