import sys
from PIL import Image, ImageDraw, ImageFont, ImageFilter
src, out = sys.argv[1], sys.argv[2]
base = Image.open(src).convert('RGBA').resize((2000, 900), Image.LANCZOS); W, H = base.size
F = '/usr/share/fonts/truetype/dejavu/'
txt = ImageFont.truetype(F+'DejaVuSans.ttf', 34)
name_f = ImageFont.truetype(F+'DejaVuSerif-Bold.ttf', 30)
small = ImageFont.truetype(F+'DejaVuSans.ttf', 20)

# 1) xoá hộp thoại cũ + chân dung cũ: lấy dải bản đồ phía trên phủ xuống (chỉ để minh hoạ)
clean = base.copy()
strip = base.crop((0, 160, W, 530))      # dải bản đồ cao 370 px thay toàn bộ đáy (xoá hộp + chân dung cũ)
clean.paste(strip, (0, 530))
clean = clean.filter(ImageFilter.GaussianBlur(0))

# 2) chân dung cũ cắt từ ảnh gốc, phóng NEAREST x1.6
por = base.crop((28, 548, 218, 758)).resize((285, 315), Image.NEAREST)   # x1.5 NEAREST

ov = Image.new('RGBA', (W, H), (0, 0, 0, 0)); d = ImageDraw.Draw(ov)
# hộp thoại: rộng 72% màn, căn giữa, cách đáy 3%
bw, bh = int(W*0.72), int(H*0.22)
bx, by = (W-bw)//2, H - bh - int(H*0.03)
d.rounded_rectangle((bx, by, bx+bw, by+bh), 18, fill=(12, 16, 24, 215))
d.rounded_rectangle((bx, by, bx+bw, by+bh), 18, outline=(230, 140, 30, 255), width=5)
d.rounded_rectangle((bx+9, by+9, bx+bw-9, by+bh-9), 12, outline=(120, 70, 20, 255), width=2)
# chân dung đứng bên trái, chồng mép trên hộp
px, py = bx + 20, by - por.size[1] + 120
d.rounded_rectangle((px-8, py-8, px+por.size[0]+8, py+por.size[1]+8), 14, fill=(12,16,24,235), outline=(230,140,30,255), width=5)
ov.alpha_composite(por, (px, py))
d.rounded_rectangle((px-8, py-8, px+por.size[0]+8, py+por.size[1]+8), 14, outline=(230,140,30,255), width=5)
# bảng tên trên mép trái hộp, sau chân dung
nx, ny, nw, nh = bx + 330, by - 34, 230, 54
d.rounded_rectangle((nx, ny, nx+nw, ny+nh), 10, fill=(20, 22, 30, 240), outline=(230, 140, 30, 255), width=4)
d.text((nx+nw//2, ny+nh//2), 'Tir', font=name_f, fill=(255, 225, 150, 255), anchor='mm')
# chữ: lề trái sau chân dung, tối đa 2–3 dòng
tx, ty = bx + 340, by + 40
lines = ['Ngươi từng là cánh tay phải của Kefnes, từng là tôi tớ',
         'lâu đời nhất của Cổ Thần, sao ta lại có thể tin ngươi được?']
for i, l in enumerate(lines):
    d.text((tx, ty + i*52), l, font=txt, fill=(245, 240, 230, 255))
# mũi tên "tiếp" nhấp nháy góc phải dưới
ax, ay = bx+bw-50, by+bh-45
d.polygon([(ax, ay), (ax+28, ay), (ax+14, ay+20)], fill=(240, 160, 40, 255))
# nút Bỏ qua (thay "#: Skip") góc trên phải hộp
sx, sy = bx+bw-170, by-34
d.rounded_rectangle((sx, sy, sx+150, sy+48), 10, fill=(20, 22, 30, 230), outline=(230, 140, 30, 255), width=3)
d.text((sx+75, sy+24), 'Bỏ qua ▶▶', font=small, fill=(255, 225, 150, 255), anchor='mm')

# chú thích kích thước (logic, theo tỉ lệ khung)
ann = Image.new('RGBA', (W, H), (0, 0, 0, 0)); a = ImageDraw.Draw(ann)
C = (90, 220, 255, 255)
def note(x, y, s):
    a.rectangle((x-4, y-2, x + a.textlength(s, font=small) + 4, y + 24), fill=(0, 0, 0, 170))
    a.text((x, y), s, font=small, fill=C)
a.line((bx, by-60, bx+bw, by-60), fill=C, width=2)
note(bx + bw//2 - 170, by-88, 'rộng 72% khung, căn giữa (≥ 400 logic)')
a.line((bx+bw+20, by, bx+bw+20, by+bh), fill=C, width=2)
note(bx+bw+28, by+bh//2-12, 'cao 22% (3 dòng 18px)')
note(px+10, py-30, 'chân dung ×1.5–2 (NEAREST), chồng mép hộp')
note(tx, by+bh-34, 'lề chữ trái = sau chân dung; tự xuống dòng theo bề rộng hộp')
note(20, 20, 'MOCKUP khung hội thoại HD (v0.3) — vẽ trên ảnh người dùng gửi')

res = Image.alpha_composite(Image.alpha_composite(clean, ov), ann)
res.convert('RGB').save(out, quality=92)
# bản không chú thích
Image.alpha_composite(clean, ov).convert('RGB').save(out.replace('.jpg', '_sach.jpg'), quality=92)
