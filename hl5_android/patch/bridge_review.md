# Rà soát patch bridge arm64 (T13b)

Phương pháp: với mỗi vị trí vá, lần luồng sau điểm vá tới `ret`/tail-call của hàm bao (br.dis), phân tích thanh ghi "đọc trước khi ghi" (liveness, `diag/brreview/df.py`), và giả lập unicorn arm64 (`diag/brreview/review.py`, chạy lại bằng `BR_ORIG=<bridge gốc> BR_FIX=<bridge đã vá> python3 diag/brreview/review.py`; kèm `patch/test_bridge.py`). Hàm `nativeOnSurfaceCreated` (0xc57fc..0xcb7c8, 14 KB, 69 callee gồm nạp guest) không giả lập nổi trọn vẹn: đoạn 0xc6c30..0xc6e68 giả lập với các callee giả (`uc_hook_add`, tra symbol, `new`, `strlen`, `memmove`).

Quy ước bridge (đọc từ mã): hàm cài hook `0xe8834` (`uc_hook_add`) trả **0 = thành công**; khác 0 là lỗi và mọi chỗ gọi đều đi nhánh ném `std::runtime_error` (0xc6d1c "Cannot install display text hook", 0xc8cec "Cannot install verified font metrics hook"). Tra symbol `0xd7620` trả 0 nếu không thấy (cũng ném, 0xc6db0/0xc6e38).

| # | Vị trí | Hàm bao | Kết luận |
|---|---|---|---|
| 1 | 0xc6c30 `b 0xc6d4c` (bỏ vòng 10 hook BFont) | nativeOnSurfaceCreated | ĐẠT. Vòng chỉ ghi x19/x23..x28 và 10 ô handle hook (không ai đọc lại); thanh ghi đọc-trước-khi-ghi tại 0xc6d4c chỉ có x20 (= JNIEnv, gán ở 0xc581c, không đổi trong vòng). Giả lập: bridge gốc/đã vá tới 0xc6e68, không throw; callee-saved khác nhau chỉ x23/x25/x26, không ai đọc (live-in tại 0xc6e68 = x20,x24,x28, đều được gán lại ở 0xc6d54..). |
| 2 | 0xc6dd8 `mov w0,#0` (hook đo GetWidth) | nativeOnSurfaceCreated | ĐẠT SAU SỬA. v0.1 dùng `#1` -> `cbnz w0,0xc8cec` -> throw. Nay w0=0 giống đường thành công gốc (`bl` chỉ ghi x0 và ô handle 0x1fe2f0, không đọc ở đâu khác). Giả lập: bản `#1` (và `uc_hook_add` giả lỗi) ném tại bước 1; bản sửa tới 0xc6e68 không throw. Tra symbol 0xd7620 vẫn phải thấy `_ZN5BFont8GetWidthEPciii`: guest đã vá còn đủ symbol, cùng địa chỉ/kích thước (0x40e30/212). |
| 3 | 0xc6e60 `mov w0,#0` (GetByteLenPrintable) | nativeOnSurfaceCreated | ĐẠT SAU SỬA, như #2 (`_ZN5BFont19GetByteLenPrintableEPciiii` 0x40f24/236 còn nguyên). Sau cbnz là log rồi tiếp khởi tạo; không còn kiểm tra hook nào. Cả hai callback hook đo (0xcfdd0) không còn được cài nên guest chạy thân hàm thật. |
| 4 | 0xc5ce8..0xc5d34 (W,H = màn/k) | nativeOnSurfaceCreated | ĐẠT. Live-in tại điểm nối 0xc5d38 = x8,x9 (W,H) + x20,x23,x24,x26,x28 (không bị chạm); mã mới chỉ dùng w8..w12. Đường scrH<240 hoặc scrW<1 nhảy tới 0xc5d38 với mặc định 400x240 (gán ở 0xc5cd8/0xc5ce0). Chia cho k>=1 (k=min(4,scrH/240), giảm tới 1). Unicorn (test_bridge) 7 cỡ màn đúng. Kết quả ghi vào [ctx+0x58] và 0x1fdac8 như cũ. |
| 5 | 0xe1e38 (viewport) | chính nó, 5 chỗ gọi: c58f8, c5d4c, cb7e0 (tail), cb9b0, e1f84 | ĐẠT. Chỉ dùng x0..x4,x9..x15 (caller-saved), tail-call `br x4` = glViewport như cũ, vẫn lưu vw,vh vào [0x38],[0x3c]. Đường kích thước <1 -> `ret` (0xe1ec4) y như gốc; khác biệt duy nhất: logic W/H=0 (gọi ở c58f8 trước khi tính, gốc gọi glViewport(0,0,0,0)) nay không gọi GL — vô hại vì c5d4c gọi lại sau khi có W,H. Giả lập 7 cỡ khớp bội số nguyên, căn giữa. |
| 6 | 0xe1f84 (tail-call vào 0xe1e38) | hàm clear 0xe1f1c | ĐẠT. Phần mới khôi phục `ldr x19,[sp,#0x10]; ldp x29,x30,[sp],#0x20` đúng khung 0x20 của hàm (giống epilogue gốc ở 0xe2000) rồi `b 0xe1e38`. Giả lập cả hàm: SP, x19, x29 và LR khôi phục, quay về đúng chỗ gọi, glViewport(2,0,1596,720) với 1600x720 (gốc: kéo giãn 1600x722, y=-1). |
| 7 | 0xcc6b8..0xcc700 (chạm) | nativeOnTouchEvent 0xcc614 | ĐẠT. Ra x12=vpW, x10=vpH, x14=offX, x11=offY, đúng các thanh ghi mà 0xcc704..0xcc76x đọc (live-in tại 0xcc704: x8,x9 toạ độ chạm, x11,x14, x19,x21,x22 không bị chạm); chỉ ghi w10..w15 (gốc cũng ghi x13,x16,x17). Khớp công thức viewport cả 7 cỡ (test_bridge). |
| 8 | 0xe3770 `b 0xcfdd0` + cave 0xcfdd0 | wrapper glTexParameterf (0xe373c..) | ĐẠT. Cave thực hiện lệnh gốc (`ldr s0,[x9,#8]`), nếu pname-0x2800 < 2 thì s0 = 9728.0 (GL_NEAREST), rồi `b 0xe3774` (`blr x8`); chỉ dùng w11 (caller-saved, không đọc sau). Không đụng ngăn xếp. 0xcfdd0 là callback hook đo, nay chết: chỉ `adr` ở 0xc6dc8/0xc6e50 (đoạn tới giờ không còn thực thi) tham chiếu; không có con trỏ/relocation tới nó. Giả lập: pname 0x2800/0x2801 -> 9728, 0x2802/0x2803 giữ nguyên. |

