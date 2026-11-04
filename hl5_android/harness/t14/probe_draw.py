import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
import struct
W,Hh,mode,f0=int(sys.argv[1]),int(sys.argv[2]),sys.argv[3],int(sys.argv[4])
r=mk(W,Hh,mode,'pd'); g=r.g; uc=g.uc
ON=[False]
N={0x5ae20:'Blend',0x5b3cc:'BlendRot',0x5b328:'Effect',0x53c3c:'Image',0x56ee8:'Stretch',0x5085c:'CoolTime',0x41d78:'Text1',0x418dc:'Text2',0x4e684:'FillAlpha',0x5b150:'EffClip',0x5af20:'BlendClip',0x5bb48:'Ring',0x74888:'InfoRect',0x5d198:'FillRect'}
out=[]
def hk(uc,a,s,u):
    if not ON[0]: return
    R=[uc.reg_read(x) for x in (UC_ARM_REG_R0,UC_ARM_REG_R1,UC_ARM_REG_R2,UC_ARM_REG_R3)]
    sp=uc.reg_read(UC_ARM_REG_SP); st=struct.unpack('<6i',bytes(uc.mem_read(sp,24)))
    lr=uc.reg_read(UC_ARM_REG_LR)-BASE
    sg=lambda v:v if v<0x80000000 else v-2**32
    print('fr%d %s lr=%x r=%s st=%s'%(r.fr,N[a-BASE],lr,[sg(x) if x<0x20000000 or x>0x30000000 else hex(x) for x in R],[sg(x&0xffffffff) for x in st]),flush=True)
for a in N: uc.hook_add(UC_HOOK_CODE,hk,begin=BASE+a,end=BASE+a)
to_play(r,f0)
ON[0]=True; r.step(2); ON[0]=False
shot(r,'/tmp/claude-0/pd_%dx%d.png'%(W,Hh))
