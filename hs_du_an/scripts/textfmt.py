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

def m2_list_start(b, e):
    """P3-5 (.m2): sau vùng kịch bản (hết ở e) là phần bản đồ rồi danh sách đối tượng. Đo phần bản đồ theo code `b.c.c.b(DataInputStream)`:
    u1 lớp s, u1 r, u1 q, u1 số tileset; mỗi tileset: UTF, u1, i2, u1, UTF, i8, u1 k, k×2 byte; rồi s*r*q byte ô.
    Trả offset đầu danh sách đối tượng (i2 n, n × bản ghi a.a.a(): ...)."""
    s, r, q, nt = b[e], b[e + 1], b[e + 2], b[e + 3]; p = e + 4
    def utf(p): return p + 2 + ((b[p] << 8) | b[p + 1])
    for _ in range(nt):
        p = utf(p); p += 1 + 2 + 1; p = utf(p); p += 8; k = b[p]; p += 1 + 2 * k
    p += s * r * q
    assert p <= len(b), 'phần bản đồ vượt file'
    return p

def strings(b, kind):
    out = []
    if kind in ('m', 'm2'):
        p0, A, _, segs = m_parse(b)
        if kind == 'm2' and p0 > 0:
            nm = utfscan.scan(b, 0, p0)
            if nm: out.append((0, nm[0][1], 'tên bản đồ'))
        for sid, s, e in segs:
            prev = ''
            for o, t in utfscan.scan(b, s, e):
                out.append((o, t, 'kịch bản %d; trước: %s' % (sid, prev[:20]))); prev = t
        if kind == 'm2':
            prev = ''
            for o, t in utfscan.scan(b, m2_list_start(b, p0 + 8 + A), len(b)):
                out.append((o, t, 'đối tượng/NPC; trước: %s' % prev[:20])); prev = t
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
    if kind in ('m', 'm2'):
        p0, A, n, segs = m_parse(b); body = bytearray(); lens = []
        for sid, s, e in segs:
            part = utfscan.rebuild(b, utfscan.scan(b, s, e), repl, s, e); lens.append(len(part)); body += part
        e = p0 + 8 + A; tail = b[e:]; pre = b[:p0]
        if kind == 'm2':
            ls = m2_list_start(b, e)
            tail = b[e:ls] + utfscan.rebuild(b, utfscan.scan(b, ls, len(b)), repl, ls, len(b))
            if p0 > 0 and 0 in repl and utfscan.scan(b, 0, p0): pre = utfscan.enc(repl[0]) + b[p0 - 1:p0]
        newA = 1 + 5 * n + len(body)
        hdr = pre + struct.pack('>II', newA, struct.unpack('>I', b[p0 + 4:p0 + 8])[0]) + bytes([n])
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

def _p(seq): return {'seq': seq, 'off4': ['i.dat'], 'off2': ['monster.dat']}
PARTS = {  # file text theo phần (đã đối chiếu loader của game bằng verify_files.py / verify_scan.py)
    1: _p(SEQ_P1),
    2: _p(['t.dat', 'tips.dat', 'help.dat', 'c.dat', 'item15.dat']),
    3: _p(['t.dat', 'tips.dat', 'lk_tips.dat', 'help.dat', 'c.dat', 'item15.dat']),
    # P4/P5: lk_tips.dat (giống hệt P3) và ii.dat (P4) không có class nào nạp -> bỏ qua
    4: _p(['t.dat', 'tips.dat', 'help.dat', 'c.dat', 'item15.dat']),
    5: _p(['t.dat', 'tips.dat', 'help.dat', 'c.dat', 'item15.dat']),
}

def units(d, cfg=None):
    """cfg: {'seq': [...], 'off4': [...], 'off2': [...]} (mặc định P1)."""
    cfg = cfg or PARTS[1]
    if isinstance(cfg, int): cfg = PARTS[cfg]
    u = []
    mp = os.path.join(d, 'map')
    if os.path.isdir(mp):
        for f in sorted(os.listdir(mp), key=lambda s: (not s.split('.')[0].isdigit(), int(s.split('.')[0]) if s.split('.')[0].isdigit() else 0, s)):
            if f.endswith('.m') or f.endswith('.m2'): u.append(('map/' + f, 'm2' if f.endswith('.m2') else 'm'))
    for k in ('off4', 'off2', 'seq'):
        for f in cfg.get(k, []):
            if os.path.exists(os.path.join(d, f)): u.append((f, k))
    return u

def esc(s): return s.replace('\\', '\\\\').replace('\n', '\\n').replace('\t', '\\t').replace('\r', '\\r')
def unesc(s):
    return re.sub(r'\\(.)', lambda m: {'n': '\n', 't': '\t', 'r': '\r', '\\': '\\'}.get(m.group(1), m.group(0)), s)
