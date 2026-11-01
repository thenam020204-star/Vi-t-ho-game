#!/usr/bin/env python3
"""T2: ghép chuỗi Android (tiếng Anh) <-> chuỗi LGT (tiếng Hàn, có ID K#####).

Chạy:  python3 -I align_android.py   (đọc android_catalog.json, ghi align.json + align_report.md)

Phương pháp (xem align_report.md):
  .scn  cùng số câu bảng chuỗi -> ghép theo chỉ số, chấm điểm hợp lý (mã điều khiển, dấu câu, số, tỉ lệ độ dài, tên riêng);
        file mà tỉ lệ cặp điểm cao < 80% thì chạy thêm DP và lấy phương án tốt hơn.
        khác số câu -> DP Needleman-Wunsch (điểm = sim - 0.3, khoảng trống 0).
  .dat  ghép bản ghi bằng DP (dải |i-j|<=BAND) theo độ giống "khung xương" (bản ghi bỏ chuỗi) + độ giống chuỗi,
        rồi ghép chuỗi trong bản ghi theo thứ tự (DP nếu lệch số chuỗi).
  File VFS không có tên (vfs:0x...) -> thử mọi file .dat LGT, lấy file có tổng điểm DP cao nhất.
"""
import os, sys, re, json, math, struct, collections, difflib

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
sys.path.insert(0, os.path.join(os.path.dirname(HERE), 'tools'))
import extract_android as EA
import scn as SCN

WORK = os.environ.get('WORK', '/tmp/claude-0/-home-user-Vi-t-ho-game/31266a47-2a55-5b6d-86d1-5e3a70f1f083/scratchpad')
V5 = WORK + '/proj/HL4_project/vi5'
LGT = WORK + '/lgt5/j/res'
VFS = WORK + '/apk/assets/data.vfs.mp3'

THR_NEW = 0.55    # dưới ngưỡng này coi là câu mới
THR_HIGH = 0.70   # "điểm cao"
BAND = 45

# ----------------------------------------------------------------- đặc trưng
CODE_RE = re.compile(r'#\d\d|%[ds]|[&|${}\'"\\^<\[]')
HAN_RE = re.compile(r'[가-힣]')


APOS_RE = re.compile(r"(?<=[A-Za-z])'(?=[A-Za-z])")


def split_codes(t, en=False):
    if en:
        t = t.replace('\\u0000', '')        # 2 câu Android có chữ '\u0000' dính cuối (lỗi bản Anh hoá)
        t = APOS_RE.sub('', t)              # dấu nháy sở hữu "Marc's" trong tiếng Anh không phải mã
    codes = collections.Counter(CODE_RE.findall(t))
    semi = t.count(';')
    plain = CODE_RE.sub('', t).replace(';', ' ')
    return codes, semi, plain


def kbytes(s):
    return sum(2 if ord(c) > 127 else 1 for c in s)


def term_class(plain):
    p = plain.strip()
    if p.endswith('...') or p.endswith('…'): return 'E'
    if p.endswith('?'): return 'Q'
    if p.endswith('!'): return 'X'
    if p.endswith('.'): return 'P'
    return 'N'


NUM_RE = re.compile(r'\d+')


