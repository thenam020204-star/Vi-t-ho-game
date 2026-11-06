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

## L6b (công nhân Sonnet, làm tiếp từ WIP a21e634; WORK ~/hl5work_l6)
Trạng thái: **XONG (1)-(4) + Trợ giúp (5), kiểm ĐẠT**. Không commit, không đóng APK. Dev .so: `~/hl5work_l6/l3/dev/dev.so` (`WORK=~/hl5work_l6 bash harness/l3/build_dev.sh`).
Lưu ý môi trường: `~/hl5work_l6/l3/dev` trước đây là symlink tới `~/hl5work/t16/test7`; lần dựng đầu đã ghi đè dev.so/p.so/sp.tsv/menu của test7 (KHÔNG phải APK dist). Đã thay symlink bằng thư mục thật; test7 nay chứa bản L6b (dựng lại từ dist APK nếu cần).

### Đã làm (WIP đã soát, dựng và chạy được, không phải sửa mã)
- `engine/menu/mm2.c`: MainMenu state 6 sub 1 Chuyên Gia (lưới 3+1, nhãn `tile_label` tự ngắt dòng/cỡ nhỏ, không đè), state 10 Thành tựu (điểm a/b, thanh %, 7 hàng, chip Nhận!, ▲▼ + thanh cuộn), state 11 Trợ giúp (sub 0 danh sách, sub 1 nội dung cuộn). Chỉ Paint + Touch mới; KeyPressed/Process gốc. Chạm = ghi trường rồi gọi phím gốc (OK/UP/DOWN/Back).
- `engine/menu/qpop.c`: StatePopup loại 9 (nhiệm vụ): tiêu đề, X, chân dung gốc `QuestMgr::DrawNpcBody`, trạng thái/khu vực/loại, mô tả cuộn, nút Xem phần thưởng/OK.
- `engine/menu/cui.c` + `hooks.def` (0x98858, 0x98a4c, 0x98880) + cờ `mui_drawn` ở thunk Paint ngoài cùng (`dispatch.c`, tính độ sâu cho popup gọi Paint cha): ẩn vòng xám và ↺ CHỈ khi Paint mới đã vẽ khung; rơi về gốc/cờ MUI_F_DISABLE thì vẽ như gốc. Back: mọi khung mới dùng cửa sổ chung (`widgets.c`, nút "<" = phím Back gốc) hoặc X (qpop), nên Back vẫn tương đương. Vùng chạm ↺ không có nút điều khiển khác đặt đè.
- states.def: mm6b, mm10, mm11, qpop; mmenu.c xuất `mm_dimwin/mm_tilebox/mm_panel`; build.sh thêm symbol QuestMgr/Mission/CommonUi.

### Kiểm (3 cỡ 400x240 / 600x270 / 1200x540)
| Mục | Kết quả |
|---|---|
| `harness/l6b/check_l6b.py` new/off vs orig (v0.2-hd): 4 chế độ Chuyên Gia, nav, Back; Thành tựu hàng/OK/▲▼/cuộn vòng/Back; Trợ giúp hàng/OK/Back x2/▲▼; popup nhiệm vụ X / Xem phần thưởng 2 lần / OK | 108/108 ĐẠT, 0 lệch (`shots/L6/l6b_check_l6b.txt`) |
| check_mm (L3) x3 cỡ new/off/orig | ĐẠT, kết luận ĐẠT (`l6b_check_mm.txt`) |
| c1 (reg_c1_l6b) x3 cỡ | 97 ĐẠT, 0 LỖI (`l6b_hoi_quy_c1.txt`) |
| cash 400x240 | sel/drag/popup/buy/vùng chạm ĐẠT; `cycles`: live/top/cls == gốc, CHỈ hash ảnh khác (đã biết từ L3: HUD mới/hook T16; nay thêm ↺/vòng bị ẩn ở cash) (`l6b_check_cash_400.txt`) |
| Không cấp phát trong paint | qpop/mm2 chỉ dùng bộ đệm static (bss) và mảng stack; không gọi calloc/malloc |
Ảnh (11): `shots/L6/l6b_{expert,mission}_{400x240,1200x540}_new`, `l6b_help1_400x240_new`, `l6b_help_600x270_new`, `l6b_mission_tap_600x270_{new,off}` (off = ↺ gốc còn hiện, new = đã ẩn), `l6b_quest_{400x240,600x270,1200x540}_new`.

