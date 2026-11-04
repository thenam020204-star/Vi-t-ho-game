#!/bin/bash
# Biên dịch + link thư viện menu mới thành đoạn PT_LOAD tại 0x171000 (T17c0).
# Dùng: build.sh [OUTDIR] [--debug]      WORK mặc định ~/hl5work; SO = $WORK/apk/assets/libHeroesLore5.so (.so gốc của APK: chỉ để tra địa chỉ hàm guest)
# Ra: OUT/menu.bin (đoạn file-backed: .text+.rodata+.data), OUT/menu.sym, OUT/menu.elf, OUT/menu.info (base, filesz, memsz, lớp đã bật).
# --debug: thêm -DMUI_DEBUG (trang widget thử cho harness; KHÔNG dùng cho bản phát hành).
set -e
cd "$(dirname "$0")"
OUT=out; DBG=0
for a in "$@"; do case "$a" in --debug) DBG=1;; *) OUT="$a";; esac; done
mkdir -p "$OUT"
: "${WORK:=$HOME/hl5work}"
SO="${SO:-$WORK/apk/assets/libHeroesLore5.so}"
BASE=0x171000
CC="clang-18 --target=armv7a-none-eabi -marm -march=armv7-a -mfloat-abi=soft -Os -ffreestanding -fPIC -fno-jump-tables \
  -fno-builtin -fno-stack-protector -fno-unwind-tables -fno-asynchronous-unwind-tables -fvisibility=hidden \
  -nostdlib -Wall -Wno-unused-function"
DEF=""
if [ "$DBG" = 1 ]; then
  DEF="-DMUI_DEBUG -I$OUT"
  python3 -I gen_dbg_text.py "$OUT/dbg_text.h" >/dev/null
fi
OBJS=""
MMK=$(python3 -I -c "import sys; sys.path.insert(0,'../../patch'); import hd_cfg; print(hd_cfg.DEFAULT_K)")   # L3: k mặc định (hd_cfg.py)
for f in rt gfx text layout widgets touch dispatch ingame popup bag combo dbg; do
  [ -f $f.c ] || continue
  $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"
