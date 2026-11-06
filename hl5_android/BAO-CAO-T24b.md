# BAO-CAO-T24b (quản đốc tự làm, 06/11): đường nhanh cho 3 hàm vẽ GỐC (BAO-CAO-T23 P2, P6, P7)
Cơ chế mới `HOOK_B(vaddr, từ_lệnh_gốc, hàm)` trong inject_menu.py: thay lệnh đầu hàm (`push {r4-r11,lr}` 0xe92d4ff0) bằng `b hàm`; hàm asm gọi bản C, trả 0 thì chạy lại `push` gốc rồi nhảy lệnh thứ 2 (đường gốc nguyên vẹn).
| Hàm | Ngữ nghĩa (mã máy) | Đường nhanh | Đường gốc giữ cho |
|---|---|---|---|
| MC_grpFillRect 0x1456a4 | xem đầu engine/menu/gfxfast.c | hàng đầu vòng ngắn + host memcpy các hàng | alpha ≤ 254, callback, độ sâu khác |
| Graphic::FillRectAlpha 0x4e684 | R=(inv·r8+a·R)&0xF800, G=((inv·g8+a·G)>>5)&0x7E0, B=((inv·b8+a·B)&0xFFFF)>>11 | 3 bảng kênh 32/64/32 dựng mỗi lần gọi; alpha 255 → tô đặc + memcpy | Graphic[0x5c] ≠ 1 (32 bit) |
| Graphic::ChangeFrameBufferBlending565 0x500e8 | bảng byte this+0x6d theo kênh, trộn a7/a8 | 3 bảng 32 mục; mỗi điểm 3 lần tra | — |
Kiểm `harness/t24b/lock.py` + `cmp.py`: md5 ảnh TỪNG khung theo ROUTE 1400 khung (khởi động → tiêu đề → chọn lớp → hoạt cảnh có màn đen FillRectAlpha + tint CFBB565), base = HEAD không T24b:
| Cỡ | khung khác | khởi động | tiêu đề | chọn lớp | hoạt cảnh | cả lộ trình |
|---|---|---|---|---|---|---|
| 400x240 | 0/1400 | ×3,33 | ×1,69 | ×1,00 | ×1,27 | ×1,50 |
| 800x360 | 0/1400 | ×4,81 | ×2,44 | ×1,00 | ×1,33 | ×1,90 |
(`shots/T24b/lock_*.txt`; byte-khối/khung). Chọn lớp không đổi vì chi phí nằm ở menu mới (T24a). Không cấp phát; bảng static 0,4 KB.
CÒN: 1200x540 chưa chạy lock; 32 bit chưa có đường nhanh (không gặp trên máy).
