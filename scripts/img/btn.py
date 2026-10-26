import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np
from lib import *
def run(rel, panel, keepcols, vn, cn, body, ol):
    I = Img(rel)
    a = I.rgba
    keep = np.zeros(a.shape[:2], bool)
    for c in keepcols: keep |= np.all(a == c, axis=-1)
    er = ~keep
    a[er] = panel
    box = (5, 3, 26, 15)
    bb = draw_text(I, vn, 12, (0, 0, 36, 21), body, ol, strict_fit=False, clip=(5,3,31,18))
    I.changed_boxes.append((4, 2, 28, 17))
    log_row(I, box, cn, vn, '12', body, ol, 'ink %s; gốc không viền? có viền %s' % (bb, '#%02x%02x%02x' % ol if ol else '-'))
    finish(I)
run('bin/caidan.png', (0x62,0x62,0x62,255), [(0xff,0xff,0xff,0),(0xcd,0xcd,0xcd,255),(0x9d,0x9d,0x9d,255),(0x62,0x62,0x62,255)], 'Menu','菜单',hexc('15e0e8'),hexc('27293d'))
run('bin/shop.png', (9,9,9,0xa3), [(9,9,9,0),(9,9,9,0x34),(9,9,9,0x66),(9,9,9,0xa3)], 'Shop','商店',hexc('ffae00'),hexc('5b1313'))
