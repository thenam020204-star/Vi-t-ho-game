# BAO-CAO-17b — T17c3b Đặc tính (InGameMenu tab 5) — để quản đốc gộp làm §17.2

Công nhân A2 (Sonnet). Code: `engine/menu/property.c`. Harness: `harness/t17c3b/` (`build_dev.sh`, `check_c3b.py`, `check_c3b_all.py`, `qlib3.py`, `c3lib.py`; thư viện c2 chép sang để dùng `WORK=~/hl5work_c3b`). Ảnh: `shots/T17c3/dac_tinh_*` (4 ảnh) + `shots/T17c3/check_c3b.txt` (kết quả kiểm).

## 17.2.1 Trạng thái gốc của tab Đặc tính (dò động + KeyPropertyMenu 0x102bc4, DrawPropertyMenu 0xecfa8)
`StateInGameMenu` tab 5 (`GS(self)==5`). Trường `this`:
- `+0xd` sub: **0** = hàng tab nhánh (TRÁI/PHẢI đổi nhánh), **1..3** = đang ở lưới của nhánh (`sub = nhánh+1`). Phím XUỐNG ở sub 0 = `SetSubState(nhánh+1)`, ghi `gv[6464+19]=gv[6464+32]=0`, con trỏ = kỹ năng có **id nhỏ nhất** nằm ở ô 0..2 của nhánh (nhánh 0 chỗ chiến binh: ô 1). Phím LÊN ở hàng 0 của lưới = quay lại sub 0, con trỏ 0.
- `+0xe` nhánh 0..2 (đổi nhánh: `+0xf=+0x10=0`).
- `+0xf` vị trí con trỏ **trong cửa sổ nhìn** (0..11 = 3*hàng+cột), `+0x10` chỉ số **tuyệt đối** trong nhánh (0..17). Hàng đầu cửa sổ = `(+0x10 − +0xf)/3` ∈ 0..2. Lưới thật có **6 hàng × 3 cột** (nhìn 4 hàng, cuộn theo hàng; mockup vẽ thanh cuộn tổng 5 là xấp xỉ, bản cài dùng 6/4). Nhãn hàng = `4*hàng` điểm nhánh (0,4,8,12,16,20).
- OK (`-5`) ở sub 1..3: `KeyPropertyMenu` tự kiểm điều kiện rồi **`SetPopupState`** → `StatePopup` loại 7 (menu dọc 1 nút "Đầu tư", đã có widget popup c1); kết quả nút 0 = Process gốc tăng cấp. Không đủ điều kiện (điểm = 0, thiếu điểm nhánh, thiếu tiền đề, đã tối đa) thì KHÔNG có popup, trạng thái giữ nguyên (đã kiểm).

## 17.2.2 Dữ liệu (không dựng lại luật; đọc đúng như gốc)
- `HeroSkillInfo(i)`, i = 10..42 (`HERO::GetHeroSkillInfoPtr(i)` 0x85064): `+4` con trỏ tên (chuỗi mã game), `+8` cấp tối đa, `+9` vị trí = `nhánh*18 + ô`, `+10` điểm nhánh tối thiểu, `+11` kỹ năng tiền đề (cấp phải > 0). Nhánh 0 = kỹ năng 10..20, nhánh 1 = 21..31, nhánh 2 = 32..42 (lớp 0; dựng bảng ô→kỹ năng theo `+9` nên không phụ thuộc lớp).
- Cấp hiện tại = `HERO[588+i]` (s8). Điểm còn lại = **s16 `HERO+754`** ("Điểm N" ở tab thứ 4); điểm đã đầu tư nhánh b = **s16 `HERO+756+2b`**. Icon = `(s8)` `HERO` vtable `+0x24`(i) (như belt của c2) → `mui_cell_i`.
- Điều kiện còn đầu tư được (hiện dấu "+", nút "Đầu tư" sáng): `[754] > 0` và `[756+2b] >= info[10]` và `HERO[588+info[11]] > 0` và `info[8] > HERO[588+i]` (đối chiếu DrawPropertyMenu 0xed6xx / KeyPropertyMenu).
- Tên tab nhánh = `CT(68+3*lớp+i)`, tab thứ 4 = `CT(14)` + " " + điểm (ô chỉ nuốt chạm); tiêu đề = `CT(4)`; "Đầu tư nhánh" = `CT(81)` + số vàng; nút = `CT(82)`; meta "LV a/b" = `CT(13)`.
- **Mô tả kỹ năng = `CommonUi::GetSkillDiscription(char* out, signed char -1, HeroSkillInfo*)` 0x76c08** (this = CommonUi, gọi trực tiếp, KHÔNG cần chế độ "bắt" như vật phẩm). Trả chuỗi đã thay `#NN` bằng số/"20%"…, còn mã màu BFont. Đệm 600 byte (tĩnh).
- Điểm/điểm nhánh bị game tính lại **mỗi khung** (`Formula::calc` 0x93e68/0x93e90/0x93ec0, `strh r0,[r4,r3]`), nên harness phải chèn hook mã (đặt `r0`, + `uc.ctl_flush_tb()`), ghi bộ nhớ không đủ.

