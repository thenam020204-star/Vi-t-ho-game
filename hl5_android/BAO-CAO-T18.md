# BAO-CAO-T18: nội suy khung hình lên 60 fps — T18a (nghiên cứu mã máy + đo harness)

Công nhân: Sonnet (A1), WORK `~/hl5work_t14c`. Chỉ đọc + dò, KHÔNG sửa guest/bridge/engine. Mã dò: `harness/t18/` (t18lib.py, p1, p3..p10). Số đo là byte khối cơ bản guest (≈ 4 B/lệnh ARM), harness Unicorn, bản gốc v0.2-hd, cảnh chơi sau khung 1300.

## KẾT LUẬN NGẮN
1. `mainPaint` gần như KHÔNG có tác dụng phụ: gọi thêm sau paint của tick (trước mainEndProcess) ra ảnh FB GIỐNG TỪNG ĐIỂM trong 120/120 tick (đi phải, đánh, đi trái), heap (số khối, top) không đổi; chỉ ghi bộ nhớ ở 3/120 tick, 1 byte (xem a-1). => **khả thi về ngữ nghĩa**.
2. **CHẶN CHÍNH: chi phí.** Paint chiếm **98-99%** CPU guest của một tick (mainProcess chỉ ~1,7%). Một khung trung gian (mainPaint đầy đủ) tốn xấp xỉ MỘT tick. Muốn 60 fps từ 22 fps cần ~2,7 lần số lần paint => cần CPU dư ≥ 2,7x (mục a-4). Bản thân người dùng đã báo lag ở 1/1..1/4; trên máy chậm, cơ chế thích nghi của thiết kế sẽ tự tắt (không có gì để vẽ thêm) => 60 fps chỉ có lợi nếu máy dư CPU. Cần số liệu thật từ máy người dùng (fps/ms mỗi tick) trước khi đầu tư T18b.
3. **Mốc 1 "chỉ camera" như thiết kế sẽ SAI nhìn thấy được**: hero (và mọi object) vẽ theo (toạ độ thế giới − camera); nếu chỉ lerp camera mà object ở toạ độ tick, hero trượt tương đối so với màn (răng cưa ~7 px/tick khi đi), trong khi nền mượt. Đo: đổi camera k px => cả ảnh (gồm hero) dịch k px (94% điểm khớp, 6% còn lại = hero/quái). Phải lerp cả hero (OBJECT+4/+8) ít nhất (mốc 1 = camera + hero, quái/hiệu ứng cùng mốc 2).

## a-1. Tác dụng phụ của mainPaint (bằng chứng harness `p3.py`, `p4.py`, `p5.py`, `p6.py`)
- Thứ tự một tick (vết `p1.py`, mọi khung giống nhau): TimerCb 0x99d98 -> mainKeyPressed (trả khác 0 khi chơi) -> mainStateMgr -> mainProcess -> [CurTime x4 trong process] -> **mainPaint -> mainEndProcess -> CUSTOM_grpFlushLcd** (đường 0x99f9c..0x99fac; nhánh trả 0 của mainKeyPressed bỏ cả paint/endproc). Process gọi vtable +0x3c, Paint +0x40, EndProc +0x44 của state trên cùng (gv+0x244).
- Paint thêm gọi NGAY SAU paint của tick (trước EndProc), 120 tick có đi/đánh: FB trùng 120/120; heap đếm khối/top không đổi; vùng ghi: chỉ stack + bộ đệm tạm do host memcpy/memset (calloc/free cân bằng 29/29).
- Paint thêm gọi SAU EndProc (ngoài tick) thì FB khác 118/120 tick: do mainEndProcess đổi bộ đếm khung hoạt hình (gv+0x130 tăng, SPRITE::GetLastFrame) => khung trung gian phải vẽ ở trạng thái SAU paint của tick và TRƯỚC EndProc, hoặc EndProc lưu/khôi phục gv+0x130.
- Tác dụng phụ thật tìm thấy: `Monster::Draw` gọi `Monster::IncreaseTempFrame` 0xb66c4 (tại 0xbee5c) => ghi 1 byte trường đếm khung của quái ở 3/120 tick (0x204b7210, giá trị 45..50). Quét tĩnh các hàm Draw/Paint gọi hàm kiểu Increase/Rand/Play: còn `HERO::IncreaseSpiritSkillMenuFrame` (Battle::Paint, menu tinh linh), `CHAR::IncreaseSpriteFrame` + `SoundMgr::PlaySound` (Battle::DrawSpiritCutIn), `StaticUtil::Rand` (StateGame::DrawExpert). => Phạm vi chỉ khi chơi thường (không cut-in tinh linh, không menu tinh linh/expert); `IncreaseTempFrame` phải bị chặn (HOOK_BL tại 0xbee5c, cờ nội suy) ở khung trung gian. Danh sách chưa phủ hết hiệu ứng/kỹ năng (kịch bản dò chỉ đi + đánh thường trong một map).
- Chưa kiểm: paint trong tick có chạm màn/popup/hoạt cảnh (đã loại khỏi phạm vi theo thiết kế).

