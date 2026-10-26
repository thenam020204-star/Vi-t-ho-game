import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/music.png')
rgb = I.orig[..., :3].copy()
mx = rgb.max(-1).astype(int)
pal = [tuple(I.im.getpalette()[i*3:i*3+3]) for i in range(len(I.im.getpalette())//3)]
# dòng 1: y 0..40, dòng 2: y 40..69
m1 = np.zeros(mx.shape, bool); m1[:40] = mx[:40] > 100
m1 = dilate(m1, 2); m1[:, :10] = False; m1[:, 180:] = m1[:, 180:]  # giữ
m2 = np.zeros(mx.shape, bool)
# dòng 2: chữ pixel + viền đen trong pill, thân sáng/hồng-xanh
m2[44:66] = (mx[44:66] > 60) | (rgb[44:66].sum(-1) < 20)
m2 = dilate(m2, 1)
m2[:44] = False
# bbox chữ hộp xoá
b1 = (10, 6, 168, 32); b2 = (2, 46, 188, 19)
box1 = np.zeros(mx.shape, bool); box1[b1[1]:b1[1]+b1[3], b1[0]:b1[0]+b1[2]] = True
box2 = np.zeros(mx.shape, bool); box2[b2[1]:b2[1]+b2[3], b2[0]:b2[0]+b2[2]] = True
m1 &= box1
import cv2
# xoá sạch nét: inpaint, rồi làm mượt (blur) vùng chữ để mất bóng ma của chữ cũ
out = inpaint(rgb, m1, 4)
bl = cv2.GaussianBlur(out, (0,0), 3.5)
wt = np.zeros(mx.shape, np.float32); wt[10:33, 16:175] = 1
wt = cv2.GaussianBlur(wt, (0,0), 3); wt[:4]=0; wt[38:]=0; wt[:, :8]=0; wt[:, 184:]=0; wt = wt[..., None]
out = (out*(1-wt) + bl*wt).astype(np.uint8)
m1 = (wt[...,0] > 0.02) | m1
I.rgba[..., :3] = out
I.rgba[..., :3] = snap_to_palette(I.rgba[..., :3].copy(), m1, pal)
m2 &= box2
rgb2 = I.rgba[..., :3].copy()
out2 = inpaint(rgb2, m2, 3)
out2 = snap_to_palette(out2, m2, pal)
I.rgba[..., :3] = np.where(m2[..., None], out2, I.rgba[..., :3])
ERASE = [(8, 4, 177, 36), (2, 46, 188, 19)]
body1, ol1 = hexc('d2fdc5'), hexc('2f78b4')
body2, ol2 = hexc('1fffd7'), hexc('080206')
t1, t2 = 'Bật âm nhạc?', 'Tắt nhạc giúp game mượt hơn'
bb1 = draw_text(I, t1, 18, (8, 4, 177, 34), body1, ol1, dy=0)
bb2 = draw_text(I, t2, 12, (2, 46, 188, 19), body2, ol2)
I.changed_boxes += ERASE
log_row(I, ERASE[0], '是否开启音乐?', t1, '18', body1, ol1, 'nền: inpaint+làm mượt quầng sáng xanh; ink %s' % (bb1,))
log_row(I, ERASE[1], '建议您关闭音乐会使游戏更加流畅', t2, '12', body2, ol2, 'nền: inpaint trong pill hồng; ink %s' % (bb2,))
finish(I)
