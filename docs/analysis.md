# T2 - Phân tích IL2CPP + scene (Magic Survival 0.9941)

Công cụ: Il2CppDumper 6.7.46 (metadata v31, chạy được; cảnh báo "may be protected" vô hại), `tools/il2dis.py`
(capstone arm64 + chú thích bl/chuỗi theo script.json + relocation R_AARCH64_RELATIVE), `tools/scan_code.py`,
`tools/ttgen.py` + `tools/dump_texts.py` (typetree sinh từ libil2cpp+metadata). RVA dưới đây là RVA trong dump.cs/libil2cpp.so (arm64).
Tái lập: giải `lib/arm64-v8a/libil2cpp.so` (split) và `assets/bin/Data/Managed/Metadata/global-metadata.dat` (base) vào `work/il2/`, chạy
`il2cppdumper libil2cpp.so global-metadata.dat out/` rồi `python3 -I tools/il2dis.py <so> out/script.json <RVA>`.

## Mã ngôn ngữ (static `Z_Database.Game_SystemLanguage`, offset 0x8BC)
1 Korea, **2 English**, 3 Japanese, 4 ChineseSimplified, 5 ChineseTraditional, 6 Spanish, 7 German, 8 Portuguese, 9 Russian
(bằng chứng: `A_MainMenuInformation$$Button_Language_English` RVA 0x10242E8: `str w9,[x8,#0x8bc]` với w9=2;
`Z_Database$$Awake` RVA 0x114AB10: SystemLanguage 23(Korean)->1, 10(English)->2, 22(Japanese)->3, 6/40(Chinese)->4 ...).

## Câu 1. English dùng font nào
**JejuHallasan** (Font path 33). `Z_Database$$Awake` 0x114AB10, nhánh `cmp w20,#2` (0x114B208):
`ldr x20,[x19,#0x28]` (= `PFont_JejuHallasan`) rồi `stp x10,x20,[x9]` => `Font_Apply_Bold = Font_Apply_Medium = PFont_JejuHallasan`;
sau đó lặp `Array_TextLanguage_All` (field 0xA0) gọi `Text.set_font(Font_Apply_Medium)` (0x114B28C).
Korean (1) cũng dùng Jeju. Các ngôn ngữ khác: 3 JP: Regular=PFont_JP_Regular(0x48), Medium=JP_Medium(0x40); 4,5 SC: 0x58/0x50;
6,7,8,9 (es/de/pt/ru): Medium=PFont_NotoSansEng_Medium(0x38)=path32 NotoSansDisplay-Medium, Bold=0x30=path35.
Z_Database (level1 path 7628) serialized: PFont_JejuHallasan=33, NotoSansEng_Bold=35, NotoSansEng_Medium=32, JP_Medium=30, JP_Regular=36, SC_Medium=31, SC_Regular=34 (đều fileID 3 = sharedassets0.assets).
Ngoài `Array_TextLanguage_*` còn 9 method gọi `Text.set_font` (A_MainMenuInformation.MainMenuInformation, C_Battle_Manager.Start,
C_Battle_AllyUnit.Display_AbilityDetail, X_Select_Ability(Show).OnEnable, C_Battle_TextEffect.Start_TextEffect, Z_GPGSManager.MainMenu_Inf_Start ...)
- đều lấy từ `Font_Apply_*`. Trong scene: 639/654 UI.Text gắn Jeju, 9 NotoDisplay-M (các nhãn tên ngôn ngữ Deutsch/Português/Español), 4 SC-M, 2 JP-M.
=> **Chỉ cần vá Font 33 cho English** (D4 đúng). Không cần đụng font Noto.
Lưu ý: ở English `Font_Apply_Regular` (static +0x10) không được gán (null) - không ảnh hưởng vì không thấy gán cho Text.

