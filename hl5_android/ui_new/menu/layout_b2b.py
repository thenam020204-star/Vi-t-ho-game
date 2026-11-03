#!/usr/bin/env python3
"""Sinh phần b2b của LAYOUT.md (nối sau dấu <!-- B2B -->). Bảng vùng chạm lấy trực tiếp từ hits do mock_b2b.py dựng."""
import os, re
from mock_menu import content_rect

MARK = '<!-- B2B -->'

TEXT = r'''
## 12. Lô b2b: NPC, Cash shop, Chuyên gia, popup StateGame, StateMap (T17b2)
> Sinh bởi `mock_b2b.py` + `layout_b2b.py` (chạy SAU `mock_menu.py` và `mock_b2a.py`). Dùng nguyên `metrics(W,H)` của mục 1; mọi số là điểm ảnh khung logic, chia nguyên. 30 màn x (400x240, `_x3`, 1200x540 + `trung_gian/` 600x270/800x360): `npc_*` (14), `cash_*` (5), `chuyen_gia_*` (4), `game_*` (5), `ban_do_*` (2). Tổng hợp `tong_hop_b2b.png`; vùng chạm `hit_rects.json` (khoá `<màn>_<WxH>`); kiểm tràn `verify.txt` (phần b2b).

### 12.1 Khung chung của các cửa sổ b2b
- **Không có thanh tab 9 mục** (NPC/Cash/Chuyên gia/Map là state riêng, không phải tab InGameMenu): cửa sổ = `w_window(tabs=None)` (khung cam, tiêu đề, nút `<` = `KeyPressed(Back)` gốc), vùng nội dung = `content_rect` cao hơn tab của b2a đúng `tabH` (24/24/36/48 px). Tiêu đề = tên chức năng THẬT trong `common_text` (83 Mua kỹ năng, 84 Mua vật phẩm, 85 Bán vật phẩm, 86/87 Tôi luyện thường/cao cấp, 88 Kết hợp Orb, 89 Chế biến thường, 284 Bộ sưu tập, 269 Đồ Giám Vũ Khí, 245 Cửa hàng Cash, 244 Cửa hàng, 195 Màn hình chuẩn bị; `ingame_text` 147/148 bản đồ).
- **Khay vật phẩm `shelf`** (dùng cho Mua kỹ năng, Mua vật phẩm, Bán, Chế biến, Bộ sưu tập, Cash shop, Cửa hàng Chuyên gia): `[dải tiền]` → `[tab con]` → hàng: `lưới 4x4` (+ thanh cuộn dọc `sbw` hoặc cột trang) | `hộp chi tiết` → `hàng giá` → `nút hành động`.
  - Dải tiền cao `lp+2s+2` (nhãn "Tiền hiện có" trái, [xu]+số phải; Bộ sưu tập: nhãn "Bộ sưu tập" + đếm; Chế biến: câu gợi ý 135 màu vàng; Cash/Chuyên gia: không có dải).
  - Tab con (chữ) = `w_subtabs` của b2a: Mua vật phẩm 3 tab (Vũ khí/Giáp/Khác = `common_text` 95/242/96), Bộ sưu tập 4 tab lớp (178–181).
  - Lưới = **4 cột x 4 hàng nhìn thấy** (như Túi đồ; lưới gốc của NPC là con trỏ 4 cột, cuộn theo hàng), ô `cell`, bước `pitch`; thanh cuộn dọc `sbw` cách lưới 3 px (tổng số hàng mẫu 5/8/6/3); ô `?` = chưa thu thập (Bộ sưu tập), ô trống = gạch xám.
  - Bán vật phẩm giữ **cột trang** như Túi đồ (`[this+0xe]` trang, `[this+0xf]` ô, 4x4 mỗi trang): `gx = x0 + pitch + gap + 1`, hộp chi tiết bắt đầu `gx + gridW + gap + 1`.
  - Hộp chi tiết = `w_info` của b2a: tên (≤2 dòng, màu phẩm chất) / loại (dòng phụ) / hàng k–v (Giới hạn cấp, ATK, Tốc độ đánh; Chế biến: nguyên liệu `có/cần`) / mô tả cuộn. **Hàng giá** (cao `max(lp+2, xu*k+2)`, khung xám xanh): [xu]+giá căn phải (vàng; đỏ khi > tiền hiện có); Cash/Chuyên gia: nhãn xanh lá "Bản offline: miễn phí" (từ `common_text` 304 bỏ `^` và dấu chấm). **Nút** `btnH` ở đáy cột chi tiết: nhãn = tên hành động THẬT (83/84/85/150/272; Cash/Chuyên gia "Nhận!" = `common_text` 261), vô hiệu (xám) khi không đủ tiền.
- **Hàng ô (`w_slot`)** cho Tôi luyện/Orb: panel xám xanh cao `cell+6`; ô `cell` bên trái + nhãn (font nhỏ, mờ) + tên (font thân, màu phẩm chất); ô trống hiện câu hướng dẫn mờ (127 "Không có vật hiến tế."). Chạm = mở chế độ chọn (`OpenBagFilter`), màn chọn = `npc_chon_vat_pham` (hàng 5 vật phẩm/trang như `tui_do_chon` b2a, không thanh tab).
- **Bảng k–v (`w_statrows`)**: panel, hàng cao `spx+5` (SD) / `px+6` (HD), nhãn mờ trái, giá trị phải, hàng tiền có [xu]. Tôi luyện: Tỉ lệ thành công / Độ tôi luyện / Tiền hiện có / Chi phí; Orb: Tỉ lệ thành công; Chuyên gia: Chế độ / Cấp độ.
- **Popup**: dùng widget chung T17b1 (`w_popup`) hoặc `w_popup_c` (cùng công thức kích thước, nhưng từng dòng có màu: tên vật phẩm vàng, nhãn giá xanh lá). Popup kết quả Chuyên gia (`w_result`): tiêu đề (Chiến thắng! vàng / Thất bại! đỏ) + vạch + 4 hàng k–v + nút OK; cao `2pad+4s+lp+4+n(lp+2)+pad+btnH+2`.
- **StateGame**: không có cửa sổ; nền = cảnh chơi tối `dim` 200 (màn chết) hoặc 90 (kết quả), popup giữa màn (như popup chung).
- **StateMap**: cửa sổ tiêu chuẩn; vùng bản đồ = nội dung trừ hàng nút dưới; bản đồ lớn: ảnh (ở mockup là nền vẽ minh hoạ, **game dùng ảnh bản đồ gốc** vẽ vừa vùng, giữ tỉ lệ), 8 điểm + nhãn tên đảo (`ingame_text` 33–40, nền đen α190) + mũi tên đỏ vị trí; bản đồ khu vực: lưới điểm 3 cột x 4 hàng (ô điểm `10s+12`, nhãn font nhỏ bên phải, tối đa 2 dòng, nền đen α215 che đường nối), ô hiện tại xanh lá, đường nối cam tối `s+1` px; 2 nút dưới: "Tắt/Bật Minimap" (`*`) và "Bản đồ lớn/khu vực" (`#`) (bỏ ký tự gợi ý phím như b1).

Số đo quan trọng (khung logic):
| khung | nội dung (x0,y0,x1,y1) | dải tiền | tab con | lưới 4x4 (không/ có tab con) | hộp chi tiết bắt đầu x (cuộn / cột trang) |
|---|---|---|---|---|---|
{ROWS}

### 12.2 Lưới/trạng thái logic GỐC giữ nguyên và chỗ lệch
Không đổi lưới nào (kiến trúc A, §13.4): con trỏ gốc của từng state NPC/Cash/Expert giữ nguyên; chạm = đặt `[this+0xe..0x14]` rồi `KeyPressed(-5)` gốc.
| Cửa sổ | Lưới gốc giữ | Lệch/ghi chú |
|---|---|---|
| Mua kỹ năng (SkillBuy, state 0) | danh sách sách kỹ năng cuộn theo hàng 4 cột (ảnh gốc ô 36: 4 cột); xác nhận `Process` | không lệch; giá chỉ là biểu tượng xu + số (gốc không nhãn) |
| Mua vật phẩm (ItemBuy, 1) | 3 tab (Vũ khí/Giáp/Khác như ô 37) + lưới 4 cột | không lệch |
| Bán (ItemSale, 2) | túi đồ 4x4 x trang (như `tui_do`) | thêm nút bán bấm được thay phím OK |
| Tôi luyện thường/cao cấp (3, 4) | 2 ô chọn (vật phẩm, đá) + 4 hàng thông tin + nút | "Độ tôi luyện" hiển thị `+n > +n+1` là giả định (gốc dạng `00'03'00` chưa rõ nghĩa) |
| Kết hợp Orb (5) | 3 ô (vật phẩm, Orb, vật hiến tế) + tỉ lệ + nút; phím `0` bỏ vật hiến tế = nút | không lệch |
| Chế biến (NormalMix, 6) | danh sách công thức 4 cột + nguyên liệu `có/cần` + nút | số ô công thức tối đa kiểm ở T17c |
| Bộ sưu tập (7) | menu 3 nút (Đồ Giám Vũ Khí/Giáp/Trang sức) + lưới 4x4 `?` x 4 lớp | tab lớp = 4 tab chữ (gốc: phím trái/phải) |
| Cash shop (state 0) | lưới 4 cột cuộn + popup 4 loại | **bỏ giá tiền thật**: nhãn "Bản offline: miễn phí" + nút "Nhận!"; 4 popup: xác nhận (91 + 304), đã nhận (93), túi đầy (92), khu vực cấm (331) |
| Chuyên gia: Chuẩn bị (0) / Cửa hàng (1) | 3 nút (Nhân vật, Cửa hàng, Bắt đầu) / lưới 4 cột cash_expert | chế độ (Cầm cự/Đua Thời Gian/Đột phá) hiển thị chỉ đọc (chọn ở MainMenu: b2c) |
| StateGame popup (SetState 1/2/4…) | hồi sinh Có/Không (260 + 304), xác nhận (293), thành công (350), kết quả Chuyên gia (327/328 + 331–338) | bỏ dòng "Giá gốc … won / phí thông tin" của bản mạng |
| StateMap (2 chế độ) | lưới điểm khu vực + ảnh đảo; `#`/`*` đổi chế độ/Minimap | ảnh bản đồ gốc thay nền minh hoạ |

### 12.3 Vùng chạm từng màn (đại diện: phần tử đầu mỗi nhóm; còn lại trong `hit_rects.json`)
Định dạng `[x0,y0,x1,y1)` theo khung logic, cột = 400x240 / 600x270 / 800x360 / 1200x540. Mọi màn có `close` (nút `<`).
'''

