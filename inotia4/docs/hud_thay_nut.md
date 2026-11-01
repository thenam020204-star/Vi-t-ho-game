# Thay nút HUD màn chơi bằng ảnh của người dùng (T10)

Tài liệu của T10a (RE) và ghi lại T10b/T10c (làm, kiểm). Gộp bàn giao đọc trước của A2 (`docs/t10a_bangiao_A2.md`, giữ nguyên làm dữ liệu gốc) với phần công nhân T10 kiểm lại bằng byte/disasm.
Quyết định thiết kế nằm ở `PLAN.md` (mục T10, "Quyết định của quản đốc cho T10"); tài liệu này không đổi chúng.
Mọi địa chỉ, byte và bảng loc dưới đây do `scripts/re/hud_check.py` dựng lại độc lập trên `libgame.so` của TEST4 (sha256 `df12cecc…5d02`), của TEST11 (`d5af7d0a…1f98`) và của APK T10; log `logs/t10a_hud_check.log`, cả 3 lần **TẤT CẢ ĐẠT**.

Lệnh tái hiện:

```
python3 -I scripts/re/hud_check.py <libgame.so> [--atlas-dir <game_res>]      # T10a: kiểm bảng loc, kiểu vẽ, 3 lệnh
python3 -I scripts/hud_swap.py --selftest --lib <libgame.so>                  # T10b: selftest atlas (mặc định lấy atlas từ test_builds/...TEST11)
python3 -I scripts/hud_swap.py <game_res vào> <thư mục ra> [--preview anh.png] # dán 7 ảnh -> UI_014/UI_015
python3 -I scripts/patch_lib.py --list --group hudbtn                         # 3 lệnh kiểu vẽ
APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/Inotia4_T10_check.apk \
   --smali net,mod --vnfont --lib --libgroup base,p1,mod,view,hud,pool,hudbtn --hud --diag
python3 -I scripts/hud_mophong.py <game_res gốc> <game_res mới> docs/hud/mo_phong_hud.png
```

## 1. Tóm tắt

| Việc | Cách |
|---|---|
| Nút HUD nằm ở đâu | 2 atlas PNG RGBA 512×512, alpha thẳng, tên `assets/common/game_res/UI_014.png.jpg` (tấn công) và `UI_015.png.jpg` (các nút tròn, khung ô kỹ năng) |
| Khung nào của atlas | bảng `loc` (x, y, w, h) trong rodata `libgame.so`, đọc qua `IMGSYS_GetLoc` |
| Thay | 7 rect (mục 3), giữ nguyên kích thước ô ⇒ bố cục và vùng chạm không đổi |
| Pipeline | `scripts/hud_swap.py` (Lanczos, căn giữa, xoá phần thừa về trong suốt) + cờ `--hud` của `build_from_apk.sh` |
| Vá lib | nhóm `hudbtn` (3 lệnh `mov w5,#2` → `#1`): lớp "tấn công bấm" vẽ alpha thường thay vì cộng |
| Kết quả | `apkdiff` TEST11 → T10: chỉ khác 2 atlas và `libgame.so` (3 byte) |

## 2. Bằng chứng (kiểm bằng `hud_check.py`, mục [1]–[7])

### 2.1 Tên nhóm và bảng loc [1], [2]

- `IMGSYS_UnitLoad` 0x12ba18 nạp `GRPX_LoadImage(name_table + g·64)`; `name_table` = GOT 0x2bddb8 → 0x2cc370 (64 B/mục); mảng con trỏ nhóm = GOT 0x2bddc0 → 0x2d0ce8 (8 B/mục, 98 nhóm: `IMGSYS_Load` lặp đến `0x310/8`).
  - nhóm **0x15** @0x2cc8b0 = `UI_014.png`, nhóm **0x16** @0x2cc8f0 = `UI_015.png`.
- `IMGSYS_GetLoc` 0x12c3a8: `cmp w0,#0x61`; bảng nhảy byte tại 0xacb60 chọn khối `mov w8,#24; adr x9,<base>; b 0x12c628`.
  - UI_014: stride 24, base **0xaf3cc**, 7 mục (kết thúc đúng tại base của UI_015).
  - UI_015: stride 24, base **0xaf474**, 40 mục (kết thúc đúng tại 0xaf834, base kế tiếp).
  - mỗi mục: x, y, w, h (int32) rồi 2 int32 bằng 0.
- Các mục dùng cho T10 (đọc thẳng từ byte của lib):

