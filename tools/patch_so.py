#!/usr/bin/env python3
"""Va byte libil2cpp.so (arm64) theo mods/patches.json.

patches.json: mang {id, rva, orig, new, method, reason}; rva = RVA (hex chuoi) trong libil2cpp.so,
orig/new = hex cung do dai. RVA -> offset file qua program header PT_LOAD (khong hard-code).

Dung:
  python3 tools/patch_so.py                       # ap len ban goc -> work/mod/libil2cpp.so
  python3 tools/patch_so.py --check [FILE]        # kiem FILE (mac dinh work/mod/libil2cpp.so) da co dung `new` o moi diem va
                                                  # chi khac ban goc o cac diem va (khong ghi gi)
  python3 tools/patch_so.py --check FILE --expect orig   # kiem FILE la ban goc (moi diem == orig)
  python3 tools/patch_so.py --revert [FILE] [-o OUT]     # tra `new` -> `orig` (kiem `new` truoc), mac dinh sua ra work/mod/libil2cpp.orig.so
Thoat != 0 neu byte khong khop. Khong doi kich thuoc file.
"""
import argparse, hashlib, json, os, struct, sys

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
DEF_IN = os.path.join(ROOT, 'work/il2/lib/arm64-v8a/libil2cpp.so')
DEF_OUT = os.path.join(ROOT, 'work/mod/libil2cpp.so')
DEF_PATCHES = os.path.join(ROOT, 'mods/patches.json')
PF_X = 1


def die(msg):
    print('LOI: ' + msg, file=sys.stderr)
    sys.exit(2)


def load_segments(data):
    if data[:4] != b'\x7fELF' or data[4] != 2 or data[5] != 1:
        die('khong phai ELF64 little-endian')
    e_machine = struct.unpack_from('<H', data, 0x12)[0]
    if e_machine != 183:
        die('khong phai AArch64 (e_machine=%d)' % e_machine)
    phoff = struct.unpack_from('<Q', data, 0x20)[0]
    phentsize, phnum = struct.unpack_from('<HH', data, 0x36)
    segs = []
    for i in range(phnum):
        t, fl, off, va, pa, fsz, msz, al = struct.unpack_from('<IIQQQQQQ', data, phoff + i * phentsize)
        if t == 1:  # PT_LOAD
            segs.append(dict(va=va, off=off, fsz=fsz, flags=fl))
    return segs


def rva2off(segs, rva, n):
    """RVA -> offset file; ca doan [rva, rva+n) phai nam tron trong phan file (filesz) cua mot PT_LOAD co quyen X."""
    for s in segs:
        if s['va'] <= rva and rva + n <= s['va'] + s['fsz']:
            if not s['flags'] & PF_X:
                die('RVA 0x%X nam trong PT_LOAD khong thuc thi' % rva)
            return s['off'] + rva - s['va']
    die('RVA 0x%X (+%d) khong nam tron trong PT_LOAD nao' % (rva, n))


def load_patches(path):
    ps = json.load(open(path, encoding='utf-8'))
    out, seen = [], set()
    for p in ps:
        for k in ('id', 'rva', 'orig', 'new', 'method', 'reason'):
            if k not in p:
                die('patch %s thieu truong %s' % (p.get('id'), k))
        if p['id'] in seen:
            die('trung id ' + p['id'])
        seen.add(p['id'])
        q = dict(p)
        q['rva_i'] = int(p['rva'], 16)
        q['orig_b'] = bytes.fromhex(p['orig'])
        q['new_b'] = bytes.fromhex(p['new'])
        if len(q['orig_b']) != len(q['new_b']) or not q['orig_b'] or len(q['orig_b']) % 4:
            die('patch %s: orig/new phai cung do dai, boi so cua 4 byte' % p['id'])
        if q['rva_i'] % 4:
            die('patch %s: RVA khong can 4' % p['id'])
        out.append(q)
    rs = sorted(out, key=lambda x: x['rva_i'])
    for a, b in zip(rs, rs[1:]):
        if a['rva_i'] + len(a['orig_b']) > b['rva_i']:
            die('patch %s va %s chong lan' % (a['id'], b['id']))
    return out


def sha(b):
    return hashlib.sha256(b).hexdigest()


def state(data, segs, p):
    o = rva2off(segs, p['rva_i'], len(p['orig_b']))
    cur = data[o:o + len(p['orig_b'])]
    return o, cur, ('orig' if cur == p['orig_b'] else 'new' if cur == p['new_b'] else 'other')


def main():
    ap = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    ap.add_argument('--patches', default=DEF_PATCHES)
    ap.add_argument('--in', dest='inp', default=DEF_IN, help='libil2cpp.so goc (che do ap dung)')
    ap.add_argument('-o', '--out', default=None)
    ap.add_argument('--check', nargs='?', const=DEF_OUT, metavar='FILE')
    ap.add_argument('--expect', choices=('new', 'orig'), default='new')
    ap.add_argument('--revert', nargs='?', const=DEF_OUT, metavar='FILE')
    a = ap.parse_args()
    ps = load_patches(a.patches)

    if a.check is not None:
        data = open(a.check, 'rb').read()
        segs = load_segments(data)
        bad = 0
        for p in ps:
            o, cur, st = state(data, segs, p)
            ok = st == a.expect
            bad += not ok
            print('%s %-40s rva=%s off=0x%X %s (mong doi %s)' % ('OK ' if ok else 'LOI', p['id'], p['rva'], o, st, a.expect))
        print('sha256', sha(data), len(data), 'B;', len(ps), 'va;', 'loi:', bad)
        sys.exit(1 if bad else 0)

    if a.revert is not None:
        src = a.revert
        out = a.out or os.path.join(ROOT, 'work/mod/libil2cpp.orig.so')
        data = bytearray(open(src, 'rb').read())
        segs = load_segments(data)
        for p in ps:
            o, cur, st = state(data, segs, p)
            if st == 'other':
                die('%s: byte tai 0x%X = %s khac ca orig lan new, khong tra ve' % (p['id'], o, cur.hex()))
            data[o:o + len(p['orig_b'])] = p['orig_b']
        os.makedirs(os.path.dirname(out), exist_ok=True)
        open(out, 'wb').write(data)
        print('da tra %d diem -> %s sha256 %s' % (len(ps), out, sha(bytes(data))))
        return

    # ap dung
    out = a.out or DEF_OUT
    data = bytearray(open(a.inp, 'rb').read())
    segs = load_segments(data)
    orig_sha = sha(bytes(data))
    for p in ps:
        o, cur, st = state(data, segs, p)
        if st != 'orig':
            die('%s: byte goc tai RVA %s (off 0x%X) = %s, mong doi %s (%s). Dung nham phien ban/da va roi?' %
                (p['id'], p['rva'], o, cur.hex(), p['orig'], st))
    for p in ps:
        o = rva2off(segs, p['rva_i'], len(p['orig_b']))
        data[o:o + len(p['new_b'])] = p['new_b']
        print('va %-40s %s off=0x%X %s -> %s' % (p['id'], p['rva'], o, p['orig'], p['new']))
    os.makedirs(os.path.dirname(out), exist_ok=True)
    tmp = out + '.tmp'
    open(tmp, 'wb').write(data)
    os.replace(tmp, out)
    print('goc  sha256', orig_sha)
    print('vá   sha256', sha(bytes(data)), '->', out, '(%d byte, %d diem va)' % (len(data), len(ps)))


if __name__ == '__main__':
    main()
