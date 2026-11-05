"""L3/T17c7: tiện ích harness dùng chung (boot -> vào game -> S3/tap/hits/shot/fork). NEW = dev.so (menu mới), ORIG = v0.2-hd gốc."""
import sys, os, json, struct, hashlib, glob
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, H)
from c2lib import *
from hl5libc import save_png
WK = os.path.expanduser(os.environ.get('WORK', '~/hl5work_l6'))
WK0 = os.path.expanduser('~/hl5work')            # bản gốc (chỉ đọc)
ORIGD = os.path.expanduser(os.environ.get('HL5_ORIG', WK0 + '/orig_v02hd'))   # v0.2-hd thật (không dựa build_apk)

# mặt nạ 2 vùng Skip/Bỏ qua (chép từ harness/l1/qlib.py của L1b; chỉ để so ảnh cycles)
SKIP_MASKS = {(400, 240): [(0, 0, 57, 21), (323, 8, 391, 32)], (600, 270): [(0, 0, 58, 20), (523, 8, 591, 32)], (1200, 540): [(5, 0, 47, 17), (1079, 24, 1175, 61)]}
def masked_md5(rgb, W, Hh):
    b = bytearray(rgb)
    for x0, y0, x1, y1 in SKIP_MASKS.get((W, Hh), []):
        for y in range(y0, y1 + 1): b[(y * W + x0) * 3:(y * W + x1 + 1) * 3] = bytes(3 * (x1 - x0 + 1))
    return hashlib.md5(bytes(b)).hexdigest()

class Ctx:
    def __init__(self, W, Hh, mode, outdir, boot_to=1000):
        self.W, self.H, self.mode = W, Hh, mode; self.NEW = mode == 'new'
        so = WK + '/l3/dev/dev.so' if self.NEW else ORIGD + '/libHeroesLore5.so'
        vfs = WK + '/l3/dev/data.vfs.mp3' if self.NEW else ORIGD + '/data.vfs.mp3'
        self.tag = '%dx%d' % (W, Hh); self.out = outdir; os.makedirs(outdir, exist_ok=True)
        self.r = Run(so, vfs, W, Hh, 'c2_%s_%s_%d' % (self.tag, mode, os.getpid()), WK + '/l3')
        route(self.r, boot_to)
        self.g = self.r.g; self.sym = {}
        if self.NEW:
            for l in open(WK + '/l3/dev/menu/menu.sym'):
                p = l.split()
                if len(p) == 3: self.sym[p[2]] = int(p[0], 16)
        self.gv = BASE + 0x16a238
        self.hero = self.g.rd32(self.gv + 5180)
    def callx(self, addr, *args):
        """gọi hàm guest (offset) với >4 tham số (đẩy lên stack)"""
        from unicorn.arm_const import UC_ARM_REG_R0, UC_ARM_REG_R12, UC_ARM_REG_SP, UC_ARM_REG_LR
        uc = self.g.uc
        saved = {r: uc.reg_read(r) for r in range(UC_ARM_REG_R0, UC_ARM_REG_R12 + 1)}
        sp0 = uc.reg_read(UC_ARM_REG_SP); lr0 = uc.reg_read(UC_ARM_REG_LR)
        sp = (sp0 - 0x400) & ~7
        stk = list(args[4:]); sp -= 4 * len(stk); sp &= ~7
        for i, v in enumerate(stk): uc.mem_write(sp + 4 * i, struct.pack('<I', v & 0xffffffff))
        for i, v in enumerate(args[:4]): uc.reg_write(UC_ARM_REG_R0 + i, v & 0xffffffff)
        uc.reg_write(UC_ARM_REG_SP, sp); uc.reg_write(UC_ARM_REG_LR, self.g.nested_stop)
        uc.emu_start(BASE + addr, self.g.nested_stop)
        res = uc.reg_read(UC_ARM_REG_R0)
        for k, v in saved.items(): uc.reg_write(k, v)
        uc.reg_write(UC_ARM_REG_SP, sp0); uc.reg_write(UC_ARM_REG_LR, lr0)
        return res
    def mimg(self): return masked_md5(self.r.shot(), self.W, self.H)
    def lcdg(self): return self.g.rd32(BASE + 0x16a210)
    # ---- đọc ----
    def b8(self, a, o=0): return bytes(self.g.uc.mem_read(a + o, 1))[0]
    def s8(self, a, o=0): v = self.b8(a, o); return v - 256 if v > 127 else v
    def s16(self, a): return struct.unpack('<h', bytes(self.g.uc.mem_read(a, 2)))[0]
    def u32(self, a): return self.g.rd32(a)
    def wr(self, a, bs): self.g.uc.mem_write(a, bytes(bs))
    def top(self): return self.r.top()
    def st(self): return self.b8(self.top(), 0xc)
    def sub(self): return self.b8(self.top(), 0xd)
    def cls(self): return self.r.cls()
    def bag(self): return self.r.call(0x85448, self.hero)
    def hits(self):
        n = self.r.rd(self.sym['mui_nhit']); out = {}
        for i in range(n):
            a = BASE + self.sym['mui_hits'] + 14 * i
            x0, y0, x1, y1, hid = struct.unpack('<hhhhh', bytes(self.g.uc.mem_read(a, 10))); out[hid] = (x0, y0, x1, y1, self.b8(a, 10))
        return out
    # ---- thao tác ----
    def S3(self, n, wait=10): self.r.call(0x428bc, 0, 3, n); self.r.step(wait)
    def key(self, k, w=3): self.r.key(k); self.r.step(w)
    def step(self, n=1): self.r.step(n)
    def tap(self, hid, w=3):
        h = self.hits()[hid]; self.r.touch((h[0] + h[2]) // 2, (h[1] + h[3]) // 2, True); self.r.step(w)
    def touch_xy(self, x, y, w=3): self.r.touch(x, y, True); self.r.step(w)
    def shot(self, name):
        save_png(os.path.join(self.out, '%s_%s_%s.png' % (name, self.tag, self.mode)), self.W, self.H, self.r.shot())
    def files(self):
        out = {}
        for f in sorted(glob.glob(os.path.join(self.g.a.storage, '**', '*'), recursive=True)):
            if os.path.isfile(f): out[os.path.relpath(f, self.g.a.storage)] = hashlib.md5(open(f, 'rb').read()).hexdigest()[:12]
        return out
    def fork(self, fn, name):
        pid = os.fork()
        if pid: os.waitpid(pid, 0); return
        try: res = fn()
        except Exception as e:
            import traceback; res = {'ERR': repr(e), 'tb': traceback.format_exc()}
        json.dump(res, open(os.path.join(self.out, 'sc_%s_%s_%s.json' % (name, self.tag, self.mode)), 'w'), default=str)
        os._exit(0)
