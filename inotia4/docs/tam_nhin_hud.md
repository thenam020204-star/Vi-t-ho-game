# Tầm nhìn rộng hơn + HUD lùi vào trong (T9)

Tài liệu của T9a (RE + thiết kế). Gộp hai bàn giao đọc trước của A2 (`docs/t9a_bangiao_A2.md`, giữ nguyên làm dữ liệu gốc) với phần công nhân T9 kiểm lại bằng disasm.
Mọi byte, vaddr và bằng chứng "mã chết" ở đây đã được dựng lại và kiểm bằng `scripts/re/view_check.py` trên `libgame.so` của TEST4
(sha256 `df12cecc7752bcba55157fffe7989cf8f9d8bf6344b211fb3ec41122443a5d02`); log `logs/t9a_view_check.log`, dòng cuối **TẤT CẢ ĐẠT**.
Quyết định thiết kế nằm ở `PLAN.md` (mục T9, "ĐỔI THIẾT KẾ sau T9a"); tài liệu này không đổi chúng.

Lệnh tái hiện:

```
python3 -I scripts/re/view_check.py /opt/work/lib4/libgame.so /opt/work/lib4/dis.txt --apk test_builds/Inotia4_TEST10_vi_offline.apk
bash scripts/asm/build_view_cave.sh            # dựng cave `view` (llvm-mc + ld.lld)
python3 scripts/patch_lib.py --list --group view,hud,pool          # bảng patch (T9b)
```

## 1. Tóm tắt

| Việc | Cách |
|---|---|
| Nhìn thấy nhiều thế giới hơn | nhân khung đệm FB `cfg.W/H` (do `OriginalDisplay_ConfigInfo` 0x147030 tính) với P%; màn logic, UI, chữ giữ nguyên |
| P | int32 @ `0x72f78c` (int thứ 4 của vùng mod menu), chỉ nhận 100..150; ngoài khoảng (kể cả 0) = như game gốc |
| Công thức | `W' = floor(W·P/100) & ~1` (W chẵn), `H' = floor(H·P/100)` |
| Điểm vá | 1 lệnh 0x14709c (`strb w20,[x19]` → `b 0x133454`) + cave 52 B 0x133454..0x133488 (nhóm `view`) |
| Lề HUD | 29 lệnh 1 lệnh/điểm, không cave (nhóm `hud`): lùi vào 24 px ngang, 16 px dọc |
| Pool bộ nhớ | `MEM_Create` trần 8 MB → 10 MB: 0x145730, 0x145734 (nhóm `pool`) |
| Áp dụng | chỉ khi mở lại app (`startClet` đọc cfg đúng 1 lần) |

## 2. Bằng chứng: vùng nhìn thế giới là khung đệm FB, không phải màn logic

- `startClet` 0x17a6f8: `OriginalDisplay_ConfigInfo(-1, cfg)` @0x17a73c → `SGL_grpInitialize(cfg.W, cfg.H, 0)` @0x17a74c (`ldrsh` từ cfg+2 / cfg+4) → `MEM_Create` @0x17a750. Cả `OriginalDisplay_ConfigInfo` lẫn `MEM_Create` chỉ có 1 caller là `startClet` (kiểm trong log mục [5]).
- `OriginalDisplay_ConfigInfo` 0x147030: đọc `GetDeviceWidth/Height` (kích thước thật, W trong w21, H trong w0):
  - W < 480 ⇒ cfg = (480, 320);
  - 480 ≤ W ≤ 799 ⇒ (480, H·480/W);
  - W ≥ 800 ⇒ (512, H·512/W) (`sdiv`, làm tròn xuống);
  - máy 2400×1080 ⇒ **512×230**; ghi `strb w20,[x19]` (cfg+0), `strh w8` (cfg+2 = W), `strh w9` (cfg+4 = H), `stp s1,s0` (cfg+8, +12; tỉ lệ nguyên W/H tính **trước** điểm vá nên không đổi).
- `SGL_grpInitialize` 0x132d6c tạo FB bằng `SGL_Device::CreateImage(fmt, cfg.W, cfg.H)`; `GRP_Initialize` 0x141554 chép W/H của FB vào `GRP_nDisplayW/H` (0x2d7ab4/0x2d7ab8), đặt `GRP_nBPL = 2·W` và gọi `MEM_Malloc(BPL·W) = MEM_Malloc(2·W·W)`.
- `MAP_*` và camera đọc `GRP_nDisplayW/H` (71/63 hàm đọc, đều qua GOT). Màn logic **không** lấy từ cfg: `SGL_grpInitialize` tính nó từ W/H thật của thiết bị `[g_pSGLDevice+8/+0xc]` (0x133010) và lưu ở 0x2d6098/0x2d609c (0x13307c/0x133080); nhân FB không đổi nó.
  - Hệ quả: UI, chữ, HUD giữ cỡ khi đổi P; map hiển thị nhiều hơn.
- Ảnh máy thật khớp FB 512: nhân vật ~170 px thật ≈ 36 px FB × 4,69.

## 3. Thứ tự onResume (Java) so với native

