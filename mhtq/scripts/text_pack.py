# -*- coding: utf-8 -*-
"""Đóng gói bản dịch (TSV) trở lại script.dat/item.dat/mission.dat/skill.dat/mate.dat và .class.

Chạy:  python3 -I mhtq/scripts/text_pack.py --tsv work/text_can_dich.tsv [--src work/k790_raw] --out DIR
       python3 -I mhtq/scripts/text_pack.py --selftest [--log logs/roundtrip.txt]

Kết quả: DIR/<file> cho mọi file có chữ (5 .dat + các .class có chuỗi CJK hiển thị). Cột dịch trống
hoặc bằng gốc => file ra giống hệt từng byte so với nguồn.
Kiểm tra (LỖI = không ghi gì, exit 1; CẢNH BÁO = vẫn ghi):
  LỖI: id không khớp/cột gốc khác nguồn; mã màu #RRGGBB/## lệch so với gốc; `#` lạ hoặc `#` ở cuối chuỗi;
       ký tự ngoài bảng f13c (trừ khi --no-font-check); độ dài vượt giới hạn định dạng;
       tên khoá chân dung (叶问天/小唯/婴咛) dịch KHÔNG đồng bộ giữa script.dat/y/v/h/mate.dat, hoặc ba tên trùng nhau.
  CẢNH BÁO: còn chữ Hán trong bản dịch; chuỗi một dòng rộng hơn giới hạn (đo bằng f13c); có xuống dòng thật.
"""
import argparse
import filecmp
import os
import re
import shutil
import sys
import tempfile

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import textmodel as T        # noqa: E402
import text_extract as X     # noqa: E402

FONT = os.path.join(L.ROOT, 'work', 'tools', 'fonts', 'f13c.bin')


class Font(object):
    def __init__(self, path):
        d = open(path, 'rb').read()
        n = (d[0] << 8) | d[1]
        self.charset = d[2:2 + n].decode('utf-8')
        self.h = d[2 + n]
        cnt = len(self.charset)
        self.w = dict(zip(self.charset, d[3 + n:3 + n + cnt]))
        self.space = self.w.get(' ', 3)

    def width(self, s):
        return sum(self.w.get(c, self.space) for c in s)


def parse_limit(lim):
    m = re.match(r'~?(\d+)px', lim or '')
    return int(m.group(1)) if m else None


def segments(s):
    s = T.CODE_RE.sub('', s)
    return re.split(u'[|\n]', s)


def validate(slots, trans, font, check_font=True):
    errs, warns = [], []
    eff = {}
    for s in slots:
        t = trans.get(s.id)
        if not t or t == s.orig:
            eff[s.id] = s.orig
            continue
        eff[s.id] = t
        # mã màu
        co, ct = sorted(T.CODE_RE.findall(s.orig)), sorted(T.CODE_RE.findall(t))
        if co != ct:
            errs.append('%s: mã màu lệch (gốc %s, dịch %s)' % (s.id, co, ct))
        rest = T.CODE_RE.sub('', t)
        if '#' in rest:
            errs.append('%s: có `#` lạ/cuối chuỗi (k.b sẽ lỗi): %r' % (s.id, t[:40]))
        if len(t) > 32767 or len(L.mutf8_encode(t)) > 65535:
            errs.append('%s: quá dài' % s.id)
        if any(ord(c) > 0xFFFF for c in t):
            errs.append('%s: ký tự ngoài BMP' % s.id)
        if '\x00' in t:
            errs.append('%s: có NUL' % s.id)
        if L.HAN_RE.search(t):
            warns.append('%s: còn chữ Hán trong bản dịch' % s.id)
        if '\n' in t or '\r' in t or '\t' in t:
            warns.append('%s: có xuống dòng/tab thật (\\n hiểu như `|`)' % s.id)
        if font and check_font:
            miss = sorted(set(c for c in T.CODE_RE.sub('', t) if c not in font.w and c not in u'|\n\r\t'))
            if miss:
                errs.append('%s: ký tự ngoài bảng f13c: %s' % (s.id, ''.join(miss)))
        lim = parse_limit(s.limit)
        if font and lim and s.single:
            mx = max(font.width(x) for x in segments(t))
            if mx > lim:
                warns.append('%s: rộng %dpx > %dpx (%s)' % (s.id, mx, lim, t[:30]))
    # đồng bộ khoá chân dung
    byg = {}
    for s in slots:
        if s.group:
            byg.setdefault(s.group, []).append(s)
    gval = {}
    for g, lst in byg.items():
        vals = {}
        for s in lst:
            vals.setdefault(eff[s.id], []).append(s.id)
        if len(vals) > 1:
            errs.append('KHOÁ %s dịch không đồng bộ: %s' % (g, '; '.join('%r <- %s' % (v, ','.join(i)) for v, i in vals.items())))
        else:
            gval[g] = list(vals)[0]
    if len(set(gval.values())) < len(gval):
        errs.append('Tên khoá chân dung dịch ra trùng nhau: %r' % gval)
    return errs, warns


