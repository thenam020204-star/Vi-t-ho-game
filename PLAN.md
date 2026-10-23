# PLAN: Việt hoá Magic Survival 0.9941 (Android/Unity) — font JejuHallasan theo nét f18

## Mục tiêu
Game hiển thị tiếng Việt đầy đủ khi chọn **English**. Chữ Việt có dấu được **vẽ tay lại dạng vector theo nét f18.bin**
và nhét vào font gốc **JejuHallasan** của game. Toàn bộ text tiếng Anh được dịch tay sang tiếng Việt.
Đầu ra: APK cài được (bản `.apks` + bản APK gộp), ký lại.

## Kết quả kiểm kê (quản đốc, 2026-10-23)
- Nguồn: `Magic_Survival_0.9941.zip/.z01–.z06` (zip chia phần) → `Magic Survival_0.9941.apks` → `base.apk` (194 MB) + `split_config.arm64_v8a.apk`.
  - `zip -s 0` báo lỗi giả; dữ liệu **nguyên vẹn** (inflate nối 7 phần, CRC 63ccbe18 khớp). Giải nén bằng Python zlib (xem T1).
- Unity **2022.3.62f3**, IL2CPP arm64. Tài nguyên trong `assets/bin/Data/data.unity3d` (UnityPy đọc được).
- 7 Font TTF động trong `sharedassets0.assets` (path_id): 30 NotoSansJP-Medium, 31 NotoSansSC-Medium, 32 NotoSansDisplay-Medium,
  **33 JejuHallasan**, 34 NotoSansSC-Regular, 35 NotoSansDisplay-Bold, 36 NotoSansJP-Regular.
  - 6 font Noto **đã đủ** chữ Việt. **JejuHallasan thiếu 133/134 chữ Việt** (có `đ`, `Ð`, `ı`; không có Latin-1 có dấu, không có dấu tổ hợp U+03xx).
  - Jeju: UPM 1000, ascent 800, descent -200, x-height ≈500, cap ≈653–668, có hinting TT (fpgm/prep/cvt).
- UI: 654 `UnityEngine.UI.Text`, **639 dùng JejuHallasan** (cỡ 17–30), 9 NotoDisplay-M, 4 SC-M, 2 JP-M. Không dùng TMP font asset.
- `Z_Database` (level0 path 110, level1 path 7628) giữ mảng font theo thứ tự [33,35,32,30,36,31,34] → **code đổi font theo ngôn ngữ lúc chạy** (chưa biết English dùng font nào → T2).
- Text: 59 TextAsset; 6 file tiếng Anh `eng_Dictionary_{Ability,Class,Explain,MagicCom,Name,Synergy}` (≈335 KB, CSV, UTF-8 BOM, **CRLF** — sửa theo T2).
  Ô có thẻ `<color=#...>`, `{0}`, phân cách `@` `^` `《》` `` ` ``; có ô tiếng Hàn là **khoá dữ liệu** (vd `마법`, `성장`) — không được dịch.
- f18.bin: `writeUTF(charset 227)` · `byte height=18` · `byte width[227]` · atlas 1bpp LSB-first. Có đủ 134 chữ Việt + `…`.
- Mạng: GitHub releases, Maven, dl.google.com dùng được.

## Phạm vi
- Trong phạm vi: font Jeju (+ đảm bảo font English thực dùng có chữ Việt), dịch 6 file eng_*, chuỗi tiếng Anh cứng trong scene/IL2CPP nếu có (T2 kiểm kê, quản đốc chốt), đóng gói APK.
- Ngoài phạm vi: Hàn/Nhật/Trung/Nga…; ảnh có chữ (nếu T2 thấy nhiều thì ghi Vướng mắc); sửa logic game; Google Play/IAP/GPGS sau khi ký lại.

## Quyết định thiết kế (KHÔNG tự đổi khi implement)
- D1. Thay slot **English**. Không đổi tên file/TextAsset, không đổi số dòng/số cột/ID; chỉ thay nội dung ô dịch được.
- D2. Glyph: **vẽ tay vector theo nét f18**, KHÔNG vector hoá ô pixel. Cụ thể:
  - Thân chữ = glyph gốc của Jeju (a e i o u y A E I O U Y, `ı` cho i có dấu, `Ð` cho `Đ`, `d`+gạch → Jeju đã có `đ`).
  - Dấu (sắc, huyền, hỏi, ngã, nặng, mũ, trăng, móc ơ/ư) = contour vector vẽ tay (điểm on/off-curve quadratic, ghi trong `font/marks.py`),
    hình dáng, độ nghiêng, độ cao tương đối và **bố cục dấu kép (mũ+sắc, trăng+ngã…) lấy theo f18** (đọc bằng `tools/f18.py`).
    Độ dày nét dấu ăn theo độ dày nét Jeju (đo thân `l`/`i`).
  - "Từng cỡ" = bố cục riêng cho **chữ thường / chữ hoa / dấu kép** như f18 (chữ hoa dấu ép sát vì chỉ còn ~147 đơn vị trên cap).
  - Glyph mới = **composite** (component thân Jeju + component dấu) → giữ nguyên nét gốc, file nhỏ.
- D3a. (quản đốc, sau T4) Ngoại lệ D3: glyph `đ` (U+0111) có sẵn của Jeju lạc phong cách → thay bằng composite `d` Jeju + gạch vector; `Đ` = `D` Jeju + gạch (không dùng `Ð`); thân `i` có dấu = glyph mới dựng từ contour `i` Jeju bỏ chấm (không dùng `ı`). Không đổi glyph nào khác.
- D3. **Không đổi metrics dọc** (hhea/OS2 ascent/descent/lineGap, UPM) để không xô layout. Không đổi glyph/cmap có sẵn.
- D4. Chỉ thay `m_FontData` của Font path 33 (và font khác nếu T2 chứng minh English dùng font thiếu glyph). Không đổi path_id, tên, fallback.
- D5. (chốt sau T2) Text NFC (dựng sẵn), không dùng dấu tổ hợp. File giữ **BOM UTF-8 + CRLF** như gốc. Ô có dấu `,` → bọc cả ô bằng `"..."` (ngoặc cân bằng);
  **cấm** `"` lẻ và `\` trong bản dịch (parser xoá `\`); không xuống dòng thật — xuống dòng dùng ` @ `; giữ nguyên số lượng `^`, `` ` ``,
  các cặp ngoặc màu `[] 【】 〈〉 『』 《》 〔〕 {}` và `##id##`; không thêm cặp ngoặc màu mới (dùng `( )` thường thay cho ngoặc vuông).
  Không sửa dòng header (khoá Hàn), không dịch ô khoá Hàn.
