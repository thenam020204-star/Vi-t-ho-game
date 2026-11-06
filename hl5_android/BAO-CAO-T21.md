# BAO-CAO-T21/T22 (quản đốc tự làm, 06/11/2026)

## T22: bình máu mua ở cash shop không uống / không gán phím tắt được
- Nguyên nhân: `t7_cash_netconnect` (bản offline của cash shop) cấp vật phẩm **loại 18** với id = rec[+8] (id hiển thị của cash, item_18 #34 "Elixir"). Chỉ **loại 11** mới uống/gán phím được: `HERO::BattleUseItem` 0x8bda0 cứng loại 11 (`DeleteBagItemCategoryIndex(11,id)` 0x8be80, `GetItemTableInfo(11,id)` 0x8bea8).
- Bằng chứng máy chủ cấp loại 11: `StateCashShop::Process` 0xd9304..0xd9404: mua rec[8]==31 (Magic Stone loại 18) thì đếm (11,15) trong túi (giới hạn 2). Quét so.dis: không có mã nào dùng (18,34)/(18,30); các id 18 khác có mã dùng trực tiếp (23 Bùa hồi sinh, 24 tự nhặt, 26..29/35/36 Expert, 39..42 đổi lớp, 8..10 tôi luyện/Orb).
- Sửa `engine/menu/t7.c`: 18/34 → 11/8 (Elixir 2500 HP), 18/30 → 11/14 (Protection Charm), 18/31 → 11/15 (Magic Stone). Tên + mô tả trùng giữa item_18 và item_11.
- Kiểm `harness/t22/check_t22.py` 600x270: mua ô 0 → (11,8) 0→20, (18,34) giữ 0; `BattleUseItem(8)` trả 1, còn 19, đặt hồi máu (hero+0x2fe=90, +0x300=250, +0x302=1) và hồi chiêu thuốc 100 như thuốc thường. Save cũ: Elixir loại 18 đã mua giữ nguyên (người dùng: không cần đổi).

## T21: camera luôn đặt hero giữa + map đệm + vòng sáng dưới chân (`engine/menu/camc.c`)
Bằng chứng (so.dis + `harness/t21/p1.py`):
- Map* = [gv+5200]; camera trái/trên = s32 [Map+0xd0]/[Map+0xd4] (Map::MapMove 0xafc54 ghi 0xafe6c/0xafe80); kích thước map px s16 [Map+112]/[Map+114]; hero x,y = s32 [HERO+4]/[HERO+8].
- Giữa map hero_x − cam_x = W/2, hero_y − cam_y = H/2 đúng (không trễ). Mép trái 600x270: cam_x = 0, hero lệch 72 thay vì 300. 1200x540: map 1088 < màn 1200 → game đặt cam_x = −56, tô đen phần ngoài map.
- `Battle::Paint` 0x4512c: `Map::Paint` (bl 0x451c4; chép FBOff→FB cả màn mỗi khung, BAO-CAO-T20) → vật thể/lớp map → hạt → `HERO::RestoreColorBack` (bl 0x4581c) = hết phần vẽ thế giới; sau đó là UI. Touch của StateGame không đọc camera (quét các hàm Touch*).
Cách làm (KHÔNG đổi logic/camera game):
- `HOOK_BL(0x4581c, 0x910c8, camc_world_done)`: khi camera kẹp mép (cam ≤ 0 hoặc ≥ map − màn) dịch cả khung (host memcpy theo hàng; hàng tạm static khi dịch ngang) để hero về giữa; mọi điểm ngoài biên map (toạ độ thế giới) vẽ nền tối #080C10 + đom đóm (lưới thế giới 32 px, 1/3 ô có, trôi + nhấp nháy theo bộ đếm khung gv+0x130, chỉ đọc). Bỏ qua khi hội thoại/hoạt cảnh ([EventProc+0x75]). Giữa map dx=dy=0 → không ghi gì.
- `HOOK_BL(0x451c4, 0xb129c, camc_map_paint)`: sau nền map vẽ elip 15x6 vàng ấm (viền mềm + lõi mờ, nhịp chậm) tại chân hero, trước vật thể/lớp map → vật đứng trước che đúng.
- Cờ `camc_on`, `ring_on` (mặc định 1). Không cấp phát; không ghi biến game.
Kiểm `harness/t21/check_t21.py` (fork cùng trạng thái; `shots/T21/check_*.json`):
| Cỡ | A giữa map ảnh trùng từng byte (camc bật/tắt) | B mép trái hero về giữa | D logic (hero/camera/HP sau 90 tick LEFT, 120 tick UP) trùng bật/tắt |
|---|---|---|---|
| 400x240 | ĐẠT | ĐẠT (dx 128) | ĐẠT |
| 600x270 | ĐẠT | ĐẠT (dx 228) | ĐẠT |
| 1200x540 | không áp dụng (map hẹp hơn màn → luôn kẹp, dx 215) | ĐẠT (dx 472) | ĐẠT |
Ảnh: `shots/T21/` (mép trái gốc/mới, 1200x540, phóng vòng sáng).
**Chế độ 1 (mặc định theo người dùng 06/11): vẽ lại CHÍNH MAP không vật thể** — phản chiếu nền map qua mép, tối 25%. Nguồn = FBOff (bộ đệm vòng nền; vật thể vẽ vào FB). Ánh xạ theo `Map::Update` 0xb10bc: ox = [Map+0xd8] mod W, oy = [Map+0xdc] mod H, rung s16 [Map+282]/[Map+284]; điểm màn (sx,sy) = FBOff[(sx−r282+ox) mod W][(sy−r284+oy) mod H]. Kiểm lại A/B/D 3 cỡ ĐẠT (`shots/T21/check_phan_chieu_*.json`, ảnh `phan_chieu_*`). `camc_mode` 0 = nền tối đom đóm (giữ làm phương án).
CÒN: chưa có mục bật/tắt trong Cài đặt; chưa đo CPU ở mép (tô nền bằng memcpy hàng, đom đóm vài chục điểm); chưa thử cùng T18 (interp.txt bật) trên máy thật.
