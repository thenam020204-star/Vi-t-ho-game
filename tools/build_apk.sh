#!/bin/bash
# Dung: bash tools/build_apk.sh TAG FONT.ttf   (vd: f18 work/font_px/JejuHallasan-f18B.ttf ; jeju work/font/JejuHallasan-VI.ttf)
set -e
cd "$(dirname "$0")/.."
source tools/env.sh
python3 -I tools/build_apk.py "$@"
