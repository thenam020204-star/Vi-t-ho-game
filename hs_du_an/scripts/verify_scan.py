"""T2: đối chiếu utfscan.scan với chuỗi harness (parser của game) trên từng đoạn.
Dùng: python3 -I verify_scan.py <WS> [phần=1] [log ra]
Cần: $WS/hc (Harness đã biên dịch), $WS/jars/hsN.jar, $WS/work/tools/freej2me_plus.jar, $WS/x/N."""
import sys, os, glob, subprocess, collections
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import utfscan, segments, textfmt

CFG = {  # phần -> (lớp+hàm .m, lớp+hàm i.dat, stub)
    1: (('main/d', 'a', 2), ('d/a', 'a', 1), 'd.a:q:512'),
    2: (('main/c', 'a', 2), ('b/a', 'b', 1), 'b.a:p:512'),
    3: (('b/f/b', 'a', 2), ('a/f', 'b', 1), 'a.f:o:512'),
    4: (('b/f/b', 'a', 2), ('a/f', 'a', 1), 'a.f:o:512'),
    5: (('a/d/b', 'a', 2), ('b/a', 'a', 1), 'b.a:o:512'),
}

TAIL = {3: 'b/c/c', 4: 'b/c/c', 5: 'a/b/a'}  # lớp có `private static X[] a(DataInputStream)` đọc danh sách đối tượng của .m2

def build_input(ws, n, root=None):
    (mc, mm, mp), (ic, im, ip), _ = CFG[n]
    root = root or 'x/%d' % n
    lines = []
    for f in sorted(glob.glob(os.path.join(ws, root, 'map', '*.m*')), key=lambda s: int(os.path.basename(s).split('.')[0])):
        rel = os.path.relpath(f, ws)
        bb = open(f, 'rb').read(); p0, A, _, segs = textfmt.m_parse(bb)
        for _, s, e in segs:
            lines.append((rel, s, e, mc, mm, mp))
        if f.endswith('.m2'):  # tên bản đồ (tiền tố) + phần bản đồ + danh sách đối tượng/NPC: đo danh sách rồi cho game đọc
            lines.append((rel, textfmt.m2_list_start(bb, p0 + 8 + A), len(bb), TAIL[n], 'a', 1))
    f = os.path.join(ws, root, 'i.dat')
    for s, e, _ in segments.idat_segments(f):
        lines.append((os.path.relpath(f, ws), s, e, ic, im, ip))
    return lines

def run_harness(ws, n, lines):
    stdin = ''.join('\t'.join(map(str, l)) + '\n' for l in lines)
    cmd = ['env', '-u', 'JAVA_TOOL_OPTIONS', 'java', '-Dfile.encoding=ISO_8859_1', '-Djava.awt.headless=true',
           '-Dharness.stub=' + CFG[n][2], '-cp', os.path.join(ws, 'hc') + ':' + os.path.join(ws, 'work/tools/freej2me_plus.jar'), 'Harness',
           os.path.join(ws, 'jars/hs%d.jar' % n), os.path.join(ws, 'work/tools/freej2me_plus.jar')]
    r = subprocess.run(cmd, input=stdin.encode(), capture_output=True, cwd=ws)
    return r.stdout.decode('utf-8').split('\n')[:-1]

def has_cjk(s):
    return any(utfscan.is_cjkish(c) for c in s)

def main():
    ws = sys.argv[1]; n = int(sys.argv[2]) if len(sys.argv) > 2 else 1
    logp = sys.argv[3] if len(sys.argv) > 3 else None
    lines = build_input(ws, n, sys.argv[4] if len(sys.argv) > 4 else None)
    out = run_harness(ws, n, lines)
    diff = []; ascii_only = []; ok = 0; derived_l = []; unread = []; scan_total = 0; cache = {}
    if len(out) != len(lines):
        print('SỐ DÒNG khác: %d vs %d' % (len(out), len(lines))); sys.exit(1)
    for l, o in zip(lines, out):
        p = o.split('\t')
        if p[0] != 'OK':
            diff.append('HARNESS %s %s' % (p[0], o[:200])); continue
        ok += 1
        b = cache.setdefault(l[0], open(os.path.join(ws, l[0]), 'rb').read())
        hs = [] if len(p) < 5 or p[4] == '' else [textfmt.unesc(x) for x in p[4].split('\x1f')]
        hset = set(x for x in hs if has_cjk(x))
        for x in hs:
            if not has_cjk(x) and x.strip():
                ascii_only.append('%s@%d\t%r' % (l[0], l[1], x))
        sset = set(s for _, s in utfscan.scan(b, l[1], l[2]))
        scan_total += len(sset)
        seg = b[l[1]:l[2]]
        reads = set(int(x) for x in p[5].split(',')) if len(p) > 5 and p[5] else set()
        sc = utfscan.scan(b, l[1], l[2]); scoff = set(o for o, _ in sc)
        # (1) mọi chuỗi CJK parser của game đọc bằng readUTF phải nằm trong kết quả quét (đúng offset)
        for o in sorted(reads):
            L = (b[o] << 8) | b[o + 1]
            try: t = b[o + 2:o + 2 + L].decode('utf-8')
            except UnicodeDecodeError: continue
            if has_cjk(t) and o not in scoff:
                diff.append('%s@%d game-đọc-mà-quét-sót @%d %r' % (l[0], l[1], o, t))
        # (2) chuỗi quét thấy mà parser không đọc bằng readUTF (nghi dữ liệu thừa/giả dương) -> liệt kê riêng
        for o, t in sc:
            if o not in reads: unread.append('%s@%d\t%r' % (l[0], o, t))
        # (3) chuỗi harness lưu trong object có nguyên văn trong đoạn phải được quét thấy; còn lại = game tự ghép
        sset = set(t for _, t in sc)
        derived = set(x for x in hset - sset if x.encode('utf-8') not in seg)
        for x in sorted(derived): derived_l.append('%s@%d\t%r' % (l[0], l[1], x))
        for x in hset - sset - derived: diff.append('%s@%d harness-có-nguyên-văn-mà-quét-sót %r' % (l[0], l[1], x))
    res = ['P%d: đoạn=%d harness_OK=%d chuỗi_scan(tập/đoạn)=%d sai_khác=%d chuỗi_harness_thuần_ASCII=%d chuỗi_harness_tự_ghép=%d chuỗi_quét_mà_game_không_readUTF=%d' %
           (n, len(lines), ok, scan_total, len(diff), len(ascii_only), len(derived_l), len(unread))]
    res += ['DIFF ' + d for d in diff] + ['ASCII ' + a for a in ascii_only] + ['DERIVED ' + d for d in derived_l] + ['UNREAD ' + u for u in unread]
    txt = '\n'.join(res)
    if logp: open(logp, 'w', encoding='utf-8').write(txt + '\n')
    print(res[0]); sys.exit(1 if diff else 0)
if __name__ == '__main__':
    main()