TAIL = r'''
### 12.4 Giả định cần xác nhận khi implement (T17c)
1. Số liệu giá / cấp / số lượng / tỉ lệ / `Độ tôi luyện` / đếm Bộ sưu tập trong ảnh là SỐ MẪU; chuỗi, tên, mô tả, vật liệu, tên khu vực là dữ liệu thật đã build. Icon gán theo thứ tự mẫu (`icon.mgr` 203–231); khi implement lấy qua `ItemInfo::DrawIcon` (cần thunk ×s như b1).
2. Nhãn giá Cash/Chuyên gia: dùng nguyên `common_text` 304 ("Bản offline: miễn phí", bỏ `^` và dấu chấm); nút "Nhận!" = 261 (nghĩa gốc: nhận vật phẩm cash, suy từ vị trí chuỗi); hai popup `cash_popup_mua`/`chuyen_gia_popup_mua` ghép 91 + 304 thay đoạn "Giá gốc … won". Nếu quản đốc muốn nhãn ngắn "Miễn phí" cần thêm 1 chuỗi mới vào dữ liệu.
3. Ảnh bản đồ lớn trong mockup là nền minh hoạ (không tìm được ảnh thật trong VFS dạng .mgr/PNG; game đọc qua `LoadWorldMapImage`): bố cục chỉ cần vùng chữ nhật + toạ độ tương đối (ISL trong `mock_b2b.py`, lấy theo ảnh gốc ô 60 của `kiemke.png`); toạ độ điểm thật phải đọc từ `DrawWorldMap` ở T17c.
4. Lưới điểm khu vực: 7 điểm (cột,hàng) lấy theo ảnh gốc ô 59; số điểm/cạnh thật của mỗi vùng đọc từ dữ liệu bản đồ ở T17c.
5. Tab "Khác"/"Giáp" của Mua vật phẩm hiển thị cùng danh sách mẫu; số vật phẩm mỗi tab lấy từ `NpcShop::LoadShopData`.
6. Popup StateGame hồi sinh/kết quả: các hàng kết quả Chuyên gia (Số màn, Thời gian còn lại, HP còn lại, Điểm số cuối cùng) theo chế độ (Cầm cự/Đua Thời Gian/Đột phá dùng bộ nhãn khác: 331–336); mockup vẽ 1 bộ.
7. Menu Cash shop `StateCashShop` có thể mở với 3 `type` (đơn/mạng/Expert): offline chỉ dùng single (và expert qua Chuyên gia); cash_network bỏ.
'''

