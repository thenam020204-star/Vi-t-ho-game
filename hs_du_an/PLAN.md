# PLAN: Việt hoá series 轩辕剑-天之痕 (Hiên Viên Kiếm – Thiên Chi Ngân) phần 1–5

## Mục tiêu
5 file jar tiếng Việt chạy ổn định trên KVM/CLDC thật (Nokia N73, 240×320), giữ behavior gốc. Kèm báo cáo khả thi gộp 5 phần (không thực hiện gộp).

## Phạm vi
- Trong phạm vi: text resource + chuỗi hiển thị trong .class, font bitmap tiếng Việt, ngắt dòng/khung thoại, ảnh có chữ, build/preverify/kiểm, báo cáo gộp.
- Ngoài phạm vi: gộp jar thật, đổi gameplay, gỡ SMS (chỉ khi chặn chơi, phải hỏi người dùng).
- Làm P1 trọn vẹn trước, sau đó nhân rộng P2–P5 (cùng engine Gameislive, tên obfuscate khác nhau).

## Đường dẫn
- `WS=/tmp/claude-0/-home-user-Vi-t-ho-game/8b6f782d-89c1-5c98-be13-817e20eadf62/scratchpad/hs` (dữ liệu làm việc, không commit)
  - `$WS/jars/hs1..5.jar` jar gốc · `$WS/x/1..5/` jar giải nén · `$WS/src/1..5/` CFR · `$WS/logs/` · `$WS/shots/`
  - `$WS/work/tools/` bộ tool NAM.P: `source $WS/work/tools/env.sh` → `$FJ` (freej2me_plus.jar), `$CFR`, `$BITMAPFONT_CLASS`, `$FONT_DIR`…
- `P=/home/user/Vi-t-ho-game/hs_du_an` (commit vào git, nhánh `ccr-1807a560-21of6s`): `scripts/`, `docs/`, `p1..p5/` (tsv, mapping), PLAN.md, PROGRESS.md
- Skill gốc (script chuẩn): `SK=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa` → `$SK/scripts/{preverify.sh,jar_check.py,dp.py,inventory.py}`, tài liệu `$SK/references/`.
- Chạy emulator: dùng `$P/scripts/run_emu.py` (bản đã sửa tham số FreeJ2ME: `jar 0 W H scale`), `--size 240x320`. Lỗi MIDI/Tone Player là do headless, bỏ qua.
- Python đọc dữ liệu game chạy với `python3 -I`. Java desktop chạy với `env -u JAVA_TOOL_OPTIONS`.

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
- Phong cách dịch: **Hán Việt nhẹ, dễ đọc**; tên riêng phiên âm Hán Việt; chỉ số/UI viết tắt Anh (HP, MP, EXP, ATK, DEF…). Thuật ngữ thống nhất cả 5 phần.
- Font: **f15c (15px, chân chọn lọc)** của bộ tool, qua `BitmapFont` (`BitmapFont.setDefault(15, BitmapFont.SELECTIVE)`, file `/font/f15c.bin`). Không tự vẽ glyph. P1 khai MIDP-1.0 nhưng đã dùng `drawRegion` → nâng manifest lên MIDP-2.0.
- Game vẽ chữ bằng font hệ thống `Font.getFont(0,0,8)`; ngắt dòng theo từng ký tự (P1: `h/b.a(String,Font,int)`). Sẽ thay bằng BitmapFont + ngắt theo dấu cách (thiết kế chi tiết chốt sau T6).
- Định dạng text (P1, đã xác minh sơ bộ):
  - Chuỗi = `readUTF` (u2 độ dài byte + UTF-8). Bộ quét chung: `$P/scripts/utfscan.py` (`scan`, `rebuild`, `enc`).
  - `map/N.m`: `u4 A, u4 B, u1 n, n×(u1 id, u4 len), kịch bản…` rồi dữ liệu bản đồ; `8+A` = hết vùng kịch bản; B không liên quan kịch bản (giữ nguyên). Kịch bản là cây lệnh đọc bằng `main/d.a(DataInputStream, d)` (không có offset nhảy) → đổi độ dài chuỗi chỉ cần cập nhật `len` từng kịch bản và `A`.
  - `i.dat`: `i4 n, n×(i2 id, i4 off), i4 blobLen, blob`; bản ghi đọc bằng `d/a.a(DataInputStream)` (private static, 1 tham số). Đổi độ dài → tính lại `off` và `blobLen`.
  - Đoạn chia sẵn: `$P/scripts/segments.py` (`m_segments`, `idat_segments`).