- `MainActivity.onResume` (Java) chạy trước `onSurfaceCreated`; sau `onSurfaceCreated` game chờ 500 ms rồi `queueEventForcedly` → `NATIVE_START` → `nativeStart` → `nativeStartClet` → `startClet`. Ghi 0x72f78c trong `onResume` vì vậy kịp cho lần mở app. Đây là hành vi framework Android (surface chỉ tạo sau khi cửa sổ hiện, tức sau `onResume`), chưa thử trên máy thật: **CHƯA XÁC ĐỊNH** trên Xiaomi/Android 16. Nếu ghi hỏng hay chậm thì P = 0 ⇒ game chạy như gốc, không hại gì.
- `nativeReapplyScreenSize` (→ `SGL_ReapplyScreenSize`, gọi từ `CWrapperKernel.onSurfaceChanged` và `MainActivity.onWindowFocusChanged(true)`) chỉ tính lại màn logic + viewport, **không** tạo lại FB. Đổi P giữa chừng không có tác dụng tới lần mở sau ⇒ mod menu ghi chú "Khởi động lại game để áp dụng".
- `SGL_Androidresume` (0x133a30, gọi từ `resumeClet` 0x17aa88) chỉ đặt viewport và gọi `SGL_InitState`; không đọc cfg, không tạo lại FB.

## 4. Các bảng P → FB (máy 2400×1080: cfg gốc 512×230)

| P | W' (chẵn) | H' | kéo lên màn thật | 2·W'² (MEM_Malloc) | FB W'·H'·2 B |
|---|---|---|---|---|---|
| 100 | 512 | 230 | 4,69 | 524.288 | 235.520 |
| 105 | **536** | 241 | 4,48 | 574.592 | 258.352 |
| 110 | 562 | 253 | 4,27 | 631.688 | 284.372 |
| 115 | 588 | 264 | 4,08 | 691.488 | 310.464 |
| **120 (mặc định)** | 614 | 276 | 3,91 | 753.992 | 338.928 |
| 125 | 640 | 287 | 3,75 | 819.200 | 367.360 |
| 130 | 664 | 299 | 3,61 | 881.792 | 397.072 |
| 135 | 690 | 310 | 3,48 | 952.200 | 427.800 |
| 140 | 716 | 322 | 3,35 | 1.025.312 | 461.104 |
| 145 | 742 | 333 | 3,23 | 1.101.128 | 494.172 |
| 150 | 768 | 345 | 3,12 | 1.179.648 | 529.920 |

- P = 105: 512·105/100 = 537,6 → `udiv` cho 537 → `and w8,w8,#~1` cho **536** (không phải 538: phép `and` luôn làm tròn xuống). Chỉ P = 105, 115, 135, 145 (W·P/100 lẻ) bị chỉnh; các P còn lại không đổi giá trị trước `and`.
- H' không bị chỉnh nên tỉ lệ khung có thể lệch tối đa ~0,4 % (vd. P = 105: 536×241 so với tỉ lệ máy 2,222 ⇒ 2,224). Không nhìn thấy được; chấp nhận.
- Vì sao W phải chẵn: `GRP_nBPL = 2·W`; `SGL_Device::CreateImageBuffer` 0x263b80 đặt pitch `(2W+3)&~3`; upload `glTexSubImage2D` căn 4 byte, không có `glPixelStorei`. W lẻ ⇒ pitch ≠ 2W ⇒ ảnh bị xiên.
- Đường vẽ thật: `SGL_grpFlushLcd` 0x133218 → `SGL_FrameBufferToScreen` 0x1327a4: ortho `(0,W_fb,0,H_fb)` lấy W/H live từ `SGL_Image`+0xc/+0x10, quad phủ viewport; mỗi khung `glTexSubImage2D(0,0,W,H)` (0x1327f8) không có số cứng; texture `SGL_Texture::Create` 0x2647c0 làm tròn lên luỹ thừa 2, UV max = `(W<<16)/POT(W)`, `GL_LINEAR`. Mọi P > 100 làm texture FB 512 → 1024 rộng (RGB565, +0,75 MB GPU; chiều cao ≤ 345 ⇒ vẫn 512).
- `RenderCoreInitialize/Flush`, `CSFB_grpFlushLcd` là **mã chết**: `nativeStart` 0x26edc4 gọi `RenderCoreInitialize` rồi `InitializeApp` 0x26ee04 gắn bảng `__CS_grp*` = CSFB, nhưng `SGL_grpInitialize` (từ `startClet`) ghi đè bảng ở 0x132e38..0x132e70 với `SGL_grpFlushLcd`; `RenderCoreFlush` chỉ có 1 caller (`CSFB_grpFlushLcd` 0x25a864) không còn được gọi. Vì vậy các hằng {240, 320, 400, 640} trong `RenderCore*` không ảnh hưởng.

## 5. Map, camera, ảnh nền, chạm

