import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
I = Img('bin/MMchar.png')
items = [('开始游戏','Bắt đầu','c'),('继续游戏','Chơi tiếp','c'),('QQ游戏中心','QQ Game','r'),
         ('QQ社区','Cộng đồng QQ','r'),('设置','Cài đặt','c'),('帮助','Trợ giúp','c'),
         ('关于','Giới thiệu','c'),('退出','Thoát','c'),('精彩推荐','Game hay','r')]
CY=(40,220,228); RD=(235,40,50); WH=(255,255,255)
rgb = I.orig[..., :3]
st = np.stack([rgb[i*27:i*27+27] for i in range(9)])  # 9,27,116,3
key = (st[...,0].astype(int)<<16)|(st[...,1].astype(int)<<8)|st[...,2]
H, Wd = 27, 116
cons = np.zeros((H, Wd, 3), np.uint8); cnt = np.zeros((H, Wd), int)
for y in range(H):
    for x in range(Wd):
        v, c = np.unique(key[:, y, x], return_counts=True); k = c.argmax()
        cnt[y, x] = c[k]; cons[y, x] = ((v[k]>>16)&255, (v[k]>>8)&255, v[k]&255)
# tập màu nền chắc chắn
sure = {tuple(cons[y,x]) for y in range(H) for x in range(Wd) if cnt[y,x] >= 7}
ok = np.array([[cnt[y,x] >= 4 and tuple(cons[y,x]) in sure for x in range(Wd)] for y in range(H)])
for y in range(H):
    for x in range(12, 105):
        if y < 26: ok[y,x] = (9 <= y <= 14 and cnt[y,x] >= 5 and cons[y,x].max() < 200 and tuple(cons[y,x]) in sure)
# nền tổng hợp
GRAY=(128,128,128); BLK=(9,1,3)
bands = {}
for y in range(20, 26):
    from collections import Counter
    bands[y] = Counter(map(tuple, rgb[[i*27+y for i in range(9)]][:, 14:100].reshape(-1,3))).most_common(1)[0][0]
syn = np.zeros((H, Wd, 3), np.uint8)
for y in range(H):
    for x in range(Wd):
        if y >= 26: syn[y,x] = cons[y,x]
        elif y >= 20: syn[y,x] = bands[y]
        else:
            e = 12 + max(0, (x-28)) * 2.0/52 if x <= 80 else 14
            syn[y,x] = GRAY if y < round(e) else BLK
bg = np.where(ok[..., None], cons, syn)
# vùng trung tâm (x 12..104, y 0..19): chỉ tin consensus ở nơi pixel là xám/đen/AA cạnh (đã lọc bởi sure & cnt>=4)
for i,(cn,vn,col) in enumerate(items):
    y0 = i*27
    I.rgba[y0:y0+26, :, :3] = bg[:26]
body = WH
for i,(cn,vn,col) in enumerate(items):
    y0 = i*27
    ol = CY if col=='c' else RD
    bb = draw_text(I, vn, 18, (2, y0+3, 112, 21), body, ol, clip=(0,y0,116,y0+26))
    I.changed_boxes.append((0, y0, 116, 26))
    log_row(I, (0,y0,116,26), cn, vn, '18', body, ol, 'nền dựng lại: consensus 9 dòng + tổng hợp; ink bbox %s' % (bb,))

finish(I)
