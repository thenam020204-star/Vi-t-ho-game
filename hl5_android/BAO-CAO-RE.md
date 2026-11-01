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

## 9. Thiết kế engine chữ Việt (T6a)
> Chỉ thiết kế, chưa implement. Địa chỉ là vaddr của `libHeroesLore5.so` (so.dis). Phần chưa kiểm chứng bằng chạy thật ghi rõ "CHƯA KIỂM". Script chứng minh cave chết: `$WORK/t6a/refscan.py` (kết quả `$WORK/t6a/refscan.out`).

### 9.1 Điểm móc (hook points)
**Mã 2 byte là một đơn vị của đường Hangul sẵn có.** Mọi hàm bố cục kiểm bit 7 của byte đầu rồi bước 2 byte:
- `GetWidth(char*,int,int,int)` 0x40e30: `40e5c tst r1,#128; bne 40ed8`; ở 40ed8 `ldrb r3,[r10+r5+1]; cmp r3,#0; beq` (byte sau phải khác 0), `40ee8 ldrsb r0,[r9,#70]; add r5,r5,#2; add r0,r0,#1; add r0,r0,r8` (mỗi mã cộng `[70]+1`).
- `GetByteLenPrintable` 0x40f24: `40f64 tst r7,#128; beq`, `40f6c ldrsb r3,[r6,#70]; ... cmp r2,r1; addlt r3,r3,#1; addlt r8,r8,#2; addlt r4,r4,r3` (vừa chiều rộng tối đa thì tiêu thụ 2 byte, cộng `[70]+1`; không vừa thì xuống dòng và KHÔNG tiêu thụ).
- `DrawText` lõi 0x41334: `413fc tst r8,#128; bne 41510`; `41580 tst r10,#128; bne 4178c`; vẽ ở nhánh 416fc..41804.
- Kiểm lệnh màu `GetColorBy` nằm sau kiểm bit 7 nên byte đầu 0xC9.. và byte sau 0xA1.. không bị hiểu là mã màu.

**Mọi hàm còn lại đi qua 3 hàm trên** (không cần móc riêng):
| hàm | bằng chứng |
|---|---|
| `GetLineCount` 0x41010 | `bl 0x40f24` ở 41058 |
| `GetTextFieldPageLen` 0x41080 | `bl 0x40f24` ở 41108 |
| `IsTextFieldDrawComplete` 0x4112c | `bl 0x40f24` ở 411c0 |
| `DrawTextLine` 0x41940 | `bl 0x40f24` ở 419d4, `bl 0x418dc` (DrawText) ở 41a88 |
| `DrawTextField` 0x41b00 | `bl 0x40f24` ở 41ba0, `bl 0x418dc` ở 41c84; trả 2 nếu byte cuối âm (41ca4) nên mã Việt (byte sau >= 0xA1) hành xử như Hangul |
| `GetWidth(char*,int)` 0x40f04 | gọi 0x40e30 ở 40f18 |
Quét toàn `.so`: `ldrsb/ldrb rX,[rY,#70|#71]` ngoài BFont chỉ có ở `Formula::getValFunc`, `GUNNER::*`, `HERO::*`, `KNIGHT::KeyPress`, `NETWORK::writeNetItem`, `StateGame::Touch*`, `WARRIOR::*` (đọc trường của struct khác, không phải BFont). Trong BFont chỉ có 10 chỗ: ASCII `[71]` ở 40e80, 40fe4, 41588, 415ec, 41618 (giữ nguyên); Hangul `[70]` ở **5 chỗ cần vá**: `0x40ee8`, `0x40f6c`, `0x41510`, `0x4170c`, `0x4178c`. `HanFontHeight` 0x41078 chỉ `ldrsb [69]` (không đụng).

**Bề rộng từ rộng hơn một ô Hangul.** Ô Hangul = `[70]+1` (BFont chỉ có 1 đối tượng, tạo ở `onStartApp` 0x99880: tham số 11,5,...,9,11 cho `[68]=11,[69]=11,[70]=9,[71]=5`, tức Hangul rộng 10 px, ASCII 6 px). Vá `[70]` thành `W-1` với `W` là bề rộng thật của từ (px, gồm cột đệm) thì mọi công thức `+1` sẵn có cho đúng `W`. Chỉ vá 5 chỗ là cả đo, ngắt dòng, canh giữa (`GetWidth` 4 đối số gọi từ 41390/416e4/41740/41774/417b4) và con trỏ x của `DrawText` khớp nhau (x tăng `[70]` ở 41720/4170c, +1 ở 41490 `addgt r8,r8,#1` khi lần vẽ trước trả > 0; `r7` tăng `[70]+1`).

