# LAYOUT.md: menu mới (T17b1) — số đo theo KHUNG LOGIC

> Sinh tự động bởi `mock_menu.py` (hàm `metrics(W,H)` + `bag_geometry`): số trong file này = số dùng để vẽ ảnh mẫu. Mọi toạ độ là **điểm ảnh của khung logic** (W×H = màn ÷ k, §8 PLAN), gốc (0,0) góc trên trái, y xuống. Không có số thực: mọi phép chia là chia nguyên (`//`), `round` chỉ ở 3 chỗ ghi rõ.
> Tái lập: `python3 -I mock_menu.py` (cần `$WORK/vi_android` đã build). Ảnh: `menu3x3_*`, `tui_do_*`, `tui_do_mo_ta_*`, `popup_xac_nhan_*`, `widgets_*`, `tong_hop*.png`; `hit_rects.json` = vùng chạm từng phần tử (400x240 … 1200x540), `verify.txt` = kiểm không tràn trên toàn bộ chuỗi thật.

## 1. Khung logic và công thức co giãn W×H
Menu mới thay cho MỌI cỡ khung (quyết định 12). Bốn mốc chuẩn: **400x240 → 600x270 → 800x360 → 1200x540** (= màn 2400x1080 ÷6, ÷4, ÷3, ÷2). Mọi số đo là hàm của (W,H) qua 3 đại lượng gốc:
```
s   = clamp(H // 180, 1, 3)        # hệ số icon NEAREST nguyên: 240→1, 270→1, 360→2, 540→3
HD  = (H >= 480)                    # khớp ngưỡng chọn font của guest (§11, vn_sel)
px  = 18 nếu HD, ngược lại 15       # font thân (bộ f18.bin / font.bin)
spx = 15 nếu HD, ngược lại 10       # font nhỏ (font.bin / font10.bin)
lp  = px + 2   slp = spx + 2        # bước dòng thân / nhỏ
mg  = 8*s                           # lề giữa mép khung và cửa sổ
winH = H - 2*mg ;  winW = min(W - 2*mg, round(1.72*winH)) ;  wx = (W-winW)//2 ;  wy = (H-winH)//2
pad = 3*s+3   gap = 2*s+2   bt = s (dày viền cam)   rad = 2*s+1 (bán kính góc cắt, pixel-art)
titleH = 6*s+14      tabH = 12*s+12      btnH = lp + 6*s      rowH = lp + 4*s
cell = 20*s+10       cg = 2*s            pitch = cell + cg    gridW = 4*pitch - cg   # ô vật phẩm 4 cột
barH = spx + 3       sbw = 3*s+1 (thanh cuộn)      kb = max(1,(titleH-4)//13) (hệ số icon nút "<")
```
Lý do chọn: `s` bám đúng bậc của khung (400/600 → icon ×1 vì khung chỉ cao 240–270; 800x360 ×2; 1200x540 ×3). **×3 ở HD** (thay vì ×2): icon 20x20 ×3 = 60 px khớp độ dày pixel-art của cảnh game ở 1200x540 (cảnh là ×1 của map 16 px/ô), ô vật phẩm 70x70 lấp đủ chiều cao cửa sổ 492 px (lưới 4×4 cao 298), còn ×2 chỉ cao 210 và để trống ~40% cửa sổ. Chữ vẫn là 18 px thật (đúng yêu cầu), nên chữ trông nhỏ hơn icon, đó là ý đồ (chữ 18 px = cỡ ghi trong PLAN §7d).

| tham số | 400x240 | 600x270 | 800x360 | 1200x540 |
|---|---|---|---|---|
| s (icon ×) | 1 | 1 | 2 | 3 |
| font thân / nhỏ | 15 / 10 | 15 / 10 | 15 / 10 | 18 / 15 |
| bước dòng lp / slp | 17 / 12 | 17 / 12 | 17 / 12 | 20 / 17 |
| lề mg | 8 | 8 | 16 | 24 |
| cửa sổ winW×winH | 384×224 | 437×254 | 564×328 | 846×492 |
| góc cửa sổ (wx,wy) | (8,8) | (81,8) | (118,16) | (177,24) |
| pad / gap / bt / rad | 6 / 4 / 1 / 3 | 6 / 4 / 1 / 3 | 9 / 6 / 2 / 5 | 12 / 8 / 3 / 7 |
| titleH / tabH | 20 / 24 | 20 / 24 | 26 / 36 | 32 / 48 |
| btnH / rowH | 23 / 21 | 23 / 21 | 29 / 25 | 38 / 32 |
| cell / pitch / gridW | 30 / 32 / 126 | 30 / 32 / 126 | 50 / 54 / 212 | 70 / 76 / 298 |
| barH / sbw / kb | 13 / 4 / 1 | 13 / 4 / 1 | 13 / 7 / 1 | 18 / 10 / 2 |

Màn thật: khung logic phóng NEAREST ×k nguyên (k = chọn trong Cài đặt, §8), căn giữa. Menu không tự phóng gì thêm: mọi số ở đây đã là logic. Touch đến guest đã ở toạ độ logic (`cg+0xf4/0xf8`, BAO-CAO §13.2) nên vùng chạm = chính hình chữ nhật vẽ.

## 2. Chữ và dữ liệu
- Font: `vi.bin` có 3 bộ (15, 10, 18). Thân = `px`, phụ = `spx` (số lượng vật phẩm, số trên thanh HP/SP/EXP, hàng chỉ số trong hộp chi tiết, nhãn nhỏ). Căn dọc: vẽ ô chữ cao đúng `px` tại y (glyph đã có baseline sẵn). Khoảng trắng mode 0 = 4 px (`spaceW` của vi.bin) cho cả ba bộ; mode 1/2 = 3 px.
- **Menu mới LUÔN vẽ chữ mode 0** (cờ mới `vn_state.force0`): từ điển Việt hiện bake mode 2 (10 px nén, hoặc 15 px nén ở HD) vào nhãn các ô hẹp (9 tên mục, v.v.). Menu mới có chỗ rộng nên bỏ nén: cần engine `vn_sel` bỏ qua `mode` của từ khi cờ bật (một biến, ~6 lệnh). Mọi bề rộng trong tài liệu này đo ở mode 0. (Hệ quả: không cần dịch/encode lại chuỗi.)
- Xuống dòng: `;` trong dữ liệu coi như dấu cách rồi ngắt theo bề rộng (đo bằng `BFont::GetWidth`); từ dài hơn hộp thì ngắt theo ký tự. Hộp chữ cố định bề rộng: **không** dùng xuống dòng cứng của dữ liệu (nó ngắt cho hộp 156 px cũ).
- Gợi ý phím đầu chuỗi (`0.Sắp xếp`, `1.Đồng ý`, `#Chi tiết`, `*Huỷ`) bị bỏ khi vẽ (UI cảm ứng): bỏ `[0-9].` hoặc 1 ký tự `#`/`*` ở đầu.
- Mã phẩm chất ở tên vật phẩm: ký tự đầu `&`(hiếm) `}`(sử thi) `` ` ``(anh hùng) `^`(huyền thoại) `<`(bộ) = màu tên + vạch màu 1·s px trên cùng ô; `{`/`|` cuối = hết màu → không vẽ. **Màu RGB trong ảnh là giả định** (`&`=90,175,255; `}`=205,130,255; `` ` ``=255,150,55; `^`=255,90,80; `<`=100,225,120); khi implement tra đúng bảng màu của `BFont::DrawText`.
- Chuỗi dùng (đều lấy từ dữ liệu đã build, `common_text.dat`): 9 tên mục rec 0–8; "Tiền hiện có" 19; "Sắp xếp" 22; "Giới hạn cấp" 221; "ATK" 228; "Tốc độ đánh" 225 + "Nhanh" 227; "Đã trang bị." 120; "Tuỳ chọn ngẫu nhiên" 287; HP/SP/EXP/LV 10–13; Có/Không 50/51; OK 56; câu xác nhận vứt 58; popup dài nhất 273. Tên vật phẩm / mô tả: `item_*.dat` (tên ở offset 2). Tên khu vực: `ingame_text.dat` rec 0–40 (gộp `;`; dài nhất "Đảo Coldcreek - Trung Tâm").

## 3. Sprite/icon dùng (sprite THẬT của game, ×s NEAREST)
- `img/ui.mgr`: icon 9 tab idx **7 người, 8 túi, 9 khiên, 10 lửa, 11 sao, 12 xoáy, 13 sao băng, 14 người chạy, 15 bánh răng** (thứ tự = 9 mục menu); 25 = mũi tên cam "<" (nút quay lại, 15×13); 16 = đồng xu vàng (11 px, tiền); 1 = rương nâu (24×22, nhãn trang túi). Icon trắng 1 màu: tô lại (dim 150,164,184 khi tab chưa chọn; trắng khi chọn).
- `img/icon.mgr` 20×20: vật phẩm (idx 200–239 trong ảnh: 203 súng/thương, 205 mũ, 206 giáp, 207 găng, 208 giày, 209 khiên, 210 nhẫn, 211 dây chuyền, 223 rương, 224 cuộn, 225 sách, 226–229 bình, 230 orb, 231 quặng). Trong game lấy icon qua `ItemInfo::DrawIcon` (không cần biết idx): widget chỉ cần gọi hàm gốc với (x,y) và ép hệ số ×s — **cần 1 thunk vẽ ×s** (DrawIcon gốc vẽ 1×): vẽ vào buffer nhỏ rồi phóng NEAREST, hoặc đọc pixel sprite trực tiếp.
- Chân dung vòng cam ở menu 3x3 dùng `ui_new/03_frame_empty.png` (116×116, nghệ thuật độ phân giải cao): ảnh mẫu thu bằng LANCZOS về `22·s+20` (SD) / 84 (HD). Khi implement: nhúng sprite đã thu sẵn đúng 2 cỡ (42 và 84) vào VFS, hoặc vẽ bằng nguyên thủy (3 vòng tròn đồng tâm). Sprite nhân vật trong vòng = `CHAR::GetSpritePtr` (mẫu chỉ đặt icon "người").

## 4. Bảng màu
| tên | RGBA | dùng |
|---|---|---|
| ink | 0,0,0 | viền ngoài 1 px, vạch phân cách |
| navy | 13,19,30 α240 | nền cửa sổ |
| panel/cell | 30,46,68 → 24,37,56 (dọc) | nền ô, hàng |
| orange | 240,138,22 | viền cam bt px, ô chọn |
| orange chọn | 252,168,40 → 200,98,12 (dọc) | nền ô/tab/nút được chọn |
| orange_in | 120,64,16 | viền trong 1 px |
| title bar | 252,176,44 → 206,104,12 | thanh tiêu đề (dọc), viền đen dưới 1 px |
| chữ | 246,240,228 | chữ thân |
| chữ mờ | 150,164,184 | nhãn phụ, tab chưa chọn |
| vàng | 255,214,90 | tiền, tên nhân vật |
| xanh lá | 104,220,112 | "Đã trang bị." |
| xanh dương | 60,140,236 | thuộc tính cộng thêm |
| HP/SP/EXP | 214,52,48 / 52,124,226 / 226,188,48 | thanh (sáng +40 ở đỉnh) |
| nền tối cảnh | đen α150; popup thêm đen α120 | FillRectAlpha lên bản chụp `CopyFrameBuffer` |

## 5. Widget (kích thước theo khung logic)
Viền khung (hàm `frame`): hình chữ nhật góc cắt bán kính `rad`: **lớp 1** đen 1 px ngoài cùng; **lớp 2** cam `bt` px; **lớp 3** cam tối (120,64,16) 1 px; phần trong tô nền. Tổng dày `bt+2`. Cửa sổ/popup/nút/ô tab chọn dùng viền cam; hộp mô tả, ô thường, cột tóm tắt dùng viền xám xanh (70,92,120) 1 px.

