#!/bin/bash
# usage: start.sh jar [savedir]
cd "$(dirname "$0")/.."
source scripts/env.sh
rm -f work/cmd.txt work/cmd.txt.done; touch work/cmd.txt
rm -rf work/live; mkdir -p work/live
nohup python3 scripts/t7_emu.py --fj $FJ --jar $1 --out work/live --cwd ${2:-work/fjsave} --size ${SIZE:-240x320} --follow work/cmd.txt --steps "wait 7" > work/live.out 2>&1 &
