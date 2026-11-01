# T9a: bàn giao đọc trước của A2 (2026-11-01, ~75% T9a, chưa chạy, chưa kiểm)

Đây là dữ liệu bàn giao. Quyết định của quản đốc nằm ở PLAN.md (mục "ĐỔI THIẾT KẾ sau T9a" trong Quyết định thiết kế và mục T9) và được ưu tiên hơn file này. Mọi byte gốc A2 đã đối chiếu với `/opt/work/lib4/libgame.so` và `dis.txt`. Asm mới đã khớp `llvm-mc`. Công nhân implement phải kiểm lại từng điểm.

## Phát hiện chính: vùng nhìn thế giới = khung đệm FB, không phải màn logic

- `startClet` 0x17a6f8 gọi `OriginalDisplay_ConfigInfo` (0x17a73c), rồi `SGL_grpInitialize` (0x17a74c).
- `OriginalDisplay_ConfigInfo` 0x147030 đọc `GetDeviceWidth/Height` (kích thước thật) và ra cfg.W/H:
  - W ≥ 800 ⇒ (512, H·512/W);
  - 480..799 ⇒ (480, H·480/W);
  - W < 480 ⇒ (480, 320).
  - Máy 2400×1080 ⇒ 512×230. Quản đốc đã tự disasm xác nhận.
- `SGL_grpInitialize` tạo FB bằng `SGL_Device::CreateImage(fmt, cfg.W, cfg.H)` (0x132ef8).
- `GRP_Initialize` 0x141554 ghi `GRP_nDisplayW/H` (0x2d7ab4/ab8; tại 0x141780/0x141790) = kích thước FB.
  - Hai chỗ ghi khác: `GRP_SetDisplayArea` (0 caller) và `STATE_ProcessInit` 0x203878 (strb, không phải kích thước).
- `MAP_*` và camera đọc `GRP_nDisplayW/H`: `MAP_DrawBase`, `MAP_DrawPartial`, `MAP_SetFocus`, `MAP_Load`, `GAMESTATE_DrawPlay`...
- `Point_ChangeGameBufferPosToUIBufferPos` 0x13ec20: x_UI = x_FB·`SGL_GetScreenWidth()`/FBW. Vậy toạ độ FB (thế giới) và toạ độ UI (logic) là hai hệ khác nhau.
- `GAMESTATE_DrawPlay`: vẽ MAP/EFFECT bằng `GRP_*`, sau đó `GRPX_Start` → `UIPlayPorting_Draw` → `GRPX_End` (UI vẽ bằng GRPX theo logic).
- Ảnh máy thật khớp FB 512: nhân vật ~170 px thật ≈ 36 px FB × 4,69. Đây là bằng chứng gián tiếp.

## 1. Thứ tự onResume / Reapply

- `onResume` (Java) chạy trước `onSurfaceCreated`. Đây là hành vi framework, chưa tự kiểm.
- Sau `onSurfaceCreated`, game chờ 500 ms, rồi `queueEventForcedly` → NATIVE_START → `nativeStart` → `nativeStartClet` → `startClet` (đọc cfg 1 lần).
- ⇒ Ghi 0x72f78c trong `onResume` là kịp cho lần mở app.
- `nativeReapplyScreenSize` (JNI 0x26f3a4 → `SGL_ReapplyScreenSize`):
  - được gọi từ `CWrapperKernel.onSurfaceChanged` và `MainActivity.onWindowFocusChanged(true)`;
  - chỉ tính lại logic + viewport, **không** tạo lại FB.
- ⇒ k chỉ áp khi mở lại app.

## 2. MAP / camera