### CÒN LẠI
- Soát mắt trên máy thật; cash 600x270/1200x540 chưa chạy lại (chỉ 400x240); hit-test so mockup cho 3 màn mới chưa có (không có mockup).
- Chuỗi Trợ giúp còn tiếng Anh ("Class/Warrior...") là dữ liệu dịch, không thuộc UI.
- Chưa làm L6c/L6d (Quản lý dữ liệu, Kỹ năng 7..10, Tinh linh 4, Nhiệm vụ 4/5/6, popup loại 0/6...). ↺ vẫn hiện ở các màn còn gốc (đúng thiết kế).
- Chưa có ảnh nhấn mạnh popup nhiệm vụ trên Back vật lý; chưa gộp vào dist/test8.

## L6c (công nhân Sonnet; WORK ~/hl5work_l6; dừng ~250k ngữ cảnh, CHƯA LÀM HẾT)
Trạng thái: **XONG (4) Nhiệm vụ sub 4/5/6 và (2) một nửa: Kỹ năng sub 8 + sub 10. CHƯA LÀM: Kỹ năng sub 7, sub 9; Tinh linh sub 4; Quản lý dữ liệu (MainMenu 9/1).** Không commit, không đóng APK. Dev .so: `~/hl5work_l6/l3/dev/dev.so` (`WORK=~/hl5work_l6 bash harness/l3/build_dev.sh`). Chạy test7 symlink: không ghi vào `~/hl5work/t16/test*` (thư mục dev là thư mục thật từ L6b).

