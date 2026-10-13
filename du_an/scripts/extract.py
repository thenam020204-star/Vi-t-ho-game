"""Trich string.pak -> work/text_can_dich.tsv (id, file, khoa, goc, dich, ghichu, gioihan).
id = t<trunk>_<idx>; khoa = ten hang STR_* trong Const.java (ngu canh)."""
import sys, re, collections
sys.path.insert(0, 'scripts')
import pak
tr, _ = pak.read_pak(open('jar_x/string.pak', 'rb').read())
const = open('gsrc/Const.java', encoding='utf-8').read()
names = re.findall(r'static final int (STR_\w+) *= *(\d+);', const)
sys_names = {}
scenes = collections.OrderedDict()
for n, v in names:
    v = int(v)
    m = re.match(r'STR_SCENE_(\w+?)_SCRIPT(\d+)$', n)
    if m:
        scenes.setdefault(m.group(1), []).append(n)
    elif n.startswith('STR_SYSTEM_'):
        sys_names[v] = n[11:]
scene_list = list(scenes.items())
rows = []
# gan ten canh cho cac trunk khong rong theo thu tu
tmap = {}
si = 0
for t in range(1, len(tr)):
    if pak.read_strings(tr[t])[0]:
        tmap[t] = scene_list[si]; si += 1
for t, data in enumerate(tr):
    strs, rest = pak.read_strings(data)
    assert not rest
    if t == 0:
        for i, s in enumerate(strs):
            k = sys_names.get(i, '')
            rows.append(('t0_%d' % i, 'string.pak', k, s))
    else:
        sc = tmap.get(t, ('?', []))
        for i, s in enumerate(strs):
            k = sc[1][i] if i < len(sc[1]) else ''
            rows.append(('t%d_%d' % (t, i), 'string.pak', k[4:], s))
with open(sys.argv[1], 'w', encoding='utf-8') as f:
    f.write('id\tfile\tkhoa\tgoc\tdich\tghichu\tgioihan\n')
    for r in rows:
        f.write('\t'.join([r[0], r[1], r[2], r[3], '', '', '']) + '\n')
print(len(rows), 'dong;', 'scene trunks', len(tr) - 1, 'scene names', len(scene_list))
for t in range(1, len(tr)):
    s, _ = pak.read_strings(tr[t])
    sc = tmap.get(t, ('?', []))
    if s and len(s) != len(sc[1]): print('LECH', t, sc[0], len(s), len(sc[1]))