| widget | kích thước / cấu tạo |
|---|---|
| Cửa sổ | `winW×winH` căn giữa; trong: thanh tiêu đề cao `titleH`; (tuỳ màn) thanh tab dưới cao `tabH`; vùng nội dung = giữa hai thanh, lề `pad` |
| Tiêu đề | dải cam dọc cao `titleH`, chữ `px` trắng + bóng đen (+1,+1) căn giữa; tối đa `winW - 2*(titleH+pad)` px |
| Nút quay lại "<" | nằm trong thanh tiêu đề, trái, rộng `titleH+4s-2`, cao `titleH-2`, nền (120,56,8) + icon ui 25 ×`kb`. Chạm → `KeyPressed(back)` gốc. Vùng chạm: `(x0, y0, x0 + titleH+4s-2+2s, y0+titleH)` |
| Thanh tab | cao `tabH`, 9 ô đều nhau `tw = innerW/9` (chia nguyên, ô i từ `round(i*tw)`), icon ui ×s căn giữa; ô chọn nền cam dọc, cách mép trên `2+s`; không chữ (tên mục nằm ở tiêu đề). Chạm ô i → đổi tab (`SetState(i)` gốc) |
| Nút | cao `btnH`, rộng tuỳ chỗ, chữ `px` căn giữa (tối đa `w-2*pad`). 4 trạng thái: thường (nền xanh 38,54,78→22,32,48), chính (cam), nhấn (cam tối 150,78,12→110,52,8, chữ vàng), vô hiệu (viền xám, chữ 88,98,112) |
| Ô lưới vật phẩm | vuông `cell`; icon 20×s căn giữa; vạch phẩm chất cao `max(1,s)` trên cùng; cờ trang bị vuông `5s+1` vàng viền đen ở góc trái trên (dưới vạch); số lượng font `spx` góc phải dưới cách mép 2 px (hiện khi >1); ô chọn: nền cam + viền cam sáng (255,192,56) 1 px + viền đen ngoài 1 px |
| Hàng danh sách | cao `rowH`, [icon] nhãn trái + giá trị phải (vàng), chọn = nền cam |
| Thanh cuộn | rộng `sbw`, rãnh (10,15,24) viền trái đen; con trượt cam, cao `max(2s+6, trackH*view/total)`, y = `track + (trackH-thumbH)*pos/(total-view)` |
| Thanh HP/SP/EXP | nhãn `px` rộng `sw(px,"EXP")+2s+2`; thanh cao `barH` viền đen 1; fill dọc sáng→đậm; số "cur/max" font `spx` giữa thanh (bóng đen chỉ khi HD) |
| Hộp mô tả | viền xám 1 px, nền (10,16,26 α235); tên (tối đa 2 dòng `px`, màu phẩm chất, bóng) → dòng phụ `spx` mờ → vạch ngang (70,92,120) → nội dung; chữ rộng `boxW - 2*(pad//2+1) - sbw - 6`; tràn → thanh cuộn (kéo dọc = `TouchMoved` mới; nội dung dài hơn hộp chỉ xảy ra với mô tả dài) |
| Popup chung | rộng `pw = round(0.70*W)` (SD: 280/420/560) hoặc 480 (HD), kẹp `W-2*mg`; chữ tối đa `pw - 2*(pad+2s+4)`, căn giữa theo dòng; cao `2*pad + 4s + n*lp + pad + btnH + 2` (n = số dòng); nút `btnH` dưới cùng; Có/Không chia đôi (cách nhau `pad`), OK rộng `max(sw(OK)+4*pad, innerW/3)`; nền màn tối thêm α120; chạm ngoài hộp bị nuốt (không đóng). Mọi popup (§13 quyết định 4) dùng widget này |

## 6. Màn mẫu 1: Menu 3x3 (`menu3x3_*`)
Lưới logic GỐC giữ nguyên: 9 mục, 3 cột × 3 hàng, con trỏ `i = 0..8` (hàng-chính: `i = r*3+c`), thứ tự Nhân vật, Túi đồ, Trang bị / Kỹ năng, Đặc tính, Liên kích / Tinh Linh, Nhiệm vụ, Hệ thống (§13.3 tab 0). Chạm ô → đặt con trỏ = i rồi `KeyPressed(-5)` gốc (→ `SetState(tab)`).
- Thanh tiêu đề: tên khu vực (chuỗi `ingame_text`) thay "Menu"; không có thanh tab (menu 3x3 là điểm vào).
- Nội dung: `x0 = wx+bt+2+pad ... x1`, `cw = x1-x0`, `ch = y1-y0`. Cột tóm tắt trái rộng `sumw = round(cw*0.36)`, cách lưới `g = gap+2`. Lưới: `tw = (cw-sumw-g-2g)//3`, `th = (ch-2g)//3`, ô (c,r) tại `gx + c*(tw+g)`, `y0 + r*(th+g)` (dư chia chia đều hai bên). Ô: viền cam nếu chọn (nền cam dọc), nếu không viền xám xanh; icon ui ×`min(s+1,3)` căn giữa phần trên; nhãn `px` cách đáy `2s+1`, căn giữa, tối đa `tw-2s-2`.
- Cột tóm tắt: vòng chân dung `22s+20` (SD) / 84 (HD) + tên (vàng) / lớp / "LV n" xếp dọc cạnh vòng (bước `lp`), 3 thanh HP/SP/EXP (bước `barH+pad-1`), vạch, "Tiền hiện có", dòng [xu + số tiền căn phải].

| khung | x0,y0 | cw×ch | sumw | ô tw×th |
|---|---|---|---|---|
| 400x240 | 17,37 | 366×186 | 132 | 72×58 |
| 600x270 | 90,37 | 419×216 | 151 | 83×68 |
| 800x360 | 131,55 | 538×276 | 194 | 106×86 |
| 1200x540 | 194,73 | 812×426 | 292 | 163×135 |

Vùng chạm: 9 ô lưới (hình chữ nhật ô); nút "<". Cột tóm tắt không chạm được.

## 7. Màn mẫu 2: Túi đồ (`tui_do_*`, `tui_do_mo_ta_*`)
**Lưới logic gốc (đọc từ `DrawBagMenu`/`KeyBagMenu`, §13.3 tab 2): 4 cột × 4 hàng = 16 ô mỗi trang; số trang = `BagItem::GetSize()/16`; `[this+0xe]` = trang, `[this+0xf]` = ô trong trang 0..15 (vật phẩm chọn = `BagItem::GetBagItemPtr([this+0xe], [this+0xf])`, chỉ số tuyệt đối `[+0xf] + 16*[+0xe]`, như `DrawBagMenu` 0xf7ca4..0xf7ce8). Cột trang bên trái: mỗi trang 1 ô (gốc cách 24 px, icon rương `ui` idx 1).** Mẫu vẽ 3 trang (kích thước túi tối đa do người triển khai kiểm; cột trang cao `pitch` mỗi ô, tối đa 4 ô thẳng hàng với 4 hàng lưới; nếu >4 trang: chia `pageH = (gridH)//n`, cell giữ).
- Chế độ con khác (gốc: `GetSubState()>1` vẽ 1 HÀNG 5 ô, `[this+0x10]` = con trỏ, nhóm theo 5: có vẻ là chọn ô phím tắt/ghép) **chưa mockup trong b1**; sẽ bàn ở T17b2/T17c (không đổi lưới 5 ô).
- Bố cục: dải tiền cao `lp+2s+2` (nhãn "Tiền hiện có" trái; [xu + số] phải; vạch cam tối dưới) → hàng 3 cột: cột trang (rộng `pitch`) | lưới (`gridW`) | hộp chi tiết (phần còn lại); các khoảng cách `gap+1`.

| khung | lưới (x,y) | cột trang x | hộp chi tiết (x0,y0,x1,y1) | nút Sắp xếp |
|---|---|---|---|---|
| 400x240 | (51,59) 126×126 | 14 | (182,59,386,202) | cao 23, đáy = y1 |
| 600x270 | (124,59) 126×126 | 87 | (255,59,512,232) | cao 23, đáy = y1 |
| 800x360 | (188,80) 212×212 | 127 | (407,80,673,299) | cao 29, đáy = y1 |
| 1200x540 | (274,104) 298×298 | 189 | (581,104,1011,456) | cao 38, đáy = y1 |

- Ô (c, r) = `(gx + c*pitch, gy + r*pitch)`, kích thước `cell`. Trang i: `(pgx, gy + i*pitch, pgx+pitch, gy+i*pitch+cell)`.
- Hộp chi tiết (viền xám): trên = **tên** (tối đa 2 dòng; đo: dài nhất 1360 vật phẩm vừa 1 dòng ở cả 4 khung, xem `verify.txt`) → dòng loại (spx, mờ) → vạch → 3 hàng chỉ số `spx` (nhãn mờ trái, giá trị phải) → "Đã trang bị." (xanh lá) → tuỳ chọn (xanh dương). Vật phẩm có mô tả (không phải trang bị): tên → vạch → mô tả cuộn (`tui_do_mo_ta_*`). Nút "Sắp xếp" cao `btnH` ở đáy cột chi tiết (thay phím `0`: cùng `KeyBagMenu(0x30)`).
- Vùng chạm: mỗi ô (chạm = đặt `[this+0xf]` rồi `KeyPressed(-5)`; chạm lại ô đang chọn = OK gốc → mở popup dùng/vứt như gốc), mỗi ô trang (đặt `[this+0xe]`, `[this+0xf]=0`), nút Sắp xếp, 9 tab, nút "<". Kéo dọc trong hộp mô tả = cuộn (mới).

## 8. Màn mẫu 3: popup xác nhận (`popup_xac_nhan_*`)
Widget popup chung (mục 5) đặt trên Túi đồ với câu 58 "Bạn có muốn vứt bỏ vật phẩm?" và Có/Không. Giữ `SetPopupState`/`Process` gốc: Có/Không chạm = đặt con trỏ popup rồi `KeyPressed(-5)`.
| khung | popup w×h (câu 58) | popup (x,y) |
|---|---|---|
| 400x240 | 280×64 (1 dòng) | (60,88) |
| 600x270 | 420×64 (1 dòng) | (90,103) |
| 800x360 | 560×83 (1 dòng) | (120,138) |
| 1200x540 | 480×108 (1 dòng) | (360,216) |

Chuỗi popup dài nhất (`common_text` 273/305/…) tối đa 3 dòng ở 400x240 (chữ 256 px) → popup cao ≤ 98 ≤ khung: xem `verify.txt`.

## 9. Vùng chạm (xuất đầy đủ ở `hit_rects.json`)
Hình chữ nhật `[x0,y0,x1,y1)` theo khung logic, khóa `<màn>_<WxH>` → danh sách {id, rect, action}. Quy ước: vùng chạm = chính hình chữ nhật vẽ (ô, tab, nút); không nở thêm vì khoảng cách giữa các phần tử đã ≥ `cg` = 2s. Kích thước chạm nhỏ nhất thực tế (màn 2400x1080): ô vật phẩm 30 px logic × 6 = 180 px ở 400x240, 70 × 2 = 140 px ở 1200x540 — đều lớn hơn 48 dp; nhỏ nhất là thanh tab: cao `tabH`. Phần tử cuộn bằng chạm: hộp mô tả (kéo dọc).

Ví dụ (400x240, Túi đồ): close=(11, 11, 36, 31); tab1=(53, 205, 95, 229); page0=(17, 62, 49, 92); cell0=(54, 62, 84, 92); cell15=(150, 158, 180, 188); sort=(185, 176, 383, 199); detail=(185, 62, 383, 172)

