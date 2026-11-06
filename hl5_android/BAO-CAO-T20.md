# BAO-CAO-T20: tăng tốc `internalGrpCopyFrameBuffer` (chép điểm ảnh FBOff -> FB)

Công nhân: Sonnet (T20, người thứ 2; người thứ nhất mất do container khởi động lại, không báo cáo, WIP đã commit `64f9236`/`61a1752`). WORK `~/hl5work_t20`. Mọi mục dưới đây tôi đã kiểm lại từ mã máy / chạy lại, không tin WIP.

## KẾT LUẬN NGẮN
1. Hàm gốc 0x144cdc chép **từng điểm ảnh** (hàng trước, cột sau, ~40 lệnh kiểm/điểm). Khi `key==0`, `ctx[8]>254` (có dấu), `ctx[11]==0` (không callback), độ sâu đích 16/32, kết quả của nó là: với mỗi hàng k hợp lệ, một đoạn liền các điểm hợp lệ được chép nguyên từ nguồn sang đích. `fbcopy.c` tính khoảng hợp lệ bằng công thức đóng (cùng các phép so với mã máy) rồi gọi host `memcpy` (PLT 0x30110) cho từng hàng (hoặc một lần cho cả khối khi các hàng liền nhau). Mọi nhánh khác -> gọi nguyên hàm gốc.
2. **ĐẠT**: ảnh FB trùng từng byte ở **1562/1562 khung flush** x 4 cỡ (400x240, 600x270, 800x360, 1200x540) và 738/738 với interp.txt bật; bộ nhớ ngoài FB trùng (trừ mã hook, trừ stack chết dưới sp); fuzz trực tiếp 100.000 ca, 0 lệch; không cấp phát.
3. **Số đo**: byte-khối paint/tick 400x240 `36,97M -> 5,87M`; 600x270 `59,3M -> 6,85M`; 800x360 `102,8M -> 9,46M` (-90,8%); 1200x540 `225,3M -> 15,3M` (-93,2%). Tổng/tick 800x360 `103,6M -> 10,2M`.
4. Với mô hình thời gian (giả định ghi dưới): nhịp 20 Hz giữ được ở 800x360 từ **~100 triệu lệnh/s** (trước T20 cần ~800 triệu lệnh/s), 1200x540 từ ~200 triệu lệnh/s (trước ~1600). Khung trung gian của T18 bắt đầu có từ ~400 triệu lệnh/s ở 800x360 (trước T20 không bao giờ có ở <=1600).
5. Sửa thêm 1 lỗi harness (không phải lỗi game): mô hình `c5b.py` cũ có thể KHÔNG tiến thời gian ảo (làm tròn về cùng ms) -> `mid_per_tick` luôn 0. Đã sửa; số T20 trong báo cáo này là số sau sửa. (Số `c5b/c5c` của công nhân trước bỏ.)

## 1. Ngữ nghĩa chính xác của 0x144cdc (disasm `so.dis` dòng 289193..289420)
Chữ ký: `internalGrpCopyFrameBuffer(dst FbInfo*, dx, dy, w, h, src FbInfo*, sx, sy, ctx, u8 key)` (r0=dst, r1=dx, r2=dy, r3=w; ngăn xếp sau khi push 9 thanh ghi + sub 60: `[sp+0x60]=h, +0x64=src, +0x68=sx, +0x6c=sy, +0x70=ctx, +0x74=key`).
FbInfo: `+0 w, +4 h, +8 stride (byte), +0xc độ sâu (bit), +0x10 con trỏ điểm ảnh`. ctx (`_MC_GrpContext`): `+4 x0, +8 y0, +0xc x1, +0x10 y1` (clip, x1/y1 loại trừ), `+0x1c` màu key, `+0x20` alpha, `+0x2c` callback, `+0x30` đối số callback.

