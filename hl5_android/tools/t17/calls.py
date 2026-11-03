import re,sys,collections,pickle,os
P='/tmp/claude-0/s17/funcs.pkl'
if os.path.exists(P):
    F=pickle.load(open(P,'rb'))
else:
    F={}  # name -> (addr, lines)
    cur=None
    hdr=re.compile(r'^([0-9a-f]{8}) <(.*)>:$')
    for ln in open(os.path.expanduser('~/hl5work/so.dis')):
        m=hdr.match(ln)
        if m:
            cur=(int(m.group(1),16),m.group(2)); F[cur]=[]; continue
        if cur: F[cur].append(ln.rstrip('\n'))
    pickle.dump(F,open(P,'wb'))
byname={}
for (a,n),l in F.items(): byname.setdefault(n,[]).append((a,l))
byaddr={a:(n,l) for (a,n),l in F.items()}
bl=re.compile(r'\t(bl|b|blx)\t0x([0-9a-f]+) <([^>]*)>')
def calls(a):
    n,l=byaddr[a]; c=collections.Counter()
    for x in l:
        m=bl.search(x)
        if m and m.group(1) in('bl','b'): c[m.group(3)]+=1
    return c
if __name__=='__main__':
    skip=re.compile(sys.argv[2] if len(sys.argv)>2 else r'^$')
    for s in sys.argv[1].split(','):
        a=int(s,16); n,l=byaddr[a]
        c=calls(a)
        print('##',hex(a),n,len(l),'lines')
        print('  '+'; '.join('%s x%d'%(k,v) for k,v in sorted(c.items(),key=lambda kv:kv[0]) if not skip.search(k)))