- D6b. (người dùng, 2026-10-23) **KHÔNG sửa chuỗi trong code**: không đụng `global-metadata.dat` lẫn `libil2cpp.so`; các chuỗi cứng (Loading…, thông báo cập nhật, Please update) giữ tiếng Anh. Phần duyệt metadata ở D6 bị huỷ; chỉ còn sửa `m_Text` scene.
- D6. (chốt sau T2) Không sửa `libil2cpp.so`. **Duyệt** sửa các chuỗi cứng tiếng Anh hiển thị (Loading/Uninstalling, thông báo cập nhật…) trong
  `global-metadata.dat` string literal (T8) với điều kiện: chỉ đổi literal data/bảng literal, mọi phần khác byte-giống, có diff. **Duyệt** sửa `m_Text`
  của các UI.Text có chữ Latin trong scene (vô hại nếu bị ghi đè lúc chạy).
- D12. (người dùng, sau thử nghiệm f18px) Bản build THỬ dùng font **f18-B**: `tools/build_font_f18px.py --px 75` → `work/font_px/JejuHallasan-f18B.ttf`
  (toàn bộ 227 ký tự f18 — Latin, số, dấu câu, chữ Việt — là ô pixel vector, 1 ô = 75 đơn vị; chữ Hàn và ký tự ngoài f18 như `= _ ~` giữ Jeju).
  **Phương án dự phòng**: `work/font/JejuHallasan-VI.ttf` (vẽ tay, T5). T9/T10 phải nhận đường dẫn font làm tham số và build được cả 2 bản
  (`out/MagicSurvival_VI_f18.*` và `out/MagicSurvival_VI_jeju.*`) để người dùng thử, không tốt thì dùng bản jeju.
- D11. (sau T2) Mọi UI.Text: BestFit=0, cắt dọc (Truncate) → bản dịch phải đo độ rộng bằng Jeju-VI theo cỡ chữ; dài hơn EN quá 15% thì rút gọn.
- D7. Đóng gói: UnityPy save bundle (giữ kiểu nén gốc), thay entry trong base.apk **giữ nguyên cách lưu (stored/deflate) và căn lề** như gốc, xoá chữ ký cũ, `zipalign -p 4`, `apksigner` v2+v3, **cùng một khoá** cho base + split. Thêm APK gộp bằng APKEditor (REAndroid).
- D8. Cấu trúc repo:
  - `tools/` script Python/Shell (chạy `python3 -I`), `font/` nguồn glyph + ảnh preview, `text/eng/` gốc, `text/vi/` bản dịch, `docs/` báo cáo/mapping.
  - `work/` (game giải nén, bản build trung gian) và `out/` (APK) **gitignore** — quá lớn cho git.
