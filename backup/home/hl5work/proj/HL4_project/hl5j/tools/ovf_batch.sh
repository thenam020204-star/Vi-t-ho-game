#!/bin/bash
# ovf_batch.sh NAME...: run ovf_run.sh for $S/k<NAME>.txt scripts, two at a time (storage save_mk11 when the
# script starts with LOAD). Total time = sum of the script delays + 15 s. Writes $S/batch.done when finished.
cd /home/claude/work/hl5j
S=${S:-/tmp/claude-0/-home-claude/73d77261-71e5-5de7-a696-71fb00a812c7/scratchpad}; export S
rm -f $S/batch.done
run1() {
  local n=$1 k; k=$(cat $S/k$n.txt)
  local t; t=$(python3 -c "
import re,sys; s=sys.argv[1]; print(sum(int(x) for x in re.findall(r'(?:^|;)(\d+)(?=[:;]|$)', s[4:])) + 15000 + (12000 if 'u:OK' in s else 0))" "$k")
  local st=""; [[ "$k" == *"LEFT;600:OK;9000"* ]] && st=$S/save_mk11
  tools/ovf_run.sh $n $t "$k" $st
}
lane() { for n in "$@"; do run1 $n; done; }
A=(); B=(); i=0
for n in "$@"; do if (( i % 2 == 0 )); then A+=($n); else B+=($n); fi; i=$((i+1)); done
lane "${A[@]}" & lane "${B[@]}" & wait
touch $S/batch.done
