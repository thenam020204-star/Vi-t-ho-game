#!/bin/bash
# ovf_run.sh <name> <total ms> "<keys>" [storage]: text-overflow check of one scenario.
# Korean run (baseline) then Vietnamese run; results in $S/ovf_<name>/{ovf.txt,draws.txt,ovf/*.png} (+ _k for Korean)
cd /home/claude/work/hl5j
S=${S:-/tmp/claude-0/-home-claude/73d77261-71e5-5de7-a696-71fb00a812c7/scratchpad}; export S
[ -n "$SKIPK" ] && [ -s $S/ovf_$1_k/ovf.txt ] || HL4_OVF=base tools/run.sh ovf_$1_k $2 100000 "$3" "$4" 0 0 >/dev/null 2>&1
RES=vi5/build/overlay HL4_VI=vi5/build/overlay/vi5 HL4_OVF=1 HL4_OVFBASE=$S/ovf_$1_k/ovf.txt tools/run.sh ovf_$1 $2 100000 "$3" "$4" 0 0 >/dev/null 2>&1
mkdir -p $S/ovf_$1 && cp /home/claude/work/vi5/build/overlay/vi5/dict.txt /home/claude/work/vi5/build/enc_map.json $S/ovf_$1/
echo "$1: $(wc -l < $S/ovf_$1/ovf.txt) flagged, $(wc -l < $S/ovf_$1/draws.txt) draws"
