#!/usr/bin/env python3
"""Đóng gói lại VFS (data.vfs.mp3): bản ghi [u32 hash][u32 size][dữ liệu], hash = djb2 của đường dẫn 'c/...'.

  vfs_pack.py IN.vfs OUT.vfs [--replace DIR] [--add name=file ...]

  --replace DIR : mọi file DIR/c/... (hoặc DIR/res/c/...) có khoá djb2 trùng một mục trong IN thì thay dữ liệu tại chỗ
                  (giữ nguyên thứ tự mục). File không trùng mục nào -> lỗi (dùng --add để thêm mới).
  --add name=file : thêm mục mới (khoá = djb2(name)) vào cuối; name đã tồn tại thì lỗi.
Không có tuỳ chọn nào thì OUT giống hệt IN từng byte.
"""
import sys, os, struct, argparse


def djb2(s):
    h = 5381
    for c in s.encode():
        h = (h * 33 + c) & 0xffffffff
    return h


def read_vfs(d):
    o = 0; ent = []
    while o + 8 <= len(d):
        h, s = struct.unpack_from('<II', d, o)
        if o + 8 + s > len(d):
            raise ValueError('VFS cắt cụt tại %d' % o)
        ent.append([h, d[o + 8:o + 8 + s]]); o += 8 + s
    if o != len(d):
        raise ValueError('VFS thừa %d byte cuối' % (len(d) - o))
    return ent


def write_vfs(ent):
    return b''.join(struct.pack('<II', h, len(b)) + b for h, b in ent)


def collect_dir(root):
    out = {}
    for dp, _, fs in os.walk(root):
        for f in fs:
            rel = os.path.relpath(os.path.join(dp, f), root).replace(os.sep, '/')
            if rel.startswith('res/'): rel = rel[4:]
            out[rel] = os.path.join(dp, f)
    return out


def pack(data, replace=None, add=()):
    ent = read_vfs(data)
    idx = {}
    for i, (h, _) in enumerate(ent):
        idx.setdefault(h, i)
    n_rep = 0
    for name, path in sorted((replace or {}).items()):
        h = djb2(name)
        if h not in idx:
            raise KeyError('không có mục VFS cho %s (hash %08x); dùng --add' % (name, h))
        ent[idx[h]][1] = open(path, 'rb').read(); n_rep += 1
    for name, path in add:
        h = djb2(name)
        if h in idx:
            raise KeyError('mục %s đã tồn tại' % name)
        ent.append([h, open(path, 'rb').read()]); idx[h] = len(ent) - 1
    return write_vfs(ent), n_rep


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('inp'); ap.add_argument('out')
    ap.add_argument('--replace'); ap.add_argument('--add', nargs='*', default=[])
    a = ap.parse_args()
    adds = []
    for x in a.add:
        n, _, p = x.partition('=')
        if not p: ap.error('--add cần name=file')
        adds.append((n, p))
    data = open(a.inp, 'rb').read()
    out, n = pack(data, collect_dir(a.replace) if a.replace else None, adds)
    open(a.out, 'wb').write(out)
    print('đã thay %d, thêm %d, %d byte' % (n, len(adds), len(out)))


if __name__ == '__main__':
    main()
