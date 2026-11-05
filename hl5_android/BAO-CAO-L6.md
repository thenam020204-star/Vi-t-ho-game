# BAO-CAO-L6: L6a kiểm kê menu con còn giao diện cũ (công nhân Sonnet, WORK ~/hl5work_l6)

Trạng thái: **L6a XONG** (bảng dưới). **L6b CHƯA LÀM** (hết ngân sách ngữ cảnh; xem "CÒN LẠI"). Không sửa file engine/states/PLAN, không commit.

## Phương pháp
- Mã máy: `so.dis` (jump table `Paint` của từng lớp, `SetState/SetSubState/SetPopupState` mọi nơi gọi, `StatePopup::Paint` bảng loại 0..15, vtable CallPopup 0x70..0x80). Script: `harness/l6/{sw,ctx,setters,pops}.py` (cần `/tmp/funcs.pk` dựng từ so.dis, xem đầu `sw.py`).
- Xác nhận dispatch bằng harness với dev .so test7 (`~/hl5work/t16/test7`, chỉ đọc): `harness/l6/plib.py` (class `Probe`) móc địa chỉ hàm Paint GỐC và các hàm paint mới (menu.sym), gọi thunk `mui_paint_<LỚP>` trên đối tượng thật đã ép (state, sub), ghi "orig" hoặc "new:<dòng>" mà không chạy Paint. `p3.py` = lưới (state x sub) cho INGAME/NPC/EXPERT/CASH/MAP/GAME; `p1.py` = MainMenu; `p4.py` = gọi `SetPopupState(n)` thật của 6 lớp rồi dò popup tạo ra; `p5/p6/p7.py` = ảnh. Lưu ý: hook cần `uc.ctl_flush_tb()` sau khi thêm (khối dịch đã cache).
- Ảnh: `shots/L6/` (9 ảnh mới 600x270 + 3 ảnh người dùng). Ảnh ép sub của Kỹ năng sub7 / Tinh linh sub4 không đáng tin (Process đặt lại sub) nên đã bỏ; các mục đó chỉ xác nhận bằng dò dispatch.
- Id lớp của `BaseState::CreateState` (0x42698): 0 NetMenu, 1 Game, 2 Popup, 3 InGameMenu, 4 NpcMenu, 5 MainMenu, 6 Expert, 7 Map, 8 CashShop.

## Bảng 1: state/sub CÒN RƠI VỀ GỐC (đã xác nhận bằng Probe)
Ưu tiên: A = người chơi gặp thường xuyên / người dùng đã báo; B = hay gặp; C = hiếm / không đáng làm.