## a-2. Camera và OBJECT (bằng chứng `p8.py`, `p9.py`, `p10.py`)
- `Map*` = r0 của Map::Paint 0xb129c (cảnh thử: 0x203a2790, một thể hiện).
- Camera X là nhóm trường int32 **Map+0xc0, +0xc8, +0xd0, +0xd8** (hero đi phải 7 px/tick: cả bốn +7/tick; +0xc4.. là Y, không đổi khi đi ngang). Chỉ đổi +0xc0 thì ảnh KHÔNG dịch (khớp d=0 98%); đổi cả 4 trường +k thì ảnh dịch đúng k px (khớp d=k 94%, k=3 và 6). Còn đổi +0xe0/+0xe8/+0xa8/+0xb0 thêm không đổi kết quả (chưa rõ vai trò, có thể là biên cuộn/tile). Y tương tự dự đoán (+0xc4,+0xcc,+0xd4,+0xdc) — CHƯA thử.
- Object vẽ theo đối số: `Monster::Draw / WARRIOR::Draw(this, gfx, -camX, -camY)` (đo: r2=-43,-50,-57.. đúng -Map+0xc0; r3=-593 = -camY) + toạ độ thế giới của object: `OBJECT::GetX` 0xcbc58 = [obj+4], `GetY` 0xcbc68 = [obj+8] (int32). Hero = đối tượng WARRIOR (cảnh thử 0x202c55e0), quái = Monster (0x205305b0, 0x204b6eb0, 0x205309e0, 0x20530e10, 0x20255800). Danh sách đối tượng duyệt trong Battle::Paint bằng ListNode::GetNextListNodePtr (chưa dò chi tiết, chưa dò hiệu ứng/đạn).
- Nội suy kiểu "đổi trường rồi khôi phục" khả thi cho camera + obj+4/+8: giá trị tick nằm trong bộ nhớ, chỉ cần ghi trước paint và trả lại sau.

