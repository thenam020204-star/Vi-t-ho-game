#!/usr/bin/env python3
"""Phan dung chung cho text_extract / text_inject / text_check.
Parser giong Z_CSVReader.Read cua game (xem docs/analysis.md, cau 2) nhung giu nguyen byte (khong trim, khong xoa `\\`).

Mo hinh:  file = BOM + '\\r\\n'.join(lines);  line = ','.join(raw_cells)   (SPLIT_RE giong game)
          raw_cell = qpre + inner + qsuf            (qpre/qsuf = chuoi dau `"` dau/cuoi, nhu TrimStart/TrimEnd cua game)
          inner    = text0 SEP text1 SEP ...        (SEP = ' @ ' | '@' | '^' - xuong dong / ngan cap, giu nguyen)
          text_k   = lead + core + trail            (lead/trail = khoang trang + cap ngoac mau bao tron doan)
Chi `core` la phan dich (key). Mot o chi dich khi: khong phai o marker/hang tieu de/cot khoa, khong co Hangul, co chu Latin, khong chi la the <color=..>.
"""
import re, os, glob

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
ENG_DIR = os.path.join(ROOT, 'text', 'eng')
VI_DIR = os.path.join(ROOT, 'text', 'vi')
FONT_VI = os.path.join(ROOT, 'work', 'font', 'JejuHallasan-VI.ttf')

# thu tu nhom (theo PLAN: Name, Explain, Class, Synergy, MagicCom, Ability)
GROUPS = ['Name', 'Explain', 'Class', 'Synergy', 'MagicCom', 'Ability']
def fname(g): return 'eng_Dictionary_%s.txt' % g

SPLIT_RE = re.compile(r',(?=(?:[^"]*"[^"]*")*(?![^"]*"))')   # y het SPLIT_RE cua game
SEP_RE = re.compile(r'( ?@ ?|\^)')
HANGUL = re.compile('[ᄀ-ᇿ㄰-㆏가-힯]')
LATIN = re.compile('[A-Za-z]')
COLOR_TAG = re.compile(r'<color=#[0-9A-Fa-f]{6,8}>|</color>')
COLOR_ONLY = re.compile(r'<color=#[0-9A-Fa-f]{6,8}>')
PAIRS = {'【': '】', '[': ']', '〈': '〉', '『': '』', '《': '》', '〔': '〕', '{': '}'}
OPEN = set(PAIRS); CLOSE = set(PAIRS.values())
MARKUP_CHARS = OPEN | CLOSE
ID_RE = re.compile(r'##\d+##')
PLACEHOLDER_CHARS = '□ㅁ'          # □ = so thay luc chay; ㅁ = ky hieu dau dong
SKIP_COLS = {'특성ID'}              # id nhanh ('a') - khong hien thi


def read_file(path):
    b = open(path, 'rb').read()
    assert b[:3] == b'\xef\xbb\xbf', 'thieu BOM: ' + path
    t = b[3:].decode('utf-8')
    assert '\n' not in t.replace('\r\n', '') and '\r' not in t.replace('\r\n', ''), 'xuong dong la: ' + path
    lines = t.split('\r\n')
    assert lines[-1] == '', 'file khong ket thuc bang CRLF: ' + path
    return lines   # phan tu cuoi '' = sau CRLF cuoi


def write_bytes(lines):
    return b'\xef\xbb\xbf' + '\r\n'.join(lines).encode('utf-8')


def split_cell(raw):
    m = re.fullmatch(r'("*)(.*?)("*)', raw, re.S)
    return m.group(1), m.group(2), m.group(3)


