# Mod menu (T8): hệ số sát thương / phòng thủ / EXP x1–x50

Tài liệu của T8a (RE + thiết kế). Mọi con số ở đây đã được kiểm lại bằng disasm trên `libgame.so` của TEST4
(sha256 `df12cecc7752bcba55157fffe7989cf8f9d8bf6344b211fb3ec41122443a5d02`, 2.917.904 B) bằng
`scripts/re/mod_check.py` (log: `logs/t8a_mod_check.log`, kết quả cuối: **TẤT CẢ ĐẠT**).
Bản nháp của A2 (`docs/t8a_bangiao_A2.md`) chỉ dùng làm điểm xuất phát; chỗ nào khác A2 được ghi rõ ở mục 9.

Lệnh tái hiện:

```
python3 -I scripts/re/mod_check.py /opt/work/lib4/libgame.so /opt/work/lib4/dis.txt     # bằng chứng
bash scripts/asm/build_mod_cave.sh                                                       # dựng cave bằng llvm-mc + ld.lld
python3 scripts/patch_lib.py --list                                                      # bảng patch (T8b)
```

## 1. Tóm tắt

| Việc | Cách |
|---|---|
| Sát thương người chơi gây ra × Mdmg | điểm A `CHAR_AddDamage` 0x18b3e0, kẻ tấn công ≠ NULL và phe 0, trần 999.999 |
| Sát thương người chơi nhận ÷ Mdef | cùng điểm A, mục tiêu phe 0, kết quả tối thiểu 1 |
| EXP × Mexp | điểm B `MERCENARYSYSTEM_AddExperience` 0x1c7860 (giết quái) và điểm C `MERCENARYSYSTEM_AddExpFixed` 0x1c7a44 (thưởng nhiệm vụ), trần 0xFFFFFF |
| Hệ số | int32 ở 0x72f780 (Mdmg), 0x72f784 (Mdef), 0x72f788 (Mexp); chỉ nhận 2..50, giá trị khác (kể cả 0 mặc định) = 1 = như game gốc |
| Kênh Java → native | `/proc/self/mem` (chính), `sun.misc.Unsafe` (dự phòng); ghi xong đọc lại để kiểm |
| Cave | 236 B = 59 lệnh tại 0x18bb00..0x18bbec, mượn thân 2 hàm chết `CHAR_AddDamageEffect` + `CHAR_AddDamageByDefaultCheck` |

## 2. Bằng chứng (đã kiểm bằng disasm)

### 2.1 `CHAR_AddDamage` 0x18b3e0 (1124 B)

- Chữ ký (từ prologue 0x18b3e0..0x18b43c): `x0` = kẻ tấn công (có thể NULL: `cbz x21,0x18b4d8` @0x18b448), `x1` = mục tiêu, `w2` = sát thương int32, `w3` (`mov w20,w3`), `w4` (`mov w25,w4`, cờ chí mạng).
- `cmp w2,#1; b.lt 0x18b7e4` @0x18b404/0x18b40c: sát thương < 1 thì thoát. Ngay sau đó đọc `ldrb w8,[x1,#0x2c9]` (không kiểm NULL), nên cave đọc `[x1,#0x311]` ở cùng thứ tự không thêm rủi ro.
- 0x18b420 `neg w1,w2` rồi `bl CHAR_AddLife@plt` @0x18b434 ⇒ máu bị trừ đúng `w2`.
- `CHAR_AddLife` 0x18b844: máu hiện tại `[x+0x1f0]` và tối đa `[x+0x9c]` đều int32; `add w23,w8,w20` rồi kẹp vào [0, tối đa] (0x18ba70..0x18ba80). Không có trần sát thương riêng; không tìm thấy hằng 99.999/999.999/0x7FFFFFFF dùng làm trần sát thương (tìm `#0x1869f`, `#0x423f`, `#0x967f`).
- 11 chỗ gọi (`bl <CHAR_AddDamage@plt>`; 10 chỗ sống + 1 trong hàm chết):

| chỗ gọi | hàm | x0 / x1 |
|---|---|---|
| 0x18c0c8 | `CHAR_AttrAttackDamageByType` | x0 = x20, x1 = mục tiêu (kiểm `[x1]==1`) |
| 0x18c1a0, 0x18c39c | `CHAR_AttrAttackDamage` | x0 = x20, x1 = x21 / `[x21]` |
| 0x18d098 | `CHAR_Act_DD` | x0 = x21 (người đánh), x1 = x19 (mục tiêu), w2 = kết quả `CHAR_SuccessOnPhysicalAttack` |
| 0x18e56c, 0x18eb20 | `CHAR_SuccessOnPhysicalAttack` | x0 = x19/x20, x1 = x20/x22 |
| 0x18f260 | `CHAR_Act_M_DD` | x0 = x21, x1 = x19 |
| 0x18fbf0 | `CHAR_SuccessOnMagicalAttack` | |
| 0x1915b4 | `CHAR_ApplyDot` | x0 = `[x19+0x10]` (có thể NULL), w3 = 8 |
| 0x194b18 | `CHAR_ProcessActUnit` | x0 = x22, x1 = x21 |
| 0x18bbe8 | `CHAR_AddDamageByDefaultCheck` | hàm chết, chính là phần cave (xem 2.5) |

  Mọi đường sát thương chiến đấu đi qua đúng cửa này ⇒ một điểm chặn là đủ. Sát thương kịch bản `EVTSYSTEM_Process` (`AddLife(-N)`) và DoT không có kẻ tấn công không được phủ (xem mục 8).