## a-3. Bridge / nhịp (mã máy)
- Java GLSurfaceView vẽ liên tục theo vsync; mỗi khung GL: `nativeOnDrawFrame` (bridge) -> guest `nativeLoop` 0x149640 = `MIDASKernelManager::timerLoop()` 0x14d194 + `updateScreen()` 0x14cf40 (BAO-CAO-RE:236). Quad màn hình được vẽ lại MỖI khung GL, nhưng texture chỉ được tải lên (glTexSubImage2D từ fb+0x10 trong `MC_grpFlushLcd` 0x1488e0 -> `Texture2D::bindTextureFromDirect` 0x14e050) khi guest gọi flush. => muốn hiện khung trung gian chỉ cần guest gọi `CUSTOM_grpFlushLcd` 0x3d9d4 với FB mới; bridge không cần sửa.
- Timer WIPI: `CUSTOM_extSetTimer` 0x36100 lưu hạn = now + delay − 20 ms vào bảng 20 mục (stride 24 B); `timerLoop` kiểm hạn mỗi `nativeLoop` => độ phân giải = nhịp vsync (~16,7 ms), không phải 5 ms. TimerCb tự đặt lại với delay = max(5, 1000/fps − t_khung); fps = bảng 0x153460, tối đa 22 (45 ms). Đặt delay ≤ 16 ms thì TimerCb chạy ở vsync kế (60 Hz).
- Chưa đọc từ mã máy bridge (GL gọi qua con trỏ hàm, `nativeOnDrawFrame` 0xcb7e4 lớn): có khoá/cờ chặn vẽ lại nếu không có flush hay không; nghi không, vì khung GL luôn chạy nativeLoop.

## a-4. Chi phí (byte khối cơ bản / tick, `p7.py`, 15 tick, bản gốc; triệu)
| cỡ | pre (KeyPressed+StateMgr) | Process | **Paint** | EndProc | Flush(guest) | tổng | Paint % |
|---|---|---|---|---|---|---|---|
| 400x240 | 0,0006 | 0,65 | 37,45 | 0,03 | 0,003 | 38,1 | 98% |
| 600x270 | 0,0006 | 0,65 | 59,14 | 0,03 | 0,003 | 59,8 | 99% |
| 800x360 | 0,0006 | 0,65 | 100,95 | 0,03 | 0,003 | 101,6 | 99% |
(≈ 9,4 / 14,8 / 25,4 triệu lệnh/tick. Không tính thời gian host của stub flush/GL.) Số khung trung gian vẽ được trong một tick 45 ms = (45 ms − t_process) / t_paint − 1 (đã tính cả paint của tick) => với 60 fps cần t_paint <= 16,7 ms, tức tốc độ Unicorn trên máy >= 0,56 / 0,89 / 1,5 G lệnh/s ở 1/4, 1/3(600x270), 1/3(800x360). Tốc độ thật trên máy người dùng CHƯA biết (harness chạy ~0,1 s/khung 600x270 trên máy dựng, chậm hơn nhiều so với yêu cầu). => khả năng cao cơ chế thích nghi sẽ không vẽ khung trung gian nào ở 1/3 trừ máy rất mạnh.

## Vướng mắc / đề nghị cho quản đốc
- Cần 1 số đo từ máy người dùng: ms mỗi tick (hoặc fps thực) ở 1/3 và 1/4. Nếu tick thực > 45 ms (đang lag) thì T18b vô ích; nếu < 15 ms thì làm được.
- Nếu muốn có lợi trên máy vừa: phương án rẻ hơn khung trung gian đầy đủ = "dịch ảnh thế giới" (chụp FB thế giới ngay trước phần UI của Battle::Paint, khung trung gian = memmove dịch theo Δcamera + vẽ lại dải biên + hero + UI). Chưa khảo sát (phải chia lớp thế giới/UI trong Battle::Paint, hero phải vẽ lại ở vị trí cố định).
- Mốc 1 phải gồm hero (xem Kết luận 3).

## T18b: nội suy camera + hero (XONG phần code; cờ mặc định TẮT)

Công nhân Sonnet (T18b/c), WORK `~/hl5work_t18`. Chưa commit, chưa chạy make_apk/--update-repo. Dev .so: `~/hl5work_t18/t16/dev/dev.so` (có interp) và `.../t16/base/dev.so` (đối chứng: cùng cây nhưng KHÔNG có interp.c + 4 dòng hook). Dựng: `WORK=~/hl5work_t18 bash harness/t18/build_dev.sh dev` và `... build_dev.sh base --nointerp` (bản sao engine vào `$WORK/t18src`, không ghi engine/out của repo).

