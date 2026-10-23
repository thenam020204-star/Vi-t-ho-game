"""Tach dau khoi chu Viet trong f18: tru bitmap than chu -> bitmap dau + vi tri.
Xuat docs/f18_marks.md va docs/f18_marks.json.   python3 -I tools/marks_from_f18.py"""
import sys, os, json, unicodedata
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from f18 import F18

ROOT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..')
COMB = {0x300: 'grave', 0x301: 'acute', 0x303: 'tilde', 0x309: 'hook', 0x323: 'dotbelow',
        0x302: 'circumflex', 0x306: 'breve', 0x31B: 'horn'}
TONES = ('acute', 'grave', 'hook', 'tilde')
VIET = [c for c in F18().charset if ord(c) > 0x7F and c not in '…‚' and c != 'đ' and c != 'Đ']


def marks_of(ch):
    d = unicodedata.normalize('NFD', ch)
    return d[0], [COMB[ord(c)] for c in d[1:]]


def components(pix):
    pix = set(pix)
    out = []
    while pix:
        s = pix.pop(); st = [s]; comp = [s]
        while st:
            y, x = st.pop()
            for dy in (-1, 0, 1):
                for dx in (-1, 0, 1):
                    q = (y + dy, x + dx)
                    if q in pix:
                        pix.remove(q); st.append(q); comp.append(q)
        out.append(sorted(comp))
    return sorted(out, key=lambda c: min(p[0] for p in c))


def bbox(pts):
    ys = [p[0] for p in pts]; xs = [p[1] for p in pts]
    return min(ys), max(ys), min(xs), max(xs)


def body_of(f, base, glyph):
    """Than chu dung de tru: i -> i bo cham (ı); con lai la chu goc."""
    g = [r[:] for r in f.glyph(base)]
    if base == 'i':
        for y in range(18):
            if sum(g[y]) and y < 8:
                g[y] = [0] * len(g[y])
    return g


def ascii_of(pts, y0=None, y1=None, x0=None, x1=None):
    b = bbox(pts)
    y0 = b[0] if y0 is None else y0; y1 = b[1] if y1 is None else y1
    x0 = b[2] if x0 is None else x0; x1 = b[3] if x1 is None else x1
    s = set(pts)
    return ['' .join('#' if (y, x) in s else '.' for x in range(x0, x1 + 1)) for y in range(y0, y1 + 1)]


def analyse():
    f = F18()
    res = {}
    for ch in VIET:
        base, ms = marks_of(ch)
        g = f.glyph(ch); b = body_of(f, base, g)
        w = len(g[0])
        extra = [(y, x) for y in range(18) for x in range(w) if g[y][x] and not b[y][x]]
        lost = [(y, x) for y in range(18) for x in range(w) if not g[y][x] and b[y][x]]
        bb = bbox([(y, x) for y in range(18) for x in range(w) if b[y][x]])  # y0,y1,x0,x1 than
        comps = components(extra)
        res[ch] = dict(base=base, marks=ms, width=w, body_bbox=bb, lost=len(lost), comps=comps)
    return f, res


def assign(info):
    """Gan tung thanh phan dau cho ten dau. Tra ve list (ten, pts)."""
    ms = list(info['marks']); comps = list(info['comps'])
    out = []
    bb = info['body_bbox']
    # dot below / horn tach bang vi tri
    for m in ('dotbelow', 'horn'):
        if m in ms:
            if m == 'dotbelow':
                c = max(comps, key=lambda c: min(p[0] for p in c))
            else:
                c = max(comps, key=lambda c: max(p[1] for p in c))
            comps.remove(c); ms.remove(m); out.append((m, c))
    # con lai: tren xuong duoi: tone tren cung, shape (circ/breve) duoi
    shapes = [m for m in ms if m in ('circumflex', 'breve')]
    tones = [m for m in ms if m in TONES]
    order = tones + shapes   # tu tren xuong
    if len(order) == 2 and len(comps) == 1:   # dau chong cham nhau: dau mu/trang (cao 2 hang) o duoi
        c = comps[0]; yb = max(p[0] for p in c)
        comps = [[p for p in c if p[0] < yb - 1], [p for p in c if p[0] >= yb - 1]]
    assert len(order) == len(comps), (info, order, comps)
    for m, c in zip(order, comps):
        out.append((m, c))
    return out


