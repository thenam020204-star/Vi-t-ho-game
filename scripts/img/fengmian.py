import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np, cv2
from lib import *
from fm_mask import build_mask
I = Img('bin/fengmian.png'); rgb = I.orig[..., :3].copy()
mask = build_mask(rgb)
r,g,b = [rgb[...,i].astype(int) for i in range(3)]
mx = rgb.max(-1).astype(int); mn = rgb.min(-1).astype(int)
extra_like = (mx < 66) | ((r>100)&(g>88)&(b>55)&(mx-mn<95)&(r-b>15)) | ((r>185)&(g>165)&(b>120)&(mx-mn<100))
# vá tay các mảnh đuôi nét/viền còn sót (toạ độ x0,y0,x1,y1)
for (x0,y0,x1,y1) in ((112,168,128,182),(158,174,176,192),(66,126,90,146),(34,118,46,134)):
    z = np.zeros_like(mask); z[y0:y1, x0:x1] = True
    mask |= cv2.dilate((z & extra_like).astype(np.uint8), np.ones((3,3),np.uint8)) > 0
ys, xs = np.nonzero(mask)
ebox = (int(xs.min()), int(ys.min()), int(xs.max()-xs.min()+1), int(ys.max()-ys.min()+1))
out = cv2.inpaint(np.ascontiguousarray(rgb), (mask*255).astype(np.uint8), 5, cv2.INPAINT_TELEA)
# làm mượt vùng đắp (giảm hình 'nón' của Telea) rồi chỉ giữ trong mask
bl = cv2.GaussianBlur(out.astype(np.float32), (0,0), 2.5)
wt = cv2.GaussianBlur(mask.astype(np.float32), (0,0), 1.5)[..., None]
out = np.where(mask[..., None], bl*0.75 + out*0.25, out).astype(np.uint8)
# lượng tử về bảng màu gốc có dither có trật tự (giống phong cách dither của ảnh gốc)
pal = np.array([I.im.getpalette()[i*3:i*3+3] for i in range(len(I.im.getpalette())//3)], int)
bayer = np.array([[0,8,2,10],[12,4,14,6],[3,11,1,9],[15,7,13,5]]) / 16.0 - 0.5
H, Wd = mask.shape
res = rgb.copy()
for y, x in zip(*np.nonzero(mask)):
    c = out[y, x].astype(float) + bayer[y % 4, x % 4] * 26
    d = ((pal - c) ** 2).sum(1)
    res[y, x] = pal[d.argmin()]
I.rgba[..., :3] = res
# chữ mới
RED, CREAM = hexc('e10301'), hexc('fdfcf4')
ORG, DK = hexc('f3a605'), hexc('040101')
bb = []
bb.append(draw_text(I, 'HÙNG BÁ', 18, (30, 104, 180, 24), RED, CREAM, dx=-2))
bb.append(draw_text(I, 'TAM QUỐC', 18, (30, 127, 180, 24), RED, CREAM, dx=-2))
bb.append(draw_text(I, 'Thiên Giáng Nữ Thần', 18, (30, 205, 184, 26), ORG, DK, dx=2))
I.changed_boxes += [ebox] + bb
log_row(I, ebox, '雄霸三国 / 之 / 天降女神 (xoá)', 'xoá chữ Hán + vẽ lại (xem 3 dòng dưới)', '-', None, None, 'nền: mask theo màu + Telea inpaint + dither bảng màu gốc; ebox=vùng xoá')
log_row(I, bb[0], '雄霸三国 (1/2)', 'HÙNG BÁ', '18', RED, CREAM, '')
log_row(I, bb[1], '雄霸三国 (2/2)', 'TAM QUỐC', '18', RED, CREAM, 'bỏ chữ 之 (liên từ), không vẽ lại')
log_row(I, bb[2], '天降女神', 'Thiên Giáng Nữ Thần', '18', ORG, DK, '')
finish(I)
