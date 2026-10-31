# PLAN: Inotia 4 Việt hoá offline — giai đoạn ổn định và phát hành

Kế hoạch tổng ban đầu (bối cảnh, quyết định của người dùng, pipeline) nằm ở `docs/PLAN_goc.md`. Tiến độ chi tiết các phiên trước ghi ở `PROGRESS.md`.

## Mục tiêu
Bản APK Việt hoá offline chạy ổn định trên máy thật Android 14–16: vào game, chơi, lưu và load save không văng, các nút/tính năng có mạng không làm sập app. Sau đó build bản phát hành (không kèm chẩn đoán) vào `releases/Inotia4/`.

## Phạm vi
- Trong phạm vi:
  - rà các lối vào mạng còn có thể làm văng app;
  - dựng emulator unidbg chạy qua được màn chọn save;
  - sửa lỗi văng khi load save;
  - IAP Google "mua miễn phí";
  - đồng bộ `rebuild.sh`;
  - bản phát hành, gói dự án và `BAO_CAO.md`.
- Ngoài phạm vi (giai đoạn này):
  - Gem trên server (socket `inotia4.com2us.net:38000`): việc RE lớn, hỏi người dùng sau.
  - Dịch lại text. Ảnh có chữ.

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
- **Không có bộ split gốc trong container.** Mọi bản build mới đi từ `test_builds/Inotia4_TEST4_vi_anh_offline.apk` qua `scripts/build_from_apk.sh`:
  - `--smali <nhóm>`: vá smali bằng `scripts/patch_smali.py`;
  - `--vnfont`: build lại `classes3.dex` (font);
  - `--diag`: bản chẩn đoán;
  - **bản phát hành KHÔNG `--diag`, KHÔNG `--internet`**.
  - `rebuild.sh` (từ split gốc) phải áp cùng các nhóm vá.
- **Giữ gỡ quyền INTERNET.** Lối vào mạng nào ném `SecurityException` thì xử lý giống nhánh "máy không có mạng" sẵn có của bản gốc:
  - thêm `.catch Ljava/lang/SecurityException;` trỏ vào handler IOException có sẵn, qua cơ chế `CATCH_ADD` trong `patch_smali.py`;
  - chỉ khi handler đó chỉ dùng API của `Throwable` (thoả verifier);
  - không thay thân method nếu không bắt buộc.
- **Vá smali:**
  - chỉ thay thân method hoặc thêm `.catch`/method mới; không đổi chữ ký, tên, access flag;
  - mỗi patch phải khớp **đúng số lần kỳ vọng**, lệch thì dừng.
- **Vá `libgame.so`:**
  - qua `scripts/patch_lib.py`, mỗi patch kiểm byte gốc;
  - chỉ vá khi đã chứng minh bằng disasm;
  - ghi vaddr và lý do.
- **Font:** VNFont phóng theo `q/16` với `q = max(16, size)` (đã chốt ở TEST7). Không đổi nếu người dùng chưa phản hồi.
- **IAP:** bấm mua thì nhận vật phẩm ngay, không gọi Google Play (người dùng đã chốt từ đầu). Native cộng vật phẩm qua callback thành công gốc (`csPurchaseCB`...). Không tự cộng ngoài luồng gốc.
- Bytecode/smali thật được tin hơn output jadx. Disasm (`llvm-objdump`) được tin hơn suy luận.
- Không cấp phát object trong vòng vẽ (VNFont).

## Môi trường (container hiện tại, mất khi container bị thu hồi)
- Công cụ:
  - `/opt/re/apktool_2.12.1.jar`;
  - `/opt/re/jadx/bin/jadx`;
  - `/opt/android/build-tools/35.0.0` (d8, apksigner, zipalign, dexdump);
  - `/opt/android/platforms/android-34/android.jar`;
  - `llvm-objdump`.
- Bản decode sẵn của TEST4 trong `/opt/work`:
  - `dec4/` (apktool);
  - `jx4/sources` (jadx);
  - `lib4/libgame.so`, `lib4/dis.txt` (disasm, có symbol).
- Tra caller: `python3 scripts/re/xref.py /opt/work/lib4/dis.txt <ham>`.
- unidbg đã build ở `/opt/unidbg` (`emu/.cp_local` đã có). Chạy:
  - lệnh: `/opt/work/runemu.sh <OUT> <frames> "<script>" [-Dopt...]`;
  - script dạng `frame:tap:x,y`, toạ độ 480x320; START = `240,277`;
  - tuỳ chọn: `-Demu.tracefn=a,b` in khi vào hàm, `-Demu.w/-Demu.h` đổi màn hình;
  - ảnh chụp ở `<OUT>/fNNNNN.png`.
