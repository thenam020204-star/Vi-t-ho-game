# BAO-CAO-L3: T17c7 MainMenu cảm ứng mới (công nhân Sonnet, WORK ~/hl5work_l3)

Trạng thái: **XONG, kiểm ĐẠT** (trừ ghi chú cycles cash ở mục Hồi quy). Dev .so: `~/hl5work_l3/l3/dev/dev.so` (dựng bằng `WORK=~/hl5work_l3 bash harness/l3/build_dev.sh`; không đụng so_patches.tsv / build_apk).

## Đã làm
- Bỏ comment dòng L3: `states.def` (mm4, mm5a, mm5b, mm6, mm9, mmpop), `hooks.def` (`HOOK_BL 0x11fc00 mm_navkey`). `classes.def` MAIN đã bật sẵn; khối L3 trong `build.sh`/`mui.h` đã có.
- Mã `engine/menu/mmenu.c` (WIP cũ) giữ nguyên, chỉ sửa 1 chỗ: danh sách Cài đặt giãn hàng cho đầy cửa sổ (tối đa +12 px/hàng) và kéo dọc dùng đúng bước hàng (`g_orh`).
- Phạm vi: state 4 lưới 6 ô 3x2 (ô gốc {0,1,4,5,6,8}; bỏ Vào kho/Cập nhật/Hỗ trợ), state 5 chọn slot + chọn lớp 2x2 + popup mô tả lớp (loại 8, OK/Huỷ), state 6 Chuyên Gia (chọn slot), state 9 Cài đặt 10 mục thật (Nhạc nền, Âm hiệu ứng, Tốc độ game, Rung, Hiệu ứng, Tự ngắm, Xem mũ, Bàn cảm ứng, Quản lý dữ liệu, + Phân giải 1/1..1/4 theo T10, ghi chú khởi động lại), popup thoát 2 nút. Chạm = ghi con trỏ + gửi phím gốc; KeyPressed/Process/SetPopupState gốc. Không cấp phát trong paint (mảng static).
- Sửa harness: `check_mm.py` (kịch bản hits chạm nhầm 2 lần), `qlib.py` (thêm `mimg`/mặt nạ Skip cho check_cash), `shots_mm.py` mới (chụp ảnh).

## Kết quả kiểm (3 cỡ 400x240 / 600x270 / 1200x540)
| Mục | Kết quả |
|---|---|
| new (chạm) vs orig (phím, v0.2-hd): grid 6 ô, slot+lớp+popup+OK, huỷ popup, Back (slot/lớp/Cài đặt/Chuyên Gia), Chuyên Gia, 10 mục Cài đặt (gv + file hd_scale.txt), cycles 20 lần mở/đóng (live 0/top 0 == gốc) | ĐẠT cả 3 cỡ (`shots/L3/check_mm.txt`) |
| off (cờ MUI_F_DISABLE) vs orig, cùng kịch bản | ĐẠT 21/21 |
| Điều hướng phím lưới 6 ô (nav) | ĐẠT (chuỗi ô hợp lệ, vòng 3x2) |
| Vùng chạm == `ui_new/menu/hit_rects.json` (6 ô, close, slot2, 4 lớp, OK, popup OK/Huỷ/mô tả; Cài đặt close) | ĐẠT; 7 hàng/chip Cài đặt không so được với mockup (mockup 7 mục, thật 10) |
| Hồi quy c1 (`shots/L3/hoi_quy_c1.txt`, `shots/L3/hoi_quy/`) | ĐẠT, 0 LỖI |
| Hồi quy cash: sel, drag, popup, mua (NET=0), vùng chạm | ĐẠT 3 cỡ |
| Hồi quy cash `cycles` | live/top/cls == gốc, CHỈ hash ảnh cuối khác (new vs orig) ở cả 3 cỡ: lệch đã biết do HUD mới/hook T16 (PLAN mục 1 cuối), không phải MainMenu |
| t16 | không chạy |

Ảnh (10) ở `shots/L3/`: lưới 6 ô (400, 1200), slot (400), lớp + popup (1200, 400), Cài đặt (400, 600, 1200 hàng Phân giải), popup thoát (600).

## Lệch / ghi chú
- Mục Cài đặt hàng thứ 7 hiển thị = hàng 5 + cuộn (cửa sổ gốc 6 hàng: con trỏ 0..5); kéo dọc = phím LÊN/XUỐNG.
- Chuyên Gia state 6 sub 0 dùng chung màn chọn slot ("Chọn Slot"); các state khác của Chuyên Gia là của L1.
- Dev .so dựng từ cây chung hiện tại (đã gồm dòng MAP của L1b; không ảnh hưởng L3).

## CÒN LẠI
- Soát mắt 600x270 các màn slot/lớp (mới soát 400 và 1200); hit-test chip Cài đặt chưa đối chiếu mockup (khác số mục).
- Gộp vào dist chưa làm (không chạy inject_menu --update-repo/make_apk theo lệnh); cần quản đốc nghiệm thu rồi mới đóng APK.
- Cycles cash: tìm nguồn lệch hash ảnh (ngoài L3).
