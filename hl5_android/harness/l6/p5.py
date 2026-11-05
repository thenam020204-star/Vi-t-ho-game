import sys, os, signal
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
W,Hh=int(sys.argv[1]),int(sys.argv[2])
OUT='/home/user/Vi-t-ho-game/hl5_android/shots/L6'
c = Ctx(W,Hh, 'new', OUT, boot_to=10); r = c.r
r.step(700); r.key('OK'); r.step(10)
p = Probe(c)
def info(tag):
    t=c.top(); print(tag, 'cls', hex(c.cls()), 'st', c.st(), 'sub', c.sub(), 'cur', c.s8(t,0xe), c.s8(t,0x10), p.decide('MAIN',t), flush=True)
# (tên, cell, sau-khi-vào: ép state/sub)
PL=[('thanhtuu',3,None),('trogiup',4,None),('chuyengia_sub1',1,(6,1)),('caidat_sub1',2,(9,1))]
for nm,cell,force in PL:
    def f():
        signal.alarm(150)
        c.tap(0x10+cell, 8); info(nm)
        if force:
            t=c.top(); c.wr(t+0xc,[force[0],force[1]]); c.step(3); info(nm+' forced')
        c.shot('mm_'+nm)
    pid=os.fork()
    if pid: os.waitpid(pid,0)
    else:
        try: f()
        except Exception as e: print('ERR',nm,e,flush=True)
        finally: os._exit(0)
