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

## Phiên 2 (tiếp): TEST6 chạy được; lỗi văng khi load save; font to hơn (TEST7)
- **TEST6 chạy được trên máy thật** (vào game, xem được đoạn mở đầu).
- **Lỗi mới khi load save** (`logs/may_that_TEST6_load_save_2026-10-31.txt`): `CRASH_NATIVE` SIGSEGV, fault addr 0x328, tại `CHAR_GetSkillPoint` (`ldrb w0,[x0,#0x328]`) ← `GAMESTATE_EnterPlay` (0x13840c) ← `GAMESTATE_SetState` ← `GAMESTATE_ProcessMapChange`.
  Con trỏ nhân vật chính (global qua GOT `0x2bebd8`) bằng null. Chỉ `SAVE_LoadCharacterAll` mới gán nó, và chỉ khi `SAVE_LoadFile` → `SAVE_LoadData` thành công. Nếu thất bại thì `SAVE_Load` bỏ qua phần nạp nhân vật mà không báo lỗi, sau đó `EnterPlay` văng.
  - Khoá và thư mục save: `HubSave_GetKey` lấy app property `Hub2uniqueUserId` / `Hub2uniqueDeviceId`, chỉ được set ở `hubCallbackAuth` / `GuestAuth` / `GuestBind` (bản offline không chạy tới các hàm này). Lưu (`SAVE_SaveData`) và đọc dùng cùng logic, nên khoá không lệch giữa lúc lưu và lúc đọc.
  - `SAVE_LoadFile` kiểm version ≤ 5 và chỉ số ô save, và cần Inventory/Quest/Event đọc được. **Nguyên nhân cụ thể CHƯA XÁC ĐỊNH**: cần file save thật.
  - Màn chọn save gọi `HttpConnectPage(6,0)`. Khi HTTP lỗi thì hiện `PopupNetError` (bảng `0x2b9878`). Đây là hành vi bình thường khi offline.
  - unidbg văng ở màn chọn save trong `run_quantize` của jemalloc thuộc libc giả lập (size 0). Lỗi riêng của unidbg, chưa sửa.
- Bản chẩn đoán đã nâng cấp: giải mã frame tombstone (rel_pc + hàm + offset), liệt kê file dữ liệu app, nút **Xuất save** (zip file ≤ 3 MB, bỏ cache → `Download/inotia4_save_*.zip`).
- **Font**: VNFont đổi từ bội số nguyên `k=(size+2)/18` sang hệ số `q/16` với `q=max(16,size)`, phóng nearest-neighbour; `GAP` 4→2.
  Số đo: native luôn xin cỡ 24 (khung 565), không phụ thuộc màn hình. Chữ trên máy chỉ bằng khoảng 60% chữ gốc. Phóng ×2 sẽ vượt bộ đệm `drawText` (cao `size*1.3`), làm cắt chữ. GPU cũng đã phóng không nguyên (×1,6875 ở 2400×1080). Ảnh so sánh: `docs/emu_shots/font_cu_moi_co24.png`.
- `scripts/build_from_apk.sh` (thay `diag_build.sh`, file cũ giờ là wrapper) với các cờ `--smali`, `--vnfont`, `--diag`, `--internet`.
- `test_builds/Inotia4_TEST7_vi_offline.apk` = TEST4 + `net` + font mới + chẩn đoán. So với TEST6 chỉ khác `classes3.dex` và `classes4.dex`.

## ĐÃ TÌM RA NGUYÊN NHÂN VĂNG (báo cáo TEST5a, Xiaomi 23113RKC6C, Android 16)
Log đầy đủ: `logs/may_that_TEST5a_2026-10-31.txt`. Lỗi `CRASH(Java)` ở luồng `Thread-19`:
`java.lang.SecurityException: Permission denied (missing INTERNET permission?)` tại `com.com2us.wrapper.network.CWrapperHttp$1.run` (`getResponseCode`).
Chuỗi gọi trong `libgame.so`: `Protection::initialize` → luồng `threadDownloadHackAppData` → `Protection::NetConnectCB` → `CS_netHttpOpenEx/ConnectEx` (tải danh sách app hack) → `CWrapperHttp`.
Thiếu quyền INTERNET thì DNS ném `SecurityException` (RuntimeException), mà luồng này chỉ bắt `IOException`, nên app văng. Emulator unidbg không gặp lỗi này vì đã stub `Protection`.
Các SDK khác (Firebase Installations, Facebook) cũng gặp `SecurityException` nhưng tự bắt được, không làm văng.

