import sys, os, argparse, struct
sys.path.insert(0,'/home/user/Vi-t-ho-game/hl5_android/harness')
from hl5run import Guest, BASE
from hl5libc import boot, KEYS, FRAME_MS
from unicorn import *; from unicorn.arm_const import *
out='/root/hl5work/t15a/cls'; os.makedirs(out,exist_ok=True)
keys='460:OK,560:OK,640:OK,720:OK'
a=argparse.Namespace(so='/root/hl5work/build_apk/libHeroesLore5.so',vfs='/root/hl5work/build_apk/data.vfs.mp3',out=out,w=600,h=270,frames=0,shot=0,keys='',touch='',storage=out+'/st',reslog=False)
os.makedirs(a.storage,exist_ok=True)
g=Guest(a); boot(g); uc=g.uc
names={0x41334:'DrawText9',0x418dc:'DrawText6a',0x41d78:'DrawText6b',0x41818:'DrawText4',0x41878:'DrawText5',0x41940:'DrawTextLine',0x41010:'GetLineCount',0x41080:'GetTextFieldPageLen'}
log=[]
def mk(n):
    def h(u,addr,size,ud):
        if not (g.frame>=722): return
        r=[u.reg_read(UC_ARM_REG_R0+i) for i in range(4)]; sp=u.reg_read(UC_ARM_REG_SP)
        st=[g.rd32(sp+4*i) for i in range(6)]
        # find string arg
        sv=''
        for v in r[1:]+st:
            if BASE<v<0x70400000 or 0x20000000<v<0x28000000:
                try:
                    s=bytes(uc.mem_read(v,24)); 
                    if all(32<=c<127 or c in (0,59) or c>=0xc9 or c>=0xa1 for c in s[:6]) and s[0]: sv=repr(s[:20]); break
                except: pass
        lr=u.reg_read(UC_ARM_REG_LR)
        log.append('f%d %s r=%s st=%s str=%s lr=%s'%(g.frame,n,[hex(x) if x>9999 else x for x in r[1:]],[x if x<100000 else hex(x) for x in st[:4]],sv,g.sym_of(lr)))
    return h
for ad,n in names.items(): uc.hook_add(UC_HOOK_CODE,mk(n),begin=BASE+ad,end=BASE+ad)
loop=g.sym('Java_co_kr_eamobile_CletEntry_nativeLoop'); keyf=g.sym('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
ev={}
for it in keys.split(','):
    f,k=it.split(':'); ev.setdefault(int(f),[]).append(k)
for fr in range(724):
    g.frame=fr
    for k in ev.get(fr,[]):
        c=KEYS[k]; g.call(keyf,g.env,0,2,c&0xffffffff,0); g.call(keyf,g.env,0,3,c&0xffffffff,0)
    g.vclock+=FRAME_MS; g.call(loop,g.env,0)
open(out+'/log.txt','w').write('\n'.join(log))
