# Định dạng P5 (Hiên Viên Kiếm – Thiên Chi Ngân chung chương)
Như P4 với tên class khác: kịch bản `a/d/b.a`, danh sách đối tượng `a/b/a.a`, `i.dat` bản ghi `b/a.a`, loader chuỗi `a/c/b.c`. `.m2`: 44 file.
- `lk_tips.dat` (giống P3) không có class nào nạp -> bỏ qua. `payInfo.ini`, `snsInfo.ini`: cấu hình thanh toán/mạng xã hội, ngoài phạm vi.

## Cách kiểm
- Kịch bản và `i.dat`: `python3 -I scripts/verify_scan.py $WS 5 logs/verify_p5.txt` -> mọi đoạn harness (parser của game) OK, `consumed == len`, 0 sai khác (xem `logs/verify_p5.txt`; mục DERIVED = chuỗi game tự ghép lúc chạy, ASCII = chuỗi không-CJK như `…`, `r61` khoá tài nguyên, KHÔNG dịch).
- Các file `.dat` còn lại: `python3 -I scripts/verify_files.py $WS 5` (gọi loader của game qua Harness chế độ LOAD): mọi chuỗi quét thấy đều được game nạp, không chuỗi nguyên văn nào bị quét sót.
- Round-trip + dịch giả: `python3 -I scripts/test_fake.py $WS 5` -> ĐẠT.
- Trích: `python3 -I scripts/extract.py $WS/x/5 p5/text_can_dich.tsv --part 5`; đóng gói: `repack.py <gốc> <tsv> <ra> --part 5`.
