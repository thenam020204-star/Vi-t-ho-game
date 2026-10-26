import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/uitishi.png')
I.rgba[:] = (0,0,0,179)
vn = '2468:Di chuyển 5/Trái:Chọn Phải:Quay lại'
body, ol = hexc('51bfff'), hexc('02284b')
bb = draw_text(I, vn, 12, (0,0,240,18), body, ol, dy=0)
I.changed_boxes.append((0,0,240,18))
log_row(I, (0,0,240,18), '2468:移动 5/左软键:确认 右软键:返回', vn, '12', body, ol, 'nền = 000000 alpha 179 phẳng; ink %s' % (bb,))
finish(I)