**Vẽ.** `DrawText` gọi `MX_fntDrawString` ở hai chỗ: 415e8 (ASCII, len=1) và **0x41804 (2 byte, len=2)**: `... str r12,[sp,#4]; str r11,[sp]; bl 0x3cf00; mov r10,r0`. `MX_fntDrawString` 0x3cf00 chỉ còn 2 caller khác là IME (`imeLocal_DrawString` 3d0bc, `imeLocal_DrawButton` 3d1e0, chữ ASCII). Chọn **vá riêng lệnh `bl` tại 0x41804** (`ebffedbd`) thành `bl vn_draw` thay vì chặn đầu `MX_fntDrawString`: không động tới đường ASCII và IME, thunk không phải lưu lại 9 thanh ghi. `vn_draw(fb,gc,fontID,x,y,str,len)` (cùng ABI: `r0..r3`, `[sp]=y`, `[sp+4]=str`, `[sp+8]=len`) kiểm `len==2 && str[0]>=0xC9 && str[1]>=0xA1 && k<nwords`; không phải mã Việt hoặc chưa nạp `vi.bin` thì `b MX_fntDrawString` (nhảy tương đối, giữ nguyên `sp`/`lr` nên tham số stack còn đúng). Mã Việt thì vẽ từng điểm bằng `_midas_funcFntDrawPixel(x,y,color,mode)` 0x376e4 (cùng hàm `_midas_funcFntDrawString` dùng cho ASCII ở 3bef0: `x=*,y=*,color=[sp+0x28],mode=[cur+0x24]`; màu `ldr r12,[r5,#0x40]` ở 3ba64 với `r5=*_mxfnt_prt`, slot GOT `_mxfnt_prt` = 0x15eb54, tra bằng `gotsym.py SO 3b994 3c908 3c90c`). Trước khi đọc màu phải gọi `_midas_funcFntGroup_SetCurrentID(fontID)` 0x31120 (như 3cf64). Giá trị trả về phải **> 0** (41490 `addgt`): trả `1`. CHƯA KIỂM: nguồn đúng của `mode` ([cur+0x24] qua `_midas_internalFntChoInfo` hay `_mxfnt_prt`): T6b so điểm ảnh một ký tự ASCII vẽ bằng đường gốc với cùng ký tự dựng bằng `vn_draw`.

**Bộ móc tối thiểu = 6 lệnh vá + 1 chỉnh header:** 5 `ldrsb` -> `bl thunk_k` và `bl` ở 0x41804. Thunk (tạo bởi `inject.py`, mỗi chỗ một cái) tính con trỏ mã rồi gọi `vn_w(p, orig)`; con trỏ: 40ee8 `r10+r5`; 40f6c `r5+r8`; 41510, 4170c, 4178c `r6+r4`. Thunk giữ nguyên mọi thanh ghi trừ đích và `lr` (cả 3 hàm đã `push {..,lr}`), lấy `orig` bằng chính `ldrsb` gốc rồi trả `W-1` nếu `p` là mã Việt, ngược lại trả `orig`. Cờ NZCV không sống qua 5 chỗ (kiểm: sau 40ee8 `add..add..cmp`, 40f6c `ldr;add;cmp`, 41510 `add;cmp`, 4170c `add` không điều kiện, 4178c `add;mov;b 41594 (ldr;add;cmp)`). Bytes gốc: 40ee8 `e1d904d6`, 40f6c `e1d634d6`, 41510/4170c `e1d534d6`, 4178c `e1d5a4d6`, 41804 `ebffedbd`.

Rủi ro vòng lặp vô hạn: `GetByteLenPrintable` không tiêu thụ ký tự khi `[70]` đã >= bề rộng hộp ngay đầu dòng, `GetTextFieldPageLen` (41110 `add r4,r4,r0`) sẽ lặp mãi nếu trả 0. Giải pháp: kiểm lúc build (T4) mọi từ có `W-1 < hộp nhỏ nhất`; trong engine không kẹp.

