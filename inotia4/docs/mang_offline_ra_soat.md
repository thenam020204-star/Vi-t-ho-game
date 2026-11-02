# Rà soát lối vào mạng Java (T1) — bản offline không có quyền INTERNET

Phạm vi: `com/com2us/**` và `com/hive/**` mà native (`libgame.so`) hoặc UI gọi được lúc chơi.
Nguồn: smali của TEST4 (`/opt/work/dec4`, bytecode thật), disasm `libgame.so` (`/opt/work/lib4/dis.txt`), log máy thật
`logs/may_that_TEST5a_2026-10-31.txt`, `logs/may_that_TEST6_load_save_2026-10-31.txt`.
Ngày rà: 2026-10-31. Người làm: A1.

## 1. Kết luận

| Chỉ số | Số |
|---|---|
| Lối vào mạng Java mở kết nối thật (HTTP/DNS) | 3 method: `CWrapperHttp$1.run`, `Hercules.HttpRequest`, `Network.sync` (Hive) |
| Điểm gọi WebView (`loadUrl`/`postUrl`/`loadData`) | 22 điểm gọi trong 20 method (13 class Hive + 5 class com2us) |
| Lệnh Java mở kết nối đã kiểm phủ ngoại lệ | 34 lệnh `invoke` (HttpURLConnection/URLConnection/InetAddress) |
| Điểm vào native → Java liên quan mạng | 8 nhóm (bảng C), khoảng 80 method Java |
| Chỗ nguy hiểm (`SecurityException` lọt ra luồng không ai bắt) | **1**: `CWrapperHttp$1.run` (đã vá ở T0, kiểm lại ở T1) |
| Mục `CATCH_ADD['net']` | **1 mục, 2 lần khớp**. T1 không cần thêm mục mới |
| Mục cần quản đốc quyết | 3 (mục 7), đều không phải lỗi `SecurityException` mạng đã chứng minh |

Lý do chỉ còn 1 chỗ: toàn bộ SDK Hive đi qua đúng một hàm mạng là `com.hive.core.Network.sync(Request)`. Hàm này bọc `catch Exception`
cho từng đoạn và dùng `runCatching` ở chỗ đọc thân, nên `SecurityException` thành mã lỗi trong `Response`. Ngoài ra chỉ còn
`CWrapperHttp$1` (luồng HTTP của wrapper, chỉ bắt `IOException`) và `Hercules.HttpRequest` (đã bắt `Exception`).

## 2. Phương pháp

1. Quét smali `com/com2us`, `com/hive` tìm `URL.openConnection/openStream`, `HttpURLConnection`, `URLConnection`, `Socket`,
   `DatagramSocket`, `InetAddress`, `okhttp3`, `WebView.loadUrl/postUrl/loadData`, `isReachable`, `Runtime.exec`, `SSLSocket`. Kết quả: ngoài danh sách ở bảng A, B không có chỗ nào khác
   (không có `okhttp`, `Socket`, `DatagramSocket` trong `com2us`/`hive`; `Runtime.exec` ở `com/hive/base/Android` chỉ đọc cấu hình máy).
2. Với mỗi lệnh mạng, kiểm phủ ngoại lệ bằng `scripts/re/cov_exc.py <dec> com/com2us com/hive`
   (theo bảng `.catch`, và theo handler của `.catchall` tới lệnh `throw`/`return`).
   - TEST4: 34 lệnh mạng, 16 chưa phủ `SecurityException`. Cả 16 thuộc `CWrapperHttp$1.run` (9) và `CWrapperHttp.netHttp*Ex` (7).
   - Sau nhóm `net`: còn 7, đều trong `netHttp*Ex` (mục A2, an toàn vì cờ `iHttpStatus`).
3. Tên method Java mà native gọi: đối chiếu chuỗi trong `libgame.so` với tên method smali, rồi tra hàm native tham chiếu chuỗi (adrp/add) và caller bằng `scripts/re/xref.py`.
4. Luồng: đọc smali tại chỗ khởi tạo (`Thread.start`, `HandlerThread`, `runOnUiThread`, coroutine).

## 3. Bảng A — mã Java mở kết nối HTTP/DNS