## 17.2.3 Chạm → trường gốc (kiến trúc A, KeyPressed/Process/SetPopupState gốc)
| Chạm | Hành động |
|---|---|
| tab nhánh i (0..2) | (nếu sub>0: `SetSubState(0)`) rồi `[+0xe]=i, [+0xf]=[+0x10]=0` = chuỗi phím gốc XUỐNG..LÊN + TRÁI/PHẢI |
| ô (hàng cửa sổ r, cột c) khi sub 0 | vào lưới như XUỐNG gốc rồi đặt con trỏ `[+0xf]=3r+c, [+0x10]=3r+c` |
| ô khi sub>0, ô chưa chọn | `[+0xf]=3r+c`, `[+0x10]=3*(đầu cửa sổ+r)+c` |
| ô đã chọn (chạm lần 2) / nút "Đầu tư" | `KeyPressed(-5)` gốc → popup xác nhận gốc (loại 7) |
| kéo dọc lưới | đổi hàng đầu cửa sổ (0..2): ghi `[+0x10]` (kẹp con trỏ vào cửa sổ mới) và `[+0xf]=[+0x10]−3·đầu`; vào lưới nếu đang ở hàng tab. (Khác phím gốc: phím XUỐNG đẩy ÔNG con trỏ; kéo giữ ô. Cả hai cho trường hợp lệ) |
| kéo dọc hộp mô tả | cuộn px của mô tả (cục bộ, không đổi trường gốc) |
| tab biểu tượng dưới, nút "<" | như c1 (`SetState` / `KeyPressed(-16)`) |

Id vùng chạm: `1` đóng, `0x10+i` tab dưới, `0x30+i` nhánh (0x33 = ô "Điểm", chỉ nuốt), `0x6F` vùng kéo lưới, `0xA0+3r+c` 12 ô cửa sổ (kể cả ô trống), `0xC8` nút Đầu tư, `0xE0` kéo mô tả, `0xE1` hộp chi tiết. Hình học theo `mock_b2a.py` (`prop_geom`, `w_subtabs`, `pips`, `w_info`); thêm so với mockup: đường nối tiền đề (như `DrawPropertyLine` gốc), dấu "+" khi còn đầu tư được, chấm cấp theo cấp tối đa thật (≤5).

## 17.2.4 Kết quả kiểm (`shots/T17c3/check_c3b.txt`, 3 cỡ khung × mới/gốc)
KẾT LUẬN: ĐẠT. Chạm == phím gốc về trường `+0xc..+0x11`, lớp state, cấp kỹ năng, điểm, tệp lưu (md5) cho: đổi nhánh, vào lưới, chọn ô hàng 3 + đổi cột, đầu tư (chạm ô 2 lần và nút "Đầu tư", qua popup Có), đầu tư không đủ điều kiện (không popup, không đổi), nút "<"; kéo cuộn đưa hàng đầu cửa sổ 1/2/0; 9 tab dưới → state 1..9 như gốc; vùng chạm == `hit_rects.json` (`dac_tinh_*`, 27 mục/khung ở cả 400x240, 600x270, 1200x540); mở/đóng 20 lần: allocation sống +80 (gốc +80), heap top +0, ảnh cuối trùng gốc. check_c1 + check_c2 chạy lại với bản dựng có `property.c`: xem mục 17.2.6.

