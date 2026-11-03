import calls,re,sys
skip=re.compile(r'^(Graphic::|BFont::|GbmImage::|SPRITE|IRect::|Texture2D|MX_|MC_|.*\+0x|__aeabi|memcpy|memset|strlen|sprintf|StateInGameMenu::GetState|BaseState::(Get|Set)|CommonUi::Draw(Box|Window|Frame)|_ZN|operator|Frame|Gbm)')
def prof(prefix,pat,show_skip=False):
    for (a,n),l in sorted(calls.F.items()):
        if n.startswith(prefix) and re.search(pat,n):
            c=calls.calls(a)
            items=[(k,v) for k,v in sorted(c.items()) if show_skip or not skip.search(k)]
            print('## %x %s (%d lines)'%(a,n,len(l)))
            print('   '+'; '.join('%s x%d'%(k,v) if v>1 else k for k,v in items))
if __name__=='__main__':
    prof(sys.argv[1],sys.argv[2])
