#!/usr/bin/env python3
"""T17p: kiểm bản thử PT_LOAD trong harness.
Dùng: check_ptload.py TEST.so BASE.so VFS OUTDIR W H [FRAMES]
Chạy TEST.so và BASE.so (v0.2-hd) cùng phím; mỗi khung đọc texture màn hình (8x8 góc trên-trái) + bộ đếm ở 0x172000 của đoạn mới.
Kiểm: bộ đếm tăng đúng 1/khung từ lúc có flush; viền trắng, lõi đổi màu theo (đếm>>4)&3; ảnh TEST khác BASE chỉ trong ô 8x8;
vùng zero-fill 0x172000+ đọc 0 trước khi chạy. Ghi OUTDIR/{test,base}/fNNNNN.png và OUTDIR/check.txt. Thoát != 0 nếu hỏng."""
import sys, os, struct, argparse
H = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, os.path.join(H, '..'))
import hl5run
from hl5libc import boot, screen_rgb, save_png, KEYS, FRAME_MS
from unicorn.arm_const import *

TAB = [0xF800, 0x07E0, 0x001F, 0xFFE0]
KEYS_SCRIPT = '460:OK,560:OK'


def run(so, vfs, out, w, h, frames, probe):
    os.makedirs(out, exist_ok=True)
    a = argparse.Namespace(so=so, vfs=vfs, out=out, w=w, h=h, frames=frames, shot=0, keys=KEYS_SCRIPT, touch='',
                           storage=os.path.join(out, 'storage'), reslog=False)
    os.makedirs(a.storage, exist_ok=True)
    g = hl5run.Guest(a)
    pre = bytes(g.uc.mem_read(hl5run.BASE + 0x172000, 0x1000)) if probe else None
    boot(g)
    S = g.sym
    loop = S('Java_co_kr_eamobile_CletEntry_nativeLoop'); keyf = S('Java_co_kr_eamobile_CletEntry_nativeKeyHandleEvent')
    ev = {}
    for it in KEYS_SCRIPT.split(','):
        fr, k = it.split(':'); ev.setdefault(int(fr), []).append(k)
    rows, shots = [], {}
    for fr in range(frames):
        g.frame = fr
        for k in ev.get(fr, []):
            c = KEYS[k]
            g.call(keyf, g.env, 0, 2, c & 0xffffffff, 0); g.call(keyf, g.env, 0, 3, c & 0xffffffff, 0)
        g.vclock += FRAME_MS
        g.call(loop, g.env, 0)
        tid = getattr(g, 'screen_tex', None)
        px = None
        if tid in g.tex:
            TW, TH, fmt, typ, data = g.tex[tid]
            px = [struct.unpack_from('<H', data, (y * TW + x) * 2)[0] for y in range(8) for x in range(8)]
        cnt = g.rd32(hl5run.BASE + 0x172000) if probe else 0
        flag = g.rd32(hl5run.BASE + 0x172004) if probe else 0
        rows.append((fr, cnt, flag, px))
        if fr in (99, 299, 459, 699):
            rgb = screen_rgb(g)
            if rgb: shots[fr] = rgb; save_png(os.path.join(out, 'f%05d.png' % fr), w, h, rgb)
    return g, rows, pre, shots


def main():
    so, base, vfs, out, w, h = sys.argv[1:7]; w = int(w); h = int(h)
    frames = int(sys.argv[7]) if len(sys.argv) > 7 else 700
    L = []; bad = 0
    def log(s):
        print(s); L.append(s)
    g, rows, pre, st = run(so, vfs, out + '/test', w, h, frames, True)
    gb, rowsb, _, sb = run(base, vfs, out + '/base', w, h, frames, False)
    log('== %dx%d, %d khung' % (w, h, frames))
    log('zero-fill 0x172000..0x173000 trước khi chạy: %s' % ('toàn 0' if pre == bytes(0x1000) else 'KHÁC 0'))
    if pre != bytes(0x1000): bad += 1
    first = next((r for r in rows if r[1] > 0), None)
    log('khung đầu có chạm hàm: %s (cờ canary=%d)' % (first[0] if first else None, first[2] if first else -1))
    if not first or first[2] != 1: bad += 1
    nbad = 0; prev = None; seen = set()
    for fr, cnt, flag, px in rows:
        if cnt == 0: continue
        if prev is not None and cnt != prev + 1: nbad += 1
        prev = cnt
        if px is None: nbad += 1; continue
        inner = TAB[(cnt >> 4) & 3]; seen.add(inner)
        exp = [0xFFFF if (x in (0, 1, 6, 7) or y in (0, 1, 6, 7)) else inner for y in range(8) for x in range(8)]
        if px != exp: nbad += 1
    log('bộ đếm cuối %d (khung %d); khung lệch (đếm không +1 / điểm ảnh sai): %d; màu lõi đã thấy: %s'
        % (rows[-1][1], frames, nbad, sorted('%04X' % c for c in seen)))
    if nbad or len(seen) < 4: bad += 1
    # ảnh test vs base: chỉ khác trong ô 8x8
    for fr in sorted(st):
        if fr not in sb: log('khung %d: thiếu ảnh' % fr); bad += 1; continue
        a, b = st[fr], sb[fr]
        diff = [(i // 3 % w, i // 3 // w) for i in range(0, len(a), 3) if a[i:i + 3] != b[i:i + 3]]
        out_of = [d for d in diff if d[0] > 7 or d[1] > 7]
        log('khung %d: điểm khác base %d (ngoài ô 8x8: %d)' % (fr, len(diff), len(out_of)))
        if out_of: bad += 1
    log('KẾT LUẬN: %s' % ('ĐẠT' if not bad else 'KHÔNG ĐẠT (%d)' % bad))
    open(out + '/check.txt', 'w').write('\n'.join(L) + '\n')
    sys.exit(1 if bad else 0)


main()