| # | Class.method | API mạng | Ai gọi | Luồng | Bắt ngoại lệ | `SecurityException` lọt? | Xử lý |
|---|---|---|---|---|---|---|---|
| A1 | `com/com2us/wrapper/network/CWrapperHttp$1.run()V` | `URL.openConnection`, `getOutputStream`, `getResponseCode`, `getContentLength`, `getInputStream` | Native `CS_netHttpOpenEx/ConnectEx` → `CHttpManager.netHttpConnectEx` → `CWrapperHttp.netHttpConnectEx` → `new CWrapperHttp$1().start()`. Game: `HttpConnectPage` (← `Scene_Process_POPUP_SC_SAVESLOT`, `UIOption_ButtonListExe`, `GameDataDownload_Confirm(_v119)`, `GameDataUpLoad_Confirm`, `hubGameData*Callback`, `hubCallbackAuth/GuestAuth`), `Protection::NetConnectCB` (`threadDownloadHackAppData`) | Luồng mới `Thread-N` cho mỗi yêu cầu (class kế thừa `Thread`), không có `UncaughtExceptionHandler` riêng ngoài của app | `IOException` ×2 vùng try (`try_0`, `try_3`), handler `:catch_0` chỉ `Throwable.printStackTrace` + `iHttpStatus=-1` + `nativeConnectCB(fd,-1)`. Vùng `getOutputStream` (POST) là `catchall` rồi `throw` nằm trong `try_3` | **Có** (đã gây văng TEST5a, log dòng 577/605). Sau vá: không | `CATCH_ADD['net']` ×2 (đã có từ T0). Dex TEST6/TEST7/T1: `IOException` và `SecurityException` cùng trỏ `0x0055` |
| A2 | `com/com2us/wrapper/network/CWrapperHttp.netHttpGetEncodingEx/netHttpGetHeaderFieldEx/netHttpGetLengthEx/netHttpGetTypeEx/netHttpGetResponseCodeEx/netHttpGetResponseMessageEx` | `getContentEncoding`, `getHeaderFields`, `getContentLength`, `getContentType`, `getResponseCode`, `getResponseMessage` | Native qua `CHttpManager.netHttp*Ex(I..)` (luồng GL/game, gọi trực tiếp) | Luồng native gọi JNI | Chỉ `netHttpGetResponseCodeEx/MessageEx` bắt `IOException`; còn lại không bắt | Không. Mọi method đều thoát sớm nếu `iHttpStatus != 2`; `iHttpStatus = 2` chỉ được gán một chỗ duy nhất (`CWrapperHttp$1.run`, sau khi `getResponseCode` và đọc thân thành công). Lúc đó các getter chỉ đọc dữ liệu đã có, không mở kết nối mới | Không cần sửa. Bằng chứng: `iput ... iHttpStatus` ở `CWrapperHttp.smali` dòng 165, 371, 397, 791, 797 chỉ gán 0/1/3/-1; `CWrapperHttp$1.smali:620` gán 2 |
| A3 | `com/com2us/security/Hercules.HttpRequest(String,[B)[B` | `URL.openConnection`, `getOutputStream`, `getInputStream` | JNI từ `libHercules.so` (tên bị làm rối), luồng native của Hercules | Luồng native (gắn vào JVM) | `catch Exception` bao toàn bộ; vùng `catchall` rethrow trong vùng `catch Exception` | Không | Không cần sửa |
| A4 | `com/hive/core/Network.sync(Request)Response` | `URL.openConnection`, `InetAddress.getByName`, `connect`, `getOutputStream`, `getResponseCode`, `getInputStream`... | Duy nhất đường mạng của Hive SDK. Caller: `Protocol` (mọi lệnh Hive; `HandlerThread` `httpRetryThread_*`), `AnalyticsNetwork`, `PushConfig`, `PushActionWorker`, `IapV4LogWorker`, `HiveResourceWorker$work$1` (WorkManager) | Luồng nền của SDK (HandlerThread / WorkManager / executor) | Từng đoạn có `catch IllegalArgumentException/MalformedURLException/SocketTimeoutException/Exception` + `catchall`; đọc thân gói trong `kotlin.Result` (`runCatching`, bắt `Throwable`) | Không. `SecurityException` (`RuntimeException`) rơi vào `catch Exception` → đặt mã lỗi trong `Response` | Không cần sửa. Hậu quả offline: mọi lệnh Hive trả lỗi mạng (đúng như mong muốn) |

