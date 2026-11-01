# Phiên bản tool dùng để dựng APK Inotia 4 Việt hoá

Ghi ở T5 (2026-11-01). Mọi phiên bản dưới đây lấy bằng lệnh thật trong container dựng TEST12 và bản `Inotia4_T5_*`; in lại cùng danh sách bằng `bash scripts/rebuild.sh --versions`. Các file nhị phân tool **không** nằm trong repo (`.gitignore`: `tools/*.jar`, `tools/jadx/`, `tools/NAM_P/`); tải/dựng lại đúng phiên bản và đặt vào `tools/` hoặc đường dẫn mặc định ở cột cuối, hoặc ghi đè bằng biến môi trường (`APKTOOL`, `APKEDITOR`, `BT`, `ANDROID_JAR`).

## 1. Chuỗi dựng APK (bắt buộc)

| Tool | Phiên bản | Lệnh kiểm | Đường dẫn mặc định / sha256 | Dùng ở |
|---|---|---|---|---|
| apktool | **2.12.1** | `java -jar apktool_2.12.1.jar --version` → `2.12.1` | `tools/apktool_2.12.1.jar` hoặc `/opt/re/apktool_2.12.1.jar` (sha256 ở mục 6) | decode/build (cả `build_from_apk.sh` và `rebuild.sh`); apktool tự mang aapt2 riêng |
| Android SDK build-tools | **35.0.0** | `Pkg.Revision=35.0.0` trong `source.properties` | `/opt/android/build-tools/35.0.0` | d8, apksigner, zipalign |
| d8 (R8) | `D8 8.6.2-dev (build abaab469b5eb…)` (bản đi kèm build-tools 35.0.0) | `java -cp $BT/lib/d8.jar com.android.tools.r8.D8 --version` | `$BT/lib/d8.jar` | dex hoá `com.namp.vh` (`--release --min-api 23 --lib android.jar --lib <stub>`) và dex chẩn đoán |
| apksigner | `0.9` (build-tools 35.0.0) | `java -jar $BT/lib/apksigner.jar --version` | `$BT/lib/apksigner.jar` | ký v1+v2+v3, `verify` |
| zipalign | build-tools 35.0.0 (không có cờ `--version`) | `$BT/zipalign -c -P 16 4 <apk>` | `$BT/zipalign` | chỉ kiểm (căn chỉnh do `scripts/fix_zip.py` làm: `.so` căn 16 KB, còn lại 4 B) |
| android.jar | **API 34** (platform android-34, revision 3, `ApiLevel=34`) | `AndroidVersion.ApiLevel=34` trong `source.properties` | `/opt/android/platforms/android-34/android.jar` (sha256 ở mục 6) | `--lib` cho `javac` và `d8` (ModMenu dùng `android.app`/`android.widget`); `java/stubs` là `--lib` thứ hai cho d8 |
| JDK | **OpenJDK 21.0.12.1** (Ubuntu 24.04) | `java -version`, `javac -version` | hệ thống | `javac --release 8` cho `java/src`, `java/stubs`, `java/diag`; `keytool` chỉ khi phải tạo keystore mới |
| Python | **3.13.18** | `python3 --version` | hệ thống | mọi `scripts/*.py` chạy `python3 -I` |
| Pillow | **12.3.0** | `python3 -c "import PIL; print(PIL.__version__)"` | pip | `scripts/hud_swap.py` (Lanczos), `scripts/img_vi.py` |
| numpy | **2.5.3** | `python3 -c "import numpy; print(numpy.__version__)"` | pip | `scripts/hud_swap.py` (selftest, ghép alpha) |
| Keystore ký | JKS, alias `namp`, chứng chỉ SHA-256 `cc5596513df8057b28e39bb12b1cb88b3c0e674ef50fd3e621bcdae9b07520d4` | `apksigner verify --print-certs` | `keystore/namp-inotia4.jks` (trong repo) | cùng khoá ký ⇒ cài đè được lên mọi bản TEST |

## 2. Chỉ khi dựng từ bộ split gốc (`rebuild.sh <thư mục split>`)

