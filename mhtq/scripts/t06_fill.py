# -*- coding: utf-8 -*-
"""T06 bước 1: điền bản dịch (work/dp_work/final/dp_in.tsv, theo id) vào cột `dịch` của work/text_can_dich.tsv
-> work/text_da_dich.tsv (file gốc giữ nguyên). Chuẩn hoá dấu câu toàn khổ và chỉnh dấu cách cho chuỗi ghép.

  python3 -I mhtq/scripts/t06_fill.py [--tsv work/text_can_dich.tsv] [--dp work/dp_work/final/dp_in.tsv]
        [--out work/text_da_dich.tsv] [--log logs/t06_fill.txt]

Chuỗi ghép (đọc bytecode `StringBuffer.append` ở v/w/m/p/n/b): gốc tiếng Hán không có dấu cách giữa các mảnh nên
bản Việt phải thêm dấu cách ở mảnh tương ứng. Bảng PAD: id -> (đầu, cuối) là chuỗi cần đặt ở hai đầu mảnh.
"""
import argparse
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import text_extract as X     # noqa: E402

FW = {u'？': u'?', u'！': u'!', u'，': u',', u'：': u':', u'；': u';', u'（': u'(', u'）': u')', u'　': u' ',
      u'。': u'.', u'～': u'~', u'“': u'"', u'”': u'"', u'‘': u"'", u'’': u"'", u'、': u','}

# id -> (cần đầu, cần cuối, chỗ ghép)
PAD = {
    'C.b.5': ('', ' ', 'b.<init>: label + mô tả nhiệm vụ'),
    'C.n.7': ('', ' ', 'n.<init>: label + phím tắt'),
    'C.n.8': ('', ' ', 'n.<init>: label + sư thừa'),
    'C.n.9': ('', ' ', 'n.<init>: label + cấp kỹ năng'),
    'C.p.60': ('', ' ', 'p.a@165: "   Cấp " + số'),
    'C.p.118': ('', ' ', 'p.a@93: label + số'),
    'C.p.127': ('', ' ', 'p.a@128: label + số'),
    'C.v.7': ('', ' ', 'v.a: "|Lên cấp: " + số + "!"'),
    'C.v.8': (' ', '', 'v.a@424: tên đồng đội + mảnh này'),
    'C.v.9': (' ', '', 'v.a@433: tên đồng đội + mảnh này'),
    'C.v.13': ('', ' ', 'v.a@57: mảnh + tên đồng đội'),
    'C.v.14': (' ', '', 'v.a@77: tên + mảnh'),
    'C.v.15': (' ', '', 'v.a@85: tên + mảnh'),
    'C.v.21': ('', ' ', 'v.a@316: "Nhận " + tên đồ'),
    'C.v.22': ('', ' ', 'v.b@1: "Nhận EXP: " + số'),
    'C.v.23': ('', ' ', 'v.a@1: "Nhận vàng: " + số'),
    'C.w.18': ('', ' ', 'w.n@1625: "Nhận " + tên đồ'),
    'C.m.43': (' ', '', 'm.a@205: tên đồ + "đã hủy"'),
    'C.m.44': (' ', '', 'm.a@452: tên đồ + "đã thêm"'),
    'C.m.46': ('', ' ', 'm.b@1286: "Hồi phục " + số + " điểm"'),
    'C.m.66': (' ', '', 'm.b@1296: số + " điểm"'),
    'C.m.52': ('', ' ', 'm.b: "ATK " + chuỗi +n'),
    'C.m.84': ('', ' ', 'm.b: "DEF " + chuỗi +n'),
    'C.m.83': ('', ' ', 'm.b: "Né " + chuỗi +n'),
    'C.m.59': ('', ' ', 'm.b: "Bạo kích " + chuỗi +n'),
    'C.m.62': (' ', ' ', 'm.a@633: tên kỹ năng + " tiêu hao " + số'),
    'C.m.63': ('', ' ', 'm.a: "Tiêu vàng " + số'),
}

# Ghi đè sau khi đo ảnh chụp (quản đốc cho phép): 107px > hộp 101px, chữ đè viền danh sách kỹ năng
OVERRIDE = {'K.n07': 'Huyết Chiến B.Phương'}


def norm(t):
    return u''.join(FW.get(c, c) for c in t)


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--tsv', default=os.path.join(L.ROOT, 'work', 'text_can_dich.tsv'))
    ap.add_argument('--dp', default=os.path.join(L.ROOT, 'work', 'dp_work', 'final', 'dp_in.tsv'))
    ap.add_argument('--out', default=os.path.join(L.ROOT, 'work', 'text_da_dich.tsv'))
    ap.add_argument('--log', default=os.path.join(L.ROOT, 'logs', 't06_fill.txt'))
    a = ap.parse_args(argv)
    dp = {}
    with open(a.dp, encoding='utf-8') as f:
        for k, ln in enumerate(f):
            if k == 0:
                continue
            c = ln.rstrip('\n').split('\t')
            dp[c[0]] = (c[1], c[4])
    rows = X.read_tsv(a.tsv)
    log = []
    n = 0
    for r in rows:
        if r[0] not in dp:
            raise SystemExit('thiếu id trong dp_in: ' + r[0])
        g, d = dp[r[0]]
        if g != r[3]:
            raise SystemExit('cột gốc khác nhau ở ' + r[0])
        t = L.unesc(d)
        t2 = norm(t)
        if t2 != t:
            log.append('chuẩn hoá toàn khổ %s: %r -> %r' % (r[0], t[:40], t2[:40]))
        t = t2
        if r[0] in PAD:
            h, tl, why = PAD[r[0]]
            t0 = t
            if h and not t.startswith(h):
                t = h + t
            if tl and not t.endswith(tl):
                t = t.rstrip(' ') + tl
            if t != t0:
                log.append('dấu cách ghép %s (%s): %r -> %r' % (r[0], why, t0, t))
        if r[0] in OVERRIDE:
            log.append('ghi đè %s: %r -> %r' % (r[0], t, OVERRIDE[r[0]]))
            t = OVERRIDE[r[0]]
        r[4] = L.esc(t)
        n += 1
    with open(a.out, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\t'.join(X.COLS) + '\n')
        for r in rows:
            f.write('\t'.join(r) + '\n')
    log.append('đã điền %d dòng -> %s' % (n, os.path.relpath(a.out, L.ROOT)))
    os.makedirs(os.path.dirname(a.log), exist_ok=True)
    with open(a.log, 'w', encoding='utf-8') as f:
        f.write('\n'.join(log) + '\n')
    print('\n'.join(log[-12:]))


if __name__ == '__main__':
    main()
