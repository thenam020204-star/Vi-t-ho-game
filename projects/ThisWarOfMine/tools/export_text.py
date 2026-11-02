"""Xuất text tiếng Anh (slot 1) từ khối localization 2f6496cc ra TSV cho dp.
python3 export_text.py <2f6496cc.bin> <thư mục ra>"""
import sys, os
sys.path.insert(0, os.path.dirname(__file__)); import locfmt
EN = 1
T2 = ('UI', 'Items', 'CharacterParameters', 'CharacterSkills', 'Gamepad', 'GameFlow', 'TakingCareOf',
      'DLC', 'Events', 'TEST', 'DEBUGTEXT', 'DEBUGTEXT_FINAL', 'Credits', 'Credits_Title', 'CreditsStories',
      'LocationDescriptions')
SKIP = ('Names',)
def esc(s):
    assert '\\' not in s and '\t' not in s and '\r' not in s
    return s.replace('\n', '\\n')
L = locfmt.parse(open(sys.argv[1], 'rb').read()); out = sys.argv[2]
f = {t: open(os.path.join(out, t + '.tsv'), 'w', encoding='utf-8') for t in ('t1', 't2', 'giu_nguyen')}
for t in f: f[t].write('id\tsrc\tdst\tctx\n')
for i, r in enumerate(L['recs']):
    k = L['keys'][i]; s = r['S'][EN]; top = k.split('/')[0]
    t = 'giu_nguyen' if (top in SKIP or not s.strip()) else ('t2' if top in T2 else 't1')
    f[t].write(f'{k}\t{esc(s)}\t\t{k}\n')
