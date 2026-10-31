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
