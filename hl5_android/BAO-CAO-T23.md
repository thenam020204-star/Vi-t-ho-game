# BAO-CAO-T23: điều tra hiệu năng toàn game (chỉ đo + phân tích + đề xuất; KHÔNG sửa mã game/engine)

Dev .so: `WORK=~/hl5work_t23 DEVDIR=~/hl5work_t23/dev bash harness/l1/build_dev.sh` (v0.2-hd + engine/menu hiện tại: T14c HUD, T16 hộp thoại, T18 nội suy, T20, T21). Mọi số là **byte-khối/khung** (tổng kích thước khối cơ bản Unicorn đã chạy; ≈4 B/lệnh ARM, chia 4 = lệnh) do `UC_HOOK_BLOCK`; M = triệu. Chi phí thật trên điện thoại ~ tỉ lệ với số này (T18: Unicorn 0,05-0,3 G lệnh/s; 1 tick = 45 ms ở 22 fps => ngân sách ~2,2-13 M lệnh = 9-54 M byte-khối ở 100% CPU chỉ cho game).

## 0. Phương pháp và công cụ (`harness/t23/`)
- `t23lib.py` `Prof`: quy địa chỉ khối về hàm bằng nhãn `so.dis` (3268 hàm) + `menu.sym` (đoạn menu 0x171000+, tiền tố `M:`; LƯU Ý có 2 hàm `blit` tĩnh: 0x18adfc = mmenu.c (menu chính), 0x199ff8 = dialog.c (chân dung) cùng tên trong báo cáo). Exclusive theo địa chỉ khối; inclusive + bảng caller theo ngăn xếp bóng (đẩy ở khối đầu hàm + LR, bật khi khối trùng LR); host call (stub PLT) đếm theo tên + byte `memcpy/memset`. `Timeline` chạy kịch bản theo khung, cửa sổ chi tiết `Prof` (30 khung, tiêu đề/chọn lớp 20) xen khung nhẹ `Light`. **Inclusive của `nativeLoop`/`nativeKeyHandleEvent` không đáng tin** (điểm vào JNI do harness gọi lồng; bỏ qua).
- Kịch bản: `scen.py` (tiêu đề + chọn lớp, cũng đo cutscene 1200x540), `scen_cut.py` (hoạt cảnh mở đầu + hội thoại, nhấn OK mỗi 40 khung, cửa sổ ở khung 895/1000/1200/1500), `scen_play.py` (chơi thường đứng/đi/đánh bằng OK, 7 menu trong game bằng `S3(n)`, mép map LEFT 90 tick). Đầu ra JSON `~/hl5work_t23/out/prof*_WxH.json`; `gen_tables.py f.json` (bảng), `show.py f.json nhãn [K]` (top exclusive/inclusive).
- Chi tiết: `ex1.py` (nhịp timer + trạng thái từng khung), `ex2.py` (từng khung 893..900: hàm nặng nhất), `ex3.py` (vị trí hero/camera/NPC từng tick trong hoạt cảnh), `ex5.py` (tham số FillRectAlpha/ChangeFrameBufferBlending565 từng lần gọi). "Sau khi sửa" làm PHÍA HARNESS (không đụng mã game): `emu.py` (hook đầu hàm, ghi bộ nhớ bằng host rồi PC=LR, chỉ đường nhanh tương đương byte-từng-byte), `exp1.py`.
- Lệnh tái hiện: `cd hl5_android; python3 harness/t23/scen.py 800 360` (~25 phút, FAST=1 bỏ cửa sổ tiêu đề), `scen_cut.py 800 360`, `scen_play.py 800 360`, `scen_play.py 1200 540`, `python3 harness/t23/exp1.py 800 360`. Boot 60 s + route ~890 khung; chạy nền song song được (4 lõi).

