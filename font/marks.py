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
# SAC (acute): net co thuon: dau tren to bo tron, duoi nhon dan.
import math


def _arc_pts(c, r, a0, a1):
    """Cung tron tam c, ban kinh r, goc a0..a1 (do), buoc <= 90: [on, off, on, ..., on] (quadratic)."""
    n = max(1, int(math.ceil(abs(a1 - a0) / 90.0 - 1e-9)))
    out = []
    for i in range(n):
        t0 = math.radians(a0 + (a1 - a0) * i / n)
        t1 = math.radians(a0 + (a1 - a0) * (i + 1) / n)
        tm = (t0 + t1) / 2
        k = r / math.cos((t1 - t0) / 2)
        if i == 0:
            out.append((c[0] + r * math.cos(t0), c[1] + r * math.sin(t0)))
        out.append((c[0] + k * math.cos(tm), c[1] + k * math.sin(tm), 0))
        out.append((c[0] + r * math.cos(t1), c[1] + r * math.sin(t1)))
    return out


def _rnd(pts):
    return [tuple(round(v) for v in p[:2]) + tuple(p[2:]) for p in pts]


def brush(t, c, r, tw=7, belly=8):
    """Net co thuon: duoi nhon t (nua be rong tw), dau tron tam c ban kinh r; belly = do phinh canh."""
    ux, uy = c[0] - t[0], c[1] - t[1]
    L = math.hypot(ux, uy)
    ux, uy = ux / L, uy / L
    nx, ny = -uy, ux
    ang = math.degrees(math.atan2(uy, ux))
    tl = (t[0] + nx * tw, t[1] + ny * tw)
    tr = (t[0] - nx * tw, t[1] - ny * tw)
    pr = (c[0] - nx * r, c[1] - ny * r)
    pl = (c[0] + nx * r, c[1] + ny * r)
    mr = ((tr[0] + pr[0]) / 2 - nx * belly, (tr[1] + pr[1]) / 2 - ny * belly, 0)
    ml = ((tl[0] + pl[0]) / 2 + nx * belly, (tl[1] + pl[1]) / 2 + ny * belly, 0)
    arc = _arc_pts(c, r, ang - 90, ang + 90)
    return _rnd([tr, mr] + arc + [ml, tl])


def _scale(c, sx, sy):
    return [(round(p[0] * sx), round(p[1] * sy)) + tuple(p[2:]) for p in c]


def _mirror(c):
    return [(-p[0],) + tuple(p[1:]) for p in c]


_AC = brush((-58, 4), (36, 132), 46)
ACUTE = {'R': [_AC], 'C': [_scale(_AC, .82, .82)], 'S': [_scale(_AC, .72, .72)]}

# HUYEN (grave): doi xung sac.
GRAVE = {k: [_mirror(c) for c in v] for k, v in ACUTE.items()}

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

def _round_cap(c):
    """Bo tron dau cat thang (diem dau <-> diem cuoi) thanh nua tron huong xuong."""
    A, B = c[0], c[-1]
    mx, my = (A[0] + B[0]) / 2, (A[1] + B[1]) / 2
    ex, ey = B[0] - A[0], B[1] - A[1]
    r = math.hypot(ex, ey) / 2
    nx, ny = ey, -ex
    if ny > 0:
        nx, ny = -nx, -ny
    k = math.hypot(nx, ny)
    nx, ny = nx / k * r * 0.85, ny / k * r * 0.85
    return _rnd(c + [(B[0] + nx, B[1] + ny, 0), (mx + nx, my + ny), (A[0] + nx, A[1] + ny, 0)])


for _k, _c in HOOK.items():
    HOOK[_k] = [_round_cap(_c[0])]

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
_HR = _rnd([(-72, -44), (20, -54, 0), (62, -28), (94, 6, 0), (98, 58)] + _arc_pts((74, 104), 24, 0, 180)
           + [(44, 60, 0), (14, 40), (-20, 30, 0), (-72, 28)])
HORN = {'R': [_HR], 'C': [_scale(_HR, .94, .94)]}
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


def _curve_pts(c, n=12):
    """Diem tren duong cong that (lay mau quadratic) cua contour c."""
    pts = [(p[0], p[1], len(p) < 3) for p in c]
    k = next(i for i, p in enumerate(pts) if p[2])
    pts = pts[k:] + pts[:k]
    out, cur, offs = [pts[0][:2]], pts[0][:2], []
    for p in pts[1:] + [pts[0]]:
        if not p[2]:
            offs.append(p[:2])
            continue
        q0 = cur
        for j, o in enumerate(offs):
            q2 = p[:2] if j == len(offs) - 1 else ((o[0] + offs[j + 1][0]) / 2, (o[1] + offs[j + 1][1]) / 2)
            for t in range(1, n + 1):
                u = t / n
                out.append(((1 - u) ** 2 * q0[0] + 2 * (1 - u) * u * o[0] + u * u * q2[0],
                            (1 - u) ** 2 * q0[1] + 2 * (1 - u) * u * o[1] + u * u * q2[1]))
            q0 = q2
        offs = []
        out.append(p[:2])
        cur = p[:2]
    return out


def bbox(name, variant):
    """Khung bao theo moi diem (ke ca off-curve): layout dung de xep dau chong (giu nguyen bo cuc T4)."""
    xs = [p[0] for c in contours(name, variant) for p in c]
    ys = [p[1] for c in contours(name, variant) for p in c]
    return min(xs), min(ys), max(xs), max(ys)


def true_bbox(name, variant):
    """Khung bao that cua duong cong (khong tinh diem dieu khien off-curve)."""
    pts = [q for c in contours(name, variant) for q in _curve_pts(c)]
    xs = [p[0] for p in pts]
    ys = [p[1] for p in pts]
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
