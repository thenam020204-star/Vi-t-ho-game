#!/usr/bin/env python3
"""Sinh phần b2c của LAYOUT.md (nối sau dấu <!-- B2C -->). Bảng vùng chạm lấy trực tiếp từ hits do mock_b2c.py dựng."""
import os, re

MARK = '<!-- B2C -->'

TEXT = r'''
## 13. Lô b2c: StateMainMenu (T17b2)
> Sinh bởi `mock_b2c.py` + `layout_b2c.py` (chạy SAU `mock_menu.py`, `mock_b2a.py`, `mock_b2b.py`). Dùng nguyên `metrics(W,H)` của mục 1. 18 màn x (400x240, `_x3`, 1200x540 + `trung_gian/` 600x270/800x360), tên `menu_chinh_*`. Tổng hợp `tong_hop_b2c.png`; vùng chạm `hit_rects.json`; kiểm tràn `verify.txt` (phần b2c).

### 13.1 Nền và khung chung
- **Nền thật toàn màn** (MainMenu tự vẽ, không chụp cảnh chơi): đen + dải dung nham `menu.mgr` #8 (333x63) lát ngang, lật dọc ở đáy (x1; x2 khi HD) + logo `sp/imgcom/title.mgr` (#5 HEROES LORE, #3 chữ V, #1 mắt dung nham mờ, #7 bản quyền, #8 "Tap to Continue"). Phụ đề "Khế Ước Hắc Ám" ở mockup là CHỮ (ảnh gốc là tiếng Anh, T11 vẽ lại); "Tap to Continue" vẫn là ảnh gốc tiếng Anh (T11).
- Các màn cửa sổ (slot, lớp, Chuyên Gia, cài đặt, thành tựu, trợ giúp, hỗ trợ) dùng `w_window` (khung cam, tiêu đề, nút `<` = `KeyPressed(Back)` gốc) làm tối nền thật `dim 95`, KHÔNG có thanh tab.
- **Lưới menu chính**: ô `tw x th` trong vùng giữa đáy logo và bản quyền; `tw = min((W-2mg-2g)/3, 2.4 th)`, `g = gap+2`; nhãn font thân (15/18 px) vừa ô ở mọi khung (kiểm `verify.txt`); ô cao đủ -> icon `menu.mgr` #19..#27 trên chữ dưới; ô thấp (9 ô ở 400x240) -> icon trái chữ phải. Logo x1 (H<300) / x2.
- Mô tả/nội dung dài: hộp có CLIP + thanh cuộn khi dài hơn hộp (kéo dọc = `TouchMoved` mới); mô tả lớp (lỗi tràn người dùng đã thấy) vẽ trong hộp popup có clip, hiện đủ ở 4 khung (xem verify).

### 13.2 Lưới/trạng thái logic GỐC giữ nguyên và chỗ lệch
| Màn (state gốc) | Lưới gốc giữ | Lệch/ghi chú |
|---|---|---|
| Menu chính 9 ô (4) | 3x3, con trỏ 0..8 theo thứ tự Bắt đầu, Chuyên Gia, Vào kho đồ, Cập nhật, Cài đặt, Thành tựu, Trợ giúp, Cộng đồng, Thoát | không lệch |
| Menu chính 6 ô (4, T7) | 3 cột x 2 hàng: Bắt đầu, Chuyên Gia, Cài đặt, Thành tựu, Trợ giúp, Thoát | **LỆCH có chủ ý theo kế hoạch T7**: bỏ ô Kho/Cập nhật/Cộng đồng, số hàng 3 -> 2; con trỏ 0..5 (T7 phải sửa bảng nhảy/số ô; chạm ánh xạ lại ô gốc 0,1,4,5,6,8) |
| Chọn Slot (5) | 3 slot dọc; chạm slot = OK gốc; `*` = xoá | nút Xoá bấm được thay phím `*` |
| Chọn lớp (5) | lưới 2x2 (4 lớp) | không lệch; thêm nút OK (= phím OK); mô tả ngắn bên phải |
| Popup mô tả lớp | StatePopup có OK/Huỷ | mô tả trong hộp cuộn có clip |
| Chuyên Gia (6) | 3 chế độ dọc (Cầm Cự, Đua Thời Gian, Đột Phá) | bỏ "Xem bảng xếp hạng" (199, cần mạng) |
| Cài đặt (9) | danh sách dọc có cuộn, mục đổi bằng TRÁI/PHẢI | 7 mục hiển thị: 6 mục gốc + Phân giải (T10); số mục gốc thật là 10 (BAO-CAO §11), mục còn lại xác nhận ở T17c |
| Thành tựu (10) | danh sách dọc 105 mục + chi tiết | không lệch |
| Trợ giúp (11) | danh sách 20 bài + trang nội dung | nút Trước/Tiếp thay phím TRÁI/PHẢI |
| Hỗ trợ (12) | trang tĩnh thông tin thẩm định | không lệch |
| State 0/1/7/8 (mạng) | - | offline: boot vào state 5/2 (patch 0x99bfc), T7 bỏ; chỉ vẽ popup chờ chung |

Số đo (khung logic; nhãn font thân):
| khung | ô lưới 6 (tw x th) | ô lưới 9 | slot cao | cài đặt: hàng cao | thành tựu: hàng cao / số hàng hiện | trợ giúp: số hàng hiện |
|---|---|---|---|---|---|---|
{ROWS}

### 13.3 Vùng chạm từng màn (đại diện: phần tử đầu mỗi nhóm; còn lại trong `hit_rects.json`)
Định dạng `[x0,y0,x1,y1)` theo khung logic, cột = 400x240 / 600x270 / 800x360 / 1200x540.
'''

