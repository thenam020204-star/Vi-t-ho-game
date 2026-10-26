# -*- coding: utf-8 -*-
import os, sys, csv
import numpy as np
from PIL import Image
W = os.environ.get('WROOT', '/tmp/claude-0/-home-user-Vi-t-ho-game/9a067d44-fb40-5db7-b229-544b694f82f4/scratchpad')
sys.path.insert(0, W + '/work/tools/pixeltext-kit')
import pixeltext as pt
GOC = W + '/img/goc'; VIET = W + '/img/viet'; CMP = W + '/img/compare'
TSV = W + '/img/anh_da_sua.tsv'

def hexc(s):
    s = s.lstrip('#'); return tuple(int(s[i:i+2], 16) for i in (0, 2, 4))

class Img:
    """ảnh gốc + mảng RGBA làm việc"""
    def __init__(self, rel):
        self.rel = rel
        self.path = os.path.join(GOC, rel)
        self.im = Image.open(self.path); self.im.load()
        self.mode = self.im.mode
        self.rgba = np.array(self.im.convert('RGBA'))
        self.orig = self.rgba.copy()
        self.w, self.h = self.im.size
        self.changed_boxes = []
        self.log = []
        reset_log(rel)
    def save(self):
        out = os.path.join(VIET, self.rel); os.makedirs(os.path.dirname(out), exist_ok=True)
        if self.mode == 'P':
            save_p(self, out)
        else:
            Image.fromarray(self.rgba, 'RGBA').save(out)
        return out

def save_p(I, out):
    im = I.im
    pal = list(im.getpalette()); ncol = len(pal) // 3
    pal = [tuple(pal[i*3:i*3+3]) for i in range(ncol)]
    tr = im.info.get('transparency')
    assert tr is None or isinstance(tr, int), tr
    idx0 = np.array(im)  # original indices
    new = idx0.copy()
    diff = np.any(I.rgba != I.orig, axis=-1)
    lut = {}
    for c in pal:
        lut.setdefault(c, pal.index(c))
    for (y, x) in zip(*np.nonzero(diff)):
        r, g, b, a = I.rgba[y, x]
        if tr is not None and a == 0:
            new[y, x] = tr; continue
        key = (int(r), int(g), int(b))
        if key not in lut:
            if len(pal) < 256:
                pal.append(key); lut[key] = len(pal) - 1
            else:
                arr = np.array(pal, int); d = ((arr - np.array(key)) ** 2).sum(1)
                if tr is not None: d[tr] = 1 << 30
                lut[key] = int(d.argmin())
        new[y, x] = lut[key]
    o = Image.fromarray(new, 'P')
    flat = [v for c in pal for v in c] + [0] * (768 - 3 * len(pal))
    o.putpalette(flat)
    kw = {}
    if tr is not None: kw['transparency'] = tr
    o.save(out, **kw)

def text_mask(text, size, mode='metric', gap=1):
    m = pt.layout(text, size, mode=mode, gap=gap)
    f = m.font
    return m, f

def text_dims(text, size, mode='metric', gap=1):
    m = pt.layout(text, size, mode=mode, gap=gap).trimmed()
    return m.w, m.h

def draw_text(I, text, size, box, body, outline, align='center', valign='center',
              diag=True, mode='metric', gap=1, dx=0, dy=0, clip=None, outline_alpha=255, strict_fit=True, clamp_y=True):
    """vẽ chữ 1 màu + viền 1px. box=(x,y,w,h). trả về (x,y,w,h) ink bbox (gồm viền)."""
    m = pt.layout(text, size, mode=mode, gap=gap)
    f = m.font
    px = m.pixels
    xs = [p[0] for p in px]; ys = [p[1] for p in px]
    x0, x1, y0, y1 = min(xs), max(xs), min(ys), max(ys)
    bx, by, bw, bh = box
    iw = x1 - x0 + 1
    if align == 'center': ox = bx + (bw - iw) // 2
    elif align == 'left': ox = bx + (1 if outline is not None else 0)
    else: ox = bx + bw - iw - (1 if outline is not None else 0)
    if valign == 'center':
        # canh theo dải chữ hoa (cap band) để các nhãn cùng baseline
        cap_top = f.baseline - f.cap_height + 1
        oy = by + (bh - f.cap_height) // 2 - cap_top
    elif valign == 'top': oy = by - y0
    else: oy = by + bh - 1 - y1
    ox += dx - x0; oy += dy
    if clamp_y and outline is not None:   # nếu dấu/đuôi chạm mép box thì dịch cả dòng cho vừa
        pad = 1 if outline is not None else 0
        top = oy + y0 - pad; bot = oy + y1 + pad
        if top < by: oy += by - top
        elif bot > by + bh - 1: oy -= bot - (by + bh - 1)
    body_px = {(x + ox, y + oy) for (x, y) in px}
    out_px = set()
    dirs = [(-1,0),(1,0),(0,-1),(0,1)] + ([(-1,-1),(1,-1),(-1,1),(1,1)] if diag else [])
    for (x, y) in body_px:
        for a, b in dirs:
            p = (x + a, y + b)
            if p not in body_px: out_px.add(p)
    allp = body_px | out_px
    bb = (min(p[0] for p in allp), min(p[1] for p in allp), max(p[0] for p in allp), max(p[1] for p in allp))
    if strict_fit:
        assert bb[0] >= bx and bb[1] >= by and bb[2] < bx + bw and bb[3] < by + bh, \
            ('tràn ô', text, size, bb, box)
    cx0, cy0, cx1, cy1 = clip or (0, 0, I.w, I.h)
    for (x, y) in out_px:
        if cx0 <= x < cx1 and cy0 <= y < cy1 and outline is not None:
            I.rgba[y, x] = (*outline, outline_alpha)
    for (x, y) in body_px:
        if cx0 <= x < cx1 and cy0 <= y < cy1:
            I.rgba[y, x] = (*body, 255)
    return (bb[0], bb[1], bb[2] - bb[0] + 1, bb[3] - bb[1] + 1)

