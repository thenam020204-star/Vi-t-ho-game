# Định dạng file save Inotia 4 và lỗi khoá rỗng

Nguồn: disasm `libgame.so` của TEST4 (`/opt/work/lib4/dis.txt`, ARM64), file save thật `logs/save_TEST7/` (xuất từ máy người dùng bằng TEST7), công cụ `scripts/re/save_crypto.py`. Mọi địa chỉ là vaddr trong `libgame.so` (file offset = vaddr ở LOAD đầu). Điểm chưa chắc ghi "CHƯA XÁC ĐỊNH".

## 1. Các file và khoá

| File | Hàm ghi / đọc | Khoá (4th arg `ENCRYPT_Process2`) | Ghi chú |
|---|---|---|---|
| `<MD5(khoá)>/save%d.dat` (`save%d.dat` @0xa5ddb) | `SAVE_Save` 0x1d4f3c → `SAVE_SaveDataAsNameAndKey` 0x1d8d3c; đọc `SAVE_LoadData` 0x1d5e2c → `SAVE_LoadDataAsNameAndKey` | `HubSave_GetKey()` 0x12b09c = buffer `HubSave_key` @0x2d0ca1, **64 byte** | Offline buffer rỗng ⇒ thư mục `d41d8cd98f00b204e9800998ecf8427e` = MD5(""). |
| `<MD5("394572")>A/thpcheckslot1..3.dat` (@0xaa598, 0xaaf0d, 0xa76f9) | cùng cặp hàm `...AsNameAndKey` | chuỗi `"394572"` | Mẫu: `logs/save_TEST7/edf2113c684f2b822de06dad8d06b016A/thpcheckslot1.dat` (31 B). Chữ `A` cuối tên thư mục: nguồn chưa đọc, CHƯA XÁC ĐỊNH. |
| `appinfo.dat` (@0xa7bb9) | `APPINFO_Save` / `APPINFO_Load` 0x17dd68 | `UTIL_GetPhoneNumber()` 0x1f0038 = buffer `UTIL_pPhoneNumber` @0x2fbbe9 | Mẫu 5 B (n=2). Giải mã với khoá rỗng thì Σthô sai ⇒ khoá lúc ghi không phải chuỗi rỗng; khoá thật CHƯA XÁC ĐỊNH. |
| `envinfo.dat` (@0xa5b05) | chưa tra | chưa tra | Mẫu 70 B, gần như toàn 0; có vẻ không mã hoá; CHƯA XÁC ĐỊNH. |
| NetworkStoreConfirm | `NetworkStoreConfirm_Save/Load` | CHƯA XÁC ĐỊNH | Cũng gọi `ENCRYPT_Process2`. |

`HubSave_GetFolderName` 0x12b198 = MD5 hex 32 ký tự của khoá (`CC_MD5` + `%02x`). `HubSave_GetKey` chỉ nạp khoá từ app property `Hub2uniqueUserId` / `Hub2uniqueDeviceId` khi `HubSave_keyType==2`; bản offline không đi vào nhánh này nên trả buffer rỗng.

`ENCRYPT_Process2` chỉ có 6 caller trực tiếp: `APPINFO_Load/Save`, `NetworkStoreConfirm_Load/Save`, `SAVE_LoadDataAsNameAndKey`, `SAVE_SaveDataAsNameAndKey` (`scripts/re/xref.py`). `SAVE_SaveData` 0x1d5b88 không có caller.

## 2. Thuật toán `ENCRYPT_Process2(buf, n, decrypt, key)` @0x140970 (408 B)

