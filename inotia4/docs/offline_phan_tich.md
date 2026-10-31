# Phân tích online / offline — Inotia 4 Android 1.4.0

Nguồn: disasm `libgame.so` (llvm-objdump, ARM64), lưu tạm ở `work/libgame.dis`. Dựng lại bằng:
`llvm-objdump -d --no-show-raw-insn lib/arm64-v8a/libgame.so > work/libgame.dis`.
Tra caller bằng: `python3 scripts/re/xref.py work/libgame.dis <hàm>...`.

## 1. Cổng Java mà native gọi (JNI theo tên)
Tên class lấy từ chuỗi rõ trong `.so`. Một số chuỗi khác bị làm rối, chưa giải.
- `com/com2us/peppermint/HubBridge`: Hive/Hub. Các hàm auth, guestAcquireUid, asyncRequest(`guest/create`, `guest/auth`), showDialog, logout... Kết quả trả về qua `HubBridge.hubCallbackWithJSON(json, j, j2)` → native `hubCallback*`.
- `com/com2us/wrapper/*`: font (đã vá), CFunction (đã vá `getLanguage`), CCommonAPI, WrapperUserDefined, WrapperStatistics.
- `com/com2us/module/inappv4/InAppV4`: callback JNI vào native gồm `csMarketListCB`, `csPurchaseCB`, `csRestoreCB`, `csGetBalanceInfoCB`, `csTransactionFinishCB`, `csGetProductInfoCB`...
- Mạng thô: `jGetActiveNetwork` (CS_netGetActiveNetwork), socket `NET_*` (`NET_ConnectSocket/Read/Write`), HTTP `HttpConnectPage` → CHttpManager/CWrapperHttp.

## 2. Luồng khởi động
- `ActiveUserCallback` → `C2S_HubInit`.
- `Inotia_AgeRangeCB` → `C2S_HubAutoLogin`.
- `DelegateCall_startClet` → `HubSave_ProcessInitialize`.
- `hubCallbackAuth` → `HubSave_ProcessLogin`, bước nạp khoá save (`HubSave_*`). **Cần xác minh: save cục bộ có phụ thuộc khoá lấy từ Hive không.**
- `MainMenu_HubLoginOK` → `GotoHubMain` → `C2S_HubMain`.
  - Nếu `CS_netGetActiveNetwork()==1` thì hiện popup text 78 và thoát.
  - Nếu cờ `@0x2bd000+0xef8` (đã đăng nhập) bật thì `CS_hubShowDialog("home")`.
  - Ngược lại thì `guestAcquireUid` / `CS_hubAuth`.
- `PopupHiveLoginChoice` → `C2S_HubGuestLogin`: dùng `guest/create`, hoặc `guest/auth` với `{"guest_uid":"%s"}`. App property `guestuid`, `sessionKey`.

## 3. Tính năng trả phí
Các hàm sau đều gọi `C2S_HubBeginWithFlow`:
`CharacterInfo_ButtonStatResetExe`, `UISkill_ButtonSkillPointResetExe`, `Wipeout_ButtonReviveExe`, `Wipeout_ButtonSpecialReviveExe`, `UIMercenary_ButtonListExe`, `UIMix_ButtonMixingGemExe`, `UIQuestMenu_ButtonClearExe`, `UIPlay_CallInAppShopProc`, `InAppV4MarketListCB`.

Cách `C2S_HubBeginWithFlow` xử lý:
- đã đăng nhập: `Network_Connect(6)`, kết nối **socket tới server game** (process type 6);
- chưa đăng nhập: `CS_hubAuth`; hoặc khi chưa kiểm thiết bị thì `PushMainThreadEvent`.

Hệ quả:
- **Gem (tiền nạp) nằm trên server** (`C2S_HubSetGemValue/GetGemValue`, `UIInApp_DrawCurrentGems`, `NetworkStore_*`, `NW_SendPacket`, `NM_Recv`).
- Để "mua miễn phí offline" có hai cách. Cách thứ nhất là giả lập server: vá socket hoặc `Network_Connect` để trả gói tin. Cách thứ hai là vá trực tiếp từng tính năng trả phí cho chạy hành động thành công mà không qua mạng.
- Đây là việc RE lớn nhất còn lại.

## 4. Kế hoạch vá (đề xuất, chưa làm)
1. **Hub giả lập (Java, smali):** `HubBridge.auth/guestAcquireUid/asyncRequest/...` trả callback JSON thành công với UID cục bộ cố định. Phải đọc `hubCallbackAuth`, `hubCallbackGuestAcquire`, `hubCallbackGuestAuth` để biết khoá JSON native đọc (`error_code`, `uid`/`did`... theo `PeppermintConstant`).
2. **`jGetActiveNetwork`:** cho native luôn thấy "có mạng" để không hiện popup 78. Các kết nối thật sẽ bị chặn ở tầng dưới.
3. **Tính năng trả phí / gem:** đọc `Network_Connect`, `Network_ConnectCB`, `Network_SendData`, `NM_Recv`, `HttpProcessSuccess` cho process type 6 (danh sách hàng, số gem, mua), rồi chọn giữa:
   - (a) server giả trong Java/native;
   - (b) vá từng `*_Exe` gọi thẳng hàm thực thi khi mua thành công (`NetworkStore_OKConfirmBuy` → `Inotia_Buy_Item`...).
4. **IAP Google (InAppV4):** `purchase` gọi thẳng `csPurchaseCB` thành công với `productId`. Native `Inotia_Buy_Item` cộng gem/vật phẩm.
5. **Gỡ quyền:** INTERNET, ACCESS_NETWORK_STATE, BILLING, C2DM; bỏ FCM/Firebase/PlayGames/Analytics service/receiver/provider; Hercules stub nếu cần.
6. `HttpProcessFailed` → `C2SHubSaveDataCheckExistFromServer_v119`: kiểm save trên server khi HTTP lỗi. Với bản offline, lỗi này phải dẫn tới "không có save trên server, dùng save trên máy".