- Kiểm chứng bằng **chính parser của game**: `$P/scripts/harness/Harness.java` (chỉ chạy desktop, KHÔNG đóng vào jar). Đầu vào stdin: `file\tstart\tend\tclass\tmethod\tnparams`.
- Chuỗi trong .class chỉ dịch khi chứng minh chỉ để hiển thị (không dùng làm khoá, tên resource, `equals`/`==`).
- Mọi class mới: ecj `-source 1.3 -target 1.3`, rồi `preverify.sh`; `jar_check.py` phải PASS. Không đổi tên/descriptor/access flag class/field/method có sẵn.
- Không tạo String/object trong `paint()`/game loop; cache kết quả đo và ngắt dòng.

## Ràng buộc và edge case
- Mã điều khiển trong text: `{…}` (tô màu từ khoá, ví dụ `{伏魔山}`), `\n`, `？？？`, `~~~`, `。。。`. Giữ `{}` cân bằng.
- Chuỗi không có chữ CJK (thuần ASCII) bộ quét bỏ qua — T2 phải báo nếu harness thấy chuỗi hiển thị thuần ASCII.
- `.pak` bị inventory nghi có chữ (b.pak, c.pak, mi0..5.pak, mm.pak) — có thể chỉ là ảnh; T3 xác minh.

## Checklist
- [x] T1. Sửa harness không in ra gì
  - Agent: A1
  - Hiện trạng: `java -cp $P/scripts/harness Harness $WS/jars/hs1.jar $FJ < $WS/logs/h1_in.txt` chạy rc=0 nhưng không in dòng nào (trước khi thêm khối init MobilePlatform thì in được, 91 ERR do `<clinit>` của `h/b` cần Font). Nghi FreeJ2ME đổi `System.out` hoặc gọi exit. Cách sửa gợi ý: ghi kết quả ra file (tham số thứ 3), hoặc giữ `PrintStream` gốc trước khi init.
  - File: `$P/scripts/harness/Harness.java`
  - Hoàn thành khi: chạy với `$WS/logs/h1_in.txt` (1113 đoạn: mọi kịch bản `.m` + mọi bản ghi `i.dat` của P1) cho 1113 dòng `OK`, `consumed == len`.
- [x] T2. Đối chiếu bộ quét với harness (P1) (phụ thuộc T1)
  - Agent: A1
  - Viết `$P/scripts/verify_scan.py`: với mỗi đoạn, tập chuỗi `utfscan.scan` trong đoạn phải bằng tập chuỗi có ký tự CJK mà harness gom được; liệt kê riêng chuỗi harness thuần ASCII (để quản đốc xem có cần dịch không).
  - Hoàn thành khi: 0 sai khác trên P1; ghi số liệu vào `$P/logs/verify_p1.txt` (commit).
- [x] T3. Các file text đơn giản của P1
  - Agent: A1
  - Đọc loader trong `$WS/src/1` (t.dat/t2.dat/tips.dat qua `h/b.b(String)`; map.dat ở `g/j`; c.dat, monster.dat, item15.dat, fb_skill.dat ở `d/a`) → viết parser Python chính xác theo code, đọc hết file đúng từng byte, đối chiếu với `utfscan`. Xác minh các `.pak` có chứa text hay không (xem class đọc chúng).
  - File: `$P/scripts/formats_p1.py`, `$P/docs/formats_p1.md`
  - Hoàn thành khi: mỗi file: parser đọc hết đúng độ dài, tập chuỗi = tập của utfscan; tài liệu ghi định dạng từng file + kết luận về `.pak`.
