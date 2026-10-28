# BÁO CÁO — Việt hoá "Tiên Sủng Kỳ Duyên" (bàn giao toàn bộ dự án)

## Kết quả
| | 240x320 | 320x240 |
|---|---|---|
| Jar | `out/TienSungKyDuyen_240x320_vi.jar` (1 016 185 B) | `out/TienSungKyDuyen_320x240_vi.jar` (1 000 232 B) |
| Jad | `.jad` cùng tên, `MIDlet-Jar-Size` khớp jar, giữ MIDlet-Name/Vendor | như trái |
| `MIDlet-Description` | `rpg,N73` (gốc) | `rpg,E62` |
| `jar_check.py` | **RESULT: PASS** (`logs/jar_check_240.txt`) | **RESULT: PASS** (`logs/jar_check_320.txt`) |
| `preverify.sh` (ProGuard -microedition) | OK | OK |

- `dp check`: OK (1573/1573 dòng dịch tay, một công nhân dịch, `dp merge` → `dich/dp_work/final/text_can_dich.tsv`); `scripts/repack.py --check`: OK, 2158 hàng, 0 cảnh báo (charset f12, `^ | #`, token, trạng thái ×5 và "没有" đồng nhất).
- `tools/check_all.sh` (bộ tool NAM.P mới `tool_main/Tool_Viet_Hoa_NAM_P.zip`, có f12): **tất cả pass** (font 8 file, sinh lại f15/f13 byte-exact, selftest pixeltext, FreeJ2ME, FontDemo, selftest f12).
- `rebuild.sh` chạy sạch một lần: dựng lại 2 jar + 2 jad; so với jar dựng trước đó: **cùng 581 entry, nội dung từng file giống hệt, cùng thứ tự; chỉ khác SHA của file zip do timestamp** (`logs/rebuild_sha256.txt`). `RUN_EMU=1`: FreeJ2ME cả hai bản qua logo → menu → CG, không exception ngoài lỗi MIDI headless của container (`Sequencer ... is null`, không phải lỗi game).
- Dựng lại từ gói zip dự án (giải nén thư mục tạm, chạy `rebuild.sh`): xem mục cuối.

## Đã sửa gì (tóm tắt theo task, chi tiết ở PLAN.md / docs/)
- **Text**: 486 blob pak (1668 trường Hán), 5 bảng `t/item*.t` (350 ô), 145 chuỗi trong class → TSV 2158 hàng, đóng gói lại giống từng byte khi chưa dịch (`selftest_repack.sh`).
- **Vẽ chữ** (`hook/VH.java`): font `f12.bin` 12 px; 29 call-site `drawString`, 24 `stringWidth`, 4 `getHeight` thay bằng `VH.*`; thay thân `Main/g.a` (thoại), `rf/b.a/b` (ngắt dòng), `rf/b.a`/`Main/f.a` (viền chữ). Ngắt dòng theo px ở dấu cách, có cache.
- **Khởi động** (QĐ 9): logo NAMP 2 giây, bỏ hỏi nhạc (nhạc luôn bật) và logo nhà phát hành.
- **Ảnh** (T8/T8b): 20 ảnh có chữ vẽ lại f12 viền đen 1 px (`res_vi/`), `res_vi_320/menu.png`, `namp.png` ngang cho 320x240.
- **Bố cục** (T7/T7b): thoại 4 dòng/trang; ô mô tả và CG lật trang (không cắt nửa dòng); phím mềm căn theo độ rộng thật; mũi tên menu chính theo nhãn; ẩn "Game khác" (web quảng cáo); cửa hàng giá căn phải, khối ◀số▶ trước giá; cache chuỗi nối (50 chỗ); `spaceConcat` chèn dấu cách khi chuỗi dịch nối với biến (45 chỗ).
- **320x240** (T9): `MIDlet-Description: rpg,E62`, bỏ remap phím của E62 (`Main/j.d`), `VH.keyEq` cho nhóm phím tương đương ở 4 chỗ `Main/a`.

## Ảnh chụp FreeJ2ME
`logs/shots_T4` (khởi đầu), `shots_T5` (logo), `shots_T7` (26 ảnh 240x320: menu, thoại, CG, túi đồ, cửa hàng, chiến đấu, nhiệm vụ, lưu/tải…), `shots_T8` (ảnh vẽ lại trước/sau), `shots_T9` (15 ảnh 320x240), `out/emu_rebuild/` (kết quả `rebuild.sh`).