Rủi ro còn lại (không kiểm được nếu không có thiết bị): phần còn lại của `nativeOnSurfaceCreated` sau 0xc6e68 (nạp ELF guest, bộ nhớ) không đổi so với bản gốc; không giả lập được. Guest .so vá + `p_memsz` (0x12d20 -> 0x12e20, cùng trang) nằm ngoài rà soát này.

## 9. T10: 0xc5ce8 `b 0xcf64c` + cave_k 0xcf64c..0xcf758 (đọc `hd_scale.txt`) — rà soát A1 (disasm đầy đủ trong `patch/bridge_patch_diff.txt`)

Hàm bao: nativeOnSurfaceCreated. Cave nằm trong callback hook chữ 0xcf64c; callback này chỉ được đăng ký ở vòng cài hook (đã bỏ qua bởi vá #1) nên không còn ai gọi nó: ghi đè là an toàn.

**Luồng điều khiển từ điểm vá tới `ret` của hàm bao.**
1. 0xc5ce8 `b 0xcf64c`; 0xc5cec..0xc5d34 nop. Cave kết thúc `b 0xc5d38` (điểm nối cũ; không có `ret`/throw nào ở giữa).
2. Cave: khung 0x140 (16-aligned), lưu x29,x30,x19,x21 và khôi phục đủ ở 0xcf74c..0xcf750 trước `b 0xc5d38`; x19,x21 callee-saved nên giá trị bridge giữ nguyên. Vùng đệm: đường dẫn sp+0x20..0x120 (snprintf giới hạn 0x100), đệm đọc sp+0x128..0x130 — nằm trong 0x140.
3. `w21=2` (=DEFAULT_K). `cbz x24` (jstring filesDir rỗng/null) -> bỏ qua toàn bộ đọc tệp, dùng k mặc định.
4. GetStringUTFChars (vtable +0x548, x20 = JNIEnv) -> `cbz x19` (null, vd OOM) -> bỏ qua. Không gọi JNI nào khác khi có ngoại lệ treo, ngoài ReleaseStringUTFChars (+0x550) hợp lệ gọi với chuỗi vừa lấy.
5. snprintf(buf, 0x100, "%s/hd_scale.txt", path): cắt cụt nếu quá dài, luôn có NUL. `open(buf, O_RDONLY)`; `tbnz w19,#31` (thư mục thiếu / không đọc được / không có tệp) -> bỏ qua, k mặc định.
6. `read(fd, [sp+0x128], 8)`: ô đọc đã được xoá 0 trước (`str xzr`), kết quả trả về bị bỏ qua: tệp rỗng hoặc lỗi đọc (thư mục: EISDIR) để nguyên 0 -> byte đầu 0-0x31 > 3 (không dấu) -> `b.hi` bỏ qua, k mặc định. Tệp khổng lồ: chỉ đọc đúng 8 byte, không cấp phát. `close(fd)` luôn gọi sau khi open thành công.
7. Chấp nhận khi byte0 ∈ '1'..'4' và byte1 < 0x21 (hết chuỗi hoặc xuống dòng/khoảng trắng); ngoài ra bỏ qua (k mặc định). Giá trị ghi ra: w21 = k (1..4).
8. Tính: scrH (w22) < 240 -> W=400,H=240 (như cũ). Ngược lại k = min(scrH/240, w21) >= 1 (nên không chia 0); lặp giảm k đến khi W>=400 && H>=240 hoặc k=1 (kết thúc chắc chắn vì k giảm dần tới 1); W,H làm chẵn xuống, đưa vào w9 (W), w8 (H). Dùng w8..w11 cùng cờ NZCV.
9. Sau 0xc5d38 giá trị dùng lại: x8,x9 (H,W) và x20,x23,x24,x26,x28 (không bị cave chạm hoặc đã khôi phục); x0..x7,x10..x18 không live-in (df.py). k, W, H không được dùng nơi nào khác; không có ngoại lệ nào ném trong cave (không có `bl` tới hàm có thể ném ngoài JNI Get/Release, snprintf/open/read/close của libc).

**Kiểm chứng động.** `patch/test_bridge_k.py` (unicorn chạy mã bridge thật): 160 lượt = 10 kích cỡ màn x 15 nội dung tệp (15 nội dung tệp, gồm cả hợp lệ và không hợp lệ) + không có filesDir, 0 lệch so với mô hình (`patch/bridge_test_k.txt`). Thư mục thiếu/không đọc được/tệp rỗng/tệp lớn: đều rơi về k mặc định, không crash trong giả lập.

**Kết luận: ĐẠT.** Rủi ro còn lại (không kiểm được nếu không có thiết bị): hành vi thật của JNI GetStringUTFChars/quyền đọc filesDir trên máy thật (nếu lỗi: k mặc định 2).