- Tham số: `x0=buf`, `w1=n`, `w2` (0 = mã hoá, ≠0 = giải mã), `x3=key` (NULL ⇒ khoá mặc định `"123"` dựng trên stack, 0x1409a8–0x1409b4).
- `L = strlen(key)` @0x1409bc. Bảng `T` 256 byte @0xb1bb0 (đọc bằng `adr` trong `ENCRYPT_GetKey` 0x140960 và `ENCRYPT_Process2`).
- Bộ đệm có `n+3` byte: `buf[0..n)` dữ liệu, `buf[n] = Σmã`, `buf[n+1] = r`, `buf[n+2] = Σthô` (mọi tổng mod 256).
- Mã hoá (`w2==0`, 0x140a24–0x140ad8): `r = MATH_GetRandom(0,255)` ghi vào `buf[n+1]`; với `i=0..n-1`: `idx=r+i`, `k = (key[idx mod L] + T[idx & 255]) & 255`, `c_i = p_i ^ k`; `Σthô = Σp_i`, `Σmã = Σc_i`.
- Giải mã (`w2!=0`, 0x1409c4–0x140ab8): `r = buf[n+1]`; `p_i = c_i ^ k` cùng công thức; trả `true` ⇔ `buf[n]==Σmã tính` và `buf[n+2]==Σthô tính`. Nếu `n<1` thì so với 0.
- Mod: `sdiv w14,w11,w0 ; msub w14,w14,w0,w11` (0x1409e8/0x140a00 giải mã; 0x140a5c/0x140a74 mã hoá). `ldrb w14,[x20,w14]` (0x140a08 / 0x140a7c) đọc `key[idx mod L]`.

Mẫu kiểm chứng (`thpcheckslot1.dat`, khoá `394572`): `r=230`, `Σmã=186`, `Σthô=68`, bản thô `2|000001|99|99|99|99|99|END|` (28 B).

## 3. Cấu trúc bản thô của `saveN.dat`

Bộ đệm do `SAVE_Save` cấp `MEM_Malloc(0x2800)` (0x1d5014); `p+0..8` **không được ghi** (rác malloc; mẫu: `20 04 20 04 00 04 00 04`), `SAVE_LoadFile` không đọc. Từ `p+8`: bảng 7 mục `(u16 off, u16 size)` little-endian, `off` tính từ `p+8`:

| # | Mục | Ghi (`SAVE_Save`) | Đọc |
|---|---|---|---|
| 0 | Info | `SAVE_Save` ghi trực tiếp tại `p+0x24` | `SAVE_LoadFile` 0x1d5c5c |
| 1 | Player | `SAVE_SavePlayer` | `SAVE_LoadPlayer` |
| 2 | Chars | `SAVE_SaveCharacterAll` | `SAVE_LoadCharacterAll` (không do `SAVE_LoadFile`) |
| 3 | Inventory | `SAVE_SaveInventory` | `SAVE_LoadInventory` (phải trả true) |
| 4 | Quest | `SAVE_SaveQuest` | `SAVE_LoadQuest` (phải trả true) |
| 5 | Event | `SAVE_SaveEvent` | `SAVE_LoadEvent` (phải trả true) |
| 6 | ETC | `SAVE_SaveETC` | `SAVE_LoadETC` |

Các mục liền nhau: `off[0]=28` (ngay sau bảng 7×4 = 28 B), `off[k+1]=off[k]+size[k]`, `8+off[6]+size[6] = n`. Mẫu `save0.dat` (n=3189): Info 28/33, Player 61/429, Chars 490/242, Inventory 732/96, Quest 828/150, Event 978/90, ETC 1068/2113 — khớp tuyệt đối.

Info (33 B, `SAVE_Save` 0x1d5040–0x1d50a4 và `SAVE_LoadFile` 0x1d5cc4–0x1d5d58): `u8 slot` (phải bằng ô đang nạp, GOT 0x2bf4c8), `u32 a` (GOT 0x2c00c0), `u64 b` (GOT 0x2c00b8), `u64 t` = `CS_knlCurrentTime()` lúc lưu, `u32 140` (đọc rồi bỏ), `u32 20132` (GOT 0x2c05f8), `u32 version = 5` (GOT 0x2c05e8). `SAVE_LoadFile` từ chối nếu `version > 5` hoặc `slot` lệch. Ý nghĩa `a`, `b`, `20132` CHƯA XÁC ĐỊNH.

## 4. Lỗi khoá rỗng

