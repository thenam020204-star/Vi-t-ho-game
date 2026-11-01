# HL5 Android (heroes-lore-5-v1.0.5.apk): báo cáo dịch ngược, phiên 1 (2026-11-01)

Mục tiêu (người dùng đã chốt):
1. Áp bản Việt hoá LGT (`HL4_project/vi5`, r9_VI) cho bản Android.
2. Font 18px (`NAM_P/fonts/f18.bin`), **chỉ dùng ở chế độ HD**. Ở khung logic gốc dùng font như bản LGT (15px/10px).
3. Cash shop miễn phí, không cần mạng, Việt hoá tên và mô tả vật phẩm.
4. "Logic HD thật": khung logic = màn thật ÷ k. Người chơi **chọn k trong game** (÷1/÷2/÷3/÷4), mặc định ÷2. Phóng theo số nguyên, lọc NEAREST, không kéo giãn mờ.
5. Máy test: arm64, Android 10+.

> Lưu ý: bản Android là mã máy ARM (`.so`), không phải bytecode J2ME. Các quy tắc KVM/CLDC/preverify **không áp dụng** cho bản này. Vẫn giữ các nguyên tắc: mã máy thật là chuẩn, mỗi patch có mapping và diff, không đoán.

## 1. Kiểm kê APK
| File | Nội dung |
|---|---|
| `assets/libHeroesLore5.so` (= `lib/armeabi*/libHeroesLore5.so`, giống hệt nhau) | Game gốc EA Mobile, ARM32 EABI5, gcc 4.4, **không strip**: có `.symtab` đầy đủ (tên C++ như `BFont::DrawText`, `StateCashShop::KeyPressed`…). Có DWARF nhưng chỉ cho phần MIDAS (`jni/midas/*.cpp`). |
| `assets/data.vfs.mp3` | Kho tài nguyên VFS (16,4 MB, 2205 mục). |
| `lib/arm64-v8a/libhl5_arm64_bridge.so` (và một bản armeabi-v7a) | **Bridge Unicorn**: giả lập ARM32, có ElfLoader, cầu GLES 1.1, JNI giả, VFS, lưu game. Đã strip, không có mã nguồn. Java `co.kr.eamobile.hl5.runner.MainActivity` **luôn** nạp bridge này (cả trên máy 32-bit). |
| `assets/target-assets.json` | sha256 của `libHeroesLore5.so` và `data.vfs.mp3`. Bridge/runner kiểm tra hash. **Sửa file nào thì phải cập nhật hash.** |
| `assets/mtouchdevice.txt` | Ghi chú "v1.0.9, MultiScreen by Xiaozhiche, Mod by Rostislav582 & Xiaozhiche". |
| `classes.dex` | Lớp Java EA gốc (`CletEntry`, `MidasActivity`…). `CletEntry.adjustAutoScreen` (mod MultiScreen) chứng tỏ game chạy được với nhiều kích thước LCD. |
| `classes2.dex` | Runner mới (`hl5.runner.MainActivity`, `TargetAssets`, `CompatibilityActivity`). |

## 2. Định dạng VFS (đã xác nhận bằng mã máy)
- `MIDASKernelManager::getAssetSizeFromVFS` @0x14c7e4: đọc tuần tự các bản ghi `[u32 hash][u32 size][size byte dữ liệu]`, bắt đầu từ offset `[this+0x614]` (=0) tới `[this+0x618]` (độ dài).
- `MIDASKernelManager::hash` @0x14c520 = **djb2**: `h=5381; h=h*33+c`.
- Khoá băm là đường dẫn **không có `/` đầu**, ví dụ `c/iconpal/231_001.pal` (file LGT `res/c/...` bỏ tiền tố `res/`).
- Dữ liệu lưu thô. Mã hoá (nếu có) do game tự xử lý (`StaticUtil::LoadResDecrypt`, `MX_desDecrypt`), giống bản LGT.
- So với `HL5.zip` (LGT): **1820/2140 file giống hệt từng byte**, 320 file khác (244 `.scn`, 56 `.dat`, 7 không đuôi, 5 `.ext`, 4 `.mgr`, 4 `.cif`). VFS có thêm 65 mục không có tên trong LGT. Script: `tools/vfs2.py`.
  - Hệ quả: overlay Việt hoá của `build_vi5.py` dùng thẳng được cho 1820 file. Với 320 file khác thì phải chạy lại phần trích/thay theo câu tiếng Hàn trên bản Android (dùng `extract5.py` cho file Android + map câu Hàn sang câu Việt bằng `HL5_dich_FULL.tsv`).

## 3. Hiển thị / độ phân giải
- Game là **màn ngang, logic gốc 400x240**.
- Bridge (`nativeOnSurfaceCreated` @0xc5cc0 trong bridge arm64) tính khung logic:
  `W = max(400, ceil(scrW*240/scrH))`, `H = max(240, ceil(scrH*400/scrW))`, làm tròn lên số chẵn, lưu vào `[x26+0x58]` và biến toàn cục 0x1fdac8, rồi gọi guest `nativeInitDisplay`.
  Ví dụ 2400x1080 thì logic là 534x240, phóng ~4,5x kiểu tuyến tính, nên **mờ**.
