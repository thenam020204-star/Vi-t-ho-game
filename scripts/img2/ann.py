import sys,os,struct
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from cont import *
from PIL import Image, ImageDraw
import glob
def mods(b):
    d=b.d; v=struct.unpack('>%dh'%(4*d),b.head[3:3+8*d]); e=v[:2*d]; f=v[2*d:]
    return [(f[2*k],f[2*k+1],e[2*k],e[2*k+1]) for k in range(d)]
def load(n,o):
    c=parse(open(JX+n+'.bin','rb').read())
    return [r for r in c.recs if r.png_off==o][0]
if __name__=='__main__':
    n,o,S=sys.argv[1],int(sys.argv[2]),int(sys.argv[3])
    b=load(n,o); im=Image.open(glob.glob(W+'/img2/png_goc/%s_*_off%d.png'%(n,o))[0]).convert('RGBA')
    bg=Image.new('RGBA',im.size,(255,0,255,255)); bg.alpha_composite(im)
    bg=bg.resize((im.width*S,im.height*S),Image.NEAREST).convert('RGB'); d=ImageDraw.Draw(bg)
    cols=[(255,255,0),(0,255,255),(255,128,0),(0,255,0)]
    for k,(x,y,w,h) in enumerate(mods(b)):
        d.rectangle([x*S,y*S,(x+w)*S-1,(y+h)*S-1],outline=cols[k%4]); d.text((x*S+2,y*S+1),str(k),fill=cols[k%4])
    bg.save(sys.argv[4])
