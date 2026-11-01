# HL5-VI v0.1.1 (bản chơi thử)

**Tệp mới: `dist/HL5-VI-v0.1.1.apk`** (22.668.446 byte), SHA-256: `119e0f2d7bc44ebf857c175fe5fe343f4039b5570bffc23590eee8bf20b84261`

## Thay đổi v0.1.1
- **Sửa crash khi mở app** (v0.1 đóng ngay khi tạo bề mặt GL): hai bản vá bridge ở 0xc6dd8 và 0xc6e60 trả w0=1 thay vì 0 làm bridge ném ngoại lệ "Cannot install verified font metrics hook". Nay trả 0 (thành công). Chỉ đổi 2 lệnh (8 byte) trong `libhl5_arm64_bridge.so`; mọi tệp khác y hệt v0.1.
- Rà lại toàn bộ 9 vùng vá bridge (kết luận từng cái ở `patch/bridge_review.md`).
- Cùng tên gói, cùng versionCode và cùng khoá ký với v0.1: cài đè được lên v0.1; nếu lỗi lạ thì gỡ rồi cài lại.
- Chưa thử trên máy thật. Nếu vẫn crash, dùng `dist/diag/HL5-diag-v0.1.1.apk` (xem `PHAT-HANH-diag.md`) rồi gửi file nhật ký trong Downloads.

# (lịch sử) HL5-VI v0.1 (bản chơi thử đầu tiên)

Tệp: `dist/HL5-VI-v0.1.apk` (22,7 MB, 21.6 MiB). Gói `co.kr.eamobile.hl5` giữ nguyên tên gói và versionCode so với bản gốc 1.0.5.

SHA-256 v0.1 (BỊ CRASH, không dùng): `a9e7b9f91b477e3dbc76034ff8c29d5a8fa7846b33ff732d1e8548a1190f9ed4`

## Cách cài
1. **Gỡ bản gốc Heroes Lore 5 trước.** APK này ký bằng khoá mới (`sign/hl5vi.keystore`), khác chữ ký bản gốc nên Android không cho cài đè. Gỡ sẽ mất dữ liệu lưu của bản gốc.
2. Chép APK vào máy (Android 10+, kiến trúc arm64), bật "cài từ nguồn không rõ", mở tệp để cài.
3. Dựng lại từ nguồn: `WORK=... ./make_apk.sh` (xem đầu tệp để biết điều kiện).

## v0.1 gồm gì
- Văn bản tiếng Việt cho hội thoại, menu, vật phẩm, kỹ năng, nhiệm vụ (14.282/14.284 câu); chữ có dấu qua engine chữ Việt (`vi.bin`, 3 cỡ chữ 10/15/18 px).
- Chạy offline: các bản vá offline đã áp, không cần máy chủ.
- Logic game = màn hình ÷ k (k nguyên: 2400x1080 -> 600x270, 1920x1080 -> 480x270...), khung hình căn giữa, lọc NEAREST (pixel sắc nét).

## Giới hạn đã biết
- Chưa có tuỳ chọn ÷k trong màn hình cài đặt.
- Chưa có chữ 18 px (chế độ HD) hoàn chỉnh.
- Ảnh có chữ vẫn là tiếng Anh; nhãn menu chính dài có thể tràn ô (sửa ở T7).
- Cửa hàng cash chưa đụng tới.
- Bridge armeabi-v7a không vá (chỉ arm64 dùng được); các `lib/armeabi*/libHeroesLore5.so` giữ nguyên bản gốc.
- **Chưa thử trên máy thật**: chỉ kiểm bằng harness Unicorn (tới bản đồ ở 600x270) và kiểm tĩnh bridge.

## Kiểm tra đã làm
`apksigner verify` (v1+v2+v3) và `zipalign -c -p 4` đạt; `aapt2 dump badging` y hệt bản gốc; dựng hai lần cho APK trùng từng byte.
