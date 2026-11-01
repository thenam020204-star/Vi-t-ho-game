# PLAN: HL5 Android: Việt hoá + logic HD + cash shop miễn phí

> Quản đốc = Opus (phiên chính). Công nhân = Sonnet 5.5. Đây là nguồn sự thật. Đọc hết mục "Quyết định thiết kế" và "Môi trường" trước khi làm.
> Tài liệu kỹ thuật chi tiết (đã kiểm chứng): `hl5_android/BAO-CAO-RE.md`.

## Mục tiêu
Từ `heroes-lore-5-v1.0.5.apk` (bản Android EA, đã Anh hoá, chạy qua bridge Unicorn arm64), làm ra APK:
(1) tiếng Việt, dùng bản dịch LGT của người dùng, câu mới chỉ bản Android có thì tự dịch Anh → Việt;
(2) chạy offline hoàn toàn, cash shop miễn phí;
(3) logic HD thật: khung logic = màn ÷ k (k chọn trong game, ÷1..÷4, mặc định ÷2), phóng số nguyên NEAREST; font 18px khi ở chế độ HD.

## Phạm vi
- Trong phạm vi: dữ liệu VFS, `libHeroesLore5.so` (ARM32 guest), `libhl5_arm64_bridge.so` (arm64 và bản armeabi-v7a cùng tên), `assets/target-assets.json`, ký lại APK.
- Ngoài phạm vi: viết lại bridge từ đầu; âm thanh; hỗ trợ máy dưới Android 10; online thật.

## Môi trường (mọi task dùng chung)
- Repo: `/home/user/Vi-t-ho-game`, nhánh `ccr-0532c7e4-lrr5j9`. Code và công cụ đặt trong `hl5_android/`.
- Thư mục làm việc (không commit, có thể mất khi container khởi động lại):
  `WORK=/tmp/claude-0/-home-user-Vi-t-ho-game/31266a47-2a55-5b6d-86d1-5e3a70f1f083/scratchpad`
  - Dựng lại bằng: `WORK=$WORK UP=/root/.claude/uploads/31266a47-2a55-5b6d-86d1-5e3a70f1f083 hl5_android/setup_work.sh`
  - `$WORK/apk/`: APK đã giải nén (`assets/libHeroesLore5.so`, `assets/data.vfs.mp3`, `lib/arm64-v8a/libhl5_arm64_bridge.so`).
  - `$WORK/lgt5/j/res/c/...`: tài nguyên LGT gốc (tiếng Hàn).
  - `$WORK/proj/HL4_project/vi5/`: pipeline Việt hoá LGT. `catalog5.json`, `ids5.json`, `build/overlay/res/c/...` (bản Việt build từ LGT), `build/overlay/vi5/{dict.txt,font.bin,font10.bin}`, `tr_in/HL5_dich_FULL.tsv` (bản dịch của người dùng), `tr_in/thuat-ngu.tsv`, `tr_in/style-guide.md`, `override.tsv`, `fit.tsv`, `build_vi5.py` (class `Encoder`, các hàm `vi_width*`, `wrap_lines`, `check_codes`, `load_fit`…).
  - `$WORK/namp/NAM_P/`: tool font NAM_P (`fonts/f18.bin`, `f15.bin`…, `fontgen/`, `java/BitmapFont.java` để tham khảo định dạng).
  - `$WORK/so.dis`, `$WORK/br.dis`: disassembly của guest (ARM) và bridge (arm64).
- Công cụ trong repo:
  - `hl5_android/harness/hl5run.py`: chạy guest headless. Ví dụ: `python3 -I hl5run.py --so X.so --vfs Y.vfs --out DIR --frames 700 --shot 20 --keys "460:OK,560:OK"`, rồi `sheet.py DIR 4` để ghép ảnh. Mốc phím đã biết: khung 460 OK (qua tiêu đề), khung 560 OK (vào menu chính → GAME START).
  - `hl5_android/patch/apply_so.py IN OUT so_patches.tsv`: áp patch byte có kiểm tra byte gốc. `patch/so_patches.tsv` là mapping mọi patch.
  - `hl5_android/tools/scn.py`: parser/builder `.scn` chuẩn theo mã máy. `tools/vfs2.py`: đọc VFS. `tools/skelcheck.py`, `tools/deadfuncs.py`, `tools/gotsym.py`, `tools/show.py`.
- Có sẵn: python3 (unicorn, capstone, keystone, pyelftools, lief, openpyxl), clang-18 + ld.lld (target armv7/aarch64), llvm-objdump, Android build-tools 34 ở `/opt/asdk/android-14/` (apksigner, zipalign, aapt2, dexdump).

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
1. **Mọi logic game nằm trong guest `.so`. Bridge chỉ sửa tối thiểu** (tắt hook chữ tiếng Anh, khung logic ÷k, lọc NEAREST, viewport phóng số nguyên). Lý do: bridge đã strip và không có mã nguồn.
2. **Patch guest tại chỗ, không đổi symbol table, không đổi địa chỉ/kích thước hàm mà bridge tra theo tên.** Bridge tra theo tên: `BFont::*` (10 hàm hook), `StateMainMenu::{SetSubState,SetState,LoadLogoSprite}`, `SlotInfo::LoadSlotData`, `CommonUi::LoadLocalSaveData`, `SaveSlotData`, `MX_sdGetSize`, các `Java_*`, `CGame`/`gv`. Code mới đặt trong **hàm chết** (`tools/deadfuncs.py`; ưu tiên `Map::MapMove_back` 0xaef38 2620B, `AndroidService::AndroidService` 0x14afb4 2436B, `StateGame::StateGame(int)` 0xdfec0 1588B, `StatePopup` ctor 0x13f100 1512B, `MX_knlMakeTime` 0x35114 1412B). Mỗi vùng đã dùng phải ghi vào `patch/caves.tsv`.
3. Mọi patch byte ghi vào `patch/so_patches.tsv` (vaddr, byte gốc, byte mới, lý do), áp bằng `apply_so.py`. Code C được biên dịch rồi chèn bằng script, output cũng là các dòng tsv (không sửa file `.so` bằng tay).
4. **Mã hoá chữ Việt giống LGT:** mỗi từ/âm tiết = 2 byte (lead 0xC9.., trail 0xA1..), chỉ số `(lead-0xC9)*94 + (trail-0xA1)` trỏ vào từ điển. Tiền tố `\x01` = dạng nén, `\x02` = font 10px (như `build_vi5.py`). Dùng lại class `Encoder` và luật fit/wrap của `build_vi5.py` bằng import, không viết lại.
5. **Dữ liệu: giữ file Android, chỉ thay chuỗi.** Không thay nguyên file bằng bản LGT, vì bản Android mới hơn (thêm bản ghi, đổi số liệu, đổi code kịch bản). Câu Android ghép với câu Hàn LGT thì lấy bản dịch người dùng (cùng ID `K#####`). Câu không ghép được thì đặt ID mới `A#####`, dịch Anh → Việt bằng tay vào `hl5_android/vi/A_dich.tsv` (cột: ID, File, Vị trí, Tiếng Anh, Tiếng Việt, Ghi chú), theo `thuat-ngu.tsv` + `style-guide.md`. Mỗi cặp ghép có độ tin cậy; cặp ghép dưới ngưỡng coi là câu mới.
6. **`.scn`:** dùng `tools/scn.py` (độ dài chuỗi u8 hoặc u16 theo header `b2`; chuỗi Android có `\0` ở cuối, tính vào độ dài). **`.dat`:** u16 số bản ghi + (u16 len + bản ghi); chuỗi trong bản ghi có tiền tố độ dài u8/u16 (`rec_strings` của extract5 chỉ dùng cho tiếng Hàn; với bản Android phải dò chuỗi ASCII theo cùng bố cục trường của bản ghi Hàn tương ứng).
7. **Engine chữ Việt trong guest:**
   (a) thay mọi lệnh `ldrsb rX,[rF,#70]` trong `BFont::*` (bề rộng Hangul-1) bằng `bl` tới thunk trả `bề_rộng_từ − 1` khi 2 byte hiện tại là mã Việt, ngược lại trả giá trị gốc;
   (b) chặn `MX_fntDrawString` (0x3cf00, đối số `(fb, gc, fontID, x, y, str, len)`): `len==2` và mã Việt thì vẽ bitmap từ bằng `_midas_funcFntDrawPixel(x,y,color,mode)`, màu và mode lấy đúng như `_midas_funcFntDrawString` (màu từ `_mxfnt_prt`+0x40), sau khi đã chạy phần thiết lập biến toàn cục của `MX_fntDrawString`; còn lại chạy mã gốc.
   (c) Từ điển + font nạp từ VFS lúc chạy (asset `c/vi/vi.bin`, đọc qua `MC_knlGetResourceID` + `MC_knlGetResource` + `MC_knlCalloc`), một lần.
   (d) Chọn font: logic cao ≥ 480 → bộ 18px (`f18.bin`); ngược lại 15px/10px như LGT.
