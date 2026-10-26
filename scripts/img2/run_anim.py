import sys,os,glob,pickle
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from eng import *
import cv2
def load_png(n,o):
    c=parse(open(JX+n+'.bin','rb').read()); b=[r for r in c.recs if r.png_off==o][0]; return c.recs.index(b),Png(b.png)
def hx(c): return '#%02x%02x%02x'%tuple(c)
def dil(m,k=1):
    return lib.dilate(m,k)
def quant_mode(px):
    q=Counter(tuple((np.array(p[:3])//24).tolist()) for p in px)
    k=q.most_common(1)[0][0]
    sel=[p[:3] for p in px if tuple((np.array(p[:3])//24).tolist())==k]
    return tuple(int(v) for v in np.mean(sel,0))
def brush(off,text,size,body=None,outline=(30,8,8),shift=(0,0),lumT=70):
    idx,P=load_png('animation',off); a=P.rgba.copy(); al=a[...,3]; amax=int(al.max())
    lum=a[...,:3].astype(int).max(-1)
    tm=(lum>=lumT)&(al>0)
    ys,xs=np.nonzero(tm&(al>=amax*0.7)); bb=(xs.min(),ys.min(),xs.max(),ys.max())
    bright=[tuple(p) for p in a[tm&(al>=amax*0.7)&(lum>=150)]]
    if body is None: body=quant_mode(bright)
    mask=dil(tm,1)
    rgb=cv2.inpaint(np.ascontiguousarray(a[...,:3]),(mask*255).astype(np.uint8),3,cv2.INPAINT_TELEA)
    al2=cv2.inpaint(np.ascontiguousarray(al),(mask*255).astype(np.uint8),3,cv2.INPAINT_TELEA)
    new=np.dstack([rgb,al2]).astype(np.uint8)
    # chỉ thay vùng mask
    out=a.copy(); out[mask]=new[mask]
    cx=(bb[0]+bb[2])//2+shift[0]; cy=(bb[1]+bb[3])//2+shift[1]
    px=text_pixels(text,size)
    d,bbx=paint(out,px,cx,cy,body,outline,amax)
    chg=mask|d
    ys,xs=np.nonzero(chg); rect=(int(xs.min()),int(ys.min()),int(xs.max()-xs.min()+1),int(ys.max()-ys.min()+1))
    return idx,P,out,[rect],(text,size,body,outline,bb,bbx,amax)

def text_pixels(text,size,bold=False,gap=1):
    """ghép từng ký tự theo vết mực thật (chừa gap px giữa các ký tự) để chữ đậm không dính nhau"""
    px=set(); cx=0; ytop=None
    for ch in text:
        if ch==' ': cx+=5; continue
        m=lib.pt.layout(ch,size,mode='metric',gap=1); P=list(m.pixels)
        x0=min(p[0] for p in P); x1=max(p[0] for p in P)
        g={(x-x0+cx,y) for x,y in P}
        if bold: g|={(x+1,y) for x,y in list(g)}
        px|=g; cx=max(p[0] for p in g)+1+gap
    x0=min(p[0] for p in px); y0=min(p[1] for p in px)
    return {(x-x0,y-y0) for x,y in px}
def paint(out,px,cx,cy,body,outline,alpha,outer=None,k=1):
    """px: tập (x,y) gốc 0; canh giữa tại cx,cy; viền 1px (8 hướng); outer: viền ngoài thứ 2"""
    w=max(p[0] for p in px)+1; h=max(p[1] for p in px)+1
    ox=cx-w//2; oy=cy-h//2
    body_px={(x+ox,y+oy) for x,y in px}
    def grow(S):
        return S|{(x+a,y+b) for x,y in S for a in(-1,0,1) for b in(-1,0,1)}
    o1=body_px
    for _ in range(k): o1=grow(o1)
    o1=o1-body_px
    allp=body_px|o1
    o2=set()
    if outer is not None: o2=grow(allp)-allp
    H,W_=out.shape[:2]; new=np.zeros((H,W_),bool)
    for S,col in ((o2,outer),(o1,outline),(body_px,body)):
        for x,y in S:
            if 0<=x<W_ and 0<=y<H:
                out[y,x]=(*col,alpha); new[y,x]=True
    return new,(ox-k-1,oy-k-1,w+2*k+2,h+2*k+2)
