"""Find Korean string literals in the game binaries."""
import re, struct, sys
HANGUL = re.compile(rb'[\xb0-\xc8][\xa1-\xfe]')
def valid(b):
    try: s = b.decode('euc-kr')
    except UnicodeDecodeError: return None
    if any(not (c.isprintable() or c in '\n') for c in s): return None
    return s
def scan(img, base, lo, hi, refs_img=None):
    """img: bytes mapped at base; scan [lo,hi) for NUL-terminated strings containing hangul."""
    out = []
    a = lo
    while a < hi:
        o = a - base
        if img[o] == 0:
            a += 1; continue
        e = img.find(b'\0', o, hi - base)
        if e < 0: break
        seg = img[o:e]
        s = valid(seg)
        if s is not None and HANGUL.search(seg):
            # slot: include following zero padding
            z = e
            while z < hi - base and img[z] == 0: z += 1
            out.append({'addr': a, 'len': e - o, 'slot': z - o, 'ko': s})
            a = base + e + 1
        else:
            a += 1
    return out
def literal_refs(img, base, targets):
    t = set(targets); refs = {}
    for i in range(0, len(img) - 3, 4):
        v = struct.unpack_from('<I', img, i)[0]
        if v in t: refs.setdefault(v, []).append(base + i)
    return refs
if __name__ == '__main__':
    d = open('/home/claude/work/lgtjar/binary.mod','rb').read()
    text = d[0x34:0x34+0x7c142]; data = d[0x93970:0x93970+0x10ac]
    ss = scan(text, 0x1000, 0x1000, 0x1000+len(text)) + scan(data, 0x1400000, 0x1400000, 0x1400000+len(data))
    refs = literal_refs(text, 0x1000, [s['addr'] for s in ss]); refs2 = literal_refs(data, 0x1400000, [s['addr'] for s in ss])
    for s in ss:
        r = refs.get(s['addr'], []) + refs2.get(s['addr'], [])
        print(hex(s['addr']), s['len'], s['slot'], len(r), s['ko'])
