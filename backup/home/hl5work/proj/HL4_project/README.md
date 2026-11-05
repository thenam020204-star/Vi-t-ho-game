# Dự án port game WIPI → J2ME: ĐỌC FILE NÀY TRƯỚC (hướng dẫn cho phiên sau)

Gói này có **2 game** dùng chung một engine (trình thông dịch ARM + HLE WIPI-C viết bằng Java):
- **HL5 – 영웅서기5: 칠흑의 계약** (Heroes Lore 5, bản LGT, 2011): thư mục `hl5j/` (engine copy riêng), `hl5/tools/`, `make5.sh`, `HL5.zip`, `release5/`, `vi5/` (Việt hoá). **Đã Việt hoá đầy đủ (bản dịch 100% của người dùng, r8_VI).** Xem mục A ngay dưới.
- **HL4 – 영웅서기4** (KTF + LGT, đã Việt hoá): `hl4j/`, `vi/`, `img/`, `make.sh`, `release/`. Xem phần "HL4" phía sau. **Không sửa `hl4j/` khi làm HL5** (hai nhánh tách hẳn).

## A. HL5 (Heroes Lore 5, LGT)
- **Bản chuẩn hiện tại: `release5/HL5_LGT_r9_VI.jar/.jad`** (= r8_VI + sửa chữ tràn khỏi ô: máy dò tràn chữ, chữ nén/10px theo `vi5/fit.tsv`, câu rút gọn trong `vi5/override.tsv`; báo cáo B16). r8_VI (= r7 + Việt hoá đầy đủ từ `vi5/tr_in/HL5_dich_FULL.tsv` + `vi5/override.tsv` + `vi5/menu_override.tsv`; hồi sinh khi chết không cần mạng (miễn phí); menu chính bỏ 창고접속/업데이트/게임문의, còn lưới 2x3; báo cáo B14). r7 (= r6 + ảnh có chữ Hàn vẽ lại tiếng Việt, blitter thứ hai HLE, server giả cho màn đồng ý SMS và thông báo offline cho dịch vụ mạng). r6 (= r5 + logo tiêu đề vẽ lại bằng pixeltext: "Heroes Lore V / Covenant of Darkness / Việt hoá bởi NAM.P", `img5/mk_title.py`). r5 (= r4 + logo NSX EA thay bằng logo "Việt hoá bởi NAMP" của người dùng; `img5/mk_ealogo.py`, overlay `img5/overlay`, make5.sh tự chép). Trước đó: `HL5_LGT_r4` (tiếng Hàn, gửi dạng zip). r1 = bản chạy đầu tiên; r2 = server giả offline + AOT; r3 = sửa 게임종료 (0x68 = thoát); r4 = blitter sprite viết lại bằng Java (combat nhanh ~1,5 lần) + AOT mới. `HL5_LGT_r4_videmo` = r4 + Việt hoá thử 16 câu (menu chính, màn thông báo) để xem font.
- Gốc: `HL5.zip` (AID 00032870, PID PD151088, `binary.mod` ELF ARM, `res/c/...` 2140 file). Đây là **bản test của EA (MIDAS test phone)**: có màn DRM kiểm tra AID và **hạn dùng 2011-02-23** (giờ ảo/thật đều qua được vì runtime trả AID đúng; xem báo cáo B2). Shop cash của bản này **tự miễn phí, không cần mạng**.
- Build: `./make5.sh [hậu tố]` → `dist5/HL5_LGT<hậu tố>.jar/.jad` (tự bung HL5.zip, biên dịch harness, chuyển âm thanh SMAF, build jar). Phát hành: copy sang `release5/HL5_LGT_rN.jar`, sửa `MIDlet-Jar-URL` trong .jad cho khớp tên, zip jar+jad.
- Chạy headless: `hl5j/tools/run.sh <tên> <tổng ms> <chu kỳ chụp ms> "<phím>" [thư mục storage nguồn] [ảnh đầu] [số ảnh]` → `$S/<tên>.png` (ảnh ghép), `$S/<tên>.log`, `$S/<tên>/storage` (S = scratchpad). Bật AOT: `HL4_AOT=1`.
  - Kịch bản phím dạng `seq:`: `D:KEY` (chờ D ms rồi bấm), `u:KEY.chu_kỳ.x.y.rrggbb` (bấm lặp tới khi điểm ảnh khớp màu), `w:x.y.rrggbb` (chờ màu), `s:tên` (chụp tên.png), `D` (chờ). Tên phím: UP DOWN LEFT RIGHT OK LSK RSK CLR 0-9 # * hoặc `k<mã>` (vd `k-10` = SEND).
  - Tới menu chính: `seq:u:OK.1500.42.99.de7508` (nút 게임시작 màu cam; từ r8 menu 2x3 dịch xuống 29 px nên điểm đo là y=99, các profile đã sửa). Kịch bản chiến đấu dùng làm profile: `hl5j/profiles/keys_combat1.txt`.
