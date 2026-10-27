# REFACTOR_LOG — Heroes Lore 4: Phantasmal Mask (mod 60 fps)

## Phiên 2c — 2026-10-27 (UTC+7): đòn chém "vẫn khựng" → chi phí vẽ + nhịp hiển thị

**Bản build:** `dist/HL4.jar` **v1.0.4**; Verify 162 class (thêm `hl4/gfx/FastFx`), 0 lỗi trước/sau preverify.
**Đo (JVM thông dịch `-Xint`, cùng save combat):** khung phụ lúc đứng ≈ 3,7 ms, lúc chém (tick 411–415) ≈ 12 ms, cả tick 33–36 ms (thường 13 ms). Slot hiển thị chỉ có 15 ms → trên máy thật khung phụ lúc chém trễ hạn, `late()` bỏ → ảnh đứng 2/3 tick rồi nhảy (giật hơn cả 22 fps đều). Không có hit-stop, camera/hero không nhảy quá ngưỡng (đẩy 4 px, đã nội suy).
**Profile (JFR):** `Fx.scaled` 44 % (ảnh phóng to/xoay + blend: vệt chém 200–380 %), trong đó `colorRaw`/`blendPixel` gọi theo từng pixel.
**Sửa:**
| # | Thay đổi | Kiểm chứng |
|---|---|---|
| 1 | `Fx.scaled` → `FastFx.scaled`: cắt vùng hiển thị một lần theo clip, bảng cột tính sẵn, kiểm biên theo dòng, inline màu + blend, cùng thứ tự ghi pixel | test vi sai 100 000 lần (ảnh thật 4/8 bit, mọi lật/xoay/neo/clip/alpha/blend): 0 khác; khung thật trùng 1.01 ở 5 kịch bản (14 762 tick) |
| 2 | Số khung phụ tự điều chỉnh: ≥3/16 tick trễ → bớt 1 slot (66→44→22 fps, luôn cách đều); 64 tick không trễ và chi phí đo được (+50 %) vừa slot → thêm 1 (không đo được thì thử lại sau 256 tick) | `GameCanvas.run`: `slots = Interp.adaptSlots(slots, period)` |
**Kết quả đo `-Xint`:** khung phụ lúc chém 12 → 9 ms, tick 33–36 → 25–32 ms; hoà mờ chiếm ~1,5–3 ms trong đó.
**Emulator (thời gian thật):** lần đầu phát hiện hồi quy — số slot bị hạ về 1 lúc khởi động (JIT nóng máy) và không hồi lại vì nhánh `n < 2` thoát trước bước tăng → 22 fps mọi màn. Đã sửa (đếm cả tick không có khung phụ, thử lại sau 128 tick). Sau sửa: FreeJ2ME tiêu đề 22 → 45 → 58 sau ~5 s khởi động, xác nhận NV 58, sự kiện 57,5–58,5; MicroEmulator tiêu đề 40 → 66,5, xác nhận NV 66, trong game 66–66,5; 0 ngoại lệ từ code game.
**Bất định của harness:** `Vi.marqueeOff` (chữ dài chạy ngang) dùng `System.currentTimeMillis` → so khung giữa 2 lần chạy dùng bản test của cả 2 JAR đổi lời gọi này sang `Clock.now()` (`DetJar`, chỉ để test).

## Phiên 2b — 2026-10-27 (UTC+7): đòn chém còn khựng → ghép cell theo danh tính + hoà mờ

