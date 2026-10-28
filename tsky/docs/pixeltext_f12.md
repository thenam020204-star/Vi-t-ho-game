# pixeltext cỡ 12 (cho quản đốc đưa vào tool main)

`work/tools/pixeltext-kit/pixeltext/font.py` gốc chưa có f12. Sửa duy nhất một dòng:

```
SIZES = (18, 15, 13, 12, '15s', '13s', '15c', '13c')   # thêm 12
```

`FONTS` sinh tự động `f12.bin` từ `$FONT_DIR` (file đã có trong `work/tools/fonts/`). `height_of(12)=12`, `style_rank` coi là có chân (rank 2, duy nhất một kiểu). CLI `--size 12` chạy được (`_size` đọc số nguyên). `fit` mặc định `--sizes` vẫn là danh sách cũ, thêm `12` nếu cần. Đã chạy `pixeltext check --size 12` cho 80 nhãn ảnh: sạch, chỉ `0` trùng `O` (chỉ dùng chữ số trong `number1.png`).
