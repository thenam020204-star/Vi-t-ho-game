# Quy tắc chung — Việt hoá / mod game J2ME (NAMP)

Đúc kết từ dự án **Tiên Sủng Kỳ Duyên** (`tsky/` trên nhánh `claude/effort-auto-8khe48`). Áp dụng cho các dự án sau cùng bộ tool `Tool_Viet_Hoa_NAM_P.zip`.

## 1. Mục tiêu và nguyên tắc tối cao
- Bản vá phải chạy đúng trên **KVM/CLDC 1.0 + MIDP 2.0 thật**. FreeJ2ME chỉ để test; J2ME Loader (Android/ART) không phải chuẩn.
- Thứ tự tin cậy: **bytecode thật > spec CLDC/MIDP > hành vi runtime > nhiều emulator > output decompiler**. CFR chỉ để đọc; mâu thuẫn thì tin `javap -c`.
- "Game vẫn chạy" **không** chứng minh bản vá an toàn. Mỗi bản build phải qua: preverify → `jar_check` PASS → chạy emulator → xem ảnh chụp.
- Không đoán: thiếu bằng chứng thì ghi "chưa xác định", tìm thêm reference, rồi mới sửa.

## 2. Tổ chức làm việc (quản đốc – công nhân)
- **Quản đốc (Opus)**: phân tích, chốt quyết định thiết kế, viết `PLAN.md`, giao việc, nghiệm thu, giải quyết vướng mắc. Không tự implement, không tự dịch.
- **Công nhân (Sonnet)**: làm đúng task được giao, không đổi quyết định thiết kế; vướng thì ghi mục **Vướng mắc** trong `PLAN.md`.
- `PLAN.md` là nguồn sự thật (quyết định, task + tiêu chí "Hoàn thành khi", Nhật ký, Vướng mắc). `PROGRESS.md` ngắn cho phiên sau.
- Mặc định 1 công nhân chạy thật. Chạy song song chỉ khi người dùng yêu cầu và **mỗi công nhân một nhóm file riêng** (ví dụ: dịch `dich/` | vẽ ảnh `res_vi/` | code `hook/ patcher/`); mỗi người chỉ `git add` đường dẫn của mình.
- Task phân tích (T1/T2) làm trước, quản đốc duyệt rồi mới dựng hạ tầng.
- Bị giới hạn usage giữa chừng: công nhân commit dở + ghi tiến độ; hết hạn chờ thì gửi tiếp cho đúng công nhân đó (giữ ngữ cảnh), không tạo mới.
- Commit + push thường xuyên (mỗi task, mỗi ~300 dòng dịch); không để việc chỉ nằm trên đĩa.

## 3. Hỏi người dùng trước khi làm
Luôn hỏi gộp một lượt: **văn phong dịch**, **font (cỡ + kiểu)**, thuật ngữ then chốt (vd 仙宠 tiên sủng, 宠物 sủng vật, 骑宠 tọa kỵ), yêu cầu khởi động (logo, hỏi nhạc), bản màn hình khác, nơi phát hành. Điểm nhỏ thì chọn mặc định hợp lý và ghi vào `PLAN.md`.

## 4. Tương thích J2ME (bắt buộc)
- Chỉ API có trong CLDC/MIDP máy đích; không reflection, không float, không API Java SE/Android. Vi phạm → **⚠️ NOT J2ME-SAFE**, không tính là xong.
- Không đổi tên/descriptor/access flag/kiểu dữ liệu của class, field, method gốc. Được **thay thân method** nếu giữ nguyên chữ ký.
- Patch bằng **ASM tĩnh** (không biên dịch lại cả game). Class mới biên dịch ecj `-source 1.3 -target 1.3`; giữ version 45.x; bỏ StackMap cũ, ProGuard `-microedition` sinh lại (preverify).
- Patcher **đếm số chỗ khớp** từng patch, lệch số kỳ vọng thì dừng.
- Không cấp phát String/object trong `paint()`/game loop khi dữ liệu không đổi: cache kết quả đo/ngắt dòng/chuỗi nối.
- Heap nhỏ: đo blob/chuỗi lớn nhất sau dịch; `readUTF` ≤ 65535 byte, blob độ dài `short` có dấu ≤ 32767.

