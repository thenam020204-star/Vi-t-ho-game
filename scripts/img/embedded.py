import sys,os,struct,zlib,io
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from lib import W
from PIL import Image
sig=b'\x89PNG\r\n\x1a\n'
out=W+'/img/embedded'; os.makedirs(out,exist_ok=True)
rep=[]
for name in ('animation.bin','uires.bin'):
    d=open(W+'/jx/bin/'+name,'rb').read(); pos=0; k=0
    while True:
        i=d.find(sig,pos)
        if i<0: break
        # duyệt chunk tới IEND
        p=i+8
        while p+8<=len(d):
            ln,=struct.unpack('>I',d[p:p+4]); t=d[p+4:p+8]; p+=12+ln
            if t==b'IEND': break
        blob=d[i:p]; fn='%s_%02d_off%d.png'%(name.replace('.bin',''),k,i)
        open(out+'/'+fn,'wb').write(blob)
        try:
            im=Image.open(io.BytesIO(blob)); sz=im.size; md=im.mode
        except Exception as e: sz=md='ERR %s'%e
        rep.append((name,i,len(blob),sz,md,fn)); k+=1; pos=p
for r in rep: print(r)
print(len(rep))
