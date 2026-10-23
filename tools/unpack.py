#!/usr/bin/env python3
"""Giai nen Magic_Survival_0.9941 (zip chia phan) -> work/game.apks -> work/apks/*.apk -> work/ex/ (assets/).

Chay: python3 -I tools/unpack.py [--src DIR_UPLOAD] [--work DIR_WORK]
zip chia phan: z01..z06 + zip noi lai; byte 0-3 la chu ky 504b0708 (span marker),
sau do la local file header cua entry duy nhat (deflate raw). Tu parse header, khong dung `zip -s 0`.
"""
import argparse, glob, os, struct, sys, zlib, zipfile

EXPECT_CRC = 0x63CCBE18
EXPECT_SIZE = 170785160
DEFAULT_SRC = "/root/.claude/uploads/9e39a733-fcc8-5d12-bac4-5922f80b6e8d"


def find_parts(src):
    """Tra ve danh sach file theo thu tu z01..zNN, .zip (ten that sau dau '-')."""
    found = {}
    for p in glob.glob(os.path.join(src, "*")):
        real = os.path.basename(p).split("-", 1)[-1]
        if real.startswith("Magic_Survival_0.9941."):
            found[real.rsplit(".", 1)[-1]] = p
    zs = sorted(k for k in found if k.startswith("z") and k[1:].isdigit())
    if "zip" not in found or not zs:
        sys.exit("Thieu phan zip trong " + src)
    return [found[k] for k in zs] + [found["zip"]]


def stream_inflate(parts, out_path):
    class Rd:
        def __init__(s):
            s.i = 0; s.f = None; s.nxt()
        def nxt(s):
            if s.f: s.f.close()
            s.f = open(parts[s.i], "rb") if s.i < len(parts) else None
            s.i += 1
        def read(s, n):
            b = b""
            while len(b) < n and s.f:
                c = s.f.read(n - len(b))
                if not c: s.nxt()
                b += c
            return b
    r = Rd()
    sig = r.read(4)
    if sig != b"PK\x07\x08":
        sys.exit("Khong thay span marker 504b0708: %r" % sig)
    h = r.read(30)
    if h[:4] != b"PK\x03\x04":
        sys.exit("Khong thay local header")
    method = struct.unpack("<H", h[8:10])[0]
    nlen, elen = struct.unpack("<HH", h[26:30])
    name = r.read(nlen); r.read(elen)
    if method != 8:
        sys.exit("method %d != 8" % method)
    d = zlib.decompressobj(-15)
    crc = 0; size = 0
    with open(out_path, "wb") as o:
        while not d.eof:
            c = r.read(1 << 20)
            if not c: break
            u = d.decompress(c)
            o.write(u); crc = zlib.crc32(u, crc); size += len(u)
    print("entry %r: %d bytes, crc %08x" % (name.decode(), size, crc))
    if crc != EXPECT_CRC or size != EXPECT_SIZE:
        sys.exit("CRC/size KHONG khop (ky vong %08x / %d)" % (EXPECT_CRC, EXPECT_SIZE))
    print("CRC OK")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--src", default=DEFAULT_SRC)
    ap.add_argument("--work", default=os.path.join(os.path.dirname(os.path.abspath(__file__)), "..", "work"))
    a = ap.parse_args()
    work = os.path.abspath(a.work)
    os.makedirs(work, exist_ok=True)
    apks = os.path.join(work, "game.apks")
    stream_inflate(find_parts(a.src), apks)
    adir = os.path.join(work, "apks")
    os.makedirs(adir, exist_ok=True)
    with zipfile.ZipFile(apks) as z:
        for i in z.infolist():
            print("  apks:", i.filename, i.file_size)
        for n in ("base.apk", "split_config.arm64_v8a.apk"):
            if n not in z.namelist():
                sys.exit("apks thieu " + n)
            with z.open(n) as s, open(os.path.join(adir, n), "wb") as o:
                while True:
                    c = s.read(1 << 20)
                    if not c: break
                    o.write(c)
    for n in ("base.apk", "split_config.arm64_v8a.apk"):
        os.replace(os.path.join(adir, n), os.path.join(work, n))
    os.rmdir(adir)
    ex = os.path.join(work, "ex")
    n = 0
    with zipfile.ZipFile(os.path.join(work, "base.apk")) as z:
        for i in z.infolist():
            if i.filename.startswith("assets/") and not i.is_dir():
                z.extract(i, ex); n += 1
    print("Giai %d file assets/ vao %s" % (n, ex))


if __name__ == "__main__":
    main()
