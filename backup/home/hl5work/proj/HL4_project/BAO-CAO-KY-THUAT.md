# Báo cáo kỹ thuật: HL4 WIPI → J2ME + Việt hoá

## 1. Tổng quan kiến trúc
```
 jar J2ME (MIDP 2.0 / CLDC 1.1)
 ├─ hl4/midp/*        HL4Midlet, GameCanvas (vòng lặp, phím, vẽ, RMS, âm thanh), Sound
 ├─ hl4/core/*        Runtime (dùng chung), Heap, Gfx16 (RGB565), Smaf (MMF→MIDI/WAV), TextEngine + VnFont (Việt hoá)
 ├─ hl4/cpu/*         Cpu (ARMv5TE: ARM + Thumb, user mode), Memory (trang 64KB), Aot (giao diện)
 ├─ hl4/lgt/*         LgtRuntime: nạp binary.mod (ELF32 ARM), bảng import WIPI-C của LGT
 ├─ hl4/ktf/*         KtfRuntime + KJvm (JVM tối giản theo layout bộ nhớ KTF) + KtfPlatform (org.kwis.msp.lcdui.*)
 ├─ hl4/aot/{Ktf|Lgt}Aot  vòng lặp nóng dịch tĩnh Thumb→Java (sinh bởi hl4j/tools/aotgen.py), cho kết quả bit-exact
 └─ tài nguyên gốc của game + overlay tiếng Việt (vi/f10.bin, vi/dict.txt, file dữ liệu đã dịch, binary đã vá chuỗi)
```
Nguyên tắc: **không viết lại logic game**. Mã máy ARM gốc (LGT: `binary.mod`, KTF: `client.bin387872`) chạy trong trình thông dịch. Chỉ các API của nền tảng (WIPI-C, WIPI-Java của KTF) được thay bằng code Java trên host (HLE).

Cơ chế gọi host: một stub là địa chỉ S (căn 4 byte) chứa lệnh Thumb `SVC #0xFF` (0xDFFF) và **id** 32-bit tại S+4. Mã khách nhảy tới S|1. id = `category<<16 | index`.

Bố cục bộ nhớ khách (Runtime): HEAP 0x02000000 (64MB ảo, cấp trang lười), STACK_TOP 0x08000000 (1MB), STUB 0x7F000000.
Category: CAT_INTERNAL 0x7F00, CAT_HLE 0x7E00 (hàm game bị thay bằng host). LGT: INIT 0x100, WIPIC 0x1FB, STDLIB 0x001. KTF: INIT 0x300, JAVA 0x302, WIPIC 0x310+bảng.

### LGT
- `binary.mod` là ELF32. Phần .text được map ở 0x1000 (vị trí trong file = addr−0x1000+0x34), phần data ở 0x1400000 (vị trí trong file = addr−0x1400000+0x93970).
- Tài nguyên nằm trong `res/` của jar. AID 0002D74B, PID PD140249 (đọc từ app_info rồi ghi vào manifest).
- DES của chính game: decrypt 0x8e28, encrypt 0x8e9c (Thumb). `test/src/HL4Tool.java` gọi hai hàm này để giải/mã hoá file dữ liệu.

### KTF
- `client.bin387872` được map ở IMAGE_BASE 0x100000 (vị trí trong file = addr−0x100000). Mã PIC có GOT (sl) = 0x189cb8.
- Gồm các lớp Java đã được biên dịch AOT sẵn cùng mã native của Clet. KJvm dựng class/object theo layout KTF (tham khảo emulator "wie" của dlunch trên GitHub). Các lớp nền tảng (java.lang.*, org.kwis.msp.lcdui.*) là method host.
- Manifest ghi: HL4-AID 010100D4, HL4-PID PD008712, HL4-Binary client.bin387872.

### Phím (GameCanvas.wipiKey)
Mã WIPI: UP −1, DOWN −2, LEFT −3, RIGHT −4, FIRE −5, LSK −6, RSK −7, **CLR −16**, số 0–9 và `*` `#` giữ nguyên mã ASCII. Sự kiện: press 502, release 503, repeat 504.
**Ánh xạ hiện tại (theo yêu cầu người dùng): phím mềm trái vật lý (−6, Motorola −21) → CLR (−16).** Các phím −8, 8 (backspace), 27 (esc) cũng → CLR. RSK −7 giữ nguyên. Hệ quả: game không còn nhận được LSK của WIPI; nếu có màn hình cần LSK thật thì phải bàn lại với người dùng.

### Lưu game, âm thanh, hiển thị
- Lưu game: Host.storageRead/Write → RecordStore (mỗi file một store). Định dạng save giống bản gốc.
- Âm thanh: MMF (SMAF) → MIDI/WAV (Smaf.java), phát bằng MMAPI Player.
- Framebuffer RGB565 trong bộ nhớ khách → int[] ARGB → vẽ (có scale nguyên nếu màn hình lớn).

### Hiệu năng
- Harness headless chạy khoảng 45 MIPS trên JVM desktop. Trên thiết bị/giả lập J2ME chậm hơn nhiều, nên có AOT.
- AOT: chạy headless với `HL4_PROFILE` để lấy profile, sau đó `aotgen.py --bin <ảnh code> --base <addr> --class KtfAot --out src/aot/hl4/aot/KtfAot.java <profile>`. Chỉ các vùng vòng lặp không có call/return/host-call mới được dịch. Kết quả đã kiểm chứng bit-exact so với trình thông dịch. Nếu thiếu class AOT thì GameCanvas tự bỏ qua.

## 2. Việt hoá: engine chữ (TextEngine + VnFont)
- Engine chữ gốc (cùng mã C ở cả hai bản): ASCII 5x11 (advance 6) và Hangul EUC-KR 2 byte (10x11, advance 11), cộng các mã điều khiển: `;` xuống dòng, ` `/`=` là khoảng trống 4px, các mã màu `&|${}'"\`^<`.
- Phần HLE thay 3 hàm bố cục chữ: **measure, fit lines (ngắt dòng/trang), draw**, cộng draw có màu.
  - LGT: hookThumb 0x2f484 (measure), 0x2f524 (fit), 0x2fe78 (draw), literal 0x1e4f0 → stub draw màu. Hàm gốc: drawOrig 0x2f71c, colorCode 0x2f680, setColor 0x2f420, ctx 0x8f10, putPixel 0x2f398.
  - KTF: hookThumb 0x133f74 / 0x13495c / 0x134840; patchBL 0x12277c → stub gần ở 0xF0000. Hàm gốc: drawOrig 0x13405c, colorCode 0x133e2c, setColor 0x133dd0, ctx 0x108a40, putPixel 0x133d24; addrEnabled/addrCodes đọc qua GOT+0x270/0x274.
- Mã hoá chữ Việt: mỗi **âm tiết/từ** (hoặc ký tự có dấu) = 2 byte, gồm lead 0xC9.. và trail 0xA1.. (94 mã mỗi lead). Chỉ số k = (lead−0xC9)*94 + (trail−0xA1) trỏ vào dòng thứ k của `vi/dict.txt`. Nhờ vậy mọi đoạn xử lý "byte cao = ký tự 2 byte" của game vẫn đúng, và độ dài byte gần bằng tiếng Hàn. ASCII giữ 1 byte.
- Font: định dạng "fontcus" (u2 utfLen, charset, s1 height, s1[n] advance, atlas 1bpp LSB-first). yOff = 8 − baseline (baseline lấy từ chữ 'H'). spaceW = advance(' ').
- Ngắt dòng theo từ, dùng chung cho measure/fit/draw để phần phân trang của game khớp. Bug đã sửa: wordWidth() ghi đè trạng thái token, nên giờ phải lưu/khôi phục tt/tlen/tw/tcode.
- Chữ Hàn còn sót và chữ ASCII nhỏ vẫn vẽ bằng hàm gốc (mỗi lần một glyph).

## 3. Việt hoá: pipeline dữ liệu (thư mục vi/)
1. `extract.py` gom mọi chuỗi Hàn (EUC-KR) vào `catalog.json` (19154 mục dùng, có id, kind, src, speaker, cap).
2. `make_chunks.py` chia thành `tr/chunks/*.json` để dịch. Bản dịch nằm ở `tr/out/*.json` và đã đủ 100%, `check.py` báo OK hết.
3. `build_vi.py` tạo overlay trong `vi/build/{lgt,ktf}` (cấu trúc thư mục = gốc jar; LGT có tiền tố `res/`):
   - scn hội thoại `MAP/SC/*_scn` (mã hoá DES), các bảng tdf/GMenu/NPCUI/EVENT_POP/HELP/FREE/UseInfo, `_tdf_TIP`, `NPC/NAME_DAT`, tên bản đồ trong `MAP/M/_MAP_M_*`;
   - các họ record (mã hoá DES): QUEST, ITM, ESDAT/BSDAT, COMBINE, GUARDIANSHOP, H_S*/H_SS/H_BH. Chuỗi dạng u1 len + bytes, record dạng u2 len, sau đó pad đủ bội 8 rồi mã hoá bằng DES của game (HL4Tool);
   - chuỗi trong binary: vá tại chỗ theo `vi/data/{lgt,ktf}_binstr.json` (addr, slot). 117 chuỗi mỗi bản. Chuỗi dịch phải vừa slot, nếu không thì giữ nguyên tiếng Hàn và in "too long";
   - chép font vào `vi/f10.bin` (tên file giữ nguyên dù là font 15px) và `vi/dict.txt`.
   - `vi/dec/` là bản giải mã sẵn của các file DES (tạo bằng `java -cp hl4j/build/test2 HL4Tool des decrypt <in> <out> <files…>`).