**Sửa** (`patch_smali.py`, nhóm `net`, đã đưa vào `rebuild.sh`): thêm `.catch Ljava/lang/SecurityException;` vào 2 vùng try của `CWrapperHttp$1.run()`, dùng chung handler `:catch_0` (in stack, `iHttpStatus=-1`, `nativeConnectCB(fd,-1)`). Đây đúng là nhánh "máy không có mạng" của bản gốc. Không đổi lệnh nào khác.
Kiểm tra: smali TEST6 so với TEST4 chỉ khác 2 dòng `.catch`; `dexdump` cho thấy catch table có `IOException` và `SecurityException` cùng trỏ `0x0055`.
- `test_builds/Inotia4_TEST6_vi_offline.apk` = TEST4 + bản sửa `net` + vẫn giữ bản chẩn đoán (để bắt lỗi kế tiếp nếu còn). Vẫn KHÔNG có INTERNET.

## Cập nhật 2026-10-31 (phiên 2): khoanh vùng lỗi văng TEST4
Người dùng: máy thật chạy **Android 14–16**, **chưa từng thử TEST1**.

Đã xác minh:
- **TEST1 và TEST4 có dex, manifest và lib giống từng byte.** Chỉ khác 21 file: 10 atlas ảnh, 7 `memorytext*.dat.jpg` và 4 `res/xml` (config, hive, clause, hubjoin; chỉ đổi chữ, cấu trúc giữ nguyên).
- **Native với toàn bộ asset của TEST4 chạy tới màn tiêu đề bình thường** (unidbg `emu/`, 600 frame; ảnh `docs/emu_shots/test4_title_unidbg.png`). Text tiếng Việt (giải nén 711.513 byte so với 619.103 bản Anh), ảnh và pool `MEM_Create` (6–8 MB) đều không làm văng lúc tải.
- `libgame.so` và `libHercules.so` có LOAD align 0x4000, nên không lỗi trên máy trang 16 KB.
- ⇒ Nghi phạm còn lại nằm ở phần **Java/manifest dùng chung**, chạy trên Android 14–16:
  1. gỡ INTERNET (DNS ném `SecurityException`, là RuntimeException);
  2. Hercules (`libHercules.so` import `exit`/`kill`/`abort`, APK đã ký lại);
  3. lỗi ART/Android 14+ khác.
- Giả lập Android đầy đủ không KVM: xem `emu_android/README.md`. Chạy được nhưng quá chậm (Watchdog giết system_server), nên đã dừng hướng này.

Bản chẩn đoán (`scripts/diag_build.sh`, code `java/diag/`). Chỉ thêm `classes4.dex` và sửa manifest; mọi file khác giữ nguyên từng byte so với TEST4, cùng khoá ký nên cài đè được:
- `DiagProvider` (initOrder 1000) cài `UncaughtExceptionHandler`. `LaunchActivity` là LAUNCHER trung gian: bình thường mở thẳng `MainActivity`; nếu lần trước thoát bất thường thì hiện báo cáo gồm ApplicationExitInfo (CRASH / CRASH_NATIVE kèm chuỗi tombstone / EXIT_SELF / SIGNALED / ANR), stack Java và logcat của app. Có nút Lưu vào Download / Chia sẻ / Sao chép / Vào game.
- `test_builds/Inotia4_TEST5a_chandoan.apk` = TEST4 + chẩn đoán (vẫn KHÔNG có INTERNET).
- `test_builds/Inotia4_TEST5b_chandoan_internet.apk` = TEST5a + trả lại quyền INTERNET (để thử giả thuyết 1).
- Cài **đè** lên TEST4 (không gỡ): lịch sử thoát của TEST4 vẫn còn, nên ngay lần mở đầu có thể đã hiện nguyên nhân.

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