## 10. Lệch khỏi lưới gốc / giả định cần quản đốc duyệt
1. **Không đổi lưới logic**: menu 3x3 (9 mục), Túi đồ (4 cột × 4 hàng × số trang), con trỏ gốc giữ nguyên.
2. Thêm UI mới (không đổi logic): nút "Sắp xếp" bấm được thay phím `0`; cuộn mô tả bằng kéo dọc (cần `TouchMoved` mới; `KeyPressed` gốc giữ nguyên); popup nuốt chạm ngoài.
3. Giả định cần xác nhận: số trang túi hiển thị 3 (theo ảnh kiểm kê, `GetSize()=48`); màu 5 mã phẩm chất; cờ `force0` trong engine; thunk vẽ icon ×s; chân dung vòng cam là sprite nhúng mới.
4. 600x270 và 800x360 là kiểm co giãn bằng công thức (ảnh trong `trung_gian/` và `tong_hop.png`), không phải mốc thiết kế riêng.
<!-- B2A -->

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


### 11.3 Vùng chạm từng màn (đại diện: phần tử đầu mỗi nhóm; còn lại trong `hit_rects.json`)

Định dạng `[x0,y0,x1,y1)` theo khung logic, cột = 400x240 / 600x270 / 800x360 / 1200x540. Gốc chung của mọi màn: nút `close` (quay lại) + 9 `tab0..tab8` (đổi tab = `SetState(i)` gốc, như T17b1).


**nhan_vat** (`nhan_vat`): Nhân vật: cộng điểm (STR/AGI/VIT/INT), tóm tắt

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| tab (9) | `11,205,53,229` | `84,235,132,259` | `122,304,184,340` | `182,463,275,511` | đổi tab: SetState(0) |
| page_toggle (1) | `23,170,87,193` | `96,200,171,223` | `140,257,234,286` | `206,401,350,439` | phím # -> KeyCharacterMenu: đổi trang Chi tiết/Cơ bản |
| cancel (1) | `93,170,157,193` | `177,200,252,223` | `243,257,337,286` | `363,401,507,439` | phím * -> StateInGameMenu::CancelApplayStat |
| minus (4) | `335,56,358,79` | `455,56,481,82` | `595,74,630,109` | `906,95,953,142` | đặt con trỏ=0 + KeyPressed(TRÁI) (giảm điểm tạm) |
| plus (4) | `360,56,383,79` | `483,56,509,82` | `634,74,669,109` | `959,95,1006,142` | đặt con trỏ=0 + KeyPressed(PHẢI) (tăng điểm, tiêu 1 điểm) |
| stat (4) | `169,56,335,79` | `264,56,455,82` | `354,74,595,109` | `529,95,906,142` | đặt con trỏ hàng [this+0xe]=0 |

**nhan_vat_co_ban** (`nhan_vat_co_ban`): Nhân vật: trang chi tiết chỉ số (13 hàng)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| page_toggle (1) | `17,176,197,199` | `90,206,296,229` | `131,266,396,295` | `194,413,595,451` | phím # -> đổi trang Cơ bản/Chi tiết |

**trang_bi** (`trang_bi`): Trang bị: 8 ô 4x2 + chi tiết + chỉ số

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| slot (8) | `72,34,102,64` | `145,34,175,64` | `215,51,265,101` | `293,68,363,138` | đặt con trỏ ô trang bị=0 rồi KeyPressed(-5) (ô có đồ: mở menu Trang bị/Tháo; ô trống: OpenEquipFilter) |
| detail (1) | `203,34,386,145` | `276,34,512,145` | `434,51,673,163` | `600,68,1011,214` | chỉ hiển thị |

**trang_bi_menu** (`trang_bi_menu`): Trang bị: menu Trang bị/Tháo (popup đặc thù)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| slot (8) | `72,34,102,64` | `145,34,175,64` | `215,51,265,101` | `293,68,363,138` | đặt con trỏ ô trang bị=0 rồi KeyPressed(-5) (ô có đồ: mở menu Trang bị/Tháo; ô trống: OpenEquipFilter) |
| detail (1) | `203,34,386,145` | `276,34,512,145` | `434,51,673,163` | `600,68,1011,214` | chỉ hiển thị |
| menu (2) | `101,108,299,131` | `146,123,454,146` | `197,164,603,193` | `413,248,787,286` | đặt con trỏ menu=0 rồi KeyPressed(-5) (SubState 1 / SetPopupState gốc) |

**tui_do_chon** (`tui_do_chon`): Chế độ con Túi đồ SubState>=3: chọn vật phẩm lọc (5/trang, cuộn ngang)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| prev (1) | `87,57,117,87` | `186,57,216,87` | `211,76,261,126` | `335,98,405,168` | KeyPressed(TRÁI): lùi 1 vật phẩm ([this+0x10]-1), qua trang khi hết 5 |
| cand (5) | `121,57,151,87` | `220,57,250,87` | `267,76,317,126` | `413,98,483,168` | đặt [this+0x10] = trang*5+0 rồi KeyPressed(-5) (chạm lần 2 = chọn) |
| next (1) | `283,57,313,87` | `382,57,412,87` | `539,76,589,126` | `795,98,865,168` | KeyPressed(PHẢI): tiến 1 vật phẩm |
| hscroll (1) | `121,89,279,97` | `220,89,378,97` | `267,130,533,141` | `413,174,787,188` | chạm/kéo ngang: [this+0x10] theo vị trí (Touch mới) hoặc nút mũi tên |
| detail (1) | `14,100,386,175` | `87,100,512,205` | `127,146,673,264` | `189,195,1011,410` | chỉ hiển thị |
| ok (1) | `14,179,198,202` | `87,209,297,232` | `127,270,397,299` | `189,418,596,456` | KeyPressed(-5) = chọn vật phẩm / gán ô (Process gốc: SetEquipItem, ApplyItemCompose, SetHotKeyBelt...) |
| cancel (1) | `202,179,386,202` | `302,209,512,232` | `403,270,673,299` | `604,418,1011,456` | phím * / back: thoát chế độ con (SetSubState(0)) |

**tui_do_phim_tat** (`tui_do_phim_tat`): Chế độ con Túi đồ SubState 2: gán ô nhanh (5 ô)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| belt (5) | `104,57,134,87` | `203,57,233,87` | `239,76,289,126` | `374,98,444,168` | đặt con trỏ ô nhanh [this+0x10]=0 rồi KeyPressed(-5) (SubState 2 -> HERO::SetHotKeyBelt) |
| belt_swap (1) | `266,57,296,99` | `365,57,395,99` | `511,76,561,138` | `756,98,826,185` | đổi bộ ô nhanh 1<->2 (gốc có 2 bộ x 5 ô; phím/hàm đổi bộ T17c xác nhận, vd HERO::ChangeHotKeyToggle) rồi vẽ lại |
| desc_scroll (1) | `17,155,383,172` | `90,155,509,202` | `130,199,670,261` | `192,258,1008,407` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `14,114,386,175` | `87,114,512,205` | `127,157,673,264` | `189,206,1011,410` | chỉ hiển thị |
| ok (1) | `14,179,198,202` | `87,209,297,232` | `127,270,397,299` | `189,418,596,456` | KeyPressed(-5) = chọn vật phẩm / gán ô (Process gốc: SetEquipItem, ApplyItemCompose, SetHotKeyBelt...) |
| cancel (1) | `202,179,386,202` | `302,209,512,232` | `403,270,673,299` | `604,418,1011,456` | phím * / back: thoát chế độ con (SetSubState(0)) |

**tui_do_menu** (`tui_do_menu`): Chế độ con Túi đồ SubState 1: menu hành động

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| page (3) | `17,62,49,92` | `90,62,122,92` | `131,84,185,134` | `194,109,270,179` | [this+0xe]=0 (trang), con trỏ [this+0xf]=0 |
| cell (16) | `54,62,84,92` | `127,62,157,92` | `192,84,242,134` | `279,109,349,179` | con trỏ [this+0xf]=0 rồi KeyPressed(-5) (chạm lần 2 = OK) |
| sort (1) | `185,176,383,199` | `258,206,509,229` | `411,266,669,295` | `586,413,1006,451` | phím 0 -> KeyBagMenu(0x30) = BagItem::SortBagItem |
| detail (1) | `185,62,383,172` | `258,62,509,202` | `411,84,669,260` | `586,109,1006,405` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| menu (4) | `101,81,299,104` | `146,96,454,119` | `197,129,603,158` | `413,202,787,240` | đặt con trỏ menu=0 rồi KeyPressed(-5) (SubState 1 / SetPopupState gốc) |

**ky_nang** (`ky_nang`): Kỹ năng: 5 tab con, lưới 4x4 cuộn, mô tả dài nhất

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (5) | `16,34,88,59` | `88,34,171,59` | `128,51,235,78` | `191,68,353,100` | đổi tab con (5): ghi trường tab con của KeySkillMenu (ví dụ [this+0x11]) rồi vẽ lại |
| skill (16) | `22,63,52,93` | `95,63,125,93` | `138,84,188,134` | `203,108,273,178` | con trỏ ô=0 (+ hàng cuộn) rồi KeyPressed(-5): chọn; chạm lần 2 = mở menu Gán phím tắt |
| grid_scroll (1) | `22,63,155,189` | `95,63,228,189` | `138,84,360,296` | `203,108,514,406` | kéo dọc = cuộn hàng (Touch mới; gốc: phím lên/xuống ở hàng 0/3) |
| desc_scroll (1) | `164,104,383,172` | `237,104,509,202` | `371,126,670,261` | `527,160,1008,407` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `161,63,386,175` | `234,63,512,205` | `368,84,673,264` | `524,108,1011,410` | mô tả: kéo dọc để cuộn |
| assign (1) | `161,179,386,202` | `234,209,512,232` | `368,270,673,299` | `524,418,1011,456` | KeyPressed(-5) -> popup/SubState gán ô nhanh (HERO::SetHotKeyBelt) |

**ky_nang_phim_tat** (`ky_nang_phim_tat`): Kỹ năng: gán ô nhanh

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (5) | `16,34,88,59` | `88,34,171,59` | `128,51,235,78` | `191,68,353,100` | đổi tab con (5): ghi trường tab con của KeySkillMenu (ví dụ [this+0x11]) rồi vẽ lại |
| skill (16) | `22,63,52,93` | `95,63,125,93` | `138,84,188,134` | `203,108,273,178` | con trỏ ô=0 (+ hàng cuộn) rồi KeyPressed(-5): chọn; chạm lần 2 = mở menu Gán phím tắt |
| grid_scroll (1) | `22,63,155,189` | `95,63,228,189` | `138,84,360,296` | `203,108,514,406` | kéo dọc = cuộn hàng (Touch mới; gốc: phím lên/xuống ở hàng 0/3) |
| desc_scroll (1) | `164,104,383,172` | `237,104,509,202` | `371,126,670,261` | `527,160,1008,407` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `161,63,386,175` | `234,63,512,205` | `368,84,673,264` | `524,108,1011,410` | mô tả: kéo dọc để cuộn |
| assign (1) | `161,179,386,202` | `234,209,512,232` | `368,270,673,299` | `524,418,1011,456` | KeyPressed(-5) -> popup/SubState gán ô nhanh (HERO::SetHotKeyBelt) |
| belt (5) | `104,110,134,140` | `204,125,234,155` | `239,155,289,205` | `374,232,444,302` | đặt con trỏ ô nhanh [this+0x10]=0 rồi KeyPressed(-5) (SubState 2 -> HERO::SetHotKeyBelt) |
| belt_swap (1) | `266,110,296,152` | `366,125,396,167` | `511,155,561,217` | `756,232,826,319` | đổi bộ ô nhanh 1<->2 (gốc có 2 bộ x 5 ô; phím/hàm đổi bộ T17c xác nhận, vd HERO::ChangeHotKeyToggle) rồi vẽ lại |
| popup_cancel (1) | `168,146,232,169` | `268,161,332,184` | `346,214,453,243` | `525,314,675,352` | back: thoát SubState 2 |

