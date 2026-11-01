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

## 7. Việc tiếp theo
1. Viết công cụ ghép lại VFS (pack) + cập nhật `target-assets.json` + ký lại APK. Thử APK không đổi gì chạy được (khớp hash).
2. Dịch ngược hook chữ tiếng Anh của bridge (tìm bảng chuỗi và hàm hook theo symbol `BFont::*`).
3. Harness headless: chạy guest `.so` bằng Unicorn (Python) + giả GLES/JNI để chụp khung hình và test.
4. Việt hoá dữ liệu, rồi hook BFont, rồi HD, rồi cash shop.
