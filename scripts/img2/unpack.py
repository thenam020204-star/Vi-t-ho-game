import sys,os
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from cont import *
for n in ('uires','animation'):
    c=parse(open(JX+n+'.bin','rb').read())
    for i,b in enumerate(c.recs):
        open(W+'/img2/png_goc/%s_%02d_off%d.png'%(n,i,b.png_off),'wb').write(b.png)
