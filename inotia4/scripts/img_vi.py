#!/usr/bin/env python3
"""Ve lai nhan chu tieng Anh trong atlas UI bang font f18 (bo NAM_P pixeltext), phong nguyen lan.

    img_vi.py <cau_hinh.json> <thu_muc_png_goc> <thu_muc_ra> [--preview anh.png]

Cau hinh: {"ANH.png": [ {"r":[x0,y0,x1,y1], "t":"CHỮ", ...tuy chon}, ... ], ...}
      hoac   {"ANH.png": {"groups": [[[x0,y0,x1,y1], ...], ...], "labels": [...]}}
  post    [{"from","to","clear","glow"}] xem glow_copy
  holes   [[x0,y0,x1,y1],...] vung khong lay lam nen mau (icon trong nut)
  groups  cac nhom nut cung kieu/cung kich thuoc: nen sach = gia tri trung nhieu nhat (xem clean_template);
          chu nam trong nut thuoc nhom thi xoa bang nen sach thay vi noi suy.
  r      vung (rong tay) chua chu goc, nam TRONG lung nut (khong cham vien nut)
  t      chu moi ("" = chi xoa)
  scale  he so phong font (mac dinh 2: atlas ve o 2x cua man 480x320)
  size   co font pixeltext (mac dinh 18)
  color  mau than chu "#rrggbb" (mac dinh: mau sang pho bien nhat cua chu goc)
  outline "#rrggbb" | "auto" | null  vien 1 diem font (= scale px); auto = mau toi nhat sat chu goc
  cx, cy tam dat chu (mac dinh tam khung chu goc)
  rot    0 | -90 | 90  xoay chu (tab doc)
  grad   ["#tren", "#duoi"]  to mau gradient doc thay cho color
  glow   ["#rrggbbaa", ban_kinh]  quang sang mem quanh chu (chu phat sang tren nen trong suot)
  diag   vien ca 8 huong (quang sang day hon)
  mode, gap  che do gian chu cua pixeltext (metric|tight|fixed), khoang cach cho tight
  fill   "#rrggbb" xoa bang mot mau phang thay vi noi suy theo hang
  tb     [x0,y0,x1,y1] khung chu goc do tay (bo qua do tu dong; van do mau trong khung)
  thr    nguong lech mau de nhan dien chu goc (mac dinh 48)

Cach xoa: moi hang trong khung chu (da no them 2px) noi suy tuyen tinh giua diem ben trai va ben phai
khung -> giu gradient doc va bong ngang cua nut. Chu moi duoc dat sao cho tam khung muc cua no trung
tam khung chu goc. Bao loi neu chu moi rong hon vung r.
"""
import json, os, sys
from collections import Counter
from PIL import Image

KIT = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'tools', 'NAM_P', 'pixeltext_kit')
sys.path.insert(0, KIT)
import pixeltext as pt  # noqa: E402


def hexc(s):
    s = s.lstrip('#')
    if len(s) == 8: return tuple(int(s[i:i + 2], 16) for i in (0, 2, 4, 6))
    return tuple(int(s[i:i + 2], 16) for i in (0, 2, 4)) + (255,)


def diff(a, b):
    return max(abs(a[0] - b[0]), abs(a[1] - b[1]), abs(a[2] - b[2]), abs(a[3] - b[3]))


