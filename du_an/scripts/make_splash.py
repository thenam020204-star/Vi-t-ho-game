"""Tao splashAnim tieng Viet: anh bia 240x320 moi (assets/cover_vi.webp) + vet muc menu goc + dai nhan menu (y>=320).
  python3 make_splash.py <splashAnim goc> <dai nhan 240x48 RGBA hoac '-'> <ra>"""
import sys
from PIL import Image
src, strip, out = sys.argv[1:4]
orig = Image.open(src).convert('RGBA')
cov = Image.open('assets/cover_vi.webp').convert('RGB')
h = 320; w = round(cov.width * h / cov.height)
cov = cov.resize((w, h), Image.LANCZOS)
x0 = (w - 240) // 2
cov = cov.crop((x0, 0, x0 + 240, h)).convert('RGBA')
# vet muc den sau nhan menu (giu nguyen tu ban goc)
for y in range(274, 316):
    for x in range(45, 200):
        if x > 165 and y < 285:
            continue
        r, g, b, a = orig.getpixel((x, y))
        if (r * 3 + g * 6 + b) // 10 < 75:
            cov.putpixel((x, y), (r, g, b, 255))
res = Image.new('RGBA', orig.size, (0, 0, 0, 0))
res.paste(cov, (0, 0))
bottom = orig.crop((0, 320, 240, orig.height)) if strip == '-' else Image.open(strip).convert('RGBA')
res.paste(bottom, (0, 320))
# PNG bang mau 8 bit, chi so 0 trong suot (giong file goc)
rgb = res.convert('RGB')
q = rgb.quantize(colors=255, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.FLOYDSTEINBERG)
pal = q.getpalette()[:255 * 3]
idx = q.load(); al = res.getchannel('A').load()
outp = Image.new('P', res.size)
outp.putpalette([255, 0, 255] + pal)
op = outp.load()
for y in range(res.height):
    for x in range(res.width):
        op[x, y] = 0 if al[x, y] < 128 else idx[x, y] + 1
outp.save(out, format='PNG', optimize=True, transparency=0)
print('ok', out)
