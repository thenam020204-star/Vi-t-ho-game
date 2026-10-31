#!/usr/bin/env python3
"""Va nhi phan qemu-system-aarch64-headless cua Android Emulator 37.2.13 (build 16740729, linux x86_64)
de chay anh he thong arm64 bang TCG (khong KVM) voi nhieu CPU.

  python3 patch_qemu.py <emulator>/qemu/linux-x86_64/qemu-system-aarch64-headless

Ba cho va (kiem byte goc truoc khi va, lech la dung):
 1. Chuoi "-soundhw" -> "-L": emulator luon them "-soundhw hda:..." nhung may ranchu arm64 tren host
    x86 khong co bus PCI -> "PCI bus not available for hda". "-L <x>" chi them thu muc tim BIOS, vo hai.
 2. ranchu_init (hw/arm/ranchu.c) khong gan "psci-conduit" cho CPU (chi gan khi KVM/HVF) -> kernel
    "psci: no cpu_on method", chi chay 1 CPU, system_server bi Watchdog giet lien tuc.
    Thay 2 lan gan "reset-cbar" (Linux arm64 khong doc CBAR) bang gan "psci-conduit" = 2 (HVC),
    giong QEMU goc lam khi TCG khong co EL2/EL3. Ket qua: "PSCIv0.2 detected", 4 CPU.
sha256 goc: e697effa1e3f5e0962dab1c8fdbca5787582e6081e2c22eabe74ff396fbd2436
"""
import hashlib, struct, sys

ORIG = 'e697effa1e3f5e0962dab1c8fdbca5787582e6081e2c22eabe74ff396fbd2436'
TEXT_DELTA = 0x1000          # .text: vaddr 0x864d00, offset 0x863d00
PSCI_STR = 0x395b28          # "psci-conduit\0" (.rodata: vaddr == offset)


def main(p):
    d = bytearray(open(p, 'rb').read())
    h = hashlib.sha256(d).hexdigest()
    if h != ORIG:
        raise SystemExit('sha256 %s khac ban goc (da va roi, hoac khac phien ban emulator)' % h)

    def put(off, old, new):
        assert d[off:off + len(old)] == old, (hex(off), d[off:off + len(old)].hex())
        d[off:off + len(new)] = new

    put(0x4196ac, b'-soundhw\x00', b'-L\x00\x00\x00\x00\x00\x00\x00')
    for va, pre in [(0x8f9339, '488d35'), (0x8f934f, '488d15'), (0x8f9397, '4c8d25')]:
        o = va - TEXT_DELTA
        put(o, bytes.fromhex(pre), bytes.fromhex(pre))
        d[o + 3:o + 7] = struct.pack('<i', PSCI_STR - (va + 7))   # lea "reset-cbar" -> lea "psci-conduit"
    for va in (0x8f935d, 0x8f941f):
        put(va - TEXT_DELTA, bytes.fromhex('be00000008'), bytes.fromhex('be02000000'))  # 0x8000000 -> 2
    open(p, 'wb').write(d)
    print('da va:', p, hashlib.sha256(d).hexdigest())


if __name__ == '__main__':
    main(sys.argv[1])
