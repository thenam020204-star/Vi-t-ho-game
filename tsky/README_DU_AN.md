# Việt hoá "Tiên Sủng Kỳ Duyên" (仙宠奇缘) — dự án J2ME

Game Java ME (CLDC 1.0 / MIDP 2.0), 2 bản: **240x320** và **320x240**. Font Việt `f12.bin` (12px) vẽ bằng lớp hook `VH`, patch bytecode tĩnh bằng ASM. Không biên dịch lại class gốc.

## Dựng lại
```
./rebuild.sh                 # goc/ -> out/TienSungKyDuyen_{240x320,320x240}_vi.{jar,jad}  (xoá build/ + out/*.jar trước)
RUN_EMU=1 ./rebuild.sh       # thêm bước chạy FreeJ2ME (logo -> menu -> CG), ảnh ở out/emu_rebuild/
TSV=file.tsv ./rebuild.sh    # bản dịch khác (mặc định dich/dp_work/final/text_can_dich.tsv)
```
Cần: JDK đầy đủ (javac, jmod), Python 3 + Pillow, zip/unzip. Chạy emulator: Xvfb, ImageMagick, python-xlib. Tool: `tools/` (trong gói zip) hoặc `work/tools/` (xem `tools/VERSIONS.md`); script tự tìm qua `scripts/env.sh` (đường dẫn tương đối theo thư mục dự án).

## Bản đồ thư mục
| Thư mục / file | Nội dung |
|---|---|
| `PLAN.md` | Nguồn sự thật: quyết định thiết kế (QĐ 1–12), task T0–T10, nhật ký, vướng mắc |
| `PROGRESS.md`, `BAO_CAO.md` | Tiến độ ngắn; báo cáo + mục Debug |
| `goc/` | Jar gốc, jad sinh từ jar gốc, `text_can_dich.tsv` gốc (chưa dịch), `anh_goc/` (ảnh gốc trước khi vẽ lại), logo NAMP |
| `dich/` | `text_can_dich.tsv` (khung), `dp_work/final/text_can_dich.tsv` (**bản dịch chốt**), `thuat-ngu.tsv`, `vai.md`, `ghi-chu.txt` |
| `scripts/` | `extract.py` / `repack.py` (pak, `t/item*.t`, chuỗi class ↔ TSV; `repack --check`), `make_img*.py` (vẽ lại ảnh), `preverify.sh`, `jar_check.py`, `run_emu.py`, `t7_emu.py` (chụp màn có `--cwd/--follow`), `t7_test_jar.py`, `make_jad.py`, `dp.py`… |
| `hook/VH.java` | Lớp hook: renderer f12, ngắt dòng, thoại, cuộn trang, phím mềm, `keyEq`… |
| `patcher/Patch.java` | ASM: thay call-site, thân method, bố cục, khởi động NAMP, phím 320 |
| `res_vi/`, `res_vi_320/` | Ảnh đã vẽ lại (đè lên jar); `res_vi_320` đè thêm cho bản 320x240 |
| `build.sh`, `build_hook.sh`, `rebuild.sh` | Dựng 2 jar / biên dịch hook+patcher / dựng sạch + jad |
| `out/` | Jar + jad cuối, `emu_rebuild/` |
| `logs/` | `jar_check_*.txt`, `patch.log` (mọi chỗ vá + số khớp), `shots_T4..T9/` (ảnh FreeJ2ME) |
| `docs/` | `script.md` (cấu trúc pak/lệnh), `render.md` (vẽ chữ, bố cục, kết quả T4/T5/T7/T9), `anh_co_chu.tsv` |
| `src/` | Mã CFR của các class đã vá (Main/a,e,f,g,j,k, rf/b,c,e) |

## Thứ tự các bước (khi làm lại từ đầu)
1. `extract.py` jar gốc → `text_can_dich.tsv` (2158 hàng); `docs/script.md` giải thích mã lệnh.
2. Dịch (`dp`, một người dịch) → `dich/dp_work/final/`.
3. `repack.py` ghi bản dịch vào pak/item/class (kiểm charset f12, token, `^ | #`).
4. `Patch` (ASM) trên class đã repack: call-site `drawString/stringWidth/…` → `VH.*`; thay thân `Main/g.a`, `rf/b.a/b`, viền chữ; khởi động NAMP; bố cục T7; phím T9.
5. Thêm `VH.class` + `font/f12.bin` + ảnh `res_vi*`; zip → `preverify.sh` (ProGuard `-microedition`) → `jar_check.py`.

## Quyết định chính (chi tiết ở PLAN.md)
- Font f12 (228 ký tự, 12 px), vẽ qua atlas màu + `drawRegion`; chữ Hán/dấu câu Hán ngoài font = ô trống rộng 12 px.
- Phương án 2 (hook bytecode tĩnh): không đổi tên/descriptor/access flag, class giữ version 45.3.
- Văn phong Hán Việt nhẹ; "空" giữ nguyên trong class/RMS, `VH` vẽ thành "Trống".
- Logo NAMP thay logo nhà phát hành, bỏ hỏi nhạc (QĐ 9). Bản 320x240 dùng chung class, khác `MIDlet-Description: rpg,E62` + ảnh (QĐ 10).
- Ẩn mục "Game khác" (liên kết web quảng cáo) khỏi menu chính.
