"""Ban do the gioi png64 (420x280): xoa nhan chu Han (inpaint OpenCV, chi dung khi tao anh) va ve ten tieng Viet
bang f12 (than chu + vien 1px lay mau nhan goc).  python3 make_map.py <png64 goc> <ra>"""
import sys
sys.path.insert(0, 'scripts')
import numpy as np, cv2
from PIL import Image
import f12
from make_png7 import outlined
WHITE = ('f8f8f8', '202020'); GOLD = ('f7e08c', '3a2a10')
# (hop chu goc x0,y0,x1,y1), [dong chu], kieu, (tam x, tam y) hoac None = tam hop
L = [((66, 42, 92, 54), ['Cửa Tây'], WHITE, None),
     ((196, 35, 240, 50), ['Mộ y quan'], WHITE, None),
     ((326, 21, 401, 41), ['Phong Tuyết', 'Ngân Thành'], GOLD, (366, 31)),
     ((81, 69, 126, 90), ['Thiên Hương'], GOLD, None),
     ((147, 84, 176, 97), ['Cửa Đông'], WHITE, None),
     ((58, 101, 84, 114), ['Cửa Nam'], WHITE, None),
     ((236, 105, 281, 119), ['Sườn Tây'], WHITE, None),
     ((160, 147, 220, 162), ['Tửu quán Tống Ký'], WHITE, None),
     ((259, 144, 331, 162), ['Rừng Thiên Phạt'], GOLD, None),
     ((344, 160, 388, 174), ['Sườn Đông'], WHITE, None),
     ((46, 165, 102, 179), ['Rừng ngoại thành'], WHITE, None),
     ((259, 184, 303, 197), ['Sườn Nam'], WHITE, None),
     ((116, 240, 172, 255), ['Vườn Đông Phương'], WHITE, None),
     ((271, 255, 326, 270), ['Huyết Hồn sơn trang'], WHITE, None)]
src, out = sys.argv[1:3]
im = Image.open(src).convert('RGB')
a = np.array(im)[:, :, ::-1].copy()
mask = np.zeros(a.shape[:2], np.uint8)
for (x0, y0, x1, y1), *_ in L:
    mask[y0:y1, x0:x1] = 255
a = cv2.inpaint(a, mask, 4, cv2.INPAINT_TELEA)
res = Image.fromarray(a[:, :, ::-1].copy()).convert('RGBA')
for (x0, y0, x1, y1), lines, (body, line), c in L:
    cx, cy = c if c else ((x0 + x1) // 2, (y0 + y1) // 2)
    ims = [outlined(t, body, line) for t in lines]
    hh = sum(i.height for i in ims) - 2 * (len(ims) - 1)
    y = cy - hh // 2
    for i in ims:
        x = min(max(1, cx - i.width // 2), res.width - i.width - 1)
        res.alpha_composite(i, (x, y)); y += i.height - 2
q = res.convert('RGB').quantize(colors=256, method=Image.Quantize.MEDIANCUT, dither=Image.Dither.NONE)
q.save(out, format='PNG', optimize=True)
print('ok', out)
