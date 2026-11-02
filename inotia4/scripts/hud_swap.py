#!/usr/bin/env python3
"""T10b: dan anh nut HUD cua nguoi dung (assets_user/hud/) vao 2 atlas goc UI_014 / UI_015 cua Inotia 4.

    python3 -I scripts/hud_swap.py <game_res vao> <thu muc ra> [--user assets_user/hud] [--lib <libgame.so>] [--preview anh.png]
    python3 -I scripts/hud_swap.py --selftest [<game_res vao; mac dinh: lay tu test_builds/Inotia4_TEST11_vi_offline.apk>] [--user assets_user/hud] [--lib <libgame.so>]

Vao : thu muc co UI_014.png.jpg, UI_015.png.jpg (PNG RGBA 512x512 nguyen ban, du duoi .jpg), vd. <apktool dec>/assets/common/game_res.
      SHA256 ca hai atlas goc va 7 anh nguoi dung (SHA256.txt) duoc kiem truoc, khac thi DUNG.
Ra  : UI_014.png.jpg, UI_015.png.jpg (PNG RGBA 8 bit, khong xen ke, alpha THANG nhu ban goc: DecodePNG khong nhan alpha).
      Atlas khong co viec nao => chep nguyen byte.

7 rect duoc thay (quyet dinh quan doc, PLAN.md muc T10; toa do (x, y, w, h) = bang loc IMGSYS_GetLoc, kiem bang scripts/re/hud_check.py):
  UI_015 loc0  (  2,  2, 75, 75) <- btn_pause   thu 73 px, can giua trong o 75
  UI_015 loc1  ( 80,  2, 75, 75) <- btn_save    nhu tren
  UI_015 loc2  (158,  2, 75, 75) <- btn_shop    nhu tren
  UI_015 loc3  (236,  2, 75, 75) <- btn_swap    nhu tren
  UI_015 loc11 (416, 99, 94, 94) <- btn_frame   thu 90 px, can giua trong o 94
  UI_014 loc2  (281,128,159,160) <- attack          cua so 159/0,34 x 160/0,34 quanh (255, 258) cua canvas 512 -> 159x160
  UI_014 loc3  (281,291,159,160) <- attack_pressed  cung cua so
Thu anh: PIL Image.resize(LANCZOS) tren RGBA (Pillow tu nhan alpha truoc khi loc va tra ve alpha THANG - tranh quang den/trang o mep);
phan rect nam ngoai anh moi bi xoa ve (0,0,0,0) (khong de lo sprite cu); moi pixel ngoai 7 rect giu nguyen.

--selftest: (1) SHA256 vao; (2) pixel ngoai 7 rect == goc, tung pixel; (3) trong rect: vien ngoai anh == (0,0,0,0), anh co alpha, mau trung binh
           theo alpha khop anh nguoi dung; (4) chay 2 lan ra cung tung byte; (5) atlas khong co viec => chep nguyen byte; (6) PNG ra
           hop le (IHDR 8 bit RGBA, khong xen ke, chi IHDR/iCCP/IDAT/IEND; iCCP la ho so mau cua atlas goc, Pillow giu lai); (7) neu co --lib: rect == bang loc doc tu libgame.so.
"""
import hashlib
import io
import os
import struct
import sys

from PIL import Image

HERE = os.path.dirname(os.path.abspath(__file__))
ROOT = os.path.dirname(HERE)

ATLAS_SHA256 = {
    'UI_014.png.jpg': '56274c88709140b62faed9718a10786a97052db3d7a2b9134f36338e4d3c8411',
    'UI_015.png.jpg': 'e05d2a292ec5d0ed9bd8d7174150daa97950e0c51778f2bce51f64390bfb6574',
}
ATLAS_NAME = {0x15: 'UI_014.png.jpg', 0x16: 'UI_015.png.jpg'}
USER_SIZE = {'btn_save.png': (116, 116), 'btn_shop.png': (116, 116), 'btn_pause.png': (116, 116), 'btn_frame.png': (116, 116),
             'btn_swap.png': (186, 186), 'attack.png': (512, 512), 'attack_pressed.png': (512, 512)}