- Phím (GameCanvas): **phím mềm trái = CLR (menu/quay lại)**, phải = RSK (shop cash). Gốc: LSK = bản đồ ("map"), SEND (-10) = lưu nhanh, CLR = menu. JAD `HL5-LSK: LSK` để trả phím trái về LSK gốc. Chạm màn hình: góc dưới trái = LSK(map), dưới phải = RSK(shop), góc trên trái = CLR.
- FPS: manifest `HL4-FPS: 25` (game tự đặt timer 5 ms, không tự giới hạn; trong game còn tuỳ chọn 게임속도).

| Việc HL5 | Sửa ở đâu |
|---|---|
| API WIPI-C LGT (đồ hoạ, db/file, mạng, thời gian) | `hl5j/src/core/hl4/lgt/LgtRuntime.java` |
| Server giả offline | `hl5j/src/core/hl4/lgt/Hl5Server.java` (+ `LgtRuntime.net*`) |
| Phím / RMS / vòng lặp MIDP | `hl5j/src/midp/hl4/midp/GameCanvas.java` |
| Âm thanh | `hl5j/test/src/SndConvert5.java` (SMAF → .mid nhạc / .wav hiệu ứng), `core/Smaf.java` (thêm stream PCM `Mtsp/Mwa`), `midp/Sound.java` |
| AOT | `hl5j/tools/regen_aot.sh` (profiles `hl5j/profiles/hl5_*.txt`), `hl5j/tools/aotgen.py` |
| Tra mã ARM | `python3 hl5/tools/img.py dis <từ> <đến>` / `str <chuỗi>` / `xref <hex>`; `import img; img.imports()` (thunk import → (bảng, hàm)) |

**Lưu ý gói dự án:** gói tải lên ngày 2026-09-27 (bản j) **thiếu `vi/build_vi.py`** (quy tắc đóng gói cũ "bỏ `vi/build*`" xoá nhầm cả file này), nên `./make.sh` của HL4 hiện không chạy được. Khi cần build lại HL4: lấy overlay tiếng Việt từ jar chuẩn `release/` (các file khác bản gốc: `vi/f10.bin`, `vi/dict.txt`, dữ liệu đã dịch) hoặc viết lại `build_vi.py` theo báo cáo §3. Từ nay đóng gói chỉ bỏ **thư mục** `vi/build*` (`find vi -maxdepth 1 -type d -name 'build*'`).

