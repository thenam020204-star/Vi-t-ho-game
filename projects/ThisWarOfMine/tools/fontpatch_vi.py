"""Bổ sung glyph tiếng Việt cho font bitmap This War of Mine bằng cách ghép glyph có sẵn
(chữ gốc + dấu trích từ à á â ã ğ / dấu đứng riêng ` ´ ^ ~ / ? / . / ’), giữ đúng nét font gốc.
Glyph cũ giữ nguyên vị trí trong atlas; glyph mới xếp vào chỗ trống, thiếu chỗ thì nới trang (POT)."""
import sys, os, numpy as np
from PIL import Image
sys.path.insert(0, os.path.dirname(__file__)); import fontfmt

W = fontfmt.W
TONES = {'': None, '̀': 'grave', '́': 'acute', '̉': 'hook', '̃': 'tilde', '̣': 'dot'}
VI_LOWER = 'aăâeêioôơuưy'
TONE_ORDER = ['̀', '́', '̉', '̃', '̣']
import unicodedata as ud


def vi_targets():
    out = []
    for v in VI_LOWER + VI_LOWER.upper():
        for t in [''] + TONE_ORDER:
            out.append(ud.normalize('NFC', v + t))
    return out + ['đ', 'Đ']


class Img:
    """Ảnh mực đã cắt sát: a[h,w,bpp]; left = toạ độ x theo bút; top = toạ độ y tính từ đỉnh dòng (xuống dưới)."""
    def __init__(s, a, left, top):
        s.a, s.left, s.top = a, left, top
    h = property(lambda s: s.a.shape[0]); w = property(lambda s: s.a.shape[1])
    right = property(lambda s: s.left + s.w); bottom = property(lambda s: s.top + s.h)
    cx = property(lambda s: s.left + s.w / 2.0)

    def trim(s):
        m = s.a.max(axis=2) > 0
        if not m.any(): return None
        ys, xs = np.where(m)
        return Img(s.a[ys.min():ys.max() + 1, xs.min():xs.max() + 1].copy(), s.left + xs.min(), s.top + ys.min())

    def rows(s, y0, y1):  # giữ các hàng có toạ độ dòng trong [y0,y1)
        a = s.a.copy(); ys = np.arange(s.top, s.bottom)
        a[(ys < y0) | (ys >= y1)] = 0
        return Img(a, s.left, s.top).trim()


def union(parts):
    l = min(p.left for p in parts); t = min(p.top for p in parts)
    r = max(p.right for p in parts); b = max(p.bottom for p in parts)
    a = np.zeros((b - t, r - l, parts[0].a.shape[2]), np.uint8)
    for p in parts:
        sl = a[p.top - t:p.bottom - t, p.left - l:p.right - l]
        np.maximum(sl, p.a, out=sl)
    return Img(a, l, t)


def scale(img, h=None, w=None, flipv=False, fliph=False):
    a = img.a
    if flipv: a = a[::-1]
    if fliph: a = a[:, ::-1]
    if h is None and w is None: return Img(a.copy(), img.left, img.top)
    if h is None: h = max(1, round(a.shape[0] * w / a.shape[1]))
    if w is None: w = max(1, round(a.shape[1] * h / a.shape[0]))
    ch = [np.asarray(Image.fromarray(a[:, :, c]).resize((w, h), Image.LANCZOS)) for c in range(a.shape[2])]
    out = Img(np.stack(ch, axis=2), img.left, img.top).trim()
    return out


def components(img):
    m = img.a.max(axis=2) > 0; lab = np.zeros(m.shape, int); n = 0
    for y, x in zip(*np.where(m)):
        if lab[y, x]: continue
        n += 1; st = [(y, x)]; lab[y, x] = n
        while st:
            cy, cx = st.pop()
            for dy in (-1, 0, 1):
                for dx in (-1, 0, 1):
                    ny, nx = cy + dy, cx + dx
                    if 0 <= ny < m.shape[0] and 0 <= nx < m.shape[1] and m[ny, nx] and not lab[ny, nx]:
                        lab[ny, nx] = n; st.append((ny, nx))
    return lab, n