## Phiên 3: sửa văng khi load save (T3a–T3c) — TEST8
- **Nguyên nhân (đã chứng minh, `docs/save_format.md`)**: khoá save rỗng ở bản offline ⇒ `ENCRYPT_Process2` (0x140970) chia modulo cho 0 ⇒ đọc `key[r+i]` vượt buffer `HubSave_key` (64 B) sang con trỏ heap (`gImgUI`, `g_pSGL*`, `s_cachedTexture`) đổi theo mỗi lần chạy ⇒ save ghi ở lần chạy A không giải mã được ở lần chạy B ⇒ `SAVE_LoadData` false ⇒ `PLAYER_pMainPlayer` NULL ⇒ SIGSEGV. Bằng chứng: known-plaintext trong `save0.dat` (hằng 140/20132/5 ở Info) cho thấy byte nhớ `0xb4` (tag con trỏ heap) tại `0x2d0d67`, `0x2d0d6f`; unidbg tái hiện đúng cơ chế (`logs/t3c_crypto_check.log`).
- **Công cụ**: `scripts/re/save_crypto.py` (giải/mã hoá + kiểm tổng, `--selftest`), `scripts/re/crypto_xcheck.py`, `emu/src/CryptoCheck.java` + `emu/crypto_check.sh` (gọi thẳng hàm trong unidbg, so lib gốc/lib vá/script).
- **Vá lib (P1)**: `scripts/patch_lib.py` patch 0x1409a4..0x1409bf: `L = max(strlen(key),1)`. Không cave (không có khe ≥16 B trong segment R-X), viết lại 7 lệnh trong thân hàm; khoá NULL vẫn "123"; khoá khác rỗng không đổi. `scripts/build_from_apk.sh` có cờ `--lib`. Patch cũ 0x203e68 được nhận là "đã áp" trên lib TEST4.
- **Kết quả unidbg (khoá rỗng, 3189 B, mã hoá khi bộ nhớ sau `HubSave_key` = rác G1; giải mã khi G1 / khi G2)**: lib gốc G1 ⇒ true, G2 ⇒ **false**; lib vá G1 ⇒ true, G2 ⇒ **true**. Khoá khác rỗng/NULL/n=0/n=28: bản mã lib gốc == lib vá == `save_crypto.py`. Lib gốc == mô hình đọc vượt buffer từng byte.
- **`test_builds/Inotia4_TEST8_vi_offline.apk`** = TEST7 + lib đã vá (`--smali net --vnfont --lib --diag`; vẫn KHÔNG có INTERNET). SHA256 ở `test_builds/SHA256_TEST8.txt`. So TEST7 chỉ khác `lib/arm64-v8a/libgame.so`; cùng khoá ký ⇒ cài đè được.
- **Nhờ người dùng thử**: cài đè TEST8 ⇒ game mới (nên dùng ô save mới hoặc ghi đè ô 0; **save cũ của TEST7 không cứu được**, nạp lại ô đó vẫn văng vì chưa áp P2) ⇒ lưu ⇒ tắt hẳn app (vuốt khỏi đa nhiệm) ⇒ mở lại ⇒ load. Nếu văng, mở bản chẩn đoán, bấm Xuất save và gửi.
- **CHƯA XÁC ĐỊNH**: khoá của `appinfo.dat` (`UTIL_pPhoneNumber`, giải mã với khoá rỗng cho Σthô sai ⇒ khoá khác rỗng hoặc bộ nhớ sau nó khác 0); `envinfo.dat` có mã hoá không; chữ `A` cuối thư mục `thpcheckslot`.
- **P2 (chỉ đề xuất, chưa áp)**: `docs/p2_load_fail_guard.md` — khi `SAVE_Load` thất bại thì `GAMESTATE_SetState(4)` (về menu chính) thay vì vào `EnterPlay` với hero NULL; cần cave trong thân `SAVE_SaveData` (hàm chết đã chứng minh). Chờ quản đốc duyệt và T2 để kiểm chứng.
- **Người dùng xác nhận (2026-11-01)**: TEST8 lưu → tắt hẳn app → mở lại → load OK trên máy thật.

## Phiên 4: font cỡ lớn f24vn (T7) — TEST9
- **Nguyên nhân mất dấu (T7a, `docs/font_lon.md`)**: dấu của f18 chỉ 1 px; phóng ×1,5 rồi GPU lọc `GL_LINEAR` ×1,6875 (`SGL_Texture::Create` 0x2648f8–0x26490c) làm nhoè. Native không cắt hàng (đệm đủ H hàng). Kết luận dựa trên disasm + mô phỏng; chưa có ảnh máy thật.
- **Font mới `font/f24vn.bin`** (233 glyph, ô 28 hàng, BASE 23; mọi dấu ≥2×2 px; `check_f24.py` 0 vi phạm), sinh bằng `fontgen_inotia/gen_f24.py`.
- **VNFont**: `size<24` giữ f18 như TEST7; `size>=24` dùng f24 hệ số size/24 (chỉ phóng to). Dữ liệu `FontData24.PARTS`.
- **`test_builds/Inotia4_TEST9_vi_offline.apk`** = TEST8 + font f24 (`--smali net --vnfont --lib --diag`). SHA256 ở `test_builds/SHA256_TEST9.txt`. So TEST8 chỉ khác `classes3.dex` (lớp `com/namp/vh/*`); cùng khoá ký ⇒ cài đè được, save giữ nguyên.
- **Nhờ người dùng thử**: cài đè TEST9, xem chữ hội thoại/menu có đủ dấu không (đặc biệt ặ ộ ợ ự ỡ ữ Ấ Ổ), chữ có tràn khung không.

