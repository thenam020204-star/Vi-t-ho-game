# PROGRESS — Dị Thế Tà Quân (异世邪君) 240x320 — Việt hoá NAM.P

## Quyết định
- Phong cách: theo tiểu thuyết Dị Thế Tà Quân (Phong Lăng Thiên Hạ), Hán Việt cổ trang. vai.md + thuat-ngu.tsv trong dich/dp_work/.
- Font: f12.bin (12px, bộ tool NAM.P) — vẽ bằng class hook VH (hook/VH.java), atlas 3 màu cố định + fillRect cho màu khác.
- Logo khởi động: logo NAMP (res_vi/p0). Logo game: ảnh bìa Việt (assets/cover_vi.webp) -> res_vi/splashAnim (scripts/make_splash.py).
- Gỡ: hỏi âm thanh (auto bật), máy quay số CMCC/Sohu + init SDK, hỏi kết nối mạng, menu 游戏圈/精品推荐/更多游戏, link thoát game, upload điểm.

## Đã xong
- Patch bytecode (patcher/Patch.java, ASM, kiểm số lần khớp): renderer, ngắt dòng px, hội thoại 3 dòng, boot flow, menu, chuỗi trong c.class.
- Dịch string.pak: 2756/2756 dòng unique, dp check OK. dich/text_da_dich.tsv -> res_vi/string.pak (scripts/repack.py, round-trip byte-identical đã kiểm).
- build.sh: patch -> stage -> preverify -> jar_check PASS. FreeJ2ME: boot, menu, cảnh mở đầu, hội thoại OK.

## Việc tiếp
1. Vẽ lại ảnh có chữ: nhãn menu splashAnim (y>=320), png7 (atlas UI), png43, png55, png64 (bản đồ), png74, LOAD.
2. Đi sâu gameplay: menu IGM, cửa hàng, trang bị, kỹ năng, trận đánh -> soát tràn chữ.
3. read->readFully trong c(DataInputStream) (an toàn KVM).
4. rebuild.sh, BAO_CAO.md, đóng gói du_an zip, PR.
