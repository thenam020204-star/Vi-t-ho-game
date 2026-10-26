import sys,os,struct
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from cont import *
class R:
    def __init__(s,d): s.d=d; s.p=0
    def sh(s): v=struct.unpack('>h',s.d[s.p:s.p+2])[0]; s.p+=2; return v
    def by(s): v=struct.unpack('>b',s.d[s.p:s.p+1])[0]; s.p+=1; return v
    def it(s): v=struct.unpack('>i',s.d[s.p:s.p+4])[0]; s.p+=4; return v
    def sa(s,n): return [s.sh() for _ in range(n)]
    def ba(s,n): v=list(s.d[s.p:s.p+n]); s.p+=n; return v
def parse_cf(d):
    r=R(d); o={}
    a=r.sh(); o['a']=a; o['b']=r.sa(a)
    o['c']=[r.sa(r.sh()) for _ in range(a)]
    o['d']=[r.sa(r.by()) for _ in range(a)]
    e=r.sh(); o['f']=[r.it() for _ in range(e)]
    g=r.sh(); o['h']=r.ba(g*2); o['i']=r.ba(g); o['j']=r.sa(g+1)
    k=r.sh(); o['l']=[r.it() for _ in range(k)]
    m=r.sh(); o['n']=r.ba(m); o['o']=r.ba(m); o['p']=r.sa(m+1)
    q=r.sh(); o['r']=r.sa(q*2)
    s=r.sh(); t=r.sh(); o['u']=r.sa(s*4); o['v']=r.sa(t*4)
    w=r.sh(); o['x']=r.ba(w*5)
    o['end']=r.p; o['m']=m
    return o
def se(v): return v-512 if v&0x100 else v
def anims(o):
    res={}
    for d in range(o['m']):
        frs=[]
        for fr in range(o['o'][d]):
            n6=o['r'][o['p'][d]+(fr<<1)]&0x3FF
            pcs=[]
            for i in range(o['i'][n6]):
                v=o['l'][o['j'][n6]+i]&0xFFFFFFFF
                n8=v>>18&0x3FF; x=se(v>>9&0x1FF); y=se(v&0x1FF); fl=v>>28&7
                f=o['f'][n8]&0xFFFFFFFF
                pcs.append((f>>16,f&0xFFFF,x,y,fl))
            frs.append(pcs)
        res[d]=frs
    return res
if __name__=='__main__':
    n=sys.argv[1]
    c=parse(open(JX+n+'.bin','rb').read())
    ci=c.ci_blob
    print(len(ci))
    ids=[]
    # ci blob may hold several cf (nci)
    for k in range(c.nci):
        blob=ci[c.offci[k]-c.offci[0]:c.offci[k+1]-c.offci[0]]
        o=parse_cf(blob); print('cf',k,'len',len(blob),'parsed end',o['end'],'a',o['a'],'m',o['m'])
        A=anims(o)
        want=set(int(x) for x in sys.argv[2:])
        for d,frs in A.items():
            used={(p[0]) for f in frs for p in f}
            if want & used:
                print(' anim',d,'frames',len(frs))
                for i,f in enumerate(frs[:4]): print('   fr',i,[p for p in f if p[0] in want])
