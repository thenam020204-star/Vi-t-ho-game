"""T14a: quét chạm — chạm (x,y) rồi nhả; ghi tập hàm HERO::/StateGame:: không phải Get/Is được gọi (chữ ký hành động). Bản gốc, gốc fork mỗi hàng."""
import sys, os, re, json; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
from unicorn import UC_HOOK_CODE
W,Hh,step=int(sys.argv[1]),int(sys.argv[2]),int(sys.argv[3]); f0=int(sys.argv[4]) if len(sys.argv)>4 else 1300
names={}
for l in open(os.path.expanduser('~/hl5work/so.dis')):
    m=re.match(r'^([0-9a-f]{8}) <((HERO|StateGame|Battle|CommonUi)::[^(]*)\(',l)
    if m:
        n=m.group(2); b=n.split('::')[1]
        if b.startswith(('Get','Is','Draw','Set','Check')) and n not in('HERO::SetAttackMotion','HERO::SetDashFlag','HERO::SetDashCheckFrame') and not n.startswith(('StateGame::SetState','StateGame::SetPopup')): continue
        if n.startswith('StateGame::') and b.startswith(('Paint','Process','~','StateGame','Touch','Key')) and b!='KeyPressed': continue
        names[int(m.group(1),16)]=n
r=mk(W,Hh,'orig','sc'); uc=r.g.uc
log=[]
def hk(uc,a,s,u): log.append(names[a-BASE])
for a in names: uc.hook_add(UC_HOOK_CODE,hk,begin=BASE+a,end=BASE+a)
to_play(r,f0)
res={}
for y in range(0,Hh,step):
    rd,wr=os.pipe(); pid=os.fork()
    if pid==0:
        os.close(rd); row=[]
        for x in range(0,W,step):
            log.clear(); r.touch(x,y,up=False); a=list(log); log.clear(); r.touch(x,y,up=True) if False else r.g.call(r.touchf,r.g.env,0,20,x,y,0); b=list(log)
            row.append(['|'.join(dict.fromkeys(a)),'|'.join(dict.fromkeys(b))])
        os.write(wr,json.dumps(row).encode()); os._exit(0)
    os.close(wr); data=b''
    while True:
        c=os.read(rd,1<<20)
        if not c: break
        data+=c
    os.waitpid(pid,0); res[y]=json.loads(data)
json.dump(res,open('/tmp/claude-0/scan_%dx%d.json'%(W,Hh),'w'))