8. **HD:** khung logic `W = scrW/k`, `H = scrH/k` (làm tròn xuống, số chẵn), k ∈ {1,2,3,4}, đọc từ file `hd_scale.txt` trong thư mục lưu game; không có file thì k = 2. Đổi k trong menu Cài đặt, khởi động lại mới có hiệu lực. Texture lọc NEAREST, viewport = (W·k)×(H·k) căn giữa.
9. **Offline:** đã có 4 patch (BAO-CAO §7.2). Menu chính bỏ UPDATE/STORAGE/SUPPORT (như LGT r8). Cash shop: mua thành công ngay, không gọi MTX/mạng, không trừ tiền thật. Hồi sinh khi chết không cần mạng.
10. **Ký APK bằng khoá mới** (`hl5_android/sign/hl5vi.keystore`, mật khẩu ghi trong `sign/README.md`). Người dùng phải gỡ bản cũ trước khi cài (ghi rõ trong báo cáo phát hành).
11. Kiểm chứng mỗi task bằng harness (ảnh chụp lưu vào `hl5_android/shots/<task>/`). Không ai coi "chạy được" là đủ: phải có diff byte (patch tsv) và ảnh hoặc log.

## Ràng buộc và edge case
- Độ dài: chuỗi `.scn` u8 tối đa 255 byte (gồm `\0`); trường `.dat` theo tiền tố u8/u16. Quá dài thì báo `QUÁ DÀI` và giữ câu Android, không cắt bừa.
- Mã điều khiển giữ đúng số lượng: `;` xuống dòng, màu `& | $ { } ' " \` ^ < [`, `#05`, `%d`, `%s`. Bản tiếng Anh có thể đã đổi số mã so với bản Hàn: khi ghép, ưu tiên khớp theo bản Android (game Android đọc chuỗi Android).
- Tối đa 5076 mã từ điển (đã dùng 4548 cho bản LGT): câu mới phải tái dùng từ, báo nếu vượt.
- Chữ 18px cao hơn 11px gốc: phải chỉnh `[font+69]`/`MX_fntGetStringHeight` (khoảng dòng) ở chế độ HD.
- Bridge kiểm sha256 trong `assets/target-assets.json` (Java `TargetAssets`): mỗi lần đổi `.so`/VFS phải cập nhật.

## Checklist
- [x] T1. Trích danh mục chuỗi Android — Agent: A1
  - Đầu vào: VFS Android, danh sách file trong `$WORK/proj/HL4_project/vi5/build/overlay/res/` (253 file có trong VFS) + mọi `c/map/*.scn`, `c/csv*/*.dat` trong VFS có chuỗi ASCII đọc được.
  - Đầu ra: `hl5_android/vi/android_catalog.json`, mỗi mục `{file, kind: scn|dat, idx | (rec, off, ps), text_en, cap}`; viết `hl5_android/vi/extract_android.py`.
  - Hoàn thành khi: chạy lại script cho kết quả giống hệt; mọi `.scn` đọc bằng `tools/scn.py`; với mỗi file, ghi chuỗi đọc ra trở lại thì file giống từng byte (roundtrip); in thống kê số câu theo file/kind.
- [x] T2. Ghép chuỗi Android ↔ LGT (phụ thuộc T1) — Agent: A1 — **Quản đốc nghiệm thu**
  - `.scn`: cùng số câu thì ghép theo chỉ số, kèm kiểm tra hợp lý; khác số câu thì dóng hàng DP (đặc trưng: tỉ lệ độ dài, dấu câu `...?!`, mã màu, số, `;`, tên riêng theo `thuat-ngu.tsv` Hàn ↔ Anh). `.dat`: ghép bản ghi theo trường khoá (ID số đầu bản ghi; tự xác định bằng dữ liệu, ghi lại kết luận), rồi theo thứ tự trường chuỗi.
  - Đầu ra: `hl5_android/vi/align.json` (mỗi mục Android → `K#####` hoặc `NEW`, điểm tin cậy) + `hl5_android/vi/align_report.md` (thống kê, 50 cặp điểm thấp nhất để quản đốc soát).
  - File: `hl5_android/vi/align_android.py`.
  - Hoàn thành khi: chạy lại ổn định; 184 `.scn` cùng số câu có ≥ 95% cặp điểm cao; báo số câu `NEW`.
