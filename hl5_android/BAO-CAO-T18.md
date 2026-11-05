# BAO-CAO-T18: nội suy khung hình lên 60 fps — T18a (nghiên cứu mã máy + đo harness)

Công nhân: Sonnet (A1), WORK `~/hl5work_t14c`. Chỉ đọc + dò, KHÔNG sửa guest/bridge/engine. Mã dò: `harness/t18/` (t18lib.py, p1, p3..p10). Số đo là byte khối cơ bản guest (≈ 4 B/lệnh ARM), harness Unicorn, bản gốc v0.2-hd, cảnh chơi sau khung 1300.

## KẾT LUẬN NGẮN
1. `mainPaint` gần như KHÔNG có tác dụng phụ: gọi thêm sau paint của tick (trước mainEndProcess) ra ảnh FB GIỐNG TỪNG ĐIỂM trong 120/120 tick (đi phải, đánh, đi trái), heap (số khối, top) không đổi; chỉ ghi bộ nhớ ở 3/120 tick, 1 byte (xem a-1). => **khả thi về ngữ nghĩa**.
2. **CHẶN CHÍNH: chi phí.** Paint chiếm **98-99%** CPU guest của một tick (mainProcess chỉ ~1,7%). Một khung trung gian (mainPaint đầy đủ) tốn xấp xỉ MỘT tick. Muốn 60 fps từ 22 fps cần ~2,7 lần số lần paint => cần CPU dư ≥ 2,7x (mục a-4). Bản thân người dùng đã báo lag ở 1/1..1/4; trên máy chậm, cơ chế thích nghi của thiết kế sẽ tự tắt (không có gì để vẽ thêm) => 60 fps chỉ có lợi nếu máy dư CPU. Cần số liệu thật từ máy người dùng (fps/ms mỗi tick) trước khi đầu tư T18b.
3. **Mốc 1 "chỉ camera" như thiết kế sẽ SAI nhìn thấy được**: hero (và mọi object) vẽ theo (toạ độ thế giới − camera); nếu chỉ lerp camera mà object ở toạ độ tick, hero trượt tương đối so với màn (răng cưa ~7 px/tick khi đi), trong khi nền mượt. Đo: đổi camera k px => cả ảnh (gồm hero) dịch k px (94% điểm khớp, 6% còn lại = hero/quái). Phải lerp cả hero (OBJECT+4/+8) ít nhất (mốc 1 = camera + hero, quái/hiệu ứng cùng mốc 2).

## a-1. Tác dụng phụ của mainPaint (bằng chứng harness `p3.py`, `p4.py`, `p5.py`, `p6.py`)
- Thứ tự một tick (vết `p1.py`, mọi khung giống nhau): TimerCb 0x99d98 -> mainKeyPressed (trả khác 0 khi chơi) -> mainStateMgr -> mainProcess -> [CurTime x4 trong process] -> **mainPaint -> mainEndProcess -> CUSTOM_grpFlushLcd** (đường 0x99f9c..0x99fac; nhánh trả 0 của mainKeyPressed bỏ cả paint/endproc). Process gọi vtable +0x3c, Paint +0x40, EndProc +0x44 của state trên cùng (gv+0x244).
- Paint thêm gọi NGAY SAU paint của tick (trước EndProc), 120 tick có đi/đánh: FB trùng 120/120; heap đếm khối/top không đổi; vùng ghi: chỉ stack + bộ đệm tạm do host memcpy/memset (calloc/free cân bằng 29/29).
- Paint thêm gọi SAU EndProc (ngoài tick) thì FB khác 118/120 tick: do mainEndProcess đổi bộ đếm khung hoạt hình (gv+0x130 tăng, SPRITE::GetLastFrame) => khung trung gian phải vẽ ở trạng thái SAU paint của tick và TRƯỚC EndProc, hoặc EndProc lưu/khôi phục gv+0x130.
- Tác dụng phụ thật tìm thấy: `Monster::Draw` gọi `Monster::IncreaseTempFrame` 0xb66c4 (tại 0xbee5c) => ghi 1 byte trường đếm khung của quái ở 3/120 tick (0x204b7210, giá trị 45..50). Quét tĩnh các hàm Draw/Paint gọi hàm kiểu Increase/Rand/Play: còn `HERO::IncreaseSpiritSkillMenuFrame` (Battle::Paint, menu tinh linh), `CHAR::IncreaseSpriteFrame` + `SoundMgr::PlaySound` (Battle::DrawSpiritCutIn), `StaticUtil::Rand` (StateGame::DrawExpert). => Phạm vi chỉ khi chơi thường (không cut-in tinh linh, không menu tinh linh/expert); `IncreaseTempFrame` phải bị chặn (HOOK_BL tại 0xbee5c, cờ nội suy) ở khung trung gian. Danh sách chưa phủ hết hiệu ứng/kỹ năng (kịch bản dò chỉ đi + đánh thường trong một map).
- Chưa kiểm: paint trong tick có chạm màn/popup/hoạt cảnh (đã loại khỏi phạm vi theo thiết kế).

