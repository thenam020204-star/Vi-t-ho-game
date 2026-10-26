# Báo cáo Việt hoá ảnh đợt 2 (PNG nhúng uires.bin / animation.bin)

## 1. Định dạng container (big-endian, DataInputStream)
Loader: `w.java:122 cf.a("/bin/uires.bin",...)`, `n.java:1112/1954 n.a("animation.bin",...)` -> `n.java:1280-1330` (n.a(String,int[],ci[],bt[])) và `cf.java:155-199`; `n.a(String)` (n.java:979-982) chỉ mở `/bin/<name>`.
```
short nCi, short nBt                       (cf.java:161-162)
int   offCi[nCi+1]                         (cf.java:164-166) ; bỏ qua bằng skip(offCi[i+1]-offCi[i]) (cf.java:176)
blob  ci/cf (nCi mục, cf.a(DataInputStream) cf.java:55-125, kết thúc đúng offCi[nCi])
int   offBt[nBt+1]                         (cf.java:179-182) ; skip(offBt[i+1]-offBt[i]) (cf.java:194)
nBt bản ghi bt (bt.java:155-189):
  byte flag(0 = có bảng module) ; short d
  [flag==0] short e[d*2] (w,h) ; short f[d*2] (x,y)    <- toạ độ cắt module trong PNG
  byte g ; short h
  h>0 : byte table[g*h] (ĐÈ lên PLTE lúc chạy, bt.java:57-67) ; int len ; byte png[len]
  h==0: int len ; byte png[len]
```
- Độ dài PNG là **int 4 byte** ngay trước PNG; bảng offBt tính từ đầu vùng bt (offBt[0]=0), đủ để bỏ qua. **Không có checksum** (CRC chỉ trong PNG; game tự tính lại CRC PLTE khi đổi bảng màu).
- Mọi PNG chữ ở uires là loại h>0, g=1, table == PLTE (h = độ dài PLTE). Vì table đè PLTE nên **không được đổi độ dài PLTE**; script giữ PLTE nguyên, chỉ đổi chỉ số điểm ảnh (assert table==PLTE mới==PLTE gốc).
- Module (x,y,w,h) của từng nhãn lấy từ bảng e/f; cách ghép module vào khung lấy từ cf (script/cfparse.py).
- Repack chưa sửa: uires sha1 b272d47c...e8eebf, animation sha1 e0a7110c...c783d5c648 — **giống từng byte** (build_all.py assert).

## 2. Kết quả
Container mới: `viet/bin/uires.bin` (100911 B), `viet/bin/animation.bin` (661033 B; nhỏ hơn vì nén lại zlib 9, offset được tính lại). Parse lại OK, 147 PNG còn lại giống hệt byte, kích thước + mode PNG không đổi, pixel ngoài box = 0, pixeltext check 0 lỗi. Chi tiết từng ô: `anh_nhung.tsv`; ảnh so sánh `compare/`.

## 3. Bỏ qua / lưu ý
- **Tên tướng** uires off 95350 và animation off 446653: sprite sheet 12 ô, mỗi ô 1 chữ Hán (曹操 诸葛亮 吕布 关羽 马超 司马懿), code ghép theo index -> KHÔNG làm, cần quản đốc quyết.
- uires off 104649: 4 ô, mỗi ô 1 chữ xếp dọc (恶战已死?) -> bỏ qua (cùng lý do).
- animation off 278962: icon nhỏ có chữ Hán li ti (关卡, 剧本...) trong icon 16px -> chưa làm.
- Ô 11x11 (武 甲 鞋 饰 ở 76573) chỉ đủ 1-2 chữ nên viết tắt: Vũ / Gi / Hà / Sứ (xám, không viền) — viết tắt gượng, nên xem lại.
- 76573: 装备 -> "Đồ", 成就 -> "Đạt", 暴击 -> "Crit", 开关 -> "Mở"/"Ko" (ô 13x13), 武将选择 dọc 战役选择 (84345, 20x71) chỉ còn "Ải".
- 84345: 最终得分 chia 2 module chồng nhau (20 và 30): "Điểm" + "cuối".
- Ảnh hiệu ứng (击杀 Hạ gục, 连斩 Liên trảm, 暴击 Chí mạng, 升级 Lên cấp) giữ alpha gốc (77/89, rất mờ), nền mực được inpaint, chữ f18 mảnh nên nhạt hơn bản gốc. 称号达成/成就达成 -> "Đạt danh hiệu"/"Đạt thành tựu", dựng lại nền tối + viền sáng; mặt nạ 53 = silhouette (alpha>0) của ảnh 52 mới.
