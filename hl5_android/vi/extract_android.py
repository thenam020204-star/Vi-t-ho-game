#!/usr/bin/env python3
"""T1: trích danh mục chuỗi tiếng Anh của bản Android (data.vfs.mp3) -> android_catalog.json

Dùng:  python3 -I extract_android.py [--vfs PATH] [--lgt DIR] [--out PATH] [--check]
  --check : chạy lại và so sánh với file đã có, kiểm roundtrip từng file.

Mục: {file, kind: scn|dat, idx | (rec, off, ps), text_en, cap}
  - file: đường dẫn VFS (vd 'c/map/00001.scn'); entry VFS không có tên LGT -> 'vfs:0xHASH.dat'
  - scn: idx = chỉ số trong bảng chuỗi; text_en = chuỗi bỏ '\\0' cuối (latin-1); nul = 1 nếu chuỗi gốc có '\\0' cuối;
         cap = 254 (u8) hoặc 65534 (u16)  (độ dài tối đa trừ '\\0')
  - dat: rec = chỉ số bản ghi, off = offset tiền tố độ dài trong bản ghi, ps = 1|2 (rộng tiền tố),
         cap = 255 (ps 1) / 4000 (ps 2) như catalog5
Chỉ giữ chuỗi có chữ cái (scn: mọi chuỗi có chữ cái; chuỗi trống/số giữ trong 'scn_other' không xuất).
"""
import os, sys, json, struct, glob, argparse, collections

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, os.path.join(os.path.dirname(HERE), 'tools'))
import scn as SCN

WORK = os.environ.get('WORK', '/tmp/claude-0/-home-user-Vi-t-ho-game/31266a47-2a55-5b6d-86d1-5e3a70f1f083/scratchpad')


def djb(s):
    h = 5381
    for c in s.encode():
        h = (h * 33 + c) & 0xffffffff
    return h


def load_vfs(path):
    d = open(path, 'rb').read()
    o = 0; ent = {}; order = []
    while o + 8 <= len(d):
        h, s = struct.unpack_from('<II', d, o)
        ent[h] = d[o + 8:o + 8 + s]; order.append(h); o += 8 + s
    assert o == len(d)
    return ent, order


def name_table(lgt_root):
    """hash -> 'c/...' từ cây LGT đã giải nén."""
    names = {}
    base = os.path.join(lgt_root, 'c')
    for p in glob.glob(base + '/**/*', recursive=True):
        if os.path.isfile(p):
            rel = 'c/' + os.path.relpath(p, base).replace(os.sep, '/')
            names[djb(rel)] = rel
    return names


# ---------------- .scn ----------------
def parse_scn(b):
    """Như tools/scn.parse nhưng chọn điểm bắt đầu đúng khi có nhiều điểm khớp EOF
    (vd c/map/00154.scn: scn.parse lấy hits[-1] sai). Header chuẩn dài 15 byte (mọi file VFS có hit ở s=15): ưu tiên s==15, không có thì hit nhiều chuỗi nhất.
    Trả (offset bảng chuỗi, [chuỗi], s) hoặc None."""
    if len(b) < 4:
        return None
    wA, wB, wS = b[0], b[1], b[2]
    if not (wA in (1, 2) and wB in (1, 2) and wS in (1, 2)):
        return None
    hits = []
    for s in range(3, len(b)):
        try:
            p = SCN._scripts(b, s, wA); p = SCN._scripts(b, p, wB)
            if p >= len(b):
                continue
            q, strs = SCN._strings(b, p, wS)
            if q == len(b):
                hits.append((p, strs, s))
        except IndexError:
            continue
    if not hits:
        return None
    return max(hits, key=lambda h: (h[2] == 15, len(h[1]), -h[2]))


def build_scn(b, strs):
    r = parse_scn(b); p = r[0]; wS = b[2]
    assert len(strs) < 256
    out = bytearray(b[:p]); out.append(len(strs))
    for s in strs:
        assert len(s) < (256 if wS == 1 else 65536), 'quá dài'
        out += bytes([len(s)]) if wS == 1 else struct.pack('<H', len(s))
    for s in strs:
        out += s
    return bytes(out)


# ---------------- .dat ----------------
def parse_dat(b):
    if len(b) < 2:
        return None
    n = struct.unpack_from('<H', b, 0)[0]; p = 2; recs = []
    for _ in range(n):
        if p + 2 > len(b):
            return None
        l = struct.unpack_from('<H', b, p)[0]; recs.append(b[p + 2:p + 2 + l]); p += 2 + l
    if p != len(b):
        return None
    return recs