- D9. Mỗi thay đổi nhị phân phải có diff: font (cmap/glyph thêm, không đổi glyph cũ), bundle (chỉ object thay đổi đúng như dự kiến), APK (danh sách entry khác nhau).
- D10. Dịch tay (Sonnet 5.5), không dịch máy; bảng thuật ngữ `text/glossary.tsv` bắt buộc dùng thống nhất.

## Ràng buộc và edge case
- Nguồn upload: `/root/.claude/uploads/9e39a733-fcc8-5d12-bac4-5922f80b6e8d/` (tên file có tiền tố hash; tên thật sau dấu `-`).
- Không đụng cột số, ID, khoá tiếng Hàn, thẻ `<color=…>`, `{n}`, `@ ^ 《 》 \``, khoảng trắng đuôi kiểu `0 ` (giữ nguyên byte).
- Chuỗi tiếng Việt dài hơn tiếng Anh → nút/ô chật: dùng từ ngắn gọn (xem D11).
- Dấu chữ hoa vượt ascent 800 → không bị clip trong UI Text nhưng có thể chạm dòng trên: giữ trong ≤ ~950 đơn vị.
- Game có thể kiểm tra chữ ký / Play Integrity: nếu ký lại làm game thoát thì ghi Vướng mắc.
- Ký tự thật dùng trong bản dịch phải ⊆ cmap font English thực dùng (script kiểm tra T9).

## Checklist
- [x] T1. Dựng môi trường + giải nén chuẩn
  - Agent: A1
  - Làm: `.gitignore` (`work/ out/ *.apk *.apks`); `tools/unpack.py` nối 7 phần → inflate → `work/game.apks` → `work/base.apk`, `work/split_config.arm64_v8a.apk`, giải `assets/` ra `work/ex/`. Cài `UnityPy fonttools freetype-py`; tải `apktool`/`APKEditor`, Android build-tools (`zipalign`, `apksigner`), Il2CppDumper (hoặc Cpp2IL) vào `work/tools/`. Ghi `tools/README.md` (lệnh tái lập).
  - Hoàn thành khi: chạy lại `tools/unpack.py` ra đúng CRC 63ccbe18; `zipalign`/`apksigner`/dumper chạy được `--help`.
- [x] T2. Phân tích IL2CPP + scene — **Quản đốc nghiệm thu**
  - Agent: A1
  - Làm: dump IL2CPP (dump.cs + script.json). Tìm trong `Z_Database` / code đổi ngôn ngữ: **English dùng font index nào**; cách đọc CSV (split `,`? xử lý `"`? `\n`?); ý nghĩa `@ ^ 《》 \``; Text có `resizeTextForBestFit`/overflow không. Kiểm kê chuỗi tiếng Anh cứng: m_Text trong scene (UI.Text) và string literal trong metadata xuất hiện trên UI. Đếm ảnh (Texture/Sprite) có chữ tiếng Anh (chỉ liệt kê tên).
  - File: `docs/analysis.md` (có bằng chứng: tên method/RVA, offset), `tools/dump_texts.py`.
  - Hoàn thành khi: `docs/analysis.md` trả lời đủ 5 câu trên kèm bằng chứng; ghi "đề xuất" cho D4/D5/D6 nếu cần đổi (không tự đổi).
- [x] T3. Đọc f18 + đo Jeju + tách dấu
  - Agent: A1 (phụ thuộc T1)
  - Làm: `tools/f18.py` (đọc .bin, in ASCII từng glyph, hàm lấy bitmap theo ký tự); `tools/marks_from_f18.py`: với mỗi chữ Việt, trừ bitmap thân chữ → bitmap dấu + vị trí (hàng/cột so với thân); xuất `docs/f18_marks.md` (ASCII từng loại dấu, bảng vị trí chữ thường/hoa/dấu kép). `tools/jeju_metrics.py`: stem width, x-height, cap, yMax a/e/o/u/y/A…, bbox `ı`, `Ð`.
  - Hoàn thành khi: `docs/f18_marks.md` đủ 8 loại dấu + mọi tổ hợp kép có trong f18; số liệu Jeju in ra file.
- [x] T4. Vẽ tay contour dấu theo nét f18 — **Quản đốc nghiệm thu** (chờ quản đốc nghiệm thu)
  - Agent: A1 (phụ thuộc T3)
  - Làm: `font/marks.py` khai báo contour vector (đơn vị font, quadratic) cho: acute, grave, hook, tilde, dotbelow, circumflex, breve, horn_o, horn_u, và biến thể hẹp/thấp cho chữ hoa + tổ hợp kép (vd circumflex_acute) đúng bố cục f18. Nét dày theo stem Jeju. `font/layout.py`: bảng ký tự → (thân, [dấu, dx, dy]).
  - Hoàn thành khi: `tools/preview_marks.py` xuất `font/preview/marks.png` (dấu f18 phóng to cạnh dấu vector) để quản đốc so.
