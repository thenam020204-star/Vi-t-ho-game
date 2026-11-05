import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
c = Ctx(400,240, 'new', '/tmp/w/o'); r = c.r
p = Probe(c); g=c.g
BUF=0x7e000000
try: g.uc.mem_map(BUF,0x20000)
except Exception as e: print('map',e)
g.uc.mem_write(BUF, b'Test text;line\0'.ljust(0x100,b'\0'))
SPS={'INGAME':(3,0xe31b8,25),'NPC':(4,0x1093d8,16),'EXPERT':(6,0xe05d4,4),'CASH':(8,0xd7e50,4),'MAIN':(5,0x117d78,30),'GAME':(1,0xda530,2)}
sel=sys.argv[1:] or list(SPS)
for cl in sel:
    cid,addr,nmax=SPS[cl]
    for n in range(1,nmax+1):
        def f():
            c.r.call(0x428bc,0,cid,0); c.step(4)
            t=c.top()
            try: c.callx(addr, t, n, BUF, 0, 0, 0, 0)
            except Exception as e: print(cl,n,'CALL ERR',e,flush=True); return
            c.step(2)
            q=c.top()
            if q==t: print(cl,n,'no popup',flush=True); return
            typ=c.s8(q,0xe); nb=c.s8(q,0x10); par=c.u32(q+8); pv=c.u32(par) if par else 0
            print(cl,n,'type',typ,'nbtn',nb,'parentvt',hex(pv),'+814',hex(c.u32(q+0x814)),'->',p.decide('POPUP',q),flush=True)
        pid=os.fork()
        if pid: os.waitpid(pid,0)
        else:
            try: f()
            except Exception as e: print(cl,n,'ERR',e,flush=True)
            finally: os._exit(0)
