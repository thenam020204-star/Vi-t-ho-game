#!/usr/bin/env python3
"""Va nhi phan toi thieu libgame.so (ARM64) cho ban offline.

    python3 patch_lib.py <libgame.so goc> <libgame.so ra>

Moi patch: (vaddr, byte goc, byte moi, ly do). Doan .text nam o LOAD dau (file offset == vaddr).
Byte goc phai khop dung, lech thi dung (khong va mo).
"""
import hashlib, struct, sys


def b_imm(src, dst):
    """Lenh B (re nhanh khong dieu kien) tu src toi dst."""
    off = dst - src
    assert off % 4 == 0 and -(1 << 27) <= off < (1 << 27)
    return struct.pack('<I', 0x14000000 | ((off >> 2) & 0x3FFFFFF))


PATCHES = [
    # STATE_EnterMainMenu (khi bam START): `bl isAuthUser; tbz w0,#0,popup_dang_nhap_Hive`
    # -> luon di nhanh 0x203f40 = UI_SetPopupProcessInfo(1,0), y het nut NO (MainMenu_HubLoginCancel).
    (0x203e68, bytes.fromhex('a0040036'), b_imm(0x203e68, 0x203f40),
     'Bo popup "Dang nhap Com2uS Hive?" khi bam START (nhu bam NO)'),
]

TEXT_END = 0x2b5570


def main(src, dst):
    data = bytearray(open(src, 'rb').read())
    print('goc sha256', hashlib.sha256(data).hexdigest())
    for va, old, new, why in PATCHES:
        assert va + len(old) <= TEXT_END
        cur = bytes(data[va:va + len(old)])
        if cur != old:
            raise SystemExit('PATCH 0x%x: byte goc %s != ky vong %s' % (va, cur.hex(), old.hex()))
        data[va:va + len(new)] = new
        print('0x%06x %s -> %s  %s' % (va, old.hex(), new.hex(), why))
    open(dst, 'wb').write(data)
    print('ra  sha256', hashlib.sha256(data).hexdigest(), '(%d patch)' % len(PATCHES))


if __name__ == '__main__':
    main(*sys.argv[1:3])
