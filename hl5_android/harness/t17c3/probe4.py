import sys; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t17c3')
from qlib import *
c = Ctx(600, 270, 'orig', '/tmp/claude-0/q3')
h = c.hero
c.S3(6, 8)
tm = c.g.rd32(BASE + int(os.popen("llvm-nm -C --defined-only ~/hl5work/apk/assets/libHeroesLore5.so | grep ' _pTextMgr$' | cut -d' ' -f1").read(), 16))
for i in range(0, 400):
    p = c.r.call(0x13fa94, tm, i)
    if p in (537297106, 537297047+0, 537298047, 537298053): print('CT', i, hex(p))
print([ (i, bytes(c.g.uc.mem_read(c.r.call(0x13fa94, tm, i), 8)).hex()) for i in (140,141,142,143,153,144,145)])
# skill tables: vtable+0x24 mapping and levels, HeroSkillInfo name
for cls in range(4):
    c.wr(h+0x22c, [cls])
    row = []
    for sk in range(20):
        idx = c.r.call(c.g.rd32(c.g.rd32(h)+0x24)-BASE, h, sk) & 255
        lvl = c.s8(h, 584+idx+4) if idx < 200 else None
        row.append((sk, idx, lvl))
    print(cls, row)
print('lvl bytes', c.rdb if False else '')
