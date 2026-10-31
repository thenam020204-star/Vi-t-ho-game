# Inotia 4 (Android 1.4.0): Việt hoá, font f18 và bản offline

## Bối cảnh
Người dùng gửi `Inotia4_1.4.0.zip`/`.z01`. Đây là zip chia phần, bên trong là **APKS Android** chứ không phải J2ME: `base.apk` (dex + assets), `split_config.arm64_v8a.apk` (`libgame.so`, `libHercules.so`), `split_config.vi.apk`, `split_config.xxxhdpi.apk`.

Yêu cầu: Việt hoá toàn bộ game; mọi chữ vẽ bằng font **f18** của bộ tool NAM_P, ký tự thiếu thì vẽ tay theo nét 18px; bản **offline thật sự**, không đăng nhập, không cloud, không truy cập mạng.

Người dùng đã chốt:
- **Nền tảng:** làm trên bản Android này. Đầu ra là APK arm64 ký lại bằng khoá mới, nên phải gỡ bản gốc trước khi cài.
- **Văn phong:** lời thoại tiếng Việt hiện đại. **Giữ tiếng Anh** cho tên class, kỹ năng, vật phẩm và tên riêng.
- **Font:** f18 **phóng to theo bội số nguyên** (x1/x2/x3 tuỳ độ phân giải nội của game) để giữ pixel sắc nét.
- **IAP:** mua miễn phí khi offline, bấm mua là nhận vật phẩm ngay.

Các nguyên tắc nghiêm ngặt kiểu J2ME vẫn giữ ở mức tương đương:
- bytecode/smali thật được tin hơn output của decompiler;
- không đổi chữ ký method gốc, chỉ thay thân method;
- không cấp phát object trong vòng vẽ;
- mỗi bản vá có diff trước/sau và đếm đúng số chỗ khớp;
- không đoán, chỗ nào chưa chắc thì ghi "chưa xác định".

## Những gì đã xác minh (đọc file, chưa sửa gì)
- **Tài nguyên** ở `assets/common/game_res/*.jpg`. Dạng file: `01 00` + LZMA-alone (props `5d`, dict 16 MB, kích thước u64). Riêng `font.fnt`, `i_imgfont.dat`, `i_portrait.dat`, `i_title.dat`, `i_worldmap.dat` không nén, và có một số PNG thường.
- **Text** nằm ở `memorytext{,_en,_de,_fr,_jp,_zhhans,_zhhant}.dat`.
  - Định dạng: `u32 count` (=35812), tiếp theo là `count` offset u24 LE, rồi các chuỗi UTF-8 kết thúc bằng `\0`. Đã kiểm: offset đầu bằng `4+3n`.
  - Bản EN có 10.602 mục khác rỗng, 8.576 chuỗi khác nhau, khoảng 77 nghìn từ.
  - Mã điều khiển: `$B $R $S $G $A $D $N` (màu), `&N &P` (xuống dòng/sang trang), `%d %s`.
  - `memorytext_e.dat` chứa công thức RPN, **không dịch**.
- **Cách vẽ chữ:** `libgame.so` có `GRPX_DrawString*` / `SGL_grpCreateFontTexture`, gọi ngược JNI `jDrawText`, `jDrawTextEx`, `jDrawTextInRect`, `jGetTextWidth`, `jGetTextHeight`, `jGetStringWidth` sang **`com/com2us/wrapper/font/CSGLFont`** (Android Canvas). Đây là điểm gắn font f18.
- **Ngôn ngữ:** chọn qua `jGetInitialLanguage`, `GetConfigLanguage`, `SGL_SetLanguage`, `___LangPrefix`.
- **Online:**
  - Hive SDK (`com/hive/*`: AuthV4, iapv4, push, promotion, hercules);
  - trong native: `C2S_HubGuestLogin`, `C2S_HubAutoLogin`, `PopupHiveLoginChoice`, `HubSave_*` (cloud save, khoá save), `HttpConnectPage`, `jGetPackageServerName/Port`;
  - Firebase, Play Games, billing;
  - `nativeHerculesInit` (chống gian lận).
- **Bộ tool NAM_P:** `fonts/f18.bin` (227 ký tự: ASCII + tiếng Việt), `pixeltext_kit`, `fontgen/` và skill `dp` (dịch tay).
- Container không có apktool, jadx, smali hay apksigner, nên phải tải về ở bước 0.

## Cách làm (mỗi giai đoạn một session; cập nhật `inotia4/PROGRESS.md` và `PLAN.md`)

### 0. Hạ tầng
- Tạo thư mục `inotia4/` trên nhánh `ccr-880efacd-ihhm7x`. Không commit APK gốc (quá lớn); ghi sha256 của file gốc vào `goc/SHA256.txt`.
- Tải về `tools/` và ghi phiên bản vào `VERSIONS.md`: apktool, jadx, baksmali/smali, uber-apk-signer (gồm zipalign và apksigner), CFR.
- Giải nén và kiểm kê bằng `scripts/inventory_apk.py`. Bước này ghi lại toàn bộ những gì ở mục "đã xác minh" ra `logs/`.

