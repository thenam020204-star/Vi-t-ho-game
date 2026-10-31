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
- [ ] T2. unidbg chạy qua màn chọn save tới game mới — **Quản đốc nghiệm thu**
  - Agent: A2 (chỉ đọc trước; chạy thật khi A1 ≥ 90%)
  - Việc:
    - tìm vì sao jemalloc của libc unidbg nhận `size 0` hoặc hỏng arena ở màn chọn save (frame khoảng 690, sau loạt `FILE_IsExist`);
    - sửa **trong emulator** (`emu/src/*.java`, hoặc hook/thay malloc trong unidbg), không sửa game;
    - viết script tap: START → chọn ô trống → game mới → vào màn chơi; tìm toạ độ qua ảnh chụp.
  - File: `emu/src/*.java`, `emu/README.md`.
  - Hoàn thành khi: chụp được ảnh đang trong màn chơi (`docs/emu_shots/emu_vao_game.png`), log không còn `UC_ERR`; ghi lệnh tái hiện vào `emu/README.md`. Giới hạn 3 hướng thử; không được thì ghi Vướng mắc.
- [ ] T3. Sửa văng khi load save (`CHAR_GetSkillPoint` null ← `GAMESTATE_EnterPlay`) — **Quản đốc nghiệm thu**. **ƯU TIÊN trước T2**: đã có file save người dùng gửi.
  - Đầu vào:
    - `logs/save_TEST7/`, xuất từ máy thật bằng TEST7: `d41d8cd98f00b204e9800998ecf8427e/save0.dat` (3192 B; thư mục = MD5 của chuỗi rỗng, tức khoá rỗng), `edf2113c684f2b822de06dad8d06b016A/thpcheckslot1.dat` (31 B), `appinfo.dat`, `envinfo.dat`;
    - chuỗi tên file `save%d.dat` @0xa5ddb, `thpcheckslot1..3.dat`;
    - log văng: `logs/may_that_TEST6_load_save_2026-10-31.txt`.
  - Agent: A1 nhận sau khi xong T1.
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
