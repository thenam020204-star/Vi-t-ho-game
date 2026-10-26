# -*- coding: utf-8 -*-
"""Chuoi CONSTANT_String co chu Han trong .class: liet ke / thay the trong constant pool.

  python3 -I classstr.py list <class_dir> <src_dir> <out.tsv>   # kem dong code CFR de xet ngu canh
  python3 -I classstr.py patch <class_dir> <in.tsv> <out_dir>    # thay Utf8 duoc CONSTANT_String tro toi
Khop theo chi so constant pool, sai thi theo gia tri chuoi goc (dung chung TSV cho N73/E62).
Chi thay Utf8 ma MOI tham chieu toi no deu la CONSTANT_String (khong dung lam ten/descriptor).
Doi do dai Utf8 khong anh huong bytecode (lenh chi tro chi so constant pool).
"""
import sys, os, struct, csv, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from binutf import mutf8_decode, mutf8_encode, is_cjk

def parse(b):
    n = struct.unpack('>H', b[8:10])[0]; p = 10; ents = [None] * n; i = 1
    while i < n:
        t = b[p]; st = p
        if t == 1:
            L = struct.unpack('>H', b[p + 1:p + 3])[0]; ents[i] = (1, st, p + 3 + L, b[p + 3:p + 3 + L]); p += 3 + L
        elif t in (3, 4): ents[i] = (t, st, p + 5, None); p += 5
        elif t in (5, 6): ents[i] = (t, st, p + 9, None); p += 9; i += 1
        elif t in (7, 8, 16): ents[i] = (t, st, p + 3, struct.unpack('>H', b[p + 1:p + 3])[0]); p += 3
        elif t in (9, 10, 11, 12): ents[i] = (t, st, p + 5, struct.unpack('>HH', b[p + 1:p + 5])); p += 5
        else: raise ValueError(t)
        i += 1
    return ents, p

def string_utf(b):
    ents, end = parse(b)
    refs = {}
    for e in ents:
        if not e: continue
        if e[0] in (7, 8, 16): refs.setdefault(e[3], set()).add(e[0])
        elif e[0] in (9, 10, 11, 12): refs.setdefault(e[3][0], set()).add(-1); refs.setdefault(e[3][1], set()).add(-1)
    out = {}
    for i, e in enumerate(ents):
        if e and e[0] == 1 and refs.get(i) == {8}:
            out[i] = mutf8_decode(e[3])
    return out

def cmd_list(cd, sd, outp):
    rows = []
    for fn in sorted(os.listdir(cd)):
        if not fn.endswith('.class'): continue
        b = open(os.path.join(cd, fn), 'rb').read(); cls = fn[:-6]
        src = os.path.join(sd, fn[:-6] + '.java')
        lines = open(src, encoding='utf-8', errors='replace').read().split('\n') if os.path.exists(src) else []
        for i, s in sorted(string_utf(b).items()):
            if not any(is_cjk(c) for c in s): continue
            esc = s.encode('unicode_escape').decode('ascii').replace('\\u', '\\u')
            esc_j = ''.join(c if ord(c) < 128 else '\\u%04x' % ord(c) for c in s)
            ctx = [l.strip() for l in lines if esc_j in l or s in l][:2]
            rows.append({'id': '%s#%d' % (cls, i), 'file': fn, 'key': str(i), 'goc': s, 'dich': '',
                         'ghichu': ' || '.join(c[:160] for c in ctx), 'gioihan': ''})
    with open(outp, 'w', encoding='utf-8', newline='') as fo:
        w = csv.DictWriter(fo, ['id', 'file', 'key', 'goc', 'dich', 'ghichu', 'gioihan'], delimiter='\t',
                           quoting=csv.QUOTE_NONE, escapechar='\\', lineterminator='\n')
        w.writeheader(); [w.writerow(r) for r in rows]
    print(len(rows), 'chuoi')

def cmd_patch(cd, tsvp, outd):
    with open(tsvp, encoding='utf-8', newline='') as fi:
        rows = [r for r in csv.DictReader(fi, delimiter='\t', quoting=csv.QUOTE_NONE, escapechar='\\') if r.get('dich')]
    by = {}
    for r in rows: by.setdefault(r['file'], []).append((int(r['key']), r['goc'], r['dich']))
    os.makedirs(outd, exist_ok=True)
    for fn, lst in sorted(by.items()):
        b = open(os.path.join(cd, fn), 'rb').read(); ents, end = parse(b); su = string_utf(b)
        # khoa = chi so constant pool cua ban lam TSV; ban khac (vd E62) chi so doi -> khop theo gia tri chuoi goc
        m = {}
        for k, goc, dich in lst:
            if su.get(k) != goc:
                hit = [i for i, v in su.items() if v == goc]
                if not hit:
                    print('  BO QUA %s: ban nay khong co chuoi %r' % (fn, goc)); continue
                if len(hit) != 1:
                    raise SystemExit('%s: chuoi goc %r khop %d vi tri' % (fn, goc, len(hit)))
                k = hit[0]
            assert k not in m, (fn, k)
            m[k] = (goc, dich)
        out = bytearray(b[:10]); p = 10
        for i, e in enumerate(ents):
            if not e: continue
            if i in m:
                assert i in su and su[i] == m[i][0], (fn, i)
                nb = mutf8_encode(m[i][1]); assert len(nb) <= 0xFFFF
                out += b[p:e[1]] + bytes([1]) + struct.pack('>H', len(nb)) + nb; p = e[2]
        out += b[p:]
        if not m: continue
        open(os.path.join(outd, fn), 'wb').write(bytes(out))
        print('%s: thay %d chuoi' % (fn, len(m)))

if __name__ == '__main__':
    {'list': cmd_list, 'patch': cmd_patch}[sys.argv[1]](*sys.argv[2:])