### 1. Phân tích (chỉ đọc), có bằng chứng smali/disasm cho từng điểm
1. **`CSGLFont`:** chữ ký JNI, kiểu tham số (String hay byte[] UTF-8), font size và scale đang dùng, cách trả về bitmap/texture, chỗ native dùng width/height. Xác định **độ phân giải nội** của game để chọn hệ số phóng nguyên.
2. **Xuống dòng:** native tự ngắt dòng theo ký tự (kiểu CJK) hay theo dấu cách? Hàm nào (`GRPX_DrawStringInRect`, `GetTextWidthInRect`)? Disasm bằng `objdump -d` để xác nhận. Nếu ngắt theo ký tự thì xử lý ở phía Java (`jDrawTextInRect`) hoặc vá native.
3. **Ngôn ngữ:** tìm cách để slot EN nạp file tiếng Việt và ép slot EN bất kể locale máy. Cách ưu tiên: thay `memorytext_en.dat` và ép `jGetInitialLanguage` trả về EN.
4. **Luồng online:**
   - Hive AuthV4 setup và signIn, `PopupHiveLoginChoice`, HubSave (khoá save sinh từ đâu, có phụ thuộc Hive UID không);
   - callback IAP: native nhận `productId` rồi cộng vật phẩm ở đâu;
   - Hercules, push, Firebase, analytics, quảng cáo/promotion, `HttpConnectPage`.
5. **Ảnh có chữ:** soát toàn bộ PNG đã giải nén bằng lưới ảnh, ghi vào `docs/anh_co_chu.tsv`. Các ảnh dự kiến: `UI_BUT_ENG_*`, `UI_TITLEMAIN`, `ETC_ban_*`, `Googleplay`, `i_imgfont.dat` (chữ số dạng ảnh), `font.fnt`.

Quản đốc duyệt kết quả phân tích xong mới sang bước 2.

### 2. Pipeline text
- Viết `scripts/memtext.py` với hai lệnh:
  - `extract`: ghi `text_can_dich.tsv` (id, gốc EN, gốc KO để tham chiếu, ghi chú, giới hạn px);
  - `repack`: dựng lại bảng offset u24, nén LZMA alone, gắn header `01 00`.
- **Selftest:** repack khi chưa dịch phải ra giống từng byte ở cả mức dữ liệu đã giải nén lẫn file `.jpg` (kiểm thêm tham số nén). Nếu không khớp được từng byte ở file nén, chứng minh native đọc được file nén lại: thử giải nén vòng tròn, và kiểm hàm nạp trong native không kiểm checksum.
- `repack --check`:
  - giữ nguyên số mã điều khiển của từng mục;
  - không chứa ký tự ngoài charset của font;
  - offset ≤ 0xFFFFFF;
  - chuỗi bị native so sánh hoặc dùng làm khoá (nếu tìm ra) thì giữ nguyên.

### 3. Font f18 cho Android
- So charset của bản dịch (gồm `… ’ “ ” × ★` và mọi ký tự có trong EN) với `f18.bin`. Ký tự còn thiếu thì **vẽ tay ở lưới 18px theo nét f18**, đặt trong `fontgen_inotia/extra18.py`, rồi build ra `f18vn.bin` (cùng định dạng, charset mở rộng). Không sửa file font gốc của tool. Xuất ảnh preview để duyệt.
- **Class Java `VNFont` cho Android:**
  - nạp `f18vn.bin` từ assets; dựng glyph theo atlas 1bpp;
  - render vào `Bitmap` ARGB với hệ số phóng nguyên (dùng `drawRect` hoặc `setPixels` theo khối, không lọc);
  - cache theo màu; không cấp phát trong mỗi lần vẽ, chỉ dùng lại buffer.
- Biên dịch bằng javac target 8, d8 ra smali, rồi **thay thân** các method vẽ/đo chữ của `CSGLFont`, giữ nguyên chữ ký.
- Width, height và line height trả về phải là advance thật của f18 × hệ số phóng.

### 4. Dịch (dùng `dp`, Claude tự dịch tay, không dịch máy, không dùng subagent dịch)
- Lập `thuat-ngu.tsv` và `vai.md`. Tên class, kỹ năng, vật phẩm, địa danh, nhân vật giữ tiếng Anh. UI thì dùng HP, MP, EXP, ATK, DEF.
- Dịch khoảng 8.576 chuỗi theo lô, qua nhiều session: commit và push sau mỗi khoảng 300 dòng, báo `M/N` mỗi khi dừng. `dp check` phải ra OK. Giới hạn px đo bằng f18 × hệ số phóng.

### 5. Offline thật sự
- **Java (smali):**
  - Hive AuthV4 và luồng đăng nhập trả ngay "guest thành công" bằng một ID cục bộ cố định, không gọi mạng;
  - bỏ `PopupHiveLoginChoice`;
  - Hive IAP purchase gọi thẳng callback thành công với đúng `productId`, để native cộng vật phẩm;
  - push, Firebase, analytics, promotion, webview: no-op.
