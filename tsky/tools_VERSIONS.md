# Công cụ đã dùng (phiên bản, nguồn)
Có sẵn trong `tools/` của gói: `fonts/` (f12..f18), `fontgen/`, `java/` (BitmapFont 12 px, class v47), `pixeltext_kit/`, `check_all.sh`, `freej2me_plus.jar` (từ NAM_P/freej2me), `cfr.jar`, `proguard.jar`, `lib/` (ASM, ecj), `env.sh`.
Bộ NAM.P: `tool_main/Tool_Viet_Hoa_NAM_P.zip` (nhánh làm việc, đã thêm 12 px).

| File | Phiên bản | Nguồn |
|---|---|---|
| `proguard.jar` | ProGuard 7.4.2 | https://github.com/Guardsquare/proguard/releases (proguard-7.4.2.tar.gz, lib/proguard.jar) |
| `cfr.jar` | CFR (Maven, build JDK 10) | https://github.com/leibnitz27/cfr/releases |
| `lib/asm-9.7.1.jar`, `asm-tree-9.7.1.jar` | ASM 9.7.1 | https://repo1.maven.org/maven2/org/ow2/asm/ |
| `lib/ecj-3.33.0.jar` | Eclipse ECJ 3.33.0 | https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.33.0/ |
| `freej2me_plus.jar` | FreeJ2ME-Plus nightly (trong NAM_P/freej2me) | https://github.com/TASEmulators/freej2me-plus |

Không kèm gói (cài trên máy): JDK 21 đầy đủ (`javac`, `jmod`; `rebuild.sh` bóc `java.base.jmod` vào `build/jbase`), Python 3.13 + Pillow 12.3 + python-xlib, Xvfb, ImageMagick, zip/unzip. Skill `j2me-viet-hoa` cung cấp bản gốc của `preverify.sh`, `jar_check.py`, `classfile.py`, `run_emu.py`, `dp.py`, `inventory.py` (đã chép vào `scripts/`).
