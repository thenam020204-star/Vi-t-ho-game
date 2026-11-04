# BAO-CAO-T16: khung hội thoại mới

Công nhân T16 (Sonnet), WORK `~/hl5work_t16`. Mockup: `ui_new/dialog/mockup_dialog_hd.jpg` (+ `_sach.jpg`), hiện trạng `ref_dialog_v02hd.jpg`. Code: `engine/menu/dialog.c` (+ `gen_dlg_text.py`), harness `harness/t16/`.

## 1. T16a: kết quả dò (disasm `so.dis` + ghi tham số bằng hook Unicorn, `harness/t16/probe3.py`/`probe4.py`)

**Ai vẽ.** Toàn bộ do guest `libHeroesLore5.so` (không phải Java/bridge). Hộp thoại thuộc `EventProc` (con trỏ `*(gv+5204)`, ví dụ 0x202b9be0 trong harness), được gọi từ `StateGame::Paint` -> `EventProc::Event_Draw` 0x6ddcc (hàm ảo). Hộp xuất hiện khi script sự kiện (cốt truyện đầu game, NPC) gọi `EventProc::Event_SituateDialogText(id_tên?, mặt, slot, id_chuỗi)` 0x6f44c; cờ "đang trong sự kiện" = byte `[EventProc+0x75]` (=1 suốt hoạt cảnh; `StateGame::TouchPressed/TouchMoved` dùng chính cờ này).

**`DIALOG_INFO`** (60 byte, mảng 2 slot tại `EventProc+176+60*i`; `[EventProc+172]` = slot đang nói, `+173/+174` = chỉ số slot): `+7` id tên NPC (`TextMgr::GetNpcNameText`), `+8` số dòng tối đa (đo = 3), `+9` trang, `+10` tốc độ chữ chạy (ký tự/khung; 1 = chạy, 1000 = hiện hết sau khi bấm), `+12` tiến độ (số ký tự đã hiện), `+14/+16/+18` x/y/w vùng chữ, `+20/+22/+24/+26` x/y/w/h hộp, `+32` con trỏ chuỗi, `+36` `GbmImage*` chân dung (null = không có), `+40` phía (0 trái, 1 phải), `+43` trạng thái (0 ẩn, 2 = đang nói, 1/3..7 hoạt ảnh vào/ra/rung), `+45` alpha, `+46` cờ vẽ (8 phía trái, 16 phải), `+48/+50` vị trí gốc, `+52/+54` lệch hiệu ứng (rung/trượt), `+56/+58` rộng/cao ảnh (đo 155x220, cố định mọi cỡ màn).

**Hình học gốc** (`Event_SituateDialogText` 0x6f44c, ghi lúc đặt chuỗi): hộp x=0, y=H-h, w=W (cả bề ngang), h=(HanFontHeight+2)*dòng+10 (600x270: 49 px, font 11); vùng chữ x=8, y=y_hộp+6, w=W-16. `SetFacePosition` 0x6f370 (gọi cuối hàm) nạp ảnh chân dung, đặt chân dung dán đáy màn: trái x=0 / phải x=W-155, y=H-220.

**Vẽ mỗi khung** (`Event_Draw`): với mỗi slot có `+43 != 0`: `DIALOG_INFO::Draw_Face` 0x6dd1c (`Graphic::DrawEffect` ảnh tại +48+52, +50+54, cờ +46, alpha +45), nếu là slot đang nói và +43 == 2: `Event_DialogWindow` 0x6afd0 = `GMenu::DrawDialogBox` (4 góc DrawEffect) + chữ + `GMenu::NameBox` (bảng tên, chỉ khi id tên 1..254); cuối cùng `EventSkipDraw` 0x67f54 (mờ toàn màn khi `[+0x6e2]==2`, và khi `[+0x6e1]==1 && [+164]==0` vẽ hai ảnh `DrawImageBlending` ở (0,0) = nhãn "#: Skip" của ui.mgr).

**Chữ chạy từng ký tự**: `Event_DialogWindow` gọi `BFont::DrawTextField(fb, x, y, w, dòng, chuỗi, trang, tiến_độ, tốc_độ, 0)` HAI lần cùng vị trí: lần 1 màu 0x404040 + `SetColorFixed(1)` với tiến độ 30000 (bản xem trước mờ của cả trang), lần 2 trắng với tiến độ `[+12]`; trả về số ký tự thêm, hàm cộng vào `[+12]` nếu `<= 29999`. Xuống dòng/lật trang HOÀN TOÀN do BFont (engine chữ Việt vn.c đã vá) tính từ cùng các trường: `w=[+18]`, `dòng=[+8]`.

**Lật trang / chạm / skip** (`EventProc::Event_Key` 0x6d214, khi `[+0xa8]==57`): bấm bất kỳ phím: `IsTextFieldDrawComplete(w, dòng, chuỗi, trang, tiến_độ, tốc_độ)` = 0 -> `[+10]=1000` (hiện hết trang ngay); khác 0 -> `trang++`, nếu `trang == GetTextFieldPageLen(w, dòng, chuỗi)` -> `[D+0xb6]=1`, kết thúc hộp, ngược lại tiến độ=0, tốc độ=1. Phím '#' (35) = `EventSkipStart` khi `[+0x6e0]==0 && [+0x6e1]==1 && [+164]==0`. Chạm: `StateGame::TouchPressed` 0xde518, state 0, khi `[EventProc+0x75] != 0`: chạm trong vùng 0..60 x 0..30 (CỐ ĐỊNH, logic px) -> `KeyPressed(35)` (0xdeadc), chạm khác -> `KeyPressed(-5)`; cuối hàm `gv[0x127]=0`. `TouchMoved` thoát ngay khi cờ `+0x75` bật. (Vùng 60x30 cố định nên ở 1200x540 chỉ còn 5% x 5.5% màn: lý do cần nút Bỏ qua lớn hơn.)

**Dữ liệu**: chuỗi = byte mã game (từ điển 2 byte) từ `Interpreter::Strings::getString`; id tên NPC -> `TextMgr::GetNpcNameText(_pTextMgr, id)`; chân dung nạp `DIALOG_INFO::Load_Faceimage(id)` 0x684c8 khi đổi id (`+0x2a`).

### Đề xuất đã chọn (và làm ở T16b)
1. **Giữ nguyên toàn bộ logic**: ghi hình học mới vào chính các trường DIALOG_INFO ngay sau `SetFacePosition` (hook `bl` 0x6f5c4) nên `DrawTextField`/`IsTextFieldDrawComplete`/`GetTextFieldPageLen` (xuống dòng, trang, lật trang, skip, tốc độ) dùng nguyên bề rộng mới mà không patch BFont/vn.c.
2. **Thay hàm vẽ** bằng hàm trong `engine/menu/dialog.c` qua `HOOK_BL` (hooks.def): `Event_DialogWindow` (2 call-site), `Draw_Face` (2), hai `DrawImageBlending` trong `EventSkipDraw` (nhãn "#: Skip" -> nút "Bỏ qua ▶▶"; trampoline asm: cờ `MUI_F_DISABLE` tắt thì nhảy về hàm gốc nguyên tham số).
3. **Chạm**: dòng `MUI_STATE(GAME, dlg, ...)` trong states.def: Paint = Paint gốc, chạm: nút Bỏ qua -> `KeyPressed(35)`, chạm khác -> `KeyPressed(-5)`.
