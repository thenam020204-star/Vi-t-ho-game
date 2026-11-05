"""Import a redrawn title picture (PNG, 290x199, any colours) as tdf/TITLE_BM (8 bpp, <=255 colours)."""
import sys, os
sys.path.insert(0, os.path.dirname(__file__))
from bm import *
im = Image.open(sys.argv[1]).convert('RGBA')
if im.size != (290, 199):
    print('warning: title picture should be 290x199, got', im.size, '-> resized')
    im = im.resize((290, 199))
os.makedirs(os.path.dirname(sys.argv[2]), exist_ok=True)
open(sys.argv[2], 'wb').write(build([encode(im)]))
print('wrote', sys.argv[2])