### 9.2 `vi.bin`
Tên trong VFS `c/vi/vi.bin` (guest gọi `"/c/vi/vi.bin"`, giống chuỗi `"/c/font/table.dat"` ở rodata 0x152510; `tempLocalGetExactFilename` 0x366b8 bỏ `/` đầu, `getAssetSizeFromVFS` băm djb2). Nạp bằng **`StaticUtil::LoadRes(char*, int*)` 0x1417a4**: nó `MC_fsFileAttribute` (thư mục lưu) rồi `CUSTOM_knlGetResourceID` 0x366f4 -> `MC_knlCalloc` 0x14934c -> `MC_knlGetResource` 0x148f04 -> `MIDASKernelManager::loadAssetFromVFS`; trả buffer (không bao giờ free) và kích thước. Nạp lười lần gọi `vn_w`/`vn_draw` đầu tiên (không cần vá `onStartApp`); `LoadRes` trả 0 thì đặt cờ `-1` và mọi thunk chạy như gốc (tiêu chí "không có dữ liệu Việt = y hệt bản gốc").

Bố cục (little-endian, căn 4):
```
0   char[4] "HLVI"; u16 ver=1; u16 nwords(4788); u16 nchars(<=255); u8 nfonts=3; u8 pad
12  u32 off_wordidx, off_words, off_font[3]          ; offset từ đầu file
    wordidx: u16[nwords+1]  offset (so với off_words) -> entry k, phần tử cuối = tổng
    words  : entry = u8 mode (0 thường, 1 nén, 2 cỡ 10), u8 n, u8 glyph[n]   ; glyph = chỉ số bảng ký tự chung
    chars  : u16 codepoint[nchars] (sắp tăng) nằm ngay sau header font nếu cần debug
    font f (f=0:15px, 1:10px, 2:18px): u8 height, u8 baseline, s8 adv[nchars] (<=0: thiếu glyph),
           bit-atlas: hàng liên tiếp, độ dài hàng = sum(adv), LSB trước, luồng bit liên tục (đúng `fontcus` của font.bin/font10.bin/f18.bin)
```
Dữ liệu đo từ `$WORK/vi_android`: 4788 từ, tổng 17.918 ký tự, dài nhất 15; 160 ký tự khác nhau trong từ điển, hợp 3 font 228 ký tự (mỗi font 227) nên chỉ số glyph vừa u8; atlas 3235/2312/4256 B (chiều cao 15/10/18). **Kích thước file ≈ 27,5 KB (từ) + 9,8 KB (3 atlas) + ~2 KB = ~40 KB.** RAM lúc chạy = file + bảng bề rộng `u8 width[3][nwords]` (14,4 KB, tính ngay sau khi nạp) + bảng offset glyph `u16[3][nchars]` (1,4 KB) ≈ **56 KB một lần `MC_knlCalloc`**. Con trỏ nằm ở struct trạng thái 64 B (9.3).
Quy tắc đo (giống `Hl5Text.measure`): chữ thường `adv`, khoảng trắng cố định 3 px khi mode 1/2 (sẵn trong từ vì Encoder gộp dấu cách đầu từ), mode 1/2 trừ 1 px mỗi chữ có `adv>2`; mode 2 dùng font 10 px. `W` = tổng; `vn_w` trả `clamp(W-1,0,127)`. Dòng vẽ: neo đường cơ sở `y + 9 - baseline` (Hangul 11 hàng, đường cơ sở hàng 9; font 10 px dùng cùng neo như `yOffS` của LGT). Chọn bộ font: mặc định 15/10 px; 18 px khi cờ HD (T10 đặt; T6a để cờ trong struct trạng thái, mặc định 0). Giữ `\x01`/`\x02` của `dict.txt` thành `mode`.
Mã -> từ: `k=(lead-0xC9)*94+(trail-0xA1)`, hợp lệ khi `trail>=0xA1`, `k<nwords`; ngược lại coi là mã Hangul gốc.

