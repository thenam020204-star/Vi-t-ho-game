# T12 - Phân tích mod offline (Magic Survival 0.9941, Unity 2022.3.62f3, IL2CPP arm64)

Chỉ phân tích, KHÔNG vá file game. Mọi RVA là RVA trong `lib/arm64-v8a/libil2cpp.so` của `split_config.arm64_v8a.apk`
(40.523.040 B, sha256 `61546e52de7f6ec0ec285c39c3a7d2a0fbce2a2f1e02624ae5d5c8617fe9b326`, Build ID `53be159e4d707729239f52a7e504849a4989752a`).
**Đổi RVA -> offset file**: đoạn `R E` có `p_vaddr 0xd9307c / p_offset 0xd8f07c`, nên với mọi hàm dưới đây `offset = RVA - 0x4000`
(tool vá vẫn nên đọc program header, không hard-code). Byte gốc 16 đầu hàm lấy từ file đó (cột `orig16`).
Tái lập: `work/il2/` (dump.cs, script.json, scan.json), `tools/il2dis.py`, capstone; Java: `dexdump`/`aapt2 dump xmltree` (build-tools r35 trong `work/tools/android-15`).
Quy ước: `ret` = `c0 03 5f d6`, `nop` = `1f 20 03 d5`. Mọi byte "new" dưới đây đã được capstone dịch ngược để kiểm.
Vá `ret` ở lệnh đầu hàm luôn an toàn về stack vì chưa có lệnh nào của hàm chạy (x30 còn nguyên của caller).

Cách đọc bằng chứng: "A -> B" nghĩa là lệnh `bl`/`b` trong A gọi B (quét toàn bộ `.text` bằng `xref`, không chỉ danh sách scan.json).
Gọi qua UnityEvent/Button trong scene (không có `bl`) được kiểm bằng UnityPy trên `data.unity3d` (level0/1/2).

## Tóm tắt điểm vá đề xuất (đầu vào cho T13/T14)

| # | Hàm | RVA | Vá | Mục đích |
|---|---|---|---|---|
| A1 | `W_GDPR_Manager$$Start` | 0x113FFCC | `ret` | không UMP, không bao giờ bật `IsGDPR_ON` => cả hai `W_AdMobAD_*.Start` thoát sớm (không `MobileAds.Initialize`, không `LoadAd`) |
| A2 | `W_AdMobAD_Reward$$Start` / `W_AdMobAD_Interstitial$$Start` | 0x113F094 / 0x113DEE4 | `ret` (dự phòng) | như trên |
| A3 | `W_AdMobAD_Reward$$ShowRewardedAd` | 0x113F3BC | `mov x1,xzr; b 0x113FB14` | nút "xem quảng cáo hồi sinh" nhận thưởng ngay |
| A3b | `W_AdMobAD_Reward$$<ShowRewardedAd>b__7_0` | 0x113FB6C | `cbz x20,#0x113FBD8` | cho phép `reward == null` |
| A4 | `W_AdMobAD_Interstitial$$ShowInterstitial` | 0x113E1F0 | `b 0x113E710` | bỏ quảng cáo xen kẽ, đi thẳng về `A_MainMenu` |
| A5 | `C_Battle_AllyUnit$$Button_RevivalSelect` | 0x1044C98 | `nop` | bỏ kiểm tra có mạng ở nút hồi sinh |
| A6 | `C_Battle_AllyUnit.<Play_Unit_Death>d__1045$$MoveNext` | 0x108B934 | `nop` | bỏ kiểm tra có mạng khi quyết định hiện bảng hồi sinh |
| B1 | `Z_Database$$AuthenticateUser` | 0x114C180 | `ret` | không gọi `PlayGamesPlatform.Authenticate` |
| B2 | `Z_GPGSManager$$LoadLocalData` (đuôi) | 0x1171434 | `b 0x1171538` | bỏ nhánh tự đăng nhập + tải cloud lúc khởi động |
| B3 | 7 hàm nút cloud/bảng xếp hạng (xem mục b) | 0x115BB60 ... 0x1173AC8 | `ret` | nút cloud save/load/xếp hạng không làm gì |
| C1 | 7 hàm `BaseIAPButton`/`CodelessIAPStoreListener` (xem mục c) | 0x21DF7C4 ... 0x21E0AD0 | `ret` | không khởi tạo Unity Purchasing, nút mua vô hiệu |
| E1 | `C_Battle_AllyUnit$$Unit_Damage` | 0x1048858 | hook Dobby (hoặc `ret`) | bất tử |
| E2 | `C_Battle_AllyUnit$$Unit_Death` | 0x10449F0 | hook Dobby | chặn chết do tự trừ máu của kỹ năng |
| F1 | `C_Battle_Manager$$Update` | 0x109CBF8 | hook Dobby | chạy lệnh từ menu trên luồng Unity, phát hiện "đang trong trận" |
| F2 | `V_Ability_Manager$$ManaGain` | 0x10FC64C | hook (lấy `this`) + gọi | lên cấp |

(d) Không có kiểm tra bản quyền/chữ ký/integrity do game viết: không cần vá gì (xem mục d).

---------------------------------------------------------------------------------------------------

## (a) Quảng cáo

**SDK**: chỉ **Google Mobile Ads (AdMob) Unity plugin** + **UMP (GDPR consent)** (`GoogleMobileAds.Api`, `GoogleMobileAds.Ump.Api`; Java `com.google.unity.ads.*`, `com.google.android.gms.ads.*`).
Không có Unity Ads/AppLovin/IronSource (không có namespace tương ứng trong dump.cs). Không có Banner/AppOpen trong mã C# game (không ai gọi `BannerView`/`AppOpenAd`;
lớp Java `com.google.unity.ads.Banner` chỉ nằm trong plugin). Mã game tự viết chỉ có 4 lớp: `W_GDPR_Manager`, `W_GDPR_Setting`, `W_AdMobAD_Reward`, `W_AdMobAD_Interstitial`.
ID (chỉ để nhận diện, không dùng): app `ca-app-pub-3480125910446181~1087202844` (meta-data trong manifest), rewarded `.../7806767243`, interstitial `.../1208713077`.

**Gắn vào scene** (UnityPy, level = scene): `W_GDPR_Manager` trên "Main Camera" của **level1 (menu chính)**, `W_AdMobAD_Reward` + `W_AdMobAD_Interstitial` trên "Main Camera" của **level2 (trận)**.

### Khởi tạo
- `W_GDPR_Manager$$Start` (0x113FFCC): đặt `MobileAds.RaiseAdEventsOnUnityMainThread=true` (static +0x20), `IsGDPR_ON(static +0)=0` (lệnh 0x11400E0 `strb wzr,[x8]`), rồi `ConsentInformation.Update(params, OnConsentInfoUpdated)` (tail `b 0x1AAD9C8`).
  Callback `<>c__DisplayClass4_0$$<OnConsentInfoUpdated>b__0` (0x1140330) đặt `IsGDPR_ON = ConsentInformation.CanRequestAds()` (0x11406D0 `strb w9,[x8]`).
- `W_AdMobAD_Reward$$Start` (0x113F094) và `W_AdMobAD_Interstitial$$Start` (0x113DEE4): `ldrb w8,[IsGDPR_ON]; cbz w8 -> ret`
  (0x113F110-0x113F114: `ldrb w8,[x8]; cbz w8,#0x113f1e0`; 0x113DF60-0x113DF64 tương tự). Nếu bật: `MobileAds.Initialize` (0x1AA88C8) rồi tail `LoadAd`.
  => **IsGDPR_ON là cổng duy nhất**: nếu `W_GDPR_Manager.Start` không chạy thì IsGDPR_ON luôn = 0 (giá trị mặc định static), SDK quảng cáo không bao giờ được khởi tạo từ C#.
  (Phía Java vẫn có `MobileAdsInitProvider` trong manifest tự khởi tạo SDK lúc mở app; vô hại khi offline, **không cần đụng manifest** theo M4. Tuỳ chọn nếu sau muốn sạch hẳn: xoá provider đó + 3 permission ADSERVICES.)

### Hiện quảng cáo
- **Xen kẽ (interstitial)**: `W_AdMobAD_Interstitial$$ShowInterstitial` (0x113E1F0) có đúng 2 chỗ gọi:
  `C_Battle_Manager$$Button_SceneMove` (0x10BBEDC, `bl` tại 0x10BC108) và `C_Battle_Manager.<Fade_RewardClass>d__582$$MoveNext` (0x10EF408, `bl` tại 0x10EF67C).
  Điều kiện gọi (cả hai): có mạng, `!Z_Database.IAP_DestroyAD` (ObscuredBool static +0x840; cổng 0x10BC018-0x10BC024 và 0x10EF580-0x10EF58C), `Game_SurvivalTime (Obscured, +0x918) >= 60.0f` (`mov w8,#0x42700000`), `IsRewardAdShow == 0`.
  Nếu không đủ điều kiện, đi thẳng `Scene_Moving("A_MainMenu","")`. Khi quảng cáo đóng: `<RegisterEventHandlers>b__11_4` (0x113E978) -> log -> tail `W_AdMobAD_Interstitial$$Scene_Moving` (0x113E768) = `SceneManager.LoadScene("A_MainMenu")`.
  Khi chưa sẵn sàng: log lỗi + `StartCoroutine("Play_NoAdField")` (hiện chữ "không có quảng cáo" rồi mới cho đi tiếp).
