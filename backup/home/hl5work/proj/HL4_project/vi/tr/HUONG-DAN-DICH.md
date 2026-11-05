# Hướng dẫn dịch — Heroes Lore 4 (Hàn → Việt)

Bạn dịch một khối text (file `tr/chunks/<tên>.json`) của game điện thoại Hàn "영웅서기4: 환영의 가면" sang tiếng Việt.
Đọc kỹ trước khi dịch: `tr/phan-tich-vai.md` (cốt truyện, nhân vật, MA TRẬN XƯNG HÔ), `tr/thuat-ngu.tsv` (thuật ngữ BẮT BUỘC), và nếu có: `tr/out/t1_names.json`, `tr/out/t2_items.json` (tên riêng/vật phẩm đã dịch — tra bằng cách so chuỗi Hàn trong `tr/chunks/t1_names.json`, `tr/chunks/t2_items.json`).

## Đầu vào
Mỗi phần tử: `id`, `ko` (text gốc), `kind` (dialog/mapname/item/skill/quest/menu/help/tip/bin/...), `src` (file:vị trí — cùng file scn = cùng cảnh, theo thứ tự), `speaker` (người nói, tên Hàn; có thể thiếu hoặc sai), `cap` (giới hạn byte sau mã hoá), `note`.

## Đầu ra
Ghi file `tr/out/<tên>.json` là MỘT object JSON: `{"<id>": "<bản dịch>", ...}` đủ mọi id của khối (UTF-8, ensure_ascii=false).
Sau khi ghi, CHẠY: `python3 /home/claude/work/vi/tr/check.py /home/claude/work/vi/tr/chunks/<tên>.json /home/claude/work/vi/tr/out/<tên>.json` và sửa đến khi in `OK`.
Mẹo: dịch theo từng cảnh (nhóm `src` cùng file), ghi dần ra file (có thể ghi nhiều lần, lần sau ghi đè bằng bản đầy đủ hơn).

## Văn phong
- Câu thuần Việt tự nhiên, đúng giọng nhân vật (xem phân tích vai). Không dịch word-by-word.
- Thuật ngữ/chiêu thức/danh hiệu → Hán Việt ngắn gọn; tên người, địa danh → Latin theo `thuat-ngu.tsv` (tuyệt đối thống nhất).
- Xưng hô theo MA TRẬN XƯNG HÔ; nhìn cảnh (các dòng trước/sau cùng file) để biết ai nói với ai.
- Không bịa thêm, không bỏ ý, không đổi số/tên. Chỗ gốc mơ hồ: dịch sát nhất.
- Có dấu đầy đủ. Chữ HOA toàn bộ hạn chế dùng (trừ viết tắt HP/SP/MP, tên kỹ năng tiếng Anh gốc).
- Dòng trùng nghĩa → dịch thống nhất.

## QUY TẮC KỸ THUẬT (vi phạm = lỗi game)
1. Các ký tự sau là MÃ ĐIỀU KHIỂN (đổi màu/hiệu ứng, không hiển thị): `` ` { } & | $ ^ < ' " `` và `=` (khoảng trống hẹp). Giữ ĐÚNG số lượng từng loại như bản gốc, đặt bao quanh cụm từ tương ứng về nghĩa. Ví dụ: `난 }케프네스{라고 한다.` → `Ta là }Kepnes{.`; `&Tên;$.......{퀘스트 입수!` giữ cấu trúc.
   → Vì vậy KHÔNG được dùng dấu nháy đơn/nháy kép, dấu backtick… trong câu dịch nếu gốc không có đúng mã đó ở vị trí đó.
2. `;` = xuống dòng. Hội thoại: hộp thoại tự ngắt dòng theo từ, nên có thể bỏ/dời `;` cho tự nhiên (giữ `;;` nếu là ngắt đoạn). Với menu/UI/tên (kind khác dialog) giữ số `;` như gốc.
3. Chỉ dùng ký tự: chữ Việt (a-z, A-Z có dấu), số, khoảng trắng và `. , : ! ? - ( ) / % + * # @ [ ] ~ …`. KHÔNG dùng: `_`, `“ ” ‘ ’ « » — –`, chữ Hàn/Hán, emoji. Dấu ba chấm: `...` hoặc `…`.
4. Giới hạn độ dài: `cap` là số byte tối đa; checker ước lượng (mỗi âm tiết có dấu hoặc ≥3 chữ ≈ 2 byte, dấu cách/chữ khác 1 byte). Tên vật phẩm/quái/kỹ năng/menu phải NGẮN (thường cap 8–20 byte ≈ 4–8 âm tiết). Quá dài → rút gọn chủ động (bỏ từ phụ, dùng Hán Việt ngắn).
5. Không thêm/bớt khoảng trắng đầu/cuối so với gốc.
6. Phím: `5키` → `phím 5`, `방향키` → `phím hướng`, `확인키` → `phím OK`, `2468키` → `phím 2468`. Giữ nguyên số, %d, chữ Latin có sẵn (HP, SP, Lv, tên đồ tiếng Anh).
7. Dòng rác/vô nghĩa (note "nghi rác") hoặc chỉ là nhãn debug → giữ nguyên chuỗi gốc NẾU nó không có chữ Hàn; nếu có chữ Hàn thì dịch sát hoặc chuyển Latin.
