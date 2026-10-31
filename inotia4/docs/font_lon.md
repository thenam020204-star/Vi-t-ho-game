# Font cỡ lớn (T7): vì sao mất dấu ở TEST7 và font `f24vn`

Phạm vi: chữ Việt trong game cỡ 24 / 28 / 38 (native xin `size` 24 gần như mọi nơi, hộp rộng 565–659 px).
Người dùng báo (2026-10-31): TEST7 chữ đã to nhưng mất dấu thanh ở nhiều ký tự.

## 1. Bằng chứng native: đường vẽ chữ trong hộp

Mọi đoạn thoại/mô tả dài đi theo một đường duy nhất (địa chỉ trong `libgame.so` của TEST4; kiểm lại bằng `llvm-objdump` trong `/opt/work/lib4/dis.txt`):

| Bước | Hàm / địa chỉ | Việc |
|---|---|---|
| 1 | `SGL_grpCreateStringImageInRect` 0x1340bc | gọi vào font Android |
| 2 | `SGL_Font_Android::RenderToTextureInRect` 0x265eb8 | gọi `SGL_GetTextSizeInRect` (0x265f30, → Java `getStringWidthHeightInRect`), làm tròn W và H lên luỹ thừa 2 (vòng nhân đôi `lsl #1` ở 0x265f58 và 0x265f88), `malloc` + `memset` đệm A8 (0x265fec, 0x266000) |
| 3 | `SGL_RenderFontToAlphaImageInRect` 0x266ac8 | gọi Java `drawTextInRect` (đệm `int[]` dùng chung, 2048×2048 ở `CWrapperActivity:139`), rồi chép **byte 3 = alpha** của từng int (`ldrb w22, [x5,#3]` @0x266bd4), nguồn stride = width, đúng `height` hàng mà `measureInRect` trả |
| 4 | 0x2660b8 / 0x2660bc | `SGL_Texture::Create` với định dạng `0x1906` (GL_ALPHA), kiểu `0x1401` (GL_UNSIGNED_BYTE); `glTexSubImage2D` @0x266104 |
| 5 | `SGL_Texture::Create` 0x2648f8–0x26490c | `glTexParameterx(GL_TEXTURE_2D, 0x2801 MIN_FILTER, 0x2601)` và `(…, 0x2800 MAG_FILTER, 0x2601)`; 0x2601 = **GL_LINEAR** |

Kết luận phần native:

- Không có chỗ nào cắt hàng của bitmap chữ: native chép đúng H hàng mà Java báo, đệm đủ (2048×2048 int). Đường `drawText` (hàng = `(int)(size*1.3)` @0x2666d4) không có caller.
- Texture chữ được lọc **tuyến tính** khi GPU phóng từ màn logic (1422×640) lên màn thật (2400×1080): hệ số 1080/640 = 1,6875.
- Bước dòng của game `GRPX_GetFontHeight` = size+2 chỉ dùng ở các đường vẽ từng dòng; đường InRect dùng `lineH` của `VNFont`, nên không có chuyện dòng sau đè dòng trước.

## 2. Mô phỏng và số đo

Công cụ (đều trong repo):

- `fontgen_inotia/preview_f24.py`: render câu mẫu bằng thuật toán `VNFont` (nearest, `q/16` cho f18; 1:1 cho f24) rồi phóng ×1,6875 bằng nearest và bilinear. Ảnh: `docs/font_preview_f24.png` (khối 1–2: f18×1,5 TEST7; khối 3–4: f24vn).
- `fontgen_inotia/mark_stats.py`: đo độ rõ của từng thành phần dấu sau bilinear ×1,6875.
- `fontgen_inotia/gpu_sim.py`: phóng ×1,6875 (nearest và bilinear) cho **đệm chữ thật** do unidbg lưu (mục 3).

Kết quả `mark_stats.py` (cỡ 24, `font/f18vn.bin` so với `font/f24vn.bin`):

| | f18 ×1,5 (TEST7) | f24vn 1:1 |
|---|---|---|
| thành phần dấu/sừng đã đo | 171 | 187 |
| bề ngang nhỏ nhất, px đệm (nhỏ nhất / trung vị) | **1** / 3 | **2** / 3 |
| thành phần chỉ dày 1 px | **35** | 0 |
| độ đậm đỉnh sau bilinear (0–255), nhỏ nhất | **152** (sừng Ư, Ứ, Ừ, Ử, Ữ, Ự) | 255 |
| thành phần có độ đậm đỉnh < 200 | **58** | 0 |
| diện tích vùng ≥ 50% (px đệm²), nhỏ nhất | 1 | 4 |

Đọc kết quả:

- ở TEST7 hệ số ×1,5 = `q/16` với q = 24: một điểm f18 thành khối rộng 1 hoặc 2 px (xen kẽ), nên dấu nặng, sừng ơ/ư, chấm dấu hỏi chỉ còn 1 px; GPU rồi lọc tuyến tính nên đỉnh chỉ còn 60–80% độ sáng và loang sang hai bên;
- 35 trên 171 thành phần dấu (khoảng 20%) chỉ dày 1 px và 58 (khoảng 34%) có độ đậm đỉnh dưới 200/255 sau lọc. Phù hợp với phản hồi "mất dấu ở nhiều ký tự";
- font mới: mọi thành phần dấu ≥ 2×2 px và có khối 2×2 đặc, nên đỉnh luôn 255 sau bilinear.

