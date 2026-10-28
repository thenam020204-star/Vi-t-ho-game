# PLAN: Việt hoá 梦回三国残剑问情天 (Mộng Hồi Tam Quốc: Tàn Kiếm Vấn Tình Thiên)

## Mục tiêu
Tạo bản tiếng Việt của bản K790 (240x320) rồi bản E62, chạy đúng trên KVM/CLDC thật (không chỉ chạy được trên J2ME Loader).

## Phạm vi
- Trong phạm vi: toàn bộ text trong resource `.dat` và chuỗi hiển thị trong `.class`; font bitmap tiếng Việt; xuống dòng, phân trang, khung thoại; ảnh có chữ (`.xng`, PNG); tên MIDlet trong manifest/JAD.
- Ngoài phạm vi: đổi gameplay, cân bằng, đổi tên/deobfuscate class.

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
- Phong cách dịch: **Hán Việt nhẹ, dễ đọc** (tên riêng, địa danh, chiêu thức phiên âm Hán Việt; thoại gần văn nói, xưng ta/ngươi/huynh/muội). Lý do: người dùng chọn ở Bước 0.
- Font: **f13c** (13px, chân chọn lọc) qua `BitmapFont` (`BitmapFont.setDefault(13, BitmapFont.SELECTIVE)`, file `/font/f13c.bin`). Lý do: người dùng chọn; font gốc là font hệ thống SMALL `Font.getFont(0,0,8)` và game đã dùng MIDP2 (`drawRegion`, `createRGBImage`), nên dùng được BitmapFont.
- Mọi chỗ vẽ/đo chữ hiển thị (`Graphics.drawString/drawChar/drawSubstring`, `Font.stringWidth/charWidth/getHeight`) trong class `f`, `u` (và nơi khác nếu T02 tìm thấy) chuyển sang BitmapFont bằng **sửa bytecode tĩnh** (class-level patch, không đổi tên/descriptor/access flag của member đã có). Class mới biên dịch bằng ecj `-source 1.3 -target 1.3`, rồi preverify.
- Không tạo String/object trong `paint()` hay game loop: cache kết quả `wrap`/đo.
- Ưu tiên bytecode (`javap -c`) hơn output của decompiler (CFR, FernFlower để đối chiếu).
- Bản E62 dùng lại 100% bản dịch và các class giống K790. Chỉ vá lại `XMIDlet`, `l`, `m`, `q` và ảnh `.xng` của E62.
- Cấu trúc thư mục (`mhtq/`): `goc/` (jar gốc), `work/` (tool, file giải nén, tsv), `src/` (decompile + class sửa), `scripts/` (mọi script), `out/` (jar/jad/zip bàn giao), `logs/`.
- Tool: `Tool_Viet_Hoa_NAM_P.zip` ở gốc repo, scripts của skill tại `$SK=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa` (`scripts/setup_tools.sh`, `inventory.py`, `preverify.sh`, `jar_check.py`, `run_emu.py`, `dp.py`, `classfile.py`; tài liệu `references/toolkit.md`, `bitmapfont.md`, `phuong-an-2-hook-bytecode.md`).

- **Thay công nhân khi ngữ cảnh vượt 400k token** (người dùng yêu cầu). Công nhân phải ghi trạng thái dở dang (đang ở bước nào, file nào, lệnh tiếp theo) vào mục "Bàn giao công nhân" bên dưới sau mỗi bước con, để công nhân mới đọc PLAN.md là làm tiếp được. Quản đốc theo dõi lượng token của công nhân và giao task tiếp cho công nhân mới khi vượt ngưỡng.