- Build từ APK nền:
  - lệnh: `APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/<ten>.apk --smali net --vnfont [--diag]`;
  - so sánh: `python3 /opt/work/apkdiff.py <a.apk> <b.apk>`.
- Nếu container mới không có các thứ trên: dựng lại theo `emu/README.md`, `emu_android/README.md` và mục Môi trường trong `PROGRESS.md`.

## Vận hành công nhân (người dùng chốt 2026-10-31)
- Công nhân có ngữ cảnh **vượt 400k token** thì thay công nhân mới cùng vai (A1/A2), không để chạy tiếp.
- Quản đốc đo ngữ cảnh mỗi lần kiểm tra tiến độ: lấy `usage` của lượt assistant cuối trong transcript subagent (`input + cache_read + cache_creation`).
- Khi thay:
  - quản đốc nhắn công nhân cũ ghi bàn giao vào dòng `Bàn giao:` dưới task đang làm (đã xong gì, file nào đã sửa, bước tiếp theo, lệnh đang dùng), rồi dừng;
  - không trả lời được thì quản đốc dừng hẳn và tự ghi bàn giao từ transcript;
  - công nhân mới đọc PLAN.md + dòng `Bàn giao:`, không làm lại phần đã tick.
- Công nhân tự ghi `Bàn giao:` mỗi khi xong một bước lớn, để việc thay không mất tiến độ.

## Ràng buộc và edge case
- Máy thật của người dùng: Xiaomi, Android 16, 2400x1080, trang nhớ 4 KB. `.so` đã căn 16 KB.
- Bản offline không đăng nhập Hub. Mọi luồng phụ thuộc `Hub2uniqueUserId` đều nhận giá trị rỗng.
- unidbg văng ở màn chọn save trong `run_quantize` (jemalloc của libc giả lập, size 0). Đây là lỗi môi trường emulator, không phải của game. Không được "sửa" bằng cách vá game.
- Mỗi bản build phải có:
  - `apksigner verify` OK;
  - `zipalign -c -P 16 4` OK;
  - `check_apk.py` PASS;
  - diff APK so với bản trước, giải thích từng file khác.

## Checklist
- [x] T0. Văng lúc khởi động (`CWrapperHttp` + `SecurityException`): nhóm `net` (TEST6, đã chạy trên máy thật).
- [x] T0b. Font to hơn `q/16` + chẩn đoán xuất save (TEST7).
- [ ] T1. Rà toàn bộ lối vào mạng Java mà native/UI gọi được — **Quản đốc nghiệm thu**
  - Agent: A1 (chạy)
  - Đầu vào: `/opt/work/jx4/sources`, `/opt/work/dec4/smali*`, `docs/offline_phan_tich.md`.
  - Việc:
    - liệt kê các class/method trong `com/com2us/**` và `com/hive/**` mở kết nối mạng: `URL.openConnection`, `Socket`, `InetAddress`, `HttpURLConnection`, `okhttp`, `WebView.loadUrl`;
    - với mỗi method, ghi: ai gọi (native JNI theo tên trong `libgame.so`, hoặc UI), chạy trên luồng nào, bắt ngoại lệ gì;
    - đánh dấu chỗ `SecurityException` có thể lọt ra luồng không ai bắt (văng app);
    - với chỗ sửa được theo Quyết định thiết kế: thêm mục vào `CATCH_ADD` nhóm `net` trong `scripts/patch_smali.py`, ghi rõ số lần khớp.
  - File: `docs/mang_offline_ra_soat.md` (bảng kết quả), `scripts/patch_smali.py`.
  - Hoàn thành khi:
    - bảng rà soát đầy đủ;
    - `python3 scripts/patch_smali.py <bản sao dec4> --group net` chạy không lỗi, đúng số lần khớp;
    - build thử `out/Inotia4_T1_check.apk` bằng `build_from_apk.sh --smali net --vnfont` PASS các kiểm tra;
    - `diff -r` smali so với `dec4` chỉ ra đúng các dòng `.catch` thêm vào.
  - Bàn giao (A1, bước 1/3 xong: rà tĩnh): đã xong — dựng bản đồ native→Java (xref `libgame.so`), quét `com/com2us`+`com/hive` bằng script phủ ngoại lệ (scratchpad `cov2.py`, theo handler `catchall`). Kết quả sơ bộ: chỉ 1 chỗ nguy hiểm thật là `CWrapperHttp$1.run` (đã có mục `CATCH_ADD['net']`); `Hercules.HttpRequest` và `Network.sync` (Hive) đã bắt `Exception`/`runCatching`; `CWrapperHttp.netHttp*Ex` bị chặn bởi `iHttpStatus==2`; WebView không ném SecurityException (WebView `app:id/webView` tạo được trong log TEST5a). Chưa sửa file nào trong repo. Bước tiếp: (2) viết `docs/mang_offline_ra_soat.md`, đọc thêm `Mercury.internalMercuryShowEx`/`ActiveUser.showUserAgreeTerms`/`WrapperUserDefined.GotoURL`; (3) `cp -r /opt/work/dec4 /opt/work/dec_t1 && python3 scripts/patch_smali.py /opt/work/dec_t1 --group net`, build `out/Inotia4_T1_check.apk` (`APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/Inotia4_T1_check.apk --smali net --vnfont`), decode lại + `diff -r` với `/opt/work/dec4`, tick T1, ghi Nhật ký.
