# Ghi chú — C1_vatpham_1 (915 dòng, nhóm Vật phẩm: item_00..item_13)

## 1. Cấu trúc dữ liệu nhận diện được
Đây là bảng vũ khí/trang bị/tiêu hao/orb, mỗi `item_XX.dat` là một **loại trang bị** cố định:
- item_00: kiếm/rìu 2 tay (Trường Kiếm, Đại Kiếm, Rìu, Rìu Chiến...)
- item_01: dao găm/kiếm một tay
- item_02: súng (Colt, Magnum, Shooter, Crow, Falcon, Weapon, Arms...)
- item_03: giáo/trường thương (kích, glaive, trident...)
- item_04: trượng phép (chỉ 2 dòng)
- item_05: đồ đội đầu (mũ trụ/mũ thép/vòng trán/trâm cài tóc...)
- item_06: áo giáp thân (áo khoác/áo choàng/giáp/giáp liền...)
- item_07: găng tay
- item_08: giày/ủng
- item_09: khiên
- item_10: phụ kiện (bùa/khuyên tai/nhẫn)
- item_11: bình thuốc & vật phẩm tiêu hao (có mô tả hồi HP kèm theo)
- item_12: socket/Orb (11 loại Orb cơ bản + biến thể "Hyper...1-3/2-4/3-5" khi kết hợp)
- item_13: nguyên liệu chế biến (chỉ 1 dòng: "살코기")

Đa số tên trang bị là ghép **[Tên riêng/hãng rèn]+[loại trang bị]** không có khoảng
trắng trong tiếng Hàn (vd `가그라드대검`=`가그라드`+`대검`). Tôi đã dựng 1 bảng
`PREFIX_MAP` dùng xuyên suốt batch cho các tên riêng lặp lại ở nhiều loại trang bị
(vd cùng là "Gagrad" dù xuất hiện ở kiếm/giáp/mũ/khiên/giày khác nhau), và bảng
suffix theo từng loại trang bị. Toàn bộ script `translate_C1.py` +
`build_and_check_C1.py` đã lưu trong thư mục gốc để đối chiếu/tái sử dụng cho các
lô vật phẩm sau (nhiều tên riêng chắc chắn sẽ lặp lại ở C2_vatpham_2).

## 2. Tên riêng mới tự đặt (đề xuất gộp vào thuat-ngu.tsv)
Phần lớn là tên rút từ thần thoại Bắc Âu (Norse) — game dùng rất nhiều tên thật
(Mimir, Nidhogg, Valkyrie, Frigg, Beelzebub, Cronos, Aesir, Vanir, Gungnir,
Gjallarhorn, Fenrir, Balder/Baldr, Cerberus, Ascalon...) nên tôi giữ nguyên
chính tả gốc thay vì phiên âm mới. Danh sách tên KHÔNG chắc chắn / tự đặt, cần
đội dịch xác nhận và dùng thống nhất về sau:
- Gagrad (가그라드), Geyrod (게이로드), Fulla (풀라), Gna (그나), Lerad (레라드),
  Secreta (세크레타), Rig (리그), Santoria (산토리아), Frico (프리코),
  Silgaras, Turangear, Kelabog, Barisada, Bateugan, Kravian, Crister,
  Killebard, Halbist, Isilgrad, Immotir, Metalligrim, Riftrecib
- Jezebel (제제벨), Minorin (미놀린), Kaiyan (카이얀), Satiras (사티라스),
  Bartholomew (바솔로뮤), Wyatt (와이어트), Heid (헤이드), Asik (아시크),
  Perse (페르세, có thể là Perseus rút gọn), Gabidu (가비두), Molark (몰라크),
  Beriel (베리엘), Gerd/Gerd(트) — 게르드 và 게르트 xuất hiện cả hai, tôi gộp
  chung thành "Gerd" (cần xác nhận có phải cùng 1 tên hay 2 tên khác nhau)
- "Perse" (K01351 페르세헬름) — nghi là viết tắt của "Perseus", giữ "Perse"
  cho ngắn, cần xác nhận.
- "미명의도끼" (K01014) — 미명 (未明, "chưa sáng rõ") mơ hồ, tạm dịch
  "Rìu Lúc Rạng Đông"; có thể ý gốc là "mờ ám/vô danh" thay vì "bình minh" — CẦN NAM XÁC NHẬN.
- "무극위상" (Vô Cực + 위상) — dịch tắt thành "Vô Cực" (bỏ nghĩa "vị thế/pha"
  của 위상 vì không đủ chỗ và không rõ nghĩa game), xuất hiện ở 4 trang bị
  cùng bộ (mũ/áo/giày/găng) — CẦN NAM XÁC NHẬN cách dịch đầy đủ hơn nếu có.
- Đá quý ở item_05 (헤어핀/서클릿): 청금석→"Lưu Ly" (lapis lazuli) và
  남옥→"Ngọc Lam" (sapphire) khá dễ nhầm nhau khi đọc nhanh — CẦN NAM XÁC NHẬN
  có muốn đổi tên rõ hơn không (vd "Đá Lưu Ly" / "Đá Ngọc Lam").
- 나이트코워즈 (K01033, item_01) — không khớp hậu tố nào đã biết trong nhóm dao
  găm, đoán là tên riêng loại dao tầng "Night", tạm dịch "Đoản Kiếm Night" —
  CẦN NAM XÁC NHẬN nghĩa thật của "코워즈".