### Phát hiện RE mới (bằng chứng so.dis + harness) làm thay đổi thiết kế mốc 1
1. **Camera là DẪN XUẤT từ hero, không có biến camera độc lập để lerp.** `Map::MapMove` 0xafc54 (gọi cuối `Battle::Process`, 0x46540) đặt `Map+0xc0/+0xc4 = HERO::GetX/GetY` (0xb01a8..0xb01c0: `[gv+5180]` = hero) rồi cuộn **bộ đệm nền vòng** (Map+0xc8..0xec) theo từng dải 32 px bằng `Map::DrawQuarter`; `Map::Paint` 0xb129c -> `Map::Update` 0xb10bc chỉ chép bộ đệm (FBOff, kích thước = màn hình) ra FB qua 4 lần `Map::CopyFrameBuffer`. Hệ quả: đổi 4 trường camera (cách T18a) làm dải rộng (curr-p) ở mép màn hình hiện nội dung bọc vòng SAI (bộ đệm chỉ chứa đúng cửa sổ tại curr). Cách đúng: **ghi hero (OBJECT+4/+8) nội suy rồi gọi lại `Map::MapMove()`** (game tự vẽ dải mới, camera + bộ đệm + biên cuộn dẫn xuất nhất quán, kẹp ở rìa map miễn phí); sau paint trả hero thật và gọi `Map::MapMove()` lần nữa để bộ đệm về đúng cửa sổ của vị trí thật. Y camera: = hero.y (0xb01b8..0xb01c0), không cần dò thêm +0xc4/+0xcc.
2. **91,8% CPU của paint là `internalGrpCopyFrameBuffer`** (vòng chép điểm ảnh chạy trong guest; `p11.py`: 93,3 triệu byte-khối / 101,7 triệu mỗi tick ở 800x360; `DrawImageBlendingClip565` 5,7%). Mọi khung (kể cả khung gốc) đang trả ~92% thời gian cho 4 lần chép nền mà bản thân host `memcpy` làm được gần như miễn phí (guest đã gọi memcpy của bridge, 119 587 lần/40 tick trong c4). **Đề xuất việc riêng (T19): thay `internalGrpCopyFrameBuffer` bằng chép từng hàng qua memcpy** => paint rẻ ~10x, làm T18 khả thi trên máy yếu, và tự nó tăng FPS cho cả game. Chi phí `Map::MapMove`: 135k lệnh/lần ở 400x240 (1,5% một paint), 2 lần/khung trung gian.
3. **Nhịp thật của bản gốc = 20 Hz (3 vsync), không phải 22:** TimerCb đặt trễ `1000/22 - t_khung`; `MC_knlSetTimer` bắn khi `now+10 > hạn` (timerLoop 0x14d1d8); chế độ bảng `extSetTimer` (cờ `internalTimerNanoCheck` 0x16a200, harness = 0) có hạn `now+ms-20`. Code đọc cờ này để tính hạn tick đúng cả hai chế độ.
4. Điều kiện "chơi thường" đã có sẵn trong code của gói T14/T16: `StateGame` (vtable 0x15e148+8), `[self+0xc]==0`, và không có `[[gv+5204]+0x75]` (hội thoại). Thêm: `Map+0x115==0` (không rung map, `Map::MapShake` 0xa9f08) và `!HERO::IsShake()/IsShakeHit()` (0x855cc/0x855fc; Battle::Process 0x46508..0x46528 bỏ MapMove khi hero rung).
5. `mainPaint` ghi `gv+0x243` (byte) và `gv+6492` (u32) mỗi lần gọi; `mainEndProcess` tăng `gv+0x130` (đã biết ở T18a). Khung trung gian lưu/khôi phục cả ba, đặt `gv+0x130` = giá trị lúc paint của tick.

