import re,sys
# find adrp/add pairs resolving to target addr in br.dis
tgt=int(sys.argv[1],16)
L=open('br.dis').read().split('\n')
pg={}
for i,l in enumerate(L):
    m=re.search(r'^\s+([0-9a-f]+):\s+adrp\s+(x\d+), 0x([0-9a-f]+)',l)
    if m: pg[m.group(2)]=(int(m.group(3),16),i); continue
    m=re.search(r'^\s+([0-9a-f]+):\s+add\s+(x\d+), (x\d+), #0x([0-9a-f]+)',l)
    if m and m.group(3) in pg:
        a=pg[m.group(3)][0]+int(m.group(4),16)
        if a==tgt: print(m.group(1))