# (atlas, so loc, (x, y, w, h), anh nguoi dung, che do)   che do: ('fit', canh) | ('window', ti le, (tam x, tam y))
JOBS = [
    ('UI_015.png.jpg', 0, (2, 2, 75, 75), 'btn_pause.png', ('fit', 73)),
    ('UI_015.png.jpg', 1, (80, 2, 75, 75), 'btn_save.png', ('fit', 73)),
    ('UI_015.png.jpg', 2, (158, 2, 75, 75), 'btn_shop.png', ('fit', 73)),
    ('UI_015.png.jpg', 3, (236, 2, 75, 75), 'btn_swap.png', ('fit', 73)),
    ('UI_015.png.jpg', 11, (416, 99, 94, 94), 'btn_frame.png', ('fit', 90)),
    ('UI_014.png.jpg', 2, (281, 128, 159, 160), 'attack.png', ('window', 0.34, (255.0, 258.0))),
    ('UI_014.png.jpg', 3, (281, 291, 159, 160), 'attack_pressed.png', ('window', 0.34, (255.0, 258.0))),
]
CLEAR = (0, 0, 0, 0)


def sha256(b):
    return hashlib.sha256(b).hexdigest()


def load_user(user_dir):
    """Doc 7 anh nguoi dung; kiem SHA256.txt va kich thuoc. Anh la du lieu ngoai: chi doc bang PIL, khong tin metadata."""
    sums = {}
    for line in open(os.path.join(user_dir, 'SHA256.txt'), encoding='utf-8'):
        p = line.split()
        if len(p) == 2:
            sums[p[1]] = p[0]
    out = {}
    for name, size in USER_SIZE.items():
        raw = open(os.path.join(user_dir, name), 'rb').read()
        if sums.get(name) != sha256(raw):
            raise SystemExit('SHA256 cua %s khong khop SHA256.txt (co %s, can %s) -> DUNG' % (name, sha256(raw)[:16], str(sums.get(name))[:16]))
        im = Image.open(io.BytesIO(raw))
        im.load()
        if im.format != 'PNG' or im.size != size or im.mode != 'RGBA':
            raise SystemExit('%s: can PNG RGBA %s, co %s %s %s -> DUNG' % (name, size, im.format, im.mode, im.size))
        out[name] = im
    return out


def load_atlases(in_dir):
    raw, ims = {}, {}
    for name, want in ATLAS_SHA256.items():
        b = open(os.path.join(in_dir, name), 'rb').read()
        if sha256(b) != want:
            raise SystemExit('%s: SHA256 %s khac atlas goc %s (da thay roi?) -> DUNG' % (name, sha256(b)[:16], want[:16]))
        im = Image.open(io.BytesIO(b))
        im.load()
        if im.format != 'PNG' or im.mode != 'RGBA' or im.size != (512, 512):
            raise SystemExit('%s: khong phai PNG RGBA 512x512 -> DUNG' % name)
        raw[name], ims[name] = b, im
    return raw, ims


def render_rect(src, w, h, mode):
    """Noi dung moi cua mot rect (w, h) RGBA thang: nen trong suot + anh da thu. Tra ve (anh rect, (ox, oy, k_w, k_h) vung co anh)."""
    out = Image.new('RGBA', (w, h), CLEAR)
    if mode[0] == 'fit':
        k = mode[1]
        small = src.resize((k, k), Image.LANCZOS)
        ox, oy = (w - k) // 2, (h - k) // 2
        out.paste(small, (ox, oy))                       # paste khong mask = thay the
        return out, (ox, oy, k, k)
    s, (cx, cy) = mode[1], mode[2]
    ww, hh = w / s, h / s                                # cua so tren canvas goc: 467,65 x 470,59 cho 159x160 o ti le 0,34
    box = (cx - ww / 2, cy - hh / 2, cx + ww / 2, cy + hh / 2)
    out = src.resize((w, h), Image.LANCZOS, box=box)
    return out, (0, 0, w, h)


def build(in_dir, user_dir, jobs=JOBS):
    """Tra ve ({ten: bytes PNG moi hoac nguyen ban}, {ten: Image moi}, thong tin tung rect)."""
    raw, ims = load_atlases(in_dir)
    user = load_user(user_dir)
    new = {n: ims[n].copy() for n in ims}
    info = []
    for atlas, loc, (x, y, w, h), uname, mode in jobs:
        rect, used = render_rect(user[uname], w, h, mode)
        new[atlas].paste(rect, (x, y))                   # thay the ca rect (khong alpha-composite): vung ngoai anh moi = (0,0,0,0)
        info.append((atlas, loc, (x, y, w, h), uname, mode, used, rect))
    outb = {}
    for n in ATLAS_SHA256:
        if not any(j[0] == n for j in jobs):
            outb[n] = raw[n]                             # khong co viec => chep nguyen byte
            continue
        bio = io.BytesIO()
        new[n].save(bio, format='PNG', optimize=True, compress_level=9)
        outb[n] = bio.getvalue()
    return outb, new, info, raw, ims, user


