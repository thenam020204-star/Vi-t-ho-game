# T24a: tối ưu CPU mã MỚI (engine/menu/{dialog,hud,gfx,mmenu}.c, mui.h)

Tiếp từ WIP facdde4 (công nhân trước dừng do hạn mức). Đã kiểm lại toàn bộ; đổi duy nhất so với WIP: **hud.c (P5) viết lại** (xem 2). Không cấp phát động trong paint.

## Kết quả kiểm
- So md5 ảnh TỪNG khung giữa base và new: 4 cỡ (400x240, 600x270, 800x360, 1200x540) x 17 cửa sổ x 100 khung = **6800/6800 trùng** (bảng cuối).
- Hai lượt: (a) base 5ed9d9b vs WIP facdde4 (cây trước khi trộn T25), 6800/6800; (b) cây hiện tại (đã trộn T25) với bản base = cùng cây nhưng 5 file T24a lấy lại từ 5ed9d9b (worktree wt_t24a_base2) vs cây chính có hud.c mới, 6800/6800. Số đo dưới đây lấy từ lượt (b) (cùng interp.c nên chỉ khác phần T24a).
- Cửa sổ: mm_grid (khung 520, là màn nạp: không đổi), mm_slot/mm_b/mm_c (menu chính, lưới + chọn lớp), cut_a..e (hoạt cảnh đầu game, hội thoại chân dung ở cut_b/cut_d; fork tại 895, OK mỗi 40 khung), play_idle/right/attack (HUD), menu_3x3/bag/skill/equip/char (menu trong game).
- Bộ nhớ static thêm (memsz menu, base 5774616 -> new 7166160): **+1 391 544 B (~1,33 MiB)**: mui_cache 896 KB + lut 3x128 KB + pcs 2x72 KB (một phần nằm trong mui_cache). Nằm trong ngân sách 1,5 MB. (Số tuyệt đối cao hơn 3,3 MB cũ vì cây đã có SOFF 2,4 MB của T25.)

## 1. Chân dung hộp thoại (dialog.c, P1) — giữ nguyên từ WIP, đã kiểm
Bộ đệm ảnh đã phóng+lật (2 khe) + bảng đoạn đục theo hàng; vẽ đoạn bằng host_memcpy (>= 32 điểm), bóng bằng bảng tra mui_lut. Vô hiệu khi (con trỏ, kích thước, lật) hoặc NỘI DUNG (băm 64 bit điểm ảnh + các mục bảng màu được lấy mẫu) đổi => không vẽ ảnh cũ khi game nạp ảnh khác vào cùng địa chỉ. Ảnh > 196608 điểm hoặc > 1023 hàng: đường cũ.
Số đo (byte-khối/khung, khung hội thoại cut_d): 400: 17,8 M -> 4,1 M (0,23); 600: 18,3 -> 4,6 M (0,25); 800: 18,8 -> 4,5 M (0,24); 1200: 67,2 -> 10,9 M (0,16).

## 2. Nút HUD `art` (hud.c, P5) — ĐỔI so với WIP
Đo bảng alpha các ảnh nút: a==255 chiếm 0% (EMB, GLOW, FRAME, PAUSE, SAVE, SWAP) và 22% (SHOP); phần lớn điểm là nửa trong suốt => đề xuất "đoạn alpha=255 -> memcpy" gần như không bắn (WIP: art_row vẫn 3,39 M, chỉ -0,03 M/khung). Đã bỏ phần đoạn/memcpy (và mảng rn), thay bằng:
- mỗi điểm 1 từ u32 `ac_t.w` = màu nhân alpha (565) | ia=255-alpha << 16 | cờ << 24 (cờ = alpha==0 hoặc có thể tràn kênh: tính một lần lúc dựng cho trường hợp xấu nhất d=max);
- điểm "an toàn": pha màu song song 2 làn (r,b) trong một phép nhân + g, cộng thẳng vào 565 (không clamp, không tách s) => ~27 lệnh guest thay vì ~38; điểm cờ -> công thức cũ có clamp. Bit-exact (md5 trùng).
- bộ nhớ POOL: 4 B/điểm thay 3 B (+ rn nhỏ bỏ đi); POOL_B không đổi (2,5 MiB, dùng ~1,7 MB ở 2400x1080; thiếu chỗ -> art_slow như cũ).
Số đo play_idle (byte-khối/khung): 400: 13,31 -> 11,92 M (0,90); 600: 14,89 -> 13,08 (0,88); 800: 19,54 -> 16,52 (0,85); 1200: 32,26 -> 25,23 (0,78). (Số tuyệt đối cao hơn lượt (a) vì khung interp T25 tính vào; mức giảm ~1,4 / 1,8 / 3,0 / 7,0 M mỗi khung.)

