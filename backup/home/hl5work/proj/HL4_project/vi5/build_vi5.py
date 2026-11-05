#!/usr/bin/env python3
"""HL5 Việt hoá: bản dịch -> overlay cho jar.

  build_vi5.py <bản dịch .xlsx|.tsv> [...] [--out build/overlay] [--font font/font_15px.bin]

Bản dịch: cột 'ID', 'Tiếng Hàn', 'Tiếng Việt' (file xuất bởi export5.py). Dòng để trống = giữ tiếng Hàn.
Kết quả (thư mục overlay, cấu trúc như gốc jar):
  res/c/csv/*.dat, res/c/csv2/*.dat, res/c/map/*.scn, binary.mod, vi5/dict.txt, vi5/font.bin
Mã hoá: mỗi từ (chuỗi chữ cái) = 2 byte (lead 0xC9.., trail 0xA1..) = chỉ số dòng trong dict.txt;
số, dấu câu, khoảng trắng, mã điều khiển giữ nguyên 1 byte ASCII (vẽ bằng font gốc của game).
"""
import sys, os, json, struct, re, glob, collections, shutil
W = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, W)
from extract5 import ROOT, RES, parse_dat, parse_scn, rec_strings

LEAD0, TRAIL0 = 0xC9, 0xA1
OFFLINE_MSG = 'Bản offline: không hỗ trợ;tính năng cần máy chủ.'
MAXCODES = (0xFF - LEAD0) * 94
CTRL = ';&|${}\'"`^<['
REPL = {'…': '...', '“': '', '”': '', '‘': '', '’': '', '–': '-', '—': '-',
        ' ': ' ', '　': ' ', '~': '-', '·': '.', '•': '.', '×': 'x'}


def load_font_chars(path):
    data = open(path, 'rb').read()
    n = struct.unpack('>H', data[:2])[0]
    return set(data[2:2 + n].decode('utf-8'))


def is_word_char(c):
    return c.isalpha() and not ('가' <= c <= '힣') and ord(c) < 0x3000


def read_translations(paths):
    tr = {}
    for p in paths:
        if p.endswith('.xlsx'):
            import openpyxl
            ws = openpyxl.load_workbook(p, read_only=True).active
            rows = list(ws.iter_rows(values_only=True))
        else:
            rows = [l.rstrip('\n').split('\t') for l in open(p, encoding='utf-8-sig')]
        head = [str(h or '').strip().lower() for h in rows[0]]
        def col(*names):
            for i, h in enumerate(head):
                for n in names:
                    if n in h: return i
            return None
        ci, ck, cv = col('id'), col('hàn', 'han'), col('việt', 'viet')
        if ck is None or cv is None: raise SystemExit('%s: thiếu cột Tiếng Hàn / Tiếng Việt' % p)
        for r in rows[1:]:
            if r is None or len(r) <= max(ck, cv): continue
            ko, vi = r[ck], r[cv]
            if ko is None or vi is None: continue
            ko = str(ko).replace('\\n', '\n'); vi = str(vi).replace('\\n', '\n')
            if vi.strip() == '': continue
            tr[ko] = vi
    return tr


