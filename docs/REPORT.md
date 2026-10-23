# Báo cáo Việt hoá Magic Survival 0.9941

## Sản phẩm (`out/`, không đưa lên git)
| File | Font | Cách cài |
|---|---|---|
| `MagicSurvival_VI_f18_merged.apk` | f18-B: Latin + chữ Việt = pixel f18 (1 ô = 75 đơn vị), chữ Hàn giữ Jeju | Cài trực tiếp |
| `MagicSurvival_VI_f18.apks` | như trên | SAI / Split APKs Installer |
| `MagicSurvival_VI_jeju_merged.apk` | Jeju-VI: thân chữ Jeju + dấu vẽ tay theo nét f18 | Cài trực tiếp (dự phòng) |
| `MagicSurvival_VI_jeju.apks` | như trên | SAI |

Chọn ngôn ngữ **English** trong game → hiện tiếng Việt.

## Đã thay đổi
- `data.unity3d`: đúng 25 object (kiểm bằng `tools/bundle_diff.py`):
  - Font path 33 JejuHallasan: `m_FontData` thay bằng font Việt (f18-B hoặc Jeju-VI).
  - 6 TextAsset `eng_Dictionary_*`: 1904 đoạn dịch tay (`text/vi/vi.tsv`), giữ BOM/CRLF, thẻ màu, ký hiệu.
  - 18 UI.Text trong scene: nhãn Latin (`text/vi/scene.tsv`).
- APK: chỉ `data.unity3d` và chữ ký khác bản gốc (`tools/apk_diff.py`). Không sửa `libil2cpp.so`, `global-metadata.dat`.

## Kiểm tra đã qua
- Font: OTS sanitize, FreeType render 134 chữ Việt ở 17/18/24/30px; glyph cũ không đổi (trừ `đ` ở Jeju-VI, D3a).
- Text: `text_check.py --strict` 0 lỗi; round-trip inject EN = byte-giống gốc.
- Bundle: cờ nén 0x243/0x2 như gốc, UnityPy mở lại đọc đúng font và text.
- APK: `apksigner verify` v2+v3, `zipalign -c -p 4`, `aapt2 dump badging` (com.vkslrzm.Zombie 612 / 0.9941).

## Chưa kiểm / giới hạn
- **Chưa chạy trên thiết bị thật** (không có Android arm64 trong môi trường build).
- Ký lại bằng khoá thử nghiệm `work/keys/ms-vi.jks` → phải **gỡ bản gốc** trước khi cài (mất save cục bộ; sao lưu Google Cloud trước). Google Play/IAP/GPGS có thể không hoạt động; game có thể tự kiểm chữ ký.
- Giữ tiếng Anh theo yêu cầu: chuỗi trong code (Loading…, thông báo cập nhật, Please update) và nhãn `(Max)`.
- Chuỗi Hàn tĩnh trong scene (nếu có hiện ở English) giữ như game gốc.
- 264 đoạn dịch rộng hơn EN >15% (`text/check_report.txt`); mọi UI.Text cắt dọc, nên nút một dòng có thể bị cắt chữ — cần xem trên máy.
- Font f18-B: Unity phóng chữ theo tỉ lệ màn hình nên pixel có thể nhòe/không đều; không ổn thì dùng bản `jeju`.

## Tái lập
`tools/README.md`: `setup_tools.sh` → `unpack.py` → `build_font.py` / `build_font_f18px.py --px 75` → `text_inject.py` → `build_apk.sh`.

## Bản mod (GĐ2): `*_f18_mod*`
Dựa trên f18 (cùng `data.unity3d`), chơi offline một mình, thêm menu mod. Ký cùng khoá `work/keys/ms-vi.jks` (gỡ bản gốc/bản không mod khác chữ ký trước khi cài; cùng khoá với bản f18 thì cài đè được).

| File | Cách cài |
|---|---|
| `out/MagicSurvival_VI_f18_mod_merged.apk` (169355878 B) | Cài trực tiếp |
| `out/MagicSurvival_VI_f18_mod.apks` (179301360 B) | SAI / Split APKs Installer |

**Menu MOD**: trong game có nút nổi **MOD** (kéo được). Bấm → bảng "Menu MOD": công tắc **Bất tử**, nút **Lên 1 cấp**, **Lên 5 cấp**, **Đóng**; dòng trạng thái ("Chỉ dùng được khi đang trong trận" thì nút mờ). Lên cấp = cấp trong trận: nhận đủ EXP, game tự hiện bảng chọn kỹ năng như thường.

**Thay đổi** (`mods/patches.json`, 22 vá byte `libil2cpp.so`, kiểm byte gốc trước khi vá):
- Quảng cáo (A1-A6): bỏ GDPR/MobileAds init; hiện quảng cáo có thưởng/xen kẽ bị bỏ, hồi sinh và thưởng trao ngay (không cần xem quảng cáo, không cần mạng).
- Google Play Games (B1-B3): bỏ đăng nhập GPGS, nút cloud save/load không làm gì.
- IAP (C1): không khởi tạo Unity Purchasing, gói nạp trong shop vô hiệu (không mở khoá gói trả phí).
- Mod: smali `UnityPlayerActivity.onCreate` gọi `com.msmod.Mod.onCreate` (classes2.dex build lại, thêm `classes3.dex`); `lib/arm64-v8a/libmsmod.so` (Dobby hook nhận sát thương + cấp/EXP).
- APK khác bản f18 đúng M7: `libil2cpp.so`, `classes2.dex`, `classes3.dex`, `libmsmod.so`, chữ ký. Build: `tools/build_apk_mod.py` (xem `tools/README.md`).

**Đã kiểm**: `apksigner verify` v2+v3 (base, split, gộp), `zipalign -c -p 4`, `aapt2 dump badging`, dexdump (classes3 có `com.msmod.Mod`, classes2 có lời gọi trong onCreate, tập lớp classes2 không đổi), sha256 `libil2cpp.so`/`libmsmod.so` trong APK gộp = bản vá. Native lib nén (extractNativeLibs=true) nên không cần căn 16KB.

**Chưa test runtime** (không có /dev/kvm, không có emulator/thiết bị arm64).

**Rủi ro**: SELinux/chặn ghi bộ nhớ thực thi có thể làm Dobby hook hỏng (menu hiện "Đang cài hook..." mãi, hoặc văng); hook sai offset có thể văng trong trận. Hồi sinh không cần quảng cáo (gồm cả khi chết). Cloud save và IAP bị vô hiệu hẳn. Game có thể tự kiểm chữ ký.

**Quay về bản không mod**: gỡ app, cài `MagicSurvival_VI_f18_merged.apk` (hoặc `.apks`) / bản gốc.
