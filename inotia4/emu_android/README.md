# Giả lập Android ARM64 đầy đủ (chạy cả phần Java/ART) trên host x86 không KVM

Bổ sung cho `emu/` (unidbg chỉ chạy native `libgame.so`). Dùng **Android Emulator 37.2.13** chạy ảnh hệ thống
`system-images;android-29;default;arm64-v8a` bằng **TCG** (dịch lệnh bằng phần mềm), không cần `/dev/kvm`.

## Trạng thái (2026-10-31): chạy được nhưng RẤT chậm, chưa dùng được để test game
- Launcher `emulator` 31+ từ chối arm64 trên host x86. Gọi thẳng `qemu/linux-x86_64/qemu-system-aarch64-headless -avd ...` thì vượt qua được bước kiểm này.
- Cần vá nhị phân qemu bằng `patch_qemu.py`:
  1. `-soundhw hda` → `-L`. Máy ranchu arm64 trên host x86 không có bus PCI, nên bị lỗi "PCI bus not available for hda".
  2. ranchu không gán `psci-conduit` khi chạy TCG, nên kernel báo "psci: no cpu_on method" và chỉ chạy được 1 CPU. Bản vá thêm PSCI v0.2 qua HVC, sau đó boot được 4 CPU.
- Chạy 1 vCPU: Android 10 boot xong trong khoảng 8 phút, nhưng `system_server` bị Watchdog giết liên tục (`*** WATCHDOG KILLING SYSTEM PROCESS`). Vì vậy không cài được APK 50 MB.
- Chạy 4 vCPU (MTTCG): mỗi vCPU chỉ được khoảng 60% một nhân host, đồng bộ TLB lại tốn kém, nên còn chậm hơn bản 1 vCPU.
- Ảnh arm64 API 24 (Android 7) bị zygote64 văng SIGSEGV liên tục dưới TCG ở cả qemu 2.12 (emulator 30.9.5) lẫn qemu 8 (emulator 37).
- Không có `binder` trong kernel container, nên Redroid và Waydroid không chạy được.

Kết luận: muốn có logcat thật thì nhanh hơn là dùng **bản chẩn đoán** (`scripts/diag_build.sh`) trên máy thật. Bản này tự hiện nguyên nhân văng ở lần mở sau.

## Dựng lại (nếu cần thử tiếp)
```bash
export ANDROID_HOME=/opt/android ANDROID_AVD_HOME=/opt/avd
sdkmanager "emulator" "platform-tools" "system-images;android-29;default;arm64-v8a"   # emulator 37.2.13
python3 patch_qemu.py $ANDROID_HOME/emulator/qemu/linux-x86_64/qemu-system-aarch64-headless
avdmanager create avd -n a29 -k "system-images;android-29;default;arm64-v8a" -d pixel
# config.ini: hw.audioInput=no hw.audioOutput=no hw.keyboard=no hw.cpu.ncore=N hw.lcd.width=960 hw.lcd.height=540
cd $ANDROID_HOME/emulator && LD_LIBRARY_PATH=$PWD/lib64:$PWD/lib64/qt/lib \
  ./qemu/linux-x86_64/qemu-system-aarch64-headless -avd a29 -no-window -no-audio -no-snapshot \
  -no-boot-anim -accel off -gpu swiftshader_indirect -feature -Vulkan,-VirtioInput -cores N
# tat VirtioInput (can PCI) -> phai tao /dev/input, neu khong system_server abort:
adb root && adb shell 'mkdir -p /dev/input'
```
Lưu ý: `pkill -f <mẫu>` sẽ giết luôn shell của chính lệnh đó nếu mẫu nằm trong dòng lệnh. Nên kill theo PID.
