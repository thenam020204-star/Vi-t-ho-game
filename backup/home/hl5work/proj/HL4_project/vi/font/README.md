# fontcus — font bitmap tiếng Việt cho J2ME (bản build lại, self-contained)

Bản gốc: `fontcus.class` + resource `/font.bin`.
Bản này: **chỉ 1 file `fontcus.class`** — dữ liệu font nằm luôn trong constant pool.
Tiêm vào game = copy 1 file, không cần thêm resource nào.

> **v2 — đã sửa lỗi glyph dính / đè lên nhau.** Xem [mục 2b](#2b-sửa-lỗi-glyph-dính--đè-lên-nhau-v2).
> Advance của 124/227 glyph nới thêm 1px; nét glyph **không đổi một pixel nào**.

---

## 1. Tiêm vào game

```bash
# thay class cũ (và bỏ luôn font.bin nếu không class nào khác đọc nó)
zip -d game.jar font.bin          # tuỳ chọn
jar uf game.jar fontcus.class     # hoặc: zip game.jar fontcus.class
```

Class ở default package, tên `fontcus`, **toàn bộ public field/method giữ nguyên
tên + descriptor** như bản gốc → mọi caller đã compile sẵn vẫn link được, không
phải build lại game.

| | Bản gốc | Bản build lại |
|---|---|---|
| file cần trong JAR | `fontcus.class` + `font.bin` | `fontcus.class` |
| class file version | 47.0 | 47.0 |
| **preverify (StackMap)** | **KHÔNG** → KVM thật ném `VerifyError` | **CÓ** |
| dung lượng trong JAR (deflate) | 4458 B | 4522 B (**+64 B**) |
| advance glyph | 6 px cho mọi glyph | 6–7 px, **không còn dính chữ** (mục 2b) |
| kết quả render | — | nét từng glyph trùng khít từng pixel |

> ⚠️ Phát hiện quan trọng: **class gốc bạn đưa chưa được preverify.**
> Nó chạy được trên J2ME Loader / MicroEmulator (chạy qua ART/JVM, verify kiểu
> type-inference) nhưng KVM/CLDC thật sẽ từ chối nạp. Bản này đã preverify
> (ProGuard `-microedition`, attribute `StackMap` đúng chuẩn CLDC).

## 2. Font

227 glyph · cao 15 px · atlas **1484×15** · 1 bpp
Bề rộng (advance): **7 px cho 124 glyph, 6 px cho 102 glyph**, riêng `space` = 4 px.
(Bản v1 dùng 6 px cho tất cả — xem mục 2b vì sao phải đổi.)
Ô glyph 15 hàng: hàng 0–4 dành cho dấu thanh phía trên, chữ hoa nằm ở hàng 5–13,
chữ thường 5–14 (phần nhô xuống của `g p q y`).
Bộ ký tự: `0-9 A-Z a-z`, dấu câu, **đầy đủ tiếng Việt hoa + thường**, `…` `‚`, space.
Thiếu: `=` `_` `~` `` ` `` (giữ nguyên như bản gốc — chưa thêm).

Chữ nào không có glyph thì `drawString` bỏ qua, `doChu` cộng 12 px.

## 2b. Sửa lỗi glyph dính / đè lên nhau (v2)

### Hiện tượng

Chữ `chọn`: nét phải của `ọ` mất 1 px, `ọ` dính liền vào `n` thành một mảng.
Xảy ra với rất nhiều cặp chữ, rõ nhất ở `o`+`n`, `a`+`n`, `u`+`n`, `g`+`h`.

### Nguyên nhân

Engine game vẽ **từng ký tự một**, mỗi ký tự kèm viền 4 hướng (vẽ chuỗi 4 lần lệch
±1 px bằng màu viền, rồi vẽ nét đè lên):

```
for (mỗi ký tự c) {
    vẽ viền c tại (x-1,y) (x+1,y) (x,y-1) (x,y+1)
    vẽ nét  c tại (x,y)
    x += advance(c)
}
```

Ký tự sau vẽ viền tại `x-1`, tức **cột cuối của ô ký tự trước**. Glyph nào có nét
chạm cột cuối (cột 5 của ô 6 px) thì đúng cột nét đó bị viền của ký tự sau ghi đè.

Đếm trên bộ font: **124/227 glyph** có nét chạm cột cuối — gần hết nguyên âm có
dấu, cộng `a c d e g h n o q u v y` và `A G H J K L N R U V`.

Lỗi này **không lộ với chữ Hán** vì ô chữ Hán rộng 18 px, nét không bao giờ chạm mép.
Nó cũng **không lộ nếu vẽ viền cho cả chuỗi trước rồi mới vẽ nét cả chuỗi**
(cách `Graphics.drawString` gốc làm) — chỉ lộ ở đường vẽ từng ký tự.

### Cách sửa

Không đụng một dòng code nào của lớp `fontcus`, chỉ nới **advance của từng glyph**
trong `font.bin`:

```
advance_mới = max(advance_cũ, cột_nét_phải_nhất + 2)
```

`+2` = 1 cột cho viền của chính glyph đó + 1 cột cho viền của glyph kế tiếp.

* 124 glyph: 6 → 7 px; 102 glyph giữ 6 px; `space` giữ 4 px.
* **Nét glyph giữ nguyên tuyệt đối** — chỉ chèn thêm cột trống vào atlas.
* `font.bin` 3235 → 3468 B; `atlasW` 1360 → 1484 px.
* Chữ dài thêm khoảng 10 %: `Tuỳ chọn` 46 → 52 px, `Lữ Tử Phong` 62 → 69 px.
  Nhớ rà lại các nhãn UI bị giới hạn pixel sau khi cập nhật font.

### Kiểm chứng

Mô phỏng đúng thứ tự vẽ ở trên cho **toàn bộ 51 529 cặp glyph liền nhau**
(227 × 227), so từng pixel với khi vẽ riêng lẻ từng glyph:

| | Số cặp bị mất nét |
|---|---|
| advance cũ (6 px đều) | **8 740** / 51 529 |
| advance mới | **0** / 51 529 |

### Nếu game của bạn vẽ viền cho cả chuỗi

Thì không cần bản v2 — advance 6 px vẫn đúng và chữ gọn hơn. Muốn quay lại:
chạy `tools/font_png.py import` với `widths` toàn 6 (trừ `space` = 4), rồi
`gen_class.py` như mục 4.

## 3. Định dạng `font.bin` (đã reverse từ bytecode)

```
u2            utfLen
u1[utfLen]    charset — Java modified-UTF8 (DataInputStream.readUTF)
s1            fontHeight
s1[nChars]    advance width của từng glyph, theo thứ tự charset
bits          atlas 1 bpp, row-major, mỗi hàng dài sum(widths),
              LSB-first trong từng byte, luồng bit LIÊN TỤC qua các hàng
              (không pad theo hàng). Tổng = ceil(h * atlasW / 8) byte.
```
Bản v1: 3235 byte = 2 + 455 + 1 + 227 + 2550.
Bản v2: **3468 byte** = 2 + 455 + 1 + 227 + 2783 (atlas rộng hơn 124 cột).
Cả hai đều khớp chính xác, không dư byte nào.

## 4. Sửa font rồi build lại

```bash
# xuất ra ảnh để vẽ tay (đen = có mực, cột đỏ = vạch ngăn glyph)
python3 tools/font_png.py export font-src/font.bin sheet.png sheet.json

#  ... sửa sheet.png bằng bất kỳ trình vẽ pixel nào ...
#  ... thêm/bớt ký tự thì sửa "chars" + "widths" trong sheet.json cho khớp ...

python3 tools/font_png.py import sheet.png sheet.json font_new.bin
python3 tools/gen_class.py font_new.bin fontcus.java
```

Rồi compile + hạ version + preverify:

```bash
javac -g:none -encoding UTF-8 -source 8 -target 8 -cp <midp-stubs> -d classes fontcus.java

# hạ version 47.0 + BỎ StackMapTable  (chọn 1 trong 2)
java    -cp asm.jar:.  J2meFix classes/fontcus.class out/fontcus.class strip
python3 tools/stripsmt.py classes/fontcus.class out/fontcus.class 47

java  -jar proguard.jar -injars in.jar -outjars pv.jar -libraryjars <midp+cldc> \
      -dontshrink -dontoptimize -dontobfuscate -microedition -keep 'class fontcus { *; }'
python3 tools/compat_scan.py pv/fontcus.class fontcus_goc.class      # phải in "PASS"
```

> ⚠️ **Bước strip là bắt buộc, đừng chỉ sửa 2 byte version.** `javac` luôn sinh
> attribute `StackMapTable` (định dạng Java 6). KVM/CLDC chỉ chấp nhận attribute
> `StackMap`; class 47.0 mà còn `StackMapTable` sẽ bị `VerifyError` trên máy thật
> dù chạy tốt trên MicroEmulator/J2ME Loader. `compat_scan.py` **không** bắt được
> lỗi này — hãy kiểm bằng `tools/stripsmt.py` (nó in ra `StackMapTable=...` sau khi gỡ)
> hoặc đọc bảng attribute của `Code` trong class đã preverify: chỉ được có `StackMap`.

`tools/fontbin.py` là codec thuần (decode/encode) nếu bạn muốn viết script riêng.
`tools/compat_scan.py` quét constant pool: API ngoài CLDC/MIDP, class version,
thiếu preverify, và diff descriptor public API so với class gốc → **reject**, không chỉ cảnh báo.

## 5. Bộ nhớ trên máy thật

Không đổi so với bản gốc:

| | |
|---|---|
| `glyphBits` `byte[15][1484]` | ~22 KB |
| `charMap` `short[8448]` | ~16.5 KB |
| `charIndex` `Hashtable` 227 entry | ~11 KB |
| cache atlas | tối đa `CACHE` × **89 KB** ảnh, tạo lazy theo từng màu |

Hai chỗ đáng chỉnh trong `fontcus.java` nếu heap máy đích chật:

* `CACHE = 6` → mỗi màu chữ mới tạo một `Image` 1484×15 ARGB (≈89 KB).
  6 màu = ~534 KB image memory. Trên S40/S60 đời cũ nên để **2**.
  (Đo thực tế trong một game RPG 240×320: chỉ dùng 3 màu chữ → ~267 KB.)
* `BUILD_CHARINDEX = true` → đặt `false` để bỏ hẳn `charIndex` (~11 KB) nếu
  code game không đọc trực tiếp `fontcus.charIndex` (class này không tự dùng nó).

Cả hai đường cấp phát ảnh đều nằm trong `try/catch (Throwable)`; hết RAM thì
`atlasOk=false` và nó tự chuyển sang vẽ từng ký tự bằng `drawRGB`, không crash.

## 6. Đã kiểm chứng những gì

Chạy song song class gốc và class mới trong 2 classloader riêng, so từng thứ:

```
fontHeight / atlasW / atlasOk / unusedNhungW      trùng
glyphW[] / glyphOff[] / charMap[8448] / glyphBits[15][atlasW]  trùng
charIndex 227 entry                               trùng
charWidth(c), doChu(7,c) cho cả 8448 code point   trùng
strWidth, XuongDong (33 600 tổ hợp), XuongDong2   trùng
render pixel-exact: drawString + drawChar (8448 ký tự) + drawUniversal,
   7 màu, CẢ đường atlas LẪN đường fallback drawRGB   trùng từng pixel
```

Compat scan: 12 external reference, tất cả nằm trong CLDC 1.0 / MIDP.
3 call thuộc MIDP 2.0 (`Image.createRGBImage`, `Graphics.drawRGB`,
`Graphics.drawRegion`) — **giống hệt bản gốc, không phải regression**.
Binary compat: 20/20 public member giữ nguyên descriptor.

Round-trip: `font.bin → PNG+JSON → font.bin` và `font.bin → decode → encode`
đều ra file byte-identical với bản gốc.

**Riêng v2** (đổi advance) thì `charWidth`/`strWidth`/`doChu`/`XuongDong` **cố ý khác**
bản v1 đúng bằng phần advance đã nới; nét glyph và toàn bộ public API vẫn trùng khít
(20/20 member giữ nguyên descriptor, xem `compat-report.txt`).

## 7. Còn lại chưa làm được ở đây

* Chưa chạy trên KVM/Nokia emulator thật (không có sẵn trong môi trường này) —
  mới verify tới mức: bytecode 47.0 + StackMap CLDC hợp lệ + API scan sạch +
  chạy đúng trên JVM. Nên test thêm ít nhất 1 emulator gần thiết bị đích.
* Chưa thêm ký tự mới (`= _ ~ \``) theo yêu cầu giữ nguyên charset.
* Advance mới chỉ đảm bảo **không mất nét** giữa 2 glyph liền nhau. Muốn có thêm
  1 cột trống hẳn giữa viền của 2 chữ thì đổi công thức thành `+ 3`, đổi lại chữ
  sẽ dài thêm ~20 % so với v1.
