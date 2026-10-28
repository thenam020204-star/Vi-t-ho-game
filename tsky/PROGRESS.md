# PROGRESS — Tiên Sủng Kỳ Duyên

Đọc `PLAN.md` trước. File này chỉ ghi tiến độ ngắn cho phiên sau.

## Dựng lại môi trường (work/ bị .gitignore)
```
S=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa/scripts
bash $S/setup_tools.sh ../Tool_Viet_Hoa_NAM_P.zip work      # nếu env.sh có đường dẫn tương đối thì sửa thành tuyệt đối
source work/tools/env.sh
mkdir -p work/x && (cd work/x && unzip -oq ../../goc/TienSungKyDuyen_240x320.jar)
env -u JAVA_TOOL_OPTIONS java -jar $CFR goc/TienSungKyDuyen_240x320.jar --outputdir src --silent true
```

## Trạng thái
- T0–T5 xong (phân tích, extract/repack, VH+Patch+build, logo NAMP). T6 dịch xong (bản chốt `dich/dp_work/final/text_can_dich.tsv`, build mặc định dùng nó). T8/T8b ảnh xong (A2). T7 bố cục xong: `logs/shots_T7/` (25 ảnh), docs/render.md mục 10, jar_check PASS. Việc dịch còn nhờ công nhân dịch rút tên quá ô (PLAN.md, Vướng mắc T7). Tiếp: T9 (320x240), T10, T11.
- Dựng jar: `./build.sh` (→ `out/TienSungKyDuyen_240x320_vi.jar`). Chụp màn: `scripts/t7_emu.py --cwd work/fjsave --follow work/cmd.txt ...` (xem docs/render.md mục 10); save thử ở `work/fjsave` (không commit).

## Quyết định đã chốt với người dùng
Hán Việt nhẹ; 仙宠 tiên sủng, 宠物 sủng vật, 骑宠 tọa kỵ; font f12; logo NAMP, bỏ hỏi nhạc; bản 320x240; tool main thêm 12px; phát hành nhánh làm việc + main/releases.
