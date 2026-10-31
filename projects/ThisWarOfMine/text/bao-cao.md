# Báo cáo Việt hoá — This War of Mine (Android)

## Kết quả
- Dịch tay 7770 dòng (T1 6874 dòng / 6612 chuỗi duy nhất — Sonnet; T2 896 dòng / 864 chuỗi — Haiku), giữ nguyên 220 dòng (`Names/*` + rỗng). Tổng 7990 bản ghi.
- Text tiếng Việt ghi vào slot **English** (slot 1) của khối `2f6496cc`; nhãn ngôn ngữ English đổi thành "Tiếng Việt".
- Font (mặc định từ nay `FONT_MODE=pixel`): 137 font Latin thay bằng font pixel NAM.P `fonts/namp/f18.bin` (18px có chân). Tỉ lệ s = min(chữ hoa gốc/10, độ rộng a–z gốc/f18): s<0.8 → f13, <0.95 → f15, <1.3 → f18 nguyên pixel; ≥1.3 → vẽ lại bằng nét (nối tâm ô pixel, nét chéo liền, chân chữ vuông, độ đậm s·min(1.2, 1+0.07(s−1)), khử răng cưa). Vẽ tay thêm: Ł ł Ą ą Ę ę Ń ń Ś ś Ź ź Ż ż – “ ” ’ © ® = _ ` ~ (f13/f15 ánh xạ hàng theo vùng). Ký tự không có trong bộ pixel giữ glyph gốc.
- Font cũ (`FONT_MODE=times`): 137 font Latin thay bằng Times New Roman (giữ cỡ: min(khớp chữ hoa, khớp độ rộng a–z), giữ baseline, chiều cao dòng, bpp; font 2bpp dựng lại viền). Font CJK/Hàn giữ nguyên.
- Build: `sh build.sh <gốc> <ra>` → `VERIFY OK: khối giữ nguyên 191, khối thay 138`.
- SHA1 bản pixel 18px: xem commit; bản Times: common.dat `93de535dfec7a6ae97fc8290026f906d406aa6bb`, common.idx `2ec6b6c0c21794d756c2d3afe7ee74286e6612bb`.

## Kiểm tra đã làm
- `dp check` T1/T2: mã điều khiển, `\n`, thẻ giới tính, glyph.
- Giải nén lại khối text từ bản đóng gói, so với gốc: 7990 bản ghi; chỉ slot English đổi (7689 bản ghi); 13 slot ngôn ngữ khác, 4 chuỗi ASCII, 5+9 byte đệm **giống hệt gốc**.
- Cân bằng thẻ `{..}` và `^..^`: 1 dòng lệch `^` (`.../ChildOnly/Anger/SPECTATOR_LOG/Egoist/00`) — **lỗi có sẵn trong bản gốc tiếng Anh**, giữ nguyên.
- Các khối không đổi byte-identical; font mới roundtrip và đủ glyph tiếng Việt.

## Xưng hô trẻ em (yêu cầu người dùng)
- Lời trẻ: "cháu" với người lớn, "con" với bố/mẹ, "tớ–cậu" giữa trẻ với nhau; người lớn nói với trẻ: chú/cô – cháu.
- Quản đốc sửa 37 dòng (lời trẻ trong `.../Kids/`, NeedMeds 6359–6369 là trẻ nói theo nội dung "mom/daddy/tummies").
- A1 làm lại 85 chuỗi KidComfort/Kids_* có **thêm thẻ giới tính** mà gốc không có. Quy ước xác định từ dữ liệu gốc (Kids_Play/SearchAndFound): trong `Dialogues/`, `{mr|}{fr|}` = giới người nghe, `{ms|}{fs|}` = giới người nói. 96 dòng `dp check` báo "thừa thẻ" là chủ ý; đã kiểm riêng: mã khác khớp, thẻ đủ cặp, chỉ nằm trong `Dialogues/`.

## Rủi ro / chưa kiểm được
- ⚠️ Chưa chạy trên máy thật: cần xác nhận game xử lý thẻ giới tính mới thêm (nếu không, sẽ hiện nguyên văn `{ms|Chú}{fs|Cô}`).
- Vài cuộc thoại trẻ còn mơ hồ vai (Kids_Grieving 01/02, Dweller_Weak, ParentDied) — xem `dp_work/ghi-chu.txt`.
- Font Times trên các font gốc hẹp (Oswald…) sẽ thấp hơn bản gốc vì giới hạn độ rộng.
- Một số dòng gốc là tiếng Ba Lan lẫn trong slot English: đã dịch nghĩa sang Việt.
