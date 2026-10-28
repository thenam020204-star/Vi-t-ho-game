#!/usr/bin/env python3
"""Ghep TSV (dich/text_can_dich.tsv) lai thanh file resource/class.
Dung: repack.py SRC_DIR TSV OUT_DIR [--check] [--force] [--charset f12.bin]
  SRC_DIR  thu muc jar giai nen (work/x)         OUT_DIR  ghi pak/*.p, t/item3-7.t, <Lop>.class co hang Han
  --check  chi kiem tra (khong ghi)               --force  van ghi du co loi kiem tra
Cot 'dich' rong = giu nguyen goc -> ket qua giong tung byte file goc."""
import sys, os, glob, collections, re
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from hanlib import *

STATUS = ['麻痹', '禁锢', '冰冻', '燃烧', '石化']


def validate(rows, charset):
    err, warn = [], []
    byfile = collections.defaultdict(list)
    for r in rows:
        byfile[r['file']].append(r)
        g, d = r['goc'], r['dich']
        if not d:
            continue
        who = '%s %s' % (r['id'], r['file'])
        bad = sorted({c for c in d if c not in charset})
        if bad:
            err.append('%s: ky tu ngoai f12: %s' % (who, ''.join(bad)))
        if HAN.search(d):
            err.append('%s: con ky tu Han' % who)
        if r['file'].startswith('class:'):
            if d.count('^') != g.count('^'):
                err.append('%s: so dau ^ khac goc (%d vs %d)' % (who, d.count('^'), g.count('^')))
        else:
            for ch in '^|':
                if ch in d and ch not in g:
                    err.append('%s: dich chua "%s" (cam)' % (who, ch))
            if r['file'].startswith('pak/'):
                nt, ot = collections.Counter(TOK.findall(d)), collections.Counter(TOK.findall(g))
                extra = nt - ot
                if extra:
                    err.append('%s: token moi %s' % (who, sorted(extra)))
                if ot - nt:
                    warn.append('%s: mat token %s' % (who, sorted((ot - nt).elements())))
                if d.count('#') != sum(nt.values()):
                    err.append('%s: co "#" ngoai token' % who)
            elif '#' in d:
                err.append('%s: dich chua "#"' % who)
        if r['file'].startswith('class:') and '@' in g and d.count('@') != g.count('@'):
            warn.append('%s: so @ khac goc' % who)
        if r['gioi han'].strip().isdigit() and len(d) > int(r['gioi han']):
            warn.append('%s: dai %d > gioi han %s' % (who, len(d), r['gioi han']))
    # khoa dong nhat: trang thai (item3 cot 4 <-> hang class Main/j)
    for st in STATUS:
        vals = {r['dich'] for r in rows if r['goc'] == st and (r['file'] == 'class:Main/j' or (r['file'] == 't/item3.t' and r['khoa'].endswith('.4')))}
        if len(vals) > 1:
            err.append('trang thai %s dich khong dong nhat: %s' % (st, sorted(vals)))
    # "没有" prefix
    pre = [r['dich'] for r in rows if r['file'] == 'class:Main/j' and r['goc'] == '没有']
    if pre and pre[0]:
        for r in rows:
            if r['file'] == 'class:Main/j' and r['goc'] != '没有' and (r['goc'].startswith('没有物品') or r['goc'].startswith('没有法宝')):
                if not r['dich'].startswith(pre[0]):
                    err.append('%s: phai bat dau bang "%s" (hang indexOf)' % (r['id'], pre[0]))
    elif any(r['dich'] for r in rows if r['file'] == 'class:Main/j' and r['goc'].startswith('没有')):
        err.append('"没有" va 3 chuoi danh sach rong phai dich cung luc')
    return err, warn, byfile


def main():
    a = [x for x in sys.argv[1:] if not x.startswith('--')]
    src, tsv, out = a[0], a[1], (a[2] if len(a) > 2 else None)
    check = '--check' in sys.argv; force = '--force' in sys.argv
    cs = f12_charset(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'work/tools/fonts/f12.bin')) if '--charset' not in sys.argv \
        else f12_charset(sys.argv[sys.argv.index('--charset') + 1])
    charset = set(cs)
    rows = read_tsv(tsv)
    err, warn, byfile = validate(rows, charset)
    for w in warn[:30]:
        print('CANH BAO', w)
    if err:
        for e in err[:60]:
            print('LOI', e)
        print('%d loi, %d canh bao' % (len(err), len(warn)))
        if not force:
            sys.exit(1)
    if check:
        print('OK kiem tra: %d hang, %d canh bao' % (len(rows), len(warn))); return
    os.makedirs(out, exist_ok=True)
    nfile = 0; ntr = 0
    # ---- pak
    for f in sorted(glob.glob(src + '/pak/*.p')):
        rel = 'pak/' + os.path.basename(f)
        head, blobs = read_pak(open(f, 'rb').read())
        tr = {}
        for r in byfile.get(rel, []):
            nm, rest = r['khoa'].rsplit('/', 1); ci, fi = map(int, rest.split('.'))
            tr[(nm, ci, fi)] = r
        newb = []
        for name, blob in blobs:
            nm = name.decode('utf-8')
            cmds = split_script(blob.decode('utf-8'))
            for (n2, ci, fi), r in list(tr.items()):
                if n2 != nm:
                    continue
                if cmds[ci][fi] != r['goc']:
                    sys.exit('LOI %s %s: goc khong khop file (TSV cu?)' % (rel, r['khoa']))
                if r['dich']:
                    cmds[ci][fi] = r['dich']; ntr += 1
            newb.append((name, join_script(cmds).encode('utf-8')))
        os.makedirs(os.path.join(out, 'pak'), exist_ok=True)
        open(os.path.join(out, rel), 'wb').write(write_pak(head, newb)); nfile += 1
    # ---- item*.t
    os.makedirs(os.path.join(out, 't'), exist_ok=True)
    for k in range(3, 8):
        rel = 't/item%d.t' % k
        d = open(os.path.join(src, rel), 'rb').read()
        head, rr, cc = read_t(d)
        cells = read_t_cells(d, rr, cc)
        for r in byfile.get(rel, []):
            ro, co = map(int, r['khoa'].split('.')); i = ro * cc + co
            if cells[i] != r['goc']:
                sys.exit('LOI %s %s: goc khong khop' % (rel, r['khoa']))
            if r['dich']:
                cells[i] = r['dich']; ntr += 1
        open(os.path.join(out, rel), 'wb').write(write_t_cells(head, cells)); nfile += 1
    # ---- class
    for rel, rs in sorted(byfile.items()):
        if not rel.startswith('class:'):
            continue
        cls = rel[6:]
        d = open(os.path.join(src, cls + '.class'), 'rb').read()
        have = {s for _, s in class_han_utf8(d)}
        repl = {}
        for r in rs:
            if r['goc'] not in have:
                sys.exit('LOI %s: khong thay hang "%s"' % (rel, r['goc']))
            if r['dich']:
                repl[r['goc']] = r['dich']; ntr += 1
        os.makedirs(os.path.dirname(os.path.join(out, cls + '.class')), exist_ok=True)
        open(os.path.join(out, cls + '.class'), 'wb').write(rewrite_class(d, repl)); nfile += 1
    print('repack: %d file, %d hang da dich -> %s' % (nfile, ntr, out))


if __name__ == '__main__':
    main()