Cắt đầu hàm (0x144cf8..0x144d78), đúng thứ tự mã máy:
- `sx<0`: `w += sx; sx = 0` (0x144d14..0x144d2c). `sy<0`: **`w += sy; sy = 0`** (0x144d3c, 0x144d44: cũng trừ vào **w**, không vào h — quirk của game, giữ nguyên).
- `w + dx > dst.w` -> `w = dst.w - dx` (0x144d4c..0x144d58). `h + dy > dst.h` -> `h = dst.h - dy` (0x144d60..0x144d6c). `h <= 0` -> thoát (0x144d78). `w <= 0` -> vòng ngoài chạy h hàng nhưng không điểm nào được chép (0x144dbc).
- Vòng: hàng k = 0..h-1 (`dy' = dy+k`, `sy' = sy+k`), cột i = 0..w-1 (`dx' = dx+i`, `sx' = sx+i`). Điểm được chép khi **tất cả**: `src.h > sy'` (0x144dd8), `src.w > sx'` (0x144dec), `dst.h > dy'` (0x144e00), `dst.w > dx'` (0x144e10), `sy',sx',dy',dx' >= 0` (0x144e18..0x144e38), `ctx.x0 <= dx'` (e4c), `ctx.y0 <= dy'` (e44), `ctx.x1 > dx'` (e5c), `ctx.y1 > dy'` (e68). Mỗi lần đọc lại các trường từ bộ nhớ.
- Độ sâu = **dst.depth** (0x144e74): 16 -> nhánh 0x144f98 (ldrh/strh); 32 -> nhánh 0x144ecc (ldr/str); khác (8, 24...) -> **không chép gì**. Độ sâu nguồn không được đọc; nguồn đọc với độ rộng điểm = độ sâu đích, bước hàng = `src.stride`.
- Địa chỉ: nguồn `src.data + sy'*src.stride + sx'*es`, đích `dst.data + dy'*dst.stride + dx'*es` (`mla`, tràn 32 bit quay vòng).
- Điểm: `key != 0 && pixel == ctx[7]` -> bỏ qua (0x144fb8/0x144ef0). Rồi `ctx[8]` so có dấu với 254: `<= 254` -> `_midas_funcGrpPixelOpProc(16|32, dst_px, src_px, ...)` (pha trộn alpha); callback `ctx[0x2c] != 0` -> `blx` callback; còn lại ghi nguyên điểm nguồn (16 bit: `lsl/lsr #16` rồi `strh`).
- Thứ tự ghi: hàng trước cột sau, từng điểm một => nếu vùng nguồn và đích chồng nhau thì điểm sau đọc dữ liệu đã bị ghi. Hàm gốc KHÔNG phải memmove: không chọn chiều chép.
- Trạng thái đổi: chỉ vùng điểm ảnh đích (và bất cứ gì callback/pixelop làm; hai thứ này không bị đường nhanh đụng tới).

**Nhánh dùng khi chơi (đo bằng hook `cov.py`, 400x240, 1300 khung boot + 150 khung chơi)**: duy nhất `bl` gọi 0x144cdc là 0x1450a4 trong `MC_grpCopyFrameBuffer` 0x14506c (`key = 0` cứng ở 0x14509c/0x1450a0); `MC_grpCopyFrameBuffer` chỉ được nhảy tới từ 0xb1090 (Map::CopyFrameBuffer, `b` tail call). Quan sát mọi khung: dst = FB (16 bit), src = FBOff, alpha 255, callback 0, key 0, `dx,dy,sx,sy` không âm, ctx = cả màn hình. 4 lần/Map::Update. Phủ: StateGame (0x15e150) chơi + hội thoại (cờ `[EventProc+0x75]`: 132 khung boot, 457 lần gọi), menu chính (0x15e3d0), tiêu đề; `n_orig_kind = n_orig_overlap = n_empty = 0` trong mọi lần chạy chơi thật: **100% lời gọi thật đi đường nhanh**; các nhánh còn lại chỉ được kiểm bằng fuzz.

## 2. Cách thay (`engine/menu/fbcopy.c`, `hooks.def` dòng `# T20`, `build.sh` dòng `# T20`)
- `HOOK_BL(0x1450a4, 0x144cdc, fbcopy)`: thay `bl internalGrpCopyFrameBuffer` duy nhất. `fbcopy` cùng chữ ký 10 đối số (ABI y hệt).
- Đường gốc (gọi nguyên hàm gốc với đối số NGUYÊN BẢN) khi: `key != 0`, `alpha <= 254`, `callback != 0`, độ sâu đích không phải 16/32, `stride <= 0` (nguồn hoặc đích), **vùng ghi (bao địa chỉ đích) chạm FbInfo nguồn/đích hoặc ctx** (hàm gốc đọc lại trường ở mỗi điểm; 2 điều kiện cuối là phần tôi THÊM), hoặc **bao địa chỉ nguồn và đích giao nhau** (so [lo,hi) của hàng đầu/cuối, lấy rộng đủ nb byte). Hàm host chỉ có memcpy (không memmove) nên chồng lấp luôn trả về hàm gốc, không bao giờ chép sai chiều.
- Đường nhanh: áp lại các phép cắt như bảng trên (cùng quirk `w += sy`), `klo=max(0,-dy,ctx.y0-dy)`, `khi=min(h, src.h-sy, dst.h-dy, ctx.y1-dy)`, `ilo=max(0,-dx,ctx.x0-dx)`, `ihi=min(w, src.w-sx, dst.w-dx, ctx.x1-dx)` (`sx',sy' >= 0` luôn đúng sau khi cắt). Hàng: `nb = (ihi-ilo)*es` byte; nếu `nb == src.stride == dst.stride` và nhiều hàng -> một `memcpy` cho cả khối; còn lại một `memcpy` mỗi hàng, theo thứ tự hàng tăng (giống bản gốc khi `stride < nb`: hàng sau đè hàng trước).
- Host memcpy: `host_memcpy = 0x30110` (PLT: `add ip,pc,#0x100000; add ip,ip,#0x2e000; ldr pc,[ip,#0x68c]!` -> GOT 0x15e7a4 R_ARM_JUMP_SLOT memcpy). Đây là đúng lối mà game đã dùng (vd. 0x51ca0 chép FBOff). `fbcopy.o`: 286 lệnh, chỉ 2 tham chiếu ngoài (`internalGrpCopyFrameBuffer`, `host_memcpy`), không gọi libgcc/chia.
- Không cấp phát, không dữ liệu khởi tạo; chỉ bộ đếm `fbcopy_st` (24 byte bss, cho harness).

