# HL4_60fps — Heroes Lore 4: Phantasmal Mask, mod 60 khung/giây

Bản hiện tại: **v1.0.2** (`dist/HL4.jar`, `dist/HL4.jad`) — nội suy hiển thị ở mọi màn có chuyển động, logic giữ nhịp gốc. Chi tiết: `REFACTOR_LOG.md`, `docs/RENDER_60FPS.md`.

```
base/       HL4_1.01.jar — JAR người dùng gửi (đầu vào build, có nội suy di chuyển phiên 1)
src/        hl4/game/Interp.java — mã nội suy (Java 1.3, CLDC 1.1 / MIDP 2.0)
tools/      build.sh, src/Patch.java (hook ASM), Verify.java, Compare.java, RenderEffects.java,
            harness/ (SeHarness: Java SE tất định; FjHarness: FreeJ2ME; MeAgent: MicroEmulator) + kịch bản
docs/       RENDER_60FPS.md, BINDIFF_session2.md
dist/       HL4.jar, HL4.jad
screenshots/session2/  khung phụ (cột: tick trước | khung phụ 1 | khung phụ 2)
```

## Build
Cần: JDK 8 (javac), JDK 11+ (chạy ASM 9.7/ProGuard), `asm*-9.7.jar`, `proguard-7.6.1`, `cldcapi11-2.0.4.jar`, `midpapi20-2.0.4.jar` (org.microemu) trong thư mục `$T`.
```
T=/đường/dẫn/toolchain VERSION=1.0.2 tools/build.sh
```
Các bước: Patch ASM → javac → ghép JAR (giữ nguyên tài nguyên & thứ tự entry) → Verify → ProGuard `-microedition` → Verify `--require-stackmap` → JAD.

## Tắt 60 fps
Thêm `HL4-60fps: 0` vào JAD.