- Hằng 0x280 trong `MAP_DrawPartial` (0x1bc71c, 0x1bc774...), `MAP_AddLayerTile`, `MAP_LoadLayer` là stride hàng tile (hàng·640 + cột·10), không phải kích thước màn.
- Khung vẽ: `MAP_nDisplayBTX/BTY/ETX/ETY` (0x2e418c..0x2e4198). Xoá màn bằng `GRP_FillRect(0,0,GRP_nDisplayW/H)`.
- Các hằng 640/960 còn lại thuộc UI:
  - `CalcResolution*`;
  - `GetScalevalue`, `GetScalePlusW/H` (0x13094c/968/9c0; chỉ dùng cho nền);
  - `STATE_EnterTitle` 0x205170/74, 0x2051b0/b4;
  - `TouchHandle_ControlEventProc` 0x13facc/fbfc (mã sự kiện 640/641).

## 3. Ảnh nền

- `GAMELOADER_DrawBackGround` 0x1ab8f0: 2 nửa ảnh (nhóm 0x4f/0x50), mỗi nửa rộng ~600, căn giữa bằng `GetResolutionWidth/Height`, thang `GetScalevalue` = H_logic/640.
- Ở 2400×1080 nền rộng ~1200 logic, hai bên đen ~111 logic mỗi bên (ảnh `/opt/work/emu24/f00400.png`).
- `DrawTitle_Title` 0x205b2c: căn W/2, H/2; chữ START neo đáy (H−25).
- Vùng chạm màn tiêu đề là nút 960×640 tại (0,0).

## 4. HUD (logic 1422×640)

`Draw` vẽ tại tâm rect (`ControlObject_GetRelativeRect`), nên sửa rect thì hình và vùng chạm cùng dời. Ngoại lệ: hình nền (W−276, H−267) và minimap (rect ID 5/6 tại 7,7).

| Nhóm | ID | Neo | Vị trí |
|---|---|---|---|
| Cột trái (lưu, giỏ, bánh răng) | 1,2,3 | trái | x=1, y=155/232/309, 75×75 |
| Cột phải (chân dung/đồng đội) | 101..103 | phải | x=W−93, y=10/110/210, 78×81 |
| Thanh mục tiêu | 4 | giữa trên | x=(W−458)/2, y=0, 458×123 |
| Nút đổi ↻ | 7 | phải | (W−78, H−327), 75×75 |
| 6 nút kỹ năng | 8..13 | phải-dưới | 80×80, x=(W−3)−tx[i], y=(H+2)−ty[i] |
| Minimap | 5,6 | trái-trên | (7,7) |
| Nút tấn công | 0x14 | phải-dưới | (W−162, H−160), 159×160 |
| Hình nền bán nguyệt | không có | phải-dưới | (W−276, H−267) |
| Cần điều khiển | 0x15 | trái-dưới | (10, H−252), 252×252 |
| Vùng chạm cần | 0x16 | trái-dưới | (10, H−380), 380×380 |
| 4 mũi tên | 0x1e..0x21 | trái-dưới | (177,H−171), (91,H−85), (25,H−171), (91,H−237) |

### Điểm vá lề HUD (24 px ngang / 16 px dọc), mỗi điểm 1 lệnh, không cave

