import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
for rel,vn,cn in (('bin/yes.png','Có','是'),('bin/no.png','Không','否')):
    I = Img(rel)
    I.rgba[:] = (0,0,0,255)
    body, ol = hexc('ffffff'), hexc('1665ab')
    bb = draw_text(I, vn, '15c', (0,0,I.w,I.h), body, ol)
    I.changed_boxes.append((0,0,I.w,I.h))
    log_row(I, (0,0,I.w,I.h), cn, vn, '15c', body, ol, 'nền đen 000000 (quầng sáng gốc là halo của chữ nên xoá hết); ink %s' % (bb,))
    finish(I)
