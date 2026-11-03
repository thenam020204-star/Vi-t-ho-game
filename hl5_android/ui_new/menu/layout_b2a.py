#!/usr/bin/env python3
"""Sinh phần b2a của LAYOUT.md (nối sau dấu <!-- B2A -->). Số liệu bảng vùng chạm lấy trực tiếp từ hits do mock_b2a.py dựng."""
import os, re

MARK = '<!-- B2A -->'

TEXT = r'''
## 11. Lô b2a: các tab InGameMenu còn lại + chế độ con Túi đồ + popup đặc thù (T17b2)
> Sinh bởi `mock_b2a.py` + `layout_b2a.py` (chạy SAU `mock_menu.py`). Dùng nguyên `metrics(W,H)` của mục 1 (s, px, spx, lp, pad, gap, cell, pitch, titleH, tabH, btnH, rowH, sbw, barH…); mọi số dưới đây là điểm ảnh khung logic, chia nguyên. Ảnh: `<màn>_400x240[.png|_x3.png]`, `<màn>_1200x540.png`, `trung_gian/<màn>_{600x270,800x360}.png`; tổng hợp `tong_hop_b2a.png`; vùng chạm đầy đủ `hit_rects.json` (khoá `<màn>_<WxH>`); kiểm tràn `verify.txt` (phần b2a).

### 11.1 Lưới/trạng thái logic GỐC giữ nguyên (bằng chứng so.dis; hằng đọc từ `Draw*`/`Key*`)
Menu mới chỉ đổi giao diện; con trỏ/cuộn/trang giữ nguyên ngữ nghĩa, chạm = đặt trường con trỏ rồi `KeyPressed(-5)` gốc (kiến trúc A, §13.4). Các hằng dưới đây lấy từ vòng lặp/`cmp` trong hàm gốc (vd `DrawEquipMenu` có vòng 8, `DrawPropertyLine` 3 ô × 4 hàng, `DrawComboMenu` 4 ô + 5 ứng viên, `DrawSkillMenu` 16 ô + 5 tab con); T17c phải đọc lại chính xác offset trường trước khi viết `TouchPressed`.
| Tab | Lưới gốc giữ | Con trỏ/trạng thái gốc | Chạm → |
|---|---|---|---|
| Nhân vật (1) | 4 hàng cộng điểm STR/AGI/VIT/INT; 2 trang (`#` = Chi tiết/Cơ bản): trang Chi tiết 13 chỉ số (ct 33–45) | con trỏ hàng `[this+0xe]`; trái/phải = giảm/tăng điểm tạm (`BackupStatPerformance`), `*` = `CancelApplayStat` | chạm hàng = đặt con trỏ; nút +/− = `KeyPressed(PHẢI/TRÁI)`; nút Chi tiết/Cơ bản = phím `#`; nút Huỷ = phím `*` |
| Trang bị (3) | 8 ô, 4 cột × 2 hàng (vòng `cmp 8` trong `DrawEquipMenu`); thứ tự giả định theo ảnh gốc: Vũ khí, Mũ, Giáp, Găng / Giày, Khiên, Trang sức, Trang sức | con trỏ ô; OK trên ô có đồ → menu 2 mục Trang bị/Tháo (`Process`: `ClearEquipItem`); ô trống/Trang bị → `OpenEquipFilter` = chế độ con Túi đồ lọc | chạm ô; chạm lần 2 = OK; menu = 2 nút |
| Kỹ năng (4) | 5 tab con (Tất cả/Đột Kích/Đặc tính/Tinh Linh/Thường), lưới 4 cột × 4 hàng thấy được, cuộn theo hàng (skill_00 có 43 kỹ năng = 11 hàng) | con trỏ ô + hàng cuộn (`KeySkillMenu` ghi `+0xe..+0x12`); trạng thái con 2,3,7–10 (gán ô nhanh, sách kỹ năng, hợp thành…) | chạm ô; chạm lần 2 hoặc nút "Gán phím tắt" = OK; kéo dọc cuộn (mới) |
| Đặc tính (5) | tab nhánh 4 (3 nhánh theo lớp + "Điểm"), mỗi nhánh lưới 3 cột × 4 hàng (`DrawPropertyLine` ×4, hàng cần 0/4/8/12 điểm), cuộn dọc | con trỏ (hàng, cột); OK = đầu tư 1 điểm | chạm nút; nút "Đầu tư" = OK |
| Liên kích (6) | 4 ô chuỗi (Hạng 1–4) + dãy ứng viên 5/trang cuộn ngang (`DrawComboMenu`: vòng 4, 5) | ô liên kích chọn `[this+0xe]`, con trỏ ứng viên; OK = `HERO::SetComboSkillBelt` | chạm ô chuỗi → chạm ứng viên; mũi tên = TRÁI/PHẢI; "Bỏ chọn" = phím 0 |
| Tinh linh (7) | 2 tab con: Cơ bản/Trang bị (2 ô trang bị Tinh linh) | Kỹ năng Tinh Linh: 4 kỹ năng chọn bằng 4 phím hướng (ct 49) | chạm ô; "Sử dụng" = OK |
| Nhiệm vụ (8) | 2 tab con: Nhiệm vụ hiện tại (danh sách dọc + chi tiết) / Nhánh nhiệm vụ (cây dọc) | con trỏ nhiệm vụ (`KeyQuestMenu` ghi `+0xd..+0x14`) | chạm hàng; OK = theo dõi |
| Hệ thống (9) | 4 nút dọc: Lưu, Cài đặt, Trợ giúp, Màn hình chính; Cài đặt 6 hàng (Nhạc nền, Âm hiệu ứng, Tốc độ game, Rung, Hiệu ứng, Tự ngắm); Trợ giúp 20 tiêu đề + nội dung | con trỏ; trái/phải đổi giá trị; popup xác nhận → `Process` (`HERO::SaveAll`…) | chạm nút/hàng; mũi tên ◀▶ = TRÁI/PHẢI |
| **Túi đồ SubState** | `GetSubState()` trong `DrawBagMenu`: **0** lưới 4×4 (T17b1); **1** menu hành động trên vật phẩm (Sử dụng / Gán phím tắt / Vứt bỏ / Phân giải); **2** chọn ô nhanh (`DrawSkillBelt`, con trỏ `[this+0x10]`, 5 ô); **≥3** chế độ lọc: 1 hàng **5 vật phẩm/trang** (`DrawFilterItemInfo`, bước 29 px gốc), `[this+0x10]` = chỉ số tuyệt đối trong danh sách đã lọc, cuộn ngang `DrawScrollHor` (tổng = trường `BagItem+872`); vào bằng `OpenBagFilter/OpenEquipFilter/OpenComposeFilter/OpenSpiritEquipFilter` | | chạm vật phẩm = đặt `[this+0x10]`; mũi tên = TRÁI/PHẢI |

### 11.2 Widget mới (cùng phong cách viền cam/đen, cùng `metrics`)
| widget | số đo |
|---|---|
| Tab con (chữ) | cao `subH = px + 2*(bt+3) + 2`; `n` ô cách nhau 2 px, `tw = (cw-(n-1)*2)//n`, chữ tối đa `tw-2*(bt+3)-2`; chọn = nền cam dọc + viền cam, không chọn = viền xám xanh. Vùng chạm = hình chữ nhật ô |
| Nút mũi tên | vuông (`cell` ở dãy ứng viên/chọn; `rowH+2s` ở hàng tuỳ chọn); tam giác cam vẽ nguyên thuỷ (cạnh `max(3, size//4)`) |
| Nút +/− | vuông `ah = rowH + s` (+ giãn thêm ở khung lớn, tối đa `4s`), dấu vẽ bằng khối dày `max(1,s)`; vô hiệu = viền xám |
| Hàng cộng điểm | cao `ah`, nhãn trái, giá trị phải (vàng; hàng chọn +điểm tạm màu xanh lá), 2 nút +/− sát phải |
| Cột tóm tắt nhân vật/Tinh linh | khung xám xanh; vòng chân dung `22s+20` (SD) / 84 (HD) + tên/LV/lớp bên phải (bước `lp`), 3 thanh HP/SP/EXP (bước `barH+pad-1`) |
| Hàng chỉ số k–v | nhãn mờ trái, giá trị phải, vạch mảnh (40,54,76) dưới, bước `slp+2` (font nhỏ) hoặc `lp+2s-1` (font thân) |
| Ô kỹ năng/nút đặc tính | `w_cell` (icon `icon.mgr` 0–188 ×s) + chấm cấp `max(2,s+1)` px (5 chấm; sáng = đã học) ở đáy ô; chưa học = phủ tối α150 |
| Hộp thông tin (`w_info`) | hộp mô tả T17b1 + (tuỳ chọn) hàng k–v font nhỏ + dòng phụ; mô tả có mã màu: `}` vàng, `{` xanh, `|` hết màu (GIẢ ĐỊNH màu), `#NN` = số giữ chỗ ("100") hoặc gạch đầu dòng khi theo sau là chữ; tràn → thanh cuộn dọc (kéo dọc = `TouchMoved` mới) |
| Thanh cuộn ngang | cao `sbw`, rãnh + con trượt cam, rộng con trượt `max(2s+6, track*view/total)` |
| Popup menu hành động | rộng `round(0.55W)` (SD) / 420 (HD), kẹp `W-2mg`; tiêu đề tên vật phẩm ≤2 dòng (màu phẩm chất); mỗi lựa chọn 1 nút `btnH` cách `gap`; chọn = cam, vô hiệu = xám; nuốt chạm ngoài |
| Popup gán ô nhanh | hàng ô nhanh = 5 ô + nút đổi bộ (ảnh `ui_new/07_swap.png` thu LANCZOS về `cell`×`cell` = 30/30/50/70, cách ô cuối `gap`, nhãn `1/2`..`2/2` font nhỏ ngay DƯỚI nút (không đè icon; hit `belt_swap` gồm cả nhãn); hit `belt_swap`); rộng `max(belt_row + 2*(pad+2s+4), 0.55W)` với `belt_row = 5*cell+4*cg+gap+cell`; tên kỹ năng, nhãn, hàng ô nhanh, nút Huỷ |
| Popup chi tiết vật phẩm | rộng `round(0.78W)` (SD) / 640 (HD), cao `H-2mg` (HD −40); hộp thông tin đầy đủ + OK |
'''

