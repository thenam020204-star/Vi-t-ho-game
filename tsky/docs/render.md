# Phân tích vẽ chữ & bố cục (T2)

Số liệu: `scripts/count_callsites.py work/jp` (đếm theo bytecode `javap -c -p`, danh sách đầy đủ `work/callsites.txt`). Dòng dẫn theo mã CFR (`src/`); "bc" = offset bytecode trong `work/jp/<Class>.txt`.
Màn hình: `j.t × j.s` = 240×320 (p=0..5) hoặc 320×240 (p=6, `j.java:5077-5107`). Font gốc mọi nơi: `Font.getFont(0,0,8)` (hệ thống, nhỏ); riêng hàm viền chữ dùng `(64,0,8)`.
Giả định số đo: f12 trung bình ~6 px/ký tự Việt (chưa đo, cần đo ở T4 bằng `VH.stringWidth`), `VH.LH = 13` theo bản tham khảo.

## 1. Tổng số call-site (bytecode)

| API | Số | Ở đâu (class: số) |
|---|---|---|
| `Graphics.drawString(String,III)V` | **50** | Main/a 1, Main/e 10, Main/f 11, Main/g 1, Main/j 13, rf/b 10, rf/c 1, rf/e 3 |
| `Font.stringWidth(String)I` | 24 | Main/e 8, Main/f 2, Main/j 12, Main/k 1, rf/b 1 |
| `Font.charWidth(C)I` | 2 | Main/g 2 |
| `Font.substringWidth(String,II)I` | 2 | rf/b 2 (trong `rf.b.a/b(String,int)`) |
| `Font.getHeight()I` | 4 | Main/e, Main/f, Main/k, rf/b (đều trong `<clinit>`, gán `k.v`, `e.b`, `f.b`, `rf.b.b`) |
| `Graphics.setFont` | 10 | Main/a 1, Main/e 4, Main/f 1, Main/j 3, rf/b 1 |
| `Font.getFont(III)` | 6 | Main/e 1, Main/f 2, Main/k 1, rf/b 2 |
| `drawChar`, `drawChars`, `drawSubstring`, `charsWidth`, `getBaselinePosition`, `Graphics.getFont`, `Font.getSize/getFace/getStyle` | **0** | không dùng → patcher chỉ cần 5 loại: `drawString`, `stringWidth`, `charWidth`, `substringWidth`, `getHeight` (bỏ qua `setFont/getFont`) |

Không có `Alert`/`Form`/`TextField` hiển thị trong luồng chơi (xem script.md mục 5.6). Mọi chữ đi qua `Graphics.drawString`.

Số kỳ vọng cho `Patch.java` (QĐ 2): drawString 50, stringWidth 24, charWidth 2, substringWidth 2, getHeight 4 (nếu muốn vá cả 4 chỗ `<clinit>`). Một số chỗ nên bỏ khỏi "thay call-site" vì thay cả method (mục 3), khi đó số kỳ vọng giảm tương ứng — Patch phải in số khớp từng nhóm.

## 2. Bảng call-site theo method

Cột "Vai trò" suy từ nội dung CFR. `A`=thay call-site là đủ; `M`=nên thay cả method (mục 3).

