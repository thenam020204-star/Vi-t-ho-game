# Dự án Việt hoá C&C: Red Alert Mobile

```
goc/cnc.jar            jar gốc (đầu vào duy nhất)
out/                   cnc_vi.jar + cnc_vi.jad, text_can_dich.tsv (id, gốc PT, dịch, ghi chú PL/RU),
                       thuat-ngu.tsv, vai.md, shots/ (FreeJ2ME), tong_hop_man_hinh.png
src_vh/NampMIDlet.java lớp splash (điểm vào mới)
scripts/
  dfpack.py            đọc/ghi kho resource EA (/index + /dfK_0)
  textpack.py          bảng ngôn ngữ (res #16) + file /t_<lang>; round-trip byte-for-byte
  make_f17.py          sinh f17.bin từ f18.bin (dùng fontgen/build_fonts.py của bộ tool)
  make_font.py         f17.bin -> font chunk EA + atlas PNG (giữ PLTE/tRNS gốc)
  build_vi.py          ráp jar: text vi, font, manifest, splash
  make_jad.py          JAD khớp MIDlet-Jar-Size
  preverify.sh, jar_check.py, classfile.py, setup_tools.sh   (từ skill j2me-viet-hoa)
  run_emu.py           FreeJ2ME headless (đã sửa tham số + lệnh ekey)
rebuild.sh             chạy lại toàn bộ: ./rebuild.sh  (hoặc --no-emu)
logs/                  inventory, df_list, jar_check, binary_diff, class_moi, run_emu.json
BAO_CAO.md, PROGRESS.md
```

## Thứ tự
1. `scripts/setup_tools.sh Tool_Viet_Hoa_NAM_P.zip work` (tự gọi trong rebuild.sh nếu thiếu `work/tools`).
2. Sửa bản dịch → sửa `out/text_can_dich.tsv` (cột 3) → `./rebuild.sh`.

## Quyết định
- Font: **17px có chân** (f18 nén 1 hàng) theo yêu cầu, viền đen 1px như font gốc; chữ thường.
- Phương án: **ghép vào font gốc của engine EA** (thay font chunk + atlas), không dùng BitmapFont, không sửa class gốc nào.
- Slot ngôn ngữ `pt` được thay bằng `vi`, `Force-Locale: vi` để vào thẳng tiếng Việt; 4 ngôn ngữ còn lại giữ nguyên.
