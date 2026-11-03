#!/usr/bin/env python3
"""Thư viện vẽ cho mockup menu mới (T17b1): font bitmap thật (font.bin 15px, font10.bin, f18.bin của vi_android),
giải mã chuỗi Việt đã build (dict.txt), sprite .mgr thật từ VFS đã build. Không phụ thuộc gì ngoài PIL.
Biến môi trường WORK (mặc định ~/hl5work)."""
import os, sys, struct
from PIL import Image

WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))
VI = os.path.join(WORK, 'vi_android')
HERE = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.abspath(os.path.join(HERE, '..', '..'))
sys.path.insert(0, os.path.join(REPO, 'tools'))
sys.path.insert(0, os.path.join(REPO, 'vi'))
sys.path.insert(0, os.path.join(WORK, 'proj/HL4_project/img5'))

# ---------------- font ----------------
def read_font(p):
    d = open(p, 'rb').read()
    n = struct.unpack_from('>H', d, 0)[0]
    cs = d[2:2 + n].decode('utf-8'); o = 2 + n
    h = struct.unpack_from('b', d, o)[0]; o += 1
    w = list(struct.unpack_from('%db' % len(cs), d, o)); o += len(cs)
    tot = sum(w); nb = (h * tot + 7) // 8
    f = dict(cs=cs, h=h, w=w, tot=tot, bits=d[o:o + nb], idx={c: i for i, c in enumerate(cs)})
    offs = [0]
    for x in w: offs.append(offs[-1] + x)
    f['off'] = offs
    return f

FONTS = {15: read_font(os.path.join(VI, 'font.bin')), 10: read_font(os.path.join(VI, 'font10.bin')),
         18: read_font(os.path.join(VI, 'f18.bin'))}
SPACE = 4          # spaceW của vi.bin (bề rộng ô trắng mode 0, mọi bộ font); mode 1/2: 3

def _words():
    w = open(os.path.join(VI, 'dict.txt'), encoding='utf-8').read().split('\n')
    if w and w[-1] == '': w.pop()
    return w
WORDS = _words()

def decode(b):
    """bytes mã LGT (2 byte/từ) -> list (mode, text). ASCII thẳng = mode 0."""
    out = []; i = 0
    while i < len(b):
        c = b[i]
        if c >= 0xC9 and i + 1 < len(b) and b[i + 1] >= 0xA1:
            k = (c - 0xC9) * 94 + (b[i + 1] - 0xA1)
            if k < len(WORDS):
                w = WORDS[k]; m = 0
                if w[:1] == '\x01': m, w = 1, w[1:]
                elif w[:1] == '\x02': m, w = 2, w[1:]
                out.append((m, w)); i += 2; continue
        out.append((0, chr(c))); i += 1
    return out

def plain(tokens): return ''.join(t for _, t in tokens)

def font_for(px, mode):
    """(font, nén?) theo vn_sel: SD 15 -> mode0 15, mode1 15 nén, mode2 10 nén. HD 18 -> 18, 18 nén, 15 nén."""
    if px == 18: return (FONTS[18], False) if mode == 0 else ((FONTS[18], True) if mode == 1 else (FONTS[15], True))
    if px == 15: return (FONTS[15], False) if mode == 0 else ((FONTS[15], True) if mode == 1 else (FONTS[10], True))
    return (FONTS[10], False) if mode == 0 else (FONTS[10], True)

def glyph_adv(f, c, tight):
    if c == ' ': return 3 if tight else SPACE
    i = f['idx'].get(c)
    if i is None or f['w'][i] <= 0: return 0
    r = f['w'][i]
    return r - 1 if (tight and r > 2) else r

def tok_width(px, tokens, force0=True):
    t = 0
    for m, s in tokens:
        f, tight = font_for(px, 0 if force0 else m)
        for c in s: t += glyph_adv(f, c, tight)
    return t

def split_lines(tokens):
    """tách theo ';' (xuống dòng của game)"""
    lines = [[]]
    for m, s in tokens:
        parts = s.split(';')
        for k, p in enumerate(parts):
            if k: lines.append([])
            if p: lines[-1].append((m, p))
    return lines

