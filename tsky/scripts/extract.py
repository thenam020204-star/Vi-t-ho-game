#!/usr/bin/env python3
"""Trich text can dich -> dich/text_can_dich.tsv.
Dung: extract.py [DIR_JAR_GIAI_NEN=work/x] [OUT_TSV=dich/text_can_dich.tsv]
Cot: id file khoa goc dich 'ghi chu' 'gioi han'.
  file = pak/<ten>.p | t/itemN.t | class:<Lop>
  khoa = pak: <ten blob>/<lenh>.<truong>   t: <hang>.<cot>   class: u<so thu tu Utf8 trong lop>
Chuoi class dinh danh bang NOI DUNG goc (repack thay moi Utf8 == goc), nen ben vung neu ASM doi chi so constant pool."""
import sys, os, glob, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from hanlib import *

CMD_NOTE = {'16': 'thoai', '54': 'thoai huong dan', '39': 'thong bao giua man', '26': 'lua chon (chi so, khong so chuoi)',
            '49': 'CG ke chuyen (@ = xuong dong, giu @)', '51': 'chan phim: cau hien khi bam sai phim', '60': 'khong hien thi'}
LIMIT = {('26', None): 24}
ITEM_COLS = {  # (file, col) -> (mo ta, gioi han)
    ('item3', 1): ('ten chieu', 14), ('item3', 2): ('mo ta ky nang', ''),
    ('item3', 4): ('ten trang thai - KHOA DONG NHAT voi 5 hang class Main/j (麻痹 禁锢 冰冻 燃烧 石化), script.md 5.1', 10),
    ('item4', 0): ('ten tien sung (noi vao thong bao)', 14), ('item4', 2): ('mo ta tien sung (cuon, dai nhat)', ''),
    ('item5', 0): ('ten vat pham (noi vao thong bao, hien "ten   xN" ~100px)', 16), ('item5', 1): ('mo ta vat pham', ''),
    ('item6', 0): ('ten nhiem vu chinh', ''), ('item6', 1): ('mo ta nhiem vu chinh', ''),
    ('item7', 0): ('ten nhiem vu phu', ''), ('item7', 1): ('mo ta nhiem vu phu', ''),
}
STATUS = {'麻痹', '禁锢', '冰冻', '燃烧', '石化'}
# chuoi class KHONG dua vao TSV: (lop, text) hoac (lop, '*')
EXCLUDE = {
    ('Main/GameMidlet', '*'): 'Form/Alert debug (GameMidlet.i==false), chet',
    ('Main/a', '*'): '"该游戏不支持竖屏" chi khi p==2 (ngoai pham vi)',
    ('Main/f', '*'): 'man khoi dong: T5 viet lai/bo (hoi nhac, "她" do chieu rong)',
    ('rf/b', '*'): 'log loi + "她" do chieu rong, khong hien thi',
    ('rf/e', '*'): 'log debug',
    ('Main/e', '她'): 'chi do stringWidth, ket qua khong dung',
    ('Main/k', '额'): 'k.w = stringWidth, VH xu ly (render.md muc 6)',
    ('Main/j', '空'): 'QD 7b: GIU NGUYEN trong class/RMS; VH anh xa khi ve "空"->"Trong"',
}


def class_note(cls, s):
    n = []
    if cls == 'Main/l' and len(s) <= 4:
        n.append('TEN TOKEN (j4#..k0#) dung o moi thoai - dong nhat toan game')
    elif cls == 'Main/k':
        n.append('meo man loading (them "^" khi hien); chi hien thi')
    elif cls == 'Main/e':
        n.append('nhan UI (一: = o luu 1, 二:, 三:; "x:" -> "1:")' if s.endswith(':') else 'ten nhan thuong (khong so sanh)')
    elif cls == 'rf/g':
        n.append('thong bao khi di chuyen bi chan')
    else:
        n.append('hien thi')
    if cls == 'Main/j':
        if s in STATUS:
            n.append('KHOA: dong nhat voi item3.t cot 4 (indexOf) - dich CUNG gia tri')
        if s == '没有':
            n.append('KHOA: hang indexOf trong j.java:3085,3092 - dich = cum dau cua 3 chuoi "没有物品/法宝/物品可卖" (vd "Khong co")')
        if s.startswith('没有物品') or s.startswith('没有法宝'):
            n.append('KHOA: phai BAT DAU bang cum dich cua hang "没有" ("Khong co")')
        if '状态,' in s:
            n.append('tien to trang thai: dung cung tu voi item3 cot 4')
    if '^' in s:
        n.append('co ^ = xuong dong, giu so luong ^')
    return '; '.join(n)


