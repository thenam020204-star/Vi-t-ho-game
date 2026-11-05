# Ghi chú dịch — batches/A_giaodien_meo.tsv (776 dòng)

## 1. Kết quả kiểm tra tự động (script Python, csv module)
- Số dòng file gốc: 776; số dòng bản dịch: 776 — khớp 100% theo ID, đúng thứ tự, không thiếu/thừa/trùng.
- Không còn ký tự Hangul nào sót lại trong cột dịch (quét bằng regex `[가-힣]`).
- Số lượng từng loại mã điều khiển (`;` `{` `}` `` ` `` `^` `<` `|` `&` `#`, `%d`, `%s`, `#số`) khớp 100% giữa gốc và dịch cho từng dòng — không lệch dòng nào.
- Không dùng ký tự cấm (`'` `"` `` ` `` khi không phải mã màu, `[` `$` `&` khi không phải mã, `=` `_` `~`, ngoặc kép cong, dấu `…`) trong nội dung dịch tự viết — riêng ký tự `=` ở cuối K00178 là mã/ký hiệu có sẵn trong bản gốc, được giữ nguyên y hệt (không phải do dịch thêm vào).
- Khoảng trắng đầu/cuối dòng: khớp với bản gốc cho toàn bộ 776 dòng (đặc biệt nhóm tên quái/tài nguyên K00701–K00726 có khoảng trắng cuối trong file gốc, đã giữ nguyên).
- Giới hạn byte (GioiHanByte): tính theo đúng công thức trong HUONG_DAN_CHUNG.md (1 TỪ tiếng Việt = 2 byte, dấu cách/số/dấu câu = 1 byte) — không có dòng nào vượt giới hạn. (Nếu tính byte theo UTF-8 thô thì 6 dòng mô tả lớp nhân vật K00021–K00024 và 2 dòng thông báo pháp lý K00172, K00175 sẽ vượt 255 byte, nhưng đây là văn bản mô tả dài vốn đã ghi RongPx rất lớn — 900–1100px — nên không phải vấn đề thực tế theo công thức chính thức của dự án.)

## 2. Thuật ngữ/tên riêng mới tự đặt — đề xuất gộp vào thuat-ngu.tsv chung
| Gốc Hàn | Đề xuất dịch | Loại | Ghi chú |
|---|---|---|---|
| 조합 | chế biến | hệ thống | Hệ thống chế tạo dùng "조합서" (Sách Chế Biến) + nguyên liệu, KHÁC với 합성 (hợp thành, đã chốt) và 제작 (chế tạo, đã chốt nhưng không xuất hiện trong lô này). |
| 제련 | tôi luyện | hệ thống | Hệ thống dùng đá tôi luyện để tăng "제련도" (độ tôi luyện) cho trang bị, khác 강화 (cường hoá). |
| 결합 | kết hợp | hệ thống | Hệ thống gắn Orb vào socket trang bị bằng "제물" (vật hiến tế). |
| 소켓 | socket | hệ thống | Giữ nguyên loanword "socket" (đặt lại/mở rộng socket). |
| 스킬북 | Sách Kỹ Năng | vật phẩm | |
| 조합서 | Sách Chế Biến | vật phẩm | |
| 노멀/레어/에픽 | thường/hiếm/sử thi | item rarity | Đây là MỘT bậc phẩm chất khác với bậc 일반/고급/희귀/영웅/전설/신화/세트 đã có trong thuat-ngu.tsv (dùng chung 3 mức anh hùng/huyền thoại/bộ). Game có vẻ dùng 2 hệ thống tên rarity riêng — cần người dùng xác nhận có phải cùng 1 thang bậc hay không trước khi hợp nhất bảng thuật ngữ toàn game. |
| 고대종 | Cổ Chủng | khái niệm | Khác với 고대신 (Cổ Thần) đã chốt — "Cổ Chủng" là chủng loài cổ đại (quái vật/thực thể), không phải vị thần. |
| 강림자 | Kẻ Giáng Lâm | khái niệm/phe | Có vẻ là hoá thân/tay sai của Cổ Thần (Con Tàu/Di Tích/Giáo Đoàn/Bàn Tay của Kẻ Giáng Lâm). Cần đối chiếu với các lô hội thoại sau để xác nhận danh tính. |
| 수호자 | Hộ Vệ | khái niệm/phe | Dùng lại từ "hộ vệ" đã mô tả vai trò của Lurain trong phan-tich-vai.md. |
| 반군 | Quân Nổi Dậy | phe | Tách biệt với "Quân Cách Mạng" (혁명군) đã chốt — có thể là phe đối lập hoặc tên gọi khác của cùng phe, cần xác nhận. |
| 크리티컬 구 | Cầu Chí Mạng | khái niệm | Tài nguyên tích luỹ để tung chiêu kết liễu (Rogue/Knight). |
| 사신 (rune) / 영제 / 속박 / 결의 / 투신 / 공명 / 현자 / 마도 / 폭풍 / 뇌제 / 금강 | Tử Thần / Linh Đế / Trói Buộc / Quyết Ý / Đấu Thần / Cộng Hưởng / Hiền Giả / Ma Đạo / Bão Tố / Lôi Đế / Kim Cang | tên rune/ngọc | Tên các bộ ngọc gắn theo chỉ số (K00386–K00398). Đặt theo phong cách Hán Việt ngắn gọn, cần người dùng duyệt lại nếu game có tên chính thức khác. |

### Tên riêng nhân vật/địa danh mới
- Địa danh: Central Tower, Bernal, Rockville, Ernest (Dinh Thự Ernest), Levan, Moytura, Irem, Trident (Đảo Trident), Coldcreek, Kingsport, Meadowhill, Ridmeyer (Nhà Ridmeyer), Silverhill (đã có trong thuat-ngu, dùng lại).
- Nhân vật/NPC mới xuất hiện trong lô này chưa có trong phan-tich-vai.md/thuat-ngu.tsv: **Kaz (카즈)**, **Alex/알렉스**, **Altena/알테나** — cần bổ sung vai trò vào phan-tich-vai.md khi có thêm hội thoại.
- **테오도어** (K00537) — biến thể chính tả của 테오도로 (Theodoro) đã chốt trong phan-tich-vai.md. Đã dịch thống nhất là "Theodoro"; cần xác nhận đây là cùng 1 nhân vật (rất có thể do lỗi gõ trong file gốc).

## 3. Câu phải rút ngắn mạnh / có nguy cơ vượt khung nút nhỏ (RongPx nhỏ)
Do đặc thù tiếng Việt có dấu, không thể nén nhiều nghĩa vào ít ký tự như tiếng Hàn (1 âm tiết Hàn ≈ 1.5 chữ cái Việt, nhưng nhiều thuật ngữ tiếng Việt cần trọn từ 4–10 ký tự). Với các nút bấm/nhãn có RongPx gốc rất nhỏ (≤30px, tức 1–2 âm tiết Hàn) và bản dịch đã là cách viết ngắn nhất có thể theo thuat-ngu.tsv/quy ước chung, các dòng sau nhiều khả năng dài hơn khung gốc và nên được người dùng cân nhắc dùng cỡ chữ nhỏ hơn hoặc chấp nhận tràn nhẹ:

K00075 (Thương), K00082 (Vật phẩm tiêu hao), K00084 (Nguyên liệu), K00091 (huyền thoại), K00108 (Thời gian hồi), K00162 (Trăm nghìn), K00164 (Nghìn), K00197 (Tinh Linh), K00202 (Nhanh nhẹn), K00204 (Tinh thần), K00232 (Phân giải), K00246 (Phòng Thủ), K00252 (Xảo Thuật), K00253 (Xung Phong), K00254 (Rèn Luyện), K00276 (Tỉ lệ thành công), K00285 (Đại thành công), K00286 (Thành công), K00316 (Hợp thành), K00328 (Sức mạnh phép), K00366 (Âm thanh hiệu ứng), K00378/K00379 (mã màu rarity `anh hùng`/`huyền thoại`), K00383 (Giáp nặng), K00457 (Trung), K00477 (Rockville), K00481 (Đường Thét Gào), K00508 (Thánh Địa), K00515 (Thám Hiểm), K00518 (Tình Hình), K00530 (Trốn Thoát), K00543 (Nghỉ Ngơi), K00566 (Phun Trào), K00594 (Đang tiến hành), K00597 (Phần thưởng), K00618 (Ong Bắp Cày), K00638 (Người Biển Sâu), K00734 (Chiến thắng!).

Phần lớn các mục này đã dùng đúng thuật ngữ chốt trong thuat-ngu.tsv (VD "Tinh Linh", "Thánh Địa") nên không thể rút ngắn thêm mà không đổi nghĩa hoặc phá vỡ tính nhất quán thuật ngữ toàn game.

Ngoài danh sách trên, khoảng 500/776 dòng khác cũng dài hơn RongPx gốc ở mức vừa phải (do bản chất tiếng Việt dài hơn chữ Hán/Hàn 1.5–3 lần) — đây là hiện tượng bình thường với văn bản UI dài (câu thoại/thông báo nhiều dòng dùng `;` để xuống dòng thủ công), không liệt kê hết ở đây để tránh nhiễu; chỉ các nhãn/nút cực ngắn ở trên mới thực sự đáng lo về việc tràn khung hiển thị.

## 4. Điểm mơ hồ / cần người dùng xác nhận
- **K00239 `(이)가`, K00242 `가 생성되었습니다.`, K00315 `을(를) 배웠습니다`, K00435 `을(를) 사용했습니다.`, K00591 `개 획득`, K00607 `님 반갑습니다.`, K00730 `을(를) 구매하였습니다.`, K00732/K00733 `에서;승리/실패 하였습니다.`, K00744 `(으)로;별명을 만들었습니다.`, K00755 `위`** — đây là các tiểu từ ngữ pháp tiếng Hàn (trợ từ chủ ngữ/tân ngữ, hậu tố xưng hô, đơn vị đếm) được ghép vào TÊN VẬT PHẨM/TÊN NGƯỜI/SỐ do engine nối chuỗi lúc chạy. Tiếng Việt không có trợ từ tương đương và có thứ tự câu khác (SVO thay vì SOV), nên các dòng này CHỈ dịch được phần vị ngữ/hậu tố còn lại (VD "đã học được", "đã được tạo ra", ", rất vui được gặp bạn.") — trợ từ thuần ngữ pháp K00239 được để trống. Câu ghép cuối cùng khi hiển thị trong game có thể hơi khác thứ tự tự nhiên tiếng Việt (VD "[Tên kỹ năng] đã học được." thay vì "Đã học được [Tên kỹ năng]."); nếu engine cho phép chỉnh thứ tự ghép chuỗi, nên báo lại để tối ưu ngữ pháp.
- **K00186 `껨존가기`** — chính tả gốc lệch chuẩn ("껨" thay vì "겜/게임"), có thể là lỗi gõ cố ý hoặc lỗi OCR trong file gốc. Đã hiểu là "vào Game Zone" (dịch "Vào Game Zone"), cần xác nhận đây đúng là tên tính năng "Game Zone" của nhà mạng.
- **K00189 `오즈 무한 자유`** — tên gói cước dữ liệu của nhà mạng Hàn Quốc (KT "OZ"), giữ phiên âm "OZ Vô Hạn Tự Do"; vì đây là nội dung quảng cáo/pháp lý đặc thù thị trường Hàn, có thể không còn liên quan khi phát hành bản Việt hoá — đề xuất người dùng cân nhắc bỏ hẳn dòng này hoặc thay bằng nội dung trung tính hơn.
- **K00446 `제  명`, K00452 `상  호`** — nhãn 2 chữ có khoảng trắng kép ở giữa (định dạng bảng chứng nhận phân loại game kiểu Hàn Quốc, tương tự "商號"/"題名"). Đã dịch nghĩa thẳng là "Tên game" / "Tên công ty" mà không giữ khoảng trắng kép (vì đây là format hiển thị đặc thù tiếng Hàn không áp dụng cho tiếng Việt).
- **K00415, K00417, K00467 (đơn vị "원"/won)** — đây là các dòng liên quan đến phí thực (Korean Won) và cước viễn thông Hàn Quốc (LGU+, KT...) — giữ nguyên đơn vị "won" và các số điện thoại/tên nhà mạng Hàn (02-559-8787, LGU+, Electronic Arts Korea) vì đây là nội dung pháp lý/liên hệ gốc của nhà phát hành, không tự quy đổi VNĐ. Toàn bộ khối K00168–K00190, K00459–K00467 là các thông báo về cước viễn thông/roaming/SMS đặc thù thị trường Hàn Quốc — nhiều khả năng KHÔNG áp dụng cho bản Việt hoá chạy ngoài mạng Hàn Quốc; đề xuất người dùng xem lại có cần giữ nguyên nội dung này không hay nên thay bằng thông báo trung tính/vô hiệu hoá.
- **K00668 `강화 시작형`** — tên loại quái vật mơ hồ, nghĩa đen "dạng khởi đầu (được) cường hoá", chưa rõ ý nghĩa chính xác trong game (có thể là loại quái spawn giai đoạn đầu của một cơ chế cường hoá/biến hình). Tạm dịch "Loại Khởi Đầu Cường Hoá" — cần đối chiếu ngữ cảnh xuất hiện thực tế để đặt tên chính xác hơn.
- **K00717 `조합용병`** — dịch tạm "Lính Đánh Thuê Công Đoàn" dựa trên suy đoán "조합" ở đây nghĩa là công đoàn/nghiệp đoàn (liên hệ "공단원"/đoàn viên đã có trong phan-tich-vai.md), KHÔNG dùng nghĩa "chế biến" như các chỗ khác — cần xác nhận lại khi có thêm ngữ cảnh (đây có thể là lính canh gần khu vực chế tạo/thợ rèn).
- **K00376–K00380, K00087–K00092 (rarity: thường/hiếm/sử thi/anh hùng/huyền thoại/bộ)** — xem mục 2, cần xác nhận có phải cùng thang bậc với 일반/고급/희귀/영웅/전설/신화/세트 đã chốt trong thuat-ngu.tsv hay là 2 hệ thống rarity riêng biệt trong game.
- Toàn bộ 190 dòng nhóm "Giao diện - menu" hầu như là thông báo hệ thống/pháp lý/lưu dữ liệu, gần như không có hội thoại nhân vật nên không cần áp ma trận xưng hô trong phan-tich-vai.md; 4 dòng mô tả lớp nhân vật (K00021–K00024) và các dòng cốt truyện mở đầu (K00016–K00020) là văn xuôi trung tính (narrator), không gán cho nhân vật cụ thể nào.

## 5. Danh sách KHÔNG DỊCH (giữ nguyên)
- Số điện thoại, URL, email: `02-559-8787`, `www.eamobile.co.kr`, `support@eamobile.co.kr`.
- Tên hãng/thương hiệu: `Electronic Arts Korea (Ltd.)`, `LGU+`.
- Ngày tháng cụ thể: chuyển "2011년 1월 21일" → "21/01/2011" (định dạng ngày, không phải dịch nghĩa).
- Đơn vị tiền thật: "won" (giữ nguyên, không quy đổi).
- Ký hiệu `=` cuối K00178 (có sẵn trong bản gốc, giữ nguyên).
