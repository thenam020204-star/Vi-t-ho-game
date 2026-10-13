# BÁO CÁO — Việt hoá Dị Thế Tà Quân 240x320

**Kết quả**: `out/Di_The_Ta_Quan_VH_240x320.jar` (1334326 byte, gốc 1524421) + `.jad` (MIDlet-Jar-Size khớp). CLDC-1.0 / MIDP-2.0, class 45.3/47, đã preverify (StackMap).

## Đã làm
1. **Dịch** `string.pak`: 4629 chuỗi (2756 chuỗi duy nhất cần dịch, 142 dòng không dịch là khoá/ID/dấu câu), dịch tay bằng `dp`, văn phong tiểu thuyết, Hán Việt cổ trang.
   - `dp check`: **OK 2756/2756**. Thiếu glyph f12: **0**.
   - Bộ đóng gói lại cho ra file giống hệt từng byte khi chưa sửa (đã kiểm).
   - 30 chuỗi hiển thị nằm cứng trong `c.class` (Đang vào game..., Tạm dừng..., Nhận..., Sao biển X...) được thay qua LDC.
2. **Chữ trong game** (class hook `VH`, font f12):
   - Mọi `drawString/drawChar/charWidth/stringWidth` của `c.class` đi qua VH.
   - DrawLineString, CalcTextPos, CalcTextPage ngắt theo px ở dấu cách.
   - Chữ chạy hội thoại kiểm tra cả từ trước khi xuống dòng (`VH.nw`).
   - Khung tên rộng theo px. Hội thoại tăng từ 2 lên 3 dòng (`h(0,2,x)` -> 3, `UI_DIALOG_HEIGHT` = 3*R).
   - FONT_HEIGHT = 13, FONT_WIDTH giữ 12 cho bố cục gốc.
3. **Gỡ nhà mạng / tính năng ngoài game**:
   - State 18: bỏ màn hỏi âm thanh, chỉ hiện logo NAMP khoảng 2,5 giây rồi **tự bật âm thanh**, vào thẳng GS_INIT.
   - Bỏ state 19: máy quay số logo China Mobile / 手机游戏 / ICE TEA / Sohu, nhạc hiệu, phần khởi tạo SDK 弱联网 (AppId 10382).
   - Bỏ state 21: hỏi kết nối mạng.
   - Menu chính bỏ 游戏圈 / 精品推荐 / 更多游戏 (`VH.mainMenu` nhảy qua mục 2–4, chặn cả xác nhận).
   - Thoát game -> GS_QUIT (không mở link 3g.tysis.cn). Hai lệnh SetGameState(15 = mở link) -> 17.
   - Bốn chỗ "tải điểm lên mạng" (state 20) -> về menu.
   - Trang Giới thiệu và Hướng dẫn: bỏ Sohu, số điện thoại / email CSKH, đoạn phí 3G China Mobile; thay bằng ghi công Việt hoá.
   - SMS: bản build này không có code gửi SMS (CSms.java bị `//@` loại khi biên dịch). Chuỗi SMS còn trong pak không ai gọi, vẫn dịch trung tính.
   - Xoá khỏi jar: source .java gốc, p1–p3, logo_G, BG, m0–m6, sound.mid, res/p.png.
4. **Ảnh**:
   - Logo khởi động NAMP; ảnh bìa tiêu đề mới (giữ vệt mực gốc sau nhãn menu).
   - Nhãn menu chính; atlas UI `png7`: 94 module (kiểu B: thân + viền 1px đúng màu gốc, người dùng chọn).
   - Bản đồ thế giới 14 địa danh; `LOAD` (Đang tải); nút Bí kíp; biểu tượng cửa hàng Giáp/Võ/Dược/Kỹ/Tạp; biển hiệu Võ/Dược/Gạo/Bạc.
5. **An toàn KVM**: 5 chỗ `DataInputStream.read(byte[]...)` bỏ qua giá trị trả về -> `readFully` (CLDC 1.0 có sẵn).

