#!/usr/bin/env python3
"""T9c: doi chieu ket qua chay unidbg (emu/view_check.sh) voi cong thuc cua nhom `view`/`hud`/`pool` (docs/tam_nhin_hud.md).

    python3 -I scripts/re/view_xcheck.py <thu muc ket qua cua emu/view_check.sh> [--title]

Doc <dir>/runs/*.log. Kiem:
  [1] kich thuoc FB (GRP_nDisplayW/H @0x2d7ab4/ab8) theo moi (man hinh, P): W' = floor(cfgW*P/100) & ~1, H' = floor(cfgH*P/100) khi 100 <= P <= 150, nguoc lai = cfg goc;
      man logic (0x2d6098/9c) khong doi theo P; kich thuoc that (0x2d6090/94) = kich thuoc may.
  [2] pool: tong = 10.485.760 B (lib va) / 8.388.608 B (lib goc); used(P) - used(goc) == 2*(W'^2 - W0^2) (MEM_Malloc(2*W*W) cua GRP_Initialize).
  [3] HUD: rect cua 21 control UIPlay_Create (goi truc tiep) doi dung +24/-24 ngang, +16/-16 doc theo bang, w/h giu nguyen.
  [4] (--title) anh man tieu de / chon save: lib va P=100, P=120 va lib goc giong tung pixel; tap START trung (anh sau tap la man chon save).
"""
import glob
import os
import re
import sys

ok_all = True


def verdict(name, ok, detail=''):
    global ok_all
    ok_all = ok_all and ok
    print('  -> %s: %s %s' % ('DAT' if ok else 'KHONG DAT', name, detail))


def cfg(W, H):
    if W < 480:
        return 480, 320
    if W > 799:
        return 512, H * 512 // W
    return 480, H * 480 // W


def logical(W, H):
    import numpy as np
    f = np.float32
    if f(W) / f(H) > f(1.5):
        s = f(640.0) / f(H)
        return int(s * f(W)), int(s * f(H))
    s = f(960.0) / f(W)
    return int(s * f(W)), int(s * f(H))


def parse_run(path):
    d = {}
    for l in open(path, errors='replace'):
        m = re.match(r'\[dump\] cuoi 0x([0-9a-f]+) = (.*)$', l)
        if m:
            d[int(m.group(1), 16)] = [int(x) for x in m.group(2).split()]
        m = re.match(r'\[pool\] cuoi used=(\d+) total=(\d+)', l)
        if m:
            d['used'], d['total'] = int(m.group(1)), int(m.group(2))
        m = re.match(r'\[pool\] DINH used=(\d+)', l)
        if m:
            d['peak'] = int(m.group(1))
    d['errs'] = sum(1 for l in open(path, errors='replace') if l.startswith('!!') or 'UC_ERR' in l)
    return d


def parse_hud(path):
    rects, ids, order = {}, {}, []
    for l in open(path, errors='replace'):
        m = re.match(r'\[hud\] rect obj=(0x[0-9a-f]+) x=(-?\d+) y=(-?\d+) w=(-?\d+) h=(-?\d+)', l)
        if m:
            rects[m.group(1)] = tuple(int(m.group(i)) for i in range(2, 6))
        m = re.match(r'\[hud\] id obj=(0x[0-9a-f]+) (-?\d+)', l)
        if m:
            ids[m.group(1)] = int(m.group(2))
            order.append(int(m.group(2)))
    return {ids[o]: r for o, r in rects.items() if o in ids}, order


# ID -> (dx, dy) mong doi
HUD_EXPECT = {1: (24, 0), 2: (24, 0), 3: (24, 0),
              101: (-24, 16), 102: (-24, 16), 103: (-24, 16),
              4: (0, 16),
              8: (-24, -16), 9: (-24, -16), 10: (-24, -16), 11: (-24, -16), 12: (-24, -16), 13: (-24, -16),
              7: (-24, -16), 20: (-24, -16),
              21: (24, -16), 22: (24, -16), 30: (24, -16), 31: (24, -16), 32: (24, -16), 33: (24, -16)}


