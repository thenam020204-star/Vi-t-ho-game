# BAO-CAO-L1: T17c4 + T17c6 + logic T7 (công nhân A1, Sonnet, phiên mới)

WORK `~/hl5work_l1` (bản sao `~/hl5work_l0`). Harness `harness/l1/` (`build_dev.sh` dựng vào `$WORK/l1/dev`, `check_t7.py`, `check_cash.py` + `compare_cash.py`, `probe_*.py`). Ngữ cảnh đã gần hạn (~300k) nên L1 chỉ làm được MỘT PHẦN: xem "CÒN LẠI".

## Trạng thái
| Hạng mục | Trạng thái |
|---|---|
| Logic T7: cash mua miễn phí không mạng | XONG, kiểm ĐẠT |
| Logic T7: hồi sinh offline | XONG (patch byte), kiểm ĐẠT (xem 2) |
| Cash shop UI (`engine/menu/cash.c`, StateCashShop state 0) + chuỗi "Miễn phí" + popup xác nhận/đã nhận | XONG, kiểm ĐẠT 3 cỡ |
| Tinh linh (2 tab), Nhiệm vụ (2 tab), bản đồ khu vực + bản đồ lớn (StateMap), Chuyên gia (2 màn + popup), popup StateGame (hồi sinh/thắng/thua UI mới) | CHƯA LÀM (đã dò một phần, ghi chú ở CÒN LẠI) |

## 1. Cash shop miễn phí (t7.c + hooks.def + so_patches.tsv khối `L1 T7`)
Luồng gốc: `StateCashShop::KeyPressed` 0xd97ac (OK) -> `SetPopupState(2)` xác nhận -> `Process` 0xd9218 sau "Có" gọi `NETWORK::netConnect(3)` -> máy chủ trả vật phẩm (`ProcPacket_Shop`). Offline:
- `HOOK_BL 0xd9330` (bl netConnect(3) trong Process) -> `t7_cash_netconnect`: rec = `[[this+0x14]+4] + 24*[this+15]`, cấp `ItemTable::NewItemToBag(ITEMTABLE(), 18, rec[8], rec[9] hoặc 1, 1)` (đúng như server cấp: loại 18, cờ 1); đặt `t7_bought`. Mode khác 3 chuyển cho `netConnect` gốc.
- `HOOK_BL 0xd9350` (bl SetPopupState(0) đóng popup sau netConnect) -> `t7_cash_popup`: nếu vừa mua thì mở popup loại 1 "Đã mua." (CT 93).
- `HOOK_BL 0xd9a24` (bl SetPopupState(2) trong KeyPressed) -> `t7_cash_confirm`: thay đoạn "Giá gốc 0 won sẽ bị tính thêm phí thông tin (Có tính cước gọi) / Bản offline: miễn phí." bằng "Bạn có muốn mua?" (CT 91) + `;` + chuỗi mới **"Miễn phí"** (`t7_free`, mã game 2 byte/từ: từ điển idx 104 "Miễn", 485 "phí"; không đụng VFS/vi.bin).
- Patch byte (`patch/mk_t7_patches.py` sinh khối `# >>> L1 T7`, kiểm mnemonic cũ bằng capstone; diff + lần luồng `patch/t7_patch_diff.txt`): 0xd9988/0xd9c2c/0xd9c30 `ldrh r11,[..giá]` -> `mov r11,#0` (giá 0, r11 chỉ là đối số sprintk, không rẽ nhánh theo r11).
- Kiểm (`harness/l1/check_t7.py`, `check_cash.py`; `shots/L1/`): vào cửa hàng, mua ô 0 (Elixir x20): túi loại 18 id 34 từ 0 -> 20; ô 4 (id 9, x5): 0 -> 5; ô 6 (id 12): 0 -> 1; popup sau "Có" = "Đã mua." rồi OK trở về cửa hàng (state 0); run.log: **0 dòng `NET`**, JNIC chỉ `freeMemory`/`getUniqueAssetNameFromID`/`getUniqueNumberFromStrings` (không MTX).
- Chưa làm: hook `netConnect(3)` thứ hai 0xd93b4 (popup loại 4 trong Process: không tìm thấy đường vào từ KeyPressed trong harness; vẫn là netConnect gốc, sẽ rơi vào chốt chặn mạng chung của L5).

