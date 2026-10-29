import sys, zipfile
src, dst, entry, path = sys.argv[1:5]
zi = zipfile.ZipFile(src)
with zipfile.ZipFile(dst, 'w', compresslevel=9) as zo:
    for info in zi.infolist():
        data = open(path, 'rb').read() if info.filename == entry else zi.read(info.filename)
        ni = zipfile.ZipInfo(info.filename, info.date_time)
        ni.compress_type = info.compress_type; ni.external_attr = info.external_attr
        zo.writestr(ni, data)
