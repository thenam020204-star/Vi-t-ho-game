#!/usr/bin/env python3
"""Tach text can dich tu text/eng/*.txt -> text/segments.tsv.  Chay: python3 -I tools/text_extract.py
Cot: no (so thu tu 1..N, dung de chia lo), key (chuoi EN duy nhat; = ban EN), en (giong key), count, refs (Nhom:dong:cot, dong 1 = header,
cot tinh tu 0), font_size_min, max_width_hint (de trong - khong suy ra duoc tu CSV), en_w24 (do rong EN o co 24 bang Jeju-VI, px), note.
note: loai o (ui/name/desc, hop cac vi tri), c=co ngoac mau/the/id ben trong, ?=chua chac hien thi.
Quy tac tach: ' @ ', '@', '^' la phan cach; cap ngoac mau bao tron doan bi tach ra ngoai key; ngoac mau/##id##/<color> ben trong giu nguyen trong key."""
import sys, os, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from text_common import *

UNSURE = {  # (nhom, ten cot Han) -> ly do
    ('Explain', '일지 조건 이름'): 'ten dieu kien nhat ky (trung ten khu vuc)',
    ('Explain', '구역 효과'): 'hieu ung khu vuc',
}

def text_width(face, s, size=24):
    face.set_pixel_sizes(0, size)
    w = 0
    for ch in s:
        face.load_char(ch)
        w += face.glyph.advance.x / 64.0
    return w

def main():
    face = None
    try:
        import freetype
        face = freetype.Face(FONT_VI)
    except Exception as e:
        print('canh bao: khong do duoc rong (%s)' % e)
    segs = collections.OrderedDict()    # key -> dict
    stats = collections.Counter()
    for g in GROUPS:
        lines, header = iter_cells(g)
        for i, line in enumerate(lines[:-1]):
            if i == 0: continue
            cells = SPLIT_RE.split(line)
            for j in range(len(cells)):
                if not cell_translatable(i, j, cells, header): continue
                qpre, qsuf, parts = parse_cell(cells[j])
                kind = col_kind(g, header, j)
                for p in parts:
                    if p[0] != 'txt' or not p[4]: continue
                    core = p[2]
                    d = segs.setdefault(core, {'group': g, 'kinds': [], 'refs': [], 'count': 0, 'unsure': set(), 'inner': False})
                    d['count'] += 1
                    ref = '%s:%d:%d' % (g, i + 1, j)
                    if ref not in d['refs']: d['refs'].append(ref)
                    if kind not in d['kinds']: d['kinds'].append(kind)
                    if (g, header[j]) in UNSURE: d['unsure'].add(UNSURE[(g, header[j])])
                    stats['seg_occurrences'] += 1
    # thu tu: theo nhom, trong nhom: name/ui truoc, roi desc, giu thu tu xuat hien
    order = {g: n for n, g in enumerate(GROUPS)}
    items = list(segs.items())
    def rank(it):
        k, d = it
        return (order[d['group']], 0 if ('name' in d['kinds'] or 'ui' in d['kinds']) else 1)
    items = sorted(items, key=rank)          # sorted on, on dinh -> giu thu tu xuat hien
    out = os.path.join(ROOT, 'text', 'segments.tsv')
    tot_chars = 0
    with open(out, 'w', encoding='utf-8', newline='') as f:
        f.write('\t'.join(['no', 'key', 'en', 'count', 'refs', 'font_size_min', 'max_width_hint', 'en_w24', 'group', 'note']) + '\n')
        for n, (k, d) in enumerate(items, 1):
            assert '\t' not in k and '\n' not in k
            note = '/'.join(d['kinds'])
            if COLOR_TAG.search(k) or ID_RE.search(k) or any(c in k for c in MARKUP_CHARS) or any(c in k for c in PLACEHOLDER_CHARS):
                note += ';c'
            if d['unsure']: note += ';?' + '|'.join(sorted(d['unsure']))
            w = '%.1f' % text_width(face, COLOR_TAG.sub('', ''.join(ch for ch in k if ch not in MARKUP_CHARS))) if face else ''
            f.write('\t'.join([str(n), k, k, str(d['count']), ' '.join(d['refs']), '', '', w, d['group'], note]) + '\n')
            tot_chars += len(k)
    print('segments.tsv: %d key duy nhat, %d lan xuat hien, %d ky tu EN' % (len(items), stats['seg_occurrences'], tot_chars))
    c = collections.Counter(d['group'] for _, d in items)
    for g in GROUPS: print('  %-9s %d' % (g, c[g]))

main()
