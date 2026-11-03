#!/usr/bin/env python3
"""T15a: thử nghiệm giả thuyết 'vẽ menu vào FB nhỏ (W/s x H/s) rồi phóng xs' (phương án a) CHỈ trong harness.
Móc StateInGameMenu::Paint (0x106c1c) bằng hook Python: đổi Graphic->FB sang FB nhỏ bằng chính
Graphic::ChangeFrameBuff (0x5c020), thay FBOff bằng bản chụp thu nhỏ, chạy Paint gốc, rồi (ở lr giả) khôi phục
và phóng NEAREST xs vào FB chính (mô phỏng thay cho đoạn mã guest sẽ viết ở T15b).
Dùng: t15a_zoom.py SO VFS OUT W H FRAMES "keys" "shotframes" [zoom=1]  (keys như hl5run: frame:KEY,...)"""
import sys, os, struct, argparse
import numpy as np
HERE = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, HERE)
from hl5run import Guest, BASE
from hl5libc import boot, KEYS, save_png, screen_rgb, FRAME_MS
from unicorn import *
from unicorn.arm_const import *
so, vfs, out, W, H, NF, keys, shots = sys.argv[1:9]
W, H, NF = int(W), int(H), int(NF)
ZOOM = int(sys.argv[9]) if len(sys.argv) > 9 else 1
TOUCH = {}            # frame -> [(x, y)]  (toạ độ khung CHÍNH, như bridge gửi cho guest)
for it in filter(None, (sys.argv[10] if len(sys.argv) > 10 else '').split(',')):
    f, x, y = it.split(':'); TOUCH.setdefault(int(f), []).append((int(x), int(y)))
shots = set(int(x) for x in shots.split(',') if x)
os.makedirs(out, exist_ok=True)
a = argparse.Namespace(so=so, vfs=vfs, out=out, w=W, h=H, frames=NF, shot=0, keys=keys, touch='',
                       storage=os.path.join(out, 'storage'), reslog=False)