def png_chunks(b):
    assert b[:8] == b'\x89PNG\r\n\x1a\n'
    i, out = 8, []
    while i < len(b):
        n, t = struct.unpack('>I4s', b[i:i + 8])
        out.append((t.decode(), b[i + 8:i + 8 + n]))
        i += 12 + n
    return out


def alpha_mean_rgb(im):
    """Mau trung binh theo alpha (anh RGBA thang) -> (r, g, b, alpha trung binh/255)."""
    import numpy as np
    a = np.asarray(im.convert('RGBA'), dtype=np.float64)
    al = a[..., 3]
    s = al.sum()
    if s == 0:
        return (0, 0, 0, 0)
    rgb = (a[..., :3] * al[..., None]).sum(axis=(0, 1)) / s
    return tuple(float(v) for v in rgb) + (float(al.mean()) / 255.0,)


def check_rects(lib):
    """Doi chieu 7 rect cua JOBS voi bang loc IMGSYS_GetLoc doc thang tu libgame.so (dung ham giai ma cua scripts/re/hud_check.py)."""
    import importlib.util
    spec = importlib.util.spec_from_file_location('hud_check', os.path.join(HERE, 're', 'hud_check.py'))
    hc = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(hc)
    e = hc.Elf(lib)
    rev = {v: k for k, v in ATLAS_NAME.items()}
    res = []
    for atlas, loc, rect_, uname, mode in JOBS:
        stride, base = hc.decode_getloc(e, rev[atlas])
        v = struct.unpack('<6i', e.rd(base + loc * stride, 24))
        res.append(('%s loc%d == %s' % (atlas[:6], loc, rect_), tuple(v[:4]) == rect_ and v[4:] == (0, 0), str(v)))
    return res


