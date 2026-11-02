# Emulator không cần KVM: unidbg + GLES phần mềm

Chạy `libgame.so` (ARM64) thật trong JVM bằng [unidbg](https://github.com/zhkl0228/unidbg) (unicorn), không cần Android/KVM.
- `src/Inotia4Emu.java`: mô phỏng vòng đời Java của `com.com2us.wrapper` (CWrapperData, các module CWrapper, ActiveUser/Mercury/Hub/InAppV4 init, nativeStart/StartClet, vòng `nativeProcess` ~60fps). JNI chưa xử lý thì log `[JNI?]` và trả giá trị rỗng.
- `src/GLES.java`: `libGLESv1_CM.so` ảo, rasterizer phần mềm (ma trận fixed/float, texture 8888/565/4444/5551, blend, alpha test, scissor). Chụp PNG.
- `src/ZipIndex.java`: offset asset trong APK cho `CResource.getFileDescriptorFromAsset` (asset phải STORED).
- `unidbg.patch`: thêm `GetDirectBufferAddress/Capacity` (CEventHandler dùng `ByteBuffer.allocateDirect`).
- Stub chỉ trong emulator: `Protection::isRunningMemoryHack`, `isRooted` (cần `system("ps")`). APK không bị sửa.

## Dựng
```bash
git clone https://github.com/zhkl0228/unidbg && cd unidbg && git apply ../inotia4/emu/unidbg.patch
mvn -q -DskipTests -Dgpg.skip install -pl unidbg-android -am
(cd unidbg-android && mvn -q dependency:build-classpath -Dmdep.outputFile=../cp.txt)
echo "$PWD/unidbg-android/target/unidbg-android-0.9.10-SNAPSHOT.jar:$(cat cp.txt)" > ../inotia4/emu/.cp_local
```
## Chạy
```bash
LIB=<libgame.so> APK=out/Inotia4_vi_offline.apk EMU_OPTS="-Demu.shot=50" bash emu/run.sh 400
# ảnh: work/emuout/fNNNNN.png, last.png ; -Demu.trace=true in mọi JNI ; -Demu.tracefn=a,b in khi vào hàm
```

## Kiểm hàm mã hoá save không cần chạy game (T3c)
```bash
bash emu/crypto_check.sh <libgame.so gốc> <libgame.so đã vá> [thư mục kết quả] [logs/save_TEST7]
```
`emu/src/CryptoCheck.java` nạp lib, gọi thẳng `ENCRYPT_Process2` với khoá rỗng = buffer `HubSave_key`, ghi rác sau buffer (mô phỏng bộ nhớ khác nhau giữa hai tiến trình) rồi mã hoá/giải mã; `scripts/re/crypto_xcheck.py` so kết quả với `scripts/re/save_crypto.py`. Dòng cuối phải là `TONG KET: TAT CA DAT`. Log mẫu: `logs/t3c_crypto_check.log`.

## Kiểm tầm nhìn / HUD / pool (T9c)
```bash
bash emu/view_check.sh <libgame.so gốc> <libgame.so đã vá đủ nhóm> [thư mục kết quả=work/viewcheck] [--title]
```
Chạy 54 lần Inotia4Emu (3 song song): `-Demu.w/-Demu.h` đổi màn hình, `-Demu.poke=0x72f78c=P` ghi int vào `.bss` trước `nativeStart` (giả lập Java ghi trong `onResume`), `-Demu.dump=addr:n,...` in n int32 ở đầu và cuối, `-Demu.pool=true` (+ `-Demu.poolevery=N`) đọc `MEM_GetUsedPoolSize/TotalPoolSize` và ghi đỉnh, `-Demu.hudcall=-1` gọi thẳng `UIPlay_Create(1)` và in rect từng control. `scripts/re/view_xcheck.py` đối chiếu với công thức; dòng cuối phải là `TONG KET: TAT CA DAT`. Log mẫu: `logs/t9c_view_check.log`.
