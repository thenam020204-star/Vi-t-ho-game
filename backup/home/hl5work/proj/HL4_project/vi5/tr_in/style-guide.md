# Hướng dẫn dịch HL5 (Heroes Lore 5 / 영웅서기5) — dùng chung cho mọi lô

Bạn đang dịch tiếp một dự án Việt hoá game J2ME đã dịch được 25%. Đây LÀ BẢN DỊCH THẬT sẽ được nạp lại vào game — không phải bản nháp.

## Việc cần làm
Đọc file input được giao (TSV, cột: ID, Nhom, File, Tieng Han, So lan, Rong goc px, Gioi han byte).
Dịch cột "Tieng Han" (tiếng Hàn) sang tiếng Việt cho TỪNG dòng.
Ghi kết quả ra file output đúng đường dẫn được giao, định dạng TSV 2 cột, có header:
```
ID	Tieng Viet
K00001	<bản dịch>
```
Giữ đúng thứ tự dòng và đầy đủ 100% số dòng của input — không được bỏ sót, không đổi ID.

## Tài liệu bắt buộc đọc trước khi dịch
- `phan-tich-vai.md` (cùng thư mục file input) — bảng nhân vật + ma trận xưng hô. BẮT BUỘC tuân theo tuyệt đối, đặc biệt: Ren luôn gọi Schurtz bằng kính ngữ "ngài Schurtz"/"Schurtz-nim" xuyên suốt; Schurtz cộc, mỉa mai, xưng tao/tôi; Tir/Lurain nói trang trọng cổ kính (ta, ngài/khanh/ngươi); Marc/Lupus/Kefnes thù địch (ta/tao — ngươi/mày).
- `thuat-ngu.tsv` (cùng thư mục) — bảng thuật ngữ đã chốt (tên nhân vật, địa danh, lớp nhân vật, rarity...). MỌI thuật ngữ xuất hiện trong bảng này PHẢI dùng đúng bản dịch đã chốt, không tự sáng tạo cách khác.

## Văn phong
- Câu văn thuần Việt tự nhiên, không dịch word-by-word.
- Danh từ/chức danh/tên chiêu thức/tên phe phái → Hán Việt ngắn gọn nếu hợp; tên riêng người/địa danh → Latin theo thuat-ngu.tsv.
- Chỉ số/UI → viết tắt tiếng Anh: HP, MP, ATK, DEF, SPD, EXP, Lv.
- Lớp nhân vật giữ tiếng Anh: Warrior, Rogue, Gunslinger, Knight, Sorcerer.
- Có dấu đầy đủ.
- Không bịa thêm ý, không bỏ ý, không đổi số/tên/giá trị. Chỗ gốc mơ hồ → dịch sát nghĩa nhất, không đoán bừa (không cần ghi log, agent điều phối sẽ rà soát lại).
- Dòng trùng "Tieng Han" giống hệt nhau → dịch giống nhau, trừ khi rõ ràng người nói/ngữ cảnh khác biệt.
- Rong goc px là ước lượng độ rộng gốc (đơn vị px) trong màn hình 240px — với dòng UI/menu/vật phẩm/tên ngắn, ưu tiên dịch NGẮN GỌN, súc tích, tránh dài hơn quá nhiều so với bản gốc. Với hội thoại (Cốt truyện) được phép dài hơn vì có xuống dòng tự động — không cần gò ép ngắn.

## Mã điều khiển PHẢI giữ nguyên y hệt (không dịch, không xoá, không đổi vị trí tương đối, không đổi số lượng)
`;` `{` `}` `` ` `` `^` `<` `|` `&` `#nn` (mã số) `%d` `%s` — đây là mã xuống dòng/tô màu/placeholder của engine. Ví dụ: `|(text){` → `|(bản dịch){`, `무황껍질` nằm giữa `}...{` → giữ nguyên cặp `}...{` bao quanh phần dịch tương ứng. Khoảng trắng đầu/cuối dòng gốc có thì giữ nguyên.

## Trường hợp đặc biệt (tham khảo bao-cao-dich.md nếu cần thêm chi tiết)
- Trợ từ ngữ pháp Hàn dạng "(이)가"/"은/는"/"을/를" đứng một mình (không có nghĩa từ vựng) → để trống cột Tieng Viet, không dịch.
- Dòng trông giống ID/tên file/mã kỹ thuật thuần ASCII không phải câu tiếng Hàn thật → để trống, không dịch (đây là dữ liệu, không phải text hiển thị).

## Sau khi dịch xong
Không cần rà soát kỹ thuật kỹ càng (sẽ có bước rà soát tự động riêng), nhưng tự kiểm tra nhanh: đủ số dòng, không bỏ mã điều khiển, không sót tiếng Hàn trong cột dịch.
Trả lời ngắn gọn: đã dịch bao nhiêu dòng, có gặp thuật ngữ mới cần bổ sung vào thuat-ngu.tsv không (liệt kê ngắn gọn nếu có), có câu nào thực sự không đoán được nghĩa/người nói không (liệt kê ID nếu có).
