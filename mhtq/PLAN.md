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
- [ ] T03. Bộ trích xuất/đóng gói text (phụ thuộc T02). **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: `scripts/text_extract.py` + `scripts/text_pack.py` (Python, chạy bằng `python3 -I`), xuất `work/text_can_dich.tsv` với các cột `id file offset/khoá gốc dịch ghi_chú giới_hạn`. Giữ nguyên và đánh dấu mã điều khiển. Chuỗi trong class vá qua constant pool (dùng `$SK/scripts/classfile.py` nếu hợp).
  - Hoàn thành khi: đóng gói lại khi cột dịch trống/bằng gốc ra từng `.dat` và `.class` giống hệt từng byte (`cmp`); log ở `logs/roundtrip.txt`.
- [ ] T04. Hook BitmapFont f13c (phụ thuộc T02). **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: thêm BitmapFont + `/font/f13c.bin`, sửa bytecode `f`/`u` (và nơi khác theo T02) để vẽ/đo bằng BitmapFont; script `scripts/patch_font.py` có thể chạy lại được. Build → preverify → jar_check → run_emu, thử nghiệm bằng cách thay tạm một chuỗi menu đầu thành tiếng Việt có dấu.
  - Hoàn thành khi: `jar_check` PASS, ảnh chụp `logs/shots_m1/` cho thấy chữ Việt có dấu đúng; zip Mốc 1 ở `out/`.
- [ ] T05. Dịch toàn bộ text (phụ thuộc T03). Đúng MỘT công nhân Sonnet, quy trình `dp` (dich-pro.md)
  - Quản đốc chuẩn bị `thuat-ngu.tsv`, `vai.md`, `PLAN_DICH.md` trước khi giao.
  - Hoàn thành khi: `dp check` OK, quản đốc soát `dp sample` đạt.
- [ ] T06. Patch text + xuống dòng/phân trang/khung thoại (phụ thuộc T04, T05). **Quản đốc nghiệm thu**
  - Hoàn thành khi: jar_check PASS; ảnh chụp hội thoại, menu, túi đồ, kỹ năng, nhiệm vụ không tràn hay đè chữ; zip Mốc 2.
- [ ] T07. Ảnh có chữ (`.xng`/PNG) (phụ thuộc T02)
  - Hoàn thành khi: codec `.xng` đóng gói lại khớp từng byte; ảnh có chữ đã vá bằng `pixeltext` cỡ 13c; `pixeltext check --size 13` OK.
- [ ] T08. Build cuối K790 + bàn giao (phụ thuộc T06, T07). **Quản đốc nghiệm thu**
  - Hoàn thành khi: `rebuild.sh` tái tạo jar cuối; jar_check PASS; run_emu qua các màn chính không có exception; `out/MHTQ_K790_du_an.zip` đúng cấu trúc bàn giao.
- [ ] T09. Port sang E62 (phụ thuộc T08). **Quản đốc nghiệm thu**
  - Hoàn thành khi: như T08 cho E62 (màn 320x240), `out/MHTQ_E62_du_an.zip`.

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- T01 check_all.sh không OK hoàn toàn (log `logs/check_all.txt`), lỗi do chính bộ tool: (1) bước 1 đòi đúng 7 file font nhưng zip có thêm `f12.bin`; (2) bước 2 `pixeltext selftest` fail ở kiểm tra 15c (serif I i j l...), không liên quan f13c; (3) bước 1b fontgen không import được `pixeltext` khi chạy trong tmp (cần PYTHONPATH tuyệt đối; env.sh đã sửa thành đường dẫn tuyệt đối). Bước 3-6 OK. Chưa xác minh f13c sinh lại khớp f18. Đề nghị quản đốc quyết: chấp nhận hay đổi bộ tool.
  - **Quản đốc quyết:** chấp nhận. Các lỗi đều nằm ở f12/f15c/selftest của bộ tool, không đụng f13c. Dự án dùng nguyên file `fonts/f13c.bin` trong zip, không sinh lại font. BAO_CAO sẽ ghi lại ba lỗi này. T01 tính là xong.
- FernFlower: tải được windup-fernflower 1.0.0.20171018 từ Maven Central (`work/tools/fernflower.jar`).

## Bàn giao công nhân (trạng thái dở dang, cập nhật liên tục)
- A1: T01, T02 xong. `logs/text_path.md` hoàn chỉnh; prototype parser ở `work/t02_proto/`. Tiếp theo: T03 (viết scripts/text_extract.py, text_pack.py theo định dạng trong text_path.md mục a).

## Nhật ký
- Quản đốc: nghiệm thu T02 đạt (đã chạy lại rt.py: 4 file roundtrip True; soát bytecode khoá `y.a` @18–314). Chốt quyết định tên khoá, khung 3 dòng, đo/vẽ. A1 dùng 278k token → T03/T04 giao công nhân mới (A1b).
- Quản đốc: kiểm kê xong, chốt phong cách + font, tạo PLAN.md, chép jar gốc vào `goc/`.
- A1: T01 xong (tool setup, CFR/FernFlower/javap đủ 27 class, inventory); check_all lỗi do bộ tool đã được quản đốc chấp nhận.
- A1: T02 xong: logs/text_path.md (a)-(e); parse+serialize khớp từng byte script/item/mission/skill.dat; .xng giải 118/118; phát hiện khoá chân dung 叶问天/小唯/婴咛 trong y.a(String); ngắt dòng theo ký tự; font hệ thống SMALL (bf/bm.dat không có).
