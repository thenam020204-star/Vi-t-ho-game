import sys, os, signal
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_l6'))
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from plib import *
W,Hh=int(sys.argv[1]),int(sys.argv[2])
OUT='/home/user/Vi-t-ho-game/hl5_android/shots/L6'
c = Ctx(W,Hh, 'new', OUT); r = c.r; p=Probe(c)
CASES=[('ig_skill_sub7',3,'INGAME',4,7),('ig_skill_sub10',3,'INGAME',4,10),('ig_spirit_sub4',3,'INGAME',7,4),('ig_quest_sub4',3,'INGAME',8,4),('ig_quest_sub5',3,'INGAME',8,5),('game_st3',1,'GAME',3,0),('game_st2_sub0',1,'GAME',2,0),('map_img',7,'MAP',0,1)]
for nm,cid,cl,s,sub in CASES:
    def f():
        signal.alarm(120)
        par = 1 if nm=='map_img' else 0
        c.r.call(0x428bc,0,cid,par); c.step(4)
        t=c.top()
        if nm!='map_img': c.wr(t+0xc,[s,sub])
        c.step(2); print(nm,'cls',hex(c.cls()),'st',c.st(),'sub',c.sub(),p.decide(cl,t),flush=True); c.shot(nm)
    pid=os.fork()
    if pid: os.waitpid(pid,0)
    else:
        try: f()
        except Exception as e: print('ERR',nm,e,flush=True)
        finally: os._exit(0)
