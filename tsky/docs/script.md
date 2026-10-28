# Phân tích script pak / bảng t / chuỗi class (T1)

Số liệu do `scripts/dump_pak.py work/x` sinh ra. Dòng dẫn theo mã CFR (`src/`), trừ khi ghi "bc" (bytecode, `work/jp/*.txt`).
Ý nghĩa lệnh suy từ tên hàm gọi; cột "Trường chữ" là chắc chắn (đã đối chiếu code + dữ liệu), cột "Ý nghĩa" là suy luận.

## 1. Đường đi của một blob script

| Bước | Nơi | Ghi chú |
|---|---|---|
| Đọc pak | `Main/h.java:48-61` (case 0) | `short`(bỏ), `byte n` (≤127), n × {`readUTF` tên, `short len` (có dấu), `byte[len]`}. Không đọc thêm byte nào sau blob cuối (`assert p==len` đúng cả 78 file). |
| Giải UTF-8 tay | `Main/l.java:609-640` `l.a(byte[],int,int)` | Không dùng readUTF. Hỗ trợ 1/2/3 byte. Buffer `char[len*2]` nên không tràn. Lỗi bị nuốt im lặng (`catch Exception {}`), chuỗi sẽ cụt chứ không crash. |
| **Mở rộng token** | `Main/l.java:25` bảng `l.j` (96 cặp), áp ở `l.java:80-86` | Với mỗi cặp `{tên, "xN#"}`: nếu blob chứa `xN#` thì thay mọi lần bằng `tên`. Làm **trên cả blob trước khi tách `^`**. |
| Tách lệnh | `l.java:88` `l.a(str,"^")` rồi `Main/b.java:16` `l.a(str,"|")` | `b.b`=mã lệnh (chuỗi số), `b.c`=các trường còn lại. Tách bằng `indexOf`, không có escape: **bản dịch cấm chứa `^` và `\|`**. |
| Thông dịch | `l.java:156-607` `l.a(Vector,j)` | `switch(parseInt(mã))`; máy trạng thái `Main/b.a` (0 nạp, 1 chạy, 2 xong→lệnh kế, 3 chờ UI). Lệnh lạ = bỏ qua. |
| Điều kiện / biến | case 5 `l.java:443-486`, case 7 `:487-`; `b(String)` `:591` | `5\|biến<op>giá trị` so số (op: `>= <= != = > <`, tìm bằng `indexOf`); sai thì nhảy tới lệnh `6`. `7\|biến=giá trị` ghi Hashtable `j.o` (lưu RMS, xem mục 6). Toàn ASCII. |

### Token trong `l.j` (`l.java:25`)
- 89 token ASCII (`a1#`..`i3#`, `z0#`) ↔ tên file sprite/ảnh (`box`, `guai5`, `npc1face`, `paopao0`, `player`...). Dùng làm tham số lệnh, **không dịch**.
- **7 token tên Hán** (hằng Hán trong class, chỉ xuất hiện trong trường chữ, không bao giờ làm khoá/tham số):

| Token | Gốc | Số lần trong pak | Gợi ý (PLAN) |
|---|---|---|---|
| `j4#` | 王大爷 | 53 | Vương đại gia |
| `j5#` | 李奶奶 | 22 | Lý bà bà |
| `j6#` | 小虎 (nhân vật chính) | 678 | Tiểu Hổ |
| `j7#` | 风雅灵 | 71 | Phong Nhã Linh |
| `j8#` | 共宛 | 44 | (hỏi quản đốc: có thể là "Cộng Uyển"/nhầm 共工) |
| `j9#` | 爷爷 | 99 | gia gia/ông nội theo ngữ cảnh (1 token = 1 bản dịch cho mọi chỗ) |
| `k0#` | 小灵子 | 54 | Tiểu Linh Tử |

  Dịch tên = sửa 7 hằng này trong `Main/l.<clinit>` (ASM), **không** đụng pak. Nhược điểm: `j9#` chỉ có một bản dịch, "ông nội" và "gia gia" không phân biệt được theo ngữ cảnh; muốn phân biệt phải dịch thẳng trong pak (khi đó token `j9#` ở chỗ đó bị thay bằng chữ thường, được phép vì token không có trong tham số).
- Cẩn thận: bản dịch không được chứa chuỗi khớp `[a-z]\d#` (cấm `#` là đủ).

## 2. Bảng mã lệnh (486 blob, 6 563 lệnh; thống kê `dump_pak.py`)