- [ ] T2. unidbg chạy qua màn chọn save tới game mới — **Quản đốc nghiệm thu**
  - Agent: A2 (chỉ đọc trước; chạy thật khi A1 ≥ 90%)
  - Việc:
    - tìm vì sao jemalloc của libc unidbg nhận `size 0` hoặc hỏng arena ở màn chọn save (frame khoảng 690, sau loạt `FILE_IsExist`);
    - sửa **trong emulator** (`emu/src/*.java`, hoặc hook/thay malloc trong unidbg), không sửa game;
    - viết script tap: START → chọn ô trống → game mới → vào màn chơi; tìm toạ độ qua ảnh chụp.
  - File: `emu/src/*.java`, `emu/README.md`.
  - Hoàn thành khi: chụp được ảnh đang trong màn chơi (`docs/emu_shots/emu_vao_game.png`), log không còn `UC_ERR`; ghi lệnh tái hiện vào `emu/README.md`. Giới hạn 3 hướng thử; không được thì ghi Vướng mắc.
  - Bàn giao (A2 đọc trước, chưa sửa file, transcript ~233k):
    - Hiện trường:
      - văng ở `libc+0x7626c` (`run_quantize(0)`), gọi từ `arena_run_first_best_fit` tại `libc+0x7782c`;
      - nút trong `runs_avail` có `mapbits & ~0xfff == 0`, tức bản đồ chunk jemalloc hỏng;
      - chunk 256 KB; libc nạp ở `0x12870000`.
      - `emulate` nuốt lỗi rồi chạy tiếp, nên phải grep `UC_ERR|Invalid memory`.
    - Hướng 1 (thử trước): unidbg `madvise` (syscall 233) là no-op trả 0.
      - jemalloc coi `MADV_DONTNEED` thành công nghĩa là trang đã về 0, nên tái dùng chunk "zeroed" mà không khởi tạo mapbits. Game `calloc` mọi thứ (`CS_knlAlloc` = `calloc(1,n)`).
      - Sửa: hook `madvise` trong `emu/src/Inotia4Emu.java`, `advice==4` thì ghi 0 vùng đó; thêm `-Demu.madv=zero|fail|off`; đếm số lần gọi. Số lần gọi = 0 thì bỏ hướng này.
    - Hướng 2: `emu/src/JMalloc.java` thay `malloc/calloc/realloc/free/memalign/posix_memalign/malloc_usable_size`:
      - địa chỉ export: `libc+0x1bb08/0x1baa0/0x1bb88/0x1bac0/0x1bb48/0x1bb68/0x1bb28`;
      - metadata giữ phía Java; bật bằng `-Demu.malloc=java`.
    - Hướng 3 (chẩn đoán): đặt write-hook lên mapbits bị zero, chạy lại tất định, in PC/LR của kẻ ghi. Nếu là lỗi game thì ghi Vướng mắc, không vá game.
    - Toạ độ 480x320:
      - ô save trống: (214,82), (214,165), (214,247); nút quay lại: (18,22). Quản đốc đã kiểm trên ảnh 960x640 `/opt/work/emuS/f00690.png`: ô đầu ở khoảng (428,165).
      - script mẫu: `620:tap:240,277;690:shot:s0;700:tap:214,82;...`;
      - sau khi chọn ô có thể hiện popup xác nhận hoặc `PopupNetError`.
