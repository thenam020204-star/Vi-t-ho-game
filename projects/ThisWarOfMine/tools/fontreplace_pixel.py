"""Thay toàn bộ glyph của một font bitmap This War of Mine bằng font pixel NAM.P (fonts/namp/f18.bin, 18px có chân).
  - Tỉ lệ s = min(chữ hoa gốc / 10, độ rộng a–z gốc / độ rộng a–z f18)  (không cao/rộng hơn font gốc).
  - s < 0.8 -> f13.bin, s < 0.95 -> f15.bin, s < 1.3 -> f18.bin nguyên bản (đúng từng pixel).
  - s >= 1.3 -> VẼ LẠI ở cỡ lớn: mỗi glyph f18 được dựng lại thành nét bút (nối tâm các ô pixel kề nhau,
    nét chéo thành đường xiên liền, không bậc thang), độ dày = s, khử răng cưa — không phóng to khối.
  - Ký tự f18 thiếu mà text game dùng (Ba Lan trong credits, – “ ” ’ © ® = _ ` ~) vẽ tay ở EXTRA bên dưới.
  - Ký tự không có trong font pixel: giữ bitmap gốc của game. Giữ baseline, chiều cao dòng (đuôi u32), bpp.
  python3 fontreplace_pixel.py <font.bin gốc> <font.bin ra>"""
import sys, os, struct, numpy as np
from PIL import Image, ImageDraw
sys.path.insert(0, os.path.dirname(__file__)); import fontfmt, fontpatch_vi as fp, fontreplace_ttf as fr

FDIR = os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 'fonts', 'namp')
# số đo lưới của từng file (hàng đỉnh chữ hoa, hàng dưới cùng của chân chữ)
GRID = {'f18': dict(cap=5, base=14), 'f15': dict(cap=5, base=12), 'f13': dict(cap=4, base=10)}