## 3. Lớp tối nền menu trong game (gfx.c, P4)
Vùng >= 30000 điểm của mui_fill_a dùng bảng tra 65536 mục (mui_lut, 3 khe LRU, dựng ở lần hỏi thứ 2 cùng (màu,a5)) — bit-exact với span_a; `span` mới dùng fill16 (stm 16 byte/lệnh), mui_fill chép hàng bằng host_memcpy (nhân đôi khối); mui_gbm chỉ duyệt phần trong clip. KHÔNG làm cache backdrop FBOff (không chứng minh được vô hiệu đúng; để P4 phần còn lại).
Số đo menu_bag: 400: 8,50 -> 5,73 M (0,67); 600: 11,65 -> 7,41 (0,64); 800: 19,08 -> 11,28 (0,59); 1200: 39,04 -> 21,53 (0,55). Cả 5 cảnh menu: 0,54-0,71.

## 4. Nền menu chính (mmenu.c, P3) + 5. inline (mui.h, P14)
mmenu blit: chỉ duyệt phần nhìn thấy, không gọi hàm (công thức alpha thay chia 255 bằng nhân 32897>>23, đúng với mọi tử số dùng ở đây); nền dung nham dựng một lần giữ trong mui_cache (hai dải, hàng giữa đen) + logo/bản quyền chụp vùng chữ nhật; khoá = kích thước màn, k, lề, băm nội dung ảnh + bảng màu. mui_getpx/mui_put16 thành static inline (mui_F công khai).
Số đo menu chính (mm_b lưới, mm_c/mm_slot chọn lớp): 400: 33,3 -> 7,5 M (0,22) / 26,5 -> 4,2 (0,16); 600: 38,0 -> 11,0 (0,29) / 30,7 -> 5,9 (0,19); 800: 53,9 -> 16,6 (0,31) / 39,1 -> 9,4 (0,24); 1200: 92,5 -> 33,5 (0,36) / 77,4 -> 19,4 (0,25).

## Tái hiện
```
git worktree add /home/user/wt_t24a_base2 2f3c465; (cd wt_t24a_base2/hl5_android; git checkout 5ed9d9b -- engine/menu/{dialog,hud,gfx,mmenu}.c engine/menu/mui.h)
WORK=~/hl5work_t24a DEVDIR=~/hl5work_t24a/dev_base3 bash <wt_t24a_base2>/hl5_android/harness/l1/build_dev.sh ; tương tự dev_new3 từ cây chính
WORK=~/hl5work_t24a DEVDIR=<dev> python3 harness/t24a/run.py W H TAG mm,cut,play ; ... TAG mmb      # ra ~/hl5work_t24a/out2/
python3 harness/t24a/cmp.py out2/base3_WxH.json out2/new3_WxH.json out2/new3_cut_WxH.json
harness/t24a/run_reg.sh BASEDEV NEWDEV OUTDIR     # hồi quy l6b / t21 600x270 / t22
```

## Bảng md5 (lượt b, cây hiện tại; mỗi ô = số khung trùng / số khung, lấy từ out2/*.json bằng cmp.py)
| cỡ | menu chính mm_grid/mm_slot/mm_b/mm_c | hoạt cảnh cut_a..e | chơi idle/right/attack | menu trong game 3x3/bag/skill/equip/char | tổng |
|---|---|---|---|---|---|
| 400x240 | 100/100 x4 | 100/100 x5 | 100/100 x3 | 100/100 x5 | 1700/1700 |
| 600x270 | 100/100 x4 | 100/100 x5 | 100/100 x3 | 100/100 x5 | 1700/1700 |
| 800x360 | 100/100 x4 | 100/100 x5 | 100/100 x3 | 100/100 x5 | 1700/1700 |
| 1200x540 | 100/100 x4 | 100/100 x5 | 100/100 x3 | 100/100 x5 | 1700/1700 |

## Hồi quy (base vs new, cây hiện tại; run_reg.sh + l6b chạy riêng với WORK trỏ l3/dev tới dev tương ứng)
- check_t21 600x270: ok A/B/D = true, kết quả JSON + ảnh giống hệt base.
- check_t22 400x240: JSON giống hệt base (dùng elixir, potion6...).
- check_l6b 400x240 (menu + game): 18 file kết quả md5 giống hệt base.
Ghi chú harness: run.py lưu mmb dưới TAG+'mmb' (trước đây ghi đè file chính); run_reg.sh cần WORK có l3/dev cho l6b (tạo symlink như đã làm, xem lệnh ở trên).

## CÒN LẠI / chưa làm
- P4 phần cache backdrop (FBOff đã tối) cho menu trong game: chưa làm (cần chứng minh vô hiệu khi FBOff đổi); hiện menu trong game còn 5,4-22 M/khung (0,54-0,71 của base).
- P5: bỏ hướng memcpy; còn art_row ~2,3 M @800 (nút HUD là nửa trong suốt, phải pha màu từng điểm).
- Chưa đo trên máy thật; chưa commit (theo luật).