## 2. Hồi sinh offline
`StateGame::KeyGameOver` 0xdbe90 (state StateGame = 1; `KeyPressed` 0xdf3xx: 0 chơi, 1 GameOver, 2 Expert, 4 RivivalOk): có Bùa (loại 18 id 23) thì trừ Bùa + `HERO::Revival()` + `SetState(4)`; không Bùa thì `SetProductID(26)` + `netConnect(5)`. Patch `0xdbf40 bgt 0xdbf70 -> b 0xdbf88` (so_patches.tsv): luôn `Revival()` + `SetState(4)`, KHÔNG trừ Bùa, KHÔNG gọi mạng (0xdbf44..0xdbf6c không còn tới được; diff + lần luồng ở `patch/t7_patch_diff.txt`). Thêm `HOOK_BL 0xdc188` (bl netConnect(5) trong `StateGame::Process`, popup xác nhận mua hồi sinh) -> `t7_game_revive` (Revival + SetState(4)) để không còn gọi mạng ở nhánh thứ hai (chưa dò được đường vào, harness không tới).
Kiểm (`check_t7.py`, ảnh `go_popup/go_after`): `StateGame::SetState(1)` (popup hồi sinh), HP đặt 5, OK -> HP = 192 (= HP tối đa), state 4 ("hồi sinh thành công"), OK -> state 0 (chơi), Bùa 0 -> 0, run.log 0 `NET`.

## 3. Cash shop UI (`cash.c`, kiến trúc A)
`states.def`: `MUI_STATE(CASH, cash, GS(self)==0, cash_paint, cash_touch, cash_move)`; `classes.def` đã có lớp CASH. Trường: +0xe ô trong cửa sổ (0..15), +0xf chỉ số tuyệt đối, hàng đầu cửa sổ = (+0xf-+0xe)/4, `data=*(this+0x14)`: u16 `[+2]` = số mục (30), mảng 24 B tại `*(data+4)`: +2 icon (u16), +4 ptr TÊN, +8 id (loại 18), +9 số lượng, +12 ptr MÔ TẢ, +16/+18/+20 giá (hiển thị "Miễn phí", không dùng giá). Chạm: ô khác = đặt +0xe/+0xf; ô đang chọn hoặc nút "Nhận!" (CT 261) = `KeyPressed(-5)` gốc; kéo dọc (từ thanh cuộn hoặc từ ô) = đổi hàng đầu cửa sổ (giữ ô cửa sổ); "<" = Back gốc. Popup (xác nhận, đã mua, túi đầy CT92, khu vực cấm CT331, vượt giới hạn CT302) là StatePopup nên dùng widget popup c1 (loại 1-2).
Kiểm (`compare_cash.py`, 3 cỡ x mới/gốc): chọn ô 5 và 14, kéo cuộn (4x DOWN == kéo 1 hàng), mở popup xác nhận: trường +0xc..+0x13 == phím gốc; 20 lần mở/đóng sau khởi động nguội: allocation sống == gốc, heap top delta == gốc, ảnh cuối trùng byte (số liệu trong BAO-CAO cuối); mua qua chạm Có (popup 2 nút) + OK; vùng chạm 16 ô + grid_scroll + close == `hit_rects.json` (`cash_shop_*`) ở 400x240, 600x270, 1200x540.
Lệch mockup (cần duyệt): (1) hộp chi tiết bắt đầu x = `gx+gridW+3+sbw+gap-1` (lệch 1-2 px so với bảng 153/226/357/510); (2) chân trang popup xác nhận hiện "Bạn có muốn mua? Miễn phí" một dòng (mockup ghép CT 91 + CT 304 thành 2 dòng); (3) nhãn nút Có của popup chung hiện "(A)Có" (gợi ý phím của chuỗi gốc, popup c1 chưa bỏ `(A)`); (4) mô tả cuộn chưa có thanh cuộn (mô tả cash ngắn); (5) chưa vẽ lớp phủ tối cho ô cuộn-biên.

