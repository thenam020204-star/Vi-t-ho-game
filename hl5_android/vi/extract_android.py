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

WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))


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


def _ok_text(t, hi=True, strict=True):
    """chuỗi văn bản: >= 2 ký tự, in được (cho phép \\n và byte >= 0x80 lẻ như 0xf1 trong common_text rec 137),
    có ít nhất 1 chữ cái. Chuỗi trường số / giữ chỗ (' ', '-') bị loại."""
    if len(t) < 2:
        return False
    if strict and len(t) < 4:   # chuỗi rất ngắn (1M, 10K, 1K...) phải có >= 2 chữ cái, chỉ gồm chữ/số/space/./:
        if sum(1 for c in t if (65 <= c <= 90) or (97 <= c <= 122)) < 2:
            return False
        if not all(chr(c).isalnum() or c in b" .:" for c in t):
            return False
    has = False
    for c in t:
        if (65 <= c <= 90) or (97 <= c <= 122):
            has = True
        elif c == 0x0a or 0x20 <= c <= 0x7e or (hi and c >= 0x80):
            pass
        else:
            return False
    return has


# ---------------- bố cục bản ghi .dat (T1b) ----------------
# Lý do cũ sai: rec_strings (DP "cực đại độ phủ chữ cái") đoán chuỗi theo tiền tố độ dài u8/u16 trong từng bản ghi.
# Byte độ dài của chuỗi thật thường là chữ cái in được ('o'=111, 'D'=68, ';'=59, ' '=32...), nên DP chọn cách cắt
# khác (một chuỗi dài bị tách thành 2-3 mảnh, hoặc chuỗi bắt đầu giữa từ). KHÔNG phải do byte >= 0x80.
# Bố cục đúng là cố định theo từng file: dãy (bỏ k byte | chuỗi tiền tố độ dài u8/u16), hết bản ghi đúng bằng 'đuôi' k.
# Bằng chứng: hàm nạp trong libHeroesLore5.so (địa chỉ ở BAO-CAO / PLAN Nhật ký T1b) và mọi bản ghi của file đều tiêu đúng
# từng byte theo bố cục (kiểm trong extract_file; sai là lỗi, không phải bỏ qua).
def _item(tail):   # id(2) | tên u8 | int32 | mô tả u8 | đuôi        (ItemTable::LoadItemTable 0x9f82c)
    return [('k', 2), ('s', 1), ('k', 4), ('s', 1), ('k', tail)]


_TXT = [('s', 1), ('k', 0)]                # cả bản ghi = 1 chuỗi u8 (TextMgr::LoadRes 0x13fd54)
_CASH = lambda t: [('k', 3), ('s', 1), ('k', 2), ('s', 1), ('k', t)]   # CashShop::LoadCashShopData 0xd9f04
_HELP = [('s', 2), ('s', 2), ('k', 0)]     # tiêu đề u16 | nội dung u16     (TextMgr::LoadHelpString 0x13fc2c)
DAT_LAYOUT = {
    'c/csv/common_text.dat': _TXT, 'c/csv/ingame_text.dat': _TXT, 'c/csv/menu_text.dat': _TXT,
    'c/csv/mission_text.dat': _TXT, 'c/csv/name.dat': _TXT, 'c/csv/tips.dat': _TXT,
    'c/csv/buildup.dat': [('s', 1), ('k', 14)],
    'c/csv/class.dat': [('s', 1), ('k', 59)],                    # HERO::LoadResClassInfo 0x881f0
    'c/csv/mission_list.dat': [('s', 1), ('k', 34)],             # Mission::LoadMissionTable 0x87a78
    'c/csv/set_option.dat': [('s', 1), ('k', 8)],
    'c/csv/cash_expert.dat': _CASH(2), 'c/csv/cash_network.dat': _CASH(2), 'c/csv/cash_single.dat': _CASH(2),
    'vfs:0x2b3c2959.dat': _CASH(6), 'vfs:0x831ffd41.dat': _CASH(6),   # cash_*_android.dat
    'c/csv/quest_0.dat': [('k', 3), ('s', 1), ('s', 1), ('s', 1), ('k', 38)],   # StateInGameMenu::SetMapMode 0xe553c (insn e5854-e5884)
    'c/csv/quest_1.dat': [('k', 3), ('s', 1), ('s', 1), ('s', 1), ('k', 38)],
    'c/csv/quest_2.dat': [('k', 3), ('s', 1), ('s', 1), ('s', 1), ('k', 38)],
    'c/csv/skill_00.dat': [('s', 1), ('k', 47), ('s', 1), ('k', 0)],            # HERO::LoadResSkillInfo 0x87ee0
    'c/csv/skill_01.dat': [('s', 1), ('k', 47), ('s', 1), ('k', 0)],
    'c/csv/skill_02.dat': [('s', 1), ('k', 47), ('s', 1), ('k', 0)],
    'c/csv/skill_03.dat': [('s', 1), ('k', 47), ('s', 1), ('k', 0)],
    'c/csv/skill_05.dat': [('s', 1), ('k', 47), ('s', 1), ('k', 0)],
    'c/csv/smith_0.dat': [('k', 2), ('s', 1), ('k', 4), ('s', 1), ('k', 13)],   # HERO::LoadMixSmithTableInfo 0x87c94
    'c/csv/smith_1.dat': [('k', 2), ('s', 1), ('k', 4), ('s', 1), ('k', 13)],
    'c/csv/smith_2.dat': [('k', 2), ('s', 1), ('k', 4), ('s', 1), ('k', 13)],
    'c/csv2/help_ktf.dat': _HELP, 'c/csv2/help_lgt.dat': _HELP, 'c/csv2/help_skt.dat': _HELP,
    'vfs:0x92eafcd8.dat': _HELP, 'vfs:0xc28f807a.dat': _HELP, 'vfs:0xe6bbcc45.dat': _HELP,   # help_android_*.dat
}
for _f in ('enemy_0', 'enemy_1', 'enemy_2', 'enemy_expert_0', 'enemy_expert_1', 'enemy_expert_2'):
    DAT_LAYOUT['c/csv/%s.dat' % _f] = [('s', 1), ('k', 128)]     # Monster::setEnemyData 0xbd990
