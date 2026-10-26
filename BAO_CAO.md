# BÁO CÁO — Việt hoá 雄霸三国之天降女神 (Hùng Bá Tam Quốc: Thiên Giáng Nữ Thần), Joymeng, bản N73 240×320

Jar cuối: `out/HungBaTamQuoc_N73_viet.jar` (+ `.jad`, `MIDlet-Jar-Size: 1393703`).
SHA-1: `7d1500e7be3739ce478fad097e370422283f27fd` — `rebuild.sh` chạy 2 lần cho ra cùng SHA-1.

## Quyết định đã chốt với người dùng
- Văn phong: **Hán Việt nhẹ**; 怒气 = **MP**; chỉ số HP/MP/EXP/ATK/DEF.
- Font game: **12px, ghép glyph f12 (bộ NAM_P) vào `bin/font.bin` gốc** (giữ renderer bitmap riêng của game).
- Ảnh: ô nhỏ f12, ô lớn f15c/f18; một màu thân chữ gốc + viền 1px màu viền gốc.
- **Giữ nguyên**: logo tựa thư pháp `fengmian.png`, logo QQ/乐堂动漫, ảnh + chữ quảng cáo game khác.
- Dịch: giao 1 agent dịch toàn bộ (theo yêu cầu người dùng), dịch tay từng dòng.

## Đã sửa
| Phần | Nội dung |
|---|---|
| Font | `bin/font.bin`: thay 94 glyph ASCII + thêm 134 chữ Việt → 7179 glyph; `：，！？（）` toàn khổ dùng glyph ASCII hẹp. `bin/fw.bin` (mới): advance từng glyph. |
| `bs.class` | Viết lại từ CFR (đã đối chiếu `javap -c`): đo/vẽ theo advance thật; ký tự thiếu không vẽ (gốc vẽ glyph gần nhất); buffer pixel dùng lại (gốc `new short[]` mỗi lần vẽ); viền có lề 1px; `readFully`; thêm `w(char)`. Mọi member gốc giữ tên/descriptor/flag (cả `<clinit>` 0x0009). |
| `vh.class` (mới) | Ngắt dòng theo px tại dấu cách/trước chữ Hán, cache 8 kết quả; khung thoại theo từ, **3 dòng/trang** (gốc 2, 12px/ký tự, `""+c` mỗi ký tự mỗi khung hình), layout tính 1 lần mỗi trang; chữ chạy trong ô chỉ cuộn khi dài hơn ô; `drawString` hệ thống → font bitmap. |
| Sửa bytecode (ASM, `tools_src/Patcher.java`) | 32+1 chỗ: `z.a(String,Font,int)`, `z.a(String,int)`, `z.a(Graphics,String,int,short[],int,int)`, 2 hàm `cb.a` ngắt dòng `&`, `cb.a(..bn..)` chữ chạy → `vh`; `n.a(Graphics,short[],int,int[],int)` (khung thoại) → `vh.d`; 19 `Graphics.drawString` (al, n, z, bb) → `vh.ds`; 3 `Font.stringWidth` (z, n) → `vh.sw`; 4 `length()*12/14` (n, bb, ag) → `bs.a`. |
| Text resource | `script.bin` (118 thoại, tính lại offset + vị trí sign màu), `string.bin`, `choice.bin`, `help_about.bin`, `gameData.bin` (360), `l1.bin` (27) — 625 dòng. |
| Chuỗi `.class` | 433 chuỗi constant pool (25 class); 24 chuỗi chỉ dùng `System.out` giữ nguyên. Màn QQ/mạng vẽ bằng font hệ thống (t, bp, bv, d, ah, b, h, ce, bu) dịch **không dấu**. |
| Bố cục | `uiform.bin` form 19 (bảng chọn ải): ô tên rộng 84px chế độ chữ chạy, ô trạng thái dời x=170 (Mở/Khóa/Xong/Qua). |
| Ảnh | 16 PNG rời (`assets/img`) + 15 PNG nhúng trong `uires.bin`/`animation.bin` (`assets/img2`, bảng palette đè PLTE giữ nguyên độ dài). |