- **Có thưởng (rewarded)**: chỉ **một luồng duy nhất = hồi sinh khi chết**. Không có x2 thưởng/bonus nào dùng quảng cáo (không có `bl` nào khác tới `W_AdMobAD_Reward`).
  Chuỗi: chết -> `C_Battle_AllyUnit.<Play_Unit_Death>d__1045$$MoveNext` (0x108B4A0) hiện bảng `Obj_FieldRevival` -> người chơi bấm nút "Select" (scene level2 GameObject `Button_ReviveSelect`, UnityEvent -> `C_Battle_AllyUnit$$Button_RevivalSelect`, 0x1044BD4)
  -> (có mạng) đếm `RewardAdNum (+0x820)++`, lần đầu (`<=1`) lấy `GetComponent<W_AdMobAD_Reward>()` từ `Obj_MainCamera (+0x20)` rồi **tail `b 0x113F3BC`** (0x1044E68) = `W_AdMobAD_Reward$$ShowRewardedAd`
  -> `RewardedAd.Show(Action<Reward> = <ShowRewardedAd>b__7_0)` -> khi xem xong SDK gọi **callback thưởng** `W_AdMobAD_Reward$$<ShowRewardedAd>b__7_0` (0x113FB14)
  -> `Obj_AllyUnit (+0x30)->GetComponent<C_Battle_AllyUnit>()` -> **`C_Battle_AllyUnit$$Unit_RevivalON` (0x1045070)** (cũng được `Play_Unit_Death` gọi trực tiếp tại 0x108B868 khi có "No Ads" - xem dưới).
  `Unit_RevivalON`: `C_Battle_Manager.IsRewardAdShow(static +0x27C)=1`, dịch nhân vật (set_position +10), `Unit_Hp = clamp(UnitApply_HpMax, ApplyPHpMaxNum)`, `Unit_StatBar()`,
  tắt `Obj_FieldRevival (+0x2F0)`, `Battle_IsPlay=true (static +0x1D1)`, `StartCoroutine("Play_RevivalON")` (khôi phục timeScale + `Unit_AriseDivine` + `C_Battle_Manager.Instant_StopExit`).
  Nút Cancel (`Button_RevivalCancel` 0x10453F0) -> `Game_EndON`. Phần đóng popup/cập nhật trạng thái nằm trong `Unit_RevivalON`/`Play_RevivalON` => chỉ cần gọi `Unit_RevivalON` là giữ đúng flow UI.
- **Cổng "có mạng"** (`Application.internetReachability`, RVA 0x2173928) chặn cả popup lẫn nút khi offline:
  `Play_Unit_Death.MoveNext` lệnh 0x108B930/0x108B934 `bl get_internetReachability; cbz w0,#0x108BAF4` (offline -> log "인터넷 연결 : X" -> `Game_EndON`, không hiện bảng hồi sinh)
  và `Button_RevivalSelect` lệnh 0x1044C94/0x1044C98 `cbz w0,#0x1044D94` (offline -> thoát trận). Quảng cáo-không-mạng nên **phải bỏ hai cổng này** thì chơi offline mới có hồi sinh.
- Điều kiện hiện bảng hồi sinh (giữ nguyên): `Game_SurvivalTime >= 30.0f` và `IsRewardAdShow == 0` (0x108B7C0-0x108B834).
- **"No Ads" (`Z_Database.IAP_DestroyAD`)**: ngoài bỏ interstitial, còn kích hoạt **hồi sinh miễn phí tự động một lần/trận** (`Play_Unit_Death` 0x108B79C-0x108B7C8: `if (IAP_DestroyAD && IsRewardAdShow==0) Unit_RevivalON()`, bỏ qua bảng). Vì vậy **không nên bật cờ này** để "tắt quảng cáo":
  nó đổi gameplay (hồi sinh tự động, không thấy bảng) và lưu vào save mã hoá. Đã không cần: vá A1-A4 đã loại hoàn toàn quảng cáo. (Nếu muốn đúng ý M3 "coi No Ads = true chỉ cho việc hiện QC": đổi `tbnz w0,#0,...` thành `b` ở 0x10BC024 và 0x10EF58C - dư thừa khi đã vá A4.)

### Đề xuất vá (kèm orig16)
| Điểm | RVA | orig16 | new |
|---|---|---|---|
| A1 `W_GDPR_Manager$$Start` | 0x113FFCC | `fe0f1df8 f65701a9 f44f02a9 95ab00d0` | `c0035fd6` (ret) |
| A2 `W_AdMobAD_Reward$$Start` | 0x113F094 | `fe0f1df8 f65701a9 f44f02a9 94ab00d0` | `c0035fd6` |
| A2 `W_AdMobAD_Interstitial$$Start` | 0x113DEE4 | `fe0f1df8 f65701a9 f44f02a9 b4ab0090` | `c0035fd6` |
| A3 `W_AdMobAD_Reward$$ShowRewardedAd` | 0x113F3BC | `fe57bea9 f44f01a9 94ab00d0 88224e39` | `e1031faa d5010014` = `mov x1,xzr; b #0x113FB14` |
| A3b callback `cbz x20` | 0x113FB6C | `140500b4 489e00f0 08c542f9 800e40fd` | `740300b4` = `cbz x20,#0x113FBD8` |
| A4 `W_AdMobAD_Interstitial$$ShowInterstitial` | 0x113E1F0 | `fe57bea9 f44f01a9 94ab00f0 88d64d39` | `48010014` = `b #0x113E710` |
| A5 `Button_RevivalSelect` cbz | 0x1044C98 | `e0070034 682248b9 08050011 1f050071` | `1f2003d5` (nop) |
| A6 `Play_Unit_Death` cbz | 0x108B934 | `000e0034 e003271e e0031faa 873c4494` | `1f2003d5` |

Giải thích A3/A3b: vào thẳng đầu callback (0x113FB14) như một tail-call từ `ShowRewardedAd` (x0 = `this` đúng kiểu `W_AdMobAD_Reward`, lr = caller), prologue của callback tự khởi tạo metadata IL2CPP (cờ 0x38E) rồi `cbz x20` (reward == null) nhảy sang 0x113FBD8 -
đoạn `Obj_AllyUnit->GetComponent<C_Battle_AllyUnit>()->Unit_RevivalON(…)` (bỏ qua `Debug.Log` dùng `reward.Amount/Type`, nếu để null sẽ NullReference ở 0x113FC0C). Callback nguyên bản vẫn đúng khi `reward != null`.
Giải thích A4: `0x113E710` = `W_AdMobAD_Interstitial$$Button_Scene_Moving(this)` -> `Scene_Moving("A_MainMenu","")` (đúng những gì handler "ad closed" làm).
A5/A6: bỏ `cbz w0` sau `Application.internetReachability` (nhánh offline) để luôn đi nhánh "có mạng"; lệnh sau nó không phụ thuộc w0.

## (b) GPGS / Google Play Games / Cloud save

**SDK**: Google Play Games Plugin for Unity (GooglePlayGames.*, `PlayGamesPlatform`, `ISavedGameClient`); Java `com.google.games.bridge.*`, `PlayGamesInitProvider`, meta-data `com.google.android.gms.games.APP_ID=1088046652684`.
Mã game: `Z_Database` (đăng nhập), `Z_GPGSManager` (cloud + **cả lưu/đọc dữ liệu cục bộ**, nên KHÔNG được vá `SaveLocalData`/`LoadLocalData` toàn hàm).
`Z_GPGSManager` và `Z_Database` nằm trên "Main Camera" của level0, level1 và (Z_GPGSManager) level2.

### Luồng đăng nhập / khởi động
- `Z_Database$$Awake` (0x114AB10): `PlayGamesPlatform.Activate()`; `bl Z_Database$$AuthenticateUser` tại **0x114BD04**; rồi **tail `b Z_GPGSManager$$LoadLocalData` tại 0x114BE60**.
- `Z_Database$$AuthenticateUser` (0x114C180): tạo `Action<SignInStatus>` tĩnh (`<>c.<AuthenticateUser>b__833_0`, 0x1174920) và tail `PlayGamesPlatform.Authenticate(cb)` (0x1A65BE8, lệnh 0x114C26C).
  Callback **chỉ `Debug.Log`** ("GPGS 로그인 성공!" / "실패! 오류 코드: ") => không có gì trong game chờ kết quả đăng nhập, **không treo màn hình loading** dù đăng nhập thất bại hay không bao giờ trả lời.
- `Z_GPGSManager$$LoadLocalData` (0x11621F0): đọc file save cục bộ (AES, `Application.persistentDataPath/...json`); **đuôi hàm (từ 0x1171434)**:
  có mạng -> `Social.localUser.Authenticate(Action<bool> <LoadLocalData>b__115_0)` (0x1171454..0x1171488) -> callback (0x1174628) đặt `Game_CloudID=15`, tail `LoadFromCloud_Start` (0x115F160);
  không mạng -> `Game_CloudID=15; Show_CloudResult(-1)` (0x1171514) (chỉ ghi chữ trạng thái "인터넷 연결 실패" vào `Text_GameState` vì CloudID==15, **CHƯA XÁC ĐỊNH** có thấy trên màn hình hay không).
  Nếu có mạng mà không có tài khoản Play Games, đường này tạo popup đăng nhập hệ thống / kết quả lỗi cloud => cần chặn (B2).
