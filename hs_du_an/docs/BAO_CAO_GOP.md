# Báo cáo khả thi: gộp 5 phần 轩辕剑-天之痕 thành một game

Chỉ là báo cáo. Chưa thực hiện gộp (theo lựa chọn của người dùng).

## 1. Hiện trạng 5 jar

| Phần | MIDlet-Name | Profile | Jar | Class | Gói obfuscate | RMS save |
|---|---|---|---|---|---|---|
| 1 | 轩辕剑-天之痕 | MIDP-1.0* / CLDC-1.0 | 806 643 B | 66 (45.3) | `a/a/a/a`, `b`–`i`, `main`, `XConnection`, `XMessage` | `sword`, `sword_qq_pay` |
| 2 | …-二章 | MIDP-2.0 / CLDC-1.0 | 838 164 B | 59 (45.3) | `a/a/a/a`, `b`–`g/a`, `main` | `sword2`, `sword2_qq_pay` |
| 3 | …-三章 | MIDP-2.0 / CLDC-1.0 | 1 020 674 B | 75 (67×45.3 + 8×48.0) | `a`, `b/a`–`b/f`, `c`–`h`, `main` | `sword3`, `gil_api_private`, `wappay_gil_api` |
| 4 | …-四章 | MIDP-2.0 / CLDC-1.0 | 995 572 B | 77 (69×45.3 + 8×48.0) | như P3 + `XConnection`, `XMessage` | `sword4`, như P3 |
| 5 | …-终章 | MIDP-2.0 / CLDC-1.0 | 1 009 077 B | 74 (67×45.3 + 7×48.0) | `a/a`–`a/f`, `b`–`h`, `main` | `sword5`, như P3 |

\* P1 khai MIDP-1.0 nhưng đã gọi `Graphics.drawRegion` và MMAPI, tức thực chất chạy trên MIDP-2.0.

Cả 5 phần cùng một engine (Gameislive/Softstar Mobile), cùng cách tổ chức resource (`map/N.m`, `i.dat`, `ani/`, `fra/`, `midi/`, `*.pak`). Nhưng mỗi phần được obfuscate riêng, nên cùng một tên class (ví dụ `b/a`, `main/MainMIDlet`) chứa code khác nhau ở mỗi phần.

Liên kết giữa các phần:
- **Không phần nào đọc save của phần trước.** Mỗi phần mở RMS riêng (`sword`…`sword5`). Các lời gọi `openRecordStore(name, vendor, suite)` chỉ thuộc SDK thanh toán wappay.
- Chuỗi `/sword_`, `/sword2_`… ở P3–P5 chỉ là link quảng cáo tải chương khác (`http://lakoo.cn/portal/.../swordN_x.jad`). Không phải cơ chế kế thừa.
- Câu kết P1 (`map/19.m`: 《轩辕剑-天之痕》首章至此结束) xác nhận mỗi phần là một game độc lập, nhân vật bắt đầu lại từ đầu.

## 2. Các phương án

### A. Một jar chứa 5 MIDlet (MIDlet suite) — KHẢ THI, an toàn cho KVM
Manifest khai `MIDlet-1` đến `MIDlet-5`. Trên Nokia S60, cài một lần sẽ hiện một thư mục có 5 biểu tượng (Chương 1 … Chương 5). Mỗi lần chỉ chạy một MIDlet. Đây là cơ chế chuẩn của MIDP, không cần API ngoài spec.

Việc phải làm:
1. **Đổi tên gói cho từng phần** để tránh trùng class: `b/a` → `p1/b/a`, `main/MainMIDlet` → `p1/main/MainMIDlet`… Phải cập nhật mọi tham chiếu trong constant pool (CONSTANT_Class, descriptor field/method, chữ ký `Class.forName` nếu có). Làm bằng công cụ remap bytecode tĩnh (kiểu jarjar/ProGuard `-applymapping`), sau đó preverify lại. Class mặc định không gói `XConnection`/`XMessage` có ở cả P1 và P4 và **giống hệt từng byte** (đã so md5), nên giữ một bản dùng chung, không cần đổi tên.
2. **Đổi tiền tố resource** cho từng phần: `/map/` → `/p1/map/`, `/i.dat` → `/p1/i.dat`… Đường dẫn resource nằm trong chuỗi literal (`"/map/" + n + ".m"`, `"/midi/" + n + ".mid"`, `"/c.dat"`, `"/t.dat"`, `"/ani/"`, `"/fra/"`, `"/logo"`, `"/mi"`, `"/m"`…) và trong một số lời gọi `getResourceAsStream(String)` nhận tên từ biến. Phải rà hết từng điểm (P3/P4/P5 có 5–7 điểm truyền tên bằng biến). Sót một điểm thì phần đó đọc nhầm resource của phần khác, vì có **347 đường dẫn trùng tên nhưng khác nội dung** giữa các phần.
3. **Manifest**: gộp thuộc tính. Các khoá riêng của SDK thanh toán (`SmsCode*`, `FreeDest`, `CP`, `UA`…) bị trùng tên khác giá trị → chỉ giữ được một bộ, hoặc bỏ tính năng thanh toán.
4. **Biểu tượng**: mỗi MIDlet một icon riêng (`/p1/icon.png`…).
5. Kiểm: `jar_check` từng phần, chạy lần lượt cả 5 MIDlet trong cùng một suite trên emulator, rồi trên máy thật.

