# T18 — Nội suy khung hình (thiết kế của quản đốc, 05/11/2026)

## 1. Các kiến trúc nội suy trên các nền tảng, và có dùng được cho HL5 không

| Kiến trúc | Dùng ở đâu | Cách làm | Với HL5 |
|---|---|---|---|
| **A. Nội suy khi vẽ ("Fix Your Timestep")** | Engine PC/console: Unity Rigidbody interpolation, Defold, Source; các bản mod "60 fps" | Logic chạy nhịp cố định. Mỗi tick lưu trạng thái `prev` và `curr`. Mỗi lần vẽ dùng `lerp(prev, curr, α)`, với α = thời gian đã qua / độ dài tick. Logic và va chạm luôn dùng giá trị thật; giá trị nội suy chỉ dùng để vẽ. | **CHỌN.** Tốc độ game không đổi. Dữ liệu chuyển động lấy thẳng từ toạ độ đối tượng nên không có lỗi hình. Trễ thêm tối đa 1 tick (~45 ms). |
| B. Ngoại suy theo vector chuyển động (Oculus ASW/AppSW, frame generation) | VR, GPU hiện đại | Đoán khung kế tiếp từ vector chuyển động (ước lượng optical flow, hoặc do game cung cấp như AppSW) | Không dùng: cần shader GPU trong bridge (bridge đã strip). Đoán sai khi đổi hướng; chi phí CPU trong Unicorn quá lớn. |
| C. Trộn / sinh khung hậu kỳ (TV motion smoothing, Lossless Scaling, đề xuất trên Dolphin) | TV, công cụ PC | Tạo khung giữa từ hai khung thật | Không dùng: phải chờ khung sau nên trễ ≥ 1 khung, và hình bị nhoè/bóng ma. Chỉ là phương án cuối cùng. |
| D. Tăng nhịp logic (mod 60 fps kiểu BotW/TotK) | Mod console | Đổi nhịp logic, sửa các hằng số theo tick | Không dùng: bảng fps 0x153460 tối đa 22. Tốc độ di chuyển, hoạt hình, AI đều tính theo tick, nên tăng nhịp sẽ làm game chạy nhanh hơn. |

Nguồn: thảo luận "Fix your timestep" trên gamedev.net, ví dụ interpolation của Defold, phân tích ASW/AppSW trên Meta developer blog và UploadVR, thảo luận về nội suy trên diễn đàn Dolphin, bài của PC Gamer về mod 60 fps TotK.

## 2. Vòng lặp hiện tại (mã máy đã đọc)
`TimerCb` 0x99d98: `mainKeyPressed` 0x98af0 → `mainStateMgr` 0x98430 → `mainProcess` 0x98a78 → `mainPaint` 0x987c0 → `mainEndProcess` 0x98760 → `CUSTOM_grpFlushLcd` 0x3d9d4 → SoundMgr → `CUSTOM_extSetTimer` 0x36100 (trễ = 1000/fps − thời gian khung, tối thiểu 5 ms; fps tối đa 22, tức ~45 ms mỗi tick).
Bridge vẽ GL liên tục theo vsync và gọi `nativeLoop` 0x149640 (timerLoop + updateScreen) ở mỗi khung GL.

## 3. Thiết kế (kiến trúc A, có thích nghi theo tải CPU)

**Nhịp.** Giữ nguyên hạn tick logic của game gốc (`next_logic = t_tick + 1000/fps`). Thay độ trễ đặt cho timer bằng `min(trễ gốc, ~16 ms)`. Ở mỗi lần TimerCb được gọi:
- nếu `now ≥ next_logic`: chạy **tick thật** (đường gốc nguyên vẹn), rồi chụp `curr` (trước đó chép `curr` sang `prev`);
- nếu chưa tới hạn: chạy **khung trung gian**. α = (now − t_tick_trước) / (t_tick − t_tick_trước), kẹp trong [0, 1]. Ghi toạ độ nội suy vào các trường vị trí, gọi `mainPaint` + `mainEndProcess` (nếu cần cho HUD) + `CUSTOM_grpFlushLcd`, rồi **khôi phục** toạ độ thật và mọi trường mà paint đã ghi. Không gọi phím, logic hay âm thanh.

