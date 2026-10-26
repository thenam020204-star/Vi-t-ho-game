# -*- coding: utf-8 -*-
"""Trich / dong goi lai text resource cua 雄霸三国之天降女神.

  python3 -I textpack.py extract <bin_dir> <out.tsv>
  python3 -I textpack.py pack <bin_dir> <in.tsv> <out_dir>     # cot 'dich' rong -> giu goc
  python3 -I textpack.py selftest <bin_dir>                    # pack voi tsv chua dich == goc tung byte

Dinh dang (doi chieu code CFR):
  script.bin  n.i/n.b/n.a (n.java:1440-1490): [s2 c1; s4 off1[c1+1]; blob1] [s2 c2; s4 off2[c2+1]; blob2]
              blob2[canh]: s2 n; n * (s2 k; s2 sign[k] = (charIndex, type, param)*; UTF)
              off = vi tri tinh tu dau blob -> tinh lai. Sign trong TSV = marker {c:p} (type 2 doi mau),
              {/} (type 3 tra mau), {tT:p} (loai khac) dat ngay truoc ky tu charIndex.
  string.bin  n.a(String,int) (n.java:1047): s2 c; s4 off[c+1]; blob[k]: s2 n; n*UTF -> tinh lai off.
  help_about  n.w(): s4 n; n*UTF; s4 n; n*UTF.
  choice.bin  n.ap() (n.java:3814): s2 c; s4 off[c+1] (code doc nhung KHONG dung); c*(s2 k; s2*k; UTF;
              s2 m; m*(s2 k; s2*k; UTF)) -> off tinh lai theo cung quy uoc neu goc khop, khong thi giu.
  gameData    bl.a(): byte c; s4 off[c+1] (KHONG dung); doc tuan tu -> thay chuoi tai cho (do tim UTF co chu Han).
  l1.bin      bf.i(): doc tuan tu -> thay tai cho; chuoi bf.o: 'S','N','P' la placeholder (bf.c()).
"""
import sys, os, re, csv, struct
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from binutf import R, utf, scan, is_cjk

HDR = ['id', 'file', 'key', 'goc', 'dich', 'ghichu', 'gioihan']
MARK = re.compile(r'\{c:(-?\d+)\}|\{/\}|\{t(-?\d+):(-?\d+)\}')
# l1.bin: khong dich quang cao game khac (giu nguyen theo quyet dinh nguoi dung)
L1_SKIP_MINLEN = 120   # cac doan gioi thieu game khac dai > 120 ky tu va khong co placeholder
def has_cjk(s): return any(is_cjk(c) for c in s)

def sign_to_text(t, sg):
    ins = {}
    for j in range(0, len(sg), 3):
        i, ty, pa = sg[j:j + 3]
        m = '{c:%d}' % pa if ty == 2 else ('{/}' if ty == 3 and pa == 0 else '{t%d:%d}' % (ty, pa))
        ins.setdefault(i, []).append(m)
    out = []
    for i in range(len(t) + 1):
        out += ins.get(i, [])
        if i < len(t): out.append(t[i])
    assert all(i <= len(t) for i in ins), 'sign ngoai chuoi'
    return ''.join(out)

def text_to_sign(s):
    sg = []; out = []; p = 0
    for m in MARK.finditer(s):
        out.append(s[p:m.start()]); idx = sum(len(x) for x in out)
        if m.group(1) is not None: sg += [idx, 2, int(m.group(1))]
        elif m.group(0) == '{/}': sg += [idx, 3, 0]
        else: sg += [idx, int(m.group(2)), int(m.group(3))]
        p = m.end()
    out.append(s[p:])
    return ''.join(out), sg

# ---------------------------------------------------------------- script.bin
def script_read(d):
    r = R(d); c1 = r.s2(); o1 = [r.s4() for _ in range(c1 + 1)]; b1 = r.p
    sec1 = d[:b1 + o1[c1]]; r.p = b1 + o1[c1]
    c2 = r.s2(); o2 = [r.s4() for _ in range(c2 + 1)]; b2 = r.p
    scenes = []
    for sc in range(c2):
        r.p = b2 + o2[sc]; n = r.s2(); items = []
        for _ in range(n):
            k = r.s2(); sg = [r.s2() for _ in range(k)]; t = r.utf(); items.append((sg, t))
        assert r.p == b2 + o2[sc + 1]; scenes.append(items)
    assert b2 + o2[c2] == len(d)
    return sec1, scenes

def script_write(sec1, scenes):
    blobs = []
    for items in scenes:
        b = bytearray(struct.pack('>h', len(items)))
        for sg, t in items:
            b += struct.pack('>h', len(sg)) + b''.join(struct.pack('>h', x) for x in sg) + utf(t)
        blobs.append(bytes(b))
    o = bytearray(sec1) + struct.pack('>h', len(scenes)); off = 0
    for b in blobs: o += struct.pack('>i', off); off += len(b)
    o += struct.pack('>i', off)
    for b in blobs: o += b
    return bytes(o)