def build_dat(recs):
    out = bytearray(struct.pack('<H', len(recs)))
    for r in recs:
        out += struct.pack('<H', len(r)) + r
    return bytes(out)


def _ok_text(t, strict=False):
    """chuỗi ASCII in được (cho phép \\n), có ít nhất 1 chữ cái.
    strict (dùng cho .dat): chuỗi < 4 ký tự phải có >= 2 chữ cái và chỉ gồm chữ/số/space/./: (loại nhiễu của trường số)."""
    has = False
    if len(t) < 2:
        return False
    if strict and len(t) < 4:
        if sum(1 for c in t if (65 <= c <= 90) or (97 <= c <= 122)) < 2:
            return False
        if not all(chr(c).isalnum() or c in b" .:" for c in t):
            return False
    for c in t:
        if c == 0x0a or 0x20 <= c <= 0x7e:
            if (65 <= c <= 90) or (97 <= c <= 122):
                has = True
        else:
            return False
    return has


def rec_strings(r):
    """Chuỗi ASCII có tiền tố độ dài u8/u16 trong 1 bản ghi; DP cực đại độ phủ chữ cái.
    Trả về [(off, ps, n)]."""
    L = len(r)
    best = [0] * (L + 1); choice = [None] * (L + 1)
    for i in range(L - 1, -1, -1):
        best[i] = best[i + 1]
        n = r[i]
        if n >= 1 and i + 1 + n <= L and _ok_text(r[i + 1:i + 1 + n], True):
            t = r[i + 1:i + 1 + n]
            v = sum(1 for c in t if 65 <= c <= 122 and (c <= 90 or c >= 97)) * 2 + 1 + best[i + 1 + n]
            if v > best[i]:
                best[i] = v; choice[i] = (1, n)
        if i + 1 < L:
            n2 = n | (r[i + 1] << 8)
            if n2 >= 2 and i + 2 + n2 <= L and _ok_text(r[i + 2:i + 2 + n2], True):
                t = r[i + 2:i + 2 + n2]
                v = sum(1 for c in t if 65 <= c <= 122 and (c <= 90 or c >= 97)) * 2 + 1 + best[i + 2 + n2]
                if v > best[i]:
                    best[i] = v; choice[i] = (2, n2)
    out = []; i = 0
    while i < L:
        c = choice[i]
        if c:
            out.append((i, c[0], c[1])); i += c[0] + c[1]
        else:
            i += 1
    return out


# ---------------- trích ----------------
def extract_file(name, b, kind):
    items = []
    if kind == 'scn':
        r = parse_scn(b)
        if r is None:
            # c/map/00206, 00217: header lạ (b0=0 hoặc b1=0), bảng chuỗi chỉ là 'VALUE' -> không có gì để dịch
            return [] if b.endswith(b'VALUE\x00') else None
        wS = b[2]
        for i, s in enumerate(r[1]):
            nul = 1 if s.endswith(b'\0') else 0
            t = s[:-1] if nul else s
            if not _ok_text(t):
                continue
            items.append({'file': name, 'kind': 'scn', 'idx': i, 'text_en': t.decode('latin-1'),
                          'nul': nul, 'cap': (254 if wS == 1 else 65534)})
    else:
        recs = parse_dat(b)
        if recs is None:
            return None
        for ri, r in enumerate(recs):
            for (o, ps, n) in rec_strings(r):
                items.append({'file': name, 'kind': 'dat', 'rec': ri, 'off': o, 'ps': ps,
                              'text_en': r[o + ps:o + ps + n].decode('latin-1'),
                              'cap': 255 if ps == 1 else 4000})
    return items


def classify(name, b):
    """'scn' | 'dat' | None cho entry VFS ứng viên."""
    if name and name.startswith('c/map/') and name.endswith('.scn'):
        return 'scn'
    if name and (name.startswith('c/csv/') or name.startswith('c/csv2/')) and name.endswith('.dat'):
        return 'dat'
    if name is None:   # entry không tên: nhận .dat nếu parse khớp EOF và có chuỗi ASCII
        if len(b) > 4 and not b.startswith((b'OggS', b'\x89PNG')):
            recs = parse_dat(b)
            if recs and any(rec_strings(r) for r in recs):
                return 'dat'
    return None


