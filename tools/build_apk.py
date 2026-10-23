#!/usr/bin/env python3
"""Dong goi APK (D7): thay data.unity3d trong base.apk, giu stored/deflate + thu tu entry, zipalign -p 4, apksigner v2+v3,
cung mot khoa cho base + split; tao .apks va APK gop (APKEditor).
Dung: source tools/env.sh; python3 -I tools/build_apk.py TAG FONT.ttf [--keep]
Ra: out/MagicSurvival_VI_TAG.apks, out/MagicSurvival_VI_TAG_merged.apk ; trung gian work/build/TAG (xoa tru khi --keep)
Khoa thu nghiem: work/keys/ms-vi.jks (tao neu chua co), mat khau trong tools/README.md."""
import os, sys, zipfile, subprocess, shutil, argparse
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
KS = os.path.join(ROOT, 'work/keys/ms-vi.jks'); PW = 'msviviet'; ALIAS = 'msvi'
BASE = os.path.join(ROOT, 'work/base.apk'); SPLIT = os.path.join(ROOT, 'work/split_config.arm64_v8a.apk')
APKED = os.path.join(ROOT, 'work/tools/APKEditor-1.4.9.jar')
BUNDLE_ENTRY = 'assets/bin/Data/data.unity3d'


def run(cmd, **kw):
    print('+', ' '.join(map(str, cmd)), flush=True)
    r = subprocess.run(cmd, **kw)
    if r.returncode: sys.exit('LOI lenh (%d): %s' % (r.returncode, cmd[0]))


def ensure_key():
    if os.path.exists(KS): return
    os.makedirs(os.path.dirname(KS), exist_ok=True)
    run(['keytool', '-genkeypair', '-keystore', KS, '-storetype', 'PKCS12', '-alias', ALIAS, '-keyalg', 'RSA', '-keysize', '2048',
         '-validity', '36500', '-storepass', PW, '-keypass', PW, '-dname', 'CN=MS Viet Hoa, O=Community, C=VN'])


def rewrite(src, dst, replace=None, drop=lambda n: False):
    """Ghi lai zip: giu thu tu, method, date, attr; thay noi dung entry trong `replace` {name: path|bytes}."""
    zin = zipfile.ZipFile(src)
    with zipfile.ZipFile(dst, 'w') as zout:
        for i in zin.infolist():
            if drop(i.filename): continue
            zi = zipfile.ZipInfo(i.filename, i.date_time)
            zi.compress_type = i.compress_type; zi.external_attr = i.external_attr; zi.create_system = i.create_system
            if replace and i.filename in replace:
                v = replace[i.filename]
                data = open(v, 'rb').read() if isinstance(v, str) else v
            else:
                data = zin.read(i.filename)
            zout.writestr(zi, data, compress_type=i.compress_type, compresslevel=6 if i.compress_type == zipfile.ZIP_DEFLATED else None)


def align_sign(unaligned, out):
    al = unaligned + '.al'
    run(['zipalign', '-f', '-p', '4', unaligned, al])
    run(['apksigner', 'sign', '--ks', KS, '--ks-pass', 'pass:' + PW, '--ks-key-alias', ALIAS, '--key-pass', 'pass:' + PW,
         '--v1-signing-enabled', 'false', '--v2-signing-enabled', 'true', '--v3-signing-enabled', 'true', '--min-sdk-version', '24',
         '--out', out, al])
    os.remove(al)
    if os.path.exists(out + '.idsig'): os.remove(out + '.idsig')


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('tag'); ap.add_argument('font'); ap.add_argument('--keep', action='store_true')
    ap.add_argument('--no-merge', action='store_true')
    a = ap.parse_args()
    tmp = os.path.join(ROOT, 'work/build', a.tag); outd = os.path.join(ROOT, 'out')
    os.makedirs(tmp, exist_ok=True); os.makedirs(outd, exist_ok=True)
    ensure_key()
    bundle = os.path.join(tmp, 'data.unity3d')
    run([sys.executable, '-I', os.path.join(ROOT, 'tools/patch_bundle.py'), '--font', a.font, '--out', bundle])
    ub = os.path.join(tmp, 'base.unaligned.apk'); us = os.path.join(tmp, 'split.unaligned.apk')
    rewrite(BASE, ub, {BUNDLE_ENTRY: bundle}, drop=lambda n: n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC')))
    os.remove(bundle)
    sb = os.path.join(tmp, 'base.apk'); ss = os.path.join(tmp, 'split_config.arm64_v8a.apk')
    align_sign(ub, sb); os.remove(ub)
    rewrite(SPLIT, us, drop=lambda n: n in ('stamp-cert-sha256',) or (n.startswith('META-INF/') and n.endswith(('.SF', '.RSA', '.DSA', '.EC', '.MF'))))
    align_sign(us, ss); os.remove(us)
    apks = os.path.join(outd, 'MagicSurvival_VI_%s.apks' % a.tag)
    with zipfile.ZipFile(apks, 'w', zipfile.ZIP_STORED) as z:
        z.write(sb, 'base.apk'); z.write(ss, 'split_config.arm64_v8a.apk')
    print('APKS', apks, os.path.getsize(apks))
    if not a.no_merge:
        mu = os.path.join(tmp, 'merged.unsigned.apk')
        if os.path.exists(mu): os.remove(mu)
        try:
            run(['java', '-jar', APKED, 'm', '-i', apks, '-o', mu, '-f'])
            merged = os.path.join(outd, 'MagicSurvival_VI_%s_merged.apk' % a.tag)
            align_sign(mu, merged); os.remove(mu)
            print('MERGED', merged, os.path.getsize(merged))
        except SystemExit as e:
            print('MERGE THAT BAI:', e)
    if not a.keep:
        shutil.rmtree(tmp, ignore_errors=True)


main()
