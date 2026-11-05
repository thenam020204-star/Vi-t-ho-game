"""Split catalog.json into translation chunks tr/chunks/*.json (with context and byte caps)."""
import json, os, re, glob, collections
from fmt import *

W = '/home/claude/work/vi'
cat = json.load(open(W + '/catalog.json', encoding='utf-8'))
DEC = {'lgt': W + '/dec/lgt', 'ktf': W + '/dec/ktf'}

# ---- caps for record fields: max original length per (family pattern, ordinal)
FAMS = [('quest', 'NPC/_QUEST_*_DAT'), ('itemdat', 'ITM/DAT/_ITM_[0-9][0-9]_DAT'), ('itemsd', 'ITM/DAT/_ITM_?_SD'),
        ('opt', 'ITM/DAT/_ITM_OPTION'), ('setopt', 'ITM/DAT/_ITM_SET_OPTION'), ('es', 'E/_ESDAT_*'),
        ('bs', 'E/_BSDAT_*'), ('comb', 'NPC/_NPCUI_COMBINE_DAT_*'), ('guard', 'NPC/NPCUI_GUARDIANSHOP_DAT'),
        ('skill', 'HDAT/_H_S[0-9]*'), ('ss', 'HDAT/_H_SS'), ('bh', 'HDAT/_H_BH')]
fam_of = {}
ordmax = collections.defaultdict(int)
ord_of = {}
for fam, pat in FAMS:
    for plat in ('lgt', 'ktf'):
        for f in glob.glob(DEC[plat] + '/' + pat):
            rel = os.path.relpath(f, DEC[plat])
            fam_of[rel] = fam
            recs, _ = records(open(f, 'rb').read())
            for ri, (p, body) in enumerate(recs):
                for k, (o, s) in enumerate(strings_in(body)):
                    ordmax[(fam, k)] = max(ordmax[(fam, k)], len(s))
                    ord_of[(plat, rel, ri, o)] = k

def cap_of(e):
    caps = []
    for o in e['occ']:
        src = o['src']
        if src.startswith('rec:'):
            _, rel, ri, off = src.split(':')
            fam = fam_of[rel]
            k = ord_of[(o['plat'], rel, int(ri), int(off))]
            caps.append(ordmax[(fam, k)])
        elif src.startswith('scn:') or src.startswith('tip:'):
            caps.append(254)
        elif src.startswith('bin:'):
            caps.append(e['cap'] if e['cap'] is not None else len(e['ko'].encode('euc-kr')))
        elif src.startswith('name:'):
            caps.append(max(12, len(e['ko'].encode('euc-kr'))))
        elif src.startswith('map:'):
            caps.append(max(14, len(e['ko'].encode('euc-kr'))))
    return min(caps) if caps else None

JUNK = re.compile(r'[^가-힣ㄱ-ㆎ -~…·]')
def row(e):
    o = e['occ'][0]
    r = {'id': e['id'], 'ko': e['ko'], 'kind': e['kind'], 'src': o['src']}
    if o.get('speaker'):
        r['speaker'] = o['speaker']
    c = cap_of(e)
    if c:
        r['cap'] = c
    if JUNK.search(e['ko']):
        r['note'] = 'nghi rác/không hiển thị: giữ nguyên nếu vô nghĩa'
    return r

os.makedirs(W + '/tr/chunks', exist_ok=True)
groups = collections.OrderedDict()
def put(name, e):
    groups.setdefault(name, []).append(row(e))

dialog = [e for e in cat if e['kind'] == 'dialog']
for e in cat:
    k = e['kind']
    if k in ('name', 'mapname', 'enemy', 'summon', 'guardian', 'combine'):
        put('t1_names', e)
    elif k == 'item':
        put('t2_items', e)
    elif k in ('skill', 'quest'):
        put('t3_skill_quest', e)
    elif k in ('menu', 'help', 'tip', 'bin'):
        put('t4_ui', e)
# dialog chunks of ~380 lines, split only at scene boundaries
cur, n, idx = [], 0, 0
def flush():
    global cur, idx
    if cur:
        groups['d%02d' % idx] = [row(e) for e in cur]
        idx += 1
        cur = []
last_file = None
for e in dialog:
    f = e['occ'][0]['src'].split(':')[1]
    if len(cur) >= 380 and f != last_file:
        flush()
    cur.append(e)
    last_file = f
flush()
for name, rows in groups.items():
    json.dump(rows, open(W + '/tr/chunks/%s.json' % name, 'w', encoding='utf-8'), ensure_ascii=False, indent=0)
    print(name, len(rows), rows[0]['src'], '->', rows[-1]['src'])