### 9.3 Cave và PIC
Chứng minh chết bằng `refscan.py` (word tuyệt đối trong mọi section ALLOC, GOTOFF `w+GOT(0x15e780)`, `b/bl/blx` trực tiếp vào bất kỳ byte nào của vùng, `adr`/`add rX,pc,#imm`, mẫu `ldr rA,[pc,#]; add rB,pc,rA`), cộng `deadfuncs.py` (không symbol JNI/bridge):
| cave | địa chỉ | cỡ | kết quả quét |
|---|---|---|---|
| A `Map::MapMove_back` | 0xaef38-0xaf974 | 2620 | CHẾT, 0 tham chiếu |
| B `AndroidService` ctor C2 | 0x14afb4-0x14b938 | 2436 | CHẾT |
| C `StatePopup` ctor C2 | 0x13f100-0x13f6e8 | 1512 | CHẾT |
| D `MX_knlMakeTime` | 0x35114-0x35698 | 1412 | CHẾT |
| `StateGame` ctor C2 | 0xdfec0-0xe04f4 | 1588 | 1 "word" ở 0x7e828 = 0xE0140: đó là literal gốc GOT (`GMenu::NameBox` 0x7e62c `ldr r4,[pc,#0x1f4]` rồi cộng pc cho ra 0x15e780), chỉ trùng số; không phải tham chiếu, nhưng KHÔNG dùng để khỏi tranh cãi |
Chỉ dùng A (và B nếu thiếu). Phân bổ: A = `vn_init`(nạp+bảng), `vn_w`, `vn_draw`, 5 thunk (mỗi cái ~24 B), hằng số; dự kiến 1,3-1,9 KB `-Os` (CHƯA KIỂM; nếu > 2,5 KB đẩy `vn_init` sang B). `caves.tsv` ghi từng khoảng.
**PIC/relocation:** (1) `bl/b` giữa cave và hàm guest là lệnh tương đối nên không cần reloc (`MX_fntDrawString`, `_midas_funcFntDrawPixel`, `_midas_funcFntGroup_SetCurrentID`, `StaticUtil::LoadRes`, `MC_knlCalloc` đều trong ±32 MB): `inject.py` tính imm24 từ địa chỉ link, không dùng địa chỉ tuyệt đối. (2) Code C không dùng biến toàn cục/chuỗi/GOT tự sinh: build `-fPIC -fno-jump-tables -nostdlib -ffreestanding`, hằng số (tên file `"/c/vi/vi.bin"`) đặt trong cave và truy cập `adr`; lệnh `-fPIC` dùng GOT phải bị cấm (kiểm `llvm-objdump -r` = 0 reloc sau link). (3) Biến toàn cục guest cần dùng (`_mxfnt_prt`) tra theo mẫu của guest: ô GOT 0x15eb54 là R_ARM_GLOB_DAT-REL, lấy bằng `ldr rX,[pc,#lit]; add rX,pc; ldr rX,[rX,#slot-GOTbase]`; `inject.py` điền `lit` bằng `0x15eb54-GOT_anchor` (hằng tương đối, không cần reloc). (4) **Cave nằm trong segment R-X nên không ghi được**: struct trạng thái 64 B đặt ở bss tại 0x1704c0 (sau `.bss` kết thúc 0x1704a8, cùng trang 0x170000-0x171000) và vá `p_memsz` của PT_LOAD thứ 2 tại file offset 0x98 (`20 2d 01 00` -> `20 2e 01 00`, kiểm 0x12d20) để bss thật sự dài 0x100 byte dù loader chỉ ánh xạ đúng memsz. Harness `hl5run.py:118` ánh xạ làm tròn trang nên không phân biệt được: dựa vào kiểm tĩnh. (5) `.ARM.exidx` không có mục cho cave mới; không ném ngoại lệ C++ qua mã mới nên không cần.

### 9.4 Cầu nối Android (bridge) có chặn engine guest hay không
**Có, bị chặn nếu T8 không tắt.** Bridge cài `uc_hook_add(UC_HOOK_CODE=4)` (hàm `0xe8834`, `w2=4`, `begin=end=địa chỉ hàm guest`) tại **đầu** từng hàm tra theo tên. Có hai họ:

