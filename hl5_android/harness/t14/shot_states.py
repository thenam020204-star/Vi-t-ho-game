"""T14c: chụp HUD ở nhiều trạng thái (thường, nhấn tấn công, dpad NE/S/W cố định, nhấn S0, nhấn DASH, nhấn tạm dừng) -> OUT_PREFIX_<tên>.rgb (RGB thô).
shot_states.py W H OUT_PREFIX   (HUD_DEV=dev_before|dev chọn bản). Dùng so_pixel.py để so."""
import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from t14lib import *
W, Hh, pre = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3]
r = mk(W, Hh, 'new', 'ss'); to_play(r, 1300); r.step(3)
f = max(8, Hh * 16 // 270)
def pos(dox, doy): return W - dox * 115 // 100 * f // 16, (Hh - doy * 115 // 100 * f // 16) if doy >= 0 else (-doy * f // 16)
def dump(n): open('%s_%s.rgb' % (pre, n), 'wb').write(bytes(r.shot()))
def hold(name, x, y, fr=3):
    r.touch(x, y, up=False); r.step(fr); dump(name); r.g.call(r.touchf, r.g.env, 0, 20, x, y, 0); r.step(3)
dump('idle')
ax, ay = pos(31, 40); hold('att', ax, ay)
s0x, s0y = pos(145, 27); hold('s0', s0x, s0y)
dx_, dy_ = pos(17, 86); hold('dash', dx_, dy_)
px_, py_ = W - 17 * 115 // 100 * f // 16, 45 * f // 16; hold('pause', px_, py_, 4)
r.step(30)
c = 78 * f // 16; cx, cy = c, Hh - c; D = 96 * f // 16
for n, (a, b) in dict(N=(0, -1), NE=(1, -1), E=(1, 0), SE=(1, 1), S=(0, 1), SW=(-1, 1), W=(-1, 0), NW=(-1, -1)).items():
    hold('dp' + n, cx + a * D * 35 // 100, cy + b * D * 35 // 100)