| Class.method (javap) | CFR | bc: drawString | bc: stringWidth/khác | Vai trò | |
|---|---|---|---|---|---|
| Main/a.a(Graphics) | `a.java:381` | 63 | – | "không hỗ trợ dọc" (chết ở 240×320) | A |
| Main/a.paint | – | – | setFont@8 | | A (bỏ qua) |
| Main/e.a(G,String[][],..,boolean,int) | `e.java:67-146` | 329,393,514,591 | sw 200,409,531 | Danh sách có cột (cửa hàng, đồ) | **M** |
| Main/e.a(G,String[],..,boolean,int) | `e.java:148-258` | 337,399,571,584 | sw 210,518 | Menu/lựa chọn/ô lưu (type 0/1/2) | A (+ xem 4.6) |
| Main/e.a(G,int[],..) | `e.java:260-` | – | setFont×2 | icon list | A (bỏ qua) |
| Main/e.a(G,int,int,String) | `e.java:404-435` | 152,212 | sw 134,193 | màn "极品/初级青龙" (mua), `Main.j.cX==4` | A |
| Main/f.a(G,String,…) | `f.java:143-161` | 31..149 (10) | getFont@6, setFont@9 | **viền chữ 8 hướng** | **M** |
| Main/f.paint | `f.java:290` | – | sw 234 | màn hỏi nhạc (sẽ bỏ ở T5) | – |
| Main/f.b(G) | `f.java:410` | 63 | – | "không hỗ trợ dọc" | A |
| Main/g.a(char[],G,…) | `g.java:21-45` | 116 | charWidth 128,166 | **vẽ thoại từng ký tự** | **M** |
| Main/j.x / y / z | `j.java:2028 / 3436 / 3937` | 22 / 32 / 32 | – | phím mềm "确定" trái, "退出", "返回" phải (`t - k.w*2 - 1`) | A + sửa tọa độ (4.7) |
| Main/j.i(G) | `j.java:3568` | 45 | – | "暂停" (`t/2 - k.w`) | A |
| Main/j.f(G) | `j.java:3470` | – | sw 119,181,240,294 | **menu chính** (7 mục) + viền (rf.b.a@262,316) | A (+ 4.8) |
| Main/j.a(G,e,e) | `j.java:2170` | – | sw 902,1345 | HUD chiến đấu (tên quái, `"" + cấp + "级"`) + rf.b.a×6 | A (+ 4.9) |
| Main/j.a(G,int,e[]) | `j.java:3682` | 161,324,1184 | sw 294,1172 | màn tiên sủng gửi/kho ("没有寄存的仙宠", tên giữa ô) | A |
| Main/j.b(G,String) | `j.java:3942` | – | sw 60 | **hộp xác nhận 2 dòng** (8 chỗ gọi) | A + 4.4 |
| Main/j.a(G,String,int,int) | `j.java:4087` | 204 | setFont@150 | **trang trợ giúp/giới thiệu** (rf.b.d, 2 chỗ + bF=true mỗi khung) | A + 4.5 |
| Main/j.a(G,String,int,int,int,int,int) | `j.java:4128` | 96 | setFont@11 | **ô mô tả cuộn dọc** (13 chỗ gọi) | A + 4.3 |
| Main/j.b(G,String,int,int,int,int,int) | `j.java:4153` | 80 | sw 51, setFont@22 | **dòng căn giữa** (5 chỗ gọi: hộp xác nhận, `c(g,str)`, thông báo `di`) | A |
| Main/j.s(G) | `j.java:5701` | 2181,2388,2591 | sw 2448,2640 | paint chính: debug `"cv:pak"` (5840), **`drawString(dk,0,0)` ở màn CG (5858,5879)**, "跳过" (5866,5885) + rf.b.a@2464,2656 | A + 4.10 |
| Main/k.<clinit> | `k.java:32-33` | – | getFont@4, sw@15 ("额"), getHeight@24 | gán `k.w`, `k.v` | A |
| rf/b.a(G,String,…) | `b.java:250-268` | 31..149 (10) | getFont@6, setFont@9 | **viền chữ 8 hướng** (13 chỗ gọi trong `Main/j`) | **M** |
| rf/b.a(String,int) / b(String,int) | `b.java:58-118` | – | substringWidth 52 / 102 | **ngắt dòng theo px** → `rf.b.c[]`/`d[]` | **M** |
| rf/b.<clinit> | `b.java:38-44` | – | getFont@28, sw@39, getHeight@46 | gán `rf.b.b` | A |
| rf/c.a | `c.java:41` | 141 | – | debug lưới map (`Main.j.c`, false) | A (bỏ qua) |
| rf/e.a/b/a(G,bool) | `e.java:233,297,387` | 998,1025,1746 | – | debug tên sprite (`Main.j.c`) | A (bỏ qua) |

