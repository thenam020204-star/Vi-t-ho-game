# Việt hoá "Tinh Vân Truyền Thuyết" (星际传说-命运轮回, Hoa Ngu) — dự án J2ME

3 bản: **240x320** (dọc), **320x240** (ngang, Nokia E62), **360x640** (màn 640x360 ngang, cảm ứng). MIDP 2.0 / CLDC 1.1 (như bản gốc).
Patch bytecode tĩnh bằng ASM qua lớp hook `src/VH`; không biên dịch lại class gốc, không đổi tên/descriptor/access flag.

## Dựng lại
```
./rebuild.sh              # goc/ -> out/TinhVanTruyenThuyet_{240x320,320x240,360x640}_vi.{jar,jad}
RUN_EMU=1 ./rebuild.sh    # thêm chạy FreeJ2ME (menu -> vào game), ảnh ở logs/rebuild_<bản>/
./build.sh 240|320|360    # dựng 1 bản (dùng res_vi_<bản> đã có)
scripts/merge.sh          # sau khi sửa bản dịch bằng dp (dich/dp_work) -> dich/dp_work/final/text_can_dich.tsv
```
Cần: JDK 21 đầy đủ (javac, jmod), Python 3 + Pillow (+ python-xlib, Xvfb, ImageMagick để chạy emulator), zip/unzip. Tool ở `tools/` (xem `tools/VERSIONS.md`).

## Bản đồ thư mục
| Đường dẫn | Nội dung |
|---|---|
| `PLAN.md` | Quyết định thiết kế (QĐ), task, vướng mắc |
| `PROGRESS.md`, `BAO_CAO.md` | Tiến độ; báo cáo + Debug + việc tồn |
| `goc/` | 3 jar gốc |
| `dich/` | `text_can_dich.tsv` (trích từ 3 bản, 5685 hàng), `dp_work/` (bản dịch tay, `final/` = bản chốt, `thuat-ngu.tsv`, `vai.md`), `charset_f12.txt` |
| `scripts/` | `extract.py`/`repack.py`/`datafmt.py`/`hanlib.py` (text ↔ resource/class), `qa.py`, `merge.sh`, `make_img.py` (vẽ lại ảnh), `build_vhm.py` (bảng ô chữ), `make_manifest.py`, `post_stage.sh`, `measure.py`, `run_emu.py`/`t7_emu.py`/`live_*.sh` (emulator), `preverify.sh`, `jar_check.py`, `dp.py`… |
| `hook/VH.java` | Renderer font Việt + ngắt dòng px + chạy chữ + hook ô chữ atlas + logo khởi động NAMP |
| `patcher/Patch.java` | ASM: cấu hình từng bản (240/320 = class `b/q/m/o/v`; 360 = `GameCanvas/SplashCanvas/Util/GameMenu/MapManager`) |
| `docs/module_chu*.tsv` | Ô chữ trong atlas ảnh (ảnh, x, y, w, h, kiểu, chữ Việt) -> `font/vhm.dat` |
| `res_vi_<bản>/` | Ảnh đã vẽ lại (sinh bởi `make_img.py`) |
| `out/` | jar + jad cuối |
| `logs/` | `patch_*.log` (mọi chỗ vá + số khớp), `jar_check_*.txt`, `check_all.txt`, ảnh FreeJ2ME |
| `src/` | mã CFR các class đã vá (tham khảo) |

## Quyết định chính (chi tiết PLAN.md)
- Văn phong **hiện đại**; tên riêng kiểu Latin (Nolan, Nebu, Meda, Hippolyte, Frederick, Công tước Schwab…), thuật ngữ trong `dich/dp_work/thuat-ngu.tsv`.
- Font **f12** (bản 240/320, = chiều cao hzk12 gốc). Bản 360 font gốc 16px, cách dòng 20 -> dùng **f15c** (cùng bộ NAM_P).
- Thoại dạng `Tên：nội dung`: giữ đúng dấu `：` (U+FF1A) vì code tách tên bằng `indexOf('：')` và so `equals` tên để chọn chân dung; VH vẽ `：` thành `:`.
- Logo NAMP thay màn upload/hỏi nhạc/logo nhà phát hành; nhạc luôn bật; ẩn mục "Thêm game" (mở web); manifest bỏ quảng cáo wap.
