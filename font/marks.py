"""Contour dau tieng Viet ve tay (vector), phong cach net co dam cua JejuHallasan.

Don vi font (UPM 1000, nhu Jeju). Moi dau = list contour; moi contour = list diem
    (x, y)      diem tren duong cong (on-curve)
    (x, y, 0)   diem dieu khien quadratic (off-curve)
Hai off-curve lien tiep ngam hieu co on-curve o giua (quy uoc TrueType); khong dung o day, moi cung deu co on-curve ro rang.

He toa do cuc bo cua tung dau: x = 0 la TAM ngang cua dau, y = 0 la DAY (diem thap nhat) cua dau.
layout.py quyet dinh tinh tien (dx, dy) len than chu.

Ba bien the kich co (theo cach f18 co bo cuc rieng cho chu thuong / chu hoa / dau chong):
    R  chu thuong (va dau chong tren chu thuong)
    C  chu hoa, dau don (ep sat, thap hon)
    S  chu hoa, dau chong (mu/trang + dau thanh): thap nhat de khong vuot ~950 don vi

Do day net: ~80-95 don vi (net Jeju: i 148, l 136, ı 100, net manh cua o/e ~ 85-140).
Hinh dang theo f18 (docs/f18_marks.md): sac `.#/#.`, huyen `#./.#`, hoi `##./..#/.#.` (mon moc cuon, duoi huong xuong),
nga `.#.#/#.#.` (song, dau phai cao), mu `.##./#..#`, trang `#..#/.##.`, nang = 1 cham, moc = nhat chong len goc phai-tren.
Dau cam ngoai khung: dau sac nghieng '/', huyen '\\' ; dau sac/huyen o f18 rong 2px = ~1/3 than chu, mu/trang/nga rong 4px = ~2/3 than.
"""

# ----------------------------------------------------------------------------------------------------------------
# SAC (acute): net nghieng /, day nho, dau to, dau cat bang nhu net co Jeju.
ACUTE = {
    'R': [[(-62, 12), (-34, 100, 0), (-4, 166), (2, 178, 0), (16, 176), (60, 176), (82, 176, 0), (82, 158), (64, 86, 0),
         (22, 6), (12, -3, 0), (-4, 0), (-50, 2), (-68, 4, 0)]],
    'C': [[(-51, 10), (-28, 82, 0), (-3, 136), (2, 146, 0), (13, 144), (49, 144), (67, 144, 0), (67, 130), (52, 71, 0),
         (18, 5), (10, -2, 0), (-3, 0), (-41, 2), (-56, 3, 0)]],
    'S': [[(-45, 9), (-24, 72, 0), (-3, 120), (1, 128, 0), (12, 127), (43, 127), (59, 127, 0), (59, 114), (46, 62, 0),
         (16, 4), (9, -2, 0), (-3, 0), (-36, 1), (-49, 3, 0)]],
}

# HUYEN (grave): doi xung sac, hai dau lech nhe.
GRAVE = {
    'R': [[(64, 12), (36, 100, 0), (6, 166), (0, 178, 0), (-14, 176), (-62, 176), (-86, 176, 0), (-84, 156), (-66, 86,
         0), (-24, 6), (-14, -3, 0), (2, 0), (52, 2), (70, 4, 0)]],
    'C': [[(52, 10), (30, 82, 0), (5, 136), (0, 146, 0), (-11, 144), (-51, 144), (-71, 144, 0), (-69, 128), (-54, 71, 0),
         (-20, 5), (-11, -2, 0), (2, 0), (43, 2), (57, 3, 0)]],
    'S': [[(46, 9), (26, 72, 0), (4, 120), (0, 128, 0), (-10, 127), (-45, 127), (-62, 127, 0), (-60, 112), (-48, 62, 0),
         (-17, 4), (-10, -2, 0), (1, 0), (37, 1), (50, 3, 0)]],
}