Trường đánh số từ 1 (sau mã lệnh). "Chữ" = trường hiển thị cần dịch.

| Mã | Số | Dạng | Ý nghĩa | Trường chữ |
|---|---|---|---|---|
| 16 | 1121 | `16\|mặt\|text` hoặc `16\|"主角"\|mặt\|text` | Hội thoại ở khung dưới (`l.java:193`, `j.f(String,String)` `j.java:6569`) | **text** = trường cuối (1114 lần 2 trường, 7 lần 3 trường). Trường 1 của dạng 3 trường ("主角") **bị bỏ qua** (code dùng `c[1],c[2]`) → không cần dịch. `mặt`=`token+số khung`, `-1`=không mặt |
| 54 | 43 | `54\|mặt\|text` | Thoại hướng dẫn (cùng khung, `l.java:401`) | text (trường 2) |
| 39 | 334 | `39\|text\|true/false` | Thông báo giữa màn hình `j.b(String,bool)` `j.java:6512` → `di` | text (trường 1) |
| 26 | 40 | `26\|lựa chọn 1\|2\|...` | Menu chọn (`l.java:207`); kết quả là **chỉ số** lưu `o["select"]` (`j.java:6500`), không so chuỗi | mọi trường (109 ô, ≤9 ký tự Hán) |
| 49 | 2 | `49\|CGn\|text` | CG kể chuyện (`j.b(String,String)` `j.java:1891`); `@`→`^` xuống dòng | trường 2 (≤360 ký tự Hán; 1 chỗ có `@`) |
| 51 | 47 | `51\|mã phím\|text` | Chặn phím bắt buộc: bấm sai phím hiện `text` (`j.d` `j.java:6242`, `Main/a.java:215-218`) | trường 2 (hiển thị qua `di`) |
| 60 | 1 | `60\|text\|4` | Quảng cáo "điểm tính phí" — `j.a(String,String)` (`j.java:1738`) **bỏ qua cả hai tham số** | không hiển thị; dịch cũng được, vô hại |
| 17 | 354 | 6 hoặc 7 trường | Tạo sprite/NPC (`j.a(5 tham số)` `j.java:941`) | không (trường "主角" ở dạng 7 trường bị bỏ) |
| 30 | 225 | 4/5 trường | Di chuyển NPC (`j.java:6360`) | không ("主角" ở dạng 5 trường bị bỏ) |
| 4 | 75 | `4\|bản đồ` | Nạp map `dat/<tên>.dat` (`j.a(String)` `:983`) | không |
| 3,35,42,48,50,52,53,55,57-59,111 | – | `x\|0` | Chuyển cảnh / reset / trạng thái UI | không |
| 5,6,7 | 769/769/495 | điều kiện/biến | xem trên | không |
| 11,27,28,31,36,37,38,40,41,43,44,45,46,56,62,15,47 | – | số/tên sprite | Camera, hiệu ứng, thêm vật phẩm (41), tiền/EXP (46), tiến độ nhiệm vụ (56 → `J[i][2]`), goto (62), kết thúc (15) | không |
| 20, 34 | 27/45 | `fight-mu\|...\|3%\|...\|pak\|-1` | Thiết lập quái gặp ngẫu nhiên / boss (`j.java:1683,1701`); `3%` bị `indexOf("%")` | không (giữ nguyên) |
| 29 | 188 | `29\|k3.p[\|0/1]` | Nạp pak kế + màn hình loading; chọn mẹo ngẫu nhiên `k.G[]` (`l.java:172-192`) | không |

Tổng: **1 668 trường chứa Hán** = 41 270 ký tự Hán, trung bình 24,7, tối đa 360 (cmd 49). Phân bố: cmd 16 = 1128 trường (34 957 ký tự, tối đa 315); cmd 39 = 334 (3 438, ≤32); cmd 54 = 43; cmd 26 = 109 ô; cmd 51 = 47; cmd 49 = 2; cmd 60 = 1; "主角" (bỏ qua) = 11 (cmd 16 ×7, 17 ×1, 30 ×3).

Chuỗi trong trường chữ có thêm: dấu câu Hán (`，1889 ：1164 ！1006 …764 ？530 。429 “”186 （）44 、39 ×18 —2`), token tên (`j4#`..`k0#`). Không có `[ ] # =` trong trường chữ; chỉ 1 chỗ có `@` (cmd 49).