### Việt hoá HL5 (thư mục `vi5/`, báo cáo B11)
- Người dùng **tự dịch**. Đã gửi `HL5_bo_dich.zip` = `HL5_dich.xlsx/.tsv` (10.133 câu không trùng, ID `K00001`…) + `HUONG-DAN-DICH-HL5.md`. Font: `vi5/font/font_15px.bin` (fontcus 15px, giống HL4).
- `vi5/extract5.py` → `catalog5.json` (13.647 vị trí: csv/csv2 `*.dat`, bảng chuỗi cuối `map/*.scn`, 19 chuỗi trong binary). `vi5/export5.py` → `out/HL5_dich.{xlsx,tsv}` + `ids5.json`.
- Gói dự án không kèm `catalog5.json`, `ids5.json`, `out/` (tạo lại: `python3 vi5/extract5.py && python3 vi5/export5.py`, ID giữ nguyên vì thứ tự cố định) và jar `r4_videmo` (tạo lại: `python3 vi5/build_vi5.py vi5/tr_test.tsv && VI=1 ./make5.sh _r4_videmo`).
- Build bản Việt hiện tại: `python3 vi5/build_vi5.py vi5/tr_in/HL5_dich_FULL.tsv vi5/override.tsv && VI=1 ./make5.sh _rN_VI` (file sau đè file trước; `override.tsv` = câu sửa cho bản offline/ô hẹp; `fit.tsv` = bề rộng ô đo được cho từng ID hoặc nhóm `G:<file>.f<trường>`, chế độ line/wrap/rewrap/small/soft, build tự nén chữ hoặc báo `CẦN RÚT GỌN` trong `vi5/build/bao-cao-build.txt`).
- **Dò chữ tràn (B16):** `S=<scratch> hl5j/tools/ovf_batch.sh <tên kịch bản>...` chạy từng kịch bản `$S/k<tên>.txt` hai lần (bản Hàn làm mốc, bản Việt để so) với `HL4_OVF`; `hl5j/tools/ovf_report.py $S` gom kết quả vào `$S/ovf_all.tsv`, `ovf_sheet_all.py` làm ảnh ghép. Tạo kịch bản: `tools/kseq.py` (dạng gọn), `tools/krand.py` (đi phím ngẫu nhiên). `SKIPK=1` để không chạy lại bản Hàn. Tổng quát: `python3 vi5/build_vi5.py <file.xlsx|tsv>...` → `vi5/build/overlay` + `vi5/build/bao-cao-build.txt` (câu quá dài, lệch mã điều khiển, dòng rộng hơn gốc, ký tự font thiếu). Rồi `VI=1 ./make5.sh _rNvi`.
- Test headless: `RES=vi5/build/overlay HL4_VI=vi5/build/overlay/vi5 hl5j/tools/run.sh ...`.
- Ảnh: `img5/mgr.py <file.mgr> <thư mục>` (mgr → png), `img5/mgr_replace.py <in> <out> <số>=<png>...` (png → mgr, giữ bpp). Đã gửi `HL5_logo_de_ve.zip` (ảnh title.mgr: 005/006 = 영웅서기, 014–019 = 칠흑의 계약) để người dùng vẽ lại logo tiếng Việt; logo 828 (828_lg.mgr) chưa thay.
- Tự ngắt dòng (`AUTOWRAP` trong build_vi5.py): câu gốc có nhiều dòng (`;`) thì dòng tiếng Việt dài hơn độ rộng dòng gốc lớn nhất được ngắt thêm theo từ. Hộp chữ xếp chồng nhiều chuỗi (màn hướng dẫn, đồng ý thông tin) thì phải viết câu ngắn trong `override.tsv` thay vì để tự ngắt.
- Việc còn lại: các màn máy dò chưa tới được (cần save xa hơn: thị trấn, NPC, rèn/chế tạo, kho, sự kiện); tên thành tựu dài vẫn bị cắt ở dòng không chọn (bản gốc cũng vậy, dòng đang chọn tự chạy chữ); chữ trong ảnh 12세이용가 chưa làm.