TAIL = r'''
### 13.4 Giả định cần xác nhận khi implement (T17c)
1. Icon: ô lưới `menu.mgr` #19..#27 theo thứ tự 9 ô (suy từ hình dạng: kiếm, quỷ sừng, túi, làm mới, bánh răng, "!", "?", mặt cười, tia); 5 icon lớp `menu.mgr` #28..#32 (mockup gán Warrior 29, Rogue 30, Gunslinger 31, Knight 32); khung chân dung dùng vòng `ui_new/03` + icon (gốc vẽ sprite nhân vật; thay ở T17c).
2. Giá trị mẫu Cài đặt: Nhạc nền/Âm hiệu ứng hiển thị cấp 0–5 (chưa rõ), Tốc độ game/Hiệu ứng = Cao/Trung/Thấp (chuỗi 321–323), Rung/Tự ngắm = BẬT/TẮT; ghi chú "Khởi động lại để áp dụng" lấy từ khối T10 của `vi.bin`. Mục Phân giải vẽ 4 chip 1/1..1/4 (gốc: vòng bằng TRÁI/PHẢI `t10_step`); chạm chip = lặp bước tới khi khớp.
3. Chuỗi `common_text` 210 trong dữ liệu build vẫn là "Xem ngang"; mockup dùng "Phân giải" (`common_text` 53) như T10 ghi đè lúc chạy.
4. Mô tả lớp: ngắn = `menu_text` 24–27, đầy đủ = 20–23; điểm số/LV/thời gian chơi/tiến trình thành tựu là SỐ MẪU; tên nhân vật mẫu = `common_text` 9.
5. Thành tựu: `mission_text[i]` ↔ `mission_list[i]` (i=0 là tiêu đề nhóm); trạng thái đã đạt trong ảnh là mẫu.
6. Huy hiệu 12+ (Logo) và "Tap to Continue" là ảnh gốc tiếng Anh/Hàn: T11 vẽ lại.
'''

def write(out, hits, SCREENS, metrics, FRAMES, G):
    rows = []
    for (W, H) in FRAMES:
        m = metrics(W, H)
        body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH)
        x0, y0, x1, y1 = (body[0] + m.pad, body[1] + m.pad, body[2] - m.pad, body[3] - m.pad)
        g6 = G['grid_geom'](m, 6); g9 = G['grid_geom'](m, 9)
        sr, _ = G['slot_geom'](m, (x0, y0, x1, y1)); o = G['opt_geom'](m, (x0, y0, x1, y1)); a = G['ach_geom'](m, (x0, y0, x1, y1)); h = G['help_geom'](m, (x0, y0, x1, y1))
        rows.append('| %dx%d | %dx%d | %dx%d | %d | %d | %d / %d | %d |' % (W, H, g6['tw'], g6['th'], g9['tw'], g9['th'], sr, o['rh'], a['rh'], a['n'], h['n']))
    lines = [TEXT.replace('{ROWS}', '\n'.join(rows))]
    for n, fn, lab in SCREENS:
        lines.append('\n**%s** (`%s`): %s\n' % (n, n, lab))
        lines.append('| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |')
        lines.append('|---|---|---|---|---|---|')
        per = {}
        for (W, H) in FRAMES:
            for hh in hits.get('%s_%dx%d' % (n, W, H), []):
                g = re.sub(r'[\d_]+$', '', hh['id']) or hh['id']
                d = per.setdefault(g, {}); d.setdefault((W, H), []).append(hh)
        for g, d in per.items():
            if g in ('popup_modal',): continue
            row = [d[(W, H)][0] for (W, H) in FRAMES if (W, H) in d]
            if not row: continue
            cnt = len(d.get((400, 240), []))
            cells = [('`%s`' % ','.join(map(str, d[(W, H)][0]['rect']))) if (W, H) in d else '-' for (W, H) in FRAMES]
            lines.append('| %s (%d) | %s | %s |' % (g, cnt, ' | '.join(cells), row[0]['action'].replace('|', '/')))
    lines.append(TAIL)
    p = os.path.join(out, 'LAYOUT.md'); t = open(p, encoding='utf-8').read()
    if MARK in t: t = t[:t.index(MARK)]
    if not t.endswith('\n'): t += '\n'
    open(p, 'w', encoding='utf-8').write(t + MARK + '\n' + '\n'.join(lines))
