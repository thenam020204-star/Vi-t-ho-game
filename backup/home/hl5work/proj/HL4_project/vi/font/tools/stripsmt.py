# -*- coding: utf-8 -*-
"""Gỡ attribute StackMapTable (định dạng Java 6+) khỏi class file và hạ class version.

Tương đương bước `J2meFix ... strip` trong hướng dẫn của bộ font: javac luôn sinh
StackMapTable, nhưng CLDC/KVM chỉ chấp nhận attribute `StackMap`. Sau khi gỡ, ProGuard
`-microedition` sẽ sinh `StackMap` đúng chuẩn.
"""
import struct, sys


def _pool(d):
    n = struct.unpack_from('>H', d, 8)[0]
    i = 10
    utf = {}
    idx = 1
    while idx < n:
        t = d[i]
        if t == 1:
            L = struct.unpack_from('>H', d, i + 1)[0]
            utf[idx] = d[i + 3:i + 3 + L].decode('utf-8', 'replace')
            i += 3 + L
        elif t in (7, 8, 16, 19, 20):
            i += 3
        elif t == 15:
            i += 4
        elif t in (3, 4, 9, 10, 11, 12, 17, 18):
            i += 5
        elif t in (5, 6):
            i += 9
            idx += 1
        else:
            raise ValueError('tag %d @%d' % (t, i))
        idx += 1
    return utf, i


def _attrs(d, p, utf, drop, depth=0):
    """đọc bảng attribute tại p, trả về (bytes_mới, p_sau)"""
    cnt = struct.unpack_from('>H', d, p)[0]
    p += 2
    kept = []
    for _ in range(cnt):
        ni, alen = struct.unpack_from('>HI', d, p)
        body_start = p + 6
        body_end = body_start + alen
        name = utf.get(ni, '')
        if name in drop:
            p = body_end
            continue
        if name == 'Code':
            # Code: u2 maxStack, u2 maxLocals, u4 len, code, u2 excCnt, exc*8, attributes
            q = body_start + 8
            clen = struct.unpack_from('>I', d, body_start + 4)[0]
            q += clen
            ecnt = struct.unpack_from('>H', d, q)[0]
            q += 2 + ecnt * 8
            sub, qend = _attrs(d, q, utf, drop, depth + 1)
            assert qend == body_end, 'Code attr lech: %d != %d' % (qend, body_end)
            new_body = d[body_start:q] + sub
            kept.append(struct.pack('>HI', ni, len(new_body)) + new_body)
            p = body_end
            continue
        kept.append(d[p:body_end])
        p = body_end
    return struct.pack('>H', len(kept)) + b''.join(kept), p


def strip(data, drop=('StackMapTable',), major=None):
    utf, p = _pool(data)
    out = bytearray(data[:p])
    # access_flags, this_class, super_class
    out += data[p:p + 6]
    p += 6
    icnt = struct.unpack_from('>H', data, p)[0]
    out += data[p:p + 2 + icnt * 2]
    p += 2 + icnt * 2
    for _ in range(2):                      # fields rồi methods
        cnt = struct.unpack_from('>H', data, p)[0]
        out += struct.pack('>H', cnt)
        p += 2
        for _ in range(cnt):
            out += data[p:p + 6]            # access, name, descriptor
            p += 6
            a, p = _attrs(data, p, utf, drop)
            out += a
    a, p = _attrs(data, p, utf, drop)       # attribute của class
    out += a
    assert p == len(data), 'thua %d byte' % (len(data) - p)
    if major is not None:
        struct.pack_into('>HH', out, 4, 0, major)
    return bytes(out)


if __name__ == '__main__':
    src, dst = sys.argv[1], sys.argv[2]
    mj = int(sys.argv[3]) if len(sys.argv) > 3 else None
    d = open(src, 'rb').read()
    n = strip(d, major=mj)
    open(dst, 'wb').write(n)
    utf, _ = _pool(n)
    names = set(utf.values())
    print('%s -> %s  %d -> %d byte  version=%d  StackMapTable=%s'
          % (src, dst, len(d), len(n), struct.unpack_from('>HH', n, 4)[1],
             'StackMapTable' in names))