- `LoadFromCloud_Start` / `SaveToCloud_Start` / `SaveToCloud` (0x115F160 / 0x115D970 / 0x115DDF8): `internetReachability` + `Social.localUser.authenticated` (gọi interface ILocalUser) -> sai thì `Show_CloudResult(2 hoặc -1)` ("저장 실패"/"인터넷 연결 실패"), đúng thì `OpenSavedGame` -> API cloud.
- `SaveLocalData(int CloudType)` (0x1160CE0) tail `SaveToCloud` (0x1162160) chỉ ở nhánh `CloudType==1` (bảng nhảy tại 0x11620DC-0x11620FC); lưu cục bộ bình thường (`CloudType==0`) không đụng cloud.
- Callers cloud ngoài luồng trên: `ButtonClick_IAP_01/02/06` -> `SaveToCloud_Start` (chỉ sau khi mua IAP; không tới được khi IAP vô hiệu).
- Bảng xếp hạng: 4 nút scene level1 `Button_RankA/B/C/IN` -> UnityEvent `Z_GPGSManager.OnLogin` (0x1173AC8) -> `Social.localUser.Authenticate` -> `<OnLogin>b__126_0` -> `OnShowLeaderBoard` (0x1173E44, `Social.ReportScore`).
- Nút cloud (scene): `Button_CloudSaveApply` (level1+level2) -> `Button_CloudSave_Apply` (0x115BC50: có mạng -> `Social.localUser.Authenticate(<Button_CloudSave_Apply>b__91_0)`); các nút Option/Load/Check:
  `Button_CloudSave_Option` 0x115BB60, `Button_CloudLoad_Option` 0x115CCE4, `Button_CloudLoad_Apply` 0x115CF3C, `Button_CloudLoadAuto_Apply` 0x115D0E8, `Button_CloudLoadCheck` 0x115D644 (-> `Authenticate(<Button_CloudLoadCheck>b__96_0)` -> `LoadFromCloud`/`LoadFromCloud_Auto`).
- `CheckLogin` (0x115DBCC) = `Social.localUser.authenticated`; không có `bl` tới nó (chỉ nằm cho UnityEvent, scene không gọi).
- Hậu quả khi không đăng nhập (nếu không vá): popup/kết quả lỗi cloud mỗi lần mở app có mạng, các nút cloud mở popup chờ rồi báo lỗi, có thể bật UI đăng nhập Play Games của hệ thống. Không văng, không treo loading (xác nhận ở trên).
- Chuỗi tiếng Anh hiển thị về cloud (đã dịch nằm ngoài phạm vi): `Z_Database$$Awake`: "Uninstalling the game will also remove your data. Use the Google Cloud Save/Load..." (chỉ là chữ, mặc kệ).

### Đề xuất vá
- **B1** `Z_Database$$AuthenticateUser` 0x114C180: `ret`. orig16 `fe0f1df8 f65701a9 f44f02a9 33ab00b0`.
- **B2** `Z_GPGSManager$$LoadLocalData` 0x1171434: `b 0x1171538` (`41000014`) - bỏ qua cả nhánh Authenticate/Show_CloudResult và nhảy thẳng vào epilogue hàm (0x1171538 = `ldp x20,x19,[sp,#0xb0] ... ret`, cùng frame `sp+0xC0`). orig16 `a00340f9 08e040b9 48000035 6b91f697` (= `ldr x0,[x29]; ldr w8,[x0,#0xe0]; cbnz w8,..; bl ..`).
  Kiểm tra: 0x117141C `b #0x1171434` và 0x1171430 `cbnz w19,#0x116fc9c` cho thấy 0x1171434 là lệnh đầu của đuôi hàm (sau vòng lặp cuối); các nhánh trong đuôi đều kết thúc ở epilogue 0x1171538. Cần T13 disasm trước/sau để chắc.
- **B3** `ret` (`c0035fd6`) ở lệnh đầu:
  | Hàm | RVA | orig16 |
  |---|---|---|
  | `Button_CloudSave_Option` | 0x115BB60 | `fe0f1ef8 f44f01a9 b4aa00d0 88924f39` |
  | `Button_CloudSave_Apply` | 0x115BC50 | `fe57bea9 f44f01a9 b4aa00d0 889a4f39` |
  | `Button_CloudLoad_Option` | 0x115CCE4 | `fe0f1df8 f65701a9 f44f02a9 b4aa00b0` |
  | `Button_CloudLoad_Apply` | 0x115CF3C | `fe57bea9 f44f01a9 b4aa00b0 88a64f39` |
  | `Button_CloudLoadAuto_Apply` | 0x115D0E8 | `fe0f1af8 fc6f01a9 fa6702a9 f85f03a9` |
  | `Button_CloudLoadCheck` | 0x115D644 | `fe57bea9 f44f01a9 b4aa0090 88ae4f39` |
  | `OnLogin` (4 nút xếp hạng) | 0x1173AC8 | `fe0f1cf8 f85f01a9 f65702a9 f44f03a9` |
  (Chỉ cần "Option" để không mở popup; "Apply/Check/OnLogin" là phòng thủ.) UI các nút vẫn hiện nhưng bấm không có phản ứng (an toàn). Không đụng `SaveLocalData`/`LoadLocalData` còn lại.
- Không cần sửa manifest: `PlayGamesInitProvider` chỉ khởi tạo SDK lặng lẽ.

## (c) IAP (Unity Purchasing 4.x "Codeless") và cờ No Ads

- **Cơ chế**: không có mã game nào gọi `UnityEngine.Purchasing` trực tiếp. Có **6 component `UnityEngine.Purchasing.IAPButton`** (assembly `UnityEngine.Purchasing.Codeless`) trên `Button_IAP_01..06` của **level1** (path_id 5778, 5915, 6222, 5836, 6444, 7288),
  mỗi cái có `onPurchaseComplete` (UnityEvent) trỏ tới `A_MainMenuInformation.ButtonClick_IAP_0N`. `productId` đọc được: IAP_02 = `tbonus`; các ID còn lại **CHƯA XÁC ĐỊNH** (chưa đọc, không cần).
- **Khởi tạo**: `[RuntimeInitializeOnLoadMethod] CodelessIAPStoreListener$$InitializeCodelessPurchasingOnLoad` (0x21E0A60; `RuntimeInitializeOnLoads.json`, loadTypes 0 = AfterSceneLoad):
  `ProductCatalog.LoadDefaultCatalog`; nếu catalog không rỗng và instance null -> `b CreateCodelessIAPStoreListenerInstance` (0x21E0AD0, async) -> `InitializePurchasing` (0x21E0B54) -> `UnityPurchasing.Initialize` (0x21E95A0).
  Đường dự phòng: `CodelessIAPStoreListener$$get_Instance` (0x21DFA4C) tự tạo instance nếu null; `BaseIAPButton$$OnEnable` (0x21DFDB4) -> `AddButtonToCodelessListener` (0x21E4200) -> `get_Instance`.
  `BaseIAPButton$$Start` (0x21DF7C4) gắn `onClick -> PurchaseProduct`; `PurchaseProduct` (0x21DFE74) -> `CodelessIAPStoreListener$$InitiatePurchase` (0x21DFED0); `Restore` (0x21E0010) nút khôi phục.
  Thanh toán xong: `IAPButton.ProcessPurchase` (0x21E31AC) -> `BaseIAPButton.ProcessPurchaseInternal` (0x21DFFD0) -> `IAPButton.OnPurchaseComplete` (0x21E4158, `UnityEvent<Product>.Invoke`) -> `ButtonClick_IAP_0N`.
- **Cờ lưu (Z_Database static, ObscuredBool 12 byte)**: `IAP_DestroyAD` +0x840 (**No Ads**), `IAP_GoldBonus` +0x84C, `IAP_limitedProduct01` +0x858, `IAP_PointA` +0x864; bản sao `FirstData_IAP_DestroyAD` +0x944.
  Được đặt bởi: `ButtonClick_IAP_01` (0x1024FDC, `str x20,[x8,#0x840]` tại 0x1025090) = No Ads; `_02` (0x1025268, +0x84C) = gold bonus; `_06` (0x10258D8, +0x858) = limited product; `_03.._05` = gói gem.
  Lưu/đọc: `Z_GPGSManager$$SaveLocalData` / `LoadLocalData` (mảng JSON mã hoá AES, phần tử [3] -> `IAP_DestroyAD`, `Boolean.Parse`, 0x11631E0); tên file cụ thể **CHƯA XÁC ĐỊNH** (không cần).
  Đọc cờ No Ads: `Button_SceneMove` (0x10BC018), `Fade_RewardClass` (0x10EF580), `Play_Unit_Death` (0x108B794), `Unit_StatBuff` (0x1047A50), `Button_Experiment` (0x1019FEC), `MainMenuInformation` (0x101318C)...
- Không có kiểm tra "restore purchases" hay xác thực biên lai trong mã game (lớp `GooglePlayTangle`/`AppleTangle` có trong assembly nhưng không ai gọi).

