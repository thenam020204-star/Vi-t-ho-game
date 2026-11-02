# T10a: bàn giao đọc trước của A2 (2026-11-01, T10a ~92%, chỉ đọc)

Đây là dữ liệu bàn giao. Quyết định của quản đốc nằm ở PLAN.md, mục T10, và được ưu tiên hơn file này.

Ảnh đối chiếu trong `docs/hud/`:
- `so_sanh_goc_moi.png`
- `mock_attack_goc_moi.png`
- `goc_atlas_UI_014_loc.png`, `goc_atlas_UI_015_loc.png`

Bản đầy đủ cùng script tạm nằm ở `/opt/work/t10/` (mất khi container bị thu hồi).

## 1. Sprite HUD gốc (`UIPlayPorting_Draw` 0x16985c)

- `IMGSYS_GetGroup(g)` lấy tên từ bảng 64 B tại 0x2cc370: nhóm 0x15 = `UI_014.png`, nhóm 0x16 = `UI_015.png`.
- `IMGSYS_GetLoc(g,i)` = base + i·24, base lấy từ jump table 0x12c398. Base của UI_014 là 0xaf3cc, của UI_015 là 0xaf474. Mỗi mục gồm x, y, w, h (int32), 0, 0.
- `GRPX_DrawPart(nhóm, x, y, loc, neo, kiểu, %)`:
  - neo 2 = vẽ theo tâm;
  - kiểu 1 = alpha thường;
  - kiểu 2 = cộng (`glBlendFunc(SRC_ALPHA, ONE)` tại 0x1318c8).
- Hàm của từng control: ID1 `UIPlay_CallSave`, ID2 `CallInAppShop`, ID3 `CallMenu`, ID7 `ButtonSwap`, ID 0x14 `ButtonOK`, ID 8..13 `ButtonSKill`.

| Nút | Atlas | loc | x,y | w×h | Lúc bấm |
|---|---|---|---|---|---|
| Lưu ID1 | UI_015 | 1 | 80,2 | 75×75 | loc4 cộng đè lên (byte `data+0x68==1`) |
| Giỏ ID2 | UI_015 | 2 | 158,2 | 75×75 | như trên |
| Bánh răng ID3 | UI_015 | 0 | 2,2 | 75×75 | như trên |
| Đổi ↻ ID7 | UI_015 | 3 | 236,2 | 75×75 | loc4 khi `GetValue==1` |
| Lớp bấm chung | UI_015 | 4 | 314,2 | 75×75 | vòng cam, vẽ cộng: GIỮ |
| Ô kỹ năng: nền | UI_015 | 5 | 416,2 | 94×94 | đĩa tối, alpha ≤128: GIỮ |
| Ô kỹ năng: vòng | UI_015 | 11 | 416,99 | 94×94 | vẽ sau nền, trước icon |
| Ô kỹ năng: bấm | UI_015 | 17 | 416,196 | 94×94 | vòng sáng, vẽ cộng: GIỮ |
| Chấm số lượng | UI_015 | 10 | 375,81 | 38×40 | GIỮ |
| Tấn công | UI_014 | 2 | 281,128 | 159×160 | vẽ tại tâm rect (0x169a44) |
| Tấn công bấm | UI_014 | 3 | 281,291 | 159×160 | vẽ ĐÈ lên loc2 bằng kiểu cộng (0x169bf8) |
| Nền bán nguyệt | UI_014 | 1 | 2,128 | 276×267 | alpha ≤95: GIỮ |

- Icon kỹ năng thuộc nhóm 0x20..0x23 (`UI_iconskill75_*`): đĩa 75 px, độ đục 80%.
- Ý nghĩa động của `data+0x68`: CHƯA XÁC ĐỊNH (mới đọc tĩnh, 0x1471d4..dc).

## 2. Định dạng

- `assets/common/game_res/UI_014.png.jpg` và `UI_015.png.jpg` thực chất là PNG thường 512×512, RGBA 8 bit, alpha thẳng (không premultiplied):
  - `DecodePNG` 0x23bed8 không nhân alpha;
  - lúc vẽ dùng `SRC_ALPHA, ONE_MINUS_SRC_ALPHA`.
- Bảng frame nằm trong rodata của libgame, không nằm trong file.
- SHA256 gốc: UI_014 `56274c88…8411`, UI_015 `e05d2a29…6574`.
- `img_vi.py` chưa dán được ảnh vào rect ⇒ cần script mới `scripts/hud_swap.py`, và cờ `--hud` trong `build_from_apk.sh`.
- PIL ghi lại PNG cho pixel giống hệt nhưng byte không giống ⇒ selftest so pixel ngoài các rect; atlas không đụng tới thì chép nguyên byte.

## 3. Dùng chung

- UI_014: chỉ `UIPlayPorting_Draw` dùng.
- UI_015 còn được dùng ở:
  - `SHORTCUTPorting_Draw` (+`_CoolPie`, `TutorialSkillButtonDraw`): loc 5, 11, 17, 24, 25;
  - `UIShortcutMenu_*` (popup gán phím tắt, mở từ màn Trang bị/Kỹ năng): loc 3, 4, 5, 11, 17;
  - thanh HP/MP/EXP: loc 28..38.
- ⇒ Popup gán phím tắt sẽ đổi theo: hình ↻ và khung ô. Màn khác không dùng (mới quét tĩnh).

## 4. Đề xuất

- Nút 75×75: thu Lanczos, giữ alpha. Đĩa gốc 71 px trong ô 75; khoảng cách giữa các rect cột trái là 77.
- `btn_frame` → loc11 (94×94):
  - thứ tự vẽ: nền → vòng → icon, nên khung không che icon;
  - ô gần nhất cách 90,9 px tâm-tâm ⇒ thu về 90 px, căn giữa;
  - lõi khung mới tối đặc (alpha 250) ⇒ ô trống thành đĩa tối đặc.
- `attack` / `attack_pressed`:
  - hai ảnh trùng toạ độ ⇒ cắt cùng một cửa sổ;
  - tỉ lệ 0,34: cửa sổ 468×471 quanh (255,258) của canvas 512 → 159×160, đĩa ≈127 px (gốc 135); phần alpha bị cắt 0,02%;
  - tỉ lệ 0,35 cắt vào tia lửa (alpha 126).
- Lớp bấm kiểu cộng làm ảnh bấm đủ màu bị cháy sáng (`mock_attack_goc_moi.png`, ô 3). Đề xuất vá 3 lệnh `mov w5,#2` → `mov w5,#1`, byte `45008052` → `25008052`:
  - 0x169bfc: tấn công lúc bấm;
  - 0x169ae8 và 0x169bb4: nhấp nháy hướng dẫn trong tutorial.
