#!/usr/bin/env python3 -I
"""Ve lai logo ten game (c_2) bang font thu phap nguoi dung. Dau ra: work/build/img_overlay/c_2.xng (+png), logs/img/logo_truoc_sau.png.
Chay tu goc repo:  python3 -I scripts/img_logo.py
"""
import sys, os, io
HERE = os.path.dirname(os.path.abspath(__file__)); ROOT = os.path.dirname(HERE)
sys.path.insert(0, HERE)
import numpy as np
from PIL import Image, ImageDraw, ImageFont, ImageFilter
import xng_codec as X

FONT = os.path.join(ROOT, 'fonts_user', 'Fz-Harukaze-v2.ttf')
SRC = os.path.join(ROOT, 'work', 'k790_raw', 'c_2.xng')
OUT = os.path.join(ROOT, 'work', 'build', 'img_overlay')
LINE1 = 'Mộng Hồi Tam Quốc'
LINE2 = 'Tàn Kiếm Vấn Tình Thiên'
# vung chu goc (x0,y0,x1,y1) tung glyph; dong lon / dong nho
BIG = [(54,18,98,72),(99,25,152,72),(149,29,192,68),(190,23,237,74)]
SMALL = [(92,75,208,99)]
BIG_AREA = (54, 18, 238, 74)      # vung dat dong lon (x0,y0,x1,y1)
SMALL_AREA = (80, 76, 212, 99)
SS = 4

def inpaint(arr, mask, iters=0):
    """push-pull tren mang (H,W,C) float, mask=True la vung can dien"""
    a = arr.astype(float); w = (~mask).astype(float)
    def pp(a, w):
        H, W = w.shape
        if H <= 1 or W <= 1 or w.min() > 0:
            if w.sum() == 0: return np.zeros_like(a) + a.mean((0, 1))
            return a
        H2, W2 = (H+1)//2, (W+1)//2
        ap = np.zeros((H2*2, W2*2, a.shape[2])); wp = np.zeros((H2*2, W2*2)); ap[:H,:W] = a*w[...,None]; wp[:H,:W] = w
        sa = ap.reshape(H2,2,W2,2,-1).sum((1,3)); sw = wp.reshape(H2,2,W2,2).sum((1,3))
        ca = np.where(sw[...,None] > 0, sa/np.maximum(sw[...,None],1e-9), 0)
        cw = (sw > 0).astype(float)
        low = pp(ca, cw)
        up = np.stack([np.array(Image.fromarray(low[..., c].astype(np.float32), 'F').resize((W2*2, H2*2), Image.BILINEAR))[:H, :W] for c in range(a.shape[2])], -1)
        return np.where(w[...,None] > 0, a, up)
    out = pp(a, w)
    # lam muot nhe vung dien
    for _ in range(6):
        p = np.pad(out, ((1,1),(1,1),(0,0)), mode='edge')
        avg = (p[:-2,1:-1]+p[2:,1:-1]+p[1:-1,:-2]+p[1:-1,2:]+out*2) / 6
        out = np.where(mask[...,None], avg, out)
    return out

def dil(m, r=1):
    o = np.zeros_like(m)
    for dy in range(-r, r+1):
        for dx in range(-r, r+1):
            o |= np.roll(np.roll(m, dy, 0), dx, 1)
    return o
def ero(m, r=1): return ~dil(~m, r)

