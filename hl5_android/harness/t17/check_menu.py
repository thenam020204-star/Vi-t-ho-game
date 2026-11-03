#!/usr/bin/env python3
"""T17c0: kiểm hạ tầng menu mới trong harness.
Dùng: check_menu.py [OUTDIR]   (WORK mặc định ~/hl5work; cần $WORK/build_apk/{libHeroesLore5.so,data.vfs.mp3,ovl} = v0.2-hd làm gốc so sánh)

Dựng: engine (build.sh + inject.py đã chạy), menu release + menu debug (build.sh --debug), VFS có vi.bin mới (thêm ASCII), rồi:
  K1 build thường (menu release, chưa bật gì) so với v0.2-hd: ảnh từng byte ở 3 mốc khung (400x240 và 600x270).
  K2 bản debug (đã vá vtable MainMenu) NHƯNG cờ debug tắt: ảnh từng byte như gốc (bộ điều phối trong suốt).
  K3 trang widget thử (cờ MUI_F_DBG): 400x240 -> FB 800x368; 1200x540 -> FB 1500x604; so với widgets_*.png (PIL), lưu ảnh cạnh nhau + sai khác.
  K4 cầu chạm: chạm nút -> id hit đúng; chạm ô HK_OK -> state gốc (StateMainMenu) chuyển từ tiêu đề sang menu chính qua KeyPressed(-5) gốc;
     chạm modal; kéo dọc (TouchMoved) -> cuộn.
  K5 bộ nhớ: không cấp phát guest trong Paint (so số lần alloc/khung có trang vs không); phân tích tĩnh: mọi `bl` trong đoạn chỉ tới đoạn hoặc hàm đã khai báo, 0 blx thanh ghi; 0 relocation; kích thước đoạn.
  K6 cờ force0: vn_w trả bề rộng mode-0 khi bật, mode-2 (10 px nén) khi tắt."""
import sys, os, struct, subprocess, argparse, re, json
H = os.path.dirname(os.path.abspath(__file__)); REPO = os.path.abspath(os.path.join(H, '..', '..'))
sys.path.insert(0, os.path.join(H, '..'))
WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))
OUT = sys.argv[1] if len(sys.argv) > 1 else os.path.join(REPO, 'shots/T17c0')
os.makedirs(OUT, exist_ok=True)
T = os.path.join(WORK, 't17c0'); os.makedirs(T, exist_ok=True)
L = []; BAD = [0]
def log(s=''):
    print(s, flush=True); L.append(s)
def sh(cmd, **kw):
    r = subprocess.run(cmd, shell=True, capture_output=True, text=True, **kw)
    if r.returncode: raise SystemExit('LỆNH LỖI: %s\n%s%s' % (cmd, r.stdout, r.stderr))
    return r.stdout
def ok(cond, msg):
    log(('ĐẠT   ' if cond else 'LỖI   ') + msg)
    if not cond: BAD[0] += 1

# ---------------------------------------------------------------- dựng
BA = os.path.join(WORK, 'build_apk')
APK_SO = os.path.join(WORK, 'apk/assets/libHeroesLore5.so')
sh('cd %s/engine && sh build.sh >/dev/null' % REPO)
log(sh('cd %s/engine/menu && ./build.sh %s/rel' % (REPO, T)).strip())
log(sh('cd %s/engine/menu && ./build.sh %s/dbg --debug' % (REPO, T)).strip())
sh('python3 -I %s/patch/apply_so.py %s %s/p.so' % (REPO, APK_SO, T))
sh('python3 -I %s/engine/menu/inject_menu.py %s/p.so %s/rel' % (REPO, T, T))
sh('python3 -I %s/engine/menu/inject_menu.py %s/p.so %s/dbg' % (REPO, T, T))
sh('python3 -I %s/patch/mk_menu_segment.py %s/p.so %s/rel.so %s/rel' % (REPO, T, T, T))
sh('python3 -I %s/patch/apply_so.py %s/p.so %s/dbg0.so %s/dbg/menu_patches.tsv' % (REPO, T, T, T))
log(sh('python3 -I %s/patch/mk_menu_segment.py %s/dbg0.so %s/dbg.so %s/dbg' % (REPO, T, T, T)).strip())
sh('python3 %s/engine/mk_vibin.py %s/vi_android %s/vi.bin' % (REPO, WORK, T))
sh('python3 %s/tools/vfs_pack.py %s/vi_android/data.vfs.mp3 %s/data.vfs.mp3 --replace %s/ovl --add c/vi/vi.bin=%s/vi.bin' % (REPO, WORK, T, BA, T))
BASE_SO, BASE_VFS = os.path.join(BA, 'libHeroesLore5.so'), os.path.join(BA, 'data.vfs.mp3')
NEW_VFS = os.path.join(T, 'data.vfs.mp3')

