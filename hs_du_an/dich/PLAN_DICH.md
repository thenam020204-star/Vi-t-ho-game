# PLAN: Việt hoá text — Hiên Viên Kiếm: Thiên Chi Ngân (phần 1–5, cả lô)

## Mục tiêu
Dịch toàn bộ text Trung → Việt của cả 5 phần trong một lô, nạp lại được ngay, đúng định dạng gốc.

## Phạm vi
- Trong phạm vi: TOÀN BỘ text: resource (`map/*.m`, `*.m2`, `i.dat`, `*.dat`) + chuỗi hiển thị trong `.class`, của P1–P5. Một task, một công nhân tại một thời điểm.
- Ngoài phạm vi: font/bytecode/ảnh (quản đốc làm).

## Đường dẫn
- Thư mục làm việc: `/home/user/Vi-t-ho-game/hs_du_an/dich` (chạy `dp` ở đây). Dữ liệu `dp_work/`, bản dịch `dp_work/out.tsv`.
- `dp` = `python3 /root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa/scripts/dp.py`
- Quy trình + luật: `.../j2me-viet-hoa/references/dich-pro.md` (Luật cứng, Tiết kiệm token, Văn phong, Bước 2–4).

## Phân tích độ khó (quản đốc)
- **Khó.** 19 614 dòng → 9 377 chuỗi duy nhất, 178 442 ký tự gốc; thoại 55 %, xưng hô 50 %; dài TB 19, max 469. Kịch bản RPG kiếm hiệp nhiều nhân vật (xưng hô theo `vai.md`), ~730 tên vật phẩm/chiêu thức/kỹ năng Hán Việt, mô tả hiệu ứng có số liệu, 173 nhãn UI có giới hạn (≤6 ký tự trở lên).
- Mã: `{…}` ×1764 (từ khoá tô đỏ), `\n` ×297, `~` ×729, `@` ×6 — `dp` ép giữ nguyên.
- Cột ngữ cảnh (`#ctx`): "kịch bản N; trước: <câu liền trước>" (câu trước thường là TÊN người nói), "tên bản đồ", "code: HIEN/KHOA/?…" (chuỗi trong class).
- Chuỗi trong class có loại `KHOA` là nhãn menu được so bằng `equals`. Không sao, vì mọi chuỗi gốc giống nhau đều dùng chung một bản dịch (dp đã gộp trùng). **Không** được dịch hai chuỗi gốc giống nhau thành hai bản khác nhau.

## Quyết định thiết kế (công nhân phải tuân theo, KHÔNG tự đổi)
- Ngôn ngữ gốc: zh. Phong cách: **Hán Việt nhẹ, dễ đọc**. Tên riêng/chiêu thức/vật phẩm Hán Việt **có dấu**.
- Công nhân: Sonnet 5.5 (`claude-sonnet-5-5`). Mỗi lúc chỉ MỘT công nhân. Dịch tay, cấm dịch máy, cấm agent khác.
- Bảng thuật ngữ: `dp_work/thuat-ngu.tsv` (131 mục; loại `~` = không ép). Vai & xưng hô: `dp_work/vai.md`.
- Charset: f15c (`charset_f15c.txt`). Font KHÔNG có dấu câu Trung, `~`, `=`, `©` → dùng dấu ASCII (xem vai.md). `~` vẫn phải giữ vì dp ép; quản đốc đổi cơ học khi ghép.
- **Xoay vòng công nhân (yêu cầu người dùng):** một công nhân làm tối đa **22 lô `dp next`** (≈ 400k token ngữ cảnh) trong phiên của mình. Đến mức đó, hoặc thấy ngữ cảnh đã rất dài: dịch xong lô đang dở, chạy `dp status`, ghi Nhật ký `Công nhân #K: M/N, dừng để xoay vòng`, rồi kết thúc. Quản đốc mở công nhân mới; công nhân mới chạy `dp next --vai` và làm tiếp từ `out.tsv`.

- Giới hạn ký tự ở `t.dat` (cột gioi_han) chỉ là ước lượng 4n+2, repack tự tính lại độ dài; tên nhân vật giữ đủ (k=42 "Trương Liệt" 11>10 đã được quản đốc duyệt, lỗi `dài` của k=42 bỏ qua).
- 芦家渡 = 卢家渡 = "bến Lư Gia" (đã thêm thuật ngữ).

## Checklist
- [x] T0. Quản đốc: prep, stats/peek, chốt bảng thuật ngữ + vai
- [x] T1. Dịch toàn bộ text (công nhân, xoay vòng theo luật trên)
  - Đầu vào/đầu ra: `dich_tat_ca.tsv` → `dp_work/out.tsv`
  - Hoàn thành khi: `dp check` = OK, đã `dp sample 30` — **Quản đốc nghiệm thu**
- [x] T2. Ghép (`dp merge`), chuẩn hoá ký tự ngoài font, chia lại theo phần, repack + build 5 jar — quản đốc

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (trống)

## Nhật ký
- Quản đốc: prep 19614 dòng/9377 duy nhất, charset f15c, mã `{}`/`\n`; thuat-ngu 131 mục, vai.md.
- Công nhân #1: 3105/9377, dừng theo lệnh quản đốc (xoay vòng). Lưu ý: k=42 nhãn "張烈" giới hạn ≤10 nhưng "Trương Liệt"=11 ký tự, đang để "TrươngLiệt" (không cách) - quản đốc quyết. Tên "Bến Lô Gia" (芦家渡, k~712,1272-1290) cần thống nhất với thuật ngữ 卢家渡=bến Lư Gia. Cảnh báo còn lại chỉ là thiếu glyph "~" (quản đốc xử lý khi ghép).
- Quản đốc: nghiệm thu #1 (sample 12 đạt, 40 thuật ngữ mới hợp lý); sửa k=42 → "Trương Liệt", Lô Gia → Lư Gia. Mở công nhân #2.
- Công nhân #2: 7203/9377, dừng để xoay vòng (16 lô; thuật ngữ mới đã thêm vào thuat-ngu.tsv, cột 4 = mới; cảnh báo còn lại chỉ thiếu glyph ~ và —).
- Quản đốc: nghiệm thu #2 (không lỗi định dạng ngoài glyph `~`/`—`; 6 lệch thuật ngữ là diễn đạt hợp lý). Mở công nhân #3 từ k=7204.
- Công nhân #3: 9377/9377 XONG (10 lô, k=7204-9377). `dp check`: chỉ còn thiếu glyph (`~` 190, `©` 6 ở k=9218/9219/9276/9349/9371/9376, `=` 1), k=42 dài 11>10 (đã duyệt), k=9201/9202 báo ký tự rộng (gốc có sẵn U+3000 đầu dòng, dp tự giữ), 6 lệch thuật ngữ của #1/#2 (diễn đạt). Đã `dp sample 30`. Thuật ngữ mới ghi `mới` trong thuat-ngu.tsv; ghi-chu.txt: mảnh ghép với số (k9199-9206, 9256-9267) cần khoảng trắng khi ghép, k9362 lẫn chữ Hán lạ, k8277 gốc 娘子{玉儿} có vẻ nhầm 月儿, k7565 浮云龙玉 dịch Phiếm Vân Long Ngọc.
- Quản đốc: nghiệm thu #3; sửa mảnh ghép số (EDGE trong split_merged.py), k9199/9256/9210, "Nhạc " (t.dat). dp merge → split_merged (thiếu glyph: không) → rebuild_all: 5/5 PASS; FreeJ2ME 5 phần không exception, ảnh shots/dich_5phan.png.