| Atlas | loc | (x, y, w, h) | Là gì | T10 |
|---|---|---|---|---|
| UI_015 | 0 | (2, 2, 75, 75) | bánh răng (ID 3) | thay |
| UI_015 | 1 | (80, 2, 75, 75) | lưu (ID 1) | thay |
| UI_015 | 2 | (158, 2, 75, 75) | giỏ hàng (ID 2) | thay |
| UI_015 | 3 | (236, 2, 75, 75) | đổi ↻ (ID 7) | thay |
| UI_015 | 11 | (416, 99, 94, 94) | vòng ô kỹ năng | thay |
| UI_014 | 2 | (281, 128, 159, 160) | nút tấn công | thay |
| UI_014 | 3 | (281, 291, 159, 160) | lớp tấn công bấm | thay |
| UI_015 | 4 / 5 / 10 / 17 | (314,2,75,75) / (416,2,94,94) / (375,81,38,40) / (416,196,94,94) | lớp bấm chung / nền ô / chấm số lượng / ô bấm | giữ |
| UI_014 | 1 | (2, 128, 276, 267) | nền bán nguyệt | giữ |

- Không rect nào trong 7 rect chồng lên rect khác của cùng atlas (kiểm theo cặp, mục [5]); rect đều nằm trong 512×512.
- Bảng loc, tên nhóm và rect khớp từng số với bàn giao của A2.

### 2.2 Điều khiển → loc [6]

`UIPlayPorting_Draw` 0x16985c lặp các control, `ControlObject_GetID` rồi vẽ tại tâm rect (`x22 = x + (w>>1)`, `GRPX_DrawPart` neo 2 trừ lại `w>>1`, nên sprite cùng cỡ rect thì góc trên-trái trùng góc rect):

| ID | Rẽ nhánh | Khối vẽ | loc |
|---|---|---|---|
| 1 | `cmp x22,#1; b.eq 0x16a350` | `GetGroup(0x16)`, `GetLoc(0x16,1)` @0x16a368 | UI_015 loc1 (lưu) |
| 2 | `cmp x22,#2` → 0x16a260 | `GetLoc(0x16,2)` @0x16a278 | loc2 (giỏ) |
| 3 | `cmp x22,#3; b.eq 0x16a404` | `GetLoc(0x16,0)` @0x16a41c | loc0 (bánh răng) |
| 7 | `cmp x22,#7` @0x16a2fc | `GetLoc(0x16,3)` @0x16a318 | loc3 (đổi) |
| 0x14 | `cmp w0,#0x14` @0x169a28 | `GetLoc(0x15,2)` @0x169a44 → `GRPX_DrawPart` kiểu 1 | UI_014 loc2 (tấn công) |

Tên hàm của từng ID (`UIPlay_CallSave`, `CallInAppShop`, `CallMenu`, `ButtonSwap`...) là của A2, chưa kiểm lại ở đây; ánh xạ ID → loc ở bảng trên là bằng chứng byte.
Rect tạo trong `UIPlay_Create` (đã đọc lại cho mô phỏng): ID 1/2/3 = (25, 155|232|309, 75, 75); ID 7 = (W−102, H−343, 75, 75); ID 8..13 = (W−27−tx, H−14−ty, 80, 80) với tx = 462,362,262,249,181,87 và ty = 89,89,89,179,242,245 (rodata 0xac5e0/0xac410/0xac1e0 và 0xac280/0xac1d0, `x27 = x29−0x40`); ID 0x14 = (W−186, H−176, 159, 160); nền bán nguyệt (W−300, H−283).

### 2.3 Kiểu vẽ [3], [4]

- `GRPX_DrawPart` 0x12a43c(x0 nhóm, w1 x, w2 y, x3 loc, w4 neo, w5 kiểu, w6 %): `mov w8,w5` → `[sp,#8]`, `w6` → `[sp,#0x10]`, rồi `bl SGL_DrawTexturePartEx` 0x131340, trong đó `w20 = [x29,#0x68]` (= `[sp,#8]` của người gọi).
- Texture RGBA (`[x19+0x60] == 0x1908`) đi nhánh 0x131790:
  - **kiểu 1** → 0x131858: chỉ `glDrawArrays` (blend giữ `SRC_ALPHA, ONE_MINUS_SRC_ALPHA`);
  - **kiểu 2** → 0x1318c8: `glBlendFunc(0x302 = SRC_ALPHA, 1 = ONE)`, vẽ, rồi trả về `(0x302, 0x303)`.
