"""Bang bo cuc: 134 chu Viet (NFC) -> (glyph than, [(ten dau, bien the, dx, dy), ...]).

Dau duoc dat bang tinh tien (dx, dy) cua he toa do cuc bo trong marks.py (x=0 giua dau, y=0 day dau).
So lieu Jeju lay tu docs/jeju_metrics.json (tools/jeju_metrics.py). Quy doi luoi f18 18 hang -> don vi Jeju:
  chu thuong: x-height f18 = 6 hang (9-14) ~ 500 don vi -> ~83 don vi / hang, khe giua dau va than f18 = 1 hang -> G_LOWER
  chu hoa:    cao f18 = 10 hang (5-14) ~ 650 don vi -> ~65 don vi / hang, khe f18 = 1 hang -> G_UPPER
  dau chong f18: chu thuong tone cach mu/trang 1 hang (gap 1), chu hoa tone sat mu/trang (gap 0) -> GAP_STACK_*
Tam ngang: tam bbox than chu + lech quang hoc (f18: sac/huyen lech +0..0.5px phai, hoi -0.5px trai).
"""
import json
import os
import sys
import unicodedata

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
from extra import IDOTLESS, IDOTLESS_BBOX   # noqa: E402
M = json.load(open(os.path.join(HERE, '..', 'docs', 'jeju_metrics.json')))

# --- hang so quy doi (don vi font) --------------------------------------------------------------------------------
G_LOWER = 58          # khe tu dinh than chu thuong den day dau don
G_UPPER = 42          # khe tu dinh than chu hoa den day dau don
GAP_STACK_LOWER = 52  # khe giua mu/trang va dau thanh o chu thuong (f18: 1 hang trong)
GAP_STACK_UPPER = 0   # chu hoa: dau thanh gan sat mu/trang (f18: 0 hang)
DOT_GAP = 28          # khe giua day than (hoac baseline) va dinh cham nang
MIN_TOP_LOWER = 500   # dinh tham chieu toi thieu cua chu thuong (x-height)
OPT_DX = {'acute': 22, 'grave': 14, 'hook': -20, 'tilde': 0, 'circumflex': 0, 'breve': 0}   # lech quang hoc (f18)
TONES = ('acute', 'grave', 'hook', 'tilde')
SHAPES = ('circumflex', 'breve')
COMB = {0x300: 'grave', 0x301: 'acute', 0x303: 'tilde', 0x309: 'hook', 0x323: 'dotbelow',
        0x302: 'circumflex', 0x306: 'breve', 0x31B: 'horn'}


def _bb(ch):
    if ch == IDOTLESS:
        return IDOTLESS_BBOX
    return M[ch]['bbox']      # xmin, ymin, xmax, ymax


def body_of(ch):
    """Ky tu Jeju dung lam than."""
    d = unicodedata.normalize('NFD', ch)
    base = d[0]
    if ch in 'đĐ':
        return 'd' if ch == 'đ' else 'D'      # D3a: gach vector (extra.py) thay glyph Jeju lac phong cach
    if base == 'i':
        return IDOTLESS                        # D3a: i Jeju bo cham, khong dung ı
    return base


def place(ch):
    """Tra ve (than, [(mark, variant, dx, dy), ...]) cho ky tu ch."""
    d = unicodedata.normalize('NFD', ch)
    ms = [COMB[ord(c)] for c in d[1:]]
    body = body_of(ch)
    if ch in 'đĐ':
        return body, [('dbar' if ch == 'đ' else 'Dbar', 'R', 0, 0)]
    if not ms:
        return body, []
    upper = d[0].isupper()
    xmin, ymin, xmax, ymax = _bb(body)
    xc = (xmin + xmax) / 2.0
    top = ymax if upper else max(ymax, MIN_TOP_LOWER)
    out = []
    tone = next((m for m in ms if m in TONES), None)
    shape = next((m for m in ms if m in SHAPES), None)
    stacked = bool(tone and shape)
    var_single = 'C' if upper else 'R'
    var_stack = 'S' if upper else 'R'
    cur = top + (G_UPPER if upper else G_LOWER)       # day dau ke tiep
    if shape:
        v = var_stack if stacked else var_single
        from marks import bbox
        out.append((shape, v, xc + OPT_DX[shape], cur))
        cur += bbox(shape, v)[3] + (GAP_STACK_UPPER if upper else GAP_STACK_LOWER)
    if tone:
        v = var_stack if stacked else var_single
        # dau thanh tren dau mu/trang: giu tam chu, khong lech them (f18 dx = 0)
        out.append((tone, v, xc + (OPT_DX[tone] if not stacked else OPT_DX[tone] * 0.5), cur))
    if 'horn' in ms:
        ax, ay = M['horn_attach'][d[0]]
        out.append(('horn', 'C' if upper else 'R', ax, ay))
    if 'dotbelow' in ms:
        dxx = xc
        if d[0] == 'y':
            dxx = 440                       # canh duoi chu y (duoi chu y chiem x 114..336 duoi baseline)
        dyy = min(ymin, 0) - DOT_GAP - _dot_h()
        if d[0] == 'y':
            dyy = -172                      # y co duoi -143: cham nam canh duoi, van >= -200
        out.append(('dotbelow', 'R', dxx, dyy))
    return body, out


def _dot_h():
    from marks import bbox
    b = bbox('dotbelow', 'R')
    return b[3] - b[1]


def all_chars():
    import sys
    sys.path.insert(0, HERE)
    cs = []
    for c in 'đĐáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ':
        cs.append(c)
    for c in 'ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ':
        cs.append(c)
    return cs


def table():
    import sys
    sys.path.insert(0, HERE)
    return {c: place(c) for c in all_chars()}


if __name__ == '__main__':
    import sys
    sys.path.insert(0, HERE)
    t = table()
    print(len(t), 'ky tu')
    for c, (b, ms) in t.items():
        print(c, b, [(m, v, round(x), round(y)) for m, v, x, y in ms])