## 3. Cách code xử lý chữ thoại — điều quan trọng cho dịch
- **Không code nào tìm `：` hay bất kỳ ký tự Hán nào trong chuỗi script.** `grep uff1a src` chỉ ra các hằng hiển thị. Tên người nói chỉ là tiền tố chữ trong text (`j4#：...` → `王大爷：...`), được vẽ như phần còn lại. → Dịch `：`→`:` an toàn.
- `Main/g.java:21-35` (vẽ thoại): bỏ qua (không vẽ) ký tự `[` `]` (hằng `a=91`,`b=93`) → bản dịch **không dùng `[` `]`**. Xuống dòng bằng px theo từng ký tự (không theo từ), 2 dòng/trang, xem `docs/render.md`.
- Thoại là `char[]` (`j.dS`, `j.java:6570`), phân trang bằng chỉ số ký tự. `dP` ≥ `dS.length` = hết thoại.
- Tên mặt: `j.f(...)` (`:6569`) tách chữ số cuối làm số khung và so tên với `{"playerface","npc0face","npc3face","npc1face"}`; mặt khác → không vẽ mặt. Toàn ASCII.

## 4. Bảng `t/item*.t` (`Main/h.java:295-337` case 3)
Header: `short`(0x0bb8 bỏ), `short rows`, `short cols`; ô theo hàng rồi cột. item1/2/8 là số (byte/byte/short); item3-7 là `readUTF`.

| File | Mảng | rows×cols | Cột (đã đối chiếu code) | Ô Hán | Ràng buộc |
|---|---|---|---|---|---|
| item3 | `k.F` (kỹ năng) | 25×9 | 0 hệ (số), 1 **tên chiêu**, 2 **mô tả** (≤69 ký tự), 3 cấp (số), 4 **tên trạng thái** hoặc `-1`, 5 xác suất `25%`/`-1`, 6 sát thương, 7 cấp yêu cầu, 8 MP | 70 ô, 1 453 ký tự | Cột 3,5,6,7,8 qua `parseInt` (`j.java:3373-3381,3720`); cột 5 qua `indexOf("-1")`/`indexOf("%")` (`:3390-3396`); **cột 4 so `indexOf` với 5 hằng Hán trong class** (xem mục 5). Chỉ dịch cột 1, 2, 4 |
| item4 | `k.z` (tiên sủng) | 50×3 | 0 **tên** (→ `e.aH`, `j.java:5426`), 1 chỉ số ảnh `guaiN.png` (số, ghép tên file `:3643`), 2 **mô tả** (≤166) | 100 ô, 2 629 ký tự | Cột 1 không được động tới |
| item5 | `k.E` (vật phẩm) | 40×4 | 0 **tên**, 1 **mô tả** (≤94), 2 giá mua, 3 giá bán (`parseInt` `:1770-1810,6274`) | 80 ô, 1 534 ký tự | Cột 2,3 giữ nguyên |
| item6 | `k.I` (nhiệm vụ chính) | 20×3 | 0 **tên**, 1 **mô tả**, 2 `"0"` (không dùng ngoài) | 40 ô, 447 ký tự | |
| item7 | `k.J` (nhiệm vụ phụ) | 30×3 | 0 **tên**, 1 **mô tả**, 2 trạng thái `"0"/"1"/"2"` (`.equals("1")` `j.java:610,625`; ghi bởi lệnh 56 `:5493`; **lưu RMS** `rf/b.java:538,690`) | 60 ô, 927 ký tự | Cột 2 giữ nguyên |

Tên vật phẩm `E[n][0]`/tên sủng `aH` bị nối chuỗi trong thông báo (`E[n][0] + "x" + n2 + "购买成功！"`...): thứ tự từ phụ thuộc hằng nối trong class, xem mục 7.

## 5. Chuỗi hiển thị bị so sánh (phải đồng bộ hoặc giữ nguyên)

