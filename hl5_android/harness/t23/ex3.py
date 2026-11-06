"""T23: điều khiển đối tượng trong hoạt cảnh: mỗi tick ghi hero/camera/NPC sự kiện/hạt. ex3.py W H [f_end]"""
import sys, json, struct; sys.path.insert(0, '/home/user/Vi-t-ho-game/hl5_android/harness/t23')
from t23lib import *
W, Hh = int(sys.argv[1]), int(sys.argv[2]); END = int(sys.argv[3]) if len(sys.argv) > 3 else 1500
c = mk(W, Hh, 0); r = c.r; g = c.g; T = Timeline(c); T.addroute(0, 960)
for f in range(900, 3000, 40): T.ev.setdefault(f, []).append('OK')
GV = 0x16a238
def s32(a): return struct.unpack('<i', bytes(g.uc.mem_read(a, 4)))[0]
def s16(a): return struct.unpack('<h', bytes(g.uc.mem_read(a, 2)))[0]
rows = []
while r.fr < END:
    T.frame()
    ep = r.rd(GV + 5204); mp = r.rd(GV + 5200); hero = r.rd(GV + 5180); npcs = r.rd(GV + 5528)
    if not ep or not mp or not hero: continue
    row = dict(fr=r.fr, e75=r.rdb(ep + 0x75)[0], hero=(s32(hero + 4), s32(hero + 8)), cam=(s32(mp + 0xd0), s32(mp + 0xd4)), tgt=(s32(mp + 0xc0), s32(mp + 0xc4)),
               cspd=s16(mp + 184), cspd2=s16(mp + 186), f686=r.rdb(ep + 0x686)[0], f687=r.rdb(ep + 0x687)[0], f688=r.rdb(ep + 0x688)[0], c68c=(s32(ep + 0x68c), s32(ep + 0x690)))
    ents = []
    for i in range(24):
        e = ep + 536 + 20 * i; ty = r.rdb(e + 4)[0]
        if ty:
            ni = r.rdb(e + 17)[0]; np_ = g.rd32(npcs + 4 * ni) if ty == 7 and ni < 64 else 0
            ents.append((i, ty, ni, s16(e), s16(e + 2), (s32(np_ + 4), s32(np_ + 8)) if np_ and np_ > 0x10000000 else None))
    row['ents'] = ents
    rows.append(row)
json.dump(rows, open(os.path.expanduser('~/hl5work_t23/out/ex3_%dx%d.json' % (W, Hh)), 'w'))
print(len(rows))