## 3. Ảnh từ unidbg (đệm chữ thật của `drawTextInRect`)

Thêm tuỳ chọn `-Demu.dumpfont=DIR` vào `emu/src/Inotia4Emu.java` (làm được ngay lần thử đầu): sau mỗi `drawTextInRect`, ghi đệm `int[]` (rộng `maxW`, cao = `measureInRect`) ra `DIR/dump_NNN_sSS.png` (chữ trắng trên nền xám đậm; mỗi văn bản/size chỉ lưu một lần, tối đa `-Demu.dumpmax`, mặc định 60). Đây đúng là đệm mà native nhận ở bước 3 ở trên.

```bash
/opt/work/runemu.sh /opt/work/t7/emu3 720 "620:tap:240,277" -Demu.dumpfont=/opt/work/t7/dump_f18
python3 -I fontgen_inotia/gpu_sim.py out.png "nhãn=/opt/work/t7/dump_f18/dump_000_s24.png"
```

Màn chọn ô save có `size=24`, `w=659`. Lưu ý: unidbg vẫn văng `UC_ERR_READ_UNMAPPED` ngay sau lệnh này (lỗi môi trường jemalloc đã ghi ở T2, không phải của game), nên **ảnh màn hình** (`fNNNNN.png`) không có chữ; đệm chữ thì đã lưu xong trước khi văng (callback Java chạy trước), nên dump vẫn dùng được.

- `docs/emu_shots/font_dump_f18_s24.png`: đệm chữ thật với `VNFont` TEST7 (f18, q=24).
- `docs/emu_shots/font_dump_f18_gpu.png`: cùng đệm, phóng ×1,6875 nearest (trên) và bilinear (dưới).

Giới hạn: unidbg không chạy GPU của máy thật, ảnh phóng là mô phỏng bằng Pillow (BILINEAR = nội suy theo tâm điểm ảnh, cùng nguyên lý `GL_LINEAR`).

## 4. Kết luận

- Nguyên nhân khả dĩ nhất (có bằng chứng native + số đo mô phỏng): dấu f18 quá nhỏ (nặng, sừng, hỏi chỉ 1 px; sắc/huyền 2 px chéo); phóng `q/16` không làm to nét 1 px, `GL_LINEAR` ×1,6875 làm đỉnh nhạt và loang.
- Loại trừ: bộ đệm cắt hàng (native chép đủ H hàng), dòng sau đè dòng trước ở đường InRect.
- **CHƯA XÁC ĐỊNH**: ảnh chụp từ máy thật của chữ nhỏ vừa vẽ; giá trị `[g_pSGL+0x14]` (hệ số size ở 0x135118). Người dùng xác nhận trên máy là điều kiện nghiệm thu cuối của T7.
- Quyết định (đã có từ quản đốc, không đổi): vẽ lại font ở lưới 24 (dấu ≥ 2×2 px), **không** vá `GL_LINEAR` → `GL_NEAREST` (0x2648fc, 0x26490c; ảnh hưởng mọi texture SGL).

## 5. Font `font/f24vn.bin`

Sinh bằng `python3 fontgen_inotia/gen_f24.py font/f18vn.bin font/f24vn.bin`; kiểm bằng `python3 fontgen_inotia/check_f24.py font/f18vn.bin font/f24vn.bin` (hiện **0 vi phạm**).

- Ô chữ H = 28, BASE = 23: chữ hoa hàng 9–23, x-height hàng 15–23, vùng dấu hàng 0–8, đuôi chữ và dấu nặng hàng 24–27; bề rộng ô 11 (dấu cách 8). H ≤ 28 ≤ `1.3*24` nên vẫn vừa đệm của đường `drawText`.
- Cùng charset và thứ tự glyph với `f18vn.bin` (233 ký tự; 6 glyph vẽ tay `_ = ~ ’ ‘ ★` giữ nguyên).
- Thân chữ: "bút 2×2" từ f18 (mỗi điểm sáng thành khối 2×2, toạ độ nhân khoảng 1,4), nét dày đều 2 px; 13 chữ vẽ tay lại (A K M N R V X Y k v w x y).
- Dấu vẽ lại trên lưới 24 và ghép: nặng 2×2; sắc/huyền 3×4; hỏi 4×4; ngã 9×3 (7×3 trên chữ có sừng, để cách sừng ≥ 2 px); mũ/á 7×3 có khối 2×2 đặc; sừng 2×2 ở hàng x-height. Dấu cách thân chữ ≥ 1 hàng; hai dấu chồng cách nhau ≥ 1 hàng.
- Kiểm tự động (`check_f24.py`): mọi thành phần dấu bbox ≥ 2×2, không có nét 1 px theo cả hai phương, cách thân (Chebyshev) ≥ 2, dấu chồng cách ≥ 1 hàng, sừng và thanh ngang `đ/Đ` dày ≥ 2.
