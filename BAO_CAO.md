# Báo cáo Việt hoá — C&C: Red Alert Mobile (J2ME, EA 2009, build 4.15.48 reeu.sek800i)

## Kết quả
- `out/cnc_vi.jar` + `out/cnc_vi.jad` (MIDlet-Jar-Size khớp). MIDP-2.0 / CLDC-1.1, 240×320.
- Toàn bộ 489 chuỗi (465 chuỗi duy nhất) đã dịch tay, phong cách **chiến tranh hiện đại**, **chữ thường** (gốc viết HOA toàn bộ).
- Font **17px**: nén từ `f18.bin` (có chân) bằng đúng thuật toán `fontgen/build_fonts.py`, bố cục 5|3|6|3 hàng
  (dấu | trên-x | x-height | dưới), khớp dòng 17px của font gốc (ascent 14 + descent 3). Viền đen 1px như font gốc.
- Màn splash "Việt hoá bởi NAMP" ~2 giây khi mở game (bấm phím bất kỳ để bỏ qua).

## Đã sửa gì (binary diff, `logs/binary_diff.txt`)
| Mục | Thay đổi |
|---|---|
| 33 class gốc | **không đổi byte nào** |
| `NampMIDlet.class`, `NampMIDlet$Splash.class` | mới: `extends com.ea.sdk.SDKMIDlet`, chỉ override `startApp()` để hiện splash rồi gọi `super.startApp()`. ecj `-source/-target 1.3`, class 47, có `StackMap`, không `StackMapTable` |
| `META-INF/MANIFEST.MF` | `MIDlet-1` → `NampMIDlet`; thêm `Force-Locale: vi` |
| `t_pt` → `t_vi` | slot ngôn ngữ 0 (pt) thành `vi`, codepage 0 = UTF-16BE (engine EA hỗ trợ sẵn) |
| `df0_0` + `index` | resource #16 (bảng ngôn ngữ: tên, codepage, độ dài chunk), #9/#10/#11 (font chunk), #0/#4/#7 (atlas font). Offset index tính lại |
| `namp.png` | logo splash (240×320, 35 KB) |
Các ngôn ngữ cs/pl/tr/ru vẫn còn và vẫn hiển thị (glyph Cyrillic/ký tự riêng của font cũ được giữ trong atlas mới).

## Kiểm tra
- Round-trip trùng từng byte trước khi patch: bảng ngôn ngữ, 5 file `t_*`, kho `df*`/`index`.
- `dp check`: **OK 465/465**. Không thiếu glyph (đo bằng bảng ký tự font mới).
- `jar_check.py`: **RESULT: PASS** (`logs/jar_check.txt`). Cảnh báo duy nhất: `Image.createImage(String)` — API MIDP 1.0, có trên mọi máy.
- FreeJ2ME (`out/shots/`, `out/tong_hop_man_hinh.png`): splash → Bật âm thanh → tiêu đề → menu chính → Chơi mới → chọn chương → briefing → trong trận → Tạm dừng → Tùy chọn. Đã soát thêm: Trợ giúp, Thông tin (™/© đúng), Game khác, Phần thưởng/Thành tích, hộp Thoát. Không có exception của game.
  Lỗi `Sequencer ... is null` là do môi trường headless không có MIDI, không phải lỗi game.
- `rebuild.sh` chạy lại từ jar gốc ra đúng jar cuối (đã chạy).

## Debug / lưu ý kỹ thuật
- Engine chữ là EA SDK (`o.class`): chuỗi là đối tượng `e` (1 byte + `/charmap`, hoặc UTF-16 khi codepage 0); font chunk = danh sách Unicode sắp tăng + rect cho từng glyph trên ảnh PNG 8-bit. Đo/xuống dòng/canh giữa của engine dùng advance thật từng glyph → không cần sửa code wrap; xuống dòng đã theo dấu cách.
- Bảng màu font: game ghi đè chunk `PLTE` của PNG (bản màu #1,2,3,5,6,8) → atlas mới giữ nguyên PLTE 256 màu + tRNS, index 0 = thân chữ, 1 = viền, 2 = trong suốt.
- Font gốc dùng `+` làm ™ và `{` làm © → giữ glyph cũ cho 2 mã này.
- Glyph có nét chạm cột cách (ơ ư Ơ Ư đ…) được advance +1 để viền chữ sau không đè lên nét.
- `scripts/run_emu.py` (bản sửa của skill): bản `freej2me_plus.jar` này cần thêm một tham số trước width/height (nếu không màn hình thành 320×1); phím X11 không vào được cửa sổ khi Xvfb không có WM → thêm lệnh `ekey` dùng file `/str/FreeJ2MEExternalKeyEvents.txt` của FreeJ2ME (`k0-9 ka kb ku kd kl kr kc ls rs`).
- Splash: FreeJ2ME không tự `paint` khi `setCurrent` → canvas `repaint()` mỗi 100 ms trong 2 s.
- `dp` xếp `SELEC.`/`SEG.` vào "không dịch" (viết tắt) → điền tay `Chọn`/`Tiếp` trong `out/text_can_dich.tsv`.

## Bộ nhớ (heap)
- Atlas font 256×126 thay cho 253×74 (+~13.5K pixel mỗi ảnh). Game tạo tối đa 6 biến thể màu → thêm ~160 KB (16bpp) đến ~320 KB (32bpp) nếu cả 6 cùng nạp.
- Buffer text lớn nhất (chunk 20) 11.3 KB thay vì 7 KB (UTF-16). Logo splash được giải phóng trước khi vào game.
- ⚠️ Chưa thử trên máy thật / KEmulator / MicroEmulator (không có trong môi trường). Nên thử trên máy heap nhỏ (SE K800 ~ 1.5 MB+).

## Tồn đọng
- Bách khoa và Giao chiến bị khoá trong save mới nên chưa chụp được bằng emulator; đã đo tĩnh độ rộng px: mọi chuỗi ngắn ≤ ~+17 px so với bản gốc dài nhất trong 5 ngôn ngữ (đã rút gọn "Tăng Rhino", "Dịch chuyển đi", "Alpha và Omega").
- Ảnh có chữ: logo "COMMAND & CONQUER RED ALERT MOBILE" là tên game → giữ nguyên.

## Kiểm tra bộ tool (`tools/check_all.sh`, chạy trên bản giải nén nguyên trạng)
- OK: 3 pixeltext examples, 4 BitmapFont.class = version 47, 5 FontDemo, 6 freej2me jar nguyên vẹn.
- FAIL (có sẵn trong bộ tool, dự án không sửa tool):
  - `1 font .bin hop le (7 file)`: thư mục fonts có 8 file (thêm `f12.bin`) trong khi script chờ 7.
  - `1b f15/f13 = sinh lai tu f18`: `No module named 'pixeltext'` — script tìm `pixeltext_kit/`, `setup_tools.sh` giải nén thành `pixeltext-kit/`.
  - `2 pixeltext selftest`: kiểm tra glyph `f15c` (chân chọn lọc). Dự án không dùng f15c.
