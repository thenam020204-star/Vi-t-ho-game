#!/usr/bin/env python3
"""Trich text can dich cua ca 3 ban -> dich/text_can_dich.tsv (cot: id file khoa goc dich 'ghi chu' 'gioi han').
Dung: extract.py X_DIR_240 X_DIR_320 X_DIR_360 OUT_TSV   (X_DIR = jar da giai nen)
  file = <ban>:data/<ten>.data | <ban>:class:<Lop>
  khoa = D-file: <hang>.<cot>   E-file: <so thu tu>   class: u<chi so Utf8>
Repack anh xa theo NOI DUNG goc (goc -> dich), nen 3 ban dung chung mot ban dich va chuoi trung nhau dich dong nhat."""
import sys, os, glob, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from hanlib import HAN, class_han_utf8, write_tsv
from datafmt import D_FILES, read_d, read_e

D_NOTE = {('iteminfo', 0): 'ten vat pham', ('iteminfo', 1): 'mo ta vat pham',
          ('skillinfo', 0): 'ten ky nang', ('skillinfo', 1): 'mo ta ky nang',
          ('missioninfo0', 0): 'ten nhiem vu chinh', ('missioninfo0', 1): 'mo ta nhiem vu chinh',
          ('missioninfo1', 0): 'ten nhiem vu phu', ('missioninfo1', 1): 'mo ta nhiem vu phu',
          ('mapinfo', 0): 'ten khu vuc', ('mapinfo', 1): 'mo ta khu vuc',
          ('universeinfo', 0): 'ten hanh tinh', ('universeinfo', 1): 'mo ta hanh tinh'}
# Ten nhan vat bi v.a(String,int) so equals() de chon chan dung: phai dich DONG NHAT o moi noi (thoai "Ten：...", class v)
KEY_NAMES = ['诺兰', '美达', '希波吕特', '小尼布', '施瓦布公爵', '尼布一世', '尼布', '菲特烈', '风语者伊萨']
EXCLUDE = {}   # (lop, text) -> ly do


def note_for(s):
    n = []
    if '：' in s and s.index('：') <= 8:
        n.append('THOAI "Ten：noi dung" - GIU dau ： (U+FF1A) ngay sau ten nguoi noi')
    if '$' in s:
        n.append('$<so> = ma mau, giu nguyen')
    if '|' in s:
        n.append('| = xuong dong, giu so luong')
    if s in KEY_NAMES:
        n.append('KHOA ten (equals chon chan dung) - dich dong nhat moi noi')
    return n


def main():
    xs = sys.argv[1:4]; outp = sys.argv[4]
    vers = ['240', '320', '360']
    rows = []; st = collections.Counter(); n = 0
    for v, X in zip(vers, xs):
        for f in D_FILES:
            r, c, cells = read_d(open(os.path.join(X, 'data', f + '.data'), 'rb').read())
            for i, s in enumerate(cells):
                if not HAN.search(s):
                    continue
                n += 1; ro, co = divmod(i, c)
                rows.append(['r%05d' % n, '%s:data/%s.data' % (v, f), '%d.%d' % (ro, co), s, '',
                             '; '.join([D_NOTE.get((f, co), '')] + note_for(s)), '']); st['d'] += 1
        for p in sorted(glob.glob(os.path.join(X, 'data', 'e*.data'))):
            f = os.path.basename(p)
            if f == 'eff.data':
                continue
            ss, tail, q = read_e(open(p, 'rb').read())
            for i, s in enumerate(ss):
                if not HAN.search(s):
                    continue
                n += 1
                rows.append(['r%05d' % n, '%s:data/%s' % (v, f), str(i), s, '',
                             '; '.join(['su kien/cot truyen %s' % f] + note_for(s)), '']); st['e'] += 1
        for p in sorted(glob.glob(os.path.join(X, 'src', '*.class'))):
            cls = 'src/' + os.path.basename(p)[:-6]; seen = set()
            for idx, s in class_han_utf8(open(p, 'rb').read()):
                if s in seen or (cls, s) in EXCLUDE:
                    continue
                seen.add(s); n += 1
                rows.append(['r%05d' % n, '%s:class:%s' % (v, cls), 'u%d' % idx, s, '',
                             '; '.join(['chuoi trong code ' + cls] + note_for(s)), '']); st['class'] += 1
    write_tsv(outp, rows)
    print('rows', len(rows), dict(st), 'unique goc', len({r[3] for r in rows}))


if __name__ == '__main__':
    main()