for _i, _t in enumerate([21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 21, 4, 2, 0, 0, 13, 4, 4, 2]):
    DAT_LAYOUT['c/csv/item_%02d.dat' % _i] = _item(_t)           # case _i của ItemTable::LoadItemTable


def walk_rec(r, layout):
    """Đi bản ghi theo bố cục. Trả về [(off, ps, n)] của mọi trường chuỗi, hoặc None nếu không tiêu đúng hết bản ghi."""
    p = 0; out = []
    for t, v in layout:
        if t == 'k':
            p += v
        else:
            if p + v > len(r):
                return None
            n = int.from_bytes(r[p:p + v], 'little')
            if p + v + n > len(r):
                return None
            out.append((p, v, n)); p += v + n
    return out if p == len(r) else None


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
            if not _ok_text(t, False, False):
                continue
            items.append({'file': name, 'kind': 'scn', 'idx': i, 'text_en': t.decode('latin-1'),
                          'nul': nul, 'cap': (254 if wS == 1 else 65534)})
    else:
        recs = parse_dat(b)
        lay = DAT_LAYOUT.get(name)
        if recs is None or lay is None:
            return None
        for ri, r in enumerate(recs):
            w = walk_rec(r, lay)
            if w is None:
                raise ValueError('%s rec %d không khớp bố cục' % (name, ri))
            for (o, ps, n) in w:
                t = r[o + ps:o + ps + n]
                if _ok_text(t):
                    items.append({'file': name, 'kind': 'dat', 'rec': ri, 'off': o, 'ps': ps,
                                  'text_en': t.decode('latin-1'), 'cap': 255 if ps == 1 else 4000})
    return items


def classify(name, b):   # name: 'c/...' hoặc 'vfs:0xHASH.dat' cho entry không có tên LGT
    """'scn' | 'dat' | None cho entry VFS ứng viên."""
    if name and name.startswith('c/map/') and name.endswith('.scn'):
        return 'scn'
    if name in DAT_LAYOUT:
        return 'dat'
    return None


def run(vfs_path, lgt_root):
    ent, order = load_vfs(vfs_path)
    names = name_table(lgt_root)
    cat = []; files = {}; stats = collections.OrderedDict()
    for h in order:
        b = ent[h]; nm = names.get(h)
        fname = nm or ('vfs:0x%08x.dat' % h)
        kind = classify(fname, b)
        if not kind:
            continue
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


def frag_check(cat):
    """Mảnh cắt dở: 2 mục liền kề (không hở byte) cùng bản ghi mà mục sau bắt đầu chữ thường ngay sau chữ cái cuối của mục trước
    (nối lại thành 1 chuỗi hợp lệ giữa từ). Phải = 0."""
    byr = collections.defaultdict(list)
    for it in cat:
        if it['kind'] == 'dat':
            byr[(it['file'], it['rec'])].append(it)
    bad = []
    for k, l in byr.items():
        l.sort(key=lambda e: e['off'])
        for a, b in zip(l, l[1:]):
            if a['off'] + a['ps'] + len(a['text_en']) == b['off'] and b['text_en'][0].islower() and a['text_en'][-1].isalpha():
                bad.append((k, a['text_en'][-10:], b['text_en'][:10]))
    return bad


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
    fr = frag_check(cat)
    print('mảnh cắt giữa từ (dat):', fr if fr else 0)
    nrec = sum(len(parse_dat(b)) for k, b in files.values() if k == 'dat')
    print('dat: %d bản ghi / %d file đều tiêu đúng bố cục (extract_file báo lỗi nếu không)' % (nrec, sum(1 for k, _ in files.values() if k == 'dat')))
    print('--- số câu theo file ---')
    for f, n in sorted(per.items()):
        print('%-28s %5d' % (f, n))
    return 1 if (bad or fr) else 0


if __name__ == '__main__':
    sys.exit(main())