- [x] T2b. Sửa ghép sau nghiệm thu T2 (phụ thuộc T2) — Agent: A1
  - Quản đốc soát 50 cặp thấp nhất, kết luận: dải điểm 0.55–0.70 có nhiều cặp **sai nghĩa** (vd 00136 idx 34/36, 00144 idx 54, 00094 idx 22, 00038 idx 64 đúng; quest_* ghép với `여분` sai; item_16 câu Anh có cả mô tả kỹ năng, câu Hàn chỉ có tên+cấp → mất nội dung).
  - Sửa trong `align_android.py`: (1) cặp điểm < 0.70 → `NEW`, giữ câu Hàn đề xuất ở trường `ko_hint`; (2) câu Hàn là chỗ trống/giữ chỗ (`여분`, rỗng, chỉ mã) → `NEW`; (3) câu Anh có số đoạn `;` hoặc độ dài vượt rõ câu Hàn (tỉ lệ ký tự Anh/Hàn > 3.5 hoặc thiếu ≥ 2 đoạn) → `NEW` + `ko_hint`; (4) 2 câu dính `\u0000` (00041 idx 54, 00042 idx 24): ghi rõ cách xử lý trong report (giữ byte gốc khi build).
  - Thêm vào report: 40 cặp ngẫu nhiên (seed cố định) trong dải 0.70–0.80 để quản đốc soát; thống kê NEW mới.
  - Hoàn thành khi: chạy lại ổn định; report có mục mẫu 0.70–0.80; số NEW mới được ghi vào Nhật ký.
- [x] T2c. Ghép FILE scn theo nội dung, không theo số (phụ thuộc T2b, T3a) — Agent: A1 — **Quản đốc nghiệm thu**
  - Lý do (quản đốc xác minh): 00216 idx 3 "For now... yes. Call me Rabi." bị gán 침입자 발견 với điểm 0.829 → bộ chấm điểm đặc trưng không phân biệt được nghĩa; sai từ khâu chọn file.
  - Ghép file: với mỗi scn Android, chấm mọi scn LGT bằng (a) độ giống phần **bytecode script** (khối Scripts A/B, bỏ bảng chuỗi; difflib/so khớp n-gram), (b) chuỗi tên riêng/số/mã màu, (c) số câu; gán 1-1 toàn cục (Hungarian / tham lam theo điểm), file không đủ điểm → toàn bộ NEW. Ghi bảng ghép file + điểm vào `align_report.md`; so với ghép theo số, liệt kê mọi file đổi ghép.
  - Sau đó chạy lại ghép câu như T2b trên cặp file mới. Bổ sung kiểm tra nghĩa thô: câu ngắn (≤ 3 từ Anh) mà Hàn dài > 12 ký tự hoặc ngược lại → NEW.
  - Giữ ổn định ID `A_dich.tsv`: dịch đã có giữ theo khoá (file, idx, text_en); câu không còn NEW → đánh dấu cột 6 `unused`; câu NEW mới → ID nối tiếp sau A01008. Không xoá bản dịch.
  - Hoàn thành khi: chạy lại ổn định; bảng ghép file trong report; các ví dụ ở Vướng mắc T3a (00216 idx 3/4/20, 00214 idx 14, 00186 idx 18) không còn gán sai; `check_A.py` vẫn 0 lỗi; ghi số NEW mới (scn) vào Nhật ký.
- [x] T2d. Soát nghĩa thủ công các cặp ghép rủi ro (phụ thuộc T2c) — Agent: A1 — **Quản đốc nghiệm thu**
  - Lý do (quản đốc xác minh sau T2c): cặp DP vẫn sai nghĩa dù điểm 1.0 (00136 idx 1 "Must be nice for the Neveds..." ↔ "전 학자는 아니지만..", idx 4 "They have a creation myth" ↔ "알고 있나요? 우리 콜크리크 섬이.."; 00108 idx 43). Bộ chấm điểm đặc trưng không đo được nghĩa.
  - Công nhân TỰ ĐỌC từng cặp Anh ↔ Hàn (không dùng dịch máy) theo lô ~100 cặp: toàn bộ 980 cặp `scn/dp`; mẫu ngẫu nhiên seed cố định 150 cặp `scn/idx` và 150 cặp `dat/rec`. Phán quyết mỗi cặp: OK / SAI / LỆCH-NỘI-DUNG (Anh thêm/bớt ý đáng kể).
  - Ghi `vi/review_T2d.tsv` (file, vị trí, id, phán quyết, ghi chú). `align_android.py` đọc file này làm ghi đè: SAI/LỆCH → `NEW` (giữ `k_low`, `ko_hint`). Nếu một file DP có ≥ 3 SAI liên tiếp → thử đúng cặp hàng lân cận (dịch ±1..±40) và ghi đề xuất K đúng vào ghi chú nếu thấy rõ.
  - Báo tỉ lệ SAI trong mẫu idx/dat; nếu mẫu idx hoặc dat > 3% SAI → ghi Vướng mắc (quản đốc sẽ mở rộng soát).
  - Giữ quy tắc ID ổn định của `A_dich.tsv` như T2c.
  - Hoàn thành khi: 1280 cặp có phán quyết; align chạy lại ổn định; check_A 0 lỗi; Nhật ký ghi số SAI theo nhóm và số NEW mới.
