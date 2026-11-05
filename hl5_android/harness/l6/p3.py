import sys, os
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
c = Ctx(400,240, 'new', '/tmp/w/o'); r = c.r
p = Probe(c)
print('top0', hex(c.cls()))
OFF={'GAME':0xc,'INGAME':0xc,'MAIN':0xc,'EXPERT':0xc,'MAP':0xc,'CASH':0xc,'NPC':0xe}
def grid(name, cid, par, cls, smax, submax):
    def f():
        c.r.call(0x428bc, 0, cid, par); c.step(6)
        t=c.top(); print(name,'created cls',hex(c.cls()),'st',c.st(),'sub',c.sub(),'+0xe..',[c.s8(t,o) for o in range(0xc,0x14)],flush=True)
        for s in range(smax+1):
            row=[]
            for sub in range(submax+1):
                if cls=='NPC': c.wr(t+0xe,[s&255,sub])
                else: c.wr(t+0xc,[s&255,sub])
                row.append(p.decide(cls,t).replace('new:','N:'))
            print(' ',s,row,flush=True)
    pid=os.fork()
    if pid: os.waitpid(pid,0)
    else:
        try: f()
        except Exception as e: print('ERR',name,e)
        finally: os._exit(0)
grid('INGAME',3,0,'INGAME',9,10)
grid('NPC',4,0,'NPC',8,9)
grid('EXPERT',6,0,'EXPERT',3,3)
grid('CASH',8,0,'CASH',3,3)
grid('MAP0',7,0,'MAP',2,2)
grid('MAP1',7,1,'MAP',2,2)
grid('GAME',1,0,'GAME',5,4)
