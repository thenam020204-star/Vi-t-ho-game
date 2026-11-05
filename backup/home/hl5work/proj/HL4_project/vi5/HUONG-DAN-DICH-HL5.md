# Hướng dẫn dịch Heroes Lore 5 (영웅서기5 – 칠흑의 계약)

## File dịch
- `HL5_dich.xlsx` (hoặc `HL5_dich.tsv`, cùng nội dung): 10.133 câu tiếng Hàn **không trùng lặp**. Một câu xuất hiện ở nhiều chỗ chỉ dịch một lần (cột "Số lần").
- **Chỉ điền cột "Tiếng Việt"**. Không sửa cột ID và "Tiếng Hàn" (máy dùng câu tiếng Hàn để tìm chỗ thay).
- Ô để trống thì trong game giữ nguyên tiếng Hàn. Vì vậy có thể dịch dần rồi gửi lại từng phần (xlsx hoặc tsv, một hay nhiều file đều được).
- Thứ tự nhóm: giao diện → vật phẩm → cửa hàng → rèn → kỹ năng → thành tựu → quái → nhiệm vụ → hướng dẫn → **cốt truyện** (theo số cảnh `00000.scn`, `00001.scn`… đúng thứ tự trong game) → chuỗi trong binary.
- Nên dịch nhóm "Giao diện" trước: đó là chữ thấy nhiều nhất.

## Mã điều khiển: giữ nguyên, đúng số lượng
| Mã | Ý nghĩa |
|---|---|
| `;` | xuống dòng (game **không** tự ngắt dòng ở phần lớn hộp thoại) |
| `&` `\|` `$` `{` `}` `'` `"` `` ` `` `^` `<` `[` | đổi màu chữ (ví dụ `}살리실리아{` = tô màu tên vật phẩm; `\|` = về màu thường) |
| `#05`, `#1`… | số do game điền vào (ví dụ `}#05%\|` = "x%") |
| `%d`, `%s` | số/chữ do game điền vào |

- **Không dùng** dấu nháy đơn `'`, nháy kép `"`, `` ` ``, `^`, `<`, `[`, `{`, `}`, `$`, `&`, `|` trong câu dịch (game hiểu là mã đổi màu). Nếu cần trích dẫn thì dùng dấu gạch `-` hoặc bỏ.
- Font không có các ký tự `=` `_` `~` và ngoặc kép cong. Dấu `…` sẽ tự đổi thành `...`.
- Lúc build, máy báo câu nào lệch số lượng mã điều khiển.

## Độ dài dòng
- Cột "Rộng dòng gốc (px)" là bề ngang dòng dài nhất của câu gốc. Mỗi dòng tiếng Việt (giữa hai dấu `;`) không nên dài hơn con số này quá nhiều, nếu không chữ tràn khỏi khung.
- Ước lượng: 1 chữ cái ≈ 6–7 px, dấu cách/số/dấu câu = 6 px, 1 âm tiết Hàn = 10 px. Tức là **1 âm tiết Hàn ≈ 1,5 chữ cái Việt**.
- Nút ở menu chính và tên ô chọn chỉ rộng khoảng 45 px (6–7 chữ cái): dịch càng ngắn càng tốt (ví dụ "Bắt đầu", "Cài đặt", "Thoát").
- Câu dài: thêm `;` để xuống dòng. Hộp thoại cốt truyện thường có 3–4 dòng.
- Lúc build, máy liệt kê những dòng dài hơn bản gốc để bạn sửa.

## Giới hạn kỹ thuật
- Mỗi câu tối đa 255 byte (hội thoại 254). Mỗi **từ** tính 2 byte; dấu cách, số, dấu câu tính 1 byte. Thực tế câu dưới khoảng 80 từ là an toàn.
- Toàn bộ bản dịch được dùng tối đa **5.076 từ khác nhau** (viết hoa và viết thường tính là hai từ). Nên viết hoa thống nhất, tránh viết hoa cả câu.
- Tên riêng (nhân vật, địa danh) nên dùng thống nhất một cách viết. Danh sách tên nhân vật nằm ở nhóm "Tên nhân vật".

## Phần không dịch được bằng file này
Chữ nằm trong **ảnh** (logo 영웅서기V, biểu tượng 12세이용가, một số nút đồ hoạ) cần sửa ảnh riêng.

## Gửi lại
Gửi file xlsx/tsv đã dịch (có thể chỉ một phần). Mình sẽ build jar Việt hoá, gửi kèm báo cáo các câu quá dài, lệch mã hoặc tràn dòng.
