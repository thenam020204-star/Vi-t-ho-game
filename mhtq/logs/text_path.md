# Đường đi của chữ trong MHTQ K790 (T02)

Nguồn: `goc/MHTQ_K790.jar`, bytecode ở `src/k790_javap/<class>.txt` (javap -c -p -constants, UTF-8; dòng dạng `offset: lệnh`), decompile CFR ở `src/k790_cfr/`. Mọi dẫn chiếu viết `Class.method(descriptor rút gọn) @offset`. Tên class/method đều bị obfuscate, nhiều method trùng tên `a`, nên luôn kèm descriptor.
Màn hình: `q.a()`/`q.b()` = `Canvas.getWidth()/getHeight()` sau `setFullScreenMode(true)` (`q.a(ZII)V`, q.java, `F=getWidth`, `G=getHeight`); với K790 là 240x320 (logo5.png cũng 240x320). Trong game còn nhiều nhánh `q.a() <= 176` cho máy nhỏ; chỉ nhánh 240 là quan trọng.

Mọi số nguyên đa byte đọc bằng `DataInputStream` nên **big-endian**. `readUTF` = u16 độ dài tính BYTE + UTF-8 chỉnh sửa (Java modified UTF-8). Mọi chuỗi `.dat` kiểm tra không có NUL, không có ký tự ngoài BMP, nên modified UTF-8 trùng UTF-8 chuẩn.

Tất cả định dạng dưới đây đã được kiểm chứng bằng cách parse rồi serialize lại, kết quả **trùng từng byte** với file gốc: script.dat, item.dat, mission.dat, skill.dat (parser nguyên mẫu ở `work/t02_proto/{sp2,dat,rt,xng}.py`, chạy từ thư mục `mhtq/` bằng `python3 -I`; T03 sẽ viết lại vào `scripts/`; bảng hằng CJK: `work/t02_proto/cjk_consts.tsv`). string.dat, mate.dat và monster.dat cũng parse hết file, không dư byte.

## (a) Định dạng từng file `.dat`

Danh sách resource do game mở (chỉ `ldc "/x.dat"` ở các chỗ sau): `/script.dat` (`i.run()V @6` -> `t.a(String)V`), `/item.dat` (`i.run @14`), `/mission.dat` (`i.run @123`), `/monster.dat` (`i.run @269`), `/mate.dat` (`i.run @321`), `/string.dat` (`i.run @439`), `/skill.dat` (`n.a()V @0`), `/layer.dat` (`w.a()V` ~@9), `/scene.dat` (`w.m()V`), `/sprite.dat` (`w.l()V @0`), `/sound.dat` (`j.<...> @69`), `/bf.dat` và `/bm.dat` (`f.a()Lf; @28, @36`).
Mở stream: `j.a(String)Ljava/io/DataInputStream;` (j.java:368) = `new DataInputStream(String.class.getResourceAsStream(path))`, đường dẫn phải bắt đầu bằng `/`.

Chỉ **5 chỗ** trong toàn bộ bytecode đọc chuỗi từ resource, vì vậy chỉ các file sau có chữ (grep `readUTF|readChar` trên cả 27 class):
- `t.a(DataInputStream,short[],int)V @99` `readChar` (script.dat),
- `k.a(DataInputStream)[String @21` `readUTF` (item/mission/skill),
- `i.run @426` và `@469` `readUTF` (mate.dat, string.dat), `i.<save>` @78, @87 (RMS, không phải file),
- `x.<init>(String,int,Z)V @73` `readUTF` (.bin hoạt ảnh, chỉ chứa tên ảnh, xem dưới).

### a1. `script.dat` (66763 byte, UTF-16BE trong từng chuỗi)
Loader: `t.a(String)V` (khởi tạo), `t.a(IZ)V` (đọc 1 chunk, lazy theo script id), `t.a(DataInputStream,short[],int)V` (đọc 1 tham số).
```
u16 H                         t.a(IZ) @25  readShort   (=31)
H byte                        @67 readByte lặp         bảng cờ ban đầu, vào byte[100] t.a (ở file toàn 0x00)
u16 N                         @78  readShort           (=110) số chunk (script)
N x u32 off[i]                @95 skip(n<<2), @100 readInt, @107 skip(off)
chunk i bắt đầu tại: o + 4*(i+1) + off[i]       với o = vị trí ngay sau u16 N (=35)
```
Offset có tính tương đối từ ngay sau chính phần tử i của bảng (`skip(n2<<2)` bỏ n2 phần tử, `readInt` đọc phần tử n2, `skip(off)` tính từ sau phần tử đó). Chunk 0 bắt đầu tại 475 = 35+4*110 và chunk cuối kết thúc đúng tại EOF. Chunk i dài `start[i+1]-start[i]`.
```
chunk:  u16 M (@112 readShort)  rồi M lệnh
lệnh:   s16 op (@136 readShort)   u8 n (@142 readByte)
        nếu op != -1 :  n tham số
        nếu op == -1 :  (điều kiện if) n bộ  {tham số A, u8 toán tử, tham số B}, rồi s16 bước nhảy (@305)
tham số: u8 type, rồi theo type (tableswitch @9 của t.a(DIS,short[],int)):
        10,11,12,17 : s16 giá trị        (10 = chỉ số biến t.a[] (byte[100]); 11 = hằng số)
        13,14       : u16 L, rồi L ký tự UTF-16BE (readChar x L). L đếm KÝ TỰ (char), không phải byte
        15,16,18    : u8 giá trị
        khác        : không có dữ liệu
```
Ví dụ hex đầu file: `00 1f` H=31, 31 byte 0, `00 6e` N=110, rồi `00 00 01 b4 | 00 00 01 b2 | ...` (off[0]=436, off[1]=434...). Chunk 8 bắt đầu tại byte 509: `00 3b` (M=59) `00 25 01 0b 00 00` (op 37, 1 tham số type 11 val 0) `ff f9 02 0a 00 03 0b 00 0f` (op -7, 2 tham số `(10,3)` `(11,15)`) ...
Ví dụ lệnh thoại: `00 02 03 0e 00 02 9b 54 59 29 0e 00 33 4e 0d ... 0b 00 00` = op 2, n=3, tham số `(14,"魔天")`, `(14,"不要以为...")`, `(11,0)`.

