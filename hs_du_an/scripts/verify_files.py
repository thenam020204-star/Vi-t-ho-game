"""Kiểm các file dữ liệu không-theo-đoạn (t.dat, tips.dat, c.dat, item15.dat, monster.dat, help.dat...) bằng chính loader của game
(Harness chế độ LOAD, chạy qua MIDletLoader của FreeJ2ME). Với mỗi file: tập chuỗi CJK game nạp được == tập chuỗi CJK của textfmt/utfscan.
Dùng: python3 -I verify_files.py <WS> <phần> [thư mục dữ liệu = x/N]"""
import sys, os, struct, subprocess, zipfile, tempfile
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import textfmt, utfscan

# phần -> {'stub': ..., 'files': {tên file: [(lớp, phương thức, đối số...)]}}; 'monster': (lớp nạp, lớp lấy bản ghi, phương thức lấy)
S = lambda c, m, f: [(c, m, '/' + f)]
CFG = {
 1: dict(stub='d.a:q:512,d.a:n:64', files={
        't.dat': S('h/b', 'b', 't.dat'), 't2.dat': S('h/b', 'b', 't2.dat'), 'tips.dat': S('h/b', 'b', 'tips.dat'),
        'c.dat': [('d/a', 'b')], 'item15.dat': [('d/a', 'e')], 'fb_skill.dat': [('d/a', 'f')]},
        monster=('d/a', 'g', 'c')),
 2: dict(stub='b.a:p:512,b.f:r:64,main.b:a:new', files={
        't.dat': S('e/a/b', 'b', 't.dat'), 'tips.dat': S('e/a/b', 'b', 'tips.dat'), 'help.dat': S('e/a/b', 'b', 'help.dat'),
        'c.dat': [('b/a', 'b')], 'item15.dat': [('b/a', 'e')]}, monster=('b/a', 'f', 'c')),
 3: dict(stub='a.f:o:512,a.g:d:64', files={
        't.dat': S('b/e/a', 'c', 't.dat'), 'tips.dat': S('b/e/a', 'c', 'tips.dat'), 'lk_tips.dat': S('b/e/a', 'c', 'lk_tips.dat'),
        'help.dat': S('b/e/a', 'c', 'help.dat'),
        'c.dat': [('a/f', 'b')], 'item15.dat': [('a/f', 'e')]}, monster=('a/f', 'f', 'c')),
 4: dict(stub='a.f:o:512,a.g:d:64', files={
        't.dat': S('b/e/a', 'c', 't.dat'), 'tips.dat': S('b/e/a', 'c', 'tips.dat'), 'lk_tips.dat': S('b/e/a', 'c', 'lk_tips.dat'),
        'help.dat': S('b/e/a', 'c', 'help.dat'),
        'c.dat': [('a/f', 'b')], 'item15.dat': [('a/f', 'e')]}, monster=('a/f', 'f', 'd')),
 5: dict(stub='b.a:o:512,b.f:z:64', files={
        't.dat': S('a/c/b', 'c', 't.dat'), 'tips.dat': S('a/c/b', 'c', 'tips.dat'), 'lk_tips.dat': S('a/c/b', 'c', 'lk_tips.dat'),
        'help.dat': S('a/c/b', 'c', 'help.dat'),
        'c.dat': [('b/a', 'b')], 'item15.dat': [('b/a', 'e')]}, monster=('b/a', 'f', 'd')),
}

def mkjar(src, dst):
    with zipfile.ZipFile(dst, 'w', zipfile.ZIP_DEFLATED) as z:
        for r, _, fs in os.walk(src):
            for f in fs:
                p = os.path.join(r, f); z.write(p, os.path.relpath(p, src))

def run(ws, n, jar, lines, stub):
    fj = os.path.join(ws, 'work/tools/freej2me_plus.jar')
    cmd = ['env', '-u', 'JAVA_TOOL_OPTIONS', 'java', '-Dfile.encoding=ISO_8859_1', '-Djava.awt.headless=true', '-Dharness.stub=' + stub,
           '-cp', os.path.join(ws, 'hc') + ':' + fj, 'Harness', jar, fj]
    r = subprocess.run(cmd, input=''.join('\t'.join(l) + '\n' for l in lines).encode(), capture_output=True, cwd=ws)
    return [x for x in r.stdout.decode('utf-8').split('\n') if x], r.stderr.decode('utf-8', 'replace')

def cjk(s): return any(utfscan.is_cjkish(c) for c in s)

def check(ws, n, d, jar=None, quiet=False):
    """Trả (số file đạt, số file lỗi, dòng báo cáo)."""
    cfg = CFG[n]; jar = jar or os.path.join(tempfile.mkdtemp(), 'x.jar'); mkjar(d, jar)
    rep = []; bad = 0; good = 0
    todo = [(f, ls, 'seq') for f, ls in cfg['files'].items() if os.path.exists(os.path.join(d, f))]
    mon = os.path.join(d, 'monster.dat')
    for f, ls, kind in todo + ([('monster.dat', None, 'mon')] if os.path.exists(mon) else []):
        b = open(os.path.join(d, f), 'rb').read()
        if kind == 'mon':
            _, ent, base, blen, offs = textfmt.off_parse(b, 2)
            c1, m1, g = cfg['monster']; ls = [(c1, m1)] + [(c1, g, str(i) + 's') for i, _ in ent]
            want = set(s for _, s, _ in textfmt.strings(b, 'off2'))
        else:
            want = set(s for _, s, _ in textfmt.strings(b, 'seq'))
        out, err = run(ws, n, jar, [('LOAD',) + tuple(l) for l in ls], cfg['stub'])
        errs = [o for o in out if o.startswith('ERR')]
        got = set()
        for o in out:
            p = o.split('\t')
            if p[0] == 'OK' and len(p) > 4 and p[4]: got |= set(textfmt.unesc(x) for x in p[4].split('\x1f'))
        gotc = set(x for x in got if cjk(x))
        raw = b.decode('latin-1')
        extra = gotc - want
        # chuỗi game nạp có nguyên văn trong file mà quét sót (quét phải thấy hết); chuỗi không có nguyên văn = game tự ghép
        sot = set(x for x in extra if x.encode('utf-8').decode('latin-1') in raw)
        ok = not errs and len(out) == len(ls) and 'Exception' not in err.replace('freej2me', '') and not (want - gotc) and not sot
        rep.append('%-13s %s game_nạp=%d quét=%d thiếu=%d tự_ghép=%d quét_sót=%d%s' % (f, 'OK ' if ok else 'LỖI', len(gotc), len(want), len(want - gotc), len(extra) - len(sot), len(sot),
                   '' if ok else ' | ' + (errs[0][:150] if errs else err[:200].replace('\n', ' '))))
        if ok: good += 1
        else: bad += 1
    if not quiet: print('\n'.join(rep))
    return good, bad, rep

if __name__ == '__main__':
    ws, n = sys.argv[1], int(sys.argv[2]); d = sys.argv[3] if len(sys.argv) > 3 else os.path.join(ws, 'x', str(n))
    g, b, _ = check(ws, n, d); sys.exit(1 if b else 0)