- **(Sau T02) Tên khoá chân dung** `叶问天`/`小唯`/`婴咛`: được dịch, nhưng phải dịch **đồng bộ cùng một chuỗi** ở cả ba nơi: script.dat (op 2, tham số 0), hằng trong `y.a(String)` và hằng trong `v.a()` (mate.dat đổi theo). Bộ đóng gói của T03 phải tự kiểm sự đồng bộ này (sai thì báo lỗi). Thuật ngữ: Diệp Vấn Thiên, Tiểu Duy, Anh Ninh.
- **(Sau T02) Không dịch:** string.dat, chuỗi build/SVN, mảng giữ chỗ, `m.a`/`m.b` chỉ dùng `.length`, 2+2 mảng trong mission.dat bị loader bỏ qua (giữ nguyên byte, chỉ dịch phần hiển thị).
- **(Sau T02) Khung thoại:** chuyển từ 2 dòng thành **3 dòng mỗi trang** với f13c. Bước dòng 15–16px nằm gọn trong thanh 52px, giữ nguyên ảnh thanh thoại. Ngắt dòng theo dấu cách, đo bằng BitmapFont. Các hàm ngắt `k.b` (hiểu `#RRGGBB`/`##`, `|`, `\n`) và `j.a` giữ nguyên descriptor, chỉ thay thân hàm. Đồng thời chặn lỗi `#` ở cuối chuỗi. Chiều rộng ngắt giữ như cũ (196/190/210/140/120/132…), trừ khi ảnh chụp cho thấy cần đổi.
- **(Sau T02) Đo/vẽ:** `f.a(C)I`, `f.a()` (chiều cao dòng) và mọi `drawString`/`drawChar` trong `f`/`u` trả theo BitmapFont f13c, để hiệu ứng gõ chữ và clip khớp với chữ vẽ ra.

## Ràng buộc và edge case
- Kiểm kê: class version 45.3, đã preverify, CLDC-1.0/MIDP-1.0 trong manifest nhưng có gọi API MIDP2 + MMAPI.
- Text: `script.dat` (UTF-16BE, ~10.6k ký tự CJK), `layer.dat` (UTF-16BE), `item.dat`/`mission.dat`/`skill.dat` (UTF-8), `string.dat`, có thể có trong `scene.dat`/`monster.dat`/`mate.dat`/`bf.dat`/`bm.dat`. Chuỗi CJK có trong các class `b f g h i j l m n p q s v w y z`.
- Đóng gói lại khi chưa sửa phải ra file giống hệt từng byte trước khi patch.
- Chuỗi trong class chỉ dịch khi chứng minh được là chuỗi hiển thị.
- `jar_check.py` phải ra PASS. Lỗi MIDI headless `Sequencer ... is null` được bỏ qua.

## Checklist
- [x] T01. Setup tool + decompile
  - Agent: A1
  - Làm: `bash $SK/scripts/setup_tools.sh Tool_Viet_Hoa_NAM_P.zip mhtq/work`; `source mhtq/work/tools/env.sh`; giải nén `goc/MHTQ_K790.jar` vào `work/k790_raw/`; CFR decompile ra `src/k790_cfr/`; FernFlower (tải về nếu được, nếu không thì ghi vào Vướng mắc rồi đi tiếp) ra `src/k790_ff/`; `javap -c -p -constants` từng class ra `src/k790_javap/`. Lưu output của `inventory.py` vào `logs/inventory_k790.txt`.
  - Hoàn thành khi: `work/tools/check_all.sh` chạy OK (log ở `logs/check_all.txt`) và có đủ ba thư mục src.
- [x] T02. Báo cáo đường đi của chữ (phụ thuộc T01). **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: đọc các class loader (`f i j k n t w x`) và các class vẽ chữ (`f`, `u`), viết `logs/text_path.md` gồm: (a) định dạng nhị phân của từng `.dat` có chữ (header, bảng offset, tiền tố độ dài, mã hoá, ID), có offset/ví dụ hex; (b) danh sách method vẽ/đo chữ (class.method + descriptor + dòng bytecode gọi `drawString`/`stringWidth`/…); (c) hàm ngắt dòng/phân trang/khung thoại: cách tính độ rộng (có ngưỡng mã ký tự hay `len*k`?), số dòng, kích thước khung; (d) các chuỗi CJK trong class: chuỗi nào hiển thị, chuỗi nào dùng làm khoá hoặc so sánh (kèm bằng chứng); (e) ảnh `.xng`: header, nén, palette (chỉ cần đủ để biết có giải mã được không).
  - Hoàn thành khi: `logs/text_path.md` trả lời đủ (a)–(e), mọi khẳng định có dẫn chiếu bytecode.
- [x] T03. Bộ trích xuất/đóng gói text (phụ thuộc T02). **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: `scripts/text_extract.py` + `scripts/text_pack.py` (Python, chạy bằng `python3 -I`), xuất `work/text_can_dich.tsv` với các cột `id file offset/khoá gốc dịch ghi_chú giới_hạn`. Giữ nguyên và đánh dấu mã điều khiển. Chuỗi trong class vá qua constant pool (dùng `$SK/scripts/classfile.py` nếu hợp).
  - Hoàn thành khi: đóng gói lại khi cột dịch trống/bằng gốc ra từng `.dat` và `.class` giống hệt từng byte (`cmp`); log ở `logs/roundtrip.txt`.