def main():
    root = sys.argv[1] if len(sys.argv) > 1 else 'work/x'
    outp = sys.argv[2] if len(sys.argv) > 2 else 'dich/text_can_dich.tsv'
    rows = []; stat = collections.Counter()
    # ---- pak
    n = 0
    for f in sorted(glob.glob(root + '/pak/*.p')):
        head, blobs = read_pak(open(f, 'rb').read())
        for name, blob in blobs:
            cmds = split_script(blob.decode('utf-8'))
            assert join_script(cmds) == blob.decode('utf-8')
            for ci, fl in enumerate(cmds):
                for fi in range(1, len(fl)):
                    x = fl[fi]
                    if not HAN.search(x) or x.strip('"') == '主角':
                        if x.strip('"') == '主角':
                            stat['skip_主角'] += 1
                        continue
                    n += 1; code = fl[0]
                    note = ['cmd%s %s' % (code, CMD_NOTE.get(code, ''))]
                    if code == '16':
                        mat = fl[-2] if len(fl) >= 3 else ''
                        note.append('mat=' + mat)
                    toks = sorted(set(TOK.findall(x)))
                    if toks:
                        note.append('TOKEN ' + ','.join(toks) + ' (giu nguyen)')
                    if not re_han_letters(x):
                        note.append('CHI-DAU-CAU (khong co chu Han; chi dau cau Han)')
                    lim = LIMIT.get((code, None), '') if code == '26' else ''
                    rows.append(['p%04d' % n, 'pak/' + os.path.basename(f), '%s/%d.%d' % (name.decode('utf-8'), ci, fi), x, '', '; '.join(note), lim])
                    stat['pak'] += 1
    # ---- item*.t
    n = 0
    for k in range(3, 8):
        f = root + '/t/item%d.t' % k
        d = open(f, 'rb').read()
        head, r, c = read_t(d)
        cells = read_t_cells(d, r, c)
        for i, x in enumerate(cells):
            if not HAN.search(x):
                continue
            n += 1; ro, co = divmod(i, c)
            desc, lim = ITEM_COLS.get(('item%d' % k, co), ('', ''))
            rows.append(['t%04d' % n, 't/item%d.t' % k, '%d.%d' % (ro, co), x, '', 'item%d hang %d cot %d %s' % (k, ro, co, desc), lim])
            stat['item'] += 1
    # ---- class
    n = 0; excl = collections.Counter()
    for f in sorted(glob.glob(root + '/**/*.class', recursive=True)):
        cls = os.path.relpath(f, root)[:-6]
        seen = set()
        for idx, s in class_han_utf8(open(f, 'rb').read()):
            if s in seen:
                continue
            seen.add(s)
            if (cls, '*') in EXCLUDE or (cls, s) in EXCLUDE:
                excl[(cls, EXCLUDE.get((cls, s)) or EXCLUDE[(cls, '*')])] += 1
                continue
            n += 1
            rows.append(['c%04d' % n, 'class:' + cls, 'u%d' % idx, s, '', class_note(cls, s), ''])
            stat['class'] += 1
    write_tsv(outp, rows)
    print('rows', len(rows), dict(stat))
    for (cls, why), v in sorted(excl.items()):
        print('  loai %-16s %2d  %s' % (cls, v, why))


def re_han_letters(x):
    import re
    return re.search(r'[一-鿿]', x) is not None


if __name__ == '__main__':
    main()