# HOI (hook): cung cuon len roi quay xuong, duoi huong xuong (f18 `##. / ..# / .#.`).
HOOK = {
    'R': [[(-74, 140), (-78, 200, 0), (-30, 234), (14, 244, 0), (66, 220), (96, 188, 0), (90, 140), (84, 110, 0), (46,
         92), (30, 82, 0), (34, 44), (34, 18, 0), (16, 1), (4, -4, 0), (-12, 6), (-26, 26, 0), (-26, 60), (-26,
         88, 0), (8, 102), (30, 124, 0), (30, 152), (28, 174, 0), (8, 178), (-8, 174, 0), (-14, 160), (-14,
         148)]],
    'C': [[(-59, 112), (-62, 160, 0), (-24, 187), (11, 195, 0), (53, 176), (77, 150, 0), (72, 112), (67, 88, 0), (37,
         74), (24, 66, 0), (27, 35), (27, 14, 0), (13, 1), (3, -3, 0), (-10, 5), (-21, 21, 0), (-21, 48), (-21,
         70, 0), (6, 82), (24, 99, 0), (24, 122), (22, 139, 0), (6, 142), (-6, 139, 0), (-11, 128), (-11, 118)]],
    'S': [[(-52, 84), (-55, 120, 0), (-21, 140), (10, 146, 0), (46, 132), (67, 113, 0), (63, 84), (59, 66, 0), (32, 55),
         (21, 49, 0), (24, 26), (24, 11, 0), (11, 1), (3, -3, 0), (-8, 4), (-18, 16, 0), (-18, 36), (-18, 60,
         0), (6, 61), (21, 74, 0), (21, 91), (20, 104, 0), (6, 106), (-6, 104, 0), (-10, 96), (-10, 89)]],
}

# NGA (tilde): song 2 nhip, dau trai thap, dau phai cao, net deu (f18 `.#.# / #.#.`).
TILDE = {
    'R': [[(-140, 56), (-124, 110, 0), (-88, 122), (-56, 134, 0), (-30, 112), (-4, 96, 0), (14, 80), (40, 62, 0), (64,
         92), (100, 112, 0), (126, 138), (134, 110, 0), (132, 80), (100, 50, 0), (66, 32), (40, 6, 0), (14, 22),
         (-6, 40, 0), (-30, 54), (-60, 70, 0), (-88, 64), (-124, 40, 0), (-142, 4)]],
    'C': [[(-115, 46), (-102, 90, 0), (-72, 100), (-46, 110, 0), (-25, 92), (-3, 79, 0), (11, 66), (33, 51, 0), (52, 75),
         (82, 92, 0), (103, 113), (110, 90, 0), (108, 66), (82, 41, 0), (54, 26), (33, 5, 0), (11, 18), (-5, 33,
         0), (-25, 44), (-49, 57, 0), (-72, 52), (-102, 33, 0), (-116, 3)]],
    'S': [[(-104, 41), (-92, 81, 0), (-65, 90), (-41, 99, 0), (-22, 83), (-3, 71, 0), (10, 59), (30, 46, 0), (47, 68),
         (74, 83, 0), (93, 102), (99, 81, 0), (98, 59), (74, 37, 0), (49, 24), (30, 4, 0), (10, 16), (-4, 30,
         0), (-22, 40), (-44, 52, 0), (-65, 47), (-92, 30, 0), (-105, 3)]],
}

# NANG (dotbelow): cham tron hoi deo, duong kinh ~115
DOTBELOW = {
    'R': [[(-56, 62), (-60, 96, 0), (-32, 116), (2, 118, 0), (42, 104), (62, 80, 0), (58, 36),
           (48, 10, 0), (12, 0), (-24, 2, 0), (-48, 22)]],
}
DOTBELOW['C'] = DOTBELOW['R']
DOTBELOW['S'] = DOTBELOW['R']

