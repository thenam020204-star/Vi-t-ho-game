# Báo cáo Việt hoá ảnh PNG (công nhân)

## pixeltext
- font.py: thêm cỡ `12` (f12.bin) vào SIZES/FONTS. `show --size 12` chạy; `check` mọi chuỗi đều không thiếu glyph/không trùng glyph.
- selftest.py: bỏ qua cỡ 12 ở phép thử "0/O phân biệt" (f12 có 0=O). Selftest còn 1 lỗi CÓ SẴN từ trước (không do thay đổi này): "15px chân chọn lọc" (kiểm tra 15c giữ chân).

## Ảnh đã làm (W/img/viet/..., ảnh so sánh ×3 ở W/img/compare/)
| Ảnh | Gốc -> Việt | Font |
|---|---|---|
| MMchar | 开始游戏 Bắt đầu / 继续游戏 Chơi tiếp / QQ游戏中心 QQ Game / QQ社区 Cộng đồng QQ / 设置 Cài đặt / 帮助 Trợ giúp / 关于 Giới thiệu / 退出 Thoát / 精彩推荐 Game hay | 18, thân trắng, viền cyan (dòng đỏ: viền đỏ) |
| tishiwenzi (13 nhãn) | 生命+ HP+, 怒气+ MP+, 经验+ EXP+, 金钱+ Xu+, 得到物品 Đồ mới, 得到装备 Trang bị, 红药不足 Hết đỏ, 蓝药不足 Hết xanh, 生命不足 HP cạn, 怒气不足 MP cạn, 接到 Nhận, 完成 Xong, 提交 Nộp | 12, giữ ký hiệu + : ! gốc |
| uitishi | 2468:Di chuyển 5/Trái:Chọn Phải:Quay lại | 12 |
| caidan / shop | Menu / Shop | 12 |
| diui | 商店 Shop (trái), 菜单 Menu (phải) | 12 |
| pause | Tạm dừng | 18 |
| yes / no | Có / Không | 15c |
| music | Bật âm nhạc? (18) / Tắt nhạc giúp game mượt hơn (12) | 18, 12 |
| battle1..5 | Thảo phạt Đổng Trác / Quan Độ giằng co / Hỏa thiêu Xích Bích / Phàn Thành chiến / Ải đặc biệt | 13c |
| fengmian | HÙNG BÁ / TAM QUỐC (đỏ, viền kem), Thiên Giáng Nữ Thần (cam, viền nâu đen) | 18 |

Chi tiết từng ô (box, màu, ghi chú): `anh_da_sua.tsv`. Script: `scripts/` (chạy lại bằng `scripts/run_all.sh`).

## Lưu ý / vấn đề
- Đã kiểm tra: kích thước và chế độ màu (P/RGBA, tRNS) giữ nguyên; số pixel đổi ngoài box = 0 ở cả 16 ảnh.
- battle2 thực là 官渡拉锯 (Quan Độ giằng co); battle4 thực là 樊城之战 (Phàn Thành chiến), không phải 攻城之战. Ảnh battle KHÔNG có khung nhãn nên không mở rộng khung; chữ dài hơn gốc nằm thẳng trên ảnh nền với viền 1px, căn trái tại x=10 như gốc.
- 怒气 (nộ khí, thanh thứ hai bơm bằng thuốc xanh) dịch thành MP theo quy ước chỉ số.
- Nhãn tishiwenzi bị chiều cao 13px nên chọn từ ngắn, không tổ hợp dấu cao + chấm dưới + đuôi (cao > 11px). "Hết xanh" và "Nhận" chạm sát ký hiệu bên phải (sát 0px).
- caidan/shop/diui/pause: gốc không có viền sáng riêng, màu viền = màu viền tối gốc (caidan 27293d, shop 5b1313, diui 25080c/313103, pause 0e4b4b). yes/no: thân trắng, viền xanh 1665ab (quầng sáng gốc là halo của chữ nên xoá hết về đen).
- Nhãn gốc là gradient; chữ mới 1 màu theo quy tắc (màu thân phổ biến nhất của gốc).
- fengmian: chữ Hán thư pháp phủ lên mặt nhân vật nên XOÁ KHÔNG HOÀN HẢO. Đã xoá sạch nét Hán (mask theo màu + Telea inpaint + làm mượt + dither bảng màu gốc); vùng giữa/trái/phải của chữ bị "nhoè màu" như tranh màu nước, không còn hình khuôn mặt bị phá (đã thử shiftmap/FSR nhưng chúng nhân bản khuôn mặt nên bỏ). Chữ 之 bị bỏ (không vẽ lại). Đề xuất: (a) thêm dải tối bán trong suốt sau tiêu đề, hoặc (b) hoạ sĩ vẽ lại vùng nền thủ công; chữ f18 mảnh nên khá nhỏ so với logo gốc (không phóng để giữ quy tắc).

## PNG nhúng (chưa sửa, chỉ trích ra W/img/embedded/, tổng 162 PNG: animation.bin 137, uires.bin 25)
Chữ Hán có trong:
- uires.bin: off 60706 (03, nhãn thuộc tính/ xác nhận), 76573 (05), 84345 (07), 95350 (11, tên tướng), 96468 (13), 97757 (17, 已有/兑换), 98480 (18, mua bán vật phẩm), 99549 (19, tiếp tục chiến), 100298 (20, kho đồ), 104649 (23), 105157 (24, 死亡复活).
- animation.bin: off 301320 (21, 击杀), 351240 (29, 连斩), 358141 (30, 暴击), 374879 (34, 升级), 404761 (52) và 414838 (53, dạng mặt nạ trắng của 52), 446653 (63, tên tướng).
Còn lại là hiệu ứng, sprite, số, không chứa chữ.