### 2.2 Phe nhân vật = byte `[char+0x311]`

Có 16 chỗ ghi (liệt kê trong log, mục [5]). Giá trị:

- **0 = người chơi/đồng minh**: `PARTY_AddMember` 0x1cde68, `PARTY_Include` 0x1cdf98, `PARTY_SetMember` 0x1cdd60, `MERCENARYSYSTEM_IncludeParty` 0x1c74ac, `PLAYER_SetMainPlayer` 0x1cff90, `SAVE_LoadCharacter` 0x1d7c2c, `SAVE_LoadCharacterAll` 0x1d8a40/0x1d8ab8, `CHARSYSTEM_Produce` 0x19fdf8 (nhánh người chơi).
- **1 = quái** (`CHARSYSTEM_Produce` 0x1a0028, `w20` = 1) và **2 = NPC** (0x1a006c, `mov w8,#2`; `PARTY_Exclude` 0x1ce18c).
- `CHAR_Act_Summon` 0x191948 chép phe của chủ (triệu hồi cùng phe).
- **Bổ sung so với A2** (A2 chưa liệt kê): `CHARLOCSYSTEM_Load` 0x19f358/0x19f484 đặt phe theo dữ liệu vị trí trên bản đồ (loại 0 hoặc id 0xee..0xf1 ⇒ phe 0; loại 1 ⇒ phe 1; khác ⇒ phe 2), `EVTSYSTEM_Process` 0x1a7668 cho phép kịch bản đổi phe lúc chạy. Hệ quả: nhân vật đồng minh do bản đồ/kịch bản đặt (phe 0) cũng được nhân/giảm như người chơi. Đây đúng nghĩa "phe người chơi".
- 36 hàm đọc byte này (AI chọn mục tiêu, `CHAR_IsHostile`...), không ảnh hưởng vì cave không ghi byte này.

### 2.3 Phòng thủ gốc

Phòng thủ gốc là phần trăm giảm: `dmg×(1000−def‰)/1000` (`CHAR_Act_DD` 0x18d030..0x18d050; `CHAR_CalcMagicalDefense` 0x18bdb0 chặn `def‰` ≤ 750 tại 0x18bde4..0x18bdf4; phép chia dùng `0x10624dd3 >> 38` ≈ ÷1000). Vì giảm tối đa 75 %, không thể "phòng thủ ×50" ⇒ chia sát thương nhận cho Mdef, đúng quyết định của quản đốc.

### 2.4 EXP

- `MERCENARYSYSTEM_AddExperience` 0x1c7688: caller duy nhất `CHARSYSTEM_Die` 0x1a0c2c. `w19` = EXP cơ sở (≥ 1 qua `cmp w0,#1; b.lt` @0x1c7754; chỉnh theo chênh lệch cấp ở 0x1c7770..0x1c77b0 và buff 0x1c7840..0x1c785c). Từ 0x1c7860: `w25 = w19×110/100` (0x1c7868..0x1c7880), rồi chia cho 3 thành viên party (0x1c78b8/0x1c7918/0x1c7970 `CHAR_AddExperience`) và cộng dồn cho lính ngủ (0x1c79e8 `add w11,w11,w19`). Nhân `w19` ngay tại 0x1c7860 kéo theo `w25` và mọi người nhận.
- `MERCENARYSYSTEM_AddExpFixed` 0x1c7a2c: caller duy nhất `QUESTSYSTEM_ApplyReward` 0x1d2718. `w19 = w0` @0x1c7a44, dùng cho `CHAR_AddExperience` (0x1c7a9c/0x1c7af4/0x1c7b44) và cộng dồn lính ngủ (0x1c7bc4).
- `CHAR_AddExperience` 0x183964 **không hook**: có thêm 3 caller nạp EXP tồn của lính (`UIMercenary_CreateList` 0x15c974, `UIMercenary_Enter` 0x15d504, `SAVE_LoadCharacterDirectEx` 0x1d870c); hook ở đó sẽ nhân đôi EXP đã nhân.
- Trong `CHAR_AddExperience`: `w26 = w21 + [char+0x318]` (int32); lên cấp lặp bằng `CAL_Calculate` và kẹp ở cấp tối đa (0x183acc..0x183af0) nên EXP lớn không gây vòng lặp vô hạn.

### 2.5 Cave: hai hàm chết

`CHAR_AddDamageEffect` 0x18bb00 (52 B) và `CHAR_AddDamageByDefaultCheck` 0x18bb34 (184 B), cộng lại 0x18bb00..0x18bbec (236 B; hàm kế tiếp `CHAR_CalcPhysicalDefense` bắt đầu ở 0x18bbec). `mod_check.py` mục [3] (đều ĐẠT):

