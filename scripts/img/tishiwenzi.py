import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/tishiwenzi.png')
# (gốc, dịch, màu thân, màu viền, cột xoá tới (exclusive), loại)
L = [('生命+','HP','60ff00','345003',24,'plus'),
     ('怒气+','MP','ffb400','640f03',24,'plus'),
     ('经验+','EXP','60ff00','345003',24,'plus'),
     ('金钱+','Xu','ffb400','640f03',24,'plus'),
     ('得到物品:','Đồ mới','ff51f1','5c0537',51,'sym'),
     ('得到装备:','Trang bị','51bbff','051c5c',51,'sym'),
     ('红药不足!','Hết đỏ','ff4b21','480315',49,'sym'),
     ('蓝药不足!','Hết xanh','ffb400','640f03',49,'sym'),
     ('生命不足!','HP cạn','ff0200','480315',49,'sym'),
     ('怒气不足!','MP cạn','ffb400','640f03',49,'sym'),
     ('接到:','Nhận','ffb400','640f03',28,'sym'),
     ('完成:','Xong','ffb400','640f03',28,'sym'),
     ('提交:','Nộp','ffb400','640f03',28,'sym')]
for i,(cn,vn,b,o,ex,kind) in enumerate(L):
    y0 = i*13
    reg = I.rgba[y0:y0+13]
    if kind == 'plus':
        keep = np.zeros((13, 56), bool)
        keep[2:11, 26:29] = True; keep[5:8, 24:31] = True
        er = np.zeros((13,56), bool); er[:, :31] = True; er &= ~keep
    else:
        er = np.zeros((13,56), bool); er[:, :ex] = True
    reg[er] = (0,0,0,0)
    bw = ex if kind=='sym' else 24
    bb = draw_text(I, vn, 12, (0, y0, bw, 13), hexc(b), hexc(o), align='left' if kind=='sym' else 'center')
    I.changed_boxes.append((0, y0, 31 if kind=='plus' else ex, 13))
    log_row(I, (0,y0,bw,13), cn, vn, '12', hexc(b), hexc(o), 'ink %s; giữ nguyên ký hiệu %s' % (bb, '+' if kind=='plus' else ':/!'))
finish(I)