def draw_text(im, x, y, tokens, px, color, force0=True):
    """vẽ 1 dòng tokens tại (x,y) góc trên-trái ô chữ; trả bề rộng."""
    px_ = im.load(); W, H = im.size; cx = x
    for m, s in tokens:
        f, tight = font_for(px, 0 if force0 else m)
        for c in s:
            adv = glyph_adv(f, c, tight)
            i = f['idx'].get(c)
            if c != ' ' and i is not None and f['w'][i] > 0:
                off = f['off'][i]
                for yy in range(f['h']):
                    for xx in range(f['w'][i]):
                        b = yy * f['tot'] + off + xx
                        if f['bits'][b >> 3] >> (b & 7) & 1:
                            X, Y = cx + xx, y + yy
                            if 0 <= X < W and 0 <= Y < H: px_[X, Y] = color
            cx += adv
    return cx - x

# ---------------- dữ liệu Việt đã build ----------------
import extract_android as X
def dat_strings(fname):
    b = open(os.path.join(VI, 'res', fname), 'rb').read()
    recs = X.parse_dat(b); lay = X.DAT_LAYOUT.get(fname); res = []
    for ri, r in enumerate(recs):
        for (o, ps, n) in X.walk_rec(r, lay):
            res.append((ri, o, decode(r[o + ps:o + ps + n])))
    return res

# ---------------- sprite .mgr ----------------
import mgr as MGR, vfs_pack as VP
_vfs = None
def vfs_get(name):
    global _vfs
    if _vfs is None:
        _vfs = {h: b for h, b in VP.read_vfs(open(os.path.join(VI, 'data.vfs.mp3'), 'rb').read())}
    return _vfs[VP.djb2(name)]
_mgr = {}
def mgr_img(bank, i):
    if bank not in _mgr:
        _mgr[bank] = [MGR.decode(d)[2] for o, d in MGR.parse(vfs_get('c/img/%s.mgr' % bank))]
    return _mgr[bank][i]


# ---------------- chuỗi thuần (menu mới luôn vẽ mode 0: bỏ nén/10px baked trong từ điển) ----------------
RARITY = {'&': (90, 175, 255), '}': (205, 130, 255), '`': (255, 150, 55), '^': (255, 90, 80), '<': (100, 225, 120)}
# GIẢ ĐỊNH: màu 5 mã phẩm chất (hiếm, sử thi, anh hùng, huyền thoại, bộ); '{' và '|' = kết thúc màu. Implement tra bảng màu BFont.

def clean_name(s):
    """bỏ mã màu phẩm chất ở đầu/cuối; trả (text, mã phẩm chất hoặc '')"""
    code = ''
    if s[:1] in RARITY: code, s = s[0], s[1:]
    return s.rstrip('{|').replace(';', ' ').strip(), code

def strip_hint(s):
    """bỏ gợi ý phím ở đầu ('0.', '1.', '#', '*') vì UI cảm ứng không cần"""
    if len(s) > 2 and s[0] in '0123456789' and s[1] == '.': return s[2:]
    if s[:1] in '#*' and len(s) > 1: return s[1:]
    return s

def sw(px, s):
    f = FONTS[px]; return sum(glyph_adv(f, c, False) for c in s)

def wrap(px, s, maxw):
    """xuống dòng theo từ; ';' trong dữ liệu coi là dấu cách. Trả list dòng (str); từ dài hơn maxw bị ngắt theo ký tự."""
    words = s.replace(';', ' ').split(' ')
    lines = []; cur = ''
    for w in words:
        if w == '': continue
        t = (cur + ' ' + w) if cur else w
        if sw(px, t) <= maxw: cur = t; continue
        if cur: lines.append(cur); cur = ''
        while sw(px, w) > maxw:           # từ quá dài: ngắt theo ký tự
            k = len(w)
            while k > 1 and sw(px, w[:k]) > maxw: k -= 1
            lines.append(w[:k]); w = w[k:]
        cur = w
    if cur: lines.append(cur)
    return lines

def text(im, x, y, s, px, color, shadow=None):
    if shadow: draw_text(im, x + 1, y + 1, [(0, s)], px, shadow)
    return draw_text(im, x, y, [(0, s)], px, color)
