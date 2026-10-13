"""Viet hoa atlas UI png7 (anim 7).
  python3 make_png7.py <png7 goc> <anim.bin vao> <png7 ra> <anim.bin ra> [preview.png]
Moi module chu duoc ve lai bang font f12 (than chu + vien 1px dung mau nhan goc - kieu B nguoi dung chon)
vao vung moi them duoi atlas; module tro sang vung moi, khung (frame) can giua lai neu ban goc can giua."""
import sys
sys.path.insert(0, 'scripts')
from PIL import Image
import anim, f12


def C(h):
    return (int(h[0:2], 16), int(h[2:4], 16), int(h[4:6], 16), 255)


G1 = ('eeeeee', '5c6263')     # chu trang vien xam (tieu de cua so)
G2 = ('154e6e', 'eeeeee')     # chu xanh dam vien trang (tab)
G3 = ('ebead3', '897244')     # chu be vien nau (menu he thong)
G4 = ('a92649', 'ebead3')     # chu do vien be (luyen dan)
G5 = ('eeeeee', 'a68c62')
BLK = ('0c0c11', 'fff6f6')    # chu den vien trang
WHT = ('eeeeee', '0c0c11')    # chu trang vien den
CYN = ('0c0c11', 'aafffe')    # chu den vien xanh nhat (nut)

