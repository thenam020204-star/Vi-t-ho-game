import re,sys,pickle
L=open('/root/hl5work/so.dis').read().split('\n')
funcs=pickle.load(open('/tmp/funcs.pk','rb'))
pat=sys.argv[1]; 
for k,(a,n,i) in enumerate(funcs):
    if re.search(pat,n):
        j=funcs[k+1][2]
        print('==',n[:70])
        for x in range(i,j):
            l=L[x]
            if re.search(r'GetSubState|GetState\(|GetPopupState',l) or re.search(r'ldrs?b\s+r\d+, \[r\d+, #(0xd|13)\]',l):
                for y in range(x,min(x+7,j)):
                    s=L[y]
                    if re.search(r'cmp|addls|beq|bne|bhi|bls|bgt|ble|blt|bge|Get|ldrs?b|tst|ands',s): print('   ',s.strip()[:100])
                print('   --')