**dac_tinh** (`dac_tinh`): Đặc tính: 3 nhánh + Điểm, lưới 3x4

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| branch (4) | `15,34,106,59` | `88,34,192,59` | `127,51,262,78` | `189,68,393,100` | đổi nhánh: KeyPropertyMenu (trái/phải ở hàng tab) |
| node (9) | `81,63,111,93` | `154,63,184,93` | `230,84,280,134` | `330,108,400,178` | con trỏ nút (hàng 0, cột 1) rồi KeyPressed(-5) |
| grid_scroll (1) | `32,63,168,195` | `105,63,241,201` | `149,84,374,293` | `217,108,531,430` | kéo dọc = cuộn hàng (Touch mới) |
| desc_scroll (1) | `177,123,383,172` | `250,123,509,202` | `385,145,670,261` | `544,182,1008,407` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `174,82,386,175` | `247,82,512,205` | `382,103,673,264` | `541,130,1011,410` | mô tả: kéo dọc cuộn |
| invest (1) | `174,179,386,202` | `247,209,512,232` | `382,270,673,299` | `541,418,1011,456` | KeyPressed(-5): đầu tư 1 điểm vào nút (HeroSkillInfo.level++ + CalcStatusComputation) |

**lien_kich** (`lien_kich`): Liên kích: 4 ô chuỗi + 5 ứng viên/trang

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| combo (4) | `117,54,147,84` | `217,54,247,84` | `262,71,312,121` | `407,91,477,161` | đặt ô liên kích [this+0xe]=0 rồi KeyPressed(-5): mở dãy ứng viên (HERO::SetComboSkillBelt khi chọn) |
| prev (1) | `87,94,117,124` | `186,94,216,124` | `211,133,261,183` | `335,175,405,245` | KeyPressed(TRÁI): lùi ứng viên |
| cand (5) | `121,94,151,124` | `220,94,250,124` | `267,133,317,183` | `413,175,483,245` | đặt con trỏ ứng viên = trang*5+0 rồi KeyPressed(-5) = HERO::SetComboSkillBelt(slot,skill) |
| next (1) | `283,94,313,124` | `382,94,412,124` | `539,133,589,183` | `795,175,865,245` | KeyPressed(PHẢI): tiến ứng viên |
| clear (1) | `14,179,198,202` | `87,209,297,232` | `127,270,397,299` | `189,418,596,456` | phím 0 -> bỏ chọn ô liên kích |
| ok (1) | `202,179,386,202` | `302,209,512,232` | `403,270,673,299` | `604,418,1011,456` | KeyPressed(-5) |

**tinh_linh** (`tinh_linh`): Tinh linh: Cơ bản/Trang bị

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (2) | `14,34,199,59` | `87,34,298,59` | `127,51,399,78` | `189,68,599,100` | đổi tab con (2): KeySpiritMenu trái/phải ở hàng tab |
| slot (2) | `191,84,221,114` | `289,84,319,114` | `386,107,436,157` | `577,136,647,206` | đặt con trỏ ô trang bị Tinh linh=0 + KeyPressed(-5) (gắn: OpenSpiritEquipFilter; gỡ: Process -> ClearEquipItemSpirit) |
| desc_scroll (1) | `194,159,383,199` | `292,159,509,229` | `389,205,670,296` | `580,266,1008,453` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `191,118,386,202` | `289,118,512,232` | `386,163,673,299` | `577,214,1011,456` | chỉ hiển thị |

**tinh_linh_ky_nang** (`tinh_linh_ky_nang`): Tinh linh: kỹ năng Tinh Linh

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (2) | `14,34,199,59` | `87,34,298,59` | `127,51,399,78` | `189,68,599,100` | đổi tab con (2): KeySpiritMenu trái/phải ở hàng tab |
| spskill (4) | `14,63,44,93` | `87,63,117,93` | `127,84,177,134` | `189,108,259,178` | chạm = chọn kỹ năng Tinh linh 0 (4 phím hướng gốc) |
| desc_scroll (1) | `17,138,383,172` | `90,138,509,202` | `130,182,670,261` | `192,238,1008,407` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `14,97,386,175` | `87,97,512,205` | `127,140,673,264` | `189,186,1011,410` | chỉ hiển thị |
| use (1) | `14,179,386,202` | `87,209,512,232` | `127,270,673,299` | `189,418,1011,456` | KeyPressed(-5) -> HERO::LearnSpiritSkill / dùng kỹ năng Tinh linh |

**nhiem_vu** (`nhiem_vu`): Nhiệm vụ: danh sách + chi tiết (mô tả dài nhất)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (2) | `14,34,199,59` | `87,34,298,59` | `127,51,399,78` | `189,68,599,100` | đổi tab con (2): KeyQuestMenu trái/phải |
| quest (5) | `14,63,193,86` | `87,63,292,86` | `127,84,390,113` | `189,108,587,146` | đặt con trỏ nhiệm vụ=0 (KeyQuestMenu) rồi KeyPressed(-5) = theo dõi (SetMapMode/QuestSetStatus) |
| desc_scroll (1) | `207,104,383,199` | `306,104,509,229` | `409,126,670,296` | `611,160,1008,453` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `204,63,386,202` | `303,63,512,232` | `406,84,673,299` | `608,108,1011,456` | chi tiết: kéo dọc cuộn |

**nhiem_vu_nhanh** (`nhiem_vu_nhanh`): Nhiệm vụ: nhánh nhiệm vụ

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (2) | `14,34,199,59` | `87,34,298,59` | `127,51,399,78` | `189,68,599,100` | đổi tab con (2): KeyQuestMenu trái/phải |
| node (4) | `20,63,380,88` | `93,63,506,88` | `190,84,610,111` | `348,108,852,140` | đặt con trỏ nút nhánh=0 (KeyQuestMenu) + KeyPressed(-5): xem chi tiết |

**he_thong** (`he_thong`): Hệ thống: 4 nút

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| save (1) | `84,57,315,82` | `167,72,431,97` | `230,94,569,127` | `390,153,810,197` | đặt con trỏ=0 + KeyPressed(-5): popup xác nhận -> Process: HERO::SaveAll |
| option (1) | `84,89,315,114` | `167,104,431,129` | `230,137,569,170` | `390,211,810,255` | đặt con trỏ=1 + KeyPressed(-5): SetSubState(1) |
| help (1) | `84,121,315,146` | `167,136,431,161` | `230,180,569,213` | `390,269,810,313` | đặt con trỏ=2 + KeyPressed(-5): SetSubState(2/3) |
| title (1) | `84,153,315,178` | `167,168,431,193` | `230,223,569,256` | `390,327,810,371` | đặt con trỏ=3 + KeyPressed(-5): popup xác nhận -> về màn hình chính |

**he_thong_cai_dat** (`he_thong_cai_dat`): Hệ thống: Cài đặt (6 tuỳ chọn)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| opt0_prev (1) | `292,34,315,57` | `418,34,441,57` | `555,51,584,80` | `858,68,896,106` | đặt con trỏ=0 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt0_next (1) | `361,34,384,57` | `487,34,510,57` | `640,51,669,80` | `967,68,1005,106` | đặt con trỏ=0 + KeyPressed(PHẢI) |
| opt (6) | `14,34,292,57` | `87,34,418,57` | `127,51,555,80` | `189,68,858,106` | đặt con trỏ hàng tuỳ chọn=0 |
| opt1_prev (1) | `292,60,315,83` | `418,60,441,83` | `555,83,584,112` | `858,109,896,147` | đặt con trỏ=1 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt1_next (1) | `361,60,384,83` | `487,60,510,83` | `640,83,669,112` | `967,109,1005,147` | đặt con trỏ=1 + KeyPressed(PHẢI) |
| opt2_prev (1) | `292,86,315,109` | `418,86,441,109` | `555,115,584,144` | `858,150,896,188` | đặt con trỏ=2 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt2_next (1) | `361,86,384,109` | `487,86,510,109` | `640,115,669,144` | `967,150,1005,188` | đặt con trỏ=2 + KeyPressed(PHẢI) |
| opt3_prev (1) | `292,112,315,135` | `418,112,441,135` | `555,147,584,176` | `858,191,896,229` | đặt con trỏ=3 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt3_next (1) | `361,112,384,135` | `487,112,510,135` | `640,147,669,176` | `967,191,1005,229` | đặt con trỏ=3 + KeyPressed(PHẢI) |
| opt4_prev (1) | `292,138,315,161` | `418,138,441,161` | `555,179,584,208` | `858,232,896,270` | đặt con trỏ=4 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt4_next (1) | `361,138,384,161` | `487,138,510,161` | `640,179,669,208` | `967,232,1005,270` | đặt con trỏ=4 + KeyPressed(PHẢI) |
| opt5_prev (1) | `292,164,315,187` | `418,164,441,187` | `555,211,584,240` | `858,273,896,311` | đặt con trỏ=5 + KeyPressed(TRÁI) (đổi giá trị tuỳ chọn) |
| opt5_next (1) | `361,164,384,187` | `487,164,510,187` | `640,211,669,240` | `967,273,1005,311` | đặt con trỏ=5 + KeyPressed(PHẢI) |

**he_thong_tro_giup** (`he_thong_tro_giup`): Hệ thống: Trợ giúp (danh sách + nội dung)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| help (7) | `14,34,163,55` | `87,34,258,55` | `127,51,346,76` | `189,68,521,100` | đặt con trỏ danh sách trợ giúp=3 (KeySystemMenu) + KeyPressed(-5) |
| desc_scroll (1) | `177,63,383,199` | `272,63,509,229` | `365,81,670,296` | `545,103,1008,453` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `174,34,386,202` | `269,34,512,232` | `362,51,673,299` | `542,68,1011,456` | nội dung: kéo dọc cuộn (gốc: phím lên/xuống) |

