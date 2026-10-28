#!/usr/bin/env python3
"""Phan tich pak/*.p va t/item*.t (chi doc). Dung: dump_pak.py DIR_JAR_GIAI_NEN [--dump]"""
import sys, os, re, struct, glob, collections
HAN = re.compile(r'[⺀-鿿＀-￯　-〿‘-‟…]')
TOK = re.compile(r'[a-z]\d#')

def rd_pak(path):
    d = open(path, 'rb').read(); p = 2
    n = d[p]; p += 1; out = []
    for _ in range(n):
        l = struct.unpack('>H', d[p:p+2])[0]; p += 2
        name = d[p:p+l].decode('utf-8'); p += l
        ln = struct.unpack('>h', d[p:p+2])[0]; p += 2
        out.append((name, d[p:p+ln])); p += ln
    assert p == len(d), (path, p, len(d))
    return out

def rd_t(path):
    d = open(path, 'rb').read()
    rows, cols = struct.unpack('>hh', d[2:6]); p = 6; res = []
    return rows, cols, d[6:]

def utf_cells(buf, n):
    p = 0; out = []
    for _ in range(n):
        l = struct.unpack('>H', buf[p:p+2])[0]; p += 2
        out.append(buf[p:p+l].decode('utf-8')); p += l
    assert p == len(buf), (p, len(buf)); return out

if __name__ == '__main__':
    root = sys.argv[1]; dump = '--dump' in sys.argv
    cmd = collections.Counter(); cmd_han = collections.Counter(); nblob = 0
    big = []; nfield_han = 0; chars = 0; pos = collections.Counter(); ex = {}
    for f in sorted(glob.glob(root + '/pak/*.p')):
        for name, b in rd_pak(f):
            nblob += 1; s = b.decode('utf-8'); big.append((len(b), os.path.basename(f), name))
            for ins in s.split('^'):
                fl = ins.split('|'); cmd[fl[0]] += 1
                for i, x in enumerate(fl[1:], 1):
                    if HAN.search(x):
                        nfield_han += 1; chars += len(x); cmd_han[(fl[0], i, len(fl)-1)] += 1
                        ex.setdefault((fl[0], i, len(fl)-1), x[:40])
                        if dump: print(os.path.basename(f), name, fl[0], i, x)
    big.sort(reverse=True)
    print('blobs', nblob, 'han fields', nfield_han, 'han chars', chars)
    print('largest', big[:5])
    print('cmd count', sorted(cmd.items(), key=lambda x: -x[1]))
    print('cmd,field,nfields han:')
    for k, v in sorted(cmd_han.items()): print(' ', k, v, ex[k])
