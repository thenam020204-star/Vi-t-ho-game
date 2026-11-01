#!/usr/bin/env python3
"""T10: giả lập (unicorn arm64) đoạn tính khung logic của nativeOnSurfaceCreated (0xc5cc0 -> 0xc5d38) đã vá,
gồm cave đọc hd_scale.txt (0xcf64c). Libc/JNI được giả bằng hook: GetStringUTFChars, snprintf, open, read, close dùng tệp thật
trong thư mục tạm. Kiểm: W,H khớp mô hình Python; x19..x28, x29, sp giữ nguyên khi tới 0xc5d38; x8/x9 = H/W.
Dùng: test_bridge_k.py PATCHED_BRIDGE.so [DEFAULT_K]"""
import sys, os, struct, tempfile
from unicorn import *
from unicorn.arm64_const import *
import unicorn.arm64_const as C
from elftools.elf.elffile import ELFFile

so = sys.argv[1]
DEFAULT_K = int(sys.argv[2]) if len(sys.argv) > 2 else 2
elf = ELFFile(open(so, 'rb'))
PLT = {0x1d2b90: 'snprintf', 0x1d2ad0: 'open', 0x1d2ae0: 'read', 0x1d2ab0: 'close'}
ENV, VT, GET, REL, STK, STR = 0x10001000, 0x10002000, 0x10003000, 0x10003010, 0x10008000, 0x10020000
STOP = 0xc5d38


