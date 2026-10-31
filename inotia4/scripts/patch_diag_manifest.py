#!/usr/bin/env python3
"""Ban CHAN DOAN: chuyen LAUNCHER sang com.namp.vh.diag.LaunchActivity + them DiagProvider (initOrder cao nhat).
    python3 patch_diag_manifest.py <AndroidManifest.xml (apktool)> [--internet]
--internet: tra lai quyen android.permission.INTERNET (de thu gia thuyet 'go INTERNET lam sap').
Moi thay doi phai khop DUNG 1 lan, lech thi dung.
"""
import re, sys

p = sys.argv[1]
internet = '--internet' in sys.argv[2:]
s = open(p, encoding='utf-8').read()
pkg = re.search(r'<manifest[^>]*\spackage="([^"]+)"', s).group(1)
MAIN = pkg + '.MainActivity'

# 1) bo intent-filter MAIN/LAUNCHER khoi MainActivity
act = re.search(r'<activity[^>]*android:name="%s"[^>]*>.*?</activity>' % re.escape(MAIN), s, re.S)
if not act:
    raise SystemExit('khong thay MainActivity')
lf = re.compile(r'\s*<intent-filter>\s*<action android:name="android.intent.action.MAIN"/>\s*'
                r'<category android:name="android.intent.category.LAUNCHER"/>\s*</intent-filter>')
block = act.group(0)
if len(lf.findall(block)) != 1:
    raise SystemExit('MainActivity: intent-filter LAUNCHER khop %d lan' % len(lf.findall(block)))
s = s.replace(block, lf.sub('', block))
if s.count('android.intent.category.LAUNCHER') != 0:
    raise SystemExit('con LAUNCHER khac trong manifest')

# 2) them LaunchActivity + DiagProvider
add = ('        <activity android:name="com.namp.vh.diag.LaunchActivity" android:exported="true" '
       'android:theme="@android:style/Theme.Black.NoTitleBar" '
       'android:configChanges="keyboardHidden|orientation|screenLayout|screenSize|smallestScreenSize">\n'
       '            <intent-filter>\n'
       '                <action android:name="android.intent.action.MAIN"/>\n'
       '                <category android:name="android.intent.category.LAUNCHER"/>\n'
       '            </intent-filter>\n'
       '        </activity>\n'
       '        <provider android:name="com.namp.vh.diag.DiagProvider" android:authorities="%s.namp_diag" '
       'android:exported="false" android:initOrder="1000"/>\n' % pkg)
if s.count('</application>') != 1:
    raise SystemExit('</application> khong duy nhat')
s = s.replace('</application>', add + '    </application>')
print('LAUNCHER -> com.namp.vh.diag.LaunchActivity; them DiagProvider (initOrder=1000)')

# 3) tuy chon: tra lai INTERNET
if internet:
    if 'android.permission.INTERNET"' in s:
        raise SystemExit('da co INTERNET')
    m = re.search(r'\n(\s*)<uses-permission ', s)
    s = s[:m.start()] + '\n%s<uses-permission android:name="android.permission.INTERNET"/>' % m.group(1) + s[m.start():]
    print('them quyen android.permission.INTERNET')
open(p, 'w', encoding='utf-8').write(s)
