"""T14a: contact sheet shots/T14/ui_hien_tai.png: ảnh HUD gốc 600x270 phóng x3, vùng chạm gốc đánh số (đọc từ res_new_600x270.json)."""
import sys, os, json
from PIL import Image, ImageDraw
D = os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', '..', 'shots', 'T14')
NM = ['ATT tấn công', 'S0 ô kỹ năng 0', 'S1 ô 1 (bình)', 'S2 ô 2', 'S3 ô 3', 'S4 ô 4', 'S5 ô 5', 'SWAP đổi bộ', 'DASH', 'PAUSE tạm dừng/menu', 'MAP bản đồ', 'SHOP cửa hàng', 'SAVE lưu']
r = json.load(open(os.path.join(D, 'res_new_600x270.json')))['new_layout']
im = Image.open(os.path.join(D, 'hud_orig_600x270.png')).convert('RGB'); k = 3
im = im.resize((im.width * k, im.height * k), Image.NEAREST); d = ImageDraw.Draw(im)
for i in range(13):
    if not r['want'][i]: continue
    x0, y0, x1, y1 = [v * k for v in r['old'][i]]
    d.rectangle([x0, y0, x1, y1], outline=(255, 60, 60), width=2); d.rectangle([x0, y0, x0 + 26, y0 + 22], fill=(255, 60, 60)); d.text((x0 + 4, y0 + 4), str(i + 1), fill=(255, 255, 255))
W = Image.new('RGB', (im.width, im.height + 120), (20, 20, 24)); W.paste(im, (0, 0)); d = ImageDraw.Draw(W)
for i in range(13):
    d.text((12 + (i % 4) * 440, im.height + 8 + (i // 4) * 22), '%d  %s   (%d,%d)-(%d,%d)' % (i + 1, NM[i], *r['old'][i]), fill=(235, 235, 235))
W.save(os.path.join(D, 'ui_hien_tai.png'))