def run(vfs_path, lgt_root):
    ent, order = load_vfs(vfs_path)
    names = name_table(lgt_root)
    cat = []; files = {}; stats = collections.OrderedDict()
    for h in order:
        b = ent[h]; nm = names.get(h)
        kind = classify(nm, b)
        if not kind:
            continue
        fname = nm or ('vfs:0x%08x.dat' % h)
        items = extract_file(fname, b, kind)
        if items is None:
            stats[fname] = {'kind': kind, 'error': 'parse'}
            continue
        files[fname] = (kind, b)
        stats[fname] = {'kind': kind, 'n': len(items)}
        cat.extend(items)
    return cat, files, stats


# ---------------- ghi lại (dùng cho roundtrip và T4) ----------------
def apply_file(kind, b, items, newtext=None):
    """Ghi lại file với chuỗi thay thế. items: các mục catalog của file; newtext: {index trong items: bytes/str}.
    Không thay (None) thì giữ nguyên chuỗi gốc. Trả về bytes."""
    newtext = newtext or {}
    def enc(it, k):
        t = newtext.get(k, it['text_en'])
        return t.encode('latin-1') if isinstance(t, str) else bytes(t)
    if kind == 'scn':
        r = parse_scn(b); strs = list(r[1]); wS = b[2]
        for k, it in enumerate(items):
            strs[it['idx']] = enc(it, k) + (b'\0' if it.get('nul', 1) else b'')
        return build_scn(b, strs)
    recs = parse_dat(b)
    byrec = collections.defaultdict(list)
    for k, it in enumerate(items):
        byrec[it['rec']].append((it['off'], k, it))
    for ri, lst in byrec.items():
        r = bytearray(recs[ri])
        for off, k, it in sorted(lst, key=lambda x: -x[0]):
            ps = it['ps']; old_n = int.from_bytes(r[off:off + ps], 'little')
            t = enc(it, k)
            assert len(t) < (1 << (8 * ps)), 'quá dài'
            r[off:off + ps + old_n] = len(t).to_bytes(ps, 'little') + t
        recs[ri] = bytes(r)
    return build_dat(recs)


def roundtrip(files, cat):
    byf = collections.defaultdict(list)
    for it in cat:
        byf[it['file']].append(it)
    bad = []
    for fname, (kind, b) in files.items():
        # (1) ghi lại chuỗi gốc
        if not byf.get(fname):
            continue
        out = apply_file(kind, b, byf.get(fname, []))
        if out != b:
            bad.append((fname, 'same-text'))
            continue
        # (2) thay bằng chuỗi khác độ dài rồi đọc lại phải thấy đúng chuỗi mới
        its = byf.get(fname, [])
        if its:
            nt = {k: 'Zz' + ''.join(ch if ch.isalnum() and ord(ch) < 128 else 'a' for ch in it['text_en'][:5]) + 'qq' for k, it in enumerate(its)}
            out2 = apply_file(kind, b, its, nt)
            again = extract_file(fname, out2, kind)
            if again is None or [x['text_en'] for x in again] != [nt[k] for k in range(len(its))]:
                bad.append((fname, 'changed-text'))
    return bad


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--vfs', default=WORK + '/apk/assets/data.vfs.mp3')
    ap.add_argument('--lgt', default=WORK + '/lgt5/j/res')
    ap.add_argument('--out', default=os.path.join(HERE, 'android_catalog.json'))
    ap.add_argument('--check', action='store_true')
    a = ap.parse_args()
    cat, files, stats = run(a.vfs, a.lgt)
    txt = json.dumps(cat, ensure_ascii=False, indent=0, sort_keys=False)
    bad = roundtrip(files, cat)
    if a.check:
        same = os.path.exists(a.out) and open(a.out, encoding='utf-8').read() == txt
        print('giống file cũ:', same)
    else:
        open(a.out, 'w', encoding='utf-8').write(txt)
    kinds = collections.Counter(it['kind'] for it in cat)
    per = collections.Counter()
    for it in cat:
        per[it['file']] += 1
    print('tổng câu:', len(cat), dict(kinds), '| số file:', len(files),
          '(scn %d, dat %d)' % (sum(1 for k, _ in files.values() if k == 'scn'), sum(1 for k, _ in files.values() if k == 'dat')))
    print('file parse lỗi:', [f for f, s in stats.items() if 'error' in s])
    print('roundtrip lỗi:', bad if bad else 0)
    print('--- số câu theo file ---')
    for f, n in sorted(per.items()):
        print('%-28s %5d' % (f, n))
    return 1 if bad else 0


if __name__ == '__main__':
    sys.exit(main())
