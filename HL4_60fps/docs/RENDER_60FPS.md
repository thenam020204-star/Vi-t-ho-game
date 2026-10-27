# Nội suy 60 khung/giây — Heroes Lore 4: Phantasmal Mask

## Nguyên lý (giữ từ phiên 1)
- Logic giữ nhịp gốc: `GameCanvas.run` một tick = `Engine.frameDelay + 15` ms (mặc định `Opt.FPS[6]=30` → 45 ms ≈ 22,2 tick/s).
- Mỗi tick: `Engine.tick` → `Interp.beforeTick` (lưu vị trí) → `process()` → **`Interp.renderBetween`** → `render()` thật.
- `renderBetween` vẽ `slots−1` **khung phụ** (`slots = round(period·60/1000)`, tối đa 4; 45 ms → 3 slot) với `alpha = 256·i/slots`, hiển thị qua `Interp.Sink` (`GameCanvas.show`) đúng mốc thời gian slot; khung phụ trễ quá mốc (`late(i)`) thì bỏ → máy yếu tự về tốc độ gốc, logic không chậm đi.
- Trước khung phụ lưu **toàn bộ** trạng thái mà `render()` có thể sửa (`saveAll`), sau mỗi khung phụ khôi phục (`restoreAll`): 6 mảng `G.*`, `m[]` của mọi object pool, `Gfx.back/scr`, trạng thái sprite, Rng, bút vẽ, … → khung phụ không bao giờ đổi gameplay.

## Phiên 2 — "mọi chỗ 60 fps"
| Thành phần | Cơ chế | Mã |
|---|---|---|
| Cổng khung phụ | trước: chỉ khi có vật di chuyển; nay: di chuyển **hoặc** biến hiệu ứng đổi **hoặc** tick trước có sprite đổi frame | `Interp.renderBetween` |
| Màn được bật | `MainGameFrame` (cũ) + `TitleFrame` + `MainMenuFrame` mode 0/1 (menu chính, tạo nhân vật) | `Interp.eligible` |
| Nội suy hoạt ảnh sprite | log mọi lần vẽ sprite của render thật (sprite, anim, hàng, frame, x, y, lật). Khung phụ tìm lần vẽ cùng sprite/anim/hàng gần nhất của tick trước; nếu frame tăng 1 (hoặc vòng về 0): **ghép cell theo danh tính** (lớp, ảnh, kiểu, góc, chế độ blend; cặp gần nhất ≤ 40 px) → cell có cặp trượt vị trí + tỉ lệ + alpha; cell không có cặp **hoà mờ** (frame cũ vẽ trước chỉ phần không có cặp, mờ dần theo 1−α; cell mới hiện dần theo α). Đổi frame khác (nhảy anim) → giữ frame cũ đến nửa tick | `Sprite.drawNpc` → `Interp.sprBegin/cellX/cellY/cellA/cellSX/cellSY/cellE/cellF` |
| Biến hiệu ứng (giá trị trực tiếp) | fade màn hình `G.map[842]` (kiểu `[840]`), nháy `G.map[847]` (`[846]`), phủ màu sự kiện `G.event[1536]` (`[1516]`) | bảng `SC_*` |
| Giá trị vẽ (bộ đếm bước 1 × px) | `Interp.lp(id, v)`: render thật ghi giá trị, khung phụ trộn với giá trị tick trước (nếu tick trước có vẽ, bước ≤ 96) | xem bảng dưới |
| Hạt (particles) | `Particles.onIdle` chạy theo thời gian thật trong render; khung phụ mô phỏng thời gian đã trôi kể từ render thật trên mảng thật rồi khôi phục `Particles.particles/curCount/maxCount` | `HeroDraw.particleProcess` → `Interp.ghostParticles` |
| Số sát thương | bộ đếm giai đoạn `m[34]` (phóng to 200→160→120→100, mờ 255→0, trôi xuống): khung phụ trộn tỉ lệ/alpha/độ trôi giữa giai đoạn `c−1` và `c`, chữ số hiện dần đổi ở nửa tick | `Effects.draw` → `Interp.drawEffect` |

Các điểm `lp`:
| id | Nơi | Giá trị |
|---|---|---|
| 0–11 | `Dialog.drawScriptWindow` (slot·3 + k) | y, x khung thoại và y chân dung khi trượt vào/ra |
| 12 | `EventDraw.draw` | chiều cao viền điện ảnh |
| 13 | `MainGameFrame.drawInfo`, `HudKtf.drawInfo` | độ trượt bảng thông tin quái |
| 14 | `EventDraw.subTitle` | alpha nền phụ đề |
| 15 | `EventDraw.skipFade` | alpha fade khi SKIP |

Làm tròn: các phần mới dùng `mix()` làm tròn đối xứng (bước 1 px đổi ở nửa tick, trái/phải như nhau). Nội suy vị trí object/camera của phiên 1 giữ nguyên công thức cũ.

Đòn chém (hero anim 9, 8 frame; vệt chém anim 31): frame keyframe đổi số cell/thứ tự/góc ảnh (`Fx` chỉ xoay bội số 90°) nên không thể "xoay" kiếm giữa 2 tư thế; tư thế cơ thể trượt theo cell ghép được, kiếm & vệt chém chuyển bằng hoà mờ. Ảnh: `screenshots/session2/slash_crossfade.png`.

## Không nội suy (có chủ đích)
- Màn tĩnh: menu trong game (`GameMenuFrame`), cửa hàng/NPC (`NpcUi`), hộp xác nhận (`TState`), màn nạp (`LoadFrame`), logo — đo được hầu như không đổi giữa các tick (menu trong game: 8 lần đổi/262 tick, chỉ khi bấm phím). Ngoài ra `GMenuInv.paint` **chạy logic vật phẩm trong lúc vẽ** (`Items.setItem` → `Quest.check` → `Event`, `Sound`) nên vẽ lại nhiều lần là không an toàn.
- Thay đổi rời rạc: chữ hiện dần theo từ, đổi ảnh/icon, xuất hiện object mới — khung phụ hiện trạng thái của tick mới (như phiên 1).
- Thanh máu: gốc đổi tức thời, không thêm hiệu ứng trượt.

## Chi phí
- Bộ nhớ thêm (cấp phát 1 lần): bảng cell 19×64 int ≈ 5 KB, log sprite 2×320×(3 int + ref) ≈ 10 KB, bảng `lp` 128 B, bản sao mảng hạt (= `Particles.particles.length`). Không có `new` trong đường vẽ khung phụ (quét `javap`).
- CPU: hoà mờ vẽ thêm phần không có cặp của frame cũ (gọi `drawNpc` lồng 1 cấp) và vẽ cell có alpha (đường blend chậm hơn đường thường) — chỉ ở khung phụ.
- CPU: trong màn chơi gần như luôn có sprite hoạt ảnh → khung phụ vẽ mỗi tick (trước chỉ khi di chuyển). Máy không kịp thì `late()` bỏ khung phụ, nhịp logic giữ nguyên.
- Tắt được: thuộc tính JAD `HL4-60fps: 0`.
