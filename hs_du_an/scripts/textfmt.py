"""Lõi chung cho extract.py / repack.py: mô tả file chứa text của engine Gameislive.
Mỗi 'đơn vị' = (file, kiểu). Hàm:
  units(dir)               -> danh sách (rel, kind) các file có text
  strings(b, kind)         -> [(offset, chuỗi, ghi_chu)] theo thứ tự trong file
  rebuild(b, kind, repl)   -> bytes mới, repl = {offset: chuỗi mới}
Kiểu: 'm' (map/N.m kịch bản), 'off4' (i.dat: i4 n, n×(i2,i4), i4 len, blob), 'off2' (monster.dat: i2 n ...),
      'seq' (nối tiếp, không bảng độ dài: quét cả file)."""
import struct, os, re
import utfscan

def _m_try(b, p0):
    """.m: p0=0. .m2 (P3-5): p0 = 2+len(UTF tên)+1 (UTF tên bản đồ + u1). Trả None nếu không khớp."""
    try:
        A, B = struct.unpack('>II', b[p0:p0 + 8]); n = b[p0 + 8]; p = p0 + 9 + 5 * n; segs = []
        for i in range(n):
            sid, L = struct.unpack('>BI', b[p0 + 9 + 5 * i:p0 + 14 + 5 * i]); segs.append((sid, p, p + L)); p += L
        if p != p0 + 8 + A or p > len(b): return None
        return p0, A, n, segs
    except (struct.error, IndexError):
        return None

def m_parse(b):
    """Trả (p0, A, n, segs[(sid,start,end)]). Thử có tiền tố tên (.m2) rồi không tiền tố (.m)."""
    cands = []
    if len(b) > 2: cands.append(2 + struct.unpack('>H', b[:2])[0] + 1)
    cands.append(0)
    for p0 in cands:
        r = _m_try(b, p0)
        if r: return r
    raise AssertionError('không nhận dạng được tiêu đề kịch bản')

def off_parse(b, cw):
    fmt = '>i' if cw == 4 else '>h'
    n = struct.unpack(fmt, b[:cw])[0]; h = cw + 6 * n
    ent = [struct.unpack('>hi', b[cw + 6 * i:cw + 6 * i + 6]) for i in range(n)]
    blen = struct.unpack('>i', b[h:h + 4])[0]; base = h + 4
    assert base + blen == len(b)
    offs = sorted(set(o for _, o in ent))
    return n, ent, base, blen, offs

def strings(b, kind):
    out = []
    if kind == 'm':
        _, _, _, segs = m_parse(b)
        for sid, s, e in segs:
            prev = ''
            for o, t in utfscan.scan(b, s, e):
                out.append((o, t, 'kịch bản %d; trước: %s' % (sid, prev[:20]))); prev = t
    elif kind in ('off4', 'off2'):
        n, ent, base, blen, offs = off_parse(b, 4 if kind == 'off4' else 2)
        ids = {}
        for i, o in ent: ids.setdefault(o, []).append(i)
        for k, o in enumerate(offs):
            e = offs[k + 1] if k + 1 < len(offs) else blen; prev = ''
            for so, t in utfscan.scan(b, base + o, base + e):
                out.append((so, t, 'bản ghi id %s; trước: %s' % ('/'.join(map(str, ids[o])), prev[:20]))); prev = t
    else:
        prev = ''
        for o, t in utfscan.scan(b):
            out.append((o, t, 'trước: %s' % prev[:20])); prev = t
    return out

def rebuild(b, kind, repl):
    if not repl and False: return b
    if kind == 'm':
        p0, A, n, segs = m_parse(b); body = bytearray(); lens = []
        for sid, s, e in segs:
            part = utfscan.rebuild(b, utfscan.scan(b, s, e), repl, s, e); lens.append(len(part)); body += part
        tail = b[p0 + 8 + A:]
        newA = 1 + 5 * n + len(body)
        hdr = b[:p0] + struct.pack('>II', newA, struct.unpack('>I', b[p0 + 4:p0 + 8])[0]) + bytes([n])
        for (sid, _, _), L in zip(segs, lens): hdr += struct.pack('>BI', sid, L)
        return hdr + bytes(body) + tail
    if kind in ('off4', 'off2'):
        cw = 4 if kind == 'off4' else 2
        n, ent, base, blen, offs = off_parse(b, cw)
        blob = bytearray(); newoff = {}
        if offs and offs[0] != 0: blob += b[base:base + offs[0]]
        for k, o in enumerate(offs):
            e = offs[k + 1] if k + 1 < len(offs) else blen
            newoff[o] = len(blob)
            blob += utfscan.rebuild(b, utfscan.scan(b, base + o, base + e), repl, base + o, base + e)
        out = struct.pack('>i' if cw == 4 else '>h', n)
        for i, o in ent: out += struct.pack('>hi', i, newoff[o])
        return out + struct.pack('>i', len(blob)) + bytes(blob)
    return utfscan.rebuild(b, utfscan.scan(b), repl)

SEQ_P1 = ['t.dat', 't2.dat', 'tips.dat', 'map.dat', 'c.dat', 'item15.dat', 'fb_skill.dat']

def units(d, cfg=None):
    """cfg: {'seq': [...], 'off4': [...], 'off2': [...]} (mặc định P1)."""
    cfg = cfg or {'seq': SEQ_P1, 'off4': ['i.dat'], 'off2': ['monster.dat']}
    u = []
    mp = os.path.join(d, 'map')
    if os.path.isdir(mp):
        for f in sorted(os.listdir(mp), key=lambda s: (not s.split('.')[0].isdigit(), int(s.split('.')[0]) if s.split('.')[0].isdigit() else 0, s)):
            if f.endswith('.m') or f.endswith('.m2'): u.append(('map/' + f, 'm'))
    for k in ('off4', 'off2', 'seq'):
        for f in cfg.get(k, []):
            if os.path.exists(os.path.join(d, f)): u.append((f, k))
    return u

def esc(s): return s.replace('\\', '\\\\').replace('\n', '\\n').replace('\t', '\\t').replace('\r', '\\r')
def unesc(s):
    return re.sub(r'\\(.)', lambda m: {'n': '\n', 't': '\t', 'r': '\r', '\\': '\\'}.get(m.group(1), m.group(0)), s)
