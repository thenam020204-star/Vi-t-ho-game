#!/bin/bash
# T18: Android emulator khong KVM (TCG) chay headless. Tat ca nam trong work/emu (khong commit).
# Dung:
#   bash tools/emu.sh setup            # tai emulator + platform-tools + system image x86_64 API 33 google_apis (~2.2 GB) + tao AVD
#   bash tools/emu.sh start            # khoi dong nen, doi sys.boot_completed (in thoi gian boot)
#   bash tools/emu.sh install APK      # adb install -r -g (APK gop)
#   bash tools/emu.sh run [pkg]        # monkey LAUNCHER, doi 60 s, logcat -d -> work/emu/out/logcat.txt, screencap -> work/emu/out/shot.png
#   bash tools/emu.sh shot NAME        # screencap -> work/emu/out/NAME.png
#   bash tools/emu.sh logcat [FILTER]  # logcat -d
#   bash tools/emu.sh stop
# Ghi chu (T18, xem PLAN): emulator moi (37.x) tu choi AVD arm64 tren host x86_64 ("Avd's CPU Architecture 'arm64' is not supported");
# -accel off can ANDROID_I_WANT_MY_TCG=yes. Game chi co lib arm64 => can anh x86_64 co ARM translation.
set -euo pipefail
ROOT="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
E="$ROOT/work/emu"; SDK="$E/sdk"; ADB="$SDK/platform-tools/adb"
export ANDROID_SDK_ROOT="$SDK" ANDROID_AVD_HOME="$E/avd" ANDROID_I_WANT_MY_TCG=yes
API="${EMU_API:-33}"; TAG="${EMU_TAG:-google_apis}"; ABI="${EMU_ABI:-x86_64}"; AVD="${EMU_AVD:-x86}"
PKG="${EMU_PKG:-com.vkslrzm.Zombie}"
mkdir -p "$E/out"
REPO=https://dl.google.com/android/repository

setup() {
  mkdir -p "$SDK" "$E/avd/$AVD.avd"; cd "$E"
  [ -x "$SDK/emulator/emulator" ] || { curl -fSL -o emu.zip "$REPO/emulator-linux_x64-16632760.zip"; unzip -q -o emu.zip -d "$SDK"; rm -f emu.zip; }
  [ -x "$ADB" ] || { curl -fSL -o pt.zip "$REPO/platform-tools_r37.0.1-linux.zip"; unzip -q -o pt.zip -d "$SDK"; rm -f pt.zip; }
  local sysdir="$SDK/system-images/android-$API/$TAG"
  if [ ! -d "$sysdir/$ABI" ]; then
    # google_apis x86_64 API 33 = x86_64-33_r17.zip (xem sys-img/google_apis/sys-img2-3.xml)
    mkdir -p "$sysdir"; curl -fSL -o si.zip "$REPO/sys-img/$TAG/$ABI-$API""_r17.zip"; unzip -q -o si.zip -d "$sysdir"; rm -f si.zip
  fi
  cat > "$E/avd/$AVD.ini" <<EOF
avd.ini.encoding=UTF-8
path=$E/avd/$AVD.avd
path.rel=avd/$AVD.avd
target=android-$API
EOF
  cat > "$E/avd/$AVD.avd/config.ini" <<EOF
AvdId=$AVD
PlayStore.enabled=false
abi.type=$ABI
avd.ini.displayname=$AVD
disk.dataPartition.size=6G
hw.accelerometer=no
hw.audioInput=no
hw.battery=no
hw.camera.back=none
hw.camera.front=none
hw.cpu.arch=$ABI
hw.cpu.ncore=4
hw.device.name=pixel
hw.gps=no
hw.gpu.enabled=yes
hw.gpu.mode=swiftshader_indirect
hw.initialOrientation=portrait
hw.keyboard=yes
hw.lcd.density=320
hw.lcd.height=1280
hw.lcd.width=720
hw.ramSize=4096
hw.sdCard=no
hw.sensors.orientation=no
hw.sensors.proximity=no
image.sysdir.1=system-images/android-$API/$TAG/$ABI/
runtime.network.latency=none
runtime.network.speed=full
showDeviceFrame=no
tag.id=$TAG
tag.display=$TAG
vm.heapSize=256
EOF
}

start() {
  local t0; t0=$(date +%s)
  nohup "$SDK/emulator/emulator" -avd "$AVD" -accel off -gpu swiftshader_indirect -no-window -no-audio -no-boot-anim -no-snapshot \
        -cores 4 -memory 4096 -no-metrics -read-only > "$E/out/emulator.log" 2>&1 &
  echo "emulator pid $!"
  while :; do
    b=$("$ADB" -s emulator-5554 shell getprop sys.boot_completed 2>/dev/null | tr -d '\r' || true)
    [ "$b" = 1 ] && break
    sleep 10
    [ $(( $(date +%s) - t0 )) -gt "${EMU_BOOT_TIMEOUT:-3000}" ] && { echo "boot timeout"; exit 1; }
  done
  echo "boot xong sau $(( $(date +%s) - t0 )) s"
  "$ADB" shell getprop ro.product.cpu.abilist
}

case "${1:-}" in
  setup) setup;;
  start) start;;
  install) "$ADB" install -r -g "$2";;
  run) "$ADB" logcat -c; "$ADB" shell monkey -p "${2:-$PKG}" -c android.intent.category.LAUNCHER 1; sleep "${EMU_WAIT:-60}"
       "$ADB" logcat -d -v time > "$E/out/logcat.txt"; "$ADB" exec-out screencap -p > "$E/out/shot.png"; ls -l "$E/out";;
  shot) "$ADB" exec-out screencap -p > "$E/out/${2:-shot}.png";;
  logcat) "$ADB" logcat -d -v time ${2:+| grep -E "$2"};;
  stop) "$ADB" emu kill || true;;
  *) sed -n 2,13p "$0"; exit 2;;
esac
