"""Parsers/builders for the text-bearing resource formats of Heroes Lore 4 (LGT & KTF).

All strings are raw bytes (EUC-KR in the original, game encoding in the translation).
"""
import struct, re

HANGUL = re.compile(rb'[\xb0-\xc8][\xa1-\xfe]')


def is_text(b):
    try:
        s = b.decode('euc-kr')
    except UnicodeDecodeError:
        return False
    return len(s) > 0 and all(c.isprintable() for c in s)


# ---------------------------------------------------------------- tdf
# u16 total, u16 n, u16 off[n], NUL-terminated strings

def tdf_parse(d):
    tot, n = struct.unpack_from('<HH', d, 0)
    offs = [struct.unpack_from('<H', d, 4 + 2 * i)[0] for i in range(n)]
    out = []
    for o in offs:
        e = d.index(b'\0', o)
        out.append(d[o:e])
    return out


def tdf_build(strings):
    n = len(strings)
    hdr = 4 + 2 * n
    offs, body = [], b''
    for s in strings:
        offs.append(hdr + len(body))
        body += s + b'\0'
    total = hdr + len(body)
    assert total < 65536
    return struct.pack('<HH', total, n) + b''.join(struct.pack('<H', o) for o in offs) + body


# ---------------------------------------------------------------- tip
# u16 n, then n x (u8 len (excl. NUL), bytes, NUL)

def tip_parse(d):
    n = struct.unpack_from('<H', d, 0)[0]
    p, out = 2, []
    for i in range(n):
        l = d[p]
        out.append(d[p + 1:p + 1 + l])
        assert p + 1 + l == len(d) or d[p + 1 + l] == 0
        p += 2 + l
    return out, b''  # (the last string of the original file has no NUL)


def tip_build(strings, tail=b''):
    out = struct.pack('<H', len(strings))
    for s in strings:
        assert len(s) < 256
        out += bytes([len(s)]) + s + b'\0'
    return out + tail


# ---------------------------------------------------------------- NAME_DAT
# records: u16 L (= n+1), u8 n, n bytes

def names_parse(d):
    p, out = 0, []
    while p < len(d):
        L = struct.unpack_from('<H', d, p)[0]
        n = d[p + 2]
        assert L == n + 1
        out.append(d[p + 3:p + 3 + n])
        p += 2 + L
    return out


def names_build(strings):
    return b''.join(struct.pack('<HB', len(s) + 1, len(s)) + s for s in strings)


# ---------------------------------------------------------------- scenario (decrypted)
# script ... then text table at the end: u8 n, u8 len[n] (incl NUL), strings; zero pad to 8

def scn_parse_table(d, p):
    n = d[p]
    q = p + 1 + n
    if n == 0 or q > len(d):
        return None
    lens = list(d[p + 1:q])
    pos, strs = q, []
    for l in lens:
        if l == 0 or pos + l > len(d) or d[pos + l - 1] != 0 or 0 in d[pos:pos + l - 1]:
            return None
        seg = d[pos:pos + l - 1]
        if seg and not is_text(seg):
            return None
        strs.append(seg)
        pos += l
    return n, pos, strs


def scn_find(d):
    best = None
    for p in range(len(d) - 2):
        r = scn_parse_table(d, p)
        if not r:
            continue
        n, end, strs = r
        tail = d[end:]
        if len(tail) <= 7 and not any(tail):
            if best is None or n > best[1]:
                best = (p, n, strs)
    return best  # (table offset, count, strings)


def scn_build(d, p, strings):
    """Rebuild a decrypted scenario with new strings (same count)."""
    out = bytearray(d[:p])
    out.append(len(strings))
    for s in strings:
        assert len(s) + 1 < 256, (len(s), s)
        out.append(len(s) + 1)
    for s in strings:
        out += s + b'\0'
    while len(out) % 8:
        out.append(0)
    return bytes(out)


def scn_speakers(d, p, nstr):
    """Heuristic: dialog op 0x2e [face][pos][name][text] -> {text: name index}."""
    sp = {}
    for i in range(p - 4):
        if d[i] == 0x2e and d[i + 4] < nstr and d[i + 3] < 64 and d[i + 2] < 8:
            sp.setdefault(d[i + 4], d[i + 3])
    return sp


# ---------------------------------------------------------------- record chains (decrypted data)
# u16 L, L bytes body

def records(d):
    p, out = 0, []
    while p + 2 <= len(d):
        L = struct.unpack_from('<H', d, p)[0]
        if L == 0 or p + 2 + L > len(d):
            break
        out.append((p, d[p + 2:p + 2 + L]))
        p += 2 + L
    return out, d[p:]


def strings_in(body, minlen=2):
    """Heuristic u8-length-prefixed text fields in a record body -> [(off, bytes)]."""
    res, o = [], 0
    while o < len(body):
        n = body[o]
        seg = body[o + 1:o + 1 + n]
        if n >= minlen and len(seg) == n and is_text(seg) and (HANGUL.search(seg) or re.search(rb'[A-Za-z]{2}', seg)):
            res.append((o, seg))
            o += 1 + n
            continue
        o += 1
    return res