- [x] T04. Hook BitmapFont f13c (phụ thuộc T02). **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: thêm BitmapFont + `/font/f13c.bin`, sửa bytecode `f`/`u` (và nơi khác theo T02) để vẽ/đo bằng BitmapFont; script `scripts/patch_font.py` có thể chạy lại được. Build → preverify → jar_check → run_emu, thử nghiệm bằng cách thay tạm một chuỗi menu đầu thành tiếng Việt có dấu.
  - Hoàn thành khi: `jar_check` PASS, ảnh chụp `logs/shots_m1/` cho thấy chữ Việt có dấu đúng; zip Mốc 1 ở `out/`.
- [x] T05. Dịch toàn bộ text (phụ thuộc T03). Đúng MỘT công nhân Sonnet, quy trình `dp` ($SK/references/dich-pro.md)
  - Agent: công nhân dịch D1 (duy nhất). Thư mục: `mhtq/work/`; dữ liệu ở `work/dp_work/` (đã `prep` từ `work/dp_in.tsv`, sinh bởi `scripts/dp_input.py`; cột giới hạn đã đổi từ px sang số ký tự theo độ rộng trung bình 4.6px/ký tự của f13c, chỉ áp cho chuỗi một dòng; chuỗi tự xuống dòng thì không giới hạn).
  - Lệnh: `DP="python3 $SK/scripts/dp.py"`, chạy trong `mhtq/work/`.
  - Phân tích độ khó (quản đốc): **Khó vừa**. 1250 dòng duy nhất, 24k ký tự Hán; 42% thoại, 38% có xưng hô; 33 người nói; mã điều khiển 8% (`#RRGGBB…##`, `|`). Điểm khó: nam chính là người hiện đại xuyên không (thoại pha từ hiện đại, độc thoại trong ngoặc), thoại tình cảm/đùa cợt, nhãn UI ngắn (254 dòng có giới hạn, nhỏ nhất ≤6 ký tự), chữ SMS/trả phí. Tên khoá chân dung phải đúng y hệt thuật ngữ.
  - Đã chốt: `dp_work/thuat-ngu.tsv` (70 mục) và `dp_work/vai.md`. Bảng glyph: `dp_work/charset.txt` (f13c).
  - Ghi chú: 9 dòng bị dp bỏ qua (`……`, `？？？`, `wp`). T06 lo chuẩn hoá dấu toàn khổ khi đóng gói.
  - Hoàn thành khi: `dp check` OK, công nhân đã `dp sample 30`. Quản đốc nghiệm thu (`status/check/sample 15` + duyệt mục `mới`) rồi `dp merge`.
- [ ] T06. Patch text + xuống dòng/phân trang/khung thoại (phụ thuộc T04, T05). **Quản đốc nghiệm thu**
  - Hoàn thành khi: jar_check PASS; ảnh chụp hội thoại, menu, túi đồ, kỹ năng, nhiệm vụ không tràn hay đè chữ; zip Mốc 2.
- [x] T07. Ảnh có chữ (`.xng`/PNG) (phụ thuộc T02)
  - Hoàn thành khi: codec `.xng` đóng gói lại khớp từng byte; ảnh có chữ đã vá bằng `pixeltext` cỡ 13c; `pixeltext check --size 13` OK.
- [ ] T08. Build cuối K790 + bàn giao (phụ thuộc T06, T07). **Quản đốc nghiệm thu**
  - Hoàn thành khi: `rebuild.sh` tái tạo jar cuối; jar_check PASS; run_emu qua các màn chính không có exception; `out/MHTQ_K790_du_an.zip` đúng cấu trúc bàn giao.
