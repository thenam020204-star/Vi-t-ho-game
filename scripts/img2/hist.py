import sys,os
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from ann import *
import numpy as np
from collections import Counter
n,o=sys.argv[1],int(sys.argv[2]); ks=[int(x) for x in sys.argv[3:]]
b=load(n,o); im=np.array(Image.open(glob.glob(W+'/img2/png_goc/%s_*_off%d.png'%(n,o))[0]).convert('RGBA'))
M=mods(b)
for k in ks:
    x,y,w,h=M[k]; c=im[y:y+h,x:x+w]
    print('cell',k,M[k])
    cnt=Counter(map(tuple,c.reshape(-1,4)))
    print(' ',[('%02x%02x%02x'%t[:3]+('' if t[3]==255 else 'a%d'%t[3]),v) for t,v in cnt.most_common(12)])
    for yy in range(h):
        print('  ',' '.join('%02x%02x%02x'%tuple(c[yy,xx,:3]) if c[yy,xx,3] else '------' for xx in range(min(w,4))))
