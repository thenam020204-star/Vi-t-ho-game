#!/bin/bash
# run.sh name total every keys [storage_src] [sheet_start n]
cd /home/claude/work
S=/tmp/claude-0/-home-claude/de1ab490-47af-5320-9f4a-a07ae33a7aab/scratchpad
O=$S/$1; rm -rf $O; mkdir -p $O/storage
[ -n "$5" ] && cp -r $5/. $O/storage/
HL4_VI=vi/build/ktf/vi HL4_RES=vi/build/ktf java $JOPT -Dstorage=$O/storage -cp ${CP:-hl4j/build/test2} RunHeadless KTF.zip $2 $3 $O "$4" > $O.log 2>&1
python3 jsheet.py $O $O.png ${6:-0} ${7:-40} 0.5