# id: (chu, kieu, mau...)   kieu: t = chu co vien tren nen trong; b = o nen (fill, vien o, mau chu); n = mot mau khong vien; blank
SPEC = {
    16: ('HP', 'b', 'fffbbf', '2b282a'), 17: ('MP', 'b', 'fffbbf', '2b282a'), 18: ('ATK', 'b', 'fffbbf', '2b282a'),
    19: ('MATK', 'b', 'fffbbf', '2b282a'), 20: ('DEF', 'b', 'fffbbf', '2b282a'), 21: ('CRIT', 'b', 'fffbbf', '2b282a'),
    22: ('EVA', 'b', 'fffbbf', '2b282a'), 23: ('SPD', 'b', 'fffbbf', '2b282a'),
    25: ('Lượt', 'b', 'fffbbf', '2b282a'), 26: ('Mục tiêu', 'b', 'fffbbf', '2b282a'),
    142: ('Lượt', 'b', 'fffbbf', '2b282a'), 143: ('Tiêu hao', 'b', 'fffbbf', '2b282a'), 144: ('Phẩm cấp', 'b', 'fffbbf', '2b282a'),
    34: ('Bản lưu 1', 't', '4f522e', 'ebead3'), 35: ('Bản lưu 2', 't', '4f522e', 'ebead3'),
    38: ('Tiêu hao', 'n', '0c0c11'),
    40: ('Né', 't', '88f5fa', '253049'),
    54: ('Lên cấp', 'b', 'fff6f6', '5b7596'),
    83: ('Tắt', 'n', '5b7596'), 84: ('Bật', 'n', '5b7596'), 85: ('Auto', 'n', '5b7596'), 86: ('Tay', 'n', '5b7596'),
    94: ('Chiến lợi phẩm',) + ('t',) + G1, 95: ('Vật phẩm', 't') + G1, 96: ('Nhân vật', 't', 'fff6f6', '717067'),
    97: ('Trang bị', 't') + G1, 98: ('Tu luyện', 't') + G1, 99: ('Nhiệm vụ', 't') + G1, 100: ('Kỹ năng', 't') + G1,
    101: ('Hệ thống', 't') + G1, 102: ('Luyện đan', 't') + G1,
    103: ('Menu', 'b', '03252c', 'fff6f6'),
    104: ('Đọc', 't') + G1, 105: ('Cài đặt', 't') + G1, 106: ('Lưu', 't') + G1, 107: (None, 'blank'), 108: ('Trợ giúp', 't') + G1,
    109: ('Đả', 'i'), 110: ('Kỹ', 'i'), 111: ('Vật', 'i'), 112: ('Mua', 'i'), 113: ('Lui', 'i'),
    119: ('Hủy', 't') + CYN, 120: ('Đổi', 't') + CYN, 121: (None, 'blank'),
    122: ('Luyện đan', 't') + G4, 123: (None, 'blank'), 124: ('Thành công', 't') + G4,
    125: ('Âm thanh', 't') + G5, 126: ('Rung', 't') + G5, 127: ('Hội thoại', 't') + G5,
    129: ('Binh', 't') + G2, 130: ('Áo', 't') + G2, 131: ('Menu', 't') + G1, 132: ('Giày', 't') + G2, 133: ('T.sức', 't') + G2,
    134: ('Thuốc', 't') + G2, 135: ('Liệu', 't') + G2, 136: ('Khác', 't') + G2,
    137: ('Tu luyện', 't') + G4, 138: ('Bắt đầu', 't') + G4,
    139: ('Tu luyện', 't') + G1, 140: ('Luyện đan', 't') + G1,
    141: ('Nguyên liệu cần', 't') + BLK,
    145: ('Mạc Tà', 't') + BLK, 146: ('Mạc Tà', 't') + WHT, 147: ('T.Nghệ', 't') + BLK, 148: ('T.Nghệ', 't') + WHT,
    149: ('T.Hàn', 't') + BLK, 150: ('T.Hàn', 't') + WHT,
    162: ('Cốt truyện', 'b', '296997', 'fff6f6'), 163: ('NV chính', 't', '2b282a', 'dedcb3'), 164: ('NV phụ', 't', '2b282a', 'dedcb3'),
    165: ('XONG', 's', 'fa5252'),
    166: ('Tải lên', 'b', '154e6e', 'fff6f6'), 167: ('Cộng đồng', 'b', '154e6e', 'fff6f6'),
    168: ('Hướng dẫn', 't') + G3, 169: (None, 'blank'),
    173: ('Nhấn "0" để chọn', 't', '03252c', 'ccfafc'),
    174: ('Mua vào', 't', '2b282a', 'c7ced9'), 175: ('Bán ra', 't', '03252c', 'c7ced9'),
    176: ('Cửa hàng', 't') + G1, 177: ('Cửa hàng', 'b', '03252c', 'e3ebf2'), 178: ('Thành tựu', 'b', '03252c', 'f0ef03'),
    189: ('Cửa hàng', 't') + G1, 190: ('Về menu chính', 't') + G3, 191: ('Lưu tiến độ', 't') + G3,
    192: ('Đọc tiến độ', 't') + G3, 193: ('Cài đặt', 't') + G3,
    204: ('Tải điểm lên', 't') + G1, 208: ('Thành tựu', 't') + G1,
    209: ('Tổng hợp', 't', 'eeeeee', '897244'), 210: ('Chiến đấu', 't', 'eeeeee', '897244'),
    211: ('Cốt truyện', 't', 'eeeeee', '897244'), 212: ('Khám phá', 't', 'eeeeee', '897244'),
    219: ('Mặc', 't') + CYN, 220: ('Rèn', 't') + CYN, 221: ('Cửa hàng', 't') + G3,
}


def outlined(text, body, line):
    a = f12.render(text, C(line)); b = f12.render(text, C(body))
    im = Image.new('RGBA', (a.width + 2, a.height + 2), (0, 0, 0, 0))
    for dx, dy in ((0, 1), (2, 1), (1, 0), (1, 2)):
        im.alpha_composite(a, (dx, dy))
    im.alpha_composite(b, (1, 1))
    return im