### Đề xuất vá (M4: KHÔNG mở khoá gói trả phí; không bật `IAP_*`)
`ret` (`c0035fd6`) ở lệnh đầu của:
| Hàm | RVA | orig16 |
|---|---|---|
| `BaseIAPButton$$Start` | 0x21DF7C4 | `fe0f1df8 f65701a9 f44f02a9 d42600b0` |
| `BaseIAPButton$$OnEnable` | 0x21DFDB4 | `fe0f1ef8 f44f01a9 d42600b0 88a26539` |
| `BaseIAPButton$$OnDisable` | 0x21DFE40 | `fe4fbfa9 080040f9 f30300aa 09855fa9` |
| `BaseIAPButton$$PurchaseProduct` | 0x21DFE74 | `fe0f1ef8 f44f01a9 080040f9 f30300aa` |
| `BaseIAPButton$$Restore` | 0x21E0010 | `ff0301d1 fe5702a9 f44f03a9 d4260090` |
| `CodelessIAPStoreListener$$InitializeCodelessPurchasingOnLoad` | 0x21E0A60 | `fe4fbfa9 d3260090 68ca6539 c8000037` |
| `CodelessIAPStoreListener$$CreateCodelessIAPStoreListenerInstance` | 0x21E0AD0 | `ffc301d1 fe2b00f9 f44f06a9 d4260090` |
Kết quả: `UnityPurchasing.Initialize` không bao giờ chạy, nút mua không có listener, không có `ButtonClick_IAP_0N`. Cờ No Ads giữ nguyên trạng thái save (mặc định false); quảng cáo đã tắt bằng mục (a).
Tuỳ chọn (UI gọn): `A_MainMenuInformation$$Button_IAP_IN` 0x1024F00 `ret` (không mở trang cửa hàng IAP), orig16 `fe0f1ef8 f44f01a9 74b40090 88127d39`. Giữ nguyên trang nếu muốn chỉ có nút mua "câm".
Lưu ý: Unity Gaming Services core (`UnityServicesInitializer`, `CorePackageInitializer.InitializeOnLoad`, `IapCoreInitializeCallback.Register` - cùng `RuntimeInitializeOnLoads.json`) vẫn chạy lúc khởi động; offline chỉ thất bại bất đồng bộ.
Có treo hay không: **CHƯA XÁC ĐỊNH** (không thấy mã game chờ nó); nếu T15 thử thấy treo thì vá `UnityServicesInitializer$$EnableServicesInitializationAsync`.

## (d) Bản quyền / chữ ký / integrity / tamper

Kết quả: **không có kiểm tra nào do game viết**.
- **Java** (dexdump `classes.dex` 7477 lớp + `classes2.dex` 10675 lớp; quét `getPackageInfo/GET_SIGNATURES/getSigningInfo/IntegrityManager/SafetyNet/LicenseChecker/getInstallerPackageName/getInstallSourceInfo/isDebuggerConnected`):
  chỉ trong thư viện: `androidx.browser.trusted.PackageIdentityUtils`, `androidx.core.content.pm.PackageInfoCompat`, `androidx.webkit`, billing (`BillingClientImpl`), `com.google.android.gms.*` (GmsSignatureVerifier cho chính GMS, ads `zzese` đọc `getInstallSourceInfo`),
  `com.google.android.play.core.assetpacks.*` (lấy versionCode của chính app để so với pack). Không có `LicenseChecker`/Play Integrity API (`com.google.android.play.core.integrity` không có).
  Lớp Application riêng: **không** (`<application>` không có `android:name`; `appComponentFactory=androidx.core.app.CoreComponentFactory`). `UnityPlayerActivity` là bản gốc của Unity (extends `android.app.Activity`).
- **C# game**: quét toàn bộ lời gọi/chuỗi của 4412 method Assembly-CSharp: không có `Detectors`/`GenuineValidator`/`CodeHash`/`ObscuredCheatingDetector`/`InjectionDetector`/`SpeedHackDetector`; scene không có component detector nào (UnityPy level0/1/2).
  Chỉ dùng **ACTk Obscured types** (ObscuredInt/Float/Bool/String) để giấu giá trị trong RAM/save; không có detector thì thay đổi giá trị không kích hoạt gì. Cẩn thận: ghi trực tiếp vào ObscuredX cần đúng cơ chế (mã hoá + `hiddenValue`), nên hook của mod chỉ nên ghi vào **trường thường** (HP, mana, level - xem e/f).
- **Native**: `libunity.so` có chuỗi `getPackageInfo` (chuỗi JNI nội bộ của Unity; mục đích CHƯA XÁC ĐỊNH, không thấy liên quan tới đối chiếu chữ ký). `libmain.so` (6,7 KB), `libil2cpp.so`: không có chuỗi root/frida/signature/integrity.
- IAP `UnityEngine.Purchasing.Security` (CrossPlatformValidator, `GooglePlayTangle`) tồn tại nhưng không ai gọi.
- Đổi khoá ký: Play Asset Delivery chỉ là thư viện (`AssetPackExtractionService` `enabled=false`, game không dùng `Google.Play.AssetDelivery`); toàn bộ asset nằm trong APK. => **ký lại bằng khoá thử nghiệm sẽ chạy bình thường**. Hệ quả duy nhất: phải gỡ bản gốc trước khi cài (đã ghi trong tools/README).
- Kiểm tra CHƯA XÁC ĐỊNH nhỏ: ACTk `ObscuredPrefs` (device lock) có trong assembly nhưng game không gọi `ObscuredPrefs.*` (0 lời gọi trong method game); save dùng AES riêng `Z_AESCrypto` với khoá hard-code trong `LoadLocalData` (chuỗi `!%&)#...`), không gắn thiết bị/chữ ký.

## (e) Nhân vật nhận sát thương / chết

- Lớp nhân vật: **`C_Battle_AllyUnit`** (MonoBehaviour trên "Main Camera"/đối tượng người chơi, level2). **Mọi trạng thái người chơi là static** - không cần con trỏ instance để đọc/ghi.
  Con trỏ `Il2CppClass*` nằm ở slot `*(libil2cpp_base + 0x259EE58)` (`C_Battle_AllyUnit_TypeInfo`), `static_fields = *(void**)(klass + 0xB8)`
  (game dùng đúng mẫu này: `ldr x0,[slot]; ldr x8,[x0,#0xb8]`). Slot có thể còn null trước khi game chạm lớp lần đầu - kiểm null mỗi lần.
- Trường (offset từ `static_fields`, không phải Obscured, kiểu thường):
  | Trường | Offset | Kiểu | Ghi chú |
  |---|---|---|---|
  | `Unit_IsLive` | +0x10 | bool | còn sống |
  | `UnitApply_HpMax` | +0x30 | float | HP tối đa hiện hành |
  | `UnitApply_EvasionChance` | +0x34 | int | tỉ lệ né % |
  | `Unit_Hp` | +0x38 | float | **HP hiện tại** |
  | `Unit_HpPlus` | +0x3C | float | HP bổ sung (khiên máu) |
  | `Unit_HpRecovery` | +0x40 | float | |
  | `IsDivine` | +0x2A0 | bool | trạng thái bất tử tạm thời có sẵn của game (`Unit_Damage` thoát ngay nếu bật) |
  | `ApplyPHpMaxNum` | +0x12C | float | trần dùng khi hồi sinh |
- **Hàm trừ máu duy nhất do kẻ địch gây ra**: `C_Battle_AllyUnit$$Unit_Damage(float dmg, float special, int damageType)` RVA **0x1048858** (x0=this, s0=dmg, s1=special, w1=type).
  Callers: `C_Battle_EnemyBullet$$Bullet_Hit` (0x10926C8), `C_Battle_EnemyExplosion$$OnCollisionEnter` (0x10940F0), `C_Battle_EnemyUnit$$Unit_Attack` (0x109B614), `C_Battle_Manager.<Play_BossLimitGround_Damage>d__513$$MoveNext` (0x10F64E8).
  Thân hàm: `if (!Unit_IsLive || IsDivine) return` (0x1048994/0x10489B0 `ldrb w9,[x8,#0x10]; cbz`; `ldrb w9,[x8,#0x2a0]; cbz`); `dmg<=0 -> return`; khiên `C_Battle_AllyUnit_Shield` chặn; né (`EvasionChance > Random.Range(0,101)` -> chữ MISS qua `C_Battle_TextEffect$$Start_TextEffect`);
  rồi trừ: `Unit_Hp = clamp(Unit_Hp - dmg, 0, ...)` bằng `stp s0,s2,[x8,#0x38]` tại **0x10490B0** (Unit_Hp, Unit_HpPlus), cuối cùng tail `Unit_StatBar` (0x10490B8 `b 0x10451B8`).
- **Chết**: `Unit_StatBar` (0x10451B8) tail-gọi `C_Battle_AllyUnit$$Unit_Death` (0x10449F0): `if (Unit_Hp <= 0 && Unit_IsLive) StartCoroutine("Play_Unit_Death")` (0x1044A48-0x1044A8C). `Unit_Death` chỉ được gọi từ `Unit_StatBar`.
  `Play_Unit_Death` (0x1044B6C -> MoveNext 0x108B4A0): animation chết, rồi hồi sinh (mục a) hoặc `Game_EndON` (0x1044E70, set `Battle_IsPlay=false`...). `Game_ClearON` (0x10454F0) là thắng.
- Có **~25 chỗ khác ghi trực tiếp `Unit_Hp`/`Unit_HpPlus`** (không qua `Unit_Damage`) - chủ yếu hồi máu, nhưng một số kỹ năng (Meteor, Tsunami, Familiar, Fireball Com03, FlashShock Com04, SuperNova, Ultimate43...) trừ máu bản thân
  (quét `str s*,[x*,#0x38/#0x3c]` trong hàm tham chiếu `C_Battle_AllyUnit_TypeInfo`). Vì vậy chỉ chặn `Unit_Damage` chưa chắc đủ.
- **Đề xuất điểm hook bất tử (ưu tiên đơn giản)**:
  1. **Hook `Unit_Damage` (0x1048858)**: nếu cờ god bật thì `return` ngay, nếu không gọi bản gốc. Chữ ký đơn giản `void(C_Battle_AllyUnit*, float, float, int)`. Tương đương vá tĩnh `ret` (`c0035fd6`) nếu muốn bất tử vĩnh viễn.
     orig16 `ea0f1afc e923016d fe6702a9 f85f03a9` (= `str d10,[sp,#-0x60]!; stp d9,d8,[sp,#0x10]; stp x30,x25,[sp,#0x20]; stp x24,x23,[sp,#0x30]`, không có lệnh PC-relative -> Dobby relocate được).
  2. **Hook `Unit_Death` (0x10449F0)**: khi god bật thì `return` (chặn chết do tự trừ máu). orig16 `fe57bea9 f44f01a9 75b30090 54a60090` (chứa `adrp x21/x20` - Dobby tự relocate adrp; nếu lo ngại thì thay bằng bước 3).
  3. Trong hook `C_Battle_Manager$$Update` (mục f), khi god bật: `if (Unit_Hp < UnitApply_HpMax) Unit_Hp = UnitApply_HpMax` (và `Unit_IsLive` giữ nguyên). Chỉ ghi float thường, không đụng Obscured.
  Không nên chỉ bật `IsDivine` (+0x2A0): các coroutine `Unit_DamageDivine`/`Play_Unit_Divine` đặt lại về false theo bộ đếm thời gian.