## Kiểm tra
- `jar_check`: **RESULT: PASS**.
  - Đổi: c.class, i.class + tài nguyên. Thêm: VH.class, font/f12.bin.
  - WARN API mới (readFully, readUTF, printStackTrace, String.indexOf(II)) đều thuộc CLDC 1.0.
- `rebuild.sh` dựng lại từ jar gốc: tài nguyên giống hệt từng byte, nội dung jar trùng 259/259 entry với jar đã test.
- FreeJ2ME headless (`out/shots/`, `logs/`), không có exception của game:
  - Logo, menu chính (6 mục), cảnh mở đầu, cung điện (khung tên, mã màu đỏ), màn tải.
  - Menu vòng trong game, Hệ thống, Lưu game, Nhân vật, Trang bị, Vật phẩm, Chiến lợi phẩm, Kỹ năng, Nhiệm vụ, Cài đặt, Hướng dẫn, Giới thiệu.
  - Lỗi `Sequencer ... is null` là do môi trường headless không có MIDI.
- `tools/check_all.sh`: mục 1 (đếm 7 file font, thực tế có 8 vì thêm f12) và mục 2 (selftest 15c) FAIL **y hệt trên bộ tool gốc chưa đụng tới**, không do bản vá. Mục 3–6 OK.

## Debug — lỗi đã gặp và cách xử lý
- Script chạy giả lập của skill truyền tham số sai cho FreeJ2ME-Plus (canvas 320x1) -> `scripts/run_emu.py` thêm cờ `0`, cắt ảnh theo thanh menu.
- `i.<clinit>`: `aE = 2*R` có `dup` trước putstatic -> sửa mẫu tìm trong Patch.
- Nút phím mềm phải "Hệ thống" tràn mép màn hình (khung gốc canh trái) -> căn giữa trong nút.
- Tuỳ chọn "Tự động/Thủ công" đè dấu ngoặc -> "Auto/Tay", nhãn hàng "Hội thoại".
- Tiêu đề màn Hướng dẫn trùng "Giới thiệu" -> "Hướng dẫn".
- Thiếu `/link` sau khi xoá ảnh quảng cáo (game vẫn nạp, có try/catch) -> PNG 1x1 trong suốt.
- `make_res.sh` lỗi bị `tee` che -> `set -o pipefail`.

## Còn tồn / cần lưu ý
- **Chưa đi tới trên giả lập**: màn chiến đấu (nút Đả/Kỹ/Vật/Mua/Lui, chữ thưởng), cửa hàng, luyện đan, các chương sau.
  - Các khung này dùng chung bộ vẽ chữ và module đã kiểm, kích thước nút tròn giữ nguyên 24x24, nhưng vẫn nên chơi thử.
- Chưa thử trên J2ME Loader và máy thật (không có trong môi trường này).
- Nhãn quá chật nên viết gọn: tab Binh/Áo/Giày/T.sức, Thuốc/Liệu/Khác; tên tab T.Nghệ/T.Hàn; NV chính/NV phụ; nút tròn "Đả" (攻).
- Biểu tượng cửa hàng png43: phần dưới hình bị chữ Việt thay chỗ.
- Class SDK mạng (at, eb/*...) vẫn nằm trong jar nhưng không còn đường gọi. Để lại để preverify/verify không thiếu kiểu; chuỗi Hán bên trong không hiển thị.
- Chuỗi debug `System.out` tiếng Trung trong c.class không hiển thị, để nguyên.
- Một số mô tả gốc có lỗi đánh máy:
  - ITEMS_DESC_45 lặp tên 强壮果果, đã dịch theo đúng vật phẩm (Linh Xảo Quả).
  - 87 强装 -> cường tráng.
- Nội dung phụ (Chí Nam/Lan, Âu Dương Phong...) có vẻ sót từ game khác cùng engine, vẫn dịch đủ.