**(a) Hook chữ, callback `0xcf64c`, 10 hàm.** Vòng cài `0xc6c30..0xc6d4c`: `adrp x8,0x1fd000; add x8,#0xac0; add x28,x8,x26; ldr x24,[x28,#0x128]` (tên), tra bằng `0xd7620`, `bl 0xe8834` ở **0xc6d14**, `x26+=0x20` đến `0x140` (10 mục). Đọc bảng bằng reloc: k=0..9 là `DrawText(iiiPc)`, `DrawText(iiiPci)`, `DrawText(iiiiPci)`, `DrawText(iiiPcii)`, `DrawTextField`, `DrawTextLine`, `GetLineCount`, `GetTextFieldPageLen`, `IsTextFieldDrawComplete`, `GetWidth(Pci)`. Callback: đọc tham số chuỗi guest, băm FNV-1a (`0x811c9dc5`, `0x01000193` ở cf7b8..cf7e8), tìm nhị phân trong bảng 3620 mục x12 B ở rodata `0x3a928` (hash, offset khoá, offset giá trị; khoá ở pool `0x452d8`), so khớp chuỗi đầy đủ rồi **ghi chuỗi tiếng Anh vào vùng 512 KB mới và sửa thanh ghi/độ dài đối số** (cfc08 `0xd9258`=ghi bộ nhớ, cfc48 `0xe7ffc`=ghi thanh ghi). Đã kiểm: 3547/3620 khoá là chuỗi Hàn CP949, và 3389/3620 giá trị tiếng Anh đã nằm sẵn trong VFS này (VFS đã Anh hoá) nên hook này là tàn dư. Trượt (chuỗi Việt không khớp) thì chuỗi thay thế bằng chuỗi gốc, so bằng nhau ở cfb0c..cfb7c rồi thoát `cfccc` không sửa gì: **trung tính nhưng tốn băm + đọc bộ nhớ mỗi lần gọi**.

**(b) Hook đo ("verified font metrics"), callback `0xcfdd0`, 2 hàm: BẮT BUỘC TẮT.** Cài ở **0xc6dd8** (`GetWidth(char*,int,int,int)`, chuỗi tên 0x35371, `w4=0`) và **0xc6e60** (`GetByteLenPrintable`, tên 0x33186, `w4=1`). Callback đọc 0x48 byte đối tượng BFont (`cfec0`), lấy `[sp+0x5f]=[71]` và `[sp+0x5e]=[70]` (`cfecc ldrsb w23`, `cfed8 ldrsb w24`, yêu cầu 0..0x1f), tính kết quả bằng cài đặt riêng của bridge (`0xd0554` cho GetWidth, `0xd0408` cho GetByteLenPrintable) rồi **`uc_reg_write R0 = kết quả` (d004c, reg 66) và `PC = LR` (d005c, reg 11)**: thân hàm guest không chạy. Vậy 5 chỗ vá ở 9.1 (40ee8, 40f6c nằm TRONG hai hàm này) bị bỏ qua, và mọi mã Việt bị đo cố định `[70]+1` (10 px). `GetLineCount/PageLen/IsTextFieldDrawComplete/DrawTextLine/DrawTextField/DrawText` đều gọi hai hàm này nên hỏng theo. `DrawText` lõi và hook vẽ (41804) không bị chặn bởi họ (b), chỉ họ (a) chạm tới 4 biến thể `DrawText`.
**Hook phải tắt trong T8 (arm64, đã có địa chỉ):**
1. `0xc6dd8` và `0xc6e60`: thay `bl 0xe8834` bằng `mov w0,#1` (`20 00 80 52`). Lệnh sau là `cbnz w0` (c6ddc, c6e64) nên đi nhánh "đã cài" `0xc8cec`. Bắt buộc.
2. `0xc6c30`: `b 0xc6c40` -> `b 0xc6d4c` (`47 00 00 14`) bỏ qua cả vòng 10 hook chữ. Khuyến nghị (đúng đắn không đổi, nhưng bỏ chi phí băm mỗi lần vẽ và loại rủi ro đụng hash).
Địa chỉ `0xc8cfc` trong PLAN là nhánh báo lỗi `"Cannot install verified font metrics hook"` (c8d14), không phải chỗ cài; T8 dùng địa chỉ trên. Đường dự phòng không cần sửa bridge: đổi 1 byte tên hai symbol (`_ZN5BFont8GetWidthEPciii`, `_ZN5BFont19GetByteLenPrintableEPciiii`) trong `.dynstr` và `.strtab` của guest, tra thất bại làm `cbz w21,0xc8cec` (c6db0, c6e38) bỏ qua hook; nhưng trái nguyên tắc "không đổi symbol" của PLAN #2 và chưa biết bridge tra bằng bảng nào, nên chỉ dùng nếu vá bridge không khả thi. Họ hook khác (callback `0xca3b4`, cài ở 0xc6884/0xc6c00; hook ở 0xc69f8; hook ở 0xccfd0) không liên quan chữ, chưa phân tích. Bản `armeabi-v7a` của bridge phải tra lại các hook này (T8). Harness không chạy bridge nên không thể kiểm hiệu quả này trong T6b; chỉ kiểm tĩnh + thử trên máy.

