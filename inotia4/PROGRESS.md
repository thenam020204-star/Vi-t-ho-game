# PROGRESS — Inotia 4 Android Việt hoá + offline

Đọc file này đầu mỗi session. Kế hoạch đầy đủ: `PLAN.md`.

## Đã xong
- Hạ tầng: `scripts/rebuild.sh <thư mục splits>` → `out/Inotia4_vi_offline.apk` (gộp split bằng APKEditor, apktool, vá smali, text, classes3.dex VNFont, fix_zip, ký v1/v2/v3 bằng `keystore/namp-inotia4.jks`). `check_apk.py` PASS.
- Text: `scripts/memtext.py` (unwrap/wrap LZMA `01 00`+alone, parse/build bảng u24). Selftest giống từng byte cho 5 file ngôn ngữ. `dich/text_can_dich.tsv` gồm 10.602 mục (id, en, ko, vi).
- Font: `font/f18vn.bin` = f18 NAM_P + 6 glyph vẽ tay (`_ = ~ ’ ‘ ★`), sinh bằng `fontgen_inotia/extra18.py`. `java/src/com/namp/vh/VNFont.java` thay thân 5 method của CFont/CSGLFont. Hệ số phóng k=max(1,(size+2)/18): cỡ 14/20/24/28 → ×1, cỡ 38 → ×2.
- Ngôn ngữ: `CFunction.getLanguage` luôn trả `"en"`; bản Việt được chép vào mọi slot `memorytext*.dat.jpg`.

## Đang làm / còn lại
- [ ] Offline: đã phân tích luồng (xem `docs/offline_phan_tich.md`). Gem/tính năng trả phí đi qua **socket tới server game** (`Network_Connect(6)`), nên phải chọn giữa giả lập server và vá từng tính năng. Chưa vá gì.
- [ ] Dịch 10.602 mục bằng `dp` (giữ tên class/kỹ năng/vật phẩm/tên riêng tiếng Anh).
- [ ] Ảnh có chữ: `UI_BUT_ENG_*`, `UI_TITLEMAIN`, `ETC_ban_*`...
- [ ] Test trên máy thật: container không có /dev/kvm nên không chạy được Android emulator.

## Ghi chú kỹ thuật
- Native libgame.so tạo 5 font: size 20/24/38/28/14, cao dòng 22/26/40/30/16 (`GRPX_CreateFont` @0x12a6ac).
- File gốc không có trong git: đặt `base.apk` + `split_config.*.apk` vào một thư mục rồi chạy rebuild (sha256 ở `goc/SHA256.txt`).
