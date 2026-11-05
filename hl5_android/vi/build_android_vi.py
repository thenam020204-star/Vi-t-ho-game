#!/usr/bin/env python3
"""T4: build dữ liệu Việt cho Android.

  python3 -I build_android_vi.py [--out DIR] [--vfs IN.vfs] [--no-vfs]

Nguồn dịch: K##### -> HL5_dich_FULL.tsv + override.tsv (+ menu_override.tsv nếu có, khoá (tên file, tiếng Hàn));
            NEW    -> vi/A_dich.tsv (khoá (file, vị trí, text_en)).
Giữ file Android, chỉ thay chuỗi (extract_android.apply_file). Mã hoá/fit dùng Encoder + luật fit của build_vi5.py.
Kết quả ($WORK/vi_android): res/c/... (file đổi), vfs_unnamed/0xHASH.dat (entry VFS không tên),
dict.txt, font.bin, font10.bin, f18.bin, bao-cao-build.txt, data.vfs.mp3 (VFS thử, tools/vfs_pack.py).
Câu K lệch số so với text_en Android -> thêm dòng mới vào A_dich.tsv (ghi chú LECHSO) để dịch tay (T3c).
"""
import sys, os, re, json, struct, collections, subprocess, shutil, argparse

HERE = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.dirname(HERE)
sys.path.insert(0, HERE)
sys.path.insert(0, os.path.join(REPO, 'tools'))
WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))
V5 = os.path.join(WORK, 'proj/HL4_project/vi5')
sys.path.insert(0, V5)
import extract_android as EA                                    # noqa: E402
import build_vi5 as B                                           # noqa: E402
from export5 import ko_width                                    # noqa: E402
import vfs_pack                                                 # noqa: E402

DICT_BUDGET = B.MAXCODES   # 5076


def read_tsv(path, nc):
    rows = []
    if os.path.exists(path):
        for l in open(path, encoding='utf-8-sig').read().split('\n')[1:]:
            c = l.split('\t')
            if len(c) >= nc: rows.append(c)
    return rows


def load_K():
    tr = {}
    for c in read_tsv(os.path.join(V5, 'tr_in/HL5_dich_FULL.tsv'), 5):
        if c[4].strip(): tr[c[0]] = c[4].replace('\\n', '\n')
    for c in read_tsv(os.path.join(V5, 'override.tsv'), 3):
        if c[2].strip(): tr[c[0]] = c[2].replace('\\n', '\n')
    # T19: dịch lại (xưng hô, câu cứng) — trong repo, đè lên mọi nguồn trên. Cột: ID, Tieng Han, Tieng Viet cu, Tieng Viet moi, Ghi chu
    for c in read_tsv(os.path.join(HERE, 'dich_lai.tsv'), 4):
        if c[3].strip(): tr[c[0]] = c[3].replace('\\n', '\n')
    return tr


def load_A():
    """-> (rows list, by key (file,pos,text_en_escaped))"""
    path = os.path.join(HERE, 'A_dich.tsv')
    lines = open(path, encoding='utf-8').read().split('\n')
    if lines and lines[-1] == '': lines.pop()
    rows = [l.split('\t') for l in lines[1:]]
    for r in rows: r += [''] * (6 - len(r))
    return lines[0], rows


def pos_of(e):
    return ('idx %d' % e['idx']) if 'idx' in e else 'rec %d off %d ps %d' % (e['rec'], e['off'], e['ps'])


def codes_bad(en, vi):
    """mã điều khiển phải giống bản Android, TRỪ ';' (Android đã ngắt dòng lại bản Anh; ';' xử lý riêng bằng reflow)"""
    bad = []
    en = re.sub(r"(?<=[A-Za-z])'(?=[A-Za-z])", '', en)     # dấu nháy trong từ (Marc's): chữ, không phải mã màu
    for c in B.CTRL.replace(';', ''):
        if en.count(c) != vi.count(c): bad.append('%s %d->%d' % (c, en.count(c), vi.count(c)))
    for m in set(re.findall(r'%[0-9]*[dsxfc]|#[0-9]+', en)):
        if en.count(m) != vi.count(m): bad.append('%s %d->%d' % (m, en.count(m), vi.count(m)))
    return bad