### 9.5 Kế hoạch test T6b (harness)
1. **Bất biến:** không `vi.bin` -> ảnh (RGB565->PNG) khung 459/579/599 giống từng byte bản gốc, và `so_patches` chỉ áp trên `p1.so`; kiểm `LoadRes` trả 0 không treo.
2. **Đơn vị (Unicorn gọi thẳng hàm):** `BFont::GetWidth(Pciii)`, `GetByteLenPrintable`, `GetLineCount`, `GetTextFieldPageLen`, `IsTextFieldDrawComplete` với chuỗi Việt dựng từ `Encoder`, so với mô hình Python (`Hl5Text.measure`, ngắt dòng theo luật `[70]+1`/maxw) trên ít nhất 300 câu từ `vi_android/res` gồm ngắt `;`, mã màu, cuối chuỗi cụt (byte đầu ở cuối), mã `k>=nwords`, từ rộng bằng hộp.
3. **Vẽ:** gọi `DrawText` trên một ký tự ASCII bằng đường gốc và bằng `vn_draw` cho cùng điểm ảnh/màu (xác minh `color`, `mode`, giá trị trả); so điểm ảnh một từ Việt với render tham chiếu của `VnFont` (Java LGT) tại 15/10/18.
4. **Cảnh thật:** `--keys "460:OK,560:OK"` chụp menu chính và một hộp thoại cốt truyện (nêu `--frames`/khung), ảnh vào `shots/T6/`; kiểm không lệch dòng, không đè dấu.
5. **Hiệu năng:** đếm lệnh Unicorn mỗi khung trước/sau; nạp `vi.bin` một lần.
6. **Chống hỏng:** tắt bộ đếm số lệnh, fuzz chuỗi ngẫu nhiên không crash; `vi.bin` hỏng (magic sai) -> tắt engine.

### 9.6 Rủi ro và câu hỏi mở
1. **Bridge (quan trọng nhất):** engine vô hiệu trên máy thật nếu T8 không tắt hook đo (9.4); không kiểm được trong harness. Quản đốc quyết: vá bridge (đề nghị) hay đổi tên symbol.
2. Nguồn đúng của `mode`/màu khi vẽ điểm (9.1) và giá trị trả của `MX_fntDrawString` gốc cho Hangul (đề nghị log trả ở 41808): sẽ chốt bằng test 3.
3. `[69]`/khoảng dòng: font 18 px cao hơn 11; `;` dùng `MX_fntGetStringHeight`+2 (414d8), tràn dòng dùng `[69]+2` (41524). Để T10; T6b không đổi khoảng dòng ở 15/10 px (như LGT, đường cơ sở hàng 9).
4. Từ rộng hơn hộp nhỏ nhất gây vòng lặp vô hạn (9.1): cần quét hộp nhỏ nhất của mọi gọi `DrawTextField/GetLineCount` (T6b log), thêm kiểm vào build_android_vi.
5. Va chạm mã: chuỗi Hàn/Hán tự trong guest có byte đầu 0xC9..0xFB, byte sau >= 0xA1 và `k<4788` sẽ bị coi là từ Việt. Dữ liệu đã Anh hoá nên khả năng thấp; T6b quét chuỗi rodata.
6. Bảng ký tự 18 px dùng chung chỉ mục glyph u8 (228 ký tự hợp, mỗi font 227): ký tự thiếu ở font nào có `adv<=0` thì in `?`; build phải kiểm 0 thiếu (hiện `bao-cao-build.txt` THIẾU GLYPH 0 cho bộ 15 px).
7. Mode 1/2 (nén/10 px) ở chế độ 18 px: chưa có font 10 px tương đương; đề nghị ở 18 px bỏ qua mode (dùng 18 px thường) và để T10 quyết định chữ có vừa hộp không.
8. Cỡ code thực tế và vị trí `.bss` mới (9.3 điểm 4) chưa kiểm; nếu bridge ánh xạ đúng `memsz` mà không làm tròn, bản vá `p_memsz` là bắt buộc.

