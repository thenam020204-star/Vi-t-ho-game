# HL5-VI: bản chẩn đoán crash (T13)

Mục đích: v0.1 bị đóng ngay khi mở app. Các APK dưới đây ghi lại nhật ký vào thư mục **Downloads** để gửi lại cho mình xem.
Tất cả cùng tên gói và cùng khoá ký với v0.1 (`dist/diag/`).

## Nguyên nhân đã tìm ra và đã sửa (v0.1.1)
Bản vá bridge của v0.1 đặt `mov w0,#1` sau khi bỏ lệnh cài hook; trong mã gốc w0 khác 0 nghĩa là **lỗi** nên bridge ném ngoại lệ ngay lúc tạo bề mặt GL. Đã sửa thành `mov w0,#0` trong `dist/HL5-VI-v0.1.1.apk`. Các bản diag dưới đây dùng bridge đã sửa; dùng khi v0.1.1 vẫn lỗi.

## Các bản (`dist/diag/`)
| Tệp | Nội dung | Dùng để |
|---|---|---|
| `HL5-diag-v0.1.1.apk` | v0.1.1 đầy đủ + chẩn đoán (trùng `HL5-diag-C.apk`) | **Thử đầu tiên**: giống bản chơi, kèm nhật ký |
| `HL5-diag-A.apk` | APK gốc 1.0.5 + chẩn đoán | Kiểm lớp chẩn đoán vô hại, máy chạy được game gốc |
| `HL5-diag-B.apk` | A + guest đã vá + dữ liệu Việt, bridge gốc | Lỗi ở guest/dữ liệu nếu A chạy mà B không |
| `HL5-diag-C.apk` | v0.1.1 + chẩn đoán | như `v0.1.1` |
| `HL5-diag-C1.apk` | C bỏ NEAREST/viewport/chạm | Chia nhỏ lỗi bridge |

## Cách làm (mỗi bản)
1. **Gỡ bản HL5 đang cài** (gỡ cả bản gốc và v0.1; dữ liệu cũ có thể làm nhiễu kết quả), cài APK diag.
2. Mở app. Nếu bị đóng ngay thì **mở lại lần nữa** (báo cáo ghi lần chạy TRƯỚC, nên cần mở ít nhất 2 lần).
3. Khi có báo cáo, app hiện thông báo "Đã lưu nhật ký chẩn đoán vào Downloads".
4. Vào **Downloads** (ứng dụng Tệp), lấy các file `HL5_diag_<thời gian>.txt` (mới nhất; gửi cả file cũ hơn nếu có).
   Nếu không thấy: `Android/data/co.kr.eamobile.hl5.runner/files/diag/` có bản sao cùng tên, kèm `steps.txt` và `steps_prev.txt`.
5. Gửi các file đó lại. Thứ tự thử: `v0.1.1`, rồi nếu vẫn lỗi thì **A**, **B**, **C1**, gỡ app giữa mỗi lần.

## File chứa gì
Thông tin máy (model, Android, ABI, kích thước trang bộ nhớ, màn hình, RAM), danh sách file thư viện/dữ liệu của app và kích thước, các mốc khởi động của lần chạy trước (`steps_prev`: onCreate, trước/sau nạp bridge, trước/sau kiểm sha256 và chép asset, trước/sau `nativeOnSurfaceCreated`, khung hình đầu...), ngoại lệ Java nếu có, `ApplicationExitInfo` (Android 11+, gồm tombstone của crash native), và `logcat` của chính app.
Mốc cuối cùng có trong `steps_prev` cho biết app chết ở bước nào.