## 1. Tổng hợp theo kịch bản (B/khung, trung bình cửa sổ 30 khung)
| cảnh | 800x360 | 1200x540 | ghi chú |
|---|---|---|---|
| **tiêu đề** (StateMainMenu::DrawLogo) | **70,1 M** | **155,8 M** | 98% là 1 lần `MC_grpFillRect` toàn màn (68,6 M @800) |
| chọn lớp a (DrawLogo) | 58,9 M | - | FillRect 68% + DrawImageBlendingClip565 x10 |
| menu chính lưới (`mm_grid_paint`, select_b) | 56,0 M | - | `M:blit` (nền menu mới) 48%, BoxBlur565 1 lần 11,4 M ở KeyTitle |
| menu chính ô (`mm_slot_paint`, select_c) | 39,1 M | - | `M:bg` 22,1 M (blit nền) + `dimwin` 36 M (span_a 11,6 M) |
| hoạt cảnh: bắt đầu (khung 895-924) | 43,3 M (tick đỉnh 126-130 M) | 87,3 M (đỉnh 282 M) | tint toàn màn 25 M/khung + nạp map |
| hoạt cảnh: hiệu ứng Npc::DrawEffect | 23,4 M | 24,3 M | DrawImageStretchClip565 70% |
| hoạt cảnh: hội thoại CÓ chân dung | **18,8 M** (khung có hộp) | **67,2 M** | chân dung = 63% / 84% |
| hoạt cảnh: hội thoại không chân dung / chờ | 6,4 M (2,1-4,2 khi không hộp) | 15,0 M (5,6-10 khi không hộp) | span_a hộp 28% |
| chơi thường đứng / đi P / đi T / đánh | 10,5 / 10,6 / 11,2 / 11,2 M | 16,7 / 16,3 / 16,1 / 17,4 M | Process chỉ 0,55-0,92 M (5-8%), **Paint ~94%** |
| menu trong game (menu3x3, Túi, Trang bị, Kỹ năng, Nhiệm vụ, Hệ thống, Nhân vật) | 16,0-20,8 M | 33,8-42,3 M | dim toàn màn + span: 56-77% span_a, 17-24% span |
| mép map (T21 đen trơn) | 10,6 M | 16,7 M | = chơi thường (không thêm chi phí) |

Chi tiết top-6 hàm exclusive từng cảnh: `python3 -I harness/t23/gen_tables.py ~/hl5work_t23/out/prof_800x360.json profcut_800x360.json profplay_800x360.json prof_1200x540.json profplay_1200x540.json`.

### 1a. Chơi thường (800x360 10,5 M; 1200x540 16,7 M)
| hàm | 800x360 | 1200x540 | ghi chú |
|---|---|---|---|
| `M:art` 0x18d954 (hud.c: nút HUD từ bộ đệm `ac_t`) | 3,68 M = **35%** | 8,5 M = **51%** | 13 nút/khung, ~283 k B/nút ≈ 45 B/điểm (vòng guest từng điểm có alpha) |
| `Graphic::DrawImageBlendingClip565` 0x56... | 3,64 M = 35% | 24% | 43 lần/khung: ô map có blending (`Map::DrawTileBlending` 46 lần, MapLayerDraw 1,98 M = 19%), Minimap 0,97 M = 9%, HUD gốc `Battle::DrawGameUI` 3,0 M |
| `MC_grpFillRect` 0x1456a4 | 0,42 M (4%) 27 lần | 2-3% | |
| `Graphic::DrawImageMirrorClip565` | 4% | 3% | sprite lật |
| `M:draw_dpad`, `M:hud_belt`, `M:draw_btn` (HUD mới) | 3% + 2,5% + 2,4% | 4%+... | |
| `Formula::calcByFormula` 0x... (187 lần/khung qua `Formula::calc`) | 2,3% (0,42 M) | ~1% | tính lại công thức mỗi khung (HUD/skill belt) |
HUD mới + HUD gốc cộng lại chiếm **~45%** tick chơi thường ở 800x360, **~60%** ở 1200x540 (`hud_paint` toàn phần 9,9 M gồm `StateGame::Paint` 5,6 M).

## 2. Cutscene lag: so với chơi thường, nguyên nhân (số đo)
**Nhịp không đổi:** `TimerCb` 0x99d98 chạy đúng 1 lần/`nativeLoop`; trễ đặt lại (đối số r2 của `CUSTOM_extSetTimer` 0x36100) = 42-44 ms trong hoạt cảnh = như chơi thường (fps = bảng 0x153460[gv[134]] = 22; `gv+134` chỉ được ghi bởi `CommonUi::InitGameOption` 0x739b8 = 9; `Scene_Init` ghi `[EventProc+0x86]` chứ không phải gv). Không có `sleep/usleep/nanosleep` trong host call (chỉ `ftime`, `fread/fseek`, memcpy). Trễ = max(5 ms, 1000/22 − t_tick) => khi tick tốn > 45 ms nhịp tụt còn 1000/(t_tick+5) tick/giây; hoạt cảnh chạy THEO TICK (chữ chạy ký tự/khung, 83 khung tint, di chuyển NPC) nên lag làm cả cốt truyện chậm lại. Vậy "lag" là **chi phí mỗi tick**, không phải nhịp timer.