**Thích nghi CPU.** Đo thời gian mỗi lần paint bằng `MC_knlCurrentTime`. Chỉ vẽ khung trung gian khi `now + 1.3 × t_paint < next_logic`. Máy chậm sẽ tự quay về 22 fps như gốc. Quan trọng vì guest chạy trong Unicorn: ở 1/3, mỗi lần paint là chi phí chính.

**Phạm vi.** Chỉ nội suy khi đang ở StateGame chơi bình thường (không popup, không hoạt cảnh, không menu); các trạng thái khác vẽ 1 lần mỗi tick như gốc. Bắt "dịch chuyển tức thời": nếu |Δ| > ngưỡng, hoặc đổi map/trạng thái, thì vẽ `curr`, không nội suy.

**Thứ tự triển khai:**
1. **Mốc 1 (rẻ nhất): chỉ nội suy CAMERA.** Chỉ đổi biến cuộn camera/bản đồ khi vẽ khung trung gian, đối tượng giữ toạ độ của tick. Phần giật dễ thấy nhất của game cuộn 2D là nền trượt theo camera. Hero luôn đi theo camera, nên hero và nền sẽ cùng mượt.
2. **Mốc 2: nội suy cả OBJECT** (quái, NPC, đạn, hiệu ứng) bằng `lerp` toạ độ x,y. Đối tượng mới sinh trong tick thì không nội suy; đối tượng bị xoá thì bỏ qua.

**Cờ.** Mục "Mượt 60 fps" trong Cài đặt, hoặc tệp `interp.txt` theo cùng cơ chế với `hd_scale.txt`; **mặc định tắt**. Khi tắt: không có khung trung gian, độ trễ timer như gốc, nên chạy giống gốc từng byte.

## 4. Việc của công nhân (theo thứ tự; mỗi bước phải có bằng chứng từ mã máy hoặc harness)
- **T18a-1. Kiểm tác dụng phụ của mainPaint bằng diff bộ nhớ.** Trong harness: chụp .data/.bss/heap, gọi `mainPaint` thêm một lần với cùng trạng thái, chụp lại rồi so (bỏ vùng FB). Liệt kê địa chỉ bị ghi kèm symbol (bộ đếm hoạt hình, rand, cờ âm thanh, sinh hạt...). Đây là danh sách trường phải lưu/khôi phục quanh khung trung gian. Nếu paint tạo hoặc xoá đối tượng, hoặc gọi âm thanh, thì ghi Vướng mắc.
- **T18a-2. Tìm biến camera** (gợi ý: `Map::MapSet` 0xafac0, `[Map+108/110]`, `Map::Paint` 0xb129c) **và danh sách OBJECT** (`OBJECT::GetX/GetY` 0xcbc58/0xcbc68: nơi lưu x,y, mảng hoặc danh sách đối tượng, hiệu ứng). Chỉ ghi điều đã thấy trong mã máy.
- **T18a-3. Bridge:** `nativeOnDrawFrame` 0xcb7e4 có tải FB lên texture ở mọi khung GL hay chỉ khi guest flush? `nativeLoop` có được gọi ở mỗi vsync không? Timer WIPI có độ phân giải ≤ 16 ms không (min 5 ms)?
- **T18a-4. Đo chi phí** (đếm lệnh Unicorn): mainProcess, mainPaint, flush ở 400x240, 600x270, 800x360. Tính số khung trung gian có thể vẽ trong một tick 45 ms.
- **T18b.** Cài mốc 1 (camera) bằng code mới `engine/menu/interp.c` + hook TimerCb, có cờ và cơ chế thích nghi. Đề xuất các dòng hooks/patch; quản đốc sẽ ghép.
- **T18c. Kiểm:**
  - (1) cờ tắt: ảnh từng khung và bộ nhớ giống gốc;
  - (2) cờ bật, cùng phím: sau N tick, trạng thái logic (vị trí hero, HP, map, bộ nhớ trừ FB và trường khôi phục) giống hệt cờ tắt;
  - (3) ảnh khung trung gian: nền lệch đúng α·Δcamera;
  - (4) không cấp phát trong khung trung gian;
  - (5) số khung trung gian thực tế mỗi tick ở 800x360.
  Sau đó mới tới mốc 2.
- Báo cáo: `hl5_android/BAO-CAO-T18.md` (CÒN LẠI nếu dừng).