class Encoder:
    def __init__(self, font_chars):
        self.fc = font_chars
        self.dict = []
        self.index = {}
        self.missing = collections.Counter()

    def clean(self, s):
        return ''.join(REPL.get(c, c) for c in s)

    def code(self, w):
        k = self.index.get(w)
        if k is None:
            if len(self.dict) >= MAXCODES: return None
            k = len(self.dict); self.dict.append(w); self.index[w] = k
        return bytes([LEAD0 + k // 94, TRAIL0 + k % 94])

    def encode(self, s, tight=False):
        """tight: False, True / 1 (15 px compact) or 2 (10 px font)"""
        pre = SMALL if tight == 2 else TIGHT
        """tight: compact dict entries (prefix \\x01: 1 px less per letter, a space before a word is
        folded into the word entry as a 3 px space)"""
        s = self.clean(s)
        out = bytearray(); i = 0; sp = ''
        while i < len(s):
            c = s[i]
            if tight and c == ' ' and i + 1 < len(s) and is_word_char(s[i + 1]) and not sp:
                sp = ' '; i += 1; continue
            if is_word_char(c):
                j = i
                while j < len(s) and is_word_char(s[j]): j += 1
                w = s[i:j]
                if i > 0 and s[i - 1] == '%':       # printf format (%s, %d...)
                    out += w.encode('ascii', 'replace'); i = j; continue
                for ch in w:
                    if ch not in self.fc: self.missing[ch] += 1
                w = ''.join(ch if ch in self.fc else '?' for ch in w)
                b = self.code(pre + sp + w if tight else w)
                sp = ''
                if b is None:  # dict full: character by character
                    for ch in w: out += self.code(ch) or b'?'
                else:
                    out += b
                i = j; continue
            if sp: out.append(32); sp = ''
            if ord(c) < 0x80:
                out.append(ord(c))
            else:
                try: out += c.encode('euc-kr')     # Hangul left in the translation
                except UnicodeEncodeError: self.missing[c] += 1; out.append(ord('?'))
            i += 1
        return bytes(out)


VNF = None
TIGHT = '\x01'
SMALL = '\x02'


def vi_width(s, adv):
    best = 0
    for line in s.split(';'):
        w = 0
        for c in line:
            if c in '&|${}\'"`^<[': continue
            if ord(c) < 0x80 and not c.isalpha(): w += 6
            else: w += adv.get(c, 7)
        best = max(best, w)
    return best


AUTOWRAP = True


def vi_width_tight(s, adv):
    best = 0
    for line in s.split(';'):
        w = 0; prev = ''
        for c in line:
            if c in '&|${}\'"`^<[': continue
            if c == ' ': w += 3 if True else 6
            elif ord(c) < 0x80 and not c.isalpha(): w += 6
            else:
                a = adv.get(c, 7); w += a - 1 if a > 2 else a
        best = max(best, w)
    return best


def vi_width_small(s, adv10):
    best = 0
    for line in s.split(';'):
        w = 0
        for c in line:
            if c in '&|${}\'"`^<[': continue
            if c == ' ': w += 3
            elif ord(c) < 0x80 and not c.isalpha(): w += 6
            else:
                a = adv10.get(c, 5); w += a - 1 if a > 2 else a
        best = max(best, w)
    return best


def load_fit():
    f = os.path.join(W, 'fit.tsv'); r = {}
    if os.path.exists(f):
        for l in open(f, encoding='utf-8').read().split('\n')[1:]:
            c = l.split('\t')
            if len(c) >= 3 and (c[0].startswith('K') or c[0].startswith('G:')): r[c[0]] = (int(c[1]), c[2].strip())
    return r


def wrap_lines(s, limit, adv):
    """greedy word wrap of each ';' line to at most limit px (colour codes kept in place)"""
    out = []
    for line in s.split(';'):
        if vi_width(line, adv) <= limit or ' ' not in line.strip():
            out.append(line)
            continue
        words = line.split(' ')
        cur = ''
        for w in words:
            t = w if cur == '' else cur + ' ' + w
            if cur != '' and vi_width(t, adv) > limit:
                out.append(cur)
                cur = w
            else:
                cur = t
        out.append(cur)
    return ';'.join(out)


def font_adv(path):
    data = open(path, 'rb').read()
    n = struct.unpack('>H', data[:2])[0]
    cs = data[2:2 + n].decode('utf-8')
    h = data[2 + n]
    ws = data[3 + n:3 + n + len(cs)]
    adv = {c: ws[i] for i, c in enumerate(cs)}
    return adv


def check_codes(ko, vi):
    """control characters must be kept (same count)"""
    bad = []
    for c in CTRL:
        if ko.count(c) != vi.count(c): bad.append('%s %d→%d' % (c, ko.count(c), vi.count(c)))
    for m in set(re.findall(r'%[0-9]*[a-z]|#[0-9]+', ko)):
        if ko.count(m) != vi.count(m): bad.append('%s %d→%d' % (m, ko.count(m), vi.count(m)))
    return bad


def main():
    args = sys.argv[1:]
    out = os.path.join(W, 'build', 'overlay')
    font = os.path.join(W, 'font', 'font_15px.bin')
    paths = []
    while args:
        a = args.pop(0)
        if a == '--out': out = args.pop(0)
        elif a == '--font': font = args.pop(0)
        else: paths.append(a)
    tr = read_translations(paths)
    fit = load_fit()
    gw = os.path.join(W, 'game_wraps.txt')
    gamewraps = set(l.strip() for l in open(gw, encoding='utf-8') if l.startswith('K')) if os.path.exists(gw) else set()
    # text groups for fit.tsv rows "G:<file pattern>.f<field>" (digits in file names -> #), e.g. G:item_#.f0
    kogroups = collections.defaultdict(set)
    _rec = collections.defaultdict(list)
    for e in json.load(open(os.path.join(W, 'catalog5.json'))):
        if e['kind'] == 'dat': _rec[(e['file'], e['rec'])].append(e)
    for es in _rec.values():
        for i, e in enumerate(sorted(es, key=lambda e: e['off'])):
            kogroups[e['src']].add('G:%s.f%d' % (re.sub(r'\d+', '#', os.path.basename(e['file'])[:-4]), i))
    adv10 = font_adv(os.path.join(W, 'font', 'font_10px_v2.bin'))
    # per-file overrides (menu_override.tsv: File, Tieng Han, Tieng Viet), e.g. short main-menu labels
    fovr = {}
    mo = os.path.join(W, 'menu_override.tsv')
    if os.path.exists(mo):
        for l in open(mo, encoding='utf-8-sig').read().split('\n')[1:]:
            c = l.split('\t')
            if len(c) >= 3 and c[2].strip(): fovr[(c[0], c[1])] = c[2]
    cat = json.load(open(os.path.join(W, 'catalog5.json')))
    ids = json.load(open(os.path.join(W, 'ids5.json')))
    enc = Encoder(load_font_chars(font))
    adv = font_adv(font); adv[' '] = 6
    report = []
    # encode every translated source once (stable dict order = catalogue order)
    encoded = {}
    for e in cat:
        ko = e['src']
        if ko in encoded or ko not in tr: continue
        vi = tr[ko]
        bad = check_codes(ko, vi)
        if bad: report.append('%s mã điều khiển khác bản gốc: %s' % (ids.get(ko, '?'), ', '.join(bad)))
        tight = False
        fi = fit.get(ids.get(ko))
        for gk in kogroups.get(ko, ()):
            g = fit.get(gk)
            if g and (fi is None or g[0] < fi[0]): fi = g
        if fi:  # measured box width (fit.tsv): wrap / compact / report
            mx, mode = fi
            if mode == 'wrap' and vi_width(vi, adv) > mx and vi_width_small(vi, adv10) > mx:
                vi = wrap_lines(vi, mx, adv)  # wrap only when even the compact forms do not fit on one line
            if mode == 'rewrap':  # box wrapped by the game at mx px: re-flow the text so no line is broken twice
                vi = wrap_lines(vi.replace(';', ' '), mx, adv)
            if mode == 'small':  # always the 10 px font (same look for every cell of a menu / tab bar)
                tight = 2
                if vi_width_small(vi, adv10) > mx:
                    report.append('%s CẦN RÚT GỌN: 10px %d > ô %d px: %s' % (ids.get(ko), vi_width_small(vi, adv10), mx, vi))
            elif vi_width(vi, adv) > mx:
                tight = 1
                if vi_width_tight(vi, adv) > mx:
                    tight = 2
                    if vi_width_small(vi, adv10) > mx and mode != 'soft':
                        report.append('%s CẦN RÚT GỌN: rộng %d px (nén %d, 10px %d) > ô %d px: %s' % (ids.get(ko), vi_width(vi, adv), vi_width_tight(vi, adv), vi_width_small(vi, adv10), mx, vi))
        elif ';' in ko and AUTOWRAP:
            # multi-line text: re-wrap Vietnamese lines longer than the widest original line (the box holds
            # that width; in boxes the game wraps itself this also avoids single-word orphan lines)
            from export5 import ko_width as _kw
            vi = wrap_lines(vi, max(_kw(ko), 60) + 4, adv)
        encoded[ko] = enc.encode(vi, tight)
        from export5 import ko_width
        kw, vw = ko_width(ko), vi_width(enc.clean(vi), adv)
        if vw > max(kw + 12, kw * 11 // 10): report.append('%s dòng dài hơn bản gốc: %d px > %d px (nên ngắt thêm ;)' % (ids.get(ko, '?'), vw, kw))
    if os.path.exists(out): shutil.rmtree(out)
    os.makedirs(out)
    used = 0; toolong = 0
    by_file = collections.defaultdict(list)
    for e in cat: by_file[e['file']].append(e)

    encmap = {}

    def pick(e):
        nonlocal used, toolong
        ko = e['src']
        b = encoded.get(ko)
        fo = fovr.get((os.path.basename(e['file']), ko))
        if fo is not None: b = enc.encode(fo)
        if b is None: return None
        if len(b) > e['cap']:
            toolong += 1
            report.append('%s quá dài (%d byte > %d), giữ tiếng Hàn' % (ids.get(ko, '?'), len(b), e['cap']))
            return None
        used += 1
        encmap[b.hex()] = ids.get(ko, '?')
        return b

    for f, es in sorted(by_file.items()):
        src = os.path.join(ROOT, f)
        kind = es[0]['kind']
        if kind == 'dat':
            recs = parse_dat(open(src, 'rb').read())
            repl = collections.defaultdict(dict)
            for e in es:
                b = pick(e)
                if b is not None: repl[e['rec']][e['off']] = b
            if not repl: continue
            outb = bytearray(struct.pack('<H', len(recs)))
            for ri, r in enumerate(recs):
                if ri in repl:
                    nr = bytearray(); p = 0
                    for (o, ps, n) in rec_strings(r):
                        if o not in repl[ri]: continue
                        nr += r[p:o]
                        b = repl[ri][o]
                        nr += bytes([len(b)]) if ps == 1 else struct.pack('<H', len(b))
                        nr += b
                        p = o + ps + n
                    nr += r[p:]
                    r = bytes(nr)
                outb += struct.pack('<H', len(r)) + r
            dst = os.path.join(out, f); os.makedirs(os.path.dirname(dst), exist_ok=True)
            open(dst, 'wb').write(outb)
        elif kind == 'scn':
            data = open(src, 'rb').read()
            p, strs = parse_scn(data)
            strs = list(strs); changed = False
            for e in es:
                b = pick(e)
                if b is not None: strs[e['idx']] = b + b'\0'; changed = True
            if not changed: continue
            nb = bytearray(data[:p]); nb.append(len(strs) & 0xFF)
            nb += bytes(len(s) for s in strs)
            for s in strs: nb += s
            dst = os.path.join(out, f); os.makedirs(os.path.dirname(dst), exist_ok=True)
            open(dst, 'wb').write(nb)
        elif kind == 'bin':
            dst = os.path.join(out, 'binary.mod')
            elf = bytearray(open(os.path.join(ROOT, 'binary.mod'), 'rb').read())
            changed = False
            for e in es:
                b = pick(e)
                if b is None: continue
                changed = True
                o = e['addr'] - 0x1000 + 0x34
                old = len(e['src'].encode('euc-kr'))
                elf[o:o + max(old, len(b)) + 1] = b + b'\0' * (max(old, len(b)) + 1 - len(b))
            if changed: open(dst, 'wb').write(elf)
    os.makedirs(os.path.join(out, 'vi5'), exist_ok=True)
    # message of the offline server for network-only services (Hl5Server.offlineMsg)
    open(os.path.join(out, 'vi5', 'offline.bin'), 'wb').write(enc.encode(OFFLINE_MSG))
    with open(os.path.join(out, 'vi5', 'dict.txt'), 'w', encoding='utf-8') as f:
        f.write('\n'.join(enc.dict) + '\n')
    shutil.copy(font, os.path.join(out, 'vi5', 'font.bin'))
    shutil.copy(os.path.join(W, 'font', 'font_10px_v2.bin'), os.path.join(out, 'vi5', 'font10.bin'))
    if enc.missing:
        report.append('Ký tự font không có (thay bằng ?): ' + ' '.join('%s×%d' % kv for kv in enc.missing.most_common()))
    json.dump(encmap, open(os.path.join(out, '..', 'enc_map.json'), 'w'))
    open(os.path.join(out, '..', 'bao-cao-build.txt'), 'w', encoding='utf-8').write('\n'.join(report) + '\n')
    print('bản dịch: %d câu; đã thay %d chỗ; quá dài %d; từ điển %d/%d mã; cảnh báo %d (xem build/bao-cao-build.txt)'
          % (len(tr), used, toolong, len(enc.dict), MAXCODES, len(report)))


if __name__ == '__main__': main()
