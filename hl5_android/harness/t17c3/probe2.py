import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
W, Hh = 600, 270
c = Ctx(W, Hh, 'orig', '/tmp/claude-0/q3')
h = c.hero
c.S3(6, 8); c.shot('lk_sub0')
c.key('RIGHT'); c.shot('lk_sub0b')
c.key('OK'); print('sub', c.sub(), c.b8(c.top(),0xe), c.b8(c.top(),0xf)); c.shot('lk_sub1')
for i in range(6): c.key('RIGHT')
print('f', c.b8(c.top(),0xf)); c.shot('lk_sub1r')
c.key(48); print('after 0: sub', c.sub(), 'e', c.b8(c.top(),0xe), 'f', c.b8(c.top(),0xf))
# belt state
print('belt', [c.r.call(0x84ec8, h, i) & 255 for i in range(5)] )
