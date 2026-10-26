# Định dạng file P1 (theo code loader, đã kiểm bằng `scripts/formats_p1.py`)

Mọi số big-endian (DataInputStream). `UTF` = `readUTF` (u2 độ dài byte + UTF-8). Kiểm: `python3 -I scripts/formats_p1.py $WS/x/1` -> tất cả OK, đọc hết đúng độ dài.

| File | Loader | Định dạng | Chuỗi CJK |
|---|---|---|---|
| t.dat, t2.dat, tips.dat | `h/b.b(String)` | `i2 n`, `n×UTF` | 80 / 13 / 16 |
| map.dat | `g/j.i()` | `i2 n`, `n×(i2 id, UTF tên bản đồ)` | 50 duy nhất (84 lần) |
| c.dat | `d/a.b()` | `u1 n`, `n×11×i2` (bảng chỉ số), `n×` nhân vật `d/g.a()`: `i2, UTF B, UTF U, UTF b, bool, i2, u1, u1` | 8 (3 chuỗi/nhân vật; `b` = `r0..r3` là khoá tài nguyên, ASCII, KHÔNG dịch) |
| item15.dat | `d/a.e()`->`d/a.c(String)` | `i2 n`, `n×(u1 loại=14, c.f.b(): i2,UTF,UTF,i2; u1,u1,i2,i2,i2,u1)` (`c/a`) | 32 |
| fb_skill.dat | `d/a.f()`->`d/a.c` | `i2 n`, `n×(u1 loại=17, c.f.b(), i2, i2)` (`b/a`) | 6 |
| monster.dat | `d/a.g()` | `i2 n`, `n×(i2 id, i4 off)`, `i4 blobLen`, blob; bản ghi `d/d.a()` tại `blob+off` (độ dài = off kế tiếp − off). Đổi độ dài chuỗi -> tính lại `off`, `blobLen` | 22 |
| exp.dat | `d/a.c()` | bảng số, không chuỗi | 0 |
| *.pak | `e/a.e(String)` | `i2 n`, `n×(UTF tên, i4 len)`, `n×blob`; **mỗi byte blob XOR 1** | 0 |

Ghi chú:
- t2.dat có 3 chuỗi rỗng (không-CJK) và `utfscan` bỏ qua chuỗi rỗng; không ảnh hưởng.
- Chuỗi của `item15`/`fb_skill`/`monster`/`c.dat` đi thẳng vào phần hiển thị (tên, mô tả); `c.dat` thứ 3 của mỗi nhân vật là khoá `rN` — giữ nguyên.
- Các chuỗi dạng "消耗N点体力, 攻+x ..." trong i.dat là chuỗi game tự ghép lúc chạy (xem `logs/verify_p1.txt`, DERIVED), phần hằng nằm trong `.class` (xử lý ở T5).

## Kết luận về `.pak`
Tất cả 11 file (`b, c, f, ic, mi0..mi5, mm`) chỉ chứa **ảnh PNG** (b/c/ic/mi*/mm) hoặc **khung hoạt ảnh `.fra`** (f.pak, 101 mục) sau khi giải XOR 1; tên mục đều ASCII, không blob nào có chuỗi CJK. Chữ trong ảnh (nếu có) thuộc việc vẽ lại ảnh, ngoài phạm vi trích text. Không cần xử lý `.pak` cho text.