Ghi chú A4: `InetAddress.getByName` (dòng 398) nằm trong `try_2` có `catch Exception → :catch_0`. 5 lệnh `getInputStream/getContentEncoding` còn lại do
`cov_exc.py` báo "chỉ catchall" là trong khối `runCatching`: handler `:catchall_3 → :goto_8` tạo `Result.failure`, không rethrow.

## 4. Bảng B — WebView (22 điểm gọi)

`WebView.loadUrl/postUrl/loadData` không ném `SecurityException` ở phía Java: lỗi mạng trả qua `WebViewClient.onReceivedError`.
Bằng chứng gián tiếp: TEST5a, log dòng 353 — `WebView ... app:id/webView` (trong `res/layout/main.xml`) được tạo ngay lúc `MainActivity` khởi động khi chưa có INTERNET và app không văng.
Chưa có log máy thật cho một lần `loadUrl` cụ thể (xem mục 7, việc 3).

| Class (điểm gọi) | Ai gọi / khi nào | Luồng | Ghi chú |
|---|---|---|---|
| `com2us/wrapper/utility/CUtility$2.run` (`loadUrl`), `CUtility$NewWebViewClient.onPageFinished` | `CUtility.makeWebView` ← JNI `jWebView` ← `CS_hlpWebView`. **Không có hàm game nào gọi** (xref: không có caller) | UI | Không tới được |
| `com2us/security/HerculesDialog.onCreate` (`postUrl`), `$1.onReceivedError` (`loadUrl`), `$1$1.run` (`postUrl`) | `HerculesDialog.start(int)` do `libHercules.so` gọi khi phát hiện hack | UI | Có vòng thử lại trong `onReceivedError` (không văng, chỉ lặp). Chỉ khi Hercules báo vi phạm |
| `hive/auth/UserAgreeDialog` (+ `$UserAgreeSchemeHandler.schemeEvent`, `$WebViewCallBack$LocalDataRunnable.run`, `closeAgreementUI`) | `ActiveUser.showUserAgreeTerms` ← `CS_UserAgreeShowTermsUI` ← game `CallUseAgreement` | UI | Nội dung nạp từ dữ liệu cục bộ + mạng; lỗi → trang lỗi |
| `hive/core/webview/HiveWebView` (`loadUrl`, `loadUrl(url,map)`, `postUrl`), `HiveWebViewClient.mainFrameErrorReload` | Mọi màn WebView của Hive (promotion, auth, IAP, social) | UI | `mainFrameErrorReload` tải lại khi lỗi: có thể lặp, không văng |
| `hive/iapv4/IapWebViewDialog` (`show`, `$WebViewCallBack.onPageStarted/receivedErrorEvent`, `$IapSchemeHandler$schemeEvent$1.sendResults`) | Hive IAP v4 (UI thanh toán/xác nhận) | UI | Thuộc luồng IAP, T4 sẽ chặn trước khi tới đây |
| `hive/social/SocialDialog$SocialWebSchemeHandler$schemeEvent$1.sendResults` | Hive Social | UI | Không có đường gọi từ game |
| `hive/ui/common/view/HiveCustomViewBanner$mWebViewClient$1.showErrorPage`, `HiveFullscreenView$mWebViewClient$1.showErrorPage`, `hive/promotion/ui/news/NewsV2WebFragment.showErrorPage` | Hive Promotion (banner / tin) ← `Mercury.mercuryShowEx` | UI | Chỉ nạp trang lỗi cục bộ |
| `hive/userengagement/UserEngagement$...InvisibleWebView$2.invokeSuspend` (`loadData`) | Hive lifecycle lúc khởi động (đo kích thước CSS) | Main (coroutine) | Đã chạy lúc khởi động trên máy thật, không văng |

## 5. Bảng C — điểm vào native → Java liên quan mạng