def text_mask(im):
    H, W = im.shape[:2]; rgb = im[..., :3]; a = im[..., 3]
    lum = (rgb*[.3,.59,.11]).sum(2)
    mx = rgb.max(2); mn = rgb.min(2); sat = (mx-mn)/np.maximum(mx, 1)
    big = np.zeros((H,W), bool); small = np.zeros((H,W), bool)
    for x0,y0,x1,y1 in BIG: big[y0:y1, x0:x1] = True
    for x0,y0,x1,y1 in SMALL: small[y0:y1, x0:x1] = True
    zone = big | small
    glyphish = ((sat > .27) & (mx > 80)) | ((lum < 85) & (a >= 200)) | ((lum > 200) & (sat < .25) & (a >= 250))
    m0 = zone & glyphish & ((a >= 200) | small)
    m0 |= small & (lum < 100) & (a >= 100)
    m0 = (dil(ero(m0 & big, 1), 1)) | (m0 & small)
    m = dil(m0, 2)
    m |= (lum < 110) & (a > 0) & (a < 220) & dil(zone, 3) & dil(m0, 5)
    return m & dil(zone, 3)

def fill_masked(im, m):
    """Vung chu bi xoa: alpha=255 neu sat ngay sau pixel nen opaque (nui/vach da) ke ben, nguoc lai trong suot (ho khoi); mau lay push-pull tu pixel opaque."""
    known = (~m) & (im[..., 3] >= 250)
    col = inpaint(im[..., :3], ~known)
    near = known.copy()
    for _ in range(3): near = dil(near, 1)
    out = im.copy()
    out[m, :3] = col[m]
    # nhan alpha trung binh cua cac diem khoi (khong opaque) lan can lam alpha trang thai trong suot
    out[m, 3] = np.where(near[m], 255.0, 0.0)
    return out

def render_line(text, size, width_target, fill_stops, outline, ink_shadow, is_small=False):
    f = ImageFont.truetype(FONT, size*SS)
    bb = f.getbbox(text, stroke_width=0)
    w, h = bb[2]-bb[0], bb[3]-bb[1]
    pad = 6*SS
    lay = Image.new('L', (w+2*pad, h+2*pad), 0)
    ImageDraw.Draw(lay).text((pad-bb[0], pad-bb[1]), text, font=f, fill=255)
    return lay, (w, h)

def gradient(h, stops):
    ys = np.linspace(0, 1, h)
    cols = np.zeros((h, 3))
    xs = [s[0] for s in stops]
    for c in range(3):
        cols[:, c] = np.interp(ys, xs, [s[1][c] for s in stops])
    return cols

def place(canvas, text, size, area, kind):
    f = ImageFont.truetype(FONT, size*SS)
    bb = f.getbbox(text)
    w, h = bb[2]-bb[0], bb[3]-bb[1]
    pad = 4*SS
    mask = Image.new('L', (w+2*pad, h+2*pad), 0)
    ImageDraw.Draw(mask).text((pad-bb[0], pad-bb[1]), text, font=f, fill=255)
    ax0, ay0, ax1, ay1 = area
    cx, cy = (ax0+ax1)/2, (ay0+ay1)/2
    tw, th = w/SS, h/SS
    ox, oy = cx - tw/2 - pad/SS, cy - th/2 - pad/SS
    W, H = canvas.size
    big = Image.new('RGBA', (W*SS, H*SS), (0,0,0,0))
    px, py = int(round(ox*SS)), int(round(oy*SS))
    def lay(m, rgba_fn, dx=0, dy=0):
        layer = Image.new('RGBA', big.size, (0,0,0,0))
        full = Image.new('L', big.size, 0); full.paste(m, (px+dx, py+dy))
        col = rgba_fn(full)
        layer.paste(col, (0,0), full)
        big.alpha_composite(layer)
    if kind == 'big':
        glow = mask.filter(ImageFilter.MaxFilter(2*SS+1))
        sh = mask.filter(ImageFilter.MaxFilter(2*SS*1+1)).filter(ImageFilter.GaussianBlur(SS*1.2))
        lay(sh.point(lambda v: int(v*0.75)), lambda full: Image.new('RGBA', big.size, (25,12,4,255)), 2*SS, 2*SS)
        lay(glow, lambda full: Image.new('RGBA', big.size, (58,34,12,255)))          # vien
        # than: gradient vang
        g = gradient(mask.height, [(0,(255,252,170)),(.35,(236,205,110)),(.7,(176,128,52)),(1,(110,66,22))])
        arr = np.zeros((big.size[1], big.size[0], 4), np.uint8)
        gi = np.zeros((mask.height, mask.width, 4), np.uint8); gi[...,:3] = g[:,None,:].astype(np.uint8); gi[...,3] = 255
        gimg = Image.fromarray(gi)
        layer = Image.new('RGBA', big.size, (0,0,0,0)); layer.paste(gimg, (px, py), mask)
        big.alpha_composite(layer)
    else:
        glow = mask.filter(ImageFilter.MaxFilter(4*SS+1)).filter(ImageFilter.GaussianBlur(SS*1.5))
        lay(glow.point(lambda v: int(v*0.7)), lambda full: Image.new('RGBA', big.size, (255,255,215,255)))
        lay(mask, lambda full: Image.new('RGBA', big.size, (18,12,8,255)))
    small = big.resize((W, H), Image.BOX)
    canvas.alpha_composite(small)
    return dict(box=(round(ox+pad/SS), round(oy+pad/SS), round(tw), round(th)))