## Ký tự font thiếu (f12, 228 ký tự)
Chỉ thiếu `'` trong ASCII in được. Có `_ = ~`, đủ chữ Việt có dấu. Không có dấu câu Hán nên bản dịch đã đổi sang ASCII: `：→:` `，→,` `。→.` `！→!` `？→?` `…→...` `“”「」→"` `、→,` `（）→()`. Ký tự ngoài font khi vẽ: chữ Hán/dấu câu Hán = ô trống rộng 12 px, ký tự khác = rộng dấu cách. `repack --check` chặn mọi ký tự ngoài f12 trong bản dịch.

## Debug — lỗi đã gặp và cách xử lý
1. **ProGuard `NegativeStackSizeException` ở `Main/j.a(...)`** khi thay thân method cuộn: mô tả hàm VH sai số tham số `I`. Sửa descriptor; từ đó Patch in số khớp từng chỗ và dừng nếu lệch.
2. **Cuộn 1 px/khung cắt nửa dòng ở mép clip** (CG tràn đáy): thay bằng lật trang theo dòng nguyên (`VH.scroll`).
3. **Ghi dấu cách sai vào đường dẫn tài nguyên** ("/Image/ ") khi thử `spaceConcat` bằng phép thử "≥ 2 chữ cái": sửa bằng cách chỉ sửa hằng LDC có trong cột dịch của hàng `class:` (Patch nhận TSV làm tham số thứ 4).
4. **Menu chính "Game khác"**: không xoá được mục (lệch chỉ số) → `VH.menuSkip` nhảy qua mục khi bấm trái/phải, chỉ khi không có màn con mở.
5. **Chụp màn trong game không vào được menu**: hướng dẫn ép bấm đúng phím (cmd 51). Dùng `scripts/t7_test_jar.py` (chỉ để chụp, không phát hành) thay cmd 51 bằng cmd không-op, cấp vật phẩm (46) và mở cửa hàng (48).
6. **Phím số không tới game trong Xvfb**: FreeJ2ME chỉ nhận NumPad (`VK_NUMPAD0`); bật NumLock rồi `KP_0`. Phím chữ QWERTY không được phát.
7. **`VH.shopCol` ném `ArrayIndexOutOfBounds`** ở danh sách 2 cột (nhiệm vụ) sau khi đổi bố cục cửa hàng: thêm kiểm độ dài; phát hiện nhờ đọc `emu.log` ở T9.
8. **`rebuild`**: ProGuard đọc đường dẫn tương đối theo thư mục file `.pro` (tmp) → `preverify.sh` đổi hết sang tuyệt đối; `jbase` (java.base bóc từ `jmods`) dựng vào `build/jbase`, không nằm trong gói.
9. **`pkill -f` trong phiên chụp giết cả shell** → dùng lệnh `quit` của `t7_emu.py --follow`.

## Việc tồn
- Chữ `l` thường của f12 vẽ giống `L` (đuôi cong) nên "lại/làm/lên" trông như "Lại/Làm/Lên": lỗi thiết kế font bộ tool, không sửa glyph theo quy tắc skill; báo người dùng.
- `VH.keyEq` cho phím chữ QWERTY (m, t, …) chưa kiểm được trên emulator (FreeJ2ME không phát); nhóm số/mũi tên đã kiểm ở 320x240.
- 320x240: các panel cao 232 px (hằng 200 của game) nên cuộn trên/dưới của cửa hàng/túi đồ/nhiệm vụ bị cắt vài px (nội dung vẫn đủ, giống game gốc trên E62).
- `zt0..zt4.png` (hoa văn 金木水火土 dưới chân), `ui_zhou_w.png` giữ nguyên (không phải nhãn).
- Ảnh có tên rút gọn cho vừa ô (Bảo, Đồ giám, Lên 3 cấp, Ch/Ph, Mộc Lỗi, Sa Lĩnh, Mộ, "Hỏa Thạch", "Lãnh Phong", "Liên Trì"); vẽ viền đen 1 px nên chữ trên nền tối/sáng lẫn vẫn đọc được nhưng khác gốc.
- Chưa chụp được: màn "Tạm dừng" (cần `pauseApp` máy thật), CG thứ 2, chiêu có trạng thái, thông báo "Lên cấp!".
- T11: bộ tool main thêm 12 px + phát hành jar/jad vào `main:releases/` do quản đốc.

## Dựng lại từ gói zip
`out/TienSungKyDuyen_du_an.zip` (22,7 MB; không commit lên git vì > 20 MB) được giải nén vào thư mục tạm, `./rebuild.sh` chạy từ đó chỉ dùng `tools/` trong gói: **REBUILD OK**, `jar_check` PASS cả hai bản, nội dung 2 jar giống hệt jar trong repo (581 entry; chỉ khác timestamp zip), 2 jad giống hệt từng byte; `tools/check_all.sh` trong gói: tất cả pass. Tạo gói: `scripts/pack_du_an.sh`.