### Kiến trúc đã cài (`engine/menu/interp.c`, 4 điểm móc, KHÔNG sửa guest nào khác)
Giữ nhịp logic gốc. Cờ bật, chỉ khi hero đang di chuyển và còn chỗ cho >= 1 khung (`rem*10 > 13*t_paint`):
- **Tick thật** chạy nguyên vẹn; chỉ `mainPaint` của tick (HOOK 0x99fa4) vẽ hero ở vị trí tick TRƯỚC (alpha=0; "hiển thị trễ 1 tick" của Fix-Your-Timestep, đúng ghi chú thiết kế) + `Map::MapMove` trước/sau. Chụp `prev<-curr`, `curr<-hero(x,y)` ngay trước paint.
- Cuối tick (HOOK 0x99f90 thay `bl CUSTOM_extSetTimer`): tính hạn tick `D` (theo chế độ timer), đặt timer 16 ms thay vì trễ gốc, `armed=1`.
- Đầu TimerCb (HOOK 0x99dfc thay `bl MC_knlCurrentTime`, stub asm `interp_entry`): `armed` và chưa tới `D` => **khung trung gian**: `alpha=(now-T)/(D-T)` kẹp [0,1], hero=lerp, `MapMove`, `mainPaint`, `CUSTOM_grpFlushLcd`, khôi phục (hero, `MapMove`, gv+0x130/0x243/6492), đặt lại timer 16 ms, thoát TimerCb bằng đúng epilogue của nó (`add sp,#92; pop {r4-r11,pc}`). Không phím, logic, âm thanh, EndProc. Tới `D` (`now-D>0`, cùng điều kiện nghiêm ngặt của timer gốc) => TimerCb chạy nguyên vẹn.
- `Monster::IncreaseTempFrame` (HOOK 0xbee5c) bị bỏ qua khi `mid=1` (chỉ khung trung gian).
- Thích nghi CPU: mỗi paint đo `t_paint` (MC_knlCurrentTime, trung bình mũ 1/4); khung trung gian chỉ vẽ khi `(D-now)*10 > 13*t_paint`; máy chậm không bao giờ vào chế độ trễ (tick vẽ bình thường, timer gốc).
- Chặn tức thời: `|Δx|,|Δy| >= 96` px, đổi hero/Map*, hoặc ngoài StateGame chơi thường => snap (vẽ thật, không nội suy). Cờ tắt => `interp_settimer` gọi đúng `CUSTOM_extSetTimer` gốc với tham số gốc, không gọi `MC_knlCurrentTime`; `armed` luôn 0 => `interp_entry` chạy đường gốc.
- Không cấp phát, không dữ liệu khởi tạo (struct `interp_st` 80 B ở bss). Đã xác nhận bằng symbol (`menu.sym`).

### Cờ
Tệp `interp.txt` (ký tự `1` = bật; thiếu/khác = tắt), đọc bằng `StaticUtil::FileRead` (0x1416e0) đúng cơ chế `hd_scale.txt` (mmenu.c:307), **đọc một lần** ở tick đầu (đổi cờ cần khởi động lại, như hd_scale). Harness: ghi `interp.txt` vào thư mục lưu rồi chạy, `interp_st.flag==2` (reg.py, 3 cỡ). Có sẵn `interp_set(int on)` (ghi tệp + đổi cờ ngay) và `interp_get()` cho mục Cài đặt (chưa gọi từ đâu).