- `IMGSYS_GetLoc(0x15, 3)` (loc3 của UI_014) có đúng **3 nơi gọi** trong cả `.text` (quét nhị phân mọi `bl IMGSYS_GetLoc@plt` kèm `mov w0`/`mov w1` gần nhất; 539 nơi gọi tổng): 0x169ad0, 0x169b9c, 0x169be4. Cả ba cùng một khuôn:

```
mov w0,#0x15 ; mov w1,#3 ; bl IMGSYS_GetLoc ; mov x3,x0 ; mov x0,x24 ; mov w1,w22 ; mov w2,w23
mov w4,#2    ; mov w5,#2 (<== 45008052) ; mov w6,wzr ; bl GRPX_DrawPart
```

| Lệnh | Nơi gọi | Điều kiện |
|---|---|---|
| 0x169ae8 | 0x169ad0 | nhấp nháy hướng dẫn: `[0x2bec78] != 0`, `[x26] == 4`, `TutorialDescription() != 0`, `bộ đếm % 10 ≤ 3` |
| 0x169bb4 | 0x169b9c | nhấp nháy hướng dẫn: `[0x2bec78] != 0`, `[0x2bf7b0] != 0`, `[x26] == 0xd`, `TutorialDescription() != 0`, `bộ đếm % 10 ≤ 3` |
| 0x169bfc | 0x169be4 | lớp bấm thật: `ControlObject_GetValue(control ID 0x14) == 1` |

  `45008052` = `movz w5,#2`; `25008052` = `movz w5,#1` (dựng bằng `llvm-mc`). Chỉ đổi 1 byte (0x45 → 0x25) mỗi chỗ.
- Còn 16 lời gọi `GRPX_DrawPart` khác trong `UIPlayPorting_Draw` (0x16985c..0x16b200) có `mov w5,#k; mov w6,wzr`: kiểu 1 ở 14 nơi, kiểu 2 ở 0x16a0e4 và 0x16a52c (lớp bấm của loc4/loc17, **không đổi**).

### 2.4 Thứ tự vẽ ô kỹ năng [7]

Khung ô mới (loc11) có lõi tối gần đặc (alpha 250), nên phải vẽ **trước** icon. Cả 3 nơi vẽ ô kỹ năng đều theo thứ tự nền loc5 → vòng loc11 → icon (`CONVERTSKILL_GetIconGroup`):
`SHORTCUTPorting_Draw` (0x1d0e1c < 0x1d0e54 < 0x1d0e98), `UIShortcutMenu_Draw` (0x170360 < 0x170398 < 0x170414), `UIShortcutMenu_DrawShortcutControl` (0x1705c8 < 0x170600 < 0x170640). Vậy khung đặc không che icon (kể cả ở popup gán phím tắt).

## 3. 7 rect thay và cách thu (`scripts/hud_swap.py`)

| Atlas, loc | Rect | Ảnh người dùng | Cách |
|---|---|---|---|
| UI_015 loc0 | (2, 2, 75, 75) | `btn_pause.png` 116² | Lanczos về 73 px, căn giữa (lệch 1 px mỗi bên) |
| UI_015 loc1 | (80, 2, 75, 75) | `btn_save.png` 116² | như trên |
| UI_015 loc2 | (158, 2, 75, 75) | `btn_shop.png` 116² | như trên |
| UI_015 loc3 | (236, 2, 75, 75) | `btn_swap.png` 186² | như trên |
| UI_015 loc11 | (416, 99, 94, 94) | `btn_frame.png` 116² | Lanczos về 90 px, căn giữa (lệch 2 px mỗi bên) |
| UI_014 loc2 | (281, 128, 159, 160) | `attack.png` 512² | cửa sổ 159/0,34 × 160/0,34 = 467,65 × 470,59 quanh (255, 258) của canvas → 159×160 |
| UI_014 loc3 | (281, 291, 159, 160) | `attack_pressed.png` 512² | cùng cửa sổ |

