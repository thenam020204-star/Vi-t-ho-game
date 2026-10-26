import sys,os
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from build_uires import *
rows=[]; res={}
for off,spec in SPEC.items():
    c,idx,P,cv,ch=run('uires',off,spec,rows)
    new=P.encode(cv.rgba)
    # kiểm tra
    q=Png(new); assert (q.w,q.h,q.ct)==(P.w,P.h,P.ct)
    d=np.any(q.rgba!=P.rgba,axis=-1); ins=np.zeros_like(d)
    for (x,y,w,h) in ch: ins[y:y+h,x:x+w]=True
    out=int((d&~ins).sum())
    print(off,'ngoài box:',out,'đổi:',int(d.sum()),'palette len',len(q.pal) if q.pal else '-')
    res[off]=(idx,new,P,q,ch)
    open(W+'/img2/png_viet/uires_%02d_off%d.png'%(idx,off),'wb').write(new)
import pickle
pickle.dump((rows,{k:(v[0],v[1],v[4]) for k,v in res.items()}),open('/tmp/uires_res.pkl','wb'))