- 0 lệnh ngoài cave nhảy/`adr`/`ldr literal` vào cave; 0 `adrp` vào trang 0x18b000;
- 0 reloc (JUMP_SLOT/GLOB_DAT) theo tên 2 hàm; 0 reloc RELATIVE có addend trong cave; không có PLT stub (`<CHAR_AddDamageEffect@plt>` không tồn tại; `CHAR_AddDamage@plt` có, 2 hàm này không);
- giá trị 64 bit trỏ vào cave chỉ có 2 mục trong `.dynsym` (dòng symbol xuất khẩu của chính 2 hàm, st_value);
- tên 2 hàm chỉ xuất hiện trong `.dynstr` (0x5873c, 0x58751 ∈ [0x46540, 0x7815a)), không có chuỗi trong `.rodata` để `dlsym` theo chuỗi; `libHercules.so` không import `CHAR_*`;
- Java chỉ gọi `Java_*`/`RegisterNatives`, tên 2 hàm này không phải JNI.
- Chênh so với A2: cave dùng cả 8 B cuối (0x18bbe4..0x18bbec = `ldp x29,x30,[sp],#0x30` và `b CHAR_AddDamage@plt` của hàm chết). Không có lệnh nào nhảy vào 2 địa chỉ này (kiểm trong mục [3]).
- Lưu ý: `.eh_frame` vẫn mô tả 2 hàm cũ (FDE). Cave không gọi hàm nào và không đổi `sp` ngoài `sub sp,sp,#0x70` của A (đúng như hàm gốc sẽ làm), nên không có unwind nào đi qua cave.

### 2.6 Vùng nhớ hệ số và `PT_LOAD` chứa `.bss`

`llvm-readelf -l`: `PT_LOAD` thứ 3: `p_vaddr = 0x2cc320`, `p_filesz = 0x38a8`, `p_memsz = 0x463450`, `p_align = 0x4000`, cờ RW.

- `p_vaddr + p_memsz = 0x72f770`;
- làm tròn lên trang 4 KB = **0x730000**; làm tròn lên trang 16 KB = **0x730000**; cả hai > 0x72f78c ✓ (và > 0x72f790, đủ chỗ cho int thứ 4 của T9b).
- Vùng 0x72f780..0x72f790 nằm sau `.bss` thật (0x72f770) nhưng trong trang cuối cùng của mapping bss.
- bionic (`linker/linker_phdr.cpp`, tag `android-16.0.0_r4`, `ElfReader::MapBssSection`): phần `.bss` ngoài file được `mmap` ẩn danh `PFLAGS_TO_PROT(p_flags)` (= `PROT_READ|PROT_WRITE`), `seg_page_end = align_up(seg_end, seg_align)` (đặt tên VMA `.bss` bằng `prctl`). Ở chế độ tương thích 16 KB thì cả vùng đặt trước đã là ẩn danh RW. Cả hai đều phủ 0x730000.
- 0 `adrp` vào trang 0x72f000 / 0x730000 / 0x72c000 ⇒ không code nào truy cập theo trang đó; 0 hằng số `#0x72fxxx`; 0 symbol (`.dynsym`) trong 0x72f000..0x730000.
- Reloc duy nhất gần đó: `R_AARCH64_RELATIVE` tại 0x2cfbc0 addend **0x72f770** (< 0x72f780). Cùng 0x2cfbb0, 0x2cfbb8 (addend 0x72ef70) tạo thành bộ ba `{begin, begin, begin+0x800}`: đây là con trỏ "hết vùng đệm" của một vùng 2048 B ở ngay trước (khớp dạng heap dự phòng của libc++abi; **CHƯA XÁC ĐỊNH** ai dùng, nhưng con trỏ kết thúc *trước* 0x72f780 nên không chồng). Khoảng 0x72f770..0x72f780 (16 B) để làm vùng đệm an toàn.
- `.bss` được kernel/loader cấp trang 0 ⇒ ban đầu Mdmg = Mdef = Mexp = 0 ⇒ coi như 1 (game gốc) cho đến khi Java ghi. Mỗi lần mở app tiến trình mới nên `.bss` về 0; Java phải ghi lại mỗi `onResume`.

## 3. Cave: mã asm (dựng bằng `llvm-mc` + `ld.lld`)

Nguồn: `scripts/asm/mod_cave.s`; dựng: `scripts/asm/build_mod_cave.sh` (`llvm-mc -triple=aarch64-linux-gnu -filetype=obj`, rồi `ld.lld -Ttext=0x18bb00 --image-base=0 --defsym=MOD_PAGE=0x72f000 --defsym=BACK_A=0x18b3e4 ...`, rồi `llvm-objcopy -O binary`). sha256 của 236 B cave: `cb64516299bc0de2441d8025c053b08341ac526fd24a088d673c0ac55d659765`. Disasm kiểm lại bằng `llvm-objdump -d` đúng với từng dòng dưới đây (xem T8b, mục 10).

Quy ước chung của cả 3 cave:
- Hệ số hợp lệ ⇔ `(M − 2) ≤u 48` (M ∈ [2,50]); ngoài khoảng thì nhảy thẳng về hàm gốc (không đổi gì).
- Chỉ dùng x9..x13 (và w2 / w19 là đích sửa), không đụng stack ngoài `sub sp,sp,#0x70` của A, không ghi bộ nhớ.
- Trần: sát thương 999.999 (`0x000f423f`), EXP 0xFFFFFF. Nếu giá trị gốc đã ≥ trần thì giữ nguyên ("mod không bao giờ làm yếu hơn game gốc"): đây là chỗ làm rõ so với bản của A2 (A2 kẹp luôn, có thể kéo giá trị gốc > trần xuống trần).
- Nhờ kiểm "giá trị gốc < trần" trước phép nhân nên có thể dùng `mul` 32 bit: dmg ≤ 999.998 × 50 = 49.999.900 và EXP ≤ 0xFFFFFE × 50 = 838.860.700 đều < 2^31.
- So với A2: một `adrp` chung cho cả hai nhánh của A (tiết kiệm 1 lệnh) để cả ba cave vừa 59 lệnh.

