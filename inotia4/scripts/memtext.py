#!/usr/bin/env python3
"""Inotia 4 (Android) - memorytext*.dat tool.

Container (.dat.jpg in assets/common/game_res):
    b'\\x01\\x00' + LZMA-alone header (props u8, dict u32 LE, size u64 LE) + LZMA1 stream
Payload (memorytext_xx.dat):
    u32 LE count | count x u24 LE absolute offsets | UTF-8 strings, each ended by 0x00

Commands
    unwrap  <in.jpg> <out.dat>             giai nen container
    wrap    <in.dat> <out.jpg>             nen lai (LZMA1, header co kich thuoc that)
    extract <en.dat> <ko.dat> <out.tsv>    xuat bang dich (id, en, ko)
    repack  <en.dat> <tsv> <out.dat>       ghep ban dich (cot 'vi'; rong = giu EN)
    check   <en.dat> <tsv> [--charset f]   kiem ma dieu khien / charset
    selftest <in.jpg>                      parse->build giong tung byte, wrap->unwrap giong tung byte
"""
import lzma, re, struct, sys, csv

HDR = b'\x01\x00'
CTRL = re.compile(r'\$[A-Za-z]|&[A-Za-z]|%[ds]')


def unwrap(blob):
    if blob[:2] != HDR:
        raise ValueError('khong phai container 01 00')
    props = blob[2]
    dict_size, size = struct.unpack('<IQ', blob[3:15])
    lc = props % 9; r = props // 9; lp = r % 5; pb = r // 5
    dec = lzma.LZMADecompressor(lzma.FORMAT_RAW, filters=[
        {'id': lzma.FILTER_LZMA1, 'lc': lc, 'lp': lp, 'pb': pb, 'dict_size': dict_size}])
    out = dec.decompress(blob[15:], max_length=size)
    if len(out) != size:
        raise ValueError('giai nen thieu %d/%d' % (len(out), size))
    return out


def wrap(data, dict_size=1 << 24):
    filt = {'id': lzma.FILTER_LZMA1, 'lc': 3, 'lp': 0, 'pb': 2,
            'dict_size': dict_size, 'preset': 9 | lzma.PRESET_EXTREME}
    raw = lzma.compress(data, format=lzma.FORMAT_RAW, filters=[filt])
    return HDR + bytes([0x5d]) + struct.pack('<IQ', dict_size, len(data)) + raw


def parse(d):
    n = struct.unpack('<I', d[:4])[0]
    offs = [int.from_bytes(d[4 + 3 * i:7 + 3 * i], 'little') for i in range(n)]
    if n and offs[0] != 4 + 3 * n:
        raise ValueError('offset dau khong khop')
    out = []
    for i in range(n):
        end = d.index(b'\x00', offs[i])
        if i + 1 < n and end + 1 != offs[i + 1]:
            raise ValueError('chuoi %d khong lien tuc' % i)
        out.append(d[offs[i]:end].decode('utf-8'))
    return out


def build(strs):
    n = len(strs)
    body = bytearray(); offs = []
    base = 4 + 3 * n
    for s in strs:
        offs.append(base + len(body))
        body += s.encode('utf-8') + b'\x00'
    if base + len(body) > 0xFFFFFF:
        raise ValueError('vuot gioi han offset u24')
    head = struct.pack('<I', n) + b''.join(o.to_bytes(3, 'little') for o in offs)
    return head + bytes(body)


def esc(s):
    return s.replace('\\', '\\\\').replace('\t', '\\t').replace('\n', '\\n').replace('\r', '\\r')


def unesc(s):
    return re.sub(r'\\(.)', lambda m: {'t': '\t', 'n': '\n', 'r': '\r', '\\': '\\'}[m.group(1)], s)


def read_tsv(path):
    rows = {}
    with open(path, encoding='utf-8', newline='') as f:
        r = csv.reader(f, delimiter='\t', quoting=csv.QUOTE_NONE)
        head = next(r)
        iid, ivi = head.index('id'), head.index('vi')
        for row in r:
            if len(row) > ivi and row[ivi] != '':
                rows[int(row[iid])] = unesc(row[ivi])
    return rows


def ctrl_sig(s):
    return sorted(CTRL.findall(s))


def main(a):
    cmd = a[0]
    if cmd == 'unwrap':
        open(a[2], 'wb').write(unwrap(open(a[1], 'rb').read()))
    elif cmd == 'wrap':
        open(a[2], 'wb').write(wrap(open(a[1], 'rb').read()))
    elif cmd == 'extract':
        en = parse(open(a[1], 'rb').read()); ko = parse(open(a[2], 'rb').read())
        with open(a[3], 'w', encoding='utf-8', newline='') as f:
            f.write('id\ten\tko\tvi\n')
            for i, s in enumerate(en):
                if s.strip():
                    f.write('%d\t%s\t%s\t\n' % (i, esc(s), esc(ko[i])))
    elif cmd == 'repack':
        en = parse(open(a[1], 'rb').read()); vi = read_tsv(a[2])
        out = [vi.get(i, s) for i, s in enumerate(en)]
        open(a[3], 'wb').write(build(out))
        print('repack: %d/%d muc da dich' % (len(vi), sum(1 for s in en if s.strip())))
    elif cmd == 'check':
        en = parse(open(a[1], 'rb').read()); vi = read_tsv(a[2])
        cs = None
        if '--charset' in a:
            cs = set(open(a[a.index('--charset') + 1], encoding='utf-8').read())
        bad = 0
        for i, v in sorted(vi.items()):
            if ctrl_sig(v) != ctrl_sig(en[i]):
                print('CTRL %d: %r vs %r' % (i, ctrl_sig(en[i]), ctrl_sig(v))); bad += 1
            if cs is not None:
                miss = sorted(set(c for c in v if c not in cs and c != '\n'))
                if miss:
                    print('GLYPH %d: %r' % (i, ''.join(miss))); bad += 1
        print('OK' if bad == 0 else 'LOI: %d' % bad)
        return 1 if bad else 0
    elif cmd == 'selftest':
        blob = open(a[1], 'rb').read()
        d = unwrap(blob)
        assert build(parse(d)) == d, 'parse->build khac'
        assert unwrap(wrap(d)) == d, 'wrap->unwrap khac'
        print('selftest OK: %d chuoi, %d byte' % (len(parse(d)), len(d)))
    else:
        print(__doc__); return 2
    return 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1:]) or 0)