## 5. Chữ và font
- Font hệ thống máy thật thiếu dấu Việt → **mọi** chữ hiển thị đi qua font bitmap (lớp hook `VH` hoặc `BitmapFont` của tool).
- Thay call-site cùng kiểu stack: `Graphics.drawString` → `VH.drawString(Graphics,…)`, `Font.stringWidth/charWidth/substringWidth/getHeight` → `VH.*(Font,…)`.
- Dùng advance width thật của glyph; bỏ mọi chỗ tính theo số ký tự hoặc độ rộng 1 chữ Hán (`k.w*2`, `len*12`).
- Ngắt dòng theo px ở dấu cách, phân trang đồng bộ; khung thoại ≥ 3 dòng; nhãn/phím mềm/mũi tên/cột bảng căn theo độ rộng thật.
- Không tự vẽ glyph mới; lỗi glyph trong font của người dùng (vd `l` giống `L` ở f12) thì báo người dùng.
- Dấu câu Hán đổi ASCII (`：`→`:`, `，`→`,`, `……`→`...`, `“”`→`"`…); không dùng ký tự ngoài charset font.

## 6. Text và dịch
- Viết `extract` + `repack`; **repack khi chưa dịch phải ra giống từng byte** (selftest) trước khi dịch.
- Chỉ trường chứa chữ hiển thị mới dịch; giữ token điều khiển (`j6#`, `@`, `~`…); cấm ký tự phân tách của engine (`^ | # [ ]`).
- Chuỗi bị code so sánh (`equals/indexOf`, khoá, lưu RMS): giữ nguyên trong class và ánh xạ khi vẽ, hoặc dịch **cùng một giá trị** ở mọi nơi; `repack --check` phải kiểm điều này.
- Dịch bằng `dp`: quản đốc chốt `thuat-ngu.tsv` + `vai.md`; **đúng MỘT công nhân dịch tay toàn bộ**; cấm dịch máy/agent khác; `dp check` = OK; quản đốc nghiệm thu (`status/check/sample`, duyệt mục `mới`).
- Giới hạn độ dài tính theo **px** đo bằng font thật, không chỉ số ký tự; tên riêng ưu tiên giữ đầy đủ, chỉnh bố cục trước khi rút tên.

## 7. Ảnh có chữ
- Soát toàn bộ PNG bằng lưới ảnh, lập `docs/anh_co_chu.tsv`.
- Xoá sạch chữ gốc, vẽ bằng `pixeltext` đúng cỡ font của game, giữ kích thước và bảng màu/trong suốt.
- Mặc định của skill: một màu thân chữ, không viền. Người dùng NAMP chọn **viền đen 1px 8 hướng** cho dễ đọc; chữ tối thì dùng viền sáng của gốc.
- Ô quá hẹp thì rút gọn chữ, không ép chữ; nhãn mơ hồ nghĩa thì xác minh bằng ảnh chụp trong game.

## 8. Khởi động, logo, quảng cáo
- Xoá logo nhà phát hành, thay logo NAMP; bỏ màn hỏi âm thanh, nhạc luôn bật; ẩn mục mở web quảng cáo/"Game khác".
- Ảnh gốc bị bỏ chỉ được xoá khỏi jar khi không còn tham chiếu.

## 9. Bản màn hình khác
- Tìm profile có sẵn trong code trước (vd `MIDlet-Description: rpg,E62` → 320x240); hai bản chỉ khác resource + manifest, dùng chung class đã vá.
- Ảnh toàn màn hình làm bản ngang riêng, không kéo giãn méo.
- Kiểm remap phím của profile: phải nhận cả phím số lẫn QWERTY.

## 10. Kiểm tra và không làm yếu kiểm tra
- Không skip/nới phép thử để cho "pass". Lỗi có sẵn từ bản gốc thì ghi trung thực và hỏi người dùng cách xử lý.
- Quản đốc tự chạy lại `jar_check` và `check_all.sh` trước khi phát hành.

## 11. Bàn giao
- `rebuild.sh` dựng lại từ jar gốc ra jar/jad cuối (`MIDlet-Jar-Size` khớp); thử giải nén gói dự án và rebuild từ đó.
- Gói `<ten>_du_an.zip`: `README_DU_AN.md`, `PROGRESS.md`, `BAO_CAO.md` (có mục Debug, việc tồn), `rebuild.sh`, `out/`, `goc/`, `src/`, `scripts/`, `logs/`, `tools/` + `VERSIONS.md`.
- Phát hành: dự án trên nhánh làm việc; jar/jad/zip vào `main:releases/<TenGame>/`; cập nhật bộ tool trên `main` khi có thay đổi (kèm `TOOL_THAY_DOI.md`).
- Bài giới thiệu đăng Facebook: tránh từ dễ bị kiểm duyệt (giết, máu, chết, link tải, miễn phí, crack/hack, tên quốc gia/chính trị), không spoil đoạn cuối, file để ở bình luận.
