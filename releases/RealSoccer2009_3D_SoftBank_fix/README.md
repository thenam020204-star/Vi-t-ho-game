# Real Soccer 2009 3D (体験版) — SoftBank/Panasonic — bản sửa lỗi khởi động

Gốc: `RS20093D_Panasonic_DEMO_3G2_WS_JP_IGP_SOFTBANK_104.jar` (Gameloft, v1.0.4, CLDC-1.1/MIDP-2.0, OPGL).

## Lỗi

```
ExceptionInInitializerError at au.<clinit> ← GloftRS93.startApp
NullPointerException: String.trim() on null at ai.<clinit>
```

Bytecode `ai.<clinit>` (offset 2095–2106):

```
getstatic GloftRS93.a
ldc "MIDlet-FullVersion-URL"
invokevirtual MIDlet.getAppProperty
invokevirtual String.trim        <-- null
putstatic ai.a:String
```

Thuộc tính `MIDlet-FullVersion-URL` chỉ có trong file JAD gốc của nhà mạng; manifest trong JAR không có.
Cài chỉ bằng JAR → `getAppProperty` trả `null` → NPE. Trên máy thật cũng lỗi y hệt nếu thiếu JAD.

`ai.a` chỉ được dùng ở `af.a()` → `platformRequest(ai.a)` (nút "mua bản đầy đủ"), bọc trong `try/catch Exception`.

## Cách sửa (chỉ tài nguyên, không đụng bytecode)

- Thêm `MIDlet-FullVersion-URL: http://www.gameloft.com` vào `META-INF/MANIFEST.MF` (URL gốc không rõ, đây là giá trị thay thế; chỉ ảnh hưởng nút mua bản đầy đủ).
- Manifest đặt làm entry đầu tiên của JAR. Mọi entry khác giữ nguyên nội dung (đã so md5: chỉ `META-INF/MANIFEST.MF` khác; không thêm/xoá entry).
- Class gốc đã preverify sẵn, không biên dịch lại → binary/StackMap giữ nguyên.
- Kèm JAD (`MIDlet-Jar-Size` = kích thước thật).

Script tái tạo: `python3 fixmf.py <jar_gốc> <jar_mới>`.

## Lỗi 2: không nhận phím (J2ME Loader)

`au` (GameCanvas) đọc mã phím từ 7 field static gán trong `au.<clinit>` (offset 7553–7584), chỉ gán ở đó;
`keyPressed/keyReleased/c(int)` là nơi duy nhất đọc chúng, sau đó mọi logic dùng bitmask `cC`.

Ý nghĩa bit lấy từ logic game (không phải bố cục phím vật lý):
- `ai.a(1, 16, J, n, …)` (điều hướng menu): bit 1 → `J−1` = **lên**, bit 16 → `J+1` = **xuống**;
- `ai.java:4605`: bit 64 → `−1` = **trái**, bit 4 → `+1` = **phải**.

Game vẽ 800×480 (`ah.b = 800; ah.c = 480`) — máy gốc chạy ngang nên mã phím SoftBank bị xoay 90°.

| Field | Bit | Ý nghĩa | Gốc (SoftBank) | Mới (Nokia/J2ME Loader) |
|---|---|---|---|---|
| cF | 1 | lên | −5 | −1 |
| cG | 16 | xuống | −2 | −2 |
| cH | 64 | trái | −1 | −3 |
| cI | 4 | phải | −6 | −4 |
| cJ | 256/524288 | OK | −20 | −5 |
| cK | 524288 | soft trái | −21 | −6 |
| cL | 8192 | soft phải | −22 | −7 |

Phím số 0–9, `*`, `#` không đổi. `cH` gốc gán bằng `iconst_m1` (1 byte) nên `patchkeys.py` đổi đích `putstatic`
(`iconst_m1 → cF`, `bipush -3 → cH`) để giữ nguyên độ dài; khối lệnh này không có nhánh, StackMap không đổi.
(Bản vá phím trước đó — commit d0073ac — gán lệch 90°: lên/xuống thành phải/trái trong menu. Đã thay.)

⚠️ Bản này dành cho J2ME Loader / máy dùng mã phím Nokia, màn hình **800×480**. Máy SoftBank thật: dùng JAR gốc + JAD gốc.

## Lỗi 4: phím số / soft key lộn xộn (`patchkeys2.py`)