Chi phí/khung hoạt cảnh so với chơi thường (cùng cỡ):
| giai đoạn | 800x360 | x chơi thường | 1200x540 | x chơi thường |
|---|---|---|---|---|
| hộp thoại CÓ chân dung | 18,8 M | 1,8x | 67,2 M | **4,0x** |
| nạp scene (khung 895/897) | 126-130 M | 12x | 282 M | 17x |
| tint toàn màn khung 897-979 (83 khung) | ~40 M | 3,8x | ~75 M | 4,5x |
| hiệu ứng ánh sáng (DrawEffect) | 23,4 M | 2,2x | 24,3 M | 1,5x |
| hộp thoại không chân dung | 6,4 M | 0,6x | 15,0 M | 0,9x |

Nguyên nhân xếp theo mức ảnh hưởng (mọi địa chỉ từ so.dis / menu.sym):
1. **Chân dung hộp thoại T16 (`dialog.c`: `portrait` 0x199abc -> `blit` 0x199ff8 hai lần/khung, bóng + ảnh):** 11,8 M/18,8 M (63%) @800 và 46,6 M/55,8-67 M (84%) @1200 mỗi khung CÓ hộp (kể cả khi chỉ chờ bấm). Mỗi điểm vẽ tốn ~86 lệnh: vòng từng điểm đích + gọi `M:mui_put16` (134 018 lần/khung @1200) và `M:mui_getpx` (72 034 lần) KHÔNG inline (gfx.c:58-59, kiểm biên mỗi điểm) + chia `(2y+1)*sh/(2dh)` mỗi hàng + khớp `skip` + tint/alpha. Ảnh chân dung 155x220 (scale 1 @800, 2 @1200: 136 k điểm đích/lần, ~62% đục). Tĩnh trong khi chờ người chơi (cùng ảnh, cùng vị trí; chỉ nền đổi nhẹ). Khi không hộp: 2-4 M @800, 5,6-10 M @1200.
2. **Tint toàn màn từng điểm** `Graphic::ChangeFrameBufferBlending565` 0x500e8 (gọi từ `Map::MapEffect` 0xa8b60, 3 chỗ, loại hiệu ứng map 1-4; tham số đọc `[Map+0x14a]`, `[Map+0x14c]`): đúng 83 khung liền (khung 897-979 @400x240; cùng số tick mọi cỡ) mỗi khung quét cả màn ~25 lệnh/điểm: 24,8 M @800 (57% của cửa sổ đầu), ~55,7 M @1200 (64%). Đây là hiệu ứng nền của map mở đầu (pha màu), không phải của hộp thoại. Maps khác có `Map+0x14a` 1-4 sẽ trả giá này MỖI khung suốt thời gian map.
3. **`Graphic::FillRectAlpha` 0x4e684** (24 lệnh/điểm; `StateGame::Paint` 0xdd0bc/0xdd108): 16 khung (881-896) quét toàn màn alpha 80 màu đen (28,8 M @800) + dải đáy 68 hàng alpha **255** (đáng lẽ là fill đặc) = cắt cảnh "màn đen" của hoạt cảnh.
4. **Nạp scene một lần (khung 895-897, `StateGame::LoadProc` 100 M B + khung kế 38 M)**: `Map::MapSet` 0xafac0 -> `Map::RepaintMap` 55 M (vẽ lại toàn bộ ô) và `Graphic::FillRect` toàn bộ bộ đệm 40 M; `getAssetSizeFromVFS` 0x14c7e4 (19 lần, 4 M B + **48 625 fread + 24 318 fseek** của host trong MỘT khung: mở lại tệp VFS mỗi lần gọi (fdopen/dup) rồi duyệt chuỗi băm, mỗi phần tử một fseek+fread). Không phải mỗi khung nhưng gây "giật" mỗi lần đổi map/bắt đầu cảnh.
5. **Hiệu ứng ánh sáng (đợt bùng nổ xanh)** `Npc::Draw` -> `Graphic::DrawEffect` (70 lần/khung) -> `DrawImageStretchClip565` 29 lần x 580 k B (17 M/khung @1200, 72% khung): phóng to sprite hiệu ứng, blend từng điểm. Chỉ trong các khúc bùng nổ (~25 khung / 100).
6. Hộp thoại không chân dung: `M:span_a` (nền hộp alpha 225) 28% = 1,9 M @800 (nhỏ).
Không tìm thấy: sleep/chờ, giải nén ảnh mỗi khung (không có `LoadRes` ngoài khúc nạp scene), sprintf mỗi khung (`vsnprintf` 4/khung), vẽ chữ chậm (BFont `_midas_funcFntDrawPixelPreRGB` 612 lần/khung chỉ 0,14 M).