def write(out, hits, SCREENS, metrics, FRAMES):
    names = [n for n, _, _ in SCREENS]
    lines = [TEXT]
    lines.append('\n### 11.3 Vùng chạm từng màn (đại diện: phần tử đầu mỗi nhóm; còn lại trong `hit_rects.json`)\n')
    lines.append('Định dạng `[x0,y0,x1,y1)` theo khung logic, cột = 400x240 / 600x270 / 800x360 / 1200x540. Gốc chung của mọi màn: nút `close` (quay lại) + 9 `tab0..tab8` (đổi tab = `SetState(i)` gốc, như T17b1).\n')
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
            if g in ('close', 'tab', 'popup_modal') and n not in ('nhan_vat',): continue
            row = [d[(W, H)][0] for (W, H) in FRAMES if (W, H) in d]
            if not row: continue
            cnt = len(d.get((400, 240), []))
            cells = [('`%s`' % ','.join(map(str, d[(W, H)][0]['rect']))) if (W, H) in d else '-' for (W, H) in FRAMES]
            lines.append('| %s (%d) | %s | %s |' % (g, cnt, ' | '.join(cells), row[0]['action'].replace('|', '/')))
    lines.append(r'''
### 11.4 Popup đặc thù InGameMenu
`SetPopupState` 0xe31b8 có 25 loại, nhưng bảng nhảy chỉ trỏ tới ~14 nhánh dựng chuỗi (`GetCommonText` + `MC_knlSprintk`): thực chất phần lớn là thông báo/xác nhận (popup chung T17b1: Có/Không, OK). Khác popup chung có 3 dạng, đã mockup: (1) **menu hành động** (`tui_do_menu`, `trang_bi_menu`: Sử dụng/Gán phím tắt/Vứt bỏ/Phân giải; Trang bị/Tháo), (2) **chi tiết vật phẩm** có `ItemInfo*` (`popup_chi_tiet`, thay `DrawAutoItemInfoBox` 156 px cố định), (3) **chọn lớp** (`popup_chon_lop`, `ChangeHeroClass`, danh sách `class.dat`); thêm popup gán ô nhanh (`ky_nang_phim_tat`). Popup xác nhận Lưu/Màn hình chính (`he_thong_popup_luu`) và OK dài nhất (`popup_ok_dai`) dùng widget chung. Việc gán chính xác từng loại trong 25 → widget làm ở T17c1 (đọc từng nhánh `SetPopupState`).

### 11.5 Lệch khỏi lưới gốc / giả định / chỗ cần xác nhận khi implement
1. **Không đổi lưới logic** của tab nào (số ô/hàng/cột/trang giữ như bảng 11.1). Cái mới chỉ là giao diện: nút +/− (thay trái/phải), nút Chi tiết/Cơ bản/Huỷ (thay `#`/`*`), nút Sắp xếp/OK/Huỷ, mũi tên cuộn, kéo dọc cuộn mô tả, thanh cuộn ngang.
2. Thứ tự 8 ô trang bị (Vũ khí, Mũ, Giáp, Găng / Giày, Khiên, Trang sức ×2) và nhãn 5 tab con Kỹ năng suy từ ảnh gốc + `common_text`; icon kỹ năng = `icon.mgr[chỉ số kỹ năng]` (ảnh mẫu; khi implement dùng đúng hàm icon của `HeroSkillInfo`).
3. Số trang/hàng cuộn của Kỹ năng/Đặc tính/Nhiệm vụ/Trợ giúp lấy từ dữ liệu (skill_00 = 43 kỹ năng → 11 hàng; quest_0 = 151; help = 20 tiêu đề); số dòng cuộn tối đa kiểm ở T17c.
4. Màu mã `}` `{` trong mô tả kỹ năng và ý nghĩa `#NN` là giả định (xem 11.2); tra `BFont::DrawText`.
5. Bộ ô nhanh: 5 ô/bộ × 2 bộ (`GetHotKeyBeltType` lặp 5 × 2). Mockup vẽ 1 bộ + nút đổi bộ (ảnh `ui_new/07_swap.png`, nhãn `bộ/2`, hit `belt_swap`) ở Túi đồ chọn ô nhanh (SubState 2) và Kỹ năng gán phím tắt. Hàm/phím đổi bộ gốc (vd `HERO::ChangeHotKeyToggle`?) T17c xác nhận.
6. Chân dung Tinh linh lấy `icon.mgr[172]` làm ảnh mẫu; thanh "SP Tinh linh" ở tab Kỹ năng Tinh Linh là nhãn mẫu (năng lượng Tinh linh 5 nấc).
''')
    p = os.path.join(out, 'LAYOUT.md'); t = open(p, encoding='utf-8').read()
    if MARK in t: t = t[:t.index(MARK)]
    if not t.endswith('\n'): t += '\n'
    open(p, 'w', encoding='utf-8').write(t + MARK + '\n' + '\n'.join(lines))
