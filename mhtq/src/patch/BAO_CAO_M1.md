# Mốc 1 — Hook BitmapFont f13c (MHTQ K790)

**Đây là bản thử nghiệm font, KHÔNG phải bản dịch.** Chỉ ~12 chuỗi được điền tạm tiếng Việt có dấu (hộp hỏi nhạc "Bật nhạc nền không?", Có/Không, trang Trợ giúp, một câu thoại + tên "Kiếm Tiên"/"Ma Thiên"). Mọi chữ Hán chưa dịch sẽ KHÔNG hiện (f13c không có glyph Hán) — đúng như thiết kế, T05/T06 sẽ dịch hết.

## Cách hook (không đổi tên/descriptor/access flag của member có sẵn)
- `f` (đo chữ): thay thân `a(String)I`, `a(C)I`, `a()I`, `b()I` để gọi `ViFont.sw/cw/h` (rộng chuỗi/ký tự theo f13c, cao dòng 13, lệch dòng 0).
- `u` (vẽ chữ): 2 lệnh `invokevirtual Graphics.drawChar/drawString` (u.a(CIII)V @185, u.a(String,III)V @185) đổi thành `invokestatic ViFont.dc/ds` (cùng vị trí, cùng stack).
- Thêm `ViFont.class` (ecj -source 1.3 -target 1.3; `src/patch/ViFont.java`), `BitmapFont.class`, `font/f13c.bin`. Preverify (ProGuard -microedition) chỉ trên f, u, ViFont; 25 class còn lại giữ nguyên từng byte.
- jar_check: PASS (Thêm: BitmapFont, ViFont, font/f13c.bin; Đổi: f, u + class/dat có chuỗi thử). Chạy lại: `bash mhtq/scripts/build_m1.sh`.

## Ghi chú heap BitmapFont f13c
- Mỗi màu chữ dùng một atlas ARGB cả bảng (f13c: ~63 KB/màu); dựng lần đầu cần thêm `int[]` tạm cùng cỡ => đỉnh ~126 KB. Cache LRU `CACHE = 4` màu => ổn định tối đa ~252 KB (thêm ~126 KB đỉnh khi dựng màu thứ 5 trở đi).
- Game dùng nhiều màu chữ (vàng 0xFFFFDA, nâu 0x452319, trắng, xanh #00FF00, 9272667, 16770338...). Nếu một màn dùng >4 màu cùng lúc, LRU sẽ dựng lại atlas mỗi khung (chậm, tạo rác). Nếu máy thật giật: (1) nâng `CACHE` lên 6 trong `BitmapFont.java` rồi `sh build_class.sh` nếu heap dư, hoặc (2) giảm số màu chữ. Nếu thiếu heap (<~400 KB trống): hạ `CACHE` xuống 2 hoặc đổi f13s (nhẹ tương đương, không giảm).
- Nếu cấp atlas thất bại (OOM), BitmapFont tự rơi về vẽ từng glyph bằng `drawRGB` (cấp phát `int[]` mỗi ký tự) — coi là dấu hiệu phải giảm màu/cache.
- `ViFont.ds` gọi `BitmapFont.drawString` (không cấp phát với chuỗi không chứa `\n`); `u.a(String,..,int[])` (chữ viền) vẫn vẽ 9+1 lần, 2 màu => tối đa 2 atlas cho một dòng viền.
- Chưa đo heap thực trên K790/E62 (không có máy); FreeJ2ME không báo heap.

## Việc còn lại cho Mốc 2 (T06)
- Hàm ngắt dòng `k.b`/`j.a` vẫn ngắt theo từng ký tự (ảnh `04_thoai_2.png` có "gia/n"), khung thoại còn 2 dòng/trang.
- Tên người nói > 4 ký tự dùng hộp 91px; chữ có dấu rộng hơn nên cần kiểm sau khi dịch.

## Lưu ý công cụ
- `scripts/run_emu.py` là bản sửa của `$SK/scripts/run_emu.py`: freej2me_plus hiện tại nhận `jar <cờ> <rộng> <cao> <scale>`; bản gốc truyền `jar 240 320 1` nên game bị đặt 320x1 và ảnh chụp chỉ có một vạch. Bản sửa cũng chạy emulator trong `work/emu_cwd` (xoá sạch mỗi lần chạy: game lưu lựa chọn nhạc vào RMS nên lần sau sẽ bỏ qua màn hỏi nhạc) để config/rms không rơi vào repo.