### 9.7 Kết quả implement T6b (chốt định dạng `vi.bin` v1)
Code: `engine/vn.c` (1669 B trong cave A 2620 B, 0 relocation sau link), `build.sh`, `inject.py` (sinh 21 dòng `patch/so_patches.tsv` + `patch/caves.tsv` + `engine/patch_diff.txt`), `mk_vibin.py` (sinh `vi.bin` 47.6 KB, VFS `c/vi/vi.bin`), `test_vn.py` (đơn vị: hộp hẹp nhất, không treo). Khác thiết kế: (1) `vi.bin` bỏ bảng bề rộng tính lúc nạp; bề rộng cộng tại chỗ từ `adv0/adv1` (không cấp phát thêm, RAM = đúng file); (2) thunk truyền thêm `maxw`,`cur` cho `vn_w`: khi `cur==0` kẹp bề rộng từ <= maxw-1 để từ rộng hơn hộp vẫn tiến (điều kiện (a); DrawText 41510 cũng có vòng vô hạn tương tự, nay cũng được kẹp); (3) `vn_draw` chạy phần thiết lập của MX_fntDrawString bằng chính nó với len=0 rồi vẽ điểm (màu `[*_mxfnt_prt+0x40]`, mode `[+0x24]`, GOT 0x15eb54); (4) `p_memsz` nằm ở file offset 0x88 (không phải 0x98 như §9.3). Bố cục `vi.bin`: xem docstring `mk_vibin.py`.

### 9.8 ElfLoader của bridge và các bản vá bridge T8m
**ElfLoader (kết luận cho bản vá `p_memsz` của T6b):** hàm nạp guest (0xd5eb0..0xd6108) duyệt mọi PT_LOAD của ELF32: `lo = min(p_vaddr)`, `hi = max(p_vaddr + p_memsz)` (0xd5f44..0xd5f5c), `uc_mem_map(base, (hi-lo+0xfff)&~0xfff, RWX)`; rồi với mỗi PT_LOAD cấp bộ đệm `p_memsz`, `memset 0`, chép `p_filesz` byte từ file, `uc_mem_write` đúng `p_memsz` byte (0xd5fc4..0xd602c). Vậy bss được zero-fill theo memsz (kể cả phần vá +0x100: `vn_state` 0x1704c0 nằm trong trang đã ánh xạ 0x170000..0x171000 và bằng 0 lúc nạp). Không cần sửa thêm.
**Bản vá (patch/bridge_patches.tsv, sinh bởi `patch/mk_bridge_patches.py`, áp bằng `patch/apply_bridge.py`, diff `patch/bridge_patch_diff.txt`, thử `patch/test_bridge.py`):** (1) 0xc6c30 `b 0xc6d4c`, 0xc6dd8/0xc6e60 `mov w0,#1`; (2) 0xc5ce8..0xc5d34 W=scrW/k, H=scrH/k chẵn, k=min(4,scrH/240) giảm tới khi W>=400 && H>=240 (scrH<240: 400x240 như cũ); (3) viewport nguyên k căn giữa ở 0xe1e38 (viết lại) + 0xe1f84 (tail-call), chạm ở 0xcc6b8..0xcc700 (cùng công thức); (4) `glTexParameterf` MIN/MAG -> 9728.0 (GL_NEAREST) bằng cave tại callback hook đo đã chết 0xcfdd0 (GLES1 của bridge chỉ có glTexParameterf, không có `x`/`i`). Bản `armeabi-v7a/libhl5_arm64_bridge.so` là tệp khác (không giống từng byte): chưa vá, ngoài v0.1 (cài arm64).
