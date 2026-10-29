# PLAN — Tinh Vân Truyền Thuyết (NAMP)

## Quyết định (QĐ)
1. Văn phong hiện đại (người dùng chọn). Font f12 cho 240/320 (người dùng chọn). Bản 360: f15c vì font gốc bản này 16px/cách dòng 20 (quản đốc chọn, đổi được ở `build.sh`: FONT/LSP).
2. Phương án hook: thay **thân** các hàm chữ của renderer (giữ chữ ký) -> `src/VH`; `Graphics.drawString` -> `VH.drawString`.
3. Text: `data/e*.data` (readUTF list + mảng short), D-file `iteminfo/skillinfo/missioninfo0,1/mapinfo/universeinfo`, hằng chuỗi class. Repack ánh xạ theo nội dung (goc -> dich) -> 3 bản dùng chung 1 bản dịch, chuỗi trùng dịch đồng nhất.
4. Chỉ thay `CONSTANT_String`; Utf8 dùng làm tên field (bản 360: `GameData.星际神殿`…) giữ nguyên, tách Utf8 mới khi dùng chung.
5. Mã điều khiển: `$n` màu, `|` xuống dòng, `：` tách tên người nói — kiểm bằng `repack.py --check` + `scripts/qa.py`.
6. Bộ đếm chạy chữ của game là `byte` -> VH đếm theo đơn vị 3 ký tự (tránh tràn khi câu Việt dài).
7. Ảnh chữ dạng atlas (module trong `*.data`): hook `drawRegion` module -> vẽ chữ f12/f15c viền đen ở tâm ô (`docs/module_chu*.tsv`). Ảnh còn lại vẽ lại bằng pixeltext (`make_img.py`).
8. Bố cục: tên quái lên hàng trên (không đè "Lv"); tên người nói căn trái + viền trắng; bản 320 lời dẫn ngắt 300px; tên hành tinh rút gọn.
9. Khởi động: thân splash -> logo NAMP 2 giây, âm lượng 4 (= chọn "Có"), vào game; xoá ảnh logo cũ không còn tham chiếu.

## Task
- [x] T1 kiểm kê, extract/repack byte-exact (3 bản)
- [x] T2 dịch tay 2034 chuỗi (dp), QA OK
- [x] T3 VH + Patch + build 240, jar_check PASS, FreeJ2ME
- [x] T4 ảnh/atlas chữ, khởi động NAMP, ẩn quảng cáo
- [x] T5 bản 320 (hằng số bố cục riêng, 4 ô chữ thêm, ảnh ngang)
- [x] T6 bản 360 (cấu hình class tên rõ, font f15c, ~130 ô chữ ui/42-43, ảnh 640x360)
- [x] T7 đóng gói, rebuild.sh, phát hành

## Vướng mắc / tồn
- Chưa đi hết các màn sâu trong game bằng emulator (cửa hàng, rèn, túi đồ… mở sau phần hướng dẫn dài); bố cục chữ ở các màn này kiểm bằng phân tích code + hook chung, có thể còn chỗ chữ dài tràn ô.
- `dp check` báo 710 dòng "còn ký tự rộng" — đều là dấu `：` cố ý giữ (QĐ 5); `qa.py` = OK.