### Phát hiện RE (mã máy so.dis, không đoán)
- **Kỹ năng sub 7..10 KHÔNG phải "mua/ghép sách" như L6a ghi**: ảnh gốc ép bằng `SetSubState` (harness/l6c/force.py; sub 8/9/10 đứng yên nhiều khung, KHÔNG bị Process đặt lại) cho thấy: sub 7 = lưới Kỹ năng + quản lý ô nhanh (KeySkillMenu bảng khoá sub 7 0x103f5c: LEFT/RIGHT, '#' = `HERO::ChangeHotKeyToggle`, OK = `SetHotKeyBelt(-1)` xoá ô); sub 8 = **"Hợp thành"** (CommonText(149)); sub 9 = **"Chế biến"** (CommonText(150), công thức = `HERO` +0x308 mảng s8, số lượng u8 +0x30c, `ItemTable::GetItemTableInfo(.., 15, id)`); sub 10 = **chọn vật phẩm ghép** (OpenComposeFilter, danh sách lọc 5/trang). Sub 8 do Back từ sub 10 hoặc từ nơi khác trong KeySkillMenu; tới sub 8 bằng phím thật chưa lần ra (sub 8 được SetSubState từ 0x104318 (Back của sub 10) và 0x105310; sub 9 từ 0x10456c).
- Bảng khoá: `KeySkillMenu` dùng chỉ số = `NumKeyToNaviKey(key)+16` (OK -5 = idx 11, RIGHT -4 = 12, LEFT -3 = 13, DOWN -2 = 14, UP -1 = 15; '0' = 48 = idx 64; Back -16 = idx 0). **Sub 8**: `[+0x11]` con trỏ 0/1 = ô A/B, 2 = nút "Bắt đầu hợp thành"; s16 `[+0x2a]`/`[+0x2c]` chỉ số vật phẩm trong túi (-1 trống; `BagItem::GetBagItemPtr(short)` 0x9b5e0); OK: con trỏ<=1 -> `OpenComposeFilter()` + `[+0x12]=0` (sang sub 10); con trỏ==2: thiếu ô -> thông báo, đủ -> popup 19; Back -> sub 5; '0' xoá hai ô; UP: 2 -> 0; DOWN -> 2; LEFT/RIGHT đảo 0/1. **Sub 10**: con trỏ s8 `[+0x12]` (tuyệt đối), danh sách = `BagItem+(122+i)*4`, tổng s16 `[bag+872]`; OK chọn vào ô `[+0x11]`; Back -> sub 8; LEFT/RIGHT ±1. **Sub 9**: `[+0x12]` chỉ số tuyệt đối, `[+0x11]` ô trong cửa sổ 0..7 (2 hàng x 4 cột; UP/DOWN ±4, LEFT/RIGHT ±1, có vòng); OK (0x104d90): kiểm nguyên liệu `RefineItem::IsHaveSpecialMixMaterial`, túi đầy -> popup, đủ -> popup (chưa đọc hết).
- **Nhiệm vụ**: `KeyQuestMenu` sub 3 và 4 dùng chung một bảng khoá (0x1014f8); OK với `[+0x11]==0` đổi `sub = 7 - sub` (3 <-> 4), `[+0x12]=0` và gọi `QuestMgr::setQuestViewInfo(sub-3, [+0x10])` (0 = mô tả, 1 = phần thưởng, văn bản ở `qm+28`); `[+0x11]==1` -> `sub += 2` (3 -> 5, 4 -> 6), `[+0x2e]=0`, `[+0x6c]=[+0x10]`, `LoadWorldMapImage`, `SetMapMode`. Paint sub 4 = `QuestMgr::drawQuestInfo(4, ...)` cùng bố cục sub 3 (nhãn nút InGameText(133)/(147)). Sub 5 và 6 vẽ y hệt nhau (DrawQuestMenu nhánh `[+0xd]-5<=1` -> 0xe9998); chạm: chế độ 0 tám điểm 60x60 (đang chọn ? OK : `TouchWorldMapPoint(i)`), thanh trái dưới 69x17 -> `MapModeKeyPressed(42)`, chế độ 1 thêm thanh phải dưới `MapModeKeyPressed(35)` + dải trên/dưới -> -1/-2, ô 17x17 góc trái trên cửa sổ -> `KeyPressed(-16)`. Bản đồ của InGameMenu là cài đặt riêng (camera `[+52]/[+54]`, sprite nút `DrawWorldMapSpr`), KHÁC StateMap (lưới 6x4 ô, map.c) nên không tái dùng được map.c.