def numbers_ok(en, vi):
    """số trong bản Anh phải có trong bản Việt. Bỏ qua: chữ rác '\\u0000' (T2b) và '1H/2H/2-Hand' khi bản Việt viết 'một/hai tay'."""
    en = en.replace('\\u0000', '')
    if re.search(r'(một|hai|1|2) ?tay|\d ?H\b', vi, re.I):
        en = re.sub(r'\b\d+-?(?:H|Hand)\b', '', en)
    return [n for n in re.findall(r'\d+', en) if n not in vi]


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--out', default=os.path.join(WORK, 'vi_android'))
    ap.add_argument('--vfs', default=os.path.join(WORK, 'apk/assets/data.vfs.mp3'))
    ap.add_argument('--no-vfs', action='store_true')
    ap.add_argument('--no-write-A', action='store_true', help='không thêm dòng LECHSO vào A_dich.tsv')
    a = ap.parse_args()
    out = a.out

    cat = json.load(open(os.path.join(HERE, 'android_catalog.json'), encoding='utf-8'))
    al = json.load(open(os.path.join(HERE, 'align.json'), encoding='utf-8'))
    assert len(cat) == len(al)
    ktr = load_K()
    head, arows = load_A()
    akey = {(r[1], r[2], r[3]): r for r in arows}
    amax = max(int(r[0][1:]) for r in arows)
    fit = B.load_fit()
    fovr = {}
    for c in read_tsv(os.path.join(V5, 'menu_override.tsv'), 3):
        if c[2].strip(): fovr[(c[0], c[1])] = c[2]

    fontp = os.path.join(V5, 'font/font_15px.bin')
    enc = B.Encoder(B.load_font_chars(fontp))
    old_dict = [l.rstrip('\n') for l in open(os.path.join(V5, 'build/overlay/vi5/dict.txt'), encoding='utf-8')]
    if old_dict and old_dict[-1] == '': old_dict.pop()
    for w in old_dict:                      # giữ nguyên thứ tự/mã của bản LGT, từ mới nối đuôi
        enc.index[w] = len(enc.dict); enc.dict.append(w)
    adv = B.font_adv(fontp); adv[' '] = 6
    adv10 = B.font_adv(os.path.join(V5, 'font/font_10px_v2.bin'))

    # nhóm trường dat (G:file.fN) theo bản ghi Android
    recf = collections.defaultdict(list)
    for e in cat:
        if e['kind'] == 'dat': recf[(e['file'], e['rec'])].append(e)
    gkey = {}
    for es in recf.values():
        for i, e in enumerate(sorted(es, key=lambda e: e['off'])):
            gkey[(e['file'], e['rec'], e['off'])] = 'G:%s.f%d' % (re.sub(r'\d+', '#', os.path.basename(e['file'])[:-4]), i)

    rep = collections.defaultdict(list)
    newtext = collections.defaultdict(dict)       # file -> {index trong list file: bytes}
    byfile = collections.OrderedDict()
    for e in cat: byfile.setdefault(e['file'], []).append(e)
    fidx = {}                                      # id(entry) -> chỉ số trong list của file
    for f, es in byfile.items():
        for k, e in enumerate(es): fidx[id(e)] = k
    stat = collections.Counter()
    added_A = []
    cache = {}

    def encode_K(kid, ko, en, vi, e):
        """luật fit của build_vi5.main cho 1 câu K; trả (bytes, vi sau wrap)"""
        tight = False
        fi = fit.get(kid)
        g = fit.get(gkey.get((e['file'], e.get('rec'), e.get('off'))))
        if g and (fi is None or g[0] < fi[0]): fi = g
        if fi:
            mx, mode = fi
            if mode == 'wrap' and B.vi_width(vi, adv) > mx and B.vi_width_small(vi, adv10) > mx:
                vi = B.wrap_lines(vi, mx, adv)
            if mode == 'rewrap':
                vi = B.wrap_lines(vi.replace(';', ' '), mx, adv)
            if mode == 'small':
                tight = 2
                if B.vi_width_small(vi, adv10) > mx:
                    rep['CẦN RÚT GỌN'].append('%s 10px %d > ô %d px: %s' % (kid, B.vi_width_small(vi, adv10), mx, vi))
            elif B.vi_width(vi, adv) > mx:
                tight = 1
                if B.vi_width_tight(vi, adv) > mx:
                    tight = 2
                    if B.vi_width_small(vi, adv10) > mx and mode != 'soft':
                        rep['CẦN RÚT GỌN'].append('%s rộng %d (nén %d, 10px %d) > ô %d px: %s' % (
                            kid, B.vi_width(vi, adv), B.vi_width_tight(vi, adv), B.vi_width_small(vi, adv10), mx, vi))
        elif ';' in en:
            # Android đã ngắt dòng bản Anh bằng ';': bản K (từ tiếng Hàn) thường không có ';' hoặc ít hơn -> reflow theo
            # bề rộng dòng Anh rộng nhất (cùng font), giữ nguyên nếu số dòng đã bằng
            if vi.count(';') != en.count(';'):
                vi = B.wrap_lines(vi.replace(';', ' '), max(B.vi_width(en, adv), 60) + 4, adv)
        elif ';' in ko:
            vi = B.wrap_lines(vi, max(ko_width(ko), 60) + 4, adv)
        return enc.encode(vi, tight), vi

    for i, (e, x) in enumerate(zip(cat, al)):
        f = e['file']; en = e['text_en']; pos = pos_of(e)
        enk = en.replace('\n', '\\n')
        kid = x['id']
        vi = None; src = None
        arow = akey.get((f, pos, enk))
        if kid != 'NEW':
            kvi = ktr.get(kid)
            fo = fovr.get((os.path.basename(f), x.get('ko', '')))
            if fo is not None: kvi = fo
            if kvi is None:
                stat['K không có bản dịch (giữ Anh)'] += 1
                rep['K CHƯA DỊCH'].append('%s %s %s' % (kid, f, pos)); continue
            # câu K lệch số / lệch mã so với bản Android
            miss = numbers_ok(en, kvi)
            cbad = codes_bad(en, kvi)
            lech = bool(miss)
            if arow is not None and re.search(r'LECH(SO|MA)', arow[5]) and arow[4].strip():
                vi = arow[4]; src = arow[0]; stat['LỆCH SỐ đã dịch lại (A)'] += 1
            elif lech or cbad:
                tag = 'LECHSO' if lech else 'LECHMA'
                if arow is None and not a.no_write_A:
                    amax += 1
                    arow = ['A%05d' % amax, f, pos, enk, '', '%s; k=%s; hint=%s' % (tag, kid, kvi.replace('\t', ' '))]
                    arows.append(arow); akey[(f, pos, enk)] = arow; added_A.append(arow)
                if lech: rep['LỆCH SỐ'].append('%s %s %s thiếu %s | EN: %s | VI: %s%s' % (
                    kid, f, pos, ','.join(miss), en, kvi, ' | A=' + arow[0] if arow else ''))
                else: rep['LỆCH MÃ'].append('%s %s %s %s | EN: %s | VI: %s%s' % (
                    kid, f, pos, ','.join(cbad), en, kvi, ' | A=' + arow[0] if arow else ''))
                stat['LỆCH SỐ' if lech else 'LỆCH MÃ'] += 1
                if cbad and not lech:
                    stat['LỆCH MÃ giữ Anh'] += 1
                    continue
                vi = kvi; src = kid           # tạm dùng bản K (còn lệch số) cho tới khi dịch A xong
            else:
                vi = kvi; src = kid
            if vi is not None and src == kid:
                b, vi2 = encode_K(kid, x.get('ko', ''), en, vi, e)
            else:
                b = enc.encode(vi); vi2 = vi
        else:
            if arow is None or not arow[4].strip():
                stat['NEW chưa dịch (giữ Anh)'] += 1
                rep['NEW CHƯA DỊCH'].append('%s %s %s' % (arow[0] if arow else '?', f, pos)); continue
            vi = arow[4]; src = arow[0]
            for m in codes_bad(en, vi): rep['LỆCH MÃ'].append('%s %s' % (src, m))
            for n in numbers_ok(en, vi): rep['LỆCH SỐ'].append('%s thiếu số %s' % (src, n))
            b = enc.encode(vi); vi2 = vi
        if enc.missing:
            rep['THIẾU GLYPH'].append('%s: %s' % (src, dict(enc.missing))); enc.missing.clear()
        if len(b) > e['cap']:
            rep['QUÁ DÀI'].append('%s %s %s: mã hoá %d byte > cap %d (trường %s) -> giữ câu Android' % (
                src, f, pos, len(b), e['cap'], 'u8' if e.get('ps', 1) == 1 and e['cap'] < 1000 else 'u16'))
            stat['QUÁ DÀI'] += 1
            continue
        newtext[f][fidx[id(e)]] = b
        stat['đã thay'] += 1

    if added_A:
        open(os.path.join(HERE, 'A_dich.tsv'), 'w', encoding='utf-8').write(
            '\n'.join([head] + ['\t'.join(r) for r in arows]) + '\n')

    # --- ghi file ---
    ent, order = EA.load_vfs(a.vfs)
    names = EA.name_table(os.path.join(WORK, 'lgt5/j/res'))
    h_of = {}
    for h in order:
        h_of[names.get(h) or ('vfs:0x%08x.dat' % h)] = h
    if os.path.exists(out): shutil.rmtree(out)
    os.makedirs(out)
    nfile = 0; rt_bad = []; skel_bad = []; outfiles = {}
    for f, tx in newtext.items():
        es = byfile[f]; kind = es[0]['kind']
        orig = ent[h_of[f]]
        nb = EA.apply_file(kind, orig, es, tx)
        rel = 'res/' + f if f.startswith('c/') else 'vfs_unnamed/' + f[4:]
        dst = os.path.join(out, rel); os.makedirs(os.path.dirname(dst), exist_ok=True)
        open(dst, 'wb').write(nb); nfile += 1
        outfiles[f] = (kind, orig, nb)
        # roundtrip + khung
        want = {es[k]['idx'] if kind == 'scn' else (es[k]['rec'], es[k]['off']): v for k, v in tx.items()}
        try:
            if kind == 'scn':
                po = EA.parse_scn(orig); pn = EA.parse_scn(nb)
                if pn is None or pn[0] != po[0] or nb[:po[0]] != orig[:po[0]] or len(pn[1]) != len(po[1]):
                    skel_bad.append((f, 'header/số chuỗi')); continue
                if EA.build_scn(nb, list(pn[1])) != nb: rt_bad.append((f, 'build_scn(parse)'))
                for i2, s in enumerate(pn[1]):
                    if i2 in want:
                        if s != want[i2] + (b'\0' if es[[k for k in tx if es[k]['idx'] == i2][0]].get('nul', 1) else b''):
                            rt_bad.append((f, 'idx %d đọc lại khác' % i2))
                    elif s != po[1][i2]:
                        rt_bad.append((f, 'idx %d bị đổi' % i2))
            else:
                ro = EA.parse_dat(orig); rn = EA.parse_dat(nb)
                if rn is None or len(rn) != len(ro): skel_bad.append((f, 'số bản ghi')); continue
                if EA.build_dat(rn) != nb: rt_bad.append((f, 'build_dat(parse)'))
                lay = EA.DAT_LAYOUT[f]
                for ri, (r0, r1) in enumerate(zip(ro, rn)):
                    w0 = EA.walk_rec(r0, lay); w1 = EA.walk_rec(r1, lay)
                    if w1 is None or len(w0) != len(w1): skel_bad.append((f, 'rec %d khung' % ri)); continue
                    # byte ngoài trường chuỗi giống hệt
                    def skel(r, w):
                        o = bytearray(); p = 0
                        for (off, ps, n) in w: o += r[p:off]; p = off + ps + n
                        return bytes(o) + r[p:]
                    if skel(r0, w0) != skel(r1, w1): skel_bad.append((f, 'rec %d byte ngoài chuỗi' % ri))
                    for (o0, ps0, n0), (o1, ps1, n1) in zip(w0, w1):
                        got = r1[o1 + ps1:o1 + ps1 + n1]
                        if (ri, o0) in want:
                            if got != want[(ri, o0)]: rt_bad.append((f, 'rec %d off %d đọc lại khác' % (ri, o0)))
                        elif got != r0[o0 + ps0:o0 + ps0 + n0]: rt_bad.append((f, 'rec %d off %d bị đổi' % (ri, o0)))
        except Exception as ex:
            rt_bad.append((f, 'exception %r' % ex))

    # --- từ điển + font ---
    with open(os.path.join(out, 'dict.txt'), 'w', encoding='utf-8') as fh:
        fh.write('\n'.join(enc.dict) + '\n')
    shutil.copy(fontp, os.path.join(out, 'font.bin'))
    shutil.copy(os.path.join(V5, 'font/font_10px_v2.bin'), os.path.join(out, 'font10.bin'))
    f18 = os.path.join(WORK, 'namp/NAM_P/fonts/f18.bin')
    if os.path.exists(f18): shutil.copy(f18, os.path.join(out, 'f18.bin'))
    open(os.path.join(out, 'offline.bin'), 'wb').write(enc.encode(B.OFFLINE_MSG))

    # --- VFS thử ---
    vfs_msg = 'bỏ qua (--no-vfs)'
    if not a.no_vfs:
        tmp = os.path.join(out, '_tmp.vfs')
        r = subprocess.run([sys.executable, '-I', os.path.join(REPO, 'tools/vfs_pack.py'), a.vfs, tmp, '--replace',
                            os.path.join(out, 'res')], capture_output=True, text=True)
        # --replace chỉ khớp res/c/...; entry không tên (vfs_unnamed/0xHASH.dat) thay trực tiếp theo hash
        vfs_msg = (r.stdout + r.stderr).strip().replace('\n', ' | ')[:200]
        if r.returncode != 0:
            # file res/vfs/ có thể làm lệnh lỗi: thử lại với thư mục chỉ có res/c
            vfs_msg = 'vfs_pack lỗi: ' + vfs_msg
        else:
            ents = vfs_pack.read_vfs(open(tmp, 'rb').read())
            vdir = os.path.join(out, 'vfs_unnamed')
            n_un = 0
            if os.path.isdir(vdir):
                for fn in os.listdir(vdir):
                    h = int(fn[2:-4], 16)
                    for en_ in ents:
                        if en_[0] == h: en_[1] = open(os.path.join(vdir, fn), 'rb').read(); n_un += 1
            open(os.path.join(out, 'data.vfs.mp3'), 'wb').write(vfs_pack.write_vfs(ents))
            os.remove(tmp)
            vfs_msg = 'data.vfs.mp3 OK (%d mục, +%d entry không tên theo hash)' % (len(ents), n_un)

    # --- báo cáo ---
    nd = len(enc.dict)
    L = []
    L.append('BÁO CÁO BUILD ANDROID VI')
    L.append('câu catalog %d | đã thay %d | file ghi %d | từ điển %d / %d (LGT cũ %d)' % (
        len(cat), stat['đã thay'], nfile, nd, DICT_BUDGET, len(old_dict)))
    L.append('thống kê: ' + '; '.join('%s=%d' % kv for kv in sorted(stat.items())))
    L.append('roundtrip lỗi: %d | khung/số bản ghi lệch: %d | dòng A mới thêm (LECHSO/LECHMA): %d | VFS: %s' % (
        len(rt_bad), len(skel_bad), len(added_A), vfs_msg))
    L.append('từ điển ' + ('OK' if nd <= DICT_BUDGET else 'VƯỢT NGÂN SÁCH'))
    for k in ('QUÁ DÀI', 'LỆCH SỐ', 'LỆCH MÃ', 'THIẾU GLYPH', 'CẦN RÚT GỌN', 'NEW CHƯA DỊCH', 'K CHƯA DỊCH'):
        L.append('== %s: %d ==' % (k, len(rep[k])))
        L.extend('  ' + s for s in rep[k])
    if rt_bad: L.append('== ROUNDTRIP LỖI =='); L.extend('  %s %s' % t for t in rt_bad)
    if skel_bad: L.append('== KHUNG LỆCH =='); L.extend('  %s %s' % t for t in skel_bad)
    open(os.path.join(out, 'bao-cao-build.txt'), 'w', encoding='utf-8').write('\n'.join(L) + '\n')
    print('\n'.join(L[:5]))
    print('QUÁ DÀI %d | LỆCH SỐ %d | LỆCH MÃ %d | THIẾU GLYPH %d | CẦN RÚT GỌN %d | NEW chưa dịch %d | K chưa dịch %d' % tuple(
        len(rep[k]) for k in ('QUÁ DÀI', 'LỆCH SỐ', 'LỆCH MÃ', 'THIẾU GLYPH', 'CẦN RÚT GỌN', 'NEW CHƯA DỊCH', 'K CHƯA DỊCH')))
    return 1 if (rt_bad or skel_bad or nd > DICT_BUDGET) else 0


if __name__ == '__main__':
    sys.exit(main())
