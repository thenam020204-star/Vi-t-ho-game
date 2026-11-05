import sys, os, argparse
sys.path.insert(0,'/home/user/Vi-t-ho-game/hl5_android/harness')
from hl5run import Guest, BASE
from hl5libc import boot
from unicorn.arm_const import *
from unicorn import *
out='/root/hl5work/t15a/chk'; os.makedirs(out+'/st',exist_ok=True)
a=argparse.Namespace(so='/root/hl5work/build_apk/libHeroesLore5.so',vfs='/root/hl5work/build_apk/data.vfs.mp3',out=out,w=1200,h=540,frames=0,shot=0,keys='',touch='',storage=out+'/st',reslog=False)
g=Guest(a); bf={}
g.uc.hook_add(UC_HOOK_CODE,lambda u,ad,sz,ud: bf.__setitem__('p',u.reg_read(UC_ARM_REG_R0)),begin=BASE+0x4201c,end=BASE+0x4201c)
boot(g)
loop=g.sym('Java_co_kr_eamobile_CletEntry_nativeLoop')
for i in range(3): g.call(loop,g.env,0)
pf=g.rd32(BASE+0x16a214); print(hex(pf), 'b69', g.uc.mem_read(pf+69,1)[0], 'hd', g.rd32(BASE+0x1704c0+36))
G=g.rd32(BASE+0x16a210); print('G',hex(G),[hex(g.rd32(G+i)) for i in (0,4,8)],g.rd32(G+0x54),g.rd32(G+0x58))