- [ ] T09. Port sang E62 (phụ thuộc T08). **Quản đốc nghiệm thu**
  - Hoàn thành khi: như T08 cho E62 (màn 320x240), `out/MHTQ_E62_du_an.zip`.

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- T01 check_all.sh không OK hoàn toàn (log `logs/check_all.txt`), lỗi do chính bộ tool: (1) bước 1 đòi đúng 7 file font nhưng zip có thêm `f12.bin`; (2) bước 2 `pixeltext selftest` fail ở kiểm tra 15c (serif I i j l...), không liên quan f13c; (3) bước 1b fontgen không import được `pixeltext` khi chạy trong tmp (cần PYTHONPATH tuyệt đối; env.sh đã sửa thành đường dẫn tuyệt đối). Bước 3-6 OK. Chưa xác minh f13c sinh lại khớp f18. Đề nghị quản đốc quyết: chấp nhận hay đổi bộ tool.
  - **Quản đốc quyết:** chấp nhận. Các lỗi đều nằm ở f12/f15c/selftest của bộ tool, không đụng f13c. Dự án dùng nguyên file `fonts/f13c.bin` trong zip, không sinh lại font. BAO_CAO sẽ ghi lại ba lỗi này. T01 tính là xong.
- (A1b, đã xử lý) `$SK/scripts/run_emu.py` truyền `jar 240 320 1` cho freej2me_plus nên game bị đặt 320x1: dùng `mhtq/scripts/run_emu.py` (đã sửa). Đề nghị quản đốc cập nhật skill nếu muốn.
- FernFlower: tải được windup-fernflower 1.0.0.20171018 từ Maven Central (`work/tools/fernflower.jar`).
- (D1, T05) `dp check` còn đúng 1 lỗi: dòng 1036 (血战八方 = `Huyết Chiến Bát Phương`, 22 ký tự) vượt giới hạn ước lượng ≤21; thuật ngữ khoá nên giữ nguyên bản dịch, chờ quản đốc nới giới hạn (dòng này thực tế ~100px) hoặc đổi thuật ngữ. Ngoài ra 48 dòng mô tả item (k=931–978) gốc bắt đầu bằng U+3000: `dp` tự giữ lại (gây cảnh báo ký tự rộng) nên D1 chuẩn hoá thẳng trong `out.tsv` thành 1 dấu cách đầu dòng; nếu `dp merge`/T06 cần lại dấu đầu dòng thì bổ sung khi đóng gói.
- (D1, T05) Dòng ghép chuỗi trong `v.class` (k=1225–1239): 1225 `|Lên cấp:`, 1228 `: Lv`, 1234/1236 `!`, 1235 `|`, 1233 `Đạt giới hạn Lv` để ghép với số; do `dp` cắt khoảng trắng cuối nên T06 cần xem lại cách nối (thêm dấu cách) khi patch `v`.
- (A2-img, T07) Chờ quyết: (1) Logo c_2 đã vá theo font người dùng (~70%), xin người dùng duyệt `logs/img/logo_truoc_sau.png`; mép lỗ trong suốt hơi vuông ở vài chỗ. (2) i_40 (danh hiệu, ô 47x11): nhãn phải rút gọn (Tép Riu, Tà Khí, Chính Khí, Thiết Cốt, Thái Bảo, Ma Đầu, Thánh, Hổ Oai, Thiên Long, Thiên Hành) và lệch thuật ngữ: 铜皮铁骨 vẽ 'Thiết Cốt' (không phải Đồng Bì Thiết Cốt), 虎威将军 vẽ 'Hổ Oai' (Hổ Uy cao 13px, vượt ô 11px); chuỗi đầy đủ chỉ hiện ở text. (3) Nhãn rút gọn do ô hẹp: i_03 (Kỹ, Giáp, N.vụ, B.đồ), i_18 (Tình, Tin), i_19 (Tâm Hồn, Ỷ Thiên), i_01 (Môn), i_02 (Bạo), i_30 'Vấn Thiên' (không đủ chỗ cho Diệp Vấn Thiên), i_41 'Tiệm', i_96 'Tránh', i_cost. (4) i_05/i_06/i_07/i_cost: code chỉ nạp, không thấy vẽ; i_07 ô 13px chỉ chứa 2 chữ cái (Ác/Ái/Tốt, 'Tốt' tràn 1px). (5) Chữ không viền có thể chìm nền: i_96 (đỏ f10e42 trên cảnh đánh), i_03/i_02/i_18 (vàng/kem khi nền sáng), i_39 (olive trên nền tối). Màu thân i_38/i_55/i_41 chọn vàng ffe522 (gốc chia 3 dải trắng/vàng/cam). (6) Logo hãng logo1-7 (Mostar, game1313, 9Game có khẩu hiệu tiếng Trung, WAP3, Sohu, 3GMAX, Hiyou) KHÔNG vá; hỏi người dùng có muốn xoá/thay khẩu hiệu không. i_47 (23x24) có thể là chữ thư pháp, không chắc, để nguyên.

