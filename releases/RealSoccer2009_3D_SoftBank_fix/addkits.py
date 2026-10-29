import sys, zipfile
src, dst, ids = sys.argv[1], sys.argv[2], sys.argv[3]
zi = zipfile.ZipFile(src); names = set(zi.namelist())
k2, k3 = zi.read('tex/2.t'), zi.read('tex/3.t')
with zipfile.ZipFile(dst, 'w', compresslevel=9) as zo:
    for info in zi.infolist():
        ni = zipfile.ZipInfo(info.filename, info.date_time); ni.compress_type = info.compress_type; ni.external_attr = info.external_attr
        zo.writestr(ni, zi.read(info.filename))
    added = 0
    for t in map(int, open(ids).read().split()):
        n = 'tex/%d.t' % (t + 1)
        if n in names: continue
        ni = zipfile.ZipInfo(n, (2009, 3, 17, 13, 41, 0)); ni.compress_type = zipfile.ZIP_DEFLATED
        zo.writestr(ni, k2 if t % 2 else k3); added += 1
print('added', added)