- [x] T4. Trích xuất / đóng gói lại P1 (phụ thuộc T2, T3)
  - Agent: A1
  - `$P/scripts/extract.py <dir giải nén> <out.tsv>` → cột `id, file, offset, goc, dich, ghi_chu, gioi_han` (id ổn định, ví dụ `map/11.m@1234`; ghi chú: id kịch bản / loại bản ghi / chuỗi liền trước làm ngữ cảnh). `$P/scripts/repack.py <dir gốc> <tsv> <dir ra>` ghi lại resource, cập nhật bảng độ dài `.m` và bảng offset `i.dat`, cột `dich` rỗng = giữ gốc. Trong TSV, xuống dòng viết `\n`.
  - Hoàn thành khi: (a) TSV chưa dịch → repack ra file giống hệt từng byte cho mọi file; (b) dịch giả (mọi chuỗi → `"Thử " + chuỗi gốc + " dài hơn"`) → harness OK toàn bộ trên file đã repack và chuỗi gom được = chuỗi giả; (c) `$P/p1/text_can_dich.tsv` được tạo và commit.
- [x] T4b. Trích xuất text P2–P5 + gộp (mở rộng từ quản đốc)
  - Agent: A1
  - `extract.py/repack.py --part N`, `verify_scan.py`, `verify_files.py`, `test_fake.py`; `pN/text_can_dich.tsv` N=1..5; `all/text_can_dich.tsv` (thêm cột `phan`, id tiền tố `pN:`); `docs/formats_pN.md`. Đã đạt cả 3 tiêu chí cho P2–P5.
- [ ] T5. Chuỗi CJK trong .class của P1 — **Quản đốc nghiệm thu**
  - Agent: A1
  - Liệt kê chuỗi CJK trong constant pool các class (`b/i, b/m, b/u, c/d, g/b, g/d, i/a` theo inventory; quét lại toàn bộ) kèm nơi dùng (`javap -c`): hiển thị / khoá / so sánh / tên RMS. Không sửa class.
  - File: `$P/p1/class_strings.tsv` (cột: class, chuỗi, nơi dùng, an_toan_dich Y/N, bằng chứng)
  - Hoàn thành khi: mọi chuỗi CJK có dòng với bằng chứng bytecode.
- [ ] T6. Bản đồ vẽ chữ P1 — **Quản đốc nghiệm thu**
  - Agent: A1
  - Liệt kê mọi call site `Graphics.drawString/drawChar/drawSubstring/drawChars`, `Font.charWidth/stringWidth/substringWidth/getHeight`, `Graphics.setFont`, hàm split `h/b.a(String,Font,int)` và nơi gọi nó (theo `javap -c`, đúng tên method + descriptor). Ghi kích thước khung thoại (số dòng, x/y/w/h) và chỗ phân trang. Không sửa class.
  - File: `$P/docs/render_p1.md`
  - Hoàn thành khi: tài liệu liệt kê đủ (kiểm bằng grep `invokevirtual javax/microedition/lcdui/(Graphics|Font)` trên toàn bộ class P1) và đề xuất điểm hook tối thiểu.

