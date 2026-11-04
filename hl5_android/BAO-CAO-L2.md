# BAO-CAO-L2: T17c5 NPC (công nhân A2, phiên Sonnet)

Trạng thái: **LÀM MỘT PHẦN** (dừng vì ngữ cảnh ~300k, theo luật). Xong và kiểm: Mua kỹ năng, Mua vật phẩm, Bán vật phẩm, Bộ sưu tập (màn menu 3 nút), popup NPC (StatePopup loại 4 có giá). Chưa làm: Tôi luyện thường/cao cấp, Kết hợp Orb, Chế biến, Bộ sưu tập (lưới/đăng ký), màn chọn vật phẩm (picker). Các màn đó vẫn chạy ĐƯỜNG GỐC (không crash: `states.def` chỉ bật state 0/1/2 và 7 khi `[this+0xf]==0`).

## File
- `engine/menu/npc.c` (mới): `npc_paint/npc_touch/npc_move`, `npcpop_paint/npcpop_touch`. Dòng `npc`, `npcpop` trong `states.def`; `build.sh` thêm biên dịch npc.c + 4 symbol guest (`GetSearchSkillBook`, `NpcShop::GetItemInfo`, `GetCollectionCompleteTotalCount`, `vtable for StateInNpcMenu`); `mui.h` thêm khai báo + macro `NPC_POPUP`; `bag.c` thêm 2 hàm xuất ở cuối file (`npc_item_info`, `npc_rarity`) để dùng lại hộp thông tin vật phẩm.
- Harness `harness/t17c5/`: `check_c5.py` (kịch bản phím gốc vs chạm mới), `cmp_c5.py` (so kết quả), `cmp_hits.py` (so `hit_rects.json`), `q2.py` (dò trường theo phím/chạm, nhiều kịch bản 1 lần boot), `q3.py/trace.py/q4.py` (dò lời gọi hàm guest). Vào NPC trong harness: `BaseState::CallNpcMenuState(0, type, shopId)` = `r.call(0x42b08, 0, type, 1)` từ cảnh chơi (khung ~1000), an toàn (đã dùng ở T17a). Lưu ý: hook mã guest trong unicorn cần `uc.ctl_flush_tb()` sau `hook_add`.
- WORK `~/hl5work_l2` (apk, vi_android, build_apk, so.dis chép từ l0; `t17c2/dev` do `harness/t17c2/build_dev.sh`).

## Giải mã (dò động bằng q2.py, đối chiếu phím gốc)
Trường StateInNpcMenu: `+0xe` state (0 Mua kỹ năng, 1 Mua vật phẩm, 2 Bán, 3/4 Tôi luyện thường/cao cấp, 5 Orb, 6 Chế biến, 7 Bộ sưu tập), `+0xf` chế độ con (`GetSubState`).
- State 0: `[0x10]` ô 0..15 (4x4, UP/DOWN +-4 vòng 16, LEFT/RIGHT vòng trong hàng); chỉ ô 0..9 hợp lệ. Sách = `NPC::GetSearchSkillBook(buf)` -> `buf[ô]` = id, `ItemTable::GetItemTableInfo(tbl, out, 16 + lớp/2, id)`. Giá = `ItemInfo+0x30`. KeySkillBuy: tiền < giá -> popup CT90, ngược lại popup CT91 có giá.
- State 1: `[0xf]` 0 chọn tab / 1 lưới; `[0x10]` tab 0..2; `[0x11]=[0x12]` ô 0..15 (không cuộn, vòng 16). Danh sách = `NpcShop::GetItemInfo(shop=*(gv+5140), out, slot)` (slot 0..n+5, trả 0 nếu hết); tab theo loại vật phẩm `ItemInfo+0x14`: <=4 Vũ khí, 5..10 Giáp, >=11 Khác (đã đối chiếu bằng hook `ItemTable::GetItemTableInfo` trong DrawItemBuy).
- State 2: `[0xf]` 0 chọn trang / 1 lưới; `[0x10]` trang (`BagItem::GetSize()/16`); `[0x11]` ô 0..15. Giá bán = `ItemInfo+0x30` (0 = không bán được); đang mặc = popup riêng.
- State 7: `[0xf]` 0 menu 3 nút (`[0x10]` 0..2, nhãn CT 269+i), OK -> `[0xf] = 1 + [0x10]`. Dòng tổng: nhãn CT283 + `HERO::GetCollectionCompleteTotalCount()` / tổng = tổng `(s8)gv[6498+42i]`, i=0..9 (từ DrawCollection 0x10d518). Tiêu đề CT284.
- Popup NPC = StatePopup loại 4: `+0x20` = GIÁ (int), `+0x24` thông điệp, kết quả `*[+0x14]`, nút `+0x43c+48i`; cha = NPC (`*(u32*)[self+8] == vtable NPC + 8`). Popup loại 1-3 (không đủ tiền, đã mua, không thể bán...) dùng `pop` có sẵn (c1); id nút: npcpop 0xA0/0xA1, pop 0xF0/0xF1/0xF2.
- Cách chạm giống Túi đồ: chạm ô chưa chọn = chọn (state 1/2: vào chế độ lưới), chạm ô đang chọn = `KeyPressed(-5)` gốc (cũng là hành vi chạm của gốc); nút Mua/Bán = vào lưới + OK; chạm tab/trang ghi `[0x10]`; nút menu Bộ sưu tập = đặt `[0x10]` + OK.

