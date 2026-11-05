import sys, os, signal
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
W,Hh=int(sys.argv[1]),int(sys.argv[2]); what=sys.argv[3:]
OUT='/home/user/Vi-t-ho-game/hl5_android/shots/L6'
c = Ctx(W,Hh, 'new', OUT); r = c.r; p=Probe(c); g=c.g
BUF=0x7e000000
try: g.uc.mem_map(BUF,0x20000)
except Exception: pass
g.uc.mem_write(BUF, b'Test text;line\0'.ljust(0x100,b'\0'))
def rep(tag):
    q=c.top(); print(tag,'cls',hex(c.cls()),'type',c.s8(q,0xe),'nbtn',c.s8(q,0x10),'->',p.decide('POPUP',q),flush=True)
def run(nm,fn):
    pid=os.fork()
    if pid: os.waitpid(pid,0); return
    try:
        signal.alarm(200); fn(); c.step(3); rep(nm); c.shot(nm)
    except Exception as e: print('ERR',nm,e,flush=True)
    finally: os._exit(0)
qm=c.u32(c.gv+5580); ep=c.u32(c.gv+5204)
def quest():
    for i in (0,1,2): c.r.call(0xcdcc4,qm,i,1)
    for i in (0,1,2): c.r.call(0xd0a68,qm,i)
if 'quest' in what: run('pop_quest',quest)
if 'msgbox' in what: run('pop_msgbox',lambda: c.callx(0x6d45c, ep, BUF, 0, 0))
if 'situate' in what:
    for a in (0,1,2): run('pop_situate%d'%a,lambda a=a: c.callx(0x68d9c, ep, a))