- [ ] T3. Sửa văng khi load save (`CHAR_GetSkillPoint` null ← `GAMESTATE_EnterPlay`) — **Quản đốc nghiệm thu**. **ƯU TIÊN trước T2**: đã có file save người dùng gửi.
  - Đầu vào:
    - `logs/save_TEST7/`, xuất từ máy thật bằng TEST7: `d41d8cd98f00b204e9800998ecf8427e/save0.dat` (3192 B; thư mục = MD5 của chuỗi rỗng, tức khoá rỗng), `edf2113c684f2b822de06dad8d06b016A/thpcheckslot1.dat` (31 B), `appinfo.dat`, `envinfo.dat`;
    - chuỗi tên file `save%d.dat` @0xa5ddb, `thpcheckslot1..3.dat`;
    - log văng: `logs/may_that_TEST6_load_save_2026-10-31.txt`.
  - Agent: A2 mới (đọc trước từ lúc A1 làm T1; chạy thật khi T1 xong và quản đốc nghiệm thu).
    - Lý do đổi: A1 làm xong T1 sẽ ở mức ~250–300k ngữ cảnh, mà T3 là RE nặng nên sẽ vượt 400k giữa chừng.
    - Sau T1, A1 dừng; công nhân này thành người chạy duy nhất.
  - Việc:
    - với file `inotia4_save_*.zip` và báo cáo TEST7: giải mã save (`SAVE_LoadDataAsNameAndKey`, khoá rỗng), xác định bước nào của `SAVE_LoadFile` thất bại (`LoadData` / version ≤ 5 / chỉ số ô / `Inventory` / `Quest` / `Event`);
    - hoặc tái hiện bằng unidbg (T2): game mới → lưu → chạy lại → load.
  - Hoàn thành khi: nêu được nguyên nhân bằng bằng chứng (disasm + dữ liệu save); có bản vá (nếu lỗi do bản Việt hoá/offline) được kiểm bằng unidbg hoặc bằng phân tích file; ghi vào PROGRESS.
- [ ] T4. IAP "mua miễn phí" — **Quản đốc nghiệm thu** (sau T1)
  - Việc:
    - đọc `com/com2us/module/inappv4/InAppV4` (smali) và native `InAppV4MarketListCB` / `csPurchaseCB` / `csGetProductInfoCB` / `InApp_BuyItem` / `UIPlay_CallInAppShopProc`;
    - thiết kế luồng giả: `marketConnect` thành công, danh sách sản phẩm trả về, `purchase(productId)` → callback thành công đúng định dạng native đọc;
    - viết nhóm `iap` trong `patch_smali.py` + class mới trong `java/src/com/namp/vh/` nếu cần.
  - Hoàn thành khi:
    - có tài liệu `docs/iap_offline.md` (định dạng callback, bằng chứng disasm);
    - nhóm `iap` áp đúng số lần;
    - build thử PASS;
    - nếu T2 xong: unidbg bấm mua một gói và thấy vật phẩm/gem được cộng.
- [ ] T5. Đồng bộ `scripts/rebuild.sh` với các bản vá mới (phụ thuộc T1, T4)
  - Việc: nhóm smali đầy đủ (`font,lang,offline,net[,iap]`); d8 thêm `--lib android.jar` và `--lib` stub; ghi phiên bản tool vào `tools/VERSIONS.md` (apktool 2.12.1, build-tools 35.0.0, jadx 1.5.3).
  - Hoàn thành khi: `bash -n scripts/rebuild.sh` OK; diff hợp lý; README/PROGRESS ghi rõ cách chạy.
- [ ] T6. Bản phát hành (phụ thuộc T3, người dùng xác nhận font) — **Quản đốc nghiệm thu**
  - Việc:
    - `build_from_apk.sh --smali <nhóm> --vnfont` (không diag) → `releases/Inotia4/Inotia4_vi_offline_<ngay>.apk` + SHA256;
    - `BAO_CAO.md` (đã làm, chưa làm, debug, việc tồn đọng);
    - gói `Inotia4_du_an.zip` (theo QUY_TAC_CHUNG mục 11, không kèm APK gốc).
  - Hoàn thành khi:
    - mọi kiểm tra ký/align/check PASS;
    - diff với TEST7 chỉ còn: bỏ `classes4.dex`, manifest trả LAUNCHER về `MainActivity`, cộng các nhóm vá mới;
    - người dùng cài thử OK.

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (trống)

## Nhật ký
- 2026-10-31 quản đốc: lập PLAN giai đoạn ổn định; T0/T0b đã xong ở phiên trước (TEST6/TEST7).
- 2026-10-31 quản đốc: giao T1 cho A1 (chạy), A2 đọc trước T2. Người dùng gửi save (TEST7) → `logs/save_TEST7/`; T3 ưu tiên trước T2.
- 2026-10-31 quản đốc: thêm quy tắc thay công nhân khi ngữ cảnh > 400k (người dùng chốt). Lúc ghi: A1 ~233k, A2 ~209k.
- 2026-10-31 quản đốc: A2 đọc xong T2 (~233k). Kế hoạch 3 hướng ghi ở "Bàn giao" dưới T2. A2 lỡ tạo rồi xoá một file ở `/tmp` (ngoài dự án), không ảnh hưởng. T2 chờ: T1 xong → T3 (ưu tiên) → T2.
