# pixeltext — bộ công cụ vẽ chữ pixel tiếng Việt cho AI

Vẽ chữ tiếng Việt có dấu bằng font bitmap `fontcus` (bản rebuilt v2), **3 cỡ: 10px / 13px / 15px**.
Thiết kế để một AI agent dùng được ngay: đo trước khi vẽ, tự chọn cỡ vừa ô,
xem kết quả bằng ASCII art (không cần mở ảnh), và vá chữ vào ảnh có sẵn mà giữ nguyên màu gốc.

```
pixeltext/
  fonts/font_10px.bin  font_13px.bin  font_15px.bin  font_15px_wide.bin
  container.py   đọc/ghi định dạng font.bin của fontcus
  font.py        Font, Glyph, metric, phát hiện glyph trùng nhau
  metrics.py     dàn chữ, xuống dòng, đo, tự chọn cỡ vừa ô
  paint.py       vẽ ra ảnh, viền, bóng, gradient, ASCII art, vá vào ảnh có sẵn
  cli.py         giao diện dòng lệnh
examples/        demo.py, game_menu.py
selftest.py      kiểm tra toàn bộ
```

Yêu cầu: Python 3.8+, Pillow. Không cần cài đặt, chỉ cần thư mục `pixeltext/` nằm cạnh script.

---

## 1. Ba cỡ font

| cỡ | chiều cao ô | cap | x-height | baseline | bề rộng glyph | ký tự |
|---|---|---|---|---|---|---|
| 10px | 10 | 7 | 5 | 7 | 2–10 (tỉ lệ) | 227 |
| 13px | 12 | 7 | 5 | 9 | 2–10 (tỉ lệ) | 230 |
| 15px | 15 | 8 | 5 | 12 | 4–6 | 227 |
| `'15w'` | 15 | 8 | 5 | 12 | 4–7 | 227 (bản v2 gốc, rộng hơn) |

Bề rộng chữ `Trò chơi mới`: **10px = 51 px · 13px = 57 px · 15px = 68 px**.

### ⚠ Cỡ 10px KHÔNG vẽ được dấu trên chữ HOA

Ô chỉ cao 10 px, chữ hoa đã chiếm hàng 1–7 nên không còn chỗ đặt dấu.
`Á À Ả Ã Ă Â Ế Ố Ú Ý …` bị vẽ **y hệt** `A E O U Y`. Một số chữ thường cũng trùng
(`ắ = ằ = ẳ = ẵ`, `â = ẩ`, `ê = ễ`, `ô = ỗ`).

```
$ python3 -m pixeltext audit
10px: 71 glyph trùng, 17 nhóm      13px: 8 (chỉ ! ！ / : ： / ? ？ / , ;)      15px: 4 (, ‚ và 0 O)
```

**Quy tắc dùng:** ở 10px hãy viết **chữ thường hoặc Viết Hoa Đầu Từ**, đừng viết IN HOA.
Ở 15px tránh đặt `0` cạnh `O` vì hai glyph giống hệt nhau.
Luôn chạy `check` trước khi chốt một chuỗi.

---

## 2. Dùng bằng dòng lệnh (khuyên dùng cho AI)

```bash
# chữ này có vẽ được không? có bị nhầm glyph không?
python3 -m pixeltext check   --text "Trò chơi mới"

# rộng bao nhiêu px ở cả 3 cỡ?
python3 -m pixeltext measure --text "Trò chơi mới" --size all

# xem thẳng bằng ASCII, không cần mở ảnh
python3 -m pixeltext show    --text "Trò chơi mới" --size 13

# cỡ nào lớn nhất mà vẫn lọt ô 64x16?
python3 -m pixeltext fit     --text "Trò chơi mới" --box 64x16
# -> {"size": 15, "mode": "tight", "gap": 0, "w": 61, "h": 15, ...}

# xuất PNG
python3 -m pixeltext draw    --text "Trò chơi mới" --size 13 --scale 4 \
        --gradient "#9ef7ff,#1f7fd0" --outline "#04121f" --shadow "#000000a0" \
        --out title.png

# ba cỡ cạnh nhau
python3 -m pixeltext compare --text "Trò chơi mới" --out compare.png

# bảng ký tự đầy đủ
python3 -m pixeltext sheet   --size 13 --out sheet13.png

# thay chữ trong ô của một ảnh có sẵn, tự lấy lại màu nền/màu chữ/viền của ảnh
python3 -m pixeltext patch --image tab.png --box 6,4,70,17 --text "Nhiệm vụ" --auto --out tab_vi.png
```