## Phiên 5: mod menu (T8) — TEST10
- **Native (`patch_lib.py` nhóm `mod`)**:
  - `CHAR_AddDamage` 0x18b3e0: phe người chơi (byte `[char+0x311]` = 0) gây sát thương × Mdmg, trần 999.999; nhận sát thương ÷ Mdef, tối thiểu 1.
  - `MERCENARYSYSTEM_AddExperience` 0x1c7860 và `AddExpFixed` 0x1c7a44: EXP × Mexp, trần 0xFFFFFF.
  - Cave 236 B ở 2 hàm chết 0x18bb00..0x18bbec. Hệ số đọc từ `.bss` 0x72f780/784/788; chỉ nhận 2..50, giá trị khác coi như x1.
- **Java**: `com.namp.vh.ModMenu` (nút MOD nổi ở lề trái, kéo thả được; 3 thanh x1–x50; lưu SharedPreferences) và `ModMem` (ghi qua `/proc/self/mem`, dự phòng `Unsafe`, đọc lại để kiểm). Gắn bằng 1 lệnh smali sau `HiveActivity.onResume` trong `MainActivity.onResume`.
- **Kiểm**: unidbg gọi thẳng 3 điểm vá, 21.772/21.782 ca đúng công thức (10 ca ngoài miền: EXP âm). Log ở `logs/t8d_mod_check.log`, chi tiết ở `docs/mod_menu.md`.
- **`test_builds/Inotia4_TEST10_vi_offline.apk`** = TEST9 + mod (`--smali net,mod --vnfont --lib --diag`, lib = P1 + mod). SHA256 ở `test_builds/SHA256_TEST10.txt`. So TEST9 chỉ khác `classes.dex`, `classes3.dex`, `libgame.so`; cùng khoá ký ⇒ cài đè được, save giữ nguyên.

## Phiên 6: tầm nhìn rộng + HUD lùi vào trong (T9) — TEST11
- **Phát hiện (T9a, `docs/tam_nhin_hud.md`)**:
  - Vùng nhìn thế giới là khung đệm FB do `OriginalDisplay_ConfigInfo` 0x147030 tính, KHÔNG phải màn logic. Máy 2400×1080 có FB 512×230, được phóng ~4,69 lần.
  - Đổi màn logic chỉ làm UI nhỏ đi.
- **Vá (`patch_lib.py`)**:
  - `view`: 0x14709c → cave 52 B trong `SGL_grpResume` (mã chết) 0x133454. Nhân cfg.W/H với P% (int 0x72f78c, chỉ nhận 100..150), ép W chẵn.
  - `hud`: 29 lệnh trong `UIPlay_Create`/`UIPlayPorting_Draw`/minimap, lùi HUD vào 24 px ngang, 16 px dọc. Hình và vùng chạm cùng dời.
  - `pool`: trần pool `MEM_Create` từ 8 lên 10 MB (`GRP_Initialize` xin 2·W² từ pool).
- **Mod menu**: thêm thanh "Tầm nhìn" 100–150%, bước 5, mặc định 120%. Chỉ áp khi mở lại game.
- **Kiểm (unidbg 2400×1080)**:
  - FB 512×230 (P=100), 614×276 (P=120), 768×345 (P=150); màn logic giữ 1422×640.
  - 21 control HUD dịch đúng; tap START vẫn trúng.
  - Log `logs/t9c_view_check.log`.
- **`test_builds/Inotia4_TEST11_vi_offline.apk`** = TEST10 + view/hud/pool. So TEST10 chỉ khác `classes3.dex` và `libgame.so`. Cùng khoá ký ⇒ cài đè được.