## a-2. Camera và OBJECT (bằng chứng `p8.py`, `p9.py`, `p10.py`)
- `Map*` = r0 của Map::Paint 0xb129c (cảnh thử: 0x203a2790, một thể hiện).
- Camera X là nhóm trường int32 **Map+0xc0, +0xc8, +0xd0, +0xd8** (hero đi phải 7 px/tick: cả bốn +7/tick; +0xc4.. là Y, không đổi khi đi ngang). Chỉ đổi +0xc0 thì ảnh KHÔNG dịch (khớp d=0 98%); đổi cả 4 trường +k thì ảnh dịch đúng k px (khớp d=k 94%, k=3 và 6). Còn đổi +0xe0/+0xe8/+0xa8/+0xb0 thêm không đổi kết quả (chưa rõ vai trò, có thể là biên cuộn/tile). Y tương tự dự đoán (+0xc4,+0xcc,+0xd4,+0xdc) — CHƯA thử.
- Object vẽ theo đối số: `Monster::Draw / WARRIOR::Draw(this, gfx, -camX, -camY)` (đo: r2=-43,-50,-57.. đúng -Map+0xc0; r3=-593 = -camY) + toạ độ thế giới của object: `OBJECT::GetX` 0xcbc58 = [obj+4], `GetY` 0xcbc68 = [obj+8] (int32). Hero = đối tượng WARRIOR (cảnh thử 0x202c55e0), quái = Monster (0x205305b0, 0x204b6eb0, 0x205309e0, 0x20530e10, 0x20255800). Danh sách đối tượng duyệt trong Battle::Paint bằng ListNode::GetNextListNodePtr (chưa dò chi tiết, chưa dò hiệu ứng/đạn).
- Nội suy kiểu "đổi trường rồi khôi phục" khả thi cho camera + obj+4/+8: giá trị tick nằm trong bộ nhớ, chỉ cần ghi trước paint và trả lại sau.

## a-3. Bridge / nhịp (mã máy)
- Java GLSurfaceView vẽ liên tục theo vsync; mỗi khung GL: `nativeOnDrawFrame` (bridge) -> guest `nativeLoop` 0x149640 = `MIDASKernelManager::timerLoop()` 0x14d194 + `updateScreen()` 0x14cf40 (BAO-CAO-RE:236). Quad màn hình được vẽ lại MỖI khung GL, nhưng texture chỉ được tải lên (glTexSubImage2D từ fb+0x10 trong `MC_grpFlushLcd` 0x1488e0 -> `Texture2D::bindTextureFromDirect` 0x14e050) khi guest gọi flush. => muốn hiện khung trung gian chỉ cần guest gọi `CUSTOM_grpFlushLcd` 0x3d9d4 với FB mới; bridge không cần sửa.
- Timer WIPI: `CUSTOM_extSetTimer` 0x36100 lưu hạn = now + delay − 20 ms vào bảng 20 mục (stride 24 B); `timerLoop` kiểm hạn mỗi `nativeLoop` => độ phân giải = nhịp vsync (~16,7 ms), không phải 5 ms. TimerCb tự đặt lại với delay = max(5, 1000/fps − t_khung); fps = bảng 0x153460, tối đa 22 (45 ms). Đặt delay ≤ 16 ms thì TimerCb chạy ở vsync kế (60 Hz).
- Chưa đọc từ mã máy bridge (GL gọi qua con trỏ hàm, `nativeOnDrawFrame` 0xcb7e4 lớn): có khoá/cờ chặn vẽ lại nếu không có flush hay không; nghi không, vì khung GL luôn chạy nativeLoop.

