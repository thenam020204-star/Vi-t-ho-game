# HL5-VI: bản chẩn đoán crash (T13)

Mục đích: v0.1 bị đóng ngay khi mở app. Các APK dưới đây ghi lại nhật ký vào thư mục **Downloads** để gửi lại cho mình xem.
Tất cả cùng tên gói và cùng khoá ký với v0.1 (`dist/diag/`).

## Đã tìm ra nguyên nhân khả dĩ nhất (kiểm tĩnh)
Bản vá bridge của v0.1 đặt `mov w0,#1` ngay sau khi bỏ lệnh cài hook (0xc6dd8, 0xc6e60). Lệnh kế tiếp là `cbnz w0` và trong mã gốc w0 khác 0 nghĩa là **lỗi**, nên bridge ném ngoại lệ C++ "Cannot install verified font metrics hook" ngay lúc tạo bề mặt GL (hàm `nativeOnSurfaceCreated`) và app bị đóng. Bản **D** sửa thành `mov w0,#0`. Hãy thử **D trước**.

## Các bản
| Tệp | Nội dung | Dùng để |
|---|---|---|
| `HL5-diag-D.apk` | v0.1 + sửa `w0=0` + lớp chẩn đoán | **Thử đầu tiên**: nếu vào được game thì đúng nguyên nhân |
| `HL5-diag-A.apk` | APK gốc 1.0.5 + lớp chẩn đoán | Kiểm lớp chẩn đoán vô hại, máy chạy được game gốc |
| `HL5-diag-B.apk` | A + guest đã vá + dữ liệu Việt, bridge gốc | Lỗi nằm ở guest/dữ liệu nếu A chạy mà B không |
| `HL5-diag-C.apk` | v0.1 đầy đủ + chẩn đoán | Tái hiện đúng lỗi của v0.1 (dự kiến crash) |
| `HL5-diag-C1.apk` | C bỏ NEAREST/viewport/chạm | Chia nhỏ lỗi bridge nếu D vẫn crash |

## Cách làm (mỗi bản)
1. **Gỡ bản HL5 đang cài** (gỡ cả bản gốc và v0.1; dữ liệu cũ có thể làm nhiễu kết quả), cài APK diag.
2. Mở app. Nếu bị đóng ngay thì **mở lại lần nữa** (báo cáo ghi lần chạy TRƯỚC, nên cần mở ít nhất 2 lần).
3. Khi có báo cáo, app hiện thông báo "Đã lưu nhật ký chẩn đoán vào Downloads".
4. Vào **Downloads** (ứng dụng Tệp), lấy các file `HL5_diag_<thời gian>.txt` (mới nhất; gửi cả file cũ hơn nếu có).
   Nếu không thấy: `Android/data/co.kr.eamobile.hl5.runner/files/diag/` có bản sao cùng tên, kèm `steps.txt` và `steps_prev.txt`.
5. Gửi các file đó lại. Thứ tự thử: **D**, rồi nếu D vẫn lỗi thì **A**, **B**, **C**, **C1**, gỡ app giữa mỗi lần.

## File chứa gì
Thông tin máy (model, Android, ABI, kích thước trang bộ nhớ, màn hình, RAM), danh sách file thư viện/dữ liệu của app và kích thước, các mốc khởi động của lần chạy trước (`steps_prev`: onCreate, trước/sau nạp bridge, trước/sau kiểm sha256 và chép asset, trước/sau `nativeOnSurfaceCreated`, khung hình đầu...), ngoại lệ Java nếu có, `ApplicationExitInfo` (Android 11+, gồm tombstone của crash native), và `logcat` của chính app.
Mốc cuối cùng có trong `steps_prev` cho biết app chết ở bước nào.
