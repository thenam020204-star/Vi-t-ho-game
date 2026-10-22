# PLAN: Việt hoá 轩辕剑-天之痕-二章 (Hiên Viên Kiếm – Thiên Chi Ngân, chương 2)

## Mục tiêu
Tạo jar tiếng Việt chạy đúng trên KVM/CLDC 1.0 + MIDP 2.0 thật (Nokia N73, 240×320). Toàn bộ text trong resource, `.class` và ảnh đều là tiếng Việt, chữ vẽ bằng BitmapFont `f15c`.

## Phạm vi
- Trong phạm vi: text resource (`i.dat`, `map/*.m`, `item15.dat`, `monster.dat`, `c.dat`, `help.dat`, `tips.dat`, `t.dat`, và các file khác nếu chứa chuỗi hiển thị), chuỗi hiển thị trong `.class`, chuyển font, word-wrap, khung thoại, ảnh có chữ, tên MIDlet trong manifest/jad.
- Ngoài phạm vi: đổi gameplay, đổi tên/descriptor class gốc, đổi cân bằng game, phần SMS/billing (giữ nguyên, không dịch các khoá SmsCode/SmsDest).

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
- **Phong cách dịch:** Hán Việt kiếm hiệp (người dùng chốt). Dịch tay do quản đốc (Opus) làm bằng `dp`, công nhân KHÔNG dịch.
- **Font:** `f15c` (15px, chân chọn lọc), `BitmapFont.setDefault(15, BitmapFont.SELECTIVE)`. Chỉ đóng `font/f15c.bin` vào jar.
- **Cách đổi font = hook bytecode tại chỗ gọi API** (vì game vẽ chữ bằng font hệ thống `Font.getFont(0,0,8)` ở `e/a/b` và gọi `drawString` ở ~20 class):
  - Class mới `VF` (package mặc định, biên dịch `-source 1.3 -target 1.3`, version ≤ 48) có các static method có chữ ký = (receiver, args gốc...), ví dụ
    `VF.drawString(Graphics,String,int,int,int)V`, `VF.drawChar(Graphics,char,int,int,int)V`, `VF.drawSubstring(...)`, `VF.drawChars(...)`,
    `VF.stringWidth(Font,String)I`, `VF.charWidth(Font,char)I`, `VF.substringWidth(...)`, `VF.charsWidth(...)`, `VF.getHeight(Font)I`, `VF.getBaselinePosition(Font)I`.
  - Transformer ASM (`/opt/maven/lib/asm-9.8.jar`) đổi `invokevirtual javax/microedition/lcdui/{Graphics,Font}.<method>` → `invokestatic VF.<method>` (stack layout giữ nguyên). Không dùng `COMPUTE_FRAMES`; giữ class version 45.3; sau đó `preverify.sh`.
  - `VF` vẽ bằng màu hiện tại `g.getColor()`, tôn trọng anchor (TOP/BASELINE/BOTTOM, LEFT/HCENTER/RIGHT). Không cấp phát object trong đường vẽ (trừ cache BitmapFont).
  - Chiều cao dòng trả về từ `VF.getHeight` = chiều cao font f15c (+ khoảng cách dòng nếu cần, quyết định sau khi xem ảnh chụp T4).
- **Word-wrap:** các hàm ngắt theo ký tự trong `e/a/b` (`a(String,Font,int)`, `a(Graphics,String,int,int,int,int)`, `a(String,int)`) và mọi hàm wrap/phân trang khác tìm thấy phải đổi sang ngắt tại dấu cách theo px (giữ mã điều khiển `@0..@9`, `#`, `\n` không tính độ rộng). Sửa bằng method mới trong `VF` + đổi thân method gốc gọi sang (giữ nguyên descriptor gốc).
- **Text resource:** parser/repacker Python mô phỏng đúng code đọc của game (không dò heuristic), tính lại mọi tiền tố độ dài u2 (`readUTF`, modified UTF-8), bảng offset (`i.dat`, `monster.dat`), bảng độ dài section (`map/*.m`). Bắt buộc round-trip byte-identical khi chưa sửa.
- **Text trong `.class`:** chỉ đổi hằng `String` đã chứng minh chỉ để hiển thị; ghi bằng ASM (đổi `ldc`/ConstantValue), không đổi gì khác.
- **Ảnh có chữ:** `pixeltext patch` cỡ `15c` (nhãn chật mới cân nhắc `13c`, phải báo quản đốc), một màu = màu thân chữ gốc.
- **Định dạng TSV chung** `work/text/text_can_dich.tsv`: cột `id	file	khoa	goc	dich	ngu_canh	gioi_han`. `id` duy nhất, ổn định giữa các lần trích. Mã điều khiển giữ nguyên trong cột `goc`.
- **Cấu trúc thư mục (gốc repo):** `goc/` (jar gốc) · `scripts/` (mọi script) · `work/` (tool + file tạm, gitignore) · `src_cfr/` (decompile, gitignore) · `src/` (nguồn class mới/đã sửa: `VF.java`, transformer) · `build/` (gitignore) · `out/` (jar/jad cuối) · `logs/`.
- **Pipeline duy nhất** `rebuild.sh`: jar gốc → patch resource → patch class (ASM) → thêm `VF.class`, `BitmapFont.class`, `font/f15c.bin` → preverify → `jar_check` → `run_emu`.