- `PIL.Image.resize(LANCZOS)` trên RGBA: Pillow tự nhân alpha trước khi lọc rồi trả về alpha **thẳng** (không quầng đen/trắng ở mép). Atlas là alpha thẳng (`DecodePNG` không nhân alpha, lúc vẽ `SRC_ALPHA, ONE_MINUS_SRC_ALPHA`), nên ra đúng định dạng atlas.
- Cả rect được **thay** (không alpha-composite): phần ngoài ảnh mới trong rect thành `(0,0,0,0)`, không để lộ sprite cũ. Không làm "alpha bleed" (RGB của pixel alpha 0 là 0); atlas gốc có pixel alpha 0 mang RGB khác 0, ảnh hưởng chỉ ở mép nếu texture lọc tuyến tính.
- Kiểm đầu vào: SHA256 hai atlas gốc (`56274c88…8411`, `e05d2a29…6574`) và 7 ảnh người dùng theo `assets_user/hud/SHA256.txt` (sai ⇒ dừng); kích thước ảnh người dùng (116², 186², 512²) và chế độ RGBA.
- Giữ nguyên: nền bán nguyệt, loc4/5/10/17 và mọi sprite khác, thanh HP/MP/EXP. Pixel ngoài 7 rect giống hệt gốc.
- Atlas ra: PNG RGBA 8 bit không xen kẽ, chunk `IHDR`, `iCCP`, `IDAT`, `IEND` (gốc có thêm `pHYs`, `cHRM`; hồ sơ màu `iCCP` giữ nội dung y hệt, chỉ tên "ICC Profile" thay "Photoshop ICC profile"); libpng đọc bằng `png_read_png` (`DecodePNG` 0x23bed8) nên không cần giống từng chunk.

| File | Gốc | Mới |
|---|---|---|
| `UI_014.png.jpg` | 79.060 B, `56274c88…8411` | 132.023 B, `d541527a4bae960dab8e0f48fc355215a529d7838e4876b99c83bd4b4505cf70` |
| `UI_015.png.jpg` | 157.435 B, `e05d2a29…6574` | 192.288 B, `a04b7cc8ad19e0b8e6660719f74dccc7b2dd7275f059e4b142271af638d82606` |

### Selftest (`logs/t10b_hud_swap_selftest.log`, chạy 2 lần, 0 mục KHÔNG ĐẠT)

1. SHA256 vào (2 atlas + 7 ảnh người dùng).
2. Pixel ngoài 7 rect == gốc, từng pixel (UI_014: 211.264 pixel ngoài, 46.964/50.880 pixel trong rect đổi; UI_015: 230.808 ngoài, 27.054/31.336 trong rect đổi).
3. Trong từng rect: nội dung == nội dung đã dựng; mọi pixel ngoài vùng ảnh mới == `(0,0,0,0)` (296 px cho ô 75, 736 px cho ô 94, 0 cho 2 rect tấn công); alpha tối đa 255; màu trung bình theo alpha khớp ảnh người dùng (sai số < 8/255 mỗi kênh).
4. Chạy lần 2 ra cùng từng byte (2 atlas).
5. Atlas không có việc ⇒ chép nguyên byte (UI_014 khi chỉ có việc của UI_015; cả hai khi không có việc nào).
6. PNG ra hợp lệ: IHDR `(512, 512, 8, 6, 0, 0, 0)`, giải mã lại được.
7. Với `--lib`: 7 rect == bảng loc đọc thẳng từ `libgame.so` (build cũng chạy bước này trên lib chưa vá và dừng nếu lệch).

Ảnh đối chiếu: `docs/hud/sau_thay.png` (atlas trước/sau, khung xanh 7 rect, từng nút gốc ↔ mới phóng ×3 với ô nhỏ), `docs/hud/so_sanh_goc_moi.png` và `mock_attack_goc_moi.png` (đề xuất của A2), `goc_atlas_UI_014_loc.png`, `goc_atlas_UI_015_loc.png`.

## 4. Nhóm lib `hudbtn` (`scripts/patch_lib.py`)

- 3 patch, mỗi patch 4 B (`45008052` → `25008052`), kiểm cả ngữ cảnh: lệnh trước `mov w4,#2` (`44008052`), lệnh sau `mov w6,wzr` (`e6031f2a`) và `bl GRPX_DrawPart@plt` (dựng bằng `bl()` từ 0x2a89a0). Sai byte gốc, sai ngữ cảnh, hoặc nhóm ở trạng thái lệch (một chỗ đã vá, hai chỗ chưa) ⇒ dừng không ghi gì (đã thử cả 3 phép thử âm tính; áp lần 2 ⇒ 0 patch mới).
- **Không nằm trong nhóm mặc định** (`DEFAULT_GROUPS` = `base,p1,mod,view,hud,pool`; `rebuild.sh` và mọi lệnh cũ không đổi, áp mặc định vẫn ra `d5af7d0a…1f98`): vì nhóm chỉ có nghĩa cùng atlas mới. `build_from_apk.sh` ràng buộc `--hud` ⇔ nhóm `hudbtn` trong `--libgroup` (thiếu một vế thì dừng).
- Lý do vá: ảnh `attack_pressed` của người dùng là hình đủ màu. Vẽ kiểu cộng (`SRC_ALPHA, ONE`) làm nó cháy sáng (xem 3 ô cuối của `docs/hud/mo_phong_hud.png`); vẽ alpha thường thì lớp bấm che hẳn nút thường.
- Hệ quả: khi bấm tấn công và ở 2 chỗ nhấp nháy hướng dẫn (tutorial), nút thành `attack_pressed` đè lên `attack` (alpha thường).
- `--check-asm` dựng lại 34 lệnh đơn `hud`/`pool`/`hudbtn` bằng `llvm-mc`: khớp. Phụ: sửa lỗi cũ của `--diff ... -v` (cờ `-v` làm hỏng kiểm số đối số).

