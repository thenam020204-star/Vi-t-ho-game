#!/usr/bin/env python3
"""T17c0: thêm đoạn PT_LOAD của menu mới (engine/menu/build.sh) vào libHeroesLore5.so ĐÃ vá (apply_so.py).

  mk_menu_segment.py IN.so OUT.so MENU_OUTDIR

Đổi phdr[4] (PT_GNU_STACK; bridge ElfLoader chỉ đọc p_type==1) -> PT_LOAD {vaddr 0x171000, filesz = menu.bin, memsz = hết bss, flags RWX, align 0x1000},
nối menu.bin vào cuối file (căn 4 KB). Tái lập, kiểm byte gốc của phdr, thoát lỗi nếu lệch. KHÔNG phụ thuộc cờ --test-ptload (T17p)."""
import struct, sys, os, re
GNU_STACK = struct.pack('<8I', 0x6474e551, 0, 0, 0, 0, 0, 6, 4)


def main():
    src, dst, mdir = sys.argv[1:4]
    info = dict(re.findall(r'(\w+)=(.*?)(?=\s\w+=|\s*$)', open(os.path.join(mdir, 'menu.info')).read().strip()))
    base, filesz, memsz = int(info['base'], 16), int(info['filesz']), int(info['memsz'])
    blob = open(os.path.join(mdir, 'menu.bin'), 'rb').read()
    assert len(blob) == filesz and memsz >= filesz
    b = bytearray(open(src, 'rb').read())
    phoff = struct.unpack_from('<I', b, 0x1c)[0]; phnum = struct.unpack_from('<H', b, 0x2c)[0]
    assert phnum == 5 and phoff == 52, (phnum, phoff)
    assert bytes(b[phoff + 128:phoff + 160]) == GNU_STACK, 'phdr[4] không phải PT_GNU_STACK (đã có đoạn thêm?)'
    end = max(va + ms for t, off, va, pa, fs, ms, fl, al in (struct.unpack_from('<8I', b, phoff + 32 * i) for i in range(phnum)) if t == 1)
    assert end <= base, (hex(end), hex(base))
    while len(b) % 0x1000: b.append(0)
    off = len(b)
    b += blob
    b[phoff + 128:phoff + 160] = struct.pack('<8I', 1, off, base, base, filesz, memsz, 7, 0x1000)
    open(dst, 'wb').write(b)
    print('PT_LOAD menu: file off %#x vaddr %#x filesz %d memsz %d (bss %d); .so %d B' % (off, base, filesz, memsz, memsz - filesz, len(b)))


main()