## Kiểm tra
- Bộ trích/đóng gói text: **round-trip giống từng byte** cả 6 file khi chưa dịch (`textpack.py selftest`). Container ảnh: repack chưa sửa giống từng byte.
- `text/kiem.py` cả 2 bảng: **OK 0** (không sót chữ Hán, `：` `|` `&` `{c:N}` `{/}` khớp, mã ải 2 chữ số, ký tự cuối string mục 4, placeholder S/N/P l1).
- `scripts/text/check_skill_ph.py`: 37 mô tả kỹ năng, **0 lệch** (by.java thay chữ hoa đầu tiên M N K Y D X E Z B L G P S; quản đốc đã sửa 9 dòng vi phạm như "Giữ", "Nhận", "Không").
- Mọi ký tự trong bản dịch có glyph trong font mới (0 thiếu).
- `jar_check.py goc/game.jar out/...jar`: **RESULT: PASS** (`logs/jar_check.txt`): class mới/sửa version ≤ 48, có StackMap, không StackMapTable, không API ngoài CLDC/MIDP, không mất descriptor public. WARN API mới: `DataInputStream.readFully`, `Integer(int)`, `Integer.intValue`, `Vector.copyInto` — đều có trong CLDC 1.0. Class chỉ đổi chuỗi giữ nguyên StackMap gốc; chỉ class sửa bytecode + class mới được preverify (ProGuard 7.4.2 `-microedition`).
- FreeJ2ME-Plus (240×320, `out/shots/`): hỏi nhạc → menu → Giới thiệu → lưu game → chọn ải (3 chiến dịch, chữ chạy) → vào trận → ~40 trang hội thoại → hộp Gợi ý → hướng dẫn phím. Không exception mới; `XMessage class not found` có cả ở jar gốc (SDK QQ dò class).
- Chưa chạy được: KEmulator/MicroEmulator/máy thật (không có trong môi trường); màn menu trong trận, shop, kho đồ chưa đi tới bằng kịch bản phím (ảnh đã kiểm từng file, xem `assets/img2/compare`).

## Debug — lỗi đã gặp và cách xử lý
- `run_emu.py` gốc mở màn 320×1: freej2me_plus nhận `jar [fullscreen] w h [scale]` → bản `scripts/run_emu.py` truyền thêm `0`.
- `jar_check` báo mất `<clinit>`: ecj sinh flag 0x0008, obfuscator gốc 0x0009 → `set_clinit_flags.py`.
- Preverify cả jar làm ProGuard viết lại mọi class → chỉ preverify class đã sửa.
- Tên người nói đổi màu theo chữ cuối trang trước (`da = ex` của bản gốc, lộ ra khi màu trải qua 2 trang) → `vh.d` tự giữ màu nối trang, trả `n.da` như cũ.
- Bảng chọn ải chồng chữ (tên Việt 70–170px, ô 62px) → sửa `uiform.bin` + chữ chạy; chữ chạy gốc (`cb.a(..bn..)`) dùng 1 bộ đếm chung nên nhiều ô cùng khung hình không chạy → bộ đếm riêng từng chuỗi (so `equals` vì game tạo chuỗi mới bằng `substring` mỗi khung hình).
- Dấu `：` (bắt buộc giữ) hiện rộng 12px → alias glyph `:` của f12.

## Còn tồn
- f12 không có `'` → bản dịch không dùng; glyph `'` gốc giữ nguyên.
- Giữ chữ Hán (ảnh ghép từng chữ theo chỉ số, không đổi được bố cục): tên tướng sprite-sheet `uires` 95350 / `animation` 446653, `uires` 104649, icon `animation` 278962.
- `fengmian.png` giữ logo thư pháp gốc (theo quyết định).
- Nhãn ảnh rút gọn mạnh vì ô 11×11/13px: 武甲鞋饰 "Vũ/Gi/Hà/Sứ", 成就 "Đạt", 暴击 "Crit"; tishiwenzi "Hết xanh", "Nhận" sát ký hiệu (0px).
- Tên kỹ năng/vũ khí Hán Việt đầy đủ dài hơn ước lượng ô (agent ghi trong `text/ghi-chu.txt`) — hiển thị thật chưa đi tới để soát.
- Chữ chạy: chuỗi vừa ô giờ đứng yên (gốc: chạy liên tục) — thay đổi hành vi có chủ đích.
- Tin nhắn SMS tính phí (l1.bin) đã dịch nhưng không còn tác dụng ngoài TQ.