Tồn đọng HL5: (1) Việt hoá: dò tràn chữ ở các màn chưa tới (xem B16), các điểm cần người dùng xác nhận trong `vi5/tr_in/bao-cao-dich.md`; (2) chưa nghe thử âm thanh trên máy thật; (3) hiệu năng: 0xa998c (máy biểu thức -O0, ~13% lệnh khi combat) vẫn chạy AOT, chưa HLE.
Đã xong phiên 2026-09-28: r9_VI – dò và sửa chữ tràn khỏi ô (B16). Phiên 2026-09-27 (tối): r8_VI – Việt hoá đầy đủ, hồi sinh offline, gỡ mục mạng khỏi menu chính (`Hl5Offline`, B14). Trước đó: blitter thứ hai 0x879d8 HLE (B10b); server game giả cho màn đồng ý SMS lúc khởi động (hết popup lỗi (-1), đồng ý được lưu, lần sau không hỏi lại) và mọi dịch vụ mạng khác trả thông báo "bản offline không hỗ trợ" (B13); `Memory.copy` dùng arraycopy.

---

# HL4 Việt hoá

Game: **영웅서기4 – 환영의 가면** (Heroes Lore 4, EA Mobile Korea 2010), bản WIPI của KTF và LGT.
Sản phẩm: file jar J2ME MIDP 2.0 (240x320) **chạy nguyên mã ARM gốc** trong trình thông dịch ARM viết bằng Java, kèm các dịch vụ WIPI-C được giả lập bằng HLE, và **đã Việt hoá toàn bộ text**.

## 0. Quy ước với người dùng (Mr NAM), BẮT BUỘC
- **Chỉ gửi file .zip** cho người dùng, không gửi jar/jad rời (đóng jar + jad vào một zip rồi gửi).
- Trả lời bằng tiếng Việt, ngắn gọn.
- **Bản chuẩn hiện tại là `release/HL4_KTF_VI_ui7.jar`** (ui5 + sửa lỗi mất save khi save lớn lên: db 6 xoá/7 đổi tên, tự khôi phục từ file `chicken`; RMS xoá store cũ rồi ghi mới; dòng chẩn đoán lưu bật bằng `HL4_SAVEDBG=1 ./make.sh KTF 15`): bản KTF, font **15px**, phím mềm trái = CLR/Quay lại, **giới hạn 27 fps + AOT mở rộng**, **bỏ phần online: server giả lập offline, cửa hàng cash miễn phí, DES chạy trên host**, **logo NSX = ảnh NAMP, màn Nhân vật mới toàn màn hình, ảnh tiêu đề Heroes Lore 4 của người dùng (img/src/title.png, nguồn title_user_src.png, `img/prep_title.py` cắt + chỉnh màu theo ảnh gốc), âm thanh đã chuyển sẵn sang .mid/.wav thuần J2ME, giải mã ADPCM chuẩn ffmpeg, hệ thống âm thanh pool/preload không trễ, lưu game ghi nguyên khối khi đóng file + RMS có bản dự phòng**. Bản trước đó (fixCLR) được người dùng đánh giá "chạy oke nhất". Mọi sửa đổi sau này lấy bản mới nhất làm gốc.
- Khi người dùng nói "chỉ fix X" thì chỉ đổi đúng X, không đổi gì khác.

## 1. Khôi phục môi trường (khoảng 1 phút)
```bash
mkdir -p /home/claude/work && cd /home/claude/work
unzip -q <gói>.zip -d /tmp/p && cp -r /tmp/p/HL4_project/. /home/claude/work/   # thư mục gốc gói = /home/claude/work
./setup.sh          # bung LGT.zip/KTF.zip -> lgtjar/ ktfjar/, biên dịch hl4j/build/test2 (harness headless)
./make.sh KTF 15    # overlay tiếng Việt + jar  -> dist/HL4_KTF_VI.jar/.jad   (all = cả KTF và LGT)
```
Yêu cầu: JDK 11+ (có `javac`, `jar`) và python3. Các script dùng **đường dẫn tuyệt đối `/home/claude/work`**, nên phải đặt đúng chỗ đó.
Đã kiểm chứng: `./make.sh KTF 15` cho ra jar trùng nội dung với bản trong `release/`.

