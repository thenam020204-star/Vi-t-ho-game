# Định dạng P3 (Hiên Viên Kiếm – Thiên Chi Ngân tam chương)
Khác P1/P2:
- **`map/N.m2`** (86 file; kèm `.png`, `.pl` ảnh/va chạm, không text): `UTF tên bản đồ`, `u1 cờ`, rồi **khối như `.m` của P1** (`u4 A, u4 B, u1 n, bảng (id,len), kịch bản…`, A tính từ sau 2 số u4), rồi phần bản đồ (`u1 s, u1 r, u1 q, u1 số tileset; mỗi tileset: UTF, u1, i2, u1, UTF, i8, u1 k, k×2 byte; s·r·q byte ô`), rồi **danh sách đối tượng/NPC** (`i2 n`, n× `a/a.a(DataInputStream)`: `i2, UTF tên, UTF khoá, …`).
  Chuỗi cần dịch: tên bản đồ (offset 0), chuỗi trong kịch bản, tên đối tượng/NPC. Khi đổi độ dài: cập nhật `len` từng kịch bản và `A`; B và phần bản đồ giữ nguyên (tiền tố tên đổi độ dài thì mọi thứ phía sau dịch đúng bằng đó).
  Ngoại lệ: `map/186.m2`, `map/188.m2` không có tiền tố tên (bắt đầu thẳng bằng `u4 A`); parser tự nhận dạng.
- Parser kịch bản: `b/f/b.a(DataInputStream, b)`; danh sách đối tượng: `b/c/c.a(DataInputStream)`; `i.dat` bản ghi: `a/f.b(DataInputStream)`.
- `t.dat`, `tips.dat`, `lk_tips.dat`, `help.dat`: `i2 n, n×UTF` (`b/e/a.c`; `help.dat` qua `b/e/a.d` ghép bằng `\n`). `lk_tips.dat` do `d/a` nạp.
- `c.dat`: nhân vật `a/g.a`: như P1 + thêm 1 byte cuối. `item15.dat`: 16 chuỗi CJK (+16 ghép lúc chạy). `monster.dat`: như P1, bản ghi `a/d`.
- `payInfo.ini` (UTF-8, cấu hình thanh toán/SMS có vài chữ Hán như tên xu, hotline): NGOÀI phạm vi (chỉ dịch nếu hiển thị được và đã hỏi người dùng).

## Cách kiểm
- Kịch bản và `i.dat`: `python3 -I scripts/verify_scan.py $WS 3 logs/verify_p3.txt` -> mọi đoạn harness (parser của game) OK, `consumed == len`, 0 sai khác (xem `logs/verify_p3.txt`; mục DERIVED = chuỗi game tự ghép lúc chạy, ASCII = chuỗi không-CJK như `…`, `r61` khoá tài nguyên, KHÔNG dịch).
- Các file `.dat` còn lại: `python3 -I scripts/verify_files.py $WS 3` (gọi loader của game qua Harness chế độ LOAD): mọi chuỗi quét thấy đều được game nạp, không chuỗi nguyên văn nào bị quét sót.
- Round-trip + dịch giả: `python3 -I scripts/test_fake.py $WS 3` -> ĐẠT.
- Trích: `python3 -I scripts/extract.py $WS/x/3 p3/text_can_dich.tsv --part 3`; đóng gói: `repack.py <gốc> <tsv> <ra> --part 3`.