Khi nạp, chuỗi type 13/14 được đẩy vào `Vector t.a` (`t.a(DIS,short[],int)V @69..208`), tham số script chỉ lưu **chỉ số trong Vector**; chỗ trống (chuỗi `""` do op -6 xoá, `t.a(II)V case -6`) được tái dùng (`@137..159`). Hệ quả: chuỗi rỗng trong file không gây lỗi nhưng đừng coi `""` là khoá.

Thống kê parse thực tế: 110 chunk, 1650 chuỗi (toàn type 14; type 13 không dùng), dài tối đa 280 ký tự, 33 tên người nói. Op có chữ (kiểu chữ ký tham số) và trình xử lý (`y.a(I[S)V`, scene class `y`, CFR y.java):
| op | tham số | số lệnh | hiển thị |
|---|---|---|---|
| 2 | (14 tên người nói, 14 lời thoại, 11 chỉ số khung hình chân dung) | 685 | `y.a(I[S)V` case 2: `m.a(String,String,w,short)V`, hội thoại 2 dòng/trang, khoá = tên (xem d) |
| 3 | (14 chữ) | 238 | case 3: dẫn truyện 1 dòng/lần, khung giữa màn hình |
| 20 | (14) | 12 | `y.a(String)V` (private static): chữ chạy từ dưới lên, `j.a(text,q.a()-30)` |
| 22 | (10 biến, 14 câu hỏi) | 27 | `m.a(String)Z` hộp hỏi Có/Không, rộng 140 |
| 36 | (11 chỉ số cg, 14 chữ) | 3 | `y.a(int,String)V` ảnh cg2/cg3 + chữ cuộn, rộng `q.a()-20` |

Nội dung chuỗi thoại: ký tự điều khiển thực tế là `|` (xuống dòng, 12 lần), `#` (307 lần, mã màu `#RRGGBB` và `##` reset), `…`. Không có `\n` thật (nhưng `\n` cũng được coi là xuống dòng, xem c). Ký tự `　` (khoảng trắng CJK) dùng đệm đầu dòng.

### a2. `item.dat`, `mission.dat`, `skill.dat`
Loader dùng các overload của `k.a(DataInputStream)` (k.java), mỗi lần gọi đọc một mảng:
- `k.a(DIS)[S` = `u16 n` + n x `s16` (shorts)
- `k.a(DIS)[Ljava/lang/String;` = `u16 n` + n x (`u16 len`, `len` byte UTF-8) (@21 readUTF)
- `k.a(DIS)[[S` = `u16 n` + n x (`u16 m` + m x `s16`)
(còn `k.a(DIS)[I` và `k.b(DIS)[S` dùng u32 độ dài, game không gọi cho các file này).
Thứ tự gọi ghi ở `i.run()V` (item @14.., mission @123..) và `n.a()V @0..` (skill), kiểm tra bằng javap; toàn bộ file đọc hết, không dư byte:

| file | thứ tự mảng (S=short[], T=String[], SS=short[][]) | ghi chú |
|---|---|---|
| item.dat (4587 B) | S, **T(tên 50)**, S, **T(mô tả 50)**, S x9 | `i.run @21..108`: `r.a`(S) `r.a`(T) `r.b`(S) `r.b`(T) `r.c..r.k`(S). Dùng trong `r.<init>(II)V` |
| mission.dat (3230 B) | T, T, S x7, **T**, **T**, S x5, **T**, T, T, S x5 | `i.run @130..261`. Mảng 0,1 và 2..8 **bị bỏ** (`pop` @133,@138,..@173): thành tựu chưa dùng. Mảng 9,10,16 lưu vào `b.a`(tên, 21), `b.b`(mô tả, 21), `b.c`(mục tiêu/địa điểm, 21), dùng trong `b.<init>(I)V`. Mảng 17,18 (8 phần tử, nhiệm vụ phụ "扫荡新野..") bị `pop` @234,@239: **không hiển thị**; 5 mảng S cuối cũng bị bỏ |
| skill.dat (2016 B) | **T(tên 17)**, **T(mô tả 17)**, S x9, **T(师承 17)**, S, S, SS, S, S, SS, S | `n.a() @7..133`. `n.<init>(II)V` ghép: `"快捷按键："+..+"|"`, `"技能等级："..`, `"技能师承："+T[11]+"|"+T[1]` |