Số chỗ gọi hàm bao (bc): `rf.b.a(G,String,…)` viền = 13 (toàn trong `Main/j`); `rf.b.a(String,int)` = 2 (`Main/e`, `Main/j`); `rf.b.b(String,int)` = 7 (`Main/e` 1, `Main/j` 5, `Main/l` 1 = loading); `Main.g.a(char[],…)` = 2 (`j.v@110`, `j.w@122`); `Main.e.a(G,String)` (hộp `di`) = 5; `j.b(G,String)` = 8; `j.c(G,String)` = 3.

## 3. Chỗ nên thay cả method (đề xuất hook)

| Method | Lý do | Hook |
|---|---|---|
| `Main/g.a(char[],G,int,int,int,int,int)I` (`g.java:21-45`) | Mỗi khung: `new String(...)` (dòng 22) + `cArray[n]+""` mỗi ký tự (dòng 29) + `charWidth` từng ký tự; ngắt dòng theo ký tự (không theo từ); đúng 2 dòng/trang; bỏ ký tự `[` `]` | ASM giữ nguyên descriptor, thân = `return VH.dialog(char[],g,w,x,y,color,start)`. `VH` tính **bảng trang** một lần cho (mảng, w) (cache so khớp tham chiếu `char[]`+w), ngắt ở dấu cách theo px, tối đa N dòng/trang (N=3, thử 4: khung cao 76 → 4 dòng·13=52 vừa), trả chỉ số ký tự **cuối trang chứa `start`** (hợp đồng: người gọi đặt `dP = ret+1`, kết thúc khi `dP >= length`). Vẽ từng dòng bằng 1 `drawString`, màu = tham số (`3217408` = 0x311B00) |
| `rf/b.a(String,int)` / `rf/b.b(String,int)` | Ngắt theo ký tự (cắt giữa từ), mảng cố định 100 dòng, `b` có 2 `System.out.println` **mỗi ký tự** (`b.java:89,100`), được gọi lại **mỗi khung** (bF=true) ở hộp xác nhận, trợ giúp, hộp `di` | Thân = `VH.wrap(String,int,dest)`: ngắt dấu cách theo px, có cache (1-2 mục, so `equals` nội dung vì chuỗi nối `+ "^"` mỗi khung là đối tượng mới), ghi vào `c/d` và cập nhật `e/f`; bỏ `println`. Giữ trần 100 dòng (kiểm tra) |
| `rf/b.a(G,String,int,int,int,int,int)` và `Main/f.a(…)` (viền 8 hướng) | 9 lần `drawString` mỗi chuỗi (3 nhóm màu) = 9× chi phí glyph | `VH.outline(g,s,x,y,c1,c2,anchor)`: cùng ngữ nghĩa (`c1==c2` → 1 lần; nếu không viền c1 quanh rồi c2 giữa). VH tự bỏ `setFont`; hỗ trợ anchor `0,17,33` (xem 4.1) |
| `Main/e.a(G,String[][],…)` nhánh `bl==false` (type 1) | Cột số lượng/giá đặt theo `stringWidth(stringArray[0][0]) + 20` (`e.java:115`): độ rộng tên của **hàng đầu** ("葡萄") làm gốc → tên dài hơn đè lên cột | Chỉ gọi từ cửa hàng `j.java:3994`. Hook: thay `a.stringWidth(stringArray[0][0])` bằng `VH.maxWidth(String[][])` (cache), hoặc cố định cột. Kiểm tra tổng: lề 15 + tên + `aM` (icon) + số + icon + giá trong rộng 148 |

## 4. Màn cần sửa bố cục

