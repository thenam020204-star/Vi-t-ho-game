#!/bin/bash
# run.sh name total_ms every_ms "keys" [storage_src] [sheet_start n]
# env: CP (classpath, default hl5j/build/t), JOPT, RES (overlay dir), plus HL4_* debug vars
cd /home/claude/work
S=${S:-/tmp/hl5run}; mkdir -p $S   # thư mục kết quả (đặt biến S để đổi)
O=$S/$1; rm -rf $O; mkdir -p $O/storage
[ -n "$5" ] && cp -r $5/. $O/storage/
export JAVA_TOOL_OPTIONS=
HL4_RES=${RES:-} java $JOPT -Dstorage=$O/storage -cp ${CP:-hl5j/build/t} RunHeadless HL5.zip $2 $3 $O "$4" > $O.log 2>&1
python3 hl5j/tools/jsheet.py $O $O.png ${6:-0} ${7:-40} 0.4