def write(out, hits, SCREENS, metrics, FRAMES):
    rows = []
    for (W, H) in FRAMES:
        m = metrics(W, H); s = m.s
        body = (m.wx, m.wy + m.titleH, m.wx + m.winW, m.wy + m.winH); x0, y0, x1, y1 = content_rect(m, body)
        st = m.lp + 2 * s + 2; sh = m.px + 2 * (m.bt + 3) + 2
        gy0 = y0 + st + m.gap; gy1 = gy0 + sh + m.gap
        sbx = x0 + m.gridW + 3; dx = sbx + m.sbw + m.gap + 2; dxp = x0 + m.pitch + m.gap + 1 + m.gridW + m.gap + 1
        rows.append('| %dx%d | %d,%d,%d,%d | %d | %d | (%d,%d) / (%d,%d) %dx%d | %d / %d |' % (W, H, x0, y0, x1, y1, st, sh, x0, gy0, x0, gy1, m.gridW, 4 * m.pitch - m.cg, dx, dxp))
    lines = [TEXT.replace('{ROWS}', '\n'.join(rows))]
    for n, fn, lab in SCREENS:
        lines.append('\n**%s** (`%s`): %s\n' % (n, n, lab))
        lines.append('| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |')
        lines.append('|---|---|---|---|---|---|')
        per = {}
        for (W, H) in FRAMES:
            for h in hits.get('%s_%dx%d' % (n, W, H), []):
                g = re.sub(r'[\d_]+$', '', h['id']) or h['id']
                d = per.setdefault(g, {}); d.setdefault((W, H), []).append(h)
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
