import re,pickle,collections
L=open('/root/hl5work/so.dis').read().split('\n')
funcs=pickle.load(open('/tmp/funcs.pk','rb'))
import bisect
starts=[f[2] for f in funcs]
def fn(i):
    k=bisect.bisect_right(starts,i)-1; return funcs[k][1].split('(')[0], funcs[k][0]
res=collections.defaultdict(list)
for i,l in enumerate(L):
    m=re.search(r'\bbl\s+0x[0-9a-f]+ <(State\w+)::(SetState|SetSubState|SetPopupState)\(',l)
    if not m: continue
    cls,kind=m.groups()
    # find mov r1,#imm backwards (up to 8 lines) ; for SetState with 2 args (r1=state)
    imm=None
    for y in range(i-1,max(i-9,0),-1):
        mm=re.search(r'\b(mov|mvn)\s+r1, #(\w+)',L[y])
        if mm: imm=('-' if mm.group(1)=='mvn' else '')+mm.group(2); break
        if re.search(r'\bbl\b',L[y]): break
    f,a=fn(i)
    res[(cls,kind)].append((imm,f,hex(a)))
pickle.dump(dict(res),open('/tmp/w/setters.pk','wb'))
for k in sorted(res):
    c=collections.defaultdict(set)
    for imm,f,a in res[k]: c[imm].add(f)
    print(k)
    for imm in sorted(c,key=lambda x:(x is None,str(x))): print('   ',imm,sorted(c[imm])[:6])
