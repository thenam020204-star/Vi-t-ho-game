# TWoM 1.6.9 (Android, arm64) — bản mod chơi đơn

Dùng cho mục đích cá nhân, không thương mại.

## Nội dung
| Tệp | Đặt vào | Tác dụng |
|---|---|---|
| `smali_patch/com/android/Game11Bits/GameHelper.smali` | base.apk → `smali/com/android/Game11Bits/` | Bỏ tự đăng nhập Google Play Games |
| `smali_patch/com/android/Game11Bits/GameActivity.smali` | base.apk → `smali/com/android/Game11Bits/` | Gọi `ModMenu.attach(this)` sau `setContentView` |
| `build/smali/com/twommod/*.smali` (4 tệp) | base.apk → `smali/com/twommod/` | Nút nổi "MOD" + bảng tuỳ chọn |
| `build/libtwommod.so` | split_config.arm64_v8a.apk → `lib/arm64-v8a/` | Hook trong game (lưu cài đặt ở SharedPreferences "twommod") |

Mã nguồn: `native/twommod.c`, `java/com/twommod/ModMenu.java`. Build lại: `build_mod.sh`.

## Cách ráp
1. `apktool d base.apk -o base` → chép 2 tệp smali_patch và thư mục `com/twommod` vào `base/smali/...` → `apktool b base -o base_mod.apk`.
2. Split arm64. Manifest có `extractNativeLibs="false"`, nên .so phải được **lưu không nén** và căn trang:
   ```
   zip -d split_config.arm64_v8a.apk 'META-INF/*'
   mkdir -p lib/arm64-v8a && cp libtwommod.so lib/arm64-v8a/
   zip -0 -X split_config.arm64_v8a.apk lib/arm64-v8a/libtwommod.so
   zipalign -f -P 16 4 split_config.arm64_v8a.apk arm64_mod.apk   # build-tools ≥ 35; bản cũ dùng -p (chỉ căn 4 KB)
   ```
3. Ký **tất cả** split bằng **cùng một khoá**: base, arm64, vi, xxxhdpi, packedData, packedData.config.s3tc. Dùng `apksigner sign --ks ...`. Sau đó cài chung bằng `adb install-multiple` hoặc SAI.

## Tính năng
- **Bỏ đăng nhập Google Play Games.** `GameHelper.mConnectOnStart` gốc là `true`; bản vá đổi thành `false` (thanh ghi `v0` đang là 0).
  - Lúc này `onStart()` không gọi `connect()`. Nó gọi `notifyListener(false)`, nên game nhận `onGoogleServicesSignedIn(false)` như khi người dùng bỏ qua đăng nhập.
  - Kiểm tra giấy phép (LVL, LoaderActivity) không bị động tới.
- **Bất tử khi lục lọi.** Hook `KosovoHPComponent::ReduceHP`, áp dụng khi `KosovoGameDelegate::IsScavenge()` đúng và mục tiêu là người của nhóm (`KosovoScene::GetDwellerIndex ≥ 0`).
  - Sát thương được kẹp để máu còn tối thiểu 2% máu tối đa, và cờ "chết ngay" (HPReducer+0x24) bị xoá.
  - Logic chiến đấu, hiệu ứng và vết thương vẫn giữ nguyên. Kẻ địch không bị ảnh hưởng.
- **Ba lô không giới hạn.** Mỗi khung hình, `SlotCount` (+0x70) của ba lô người trong nhóm được đặt thành −1. Theo `CanTake` gốc, giá trị < 0 nghĩa là không giới hạn.
  - Khi tắt tuỳ chọn, giá trị gốc được trả lại, kể cả phần cộng thêm qua `AddSlotCount` trong lúc bật.
