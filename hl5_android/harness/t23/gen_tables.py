"""T23: bảng markdown từ prof*.json. gen_tables.py file.json [file2.json...]  -> in: mỗi cửa sổ: tổng/khung, Process, Paint, host/khung, top-6 hàm exclusive."""
import json, sys, os
def short(n):
    n = n.split('(')[0] if not n.startswith('M:') else n
    return n
for f in sys.argv[1:]:
    d = json.load(open(f)); W, H = d['W'], d['H']
    print('### %dx%d (%s)' % (W, H, os.path.basename(f)))
    print('| cảnh | tổng B/khung | Process | Paint | menu mới (excl) | host/khung | top exclusive (% khung) |')
    print('|---|---|---|---|---|---|---|')
    for lab, w in d['windows'].items():
        s = w['snap']; per = w['per']; n = len(per)
        av = lambda k: sum(r.get(k, 0) for r in per) / n
        top = sorted(s['excl'].items(), key=lambda x: -x[1])[:6]
        t = '; '.join('%s %.0f%%' % (short(k), 100 * v / s['total']) for k, v in top)
        print('| %s | %.2f M | %.2f M | %.2f M | %.2f M | %.0f | %s |' % (lab, s['total'] / 1e6, av('mainProcess()') / 1e6, av('mainPaint()') / 1e6, av('menu_excl') / 1e6, sum(s['host'].values()), t))