| vaddr | byte gốc → mới | ghi chú |
|---|---|---|
| 0x167f7c | `137c4093` → `136000d1` | `sxtw x19,w0` → `sub x19,x0,#24` (W neo phải). Dời ID 101..103, 8..13, 7, 0x14 |
| 0x167f88 | `187c4093` → `184000d1` | `sub x24,x0,#16` (H neo dưới) |
| 0x167fb8 | `21008052` → `21038052` | cột trái x 1 → 25 |
| 0x16800c | `21008052` → `21038052` | cột trái |
| 0x16805c | `21008052` → `21038052` | cột trái |
| 0x1680cc | `42018052` → `42038052` | cột phải y=26 |
| 0x16811c | `c20d8052` → `c20f8052` | y=126 |
| 0x168168 | `421a8052` → `421c8052` | y=226 |
| 0x1681b8 | `682a07f1` → `68ca06f1` | thanh ID 4, bù tâm vì W đã trừ 24 |
| 0x1681bc | `692607d1` → `69c606d1` | thanh ID 4 |
| 0x1681c8 | `e2031faa` → `020280d2` | thanh ID 4 y=16 |
| 0x168424 | `08f00351` → `08300451` | cần 0x15 y |
| 0x16842c | `41018052` → `41048052` | cần 0x15 x |
| 0x168490 | `08f00551` → `08300651` | 0x16 y |
| 0x168498 | `41018052` → `41048052` | 0x16 x |
| 0x1684e8 | `08ac0251` → `08ec0251` | 0x1e y |
| 0x1684f0 | `21168052` → `21198052` | 0x1e x |
| 0x168540 | `08540151` → `08940151` | 0x1f y |
| 0x168548 | `610b8052` → `610e8052` | 0x1f x |
| 0x168598 | `08ac0251` → `08ec0251` | 0x20 y |
| 0x1685a0 | `21038052` → `21068052` | 0x20 x |
| 0x1685f0 | `08b40351` → `08f40351` | 0x21 y |
| 0x1685f8 | `610b8052` → `610e8052` | 0x21 x |
| 0x16b234 | `e1008052` → `e1038052` | minimap (7,7) → (31,23) |
| 0x16b238 | `e2008052` → `e2028052` | minimap |
| 0x16b264 | `e1008052` → `e1038052` | minimap |
| 0x16b268 | `e2008052` → `e2028052` | minimap |
| 0x1698c4 | `14500451` → `14b00451` | hình nền ở `UIPlayPorting_Draw` |
| 0x1698cc | `152c0451` → `156c0451` | hình nền |

- `SGL_GetScreenWidth/Height` kết thúc bằng `ldr w0,[x8]; ret` (0x13194c, 0x1319a8) nên 32 bit cao của x0 sạch. x19/x24 chỉ dùng làm W/H ở 0x1680c4, 0x1681b8, 0x168260..70.
- `UIPlay_ButtonDirection` 0x168e38 dùng `GetAbsoluteRect` của chính các control này, nên chạm tự đi theo.
- Không cần vá: `INSTANTMSG_*`, `DrawBuffInfo`, `SHORTCUT*`.
- Tuỳ chọn, quản đốc KHÔNG áp: 0x2051b0 `03788052` → `03f48152`, 0x2051b4 `04508052` → `04f48152` (vùng chạm màn tiêu đề).
- Rủi ro: mũi tên hướng dẫn `TutorialDescription` có thể lệch 24/16 px (CHƯA XÁC ĐỊNH).

## 5. Đường chạm

- Java: `CTouchListener.onTouch` → `CFunction.convertDisplaytoOriginal` (x·480/displayW, y·320/displayH).
- Native: `handleCletEvent_Wrapper` 0x2677f8 → `handleCletEventOriginal2Device` 0x230e14 → `handleCletEventImpl` 0x17a128.
- Tại 0x17a1e4..0x17a230: x·`SGL_GetScreenWidth()`/480, y·`SGL_GetScreenHeight()`/320, đọc live.
- `TouchGameObject_*` (chạm lên thế giới): CHƯA XÁC ĐỊNH.

## 6. Patch tầm nhìn: phương án A (đã chọn)

- Điểm vá 0x14709c `74020039` (`strb w20,[x19]`) → `eeb0ff17` (`b 0x133454`).
- Cave 48 B tại 0x133454..0x133484, trong thân `SGL_grpResume` (1156 B). Bằng chứng mã chết:
  - 0 caller;
  - 0 reloc;
  - không có `adrp+add`/`adr` trỏ vào;
  - không có PLT;
  - `libHercules` không import `SGL_*`.
  - Quản đốc kiểm lại: không có reloc nào chứa grpResume; tên chỉ có trong dynsym/dynstr (symbol `T` được export) ⇒ còn rủi ro nhỏ có nơi tra bằng `dlsym`; công nhân kiểm thêm trong dex/smali chuỗi "grpResume".
