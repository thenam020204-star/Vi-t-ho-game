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

## Kiểm đầu-cuối P1 (text giả tiếng Việt, chỉ để thử bố cục)
- repack 3924 chuỗi → build_part.sh → jar_check PASS → FreeJ2ME: chữ có dấu hiển thị đúng, ngắt tại dấu cách, khung thoại 3 dòng cuộn đúng, `{từ khoá}` đỏ giữ màu qua dòng (`shots/fakevi_p1/`).

## Ảnh menu (người dùng tự vẽ)
- `anh_menu/`: toàn bộ ảnh menu 5 phần (PNG gốc jar + giải nén b/c/ic/mm.pak), `can_ve/pN/` 149 ảnh có chữ (87 ảnh khác nhau), `DANH_SACH_CO_CHU.tsv`, `README_VE_ANH.md`.
- `scripts/pakimg.py` unpack/pack (XOR 1), round-trip 41/41.

## Trích xuất text (A1, đã nghiệm thu)
- `pN/text_can_dich.tsv` (P1 3924 · P2 5189 · P3 3801 · P4 2860 · P5 3114), `all/text_can_dich.tsv` 18888 dòng / 9192 chuỗi gốc duy nhất.
- Tiếp: chuỗi CJK trong .class (T5) → chốt thuật ngữ + vai.md → 1 công nhân dịch cả lô (xoay vòng khi ngữ cảnh > 400k).

## Tiêu đề + credit + tên game (theo người dùng)
- 5 ảnh tiêu đề của người dùng → `anh_menu/can_ve/pN/mm.pak/title.png` (240x320, 256 màu; nguồn `anh_menu/nguon_tieu_de/`).
- Vùng an toàn của title.png: y 0–220 (menu tt.png y 222–258, núi m.png y 251–320, 2 dòng copyright ~286–320).
- Credit: chuỗi class dòng copyright dưới → "Việt hoá bởi NAM.P"; dòng trên "© …" → "(c) …" (`pN/class_override.tsv`, thắng bản dịch).
- Tên game (manifest/JAD): "Xuan-Yuan Sword - The Scar of Sky I/II/III/IV/Final" (`pN/manifest_override.txt`, `scripts/manifest_edit.py`). Đổi MIDlet-Name → máy coi là game mới, save bản Trung không thấy.