| # | Lớp | state/sub (hoặc loại) | Màn là gì (mã máy) | Đường tới (offline) | Ưu | Ảnh |
|---|---|---|---|---|---|---|
| 1 | MainMenu | state 6 sub 1 | Chuyên Gia: chọn chế độ (Cầm cự / Đua thời gian / Đột phá / Xem BXH), `DrawExpert` nhánh `sub==1` | Lưới > Chuyên Gia > chọn slot có hero (OK) | A (người dùng) | mm_chuyengia_sub1, nguoidung_1 |
| 2 | MainMenu | state 10 | Thành tựu (`DrawMission`): điểm 0/300, thanh %, danh sách + chip "Nhận!", cuộn | Lưới ô Thành tựu | A (người dùng) | mm_thanhtuu, nguoidung_2 |
| 3 | MainMenu | state 11 | Trợ giúp (`DrawHelp`): danh sách tiêu đề (vài mục còn tiếng Anh "Class/Warrior") + nội dung | Lưới ô Trợ giúp | A | mm_trogiup |
| 4 | MainMenu | state 9 sub 1 | Quản lý dữ liệu (nút "Lưu dữ liệu") | Cài đặt > mục 9 > OK | B | mm_caidat_sub1 |
| 5 | StatePopup | loại 9 | Popup nhiệm vụ (`QuestMgr::QuestStatusList`, tiêu đề, chân dung NPC, trạng thái/khu vực/loại, mô tả cuộn, "Xem phần thưởng"/OK/X), nbtn 2 | Trong game: bấm nhiệm vụ ở NPC/HUD; InGame Nhiệm vụ | A (người dùng) | pop_quest, nguoidung_3 |
| 6 | InGameMenu | state 4 (Kỹ năng) sub 7..10 | mua/ghép sách kỹ năng (SetSubState 7 từ Process, 8/9 từ KeySkillMenu, 10 từ OpenComposeFilter) | Kỹ năng > ô sách | B | (chỉ dò dispatch) |
| 7 | InGameMenu | state 7 (Tinh linh) sub 4 | SetSubState 4 từ OpenBagFilter/Process (chọn vật phẩm cho tinh linh) | Tinh linh > thêm vật | B | (chỉ dò) |
| 8 | InGameMenu | state 8 (Nhiệm vụ) sub 4 | nhận thưởng nhiệm vụ (khung trắng "Hoàn tất", nút Xem nội dung / Bản đồ lớn) | Nhiệm vụ > chi tiết > hoàn tất | A | ig_quest_sub4 |
| 9 | InGameMenu | state 8 sub 5, 6 | bản đồ thế giới trong menu (`SetMapMode`, `DrawWorldMap`, `MapModeKeyPressed` 0xe658c) | Nhiệm vụ > Bản đồ lớn | B | (chỉ dò; ép sub 5 lỗi đọc bộ nhớ, cần dữ liệu bản đồ) |
| 10 | StateMap | `[+0xd]==1` (ctor tham số 1) | bản đồ chỉ-ảnh (có ↺ gốc) | `CallState(7,1)` (nơi gọi chưa lần ra) | B | map_img |
| 11 | StatePopup | loại 0 (ô mờ) | `Event_SituatePopup` (chú thích tình huống, nền mờ) | trong game (sự kiện kịch bản) | B | pop_situate0 |
| 12 | StatePopup | loại 6 | `EventProc::onMessageBox(char*,char**,int)` | sự kiện kịch bản | B | (gọi trực tiếp không tạo popup; chưa chụp) |
| 13 | StatePopup | loại 1..3 nbtn 0 hoặc >2; loại 7 nbtn 0 hoặc >6; loại 10 khi `[+0x814]==0`; loại 4/5 cha không phải NPC/Expert | biến thể không có dòng; ở các `SetPopupState` đã dò mọi n đều rơi vào dòng đang có (bảng 3) | Event_PlayerGetRepay (loại biến) chưa xác định | C/B | - |
| 14 | StatePopup | loại 11, 12, 13, 14, 15 | 11 = vá mạng, 15 = DSP tải (mạng); 12, 13, 14: không thấy nơi tạo hằng số trong mã | không tới được offline (mạng) / chưa thấy | C | - |
| 15 | StateGame | state 2 sub 0 | biểu ngữ ảnh Chuyên gia (`DrawExpert`) | Chế độ Chuyên Gia vào trận | C (ảnh) | - |
| 16 | StateGame | state 3 | màn LOADING + mẹo chữ (không phải menu) | mọi lần vào bản đồ | C | game_st3 |
| 17 | MainMenu | state 0, 1, 2, 3 | CheckEvent, UserLogReg (mạng), Logo, Title (ảnh nền + chữ ảnh) | khởi động | C | - |
| 18 | MainMenu | state 7 Network, 8 Update, 12 Community | ô lưới đã bỏ (L3: Vào kho/Cập nhật/Hỗ trợ) | KHÔNG tới được offline (suy từ `KeyMainMenu`; chưa ép dò động) | C | - |
| 19 | StateNetMenu | mọi state | `CreateState(0)` chỉ từ ô Vào kho + gói mạng | KHÔNG tới được offline (chưa dò động) | C | - |
| 20 | MainMenu | state 5 sub>=2, 6 sub>=2, 9 sub>=2 | không thấy nơi vẽ riêng (`KeyExpert` có so sub với 1 và 2: nghi 6 sub 2, cần dò thêm) | ? | ? | - |

## Bảng 2: đã có dòng states.def và xác nhận dispatch = new (không cần làm)
INGAME: state 0,1,2,3,5,6,9 mọi sub (riêng System sub 3: `DrawSystemMenu` có nhánh sub 3 (0xe7f1c, dùng con trỏ [+0xf]/[+0x10]) mà `paint_sys` không vẽ gì ở sub 3 và coi sub 2/4 là Trợ giúp: NGHI VẤN, cần đối chiếu bản gốc), state 4 sub 0..6, state 7 sub 0..3, state 8 sub 0..3. NPC: state 0..6 và 7 sub 0..8 (state 8 và 7 sub 9 rơi gốc nhưng Paint gốc không có nhánh nên không hợp lệ). EXPERT: state 0,1 (không có state >=2). CASH: state 0 (Paint gốc không phân nhánh state). MAP: mode 0/1 với `[+0xd]==0`. GAME: state 0 (dlg/hud), 1 và 4 (gpop), 2 sub 1..2 (gres). MAIN: state 4, 5 sub 0/1, 6 sub 0, 9 sub 0.

## Bảng 3: popup tạo từ `SetPopupState(n)` thật (p4.py, n lấy đủ dải của từng lớp)
Mọi n đều tạo popup loại 2 (nbtn 1..2), 3 (nbtn 2), 7 (nbtn 1..3), 4 (nbtn 2), 5 (nbtn 1), 8 (nbtn 2) và đều ra dòng mới: INGAME n=1..25 -> `pop`; NPC 1..15 -> `pop` / `expop` / `expqty` (n=16: không popup); EXPERT 1..4 -> pop/expqty/expop; CASH 1..4 -> pop; MAIN 1..30 -> pop (n=4 -> mmpop, 28 -> loại 3 -> pop); GAME 1..2 -> pop. Tức mọi popup do các lớp menu gọi (Có/Không, thông báo, menu dọc) đã có giao diện mới; chỉ còn các popup do EventProc/QuestMgr tạo (bảng 1 dòng 5, 11, 12, 13). Giới hạn: dò với tham số 0/văn bản giả, loại 10 (khe 0x74, cần ItemInfo*) chưa tạo được.

