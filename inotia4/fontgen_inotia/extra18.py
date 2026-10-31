#!/usr/bin/env python3
"""Sinh f18vn.bin = f18.bin (NAM_P, giu nguyen 227 glyph) + glyph ve tay o luoi 18px theo net f18.

Quy uoc net f18: net 1px, chu thuong cao x hang 9..14, chu hoa/so hang 5..14, day chu = hang 14,
phan duoi 15..17, cot cuoi de trong lam khoang cach (rong 7).

    python3 extra18.py <f18.bin> <out f18vn.bin> [--show]
"""
import os, sys
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import f18io

E = '.......'


def g(rows, top=0, h=18, w=7):
    out = [('.' * w)] * h
    for i, r in enumerate(rows):
        assert len(r) == w, r
        out[top + i] = r
    return out


EXTRA = {
    # gach duoi: hang 16 (vung phan duoi), dai nhu '-' + 1
    '_': g(['######.'], top=16),
    # bang: hai vach dai nhu '-' (5 cot) o hang 10 va 12, quanh hang 11 cua '-'
    '=': g(['#####..', '.......', '#####..'], top=10),
    # nga: song o giua x-height
    '~': g(['.##..#.', '#..##..'], top=10),
    # nhay don cong phai: cung hinh voi dau ' cua f18
    '’': g(['..#....', '..#....', '.#.....'], top=5),
    # nhay don cong trai: doi xung
    '‘': g(['..#....', '..#....', '...#...'], top=5),
    # sao dac: cao 10 hang nhu chu hoa (5..14), rong 7 + 1 cot cach
    '★': g(['...#....',
                 '...#....',
                 '..###...',
                 '#######.',
                 '.#####..',
                 '..###...',
                 '.#####..',
                 '.##.##..',
                 '##...##.',
                 '#.....#.'], top=5, w=8),
}


def main(a):
    cs, h, gl = f18io.load(a[0])
    assert h == 18
    for c, rows in EXTRA.items():
        if c in gl:
            raise SystemExit('glyph %r da co trong f18, khong ghi de' % c)
        gl[c] = rows
        cs += c
    f18io.save(a[1], cs, h, gl)
    if '--show' in a:
        f18io.show(gl, "x" + ''.join(EXTRA) + "-'H")
    print('f18vn: %d ky tu (%d moi)' % (len(cs), len(EXTRA)))


if __name__ == '__main__':
    main(sys.argv[1:])