### 4.1 Hội thoại (j.v/j.w → Main/g.a) — QUAN TRỌNG NHẤT
| Mục | Giá trị |
|---|---|
| Hàm | `j.v` (`j.java:6520`), `j.w` (`:6545`, thêm `setClip`) gọi `g.a` tại bc `j.v@110`, `j.w@122`. Gọi từ paint chính khi `bj==true` (`j.java:5951-5957,6054,6180`) |
| Khung | `e.a(g,0,n3,dK)` (`e.java:388-402`): ảnh `ui_zhou_h.png` 16×76 vẽ 2 đầu + 3 viền màu; `n3 = s - 76`; chiều rộng `dK = t` (240 / 320). Phần trong cao 64 px (từ `n3+6`) |
| Vùng chữ | x0 = `P[13].w + 3 + k.w/2` = 19 + 6 = 25; y0 = `s - 76 + 8` = `s-68` (252 / 172); rộng `n = t - 2*19` = **202 (240) / 282 (320)**; giới hạn dòng `n - 5 - charWidth(ký tự kế)`; bước dòng `k.v` |
| Số dòng | Cứng 2 dòng/trang (`++n7 == 2` return, `g.java:35`). Khung chứa được 4 dòng×13 px (đáy chữ ở `s-16`, viền trong ở `~s-9`) |
| Người nói | Không có hộp tên: là tiền tố trong chuỗi (`j4#：...`), vẽ cùng dòng đầu |
| Mặt nhân vật | `da` (sprite `playerface/npc0face/npc3face/npc1face`, ≈ 105-167 × 168-218) vẽ **trước** thoại tại `(dz[dx]/2, s-76+12)` với `dz={136,126,167,135}` (`j.java:5951-5953,6051,6177`), neo sprite chưa rõ → cần xem ảnh chụp ở T4: nguy cơ mặt che chữ ở bên trái (x0=25) |
| Hiệu ứng chạy chữ | **Không có** (vẽ cả trang mỗi khung; `W` chỉ đếm `j.java:6530`) |
| Chuyển trang | Phím OK (`a.d().o()`): `dP = n4 + 1`; `dP >= dS.length` → `bj=false; b(-1); dP=0; Main.b.a(0); ++Main.l.a` (sang lệnh kế). Phím được tiêu thụ bằng `d=-99; a()` |
| Cấp phát mỗi khung | `new String(cArray,n5,len-n5)` + `cArray[n8] + ""` mỗi ký tự (≈ 60 đối tượng/khung) |
| Chữ số | Thoại dài nhất 315 ký tự Hán (~800 ký tự Việt) ≈ 22-25 dòng@202 px → 8 trang @3 dòng, nhưng thoại thường (TB 31 ký tự Hán ≈ 80 ký tự Việt ≈ 3 dòng) → 1-2 trang |
| Cmd 54 (thoại hướng dẫn) | Cùng `j.v/j.w` (cùng hook) |

### 4.2 Thông báo `di` — giữa màn hình
`Main/e.java:359-372`: rộng hộp `t - 40`, wrap `t-50` (190 / 270 px) qua `rf.b.b(di+"^", n-10)`; chiều cao hộp = số dòng × (`k.v`+10) (tự co dãn, căn giữa dọc); vẽ dòng căn giữa bằng `j.b(7 tham số)` (`:4153`). Tự tắt sau 20 khung (`j.java:5962-5966`, `cw>20`) hoặc OK. Không cần sửa bố cục, chỉ cần wrap tốt và trần dòng. Chuỗi dài: `rf/g.java:124-144`, `j.java:3238`, `3831` (~2-3 dòng). 20 khung có thể ngắn để đọc câu dài → cân nhắc tăng ở T7 (ngoài phạm vi).

