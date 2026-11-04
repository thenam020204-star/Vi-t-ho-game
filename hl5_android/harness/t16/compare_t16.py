#!/usr/bin/env python3
"""T16b: so sánh kết quả check_t16.py giữa gốc (orig) và mới (new) ở từng cỡ.
Dùng: compare_t16.py DIR [WxH ...]   -> in KẾT LUẬN, ghi DIR/check.txt
Kiểm:
  C1 chuỗi thông điệp (id tên + chuỗi byte) mới == gốc, cùng thứ tự;
  C2 mọi thông điệp có >= 1 trang, trang liên tục 0..n-1, số trang mới >= gốc (hộp hẹp hơn: được phép nhiều trang hơn) và <= 3x;
  C3 hình học mới: vùng chữ nằm trong hộp, hộp nằm trong màn, rộng hộp >= min(400, W-16) và ~72% W;
  C4 không tràn: ở ảnh mỗi trang (new) không có điểm chữ trắng ngoài vùng chữ trong hộp (trừ chân dung/mũi tên/bảng tên) — đếm điểm chữ trắng nằm ngoài [tx, tx+tw] trong dải chữ;
  C5 cấp phát: heap sống/đỉnh từ đầu hoạt cảnh tới hết thoại mới == gốc (không rò thêm)."""
import sys, os, json, glob
from PIL import Image
D = sys.argv[1]; sizes = sys.argv[2:] or ['400x240', '600x270', '1200x540']
out = []; ok_all = True
def log(s): out.append(s); print(s)
def check(c, msg):
    global ok_all
    if not c: ok_all = False
    log(('  ĐẠT  ' if c else '  LỖI  ') + msg)
for sz in sizes:
    W, H = map(int, sz.split('x'))
    log('== %s' % sz)
    try:
        o = json.load(open(os.path.join(D, 'msgs_%s_orig.json' % sz))); n = json.load(open(os.path.join(D, 'msgs_%s_new.json' % sz)))
    except Exception as e:
        log('  thiếu dữ liệu: %s' % e); ok_all = False; continue
    mo, mn = o['msgs'], n['msgs']
    seq_o = [(m['name'], m['text']) for m in mo]; seq_n = [(m['name'], m['text']) for m in mn]
    k = min(len(seq_o), len(seq_n))
    check(len(mo) > 0 and len(mn) > 0, 'có thông điệp (gốc %d, mới %d)' % (len(mo), len(mn)))
    # hai bên có thể dừng ở khung khác nhau (f_end): so phần chung; thông điệp cuối có thể dở dang
    kk = k if len(seq_o) == len(seq_n) else max(k - 1, 0)
    check(seq_o[:kk] == seq_n[:kk] and k >= 1, 'C1 %d/%d thông điệp giống nhau từng byte (id tên + chuỗi), gốc=%d mới=%d' % (kk, k, len(mo), len(mn)))
    po = [len(m['pages']) for m in mo]; pn = [len(m['pages']) for m in mn]
    log('     số trang mỗi thông điệp  gốc: %s' % po[:30]); log('     số trang mỗi thông điệp  mới: %s' % pn[:30])
    okp = True
    for i in range(kk):
        pg = sorted(mn[i]['pages'], key=int)
        if [int(x) for x in pg] != list(range(len(pg))): okp = False
        if not (pn[i] >= po[i] and pn[i] <= 3 * po[i] + 1): okp = False
    check(okp, 'C2 trang liên tục, số trang mới trong [gốc, 3*gốc+1]; tổng trang gốc=%d mới=%d' % (sum(po), sum(pn)))
    g_ok = True
    for m in mn:
        bx, by, bw, bh = m['box']; tx, ty, tw = m['tx']
        if not (0 <= bx and bx + bw <= W and 0 <= by and by + bh <= H and bx <= tx and tx + tw <= bx + bw and by <= ty and tw > 0): g_ok = False
        if bw < min(400, W - 16) or abs(bw * 100 // W - 72) > 3 and bw != W - 16 and bw != 400: g_ok = False
    check(g_ok and len(mn) > 0, 'C3 hình học: hộp %s, vùng chữ %s' % (mn[0]['box'] if mn else '-', mn[0]['tx'] if mn else '-'))
    # C4: tràn chữ
    bad = 0; tot = 0
    for f in sorted(glob.glob(os.path.join(D, 'msg*_%s_new.png' % sz))):
        mi = int(os.path.basename(f)[3:5])
        if mi >= len(mn): continue
        m = mn[mi]; bx, by, bw, bh = m['box']; tx, ty, tw = m['tx']
        im = Image.open(f).convert('RGB'); px = im.load(); tot += 1
        edge = 4
        for y in range(ty, min(by + bh - 6, H)):
            for x in list(range(bx + edge, tx - 3)) + list(range(tx + tw + 2, bx + bw - edge)):
                if m['face'] and ((m['side'] == 0 and x < tx) or (m['side'] == 1 and x > tx + tw)): continue
                r, g, b = px[x, y]
                if r > 225 and g > 225 and b > 215: bad += 1
    check(tot > 0 and bad == 0, 'C4 không có điểm chữ trắng ngoài vùng chữ (%d ảnh trang, %d điểm lạ)' % (tot, bad))
    check(o['heap1'][0] - o['heap0'][0] == n['heap1'][0] - n['heap0'][0] and o['heap1'][1] - o['heap0'][1] == n['heap1'][1] - n['heap0'][1],
          'C5 cấp phát sống/đỉnh trong hoạt cảnh: gốc +%d/+%d, mới +%d/+%d' % (o['heap1'][0] - o['heap0'][0], o['heap1'][1] - o['heap0'][1], n['heap1'][0] - n['heap0'][0], n['heap1'][1] - n['heap0'][1]))
    # C6: cờ MUI_F_DISABLE (mode off, nếu có) == gốc: cùng thông điệp/trang/khung và ảnh trang trùng từng byte
    offj = os.path.join(D, 'msgs_%s_off.json' % sz)
    if os.path.exists(offj):
        import hashlib
        f_ = json.load(open(offj))
        same = [(m['name'], m['text'], sorted(m['pages'].items())) for m in f_['msgs']] == [(m['name'], m['text'], sorted(m['pages'].items())) for m in mo]
        nb = 0; nt = 0
        for fo in sorted(glob.glob(os.path.join(D, 'msg*_%s_orig.png' % sz))):
            fx = fo.replace('_orig.png', '_off.png')
            if os.path.exists(fx):
                nt += 1
                if hashlib.md5(open(fo, 'rb').read()).digest() != hashlib.md5(open(fx, 'rb').read()).digest(): nb += 1
        check(same and nt > 0 and nb == 0, 'C6 cờ tắt == gốc: thông điệp/trang/khung giống hệt; %d ảnh trang, %d khác byte' % (nt, nb))
log('KẾT LUẬN: ' + ('ĐẠT' if ok_all else 'CÒN LỖI'))
open(os.path.join(D, 'check.txt'), 'w').write('\n'.join(out) + '\n')