done
# T17c3b: property.c
for f in property; do [ -f $f.c ] || continue; $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; done
for f in skill; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # T17c3c: Kỹ năng
for f in cash; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T17c6: Cash shop
for f in t7; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T7: logic offline
for f in spirit; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T17c4: Tinh linh
for f in gpop; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T17c6: popup StateGame
for f in quest; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T17c4: Nhiệm vụ
for f in map; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1b/T17c8: StateMap
for f in expert; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L1/T17c4: Chuyên gia
for f in mmenu; do [ -f $f.c ] && { $CC $DEF -DMM_DEFAULT_K=$MMK -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L3/T17c7: MainMenu
for f in hud; do [ -f $f.c ] && { python3 -I gen_hud_art.py "$OUT/hud_art.h"; python3 -I gen_hud_text.py "$OUT/hud_text.h"; $CC $DEF -I"$OUT" -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # T14: HUD chiến đấu
for f in npc; do [ -f $f.c ] && { $CC $DEF -c $f.c -o "$OUT/$f.o"; OBJS="$OBJS $OUT/$f.o"; }; done   # L2/T17c5: NPC
# T16: khung hội thoại mới (dialog.c; dlg_text.h sinh từ từ điển $WORK/vi_android/dict.txt)
if [ -f dialog.c ]; then python3 -I gen_dlg_text.py "$OUT/dlg_text.h"; $CC $DEF -I"$OUT" -c dialog.c -o "$OUT/dialog.o"; OBJS="$OBJS $OUT/dialog.o"; fi
# --- địa chỉ hàm/biến guest (tra theo tên demangled trong .so gốc; ld.lld --defsym) ---
NM=$(llvm-nm -C --defined-only "$SO")
sym() { echo "$NM" | grep -F " $1" | awk -v n="$1" '{ l=$0; sub(/^[0-9a-f]+ . /,"",l); if (l==n) {print $1; exit} }'; }
DEFS="--defsym=vn_state=0x1704c0"
for g in _lcdG gv SKIP_COLOR; do a=$(sym $g); [ -n "$a" ] || { echo "thiếu symbol $g"; exit 1; }; DEFS="$DEFS --defsym=$g=0x$a"; done
a=$(sym "Graphic::DrawImage(int, int, int, GbmImage*, int)"); DEFS="$DEFS --defsym=Graphic_DrawImage=0x$a"
for p in "_pTextMgr|_pTextMgr" "TextMgr::GetCommonText(int)|TextMgr_GetCommonText" "TextMgr::GetHelpTitleString(signed char)|TextMgr_GetHelpTitleString" \
  "TextMgr::DT_GetHelpInfoString(signed char)|TextMgr_DT_GetHelpInfoString" "Graphic::GetFB()|Graphic_GetFB" "Graphic::GetFBOff()|Graphic_GetFBOff" \
  "Graphic::CopyFrameBuffer(int, int)|Graphic_CopyFrameBuffer" "HERO::CalcStatusComputation(unsigned char)|HERO_CalcStatusComputation" \
  "StateInGameMenu::SetState(signed char)|StateInGameMenu_SetState" "StateInGameMenu::SetSubState(signed char)|StateInGameMenu_SetSubState" \
  "BagItem::GetMoney()|BagItem_GetMoney" "HERO::GetBagItemPtr()|HERO_GetBagItemPtr" \
  "TextMgr::GetInGameText(int)|TextMgr_GetInGameText" "HERO::IsNoneEquipWeapon()|HERO_IsNoneEquipWeapon" "HERO::IsNoneEquipShield()|HERO_IsNoneEquipShield" \
  "CHAR::GetSpritePtr()|CHAR_GetSpritePtr" "SPRITE::DrawSprite(int, int, int, signed char, signed char, short, unsigned char)|SPRITE_DrawSprite" \
  "Graphic::SetClip(int, int, int, int)|Graphic_SetClip" "Graphic::ResetClip()|Graphic_ResetClip" \
  "BagItem::GetSize()|BagItem_GetSize" "BagItem::GetCount()|BagItem_GetCount" "BagItem::GetBagItemPtr(signed char, short)|BagItem_GetBagItemPtr" \
  "BagItem::GetBagItemTotalBunchCount(signed char, signed char)|BagItem_GetBunch" "EquipItemInfo::IsEquipIng()|EquipItemInfo_IsEquipIng" \
  "HERO::GetEquipItemPtr()|HERO_GetEquipItemPtr" "EquipItem::GetEquipItem(signed char)|EquipItem_GetEquipItem" "HERO::GetHotKeyToggle()|HERO_GetHotKeyToggle" \
  "HERO::GetHotKeyBeltType(signed char, signed char)|HERO_GetHotKeyBeltType" "HERO::GetHotKeyBeltSkillIdx(signed char, signed char)|HERO_GetHotKeyBeltSkillIdx" \
  "ItemTable::GetItemTableInfo(ItemInfo*, signed char, signed char)|ItemTable_GetItemTableInfo" \
  "CommonUi::DrawIcon(int, int, int, short, unsigned char, unsigned char, int, int)|CommonUi_DrawIcon" \
  "CommonUi::DrawItemDiscription(int, int, int, ItemInfo*, signed char, unsigned char)|CommonUi_DrawItemDiscription" \
  "BFont::DrawTextLine(int, int, int, int, int, char*, int, int)|BFont_DrawTextLine" \
  "HERO::GetComboSkillBelt(signed char)|HERO_GetComboSkillBelt" "HERO::GetHeroSkillInfoPtr(signed char)|HERO_GetHeroSkillInfoPtr"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L2/T17c5: symbol guest cho npc.c
for p in "StateInNpcMenu::GetSearchSkillBook(signed char*)|NPC_GetSearchSkillBook" "NpcShop::GetItemInfo(ItemInfo*, signed char)|NpcShop_GetItemInfo" "HERO::GetCollectionCompleteTotalCount()|HERO_GetCollectionCompleteTotalCount" "vtable for StateInNpcMenu|NPC_vtable"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L1/T17c6: symbol guest cho expert.c (vtable StateInExpertMenu: nhận diện popup có cha là Chuyên gia)
for p in "vtable for StateInExpertMenu|EXPERT_vtable"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L2/T17c5b: symbol guest cho npc.c (Bộ sưu tập lưới)
for p in "HERO::IsCollectionComplete(signed char, signed char)|HERO_IsCollectionComplete" "HERO::GetCollectionCompleteCount(signed char)|HERO_GetCollectionCompleteCount" "BagItem::GetBagItemTotalBunchCount(signed char, signed char)|BagItem_GetBagItemTotalBunchCount"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L2/T17c5c: symbol guest cho npc.c (Tôi luyện/Orb/Chế biến)
for p in "HERO::GetRefineItemPtr()|HERO_GetRefineItemPtr" "RefineItem::GetItemRefineMoney(short)|RefineItem_GetItemRefineMoney" "HERO::GetMixSmithTableInfoPtr(signed char)|HERO_GetMixSmithTableInfoPtr"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L3/T17c7: symbol guest cho mmenu.c (SlotInfo, MenuText, NumKeyToNaviKey, vtable StateMainMenu)
for p in "SlotInfo::GetHeroClass()|SlotInfo_GetHeroClass" "SlotInfo::GetHeroLevel()|SlotInfo_GetHeroLevel" "SlotInfo::GetPlayTime()|SlotInfo_GetPlayTime" "TextMgr::GetMenuText(int)|TextMgr_GetMenuText" "StaticUtil::NumKeyToNaviKey(int)|StaticUtil_NumKeyToNaviKey" "vtable for StateMainMenu|MAIN_vtable"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
DEFS="$DEFS --defsym=StaticUtil_FileRead=0x1416e0"
# T17c3b: symbol guest cho property.c
for p in "HERO::GetHeroSkillInfoPtr(signed char)|HERO_GetHeroSkillInfoPtr" "CommonUi::GetSkillDiscription(char*, signed char, HeroSkillInfo*)|CommonUi_GetSkillDiscription"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
a=$(sym "CommonUi::GetSkillDiscription(char*, signed char, HeroSkillInfo*)"); [ -n "$a" ] || { echo "thiếu symbol GetSkillDiscription"; exit 1; }; DEFS="$DEFS --defsym=CommonUi_GetSkillDiscription=0x$a"   # T17c3c
# L1/T7: symbol guest cho t7.c
for p in "ItemTable::NewItemToBag(signed char, signed char, short, unsigned char)|ItemTable_NewItemToBag" "StateCashShop::SetPopupState(signed char, char*, int, int, int)|StateCashShop_SetPopupState" \
  "HERO::Revival()|HERO_Revival" "StateGame::SetState(signed char)|StateGame_SetState" "NETWORK::netConnect(signed char)|NETWORK_netConnect"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L1/T17c4: symbol guest cho spirit.c
for p in "Battle::Paint()|Battle_Paint" "HERO::GetSpiritInfoPtr()|HERO_GetSpiritInfoPtr" "HERO::GetEquipItemSpiritPtr()|HERO_GetEquipItemSpiritPtr" "EquipItemSpirit::GetEquipItemSpirit(signed char)|EquipItemSpirit_GetEquipItemSpirit"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# T16: symbol guest cho dialog.c (BFont, EventProc/DIALOG_INFO, _pCFont)
for p in "_pCFont|_pCFont" "BFont::DrawTextField(int, int, int, int, int, char*, int, int, int, int)|BFont_DrawTextField" \
  "BFont::IsTextFieldDrawComplete(int, int, char*, int, int, int)|BFont_IsTextFieldDrawComplete" "BFont::HanFontHeight()|BFont_HanFontHeight" \
  "BFont::GetTextFieldPageLen(int, int, char*)|BFont_GetTextFieldPageLen" "BFont::SetColor(int)|BFont_SetColor" "BFont::SetColorFixed(unsigned char)|BFont_SetColorFixed" "TextMgr::GetNpcNameText(int)|TextMgr_GetNpcNameText" \
  "EventProc::Event_DialogWindow(signed char)|EventProc_Event_DialogWindow" "DIALOG_INFO::Draw_Face()|DIALOG_INFO_Draw_Face" \
  "DIALOG_INFO::SetFacePosition(unsigned char, signed char)|DIALOG_INFO_SetFacePosition" \
  "Graphic::DrawImageBlending(int, int, int, GbmImage*, unsigned char, unsigned char, unsigned char, int)|Graphic_DrawImageBlending"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# T14: symbol guest cho hud.c
for p in "GMenu::DrawSkillBelt(int, int, int, unsigned char, signed char, short)|GMenu_DrawSkillBelt" "Graphic::DrawEffect(int, int, int, GbmImage*, unsigned char, int, int, int, unsigned char, unsigned char, int)|Graphic_DrawEffect" \
  "CommonUi::DrawInfoRect(int, int, int, int, int)|CommonUi_DrawInfoRect" "BFont::DrawText(int, int, int, char*, int, int)|BFont_DrawText" "StaticUtil::FileWrite(char*, char*, int)|StaticUtil_FileWrite"; do
  a=$(sym "${p%%|*}"); [ -n "$a" ] || { echo "thiếu symbol ${p%%|*}"; exit 1; }; DEFS="$DEFS --defsym=${p##*|}=0x$a"
done
# L1b/T17c8: symbol guest cho map.c
a=$(sym "StateMap::DrawZone()"); [ -n "$a" ] || { echo "thiếu symbol StateMap::DrawZone"; exit 1; }; DEFS="$DEFS --defsym=StateMap_DrawZone=0x$a"
a=$(grep -E ' vn_ensure$' ../out/vn.sym | awk '{print $1}'); [ -n "$a" ] || { echo "thiếu vn_ensure (build.sh của engine chạy trước)"; exit 1; }; DEFS="$DEFS --defsym=vn_ensure=0x$a"
for c in $(grep -o '^MUI_CLASS([A-Z]*, [A-Za-z]*' classes.def | sed 's/MUI_CLASS([A-Z]*, //'); do
  for m in "Paint()" "KeyPressed(int)" "TouchPressed(int, int, int)" "TouchMoved(int, int, int)" "TouchReleased(int, int, int)"; do
    a=$(sym "$c::$m"); [ -n "$a" ] || { echo "thiếu $c::$m"; exit 1; }
    DEFS="$DEFS --defsym=${c}_${m%%(*}=0x$a"
  done
done
cat > "$OUT/menu.ld" <<L
ENTRY(mui_begin)
SECTIONS {
  . = $BASE;
  .text : { *(.text) *(.text.*) }
  .rodata : ALIGN(4) { *(.rodata) *(.rodata.*) }
  .data : ALIGN(4) { *(.data) *(.data.*) }
  mui_file_end = .;
  .bss : ALIGN(16) { *(.bss) *(.bss.*) *(COMMON) }
  mui_seg_end = .;
  /DISCARD/ : { *(.ARM.exidx*) *(.ARM.extab*) *(.comment) *(.note*) *(.ARM.attributes) }
}
L
ld.lld -m armelf -static --emit-relocs -T "$OUT/menu.ld" --no-rosegment $DEFS $OBJS -o "$OUT/menu.elf"
llvm-objcopy -O binary -j .text -j .rodata -j .data "$OUT/menu.elf" "$OUT/menu.bin"
llvm-nm "$OUT/menu.elf" > "$OUT/menu.sym"
FE=$(awk '$3=="mui_file_end"{print $1}' "$OUT/menu.sym"); SE=$(awk '$3=="mui_seg_end"{print $1}' "$OUT/menu.sym")
FS=$((0x$FE - BASE)); MS=$((0x$SE - BASE))
BS=$(stat -c %s "$OUT/menu.bin")
[ "$BS" = "$FS" ] || { echo "kích thước bin $BS != file_end-base $FS"; exit 1; }
RELOC=$(llvm-readelf -r "$OUT/menu.elf" | grep -cE 'R_ARM_(ABS32|GOT|RELATIVE|GLOB_DAT|TARGET1|PREL31)' || true)   # --emit-relocs: ABS32 = địa chỉ tuyệt đối link (SAI khi nạp ở base khác)
# lớp đã bật = các lớp xuất hiện trong states.def (sau tiền xử lý, có/không -DMUI_DEBUG)
EN=$(clang-18 -E -P -x c -D'MUI_STATE(C,N,CO,P,T,M)=@@C@@' $( [ "$DBG" = 1 ] && echo -DMUI_DEBUG ) states.def | grep -o '@@[A-Z]*@@' | tr -d '@' | sort -u | tr '\n' ' ')
DS=$(llvm-readelf -S "$OUT/menu.elf" | awk '$2==".data"{print $6}')
[ -z "$DS" ] || [ "$((0x$DS))" = 0 ] || { echo ".data không rỗng ($DS): biến có khởi tạo không được phép"; exit 1; }
echo "base=$BASE filesz=$FS memsz=$MS debug=$DBG reloc=$RELOC classes=$EN" | tee "$OUT/menu.info"
[ "$RELOC" = 0 ] || { echo "CÓ RELOCATION (phải 0)"; exit 1; }