### 4.3 Ô mô tả vật phẩm/kỹ năng/nhiệm vụ (`j.a(7 tham số)`, cuộn dọc tự động)
`j.java:4128`: vùng cắt `(x,y,w,h)`; bước dòng `rf.b.b + 5` (18); cuộn 1 px/khung đến hết rồi quay lại (`X`). Số dòng không giới hạn ngoài 100.
| Chỗ gọi (CFR) | w×h (px) | Nội dung | Ghi chú |
|---|---|---|---|
| 2958,2990,3004... (túi đồ `b(g,int)` 2890) | 130×60 (vật phẩm) / 90×60 (cạnh icon, tọa độ +50) | `E[][1]` (≤94 ký tự Hán) | ô cao 60 chỉ ~3 dòng@18; mô tả ~190 ký tự Việt ≈ 10 dòng@130 px → cuộn ~180 khung. Chấp nhận được, nhưng chậm với mô tả dài |
| 3342 (kỹ năng) | 130×50 | `F[][2]` (≤69 ký tự Hán) | |
| 3604 (tiên sủng đại toàn `j` 3590) | 130×60 | `z[][2]` (≤166 ký tự Hán ≈ 400 Việt ≈ 18 dòng@130) | **dài nhất**, cuộn ~320 khung (~30 s) |
| 3759 | 150×100 | | |
| 3998,4006 (cửa hàng) | 90×60 / 130×60 | `E[][1]` | |
| 4331,4333 (nhiệm vụ) | 130×60 | `I/J[][1]` | |
Đề xuất T7: giữ vùng, nhưng `VH` rút bước dòng về `LH+1` và nếu cần tăng tốc cuộn; không đổi kích thước hộp (ảnh nền cố định `ui_corner`/`P[11]` 148×68).

### 4.4 Hộp xác nhận (`j.b(G,String)` `j.java:3942`)
Rộng `t - 10` (230 / 310), wrap `n - 10` (**220 / 300 px**), **cao cố định 2 dòng**: `(k.v+10)*2+10` = 56; hai nút trái/phải vẽ bằng ảnh `P[10]`. Gọi 8 chỗ: thoát, thả sinh, ký gửi, nhận lại từ túi, âm thanh, ghi đè lưu (`do` ghép giá: `j.java:4045`)... Mọi câu dịch ≤ 2 dòng@220 px (~36 ký tự/dòng, **72 ký tự tổng**). `bF=true` mỗi khung → wrap lại mỗi khung (xem 5).

### 4.5 Trang trợ giúp / giới thiệu (`j.a(G,String,int,int)` `:4087`, gọi từ `a(G,String)` `:2875`)
wrap `t-20` (220/300); số dòng/trang `(s - 2*20)/(10 + rf.b.b)` = 12 (240×320) / 8 (320×240); lật trang bằng phím lên/xuống (`cW`). `dm` (~120 ký tự Hán → ~320 Việt ≈ 9 dòng) và `dj` (~190 → ~15 dòng) → 1-2 trang. Có `bF=true` mỗi khung và 400+ `println` mỗi khung ở bản gốc (xem 5).

### 4.6 Danh sách menu (`Main/e.a(G,String[],…)`)
| Dùng ở | Tham số | Hành vi |
|---|---|---|
| Lựa chọn script (cmd 26) `j.java:6492` | `x=20,y=35,w=190(P[12]),h=155`, căn giữa, type 1 | hàng cao `max(155/n, k.v+10)`=26 (6 mục tối đa → vừa); mũi tên `N[0]` ở dưới dòng được chọn; tên mục Việt ≤ ~28 ký tự (190 px) |
| Danh sách vật phẩm `j.java:3026` | w=148 (`nArray2[2]`), x=panel+65, trái, type 1 | `"tên    xN"`: ~148-45 = 100 px còn lại cho tên+bội số; chuỗi tên Việt ≤ ~12 ký tự để không tràn |
| Kỹ năng `3340`, tiên sủng `3738`, ô lưu `3898,4360` (w=120), đội hình `2566` (type 2, nhãn "一:" ở x=30(+44@320)), nhiệm vụ `4324` | căn giữa theo `stringWidth` | tự căn đúng với font mới; ô lưu chỉ rộng 120 px: "存档一" → "Ô lưu 1" ≈ 42 px OK |
| Cửa hàng `3994` | type 1, trái | **rủi ro** cột sau tên (mục 3) |