**Cave A** @0x18bb00

| vaddr | byte | lệnh | giải thích |
|---|---|---|---|
| 0x18bb00 | `ffc301d1` | `sub sp, sp, #0x70` | lệnh gốc bị thay thế (`sub sp,sp,#0x70`); cave tự làm lại đúng một lần |
| 0x18bb04 | `5f040071` | `cmp w2, #0x1` | so sánh sát thương w2 với 1 |
| 0x18bb08 | `4b030054` | `b.lt 0x18bb70 <A_ret>` | w2 < 1: ra thẳng A_ret; hàm gốc sẽ tự thoát ở `cmp w2,#1; b.lt` @0x18b404. Chưa đọc `[x1]`, đúng thứ tự của hàm gốc |
| 0x18bb0c | `29444c39` | `ldrb w9, [x1, #0x311]` | w9 = phe của MỤC TIÊU `[x1+0x311]` (0 = người chơi/đồng minh). Hàm gốc cũng đọc `[x1+0x2c9]` ngay sau, không kiểm NULL |
| 0x18bb10 | `2a2d0090` | `adrp x10, 0x72f000` | x10 = trang 0x72f000 (chứa 3 hệ số); adrp không đổi cờ |
| 0x18bb14 | `29010035` | `cbnz w9, 0x18bb38 <A_atk>` | mục tiêu không thuộc phe 0 ⇒ nhảy sang nhánh kẻ tấn công |
| 0x18bb18 | `4b8547b9` | `ldr w11, [x10, #0x784]` | w11 = Mdef |
| 0x18bb1c | `6c090051` | `sub w12, w11, #0x2` | w12 = Mdef − 2 |
| 0x18bb20 | `9fc10071` | `cmp w12, #0x30` | so sánh w12 với 48 (không dấu) |
| 0x18bb24 | `68020054` | `b.hi 0x18bb70 <A_ret>` | w12 > 48 (không dấu) ⇔ Mdef ∉ [2,50] (0, 1, âm, > 50) ⇒ không đổi |
| 0x18bb28 | `4c08cb1a` | `udiv w12, w2, w11` | w12 = ⌊dmg / Mdef⌋; Mdef ≥ 2 nên không chia 0 |
| 0x18bb2c | `9f010071` | `cmp w12, #0x0` | w12 có bằng 0 không |
| 0x18bb30 | `82159f1a` | `csinc w2, w12, wzr, ne` | w2 = (w12 ≠ 0) ? w12 : 1 ⇒ tối thiểu 1 |
| 0x18bb34 | `0f000014` | `b 0x18bb70 <A_ret>` | xong nhánh phòng thủ |
| 0x18bb38 | `c00100b4` | `cbz x0, 0x18bb70 <A_ret>` | (A_atk) không có kẻ tấn công (DoT/kịch bản) ⇒ không nhân |
| 0x18bb3c | `09444c39` | `ldrb w9, [x0, #0x311]` | w9 = phe KẺ TẤN CÔNG |
| 0x18bb40 | `89010035` | `cbnz w9, 0x18bb70 <A_ret>` | không phải phe 0 ⇒ không nhân |
| 0x18bb44 | `4b8147b9` | `ldr w11, [x10, #0x780]` | w11 = Mdmg |
| 0x18bb48 | `6c090051` | `sub w12, w11, #0x2` | w12 = Mdmg − 2 |
| 0x18bb4c | `9fc10071` | `cmp w12, #0x30` | so sánh với 48 |
| 0x18bb50 | `08010054` | `b.hi 0x18bb70 <A_ret>` | Mdmg ∉ [2,50] ⇒ không đổi |
| 0x18bb54 | `ed478852` | `mov w13, #0x423f` | w13 = 0x423f |
| 0x18bb58 | `ed01a072` | `movk w13, #0xf, lsl #16` | w13 = 0x000f423f = 999.999 (trần sát thương) |
| 0x18bb5c | `5f000d6b` | `cmp w2, w13` | so sánh sát thương gốc với trần |
| 0x18bb60 | `8a000054` | `b.ge 0x18bb70 <A_ret>` | sát thương gốc đã ≥ trần ⇒ giữ nguyên (mod không bao giờ làm yếu hơn game gốc) |
| 0x18bb64 | `4c7c0b1b` | `mul w12, w2, w11` | w12 = dmg × Mdmg (dmg < 999.999, Mdmg ≤ 50 ⇒ ≤ 49.999.900 < 2^31: không tràn) |
| 0x18bb68 | `9f010d6b` | `cmp w12, w13` | so sánh tích với trần |
| 0x18bb6c | `82b18d1a` | `csel w2, w12, w13, lt` | w2 = min(tích, 999.999) (signed `lt`) |
| 0x18bb70 | `1dfeff17` | `b 0x18b3e4` | (A_ret) quay lại lệnh thứ 2 của hàm gốc (`stp x29,x30,[sp,#0x20]`) |

**Cave B** @0x18bb74

