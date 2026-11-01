#!/usr/bin/env python3
"""HL5: extract Korean text (csv/*.dat records, csv2, map/*.scn string tables, binary.mod) -> catalog5.json"""
import struct, os, re, json, sys, glob
ROOT = os.environ.get('HL5_JAR', os.path.join(os.path.dirname(os.path.dirname(os.path.abspath(__file__))), 'hl5', 'jar'))
RES = ROOT + '/res/c'
HANGUL = re.compile(rb'[\xb0-\xc8][\xa1-\xfe]')

def is_text(s):
    """valid EUC-KR text containing Hangul, no control bytes"""
    if not HANGUL.search(s): return False
    i = 0
    while i < len(s):
        c = s[i]
        if c >= 0x80:
            if i + 1 >= len(s) or not (0xa1 <= c <= 0xfe and 0xa1 <= s[i + 1] <= 0xfe): return False
            i += 2
        else:
            if c < 0x20 and c not in (0x0a,): return False
            i += 1
    try: s.decode('euc-kr')
    except: return False
    return True

def cands(r, i):
    out = []
    n = r[i]
    if n >= 1 and i + 1 + n <= len(r) and is_text(r[i + 1:i + 1 + n]): out.append((1, n))
    if i + 1 < len(r):
        n2 = n | (r[i + 1] << 8)
        if n2 >= 2 and i + 2 + n2 <= len(r) and is_text(r[i + 2:i + 2 + n2]): out.append((2, n2))
    return out

def rec_strings(r):
    """length-prefixed strings in a record: parse maximizing covered Hangul (DP)"""
    L = len(r)
    best = [0] * (L + 1); choice = [None] * (L + 1)
    for i in range(L - 1, -1, -1):
        best[i] = best[i + 1]; choice[i] = None
        for (ps, n) in cands(r, i):
            v = len(HANGUL.findall(r[i + ps:i + ps + n])) * 2 + 1 + best[i + ps + n]
            if v > best[i]: best[i] = v; choice[i] = (ps, n)
    out = []; i = 0
    while i < L:
        c = choice[i]
        if c: out.append((i, c[0], c[1])); i += c[0] + c[1]
        else: i += 1
    return out

def parse_dat(b):
    n = struct.unpack_from('<H', b, 0)[0]; p = 2; recs = []
    for _ in range(n):
        l = struct.unpack_from('<H', b, p)[0]; recs.append(b[p + 2:p + 2 + l]); p += 2 + l
    assert p == len(b)
    return recs

def parse_scn(b):
    for p in range(len(b) - 2, -1, -1):
        n = b[p]
        if n == 0: continue
        if p + 1 + n + sum(b[p + 1:p + 1 + n]) == len(b) and p + 1 + n <= len(b):
            lens = b[p + 1:p + 1 + n]; q = p + 1 + n; strs = []
            for l in lens: strs.append(b[q:q + l]); q += l
            return p, strs
    return None, None

def main():
    cat = []
    for f in sorted(glob.glob(RES + '/csv/*.dat') + glob.glob(RES + '/csv2/*.dat')):
        rel = os.path.relpath(f, ROOT)
        for ri, r in enumerate(parse_dat(open(f, 'rb').read())):
            for (o, ps, n) in rec_strings(r):
                cat.append({'file': rel, 'kind': 'dat', 'rec': ri, 'off': o, 'ps': ps, 'src': r[o + ps:o + ps + n].decode('euc-kr'), 'cap': 255 if ps == 1 else 4000})
    for f in sorted(glob.glob(RES + '/map/*.scn')):
        rel = os.path.relpath(f, ROOT)
        p, strs = parse_scn(open(f, 'rb').read())
        if p is None: print('no table', f); continue
        for si, s in enumerate(strs):
            t = s.rstrip(b'\0')
            if is_text(t): cat.append({'file': rel, 'kind': 'scn', 'idx': si, 'src': t.decode('euc-kr'), 'cap': 254})
    # strings inside binary.mod (patched in place): item category names (256-byte slots), DRM notice
    elf = open(ROOT + '/binary.mod', 'rb').read()
    def boff(a): return a - 0x1000 + 0x34
    for i in range(18):
        a = 0x11452a + 0x100 * i
        t = elf[boff(a):boff(a) + 0x100].split(b'\0')[0]
        if is_text(t): cat.append({'file': 'binary.mod', 'kind': 'bin', 'addr': a, 'src': t.decode('euc-kr'), 'cap': 0xff})
    a = 0x110250
    t = elf[boff(a):].split(b'\0')[0]
    cat.append({'file': 'binary.mod', 'kind': 'bin', 'addr': a, 'src': t.decode('euc-kr'), 'cap': len(t)})
    json.dump(cat, open(os.path.join(os.path.dirname(os.path.abspath(__file__)), 'catalog5.json'), 'w'), ensure_ascii=False, indent=0)
    uniq = {}
    for e in cat: uniq.setdefault(e['src'], 0); uniq[e['src']] += 1
    print(len(cat), 'entries', len(uniq), 'unique', sum(len(k.encode('euc-kr')) for k in uniq), 'bytes')

if __name__ == '__main__': main()
