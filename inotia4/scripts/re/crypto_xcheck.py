#!/usr/bin/env python3
"""So ket qua unidbg (emu/src/CryptoCheck.java) voi scripts/re/save_crypto.py.

    crypto_xcheck.py <libgame.so goc> <thu muc ket qua CryptoCheck> <tag_goc> <tag_da_va>

Doc <dir>/<tag>.<case>.{in,out,mem,dec}. Kiem:
  1. ban VA, moi truong hop: ban ma cua lib == save_crypto.encrypt(plain, key, r doc tu buf[n+1]);
  2. ban GOC, khoa rong + rac: ban ma cua lib == save_crypto voi --oob file:<dump bo nho luc ma hoa> (chung minh mo hinh
     "doc key[r+i] vuot buffer" dung tung byte);
  3. ban GOC, khoa khong rong / NULL: ban ma == ban VA (khong hoi quy), cung r;
  4. D_save0 (file that): plaintext lib ghi ra == plaintext save_crypto --key "" (ca hai ban).
Ma thoat 0 neu moi kiem tra dat.
"""
import importlib.util
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
spec = importlib.util.spec_from_file_location('save_crypto', os.path.join(HERE, 'save_crypto.py'))
sc = importlib.util.module_from_spec(spec)
spec.loader.exec_module(sc)

lib, d, tg, tp = sys.argv[1:5]
elf = sc.Elf(lib)
T = elf.read(sc.find_table(elf)[0], 256)
Z = sc.Oob('zero')
bad = 0


def rd(tag, case, ext):
    return open(os.path.join(d, '%s.%s.%s' % (tag, case, ext)), 'rb').read()


def check(name, ok, extra=''):
    global bad
    print('%-62s %s %s' % (name, 'DAT' if ok else 'KHONG DAT', extra))
    bad += not ok


KEYS = {'A_empty_zero': b'', 'B_empty_garbageG1': b'', 'C_key_394572': b'394572', 'C_key_a': b'a',
        'C_key_63': bytes(65 + i % 26 for i in range(63)), 'C_key_NULL123': b'123', 'C_small_n28': b'394572',
        'C_empty_n0': b''}

print('== 1. ban VA == save_crypto.py (keystream = T khi khoa rong; r doc tu buf[n+1])')
for case, key in KEYS.items():
    plain, out = rd(tp, case, 'in'), rd(tp, case, 'out')
    r = out[len(plain) + 1]
    want = sc.encrypt(plain, T, key, Z, r)
    check('%s (n=%d, r=%d, khoa %r)' % (case, len(plain), r, key if len(key) < 12 else key[:8] + b'..'), out == want)

print('== 2. ban GOC, khoa rong + rac: ban ma == save_crypto --oob file:<dump luc ma hoa>')
for case in ('A_empty_zero', 'B_empty_garbageG1'):
    plain, out, mem = rd(tg, case, 'in'), rd(tg, case, 'out'), rd(tg, case, 'mem')
    open(os.path.join(d, '_oob.bin'), 'wb').write(mem)
    oob = sc.Oob('file:' + os.path.join(d, '_oob.bin'))
    r = out[len(plain) + 1]
    check('%s: lib goc == mo hinh doc vuot buffer (r=%d)' % (case, r), out == sc.encrypt(plain, T, b'', oob, r))
    keystream_t = sc.encrypt(plain, T, b'', Z, r)
    print('      (so voi keystream T thuan: %s)' % ('GIONG' if out == keystream_t else 'KHAC - rac anh huong'))

print('== 3. khoa khong rong / NULL: lib GOC == lib VA (khong hoi quy)')
for case in ('C_key_394572', 'C_key_a', 'C_key_63', 'C_key_NULL123', 'C_small_n28', 'C_empty_n0', 'A_empty_zero'):
    check('%s' % case, rd(tg, case, 'out') == rd(tp, case, 'out'))

print('== 4. D_save0 (file that, giai ma that bai): plaintext lib ghi ra == save_crypto --key ""')
s0 = open(os.path.join(d, tp + '.D_save0.in'), 'rb').read()
res = sc.decrypt(s0, T, b'', Z)
for tag in (tg, tp):
    check('D_save0 [%s] byte plaintext == save_crypto' % tag, rd(tag, 'D_save0_key_empty', 'dec')[:res['n']] == res['p'])
print('== 5. thpcheckslot1 (khoa 394572): ca hai ban giai ma dung, plaintext == %r' % sc.KAT_PLAIN)
for tag in (tg, tp):
    check('D_thpcheckslot1 [%s]' % tag, rd(tag, 'D_thpcheckslot1', 'dec')[:len(sc.KAT_PLAIN)] == sc.KAT_PLAIN)
print('TONG KET:', 'TAT CA DAT' if not bad else '%d KIEM TRA KHONG DAT' % bad)
sys.exit(1 if bad else 0)
