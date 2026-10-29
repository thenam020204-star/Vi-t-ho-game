#!/bin/bash
# Headless FreeJ2ME test. Usage: test/run.sh <game.jar> <script.txt> <outdir>
# Needs freej2me_plus.jar (from Tool_Viet_Hoa_NAM_P.zip -> NAM_P/freej2me/) in $FJ.
# OPGL stubs are injected into a TEST-ONLY copy of the jar (FreeJ2ME has no com.mexa.opgl).
set -e
H=$(cd "$(dirname "$0")" && pwd); FJ=${FJ:?set FJ=/path/to/freej2me_plus.jar}
W=$(mktemp -d); mkdir -p "$3"
javac -cp "$FJ" -d "$W/h" "$H/Harness.java"
javac --release 8 -d "$W/stub" "$H"/stub/com/mexa/opgl/*.java
cp "$1" "$W/test.jar"; (cd "$W/stub" && zip -q "$W/test.jar" com/mexa/opgl/*.class)
OUT=$(cd "$3" && pwd)
(cd "$W/h" && timeout 60 java $JOPTS -Djava.security.manager=allow -Dhlog="$OUT/harness.log" -Djava.awt.headless=true \
  --add-opens java.base/java.lang=ALL-UNNAMED -cp "$FJ:." Harness "$W/test.jar" ${SCREEN_W:-800} ${SCREEN_H:-480} "$2" "$OUT") || true
cat "$OUT/harness.log"
