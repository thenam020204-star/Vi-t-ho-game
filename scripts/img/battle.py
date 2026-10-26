import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
CFG = {1:('讨逆董卓','Thảo phạt Đổng Trác','5adebb','002f42'),
       2:('官渡拉锯','Quan Độ giằng co','17e2ff','091741'),
       3:('火烧赤壁','Hỏa thiêu Xích Bích','f85422','490202'),
       4:('樊城之战','Phàn Thành chiến','fff32f','4e2c0f'),
       5:('特殊关卡','Ải đặc biệt','ee8597','270114')}
for n,(cn,vn,b,o) in CFG.items():
    I = Img('bin/battle/battle%d.png' % n)
    rgb = I.orig[..., :3].copy()
    ol = hexc(o)
    m = np.all(rgb == ol, axis=-1)
    z = np.zeros_like(m); z[3:23, 8:64] = True
    m &= z
    m[:12, :12] = False   # góc hoạ tiết
    mask = dilate(m, 2) & z
    mask[:12, :11] = False
    out = inpaint(rgb, mask, 3, 'ns')
    pal = [tuple(I.im.getpalette()[i*3:i*3+3]) for i in range(len(I.im.getpalette())//3)]
    out = snap_to_palette(out, mask, pal)
    I.rgba[..., :3] = np.where(mask[..., None], out, I.rgba[..., :3])
    bb = draw_text(I, vn, '13c', (10, 4, 130, 17), hexc(b), ol, align='left', valign='center')
    erase = (8, 3, 56, 20)
    I.changed_boxes += [erase, bb]
    log_row(I, bb, cn, vn, '13c', hexc(b), ol, 'nền: inpaint dưới chữ cũ (mask=viền gốc giãn 2px); căn trái tại x=10 như gốc; không có khung nhãn nên không mở rộng')
    finish(I)
