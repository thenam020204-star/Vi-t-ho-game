import sys, zipfile
src, dst = sys.argv[1], sys.argv[2]
zi = zipfile.ZipFile(src)
names = zi.namelist()
mf = zi.read('META-INF/MANIFEST.MF')
assert b'MIDlet-FullVersion-URL' not in mf
nl = b'\r\n' if b'\r\n' in mf else b'\n'
body = mf.rstrip(b'\r\n')
new_mf = body + nl + b'MIDlet-FullVersion-URL: http://www.gameloft.com' + nl + nl
with zipfile.ZipFile(dst, "w", compresslevel=9) as zo:
    order = ['META-INF/MANIFEST.MF'] + [n for n in names if n != 'META-INF/MANIFEST.MF']
    for n in order:
        info = zi.getinfo(n)
        data = new_mf if n == 'META-INF/MANIFEST.MF' else zi.read(n)
        ni = zipfile.ZipInfo(n, info.date_time)
        ni.compress_type = info.compress_type
        ni.external_attr = info.external_attr
        zo.writestr(ni, data)
print(names[:3], len(names), repr(nl))