### Đề xuất ghép (quản đốc ghép; trong cây repo tôi đã THÊM, không sửa/xoá dòng người khác)
- `engine/menu/interp.c` (mới).
- `hooks.def` (4 dòng, cuối file, có nhãn `# T18`): `HOOK_BL(0x99dfc, 0x146da0, interp_entry)`, `HOOK_BL(0x99f90, 0x36100, interp_settimer)`, `HOOK_BL(0x99fa4, 0x987c0, interp_tick_paint)`, `HOOK_BL(0xbee5c, 0xb66c4, interp_inc_temp)`.
- `build.sh`: 1 dòng biên dịch `for f in interp; ...` + 1 khối `--defsym` (MC_knlCurrentTime, CUSTOM_extSetTimer, CUSTOM_grpFlushLcd, mainPaint(), Map::MapMove(), Monster::IncreaseTempFrame(), HERO::IsShake()/IsShakeHit(), Graphic::GetWidth()/GetHeight(), internalTimerNanoCheck, vtable for StateGame).
- Không cần patch bridge, không cần dòng so_patches.tsv thủ công (4 dòng `bl` do `inject_menu.py` sinh, loại `T17c2`).
- Mục Cài đặt đề xuất (chưa sửa mmenu.c): "Mượt 60 fps (thử nghiệm)" Bật/Tắt gọi `interp_set(1/0)` (hiệu lực từ tick kế vì cờ đọc một lần: nên sửa `flag_load` thành đọc lại khi menu đóng, hoặc để `interp_set` đủ vì nó đặt `I.flag` tại chỗ).

## T18c: kiểm (400x240, cảnh chơi sau khung 1300; đi phải / bắn OK / đi trái; vsync giả lập 16 ms/vòng)

| # | Kiểm | Kết quả | Chứng cứ |
|---|---|---|---|
| (1) | Cờ tắt = gốc | **ĐẠT.** 150 vòng: số flush/tick trùng, **ảnh FB trùng từng khung (0/150 khác)**; bộ nhớ .so+heap khác ở đúng 14 dải mã (độ dời `bl` của hook do menu đổi kích thước + header ELF), KHÔNG dải dữ liệu nào | `c1.py` (dev vs base) |
| (2) | Bật = tắt cùng phím | **Logic ĐẠT**: 130 tick, vị trí hero trùng 130/130, số khối heap và `top` trùng, **bộ đệm nền FBOff (0x20130b00..) trùng từng byte ở mọi mốc**. Còn lại khác biệt chỉ ở: thời gian (gv+0x1da0, gv+0, 0x2004afa0 `ParticleMgr::process`: cũng khác khi so 2 lần chạy cờ TẮT lệch nhịp 16/19 ms, `c2.py OFFOFF=1`); chỉ số hàng đợi sự kiện timer của kernel (0x200015b4/b8, ghi bởi `__handleCletEvent`/`timerLoop`, đếm số lần TimerCb); trạng thái clip/bối cảnh đồ hoạ ghi lại mỗi paint (0x203623a8.. `Map::SetMapClip`, `MapMove`; 0x2003d3d0 `MC_grpSetContext`); vài dải RGB565 nằm ngay sau FB (0x2003050f.., 13-134 B, xuất hiện thoáng ở 4/13 mốc, lệch 1 đơn vị màu): CHƯA quy được nguyên nhân (xem Vướng mắc) | `c2.py`, `c8.py` (ai ghi), `c10.py` |
| (3) | Ảnh trung gian | **ĐẠT**: ép alpha 0/64/128/192/256 (hero đi 7 px/tick): nền dịch -0,-2,-4,-5,-7 px (kỳ vọng 0, -1,75, -3,5, -5,25, -7; chênh <= 0,5 px do làm tròn hero); **dải mép trái/phải vừa lộ ra khớp 100% điểm với ảnh tick kề** (không có dải bọc vòng sai); alpha=0 == khung tick (chỉ khác 724 điểm = sprite hero/kiếm, hoạt hình tiến 1 khung sau EndProc) | `c3.py`; ảnh `~/hl5work_t18/out/c3/` -> shots/T18 |
| (4) | Không cấp phát | **ĐẠT**: 33 khung trung gian: số khối heap và `top` không đổi; hàm host gọi: pthread_mutex, ftime(4), vsnprintf+strlen (4, vẽ chữ HUD), memcpy/memset, glBindTexture/glTexSubImage2D/glDrawArrays (flush); KHÔNG malloc/calloc/free | `c4.py` |
| (5) | Số khung/tick ở 800x360 | xem bảng dưới | `c5.py` |