### 4.7 Phím mềm và nhãn góc
`j.java:2030` "确定" (x=1), `3438` "退出" và `3939` "返回" ở `x = t - k.w*2 - 1` (2 chữ Hán căn phải), `5866/5885` "跳过" ở `t - stringWidth("跳过")` (đã đo thật, OK). Với chữ Việt: `k.w*2` (24 px) sai → hook tại `y`/`z`: thay `drawString` bằng `VH.drawRight(g,s,y,anchor)` (x = `Main.j.t - width - 1`) hoặc đổi nhãn ngắn ≤ 4 ký tự ("Thoát"/"Về"). Cùng vấn đề: `k.w` (đo chữ "额") còn dùng ở `j.java:3573` ("暂停", `t/2 - k.w`) và dialog x0 (`k.w/2`) → định nghĩa `VH.stringWidth` của ký tự ngoài bảng (Hán) = **12** để giữ `k.w = 12`.

### 4.8 Menu chính (`j.f` `:3470`)
Mục `stringArray[0..6]` (7 chuỗi) vẽ tại `y = s - k.v*3`, căn giữa theo độ rộng chính nó; hai mũi tên `aO/aP` đặt ở ±`stringWidth(stringArray[1])/2` ("继续游戏", mục số 2) → mũi tên đứng cố định theo độ rộng nhãn mục số 2, nhãn khác dài hơn sẽ đè lên mũi tên. Khuyến nghị: nhãn ≤ độ rộng "Tiếp tục" (≈ 48 px) hoặc sửa tọa độ mũi tên theo nhãn hiện tại (`stringWidth(stringArray[cR])`), cùng hook `j.f`. Mục "更多游戏" (index 2) gọi `platformRequest` ra web quảng cáo.

### 4.9 HUD chiến đấu (`j.a(G,e,e)` `:2170`)
Toạ độ cứng cho 240×320 / 320×240 / p==2 (`t==320`, `p==2` ở 2257-2283): `"" + cấp + "级"` vẽ viền tại (35,285)/(77,200)/(5,200) và (175,25)/(150,25); tên đối thủ căn giữa `t/2` ở y=0 (đo `stringWidth(e3.aH)` bc@1345); thanh HP/MP bằng ảnh. "N级" → "Lv N" (xem script.md mục 7). Tên tiên sủng Việt dài hơn tên Hán (thường 2-4 chữ) → kiểm tra tràn ngang 240 px; số cấp ghép sau tên cần chừa chỗ.

### 4.10 Màn CG kể chuyện (`j.s` case 7/14, `j.java:5850-5890`)
Văn bản `dk` (cmd 49, ≤360 ký tự Hán ≈ 1000 Việt) vẽ bằng `j.a(7 tham số)` ở `(10, h_ảnh)` rộng `t-20` **và** `drawString(this.dk,0,0,0)` (bc@2388, 2591): dòng gỡ lỗi sót, vẽ cả đoạn dài thành một dòng trắng ở góc trên-trái (có thể đè lên CG). Hook: NOP hai call-site này (hoặc `VH.noop`). "跳过" ở góc dưới phải, giữ.

### 4.11 Màn loading và mẹo (`k.G/H`)
`l.java:172-192`, `j.java:449,459,494`: `dg = tip + "^"`; `rf.b.b(dg, t-20)` (bc `j.a(int)@206,287,482`) rồi hiện căn giữa bằng `j.c(g,str)`/`j.b(7 tham số)` (`j.java:4487-4492`: nền đen + ảnh + chữ trắng); rộng 220/300. Mẹo Việt ≤ ~120 ký tự → ~3-4 dòng, vừa màn hình.