Chuỗi trong item/mission/skill: dài tối đa 7 ký tự (tên item), 32 (mô tả item), 25 (mô tả nhiệm vụ), 30 (mô tả kỹ năng). Có 2 mô tả item rỗng. Ký tự đặc biệt chỉ thấy `+ / %` và `　` đầu mô tả; không có `|` hay `#` trong dữ liệu gốc nhưng chuỗi ghép từ mã (`n.<init>`, `b.<init>`) dùng `|` làm xuống dòng, và mô tả đi qua `j.a(String,int)` nên `|` có tác dụng ngắt dòng.
Hex mẫu: item.dat `00 32` (S n=50) + 100 byte 0, rồi `00 32 00 12 e7 a7 b0 e5 8f b7 ef bc 9a ...` (T n=50, chuỗi đầu dài 18 byte "称号："). skill.dat `00 11 00 0c e6 99 ae e9 80 9a e6 94 bb e5 87 bb` (n=17, "普通攻击").
Đóng gói lại: ghi lại đúng chuỗi mảng như bảng (kể cả các mảng "bị bỏ"), `u16 len` = số byte UTF-8 (tối đa 65535).

### a3. `string.dat` (369 B) — KHÔNG phải chữ hiển thị
`u16 n=27` + 27 x `readUTF` (`i.run @446..476`) -> `i.a()[String` (getter `i.a()[Ljava/lang/String;`). Nội dung toàn tên file ảnh: `a_lubiao.png, a_zhangpeng.png, ..., shandong.png`. Dùng làm khoá nạp ảnh: `w.a()V` (layer) `"/"+i.a()[idx]` -> `g.a(String)` và `x.a(DIS)V` `g.a("/"+string)`. **Không dịch.**

### a4. `mate.dat` (39 B)
14 x `readShort` (`i.run @328..415`, `v.m..v.z`, chỉ số chiến đấu) rồi `readUTF` (`@426`) -> `v.b` (String static) = `"叶问天"` (`00 09 e5 8f b6 e9 97 ae e5 a4 a9`). Là **tên nhân vật chính**: `v.<init>()V @5-8` (`getstatic v.b` -> `putfield o.a`) gán `o.a = v.b`; hiển thị ở bảng thông số (`p.a(u,v,e,int)V @159` `getfield o.a`). Không có `equals` nào dùng giá trị này, nên đây là chuỗi **hiển thị** (không phải khoá); nên giữ cùng cách dịch với tên `叶问天` trong script.dat để nhất quán.

### a5. `monster.dat` (12 B)
6 x `readShort` (`i.run @276..314`, `e.a..e.f`), không có chữ.

### a6. `bf.dat`, `bm.dat`
Không có trong jar. `f.a()Lf; @6..52`: nếu `f.a:Z` (static, đặt `true` trong `f.<clinit> @10-11`) là true thì dùng font hệ thống; nhánh đọc `/bf.dat`,`/bm.dat` (font bitmap GB2312 12x12 + bảng ánh xạ) là **mã chết**. Không có chỗ nào gọi `f.a(Z)V` với false (`f.a(Z)V @0-1` luôn đặt true; grep `Method f.a:(Z)V` ở class khác: 0 kết quả).

### a7. Các file KHÔNG có chữ
- `layer.dat` (97362 B): bản đồ ô. `w.a()V` chỉ dùng `readUnsignedShort/readInt/readLong/readUnsignedByte/readShort/readByte` (w.java dòng 76..103): u16 N, N x u32 bảng offset, `skip(off[g-1])` rồi tham số bản đồ + 2 lớp short + lớp byte. Báo cáo `inventory.py` ghi 666 ký tự CJK do giải mã UTF-16 nhầm trên dữ liệu số: **dương tính giả**.
- `scene.dat` (17442 B): `w.m()V` (cùng bảng offset u16 N + N x u32), mỗi thực thể toàn số (short/byte), không `readUTF/readChar`.
- `sprite.dat`, `sound.dat`: số/âm thanh. `*.bin` (22 file): hoạt ảnh `x.<init>(String,int,Z)V @73`: `readUTF` là **tên ảnh** (`/a_rolehead01`, `/f_enemy01`...), không có CJK (đã quét cả 22 file); phần còn lại là số.
- `cpid.txt`: không class nào tham chiếu.
- 8 PNG gốc (`logo1..7.png`, `icon.png`): `l.<...>` nạp `/logo1.png..logo7.png`; logo5 có chữ "搜狐游戏中心", logo1 "星怡动", logo3 "9Game", đều là logo thương hiệu (tuỳ chọn khi làm ảnh).

## (b) Method vẽ/đo chữ

Toàn game chỉ có **hai class** gọi API chữ của MIDP (grep `drawString|drawChar|drawSubstring|stringWidth|charWidth|getFont|setFont|substringWidth` trên mọi class kể cả `com/yesky/game/XMIDlet`):

