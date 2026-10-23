"""font/preview/sample_{17,18,24,30}.png: cau mau tieng Viet (FreeType, nhu Unity) - Jeju-VI vs Jeju goc vs NotoSansDisplay-Medium.
   Anh render dung pixel that roi phong 3x (nearest) de soi.   python3 -I tools/make_samples.py"""
import os
from PIL import Image, ImageDraw, ImageFont

ROOT = os.path.abspath(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))
F = os.path.join(ROOT, 'work', 'font')
OUT = os.path.join(ROOT, 'font', 'preview')
FONTS = [('Jeju-VI (moi)', 'JejuHallasan-VI.ttf'), ('Jeju goc', 'JejuHallasan.ttf'), ('NotoSansDisplay-Medium', 'NotoSansDisplay-Medium.ttf')]
LINES = [
    'Tiếng Việt: Phù thủy Nguyễn Quỳnh Hương đã đánh bại Quỷ Vương ở Đảo Ẩn!',
    'ĐỘC DƯỢC · Kỹ năng tăng 50% sát thương · Hồi phục ngay lập tức · Mỗi ơ ư',
    'Ắ Ằ Ẳ Ẵ Ặ  Ấ Ầ Ẩ Ẫ Ậ  Ế Ề Ể Ễ Ệ  Ố Ồ Ổ Ỗ Ộ  Ớ Ờ Ở Ỡ Ợ  Ứ Ừ Ử Ữ Ự  Í Ì Ỉ Ĩ Ị  Ý Ỳ Ỷ Ỹ Ỵ',
    'ắ ằ ẳ ẵ ặ  ấ ầ ẩ ẫ ậ  ế ề ể ễ ệ  ố ồ ổ ỗ ộ  ớ ờ ở ỡ ợ  ứ ừ ử ữ ự  í ì ỉ ĩ ị  ý ỳ ỷ ỹ ỵ',
    'Đường đi đến đền thờ nằm giữa những đồi cỏ; ĐÔNG ĐẢO, Đại Đế quản lý.',
]


def main():
    os.makedirs(OUT, exist_ok=True)
    lab = ImageFont.truetype('/usr/share/fonts/truetype/dejavu/DejaVuSans.ttf', 11)
    for sz in (17, 18, 24, 30):
        W = 1500 if sz >= 24 else 1100
        rows = []
        for name, fn in FONTS:
            rows.append((name, ImageFont.truetype(os.path.join(F, fn), sz)))
        lh = int(sz * 1.9)
        H = 8 + len(FONTS) * (len(LINES) * lh + 22) + 8
        im = Image.new('RGB', (W, H), (255, 255, 255))
        d = ImageDraw.Draw(im)
        y = 6
        for name, f in rows:
            d.text((4, y), '== %s, %dpx' % (name, sz), font=lab, fill=(200, 40, 40))
            y += 16
            for ln in LINES:
                d.text((4, y), ln, font=f, fill=(15, 15, 15))
                y += lh
            y += 6
        im = im.crop((0, 0, W, y + 4)).resize((W * 2, (y + 4) * 2), Image.NEAREST) if sz < 24 else im.crop((0, 0, W, y + 4))
        im.save(os.path.join(OUT, 'sample_%d.png' % sz))
    print('ok')


if __name__ == '__main__':
    main()
