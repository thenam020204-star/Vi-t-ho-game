"""T24b: ghi md5 ảnh từng khung theo ROUTE (khởi động -> tiêu đề -> chọn lớp -> hoạt cảnh -> chơi) + byte-khối/khung, cho 1 dev.so.
Dùng: lock.py DEVDIR W H N OUT.json   (so 2 file OUT bằng cmp.py)"""
import sys, os, json, hashlib
sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 'l1'))
from c2lib import Run, route, ROUTE
from unicorn import UC_HOOK_BLOCK
D, W, Hh, N, OUT = sys.argv[1], int(sys.argv[2]), int(sys.argv[3]), int(sys.argv[4]), sys.argv[5]
r = Run(D + '/dev.so', D + '/data.vfs.mp3', W, Hh, 'lk_%d' % os.getpid(), os.path.dirname(OUT))
cnt = [0]
def hb(uc, a, s, u): cnt[0] += s
r.g.uc.hook_add(UC_HOOK_BLOCK, hb); r.g.uc.ctl_flush_tb()
md, bk = [], []
st0 = r.step
def step(n=1):
    for _ in range(n):
        cnt[0] = 0; st0(1); md.append(hashlib.md5(bytes(r.shot())).hexdigest()[:12]); bk.append(cnt[0])
r.step = step
route(r, N)
json.dump({'md5': md, 'blk': bk}, open(OUT, 'w'))
