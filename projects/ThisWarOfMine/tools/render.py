import sys,os,numpy as np
from PIL import Image
sys.path.insert(0,os.path.dirname(__file__)); import fontfmt
def render(data,text,scale=1,pad=4):
    f=fontfmt.parse(data); bpp=f['bpp']
    pages=[np.frombuffer(p,np.uint8).reshape(h,1024,bpp) for p,h in zip(f['pages'],f['H'])]
    G={g[0]:g for g in f['G']}; lh=int.from_bytes(f['tail'][:4],'little')
    lines=text.split('\n'); Wd=0
    for ln in lines: Wd=max(Wd,sum(G[ord(c)][4] if ord(c) in G else 8 for c in ln))
    img=np.zeros(((lh+8)*len(lines)+2*pad+60, Wd+2*pad+20),np.uint8)
    y=pad+40
    for ln in lines:
        x=pad+10
        for c in ln:
            g=G.get(ord(c))
            if not g: x+=8; img[y:y+lh, x-6:x-2]=128; continue
            _,pi,xo,yo,adv,x0,x1,y0,y1=g
            a=pages[pi][y0:y1,x0:x1,0]
            ty,tx=y-yo,x+xo
            sl=img[ty:ty+a.shape[0],tx:tx+a.shape[1]]; np.maximum(sl,a[:sl.shape[0],:sl.shape[1]],out=sl)
            x+=adv
        y+=lh+8
    im=Image.fromarray(img)
    return im.resize((im.width*scale,im.height*scale),Image.NEAREST) if scale>1 else im