def pack(src, trans, out, font=None, check_font=True, quiet=False):
    docs, slots = T.collect(src)
    errs, warns = validate(slots, trans, font, check_font)
    if errs:
        return errs, warns, 0
    n = 0
    for s in slots:
        t = trans.get(s.id)
        if t and t != s.orig:
            s.set(t)
            n += 1
    os.makedirs(out, exist_ok=True)
    for rel, d in docs:
        with open(os.path.join(out, rel), 'wb') as f:
            f.write(d.serialize())
    return errs, warns, n


def load_trans(tsv, src):
    docs, slots = T.collect(src)
    ids = dict((s.id, s) for s in slots)
    trans = {}
    errs = []
    seen = set()
    for r in X.read_tsv(tsv):
        if r[0] not in ids:
            errs.append('id lạ trong TSV: %s' % r[0]); continue
        s = ids[r[0]]
        seen.add(r[0])
        if L.unesc(r[3]) != s.orig:
            errs.append('%s: cột gốc khác nguồn (nguồn đã đổi?)' % r[0])
        try:
            t = L.unesc(r[4])
        except ValueError as e:
            errs.append('%s: %s' % (r[0], e)); continue
        if t:
            trans[r[0]] = t
    for i in ids:
        if i not in seen:
            errs.append('thiếu id trong TSV: %s' % i)
    return trans, errs