## (f) EXP / cấp trong trận

Game gọi EXP là **"Mana"**. Trạng thái nằm ở **static của `Z_Database`** (Il2CppClass* slot `*(base + 0x25A4B48)`), kiểu thường:
| Trường | Offset (static_fields) | Kiểu | Ý nghĩa |
|---|---|---|---|
| `AllyUnitGlobal_Level` | +0x9D8 | int | cấp hiện tại |
| `AllyUnitGlobal_LevelAdd` | +0x9DC | int | cấp bổ sung (sau cấp tối đa thường) |
| `AllyUnitGlobal_LevelAddMax` | +0x9E0 | int | |
| `AllyUnitGlobal_ManaCost` | +0x9E4 | float | EXP cần cho lần lên cấp kế |
| `AllyUnitGlobal_ManaStock` | +0x9E8 | float | EXP hiện có |
| `AllyUnitGlobal_ManaObtainLimit` | +0x9EC | float | |
| `C_Battle_AllyUnit.ApplyLevelMax` | AllyUnit static +0x18 | int | trần cấp thường |
| `C_Battle_AllyUnit.ApplyLevelAddMax` | AllyUnit static +0x1C | int | trần cấp bổ sung |

- **Hàm cộng EXP = `V_Ability_Manager$$ManaGain(float mana)` RVA 0x10FC64C** (instance, x0=this V_Ability_Manager, s0=mana). Callers: `C_Battle_Item$$Obtain_Item` (0x10CC368, lệnh 0x10CCE08; nhặt bóng mana), `C_Battle_EnemyUnit.<Play_Unit_Death>d__266$$MoveNext` (0x10C62F4),
  `C_Battle_AllyUnit.<Play_Regen>`, `<Play_Obtain_WorldTreeOrb>`, `V_Artifact_Manager.<Play_ObeliskLevelUp>`.
  Thân hàm: `if (Unit_IsLive) { if (Level < ApplyLevelMax) { ManaStock += mana; if (ManaStock >= ManaCost && !(this+0x90 /*Obj_CanvasArtifact*/ ).activeSelf) { ManaStock -= ManaCost; Level++ ... ManaCost = Dictionary_Ability_Mana[min(Level,100)]["마나 요구량"]; ...; Unit_StatBar(); Create_Ability(); } } else if (LevelAdd < ApplyLevelAddMax) {...tương tự với LevelAdd...} }`
  (0x10FC6F0-0x10FC8E8 nhánh Level, 0x10FC8EC-0x10FC9FC nhánh LevelAdd; `fadd s0,s0,s8; fcmp s0,s1` tại 0x10FC768/0x10FC934).
- **Hàm "lên cấp hiện bảng chọn kỹ năng" = `V_Ability_Manager$$Create_Ability()` RVA 0x10FCD6C** (gọi từ `ManaGain` tại 0x10FCD14 và `C_Battle_Manager.<First_Fade_IN_Add>` 0x10F35DC): dựng 3 lựa chọn, kích hoạt bảng (`SetActive`), `Time.timeScale = 0` (0x10FD244), phát âm.
  Người chơi chọn -> `X_Select_Ability$$Button_SelectAbility` (0x1142E48) đóng bảng và trả `timeScale`.
- **Trạng thái đang chọn kỹ năng (để không gọi chồng)**: bảng hiện <=> `Time.timeScale == 0` (hàm `UnityEngine.Time$$get_timeScale` RVA 0x219AB34; chính `C_Battle_Manager$$Update` dùng `fcmp s0,#0` ở 0x109CD3C) - đây là tín hiệu duy nhất `Create_Ability` đặt rõ ràng.
  Phụ: `ManaGain` tự hoãn nếu `Obj_CanvasArtifact` (V_Ability_Manager +0x90) đang bật (EXP vẫn cộng dồn, lên cấp sau). `V_Ability_Manager.Ability_SelectON` (static +0x08) KHÔNG phải cờ này (chỉ đặt ở `Start_AbilityCh` menu chính / `Play_LimitGround`).
  Cũng có các static `C_Battle_Manager.IsGamePaused` (+0x1D0), `IsStopGame` (+0x1E8), `Battle_IsPlay` (+0x1D1) cho pause/menu/kết thúc (chưa xác định cái nào đặt khi mở bảng skill: **CHƯA XÁC ĐỊNH**, dùng timeScale).
- **Instance `V_Ability_Manager`**: không có singleton tĩnh. Mẫu của game: `Obj_MainCamera (field +0x20 của nhiều lớp) -> GetComponent<V_Ability_Manager>()` (ví dụ `C_Battle_Item$$Obtain_Item` 0x10CCDF0-0x10CCE04). Hai cách cho `libmsmod.so`:
  (i) **hook `ManaGain`** (0x10FC64C) chỉ để lưu `this` (hợp lệ cho tới khi cảnh trận bị huỷ; xoá khi `C_Battle_Manager$$Start` 0x109E444 chạy lại), hoặc
  (ii) gọi `GameObject$$GetComponent<object>` (0x125EFA4) với `x1` = nội dung slot `Method$GetComponent<V_Ability_Manager>` tại `*(base+0x25B6768)` (slot được game điền lúc nhặt mana lần đầu; có thể còn null sớm trong trận).
  Chưa xác định V_Ability_Manager và C_Battle_Manager có cùng GameObject không (**CHƯA XÁC ĐỊNH**) nên cách (i) là chắc chắn hơn.
- **Cách lên cấp ngay (M6)**: trong hook `Update` (luồng Unity), khi có yêu cầu "Lên N cấp" và đủ điều kiện (`Time.timeScale > 0`, `Unit_IsLive`, đã có `this`, `Level < ApplyLevelMax` hoặc `LevelAdd < ApplyLevelAddMax`):
  gọi `ManaGain(this, ManaCost - ManaStock + 0.01f)`; hàm sẽ tự trừ EXP, tăng cấp và bật bảng chọn (timeScale=0). Sau khi người chơi chọn (timeScale>0) tiếp tục yêu cầu còn lại. Mỗi `Update` làm tối đa 1 cấp.
  Không ghi thẳng `AllyUnitGlobal_Level` (sẽ bỏ qua bảng chọn và cập nhật thanh mana).
- **orig16 các hàm tham chiếu/hook (mục e, f)** - kiểm trước khi cài hook (đều là prologue chuẩn, không PC-relative trong 16 byte đầu trừ ghi chú):
  | Hàm | RVA | orig16 |
  |---|---|---|
  | `C_Battle_AllyUnit$$Unit_Damage` | 0x1048858 | `ea0f1afc e923016d fe6702a9 f85f03a9` |
  | `C_Battle_AllyUnit$$Unit_Death` | 0x10449F0 | `fe57bea9 f44f01a9 75b30090 54a60090` (có `adrp`) |
  | `C_Battle_AllyUnit$$Unit_RevivalON` | 0x1045070 | `e80f1dfc fe0700f9 f65701a9 f44f02a9` |
  | `C_Battle_Manager$$Update` | 0x109CBF8 | `e80f1afc fe6f01a9 fa6702a9 f85f03a9` |
  | `C_Battle_Manager$$Start` | 0x109E444 | `ffc306d1 ef3b116d ed33126d eb2b136d` |
  | `V_Ability_Manager$$ManaGain` | 0x10FC64C | `e923bb6d fe0b00f9 f85f02a9 f65703a9` |
  | `V_Ability_Manager$$Create_Ability` | 0x10FCD6C | `ffc304d1 ed330a6d eb2b0b6d e9230c6d` |
  | `UnityEngine.Time$$get_timeScale` | 0x219AB34 | `fe4fbfa9 f32800b0 603646f9 a00000b5` (có `adrp`) |
- **Hook thực thi mỗi frame: `C_Battle_Manager$$Update` RVA 0x109CBF8** (private void Update()). Chỉ tồn tại ở cảnh trận (level2). orig16 `e80f1afc fe6f01a9 fa6702a9 f85f03a9` (`str d8,[sp,#-0x60]!; stp x30,x27,[sp,#0x10]; stp x26,x25,[sp,#0x20]; stp x24,x23,[sp,#0x30]`, không PC-relative -> hook Dobby sạch).
  Hook: gọi bản gốc rồi chạy tác vụ menu. **Xác định "đang trong trận"**: Update của `C_Battle_Manager` đang được gọi (đặt `lastUpdateFrame`, coi "không trong trận" nếu quá ~0.5 giây không có Update) AND `Unit_IsLive == true` (AllyUnit static +0x10). Phụ: `C_Battle_Manager.Battle_IsPlay` (static +0x1D1) = đang chạy.
  Không có `Update` trên `C_Battle_AllyUnit` (chỉ `Start`), `A_MainMenuInformation$$Update` (0x1010C18) là của menu chính.

## (g) AndroidManifest, dex, ABI, công cụ cần tải

