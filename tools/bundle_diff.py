#!/usr/bin/env python3
"""So 2 data.unity3d theo tung object (file, path_id, sha1 du lieu tho). In object khac / them / mat.
Dung: python3 -I tools/bundle_diff.py GOC MOI [--expect-scene text/vi/scene.tsv]
Ky vong: 1 Font (sharedassets0.assets 33) + 6 TextAsset (resources.assets) + cac UI.Text trong scene.tsv; khong gi khac.
Exit 1 neu khac ky vong."""
import sys, os, hashlib, argparse
import UnityPy

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
TEXT_IDS = {2855, 2856, 2867, 2879, 2882, 2887}


def digest(path):
    env = UnityPy.load(path)
    b = list(env.files.values())[0]
    meta = (hex(b.dataflags), hex(b._block_info_flags), b.version_engine, sorted(b.files))
    objs = {}
    ext = {}
    for fn, f in b.files.items():
        if not hasattr(f, 'objects'): continue
        ext[fn] = [getattr(e, 'path', str(e)) for e in f.externals]
        for pid, o in f.objects.items():
            objs[(fn, pid)] = (o.type.name, o.byte_size, hashlib.sha1(o.get_raw_data()).hexdigest())
    return meta, objs, ext


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('a'); ap.add_argument('b')
    ap.add_argument('--expect-scene', default=os.path.join(ROOT, 'text/vi/scene.tsv'))
    x = ap.parse_args()
    ma, oa, ea = digest(x.a); mb, ob, eb = digest(x.b)
    print('bundle goc :', ma[:3], '\nbundle moi :', mb[:3])
    bad = []
    if ma != mb: bad.append('meta bundle khac (co nen/phien ban/danh sach file)')
    if ea != eb: bad.append('externals khac')
    if set(oa) != set(ob): bad.append('tap object khac: +%d -%d' % (len(set(ob) - set(oa)), len(set(oa) - set(ob))))
    changed = sorted(k for k in oa if k in ob and oa[k][2] != ob[k][2])
    exp = {('sharedassets0.assets', 33)} | {('resources.assets', p) for p in TEXT_IDS}
    scene = set()
    for ln in open(x.expect_scene, encoding='utf-8').read().split('\n')[1:]:
        if ln.strip():
            lv, pid = ln.split('\t')[:2]; scene.add((lv, int(pid)))
    exp |= scene
    for k in changed:
        print('  doi %-22s %6d %-14s %9d -> %9d B' % (k[0], k[1], oa[k][0], oa[k][1], ob[k][1]))
    types = {}
    for k in changed: types[oa[k][0]] = types.get(oa[k][0], 0) + 1
    print('tong object doi:', len(changed), types, '| ky vong:', len(exp), '(1 Font + 6 TextAsset + %d UI.Text)' % len(scene))
    if set(changed) != exp: bad.append('doi sai: thua %s thieu %s' % (sorted(set(changed) - exp)[:5], sorted(exp - set(changed))[:5]))
    print('KET QUA:', 'KHOP du kien' if not bad else 'LOI ' + '; '.join(bad))
    sys.exit(1 if bad else 0)


main()
