#!/usr/bin/env python3
"""T11 (phần 1): ghép ảnh Việt của bản J2ME (HL4_project/img5/overlay, r9_VI) vào các .mgr của bản Android.

  mk_vi_images.py ORIG_VFS J2ME_OVERLAY_RES J2ME_ORIG_RES LOGO_PNG OUT_DIR

Ghi OUT_DIR/c/... (dùng với tools/vfs_pack.py --replace OUT_DIR). Chỉ thay ảnh theo số thứ tự, các ảnh khác giữ nguyên byte.
- c/img/gmenu.mgr 38, 39 (Success/Failed + dòng Hàn): ảnh Android gốc TRÙNG BYTE ảnh J2ME gốc -> chép mục J2ME Việt.
- c/img/menu.mgr 12, 13 (huy hiệu), 14-18 (đoạn mở đầu): Android có bản chữ Anh cùng vai trò, cùng kích thước với J2ME gốc
  (kiểm) -> chép mục J2ME Việt (14-17 to hơn, game vẽ căn giữa theo điểm neo như bản J2ME).
- c/sp/imgcom/ealogo.mgr 0-2: logo "Việt hoá bởi NAMP" (LOGO_PNG 240x320) thu về cao 220 (vừa khung logic cao >= 240),
  3 mức sáng 35/70/100 % như mk_ealogo.py; 8 bpp <= 255 màu, không dùng 0xF81F.
Logo tiêu đề (title.mgr) KHÔNG đổi ở đây (bản Android đã là chữ Anh "HEROES LORE"; dòng ghi công làm riêng).
"""
import sys, os, struct
from PIL import Image, ImageEnhance
HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, os.path.join(HERE, '..', 'tools'))
from vfs_pack import djb2, read_vfs

vfs_path, jvi, jorig, logo, out = sys.argv[1:6]


def parse(b):
    n = struct.unpack_from('<I', b, 0)[0]; p = 4; r = []
    for _ in range(n):
        s = struct.unpack_from('<I', b, p)[0]; r.append(b[p + 4:p + 4 + s]); p += 4 + s
    assert p == len(b)
    return r


def build(ents):
    return struct.pack('<I', len(ents)) + b''.join(struct.pack('<I', len(e)) + e for e in ents)


def size(e):
    return struct.unpack_from('<HH', e, 2)


def enc8(im):
    q = im.convert('RGB').quantize(colors=255, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.FLOYDSTEINBERG)
    pal = q.getpalette(); n = max(q.getdata()) + 1; cols = []
    for i in range(n):
        r, g, b = pal[3 * i:3 * i + 3]
        c = ((r >> 3) << 11) | ((g >> 2) << 5) | (b >> 3)
        cols.append(0xF81E if c == 0xF81F else c)
    w, h = q.size
    return struct.pack('<BBHH', 0x18, n, w, h) + struct.pack('<%dH' % n, *cols) + bytes(q.getdata())


E = {h: b for h, b in read_vfs(open(vfs_path, 'rb').read())}
jobs = {'c/img/gmenu.mgr': ([38, 39], 'same'), 'c/img/menu.mgr': ([12, 13, 14, 15, 16, 17, 18], 'size')}
for f, (ids, mode) in jobs.items():
    an = parse(E[djb2(f)]); jv = parse(open(os.path.join(jvi, f), 'rb').read()); jo = parse(open(os.path.join(jorig, f), 'rb').read())
    for i in ids:
        assert jv[i] != jo[i], '%s %d: J2ME chưa vẽ lại' % (f, i)
        if mode == 'same': assert an[i] == jo[i], '%s %d: ảnh Android khác J2ME gốc' % (f, i)
        else: assert size(an[i]) == size(jo[i]), '%s %d: kích thước Android khác J2ME gốc' % (f, i)
        an[i] = jv[i]
    os.makedirs(os.path.join(out, os.path.dirname(f)), exist_ok=True)
    open(os.path.join(out, f), 'wb').write(build(an))
    print(f, 'thay', ids)
f = 'c/sp/imgcom/ealogo.mgr'
an = parse(E[djb2(f)]); assert len(an) == 3
src = Image.open(logo).convert('RGB'); assert src.size == (240, 320), src.size
src = src.resize((165, 220), Image.LANCZOS)
ents = [enc8(ImageEnhance.Brightness(src).enhance(k)) for k in (0.35, 0.7, 1.0)]
os.makedirs(os.path.join(out, os.path.dirname(f)), exist_ok=True)
open(os.path.join(out, f), 'wb').write(build(ents))
print(f, 'logo NAMP 165x220 x3 mức sáng')