## 2. Việc hay gặp: làm ở đâu
| Việc | Sửa ở đâu | Build lại |
|---|---|---|
| Đổi giới hạn fps | manifest `HL4-FPS` trong `hl4j/build.sh` (0 = không giới hạn) | `./make.sh KTF` |
| Tăng tốc (AOT) | `hl4j/tools/regen_ktf_aot.sh` (profile trong `hl4j/profiles/`), xem báo cáo §8 | `./make.sh KTF` |
| Cửa hàng offline / server giả | `hl4j/src/core/hl4/ktf/FakeServer.java` (danh mục `CATALOG`), `KtfRuntime.net()`; báo cáo §9 | `./make.sh KTF` |
| Logo NSX / ảnh tiêu đề | `img/src/logo_namp.png`, `img/src/title.png` (290x199); công cụ `img/bm.py`, `make_logo.py`, `make_title.py` | `./make.sh KTF` |
| UI mới (Nhân vật, sắp tới Túi đồ) | `hl4j/src/core/hl4/core/UiOverlay.java`; báo cáo §10 | `./make.sh KTF` |
| Âm thanh (.mid/.wav) | `hl4j/test/src/SndConvert.java` (make.sh gọi), phát ở `midp/Sound.java`; báo cáo §11 | `./make.sh` |
| Đổi phím | `hl4j/src/midp/hl4/midp/GameCanvas.java` → `wipiKey()` | `./make.sh KTF` |
| Sửa câu dịch | `vi/tr/out/*.json` (key = id trong `vi/catalog.json`), chạy `vi/tr/check.py` | `./make.sh` |
| Thuật ngữ | `vi/tr/thuat-ngu.tsv`, văn phong: `vi/tr/HUONG-DAN-DICH.md`, `vi/tr/phan-tich-vai.md` | – |
| Đổi font | `vi/font/` (font_15px.bin mặc định, font_10px), `./make.sh KTF 10` | – |
| Lỗi hiển thị/ngắt dòng chữ | `hl4j/src/core/hl4/core/TextEngine.java` | `./make.sh` |
| Lỗi giả lập (API WIPI) | `hl4j/src/core/hl4/{lgt,ktf}/*Runtime.java`, `core/Runtime.java` | `./make.sh` |
| Vá nhanh 1 class vào jar chuẩn | build rồi `zip` đè `hl4/...class` vào bản copy của jar chuẩn, tạo lại .jad (MIDlet-Jar-Size) | – |

Chạy thử không cần giả lập (headless, xuất PNG):
```bash
HL4_VI=vi/build/ktf/vi HL4_RES=vi/build/ktf java -cp hl4j/build/test2 RunHeadless KTF.zip 30000 5000 /tmp/out "8000:OK,10000:DOWN,12000:OK"
```
Tên phím: UP DOWN LEFT RIGHT OK LSK RSK CLR 0-9 # *. Trong harness đây là **mã WIPI trực tiếp**, không đi qua ánh xạ của GameCanvas. `monkey:<seed>` = bấm ngẫu nhiên. Các biến debug khác xem `BAO-CAO-KY-THUAT.md` §6.

## 3. Việc còn tồn đọng
0. **Tiếp theo:** màn Túi đồ kiểu game tham chiếu (lưới 8 cột, các tab, nhiều túi hơn). Cần tìm cấu trúc danh sách vật phẩm trong bộ nhớ và bảng icon `ITM/_ITM_ICN`, xem báo cáo §10. Ảnh tiêu đề đã thay.
1. Chữ Hàn trong **ảnh** (logo 영웅서기4, một số tiêu đề/nút dạng ảnh) chưa Việt hoá.
2. Hộp thông tin (info box) dùng `;` để ngắt dòng cứng, nên với font Việt có dòng dài, dòng ngắn không đều.
3. Font 15px dùng khoảng cách dòng 13px (hơi chật).
4. Nên rà lại tính thống nhất của bản dịch và chơi thử dài hơn (đặc biệt tuyến Lurain).
5. Bản LGT chưa được người dùng xác nhận trên máy/giả lập thật (headless thì chạy tốt).

Chi tiết kỹ thuật xem **`BAO-CAO-KY-THUAT.md`**.