## Kiểm
Xem mục "Kết quả" cuối file (số liệu chạy cuối).

## CÒN LẠI (giao công nhân mới, đọc mục "Giải mã")
1. **Tôi luyện thường/cao cấp (state 3/4)**: `[0xf]` 0 hàng / 1 chọn vật phẩm (OpenRefineFilter); `[0x10]` hàng 0 vật phẩm, 1 đá, 2 nút (UP từ 0 -> 2). `HERO::GetRefineItemPtr()` (0x85468) trả struct RefineItem (trường chưa giải mã: dump 0x30 byte khi chưa chọn thấy `[+4]` con trỏ, `[+0x11]=0xff`, `[+0x2a]=0xff`). Chi phí = `RefineItem::GetItemRefineMoney(short)` 0x9c844 = `price + sext(item[0x165]) * price` (price = item+0x30). Cần giải mã tỉ lệ thành công và "Độ tôi luyện" (gốc `00'03'00`, mockup `+3 > +4`) từ DrawRefine 0x1122f0 và `RefineItem::ApplyItemRefine` 0x9e874. **Hero harness mặc định không có vật phẩm tôi luyện được nên picker trả về ngay**: thêm vật phẩm vào túi (`ItemTable::NewItemToBag`) trước khi dò. So RNG: chưa kiểm (chưa dò seed).
2. **Orb (state 5)**: `[0x10]` 0..3 (vật phẩm, Orb, vật hiến tế, nút); `RefineItem::IsOrbCombinableItem/Part`, `ApplyOrbCombine`; mockup có nút "Bỏ vật hiến tế" (gốc: phím `0`).
3. **Chế biến (state 6)**: `[0x10]` ô công thức (4 cột, vòng trong hàng, UP/DOWN +-4), OK -> popup; dữ liệu `HERO::GetMixSmithTableInfoPtr(i)`, `RefineItem::IsHaveNormalMixMaterial`, `ItemTable::NewItem/DeleteItem`.
4. **Bộ sưu tập lưới/đăng ký (state 7, `[0xf]` 1..3 = loại; trong lưới DOWN/OK -> `[0xf]=4`)**: `HERO::IsCollectionComplete(lớp, ô)`, `OpenCollectionFilter`, `SetCollectionComplete`.
5. **Màn chọn vật phẩm (picker, `[0xf]==1` của state 3/4/5/7)**: dựa `paint_picker` của bag.c.
6. Popup NPC loại 5 (nhập số lượng; `SetPopupState` 0x1093d8) chưa làm (giữ đường gốc).
7. Chưa làm: ô sách kỹ năng không vẽ biểu tượng khoá (gốc vẽ khoá, công thức chưa giải mã, không phải `tiền < giá`); hộp thông tin sách kỹ năng dùng `CommonUi::DrawItemDiscription` (không có hàng "Cần cấp" riêng như mockup).

## CÒN LẠI: (các bước cuối chưa xong khi dừng; file đang sửa: engine/menu/npc.c, đã sửa chỉnh vùng chạm tab con/nút bộ sưu tập NHƯNG CHƯA build lại)
1. `export WORK=~/hl5work_l2; harness/t17c2/build_dev.sh` rồi `python3 -I harness/t17c5/check_c5.py 400 240 new OUT/new` và `... orig OUT/orig`, `python3 harness/t17c5/cmp_c5.py OUT 400x240`, `python3 harness/t17c5/cmp_hits.py OUT/new 400x240` (cũng 1200x540). Lần chạy gần nhất (trước chỉnh): mọi kịch bản ĐẠT (skill_buy, skill_nomoney, item_buy, item_buy_tab1, item_buy_no, sale, sale_page) trừ 2 kịch bản coll_menu/item_buy_btn do lỗi thiết kế kịch bản (đã sửa trong check_c5.py, chưa chạy lại); vùng chạm lệch 6/64 (tab con 1-2 px, nút bộ sưu tập 2 px; đã sửa trong npc.c).
2. Hồi quy c1, c2, c3a, c3b, c3c: script `/tmp/claude-0/regress.sh` (log `/tmp/claude-0/regress.log`) chưa xong khi dừng; chạy lại từng `harness/t17c*/check_*_all.py` với WORK=~/hl5work_l2.
3. Chưa chép ảnh vào `shots/L2/` (ảnh nằm ở /tmp/claude-0/c5/new/raw_400x240/*.png sau lần chạy; scratch có thể mất).
4. Chưa 20 lần mở/đóng NPC (rò) và chưa chạy 1200x540/600x270.