4. `hl4j/build.sh <LGT|KTF> <zip gốc> <outdir>` với env `OVERLAY` và `SUFFIX`: ecj (source 1.3 / target 1.2) → ProGuard (preverify, không obfuscate) → ghép jar gốc + overlay + manifest/jad.
5. `make.sh` (thư mục gốc) chạy bước 3 và 4.

Quy tắc dịch (quan trọng khi sửa câu): xem `vi/tr/HUONG-DAN-DICH.md`. Tóm tắt: giữ nguyên số lượng các mã điều khiển; không dùng `_`, ngoặc kép cong, gạch dài; `;` là xuống dòng; tên riêng theo `thuat-ngu.tsv`; `cap` là giới hạn byte.

## 4. Lịch sử bản phát hành
- test1: bản VI đầu tiên (font 10px v2).
- test2: có hai biến thể _VI (10px) và _VI15 (15px).
- **fixCLR (bản chuẩn)**: KTF, font 15px, LSK→CLR. Người dùng xác nhận chạy tốt nhất. File: `release/`.

## 5. Tài nguyên ngoài gói
- `wie` (emulator WIPI viết bằng Rust của dlunch, GitHub): tài liệu tham khảo cho layout KTF, SMAF, API. Không kèm vì nặng 850MB.
- FreeJ2ME: dùng để thử jar trên PC (test harness nằm ở `hl4j/test/fj`). Không kèm mã nguồn.
- Bộ công cụ font của người dùng (Tool_Vi_t_Ho__NAM.P.zip) đã chép nội dung vào `vi/font/` (README, fontcus.java, pixeltext-kit, font_10px, font_15px.bin).

## 6. Biến môi trường của harness RunHeadless
`HL4_VI=<thư mục có f10.bin, dict.txt>` bật engine chữ Việt; `HL4_RES=<overlay>` đè tài nguyên; `HL4_DEBUG`, `HL4_TEDEBUG` (log engine chữ), `HL4_TRACE=<svc hex,…>`, `HL4_SVCLOG`, `HL4_CALLS=<addr hex,…>` (+`HL4_CALLS_FILTER`, `HL4_CALLS_STK`), `HL4_WATCH=<tên file>` (theo dõi truy cập vùng nhớ chứa file tài nguyên), `HL4_WATCHADDR`, `HL4_PROFILE` (profile cho AOT), `HL4_AOT` (bật AOT trong harness), `HL4_JTRACE` (trace KJvm); `-Dipms=` số lệnh/ms của đồng hồ ảo, `-Dstorage=` thư mục save.
Lưu ý: chạy headless tới màn hình tiêu đề có thể hiện "Mất kết nối với máy chủ" nếu bấm OK sớm vào mục mạng. Đây là hành vi bình thường vì không có mạng.

## 7. Cấu trúc gói
```
README.md  BAO-CAO-KY-THUAT.md  setup.sh  make.sh  LGT.zip  KTF.zip (bản gốc)
release/   HL4_KTF_VI_fixCLR.jar/.jad (bản chuẩn)
hl4j/      src/{core,midp,aot}  test/src (RunHeadless, HL4Tool, CpuTest...)  tools/aotgen.py  build.sh  tbuild.sh
tools/     ecj, proguard, stub CLDC/MIDP, microemulator
vi/        extract.py make_chunks.py build_vi.py fmt.py binstr.py catalog.json dec/ data/ font/ tr/
```

## 8. Hiệu năng và giới hạn 27 fps (phiên 2026-09-27)
**Nguyên nhân tụt khung hình:** vòng lặp game đặt lại timer 1 ms và mỗi callback là 1 khung hình, logic chạy theo khung hình, nên game chạy nhanh hết mức CPU cho phép và tốc độ game phụ thuộc fps. AOT cũ chỉ dịch được vòng lặp đo từ màn hình tiêu đề/menu. Hàm vẽ sprite trong suốt (alpha 4-bit, 0x10f404) chiếm khoảng 30% thời gian khi chơi nhưng chạy bằng trình thông dịch. Ngoài ra khoảng 10% lệnh còn thông dịch nhưng chiếm khoảng 60% thời gian.

**Giới hạn fps** (`Runtime.setFrameLimit`, manifest `HL4-FPS: 27`, harness dùng `HL4_FPSCAP=27`): timer của game đặt lại với timeout nhỏ hơn 1 khung thì bị lùi để hai callback liên tiếp cách nhau ít nhất 1000/27 ms. Lịch chạy theo lưới thời gian cố định (µs), chỉ bắt kịp khi trễ dưới nửa khung, nên trung bình đúng 27 fps. Khung chậm hơn thì không bị chờ thêm.

**AOT mới** (`hl4j/tools/aotgen.py`, `tools/regen_ktf_aot.sh`):
- `--coldcut`: bỏ nhánh lạnh khỏi region vòng lặp; `--maxinsns 1000` để phủ được vòng blit alpha lớn;
- loại bỏ cờ chết: phân tích liveness của n/z/C/v, chỉ tính cờ khi còn được đọc;
- `--funcregions`: dịch cả code nóng không phải vòng lặp. Region thoát ở BL/BLX (có đặt r14), BX/BLX thanh ghi, POP {pc}; lối ra sang ARM được `Cpu.runLoop` xử lý qua bit0;
- bảng entry lưu dạng chuỗi (để `<clinit>` nhỏ);
- **loại trừ các địa chỉ bị HLE vá lúc chạy** (0x133f74, 0x13495c, 0x134840, 0x12277c). Nếu quên, AOT sẽ chạy mã gốc thay cho hook. `Runtime.hookThumb/patchBL` còn gọi `cpu.aotBlock()` để chặn thêm lúc chạy.
- Profile: `hl4j/profiles/ktf_*.txt` (gameplay, đi lại/hội thoại, menu).
Kết quả trên PC: AOT phủ khoảng 99% số lệnh khi chơi. Đoạn chơi 15 s: bản cũ 20,5 s thực → bản mới 2,8 s. Đã kiểm tra **bit-exact** với trình thông dịch (cùng icount, cùng ảnh khung, cùng thống kê svc) trên 2 kịch bản 150 s và 500 s.
**Host:** `Gfx16.copy` không cấp phát mảng mỗi lần gọi nữa (memmove theo từng từ), `fillRect` điền theo từ, `toArgb` đọc 2 pixel mỗi lần.
**Công cụ kiểm chứng:** `HL4_AOTCHECK=<icFrom>,<icTo>` chạy lại mọi region AOT trong khoảng đó bằng trình thông dịch và in chỗ lệch (`AOTCHECK ...`). `HL4_FPS=1` in fps, số lệnh/khung, thời gian thực.
LgtAot (bản LGT) chưa được sinh lại.