## Quyết định của quản đốc sau T05
- Nghiệm thu T05 **đạt**: 1250/1250 dòng; `dp check` chỉ còn 1036 (vượt giới hạn ký tự ước lượng). Quản đốc đã đo px thật mọi chuỗi một dòng: chỉ `K.n07` "Huyết Chiến Bát Phương" rộng 107px, vượt hộp 101px (danh sách kỹ năng, canh giữa). Giữ thuật ngữ. T06 xem ảnh chụp danh sách kỹ năng: nếu chữ bị cắt hoặc đè viền thì chỉ cho `K.n07` dùng "Huyết Chiến B.Phương"; nếu không thì giữ nguyên.
- Đã duyệt toàn bộ thuật ngữ `mới` (bỏ cờ). Bản ghép: `work/dp_work/final/dp_in.tsv` (cột `dịch`, khoá theo `id`). T06 điền vào cột `dịch` của `work/text_can_dich.tsv` theo `id`.
- Dòng dp bỏ qua: `……` giữ nguyên (f13c có `…`); `？？？` → `???`; `wp` giữ nguyên. Khi đóng gói, chuẩn hoá dấu câu toàn khổ còn sót (？！，：（）　) sang bán khổ.
- Chuỗi ghép trong `v.class`, `m.class`, `w.class`… (k=1225–1239 và các toast ghép số/tên): T06 phải đọc bytecode chỗ `StringBuffer.append` để đặt dấu cách đúng giữa các mảnh (ví dụ "Nhận" + tên đồ → "Nhận " + tên). Chuỗi gốc không có dấu cách nên phải thêm vào bản dịch của mảnh tương ứng (ghi lại trong log), không sửa logic ghép.

## Quyết định của quản đốc: chạy song song T07 (người dùng yêu cầu)
- Người dùng yêu cầu thêm một công nhân vẽ ảnh, nên **A2-img làm T07 song song với A1d (T06)**. Đây là ngoại lệ có chủ đích với luật "1 công nhân chạy thật".
- Ranh giới để không đụng nhau:
  - A2-img chỉ tạo/sửa `scripts/xng_*.py`, `scripts/img_*.py`, `work/img/**`, `logs/img/**`, `logs/shots_img/**`.
  - Ảnh đã vá đặt ở `work/build/img_overlay/` (tên file đúng như trong jar). Chưa đưa vào build_m2; T08 mới gộp.
  - Không sửa class, `.dat`, `build_m*.sh`, `text_*.py`, `patch_*.py`.
  - Chạy emu thì dùng thư mục riêng `work/emu_cwd_img` và jar thử riêng `work/build/img_test.jar`.
  - Trong PLAN.md, A2-img chỉ ghi vào dòng của mình ở "Bàn giao công nhân", "Nhật ký", "Vướng mắc".

## Quyết định: logo tên game (người dùng chốt)
- Font: `mhtq/fonts_user/Fz-Harukaze-v2.ttf` (người dùng cung cấp, đủ glyph trừ "–", không dùng tới). Chỉ dùng cho logo; chữ UI vẫn dùng f13c.
- Chữ trên logo, 2 dòng như gốc: dòng lớn "Mộng Hồi Tam Quốc" (thay 梦回三国), dòng nhỏ "Tàn Kiếm Vấn Tình Thiên" (thay 残剑问情天).
- Mức giống: khoảng 70% so với logo gốc (vị trí, cỡ, màu thân, viền/đổ bóng tương tự gốc). Logo được giữ hiệu ứng như gốc, là ngoại lệ với luật "nét đơn" của ảnh UI.
- Render TTF bằng PIL ở kích thước thật rồi lượng tử về palette/kích thước như ảnh gốc. Vá cả bản K790 lẫn ảnh bìa E62 (E62 làm ở T09). Làm ảnh trước/sau để người dùng duyệt.