# ---------------------------------------------------------------- string.bin
def strings_read(d):
    r = R(d); c = r.s2(); o = [r.s4() for _ in range(c + 1)]; b = r.p; secs = []
    for k in range(c):
        r.p = b + o[k]; n = r.s2(); secs.append([r.utf() for _ in range(n)]); assert r.p == b + o[k + 1]
    assert b + o[c] == len(d)
    return secs

def strings_write(secs):
    blobs = [struct.pack('>h', len(s)) + b''.join(utf(x) for x in s) for s in secs]
    o = bytearray(struct.pack('>h', len(secs))); off = 0
    for b in blobs: o += struct.pack('>i', off); off += len(b)
    o += struct.pack('>i', off)
    for b in blobs: o += b
    return bytes(o)

# ---------------------------------------------------------------- help_about.bin
def help_read(d):
    r = R(d); out = []
    for _ in range(2):
        n = r.s4(); out.append([r.utf() for _ in range(n)])
    assert r.p == len(d); return out

def help_write(secs):
    return b''.join(struct.pack('>i', len(s)) + b''.join(utf(x) for x in s) for s in secs)

# ---------------------------------------------------------------- choice.bin
def choice_read(d):
    r = R(d); c = r.s2(); o = [r.s4() for _ in range(c + 1)]; b = r.p; ents = []; starts = []
    for _ in range(c):
        starts.append(r.p - b)
        k = r.s2(); sg = [r.s2() for _ in range(k)]; q = r.utf(); m = r.s2(); opts = []
        for _ in range(m):
            k2 = r.s2(); sg2 = [r.s2() for _ in range(k2)]; opts.append((sg2, r.utf()))
        ents.append((sg, q, opts))
    starts.append(r.p - b)
    assert r.p == len(d)
    return o, starts, ents

def choice_write(o, starts, ents):
    blobs = []
    for sg, q, opts in ents:
        x = bytearray(struct.pack('>h', len(sg)) + b''.join(struct.pack('>h', v) for v in sg) + utf(q))
        x += struct.pack('>h', len(opts))
        for sg2, t in opts:
            x += struct.pack('>h', len(sg2)) + b''.join(struct.pack('>h', v) for v in sg2) + utf(t)
        blobs.append(bytes(x))
    if o == starts:     # off goc = vi tri tu dau blob -> tinh lai
        no = []; off = 0
        for b in blobs: no.append(off); off += len(b)
        no.append(off)
    else:
        no = o           # khong hieu quy uoc -> giu (code khong dung)
    return struct.pack('>h', len(ents)) + b''.join(struct.pack('>i', v) for v in no) + b''.join(blobs)

# ---------------------------------------------------------------- in-place (gameData, l1)
def inplace_items(name, d):
    items = scan(d)
    if name == 'l1.bin':
        items = [(p, s) for p, s in items if not (len(s) > L1_SKIP_MINLEN and 'S' not in s)]
    return items

def inplace_write(d, repl):
    """repl: {offset_goc: text_moi}"""
    out = bytearray(); p = 0
    for off in sorted(repl):
        L = struct.unpack('>H', d[off:off + 2])[0]
        out += d[p:off] + utf(repl[off]); p = off + 2 + L
    out += d[p:]
    return bytes(out)

# ---------------------------------------------------------------- extract / pack
def rows_all(bd):
    rows = []
    def add(f, key, s, note='', lim=''):
        rows.append({'id': '%s|%s' % (f, key), 'file': f, 'key': key, 'goc': s, 'dich': '', 'ghichu': note, 'gioihan': lim})
    d = open(os.path.join(bd, 'script.bin'), 'rb').read()
    _, scenes = script_read(d)
    for sc, items in enumerate(scenes):
        for i, (sg, t) in enumerate(items):
            if has_cjk(t): add('script.bin', '%d.%d' % (sc, i), sign_to_text(t, sg), 'hoi thoai; "Ten：" (U+FF1A) = nguoi noi')
    secs = strings_read(open(os.path.join(bd, 'string.bin'), 'rb').read())
    for k, s in enumerate(secs):
        for i, t in enumerate(s):
            if has_cjk(t):
                note = 'muc 4: 2 ky tu dau = ma ai (giu), ky tu cuoi bi cat khi hien' if k == 4 and t[:2].isdigit() else ''
                add('string.bin', '%d.%d' % (k, i), t, note)
    hs = help_read(open(os.path.join(bd, 'help_about.bin'), 'rb').read())
    for k, s in enumerate(hs):
        for i, t in enumerate(s): add('help_about.bin', '%d.%d' % (k, i), t, '| = xuong dong')
    o, st, ents = choice_read(open(os.path.join(bd, 'choice.bin'), 'rb').read())
    for i, (sg, q, opts) in enumerate(ents):
        if has_cjk(q): add('choice.bin', '%d.q' % i, q)
        for j, (sg2, t) in enumerate(opts):
            if has_cjk(t): add('choice.bin', '%d.%d' % (i, j), t, 'lua chon')
    for f in ('gameData.bin', 'l1.bin'):
        d = open(os.path.join(bd, f), 'rb').read()
        for p, s in inplace_items(f, d):
            add(f, str(p), s, 'S N P = placeholder, cam chu hoa S/N/P khac' if f == 'l1.bin' and 'S' in s else '')
    return rows