## Ràng buộc và edge case
- CLDC 1.0: không `float/double`, không API ngoài CLDC1.0/MIDP2.0. `jar_check.py` phải `RESULT: PASS`.
- Không đổi tên/descriptor/access flag của class/field/method gốc. Phần "Đổi" của `jar_check` chỉ gồm class có chỗ gọi đã hook/sửa + resource đã dịch + class mới.
- Heap: 1 font, tối đa 4 màu cache (~292 KB). Nếu game dùng nhiều màu chữ hơn, báo quản đốc.
- `@<số>` = đổi màu (bảng `e/a/b.d`), `#` = về màu mặc định, `\n` = xuống dòng. Kiểm thêm mã khác trong script map.
- Không tạo String/object mới trong `paint()`/game loop: cache kết quả wrap.
- FreeJ2ME: `--size 240x320`; tắt nhạc ở màn hỏi âm thanh.

## Checklist
- [ ] T1. Baseline: chụp màn gốc + xác định luồng màn hình
  - Agent: A1
  - Làm: chạy `scripts/run_emu.py` (skill) trên `goc/game.jar` 240×320, tìm chuỗi phím đi qua: màn hỏi âm thanh → logo → menu chính → các mục menu (giúp/giới thiệu) → vào game → hội thoại đầu → menu trong game (trạng thái/vật phẩm/kỹ năng) → 1 trận đánh nếu tới được. Ghi chuỗi phím vào `scripts/emu_steps.txt`, ảnh vào `logs/shots_goc/`.
  - Xem 8 file PNG trong jar, liệt kê ảnh nào có chữ Hán + toạ độ ô chữ (ghi `logs/anh_co_chu.md`).
  - Hoàn thành khi: có ≥ 8 ảnh chụp các màn trên, `emu_steps.txt` tái chạy được, `anh_co_chu.md` có đủ 8 ảnh.
- [ ] T2. Parser/repacker text resource — **Quản đốc nghiệm thu**
  - Agent: A1
  - Đầu vào: `goc/game.jar`, code đọc trong `src_cfr/` (`b/a.java`, `main/c.java` `a(DataInputStream,c)` dòng ~828, `f/*.java` `a(DataInputStream)`, `e/a/b.b(String)`, `c/r.java`, `d/k.java`, `d/e.java`).
  - Làm: `scripts/res_text.py extract goc/game.jar work/text/res.tsv` và `scripts/res_text.py pack goc/game.jar work/text/res.tsv build/res/` (ghi file resource đã thay vào thư mục). Parse đúng cấu trúc từng file (ghi rõ định dạng trong docstring). Kiểm tất cả file resource có CJK trong `logs/inventory.txt`; `.pak/.mid/.fra` chỉ cần chứng minh là dữ liệu nhị phân không có chuỗi hiển thị (ghi lý do).
  - Hoàn thành khi: (a) pack với cột `dich` rỗng → mọi file byte-identical với gốc; (b) pack với `dich` = chuỗi thử dài gấp 3 (ví dụ `"Thử "+goc`) → parse lại ra đúng chuỗi mới, mọi trường số khác không đổi (script tự kiểm bằng so sánh object sau parse); (c) không sót: tổng số ký tự CJK trích được ≈ số trong inventory (giải thích chênh lệch).
