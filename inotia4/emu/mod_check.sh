#!/usr/bin/env bash
# T8d: goi thang 3 diem va cua mod menu (CHAR_AddDamage 0x18b3e0, MERCENARYSYSTEM_AddExperience 0x1c7860,
# MERCENARYSYSTEM_AddExpFixed 0x1c7a44) trong unidbg (emu_start, khong chay game) tren lib da va va lib goc.
#   bash emu/mod_check.sh <libgame goc> <libgame da va> [thu muc ket qua]
# Lib da va: ket qua phai dung cong thuc docs/mod_menu.md. Lib goc (doi chung): moi ca == gia tri dau vao (he so khong co tac dung).
# Man hinh/log chi in ca LOI, ca ngoai mien, bang ca bien va tong hop; log day du tung ca o <ket qua>/<patched|orig>.full.log.
# Can: emu/.cp_local (classpath unidbg, xem emu/README.md). Dong cuoi: "TONG KET: ..." (thoat 0 neu moi ca dat).
set -uo pipefail
cd "$(dirname "$0")/.."
ORIG=$(realpath "$1"); PATCHED=$(realpath "$2")
OUT=${3:-work/modcheck}
CP=${UNIDBG_CP:-$(cat emu/.cp_local)}
mkdir -p "$OUT" work/emucls_mc
env -u JAVA_TOOL_OPTIONS javac -nowarn -encoding UTF-8 -cp "$CP" -d work/emucls_mc emu/src/ModCheck.java || exit 2
FILTER='^[0-9A-F]{4}: |^size: |md5=|^>-|^\^-|CallInitFunction|log4j:WARN'
rc=0
for pair in "patched:$PATCHED" "orig:$ORIG"; do
  tag=${pair%%:*}; lib=${pair#*:}
  echo "##### $tag: $lib (sha256 $(sha256sum "$lib" | cut -c1-16))"
  env -u JAVA_TOOL_OPTIONS java -Xss8m -cp "work/emucls_mc:$CP" ModCheck "$lib" "$tag" "$OUT/$tag.full.log" 2>&1 | grep -avE "$FILTER" | tee "$OUT/$tag.log"
  [ "${PIPESTATUS[0]}" -eq 0 ] || rc=1
done
# Doi chung am tinh: dung sai mo hinh thi test phai bao LOI (chung to test phan biet duoc lib va va lib goc).
for pair in "orig:$PATCHED" "patched:$ORIG"; do
  expect=${pair%%:*}; lib=${pair#*:}
  line=$(env -u JAVA_TOOL_OPTIONS java -Xss8m -cp "work/emucls_mc:$CP" ModCheck "$lib" "$expect" 2>&1 | grep -a "^KET QUA")
  nloi=$(echo "$line" | sed -E 's/.*LOI=([0-9]+).*/\1/')
  if [ "${nloi:-0}" -gt 0 ]; then st="OK"; else st="SAI"; rc=1; fi
  echo "DOI CHUNG AM TINH: lib $(sha256sum "$lib" | cut -c1-8) voi mo hinh '$expect' phai bao LOI: $line => $st"
done
if [ $rc -eq 0 ]; then echo "TONG KET: TAT CA DAT (lib va + lib goc)"; else echo "TONG KET: CO LOI"; fi
exit $rc
