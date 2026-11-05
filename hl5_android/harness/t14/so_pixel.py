"""T14c: so pixel HUD trước/sau. so_pixel.py PRE_A PRE_B  -> mỗi trạng thái: số điểm lệch, độ lệch tối đa RGB565 mỗi kênh (RGB thô 8 bit -> quy 565)."""
import sys, glob, os, json
a, b = sys.argv[1], sys.argv[2]; res = {}
for fa in sorted(glob.glob(a + '_*.rgb')):
    n = fa[len(a) + 1:-4]; fb = b + '_' + n + '.rgb'
    A, B = open(fa, 'rb').read(), open(fb, 'rb').read()
    nd = 0; mx = 0
    for i in range(0, len(A), 3):
        if A[i:i + 3] != B[i:i + 3]:
            nd += 1
            for k, sh in ((0, 3), (1, 2), (2, 3)): mx = max(mx, abs((A[i + k] >> sh) - (B[i + k] >> sh)))
    res[n] = (nd, mx, len(A) == len(B))
print(json.dumps(res))
