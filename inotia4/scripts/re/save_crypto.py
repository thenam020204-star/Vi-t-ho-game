#!/usr/bin/env python3
"""Giai ma / ma hoa file save cua Inotia 4 (ENCRYPT_Process2 trong libgame.so, ARM64).

    save_crypto.py <libgame.so> <file> --key STR [--oob zero|file:PATH[@OFF]] [--encrypt] [--r N]
                   [--out PATH] [--null-key] [--key-hex HEX] [--table-vaddr 0x..]
    save_crypto.py <libgame.so> --selftest

Dinh dang (xem docs/save_format.md): file = n byte ma + 3 byte duoi [sum_ma, r, sum_tho] (mod 256).
    p_i = c_i ^ ((key[(r+i) mod L] + T[(r+i) & 255]) & 255),   T = bang 256 byte doc tu lib.
Bang T duoc tim bang cach doc lenh ADR trong ENCRYPT_GetKey / ENCRYPT_Process2 (khong hard-code).

--oob chi co nghia khi khoa RONG (L = 0). Ban goc khi do khong chia modulo ma doc key[r+i] vuot
buffer (ENCRYPT_Process2 @0x140970, vong 0x1409e8/0x140a5c: sdiv cho 0, msub tra nguyen chi so).
  zero      : byte vuot buffer = 0  =>  keystream = T  (dung la hanh vi sau ban va P1: coi L = 1, key[0] = NUL)
  file:P@O  : byte vuot buffer lay tu tep P (dump bo nho bat dau tai dia chi khoa), tu offset O (mac dinh 0)
Khoa rong ma khong chi --oob thi mac dinh la zero va in ghi chu.

Ma thoat: 0 neu ca hai tong kiem khop (hoac che do ma hoa thanh cong), 1 neu khong khop, 2 neu loi tham so.
"""
import argparse
import os
import random
import struct
import sys

SECTION_NAMES = ['Info', 'Player', 'Chars', 'Inventory', 'Quest', 'Event', 'ETC']


