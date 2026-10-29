# PROGRESS — Tinh Vân Truyền Thuyết (Việt hoá NAMP)

Đọc README_DU_AN.md + PLAN.md trước. Trạng thái: **xong, đã phát hành** (main:releases/TinhVanTruyenThuyet/).

- Dựng lại: `./rebuild.sh` (3 bản). Sửa dịch: `cd dich && python3 ../scripts/dp.py fix` rồi `scripts/merge.sh`, `./build.sh <bản>`.
- Thêm ô chữ ảnh: thêm dòng vào `docs/module_chu.tsv` (hoặc `_320`/`_360`), build lại.
- Emulator: phím số = NumPad (bật Num_Lock rồi KP_0..9), q/w = phím mềm, Return chỉ ở menu chính; bản ngang dùng `--size 320x240` / `640x360`.
- Kiểm: `logs/patch_<bản>.log` toàn OK, `logs/jar_check_<bản>.txt` PASS, `scripts/qa.py` OK, `tools/check_all.sh` OK.
- Tồn: soát thêm màn sâu (cửa hàng/rèn/túi) trên máy thật hoặc emulator khi có save.
