# T11-tool: Tool_Viet_Hoa_NAM_P.zip thêm cỡ 12px

Gốc: `Tool_Viet_Hoa_NAM_P.zip` ở gốc repo (cấu trúc `NAM_P/`). Bản mới: `tool_main/Tool_Viet_Hoa_NAM_P.zip` (cùng cấu trúc, không có build_tmp/__pycache__). Mọi file `fonts/*.bin` giữ NGUYÊN từng byte so với zip gốc.

## Thêm cỡ 12px
- `java/BitmapFont.java`: `SIZE_12 = 12`; `SIZES = {18,15,13,12}`, `HAS_STYLES = {false,true,true,false}`; làm tròn >=17 -> 18, 14..16 -> 15, 13 -> 13, <=12 -> 12. SANS/SELECTIVE ở 12 dùng `f12.bin`. Loader đọc số glyph theo độ dài charset trong file (f12 = 228 ký tự), không hardcode 227. Comment đầu file cập nhật.
- `java/BitmapFont.class`: build lại bằng `sh java/build_class.sh` -> class version 47.
- `java/test/FontDemo.java`: thêm f12.
- `pixeltext_kit/pixeltext/font.py`: `SIZES` thêm 12 (`--size 12`); `cli.py` chú thích; `selftest_f12.py` mới.
- `README.md`: dòng f12 trong bảng, `BitmapFont.SIZE_12`, quy tắc làm tròn, `--size 12`.

## Theo quyết định người dùng (đồng bộ bộ kiểm với font đang dùng)
1. `check_all.sh` bước 1: danh sách chuẩn 8 file (có `f12.bin`). Kiểm chặt như cũ cho f18/f15/f13/f15s/f13s/f15c (charset đúng bằng f18, 227 ký tự). Ngoại lệ có chú thích trong script: `f13c` = charset f18 + đúng `=~` ở cuối (người dùng giữ), `f12` = 228 ký tự riêng (chỉ được thiếu `'` `…` `‚` so với f18).
2. `fontgen/hand_tuned.py` (mới) + `build_fonts.py`: nạp glyph chỉnh tay của f15c/f13c (e , ; ‚ á ạ ặ ậ é è ẻ ẽ ẹ ê ế ề ể ễ ệ ị ọ ộ ợ ụ ự ...) và `=~` cho f13c qua `OVERRIDES`/`EXTRA_CHARS`. `build_fonts.py` nay sinh lại ĐÚNG từng byte cả 6 file f15/f13 (+s,+c) (bước 1b so cmp không nới).
3. `selftest.py`: các phép thử cũ giữ nguyên; riêng "chân chọn lọc" đổi từ "chỉ I i j l J 1 4 khác bản không chân" thành đúng tập glyph khác của font hiện có (`HAND_DIFF` theo cỡ, so bằng) và vẫn bắt buộc I i j l J 1 4 (+dấu) giữ chân; chỉ so ký tự chung (f13c có thêm `=~`). Vòng kiểm 0/O bỏ f12 (f12 có `0`/`O` trùng glyph, chỉ dùng chữ số), f12 kiểm riêng ở `selftest_f12.py` (bước 7b).
4. `0`/`O` trùng glyph ở f12 và f12 có `= _ ~` nhưng thiếu `'` (PLAN ghi ngược) — ghi nhận, không sửa.

## Kết quả `sh check_all.sh`: tất cả pass
1 font .bin (8 file) OK; 1b sinh lại f15/f13/+s/+c byte-exact OK; 2 selftest OK; 3 examples OK; 4 BitmapFont.class version 47 OK; 5 FontDemo (gồm f12) OK; 7b selftest_f12 OK; 6 freej2me OK.
