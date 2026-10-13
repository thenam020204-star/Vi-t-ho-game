"""Doc/ghi anim.bin (CGame.LoadAnimationData, SUPERBIG_DATA: module/frame la short big-endian).
Module = (y, x, w, h) tren anh; muc trong khung = (module, dy, dx, co lat).
Moi anim: u8 id | u16 nModule | nModule*4 short (x,y,w,h) | u8 nFrame | nFrame*u8 (so FModule) | sum*4 short
          | u8 nAction | nAction*u8 (so khung) | bytes frames | bytes duration | bytes pos(2x). Ket thuc: het file hoac id 0xFF."""
import struct, sys
def parse(d):
    p = 0; anims = []
    while p < len(d):
        aid = d[p]; p += 1
        if aid == 0xFF: return anims, b'\xff' + d[p:]
        nm = struct.unpack('>H', d[p:p+2])[0]; p += 2
        mods = [struct.unpack('>4h', d[p+8*i:p+8*i+8]) for i in range(nm)]; p += 8 * nm
        nf = d[p]; p += 1
        fcnt = list(d[p:p+nf]); p += nf
        tot = sum(fcnt) * 4
        fdat = list(struct.unpack('>%dh' % tot, d[p:p+2*tot])); p += 2 * tot
        na = d[p]; p += 1
        acnt = list(d[p:p+na]); p += na
        s = sum(acnt)
        afr = d[p:p+s]; p += s
        adur = d[p:p+s]; p += s
        apos = d[p:p+2*s]; p += 2 * s
        frames = []; o = 0
        for c in fcnt:
            frames.append([tuple(fdat[o+4*k:o+4*k+4]) for k in range(c)]); o += 4 * c
        anims.append(dict(id=aid, mods=mods, frames=frames, acnt=acnt, afr=afr, adur=adur, apos=apos))
    return anims, d[p:]
def write(anims, tail=b''):
    o = bytearray()
    for a in anims:
        o.append(a['id']); o += struct.pack('>H', len(a['mods']))
        for m in a['mods']: o += struct.pack('>4h', *m)
        o.append(len(a['frames'])); o += bytes(len(f) for f in a['frames'])
        for f in a['frames']:
            for e in f: o += struct.pack('>4h', *e)
        o.append(len(a['acnt'])); o += bytes(a['acnt']); o += a['afr'] + a['adur'] + a['apos']
    return bytes(o) + tail
if __name__ == '__main__':
    d = open(sys.argv[1], 'rb').read()
    an, tail = parse(d)
    print(len(an), 'anims, tail', len(tail), 'roundtrip', write(an, tail) == d)
    for a in an:
        if a['id'] == int(sys.argv[2]):
            for i, m in enumerate(a['mods']): print('M', i, m)
            for i, f in enumerate(a['frames']): print('F', i, f)