| vaddr | byte | lệnh | giải thích |
|---|---|---|---|
| 0x18bb74 | `c80d8052` | `mov w8, #0x6e` | lệnh gốc bị thay thế (`mov w8,#110`), làm trước mọi nhánh |
| 0x18bb78 | `292d0090` | `adrp x9, 0x72f000` | x9 = trang 0x72f000 |
| 0x18bb7c | `2a8947b9` | `ldr w10, [x9, #0x788]` | w10 = Mexp |
| 0x18bb80 | `4b090051` | `sub w11, w10, #0x2` | w11 = Mexp − 2 |
| 0x18bb84 | `7fc10071` | `cmp w11, #0x30` | so sánh với 48 |
| 0x18bb88 | `08010054` | `b.hi 0x18bba8 <B_ret>` | Mexp ∉ [2,50] ⇒ không đổi |
| 0x18bb8c | `ecff9f52` | `mov w12, #0xffff` | w12 = 0xffff |
| 0x18bb90 | `ec1fa072` | `movk w12, #0xff, lsl #16` | w12 = 0x00ffffff = 16.777.215 (trần EXP) |
| 0x18bb94 | `7f020c6b` | `cmp w19, w12` | so sánh EXP cơ sở w19 với trần |
| 0x18bb98 | `8a000054` | `b.ge 0x18bba8 <B_ret>` | EXP đã ≥ trần ⇒ giữ nguyên |
| 0x18bb9c | `6b7e0a1b` | `mul w11, w19, w10` | w11 = w19 × Mexp (< 0xFFFFFF × 50 = 838.860.750 < 2^31) |
| 0x18bba0 | `7f010c6b` | `cmp w11, w12` | so sánh với trần |
| 0x18bba4 | `73b18c1a` | `csel w19, w11, w12, lt` | w19 = min(tích, 0xFFFFFF); hàm gốc tính tiếp `w25 = w19×110/100` từ w19 mới |
| 0x18bba8 | `2fef0014` | `b 0x1c7864` | (B_ret) về lệnh kế tiếp của hàm gốc (`mov w9,#0x851f` @0x1c7864) |

**Cave C** @0x18bbac

| vaddr | byte | lệnh | giải thích |
|---|---|---|---|
| 0x18bbac | `f303002a` | `mov w19, w0` | lệnh gốc bị thay thế (`mov w19,w0`) |
| 0x18bbb0 | `7f060071` | `cmp w19, #0x1` | EXP cố định ≥ 1 ? |
| 0x18bbb4 | `ab010054` | `b.lt 0x18bbe8` | EXP ≤ 0 (phạt/không thưởng) ⇒ không đổi |
| 0x18bbb8 | `292d0090` | `adrp x9, 0x72f000` | x9 = trang 0x72f000 (KHÔNG dùng x8: `adrp x8` @0x1c7a40 còn sống) |
| 0x18bbbc | `2a8947b9` | `ldr w10, [x9, #0x788]` | w10 = Mexp |
| 0x18bbc0 | `4b090051` | `sub w11, w10, #0x2` | w11 = Mexp − 2 |
| 0x18bbc4 | `7fc10071` | `cmp w11, #0x30` | so sánh với 48 |
| 0x18bbc8 | `08010054` | `b.hi 0x18bbe8` | Mexp ∉ [2,50] ⇒ không đổi |
| 0x18bbcc | `ecff9f52` | `mov w12, #0xffff` | w12 = 0xffff |
| 0x18bbd0 | `ec1fa072` | `movk w12, #0xff, lsl #16` | w12 = 0xffffff |
| 0x18bbd4 | `7f020c6b` | `cmp w19, w12` | so sánh với trần |
| 0x18bbd8 | `8a000054` | `b.ge 0x18bbe8` | đã ≥ trần ⇒ giữ nguyên |
| 0x18bbdc | `6b7e0a1b` | `mul w11, w19, w10` | w11 = w19 × Mexp |
| 0x18bbe0 | `7f010c6b` | `cmp w11, w12` | so sánh với trần |
| 0x18bbe4 | `73b18c1a` | `csel w19, w11, w12, lt` | w19 = min(tích, 0xFFFFFF) |
| 0x18bbe8 | `98ef0014` | `b 0x1c7a48` | (C_ret) về `ldr x8,[x8,#0xbd8]` @0x1c7a48 |

### 3.1 Thanh ghi dùng phải tự do (chứng minh)

Ba tầng bằng chứng, đều trong `mod_check.py` mục [7]:

1. **ABI AArch64 (AAPCS64)**: x9..x15 là thanh ghi tạm do callee tự do ghi; x8 là thanh ghi kết quả gián tiếp. Mã được biên dịch bằng clang, không giả định chúng được giữ qua lời gọi.
2. **Phân tích luồng dữ liệu tại điểm tiếp tục** (duyệt mọi đường của hàm từ lệnh ngay sau cave; "đọc trước khi ghi" là vi phạm; `bl` coi là ghi x0..x18; có đối chứng âm tính: đặt x1..x4 ở A, x19/x22 ở B, x8 ở C thì báo vi phạm đúng):
   - A: x9, x10, x11, x12, x13 tự do tại 0x18b3e4 (33 trạng thái);
   - B: x9..x12 tự do tại 0x1c7864 (9 trạng thái);
   - C: x9..x12 tự do tại 0x1c7a48 (11 trạng thái);
   - x8 sống ở C (`adrp x8` @0x1c7a40, đọc ở 0x1c7a48) ⇒ cave C không dùng x8 (kiểm bằng cách tìm trong nguồn).
