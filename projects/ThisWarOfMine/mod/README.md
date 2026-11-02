# TWoM 1.6.9 (Android, arm64) — bản mod chơi đơn

Dùng cho mục đích cá nhân, không thương mại.

## Nội dung
| Tệp | Đặt vào | Tác dụng |
|---|---|---|
| `smali_patch/com/android/Game11Bits/GameHelper.smali` | base.apk → `smali/com/android/Game11Bits/` | Bỏ tự đăng nhập Google Play Games |
| `smali_patch/com/android/Game11Bits/GameActivity.smali` | base.apk → `smali/com/android/Game11Bits/` | Gọi `ModMenu.attach(this)` sau `setContentView` |
| `build/smali/com/twommod/*.smali` (4 tệp) | base.apk → `smali/com/twommod/` | Nút nổi "MOD" + bảng tuỳ chọn |
| `build/libtwommod.so` | split_config.arm64_v8a.apk → `lib/arm64-v8a/` | Hook trong game (lưu cài đặt ở SharedPreferences "twommod") |
| `lua/patch_lua.py` | common.dat (khối `a0d11dd5`) | Phần Lua của "công việc xong tức thì"; `MOD_LUA=1 sh build.sh ...` |

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

- **Công việc xong tức thì** (người trong nhóm):
  - *Chế tạo / xây / nâng cấp*: hook `KosovoCraftingBaseComponent::OnTickCrafting` (ô vtable +0x130 của 4 lớp chế tạo; chỉ thay khi ô đang trỏ đúng hàm gốc). Khi trạng thái = 3 (đang có người làm), dt được nâng lên bằng phần thời gian còn lại của món hiện tại → món hoàn thành qua đúng đường gốc (sự kiện, nhận đồ, hàng đợi món kế tiếp ở khung hình sau). Pha thiết bị tự chạy (trạng thái 4: lọc nước, chưng cất…) **không** đổi.
  - *Lục đồ, dọn đống đổ nát, chặt đồ gỗ, dỡ chướng ngại, mở khoá/cạy khoá*: tất cả đi qua `NonInstantAction:OnUpdateTime` (Lua, trong common.dat). Bản vá thêm một nhánh: nếu công tắc bật, người làm có tag `Dweller`, và lớp hành động thuộc danh sách trên → Timer nhảy tới Duration, rồi `Complete()` gốc chạy như bình thường (vẫn chờ animation kết thúc nếu lớp đó yêu cầu). Ngủ, ăn, nói chuyện, chơi với trẻ, hành động của NPC/kẻ địch: **không** đổi. Script hỏi trạng thái công tắc qua `gConfigHelper:PS4IsPlusSubscriptionActive()` (hàm chỉ dành cho PS4, trên Android luôn `return false`, chỉ được gọi từ wrapper tolua); libtwommod.so hook GOT của hàm này. **Native không bao giờ gọi vào Lua.**
  - Không có common.dat đã vá thì phần Lua không có tác dụng (chế tạo vẫn tức thì).
- **Tốc độ ngày/đêm** (thanh kéo x0.25 … x1 … x4; kéo trái = chậm, kéo phải = nhanh):
  - *Ban ngày*: đổi `MainParams+0x44` (độ dài 1 ngày, giây thật; MainParams không nằm trong save). Mọi thứ tính theo giờ trong game đọc giá trị này (`GetDayTimeRatio`, `GetHourDurationInSeconds`, lò sưởi, đói/mệt, chế tạo), nên cả ngày giãn/co **đồng bộ**: số việc làm được và mức đói mỗi ngày giữ như gốc; nhân vật đi lại, AI chạy theo thời gian thật nên không bị đổi.
  - *Ban đêm (lục lọi)*: độ dài đêm (`GlobalState+0xe4` "ScavengeTime") chia cho tốc độ khi `InitScavengeScene` đặt nó, và cập nhật ngay nếu kéo thanh giữa đêm. Đêm "không giới hạn" (100000) giữ nguyên. AI kẻ địch/NPC không đổi, chỉ hạn giờ về nhà đổi.
  - Về x1: trả đúng giá trị gốc.

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
| Độ dài ngày = MainParams+0x44; progress += gameplayDt / [+0x44] | `FlowStateDay::OnTick`+0x78c, `GetDayTimeRatio` | HIGH |
| Độ dài đêm = GlobalState+0xe4 "ScavengeTime", đặt từ LocationStateInfo+0xc8 | `RegisterProperties`, `InitScavengeScene`, `AskForScavenge::OnNewScene` | HIGH |
| Chế tạo: +0x44 state (3), +0x50/+0x54; OnTickCrafting = vtable+0x130, gọi từ event 0xa1 (TickCrafting, dt = −1) | disasm `OnTickCrafting`, `OnEvent`, relocation vtable | HIGH |
| Hành động có tiến độ = `NonInstantAction:OnUpdateTime` (Lua 5.1, mã nguồn chữ trong common.dat khối a0d11dd5) | script gốc | HIGH |
| Mỗi khung hình: `KosovoScene::OnTick` qua PLT từ `GameFlowController::Tick` | xref | HIGH |