- [x] T4b. Sửa sau nghiệm thu T4 — **Quản đốc nghiệm thu** (chờ quản đốc nghiệm thu)
  - Agent: A1
  - Làm: (1) glyph mới `idotless.jeju` = contour của `i` Jeju bỏ contour chấm (contour có yMin cao nhất), dùng làm thân `í ì ỉ ĩ ị Í…` thay `ı` (chữ hoa vẫn dùng `I`); (2) `đ` = `d` Jeju + gạch vector nét cọ đặt qua thân phải ở vùng ascender như f18; `Đ` = `D` Jeju + gạch ngang qua thân trái; (3) gọt dấu sắc/huyền thành nét cọ thuôn (đầu trên to bo tròn, đuôi dưới nhọn), móc hỏi bo đầu trái, móc ơ/ư nối thuôn vào thân (không đầu vuông), giữ kích thước/vị trí f18.
  - Hoàn thành khi: preview cập nhật (`glyphs_all.png`, `marks.png`) + thêm `font/preview/compare_i_d.png` (trước/sau cho i có dấu, đ, Đ).
- [x] T5. Build JejuHallasan-VI.ttf
  - Agent: A1 (phụ thuộc T4)
  - Làm: `tools/build_font.py`: thêm glyph dấu + 133 glyph composite + cmap (cả `Đ`→ composite từ `Ð`), hmtx (advance = advance thân chữ), giữ nguyên mọi glyph/metrics cũ; `fontTools` lưu → `work/font/JejuHallasan-VI.ttf`; kiểm tra bằng `ttx`/FreeType load OK. `tools/font_diff.py`: liệt kê glyph/cmap thêm, xác nhận glyph cũ byte-giống.
  - Hoàn thành khi: 134/134 chữ Việt có trong cmap; font_diff "0 glyph cũ thay đổi"; `font/preview/sample_{17,18,24,30}.png` render câu mẫu (FreeType, như Unity) + bản so Jeju gốc vs NotoDisplay.
- [x] T6. Bảng thuật ngữ + trích text
  - Agent: A1 (phụ thuộc T2)
  - Làm: `tools/text_extract.py`: từ `text/eng/*.txt` (bản gốc, chép vào repo) xuất `text/segments.tsv` (id ổn định `file:row:col:seg`, nguồn EN, đã dedupe theo chuỗi, đánh dấu thẻ/placeholder) — chỉ ô chứa chữ Latin, bỏ ô số/khoá Hàn. `text/glossary.tsv`: tên phép, thuộc tính, chỉ số, UI (≥150 mục, có cột ghi chú/độ dài).
  - Hoàn thành khi: round-trip (inject bản EN vào chính nó) ra byte-giống 6 file gốc; số segment duy nhất ghi vào Nhật ký.