Class `f` (font): font hệ thống `Font.getFont(0,0,8)` = FACE_SYSTEM, STYLE_PLAIN, SIZE_SMALL (`f.<clinit> @0-7`).
| method | dẫn chiếu |
|---|---|
| `f.a()Lf;` singleton | `new f(Font)` @12-22 (nhánh hệ thống) |
| `f.<init>(Font)V` | gọi `f.a(Font)V` @15 để đo chiều cao |
| `f.a(Font)V` (private, đo thử) | `Graphics.setFont` @28, **`drawString("我是谁",0,0,20)` @46** lên Image 100x100, quét `getRGB` tìm dòng đầu `this.a` (b()) và dòng cuối, `this.c = cuối-đầu+1` (chiều cao thực của chữ). Chuỗi "我是谁" là hằng đo, không hiển thị. Nếu lỗi: a=0,b=11,c=12 |
| `f.a()I` chiều cao dòng | trả `c` (thực tế đo từ font hệ thống), fallback 12 |
| `f.b()I` | trả `a` (dòng đầu có mực), dùng để bù y khi vẽ |
| `f.a(Ljava/lang/String;)I` | **`Font.stringWidth` @12** (nhánh bitmap: cộng `f.a(C)I`) |
| `f.a(C)I` | **`Font.charWidth` @12**; nhánh bitmap (chết): `(c>>8==0 && (c&0x80)==0) ? 6+1 : 12+1` |
| `f.a(Graphics,String,II)V` (static) | vẽ bitmap 12x12 GB2312 từ bf/bm.dat bằng `drawLine`: mã chết |
| `f.a(Z)Z`... `f.a()Z` | `this.b != null` (có Font hệ thống) |

Class `u` (wrapper Graphics, mọi vẽ chữ đều qua đây):
| method | dẫn chiếu |
|---|---|
| `u.<init>(Graphics)V` | `f.a()Lf;` @32, `Graphics.setFont(f.a()Font)` **@59** |
| `u.a(C,int,int,int)V` vẽ 1 ký tự | căn lề theo `f.a(C)I` (@55, @78), `y -= f.b()` (@91), `f.a()I` (@121,@142), rồi **`Graphics.drawChar(c,x,y,20)` @185** (anchor TOP\|LEFT) |
| `u.a(String,int,int,int)V` vẽ chuỗi | tương tự với `f.a(String)I` (@55,@78), **`Graphics.drawString(s,x,y,20)` @185** |
| `u.a(String,int,int,int,int)I`, `a(String,II,II)V`, `a(String)V`, `a(String,int)V`, `a(String,String)V`, `a(String,String,IIIII)V` | bao quanh `u.a(String,IIII)V`: căn giữa theo ô, chữ giữa màn hình, phím mềm trái/phải (`@anchor 36/40`, lề 6px) |
| `u.a(String,int,int,int,int[])V` | chữ có viền: vẽ **9 lần** lệch (-1..1,-1..1) bằng màu viền rồi 1 lần màu chữ |
| `u.a(g,String,int[],int,int,int)V` | chuỗi **chữ số** vẽ bằng ảnh số (`j.a(String)[I` đổi '0'-'9','/','+'... thành chỉ số), không phải font |

Nơi đo bề rộng/chiều cao (đều gọi `f` qua `q.a().a()`/`u.a()`):
`j.a(String,char,int)[String @68,@159` (`f.a(C)I`); `k.b(String,int)[String @139`; `k.a(String)I @74`; `k.a(u,String,int,int,int)V @50`; `j.a(u,String[])V @26` (f.a()I); `l.b(I)Z @36,@227,@244`, `l.a([I)I @90,@403,@420`; `m.a(String,String)V @53`; `p.a(u,String,String[],int,Z,Z)V @32` (stringWidth); `p.a(u,String,int)V @114`; `p.a(u,String[],int,int)I @3`.

Hệ quả cho T04: chỉ cần thay `f.a()I`, `f.b()I`, `f.a(C)I`, `f.a(String)I`, `u.<init>` (@59 setFont) và hai lệnh vẽ `u.a(C..) @185`, `u.a(String..) @185` là mọi chữ trong game chuyển sang BitmapFont. Chiều cao hiện tại **đo động** từ ảnh vẽ thử, không phải `Font.getHeight`; với BitmapFont nên trả hằng (13) và `b()=0`.

## (c) Ngắt dòng / phân trang / khung thoại

### Đo độ rộng
Không có ngưỡng mã ký tự hay `len*k`: độ rộng = `Font.charWidth(c)` (font hệ thống SMALL) cộng dồn theo từng char (`f.a(C)I @12`). Với CJK máy thật thường ~12-13px. Nhánh dự phòng bitmap (chết) mới dùng 7/13 (`f.a(C)I` fallback).

### Hai hàm ngắt dòng (ngắt **từng ký tự**, không biết tách từ)
- `k.b(String,int)[String` (k.java:39), dùng cho **hội thoại và dẫn truyện**: duyệt từng char; `|` hoặc `\n` -> xuống dòng, reset độ rộng; `#`: nếu char kế là `#` thì bỏ qua 2 char (`##`), ngược lại bỏ qua **7 char** (`#RRGGBB`, không tính độ rộng; **nếu `#` ở cuối chuỗi sẽ ném StringIndexOutOfBounds** vì `charAt(i+1)`); `acc += f.a(c); if (acc > w) { cắt trước c; acc=0; xử lý lại c }` nên mỗi dòng có độ rộng **<= w**. `w < 20` bị nâng lên 20 (`k.b @?`: `n2 = n2 < 20 ? 20 : n2`).
- `j.a(String,char,int)[String` (j.java:244; `j.a(String,int)` = gọi với `'|'`), dùng cho mô tả, hộp, cuộn: giống nhưng **không hiểu `#`** (màu) và điều kiện cắt là `acc >= w` (dòng **< w**); `'|'` và `'\n'` xuống dòng.
- `k.a(String)I` (k.java:75): độ rộng một chuỗi, bỏ qua `|`, `\n`, mã màu như `k.b`. `k.a(u,String,x,y,anchor)V` (k.java:94): vẽ **từng char** bằng `u.a(char,x,y,anchor)` và tiến `u.a().a(c)`; `#RRGGBB` -> `Integer.parseInt(...,16)` đổi màu, `##` -> màu mặc định `0xFFFFDA`.
Vì ngắt từng ký tự, tiếng Việt sẽ bị cắt giữa từ; T06 phải thay bằng ngắt theo khoảng trắng và đo bằng BitmapFont.