def count_outside(I, boxes):
    """số pixel khác gốc nằm ngoài mọi box (x,y,w,h)"""
    diff = np.any(I.rgba != I.orig, axis=-1)
    inside = np.zeros_like(diff)
    for (x, y, w, h) in boxes:
        inside[max(y,0):y+h, max(x,0):x+w] = True
    return int((diff & ~inside).sum())

def compare_png(I, out=None, scale=3):
    out = out or os.path.join(CMP, I.rel.replace('/', '_'))
    os.makedirs(os.path.dirname(out), exist_ok=True)
    def prep(a):
        im = Image.fromarray(a, 'RGBA')
        bg = Image.new('RGBA', im.size, (255, 0, 255, 255)); bg.alpha_composite(im)
        return bg.resize((im.width * scale, im.height * scale), Image.NEAREST)
    a, b = prep(I.orig), prep(I.rgba)
    gap = 6
    if I.w * scale <= 480 and I.h <= 100:   # xếp dọc nếu ảnh thấp
        c = Image.new('RGBA', (a.width, a.height * 2 + gap), (30, 30, 30, 255))
        c.paste(a, (0, 0)); c.paste(b, (0, a.height + gap))
    else:
        c = Image.new('RGBA', (a.width * 2 + gap, a.height), (30, 30, 30, 255))
        c.paste(a, (0, 0)); c.paste(b, (a.width + gap, 0))
    c.save(out); return out

FIELDS = ['file', 'box', 'goc', 'dich', 'font', 'mau_than', 'mau_vien', 'ghi_chu']
def log_row(I, box, goc, dich, font, body, outline, note=''):
    new = not os.path.exists(TSV)
    with open(TSV, 'a', newline='', encoding='utf-8') as fh:
        w = csv.writer(fh, delimiter='\t')
        if new: w.writerow(FIELDS)
        h = lambda c: '#%02x%02x%02x' % tuple(c) if c is not None else '-'
        w.writerow([I.rel, '%d,%d,%d,%d' % tuple(box), goc, dich, font, h(body), h(outline), note])

def reset_log(prefix):
    if not os.path.exists(TSV): return
    rows = list(csv.reader(open(TSV, encoding='utf-8'), delimiter='\t'))
    keep = [rows[0]] + [r for r in rows[1:] if r[0] != prefix]
    with open(TSV, 'w', newline='', encoding='utf-8') as fh:
        csv.writer(fh, delimiter='\t').writerows(keep)

def finish(I, boxes=None):
    boxes = boxes if boxes is not None else I.changed_boxes
    out = I.save()
    chk = Image.open(out)
    assert chk.size == I.im.size and chk.mode == I.im.mode, (chk.size, chk.mode)
    n = count_outside(I, boxes)
    # kiểm tra lại từ file đã lưu: pixel ngoài box phải bằng gốc
    a = np.array(chk.convert('RGBA')); o = np.array(I.im.convert('RGBA'))
    d = np.any(a != o, axis=-1); ins = np.zeros_like(d)
    for (x, y, w, h) in boxes: ins[max(y,0):y+h, max(x,0):x+w] = True
    n2 = int((d & ~ins).sum())
    c = compare_png(I)
    print(I.rel, 'mode', chk.mode, 'size', chk.size, 'ngoài box (mảng/file):', n, n2, 'đổi:', int(d.sum()))
    return n2


def inpaint(rgb, mask, radius=3, method='telea'):
    """rgb: HxWx3 uint8, mask: bool HxW. Trả về rgb mới (cv2.inpaint)."""
    import cv2
    m = (mask.astype(np.uint8)) * 255
    flag = cv2.INPAINT_TELEA if method == 'telea' else cv2.INPAINT_NS
    return cv2.inpaint(np.ascontiguousarray(rgb), m, radius, flag)

def dilate(mask, n=1):
    m = mask.copy()
    for _ in range(n):
        p = np.pad(m, 1)
        m = (p[1:-1,1:-1] | p[:-2,1:-1] | p[2:,1:-1] | p[1:-1,:-2] | p[1:-1,2:] |
             p[:-2,:-2] | p[:-2,2:] | p[2:,:-2] | p[2:,2:])
    return m

def snap_to_palette(rgb, mask, pal):
    """gán lại các pixel trong mask về màu gần nhất trong pal (Nx3)"""
    pal = np.array(pal, int)
    ys, xs = np.nonzero(mask)
    for y, x in zip(ys, xs):
        d = ((pal - rgb[y, x].astype(int)) ** 2).sum(1)
        rgb[y, x] = pal[d.argmin()]
    return rgb
