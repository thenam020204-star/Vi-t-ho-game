#!/bin/bash
# VI death test: find the player's HP (pattern from the Korean run) then poke 0 and choose "yes"
cd /home/claude/work/hl5j
export S=/tmp/claude-0/-home-claude/73d77261-71e5-5de7-a696-71fb00a812c7/scratchpad
export RES=vi5/build/overlay HL4_VI=vi5/build/overlay/vi5 HL4_AOT=1
HL4_DUMPMEM=36400 tools/run.sh dv 36500 5000 "$(cat profiles/keys_die.txt)" "" 0 1 >/dev/null
A=$(cd tools && python3 -c "
import mem
S='$S/'
k=mem.load(S+'dk/mem.bin'); v=mem.load(S+'dv/mem.bin')
a=0x215d618; pat=k[a>>16][(a&0xffff)-0x10:(a&0xffff)+0x10]
for pi,pg in v.items():
    i=pg.find(pat)
    if i>=0: print(hex((pi<<16)+i+0x10)[2:]); break
")
echo HP=$A
HL4_POKE="38000:$A=0" tools/run.sh jr 44000 500 "$(cat profiles/keys_die.txt)" "" 76 17 >/dev/null
