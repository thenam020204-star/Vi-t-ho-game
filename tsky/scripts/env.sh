# Moi truong du an (duong dan tuong doi theo thu muc du an). source scripts/env.sh
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]:-$0}")/.." && pwd)"; export ROOT
TOOLS="$ROOT/tools"; [ -d "$TOOLS" ] || TOOLS="$ROOT/work/tools"; export TOOLS J2ME_TOOLS="$TOOLS"
export LIBS="$TOOLS/lib"
export FJ="$TOOLS/freej2me_plus.jar" CFR="$TOOLS/cfr.jar" PROGUARD="$TOOLS/proguard.jar" FONT_DIR="$TOOLS/fonts"
export BITMAPFONT_CLASS="$TOOLS/java/BitmapFont.class" BITMAPFONT_JAVA="$TOOLS/java/BitmapFont.java"
export PYTHONPATH="$TOOLS/pixeltext_kit${PYTHONPATH:+:$PYTHONPATH}"
export S="$ROOT/scripts"
