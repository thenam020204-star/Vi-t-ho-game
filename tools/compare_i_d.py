"""font/preview/compare_i_d.png: truoc (work/font/_preview_old.ttf) / sau (_preview.ttf) cho i co dau, d/D gach, dau thuon.
   python3 -I tools/compare_i_d.py [chars]"""
import os
import sys
from PIL import Image, ImageDraw, ImageFont

ROOT = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))
OLD = os.path.join(ROOT, 'work', 'font', '_preview_old.ttf')
NEW = os.path.join(ROOT, 'work', 'font', '_preview.ttf')
OUT = os.path.join(ROOT, 'font', 'preview', 'compare_i_d.png')
LAB = ImageFont.truetype('/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf', 16)


def main(rows=None):
    rows = rows or ['íìỉĩịÍÌỈĨỊ', 'đĐáàảãạ', 'ÁÀẢÃẠắầẩ', 'ơớờởỡợưứừửữự']
    SZ = 120
    fo, fn = ImageFont.truetype(OLD, SZ), ImageFont.truetype(NEW, SZ)
    adv = SZ * 0.8
    W = int(max(len(r) for r in rows) * adv) + 140
    H = len(rows) * (2 * (SZ + 40) + 20) + 20
    im = Image.new('RGB', (W, H), (255, 255, 255))
    d = ImageDraw.Draw(im)
    y = 10
    for r in rows:
        for lab, f in (('truoc', fo), ('sau', fn)):
            d.text((8, y + SZ // 2), lab, font=LAB, fill=(200, 40, 40) if lab == 'truoc' else (30, 90, 200))
            d.line([(0, y + SZ + 40 - 18), (W, y + SZ + 40 - 18)], fill=(235, 235, 245))
            for i, ch in enumerate(r):
                d.text((70 + i * adv, y), ch, font=f, fill=(20, 20, 20))
            y += SZ + 40
        y += 20
    im.save(OUT)
    print('ok ->', OUT)


if __name__ == '__main__':
    main([a for a in sys.argv[1:]])