| # | Nơi | So sánh | Nguồn giá trị | Quyết định đề xuất |
|---|---|---|---|---|
| 1 | `j.java:3399,3401,3403,3405,3407` và `:4590,4592,4595,4598,4601` | `F[n][4].indexOf("麻痹"/"禁锢"/"冰冻"/"燃烧"/"石化")` | `item3.t` cột 4 (5 giá trị Hán + `-1`) | Dịch **cùng một giá trị** ở cả 10 hằng và cột 4. Dùng từ 1 chữ không dấu cách trùng nhau, ví dụ Tê liệt / Cấm cố / Đóng băng / Thiêu đốt / Hóa đá; mỗi từ không được là chuỗi con của từ khác (kiểm tra khi chốt thuật ngữ). Ghi trong `dich/thuat-ngu.tsv` |
| 2 | `j.java:3085,3092` | `stringArray[0].indexOf("没有")` (phát hiện danh sách rỗng) | hằng `j.java:2925` "没有物品"/"没有法宝", `:2937` "没有物品可卖" | Cả 3 chuỗi bắt đầu cùng một cụm ("Không có …") và đổi hằng `"没有"` thành đúng cụm đó. Kiểm tra tên vật phẩm không chứa cụm này |
| 3 | `j.java:3912,4378` `.equals("空")`; khởi tạo `:322`; ghi/đọc RMS `rf/b.java:352,595` | ô lưu game rỗng | hằng "空" lưu **vào RMS** `xianchong1` cùng nhãn "存档一/二/三" (`j.java:4356-4397`) | An toàn nhất: **giữ nguyên "空" trong class**, để `VH.drawString` ánh xạ riêng chuỗi `"空"`→"Trống" khi vẽ (tương thích save cũ). Nếu dịch hằng: phải đổi cả 3 chỗ (322,3912,4378) cùng giá trị, save cũ sẽ coi như ô đầy |
| 4 | `j.java:2562,2842,2849,3763` | không so sánh, chỉ vẽ ("空"/"空出该位置") | – | Dịch tự do (hoặc chung ánh xạ ở mục 3) |
| 5 | `Main/e.java:414` ("极品青龙","初级青龙") | **không so sánh**; chọn bằng chỉ số `h` (`e.java:416-433`), chỉ `drawString` + `stringWidth` | – | Dịch tự do, đã kiểm: PLAN nghi ngờ nhưng không có `equals` |
| 6 | `GameMidlet.java:76-114` | Form/Alert/Command ("排行榜","确定","返回","系统提示") | chỉ khi `GameMidlet.i == true` (cờ debug, mặc định false, không có nơi nào gán `true`) | Chết, không cần dịch |

Không còn so sánh chuỗi hiển thị nào khác: mọi `equals/indexOf/Hashtable` còn lại là ASCII (tên file, khoá `o`, `"true"`, `"1"`, `"%"`, `"/"`, `"guai"`, `"zhua"`, `"125/100"`). Đã rà bằng grep toàn `src/`.

## 6. Lưu game (RecordStore) có chứa chuỗi hiển thị không?
- RMS `xianchong1` (`rf/b.java:331-360,590-600`): bản ghi 1 = 3 nhãn ô (`cM[]`) + 2 cờ âm thanh. **Có chuỗi hiển thị**: "存档一/二/三" và "空" (mục 5.3). Chỉ "空" bị so sánh; nhãn "存档x" chỉ để vẽ.
- RMS dữ liệu ô (`rf/b.java:421-540,621-700`): số nguyên, `t` (tên sprite ASCII, `-1`), Hashtable `j.o` (khoá/giá trị ASCII), `cN` (tên pak), `J[n][2]` (`"0/1/2"`), số lượng vật phẩm. **Không** lưu tên tiên sủng (`aH` dựng lại từ `item4` khi nạp qua `j.l().g(e)` `j.java:5426`). → đổi bản dịch tên/mô tả không làm hỏng save.
- Hệ quả: khi phát hành, tên RMS (`xianchong1`...) giữ nguyên là được; chỉ cần xử lý "空".

## 7. Phân loại chuỗi Hán trong class (118 hằng độc nhất ở `Main/j`)

