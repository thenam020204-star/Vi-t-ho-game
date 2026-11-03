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
