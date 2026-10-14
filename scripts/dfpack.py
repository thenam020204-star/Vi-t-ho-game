#!/usr/bin/env python3
"""Doc/ghi kho resource EA SDK (/index + /dfK_0), theo d.class (d.b(), d.a(int))."""
import sys, zipfile, os, struct

def rd(b, p, n):
    v = 0
    for i in range(n): v |= b[p+i] << (8*i)
    return v, p+n

def parse_index(idx):
    p = 1
    na, p = rd(idx, p, 2); osz, p = rd(idx, p, 1); nv, p = rd(idx, p, 1)
    arch = []
    for _ in range(na):
        last, p = rd(idx, p, 2)
        offs = []
        for _ in range(nv+1):
            o, p = rd(idx, p, osz); offs.append(o)
        tail, p = rd(idx, p, osz)
        arch.append((last, offs, tail))
    nres, p = rd(idx, p, 2)
    mb = (nv+7)//8
    res = []
    for _ in range(nres):
        m, p = rd(idx, p, mb)
        base, p = rd(idx, p, osz)
        vs = []
        for k in range(nv):
            if m & (1 << k):
                o, p = rd(idx, p, osz); vs.append((k, o))
        res.append((base, vs))
    assert p == len(idx), (p, len(idx))
    return dict(na=na, osz=osz, nv=nv, arch=arch, res=res)

def locate(ix, n):
    """Mo phong d.a(int): -> (archive, offset, length)."""
    a, res = ix['arch'], ix['res']
    off = res[n][0]
    k = 0
    for i in range(len(a)):
        if a[i][0] >= n and i > 0 and a[i-1][0] < n:
            k = i; break
    ln = 0
    if n == a[k][0]:
        ln = a[k][1][0] - off
    elif n+1 < len(res):
        ln = res[n+1][0] - off
    return k, off, ln

if __name__ == '__main__':
    jar, out = sys.argv[1], sys.argv[2]
    z = zipfile.ZipFile(jar)
    ix = parse_index(z.read('index'))
    print('archives', ix['na'], 'offsize', ix['osz'], 'variants', ix['nv'], 'res', len(ix['res']))
    for i, a in enumerate(ix['arch']): print(' arch', i, a)
    os.makedirs(out, exist_ok=True)
    dfs = {}
    for n in range(len(ix['res'])):
        k, off, ln = locate(ix, n)
        d = dfs.setdefault(k, z.read('df%d_0' % k))
        blob = d[off:off+ln]
        ext = 'png' if blob[:4] == b'\x89PNG' else 'bin'
        open(os.path.join(out, '%03d_df%d.%s' % (n, k, ext)), 'wb').write(blob)
        print(n, 'df%d' % k, off, ln, ext, blob[:12].hex(), 'variants', ix['res'][n][1])

def wr(v, n): return bytes((v >> (8*i)) & 255 for i in range(n))

def extract_all(z):
    ix = parse_index(z.read('index'))
    dfs = {}; blobs = []
    for n in range(len(ix['res'])):
        k, off, ln = locate(ix, n)
        d = dfs.setdefault(k, z.read('df%d_0' % k))
        blobs.append(d[off:off+ln])
    return ix, blobs

def rebuild(ix, blobs, idx_head):
    """Ghi lai index + df<k>_0. Giu nguyen phan chia resource theo kho (last index moi kho)."""
    assert all(not v for _, v in ix['res']), 'chua ho tro bien the palette trong index'
    osz, nv = ix['osz'], ix['nv']
    out_idx = bytes([idx_head]) + wr(ix['na'], 2) + wr(osz, 1) + wr(nv, 1)
    dfs = {}; offs = []; first = 0
    for k, (last, aoffs, tail) in enumerate(ix['arch']):
        data = b''
        for n in range(first, last + 1):
            offs.append(len(data)); data += blobs[n]
        dfs[k] = data
        assert len(data) < (1 << (8*osz))
        out_idx += wr(last, 2) + wr(len(data), osz) + wr(0, osz) * nv + wr(tail, osz)
        first = last + 1
    out_idx += wr(len(blobs), 2)
    mb = (nv + 7) // 8
    for o in offs:
        out_idx += wr(0, mb) + wr(o, osz)
    return out_idx, dfs