class Sim:
    """Chấm điểm 0..1 cho cặp (câu Anh, câu Hàn)."""

    def __init__(self, namemap=None):
        self.nm = namemap or {}        # từ Hàn -> set(token Anh thường)
        self.cache = {}

    def __call__(self, a, k):
        key = (a, k)
        v = self.cache.get(key)
        if v is None:
            v = self.cache[key] = self._sim(a, k)
        return v

    def _sim(self, a, k):
        ca, sa, pa = split_codes(a, True)
        ck, sk, pk = split_codes(k)
        ws = 0.0; tot = 0.0
        # mã điều khiển (trừ ';')
        n1 = sum(ca.values()); n2 = sum(ck.values())
        if n1 or n2:
            l1 = sum(abs(ca[c] - ck[c]) for c in set(ca) | set(ck))
            f = 1 - l1 / max(1, sum(max(ca[c], ck[c]) for c in set(ca) | set(ck)))
        else:
            f = 1.0
        tot += 3 * f; ws += 3
        # ';' xuống dòng: bản Anh có thể ngắt lại dòng nên chỉ so tương đối
        if sa and sk:
            f = 1 - abs(sa - sk) / max(sa, sk, 1)
            tot += 1 * f; ws += 1
        # dấu cuối câu
        ta, tk = term_class(pa), term_class(pk)
        f = 1.0 if ta == tk else (0.4 if 'N' in (ta, tk) and {ta, tk} & {'P'} else (0.5 if {ta, tk} == {'E', 'P'} else 0.0))
        tot += 2 * f; ws += 2
        # số
        na, nk = NUM_RE.findall(pa), NUM_RE.findall(pk)
        if na or nk:
            f = 1.0 if na == nk else (0.5 if sorted(na) == sorted(nk) else 0.0)
            tot += 2 * f; ws += 2
        # tỉ lệ độ dài
        la = max(1, len(pa.strip())); lk = max(1, kbytes(pk.strip()))
        r = la / lk
        lo, hi = (0.35, 6.0) if lk <= 8 else ((0.5, 4.0) if lk < 14 else (0.5, 3.5))
        d = max(0.0, math.log(lo / r), math.log(r / hi))
        tot += 2 * max(0.0, 1 - d); ws += 2
        # tên riêng
        ev = self.names(pa, pk)
        if ev:
            tot += 2 * (sum(ev) / len(ev)); ws += 2
        v = tot / ws
        # veto: câu Hàn là chỗ giữ chỗ ngắn ("여분") còn câu Anh dài, hoặc Anh dài hơn hẳn và nhiều dòng hơn hẳn (bản Anh có thêm nội dung)
        if max(la, lk) >= 16 and (r > 8 or r < 0.12): v = min(v, 0.5)
        if sa >= 2 * sk + 1 and sk > 0 and r > 3.2 and la > 40: v = min(v, 0.5)
        return v

    def names(self, pa, pk):
        if not self.nm: return []
        la = set(re.findall(r'[a-z]{3,}', pa.lower()))
        out = []
        for t, toks in self.nm.items():
            if t in pk:
                out.append(1.0 if (la & toks) else 0.0)
        return out


# ----------------------------------------------------------------- DP
def dp_align(na, nk, simfn, thr=0.3, band=None):
    """Ghép tuần tự (không bắt chéo) cực đại tổng (sim - thr). Trả về [(i, j, sim)]."""
    NEG = -1e18
    best = [[0.0] * (nk + 1) for _ in range(na + 1)]
    mv = [[0] * (nk + 1) for _ in range(na + 1)]   # 0 diag,1 up(skip a),2 left(skip k)
    sims = {}
    for i in range(na - 1, -1, -1):
        for j in range(nk - 1, -1, -1):
            b = best[i + 1][j]; m = 1
            if best[i][j + 1] > b: b = best[i][j + 1]; m = 2
            if band is None or abs(i - j) <= band:
                s = simfn(i, j)
                sims[(i, j)] = s
                if s > thr:
                    v = s - thr + best[i + 1][j + 1]
                    if v >= b: b = v; m = 0
            best[i][j] = b; mv[i][j] = m
    out = []; i = j = 0
    while i < na and j < nk:
        m = mv[i][j]
        if m == 0: out.append((i, j, sims[(i, j)])); i += 1; j += 1
        elif m == 1: i += 1
        else: j += 1
    return out, best[0][0]


# ----------------------------------------------------------------- nạp dữ liệu
def load_all():
    acat = json.load(open(os.path.join(HERE, 'android_catalog.json'), encoding='utf-8'))
    kcat = json.load(open(V5 + '/catalog5.json', encoding='utf-8'))
    ids = json.load(open(V5 + '/ids5.json', encoding='utf-8'))
    return acat, kcat, ids


