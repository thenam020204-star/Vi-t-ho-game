#!/usr/bin/env python3
"""So kết quả hoi_quy.py: orig/off/new ở 3 cỡ. Dùng: hoi_quy_cmp.py HQDIR OUTDIR -> OUTDIR/ketqua.txt + so_anh_<cỡ>.png (trên gốc, giữa mới, dưới |khác| x4)"""
import sys, os, json
from PIL import Image, ImageChops
D, O = sys.argv[1], sys.argv[2]; os.makedirs(O, exist_ok=True); L = []
def log(s=''): print(s); L.append(s)
for sz in ('400x240', '600x270', '1200x540'):
    W, H = map(int, sz.split('x')); R = {m: json.load(open(os.path.join(D, 'hq_%s_%s.json' % (sz, m)))) for m in ('orig', 'off', 'new')}
    log('== ' + sz)
    for k in R['orig']:
        o, f, n = (R[m][k]['md5'] for m in ('orig', 'off', 'new'))
        log('  %-6s orig=%s off=%s new=%s   off==orig: %s   new==orig: %s' % (k, o[:8], f[:8], n[:8], f == o, n == o))
    for ph in ('before', 'after'):
        a = Image.open(os.path.join(D, 'hq_%s_orig_%s_orig.png' % (ph, sz))).convert('RGB'); b = Image.open(os.path.join(D, 'hq_%s_new_%s_new.png' % (ph, sz))).convert('RGB')
        d = ImageChops.difference(a, b).convert('L').point(lambda v: 255 if v else 0)
        l = d.crop((0, 0, W // 2, H)).getbbox(); r = d.crop((W // 2, 0, W, H)).getbbox()
        r = (r[0] + W // 2, r[1], r[2] + W // 2, r[3]) if r else None
        # che 2 vùng (nhãn '# Skip' gốc ở góc trên trái; nút 'Bỏ qua' mới ở góc trên phải) rồi so lại
        m = Image.new('L', (W, H), 255); 
        from PIL import ImageDraw
        dr = ImageDraw.Draw(m)
        if l: dr.rectangle(l, 0)
        if r: dr.rectangle(r, 0)
        left = ImageChops.multiply(d, m).getbbox()
        log('  %s: vùng lệch trái(nhãn Skip gốc)=%s phải(nút Bỏ qua mới)=%s; lệch NGOÀI hai vùng: %s' % (ph, l, r, left))
        if ph == 'after':
            S = Image.new('RGB', (W, 3 * H + 8), (255, 0, 255)); S.paste(a, (0, 0)); S.paste(b, (0, H + 4))
            S.paste(ImageChops.difference(a, b).point(lambda v: min(255, v * 4)), (0, 2 * H + 8)); S.save(os.path.join(O, 'so_anh_%s.png' % sz))
open(os.path.join(O, 'ketqua.txt'), 'w').write('\n'.join(L) + '\n')
