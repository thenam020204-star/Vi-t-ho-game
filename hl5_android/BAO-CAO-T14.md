# BAO-CAO-T14: HUD chiến đấu mới (T14a kiểm kê + T14b 7 nút) + sửa bố cục + dpad 8 hướng

Trạng thái: DỞ (dừng theo ngân sách ngữ cảnh). Công nhân: Sonnet, WORK ~/hl5work_t14, gốc so sánh ~/hl5work/orig_v02hd. Không commit, không inject --update-repo, không make_apk.

## 1. T14a kiểm kê (mã máy + harness, ảnh `shots/T14/ui_hien_tai.png`)
Guest tự vẽ toàn bộ nút cảm ứng (không phải bridge/Java). Toạ độ ở 600x270 (hệ logic guest, neo phải/dưới theo hằng số):
| # | Nút | Nơi vẽ (địa chỉ) | Vùng chạm gốc | Hành động |
|---|-----|------------------|---------------|-----------|
| 1 | Tấn công (vòng cam) | StateGame::DrawTouchUi 0xdc1ac: DrawImageBlending 0xdc29c/0xdc304, tâm (W-31,H-40) | rect SetTouchKeypad 0xdd3f4 idx 8 = (W-61,H-70,60x60) | KeyPressed(-5) -> UseSkill/SetAttackMotion |
| 2-7 | 6 ô kỹ năng/bình (cung) | GMenu::DrawSkillBelt 0x7f9f8 (gọi 0x4492c trong Battle::Paint); khung DrawEffect 0x7fc84, biểu tượng 0x7fc1c/0x7fcd0 | 30x30 tại (TypeX[i],TypeY[i]) (StateGame+224/236; TouchPressed 0xde7b0 vòng 6 ô) | KeyPressed -> Battle::KeyPressed (ô0 UseSkill, ô1 BattleUseItem...) |
| 8 | Đổi bộ (#) | DrawSkillBelt, DrawEffect 0x7fd34 | 30x30 tại (W-191,H-42) (ChangeX/Y +248/+250) | HERO::ChangeHotKeyToggle |
| 9 | DASH | DrawTouchUi 0xdc394/0xdc3f4 | x [W-35,W), y [H-101,H-71) (TouchPressed 0xde814..0xde858) | SetAttackMotion(7)+SetDashFlag+SetDashCheckFrame (trực tiếp, không KeyPressed) |
| 10 | Tạm dừng/menu | CommonUi::DrawTouchCancelUi 0x73ce8, DrawImageBlending 0x73dc4 tại (W,30) | ~x [W-35,W), y [30,60) (đo bằng quét chạm) | StateInGameMenu::SetState (mở menu) |
| 11-13 | "bản đồ / cửa hàng / lưu" | DrawTouchUi vòng 3 lần DrawInfoRect 0xdcf9c + DrawText 0xdcff0, x = W/2-153+50i, y 0, 30x13 | ~30x30 | StateMap::SetMapMode / SetSystemMsgUi (cửa hàng) / HERO::SaveAll |
Dpad gốc: 9 IRect (SetTouchKeypad), chỉ phát KeyPressed(-1 lên, -2 xuống, -3 trái, -4 phải) qua vtable+0x1c (TouchPressed 0xdf0dc/0xdf1b8/0xdf25c/0xdf284); KHÔNG có mã chéo; không vẽ ở bản gốc 600x270. Chéo = giữ hai phím cùng lúc (chưa kiểm hero có đi chéo).
Ánh xạ 7 ảnh: 01/02 = tấn công (thường/nhấn), 03 = nền 6 ô + DASH + bản đồ, 04 = tạm dừng, 05 = lưu, 06 = cửa hàng, 07 = đổi bộ. Tự vẽ (cùng phong cách 03): DASH (khung 03 + chữ "DASH"), bản đồ (khung 03 + biểu tượng bản đồ gấp vẽ bằng điểm), mũi tên dpad.

## 2. T14b đã làm
`engine/menu/hud.c` (+ `gen_hud_art.py` sinh `hud_art.h` từ ui_new, `gen_hud_text.py` thêm nhãn). Hàng `states.def`: `MUI_STATE(GAME, hud, GS==0, hud_paint, hud_touch, hud_move)`; 12 điểm `HOOK_BL` trong `hooks.def` (bắt vẽ nút gốc: 0x4492c, 0x7fc84, 0x7fd34, 0xdc29c, 0xdc304, 0xdc394, 0xdc3f4, 0x73dc4, 0xdcf9c, 0xdcff0); `dispatch.c`: thêm `mui_tz` (con trỏ z) và nhánh nhả của hàng hud; `build.sh`: biên dịch hud.c + defsym GMenu::DrawSkillBelt, Graphic::DrawEffect, CommonUi::DrawInfoRect, BFont::DrawText, StaticUtil::FileWrite, StateGame::KeyReleased.
Cách: Paint GỐC chạy, các điểm vẽ nút gốc bị bắt (cờ S.on chỉ trong hud_paint); ô kỹ năng: chụp nền, guest vẽ biểu tượng/hồi chiêu/số, lấy điểm khác nền rồi khôi phục nền; sau Paint vẽ khung 03 + biểu tượng phóng (NEAREST) + các nút khác (bilinear, premultiplied). Cỡ theo f = H/270 (HD to theo k: 1200x540 gấp đôi 600x270). Chạm: nút mới -> toạ độ TÂM vùng chạm gốc (co giãn kéo) rồi gọi TouchPressed/Moved/Released GỐC => cùng phím/hành động. Sửa dòng "bản đồcửa hàng lưu": các nhãn chữ này không còn vẽ (thay bằng 3 nút ảnh).
Kích thước đoạn menu: filesz 233912 B (+~108 KB do ảnh), reloc=0, 50 dòng patch.
Ảnh: `shots/T14/hud_new_{400x240,600x270,1200x540}.png`, so với `hud_orig_*.png` (400x240, 600x270).

## 3. Kiểm
- Chạm từng nút mới so với chạm tâm vùng gốc ở bản gốc (chữ ký hàm HERO::/StateGame::/Battle:: + state cuối, `harness/t14/check_t14.py`): 400x240 13/13 ĐẠT, 600x270 13/13 ĐẠT (`shots/T14/res_400x240.json`, `res_600x270.json`). 1200x540: CHƯA có kết quả (chỉ ảnh hud_new; tiến trình bị dừng).
- Bản đồ hiển thị (ảnh): đúng 3 cỡ ảnh HUD mới; 1200x540 chỉ soát ảnh.
- KHÔNG ĐẠT / CHƯA KIỂM: nuốt chạm vùng gốc (`swallow_ok False` cả 2 cỡ): chạm vào vùng nút GỐC (đã vắng) vẫn kích hoạt hành động gốc. Nguyên nhân (bằng chứng): bấm nút tạm dừng ở vị trí mới (nằm trong vùng gốc) mở menu ngay lúc nhấn dù hud_touch đã nuốt; chuỗi gọi `mainStateMgr 0x98430 -> BaseState::CallState 0x428bc -> CreateState 0x42698` => guest còn đường xử lý chạm khác (nghi `BaseState::TouchPress` 0x42d80 dùng GetTouchRect/GetTypeX/GetTypeY, hoặc hàm gọi trong mainStateMgr) chạy song song, không đi qua vtable TouchPressed của StateGame. Hệ quả: (a) nhấn giữ nút tạm dừng KHÔNG vào được chế độ sửa bố cục (menu bật trước); (b) nút kéo đi chỗ khác vẫn để lại vùng chạm gốc "ma".
- Chưa chạy: bộ nhớ rò 2000 khung, hồi quy check_t16 và check_cash (shots/T14/hoi_quy/ trống), kiểm sửa bố cục (lưu/đọc/chặn ra ngoài/đè) — mã đã viết (hud.c: edit_*, lay_load/lay_save, `hud_layout.dat` = "HL5H", ver 2, 14 cặp phần nghìn + 1 byte chế độ dpad, ghi bằng StaticUtil::FileWrite vào filesDir như hd_scale.txt) nhưng chưa nghiệm thu.
- Dpad 8 hướng + 2 chế độ (cố định / theo ngón, nút bật chế độ trong màn sửa): mã đã biên dịch (hud.c: draw_dpad, dir_of 8 cung + vùng chết, dkeys phát KeyPressed -1..-4 và StateGame::KeyReleased; chéo = 2 phím) nhưng CHƯA chạy thử lần nào, chưa kiểm hero có đi chéo với 2 phím.

## 4. Cách vào chế độ sửa bố cục (thiết kế) và nơi lưu
Nhấn giữ nút tạm dừng ~HOLD_N=24 khung (ít rủi ro: không đụng menu Cài đặt đã khoá bằng harness, nút luôn có trong HUD). Hiện KHÔNG hoạt động do lỗi ở mục 3. Màn sửa: kéo thả, Lưu / Mặc định / Huỷ + nút chế độ dpad; chặn ra ngoài màn (tâm kẹp theo bán kính), chặn đè nặng (khoảng cách tâm < 0,7 tổng bán kính thì bỏ bước đó). Tệp: `hud_layout.dat` (nội bộ, cạnh hd_scale.txt); lỗi/không có tệp = mặc định.

## 5. File đã sửa/thêm
engine/menu: hud.c (mới), gen_hud_art.py (mới), gen_hud_text.py (mới), dispatch.c, states.def, hooks.def, build.sh. harness/t14: t14lib.py, check_t14.py, probe_draw.py, probe_touch.py, scan_touch.py, make_sheet.py, dbg1.py, dbg2.py, p1.py. shots/T14.
LƯU Ý: `check_t14.py` đọc struct S theo offset NB=13; sau khi thêm dpad (NB=14, thêm dmode/dk/fvis/fcx/fcy/dd) offset mới: want@1..14, nslot@15, ntab@16, edit@17, hold@18, loaded@19, custom@20, cap@21..24, W@60, Ht@64, sel@72, cx@84, cy@140, d@196, old@252, pm@364; cần sửa script (cx/cy/d/want 14 phần tử) trước khi chạy lại. Bản dev hiện tại (build_dev.sh) đã gồm dpad.

## CÒN LẠI (theo thứ tự)
1. Tìm và chặn đường xử lý chạm song song của guest (xem mục 3): hook `BaseState::TouchPress` 0x42d80 (và nơi gọi nó trong mainStateMgr/CletEntry) để nuốt chạm trong vùng gốc khi hud đang bật (hoặc dịch toạ độ ở đó); kiểm lại swallow_ok.
2. Sửa check_t14.py theo offset mới, chạy 400x240, 600x270, 1200x540 (cần chạy tiến trình ở foreground; môi trường chỉ chạy khi có lệnh đang thực thi): chạm 13 nút + dpad, sửa bố cục (giữ/kéo/clamp/đè/Lưu/Mặc định/Huỷ/đọc lại, đổi độ phân giải giữ tỉ lệ), dpad 8 hướng cố định + theo ngón (giữ ngón đổi hướng), hero đi chéo với 2 phím hay không (nếu không: dùng phím số 1/3/7/9 nếu StateGame::KeyPressed hỗ trợ — chưa tra).
3. 2000 khung không rò (so cấp phát với gốc), hồi quy `harness/t16/check_t16.py 600x270` và `harness/l1/check_cash.py 600x270` ghi vào shots/T14/hoi_quy/ (nhớ bản dựng có HUD mới làm ảnh cảnh lệch ở vùng nút: cần mặt nạ như T16).
4. Soát mắt: biểu tượng ô nhỏ hơi tràn khung (đã giảm 66%→60%, chưa dựng lại ảnh), chồng nút ở 400x240, nút DASH chữ ở 1200x540.
5. Hạ tầng: phát hành cần ghép vtable theo quy trình cũ (hooks.def thêm 12 dòng; hàng hud trong states.def).