Ước lượng dung lượng (đã nén):
- Cộng 5 jar: 4 374 286 B nội dung (~4,7 MB tính cả header zip).
- Gộp các file trùng nội dung (ảnh `fra/`, `b.pak`, `ani/`, `midi/`…): còn khoảng **3,76 MB**. Muốn khử trùng như vậy thì phải để các phần dùng chung một đường dẫn cho các file giống hệt nhau (thêm việc ở bước 2).

### B. Một MIDlet có menu chọn chương — KHÔNG khuyến nghị
Muốn vậy, launcher phải tự `new` MainMIDlet của từng phần hoặc gọi `startApp()` của nó. Theo MIDP, chỉ AMS được tạo MIDlet: nhiều KVM (Nokia S40/S60, Sony Ericsson) ném `SecurityException` khi constructor `MIDlet` chạy lần thứ hai. Cách thay thế là viết lại vòng đời của cả 5 phần (Display, `notifyDestroyed`, `Canvas`) để chạy được không cần MIDlet riêng. Khối lượng lớn, rủi ro cao. Kết luận: **⚠️ NOT J2ME-SAFE** nếu làm kiểu tự khởi tạo MIDlet.

### C. Gộp thành một mạch truyện liên tục (save chuyển chương) — chưa khả thi
Năm phần có 5 bộ engine/obfuscate khác nhau, định dạng save khác nhau, không có code nhập save. Muốn chuyển nhân vật từ chương trước sang chương sau phải viết bộ chuyển đổi save cho từng cặp phần. Đây là dự án riêng, ngoài phạm vi.

## 3. Giới hạn thiết bị

| Thiết bị | Giới hạn jar | Phương án A |
|---|---|---|
| Nokia S60 3rd (N73, N95…) | không giới hạn cứng (theo bộ nhớ máy) | Được |
| Nokia S40 đời cũ | thường 1–2 MB | Không được: dùng 5 jar riêng |
| Sony Ericsson JP-7/8 | khoảng 2 MB trở lên | Tuỳ máy, phải thử |
| J2ME Loader (Android) | không giới hạn | Được, nhưng không dùng làm chuẩn |

Heap không tăng: mỗi lần chỉ một MIDlet chạy, class của phần khác không được nạp. Riêng S60 có thể giữ class đã nạp tới khi thoát suite, nên phải thử lần lượt Chương 1 → thoát → Chương 2 trên máy thật.

## 4. Rủi ro

- Remap sót tham chiếu → `NoClassDefFoundError`/`VerifyError` chỉ lộ khi chạy tới đúng nhánh đó.
- Sót đường dẫn resource truyền bằng biến → phần này đọc nhầm dữ liệu của phần khác, không báo lỗi gì.
- Class 48.0 của SDK thanh toán (P3–P5): đã có sẵn trong bản gốc; phải giữ nguyên tình trạng preverify như cũ.
- `XConnection`/`XMessage` của P1 và P4 giống hệt nhau nên dùng chung được. Nếu sau này sửa riêng một bản thì phải đổi tên.

## 5. Kết luận

- **Khả thi** gộp thành **1 jar – 5 MIDlet** (phương án A) cho máy S60 (đúng máy đích N73 của bản gốc). Jar ~3,8–4,7 MB.
- **Không khả thi an toàn** kiểu một MIDlet có menu chọn chương (B), và chưa khả thi kiểu mạch truyện liên tục (C).
- Nên việt hoá xong 5 jar riêng trước (đang làm), rồi gộp theo A ở bước cuối. Bản dịch và font dùng chung không bị ảnh hưởng, vì bước gộp chỉ đổi tên gói và tiền tố resource.
- Để 5 jar riêng vẫn là bản phát hành chính cho máy S40/máy yếu.
