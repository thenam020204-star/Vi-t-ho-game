#!/bin/bash
# Khôi phục môi trường làm việc sau khi giải nén gói dự án.
# Dự án PHẢI nằm ở /home/claude/work (các script dùng đường dẫn tuyệt đối này).
set -e
W=/home/claude/work
[ "$(cd "$(dirname "$0")" && pwd)" = "$W" ] || { echo "Hãy giải nén sao cho thư mục này là $W"; exit 1; }
cd $W
export JAVA_TOOL_OPTIONS=
# 1) HL4: bung jar gốc (tài nguyên thô dùng cho extract/build_vi)
rm -rf lgtjar ktfjar tmpx && mkdir -p lgtjar ktfjar tmpx
unzip -q -o LGT.zip -d tmpx/l && (cd lgtjar && unzip -q -o ../tmpx/l/*.jar)
unzip -q -o KTF.zip -d tmpx/k && (cd ktfjar && unzip -q -o ../tmpx/k/*.jar)
rm -rf tmpx
# 2) HL4: biên dịch harness headless + HL4Tool (Java SE, cần JDK 11+)
(cd hl4j && ./tbuild.sh build/test2)
# 3) HL5: bung HL5.zip -> hl5/orig, hl5/jar (binary.mod + res/), biên dịch harness HL5
if [ -f HL5.zip ]; then
  rm -rf hl5/orig hl5/jar && mkdir -p hl5/orig hl5/jar
  unzip -q -o HL5.zip -d hl5/orig && (cd hl5/jar && unzip -q -o ../orig/*.jar)
  (cd hl5j && ./tbuild.sh build/t)
  pip install openpyxl --break-system-packages -q 2>/dev/null || true   # vi5/export5.py, build_vi5.py
fi
# công cụ RE (disasm) cho hl5/tools/img.py
python3 -c "import capstone" 2>/dev/null || pip install capstone --break-system-packages -q || true
echo "OK: HL4 -> ./make.sh ; HL5 -> ./make5.sh (harness: hl5j/build/t, chạy: hl5j/tools/run.sh)"