### Các điểm gọi ngắt dòng và kích thước khung
| nơi | hàm | rộng ngắt | khung |
|---|---|---|---|
| Hội thoại (op 2, trận đấu `z.d()`, `s`): `m.a(String,String,w,short)V` -> private `m.a(String,String,w,z,Z,I,Z)V` | `k.b @113`, w = `q.a()-44` (=196) | **2 dòng/trang** (`n3 += 2`); vẽ bằng `p.a(u,String,String[],int,int,int[])V` |
| Dẫn truyện (op 3) | `y.a(I[S)V @273` `k.b(text,q.a()-50)` (=190) | từng dòng một, hộp trung tâm `p.a(u,String,III)V`: `x=25,w=q.a()-50`, chữ canh giữa theo `k.a(line)`; mỗi dòng hiện 15 khung hình rồi tự qua |
| Chữ cuộn (op 20) | `y.a(String)V @7` `j.a(text,q.a()-30)` (=210) | x=15, dòng cách 22px, cuộn lên 3px/khung |
| Cg + chữ (op 36) | `y.a(IString)V @121` `j.a(text,q.a()-20)` | x=10, dòng cách 21px, vùng dưới ảnh cg, cuộn |
| Hỏi Có/Không (op 22, `i.d()V`, ...) | `m.a(String)Z @6` `j.a(text,'|',140)` | hộp `p.a(u,String[],II)I` 160 |
| Hộp thông báo có tiêu đề `m.a(g,String,String,w,z)V @3` | `j.a(text,120)` | hộp 150 rộng, 5 dòng x 21px, `x=(q.a()-150)/2`, chữ rộng 120 |
| Mô tả item/kỹ năng/nhiệm vụ (`p.a(u,String)V` private, 2 chỗ gọi `p.@310`, `@306`) | `p.a(u,String)V @30` `j.a(text,132)` | clip `(22,116,132,81)` = **4 dòng cao 21px**; nếu hơn 4 dòng thì tự cuộn (b -= 3/khung, `p.a(u,String)V @139..164`), màu `0x452319`. Có cache theo chuỗi (`getstatic p.a:String` so sánh `if_acmpne` @9 + `equals` @16): không cấp phát mỗi khung |
| Nhật ký chiến đấu (`m.a(z,v,Z[],String[],int[])[I @705`) | `k.b(sb, n17-10)` (n17 biến cục bộ, chưa truy ra giá trị) | chưa kiểm tra |
| SMS/trả phí `l.b(I)Z @29`, `l.a([I)I @77` | `j.a(text,'|',q.a()-15)` | màn thanh toán |
| Màn lỗi `j.a(String,Throwable)V @?` | `j.a(msg,'|',q.a()-15)` | chỉ khi ngoại lệ |
| Màn "Giới thiệu" `m.a(String,String)V @28` | `j.a(text,'|',q.a()-26)` | cuộn dọc, dòng cách `f.a()+12` |

### Khung hội thoại cụ thể (K790, q.a()=240, q.b()=320)
`p.a(u,String,String[],int,int,int[])V` (p.txt, method bắt đầu @0), ảnh `p.a[0]=/i_16.png` (29x52), `p.a[1]=/i_15.png`, `p.b[0]=/i_13.png` (32x24), `p.b[1]=/i_14.png` (`p.a()V` p.java:91-95):
- Thanh hội thoại: cao `H = p.a[0].b() = 52`, vẽ từ `y = q.b()-H = 268`, rộng toàn màn (`p.a(u,g[],III)V @80`).
- Nền tên: `p.a(u,III)V @89` tại `(0, q.b()-80 = 240)`, rộng `n4 = 81`, **91 nếu `name.length() > 4`** (@47), cao 24; tên căn giữa tại `(n4/2, 240+12)` bằng `u.a(String,III)V @195`, anchor 3 (HCENTER\|VCENTER), màu `0xFFFFDA` (@173).
- Nội dung: `n3 = q.b()-H+3 = 271`; dòng i (0,1) có `clip = (22, 271+23*i, progress[i], 22)` (@116-133), chữ vẽ bằng `k.a(u,line,22, 271+23*i+11, 6)` (@142-157): x=22, tâm dọc cách đỉnh 11px, anchor 6 (LEFT\|VCENTER). Dòng cao 22, cách nhau 23.
- Hiệu ứng gõ chữ: `progress[i]` tăng 15px/khung (`m.a(...)Z..`, "nArray[i]+15") tới `k.a(line)`; hết hai dòng thì chờ phím, `n3 += 2` sang trang. Tức là tốc độ phụ thuộc **độ rộng pixel đo bằng cùng hàm** nên không phụ thuộc font nếu đo/vẽ nhất quán.
- Chiều rộng chữ khả dụng: 196px (ngắt) nhưng clip chỉ tính từ x=22 đến hết màn (218px).
Vì mỗi trang chỉ 2 dòng, văn bản dài chỉ làm tăng số trang, không bị tràn dọc. Rủi ro chính là tràn ngang nếu dùng đo khác với vẽ, và tên người nói dài hơn khung (rộng 91px).