## 17.2.5 Xác nhận màu mã `}` `{` và `#NN` (dùng chung cho c3c Kỹ năng)
- `}` = (0,103,243) xanh dương, `{` = (255,255,255) trắng (kết thúc màu), `|` = (128,128,128) xám: đúng bảng BFont thật trong BAO-CAO §? (`text.c: RC_COL`), KHÔNG phải vàng/xanh như mockup giả định (`parse_rich`). Đã vẽ mô tả kỹ năng bằng bảng thật: số/từ khoá nhấn (`}...|`) thành xanh dương, phần sau `|` hiện xám (ảnh `dac_tinh_chon_*`: đo điểm ảnh (0,100,240) / (128,128,128) / (240,240,224) sau lọc màu). Nếu muốn đọc rõ hơn trên nền tối thì đổi bảng ở `text.c` (file chung, đề xuất cho quản đốc, không làm ở đây).
- `#NN`: KHÔNG phải mã vẽ; `CommonUi::GetSkillDiscription` thay `#NN` bằng số/phần trăm thật (vd "}8 giây|", "}20%|", "}10%|") ngay trong chuỗi trả về. Không cần xử lý riêng; dùng thẳng getter này cho c3c (mô tả Kỹ năng).
- Mô tả kỹ năng dùng `;` như dấu cách/xuống dòng mềm (`mui_wrap` đã coi `;` là dấu cách).

## 17.2.6 Hồi quy check_c1 + check_c2
Chạy lại bằng bản dựng có `property.c` (WORK=~/hl5work_c3b; chuẩn gốc dùng lại từ c2): `check_c2_all.py` KẾT LUẬN: ĐẠT, `check_c1_all.py` KẾT LUẬN: ĐẠT (bản sao script trỏ WORK mới, không ghi `shots/T17c1|T17c2`). Hai script gốc trong `harness/t17c2/` có đường dẫn `~/hl5work_c2` cứng; công nhân sau nên đọc `WORK` từ môi trường.

## 17.2.7 Đề xuất widget chung (không sửa file chung; bản cục bộ trong `property.c`)
1. `subtab()` (tab con chữ n ô, cam khi chọn) + `pips()` (chấm cấp) + `plus_mark()`: c3c (Kỹ năng) cần y hệt (5 tab con + chấm cấp) → nên nâng lên `widgets.c` sau khi gộp (hiện c3c tự làm bản riêng hoặc chép từ `property.c`).
2. Popup loại 7 của StateInGameMenu chỉ có tiêu đề (tên vật phẩm) cho tab Túi/Trang bị (`pop_paint`): với Đặc tính popup không có tiêu đề. Có thể thêm tên kỹ năng + "LV a→a+1" (cần sửa `popup.c` chung: không làm).
3. Bảng màu `RC_COL['|']`: xem 17.2.5.

## 17.2.8 Dòng đã thêm vào file dùng chung
- `engine/menu/states.def`: 2 dòng (chú thích + `MUI_STATE(INGAME, prop, (GS(self) == 5), prop_paint, prop_touch, prop_move)`), trước khối `#ifdef MUI_DEBUG`.
- `engine/menu/build.sh`: vòng biên dịch `for f in property` (trước khối địa chỉ guest) + vòng `--defsym` cho 2 hàm guest (`HERO::GetHeroSkillInfoPtr(signed char)`, `CommonUi::GetSkillDiscription(char*, signed char, HeroSkillInfo*)`) trước dòng `vn_ensure`.
- Không đụng `classes.def`, `mui.h`, `widgets.c`, `inject_menu.py`, `so_patches.tsv`, `caves.tsv` (không điểm móc `bl` mới: chỉ ô vtable INGAME đã có; các dòng `so_patches.tsv`/`caves.tsv` của khối menu do build phát hành `inject_menu.py --update-repo` sinh lại, nội dung không đổi vì lớp INGAME đã bật).

## CÒN LẠI
Không còn việc trong phạm vi T17c3b. (Khi gộp 3 nhánh: build phát hành `--update-repo`, chạy lại check_c1/check_c2/check_c3b_all, nâng widget chung nếu muốn.)
