import sys,re
from capstone import *
from capstone.arm64 import *
from elftools.elf.elffile import ELFFile
import os
so=os.environ['BR_ORIG']
data=open(so,'rb').read(); elf=ELFFile(open(so,'rb'))
segs=[(s['p_vaddr'],s['p_offset'],s['p_filesz']) for s in elf.iter_segments() if s['p_type']=='PT_LOAD']
def rd(va,n):
    for v,o,f in segs:
        if v<=va<v+f: return data[o+va-v:o+va-v+n]
md=Cs(CS_ARCH_ARM64,CS_MODE_ARM); md.detail=True
def ins(va):
    for i in md.disasm(rd(va,4),va): return i
def regnum(r,i):
    n=i.reg_name(r)
    m=re.fullmatch(r'[xw](\d+)',n)
    return int(m.group(1)) if m else (29 if n in('x29','w29','fp') else 30 if n in('x30','lr') else None)
def livein(start,maxn=4000):
    # path-insensitive: for each path, regs read before written; union over paths (DFS with visited per (addr,writtenset))
    res={}; seen=set(); stack=[(start,frozenset())]
    while stack:
        a,w=stack.pop()
        while True:
            if (a,w) in seen: break
            seen.add((a,w))
            if len(seen)>200000: return res
            i=ins(a)
            if i is None: break
            rr,ww=i.regs_access()
            for r in rr:
                n=regnum(r,i)
                if n is not None and n<=28 and n not in w and n not in res: res[n]=hex(a)
            nw=set(w)
            for r in ww:
                n=regnum(r,i)
                if n is not None: nw.add(n)
            if i.mnemonic in('bl','blr'): nw|=set(range(0,19))|{30}
            w=frozenset(nw)
            if i.mnemonic=='ret' : break
            if i.mnemonic in('b',): a=i.operands[0].imm; continue
            if i.mnemonic in('br',): break
            if i.mnemonic.startswith('b.') or i.mnemonic in('cbz','cbnz','tbz','tbnz'):
                stack.append((i.operands[-1].imm,w))
            a+=4
    return res
if __name__=='__main__':
    print({k:v for k,v in sorted(livein(int(sys.argv[1],16)).items())})
