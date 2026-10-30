import struct
NL=14
def parse(r):
    tot,kb=struct.unpack('<II',r[:8]); assert tot==len(r)-4
    K=r[8:8+kb]; p=8+kb
    n,=struct.unpack('<I',r[p:p+4]); p+=4
    pairs=[struct.unpack('<II',r[p+8*i:p+8*i+8]) for i in range(n)]; p+=8*n
    n2,=struct.unpack('<I',r[p:p+4]); p+=4; assert n2==n
    recs=[]
    def u16(p):
        e=p
        while r[e:e+2]!=b'\0\0': e+=2
        return r[p:e].decode('utf-16-le'),e+2
    for i in range(n):
        pre=r[p:p+5]; p+=5; S=[]
        for j in range(NL): s,p=u16(p); S.append(s)
        A=[]
        for j in range(4):
            e=r.index(b'\0',p); A.append(r[p:e]); p=e+1
        post=r[p:p+9]; p+=9
        recs.append(dict(pre=pre,S=S,A=A,post=post))
    assert p==len(r)
    keys={}
    for idx,ko in pairs: keys[idx]=K[ko:K.index(b'\0',ko)].decode()
    return dict(K=K,pairs=pairs,recs=recs,keys=keys)
def build(L):
    body=bytearray()
    for rc in L['recs']:
        body+=rc['pre']
        for s in rc['S']: body+=s.encode('utf-16-le')+b'\0\0'
        for a in rc['A']: body+=a+b'\0'
        body+=rc['post']
    n=len(L['recs'])
    o=bytearray(struct.pack('<I',len(L['K'])))+L['K']+struct.pack('<I',n)
    for a,b in L['pairs']: o+=struct.pack('<II',a,b)
    o+=struct.pack('<I',n)+body
    return struct.pack('<I',len(o))+bytes(o)
