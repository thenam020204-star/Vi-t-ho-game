import re,sys,pickle
L=open('/root/hl5work/so.dis').read().split('\n')
funcs=pickle.load(open('/tmp/funcs.pk','rb'))
def rng(pat):
    out=[]
    for k,(a,n,i) in enumerate(funcs):
        if re.search(pat,n):
            j=funcs[k+1][2] if k+1<len(funcs) else len(L)
            out.append((a,n,i,j))
    return out
def scan(i,j):
    res=[]
    for x in range(i,j):
        l=L[x]
        if re.search(r'bl\s+0x[0-9a-f]+ <\w+::(Get(Sub)?State|GetPopupState)\(\)>',l) or re.search(r'ldrs?b\s+r\d+, \[r\d+, #(0xc|0xd|0xe|12|13|14)\]',l):
            tag='GS' if 'GetState' in l else 'SUB' if 'GetSub' in l else 'POP' if 'GetPop' in l else 'LD'+l.split('#')[-1].strip(']')
            ctx=[]
            for y in range(x+1,min(x+8,j)):
                m=re.search(r'\bcmp\s+r\d+, #(\w+)',L[y]) 
                if m: ctx.append('cmp'+m.group(1))
                if 'addls\tpc' in L[y]: ctx.append('SWITCH')
                if re.search(r'\bsub[s]?\s+r\d+, r\d+, #',L[y]) and 'sp' not in L[y]: ctx.append('sub'+L[y].split('#')[-1])
            res.append((L[x].split(':')[0].strip(),tag,ctx))
    return res
if __name__=='__main__':
    for a,n,i,j in rng(sys.argv[1]):
        print('==',hex(a),n[:60])
        for r in scan(i,j): print('  ',*r)