## Quyết định: logo khởi động + logo game (người dùng chốt)
- Logo tên game (`c_2`) của A2-img: người dùng **duyệt dùng luôn**.
- Logo khởi động: thay `logo1.png` bằng ảnh người dùng gửi, `mhtq/fonts_user/logo_namp.png` (240x320, "Việt hóa bởi NAMP"). Theo bytecode, `l.b()` luôn vẽ `/logo1.png` 20 khung trên nền trắng 0xFFFFFF, căn qua `u.a(g)`. Các logo2–7 chỉ hiện khi CID khớp bảng `l.a` (CID "00" không khớp), nên giữ nguyên.
- Yêu cầu file: PNG 8-bit (palette nếu giữ được chất lượng, không interlace, không chunk lạ) để giảm jar và heap lúc giải mã. Kích thước 240x320 cho K790. E62 (màn ngang) làm bản riêng ở T09.

## Bàn giao công nhân (trạng thái dở dang, cập nhật liên tục)
- A1: T01, T02 xong. `logs/text_path.md` hoàn chỉnh; prototype parser ở `work/t02_proto/`.
- A1b: T03 và T04 XONG (A1b dừng ở đây). T03: scripts/mhtqlib.py, textmodel.py, text_extract.py, text_pack.py; `work/text_can_dich.tsv` (1425 dòng, cột `id file offset/khoá gốc dịch ghi_chú giới_hạn`); `logs/roundtrip.txt` PASS. Lệnh: `python3 -I scripts/text_extract.py [--merge TSV_cu]`; `python3 -I scripts/text_pack.py --tsv work/text_can_dich.tsv --out DIR` (kiểm mã màu, `#` lạ, glyph f13c, đồng bộ khoá, cảnh báo độ rộng); `python3 -I scripts/text_pack.py --selftest --log logs/roundtrip.txt`.
  T04: `scripts/patch_font.py` (thay thân f.a(String)I/a(C)I/a()I/b()I; u.a(CIII)V và u.a(String,III)V đổi 2 lệnh gọi drawChar/drawString thành invokestatic ViFont.dc/ds; `src/patch/ViFont.java`; preverify chỉ f, u, ViFont; thêm BitmapFont.class + font/f13c.bin); `scripts/build_m1.sh` dựng lại Mốc 1 + ảnh `logs/shots_m1/` + `out/MHTQ_K790_moc1.zip`. `scripts/run_emu.py` là bản SỬA của skill (FJ nhận `jar 0 W H scale`; bản skill đặt màn 320x1; chạy trong work/emu_cwd, xoá sạch mỗi lần).
  Tiếp theo (không phải việc của A1b): T05 dịch (đầu vào `work/text_can_dich.tsv`; thuật ngữ khoá: Diệp Vấn Thiên/Tiểu Duy/Anh Ninh; dòng KHOÁ phải cùng một chuỗi); T06 viết lại `k.b`/`j.a` (ngắt dòng theo dấu cách bằng ViFont, 3 dòng/trang, chặn `#` cuối chuỗi) rồi gộp text_pack -> patch_font (`--overlay` thư mục text_pack); T07 ảnh `.xng`.