## a-4. Chi phí (byte khối cơ bản / tick, `p7.py`, 15 tick, bản gốc; triệu)
| cỡ | pre (KeyPressed+StateMgr) | Process | **Paint** | EndProc | Flush(guest) | tổng | Paint % |
|---|---|---|---|---|---|---|---|
| 400x240 | 0,0006 | 0,65 | 37,45 | 0,03 | 0,003 | 38,1 | 98% |
| 600x270 | 0,0006 | 0,65 | 59,14 | 0,03 | 0,003 | 59,8 | 99% |
| 800x360 | 0,0006 | 0,65 | 100,95 | 0,03 | 0,003 | 101,6 | 99% |
(≈ 9,4 / 14,8 / 25,4 triệu lệnh/tick. Không tính thời gian host của stub flush/GL.) Số khung trung gian vẽ được trong một tick 45 ms = (45 ms − t_process) / t_paint − 1 (đã tính cả paint của tick) => với 60 fps cần t_paint <= 16,7 ms, tức tốc độ Unicorn trên máy >= 0,56 / 0,89 / 1,5 G lệnh/s ở 1/4, 1/3(600x270), 1/3(800x360). Tốc độ thật trên máy người dùng CHƯA biết (harness chạy ~0,1 s/khung 600x270 trên máy dựng, chậm hơn nhiều so với yêu cầu). => khả năng cao cơ chế thích nghi sẽ không vẽ khung trung gian nào ở 1/3 trừ máy rất mạnh.

## Vướng mắc / đề nghị cho quản đốc
- Cần 1 số đo từ máy người dùng: ms mỗi tick (hoặc fps thực) ở 1/3 và 1/4. Nếu tick thực > 45 ms (đang lag) thì T18b vô ích; nếu < 15 ms thì làm được.
- Nếu muốn có lợi trên máy vừa: phương án rẻ hơn khung trung gian đầy đủ = "dịch ảnh thế giới" (chụp FB thế giới ngay trước phần UI của Battle::Paint, khung trung gian = memmove dịch theo Δcamera + vẽ lại dải biên + hero + UI). Chưa khảo sát (phải chia lớp thế giới/UI trong Battle::Paint, hero phải vẽ lại ở vị trí cố định).
- Mốc 1 phải gồm hero (xem Kết luận 3).

## T18b / T18c: CHƯA LÀM
Lý do: kết quả a-4 (chi phí) + a-2 (mốc 1 sai) làm thiết kế cần quyết định lại; không viết interp.c khi chưa biết CPU dư trên máy thật.
**CÒN LẠI:** (1) quản đốc quyết: có đo máy thật không / đổi sang phương án dịch-ảnh / làm mốc "camera+hero" với paint đầy đủ có thích nghi; (2) dò Y camera (+0xc4,+0xcc,+0xd4,+0xdc), vai trò +0xe0/+0xe8/+0xa8/+0xb0, danh sách OBJECT/hiệu ứng/đạn trong Battle::Paint; (3) kiểm tác dụng phụ paint với kỹ năng/quái chết/rơi đồ/map khác; (4) T18b (hook TimerCb, interp.c, cờ interp.txt), T18c.
