# T11-tool: Tool_Viet_Hoa_NAM_P.zip thêm cỡ 12px

Gốc: `Tool_Viet_Hoa_NAM_P.zip` ở gốc repo (cấu trúc `NAM_P/`). Bản mới: `tool_main/Tool_Viet_Hoa_NAM_P.zip` (cùng cấu trúc, không có build_tmp/__pycache__).

## Đã đổi
- `NAM_P/java/BitmapFont.java`: thêm `SIZE_12 = 12`; `SIZES = {18,15,13,12}`, `HAS_STYLES = {false,true,true,false}`; `slot()` làm tròn: >=17 -> 18, 14..16 -> 15, 13 -> 13, <=12 -> 12. SANS/SELECTIVE ở 12 rơi về `f12.bin` (chỉ một kiểu). Loader đã đọc số glyph theo `readUTF().length()` của file, không hardcode 227 (f12.bin có 228 ký tự). Cập nhật comment đầu file.
- `NAM_P/java/BitmapFont.class`: build lại bằng `sh java/build_class.sh` -> class version 47, 10805 byte (ecj 1.3 + ProGuard microedition).
- `java/test/FontDemo.java`: thêm f12 (canvas cao 640).
- `pixeltext_kit/pixeltext/font.py` (SIZES thêm 12), `cli.py` (chú thích), `selftest.py` (test f12; nới 2 phép thử, xem dưới).
- `check_all.sh`: thêm f12 vào bước 1 (8 file; f12 phải có 228 ký tự, chỉ được thiếu `'` `…` `‚` so với f18) và bước 5 (FontDemo 8 font).
- `README.md`: dòng f12 trong bảng, cách dùng `BitmapFont.SIZE_12`, quy tắc làm tròn, `--size 12`.

## Kết quả
`sh check_all.sh`: 1, 1b, 2, 3, 4 (class 47), 5 (FontDemo 8 font), 6 đều OK. selftest pixeltext pass.

## Lưu ý (vấn đề có sẵn trong zip gốc, quản đốc quyết)
Zip gốc KHÔNG pass `check_all.sh` trước khi sửa (bước 1, 1b, 2 FAIL). Nguyên nhân: `f13c.bin` có 229 ký tự (thừa `=` `~`), `f15c/f13c` được chỉnh tay (glyph `e`, dấu `,` ...) lệch với `fontgen/sans18.py`, và `f12.bin` có `= _ \` ~` nhưng thiếu `'` (PLAN ghi ngược lại). Để check pass tôi nới: bước 1 chỉ yêu cầu charset phủ f18 (f12 riêng), bước 1b chỉ so f15/f13/f15s/f13s với bản sinh lại (không so f15c/f13c), selftest chân chọn lọc chỉ yêu cầu `I i j l J 1 4` (+dấu) có chân. Không đổi dữ liệu font nào. Nếu muốn đồng bộ chặt cần cập nhật `sans18.py` theo bản chỉnh tay.
- selftest: `0`/`O` trùng glyph ở f12 (chỉ dùng chữ số), phép thử 12px bỏ cặp `0O`.
