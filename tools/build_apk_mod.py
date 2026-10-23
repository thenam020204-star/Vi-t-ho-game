#!/usr/bin/env python3
"""T15: rap ban mod f18 (M7). Dau vao: out/MagicSurvival_VI_f18.apks (lay data.unity3d da build o T10), work/mod/libil2cpp.so (patch_so.py),
work/mod/libmsmod.so, work/mod/classes_mod.dex, va classes2.dex da build lai tu smali sua UnityPlayerActivity (--dex2).
Dung: source tools/env.sh; python3 -I tools/build_apk_mod.py --dex2 PATH/classes2.dex
Ra: out/MagicSurvival_VI_f18_mod.apks, out/MagicSurvival_VI_f18_mod_merged.apk. Cac entry khac cua base/split giu nguyen byte + cach luu."""
import os, sys, zipfile, argparse, shutil, importlib.util, re
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
# dung lai ham cua build_apk.py (khong import vi file chay main() khi nap): cat phan dinh nghia
src = open(os.path.join(ROOT, 'tools/build_apk.py')).read().split('\ndef main():')[0]
exec(compile(src, 'build_apk_defs', 'exec'))

def rewrite_add(src, dst, replace, add, after, drop=lambda n: False):
    """Nhu rewrite() nhung them entry moi `add` {name: (path, compress_type)} ngay sau entry `after[name]`."""
    zin = zipfile.ZipFile(src)
    with zipfile.ZipFile(dst, 'w') as zout:
        def put(name, data, ct, like):
            zi = zipfile.ZipInfo(name, like.date_time); zi.compress_type = ct; zi.external_attr = like.external_attr; zi.create_system = like.create_system
            zout.writestr(zi, data, compress_type=ct, compresslevel=6 if ct == zipfile.ZIP_DEFLATED else None)
        for i in zin.infolist():
            if drop(i.filename): continue
            data = open(replace[i.filename], 'rb').read() if i.filename in replace else zin.read(i.filename)
            put(i.filename, data, i.compress_type, i)
            for n, a in after.items():
                if a == i.filename:
                    path, ct = add[n]; put(n, open(path, 'rb').read(), ct, i)

ap = argparse.ArgumentParser(); ap.add_argument('--dex2', required=True); ap.add_argument('--keep', action='store_true'); a = ap.parse_args()
tmp = os.path.join(ROOT, 'work/build/f18mod'); outd = os.path.join(ROOT, 'out'); os.makedirs(tmp, exist_ok=True)
ensure_key()
f18 = os.path.join(outd, 'MagicSurvival_VI_f18.apks')
bundle = os.path.join(tmp, 'data.unity3d')
import io
zb = zipfile.ZipFile(io.BytesIO(zipfile.ZipFile(f18).read('base.apk')))
open(bundle, 'wb').write(zb.read(BUNDLE_ENTRY)); del zb
M = os.path.join(ROOT, 'work/mod')
ub = os.path.join(tmp, 'base.unaligned.apk'); us = os.path.join(tmp, 'split.unaligned.apk')
rewrite_add(BASE, ub, {BUNDLE_ENTRY: bundle, 'classes2.dex': a.dex2}, {'classes3.dex': (os.path.join(M, 'classes_mod.dex'), zipfile.ZIP_STORED)},
            {'classes3.dex': 'classes2.dex'}, drop=lambda n: n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC')))
os.remove(bundle)
sb = os.path.join(tmp, 'base.apk'); ss = os.path.join(tmp, 'split_config.arm64_v8a.apk')
align_sign(ub, sb); os.remove(ub)
rewrite_add(SPLIT, us, {'lib/arm64-v8a/libil2cpp.so': os.path.join(M, 'libil2cpp.so')},
            {'lib/arm64-v8a/libmsmod.so': (os.path.join(M, 'libmsmod.so'), zipfile.ZIP_DEFLATED)}, {'lib/arm64-v8a/libmsmod.so': 'lib/arm64-v8a/libil2cpp.so'},
            drop=lambda n: n in ('stamp-cert-sha256',) or (n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC', '.MF'))))
align_sign(us, ss); os.remove(us)
apks = os.path.join(outd, 'MagicSurvival_VI_f18_mod.apks')
with zipfile.ZipFile(apks, 'w', zipfile.ZIP_STORED) as z:
    z.write(sb, 'base.apk'); z.write(ss, 'split_config.arm64_v8a.apk')
print('APKS', apks, os.path.getsize(apks))
mu = os.path.join(tmp, 'merged.unsigned.apk')
run(['java', '-jar', APKED, 'm', '-i', apks, '-o', mu, '-f'])
merged = os.path.join(outd, 'MagicSurvival_VI_f18_mod_merged.apk')
align_sign(mu, merged); os.remove(mu)
print('MERGED', merged, os.path.getsize(merged))
if not a.keep: shutil.rmtree(tmp, ignore_errors=True)