**Manifest** (`aapt2 dump xmltree`, base.apk): package `com.vkslrzm.Zombie`, versionName `0.9941`, versionCode 612, **minSdk 24, targetSdk 36**, compileSdk 36, `installLocation=preferExternal`,
`requiredSplitTypes=base__abi`, `extractNativeLibs=true`, `isGame=true`, `enableOnBackInvokedCallback=true`, meta-data `com.google.unity.ads.UNITY_VERSION=2022.3.62f3`.
Quyền: INTERNET, ACCESS_NETWORK_STATE, WRITE/READ_EXTERNAL_STORAGE, FOREGROUND_SERVICE(+DATA_SYNC), WAKE_LOCK, `com.android.vending.BILLING`, `com.google.android.gms.permission.AD_ID`, ACCESS_ADSERVICES_*.
- **Activity launcher**: `com.unity3d.player.UnityPlayerActivity` (exported, `launchMode=2` singleTask, `screenOrientation=12`, `hardwareAccelerated=false`, `configChanges=0x40003fff`, categories LAUNCHER + LEANBACK_LAUNCHER). Nằm trong **`classes2.dex`** (class_def trong dex #2; dexdump tại 0x4CFF14 `<init>`, `onCreate` 0x4CFF50).
- **Lớp Application tuỳ biến**: không có (mặc định `android.app.Application`; `appComponentFactory=androidx.core.app.CoreComponentFactory`).
- **UnityPlayerActivity.onCreate (Landroid/os/Bundle;)V** (44 code units): `requestWindowFeature(1)`, `super.onCreate`, `updateUnityCommandLineArguments`, `new UnityPlayer(this,this)`, `mUnityPlayer = v3`, `setContentView(v3)`, `v3.requestFocus()`, `return-void` (registers 4; v0..v3). Không có `<clinit>`.
  Điểm chèn đề xuất (smali, apktool `-r`): ngay trước `return-void` thêm `invoke-static {p0}, Lcom/msmod/Mod;->onCreate(Landroid/app/Activity;)V` (lớp `Mod` trong dex mới `classes3.dex`, có `static { System.loadLibrary("msmod"); }`)
  - như vậy không phải thêm `<clinit>` vào `UnityPlayerActivity`. Nút nổi: `activity.addContentView(view, LayoutParams)` (không cần quyền overlay), chạy trong `runOnUiThread`.
  Lưu ý: Dobby phải đặt hook **sau khi `libil2cpp.so` đã được `dlopen` và IL2CPP khởi tạo xong** (libunity tải nó muộn hơn UnityPlayer constructor): `libmsmod` nên chạy một luồng nền thăm dò `dl_iterate_phdr`/`/proc/self/maps` (10-50 ms) tới khi thấy `libil2cpp.so`, lấy base, tính `base + RVA`, rồi cài hook.
- **Số dex**: 2 (`classes.dex` 8.325.960 B v037 7477 lớp; `classes2.dex` 7.735.928 B 10675 lớp; sha256 classes2 `ef60fd1136c6ff4d877f7b300e53bf919a689a153a16f72107f15db5de69b9ac`). minSdk 24 hỗ trợ multidex gốc => thêm `classes3.dex` (từ `d8`) là đủ, không cần chạm dex gốc nếu chỉ smali-patch 1 lớp; nếu dùng apktool thì lớp `UnityPlayerActivity` bị dịch lại và dex #2 build lại.
- **ABI**: base.apk **không có thư viện native**; split `config.arm64_v8a` chứa `lib/arm64-v8a/{libil2cpp.so (40.523.040 B), libunity.so (15.935.528 B), libmain.so (6.728 B)}`. Chỉ arm64-v8a. `libmsmod.so` đặt trong **split arm64** (hoặc base `lib/arm64-v8a/` + `extractNativeLibs=true`; với bản merged thì `lib/arm64-v8a/`); NEEDED hợp lệ: libc/libdl/liblog/libandroid (libil2cpp/libunity đã NEEDED libc, libm, libdl, liblog, libz...).
  libil2cpp: ELF arm64, segment căn 0x4000 (16 KB), có `.rela.dyn` RELATIVE (không có symbol IL2CPP nội bộ; dùng RVA). Hàm `il2cpp_*` xuất khẩu có thể dùng nếu cần, nhưng đề xuất chỉ dùng RVA + slot TypeInfo ở trên.
- **Cần tải cho T14**:
  | Gói | Nguồn (đã thử từ máy này) | Ghi chú |
  |---|---|---|
  | NDK **r27c** (27.2.12479018) | `https://dl.google.com/android/repository/android-ndk-r27c-linux.zip` (HTTP 200, 663.987.688 B); phương án: r28b `.../android-ndk-r28b-linux.zip` 722.264.879 B | dùng `-DANDROID_ABI=arm64-v8a -DANDROID_PLATFORM=android-24`, thêm `-Wl,-z,max-page-size=16384` với r27 (r28+ mặc định 16 KB) |
  | **Dobby** | `git clone https://github.com/jmpews/Dobby` (**clone được**; ZIP/API/releases bị chặn 403; `raw.githubusercontent.com` và `cdn.jsdelivr.net/gh/jmpews/Dobby@<sha>` đọc được từng file) | commit đã thử `5dfc8546954ce3b3198132ab13fddb89ee92cdd7` (2024-03-14, master). Build tĩnh arm64 bằng CMake NDK (`-DDOBBY_GENERATE_SHARED=OFF`), liên kết vào `libmsmod.so` |
  | **android.jar** | `https://dl.google.com/android/repository/platform-36_r02.zip` (65.878.410 B, HTTP 200) -> `android-36/android.jar`; hoặc `platform-35_r02.zip` (64.273.788 B) | để biên dịch Java menu bằng javac (target 8/11) |
  | **d8** | **đã có** `work/tools/android-15/d8` + `lib/d8.jar` (build-tools r35.0.0) | `d8 --release --min-api 24 --output work/mod X.jar` |
  | JDK (javac) | kiểm tra `javac -version` ở T14 | `java` có sẵn (APKEditor/apktool chạy được) |
  | dexdump, aapt2, zipalign, apksigner | đã có trong `work/tools/android-15` | |
  | apktool 2.12.0 / APKEditor 1.4.9 | đã có | theo M5 dùng `apktool d -r` (không decode resource) |
  Công cụ tạo lệnh ARM: capstone có sẵn (chỉ để kiểm); đề xuất tự mã hoá `b`/`cbz`/`nop`/`ret` bằng `struct` (như `work/.../patchtab.py` của T12, nhúng sang `tools/disasm_patch.py` ở T13) thay vì thêm keystone.

---------------------------------------------------------------------------------------------------

## Điểm CHƯA XÁC ĐỊNH (tổng hợp)

1. Có hiển thị chữ "인터넷 연결 실패" của `Show_CloudResult(-1)` lúc khởi động offline hay không (B2 loại bỏ nên không cần biết).
2. `productId` của IAP_01, 03-06 và tên file save chứa `IAP_DestroyAD` (không cần cho mod).
3. Trạng thái nào trong `C_Battle_Manager` (IsGamePaused/IsStopGame/Battle_IsPlay) thay đổi khi mở bảng chọn kỹ năng (đề xuất dùng `Time.timeScale == 0`).
4. `V_Ability_Manager` và `C_Battle_Manager` có chung GameObject hay không (dùng hook `ManaGain` để lấy `this`).
5. UGS Core / `UnityServicesInitializer` có làm chậm hoặc treo khi offline hay không (chưa thấy mã game chờ nó).
6. Hành vi runtime thật (chưa chạy thử; không có `/dev/kvm`/thiết bị): toàn bộ đề xuất vá dựa trên disasm tĩnh. Kiểm bằng thử máy thật ở T15/T16.

---------------------------------------------------------------------------------------------------

## T13 kiểm chứng (điều kiện quản đốc)

Công cụ: `tools/patch_so.py` (áp/kiểm/trả), `mods/patches.json` (22 điểm vá), `tools/disasm_patch.py` -> `docs/patch_disasm.txt` (trước/sau + tự kiểm, 0 lỗi).
Kết quả chạy: áp lên `libil2cpp.so` gốc (sha256 `61546e52...e9b326`) -> `work/mod/libil2cpp.so` sha256 `2e4dd130c472ab58388b8498d3221ee8600f4097a493c0ec75d04b1844040e0e`
(22 điểm, 40.523.040 B, đúng kích thước gốc); `--check` bản vá OK 22/22; `--check` bản gốc với `--expect orig` OK 22/22; `--revert` ra sha256 trùng gốc;
byte gốc sai -> thoát mã 2, không ghi file. Quy đổi RVA->offset qua PT_LOAD có quyền X (mọi điểm: off = RVA - 0x4000).
Danh sách vá có đổi so với bảng T12: **bỏ A2** (hai `W_AdMobAD_*.Start`), vì dư thừa (xem 1.c); còn lại giữ nguyên.

### 1. `W_GDPR_Manager$$Start` có điều khiển flow game không? -> KHÔNG, nên `ret` cả hàm là an toàn

a) Toàn hàm `Start` (0x113FFCC..0x1140188, đã disasm) chỉ làm: khởi tạo metadata IL2CPP (cờ 0x26b1397), `IsFirstStart` (static +1) = 1 nếu chưa (0x1140058-0x114007C),
`MobileAds.RaiseAdEventsOnUnityMainThread = true` (0x11400D4), `IsGDPR_ON = 0` (0x11400E0), 2 lần `Debug.Log`, rồi tail `ConsentInformation.Update(new ConsentRequestParameters(), new Action<FormError>(OnConsentInfoUpdated))` (0x1140184).
Các hàm nó gọi: `OnConsentInfoUpdated` (0x1140188): chỉ `Debug.Log` + tail `ConsentForm.LoadAndShowConsentFormIfRequired(cb)` (form UMP native của Google, không phải UI/scene của game);
callback `<OnConsentInfoUpdated>b__0` (0x1140330, 0x3F4 B): `Debug.Log`, `Boolean.ToString`, `String.Concat`, `W_GDPR_Setting$$CanAdShow` / `CanShowPersonalizedAds` (đọc PlayerPrefs IABTCF), `ConsentInformation.CanRequestAds`,
ghi `W_GDPR_Manager.isNoAd` (this+0x20) và `IsGDPR_ON` (0x11406D0 / 0x1140708). **Không có `SceneManager.LoadScene`, `SetActive`, `StartCoroutine`, `Time.timeScale`, khởi tạo nào khác** (danh sách `bl` trong 3 hàm: Debug.Log, String.Concat/ToString, W_GDPR_Setting.*, ConsentInformation/ConsentForm/ConsentRequestParameters ctor, Action ctor, il2cpp class-init stub 0xf158e4/0xf159ec/0xf15b00).
b) Ai đọc kết quả của nó (quét toàn `.text` các tham chiếu `W_GDPR_Manager_TypeInfo`, slot 0x250F040): chỉ `W_AdMobAD_Reward$$Start`, `W_AdMobAD_Interstitial$$Start` (đọc `IsGDPR_ON`, static +0 : `ldrb w8,[x8]; cbz w8,<ret>`, 0x113F110-0x113F114 và 0x113DF60-0x113DF64) và chính các hàm của `W_GDPR_Manager`. `IsFirstStart` chỉ `Start` đọc. `isNoAd` không ai đọc (field private, 3 hàm của lớp không đọc nó). `W_GDPR_Setting` chỉ được `b__0` dùng (các hàm khác của nó chỉ gọi nhau). Không có UnityEvent trong scene trỏ tới `W_GDPR_*` (UnityPy level0/1/2: không có `m_MethodName` nào của lớp này).
c) **Hai `W_AdMobAD_*.Start` thoát sớm an toàn khi `IsGDPR_ON == 0`**: sau khi `W_GDPR_Manager.Start` bị vá, `IsGDPR_ON` giữ giá trị mặc định 0 (static của lớp không có `.cctor`; IL2CPP zero-init), nên cả hai `Start` chạy `cbz -> ldp...; ret` (0x113F1E0-0x113F1EC / 0x113E030-0x113E03C) mà không gọi `MobileAds.Initialize` hay `LoadAd`. Đây cũng chính là đường game tự đi khi người dùng từ chối đồng ý/UMP lỗi, nên là đường có sẵn trong mã gốc. => **không cần vá riêng `W_AdMobAD_*.Start`** (đã bỏ A2).
Hậu quả: `_rewardedAd` (Reward +0x20) và `_interstitialAd` (Interstitial +0x20) luôn null. Mọi chỗ đọc hai field này (đã disasm toàn bộ method của hai lớp):
 - `ShowRewardedAd` 0x113F3BC: `ldr x0,[x19,#0x20]; cbz x0,<LogError>` (0x113F41C-0x113F424) - **được vá A3 bỏ qua hẳn**; không còn ai dùng. `ShowInterstitial` 0x113E1F0: `cbz x0,<StartCoroutine Play_NoAdField>` (0x113E244-0x113E24C) - bản gốc đã null-safe và được vá A4 rẽ sang `Button_Scene_Moving`.
 - `DestroyAd` (Reward 0x113F348-0x113F380, Interstitial 0x113E198-0x113E1D0), `LogResponseInfo` (Reward 0x113F500, Interstitial 0x113E30C), `LoadAd` (0x113F264 / 0x113E0B4): đều `ldr ...,[this,#0x20]; cbz` trước khi deref. `LoadAd`/`RegisterEventHandlers`/`<LoadAd>b__*` chỉ được gọi từ `Start`(đã thoát) và callback của chính SDK (không bao giờ chạy). Không lớp ngoài nào đọc (private); `scan.json`: lời gọi tới hai lớp từ code game chỉ có `ShowInterstitial` (2 chỗ) và tail `ShowRewardedAd`; UnityEvent scene chỉ gọi `Button_Scene_Moving` của Interstitial (an toàn, không đụng field).
