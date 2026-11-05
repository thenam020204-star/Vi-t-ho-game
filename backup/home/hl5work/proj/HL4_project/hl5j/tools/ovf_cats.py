# ovf_cats.py <scratch>: which text categories (file group, field) are drawn at which sites, widths, flags
import sys, os, glob, json, re, collections
sys.path.insert(0, os.path.dirname(__file__))
S = sys.argv[1]
W = '/home/claude/work/vi5'
sys.argv = [sys.argv[0], S, os.path.join(S, 'ovf_all.tsv')]
import importlib.util
spec = importlib.util.spec_from_file_location('rep', os.path.join(os.path.dirname(__file__), 'ovf_report.py'))
cat = json.load(open(os.path.join(W, 'catalog5.json')))
ids = json.load(open(os.path.join(W, 'ids5.json')))
# field index of each dat entry within its record
byrec = collections.defaultdict(list)
for e in cat:
    if e['kind'] == 'dat': byrec[(e['file'], e['rec'])].append(e)
for k, es in byrec.items():
    for i, e in enumerate(sorted(es, key=lambda e: e['off'])): e['field'] = i
def group(e):
    f = os.path.basename(e['file'])
    g = re.sub(r'_?\d+', '#', f[:-4]) if e['kind'] != 'scn' else 'scn'
    return '%s.f%s' % (g, e.get('field', 0)) if e['kind'] == 'dat' else g
id2groups = collections.defaultdict(set)
for e in cat:
    i = ids.get(e['src'])
    if i: id2groups[i].add(group(e))
json.dump({k: sorted(v) for k, v in id2groups.items()}, open(os.path.join(S, 'id2groups.json'), 'w'))
print('groups', len(set(g for v in id2groups.values() for g in v)))
