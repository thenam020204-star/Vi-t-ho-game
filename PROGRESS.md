# PROGRESS — Việt hoá 雄霸三国之天降女神 (N73)

Quyết định: Hán Việt nhẹ · font game 12px (ghép f12 vào bin/font.bin) · ảnh: f12 ô nhỏ, f15c/f18 ô lớn, 1 màu + viền 1px · giữ nguyên logo/quảng cáo.
Kế hoạch: /root/.claude/plans/streamed-snacking-forest.md (A ảnh → B font → C text → D code → test).

- [x] Kiểm kê, decompile CFR, khảo sát đường đi chữ
- [ ] A. Ảnh (công nhân Sonnet đang làm)
- [x] B. Font 12px: scripts/build_font12.py (thay 94 ASCII, thêm 134 chữ Việt → 7179 glyph, bin/fw.bin advance), src_patch/bs.java (advance thật, buffer dùng lại, viền có lề 1px, readFully); preverify riêng bs + set_clinit_flags → jar_check PASS; FreeJ2ME: chữ Việt mẫu ở Trợ giúp hiển thị đúng
- [ ] C. Trích/dịch text resource + class
- [ ] D. Patch wrapper/dialogue/drawString
- [ ] Test FreeJ2ME, jar_check, bàn giao

Ghi chú môi trường: freej2me_plus nhận tham số `jar [fullscreen] w h [scale]` → scripts/run_emu.py (bản sửa) truyền thêm '0'. Còn tồn ở D: wrapper z.a để dấu cách đầu dòng.
