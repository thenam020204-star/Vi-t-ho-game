# Dự án Việt hoá Hùng Bá Tam Quốc: Thiên Giáng Nữ Thần (J2ME, N73 240×320)

## Bản đồ thư mục
| Thư mục | Nội dung |
|---|---|
| `goc/` | `game.jar` gốc N73, `game_e62.jar` gốc E62, `text_goc.tsv` (625 dòng resource gốc), `class_goc.tsv` (433 chuỗi .class gốc) |
| `out/` | Jar + jad cuối, `shots/` ảnh chụp FreeJ2ME, `moc1/` mốc 1 |
| `text/` | `text_da_dich.tsv`, `class_da_dich.tsv` (bản dịch — sửa ở đây rồi chạy `rebuild.sh`), `thuat-ngu.tsv`, `vai.md`, `ghi-chu.txt`, `kiem.py` |
| `assets/img/` | 16 PNG rời đã Việt hoá + ảnh so sánh + `anh_da_sua.tsv` |
| `assets/img_e62/` | Ảnh riêng bản E62 (`diui.png` 320×36) đè lên `assets/img` |
| `assets/img2/` | `uires.bin`, `animation.bin` đã thay PNG nhúng + `compare/` + `anh_nhung.tsv` |
| `src_patch/` | `bs.java` (renderer font, viết lại), `vh.java` (lớp mới: ngắt dòng, khung thoại, chữ chạy) |
| `tools_src/Patcher.java` | Sửa bytecode tĩnh bằng ASM (danh sách chỗ sửa ở `logs/patcher.txt`) |
| `scripts/` | `build_font12.py`, `repack.py`, `set_clinit_flags.py`, `patch_uiform.py`, `run_emu.py`, `preverify.sh`, `jar_check.py`; `text/` (textpack, classstr, check_skill_ph, binutf); `img/`, `img2/` (script vẽ ảnh) |
| `logs/` | `rebuild.txt`, `jar_check.txt`, `patcher.txt`, `inventory.txt`, log FreeJ2ME |
| `Tool_Viet_Hoa_NAM_P.zip` | Bộ tool người dùng (font f12, FreeJ2ME-Plus, pixeltext) |

## Dựng lại
```bash
bash rebuild.sh all        # N73 + E62 → out/HungBaTamQuoc_{N73,E62}_viet.jar (+jad), tự jar_check
bash rebuild.sh e62        # chỉ E62 (run_emu thêm --size 320x240)
python3 -I scripts/run_emu.py --fj lib/NAM_P/freej2me/freej2me_plus.jar --jar out/HungBaTamQuoc_N73_viet.jar \
    --out shots --steps "wait 12; key w; wait 5; shot menu; key Return; wait 4; shot luu"
```
Cần JDK 11+ đầy đủ (javac, jmod), python3, zip/unzip, curl (tải ecj 3.26.0, ASM 9.7, ProGuard 7.4.2 vào `lib/`).
FreeJ2ME cần Xvfb, ImageMagick, `pip install python-xlib pillow`.

## Thứ tự các bước trong rebuild.sh
1. font 12px → 2. text resource (`textpack.py pack`) → 2b. `uiform.bin` → 3. ảnh → 4. chuỗi .class (`classstr.py patch`, chỉ đổi constant pool) → 5. ASM `Patcher` + ecj `-source 1.3 -target 1.3` cho `bs`, `vh` → 6. preverify riêng class sửa bytecode → 7. đóng jar + jad → 8. `jar_check`.

## Quyết định kỹ thuật
- **Phương án 1** (ghép font vào renderer gốc, không dùng `BitmapFont`): game có renderer bitmap riêng 12px (`bs`, Nokia `DirectGraphics.drawPixels`), cỡ chọn trùng chiều cao gốc.
- Sửa code bằng **thay thân method / thay lệnh gọi** (ASM) trỏ sang class mới `vh`, không biên dịch lại class lớn từ decompile. Không đổi tên/descriptor/access flag của member có sẵn; `vh` chỉ dùng field/method public của game (field private của `n` được đọc/ghi trong chính thân method `n` đã thay).
- Luật dữ liệu quan trọng khi sửa bản dịch: xem `BAO_CAO.md` mục Kiểm tra và `text/kiem.py`, `scripts/text/check_skill_ph.py`.