## 3. Mở rộng / lệnh đo "sau" (phía harness, kết quả thật)
- `emu.enable_fillrect` (mô phỏng sửa MC_grpFillRect): **tiêu đề 800x360: 81,55 M -> 0,883 M/khung (92x), ảnh md5 trùng từng khung**; hoạt cảnh khung 895/897: 126 M -> 80 M (1,57x), khung giữa 1,08x, ảnh trùng 18/18 khung (`exp1_*_800x360.json`). Điều kiện đường nhanh: `ctx[0x20]>254 (có dấu)`, `ctx[0x2c]==0` (không callback), độ sâu 16/32; cắt: fb w/h `[fb]/[fb+4]`, clip `ctx[4..0x10]` (x0,y0,x1,y1 loại trừ biên trên), màu `ctx[0x14]`, fb stride `[fb+8]`, dữ liệu `[fb+0x10]`.

## 4. DANH SÁCH "code ngu" và đề xuất (xếp theo lợi/rủi ro)
Ký hiệu: HOOK_BL = vá call-site như T20; THAY = vá lệnh đầu hàm bằng `b hook` (hook tự gọi bản sao đầu hàm hoặc chỉ xử lý đường nhanh và gọi lại hàm gốc qua call-site khác). % = tỉ lệ khung (800 / 1200).
| # | điểm | địa chỉ / ngữ nghĩa (so.dis) | % CPU/khung (cảnh) | đề xuất + cách móc | lợi ước tính | rủi ro (đổi hành vi) | ưu tiên |
|---|---|---|---|---|---|---|---|
| P1 | chân dung hộp thoại `blit` | engine/menu/dialog.c, `portrait` 0x199abc -> `blit` 0x199ff8 x2/khung; từng điểm + 2 lệnh gọi hàm mỗi điểm | 63% / 84% khung hội thoại có chân dung | (a) inline get/put + con trỏ hàng (không đổi hình), (b) bộ đệm đã phóng+lật RGB565+mặt nạ (như `ac_get` của hud.c), vẽ bằng đoạn liền: alpha=255 -> `host memcpy` theo đoạn đục; bóng: `span_a` chỉ trên đoạn đục hoặc bỏ bóng | khung hội thoại 18,8 -> ~7 M (2,7x) @800; 67 -> ~20 M (3,3x) @1200 | thấp (mã engine mới, kiểm trùng ảnh với bản cũ) | **1** |
| P2 | `MC_grpFillRect` | 0x1456a4 `(fb,x,y,w,h,ctx)`: vòng y/x, mỗi điểm kiểm clip ctx + alpha + callback (35 lệnh/điểm = 140 B). 217 chỗ gọi, gần hết qua `Graphic::FillRect` 0x5c52c/0x5d198 (`b MC_grpFillRect` ở 0x5c570, 0x5d1bc) | tiêu đề 98%, chọn lớp 68%, nạp scene 40 M, hoạt cảnh đầu 9-11%, chơi thường 4%, DrawLogo mọi StateMainMenu | HOOK tại 2 `b` (0x5c570, 0x5d1bc) + 5 chỗ `bl` khác: đường nhanh như `emu.py` (ghi hàng đầu bằng vòng ngắn rồi host memcpy nhân đôi / memcpy hàng); trường hợp khác nhảy về 0x1456a4. Cùng cách T20 (`fbcopy.c`) | **tiêu đề 92x (đo, ảnh trùng)**; chọn lớp 3x; nạp scene -40 M | rất thấp (tương đương từng byte, kiểm bằng ảnh) | **1** |
| P3 | nền menu chính | mmenu.c `blit` 0x18adfc (nền phóng NEAREST từng điểm với `mui_put16/getpx/__divsi3` 43 k lần) gọi từ `bg` 0x188380 và `mm_grid_paint` 0x187ba8 | 40% khung menu chính @800 (22,1 M `bg` + 16 M ô) | cache nền đã dựng vào bộ đệm (kích thước màn, tạo 1 lần), mỗi khung host memcpy; ô lưới giữ cách khác | menu chính 56 -> ~25 M (2x+) | thấp | 2 |
| P4 | lớp tối nền menu trong game + `span` | ingame.c `prelude()` (`Graphic_CopyFrameBuffer` FB<-FBOff rồi `mui_fill_a(...,150)` toàn màn mỗi khung) -> `span_a` gfx.c:40 (~10 lệnh/điểm, 11,6 M @800, ~26 M @1200); `span` gfx.c:30 (u32/lần) 17-24% | 56-77% span_a + 17-24% span của MỌI khung menu trong game (16-21 M / 34-42 M) | lưu bản "đã tối" của FBOff một lần khi mở menu (bộ đệm 0,58/1,3 MB), mỗi khung host memcpy vào FB; `span` -> hàng đầu + memcpy nhân đôi; chỉ vẽ lại phần đổi | menu 20 -> ~6 M @800; 40 -> ~12 M @1200 | thấp-vừa (làm mất hiệu lực khi FBOff đổi: đóng menu/đổi map) | **2** |
| P5 | nút HUD `art` | hud.c:237 `art`/`ac_get`: bộ đệm (col,al) đã dựng; mỗi khung chạy từng điểm theo `[xs,xe)` có alpha (45 B/điểm), 13 nút/khung | **35% / 51%** khung chơi thường | tách mỗi hàng thành đoạn a==255 (memcpy host) + vài điểm biên (alpha), hoặc chỉ vẽ lại ô khi đổi (đang bấm/hồi chiêu) và giữ bản chép; `bright` đường riêng | chơi thường 10,5 -> ~7,5 M (-28%) @800; 16,7 -> ~9 M (-46%) @1200 | thấp | **1** (lợi lớn nhất cho chơi thường) |
| P6 | `Graphic::FillRectAlpha` | 0x4e684: ~24 lệnh/điểm (tách 3 kênh, 3 mla, ghép) cho màu hằng+alpha hằng; gọi từ `StateGame::Paint` 0xdd0bc/0xdd108 (màn đen hoạt cảnh), DrawExpBar, minimap, DrawRectAlpha 0x4e92c | 4,4% + 28,8 M/khung trong 16 khung màn đen (@800) | alpha==255 -> fill đặc (P2); còn lại: C biên dịch 2 điểm/vòng với 3 bảng kênh (32/64/32 mục) dựng mỗi lần gọi (kết quả mỗi kênh chỉ phụ thuộc kênh đó) | x2-3 trên phần còn lại; dải 255 -> 0 | vừa (phải trùng làm tròn: kiểm bằng so ảnh từng điểm) | 3 |
| P7 | `ChangeFrameBufferBlending565` | 0x500e8 `(this,fb?,col,?,alpha)`: 25 lệnh/điểm, tra bảng `[tbl+0x6d]` theo kênh; `Map::MapEffect` 0xa8b60 (0xa91c4 + 2 chỗ) | 57-64% khung trong 83 khung đầu hoạt cảnh; MỌI khung của map có hiệu ứng 1-4 | 3 bảng kênh dựng mỗi lần + vòng u32 (như P6); hoặc đổi hành vi: tint từ khung nền (FBOff) một lần/nhiều khung thay vì mỗi khung (hiệu ứng nền ít đổi) | x2,3 (hoặc x10 nếu tint nửa tốc) | vừa; đổi hành vi (tint mượt nhịp thấp) chấp nhận | 3 |
| P8 | `DrawImageStretchClip565` (hiệu ứng) | `Npc::Draw` -> `DrawEffect` (69,7 lần/khung) -> 0x...StretchClip565 29 lần x 580 k B | 70-72% khung hiệu ứng (23-24 M) | thay đổi hành vi: giới hạn kích thước/tần suất phóng (vẽ 1/2 tick, nửa độ phân giải rồi nhân đôi), hoặc cắt nhỏ số hiệu ứng đồng thời; khó viết lại hàm blit phóng | x2 trong khúc bùng nổ | trung bình (hình ảnh hiệu ứng) | 4 |
| P9 | nạp scene: VFS | `getAssetSizeFromVFS` 0x14c7e4 (đối số: this/tên/out): mở lại VFS + duyệt chuỗi băm bằng fseek+fread từng phần tử (48 625 fread + 24 318 fseek/khung nạp) | một lần mỗi map/cảnh (4 M B + ~73 k host call) | lập chỉ mục RAM của thư mục VFS lần đầu, tra bảng; hook hàm (đọc cùng định dạng) | giảm giật khi đổi map | vừa (định dạng VFS/offset) | 4 |
| P10 | `Map::MapSet`/`RepaintMap` | 0xafac0: vẽ lại toàn bộ map 55 M khi nạp (kèm FillRect 40 M) | một lần/scene (95,7 M @800) | P2 cắt ~40 M; còn lại chấp nhận | -40% khúc nạp | thấp | 5 |
| P11 | minimap | `Map::Map_Draw_Minimap` (từ `Battle::DrawGameUI`): 0,97 M = 9% chơi thường @800, vẽ lại cả bản đồ mỗi khung dù tĩnh | 9% / ~6% | bộ đệm minimap, đổi khi hero đổi ô/mở map; chỉ vẽ chấm | -8% | thấp-vừa | 5 |
| P12 | `Map::MapLayerDraw`/`DrawTileBlending` | 0xb...: 9 lớp, 46 ô blending/khung = 1,98 M (19% chơi thường @800) | 19% / ~11% | lớp tĩnh có thể vẽ vào FBOff chỉ khi đổi (cần hiểu `Map::Update` 0xb10bc/0xb1090) | -15% | vừa-cao (logic map) | 6 |
| P13 | `Formula::calc` | `calc`->`calcByFormula` 187 lần/khung (0,42 M, ~4%) | 4% | ghi nhớ kết quả theo tick/đổi chỉ số (hash tham số) | -3% | thấp | 6 |
| P14 | `mmenu.c`/`dialog.c` `blit` dùng chung | gfx: `mui_getpx/put16` (gfx.c:58-59) không inline, kiểm biên mỗi điểm: 16-26% các cảnh menu/hội thoại | xem P1/P3 | khai báo `static inline` trong mui.h + bản `span`/`blit` dùng con trỏ | -10% các cảnh | rất thấp | 2 |
Ghi chú: P2+P5+P1 (3 mục thấp rủi ro) giảm: tiêu đề 70->~1 M, hội thoại có chân dung 18,8->~7 M, chơi thường 10,5->~7,5 M (@800). Cần P1,P2,P5 TRƯỚC khi mở T18 cho hoạt cảnh: khung trung gian cũng trả đủ phí Paint (T18 báo).

