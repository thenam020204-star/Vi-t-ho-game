# T11-tool: Tool_Viet_Hoa_NAM_P.zip thêm cỡ 12px

Gốc: `Tool_Viet_Hoa_NAM_P.zip` ở gốc repo (cấu trúc `NAM_P/`). Bản mới: `tool_main/Tool_Viet_Hoa_NAM_P.zip` (cùng cấu trúc, không có build_tmp/__pycache__).

## Đã đổi
- `NAM_P/java/BitmapFont.java`: thêm `SIZE_12 = 12`; `SIZES = {18,15,13,12}`, `HAS_STYLES = {false,true,true,false}`; `slot()` làm tròn: >=17 -> 18, 14..16 -> 15, 13 -> 13, <=12 -> 12. SANS/SELECTIVE ở 12 rơi về `f12.bin` (chỉ một kiểu). Loader đã đọc số glyph theo `readUTF().length()` của file, không hardcode 227 (f12.bin có 228 ký tự). Cập nhật comment đầu file.
- `NAM_P/java/BitmapFont.class`: build lại bằng `sh java/build_class.sh` -> class version 47, 10805 byte (ecj 1.3 + ProGuard microedition).
- `java/test/FontDemo.java`: thêm f12 (canvas cao 640).
- `pixeltext_kit/pixeltext/font.py` (SIZES thêm 12), `cli.py` (chú thích), `selftest.py` (như gốc, chỉ loại f12 khỏi vòng lặp 0/O cũ; phần kiểm f12 nằm ở `selftest_f12.py` mới).
- `check_all.sh`: GIỮ NGUYÊN các phép thử cũ như zip gốc; chỉ THÊM bước 7 (kiểm `f12.bin`: cao 12, 228 ký tự, chỉ thiếu `'` `…` `‚` so với f18, round-trip) và 7b (`pixeltext_kit/selftest_f12.py`), và đổi nhãn bước 5 (FontDemo đã gồm f12).
- `README.md`: dòng f12 trong bảng, cách dùng `BitmapFont.SIZE_12`, quy tắc làm tròn, `--size 12`.

## Kết quả `sh check_all.sh` (không nới phép thử nào)
| Bước | Kết quả | Ghi chú |
|---|---|---|
| 1 font .bin hợp lệ (7 file) | FAIL | FAIL từ zip gốc: thư mục `fonts/` có thêm `f12.bin` nên danh sách 7 file không khớp; ngoài ra `f13c.bin` có 229 ký tự (thừa `=` `~`) khác charset 227 của f18 |
| 1b f15/f13 sinh lại từ f18 | FAIL | FAIL từ zip gốc: `f15c/f13c` bị chỉnh tay (glyph `e`, dấu `,` ...) lệch với `fontgen/sans18.py` |
| 2 pixeltext selftest | FAIL | FAIL từ zip gốc: "chân chọn lọc chỉ I i j l J 1 4 giữ chân" thất bại vì f15c/f13c chỉnh tay (khác thêm `e á ạ ... ,`) |
| 3 examples | OK | |
| 4 BitmapFont.class version 47 | OK | |
| 5 FontDemo (gồm f12) | OK | f12 dựng đúng, loader đọc 228 ký tự |
| 7 f12.bin | OK | mới |
| 7b selftest_f12 | OK | mới (`--size 12`, phủ chữ Việt, round-trip, I/l/1 phân biệt) |
| 6 freej2me | OK | |

Bước 1, 1b, 2 đã FAIL trên zip gốc trước khi sửa gì (kiểm lại bằng cách giải nén zip gốc và chạy `check_all.sh`); KHÔNG do thay đổi 12px. Phần 12px (BitmapFont SIZE_12, pixeltext `--size 12`, FontDemo, bước 7/7b) đều pass.

Ghi chú khác: `f12.bin` có `= _ \` ~` nhưng thiếu `'` (PLAN ghi ngược); `0` và `O` trùng glyph ở f12 (chỉ dùng chữ số). Muốn check_all xanh hoàn toàn cần người dùng quyết: đồng bộ `sans18.py` với f15c/f13c chỉnh tay, đưa f12 vào danh sách 8 file, đưa f13c về 227 ký tự (hoặc nới charset) trong phép thử.