## 3. Bảng kiểm
| Kiểm | Kết quả |
|---|---|
| Ảnh FB từng khung flush base (bỏ T20, cùng cây) vs dev, v1.py: boot 1300 khung (tiêu đề, menu chính, GAME START, hội thoại) + 170 vòng chơi (đi, cuộn bản đồ, OK, đổi hướng) + menu trong game (3 lần mở, di chuyển/OK/CLR) + 30 vòng sau | **400x240 0/1562, 600x270 0/1562, 800x360 0/1562, 1200x540 0/1562 khung khác; lệch nhịp flush/tick 0** |
| Như trên với interp.txt=1 (T18, khung trung gian, `vloop` 16 ms) | 400/600/800/1200: **0/738 khung khác**, `n_mid` = 47 = base, `n_tick_lag` = 47 = base |
| Bộ nhớ ngoài FB (so/heap/misc/stack, mỗi 40 vòng + sau boot + cuối) | heap 0 byte khác, số khối heap và `top` bằng nhau; misc 0; `.so`: 4 byte ở 0x100000c4 (phdr đoạn menu, kích thước đoạn khác nhau giữa 2 bản — không phải trạng thái game), loại các từ hook theo `menu_patches.tsv`; **stack: 10..26 byte, `stk.py`: 100% nằm DƯỚI sp (vùng chết, dư lượng khung của hook)** |
| Fuzz trực tiếp (`fuzz.py`): ngẫu nhiên tham số/clip/stride/độ sâu 8,16,24,32/alpha/key/callback, 9 kiểu bố trí (tách rời, sát nhau, chồng nhẹ, chồng nhiều, cùng FB, khối liền, FbInfo/ctx nằm TRONG vùng đích); so toàn bộ vùng đệm + struct sau khi chạy hàm gốc và `fbcopy` trên cùng bộ nhớ đầu vào | seed 21: 40.000 ca; seed 22: 60.000 ca; (seed 7: 30.000 và 11: 40.000 ở bản trước khi thêm bảo vệ); **0 lệch**. ~25% ca đi đường nhanh (kể cả `sx,sy<0`, `dx,dy<0`, clip hẹp, `stride>nb`, `stride<nb`), còn lại về hàm gốc; ca alias làm hàm gốc lỗi bộ nhớ (~300) thì bản hook cũng lỗi y hệt |
| Không cấp phát (`a1.py`, 40 tick đi phải 400x240 và 800x360) | malloc/calloc/free = 0/0/0 cả hai bản; heap (khối, top) không đổi; hàm host gọi giống hệt, chỉ khác số `memcpy`: +19.200 (400x240) / +21.620 (800x360) mỗi 40 tick, tức +480 / +540 lời gọi mỗi tick (host memcpy là các lời gọi 704/96 B... theo hàng) |
| Mã máy hook | `llvm-objdump` dev.so: 0x1450a4 = `bl 0x191fbc` (fbcopy); base: `bl 0x144cdc` |

Ảnh: `shots/T20/fb_WxH_<tag>.png` (3 ảnh cạnh nhau: base | dev | |khác| khuếch đại, đều đen): 400x240 `hoithoai_boot1100`, `choi_1300`, `cuon_phai_25`, `menu_ingame`; 800x360 cùng bộ. Mọi `điểm khác = 0`.

