# PLAN — Việt hoá "Tiên Sủng Kỳ Duyên" (仙宠奇缘) 240x320 + 320x240

Nguồn sự thật của dự án. Công nhân đọc hết file này (nhất là **Quyết định thiết kế**) trước khi làm. Đường dẫn tương đối theo `tsky/`.

## Mục tiêu
1. Việt hoá toàn bộ (resource + chuỗi trong class + ảnh có chữ), font **f12.bin** 12px, chạy đúng trên KVM/CLDC 1.0 + MIDP 2.0 thật.
2. Xoá logo khởi động gốc (hoạt hình logo China Mobile/2rong/ZOSEAN), thay logo NAMP (`goc/logo_namp_240x320.png`). Bỏ màn hỏi bật nhạc, nhạc luôn bật.
3. Làm thêm bản **320x240** từ bản Việt hoá.
4. Phát hành: dự án trên nhánh `claude/effort-auto-8khe48`; jar/jad vào `main:releases/TienSungKyDuyen/`. Tool trên `main`: BitmapFont hỗ trợ 12px.

Ngoài phạm vi: đổi gameplay, cân bằng, sửa lỗi game gốc không liên quan chữ/màn hình.

## Văn phong (đã chốt với người dùng)
- **Hán Việt nhẹ**: tiên hiệp dễ đọc, thuật ngữ/chiêu thức Hán Việt, thoại tự nhiên. Tên riêng phiên âm Hán Việt (王大爷 Vương đại gia, 李奶奶 Lý bà bà, 小虎 Tiểu Hổ, 风雅灵 Phong Nhã Linh, 小灵子 Tiểu Linh Tử, 爷爷 ông nội/gia gia theo ngữ cảnh).
- 仙宠 = **tiên sủng**; 宠物/宠 = **sủng vật** hoặc tiên sủng tuỳ ngữ cảnh; 骑宠 = **tọa kỵ**. Chưa rõ thì ghi Vướng mắc để quản đốc hỏi người dùng.
- Chỉ số/UI viết tắt Anh: HP, MP, EXP, ATK, DEF, SPD, Lv.

## Hiện trạng (đã khảo sát)
- Jar gốc: `goc/TienSungKyDuyen_240x320.jar`. MIDP-2.0/CLDC-1.0, 21 class version 45.3 (đã preverify). `MIDlet-Description: rpg,N73`.
- Môi trường: `source work/tools/env.sh` → `$FJ $CFR $PROGUARD $FONT_DIR`, pixeltext trong PYTHONPATH. Jar giải nén: `work/x/`. CFR: `src/`. Script skill: `S=/root/.claude/skills/synced/c7cebff6-afdc-419c-9062-179641ffc813_dd046f8a-671d-4e98-8dc1-6aad796e07ca/j2me-viet-hoa/scripts` (dp.py, preverify.sh, jar_check.py, run_emu.py, classfile.py). Tham chiếu skill: `$S/../references/` (bitmapfont.md, dich-pro.md, phuong-an-2-hook-bytecode.md, toolkit.md).
- Text resource:
  - `pak/*.p` — `Main/h.a(path, 0)`: `short`(bỏ qua), `byte n`, n × {`readUTF` tên, `short len` (CÓ DẤU → blob ≤ 32767), `byte[len]`}. Blob = script UTF-8: lệnh tách `^`, trường tách `|`, trường đầu = mã lệnh. Ví dụ `16|g4#0|j4#：快点…`, `39|炉灶里…|false`, `5|jq=6`, `29|k3.p`. Token `j6#`, `g4#0`, `i4#`… phải giữ nguyên.
  - `t/item1..8.t` — `Main/h.a(path, 3)`: `short`, `short rows`, `short cols`, ô `readByte`/`readUTF`/`readShort` vào `Main.k.A/x/F/z/E/I/J/y` (item3=F, item4=z, item5=E, item6=I, item7=J là chuỗi).
  - Chuỗi Hán trong class: `Main/j` 118, `Main/k` 14 (mẹo `G`, `H`), `Main/l` 7 (tên NPC), `Main/e` 6, `Main/f` 5, `GameMidlet` 5, `rf/g` 3, `rf/b` 2, `rf/e` 2, `Main/a` 1.
  - `dat/26xueyewai.dat`, `dat/30xuecunzi.dat` bị inventory nghi UTF-16 Hán — cần xác minh (có thể là dữ liệu bản đồ).
