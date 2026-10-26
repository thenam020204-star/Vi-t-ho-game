# PROGRESS — Việt hoá Hiên Viên Kiếm – Thiên Chi Ngân 1–5
- Nguồn sự thật: PLAN.md (checklist, quyết định thiết kế).
- Đã chốt: Hán Việt nhẹ · font f15c · gộp = chỉ báo cáo khả thi.
- Giai đoạn hiện tại: P1 — phân tích định dạng text + harness (T1–T6).

## Font (xong cho cả 5 phần — quản đốc)
- `java/VnFont.java` (BitmapFont f15c, LINE_GAP=2) + `scripts/cp_redirect.py` (patch constant pool, invokevirtual→invokestatic, không đổi độ dài code).
- Luật từng phần: `pN/font_rules.tsv`; báo cáo chỗ đã đổi: `pN/redirect_report.txt`.
  - P1 64 lời gọi (split `h/b.a`) · P2 66 (`e/a/b.a`) · P3 132 (`b/e/a.a`, SDK `g/b.a`) · P4 131 (như P3) · P5 142 (`a/c/b.a`, SDK `d/a.a`). Số lời gọi khớp đúng thống kê javap.
- Build: `scripts/build_part.sh N <res_dir> out.jar` (redirect → VnFont+BitmapFont+font/f15c.bin → manifest MIDP-2.0 → preverify → jar_check).
- jar_check: PASS cả 5 (`pN/jar_check_font.txt`). FreeJ2ME: không exception, chữ Latin vẽ bằng f15c (`shots/font_test/pN`). Chữ Hán biến mất là đúng (font không có glyph Hán).
- Khung thoại engine: `o = lineH*3 + 6` → 3 dòng sẵn; lineH = VnFont.getHeight (17px).
- Còn chờ: text tiếng Việt (A1 trích xuất → công nhân dịch) để soát độ rộng/ngắt dòng thật.