def kfile_key(f):
    return f[len('res/'):] if f.startswith('res/') else f


def load_nameseeds():
    seeds = {}
    for line in open(V5 + '/tr_in/thuat-ngu.tsv', encoding='utf-8').read().split('\n')[1:]:
        p = line.split('\t')
        if len(p) >= 3 and HAN_RE.search(p[0]) and len(p[0]) >= 2:
            seeds[p[0]] = p[1]
    return seeds


def learn_names(pairs, seeds):
    """pairs: [(en, ko)] đã ghép chắc chắn (cùng số câu). Trả về {từ Hàn: set(token Anh)}."""
    stop = set('the and you that this have with for are was but not what your they him her his its our from there will can been would could should about then them than into just like out one now all get got did does has had who how why when where which these those were let well yes some any more very too only also over such their here make made want need know think said say going come back take give tell see look must may might shall each every both even still much many most other another because while until after before again never always right good great'.split())
    N = len(pairs)
    df = collections.Counter(); toks_of = []
    capc = collections.Counter(); allc = collections.Counter()
    for en, ko in pairs:
        for w in re.findall(r'[A-Za-z]{3,}', CODE_RE.sub('', en)):
            allc[w.lower()] += 1
            if w[0].isupper(): capc[w.lower()] += 1
        t = set(re.findall(r'[a-z]{3,}', CODE_RE.sub('', en).lower())) - stop
        toks_of.append(t); df.update(t)
    iscap = lambda w: capc[w] >= 0.6 * allc[w] and allc[w] > 0
    nm = {}
    for T, vi in seeds.items():
        idx = [i for i, (en, ko) in enumerate(pairs) if T in ko]
        n = len(idx)
        if n == 0: continue
        co = collections.Counter()
        for i in idx: co.update(toks_of[i])
        good = set()
        seedtoks = set(re.findall(r'[a-z]{3,}', vi.lower())) if vi.isascii() else set()
        for w, c in co.items():
            if w in stop or not iscap(w): continue
            frac = c / n
            base = df[w] / N
            if n >= 2 and frac >= 0.6 and frac >= 4 * base and c >= 2: good.add(w)
            elif n >= 1 and w in seedtoks and c >= 1: good.add(w)
        if good: nm[T] = good
    return nm


# ----------------------------------------------------------------- scn
def align_scn_file(fname, aitems, ktab_n, kitems_by_idx, atab_n, sim):
    """aitems: mục catalog Android của file. kitems_by_idx: {idx: src hàn}. Trả về {id_item: (src|None, score, method, note)}"""
    res = {}
    if atab_n == ktab_n:
        for it in aitems:
            i = it['idx']; ksrc = kitems_by_idx.get(i)
            if ksrc is None:
                res[id(it)] = (None, 0.0, 'idx', 'K không có chữ Hàn ở chỉ số này')
            else:
                res[id(it)] = (ksrc, sim(it['text_en'], ksrc), 'idx', '')
        scores = [v[1] for v in res.values() if v[0] is not None]
        okfrac = (sum(1 for s in scores if s >= THR_HIGH) / len(aitems)) if aitems else 1.0
        if okfrac >= 0.8:
            # cờ lệch: cặp có hàng xóm điểm cao hơn hẳn
            for it in aitems:
                i = it['idx']; v = res[id(it)]
                if v[0] is None: continue
                for d in (-1, 1):
                    k2 = kitems_by_idx.get(i + d)
                    if k2 and sim(it['text_en'], k2) > v[1] + 0.25:
                        res[id(it)] = (v[0], v[1], 'idx', 'nghi lệch ' + ('%+d' % d))
                        break
            return res, 'idx'
    # DP trên chuỗi có chữ
    kidx = sorted(kitems_by_idx)
    pairs, _ = dp_align(len(aitems), len(kidx), lambda i, j: sim(aitems[i]['text_en'], kitems_by_idx[kidx[j]]))
    res2 = {id(it): (None, 0.0, 'dp', 'không ghép được') for it in aitems}
    for i, j, s in pairs:
        res2[id(aitems[i])] = (kitems_by_idx[kidx[j]], s, 'dp', '')
    if res and sum(v[1] for v in res.values()) >= sum(v[1] for v in res2.values()):
        return res, 'idx'
    return res2, 'dp'