3. **Phía người gọi**: sau mỗi trong 12 lệnh `bl` tới 3 hàm được vá (10 + 1 + 1), x9..x13 (x9..x12 với B, C) không bị đọc trước khi ghi trong hàm gọi ⇒ không ai trông cậy giá trị chúng qua lời gọi.
4. **Cờ NZCV**: cave A làm hỏng cờ; từ 0x18b3e4 lệnh `cmp w2,#1` @0x18b404 đặt lại cờ trước mọi lệnh đọc cờ. B, C: sau 0x1c7864 / 0x1c7a48 không có lệnh đọc cờ cho tới `cmp w22,w8` (0x1c78a0) / `cbz` (không đọc cờ). Đọc thủ công từ disasm.
5. x0, x1, w2, w3, w4 (A) và w0 (C): cave không ghi; w2 (A) và w19 (B, C) là giá trị đích.

## 4. Bảng điểm vá

| # | vaddr | byte gốc | byte mới | lệnh mới | lý do |
|---|---|---|---|---|---|
| A | 0x18b3e0 | `ffc301d1` (`sub sp,sp,#0x70`) | `c8010014` | `b 0x18bb00` | vào cave A (sát thương / phòng thủ) |
| B | 0x1c7860 | `c80d8052` (`mov w8,#0x6e`) | `c510ff17` | `b 0x18bb74` | vào cave B (EXP giết quái) |
| C | 0x1c7a44 | `f303002a` (`mov w19,w0`) | `5a10ff17` | `b 0x18bbac` | vào cave C (EXP nhiệm vụ) |
| cave | 0x18bb00..0x18bbec (236 B) | thân 2 hàm chết, sha256 `48718ee58e6904aacd34067ee87fead67afd709899bcc11fa3f276ceaaf73c36` | 59 lệnh ở mục 3, sha256 `cb64516299bc0de2441d8025c053b08341ac526fd24a088d673c0ac55d659765` | | chứa A, B, C |

Lệnh kế tiếp được giữ nguyên (kiểm như `refs` trong `patch_lib.py`): 0x18b3e4 `fd7b02a9`, 0x1c7864 `e9a39052`, 0x1c7a48 `08ed45f9`.
(A2 ghi byte C là `59 10 ff 17` vì cave C của A2 ở 0x18bba8; cave này dài hơn nên C ở 0x18bbac và byte là `5a10ff17`.)

Không có điểm vá nào khác. Byte vùng 0x72f780 không nằm trong file (`.bss`), không vá file.

## 5. Chặn tràn số

| chỗ | kiểu | rủi ro gốc | xử lý |
|---|---|---|---|
| sát thương `w2` | int32 có dấu | nhân 50 có thể vượt 2^31 | chỉ nhân khi `w2 < 999.999`, tích ≤ 49.999.900 (không tràn) rồi kẹp ≤ 999.999 |
| `dmg×80` @0x18b48c (`add w8,w22,w22,lsl#2; lsl w8,w8,#4; umull`) | 32 bit rồi `umull` 64 bit | tràn nếu dmg > 53,6 triệu | 999.999×80 = 79.999.920 < 2^31 ✓ |
| `[atk+0x19c]×dmg` @0x18b4e8 (`mul w8,w8,w24`, rồi `smull ×0x10624dd3 >> 38` ≈ ÷1000; kết quả đưa `HATESYSTEM_Add`) | int32 | tràn nếu `[atk+0x19c] > 2147` khi dmg = 999.999 | **CHƯA XÁC ĐỊNH** giá trị tối đa của `[atk+0x19c]` (thuộc tính 0x5e do `CHAR_UpdateAttr` nạp). Nếu tràn chỉ làm sai lượng "thù hận" (aggro), không đổi HP/EXP |
| `CHAR_AddLife` `add w23,w8,w20` | int32 | `life + (−dmg)` | |dmg| ≤ 999.999 nên không tràn; kẹp [0, maxHP] sẵn có |
| EXP cơ sở `w19` @0x1c7860 | int32 | `w19×110` @0x1c7868 tràn khi w19 > 19.522.092 | chỉ nhân khi `w19 < 0xFFFFFF`; tích < 838.860.750; kẹp ≤ 0xFFFFFF; 0xFFFFFF×110 = 1.845.493.650 < 2^31 ✓ |
| `CHAR_AddExperience` `add w26,w21,w20` | int32 | EXP + EXP tồn | w21 ≤ ~18,5 triệu (kể cả +10 % và +2 %), `[char+0x318]` nhỏ hơn mốc cấp ⇒ không tràn |
| EXP tồn của lính ngủ 0x1c79ec / 0x1c7bc4 (`add w11,w11,w19`) | int32, game không kẹp | cộng dồn nhiều lần | chỉ tràn sau ≥ 128 lần ăn đúng trần 0xFFFFFF (EXP cơ sở ≥ 335 nghìn × 50); không thực tế, ghi nhận |
| Hệ số | int32 trong `.bss` | giá trị rác/ngoài khoảng | `(M−2) ≤u 48` ⇒ coi như 1 |
| Phòng thủ | `udiv` không dấu, dmg ≥ 1, M ≥ 2 | chia 0 / dmg 0 | M ≥ 2 đã kiểm; kết quả `csinc` ⇒ ≥ 1 |

