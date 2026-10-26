"""T4(a,b): (a) TSV chưa dịch -> repack giống hệt từng byte; (b) dịch giả "Thử "+gốc+" dài hơn" -> harness OK 100%
và tập chuỗi harness (CJK) == tập chuỗi giả; file đơn giản đọc bằng formats_pN == chuỗi giả.
Dùng: python3 -I test_fake.py <WS> <phần> [thư mục tsv]"""
import sys, os, subprocess, filecmp, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import textfmt, verify_scan, utfscan
D = os.path.dirname(os.path.abspath(__file__))

def sh(*a): return subprocess.run(['python3', '-I'] + list(a), capture_output=True, text=True)

def main():
    ws, n = sys.argv[1], int(sys.argv[2]); tdir = os.path.join(ws, 't4'); os.makedirs(tdir, exist_ok=True)
    src = os.path.join(ws, 'x', str(n)); tsv = os.path.join(tdir, 'p%d.tsv' % n)
    r = sh(os.path.join(D, 'extract.py'), src, tsv); print(r.stdout.strip(), r.stderr[-300:])
    r = sh(os.path.join(D, 'repack.py'), src, tsv, os.path.join(tdir, 'r%d' % n)); assert r.returncode == 0, r.stderr
    cmp = subprocess.run(['diff', '-rq', src, os.path.join(tdir, 'r%d' % n)], capture_output=True, text=True)
    print('(a) round-trip byte-identical:', 'ĐẠT' if cmp.returncode == 0 else 'LỖI ' + cmp.stdout[:300])
    # (b)
    rows = open(tsv, encoding='utf-8').read().split('\n'); fake = set(); out = [rows[0]]
    for ln in rows[1:]:
        if not ln: continue
        c = ln.split('\t'); g = textfmt.unesc(c[3]); f = 'Thử ' + g + ' dài hơn'; fake.add(f)
        c[4] = textfmt.esc(f); out.append('\t'.join(c))
    ftsv = os.path.join(tdir, 'p%d_fake.tsv' % n); open(ftsv, 'w', encoding='utf-8').write('\n'.join(out) + '\n')
    r = sh(os.path.join(D, 'repack.py'), src, ftsv, os.path.join(tdir, 'f%d' % n)); assert r.returncode == 0, r.stderr
    root = 't4/f%d' % n
    lines = verify_scan.build_input(ws, n, root); res = verify_scan.run_harness(ws, n, lines)
    assert len(res) == len(lines)
    okc = sum(1 for x in res if x.startswith('OK')); got = set()
    for x in res:
        p = x.split('\t')
        if p[0] == 'OK' and len(p) > 4 and p[4]:
            got |= set(textfmt.unesc(s) for s in p[4].split('\x1f'))
    gotc = set(s for s in got if verify_scan.has_cjk(s) or s.startswith('Thử '))
    # chuỗi giả của file đơn giản (không có harness) được kiểm riêng bằng utfscan trên file đã repack
    seqfake = set()
    for rel, kind in textfmt.units(os.path.join(tdir, 'f%d' % n)):
        if kind in ('seq', 'off2'):
            seqfake |= set(s for _, s, _ in textfmt.strings(open(os.path.join(tdir, 'f%d' % n, rel), 'rb').read(), kind))
    hfake = set(s for s in gotc if s.startswith('Thử '))
    allscan = set()
    for rel, kind in textfmt.units(os.path.join(tdir, 'f%d' % n)):
        allscan |= set(s for _, s, _ in textfmt.strings(open(os.path.join(tdir, 'f%d' % n, rel), 'rb').read(), kind))
    print('(b) harness OK %d/%d; chuỗi giả harness=%d; chuỗi giả quét lại=%d; harness⊄quét=%d' %
          (okc, len(lines), len(hfake), len(allscan), len(hfake - allscan)))
    ok = okc == len(lines) and not (hfake - allscan) and allscan == fake
    print('(b) fake == tập chuỗi quét lại:', allscan == fake, '=>', 'ĐẠT' if ok and cmp.returncode == 0 else 'LỖI')
    if n == 1:
        r = sh(os.path.join(D, 'formats_p1.py'), os.path.join(tdir, 'f1'))
        print('(b) formats_p1 trên bản giả: rc=%d' % r.returncode); print(r.stdout[:900])
    sys.exit(0 if ok and cmp.returncode == 0 else 1)
main()