### 4.12 Màn khác dùng cùng hàm (không cần sửa riêng)
Đội hình (`d(G)` 2528, "空" đỏ tại 2842/2849 qua `rf.b.a`), kho tiên sủng (`a(G,int,e[])` 3682, "没有寄存的仙宠" `drawString(…, 17)` căn HCENTER|TOP), thông tin tiên sủng (`j(G)` 3590: tên viền `rf.b.a(…, 33)` = HCENTER|BOTTOM, mô tả cuộn), túi đồ/pháp bảo/bán (`b(G,int)` 2890), nhiệm vụ (`p(G)`), lưu/đọc (`q/k`), âm thanh. Đã gom vào các hàm chung ở mục 3/4.

## 5. Cấp phát và hiệu năng trong paint liên quan chữ

| Chỗ | Vấn đề | Đề xuất |
|---|---|---|
| `g.java:22,29` | `new String`, `char + ""` mỗi ký tự mỗi khung | thay cả method (mục 3) |
| `rf/b.java:89,100` | `println("width2="+n)`, `println("width="+n)` mỗi vòng, string-concat | thay cả method (mục 3) |
| `j.java:2878`, `2877` (`bF=true` mỗi khung), `3957` (`string + "^"` + `bF=true`) | wrap lại toàn chuỗi mỗi khung (O(n·dòng)) | cache theo nội dung trong `VH.wrap` |
| `j.java:2958-3004,3990-4010` | dựng `String[]`/`String[][]` danh sách (nối chuỗi, copy 39×4 ô bảng `E`) mỗi khung | ngoài phạm vi chữ; nếu chậm thì cache ở T7 (không bắt buộc) |
| `j.java:2258-2283` | `"" + cấp + "级"` mỗi khung | `VH` không cache được; chi phí nhỏ, chấp nhận |
| `j.java:3474` (`new String[7]` mỗi khung), `4356` (`new String[3]`), `e.java:207,414` | mảng chuỗi hằng mỗi khung | chấp nhận hoặc ASM hóa hằng thành static |
| `j.java:5840` | `"" + cv + ":" + pak` vẽ đỏ ở (10,10) trên **bản đồ lớn** (không có điều kiện debug) | giữ nguyên hoặc NOP (hỏi quản đốc): sẽ thấy chữ `0:l0` trong bản Việt giống bản gốc |

Bộ nhớ: `VH` cần cache riêng (bảng trang thoại ≤ 1 mục, wrap ≤ 2 mục); không tạo `String` trong `paint` khi chuỗi không đổi (QĐ 4).

## 6. Chỗ phụ thuộc bề rộng ký tự Hán (kiểm kê)

| Biến / chỗ | Giá trị gốc | Dùng ở | Xử lý |
|---|---|---|---|
| `k.w = stringWidth("额")` | 1 chữ Hán (~12-13) | 5 chỗ: thoại x0 (`j.java:6529,6555`), "退出"/"返回" (3438,3939), "暂停" (3573) | `VH.stringWidth` cho ký tự ngoài bảng = 12; sửa 3 nhãn góc (4.7) |
| `k.v = getHeight()` | ~13 | 27 chỗ (`Main/e` 8, `Main/g` 1, `Main/j` 18): bước dòng, chiều cao hộp, vị trí phím mềm | `VH.getHeight = LH = 13`; mọi hộp co theo |
| `rf.b.b`, `e.b`, `f.b` | ~13 | `rf.b.b` ×10 (`Main/j`), `e.b` nội bộ | như trên |
| `f.c = stringWidth("她")` | 1 chữ Hán | `f.java:298` ("否" căn phải, sẽ bỏ ở T5) | không cần |
| 130/90/150 px cố định (ô mô tả) | ~10 / 7 / 12 chữ Hán | mục 4.3 | wrap theo px mới, dòng nhiều hơn nhưng cuộn |
| Menu chính: mũi tên theo "继续游戏" | | mục 4.8 | nhãn mục 2 làm chuẩn hoặc hook |
| `P[12]` 190, ô lưu 120, ô đồ 148 | | mục 4.6 | tên Việt ≤ giới hạn px, đo bằng `pixeltext measure` |
| Nhãn ô lưu "一:" ở x=30 (+44@320) | | `e.java:207,227` | "1:" |

