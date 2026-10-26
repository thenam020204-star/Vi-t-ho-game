import sys,os; sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
import numpy as np, cv2
from lib import *
def build_mask(rgb):
    r,g,b = [rgb[...,i].astype(int) for i in range(3)]
    mx = rgb.max(-1).astype(int); mn = rgb.min(-1).astype(int)
    red = (r>95)&(g<62)&(b<66)&(r-g>70)&(r-b>60)
    dkred = (r>45)&(g<28)&(b<30)&(r-g>30)
    orange = (r>190)&(b<95)&(r-b>120)&(g>40)
    cream = (r>185)&(g>165)&(b>120)&(mx-mn<100)
    box1 = np.zeros(r.shape,bool); box1[58:194,36:206]=True
    box2 = np.zeros(r.shape,bool); box2[194:242,40:204]=True
    core = ((red|dkred)&box1) | ((red|dkred|orange)&box2)
    k = np.ones((3,3),np.uint8)
    core_u = core.astype(np.uint8)
    near = cv2.dilate(core_u, np.ones((9,9),np.uint8))>0   # cream trong 4px quanh thân đỏ
    cr = cream & near & (box1|box2)
    sil = (core|cr).astype(np.uint8)
    sil = cv2.morphologyEx(sil, cv2.MORPH_CLOSE, np.ones((5,5),np.uint8))
    # bỏ nhiễu lẻ (thành phần nhỏ)
    n,lab,st,_ = cv2.connectedComponentsWithStats(sil, connectivity=8)
    keep = np.zeros_like(sil)
    for i in range(1,n):
        if st[i,cv2.CC_STAT_AREA]>=25: keep[lab==i]=1
    m = keep > 0
    # loại bỏ chỗ nhầm (quần áo cô gái trên cùng)
    yy, xx = np.mgrid[0:r.shape[0], 0:r.shape[1]]
    m &= ~(((xx >= 112) & (yy < 96)) | ((xx >= 96) & (yy < 68)))
    tan = (r>100)&(g>88)&(b>55)&(mx-mn<95)&(r-b>15)
    outl = ((mx < 66) | cream | tan)
    outl_u = outl.astype(np.uint8)
    cur = m.copy()
    for i in range(6):   # mọc dần vào vùng viền đen/kem quanh chữ
        grow = cv2.dilate(cur.astype(np.uint8), np.ones((3,3),np.uint8)) > 0
        cur = cur | (grow & outl)
    cur = cv2.dilate(cur.astype(np.uint8), np.ones((3,3),np.uint8)) > 0
    cur &= ~(((xx >= 112) & (yy < 96)) | ((xx >= 96) & (yy < 68)))
    return cur
if __name__=='__main__':
    I=Img('bin/fengmian.png'); rgb=I.orig[...,:3]
    mask=build_mask(rgb)
    np.save('/tmp/fm_mask.npy',mask)
    ov=rgb.copy(); ov[mask]=(ov[mask]*0.4+np.array([255,0,255])*0.6).astype(np.uint8)
    from PIL import Image
    Image.fromarray(ov).crop((30,55,210,245)).resize((180*5,190*5),Image.NEAREST).save(W+'/img/prev/fm_ov.png')