## (d) Chuỗi CJK trong class: hiển thị hay khoá

Tổng 293 hằng `ldc String` có CJK (bảng đầy đủ: `work/t02_proto/cjk_consts.tsv`, mỗi dòng class/method/offset/chuỗi/lệnh kế). Quy tắc kết luận: **chuỗi chỉ được gọi `equals` ở 3 nhóm; còn lại chỉ đi vào `StringBuffer.append`, `m.a(String)`, `u.a(...)`** (grep `String.equals|equalsIgnoreCase|indexOf|startsWith|compareTo|hashCode|Hashtable` toàn bộ: chỉ `y.a(String)`, `j` (trim rỗng), `l` (khoá CID), `m @28` (`"97"`), `p` (cache), `t` (chuỗi rỗng), `w @461` (`"/shandong.png"`), `XMIDlet` (thuộc tính); mọi `if_acmp*` đều so object không phải String).

### KHOÁ (không dịch riêng lẻ; phải đổi đồng bộ nếu dịch)
| chuỗi | chỗ | bằng chứng |
|---|---|---|
| `叶问天`, `小唯`, `婴咛` | `y.a(Ljava/lang/String;)Ljava/lang/Object;` @18/@135 (`叶问天`), @57/@223 (`小唯`), @96/@311 (`婴咛`) | `String.equals` @21,60,99,138,226,314 chọn **ảnh/hoạt ảnh chân dung** (`i_45/i_44/i_43.png` hoặc `a_rolehead01..03`); gọi từ `m.a(String,String,w,z,Z,I,Z)V` (`i.a().a(name)` đầu hàm) |
| `叶问天` ở **script.dat** op 2 tham số 0 (243 lần), `小唯` (79), `婴咛` (59) | script.dat | giá trị truyền thành tham số `name` của `m.a(String,String,w,short)V` (y case 2) -> khoá chân dung |
| `小唯`, `婴咛` ở `v.a()Ljava/lang/String;` @7, @10 | trả tên đồng đội hiện tại | truyền làm `name` trong trận (`z.d()V`: `m.b(this.a.a(), h.b[l][n], z, false)`) -> khoá chân dung và cũng hiển thị trên nền tên |
→ Nếu dịch tên (ví dụ "Diệp Vấn Thiên", "Tiểu Duy", "Anh Ninh") thì phải đổi **cùng lúc** 3 nơi: script.dat op 2 tham số 0, hằng trong `y.a(String)` (3 hằng, dùng chung cho 6 lần `equals`), hằng trong `v.a()` (mate.dat chỉ để hiển thị, đổi theo cho nhất quán); hoặc giữ nguyên chuỗi gốc làm khoá và thêm bảng ánh xạ hiển thị. Tên khác (`诸葛亮 张飞 夏侯敦 魔天 剑仙 系统 ...`) không khớp khoá nào nên lookup trả `null` (không chân dung), chỉ hiển thị -> dịch được (kiểm tra: `y.a(String)` chỉ có 3 nhánh).
Khoá khác không phải CJK nhưng nằm trong class: `"/shandong.png"` (`w.@459-461`: chọn kiểu nền), thuộc tính `CID` và bảng `l.a[][]` (mã nhà cung cấp SMS), tên resource trong `h.c[]`, `h.a[][][1]` (`f_enemy01`...) và `string.dat`.