Cột "Game gọi" lấy bằng `xref.py` trên `libgame.so`. "(không có)" nghĩa là shim `CS_*` tồn tại nhưng không hàm game nào gọi nên không tới được lúc chơi.

| Java (JNI) | Shim native | Game gọi | Phía sau Java | Kết quả offline / ngoại lệ |
|---|---|---|---|---|
| `CHttpManager.netHttp*` (19 method) | `CS_netHttp*Ex` | `HttpConnectPage`, `Protection::NetConnectCB` | Bảng A1/A2 | Đã vá (A1). Native nhận `nativeConnectCB(fd,-1)` = nhánh "không có mạng" của bản gốc |
| `HubBridge.*` (`initializeEx`, `asyncRequest`, `auth`, `authWithParam` đã thay bởi nhóm `offline`; còn lại `showDialog`, `guestAcquireUid`, `socialRequest`, `logout`, `storeCurrentUserId`...) | `CS_hub*` | `C2S_HubMain`, `onHubMoreGames`, `onHubShow` (`CS_hubShowDialog`), `C2S_HubAutoLogin`, `PopupHiveLoginChoice`... | `Peppermint` không bao giờ được tạo → 16/16 method còn lại có dùng `peppermint` đều kiểm `== null` rồi `return -16` (đã đọc từng method); `setLogged` chỉ bật log Hive | Không ném ngoại lệ, không mạng |
| `Mercury.mercuryShowEx` → `PromotionImpl.showPromotion/showCustomContents` | `CS_MercuryShowEx` | `ExeMercury`, `C2S_HubMercuryShowBanner` | Hive Promotion: `Protocol` → `Network.sync`, WebView/Glide | A4 an toàn. Glide: `GlideExecutor$DefaultThreadFactory$1.run` bắt `Throwable` rồi `UncaughtThrowableStrategy.DEFAULT = LOG` (không văng) |
| Các method `Mercury.*` còn lại (`showMoregamesPopup`, `showReviewPopup`, `mercuryGetBadge`, `mercuryNativeGetBannerInfo`, `...UAInfo`, `initImageLoader`, `setBannerOnGameWindow`) | `CS_Mercury*` | (không có) | — | Không tới được |
| `InAppV4.marketConnect/purchase/restore/getProductInfo/transactionFinish` | `CS_IapV4*` | `InApp_SelectTarget`, `NoAd_SelectTarget`, `Inotia_IapV4MarketConnect`, `Inotia_Market_Connect` (connect); `InApp_BuyItem`, `Inotia_Buy_Item` (purchase); `Inotia_Buy_Restore`, `Inotia_ReceiptInfoCB`, `item_restore` (restore); `InAppV4MarketListCB`, `InApp_Lebiinitialize`, `Inotia_IapV4GetProductInfo` (product); `Inotia_Transaction_Finish` | Hive `IAPV4` → Google Play Billing (qua dịch vụ Play, bằng binder, không dùng socket của app) + `Network.sync` + `IapWebViewDialog` | Mạng Hive an toàn (A4). Thiếu quyền `BILLING`: đi qua `BillingClient`, không đo được tĩnh. **Thiết kế lại ở T4** (luồng giả) nên không vá riêng ở T1. `getBalanceInfo/showCharge/showInAppMessages/showMarketSelection/checkPromotePurchase/subscription` không có caller |
| `ActiveUser.showUserAgreeTerms` | `CS_UserAgreeShowTermsUI` | `CallUseAgreement` | `AuthImpl.showTerms` → `Protocol` + WebView | A4 + bảng B |
| `ActiveUser.setServerId`, `setLogged` | `CS_ActiveUserSetServerId`, `CS_ActiveUserSetLogged` | `STATE_ProcessInit`, `startClet` | Chỉ đặt cấu hình/log Hive (`ConfigurationImpl.setServerId`, `Logger.setLogEnable`) | Không mạng |
| `ActiveUser.start(...)`, `jniGetAgeRange` | `CS_ActiveUserGetAgeRange` | `ActiveUserCallback`, `Inotia_AgeRangeCB` | Đã thay bằng `Offline` (nhóm `offline`) | Không mạng |
| `WrapperUserDefined.GotoURL` | `jGotoURL` | `GotoEquipEnchantProbabilityView`, `GotoProbabilityView`, `GotoReview`, `GotoRightToCancelView` | `startActivity(ACTION_VIEW)`, mở trình duyệt/Play Store của máy | Không dùng quyền INTERNET của app. Không bắt `ActivityNotFoundException` (hiếm, ngoài phạm vi) |
| `WrapperUserDefined.GetNoticeHttp` | `jRequestNotice` | (không có) | Thân method: `return null` | Không mạng |
| `CCommonAPI.getActiveNetwork` | `CS_netGetActiveNetwork` | `UIPlay_CallInAppShopProc`, `Wipeout_ButtonReviveExe`, `UIMix_ButtonMixingGemExe`, `C2S_HubMain`, `onHubMoreGames`, `ExeMercury`... | `ConnectivityManager.getActiveNetworkInfo/getAllNetworkInfo` (quyền `ACCESS_NETWORK_STATE` còn) | Không ném. Trả "có mạng" nếu máy có Wi-Fi/dữ liệu dù app bị chặn INTERNET (xem mục 7, việc 1) |
| `HiveLifecycle.registerNetworkCallback` (Hive tự gọi lúc khởi động) | — | Hive lifecycle | `ConnectivityManager.registerNetworkCallback`, bọc `catchall` có log `registerNetworkCallback failed` | Không văng; cần `ACCESS_NETWORK_STATE` (còn) |

