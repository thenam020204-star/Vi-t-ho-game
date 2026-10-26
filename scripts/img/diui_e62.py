# diui.png ban E62 (320x36, bang mau khac N73): xoa chu 商店/菜单 ve mau than thanh (index 91 = 0,0,0),
# giu trong suot (index 0); ve "Shop"/"Menu" f12, cung mau than/vien nhu ban N73 (diui.py).
#   WROOT=<thu muc co img/goc/bin/diui.png va work/tools> python3 diui_e62.py
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/diui.png')
idx = np.array(I.im)
BAR = (0, 0, 0, 255)
for (bx, by, bw, bh, vn, cn, b, o) in [(16, 14, 38, 16, 'Shop', '商店', 'ff92fd', '25080c'),
                                       (267, 13, 38, 16, 'Menu', '菜单', 'fcff69', '313103')]:
    sub = idx[by:by + bh, bx:bx + bw]
    er = ~np.isin(sub, [0, 91, 92])
    I.rgba[by:by + bh, bx:bx + bw][er] = BAR
    bb = draw_text(I, vn, 12, (bx, by + 1, bw, bh - 1), hexc(b), hexc(o), clip=(bx, by, bx + bw, by + bh))
    I.changed_boxes.append((bx, by, bw, bh))
    log_row(I, (bx, by, bw, bh), cn, vn, '12', hexc(b), hexc(o), 'E62; xoa chu ve mau than thanh, giu trong suot; ink %s' % (bb,))
finish(I)
