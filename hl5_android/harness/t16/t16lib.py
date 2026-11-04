"""T16: thư viện harness cho khung hội thoại. Chạy hoạt cảnh mở đầu (route c1lib tới hết chọn lớp, KHÔNG bấm '#'), lái hội thoại bằng phím OK,
ghi từng thông điệp (id tên, chuỗi, trang), chụp ảnh, thử nút Bỏ qua / chạm, đếm cấp phát.
mode: 'orig' = v0.2-hd gốc ($HOME/hl5work/build_apk), 'new' = dev.so của harness/t16/build_dev.sh, 'off' = dev.so với mui_flags |= MUI_F_DISABLE (đường gốc trong bản dev)."""
import sys, os, struct, json
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
sys.path.insert(0, os.path.join(H, '..', 't17c1')); sys.path.insert(0, os.path.join(H, '..'))
from c1lib import Run, ROUTE, BASE
from hl5libc import save_png
WK = os.path.expanduser(os.environ.get('WORK', '~/hl5work_t16')); WK0 = os.path.expanduser('~/hl5work')
ORIGD = os.path.expanduser(os.environ.get('HL5_ORIG', WK0 + '/build_apk'))   # gốc v0.2-hd (xem qlib.py của t17c2)
GV = 0x16a238

class Dlg:
    def __init__(self, W, Hh, mode, tag='t16'):
        self.W, self.H, self.mode = W, Hh, mode
        newso = mode in ('new', 'off')
        so = WK + '/t16/dev/dev.so' if newso else ORIGD + '/libHeroesLore5.so'
        vfs = WK + '/t16/dev/data.vfs.mp3' if newso else ORIGD + '/data.vfs.mp3'
        self.r = Run(so, vfs, W, Hh, '%s_%dx%d_%s_%d' % (tag, W, Hh, mode, os.getpid()), WK + '/t16')
        self.sym = {}
        if newso:
            for l in open(WK + '/t16/dev/menu/menu.sym'):
                p = l.split()
                if len(p) == 3: self.sym[p[2]] = int(p[0], 16)
            if mode == 'off': self.r.g.wr32(BASE + self.sym['mui_flags'], 1)
        ev = {}
        for it in ROUTE.split(','):
            f, k = it.split(':'); f = int(f)
            if f < 960: ev.setdefault(f, []).append(k)
        ev.setdefault(970, []).append('OK')
        self.ev = ev; self.msgs = []; self.cur_msg = None; self.log = []

    def ep(self): return self.r.rd(GV + 5204)
    def D(self, ep, i): return self.r.rdb(ep + 176 + 60 * i, 60)
    def e75(self):
        ep = self.ep(); return self.r.rdb(ep + 0x75)[0] if ep else 0
    def state(self):
        ep = self.ep()
        if not ep: return None
        e = self.r.rdb(ep, 0x6f0); cur = e[172]; d = e[176 + 60 * cur:176 + 60 * cur + 60]
        txt = struct.unpack('<I', d[32:36])[0]
        s = b''
        if txt:
            try: s = bytes(self.r.g.uc.mem_read(txt, 400)).split(b'\0')[0]
            except Exception: pass
        return dict(e75=e[0x75], cur=cur, st=d[43], name=d[7], lines=d[8], page=d[9], speed=struct.unpack('<h', d[10:12])[0],
                    prog=struct.unpack('<h', d[12:14])[0], tx=struct.unpack('<hhh', d[14:20]), box=struct.unpack('<hhhh', d[20:28]),
                    text=s, face=struct.unpack('<I', d[36:40])[0], side=d[40], s6e0=e[0x6e0], s6e1=e[0x6e1], a4=e[164])

    def step(self, n=1):
        for _ in range(n):
            for k in self.ev.get(self.r.fr, []): self.r.key(k)
            self.r.step(1)

    def shot(self, path): save_png(path, self.W, self.H, self.r.shot())

    def heap(self): return (len(self.r.g.heap.sizes), self.r.g.heap.top)

    def run_dialogs(self, f_end, shots=None, shot_dir=None, max_msgs=60):
        """lái đến khi hết hội thoại; mỗi trang: đợi chữ chạy, sau 8 khung bấm OK (hiện hết), khi tiến độ đứng yên 3 khung = trang đủ -> chụp -> OK (lật).
        Trả danh sách thông điệp: dict(name, text(hex), pages{trang: khung đầu}, first, box, tx, lines, face, side)."""
        r = self.r; shots = shots or {}; seen = {}; order = []; started = False
        self.step(900 - r.fr if r.fr < 900 else 0)
        h0 = None; cur_key = None; cnt = 0; flushed = False; last_prog = -1; stable = 0; idle = 0
        while r.fr < f_end:
            self.step(1)
            s = self.state()
            if not s: continue
            if s['e75'] and h0 is None: h0 = self.heap()
            if s['st'] == 2 and s['text']:
                started = True; idle = 0
                key = (s['name'], s['text'])
                if not order or order[-1] != key:
                    order.append(key); seen[len(order) - 1] = dict(name=s['name'], text=s['text'].hex(), pages={}, first=r.fr, box=s['box'], tx=s['tx'], lines=s['lines'], face=bool(s['face']), side=s['side'])
                mi = len(order) - 1; m = seen[mi]; m['pages'].setdefault(s['page'], r.fr)
                pk = (mi, s['page'])
                if pk != cur_key: cur_key = pk; cnt = 0; flushed = False; last_prog = -1; stable = 0
                cnt += 1
                if cnt == 3: r.key('OK')                                  # hiện hết trang (Event_Key: chưa đủ -> tốc độ 1000)
                elif cnt == 6:                                            # trang đã hiện đủ: chụp
                    if shot_dir and pk in shots and shots[pk] is False:
                        self.shot(os.path.join(shot_dir, 'msg%02d_p%d_%dx%d_%s.png' % (mi, s['page'], self.W, self.H, self.mode))); shots[pk] = True
                    m.setdefault('done', {})[s['page']] = dict(frame=r.fr, prog=s['prog'], speed=s['speed'])
                elif cnt == 7 or (cnt > 40 and cnt % 20 == 0): r.key('OK')   # lật trang / sang thông điệp kế
            else:
                if started and not s['e75']: break
                if started:
                    idle += 1
                    if idle % 60 == 0: r.key('OK')
                    if idle > 500: break
        self.h0 = h0; self.h1 = self.heap()
        return [seen[i] for i in range(len(order))]