**Bản build:** `dist/HL4.jar` **v1.0.3** (3 068 740 byte, 2 014 entry, md5 `f6cd53f336ee30ece34c4b111b9f499e`) + `.jad`; Verify 161 class, 0 lỗi trước/sau preverify. Mọi entry trừ manifest giống hệt bản đã test.
**Phản hồi người dùng:** "Hoạt ảnh chém vẫn khựng lắm" (ảnh: combat sa mạc, đòn chém thường).
**Nguyên nhân (đo):** đòn chém thường của Warrior = anim 9 (8 frame, 1 frame/tick) + vệt chém anim 31 (4 frame, 2 lớp) trong `_H_001_CIF`. Giữa các frame, số cell, thứ tự cell, ảnh, góc (bội số 90°) và tỉ lệ đều đổi → bản 1.0.2 ghép cell theo **thứ tự** nên gần như không cặp nào khớp (anim 9: 3/21 cặp frame cùng bố cục) → chỉ đổi frame ở nửa tick = nhịp 22 fps.
**Sửa:**
| Điểm | Cách làm |
|---|---|
| Ghép cell | theo (lớp, ảnh, kiểu thường/mở rộng, góc, blend) + gần nhất ≤ 40 px, không phụ thuộc thứ tự |
| Cell có cặp | nội suy vị trí, tỉ lệ (scale x/y), alpha riêng của cell |
| Cell không có cặp | hoà mờ: frame cũ (chỉ cell không có cặp) vẽ trước với độ mờ 1−α qua `drawNpc` lồng 1 cấp; cell mới hiện dần theo α |
| Hook mới trong `Sprite.drawNpc` | `sprBegin` nhận đủ tham số (để vẽ lồng frame cũ); `cellA/cellSX/cellSY` trên local 41/37/38 (cell mở rộng), `cellE/cellF` trên lần đọc đầu `f6e/f6f` (cell thường) |
**Kiểm tra:** tất định 10 703/10 703 tick khung thật giống 1.01 (bật/tắt nội suy); khung phụ có giá trị trung gian trong combat 31,2% → 41,6% (`cb`), tấn công 40,8% (`atk`); số sát thương vẫn khớp pixel 60/60; 0 ngoại lệ ở 4 kịch bản SE; diff nhị phân: vẫn đúng 9 class/method như 1.0.2; quét cấp phát: chỉ `alloc()`/`<clinit>` (+≈5 KB bảng cell). FreeJ2ME: tiêu đề 58,5, xác nhận NV 58,5, sự kiện 57,5–58; MicroEmulator: 66–66,5 ở tiêu đề / xác nhận / trong game; 0 ngoại lệ từ code game (chỉ MIDI của container).
**Giới hạn:** lưỡi kiếm không thể xoay dần giữa 2 tư thế (engine chỉ xoay 90°, frame là keyframe vẽ tay) → chuyển bằng hoà mờ (giống motion blur). Hoà mờ dùng đường vẽ alpha → tốn CPU hơn ở khung phụ.

## Phiên 2 — 2026-10-27 (UTC+7): 60 khung/giây ở mọi màn có chuyển động

**Bản build:** `dist/HL4.jar` **v1.0.2** (2 014 entry) + `dist/HL4.jad`; preverified CLDC 1.1 / MIDP 2.0, class 45.3 — `Verify` 161 class, 0 lỗi trước và sau preverify (`--require-stackmap`), mọi entry Deflate. md5 `72ce40c96cb781a0aed8c2fb8595660f`.
**Nền:** `base/HL4_1.01.jar` (bản người dùng gửi, md5 `597792f3…cb89`, đã có nội suy di chuyển của phiên 1). Không có chỉnh sửa tài nguyên nào: mọi entry không phải class giống hệt, trừ `MIDlet-Version`.
**Cách hiểu yêu cầu:** "mọi chỗ 60 fps" = mọi màn **có chuyển động** hiển thị ≥ 60 khung/s, nhịp logic (22,2 tick/s) và gameplay giữ nguyên tuyệt đối. Màn tĩnh không có gì để nội suy.
**Đường patch:** B (hook bytecode + class mới). Class gốc không biên dịch lại được tương đương (thử CFR → javac: 18/136 class giống bytecode), nên chỉ chèn lời gọi bằng ASM vào 8 method gốc, mã mới nằm trong `Interp` (viết lại toàn bộ từ bytecode phiên 1, giữ nguyên API).

### 1. Hạng mục đã xử lý
| # | Yêu cầu | Kết quả |
|---|---|---|
| 1 | Nội suy hoạt ảnh sprite (không chỉ vị trí) | Cell của frame cũ → frame mới trượt dần khi cùng bố cục; đổi frame khác giữ frame cũ đến nửa tick. Áp cho mọi `Sprite.drawNpc` (hero, quái, NPC, boss, hiệu ứng kỹ năng, tia sáng tiêu đề, nhân vật menu) |
| 2 | Khung phụ cả khi không ai di chuyển | Cổng mới: di chuyển ∨ biến hiệu ứng đổi ∨ sprite đổi frame. Sự kiện: FreeJ2ME 22 → 58 fps, MicroEmulator 22 → 66 fps |
| 3 | Tiêu đề, menu chính, tạo nhân vật | Bật khung phụ cho `TitleFrame`, `MainMenuFrame` mode 0/1. Tiêu đề 21–22 → 58 (FreeJ2ME) / 66 (ME); xác nhận nhân vật 22 → 58 / 66 |
| 4 | Fade / nháy / phủ màu | Bảng `SC_*` (`G.map[842]`, `[847]`, `G.event[1536]`) |
| 5 | Khung thoại trượt, viền điện ảnh, bảng thông tin quái, fade phụ đề & SKIP | `Interp.lp()` trên giá trị đã vẽ (bộ đếm gốc bước 1 nên không thể nội suy trên bộ đếm) |
| 6 | Hạt (kỹ năng) | Khung phụ mô phỏng theo thời gian thật đã trôi, khôi phục sau |
| 7 | Số sát thương | Trộn tỉ lệ/alpha/độ trôi giữa 2 giai đoạn; kiểm `alpha=256` khớp từng pixel bản gốc (60/60 trường hợp) |

