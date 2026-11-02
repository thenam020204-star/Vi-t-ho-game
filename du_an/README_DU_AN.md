# Dị Thế Tà Quân (异世邪君) 240x320 — dự án Việt hoá (NAM.P)

Bản Việt hoá chạy trên J2ME thật (CLDC 1.0 / MIDP 2.0). Đã gỡ toàn bộ nội dung nhà mạng: logo, SMS/thương thành, kết nối mạng, quảng cáo "thêm game", màn hỏi âm thanh (âm thanh tự bật).

## Dựng lại
```
bash rebuild.sh          # goc/game.jar -> out/Di_The_Ta_Quan_VH_240x320.jar + .jad, kèm chạy thử FreeJ2ME
bash rebuild.sh --no-emu # bỏ bước chạy thử
```
Cần: JDK 9+ đầy đủ (có `jmods/java.base.jmod`), python3 + Pillow, numpy + opencv-python-headless (chỉ để vẽ lại bản đồ), zip/unzip.
Chạy thử thì cần thêm Xvfb, ImageMagick, python-xlib.

## Thứ tự các bước (rebuild.sh)
1. Giải nén `goc/game.jar` -> `jar_x/`.
2. `make_res.sh`: sinh `res_vi/`
   - `dp check` + `dp merge` (bản dịch ở `dich/dp_work/`), sau đó `scripts/apply_tl.py` (ghi đè trang Giới thiệu + chuẩn hoá dấu câu).
   - `scripts/repack.py` -> `string.pak`. Định dạng trunk: varint7 độ dài, rồi short count, short len[], UTF-8.
   - `scripts/make_menu_strip.py` + `make_splash.py` -> `splashAnim`: ảnh bìa mới `assets/cover_vi.webp`, giữ vệt mực gốc, nhãn menu chính.
   - `scripts/make_png7.py` -> atlas UI `png7` + `anim.bin`: vẽ lại 94 module chữ, kiểu B (thân chữ + viền 1px đúng màu gốc).
   - `scripts/make_small.py` -> `LOAD` (Đang tải), `png74` (Bí kíp), `png43` (biểu tượng cửa hàng), `png55` (biển hiệu).
   - `scripts/make_map.py` -> bản đồ thế giới `png64`.
   - `res_vi/p0` là logo NAMP (đặt tay).
   - `res_vi/link` là PNG 1x1 trong suốt: game vẫn nạp `/link` dù đã bỏ màn quảng cáo.
3. `build.sh`:
   - `build_hook.sh` biên dịch `hook/VH.java` (ecj -source/-target 1.3) và `patcher/Patch.java` (ASM).
   - `Patch` vá `c.class` + `i.class`. Mỗi patch kiểm số chỗ khớp, sai là dừng. Log ở `logs/patch.log`.
   - Đóng jar (bỏ source .java gốc và ảnh nhà mạng), `scripts_skill/preverify.sh` (ProGuard -microedition), `scripts_skill/jar_check.py`.
4. Chép ra `out/` + tạo `.jad` (MIDlet-Jar-Size khớp), chạy `scripts/run_emu.py`.

## Quyết định đã chọn
- **Văn phong**: theo tiểu thuyết Dị Thế Tà Quân (Phong Lăng Thiên Hạ), Hán Việt cổ trang. Xưng hô ở `dich/dp_work/vai.md`, thuật ngữ ở `dich/dp_work/thuat-ngu.tsv`.
- **Font**: f12.bin (12px) của bộ tool NAM.P, theo lựa chọn của người dùng.
  - Vẽ bằng class `VH`: atlas cố định 3 màu, màu khác vẽ bằng fillRect theo run, nên không dựng lại atlas mỗi khung hình. Không dùng BitmapFont vì lớp đó chỉ có cỡ 13/15/18.
  - Tạo thêm `VH` là một bổ sung, không đổi tên/descriptor/access flag của class gốc nào.
  - Ngắt dòng theo px ở dấu cách. Hội thoại tăng từ 2 lên 3 dòng.
- **Chữ trong ảnh**: kiểu B (giữ viền như nhãn gốc), người dùng chọn vì chữ một màu trên nền giấy be khó đọc.
- **Phương án patch**: hook bytecode tĩnh (ASM, COMPUTE_MAXS, giữ class 45.3, bỏ StackMap cũ, ProGuard sinh lại). Lý do: decompile cả game để biên dịch lại thì rủi ro lệch hành vi.

## Bản đồ thư mục
| Thư mục | Nội dung |
|---|---|
| `goc/` | jar gốc, TSV gốc, ảnh gốc trước khi vá (`goc/anh`), ảnh chụp màn nhà mạng cũ |
| `out/` | jar + jad cuối, `text_da_dich.tsv`, `thuat-ngu.tsv`, `vai.md`, `shots/` (ảnh FreeJ2ME từng màn) |
| `dich/` | dữ liệu dp (bản dịch tay), `overrides.tsv`, `charset_f12.txt` |
| `hook/VH.java` | class hook chữ (CLDC) |
| `patcher/Patch.java` | patch ASM, có chú thích từng patch |
| `scripts/` | toàn bộ script đã viết cho game này |
| `scripts_skill/` | script của skill (dp, preverify, jar_check; `run_emu.py` đã sửa tham số FreeJ2ME-Plus nằm ở `scripts/`) |
| `src/decompile` | CFR của các class đã đụng tới |
| `src/goc_java` | source Java gốc có sẵn trong jar (GBK -> UTF-8), tra tên hàm/hằng |
| `res_vi/` | tài nguyên Việt hoá đã sinh |
| `tools/` | bộ tool đã dùng, xem `tools/VERSIONS.md` |
| `logs/` | inventory, patch, jar_check, các lượt chạy FreeJ2ME |
