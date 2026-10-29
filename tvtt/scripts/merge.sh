#!/bin/bash
# dp merge + chuyen dong chi-dau-cau (khong dich) sang ASCII -> dich/dp_work/final/text_can_dich.tsv
set -e
cd "$(dirname "$0")/../dich"
python3 ../scripts/dp.py merge > /dev/null
python3 -I - <<'PY'
import sys; sys.path.insert(0, '../scripts')
from hanlib import read_tsv, write_tsv, HEADER
p = 'dp_work/final/text_can_dich.tsv'; R = read_tsv(p)
M = {' ％': ' %', '、': ',', '。': '.', '：': '：'}
for r in R:
    if r['goc'] in M: r['dich'] = M[r['goc']]
write_tsv(p, [[r[h] for h in HEADER] for r in R])
PY
python3 -I ../scripts/qa.py | tail -1
echo "merge OK"
