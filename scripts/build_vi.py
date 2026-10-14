#!/usr/bin/env python3
"""Dung jar Viet hoa (chua preverify class moi) tu jar goc.
python3 -I scripts/build_vi.py <goc.jar> <text_dich.tsv> <font_chunk.bin> <font_atlas.png> <classes_dir> <logo.png> <out_raw.jar>
- slot ngon ngu 0 ('pt') -> 'vi', codepage 0 (UTF-16BE), file /t_pt -> /t_vi
- font chunk res 9/10/11 + atlas res 0/4/7 (kho df) thay bang font 17px
- MIDlet-1 -> NampMIDlet (splash), them Force-Locale: vi"""
import sys, os, csv, zipfile
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import dfpack, textpack

goc, tsv, fchunk, fatlas, cdir, logo, out = sys.argv[1:8]
z = zipfile.ZipFile(goc)
ix, blobs = dfpack.extract_all(z)
cfg = textpack.parse_cfg(blobs[16])
cm = z.read('charmap')
L0 = cfg['langs'][0]; assert L0['name'] == b'pt   '
t_old = textpack.decode_t(cfg, 0, z.read('t_pt'), cm)

tr = {}
for r in csv.reader(open(tsv, encoding='utf-8'), delimiter='\t'):
    if r[0] == 'id': continue
    assert r[2], ('chua dich', r[0])
    tr[r[0]] = r[2]
strs = [[tr['%d:%d' % (ci + 1, si)] for si in range(len(t_old[ci]))] for ci in range(len(t_old))]
assert sum(map(len, strs)) == len(tr) - 3
L0['name'] = b'vi   '; L0['cp'] = 0
L0['inline'] = [tr['inl:0'], tr['inl:1'], tr['inl:2']]
data, lens = textpack.encode_t(cfg, 0, strs, cm)
for ch, n in zip(cfg['chunks'], lens): ch['lens'][0] = n
cfgb = textpack.build_cfg(cfg)
# tu kiem: doc lai dung nhu engine
c2 = textpack.parse_cfg(cfgb)
assert textpack.decode_t(c2, 0, data, cm) == strs and c2['langs'][0]['inline'] == L0['inline']
blobs[16] = cfgb
fc = open(fchunk, 'rb').read(); fa = open(fatlas, 'rb').read()
assert blobs[9] == blobs[10] == blobs[11] and blobs[0] == blobs[4] == blobs[7]
for n in (9, 10, 11): blobs[n] = fc
for n in (0, 4, 7): blobs[n] = fa
idx, dfs = dfpack.rebuild(ix, blobs, z.read('index')[0])

man = z.read('META-INF/MANIFEST.MF').decode('latin-1').replace('\r\n', '\n').rstrip('\n').split('\n')
man = [l.replace('com.ea.sdk.SDKMIDlet', 'NampMIDlet') if l.startswith('MIDlet-1:') else l for l in man]
assert any('NampMIDlet' in l for l in man)
man.append('Force-Locale: vi')
manb = ('\r\n'.join(man) + '\r\n').encode('latin-1')

zo = zipfile.ZipFile(out, 'w', zipfile.ZIP_DEFLATED)
for it in z.infolist():
    n = it.filename
    if n == 'META-INF/MANIFEST.MF': zo.writestr(n, manb); continue
    if n == 't_pt': continue
    if n == 'index': zo.writestr(n, idx); continue
    if n.startswith('df') and n.endswith('_0'): zo.writestr(n, dfs[int(n[2:-2])]); continue
    zo.writestr(it, z.read(n))
zo.writestr('t_vi', data)
zo.writestr('namp.png', open(logo, 'rb').read())
for f in sorted(os.listdir(cdir)):
    zo.write(os.path.join(cdir, f), f)
zo.close()
print('OK', out, 't_vi %d B' % len(data), 'chunks', lens)