# ----------------------------------------------------------------- dat
def skeleton(rec, spans):
    out = bytearray(); p = 0
    for (o, ps, n) in sorted(spans):
        out += rec[p:o]; p = o + ps + n
    out += rec[p:]
    return bytes(out)


def kspans(r, kitems):
    return [(x['off'], x['ps'], len(x['src'].encode('euc-kr'))) for x in kitems]


def skel_sim(sa, sk):
    if sa == sk: return 1.0
    if not sa and not sk: return 1.0
    if not sa or not sk: return 0.0
    return difflib.SequenceMatcher(None, sa, sk, autojunk=False).ratio()


def str_sims(alist, klist, sim):
    """alist/klist: danh sách chuỗi; trả về điểm trung bình theo ghép tuần tự (DP) và danh sách cặp."""
    if not alist or not klist:
        return 0.0, []
    if len(alist) == len(klist):
        pr = [(i, i, sim(alist[i], klist[i])) for i in range(len(alist))]
    else:
        pr, _ = dp_align(len(alist), len(klist), lambda i, j: sim(alist[i], klist[j]), thr=0.2)
    avg = sum(s for _, _, s in pr) / max(len(alist), len(klist)) if pr else 0.0
    return avg, pr


def align_dat_file(arecs, aspans, aitems_by_rec, krecs, kitems_by_rec, sim, band=BAND):
    """arecs/krecs: danh sách bytes. Trả về ([(ra, rk, recsim, skel)], tổng điểm)."""
    askel = [skeleton(arecs[r], aspans.get(r, [])) for r in range(len(arecs))]
    kskel = [skeleton(krecs[r], kspans(krecs[r], kitems_by_rec.get(r, []))) for r in range(len(krecs))]
    cache = {}

    def rs(i, j):
        v = cache.get((i, j))
        if v is not None: return v
        al = [x['text_en'] for x in aitems_by_rec.get(i, [])]
        kl = [x['src'] for x in kitems_by_rec.get(j, [])]
        sk = skel_sim(askel[i], kskel[j])
        if al and kl:
            ss, _ = str_sims(al, kl, sim)
        elif not al and not kl:
            ss = 1.0
        else:
            ss = 0.0
        # bản ghi chỉ có chuỗi (khung rỗng): dựa chuỗi; còn lại khung 50%
        if len(askel[i]) <= 1 and len(kskel[j]) <= 1:
            v = ss
        else:
            v = 0.5 * sk + 0.5 * ss
        cache[(i, j)] = (v, sk)
        return cache[(i, j)]

    prs, tot = dp_align(len(arecs), len(krecs), lambda i, j: rs(i, j)[0], thr=0.3, band=band)
    return [(i, j, s, rs(i, j)[1]) for i, j, s in prs], tot