## CommonUi: nút ↺ và vòng tròn xám (hỏi thêm của quản đốc)
Cả hai do `mainPaint` (0x987c4) vẽ SAU Paint của state, nên giao diện mới không chặn được:
- `[top+4]` = id lớp. Lớp <= 1 (NetMenu, Game): không vẽ gì. MainMenu (5): state 2/3 chỉ vẽ vòng; state 4 vẽ vòng không ↺; mọi state khác (5, 6, 9, 10, 11) vẽ vòng + ↺. Popup (2): ↺ trừ loại 6, 11, 12, 15. Expert (6): ↺ khi state != 0. InGame/Npc/Map/Cash (3, 4, 7, 8): luôn có vòng + ↺.
- ↺ = `CommonUi::DrawTouchCancelUi` 0x73ce8 (gọi ở 0x98880; vẽ, còn chạm do state xử lý). Vòng xám = `CommonUi::DrawTouchFeedbackSprite` 0x73c50 (gọi 0x98858, 0x98a4c): sprite id 30 tại `gv+0xf4/0xf8` (toạ độ chạm cuối, ghi bởi `BaseState::TouchPress`), khung hoạt hình `gv+0x130` (đặt 0 trong `mainKeyPressed`); nó là "phản hồi chạm" nên hiện ở vị trí chạm gần nhất, không phải phần tử cố định. Chưa lần ra vì sao nó đứng yên lâu (nơi tăng `0x130` không là `strb` đơn giản).
- Đề xuất (L6b, nhỏ): HOOK_BL ở 0x98880 và 0x98858/0x98a4c bọc: bỏ qua nếu khung này Paint mới đã vẽ (cờ đặt trong `mui_begin`, xoá đầu mỗi khung). Cần giữ ↺ ở các màn còn gốc (bảng 1) cho tới khi làm xong; cần kiểm cờ off (`MUI_F_DISABLE`) = gốc.
- Nửa nút tròn trắng ở mép trái: người dùng xác nhận là của hệ thống/điện thoại, bỏ qua.

## Đề xuất chia lô
- **L6b (ưu tiên 1, ~1 công nhân)**: (1) Chuyên Gia state 6 sub 1 (lưới 3+1 ô, tái dùng `mm_grid_paint`/`mui_button` của mmenu.c; nhãn đè nhau do ô quá hẹp: cần bố cục riêng + `mui_text` có cắt); (2) Thành tựu state 10 (thanh điểm + `mui_row` + `mui_scrollbar` + chip "Nhận!"; mẫu danh sách có sẵn ở `paint_sys` sub 2/4 của ingame.c và `mm_option_paint`); (3) popup nhiệm vụ loại 9 (khung/nút của popup.c `pop_paint`, mô tả cuộn `mui_wrap` + `mui_scrollbar` như hộp Trợ giúp ingame.c, chân dung = ảnh gốc qua Paint gốc/`gpop`-style đè); (4) ẩn ↺ + vòng xám bằng hook. Có thể kèm Trợ giúp state 11 (tái dùng nguyên khối danh sách + hộp nội dung của ingame.c sub 2/4).
- **L6c**: Quản lý dữ liệu (MainMenu 9 sub 1) + InGame Kỹ năng sub 7..10 (tái dùng lưới/`sk.c`, `bag.c` cho ô sách + popup chọn) + Tinh linh sub 4 (`spirit.c`) + Nhiệm vụ sub 4 (phần thưởng; quest.c) và sub 5/6 (bản đồ thế giới; map.c).
- **L6d**: popup loại 0, 6, `Event_PlayerGetRepay`, StateMap chỉ-ảnh (có thể giữ gốc), Game state 2 sub 0 (ảnh), rà các nghi vấn dòng 20 và System sub 3.
- Ảnh logo/Title/loading (dòng 16, 17) nên giữ gốc.

## CÒN LẠI
- L6b chưa làm (theo luật ngữ cảnh). Chưa chụp loại 6; chưa lần ra nơi tạo loại 12/13/14 và `Event_PlayerGetRepay`; chưa xác nhận động việc state 7/8/12 và NetMenu không tới được; nghi vấn System sub 3 và MainMenu 6 sub 2 (dòng 20) chưa đối chiếu.
- Chạy harness chậm (mỗi lần khởi động ~vài phút CPU ở 600x270); dùng `tail --pid` để chờ thay vì vòng sleep.
