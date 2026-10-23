#!/usr/bin/env python3
"""T17: rap cac APK gop chan doan / fix tu build_apk_mod.py (cung khoa ky, cung data.unity3d f18).
Bien the (xem PLAN T17):
  diag_patch : libil2cpp DA VA (mods/patches.json), co dex menu (chi nut "Luu log"), KHONG co libmsmod.so
  diag_menu  : libil2cpp GOC (khong va), co dex menu + libmsmod.so
  diag_full  : libil2cpp da va (patches.json) + dex menu + libmsmod.so  (= ban mod cu + Luu log)
  fix        : nhu diag_full nhung libil2cpp theo mods/patches_fix.json (B2 sua)
Dung: source tools/env.sh; python3 -I tools/build_apk_diag.py --dex2 work/diag/classes2.dex [--only patch,menu,full,fix]
Ra: out/MagicSurvival_VI_f18_<bien the>_merged.apk. Dau vao: work/mod/{libmsmod.so,classes_mod.dex}, libil2cpp tu patch_so.py."""
import os, sys, zipfile, argparse, shutil, io, subprocess
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
src = open(os.path.join(ROOT, 'tools/build_apk.py')).read().split('\ndef main():')[0]
exec(compile(src, 'build_apk_defs', 'exec'))
msrc = open(os.path.join(ROOT, 'tools/build_apk_mod.py')).read()
exec(compile(msrc[msrc.index('def rewrite_add'):msrc.index('ap = argparse')], 'rewrite_add_def', 'exec'))

ap = argparse.ArgumentParser(); ap.add_argument('--dex2', required=True); ap.add_argument('--only', default='patch,menu,full,fix'); a = ap.parse_args()
M = os.path.join(ROOT, 'work/mod'); D = os.path.join(ROOT, 'work/diag'); outd = os.path.join(ROOT, 'out'); os.makedirs(D, exist_ok=True)
ensure_key()

def make_so(name, patches):
    out = os.path.join(D, name)
    subprocess.check_call([sys.executable, '-I', os.path.join(ROOT, 'tools/patch_so.py'), '--patches', patches, '-o', out], stdout=subprocess.DEVNULL)
    subprocess.check_call([sys.executable, '-I', os.path.join(ROOT, 'tools/patch_so.py'), '--patches', patches, '--check', out], stdout=subprocess.DEVNULL)
    return out
so_patched = make_so('libil2cpp_patched.so', os.path.join(ROOT, 'mods/patches.json'))
so_fix = make_so('libil2cpp_fix.so', os.path.join(ROOT, 'mods/patches_fix.json'))
so_orig = os.path.join(ROOT, 'work/il2/lib/arm64-v8a/libil2cpp.so')

f18 = os.path.join(outd, 'MagicSurvival_VI_f18.apks')
bundle = os.path.join(D, 'data.unity3d')
zb = zipfile.ZipFile(io.BytesIO(zipfile.ZipFile(f18).read('base.apk'))); open(bundle, 'wb').write(zb.read(BUNDLE_ENTRY)); del zb
drop_sig = lambda n: n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC'))
drop_sig_split = lambda n: n in ('stamp-cert-sha256',) or (n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC', '.MF')))

V = {'patch': (so_patched, False), 'menu': (so_orig, True), 'full': (so_patched, True), 'fix': (so_fix, True)}
for v in a.only.split(','):
    so, withlib = V[v]
    ub = os.path.join(D, 'b.unaligned.apk'); us = os.path.join(D, 's.unaligned.apk')
    rewrite_add(BASE, ub, {BUNDLE_ENTRY: bundle, 'classes2.dex': a.dex2}, {'classes3.dex': (os.path.join(M, 'classes_mod.dex'), zipfile.ZIP_STORED)},
                {'classes3.dex': 'classes2.dex'}, drop=drop_sig)
    sb = os.path.join(D, 'base.apk'); ss = os.path.join(D, 'split_config.arm64_v8a.apk')
    align_sign(ub, sb); os.remove(ub)
    add = {'lib/arm64-v8a/libmsmod.so': (os.path.join(M, 'libmsmod.so'), zipfile.ZIP_DEFLATED)} if withlib else {}
    after = {'lib/arm64-v8a/libmsmod.so': 'lib/arm64-v8a/libil2cpp.so'} if withlib else {}
    rewrite_add(SPLIT, us, {'lib/arm64-v8a/libil2cpp.so': so}, add, after, drop=drop_sig_split)
    align_sign(us, ss); os.remove(us)
    apks = os.path.join(D, 'x.apks')
    with zipfile.ZipFile(apks, 'w', zipfile.ZIP_STORED) as z:
        z.write(sb, 'base.apk'); z.write(ss, 'split_config.arm64_v8a.apk')
    mu = os.path.join(D, 'merged.unsigned.apk')
    run(['java', '-jar', APKED, 'm', '-i', apks, '-o', mu, '-f'])
    merged = os.path.join(outd, 'MagicSurvival_VI_f18_%s_merged.apk' % ('diag_' + v if v != 'fix' else 'fix'))
    align_sign(mu, merged)
    for f in (mu, apks, sb, ss): os.remove(f)
    print('OK', v, merged, os.path.getsize(merged), flush=True)
os.remove(bundle)
