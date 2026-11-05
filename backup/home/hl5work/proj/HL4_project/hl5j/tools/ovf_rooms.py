# ovf_rooms.py <scratch>: per text category, the display sites seen in the VI runs and their free room
import sys, os, glob, json, collections, re
S = sys.argv[1]
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
W = '/home/claude/work/vi5'
dic = open(os.path.join(W, 'build/overlay/vi5/dict.txt'), encoding='utf-8').read().split('\n')
enc = json.load(open(os.path.join(W, 'build/enc_map.json')))
g = json.load(open(os.path.join(S, 'id2groups.json')))
encl = sorted(enc.items(), key=lambda kv: -len(kv[0]))
def ids_for(h):
    if h in enc: return [enc[h]], True
    res = []
    for k, v in encl:
        if len(k) >= 8 and k in h and h.find(k) % 2 == 0 and v not in res: res.append(v)
        if len(res) >= 4: break
    return res, False
sites = collections.defaultdict(lambda: collections.defaultdict(list))  # group -> key -> [(avail, textw, exact, id)]
for d in sorted(glob.glob(os.path.join(S, 'ovf_*'))):
    if d.endswith('_k') or not os.path.exists(os.path.join(d, 'draws.txt')): continue
    for l in open(os.path.join(d, 'draws.txt'), encoding='utf-8'):
        c = l.rstrip('\n').split('\t')
        if len(c) < 7: continue
        key, room, ext, clipped, cover, t, h = c
        site, x, y, al = key.split(':'); x = int(x); al = int(al)
        L, R = map(int, room.split(','))
        e = list(map(int, ext.split(',')))
        avail = R - x if al == 0 else (2 * min(x - L, R - x) if al == 1 else x - L)
        idl, exact = ids_for(h)
        for i in idl:
            for gr in g.get(i, []): sites[gr][key].append((avail, e[2] - e[0] + 1, exact, i))
out = {}
for gr in sorted(sites):
    print(gr)
    for key, v in sites[gr].items():
        av = min(a for a, _, _, _ in v); mw = max(w for _, w, _, _ in v)
        print('   %-22s avail=%4d maxTextW=%4d n=%d %s' % (key, av, mw, len(v), v[0][3]))
