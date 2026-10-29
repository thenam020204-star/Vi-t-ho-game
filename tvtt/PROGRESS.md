# PROGRESS — Tinh Vân Truyền Thuyết (星际传说-命运轮回) Việt hoá

Quyết định (người dùng chốt): văn phong **hiện đại**; font **f12**; khởi động như dự án trước (logo NAMP, bỏ hỏi nhạc = nhạc bật, ẩn quảng cáo/web).
3 bản: 240x320 (làm trước), 320 (class obfuscate giống 240, khác hằng số), 360x640 (class tên rõ: GameCanvas…, cần cấu hình patch riêng).

## Kiến trúc
- Text: `data/e*.data` (m.b: short n + n×readUTF + mảng short), D-file `iteminfo/skillinfo/missioninfo0,1/mapinfo/universeinfo` (m.d: rows, cols, readUTF), chuỗi trong class.
- Renderer gốc: `src/b` vẽ hzk12 bằng drawRGB + gradient; `$n` = mã màu, `|` = xuống dòng; thoại "Tên：nội dung" (v.a tách ở '：', equals tên → chân dung).
- Hook `hook/VH.java` (package src): thay thân 9 hàm chữ của `b` + 11 drawString; chạy chữ theo đơn vị 3 ký tự (bộ đếm byte).
- Khởi động: thân `q.run/paint/a()` → logo NAMP 2s, âm lượng q.a=4, startGame(1).

## Trạng thái
- [x] extract (5685 hàng, 2038 chuỗi khác nhau) / repack selftest byte-exact cả 3 bản
- [x] VH + Patch + build.sh 240: jar_check PASS, FreeJ2ME chạy (logs/shots_t2)
- [x] Dịch tay 2034/2034 (dp), QA OK (scripts/qa.py, scripts/merge.sh); tên hành tinh rút gọn cho khung bản đồ
- [x] Hook ô chữ atlas: VH.mod (thay thân b.a(Image,…,short[],int)) + VH.reg (m.a(String) nạp ảnh) + font/vhm.dat từ docs/module_chu.tsv
- [x] Ảnh vẽ lại (scripts/make_img.py -> res_vi_240): menu/1 (tiêu đề), menu/2 (mục menu), map/122-126, head/play, logo/exit (nền NAMP)
- [x] Ẩn "Thêm game" (o.<init> bỏ mục 8), xoá ảnh logo cũ (scripts/post_stage.sh)
- Phím FreeJ2ME: bật Num_Lock rồi KP_0..KP_9; q/w = phím mềm; Return chỉ ở menu chính
- [ ] Soát màn trong game (cửa hàng, túi, nhiệm vụ…) — [ ] ui/35.png (phím vẽ dọc) — [ ] bản 320, 360 — [ ] đóng gói