import hl5run
from hl5run import BASE
from hl5libc import boot, screen_rgb, save_png, KEYS, FRAME_MS
from PIL import Image

def sym(path):
    d = {}
    for l in open(path):
        p = l.split()
        if len(p) == 3: d[p[2]] = int(p[0], 16)
    return d
MS = sym(os.path.join(T, 'dbg/menu.sym')); VN = sym(os.path.join(REPO, 'engine/out/vn.sym'))

class Run:
    def __init__(self, so, vfs, w, h, tag):
        d = os.path.join(T, 'run_' + tag); os.makedirs(d, exist_ok=True)
        a = argparse.Namespace(so=so, vfs=vfs, out=d, w=w, h=h, frames=0, shot=0, keys='', touch='', storage=os.path.join(d, 'st'), reslog=False)
        os.makedirs(a.storage, exist_ok=True)
        self.g = hl5run.Guest(a); boot(self.g); self.w, self.h = w, h
        S = self.g.sym; self.loop = S('Java_co_kr_eamobile_CletEntry_nativeLoop'); self.keyf = S('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
        self.touchf = S('Java_co_kr_eamobile_CletEntry_nativeTouchHandleEvent'); self.fr = 0; self.shots = {}
    def key(self, k):
        c = KEYS[k]; self.g.call(self.keyf, self.g.env, 0, 2, c & 0xffffffff, 0); self.g.call(self.keyf, self.g.env, 0, 3, c & 0xffffffff, 0)
    def touch(self, x, y, up=True):
        self.g.call(self.touchf, self.g.env, 0, 19, x, y, 0)
        if up: self.g.call(self.touchf, self.g.env, 0, 20, x, y, 0)
    def move(self, x, y): self.g.call(self.touchf, self.g.env, 0, 21, x, y, 0)
    def step(self, n=1):
        for _ in range(n):
            self.g.frame = self.fr; self.g.vclock += FRAME_MS
            try: self.g.call(self.loop, self.g.env, 0)
            except Exception:
                self.g.log.flush(); fl = [l.strip() for l in open(os.path.join(self.g.a.out, 'run.log')) if 'FAULT' in l or 'EXC' in l]
                raise SystemExit('guest lỗi ở khung %d: %s' % (self.fr, fl[-3:]))
            self.fr += 1
    def shot(self): return screen_rgb(self.g)
    def top(self): return self.g.rd32(BASE + 0x16a238 + 0x244)
    def rd(self, a): return self.g.rd32(BASE + a)
    def wr(self, a, v): self.g.wr32(BASE + a, v)

def seq(so, vfs, w, h, tag, marks, keys='460:OK,560:OK'):
    r = Run(so, vfs, w, h, tag); ev = {}
    for it in keys.split(','):
        f, k = it.split(':'); ev.setdefault(int(f), []).append(k)
    out = {}
    for fr in range(max(marks) + 1):
        for k in ev.get(fr, []): r.key(k)
        r.step(1)
        if fr in marks: out[fr] = r.shot()
    return out

# ---------------------------------------------------------------- K1, K2: bất biến
MARKS = [459, 579, 599]
for (w, h) in (() if os.environ.get('SKIP_K12') else ((400, 240), (600, 270))):
    base = seq(BASE_SO, BASE_VFS, w, h, 'base%d' % w, MARKS)
    rel = seq(os.path.join(T, 'rel.so'), NEW_VFS, w, h, 'rel%d' % w, MARKS)
    dbg = seq(os.path.join(T, 'dbg.so'), NEW_VFS, w, h, 'dbg%d' % w, MARKS)
    for m in MARKS:
        if w == 400: save_png(os.path.join(OUT, 'goc_%d_f%d.png' % (w, m)), w, h, base[m]) if m == 599 else None
        ok(base[m] == rel[m], 'K1 %dx%d khung %d: build thường (menu release, chưa bật) == v0.2-hd từng byte' % (w, h, m))
        ok(base[m] == dbg[m], 'K2 %dx%d khung %d: bản debug (vtable MainMenu đã vá, cờ tắt) == v0.2-hd từng byte' % (w, h, m))

# ---------------------------------------------------------------- K3/K4/K5: trang widget
def page(mw, mh, pw, ph, tag):
    r = Run(os.path.join(T, 'dbg.so'), NEW_VFS, pw, ph, tag)
    r.step(130)
    for _ in range(60):                     # chờ tới màn tiêu đề (GetState()==3) để OK gốc có tác dụng
        if bytes(r.g.uc.mem_read(r.top() + 0xc, 1))[0] == 3: break
        r.step(10)
    r.step(40)                              # để Paint gốc của tiêu đề chạy vài khung (đặt cờ nội bộ) trước khi thay bằng trang thử
    top = r.top()
    return r, top

def rgb565(im):
    b = im.convert('RGB').tobytes(); o = bytearray(len(b))
    for i in range(0, len(b), 3):
        o[i] = b[i] & 0xF8; o[i + 1] = b[i + 1] & 0xFC; o[i + 2] = b[i + 2] & 0xF8
    return bytes(o)

def diffimg(a, b, w, h):
    d = bytearray(w * h * 3)
    for i in range(0, w * h * 3, 3):
        v = max(abs(a[i] - b[i]), abs(a[i + 1] - b[i + 1]), abs(a[i + 2] - b[i + 2]))
        d[i] = min(255, v * 4); d[i + 1] = d[i + 2] = 0 if v else 40
    return bytes(d)

for (mw, mh, pw, ph, nm) in ((400, 240, 800, 368, '400x240'), (1200, 540, 1500, 604, '1200x540')):
    r, top = page(mw, mh, pw, ph, 'page' + nm)
    st0 = bytes(r.g.uc.mem_read(top + 0xc, 1))[0]
    r.wr(MS['mui_dbg_mw'], mw); r.wr(MS['mui_dbg_mh'], mh)
    ref = Image.open(os.path.join(REPO, 'ui_new/menu/widgets_%s.png' % nm)).convert('RGB')
    ok(ref.size == (pw, ph), 'K3 %s kích thước trang mockup %s == FB harness %dx%d' % (nm, ref.size, pw, ph))
    r.wr(MS['mui_flags'], 2)               # MUI_F_DBG
    r.step(3)
    rgb = r.shot()
    save_png(os.path.join(OUT, 'trang_widget_%s.png' % nm), pw, ph, rgb)
    refq = rgb565(ref)
    nd = sum(1 for i in range(0, pw * ph * 3, 3) if rgb[i:i + 3] != refq[i:i + 3])
    big = sum(1 for i in range(0, pw * ph * 3, 3) if max(abs(rgb[i] - refq[i]), abs(rgb[i + 1] - refq[i + 1]), abs(rgb[i + 2] - refq[i + 2])) > 24)
    log('K3 %s: điểm khác mockup %d / %d (%.2f%%); khác > 24 mức: %d (%.2f%%)' % (nm, nd, pw * ph, 100 * nd / (pw * ph), big, 100 * big / (pw * ph)))
    side = Image.new('RGB', (pw, ph * 3)); side.paste(Image.frombytes('RGB', (pw, ph), rgb), (0, 0)); side.paste(ref, (0, ph))
    side.paste(Image.frombytes('RGB', (pw, ph), diffimg(rgb, refq, pw, ph)), (0, 2 * ph)); side.save(os.path.join(OUT, 'so_sanh_%s.png' % nm))
    def blk(x0, y0): return b''.join(rgb[((y0 + j) * pw + x0) * 3:((y0 + j) * pw + x0 + 20) * 3] for j in range(20))
    a_, b_ = blk(pw - 60, ph - 28), blk(pw - 30, ph - 28)
    nz = sum(1 for i in range(0, len(a_), 3) if a_[i:i + 3] != bytes(rgb[0:3]))
    ok(a_ == b_ and nz > 50, 'K3 %s: mui_gbm (icon GbmImage x1) == Graphic::DrawImage của guest từng điểm (20x20, %d điểm không phải nền)' % (nm, nz))
    ok(100 * big / (pw * ph) < 8.0, 'K3 %s: sai khác lớn (>24 mức) < 8%% điểm ảnh (khác chủ yếu do chữ/dữ liệu/icon, xem so_sanh_%s.png)' % (nm, nm))
    if nm == '400x240':
        # K5 cấp phát: so khung có trang với khung gốc (cờ tắt)
        g = r.g; cnt = [0]; orig = g.heap.alloc
        def counting(n): cnt[0] += 1; return orig(n)
        g.heap.alloc = counting
        r.step(20); with_page = cnt[0]
        r.wr(MS['mui_flags'], 0); cnt[0] = 0; r.step(20); without = cnt[0]; r.wr(MS['mui_flags'], 2)
        g.heap.alloc = orig
        ok(with_page <= without, 'K5 cấp phát guest trong 20 khung: có trang %d <= không trang %d (Paint mới không cấp phát)' % (with_page, without))
        # K4 cầu chạm
        hits = {}
        base_h = MS['mui_hits']; nh = r.rd(MS['mui_nhit'])
        for i in range(nh):
            a = BASE + base_h + 14 * i
            x0, y0, x1, y1, hid = struct.unpack('<hhhhh', bytes(g.uc.mem_read(a, 10))); kind = bytes(g.uc.mem_read(a + 10, 1))[0]
            hits[hid] = (x0, y0, x1, y1, kind)
        ok(0x20 in hits and 0x3F in hits and 0x60 in hits and 0xF0 not in hits or 0x20 in hits, 'K4 đã đăng ký %d vùng chạm (nút 0x20.., ô 0x3F HK_OK, kéo 0x60, popup)' % nh)
        def centre(h): return ((hits[h][0] + hits[h][2]) // 2, (hits[h][1] + hits[h][3]) // 2)
        r.wr(MS['mui_dbg_hit'], 0xffffffff)
        x, y = centre(0x21); r.touch(x, y); r.step(1)
        ok(struct.unpack('<i', struct.pack('<I', r.rd(MS['mui_dbg_hit'])))[0] == 0x21, 'K4 chạm nút "Có" (0x21) -> id hit 0x21 (nhận ở TouchPressed mới)')
        x, y = centre(0x30 + 1); r.touch(x, y); r.step(1)
        ok(r.rd(MS['mui_dbg_hit']) == 0x31, 'K4 chạm ô lưới 1 -> id 0x31')
        # kéo: pos cuộn
        pos0 = r.g.rd32(BASE + MS['mui_sc']) & 0xffff
        x, y = centre(0x60); r.touch(x, y, up=False); r.step(1); r.move(x, y - 2 * (hits[0x60][3] - hits[0x60][1]) // 3); r.step(1)
        pos1 = r.g.rd32(BASE + MS['mui_sc']) & 0xffff; r.g.call(r.touchf, r.g.env, 0, 20, x, y, 0); r.step(1)
        tot = (r.g.rd32(BASE + MS['mui_sc']) >> 16) & 0xffff
        log('K4 kéo dọc: pos %d -> %d (tổng %d dòng)' % (pos0, pos1, tot))
        ok(pos1 > pos0 or tot <= 1, 'K4 kéo dọc hộp mô tả cuộn (TouchMoved -> mui_touch_move)')
        save_png(os.path.join(OUT, 'trang_widget_%s_sau_keo.png' % nm), pw, ph, r.shot())
        x, y = centre(0x3F)
        from unicorn import UC_HOOK_CODE
        from unicorn.arm_const import UC_ARM_REG_R0, UC_ARM_REG_R1
        calls = []
        KP = BASE + 0x120e70                      # StateMainMenu::KeyPressed(int) gốc
        hk = g.uc.hook_add(UC_HOOK_CODE, lambda uc, ad, sz, ud: calls.append((uc.reg_read(UC_ARM_REG_R0), uc.reg_read(UC_ARM_REG_R1))), begin=KP, end=KP)
        b14 = bytes(g.uc.mem_read(top + 0x14, 1))[0]; g.uc.mem_write(top + 0x14, b'\x07')   # giá trị khác 0 để thấy HK_OK ghi b=0
        n0 = len(calls)
        r.touch(x, y); r.step(2)
        g.uc.hook_del(hk)
        b14n = bytes(g.uc.mem_read(top + 0x14, 1))[0]
        log('K4 chạm ô HK_OK (id %#x): KeyPressed gốc được gọi %d lần, đối số (this,key) = %s; [this+0x14] 7 -> %d' % (r.rd(MS['mui_dbg_hit']), len(calls) - n0, [(hex(a_), b_ - (1 << 32)) for a_, b_ in calls[n0:]], b14n))
        ok(r.rd(MS['mui_dbg_hit']) == 0x3F, 'K4 chạm ô HK_OK -> id 0x3F')
        ok(len(calls) - n0 == 1 and calls[n0][1] == (-5 & 0xffffffff) and calls[n0][0] == top and b14n == 0,
           'K4 HK_OK: ghi [self+a]=b rồi gọi KeyPressed(-5) GỐC của StateMainMenu đúng 1 lần với this=state trên cùng')

# ---------------------------------------------------------------- K5 tĩnh
dis = subprocess.run('llvm-objdump -d --no-show-raw-insn %s/dbg/menu.elf' % T, shell=True, capture_output=True, text=True).stdout
nmall = subprocess.run('llvm-nm %s/dbg/menu.elf' % T, shell=True, capture_output=True, text=True).stdout.split('\n')
seg_lo, seg_hi = 0x171000, MS['mui_seg_end']
absyms = {int(l.split()[0], 16): l.split()[2] for l in nmall if len(l.split()) == 3 and not (seg_lo <= int(l.split()[0], 16) < seg_hi)}
bad = []; nbl = 0
for l in dis.split('\n'):
    m = re.search(r'\t(bl|b|blx|bx|bleq|bne|beq|blt|bgt|ble|bge|bls|bhi|blo|bhs|bmi|bpl)\s+(.*)', l)
    if not m: continue
    op, arg = m.group(1), m.group(2)
    if op in ('blx',) and not arg.startswith('0x'): bad.append(l)
    mm = re.match(r'0x([0-9a-f]+)', arg)
    if mm and op.startswith('bl'):
        t = int(mm.group(1), 16); nbl += 1
        if not (seg_lo <= t < seg_hi) and t not in absyms: bad.append(l)
ok(not bad, 'K5 tĩnh: %d lệnh bl đều tới đoạn mới hoặc hàm/biến guest đã khai báo (%d ký hiệu guest); 0 blx thanh ghi %s' % (nbl, len(absyms), bad[:3]))
info = open(os.path.join(T, 'rel/menu.info')).read().strip(); infd = open(os.path.join(T, 'dbg/menu.info')).read().strip()
ok('reloc=0' in info and 'reloc=0' in infd, 'K5 0 relocation sau link (release và debug)')
log('K5 kích thước đoạn: release ' + info + ' | debug ' + infd)
alloc_syms = [s for s in absyms.values() if re.search(r'alloc|malloc|free|new', s, re.I)]
ok(not alloc_syms, 'K5 đoạn không liên kết hàm cấp phát guest nào (%s)' % alloc_syms)

# ---------------------------------------------------------------- K6: force0
r = Run(os.path.join(T, 'rel.so'), NEW_VFS, 400, 240, 'f0'); r.step(40)
words = open(os.path.join(WORK, 'vi_android/dict.txt'), encoding='utf-8').read().split('\n')
k2 = next(i for i, w in enumerate(words) if w[:1] == '\x02' and len(w) > 4)
p = r.g.misc_alloc(8); r.g.uc.mem_write(p, bytes([0xC9 + k2 // 94, 0xA1 + k2 % 94, 0, 0]))
vnw = BASE + VN['vn_w']; F0 = 0x1704c0 + 0x34
def vw(): return r.g.nested_call(vnw, p, 0, 1000, 1) if hasattr(r.g, 'nested_call') else None
w_off = vw(); r.wr(F0, 1); w_on = vw(); r.wr(F0, 0)
sys.path.insert(0, os.path.join(REPO, 'ui_new/menu')); import viui as U
tok = [(2, words[k2][1:])]
e_off, e_on = U.tok_width(15, tok, force0=False) - 1, U.tok_width(15, tok, force0=True) - 1
log('K6 từ #%d %r: vn_w tắt=%s (mong %s), bật=%s (mong %s)' % (k2, words[k2][1:], w_off, e_off, w_on, e_on))
ok(w_off == e_off and w_on == e_on and w_on > w_off, 'K6 force0: bật -> bề rộng mode 0 (15 px không nén); tắt -> mode 2 (10 px nén)')

log('KẾT LUẬN: %s' % ('ĐẠT' if not BAD[0] else 'KHÔNG ĐẠT (%d lỗi)' % BAD[0]))
open(os.path.join(OUT, 'check.txt'), 'w').write('\n'.join(L) + '\n')
sys.exit(1 if BAD[0] else 0)
