#!/usr/bin/env python3
"""T10 (dữ liệu): ghi nhãn "Độ phân giải" vào common_text.dat rec 210 (id nhãn mục Cài đặt thứ 10; bản gốc là "Hiển thị ngang",
mục đã bỏ, không nơi nào dùng id 210 ngoài vòng tạo bảng nhãn).

  t10_data.py DIR_VI_ANDROID OUT_DIR       ghi OUT_DIR/c/csv/common_text.dat (dùng với vfs_pack --replace OUT_DIR)
"""
import sys, os
HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, os.path.join(HERE, '../engine')); sys.path.insert(0, os.path.join(HERE, '../vi'))
import t10_text, extract_android as X
src, out = sys.argv[1:3]
words = t10_text.load_dict(src)
recs = X.parse_dat(open(os.path.join(src, 'res/c/csv/common_text.dat'), 'rb').read())
b = t10_text.enc(t10_text.LABEL, words)
old = recs[t10_text.LABEL_REC]
recs[t10_text.LABEL_REC] = bytes([len(b)]) + b
os.makedirs(os.path.join(out, 'c/csv'), exist_ok=True)
open(os.path.join(out, 'c/csv/common_text.dat'), 'wb').write(X.build_dat(recs))
print('common_text rec %d: %r -> %r (%s)' % (t10_text.LABEL_REC, old.hex(), recs[t10_text.LABEL_REC].hex(), t10_text.LABEL))
