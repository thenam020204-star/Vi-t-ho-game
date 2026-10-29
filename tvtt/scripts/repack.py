#!/usr/bin/env python3
"""Ghi ban dich vao resource/class cua MOT ban jar.
Dung: repack.py X_DIR TSV OUT_DIR [--check]
  X_DIR  jar goc da giai nen; OUT_DIR nhan data/*.data va src/*.class co thay doi (cung duong dan tuong doi).
  Anh xa theo noi dung: goc -> dich (dong co cot dich rong = giu nguyen).
  --check: chi kiem (charset f12 + '：', ma $n, so '|', dau ： cua thoai, ten khoa dong nhat), khong ghi."""
import sys, os, glob, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from hanlib import HAN, read_tsv, rewrite_class, class_han_utf8, f12_charset
from datafmt import D_FILES, read_d, write_d, read_e, write_e
from extract import KEY_NAMES

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
EXTRA_OK = set('：')     # giu lam dau tach ten nguoi noi; VH ve thanh ':'


def validate(rows):
    cs = set(f12_charset(os.path.join(ROOT, 'tools', 'fonts', 'f12.bin'))) | EXTRA_OK
    err = []; tr = {}
    for r in rows:
        g, d = r['goc'], r['dich']
        if not d:
            continue
        if tr.get(g, d) != d:
            err.append('%s: cung goc, ban dich khac nhau' % r['id'])
        tr[g] = d
        bad = sorted({c for c in d if c not in cs})
        if bad:
            err.append('%s: ky tu ngoai f12: %s' % (r['id'], ''.join(bad)))
        if re.findall(r'\$\d', g) != re.findall(r'\$\d', d):
            err.append('%s: ma mau $n lech' % r['id'])
        if g.count('|') != d.count('|'):
            err.append('%s: so | lech' % r['id'])
        if d.count('$') != len(re.findall(r'\$\d', d)):
            err.append('%s: $ le khong kem so' % r['id'])
        if '：' in g and g.index('：') <= 8:
            if '：' not in d:
                err.append('%s: thoai mat dau ：' % r['id'])
    # ten khoa: ten trong thoai "Ten：" phai trung ban dich cua ten khoa
    for name in KEY_NAMES:
        if name not in tr:
            continue
        for r in rows:
            g, d = r['goc'], r['dich']
            if d and g.startswith(name + '：') and not d.startswith(tr[name] + '：'):
                err.append('%s: ten nguoi noi "%s" phai dich la "%s"' % (r['id'], name, tr[name]))
    return err, tr


def main():
    a = [x for x in sys.argv[1:] if not x.startswith('--')]
    X, tsv = a[0], a[1]
    rows = read_tsv(tsv)
    err, tr = validate(rows)
    for e in err:
        print('LOI', e)
    if '--check' in sys.argv:
        print('check:', 'OK' if not err else '%d loi' % len(err), '| so ban dich', len(tr))
        sys.exit(1 if err else 0)
    if err:
        sys.exit('repack dung: con loi')
    out = a[2]; nf = 0; miss = set()
    def put(rel, data, orig):
        nonlocal nf
        if data != orig:
            p = os.path.join(out, rel); os.makedirs(os.path.dirname(p), exist_ok=True)
            open(p, 'wb').write(data); nf += 1
    for f in D_FILES:
        rel = 'data/%s.data' % f; o = open(os.path.join(X, rel), 'rb').read()
        r, c, cells = read_d(o)
        for s in cells:
            if HAN.search(s) and s not in tr: miss.add(s)
        put(rel, write_d(r, c, [tr.get(s, s) for s in cells]), o)
    for p in sorted(glob.glob(os.path.join(X, 'data', 'e*.data'))):
        if os.path.basename(p) == 'eff.data':
            continue
        rel = os.path.relpath(p, X); o = open(p, 'rb').read()
        ss, tail, q = read_e(o)
        for s in ss:
            if HAN.search(s) and s not in tr: miss.add(s)
        put(rel, write_e([tr.get(s, s) for s in ss], tail), o)
    for p in sorted(glob.glob(os.path.join(X, 'src', '*.class'))):
        rel = os.path.relpath(p, X); o = open(p, 'rb').read()
        hs = {s for _, s in class_han_utf8(o)}
        miss |= {s for s in hs if s not in tr}
        put(rel, rewrite_class(o, {s: tr[s] for s in hs if s in tr}), o)
    print('repack: %d file doi, %d chuoi Han chua dich' % (nf, len(miss)))


if __name__ == '__main__':
    main()