- Vẽ chữ: font hệ thống. `Main/k.D = Font.getFont(0,0,8)`, `k.w = D.stringWidth("额")`, `k.v = D.getHeight()`. `Main/f`: font `a`, `c` = rộng 1 chữ Hán, `b` = cao-1, `f.a(Graphics,String,x,y,c1,c2,anchor)` vẽ viền 8 hướng. `Main/g.a(char[],Graphics,w,x,y,color,start)` vẽ từng ký tự, xuống dòng theo px, tối đa 2 dòng/trang, trả về chỉ số ký tự. `rf/b` có font riêng (`b = height`). Class gọi drawString/stringWidth: `Main/a,e,f,g,j,k`, `rf/b,c,e`.
- Khởi động `Main/f`: pha `u` (vẽ `menu.png` thu nhỏ giữa màn, 2s) → `w` (hỏi "是否开启音乐", phím mềm trái = có → `g=true, j.bY=true`) → `v` (34 khung hoạt hình `bg.png` + `logo0..5.png`, `logo51/52.png` = logo nhà phát hành; ở `k==0` gọi `e()` bắt đầu nhạc `/Image/xjoy.mid` nếu `g`). Hết thì `finally` chuyển sang canvas `Main/a`.
- 320x240: `GameMidlet.a(String)` đọc phần sau dấu phẩy của `MIDlet-Description`; `E62` → `j.p = 6` → `j.o()` đặt `a.p=320, a.o=240`; nhiều nhánh `t == 320` trong `Main/j`. `j.java:477-480`: khi `p==6` ảnh `menu.png` (bìa dọc) bị kéo giãn ra 320x240. `j.d(String,String)` case 6 đổi phím hướng dẫn sang QWERTY (48→109, -4→104, -2→98) → máy bàn phím số kẹt ở hướng dẫn. Phím thường trong `Main/a` đã nhận cả số lẫn chữ QWERTY.
- f12.bin: 228 ký tự = ASCII in được (thiếu `'`, `_`, `=`, `` ` ``, `~`) + đủ chữ Việt + dấu cách. Không có `…`, `：`, `“”`, `，`, `。`, `！`, `？`, `、`.
- Tham khảo đã chạy được (cùng tool, cùng f12): nhánh `origin/ccr-26b03ef8-e773fn`, thư mục `du_an/`: `hook/VH.java` (renderer f12, atlas 3 màu + fillRect, `dl/calcPos/calcPage` ngắt px), `patcher/Patch.java` (ASM), `build_hook.sh`, `build.sh`, `rebuild.sh`, `scripts/run_emu.py` (đã sửa tham số FreeJ2ME-Plus). Đọc bằng `git show origin/ccr-26b03ef8-e773fn:du_an/<file>`. **Được phép chép và chỉnh**, đây là game khác nên mọi chỗ vá phải làm lại cho class của game này.

## Quyết định thiết kế (công nhân KHÔNG tự đổi)
1. **Hook bytecode tĩnh bằng ASM**, không biên dịch lại class gốc. Class mới `VH` (gói mặc định, biên dịch ecj `-source 1.3 -target 1.3`, chỉ CLDC 1.0/MIDP 2.0: `drawRegion`, `createRGBImage`, `fillRect`; không float, không reflection). Font `/font/f12.bin`.
2. Call-site thay thế (cùng kiểu stack, `invokevirtual` → `invokestatic`):
   - `Graphics.drawString(String,III)V` → `VH.drawString(Graphics,String,III)V`; tương tự `drawChar`, `drawChars`, `drawSubstring`.
   - `Font.stringWidth(String)I` → `VH.stringWidth(Font,String)I`; tương tự `charWidth`, `charsWidth`, `substringWidth`.
   - `Font.getHeight()I` của font chữ → `VH.getHeight(Font)I` (trả `VH.LH`).
   - Màu: `VH.drawString` lấy màu hiện tại `g.getColor()`.
   - Patcher in số chỗ khớp từng patch và **dừng nếu số khớp khác số kỳ vọng** ghi trong code.
3. Không đổi tên/descriptor/access flag của class, field, method gốc. Class output giữ version 45.3, bỏ StackMap cũ, ProGuard `-microedition` sinh lại (dùng `$S/preverify.sh`).
4. Ngắt dòng theo px ở dấu cách (từ dài hơn dòng thì cắt theo ký tự). Kết quả ngắt cache theo (chuỗi, độ rộng); không tạo String/object mới trong `paint()`/vòng lặp game khi chuỗi không đổi. Khung hội thoại tối thiểu 3 dòng, phân trang sửa đồng bộ.
5. Text resource: trích bằng Python. Chỉ trường chứa ký tự Hán (hoặc dấu câu Hán) là text dịch; token `[a-z]\d+#\d*`, `^`, `|`, `=`, tên file giữ nguyên. Bản dịch không được chứa `^ | #` và ký tự ngoài charset f12. Dấu câu Hán đổi sang ASCII: `：`→`:`, `，`→`,`, `。`→`.`, `！`→`!`, `？`→`?`, `……`/`…`→`...`, `“”「」`→`"`, `、`→`,`, `（）`→`()`. Riêng `：` ngay sau token tên người nói (vd `j4#：`) → kiểm xem code có tìm `：` không (T1) rồi mới quyết.
6. Bộ đóng gói phải tái tạo **giống từng byte** khi chưa dịch. Blob pak ≤ 32767 byte; mỗi readUTF ≤ 65535 byte.
7. Chuỗi trong class: thay hằng LDC bằng ASM chỉ khi chứng minh chỉ để hiển thị; chuỗi dính `equals/indexOf`/khoá Hashtable với dữ liệu resource thì dịch **cùng một giá trị** ở mọi nơi hoặc giữ nguyên (ghi rõ trong docs).
8. Dịch: `dp` theo `$S/../references/dich-pro.md`. Quản đốc chốt `dich/thuat-ngu.tsv` + `dich/vai.md`; **đúng MỘT công nhân Sonnet 5.5 dịch tay toàn bộ**, cấm dịch máy, cấm agent khác. `dp check` phải `OK`.
9. Khởi động: ASM viết lại `Main/f.run()`: pha logo NAMP 2s (ảnh mới `/Image/namp.png`, nền đen, vẽ giữa màn hình, không thu nhỏ) → `g=true; j.bY=true; w=false; v=false; this.e()` → khối `finally` gốc (chuyển canvas, `a.d().a(2)`, start thread). `paint` pha `u` vẽ `namp.png` thay `menu.png` thu nhỏ. Ảnh logo nhà phát hành (`logo0..5,51,52.png`, `bg.png`) bỏ khỏi jar **chỉ khi** không còn tham chiếu.
10. 320x240: dùng chung class đã vá; khác `MIDlet-Description: rpg,E62`, `menu.png` ngang 320x240 (dựng từ bìa dọc, không kéo giãn méo) và `namp.png` ngang (logo dọc thu về cao 240, đặt giữa, nền mở rộng từ ảnh). Vá `j.d(String,String)`: bỏ remap case 6; thay so sánh `cq != Integer.parseInt(cr)` trong `Main/a.keyPressed` và `Main/a.b(int)` bằng `!VH.keyEq(cq, Integer.parseInt(cr))` nhận nhóm tương đương {-1,50,116} {-2,56,98} {-3,52,102} {-4,54,104} {-5,53,103} {48,109}. Hai bản chỉ khác resource + manifest.
11. Ảnh có chữ: vẽ lại bằng `pixeltext` cỡ 12 theo quy tắc skill (xoá sạch chữ gốc, một màu thân chữ, nét đơn, rút gọn chữ nếu không vừa).
12. Tool main: thêm `SIZE_12 = 12` vào `BitmapFont.java` (mảng SIZES, làm tròn ≤12 → 12, 13 giữ 13), f12 chỉ có một kiểu nên SANS/SELECTIVE ở 12 dùng f12; build `java/build_class.sh` (class 47), thêm f12 vào `check_all.sh` + README, đóng lại `Tool_Viet_Hoa_NAM_P.zip` cùng cấu trúc `NAM_P/`.

## Cấu trúc thư mục dự án
```
tsky/ PLAN.md PROGRESS.md .gitignore
  goc/      jar gốc, logo NAMP
  docs/     script.md, render.md, (phân tích)
  scripts/  extract.py, repack.py, make_img.py, ...
  dich/     text_can_dich.tsv, dp_work/, thuat-ngu.tsv, vai.md
  hook/     VH.java
  patcher/  Patch.java (+ asm jar tải về work/)
  res_vi/   tài nguyên đã sinh (240), res_vi_320/ (khác biệt cho 320x240)
  build.sh rebuild.sh
  out/      jar+jad cuối 2 bản, shots/
  logs/
  work/ src/ build/   (bị .gitignore)
```

## Task
Trạng thái: `[ ]` chưa, `[~]` đang làm (ghi tên), `[x]` xong.

### Giai đoạn 1 — Phân tích (A1)
- [x] T0 Thiết lập `.gitignore`, `PLAN.md`, `PROGRESS.md`. (quản đốc)
- [x] T1 **Phân tích script pak** → `docs/script.md`. **Quản đốc nghiệm thu.**
  - Tìm hàm tách `^`/`|` trong `Main/j`, bộ thông dịch lệnh; lập bảng mã lệnh → ý nghĩa → trường nào là chữ hiển thị (thoại, tường thuật, lựa chọn, tên hiển thị) / trường mã (tên file, biến, cờ, số).
  - Ý nghĩa token `jN#`, `gN#`, `iN#`… và cách thay khi vẽ; code có tìm `：` hay ký tự Hán nào trong chuỗi script không (`indexOf`, `charAt`).
  - `t/item*.t`: ý nghĩa từng cột F/z/E/I/J; ô nào bị `Integer.parseInt`/so sánh.
  - Chuỗi hiển thị nào bị so sánh (`equals/indexOf/startsWith`, khoá Hashtable) — liệt kê đủ, kể cả `Main/e.java:414` ("极品青龙"/"初级青龙").
  - Save (RecordStore) có lưu chuỗi hiển thị không.
  - Xác minh `dat/26xueyewai.dat`, `dat/30xuecunzi.dat`, `sr/*.sr` có chữ thật không.
  - Phân loại 118 chuỗi Hán của `Main/j` + chuỗi các class khác: hiển thị / so sánh / debug.
  - Hoàn thành khi: `docs/script.md` có đủ các mục trên, dẫn `file:dòng`; có số liệu thống kê (số blob, số trường text, blob lớn nhất, ước lượng blob lớn nhất sau dịch ×1.6).
- [x] T2 **Phân tích vẽ chữ** → `docs/render.md`. **Quản đốc nghiệm thu.**
  - Mọi call-site `drawString/drawChar/drawChars/drawSubstring`, `stringWidth/charWidth/charsWidth/substringWidth/getHeight/getBaselinePosition`, `setFont/getFont/Font.getFont` theo class (số lượng, dùng `javap -c` để đếm chính xác theo bytecode, không theo CFR).
  - Chỗ bố cục theo số ký tự / độ rộng cố định (`k.w`, `f.c`, `length()*`, cắt `substring` theo chỉ số để xuống dòng, vòng lặp `toCharArray`): màn nào, số dòng tối đa, độ rộng ô px cho 240 và 320, phân trang.
  - Khung hội thoại: hình học, số dòng, cách vẽ tên người nói, hiệu ứng chạy chữ, chuyển trang.
  - Cấp phát trong paint liên quan chữ (`"" + char`, `new String`).
  - Hoàn thành khi: `docs/render.md` có bảng call-site (class.method:offset bytecode, số lần) + danh sách màn cần sửa bố cục, mỗi mục đề xuất cách hook (thay call-site hay thay cả method).

### Giai đoạn 2 — Hạ tầng (A1, sau khi quản đốc duyệt T1/T2)
- [ ] T3 `scripts/extract.py` + `scripts/repack.py` → `dich/text_can_dich.tsv` (cột `id, file, offset/khoá, gốc, dịch, ghi chú ngữ cảnh, giới hạn`). Bao gồm pak, item*.t, chuỗi class (cột file = `class:Main/j` …).
  - Hoàn thành khi: `repack` từ TSV chưa dịch ra mọi file resource giống từng byte (`cmp`), có test tự động `scripts/selftest_repack.sh`; TSV đánh dấu token mã điều khiển.
- [ ] T4 `hook/VH.java`, `patcher/Patch.java`, `build_hook.sh`, `build.sh`. **Quản đốc nghiệm thu.**
  - VH port từ nhánh tham khảo, chỉnh theo docs/render.md. Patch theo QĐ 2–4.
  - Build: giải nén goc → patch class → chép `font/f12.bin`, `VH.class` → zip → `$S/preverify.sh` → `$S/jar_check.py goc patched` → `run_emu.py`.
  - Hoàn thành khi: `jar_check` `RESULT: PASS`; ảnh chụp FreeJ2ME menu chính + một hội thoại hiện chữ (còn Hán vẫn được, chưa dịch) bằng font f12, không exception.
- [ ] T5 Khởi động theo QĐ 9 (`res_vi/Image/namp.png` từ `goc/logo_namp_240x320.png`).
  - Hoàn thành khi: chụp FreeJ2ME: màn đầu là logo NAMP, sau ~2s vào game, không có màn hỏi nhạc, không có logo nhà phát hành; log có `Player.start` (hoặc lỗi MIDI headless như skill mô tả).

### Giai đoạn 3 — Dịch (đúng MỘT công nhân Sonnet 5.5)
- [ ] T6a (quản đốc) `dp prep --charset` (charset f12), chốt `dich/thuat-ngu.tsv`, `dich/vai.md`.
- [ ] T6b (công nhân dịch) dịch toàn bộ bằng `dp next/add/fix/check/sample`; `dp check` = OK; quản đốc `dp merge`.

### Giai đoạn 4 — Hoàn thiện (A1)
- [ ] T7 Bố cục: khung thoại ≥3 dòng, ngắt px ở dấu cách, phân trang, canh giữa theo độ rộng thật, các màn menu/túi đồ/cửa hàng/chiến đấu/tiên sủng đại toàn/mẹo. Hoàn thành khi: ảnh chụp từng màn không tràn, không cắt đôi từ, không sót Hán.
- [ ] T8 Ảnh có chữ: soát 327 PNG (`scripts/contact_sheet.py` ra lưới ảnh), danh sách `docs/anh_co_chu.tsv`, vẽ lại vào `res_vi/`. Hoàn thành khi: mọi ảnh có chữ Hán đã có bản Việt, `pixeltext check --size 12` OK.
- [ ] T9 Bản 320x240 theo QĐ 10. Hoàn thành khi: chụp FreeJ2ME 320x240 qua logo, menu, hội thoại, túi đồ, chiến đấu không tràn/méo; kiểm phím hướng dẫn bằng phím số.
- [ ] T10 `rebuild.sh` (goc → out cả 2 bản + jad `MIDlet-Jar-Size` khớp), `run_emu.py` đi qua các màn chính 2 bản, `BAO_CAO.md`, `README_DU_AN.md`, `PROGRESS.md`, gói `TienSungKyDuyen_du_an.zip` theo mục Bàn giao của skill. **Quản đốc nghiệm thu.**
- [ ] T11 Tool main theo QĐ 12 + phát hành jar/jad vào `main:releases/TienSungKyDuyen/` (quản đốc commit/push lên main).

## Phân công
- A1 (Sonnet 5.5): T1 → T2 → (duyệt) → T3 → T4 → T5 → T7 → T8 → T9 → T10 → T11-tool.
- Công nhân dịch (Sonnet 5.5, một người): T6b.
- Chỉ một công nhân chạy thật tại một thời điểm.

## Nhật ký
- T0 (quản đốc): tạo .gitignore, PLAN.md, PROGRESS.md; goc/logo_namp_240x320.png.
- T1 (A1): docs/script.md + scripts/dump_pak.py; 486 blob/1668 trường Hán, blob lớn nhất 14701 B (~20.7 KB sau dịch), 7 token tên Hán nằm trong Main/l, 3 nhóm chuỗi bị so sánh (trạng thái ×5, "没有", "空" lưu RMS).
- T2 (A1): docs/render.md + scripts/count_callsites.py; 50 drawString/24 stringWidth/2 charWidth/2 substringWidth/4 getHeight, không có drawChar(s)/drawSubstring; thoại 2 dòng/trang ở Main/g.a, cần thay cả method.

## Quyết định quản đốc sau T1/T2 (bổ sung QĐ, công nhân làm theo)
- QĐ 2b: **Được thay cả thân method** (giữ nguyên tên/descriptor/access) cho `Main/g.a(char[],...)`, `rf/b.a(String,int)`, `rf/b.b(String,int)`, `rf/b.a(Graphics,...)` viền chữ, `Main/f.a(Graphics,String,...)` viền chữ: thân mới chỉ gọi `invokestatic VH.*` rồi return. Bỏ `println` mỗi ký tự trong `rf/b.b`. Ngắt dòng/trang tính 1 lần khi chuỗi đổi, cache trong VH.
- QĐ 7b: "空" GIỮ NGUYÊN trong class/RMS; VH ánh xạ khi vẽ/đo: chuỗi đúng bằng "空" → "Trống". Trạng thái 麻痹/禁锢/冰冻/燃烧/石化 dịch CÙNG giá trị ở hằng class và `item3.t` (Tê liệt / Giam cầm / Đóng băng / Thiêu đốt / Hóa đá). `indexOf("没有")`: 3 chuỗi danh sách rỗng dịch bắt đầu bằng "Không có", hằng indexOf đổi thành "Không có"; kiểm không chuỗi hiển thị nào khác lọt nhánh này.
- Tên token trong `Main/l`: 王大爷 Vương đại gia, 李奶奶 Lý bà bà, 小虎 Tiểu Hổ, 风雅灵 Phong Nhã Linh, 共宛 Cộng Uyển, 爷爷 Gia gia, 小灵子 Tiểu Linh Tử.
- `Main/j` drawString(dk,0,0) (CFR `j.java:5858,5879`): chụp màn CG trên **jar gốc** ở T4; nếu đúng là chữ debug đè góc trên-trái thì NOP (pop đủ đối số), nếu không thì giữ và ghi lại.
- Mục menu "更多游戏" (platformRequest web quảng cáo): ẩn khỏi menu (hoặc nếu ẩn rủi ro lệch chỉ số menu thì biến thành no-op và đổi nhãn). Ghi cách làm vào docs/render.md.
- Phím mềm căn theo `k.w*2`, cửa hàng đặt cột theo tên hàng đầu, mũi tên menu theo "继续游戏": sửa ở T7 bằng đo độ rộng thật.

## Vướng mắc
- (đã xử lý, xem mục trên) (T1/T2, A1) QĐ 2 + QĐ 4 chưa đủ cho 5 chỗ: chỉ thay call-site không sửa được ngắt dòng/trang ở `Main/g.a`, `rf/b.a(String,int)`, `rf/b.b(String,int)` và viền chữ 8 hướng (`rf/b.a(G,...)`, `Main/f.a`) -> đề xuất thay cả thân method bằng `invokestatic VH.*` (vẫn giữ descriptor, QĐ 3 không vi phạm). Cần quản đốc duyệt.
- (T1) Hằng "空" bị `equals` và lưu RMS `xianchong1`: đề xuất giữ nguyên trong class, ánh xạ khi vẽ trong VH (trái QĐ 7 "dịch cùng giá trị"?). Quản đốc chốt.
- (T1) `j9#` (爷爷) chỉ có một bản dịch trong `Main/l`; muốn đổi "ông nội"/"gia gia" theo ngữ cảnh phải dịch thẳng trong pak. Token `j8#` (共宛) cần hỏi người dùng.
- (T2) `Main/j.java:5858,5879` `drawString(dk,0,0)` là mã debug sót vẽ cả đoạn CG ở góc trên-trái; đề xuất NOP. Mục menu "更多游戏" mở web quảng cáo (`platformRequest`), ngoài phạm vi nhưng nên hỏi người dùng có ẩn không.