Hồi quy 3 cỡ (`reg.py`, cờ qua TỆP `interp.txt`, tới cảnh chơi, đi phải, tắt rồi bật; ảnh `shots/T18/reg_*`): 400x240 ĐẠT (20 tick: tắt 20 flush, bật 39 flush = 19 khung trung gian), 600x270 ĐẠT (20 tick: 20 / 39), 1200x540 ĐẠT (15 tick: 15 / 29). Hero cùng vị trí cuối trong cả hai chế độ ở cả 3 cỡ.

### (5) Mô phỏng thời gian 800x360 (GIẢ ĐỊNH, chưa có số đo máy thật)
Mô hình: vsync 16,667 ms; nativeLoop chạy ở vsync đầu tiên sau khi loop trước xong; chi phí guest = mainPaint 25,2 triệu lệnh (byte khối/4, T18a-4), mainProcess 0,16 triệu, MapMove 0,2 triệu (hiệu chuẩn 135k ở 400x240, nhân theo chiều cao), flush/GL phía host = 0; "ipms" = nghìn lệnh guest/ms của máy. Hero luôn di chuyển (đi qua lại).
Kết quả đã chạy (`~/hl5work_t18/out/c5_800_on.txt`, `_off.txt`; 5 s ảo mỗi tốc độ): nhịp tick giữ đúng như bản gốc ở mọi tốc độ (12,0 Hz ở 400k lệnh/ms vì tick dài hơn 45 ms; 19,9-20,1 Hz từ 800k trở lên; trùng chế độ tắt) => **cờ bật không đổi tốc độ game**. t_paint đo trong guest = 66 / 32-34 / 18 / 13 / 10 / 6 ms ở 400 / 800 / 1600 / 2400 / 3200 / 6400 k lệnh/ms (đúng 25,2 triệu / ipms).
**CHƯA CÓ số "khung trung gian mỗi tick" đáng tin ở 800x360:** trong lần chạy c5 hero kẹt ở tường x=504 (đổi hướng LEFT không làm hero đi) nên `moved=false` gần như suốt, mid_per_tick = 0 (lag_ticks 0-5). Số đo thay thế đã có: c0/reg.py (400x240, 600x270, 1200x540, thời gian ảo không tính chi phí): **1 khung trung gian mỗi tick (2 khung/tick = 60 fps ở nhịp tick 2 vsync)**; mô hình: nhịp gốc 20 Hz = 3 vsync => tối đa 2 khung trung gian/tick khi `t_paint + 2*MapMove <= ~16 ms` tức tốc độ >= 1,6 triệu lệnh/ms ở 800x360 (cần >= 0,6 / 1,0 triệu ở 400x240 / 600x270); dưới ngưỡng cơ chế thích nghi tự tắt (đã thấy `lag_ticks`/`skipped` ~0 ở 800k). **Việc kế để có số này:** sửa kịch bản c5 (chạy từ đầu mỗi tốc độ theo chuỗi phím c2: RIGHT tick 5..40, rồi LEFT 60..100) và đếm `n_mid/n_tick`.

