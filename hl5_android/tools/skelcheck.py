#!/usr/bin/env python3
"""So khung cấu trúc (bỏ chuỗi) giữa file LGT tiếng Hàn và file Android (đã Anh hoá).
Báo những file có khác biệt ngoài chuỗi -> không được thay thẳng bằng bản Việt build từ LGT.
Dùng: skelcheck.py VFS LGT_RES_DIR OVERLAY_RES_DIR EXTRACT5_DIR"""
import sys, os, struct, glob
sys.path.insert(0, sys.argv[4])
from extract5 import parse_dat, parse_scn, rec_strings
vfs = open(sys.argv[1], 'rb').read(); lg = sys.argv[2]; ov = sys.argv[3]
H = {}; o = 0
while o + 8 <= len(vfs):
    h, s = struct.unpack_from('<II', vfs, o); H[h] = (o + 8, s); o += 8 + s
def djb(s):
    h = 5381
    for c in s.encode(): h = (h * 33 + c) & 0xffffffff
    return h
def skel_rec(kr, an):
    """Đi song song theo bố cục chuỗi của bản Hàn; trả về (ok, lý do)."""
    ks = rec_strings(kr); pa = 0; pk = 0
    for (i, ps, n) in ks:
        if kr[pk:i] != an[pa:pa + (i - pk)]: return False, 'field@%d' % pk
        pa += i - pk
        na = an[pa] if ps == 1 else an[pa] | an[pa + 1] << 8
        pa += ps + na; pk = i + ps + n
    if kr[pk:] != an[pa:]: return False, 'tail'
    return True, ''
bad = 0; good = 0
for p in sorted(glob.glob(ov + '**/*', recursive=True)):
    if not os.path.isfile(p): continue
    rel = p[len(ov):]; e = H.get(djb(rel))
    if not e: continue
    an = vfs[e[0]:e[0] + e[1]]; kr = open(lg + rel, 'rb').read()
    try:
        if rel.endswith('.scn'):
            pk, _ = parse_scn(kr); pa, _ = parse_scn(an)
            ok = pk is not None and pa is not None and kr[:pk] == an[:pa]; why = 'code'
        elif rel.endswith('.dat'):
            rk = parse_dat(kr); ra = parse_dat(an)
            ok = len(rk) == len(ra); why = 'nrec %d/%d' % (len(rk), len(ra))
            if ok:
                for j, (a, b) in enumerate(zip(rk, ra)):
                    ok, w = skel_rec(a, b)
                    if not ok: why = 'rec%d %s' % (j, w); break
        else:
            ok = False; why = 'kiểu file khác'
    except Exception as ex:
        ok = False; why = 'parse %r' % ex
    if ok: good += 1
    else: bad += 1; print('KHÁC', rel, why)
print('khớp khung:', good, 'khác:', bad)
