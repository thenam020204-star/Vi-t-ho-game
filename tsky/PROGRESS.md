# PROGRESS — Tiên Sủng Kỳ Duyên

Đọc `PLAN.md` trước. File này chỉ ghi tiến độ ngắn cho phiên sau.

## Dựng lại môi trường
```
./rebuild.sh                 # từ goc/ ra out/ (2 jar + 2 jad); RUN_EMU=1 để chạy FreeJ2ME
sh tools/check_all.sh        # (gói zip) hoặc work/tools/check_all.sh: kiểm bộ tool
```
Tool: `tools/` (trong gói) hoặc `work/tools/` (xem `tools/VERSIONS.md`; bộ NAM.P ở `tool_main/Tool_Viet_Hoa_NAM_P.zip`, thư viện ASM/ecj ở `<tools>/lib`). `scripts/env.sh` tự tìm đường dẫn. Mã CFR: `env -u JAVA_TOOL_OPTIONS java -jar $CFR goc/TienSungKyDuyen_240x320.jar --outputdir src --silent true`.

## Trạng thái
- T0–T5 xong (phân tích, extract/repack, VH+Patch+build, logo NAMP). T6 dịch xong (bản chốt `dich/dp_work/final/text_can_dich.tsv`, build mặc định dùng nó). T8/T8b ảnh xong (A2). T7 bố cục xong: `logs/shots_T7/` (25 ảnh), docs/render.md mục 10, jar_check PASS. Việc dịch còn nhờ công nhân dịch rút tên quá ô (PLAN.md, Vướng mắc T7). T7b (cửa hàng) và T9 (320x240, 2 jar PASS, ảnh `logs/shots_T9/`) xong. T10 xong (rebuild.sh, jad, BAO_CAO.md, README_DU_AN.md, gói `out/TienSungKyDuyen_du_an.zip` bằng `scripts/pack_du_an.sh`). Tiếp: T11 (quản đốc: tool main 12 px + phát hành).
- Dựng jar: `./build.sh` (→ `out/TienSungKyDuyen_240x320_vi.jar`). Chụp màn: `scripts/t7_emu.py --cwd work/fjsave --follow work/cmd.txt ...` (xem docs/render.md mục 10); save thử ở `work/fjsave` (không commit).

## Quyết định đã chốt với người dùng
Hán Việt nhẹ; 仙宠 tiên sủng, 宠物 sủng vật, 骑宠 tọa kỵ; font f12; logo NAMP, bỏ hỏi nhạc; bản 320x240; tool main thêm 12px; phát hành nhánh làm việc + main/releases.
