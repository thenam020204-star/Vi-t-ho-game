# BÁO CÁO — Việt hoá "Tinh Vân Truyền Thuyết" (NAMP)

## Kết quả
| Bản | File | jar_check | FreeJ2ME (menu → thoại → chiến đấu) |
|---|---|---|---|
| 240x320 | `out/TinhVanTruyenThuyet_240x320_vi.jar/.jad` | PASS | không exception |
| 320x240 (E62) | `out/TinhVanTruyenThuyet_320x240_vi.jar/.jad` | PASS | không exception |
| 360x640 (màn 640x360, cảm ứng) | `out/TinhVanTruyenThuyet_360x640_vi.jar/.jad` | PASS | không exception |

Class version giữ 47 (như gốc), đã preverify (StackMap, không StackMapTable), không API ngoài CLDC/MIDP (WARN chỉ là API MIDP chuẩn mới dùng: `Graphics.getColor`, `Canvas.repaint/serviceRepaints`, `Vector.size/copyInto`).

## Đã làm
- **Dịch tay** 2034 chuỗi khác nhau (5685 vị trí ở 3 bản): cốt truyện, thoại, nhiệm vụ, vật phẩm, kỹ năng, địa danh, UI, SMS, giới thiệu. Văn phong hiện đại; `vai.md`, `thuat-ngu.tsv` trong `dich/dp_work/`.
- **Font**: renderer hzk12 gốc (drawRGB + gradient từng hàng) thay bằng `src/VH` vẽ glyph f12 (240/320) / f15c (360) vẫn giữ gradient + mã màu `$n`; đo rộng theo advance thật; ngắt dòng theo px ở dấu cách; chạy chữ tính theo đơn vị 3 ký tự (bộ đếm `byte` của game không tràn).
- **Ảnh chữ**: hook vẽ module atlas (`ui/2,3,6,42,43`, `batternum`) -> chữ Việt viền đen; vẽ lại ảnh: tiêu đề, mục menu, bảng tên 5 thành phố, "Phát", "Trượt", trạng thái "Nộ", "Chạm màn hình để tiếp tục", nền màn thoát.
- **Khởi động**: logo NAMP 2 giây, bỏ màn upload GiaiTri321 + hỏi âm thanh (nhạc bật) + hoạt cảnh logo nhà phát hành; ẩn mục "Thêm game"; manifest bỏ chữ Hán/quảng cáo wap (`MIDlet-Delete-Confirm`).
- **Bố cục**: tên quái lên hàng trên (240/320), tên người nói căn trái + viền, lời dẫn bản 320 ngắt 300px, tên hành tinh rút gọn cho khung bản đồ.

## Kiểm tra
- `repack.py --check` (3 bản): OK — charset, `$n`, `|`, dấu `：` thoại, tên khoá đồng nhất (8 tên dùng `equals` chọn chân dung).
- `scripts/qa.py`: OK (2034/2034).
- `dp check`: 710 dòng báo "ký tự rộng" = dấu `：` **cố ý giữ** (code tách tên người nói bằng `indexOf('：')`); 6 cảnh báo thuật ngữ là viết tắt có chủ ý (vd "Điểm KN", "cưỡi thú"). Không có lỗi khác.
- `patch_<bản>.log`: mọi patch khớp đúng số lần mong đợi (22/23/21 mục OK).
- `tools/check_all.sh`: tất cả OK (`logs/check_all.txt`).
- `rebuild.sh`: dựng lại từ jar gốc -> nội dung từng file trong jar trùng bản phát hành (so CRC; chỉ khác timestamp trong zip). Thử giải nén gói `_du_an.zip` vào thư mục mới và chạy `rebuild.sh`: OK.

## Debug (lỗi đã gặp)
1. **Tên field là chữ Hán (bản 360)**: thay Utf8 theo nội dung làm đổi tên field `GameData.*` -> `jar_check` REJECT. Sửa: chỉ đổi `CONSTANT_String`, tách Utf8 mới (`hanlib.rewrite_class`).
2. **Bộ đếm chạy chữ kiểu byte**: câu Việt dài làm tổng ký tự > 127 -> âm -> thoại không hiện. Sửa: đếm theo đơn vị 3 ký tự.
3. **Thoại cần dấu `：`**: dịch đổi sang `:` sẽ làm `substring(0,-1)` văng exception. QA tự khôi phục `：`.
4. **Chữ "未命中" và "狂暴"** vẽ bằng `drawRegion` trực tiếp (không qua hàm module) -> sửa ảnh thay vì hook.
5. FreeJ2ME: phím số là NumPad (phải bật Num_Lock, dùng KP_0..9); lỗi `Sequencer ... is null` là môi trường headless không có MIDI; `ZipException` khi build đè jar lúc emulator đang chạy (không phải lỗi game).

## Ký tự font
Không thiếu ký tự: mọi bản dịch nằm trong charset f12 (f15c là tập lớn hơn). Dấu câu Hán còn sót được VH đổi sang ASCII khi vẽ.

## Việc còn tồn
- Chưa đi hết các màn sâu (cửa hàng, rèn, túi đồ, nhiệm vụ...) trong emulator vì phần hướng dẫn mở đầu dài; chữ ở các màn này đã qua renderer/hook chung nhưng một số chuỗi dài (tên vật phẩm, nhãn cột) có thể tràn ô — cần soát trên máy thật.
- Bản 360 dùng f15c (gần cỡ 16px gốc) thay vì f12 — đổi được ở `build.sh` nếu muốn.
