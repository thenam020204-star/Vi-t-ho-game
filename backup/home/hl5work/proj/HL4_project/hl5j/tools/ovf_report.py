# ovf_report.py <scratch dir> [out.tsv]: collect flagged texts of all ovf_<name> runs, decode, map to IDs
import sys, os, glob, json, collections
S = sys.argv[1]
W = '/home/claude/work/vi5'
dic = open(os.path.join(W, 'build/overlay/vi5/dict.txt'), encoding='utf-8').read().split('\n')
enc = json.load(open(os.path.join(W, 'build/enc_map.json')))
LEAD0, TRAIL0 = 0xC9, 0xA1
def decode(h):
    b = bytes.fromhex(h); out = []; i = 0
    while i < len(b):
        c = b[i]
        if c == 0: break
        if c >= LEAD0 and i + 1 < len(b) and b[i + 1] >= TRAIL0:
            k = (c - LEAD0) * 94 + (b[i + 1] - TRAIL0)
            out.append(dic[k] if k < len(dic) else '?'); i += 2; continue
        if c >= 0x80 and i + 1 < len(b):
            try: out.append(b[i:i + 2].decode('euc-kr'))
            except Exception: out.append('?')
            i += 2; continue
        out.append(chr(c)); i += 1
    return ''.join(out)
# longest encoded entries first for substring matching
encl = sorted(enc.items(), key=lambda kv: -len(kv[0]))
def ids_for(h):
    if h in enc: return [enc[h]]
    h0 = h.split('00')[0] if False else h
    res = []
    for k, v in encl:
        if len(k) >= 4 and k in h and v not in res:
            # keep only matches aligned to byte boundary
            if h.find(k) % 2 == 0: res.append(v)
        if len(res) >= 3: break
    return res
rows = []
for d in sorted(glob.glob(os.path.join(S, 'ovf_*'))):
    if d.endswith('_k') or not os.path.isdir(d): continue
    f = os.path.join(d, 'ovf.txt')
    if not os.path.exists(f): continue
    if os.path.exists(os.path.join(d, 'dict.txt')):
        dic[:] = open(os.path.join(d, 'dict.txt'), encoding='utf-8').read().split('\n')
        enc.clear(); enc.update(json.load(open(os.path.join(d, 'enc_map.json'))))
        encl[:] = sorted(enc.items(), key=lambda kv: -len(kv[0]))
    for l in open(f, encoding='utf-8'):
        c = l.rstrip('\n').split('\t')
        if len(c) < 7: continue
        n, t, why, key, room, ext, h = c
        rows.append((os.path.basename(d)[4:], n, why, key, room, ext, h, decode(h), ids_for(h)))
seen = collections.OrderedDict()
for r in rows:
    txt = r[7]; ids = r[8]
    k = (tuple(ids), txt) if ids else ('?', txt)
    if k in seen: seen[k]['where'].append(r[0] + '#' + r[1]); continue
    import re
    e = re.match(r'ext=(-?\d+),(-?\d+)-(-?\d+),(-?\d+)', r[5]).groups()
    seen[k] = dict(ids=ids, txt=txt, why=r[2], key=r[3], room=r[4][5:], w=int(e[2]) - int(e[0]) + 1, where=[r[0] + '#' + r[1]])
out = sys.argv[2] if len(sys.argv) > 2 else os.path.join(S, 'ovf_all.tsv')
with open(out, 'w', encoding='utf-8') as f:
    f.write('ID\tText\tWhy\tSite\tRoom\tTextW\tWhere\n')
    for v in seen.values():
        f.write('%s\t%s\t%s\t%s\t%s\t%d\t%s\n' % (','.join(v['ids']) or '?', v['txt'].replace('\n', '|'), v['why'], v['key'], v['room'], v['w'], ' '.join(v['where'])))
print(len(rows), 'flags ->', len(seen), 'unique;', out)