- [ ] T3. Trích chuỗi trong `.class` + phân loại hiển thị/khoá
  - Agent: A1
  - Làm: `scripts/class_text.py extract` → `work/text/cls.tsv` (cột `khoa` = `class#method#index`), cột `ngu_canh` ghi cách dùng (drawString / nối chuỗi hiển thị / khoá / equals / tên resource). Chuỗi không phải hiển thị đánh dấu `KHONG_DICH` trong `ngu_canh`. `scripts/class_text.py pack` ghi hằng mới bằng ASM.
  - Hoàn thành khi: pack với `dich` rỗng → class byte-identical; pack thử → `javap` thấy chuỗi mới; mọi chuỗi CJK (≈88 chuỗi, inventory) có trong tsv. Gộp `res.tsv` + `cls.tsv` → `work/text/text_can_dich.tsv` bằng `scripts/merge_tsv.py`.
- [ ] T4. Class `VF` + transformer hook font + word-wrap — **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: `src/VF.java`, `scripts/hook_font.py` (hoặc `src/HookFont.java` chạy bằng JDK + ASM), sửa các hàm wrap trong `e/a/b` (và hàm wrap khác nếu tìm thấy: grep `charWidth`, `'\n'`, `substring` trong `src_cfr`). Liệt kê đủ mọi chỗ gọi bằng `javap` (ghi `logs/hook_sites.txt`). Build qua `rebuild.sh` (bản đầu: chưa dịch, chỉ đổi font).
  - Hoàn thành khi: `jar_check` PASS; `run_emu` theo `emu_steps.txt` không exception; ảnh chụp cho thấy chữ (Hán chưa có glyph sẽ trống — chấp nhận) và chuỗi thử tiếng Việt (tạm thay 1 chuỗi menu, ví dụ tiêu đề menu) hiển thị đúng dấu, không thưa/đè; `logs/hook_sites.txt` = 0 chỗ gọi Font/Graphics text còn sót.
- [ ] T5. Dịch toàn bộ (`dp`) — Quản đốc tự làm (không giao công nhân)
- [ ] T6. Ghép bản dịch + build Mốc 2 (phụ thuộc T2–T5)
  - Agent: A1
  - Làm: `rebuild.sh` dùng `work/text/text_can_dich.tsv` đã dịch; chạy `emu_steps.txt`, chụp `logs/shots_vi/`.
  - Hoàn thành khi: `jar_check` PASS, không exception, không còn chữ Hán trong ảnh chụp các màn ở T1.
- [ ] T7. Khung thoại / bố cục (phụ thuộc T6) — **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: theo danh sách lỗi bố cục quản đốc ghi ở mục Vướng mắc/T7-lỗi sau khi xem ảnh T6 (khung thoại ≥ 3 dòng, nền bán trong suốt nếu cần, menu tràn...).
  - Hoàn thành khi: ảnh chụp không còn tràn/đè/cắt chữ ở các màn đã liệt kê.
- [ ] T8. Ảnh có chữ (phụ thuộc T1, T5 phần thuật ngữ)
  - Agent: A2 (chỉ đọc trước; bắt đầu khi A1 ≥ 90%)
  - Làm: `scripts/patch_images.py` dùng pixeltext `15c`, văn bản tiếng Việt do quản đốc ghi trong `logs/anh_co_chu.md`.
  - Hoàn thành khi: `pixeltext check` OK, ảnh chụp màn có ảnh đó hiển thị đúng.
- [ ] T9. Manifest/JAD + đóng gói bàn giao (phụ thuộc tất cả)
  - Agent: A1
  - Làm: `MIDlet-Name`/`MIDlet-1`/`MIDlet-Description` tiếng Việt không dấu? → KHÔNG: giữ tên gốc trong `MIDlet-1` nếu điện thoại không hiển thị được dấu; quản đốc quyết ở bước này. Tạo jad (`MIDlet-Jar-Size` khớp), `README_DU_AN.md`, `BAO_CAO.md`, zip `HienVienKiem2_du_an.zip` theo mục Bàn giao của skill j2me-viet-hoa.
  - Hoàn thành khi: `rebuild.sh` tái tạo đúng jar cuối, `work/tools/check_all.sh` OK, zip đủ thư mục.

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (trống)

## Nhật ký
- Quản đốc: kiểm kê xong (`logs/inventory.txt`), chốt phong cách + font f15c, lập PLAN.md.
