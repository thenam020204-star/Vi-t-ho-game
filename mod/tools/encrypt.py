"""Encrypt / decrypt game resources with the rc.java byte permutation (key 56958410).
usage: encrypt.py <dir> [-d]   -- in place, every file except *.class, META-INF/*, icon.png
       -d decrypts (for editing resources of a release build)"""
import os, sys
KEY = 56958410
SKIP = {'icon.png'}


def tables():
    enc = list(range(256))
    s = KEY & 0xFFFFFFFF
    for i in range(255, 0, -1):
        s ^= (s << 13) & 0xFFFFFFFF
        s ^= s >> 17
        s ^= (s << 5) & 0xFFFFFFFF
        j = (s >> 1) % (i + 1)
        enc[i], enc[j] = enc[j], enc[i]
    dec = [0] * 256
    for i, e in enumerate(enc):
        dec[e] = i
    return bytes(enc), bytes(dec)


ENC, DEC = tables()


def crypt(data, decrypt=False):
    return data.translate(DEC if decrypt else ENC)


if __name__ == '__main__':
    root = sys.argv[1]; dec = '-d' in sys.argv[2:]; n = 0
    for r, ds, fs in os.walk(root):
        for f in fs:
            p = os.path.join(r, f); rel = os.path.relpath(p, root).replace(os.sep, '/')
            if rel.endswith('.class') or rel.startswith('META-INF/') or rel in SKIP:
                continue
            d = open(p, 'rb').read(); open(p, 'wb').write(crypt(d, dec)); n += 1
    print(('decrypted' if dec else 'encrypted'), n, 'files')
