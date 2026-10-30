#!/bin/sh
# Build common.dat/common.idx Việt hoá.  sh build.sh <thư mục chứa common.idx+common.dat gốc> <thư mục ra>
set -e
H=$(cd "$(dirname "$0")" && pwd); T=$H/tools; IN=$1; OUT=$2; W=$OUT/_work
rm -rf "$W"; mkdir -p "$W/ex" "$W/rep" "$OUT"
python3 "$T/datpack.py" unpack "$IN/common.idx" "$IN/common.dat" "$W/ex"
# 1) font: vá mọi font Latin (bỏ font CJK/Hàn)
T="$T" python3 -c "
import sys,os; sys.path.insert(0,'$T'); import fontfmt, fontpatch_vi as fp
ex,rep='$W/ex','$W/rep'; n=0
for fn in sorted(os.listdir(ex)):
    d=open(os.path.join(ex,fn),'rb').read()
    if d[:4]!=b'\xe3\x0a\xf2\x23': continue
    cps={g[0] for g in fontfmt.parse(d)['G']}
    if 0x41 not in cps or 0x4e00 in cps or 0xac00 in cps: continue
    F=fp.Font(d); F.patch(); open(os.path.join(rep,fn),'wb').write(F.data()); n+=1
print('font đã vá:',n)"
# 2) text
F=${FINAL:-$H/text/dp_work/final}
python3 "$T/import_text.py" "$W/ex/2f6496cc.bin" "$W/rep/2f6496cc.bin" $F/t1.tsv $F/t2.tsv
# 3) đóng gói + kiểm
python3 "$T/datpack.py" pack "$IN/common.idx" "$IN/common.dat" "$W/rep" "$OUT/common.idx" "$OUT/common.dat"
python3 "$T/verify.py" "$IN" "$OUT" "$W/rep"
rm -rf "$W"
