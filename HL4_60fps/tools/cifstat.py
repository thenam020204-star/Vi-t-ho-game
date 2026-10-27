import os, sys
def parse(d, mode):
    p = 1 if d[0] <= 0 or d[0] > 127 else (d[0] if d[0] < 128 else 0) + 1
    b0 = d[0] - 256 if d[0] > 127 else d[0]
    p = 1 if b0 <= 0 else b0 + 1
    nA = d[p]; p += 1
    anims = []
    for a in range(nA):
        n4 = d[p] - 256 if d[p] > 127 else d[p]; p += 1
        dirs = []
        for di in range(max(n4, 0)):
            nf = d[p]; p += 1
            frames = []
            for f in range(nf):
                nc = d[p] - 256 if d[p] > 127 else d[p]; p += 1
                cells = []
                for c in range(max(nc, 0)):
                    ext = d[p] & 0x80
                    ln = (10 if ext else 5) if mode == 2 else (9 if ext else 4)
                    cells.append(bytes(d[p:p+ln])); p += ln
                frames.append(cells)
            dirs.append(frames)
        anims.append(dirs)
    return anims
def pos(c, mode):
    if mode == 2:
        a, b, e = c[2], c[3], c[4]
        x = (a << 4 | b >> 4); x = x - 4096 if x & 0x800 else x
        y = (e | (b & 15) << 8); y = y - 4096 if y & 0x800 else y
        return x, y
    s = lambda v: v - 256 if v > 127 else v
    return s(c[2]), s(c[3])
root = sys.argv[1]
tot = {'pairs':0,'same':0,'match_moved':0,'match_static':0,'mismatch':0,'samecount':0}
maxd = []
for r, _, fs in os.walk(root):
    for f in fs:
        if not f.endswith('_CIF'): continue
        path = os.path.join(r, f); d = open(path,'rb').read()
        mode = 2 if '_H_001' in f or '_H_002' in f or '_H_003' in f or '_H_004' in f or 'TOUCH' in r else 1
        try: anims = parse(d, mode)
        except Exception as e: print('ERR', path, e); continue
        for dirs in anims:
            for frames in dirs:
                for i in range(1, len(frames)):
                    A, B = frames[i-1], frames[i]; tot['pairs'] += 1
                    if A == B: tot['same'] += 1; continue
                    if len(A) == len(B):
                        tot['samecount'] += 1
                        if all(a[:2] == b[:2] and (a[0] & 0x80) == (b[0] & 0x80) for a, b in zip(A, B)):
                            dd = max(max(abs(pos(a,mode)[0]-pos(b,mode)[0]), abs(pos(a,mode)[1]-pos(b,mode)[1])) for a, b in zip(A, B))
                            if dd: tot['match_moved'] += 1; maxd.append(dd)
                            else: tot['match_static'] += 1
                            continue
                    tot['mismatch'] += 1
print(tot)
maxd.sort(); print('disp quantiles', [maxd[int(len(maxd)*q)] for q in (0.1,0.5,0.9,0.99)] if maxd else None)