- **Mỗi ô chứa 99.** `StackSize` (+0x34) của mọi `KosovoItemElementConfig` dưới 99 được nâng lên 99. Tắt tuỳ chọn thì giá trị gốc được trả lại.
  - Tuỳ chọn chỉ bật được khi bố cục bảng vật phẩm đã được kiểm (mọi StackSize nằm trong 1..10000).
  - Thay đổi này áp dụng cho mọi kho, kể cả kho ở nhà.
- **Hệ số nhặt đồ x1–x6** (thanh kéo). Áp dụng khi chuyển đồ từ thùng hoặc đống đồ vào ba lô người trong nhóm qua bảng đồ của chế độ lục lọi: lấy hết, kéo thả, chạm từng món.
  - Phần tăng thêm bằng (hệ số − 1) × số đồ vừa nhặt, cộng qua `KosovoInventoryContainer::Add`. Nếu ba lô có giới hạn, phần tăng bị kẹp theo `CanTake`.
  - Không áp dụng cho trao đổi với thương nhân, chế tạo, hay việc chuyển đồ ở nhà.

Mọi hook đi qua GOT (PLT) của `libAndroidGame.so`, không sửa mã máy của game. Nếu không tìm được symbol hoặc layout, tính năng tương ứng tự tắt và hiện mờ trong menu.

## Bằng chứng (offset trong libAndroidGame.so 1.6.9 arm64)
| Mục | Nguồn | Độ tin cậy |
|---|---|---|
| HP +0x44, HP tối đa = config(+0x30)+0x20, entity = host(+0x38) − 0x2d8 | disasm `ReduceHP` @0x9cc118 | HIGH |
| HPReducer +0x00 sát thương, +0x24 chết ngay | nhánh 0x9cc2a8 | HIGH |
| Dweller: scene+0x108 số lượng, +0x110 mảng handle | `GetDwellerIndex` @0xb27094 | HIGH |
| Ba lô = entity+0x20d0 | `CheckWeaponInBackpack`, `GetInventorySlots` | HIGH |
| SlotCount +0x70 (< 0 = vô hạn) | `RegisterProperties` "SlotCount", `CanTake` trả 0x7fffffff | HIGH |
| StackSize = config+0x34, mục 0x2f0 byte, gKosovoItemConfig +0x18/+0x20 | `RegisterProperties` "StackSize", `GetEntryWithName` | HIGH |
| Đường nhặt đồ qua UI: `OnGrabAll` → `Steal`; `GiveSelectedTo*` → `StealSpecific*` | xref BL/B | HIGH |
| Mỗi khung hình: `KosovoScene::OnTick` qua PLT từ `GameFlowController::Tick` | xref | HIGH |

## Đã kiểm
- Smali đã sửa assemble được: dựng lại base.apk bằng apktool 2.10.0, baksmali lại thì thấy đúng hai chỗ sửa và 4 lớp `com.twommod`.
- `libtwommod.so`:
  - build với API 21 (= minSdk), không cảnh báo;
  - LOAD segment căn 16 KB;
  - chỉ export `JNI_OnLoad`, `Java_com_twommod_ModMenu_nSet` và `Java_com_twommod_ModMenu_nAvail`;
  - chỉ import hàm libc/liblog/libdl có từ API 21.
- Mọi tên symbol được hook đều có trong bảng PLT/GOT của game.
- Split arm64 thử ráp: .so lưu không nén, offset căn 4 KB (`zipalign -c -p` OK).

## ⚠️ Chưa kiểm được
- **Chưa chạy trên máy thật.** Container không có thiết bị hay emulator Android, nên mọi hook mới chỉ được kiểm tĩnh. Lúc thử, xem log bằng `adb logcat -s twommod`.
- `SlotCount = −1` và StackSize 99 có thể bị ghi vào file save nếu lưu game khi đang bật. Muốn chắc chắn thì tắt tuỳ chọn trước khi về nhà hoặc lưu, rồi bật lại sau.
- Hệ số nhặt chỉ nhận đường qua bảng đồ. Đường nhặt khác (nếu có) sẽ giữ x1.