def decode(path):
    """fontcus .bin: u2 len + MUTF-8 charset, s1 height, s1 width[n], atlas 1bpp LSB-first liên tục."""
    d = open(path, 'rb').read()
    n = struct.unpack('>H', d[:2])[0]; chars = d[2:2 + n].replace(b'\xc0\x80', b'\0').decode('utf-8'); p = 2 + n
    h = d[p]; p += 1
    ws = list(d[p:p + len(chars)]); p += len(chars)
    W_ = sum(ws); bits = np.unpackbits(np.frombuffer(d[p:p + (h * W_ + 7) // 8], np.uint8), bitorder='little')
    atlas = bits[:h * W_].reshape(h, W_)
    G, x = {}, 0
    for ch, w in zip(chars, ws):
        G[ch] = atlas[:, x:x + w].astype(np.uint8); x += w
    return h, G


def art(rows, h=18, top=0):
    a = np.zeros((h, len(rows[0])), np.uint8)
    for i, r in enumerate(rows): a[top + i] = [c == '#' for c in r]
    return a


# ---- vẽ tay thêm (lưới 18 hàng, rộng 7, cột 6 để trống làm khoảng cách như f18) ----
EXTRA = {
    '=': (10, ["#####..", ".......", "#####.."]),
    '_': (15, ["######."]),
    '`': (5, [".#.....", "..#...."]),
    '~': (10, [".##..#.", "#..##.."]),
    '–': (11, ["######."]),
    '“': (5, ["..#..#.", ".#..#..", ".##.##."]),
    '”': (5, [".##.##.", "..#..#.", ".#..#.."]),
    '©': (6, [".####..", "#....#.", "#.##.#.", "#.#..#.", "#.#..#.", "#.##.#.", "#....#.", ".####.."]),
    '®': (6, [".####..", "#....#.", "#.##.#.", "#.#.##.", "#.##.#.", "#.#.##.", "#....#.", ".####.."]),
}


# vùng hàng của lưới 18px (dấu | trên x | thân x | phần dưới) và số hàng giữ lại ở từng cỡ (theo fontgen NAM.P)
ZONES = ((0, 5), (5, 9), (9, 15), (15, 18))
KEEP = {'f18': (5, 4, 6, 3), 'f15': (5, 3, 5, 2), 'f13': (4, 2, 5, 2)}


def rowmap(name, r):
    """hàng r của lưới 18px -> hàng tương ứng ở lưới của font name (chia đều trong từng vùng)."""
    t = 0
    for (a, b), k in zip(ZONES, KEEP[name]):
        if a <= r < b: return t + (r - a) * k // (b - a)
        t += k
    return t - 1


def squeeze(g18, name, h):
    out = np.zeros((h, g18.shape[1]), np.uint8)
    for r in range(18):
        out[rowmap(name, r)] |= g18[r]
    return out


def extras(G, name, h):
    for ch, (top, rows) in EXTRA.items():
        if ch not in G: G[ch] = squeeze(art(rows, top=top), name, h)
    G['’'] = G["'"].copy(); G['\xa0'] = G[' '].copy()
    cap, base = GRID[name]['cap'], GRID[name]['base']
    up = (G['Á'] > G['A']).astype(np.uint8); up[cap:] = 0            # dấu sắc chữ hoa của chính font này
    xr = int(np.nonzero(G['a'].max(1))[0][0])                          # hàng đỉnh x-height
    lo = (G['á'] > G['a']).astype(np.uint8); lo[xr:] = 0
    for b, a in zip('NSZnsz', 'ŃŚŹńśź'): G[a] = np.maximum(G[b], up if b.isupper() else lo)
    G['Ż'] = G['Z'].copy(); G['Ż'][cap - 2, 2] = 1
    G['ż'] = G['z'].copy(); G['ż'][xr - 2, 2] = 1
    def ogo(g, x):
        g[base + 1, x] = 1
        if base + 2 < h: g[base + 2, x] = g[base + 2, x + 1] = 1
    for b, a, x in (('A', 'Ą', 4), ('E', 'Ę', 4), ('a', 'ą', 4), ('e', 'ę', 3)):
        G[a] = G[b].copy(); ogo(G[a], x)
    m = (cap + base) // 2 + 1                                          # gạch chéo của Ł quanh giữa thân
    G['Ł'] = G['L'].copy(); G['Ł'][m - 1, 3] = G['Ł'][m, 2] = G['Ł'][m + 1, 0] = 1
    lt = int(np.nonzero(G['l'].max(1))[0][0]); m = (lt + base) // 2
    G['ł'] = G['l'].copy(); G['ł'][m - 1, 4] = G['ł'][m, 3] = G['ł'][m + 1, 1] = 1
    return G


_CACHE = {}
def pixfont(name):
    if name not in _CACHE:
        h, G = decode(os.path.join(FDIR, name + '.bin'))
        G = extras(G, name, h)
        _CACHE[name] = (h, G)
    return _CACHE[name]


def redraw(g, s, ss=4):
    """Dựng lại glyph pixel g (h x w, 0/1) thành nét bút ở tỉ lệ s; trả về ảnh alpha (H x W) uint8."""
    h, w = g.shape; S = s * ss
    im = Image.new('L', (int(np.ceil(w * S)) + ss, int(np.ceil(h * S)) + ss), 0); dr = ImageDraw.Draw(im)
    pen = S * min(1.2, 1 + 0.07 * (s - 1)); r = pen / 2   # nét đậm dần khi phóng lớn
    on = lambda y, x: 0 <= y < h and 0 <= x < w and g[y, x]
    c = lambda y, x: ((x + .5) * S, (y + .5) * S)
    for y, x in zip(*np.nonzero(g)):
        cx, cy = c(y, x)
        # điểm: tròn nếu có nối chéo, vuông nếu chỉ nối thẳng (giữ chân chữ/góc vuông của f18)
        diag = False
        for dy, dx in ((1, 1), (1, -1)):
            if on(y + dy, x + dx) and not on(y + dy, x) and not on(y, x + dx):
                dr.line([c(y, x), c(y + dy, x + dx)], fill=255, width=int(round(pen))); diag = True
                ex, ey = c(y + dy, x + dx); dr.ellipse([ex - r, ey - r, ex + r, ey + r], fill=255)
        for dy, dx in ((-1, 1), (-1, -1)):
            if on(y + dy, x + dx) and not on(y + dy, x) and not on(y, x + dx): diag = True
        if diag: dr.ellipse([cx - r, cy - r, cx + r, cy + r], fill=255)
        else: dr.rectangle([cx - r, cy - r, cx + r - 1, cy + r - 1], fill=255)
        for dy, dx in ((0, 1), (1, 0)):
            if on(y + dy, x + dx):
                ex, ey = c(y + dy, x + dx)
                dr.rectangle([min(cx, ex) - r, min(cy, ey) - r, max(cx, ex) + r - 1, max(cy, ey) + r - 1], fill=255)
    W2, H2 = im.size[0] // ss, im.size[1] // ss
    return np.array(im.crop((0, 0, W2 * ss, H2 * ss)).resize((W2, H2), Image.BOX))


def choose(F):
    _, G = pixfont('f18')
    wid = sum(G[c].shape[1] for c in fr.LOW)
    s = min(F.cap / 10.0, sum(F.adv(c) for c in fr.LOW if F.has(c)) / wid)
    if s < 0.8: return 'f13', 1.0, s
    if s < 0.95: return 'f15', 1.0, s
    if s < 1.3: return 'f18', 1.0, s
    return 'f18', s, s


def replace(data):
    F = fp.Font(data); F.metrics(); bpp = F.bpp
    name, s, s0 = choose(F)
    _, PG = pixfont(name); gb = GRID[name]['base'] + 1          # đường chân chữ (cạnh dưới hàng base)
    r = fr.outline_radius(F) if bpp == 2 else 0
    pad = 1 + r; base = F.base
    cps = sorted(set(F.G) | {ord(c) for c in fp.vi_targets()} | {ord(c) for c in PG})
    items = []; meta = {}
    for cp in cps:
        ch = chr(cp)
        if ch not in PG:
            if cp not in F.G: continue
            g = F.G[cp]; items.append((cp, F.pages[g[1]][g[7]:g[8], g[5]:g[6]].copy())); meta[cp] = (g[2], g[3], g[4]); continue
        pg = PG[ch]; adv = int(round(pg.shape[1] * s))
        ink = (pg * 255).astype(np.uint8) if s == 1.0 else redraw(pg, s)
        if ink.max() == 0:
            items.append((cp, np.zeros((0, 0, bpp), np.uint8))); meta[cp] = (0, -(base - 1), adv); continue
        ys, xs = np.where(ink > 0)
        ink = ink[ys.min():ys.max() + 1, xs.min():xs.max() + 1]
        left = xs.min(); top = base - (gb * s - ys.min())                 # toạ độ từ đỉnh dòng
        top = int(round(top))
        h, w = ink.shape
        a = np.zeros((h + 2 * pad, w + 2 * pad, bpp), np.uint8); a[pad:pad + h, pad:pad + w, 0] = ink
        if bpp == 2:
            dil = fr.disk_dilate(a[:, :, 0], r)
            a[:, :, 1] = np.clip(dil.astype(int) - a[:, :, 0], 0, 255).astype(np.uint8)
        xo, yo = left - pad, -(top - pad)
        assert -128 <= xo < 128 and -128 <= yo < 128 and 0 <= adv < 256, (ch, s, xo, yo, adv)
        items.append((cp, a)); meta[cp] = (xo, yo, adv)
    pages, pos = fr.pack([it for it in items if it[1].size], bpp)
    G = []
    for cp, a in items:
        xo, yo, adv = meta[cp]
        if a.size: pi, x, y = pos[cp]; G.append([cp, pi, xo, yo, adv, x, x + a.shape[1], y, y + a.shape[0]])
        else: G.append([cp, 0, xo, yo, adv, 0, 0, 0, 0])
    G.sort(key=lambda g: g[0])
    f = F.f; f['G'] = G; f['H'] = [p.shape[0] for p in pages]; f['pages'] = [p.tobytes() for p in pages]
    return fontfmt.build(f), (name, round(s0, 2))


if __name__ == '__main__':
    out, info = replace(open(sys.argv[1], 'rb').read())
    open(sys.argv[2], 'wb').write(out); print(os.path.basename(sys.argv[1]), info)