Mạng thô ở native (ngoài Java): `NET_ConnectSocket` → `CS_netSocketConnect` → `NM_Connect` + `getaddrinfo`/`socket` của libc. Không ném `SecurityException` Java; libc trả lỗi (`EACCES`) và native xử lý bằng mã lỗi. Nối sau T1 (T4/gem), không kiểm trong T1.

## 6. Ngoài `com2us`/`hive` (chỉ ghi nhận, không sửa)

Các thư viện bên thứ ba có `openConnection` nhưng không thuộc phạm vi: Facebook (`GraphRequest`, `ImageDownloader`, `FileDownloadTask`, `OidcSecurityUtil`, `cloudbridge`),
Firebase (`FirebaseInstallationServiceClient`, `messaging.ImageDownload`), Google datatransport (`CctTransportBackend`), GMS (`tagmanager`, `signin.zzbb`, `ads.identifier`), Glide (`HttpUrlFetcher`).
Bằng chứng chạy thật (log máy thật TEST5a/TEST6): Firebase Installations ném `SecurityException` trong `SequentialExecutor` nhưng chỉ log, Facebook chỉ cảnh báo thiếu INTERNET,
app tiếp tục chạy tới tận màn chơi. Glide: `DecodeJob` rethrow nhưng luồng bọc `Throwable` (xem bảng C).

## 7. Mục cần quản đốc quyết

| # | Vấn đề | Bằng chứng | Đề xuất |
|---|---|---|---|
| 1 | `CCommonAPI.getActiveNetwork` báo "có mạng" khi máy có Wi-Fi, trong khi app bị chặn INTERNET. Các nút trả phí (hồi sinh, gem, shop, "Game khác") qua `CS_netGetActiveNetwork` rồi vào `C2S_HubBeginWithFlow` → `Network_Connect(6)` (socket native) thay vì hiện popup "không có mạng" của bản gốc | Bảng C; `docs/offline_phan_tich.md` mục 2 (đề xuất giữ "có mạng" để chạy luồng mua) | Giữ nguyên cho tới T4 (cần thấy được luồng mua). Chỉ đổi sang "không có mạng" nếu T4 thất bại. Cần quyết khi làm T4 |
| 2 | `CFunction.getDeviceId()` gọi `TelephonyManager.getDeviceId()`: sẽ ném `SecurityException` trên Android 10+ vì app không có `READ_PHONE_STATE`; shim `jGetDeviceId` không kiểm `ExceptionCheck`, nên sẽ văng native. Tương tự `getPhoneNumber`, `getMIN` | Khoá `UDID`/`PHONENUMBER`/`MIN` chỉ có trong `CS_knlGetSystemProperty`; caller của nó (`Network_Connect`, `HttpMakeDataReqSaveDataCommon`) chỉ hỏi `MODEL`/`SYSTEMVERSION`; xref không tìm thấy caller nào hỏi `UDID` | Không sửa lúc này (không có đường gọi). Nếu muốn phòng thủ: thay thân `getDeviceId()` trả `""` (cần quyết vì Quyết định thiết kế cấm thay thân nếu không bắt buộc; method này không có handler để `CATCH_ADD`) |
| 3 | Chưa có bằng chứng máy thật cho `WebView.loadUrl` lúc không có INTERNET, và cho các màn Hive (Mercury, UserAgree) | Chỉ có WebView khởi tạo trong `main.xml` (log TEST5a:353) | Bản chẩn đoán kế tiếp (nếu có) nên nhờ người dùng bấm: `GAME KHÁC`, `HIVE`, chọn ô save, nút Option → cloud save, shop/gem, rồi gửi `Xuất báo cáo`. Không cần vá trước khi có log |

