"""Thu vien dung chung cho extract.py / repack.py (pak, item*.t, hang chuoi trong .class)."""
import re, struct, os, glob

HAN = re.compile(r'[⺀-鿿＀-￯　-〿‘-‟…—×]')
TOK = re.compile(r'[a-z]\d#')
NLE, TABE = '¶', '⇥'          # cung quy uoc voi dp.py
HEADER = ['id', 'file', 'khoa', 'goc', 'dich', 'ghi chu', 'gioi han']


# ---------------------------------------------------------------- pak
def read_pak(data):
    """-> (head2, [(name_bytes, blob_bytes)]); khop Main/h.a(path,0)."""
    head = data[:2]
    n = data[2]; p = 3; out = []
    for _ in range(n):
        l = struct.unpack_from('>H', data, p)[0]; p += 2
        name = data[p:p + l]; p += l
        ln = struct.unpack_from('>h', data, p)[0]; p += 2
        if ln < 0:
            raise ValueError('blob length < 0')
        out.append((name, data[p:p + ln])); p += ln
    if p != len(data):
        raise ValueError('pak: thua %d byte cuoi' % (len(data) - p))
    return head, out


def write_pak(head, blobs):
    if len(blobs) > 127:
        raise ValueError('pak > 127 blob')
    b = bytearray(head); b.append(len(blobs))
    for name, blob in blobs:
        if len(blob) > 32767:
            raise ValueError('blob %r dai %d > 32767' % (name, len(blob)))
        b += struct.pack('>H', len(name)) + name + struct.pack('>h', len(blob)) + blob
    return bytes(b)


def split_script(text):
    """blob -> [[truong...] ...] (lenh tach '^', truong tach '|'); noi lai khong mat gi."""
    return [ins.split('|') for ins in text.split('^')]


def join_script(cmds):
    return '^'.join('|'.join(c) for c in cmds)


# ---------------------------------------------------------------- item*.t
def read_t(data):
    """-> (head, rows, cols, cells|None). item1/2/8 la so: cells=None."""
    rows, cols = struct.unpack_from('>hh', data, 2)
    return data[:6], rows, cols


def read_t_cells(data, rows, cols):
    p = 6; cells = []
    for _ in range(rows * cols):
        l = struct.unpack_from('>H', data, p)[0]; p += 2
        cells.append(data[p:p + l].decode('utf-8')); p += l
    if p != len(data):
        raise ValueError('item.t: thua byte')
    return cells


def write_t_cells(head, cells):
    b = bytearray(head)
    for c in cells:
        e = c.encode('utf-8')
        if len(e) > 65535:
            raise ValueError('readUTF > 65535')
        b += struct.pack('>H', len(e)) + e
    return bytes(b)


# ---------------------------------------------------------------- class constant pool
def mutf8_decode(b):
    return b.replace(b'\xc0\x80', b'\x00').decode('utf-8', 'surrogatepass')


def mutf8_encode(s):
    if any(ord(c) > 0xFFFF for c in s):
        raise ValueError('ky tu ngoai BMP')
    return s.encode('utf-8', 'surrogatepass').replace(b'\x00', b'\xc0\x80')


def parse_cp(d):
    """-> (entries, end_offset). entries: list cua [tag, raw_bytes_sau_tag] (None cho chi so 0/slot thu 2 cua long/double)."""
    if struct.unpack_from('>I', d, 0)[0] != 0xCAFEBABE:
        raise ValueError('not a class')
    n = struct.unpack_from('>H', d, 8)[0]; o = 10; i = 1
    ent = [None] * n
    while i < n:
        t = d[o]
        if t == 1:
            l = struct.unpack_from('>H', d, o + 1)[0]; ln = 2 + l
        elif t in (3, 4, 9, 10, 11, 12, 17, 18):
            ln = 4
        elif t in (5, 6):
            ln = 8
        elif t in (7, 8, 16, 19, 20):
            ln = 2
        elif t == 15:
            ln = 3
        else:
            raise ValueError('cp tag %d' % t)
        ent[i] = [t, d[o + 1:o + 1 + ln]]
        o += 1 + ln; i += 1
        if t in (5, 6):
            i += 1
    return ent, o


def _string_refs(ent):
    """chi so Utf8 duoc CONSTANT_String (tag 8) tham chieu."""
    return {struct.unpack('>H', e[1])[0] for e in ent if e and e[0] == 8}


def class_han_utf8(d):
    """-> [(cp_index, text)] cac Utf8 chua Han DUOC DUNG LAM HANG CHUOI (CONSTANT_String).
    Utf8 chi dung lam ten field/method/descriptor (vd ban 360: GameData.星际神殿) khong tinh."""
    ent, _ = parse_cp(d)
    refs = _string_refs(ent)
    out = []
    for i, e in enumerate(ent):
        if e and e[0] == 1 and i in refs:
            s = mutf8_decode(e[1][2:])
            if HAN.search(s):
                out.append((i, s))
    return out


def rewrite_class(d, repl):
    """repl: {text_goc: text_dich}. Chi doi HANG CHUOI: moi CONSTANT_String tro toi Utf8 co noi dung == text_goc
    duoc tro sang mot Utf8 MOI them cuoi constant pool (Utf8 goc giu nguyen, vi co the la ten field/method).
    Khong doi gi thi tra nguyen byte."""
    ent, end = parse_cp(d)
    if not repl:
        return d
    n = len(ent)
    new_utf = []          # [(text_dich)] them sau cung
    remap = {}            # cp_index Utf8 goc -> cp_index Utf8 moi
    for i, e in enumerate(ent):
        if e and e[0] == 8:
            u = struct.unpack('>H', e[1])[0]
            s = mutf8_decode(ent[u][1][2:])
            if s in repl and repl[s] != s and u not in remap:
                remap[u] = n + len(new_utf); new_utf.append(repl[s])
    if not remap:
        return d
    if n + len(new_utf) > 65535:
        raise ValueError('constant pool qua lon')
    out = bytearray(d[:8]) + struct.pack('>H', n + len(new_utf))
    for i, e in enumerate(ent):
        if e is None:
            continue
        t, raw = e
        if t == 8:
            u = struct.unpack('>H', raw)[0]
            if u in remap:
                raw = struct.pack('>H', remap[u])
        out.append(t); out += raw
    for txt in new_utf:
        nb = mutf8_encode(txt)
        if len(nb) > 65535:
            raise ValueError('Utf8 > 65535')
        out.append(1); out += struct.pack('>H', len(nb)) + nb
    out += d[end:]
    return bytes(out)


# ---------------------------------------------------------------- font charset
def f12_charset(path):
    d = open(path, 'rb').read()
    l = struct.unpack_from('>H', d, 0)[0]
    return d[2:2 + l].decode('utf-8')


# ---------------------------------------------------------------- tsv
def esc(s):
    return s.replace('\r\n', NLE).replace('\n', NLE).replace('\t', TABE)


def write_tsv(path, rows):
    with open(path, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\t'.join(HEADER) + '\n')
        for r in rows:
            f.write('\t'.join(esc(str(x)) for x in r) + '\n')


def read_tsv(path):
    L = open(path, encoding='utf-8', newline='').read().split('\n')
    if L and L[-1] == '':
        L.pop()
    assert L[0].split('\t') == HEADER, 'header TSV sai'
    out = []
    for l in L[1:]:
        c = l.split('\t'); c += [''] * (7 - len(c))
        out.append(dict(zip(HEADER, c)))
    return out