**Pipeline kiểm tra (bản cuối):**
| Bước | Kết quả |
|---|---|
| javac JDK 8 `-source 1.3 -target 1.1`, stub microemu cldcapi11/midpapi20 2.0.4 | 0 lỗi |
| Patch ASM có kiểm ngữ cảnh lệnh (sai ngữ cảnh → dừng build) | 8 class |
| Verify JAR chưa preverify | 161 class, 0 lỗi |
| ProGuard 7.6.1 `-microedition` → Verify `--require-stackmap` | 0 lỗi |
| JAD: `MIDlet-Jar-Size` = 3 067 183, `MIDlet-Version` 1.0.2 | ✔ |
| Quét cấp phát `Interp` (javap) | `new` chỉ ở `alloc()` (1 lần), `<clinit>`, `shot` (1 lần), `pSaved` (khi mảng hạt đổi kích thước) |
| Quét alpha | không áp dụng (khung phụ vẽ lại toàn bộ, không phát lại ảnh) |
| Diff nhị phân (`docs/BINDIFF_session2.md`) | 9 class đổi ở mức method, đúng các method có chủ đích; 152 class tương đương bytecode; 0 tài nguyên đổi |
| **Tất định** (harness SE, đồng hồ ảo): khung thật + `G.game/map/event` + `hero.m` mỗi tick, bản mới bật vs tắt nội suy vs bản 1.01 | 10 703/10 703 tick giống hệt cả 3 → khung phụ không đổi gameplay |
| FreeJ2ME-plus (thời gian thật, Xvfb, bản gốc → mới) | tiêu đề 21,5→58; xác nhận NV 22→58; sự kiện 22→58; menu tĩnh 22→22. 0 ngoại lệ từ code game (chỉ MIDI không có thiết bị, có ở cả bản gốc) |
| MicroEmulator 2.0.4 (JDK 8, Xvfb) | tiêu đề 22→66; xác nhận NV 22→66; trong game 22→66,5; 0 ngoại lệ từ code game (chỉ MIDI) |
| Save combat người dùng gửi (harness SE) | nạp được, đánh/kỹ năng/hạt/số sát thương chạy, khung phụ có ở 205/206 tick có thay đổi |
| J2ME Loader / máy thật | ⚠️ chưa chạy (không có trong môi trường) |

### 2. Các file đã chỉnh sửa / tạo mới
| File | Thay đổi |
|---|---|
| `src/hl4/game/Interp.java` | viết lại (phiên 1 → nguồn Java), thêm cổng mới, `SC_*`, `lp`, log/tween sprite, particles, `drawEffect`, lưu `spr2` của boss, `TitleFrame.frameCnt`, `Hf.semicolon` |
| `tools/src/Patch.java` | patch ASM cho 8 class gốc |
| `tools/build.sh` | build tái lập từ `base/` |
| `tools/src/{Verify,Compare,RenderEffects}.java` | quét tương thích, so tương đương bytecode, side effect của `render()` |
| `tools/harness/*` | `SeHarness` (Java SE, tất định), `FjHarness` (FreeJ2ME), `MeAgent` (MicroEmulator) + kịch bản |
| `docs/RENDER_60FPS.md`, `docs/BINDIFF_session2.md` | tài liệu |

### 3. Class đổi tên / thêm / xoá
Không đổi tên, không thêm/xoá class. `Interp`, `Interp$Sink` thay bằng bản biên dịch từ nguồn mới (cùng tên, cùng API công khai).

