# T8a: bàn giao đọc trước của A2 (nguyên văn phần patch, 2026-10-31)

Đây là dữ liệu bàn giao, **chưa chạy, chưa kiểm**. Quyết định của quản đốc nằm ở PLAN.md, mục T8, và được ưu tiên hơn file này.

Quản đốc đã đổi một điểm so với bản dưới: trần sát thương ở cave A là **999.999** (`mov w13,#0x423f; movk w13,#0xf,lsl#16`), không phải 0x7FFFFFFF. Vì vậy hex của A phải dựng lại bằng `llvm-mc`.

## Điểm vá

| Điểm | vaddr | Byte gốc | Byte mới | Ghi chú |
|---|---|---|---|---|
| A | 0x18b3e0 | `ff c3 01 d1` (sub sp,sp,#0x70) | `c8 01 00 14` (b 0x18bb00) | x9–x13 tự do |
| B | 0x1c7860 | `c8 0d 80 52` (mov w8,#0x6e) | `c5 10 ff 17` (b 0x18bb74) | x9–x12 chết |
| C | 0x1c7a44 | `f3 03 00 2a` (mov w19,w0) | `59 10 ff 17` (b 0x18bba8) | x8 sống, cave chỉ dùng x9–x12 |

Cave 228 B tại 0x18bb00..0x18bbe4. sha256[:16] của byte gốc là `e35d270c3ba4b6ee`. Nội dung cave nối A, B, C liên tiếp.

### A (29 lệnh, 116 B; bản của A2, trần 0x7FFFFFFF)
```
sub sp,sp,#0x70; cmp w2,#1; b.lt R; ldrb w9,[x1,#0x311]; cbnz w9,ATK;
adrp x10,0x72f000; ldr w11,[x10,#0x784]; sub w12,w11,#2; cmp w12,#48; b.hi R;
udiv w12,w2,w11; cmp w12,#0; csinc w2,w12,wzr,ne; b R;
ATK: cbz x0,R; ldrb w9,[x0,#0x311]; cbnz w9,R; adrp x10,0x72f000; ldr w11,[x10,#0x780];
sub w12,w11,#2; cmp w12,#48; b.hi R; smull x12,w2,w11; mov w13,#0xffff; movk w13,#0x7fff,lsl#16;
cmp x12,x13; csel x12,x12,x13,lt; mov w2,w12;
R: b 0x18b3e4
```
hex: `ffc301d15f0400714b03005429444c39490100352a2d00904b8547b96c0900519fc10071680200544c08cb1a9f01007182159f1a0f000014c00100b409444c39890100352a2d00904b8147b96c0900519fc10071e80000544c7c2b9bedff9f52edffaf729f010deb8cb18d9ae2030c2a1dfeff17`

### B (13 lệnh)
```
mov w8,#0x6e; adrp x9,0x72f000; ldr w10,[x9,#0x788]; sub w11,w10,#2; cmp w11,#48; b.hi R;
smull x11,w19,w10; mov w12,#0xffff; movk w12,#0xff,lsl#16; cmp x11,x12; csel x11,x11,x12,lt; mov w19,w11;
R: b 0x1c7864
```
hex: `c80d8052292d00902a8947b94b0900517fc10071e80000546b7e2a9becff9f52ec1fa0727f010ceb6bb18c9af3030b2a30ef0014`

### C (15 lệnh)
```
mov w19,w0; cmp w19,#1; b.lt R; (rồi giống B) ... R: b 0x1c7a48
```
hex: `f303002a7f0600718b010054292d00902a8947b94b0900517fc10071e80000546b7e2a9becff9f52ec1fa0727f010ceb6bb18c9af3030b2a9aef0014`

## Bằng chứng khác (tóm tắt)
- Phe là byte `[char+0x311]`: 0 = người chơi, 1 = quái, 2 = NPC.
  - Giá trị 0 được ghi bởi `PARTY_AddMember` 0x1cde68, `PARTY_Include` 0x1cdf98, `PLAYER_SetMainPlayer` 0x1cff90, `SAVE_LoadCharacter[All]` và `CHARSYSTEM_Produce` 0x19fdf8.
  - Giá trị 1: 0x1a0028. Giá trị 2: 0x1a006c và `PARTY_Exclude` 0x1ce18c.
  - Triệu hồi chép phe của chủ (`CHAR_Act_Summon` 0x191948).
- Caller của `CHAR_AddDamage` (11 chỗ):
  - `CHAR_Act_DD` (gọi ở 0x18d098), `CHAR_Act_M_DD` (gọi ở 0x18f260);
  - phản đòn ở 0x18e56c, 0x18eb20, 0x18fbf0;
  - nguyên tố ở 0x18c0c8, 0x18c1a0, 0x18c39c;
  - `CHAR_ApplyDot` (0x1915b4; kẻ tấn công có thể NULL);
  - `CHAR_ProcessActUnit` 0x194b18.
- Tràn số:
  - `dmg×80` ở 0x18b48c, `[atk+0x19c]×dmg` ở 0x18b4e8;
  - `w19×110` ở 0x1c7868 (EXP).
- Vùng nhớ 0x72f780..0x72f78c:
  - `.bss` kết thúc ở 0x72f770; 0 adrp vào trang 0x72f000/0x730000;
  - reloc RELATIVE duy nhất gần đó có addend 0x72f770, nằm ở 0x2cfbc0.
- Java:
  - `MainActivity.onResume` (`.locals 1`) chèn sau `invoke-static {p0}, Lcom/hive/HiveActivity;->onResume(Landroid/app/Activity;)V`;
  - `mGLSurfaceView` là `CCustomGLSurfaceView` trong FrameLayout `GLViewLayout` của `R.layout.main`;
  - lấy lại immersive ở `onWindowFocusChanged`.
- `hiddenapi-flags.csv` (AOSP prebuilts/runtime): `Lsun/misc/Unsafe;->theUnsafe`, `putInt(JI)V`, `getInt(J)I` đều mang cờ `unsupported`. targetSdk của app là 36.
