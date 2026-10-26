# -*- coding: utf-8 -*-
"""parse/repack container uires.bin / animation.bin
Định dạng (xem BAO_CAO.md):
 short nCi, short nBt, int[nCi+1] offCi, ci-blob, int[nBt+1] offBt, bt-records
 bt record: byte flag(0 => có bảng e,f), short d, [e:d*2 short, f:d*2 short], byte g, short h,
   h>0: byte[g*h] bảng palette, int len, PNG ; h==0: int len, PNG
"""
import struct, os, hashlib
W = os.environ.get('WROOT', '/tmp/claude-0/-home-user-Vi-t-ho-game/9a067d44-fb40-5db7-b229-544b694f82f4/scratchpad')
JX = W + '/jx/bin/'

class Bt:
    pass

def parse(data):
    nci, nbt = struct.unpack('>hh', data[:4])
    p = 4
    offci = struct.unpack('>%di' % (nci + 1), data[p:p + 4 * (nci + 1)]); p += 4 * (nci + 1)
    ci_start = p
    ci_blob = data[p:p + offci[-1] - offci[0]]  # sizes by diff
    p += offci[-1] - offci[0]
    hdr_end_ci = p
    offbt = struct.unpack('>%di' % (nbt + 1), data[p:p + 4 * (nbt + 1)]); p += 4 * (nbt + 1)
    bt_start = p
    recs = []
    for i in range(nbt):
        size = offbt[i + 1] - offbt[i]
        r = data[p:p + size]
        recs.append(parse_bt(r, p))
        p += size
    assert p == len(data), (p, len(data))
    c = Bt()
    c.nci, c.nbt, c.offci, c.ci_start, c.ci_blob, c.offbt, c.bt_start, c.recs = nci, nbt, offci, ci_start, ci_blob, offbt, bt_start, recs
    c.ci_off_len = p
    return c

def parse_bt(r, fileoff):
    b = Bt(); b.fileoff = fileoff
    q = 0
    flag = r[0]; q = 1
    d = struct.unpack('>h', r[q:q + 2])[0]; q += 2
    if flag == 0:
        q += 8 * d  # e (d*2 short) + f (d*2 short)
    g = r[q]; q += 1
    h = struct.unpack('>h', r[q:q + 2])[0]; q += 2
    b.flag, b.d, b.g, b.h = flag, d, g, h
    if h > 0:
        q += g * h
    n = struct.unpack('>i', r[q:q + 4])[0]; q += 4
    b.head = r[:q]
    b.png = r[q:q + n]
    b.png_off = fileoff + q
    b.tail = r[q + n:]
    assert len(b.tail) == 0, ('tail', len(b.tail))
    assert b.png[:8] == b'\x89PNG\r\n\x1a\n', 'not png'
    return b

def build(c, data_ci_prefix=None):
    """dựng lại từ c (recs có .png mới). Cập nhật bảng offBt và int độ dài."""
    out = bytearray()
    out += struct.pack('>hh', c.nci, c.nbt)
    out += struct.pack('>%di' % (c.nci + 1), *c.offci)
    out += c.ci_blob
    # offBt: giữ offset đầu, cộng dồn kích thước
    recs = []
    for b in c.recs:
        head = bytearray(b.head)
        head[-4:] = struct.pack('>i', len(b.png))
        recs.append(bytes(head) + b.png)
    base = c.offbt[0]
    offs = [base]
    for r in recs: offs.append(offs[-1] + len(r))
    out += struct.pack('>%di' % (c.nbt + 1), *offs)
    for r in recs: out += r
    return bytes(out)

def sha1(b): return hashlib.sha1(b).hexdigest()

if __name__ == '__main__':
    for n in ('uires.bin', 'animation.bin'):
        d = open(JX + n, 'rb').read()
        c = parse(d)
        print(n, len(d), 'nci', c.nci, 'nbt', c.nbt, 'offci0', c.offci[0], 'offci-1', c.offci[-1], 'offbt0', c.offbt[0], 'offbt-1', c.offbt[-1], 'bt_start', c.bt_start)
        nd = build(c)
        print(' repack identical:', nd == d, sha1(nd), sha1(d))
        print(' h>0:', [(i, b.h, b.g, len(b.png)) for i, b in enumerate(c.recs) if b.h > 0][:20])