### 4. Method / field
| Class | Thay đổi | Descriptor |
|---|---|---|
| `hl4/gfx/Sprite.drawNpc` | `local7 = Interp.sprBegin(this, anim, row, frame, x, y, n8, n4, n3)` trước khi chọn frame; `Interp.cellX/cellY(i, v)` sau khi giải mã offset cell | không đổi |
| `hl4/game/HeroDraw.particleProcess` | nhánh khung phụ gọi `Interp.ghostParticles(particleStart)` | không đổi |
| `hl4/game/Effects.draw` | khung phụ → `Interp.drawEffect` | không đổi |
| `hl4/game/Dialog.drawScriptWindow` | `lp` trên local 15/16/17 tại điểm hợp nhánh | không đổi |
| `hl4/game/EventDraw.draw/subTitle/skipFade` | `lp` trên chiều cao viền, alpha | không đổi |
| `hl4/game/MainGameFrame.drawInfo`, `HudKtf.drawInfo` | `lp` trên độ trượt | không đổi |
| `hl4/game/TitleFrame.frameCnt` | `private static` → `static` (package) để lưu/khôi phục | field `I` không đổi; chỉ `Interp` (cùng package) truy cập |
| `hl4/game/Interp` | API cũ giữ nguyên (`on, ghost, alpha, sink, beforeTick, renderBetween, Sink`); thêm `sprBegin, cellX, cellY, lp, ghostParticles, drawEffect, LP_*` | — |

### 5. Biến quan trọng mới
| Biến | Ý nghĩa |
|---|---|
| `Interp.lgSpr/lgKey/lgX/lgY/lgN/lgW` | log sprite của 2 render thật gần nhất |
| `Interp.sprAnim` | render thật vừa rồi có sprite đổi frame → tick sau vẽ khung phụ |
| `Interp.tw*` | dữ liệu cell frame cũ của lần vẽ hiện tại |
| `Interp.SC_*`, `scPrev/scCur` | biến hiệu ứng nội suy trực tiếp + khoá bảo vệ |
| `Interp.lpVal/lpSer/serial` | giá trị vẽ của render thật trước (và số thứ tự render) |
| `Interp.pSaved/pArr/pCount/pMax` | bản sao mảng hạt quanh khung phụ |

### 6. Luồng thực thi cập nhật
`GameCanvas.run` → `Engine.tick` → `Interp.beforeTick` → `process()` → `Interp.renderBetween(cur)`: `scCapture` → [eligible ∧ (sprAnim ∨ SC đổi ∨ moved)] → `saveAll` → với i = 1..slots−1: `ghostFrame` (lerp vị trí/camera/SC, `ghost=true`, `render()`: `drawNpc`→`sprBegin` tween, `lp` trộn, particles mô phỏng, `drawEffect`) → chụp → `restoreAll` → `Sink.show(i)` → `finally`: `scShift`, `serial++`, đổi buffer log → `render()` thật (ghi log sprite, ghi `lp`).

### 7. Tài nguyên đã chuyển đổi
Không có.

### 8. Vấn đề còn tồn đọng
- ⚠️ Chưa chạy trên J2ME Loader / KEmulator / máy Nokia thật. Chi phí CPU tăng: trong màn chơi gần như luôn có sprite hoạt ảnh nên khung phụ vẽ mỗi tick (trước chỉ khi di chuyển). Máy yếu: `late()` tự bỏ khung phụ, logic không chậm; nếu vẫn nặng có thể tắt bằng `HL4-60fps: 0` trong JAD.
- Hiển thị 66,7 khung/s trên ME (3 × 22,2) và ~58 trên FreeJ2ME (giới hạn của emulator).
- Thay đổi rời rạc (chữ hiện theo từ, object mới xuất hiện, ảnh đổi) vẫn hiện ở khung phụ đầu của tick mới — như phiên 1.
- Sprite đổi frame mà bố cục cell khác (nhiều hiệu ứng kỹ năng) không trượt được, chỉ đổi frame ở nửa tick.
- Bước 1 px của hoạt ảnh đứng yên (nhân vật menu nhún 1 px) không thể mượt hơn 1 px.
- `Fx.setPalBlendEx` gốc cấp phát `short[]` mỗi lần đổi palette; khung phụ gọi thêm → áp lực GC tăng (mã gốc, không sửa trong phiên này).
- Menu trong game / cửa hàng: giữ nhịp gốc vì tĩnh và `GMenuInv.paint` chạy logic trong lúc vẽ.

### 9. Kế hoạch tiếp theo
- Chạy bản 1.0.2 trên J2ME Loader và máy thật; nếu chậm, thêm tuỳ chọn trong menu Hệ thống để bật/tắt 60 fps.
- Nếu cần: nội suy thanh HP/SP, chữ hiện dần theo ký tự.
