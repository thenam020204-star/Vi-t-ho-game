#!/usr/bin/env python3
"""Thay nhạc nền trong một APK HL5 đã build: chỉ đổi 21 mục c/snd/bgm_XX.ogg trong assets/data.vfs.mp3.

  swap_bgm.py IN.apk OGG_DIR OUT_unsigned.apk [--replace DIR]

--replace DIR: thêm các file DIR/c/... thay mục VFS trùng khoá (như vfs_pack.py --replace; ví dụ ảnh Việt của patch/mk_vi_images.py).

OGG_DIR chứa bgm_00.ogg .. bgm_20.ogg (Vorbis). Mọi mục VFS khác và mọi file APK khác giữ nguyên từng byte,
giữ thứ tự và kiểu nén của từng mục zip (data.vfs.mp3 phải STORED vì Java mở bằng AssetFileDescriptor +
MediaPlayer.setDataSource(fd, off, len)). Cập nhật sha256 của data.vfs.mp3 trong assets/target-assets.json
(Java TargetAssets so sha256 khi chép asset; lệch -> "Asset fingerprint mismatch"). Chữ ký cũ (META-INF/*.SF|RSA|MF) bị bỏ; sau đó zipalign -p 4 + apksigner.
"""
import sys, os, zipfile, struct, json, hashlib
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from vfs_pack import djb2, read_vfs, write_vfs

src, oggdir, dst = sys.argv[1:4]
rep = sys.argv[5] if len(sys.argv) > 5 and sys.argv[4] == '--replace' else None
zin = zipfile.ZipFile(src)
vfs = zin.read('assets/data.vfs.mp3')
ent = read_vfs(vfs)
idx = {h: i for i, (h, _) in enumerate(ent)}
for i in range(21):
    n = 'bgm_%02d' % i
    h = djb2('c/snd/%s.ogg' % n)
    assert h in idx, n
    assert ent[idx[h]][1][:4] == b'OggS', 'mục gốc %s không phải OGG' % n
    new = open(os.path.join(oggdir, n + '.ogg'), 'rb').read()
    assert new[:4] == b'OggS' and b'vorbis' in new[:64], '%s không phải Ogg Vorbis' % n
    ent[idx[h]][1] = new
if rep:
    for dp, _, fs in os.walk(rep):
        for fn in fs:
            p = os.path.join(dp, fn); key = os.path.relpath(p, rep).replace(os.sep, '/')
            h = djb2(key); assert h in idx, 'không có mục VFS: ' + key
            ent[idx[h]][1] = open(p, 'rb').read(); print('thay', key)
out = write_vfs(ent)
assert len(read_vfs(out)) == len(ent)
ta = json.loads(zin.read('assets/target-assets.json'))
assert 'data.vfs.mp3' in ta
assert ta['data.vfs.mp3'] == hashlib.sha256(vfs).hexdigest(), 'target-assets.json của APK vào không khớp VFS'
ta['data.vfs.mp3'] = hashlib.sha256(out).hexdigest()
zout = zipfile.ZipFile(dst, 'w')
for zi in zin.infolist():
    if zi.filename.startswith('META-INF/') and zi.filename.rsplit('.', 1)[-1] in ('SF', 'RSA', 'MF', 'EC', 'DSA'):
        continue
    data = {'assets/data.vfs.mp3': out, 'assets/target-assets.json': json.dumps(ta).encode()}.get(zi.filename) or zin.read(zi.filename)
    ni = zipfile.ZipInfo(zi.filename, zi.date_time)
    ni.compress_type = zi.compress_type; ni.external_attr = zi.external_attr
    zout.writestr(ni, data)
zout.close()
z = zipfile.ZipFile(dst)
assert z.getinfo('assets/data.vfs.mp3').compress_type == zipfile.ZIP_STORED
chk = json.loads(z.read('assets/target-assets.json'))
for k, h in chk.items():
    assert hashlib.sha256(z.read('assets/' + k)).hexdigest() == h, 'sha256 lệch: ' + k
print('target-assets.json khớp:', chk)
print('VFS %d -> %d B, %d mục; ghi %s' % (len(vfs), len(out), len(ent), dst))
