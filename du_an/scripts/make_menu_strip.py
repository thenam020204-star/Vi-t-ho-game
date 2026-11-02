"""Dai nhan menu chinh (splashAnim y=320..367) + sua module anim 11 trong anim.bin.
  python3 make_menu_strip.py <splashAnim goc> <anim.bin goc> <ra strip.png> <ra anim.bin>"""
import sys
sys.path.insert(0, 'scripts')
from PIL import Image
import anim, f12
src, abin, out_strip, out_anim = sys.argv[1:5]
orig = Image.open(src).convert('RGBA')
an, tail = anim.parse(open(abin, 'rb').read())
A = next(a for a in an if a['id'] == 11)
strip = Image.new('RGBA', (240, 48), (0, 0, 0, 0))
# giu sprite trang tri module 14 (y=320,x=221,14x7)
y, x, w, h = A['mods'][14]
strip.paste(orig.crop((x, y, x + w, y + h)), (x, y - 320))
WHITE = (255, 255, 255, 255)
# module -> nhan (slot menu: 0 moi=M4, 1 tiep=M5, 5 cai dat=M6, 6 huong dan=M7, 7 gioi thieu=M8, 8 thoat=M13)
labels = [(4, 'Trò chơi mới'), (5, 'Chơi tiếp'), (6, 'Cài đặt'), (7, 'Hướng dẫn'), (8, 'Giới thiệu'), (13, 'Thoát game')]
pos = [(0, 0), (80, 0), (150, 0), (0, 14), (70, 14), (140, 14)]   # (x, y trong dai), hang 12px + 2px cach
for (m, t), (px, py) in zip(labels, pos):
    im = f12.render(t, WHITE)
    strip.alpha_composite(im, (px, py))
    A['mods'][m] = (320 + py, px, im.width, 12)
# muc da go (Game Center/QQ/more) -> vung trong suot
for m in (9, 10, 11, 12, 15, 16, 17):
    A['mods'][m] = (320 + 30, 0, 1, 1)
# can giua lai: dx = -w/2 cho khung 13..24
for fi in range(13, 25):
    f = A['frames'][fi]
    A['frames'][fi] = [(mm, dy, -(A['mods'][mm][2] // 2) if mm not in (15, 16) else dx, fl) for (mm, dy, dx, fl) in f]
strip.save(out_strip)
open(out_anim, 'wb').write(anim.write(an, tail))
for fi in range(13, 22): print(fi, A['frames'][fi], [A['mods'][e[0]] for e in A['frames'][fi]])
