import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/diui.png')
idx = np.array(I.im)
BAR = (1,0,0,255)
for (bx,by,bw,bh,vn,cn,b,o) in [(4,14,41,16,'Shop','商店','ff92fd','25080c'),(199,13,38,16,'Menu','菜单','fcff69','313103')]:
    sub = idx[by:by+bh, bx:bx+bw]
    er = (sub != 0) & (sub != 62)
    reg = I.rgba[by:by+bh, bx:bx+bw]
    reg[er] = BAR
    bb = draw_text(I, vn, 12, (bx,by+1,bw,bh-1), hexc(b), hexc(o), clip=(bx,by,bx+bw,by+bh))
    I.changed_boxes.append((bx,by,bw,bh))
    log_row(I, (bx,by,bw,bh), cn, vn, '12', hexc(b), hexc(o), 'xoá glow về nền thanh 010000; giữ pixel trong suốt; ink %s' % (bb,))
finish(I)
