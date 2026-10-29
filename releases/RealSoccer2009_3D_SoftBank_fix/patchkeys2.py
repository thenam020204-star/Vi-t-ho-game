# Key layout for J2ME Loader (Nokia codes), matching the game's own help text
# (text 384: "direction keys or 1/2/3/4/6/7/8/9 move", 386: "5 pass", 388: "0 shoot",
# 389: "# long pass") plus the user's request (* = feint, softkeys = on-screen labels).
#
# Game key bits (au.c(int) result, consumed via au.c()/au.a()):
#   1 up, 2 up-right, 4 right, 8 down-right, 16 down, 32 down-left, 64 left, 128 up-left
#   256 pass/select, 512 long pass, 1024 shoot, 2048 feint/skill, 4096 back, 8192 select,
#   524288 = "any key" only (never tested on its own).
# Input: au.class already remapped by patchkeys.py (cF..cI = -1..-4, cJ -5, cK -6, cL -7).
#
# All edits keep instruction lengths and only jump to offsets that already carry a
# StackMap frame (checked below), so the CLDC StackMap stays valid.
import struct, sys

def u2(b, o): return struct.unpack('>H', b[o:o+2])[0]
def u4(b, o): return struct.unpack('>I', b[o:o+4])[0]

def parse_cp(d):
    p = 10; n = u2(d, 8); cp = [None] * n; i = 1
    while i < n:
        t = d[p]
        if t == 1: l = u2(d, p+1); cp[i] = d[p+3:p+3+l].decode('utf8', 'replace'); p += 3 + l
        elif t in (3, 4): p += 5
        elif t in (5, 6): p += 9; i += 1
        elif t in (7, 8, 16): p += 3
        elif t in (9, 10, 11, 12, 18): p += 5
        elif t == 15: p += 4
        else: raise SystemExit('cp tag %d' % t)
        i += 1
    return cp, p

def find_code(d, name, desc):
    cp, p = parse_cp(d)
    p += 6; p += 2 + 2 * u2(d, p)
    for _ in range(2):                       # fields, methods
        n = u2(d, p); p += 2; hits = []
        for _ in range(n):
            ni, di, ac = u2(d, p+2), u2(d, p+4), u2(d, p+6); p += 8
            for _ in range(ac):
                an, al = u2(d, p), u4(d, p+2)
                if cp[an] == 'Code' and cp[ni] == name and cp[di] == desc: hits.append(p)
                p += 6 + al
    assert len(hits) == 1, hits
    a = hits[0]; cl = u4(d, a+10); cs = a + 14
    # locate StackMap sub-attribute
    q = cs + cl; exl = u2(d, q); q += 2 + 8 * exl; na = u2(d, q); q += 2; frames = set()
    for _ in range(na):
        an, al = u2(d, q), u4(d, q+2)
        if cp[an] == 'StackMap':
            nf = u2(d, q+6); r = q + 8
            for _ in range(nf):
                off = u2(d, r); r += 2; frames.add(off)
                for _k in range(2):           # locals, stack
                    cnt = u2(d, r); r += 2
                    for _j in range(cnt):
                        tag = d[r]; r += 1
                        if tag in (7, 8): r += 2
        q += 6 + al
    return cs, cl, frames

def main(src, dst):
    d = bytearray(open(src, 'rb').read())
    cs, cl, frames = find_code(d, 'c', '(I)I')
    code = d[cs:cs+cl]
    def expect(off, bs):
        assert bytes(code[off:off+len(bs)]) == bytes(bs), (off, bytes(code[off:off+len(bs)]).hex(), bytes(bs).hex())
    # fire (cJ): never take the in-match "any key" path -> always 256 (0 while ac == 14)
    expect(50, [0x10, 8]); code[51] = 127
    # cK branch: 'ldc #34; ireturn' -> 'goto 327' (slot 'sipush 4096; ireturn')
    expect(174, [0x12, 34, 0xac]); assert 327 in frames
    code[174:177] = bytes([0xa7]) + struct.pack('>h', 327 - 174)
    # return slots: (offset, old bytes, new bytes)
    slots = {
        296: ([0x12, 34], [0x10, 1]),            # up
        303: ([0x12, 34], [0x10, 64]),           # left
        310: ([0x12, 34], [0x10, 2]),            # up-right
        313: ([0x12, 34], [0x10, 32]),           # down-left
        316: ([0x12, 34], [0x10, 4]),            # right
        331: ([0x12, 34], [0x10, 16]),           # down
        334: ([0x12, 34], [0x10, 8]),            # down-right
        299: ([0x11, 0x02, 0x00], [0x11, 0x00, 0x80]),   # 512  -> 128 up-left
        306: ([0x11, 0x04, 0x00], [0x11, 0x02, 0x00]),   # 1024 -> 512 long pass
        319: ([0x11, 0x08, 0x00], [0x11, 0x04, 0x00]),   # 2048 -> 1024 shoot
        323: ([0x11, 0x01, 0x00], [0x11, 0x08, 0x00]),   # 256  -> 2048 feint
        # 327: sipush 4096 kept (soft right via goto)
    }
    for off, (old, new) in slots.items():
        assert off in frames, off
        expect(off, old); code[off:off+len(new)] = bytes(new)
    # tableswitch at 189: pad to 4, default, low, high, offsets
    expect(189, [0xaa]); t = 189 + 1 + (-(189 + 1) % 4)
    low, high = struct.unpack('>ii', code[t+4:t+12]); assert (low, high) == (35, 57)
    target = {'#': 306, '*': 323, '0': 319, '1': 299, '2': 296, '3': 310, '4': 303,
              '5': 153, '6': 316, '7': 313, '8': 331, '9': 334}
    for ch, tgt in target.items():
        assert tgt in frames, (ch, tgt)
        k = ord(ch); o = t + 12 + 4 * (k - low)
        code[o:o+4] = struct.pack('>i', tgt - 189)
    d[cs:cs+cl] = code
    # swap softkey codes in au.<clinit>: cK = -7 (-> 4096 back), cL = -6 (-> 8192 select)
    old = bytes([0x10, 0xfa, 0xb3, 0x02, 0xa5, 0x10, 0xf9, 0xb3, 0x02, 0xa6])   # bipush -6 cK, bipush -7 cL
    new = bytes([0x10, 0xf9, 0xb3, 0x02, 0xa5, 0x10, 0xfa, 0xb3, 0x02, 0xa6])
    assert d.count(old) == 1, d.count(old); d = d.replace(old, new)
    open(dst, 'wb').write(d)
    print('ok; frames in c(I)I:', sorted(frames)[:40])

main(sys.argv[1], sys.argv[2])