| Nhóm | Số hằng | Vị trí `j.java` | Ghi chú dịch |
|---|---|---|---|
| Thông báo `di` (thành công/thất bại/tiền/đã chết/…) | 77 | 1141-1843, 2100-2760, 3097-3376, 3421, 3831, 4386-4837, 5832, 6305-6986, 7017-7173 | Hiện trong hộp tự co dãn (`e.java:359`, rộng t-50, tự xuống dòng, 20 khung). Nhiều chuỗi **nối** `tênSủng + "…"`, `tênVP + "x" + n + "…"`, `"" + cấp + "级"` (2258-2283), `"获得"+…+"经验^"` (2388), `…+"金"` (2403,2986): thứ tự từ buộc phải giữ dạng `[đầu] + biến + [đuôi]`. Với `级`: dùng hậu tố ngắn kiểu `" cấp"`/`"Lv"` hoặc ASM thay cả biểu thức nếu muốn "Lv5". HP/MP/EXP viết tắt Anh theo PLAN |
| Nhãn UI/hộp xác nhận | 27 | 2030,2034,2047,2063,3182,3438,3474,3535,3552,3572,3700,3806,3939,4171,4356,4362,5866,5877,5885,6621,6625 | Menu chính 7 mục (3474) rộng theo `rf.b.a.stringWidth(mục[1])` (xem render.md). Xác nhận `b(g,str)` (3942) chỉ cao 2 dòng cố định → câu ≤ 2 dòng ở rộng t-20. "更多游戏…wap.xjoys.com" (3535,3552) là quảng cáo + `platformRequest` (3540,3557): đề xuất ẩn mục "Thêm game" (ngoài phạm vi, hỏi quản đốc) |
| Văn bản dài | 2 | `dm` 335 (hướng dẫn, ~120 ký tự; có `^`), `dj` 336 (giới thiệu/nhà phát hành, có email/điện thoại/`^`) | Hiện bằng `j.a(g,String,int,int)` (4086) phân trang bằng phím lên/xuống. `dj` cần quản đốc quyết định giữ/ghi chú nhà phát hành gốc |
| Tiền tố/placeholder/trạng thái | 6 | 322,2557 "空"; 596,617 "目前没有任务"; 865,870,889 "麻痹状态,"/"禁锢状态,"/"石化状态," (tiền tố `dl` ghép với 2486,2495,3369,3376) | "空" xem mục 5.3 |
| **So sánh** (mục 5) | 6 | 3399-3407 (5), 3085 (1) | |
| Gốc `Main/k` (`k.G` 9 mẹo, `k.H` 5 mẹo) | 14 | `k.java:47-48` | Hiện ở màn loading: `dg = G/H[rand] + "^"` (`j.java:449,459,494`, `l.java:180`), căn giữa qua `rf.b.b(dg,t-20)`. Chứa `“ ”`→`"`. Chỉ hiển thị. Lưu ý 2 mục `G` trùng nhau (hỏa thử) |
| `Main/l` (7 tên NPC) | 7 | `l.java:25` | Mục 1; chỉ hiển thị |
| `Main/e` | 6 | 37 "她" (đo bề rộng 1 chữ Hán), 207 "一:/二:/三:" (nhãn ô lưu, dòng `e.java:207`), 414 "极品青龙/初级青龙" | "她" dùng để đo `a.stringWidth`, kết quả không dùng sau đó; "一:" đổi "1:" |
| `Main/f` | 5 | 75,290,292,297,298 | Chỉ ở khởi động (T5 sẽ bỏ pha hỏi nhạc) |
| `Main/a`,`Main/f:410` | 2 | "该游戏不支持竖屏" (khi `Main.j.p==2`, ngoài phạm vi) | Bỏ qua |
| `GameMidlet` | 5 | 76-114 | Chết (mục 5.6) |
| `rf/b` | 2 | 43 (đo "她"), 125 (log lỗi) | Debug, không dịch |
| `rf/e` | 2 | 394 (log) | Debug |
| `rf/g` | 3 | 124,134,144 (thông báo qua `di`: nước sâu/cao/nham thạch) | Hiển thị |
| `Main/k:32` "额" | – | `k.w = D.stringWidth("额")` | Đo 1 chữ Hán, xem render.md |

## 8. Dữ liệu nhị phân: `dat/*.dat`, `sr/*.sr`, ảnh
- Quét UTF-8 toàn bộ `dat/` (52 file), `sr/` (97 file): **0 chuỗi Hán**. Các "Hán UTF-16" mà inventory báo ở `26xueyewai.dat`, `30xuecunzi.dat` (và gần như mọi dat/sr) là **nhiễu**: file là ô bản đồ/khung sprite nhị phân; chuỗi duy nhất đọc được là tên PNG (`map4.png`, `s0.png`, `tx_men.png`...) qua `readUTF` (`h.java:67-76`). Không cần xử lý.
- `Image/*.png` (326) có 1 trùng khớp ngẫu nhiên (`tx_huifu.png`) — dữ liệu ảnh, bỏ qua. Ảnh có chữ xử lý riêng ở T8.
- Rác: `dat/%u590d%u4ef6 1mucunzi.dat` ("复件 1mucunzi.dat" = bản sao); kiểm tra có được tham chiếu không trước khi giữ (nên giữ nguyên để khỏi đổi kích thước/thứ tự).

