#!/bin/sh
# chạy lại toàn bộ (cần numpy, Pillow, opencv-contrib-python-headless)
cd "$(dirname "$0")"
for s in mmchar tishiwenzi uitishi btn diui pause yesno music battle fengmian embedded; do python3 -I $s.py; done
