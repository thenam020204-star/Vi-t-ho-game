#!/usr/bin/env bash
# Tai cong cu vao work/tools/ (idempotent). Chay: bash tools/setup_tools.sh
set -euo pipefail
cd "$(dirname "$0")/.."
T=work/tools; mkdir -p "$T"; cd "$T"
pip install --quiet UnityPy fonttools freetype-py capstone pillow
[ -x dotnet/dotnet ] || { curl -sSL -o dotnet-install.sh https://dot.net/v1/dotnet-install.sh
  bash dotnet-install.sh --channel 8.0 --runtime dotnet --install-dir ./dotnet; }
[ -d il2cppdumper ] || { curl -sSLO https://github.com/Perfare/Il2CppDumper/releases/download/v6.7.46/Il2CppDumper-net6-v6.7.46.zip
  unzip -q -o Il2CppDumper-net6-v6.7.46.zip -d il2cppdumper; }
[ -d android-15 ] || { curl -sSLO https://dl.google.com/android/repository/build-tools_r35_linux.zip
  unzip -q -o build-tools_r35_linux.zip; }
[ -f APKEditor-1.4.9.jar ] || curl -sSLO https://github.com/REAndroid/APKEditor/releases/download/V1.4.9/APKEditor-1.4.9.jar
[ -f apktool_2.12.0.jar ] || curl -sSLO https://github.com/iBotPeaches/Apktool/releases/download/v2.12.0/apktool_2.12.0.jar
echo OK
