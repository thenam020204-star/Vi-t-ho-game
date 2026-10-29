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

## Kiểm tra đã làm / còn lại

- ✅ JarFile đọc manifest mới: `MIDlet-FullVersion-URL` có giá trị.
- ✅ Đối chiếu mọi lời gọi `com.mexa.opgl.*` và `com.jblend.media.smaf.phrase.*` với mã nguồn J2ME Loader: đều tồn tại (SMAF trong J2ME Loader là stub → âm thanh SMAF có thể im).
- ⚠️ Chưa chạy thử trong emulator (không có emulator hỗ trợ OPGL trong môi trường build). Chưa thử máy thật.
- ⚠️ Game dùng API 3D riêng của SoftBank (`com.mexa.opgl`): chỉ chạy trên máy SoftBank hỗ trợ OPGL hoặc J2ME Loader; KEmulator/MicroEmulator/Nokia không có API này.