Hiển thị: **CHƯA XÁC ĐỊNH** UI vẽ sát thương nhiều chữ số thế nào (không thấy chỗ đọc `[char+0x1b0]` 32 bit); sát thương tối đa tăng từ ~5 chữ số lên 6 chữ số.

## 6. Kênh Java → native

### 6.1 Chọn: `/proc/self/mem` (chính), `sun.misc.Unsafe.putInt/getInt` (dự phòng)

Lý do chọn (bằng chứng lấy từ mã nguồn AOSP/kernel đã tải về trong phiên này):

- **SELinux** (`system/sepolicy`, `domain.te` dòng 28; có ở `refs/heads/android14-release/public/domain.te`, `refs/tags/android-15.0.0_r36/private/domain.te`, `refs/tags/android-16.0.0_r4/private/domain.te`): `allow domain self:{ fifo_file file } rw_file_perms;`. Tệp `/proc/<pid>/*` mang nhãn domain của chính tiến trình ⇒ app mở/đọc/ghi `/proc/self/mem` được phép (`rw_file_perms` gồm `open read write getattr`).
- **Kernel** (`kernel/common`, nhánh `android16-6.12`, `fs/proc/base.c`): `mem_open` → `proc_mem_open(…, PTRACE_MODE_ATTACH)` → `mm_access` → `__ptrace_may_access` có `if (same_thread_group(task, current)) return 0;` (kernel/ptrace.c dòng 311, ghi chú "Don't let security modules deny introspection") ⇒ truy cập chính mình luôn qua. `mem_rw` ghi bằng `access_remote_vm(mm, addr, page, len, FOLL_WRITE [| FOLL_FORCE])`; `.bss` là VMA `rw-p` (có `VM_WRITE`) nên không cần `FOLL_FORCE`, `proc_mem.force_override` không ảnh hưởng.
- `untrusted_app_all.te`: `allow untrusted_app_all self:process ptrace;` (cùng domain, càng không chặn).
- **Unsafe**: `hiddenapi-flags.csv` ghi `Lsun/misc/Unsafe;->theUnsafe`, `putInt(JI)V`, `getInt(J)I` là `unsupported` (không bị chặn ở targetSdk 36; theo bàn giao của A2).
- seccomp của app cho phép `openat`/`pwrite64`/`lseek` (đều dùng bởi `RandomAccessFile` bình thường).

**Chưa thử trên máy thật** (container không có thiết bị Android). Kernel ở container (6.18) chạy được thử nghiệm tương đương cho `/proc/self/mem` (xem `java/test/ModMemTest.java`, T8c). Độ tin cậy trên máy Xiaomi Android 16: **CHƯA XÁC ĐỊNH** cho tới khi người dùng thử; vì vậy có dự phòng `Unsafe` và thông báo rõ khi cả hai hỏng.

### 6.2 Thuật toán (cài ở `ModMem`, T8c)

1. Đọc `/proc/self/maps`. Tìm dòng có đường dẫn kết thúc bằng `/libgame.so`, offset `00000000`, địa chỉ thấp nhất ⇒ `base`. (`MainActivity.<clinit>` gọi `System.loadLibrary("game")` nên lib đã nạp trước `onResume`. Manifest không đặt `extractNativeLibs` ⇒ mặc định `true`, lib được giải nén ra `lib/arm64/` và `maps` có tên tệp `libgame.so`, offset 0. Nếu sau này đóng gói `extractNativeLibs=false` thì không tìm thấy dòng nào ⇒ báo lỗi, không đoán.)
2. Tìm dòng `maps` phủ `[base+0x72f780, base+0x72f780+12)` (và `+16` nếu có int thứ 4) với quyền **`rw-p`**; không có ⇒ lỗi.
3. Đọc 4 byte tại `base`: phải là `7f 45 4c 46` (magic ELF). Với `/proc/self/mem` đọc được; với `Unsafe` đây là điều kiện bắt buộc trước khi ghi.
4. Ghi 12 byte (3 int little-endian: Mdmg, Mdef, Mexp) tại `base + 0x72f780` bằng `RandomAccessFile("/proc/self/mem","rw")` (`seek` rồi `write`).
5. Đọc lại 12 byte và so sánh; khác ⇒ coi kênh này hỏng.
6. Kênh chính hỏng (ngoại lệ `IOException`/`SecurityException`/đọc lại khác) ⇒ thử `Unsafe` (bước 3 bắt buộc, bước 2 bắt buộc), đọc lại bằng `getInt`.
7. Cả hai hỏng ⇒ Toast "Mod không chạy được trên máy này"; game vẫn chạy (không ném ngoại lệ ra ngoài).

Ghi lại cả 3 giá trị ở mỗi `onResume` vì `.bss` về 0 mỗi lần mở app. Torn write không nguy hiểm: mỗi hệ số ≤ 50 chỉ khác ở byte thấp, nên đọc lẫn giữa giá trị cũ và mới vẫn ∈ [cũ, mới] hoặc ngoài khoảng ⇒ coi như 1.
Java không cấp phát trong vòng lặp game: chỉ ghi khi bấm nút/đổi thanh kéo hoặc `onResume`.