## 5. Ảnh hưởng phụ (chấp nhận theo quyết định của quản đốc)

- Popup gán phím tắt (`UIShortcutMenu_*`, mở từ màn Trang bị/Kỹ năng) cũng vẽ UI_015 loc3 (↻), loc5, loc11, loc17 ⇒ đổi theo ảnh mới (cùng phong cách).
- `SHORTCUTPorting_Draw` (+`_CoolPie`, `TutorialSkillButtonDraw`) dùng loc5/11/17/24/25 ⇒ ô kỹ năng và ô bình máu đều có khung mới (vẫn do game vẽ icon/số lượng đè lên).
- Lớp bấm chung loc4 (vòng cam, cộng) của 4 nút 75 px và loc17 của ô kỹ năng **không đổi**: vòng sáng cam của game sẽ chồng lên nút mới khi bấm (cùng tông cam/vàng với khung mới).
- Atlas lớn hơn: UI_014 +53 KB, UI_015 +35 KB (do ảnh có nhiều chi tiết hơn). Kích thước APK T10 50.085.687 B so với 50.009.663 B của TEST11.

## 6. Kết quả build T10c (`out/Inotia4_T10_check.apk`, không commit)

- Lệnh ở đầu tài liệu; log đầy đủ `logs/t10c_apk_check.txt`.
- sha256 `2692c8ca4ae61b194cb38e713d5e7ba04c142db196437ba0364cf370f09e37e6`, 50.085.687 B.
- `apkdiff` với `test_builds/Inotia4_TEST11_vi_offline.apk`: không có mục chỉ ở một bên; khác nội dung đúng 3/1454 file chung: `assets/common/game_res/UI_014.png.jpg`, `UI_015.png.jpg`, `lib/arm64-v8a/libgame.so`.
- `patch_lib --diff` (nhóm đủ + `hudbtn`) TEST11 → T10: 3 byte khác, 3 đoạn, tại 0x169ae8, 0x169bb4, 0x169bfc, tất cả trong vùng khai báo. Lib trong APK == lib vá từ TEST4 với nhóm đủ (`442cd19c…9494`).
- `apksigner verify`: v1/v2/v3 đạt, cùng chứng chỉ NAMP `cc559651…20d4`; `zipalign -c -P 16 4`: đạt; `check_apk.py`: PASS; atlas trong APK == bản `hud_swap` (STORED).
- Mô phỏng: `docs/hud/mo_phong_hud.png` (1698×1712): HUD 1422×640 sau lề T9, bản mới và bản gốc, rồi vùng nút tấn công ở 5 trạng thái (gốc thường, gốc bấm cộng, mới thường, mới bấm cộng = cháy sáng, mới bấm alpha). Đây là **mô phỏng bằng Python** theo rect của `UIPlay_Create`, không phải ảnh chụp game: chân dung, thanh mục tiêu, minimap và cần điều khiển chỉ là khung gạch đứt (minimap và cần: kích thước minh hoạ).

## 7. CHƯA XÁC ĐỊNH

- Hình ảnh thật trong màn chơi (không chạy tới màn chơi trong unidbg: lỗi T2; chưa thử trên máy thật).
- Texture atlas lọc tuyến tính hay gần nhất (ảnh hưởng độ mượt mép); chưa đọc `SGL_Texture::Create` cho atlas UI.
- Ý nghĩa động của `data+0x68` (lớp bấm của 4 nút 75 px dùng nó), A2 chưa xác định.
- Cảm giác khi nhấp nháy hướng dẫn (tutorial) với lớp bấm vẽ alpha thường (hai chỗ 0x169ae8, 0x169bb4): chưa thấy chạy.
- Tên hàm của từng ID control (`UIPlay_CallSave`...) là của A2; chỉ ánh xạ ID → loc được kiểm bằng byte.
- Kích thước thật của minimap (ID 5/6) và cần điều khiển (0x15) trong mô phỏng.
