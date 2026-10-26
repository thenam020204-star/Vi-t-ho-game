import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/pause.png')
idx = np.array(I.im)
BG = (0x26,0x23,0x25,255)
x0,y0,w,h = 8,3,112,30
sub = I.rgba[y0:y0+h, x0:x0+w]; sub[:] = BG
vn = 'Tạm dừng'
body, ol = hexc('00f4f6'), hexc('0e4b4b')
size = 18
bb = draw_text(I, vn, size, (x0,y0,w,h), body, ol)
I.changed_boxes.append((x0,y0,w,h))
log_row(I, (x0,y0,w,h), '游戏暂停', vn, str(size), body, ol, 'nền phẳng 262325; ink %s' % (bb,))
finish(I)
