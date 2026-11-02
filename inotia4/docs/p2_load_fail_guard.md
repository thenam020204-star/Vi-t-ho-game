# P2 (đề xuất, CHƯA ÁP): không vào `EnterPlay` khi `SAVE_Load` thất bại

Trạng thái: **chỉ là đề xuất**. Không có dòng nào trong `scripts/patch_lib.py` hay bản build nào (kể cả TEST8) áp P2. Cần quản đốc duyệt. Nguồn: disasm `libgame.so` TEST4 (`/opt/work/lib4/dis.txt`); mọi địa chỉ là vaddr. Điểm chưa chạy thật hoặc chưa chắc ghi "CHƯA XÁC ĐỊNH".

## 1. Chuỗi lỗi

`SaveSlot_SlotButtonExe` → `GAME_StartResumeGame(slot)` (0x1ad498): khởi tạo các pool (`PLAYER_Initialize`, `MAP_Initialize`...), ghi ô vào GOT 0x2bf4c8, `STATE_Set(5)` (STATE_Game), `MAPCHANGE_Set(-1,-1,-1,0)` (mapId âm = "nạp từ save"), `GAMESTATE_SetState(3)` (MapChange).

Mỗi khung hình `GAMESTATE_ProcessMapChange` (0x1381b0) chạy theo bộ đếm `MAPCHANGE_nFrame` (GOT 0x2beb50): ở khung 4, mapId âm ⇒ `bl SAVE_Load` @0x138264, **bỏ qua giá trị trả về**; khung 0xb ⇒ `GAMESTATE_SetState(0)` @0x13823c ⇒ `GAMESTATE_EnterPlay` ⇒ `CHAR_GetSkillPoint(PLAYER_pMainPlayer)`.

`SAVE_Load` (0x1d6874) trả `w0=0` ngay khi `SAVE_LoadFile` false (0x1d6968), trả `w0=1` ở cuối (0x1d6a98). Khi trả 0, `SAVE_LoadCharacterAll` (nơi duy nhất gán `PLAYER_pMainPlayer`, GOT 0x2bebd8) không chạy ⇒ hero NULL ⇒ SIGSEGV tại `ldrb w0,[x0,#0x328]`.

`SAVE_Load` chỉ có **một** caller: `GAMESTATE_ProcessMapChange` (xref). Chặn tại đó là đủ cho mọi đường nạp.

## 2. Bảng trạng thái liên quan (đọc từ `GAMESTATE_SetState` 0x20344c và `STATE_NextStartProcess` 0x179b28)

| GAMESTATE (`GAMESTATE_SetState`) | Hàm vào |
|---|---|
| 0 | `GAMESTATE_EnterPlay` |
| 1 | `GAMESTATE_EnterEvent` |
| 2 | popup (`UI_SetPopupProcessInfo(1,0x10)`) |
| 3 | `GAMESTATE_EnterMapChange` |
| 4 | `GAME_Exit()` rồi `STATE_Set(4)` |

| STATE (`STATE_Set`) | Hàm vào |
|---|---|
| 0 Init, 1 Grade, 2 Logo, 3 Title | `STATE_EnterInit/Grade/Logo/Title` |
| **4 MainMenu** | `STATE_EnterMainMenu` (màn START rồi chọn ô save) |
| 5 Game | `STATE_EnterGame` |

`GAMESTATE_SetState(4)` là đường **có sẵn của game** để thoát về menu chính: `SystemMenu_ConfirmExitOK` (0x2010f4: `UI_SetPopupProcessInfo(4,0); GAMESTATE_SetState(4)`) và `Wipeout_PopupOKExit` (0x2024d4) dùng nó. `GAME_Exit` (0x1ad5a8) chỉ gọi các hàm dỡ tài nguyên: `SNASYS_UnloadAll`, `DEALSYSTEM_UnloadAll`, `MAP_UnloadTile`, `MAPFEATURESYSTEM_Destroy`, `SOUNDSYSTEM_StopAll`, `INSTANTMSGSYSTEM_Unload`, `GRP_Initialize*`, `UI_SetPopupProcessInfo(4,0)`; **không đụng `PLAYER_pMainPlayer`**. Đã đọc `MAP_UnloadTile` (0x1bc04c, kiểm `cbz x0`), `MAPFEATURESYSTEM_Destroy` (0x1c15ac, kiểm NULL), `PALETTE_Destroy` (0x1db41c, kiểm NULL), `DEALSYSTEM_UnloadAll` (vòng 0x30 mục): đều chịu được trạng thái "vừa `Initialize`, chưa nạp gì". `GAMESTATE_ExitMapChange` (0x138388) chỉ khôi phục cờ phím. Cách gọi `GAMESTATE_SetState` từ trong `ProcessMapChange` cũng đã có sẵn: 0x13823c (`SetState(0)` rồi `b 0x1382c4`).

Sau `SetState(4)`: con trỏ `GAMESTATE_fp*` bị xoá, `STATE_ProcessGame` (0x203028) thấy `fpProcess/fpDraw` NULL nên không gọi gì; khung sau `STATE_NextStartProcess` đổi sang MainMenu và `STATE_ExitGame` chỉ `InputLayer_Destroy`.

## 3. Đề xuất (đích an toàn = menu chính, giống nút thoát của game)

Logic: `if (!SAVE_Load()) { GAMESTATE_SetState(4); goto tail(0x1382c4); }`, ngược lại đi tiếp đường cũ (0x138268). Không thêm thông báo (xem mục 6).

