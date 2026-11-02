"""Bảng xem nhanh ảnh: contact_sheet.py <thư_mục> <ra.png> [cột=6]"""
import os, sys
from PIL import Image, ImageDraw
d, out = sys.argv[1], sys.argv[2]
cols = int(sys.argv[3]) if len(sys.argv) > 3 else 6
files = sorted(os.path.relpath(os.path.join(r, f), d) for r, _, fs in os.walk(d) for f in fs if f.endswith('.png'))
cw, ch = 160, 150
rows = (len(files) + cols - 1) // cols
sheet = Image.new('RGB', (cols * cw, rows * ch), (90, 90, 90))
dr = ImageDraw.Draw(sheet)
for i, f in enumerate(files):
    x, y = (i % cols) * cw, (i // cols) * ch
    try:
        im = Image.open(os.path.join(d, f)).convert('RGBA')
    except Exception:
        dr.text((x + 4, y + 4), 'LOI ' + f, fill=(255, 0, 0)); continue
    w, h = im.size
    s = min(1.0 * (cw - 8) / w, 1.0 * (ch - 26) / h, 3.0)
    im2 = im.resize((max(1, int(w * s)), max(1, int(h * s))), Image.NEAREST)
    bg = Image.new('RGBA', im2.size, (60, 60, 60, 255))
    for yy in range(0, im2.size[1], 8):
        for xx in range(0, im2.size[0], 8):
            if (xx // 8 + yy // 8) % 2:
                bg.paste((110, 110, 110, 255), (xx, yy, min(xx + 8, im2.size[0]), min(yy + 8, im2.size[1])))
    bg.alpha_composite(im2)
    sheet.paste(bg.convert('RGB'), (x + 4, y + 4))
    dr.text((x + 4, y + ch - 20), '%s %dx%d' % (f[-22:], w, h), fill=(255, 255, 0))
sheet.save(out)
print(out, len(files))
