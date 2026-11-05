import re,pickle,collections,bisect
L=open('/root/hl5work/so.dis').read().split('\n')
funcs=pickle.load(open('/tmp/funcs.pk','rb')); starts=[f[2] for f in funcs]
res=collections.defaultdict(lambda: collections.defaultdict(set))
for i,l in enumerate(L):
    m=re.search(r'ldr\s+pc, \[r\d+, #(0x[0-9a-f]+)\]',l)
    if not m or m.group(1) not in ('0x70','0x74','0x78','0x7c','0x80'): continue
    imm=None
    for y in range(i-1,max(i-30,0),-1):
        mm=re.search(r'\b(mov|mvn)\s+r1, #(-?\w+)',L[y])
        if mm: imm=mm.group(2); break
        if re.search(r'\bbl\b',L[y]) and 'GetCommonText' not in L[y]  and 'Sprintk' not in L[y]: break
    k=bisect.bisect_right(starts,i)-1
    res[m.group(1)][imm].add(funcs[k][1].split('(')[0])
for s in sorted(res):
    print('slot',s)
    for imm in sorted(res[s],key=str): print('  type',imm,sorted(res[s][imm]))
