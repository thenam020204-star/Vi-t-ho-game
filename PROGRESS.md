# PROGRESS — Việt hoá 雄霸三国之天降女神 (N73)

Quyết định: Hán Việt nhẹ · 怒气=MP · font game 12px (ghép f12 vào bin/font.bin) · ảnh f12 / f15c / f18, 1 màu + viền 1px · giữ logo bìa + quảng cáo.

- [x] Kiểm kê, decompile, đường đi chữ
- [x] A. 16 PNG rời + 15 PNG nhúng (uires/animation)
- [x] B. Font 12px + bs viết lại
- [x] C. Trích/dịch 625 dòng resource + 433 chuỗi .class (kiem OK, placeholder kỹ năng OK)
- [x] D. vh: ngắt dòng px, khung thoại 3 dòng, chữ chạy, drawString→bitmap; uiform bảng chọn ải
- [x] jar_check PASS, FreeJ2ME các màn chính, rebuild tái lập (SHA-1 ổn định)
- [ ] Soát: menu trong trận / shop / kho / kỹ năng (chưa đi tới bằng kịch bản phím); máy thật / KEmulator

Phiên sau: đọc BAO_CAO.md mục "Còn tồn"; sửa bản dịch ở text/*.tsv → `bash rebuild.sh` → `text/kiem.py` + `scripts/text/check_skill_ph.py`.