Không đề xuất thay thân method nào trong phạm vi T1.

## 8. Mục `CATCH_ADD['net']`

| Class | Method | Cũ → mới | Khớp | Handler |
|---|---|---|---|---|
| `com/com2us/wrapper/network/CWrapperHttp$1` | `.method public run()V` | `IOException` → thêm `SecurityException` | **2** (vùng `try_start_0..try_end_0` và `try_start_3..try_end_3`) | `:catch_0` chỉ gọi `Throwable.printStackTrace` rồi `nativeConnectCB(fd,-1)`: thoả verifier |

Không thêm mục mới trong T1 (chỉ có đúng một chỗ nguy hiểm).

## 9. Kiểm tra

- `cp -r /opt/work/dec4 /opt/work/dec_t1 && python3 -I scripts/patch_smali.py /opt/work/dec_t1 --group net` → `catch ... CWrapperHttp$1 ... +Ljava/lang/SecurityException; x2`, `tong: 1 method`, không lỗi.
- `diff -r /opt/work/dec4 /opt/work/dec_t1` → chỉ 2 dòng thêm vào `smali/com/com2us/wrapper/network/CWrapperHttp$1.smali`:
  `.catch Ljava/lang/SecurityException; {:try_start_0 .. :try_end_0} :catch_0` (sau dòng 307) và `... {:try_start_3 .. :try_end_3} :catch_0` (sau dòng 658).
- Build `APKTOOL=/opt/re/apktool_2.12.1.jar bash scripts/build_from_apk.sh test_builds/Inotia4_TEST4_vi_anh_offline.apk out/Inotia4_T1_check.apk --smali net --vnfont`:
  `zipalign -c -P 16 4` OK, `apksigner verify` OK, `check_apk.py` PASS (không INTERNET, có ACCESS_NETWORK_STATE, không BILLING).
- Decode lại `out/Inotia4_T1_check.apk` bằng apktool vào `/opt/work/dec_t1_out`, rồi `diff -r` với `/opt/work/dec4`:
  - `smali/`: chỉ 2 dòng `.catch` trên;
  - `smali_classes2/`: không khác;
  - `smali_classes3/`: khác ở `com/namp/vh/VNFont.smali`, `Offline.smali` do `--vnfont` build lại từ `java/src` (font `q/16`, d8 sắp xếp lệnh khác), không liên quan `.catch`;
  - `AndroidManifest.xml` (dạng text), `res/`, `assets/`, `lib/`: giống hệt `dec4`.
- `dexdump` `classes.dex` của bản T1: catch table của `CWrapperHttp$1.run` gồm `IOException -> 0x0055` và `SecurityException -> 0x0055` ở cả hai vùng.
- `apkdiff` bản T1 với TEST7: chỉ khác `AndroidManifest.xml` và thiếu `classes4.dex` (không `--diag`); `classes.dex`, `classes2.dex`, `classes3.dex` giống từng byte.
  Với TEST4: khác `AndroidManifest.xml` (mã hoá lại bởi apktool, nội dung text giống), `classes.dex` (2 dòng catch), `classes3.dex` (VNFont mới).
- `scripts/re/cov_exc.py`: bản vá giảm lệnh mạng chưa phủ `SecurityException` từ 16 xuống 7 (7 còn lại là A2).
