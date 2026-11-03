import calls,re,sys,collections
def fields(addr, maxn=40):
    n,l=calls.byaddr[addr]
    this=None; offs=collections.Counter()
    regs={'r0'}  # registers aliasing this
    for x in l[:400]:
        t=x.split('\t',1)[-1] if '\t' in x else x
        m=re.search(r'\tmov\t(r\d+), r0$',x)
        if m and this is None: regs.add(m.group(1))
    for x in l:
        m=re.search(r'\t(ldr[a-z]*|str[a-z]*)\t(r\d+), \[(r\d+), #(0x[0-9a-f]+|\d+)\]',x)
        if m and m.group(3) in regs:
            o=int(m.group(4),0); offs[(o,m.group(1)[0:3]+('b' if 'b' in m.group(1) else 'h' if 'h' in m.group(1) else ''))]+=1
    return regs,offs
if __name__=='__main__':
    for s in sys.argv[1:]:
        a=int(s,16); r,o=fields(a)
        print(hex(a),calls.byaddr[a][0].split('(')[0],sorted(r))
        print('  ',' '.join('+%#x:%s%d'%(k[0],k[1],v) for k,v in sorted(o.items())[:60]))
