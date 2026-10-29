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
Logic ghép phím chéo cho thấy vòng hướng `cF(bit1) – cI(bit4) – cG(bit16) – cH(bit64)` → cặp đối diện
`cF↔cG`, `cI↔cH`, khớp bố cục phím Sharp/SoftBank:

| Field | Ý nghĩa | Gốc (SoftBank) | Mới (Nokia/J2ME Loader) |
|---|---|---|---|
| cH | lên | −1 | −1 (giữ) |
| cI | xuống | −6 | −2 |
| cG | trái | −2 | −3 |
| cF | phải | −5 | −4 |
| cJ | OK/chọn | −20 | −5 |
| cK | soft trái | −21 | −6 |
| cL | soft phải | −22 | −7 |

Phím số 0–9, `*`, `#` (48–57, 42, 35) không đổi.

Vá tại chỗ 6 toán hạng `bipush` (`patchkeys.py`, cùng độ dài lệnh, không đổi opcode/kiểu → StackMap giữ nguyên).
`javap` trước/sau chỉ khác đúng 6 dòng đó. Đóng gói: `replace.py <jar> <jar_mới> au.class <au.class_đã_vá>`.

⚠️ Bản này dành cho J2ME Loader / máy dùng mã phím Nokia. Trên máy SoftBank thật hãy dùng JAR gốc + bản sửa manifest
(mã −2/−5/−6 trùng giữa hai bố cục nên một JAR không hỗ trợ cả hai được nếu không phát hiện nền tảng).

## Kiểm tra đã làm / còn lại

- ✅ JarFile đọc manifest mới: `MIDlet-FullVersion-URL` có giá trị.
- ✅ Đối chiếu mọi lời gọi `com.mexa.opgl.*` và `com.jblend.media.smaf.phrase.*` với mã nguồn J2ME Loader: đều tồn tại (SMAF trong J2ME Loader là stub → âm thanh SMAF có thể im).
- ⚠️ Chưa chạy thử trong emulator (không có emulator hỗ trợ OPGL trong môi trường build). Chưa thử máy thật.
- ⚠️ Game dùng API 3D riêng của SoftBank (`com.mexa.opgl`): chỉ chạy trên máy SoftBank hỗ trợ OPGL hoặc J2ME Loader; KEmulator/MicroEmulator/Nokia không có API này.