def model(sw, sh, content, has_dir=True):
    """mô hình tham chiếu: k từ file (đúng 1 chữ số 1..4, ký tự sau < 0x21), kẹp W>=400,H>=240."""
    k = DEFAULT_K
    if has_dir and content is not None and len(content) >= 1 and 0x31 <= content[0] <= 0x34 and (len(content) < 2 or content[1] < 0x21):
        k = content[0] - 0x30
    if sh < 240: return 400, 240
    k = min(k, sh // 240)
    while True:
        w, h = sw // k, sh // k
        if k <= 1 or (w >= 400 and h >= 240): break
        k -= 1
    return w & ~1, h & ~1


def run(sw, sh, content, has_dir=True):
    d = tempfile.mkdtemp()
    if content is not None: open(d + '/hd_scale.txt', 'wb').write(content)
    uc = Uc(UC_ARCH_ARM64, UC_MODE_ARM)
    uc.mem_map(0, 0x240000)
    for s in elf.iter_segments():
        if s['p_type'] == 'PT_LOAD': uc.mem_write(s['p_vaddr'], s.data())
    uc.mem_map(0x10000000, 0x40000)
    uc.reg_write(UC_ARM64_REG_CPACR_EL1, 0x300000)
    uc.mem_write(ENV, struct.pack('<Q', VT))
    uc.mem_write(VT + 0x548, struct.pack('<Q', GET)); uc.mem_write(VT + 0x550, struct.pack('<Q', REL))
    uc.mem_write(STR, d.encode() + b'\0')
    fds = {}; log = []

    def cstr(a):
        b = bytearray()
        while True:
            c = uc.mem_read(a + len(b), 1)[0]
            if c == 0: return bytes(b)
            b.append(c)

    def hook(uc, addr, size, ud):
        x = lambda n: uc.reg_read(getattr(C, 'UC_ARM64_REG_X%d' % n))
        ret = 0
        if addr == GET: ret = STR; log.append('GetStringUTFChars')
        elif addr == REL: log.append('Release'); ret = 0
        elif PLT.get(addr) == 'snprintf':
            fmt = cstr(x(2)); assert fmt == b'%s/hd_scale.txt', fmt
            out = (cstr(x(3)) + b'/hd_scale.txt')[:x(1) - 1] + b'\0'
            uc.mem_write(x(0), out); ret = len(out) - 1
        elif PLT.get(addr) == 'open':
            p = cstr(x(0)).decode(); assert x(1) & 0xffffffff == 0
            try: f = open(p, 'rb')
            except OSError: ret = 0xffffffff
            else: fd = 100 + len(fds); fds[fd] = f; ret = fd; log.append('open ' + os.path.basename(p))
        elif PLT.get(addr) == 'read':
            f = fds[x(0) & 0xffffffff]; dd = f.read(x(2)); uc.mem_write(x(1), dd); ret = len(dd); log.append('read %d' % len(dd))
        elif PLT.get(addr) == 'close':
            fds.pop(x(0) & 0xffffffff).close(); ret = 0; log.append('close')
        uc.reg_write(UC_ARM64_REG_X0, ret)
        uc.reg_write(UC_ARM64_REG_PC, uc.reg_read(UC_ARM64_REG_X30))
    for a in list(PLT) + [GET, REL]:
        uc.hook_add(UC_HOOK_CODE, hook, begin=a, end=a)
    sp0 = STK; regs = {19: 0x1919, 20: ENV, 21: 0x2121, 22: sh, 23: 0x2323, 24: (STR if has_dir else 0), 25: sw,
                       26: 0x10005000, 27: 0x2727, 28: 0x2828, 29: 0x2929}
    for n, v in regs.items(): uc.reg_write(getattr(C, 'UC_ARM64_REG_X%d' % n), v)
    uc.reg_write(UC_ARM64_REG_SP, sp0); uc.reg_write(UC_ARM64_REG_X30, 0x77770)
    uc.emu_start(0xc5cc0, STOP, count=3000)
    r = lambda n: uc.reg_read(getattr(C, 'UC_ARM64_REG_X%d' % n))
    for n in (19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29):
        assert r(n) == regs[n], ('x%d bị đổi' % n, hex(r(n)))
    assert uc.reg_read(UC_ARM64_REG_SP) == sp0 and r(30) == 0x77770, 'sp/lr đổi'
    # kiểm tiếp 0xc5d38 -> 0xc5d4c (ghi W/H vào đối tượng GL và biến toàn cục)
    W, H = r(9) & 0xffffffff, r(8) & 0xffffffff
    uc.emu_start(STOP, 0xc5d48, count=100)
    gw, gh = struct.unpack('<II', uc.mem_read(0x1fdac8, 8))
    ow, oh = struct.unpack('<II', uc.mem_read(0x10005058, 8))
    assert (gw, gh) == (W, H) == (ow, oh), (gw, gh, W, H, ow, oh)
    for f in fds.values(): f.close()
    return W, H, log


CASES = [('thiếu', None), ('"1"', b'1'), ('"2"', b'2'), ('"3"', b'3'), ('"4"', b'4'), ('"3\\n"', b'3\n'), ('"4\\r\\n"', b'4\r\n'),
         ('rỗng', b''), ('"0"', b'0'), ('"5"', b'5'), ('"12"', b'12'), ('"abc"', b'abc'), ('"-1"', b'-1'),
         ('nhị phân', bytes(range(0x80, 0x90)) + b'\0\1'), ('" 2"', b' 2')]
SCREENS = [(2400, 1080), (1920, 1080), (1600, 720), (1280, 720), (800, 480), (600, 270), (400, 240), (320, 200), (2560, 1440), (1080, 2400)]
bad = 0; n = 0
print('| màn | ' + ' | '.join(c[0] for c in CASES) + ' | không filesDir |')
print('|---|' + '---|' * (len(CASES) + 1))
for sw, sh in SCREENS:
    row = []
    for name, c in CASES:
        W, H, log = run(sw, sh, c); n += 1
        ok = (W, H) == model(sw, sh, c)
        if c is None: ok = ok and 'open' not in ' '.join(log)
        bad += not ok
        row.append('%dx%d%s' % (W, H, '' if ok else ' LỆCH'))
    W, H, log = run(sw, sh, b'1', has_dir=False); n += 1
    ok = (W, H) == model(sw, sh, b'1', has_dir=False) and not log
    bad += not ok
    row.append('%dx%d%s' % (W, H, '' if ok else ' LỆCH'))
    print('| %dx%d | ' % (sw, sh) + ' | '.join(row) + ' |')
print('\n%d lượt, %d lệch; DEFAULT_K=%d' % (n, bad, DEFAULT_K))
sys.exit(1 if bad else 0)