Khoá rỗng ⇒ `L=0` ⇒ `sdiv` cho 0 ⇒ `msub` trả nguyên `idx=r+i` ⇒ `key[idx]` đọc **vượt buffer `HubSave_key` (64 B)** sang các biến toàn cục kế tiếp (`gImgUI` @0x2d0ce8 784 B, `g_pSGL*` @0x2d1408.., `s_cachedTexture` @0x2d1448...). Các biến này chứa **con trỏ heap Android có tag byte cao** (`0xb4..`, `0x54..`...) đổi theo mỗi lần chạy ⇒ keystream lúc lưu ≠ lúc nạp ở tiến trình khác ⇒ `Σthô` sai ⇒ `SAVE_LoadData` false ⇒ `PLAYER_pMainPlayer` NULL ⇒ văng (xem PLAN T3).

Bằng chứng từ `save0.dat` (`save_crypto.py --key ""`, tức keystream = T):

1. `Σmã` khớp (173), `Σthô` lưu 16, tính 226 (SAI): file nguyên vẹn nhưng keystream không phải T.
2. Bảng 7 mục (`p+8..p+36`) vẫn giải mã đúng bằng T, tức byte nhớ tại `idx=r+8..r+35 = 147..174` lúc lưu bằng 0 (suy ra từ việc bảng hợp lệ, không đo trực tiếp).
3. Known-plaintext: `SAVE_Save` ghi hằng `140, 20132, 5` tại Info+21..+32. Giải mã bằng T sai 8/12 byte. Từ đó suy ra chính xác byte bộ nhớ tại `HubSave_key+idx` lúc lưu: `0x2d0d67=0xb4`, `0x2d0d68=0x90`, `0x2d0d69=0x36`, `0x2d0d6a=0xdb`, `0x2d0d6b=0x7f`, `0x2d0d6c=0x76`, `0x2d0d6f=0xb4`, `0x2d0d70=0xd0`. Hai byte `0xb4` cách nhau đúng 8 byte ở byte cao của hai qword liên tiếp (0x2d0d60 và 0x2d0d68) trong `gImgUI`: đúng dạng con trỏ heap có tag.
4. Quét heuristic (qword `xx 00 00 tag` chia hết 8): 20 vị trí, tất cả rơi vào `gImgUI` (11), `g_pSGL..g_pSGLFontText` (7 con trỏ liên tiếp @0x2d1408–0x2d1438) và `s_cachedTexture` (2) — toàn biến kiểu con trỏ.

Lệnh tái hiện:

```
python3 scripts/re/save_crypto.py /opt/work/lib4/libgame.so logs/save_TEST7/edf2113c684f2b822de06dad8d06b016A/thpcheckslot1.dat --key 394572   # Σthô KHỚP
python3 scripts/re/save_crypto.py /opt/work/lib4/libgame.so logs/save_TEST7/d41d8cd98f00b204e9800998ecf8427e/save0.dat --key ""                 # Σthô SAI + vị trí chênh
python3 scripts/re/save_crypto.py /opt/work/lib4/libgame.so --selftest                                                                         # KAT + roundtrip
```

## 5. Hướng sửa P1 (đã chốt) và hệ quả

P1 coi khoá rỗng như độ dài 1 (`key[0]=NUL` ⇒ `k = T[(r+i)&255]`): `save_crypto.py --key "" --oob zero` mô phỏng đúng hành vi này. Khoá không rỗng không đổi (tương thích `thpcheckslot`). Save đã tạo bằng bản không vá (`save0.dat` của TEST7) **không giải mã được** vì byte nhớ lúc lưu không còn: chỉ khôi phục được vài byte nhờ known-plaintext, không đủ.

## 6. `scripts/re/save_crypto.py`

```
save_crypto.py <libgame.so> <file> --key STR [--oob zero|file:PATH[@OFF]] [--encrypt] [--r N] [--out PATH]
               [--null-key] [--key-hex HEX] [--table-vaddr 0x..]
save_crypto.py <libgame.so> --selftest
```
Bảng `T` lấy từ lib (lệnh `adr` trong `ENCRYPT_GetKey`, đối chiếu `ENCRYPT_Process2`). `--oob file:PATH` nạp dump bộ nhớ bắt đầu tại địa chỉ khoá để mô phỏng bản gốc đọc vượt buffer (dùng ở T3c). Mã thoát 0 = khớp, 1 = sai tổng kiểm.