## Vướng mắc / rủi ro / việc kế
1. **Chi phí (kết luận T18a vẫn đúng và nặng hơn):** paint 25 triệu lệnh ở 800x360 => cần >= 1,5 triệu lệnh/ms (1,5 G lệnh/s) để vẽ 1 khung trung gian trong 16 ms; Unicorn trên điện thoại thường 0,05-0,3 G lệnh/s => T18 gần như không bao giờ có khung trung gian trên máy thật **nếu giữ `internalGrpCopyFrameBuffer` ở dạng vòng guest**. Hướng cứu: T19 (memcpy theo hàng, mục 2 trên) gỡ ~92% chi phí paint, đồng thời cải thiện FPS thật của cả game. Nên làm T19 trước khi thử T18 trên máy người dùng.
2. **Khác biệt còn lại khung trung gian vs khung tick (724 điểm, sprite hero/kiếm, y=92..122 ở 400x240):** hoạt hình sprite của hero tiến thêm 1 khung sau `EndProc` (chưa tìm đúng trường: chỉ đã loại trừ thời gian (c10) và gv+0x130 (đã khôi phục)). Hậu quả thẩm mỹ: hero/kiếm hiện khung hoạt hình kế sớm hơn tối đa 1 tick, rồi tick sau vẽ lại đúng khung đó (không nhảy ngược). Việc kế: so trường trước/sau `mainEndProcess` bằng diff bộ nhớ (cần quét `CHAR`/`HERO` sprite frame).
3. Các dải RGB565 nằm sát sau FB (0x2003050f..) khác thoáng giữa chế độ bật/tắt (mục (2)); không thấy trong đối chứng thời gian; có thể là bộ đệm tạm của hiệu ứng vẽ. Chưa kiểm hết kịch bản (kỹ năng, quái chết, rơi đồ, map khác, popup, cắt cảnh: loại khỏi phạm vi bằng `eligible()`, chỉ kiểm tĩnh).
4. **Độ trễ cảm nhận:** chế độ trễ thêm 1 tick (~50 ms) cho hero+camera (kiến trúc A); quái/hiệu ứng vẫn vẽ ở tick hiện tại => hero lệch tối đa 1 bước (7 px) so với quái trong lúc di chuyển (hạn chế của mốc 1; mốc 2 nội suy cả đối tượng).
5. Các đường thoát không móc: nhánh `mainKeyPressed==0` (không paint), màn hình menu (timer gốc), đường cert-error.
6. Chưa thử trên thiết bị thật. Cờ đọc một lần: đổi cờ cần `interp_set`.

## Lệnh tái hiện (WORK=~/hl5work_t18; harness/t18/)
`bash harness/t18/build_dev.sh dev; bash harness/t18/build_dev.sh base --nointerp` rồi: `c1.py 400 240 150` (tắt/gốc), `c2.py 400 240 130` (bật vs tắt; `OFFOFF=1`, `DETAIL=1`), `c3.py 400 240 20` (ảnh khung trung gian), `c4.py 400 240 80` (cấp phát), `c5.py 800 360 800,1600,3200,6400 5 1` (mô phỏng thời gian, `MM_INS=200000`), `c6.py 400 240 12` (chi phí MapMove), `c8.py 400 240` (ai ghi địa chỉ khác), `c10.py`, `reg.py W H N`. Mã mới: `p11.py` (phân bổ chi phí theo hàm), `p12/p13.py` (timer), `t18b.py` (thư viện).

Danh sách file: mới `engine/menu/interp.c`, `harness/t18/{build_dev.sh,t18b.py,c0..c10.py,p11..p13.py,reg.py}`, `shots/T18/*`; sửa (chỉ thêm dòng) `engine/menu/hooks.def`, `engine/menu/build.sh`; thay mục T18b/T18c trong file này.

## CÒN LẠI (công nhân dừng ở ~270k ngữ cảnh)
1. Số đo (5) ở 800x360 với hero di chuyển liên tục (mục trên). 2. Quy nguyên nhân 724 điểm sprite hero/kiếm khác giữa khung trung gian và khung tick, và các dải RGB565 sau FB (vướng mắc 2, 3). 3. Kiểm thêm kịch bản: kỹ năng, quái chết/rơi đồ, đổi map, rung màn hình (eligible() loại), popup/hội thoại bật giữa chừng. 4. T19 (memcpy cho `internalGrpCopyFrameBuffer`) trước khi thử trên máy người dùng. 5. Quản đốc ghép hooks.def/build.sh/interp.c (đã nằm sẵn trong cây repo, chưa commit) rồi dựng APK thử; mục Cài đặt "Mượt 60 fps" gọi `interp_set`.