- Cave:
  ```
  133454 strb w20,[x19]
  133458 adrp x10,0x72f000
  13345c ldr  w10,[x10,#0x78c]
  133460 sub  w11,w10,#100
  133464 cmp  w11,#50
  133468 b.hi 0x133480
  13346c mov  w11,#100
  133470 mul  w8,w8,w10
  133474 udiv w8,w8,w11
  133478 mul  w9,w9,w10
  13347c udiv w9,w9,w11
  133480 b    0x1470a0
  ```
  hex: `74020039ea2f00904a8d47b94b9101517fc90071c80000548b0c8052087d0a1b0809cb1a297d0a1b2909cb1a084f0014`
- Thanh ghi:
  - sau 0x14709c chỉ còn `strh w8,[x19,#2]`, `strh w9,[x19,#4]`, `stp s1,s0,[x19,#8]`, epilogue;
  - w10/w11 chết; NZCV không còn được dùng;
  - cả nhánh W < 480 (qua 0x147090) cũng đi qua cave.
- Còn trống 0x133488..0x1334dc.

### Phương án B (KHÔNG chọn, giữ để tham khảo)

- Vá 4 hằng logic 0x133048/0x133058/0x1333f8/0x13340c bằng `b cave`.
- Thanh ghi tự do w8, w9, w10, s2. Reapply chưa lưu `lr` ⇒ chỉ được `b`.
- Chưa dựng hex.

## Còn lại của T9a (~25%)

1. `RenderCoreInitialize` (nhánh theo {0xf0, 0x140, 0x190, 0x280} tại 0x25a9b8..0x25aa88, mặc định 0x25ab38).
   - Kiểm đó là W hay H của FB.
   - Kiểm texture FB có cố định 512 không.
2. `RenderCoreFlush` (hằng 640f ở 0x25b67c, texture ở 0x25b664) và `CSFB_grpFlushLcd` 0x25a828: FB kéo lên màn thế nào, có méo không.
3. `MAP_SetFocus` (camera kẹp hay để đen khi map nhỏ hơn vùng nhìn), `TouchGameObject_AddMap`.
4. unidbg 2400×1080 (thuộc T9c):
   - dump 0x2d6098/9c, 0x2d7ab4/ab8; kỳ vọng 1422/640 và 512/230;
   - so P = 100 và P = 120, chụp ảnh.

---

# T9a phần 2: bàn giao của A2 thứ 3 (2026-11-01, T9a ~92%, chỉ đọc)

## RenderCore* không nằm trên đường vẽ thật

- `nativeStart` 0x26edc4: nếu `jWD_GetRendererType()==0` thì gọi `RenderCoreInitialize(OrigW, OrigH, 0)`, rồi `InitializeApp` 0x26ee04 gắn bảng `__CS_grp*` = CSFB.
- `SGL_grpInitialize` 0x132d6c (gọi từ `startClet`) ghi đè bảng ở 0x132e38..0x132e70: `__CS_grpFlushLcd` = `SGL_grpFlushLcd` (GOT 0x2bdd80).
  - `InitializeApp` chỉ có 1 caller. `RenderCoreFlush` chỉ có 1 caller (`CSFB_grpFlushLcd` 0x25a864) ⇒ mã chết.
- `RenderCoreInitialize`:
  - rẽ nhánh theo `jWD_GetOriginalHeight` (480×320 do `getWrapperData` đặt), không phải FB;
  - texture RGB565 rộng = POT(W).
  - Lý do tránh P = 125 (640) **không còn**.

## Đường vẽ thật: `SGL_grpFlushLcd` 0x133218 → `SGL_FrameBufferToScreen` 0x1327a4

- Ortho (0,W_fb,0,H_fb) lấy W/H live từ `SGL_Image`+0xc/+0x10. Quad phủ viewport `glViewport(0,0,devW,devH)`.
- Mỗi khung `glTexSubImage2D(0,0,W,H)` (0x1327f8), không có số cứng.
- Texture `SGL_Texture::Create` 0x2647c0, kích thước POT, UV max = (W<<16)/POT(W), GL_LINEAR.
- Hệ số kéo: 512×230 ≈ 4,69; 614×276 ≈ 3,91; 640×287 ≈ 3,75; 768×345 ≈ 3,13.
- **W phải chẵn:**
  - `GRP_Initialize` 0x141640 đặt `GRP_nBPL` = 2·W;
  - `SGL_Device::CreateImageBuffer` 0x263b80 đặt pitch = (2W+3)&~3;
  - GL alignment 4, không có `glPixelStorei`.
  - W lẻ ⇒ ảnh bị xiên. Cave cũ cho W lẻ ở P = 105/110/130/135.