def boxed(orig, text, fill, txt):
    """o nen: giu 2 cot/hang vien goc o 4 goc, giua to mau fill, chu 1 mau o giua"""
    t = f12.render(text, C(txt))
    w0, h0 = orig.size
    w = max(w0, t.width + 6); h = max(h0, t.height + 2)
    im = Image.new('RGBA', (w, h), (0, 0, 0, 0))
    edge = orig.getpixel((0, h0 // 2))
    top = orig.getpixel((w0 // 2, 0))
    # khung: hang tren/duoi va cot trai/phai lay tu goc
    for x in range(w):
        im.putpixel((x, 0), top); im.putpixel((x, h - 1), orig.getpixel((w0 // 2, h0 - 1)))
    for y in range(h):
        im.putpixel((0, y), edge); im.putpixel((w - 1, y), orig.getpixel((w0 - 1, h0 // 2)))
    for y in range(1, h - 1):
        for x in range(1, w - 1):
            im.putpixel((x, y), C(fill))
    for (x, y) in ((0, 0), (w - 1, 0), (0, h - 1), (w - 1, h - 1)):
        ox = 0 if x == 0 else w0 - 1; oy = 0 if y == 0 else h0 - 1
        im.putpixel((x, y), orig.getpixel((ox, oy)))
    im.alpha_composite(t, ((w - t.width) // 2, (h - t.height) // 2))
    return im


def icon(orig, text):
    """nut tron 24x24: xoa chu Han o giua bang mau nen dia, ve chu moi (than sang, vien toi lay tu chu goc)"""
    im = orig.copy()
    w, h = im.size
    from collections import Counter
    # mau nen dia = mau pho bien nhat o vanh trong (ban kinh 8..9)
    ring = Counter()
    for y in range(h):
        for x in range(w):
            r2 = (x - 11.5) ** 2 + (y - 11.5) ** 2
            if 49 <= r2 <= 72 and im.getpixel((x, y))[3] > 128:
                ring[im.getpixel((x, y))] += 1
    bg = ring.most_common(1)[0][0]
    inner = Counter()
    for y in range(h):
        for x in range(w):
            if (x - 11.5) ** 2 + (y - 11.5) ** 2 < 49 and im.getpixel((x, y)) != bg:
                inner[im.getpixel((x, y))] += 1
    cols = [c for c, _ in inner.most_common(3)]
    light = max(cols, key=lambda c: sum(c[:3])); dark = min(cols, key=lambda c: sum(c[:3]))
    for y in range(h):
        for x in range(w):
            if (x - 11.5) ** 2 + (y - 11.5) ** 2 < 56:
                im.putpixel((x, y), bg)
    hx = lambda c: '%02x%02x%02x' % c[:3]
    t = outlined(text, hx(light), hx(dark))
    if t.width > w - 2:
        t = f12.render(text, light)
    im.alpha_composite(t, ((w - t.width) // 2, (h - t.height) // 2))
    return im


def stamp(orig, text, col):
    w0, h0 = orig.size
    t = f12.render(text, C(col))
    w = max(w0, t.width + 6); h = max(h0, t.height + 6)
    im = Image.new('RGBA', (w, h), (0, 0, 0, 0))
    for x in range(1, w - 1):
        im.putpixel((x, 1), C(col)); im.putpixel((x, h - 2), C(col))
    for y in range(1, h - 1):
        im.putpixel((1, y), C(col)); im.putpixel((w - 2, y), C(col))
    im.alpha_composite(t, ((w - t.width) // 2, (h - t.height) // 2))
    return im


def build(src_png, src_anim, out_png, out_anim, preview=None):
    img = Image.open(src_png).convert('RGBA')
    an, tail = anim.parse(open(src_anim, 'rb').read())
    A = next(a for a in an if a['id'] == 7)
    old = list(A['mods'])
    made = {}
    for mid, sp in SPEC.items():
        y, x, w, h = old[mid]
        o = img.crop((x, y, x + w, y + h))
        text, kind = sp[0], sp[1]
        if kind == 'blank':
            made[mid] = None; continue
        if kind == 't':
            t = outlined(text, sp[2], sp[3])
        elif kind == 'n':
            t = f12.render(text, C(sp[2]))
        elif kind == 'b':
            t = boxed(o, text, sp[2], sp[3])
        elif kind == 'i':
            t = icon(o, text)
        elif kind == 's':
            t = stamp(o, text, sp[2])
        # canh doc: giu chieu cao goc neu du cho
        hh = max(h, t.height)
        cv = Image.new('RGBA', (t.width, hh), (0, 0, 0, 0))
        cv.alpha_composite(t, (0, (hh - t.height) // 2))
        made[mid] = cv
    # xep vao vung moi duoi atlas (ke tu y = img.height + 1)
    W = img.width
    X = 0; Y = img.height + 1; rh = 0; place = {}
    for mid in sorted(made, key=lambda m: -(made[m].height if made[m] else 0)):
        im = made[mid]
        if im is None: continue
        if X + im.width > W: X = 0; Y += rh + 1; rh = 0
        place[mid] = (X, Y); X += im.width + 1; rh = max(rh, im.height)
    H = Y + rh + 1
    out = Image.new('RGBA', (W, H), (0, 0, 0, 0))
    out.paste(img, (0, 0))
    blank = (H - 1, W - 1, 1, 1)   # pixel trong suot o goc duoi phai
    for mid, im in made.items():
        if im is None:
            A['mods'][mid] = blank; continue
        px, py = place[mid]
        out.alpha_composite(im, (px, py))
        A['mods'][mid] = (py, px, im.width, im.height)
    # sua khung
    for fi, f in enumerate(A['frames']):
        nf = []
        for (m, dy, dx, fl) in f:
            if m in made and made[m] is not None:
                oy, ox, ow, oh = old[m]; ny, nx, nw, nh = A['mods'][m]
                if abs(dx + ow / 2.0) <= 3:          # ban goc can giua quanh x = 0
                    dx = dx + ow // 2 - nw // 2
                dy = dy - (nh - oh) // 2
            nf.append((m, dy, dx, fl))
        A['frames'][fi] = nf
    # cap ghep 2 module (bo module thu 2): 105+107 'Cai dat', 221+169 'Cua hang'
    def recenter(fi, m):
        f = A['frames'][fi]
        A['frames'][fi] = [(mm, dy, (-(A['mods'][mm][2] // 2) if mm == m else dx), fl) for (mm, dy, dx, fl) in f]
    recenter(30, 105); recenter(44, 221)
    # nut "Bat dau + Luyen dan/Tu luyen" (khung 92, 94): xep lien nhau, can giua quanh x = 0
    for fi, right in ((92, 122), (94, 137)):
        wl = A['mods'][138][2]; wr = A['mods'][right][2]
        x0 = -(wl + wr + 2) // 2
        A['frames'][fi] = [(mm, dy, (x0 if mm == 138 else x0 + wl + 2 if mm == right else dx), fl)
                           for (mm, dy, dx, fl) in A['frames'][fi]]
    # "Luyen dan..." / "Tu luyen..." (khung 154..161): chu ket thuc tai x = 15, sau do la dau cham dong
    for fi in range(154, 162):
        A['frames'][fi] = [(mm, dy, (15 - A['mods'][mm][2] if mm in (122, 137) else dx), fl)
                           for (mm, dy, dx, fl) in A['frames'][fi]]
    # nut phim mem phai "He thong" (khung 23): nut keo tu x=-54 den 0 -> can giua chu trong nut
    A['frames'][23] = [(mm, dy, (-27 - A['mods'][mm][2] // 2 if mm == 101 else dx), fl) for (mm, dy, dx, fl) in A['frames'][23]]
    # luu PNG bang mau 8 bit, chi so 0 trong suot
    rgb = out.convert('RGB')
    q = rgb.quantize(colors=255, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.NONE)
    pal = q.getpalette()[:255 * 3]
    idx = q.load(); al = out.getchannel('A').load()
    p = Image.new('P', out.size); p.putpalette([255, 0, 255] + pal); op = p.load()
    for yy in range(out.height):
        for xx in range(out.width):
            op[xx, yy] = 0 if al[xx, yy] < 128 else idx[xx, yy] + 1
    p.save(out_png, format='PNG', optimize=True, transparency=0)
    open(out_anim, 'wb').write(anim.write(an, tail))
    if preview:
        bg = Image.new('RGBA', out.size, (255, 0, 255, 255)); bg.alpha_composite(out)
        bg.crop((0, img.height, W, H)).resize((W * 2, (H - img.height) * 2), Image.NEAREST).save(preview)
    print('png7 moi: %dx%d (goc %dx%d), %d module ve lai' % (W, H, img.width, img.height, len([m for m in made if made[m]])))


if __name__ == '__main__':
    build(*sys.argv[1:])