- A1c (T06, đang làm): XONG bước 1-3 (mã): `scripts/t06_fill.py` (dp_in -> `work/text_da_dich.tsv`, chuẩn hoá toàn khổ, bảng PAD dấu cách cho chuỗi ghép; log `logs/t06_fill.txt`); `text_pack` chạy OK (chỉ cảnh báo K.n07 107px). `scripts/patch_wrap.py` (+`src/patch/ViWrap.java`, `ViTalk.java`): k.b/j.a(String,char,int) -> ViWrap.wrap (ngắt dấu cách, cache 4 mục), m.a(7 tham số) 3 dòng/trang (đổi hằng + ViTalk.done/lt/fill/step, cùng độ dài), p.a(u,String,String[],II[I) bước dòng 15/clip 15/tâm +8. Thử emu: thoại 3 dòng ngắt đúng. Lệnh thủ công: `python3 -I scripts/patch_font.py --overlay work/build/m2_text --out-jar work/build/MHTQ_K790_font.jar` rồi `python3 -I scripts/patch_wrap.py` -> `work/build/MHTQ_K790_m2.jar`.
  A1d: XONG scripts/build_m2.sh (chạy lại toàn bộ, jar_check PASS, JAD size khớp; ra work/build/MHTQ_K790_moc2.jar/.jad, log logs/jar_check_m2.txt). Còn: ảnh chụp logs/shots_m2/ (dò đường vào các màn bằng run_emu --steps), kiểm hộp tên 81/91px, K.n07, manifest tên MIDlet ASCII "Mong Hoi Tam Quoc", zip out/MHTQ_K790_moc2.zip, BAO_CAO, tick T06.
  A2-img: T07 XONG. Codec `scripts/xng_codec.py` (118/118 khớp byte, `logs/img/roundtrip.txt`); logo `scripts/img_logo.py`; ảnh UI `scripts/img_patch.py` (bảng ô+nhãn trong SPEC; chạy `source work/tools/env.sh; python3 -I scripts/img_patch.py`); đầu ra 20 ảnh ở `work/build/img_overlay/*.xng|.png` (c_2 logo + 19 ảnh UI); T08 chỉ cần chép các .xng này đè vào jar. Bảng `logs/img/anh_co_chu.md`, trước/sau `logs/img/truoc_sau_*.png`, `logs/img/logo_truoc_sau.png`; thử emu `work/build/img_test.jar` (moc2 + overlay), ảnh `logs/shots_img/` (logo và menu 'Trò chơi mới' đúng). E62: chạy lại cùng script trên ảnh E62 ở T09. THÊM: logo khởi động `scripts/img_logo1.py` -> `work/build/img_overlay/logo1.png` (240x320, 8-bit palette 256, không interlace, chỉ IHDR/PLTE/IDAT/IEND, 38.6KB; đây là PNG, không phải .xng, T08 chép đè `/logo1.png`). `work/build/img_test.jar` dựng lại (moc2 + 20 xng + logo1.png), emu không exception, ảnh `logs/shots_img/logo1_boot.png` (logo vừa kín màn).

## Nhật ký
- D1: T05 xong: 1250/1250 dòng dịch tay; `dp check` chỉ còn lỗi 1036 (xem Vướng mắc); đã `dp sample 30` và sửa; thêm ~30 mục `mới` vào thuat-ngu.tsv chờ quản đốc duyệt.
- A1b: T04 xong: hook f13c bằng bytecode tĩnh; jar_check PASS (Thêm BitmapFont/ViFont/font/f13c.bin; Đổi f,u + file thử); ảnh chụp logs/shots_m1/ (hộp nhạc, thoại có tên `Kiếm Tiên`, trang Trợ giúp đủ dấu); zip out/MHTQ_K790_moc1.zip. Ghi chú heap trong BAO_CAO (63KB/màu, CACHE=4 => ~252KB, đỉnh ~126KB thêm). Phát hiện thêm: ngắt ký tự làm thoại bị cắt giữa từ (cần T06); skill run_emu.py sai thứ tự tham số FJ (đã sửa bản trong scripts/).
- A1b: T03 xong: 1425 slot (script 998 gồm 33 tên người nói, item 98, mission 63, skill 51, mate 1, class 213); roundtrip 19/19 file giống byte; kiểm đồng bộ khoá 叶问天/小唯/婴咛 (script/y/v/h/mate.dat; phát hiện thêm h.class cũng chứa 叶问天 làm tên người nói -> là khoá).
- Quản đốc: nghiệm thu T02 đạt (đã chạy lại rt.py: 4 file roundtrip True; soát bytecode khoá `y.a` @18–314). Chốt quyết định tên khoá, khung 3 dòng, đo/vẽ. A1 dùng 278k token → T03/T04 giao công nhân mới (A1b).
- Quản đốc: kiểm kê xong, chốt phong cách + font, tạo PLAN.md, chép jar gốc vào `goc/`.
- A1: T01 xong (tool setup, CFR/FernFlower/javap đủ 27 class, inventory); check_all lỗi do bộ tool đã được quản đốc chấp nhận.
- A1: T02 xong: logs/text_path.md (a)-(e); parse+serialize khớp từng byte script/item/mission/skill.dat; .xng giải 118/118; phát hiện khoá chân dung 叶问天/小唯/婴咛 trong y.a(String); ngắt dòng theo ký tự; font hệ thống SMALL (bf/bm.dat không có).
- A2-img: T07 xong: codec .xng 118/118; logo c_2 vẽ bằng Fz-Harukaze; 19 ảnh UI vá 13c (pixeltext check OK cả 13 và 13c, 66 chuỗi); overlay 20 ảnh; thử emu OK (logs/shots_img). Không đụng class/.dat/build.