- Phía guest: `Java_..._nativeInitDisplay` @0x1499dc gọi `setTargetDisplayInfo` (+0x624/0x628 = cỡ đích, +0x62c.. màu/margin/mag/filter/viewmode), `setWIPIDisplayInfo(w,h,colorType)` @0x14d09c (+0x4fc/0x500 = cỡ WIPI, cấp phát framebuffer), `init2DScreen` @0x14cfc8 (glViewport/glOrthof), `createTextures`.
- Hướng làm HD:
  1. Patch bridge: thay công thức trên thành `W=scrW/k, H=scrH/k` (k đọc từ file cấu hình do game ghi), đổi lọc texture sang NEAREST, viewport phóng số nguyên và căn giữa.
  2. Kiểm tra trong game các mảng cố định theo cỡ màn (camera, cache tile, UI neo theo 400x240), sửa những chỗ không co giãn.
  3. Thêm mục chọn ÷k vào menu Cài đặt.

## 4. Chữ
- Bản LGT: từ tiếng Việt = mã 2 byte (lead 0xC9.., trail 0xA1..) trỏ vào `dict.txt`. HLE thay bề rộng Hangul và lời vẽ glyph.
- Bản Android có lớp `BFont` với symbol: `DrawText(int,int,int,char*,int[,int])`, `DrawTextField`, `GetWidth`, `GetLineCount`, `GetTextFieldPageLen`, `GetByteLenPrintable`, `HanFontHeight`. Font gốc: `c/font/table.dat`, `c/font/type.dat`.
- Bridge **đang có hook chữ riêng** ("display text hook", "font metrics hook") chứa bản dịch **tiếng Anh** nhúng trong `.rodata`. APK hiện tại là bản Anh hoá. Phải **tắt hook này** khi đưa bản Việt vào, nếu không nó sẽ thay chữ.

## 5. Cash shop
- `StateCashShop::KeyPressed` @0xd97ac, MTX qua `CarrierFuncActivity` / `nativeResultMTX` @0x14d568. Chưa phân tích.

## 6. Công cụ
- `tools/show.py <file.dis> <addr hex> <số dòng>`: xem mã đã disassemble (`llvm-objdump -d -C --triple=armv7`).
- `tools/vfs2.py`: đọc VFS và so với tài nguyên LGT.
- Android build-tools 34 (apksigner, zipalign, aapt2, d8, dexdump) tải từ dl.google.com. Python: unicorn, capstone, keystone, lief, pyelftools.

## 7. Phiên 1b: harness, offline, chữ, dữ liệu (đã kiểm chứng trong harness)

