#!/bin/bash
# usage: c.sh "steps"   -- gui lenh vao emulator dang chay (work/cmd.txt), doi xong
cd "$(dirname "$0")/.."
echo "$1" >> work/cmd.txt
n=$(wc -l < work/cmd.txt)
timeout ${2:-110} bash -c "until [ \"\$(cat work/cmd.txt.done 2>/dev/null)\" = \"$n\" ]; do sleep 0.5; done" && echo done || echo TIMEOUT
