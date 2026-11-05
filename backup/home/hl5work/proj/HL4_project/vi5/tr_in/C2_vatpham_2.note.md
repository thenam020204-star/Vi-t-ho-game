# Ghi chú — C2_vatpham_2.tsv (915 dòng)

## 1. Thuật ngữ MỚI cần gộp vào `thuat-ngu.tsv` (quan trọng, có thể tái xuất hiện ở các batch khác)

### Tên lớp nhân vật (class) — rất có thể xuất hiện lại ở batch B (ten_lop_dactinh_bo) và các batch F (story)
| Hàn | Việt đã chọn | Ghi chú |
|---|---|---|
| 워리어 | Chiến Binh (Warrior) | |
| 로그 | Đạo Tặc (Rogue) | |
| 건슬링어 | Xạ Thủ (Gunslinger) | |
| 나이트 | Kỵ Sĩ (Knight) | |
| 클래스 | class (giữ nguyên tiếng Anh, ví dụ "đổi class") | |

**CẦN NAM XÁC NHẬN**: đây là 4 lớp nhân vật chính của game — tên dịch này do tôi tự chọn (chưa có trong `thuat-ngu.tsv`/`phan-tich-vai.md`). Batch B (`B_ten_lop_dactinh_bo.tsv`) là nơi tên lớp xuất hiện gốc — nếu agent xử lý batch B chọn tên khác, cần đồng bộ lại toàn bộ game.

### Tên kỹ năng (skill) theo lớp — dùng trong tên vật phẩm "Sách Kỹ Năng"
- Chiến Binh: 양손베기→Chém Hai Tay, 내려찍기→Bổ Xuống, 어깨치기→Đánh Vai, 올려베기→Chém Hất, 회전베기→Chém Xoay, 도발→Khiêu Khích, 찌르기→Đâm, 발도→Bạt Đao, 스톰프→Giẫm Đạp, 돌진→Xung Phong
- Đạo Tặc: 연속베기→Chém Liên Hoàn, 축지→Súc Địa, 절단→Cắt Đứt, 자르기→Chặt, 긴급회피→Né Khẩn Cấp, 속임수→Mưu Kế, 기절시키기→Gây Choáng, 투척→Ném, 고통→Gây Đau, 난도질→Băm Vằm
- Xạ Thủ: 연속사격→Bắn Liên Hoàn, 동시사격→Bắn Đồng Loạt, 샷건→Shotgun (giữ tiếng Anh), 화염방사→Phun Lửa, 충격포→Pháo Chấn Động, 자동사격→Bắn Tự Động, 트릭샷→Trick Shot (giữ tiếng Anh), 포대→Pháo Đài, 난사→Bắn Loạn, 포격→Pháo Kích
- Kỵ Sĩ: 창격술→Thương Kích Thuật, 난격→Loạn Kích, 릴리즈→Giải Phóng, 보복→Báo Thù, 액티브블록→Chặn Chủ Động, 끌어오기→Kéo Về, 방어분쇄→Phá Giáp, 집중공격→Tấn Công Tập Trung, 무장해제→Tước Vũ Khí, 거창→Cự Thương

Các tên kỹ năng này nhiều khả năng còn xuất hiện lại trong mô tả kỹ năng chi tiết ở các batch khác (D_cuahang_ren_kynang.tsv) — **cần dùng đúng các tên này khi dịch batch D** để nhất quán.

### Loại trang bị (equipment type)
양손검→kiếm hai tay, 한손검→kiếm một tay, 건→súng, 창→thương, 장갑→găng tay, 신발→giày, 갑옷→giáp, 투구→mũ giáp, 방패→khiên

### Tên vật phẩm/vũ khí/bộ đồ mới tự đặt (chỉ xuất hiện trong batch này, ít khả năng trùng batch khác nhưng liệt kê để tham khảo)
Phần lớn là tên phiên âm kiểu tên riêng phương Tây (Turangear, Kelabog, Soul Quaid, Bone Seeker, Final Buster, Demon Arms, Elven Wingspear, Claw Halberd, Main Combat Gauntlet/Shoes/Suit, Phantom Glove/Greave/Armor/Scouter, Main Gear, Demon Shield, Bone Protector, Vel Hand/Shoes/Suit/Eye, Kaiyan Gauntlet/Greave/Armor/Helm, Angel Wing Hand, Dragon Bone Hand, Phantom Breaker, Kilebard, Black Kite/Harpy, Owen Glaive, Shadow Lance, Liquid/Grand Defender, Divine Saber, Valkarian, Crimson Eye, Falcon Magnum, Hawk Arms, Shell Trident, Vanaheim, Titan Combat/War Gauntlet/Shoes/Armor/Helmet, Skuld Gauntlet/Greave/War Mail/War Helm, Wyatt Hat, Roc Helm, Ultimate Shield, Blidablik, Destroyer, Zebius, Ignis, Metalligrim, Warhead/Dragon Gauntlet, Veriel Gauntlet/Boots/Armor/Circlet, Balder Gauntlet/Greave/War Mail/War Helm, Frost Shield, Garunzeel Shield, Acheyaki Gauntlet, Gabidu Gauntlet/Boots/Coat/Helmet, Tir War Gauntlet/Shoes/Armor/Helmet, Helicion, Ascalon, Crabian, Soul Crow, Stranger, Agnar Spear, Rift Reave, Sigmund Helm, Unbreakable, Grandish Shield) và một số tên thuần Việt cho vũ khí mang tên mô tả trực tiếp (Đại Kiếm Sư Tử Đỏ, Rìu Long Phi Xanh, Kiếm Rune Cuồng Công, Kiếm Chúa Tể Hủy Diệt, Kiếm Ma Đạo Tàn Sát/Trấn Hồn, Chân Kiếm Ma Đạo Tàn Sát, Trường Thương Trảm Sát Mặt Trời, Găng Tay Phi Long Xanh, Mũ Sài Lang Bạo Quân).
Đây đều là sách công thức chế tạo trang bị `영웅`(anh hùng)/`세트`(bộ) cấp 21-99, xuất hiện thành CẶP (tên vật phẩm + mô tả sách). Nếu các batch khác (D — cửa hàng/rèn, hoặc F — story) nhắc lại đúng các tên trang bị này thì PHẢI dùng lại y hệt bản dịch trên.