def strip_wrap(s):
    """Tra ve (lead, core, trail): bo khoang trang va cap ngoac mau/the color bao tron toan doan."""
    lead = trail = ''
    while True:
        m = re.fullmatch(r'(\s*)(.*?)(\s*)', s, re.S)
        if m.group(1) or m.group(3):
            lead += m.group(1); trail = m.group(3) + trail; s = m.group(2)
            continue
        if s and s[0] in OPEN:
            o, c = s[0], PAIRS[s[0]]
            depth = 0; end = None
            for k, ch in enumerate(s):
                if ch == o: depth += 1
                elif ch == c:
                    depth -= 1
                    if depth == 0: end = k; break
            if end == len(s) - 1:
                lead += o; trail = c + trail; s = s[1:-1]
                continue
        m = re.fullmatch(r'(<color=#[0-9A-Fa-f]{6,8}>)(.*)(</color>)', s, re.S)
        if m and '<color' not in m.group(2) and '</color>' not in m.group(2):
            lead += m.group(1); trail = m.group(3) + trail; s = m.group(2)
            continue
        return lead, s, trail


def is_translatable_core(core):
    return bool(LATIN.search(core)) and not HANGUL.search(core)


def cell_translatable(i, j, cells, header):
    """o (dong i>=1, cot j) co phai o can dich khong"""
    if i < 1 or j == 0: return False
    first = cells[0].strip('"').strip()
    if first == '0' or not first.isdigit(): return False     # hang marker Name/Type/Explain va hang tieu de giua file
    if j < len(header) and header[j] in SKIP_COLS: return False
    raw = cells[j]
    if COLOR_ONLY.fullmatch(raw) or HANGUL.search(raw) or not LATIN.search(raw): return False
    return True


def parse_cell(raw):
    """-> (qpre, qsuf, parts) ; parts = list cua ('sep', s) | ('txt', lead, core, trail, translatable)"""
    qpre, inner, qsuf = split_cell(raw)
    pieces = SEP_RE.split(inner)
    parts = []
    for k, p in enumerate(pieces):
        if k % 2:
            parts.append(('sep', p))
        else:
            lead, core, trail = strip_wrap(p)
            parts.append(('txt', lead, core, trail, is_translatable_core(core)))
    return qpre, qsuf, parts


def build_cell(qpre, qsuf, parts, tr=None):
    out = []
    for p in parts:
        if p[0] == 'sep': out.append(p[1])
        else:
            _, lead, core, trail, ok = p
            if ok and tr is not None and tr.get(core):
                core = tr[core]
            out.append(lead + core + trail)
    inner = ''.join(out)
    if ',' in inner and not qpre:
        qpre = qsuf = '"'
    return qpre + inner + qsuf


def iter_cells(group):
    """-> (lines, rows) ; rows[i] = (cells|None, header) ; dung de duyet"""
    lines = read_file(os.path.join(ENG_DIR, fname(group)))
    header = SPLIT_RE.split(lines[0])
    return lines, header


def col_kind(group, header, j):
    h = header[j] if j < len(header) else ''
    if group == 'Name': return 'ui'
    if '이름' in h: return 'name'
    return 'desc'


def load_vi(vi_dir=VI_DIR):
    """doc text/vi/*.tsv (cot key, vi) -> dict. Tra (dict, [canh bao])"""
    tr = {}; warn = []
    for p in sorted(glob.glob(os.path.join(vi_dir, '*.tsv'))):
        with open(p, encoding='utf-8', newline='') as f:
            rows = f.read().split('\n')
        hdr = rows[0].rstrip('\r').split('\t')
        if 'key' not in hdr or 'vi' not in hdr:
            warn.append('%s: thieu cot key/vi' % os.path.basename(p)); continue
        ki, vi = hdr.index('key'), hdr.index('vi')
        for n, r in enumerate(rows[1:], 2):
            r = r.rstrip('\r')
            if not r: continue
            c = r.split('\t')
            if len(c) <= max(ki, vi): continue
            k, v = c[ki], c[vi].strip()
            if not v: continue
            if k in tr and tr[k] != v:
                warn.append('%s:%d: key trung, ban dich khac nhau: %r' % (os.path.basename(p), n, k[:50]))
            tr[k] = v
    return tr, warn


def load_segments(path=None):
    path = path or os.path.join(ROOT, 'text', 'segments.tsv')
    with open(path, encoding='utf-8', newline='') as f:
        rows = [r.rstrip('\r').split('\t') for r in f.read().split('\n') if r.strip('\r')]
    hdr = rows[0]
    return hdr, [dict(zip(hdr, r)) for r in rows[1:]]