## 4. Số đo trước/sau (byte-khối Unicorn/tick, `m1.py`, hero đi phải; ~4 byte/lệnh)
| Cỡ | paint base | paint T20 | tổng/tick base | tổng/tick T20 | giảm | host memcpy/tick (lần, MB) base -> T20 |
|---|---|---|---|---|---|---|
| 400x240 | 36,97M | 5,87M | 38,35M | 7,25M | 81% | 2989, 0,01 -> 3469, 0,21 |
| 600x270 | 59,33M | 6,85M | 60,81M | 8,32M | 86% | 2989, 0,01 -> 3529, 0,34 |
| 800x360 | 102,79M | 9,46M | 103,57M | 10,24M | 90% | 2989, 0,01 -> 3063, 0,59 |
| 1200x540 | 225,28M | 15,33M | 225,96M | 16,01M | 93% | 2989, 0,01 -> 4069, 1,31 |
Đứng yên: 36,98 -> 5,88 / 59,26 -> 6,78 / 102,77 -> 9,43 / 225,25 -> 15,31 (paint). Hàm gốc chiếm 81..93% tổng/tick trước T20; phần còn lại sau T20 là `DrawImageBlendingClip565`, `art`, `DrawImageMirrorClip565`, `MC_grpFillRect` (<=30% mỗi cái, vô ích để tối ưu tiếp ở T20).

## 5. Khung trung gian có thể vẽ mỗi tick (T18, interp.txt=1) — mô hình `c5b.py`, `runc5.sh`
**Giả định tốc độ máy (CHƯA đo trên máy thật):** cột 100/200/.../1600 = triệu lệnh ARM32 guest chạy được mỗi giây trong Unicorn (trong mã `ipms_k` = nghìn lệnh/ms). Chi phí guest mỗi mainPaint = byte-khối/4 (bảng 4), mỗi mainProcess và Map::MapMove như `m1.py`/T18, vsync 16,667 ms, nativeLoop chạy ở vsync đầu tiên sau khi xong vòng trước; host memcpy = 0,3 µs/lần gọi + MB/4000MB/s ("dev"); dòng "dev bi quan" dùng 2 µs/lần. Lưu ý mô hình đặt hero chạy liên tục.

Bảng: nhịp tick (Hz) / số khung hình hiển thị (fps) / khung trung gian trung bình mỗi tick:
| Cỡ, bản | 100 | 200 | 400 | 800 | 1600 (triệu lệnh/s) |
|---|---|---|---|---|---|
| 400x240 base | 9,8 / 9,8 / 0 | 18,2 / 18,2 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 37,5 / 0,88 |
| 400x240 T20 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 35,0 / 0,75 | 20 / 37,5 / 0,88 | 20 / 38,8 / 0,94 |
| 400x240 T20 bi quan | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 37,5 / 0,88 | 20 / 38,8 / 0,94 |
| 600x270 base | 6,0 / 6,0 / 0 | 12,0 / 12,0 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 30,7 / 0,53 |
| 600x270 T20 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 38,3 / 0,92 | 20 / 33,3 / 0,67 | 20 / 38,3 / 0,92 |
| 800x360 base | 3,7 / 3,7 / 0 | 7,5 / 7,5 / 0 | 14,0 / 14,0 / 0 | 20 / 20 / 0 | 20 / 20 / 0 |
| 800x360 T20 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 38,3 / 0,92 | 20 / 33,3 / 0,67 | 20 / 38,3 / 0,92 |
| 800x360 T20 bi quan | 19,9 / 19,9 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 33,0 / 0,65 | 20 / 38,3 / 0,92 |
| 1200x540 base | 1,7 / 1,7 / 0 | 3,3 / 3,3 / 0 | 6,6 / 6,6 / 0 | 12,0 / 12,0 / 0 | 20 / 20 / 0 |
| 1200x540 T20 | 19,3 / 19,6 / 0,02 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 33,3 / 0,67 | 20 / 38,3 / 0,92 |
| 1200x540 T20 bi quan | 14,9 / 14,9 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 20 / 0 | 20 / 38,3 / 0,92 |
Đọc: (a) T20 đưa nhịp 20 Hz về mức máy chậm hơn 4..16 lần; (b) tối đa **~0,92..0,94 khung trung gian/tick (~38 fps)** vì thiết kế T18 chỉ cho tối đa 1 khung trung gian (hạn tick D = bắt đầu + chu kỳ - slack 10..20 ms ~ 32 ms nên vsync thứ 3 không còn chỗ; muốn 60 fps phải đổi interp.c — ngoài phạm vi T20); (c) 800x360 cần ~400 triệu lệnh/s để có khung trung gian (trước T20: không đạt ở 1600); 1200x540 ~800 triệu lệnh/s; (d) chi phí gọi host memcpy ảnh hưởng ở ngưỡng (xem "bi quan"), chưa đo trên bridge thật.
Kèm sửa lỗi: dòng "c5b cũ" của công nhân trước có `mid_per_tick = 0` ở mọi dòng vì vòng vsync không tiến thời gian (xem Kết luận 5).