def selftest(in_dir, user_dir, lib=None):
    import numpy as np
    ok = [True]

    def verdict(name, cond, detail=''):
        ok[0] = ok[0] and bool(cond)
        print('  -> %s: %s %s' % ('DAT' if cond else 'KHONG DAT', name, detail))

    print('[1] SHA256 vao (atlas goc + 7 anh nguoi dung) da kiem trong build(): neu sai se DUNG truoc khi toi day')
    out1, new, info, raw, ims, user = build(in_dir, user_dir)
    for n in ATLAS_SHA256:
        verdict('%s goc sha256 %s...' % (n, sha256(raw[n])[:16]), sha256(raw[n]) == ATLAS_SHA256[n])

    print('[2] pixel ngoai 7 rect == goc (tung pixel), moi atlas')
    for n in ATLAS_SHA256:
        a0 = np.asarray(ims[n].convert('RGBA'))
        a1 = np.asarray(Image.open(io.BytesIO(out1[n])).convert('RGBA'))
        mask = np.zeros(a0.shape[:2], bool)
        for atlas, loc, (x, y, w, h), *_ in info:
            if atlas == n:
                mask[y:y + h, x:x + w] = True
        same = np.array_equal(a0[~mask], a1[~mask])
        diff_in = int((a0[mask] != a1[mask]).any(axis=1).sum())
        verdict('%s: ngoai rect giong het goc (%d pixel ngoai, %d pixel trong rect, %d pixel trong rect doi)' % (n, (~mask).sum(), mask.sum(), diff_in), same)

    print('[3] trong tung rect')
    for atlas, loc, (x, y, w, h), uname, mode, (ox, oy, kw, kh), rect in info:
        a1 = Image.open(io.BytesIO(out1[atlas])).convert('RGBA')
        crop = np.asarray(a1.crop((x, y, x + w, y + h)))
        verdict('%s loc%d: noi dung rect == noi dung da dung' % (atlas[:6], loc), np.array_equal(crop, np.asarray(rect)))
        ring = np.ones((h, w), bool)
        ring[oy:oy + kh, ox:ox + kw] = False
        verdict('%s loc%d <- %s: %d pixel ngoai vung anh moi == (0,0,0,0)' % (atlas[:6], loc, uname, ring.sum()), (crop[ring] == 0).all())
        al = crop[..., 3]
        verdict('%s loc%d: co alpha day (max %d), bbox alpha %s' % (atlas[:6], loc, al.max(), Image.fromarray(al).getbbox()), al.max() == 255)
        # mau trung binh theo alpha so voi anh nguoi dung (cua so cat cho attack): sai so < 8/255 moi kenh
        srcim = user[uname]
        if mode[0] == 'fit':
            ref = srcim
        else:
            s, (cx, cy) = mode[1], mode[2]
            ww, hh = w / s, h / s
            ref = srcim.crop((int(round(cx - ww / 2)), int(round(cy - hh / 2)), int(round(cx + ww / 2)), int(round(cy + hh / 2))))
        m0, m1 = alpha_mean_rgb(ref), alpha_mean_rgb(Image.fromarray(crop[oy:oy + kh, ox:ox + kw]))
        verdict('%s loc%d: mau TB theo alpha khop anh nguoi dung (%s vs %s)' % (atlas[:6], loc, tuple(round(v, 1) for v in m0[:3]), tuple(round(v, 1) for v in m1[:3])),
                max(abs(m0[i] - m1[i]) for i in range(3)) < 8)

    print('[4] chay lan 2 ra cung tung byte')
    out2 = build(in_dir, user_dir)[0]
    for n in ATLAS_SHA256:
        verdict('%s: %d B, sha256 %s' % (n, len(out1[n]), sha256(out1[n])[:16]), out1[n] == out2[n])

    print('[5] atlas khong co viec => chep nguyen byte')
    out3 = build(in_dir, user_dir, jobs=[j for j in JOBS if j[0] == 'UI_015.png.jpg'])[0]
    verdict('UI_014 (khong co viec) == goc tung byte', out3['UI_014.png.jpg'] == raw['UI_014.png.jpg'])
    verdict('UI_015 (co viec) == ket qua day du', out3['UI_015.png.jpg'] == out1['UI_015.png.jpg'])
    outn = build(in_dir, user_dir, jobs=[])[0]
    verdict('khong viec nao: ca hai atlas == goc tung byte', all(outn[n] == raw[n] for n in ATLAS_SHA256))

    print('[6] PNG ra hop le')
    for n in ATLAS_SHA256:
        ch = png_chunks(out1[n])
        ihdr = struct.unpack('>IIBBBBB', ch[0][1])
        verdict('%s: IHDR %s (512x512, 8 bit, mau 6 = RGBA, nen 0, loc 0, xen ke 0); chunk %s' % (n, ihdr, [c[0] for c in ch if c[0] != 'IDAT'] + ['IDAT x%d' % sum(1 for c in ch if c[0] == 'IDAT')]),
                ihdr == (512, 512, 8, 6, 0, 0, 0) and ch[0][0] == 'IHDR' and ch[-1][0] == 'IEND' and set(c[0] for c in ch) <= {'IHDR', 'iCCP', 'IDAT', 'IEND'})
        im = Image.open(io.BytesIO(out1[n]))
        im.load()
        verdict('%s: giai ma lai duoc, %s %s' % (n, im.mode, im.size), im.mode == 'RGBA' and im.size == (512, 512))

    if lib:
        print('[7] rect == bang loc doc tu %s' % lib)
        for name, cond, detail in check_rects(lib):
            verdict(name, cond, detail)
    print('KET LUAN:', 'TAT CA DAT' if ok[0] else 'CO MUC KHONG DAT')
    return 0 if ok[0] else 1


# ------------------------------------------------------------------------------------------------------------- anh so sanh
def font(size):
    from PIL import ImageFont
    for f in ('/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf', '/usr/share/fonts/truetype/liberation/LiberationSans-Regular.ttf'):
        if os.path.exists(f):
            return ImageFont.truetype(f, size)
    return ImageFont.load_default()