- [x] T3. Dịch câu NEW Anh → Việt (phụ thuộc T2b) — Agent: A1 — chia lô: **T3a** = 348 NEW `.scn` (thoại), **T3b** = 660 NEW `.dat` (vật phẩm/kỹ năng/nhiệm vụ/UI). Mỗi lô một tick riêng trong Nhật ký.
  - Câu có `k_low` (ID LGT gần nhất): mở bản Việt của `k_low` trong `HL5_dich_FULL.tsv`/`override.tsv` làm gợi ý, tái dùng câu chữ/thuật ngữ đã có rồi bổ sung phần Anh thêm (vd mô tả kỹ năng `item_16`, số `100,000`), ngắt `;` theo bản Anh (bản Android đã ngắt dòng lại).
  - Mỗi dòng `A_dich.tsv`: `A#####<TAB>file<TAB>vị trí<TAB>text_en<TAB>vi` (A##### ổn định, sinh theo thứ tự align.json).
  - Dịch tay (không dịch máy), theo `thuat-ngu.tsv`, `style-guide.md`, cách xưng hô của cùng nhân vật trong `HL5_dich_FULL.tsv`. Giữ mã điều khiển. Không dùng ký tự `' " \` ^ < [ { } $ & |` ngoài mã.
  - Đầu ra: `hl5_android/vi/A_dich.tsv`.
  - Hoàn thành khi: mọi `NEW` có bản dịch; script kiểm tra mã điều khiển báo 0 lệch; độ dài sau mã hoá ≤ cap.
- [x] T1b. Sửa trích chuỗi `.dat` bị cắt mảnh (phụ thuộc T3) — Agent: A1 — **Quản đốc nghiệm thu**
  - Bằng chứng (quản đốc): `A00488` skill_00 rec 42 off 54 = "T+rBuff Skill. Cooldown:;...Max;HP drops b" + `A00489` off 121 = " but;ATK Spd increases..." → một chuỗi bị DP heuristic `rec_strings` tách mảnh / lệch tiền tố; tương tự A00492-93, A00495-97, A00499, A00501-02 (skill_01/02), `cash_expert` "oAl"+"ows starting..." (A00008/9). Ghi đè các mảnh khi build sẽ PHÁ dữ liệu.
  - Việc: (1) tìm nguyên nhân bằng byte thật (giả thuyết: chuỗi chứa byte ≥ 0x80 / ký tự ngoài `_ok_text` làm DP bỏ chuỗi thật); (2) xác định bố cục trường chính xác cho mỗi file `.dat` có chuỗi (ưu tiên đọc hàm nạp trong `libHeroesLore5.so` qua `so.dis`; nếu không, dùng khung bản ghi cố định trong cùng file — mọi bản ghi cùng file phải ra cùng chuỗi kiểu trường); (3) `extract_android.py` trích theo bố cục đó; roundtrip từng byte; liệt kê mọi mục catalog thay đổi (bị gộp/tách/đổi off).
  - Cập nhật `align.json` (chạy lại `align_android.py`) và `A_dich.tsv` theo quy tắc ID ổn định: mảnh cũ → `unused`; chuỗi gộp → ID mới, cột 6 ghi `hint=A#####,A#####` (bản dịch cũ của các mảnh) để T3c dịch lại.
  - Sửa `check_A.py`: cap lấy từ catalog (255 ps1 / 4000 ps2), không cứng 255.
  - Hoàn thành khi: 0 chuỗi dat bắt đầu/kết thúc giữa từ do cắt mảnh (script kiểm: mảnh liền kề cùng bản ghi mà nối lại thành 1 trường hợp lệ = 0); roundtrip 0 lỗi; check_A 0 lỗi thật; Nhật ký ghi số mục thay đổi và số ID mới cần T3c.
- [x] T3c. Dịch lại các chuỗi gộp từ T1b (dùng `hint`) + các câu LỆCH SỐ từ T4 — Agent: A1
- [x] T4. Build dữ liệu Việt cho Android (phụ thuộc T2, T3) — Agent: A1
  - `hl5_android/vi/build_android_vi.py`: import `Encoder`/luật fit từ `build_vi5.py`; nguồn dịch = `HL5_dich_FULL.tsv` + `override.tsv` + `menu_override.tsv` (theo `K#####`) + `A_dich.tsv` (theo `A#####`); ghi file Android đã thay chuỗi vào `$WORK/vi_android/res/c/...`; xuất `$WORK/vi_android/vi.bin` (định dạng do T6 chốt; tạm thời xuất `dict.txt` + các font) và `bao-cao-build.txt`.
  - Kiểm tra số: mọi số (chuỗi chữ số) trong `text_en` phải có trong bản Việt; câu K##### lệch số (Android đổi số so với Hàn, vd mẫu #10 `100,000 Gold`) → liệt kê `bao-cao-build.txt` mục LỆCH SỐ và đưa vào `A_dich.tsv` lô bổ sung (T3c).
  - Hoàn thành khi: 0 câu quá dài (hoặc có liệt kê lý do), từ điển ≤ 5076; mọi file output đọc lại được (roundtrip parser), số bản ghi/khung code giống file Android gốc.
- [x] T5. Đóng gói VFS (song song được với T2–T4; chỉ đụng `hl5_android/tools/vfs_pack.py`) — Agent: A1
  - `vfs_pack.py IN.vfs OUT.vfs --replace DIR [--add name=file ...]`: giữ nguyên thứ tự mục, thay dữ liệu theo khoá djb2 của đường dẫn `c/...`, thêm mục mới vào cuối.
  - Hoàn thành khi: pack không thay gì thì cho file giống hệt từng byte; pack với overlay thử (1 file `.dat`) thì harness vẫn boot tới menu chính.
- [x] T6. (T6a ✓ thiết kế §9; T6b ✓, chờ quản đốc nghiệm thu) Engine chữ Việt trong guest (phụ thuộc T5) — Agent: A1 — **Quản đốc nghiệm thu**
  - `hl5_android/engine/vn.c` (+ `build.sh`): clang `--target=armv7a-none-eabi -marm -Os -ffreestanding -fPIC -nostdlib`, link tại địa chỉ cave; gọi hàm guest qua địa chỉ tuyệt đối tính từ base nạp (dùng PC-relative, vì `.so` là PIC). Script `engine/inject.py` sinh dòng `so_patches.tsv` + `caves.tsv`.
  - Định dạng `vi.bin` (do task này chốt và ghi vào BAO-CAO): header, bảng offset từ, chuỗi UTF-8 hoặc glyph id, 3 bộ font (15px, 10px, 18px).
  - Hoàn thành khi: trong harness, menu chính và một hộp thoại cốt truyện hiện chữ Việt đúng dấu, không lệch dòng; bản không có dữ liệu Việt vẫn chạy y hệt bản gốc (so ảnh từng byte tại 3 mốc khung).
- [ ] T7. Menu chính offline + cash shop miễn phí + hồi sinh offline (phụ thuộc T6 để thấy chữ; logic làm trước được) — Agent: A1
  - Bỏ UPDATE/STORAGE/SUPPORT khỏi lưới; cash shop mua ngay (phân tích `StateCashShop::*`, `MTX`, `nativeResultMTX` 0x14d568); màn chết hồi sinh không mạng.
  - Hoàn thành khi: harness điều khiển phím vào shop, mua 1 vật phẩm cash, vật phẩm vào túi, không có lời gọi socket/MTX (log); menu chính đi được hết các ô.
- [ ] T8. Bridge: tắt hook chữ + HD ÷k + NEAREST (độc lập với T6/T7) — Agent: A1 — **Quản đốc nghiệm thu**
  - Tắt vòng cài hook chữ (vòng ở 0xc6c30..0xc6d4c lặp 10 symbol `BFont::*`, callback 0xcf64c) và hook font metrics (0xc8cfc). Công thức khung logic ở 0xc5cc0..0xc5d44 (đặt `[x26+0x58]` và biến toàn cục 0x1fdac8) đổi sang ÷k. Tìm chỗ gọi `glTexParameter*` và viewport/ortho trong `nativeOnDrawFrame` 0xcb7e4, đổi sang NEAREST + phóng số nguyên. Toạ độ chạm phải chia đúng k và offset căn giữa (`nativeOnTouchEvent` 0xcc614).
  - Patch bridge ghi vào `patch/bridge_patches.tsv` (cùng định dạng), áp cả bản armeabi-v7a nếu là cùng code (kiểm tra; nếu khác thì ghi Vướng mắc).
  - Hoàn thành khi: kiểm tra tĩnh (disasm trước/sau) + giả lập các hàm đã sửa bằng unicorn arm64 với đầu vào 2400x1080, k=1..4 cho ra W,H,viewport đúng.
- [ ] T8m. Bridge tối thiểu cho v0.1 (phụ thuộc T6) — Agent: A1 — **Quản đốc nghiệm thu**
  - (1) Tắt hook chữ theo BAO-CAO §9.4: bỏ vòng 10 hook (0xc6c30 → 0xc6d4c) + 2 `bl 0xe8834` (metrics, 0xc6dd8/0xc6e60) → `mov w0,#1`; xác minh lại địa chỉ bằng br.dis trước khi vá.
  - (2) Logic = màn ÷ k, k mặc định = max(1, floor(scrH/240)) (T10 sau này cho chọn ÷1..÷4): sửa đoạn tính W/H 0xc5cc0–0xc5d44 thành W = scrW/k, H = scrH/k (làm chẵn), giữ min 400x240 thì giảm k.
  - (3) Lọc NEAREST (GL_TEXTURE_MIN/MAG_FILTER = GL_NEAREST 0x2600 thay GL_LINEAR 0x2601) cho texture màn hình + viewport = W*k x H*k căn giữa (phần dư viền đen), không kéo giãn lệch tỉ lệ.
  - (4) Kiểm ElfLoader của bridge: cách ánh xạ PT_LOAD/zero bss (cho bản vá p_memsz T6b) — ghi kết luận vào BAO-CAO.
  - Patch ghi `patch/bridge_patches.tsv` (off, gốc, mới, ghi chú; script áp dụng kiểm byte gốc) + diff disasm trước/sau. Giả lập hàm tính W/H bằng unicorn arm64 với 2400x1080, 1920x1080, 1600x720, 1280x720, 800x480 → bảng (k, W, H, viewport).
  - Harness: guest chạy được ở các W×H đó (ít nhất 600x270, 640x288, 480x240, 400x240): tiêu đề → menu → vào game → đi 30 s, không crash; ảnh vào `shots/T8/` (≤ 8 file).
- [ ] T9. Guest chạy đúng ở khung lớn (phụ thuộc T6) — Agent: A1 — **Quản đốc nghiệm thu**
  - Harness ở 400x240, 534x240, 800x360, 1200x540, 2400x1080: ghi lỗi (UI neo sai, mảng cố định, camera, crash). Sửa trong guest.
  - Hoàn thành khi: 5 cỡ đều đi được tiêu đề → menu → vào game → di chuyển 30 s không crash, UI đúng chỗ (ảnh trong `shots/T9/`).
- [ ] T10. Tuỳ chọn ÷k trong Cài đặt + font 18px ở HD (phụ thuộc T8, T9) — Agent: A1
  - Thêm mục vào `StateMainMenu` Option (vẽ/phím), ghi `hd_scale.txt`; bridge đọc ở boot. Font 18px + khoảng dòng khi logic cao ≥ 480.
  - Hoàn thành khi: harness đổi mục rồi file được ghi; ảnh menu ở 1200x540 dùng 18px, không chồng dòng.
- [ ] T11. Vẽ lại ảnh có chữ tiếng Anh (độc lập) — Agent: A1
  - `img/menu.mgr`, `img/ui.mgr`, `img/touch.mgr`, `sp/imgcom/title.mgr` (+ logo "Việt hoá bởi NAM.P" như LGT r5/r6), dùng `img5/` của project và pixeltext. Định dạng `.mgr` xem BAO-CAO-KY-THUAT B12.
  - Hoàn thành khi: ảnh ghép trước/sau trong `shots/T11/`; harness hiện đúng.
- [ ] T12. Đóng gói APK + kiểm tra cuối (phụ thuộc tất cả) — Agent: A1 — **Quản đốc nghiệm thu**
  - `hl5_android/make_apk.sh`: áp patch guest/bridge, pack VFS, cập nhật `target-assets.json`, zip (giữ nguyên thuộc tính nén của `.so`/asset như APK gốc), zipalign, apksigner.
  - Hoàn thành khi: `apksigner verify` đạt; harness chạy `.so`+VFS lấy từ APK đã đóng gói; báo cáo phát hành `hl5_android/PHAT-HANH.md`.

## Mốc v0.1 — bản chơi được đầu tiên (người dùng giao chạy tự động qua đêm)
- Thứ tự ưu tiên: T6 (chữ Việt) → T8 tối thiểu (tắt hook chữ của bridge, NEAREST, logic gốc) → T7 tối thiểu (offline, cash shop miễn phí nếu kịp) → T12 (APK ký, cập nhật sha256 `target-assets.json`) → harness chơi thử. T9/T10/T11 sau v0.1.
- v0.1 đạt khi: APK cài được (arm64), qua tiêu đề → menu → vào game, chữ Việt hiện đúng, không cần mạng; kiểm trong harness + kiểm tĩnh bridge (không có thiết bị thật).

## Quy tắc thay công nhân (người dùng chốt)
- Một công nhân làm tiếp nhiều task liên tiếp (SendMessage) cho tới khi ngữ cảnh của nó vượt **400k token**; khi đó quản đốc thay bằng công nhân mới (đọc lại PLAN.md). Công nhân phải ghi trạng thái dở dang vào PLAN.md trước khi bị thay.

## Phân công hiện tại
- A1: T1–T6 ✓ → T8m (đang giao) → T7 tối thiểu → T12.
- A2: (chưa có)

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- [A1/T3a] Aligner ghép sai theo SỐ FILE: scn Android lệch số với LGT (Android 00207 = LGT 00208, 00209 = 00211, 00210 = 00212, 00213 = 00215, 00215 = 00216, 00216 phần lớn mới). Hậu quả: một số câu KHÔNG phải NEW bị gán K sai nghĩa (vd 00216 idx 3,4,20 nhận "Phát hiện kẻ xâm nhập"/"Bắt đầu hành động loại trừ"/"Cái quái gì nữa đây..."; 00214 idx 14 "It needs some improvements." nhận "Nhà kho cũ"; 00186 idx 18 nhận "Chơi lại game..."; 00094/00108/00136/00144/00170/00174 có các K lệch 1-36 vị trí). Đề nghị quản đốc cho T2c soát lại ghép các file này trước T4. Với NEW, A_dich.tsv đã dịch từ tiếng Anh và tái dùng đúng K Việt có sẵn ở các file lệch số.
  - [A1/T2c] ĐÃ XỬ LÝ: ghép file theo bytecode; 00216 idx 3/4/20, 00214 idx 14, 00186 idx 18 nay đều NEW (không còn gán K sai).

- [A1/T6b] p_memsz ở file offset 0x88 (BAO-CAO §9.3 ghi nhầm 0x98); bridge ElfLoader có map/zero đúng memsz hay không vẫn là việc T8.
- [A1/T6a] (1) Hook đo của bridge (xem Nhật ký T6a, BAO-CAO §9.4) làm engine vô hiệu trên máy thật nếu không vá T8; harness không chạy bridge nên T6b không kiểm được, cần quyết: vá bridge hay đổi tên symbol guest (trái quyết định #2). (2) Từ rộng hơn hộp nhỏ nhất gây vòng lặp vô hạn trong GetTextFieldPageLen. (3) Danh sách rủi ro còn lại: BAO-CAO §9.6.
- [A1/T2d] (1) `A_dich.tsv` có 1 dòng hỏng từ trước (A01012, text_en chứa byte 0x0a thật, vỡ thành 2 dòng, làm `update_A_dich` lỗi khi chạy lại): đã sửa bộ đọc/ghi, nay ghi `\n` literal trong cột Anh (nội dung không đổi); T3/T4 cần biết. (2) 00136 sai gần cả file (26/28 dp); 00107/00170/00174 sai theo cụm. (3) Hàn `EVENT_게임오버` (00144 idx 1, 00150 idx 12) là nhãn nội bộ, đánh LỆCH.

## Nhật ký
- [quản đốc] Lập PLAN.md; các phân tích nền nằm trong BAO-CAO-RE.md §1–7; harness + 4 patch offline đã xong.
- [A1] T1 xong: `vi/extract_android.py` -> `vi/android_catalog.json` 14298 câu (scn 7163 / 218 file, dat 7135 / 85 file gồm 5 file VFS không tên); roundtrip 0 lỗi, chạy lại giống hệt. Lưu ý `tools/scn.py:parse` chọn sai hit ở vài file (00154...), dùng `extract_android.parse_scn/build_scn`.
- [A1] T2 xong: `vi/align_android.py` -> `vi/align.json`, `vi/align_report.md`: ghép 13763 / NEW 535; 176 scn cùng số câu (5851 câu) điểm cao 99.5%. Chờ quản đốc nghiệm thu.
- [quản đốc] Nghiệm thu T2: đạt tiêu chí số (99.5%), nhưng dải 0.55–0.70 có cặp sai nghĩa → giao T2b. Chấp nhận dùng `extract_android.parse_scn/build_scn` thay `tools/scn.py` cho T4.
- [A1] T2b xong: NEW 535 -> 1008 (scn 348, dat 660); 485 NEW có ko_hint (326 do luật `;` vượt >= 2, 139 điểm < 0.70, 15 giữ chỗ, 5 dài > 3.5 lần). Luật `;` có thể quá chặt với dat (Android ngắt dòng lại) -> quản đốc cân nhắc nới. Report có mẫu 40 cặp dải 0.70-0.80; chạy lại ổn định.
- [A1] T5 xong: `tools/vfs_pack.py`; pack không thay gì = giống hệt từng byte; overlay thử (menu_text.dat) thay đúng 1 mục (thứ tự/khoá giữ nguyên); harness (p1.so) boot tới menu chính, ảnh khung 459/579/599 giống bản gốc.
- [quản đốc] Nghiệm thu T2b: NEW 1008 (485 có ko_hint); mẫu 0.70–0.80: 38/40 đúng, #33 (00107 idx 12) sai nghĩa, #10 lệch số → thêm kiểm tra số vào T4. Giữ luật `;` (326 câu): T3 tái dùng bản Việt của `k_low` nên chi phí thấp. Nghiệm thu T5 (roundtrip từng byte + boot harness).
- [A1] T3a xong: 348 NEW scn dịch tay -> `vi/A_dich.tsv` (A00661-A01008; A00001-A00660 giữ chỗ cho 660 dat, cột Việt trống chờ T3b) + `vi/check_A.py` (mã điều khiển, ký tự cấm, số, độ dài bằng `Encoder` của build_vi5, từ điển mới; `--kind dat|all` cho T3b). Kết quả: 0 lỗi, dài nhất 123 byte (cap 254), từ điển 4548 -> 4727 / 5076 (còn 349 cho T3b). T3 chưa tick (chờ T3b).
- [quản đốc] Nghiệm thu T3a: soát mẫu bản dịch đạt; check_A 0 lỗi; từ điển 4727/5076. Vướng mắc lệch số file: xác minh đúng (00216 idx 3 điểm 0.829 nhưng sai nghĩa) → giao T2c ghép file theo bytecode script.
- [A1] T2c xong: ghép file scn theo nội dung (bytecode Dice 4-gram + tên/số): 13 file đổi ghép (00207->00208, 00208->00209, 00209->00211, 00210->00212, 00211->00213, 00212->00214, 00213->00215, 00215->00216; 00019/00175/00186/00214/00216 không ghép = toàn NEW). NEW mới: scn 338 (trước 348), dat 669 (trước 660), tổng 1007. A_dich.tsv: giữ nguyên 1008 dòng cũ (36 scn + 1 dat đánh `unused`), thêm A01009-A01044 (26 scn + 11 dat) chưa dịch -> cần T3a-bổ sung 26 câu scn. check_A: 348 dòng đã dịch 0 lỗi (26 báo chưa dịch). Chạy lại ổn định (md5 align.json/report/A_dich).
- [quản đốc] Nghiệm thu T2c: đạt (13 file đổi ghép, ví dụ Vướng mắc đã sửa). Soát mẫu thấy cặp DP 00136/00108 sai nghĩa với điểm 1.0 → giao T2d soát nghĩa thủ công.
- [A1] T2d xong: đọc tay 1280 cặp (`vi/review_T2d.tsv`, 980 dp + 150 idx + 150 dat; mẫu seed 20240601). SAI/LỆCH: dp 64/980 (SAI 61, LỆCH 3; tập trung 00136 = 26/28 sai cả file, 00107 idx 11-20, 00170, 00174, 00144 idx 55-65, 00198, 00087), idx 1/150 (0.7%), dat 1/150 (0.7%) -> dưới 3%. `align_android.py` đọc review làm ghi đè (SAI/LỆCH -> NEW, giữ k_low/ko_hint). NEW 1007 -> 1073 (scn 403, dat 670). A_dich.tsv thêm A01045-A01110 (66 ID, chưa dịch); chạy lại 2 lần md5 giống nhau; check_A: 0 lỗi thật, 91 dòng "chưa dịch" (25 cũ scn + 66 mới). Các đề xuất K đúng nằm ở cột ghi chú review.
- [quản đốc] Nghiệm thu T2d: đạt (mẫu idx/dat 0.7% SAI < 3%; soát 6 phán quyết SAI ngẫu nhiên đều đúng). Giao tiếp: T3a-bổ sung (mọi scn chưa dịch) + T3b-1 (dat NEW nửa đầu theo thứ tự A#####), rồi T3b-2 (nửa sau) ở phiên công nhân mới.
- [A1] T3a-bổ sung xong: 91 dòng scn chưa dịch (A01019-A01110) dịch tay từ tiếng Anh. T3b-1 xong: 431 dòng dat (A00001-A00432, vượt mốc 335 vì cùng loại item/skill), còn 239 dòng dat cho T3b-2 (A00433-A01045). Lưu ý A00008 'oAl' là mảnh tách sai của 'Allows' (A00009 dịch trọn câu, A00008 giữ nguyên). check_A --kind all: 0 lỗi thật (240 'chưa dịch' = phần T3b-2), từ điển 4762 / 5076.
- [quản đốc] Nghiệm thu T3a-bổ sung + T3b-1: check_A 0 lỗi thật; soát mẫu đạt, tên lớp giữ tiếng Anh đúng thuật ngữ; từ điển 4762/5076. Còn 239 dòng dat → T3b-2. Lệnh kiểm: `check_A.py vi/A_dich.tsv --kind all`.
- [A1] T3b-2 xong: 240 dòng dat còn lại (A00433-A01045, gồm A00149 unused) dịch tay -> `A_dich.tsv`; từ điển 4795 / 5076. Lưu ý cho quản đốc/T4: (1) `check_A.py` đặt cap dat = 255 cứng (key None), nhưng catalog cap = 4000 cho ps 2: 15 dòng help (A00572/576/597/629/644 + bản trùng) mã hoá 289-354 byte nên báo QUÁ DÀI giả; bản sao đã sửa cap cho 0 lỗi, cần sửa `check_A.py`. (2) Các mảnh skill (A00488-A00504, 'T+r...', 'osts...') thực ra là lát của MỘT chuỗi dài trong bản ghi (tiền tố độ dài thật nằm trước 'Costs...', các 'tiền tố' trong catalog là trùng hợp), nên T4 phải ghép/ghi lại theo cả chuỗi, không thay từng mảnh.
- [quản đốc] Nghiệm thu T3b-2: dịch đủ, 15 "QUÁ DÀI" là lỗi cap cứng của check_A (ps2 = 4000). Phát hiện chuỗi dat bị cắt mảnh (skill_*, cash_expert) → giao T1b trước T4.
- [A1] T1b xong. NGUYÊN NHÂN: `rec_strings` (DP cực đại độ phủ chữ cái) đoán chuỗi theo tiền tố u8/u16 và byte độ dài của chuỗi thật thường là chữ in được ('o'=111, 'D'=68, ';'=59, ' '=32, 'T'...), nên DP chọn cách cắt khác (chuỗi dài tách 2-3 mảnh, mảnh đầu lẹm tiền tố 'T+r', 'oAl'); KHÔNG do byte >= 0x80. BỐ CỤC: mỗi file dat có khung cố định = dãy (bỏ k byte | chuỗi u8/u16), hết bản ghi đúng 0 byte; bảng `DAT_LAYOUT` trong `extract_android.py`, 57 file / 4814 bản ghi đều tiêu đúng (sai thì báo lỗi). Bằng chứng mã máy (so.dis): item_NN `ItemTable::LoadItemTable` 0x9f82c (id2 | tên u8 | int32 | mô tả u8 | đuôi: 21 cho 0-10, 4/2/0/0/13/4/4/2 cho 11-18; mô tả item_05..10 chỉ là 1 dấu cách); skill `HERO::LoadResSkillInfo` 0x87ee0 (tên u8 | 47 byte | mô tả u8, hết bản ghi); smith `HERO::LoadMixSmithTableInfo` 0x87c94 (id2 | tên u8 | int32 | vật liệu u8 | 13); quest `StateInGameMenu::SetMapMode` 0xe553c insn e5854-e5884 (3 | tên | mô tả | loại/khách u8 | 36 + 2 byte đọc, tổng đuôi 38); cash `CashShop::LoadCashShopData` 0xd9f04 (3 | tên | 2 | mô tả | đuôi 2, bản android 6); help `TextMgr::LoadHelpString` 0x13fc2c (tiêu đề u16 | nội dung u16); text/name/tips/mission_text = cả bản ghi 1 chuỗi u8 (TextMgr::LoadRes 0x13fd54); các file còn lại (buildup, class, mission_list, set_option, enemy_*) theo khung bản ghi cố định (mọi bản ghi tiêu đúng). Catalog dat 7135 -> 7121 (scn không đổi 7163): 29 mục bỏ (cash_expert 2, enemy_expert_0 5 'HX' nhiễu, item_11 1, item_15 2, skill_00..03 16, smith_1/2 3), 15 mục thêm (chuỗi gộp: cash_expert 1, item_11 1, item_15 2, skill 10; common_text rec 137 'Success: \xf1ombined' vì byte 0xf1 lạ). Roundtrip 0 lỗi, chạy lại giống hệt, kiểm mảnh cắt giữa từ = 0 (trước 2). align chạy lại 2 lần giống md5: NEW 1073 -> 1053 (scn 403, dat 650). 10 chuỗi skill/item_15 gộp nay khớp thẳng K (điểm 0.85-1.0, dùng bản Việt của người dùng; T4 kiểm số) nên CHỈ 2 ID mới cần T3c: A01111 (cash_expert rec 7, hint=A00008,A00009), A01112 (item_11 rec 12, hint=A01012). 21 dòng cũ (mảnh/nhiễu) đánh `unused`. `check_A.py`: cap lấy từ catalog (dat ps1 255 / ps2 4000), dòng `unused` bỏ qua, `chưa dịch` tách khỏi lỗi: 0 lỗi, 2 chưa dịch (A01111-12), từ điển 4787 / 5076.

- [quản đốc] Nghiệm thu T1b: đạt (bố cục dat lấy từ hàm nạp, 4814 bản ghi dư 0 byte; skill_00 rec42/skill_02 rec6 nay là chuỗi nguyên, ghép K đúng). Giao T3c (2 dòng) + T4.
- [A1] T3c + T4 xong. T3c: A01111, A01112 dịch tay (từ `hint`); sau build, 8 câu LỆCH SỐ thật được thêm A01113-A01120 (buildup rec 26 `#2`, menu_text `100K` + đồng ý thông tin, mission_text 3 dòng Vàng 10,000/100,000/1,000,000 mà bản K bỏ số, 00001 idx 3 "5 years", 00035 idx 38 "70-30") và dịch tay; check_A --kind all 0 lỗi, từ điển 4788 / 5076. T4: `vi/build_android_vi.py` (chạy lại md5 giống nhau, ~18 s) -> `$WORK/vi_android/` (260 file đổi: `res/c/...`; 5 entry VFS không tên ở `vfs_unnamed/0xHASH.dat`; dict.txt, font.bin, font10.bin, f18.bin, offline.bin, bao-cao-build.txt, data.vfs.mp3). Thay 14282/14284 câu (2 giữ Anh vì K không có bản dịch: K00239 `(A)END` mẫu ngữ pháp, K07780 00089 idx 129 nhãn EVENT_); QUÁ DÀI 0; LỆCH SỐ còn 0 (8 đã chuyển sang A); LỆCH MÃ 0; thiếu glyph 0; roundtrip 0 lỗi (đọc lại từng chuỗi, build(parse)=bytes), khung (số bản ghi, byte ngoài trường chuỗi, header scn, số chuỗi) lệch 0. Quyết định kỹ thuật: (1) từ điển nạp sẵn 4548 mục LGT (mã giữ nguyên thứ tự) rồi nối từ mới; (2) kiểm mã bỏ `;` (bản Anh đã ngắt dòng, bản Hàn thường không: 1550 câu) và reflow bản Việt theo bề rộng dòng Anh rộng nhất khi số `;` khác; dấu `'` giữa chữ cái (Marc's) coi là chữ; `%r` trong `#1%reduce` không phải printf; (3) kiểm số bỏ `\u0000` rác và `1H/2H/2-Hand` khi bản Việt viết "một/hai tay"; (4) dòng LECHSO trong A_dich.tsv được build dùng dù note có thể bị `unused` nếu chạy lại align_android. Harness: VFS Việt (`vi_android/data.vfs.mp3`) + p1.so boot tới menu chính (khung 599 lưới 3x3 như bản gốc; chữ thô vì engine là T6).
- [quản đốc] Nghiệm thu T3c+T4: đạt (14282/14284 chuỗi, 0 QUÁ DÀI, 8 LỆCH SỐ đã dịch, roundtrip 0 lỗi, build tái lập md5, harness boot tới menu). Chấp nhận các lựa chọn: nạp sẵn 4548 từ LGT, bỏ qua `;` khi so mã. T6 chia: T6a thiết kế (quản đốc duyệt) → T6b implement.
- [quản đốc] T6a bị dừng giữa chừng do hết hạn mức usage (reset 14:40 UTC), chưa ghi gì vào BAO-CAO (nháp nếu có ở `$WORK/t6a/`). Tiếp tục: gửi tiếp cho công nhân T6a hoặc giao lại T6a nguyên văn. Lưu ý BAO-CAO đã có "## 8. Việc tiếp theo" → thiết kế ghi thành §9.
- [A1] T6a xong (thiết kế, chưa implement): `BAO-CAO-RE.md` mục 9; script chứng minh cave `$WORK/t6a/refscan.py` + `refscan.out`. Kết luận chính: (1) hook tối thiểu = vá 5 `ldrsb [font,#70]` (0x40ee8, 0x40f6c, 0x41510, 0x4170c, 0x4178c) thành `bl thunk` + vá `bl MX_fntDrawString` tại 0x41804 (đường vẽ 2 byte duy nhất của DrawText); GetLineCount/PageLen/IsTextFieldDrawComplete/DrawTextLine/Field đều đi qua GetByteLenPrintable nên không cần móc. (2) `vi.bin` ~40 KB (4788 từ u8-glyph + 3 atlas fontcus 15/10/18), nạp lười bằng `StaticUtil::LoadRes` 0x1417a4, ~56 KB RAM. (3) Cave A = `Map::MapMove_back` 0xaef38 (2620 B), B dự phòng = `AndroidService` ctor 0x14afb4; cave không ghi được nên trạng thái đặt ở bss 0x1704c0 + vá p_memsz (file off 0x98). (4) BRIDGE: có hook đo (callback 0xcfdd0, cài ở 0xc6dd8 GetWidth(Pciii) và 0xc6e60 GetByteLenPrintable) đặt R0 và PC=LR nên BỎ QUA thân hàm guest; T8 BẮT BUỘC thay hai `bl 0xe8834` bằng `mov w0,#1`; nên bỏ cả vòng 10 hook chữ (c6c30 `b` -> c6d4c). Địa chỉ 0xc8cfc trong T8 là nhánh lỗi, không phải chỗ cài. Chờ quản đốc duyệt.

- [quản đốc] Duyệt T6a (BAO-CAO §9): ĐỒNG Ý thiết kế (vá 5 `ldrsb [font,#70]` + `bl` 0x41804 → `vn_draw`; cave MapMove_back; trạng thái bss 0x1704c0 + vá p_memsz). Điều kiện cho T6b: (a) không được treo vòng page-len — từ rộng hơn hộp phải vẫn tiến ≥ 1 đơn vị/dòng (test bằng hộp hẹp nhất); (b) `vn_draw` không phải mã Việt → đường gốc y hệt (so ảnh từng byte bản không vi.bin tại 3 mốc); (c) xác minh mode/màu bằng test 3 trước khi vẽ chữ Việt; (d) ở 18 px bỏ mode 1/2 như đề xuất. Kiểm ElfLoader bridge (ánh xạ/zero bss) chuyển sang T8.
- [A1] T6b xong (chờ nghiệm thu). `engine/{vn.c,build.sh,inject.py,mk_vibin.py,test_vn.py,patch_diff.txt}`; 21 dòng vào `patch/so_patches.tsv` (khối `# >>> vn-engine`, mỗi dòng kiểm byte gốc), `patch/caves.tsv`; `vi.bin` 47608 B thêm vào VFS bằng `vfs_pack --add c/vi/vi.bin=...`. Kiểm: (1) harness: menu chính ("Bắt đầu, Chuyên Gia, Vào kho đồ...") và hộp thoại cốt truyện đúng dấu, không lệch dòng, ảnh `shots/T6/`; (2) KHÔNG vi.bin: ảnh khung 459/579/599 và run.log giống từng byte bản gốc p1.so; (3) `test_vn.py`: hộp 1..200 px với 6 từ rộng nhất (90 px) không treo (PageLen/LineCount); (4) 0 relocation sau link; (5) diff disasm ở `engine/patch_diff.txt`. Điều kiện (a)-(d) đã đáp ứng; mode/màu lấy đúng theo mã máy `_midas_funcFntDrawString` (màu thể hiện đúng ở menu/thoại; chưa so từng điểm với VnFont Java). Chưa kiểm: bridge thật (T8), chế độ 18 px (cờ `vn_state.hd` mặc định 0, đường cơ sở 18 px để T10). Dùng tạm: `python3 patch/apply_so.py p1.so p2.so` rồi `hl5run.py --so p2.so --vfs vi.vfs`.
- [quản đốc] Nghiệm thu T6b: đạt (menu + hộp thoại tiếng Việt đúng dấu; không vi.bin = y hệt gốc; 0 reloc; không treo). Ghi chú: nhãn menu chính dài tràn ô ("Vào kho đồ", "Hỗ trợ game", "Cập nhật") → rút gọn ở T7 (menu_override). Giao T8m.