def write_tsv(rows, path):
    with open(path, 'w', encoding='utf-8', newline='') as fo:
        w = csv.DictWriter(fo, HDR, delimiter='\t', quoting=csv.QUOTE_NONE, escapechar='\\', lineterminator='\n')
        w.writeheader()
        for r in rows: w.writerow(r)

def read_tsv(path):
    with open(path, encoding='utf-8', newline='') as fi:
        return list(csv.DictReader(fi, delimiter='\t', quoting=csv.QUOTE_NONE, escapechar='\\'))

def pack(bd, rows, outd):
    tr = {r['id']: r['dich'] for r in rows if r.get('dich')}
    os.makedirs(outd, exist_ok=True); res = {}
    d = open(os.path.join(bd, 'script.bin'), 'rb').read(); sec1, scenes = script_read(d)
    for sc, items in enumerate(scenes):
        for i, (sg, t) in enumerate(items):
            v = tr.get('script.bin|%d.%d' % (sc, i))
            if v is not None:
                nt, nsg = text_to_sign(v); items[i] = (nsg, nt)
    res['script.bin'] = script_write(sec1, scenes)
    secs = strings_read(open(os.path.join(bd, 'string.bin'), 'rb').read())
    for k, s in enumerate(secs):
        for i in range(len(s)):
            v = tr.get('string.bin|%d.%d' % (k, i))
            if v is not None: s[i] = v
    res['string.bin'] = strings_write(secs)
    hs = help_read(open(os.path.join(bd, 'help_about.bin'), 'rb').read())
    for k, s in enumerate(hs):
        for i in range(len(s)):
            v = tr.get('help_about.bin|%d.%d' % (k, i))
            if v is not None: s[i] = v
    res['help_about.bin'] = help_write(hs)
    o, st, ents = choice_read(open(os.path.join(bd, 'choice.bin'), 'rb').read())
    for i, (sg, q, opts) in enumerate(ents):
        q = tr.get('choice.bin|%d.q' % i, q)
        opts = [(sg2, tr.get('choice.bin|%d.%d' % (i, j), t)) for j, (sg2, t) in enumerate(opts)]
        ents[i] = (sg, q, opts)
    res['choice.bin'] = choice_write(o, st, ents)
    for f in ('gameData.bin', 'l1.bin'):
        d = open(os.path.join(bd, f), 'rb').read()
        repl = {p: tr['%s|%d' % (f, p)] for p, _ in inplace_items(f, d) if ('%s|%d' % (f, p)) in tr}
        res[f] = inplace_write(d, repl)
    for f, b in res.items():
        open(os.path.join(outd, f), 'wb').write(b)
    return res

def main():
    cmd = sys.argv[1]
    if cmd == 'extract':
        rows = rows_all(sys.argv[2]); write_tsv(rows, sys.argv[3])
        from collections import Counter
        print('%d dong:' % len(rows), dict(Counter(r['file'] for r in rows)))
    elif cmd == 'pack':
        res = pack(sys.argv[2], read_tsv(sys.argv[3]), sys.argv[4])
        print('ghi:', ', '.join('%s %d' % (k, len(v)) for k, v in res.items()))
    elif cmd == 'selftest':
        import tempfile
        rows = rows_all(sys.argv[2]); t = tempfile.mkdtemp()
        write_tsv(rows, os.path.join(t, 'a.tsv')); rows2 = read_tsv(os.path.join(t, 'a.tsv'))
        assert [r['goc'] for r in rows] == [r['goc'] for r in rows2], 'TSV khong round-trip'
        for r in rows2: r['dich'] = r['goc']          # "dich" = goc -> phai ra y het
        res = pack(sys.argv[2], rows2, t); bad = 0
        for f, b in res.items():
            same = b == open(os.path.join(sys.argv[2], f), 'rb').read()
            print('%-15s %s' % (f, 'GIONG TUNG BYTE' if same else 'KHAC!')); bad += not same
        sys.exit(1 if bad else 0)

if __name__ == '__main__':
    main()