### Đã làm
| Màn | Cách làm | File |
|---|---|---|
| Nhiệm vụ sub 4 (phần thưởng) | mở rộng `quest` (states.def `<=4`): cùng hộp chi tiết sub 3, văn bản = `qm+28` (bỏ ';' cuối, `$` -> vàng, `|` -> trắng như qpop; bộ đệm static 520 B), nút 0 = InGameText(133) "Xem nội dung", nút 1 = Bản đồ lớn; Back/kéo/nút chạm = phím gốc (OK/Back), cuộn mô tả đặt lại khi đổi sub | quest.c, states.def |
| Nhiệm vụ sub 5/6 (bản đồ thế giới) | `qmap`: Paint = **Paint GỐC** (bản đồ/DrawZone vẽ bằng sprite gốc, camera và điểm không đổi) rồi đè thanh nút góc trái dưới (+ phải dưới ở chế độ 1) phủ kín ô 69x17 gốc, nút "<" góc trái trên (sprite ui 25); chạm nút -> đúng hàm gốc (`MapModeKeyPressed(42/35)`, `KeyPressed(-16)`), chạm khác -> `TouchPressed` GỐC (điểm bản đồ, dải cuộn) | l6c.c (qmap_*), states.def, build.sh (defsym MapModeKeyPressed) |
| Kỹ năng sub 8 "Hợp thành" | `skc`: cửa sổ + tab dưới, 2 ô vật phẩm (icon qua `BagItem::GetBagItemPtr(short)`) + "+" + "?" (gốc cũng chỉ vẽ "?") + nút "Bắt đầu hợp thành" (xám khi thiếu) + "Bỏ chọn" + hộp thông tin vật phẩm ô đang chọn (draw_item_info của bag.c); chạm ô i: `[+0x11]=i` rồi OK gốc (mở danh sách sub 10); nút: `[+0x11]=2` rồi OK; "Bỏ chọn": phím '0'(48); Back = nút "<" cửa sổ | l6c.c (skc_*) |
| Kỹ năng sub 10 (chọn vật phẩm ghép) | `skc` dùng lại picker của Túi (`paint_picker`/`pick_cb`, con trỏ s8 `[+0x12]`): 5 ô/trang, ◀▶, thanh cuộn ngang, hộp thông tin, OK/Huỷ; chạm lần 1 chọn, lần 2 = OK như TouchSkillMenu gốc | bag.c (+ 2 hàm xuất sk_pick_paint/sk_pick_cb ở cuối), l6c.c |
Không cấp phát trong Paint (bộ đệm static/stack; không malloc). File chung chỉ thêm dòng: states.def (3 dòng + sửa điều kiện quest), build.sh (2 defsym + 1 dòng biên dịch l6c), mui.h (khai báo cuối file). Không đụng hud.c.

### Kiểm (3 cỡ 400x240 / 600x270 / 1200x540; new, off = MUI_F_DISABLE, orig = v0.2-hd)
Harness mới `harness/l6c/{check_l6c,compare_l6c}.py`, `run.sh OUTDIR PART` (quest|skill), `explore.py`, `force.py` (ép sub bằng SetSubState trên cả orig lẫn new). So trạng thái guest (state/sub/con trỏ/popup/s16 ô/camera) sau thao tác: chạm mới vs phím/chạm gốc.
| Mục | Kết quả |
|---|---|
| Nhiệm vụ: sub 3 -> 4 (nút 0), 4 -> 3, Back từ sub 4, sub 4 -> 6 (Bản đồ lớn), thanh '*' (cờ Minimap), chế độ 1 (OK gốc) + nút '#', dải cuộn trên/dưới chế độ 1, chạm điểm bản đồ 2 và 5 (chọn + OK), Back bằng phím (6 -> 4) | ĐẠT 51/54 so orig; 3 "lệch" đều là một kịch bản `qmback` (1 cỡ x 3): chạm góc trái trên trong **gốc** đồng thời đụng ô điểm bản đồ số 4 (đổi `[+0x41]` 6 -> 4) rồi mới Back; nút "<" mới chỉ Back (sub 4 giống nhau, `[+0x41]` giữ 6). Khác biệt có chủ ý, không phải lỗi |
| Kỹ năng sub 8/10: chạm ô A, ô B (sub 8 -> 10, `[+0x12]=0`, `[+0x11]` đúng), nút Bắt đầu (popup loại 2 nbtn 2), "Bỏ chọn" ('0': hai ô -> -1), Back sub 8 (-> 5), Back sub 10 và nút Huỷ (-> 8) | ĐẠT 48/48 (new và off == orig) |
Ảnh (11): `shots/L6/l6c_quest_reward_{400x240,600x270,1200x540}_new`, `l6c_quest_map0_{400x240,1200x540}_new`, `l6c_quest_map1_600x270_{new,orig}` (chế độ khu vực: bản gốc/mới dùng chung cảnh), `l6c_sk8_{400x240,1200x540}_new`, `l6c_sk10_600x270_new`, `l6c_sk8_600x270_orig`.
