# -*- coding: utf-8 -*-
"""Tiện ích jar: chồng file lên jar gốc (giữ thứ tự entry) và sinh .jad."""
import os
import zipfile


def overlay(base, out, files, remove=()):
    """files: {tên_trong_jar: bytes}. Entry có sẵn bị thay tại chỗ, entry mới thêm ở cuối."""
    zin = zipfile.ZipFile(base)
    infos = zin.infolist()
    done = set()
    tmp = out + '.tmp'
    with zipfile.ZipFile(tmp, 'w', zipfile.ZIP_DEFLATED) as zo:
        for it in infos:
            if it.filename in remove:
                continue
            data = files[it.filename] if it.filename in files else zin.read(it.filename)
            done.add(it.filename)
            zi = zipfile.ZipInfo(it.filename, date_time=it.date_time)
            zi.compress_type = zipfile.ZIP_STORED if it.filename.endswith('/') else zipfile.ZIP_DEFLATED
            zi.external_attr = it.external_attr
            zo.writestr(zi, data)
        for name in sorted(files):
            if name not in done:
                zi = zipfile.ZipInfo(name, date_time=(2026, 1, 1, 0, 0, 0))
                zi.compress_type = zipfile.ZIP_DEFLATED
                zo.writestr(zi, files[name])
    os.replace(tmp, out)


def manifest_attrs(jar):
    z = zipfile.ZipFile(jar)
    txt = z.read('META-INF/MANIFEST.MF').decode('utf-8', 'replace').replace('\r\n', '\n')
    attrs = []
    for ln in txt.split('\n'):
        if ln.startswith(' ') and attrs:
            attrs[-1] = (attrs[-1][0], attrs[-1][1] + ln[1:])
        elif ': ' in ln:
            k, v = ln.split(': ', 1)
            attrs.append((k, v))
    return attrs


def make_jad(jar, jad):
    """JAD = các thuộc tính MIDlet-*/MicroEdition-* của manifest + MIDlet-Jar-URL/Size."""
    keep = []
    for k, v in manifest_attrs(jar):
        if k == 'Manifest-Version':
            continue
        keep.append((k, v))
    keep = [(k, v) for k, v in keep if k not in ('MIDlet-Jar-URL', 'MIDlet-Jar-Size')]
    keep.append(('MIDlet-Jar-URL', os.path.basename(jar)))
    keep.append(('MIDlet-Jar-Size', str(os.path.getsize(jar))))
    with open(jad, 'w', encoding='utf-8', newline='\n') as f:
        for k, v in keep:
            f.write('%s: %s\n' % (k, v))