## Đã kiểm
- Smali đã sửa assemble được: dựng lại base.apk bằng apktool 2.10.0, baksmali lại thì thấy đúng hai chỗ sửa và 4 lớp `com.twommod`.
- `libtwommod.so`:
  - build với API 21 (= minSdk), không cảnh báo;
  - LOAD segment căn 16 KB;
  - chỉ export `JNI_OnLoad`, `Java_com_twommod_ModMenu_nSet` và `Java_com_twommod_ModMenu_nAvail`;
  - chỉ import hàm libc/liblog/libdl có từ API 21.
- Mọi tên symbol được hook đều có trong bảng PLT/GOT của game.
- Lua: `luac -p` (Lua 5.1.5) cho bản gốc và bản vá đều OK; chạy thử `NonInstantAction` đã vá trong Lua 5.1.5 với môi trường giả lập: cờ tắt → giống gốc; người nhóm lục đồ → xong ngay; kẻ địch → giữ nguyên; CheerAction → giữ nguyên.
- common.dat mod = bản Việt hoá pixel + đúng 1 khối Lua khác (329 khối, so từng byte).
- Split arm64 thử ráp: .so lưu không nén, offset căn 4 KB (`zipalign -c -p` OK).

## ⚠️ Chưa kiểm được
- **Chưa chạy trên máy thật.** Container không có thiết bị hay emulator Android, nên mọi hook mới chỉ được kiểm tĩnh. Lúc thử, xem log bằng `adb logcat -s twommod`.
- `SlotCount = −1` và StackSize 99 có thể bị ghi vào file save nếu lưu game khi đang bật. Muốn chắc chắn thì tắt tuỳ chọn trước khi về nhà hoặc lưu, rồi bật lại sau.
- Hệ số nhặt chỉ nhận đường qua bảng đồ. Đường nhặt khác (nếu có) sẽ giữ x1.
- Tốc độ đêm: nếu game lưu giữa đêm lục lọi (thường không), giá trị đã đổi tốc độ sẽ nằm trong save. Về x1 trước khi thoát giữa đêm cho chắc.
- Chế tạo tức thì: đồ đang làm dở khi bật sẽ xong ở khung hình kế tiếp nhân vật đứng làm.

## Lịch sử sửa lỗi
- **Treo/chết luồng game khi đa nhiệm** (bản trước): bản cũ đặt cờ Lua bằng `LuaWrapper::Execute` từ trong `KosovoScene::OnTick`, định kỳ ~600 khung hình, kể cả khi mọi tính năng đều tắt — đây là thao tác duy nhất của mod luôn chạy và đụng vào Lua state. Khi game tạm dừng/nạp lại sau đa nhiệm, chen lệnh vào Lua state có thể làm hỏng nó. Đã bỏ hoàn toàn; Lua tự hỏi native qua hàm PS4IsPlusSubscriptionActive đã hook. (Nguyên nhân là suy luận từ mã — chưa có log máy thật; nếu còn treo, gửi `adb logcat` quanh lúc treo.)