## 9. Thống kê & ước lượng kích thước

| Chỉ tiêu | Giá trị |
|---|---|
| File pak / blob | 78 / 486 (74 file có >1 blob, tối đa 23 blob/file) |
| Trường Hán trong pak | 1 668 (41 270 ký tự), tên blob ASCII (`0`, `s1`, `say52.s`...) |
| Blob lớn nhất | `k3.p` blob `0` = **14 701 B** (Hán 10 064 B + không Hán 4 637 B); kế: `k1.p/0` 11 218 B, `n9.p/0` 7 281 B, `l0.p/0` 4 140 B |
| Ước lượng sau dịch của blob lớn nhất (non-Hán + ×1.6·Hán) | **20 739 B** (< 32 767). Mức dự phòng: ×2,2 cho Hán cũng chỉ 26 777 B. Không blob nào vượt 32 767 ở ×1.6 hay ×2.2 |
| File pak lớn nhất | `k3.p` 20 415 B (7 blob, tổng 20 366 B) → ~30 KB sau dịch; heap: tất cả blob của 1 pak bị giải sẵn thành `String` + `b[]` khi nạp (`l.java:70-100`), ~2 byte/ký tự: ≤ ~60-80 KB cho pak lớn nhất, chấp nhận được ở heap vài trăm KB nhưng nên đo ở T4 |
| `readUTF` mỗi ô | item4 ô dài nhất 166 ký tự Hán (~500 B sau dịch), item5 94, item3 69: xa ngưỡng 65 535 |
| Ô Hán `item*.t` | 350 ô (item3 70, item4 100, item5 80, item6 40, item7 60), 6 990 ký tự |

## 10. Rủi ro / việc cần làm cho T3-T7
1. Token tên Hán (mục 1) nằm trong class, không trong TSV pak: T3 phải đưa 7 hằng `l.j` vào danh sách "chuỗi class" và đặt cột ràng buộc "dịch đồng nhất".
2. Hai bộ chuỗi so sánh (mục 5.1 và 5.2) cần cột "khoá đồng nhất" trong TSV và kiểm tra tự động (`repack` báo lỗi nếu cột 4 của item3 ∉ tập 5 giá trị + `-1`).
3. Sentinel "空" (mục 5.3) và save cũ: dùng ánh xạ hiển thị trong VH.
4. Trường chữ trong cmd 49 có `@` thay `^`; giữ `@` làm ký hiệu xuống dòng (`j.java:1895`). cmd 51 chặn phím: câu hướng dẫn nên khớp phím thực tế ở bản 320x240 (QĐ 10).
5. Ký tự `×` (18), `—` (2) trong script: ngoài charset f12 → đổi `x` và `-`.
6. `rf.b.a/b` giữ tối đa 100 dòng (`rf/b.java:32-33`): chuỗi dài nhất (`dm`, `dj`, CG 360 ký tự Hán ≈ 1000 ký tự Việt) khoảng 30-60 dòng ở rộng 220 px → còn dư, nhưng không được dài gấp đôi nữa.

## 11. TSV `dich/text_can_dich.tsv` (T3)
- Sinh bởi `scripts/extract.py`, ghép bởi `scripts/repack.py`, kiểm `scripts/selftest_repack.sh`. 2158 hàng: pak 1663, item3-7 350, class 145.
- Không đưa vào TSV: `"主角"` (5 trường, bị code bỏ qua); chuỗi class chết/debug (GameMidlet, Main/a, Main/f, rf/b, rf/e, "她", "额") và "空" (QĐ 7b, VH ánh xạ).
- Khoá class = nội dung gốc (repack thay mọi Utf8 == gốc) nên ASM đổi chỉ số constant pool không sao. Đặt `repack` SAU bước patch hoặc trước đều được.
- `repack --check` bắt: ký tự ngoài f12, còn Hán, `^ | #` cấm ở pak/item, `^` class phải đủ số lượng, token mới, trạng thái ×5 không đồng nhất giữa `item3` cột 4 và hằng `Main/j`, "没有" không là tiền tố của 3 chuỗi danh sách rỗng.
- Hàng ghi chú `CHI-DAU-CAU` (4 hàng chỉ có `……`/`！`): `dp prep` có thể loại khỏi dịch, nhưng f12 không có `…`/`！` Hán nên PHẢI dịch tay (hoặc quản đốc đổi bằng bảng ánh xạ) trước khi ghép.
