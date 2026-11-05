"""Replace the publisher (EA mobile) logo screen image with the user's picture: vi overlay l/_LOGO."""
import sys, os
sys.path.insert(0, os.path.dirname(__file__))
from bm import *
src, out = sys.argv[1], sys.argv[2]
d = open(src, 'rb').read()
bodies, tail = parse(d)
logo = Image.open(sys.argv[3]).convert('RGBA').resize((240, 320))
# the logo screen draws the picture at y = 160 - 3h/4 (x centred): a 640 px tall picture with the
# artwork in its lower half lands exactly on the 240x320 screen
canvas = Image.new('RGBA', (240, 640), (0, 0, 0, 0))
canvas.paste(logo, (0, 320))
logo = canvas
new = [blank(b) for b in bodies]
new[4] = encode(logo)
os.makedirs(os.path.dirname(out), exist_ok=True)
open(out, 'wb').write(build(new, tail))
print('wrote', out, len(new[4]))