Không tìm thấy: cắt `substring` theo chỉ số để xuống dòng (ngoài `rf.b.a/b`, `Main/g`), `length()*N`, vòng `toCharArray` để vẽ (chỉ `j.java:6226` thay chuỗi và `6570` đổi thoại thành mảng).

## 7. Tóm tắt đề xuất hook (cho T4)
1. `Patch.java` thay 3 nhóm call-site tĩnh: drawString (50), stringWidth (24) + charWidth (2) + substringWidth (2), getHeight (4). Bỏ qua `setFont/getFont`.
2. Thay thân 4 method bằng `invokestatic VH.*` (giữ descriptor): `Main/g.a(char[]…)`, `rf/b.a(String,I)V`, `rf/b.b(String,I)V`, `rf/b.a(G,String,IIIII)V` và `Main/f.a(G,String,IIIII)V`.
3. Hook nhỏ: ánh xạ "空" khi vẽ; NOP `drawString(dk,0,0)` (bc `j.s@2388,2591`); nhãn góc phải (`j.y`, `j.z`); mũi tên menu chính (`j.f`); cột cửa hàng (`e.a(String[][])`).
4. Đo ở T4/T7: neo mặt nhân vật (4.1), `LH` thực của f12, độ rộng tên Việt dài nhất của `E`, `z`, `F`.

## 8. Kết quả T4 (VH + Patch)
- Build: `./build.sh [jar_ra]` (`TSV=file.tsv` để thử bản dịch tạm; mặc định `dich/text_can_dich.tsv`). Thứ tự: giải nén goc → `repack` (chữ Việt vào pak/t/class) → `Patch` (ASM, in `logs/patch.log`, dừng nếu số call-site lệch) → + `VH.class` + `font/f12.bin` → zip → preverify → `jar_check`.
- Số call-site thực thay: drawString 29, stringWidth 24, getHeight 4 (+ thân 5 method QĐ 2b: `Main/g.a`, `rf/b.a/b(String,int)`, `rf/b.a(G..)`, `Main/f.a(G..)` chứa 21 drawString, 2 charWidth, 2 substringWidth).
- Màn CG (`j.java:5858,5879` `drawString(dk,0,0)`): trên jar gốc (`logs/shots_T4/goc_cg.png`) KHÔNG hiện chữ ở góc trái trên vì clip còn đặt từ `j.a(7 tham số)`; giữ nguyên, `VH.drawRange` bỏ qua phần ngoài clip nên không tốn chi phí vẽ.
- VH: ký tự ngoài f12 — chữ Hán/dấu câu Hán (>= U+2E80) = ô trống rộng 12 (giữ bố cục), còn lại = rộng dấu cách. Thoại 4 dòng/trang (hằng `PAGE_LINES`), ngắt ở dấu cách theo px, lề phải 5 px như code gốc.
- Chưa làm (T7): nhãn phím mềm căn `k.w*2`, mũi tên menu chính, cột cửa hàng, "更多游戏", hộp xác nhận 2 dòng.

## 9. Kết quả T5 (khởi động, QĐ 9)
- `Main/f.c()` chỉ còn nạp `/Image/namp.png` (`VH.logo()`); `paint` pha `u` vẽ `h` ở `(t/2, s/2)` anchor 3 (đã bỏ lệch -40); `run()` chèn `g=true; Main.j.bY=true; w=false; v=false; this.e()` ngay trước `u=false` (tránh `paint` rơi vào nhánh `w` khi `o[]`=null), rồi chạy khối `finally` gốc. Logo hiện ~2 s theo `Thread.sleep(2000)` gốc.
- Jar bỏ `Image/bg.png`, `logo0..5.png`, `logo51/52.png`; giữ `menu.png` (bìa menu chính trong `Main/j`) và `xjoy.mid`.
- `res_vi/Image/namp.png` = `goc/logo_namp_240x320.png` (bản 320x240 làm ở T9).
