# Ảnh menu có chữ — để vẽ lại tiếng Việt

## Thư mục
- `can_ve/pN/` — **chỉ các ảnh có chữ** của phần N (đây là chỗ cần vẽ). Mỗi phần có `_xem_nhanh.png` để xem cả bộ.
- `DANH_SACH_CO_CHU.tsv` — 149 ảnh: phần, file, kích thước, chữ gốc, gợi ý tiếng Việt, ghi chú, `nhom_trung`, `dung_chung_voi`.
  - **87 ảnh khác nhau** thực sự cần vẽ. Các ảnh cùng `nhom_trung` giống hệt từng byte giữa các phần: vẽ một lần rồi chép cho các phần ở cột `dung_chung_voi`.
- `pN/` — **toàn bộ** ảnh menu của phần N (có cả ảnh không chữ), giữ nguyên cấu trúc gốc:
  - `goc_jar/` — PNG nằm thẳng ở gốc jar.
  - `b.pak/ c.pak/ ic.pak/ mm.pak/` — ảnh đã giải nén từ file `.pak` (kèm `_manifest.tsv`; **không sửa, không xoá** file này).

## Quy tắc khi vẽ (để đóng gói lại vào game được)
1. **Giữ nguyên tên file và kích thước (W×H).** Code game cắt ảnh theo toạ độ cố định. Ví dụ: `tt.png` (menu chính) và `mmtext.png` (menu trong game) là dải nhiều ô bằng nhau, mỗi mục một ô, nên phải giữ đúng vị trí từng ô.
2. Lưu **PNG** (palette hoặc RGBA đều được). Phần trong suốt phải giữ trong suốt như bản gốc.
3. Chữ Việt có dấu cần chỗ phía trên. Ô nhỏ (13×13, 36×17…) thì dùng chữ viết tắt (ATK, DEF, SPD, HP, MP) hoặc một chữ ngắn.
4. Ảnh logo/tiêu đề (`logo0`, `logo2`, `title.png`, `icon.png`, `cr.png`) là tuỳ chọn: không vẽ thì giữ bản gốc.
5. Vẽ xong, đặt ảnh vào **đúng chỗ cũ** trong `can_ve/pN/...` (cùng đường dẫn) rồi gửi lại. Quản đốc sẽ chép vào `pN/`, đóng gói lại `.pak` (`scripts/pakimg.py pack`) và build jar.

## Công cụ
- Giải nén `.pak`: `python3 scripts/pakimg.py unpack x/N/c.pak anh_menu/pN/c.pak`
- Đóng gói lại: `python3 scripts/pakimg.py pack anh_menu/pN/c.pak c.pak` (định dạng: u2 n · n×(UTF tên, u4 cỡ) · dữ liệu XOR 1)
- Đã kiểm 41/41 `.pak` ảnh của 5 phần: giải nén rồi đóng gói lại cho ra file giống hệt từng byte. (`f.pak` của P1 chứa `.fra`, không phải ảnh.)
