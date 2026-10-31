"""Thay toàn bộ glyph của một font bitmap This War of Mine bằng glyph render từ file TTF (vd Times New Roman),
giữ cỡ gốc: cỡ chữ = min(khớp chiều cao chữ hoa, khớp độ rộng a–z) để không cao/rộng hơn bản gốc;
giữ baseline, chiều cao dòng (đuôi u32), cờ bpp. Font 2 byte/pixel: kênh 1 = viền ngoài (dilate − nét).
Bộ ký tự = ký tự gốc ∪ tiếng Việt; ký tự TTF không có thì giữ bitmap gốc.
  python3 fontreplace_ttf.py <ttf> <font.bin gốc> <font.bin ra>"""
import sys, os, numpy as np
from PIL import ImageFont
sys.path.insert(0, os.path.dirname(__file__)); import fontfmt, fontpatch_vi as fp
from fontTools.ttLib import TTFont

W = fontfmt.W
LOW = 'abcdefghijklmnopqrstuvwxyz '


def disk_dilate(a, r):
    out = a.copy(); h, w = a.shape
    for dy in range(-r, r + 1):
        for dx in range(-r, r + 1):
            if dx * dx + dy * dy > r * r + r: continue
            sh = np.zeros_like(a)
            sh[max(0, dy):h + min(0, dy), max(0, dx):w + min(0, dx)] = a[max(0, -dy):h + min(0, -dy), max(0, -dx):w + min(0, -dx)]
            np.maximum(out, sh, out=out)
    return out


def outline_radius(F):
    g = F.G.get(ord('A')) or F.G.get(ord('H'))
    a = F.pages[g[1]][g[7]:g[8], g[5]:g[6]]
    w0 = np.where(a[:, :, 0].max(0) > 0)[0]; w1 = np.where(a[:, :, 1].max(0) > 0)[0]
    return max(1, int(round(((w1.max() - w1.min()) - (w0.max() - w0.min())) / 2.0)))


def choose_size(F, ttf):
    f100 = ImageFont.truetype(ttf, 100)
    cap = -f100.getbbox('H', anchor='ls')[1] / 100.0
    wid = f100.getlength(LOW) / 100.0
    s_cap = F.cap / cap
    s_w = sum(F.adv(c) for c in LOW if F.has(c)) / wid
    return max(6, int(round(min(s_cap, s_w))))


def pack(items, bpp):
    """items: list of (cp, arr[h,w,bpp] đã gồm viền) -> pages, {cp:(page,x,y)}; xếp kệ, rộng 1024, cao POT."""
    order = sorted(items, key=lambda it: (-it[1].shape[0], it[0]))
    pages = []; pos = {}; cur = None
    def new_page():
        return dict(x=0, y=0, row=0, rects=[])
    cur = new_page()
    for cp, a in order:
        h, w = a.shape[:2]
        if cur['x'] + w > W:
            cur['y'] += cur['row'] + 1; cur['x'] = 0; cur['row'] = 0
        if cur['y'] + h > 1024:
            pages.append(cur); cur = new_page()
        cur['rects'].append((cp, a, cur['x'], cur['y']))
        cur['row'] = max(cur['row'], h); cur['x'] += w + 1
    pages.append(cur)
    out = []
    for pi, p in enumerate(pages):
        need = p['y'] + p['row'] + 1; H = 32
        while H < need: H *= 2
        img = np.zeros((H, W, bpp), np.uint8)
        for cp, a, x, y in p['rects']:
            img[y:y + a.shape[0], x:x + a.shape[1]] = a; pos[cp] = (pi, x, y)
        out.append(img)
    return out, pos


def replace(ttf, data):
    F = fp.Font(data); F.metrics(); bpp = F.bpp
    size = choose_size(F, ttf)
    font = ImageFont.truetype(ttf, size)
    cmap = set(TTFont(ttf, fontNumber=0).getBestCmap())
    r = outline_radius(F) if bpp == 2 else 0
    pad = 1 + r
    base = F.base
    cps = sorted(set(F.G) | {ord(c) for c in fp.vi_targets()} | {ord('ı')})
    items = []; meta = {}
    for cp in cps:
        ch = chr(cp)
        if cp not in cmap:
            if cp not in F.G: continue
            g = F.G[cp]; a = F.pages[g[1]][g[7]:g[8], g[5]:g[6]].copy()
            items.append((cp, a)); meta[cp] = (g[2], g[3], g[4]); continue
        adv = int(round(font.getlength(ch)))
        m, off = font.getmask2(ch, mode='L', anchor='ls')
        ink = np.array(m, np.uint8).reshape(m.size[1], m.size[0]) if m.size[0] * m.size[1] else np.zeros((0, 0), np.uint8)
        if ink.size == 0 or ink.max() == 0:
            items.append((cp, np.zeros((0, 0, bpp), np.uint8))); meta[cp] = (0, -(base - 1), adv); continue
        ys, xs = np.where(ink > 0)
        ink = ink[ys.min():ys.max() + 1, xs.min():xs.max() + 1]
        left = off[0] + xs.min(); top = base + off[1] + ys.min()
        h, w = ink.shape
        a = np.zeros((h + 2 * pad, w + 2 * pad, bpp), np.uint8)
        a[pad:pad + h, pad:pad + w, 0] = ink
        if bpp == 2:
            dil = disk_dilate(a[:, :, 0], r)
            a[:, :, 1] = np.clip(dil.astype(int) - a[:, :, 0], 0, 255).astype(np.uint8)
        xo, yo = left - pad, -(top - pad)
        assert -128 <= xo < 128 and -128 <= yo < 128 and 0 <= adv < 256, (ch, size, xo, yo, adv)
        items.append((cp, a)); meta[cp] = (xo, yo, adv)
    pages, pos = pack([it for it in items if it[1].size], bpp)
    G = []
    for cp, a in items:
        xo, yo, adv = meta[cp]
        if a.size: pi, x, y = pos[cp]; G.append([cp, pi, xo, yo, adv, x, x + a.shape[1], y, y + a.shape[0]])
        else: G.append([cp, 0, xo, yo, adv, 0, 0, 0, 0])
    G.sort(key=lambda g: g[0])
    f = F.f
    f['G'] = G; f['H'] = [p.shape[0] for p in pages]; f['pages'] = [p.tobytes() for p in pages]
    return fontfmt.build(f), size


if __name__ == '__main__':
    out, size = replace(sys.argv[1], open(sys.argv[2], 'rb').read())
    open(sys.argv[3], 'wb').write(out); print(os.path.basename(sys.argv[2]), 'size', size)
