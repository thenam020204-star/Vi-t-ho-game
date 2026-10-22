# Tiến độ: Việt hoá Hiên Viên Kiếm – Thiên Chi Ngân (chương 2)

## Mục tiêu
Jar tiếng Việt chạy trên KVM thật (N73, MIDP2/CLDC1.0, 240×320). Chi tiết task: PLAN.md.

## Trạng thái hiện tại
- Giai đoạn: trích text (T1–T3, công nhân A1)
- Bước tiếp theo: quản đốc dịch bằng `dp` trên `work/text/text_can_dich.tsv`; giao T4 (hook font) cho A1.

## Quyết định
- Phong cách: Hán Việt kiếm hiệp. Font: f15c (BitmapFont, SELECTIVE, 15).
- Font gốc: hệ thống `Font.getFont(0,0,8)` tại `e/a/b.a`, chiều cao `e/a/b.b`. Hook toàn cục Graphics/Font → `VF` (PLAN.md).

## Phát hiện
- Mã điều khiển text: `@0..@9` đổi màu (bảng `e/a/b.d` = {đỏ, xanh lá}), `#` về màu gốc, `\n`.
- Hàm wrap theo ký tự: `e/a/b.a(String,Font,int)`, `a(Graphics,String,int,int,int,int)`, `a(String,int)`.
- Nạp text: `b/a.java` (c.dat, map/N.m, i.dat, item15.dat, monster.dat), `e/a/b.b()` (help/tips/t.dat: short n + n×readUTF), script map parse ở `main/c.java:828`.

## Cách chạy
- `source work/tools/env.sh` từ gốc repo; skill scripts ở thư mục skill j2me-viet-hoa/scripts.
- `work/` không có trong git: chạy lại `setup_tools.sh Tool_Viet_Hoa_NAM_P.zip work` rồi sửa env.sh nếu mất.
