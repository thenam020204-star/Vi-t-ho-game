"""probe: danh sách điểm 'x,y' -> chạm, 1 khung, nhả, 1 khung; in hàm hành động. fork mỗi điểm."""
import sys, os, re, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
from unicorn import UC_HOOK_CODE
W,Hh=int(sys.argv[1]),int(sys.argv[2]); pts=[tuple(map(int,p.split(','))) for p in sys.argv[3].split()]; f0=int(sys.argv[4]) if len(sys.argv)>4 else 1300
names={}
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    m=re.match(r'^([0-9a-f]{8}) <((HERO|StateGame|Battle|CommonUi|GMenu|StateInGameMenu|StateMap)::[^(]*)\(',l)
    if m:
        n=m.group(2); b=n.split('::')[1]
        if b.startswith(('Get','Is','Draw','Check','Process','Paint','~')) or n.split('::')[0] in('StateInGameMenu','StateMap','CommonUi','GMenu','Battle') and not b.startswith(('Set','Key','Start','Open')): continue
        if n.startswith('StateGame::') and b.startswith(('StateGame','Touch','Key')) and b!='KeyPressed': continue
        names[int(m.group(1),16)]=n
r=mk(W,Hh,'orig','pt'); uc=r.g.uc; log=[]
uc.hook_add(UC_HOOK_CODE,lambda uc,a,s,u: log.append(names[a-BASE]),begin=BASE,end=BASE+0x200000) if False else None
for a in names: uc.hook_add(UC_HOOK_CODE,lambda uc,a,s,u: log.append(names[a-BASE]),begin=BASE+a,end=BASE+a)
to_play(r,f0)
for (x,y) in pts:
    pid=os.fork()
    if pid==0:
        log.clear(); r.touch(x,y,up=False); r.step(1); a=list(log); log.clear()
        r.g.call(r.touchf,r.g.env,0,20,x,y,0); r.step(1); b=list(log)
        print('(%d,%d) press:%s | release:%s | cls=%x'%(x,y,list(dict.fromkeys(a)),list(dict.fromkeys(b)),r.cls()),flush=True); os._exit(0)
    os.waitpid(pid,0)