## 5. Nội suy trong hoạt cảnh (mã máy + `ex3.py`; không sửa interp.c)
Điều khiển (so.dis):
- Cờ "đang sự kiện" = `[EventProc+0x75]`; Tick: `EventProc::Event_Tick` 0x6e69c -> `EventSkipProc`, `Event_TickMovie` 0x6e348 (moveBreak, screenEffect 0x6b248, cameraMove 0x671ac, DialogWindow_Proc 0x6dfb8), `Interpreter::tick` 0x9b1b0. Vẽ: `StateGame::Paint` -> `EventProc::Event_Draw` 0x6ddcc (hộp thoại + `Draw_Face`).
- **Camera:** `Map::MapMove` 0xafc54: khi `[EventProc+0x75]!=0`: nếu `[EP+0x686]==0` camera bám hero như chơi thường; nếu `[EP+0x686]==1` camera tâm hướng tới đích `[Map+0xc0/0xc4]` = (`[EP+0x68c]`,`[EP+0x690]`) hoặc vị trí bảng sự kiện `[EP+536+20*i]`+8 khi `[EP+0x688]=i!=255`; mỗi tick dịch tối đa `s16[Map+184]` px (đo 32) -> `s32 [Map+0xd0]/[Map+0xd4]` (trái/trên), rồi kẹp biên map; thời lượng `[EP+0x687]` tick (giảm trong `Event_cameraMove`). `Event_SituateCameraTarget` 0x692a4 / `Event_SituateCamera` 0x693e8 đặt các trường này và `Map+184 = ceil(max(|dx|,|dy|)/tick)`. Hoạt cảnh mở đầu (đo 619 tick, khung 882-1500): `0x686==1` 526 tick; camera KHÔNG bám hero; bước camera 7-32 px/tick, nhảy cảnh 192/751 px.
- **Hero:** `HERO` s32 x=[+4], y=[+8]; hoạt cảnh chuyển hero bằng `Event_PlayerMove` 0x6972c/`Event_PlayerTeleport` 0x69600/`Event_PlayerAction`; đo: 8-22 px/tick (một số bước 32), teleport 656/1128 px.
- **NPC sự kiện:** bảng `EventProc+536+20*i`: `[+4]`=loại (7 = NPC), `[+0..+3]` x,y s16 (px, bằng vị trí Npc − 8), `[+17]` chỉ số Npc; `Npc*` = `[[gv+5528] + 4*chỉ_số]`, vị trí s32 [+4]/[+8] như HERO; di chuyển bằng `Event_EventMove` 0x6a5e8 -> `Npc::MoveSeting` 0xca670 rồi bước mỗi tick. Đo: 1-22 px/tick thường, 32-80 khi chạy, teleport 336-624 px.
Gợi ý mở rộng `interp.c` (đối tượng cần lerp / điều kiện snap):
1. `eligible()`: cho phép khi `ev[0x75]` nếu: không có fade/tint (`Map+0x14a` hiệu ứng 1-4, `Event_screenEffect`), không rung, hộp thoại KHÔNG cần loại trừ nhưng khung trung gian vẫn trả đủ Paint (P1 phải xong trước), không đổi scene (`Scene_Init` 0x6e818 / `Event_nextScreen` 0x69068).
2. Lerp: hero (+4/+8); mỗi NPC sự kiện type 7 (+4/+8 của `Npc`); camera: khi `[EP+0x686]==0` dùng đường hiện có (đặt hero nội suy rồi `Map::MapMove`); khi `==1` đặt tạm `[EP+0x686]=1,[EP+0x688]=255,[EP+0x68c/0x690]=điểm nội suy của TÂM camera, [Map+184]=255` rồi `MapMove` (đích trong tầm bước nên camera đặt đúng vào điểm), paint, khôi phục các trường đó và `MapMove` lần nữa (cùng mẫu "đặt – vẽ – khôi phục" của T18).
3. Snap (không nội suy khung đó): |Δ| > 96 px cho hero/NPC/camera (khoảng cách đo tách rõ: bước thường ≤ 80, teleport ≥ 192), đổi `[EP+0x688]`/`[EP+0x687]` đột ngột, đổi số/kiểu NPC hoặc `Map*`, `HERO::IsShake`, đang tint.
4. Cần chặn trong khung trung gian: `Npc::IncreaseTempFrame`-tương đương (hoạt ảnh NPC; T18 đã chặn `Monster::IncreaseTempFrame`), `ParticleMgr::process`, bộ đếm chữ hội thoại (`DIALOG_INFO+12`), `gv+0x130`. CHƯA kiểm: Npc có tự tăng khung trong `Draw` hay trong `Process` (cần RE `Npc::Draw` 0xca... trước).

## 6. Vướng mắc / chưa kiểm
- Số khung ở 1200x540 cho cutscene dùng `scen.py` (FAST) + `scen_cut` chỉ ở 800x360; chọn lớp/menu chính ở 1200x540 chưa đo (chỉ tiêu đề 155,8 M).
- Chưa có thời gian thật trên máy người dùng; "x chơi thường" là tỉ lệ byte-khối (không gồm chi phí host memcpy: 3,5-31 k lần/khung, bytes ~0,6-1,6 MB/khung).
- Không viết bản vá C để đo "sau" ngoài FillRect (cấm sửa engine). Số "lợi ước tính" khác FillRect là tính từ đo số điểm * lệnh/điểm.
- Chưa tìm mã máy `Map::Map_Draw_Minimap`, `Map::MapLayerDraw`, `Npc::Draw` chi tiết (chỉ có inclusive/caller).

## CÒN LẠI
(1) Đo chọn lớp/menu chính/hoạt cảnh ở 1200x540 đủ cảnh; (2) RE `Npc::Draw`/frame counter để chốt mục 5.4; (3) viết thử P1/P5 trong bản sao để có số "sau" thật; (4) đối chiếu thời gian thật trên máy.
