"""Đọc/ghi cặp common.idx + common.dat của This War of Mine (Android).
idx: 11 byte header, rồi N mục 17 byte: u32 hash, u32 packed, u32 unpacked, u32 offset, u8 flag (sắp theo hash).
dat: các khối gzip (header 10 byte, deflate kết thúc bằng sync-flush, không trailer) nối liền theo offset.
  python3 datpack.py unpack <idx> <dat> <outdir>
  python3 datpack.py pack <idx> <dat> <replacedir> <out_idx> <out_dat>   (replacedir chứa <hash8>.bin thay thế)"""
import sys, os, struct, zlib
HDR = 11; ES = 17
GZH = b'\x1f\x8b\x08\x00\x00\x00\x00\x00\x00\x00'
def read(idx, dat):
    d = open(idx, 'rb').read(); D = open(dat, 'rb').read()
    n, = struct.unpack('<I', d[3:7]); assert len(d) == HDR + ES * n
    E = [list(struct.unpack('<IIIIB', d[HDR + ES * i:HDR + ES * (i + 1)])) for i in range(n)]
    return d[:HDR], E, D
def inflate(blob, size):
    r = zlib.decompressobj(31).decompress(blob); assert len(r) == size; return r
def deflate(raw):
    c = zlib.compressobj(9, zlib.DEFLATED, -15, 9)
    return GZH + c.compress(raw) + c.flush(zlib.Z_SYNC_FLUSH)
def unpack(idx, dat, out):
    h, E, D = read(idx, dat); os.makedirs(out, exist_ok=True)
    for hs, pk, un, off, fl in E:
        open(os.path.join(out, '%08x.bin' % hs), 'wb').write(inflate(D[off:off + pk], un))
def pack(idx, dat, rep, oidx, odat):
    h, E, D = read(idx, dat)
    o = bytearray(); newE = {}
    for e in sorted(E, key=lambda e: e[3]):
        hs, pk, un, off, fl = e
        p = os.path.join(rep, '%08x.bin' % hs)
        if os.path.exists(p):
            raw = open(p, 'rb').read(); blob = deflate(raw); assert inflate(blob, len(raw)) == raw
            un = len(raw)
        else:
            blob = D[off:off + pk]
        newE[hs] = [hs, len(blob), un, len(o), fl]; o += blob
    oi = bytearray(h)
    for e in E: oi += struct.pack('<IIIIB', *newE[e[0]])
    open(oidx, 'wb').write(oi); open(odat, 'wb').write(o)
if __name__ == '__main__':
    {'unpack': unpack, 'pack': pack}[sys.argv[1]](*sys.argv[2:])