## 9. Bỏ online, server giả lập offline (phiên 2026-09-27)
**Mạng của game (KTF):** TCP tới 210.222.18.31:8894. Bảng mạng WIPI-C của KTF dài hơn 30 hàm, phần tiếp theo rơi vào bảng T_IF11: idx 30 = socket connect (fd, addr, port, ?, cb, param), trả về M_E_WOULDBLOCK = -19 rồi gọi cb(fd, 0, param); 31 = write, 32 = read (-19 khi chưa có dữ liệu), 33 = close. Game dùng thêm `MC_netSetReadCB` (13).
Gói tin: `u32 len (tính cả header) | u8 major | u8 minor | payload`. Hàm phân phối gói 0x1696e0: major 1 → 0x169444, 5 → 0x1678a8 (bảng nhảy theo minor-3), 20 → 0x167630, 100 → 0x167414. Trong bộ đệm nhận, gói nằm ở buf+12, payload bắt đầu từ buf+18.
Trạng thái kết nối = byte `[[GOT+0x18c]]+0xdb` (0x189e44 → G): 5 = đồng ý SMS ở màn tiêu đề, 2 = khôi phục nhân vật (#), 6 = mua hồi sinh, 0 + cờ G+0xd4 = cửa hàng (phím phải).

**`hl4.ktf.FakeServer`** trả lời các gói sau:
- 1/1 hello → [0]; 1/0x3d login → [0]; 1/0x3e → [0,0,văn bản]; 1/0x50 (đồng ý SMS) → [1,0,"Đã lưu lựa chọn."]. Payload[0] ≠ 0 nghĩa là có thông báo; cờ được lưu nên lần sau không hỏi lại.
- 5/63 danh mục cửa hàng: [cờ, cờ, u16 n, n bản ghi × 37 byte]. Bản ghi: [0] tab; [1..8] id (gửi lại ở 5/66); [9..16] mã sản phẩm (gửi lại ở 5/64 và 5/107, ta đặt = loại, số thứ tự); [17] loại, [18] số thứ tự vật phẩm; [19] số lượng; [20] cờ; [21..24] giá (0).
- 5/66 (mua) → [1,0,msg] để game đi tiếp sang thanh toán 5/64. 5/64 hoặc 5/107 → [1, L, msg(L), code]. code 0xff = trao vật phẩm đã chọn (0x140248). Code khác = một mục của `_ITM_CASH_RANOMBOX` (dùng cho Rương báu 25..28; 0x129760 phát nội dung).
- 5/106 số dư KOIN → [1, L, msg, u32 số dư, u32 giá]; 5/110, 5/111 (hồi sinh) → [1,0,msg].
- Gói khác: server "ngắt". **Kho mạng và khôi phục nhân vật bị từ chối ngay ở `MC_netConnect`**: chỉ trạng thái 5, 6 và cửa hàng mới được kết nối; các trạng thái khác nhận cb(-1) như khi không có mạng, và game hiện "Bản offline: không hỗ trợ" (chuỗi 7252).
- Danh mục mặc định: 15 vật phẩm cash, loại 8, số thứ tự 15..29 (Elixir ×5, Sách hồi sinh ×3, Đá tinh luyện cao cấp ×5, các loại bùa, Thêm ổ khảm, Túi hành trang, Tự nhặt đồ, các loại đặt lại, Rương báu ×4 loại). Tất cả miễn phí.
- Chuỗi hiển thị do server gửi được mã hoá bằng `TextEngine.encode()` (cùng cách mã hoá với build_vi). Bản tiếng Hàn dùng ASCII.
- Harness: `HL4_ECHO=1` (server chỉ phản hồi lại đúng gói nhận được), `-Dhl4.fs.box=<code>` (ép mã rương). Log `[FakeServer] req M/m hex`. Game tự in tên giao thức qua printk.
- Chưa kiểm tra trong headless: mua hồi sinh khi chết (5/110-111), thanh toán KOIN, NPC "Mạng" (kho mạng; dự kiến bị từ chối như khôi phục nhân vật).

**DES trên host:** hàm 0x108500 (16 vòng Feistel trên mảng bit kiểu int) được hook thành HLE 16 (`KtfRuntime.desRounds`). Bảng E/P/S/khoá con đọc từ bộ nhớ game qua chuỗi con trỏ GOT (literal 0x1086a8/ac/b0/b4). `test/src/KtfDesCheck.java` so sánh giải mã và mã hoá của 504 file (823 KB) giữa mã game và HLE: khớp 100%. Hệ quả: mở cửa hàng từ khoảng 650 triệu lệnh (≈6,5 s ảo) còn tức thì, tải map và save cũng nhanh hơn. Trong AOT đã loại trừ vùng 108500-108508. Lưu ý: vì HLE không tăng icount nên các lần chạy harness không còn khớp bit-exact với bản cũ (chỉ lệch về thời gian ảo).

## 10. Ảnh, logo và UI mới (phiên 2026-09-27)
**Định dạng ảnh `*_BM`** (`img/bm.py`): u32 số ảnh, mỗi ảnh gồm u32 kích thước + thân. Thân: u8 kiểu (0x0b = 4 bpp, nibble cao đứng trước; 0x0c = 8 bpp), u16 w, u16 h, u16 aux (thường bằng w), u16 số màu, bảng màu RGB565 (màu 0 = 0xF81F trong suốt), rồi các điểm ảnh. Các hàm `parse`, `decode`, `encode` (lượng tử hoá còn 255 màu, có dither), `blank`, `build`.
- **Logo NSX:** file `l/_LOGO` có 6 ảnh; ảnh 4 là logo EA 86x98. Màn logo vẽ ảnh 4 ở x căn giữa, y = 160 − 3h/4. `make_logo.py` làm các ảnh khác trong suốt và đặt ảnh 4 thành 240x640, tranh của người dùng nằm ở nửa dưới, nhờ vậy phủ kín màn 240x320. Nguồn: `img/src/logo_namp.png`, `build_vi.py` tự áp cho cả KTF và LGT.
- **Ảnh tiêu đề:** `tdf/TITLE_BM` là một ảnh 290x199 8 bpp (chữ logo in liền vào nền), vẽ ở vị trí (−18, 16); vùng thấy được là cột 18..257. `make_title.py` nhận PNG (tự đưa về 290x199). Nếu có `img/src/title.png` thì `build_vi.py` tự đóng gói. Gói xuất ra cho người dùng vẽ: `HL4_logo_tieude_de_ve.zip`.

**UI thay thế (`hl4.core.UiOverlay`)**, chỉ có ở bản VI: `TextEngine.Sink` nhận mọi lần vẽ chữ (toạ độ + chuỗi đã giải mã) trong một khung hình. Lúc flush (`ui.compose` trong KtfRuntime và LgtRuntime), nếu nhận ra một cửa sổ thì vẽ lại toàn màn hình. Logic và phím vẫn do game xử lý.
- **Nhân vật (Status):** nhận diện qua chữ "Status" ở (100..105, 80..86). Các giá trị lấy theo vùng toạ độ chữ gốc: tên (40..135, 98..108), chủng tộc, lớp, Level (x > 170, y 104..112), HP/SP/EXP/NEXT, 8 chỉ số (y 123..186, bước 9), điểm (40..100, 221..228), STR/AGI/VIT/INT. Ô đang chọn: kiểm tra điểm vàng ở cạnh trái hộp (x 127..129 và 179..181; y 215 và 229). Chân dung: chép vùng (33, 100, 44x54) của khung gốc, phóng x2.
- **Túi đồ (chưa làm):** game chỉ vẽ lưới 4 cột của túi đang mở, nên để có lưới 8 cột như tham chiếu phải đọc dữ liệu thật. Đầu mối: đối tượng vật phẩm có +0x20 loại, +0x22 số thứ tự, +0x25 số lượng (xem 0x16747c và 0x140248 = thêm vào túi); icon ở `ITM/_ITM_ICN`, `_ITM_SD*`. Dữ liệu người chơi nằm quanh 0x19a28c (HP int; STR/AGI/VIT/INT dạng byte ở 0x19a36e và 0x19a390).
- Công cụ harness: `HL4_TEXTLOG=<ms>` in mọi chữ được vẽ trong 60 ms kể từ thời điểm đó; `HL4_DUMPMEM=<ms>` ghi `mem.bin` (các trang: số trang + 64 KB) và `mem.png`; đọc bằng `tools/mem.py` (`load`, `find_seq`).

## 11. Âm thanh thuần J2ME (phiên 2026-09-27)
- Lúc build, `SndConvert` (make.sh gọi, dùng `hl4.core.Smaf`) chuyển mọi `SND/*_MMF` thành `SND/<tên>.mid` (track score → SMF format 1) hoặc `SND/<tên>.wav` (PCM 16-bit, 8 kHz, mono). KTF: 20 .mid + 21 .wav; LGT: 20 + 22. Không có file nào lỗi.
- Tên gốc `SND/*_MMF` được thay bằng stub 22 byte: `"HL4SND\0" + đường dẫn tài nguyên` (LGT: `/res/SND/...`). Game vẫn nạp và truyền dữ liệu như cũ, vì game không đọc nội dung MMF (số lời gọi media trùng khớp với bản cũ). `Sound.create` thấy stub thì đọc thẳng .mid/.wav trong jar và phát bằng MMAPI (`audio/midi`, `audio/x-wav`). Không còn chuyển đổi lúc chạy.
- Smaf.java vẫn nằm trong jar, chỉ để phát file MMF chưa chuyển (bản build không có overlay).
- Muốn thay nhạc: đặt file .mid/.wav cùng tên vào overlay (sau bước SndConvert trong make.sh), giữ nguyên stub.

## 12. Sửa âm thanh bị nhỏ/mất, hệ thống âm thanh mới (phiên 2026-09-27)
- **Lỗi:** bộ giải mã Yamaha ADPCM trong `Smaf.adpcm` dùng bảng bước 57..153 >>6 (0,8906 thay vì 230/256) cộng bộ lọc DC, nên biên độ tụt dần: nhiều hiệu ứng nhỏ hơn 10–25 dB (EFF_07 rms 724 → 9918, EFF_10 188 → 3457). Đây có thể là nguyên nhân mất tiếng súng.
- **Sửa:** dùng đúng thuật toán `adpcm_yamaha` của ffmpeg (bảng 230,230,230,230,307,409,512,614 >>8, bước kẹp trong 127..24576, nibble thấp trước), bỏ bộ lọc DC, thêm fade-out 5 ms chống tiếng click. Đã so với `ffmpeg -f mmf`: 21/21 file trùng từng mẫu (trừ đoạn fade).
- **`hl4.midp.Sound` viết lại:** mỗi file là một bank được cache, không tạo lại khi game làm "stop, free, set data, play" cho từng hiệu ứng.
  - Hiệu ứng có pool tối đa 3 player đã prefetch, player thứ 2 và 3 chỉ tạo khi cần phát chồng (bắn liên tục).
  - Hiệu ứng một lần phát hết, không bị cắt khi game gọi stop/free; nhạc và âm lặp vẫn theo đúng stop/free.
  - Tối đa 6 tiếng hiệu ứng cùng lúc; chỉ giữ 24 player (bỏ player lâu không dùng nhất); nhạc nền chỉ giữ một bài.
  - Lúc khởi động, một luồng nền prefetch sẵn mọi file .wav theo `SND/list.txt` (do SndConvert ghi).
- Đã test logic bằng MMAPI giả (`/tmp/sndmock`): bắn liên tục cách nhau 120 ms thì mỗi phát đều start ngay, xoay vòng pool. **Chưa nghe thử trên máy thật.**
- Harness: `HL4_SNDLOG=1` in create/play/stop/free theo tên file.

## 13. Lưu game (phiên 2026-09-27)
- Cách game lưu (KTF): file `kickass` (khoảng 2248 byte). Mỗi phần: open mode 8 → seek → ghi dữ liệu → seek 0 → ghi thư mục 200 byte → close. Lưu một slot gồm 3 lượt như vậy. Mode: 1 = đọc, 2 = tạo mới, 4 = xoá rỗng, 8 = đọc/ghi.
- Bản cũ ghi cả file xuống RMS ở **mỗi lần** stream write, và mỗi handle giữ một bản sao dữ liệu riêng. Nếu máy dừng giữa chừng hoặc có handle cũ thì có thể lưu dở hoặc đè dữ liệu cũ.
- **Mới (`KtfRuntime`):** mỗi tên file dùng một bộ đệm `FileBuf` chung cho mọi handle. Ghi chỉ vào bộ nhớ; khi close mới ghi nguyên khối xuống storage, tức mỗi phần lưu được ghi trọn một lần. `flushStorage()` được gọi khi app bị tạm dừng, ẩn hoặc thoát.
- **RMS (`GameCanvas`):** store `hl4<tên>` cộng bản dự phòng `hl4<tên>~`. Tìm record theo enumeration thay vì mặc định id 1. Ghi xong đọc lại để so; nếu sai thì xoá store, tạo lại rồi ghi tiếp. Khi đọc, nếu store chính hỏng thì lấy bản dự phòng.
- Đã test headless: lưu lúc 146 s rồi lưu lại lúc 206 s, chạy lại game thì slot hiện play time 1:41 (bản mới nhất) và nạp đúng vị trí. **Chưa test RMS trên máy thật.** Lưu ý: xác nhận "Lưu game? Có" phải bấm OK/5; phím mềm trái giờ là CLR (huỷ).
- LGT (`LgtRuntime`) vẫn dùng kiểu ghi cũ, chưa sửa.

### 13b. Lỗi "mua cash rồi load bị lùi về trước giao dịch" (ĐÃ SỬA ở ui7)
- Người dùng (ui5): mua Rương báu trong shop, lưu (System > Lưu), rồi System > Main menu > load thì ra trạng thái trước khi mua.
- **Không tái hiện được trên harness** (đã thử: Elixir, Túi hành trang, Tự nhặt đồ, Rương báu với mã 0..4; lưu tay, load qua Main menu và qua khởi động lại; có/không AOT; FPS cap 27; đồng hồ 2026). Mọi trường hợp đều load đúng trạng thái mới.
- Game **không** kiểm tra đồ với server khi load. Mỗi phần save = 16 byte hash (hàm 0x1434b4) + dữ liệu mã hoá DES (0x1089b0 / 0x108920). Đọc phần (0x176778) giải mã rồi so hash. CERTIDATA (0x143504) chỉ lưu PHONENUMBER (ta trả ""). Mua xong game tự lưu GameSave + SlotSave ngay khi nhận 5/64. Có gói 20/70 gửi GameSave lên server (0x166104, sao lưu nhân vật), nhưng bản offline đã chặn.
- Load qua Main menu đọc từ `FileBuf` trong bộ nhớ, không qua RMS. Nếu lỗi xảy ra theo đường này thì game đã không ghi, hoặc đã ghi dữ liệu cũ.
- **ui6 (theo yêu cầu người dùng): không ghi đè nữa.** `GameCanvas.storageWrite` xoá hẳn store bản dự phòng `hl4<tên>~` rồi tạo lại và ghi, sau đó làm y như vậy với store chính `hl4<tên>`; nếu lỗi thì thử lại 1 lần. Lúc nào cũng còn ít nhất một bản đầy đủ. Đọc: store chính rỗng hoặc hỏng thì lấy bản dự phòng. Bỏ hẳn `RecordEnumeration`: FreeJ2ME có lỗi `nextRecordId()` không tiến nên vòng lặp cũ treo. Giờ đọc bản ghi đầu tiên có thật, từ id 1 tới `getNextRecordID()-1`. Đã test với RMS của FreeJ2ME: ghi 5 lần liên tiếp đọc lại đúng, xoá store chính thì đọc được từ bản dự phòng.
- **Dòng chẩn đoán (có trong ui6)** (manifest `HL4-SAVEDBG: 1`, harness `HL4_SAVEDBG=1`): vẽ một dòng vàng trên đầu màn hình trong 5 s.
  - `GHI#n <size>B G:xxxxxx S:yyyyyy` khi ghi file. G/S là 3 byte đầu hash của GameSave (@456) và SlotSave (@1736).
  - `DOC G:xxxxxx` khi game đọc GameSave để load.
  - `LOI GHI RMS`, `DOC BAN DU PHONG` khi RMS lỗi.
  - Nhờ người dùng so G lúc lưu và lúc load.
- Harness: `HL4_VTIME=<ms>` đặt đồng hồ ảo, `-Dipms=` (qua `JOPT`) đổi tốc độ CPU ảo, `-Dhl4.fs.box=<n>` ép nội dung rương.

- **Nguyên nhân (tìm được nhờ ảnh chụp của người dùng trên ui6):** file 4040 byte, mã GameSave không đổi giữa các lần lưu. Hàm ghi phần save 0x176eb8: nếu dữ liệu mới (làm tròn lên bội 256) lớn hơn chỗ đã cấp cho phần đó (GameSave cấp 0x500 = 1280 byte, túi đồ nhiều thì vượt), game **dựng lại cả file** sang file tạm `chicken`: mở mode 2, ghi thư mục mới, chép các phần khác sang, thêm phần mới vào cuối. Sau đó gọi db hàm 6 `(kickass, 1)` = xoá file, rồi db hàm 7 `(chicken, kickass, 1)` = đổi tên. Bản cũ coi hàm 6 là no-op và **không có hàm 7** (trả -1). Vì vậy `kickass` giữ nguyên bản cũ, còn mọi lần lưu sau chỉ ghi vào `chicken`. Load thì đọc `kickass`, ra trạng thái trước khi túi đồ lớn lên (tức trước lần mua).
- **Sửa (ui7, `KtfRuntime`):**
  - db 6 với tên file = xoá file (nếu arg0 là handle đang mở thì vẫn là no-op).
  - db 7 = đổi tên: chuyển `FileBuf` sang tên mới, ghi xuống storage, xoá tên cũ.
  - mode 2 = tạo mới rỗng.
  - Lúc khởi động, nếu còn `chicken` sót lại từ bản cũ (luôn là save mới nhất) thì chép thành `kickass`, xoá `chicken`, rồi hiện dòng "KHOI PHUC SAVE MOI NHAT".
- Test headless:
  - mua 17 món: save được dựng lại 2 lần (2504 → 2760 → 3016 byte), load qua Main menu đúng, mã G lúc lưu và lúc đọc khớp;
  - storage gồm `kickass` cũ + `chicken`: khởi động lấy đúng bản mới (play time 4:21, túi đầy).
- Dòng chẩn đoán giờ tìm vị trí GameSave/SlotSave theo thư mục, không dùng offset cố định nữa. Có thêm dòng `XOA`, `DOI TEN`.

---

# Phần B: HL5 – 영웅서기5: 칠흑의 계약 (LGT) → J2ME (phiên 2026-09-27)

Mã nguồn riêng ở `hl5j/` (copy engine HL4 rồi sửa; `hl4j/` giữ nguyên). Công cụ RE: `hl5/tools/img.py` (nạp ELF theo section, `dis` Thumb/ARM có chú thích literal + chuỗi, `str`, `xref`, `imports()`, `users(bảng, hàm)`), `hl5j/tools/prof.py` (gom profile theo hàm), `hl5j/tools/jsheet.py` (ảnh ghép). Cần `pip install capstone --break-system-packages`.

## B1. Nạp binary.mod, import
- ELF ARM: .text 0x1000 (file off 0x34, 0x118174 byte), .data 0x1400000 (off 0x141598), .bss 0x1500000 (0xfd58), entry 0x10fa64 (Thumb). `LgtRuntime.load` map theo section như HL4.
- Import **lười**: mỗi hàm WIPI là một thunk 16 byte trong .data: `str lr,[sp,#-4]!; bl 0x10f454; .word bảng; .word hàm` (ARM). Lần gọi đầu, resolver 0x10f454 → 0x10f9d4 gọi get_import_function rồi **vá thunk** thành `ldr ip,[pc,#4]; bx ip; .word bảng; .word địa_chỉ_stub`. Code Thumb gọi qua veneer `bx r3/r4...` ở 0x10e220.. (`ldr r3,=thunk; bl 0x10e224`). `img.imports()` liệt kê 175 thunk (95 stdlib bảng 1, 79 WIPI-C bảng 0x1fb, 1 bảng 0x1f8).

## B2. DRM MIDAS (bản test phone)
- startClet 0xa328 → 0x99d8(0) → 0x9900: mode 0 so `"00032870"` với chuỗi của WIPI 0x97(0x6a()) (AID). Khớp → 0x92ac → vào game (state 0x140005c = 0 → 1). Không khớp → kết nối 210.222.18.28:11532 xác thực + màn "AUTH / Hacking is not your rights", nhập PASSWD 4 số ("%04d" 0x1d0b = 7435).
- Có hạn dùng: `0x4d64699b − now/1000` (2011-02-23) chỉ để hiển thị "%dDay %dHours" khi rơi vào màn xác thực. Vì 0x97 trả AID thật (manifest `HL4-AID: 00032870`) nên không bao giờ vào màn này. **Harness HL4 cũ hard-code AID HL4 nên sẽ kẹt ở màn này**; harness HL5 đọc AID/PID từ `app_info`.

## B3. API WIPI-C LGT thêm cho HL5 (`LgtRuntime`)
- 0x198: gọi với thư mục tài nguyên (`/c`, `/c/sp/img0`...) cờ 1 lúc khởi động (kiểu mkdir) → trả 0.
- 0xcf **MC_grpGetContext**(ctx, idx, out): clip = {x1,y1,x2,y2} với x2/y2 **không gồm** (nội bộ lưu x2−1), các chỉ số khác 1 word. Game dùng để lấy clip cho hàm putPixel riêng.
- `TIMEZONE` = `"GMT+09:00"` (game parse "GMT±hh:mm").
- Mạng: 0x258 connect(cb,param), 0x259 close, **0x7d0 = socket(2,1)**, 0x25b socketConnect(fd, addr, port(be16), cb(fd,res,param), param) trả −19 rồi gọi cb, 0x25c write, 0x25d read (−19 khi chưa có dữ liệu), 0x25e socketClose, 0x263 getHostAddr, 0x265 setReadCB(fd,cb,param) (một lần), 0x266 setWriteCB, **0x384 htonl, 0x385 htons**.
- **0x68(0) = thoát chương trình** (game gọi ở mọi điểm thoát: 게임종료, xác thực lỗi); bản r1/r2 coi là stub nên chọn 게임종료 không thoát được, từ r3 = `knlExit`.
- Các import có trong bảng nhưng chưa thấy gọi khi chơi: 0xd5 DrawImage/0xe9 CreateImage (chỉ dùng cho ảnh `__rating/*` của máy, không có trong jar), 0xeb, 0x12c–0x12e, 0x130, 0x514, stdlib 0x3e9–0x461 (phần lớn chưa gọi; gặp hàm nào sẽ ném `unimplemented stdlib function`).

## B4. File/DB và lưu game
- Game đọc **mọi tài nguyên qua API db** (`MC_dbOpen("/c/csv/x.dat", 1)` ...), khoảng 150 file lúc khởi động. Bản LGT cũ của HL4 chép tài nguyên đóng gói vào storage khi mở → với HL5 sẽ ghi 2000+ file vào RMS. Sửa: tên `/c/...` mở mode 1 đọc thẳng từ jar (read-only, không đụng storage).
- File ghi được: `kickass` (save, khoảng 2.3 KB rỗng → 15 KB sau lần lưu đầu), `LOCAL.sav` (63 byte, tuỳ chọn/đồng ý SMS), `DEBUG.sav` (chỉ bản debug). Mode: 1 đọc, 2/4 tạo mới rỗng, 8 đọc/ghi. Cách làm giống KTF ui7: **một FileBuf chung mỗi tên, ghi xuống storage nguyên khối khi close** (+ `flushStorage()` khi pause/ẩn/thoát). 0x196 = xoá theo tên, 0x195 = stat(name, int[3], n) → {1,0,size}.
- Lưu nhanh = phím SEND (−10). Lưu qua menu CLR → 시스템 → 저장하기 → 예. Đã test lưu → khởi động lại → 게임시작 → slot 1 → "이대로 진행하시겠습니까?" chọn 예 (LEFT, OK) → vào đúng chỗ đã lưu.

## B5. Âm thanh
- `res/c/snd/bgm_00..20.mmf` (nhạc, score track) và `eff_00..20.mmf`. Hiệu ứng 00–18 là **MTR có stream PCM**: `Mtsp` → `Mwa1` (u8 kiểu 0x20 = Yamaha ADPCM 4 bit, u16 tần số 16000/8000 BE, dữ liệu), phát bằng một nốt kênh drum bank 0x7D. Converter cũ bỏ qua `Mtsp` nên ra MIDI câm. `Smaf.streamPcm/renderStream` giải mã (ADPCM nibble thấp trước, như ATR; đã kiểm tra: nibble cao → trôi DC và cắt) và trộn theo thời điểm nốt → WAV. eff_19/20 là ATR (WAV 8 kHz).
- `SndConvert5 <src> <out> /res/c/snd/`: 21 .mid + 21 .wav, stub `HL4SND\0/res/c/snd/x.wav` thay file .mmf; `Sound.preload` đọc `/res/c/snd/list.txt`. Game không đọc nội dung MMF (không có kiểm tra "MMMD").

## B6. Mạng / server giả (`Hl5Server`)
- 11532 (dịch vụ sao lưu save, **giao thức chữ**): `"LỆNH<space>SĐT<space>G1000157"`: IS_SAVEDATA_EXIST, START_SAVEDATA_UP, BLOCK_SAVEDATA_UP, BLOCK_SAVEDATA_DOWN_OK, DELETE_SAVEDATA, FINISH_SAVEDATA. Trả lời nhị phân `[kiểu][kết quả][len][chuỗi][...]` (0x2d64). Đang trả: IS_SAVEDATA_EXIST → `02 00 00 00 00000000` (không có save trên server), FINISH → `07 00 00`. Kết quả: lần đầu vào game (chưa có save) không còn popup "서버와의 접속이 원활하지 않습니다".
- 18182 (game server, nhị phân: `u32 len BE | "G1000157" | ... | "Emulator" | ... | "HERO5.ALL.SS.000"`) và 9093: sàn giao dịch 창고접속, xếp hạng, cập nhật... → **từ chối ngay** ở socketConnect (cb với −1): game báo "(300)" không phải chờ 1 s.
- Shop cash (RSK) của bản test này: giá 0원, mua là có ngay, không dùng mạng.

## B7. Hiệu năng
- Game đặt timer 5 ms sau mỗi khung (bảng 0x110830: 90..0 ms theo tuỳ chọn 게임속도, mặc định mức cao nhất) → cần giới hạn fps: `HL4-FPS: 25`.
- Tải: 2–3 triệu lệnh/khung khi đi lại, 6–8 triệu ở màn tiêu đề/nhiều hiệu ứng. Hàm vẽ sprite 0x8888c (nhiều độ sâu màu 4/8/12/16, lật, trộn alpha qua bảng) chiếm 26% (tiêu đề) – 64% (chiến đấu) số lệnh.
- AOT (`tools/regen_aot.sh`, profile tiêu đề + chiến đấu): 688 vùng, phủ 98% lệnh. Cải tiến aotgen:
  - `bl veneer` (`bx rM`) gấp luôn lệnh bx (không ra trình thông dịch);
  - `--hostcalls`: nếu rM là thunk import đã vá (`ldr ip,[pc,#4]; bx ip; ..; stub SVC`) thì gọi host trực tiếp trong vùng AOT (`Cpu.thunkHost/host`), icount cộng đúng như đường thông dịch (bl 2 + bx 1 + ldr 1 + bx 1, SVC 1 sau khi gọi);
  - `--maxbytes 7200`: ước lượng bytecode (0.28 × số ký tự Java) và cắt lệnh lạnh để **mọi method < 8000 byte** (HotSpot không JIT method lớn hơn; vùng r354 từng 9762 byte chạy thông dịch).
  - funcmin 20000 là tối ưu (3000 → 2009 vùng, chậm hơn 30% vì vào/ra vùng nhiều).
- Kết quả (PC, kịch bản chiến đấu 190 s): thông dịch 66 MIPS → AOT ~600 MIPS. **Bit-exact** với trình thông dịch: cùng icount (16139544310 / 4956124807) và 30/30 ảnh trùng; hash svc mỗi 100000 lời gọi trùng.
- Còn lại (JFR): truy cập bộ nhớ ~33%, thông dịch phần lạnh ~19%, vùng AOT ~30%. Hướng tiếp: HLE 0x8888c.

## B8. Phím
- WIPI: SEND = −10 (lưu nhanh, đã thử bằng `k-10`), CLR = −16 (menu trong game, quay lại), LSK = −6 (bản đồ), RSK = −7 (shop), # (đổi thanh phím tắt / bỏ qua cảnh), 7 (liên kỹ), 1/3/9/0/* (phím tắt), 2/4/6/8 + mũi tên (đi, bấm đúp = chạy), 5/OK.
- `GameCanvas.wipiKey`: MIDP −6/−21 → CLR (hoặc LSK nếu JAD `HL5-LSK: LSK`), −7/−22 → RSK, −8/8/27 → CLR, −10 → SEND. `pointerPressed`: vùng chữ "map"/"shop" dưới màn và góc trên trái.

## B9. Harness (`hl5j/test/src/RunHeadless.java`)
- AID/PID đọc từ `app_info`; `HL4_IMPORTS` in thunk đã giải; kịch bản `seq:` (xem README A); `HL4_SVCLOG=<bỏ qua n>` ghi 200000 lời gọi từ vị trí n + dòng `H` hash mỗi 100000 (so hai lần chạy để tìm chỗ lệch); `HL4_AOTSTAT=1` ghi số lần vào từng entry AOT (`aotstat.txt`).
- FreeJ2ME build từ nguồn (`freej2me_src/`, không đóng gói) + `hl5j/test/fj/FJTest.java`: chạy jar thật headless (không có thiết bị âm thanh nên log lỗi Clip/MIDI là bình thường).
- Đã chạy: title → tạo nhân vật → intro (bỏ qua bằng #) → hầm ngục; menu trong game (9 mục), hệ thống/lưu, cài đặt, trợ giúp, 달성과제, 익스퍼트, 게임문의 (gọi điện: không làm gì), shop cash; 3 phiên monkey × 300 s không lỗi.

## B10. HLE blitter sprite 0x8888c (r4)
- **Vấn đề:** hàm vẽ sprite 0x8888c chiếm ~60% lệnh khi combat (nhánh 4bpp chế độ 16), 14% ở màn tiêu đề (8bpp).
- **Cách làm (`hl5j/src/core/hl4/lgt/Hl5Blit.java`):** hook đầu 4 nhánh điểm ảnh sau phần chuẩn bị chung: 0x8947c (4bpp, chế độ 16 = thường), 0x89030 (8bpp chế độ 16; hook lệch 1 lệnh vì 0x8902e không chia hết 4), 0x88ec8 (8bpp chế độ 12 = xoay 180°), 0x891a8 (8bpp chế độ 8 = lật ngang). Java đọc khung stack của hàm (d4 ctx, dc x, d8 y, 144 w, 148 h, a8 imgW, ac fbW, b0 số byte/hàng 4bpp, b4 vị trí hàng nguồn, bc bảng màu, c4 fb, cc điểm ảnh, 104..106 byte tint, r10 alpha), vẽ đúng công thức pha màu (bảng `ctx+0x69`, màu trong suốt `[0x150bf7c]`), rồi nhảy về lối ra chung 0x889c6 (`r14 = 0x889c7`).
- **Đếm lệnh chính xác:** HLE cộng vào `icount` đúng số lệnh ARM mà nhánh gốc chạy (tính theo từng đoạn: đầu hàng, điểm ảnh trong suốt/không, có tint/không, cuối hàng). Nhờ vậy giờ ảo của harness không đổi và mọi kịch bản cũ cho **icount và ảnh giống hệt** bản không HLE.
- **Kiểm chứng:** `HL4_BLITCHECK=1` (chỉ trình thông dịch): mỗi lần blit, Java vẽ vào bản sao, khôi phục mã gốc và đặt điểm dừng ở 0x889c8, cho mã ARM chạy thật rồi so từng byte vùng fb và số lệnh. Kịch bản combat 70 s: 52.885 lần blit, 0 sai. Toàn bộ chạy HLE / không HLE / AOT+HLE: icount 5.890.436.994, 15 ảnh giống hệt.
- Tắt: `HL4_NOBLIT=1` (harness), JAD `HL5-BLIT: 0` (máy thật). AOT: `regen_aot.sh` loại các nhánh đã HLE (`--exclude`) và thêm profile `hl5_combat2_hle.txt`.
- Kết quả: combat nhanh hơn ~1,5 lần (145 → 95 ms thật cho ~30 khung). Còn lại đáng kể: 0x879d8 (blitter khác, 17%), 0xa998c (13%).

## B11. Việt hoá HL5 (engine chữ + dữ liệu, thư mục `vi5/`)
- **Engine chữ gốc** (module C -O0 ở 0x74a7c–0x76xxx, đối tượng font 0x2008f88): độ rộng cố định lấy từ byte font `+0x46` (Hangul, 9 → bước 10 px) và `+0x47` (ASCII, 5 → 6 px); 11 mã màu ở `+0x18`: `& | $ { } ' " ` ^ < [`; `;` xuống dòng. Hàm: 0x74c14 đo, 0x74d38 tự ngắt dòng theo bề ngang, 0x7540c vẽ chuỗi (gọi vẽ glyph qua con trỏ 0x5b51 → 0x5b50 → 0x4f60(x, y, str, len)); 0x4c84(x, y, màu, mode) = đặt điểm ảnh (có clip, blend).
- **Cách làm (`Hl5Text.java`):** từ tiếng Việt = mã 2 byte (lead 0xC9.., trail 0xA1..) trỏ vào `vi5/dict.txt`, nên mọi chỗ "byte ≥ 0x80 = ký tự 2 byte" vẫn đúng. Chỉ thay 2 thứ:
  - 5 chỗ đọc độ rộng Hangul `adds r3,#0x46; ldrb r3,[r3]` (0x74c7e, 0x74da4, 0x7551e, 0x757c8, 0x75888) được vá thành `bl` tới stub 0x11f000 (vùng trống trong trang .text); HLE lấy con trỏ ký tự từ stack của từng hàm và trả bề rộng thật của từ (font 15px) − 1.
  - lời gọi vẽ glyph ở 0x5be4 → stub 0x11f008: từ tiếng Việt thì vẽ bằng VnFont qua 0x4c84 (màu `[cur+0x40]`, mode `[cur+0x24]`, cur = `[0x15000dc]`), còn lại gọi 0x4f60 gốc.
  - Bố cục, ngắt dòng, căn giữa, màu vẫn do game làm. Số, dấu câu, dấu cách vẫn vẽ bằng font ASCII gốc (6 px).
  - AOT: các điểm vá đã `--exclude` trong `regen_aot.sh`. Kiểm tra: chạy Việt hoá với và không AOT cho ảnh giống hệt; bản không Việt hoá vẫn bit-exact.
- **Dữ liệu:** `*.dat` = u16 số record + (u16 độ dài + record); chuỗi trong record có tiền tố u8 hoặc u16 (help). Vị trí chuỗi tìm bằng quy hoạch động chọn cách tách phủ nhiều chữ Hàn nhất (tránh nhận nhầm byte số). `*.scn`: bảng chuỗi ở cuối file = u8 số chuỗi + u8 độ dài (gồm NUL) + dữ liệu, tra theo chỉ số (parser 0xd2bd0), nên viết lại cả bảng tự do. Binary: 18 tên loại vật phẩm (ô 256 byte ở 0x11452a) + thông báo DRM 0x110250 (vá tại chỗ).
- **Công cụ:** `extract5.py` → `catalog5.json`; `export5.py` → `HL5_dich.xlsx/.tsv` (câu không trùng, cột "Rộng dòng gốc px", "Giới hạn byte"); `build_vi5.py` → overlay + báo cáo (quá dài, lệch mã điều khiển, dòng tràn, ký tự font thiếu; từ điển tối đa 5.076 từ).
- Đã test bằng 16 câu mẫu (màn thông báo, đồng ý, menu chính, chọn ô, chọn lớp) trong harness và FreeJ2ME (`HL5_LGT_r4_videmo`).

## B12. Ảnh `.mgr` và thay logo NSX (r5)
- `.mgr` = u32 số ảnh + mỗi ảnh (u32 kích thước + [u8 fmt: bpp ở 4 bit thấp (4/8), 0x10 = có trong suốt][u8 số màu][u16 w][u16 h][u16 bảng màu RGB565][điểm ảnh; 4bpp mỗi hàng đủ byte, nibble cao trước]); màu 0xF81F trong suốt. `.cif` = kịch bản khung hình, ảnh vẽ **căn giữa** theo điểm neo.
- Logo EA: `res/c/sp/imgcom/ealogo.mgr` (3 khung mờ dần, `ealogo.cif`). Thay bằng ảnh 240x320 của người dùng ở 3 mức sáng 35/70/100% (giữ hiệu ứng hiện dần), 8bpp ≤255 màu (`img5/mk_ealogo.py`). Ảnh căn giữa màn hình đúng như logo cũ, đã kiểm tra trong harness và FreeJ2ME.
- Tìm file ảnh bằng `HL4_RESLOG=1` (log từng file tài nguyên được đọc, kèm thời điểm).
- **Logo tiêu đề (r6):** `img5/mk_title.py` vẽ bằng bộ pixeltext của người dùng (`img5/pixeltext-kit`, font 15px, x2 cho tên game) rồi đóng vào `title.mgr`: ảnh 5/6 (영웅서기, tâm neo (80,134)) = "Heroes Lore" (chữ V giữ nguyên); ảnh 14 (chữ 칠 đầu tiên, tâm (86,155)) = "Covenant of Darkness" + "Việt hoá bởi NAM.P"; ảnh 15–18 (các chữ còn lại) = ảnh trong suốt 1x1. Vì ảnh vẽ căn giữa theo điểm neo, nội dung được đặt đúng chỗ bằng cách đệm ảnh đối xứng quanh điểm neo (không phải sửa `.cif`).
- Lưu ý khi test: nếu không bấm phím, sau logo game chạy đoạn mở đầu (prologue) khoảng 30 s rồi mới tới màn tiêu đề.
- **Ảnh có chữ Hàn khác (r7):** khảo sát toàn bộ `.mgr` (`img5/sheet.py` = ảnh ghép, lọc thêm ảnh dẹt dạng chữ trong `sp/`). Chỉ có: `img/menu.mgr` 12 (12세이용가 → "Từ 12 tuổi"), 13 (사행성 → "May rủi"), 14–17 (các dòng chữ đoạn mở đầu), 18 ("이 세계에 그런 신은 없다" → "Thế giới này / không có vị thần nào như thế."); `img/gmenu.mgr` 38/39 (dòng nhỏ 공략 성공!/실패! dưới Success/Failed → "Thành công!"/"Thất bại!"). Ảnh trong `sp/` chỉ có chữ tiếng Anh. Vẽ bằng `img5/mk_images.py` (pixeltext 15px, riêng dòng Success/Failed 10px); câu đoạn mở đầu do mình dịch theo `vi/tr/thuat-ngu.tsv` (Cổ tộc, Thánh Địa, quần đảo Orbis), sửa trực tiếp trong script. Vị trí đo bằng khớp mẫu trên ảnh chụp; đoạn mở đầu viết lại 15px nên dòng đầu thành 2 dòng.
- `mgr_replace.py`: ảnh 8bpp tối đa 254 màu + 1 màu trong suốt (byte số màu ≤ 255).

## B10b. HLE blitter thứ hai 0x879d8 (r7)
- Blitter chép có màu trong suốt, không pha màu (0x879d8). Hook 4 nhánh: 0x87c68 (8bpp thường; lệch 1 lệnh sau 0x87c66), 0x87c04 (8bpp lật ngang), 0x87dbc (4bpp thường), 0x87e94 (4bpp lật ngang); lối ra chung 0x87acc. Tham số lấy từ thanh ghi (sb = đầu hàng nguồn, sl = gốc điểm ảnh, r8 = bảng màu, r4 = sx) và stack (40 fb, 8c w, 90 h, 48 y, 4c x, 34 fbW, 30 imgW, 38 byte/hàng 4bpp).
- Đếm lệnh chính xác như B10; chế độ `HL4_BLITCHECK` mở rộng cho blitter này (điểm dừng tại 0x87acc). Combat 70 s: 118.925 lần blit (cả hai hàm), 0 sai; menu/tiêu đề: 48.119 lần, 0 sai; icount toàn bộ vẫn 5.890.436.994, ảnh giống hệt.
- Với AOT, tốc độ gần như không đổi (hàm này vốn gọn); lợi ở chỗ bỏ được mã AOT của các vòng lặp này. `Memory.copy` chuyển sang chép theo trang bằng `System.arraycopy`.

## B13. Server game giả (18182/9093) (r7)
- Gói tin: [u32 BE tổng độ dài]["G1000157"][u32 major][u32 minor][trường BE]; chuỗi = u32 độ dài + byte. Game kiểm tra độ dài gói = số byte đọc được, nên **mỗi lần đọc chỉ trả một gói** (`Hl5Server.poll`, `LgtRuntime.netDeliver`).
- Luồng mạng của game: `0x38874(ctx, op)` (ctx = `[0x150bf80]`, op lưu ở `ctx+0x344`) → kết nối → gửi đăng nhập (1,1). Phản hồi xử lý ở 0x37cc4 (major 1): minor 1 = đăng nhập {kết quả, chuỗi, thời gian}; kết quả ≠ 0 → hiện chuỗi của server làm thông báo. Nếu chưa có mã thành viên (`0x150a570+0xc..0x18` = 0) game gửi (1,5) đăng ký {kết quả, chuỗi, u64, u64}; sau đó theo op: op1 → (1,2) đồng ý SMS {kết quả, chuỗi} → hiện "수신 동의가 완료/거부되었습니다" và lưu.
- **Màn đồng ý SMS (op 1) mỗi lần khởi động** trước đây luôn báo "서버상태가 좋지 않습니다(-1)" và không lưu. Nay trả lời thành công cả 3 bước, câu trả lời được lưu nên lần sau không hỏi lại. (0,2) là gói "tạm biệt" của máy khách, không trả lời.
- **Các op khác** (kho mạng 창고접속, xếp hạng, giao dịch…): trả lời đăng nhập thất bại kèm thông báo offline. Bản Hàn: "오프라인 버전에서는;지원하지 않는 기능입니다." (byte EUC-KR nhúng sẵn, không phụ thuộc bảng mã của máy). Bản Việt: `build_vi5.py` mã hoá "Bản offline: không hỗ trợ;tính năng cần máy chủ." thành `vi5/offline.bin`, GameCanvas và harness tự nạp.
- Debug: `-Dhl5.ports=<cổng>` cho server giả nhận thêm cổng.

## B14. Bản offline: hồi sinh không cần mạng, menu chính bỏ mục mạng (r8, `Hl5Offline.java`)
- Cài trong `LgtRuntime.start()` (luôn bật). Kiểm tra mã gốc tại từng chỗ vá trước khi vá (sai thì báo lỗi ngay).
- **Hồi sinh:** màn chết (hàm phím 0x4bd0c, trạng thái 1) chọn "예" → đếm vật phẩm cash (0x12, 0x17) qua 0x10e224; có thì xoá 1 cái (0x4bdba) rồi hồi sinh (0x4bdbe..0x4bdea, setState 0 = `0x4b7a4(ctx,0)`); không có thì gọi mua qua mạng op 5 (`0x38874`) → lỗi mạng. Sửa: 0x4bdf0 = `b 0x4bdbe` (0xE7E5): nhảy vào nhánh "có vật phẩm" ngay sau lệnh xoá vật phẩm. Nhảy thẳng tới 0x4bde4 (chỉ setState 0) KHÔNG đủ: game đứng ở hộp thoại, vì hai lời gọi 0x4bdc6/0x4bde0 mới là phần hồi máu.
- Chữ trong hộp thoại (bản Việt, `vi5/override.tsv`): K00412 "Hồi sinh bằng Bùa Hồi Sinh?", K00416/K00467 "Giá gốc 100 won. Bản offline: miễn phí.", K00441 "Chọn Có để hồi sinh ngay tại chỗ." (không dùng dấu `"` vì là mã màu).
- **Menu chính 3x3 → 2x3** (bỏ 2 창고접속, 3 업데이트, 7 게임문의). Hàm vẽ 0x50170: vòng hàng `cmp r3,#2` (0x501ba) → `#1`; gốc y `subs r3,#0x63` (0x50194) → `#0x46` (xuống nửa ô cho cân). Ba chỗ tính chỉ số ô `adds r3,r2,r3` (r2 = hàng*3, r3 = cột) thay bằng BL tới stub 0x11f010 (HLE 102): 0x5025c (+ `cmp r1,r3`: Java đặt cờ NZCV) và 0x5036c/0x504c4 (+ `adds r3,#0xa6`: chỉ số chữ). Ô p hiện mục `ITEMS[p]` = {0,1,4,5,6,8}. Hai chỗ `cmp r3,#7` không cần sửa (vị trí 0..5).
- Byte chọn `[menu+0xe]` vẫn là số mục gốc nên hàm OK (0x513b8) không đổi. Phím: switch ở 0x512e0 (`lsls r2,r1,#2; ldr r3,=0x11297c`, literal ở 0x5154c) thay bằng BL tới stub 0x11f018 (HLE 103): Java đọc đích trong bảng nhảy; nếu là UP (0x512ea) / DOWN (0x51316) / LEFT (0x51342) / RIGHT (0x5137c) thì tự di chuyển trong lưới 2x3 (quay vòng) rồi về 0x5153a; còn lại chạy lệnh gốc và tiếp tục ở 0x512e4.
- AOT: thêm `--exclude 4bdf0-4bdf2 50194-50196 501ba-501bc 5025c-50260 5036c-50370 504c4-504c8 512e0-512e4`. Kiểm tra: kịch bản chết + hồi sinh (`profiles/keys_die.txt`, 44 s) AOT và thông dịch cùng 3.662.071.584 lệnh, ảnh trùng từng byte.
- Test: menu `seq:u:OK.1500.42.99.de7508;1000:RIGHT;...` với `-Dhl5.navlog=1` in từng phím; chết: `HL4_POKE="36500:215d618=1"` (bản Hàn, `profiles/keys_die.txt`) rồi LEFT + OK ở hộp thoại → nhân vật đứng dậy đủ máu, không có gói mạng. Bản Việt địa chỉ máu khác (heap khác), tìm bằng mẫu byte quanh 0x215d618 trong dump `HL4_DUMPMEM` (script `hl5j/tools/die_vi.sh`, kịch bản `hl5j/profiles/keys_die.txt`; cần dump bản Hàn `$S/dk/mem.bin` trước: `HL4_DUMPMEM=36400 HL4_AOT=1 tools/run.sh dk 36500 5000 "$(cat profiles/keys_die.txt)"`).

## B15. Việt hoá đầy đủ (r8_VI)
- Bản dịch người dùng: `vi5/tr_in/` (HL5_dich_FULL.tsv 10.131/10.133 câu, thuật ngữ, báo cáo dịch). Build: 13.645 vị trí thay, 0 câu quá dài, từ điển 4.234/5.076 mã.
- `build_vi5.py` thêm: (1) nhiều file dịch, file sau đè file trước; (2) `menu_override.tsv` (File, Tiếng Hàn, Tiếng Việt) đè theo từng file, dùng cho nhãn menu chính ô 48 px (Bắt đầu, Chuyên;gia, Thành;tựu, Thoát) mà không đổi chữ trong trợ giúp; (3) tự ngắt dòng cho câu gốc nhiều dòng (giới hạn = dòng gốc rộng nhất + 4 px, tối thiểu 60).
- `override.tsv`: màn hướng dẫn/đồng ý thông tin lúc khởi động (K00168, K00172–K00176) viết ngắn lại cho vừa hộp cố định (các chuỗi này xếp chồng nhau theo dòng, tự ngắt làm lệch); chỉ số nhân vật K00201–K00204 → STR/AGI/VIT/INT (ô 20 px); chữ hộp thoại hồi sinh (B14).
- Đã chụp kiểm tra: màn khởi động, menu chính, chọn lớp, nhiệm vụ đầu, menu trong game, màn nhân vật, hộp thoại hồi sinh. Còn thấy: tên vùng trên bản đồ thế giới chồng lên nhau (nhãn đặt toạ độ cố định, tên dịch dài).

## B16. Dò và sửa chữ tràn khỏi ô (r9_VI)
- **Máy dò (`hl5j/test/src/OvfDetect.java`, harness `HL4_OVF=1|base`, chỉ chạy thông dịch):** mỗi lần game gọi hàm vẽ chuỗi 0x7540c (r2 = x, r3 = y, [sp] = bề rộng ngắt dòng, [sp+4] = chuỗi, [sp+16] = độ dài, [sp+20] = căn lề 0/1/2) thì chụp màn hình trước khi vẽ và ghi mọi điểm ảnh chữ định vẽ (các lời gọi 0x444c). Biến toàn cục của 0x444c: con trỏ chiều rộng/cao màn hình ở literal 0x4544/0x4548, khung clip ở 0x454c, bộ đệm ở 0x4554. Chữ bị đánh dấu khi:
  - `clip`: điểm ảnh bị cắt hoặc ra ngoài màn hình;
  - `v<cột>`/`h<hàng>`: một đường viền của nền (cột hoặc hàng khác màu với cột/hàng bên cạnh trên ≥ 90% chiều cao/rộng chữ) cắt ngang chữ;
  - `cover`: tới khung hình kế tiếp, một phần chữ bị thứ vẽ sau che (ô giá trị, nút). Giữ tỉ lệ nhỏ nhất qua các khung;
  - `ovl`: hộp chữ chồng lên hộp chữ khác trong cùng khung (bỏ qua chữ bị popup che trên 30%).
- **So với bản Hàn:** kịch bản chạy bản Hàn với `HL4_OVF=base` để làm mốc cho từng vị trí vẽ (hàm gọi:x:y:căn lề). Bản Việt chỉ báo những gì bản Hàn không có: viền nằm ngoài phạm vi chữ Hàn, clip/cover/ovl mới. Mỗi lần chạy còn ghi `draws.txt` (mọi chuỗi đã vẽ, vùng trống quanh điểm neo) và bề rộng ô ước tính `fit=` trong `ovf.txt`.
- **Kịch bản đã chạy (`hl5j/profiles/ovf/k*.txt`, chép vào `$S` trước khi chạy; save dùng chung `hl5j/profiles/ovf/save_mk11` → `$S/save_mk11`, ~17 kịch bản):**
  - menu chính và Cài đặt/Trợ giúp/Thành tựu/Chuyên gia;
  - chơi mới, chọn lớp, đi ngẫu nhiên 260 phím từ đầu game;
  - từng tab trong game: nhân vật, túi đồ, trang bị, kỹ năng, đặc tính, nhiệm vụ, hệ thống;
  - cửa hàng, bản đồ, toàn bộ trang trợ giúp, danh sách thành tựu;
  - 2 lần đi phím ngẫu nhiên.
- **Cách sửa, do `build_vi5.py` áp theo `vi5/fit.tsv` (ID hoặc nhóm `G:<file bỏ số>.f<trường>`, bề rộng px, chế độ):**
  - `line`: chữ rộng hơn ô thì dùng dạng nén (font 15px, bớt 1 px mỗi chữ, dấu cách 3 px); vẫn không vừa thì dùng font 10px (`vi5/font/font_10px_v2.bin`, có dấu chữ hoa, cũng bớt 1 px); vẫn không vừa thì báo `CẦN RÚT GỌN`.
  - `small`: luôn dùng 10px, để các ô của cùng một menu/thanh tab trông đồng đều.
  - `wrap`: xuống dòng khi dạng nén không vừa một dòng.
  - `rewrap`: bỏ các dấu `;` rồi ngắt lại theo bề rộng ô (ô game tự ngắt dòng, tránh dòng mồ côi).
  - `soft`: nén nếu giúp được nhưng không báo.
- **Engine chữ:**
  - Mục từ điển có tiền tố `\x01` là dạng nén, `\x02` là font 10px. Dấu cách trước từ được gộp vào mục (3 px).
  - `Hl5Text` đo và vẽ theo tiền tố. Font 10px nạp từ `/vi5/font10.bin` (GameCanvas, và harness đọc từ thư mục `HL4_VI`).
  - Không đổi bố cục của game: bề rộng trả về cho hàm đo đã là bề rộng nén.
- **Câu rút gọn (`vi5/override.tsv`):**
  - Nhãn menu chính: Bắt đầu, Thoát.
  - Cài đặt: Âm hiệu ứng, Hiệu ứng.
  - Tab: Phòng, Nhánh truyện.
  - Nhãn chỉ số: ATK cận chiến / ATK tầm xa / ATK phép, DEF vật lý / DEF phép, Trúng, Né, Chính xác, STR/AGI/VIT/INT.
  - Túi đồ: 0.Sắp xếp, Cần cấp, Gán phím tắt.
  - Bản đồ: #Bản đồ lớn, Ngầm Central, Coldcreek.
  - Trang Thông tin thẩm định: Độ tuổi, Ngày cấp, Công ty, Mã số, Số đăng ký; tên game và tên công ty 2 dòng.
  - Tiêu đề trợ giúp: Tinh Linh/Cơ Bản.
  - Hộp thoại mua (dùng chung cho hồi sinh và cửa hàng): "Giá gốc N won. / Bản offline: miễn phí."
- **Tự ngắt dòng** (mọi câu gốc nhiều dòng, trừ câu có trong fit.tsv): ngắt lại theo dòng gốc rộng nhất. Ở ô game tự ngắt dòng, việc này tránh dòng chỉ có một từ.
- **Kết quả lần chạy cuối:** chỉ còn các cảnh báo sai (chữ 15px cao hơn chữ Hàn nên chạm vạch ngang trong nút/thanh tiêu đề; popup trong suốt đè tiêu đề; "???" do game tự vẽ) và tên thành tựu dài bị cắt ở dòng không chọn. Bản gốc cũng cắt các tên này; dòng đang chọn tự chạy chữ.
- **Giới hạn:**
  - Màn chưa tới được (thị trấn/NPC, rèn, kho, sự kiện sau này) chưa dò. Câu thoại cốt truyện không cần sửa: hộp thoại tự ngắt dòng và tự sang trang, đã thử ở đoạn mở đầu.
  - Save tạo bằng bản Hàn còn lưu sẵn tên nhiệm vụ đang làm bằng tiếng Hàn ("지하도 청소", "메인 퀘스트"); chơi mới thì hiện tiếng Việt.