def main(d, title):
    runs = {}
    for f in glob.glob(os.path.join(d, 'runs', '*.log')):
        runs[os.path.basename(f)[:-4]] = f
    print('thu muc', d, ':', len(runs), 'lan chay')

    print('\n[1] kich thuoc FB theo (man hinh, P) va man logic')
    sizes = sorted({re.match(r'p_(\d+x\d+)_', n).group(1) for n in runs if n.startswith('p_')}, key=lambda s: -int(s.split('x')[0]))
    rows = 0
    bad = 0
    for sz in sizes:
        W, H = (int(x) for x in sz.split('x'))
        c0 = cfg(W, H)
        lw, lh = logical(W, H)
        for P in ('unset', 100, 105, 110, 115, 120, 125, 130, 135, 140, 145, 150, 99, 151, 0, -1):
            n = 'p_%s_%s' % (sz, P)
            if n not in runs:
                continue
            r = parse_run(runs[n])
            p = 0 if P == 'unset' else P
            if 100 <= p <= 150:
                exp = ((c0[0] * p // 100) & ~1, c0[1] * p // 100)
            else:
                exp = c0
            got = tuple(r.get(0x2d7ab4, []))
            real = r.get(0x2d6090, [])
            ok = got == exp and real[:2] == [W, H] and real[2:4] == [lw, lh] and r['errs'] == 0
            rows += 1
            bad += not ok
            print('  %-10s P=%-6s cfg %dx%d -> FB mong doi %dx%d, do duoc %s ; that %s logic %s (mong doi %dx%d)%s' % (
                sz, P, c0[0], c0[1], exp[0], exp[1], list(got), real[:2], real[2:4], lw, lh, '' if ok else '   <== LOI'))
    verdict('%d ca (man hinh x P): FB = cong thuc, W chan, ngoai [100,150] = nhu goc, man logic va kich thuoc that khong doi' % rows, bad == 0 and rows > 0)
    r100 = parse_run(runs['p_2400x1080_100'])
    r120 = parse_run(runs['p_2400x1080_120'])
    r105 = parse_run(runs['p_2400x1080_105'])
    verdict('2400x1080: P = 100 -> 512/230; P = 120 -> 614/276; P = 105 -> 536/241 (W chan, khong phai 537)',
            r100[0x2d7ab4] == [512, 230] and r120[0x2d7ab4] == [614, 276] and r105[0x2d7ab4] == [536, 241])
    verdict('2400x1080: man logic = 1422/640 o moi P', all(parse_run(runs['p_2400x1080_%s' % P])[0x2d6090][2:4] == [1422, 640] for P in (100, 120, 150)))

    print('\n[2] pool MEM')
    tot_ok = used_ok = True
    for sz in sizes:
        W, H = (int(x) for x in sz.split('x'))
        c0 = cfg(W, H)
        base = parse_run(runs['p_%s_unset' % sz])
        tot_ok = tot_ok and base['total'] == 10485760
        for P in (100, 105, 120, 150):
            n = 'p_%s_%s' % (sz, P)
            if n not in runs:
                continue
            r = parse_run(runs[n])
            w1 = (c0[0] * P // 100) & ~1
            want = 2 * (w1 * w1 - c0[0] * c0[0])
            got = r['used'] - base['used']
            used_ok = used_ok and got == want
            print('  %-10s P=%-4s used %d (goc P=0: %d), chenh %d, mong doi 2*(%d^2-%d^2) = %d %s' % (sz, P, r['used'], base['used'], got, w1, c0[0], want, '' if got == want else '<== LOI'))
    verdict('pool tong = 10.485.760 B o moi lan chay (lib va)', tot_ok)
    verdict('used(P) - used(P goc) == 2*(W\'^2 - W0^2) (MEM_Malloc(2*W*W) cua GRP_Initialize), khop tung byte', used_ok)
    if 'base_orig' in runs:
        ro = parse_run(runs['base_orig'])
        verdict('lib goc (chua va): pool tong = 8.388.608 B, FB 512x230, man logic 1422x640', ro['total'] == 8388608 and ro[0x2d7ab4] == [512, 230] and ro[0x2d6090][2:4] == [1422, 640])
        p0 = parse_run(runs['p_2400x1080_unset'])
        verdict('lib va (P chua ghi) == lib goc ve used: %d == %d' % (p0['used'], ro['used']), p0['used'] == ro['used'])
    for n in sorted(runs):
        if n.startswith('title_') or n.startswith('deep_'):
            r = parse_run(runs[n])
            if 'peak' in r:
                print('  %s: dinh used = %d B (%.3f MB), total = %d' % (n, r['peak'], r['peak'] / 1048576.0, r.get('total', 0)))

    print('\n[3] HUD: rect that cua UIPlay_Create (goi truc tiep trong unidbg)')
    for tag in ('2400x1080', '960x640'):
        if 'hud_orig_%s' % tag not in runs:
            continue
        a, oa = parse_hud(runs['hud_orig_%s' % tag])
        b, ob = parse_hud(runs['hud_new_%s' % tag])
        bad = []
        for i, (dx, dy) in HUD_EXPECT.items():
            if i not in a or i not in b:
                bad.append((i, 'thieu'))
                continue
            ra, rb = a[i], b[i]
            if (rb[0] - ra[0], rb[1] - ra[1]) != (dx, dy) or ra[2:] != rb[2:]:
                bad.append((i, ra, rb))
        for i in sorted(HUD_EXPECT, key=lambda k: oa.index(k) if k in oa else 99):
            if i in a and i in b:
                print('  %-9s ID %3d goc %-22s moi %-22s d=(%+d,%+d)' % (tag, i, a[i], b[i], b[i][0] - a[i][0], b[i][1] - a[i][1]))
        verdict('%s: 21 control dich dung bang (ngang +-24, doc +-16), w/h khong doi, thu tu tao giong nhau' % tag, not bad and oa == ob and len(a) == len(b) == 21, str(bad) if bad else '')

    if title:
        print('\n[4] man tieu de / chon save (2400x1080): anh')
        from PIL import Image, ImageChops
        import numpy as np

        def diff(a, b):
            ia, ib = Image.open(a).convert('RGB'), Image.open(b).convert('RGB')
            return int((np.asarray(ImageChops.difference(ia, ib)).sum(axis=2) > 0).sum())
        for n in ('t610', 's640'):
            for x in ('title_P100', 'title_P120', 'title_orig'):
                if not os.path.exists(os.path.join(d, 'runs', x, n + '.png')):
                    print('  thieu', x, n)
        pairs = [('title_P100', 'title_P120'), ('title_orig', 'title_P100'), ('title_orig', 'title_P120')]
        for a, b in pairs:
            for n in ('t610', 's640', 's720'):
                pa, pb = os.path.join(d, 'runs', a, n + '.png'), os.path.join(d, 'runs', b, n + '.png')
                if os.path.exists(pa) and os.path.exists(pb):
                    k = diff(pa, pb)
                    print('  %-10s vs %-10s %s: %d px khac' % (a, b, n, k))
                    verdict('%s == %s (%s)' % (a, b, n), k == 0)
        # tap START: anh sau tap phai khac anh truoc tap (da vao man chon save)
        for x in ('title_P100', 'title_P120'):
            pa, pb = os.path.join(d, 'runs', x, 't610.png'), os.path.join(d, 'runs', x, 's640.png')
            if os.path.exists(pa) and os.path.exists(pb):
                k = diff(pa, pb)
                verdict('%s: tap START (240,277) @khung 620 doi man (t610 -> s640: %d px khac)' % (x, k), k > 100000)
    print('\nTONG KET:', 'TAT CA DAT' if ok_all else 'CO MUC KHONG DAT')
    return 0 if ok_all else 1


if __name__ == '__main__':
    a = [x for x in sys.argv[1:] if not x.startswith('--')]
    if len(a) != 1:
        print(__doc__)
        sys.exit(2)
    sys.exit(main(a[0], '--title' in sys.argv))
