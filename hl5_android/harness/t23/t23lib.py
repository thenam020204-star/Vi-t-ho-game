"""T23: thư viện đo hiệu năng toàn game. Profiler theo hàm (UC_HOOK_BLOCK) + khung kịch bản.
Cách dùng: from t23lib import *  ; c = mk(W,H)  ; prof = Prof(c) ; prof.start() ; c.step(n) ; prof.stop() ; prof.report()
Đơn vị: 'byte-khối' = tổng kích thước khối cơ bản Unicorn đã thực thi (~4 B/lệnh ARM => /4 = lệnh)."""
import sys, os, re, bisect, struct, collections
os.environ.setdefault('WORK', os.path.expanduser('~/hl5work_t23')); os.environ.setdefault('DEVDIR', os.path.expanduser('~/hl5work_t23/dev'))
H = os.path.dirname(os.path.abspath(__file__)); sys.path.insert(0, os.path.join(H, '..', 'l1'))
from qlib import *
from unicorn import UC_HOOK_BLOCK, UC_HOOK_CODE
from unicorn.arm_const import *
import hl5run
SO_DIS = os.path.expanduser('~/hl5work/so.dis')
STUB = hl5run.STUB if hasattr(hl5run, 'STUB') else None

def load_syms(menu_sym):
    """trả (addrs[], names[]) sắp xếp; nguồn: so.dis (nhãn hàm) + menu.sym (kiểu t/T/a ở >= 0x171000)"""
    S = {}
    for l in open(SO_DIS, errors='replace'):
        m = re.match(r'^([0-9a-f]{8}) <(.+)>:$', l)
        if m: S[int(m.group(1), 16)] = m.group(2)
    if menu_sym:
        for l in open(menu_sym):
            p = l.split()
            if len(p) == 3 and p[1] in 'tTa' and int(p[0], 16) >= 0x171000 and not p[2].startswith('.'): S[int(p[0], 16)] = 'M:' + p[2]
    a = sorted(S); return a, [S[x] for x in a], S

def mk(W, Hh, boot_to=1000, mode='new'):
    return Ctx(W, Hh, mode, os.path.expanduser('~/hl5work_t23/out'), boot_to=boot_to)

class Prof:
    def __init__(self, c):
        self.c = c; self.g = c.g; self.uc = c.g.uc
        menu = (DEV + '/menu/menu.sym') if c.NEW else None
        self.addrs, self.names, self.S = load_syms(menu)
        self.fe = set(self.addrs)
        self.cache = {}          # addr -> func start
        self.reset(); self.h = None
    def reset(self):
        self.excl = collections.Counter(); self.incl = collections.Counter(); self.calls = collections.Counter()
        self.host = collections.Counter(); self.hostb = collections.Counter(); self.hostcaller = collections.Counter()
        self.callers = collections.Counter()   # (callee, caller) -> incl bytes
        self.stack = []; self.total = 0; self.nblk = 0
    def fn(self, a):
        r = self.cache.get(a)
        if r is None:
            o = a - hl5run.BASE if a >= hl5run.BASE else -1
            if o < 0: r = -1
            else:
                i = bisect.bisect_right(self.addrs, o) - 1
                r = self.addrs[i] if i >= 0 else -1
            self.cache[a] = r
        return r
    def name(self, f): return self.names[bisect.bisect_left(self.addrs, f)] if f in self.fe else ('0x%x' % f)
    def hook(self, uc, a, s, _):
        B = hl5run.BASE
        self.total += s; self.nblk += 1
        st = self.stack
        # trả: pop các frame có ret == a
        if st:
            sp = None
            while st and st[-1][1] == a:
                fr = st.pop(); f = fr[0]; self.incl[f] += fr[2]
                if st: st[-1][2] += fr[2]; self.callers[(f, st[-1][0])] += fr[2]
        h = self.g.handlers.get(a)
        if h is not None:       # stub host
            n = h[0]; self.host[n] += 1
            cur = st[-1][0] if st else -1; self.hostcaller[(n, cur)] += 1
            if n in ('memcpy', 'memmove', 'memset', 'memcpy16', '__aeabi_memcpy', '__aeabi_memset'): self.hostb[n] += uc.reg_read(UC_ARM_REG_R2)
            return
        f = self.fn(a)
        o = a - B
        if f == -1:
            return
        if 0x300b4 <= o < 0x31000:  # PLT: tính cho hàm đang chạy
            if st: self.excl[st[-1][0]] += s; st[-1][2] += s
            return
        self.excl[f] += s
        if o == f:   # vào hàm mới (bl) => đẩy frame
            lr = uc.reg_read(UC_ARM_REG_LR)
            self.calls[f] += 1
            st.append([f, lr & ~1, s])
        else:
            if st: st[-1][2] += s
            else: self.incl[f] += 0
    def start(self):
        self.reset(); self.h = self.uc.hook_add(UC_HOOK_BLOCK, self.hook); self.uc.ctl_flush_tb()
    def stop(self):
        if self.h is not None: self.uc.hook_del(self.h); self.h = None
        # flush frame còn dở (khung chưa trả)
        while self.stack:
            fr = self.stack.pop(); self.incl[fr[0]] += fr[2]
            if self.stack: self.stack[-1][2] += fr[2]
    def snapshot(self, n=1):
        return dict(total=self.total // n, excl={self.nm(f): v // n for f, v in self.excl.items()}, incl={self.nm(f): v // n for f, v in self.incl.items()},
                    calls={self.nm(f): v / n for f, v in self.calls.items()}, host={k: v / n for k, v in self.host.items()}, hostb={k: v // n for k, v in self.hostb.items()},
                    callers={'%s<-%s' % (self.nm(a), self.nm(b)): v // n for (a, b), v in self.callers.items()},
                    hostcaller={'%s<-%s' % (a, self.nm(b)): v / n for (a, b), v in self.hostcaller.items()})
    def nm(self, f):
        if f == -1: return '?'
        return self.name(f) if f in self.fe else '0x%x' % f
