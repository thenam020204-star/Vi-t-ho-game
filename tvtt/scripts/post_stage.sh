#!/bin/bash
# Don stage truoc khi dong jar: xoa anh logo/quang cao chi con duoc q.a() goc tham chieu (than da thay = rong).
V=$1; ST=$2
for f in bg gameLogo gameLogo1 logo0 logo1 logo2 logo3 logo4 logo5 logo51 logo52 soundAsk cwa; do rm -f $ST/logo/$f.png; done