## Câu 2. Cách đọc CSV (`Z_CSVReader.Read(string file)` RVA 0x114A5B0; .cctor 0x114AA3C)
Là bản "CSVReader" kinh điển của Unity wiki:
- `Resources.Load(file) as TextAsset` -> `TextAsset.text`.
- `Regex.Split(text, @"\r\n|\n\r|\n|\r")` (LINE_SPLIT_RE) -> tách dòng **trước** khi tách ô => **ô không được chứa xuống dòng**.
- Dòng 0 = header, tách bằng `SPLIT_RE = ,(?=(?:[^"]*"[^"]*")*(?![^"]*"))` (dấu phẩy chỉ là phân cách khi số dấu `"` còn lại phía sau là **chẵn**).
  => dấu phẩy trong ô phải nằm trong cặp `"..."` cân bằng; ô có dấu `"` lẻ làm hỏng cả dòng. (Dữ liệu EN gốc có sẵn ô dạng `"The remains..., ..."`.)
- Mỗi dòng i>=1: tách cùng regex; **bỏ dòng nếu 0 phần tử hoặc ô đầu == ""**. Với j < min(header.Length, values.Length):
  `value = values[j].TrimStart('"').TrimEnd('"').Replace("\\","")` (xoá **mọi** dấu `\`; trim mọi `"` đầu/cuối), rồi `int.TryParse` -> boxed int, nếu không `float.TryParse` -> boxed float, nếu không giữ string. `dict[header[j]] = value`.
  (0x114A8B8 TrimStart, 0x114A8D0 TrimEnd, 0x114A8F4 Replace, 0x114A904 Int32.TryParse, 0x114A930 Single.TryParse, 0x114A984 set_Item.)
- Không unescape `""`; `\n` văn bản KHÔNG thành xuống dòng (bị xoá `\`). Xuống dòng dùng ký hiệu ` @ ` (câu 3).
- Khoá dict = header **tiếng Hàn** (`이름`, `유형`, `효과 설명줄06`, `특수값01`, ...) được hard-code trong mã (Z_CSVReader chỉ trả List<Dictionary<string,object>>) => **không đổi dòng header**, không đổi thứ tự cột.
- Hệ quả D5: dấu `,` và `"` trong bản dịch dùng được nếu bọc ô bằng `"..."` và không có `"` lẻ bên trong; an toàn nhất: tránh `"` trong nội dung, và nếu có `,` thì bọc `"`. Tránh `\`. Không xuống dòng thật trong ô. Ô chỉ gồm chữ số (vd `10`, `1.5`) bị chuyển thành số (không thành vấn đề cho văn bản Việt).
- Dữ liệu thật: 6 file eng_* đều có **BOM UTF-8 + CRLF** (không phải LF như PLAN ghi), kết thúc bằng CRLF; giữ nguyên byte. Ô `` ` `` = ô rỗng giả (xem câu 3), ô `0 ` giữ nguyên khoảng trắng đuôi.
- Chọn file theo ngôn ngữ ở `Z_Database$$Start_DataBase` RVA 0x11508C0 (nhánh `cmp w19,#2` 0x1156078): English đọc `eng_Dictionary_{Ability,Name,Explain,Synergy,MagicCom,Class}` (cùng 3 sprite `SpriteData/Sprite_UI/Revive{Cancel,Select,Explain}_Text_eng` - xem câu 5). Dictionary_AllStat/UnitStat không theo ngôn ngữ.

## Câu 3. Ý nghĩa `@ ^ 《》 ` ` (và các ký hiệu khác)
`X_TextColor$$TextColor` RVA 0x114764C (còn TextColor_AllGrey 0x1149428, _Light 0x114984C: cùng chuỗi nhưng màu xám), áp lên `Text` trước khi hiển thị. Thứ tự `String.Replace`:
| Ký hiệu | Xử lý |
|---|---|
| ` @ ` (dấu cách-@-dấu cách, 3 ký tự) | -> `\n` (xuống dòng). Mọi `@` trong 6 file EN đều ở dạng ` @ ` (trừ 1 ô ở Name: `@` sát chữ - kiểm tra khi dịch) |
| `` ` `` | -> `""` (xoá). Ô chỉ có `` ` `` = chỗ trống placeholder (CSVReader bỏ dòng nếu ô đầu rỗng nên cần ký tự) |
| `【` `】` | `<color=#FF6464>` ... `</color>` (đỏ) |
| `[` `]` | `<color=#FFEB9B>` ... `</color>` (vàng) — **dấu ngoặc vuông bị mất, thay bằng màu** |
| `〈` `〉` | `<color=#B9FF32>` (xanh lá) |
| `『` `』` | `<color=#EB96FF>` (tím) |
| `《` `》` | `<color=#6EDCFF>` (xanh dương) |
| `〔` `〕` | `<color=#32FFE1>` (cyan) |
| `{` `}` | `<color=#FF76DE>` (hồng) |
| `##(\d+)##` (Regex) | -> `Dictionary_Name[id]["이름"]` tức tên mục id (thay thế chuỗi bằng tên đã dịch) |
| `^` | **ngăn cách mô tả theo cấp**: `X_Select_Ability$$OnEnable` RVA 0x11419FC, lệnh 0x1142788 `mov w1,#0x5e` + `String.Split`: lấy `Dictionary_Ability[row]["효과 설명줄06"].Split('^')[level-2]` rồi Replace(" @ ","\n"). **Số lượng `^` phải giữ nguyên** (thiếu -> IndexOutOfRange 0x1142E20). |
Chữ `<color=#...>` thật trong ô vẫn dùng được (rich text bật). Ô tiếng Hàn như `마법`, `패시브`, `학파`, `인챈트`, `궁극기`, `조합` là **khoá so sánh** (`String.op_Equality`, vd 0x1142728 so `유형` với `마법`) - không dịch.
Các ký tự `{0}` không thấy trong mã quét (không có String.Format theo ô): coi là văn bản, giữ nguyên nếu có.

## Câu 4. Best-fit / overflow
Mã không gọi `set_resizeTextForBestFit` / overflow (0 lần trong Assembly-CSharp). Trong scene (`tools/dump_texts.py` -> `work/an/fonts.tsv`):
**654/654 UI.Text: m_BestFit=0, m_HorizontalOverflow=0 (Wrap), m_VerticalOverflow=0 (Truncate)**. Nghĩa là: tự xuống dòng theo chiều ngang,
**chữ vượt chiều cao ô bị cắt (không vẽ)**, không co chữ. Mã đổi `fontSize` (218 chỗ gọi set_fontSize) và `lineSpacing` (149) theo ngôn ngữ/ngữ cảnh,
vd Awake: English loading text size 0x1C (28), dòng cảnh báo 0x15 (21). => Bản dịch Việt dài hơn EN có thể bị cắt ở ô nhiều dòng; cần từ ngắn hoặc (đề xuất) tăng chiều cao/giảm cỡ ở các ô trọng yếu (T8, quản đốc duyệt).
Font Jeju là dynamic font (Unity rasterise theo cỡ), không cần atlas riêng.

## Câu 5. Chuỗi cứng + ảnh có chữ
**Chuỗi tiếng Anh cứng trong code** (string literal -> `global-metadata.dat`, D6 cần duyệt), `scan.json`/`tools/scan_code.py`:
- `Z_Database$$Awake`: `Loading resources...`, `Now Loading ...`, `Uninstalling the game will also remove your data.\nUse the Google Cloud Save/Load function in the Options screen.`, `Deleting the game will also remove your data.\nPlease use the google cloud save and open option.` (hai chuỗi cuối: một bản dùng cho EN; xác định bản nào cần xem 0x114B3E8 - lệnh dùng `Uninstalling...` ở nhánh English).
- `Z_GPGSManager$$MainMenu_Inf_Start` (thông báo cập nhật, theo ngôn ngữ): `New Ultimate Added`, `〈Research currency has been reworked, and Gold will be granted.〉`, `Save data has been changed in this update,\nso autosave data is gone.\n〈`, `〉 obtained from Autosave.`, `〉 Gold obtained from auto-save is additionally paid.`, `\n\nYou will receive  {`, ` Gems}  as a reward for the first Areas cleared.`, (phiên bản `v 0.855/0.859/0.993`; ghi chú cập nhật tiếng Hàn `{신규} [아티팩트]...` chỉ có bản Hàn).
- `Z_GPGSManager$$LoadLocalData`: `Please update\n` (+ `Update`?); các chuỗi `!%&)#...` là khoá mã hoá, KHÔNG dịch.
- Cloud: `Show_CloudResult` chỉ có literal tiếng Hàn (`\n저장 성공` ...) - có thể là khoá/ngôn ngữ Hàn; **CHƯA XÁC ĐỊNH** có hiển thị ở English hay không (không thấy bản EN trong literal; bản EN có thể lấy từ Dictionary_Name cột 구글 클라우드).
- Còn lại (AdMob/GDPR/GPGS log tiếng Anh/Hàn, tên Animation/Sprite/Sound) là log/khoá, không lên UI.
Phần lớn UI chữ lấy từ `eng_Dictionary_Name` (bảng cột theo nhóm UI: `메인 메뉴`, `자동 저장`, `연구`, `상점`, ...; xem ID001 header) -> đã nằm trong 6 file cần dịch.
**Scene (UI.Text m_Text)**: 654 Text: 434 có tiếng Hàn (placeholder, bị ghi đè lúc chạy), 168 số/ký hiệu, 20 rỗng, **58 có chữ Latin**, trong đó cứng-tiếng-Anh dùng được trên UI:
`ON`/`OFF`?, `SAVE`, `LOAD`, `Master`, `(Max)`, `No Ads`, `Lv 1/Lv 2`, `Point`, `English` (nhãn tên ngôn ngữ, không dịch), `ERROR`, `error code : tp801105`, `Text_Version v 0.9941`. Danh sách đầy đủ: `work/an/texts.tsv` (do `tools/dump_texts.py` tạo, không commit). Các nhãn này có thể bị ghi đè lúc chạy bởi Dictionary_Name (vd `ON`,`OFF`,`SAVE`,`LOAD`,`Master`,`No` có trong eng_Dictionary_Name) -> **cần quản đốc quyết (T8)** có sửa m_Text scene hay không; mặc định chỉ dịch qua CSV.
**Ảnh có chữ Latin**: đã xem 16 ảnh nghi ngờ trong 5628 Texture2D/Sprite: chỉ **TitleText** (logo "Magic Survival", 290x210), **GameLogo** (432x528, chữ "LEME"), **LoadingLogo** (256x128, "LEME") có chữ - đều là logo/tên hãng, không cần Việt hoá. Shop_IAP_*, Sheet_*, Update092, LoadingImg*, Icon*, Journal/Portrait không có chữ. 3 sprite theo ngôn ngữ mà mã yêu cầu (`SpriteData/Sprite_UI/ReviveSelect_Text_eng`, `ReviveCancel_Text_eng`, `ReviveExplain_eng`; Start_DataBase 0x11560DC-0x11560E4) **không tồn tại** trong ResourceManager container (5733 mục, chỉ có `revive*_image`) -> mã tải ra null; không có ảnh chữ cần vẽ lại. Chưa duyệt từng sprite trong 14 atlas UI (`sactx-*-SpriteAtlas_UI`): **CHƯA XÁC ĐỊNH** (nhãn UI là Text, không phải ảnh, theo cấu trúc scene).

## Đề xuất (không tự đổi quyết định)
- D4: giữ nguyên - chỉ vá Font 33. Xác nhận English = Jeju.
- D5: dùng được `,` nếu bọc `"..."` cân bằng; nên tránh `"` và `\`; không xuống dòng trong ô; xuống dòng bằng ` @ ` (có dấu cách hai bên); giữ số lượng `^`; `[ ]` `{ }` `〈 〉`... là lệnh màu (dấu ngoặc biến mất khi hiển thị) - muốn hiện dấu ngoặc phải viết khác.
- D6/T8: nên cho phép sửa 4-6 chuỗi cứng trong metadata (Loading/Uninstalling/ghi chú cập nhật) - chuỗi Việt phải **không dài hơn** chuỗi gốc theo byte UTF-8 nếu vá tại chỗ, hoặc cần dựng lại bảng string literal; quản đốc duyệt.
- Vì Vertical Overflow = Truncate cho mọi Text: cần kiểm tra độ dài bản dịch so với EN (đề xuất script đo chiều rộng bằng FreeJ2ME-like: render Jeju-VI theo cỡ trong `texts.tsv`) ở T6/T7.
- PLAN ghi "LF" nhưng file thật là CRLF+BOM; text_extract/inject phải giữ CRLF + BOM.