- [ ] T7. Vẽ lại ảnh có chữ Hán (5 phần) — **Quản đốc nghiệm thu** (chỉ giao SAU khi text dịch xong; người dùng yêu cầu tự động)
  - Agent: A1 (Sonnet 5.5, một công nhân). Không chạm `dich/`, `out/`, class, script build.
  - Đầu vào: `anh_menu/DANH_SACH_CO_CHU.tsv` (149 dòng, 87 `nhom_trung` khác nhau), `anh_menu/README_VE_ANH.md`, ảnh gốc ở `anh_menu/pN/...` (KHÔNG sửa). Nhãn chữ phải khớp bản dịch text: tra `dich/dp_work/thuat-ngu.tsv` và `dich/dp_work/out.tsv` (cùng chữ Hán trong `t.dat`, ví dụ 奇术, 物品, 装备 → dùng đúng bản dịch đó, rút gọn nếu ô nhỏ).
  - BỎ QUA (giữ nguyên): `mm.pak/title.png` và `title_p*.png` (ảnh người dùng vẽ), `goc_jar/icon.png`, `goc_jar/logo0.png`, `goc_jar/logo2.png`, `goc_jar/sns_logo.png` (logo hãng). `mm.pak/cr.png` → "(c)2008 Lakoo".
  - Cách vẽ: pixeltext (`source $WS/work/tools/env.sh`; `cd $WS && python3 -m pixeltext ...`, xem `$SK/references/toolkit.md` mục pixeltext). Xoá chữ Hán bằng màu nền/viền xung quanh (giữ khung, viền, nền trang trí), vẽ chữ Việt màu giống chữ gốc; chọn cỡ bằng `pixeltext fit` trong 15c/13c/13s; ô quá nhỏ (13×13, 20×20) dùng viết tắt ATK/DEF/SPD/Nộ… Dải nhiều ô (`tt.png`, `mmtext.png`, `pt0/pt1`, `n0..n3`, `text.png`) giữ đúng toạ độ từng ô (đo ô từ ảnh gốc). Chữ có dấu không được tràn ra ngoài ô.
  - Ra: ghi đè ảnh trong `anh_menu/can_ve/pN/<cùng đường dẫn>`; ảnh cùng `nhom_trung` vẽ một lần rồi chép sang các phần ở cột `dung_chung_voi`. Giữ nguyên tên file, W×H, chế độ trong suốt (pixel trong suốt của gốc vẫn trong suốt). Ghi bảng `anh_menu/DA_VE.tsv` (phan, file, chu_viet, co_chu, ghi_chu).
  - Hoàn thành khi: (1) script kiểm: mọi ảnh trong can_ve cùng W×H với gốc, ảnh có alpha vẫn có alpha; (2) `python3 -I scripts/contact_sheet.py` ra `shots/anh_vi/pN.png` cho 5 phần và công nhân đã tự xem từng ảnh (Read) — không còn chữ Hán, không tràn ô, dấu đọc được; (3) mỗi dòng DANH_SACH_CO_CHU (trừ mục BỎ QUA) có trong DA_VE.tsv.
  - Sau đó quản đốc: `rebuild_all.sh`, chụp màn hình menu/chiến đấu/cửa hàng để nghiệm thu.

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (không)

## Nhật ký
- Quản đốc: kiểm kê 5 jar (`$WS/logs/inv*.txt`), CFR `$WS/src/1..5`, sửa run_emu (FreeJ2ME args), viết utfscan.py, segments.py, Harness.java (chưa chạy được → T1). Chốt phong cách + font + gộp = chỉ báo cáo.
- A1 T1: Harness im lặng do `MobilePlatform.checkFileEncoding` gọi `System.exit(0)` khi `file.encoding` != ISO_8859_1 -> chạy với `-Dfile.encoding=ISO_8859_1`; `setPlatform` nhận 2 tham số; `<clinit>` của `d.g` cần `d.a.q` -> thêm `-Dharness.stub=lớp:trường:cỡ`; ghi qua fd 1 gốc. Harness.java; 1113/1113 OK (consumed==len). Thêm chế độ `LOAD` (gọi loader game qua MIDletLoader của FreeJ2ME, cần FreeJ2ME trong -cp) và ghi offset các lần readUTF.
- A1 T2: scripts/verify_scan.py; P1 1113 đoạn, 0 sai khác; 22 chuỗi "消耗N点体力..." trong i.dat là game tự ghép (không phải dữ liệu), 0 chuỗi harness thuần ASCII hiển thị; logs/verify_p1.txt. Sửa utfscan.scan (giả dương lệch 1 byte ở chuỗi dài, thấy ở P2 map/128.m).
- A1 T3: scripts/formats_p1.py + docs/formats_p1.md; 8 file text/dữ liệu đọc hết đúng byte, tập chuỗi = utfscan; 11 `.pak` chỉ chứa PNG/.fra (XOR 1), không text.
- A1 T4: scripts/{textfmt,extract,repack,test_fake}.py, p1/text_can_dich.tsv (3924 chuỗi, 65 file); round-trip giống hệt byte; dịch giả OK harness 1113/1113 + loader game 7/7.
- A1 T4b: P2–P5 cùng bộ công cụ (P3–5 dùng `.m2`: tên bản đồ + kịch bản + danh sách NPC, có thêm kiểu 'm2'); p2..p5/text_can_dich.tsv (5189/3801/2860/3114 chuỗi), all/text_can_dich.tsv (18888 dòng, 9192 chuỗi gốc duy nhất); logs/verify_p*.txt, verify_files_p*.txt; docs/formats_p2..p5.md. Bỏ qua (không loader): lk_tips.dat P4/P5, ii.dat P4; ngoài phạm vi: payInfo.ini/snsInfo.ini.