- [x] T7. Dịch toàn bộ `text/segments.tsv` (1 công nhân, tuần tự từ đầu đến cuối) — 1904 key duy nhất, 61602 ký tự EN
  - Tiến độ: đã dịch no 1..1904 (xong)
  - Agent: A1. Đầu ra: `text/vi/vi.tsv` (cột `key`, `vi`; key = chuỗi EN y nguyên). Thứ tự dịch = cột `no` (Name, Explain, Class, Synergy, MagicCom, Ability; trong nhóm tên/UI trước, mô tả sau).
  - Quy tắc: dịch tay theo `text/glossary.tsv`; giữ ngoặc màu/`##id##`/số/%/`□`/`ㅁ`; chỉ dịch phần chữ (không có `@ ^` trong key); không `"` `\`.
  - Hoàn thành khi: `python3 -I tools/text_check.py --strict` không lỗi (cảnh báo độ rộng D11 thì rút gọn); `tools/text_inject.py` chạy ra 6 file.
- [x] T8. Chuỗi cứng scene (UI.Text m_Text; D6b: không đụng metadata/code)
  - Agent: A1
  - Hoàn thành khi: chuỗi dịch nằm trong `text/vi/hardcoded.tsv` và tool patch có diff.
- [x] T9. Patch bundle — **Quản đốc nghiệm thu** (chờ quản đốc nghiệm thu)
  - Agent: A1 (phụ thuộc T5, T7, T8)
  - Làm: `tools/patch_bundle.py`: thay `m_FontData` font 33 (+ font khác theo T2), thay 6 TextAsset eng_* bằng bản `text_inject.py`; save `work/build/data.unity3d`. `tools/bundle_diff.py`: liệt kê object đổi (phải đúng 1 Font + 6 TextAsset [+ T8]).
  - Hoàn thành khi: UnityPy mở lại bundle mới, đọc font/text đúng; bundle_diff khớp dự kiến.
- [x] T10. Đóng gói APK + ký — **Quản đốc nghiệm thu** (chờ quản đốc nghiệm thu)
  - Agent: A1 (phụ thuộc T9)
  - Làm: `tools/build_apk.sh` theo D7 → `out/MagicSurvival_VI.apks` (base+split ký cùng khoá) và `out/MagicSurvival_VI_merged.apk`. `apk_diff`: chỉ `data.unity3d` (+metadata nếu T8) và META-INF khác.
  - Hoàn thành khi: `apksigner verify --verbose` OK cả 2; `zipalign -c -p 4` OK; `aapt2 dump badging` OK.
- [x] T11. Kiểm thử cuối + báo cáo
  - Agent: A1
  - Làm: nếu có thể chạy Android emulator headless (x86_64 không chạy arm64 IL2CPP → nhiều khả năng không) thì thử; nếu không, ghi rõ "chưa test thiết bị". `docs/REPORT.md`: thay đổi, cách cài (SAI / APK gộp), lưu ý ký lại.
  - Hoàn thành khi: REPORT.md đủ; commit + push nhánh `ccr-1124ae01-q5nel0` (không push APK/work).

## Vướng mắc (công nhân ghi vào đây, chờ quản đốc/người dùng quyết)
- (trống)

## Nhật ký
- 2026-10-23 quản đốc: kiểm kê xong, lập PLAN.md. Người dùng chốt: glyph vẽ tay theo nét f18; thay English; font + dịch toàn bộ.
- 2026-10-23 A1: T1 xong. unpack.py ra CRC 63ccbe18 (3.9s); tools: dotnet 8 runtime (+ROLL_FORWARD=Major), Il2CppDumper 6.7.46, build-tools r35, APKEditor 1.4.9, apktool 2.12.0 đều chạy. GitHub API bị chặn, chỉ tải releases/download trực tiếp.
- 2026-10-23 A1: T2 xong, docs/analysis.md. English=Jeju (Awake 0x114B208); CSV = Regex split (dấu phẩy trong "..."), xoá mọi `\`; ` @ `=xuống dòng, `^`=ngăn cấp trong 효과 설명줄06, `[]{}〈〉《》`... = màu; Text 654/654 Wrap+Truncate, không best-fit; ảnh có chữ chỉ logo; vài chuỗi cứng EN trong metadata. File thật CRLF+BOM (PLAN ghi LF).
- 2026-10-23 quản đốc: nghiệm thu T2 đạt (English = Font 33 Jeju, bằng chứng Z_Database$$Awake 0x114B208). Chốt D5, D6, thêm D11.
- 2026-10-23 A1: T3 xong. tools/f18.py, tools/marks_from_f18.py -> docs/f18_marks.md/.json (132 chữ tách dấu OK, đ/Đ có thân riêng); tools/jeju_metrics.py -> docs/jeju_metrics.txt/.json. Jeju: x-height 500 (a cao 556), cap 627-688, stem ~100-148, ı bbox -52..457 (hình chữ nhật hình học, KHÔNG cùng phong cách Jeju), Ð cũng nét đơn hình học.
- 2026-10-23 A1: T4 xong (chờ quản đốc nghiệm thu). font/marks.py (8 dấu x 3 cỡ R/C/S, vẽ tay), font/layout.py (134 chữ), tools/preview_marks.py -> font/preview/{marks,marks_context,glyphs_all}.png. Dấu hoa kép max 949, nặng min -198. Lưu ý: ı và Ð lệch phong cách Jeju (i có dấu, Đ hiện rõ) — đề xuất vẽ Đ = D Jeju + gạch vector, i = thân l cắt; chờ quyết.
- 2026-10-23 quản đốc: nghiệm thu T4: đạt bố cục; yêu cầu sửa T4b (thân i, đ/Đ, gọt nét dấu). Thêm D3a.
- 2026-10-23 A1: T4b xong (chờ quản đốc nghiệm thu). font/extra.py (idotless.jeju từ contour thân i Jeju bỏ chấm; gạch đ/Đ nét cọ theo f18), marks.py: sắc/huyền = nét cọ thuôn (đầu tròn, đuôi nhọn), hỏi bo tròn đầu trái, móc ơ/ư thuôn đầu tròn; ảnh font/preview/{glyphs_all,marks,compare_i_d}.png (tools/compare_i_d.py).
- 2026-10-23 A1: T5 xong. tools/build_font.py -> work/font/JejuHallasan-VI.ttf (6660012 -> 6667056 B, +158 glyph: 1 thân i, 22 dấu/gạch, 135 composite; cmap +133, đổi U+0111). font_diff: 0 glyph cũ đổi byte, 0 hmtx cũ đổi, kern/cvt/fpgm/prep/gasp/name/post/OS2 byte-giống; verify_font.py: 134/134, FreeType 17/18/24/30 (hinted+nohint) OK, ots PASS; ảnh font/preview/sample_{17,18,24,30}.png (tools/make_samples.py). post giữ format 3.0 (không lưu tên glyph); OS/2 unicode range không đổi.
- 2026-10-23 quản đốc: nghiệm thu T4b + T5 đạt (xem compare_i_d.png, sample_24.png). Bước tiếp: T6.
- 2026-10-23 A1: T6 xong. text/eng/ (6 file byte-giống gốc), tools/text_{common,extract,inject,check}.py, text/segments.tsv = 1904 key duy nhất (2304 lần xuất hiện, 61602 ký tự EN: Name 259, Explain 101, Class 195, Synergy 145, MagicCom 376, Ability 828), text/glossary.tsv 255 mục. Round-trip inject(vi=en) = 6 file byte-giống. Bỏ qua: hàng ID 0 (marker Name/Type/Explain), hàng tiêu đề giữa file (ID,...), cột 특성ID ("a"). Chưa chắc hiển thị: Explain "일지 조건 이름" và "구역 효과" (đã gắn `?` ở note). T7 gộp thành 1 task theo yêu cầu người dùng.
- 2026-10-23 quản đốc: người dùng chọn thử font f18-B (D12); Jeju-VI làm dự phòng.
- 2026-10-23 A1: T7 xong. text/vi/vi.tsv 1904/1904 key (2 cột key,vi); text_check --strict 0 lỗi, 263 cảnh báo độ rộng D11 (chủ yếu từ ngắn bắt buộc theo glossary: Trường phái, Cửa hàng, tên Class/Spell/Artifact); text_inject ra 6 file ở work/text_vi. Sửa tools/text_check.py: thuật ngữ viết HOA (ON/OFF) khớp phân biệt hoa thường; ngoặc không cân bằng chỉ báo lỗi khi key cân bằng (key 《...》 bị tách qua " @ "). Glossary: thêm Mirage, Crater, Elite Monster, Critical Strike Multiplier (T7) + phương án phụ cho Active/Center/Left/Count/Give Up.
- 2026-10-23 quản đốc: nghiệm thu T7 (người dùng đã duyệt bản dịch). 264 cảnh báo độ rộng chấp nhận, kiểm trên máy thật ở T11. A1 kết thúc; A2 được phép implement T8–T10 sau khi nộp bản đọc trước.
- 2026-10-23 quản đốc: người dùng bỏ chuỗi trong code (D6b). T8 chỉ còn m_Text scene.
- 2026-10-23 A2: T8 xong. text/vi/scene.tsv (level,path_id,en,vi) = 18 UI.Text: ERROR->LỖI (5), ERROR 255, error code (2), Point->Điểm (2), ON->BẬT (2), SAVE->LƯU, LOAD->TẢI, Master->Bậc thầy (3), No Ads->Không QC. Bỏ `(Max)` (VI 133–155% EN ở cỡ 16, giữ EN). Còn vượt 15%: ON/BẬT 138–150%, Master/Bậc thầy 129%, No Ads/Không QC 135–139% (quản đốc cho phép; theo glossary; nhãn ngắn, có thể bị code ghi đè). Chuỗi Hàn tĩnh giữ nguyên (không T8b).
- 2026-10-23 A2: T9 xong. tools/patch_bundle.py (--font --out; font tham số) + tools/bundle_diff.py. Lưu packer='original': mở lại cờ 0x243, khối 0x2 như gốc (5621 khối 128KB; gốc 98 khối stored, mới 1; kích thước 135 MB vs 169 MB do nén tốt hơn). bundle_diff (bản jeju): đúng 25 object đổi = 1 Font(33) + 6 TextAsset + 18 UI.Text, tập object/externals/cờ bằng nhau. patch_bundle ~1 phút. build_font_f18px.py: mặc định --px 75, thêm --variants. text_common.load_vi bỏ qua scene.tsv.
- 2026-10-23 A2: T10 xong. tools/build_apk.{sh,py}, tools/apk_diff.py; khoá thử nghiệm work/keys/ms-vi.jks (mk trong tools/README.md). Ra out/: MagicSurvival_VI_f18.apks 179.198.831 B, _f18_merged.apk 169.236.965 B; MagicSurvival_VI_jeju.apks 179.190.639 B, _jeju_merged.apk 169.228.773 B. Cả 4 + base/split: apksigner verify OK (v2+v3, cùng cert SHA-256 410bf8a0...), zipalign -c -p 4 OK, aapt2 badging com.vkslrzm.Zombie 612/0.9941 (split=config.arm64_v8a). apk_diff: base chỉ khác data.unity3d (+ bỏ stamp-cert-sha256), split chỉ bỏ chữ ký v1/stamp, merged so với APKEditor-merge bản gốc chỉ data.unity3d + chữ ký; bundle_diff trích từ APK gộp: đúng 1 Font + 6 TextAsset + 18 UI.Text. APKEditor merge chạy được (extractNativeLibs=true do manifest; zipalign in 'header mismatch' vô hại). Chưa test trên thiết bị (T11). Lưu ý: khác khoá ký gốc -> gỡ bản gốc trước khi cài; chữ ký v1 của split bỏ, chỉ v2+v3 (minSdk base 24).
- 2026-10-23 quản đốc: nghiệm thu T8–T10 (tự kiểm: f18 dùng glyph pixel, jeju nét gốc, text tiếng Việt trong bundle). T11: docs/REPORT.md; chưa test thiết bị — chờ người dùng cài thử.
- 2026-10-23 quản đốc: mở GĐ2 (mod offline) theo yêu cầu người dùng; người dùng chọn bản f18.
- 2026-10-23 A1: T12 xong (chờ quản đốc nghiệm thu), docs/mod_analysis.md. QC: chỉ AdMob+UMP, cổng IsGDPR_ON (W_GDPR_Manager.Start 0x113FFCC ret) + rewarded duy nhất = hồi sinh (ShowRewardedAd 0x113F3BC -> Unit_RevivalON 0x1045070; cần bỏ 2 cổng internetReachability 0x1044C98/0x108B934); GPGS: callback đăng nhập chỉ log, chặn AuthenticateUser 0x114C180 + đuôi LoadLocalData 0x1171434 + 7 nút cloud; IAP là IAPButton Codeless (ret 7 hàm BaseIAPButton/CodelessIAPStoreListener, KHÔNG bật IAP_DestroyAD vì nó kích hoạt hồi sinh tự động); không có kiểm tra bản quyền/chữ ký; HP = static C_Battle_AllyUnit +0x38, hook Unit_Damage 0x1048858/Unit_Death 0x10449F0; EXP = "Mana" static Z_Database +0x9E4/+0x9E8, lên cấp = V_Ability_Manager.ManaGain 0x10FC64C, Update hook C_Battle_Manager 0x109CBF8; launcher UnityPlayerActivity (classes2.dex), minSdk 24/target 36, 2 dex, arm64 only; NDK r27c, Dobby (git clone được), platform-36 android.jar.

---
# GIAI ĐOẠN 2: Mod chơi offline (người dùng yêu cầu 2026-10-23)

## Mục tiêu GĐ2
Bản **f18** (D12) + bỏ quảng cáo + gỡ phụ thuộc Google Play (đăng nhập GPGS, cloud save, IAP) để chơi một mình offline
+ menu mod trong game: **Bất tử (god mode)** và **Lên cấp ngay** trong trận.

## Quyết định thiết kế GĐ2 (KHÔNG tự đổi)
- M1. Chỉ build biến thể font **f18**. Mọi bước GĐ1 giữ nguyên (bundle T9 dùng lại).
- M2. Sửa code = **vá byte trong `libil2cpp.so` arm64** theo RVA từ dump (Il2CppDumper, T2). Mỗi vá ghi trong `mods/patches.json`
  (`rva`, `orig` hex, `new` hex, `method`, `lý do`); `tools/patch_so.py` **kiểm byte gốc trước khi vá**, lệch là dừng. Không đổi kích thước file, không đổi bảng symbol/section.
  `global-metadata.dat` vẫn không đụng (D6b).
- M3. Quảng cáo: không khởi tạo SDK quảng cáo (AdMob/GDPR consent), không hiện banner/interstitial; quảng cáo có thưởng (hồi sinh, nhân thưởng…)
  → **trao thưởng ngay** bằng cách gọi đúng hàm callback thưởng của game, không gọi SDK.
- M4. Google Play: bỏ đăng nhập GPGS (game chạy như chưa đăng nhập, không văng/treo), nút cloud save không làm gì; **IAP: không khởi tạo
  Unity Purchasing, gói nạp trong cửa hàng vô hiệu** (KHÔNG mở khoá gói trả phí miễn phí). Gói "No Ads" coi như đã có (vì M3).
  Có kiểm tra bản quyền/Play Integrity/chữ ký thì vô hiệu để chạy được khi ký lại. Manifest giữ nguyên trừ khi bắt buộc (ghi lý do).
- M5. Menu mod: **nút nổi trong Activity** (Java, thêm dex mới, gắn vào DecorView của UnityPlayerActivity — không cần quyền overlay),
  bảng tiếng Việt: công tắc `Bất tử`, nút `Lên 1 cấp`, `Lên 5 cấp`. Java gọi JNI vào `libmsmod.so` (C, NDK, arm64).
  `libmsmod.so` dùng **Dobby** (inline hook) để hook hàm nhận sát thương của nhân vật (bật bất tử → bỏ qua) và gọi hàm lên cấp/cộng EXP
  của game trên đúng luồng Unity (đặt cờ, thực thi trong hook Update của battle manager — không gọi Unity API từ luồng UI Android).
  Nạp lib: smali `System.loadLibrary("msmod")` trong `onCreate` của Activity launcher (apktool `-r`, không decode resource).
- M6. Lên cấp = **cấp trong trận** (nhận EXP đủ lên cấp → game tự hiện bảng chọn kỹ năng như bình thường). Ngoài trận nút bị mờ.
- M7. Đầu ra: `out/MagicSurvival_VI_f18_mod.apks` + `out/MagicSurvival_VI_f18_mod_merged.apk`, cùng khoá `work/keys/ms-vi.jks`.
  `apk_diff` chỉ được khác: `data.unity3d`, `libil2cpp.so`, `classes*.dex`/smali Activity, `lib/arm64-v8a/libmsmod.so`, chữ ký.

## Checklist GĐ2
- [x] T12. Phân tích mod — **Quản đốc nghiệm thu** (chờ quản đốc nghiệm thu)
  - Agent: A1
  - Làm: từ dump.cs + disasm (capstone) tìm, có bằng chứng RVA: (a) mọi điểm khởi tạo/hiện quảng cáo + callback thưởng (hồi sinh, x2 thưởng, khác);
    (b) luồng GPGS login/cloud save + chuyện gì xảy ra khi không đăng nhập; (c) IAP init + nút shop + kiểm tra "No Ads"; (d) kiểm tra bản quyền/chữ ký/integrity nếu có;
    (e) hàm nhân vật nhận sát thương/chết, nơi lưu HP; (f) EXP/cấp trong trận, hàm lên cấp, instance tĩnh của battle manager/player, hàm Update để gắn hook;
    (g) Activity launcher (AndroidManifest), có sẵn libDobby/NDK cần tải gì.
  - File: `docs/mod_analysis.md`. Không vá gì.
  - Hoàn thành khi: mỗi mục có RVA + đoạn lệnh/IL làm bằng chứng và đề xuất vá cụ thể; chỗ chưa chắc ghi CHƯA XÁC ĐỊNH.
- [ ] T13. Khung vá + vá quảng cáo/Play (M2–M4) — **Quản đốc nghiệm thu**
  - Agent: A1 (phụ thuộc T12)
  - File: `tools/patch_so.py`, `mods/patches.json`, `tools/disasm_patch.py` (in trước/sau từng điểm vá).
  - Hoàn thành khi: vá áp được lên libil2cpp gốc, kiểm lại byte gốc OK, disasm trước/sau hợp lệ (nhánh/ret/ABI đúng, không vỡ stack frame).
- [ ] T14. Toolchain + `libmsmod.so` + menu Java (M5, M6)
  - Agent: A1 (phụ thuộc T12)
  - Làm: tải NDK, Dobby (build arm64 static), android.jar, d8; `mods/native/` (C + CMake/ndk-build), `mods/java/` (menu), script build ra `work/mod/libmsmod.so`, `work/mod/classes_mod.dex`.
  - Hoàn thành khi: build sạch; `readelf` lib arm64, chỉ phụ thuộc libc/liblog/libdl/libandroid; dex qua `dexdump`.
- [ ] T15. Ráp APK mod + ký + kiểm (M7) — **Quản đốc nghiệm thu**
  - Agent: A1 (phụ thuộc T13, T14)
  - Hoàn thành khi: apksigner/zipalign/aapt2 OK; apk_diff đúng M7; nếu có `/dev/kvm` + emulator arm64-translation thì chạy thử, không thì ghi rõ chưa test.
- [ ] T16. Cập nhật `docs/REPORT.md` (phần mod), commit + push.

## Vướng mắc GĐ2
- (trống)