### 7.1 Harness headless (`harness/`)
- `hl5run.py` + `hl5libc.py`: Unicorn ARM32, nạp ELF, xử lý reloc, giả libc/GLES/JNI/JavaVM, VFS, lưu game. Trình tự boot như runner thật: InitKernel → InitDisplay(W,H) → FileStorage → VFS → Audio → StartApp → nativeLoop mỗi khung (50 ms ảo). Chụp texture màn hình (RGB565) ra PNG. `sheet.py` ghép ảnh.
- Phím: `nativeKeyHandleEvent(env, this, type, key, 0)`, type 2 = nhấn, 3 = nhả (theo `CletEntry.onKeyDown/Up`). Mã: UP -1, DOWN -2, LEFT -3, RIGHT -4, OK -5, CLR -16.
- Tài nguyên tra qua Java: `getUniqueNumberFromStrings(name)` ↔ `getUniqueAssetNameFromID(id)` (JNI `CallStatic*MethodV`, #115/#130).
- Tốc độ khoảng 40 khung/giây ảo, đủ dùng để test.

### 7.2 Patch offline (`patch/so_patches.tsv`, áp bằng `patch/apply_so.py`, có kiểm tra byte gốc)
| vaddr | gốc → mới | ý nghĩa |
|---|---|---|
| 0x99bfc | `mov r2,#0` → `mov r2,#2` | Boot `StateMainMenu(5, 2)`: bỏ CheckEvent (cần mạng). Constructor tự `LoadLogoSprite(0)` khi state==2. Làm lại tĩnh việc OFFLINE_HARDENING của bridge. |
| 0x120148 | `bne` → `b` | Bỏ popup đồng ý SMS: thẳng `LoadLogoSprite(1)` + `SetSubState(3)`, như khi cờ `[G+42]` đã đặt. |
| 0x11fecc | `beq` → `b` | Màn tiêu đề không `DSP_Connect` kiểm tra save trên server, vào thẳng menu chính. |
| 0x11fef8 | `mov r1,r3` → `mov r1,#1` | `DrawTitle(1)` như nhánh gốc. |
- Kết quả trong harness: logo → mở đầu → tiêu đề → OK → **menu chính 3x3**, không có gói mạng nào.
- Còn mạng: các mục menu UPDATE/STORAGE/SUPPORT (sẽ bỏ khỏi lưới, giống LGT r8), cash shop, hồi sinh.

### 7.3 Engine chữ Android (giống LGT về thiết kế)
- `BFont` (đối tượng font): `+0x18` = 11 mã màu, `+69` = cao dòng-2?, `+70` = độ rộng Hangul−1, `+71` = độ rộng ASCII−1; `;` = xuống dòng.
- Bố cục (`GetWidth` 0x40e30, `GetByteLenPrintable` 0x40f24, `DrawText` lõi 0x41334, `DrawTextLine`, `DrawTextField`, `GetTextFieldPageLen`, `IsTextFieldDrawComplete`) tự tính, đọc `ldrsb [font,#70]` / `[font,#71]`.
- Vẽ: `DrawText` gọi `MX_fntDrawString(fb, gc, fontID, x, y, str+i, len=1|2)` **cho từng ký tự**, rồi gọi tiếp `_midas_funcFntDrawString(x,y,str,len)`, rồi `_midas_funcFntDrawPixel(x, y, color, mode)`. Biến toàn cục `_mxfnt_prt` (+0x40 màu chữ, +0x44 màu đậm).
- Chữ ở đoạn mở đầu, logo, tiêu đề, "Tap to Continue" là **ảnh** (`img/menu.mgr`, `sp/imgcom/title.mgr`…), không đi qua font.
- Kế hoạch: dùng mã từ 2 byte của LGT (lead 0xC9.., trail 0xA1.., `dict.txt`). (1) Vá các chỗ đọc `[font,#70]` để trả bề rộng thật của từ. (2) Chặn `MX_fntDrawString`: gặp mã Việt thì vẽ bitmap (font 15px/10px ở khung gốc, 18px ở chế độ HD) bằng `_midas_funcFntDrawPixel` với màu hiện tại, còn lại gọi hàm gốc.
- Chỗ chứa code: hàm không còn tham chiếu (`tools/deadfuncs.py`, khoảng 22 KB, ví dụ bản sao constructor C1/C2 `StateGame::StateGame(int)` 1588 B, `AndroidService` ctor 2436 B, `Map::MapMove_back` 2620 B). Dữ liệu (từ điển, font) nạp từ VFS lúc chạy. Không dùng các symbol mà bridge tra theo tên.

### 7.4 Dữ liệu: bản Android là **phiên bản game mới hơn** bản LGT
- VFS của APK này **đã Anh hoá** (chữ trong `.dat`/`.scn` và ảnh "AGES 12+", "GAMBLING"…).
- `vi5/build_vi5.py` chạy lại được (đã sửa đường dẫn cứng trong `extract5.py`): 13.645 chỗ, 0 quá dài, từ điển 4548/5076. Overlay 258 file, trong đó 253 có trong VFS.
- `tools/skelcheck.py` (so khung cấu trúc, bỏ chuỗi): **136 khớp, 117 khác**.
  - `.dat`: Android có thêm bản ghi (item_00 81→86, item_18 43→49, menu_text 205→210, common_text 333→351, ingame_text 355→357, quest 152→151), một số trường số khác (skill_00/01/02, enemy_*, item_12, mission_*), `cash_expert.dat` khác định dạng.
  - `.scn`: code kịch bản Android khác (sửa sự kiện, thêm lệnh). 168 file cùng số câu, 33 file khác số câu (vài cảnh mới: 00216 4→54, 00175 1→17, 00214 1→17…). Một số file Android chỉ còn 32 câu, cần kiểm tra lại parser (có thể bảng chuỗi khác định dạng).
- **Hệ quả:** không được thay nguyên file bằng bản LGT. Cách làm:
  1. Giữ file Android (cấu trúc và số liệu), chỉ thay chuỗi.
  2. Ghép chuỗi Android (tiếng Anh) với chuỗi LGT (tiếng Hàn → bản dịch Việt của người dùng): theo chỉ số khi khung khớp, theo khoá nội dung (mã điều khiển, số, ID bản ghi) khi lệch.
  3. Câu Android không ghép được: dịch Anh → Việt theo `thuat-ngu.tsv`/`style-guide.md`, đánh dấu riêng để người dùng duyệt.
- File chỉ bản Android có (giữ nguyên): `ep/ep_*/s*_*.scn`, `mon/*_ai`, `map/(md)*`, `sp/cif/*`, `sp/ext/*`, `calc/calc_sk.dat`, `csv/enemy_g.dat`, `csv/rewards_*.dat`. Ảnh có chữ tiếng Anh cần vẽ lại: `img/menu.mgr`, `img/ui.mgr`, `img/touch.mgr`, `sp/imgcom/title.mgr`.

## 8. Việc tiếp theo
1. Công cụ ghép chuỗi Android ↔ LGT (`.dat` theo bản ghi/trường, `.scn` theo chỉ số + kiểm mã điều khiển), xuất danh sách câu mới cần dịch.
2. Engine chữ Việt trong guest (C → ARM32, chèn vào hàm chết), test trong harness.
3. Đóng gói VFS + ký APK; bridge: tắt hook chữ tiếng Anh, chế độ HD (÷k chọn trong game), lọc NEAREST.
4. Cash shop miễn phí; bỏ mục mạng ở menu chính; hồi sinh offline.
5. Vẽ lại ảnh có chữ.
