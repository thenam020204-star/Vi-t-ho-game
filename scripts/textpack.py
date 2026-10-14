#!/usr/bin/env python3
"""Text EA SDK: bang cau hinh (resource df #16, parse o.a(byte[])) + file /t_<lang>.
cfg: u1 ver=1 | u1 mode | u3 cpMask | u2 nInline | u1 nLang | u1 nGrp | u1 nChunk
     nLang x { 5B name, u1 codepage, u1 g, u2 inlineOff[nInline+1] } | inline bytes (UTF-16BE)
     nChunk x { u1 flags|grp, u2 count, u2 bytes[nLang] }
t_<lang> (mode 0): chunk 1..n lien tiep: u2 off[count+1] (byte, tuong doi) + bytes.
codepage 0 = UTF-16BE; khac 0 = 1 byte, >=0x80 tra /charmap trang (so bit cpMask duoi cp) x 256B."""
import struct, zipfile, sys, csv

def u2(b, p): return (b[p] << 8) | b[p+1]

def parse_cfg(b):
    c = dict(ver=b[0], mode=b[1], cpmask=(b[2] << 16) | (b[3] << 8) | b[4], ninl=u2(b, 5), nlang=b[7], ngrp=b[8], nchunk=b[9])
    p = 10; s3 = c['ninl'] + 1; langs = []
    for _ in range(c['nlang']):
        name = b[p:p+5]; cp = b[p+5]; g = b[p+6]; p += 7
        offs = [u2(b, p + 2*k) for k in range(s3)]; p += 2*s3
        langs.append(dict(name=name, cp=cp, g=g, offs=offs))
    n = langs[-1]['offs'][-1]; inl = b[p:p+n]; p += n
    for L in langs:
        o = L['offs']; L['inline'] = [inl[o[k]:o[k+1]].decode('utf-16-be') for k in range(c['ninl'])]
    chunks = []
    for _ in range(c['nchunk']):
        fl = b[p]; cnt = u2(b, p+1); p += 3
        lens = [u2(b, p + 2*k) for k in range(c['nlang'])]; p += 2*c['nlang']
        chunks.append(dict(flags=fl, count=cnt, lens=lens))
    assert p == len(b)
    c['langs'] = langs; c['chunks'] = chunks
    return c

def build_cfg(c):
    out = bytes([c['ver'], c['mode'], (c['cpmask'] >> 16) & 255, (c['cpmask'] >> 8) & 255, c['cpmask'] & 255])
    out += struct.pack('>H', c['ninl']) + bytes([c['nlang'], c['ngrp'], c['nchunk']])
    inl = b''; tabs = b''
    for L in c['langs']:
        offs = []
        for s in L['inline']:
            offs.append(len(inl)); inl += s.encode('utf-16-be')
        offs.append(len(inl))
        tabs += L['name'] + bytes([L['cp'], L['g']]) + struct.pack('>%dH' % len(offs), *offs)
    out += tabs + inl
    for ch in c['chunks']:
        out += bytes([ch['flags']]) + struct.pack('>H', ch['count']) + struct.pack('>%dH' % len(ch['lens']), *ch['lens'])
    return out

def charmap_page(cfg, charmap, cp):
    n = sum(1 for i in range(1, cp) if cfg['cpmask'] & (1 << i))
    return [u2(charmap, (n << 8) + 2*i) for i in range(128)]

def decode_t(cfg, li, data, charmap):
    cp = cfg['langs'][li]['cp']
    page = charmap_page(cfg, charmap, cp) if cp > 1 else None
    p = 0; res = []
    for ci, ch in enumerate(cfg['chunks']):
        if (ch['flags'] & 0x40) and li != 0: continue
        n = ch['count']
        offs = [u2(data, p + 2*k) for k in range(n + 1)]; p += 2*(n + 1)
        blob = data[p:p + ch['lens'][li]]; p += ch['lens'][li]
        u16 = cp == 0 or (ch['flags'] & 0xC0)
        strs = []
        for k in range(n):
            s = blob[offs[k]:offs[k+1]]
            if u16: strs.append(s.decode('utf-16-be'))
            else: strs.append(''.join(chr(x) if x < 128 or page is None else chr(page[x - 128]) for x in s))
        assert offs[n] == len(blob), (ci, offs[n], len(blob))
        res.append(strs)
    assert p == len(data), (p, len(data))
    return res

def encode_t(cfg, li, chunks_strs, charmap):
    """tra ve (bytes file, danh sach do dai byte tung chunk)."""
    cp = cfg['langs'][li]['cp']
    page = charmap_page(cfg, charmap, cp) if cp > 1 else None
    inv = {u: i + 128 for i, u in enumerate(page)} if page else {}
    out = b''; lens = []
    for ch, strs in zip(cfg['chunks'], chunks_strs):
        u16 = cp == 0 or (ch['flags'] & 0xC0)
        blob = b''; offs = []
        for s in strs:
            offs.append(len(blob))
            if u16: blob += s.encode('utf-16-be')
            else: blob += bytes(ord(x) if ord(x) < 128 else inv[ord(x)] for x in s)
        offs.append(len(blob))
        assert len(blob) < 65536
        out += struct.pack('>%dH' % len(offs), *offs) + blob; lens.append(len(blob))
    return out, lens

if __name__ == '__main__':
    import os
    sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
    import dfpack
    jar = sys.argv[1]
    z = zipfile.ZipFile(jar)
    ix = dfpack.parse_index(z.read('index'))
    k, off, ln = dfpack.locate(ix, 16)
    cfg = parse_cfg(z.read('df%d_0' % k)[off:off+ln])
    assert build_cfg(cfg) == z.read('df%d_0' % k)[off:off+ln]; print('cfg round-trip OK')
    cm = z.read('charmap'); allt = []
    for li, L in enumerate(cfg['langs']):
        name = L['name'].decode().strip(); raw = z.read('t_' + name)
        t = decode_t(cfg, li, raw, cm)
        enc, lens = encode_t(cfg, li, t, cm)
        assert enc == raw and lens == [c['lens'][li] for c in cfg['chunks']], name
        print('t_%s round-trip OK (%d chuoi)' % (name, sum(map(len, t))))
        allt.append(t)
    if len(sys.argv) > 2:
        with open(sys.argv[2], 'w', newline='') as fh:
            w = csv.writer(fh, delimiter='\t', lineterminator='\n')
            w.writerow(['id', 'pt', 'pl', 'ru', 'cs', 'tr'])
            names = [L['name'].decode().strip() for L in cfg['langs']]
            for i in range(3):
                w.writerow(['inl:%d' % i] + [cfg['langs'][names.index(n)]['inline'][i] for n in ('pt', 'pl', 'ru', 'cs', 'tr')])
            for ci in range(len(cfg['chunks'])):
                for si in range(cfg['chunks'][ci]['count']):
                    w.writerow(['%d:%d' % (ci + 1, si)] + [allt[names.index(n)][ci][si] for n in ('pt', 'pl', 'ru', 'cs', 'tr')])
