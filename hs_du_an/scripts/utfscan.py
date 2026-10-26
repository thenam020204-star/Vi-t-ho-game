"""Quét chuỗi kiểu DataInput.readUTF (u2 độ dài + UTF-8) có chữ CJK trong file nhị phân.
Dùng chung cho mọi phần; kết quả được kiểm lại bằng harness Java (parser của chính game)."""
import struct

def is_cjkish(c):
    o = ord(c)
    return o >= 0x2E80 and not (0xD800 <= o < 0xE000)

def ok_char(c):
    o = ord(c)
    if o < 0x20:
        return c in '\n'
    return not (0xD800 <= o < 0xE000) and o not in (0xFFFE, 0xFFFF)

def try_utf(b, o, maxlen=4000):
    if o + 2 > len(b):
        return None
    L = (b[o] << 8) | b[o + 1]
    if L < 2 or L > maxlen or o + 2 + L > len(b):
        return None
    raw = b[o + 2:o + 2 + L]
    if b'\x00' in raw:
        return None
    try:
        s = raw.decode('utf-8')
    except UnicodeDecodeError:
        return None
    if not any(is_cjkish(c) for c in s) or not all(ok_char(c) for c in s):
        return None
    return s

def scan(b, start=0, end=None):
    """Trả về list (offset, chuỗi); offset trỏ vào tiền tố u2."""
    end = len(b) if end is None else end
    out = []
    o = start
    while o < end - 2:
        s = try_utf(b, o)
        if s is not None and o + 2 + ((b[o] << 8) | b[o + 1]) <= end:
            out.append((o, s))
            o += 2 + ((b[o] << 8) | b[o + 1])
        else:
            o += 1
    return out

def enc(s):
    r = s.encode('utf-8')
    if len(r) > 65535:
        raise ValueError('chuỗi quá dài')
    return struct.pack('>H', len(r)) + r

def rebuild(b, hits, repl, start=0, end=None):
    """Ghép lại đoạn b[start:end] thay chuỗi theo repl {offset: chuỗi mới}."""
    end = len(b) if end is None else end
    out = bytearray(); p = start
    for o, s in hits:
        if o < start or o >= end:
            continue
        L = (b[o] << 8) | b[o + 1]
        out += b[p:o]
        out += enc(repl.get(o, s))
        p = o + 2 + L
    out += b[p:end]
    return bytes(out)