class Font:
    def __init__(s, data):
        s.f = fontfmt.parse(data); s.bpp = s.f['bpp']
        s.pages = [np.frombuffer(p, np.uint8).reshape(h, W, s.bpp).copy() for p, h in zip(s.f['pages'], s.f['H'])]
        s.G = {g[0]: g for g in s.f['G']}
        s.new = {}  # cp -> (Img, adv)

    def has(s, c): return ord(c) in s.G or ord(c) in s.new

    def get(s, c):
        cp = ord(c)
        if cp in s.new: return s.new[cp][0]
        g = s.G.get(cp)
        if not g: return None
        _, pi, xo, yo, adv, x0, x1, y0, y1 = g
        return Img(s.pages[pi][y0:y1, x0:x1].copy(), xo, -yo).trim()

    def adv(s, c):
        cp = ord(c)
        return s.new[cp][1] if cp in s.new else s.G[cp][4]

    # ---- số đo ----
    def metrics(s):
        x = s.get('x') or s.get('o'); H_ = s.get('H') or s.get('I')
        s.xh_top, s.base = x.top, x.bottom
        s.cap_top, s.cap_base = H_.top, H_.bottom
        s.xh = s.base - s.xh_top; s.cap = s.cap_base - s.cap_top

    # ---- trích dấu ----
    def mark_from(s, pre, base):
        P, B = s.get(pre), s.get(base)
        if P is None or B is None: return None
        m = P.rows(-10 ** 6, B.top)
        if m is None or m.h > (B.h * 0.8): return None
        return dict(img=m, dx=m.cx - B.cx, gap=B.top - m.bottom)

    def mark_spacing(s, ch, upper, flipv=False, fliph=False, ref=None):
        g = s.get(ch)
        if g is None: return None
        ref = ref or s.ref_size(upper)
        tw, th = ref
        a = scale(g, flipv=flipv, fliph=fliph)
        k = min(tw / a.w, th / a.h) if ch in '^~' else th / a.h
        a = scale(a, h=max(1, round(a.h * k)), w=max(1, round(a.w * k)))
        return dict(img=a, dx=0.0, gap=max(1, round((s.cap if upper else s.xh) * 0.12)))

    def ref_size(s, upper):
        n = s.cap if upper else s.xh
        return (max(2, round(n * 0.6)), max(2, round(n * 0.3)))

    def marks(s, upper):
        A = 'A' if upper else 'a'; E = 'E' if upper else 'e'
        pre = {'grave': 'ÀÈÒÙ' if upper else 'àèòù', 'acute': 'ÁÉÓÚ' if upper else 'áéóú',
               'tilde': 'ÃÕÑ' if upper else 'ãõñ', 'circ': 'ÂÊÔ' if upper else 'âêô'}
        M = {}
        for k, chars in pre.items():
            for c in chars:
                b = ud.normalize('NFD', c)[0]
                m = s.mark_from(c, b)
                if m: M[k] = m; break
        br = s.mark_from('Ğ' if upper else 'ğ', 'G' if upper else 'g') or s.mark_from('Ă' if upper else 'ă', A)
        if br: M['breve'] = br
        # dấu đứng riêng làm nguồn dự phòng
        if 'acute' not in M: M['acute'] = s.mark_spacing('´', upper) or s.mark_spacing('`', upper, fliph=True)
        if 'grave' not in M: M['grave'] = s.mark_spacing('`', upper) or (M['acute'] and dict(M['acute'], img=scale(M['acute']['img'], fliph=True)))
        ref = (M['acute']['img'].w * 2, M['acute']['img'].h) if M.get('acute') else None
        if 'circ' not in M: M['circ'] = s.mark_spacing('^', upper, ref=ref)
        if 'tilde' not in M: M['tilde'] = s.mark_spacing('~', upper, ref=ref)
        if 'breve' not in M and M.get('circ'):
            c = M['circ']; M['breve'] = dict(c, img=scale(c['img'], flipv=True))
        # dấu hỏi: thân của '?' bỏ chấm, thu về cỡ dấu sắc
        q = s.get('?'); ac = M['acute']
        lab, n = components(q)
        if n > 1:
            ys = [np.where(lab == i)[0].min() for i in range(1, n + 1)]
            keep = [i + 1 for i in range(n) if ys[i] < q.h * 0.6]
            a = q.a.copy(); a[~np.isin(lab, keep)] = 0; q = Img(a, q.left, q.top).trim()
        else:
            q = q.rows(q.top, q.top + max(1, round(q.h * 0.72)))
        th = max(3, ac['img'].h + (1 if ac['img'].h >= 3 else 0))
        hk = scale(q, h=max(2, th))
        M['hook'] = dict(img=hk, dx=ac['dx'] * 0.5, gap=max(1, ac['gap']))
        for k in ('grave', 'acute', 'tilde', 'circ', 'breve', 'hook'):
            if not M.get(k): raise RuntimeError('thiếu dấu ' + k)
        return M

    # ---- ghép ----
    def above(s, base, m, stack=None):
        mi = m['img']; gap = max(0, m['gap'])
        top = base.top - gap - mi.h
        if stack == 'right':
            # dấu thanh nằm bên phải dấu mũ, hơi nâng lên (kiểu chữ Việt)
            cap = base.rows(base.top, base.top + max(1, mi.h))
            left = int(cap.right - round(mi.w * 0.2))
            top = base.top + max(1, round(mi.h * 0.4)) - mi.h
        else:
            left = int(round(base.cx + m['dx'] - mi.w / 2.0))
        return Img(mi.a, left, top)

    def compose(s, ch):
        upper = ch.isupper()
        M = s.MU if upper else s.ML
        d = ud.normalize('NFD', ch)
        letter, marks = d[0], d[1:]
        tone = next((TONES[t] for t in marks if t in TONES), None)
        mod = ''.join(t for t in marks if t not in TONES)
        # chữ nền (gồm mũ/trăng/móc) đã có sẵn?
        nfc_base = ud.normalize('NFC', letter + mod)
        if tone and tone != 'dot' and s.has(nfc_base) and mod in ('̂',):
            base_img = s.get(nfc_base); adv = s.adv(nfc_base); stack = 'right' if tone in ('acute', 'grave', 'hook') else 'top'
            core = s.get(letter)
        elif tone and s.has(nfc_base) and mod:
            base_img = s.get(nfc_base); adv = s.adv(nfc_base); stack = 'top'; core = None
        else:
            stack = None
            src = letter
            if letter in 'i' and tone and tone != 'dot':
                src = 'ı'
            base_img = s.get(src); adv = s.adv(src)
            if mod:
                base_img, adv = s.apply_mod(base_img, adv, letter, mod, upper)
        parts = [base_img]
        if tone == 'dot':
            dot = s.get('.')
            bl = s.cap_base if upper else s.base
            gap = max(1, round((s.cap if upper else s.xh) * 0.12))
            cx = base_img.cx + (base_img.w * 0.22 if letter == 'y' else 0)
            parts.append(Img(dot.a, int(round(cx - dot.w / 2.0)), bl + gap))
        elif tone:
            m = M[tone]
            if stack == 'right' and tone == 'grave':
                m = dict(m)  # dấu huyền cũng đặt bên phải dấu mũ
            parts.append(s.above(base_img, m, stack='right' if stack == 'right' else None))
        return union(parts), adv  # dấu thanh không đổi advance (được phép chờm sang lề chữ sau)

    def apply_mod(s, b, adv, letter, mod, upper):
        M = s.MU if upper else s.ML
        if mod == '̂':
            return union([b, s.above(b, M['circ'])]), adv
        if mod == '̆':
            return union([b, s.above(b, M['breve'])]), adv
        if mod == '̛':  # móc ơ ư
            src = s.get('’') or s.get("'") or s.get(',')
            n = s.cap if upper else s.xh
            hh = max(2, round(n * (0.4 if upper else 0.5)))
            hn = scale(src, h=hh)
            if hn.w > max(2, round(n * 0.4)): hn = scale(src, h=hh, w=max(2, round(n * 0.4)))
            left = b.right - max(1, round(hn.w * 0.45))
            top = b.top - round(hn.h * 0.45)
            g = union([b, Img(hn.a, left, top)])
            return g, adv + max(0, g.right - adv)
        raise RuntimeError('mod ' + repr(mod))

    def make_dstroke(s, upper):
        if upper:
            if s.has('Ð'): return s.get('Ð'), s.adv('Ð')
            D = s.get('D'); mid = D.top + D.h // 2
            seg = D.rows(D.top + D.h // 4, D.top + 3 * D.h // 4)
            col = np.where(seg.a.max(axis=2)[seg.h // 2] > 0)[0]
            s0 = seg.left + (col.min() if len(col) else 0); s1 = s0 + max(1, round(s.cap * 0.14))
            x0, x1 = s0 - max(1, round(s.cap * 0.18)), s1 + max(1, round(s.cap * 0.18))
            return s.bar(D, x0, x1, mid), s.adv('D')
        d = s.get('d')
        asc = d.rows(d.top, s.xh_top)
        if asc is None: asc = d
        cols = np.where(asc.a.max(axis=2).max(axis=0) > 0)[0]
        s0, s1 = asc.left + cols.min(), asc.left + cols.max() + 1
        mid = (d.top + s.xh_top) // 2
        x0 = s0 - max(1, round(d.w * 0.28)); x1 = s1 + max(1, round(d.w * 0.12))
        g = s.bar(d, x0, x1, mid, tmax=max(1, round((s.xh_top - d.top) * 0.4)))
        return g, s.adv('d') + max(0, g.right - s.adv('d'))

    def bar(s, base, x0, x1, ymid, tmax=99):
        hy = s.get('-'); t = max(1, min(hy.h if hy else 1, max(1, round(s.xh * 0.16)), tmax))
        v = base.a.reshape(-1, s.bpp).max(axis=0)
        a = np.zeros((t, x1 - x0, s.bpp), np.uint8); a[:] = v
        return union([base, Img(a, x0, ymid - t // 2)])

    # ---- xếp atlas ----
    def place_all(s):
        occ = []
        for pi, pg in enumerate(s.pages):
            o = np.zeros(pg.shape[:2], bool)
            for g in s.f['G']:
                if g[1] == pi: o[g[7]:min(g[8] + 1, o.shape[0]), g[5]:min(g[6] + 1, W)] = True
            occ.append(o)
        newG = []
        for cp, (img, adv) in sorted(s.new.items()):
            w, h = img.w + 2, img.h + 2
            pi, x, y = s.find(occ, w + 1, h + 1)
            s.pages[pi][y + 1:y + 1 + img.h, x + 1:x + 1 + img.w] = img.a
            occ[pi][y:y + h + 1, x:x + w + 1] = True
            xo, yo = img.left - 1, -(img.top - 1)
            assert -128 <= xo < 128 and -128 <= yo < 128 and 0 <= adv < 256, (cp, xo, yo, adv)
            newG.append([cp, pi, xo, yo, adv, x, x + w, y, y + h])
        G = s.f['G'] + newG
        G.sort(key=lambda g: g[0])
        s.f['G'] = G
        s.f['H'] = [p.shape[0] for p in s.pages]
        s.f['pages'] = [p.tobytes() for p in s.pages]

    def find(s, occ, w, h):
        while True:
            for pi in range(len(occ)):
                o = occ[pi]
                if o.shape[0] < h: continue
                S = np.zeros((o.shape[0] + 1, W + 1), np.int32)
                S[1:, 1:] = o.cumsum(0).cumsum(1)
                tot = S[h:, w:] - S[:-h, w:] - S[h:, :-w] + S[:-h, :-w]
                ys, xs = np.where(tot == 0)
                if len(ys): return pi, int(xs[0]), int(ys[0])
            last = len(s.pages) - 1; H = s.pages[last].shape[0]
            if H < 1024:
                s.pages[last] = np.concatenate([s.pages[last], np.zeros_like(s.pages[last])])
                occ[last] = np.concatenate([occ[last], np.zeros_like(occ[last])])
            else:
                s.pages.append(np.zeros((64, W, s.bpp), np.uint8)); occ.append(np.zeros((64, W), bool))

    def patch(s):
        s.metrics(); s.ML = s.marks(False); s.MU = s.marks(True)
        if not s.has('ı'):
            i = s.get('i'); s.new[ord('ı')] = (i.rows(s.xh_top, 10 ** 6), s.adv('i'))
        for up in (False, True):
            c = 'Đ' if up else 'đ'
            if not s.has(c): s.new[ord(c)] = s.make_dstroke(up)
        # thứ tự: chữ có mũ/trăng/móc trước, rồi mới thêm dấu thanh
        tg = vi_targets()
        tg.sort(key=lambda c: len(ud.normalize('NFD', c)))
        for c in tg:
            if not s.has(c): s.new[ord(c)] = s.compose(c)
        added = len(s.new); s.place_all()
        return added

    def data(s): return fontfmt.build(s.f)


if __name__ == '__main__':
    src, dst = sys.argv[1], sys.argv[2]
    F = Font(open(src, 'rb').read()); n = F.patch()
    open(dst, 'wb').write(F.data()); print(os.path.basename(src), '+%d glyph' % n, 'H', F.f['H'])
