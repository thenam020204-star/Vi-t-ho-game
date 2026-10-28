# -*- coding: utf-8 -*-
"""Trích toàn bộ text hiển thị của MHTQ K790 ra TSV để dịch.

Chạy:  python3 -I mhtq/scripts/text_extract.py [--src work/k790_raw] [--out work/text_can_dich.tsv]
        [--merge TSV_CU]   (giữ lại cột dịch đã có theo id khi chạy lại)

Cột:  id  file  offset/khoá  gốc  dịch  ghi_chú  giới_hạn
  - gốc/dịch: escape `\\` `\\t` `\\n` `\\r`. Mã điều khiển (`|` xuống dòng, `#RRGGBB`/`##` màu) giữ nguyên
    trong chuỗi và liệt kê ở ghi_chú sau "ĐK:". Dịch trống = chưa dịch (đóng gói sẽ giữ bản gốc).
  - ghi_chú: loại (thoại/dẫn truyện/tên item/mô tả/UI...), người nói, vị trí bytecode.
    Dòng KHOÁ = tên khoá chân dung: phải dịch CÙNG một chuỗi ở mọi dòng có cờ KHOÁ.
  - giới_hạn: xem docstring của textmodel.py (px của màn 240x320; ~ = ước lượng).
Loại bỏ (không đưa vào TSV): string.dat, layer/scene/sprite/sound, chuỗi bản dựng, mảng giữ chỗ,
m.b/m.a (chỉ dùng .length), 4 mảng bị loader bỏ trong mission.dat, g (debug), f "我是谁".
"""
import argparse
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import textmodel as T        # noqa: E402

COLS = ['id', 'file', 'offset/khoá', 'gốc', 'dịch', 'ghi_chú', 'giới_hạn']


def read_tsv(path):
    rows = []
    with open(path, encoding='utf-8') as f:
        first = True
        for ln in f:
            ln = ln.rstrip('\n')
            if first:
                first = False
                if ln.startswith('id\t'):
                    continue
            if not ln.strip():
                continue
            c = ln.split('\t')
            if len(c) < 7:
                c += [''] * (7 - len(c))
            rows.append(c[:7])
    return rows


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--src', default=os.path.join(L.ROOT, 'work', 'k790_raw'))
    ap.add_argument('--out', default=os.path.join(L.ROOT, 'work', 'text_can_dich.tsv'))
    ap.add_argument('--merge', help='TSV cũ: giữ cột dịch theo id (nếu cột gốc không đổi)')
    a = ap.parse_args(argv)
    docs, slots = T.collect(a.src)
    old = {}
    if a.merge and os.path.exists(a.merge):
        for r in read_tsv(a.merge):
            old[r[0]] = r
    n_kept = 0
    with open(a.out, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\t'.join(COLS) + '\n')
        for s in slots:
            tr = ''
            o = old.get(s.id)
            if o and L.unesc(o[3]) == s.orig and o[4]:
                tr = o[4]
                n_kept += 1
            f.write('\t'.join([s.id, s.file, s.loc, L.esc(s.orig), tr, L.esc(s.note), s.limit]) + '\n')
    byfile = {}
    for s in slots:
        byfile[s.file] = byfile.get(s.file, 0) + 1
    print('đã ghi %d dòng -> %s (giữ %d bản dịch cũ)' % (len(slots), a.out, n_kept))
    for k in sorted(byfile):
        print('  %-14s %d' % (k, byfile[k]))


if __name__ == '__main__':
    main()
