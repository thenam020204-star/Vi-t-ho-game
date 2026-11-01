#!/usr/bin/env python3
"""Parser .scn theo đúng mã máy (Interpreter::open / Scripts::init 0x9a85c / Strings::init 0x9a724).
Header: b0 = độ rộng độ dài khối script A, b1 = khối script B, b2 = bảng chuỗi (1 = u8, 2 = u16 LE).
Bố cục từ offset s: ScriptsA, ScriptsB, Strings; mỗi Scripts = u8 n, n độ dài, rồi thân; Strings = u8 n, n độ dài, n chuỗi (không NUL)."""
import struct

def _scripts(b, p, w):
    n = b[p]; p += 1; tot = 0
    for _ in range(n):
        l = b[p] if w == 1 else b[p] | b[p + 1] << 8
        p += w; tot += l
    return p + tot

def _strings(b, p, w):
    n = b[p]; p += 1; lens = []
    for _ in range(n):
        lens.append(b[p] if w == 1 else b[p] | b[p + 1] << 8); p += w
    out = []
    for l in lens:
        out.append(b[p:p + l]); p += l
    return p, out

def parse(b):
    """Trả về (offset bảng chuỗi, [chuỗi], offset bắt đầu script) hoặc None. Thử mọi điểm bắt đầu, chọn điểm khớp EOF."""
    wA, wB, wS = b[0], b[1], b[2]
    if not (wA in (1, 2) and wB in (1, 2) and wS in (1, 2)): return None
    hits = []
    for s in range(3, len(b)):
        try:
            p = _scripts(b, s, wA); p = _scripts(b, p, wB)
            if p >= len(b): continue
            q, strs = _strings(b, p, wS)
            if q == len(b): hits.append((p, strs, s))
        except IndexError:
            continue
    return hits[-1] if hits else None   # thường chỉ 1; lấy cái cuối (gần bảng chuỗi nhất)

def build(b, strs):
    """Ghi lại file với bảng chuỗi mới (giữ nguyên phần trước)."""
    r = parse(b); p = r[0]; wS = b[2]
    assert len(strs) < 256
    out = bytearray(b[:p]); out.append(len(strs))
    for s in strs:
        assert len(s) < (256 if wS == 1 else 65536), (len(s), s[:40])
        out += bytes([len(s)]) if wS == 1 else struct.pack('<H', len(s))
    for s in strs: out += s
    return bytes(out)