os.makedirs(a.storage, exist_ok=True)
g = Guest(a)
uc = g.uc
PF = lambda: g.rd32(BASE + 0x16a214)     # _pCFont: con trỏ BFont toàn cục
boot(g)
VN = BASE + 0x1704c0
GV = BASE + 0x16a210            # con trỏ Graphic*
S = max(1, H // 270)
w, h = W // S, H // S
st = {'on': False}


def mkfb(w, h):
    p = g.heap.alloc(w * h * 2); s = g.heap.alloc(32)
    uc.mem_write(p, bytes(w * h * 2))
    for i, v in enumerate((w, h, w * 2, 16, p)): g.wr32(s + 4 * i, v)
    return s, p


small = snap = None


def paint_enter(uc_, addr, size, ud):
    global small, snap
    if not ZOOM or S == 1 or st['on']: return
    G = g.rd32(GV)
    if small is None: small = mkfb(w, h); snap = mkfb(w, h)
    mainfb, mainoff = g.rd32(G), g.rd32(G + 8)
    st['hd'] = g.rd32(VN + 36); st['b69'] = uc.mem_read(PF() + 69, 1)[0]
    g.wr32(VN + 36, 0); uc.mem_write(PF() + 69, bytes([11]))        # font 15/10 px như khung 600x270
    st['last'] = g.frame
    g.log.write('zoom paint f%d b69=%d hd=%d\n' % (g.frame, st['b69'], st['hd']))
    st.update(on=True, G=G, mainfb=mainfb, mainoff=mainoff, lr=uc.reg_read(UC_ARM_REG_LR))
    src = np.frombuffer(bytes(uc.mem_read(g.rd32(mainoff + 16), W * H * 2)), dtype='<u2').reshape(H, W)
    uc.mem_write(snap[1], np.ascontiguousarray(src[::S, ::S][:h, :w]).astype('<u2').tobytes())
    g.wr32(G + 8, snap[0])
    g.nested_call(BASE + 0x5c020, G, small[0])          # Graphic::ChangeFrameBuff(small)
    uc.reg_write(UC_ARM_REG_LR, ret_stub)


def paint_exit():
    G = st['G']
    g.nested_call(BASE + 0x5c020, G, st['mainfb'])
    g.wr32(G + 8, st['mainoff'])
    g.wr32(VN + 36, st['hd']); uc.mem_write(PF() + 69, bytes([st['b69']]))
    sm = np.frombuffer(bytes(uc.mem_read(small[1], w * h * 2)), dtype='<u2').reshape(h, w)
    big = np.repeat(np.repeat(sm, S, 0), S, 1)
    full = np.zeros((H, W), '<u2'); oy, ox = (H - h * S) // 2, (W - w * S) // 2
    full[oy:oy + h * S, ox:ox + w * S] = big
    uc.mem_write(g.rd32(st['mainfb'] + 16), full.tobytes())
    uc.reg_write(UC_ARM_REG_PC, st['lr']); st['on'] = False


ret_stub = g.stub('t15a_ret', paint_exit)
def ev_enter(uc_, addr, size, ud):      # onHandleEvent(type, x, y, id): sự kiện chạm = 19, 20, 21
    t = uc.reg_read(UC_ARM_REG_R0)
    if t in (19, 20, 21) and ZOOM and S > 1 and st.get('last', -9) >= g.frame - 1:
        x, y = uc.reg_read(UC_ARM_REG_R1), uc.reg_read(UC_ARM_REG_R2)
        uc.reg_write(UC_ARM_REG_R1, ((x - (W - w * S) // 2) // S) & 0xffffffff); uc.reg_write(UC_ARM_REG_R2, ((y - (H - h * S) // 2) // S) & 0xffffffff)
        G = g.rd32(GV); st['ev'] = (G, [g.rd32(G + o) for o in (0x54, 0x58, 0x4c, 0x50)], uc.reg_read(UC_ARM_REG_LR))
        for o, v in zip((0x54, 0x58, 0x4c, 0x50), (w, h, w // 2, h // 2)): g.wr32(G + o, v)   # chạm: Draw/hit-test đọc Graphic::GetWidth/GetHalfWidth lúc xử lý sự kiện
        uc.reg_write(UC_ARM_REG_LR, ev_ret)
        g.log.write('touch %d (%d,%d) -> (%d,%d)\n' % (t, x, y, (x - (W - w * S) // 2) // S, (y - (H - h * S) // 2) // S))
def ev_exit():
    G, old, lr = st['ev']
    for o, v in zip((0x54, 0x58, 0x4c, 0x50), old): g.wr32(G + o, v)
    uc.reg_write(UC_ARM_REG_PC, lr)
ev_ret = g.stub('t15a_evret', ev_exit)
uc.hook_add(UC_HOOK_CODE, ev_enter, begin=BASE + 0x9831c, end=BASE + 0x9831c)
def logcall(n):
    def h(u, ad, sz, ud):
        G = g.rd32(GV)
        g.log.write('call %s f%d r0=%#x r1=%d r2=%d r3=%d | G w=%d h=%d hw=%d hh=%d sel=%d lr=%s\n' % (n, g.frame, u.reg_read(UC_ARM_REG_R0), u.reg_read(UC_ARM_REG_R1), u.reg_read(UC_ARM_REG_R2), u.reg_read(UC_ARM_REG_R3), g.rd32(G + 0x54), g.rd32(G + 0x58), g.rd32(G + 0x4c), g.rd32(G + 0x50), u.mem_read(u.reg_read(UC_ARM_REG_R0) + 14, 1)[0], g.sym_of(u.reg_read(UC_ARM_REG_LR))))
    return h
for ad, n in ((0x42d80, 'BaseState::TouchPress'), (0x100648, 'InGameMenu::TouchPressed'), (0xe406c, 'TouchMainMenu')):
    uc.hook_add(UC_HOOK_CODE, logcall(n), begin=BASE + ad, end=BASE + ad)
# mainKeyPressed() 0x98af0 (hàm gọi State::KeyPressed/TouchPressed mỗi khung) chạy với Graphic cỡ nhỏ khi state đang bị phóng (hit-test nằm ở đó; BaseState::TouchPress chỉ ghi toạ độ)
pst = {}
def proc_enter(u, ad, sz, ud):
    if not ZOOM or S == 1 or pst.get('on') or st.get('last', -9) < g.frame - 1: return
    G = g.rd32(GV); pst.update(on=True, G=G, old=[g.rd32(G + o) for o in (0x54, 0x58, 0x4c, 0x50)], lr=u.reg_read(UC_ARM_REG_LR))
    for o, v in zip((0x54, 0x58, 0x4c, 0x50), (w, h, w // 2, h // 2)): g.wr32(G + o, v)
    u.reg_write(UC_ARM_REG_LR, proc_ret)
def proc_exit():
    for o, v in zip((0x54, 0x58, 0x4c, 0x50), pst['old']): g.wr32(pst['G'] + o, v)
    uc.reg_write(UC_ARM_REG_PC, pst['lr']); pst['on'] = False
proc_ret = g.stub('t15a_procret', proc_exit)
uc.hook_add(UC_HOOK_CODE, proc_enter, begin=BASE + 0x98af0, end=BASE + 0x98af0)
touchf = g.sym('Java_co_kr_eamobile_CletEntry_nativeTouchHandleEvent')
uc.hook_add(UC_HOOK_CODE, paint_enter, begin=BASE + 0x106c1c, end=BASE + 0x106c1c)
loop = g.sym('Java_co_kr_eamobile_CletEntry_nativeLoop'); keyf = g.sym('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
ev = {}
for it in filter(None, keys.split(',')):
    f, k = it.split(':')[:2]; ev.setdefault(int(f), []).append(k)
for fr in range(NF):
    g.frame = fr
    for k in ev.get(fr, []):
        code = KEYS.get(k); code = int(k) if code is None else code
        g.call(keyf, g.env, 0, 2, code & 0xffffffff, 0); g.call(keyf, g.env, 0, 3, code & 0xffffffff, 0)
    for x, y in TOUCH.get(fr, []):
        g.call(touchf, g.env, 0, 19, x, y, 0)       # 19 = nhấn (BaseState::TouchPress), 20 = nhả, 21 = kéo
    for x, y in TOUCH.get(fr - 3, []):
        g.call(touchf, g.env, 0, 20, x, y, 0)
    g.vclock += FRAME_MS
    g.call(loop, g.env, 0)
    if fr in shots:
        rgb = screen_rgb(g)
        if rgb: save_png(os.path.join(out, 's%05d.png' % fr), W, H, rgb)
g.log.flush()
