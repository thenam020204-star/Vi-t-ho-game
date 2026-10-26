import sys,os,struct,zlib
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from cont import *
def chunks(png):
    p=8;o=[]
    while p<len(png):
        n=struct.unpack('>i',png[p:p+4])[0]; t=png[p+4:p+8]; o.append((t,png[p+8:p+8+n])); p+=12+n
    return o
for n in ('uires.bin','animation.bin'):
    c=parse(open(JX+n,'rb').read())
    for i,b in enumerate(c.recs):
        ch=chunks(b.png); ihdr=ch[0][1]; w,h,bd,ct=struct.unpack('>iiBB',ihdr[:10])
        pl=[x for t,x in ch if t==b'PLTE']; 
        info=''
        if b.h>0:
            pt=b.head[-4-b.g*b.h:-4]
            info='PLTE=%d h=%d g=%d tbl==PLTE:%s'%(len(pl[0]),b.h,b.g,pt[:len(pl[0])]==pl[0])
        print(n,i,'off',b.png_off,w,h,'bd',bd,'ct',ct,[t.decode() for t,_ in ch],info)