**he_thong_popup_luu** (`he_thong_popup_luu`): Hệ thống: popup Lưu (popup chung)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| save (1) | `84,57,315,82` | `167,72,431,97` | `230,94,569,127` | `390,153,810,197` | đặt con trỏ=0 + KeyPressed(-5): popup xác nhận -> Process: HERO::SaveAll |
| option (1) | `84,89,315,114` | `167,104,431,129` | `230,137,569,170` | `390,211,810,255` | đặt con trỏ=1 + KeyPressed(-5): SetSubState(1) |
| help (1) | `84,121,315,146` | `167,136,431,161` | `230,180,569,213` | `390,269,810,313` | đặt con trỏ=2 + KeyPressed(-5): SetSubState(2/3) |
| title (1) | `84,153,315,178` | `167,168,431,193` | `230,223,569,256` | `390,327,810,371` | đặt con trỏ=3 + KeyPressed(-5): popup xác nhận -> về màn hình chính |
| popup_yes (1) | `71,120,197,143` | `101,135,297,158` | `137,179,396,208` | `383,269,594,307` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,120,329,143` | `304,135,499,158` | `405,179,663,208` | `607,269,817,307` | con trỏ popup=Không + KeyPressed(-5) |

**popup_chi_tiet** (`popup_chi_tiet`): Popup đặc thù: chi tiết vật phẩm

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| page (3) | `17,62,49,92` | `90,62,122,92` | `131,84,185,134` | `194,109,270,179` | [this+0xe]=0 (trang), con trỏ [this+0xf]=0 |
| cell (16) | `54,62,84,92` | `127,62,157,92` | `192,84,242,134` | `279,109,349,179` | con trỏ [this+0xf]=0 rồi KeyPressed(-5) (chạm lần 2 = OK) |
| sort (1) | `185,176,383,199` | `258,206,509,229` | `411,266,669,295` | `586,413,1006,451` | phím 0 -> KeyBagMenu(0x30) = BagItem::SortBagItem |
| detail (2) | `185,62,383,172` | `258,62,509,202` | `411,84,669,260` | `586,109,1006,405` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| desc_scroll (1) | `56,58,344,193` | `78,58,522,223` | `104,71,696,293` | `300,113,900,430` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| popup_ok (1) | `149,200,251,223` | `223,230,377,253` | `297,302,502,331` | `495,441,705,479` | KeyPressed(-5) -> SetPopupState(0) |

**popup_chon_lop** (`popup_chon_lop`): Popup đặc thù: chọn lớp (danh sách)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| subtab (2) | `14,34,199,59` | `87,34,298,59` | `127,51,399,78` | `189,68,599,100` | đổi tab con (2): KeyQuestMenu trái/phải |
| quest (5) | `14,63,193,86` | `87,63,292,86` | `127,84,390,113` | `189,108,587,146` | đặt con trỏ nhiệm vụ=0 (KeyQuestMenu) rồi KeyPressed(-5) = theo dõi (SetMapMode/QuestSetStatus) |
| desc_scroll (1) | `207,104,383,199` | `306,104,509,229` | `409,126,670,296` | `611,160,1008,453` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `204,63,386,202` | `303,63,512,232` | `406,84,673,299` | `608,108,1011,456` | chi tiết: kéo dọc cuộn |
| menu (5) | `101,68,299,91` | `146,83,454,106` | `197,111,603,140` | `413,179,787,217` | đặt con trỏ menu=0 rồi KeyPressed(-5) (SubState 1 / SetPopupState gốc) |

**popup_ok_dai** (`popup_ok_dai`): Popup chỉ OK, chuỗi dài nhất

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| save (1) | `84,57,315,82` | `167,72,431,97` | `230,94,569,127` | `390,153,810,197` | đặt con trỏ=0 + KeyPressed(-5): popup xác nhận -> Process: HERO::SaveAll |
| option (1) | `84,89,315,114` | `167,104,431,129` | `230,137,569,170` | `390,211,810,255` | đặt con trỏ=1 + KeyPressed(-5): SetSubState(1) |
| help (1) | `84,121,315,146` | `167,136,431,161` | `230,180,569,213` | `390,269,810,313` | đặt con trỏ=2 + KeyPressed(-5): SetSubState(2/3) |
| title (1) | `84,153,315,178` | `167,168,431,193` | `230,223,569,256` | `390,327,810,371` | đặt con trỏ=3 + KeyPressed(-5): popup xác nhận -> về màn hình chính |
| popup_ok (1) | `157,137,243,160` | `234,143,366,166` | `312,179,487,208` | `528,279,672,317` | KeyPressed(-5) |

### 11.4 Popup đặc thù InGameMenu
`SetPopupState` 0xe31b8 có 25 loại, nhưng bảng nhảy chỉ trỏ tới ~14 nhánh dựng chuỗi (`GetCommonText` + `MC_knlSprintk`): thực chất phần lớn là thông báo/xác nhận (popup chung T17b1: Có/Không, OK). Khác popup chung có 3 dạng, đã mockup: (1) **menu hành động** (`tui_do_menu`, `trang_bi_menu`: Sử dụng/Gán phím tắt/Vứt bỏ/Phân giải; Trang bị/Tháo), (2) **chi tiết vật phẩm** có `ItemInfo*` (`popup_chi_tiet`, thay `DrawAutoItemInfoBox` 156 px cố định), (3) **chọn lớp** (`popup_chon_lop`, `ChangeHeroClass`, danh sách `class.dat`); thêm popup gán ô nhanh (`ky_nang_phim_tat`). Popup xác nhận Lưu/Màn hình chính (`he_thong_popup_luu`) và OK dài nhất (`popup_ok_dai`) dùng widget chung. Việc gán chính xác từng loại trong 25 → widget làm ở T17c1 (đọc từng nhánh `SetPopupState`).

### 11.5 Lệch khỏi lưới gốc / giả định / chỗ cần xác nhận khi implement
1. **Không đổi lưới logic** của tab nào (số ô/hàng/cột/trang giữ như bảng 11.1). Cái mới chỉ là giao diện: nút +/− (thay trái/phải), nút Chi tiết/Cơ bản/Huỷ (thay `#`/`*`), nút Sắp xếp/OK/Huỷ, mũi tên cuộn, kéo dọc cuộn mô tả, thanh cuộn ngang.
2. Thứ tự 8 ô trang bị (Vũ khí, Mũ, Giáp, Găng / Giày, Khiên, Trang sức ×2) và nhãn 5 tab con Kỹ năng suy từ ảnh gốc + `common_text`; icon kỹ năng = `icon.mgr[chỉ số kỹ năng]` (ảnh mẫu; khi implement dùng đúng hàm icon của `HeroSkillInfo`).
3. Số trang/hàng cuộn của Kỹ năng/Đặc tính/Nhiệm vụ/Trợ giúp lấy từ dữ liệu (skill_00 = 43 kỹ năng → 11 hàng; quest_0 = 151; help = 20 tiêu đề); số dòng cuộn tối đa kiểm ở T17c.
4. Màu mã `}` `{` trong mô tả kỹ năng và ý nghĩa `#NN` là giả định (xem 11.2); tra `BFont::DrawText`.
5. Bộ ô nhanh: 5 ô/bộ × 2 bộ (`GetHotKeyBeltType` lặp 5 × 2). Mockup vẽ 1 bộ + nút đổi bộ (ảnh `ui_new/07_swap.png`, nhãn `bộ/2`, hit `belt_swap`) ở Túi đồ chọn ô nhanh (SubState 2) và Kỹ năng gán phím tắt. Hàm/phím đổi bộ gốc (vd `HERO::ChangeHotKeyToggle`?) T17c xác nhận.
6. Chân dung Tinh linh lấy `icon.mgr[172]` làm ảnh mẫu; thanh "SP Tinh linh" ở tab Kỹ năng Tinh Linh là nhãn mẫu (năng lượng Tinh linh 5 nấc).
<!-- B2B -->

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
| 400x240 | 14,34,386,226 | 21 | 25 | (14,59) / (14,88) 126x126 | 153 / 182 |
| 600x270 | 87,34,512,256 | 21 | 25 | (87,59) / (87,88) 126x126 | 226 / 255 |
| 800x360 | 127,51,673,335 | 23 | 27 | (127,80) / (127,113) 212x212 | 357 / 407 |
| 1200x540 | 189,68,1011,504 | 28 | 32 | (189,104) / (189,144) 298x298 | 510 / 581 |

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


**npc_mua_ky_nang** (`npc_mua_ky_nang`): NPC Mua kỹ năng (SkillBuy): lưới sách kỹ năng + giá

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,62,150,188` | `90,62,223,188` | `131,84,353,296` | `194,109,505,407` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,62,47,92` | `90,62,120,92` | `131,84,181,134` | `194,109,264,179` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,116,380,170` | `232,116,506,200` | `364,139,666,259` | `518,179,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,62,383,173` | `229,62,509,203` | `361,84,669,262` | `515,109,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | đặt con trỏ=ô + KeyPressed(-5) -> popup xác nhận -> Process: NpcShop::NewShopItemToBag + BagItem::IncreaseMoney(-giá) |

