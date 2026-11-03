import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
W,Hh=int(sys.argv[1]),int(sys.argv[2])
c = Ctx(W, Hh, 'new', '/tmp/claude-0/q3')
c.S3(6, 8); c.shot('lkn_sub0'); print(sorted(c.hits().items())[:40])
c.key('OK'); c.key('RIGHT'); c.key('RIGHT'); c.shot('lkn_sub1')
