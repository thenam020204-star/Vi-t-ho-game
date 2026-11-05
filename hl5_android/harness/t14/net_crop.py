"""T14c: ảnh so nét nút trước/sau: net_crop.py W H PRE_BEFORE PRE_AFTER OUT.png  (vùng nút phải-dưới, phóng 2x NEAREST, trước | sau)"""
import sys
from PIL import Image
W, Hh, pa, pb, out = int(sys.argv[1]), int(sys.argv[2]), sys.argv[3], sys.argv[4], sys.argv[5]
cw, ch = 150 * Hh // 270, 170 * Hh // 270
def crop(p): return Image.frombytes('RGB', (W, Hh), open(p + '_idle.rgb', 'rb').read()).crop((W - cw, Hh - ch, W, Hh)).resize((cw * 2, ch * 2), Image.NEAREST)
a, b = crop(pa), crop(pb); im = Image.new('RGB', (cw * 4 + 8, ch * 2), (255, 0, 255)); im.paste(a, (0, 0)); im.paste(b, (cw * 2 + 8, 0)); im.save(out)