Bảng `au.c(int)` của bản SoftBank (máy cầm ngang) gán 0/1/2/3/4/7/* và soft trái = 524288 — bit này **không nơi nào
kiểm tra** (chỉ là “phím bất kỳ”) — còn 6→1024, 8→512, 9→2048, #→4096, soft phải→8192; OK giữa trong trận cũng 524288.
Ý nghĩa bit trong trận (`bh.java` ~3700): 256 chuyền (`ax==5`), 512 chuyền dài (`ax==2`), 1024 sút (`ax==3`, `h(9216·lực)`),
2048 động tác giả/kỹ thuật (`ax==6`); 4096 quay lại, 8192 / 8448 chọn.
Chữ hướng dẫn trong game: 384「方向ｷｰ|または|1/2/3/4/6/7/8/9ｷｰで|移動」, 386「5ｷｰで|ﾊﾟｽ」, 388「0ｷｰで|ｼｭｰﾄ」, 389「#ｷｰで|ﾛﾝｸﾞﾊﾟｽ」.

| Phím | Bit | Tác dụng |
|---|---|---|
| 1 2 3 / 4 6 / 7 8 9, D-pad | 128 1 2 / 64 4 / 32 16 8 | 8 hướng |
| 5, OK giữa | 256 | chuyền / chọn |
| 0 | 1024 | sút |
| * | 2048 | động tác giả |
| # | 512 | chuyền dài |
| soft trái | 8192 | 選択 |
| soft phải | 4096 | 戻る |

Cách vá (giữ độ dài lệnh, chỉ nhảy tới offset đã có frame StackMap — script tự kiểm tra):
- viết lại 12 entry `tableswitch` (35..57) và hằng trong các slot `return` (`ldc 524288` → `bipush`, `sipush` → `sipush`);
- phím 5 trỏ tới nhánh có sẵn offset 153 (→ 256, giống OK);
- OK giữa: toán hạng `bipush 8` (so `ac == 8`, đang trong trận) → 127, nên luôn ra 256;
- nhánh `cK`: `ldc; ireturn` → `goto 327` (slot `sipush 4096`); `<clinit>`: `cK = −7`, `cL = −6`.

Đo trên FreeJ2ME (`keys_layout.txt`, bitmask `au.cC` khi giữ phím) — khớp bảng trên 19/19 phím.
`into_match.txt` → `freej2me_into_match.png`: menu bằng 8/2/5, soft phải quay lại, vào tới trận (`ac = 8`, đồng hồ chạy),
giữ 6 → `aC = 4`, 0 → 1024, `bE = 0` (không kẹt). Hình 3D vỡ là do stub OPGL của harness.

## Mở giới hạn bản demo (`patchdemo.py`, `addkits.py`)

| Giới hạn | Vị trí | Cách mở |
|---|---|---|
| 5 lượt chơi (`au.a`, lưu RMS) | `au.run` offset 1293, 1604: `a = a − 1` | `iconst_1 → iconst_0` (không trừ) |
| Hết lượt → màn 14 “mua bản đầy đủ” | `ai.t` offset 956: `getstatic au.a; ifgt` | `iconst_1; nop; nop` (save cũ đã về 0 vẫn chơi được) |
| Con trỏ chọn đội chỉ qua lại ô 1↔2, đội 2 bị ép | `ai.a()Z` | 2 × `iconst_0 → iconst_1` (luôn cho di chuyển) |
| Ổ khoá vẽ trên mọi ô trừ Ba Lan/Anh | `ai.f(I)` offset 5024–5041 | điều kiện “bỏ qua” luôn đúng (`iload → iconst`, cùng độ dài) |
| JAR demo chỉ có áo `tex/2.t`, `tex/3.t` (+199) → NPE khi nạp đội khác, game kẹt | `au.a(IZZ)` nạp `/tex/<id+1>.t` | **thêm 196 file** `tex/N.t` (chép áo Ba Lan/Anh xen kẽ) cho 198 đội |

Đo (FreeJ2ME, `team_b_match.txt`): chọn Tây Ban Nha + Bắc Ireland (trang ヨーロッパB) → vào trận `ac = 8`, `bE = 0`,
`au.a` giữ 5; tập exception giống hệt bản trước. JAR tăng 1,59 → 3,19 MB.

⚠️ Không có trong JAR demo (không thể “mở khoá”, phải dựng lại):
- áo thật của 196 đội (đang dùng áo Ba Lan/Anh), cờ của các trang ngoài ヨーロッパA (ô trống);
- handler chọn của **ゲームモード** (màn 137: カップ/リーグ/PK戦/練習) và chỉnh **設定** (màn 102) — demo xoá code, chỉ còn
  lên/xuống/quay lại; màn đích 129/131/138/110 vẫn còn;
- chữ kết quả trận `au.a[640 + loại_trận]` cho loại ≠ exhibition: bảng chữ demo chỉ có 641 chữ (0…640).

## Lỗi 3: đơ ở menu chính

`au.run()` bọc cả tick trong `try { … bE = 0; } catch (Exception e) {}`. Nếu tick ném exception, `bE` kẹt ở 1 → khối
input/logic không bao giờ chạy lại (vẫn vẽ, nên trông như “đơ, không bấm được gì”).

Exception thật (bắt bằng `-Xlog:exceptions`): `ArrayIndexOutOfBoundsException: 5` trong `ai.a(IIII[IZZ)V` ← `ai.f(101)` (vẽ menu chính).
- IGP (`bg`, quảng cáo Gameloft) chỉ bật khi JAD nhà mạng có `URL-TEMPLATE-GAME`, `IGP-PROMOS`, `IGP-CATEGORIES`…
  Cài bằng JAR → `bg.a() == false` → `ai.H = 7 − 1 = 6` mục, dùng bảng `ai.u`/`ai.v`.
- Nhưng `u = {1,4,5,6,7}`, `v = {27,38,33,35,36}` chỉ có 5 phần tử (bảng của bản đầy đủ, không có mục “mua”),
  trong khi code demo coi mục 0 là “完全版購入” (`t()`: `J == 0 → af.a()` mở link mua).
- Sửa (`patchmenu.py`, chỉ `ai.<clinit>`): `u = {0,1,4,5,6,7}`, `v = {628,629,38,33,35,36}` = `s`/`t` bỏ mục IGP
  (2225 / chữ 34 「ｹﾞｰﾑを|ｹﾞｯﾄ!」). Chữ: 628「完全版購入」, 629「ｴｷｼﾋﾞｼﾞｮﾝ」, 38「ｹﾞｰﾑﾓｰﾄﾞ」, 33「ｵﾌﾟｼｮﾝ」, 35「製品情報」, 36「終了」.
  `<clinit>` không có nhánh/bảng exception/thuộc tính con → chỉ cập nhật `code_length`/`attribute_length` (+13 byte).

## Kiểm tra đã làm / còn lại

- ✅ Diff entry với JAR gốc: chỉ `META-INF/MANIFEST.MF`, `au.class`, `ai.class` khác; không thêm/xoá entry.
- ✅ `javap` trước/sau: `au` khác ở 7 lệnh trong `<clinit>` và trong `c(I)I` (tableswitch + slot return + 2 lệnh); `ai` chỉ khác khối `u`/`v`.
- ✅ ASM Verify (`--require-stackmap`, lib = FreeJ2ME + JDK java.base + stub OPGL): kết quả **giống hệt** JAR gốc
  (75 class, không tham chiếu thiếu, đủ StackMap; 8 lỗi nội bộ công cụ ở 8 method không bị sửa, có cả trên bản gốc).
- ✅ FreeJ2ME 800×480: hỏi âm thanh → tiêu đề → menu 6 mục → lên/xuống đổi mục (`ai.J` 0→2→1) → OK vào chọn đội;
  `au.bE` về 0 (vòng lặp không kẹt), không còn exception trong code game.
- ✅ Đối chiếu mọi lời gọi `com.mexa.opgl.*` / `com.jblend.media.smaf.phrase.*` với J2ME Loader: đều có (SMAF là stub → có thể im).
- ⚠️ Phần 3D (trận đấu) chưa test được: FreeJ2ME không có OPGL. Chưa thử máy thật.
- ⚠️ Mục 「完全版購入」 gọi `platformRequest(MIDlet-FullVersion-URL)` rồi **thoát game** (hành vi gốc của bản demo).

## Test FreeJ2ME headless (`test/`)

`FJ=<freej2me_plus.jar> test/run.sh <jar> <script> <outdir>` (`SCREEN_W`/`SCREEN_H` mặc định 800×480, `JOPTS` thêm tuỳ chọn JVM,
ví dụ `-Xlog:exceptions=info:file=exc.log`). Dùng `freej2me_plus.jar` trong `Tool_Viet_Hoa_NAM_P.zip`, bảng mã Shift_JIS.
FreeJ2ME không có `com.mexa.opgl` → `test/stub/` là stub no-op, chỉ chèn vào **bản sao test** của JAR.

Lệnh kịch bản: `wait ms`, `press k [giữ]`, `down/up k`, `rawdown/rawup k` (gọi thẳng `Canvas.keyPressed`), `shot tên`,
`probe Lớp field`, `strs Lớp field i…` (in phần tử mảng, kể cả chuỗi game `t`), `threads`.

- `menu_nav.txt` → `freej2me_menu_fixed.png`: menu chính 6 mục, đi xuống/lên, vào chọn đội.
- `keys_raw.txt`, `menu_ok.txt`, `freej2me_keys_before_after.png`: test của bản vá phím trước (480×800, đã thay).