### HIỂN THỊ (dịch được), theo class
- `b`: `任务完成：` (`b.<init>(I)V @58`, ghép vào chuỗi nhiệm vụ có `|`).
- `n`: `快捷按键：` @234, `技能等级：` @271, `技能师承：` @348 (ghép trong `n.<init>(II)V` với `|`).
- `i`: `是否开启音乐？` `是` `否` (`i.run @512..522`), `是否关闭音乐` `是否开启音乐` (`i.d()V @6/@22`, vào `m.a(String)Z`), `暂无存档 第二存档 第三存档 **存档` (`i.<clinit> @46..60`, `i.a()[[String @35`; danh sách ô lưu, hiển thị ở màn lưu/đọc (`p.a(u,I,String[][])V`, có `确定/返回`)), `存档格式错误!`, `，存档格式错误！` (thông báo lỗi), `World.getRMSDes() error. 存档格式异常!` (log lỗi, dạng `j.a(String,Throwable)` hiện lên màn lỗi; không cần dịch).
- `j.a(u,String[])V @45`: `返回` (phím mềm). `q.paint(Graphics)V @62..70`: `是否返回游戏？ 返回游戏 退出游戏` (menu tạm dừng khi mất focus).
- `m` (79 chuỗi): menu chính `m.<clinit>`: **`m.b` (`新游戏 继续游戏 游戏帮助 游戏关于 音乐设置 退出游戏 厂商专区 搜狐游戏 更多游戏`) chỉ lấy `.length`** (`m.a()I @192` `u.a(Lg;IIIIZZIII)V` truyền `m.b.length`; nhãn thật nằm trong ảnh `c_f.xng`). `m.a` (`属性 铸剑 技能 装备 任务 声誉`) chỉ lấy `.length` (`p.b(u,I)V @74`, `m.@11`): nhãn thật trong ảnh `i_03.xng`. Còn hiển thị thật: `m.c` (menu tạm dừng `吞食天地 少女杀手 剑神一笑 回到游戏 保存记录 读取记录 音乐设置 退出游戏`, `m.a() @59`), `m.d` (`装备 丢弃`), `m.e` (`使用 丢弃`), `m.f` (`出战 铸剑`, `p.a(u,String,String[],IZ)V` @25 trong `m.a(v,I)V`); các thông báo `装备完毕 等级不足无法装备 不需要使用药品 恢复..点 成功使用称号 无法使用该物品 无法丢弃该物品 金钱不足 购买成功 出售成功 无法出售 铸剑成功 铸剑失败 攻击 防御 闪避 暴击 情感+ 情感已满 消费金钱 军粮丸 已取消 已添加 暂无药品 怒气不足 消耗 记录保存完成 正在保存.. 是否覆盖原来存档？`, trang trợ giúp `游戏帮助` (@467, @469 chuỗi dài mô tả/luật chơi) và `游戏关于` (@477/479: tên game, công ty, điện thoại, email). **Trang gỡ lỗi ẩn** `m.a(String,String)V @32`: chuỗi bản dựng (`项目号：R1|版本..|机型：Sony-Ericsson/K790|...SVN...`) chỉ hiện khi bấm chuỗi phím trong menu "关于" (m.a(String,String): `nArray={1,1,16,16,64,4,64,4}`); đây là dữ liệu bản dựng, **không dịch**.
- `p`: `暂无技能 暂无队友 暂无任务`, `请选择 未选择`, `个 总价： 金钱：`, `确定 取消`, `   等级` (có 3 dấu cách đầu), `是 否`, `加载资源(`.
- `s`: `魔天` (`s.b()V @376` gán `o.a`, `s.f()V @30` đọc lại làm tên người nói cho `h.d[1]`), `剑仙` (`s.d()V @37`, `s.f()V @86`, tên người nói cho `h.d[0]`, `h.d[2]` qua `m.a(String,String,w,Z)`): không khớp khoá nên chỉ hiển thị.
- `v`: `此人已出战 设置成功`, tên `小唯 婴咛` (xem khoá), `升至 级| 获得 信赖+1 信赖已满 少女杀手效果发动， 情感+1。 情感已满。 获得金钱 获得经验 等级达到 级上限 |等级升至 级`.
- `w`: `获得` (@1625) + tên vật phẩm, `宝箱是空的`.
- `y`: `金钱不足`, `返回`.
- `z`: `尚未习得技能 怒气不足 药品不足 真气不足 技能未习得 无法重复上次技能`, `主角阵亡...|可以选择复活功能`, `主角阵亡|可以使用|不屈意志|满血满气|原地复活`, `是否使用不屈意志`, `使用不屈意志|满血满气|原地复活` (dùng `|` ngắt dòng hộp `m.a(g,String,String)V`).
- `l` (SMS/trả phí, đường chạy phụ thuộc CID, thực tế nằm trong luồng "mua gói"): 8 chuỗi mô tả (mảng `l.<clinit> @52..95`) và các thông báo (`发送成功 发送失败 发送取消 短信发送中.. 点播 返回 进入吞食天地模式 获得金钱10000 ...`), đoạn pháp lý `您将选择使用由长沙掌讯公司提供的短信乐园业务...客服电话：...`. Có thể giữ hoặc dịch nhẹ; không ảnh hưởng gameplay.
- `h` (bảng dữ liệu tĩnh, `h.<clinit>`): `h.a[String](25)` tên màn/khu (`妖洞房 妖狐洞窟1-5级...`, có `天路??级`, vài `关卡1`: nơi giữ chỗ) hiển thị ở `w.<...>` (`u.a(h.a[this.b],...)`) và lưu RMS (`m.a(...) @405`); `h.b[String](25)` tên quái (nhiều `怪物名字1` là giữ chỗ chưa dùng) -> `o.a` ở `z.<init>`; `h.c[String](30)` **khoá** tên sprite (`f_enemy01`); `h.a[String][](13 x 4)`: `[tên quái, khoá sprite, tên người nói, lời thoại]` (`z.java:151,152,191,192`; ô 2,3 rỗng cho phần lớn); `h.b[String][](2 x 5)` lời nói đồng đội trong trận (`z.d() @?` `m.b(name, h.b[l][rand], ...)`); `h.d[String](3)` lời thoại boss (`s.d()V @39`, `@33`, `@88`); `h.e[String](3)` mô tả tính năng đặc biệt (`m.a() ...`, `吞食天地：...` v.v.); mảng 5 phần tử `我是提示信息1` là **biến cục bộ trong `h.<clinit>`, không bao giờ dùng** (giữ chỗ).
- `g.<clinit> @7..55` mảng 10 tên chế độ pha màu ảnh (`普通 灰色 1/3渐白 ... 做旧`): không có chỗ hiển thị (debug), không dịch.
- `f`: `我是谁` chỉ để đo font, không hiển thị.

## (e) Ảnh `.xng` (118 file) — giải mã được, đã kiểm chứng