Không có khe đệm ≥16 B trong segment R-X (khe giữa section chỉ 8/12 B) và không thể chèn 3–4 lệnh tại chỗ, nên cần cave. Cave đề xuất = **thân `SAVE_SaveData` (0x1d5b88, 172 B)**, thoả điều kiện PLAN (đã kiểm):
- không có caller `bl/b` (xref.py: `(khong co)`), không có caller qua PLT (không có reloc nào mang tên `SAVE_SaveData`, `grep` `.rela.plt/.rela.dyn` trống);
- không có reloc `R_AARCH64_RELATIVE` trỏ 0x1d5b88, không có qword = 0x1d5b88 trong phần dữ liệu, không có `adrp` tới trang 0x1d5000, không có `adr` hay nhánh vào [0x1d5b88, 0x1d5c34) (chỉ `b.ne 0x1d5b84` nằm trong `SAVE_SaveETC`, trước biên);
- không có chuỗi `SAVE_SaveData` trong smali hay `libHercules.so`; không phải tên `Java_*` / `RegisterNatives`.

Patch (chưa áp):

| vaddr | trước | sau (byte) | lệnh sau |
|---|---|---|---|
| 0x138264 | `7fc60594` `bl SAVE_Load@plt` | `49760214` | `b 0x1d5b88` |
| 0x1d5b88 (28 B) | `ff4307d1 fd7b19a9 fc5f1aa9 f6571ba9 f44f1ca9 fd430691 57d03bd5` (đầu thân cũ) | `36500394 1f1c0072 40000054 b589fd17 80008052 29500394 c989fd17` | xem dưới |

Cave (đã dựng và disasm bằng `llvm-objdump` trên một bản sao rời, không dùng cho APK):

```
1d5b88  bl   0x2a9c60 <SAVE_Load@plt>        ; lệnh bị thay ở 0x138264
1d5b8c  tst  w0, #0xff
1d5b90  b.eq 0x1d5b98
1d5b94  b    0x138268                         ; thành công: đường cũ (adrp x8..; ldr w19,[x8])
1d5b98  mov  w0, #0x4
1d5b9c  bl   0x2a9c40 <GAMESTATE_SetState@plt>
1d5ba0  b    0x1382c4                         ; đuôi chung của ProcessMapChange (clamp alpha, ++nFrame, ret)
```

Thanh ghi: `x19` (map id) và `x20` (GOT `MAPCHANGE_nFrame`, dùng ở 0x1382f8) là callee-saved, qua `bl` không đổi; nhánh lỗi không dùng `w19`. Phần thân cũ của `SAVE_SaveData` sau byte thứ 28 là mã chết, giữ nguyên. Kích thước symbol không đổi. Cách áp: thêm hai mục vào `PATCHES` của `scripts/patch_lib.py` (old/new như bảng; nên thêm `refs` là các lệnh đích 0x138268 và 0x1382c4 đọc từ lib), `build_from_apk.sh --lib` đã sẵn.

## 4. Phương án đã loại

- Vá trong `SAVE_Load`: epilogue chung (canary, `ldp`, `ret`) nằm sau, gọi `GAMESTATE_SetState` từ giữa hàm làm hỏng khung/LR; chỉ còn 2 lệnh ở 0x1d6968.
- Chặn trong `GAMESTATE_EnterPlay`/`CHAR_GetSkillPoint` (kiểm NULL): `EnterPlay` chạy bên trong `SetState(0)` sau khi `fp*` đã đặt sang Play; gọi `SetState(4)` lồng vào sẽ chạy `ExitPlay` trên hero NULL. Che lỗi bằng cách trả 0 thì vẫn vào Play với hero NULL và văng chỗ khác.
- Đổi `SetState(0)` ở 0x13823c thành tham số động: không đủ chỗ (cần ~5 lệnh, chỉ có 1).

## 5. Kiểm chứng đề xuất (khi được duyệt)

Fixture tự nhiên: `logs/save_TEST7/d41d8cd98f00b204e9800998ecf8427e/save0.dat` **vẫn không giải mã được** kể cả sau P1 (`save_crypto.py`: Σthô sai) ⇒ dùng làm save hỏng. Cần T2 (unidbg qua màn chọn save) để bấm ô 0: mong đợi về lại MainMenu, không `UC_ERR`/SIGSEGV; sau đó chọn ô khác/game mới vào được. Có thể dùng `-Demu.tracefn=GAMESTATE_SetState,SAVE_Load,GAME_Exit` để thấy thứ tự gọi `SAVE_Load → GAMESTATE_SetState(4) → GAME_Exit`.

## 6. CHƯA XÁC ĐỊNH

- Chưa chạy thật (T2 chưa xong): đường `SetState(4)` lúc đang ở MapChange (sau `STATE_EnterGame` đã `UIPlay_Create`) mới được kiểm bằng đọc disasm; trong game gốc nó luôn được gọi từ trạng thái Play.
- `SAVE_LoadFile` có thể đã ghi một phần biến toàn cục (Player) trước khi lỗi; lần `GAME_StartResumeGame` kế tiếp khởi tạo lại các pool nên không thấy hệ quả, nhưng chưa chạy thật.
- Thông báo cho người chơi: bản tiếng Việt có chuỗi "Không có dữ liệu đã lưu." (`dich/text_can_dich.tsv` id 35330); chưa tra ánh xạ id này sang chỉ số `MEMORYTEXT_GetText`, và `GAME_Exit` gọi `INSTANTMSGSYSTEM_Unload` nên thông báo tức thời sẽ bị xoá ngay. Muốn hiện lời nhắn cần đặt cờ toàn cục để MainMenu hiển thị popup (việc riêng, không nằm trong đề xuất này).
- P2 chỉ bảo vệ khỏi văng; save đã hỏng vẫn không khôi phục được (xem `docs/save_format.md` mục 5).