## 4. Dòng đã thêm vào file dùng chung
- `engine/menu/states.def`: 2 dòng (chú thích + `MUI_STATE(CASH, ...)`), trước `#ifdef MUI_DEBUG`.
- `engine/menu/build.sh`: 2 dòng biên dịch (`for f in cash`, `for f in t7`) + 1 khối `--defsym` (ItemTable::NewItemToBag, StateCashShop::SetPopupState, HERO::Revival, StateGame::SetState, NETWORK::netConnect) trước dòng `vn_ensure`.
- `engine/menu/hooks.def`: 4 dòng `HOOK_BL` (0xd9330, 0xd9350, 0xd9a24, 0xdc188) + 2 dòng chú thích.
- `engine/menu/mui.h`: 3 khai báo `cash_paint/cash_touch/cash_move`.
- `patch/so_patches.tsv`: khối `# >>> L1 T7 ... # <<< L1 T7` (4 dòng patch byte, sinh bởi `patch/mk_t7_patches.py --update-repo`, đặt TRƯỚC khối `T17c menu-vtable`). Các dòng vtable CASH + 4 call-site `bl` do `inject_menu.py --update-repo` sinh lại khi gộp (không chạy ở đây để khỏi đè dòng của L2).
- `patch/caves.tsv`: không đổi (code trong đoạn PT_LOAD menu, không dùng hàm chết).
- Mới: `engine/menu/cash.c`, `engine/menu/t7.c`, `patch/mk_t7_patches.py`, `patch/t7_patch_diff.txt`, `harness/l1/*`.

## 5. CÒN LẠI (làm tiếp ở lượt sau)
Phân tích đã có (tiết kiệm thời gian):
- **Tinh linh** (InGameMenu state 7; `KeySpiritMenu` 0x101bc0, `DrawSpiritMenu` 0xee39c, `TouchSpiritMenu` 0xfc650). SubState (`+0xd`): 0 = hàng tab con (`+0xe` 0 = Cơ bản/Trang bị, 1 = Kỹ năng Tinh Linh; TRÁI/PHẢI đổi `+0xe`, đặt `+0xf=-1` khi sang tab 1), XUỐNG ở tab 0 = `SetSubState(1)` (chế độ lọc trang bị Tinh linh: 5 ô/trang + mũi tên, `+0xf` con trỏ; OK = `EquipItemSpirit::SetEquipItemSpirit(slot, item[+0x12])`, popup CT119/121), trong sub 1 `+0xf` = ô trang bị 0/1 (OK -> `SetPopupState(13)` gỡ: `Process` -> `ClearEquipItemSpirit`); tab 1: sub 2..4 (kỹ năng Tinh linh, 4 ô, `+0xf` 0..3, `HERO::LearnSpiritSkill`). Nút "9" (`0x102158`) -> `SetState(9)`. Dữ liệu: `HERO::GetSpiritInfoPtr`, `GetEquipItemSpiritPtr`. Đã chụp bản gốc (trống, hero mới): `~/hl5work_l1/p/sp_*.png`.
- **Nhiệm vụ** (state 8): `KeyQuestMenu` 0x101260 (`+0xd..+0x14,+0x2e,+0x3c,+0x6c`), `DrawQuestMenu` 0xe8b34, `DrawWorldMap` 0xe4c4c, `TouchQuestMenu` 0xfb3c0; chưa dò.
- **StateMap**: `classes.def` MAP vẫn `-1000` (cần `GetState()` của StateMap; ảnh bản đồ thật qua `LoadWorldMapImage`, toạ độ qua `DrawWorldMap` 0x123618, ảnh gốc ô 59/60 `shots/T17/kiemke.png`).
- **Chuyên gia** (`StateInExpertMenu`: `+0xc` state 0 chuẩn bị `DrawReady` 0xe1ba4 / `KeyReady` 0xe1818, 1 cửa hàng `DrawShop` 0xe0b40 / `KeyShop` 0xe16b0; cửa hàng dùng chung dạng lưới + `cash_expert` của cash.c có thể tái dùng `cash_paint` với bản ghi khác); popup `Process` 0xe275c.
- **Popup StateGame**: `StateGame::SetState(s)` 0xdbc54/`SetState(s,sub)` 0xda410: 1 = GameOver (hồi sinh Có/Không, `[this+0xe]` 0 = Có, 1 = Không), 4 = RivivalOk ("Hồi sinh thành công"), 2 = Expert kết quả; `KeyPressed` 0xdf3xx dispatch (0 chơi, 1 `KeyGameOver`, 2 `KeyExpert`, 4 `KeyRivivalOk`). Lớp GAME trong classes.def đã có (`[this+0xc]`), chưa có dòng `states.def` (Paint gốc của StateGame vẽ cả cảnh: cần gọi Paint gốc rồi vẽ popup đè, như `pop_paint`).
- Việc T7 còn: chặn `netConnect` còn lại (0xd93b4, 0xc64bc, 0x11b344/364/6a8, 0x11f378, 0x120e48, 0x1218f8/970, 0x121c28) thuộc L5.
