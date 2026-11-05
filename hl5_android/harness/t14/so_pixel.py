"""T14c: so pixel HUD trước/sau. so_pixel.py PRE_A PRE_B W H [dpad]
 Không có 'dpad': so cả khung từng trạng thái. Có 'dpad': chỉ so vùng dpad (cx±D/2+2) ở các ảnh dp*: kiểm vẽ dpad bằng bộ đệm == vẽ trực tiếp (kỳ vọng 0 điểm lệch).
 In: trạng thái -> (số điểm lệch, độ lệch tối đa 1 kênh theo đơn vị RGB565, cùng cỡ)."""
import sys, glob, json
a, b, W, Hh = sys.argv[1], sys.argv[2], int(sys.argv[3]), int(sys.argv[4]); dp = len(sys.argv) > 5; res = {}
f = max(8, Hh * 16 // 270); c = 78 * f // 16; cx, cy = c, Hh - c; R = 96 * f // 16 // 2 + 2
for fa in sorted(glob.glob(a + '_*.rgb')):
    n = fa[len(a) + 1:-4]
    if dp and not n.startswith('dp'): continue
    A, B = open(fa, 'rb').read(), open(b + '_' + n + '.rgb', 'rb').read()
    nd = 0; mx = 0
    rows = range(max(0, cy - R), min(Hh, cy + R + 1)) if dp else range(Hh)
    x0, x1 = (max(0, cx - R), min(W, cx + R + 1)) if dp else (0, W)
    for y in rows:
        o = (y * W + x0) * 3; e = (y * W + x1) * 3
        if A[o:e] == B[o:e]: continue
        for i in range(o, e, 3):
            if A[i:i + 3] != B[i:i + 3]:
                nd += 1
                for k, sh in ((0, 3), (1, 2), (2, 3)): mx = max(mx, abs((A[i + k] >> sh) - (B[i + k] >> sh)))
    res[n] = (nd, mx)
print(json.dumps(res))
