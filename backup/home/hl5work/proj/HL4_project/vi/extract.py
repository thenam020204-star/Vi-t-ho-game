"""Extract every Korean string of Heroes Lore 4 (LGT + KTF) into catalog.json.

catalog.json: list of entries in translation order:
  {"id": n, "ko": text, "kind": ..., "occ": [{"plat","src","speaker"?}], "cap": min byte capacity or null}
"""
import json, os, re, glob, sys
from fmt import *

W = '/home/claude/work'
RAW = {'lgt': W + '/lgtjar/res', 'ktf': W + '/ktfjar'}
DEC = {'lgt': W + '/vi/dec/lgt', 'ktf': W + '/vi/dec/ktf'}

entries = {}   # ko -> entry
order = []


def add(ko_bytes, kind, plat, src, cap=None, **ctx):
    if not HANGUL.search(ko_bytes):
        return
    ko = ko_bytes.decode('euc-kr')
    e = entries.get(ko)
    if e is None:
        e = entries[ko] = {'ko': ko, 'kind': kind, 'occ': [], 'cap': None}
        order.append(ko)
    o = {'plat': plat, 'src': src}
    o.update(ctx)
    e['occ'].append(o)
    if cap is not None:
        e['cap'] = cap if e['cap'] is None else min(e['cap'], cap)


def names(plat):
    return [s.decode('euc-kr') for s in names_parse(open(RAW[plat] + '/NPC/NAME_DAT', 'rb').read())]


def do_scn(plat, prefix):
    nm = names(plat)
    for f in sorted(glob.glob(DEC[plat] + '/MAP/SC/%s*_scn' % prefix)):
        d = open(f, 'rb').read()
        r = scn_find(d)
        if not r:
            continue
        p, n, strs = r
        sp = scn_speakers(d, p, n)
        base = os.path.basename(f)
        for i, s in enumerate(strs):
            kind = 'mapname' if i == 0 else 'dialog'
            who = nm[sp[i]] if i in sp and sp[i] < len(nm) else None
            add(s, kind, plat, 'scn:%s:%d' % (base, i), 254, speaker=who)


def do_tdf(plat, rel, kind):
    path = RAW[plat] + '/' + rel
    if not os.path.exists(path):
        return
    for i, s in enumerate(tdf_parse(open(path, 'rb').read())):
        add(s, kind, plat, 'tdf:%s:%d' % (rel, i))


def do_tip(plat):
    strs, _ = tip_parse(open(RAW[plat] + '/tdf/_tdf_TIP', 'rb').read())
    for i, s in enumerate(strs):
        add(s, 'tip', plat, 'tip:%d' % i, 254)


def do_names(plat):
    for i, s in enumerate(names_parse(open(RAW[plat] + '/NPC/NAME_DAT', 'rb').read())):
        add(s, 'name', plat, 'name:%d' % i)


FAMILIES = [
    ('quest', 'NPC/_QUEST_*_DAT'), ('item', 'ITM/DAT/_ITM_[0-9][0-9]_DAT'), ('item', 'ITM/DAT/_ITM_?_SD'),
    ('item', 'ITM/DAT/_ITM_OPTION'), ('item', 'ITM/DAT/_ITM_SET_OPTION'), ('enemy', 'E/_ESDAT_*'),
    ('enemy', 'E/_BSDAT_*'), ('combine', 'NPC/_NPCUI_COMBINE_DAT_*'), ('guardian', 'NPC/NPCUI_GUARDIANSHOP_DAT'),
    ('skill', 'HDAT/_H_S[0-9]*'), ('summon', 'HDAT/_H_SS'), ('hero', 'HDAT/_H_BH'),
]


def do_families(plat):
    for kind, pat in FAMILIES:
        for f in sorted(glob.glob(DEC[plat] + '/' + pat)):
            rel = os.path.relpath(f, DEC[plat])
            recs, _ = records(open(f, 'rb').read())
            for ri, (p, body) in enumerate(recs):
                for (o, s) in strings_in(body):
                    add(s, kind, plat, 'rec:%s:%d:%d' % (rel, ri, o))


def do_maps(plat):
    for f in sorted(glob.glob(RAW[plat] + '/MAP/M/_MAP_M_*')):
        d = open(f, 'rb').read()
        p = 3 if d[0] == 1 else 5
        for k in range(2):
            n = d[p]
            s = d[p + 1:p + 1 + n]
            if is_text(s):
                add(s, 'mapname', plat, 'map:%s:%d' % (os.path.basename(f), k))
            p += 1 + n


def do_bin(plat):
    js = json.load(open(W + '/vi/data/%s_binstr.json' % plat))
    for s in js:
        ko = s['ko']
        b = ko.encode('euc-kr')
        # drop code false positives: keep strings made of hangul/ascii words
        if len(HANGUL.findall(b)) < 1 or re.search(r'[^가-힣 -~…·]', ko):
            continue
        if len(b) < 4 and not s.get('refs'):
            continue
        add(b, 'bin', plat, 'bin:%x' % s['addr'], s['slot'] - 1, refs=len(s.get('refs', [])))


for plat in ('lgt', 'ktf'):
    do_scn(plat, 'e')
for plat in ('lgt', 'ktf'):
    do_scn(plat, 'n')
for plat in ('lgt', 'ktf'):
    do_names(plat)
    for rel, kind in [('tdf/_tdf_MENU_TXT', 'menu'), ('GMenu/_GMENU_TXT', 'menu'), ('GMenu/_GMENU_SUB_TXT', 'menu'),
                      ('GMenu/_NPCUI_TXT', 'menu'), ('NPC/_EVENT_POP_TXT', 'menu'), ('tdf/_tdf_FREE_TXT', 'menu'),
                      ('tdf/_tdf_UseInfo_TXT', 'menu'), ('tdf/_tdf_HELP', 'help')]:
        do_tdf(plat, rel, kind)
    do_tip(plat)
    do_families(plat)
    do_maps(plat)
    do_bin(plat)

cat = []
for i, ko in enumerate(order):
    e = entries[ko]
    e['id'] = i
    cat.append(e)
json.dump(cat, open(W + '/vi/catalog.json', 'w'), ensure_ascii=False, indent=1)
import collections
k = collections.Counter(e['kind'] for e in cat)
syl = collections.Counter()
for e in cat:
    syl[e['kind']] += len(HANGUL.findall(e['ko'].encode('euc-kr')))
print(len(cat), 'unique strings')
for kind in k:
    print('  %-9s %5d strings %6d syllables' % (kind, k[kind], syl[kind]))
print('total syllables', sum(syl.values()))
