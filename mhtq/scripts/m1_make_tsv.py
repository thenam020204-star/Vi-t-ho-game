# -*- coding: utf-8 -*-
"""Tạo TSV thử Mốc 1: điền tạm vài chuỗi tiếng Việt có dấu vào bản trích xuất (không phải bản dịch thật)."""
import os
import sys

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import text_extract as X     # noqa: E402

TEST = {
    'C.i.25': u'Bật nhạc nền không?',
    'C.i.22': u'Có',
    'C.i.20': u'Không',
    'C.m.65': u'Trợ giúp',
    'C.m.25': u'[Mô tả]|Yêu thánh Ma Thiên bị Kiếm Tiên đả thương, thề báo thù.|ẤẦẨẪẬ ẮẰẲẴẶ ẾỀỂỄỆ ỐỒỔỖỘ ỚỜỞỠỢ ỨỪỬỮỰ|áàảãạ ăắằẳẵặ âấầẩẫậ éèẻẽẹ êếềểễệ|íìỉĩị óòỏõọ ôốồổỗộ ơớờởỡợ úùủũụ ưứừửữự ýỳỷỹỵ đĐ|Diệp Vấn Thiên, Tiểu Duy, Anh Ninh',
    'SN000': u'Ma Thiên',
    'C.s.7': u'Kiếm Tiên',
    'C.s.8': u'Ma Thiên',
    'C.h.73': u'Ma Thiên, ngươi dẫn yêu ma gây họa nhân gian, hôm nay ta dùng ma kiếm Ỷ Thiên này diệt trừ cho dân!',
    'C.h.6': u'#00FF00Phím bên trái màn hình là lệnh chiến đấu: phím 1 đánh thường, phím 2-6 kỹ năng đặc biệt, phím 8 dùng thuốc##',
    'S008.004.0': u'Ngày 21 tháng 12 năm 2012',
    'S008.010.1': u'Thuận lợi đến Hồ Yêu Động rồi! Ngươi ở đây đợi ta nhé.',
}


def main():
    src = os.path.join(L.ROOT, 'work', 'text_can_dich.tsv')
    out = os.path.join(L.ROOT, 'work', 'build', 'm1_test.tsv')
    os.makedirs(os.path.dirname(out), exist_ok=True)
    rows = X.read_tsv(src)
    with open(out, 'w', encoding='utf-8', newline='\n') as f:
        f.write('\t'.join(X.COLS) + '\n')
        for r in rows:
            if r[0] in TEST:
                r[4] = L.esc(TEST[r[0]])
            f.write('\t'.join(r) + '\n')
    print(out)


if __name__ == '__main__':
    main()