def find_text(px, r, thr):
    x0, y0, x1, y1 = r
    mask = set()
    for y in range(y0, y1):
        row = [px[x, y] for x in range(x0, x1)]
        med = tuple(sorted(c[i] for c in row)[len(row) // 2] for i in range(4))
        for x in range(x0, x1):
            if diff(px[x, y], med) > thr:
                mask.add((x, y))
    if not mask:
        raise SystemExit('khong thay chu trong vung %s' % (r,))
    xs = [p[0] for p in mask]; ys = [p[1] for p in mask]
    return mask, (min(xs), min(ys), max(xs) + 1, max(ys) + 1)


def clean_template(px, rects, holes, mirror=True):
    """Nen sach cua mot kieu nut (cac nut cung kich thuoc, cung hinh).
    Tai moi vi tri: lay trung vi tu cac nut (va ban lat guong ngang) ma vi tri do KHONG nam trong khung
    chu cua nut do (holes). Neu moi nut deu co chu o do (giua nut): lay cot sach gan nhat cung hang -
    phan giua nut phang theo chieu ngang."""
    w = rects[0][2] - rects[0][0]; h = rects[0][3] - rects[0][1]
    for r in rects:
        if (r[2] - r[0], r[3] - r[1]) != (w, h):
            raise SystemExit('nhom nut lech kich thuoc: %s' % (rects,))
    def inhole(r, x, y):
        X, Y = r[0] + x, r[1] + y
        return any(hb[0] <= X < hb[2] and hb[1] <= Y < hb[3] for hb in holes)
    tpl = {}
    for y in range(h):
        row = {}
        for x in range(w):
            c = []
            for r in rects:
                if not inhole(r, x, y): c.append(px[r[0] + x, r[1] + y])
                xm = w - 1 - x
                if mirror and not inhole(r, xm, y): c.append(px[r[0] + xm, r[1] + y])
            if c:
                row[x] = tuple(sorted(v[i] for v in c)[len(c) // 2] for i in range(4))
        for x in range(w):
            if x not in row:
                if not row: raise SystemExit('khong dung duoc nen cho hang %d' % y)
                xn = min(row, key=lambda k: abs(k - x))
                row[x] = row[xn]
            tpl[x, y] = row[x]
    return tpl


def erase(px, box, fill=None, tpl=None):
    bx0, by0, bx1, by1 = box
    if tpl:
        t, (rx, ry) = tpl
        for y in range(by0, by1):
            for x in range(bx0, bx1):
                px[x, y] = t[x - rx, y - ry]
        return
    for y in range(by0, by1):
        L = px[bx0 - 1, y]; R = px[bx1, y]
        n = bx1 - bx0 + 1
        for x in range(bx0, bx1):
            if fill:
                px[x, y] = fill
            else:
                t = (x - bx0 + 1) / n
                px[x, y] = tuple(int(round(L[i] + (R[i] - L[i]) * t)) for i in range(4))


def render(text, size, scale, color, outline, rot, mode='metric', gap=1, diag=False, grad=None):
    img = pt.render(text, size=size, mode=mode, gap=gap, scale=1, fill=grad or color, outline=outline, outline_diag=diag, pad=1)
    if scale > 1:
        img = img.resize((img.width * scale, img.height * scale), Image.NEAREST)
    if rot:
        img = img.rotate(rot, expand=True)
    bb = img.getbbox()
    f = pt.Font.load(size)
    cap = (f.baseline - f.cap_height + 2) * scale, (f.baseline + 2) * scale  # dai than chu hoa (+1 vi pad=1)
    return img.crop(bb), (cap[0] - bb[1], cap[1] - bb[1]) if not rot else None


def label(im, src, e, tpls=()):
    px = im.load()
    r = e['r']
    scale = e.get('scale', 2); size = e.get('size', 18); rot = e.get('rot', 0)
    if 'tb' in e:  # khung chu do tay (chinh xac hon do tu dong khi nut co dai sang)
        tb = tuple(e['tb'])
        mask, _ = find_text(px, tb, e.get('thr', 48))
    else:
        mask, tb = find_text(px, r, e.get('thr', 48))
    if 'color' in e:
        color = hexc(e['color'])
    else:
        lum = sorted(mask, key=lambda p: -sum(src[p][:3]))
        top = lum[:max(1, len(lum) // 3)]
        color = Counter(tuple(c // 8 * 8 for c in src[p][:3]) for p in top).most_common(1)[0][0] + (255,)
    ol = e.get('outline')
    if ol == 'auto':
        dark = sorted(mask, key=lambda p: sum(src[p][:3]))[:max(1, len(mask) // 10)]
        ol = Counter(tuple(c // 8 * 8 for c in src[p][:3]) for p in dark).most_common(1)[0][0] + (255,)
    elif ol:
        ol = hexc(ol)
    pad = e.get('pad', 2)
    box = (max(r[0], tb[0] - pad), max(r[1], tb[1] - pad), min(r[2], tb[2] + pad), min(r[3], tb[3] + pad))
    tp = None
    for rects, t in tpls:
        for rr in rects:
            if rr[0] <= box[0] and rr[1] <= box[1] and box[2] <= rr[2] and box[3] <= rr[3]:
                tp = (t, (rr[0], rr[1]))
    erase(px, box, hexc(e['fill']) if 'fill' in e else None, tp)
    if not e.get('t'):
        return dict(box=box)
    g, capb = render(e['t'], size, scale, color, ol, rot, e.get('mode', 'metric'), e.get('gap', 1), e.get('diag', False), e.get('grad'))
    cx = (tb[0] + tb[2]) / 2; cy = (tb[1] + tb[3]) / 2
    if 'cy' in e: cy = e['cy']
    if 'cx' in e: cx = e['cx']
    x = int(round(cx - g.width / 2))
    # canh doc theo than chu hoa (dau tieng Viet nho len tren), khong theo ca khoi muc
    y = int(round(cy - (capb[0] + capb[1]) / 2)) if capb else int(round(cy - g.height / 2))
    if x < r[0] or x + g.width > r[2] or y < r[1] - 4 or y + g.height > r[3] + 4:
        raise SystemExit('TRAN: "%s" %dx%d tai (%d,%d) khong lot vung %s' % (e['t'], g.width, g.height, x, y, r))
    if e.get('glow'):  # quang sang mem: lam mo kenh alpha cua chu, to mau glow, dat duoi chu
        from PIL import ImageFilter
        gc, rad = hexc(e['glow'][0]), e['glow'][1]
        P = rad * 3
        a = Image.new('L', (g.width + 2 * P, g.height + 2 * P), 0)
        a.paste(g.getchannel('A'), (P, P))
        a = a.filter(ImageFilter.GaussianBlur(rad)).point(lambda v: min(255, v * gc[3] // 128))
        halo = Image.new('RGBA', a.size, gc[:3] + (0,)); halo.putalpha(a)
        im.alpha_composite(halo, (x - P, y - P))
    im.alpha_composite(g, (x, y))
    return dict(box=box, at=(x, y, g.width, g.height), color=color[:3], outline=ol[:3] if ol else None)


def glow_copy(im, op):
    """post: {"from":[x0,y0,x1,y1], "to":[x,y], "clear":[x0,y0,x1,y1], "glow":["#rrggbbaa", r]}
    Xoa vung clear (trong suot), chep vung from (da Viet hoa) sang to, them quang sang mem.
    Dung cho ban "dang chon/phat sang" cua mot nut ma ban thuong da duoc ve lai."""
    from PIL import ImageFilter
    x0, y0, x1, y1 = op['clear']
    im.paste(Image.new('RGBA', (x1 - x0, y1 - y0), (0, 0, 0, 0)), (x0, y0))
    src = im.crop(tuple(op['from']))
    gc, rad = hexc(op['glow'][0]), op['glow'][1]
    P = rad * 3
    a = Image.new('L', (src.width + 2 * P, src.height + 2 * P), 0)
    a.paste(src.getchannel('A'), (P, P))
    a = a.filter(ImageFilter.GaussianBlur(rad)).point(lambda v: min(255, v * gc[3] // 128))
    halo = Image.new('RGBA', a.size, gc[:3] + (0,)); halo.putalpha(a)
    tx, ty = op['to']
    layer = Image.new('RGBA', im.size, (0, 0, 0, 0))
    layer.alpha_composite(halo, (tx - P, ty - P)); layer.alpha_composite(src, (tx, ty))
    box = (x0, y0, x1, y1)
    im.alpha_composite(layer.crop(box), (x0, y0))


def main(cfg, src_dir, out_dir, preview=None):
    conf = json.load(open(cfg, encoding='utf-8'))
    os.makedirs(out_dir, exist_ok=True)
    shots = []
    for name, labels in conf.items():
        if name.startswith('_'):
            continue
        orig = Image.open(os.path.join(src_dir, name)).convert('RGBA')
        im = orig.copy(); src = orig.load()
        groups = []
        if isinstance(labels, dict):
            labels_ = labels['labels']
            holes = [tuple(h) for h in labels.get('holes', [])]
            for e in labels_:
                tb = e.get('tb') or e['r']; pd = e.get('pad', 2) + 1
                holes.append((tb[0] - pd, tb[1] - pd, tb[2] + pd, tb[3] + pd))
            groups = [(g, clean_template(src, g, holes, labels.get('mirror', True))) for g in labels.get('groups', [])]
            labels = labels_
        for e in labels:
            info = label(im, src, e, groups)
            print('%-28s %-14s %s' % (name, e.get('t', ''), info))
        for op in (conf[name].get('post', []) if isinstance(conf[name], dict) else []):
            glow_copy(im, op)
        im.save(os.path.join(out_dir, name))
        shots.append((name, orig, im))
    if preview:
        W = sum(max(o.width, 1) for _, o, _ in shots[:1])
        tiles = []
        for name, o, n in shots:
            t = Image.new('RGBA', (o.width * 2 + 8, o.height), (40, 40, 70, 255))
            t.alpha_composite(o, (0, 0)); t.alpha_composite(n, (o.width + 8, 0))
            tiles.append(t)
        H = sum(t.height + 8 for t in tiles); Wm = max(t.width for t in tiles)
        sheet = Image.new('RGBA', (Wm, H), (20, 20, 30, 255)); y = 0
        for t in tiles:
            sheet.alpha_composite(t, (0, y)); y += t.height + 8
        sheet.save(preview)


if __name__ == '__main__':
    a = sys.argv[1:]
    pv = None
    if '--preview' in a:
        i = a.index('--preview'); pv = a[i + 1]; del a[i:i + 2]
    main(a[0], a[1], a[2], pv)
