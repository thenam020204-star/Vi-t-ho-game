# BAO-CAO-T25 (quản đốc tự làm, 06/11): nội suy cả hoạt cảnh + mọi đối tượng; logic trùng tuyệt đối
## Tác dụng phụ của Paint trong hoạt cảnh (harness/t25/pdiff.py, wtrace.py: gọi thêm mainPaint, diff bộ nhớ bỏ FB/FBOff/stack, hook ghi)
- `Map::MapEffect` 0xa8b60 ghi Map+0x14c (fade, str 0xa8bd0/0xa8e6c/0xa9184/0xa948c); `Map::RepaintMapEffect` ghi Map+0x160 (strb 0xb0f74).
- `CHAR::CharSprEffect` 0x48344 ghi CHAR+0xe4/+0xe1 (gọi từ Draw của WARRIOR/ROGUE/GUNNER/KNIGHT/Monster/Npc/Spirit).
- `ParticleMgr::paintParticles` 0xcda70 ghi +0xf8/+0x100; `Monster::IncreaseTempFrame` (T18 đã chặn).
- Còn lại: bảng màu/clip tạm (tính lại mỗi lần vẽ).
## Camera sự kiện (mã máy Map::MapMove 0xafc54; BAO-CAO-T23 ghi ngược nghĩa 0x686)
[EP+0x75]==0: bám hero (0xb0198). Sự kiện: [EP+0x686]==0 → đích ([EP+0x68c],[EP+0x690]) (0xaffb8); ==1 & [EP+0x688]==255 → đích = hero (0xb0814); khác → đối tượng sự kiện. Tâm = [Map+0xd0]+W/2 dịch tối đa s16 [Map+184]/lần (MapMove chép 184→186; có trạng thái cuộn Map+0xa8..0xb4).
## Cách làm (engine/menu/interp.c)
- Mỗi tick (Paint THẬT chạy nguyên bản): ghi vị trí mọi đối tượng trong 5 danh sách vẽ (LinkedList* gv+5536..5552, node next=[+4] obj=[+8], OBJECT x,y [+4]/[+8]) + tâm camera; |Δ|>96 → không nội suy đối tượng đó.
- Khung trễ (alpha 0, ngay sau Paint thật) và khung trung gian: chụp struct Map (4284 B, new ở 0xdf944) + EventProc (1788 B, 0xdf96c) + điểm ảnh FBOff bằng host memcpy; đặt vị trí nội suy; camera: chơi thường = MapMove (bám hero), sự kiện = đặt tạm 686=0, đích = tâm nội suy, bước 255 rồi MapMove; mainPaint với I.mid=1 (chặn IncreaseTempFrame, giữ ParticleMgr qua HOOK_BL 0x45804, ghi CHAR+0xe0..e7 qua HOOK_B 0x48344 để trả lại); rồi trả MỌI thứ (vị trí, Map, EP, FBOff, CHAR). Bss thêm ~2,4 MB (bộ đệm FBOff tối đa 2,4 MB, >2,4 MB thì không nội suy).
- Bản đầu (đưa camera về bằng MapMove lần 2) làm lệch Map+0xa8..0xba từ tick 899 → bỏ, thay bằng chụp/trả.
## Kiểm harness/t25/lock_interp.py (bước 16 ms = vsync, phím theo SỐ TICK, so trạng thái mỗi tick thật: EventProc 1788 B, Map camera/fade/0x160, vị trí mọi đối tượng, hero HP)
| 400x240 | tick | khác | tick sự kiện | khung trung gian |
|---|---|---|---|---|
| bật vs tắt | 1870 | **0** | 137 (881–1049) | 851 (37 tick sự kiện có chế độ nội suy) |
CÒN: chưa so ảnh khung trung gian trong sự kiện (vị trí camera nội suy) bằng mắt; chưa chạy 600/800/1200; Paint 2 lần/tick khi nội suy (thích nghi CPU tự tắt khi không đủ thời gian).
