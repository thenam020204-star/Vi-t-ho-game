# This War of Mine (Android) — Việt hoá

Toàn bộ text và font của game nằm trong `common.dat` + `common.idx` (trong APK không có file font).

## Định dạng đã giải mã
- `common.idx`: header 11 byte, sau đó 329 mục × 17 byte `u32 hash, u32 packed, u32 unpacked, u32 offset, u8 flag`. Không có tên file, chỉ có hash.
- `common.dat`: các khối gzip nối liền nhau (deflate kết thúc bằng sync-flush, không có trailer CRC).
- Khối `2f6496cc` (localization): key ASCII + 7990 bản ghi, mỗi bản ghi 14 slot UTF-16
  (pl, **en**, de, fr, it, es, ja, zh, ru, —, ko, zh, pt, tr) + 4 chuỗi ASCII ghi chú + byte cờ. Xem `tools/locfmt.py`.
- 182 khối font bitmap (magic `e3 0a f2 23`): các trang atlas rộng 1024 × H (1 hoặc 2 byte/pixel) + bảng glyph 16 byte.
  Xem `tools/fontfmt.py`.

## Cách làm
- Text tiếng Việt ghi đè **slot tiếng Anh**: để máy ở ngôn ngữ English là game hiện tiếng Việt.
- Font: `tools/fontpatch_vi.py` ghép thêm 134 glyph tiếng Việt cho 137 font Latin từ chính nét chữ gốc
  (chữ cơ sở + dấu lấy từ à á â ã ğ / ` ´ ^ ~ / ? / . / ’). Glyph cũ giữ nguyên vị trí;
  hết chỗ thì nới chiều cao trang atlas lên luỹ thừa 2 tiếp theo.
- Dịch: quy trình dich-pro (`text/PLAN.md`, `text/dp_work/`).

## Build
```
sh build.sh <thư mục có common.idx + common.dat gốc> <thư mục ra>
```
Script tự vá font, nhập `text/dp_work/final/t1.tsv` + `t2.tsv`, đóng gói, rồi `tools/verify.py` kiểm:
khối không đổi phải trùng byte với gốc, font/text phải parse và round-trip được, và font phải đủ glyph tiếng Việt.
