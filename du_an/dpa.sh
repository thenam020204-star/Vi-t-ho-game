#!/bin/bash
# dpa: nhan "k|dich" tu stdin -> dp add (doi '|' dau tien thanh TAB), roi dp next
DP="python3 $(realpath "$(dirname "$0")")/scripts_skill/dp.py"
cd "$(dirname "$0")/dich"
cmd=${1:-add}
sed 's/|/\t/' | $DP $cmd && { [ "$cmd" = add ] && $DP next --chars ${CH:-1800} | grep -v '^#ctx HT:' ; true; }
