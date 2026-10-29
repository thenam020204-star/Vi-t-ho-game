"""Luoi anh PNG de soat chu: png_grid.py <thu_muc_jar_giai_nen> <out_prefix>  (moi trang ~40 anh, co ten file)"""
import sys, os, glob
from PIL import Image, ImageDraw
root, outp = sys.argv[1], sys.argv[2]
fs = []
for f in sorted(glob.glob(os.path.join(root, '**', '*.png'), recursive=True)):
    try:
        Image.open(f).load(); fs.append(f)
    except Exception:
        pass
page = 0; i = 0
while i < len(fs):
    tiles = []; h = 0
    canvas = Image.new('RGB', (1400, 1800), (90, 0, 90)); d = ImageDraw.Draw(canvas)
    x = y = 0; rowh = 0
    while i < len(fs):
        im = Image.open(fs[i]).convert('RGBA')
        if im.width > 330: im = im.resize((330, max(1, im.height * 330 // im.width)))
        if x + im.width + 10 > 1400:
            x = 0; y += rowh + 14; rowh = 0
        if y + im.height + 14 > 1800: break
        canvas.paste(im, (x, y + 12), im)
        d.text((x, y), os.path.relpath(fs[i], root), fill=(255, 255, 0))
        x += max(im.width, 90) + 10; rowh = max(rowh, im.height); i += 1
    canvas.save('%s_%02d.png' % (outp, page)); page += 1
print(len(fs), 'anh,', page, 'trang')