### Tên riêng địa danh/vật liệu mới (chưa có trong `thuat-ngu.tsv`)
- 센트럴 → Central (khu vực/thành phố, xuất hiện nhiều lần: "Toà Tháp Central", "phố số 9 Central")
- 모이투라 → Moytura (tên đảo, K02035)
- 론베르크 → Lonberg (tên gia tộc rèn vũ khí, K02079/K02083)
- 살리실리아 → Salicylia (tên nguyên liệu hoá học phiên âm, K02019)
- 아미노페나 → Aminophena (tên nguyên liệu hoá học phiên âm, K02025)
- 선주종 → dịch là "giống dân bản địa" (K02067, không phải tên riêng, là danh từ chung chỉ chủng tộc bản địa)

## 2. Điểm mơ hồ / cần Nam xác nhận
- **K01875** `"여기까지 퀘스트 관련"` (RongPx 102) — có vẻ là **ghi chú nội bộ của dev** đánh dấu ranh giới "đến đây là phần liên quan nhiệm vụ" trong danh sách item, không rõ có hiển thị cho người chơi hay không. Tôi dịch nghĩa đen "Đến đây là phần liên quan nhiệm vụ" — nếu đây là item ẩn/dev-only thì có thể bỏ qua không cần chỉnh chu.
- **K01867** `"가나다라마바사아자차카타파하 가나다라마바사아자차카타파하"` (RongPx 286, rất rộng) — đây là **chuỗi test font/glyph** (liệt kê phụ âm tiếng Hàn 2 lần), không phải nội dung thật. Tôi đã thay bằng chuỗi test bảng chữ cái tiếng Việt tương ứng ("Aa Ăă Ââ Bb Cc Dd Đđ ... Yy") để giữ đúng mục đích test font. Cần Nam xác nhận cách xử lý này có phù hợp với quy trình patch font hay không (có thể việc test font nên do skill j2me-viet-hoa xử lý riêng, không cần dịch).
- **K02748, K02755, K02756**: "보유제한/보유한도: 3개" dịch là "Giới hạn sở hữu: 3 cái" — từ "개/cái" ở đây chỉ đơn vị đếm vật phẩm chung, không phải chỉ số game.
- **테어도어** (K01990) là biến thể chính tả của 테오도로/테오도어 (Theodoro) — đã dịch thống nhất là "Theodoro" theo đúng `thuat-ngu.tsv` (dòng 104 đã ghi nhận biến thể này).
- Rarity "노멀" (K02109-K02124, nhãn thể loại sách công thức cấp thường) dịch là "thường", theo đúng mục đã có sẵn (nhưng còn đang **CẦN NAM XÁC NHẬN**) trong `thuat-ngu.tsv` dòng 97-99 cho hệ rarity thứ 2 (노멀/레어/에픽).

## 3. Độ dài / RongPx
- Nhìn chung, phần lớn tên vật phẩm ngắn (RongPx 20-60px, tương ứng 2-6 âm tiết Hàn) khi dịch sang tiếng Việt đều DÀI HƠN đáng kể vì tên vật phẩm tiếng Việt thường cần ghép 2-3 từ có dấu (VD: 황혼버섯 (4 âm tiết, 40px) → "Nấm Hoàng Hôn" (13 ký tự)). Đây là đặc điểm ngôn ngữ khó tránh khi dịch danh từ ghép Hán/Hàn sang Việt có dấu — đã cố gắng dùng từ ngắn gọn nhất có thể nhưng vẫn vượt ước lượng RongPx gốc ở khoảng 600+/915 dòng (ước lượng thô ~6,5px/ký tự). Đề nghị Nam kiểm tra thực tế trên máy ảo J2ME xem UI có bị tràn/cắt chữ hay tự động thu nhỏ font không, trước khi quyết định có cần rút ngắn thêm (viết tắt mạnh hơn) hay không.
- Không có dòng nào vượt giới hạn byte (`GioiHanByte`) theo kiểm tra tự động.
- K01942 `"강화병에 추가된 장갑판. "` có khoảng trắng cuối dòng trong bản gốc — đã giữ nguyên khoảng trắng cuối trong bản dịch.

## 4. Kết quả kiểm tra tự động (build_and_check_C2.py)
- (a) Đủ 915 ID, khớp 100% thứ tự với file gốc — ĐẠT.
- (b) Không còn Hangul sót trong cột dịch — ĐẠT.
- (c) Số lượng mã điều khiển (`;`, `{`, `}`, `` ` ``, `^`, `<`, `|`, `&`, `%d`, `%s`, `#nn`) từng dòng khớp gốc-dịch — ĐẠT (915/915 dòng).
- Không dùng ký tự cấm (`'`, `"`, `` ` `` dùng đúng vai trò mã màu, `$`, `=`, `_`, `~`, `…`) trong nội dung dịch (ký tự `~` trong nguyên liệu K02715 đã đổi thành `-`).
- Khoảng trắng đầu/cuối dòng khớp gốc-dịch — ĐẠT.