d) Kết luận: `ret` ở entry (lệnh gốc `str x30,[sp,#-0x30]!` -> chưa có frame) là an toàn. Phần "flow" duy nhất (hiện form đồng ý) đúng là thứ cần bỏ.

### 2. B2 `b 0x1171538` tại 0x1171434 (`Z_GPGSManager$$LoadLocalData` đuôi)
Disasm 0x1171378..0x11715C8 xem `docs/patch_disasm.txt` (mục B2) và đoạn dưới.
- 0x1171434 là **lệnh đầu của "đuôi mạng"** sau khi toàn bộ phần đọc/giải mã save cục bộ đã xong: nó được tới từ 0x116FC7C/0x116FC98 (không có file autosave), 0x1171258, 0x1171378 (hết vòng đọc), 0x117141C (hết vòng cuối) và rơi xuống từ 0x1171430. Từ 0x1171434 đến epilogue chỉ có: `Application.internetReachability`; có mạng -> `Social.localUser` + `new Action<bool>(<LoadLocalData>b__115_0)` + gọi `ILocalUser.Authenticate` (interface call 0x1171490-0x1171534); không mạng -> `ObscuredInt(15)` ghi `Z_Database.Game_CloudID` (static +0x808/+0x810) rồi `Show_CloudResult(-1)`. **Không có lệnh `Save*`, `File.Write*`, hay ghi trường save nào khác.**
- Callback `<LoadLocalData>b__115_0` (0x1174628) chỉ đặt `this.StartCloudLoadID(+0x24C)=1`, `Game_CloudID=15` rồi tail `LoadFromCloud_Start`. Đọc `StartCloudLoadID`: duy nhất `OnSavedGameDataRead` (0x115F6DC, luồng cloud); đọc `Game_CloudID`: duy nhất `Show_CloudResult` (0x115BF28, so với 15 = "đang ở bước khởi động": chỉ reset về 0 và ghi chữ vào `Text_GameState`). Bỏ qua = trạng thái cloud không bao giờ khởi tạo, các nút cloud đã `ret` => không ai chạm.
- Stack/epilogue: hàm có **đúng một** `sub sp,sp,#0xC0` (0x11621F0) và **một** `add sp,sp,#0xC0` (0x1171558), không có thay đổi sp nào khác trong 0xF3D8 B (tự kiểm trong disasm_patch). Prologue lưu d10@0x40, d9/d8@0x50, x29/x30@0x60, x28/x27@0x70, x26/x25@0x80, x24/x23@0x90, x22/x21@0xA0, x20/x19@0xB0; epilogue 0x1171538-0x117155C khôi phục đúng các ô đó (`ldp x20,x19,[sp,#0xb0]` ... `ldr d10,[sp,#0x40]; add sp,sp,#0xc0; ret`) và là epilogue dùng chung của nhánh Authenticate (0x1171518 `b 0x1171538`). Mọi thanh ghi callee-saved (kể cả x29 mà hàm dùng như thanh ghi thường) được nạp từ ô lưu, nên nhảy từ giữa hàm là đúng ABI; hàm `void` nên không cần x0.
- Giá trị `w19` (cờ `[static+0x83b]`) tại 0x1171430 rơi sang 0x1171434 khi = 0: không có tác dụng phụ ở nhánh bị bỏ.

### 3. Mọi vá `ret`/`nop`/`b` thoát: kiểu trả về và trạng thái chờ
Chữ ký lấy từ `dump.cs` (khớp RVA). `ret` đặt tại **entry hàm** (lệnh gốc là lệnh đẩy frame, đã tự kiểm) nên x30/sp nguyên vẹn.
| Vá | Chữ ký | Ai gọi / ai chờ | Kết luận |
|---|---|---|---|
| A1 `W_GDPR_Manager$$Start` | `private void Start()` | Unity message | void, không ai chờ |
| B1 `Z_Database$$AuthenticateUser` | `private void` | chỉ `Z_Database$$Awake` (`bl` 0x114BD04, bỏ qua kết quả); callback gốc chỉ `Debug.Log` | void, không ai chờ đăng nhập |
| B3 7 hàm `Z_GPGSManager$$Button_Cloud*`, `OnLogin` | `public void` | chỉ UnityEvent scene (không có `bl` nào trong code game; scan.json) | void; UnityEvent bỏ qua kết quả; không coroutine |
| C1 `BaseIAPButton$$Start/OnEnable/OnDisable/PurchaseProduct/Restore` | `private void` | Unity message / onClick (onClick chưa được gắn vì `Start` ret) | void |
| C1 `CodelessIAPStoreListener$$InitializeCodelessPurchasingOnLoad`, `...CreateCodelessIAPStoreListenerInstance` | `private static void` | `[RuntimeInitializeOnLoadMethod]` / nội bộ | void; không `async`/coroutine (không phải iterator) |
| A3 `ShowRewardedAd` (thay 2 lệnh) | `public void` | tail-call từ `Button_RevivalSelect` (0x1044E68) | tail `b` tới callback: x0=this, lr=caller; callback trả void |
| A4 `ShowInterstitial` (thay 1 lệnh) | `public void` | `Button_SceneMove` (0x10BC108), `Fade_RewardClass.MoveNext` (0x10EF67C): gọi rồi đi tiếp | tail `b` tới `Button_Scene_Moving` (void) |
Code game không gọi trực tiếp bất kỳ hàm `UnityEngine.Purchasing` nào (scan.json: 0 lời gọi) và không có hàm nào trả bool/obj bị vá (nên không cần `mov w0/x0`). Không IAP_DestroyAD, không vá bất tử/lên cấp (T14 dùng hook).
Phần chưa kiểm được ngoài tĩnh: hành vi chạy thật (xem mục 6 ở "Điểm CHƯA XÁC ĐỊNH").