# MU (circumflex): chevron ^ hai chan cat bang, dinh bo (f18 `.##. / #..#`).
CIRCUMFLEX = {
    'R': [[(-130, 6), (-84, 56, 0), (-20, 122), (0, 132, 0), (20, 122), (84, 56, 0), (130, 6), (62, 0), (40, 32, 0), (6,
         76), (-6, 76), (-40, 32, 0), (-62, 0)]],
    'C': [[(-120, 6), (-77, 52, 0), (-18, 112), (0, 121, 0), (18, 112), (77, 52, 0), (120, 6), (57, 0), (37, 29, 0), (6,
         70), (-6, 70), (-37, 29, 0), (-57, 0)]],
    'S': [[(-112, 5), (-72, 46, 0), (-17, 100), (0, 108, 0), (17, 100), (72, 46, 0), (112, 5), (53, 0), (34, 26, 0), (5,
         62), (-5, 62), (-34, 26, 0), (-53, 0)]],
}

# TRANG (breve): chen ngua, hai dau cat bang phia tren (f18 `#..# / .##.`).
BREVE = {
    'R': [[(-125, 102), (-122, 20, 0), (-52, 1), (52, 1), (122, 20, 0), (125, 102), (58, 102), (60, 66, 0), (30, 56),
         (-30, 56), (-60, 66, 0), (-58, 102)]],
    'C': [[(-115, 94), (-112, 18, 0), (-48, 1), (48, 1), (112, 18, 0), (115, 94), (53, 94), (55, 61, 0), (28, 52), (-28,
         52), (-55, 61, 0), (-53, 94)]],
    'S': [[(-108, 84), (-105, 16, 0), (-45, 1), (45, 1), (105, 16, 0), (108, 84), (50, 84), (52, 54, 0), (26, 46), (-26,
         46), (-52, 54, 0), (-50, 84)]],
}

# MOC (horn): goc (0,0) = diem tren mep phai-tren than o/u; phan am nam trong than chu de dinh lien.
HORN = {
    'R': [[(-50, -42), (20, -46, 0), (54, -22), (84, 10, 0), (90, 58), (92, 100, 0), (70, 122), (52, 108, 0), (38, 70),
         (22, 40, 0), (-50, 36)]],
    'C': [[(-46, -39), (19, -43, 0), (50, -20), (78, 9, 0), (84, 54), (86, 93, 0), (65, 113), (48, 100, 0), (35, 65),
         (20, 37, 0), (-46, 33)]],
}
HORN['S'] = HORN['C']

MARKS = {
    'acute': ACUTE, 'grave': GRAVE, 'hook': HOOK, 'tilde': TILDE, 'dotbelow': DOTBELOW,
    'circumflex': CIRCUMFLEX, 'breve': BREVE, 'horn': HORN,
}


def contours(name, variant):
    """Contour (da chuan hoa chieu quay TrueType: ngoai = nguoc kim dong ho nghia la y-up clockwise) cua dau/bien the."""
    v = MARKS[name]
    return [orient(c) for c in v.get(variant) or v['R']]


def orient(c):
    """TrueType: contour ngoai quay THUAN chieu kim dong ho (toa do y huong len)."""
    a = 0
    for i, p in enumerate(c):
        q = c[(i + 1) % len(c)]
        a += p[0] * q[1] - q[0] * p[1]
    return c if a < 0 else c[::-1]


def bbox(name, variant):
    xs = [p[0] for c in contours(name, variant) for p in c]
    ys = [p[1] for c in contours(name, variant) for p in c]
    return min(xs), min(ys), max(xs), max(ys)


def draw(pen, name, variant, dx=0, dy=0):
    """Ve len mot fontTools pen (TrueType quadratic): dung qCurveTo/lineTo, toa do + (dx, dy)."""
    for c in contours(name, variant):
        pts = [(p[0] + dx, p[1] + dy, len(p) < 3) for p in c]
        # xoay de bat dau o diem on-curve
        k = next(i for i, p in enumerate(pts) if p[2])
        pts = pts[k:] + pts[:k]
        pen.moveTo(pts[0][:2])
        i = 1
        n = len(pts)
        offs = []
        while i < n:
            if pts[i][2]:
                if offs:
                    pen.qCurveTo(*offs, pts[i][:2]); offs = []
                else:
                    pen.lineTo(pts[i][:2])
            else:
                offs.append(pts[i][:2])
            i += 1
        if offs:
            pen.qCurveTo(*offs, pts[0][:2])
        pen.closePath()