**npc_mua_ky_nang_het_tien** (`npc_mua_ky_nang_het_tien`): NPC Mua kỹ năng: popup không đủ tiền (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,62,150,188` | `90,62,223,188` | `131,84,353,296` | `194,109,505,407` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,62,47,92` | `90,62,120,92` | `131,84,181,134` | `194,109,264,179` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,116,380,170` | `232,116,506,200` | `364,139,666,259` | `518,179,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,62,383,173` | `229,62,509,203` | `361,84,669,262` | `515,109,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | đặt con trỏ=ô + KeyPressed(-5) -> popup (không đủ tiền) |
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**npc_mua_vat_pham** (`npc_mua_vat_pham`): NPC Mua vật phẩm (ItemBuy): 3 tab hàng + lưới 4x4 cuộn

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| subtab (3) | `18,62,138,87` | `90,62,228,87` | `131,84,309,111` | `194,109,463,141` | đổi tab: ghi trường tab của Key* gốc rồi vẽ lại |
| grid_scroll (1) | `17,91,150,217` | `90,91,223,217` | `131,117,353,329` | `194,149,505,447` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,91,47,121` | `90,91,120,121` | `131,117,181,167` | `194,149,264,219` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `156,91,383,173` | `229,91,509,203` | `361,117,669,262` | `515,149,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | đặt con trỏ=ô + KeyPressed(-5) -> popup xác nhận -> Process: NpcShop::NewShopItemToBag / ItemTable::NewItemToBag |

**npc_mua_vat_pham_xac_nhan** (`npc_mua_vat_pham_xac_nhan`): NPC Mua vật phẩm: popup xác nhận Có/Không

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| subtab (3) | `18,62,138,87` | `90,62,228,87` | `131,84,309,111` | `194,109,463,141` | đổi tab: ghi trường tab của Key* gốc rồi vẽ lại |
| grid_scroll (1) | `17,91,150,217` | `90,91,223,217` | `131,117,353,329` | `194,149,505,447` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,91,47,121` | `90,91,120,121` | `131,117,181,167` | `194,149,264,219` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `156,91,383,173` | `229,91,509,203` | `361,117,669,262` | `515,149,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | đặt con trỏ=ô + KeyPressed(-5) -> popup xác nhận -> Process: NpcShop::NewShopItemToBag / ItemTable::NewItemToBag |
| popup_yes (1) | `71,137,197,160` | `101,152,297,175` | `137,196,396,225` | `383,289,594,327` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,137,329,160` | `304,152,499,175` | `405,196,663,225` | `607,289,817,327` | con trỏ popup=Không + KeyPressed(-5) |

**npc_ban** (`npc_ban`): NPC Bán vật phẩm (ItemSale): túi đồ theo trang + giá bán

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| page (3) | `17,62,49,92` | `90,62,122,92` | `131,84,185,134` | `194,109,270,179` | [this+0xe]=0 (trang), con trỏ [this+0xf]=0 |
| cell (16) | `54,62,84,92` | `127,62,157,92` | `192,84,242,134` | `279,109,349,179` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `185,62,383,173` | `258,62,509,203` | `411,84,669,262` | `586,109,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| sell (1) | `185,200,383,223` | `258,230,509,253` | `411,302,669,331` | `586,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: BagItem::DeleteBagItemPosIndex + IncreaseMoney(+giá) |

**npc_toi_luyen** (`npc_toi_luyen`): NPC Tôi luyện thường (LowRefine)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| slot_item (1) | `17,37,383,73` | `90,37,509,73` | `131,55,669,111` | `194,73,1006,149` | chạm = mở chế độ chọn vật phẩm (OpenBagFilter/OpenEquipFilter) -> KeyRefine gốc |
| slot_stone (1) | `17,77,383,113` | `90,77,509,113` | `131,117,669,173` | `194,157,1006,233` | chạm = mở chế độ chọn đá tôi luyện (OpenBagFilter) |
| start (1) | `17,200,383,223` | `90,230,509,253` | `131,302,669,331` | `194,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyItemRefine |

**npc_toi_luyen_cao_cap** (`npc_toi_luyen_cao_cap`): NPC Tôi luyện cao cấp (HighRefine)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| slot_item (1) | `17,37,383,73` | `90,37,509,73` | `131,55,669,111` | `194,73,1006,149` | chạm = mở chế độ chọn vật phẩm (OpenBagFilter/OpenEquipFilter) -> KeyRefine gốc |
| slot_stone (1) | `17,77,383,113` | `90,77,509,113` | `131,117,669,173` | `194,157,1006,233` | chạm = mở chế độ chọn đá tôi luyện (OpenBagFilter) |
| start (1) | `17,200,383,223` | `90,230,509,253` | `131,302,669,331` | `194,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyItemRefine |

**npc_toi_luyen_ket_qua** (`npc_toi_luyen_ket_qua`): NPC Tôi luyện: popup kết quả (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| slot_item (1) | `17,37,383,73` | `90,37,509,73` | `131,55,669,111` | `194,73,1006,149` | chạm = mở chế độ chọn vật phẩm (OpenBagFilter/OpenEquipFilter) -> KeyRefine gốc |
| slot_stone (1) | `17,77,383,113` | `90,77,509,113` | `131,117,669,173` | `194,157,1006,233` | chạm = mở chế độ chọn đá tôi luyện (OpenBagFilter) |
| start (1) | `17,200,383,223` | `90,230,509,253` | `131,302,669,331` | `194,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyItemRefine |
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**npc_chon_vat_pham** (`npc_chon_vat_pham`): NPC chế độ chọn vật phẩm (OpenBagFilter, 5/trang)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| prev (1) | `87,60,117,90` | `186,60,216,90` | `211,80,261,130` | `335,103,405,173` | KeyPressed(TRÁI): lùi 1 vật phẩm ([this+0x10]-1) |
| cand (5) | `121,60,151,90` | `220,60,250,90` | `267,80,317,130` | `413,103,483,173` | đặt [this+0x10] = trang*5+0 rồi KeyPressed(-5) (chạm lần 2 = chọn) |
| next (1) | `283,60,313,90` | `382,60,412,90` | `539,80,589,130` | `795,103,865,173` | KeyPressed(PHẢI): tiến 1 vật phẩm |
| hscroll (1) | `121,92,279,100` | `220,92,378,100` | `267,134,533,145` | `413,179,787,193` | chạm/kéo ngang: [this+0x10] theo vị trí (Touch mới) |
| detail (1) | `17,103,383,196` | `90,103,509,226` | `131,150,669,296` | `194,200,1006,453` | chỉ hiển thị |
| ok (1) | `17,200,198,223` | `90,230,297,253` | `131,302,397,331` | `194,461,596,499` | KeyPressed(-5) = chọn vật phẩm (Process gốc) |
| cancel (1) | `202,200,383,223` | `302,230,509,253` | `403,302,669,331` | `604,461,1006,499` | phím * / back: thoát chế độ chọn |

**npc_orb** (`npc_orb`): NPC Kết hợp Orb (OrbCombine): 3 ô + tỉ lệ

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| slot_item (1) | `17,37,383,73` | `90,37,509,73` | `131,55,669,111` | `194,73,1006,149` | chạm = chọn vật phẩm cần kết hợp (OpenBagFilter) |
| slot_orb (1) | `17,77,383,113` | `90,77,509,113` | `131,117,669,173` | `194,157,1006,233` | chạm = chọn Orb (OpenBagFilter) |
| slot_sacrifice (1) | `17,117,383,153` | `90,117,509,153` | `131,179,669,235` | `194,241,1006,317` | chạm = chọn vật hiến tế (OpenBagFilter) |
| clear (1) | `17,200,198,223` | `90,230,297,253` | `131,302,397,331` | `194,461,596,499` | phím 0 -> bỏ vật hiến tế (KeyOrbCombine) |
| start (1) | `202,200,383,223` | `302,230,509,253` | `403,302,669,331` | `604,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyOrbCombine |

**npc_che_bien** (`npc_che_bien`): NPC Chế biến thường (NormalMix): công thức + nguyên liệu

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,62,150,188` | `90,62,223,188` | `131,84,353,296` | `194,109,505,407` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,62,47,92` | `90,62,120,92` | `131,84,181,134` | `194,109,264,179` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `156,62,383,196` | `229,62,509,226` | `361,84,669,296` | `515,109,1006,453` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| mix (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: RefineItem::ApplyNormalMix (+ItemTable::NewItem/DeleteItem) |

**npc_bo_suu_tap** (`npc_bo_suu_tap`): NPC Bộ sưu tập: chọn Đồ Giám (3 nút)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| cat (3) | `86,96,313,121` | `169,111,429,136` | `233,145,567,178` | `390,220,810,264` | đặt con trỏ=0 + KeyPressed(-5) -> vào danh sách Đồ Giám Vũ khí |

**npc_bo_suu_tap_luoi** (`npc_bo_suu_tap_luoi`): NPC Bộ sưu tập: Đồ Giám Vũ Khí (4 lớp, lưới 4x4)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| subtab (4) | `17,62,107,87` | `90,62,193,87` | `131,84,264,111` | `195,109,396,141` | đổi tab: ghi trường tab của Key* gốc rồi vẽ lại |
| grid_scroll (1) | `17,91,150,217` | `90,91,223,217` | `131,117,353,329` | `194,149,505,447` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,91,47,121` | `90,91,120,121` | `131,117,181,167` | `194,149,264,219` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `156,91,383,196` | `229,91,509,226` | `361,117,669,296` | `515,149,1006,453` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| register (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận (273) -> Process: HERO::SetCollectionComplete + Mission::CheckCollection |

**npc_bo_suu_tap_dang_ky** (`npc_bo_suu_tap_dang_ky`): NPC Bộ sưu tập: popup đăng ký Có/Không (273)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| subtab (4) | `17,62,107,87` | `90,62,193,87` | `131,84,264,111` | `195,109,396,141` | đổi tab: ghi trường tab của Key* gốc rồi vẽ lại |
| grid_scroll (1) | `17,91,150,217` | `90,91,223,217` | `131,117,353,329` | `194,149,505,447` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,91,47,121` | `90,91,120,121` | `131,117,181,167` | `194,149,264,219` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| detail (1) | `156,91,383,196` | `229,91,509,226` | `361,117,669,296` | `515,149,1006,453` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| register (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận (273) -> Process: HERO::SetCollectionComplete + Mission::CheckCollection |
| popup_yes (1) | `71,137,197,160` | `101,143,297,166` | `137,179,396,208` | `383,279,594,317` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,137,329,160` | `304,143,499,166` | `405,179,663,208` | `607,279,817,317` | con trỏ popup=Không + KeyPressed(-5) |

**npc_so_luong** (L2, KHÔNG có mockup riêng: bố cục viết trong `engine/menu/npc.c` `qty_paint`, số đo đo từ build, `hit_rects.json` khoá `npc_so_luong_<khung>`, 3 khung 400x240/600x270/1200x540): popup NPC loại 5 = nhập số lượng (StatePopup, cha = NPC; dùng ở Tôi luyện (đá), Mua/Bán vật phẩm xếp chồng). Hộp giữa màn hình rộng 66% (hd 460), dòng tiêu đề (tên cửa sổ cha: CT84/85/102/103), hàng `[-10] [◀] [giá trị/tối đa] [▶] [+10]`, hàng `[OK] [Huỷ]`; chiều cao nút = `btnH`. Chạm = phím gốc: ◀ TRÁI(-1, vòng), ▶ PHẢI(+1, vòng), -10 XUỐNG, +10 LÊN, OK -5, Huỷ CLR (-16).

| nhóm | 400x240 | 600x270 | 1200x540 | hành động |
|---|---|---|---|---|
| tru10 | `77,107,123,130` | `111,122,157,145` | `385,245,461,283` | KeyPressed(XUỐNG) |
| tru1 | `127,107,150,130` | `161,122,184,145` | `469,245,507,283` | KeyPressed(TRÁI) |
| cong1 | `250,107,273,130` | `416,122,439,145` | `693,245,731,283` | KeyPressed(PHẢI) |
| cong10 | `277,107,323,130` | `443,122,489,145` | `739,245,815,283` | KeyPressed(LÊN) |
| popup_ok | `77,136,198,159` | `111,151,298,174` | `385,295,596,333` | KeyPressed(-5) |
| popup_huy | `202,136,323,159` | `302,151,489,174` | `604,295,815,333` | KeyPressed(CLR) |

Ghi chú L2 (Tôi luyện / Orb / Chế biến): dựng theo mockup `scr_refine/scr_orb/scr_mix`; khác mockup (dữ liệu thật): Tôi luyện hàng đá = "tên đá xSL/có", Độ tôi luyện = `n/10 > n+1/10` (như gốc); Orb nút "Bỏ vật hiến tế" vẽ mờ khi chưa có vật hiến tế (vẫn chạm được); Chế biến: lưới logic gốc 2 hàng x 4 cột = 8 công thức/cửa hàng (ô 8..15 trống, chạm bỏ qua), nguyên liệu = bảng `có/cần` dưới hộp thông tin (xanh khi đủ, đỏ khi thiếu), ô công thức chưa học (cat < 0) để trống.

**cash_shop** (`cash_shop`): Cash shop: lưới 4x4 cuộn + nhãn giá offline miễn phí

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag |

**cash_popup_mua** (`cash_popup_mua`): Cash shop popup 1/4: xác nhận mua (Có/Không)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag |
| popup_yes (1) | `71,137,197,160` | `101,152,297,175` | `137,196,396,225` | `383,289,594,327` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,137,329,160` | `304,152,499,175` | `405,196,663,225` | `607,289,817,327` | con trỏ popup=Không + KeyPressed(-5) |

**cash_popup_xong** (`cash_popup_xong`): Cash shop popup 2/4: đã nhận (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag |
| popup_ok (1) | `157,128,243,151` | `234,143,366,166` | `312,188,487,217` | `528,279,672,317` | KeyPressed(-5) |

**cash_popup_day** (`cash_popup_day`): Cash shop popup 3/4: túi đồ đầy (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag |
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**cash_popup_khu_vuc** (`cash_popup_khu_vuc`): Cash shop popup 4/4: khu vực không dùng được (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: cash miễn phí, bỏ NETWORK::SetProductID/CallWriteData (T7), ItemTable::NewItemToBag |
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**chuyen_gia_chuan_bi** (`chuyen_gia_chuan_bi`): Chuyên gia: Màn hình chuẩn bị

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| start (1) | `191,200,383,223` | `289,230,509,253` | `386,302,669,331` | `578,461,1006,499` | KeyPressed(-5) -> bắt đầu chế độ Chuyên gia (KeyReady/Process gốc) |
| char (1) | `191,173,285,196` | `289,203,397,226` | `386,267,524,296` | `578,415,788,453` | đặt con trỏ=0 + KeyPressed(-5) (KeyReady): mở Nhân vật |
| shop (1) | `289,173,383,196` | `401,203,509,226` | `531,267,669,296` | `796,415,1006,453` | đặt con trỏ=1 + KeyPressed(-5) (KeyReady): SetState(1) Cửa hàng |

**chuyen_gia_cua_hang** (`chuyen_gia_cua_hang`): Chuyên gia: Cửa hàng

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: ItemTable::NewItemToBag (miễn phí offline) |

**chuyen_gia_popup_mua** (`chuyen_gia_popup_mua`): Chuyên gia popup: xác nhận mua (Có/Không)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| grid_scroll (1) | `17,37,150,163` | `90,37,223,163` | `131,55,353,267` | `194,73,505,371` | chạm mũi tên/kéo dọc: cuộn hàng (Touch mới; gốc: phím lên/xuống) |
| cell (16) | `17,37,47,67` | `90,37,120,67` | `131,55,181,105` | `194,73,264,143` | đặt con trỏ ô=0 rồi KeyPressed(-5) (chạm lần 2 = OK gốc) |
| desc_scroll (1) | `159,66,380,170` | `232,66,506,200` | `364,85,666,259` | `518,108,1003,414` | kéo dọc = cuộn mô tả (TouchMoved mới), không đổi trường gốc |
| detail (1) | `156,37,383,173` | `229,37,509,203` | `361,55,669,262` | `515,73,1006,417` | chỉ hiển thị (chạm = chọn ô tương ứng) |
| buy (1) | `156,200,383,223` | `229,230,509,253` | `361,302,669,331` | `515,461,1006,499` | KeyPressed(-5) -> popup xác nhận -> Process: ItemTable::NewItemToBag (miễn phí offline) |
| popup_yes (1) | `71,137,197,160` | `101,152,297,175` | `137,196,396,225` | `383,289,594,327` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,137,329,160` | `304,152,499,175` | `405,196,663,225` | `607,289,817,327` | con trỏ popup=Không + KeyPressed(-5) |

**chuyen_gia_popup_khong_dung** (`chuyen_gia_popup_khong_dung`): Chuyên gia popup: không thể dùng ở chế độ Chuyên gia (OK)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| start (1) | `191,200,383,223` | `289,230,509,253` | `386,302,669,331` | `578,461,1006,499` | KeyPressed(-5) -> bắt đầu chế độ Chuyên gia (KeyReady/Process gốc) |
| char (1) | `191,173,285,196` | `289,203,397,226` | `386,267,524,296` | `578,415,788,453` | đặt con trỏ=0 + KeyPressed(-5) (KeyReady): mở Nhân vật |
| shop (1) | `289,173,383,196` | `401,203,509,226` | `531,267,669,296` | `796,415,1006,453` | đặt con trỏ=1 + KeyPressed(-5) (KeyReady): SetState(1) Cửa hàng |
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**game_hoi_sinh** (`game_hoi_sinh`): StateGame popup: hồi sinh bằng Bùa (miễn phí offline)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| popup_yes (1) | `71,128,197,151` | `101,143,297,166` | `137,188,396,217` | `383,279,594,317` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,128,329,151` | `304,143,499,166` | `405,188,663,217` | `607,279,817,317` | con trỏ popup=Không + KeyPressed(-5) |

**game_hoi_sinh_xac_nhan** (`game_hoi_sinh_xac_nhan`): StateGame popup: xác nhận dùng Bùa Hồi Sinh

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| popup_yes (1) | `71,120,197,143` | `101,135,297,158` | `137,179,396,208` | `383,269,594,307` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,120,329,143` | `304,135,499,158` | `405,179,663,208` | `607,269,817,307` | con trỏ popup=Không + KeyPressed(-5) |

**game_hoi_sinh_xong** (`game_hoi_sinh_xong`): StateGame popup: hồi sinh thành công

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| popup_ok (1) | `157,120,243,143` | `234,135,366,158` | `312,179,487,208` | `528,269,672,307` | KeyPressed(-5) |

**game_chien_thang** (`game_chien_thang`): StateGame popup: Chiến thắng! (kết quả Chuyên gia)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| popup_ok (1) | `157,160,243,183` | `234,175,366,198` | `312,219,487,248` | `528,315,672,353` | KeyPressed(-5) -> thoát màn kết quả (KeyGameOver/KeyExpert) |

**game_that_bai** (`game_that_bai`): StateGame popup: Thất bại! (kết quả Chuyên gia)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| popup_ok (1) | `157,160,243,183` | `234,175,366,198` | `312,219,487,248` | `528,315,672,353` | KeyPressed(-5) -> thoát màn kết quả (KeyGameOver/KeyExpert) |

**ban_do_khu_vuc** (`ban_do_khu_vuc`): StateMap: bản đồ khu vực (lưới điểm)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| node (7) | `143,45,165,67` | `233,49,255,71` | `314,69,346,101` | `468,99,510,141` | chỉ hiển thị (điểm khu vực Central Tower) |
| minimap (1) | `17,200,198,223` | `90,230,297,253` | `131,302,397,331` | `194,461,596,499` | phím * -> bật/tắt Minimap (StateMap KeyPressed) |
| toggle (1) | `202,200,383,223` | `302,230,509,253` | `403,302,669,331` | `604,461,1006,499` | phím # -> StateMap: chuyển sang bản đồ lớn (ảnh) |

**ban_do_lon** (`ban_do_lon`): StateMap: bản đồ lớn (ảnh các đảo)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| zone (8) | `94,105,102,126` | `178,118,186,139` | `241,155,257,182` | `361,232,385,270` | chỉ hiển thị (bản đồ lớn: ảnh + tên khu vực) |
| toggle (1) | `261,200,383,223` | `370,230,509,253` | `490,302,669,331` | `736,461,1006,499` | phím # -> StateMap: chuyển sang bản đồ khu vực (lưới điểm) |

### 12.4 Giả định cần xác nhận khi implement (T17c)
1. Số liệu giá / cấp / số lượng / tỉ lệ / `Độ tôi luyện` / đếm Bộ sưu tập trong ảnh là SỐ MẪU; chuỗi, tên, mô tả, vật liệu, tên khu vực là dữ liệu thật đã build. Icon gán theo thứ tự mẫu (`icon.mgr` 203–231); khi implement lấy qua `ItemInfo::DrawIcon` (cần thunk ×s như b1).
2. Nhãn giá Cash/Chuyên gia: dùng nguyên `common_text` 304 ("Bản offline: miễn phí", bỏ `^` và dấu chấm); nút "Nhận!" = 261 (nghĩa gốc: nhận vật phẩm cash, suy từ vị trí chuỗi); hai popup `cash_popup_mua`/`chuyen_gia_popup_mua` ghép 91 + 304 thay đoạn "Giá gốc … won". Nếu quản đốc muốn nhãn ngắn "Miễn phí" cần thêm 1 chuỗi mới vào dữ liệu.
3. Ảnh bản đồ lớn trong mockup là nền minh hoạ (không tìm được ảnh thật trong VFS dạng .mgr/PNG; game đọc qua `LoadWorldMapImage`): bố cục chỉ cần vùng chữ nhật + toạ độ tương đối (ISL trong `mock_b2b.py`, lấy theo ảnh gốc ô 60 của `kiemke.png`); toạ độ điểm thật phải đọc từ `DrawWorldMap` ở T17c.
4. Lưới điểm khu vực: 7 điểm (cột,hàng) lấy theo ảnh gốc ô 59; số điểm/cạnh thật của mỗi vùng đọc từ dữ liệu bản đồ ở T17c.
5. Tab "Khác"/"Giáp" của Mua vật phẩm hiển thị cùng danh sách mẫu; số vật phẩm mỗi tab lấy từ `NpcShop::LoadShopData`.
6. Popup StateGame hồi sinh/kết quả: các hàng kết quả Chuyên gia (Số màn, Thời gian còn lại, HP còn lại, Điểm số cuối cùng) theo chế độ (Cầm cự/Đua Thời Gian/Đột phá dùng bộ nhãn khác: 331–336); mockup vẽ 1 bộ.
7. Menu Cash shop `StateCashShop` có thể mở với 3 `type` (đơn/mạng/Expert): offline chỉ dùng single (và expert qua Chuyên gia); cash_network bỏ.
<!-- B2C -->

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
| 400x240 | 124x64 | 96x40 | 61 | 23 | 38 / 4 | 7 |
| 600x270 | 190x79 | 120x50 | 71 | 27 | 38 / 4 | 8 |
| 800x360 | 192x80 | 122x51 | 90 | 27 | 38 / 6 | 10 |
| 1200x540 | 377x160 | 247x103 | 140 | 30 | 44 / 8 | 14 |

### 13.3 Vùng chạm từng màn (đại diện: phần tử đầu mỗi nhóm; còn lại trong `hit_rects.json`)
Định dạng `[x0,y0,x1,y1)` theo khung logic, cột = 400x240 / 600x270 / 800x360 / 1200x540.


**menu_chinh_logo** (`menu_chinh_logo`): MainMenu state 2 (DrawLogo): huy hiệu 12+ trên nền dung nham

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| logo (1) | `0,0,400,240` | `0,0,600,270` | `0,0,800,360` | `0,0,1200,540` | chạm = bỏ qua logo (DrawLogo, StateMainMenu state 2) |

**menu_chinh_tieu_de** (`menu_chinh_tieu_de`): MainMenu state 3 (DrawTitle): logo + "Tap to Continue" (ảnh)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| tap (1) | `0,0,400,240` | `0,0,600,270` | `0,0,800,360` | `0,0,1200,540` | chạm bất kỳ = tiếp tục (DrawTitle: KeyPressed(-5)) -> state 4 menu chính; "Tap to Continue" là ẢNH (T11 vẽ lại tiếng Việt) |

**menu_chinh_luoi6** (`menu_chinh_luoi6`): MainMenu state 4: menu chính BẢN 6 Ô (theo kế hoạch T7)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| tile (6) | `8,88,132,152` | `9,88,199,167` | `104,157,296,237` | `24,170,401,330` | đặt con trỏ lưới gốc = 0 (Bắt đầu) rồi KeyPressed(-5) (StateMainMenu state 4, DrawMainMenu/KeyMainMenu) |

**menu_chinh_luoi9** (`menu_chinh_luoi9`): MainMenu state 4: menu chính lưới 3x3 GỐC (9 ô, trước T7)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| tile (9) | `50,88,146,128` | `114,88,234,138` | `209,157,331,208` | `219,170,466,273` | đặt con trỏ lưới gốc = 0 (Bắt đầu) rồi KeyPressed(-5) (StateMainMenu state 4, DrawMainMenu/KeyMainMenu) |

**menu_chinh_thoat** (`menu_chinh_thoat`): MainMenu popup: "Bạn có muốn thoát?" (Có/Không)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| tile (6) | `8,88,132,152` | `9,88,199,167` | `104,157,296,237` | `24,170,401,330` | đặt con trỏ lưới gốc = 0 (Bắt đầu) rồi KeyPressed(-5) (StateMainMenu state 4, DrawMainMenu/KeyMainMenu) |
| popup_yes (1) | `71,120,197,143` | `101,135,297,158` | `137,179,396,208` | `383,269,594,307` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,120,329,143` | `304,135,499,158` | `405,179,663,208` | `607,269,817,307` | con trỏ popup=Không + KeyPressed(-5) |

**menu_chinh_cho** (`menu_chinh_cho`): MainMenu state 0/1/7/8 (mạng): popup chờ (Đang kiểm tra cập nhật)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| tile (6) | `8,88,132,152` | `9,88,199,167` | `104,157,296,237` | `24,170,401,330` | đặt con trỏ lưới gốc = 0 (Bắt đầu) rồi KeyPressed(-5) (StateMainMenu state 4, DrawMainMenu/KeyMainMenu) |

**menu_chinh_slot** (`menu_chinh_slot`): MainMenu state 5 (DrawStart): Chọn Slot, 3 slot (2 có dữ liệu, 1 trống)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| del (2) | `337,55,379,78` | `463,60,505,83` | `610,84,664,113` | `931,122,1000,160` | phím * (*Xoá): popup xoá slot 0 (193) -> Có = xoá dữ liệu slot |
| slot (3) | `17,37,335,96` | `90,37,461,106` | `131,55,608,143` | `194,73,929,209` | đặt con trỏ slot=0 + KeyPressed(-5): slot có dữ liệu -> ContinueGame (LoadSlotData) |

**menu_chinh_slot_moi** (`menu_chinh_slot_moi`): Chọn Slot: popup tạo nhân vật mới (Có/Không)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| del (2) | `337,55,379,78` | `463,60,505,83` | `610,84,664,113` | `931,122,1000,160` | phím * (*Xoá): popup xoá slot 0 (193) -> Có = xoá dữ liệu slot |
| slot (3) | `17,37,335,96` | `90,37,461,106` | `131,55,608,143` | `194,73,929,209` | đặt con trỏ slot=0 + KeyPressed(-5): slot có dữ liệu -> ContinueGame (LoadSlotData) |
| popup_yes (1) | `71,120,197,143` | `101,135,297,158` | `137,179,396,208` | `383,269,594,307` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,120,329,143` | `304,135,499,158` | `405,179,663,208` | `607,269,817,307` | con trỏ popup=Không + KeyPressed(-5) |

**menu_chinh_slot_xoa** (`menu_chinh_slot_xoa`): Chọn Slot: popup xoá slot (Có/Không)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| del (2) | `337,55,379,78` | `463,60,505,83` | `610,84,664,113` | `931,122,1000,160` | phím * (*Xoá): popup xoá slot 0 (193) -> Có = xoá dữ liệu slot |
| slot (3) | `17,37,335,96` | `90,37,461,106` | `131,55,608,143` | `194,73,929,209` | đặt con trỏ slot=0 + KeyPressed(-5): slot có dữ liệu -> ContinueGame (LoadSlotData) |
| popup_yes (1) | `71,120,197,143` | `101,135,297,158` | `137,179,396,208` | `383,269,594,307` | KeyPressed(-5) với con trỏ popup=Có |
| popup_no (1) | `204,120,329,143` | `304,135,499,158` | `405,179,663,208` | `607,269,817,307` | con trỏ popup=Không + KeyPressed(-5) |

**menu_chinh_lop** (`menu_chinh_lop`): MainMenu state 5 (DrawStart): Chọn lớp nhân vật (lưới 2x2 gốc + mô tả ngắn)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| class (4) | `17,37,102,127` | `90,37,187,142` | `131,55,256,189` | `194,73,384,281` | đặt con trỏ lớp (2x2 gốc)=0; chạm lần 2 hoặc OK = KeyPressed(-5) -> popup mô tả lớp |
| ok (1) | `207,192,375,215` | `305,222,501,245` | `406,293,660,322` | `605,450,995,488` | KeyPressed(-5) -> popup mô tả lớp (StatePopup) rồi OK = NewGame(lớp) |

**menu_chinh_lop_mo_ta** (`menu_chinh_lop_mo_ta`): Chọn lớp: popup mô tả lớp đầy đủ (hộp cuộn có clip) + OK/Huỷ

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| class (4) | `17,37,102,127` | `90,37,187,142` | `131,55,256,189` | `194,73,384,281` | đặt con trỏ lớp (2x2 gốc)=0; chạm lần 2 hoặc OK = KeyPressed(-5) -> popup mô tả lớp |
| ok (1) | `207,192,375,215` | `305,222,501,245` | `406,293,660,322` | `605,450,995,488` | KeyPressed(-5) -> popup mô tả lớp (StatePopup) rồi OK = NewGame(lớp) |
| desc_scroll (1) | `47,93,353,165` | `65,117,535,172` | `89,166,711,204` | `303,242,897,306` | kéo dọc = cuộn mô tả (TouchMoved mới); mô tả vẽ trong hộp CÓ CLIP (sửa lỗi tràn chữ ở DrawStart 0x11d390) |
| popup_ok (1) | `47,171,197,194` | `65,178,297,201` | `89,215,396,244` | `303,321,594,359` | KeyPressed(-5) = NewGame với lớp đã chọn |
| popup_cancel (1) | `204,171,353,194` | `304,178,535,201` | `405,215,711,244` | `607,321,897,359` | huỷ: về chọn lớp |

**menu_chinh_chuyen_gia** (`menu_chinh_chuyen_gia`): MainMenu state 6 (DrawExpert): chọn chế độ Chuyên Gia

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| mode (3) | `17,37,163,95` | `90,37,258,105` | `131,56,346,142` | `194,105,519,219` | đặt con trỏ chế độ=0 + KeyPressed(-5) (DrawExpert/KeyExpert gốc) |
| desc_scroll (1) | `177,68,375,188` | `272,68,501,218` | `363,87,660,287` | `540,110,995,442` | kéo dọc = cuộn mô tả |
| start (1) | `177,192,375,215` | `272,222,501,245` | `363,293,660,322` | `540,450,995,488` | KeyPressed(-5) -> StateInExpertMenu (màn chuẩn bị, b2b). Bỏ "Xem bảng xếp hạng" (199, cần mạng) |

**menu_chinh_cai_dat** (`menu_chinh_cai_dat`): MainMenu state 9 (DrawOption): Cài đặt, 7 mục

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| opt0_l (1) | `323,39,341,57` | `441,39,463,61` | `590,57,613,80` | `911,75,937,101` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt0_r (1) | `359,39,377,57` | `481,39,503,61` | `637,57,660,80` | `968,75,994,101` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt (15) | `17,37,317,59` | `90,37,435,63` | `131,55,581,82` | `194,73,899,103` | đặt con trỏ mục=opt0 (KeyPressed(-5) = đổi giá trị với mục bật/tắt) |
| opt1_l (1) | `323,63,341,81` | `441,67,463,89` | `590,86,613,109` | `911,107,937,133` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt1_r (1) | `359,63,377,81` | `481,67,503,89` | `637,86,660,109` | `968,107,994,133` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt2_l (1) | `299,87,317,105` | `417,95,439,117` | `566,115,589,138` | `883,139,909,165` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt2_r (1) | `359,87,377,105` | `481,95,503,117` | `637,115,660,138` | `968,139,994,165` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt4_l (1) | `299,135,317,153` | `417,151,439,173` | `566,173,589,196` | `883,203,909,229` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt4_r (1) | `359,135,377,153` | `481,151,503,173` | `637,173,660,196` | `968,203,994,229` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| note_restart (1) | `17,205,383,219` | `90,233,509,247` | `131,258,669,272` | `194,297,1006,316` | chỉ hiển thị: đổi Phân giải ghi hd_scale.txt, có hiệu lực SAU khi khởi động lại |

**menu_chinh_cai_dat_phan_giai** (`menu_chinh_cai_dat_phan_giai`): Cài đặt: mục Phân giải 1/1..1/4 (đang chọn 1/3) + ghi chú khởi động lại

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| opt0_l (1) | `323,39,341,57` | `441,39,463,61` | `590,57,613,80` | `911,75,937,101` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt0_r (1) | `359,39,377,57` | `481,39,503,61` | `637,57,660,80` | `968,75,994,101` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt (15) | `17,37,317,59` | `90,37,435,63` | `131,55,581,82` | `194,73,899,103` | đặt con trỏ mục=opt0 (KeyPressed(-5) = đổi giá trị với mục bật/tắt) |
| opt1_l (1) | `323,63,341,81` | `441,67,463,89` | `590,86,613,109` | `911,107,937,133` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt1_r (1) | `359,63,377,81` | `481,67,503,89` | `637,86,660,109` | `968,107,994,133` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt2_l (1) | `299,87,317,105` | `417,95,439,117` | `566,115,589,138` | `883,139,909,165` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt2_r (1) | `359,87,377,105` | `481,95,503,117` | `637,115,660,138` | `968,139,994,165` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| opt4_l (1) | `299,135,317,153` | `417,151,439,173` | `566,173,589,196` | `883,203,909,229` | KeyPressed(TRÁI) của mục: giảm giá trị |
| opt4_r (1) | `359,135,377,153` | `481,151,503,173` | `637,173,660,196` | `968,203,994,229` | KeyPressed(PHẢI) của mục: tăng giá trị (mục Phân giải: t10_step, 1/k vòng 1..4) |
| note_restart (1) | `17,205,383,219` | `90,233,509,247` | `131,258,669,272` | `194,297,1006,316` | chỉ hiển thị: đổi Phân giải ghi hd_scale.txt, có hiệu lực SAU khi khởi động lại |

**menu_chinh_thanh_tuu** (`menu_chinh_thanh_tuu`): MainMenu state 10 (DrawMission): Thành tựu (danh sách + chi tiết)

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| row (4) | `17,62,216,100` | `90,62,319,100` | `131,84,423,122` | `194,109,637,153` | đặt con trỏ thành tựu=0 (DrawMission/KeyMission); chạm hàng đã chọn = không có hành động (chỉ xem) |
| list_scroll (1) | `17,62,222,220` | `90,62,325,220` | `131,84,432,322` | `194,109,649,475` | kéo dọc/mũi tên: cuộn danh sách (con trỏ gốc lên/xuống) |
| desc_scroll (1) | `236,104,375,215` | `339,104,501,245` | `449,127,660,322` | `670,162,995,488` | kéo dọc = cuộn mô tả |

**menu_chinh_tro_giup** (`menu_chinh_tro_giup`): MainMenu state 11 (DrawHelp): Trợ giúp, danh sách 20 bài

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| row (6) | `17,37,377,62` | `90,37,503,62` | `131,55,660,80` | `194,73,994,101` | đặt con trỏ trợ giúp=0 + KeyPressed(-5) -> trang nội dung (DrawHelp) |
| list_scroll (1) | `17,37,383,197` | `90,37,509,251` | `131,55,669,323` | `194,73,1006,491` | kéo dọc/mũi tên: cuộn danh sách |

**menu_chinh_tro_giup_noi_dung** (`menu_chinh_tro_giup_noi_dung`): MainMenu state 11: trang nội dung Trợ giúp (bài dài nhất) + Trước/Tiếp

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |
| page_scroll (1) | `17,65,383,193` | `90,65,509,223` | `131,84,669,293` | `194,106,1006,450` | kéo dọc = cuộn trang (TouchMoved mới) |
| prev (1) | `17,200,175,223` | `90,230,274,253` | `131,302,370,331` | `194,461,562,499` | KeyPressed(TRÁI): trang trước (DrawHelp) |
| next (1) | `225,200,383,223` | `325,230,509,253` | `430,302,669,331` | `638,461,1006,499` | KeyPressed(PHẢI): trang sau |

**menu_chinh_ho_tro** (`menu_chinh_ho_tro`): MainMenu state 12: Hỗ trợ / thông tin thẩm định game

| nhóm (số phần tử) | 400x240 | 600x270 | 800x360 | 1200x540 | hành động |
|---|---|---|---|---|---|
| close (1) | `11,11,36,31` | `84,11,109,31` | `122,20,159,46` | `182,29,231,61` | KeyPressed(Back) -> state gốc |

### 13.4 Giả định cần xác nhận khi implement (T17c)
1. Icon: ô lưới `menu.mgr` #19..#27 theo thứ tự 9 ô (suy từ hình dạng: kiếm, quỷ sừng, túi, làm mới, bánh răng, "!", "?", mặt cười, tia); 5 icon lớp `menu.mgr` #28..#32 (mockup gán Warrior 29, Rogue 30, Gunslinger 31, Knight 32); khung chân dung dùng vòng `ui_new/03` + icon (gốc vẽ sprite nhân vật; thay ở T17c).
2. Giá trị mẫu Cài đặt: Nhạc nền/Âm hiệu ứng hiển thị cấp 0–5 (chưa rõ), Tốc độ game/Hiệu ứng = Cao/Trung/Thấp (chuỗi 321–323), Rung/Tự ngắm = BẬT/TẮT; ghi chú "Khởi động lại để áp dụng" lấy từ khối T10 của `vi.bin`. Mục Phân giải vẽ 4 chip 1/1..1/4 (gốc: vòng bằng TRÁI/PHẢI `t10_step`); chạm chip = lặp bước tới khi khớp.
3. Chuỗi `common_text` 210 trong dữ liệu build vẫn là "Xem ngang"; mockup dùng "Phân giải" (`common_text` 53) như T10 ghi đè lúc chạy.
4. Mô tả lớp: ngắn = `menu_text` 24–27, đầy đủ = 20–23; điểm số/LV/thời gian chơi/tiến trình thành tựu là SỐ MẪU; tên nhân vật mẫu = `common_text` 9.
5. Thành tựu: `mission_text[i]` ↔ `mission_list[i]` (i=0 là tiêu đề nhóm); trạng thái đã đạt trong ảnh là mẫu.
6. Huy hiệu 12+ (Logo) và "Tap to Continue" là ảnh gốc tiếng Anh/Hàn: T11 vẽ lại.

## 14. T16: khung hội thoại (bố cục v2)
Xem BAO-CAO-T16.md §2 (công thức theo W x H: sc = clamp(82%H / cao ảnh, 1..3), lề 8*sc, che 30% bề rộng ảnh, viền b = 2 nếu H>=480 else 1). Nút "Bỏ qua": góc trên phải màn, lề 8*s, rộng = chữ + 20b + px, cao = px + 10b (vùng chạm nới pad/2+2).