---------------------------------------------------------------------------------------------------

## T14 - libmsmod.so + menu Java (kết quả build và kiểm chứng)

Build: `tools/build_mod.sh [native|java|all]` (tự tải toolchain vào `work/tools` nếu thiếu) -> `work/mod/libmsmod.so` (236.776 B, đã strip) và `work/mod/classes_mod.dex` (10.368 B, DEX 037, min-api 24).
Toolchain: NDK r27c (27.2.12479018), `-DANDROID_PLATFORM=android-24`, `-Wl,-z,max-page-size=16384`; android.jar = platform-36_r02; javac 21 `--release 8`; d8 build-tools r35 `--release --min-api 24`.
**Dobby**: dùng commit `e9fe7fb` (2023-04-21), KHÔNG dùng master `5dfc854` (đã thử: master đang dở refactor, `ProcessRuntime.cc`/`os_arch_features.h` không biên dịch được). Build tĩnh `dobby_static`, tắt `Plugin.SymbolResolver/ImportTableReplace/Android.BionicLinkerUtil`, `DOBBY_DEBUG=OFF`;
một sửa nhỏ do script áp (sed): `logging.h` định nghĩa `Logger::Shared()` trong header thiếu `inline` => trùng ký hiệu khi link static.
Kiểm: `readelf -d` NEEDED = `liblog.so libm.so libdl.so libc.so` (không libc++_shared, không libandroid); LOAD căn 0x4000; `GNU_STACK` RW (noexecstack); không TEXTREL.
`dexdump`: lớp `Lcom/msmod/Mod;` (+ `Mod$Ui`, `Mod$Ui$1..4`, `Mod$Ui$DragListener`, `Mod$1`).

### Chữ ký JNI (build_mod.sh tự kiểm: native trong dex == ký hiệu xuất trong .so)
| Java (`com.msmod.Mod`, static native) | Ký hiệu C |
|---|---|
| `void setGodMode(boolean)` | `Java_com_msmod_Mod_setGodMode(JNIEnv*, jclass, jboolean)` |
| `void requestLevelUp(int)` | `Java_com_msmod_Mod_requestLevelUp(JNIEnv*, jclass, jint)` |
| `boolean isInBattle()` | `Java_com_msmod_Mod_isInBattle(JNIEnv*, jclass)` (Update chạy trong <1 s và Unit_IsLive) |
| `int getPendingLevelUps()` (thêm) | `Java_com_msmod_Mod_getPendingLevelUps` |
| `boolean isReady()` (thêm) | `Java_com_msmod_Mod_isReady` (đã cài xong cả 5 hook) |
Java: `Mod.onCreate(Activity)` (public static) = `System.loadLibrary("msmod")` + dựng nút nổi "MOD" (kéo được, chạm để mở bảng) trên UI thread bằng `addContentView`; bảng: Switch "Bất tử", nút "Lên 1 cấp", "Lên 5 cấp" (mờ + vô hiệu khi `!isInBattle()`, làm mới mỗi 500 ms bằng `Handler` chỉ khi bảng đang mở), "Đóng". Không cần quyền. T15 chèn `invoke-static {p0}, Lcom/msmod/Mod;->onCreate(Landroid/app/Activity;)V` trước `return-void` của `UnityPlayerActivity.onCreate` như mục (g).

### Hook (mods/native/msmod.c, offsets.h)
| Hook | RVA | Chữ ký (kèm MethodInfo* cuối) | Hành vi |
|---|---|---|---|
| `C_Battle_AllyUnit$$Unit_Damage` | 0x1048858 | `void(this, float, float, int32, MethodInfo*)` | bất tử bật -> return, ngược lại gọi gốc |
| `C_Battle_AllyUnit$$Unit_Death` | 0x10449F0 | `void(this, MethodInfo*)` | bất tử bật -> return |
| `C_Battle_Manager$$Update` | 0x109CBF8 | `void(this, MethodInfo*)` | gọi gốc; ghi mốc thời gian + `Unit_IsLive`; bất tử: `Unit_Hp=UnitApply_HpMax` nếu thấp hơn; lên cấp (xem dưới) |
| `V_Ability_Manager$$ManaGain` | 0x10FC64C | `void(this, float, MethodInfo*)` | lưu `this` rồi gọi gốc |
| `C_Battle_Manager$$Start` (thêm) | 0x109E444 | `void(this, MethodInfo*)` | xoá `this` V_Ability_Manager cũ + bộ đếm lên cấp khi vào trận mới (tránh dùng con trỏ đã bị huỷ), rồi gọi gốc |
Trước khi hook, `installer` so 16 byte đầu mỗi hàm với giá trị trong `msmod.c` (không khớp -> không hook, ghi log `msmod`); `tools/check_hooks.py` (chạy trong build) đã so các giá trị này với `libil2cpp.so` gốc: KHỚP cả 5.
**Dobby relocate 16 byte đầu**: 4/5 hàm không có lệnh PC-relative trong 16 byte đầu. `Unit_Death` có 2 `adrp` (x21, x20): `InstructionRelocationARM64.cc` (`inst_is_adrp`, dòng ~223) dịch `adrp Rd, page` thành `Mov Xd, <địa chỉ trang tuyệt đối tính theo địa chỉ nguồn>` nên relocate đúng; `b/bl/ldr literal/adr/b.cond/cbz/tbz` cũng được xử lý (không có trong 16 byte đầu của 5 hàm).
Không có nhánh nào trong thân hàm nhảy vào 20 byte đầu của 5 hàm (check_hooks) nên trampoline (4 byte near-branch hoặc 16 byte `ldr x17; br x17`) không cắt ngang đích nhảy. Chưa chạy trên thiết bị (không có arm64/KVM) - CHƯA XÁC ĐỊNH ở runtime.
**Truy cập static**: `klass = *(base + slot)`, `static_fields = *(klass + 0xB8)` (đúng mẫu `ldr x0,[slot]; ldr x8,[x0,#0xb8]` của game). Phát hiện khi viết: **slot chưa phải con trỏ cho tới khi game chạy stub khởi tạo metadata (`bl 0xf158e4` với x0 = địa chỉ slot, đầu mỗi hàm, cờ `tbnz` lần đầu) cho lớp đó** - giá trị khởi tạo trong file là token mã hoá nhỏ (vd slot `C_Battle_AllyUnit_TypeInfo` 0x259EE58 = 0x200047ED; `Z_Database_TypeInfo` 0x25A4B48 = 0x200089C9, đọc thẳng từ file) chứ KHÔNG phải 0. Vì vậy code chỉ coi là con trỏ khi `>= 2^32` và căn 8, sau đó kiểm `static_fields` tương tự (nếu không -> bỏ qua frame đó). Offsets/field (bảng ở mục e/f) đã chép vào `offsets.h` kèm RVA/dòng dump.cs nguồn.
**Lên cấp**: `pending` (atomic, tối đa 99) do Java đặt; trong Update (luồng Unity) khi `pending>0`, `Unit_IsLive`, có `this` V_Ability_Manager, `Time.timeScale > 0.01` (gọi `get_timeScale` 0x219AB34 với MethodInfo=NULL) và còn cấp để lên (`Level<ApplyLevelMax || LevelAdd<ApplyLevelAddMax`) thì gọi **bản gốc** `ManaGain(this, max(ManaCost-ManaStock,0)+0.01f)`; giảm `pending` khi `Level+LevelAdd` tăng; tối đa 1 cấp/frame; sau 120 frame liên tiếp không lên được (vd bảng artifact đang mở) thì huỷ yêu cầu. Bảng chọn kỹ năng của game tự hiện (timeScale=0) nên frame sau tự chờ.
Khác với đề bài (có chủ đích): điều kiện là `timeScale > 0.01` chứ không `== 1` (người chơi có thể bật tốc độ x2 - khi đó `==1` sẽ không bao giờ thoả); thêm hook `C_Battle_Manager$$Start` (xoá con trỏ cũ) và 2 JNI phụ `getPendingLevelUps/isReady` để menu hiện trạng thái.
Không cấp phát/khoá trong hook: chỉ `atomic_*`, `clock_gettime` (vDSO) và đọc bộ nhớ; `static int fails` chỉ luồng Unity đụng.
Hạn chế đã biết: (1) `this` của V_Ability_Manager chỉ có sau lần `ManaGain` đầu tiên của trận (nhặt viên EXP đầu tiên, hoặc kẻ địch chết) - bấm "Lên cấp" trước đó thì yêu cầu được giữ và thực hiện ngay khi có `this` (menu hiện "Đang chờ lên cấp: N"); (2) hook cài từ luồng nền ~ngay khi `libil2cpp.so` xuất hiện trong `dl_iterate_phdr` (thăm dò 20 ms, tối đa 120 s) - nếu hook bị cài đúng lúc luồng khác đang chạy chính hàm đó (không xảy ra ở giai đoạn khởi động vì `Update` chỉ chạy ở cảnh trận) thì có race, chấp nhận; (3) `Unit_Death` bị chặn chỉ khi bất tử bật; coroutine `Play_Unit_Death` đã bắt đầu trước đó vẫn chạy; (4) SELinux/W^X: Dobby `mprotect` RWX trang `.text` của libil2cpp (extractNativeLibs=true, file riêng tư) - cách phổ biến, chưa thử thật.