def checker(w, h, s=8):
    import numpy as np
    yy, xx = np.mgrid[0:h, 0:w]
    v = np.where(((xx // s) + (yy // s)) % 2 == 0, 58, 74).astype(np.uint8)
    return Image.fromarray(np.stack([v, v, v + 6, np.full_like(v, 255)], -1), 'RGBA')


def on_bg(im, z=1):
    bg = checker(im.width * z, im.height * z, 8)
    big = im.resize((im.width * z, im.height * z), Image.NEAREST) if z > 1 else im
    bg.alpha_composite(big)
    return bg


def preview(path, ims, new, info):
    from PIL import ImageDraw
    F, Fs = font(15), font(12)
    pad = 10
    # hang 1-2: atlas truoc/sau
    atl_w = 512
    rows = []
    sheet_w = 2 * atl_w + 3 * pad
    # cac sprite
    cell_w = 340
    sheet_h = pad + 2 * (atl_w + 26 + pad) + 26
    sprites = []
    for atlas, loc, (x, y, w, h), uname, mode, used, rect in info:
        z = 3 if w < 100 else 1
        sprites.append((atlas, loc, (x, y, w, h), uname, z))
    sheet_h += sum(s[2][3] * s[4] + 28 for s in sprites)
    sheet = Image.new('RGBA', (sheet_w, sheet_h), (24, 24, 30, 255))
    d = ImageDraw.Draw(sheet)
    y0 = pad
    for n in ('UI_015.png.jpg', 'UI_014.png.jpg'):
        d.text((pad, y0), '%s  GOC' % n[:6], fill=(255, 255, 255, 255), font=F)
        d.text((2 * pad + atl_w, y0), '%s  SAU KHI THAY (khung xanh = 7 rect da thay)' % n[:6], fill=(255, 255, 0, 255), font=F)
        y0 += 22
        sheet.alpha_composite(on_bg(ims[n]), (pad, y0))
        a2 = on_bg(new[n])
        d2 = ImageDraw.Draw(a2)
        for atlas, loc, (x, y, w, h), *_ in info:
            if atlas == n:
                d2.rectangle((x - 1, y - 1, x + w, y + h), outline=(0, 255, 0, 255))
                d2.text((x + 2, y + 1), 'loc%d' % loc, fill=(255, 255, 0, 255), font=Fs)
        sheet.alpha_composite(a2, (2 * pad + atl_w, y0))
        y0 += atl_w + pad
    d.text((pad, y0), 'Tung nut (phong to x3 voi o nho): goc  |  moi', fill=(255, 255, 255, 255), font=F)
    y0 += 24
    for atlas, loc, (x, y, w, h), uname, z in sprites:
        old = ims[atlas].crop((x, y, x + w, y + h))
        nw = new[atlas].crop((x, y, x + w, y + h))
        d.text((pad, y0), '%s loc%d %dx%d' % (atlas[:6], loc, w, h), fill=(255, 255, 255, 255), font=Fs)
        d.text((2 * pad + atl_w, y0), '<- %s' % uname, fill=(255, 255, 0, 255), font=Fs)
        y0 += 14
        sheet.alpha_composite(on_bg(old, z), (pad, y0))
        sheet.alpha_composite(on_bg(nw, z), (2 * pad + atl_w, y0))
        y0 += h * z + 14
    sheet = sheet.crop((0, 0, sheet_w, y0 + pad))
    sheet.convert('RGB').save(path, optimize=True)


def extract_default():
    """Khong co thu muc vao: lay 2 atlas tu APK TEST11 trong repo (test_builds/) ra thu muc tam."""
    import tempfile
    import zipfile
    d = tempfile.mkdtemp(prefix='hud_atlas_')
    z = zipfile.ZipFile(os.path.join(ROOT, 'test_builds', 'Inotia4_TEST11_vi_offline.apk'))
    for n in ATLAS_SHA256:
        open(os.path.join(d, n), 'wb').write(z.read('assets/common/game_res/' + n))
    return d


def main(argv):
    user_dir = os.path.join(ROOT, 'assets_user', 'hud')
    lib = prev = None
    args = []
    i = 0
    while i < len(argv):
        a = argv[i]
        if a == '--user':
            user_dir = argv[i + 1]; i += 2
        elif a == '--lib':
            lib = argv[i + 1]; i += 2
        elif a == '--preview':
            prev = argv[i + 1]; i += 2
        else:
            args.append(a); i += 1
    if args[:1] == ['--selftest']:
        in_dir = args[1] if len(args) > 1 else extract_default()
        return selftest(in_dir, user_dir, lib)
    if len(args) != 2:
        print(__doc__)
        return 2
    in_dir, out_dir = args
    outb, new, info, raw, ims, user = build(in_dir, user_dir)
    if lib:
        bad = [r for r in check_rects(lib) if not r[1]]
        if bad:
            raise SystemExit('rect trong JOBS khac bang loc cua %s: %s -> DUNG' % (lib, bad))
        print('7 rect == bang loc doc tu %s: DAT' % lib)
    os.makedirs(out_dir, exist_ok=True)
    for n, b in outb.items():
        open(os.path.join(out_dir, n), 'wb').write(b)
        print('%s: %s (%d B) <- goc %s' % (n, sha256(b), len(b), sha256(raw[n])[:16]))
    for atlas, loc, rect, uname, mode, used, _ in info:
        print('  %s loc%-2d %s <- %-18s %s anh trong rect %s' % (atlas[:6], loc, rect, uname, mode, used))
    if prev:
        preview(prev, ims, new, info)
        print('anh so sanh:', prev)
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]))
