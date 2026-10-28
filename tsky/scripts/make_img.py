#!/usr/bin/env python3
"""T8: vẽ lại ảnh có chữ Hán bằng pixeltext f12 -> res_vi/Image/.
Chạy:  source work/tools/env.sh && python3 scripts/make_img.py
Quy tắc: xoá sạch chữ gốc, một màu thân chữ, không viền/bóng, giữ kích thước + bảng màu P/trong suốt."""
import os, sys
from PIL import Image
import pixeltext as pt
from pixeltext import paint as R, metrics as M

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
SRC = os.path.join(ROOT, 'work/x/Image')
DST = os.path.join(ROOT, 'res_vi/Image')
SIZE = 12
OUTLINE = '#000000'   # viền đen 1px 8 hướng theo yêu cầu người dùng (đặt None để tắt)
CHECK = []   # mọi nhãn để chạy pixeltext check

class Img:
    def __init__(self, name):
        self.name = name
        self.orig = Image.open(os.path.join(SRC, name + '.png'))
        self.im = self.orig.convert('RGBA')
        self.px = self.im.load()

    # ---- xoá
    def clear(self, box, color=(0, 0, 0, 0)):
        x0, y0, w, h = box
        for y in range(y0, y0 + h):
            for x in range(x0, x0 + w):
                self.px[x, y] = color

    def clear_rows(self, box, xs):
        """xoá bằng màu mẫu theo hàng tại cột xs (nền nút chuyển sắc theo chiều dọc)"""
        x0, y0, w, h = box
        for y in range(y0, y0 + h):
            c = self.px[xs, y]
            for x in range(x0, x0 + w):
                self.px[x, y] = c

    def inpaint(self, mask):
        """lấp các pixel trong mask bằng trung bình các pixel lân cận không thuộc mask (lan dần)"""
        mask = set(mask)
        W, H = self.im.size
        while mask:
            fill = {}
            for (x, y) in mask:
                nb = [self.px[x+dx, y+dy] for dx in (-1, 0, 1) for dy in (-1, 0, 1)
                      if (dx or dy) and 0 <= x+dx < W and 0 <= y+dy < H and (x+dx, y+dy) not in mask]
                if nb:
                    fill[(x, y)] = tuple(sum(c[i] for c in nb) // len(nb) for i in range(4))
            if not fill:
                break
            for k, c in fill.items():
                self.px[k] = c
            mask -= set(fill)

    def mask_where(self, box, pred, grow=0):
        x0, y0, w, h = box
        m = {(x, y) for x in range(x0, x0 + w) for y in range(y0, y0 + h) if pred(self.px[x, y], x, y)}
        for _ in range(grow):
            m |= {(x+dx, y+dy) for (x, y) in m for dx in (-1, 0, 1) for dy in (-1, 0, 1)
                  if 0 <= x+dx < self.im.width and 0 <= y+dy < self.im.height}
        return m

    # ---- vẽ
    def text(self, box, txt, color, mode='metric', align='center', valign='center', dx=0, dy=0):
        CHECK.append(txt)
        r = R.draw(self.im, box, txt, size=SIZE, mode=mode, gap=1, align=align, valign=valign,
                   dx=dx, dy=dy, fill=color + (255,) if len(color) == 3 else color, outline=OUTLINE, outline_diag=True, clip=True)
        if r['overflow_x'] or r['overflow_y']:
            print('  TRÀN', self.name, txt, box, r)
        return r

    def text_rot(self, box, txt, color, mode='metric'):
        """chữ xoay 90 độ theo chiều kim đồng hồ (biển dọc), canh giữa trong box"""
        CHECK.append(txt)
        t = R.render(txt, SIZE, mode, 1, trim=True, fill=color + (255,), outline=OUTLINE, outline_diag=True)
        t = t.rotate(-90, expand=True)
        x0, y0, w, h = box
        if t.width > w or t.height > h:
            print('  TRÀN', self.name, txt, (t.width, t.height), box)
        self.im.alpha_composite(t, (x0 + (w - t.width) // 2, y0 + (h - t.height) // 2))

    # ---- lưu (giữ P + trong suốt nếu ảnh gốc là P)
    def save(self, out=None):
        out = out or os.path.join(DST, self.name + '.png')
        os.makedirs(os.path.dirname(out), exist_ok=True)
        if self.orig.mode != 'P':
            self.im.save(out); return out
        pal = self.orig.getpalette() or []
        tr = self.orig.info.get('transparency')
        ents = []   # (r,g,b,a) theo thứ tự palette gốc
        n = len(pal) // 3
        for i in range(n):
            a = 255
            if isinstance(tr, (bytes, bytearray)): a = tr[i] if i < len(tr) else 255
            elif isinstance(tr, int): a = 0 if i == tr else 255
            ents.append((pal[3*i], pal[3*i+1], pal[3*i+2], a))
        idx = {}
        for i, e in enumerate(ents):
            idx.setdefault(e, i)
        clear_i = next((i for i, e in enumerate(ents) if e[3] == 0), None)
        outp = Image.new('P', self.im.size)
        o = outp.load(); px = self.im.load()
        for y in range(self.im.height):
            for x in range(self.im.width):
                c = px[x, y]
                if c[3] == 0:
                    if clear_i is None:
                        ents.append((0, 0, 0, 0)); clear_i = len(ents) - 1
                    o[x, y] = clear_i; continue
                if c not in idx:
                    ents.append(c); idx[c] = len(ents) - 1
                o[x, y] = idx[c]
        if len(ents) > 256:
            print('  >256 màu, lưu RGBA', self.name); self.im.save(out); return out
        flat = []
        for e in ents: flat += list(e[:3])
        outp.putpalette(flat)
        trn = bytes(e[3] for e in ents)
        if any(a != 255 for a in trn): outp.info['transparency'] = trn
        outp.save(out, transparency=trn if any(a != 255 for a in trn) else None, optimize=False)
        return out

def rows(name, texts, rowh, w, colors, mode='metric', **kw):
    im = Img(name)
    for i, t in enumerate(texts):
        box = (0, i * rowh, w, rowh)
        im.clear(box)
        col = colors[i] if isinstance(colors, list) else colors
        im.text(box, t, col, mode=mode)
    return im

DONE = []
def done(im):
    p = im.save(); DONE.append(im.name); print('ok', im.name)

# ============================================================ danh sách
def main():
    # --- ảnh lưới chữ (nền trong suốt)
    done(rows('ui_shuxing1',
        ['ATK', 'SPD', 'Bạo', 'DEF', 'Né', 'Pháp', 'Vị', 'Lv', 'Lực', 'Trí', 'Thể', 'Mẫn', 'Vận'], 13, 26,
        [(244,0,5),(255,114,0),(255,222,0),(48,185,0),(0,117,255),(89,239,238),(255,128,233),(255,240,51),
         (255,189,34),(255,189,34),(255,189,34),(255,189,34),(255,189,34)]))
    done(rows('ui_system',
        ['Nhiệm vụ', 'Túi tiên sủng', 'Cài chiến đấu', 'Vật phẩm', 'Trang bị', 'Sủng toàn thư', 'Hệ thống'], 17, 78, (0,0,0)))
    done(rows('ui_system2',
        ['Lưu tiến độ', 'Tải tiến độ', 'Cài đặt nhạc', 'Hướng dẫn', 'Thoát game'], 17, 78, (0,0,0)))
    SHOP = (60, 80, 40)
    done(rows('shop_sms',
        ['Gấp đôi EXP', 'Gấp đôi tiền', 'Lên liền 3 cấp', 'Mua tiền', 'Mua T.Long', 'Cửa hàng'], 17, 78, SHOP))
    done(rows('ui_lifeword', ['HP', 'MP', 'EXP'], 15, 32, (255,255,255)))
    done(rows('ui_money', ['Tiền'], 18, 36, (255,198,0)))
    im = Img('ui_pro')
    for i, (t, c) in enumerate([('Kim',(240,111,4)),('Mộc',(51,163,39)),('Thủy',(75,137,227)),('Hỏa',(239,42,3)),('Thổ',(124,83,7))]):
        b = (29*i, 0, 29, 28); im.clear(b); im.text(b, t, c)
    done(im)
    im = Img('ui_fight_mid')
    xs = [0, 29, 58, 86, 115, 144, 173]
    for i, (t, c) in enumerate([('Đổi',(31,131,255)),('Chạy',(54,143,0)),('Sủng',(31,131,255)),
                                ('Vật',(31,131,255)),('Đánh',(31,131,255)),('Phá',(255,121,1))]):
        b = (xs[i], 0, xs[i+1]-xs[i], 28); im.clear(b); im.text(b, t, c)
    done(im)
    im = Img('number1')
    for i, t in enumerate(list('0123456789') + ['Chủ', 'Phụ']):
        b = (18*i, 0, 18, 17); im.clear(b); im.text(b, t, (0,0,0), mode='fixed' if len(t) > 1 else 'metric')
    done(im)
    # --- nhãn lớn / hiệu ứng
    im = Img('baoji'); im.clear((0,0,82,36)); im.text((0,0,82,36), 'Bạo kích!', (255,149,0)); done(im)
    im = Img('shanbi'); im.clear((0,0,95,36)); im.text((0,0,95,36), 'Né!', (206,117,255)); done(im)
    im = Img('tx_ke')
    im.inpaint(im.mask_where((18, 15, 34, 34), lambda p, x, y: p[3] > 0 and p[1] > p[0] + 12 and p[1] > p[2] + 12, 1))
    im.text((14, 20, 42, 24), 'Khắc', (33,199,19)); done(im)
    im = Img('tx_qu')
    im.inpaint(im.mask_where((18, 15, 34, 34), lambda p, x, y: p[3] > 0 and p[0] > 60 and p[1] < 80 and p[0] > p[1] + 40, 1))
    im.text((14, 20, 42, 24), 'Kém', (223,0,0)); done(im)
    im = Img('ok'); im.clear((0,0,26,20)); im.text((0,0,26,20), 'Xong', (204,0,0)); done(im)
    # --- rand: giáp ất bính đinh trong vòng tròn
    im = Img('rand')
    for i, t in enumerate(['G', 'Ấ', 'B', 'Đ']):
        b = (26*i+5, 5, 16, 16)
        for y in range(26):
            for x in range(26*i, 26*i+26):
                r = ((x - 26*i - 12.0) ** 2 + (y - 12.0) ** 2) ** 0.5
                if im.px[x, y][3] == 0 and r > 10.3: continue
                im.px[x, y] = (0,0,0,255) if (r > 10.3 or 6.0 < r <= 7.0) else (237,20,0,255)
        im.text(b, t, (0,0,0))
    done(im)
    # --- biển địa danh dọc (ad: 6 ô rộng 21), chữ xoay
    im = Img('ad')
    names = ['Mộc Lỗi thôn', 'Sa Lĩnh trấn', 'Hỏa Thạch', 'Lãnh Phong', 'Thiên Sơn', 'Liên Trì']
    for i, t in enumerate(names):
        b = (21*i, 0, 21, 70); im.clear(b); im.text_rot((21*i+1, 0, 19, 70), t, (198,57,49))
    done(im)
    # --- key.png: nhãn trên nút phím mềm (nền nút chuyển sắc theo hàng)
    im = Img('key')
    im.clear_rows((120, 330, 57, 19), 117); im.text((120, 330, 57, 19), 'Phím trái', (72,72,83))
    im.clear_rows((462, 330, 57, 19), 457); im.text((462, 330, 57, 19), 'Phím phải', (72,72,83))
    done(im)
    # --- CG2: chữ vàng trên nền đen (giữ dòng "...")
    im = Img('CG2')
    GOLD = (194,160,69)
    for (y0, y1, t) in [(24, 50, 'Đây là đâu!?'), (87, 108, 'Ta chết như vậy sao?'),
                        (120, 142, 'Ông nội, Tiểu Linh Tử còn đợi ta...')]:
        im.clear((0, y0, 240, y1 - y0), (0,0,0,255)); im.text((0, y0, 240, y1 - y0), t, GOLD)
    done(im)
    # --- npc21: bia mộ, chữ khắc dọc
    im = Img('npc21')
    im.inpaint(im.mask_where((16, 6, 13, 36), lambda p, x, y: p[3] > 0 and sum(p[:3]) < 330))
    im.text((16, 8, 13, 14), 'Mộ', (70, 70, 50)); done(im)
    # --- obj_jin0: biển hiệu
    im = Img('obj_jin0')
    GL = {(145,79,32,255), (142,53,11,255)}
    def inside(x, y):
        return 50 + (y - 40) * 5 // 55 + 3 <= x <= 75 + (y - 46) * 3 // 50 - 2
    im.inpaint(im.mask_where((50, 50, 30, 55), lambda p, x, y: p in GL and inside(x, y), 1))
    im.text_rot((54, 50, 20, 52), 'Kim Loan', (142,53,11)); done(im)

if __name__ == '__main__':
    main()
    open(os.path.join(ROOT, 'work/check_labels.txt'), 'w').write('\n'.join(CHECK))
