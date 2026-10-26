"""T3: parser Python cho các file text/dữ liệu đơn giản của P1, viết đúng theo code loader của game.
Mỗi parser trả về (list (offset_u2_của_chuỗi, chuỗi), số byte đã đọc). Dùng: python3 -I formats_p1.py <thư mục x/1>"""
import struct, sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import utfscan

class R:
    def __init__(s, b, p=0): s.b, s.p, s.strs = b, p, []
    def u1(s): v = s.b[s.p]; s.p += 1; return v
    def i2(s): v = struct.unpack_from('>h', s.b, s.p)[0]; s.p += 2; return v
    def u2(s): v = struct.unpack_from('>H', s.b, s.p)[0]; s.p += 2; return v
    def i4(s): v = struct.unpack_from('>i', s.b, s.p)[0]; s.p += 4; return v
    def skip(s, n): s.p += n; assert s.p <= len(s.b)
    def utf(s):
        o = s.p; n = s.u2(); raw = s.b[s.p:s.p + n]; assert len(raw) == n
        t = raw.decode('utf-8'); s.p += n; s.strs.append((o, t)); return t

def strlist(b):
    """t.dat / t2.dat / tips.dat: h.b.b(String): i2 n, n×UTF"""
    r = R(b); n = r.i2()
    for _ in range(n): r.utf()
    return r.strs, r.p

def mapdat(b):
    """map.dat (g/j.i): i2 n, n×(i2 id, UTF tên bản đồ)"""
    r = R(b); n = r.i2()
    for _ in range(n): r.i2(); r.utf()
    return r.strs, r.p

def cdat(b):
    """c.dat (d/a.b): u1 n; n×11 i2 (d.g.v.length=11); n×nhân vật g.a(): i2, UTF B, UTF U, UTF b, bool, i2, u1, u1"""
    r = R(b); n = r.u1(); r.skip(n * 11 * 2)
    for _ in range(n):
        r.i2(); r.utf(); r.utf(); r.utf(); r.skip(1); r.i2(); r.skip(2)
    return r.strs, r.p

def cf_head(r):  # c.f.b(): i2, UTF, UTF, i2
    r.i2(); r.utf(); r.utf(); r.i2()

def item15(b):
    """item15.dat (d/a.e -> d/a.c): i2 n; n×(u1 loại=14, c.f.b(), u1, u1, i2, i2, i2, u1)  [c.a]"""
    r = R(b); n = r.i2()
    for _ in range(n):
        t = r.u1(); assert t == 14, t
        cf_head(r); r.skip(2); r.skip(6); r.skip(1)
    return r.strs, r.p

def fbskill(b):
    """fb_skill.dat (d/a.f -> d/a.c): i2 n; n×(u1 loại=17, c.f.b(), i2, i2)  [b.a]"""
    r = R(b); n = r.i2()
    for _ in range(n):
        t = r.u1(); assert t == 17, t
        cf_head(r); r.skip(4)
    return r.strs, r.p

def monster(b):
    """monster.dat (d/a.g): i2 n; n×(i2 id, i4 off); i4 blobLen; blob. Bản ghi d/d.a() tại blob+off, đọc bằng parser dưới."""
    r = R(b); n = r.i2(); ent = [(r.i2(), r.i4()) for _ in range(n)]
    bl = r.i4(); base = r.p; assert base + bl == len(b)
    offs = sorted(set(o for _, o in ent)); ends = {}
    for i, o in enumerate(offs): ends[o] = offs[i + 1] if i + 1 < len(offs) else bl
    for _, o in ent:
        q = R(b, base + o)
        q.i2(); q.utf(); q.i2(); q.i2(); q.skip(1); q.i2(); q.i4(); q.skip(4); q.skip(8); q.i4(); q.i4()
        q.i2(); q.skip(1); q.i2(); q.skip(1); q.skip(1); q.skip(1)
        for _k in range(2):
            c = q.u1()
            for _j in range(c): q.i2(); q.skip(1)
        q.i2(); q.i2()
        assert q.p == base + ends[o], (o, q.p - base, ends[o])
        r.strs += q.strs
    return r.strs, len(b)

def pak(b, decode=True):
    """*.pak (e/a.e): i2 n; n×(UTF tên, i4 len); n×blob; mỗi byte blob XOR 1. Trả (tên+blob đã giải mã)."""
    r = R(b); n = r.i2(); ent = []
    for _ in range(n): nm = r.utf(); ent.append((nm, r.i4()))
    blobs = []
    for nm, L in ent:
        blobs.append((nm, bytes(x ^ 1 for x in b[r.p:r.p + L]))); r.skip(L)
    return blobs, r.p

SIMPLE = {'t.dat': strlist, 't2.dat': strlist, 'tips.dat': strlist, 'map.dat': mapdat, 'c.dat': cdat,
          'item15.dat': item15, 'fb_skill.dat': fbskill, 'monster.dat': monster}

def main(d):
    bad = 0
    for f, fn in SIMPLE.items():
        b = open(os.path.join(d, f), 'rb').read()
        strs, used = fn(b)
        cj = [(o, s) for o, s in strs if any(utfscan.is_cjkish(c) for c in s)]
        asc = [s for o, s in strs if not any(utfscan.is_cjkish(c) for c in s)]
        ps = set(s for _, s in cj); ss = set(s for _, s in utfscan.scan(b))
        ok = used == len(b) and ps == ss and (f == 'monster.dat' or sorted(cj) == sorted(utfscan.scan(b)))
        print('%-13s %s đọc %d/%d byte, chuỗi CJK parser=%d utfscan=%d%s, chuỗi không-CJK=%d %s' % (f, 'OK ' if ok else 'LỖI', used, len(b), len(ps), len(ss), '' if ps == ss else ' KHÁC', len(asc), sorted(set(asc))[:6]))
        bad += not ok
    for f in sorted(x for x in os.listdir(d) if x.endswith('.pak')):
        b = open(os.path.join(d, f), 'rb').read()
        bl, used = pak(b)
        cjk = sum(1 for _, x in bl if utfscan.scan(x))
        kinds = {}
        for nm, x in bl:
            k = 'png' if x[:4] == b'\x89PNG' else nm.rsplit('.', 1)[-1]
            kinds[k] = kinds.get(k, 0) + 1
        cjkname = sum(1 for nm, _ in bl if any(utfscan.is_cjkish(c) for c in nm))
        ok = used == len(b) and cjk == 0 and cjkname == 0
        print('%-13s %s đọc %d/%d byte, %d mục %s, blob có text CJK: %d, tên CJK: %d' % (f, 'OK ' if ok else 'LỖI', used, len(b), len(bl), kinds, cjk, cjkname))
        bad += not ok
    sys.exit(1 if bad else 0)

if __name__ == '__main__':
    main(sys.argv[1])
