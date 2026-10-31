# PROGRESS — Inotia 4 Android Việt hoá + offline

Đọc file này đầu mỗi session. Kế hoạch đầy đủ: `PLAN.md`.

## Đã xong
- Hạ tầng: `scripts/rebuild.sh <thư mục splits>` → `out/Inotia4_vi_offline.apk` (gộp split bằng APKEditor, apktool, vá smali, text, classes3.dex VNFont, fix_zip, ký v1/v2/v3 bằng `keystore/namp-inotia4.jks`). `check_apk.py` PASS.
- Text: `scripts/memtext.py` (unwrap/wrap LZMA `01 00`+alone, parse/build bảng u24). Selftest giống từng byte cho 5 file ngôn ngữ. `dich/text_can_dich.tsv` gồm 10.602 mục (id, en, ko, vi).
- Font: `font/f18vn.bin` = f18 NAM_P + 6 glyph vẽ tay (`_ = ~ ’ ‘ ★`), sinh bằng `fontgen_inotia/extra18.py`. `java/src/com/namp/vh/VNFont.java` thay thân 5 method của CFont/CSGLFont. Hệ số phóng k=max(1,(size+2)/18): cỡ 14/20/24/28 → ×1, cỡ 38 → ×2.
- Ngôn ngữ: `CFunction.getLanguage` luôn trả `"en"`; bản Việt được chép vào mọi slot `memorytext*.dat.jpg`.

## Đang làm / còn lại
- [x] Offline khởi động, đã kiểm trên emulator unidbg (`emu/`):
  - `ActiveUser.start` báo thành công ngay; AgeRange trả `(1,4)`;
  - Hub `device/registered` trả `is_registered=false`; `auth` và mọi request khác trả `error_code=1` (offline); không tạo Peppermint;
  - bỏ PlayGames sign-in;
  - vá lib `0x203e68`: bỏ popup "Đăng nhập Hive?" khi bấm START;
  - gỡ quyền INTERNET, BILLING, C2DM.
  - Kết quả trên emulator: START vào thẳng màn chọn ô save.
- [ ] Mua miễn phí (IAP và Gem). Gem nằm trên server game (socket `inotia4.com2us.net:38000`) nên chưa làm.
- [ ] Emulator sập ở màn chọn save (hỏng heap trong unidbg). Theo yêu cầu người dùng thì không đào sâu; cần test máy thật.
- [x] Dịch xong 10.602 mục (8.392 chuỗi khác nhau) bằng `dp`, `dp check` OK, `memtext check` OK (đủ glyph). Credits giữ tên gốc. `dp` có thêm khoá `code0` trong meta để không coi `<...>` là mã (game không có thẻ).
  - Build thử: `test_builds/Inotia4_TEST4_vi_anh_offline.apk` (check_apk PASS). Mẫu render VNFont: `docs/emu_shots/vnfont_mau_thoai.png`.
- [x] Text UI trong `res/xml/{config,hive,clause,hubjoin}.xml`: `dich/xml_text.tsv` (30 key) → `scripts/plist_text.py apply` thay mọi ngôn ngữ trong array (154 `<string>`), đã gắn vào `rebuild.sh`.
- [x] Ảnh có chữ: 10 atlas (`UI_BUT_ENG_001..005`, `UI_TITLEMAIN_BUT_ENG_001`, `UI_006`, `UI_008`, `UI_TITLESLOT_01`, `UI_TITLE_SETTINGS_01`) vẽ lại bằng f18 ×2/×3 qua `scripts/img_vi.py` + `res_vi/labels.json` (xoá chữ bằng nền sạch dựng từ các nút cùng kiểu/lật gương). Ảnh xem trước: `logs/anh_vi_preview.png`. Giữ nguyên: tên ngôn ngữ, logo, quảng cáo `ETC_ban_*` (bản offline không hiện), ruy băng NEW/HOT.
- [ ] Test trên máy thật: container không có /dev/kvm nên không chạy được Android emulator.

## Ghi chú kỹ thuật
- Native libgame.so tạo 5 font: size 20/24/38/28/14, cao dòng 22/26/40/30/16 (`GRPX_CreateFont` @0x12a6ac).
- File gốc không có trong git: đặt `base.apk` + `split_config.*.apk` vào một thư mục rồi chạy rebuild (sha256 ở `goc/SHA256.txt`).

## LỖI MỞ: TEST4 văng ngay lúc tải trên máy thật (người dùng báo 2026-10-31)
Màn hình: nền đen + vòng xoay tải Android, rồi văng. Emulator unidbg chỉ chạy native nên **chưa từng chạy phần Java trên ART thật**. Chưa có logcat, nên nguyên nhân CHƯA XÁC ĐỊNH.

Đã kiểm (tĩnh):
- Không có lớp Application riêng. LAUNCHER = `MainActivity` (extends CWrapperActivity, gọi `HiveActivity.onCreate`...).
- `ActiveUser.start` không có caller smali (native gọi qua JNI). `Offline.*` dùng `CFunction.runOnGlThread` → `CFunction.mGLSurfaceView.queueEvent`. Nếu bị gọi trước khi GL view được gán thì NPE (cần xác minh).
- Manifest không còn split bắt buộc. Quyền còn lại: VIBRATE, ACCESS_WIFI_STATE, ACCESS_NETWORK_STATE, WAKE_LOCK, ...
- Hercules (`com.com2us.security.Hercules`, libHercules.so) vẫn chạy; APK đã ký lại bằng khoá khác, nên có thể bị chống sửa. Chưa kiểm.

Nghi vấn theo thứ tự ưu tiên:
1. **Gỡ INTERNET**: không có quyền này, DNS ném `SecurityException` (RuntimeException), có thể làm sập thread của Hive/Firebase/Facebook/PlayGames init provider. Vòng xoay rất giống màn setup của Hive.
2. Hercules phát hiện chữ ký mới hoặc APK bị sửa.
3. NPE trong `Offline` (`mGLSurfaceView` null).
4. classes3.dex: d8 báo "java.lang.Runnable not found" vì chưa truyền `--lib android.jar`. Cần build lại với `--lib`.

Việc tiếp theo (đề xuất):
- Làm bản chẩn đoán: ContentProvider `initOrder` cao cài `UncaughtExceptionHandler` và đọc `logcat -d` của chính uid lúc khởi động. Nếu lần chạy trước có `FATAL EXCEPTION`/`Fatal signal` thì mở Activity hiện log kèm nút Chia sẻ. android.jar API 16 tải từ Maven: `com.google.android:android:4.1.1.4`.
- Gửi 2 bản để thu hẹp nguyên nhân: (a) như TEST4 + chẩn đoán; (b) trả lại quyền INTERNET + chẩn đoán.
- Hoặc nhờ người dùng gửi logcat (`adb logcat -b crash`).
- d8: thêm `--lib android.jar`.
