"""Quản đốc: tạo đầu vào cho dp từ work/text_can_dich.tsv.
- work/dp_in.tsv: id, gốc, ghi chú, giới hạn (số ký tự, chỉ cho chuỗi 1 dòng không wrap).
  Giới hạn ký tự = px / độ rộng TB của chữ Việt trong f13c (ước lượng; text_pack.py đo px chính xác sau).
- work/charset_f13c.txt: bảng glyph f13c.
"""
import csv, os, re, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from text_pack import Font, FONT
f = Font(FONT)
sample = 'Ngươi dám đến đây sao? Ta là Diệp Vấn Thiên, hôm nay quyết không tha cho ngươi. Huyết Kiếm, Ma Thiên'
avg = f.width(sample) / float(len(sample))
root = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
rows = list(csv.reader(open(os.path.join(root, 'work/text_can_dich.tsv'), encoding='utf-8'), delimiter='\t'))
out = open(os.path.join(root, 'work/dp_in.tsv'), 'w', encoding='utf-8', newline='\n')
out.write('id\tgốc\tghi chú\tgiới hạn\n')
for r in rows[1:]:
    lim = r[6]
    m = re.match(r'~?(\d+)px$', lim)
    n = str(int(int(m.group(1)) / avg)) if m else ''
    ctx = '%s | %s | %s' % (r[1], r[5], lim)
    out.write('\t'.join([r[0], r[3], ctx.replace('\t', ' '), n]) + '\n')
out.close()
open(os.path.join(root, 'work/charset_f13c.txt'), 'w', encoding='utf-8').write(f.charset)
print('avg px/char %.2f, rows %d, charset %d' % (avg, len(rows) - 1, len(f.charset)))
