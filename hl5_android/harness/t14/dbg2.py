import sys, os, struct, re, bisect; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
from unicorn import UC_HOOK_CODE
from unicorn.arm_const import *
fs=[]
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    m=re.match(r'^([0-9a-f]{8}) <(.*)>:$',l)
    if m: fs.append((int(m.group(1),16),m.group(2)))
ad=[a for a,_ in fs]
def fname(a):
    i=bisect.bisect_right(ad,a)-1; return '%x %s'%(fs[i][0],fs[i][1][:60])
r=mk(400,240,'new','dbg'); g=r.g; sa=r.sym['S']; uc=g.uc; ON=[0]
def hk(uc,a,s,u):
    if ON[0]:
        sp=uc.reg_read(UC_ARM_REG_SP); seen=[]
        for i in range(0,600):
            try: v=struct.unpack('<I',bytes(uc.mem_read(sp+4*i,4)))[0]-BASE
            except Exception: break
            if 0<v<0x16a000 and (v&1)==0:
                n=fname(v)
                if n not in seen: seen.append(n)
        print('\n'.join(seen[:14]),flush=True)

sym=[l for l in open(os.path.expanduser('~/hl5work/so.dis')) if 'StateInGameMenu::StateInGameMenu(int, signed char)>:' in l][0].split()[0]; print('ctor',sym)
uc.hook_add(UC_HOOK_CODE,hk,begin=BASE+int(sym,16),end=BASE+int(sym,16))
to_play(r,1300); r.step(2)
def rd(o,n): return bytes(g.uc.mem_read(BASE+sa+o,n))
cx=struct.unpack('<14i',rd(84,56))[9]; cy=struct.unpack('<14i',rd(140,56))[9]
ep=r.rd(0x16a238+5204); print('ep',hex(ep),'e75',r.rdb(ep+0x75)[0] if ep else None,'gs',r.rdb(r.top()+0xc)[0],flush=True)
ON[0]=0; r.touch(cx,cy,up=False); r.step(1); print('edit',rd(17,1)[0],'hold',rd(18,1)[0],'cls',hex(r.cls()),flush=True)