## 6. Lệnh tái hiện
```
cd /home/user/Vi-t-ho-game/hl5_android; export WORK=$HOME/hl5work_t20
bash harness/t20/build_dev.sh dev                    # cây hiện tại, có T20
bash harness/t20/build_dev.sh base --nofbcopy        # đối chứng: bỏ fbcopy.c + mọi dòng "# T20"
python3 harness/t20/v1.py 800 360 170 1300 0         # lockstep base vs dev: W H vòng_chơi boot interp(0/1)
python3 harness/t20/fuzz.py 40000 21                 # fuzz hàm
python3 harness/t20/m1.py 800 360 dev 10 1           # byte-khối/tick   (base: thay dev)
python3 harness/t20/a1.py 800 360 40                 # không cấp phát
python3 harness/t20/stk.py 400 240 12                # byte stack khác nằm dưới sp
python3 harness/t20/cov.py 400 240                   # độ phủ cảnh/tham số
harness/t20/runc5.sh 800                             # mô hình thời gian interp (4 cỡ: 400|600|800|1200) -> $WORK/out2/c5_*.log
python3 harness/t20/s1.py 800 360                    # ảnh shots/T20
```
Log chạy: `~/hl5work_t20/out2/` (v1_*, v1i_*, fuzz_21/22, m1_dev, a1_*, stk_400, cov_400, c5_*).

## 7. Rủi ro / giả định
1. **Bridge memcpy thật (chưa xem được: bridge bị strip)**: harness dùng `uc.mem_read/mem_write` (an toàn chồng lấp); bridge thật có thể khác (nhưng game đã gọi cùng PLT memcpy cho khung 400x240x2 byte mỗi paint — T-nền — và đường nhanh chỉ gọi với vùng KHÔNG chồng lấp). Chi phí mỗi lần gọi (hook Unicorn + sao chép host) chưa đo; hiện +74..1080 lời gọi/tick (bảng 4). Nếu đắt hơn 2 µs, ở 1200x540 cần giảm số lần gọi (gộp hàng).
2. Mô hình thời gian dựa byte-khối/4 = lệnh và tốc độ máy GIẢ ĐỊNH; con số thật phụ thuộc Unicorn trên máy; cần đo `tp` trên máy thật (interp_st.tp đã có).
3. Hàm gốc đọc lại trường FbInfo/ctx mỗi điểm; đường nhanh giả định chúng không bị chính phép chép sửa (đã chặn bằng kiểm bao địa chỉ + fuzz alias). Callback/alpha/key/độ sâu lạ/stride<=0: không bao giờ đi đường nhanh.
4. `sy < 0 -> w += sy` là quirk của game, đã bắt chước. Phép nhân địa chỉ 32 bit quay vòng được bắt chước bằng u32 (chỉ khi stride>0).
5. Chỉ tăng tốc `internalGrpCopyFrameBuffer`; `DrawImageBlendingClip565`, `art`, `FillRect` còn lại (bảng 4: ~2,6M lệnh/tick ở 800x360) là mục tiêu của tối ưu kế (nếu cần).
6. Mô hình `c5b.py` không mô phỏng GL/flush phía host và giả định hero luôn di chuyển.

## CÒN LẠI
- Đo trên máy thật: `interp_st.tp` + thời gian host memcpy qua bridge (tính bằng ms/tick) để chốt ngưỡng; nếu memcpy host đắt, gộp hàng/giảm lời gọi.
- Nếu muốn 60 fps (2 khung trung gian/tick): sửa interp.c (D/slack), kiểm lại tương đương — không thuộc T20.
- Chưa kiểm: các cảnh không ở ROUTE (chiến đấu bản đồ khác, ngoài trời lớn hơn FB, ending). Hàm gốc ở mọi cảnh vẫn được bảo vệ vì mọi lời gọi qua cùng một `bl` với (alpha 255, key 0, callback 0) và đường nhanh tự rơi về hàm gốc khi lệch; fuzz chứng minh tương đương cho các tham số ngẫu nhiên hợp lệ.
- Chưa dựng APK / `--update-repo` (theo luật).