def main():
    f, res = analyse()
    lines = ['# f18.bin - dau tach tu chu Viet', '',
             'Luoi 18 hang (0 = tren cung). Than chu: chu thuong x-height = hang 9-14, chu hoa (cap) = hang 5-14, '
             'duoi = hang 15-17. Dau = bitmap f18 tru bitmap chu goc (i dung than khong cham). '
             '`gap` = so hang trong giua day dau va dinh than (0 = sat). `dx` = lech tam dau so voi tam bbox than, don vi pixel (+ = phai).', '']
    js = {}
    # 1. bang theo (loai dau, ngu canh)
    stats = {}
    for ch, info in res.items():
        parts = assign(info)
        up = ch.isupper()
        body_top = info['body_bbox'][0]; body_bot = info['body_bbox'][1]
        cx = (info['body_bbox'][2] + info['body_bbox'][3]) / 2
        ctx_marks = [m for m, _ in parts]
        rec = []
        for m, pts in parts:
            y0, y1, x0, x1 = bbox(pts)
            rec.append(dict(mark=m, y0=y0, y1=y1, x0=x0, x1=x1, h=y1 - y0 + 1, w=x1 - x0 + 1,
                            gap=(body_top - y1 - 1) if y1 < body_top else (y0 - body_bot - 1),
                            dx=(x0 + x1) / 2 - cx, ascii=ascii_of(pts)))
        js[ch] = dict(base=info['base'], marks=info['marks'], body_bbox=info['body_bbox'], width=info['width'],
                      lost=info['lost'], parts=rec)
        for r in rec:
            key = (r['mark'], 'upper' if up else 'lower', '+'.join(m for m in ctx_marks if m != r['mark']) or '-')
            stats.setdefault(key, []).append((ch, r))
    order = ['acute', 'grave', 'hook', 'tilde', 'dotbelow', 'circumflex', 'breve', 'horn']
    lines += ['## 1. Tong hop theo loai dau', '',
              '| dau | chu | co dau kem | hinh (ASCII) | y0-y1 | h | gap | dx min..max | ky tu mau |', '|---|---|---|---|---|---|---|---|---|']
    for m in order:
        for key in sorted(k for k in stats if k[0] == m):
            items = stats[key]
            ch0, r0 = items[0]
            shapes = {tuple(r['ascii']) for _, r in items}
            tops = sorted({r['y0'] for _, r in items}); bots = sorted({r['y1'] for _, r in items})
            gaps = sorted({r['gap'] for _, r in items}); dxs = [r['dx'] for _, r in items]
            lines.append('| %s | %s | %s | `%s` (%d dang) | %s-%s | %d | %s | %.1f..%.1f | %s |' % (
                m, key[1], key[2], ' / '.join(r0['ascii']), len(shapes),
                '/'.join(map(str, tops)), '/'.join(map(str, bots)), r0['h'], '/'.join(map(str, gaps)),
                min(dxs), max(dxs), ''.join(c for c, _ in items[:8])))
    # 2. ASCII tung dau (chu thuong, dau don) tren than 'a'/'o'... : hien thi glyph day du
    lines += ['', '## 2. ASCII tung loai dau (hinh chuan, lay tu chu mau)', '']
    for m in order:
        for ctx in ('lower', 'upper'):
            cands = [(c, r) for (k, v) in stats.items() if k[0] == m and k[1] == ctx and k[2] == '-' for c, r in v]
            if not cands:
                continue
            c, r = cands[0]
            lines += ['**%s / %s** (mau `%s`, hang %d-%d):' % (m, ctx, c, r['y0'], r['y1']), '```'] + r['ascii'] + ['```']
    # 3. ASCII day du cac chu mau
    lines += ['', '## 3. Glyph f18 day du, cac chu mau', '']
    for ch in 'áàảãạâấầẩẫậăắằẳẵặêếềểễệôốồổỗộơớờởỡợưứừửữựíìỉĩịÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶÊẾỀỂỄỆÔỐỒỔỖỘƠỚỜỞỠỢƯỨỪỬỮỰÍÌỈĨỊÝỲỶỸỴ':
        lines += ['`%s` (w=%d)' % (ch, f.width(ch)), '```', f.ascii(ch), '```']
    # 4. bang tung ky tu
    lines += ['', '## 4. Bang tung ky tu (134)', '',
              '| ky tu | than | dau (tren xuong duoi) | y0-y1 | gap | dx | than top-bot |', '|---|---|---|---|---|---|---|']
    for ch, d in js.items():
        desc = '; '.join(p['mark'] for p in d['parts'])
        lines.append('| %s | %s | %s | %s | %s | %s | %d-%d |' % (
            ch, d['base'], desc, ' ; '.join('%d-%d' % (p['y0'], p['y1']) for p in d['parts']),
            ' ; '.join(str(p['gap']) for p in d['parts']), ' ; '.join('%.1f' % p['dx'] for p in d['parts']),
            d['body_bbox'][0], d['body_bbox'][1]))
    lost = [ch for ch, d in js.items() if d['lost']]
    lines += ['', 'Ky tu ma f18 doi them pixel than (khong chi them dau): %s' % (''.join(lost) or 'khong co'), '',
              'Ghi chu: `Đ` va `đ` co than rieng trong f18 (khong phai dau tach roi) nen khong nam trong 134 ky tu o day; '
              'trong font Jeju `Đ` dung than `Ð` (U+00D0), `đ` da co san.']
    open(os.path.join(ROOT, 'docs', 'f18_marks.md'), 'w').write('\n'.join(lines) + '\n')
    json.dump(js, open(os.path.join(ROOT, 'docs', 'f18_marks.json'), 'w'), ensure_ascii=False, indent=1)
    print('chu Viet:', len(VIET), '(+ Đ = 134 voi đ khong tinh?)')
    print('lost:', ''.join(lost))


if __name__ == '__main__':
    main()
