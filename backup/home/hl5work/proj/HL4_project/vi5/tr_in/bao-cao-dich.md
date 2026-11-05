# Báo cáo dịch HL5 — HOÀN THÀNH 100%

**Tiến độ:** 10.131 / 10.133 dòng có bản dịch. 2 dòng để trống có chủ đích:
- **K00239** `(이)가`: trợ từ tiếng Hàn, không có từ tương đương trong tiếng Việt. Nên bỏ trợ từ này ở tầng code.
- **K07780**: ghi chú của dev (`EVENT_일반대사…`), không phải chữ hiện trong game.

Phiên này dịch thêm 7.355 dòng: Kỹ năng 366, Rèn/chế tạo 215, Cửa hàng cash 41, Trong binary 10, Thành tựu 202, Nhiệm vụ 163, Quái vật 79, Hướng dẫn 36, Cốt truyện 6.244.

## Rà soát tự động trên cả 10.133 dòng (kết quả cuối)
- ID và thứ tự dòng khớp 100% với file gốc.
- 0 dòng lệch mã `{ } ` ^ < > | & #nn %d %s` về tập và số lượng. Đã sửa 7 dòng: K03113, K03222, K03277, K03364, K06352, K06396, K06496.
- 0 dòng còn ký tự Hàn. Riêng K03878 đã bỏ `(유)` sau tên công ty.
- 0 dòng lệch khoảng trắng đầu/cuối. Đã sửa 6 dòng bị mất dấu cách cuối.
- 0 dòng vượt giới hạn byte, tính theo công thức của dự án (1 từ = 2 byte, dấu cách/số/dấu câu = 1 byte).
- 0 ký tự cấm. Đã xoá dấu `"` do người dịch tự thêm ở 64 dòng hội thoại.
- **Cần Nam kiểm tra:** 79 dòng Kỹ năng/Đặc tính (K03035–K03399) có **số dấu `;` khác bản gốc**. Tức là số dòng xuống hàng trong ô mô tả đã thay đổi, vì câu tiếng Việt dài hơn nên phải ngắt lại. Mã màu vẫn đúng. Nếu ô mô tả kỹ năng có chiều cao cố định, cần xem trên FreeJ2ME có bị tràn không.

## Chuẩn hoá thuật ngữ sau khi gộp (các lô dịch song song dùng cách gọi khác nhau)
Đã thay hàng loạt về một cách gọi:
- lính Áo Đen (bỏ "Hắc Y Binh")
- Tiên Chủng (bỏ "Tiên Trú Chủng")
- Điện Giáo Lý / Điện Khởi Nguyên (bỏ "quan tài…")
- Tuyết Nữ
- Đá Linh Hồn / Bột Linh Hồn
- nhà Wade
- Biển Băng
- Nghị Hội
- Hiệp Hội Mỏ / Công Đoàn Mỏ
- Thánh Địa Chiến
- Chúa Tể Ác Mộng
- Schu (tên thân mật của Schurtz, bỏ "Shu")

Bảng `thuat-ngu.tsv` đã tăng từ 154 lên 208 mục.

## Cần Nam xác nhận
1. **사신**: tên quái vật K03631 dịch là "Tử Thần", còn rune 사신 đã chốt là "Sứ Thần". Hai chữ này đồng âm trong tiếng Hàn. Cần đối chiếu trong game.
2. **Tên vũ khí phiên âm tạm** (K03535–K03540): Lilis, Gimir, Ysir, Shopin, Grande, Dielun, Ferdin, Blinka, Alas.
3. **K02790–K02793** (cash_expert.dat): có vẻ là các mảnh của cùng một câu bị cắt ra, ghép lại không khớp. K02790 `i정령` tạm dịch "i Tinh Linh".
4. **Chơi chữ trong Thành tựu**: K03407, K03459, K03462, K03477–K03481 (김제련 tạm dịch "Kim Tôi Luyện"). Đã dịch sát nghĩa nên phần chơi chữ bị mất.
5. **Câu gốc bị cụt hoặc thiếu chữ**, đã dịch sát nhất có thể:
   - K09439, K09883, K09920
   - Các câu thoại đứt quãng của Tiana/Kris: K05578, K05591, K05601, K05841, K05854, K07205, K07391
6. **K04672** "부탁한다, 렌!!": nhân vật Ren ở đây có thể là người khác, không phải Ren nữ chính.
7. **Kỹ năng K03200–K03202**: mã `}#` nằm vắt qua 2 dòng dữ liệu. Cần kiểm tra khi ghép lại để patch.
8. **Một số đoạn chưa chắc ai là người nói** (dịch theo mức trung tính lịch sự):
   - K05052–K05067, K06280–K06290, K07039–K07054
   - K08309–K08324, K09391–K09398, K09736–K09764
9. **Xưng hô Schurtz ↔ Tir/Theodoro** chưa thống nhất hẳn giữa "tao" và "tôi", ví dụ K08524, K09353. Nên đọc lại khi test.

## Lưu ý cũ (vẫn còn hiệu lực)
- Các dòng trợ từ Hàn như K00239 cần xử lý ở tầng code.
- K01867 là chuỗi test font.
- Hệ độ hiếm thứ 2: thường / hiếm / sử thi.
- K00923 염도효과.
- Tên đồ phiên âm: xem ghi chú lô C1/C2.
- Còn một vài tên đồ ở lô 25% chưa khớp bảng thuật ngữ: 지크문드 = Sigmund/Siegmund, 폭군쟈칼. Nên chọn một cách viết.