## Buffer theo FB

- `CreateImage` 0x263a28 → `CreateImageBuffer`: `malloc(((2W+3)&~3)·H)`.
- `GRP_Initialize`: `MEM_Malloc(2·W·W)`: 0,5 MB ở 512; 0,75 MB ở 614; 1,18 MB ở 768.
- Back buffer `CS_grpCreateOffScreenFrameBuffer(H,W)` đủ chứa memcpy của `GRP_SaveLCD`/`RestoreLCD`.
- `GRP_nDisplayW/H`: 71/63 hàm đọc, đều qua GOT, tự co giãn.
- `MENUSTATEUTIL_ResetSimpleMoveType` 0x1f061c rẽ nhánh `H≥320` (0x1f064c), `H≥220` (0x1f0670). Từ P ≥ 140 (H = 322), tier trượt camera menu đổi; không crash.

## Camera / map

- `MAP_SetInformation` 0x1be718, `MAP_Load` 0x1bf1b8: map hẹp hơn vùng nhìn ⇒ căn giữa, phần thừa tô đen. `MAP_DrawPartial` 0x1bc488..0x1bc4c0 luôn tô đen dải 50 px đáy.
- `MAP_SetFocus` 0x1bf5dc kẹp focus. Không có hằng nào phụ thuộc 512.

## Chạm thế giới

- `TouchGameObject_AddMap` 0x14a8fc: rect (0,0,GRP_nDisplayW,H) đọc live.
- `TouchGameObject_EventMap` 0x14a6fc: thế giới = pt + BTX·16 − BX, **không đổi tỉ lệ UI→FB** (giống game gốc).
- CHƯA XÁC ĐỊNH: kiểm bằng unidbg, break ở 0x14a7f8.

## Cave mới 52 B tại 0x133454..0x133488 (thêm ép W chẵn)

```
133454 strb w20,[x19]            74020039
133458 adrp x10,0x72f000         ea2f0090
13345c ldr  w10,[x10,#0x78c]     4a8d47b9
133460 sub  w11,w10,#100         4b910151
133464 cmp  w11,#50              7fc90071
133468 b.hi 0x133484             e8000054
13346c mov  w11,#100             8b0c8052
133470 mul  w8,w8,w10            087d0a1b
133474 udiv w8,w8,w11            0809cb1a
133478 and  w8,w8,#0xfffffffe    08791f12
13347c mul  w9,w9,w10            297d0a1b
133480 udiv w9,w9,w11            2909cb1a
133484 b    0x1470a0             074f0014
```

- Byte gốc 0x133454..0x133488: `ffc301d1fd7b02a9f91b00f9f85f04a9f65705a9f44f06a9fd83009156d03bd5490c00f0e0031f2ac81640f9291d40f9e1031f2a`.
- Hex trên do A2 ghép tay ⇒ phải dựng lại bằng `llvm-mc` và đối chiếu.

## Bổ sung của quản đốc: pool `MEM_Malloc` có trần

- `MEM_Create` 0x1456e4 (gọi từ 0x17a750): pool = clamp(free − 0x64000, 6 MB, **8 MB**). Trần 8 MB ở 0x145730 (`mov w9,#0x800000`) và 0x145734 (`cmp x8,#0x800,lsl#12`).
- `MEM_Malloc` 0x14585c so phần còn trống với kích thước, thiếu thì nhảy 0x145978.
- `MEM_Malloc(2·W·W)` của `GRP_Initialize` tăng thêm ~0,23 MB ở P = 120 và ~0,66 MB ở P = 150 ⇒ rủi ro hết pool. Mức dùng đỉnh của game: CHƯA XÁC ĐỊNH.