### 6.3 Thiết kế mở rộng cho T9b

`ModMem` ghi `N` int từ mảng (N = 3 ở T8, thêm phần tử thứ 4 cho "Tầm nhìn" @0x72f78c ở T9b). T8 **không** ghi int thứ 4; kiểm `maps` ở T8 đã phủ 16 B.

## 7. Kế hoạch kiểm

| Tầng | Cách | Giai đoạn |
|---|---|---|
| Bằng chứng tĩnh | `mod_check.py` (log `logs/t8a_mod_check.log`) | T8a |
| Byte | `patch_lib.py` (khớp byte gốc / "đã áp", sha256 cave, `refs`), disasm trước/sau, `--diff` đúng vaddr | T8b |
| Hàm đã vá | unidbg `emu_start` tại 0x18b3e0 / 0x1c7860 / 0x1c7a44 với thanh ghi và `.bss` đặt sẵn; hệ số 0, 1, 2, 50, 51 và các biên; đối chiếu công thức (A: đọc `w1 = −dmg` tại 0x18b424; B: đọc `w19`, `w25` tại 0x1c7884; C: đọc `w19` tại 0x1c7a48) | T8d |
| Kênh Java | `java/test/ModMemTest.java` chạy trên JVM của container: ghi `/proc/self/mem` vào vùng heap, đọc lại; phân tích `maps` giả (lib không tìm thấy, vùng không `rw-p`, thiếu magic) | T8c |
| UI Java | biên dịch với `android.jar`, d8, kiểm dex (`dexdump`), soát tay; thử trên máy thật là việc của người dùng | T8c |
| Build | `--smali net,mod --vnfont --lib --diag`; `apkdiff` với TEST9; apksigner v1/v2/v3, `zipalign -c -P 16 4`, `check_apk` | T8d |

Danh sách thử trên máy thật (khi người dùng nhận bản thử): (1) nút MOD hiện ở lề phải, kéo được, không che HUD; (2) mở hộp thoại, kéo 3 thanh, đặt lại x1; (3) Sát thương x10: đánh quái thấy sát thương gấp ~10; (4) Phòng thủ x10: quái đánh đau ÷10, tối thiểu 1; (5) EXP x10; (6) thoát hẳn app, mở lại: giá trị còn và vẫn áp (ghi lại ở `onResume`); (7) nếu toast "Mod không chạy được trên máy này" xuất hiện thì gửi `logcat` (thẻ `NampMod`).

## 8. Rủi ro và giới hạn

- **Không phủ**: sát thương kịch bản `EVTSYSTEM_Process` (`AddLife(-N)`) và DoT không có kẻ tấn công (`CHAR_ApplyDot` với x0 NULL); các đường này không bị nhân/giảm. Sát thương từ nhân vật phe 0 vào nhân vật phe 0 (đồng minh đánh nhau, ví dụ bị mê hoặc) chỉ bị chia Mdef, không nhân Mdmg.
- NPC (phe 2) bị người chơi đánh thì bị nhân (nếu game cho phép đánh NPC).
- Triệu hồi cùng phe với chủ nên được nhân (ghi nhận là mong muốn).
- Trần sát thương 999.999 và EXP 0xFFFFFF/lần chỉ là trần của mod; sát thương gốc > trần giữ nguyên.
- `[atk+0x19c]` và UI sát thương: **CHƯA XÁC ĐỊNH** (mục 5).
- `libgame.so` đang được vá chung với P1 (T3) và `0x203e68`; nhóm `mod` không đụng các vùng đó (kiểm bằng `check_ranges`).
- T9 (vùng nhìn) dùng cùng trang 0x72f000 (int thứ 4 @0x72f78c) và các vá khác trong `.text`; không chồng với cave 0x18bb00..0x18bbec và 3 điểm vá ở mục 4.

## 9. Chênh lệch so với bản nháp của A2

| Điểm | A2 | Bản này | Lý do |
|---|---|---|---|
| Trần sát thương | 0x7FFFFFFF | 999.999 | quyết định quản đốc, tránh tràn `dmg×80`, `[atk+0x19c]×dmg` |
| Nhân khi gốc ≥ trần | kẹp xuống trần | giữ nguyên | không làm yếu hơn game gốc |
| Phép nhân | `smull` 64 bit | `mul` 32 bit sau khi kiểm < trần | an toàn và ngắn hơn |
| `adrp` trong A | hai lần | một lần | cave vừa 59 lệnh (236 B) |
| Cave | 57 lệnh, 228 B | 59 lệnh, 236 B | dùng hết hai hàm chết |
| C | 0x1c7a44 → `59 10 ff 17` (cave 0x18bba8) | `5a10ff17` (cave 0x18bbac) | cave dài hơn |
| Danh sách phe | thiếu `CHARLOCSYSTEM_Load`, `EVTSYSTEM_Process`, `MERCENARYSYSTEM_IncludeParty`, `PARTY_SetMember` | đã bổ sung | mục 2.2 |
| Trần EXP | 0xFFFFFF | 0xFFFFFF | giữ |
| Vùng nhớ | 0x72f780/84/88 | 0x72f780/84/88 | giữ; kiểm lại PT_LOAD |

## 10. Disasm trước/sau (T8b)

(Sẽ được điền ở bước T8b bằng `python3 scripts/patch_lib.py <lib> <ra> --disasm --group mod`.)
