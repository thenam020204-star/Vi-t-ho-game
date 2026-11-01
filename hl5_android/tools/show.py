import sys,bisect
L=open(sys.argv[1]).read().split('\n')
idx={}
for i,l in enumerate(L):
    if len(l)>9 and l[8]==':' : 
        try: idx[int(l[:8].strip(),16)]=i
        except: pass
a=int(sys.argv[2],16); n=int(sys.argv[3])
i=idx.get(a)
if i is None:
    for i,l in enumerate(L):
        if l.strip().startswith('%x:'%a): break
print('\n'.join(L[i:i+n]))
