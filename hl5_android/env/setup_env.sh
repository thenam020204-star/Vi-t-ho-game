#!/bin/bash
# Dựng môi trường làm việc HL5-VI nhanh nhất, không cần upload lại (mọi đầu vào nằm trong hl5_android/env/).
# Dùng:  hl5_android/env/setup_env.sh            (WORK mặc định ~/hl5work)
#        WORK=/đường/dẫn hl5_android/env/setup_env.sh [--build]   (--build: dựng luôn APK và so sha256 với dist/)
set -euo pipefail
ENV="$(cd "$(dirname "$0")" && pwd)"; HL="$(dirname "$ENV")"
: "${WORK:=$HOME/hl5work}"; export WORK; mkdir -p "$WORK"
echo "== kiểm sha256 đầu vào"; (cd "$ENV" && sha256sum -c --quiet SHA256SUMS)

echo "== python (unicorn/keystone/capstone/pyelftools/lief/pillow/openpyxl)"
python3 - <<'PY' 2>/dev/null || pip install -q unicorn==2.1.4 keystone-engine==0.9.2 capstone==5.0.9 pyelftools==0.33 lief==1.0.0 pillow openpyxl
import unicorn, keystone, capstone, elftools, lief, PIL, openpyxl
PY

echo "== công cụ hệ thống (clang-18, ld.lld, llvm-objdump, java/javac/keytool)"
for t in clang-18 ld.lld java javac keytool; do command -v $t >/dev/null || MISS="${MISS:-} $t"; done
command -v llvm-objdump >/dev/null || command -v llvm-objdump-18 >/dev/null || MISS="${MISS:-} llvm-objdump"
if [ -n "${MISS:-}" ]; then echo "thiếu:$MISS → apt-get install clang-18 lld-18 llvm-18 default-jdk-headless"; \
  (apt-get install -y -q clang-18 lld-18 llvm-18 default-jdk-headless >/dev/null 2>&1 || true); fi
command -v llvm-objdump >/dev/null || { [ -x /usr/bin/llvm-objdump-18 ] && ln -sf /usr/bin/llvm-objdump-18 /usr/local/bin/llvm-objdump; }
command -v ld.lld >/dev/null || { [ -x /usr/bin/ld.lld-18 ] && ln -sf /usr/bin/ld.lld-18 /usr/local/bin/ld.lld; }

echo "== Android build-tools 34 → /opt/asdk/android-14"
if [ ! -x /opt/asdk/android-14/apksigner ]; then mkdir -p /opt/asdk && unzip -q -o "$ENV/sdk/build-tools_r34-linux.zip" -d /opt/asdk; fi

echo "== jar smali/baksmali cho lớp chẩn đoán → \$WORK/tools_dl"
mkdir -p "$WORK/tools_dl" && cp -n "$ENV"/jars/*.jar "$WORK/tools_dl/"

echo "== WORK (giải nén APK gốc, project LGT, tool NAM_P; build vi5; disasm)"
UP="$ENV/inputs" "$HL/setup_work.sh"
[ -f "$WORK/orig.apk" ] || cp "$ENV/inputs/heroes-lore-5-v1.0.5.apk" "$WORK/orig.apk"

if [ "${1:-}" = "--build" ]; then
  echo "== build APK + so với dist/ (bản đã commit)"
  REF=$(cd "$HL" && git show HEAD:hl5_android/dist/HL5-VI-v0.2-hd.apk 2>/dev/null | sha256sum | cut -d' ' -f1 || true)
  "$HL/make_apk.sh"
  NEW=$(sha256sum "$WORK/build_apk/HL5-VI-v0.2-hd.apk" | cut -d' ' -f1)
  echo "đã commit: $REF"; echo "vừa build: $NEW"; [ "$REF" = "$NEW" ] && echo "TRÙNG TỪNG BYTE" || echo "KHÁC (kiểm lại)"
fi
echo "XONG. WORK=$WORK"