Là PNG bị cắt và xáo trộn. Giải mã ở `g.b(String)[B` (g.java:443-477) với các hằng `g.a` (16 byte: `89 50 4E 47 0D 0A 1A 0A 00 00 00 0D 49 48 44 52`), `g.b` (12 byte đuôi chunk `IEND`: `00 00 00 00 49 45 4E 44 AE 42 60 82`), `g.c` (khoá XOR 16 byte `be cd b2 bb b8 e6 cb df c4 e3 c4 f3 b9 fe b9 fe`), `g.a(InputStream,ByteArrayOutputStream,I)V` (g.java:479-486). Đường dẫn gọi: `g.a(String)` đổi `.png` -> `.xng` (`g.java:432`).
Định dạng file `.xng` (n4 = 16 + số byte đã đọc sau byte cờ):
```
byte[0]  cờ (==1 : không có byte xáo trộn; ==0 : có)    (116 file cờ 0, 2 file cờ 1)
đầu ra bắt đầu bằng 16 byte g.a (chữ ký PNG + độ dài + "IHDR")
n4==16 : 1 byte chỉ số khoá k, 4 byte XOR g.c[k..k+3]   -> chiều rộng (u32 BE)
n4==21 : 1 byte k, 4 byte XOR g.c[k..k+3]               -> chiều cao
tiếp: các byte còn lại của IHDR (độ sâu, kiểu màu, nén, lọc, xen kẽ) + CRC IHDR (sao chép nguyên)
nếu cờ==0, tại n4==35: 2 byte u16 n3 (BE) = vị trí byte rác; tại n4==n3 đọc 1 byte rồi BỎ (không ghi)
phần còn lại (PLTE/tRNS/IDAT... nguyên bản) sao chép; cuối thêm g.b (IEND)
```
Hex đầu `a_box.xng`: `00 03 bb b8 e6 e6 04 b8 e6 cb cf 08 03 00 00 00 b5 20 06 db 00 74 ...` = cờ 0, k=3, XOR(`bb b8 e6 e6`)=width 45 (`00 00 00 2d`), k=4, height 16, rồi `08 03 00 00 00` (8-bit, palette) + CRC, `00 74` n3=116 (byte rác thứ 116 theo n4).
Kiểm chứng: decoder Python `work/t02_proto/xng.py` giải **118/118** file ra PNG hợp lệ (PIL load không lỗi; chế độ P, palette; cả `i_itemicon` 1000x20). Chưa viết bộ mã hoá lại; để đóng gói khớp từng byte cần lưu: cờ, khoá k1,k2, `n3` và giá trị byte rác (decoder đã ghi `meta` cho từng file). Quy luật đã kiểm chứng trên cả 116 file cờ 0: byte rác luôn bằng `n3 & 0xFF` (116/116), nên chỉ cần lưu cờ, 2 chỉ số khoá và `n3` khi mã hoá lại. Kích thước ảnh không nằm trong PNG chuẩn của file nên không thể chỉ vá IDAT: nếu đổi kích thước phải mã hoá lại width/height với khoá.

Ảnh có chữ (từ duyệt mắt 118 ảnh, giải ra PNG ở scratch): `c_2` (logo "梦回三国 残剑问情天", 240x165), `c_f` (nhãn menu chính, 9 hàng, chỉ `m.<...> @240` dùng), `i_01` (等级/称号/师承), `i_02` (攻击/防御/暴击/躲闪), `i_03` (属性 铸剑 技能 装备 任务 地图), `i_05` (邪正 道德), `i_06` (魏蜀吴), `i_07` (险恶 亲密 友善), `i_18` (生命 真气 经验 情感 信赖), `i_19` (技能/任务/成就/声誉... hai cột sáng/tối), `i_30` (叶问天), `i_31` (小唯 婴咛), `i_38` (战斗胜利), `i_39` (战斗失败), `i_40` (bảng danh hiệu 称号, 2 cột), `i_41` (商店), `i_42` (购买 出售 离开), `i_55` (任务完成), `i_96` (闪避, chữ nổi), `i_cost` (特效礼包/复仇之魂, 2 cột). Không có chữ ở các ảnh còn lại (nhân vật, quái, nền, tileset, số `i_24 i_25 i_27 i_48 i_97 i_98` chỉ có chữ số).

## Rủi ro/ghi chú cho các task sau
1. Khoá tên người nói (xem d): dịch phải đồng bộ 4 nơi hoặc giữ khoá. Chuỗi trong class `y` và `v` là các hằng cùng nhóm cần vá qua constant pool.
2. Ngắt dòng theo ký tự, `>` vs `>=` khác nhau giữa `k.b` và `j.a`, `#` cuối chuỗi gây lỗi, mã màu `#RRGGBB` + `##`. Bản dịch không được chứa `#`, `|` ngoài mục đích điều khiển.
3. Chiều cao dòng `f.a()` đo động; phải thay hàm này khi đổi font, nếu không khung sẽ lệch.
4. `u.a(String,...,int[])` vẽ viền 9 lần: chi phí khi dùng BitmapFont, có thể cache.
5. Chưa truy: độ rộng hộp nhật ký chiến đấu (`m.a(z,v,...)`@705), toàn bộ nơi `l` (SMS) có thật sự đạt tới với CID "00" của manifest hay không; chiều rộng thật của font SMALL K790 (không có máy thật, giả định 12-13px cho CJK).
6. `script.dat`/`item.dat`... chiều dài chuỗi bản dịch: `script.dat` chuỗi tối đa 32767 char (s16 độ dài), `readUTF` tối đa 65535 byte; bảng offset script phải tính lại khi đóng gói.