## 3. Phát hiện đáng chú ý: bộ áo giáp item_06 là tên Stand trong JoJo's Bizarre Adventure
Các trang bị K01452–K01462 (nhóm "더 풀", "매지션즈레드", "실버 채리옷", "더 월드"...)
trùng khớp gần như chính xác với tên Stand trong JoJo's Bizarre Adventure
(The Fool, Magician's Red, Silver Chariot, The World, Wheel of Fortune...).
Tôi giữ nguyên tiếng Anh (như các vũ khí "Stormbringer", "Divine Saver" trước đó)
thay vì dịch nghĩa. Riêng "심판자" (nghĩa đen "kẻ phán xét") tôi dịch thành
"Judgement" để khớp mẫu hình đặt tên Stand — CẦN NAM XÁC NHẬN vì đây là suy luận,
không phải dịch trực tiếp. Lưu ý: dấu nháy đơn trong "Magician's Red" và
"Guardian's Shield" đã bị BỎ (thành "Magicians Red", "Guardians Shield") vì
dấu `'` nằm trong danh sách ký tự cấm dùng.

## 4. Xử lý mã điều khiển đặc biệt
- Các dòng orb "하이퍼X1~3/2~4/3~5" (K01784–K01861) dùng ký tự `~` trong tên gốc.
  Ký tự `~` nằm trong danh sách CẤM DÙNG của HUONG_DAN_CHUNG.md, nên tôi đã đổi
  toàn bộ `~` thành `-` trong bản dịch (vd "1~3" → "1-3"). Đã kiểm tra không còn
  `~` nào sót trong file output.
- Số lượng `;`, `{`, `^` giữa gốc và dịch đã khớp 100% qua kiểm tra script cho
  toàn bộ 915 dòng (xem log `build_and_check_C1.py`).

## 5. Về giới hạn RongPx (độ rộng px)
Đa số tên trang bị trong lô này theo mẫu tiếng Hàn "[Tên riêng][Loại]" ghép liền
không dấu cách (vd `가그라드대검` = 6 âm tiết ≈ 60px), trong khi bản Việt cần
"[Loại trang bị] [Tên riêng]" — hai từ có dấu cách, và bản thân từ loại trang bị
tiếng Việt (Trường Kiếm, Trường Thương, Giáp Ống Chân...) đã dài hơn 1 âm tiết Hàn.
Kết quả: **phần lớn tên trang bị trong lô này (ước tính ~55-60%) vượt RongPx gốc
theo công thức ước lượng của HUONG_DAN_CHUNG.md**, đặc biệt các loại có tên loại
trang bị dài (Trường Thương, Giáp Ống Chân, Mũ Trụ Chiến, Giáp Lưới Chiến...).
Đây là vấn đề mang tính hệ thống của toàn bộ nhóm vật phẩm trang bị (không riêng
gì lô này) do khác biệt cấu trúc ngôn ngữ, KHÔNG thể khắc phục bằng cách rút ngắn
câu chữ mà không mất thông tin loại/tên trang bị. Ví dụ điển hình (RongPx gốc →
ước tính px bản dịch):
- K01260 `태양척살장창` (RongPx 60) → "Trường Thương Trừ Khử Mặt Trời" (~210px)
- K01057 `지옥화염도검` (RongPx 60) → "Đao Kiếm Hoả Diễm Địa Ngục" (~182px)
- K01723 `신비조사회의 반지` (RongPx 86) → "Nhẫn của Hội Điều Tra Bí Ẩn" (~189px)

Đề xuất gửi đội kỹ thuật/game design: (a) chấp nhận hiển thị tràn/thu nhỏ font
cho tên trang bị tiếng Việt (thường các bản Việt hoá J2ME khác cũng chấp nhận
điều này), hoặc (b) nếu engine hỗ trợ, rút ngắn bằng cách bỏ bớt từ "loại trang
bị" cho các item đã có icon phân biệt trong UI (vd chỉ hiển thị "Gagrad" thay vì
"Đại Kiếm Gagrad") — cần NAM quyết định vì ảnh hưởng cách hiển thị toàn bộ game,
không chỉ lô này.

Các câu tiêu hao/orb dài (item_11, item_12) đều nằm trong giới hạn GioiHanByte=255
(byte ước tính tối đa ~85/255), không đáng lo về byte.

## 6. Vật phẩm nghi là placeholder/debug
- K01280 `가나다라마바사` (chuỗi phụ âm/nguyên âm Hàn liên tiếp, tương đương
  "abcdefg") — nghi là item test/debug trong game gốc, tạm dịch "Trượng Thử
  Nghiệm" để giữ nguyên vị trí là 1 cây trượng, không dịch nghĩa đen.
- K01742 `포션9` ("Potion9") — tên có số lạ, giữ nguyên số, tạm dịch
  "Bình Thuốc 9" — có thể là item test tương tự.
- K01757 `사용안함` ("không dùng/không sử dụng") — khả năng cao là slot socket
  ẩn/không kích hoạt trong UI, đã dịch "Không Dùng".

## 7. Kết quả kiểm tra tự động (build_and_check_C1.py)
- Số dòng nguồn: 915, số dòng dịch: 915 — khớp 100% ID và thứ tự.
- Không còn ký tự Hangul sót trong cột dịch.
- Số lượng mã điều khiển (`;` `{` `}` `` ` `` `^` `<` `|` `&` `#` `%d` `%s`)
  giữa gốc và dịch khớp nhau ở toàn bộ 915 dòng.
- Không còn ký tự cấm dùng (`'` `"` `` ` `` `^` `<` `[` `{` `}` `$` `&` `|` `=`
  `_` `~` và ngoặc kép cong) trong cột dịch sau khi sửa 2 dòng có `'` và ~78
  dòng có `~`.
- Khoảng trắng đầu/cuối dòng khớp 100% giữa gốc và dịch.
- File kết quả: `outputs/C1_vatpham_1.out.tsv` (915 dòng + header, đúng thứ tự ID).
