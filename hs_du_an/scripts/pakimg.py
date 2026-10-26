"""Giải nén / đóng gói lại file .pak của engine Heaven Sword (Gameislive).

Định dạng (theo e/a.java ở P1): u2 n · n×(readUTF tên, u4 độ dài) · n khối dữ liệu,
mỗi byte XOR với một khoá (P1 = 1). Khoá được tự dò từ chữ ký PNG / ANI.

  python3 pakimg.py unpack <file.pak> <thư_mục_ra>   -> ảnh + _manifest.tsv
  python3 pakimg.py pack   <thư_mục> <file.pak>      -> theo đúng thứ tự trong _manifest.tsv
  python3 pakimg.py test   <file.pak>                -> unpack + pack phải giống hệt từng byte
"""
import os, struct, sys, tempfile

PNG = b'\x89PNG\r\n\x1a\n'


def read_pak(b):
    n = struct.unpack('>h', b[:2])[0]
    p = 2
    ents = []
    for _ in range(n):
        L = struct.unpack('>H', b[p:p + 2])[0]
        name = b[p + 2:p + 2 + L].decode('utf-8')
        size = struct.unpack('>i', b[p + 2 + L:p + 6 + L])[0]
        ents.append([name, size])
        p += 6 + L
    for e in ents:
        e.append(b[p:p + e[1]])
        p += e[1]
    if p != len(b):
        raise ValueError('pak thừa/thiếu byte: %d/%d' % (p, len(b)))
    return ents


def guess_key(ents):
    for name, size, data in ents:
        if name.lower().endswith('.png') and size >= 8:
            k = data[0] ^ 0x89
            if bytes(c ^ k for c in data[:8]) == PNG:
                return k
    return None


def unpack(pak, out):
    b = open(pak, 'rb').read()
    ents = read_pak(b)
    k = guess_key(ents)
    if k is None:
        raise ValueError('không dò được khoá XOR (không có PNG hợp lệ)')
    os.makedirs(out, exist_ok=True)
    with open(os.path.join(out, '_manifest.tsv'), 'w', encoding='utf-8') as m:
        m.write('#key\t%d\n' % k)
        for name, size, data in ents:
            dec = bytes(c ^ k for c in data)
            q = os.path.join(out, name)
            os.makedirs(os.path.dirname(q) or out, exist_ok=True)
            open(q, 'wb').write(dec)
            m.write('%s\t%d\n' % (name, size))
    return len(ents), k


def pack(d, pak):
    lines = open(os.path.join(d, '_manifest.tsv'), encoding='utf-8').read().splitlines()
    k = int(lines[0].split('\t')[1])
    names = [l.split('\t')[0] for l in lines[1:] if l]
    blobs = [open(os.path.join(d, n), 'rb').read() for n in names]
    out = bytearray(struct.pack('>h', len(names)))
    for n, data in zip(names, blobs):
        r = n.encode('utf-8')
        out += struct.pack('>H', len(r)) + r + struct.pack('>i', len(data))
    for data in blobs:
        out += bytes(c ^ k for c in data)
    open(pak, 'wb').write(bytes(out))


def main():
    cmd = sys.argv[1]
    if cmd == 'unpack':
        n, k = unpack(sys.argv[2], sys.argv[3])
        print('%s: %d mục, khoá XOR %d' % (sys.argv[2], n, k))
    elif cmd == 'pack':
        pack(sys.argv[2], sys.argv[3])
    elif cmd == 'test':
        t = tempfile.mkdtemp()
        unpack(sys.argv[2], t + '/d')
        pack(t + '/d', t + '/r.pak')
        same = open(sys.argv[2], 'rb').read() == open(t + '/r.pak', 'rb').read()
        print('%s round-trip: %s' % (sys.argv[2], 'OK' if same else 'SAI'))
        sys.exit(0 if same else 1)


if __name__ == '__main__':
    main()
