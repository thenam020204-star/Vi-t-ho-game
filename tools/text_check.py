#!/usr/bin/env python3
"""Kiem tra ban dich text/vi/*.tsv (cot key, vi) so voi text/segments.tsv.
Dung: python3 -I tools/text_check.py [--vi-dir DIR] [--strict] [--report FILE]
LOI (exit 1): thieu/du token (ngoac mau, <color>, ##id##, so, %, □ㅁ), co @ ^ ` " \\ tab/xuong dong, khong NFC, ky tu ngoai cmap Jeju-VI,
              thua key la, thieu thuat ngu glossary, rong. --strict: thieu ban dich cung la loi.
CANH BAO: rong (Jeju-VI, co 24) VI > 115% EN (>= 40 ky tu EN: 130%) - D11."""
import sys, os, re, argparse, unicodedata, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
from text_common import *

def load_glossary():
    terms = []
    with open(os.path.join(ROOT, 'text', 'glossary.tsv'), encoding='utf-8') as f:
        for l in f.read().split('\n')[1:]:
            c = l.split('\t')
            if len(c) >= 2 and c[0]:
                terms.append((c[0], [v.strip().lower() for v in c[1].split('|')]))
    terms.sort(key=lambda t: -len(t[0]))
    return terms

def gloss_hits(en, terms):
    """cac thuat ngu glossary xuat hien trong EN (khop tu nguyen, cho phep s/es; thuat ngu dai che thuat ngu ngan)"""
    s = en; hits = []
    for t, vi in terms:
        pat = re.compile(r'(?<![A-Za-z])' + re.escape(t) + r'(?:e?s)?(?![A-Za-z])', 0 if (t.isupper() and len(t) > 1 and t not in ('HP', 'ATK')) else re.I)
        if pat.search(s):
            hits.append((t, vi))
            s = pat.sub(' ' * 3, s)
    return hits

def strip_markup(s):
    s = COLOR_TAG.sub('', s)
    return ''.join(c for c in s if c not in MARKUP_CHARS)

def tokens(s):
    return {
        'ngoac/mau': collections.Counter(c for c in s if c in MARKUP_CHARS),
        'the <color>': collections.Counter(COLOR_TAG.findall(s)),
        '##id##': collections.Counter(ID_RE.findall(s)),
        'so': collections.Counter(re.findall(r'\d+(?:\.\d+)?', ID_RE.sub('', s))),
        '%': collections.Counter({'%': s.count('%')}) if '%' in s else collections.Counter(),
        'placeholder': collections.Counter(c for c in s if c in PLACEHOLDER_CHARS),
    }

def balanced(s):
    st = []
    for c in s:
        if c in OPEN: st.append(PAIRS[c])
        elif c in CLOSE:
            if not st or st.pop() != c: return False
    return not st

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--vi-dir', default=VI_DIR)
    ap.add_argument('--strict', action='store_true')
    ap.add_argument('--report', default=os.path.join(ROOT, 'text', 'check_report.txt'))
    a = ap.parse_args()
    _, segs = load_segments()
    by_key = {s['key']: s for s in segs}
    tr, warn = load_vi(a.vi_dir)
    terms = load_glossary()
    cmap = None; face = None
    try:
        from fontTools.ttLib import TTFont
        cmap = set(TTFont(FONT_VI).getBestCmap())
        import freetype
        face = freetype.Face(FONT_VI); face.set_pixel_sizes(0, 24)
    except Exception as e:
        warn.append('khong nap duoc font (%s): bo qua kiem cmap/rong' % e)
    def width(s):
        w = 0
        for ch in strip_markup(s):
            face.load_char(ch); w += face.glyph.advance.x / 64.0
        return w
    errs = []; warns = []; ratios = []
    for k, v in tr.items():
        if k not in by_key: errs.append(('KEYLA', k, 'key khong co trong segments.tsv')); continue
        if v != unicodedata.normalize('NFC', v): errs.append(('NFC', k, 'khong phai NFC'))
        bad = [c for c in v if c in '"\\\t\r\n@^`']
        if bad: errs.append(('CAM', k, 'ky tu cam %r' % bad[0]))
        if v != v.strip(): errs.append(('KHOANGTRANG', k, 'thua khoang trang dau/cuoi'))
        te, tv = tokens(k), tokens(v)
        for name in te:
            if te[name] != tv[name]:
                errs.append(('TOKEN', k, '%s khac: EN %s / VI %s' % (name, dict(te[name]), dict(tv[name]))))
        if not balanced(v): errs.append(('NGOAC', k, 'ngoac mau khong can doi/sai thu tu'))
        if cmap:
            miss = sorted({c for c in strip_markup(v) if ord(c) not in cmap and c not in PLACEHOLDER_CHARS})
            if miss: errs.append(('CMAP', k, 'ky tu ngoai cmap: ' + ''.join(miss)))
        vl = v.lower()
        for t, vis in gloss_hits(k, terms):
            if not any(x in vl for x in vis):
                errs.append(('GLOSS', k, 'EN co "%s" -> can "%s"' % (t, '|'.join(vis))))
        if face:
            we, wv = width(k), width(v)
            if we > 0:
                r = wv / we; ratios.append(r)
                lim = 1.30 if len(k) >= 40 else 1.15
                if r > lim: warns.append(('RONG', k, '%.0f%% (EN %.0fpx, VI %.0fpx, nguong %d%%)' % (r * 100, we, wv, round(lim * 100))))
    missing = [k for k in by_key if k not in tr]
    if a.strict and missing: errs.append(('THIEU', '', '%d key chua dich' % len(missing)))
    out = []
    out.append('text_check: %d/%d key da dich, %d chua dich' % (len(tr), len(by_key), len(missing)))
    for w in warn: out.append('canh bao: ' + w)
    c = collections.Counter(e[0] for e in errs)
    out.append('LOI: %d %s' % (len(errs), dict(c)))
    cw = collections.Counter(e[0] for e in warns)
    out.append('CANH BAO: %d %s' % (len(warns), dict(cw)))
    if ratios: out.append('ty le rong VI/EN: trung binh %.0f%%, max %.0f%%' % (100 * sum(ratios) / len(ratios), 100 * max(ratios)))
    out.append('')
    for tag, lst in (('LOI', errs), ('CANH BAO', warns)):
        for code, k, msg in lst:
            ref = by_key.get(k, {}).get('no', '?')
            out.append('%s [%s] #%s %s | %s' % (tag, code, ref, k[:70], msg))
    txt = '\n'.join(out) + '\n'
    open(a.report, 'w', encoding='utf-8').write(txt)
    print('\n'.join(out[:6] + (out[6:26] if len(out) > 6 else [])))
    print('(bao cao day du: %s)' % a.report)
    sys.exit(1 if errs else 0)

main()
