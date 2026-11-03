#!/usr/bin/env python3
"""T17a: chụp từng cửa sổ trong game. Chạy guest tới khung F0 rồi os.fork() cho mỗi kịch bản (không phải chạy lại lộ trình).
Dùng: t17_inv.py SO VFS OUT W H F0 "keys_lộ_trình" scen_file
scen_file: mỗi dòng  tên|số_khung|off:hành_động,off:hành_động,...   (off tính từ F0)
hành_động: KEY (UP DOWN LEFT RIGHT OK CLR LSK RSK HASH STAR 0..9) | S<id>.<arg> = BaseState::CallState(id,arg) (id: 1 Game,2 Popup,
3 InGameMenu,4 NpcMenu,5 MainMenu,6 Expert,7 Map,8 CashShop,9 NetMenu) | N<type>.<id> = BaseState::CallNpcMenuState(type,id) |
C<type> = BaseState::CallCashShopMenuState(type) | SHOT (chụp ảnh OUT/<tên>_<off>.png) | T<addr>.<a>.<b> = gọi phương thức (vaddr hex) trên state trên cùng | W<addr>.<val> ghi word guest | D<addr> in word ra log"""
import sys, os, argparse
HERE = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, HERE)
from hl5run import Guest, BASE
from hl5libc import boot, KEYS, save_png, screen_rgb, FRAME_MS
from unicorn.arm_const import *

so, vfs, out, W, H, F0, keys, scen = sys.argv[1:9]
W, H, F0 = int(W), int(H), int(F0)
os.makedirs(out, exist_ok=True)
a = argparse.Namespace(so=so, vfs=vfs, out=out, w=W, h=H, frames=F0, shot=0, keys=keys, touch='',
                       storage=os.path.join(out, 'storage'), reslog=False)
os.makedirs(a.storage, exist_ok=True)
g = Guest(a); boot(g)
loop = g.sym('Java_co_kr_eamobile_CletEntry_nativeLoop'); keyf = g.sym('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
ev = {}
for it in filter(None, keys.split(',')):
    f, k = it.split(':')[:2]; ev.setdefault(int(f), []).append(k)


def press(k):
    code = KEYS.get(k); code = int(k) if code is None else code
    g.call(keyf, g.env, 0, 2, code & 0xffffffff, 0); g.call(keyf, g.env, 0, 3, code & 0xffffffff, 0)


def step(fr):
    g.frame = fr; g.vclock += FRAME_MS; g.call(loop, g.env, 0)


for fr in range(F0):
    g.frame = fr
    for k in ev.get(fr, []): press(k)
    g.vclock += FRAME_MS; g.call(loop, g.env, 0)

kids = []
for ln in open(scen):
    ln = ln.strip()
    if not ln or ln.startswith('#'): continue
    name, nf, acts = ln.split('|')
    while len(kids) >= 4:
        os.waitpid(kids.pop(0), 0)
    pid = os.fork()
    if pid:
        kids.append(pid); continue
    try:
        sched = {}
        for it in filter(None, acts.split(',')):
            off, act = it.split(':', 1); sched.setdefault(int(off), []).append(act)
        g.log = open(os.path.join(out, name + '.log'), 'w')
        for off in range(int(nf)):
            for act in sched.get(off, []):
                if act in KEYS or act.isdigit():
                    press(act)
                elif act == 'SHOT':
                    rgb = screen_rgb(g)
                    if rgb: save_png(os.path.join(out, '%s_%d.png' % (name, off)), W, H, rgb)
                elif act[0] == 'S':
                    i, x = act[1:].split('.'); g.nested_call(BASE + 0x428bc, 0, int(i), int(x))
                elif act[0] == 'N':
                    i, x = act[1:].split('.'); g.nested_call(BASE + 0x42b08, 0, int(i), int(x))
                elif act[0] == 'C':
                    g.nested_call(BASE + 0x42ab8, 0, int(act[1:]))
                elif act[0] == 'T':     # T<addr hex>.<a>.<b>: gọi phương thức trên state trên cùng ([gv+0x244]) với (this,a,b)
                    ad, x, y = act[1:].split('.'); top = g.rd32(BASE + 0x16a238 + 0x244)
                    g.nested_call(BASE + int(ad, 16), top, int(x), int(y))
                elif act[0] == 'W':
                    ad, v = act[1:].split('.'); g.wr32(int(ad, 16), int(v))
                elif act[0] == 'D':
                    g.log.write('D %s = %#x\n' % (act[1:], g.rd32(int(act[1:], 16))))
                else:
                    press(act)
            step(F0 + off)
        g.log.flush()
    except Exception as e:
        open(os.path.join(out, name + '.err'), 'w').write(repr(e))
    os._exit(0)

for p in kids: os.waitpid(p, 0)