Mọi lệnh trả JSON ra stdout nên agent parse được. `check` và `fit` trả **exit code 1**
khi có vấn đề (thiếu glyph / glyph trùng / không lọt ô).

---

## 3. Dùng bằng Python

```python
import pixeltext as pt

pt.measure("Trò chơi mới", 13)          # {'w': 57, 'h': 12, 'lines': 1, ...}
pt.Font.load(13).missing("Xin chào ★")  # ['★']
pt.Font.load(10).ambiguous_in("TRÒ")    # [('Ò', 'OÓÕÔỔỖ')]  -> 10px không có dấu chữ hoa

# 1) ảnh mới
img = pt.render("Trò chơi mới", size=15, scale=3,
                fill=["#ffe9a3", "#ff8a3c"],       # gradient trên->dưới
                outline="#2b1200", shadow="#00000080")
img.save("title.png")

# 2) vẽ vào ảnh có sẵn, canh trong ô
info = pt.draw(canvas, box=(8, 40, 120, 16), text="Bắt đầu", size=13,
               align="center", valign="center", fill="#7CFF6B", outline="#0a3d14")
info["overflow_x"]        # > 0 nghĩa là chữ tràn ô

# 3) tự chọn cỡ cho vừa ô rồi vẽ
plan = pt.fit("Nhiệm vụ chính", (70, 17))
pt.draw(canvas, (6, 4, 70, 17), plan["text"], size=plan["size"],
        mode=plan["mode"], gap=plan["gap"], scale=plan["scale"])

# 4) thay chữ trong artwork, giữ nguyên bảng màu + gradient + viền của ảnh gốc
pt.patch(ui, (6, 4, 70, 17), "Nhiệm vụ", fill_rule="mode")

# 5) xuống dòng theo bề rộng pixel
print(pt.wrap("Đội Red Hawk đã sẵn sàng, chờ lệnh xuất kích.", 200, 13))
```

### Ba chế độ giãn chữ (`mode`)

| mode | ý nghĩa | dùng khi |
|---|---|---|
| `metric` *(mặc định)* | dùng đúng bề rộng advance mà người vẽ font đặt | văn bản bình thường |
| `tight` | xếp sát theo nét thật + `gap` px | ô UI chật, cần nhét thêm 1–2 ký tự |
| `fixed` | mọi ký tự chiếm đúng `cell` px, canh giữa | bảng số, HUD cần thẳng cột |

### Màu

`fill` nhận: `"#RRGGBB"` / `"#RRGGBBAA"` / tuple RGBA / **list = gradient dọc**
(`["#fff","#08f"]`) / dict `{hàng: màu}` / hàm `f(y) -> RGBA`.
`outline`, `shadow` nhận màu đơn. `outline_diag=True` để viền cả 8 hướng.

---

## 4. Vá chữ vào artwork game (giữ nguyên bảng màu)

`sample_style()` đọc ngược từ chính ô ảnh: màu nền (pixel góc), màu chữ **theo từng hàng**
(giữ gradient dọc), màu viền (màu tối nhất). `patch()` xoá ô rồi vẽ lại bằng đúng bộ màu đó,
nên chữ mới trông như do cùng một hoạ sĩ vẽ.

```python
pt.patch(img, (0, 0, 38, 14), "Công", fill_rule="bright")  # giữ gradient
pt.patch(img, (0, 0, 199, 46), "THẤT BẠI", fill_rule="flat", scale=3,
         fill="#c60c0c", outline="bright")                 # banner lớn
```

`fill_rule`: `bright` (sáng nhất mỗi hàng — giữ gradient) · `mode` (màu phổ biến nhất —
bỏ qua viền) · `flat` (một màu cho cả khối).

---

## 5. Lưu ý khi đưa font vào máy J2ME thật

Nếu vẽ chữ để nhét vào bảng glyph 8×16 của engine J2ME (2bpp, đọc `w = (b0<<8)|b1`):
**cột 7 phải để trống**. `b0` bị sign-extend nên nếu bit 15 bật thì engine vẽ thừa
một cột đè lên ký tự kế tiếp. Ba font ở đây rộng tối đa 10 px nên phải kiểm tra khi đóng gói.

---

## 6. Tự kiểm tra

```bash
python3 selftest.py          # 30+ assert: codec, metric, wrap, fit, render, patch
python3 examples/demo.py     # "Trò chơi mới" ở 3 cỡ + ASCII
python3 examples/game_menu.py  # màn hình menu game 240x320 dựng ở cả 3 cỡ
```