def selftest(src, log):
    lines = []

    def P(x):
        lines.append(x)
        print(x)
    docs, slots = T.collect(src)
    P('# Roundtrip text_extract/text_pack (nguồn: %s)' % os.path.relpath(src, L.ROOT))
    P('slot: %d; file có chữ: %s' % (len(slots), ', '.join(r for r, _ in docs)))
    tmp = tempfile.mkdtemp(prefix='mhtq_rt_')
    ok_all = True
    try:
        tsv = os.path.join(tmp, 'rt.tsv')
        import io
        import contextlib
        with contextlib.redirect_stdout(io.StringIO()):
            X.main(['--src', src, '--out', tsv])
        font = Font(FONT) if os.path.exists(FONT) else None

        def cmp_dir(out, title):
            nonlocal ok_all
            good = 0
            for rel, _ in docs:
                same = filecmp.cmp(os.path.join(src, rel), os.path.join(out, rel), shallow=False)
                good += same
                if not same:
                    ok_all = False
                    P('  KHÁC: %s' % rel)
            P('%s: %d/%d file giống từng byte (cmp)' % (title, good, len(docs)))

        # 1. dịch trống
        trans, errs = load_trans(tsv, src)
        assert not errs, errs
        o1 = os.path.join(tmp, 'o1')
        e, w, n = pack(src, trans, o1, font)
        P('[1] cột dịch trống: lỗi=%d cảnh báo=%d số dòng thay=%d' % (len(e), len(w), n))
        cmp_dir(o1, '[1]')
        # 2. dịch = gốc (điền đủ)
        tsv2 = os.path.join(tmp, 'rt2.tsv')
        with open(tsv, encoding='utf-8') as f, open(tsv2, 'w', encoding='utf-8') as g:
            for k, ln in enumerate(f):
                c = ln.rstrip('\n').split('\t')
                if k > 0:
                    c[4] = c[3]
                g.write('\t'.join(c) + '\n')
        trans, errs = load_trans(tsv2, src)
        assert not errs, errs
        o2 = os.path.join(tmp, 'o2')
        e, w, n = pack(src, trans, o2, font)
        P('[2] cột dịch = gốc: lỗi=%d cảnh báo=%d số dòng thay=%d' % (len(e), len(w), n))
        cmp_dir(o2, '[2]')
        # 3. dịch thật (tổng hợp): thêm tiền tố có dấu tiếng Việt, kiểm tra đọc lại
        syn = dict((s.id, u'Việt ' + s.orig) for s in slots)
        o3 = os.path.join(tmp, 'o3')
        e, w, n = pack(src, syn, o3, None, check_font=False)
        P('[3] dịch tổng hợp (tiền tố "Việt "): lỗi=%d số dòng thay=%d' % (len(e), n))
        docs3, slots3 = T.collect(o3)
        got = dict((s.id, s.orig) for s in slots3)
        bad = [i for i in syn if got.get(i) != syn[i]]
        P('[3] đọc lại từ file đã đóng gói: %d/%d chuỗi khớp' % (len(syn) - len(bad), len(syn)))
        if bad:
            ok_all = False
            P('  sai: %s' % bad[:10])
        # file đóng gói còn nhận lại byte cấu trúc: serialize(parse(x)) == x
        okc = 0
        for rel, _ in docs:
            d3 = open(os.path.join(o3, rel), 'rb').read()
            if rel.endswith('.class'):
                c = L.ClassFile(d3)
                okc += (c.patch({}) == d3)
            else:
                okc += 1
        P('[3] class mới parse lại + cấu trúc nguyên vẹn: %d/%d' % (okc, len(docs)))
        # 4. kiểm thử bộ kiểm tra khoá chân dung
        k = [s for s in slots if s.group == T.KEYS[0]]
        bad_t = {k[0].id: u'Diệp Vấn Thiên'}
        e, w, n = pack(src, bad_t, os.path.join(tmp, 'o4'), font)
        P('[4] dịch lệch khoá (chỉ 1/%d nơi của 叶问天): %s' % (len(k), 'BÁO LỖI đúng' if any('KHOÁ' in x for x in e) else 'KHÔNG báo lỗi (SAI)'))
        if not any('KHOÁ' in x for x in e):
            ok_all = False
        good_t = dict((s.id, u'Diệp Vấn Thiên') for s in k)
        for g, nm in ((T.KEYS[1], u'Tiểu Duy'), (T.KEYS[2], u'Anh Ninh')):
            for s in slots:
                if s.group == g:
                    good_t[s.id] = nm
        e, w, n = pack(src, good_t, os.path.join(tmp, 'o5'), font)
        P('[5] dịch đồng bộ 3 khoá (script/y/v/h/mate): lỗi=%d, thay %d dòng' % (len(e), n))
        if e:
            ok_all = False
            P('  ' + '; '.join(e[:3]))
    finally:
        shutil.rmtree(tmp, ignore_errors=True)
    P('KẾT QUẢ: %s' % ('PASS' if ok_all else 'FAIL'))
    if log:
        os.makedirs(os.path.dirname(os.path.abspath(log)), exist_ok=True)
        with open(log, 'w', encoding='utf-8') as f:
            f.write('\n'.join(lines) + '\n')
    return ok_all


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--src', default=os.path.join(L.ROOT, 'work', 'k790_raw'))
    ap.add_argument('--tsv', default=os.path.join(L.ROOT, 'work', 'text_can_dich.tsv'))
    ap.add_argument('--out')
    ap.add_argument('--selftest', action='store_true')
    ap.add_argument('--log')
    ap.add_argument('--no-font-check', action='store_true')
    a = ap.parse_args(argv)
    if a.selftest:
        return 0 if selftest(a.src, a.log) else 1
    if not a.out:
        ap.error('cần --out')
    trans, errs = load_trans(a.tsv, a.src)
    font = Font(FONT) if os.path.exists(FONT) else None
    if not errs:
        e, w, n = pack(a.src, trans, a.out, font, not a.no_font_check)
        errs += e
    else:
        w = []
    for x in w:
        print('CẢNH BÁO', x)
    for x in errs:
        print('LỖI', x)
    if errs:
        print('%d lỗi: không ghi file' % len(errs))
        return 1
    print('đã ghi %s (thay %d dòng, %d cảnh báo)' % (a.out, n, len(w)))
    return 0


if __name__ == '__main__':
    sys.exit(main())
