# Ghi chú dịch — batches/B_ten_lop_dactinh_bo.tsv (171 dòng, K00777–K00947)

## 1. Kết quả kiểm tra tự động (script Python, csv module — build_and_check_B.py)
- Số dòng file gốc: 171; số dòng bản dịch: 171 — khớp 100% theo ID, đúng thứ tự, không thiếu/thừa/trùng.
- Không còn ký tự Hangul nào sót lại trong cột dịch (quét bằng regex `[가-힣]`).
- Số lượng từng loại mã điều khiển (`;` `{` `}` `` ` `` `^` `<` `|` `&` `#`, `%d`, `%s`, `#số`) khớp 100% giữa gốc và dịch cho từng dòng (nhóm Đặc tính có nhiều dòng dùng `#1` làm placeholder số — tất cả đã giữ đúng vị trí/số lượng).
- Không dùng ký tự cấm (`'` `"` `` ` `` `$` `=` `_` `~`, ngoặc kép cong, dấu `…`) trong nội dung dịch.
- Khoảng trắng đầu/cuối dòng: khớp 100% với bản gốc.
- Không có dòng nào cần đánh dấu KHÔNG DỊCH trong lô này (toàn bộ 171 dòng là tên/nhãn hiển thị cần dịch).

## 2. Thuật ngữ/tên riêng mới — đề xuất gộp vào thuat-ngu.tsv chung

### Lớp nhân vật (class.dat)
- **소서러 (K00825) → "Sorcerer"** — giữ nguyên tiếng Anh, theo đúng tiền lệ đã chốt ở lô A_giaodien_meo cho các lớp nhân vật khác: 워리어→"Warrior", 로그→"Rogue", 건슬링어→"Gunslinger", 나이트→"Knight" (xem K00344–346, K00085–086 trong outputs/A_giaodien_meo.out.tsv). Đề nghị Nam xác nhận vì đây là lớp thứ 5 chưa từng xuất hiện ở lô trước.
- K00918–K00921 (hiệu ứng đặc tính gắn với lớp nhân vật) cũng dùng lại đúng tên tiếng Anh này: "Warrior" (K00918), "Rogue" (K00919, K00920), "Gunslinger" (K00921 — bản gốc viết tắt "건슬" thay vì "건슬링어" đầy đủ, đã hiểu là cùng lớp Gunslinger).

### Rune/ngọc bổ trợ (buildup.dat, K00826–K00836)
Đây là cùng bộ tên rune đã xuất hiện và được đặt tên ở lô trước (xem mục thuật ngữ mới trong outputs/A_giaodien_meo.note.md, mục 2, dòng "사신/영제/속박/결의/투신/공명/현자/마도/폭풍/뇌제/금강"). Đã tái dùng đúng: 뇌제→Lôi Đế, 금강→Kim Cang, 영제→Linh Đế, 속박→Trói Buộc, 결의→Quyết Ý, 투신→Đấu Thần, 공명→Cộng Hưởng, 현자→Hiền Giả, 마도→Ma Đạo, 폭풍→Bão Tố.
- **흡혈 (K00833) → "Hấp Huyết"** — tên rune mới, chưa xuất hiện ở lô trước, đặt theo phong cách Hán Việt ngắn gọn cùng bộ (hút máu/lifesteal). Cần Nam duyệt.

### Bộ trang bị (set_option.dat, K00934–K00947)
Nhóm này là tiền tố tên Bộ sẽ được ghép vào rất nhiều tên vật phẩm giáp trụ ở batch C1/C2 (item_05–08.dat: vd "가시나무투구"=mũ Bộ Gai Nhọn, "드레이크헬름"=mũ Bộ Drake...). Đã kiểm tra chéo với batches/C1_vatpham_1.tsv và C2_vatpham_2.tsv để đảm bảo cách đặt tên ở đây dùng nhất quán cho các batch dịch item sau này:
- Tên là từ mượn tiếng Anh viết bằng Hangul (không phải từ tiếng Hàn thuần) → **giữ nguyên tiếng Anh**, đồng nhất với tiền lệ giữ "Warrior/Rogue/Gunslinger/Knight": 드레이크→**Drake**, 나이트메어→**Nightmare**, 피닉스→**Phoenix**, 엔젤윙→**Angel Wing**, 드래곤본→**Dragonbone**, 와이어트→**Wyatt** (tên riêng), 워헤드→**Warhead**, 드래곤→**Dragon**, 지크문드→**Siegmund** (tên riêng), 로크세트→**Roc Set**.
- Tên là cụm từ Hàn/Hán-Hàn thuần (không phải phiên âm tiếng Anh) → dịch sang tiếng Việt: 가시나무 ("cây gai") → **Gai Nhọn**; 푸른비룡 ("rồng bay xanh") → **Phi Long Xanh**; 폭군쟈칼 ("bạo chúa + jackal") → **Dã Can Bạo Chúa** (dã can = Hán Việt của "chó rừng/jackal").
- **아체야키 (K00944) → "Acheyaki"** (phiên âm giữ nguyên) — KHÔNG rõ nguồn gốc từ này (không phải tiếng Anh/Hán-Hàn thông dụng nào Claude nhận ra được), tạm phiên âm La-tinh hoá theo cách đọc Hangul. Cần Nam xác nhận đây có phải tên riêng cố ý (nhân vật/khu vực/thương hiệu trong game) hay là lỗi chính tả của "아체야키" từ một từ khác.

**Quan trọng:** cách đặt tên Bộ trang bị ở trên là quyết định có tính lan toả — toàn bộ ~40 dòng vật phẩm giáp (mũ/áo/găng/giày) ở batches C1_vatpham_1.tsv và C2_vatpham_2.tsv dùng lại các tiền tố này (vd K01304 가시나무투구, K01305 드레이크헬름...). Khi dịch các batch đó cần dùng đúng các tên đã chốt ở đây để nhất quán toàn game.

## 3. Điểm mơ hồ / cần Nam xác nhận
- **K00822 공단원 → "Đoàn Viên"** — đã dùng đúng gợi ý có sẵn trong thuat-ngu.tsv, nhưng bản thân thuat-ngu.tsv cũng ghi chú "chưa rõ nghĩa 공단, giữ tạm dịch chung" — vẫn còn mơ hồ, cần xác nhận khi có thêm ngữ cảnh xuất hiện của NPC này.
- **K00923 염도효과 → "Hiệu Ứng Độ Mặn"** — dịch sát nghĩa đen của "염도" (độ mặn/salinity), nhưng đặt trong danh sách hiệu ứng chiến đấu (cùng nhóm với "Bão Băng", "Chế Áp", "Cầu Ánh Sáng"...) thì nghĩa "độ mặn" rất khó hiểu/không hợp ngữ cảnh RPG. Có khả năng đây là lỗi gõ/viết tắt trong dữ liệu gốc của game (có thể nhầm với "염력" = niệm lực/telekinesis, hoặc một từ ghép khác đã bị cắt). Đã dịch sát chữ theo đúng quy tắc "không đoán bừa", đề nghị Nam đối chiếu với hình ảnh/skill thực tế trong game để đặt tên chính xác hơn.
- **K00944 아체야키** — xem mục 2, tên chưa rõ nguồn gốc.
- **K00780 래비 → "Raby"** — thuat-ngu.tsv đã ghi chú sẵn "phiên âm chưa chắc chắn"; đã dùng đúng theo bảng, giữ nguyên vấn đề tồn đọng để Nam quyết định sau.

## 4. Dòng có nguy cơ vượt khung hiển thị (RongPx nhỏ so với tiếng Việt)
Nhóm "Tên nhân vật" phiếm chỉ (NPC nền) có RongPx gốc rất nhỏ (20–36px, tương ứng 1–2 âm tiết Hàn) nhưng thuật ngữ vai trò tiếng Việt tương ứng đã chốt sẵn trong phan-tich-vai.md dài hơn nhiều và không thể rút ngắn thêm mà không đổi nghĩa hoặc lệch với các lô khác đã/sẽ dùng cùng từ:
- K00799/800 (RongPx 20/26) → "Người Đàn Ông" / "Người Đàn Ông 2"
- K00801/802 (RongPx 20/26) → "Phụ Nữ" / "Phụ Nữ 2"
- K00811/812 (RongPx 30/36) → "Công Chức" / "Công Chức 2"
- K00816 (RongPx 20) → "Thuyền Trưởng"
- K00817 (RongPx 20) → "Thuỷ Thủ"
- K00818 (RongPx 30) → "Kẻ Lang Thang"

Đây là hiện tượng chung không thể tránh khi Việt hoá nhãn ngắn từ tiếng Hàn (đã ghi nhận tương tự ở lô A); đề xuất Nam cân nhắc cỡ chữ nhỏ hơn cho nhãn tên NPC nếu engine cho phép, thay vì đổi từ vựng.

## 5. Danh sách KHÔNG DỊCH
- Không có dòng nào trong lô này cần giữ nguyên dạng KHÔNG DỊCH — toàn bộ 171 ID đều là tên hiển thị (tên nhân vật/NPC, tên lớp, tên đặc tính, tên bộ trang bị) cần dịch sang tiếng Việt (hoặc giữ tên riêng/tên tiếng Anh theo quy ước ở mục 2).