- Các hằng 0x280 trong `MAP_DrawPartial` (0x1bc71c, 0x1bc774...), `MAP_AddLayerTile`, `MAP_LoadLayer` là stride hàng tile (`hàng·640 + cột·10`), không phải kích thước màn. Khung vẽ là `MAP_nDisplayBTX/BTY/ETX/ETY` (0x2e418c..0x2e4198); xoá màn bằng `GRP_FillRect(0,0,GRP_nDisplayW/H)`. Map hẹp hơn vùng nhìn thì căn giữa, phần thừa tô đen (`MAP_SetInformation` 0x1be718, `MAP_Load` 0x1bf1b8); `MAP_DrawPartial` 0x1bc488..0x1bc4c0 luôn tô đen dải 50 px đáy (như gốc). `MAP_SetFocus` 0x1bf5dc kẹp focus; không có hằng nào phụ thuộc 512.
- Menu: `MENUSTATEUTIL_ResetSimpleMoveType` 0x1f061c rẽ nhánh `H ≥ 320` (0x1f064c) và `H ≥ 220` (0x1f0670): từ P = 140 (H' = 322) tier trượt camera đổi (không crash) ⇒ chấp nhận.
- Ảnh nền toàn màn (`GAMELOADER_DrawBackGround` 0x1ab8f0, `DrawTitle_Title` 0x205b2c): vẽ theo màn logic (thang `GetScalevalue` = H_logic/640, căn giữa bằng `GetResolutionWidth/Height`), không đụng cfg/FB ⇒ không đổi khi đổi P. Vùng chạm màn tiêu đề là nút 960×640 tại (0,0) (không vá, màn logic không đổi).
- Đường chạm: Java `CTouchListener.onTouch` → `CFunction.convertDisplaytoOriginal` (x·480/displayW, y·320/displayH) → native `handleCletEvent_Wrapper` 0x2677f8 → `handleCletEventOriginal2Device` 0x230e14 → `handleCletEventImpl` 0x17a128; tại 0x17a1e4..0x17a230 đổi sang toạ độ UI: `x·SGL_GetScreenWidth()/480`, `y·SGL_GetScreenHeight()/320` (đọc live). Nhóm `hud` sửa rect của control (`ControlObject_SetRect`), nên hình và vùng chạm dời cùng nhau; `UIPlay_ButtonDirection` 0x168e38 dùng `GetAbsoluteRect` của chính các control này.
- Chạm thế giới (chạm lên map để đi): `TouchGameObject_AddMap` 0x14a8fc đặt rect `(0,0,GRP_nDisplayW,GRP_nDisplayH)`; `TouchGameObject_EventMap` 0x14a6fc dùng `ControlObject_FindByPoint(ui, pt.x, pt.y)` rồi toạ độ thế giới = `pt + BTX·16 − BX` (0x14a7dc..0x14a7f4, vào `CHAR_SearchPathAsLocationAndNotify`), tức dùng thẳng toạ độ chạm đã đổi sang đơn vị UI logic như đơn vị FB. Game gốc đã như vậy (UI 1422 px ↔ FB 512 px) và nhóm vá **không đổi** quy tắc đó; vùng chạm map là W'×H' đơn vị UI nên rộng hơn P% so với gốc. Hệ quả cho tap-để-đi (không phải cần điều khiển): **CHƯA XÁC ĐỊNH** trên màn chơi thật (không chạy tới màn chơi trong unidbg: lỗi T2).

## 6. Điểm vá lề HUD (nhóm `hud`, 29 lệnh, lùi 24 px ngang / 16 px dọc, mỗi điểm 1 lệnh, không cave)

Logic 1422×640 (máy 2400×1080). `UIPlay_Create` 0x167f28 tạo các control, mỗi cái gọi `ControlObject_SetRect(obj, x, y, w, h)`; `UIPlayPorting_Draw` 0x16985c vẽ tại tâm rect (`ControlObject_GetRelativeRect`), nên sửa rect thì hình + chạm cùng dời. Ngoại lệ: hình nền bán nguyệt (W−276, H−267) ở `UIPlayPorting_Draw` và minimap (`UIPlay_MakeMinimap`, rect ID 5/6 tại (7,7); `UIPlay_DrawMinimap` đọc `GetAbsoluteRect` của ID 5 nên chỉ cần sửa rect).

| Nhóm | ID | Neo | Vị trí gốc → mới |
|---|---|---|---|
| Cột trái (lưu, giỏ, bánh răng) | 1,2,3 | trái | x = 1 → 25; y = 155/232/309 giữ nguyên |
| Cột phải (chân dung/đồng đội) | 101..103 | phải | x = W−93 → W−117; y = 10/110/210 → 26/126/226 |
| Thanh mục tiêu | 4 | giữa trên | x = (W−458)/2 giữ nguyên; y = 0 → 16 |
| Nút đổi ↻ | 7 | phải-dưới | (W−78, H−327) → (W−102, H−343) |
| 6 nút kỹ năng | 8..13 | phải-dưới | (W−3−tx, H+2−ty) → (W−27−tx, H−14−ty) |
| Nút tấn công | 0x14 | phải-dưới | (W−162, H−160) → (W−186, H−176) |
| Hình nền bán nguyệt | không có | phải-dưới | (W−276, H−267) → (W−300, H−283) |
| Cần điều khiển | 0x15 | trái-dưới | (10, H−252) → (34, H−268) |
| Vùng chạm cần | 0x16 | trái-dưới | (10, H−380) → (34, H−396) |
| 4 mũi tên | 0x1e..0x21 | trái-dưới | (177,H−171), (91,H−85), (25,H−171), (91,H−237) → mỗi cái (+24, −16) |
| Minimap | 5,6 | trái-trên | (7,7) → (31,23) |

Hai điểm neo: `UIPlay_Create` lấy `x19 = SGL_GetScreenWidth()`, `x24 = SGL_GetScreenHeight()` (`sxtw` @0x167f7c, 0x167f88). Thay `sxtw` bằng `sub x19,x0,#24` / `sub x24,x0,#16` dời mọi thứ neo phải/dưới tính từ x19/x24 (cột phải @0x1680c4; thanh ID 4 @0x1681b8/bc; hình kỹ năng, ID 7, 0x14 qua `[sp,#8]` @0x168268, `x19−3` @0x16826c, `x25 = x24` @0x168260, `x24+2` @0x168270). `SGL_GetScreenWidth/Height` kết thúc bằng `ldr w0,[x8]; ret` (0x13194c, 0x1319a8) nên 32 bit cao của x0 sạch và `sub x19,x0,#24` đúng như `sxtw` (W, H dương).

Các lệnh còn lại không dùng x19/x24 (đọc `SGL_GetScreenHeight` trực tiếp ở 0x168420, 0x16848c, 0x1684e4, 0x16853c, 0x168594, 0x1685ec rồi `sub w8,w0,#k`; x là hằng) nên vá từng hằng.

### Bảng cuối (đã đối chiếu byte với `libgame.so`, lệnh mới dựng lại bằng `llvm-mc`, ngữ nghĩa kiểm tự động)

| # | vaddr | hàm | byte gốc → mới | lệnh gốc → mới | ghi chú |
|---|---|---|---|---|---|
| 1 | 0x167f7c | UIPlay_Create | `137c4093` → `136000d1` | `sxtw x19,w0` → `sub x19,x0,#24` | W neo phải; dời ID 101..103, 8..13, 7, 0x14 |
| 2 | 0x167f88 | UIPlay_Create | `187c4093` → `184000d1` | `sxtw x24,w0` → `sub x24,x0,#16` | H neo dưới |
| 3 | 0x167fb8 | UIPlay_Create | `21008052` → `21038052` | `mov w1,#1` → `#25` | ID 1 x |
| 4 | 0x16800c | UIPlay_Create | `21008052` → `21038052` | `mov w1,#1` → `#25` | ID 2 x |
| 5 | 0x16805c | UIPlay_Create | `21008052` → `21038052` | `mov w1,#1` → `#25` | ID 3 x |
| 6 | 0x1680cc | UIPlay_Create | `42018052` → `42038052` | `mov w2,#10` → `#26` | ID 101 y |
| 7 | 0x16811c | UIPlay_Create | `c20d8052` → `c20f8052` | `mov w2,#110` → `#126` | ID 102 y |
| 8 | 0x168168 | UIPlay_Create | `421a8052` → `421c8052` | `mov w2,#210` → `#226` | ID 103 y |
| 9 | 0x1681b8 | UIPlay_Create | `682a07f1` → `68ca06f1` | `subs x8,x19,#458` → `#434` | thanh ID 4: (W−24−434) = W−458, giữ tâm |
| 10 | 0x1681bc | UIPlay_Create | `692607d1` → `69c606d1` | `sub x9,x19,#457` → `#433` | thanh ID 4 (nhánh số âm của phép chia 2) |
| 11 | 0x1681c8 | UIPlay_Create | `e2031faa` → `020280d2` | `mov x2,xzr` → `mov x2,#16` | thanh ID 4 y |
| 12 | 0x168424 | UIPlay_Create | `08f00351` → `08300451` | `sub w8,w0,#252` → `#268` | cần 0x15 y |
| 13 | 0x16842c | UIPlay_Create | `41018052` → `41048052` | `mov w1,#10` → `#34` | cần 0x15 x |
| 14 | 0x168490 | UIPlay_Create | `08f00551` → `08300651` | `sub w8,w0,#380` → `#396` | vùng chạm cần 0x16 y |
| 15 | 0x168498 | UIPlay_Create | `41018052` → `41048052` | `mov w1,#10` → `#34` | 0x16 x |
| 16 | 0x1684e8 | UIPlay_Create | `08ac0251` → `08ec0251` | `sub w8,w0,#171` → `#187` | mũi tên 0x1e y |
| 17 | 0x1684f0 | UIPlay_Create | `21168052` → `21198052` | `mov w1,#177` → `#201` | 0x1e x |
| 18 | 0x168540 | UIPlay_Create | `08540151` → `08940151` | `sub w8,w0,#85` → `#101` | 0x1f y |
| 19 | 0x168548 | UIPlay_Create | `610b8052` → `610e8052` | `mov w1,#91` → `#115` | 0x1f x |
| 20 | 0x168598 | UIPlay_Create | `08ac0251` → `08ec0251` | `sub w8,w0,#171` → `#187` | 0x20 y |
| 21 | 0x1685a0 | UIPlay_Create | `21038052` → `21068052` | `mov w1,#25` → `#49` | 0x20 x |
| 22 | 0x1685f0 | UIPlay_Create | `08b40351` → `08f40351` | `sub w8,w0,#237` → `#253` | 0x21 y |
| 23 | 0x1685f8 | UIPlay_Create | `610b8052` → `610e8052` | `mov w1,#91` → `#115` | 0x21 x |
| 24 | 0x16b234 | UIPlay_MakeMinimap | `e1008052` → `e1038052` | `mov w1,#7` → `#31` | minimap rect ID 5 x |
| 25 | 0x16b238 | UIPlay_MakeMinimap | `e2008052` → `e2028052` | `mov w2,#7` → `#23` | ID 5 y |
| 26 | 0x16b264 | UIPlay_MakeMinimap | `e1008052` → `e1038052` | `mov w1,#7` → `#31` | ID 6 x |
| 27 | 0x16b268 | UIPlay_MakeMinimap | `e2008052` → `e2028052` | `mov w2,#7` → `#23` | ID 6 y |
| 28 | 0x1698c4 | UIPlayPorting_Draw | `14500451` → `14b00451` | `sub w20,w0,#276` → `#300` | hình nền bán nguyệt x |
| 29 | 0x1698cc | UIPlayPorting_Draw | `152c0451` → `156c0451` | `sub w21,w0,#267` → `#283` | hình nền bán nguyệt y |

Kiểm tự động (`view_check.py` mục [1], [2]): 29/29 byte gốc khớp; 29/29 lệnh mới dựng lại bằng `llvm-mc` ra đúng byte; delta ngữ nghĩa +24 ngang / +16 dọc cho từng loại điểm; `x19`/`x24` chỉ được dùng ở các lệnh nêu trên; 6 chỗ `SGL_GetScreenHeight` của cần/mũi tên và 2 chỗ của `UIPlayPorting_Draw` đều có lệnh vá.

- Không vá (theo A2: không neo mép HUD): `INSTANTMSG_*` (dòng thông báo), `DrawBuffInfo`, `SHORTCUT*`. Công nhân T9 chỉ kiểm lại bằng cách liệt kê mọi hàm `UIPlay*`/`UIPlayPorting*`/`INSTANTMSG*`/`Tutorial*`/`UIPopupMsg*` gọi `SGL_GetScreenWidth/Height` (trong `UIPlay*`/`UIPlayPorting*` chỉ còn `UIPlay_Create` và `UIPlayPorting_Draw`, cả hai đã phủ); nội dung `INSTANTMSG_*`, `Tutorial*`, `UIPopupMsg_*` KHÔNG đọc lại ⇒ lệch nếu có là **CHƯA XÁC ĐỊNH**.
- Rủi ro: mũi tên hướng dẫn trong `TutorialDescription` trỏ vào nút HUD có thể lệch 24/16 px (**CHƯA XÁC ĐỊNH**).
- Tuỳ chọn, **KHÔNG áp** (quản đốc): 0x2051b0 `03788052` → `03f48152`, 0x2051b4 `04508052` → `04f48152` (vùng chạm màn tiêu đề): màn logic không đổi nên giữ như gốc.
- Nút MOD của mod menu (T8) nằm ở lề phải cao 30 % cửa sổ (view Android, không phải HUD game); không đụng.

## 7. Cave `view` (nhóm `view`)

- Điểm vào: 0x14709c `74020039` (`strb w20,[x19]`) → `eeb0ff17` (`b 0x133454`). Mọi đường tới 0x14709c đều có w8 = cfg.W, w9 = cfg.H: nhánh W ≥ 480 qua `b 0x14709c` @0x14708c, nhánh W < 480 qua 0x147090..0x147098 (`mov w8,#480` @0x147058, `mov w9,#320` @0x147098).
- Sau 0x14709c chỉ còn `strh w8,[x19,#2]`, `strh w9,[x19,#4]`, `stp s1,s0,[x19,#8]` và epilogue: w10, w11 và cờ NZCV không còn được đọc ⇒ cave dùng w10, w11, làm hỏng cờ được.
- Nguồn: `scripts/asm/view_cave.s`; dựng: `scripts/asm/build_view_cave.sh` (llvm-mc + ld.lld, `-Ttext=0x133454`). 13 lệnh = 52 B, sha256 `2c406b7195be9b642b8d25577c26a4ffc04b8d9ea65157f0a1d57e69634da97d`:

```
133454 74020039 strb w20,[x19]            lệnh gốc bị thay (0x14709c)
133458 ea2f0090 adrp x10,0x72f000
13345c 4a8d47b9 ldr  w10,[x10,#0x78c]     P
133460 4b910151 sub  w11,w10,#100
133464 7fc90071 cmp  w11,#50
133468 e8000054 b.hi 0x133484             P ngoài [100,150] (kể cả 0, âm) => giữ nguyên
13346c 8b0c8052 mov  w11,#100
133470 087d0a1b mul  w8,w8,w10            W·P (W ≤ 512, P ≤ 150 => < 2^31)
133474 0809cb1a udiv w8,w8,w11
133478 08791f12 and  w8,w8,#0xfffffffe    ép W chẵn
13347c 297d0a1b mul  w9,w9,w10
133480 2909cb1a udiv w9,w9,w11
133484 074f0014 b    0x1470a0             strh w8,[x19,#2]
```

  Hex ghép tay của A2 trùng byte với bản llvm-mc (kiểm tự động). Byte gốc 0x133454..0x133488 (sha256 `1e2a3432380f35c80623114756e2556e6d1f6d0adab1f7d5c73ac0ddfc881f45`): `ffc301d1fd7b02a9f91b00f9f85f04a9f65705a9f44f06a9fd83009156d03bd5490c00f0e0031f2ac81640f9291d40f9e1031f2a` (đầu thân `SGL_grpResume`: `sub sp,sp,#0x70; stp x29,x30,...`). Phần còn lại của hàm (0x133488..0x1338d8) trở thành byte rác nhưng không bao giờ được thực thi (xem mục 8). `.eh_frame` vẫn mô tả hàm cũ, nhưng cave không gọi hàm nào, không đổi `sp`, nên không có unwind nào đi qua cave.
- Các nhánh còn trống 0x133488..0x1338d8 không dùng.
- Phương án B (vá 4 hằng 0x133048/0x133058/0x1333f8/0x13340c của màn logic): quản đốc đã loại, không làm.

## 8. Bằng chứng `SGL_grpResume` là mã chết (kiểm bởi `view_check.py` mục [4])

`SGL_grpResume` 0x133454..0x1338d8 (1156 B, symbol `T` xuất khẩu trong `.dynsym`):

- 0 lệnh `b/bl/b.cond/cbz/cbnz/tbz/tbnz/adr/ldr literal` ngoài hàm nhảy vào hàm (0 caller); không có nhánh nội bộ nào nhảy vào 52 B cave;
- 0 cặp `adrp` (trang 0x133000) + `add/ldr` tính ra địa chỉ trong hàm;
- 0 PLT stub `SGL_grpResume@plt`;
- 0 reloc theo tên, 0 reloc `R_AARCH64_RELATIVE` có addend trong hàm (không có trong `.init_array`, bảng JNI, bảng con trỏ hàm); mọi giá trị 64 bit căn 4 B trong dữ liệu trỏ vào hàm chỉ có 1: `st_value` của chính symbol trong `.dynsym`;
- không có slot `__CS_grpResume`; bảng `__CS_grp*` được điền bởi `SGL_grpInitialize`, không chứa `SGL_grpResume`;
- chuỗi `SGL_grpResume` chỉ có ở `.dynstr` (file off 0x49ace), không ở `.rodata`, không dạng `Java_*`; `libHercules.so` không NEEDED `libgame.so`, 0 symbol UND dạng `SGL_*`/`grp*`, không chứa chuỗi `grpResume`;
- trong APK TEST10 (1462 mục, mọi `classes*.dex`, smali đã quét qua dex, asset, manifest): chuỗi `grpResume` chỉ xuất hiện trong `lib/arm64-v8a/libgame.so`. (Grep thêm `dec4/smali*`, `jx4/sources`: 0 kết quả.)
- Bản sao sống của logic này nằm ở `SGL_InitState` (gọi từ `SGL_Androidresume` và `SGL_grpInitialize`), không dùng `SGL_grpResume`.
- Rủi ro còn lại (rất nhỏ): có `dlsym("SGL_grpResume")` từ thư viện nạp lúc chạy mà tên đó không nằm trong APK (không có thư viện khác); chấp nhận.

## 9. Pool `MEM_Create` (nhóm `pool`)

`MEM_Create` 0x1456e4 (1 caller: `startClet`):

```
145720 sub x9,x0,#0x64,lsl#12        x0 = CS_knlGetFreeMemory(); x9 = free - 0x64000
145724 mov w8,#0x600000
145728 cmp x9,#0x600,lsl#12
14572c csel x8,x9,x8,hi              x8 = max(x9, 6 MB)
145730 mov w9,#0x800000              <== 0910a052  =>  mov w9,#0xa00000   (0914a052)
145734 cmp x8,#0x800,lsl#12          <== 1f0160f1  =>  cmp x8,#0xa00,lsl#12 (1f0168f1)
145738 csel x8,x8,x9,lo              x8 = min(x8, trần)
14573c..145748: x0 = (x8 + 0x3ff) & 0x1fffc00   làm tròn lên 1 KB, mặt nạ tối đa 32 MB
14574c str x0,[pool_total]; 145750 bl CS_knlAlloc
```

- Mã hoá đã dựng lại bằng `llvm-mc` (log mục [5]): `mov w9,#0xa00000` = movz imm16 0xa0 lsl 16 hợp lệ; `cmp x8,#0xa00,lsl#12` imm12 0xa00 ≤ 0xfff hợp lệ. Mặt nạ `0x1fffc00` giữ nguyên 10 MB: `(0xa00000+0x3ff) & 0x1fffc00 = 0xa00000`.
- Hằng 8 MB chỉ dùng ở `MEM_Create` (0x145730, 0x145734); hai chỗ khác có `#0x800000` (`pactSqrtx`, `__emutls_get_address`) không liên quan pool.
- **Nguồn bộ nhớ:** `CS_knlAlloc` = `calloc(1, n)` (0x261ba4) ⇒ heap libc, không phải vùng cố định; 10 MB `calloc` không thành vấn đề trên máy 64 bit. `CS_knlGetFreeMemory` = `jGetNativeAvailableMemory` = `CFunction.getNativeAvailableMemory()` = `ActivityManager.MemoryInfo.availMem` (RAM còn trống của cả hệ thống, bytes). Trần mới chỉ có tác dụng khi `availMem − 0x64000` > 8 MB (mọi điện thoại hiện đại); nếu thấp hơn thì pool = max(…, 6 MB) như gốc.
- Dùng pool: `MEM_Malloc(2·W'²)` của `GRP_Initialize` tăng +0,05 MB (P = 105) tới +0,66 MB (P = 150) so với 0,5 MB gốc (bảng ở mục 4). Mức dùng đỉnh của game: **CHƯA XÁC ĐỊNH**, đo ở T9c (đọc `MEM_GetUsedPoolSize`/`MEM_GetTotalPoolSize`).

## 10. Rủi ro theo màn

| Màn | Rủi ro | Mức |
|---|---|---|
| Tiêu đề / intro | không đổi (màn logic + vùng chạm giữ nguyên); FB lớn hơn nhưng nền vẽ theo màn logic | thấp |
| Menu có trượt camera (`MENUSTATEUTIL_ResetSimpleMoveType`) | từ P ≥ 140 (H' ≥ 322) đổi tier, không crash | trung bình, đã chấp nhận (mặc định 120) |
| Màn chơi, map hẹp hơn vùng nhìn | căn giữa, thừa tô đen (hành vi gốc cho map nhỏ) | thấp |
| Bản đồ nhỏ (minimap) | rect ID 5/6 dời (31,23); `UIPlay_DrawMinimap` đọc `GetAbsoluteRect` nên theo | thấp |
| Hội thoại, cửa hàng, hộp thoại | vẽ theo màn logic, không đổi | thấp |
| HUD | dời cứng 24/16 px; mũi tên hướng dẫn có thể lệch (CHƯA XÁC ĐỊNH) | thấp |
| Chạm thế giới | không đổi quy tắc gốc; vùng chạm map rộng hơn P% (CHƯA XÁC ĐỊNH tác động tap-để-đi) | không rõ |
| Hết pool `MEM_Malloc` | 8 → 10 MB; +0,05..0,66 MB cho `MEM_Malloc(2·W²)` | thấp (đo ở T9c) |

## 11. Kế hoạch kiểm (T9c)

1. unidbg `-Demu.w=2400 -Demu.h=1080`: ghi P vào 0x72f78c (mô phỏng Java `onResume`) trước `startClet`; dump 0x2d6098/9c (kỳ vọng 1422/640), `GRP_nDisplayW/H` 0x2d7ab4/ab8 (P = 100 ⇒ 512/230; P = 120 ⇒ 614/276; P = 105 ⇒ 536/241, W chẵn).
2. Chụp màn tiêu đề/intro ở P = 100 và 120, tap START (240,277) vẫn trúng.
3. Đọc `MEM_GetUsedPoolSize`/`TotalPoolSize` lúc tới màn sâu nhất chạy được; ghi số đo.
4. Màn chơi: chỉ khi unidbg qua được màn chọn save (lỗi T2); nếu không thì ghi rõ.
5. Build `out/Inotia4_T9_check.apk` (`--smali net,mod --vnfont --lib --diag`), `apkdiff` với TEST10, apksigner, `zipalign -c -P 16 4`, `check_apk`.

## 12. T9b: nhóm patch trong `scripts/patch_lib.py`, mod menu, build

| Nhóm | Số patch | Vùng (vaddr) | Byte khai báo → byte thật sự khác (lib TEST10 → T9) |
|---|---|---|---|
| `view` | 2 | 0x14709c (4 B) + cave 0x133454..0x133488 (52 B) | 56 → 54 (4 + 50) |
| `hud` | 29 | 29 lệnh 4 B trong 0x167f7c..0x16b26c và 0x1698c4/0x1698cc | 116 → 40 |
| `pool` | 2 | 0x145730, 0x145734 (4 B mỗi cái) | 8 → 2 |

- Mỗi patch kiểm byte gốc (lệch cả bản gốc lẫn bản mới thì dừng, không ghi gì), kiểm lệnh kế tiếp (`refs`), mỗi nhóm áp trọn vẹn hoặc dừng. `--check-asm` dựng lại cave `mod`, cave `view` (llvm-mc + ld.lld) và 31 lệnh đơn `hud`/`pool` (llvm-mc) rồi so byte; `--disasm` in disasm trước/sau và so với lệnh mong đợi (39/39 khớp).
- Lib TEST4 + 6 nhóm == lib TEST10 + `view,hud,pool` (cùng sha256 `d5af7d0a80eee990a9e2ff8af87c8c98a488b87d23c53983257a3d5577d81f98`); áp lần 2 ⇒ 39 "đã áp"; `--diff` TEST10 → T9: 96 B, 35 đoạn; TEST4 → T9: 350 B, 56 đoạn; mọi byte nằm trong vùng khai báo. 5 phép thử âm tính (byte gốc HUD/cave/pool sai, `refs` sai, nhóm HUD lệch trạng thái) đều dừng; byte lạ ngoài vùng ⇒ `--diff` báo "NGOAI VUNG".
- `ModMenu`: thanh thứ 4 "Tầm nhìn" 100..150, bước 5, mặc định 120, hiển thị "%d%%", ghi chú "Khởi động lại game để áp dụng", ghi int thứ 4 qua `ModMem` (LOCATE_LEN = 16 B đã phủ). Nút "Đặt lại x1" chỉ đặt lại 3 thanh hệ số (`MULT_COUNT = 3`); tầm nhìn không bị đặt lại. Người dùng TEST10 chưa có khoá `view` trong SharedPreferences ⇒ nhận mặc định 120 ở lần mở đầu tiên của bản T9.
- Build: `APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/Inotia4_T9_check.apk --smali net,mod --vnfont --lib --libgroup base,p1,mod,view,hud,pool --diag`.

## 13. T9c: kết quả kiểm (unidbg, 2400×1080)

Lệnh: `bash emu/view_check.sh /opt/work/lib4/libgame.so <lib T9> work/viewcheck --title` rồi `scripts/re/view_xcheck.py` (log `logs/t9c_view_check.log`, dòng cuối **TẤT CẢ ĐẠT**); mô phỏng Java ghi P bằng `-Demu.poke=0x72f78c=P` trước `nativeStart` (trước `startClet`, như `onResume`).

| P | `0x2d6090/94` (máy) | `0x2d6098/9c` (logic) | `0x2d7ab4/ab8` (FB) | pool dùng lúc khung 120 |
|---|---|---|---|---|
| lib gốc | 2400/1080 | 1422/640 | 512/230 | 1.753.864 B (pool 8 MB) |
| chưa ghi (P=0), 99, 151, −1, 200 | 2400/1080 | 1422/640 | 512/230 | 1.753.864 B |
| 100 | 2400/1080 | 1422/640 | 512/230 | 1.753.864 B |
| 105 | 2400/1080 | 1422/640 | **536/241** | 1.804.168 B |
| 120 | 2400/1080 | 1422/640 | 614/276 | 1.983.568 B (+229.704 B = 2·614² − 2·512²) |
| 150 | 2400/1080 | 1422/640 | 768/345 | 2.409.224 B (+655.360 B) |

- Cả 16 giá trị P ở 2400×1080 (100..150 bước 5, 99, 151, 0, −1, kèm "chưa ghi") và 5 giá trị P ở mỗi máy 960×640, 1920×1080, 800×480, 799×480, 600×360, 479×320 (nhánh cfg 512×H, 480×H, 480×320) khớp công thức `floor(cfg·P/100)` (W chẵn); màn logic và kích thước thật không đổi theo P.
- P = 105 ⇒ W = **536**, không phải 538 (xem mục 4). W chẵn ở mọi P (kể cả 105, 115, 135, 145).
- Pool: tổng 8.388.608 B (lib gốc) → 10.485.760 B (lib T9) ở mọi lần chạy. `used(P) − used(gốc)` khớp từng byte với `2·(W'² − W0²)` (`MEM_Malloc(2·W·W)` của `GRP_Initialize`) ở cả 6 kích thước máy.
- **Mức dùng đỉnh đo được** (màn sâu nhất chạy được = màn chọn ô save, sau khi bấm BẮT ĐẦU; lấy mẫu mỗi 20 khung tới khung 760): lib gốc 3.674.063 B (3,50 MB / 8 MB); T9 P=100 3.674.063 B; T9 P=120 **3.904.879 B (3,72 MB / 10 MB)**. Trong màn chơi: **CHƯA XÁC ĐỊNH** (không tới được).
- Màn tiêu đề / intro (`docs/emu_shots/t9c_tieu_de_P100.png`, `t9c_tieu_de_P120.png`, `t9c_chon_save_P100.png`, `t9c_chon_save_P120.png`, thu nhỏ 1200×540; bản đầy đủ 2400×1080 trong `work/viewcheck/runs/title_P*`): ở khung 400 (thanh nạp), 610 (màn tiêu đề), 640/690/720 (sau tap) ảnh của lib gốc, P=100 và P=120 **giống nhau từng pixel** (0 px khác), đúng dự đoán: nền và nút vẽ theo màn logic, không phụ thuộc FB. Tap START (240,277) ở khung 620 vẫn trúng (khung 610 → 640 đổi từ màn tiêu đề sang màn chọn ô save, 1.237.594 px khác ở cả hai P).
- unidbg dừng ở màn chọn ô save (`UC_ERR_READ_UNMAPPED` sau lệnh vẽ chữ "Chọn một ô trống…", lỗi môi trường T2, xảy ra ở cả lib gốc); các khung sau đó đứng yên (0 px khác giữa các ảnh khung 730/760/830/880 ở P=120) nên **không chụp được màn chơi / HUD thật**.
- **HUD (thay cho ảnh màn chơi):** `-Demu.hudcall=-1` gọi thẳng `UIPlay_Create(1)` sau khi tự gọi `InputLayer_Create` (tầng UI chỉ được game tạo khi vào state) và in mọi `ControlObject_SetRect`/`SetID`. 21 control ở màn logic 1422×640 (và 960×640) dịch đúng theo bảng mục 6 (cột trái +24 ngang; cột phải −24/+16; thanh ID 4 +16 dọc, x giữ 482; 6 kỹ năng, nút đổi, nút tấn công −24/−16; cần điều khiển, vùng chạm cần và 4 mũi tên +24/−16), w/h không đổi, thứ tự tạo control giống nhau. Chưa kiểm bằng chạy: minimap (4 điểm 0x16b234..0x16b268, `UIPlay_MakeMinimap` cần dữ liệu bản đồ) và hình nền bán nguyệt (`UIPlayPorting_Draw`): chỉ kiểm bằng disasm (mục 6).
- APK `out/Inotia4_T9_check.apk` (sha256 `5d247dd93011281e066c60181866eb52a40a7efa94d279d8e538bd5971c96f16`, không commit): `apkdiff` với TEST10 chỉ khác `classes3.dex` và `lib/arm64-v8a/libgame.so` (1454 file chung); decode lại bằng apktool: smali chỉ khác ở `com/namp/vh/ModMenu`, `ModMenu$1..$4`, `ModMenu$FloatButton`, `ModMenu$Slot`; apksigner v1/v2/v3 đạt, cùng chứng chỉ TEST10 (`cc559651…20d4`); `zipalign -c -P 16 4` đạt; `check_apk.py` PASS. Log `logs/t9c_apk_check.txt`.
