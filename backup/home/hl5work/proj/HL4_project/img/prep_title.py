"""User title art -> img/src/title.png (290x199): crop to the artwork, fit it into the part of the
picture that is visible on screen (columns 18..257), match the colour mood of the original title."""
import sys, os
import numpy as np
from PIL import Image
sys.path.insert(0, os.path.dirname(__file__))
from color_match import match
src, ref, out = sys.argv[1], sys.argv[2], sys.argv[3]
k = float(sys.argv[4]) if len(sys.argv) > 4 else 0.9
im = Image.open(src).convert('RGB')
a = np.asarray(im).astype(int).sum(2)
ys, xs = np.where(a > 60)
box = (max(0, xs.min() - 10), max(0, ys.min() - 10), min(im.width, xs.max() + 10), min(im.height, ys.max() + 10))
art = im.crop(box)
s = min(236 / art.width, 195 / art.height)
art = art.resize((max(1, int(art.width * s)), max(1, int(art.height * s))), Image.LANCZOS)
canvas = Image.new('RGB', (290, 199), (0, 0, 0))
canvas.paste(art, (18 + (240 - art.width) // 2, (199 - art.height) // 2))
match(canvas, Image.open(ref), k).save(out)
print('art box', box, '->', art.size)
