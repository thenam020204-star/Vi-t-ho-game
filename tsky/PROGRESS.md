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
- Giai đoạn 1: T0–T2 xong. T3 xong (extract/repack/selftest, TSV 2158 hàng). T4, T5: xem PLAN.

## Quyết định đã chốt với người dùng
Hán Việt nhẹ; 仙宠 tiên sủng, 宠物 sủng vật, 骑宠 tọa kỵ; font f12; logo NAMP, bỏ hỏi nhạc; bản 320x240; tool main thêm 12px; phát hành nhánh làm việc + main/releases.