# ---------------------------------------------------------------- ELF (chi phan can dung)
class Elf:
    def __init__(self, path):
        self.d = open(path, 'rb').read()
        d = self.d
        if d[:4] != b'\x7fELF' or d[4] != 2 or d[5] != 1:
            raise SystemExit('%s: khong phai ELF64 little-endian' % path)
        (self.phoff,) = struct.unpack_from('<Q', d, 0x20)
        (self.shoff,) = struct.unpack_from('<Q', d, 0x28)
        self.phentsize, self.phnum, self.shentsize, self.shnum = struct.unpack_from('<HHHH', d, 0x36)
        self.loads = []
        for i in range(self.phnum):
            ptype, _fl, off, va, _pa, fsz, msz, _al = struct.unpack_from('<IIQQQQQQ', d, self.phoff + i * self.phentsize)
            if ptype == 1:
                self.loads.append((va, off, fsz, msz))
        self.syms = {}      # ten -> (vaddr, size)
        self.objs = []      # (vaddr, size, ten) cho OBJECT
        self._read_dynsym()

    def _read_dynsym(self):
        d = self.d
        secs = [struct.unpack_from('<IIQQQQIIQQ', d, self.shoff + i * self.shentsize) for i in range(self.shnum)]
        for s in secs:
            if s[1] == 11:                                  # SHT_DYNSYM
                strtab = secs[s[6]]
                off, size = s[4], s[5]
                for j in range(size // 24):
                    nm, info, _o, _sh, val, sz = struct.unpack_from('<IBBHQQ', d, off + j * 24)
                    e = d.index(b'\0', strtab[4] + nm)
                    name = d[strtab[4] + nm:e].decode('latin1')
                    if name:
                        self.syms[name] = (val, sz)
                        if (info & 0xf) == 1 and sz:
                            self.objs.append((val, sz, name))
        self.objs.sort()

    def off(self, va, n=1):
        for lva, loff, fsz, _m in self.loads:
            if lva <= va and va + n <= lva + fsz:
                return loff + (va - lva)
        raise SystemExit('vaddr 0x%x (+%d) khong nam trong phan co file cua segment LOAD nao' % (va, n))

    def read(self, va, n):
        o = self.off(va, n)
        return self.d[o:o + n]

    def u32(self, va):
        return struct.unpack('<I', self.read(va, 4))[0]

    def obj_at(self, va):
        for a, sz, nm in self.objs:
            if a <= va < a + sz:
                return '%s+0x%x' % (nm, va - a)
        return None


def adr_targets(elf, name):
    """Mo ta cac lenh ADR trong ham `name`: [(pc, dich)]."""
    va, sz = elf.syms[name]
    out = []
    for pc in range(va, va + sz, 4):
        w = elf.u32(pc)
        if (w & 0x9f000000) == 0x10000000:
            imm = ((w >> 5) & 0x7ffff) << 2 | ((w >> 29) & 3)
            if imm & (1 << 20):
                imm -= 1 << 21
            out.append((pc, pc + imm))
    return out


def find_table(elf, override=None):
    if override is not None:
        return override, 'tham so --table-vaddr'
    a = {t for _pc, t in adr_targets(elf, 'ENCRYPT_GetKey')}
    b = {t for _pc, t in adr_targets(elf, 'ENCRYPT_Process2')}
    if len(a) != 1 or a != b:
        raise SystemExit('khong xac dinh duoc bang T: ADR trong ENCRYPT_GetKey=%s, ENCRYPT_Process2=%s'
                         % (sorted(map(hex, a)), sorted(map(hex, b))))
    return a.pop(), 'ADR trong ENCRYPT_GetKey va ENCRYPT_Process2'


# ---------------------------------------------------------------- loi ma hoa
class Oob:
    """Nguon byte key[idx] khi khoa rong."""

    def __init__(self, spec):
        self.spec = spec
        self.mem = None
        if spec == 'zero':
            return
        if spec.startswith('file:'):
            path, _, off = spec[5:].partition('@')
            self.mem = open(path, 'rb').read()[int(off, 0) if off else 0:]
            return
        raise SystemExit('--oob khong hop le: %r (zero | file:PATH[@OFF])' % spec)

    def __call__(self, idx):
        if self.mem is None:
            return 0
        if idx >= len(self.mem):
            raise SystemExit('--oob file qua ngan: can byte %d, chi co %d' % (idx, len(self.mem)))
        return self.mem[idx]


def keystream(T, key, oob, r, n):
    L = len(key)
    ks = bytearray()
    for i in range(n):
        idx = r + i
        kb = key[idx % L] if L else oob(idx)
        ks.append((kb + T[idx & 255]) & 255)
    return bytes(ks)


def decrypt(buf, T, key, oob):
    """buf = ma + 3 byte duoi. Tra ve dict."""
    if len(buf) < 3:
        raise SystemExit('file qua ngan (%d B) cho phan duoi 3 byte' % len(buf))
    n = len(buf) - 3
    c = buf[:n]
    sum_c_stored, r, sum_p_stored = buf[n], buf[n + 1], buf[n + 2]
    if n < 1:      # ban goc: b.lt -> tong = 0
        p = b''
        r_used = 0
    else:
        r_used = r
        ks = keystream(T, key, oob, r, n)
        p = bytes(a ^ b for a, b in zip(c, ks))
    return dict(n=n, c=c, p=p, r=r, sum_c_stored=sum_c_stored, sum_p_stored=sum_p_stored,
                sum_c=sum(c) & 255, sum_p=sum(p) & 255)


def encrypt(plain, T, key, oob, r):
    n = len(plain)
    ks = keystream(T, key, oob, r, n) if n else b''
    c = bytes(a ^ b for a, b in zip(plain, ks))
    return c + bytes([sum(c) & 255, r & 255, sum(plain) & 255])


# ---------------------------------------------------------------- container save
def parse_container(p):
    """Bang 7 muc tai p+8: (u16 off, u16 size), off tinh tu p+8. Tra ve (rows, loi)."""
    errs = []
    if len(p) < 8 + 28:
        return [], ['qua ngan cho bang 7 muc (%d B)' % len(p)]
    rows = []
    for k in range(7):
        off, size = struct.unpack_from('<HH', p, 8 + 4 * k)
        rows.append((SECTION_NAMES[k], off, size))
    if rows[0][1] != 28:
        errs.append('muc dau (Info) off=%d, ky vong 28 (ngay sau bang)' % rows[0][1])
    for (na, oa, sa), (nb, ob, _sb) in zip(rows, rows[1:]):
        if oa + sa != ob:
            errs.append('%s: off+size=%d khong khop off cua %s=%d' % (na, oa + sa, nb, ob))
    end = 8 + rows[-1][1] + rows[-1][2]
    if end != len(p):
        errs.append('ket thuc muc cuoi = %d khac n = %d' % (end, len(p)))
    return rows, errs


def parse_info(p, rows):
    """Info (33 B) theo SAVE_LoadFile: u8 slot, u32, u64, u64, u32, u32, u32 version."""
    off = 8 + rows[0][1]
    if rows[0][2] < 33 or off + 33 > len(p):
        return None
    slot = p[off]
    a, b, c, d, e, ver = struct.unpack_from('<IQQIII', p, off + 1)
    return dict(slot=slot, u32_a=a, u64_b=b, u64_c=c, u32_d=d, u32_e=e, version=ver)


def suspects(p, r, base):
    """Tim qword dang 'con tro heap Android' (xx 00 00 tag, byte4 >= 0x60, tag != 0) o dia chi tuyet doi chia het 8
    trong anh khoa (HubSave_key + r + i). Chi la goi y (heuristic), khong phai chung minh."""
    out = []
    for i in range(0, len(p) - 7):
        a = base + r + i
        if a % 8:
            continue
        q = int.from_bytes(p[i:i + 8], 'little')
        if ((q >> 40) & 0xffff) == 0 and (q >> 56) != 0 and ((q >> 32) & 0xff) >= 0x60:
            out.append((i, r + i, a, q))
    return out


KNOWN_INFO = struct.pack('<III', 140, 20132, 5)   # SAVE_Save @0x1d5088..0x1d50a4: MEM_WriteUint32_S(0x8c), (0x4ea4), (5)


def known_plain(buf, T, rows, r, base):
    """Bang chung chac chan hon heuristic: 12 byte Info+21..+32 la hang so (140, 20132, version 5) do SAVE_Save ghi.
    Neu giai ma voi keystream T ma khac hang so thi byte khoa that tai do KHAC 0; suy ra byte bo nho
    b = ((c ^ p_dung) - T[(r+i)&255]) mod 256 tai dia chi HubSave_key + (r+i)."""
    start = 8 + rows[0][1] + 21
    out = []
    for k, exp in enumerate(KNOWN_INFO):
        i = start + k
        c = buf[i]
        got = c ^ T[(r + i) & 255]
        b = ((c ^ exp) - T[(r + i) & 255]) & 255
        out.append((i, r + i, base + r + i, got, exp, b))
    return out


# ---------------------------------------------------------------- selftest
KAT_FILE = bytes.fromhex(
    '4ad27913e74eda4d5362d898895bd5587cf683eb0d0cae5e535e8743bae644')   # 31 B: logs/save_TEST7/.../thpcheckslot1.dat
KAT_KEY = b'394572'
KAT_PLAIN = b'2|000001|99|99|99|99|99|END|'


def selftest(T):
    bad = 0
    # 1. known-answer test voi file that cua may nguoi dung (thpcheckslot1.dat, khoa 394572)
    res = decrypt(KAT_FILE, T, KAT_KEY, Oob('zero'))
    ok = (res['p'] == KAT_PLAIN and res['sum_c'] == res['sum_c_stored'] and res['sum_p'] == res['sum_p_stored']
          and res['r'] == 230)
    print('KAT thpcheckslot1.dat (khoa 394572): %s' % ('OK' if ok else 'SAI'), res['p'])
    bad += not ok
    # 2. roundtrip
    rng = random.Random(12345)
    cases = 0
    for key in (b'', b'394572', b'a', b'0123456789abcdef' * 4, b'\xff\x80'):
        for n in (0, 1, 2, 7, 36, 255, 256, 257, 3189, 4000):
            for r in (0, 1, 139, 230, 255):
                plain = bytes(rng.randrange(256) for _ in range(n))
                enc = encrypt(plain, T, key, Oob('zero'), r)
                dec = decrypt(enc, T, key, Oob('zero'))
                cases += 1
                if len(enc) != n + 3 or dec['p'] != plain or dec['sum_c'] != dec['sum_c_stored'] \
                        or (n and dec['sum_p'] != dec['sum_p_stored']) or enc[n + 1] != r:
                    print('roundtrip SAI: key=%r n=%d r=%d' % (key, n, r))
                    bad += 1
    print('roundtrip ma->giai ma: %d truong hop, %s' % (cases, 'tat ca bang nhau' if not bad else 'CO LOI'))
    # 3. khoa rong + oob zero == khoa 1 byte NUL (hanh vi cua P1: L = 1, key[0] = NUL)
    plain = bytes(rng.randrange(256) for _ in range(500))
    e1 = encrypt(plain, T, b'', Oob('zero'), 77)
    e2 = encrypt(plain, T, b'\0', Oob('zero'), 77)
    ok = e1 == e2
    print('khoa rong (oob zero) == khoa "\\0" do dai 1: %s' % ('OK' if ok else 'SAI'))
    bad += not ok
    return 0 if not bad else 1


# ---------------------------------------------------------------- main
def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument('lib')
    ap.add_argument('file', nargs='?')
    ap.add_argument('--key', default=None, help='khoa dang chuoi (UTF-8); "" = khoa rong')
    ap.add_argument('--key-hex', default=None)
    ap.add_argument('--null-key', action='store_true', help='x3 = NULL trong ENCRYPT_Process2 -> khoa mac dinh "123"')
    ap.add_argument('--oob', default=None)
    ap.add_argument('--encrypt', action='store_true', help='file = ban tho, ghi ban ma + 3 byte duoi vao --out')
    ap.add_argument('--r', type=lambda s: int(s, 0), default=None, help='r (0..255) khi ma hoa; mac dinh ngau nhien')
    ap.add_argument('--out', default=None, help='ghi ban tho (giai ma) hoac ban ma (--encrypt)')
    ap.add_argument('--table-vaddr', type=lambda s: int(s, 0), default=None)
    ap.add_argument('--selftest', action='store_true')
    a = ap.parse_args()

    elf = Elf(a.lib)
    tva, how = find_table(elf, a.table_vaddr)
    T = elf.read(tva, 256)
    print('lib   : %s' % a.lib)
    print('bang T: vaddr 0x%x (%s), T[0..7]=%s' % (tva, how, T[:8].hex()))
    if a.selftest:
        return selftest(T)
    if a.file is None:
        ap.error('thieu <file>')

    if a.null_key:
        key = b'123'
        kdesc = 'NULL -> mac dinh "123"'
    elif a.key_hex is not None:
        key = bytes.fromhex(a.key_hex)
        kdesc = 'hex %s' % a.key_hex
    elif a.key is not None:
        key = a.key.encode('utf-8')
        kdesc = repr(a.key)
    else:
        ap.error('can --key (hoac --key-hex / --null-key)')
    oob = Oob(a.oob or 'zero')
    if not key:
        if a.oob is None:
            print('ghi chu: khoa RONG -> mac dinh --oob zero (keystream = T, giong ban sau P1). '
                  'Ban goc doc key[r+i] vuot buffer HubSave_key (64 B).')
        kdesc += ' (L=0, oob=%s)' % oob.spec
    print('khoa  : %s' % kdesc)

    data = open(a.file, 'rb').read()
    if a.encrypt:
        r = a.r if a.r is not None else random.randrange(256)
        enc = encrypt(data, T, key, oob, r & 255)
        back = decrypt(enc, T, key, oob)
        ok = back['p'] == data and back['sum_c'] == back['sum_c_stored'] and (not data or back['sum_p'] == back['sum_p_stored'])
        print('ma hoa: n=%d r=%d  sum_ma=%d sum_tho=%d  -> %d B; kiem lai bang giai ma: %s'
              % (len(data), r & 255, enc[-3], enc[-1], len(enc), 'OK' if ok else 'SAI'))
        if a.out:
            open(a.out, 'wb').write(enc)
            print('da ghi %s' % a.out)
        return 0 if ok else 1

    res = decrypt(data, T, key, oob)
    n = res['n']
    okc = res['sum_c'] == res['sum_c_stored']
    okp = res['sum_p'] == res['sum_p_stored']
    print('file  : %s (%d B) -> n=%d byte ma + 3 byte duoi' % (a.file, len(data), n))
    print('duoi  : sum_ma luu=%d tinh=%d %s | r=%d | sum_tho luu=%d tinh=%d %s'
          % (res['sum_c_stored'], res['sum_c'], 'KHOP' if okc else 'SAI',
             res['r'], res['sum_p_stored'], res['sum_p'], 'KHOP' if okp else 'SAI'))
    if not okp:
        print('        sum_tho lech (luu - tinh) mod 256 = %d' % ((res['sum_p_stored'] - res['sum_p']) & 255))
    print('ket luan: %s' % ('GIAI MA DUNG (ca hai tong kiem khop)' if okc and okp else
                            'GIAI MA THAT BAI (' + ('' if okc else 'sum_ma ') + ('' if okp else 'sum_tho') + ' sai)'))
    p = res['p']
    if a.out:
        open(a.out, 'wb').write(p)
        print('da ghi ban tho (%d B) vao %s' % (len(p), a.out))

    rows, errs = parse_container(p)
    if rows and (not errs or okp):
        print('\nbang 7 muc (u16 off, u16 size tai p+8; off tinh tu p+8):')
        print('  #  %-10s %6s %6s   [tuyet doi tu p+0)' % ('ten', 'off', 'size'))
        for k, (nm, off, size) in enumerate(rows):
            print('  %d  %-10s %6d %6d   [%d, %d)' % (k, nm, off, size, 8 + off, 8 + off + size))
        print('  kiem tra cau truc: %s' % ('HOP LE' if not errs else 'KHONG hop le'))
        for e in errs:
            print('    - ' + e)
        info = parse_info(p, rows) if not errs else None
        if info:
            print('  Info: slot=%d u32_a=0x%08x u64_b=0x%016x u64_c=0x%016x u32_d=0x%08x u32_e=0x%08x version=%d%s'
                  % (info['slot'], info['u32_a'], info['u64_b'], info['u64_c'], info['u32_d'], info['u32_e'],
                     info['version'], '' if info['version'] <= 5 else '  (> 5: SAVE_LoadFile se tu choi)'))
    if not okp and not key and 'HubSave_key' in elf.syms:
        base = elf.syms['HubSave_key'][0]
        if rows and not errs and rows[0][2] == 33:
            kp = known_plain(data, T, rows, res['r'], base)
            bad = [x for x in kp if x[3] != x[4]]
            print('\nbang chung chac (known-plaintext): SAVE_Save ghi hang so 140, 20132, 5 (u32) tai Info+21..+32;'
                  ' giai ma bang T cho %d/%d byte sai:' % (len(bad), len(kp)))
            for i, idx, addr, got, exp, b in kp:
                print('  i=%-4d key-index=%-4d dia chi 0x%x (%s)  giai ma=0x%02x ky vong=0x%02x  byte bo nho suy ra=0x%02x%s'
                      % (i, idx, addr, elf.obj_at(addr) or '?', got, exp, b, '' if got == exp else '  <-- CHENH'))
        sus = suspects(p, res['r'], base)
        print('\nvi tri nghi chenh so voi keystream T (goi y heuristic: qword dang con tro heap tag:00:00:xx:........ o dia chi '
              'chia het 8; key-index = r+i, dia chi = HubSave_key(0x%x)+r+i):' % base)
        if not sus:
            print('  (khong tim thay)')
        for i, idx, addr, q in sus:
            print('  i=%-5d key-index=%-5d dia chi 0x%x (%s) qword=0x%016x' % (i, idx, addr, elf.obj_at(addr) or '?', q))
        print('  Y nghia: neu ban dau khoa rong doc bo nho vuot buffer thi cac byte nay la gia tri con tro heap luc luu'
              ' (doi theo moi lan chay) => khong tai lap duoc o tien trinh khac.')
    return 0 if okc and okp else 1


if __name__ == '__main__':
    sys.exit(main())
