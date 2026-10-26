# Mốc 1 — Font 12px + ảnh rời đã Việt hoá

## Đã sửa
- `bin/font.bin`: ghép glyph f12 (thay 94 ASCII, thêm 134 chữ Việt) → 7179 glyph; `bin/fw.bin` (mới): advance từng glyph.
- `bs.class`: renderer đo/vẽ theo advance thật, ký tự thiếu không vẽ bừa, buffer dùng lại, viền có lề 1px, `readFully`.
- 16 PNG rời: MMchar, tishiwenzi, uitishi, caidan, shop, diui, pause, yes, no, music, battle1-5 (chi tiết `assets/img/anh_da_sua.tsv`).
- Giữ nguyên: fengmian (logo thư pháp, theo quyết định người dùng), logo_1, logo_2, wonder1/2.

## Kiểm tra
- `jar_check.py game.jar moc1.jar`: **RESULT: PASS** (chỉ đổi bs.class, font.bin, 16 PNG; thêm fw.bin). WARN: `DataInputStream.readFully([B)V` — có trong CLDC 1.0.
- bs.class: class 47, có StackMap, không StackMapTable; mọi member gốc giữ nguyên descriptor + access flag (kể cả `<clinit>` 0x0009).
- FreeJ2ME (240×320): boot → hỏi nhạc → menu → lưu game → chọn ải → vào trận → hội thoại; không exception mới (lỗi `XMessage` class not found có cả ở jar gốc — SDK QQ dò class).

## Còn tồn
- Text resource/.class chưa dịch (mốc 2). Khung thoại 2 dòng, wrapper 12/14px cố định (giai đoạn D).
- PNG nhúng trong `uires.bin`/`animation.bin` (存档界面, 确定, 返回, 战役选择, 击杀…) đang làm.
- tishiwenzi: "Hết xanh", "Nhận" sát ký hiệu bên phải (0px).