- **Native (`libgame.so`, vá nhị phân ARM64 tối thiểu, có bảng offset trước/sau):** chỉ vá khi bước 1 chứng minh cần. Gồm:
  - `C2S_Hub*` và `HubSave` server check trả "không có save trên server", save chỉ ở máy;
  - `HttpConnectPage` không làm gì;
  - Hercules: giữ lại nếu chạy offline không lỗi, nếu không thì stub init.
- **Manifest:**
  - bỏ quyền `INTERNET`, `ACCESS_NETWORK_STATE`, `BILLING`;
  - bỏ service/receiver của FCM, Firebase, Play Games và provider khởi tạo Firebase.
  
  Đây là bảo đảm cuối cùng rằng game không vào mạng. Mọi chỗ gọi mạng còn sót sẽ lộ ra thành lỗi khi test.

### 6. Ảnh có chữ
Vẽ lại bằng `pixeltext` cỡ 18 × hệ số phóng tương ứng với ảnh: một màu thân chữ, giữ kích thước và alpha, chữ dài hơn ô thì rút gọn chữ chứ không ép. Theo quy tắc NAMP thì dùng viền đen 1px, xác nhận lại khi duyệt preview. Thay logo/màn Hive và Google Play nếu có chữ quảng cáo.

### 7. Build
- `scripts/rebuild.sh` làm các bước:
  1. ghép các split thành **một APK duy nhất** (base + lib arm64 + config);
  2. apktool decode;
  3. áp các bản vá smali, manifest và lib;
  4. thay assets;
  5. apktool build;
  6. zipalign;
  7. ký v2/v3 bằng keystore NAMP (keystore để ngoài repo, có hướng dẫn tạo).
- Xuất `out/Inotia4_vi_offline.apk`. Phát hành vào `main:releases/Inotia4/` theo quy tắc của repo, kèm gói `Inotia4_du_an.zip`.

## Các file chính
- **Sẽ tạo trong `inotia4/`:**
  - `PLAN.md`, `PROGRESS.md`;
  - `scripts/{inventory_apk.py, memtext.py, rebuild.sh, patch_smali.py, patch_lib.py, check_offline.py}`;
  - `java/VNFont.java`, `fontgen_inotia/extra18.py`;
  - `dich/{text_can_dich.tsv, thuat-ngu.tsv, vai.md}`;
  - `res_vi/`, `docs/`.
- **Sẽ sửa (trong bản decode):**
  - `smali*/com/com2us/wrapper/font/CSGLFont.smali`;
  - các class Hive dùng cho auth, iap và push;
  - `AndroidManifest.xml`;
  - `assets/common/game_res/memorytext_en.dat.jpg` và các ảnh có chữ;
  - `lib/arm64-v8a/libgame.so` (chỉ khi cần).
- **Dùng lại:**
  - `Tool_Viet_Hoa_NAM_P.zip`: `fonts/f18.bin`, `pixeltext_kit`, `fontgen/build_fonts.py` (định dạng `.bin`), `BitmapFont.java` (logic giải atlas, chuyển sang Android);
  - skill `dp` để dịch.

## Kiểm tra
1. **Text:** selftest repack từng byte; `memtext.py repack --check` và `dp check` đều OK.
2. **Font:** test JVM render câu mẫu và khung thoại bằng `VNFont` (stub Bitmap) ra PNG. Kiểm đủ glyph cho toàn bộ bản dịch, không có ô vuông. Kiểm phóng nguyên lần không bị mờ.
3. **Smali:** apktool rebuild không lỗi; jadx decompile lại APK đã vá để soát. Script đếm số chỗ vá, lệch thì dừng. So sánh dex và lib trước/sau, ghi ra `logs/diff_*.txt`.
4. **Offline:**
   - `check_offline.py` grep manifest để chắc không còn quyền INTERNET;
   - liệt kê các call site mạng còn lại (`HttpURLConnection`, `okhttp`, `Socket`, `WebView.loadUrl`, `getaddrinfo` trong lib) và chứng minh chúng không còn được gọi tới, hoặc sẽ thất bại một cách an toàn.
5. **Chạy thật:**
   - Kiểm `/dev/kvm`. Nếu có thì chạy Android emulator arm64 headless, chụp màn: tiêu đề, tạo nhân vật, hội thoại đầu, menu, shop mua miễn phí, lưu/tải.
   - Nếu container không chạy được ARM64, ghi rõ trong `BAO_CAO.md` là **chưa test trên thiết bị** và đưa checklist để người dùng test trên máy thật. Không báo là xong khi chưa có kết quả chạy thật.
6. **Bàn giao theo mốc:**
   1. pipeline text + font hiện ở màn đầu;
   2. dịch xong toàn bộ;
   3. offline + ảnh + gói dự án hoàn chỉnh.