def main():
    os.makedirs(OUT, exist_ok=True); os.makedirs(os.path.join(ROOT, 'logs', 'img'), exist_ok=True)
    d = open(SRC, 'rb').read(); png, meta = X.decode(d)
    orig = Image.open(io.BytesIO(png)).convert('RGBA')
    im = np.array(orig).astype(float)
    m = text_mask(im.astype(int))
    clean = fill_masked(im, m)
    canvas = Image.fromarray(np.clip(clean+0.5, 0, 255).astype('uint8'), 'RGBA')
    # xoa cac pixel bi tran mau: alpha=0 thi RGB khong quan trong
    if os.environ.get('LOGO_DEBUG'):
        bg = Image.new('RGBA', canvas.size, (60,110,190,255)); bg.alpha_composite(canvas); bg.crop((40,5,240,105)).resize((800,400), Image.NEAREST).convert('RGB').save('/tmp/claude-0/s/clean.png')
    i1 = place(canvas, LINE1, 32, BIG_AREA, 'big')
    i2 = place(canvas, LINE2, 22, SMALL_AREA, 'small')
    buf = io.BytesIO(); canvas.save(buf, 'PNG', optimize=False)
    pngb = buf.getvalue()
    xng = X.encode(pngb, meta)
    # kiem lai giai ma
    back, _ = X.decode(xng); assert back == pngb
    open(os.path.join(OUT, 'c_2.xng'), 'wb').write(xng)
    canvas.save(os.path.join(OUT, 'c_2.png'))
    # truoc/sau (nen xanh de thay vung trong suot)
    def comp(img, col):
        bg = col.copy() if isinstance(col, Image.Image) else Image.new('RGBA', img.size, col); bg.alpha_composite(img); return bg
    c4 = X.decode(open(os.path.join(ROOT,'work','k790_raw','c_4.xng'),'rb').read())[0]
    c4 = Image.open(io.BytesIO(c4)).convert('RGBA')
    cols = [c4, (60,110,190,255)]
    k = 3
    rows = []
    for c in cols:
        a = comp(orig, c).resize((240*k, 165*k), Image.NEAREST); b = comp(canvas, c).resize((240*k, 165*k), Image.NEAREST)
        r = Image.new('RGB', (240*k*2+10, 165*k), (30,30,30)); r.paste(a.convert('RGB'), (0,0)); r.paste(b.convert('RGB'), (240*k+10, 0)); rows.append(r)
    out = Image.new('RGB', (rows[0].width, rows[0].height*2+10), (30,30,30)); out.paste(rows[0], (0,0)); out.paste(rows[1], (0, rows[0].height+10))
    out.save(os.path.join(ROOT, 'logs', 'img', 'logo_truoc_sau.png'))
    print('OK', i1, i2, 'mask px', int(m.sum()))
main()
