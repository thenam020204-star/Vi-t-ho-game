#!/usr/bin/env bash
# T3c: goi thang ENCRYPT_Process2 trong unidbg (khong chay game) cho lib goc va lib da va, roi so voi scripts/re/save_crypto.py.
#   bash emu/crypto_check.sh <libgame goc> <libgame da va> [thu muc ket qua] [logs/save_TEST7]
# Can: emu/.cp_local (classpath unidbg, xem emu/README.md). Ket qua o cuoi: "TONG KET: TAT CA DAT".
set -euo pipefail
cd "$(dirname "$0")/.."
ORIG=$(realpath "$1"); PATCHED=$(realpath "$2")
OUT=${3:-work/cryptocheck}; SAVES=${4:-logs/save_TEST7}
CP=${UNIDBG_CP:-$(cat emu/.cp_local)}
mkdir -p "$OUT" work/emucls_cc
env -u JAVA_TOOL_OPTIONS javac -nowarn -encoding UTF-8 -cp "$CP" -d work/emucls_cc emu/src/CryptoCheck.java
FILTER='^[0-9A-F]{4}: |^size: |md5=|^>-|^\^-|CallInitFunction|log4j:WARN'
for pair in "orig:$ORIG" "patched:$PATCHED"; do
  tag=${pair%%:*}; lib=${pair#*:}
  echo "##### $tag: $lib"
  env -u JAVA_TOOL_OPTIONS java -Xss8m -cp "work/emucls_cc:$CP" CryptoCheck "$lib" "$OUT" "$tag" "$SAVES" 2>&1 | grep -avE "$FILTER"
done
python3 -I scripts/re/crypto_xcheck.py "$ORIG" "$OUT" orig patched