| Tool | Phiên bản | Ghi chú |
|---|---|---|
| APKEditor | **1.4.1** (ARSCLib 1.3.5) đã dùng để gộp split ra TEST1/TEST4 (`logs/merge.log`: `APKEditor version 1.4.1`). Trong container hiện tại chỉ có **1.4.5** (`/opt/re/APKEditor-1.4.5.jar`, sha256 ở mục 6; `java -jar APKEditor-1.4.5.jar -h` → `APKEditor - 1.4.5`) | `rebuild.sh` ưu tiên `tools/APKEditor-1.4.1.jar`, rồi 1.4.5. **1.4.5 mới được thử gộp 1 `base.apk` (TEST4) rồi decode bằng apktool 2.12.1: cây decode giống bản decode thẳng TEST4, chỉ `apktool.yml` khác (`apkFileName`, mất dòng `- so` trong `doNotCompress`, `fix_zip.py` đằng nào cũng ghi lại `.so` STORED). Gộp 4 split thật CHƯA chạy (không có split trong container).** |
| Tool_Viet_Hoa_NAM_P (pixeltext_kit) | trong `Tool_Viet_Hoa_NAM_P.zip` (gốc repo) | `rebuild.sh` tự giải nén vào `tools/NAM_P/` khi cần (`scripts/img_vi.py` vẽ lại nhãn ảnh) |

## 3. Chỉ để đọc/kiểm, KHÔNG nằm trong chuỗi dựng APK

| Tool | Phiên bản | Dùng ở |
|---|---|---|
| jadx | **1.5.3** (`/opt/re/jadx/bin/jadx --version` → `1.5.3`; `jadx-1.5.3.zip`, sha256 ở mục 6) | đọc mã Java (`/opt/work/jx4/sources`), không sửa APK |
| LLVM | **18.1.3** (Ubuntu): `llvm-objdump`, `llvm-mc`, `ld.lld`, `llvm-readelf` | RE `libgame.so`, `patch_lib.py --disasm/--check-asm`, `scripts/asm/build_*_cave.sh` |
| unidbg | `0.9.10-SNAPSHOT` (commit `e0d88a9` + `emu/unidbg.patch`), Maven 3.9.11 | `emu/*` (kiểm cave, tầm nhìn, HUD) |
| aapt2 (build-tools 35.0.0) | `2.19-11948202` | chỉ `aapt2 dump xmltree` để so manifest giữa hai APK |

## 4. Tool cũ (không dùng nữa)

`rebuild.sh` trước T5 dùng `apktool_2.10.0.jar` (`logs/decode.log`: `Using Apktool 2.10.0`), `APKEditor-1.4.1.jar`, `r8.jar` (d8 không có `--lib android.jar`), `uber-apk-signer-1.3.0.jar`. TEST1–TEST6 dựng bằng bộ này; từ T5 `rebuild.sh` dùng đúng bộ ở mục 1 như `build_from_apk.sh`.

## 5. Cờ build chuẩn tương đương TEST12

```bash
# từ APK nền TEST4 (container không có split gốc)
APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/<ten>.apk \
   --smali net,mod --vnfont --lib --libgroup base,p1,mod,view,hud,pool,hudbtn --hud [--diag]
# từ bộ split gốc (đặt base.apk + split_config.*.apk vào một thư mục, sha256 ở goc/SHA256.txt)
bash scripts/rebuild.sh <thư mục split> [--out out/<ten>.apk] [--diag]
# kiểm rebuild.sh khi không có split (nền đã dịch / chưa dịch)
bash scripts/rebuild.sh --base-apk test_builds/Inotia4_TEST4_vi_anh_offline.apk --out out/<ten>.apk
bash scripts/rebuild.sh --base-apk test_builds/Inotia4_TEST1_font18_offline_EN.apk --base-kind en --out out/<ten>.apk
```

Bản phát hành (T6) **không** `--diag` và **không** `--internet`.

## 6. sha256 file tool (kiểm đã tải đúng bản)

```
66cf4524a4a45a7f56567d08b2c9b6ec237bcdd78cee69fd4a59c8a0243aeafa  apktool_2.12.1.jar
71999a1f28cf6b457aff17c139436349cd6ea30d75a0f9cd52f07bd52e21897b  APKEditor-1.4.5.jar
8280f3799c0273fe797a2bcd90258c943e451fd195f13d05400de5e6451d15ec  jadx-1.5.3.zip
6cea1df3efb77103ac3e2beb9bf4718964b0e0869ab16d39d29d5cbae1c147ad  android-34/android.jar
```
