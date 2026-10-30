# PLAN: Việt hoá text — This War of Mine (Android)

## Mục tiêu
Dịch text Anh → Việt (giọng thời chiến), ghi vào slot tiếng Anh của khối localization `2f6496cc` trong `common.dat`, nạp lại được ngay, đúng định dạng gốc.

## Phạm vi
- Trong phạm vi: T1 = hội thoại/mô tả/nhật ký/radio/tiểu sử (`t1.tsv`); T2 = UI, vật phẩm, chỉ số, kỹ năng, gamepad, credits (`t2.tsv`)
- Giữ nguyên (không dịch): `Names/*` (tên nhân vật ngẫu nhiên) + dòng rỗng → `giu_nguyen.tsv`
- Ngoài phạm vi của công nhân: patch font bitmap, đóng gói `common.dat/.idx` (quản đốc làm)

## Phân tích độ khó (quản đốc, từ `dp stats` + `dp peek 60`)
- T1: **Khó** — 6612 dòng duy nhất, 508K ký tự, thoại 53%, xưng hô 69%, nhiều người nói (người trú ẩn, trẻ con, lính, cướp, thương nhân, radio), lóng 4% (chửi thề của lính/cướp); điểm khó: xưng hô theo vai, thẻ giới tính {mr|}{fr|}, mảnh câu ghép DailyLog/ShelterAttack, giữ giọng thời chiến.
- T2: **Dễ** — 864 dòng, 48K ký tự, 37% nhãn ≤12 ký tự, thoại 8%; điểm khó: rút gọn nhãn UI, giữ mã |XPadA|, <BR>/<HEADER>/<NAME> và \n trong Credits.
- Dòng chuyển từ T2 sang T1: không (Traders/Events/E3 đã xếp T1/T2 theo prefix key).

## Quyết định thiết kế (công nhân phải tuân theo, KHÔNG tự đổi)
- Ngôn ngữ gốc: en — văn phong: **chiến tranh**: khô, lạnh, ngắn, đau đớn, thuần Việt; xem `dp_work/vai.md`
- Model T1: Sonnet 5.5 — lý do: Khó (xưng hô, nhiều vai, giọng văn)
- Model T2: Haiku 5.5 — lý do: text UI/HUD đơn giản
- Cách tách: file riêng theo prefix key (`tools/export_text.py`)
- **Bảng thuật ngữ** (quản đốc chốt): dp_work/thuat-ngu.tsv — tên riêng giữ Latin, phe phái, địa danh, vật phẩm, trạng thái
- **Vai & xưng hô** (quản đốc chốt): dp_work/vai.md
- Charset/glyph: dp_work/charset.txt (font sẽ được bổ sung đủ glyph tiếng Việt); mã điều khiển thêm: `\^[A-Za-z]+\^|\{[mf][rs]\||\}|\|XPad\w*\|`
- Lệnh dp: `python3 ../tools/dp.py -t t1 ...` chạy trong thư mục `projects/ThisWarOfMine/text`

## Checklist
- [x] T0. Quản đốc: prep, phân tích độ khó, chốt bảng thuật ngữ + vai, chọn model
- [ ] T1. Dịch text tài nguyên
  - Agent: A1 — Sonnet 5.5
  - Đầu vào/đầu ra: t1.tsv → dp_work/t1/out.tsv (`dp -t t1 ...`)
  - Hoàn thành khi: `dp -t t1 check` = OK, đã `sample 30` — **Quản đốc nghiệm thu**
- [ ] T2. Dịch text UI/HUD
  - Agent: A2 (chờ A1 ≥ 90%) — Haiku 5.5
  - Đầu vào/đầu ra: t2.tsv → dp_work/t2/out.tsv (`dp -t t2 ...`)
  - Hoàn thành khi: `dp -t t2 check` = OK, đã `sample 15`
- [ ] T3. Ghép, patch font, đóng gói common.dat/.idx, bao-cao.md — quản đốc

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (trống)

## Nhật ký
- T0 xong: t1 6612 dòng, t2 864 dòng, giữ nguyên 220 dòng.
