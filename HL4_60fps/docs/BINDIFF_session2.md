# Diff nhị phân — base/HL4_1.01.jar → dist/HL4.jar (v1.0.2)

Công cụ: `tools/src/Compare.java` (ASM, bỏ debug/frame, so từng method sau chuẩn hoá label & constant pool) + so md5 từng entry.

## Entry
- base 2 014 entry, mới 2 014 entry; thêm 0, xoá 0.
- Tài nguyên khác: chỉ `META-INF/MANIFEST.MF` (`MIDlet-Version: 1.0.1` → `1.0.2`).
- Class khác byte: 12. Trong đó 3 class chỉ khác do preverify lại (StackMap), mọi method tương đương: `hl4/sys/AudioPlayer`, `hl4/sys/Rms`, `hl4/game/Interp$Sink`.

## Method khác (11 class + 1 class mới `hl4/gfx/FastFx`, v1.0.4)
| Class | Method | Chủ đích |
|---|---|---|
| `hl4/gfx/Sprite` | `drawNpc(...)I` | hook `sprBegin` + `cellX/cellY` |
| `hl4/game/HeroDraw` | `particleProcess()V` | khung phụ mô phỏng hạt |
| `hl4/game/Effects` | `draw(Obj,II)V` | khung phụ → `Interp.drawEffect` |
| `hl4/game/Dialog` | `drawScriptWindow(Frame,IZ)V` | `lp` vị trí khung thoại |
| `hl4/game/EventDraw` | `draw`, `subTitle`, `skipFade` | `lp` viền / alpha |
| `hl4/game/HudKtf` | `drawInfo(Obj)V` | `lp` độ trượt |
| `hl4/game/MainGameFrame` | `drawInfo()V` | `lp` độ trượt |
| `hl4/game/TitleFrame` | `<header>` (cờ field `frameCnt`) | private → package |
| `hl4/gfx/Fx` | `scaled(...)V` | 2c: chuyển sang `FastFx.scaled` (class mới) |
| `hl4/GameCanvas` | `run()V` | 2c: `slots = Interp.adaptSlots(slots, period)` |
| `hl4/game/Interp` | 29/31 method + header | viết lại (phiên 2) |

152 class còn lại: tương đương bytecode từng method.