# ----------------------------------------------------------------- chính
def main():
    acat, kcat, ids = load_all()
    ent, order = EA.load_vfs(VFS)
    names = EA.name_table(LGT)
    h2name = {}
    for h in order:
        h2name[h] = names.get(h)

    # nhóm theo file
    aby = collections.OrderedDict()
    for it in acat: aby.setdefault(it['file'], []).append(it)
    kscn = collections.defaultdict(dict); kdat = collections.defaultdict(list)
    for x in kcat:
        f = kfile_key(x['file'])
        if x['kind'] == 'scn': kscn[f][x['idx']] = x['src']
        elif x['kind'] == 'dat': kdat[f].append(x)

    # bảng số câu
    def a_tab(fname):
        for h, nm in h2name.items():
            if (nm or ('vfs:0x%08x.dat' % h)) == fname:
                return ent[h]
    ktab_n = {}
    for f in kscn:
        p = LGT + '/' + f
        if os.path.exists(p):
            r = EA.parse_scn(open(p, 'rb').read())   # tools/scn.parse chọn sai hit ở vài file (hits[-1])
            if r: ktab_n[f] = len(r[1])
    atab_n = {}
    for f in aby:
        if f.endswith('.scn'):
            r = EA.parse_scn(a_tab(f))
            atab_n[f] = len(r[1]) if r else 0

    allsame = [0, 0]
    for h in order:
        n_ = h2name.get(h)
        if n_ and n_.startswith('c/map/') and n_.endswith('.scn') and os.path.exists(LGT + '/' + n_):
            ra = EA.parse_scn(ent[h]); rk = EA.parse_scn(open(LGT + '/' + n_, 'rb').read())
            if ra and rk: allsame[0 if len(ra[1]) == len(rk[1]) else 1] += 1
    # --- học tên riêng từ các file scn cùng số câu
    same = [f for f in aby if f.endswith('.scn') and f in ktab_n and atab_n[f] == ktab_n[f]]
    pairs0 = []
    for f in same:
        for it in aby[f]:
            k = kscn[f].get(it['idx'])
            if k: pairs0.append((it['text_en'], k))
    nm = learn_names(pairs0, load_nameseeds())
    sim = Sim(nm)

    result = {}   # id(item) -> (src, score, method, note)
    filestat = collections.OrderedDict()

    # --- scn
    for f, items in aby.items():
        if not f.endswith('.scn'): continue
        if f not in kscn:
            for it in items: result[id(it)] = (None, 0.0, 'nofile', 'LGT không có file này')
            filestat[f] = dict(kind='scn', A=len(items), K=0, method='nofile', same=False)
            continue
        res, meth = align_scn_file(f, items, ktab_n.get(f, 0), kscn[f], atab_n[f], sim)
        result.update(res)
        filestat[f] = dict(kind='scn', A=len(items), K=len(kscn[f]), tabA=atab_n[f], tabK=ktab_n.get(f), method=meth,
                           same=(atab_n[f] == ktab_n.get(f)))

    # --- dat
    kdat_files = {}
    def kload(f):
        if f in kdat_files: return kdat_files[f]
        import extract5 as X5
        recs = X5.parse_dat(open(LGT + '/' + f, 'rb').read())
        byr = collections.defaultdict(list)
        for x in kdat[f]: byr[x['rec']].append(x)
        for r in byr: byr[r].sort(key=lambda x: x['off'])
        kdat_files[f] = (recs, byr)
        return kdat_files[f]
    sys.path.insert(0, V5)

    datlog = {}
    for f, items in aby.items():
        if not f.endswith('.dat'): continue
        b = a_tab(f)
        arecs = EA.parse_dat(b)
        aby_rec = collections.defaultdict(list)
        for it in items: aby_rec[it['rec']].append(it)
        for r in aby_rec: aby_rec[r].sort(key=lambda x: x['off'])
        aspans = {r: [(x['off'], x['ps'], len(x['text_en'])) for x in lst] for r, lst in aby_rec.items()}
        if f.startswith('c/') and f in kdat:
            cands = [f]
        else:
            cands = [g for g in kdat if abs(len(kload(g)[0]) - len(arecs)) <= max(6, len(arecs) // 3)]
        best = None
        for g in cands:
            krecs, kbyr = kload(g)
            pr, tot = align_dat_file(arecs, aspans, aby_rec, krecs, kbyr, sim)
            key = (round(tot, 4), -abs(len(krecs) - len(arecs)))   # hoà điểm: chọn file LGT có số bản ghi gần nhất
            if best is None or key > best[3]: best = (tot, g, pr, key)
        if best is None:
            for it in items: result[id(it)] = (None, 0.0, 'nofile', 'không có file LGT tương ứng')
            filestat[f] = dict(kind='dat', A=len(items), K=0, method='nofile', recsA=len(arecs))
            continue
        tot, g, pr, _k = best
        krecs, kbyr = kload(g)
        matched = {}
        for ra, rk, s, sk in pr: matched[ra] = (rk, s, sk)
        for ra, lst in aby_rec.items():
            if ra not in matched:
                for it in lst: result[id(it)] = (None, 0.0, 'rec', 'bản ghi mới (không có bản ghi LGT tương ứng)')
                continue
            rk, s, sk = matched[ra]
            kl = kbyr.get(rk, [])
            if not kl:
                for it in lst: result[id(it)] = (None, 0.0, 'rec', 'bản ghi LGT không có chuỗi Hàn')
                continue
            al = [x['text_en'] for x in lst]; kls = [x['src'] for x in kl]
            if len(al) == len(kls): prs = [(i, i, sim(al[i], kls[i])) for i in range(len(al))]
            else: prs, _ = dp_align(len(al), len(kls), lambda i, j: sim(al[i], kls[j]), thr=0.2)
            got = set()
            for i, j, ss in prs:
                # điểm: 75% chuỗi + 25% khung xương (nếu có khung)
                sc = ss if (len(skeleton(arecs[ra], aspans.get(ra, []))) <= 1) else 0.75 * ss + 0.25 * sk
                result[id(lst[i])] = (kls[j], sc, 'rec', ''); got.add(i)
            for i, it in enumerate(lst):
                if i not in got: result[id(it)] = (None, 0.0, 'rec', 'chuỗi thừa trong bản ghi')
        # thống kê
        diag = sum(1 for ra, rk, s, sk in pr if ra == rk)
        ident = sum(1 for ra, rk, s, sk in pr if sk == 1.0)
        shifts = collections.Counter(rk - ra for ra, rk, s, sk in pr)
        filestat[f] = dict(kind='dat', A=len(items), recsA=len(arecs), recsK=len(krecs), kfile=g, matched=len(pr), diag=diag,
                           ident=ident, shifts=shifts.most_common(4), tot=round(tot, 1))

    # --- xuất align.json
    out = []
    for it in acat:
        src, sc, meth, note = result[id(it)]
        kid = ids.get(src) if src else None
        new = (kid is None) or sc < THR_NEW
        e = {k: it[k] for k in ('file', 'kind') }
        for k in ('idx', 'rec', 'off', 'ps'):
            if k in it: e[k] = it[k]
        e['text_en'] = it['text_en']
        e['id'] = 'NEW' if new else kid
        e['score'] = round(sc, 3)
        e['method'] = meth
        if src: e['ko'] = src
        if kid and new: e['k_low'] = kid
        if note: e['note'] = note
        out.append(e)
    json.dump(out, open(os.path.join(HERE, 'align.json'), 'w', encoding='utf-8'), ensure_ascii=False, indent=0)
    report(out, filestat, nm, same, ktab_n, atab_n, sim, allsame)


def report(out, filestat, nm, same, ktab_n, atab_n, sim, allsame):
    L = []
    P = L.append
    n = len(out)
    scn = [e for e in out if e['kind'] == 'scn']; dat = [e for e in out if e['kind'] == 'dat']
    def stat(es):
        new = sum(1 for e in es if e['id'] == 'NEW'); hi = sum(1 for e in es if e['id'] != 'NEW' and e['score'] >= THR_HIGH)
        return len(es), len(es) - new, new, hi
    P('# Báo cáo ghép chuỗi Android <-> LGT (T2)\n')
    P('Sinh bởi `align_android.py`. Ngưỡng: NEW nếu điểm < %.2f hoặc không có câu Hàn tương ứng; "điểm cao" >= %.2f.\n' % (THR_NEW, THR_HIGH))
    P('## Tổng kết\n')
    P('| nhóm | tổng câu | ghép được (K#####) | NEW | trong đó điểm cao |')
    P('|---|---:|---:|---:|---:|')
    for nm_, es in (('scn', scn), ('dat', dat), ('tất cả', out)):
        t, m, nw, hi = stat(es)
        P('| %s | %d | %d | %d | %d |' % (nm_, t, m, nw, hi))
    # 184 scn cùng số câu
    sf = [f for f, s in filestat.items() if s['kind'] == 'scn' and s.get('same')]
    es = [e for e in scn if e['file'] in set(sf)]
    t, m, nw, hi = stat(es)
    P('\n## scn cùng số câu (ghép theo chỉ số)\n')
    P('- Trong %d file scn có ở cả hai bản: **%d** cùng số câu bảng chuỗi, %d khác số câu (kể cả file không có câu chữ).' % (sum(allsame), allsame[0], allsame[1]))
    P('- Số file scn Android có câu chữ = %d; trong đó cùng số câu bảng chuỗi với LGT = **%d** file (%d câu Android).' % (sum(1 for s in filestat.values() if s['kind']=='scn'), len(sf), t))
    P('- Cặp điểm cao (>= %.2f): **%d / %d = %.1f%%**; ghép được (>= %.2f): %d (%.1f%%); NEW: %d.' % (THR_HIGH, hi, t, 100.0 * hi / max(1, t), THR_NEW, m, 100.0 * m / max(1, t), nw))
    nok = [f for f in sf if filestat[f]['method'] != 'idx']
    P('- File cùng số câu nhưng phải dùng DP (ghép theo chỉ số cho điểm thấp): %d %s' % (len(nok), nok[:10]))
    P('\n## scn khác số câu (DP)\n')
    P('| file | bảng Android | bảng LGT | câu Android có chữ | ghép | NEW |')
    P('|---|---:|---:|---:|---:|---:|')
    for f, s in filestat.items():
        if s['kind'] == 'scn' and not s.get('same'):
            fe = [e for e in scn if e['file'] == f]
            tt, mm, nn, _ = stat(fe)
            P('| %s | %s | %s | %d | %d | %d |' % (f, s.get('tabA'), s.get('tabK'), tt, mm, nn))
    P('\n## .dat: kết luận về trường khoá\n')
    P('Không có trường ID duy nhất đầu bản ghi dùng chung cho mọi file (nhiều file bản ghi bắt đầu bằng số loại/kiểu lặp lại hoặc chỉ chứa chuỗi). '
      'Khoá thực dụng: **bản ghi LGT và Android giữ nguyên thứ tự tương đối; Android chỉ chèn thêm/bớt bản ghi**. '
      'Do đó ghép bản ghi bằng DP tuần tự theo (a) khung xương = bản ghi bỏ các chuỗi (giống nhau từng byte ở phần lớn bản ghi), (b) độ giống chuỗi. '
      'Cột `diag` = số bản ghi ghép vẫn cùng chỉ số; `ident` = số cặp khung xương giống hệt; `shifts` = độ lệch chỉ số phổ biến (K - A).\n')
    P('| file Android | file LGT | bản ghi A | bản ghi K | ghép | diag | khung giống hệt | độ lệch phổ biến | câu A | NEW |')
    P('|---|---|---:|---:|---:|---:|---:|---|---:|---:|')
    for f, s in filestat.items():
        if s['kind'] != 'dat': continue
        fe = [e for e in dat if e['file'] == f]
        tt, mm, nn, _ = stat(fe)
        if s.get('method') == 'nofile':
            P('| %s | (không có) | %d | - | - | - | - | - | %d | %d |' % (f, s['recsA'], tt, nn)); continue
        P('| %s | %s | %d | %d | %d | %d | %d | %s | %d | %d |' % (f, s['kfile'], s['recsA'], s['recsK'], s['matched'], s['diag'], s['ident'],
                                                           ' '.join('%+d:%d' % (k, v) for k, v in s['shifts']), tt, nn))
    P('\n## Tên riêng học được (Hàn -> token Anh), %d mục\n' % len(nm))
    P(', '.join('%s=%s' % (k, '/'.join(sorted(v))) for k, v in sorted(nm.items())[:120]))
    # phân bố điểm
    P('\n## Phân bố điểm (cặp được ghép)\n')
    hist = collections.Counter()
    for e in out:
        if e['id'] != 'NEW' or e.get('k_low'): hist[min(9, int(e['score'] * 10))] += 1
    P('| khoảng | số cặp |'); P('|---|---:|')
    for b in range(10): P('| %.1f-%.1f | %d |' % (b / 10, (b + 1) / 10, hist[b]))
    # 50 cặp điểm thấp nhất (trong các cặp đã ghép, kể cả vừa dưới ngưỡng)
    cand = [e for e in out if e.get('ko')]
    cand.sort(key=lambda e: e['score'])
    P('\n## 50 cặp điểm thấp nhất (có câu Hàn đề xuất) để quản đốc soát\n')
    P('| # | file | vị trí | điểm | kết quả | Anh | Hàn |'); P('|---:|---|---|---:|---|---|---|')
    def loc(e): return ('idx %d' % e['idx']) if 'idx' in e else ('rec %d@%d' % (e['rec'], e['off']))
    def esc(s): return s.replace('|', '\\|').replace('\n', ' ')[:70]
    for i, e in enumerate(cand[:50]):
        P('| %d | %s | %s | %.2f | %s | %s | %s |' % (i + 1, e['file'], loc(e), e['score'], e['id'], esc(e['text_en']), esc(e['ko'])))
    # mẫu NEW
    news = [e for e in out if e['id'] == 'NEW']
    P('\n## Thống kê NEW theo file (20 file nhiều nhất)\n')
    c = collections.Counter(e['file'] for e in news)
    P('| file | NEW |'); P('|---|---:|')
    for f, v in c.most_common(20): P('| %s | %d |' % (f, v))
    P('\n## Ghi chú kỹ thuật\n')
    P('- `tools/scn.py:parse` chọn `hits[-1]` nên sai ở các file có nhiều điểm khớp EOF (vd Android c/map/00154.scn, và vài file LGT như 00026/00034 trả về 0 chuỗi). '
      '`extract_android.parse_scn` ưu tiên điểm bắt đầu s=15 (header chuẩn mọi file) rồi mới chọn hit nhiều chuỗi nhất; T4 phải dùng `extract_android.parse_scn/build_scn`, không dùng `scn.build`.')
    P('- 2 câu Android có chữ `\\u0000` dính cuối (c/map/00041.scn idx 54, c/map/00042.scn idx 24): lỗi của bản Anh hoá, bỏ khi chấm điểm; T4 nên bỏ đoạn này khi ghi.')
    P('- Dấu nháy sở hữu trong .dat tiếng Anh ("Marc\'s") là chữ thường, nhưng `\'` là mã điều khiển của engine; chấm điểm bỏ qua dấu nháy giữa 2 chữ cái. Cần T4/T3 kiểm tra cách engine vẽ.')
    P('- Ví dụ nhiễu còn sót trong android_catalog (chuỗi .dat 2 ký tự trùng bố cục số): `HX` (enemy_expert_*), `oAl`: ghi nhận, sẽ ra NEW và khi dịch có thể bỏ qua.')
    P('- Câu NEW của .dat thuộc bản ghi mới của Android (item_00..03/18 thêm 5-6 bản ghi, common_text +18, v.v.), của file chỉ Android, hoặc bản ghi LGT có chữ giữ chỗ `여분`.')
    P('\nNEW chia theo lý do: ' + str(dict(collections.Counter(e.get('note', '') or ('điểm thấp' if e.get('k_low') else '?') for e in news))))
    open(os.path.join(HERE, 'align_report.md'), 'w', encoding='utf-8').write('\n'.join(L) + '\n')
    print('\n'.join(L[:12]))
    print('scn cùng số câu: %d file, %d câu, điểm cao %d (%.1f%%)' % (len(sf), t, hi, 100.0 * hi / max(1, t)))


if __name__ == '__main__':
    main()
