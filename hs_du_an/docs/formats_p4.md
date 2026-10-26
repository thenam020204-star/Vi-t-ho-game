# Định dạng P4 (Hiên Viên Kiếm – Thiên Chi Ngân tứ chương)
Như P3 (`.m2`: tên bản đồ + khối kịch bản + phần bản đồ + danh sách đối tượng; parser kịch bản `b/f/b.a`, danh sách đối tượng `b/c/c.a`) với khác biệt:
- `i.dat`: bản ghi đọc bằng `a/f.a(DataInputStream)`. **`ii.dat` không có class nào nạp** (bản cũ của `i.dat`) -> bỏ qua.
- `tips.dat` nạp bằng `b/e/a.d` (chọn ngẫu nhiên 1 chuỗi) cùng định dạng `i2 n, n×UTF`; `t.dat`, `help.dat` như P3.
- `lk_tips.dat` giống hệt P3 nhưng không có class nào nạp -> bỏ qua.
- `payInfo.ini` có BOM UTF-8; ngoài phạm vi.

## Cách kiểm
- Kịch bản và `i.dat`: `python3 -I scripts/verify_scan.py $WS 4 logs/verify_p4.txt` -> mọi đoạn harness (parser của game) OK, `consumed == len`, 0 sai khác (xem `logs/verify_p4.txt`; mục DERIVED = chuỗi game tự ghép lúc chạy, ASCII = chuỗi không-CJK như `…`, `r61` khoá tài nguyên, KHÔNG dịch).
- Các file `.dat` còn lại: `python3 -I scripts/verify_files.py $WS 4` (gọi loader của game qua Harness chế độ LOAD): mọi chuỗi quét thấy đều được game nạp, không chuỗi nguyên văn nào bị quét sót.
- Round-trip + dịch giả: `python3 -I scripts/test_fake.py $WS 4` -> ĐẠT.
- Trích: `python3 -I scripts/extract.py $WS/x/4 p4/text_can_dich.tsv --part 4`; đóng gói: `repack.py <gốc> <tsv> <ra> --part 4`.
