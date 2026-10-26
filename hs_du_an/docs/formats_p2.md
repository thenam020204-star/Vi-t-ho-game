# Định dạng P2 (Hiên Viên Kiếm – Thiên Chi Ngân nhị chương)
Giống P1 (xem `formats_p1.md`) với các khác biệt:
- `map/N.m` (72 file): **cùng định dạng P1** (`u4 A, u4 B, u1 n, bảng, kịch bản…`). Parser kịch bản: `main/c.a(DataInputStream, c)`.
- `i.dat`: cùng định dạng P1 (`i4 n, n×(i2,i4), i4 len, blob`); bản ghi đọc bằng `b/a.b(DataInputStream)` (private static).
- Không có `map.dat`, `t2.dat`, `fb_skill.dat`, `exp.dat`. Có thêm `help.dat` (`i2 n, n×UTF`, loader `e/a/b.b`, game ghép các dòng bằng `\n`) và `lmagic.dat` (số liệu, không chuỗi).
- `t.dat`, `tips.dat`, `help.dat`: danh sách chuỗi `i2 n, n×UTF` (`e/a/b.b(String)`).
- `c.dat`: như P1 (`b/f.a`: `i2, UTF, UTF, UTF, bool, i2, u1, u1`); khoá `rN` ASCII giữ nguyên.
- `item15.dat`: bản ghi khác P1 (loader `b/a.e`): 16 chuỗi CJK quét được; game ghép thêm 19 chuỗi mô tả lúc chạy từ số + hằng trong .class.
- `monster.dat`: `i2 n, n×(i2 id, i4 off), i4 len, blob` như P1; bản ghi `b/c` (cùng trình tự đọc với `d/d` P1).
- `*.pak`: ảnh/khung hoạt ảnh (XOR 1), không text (chưa kiểm từng file như P1).

## Cách kiểm
- Kịch bản và `i.dat`: `python3 -I scripts/verify_scan.py $WS 2 logs/verify_p2.txt` -> mọi đoạn harness (parser của game) OK, `consumed == len`, 0 sai khác (xem `logs/verify_p2.txt`; mục DERIVED = chuỗi game tự ghép lúc chạy, ASCII = chuỗi không-CJK như `…`, `r61` khoá tài nguyên, KHÔNG dịch).
- Các file `.dat` còn lại: `python3 -I scripts/verify_files.py $WS 2` (gọi loader của game qua Harness chế độ LOAD): mọi chuỗi quét thấy đều được game nạp, không chuỗi nguyên văn nào bị quét sót.
- Round-trip + dịch giả: `python3 -I scripts/test_fake.py $WS 2` -> ĐẠT.
- Trích: `python3 -I scripts/extract.py $WS/x/2 p2/text_can_dich.tsv --part 2`; đóng gói: `repack.py <gốc> <tsv> <ra> --part 2`.
