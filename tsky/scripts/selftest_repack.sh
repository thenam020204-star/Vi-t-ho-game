#!/bin/bash
# Kiem tra: extract -> repack (chua dich) giong tung byte goc; +1 test dich thu (round-trip qua parse lai)
set -e -o pipefail
cd "$(dirname "$0")/.."
SRC=${1:-work/x}
T=$(mktemp -d); trap 'rm -rf "$T"' EXIT
python3 -I scripts/extract.py "$SRC" "$T/t.tsv" >/dev/null
python3 -I scripts/repack.py "$SRC" "$T/t.tsv" "$T/o" >/dev/null
fail=0; n=0
for f in $(cd "$T/o" && find . -type f | sort); do
  n=$((n+1)); cmp -s "$T/o/$f" "$SRC/$f" || { echo "KHAC: $f"; fail=1; }
done
echo "so file so sanh: $n"
# test dich thu: doi 1 hang pak, 1 o item, 1 hang class, doc lai bang parser doc lap
python3 -I - "$SRC" "$T" <<'PY'
import sys, os
sys.path.insert(0, 'scripts')
from hanlib import *
src, T = sys.argv[1], sys.argv[2]
rows = read_tsv(T + '/t.tsv')
pick = {}
for r in rows:
    for pre in ('pak/', 't/item5', 'class:Main/l'):
        if r['file'].startswith(pre) and pre not in pick:
            pick[pre] = r
for r in pick.values():
    r['dich'] = 'Thu nghiem Viet'
    if r['file'].startswith('pak/') and '#' in r['goc']:
        r['dich'] = 'Thu nghiem Viet ' + ' '.join(TOK.findall(r['goc'])[:1])
write_tsv(T + '/t2.tsv', [[r[h] for h in HEADER] for r in rows])
PY
python3 -I scripts/repack.py "$SRC" "$T/t2.tsv" "$T/o2" >/dev/null
python3 -I - "$SRC" "$T" <<'PY'
import sys
sys.path.insert(0, 'scripts')
from hanlib import *
src, T = sys.argv[1], sys.argv[2]
rows = [r for r in read_tsv(T + '/t2.tsv') if r['dich']]
assert len(rows) == 3, len(rows)
for r in rows:
    f = r['file']
    if f.startswith('pak/'):
        h, bl = read_pak(open(T + '/o2/' + f, 'rb').read())
        nm, rest = r['khoa'].rsplit('/', 1); ci, fi = map(int, rest.split('.'))
        cmds = split_script(dict((n.decode(), b) for n, b in bl)[nm].decode())
        assert cmds[ci][fi] == r['dich']
    elif f.startswith('t/'):
        d = open(T + '/o2/' + f, 'rb').read(); h, ro, co = read_t(d)
        r0, c0 = map(int, r['khoa'].split('.'))
        assert read_t_cells(d, ro, co)[r0 * co + c0] == r['dich']
    else:
        d = open(T + '/o2/' + f[6:] + '.class', 'rb').read()
        assert r['dich'] in [s for _, s in [(0, mutf8_decode(e[1][2:])) for e in parse_cp(d)[0] if e and e[0] == 1]]
        assert d[:8] == open(src + '/' + f[6:] + '.class', 'rb').read()[:8]
print('test dich thu: OK')
PY
[ $fail = 0 ] && echo "SELFTEST OK: moi file giong tung byte" || { echo "SELFTEST LOI"; exit 1; }
