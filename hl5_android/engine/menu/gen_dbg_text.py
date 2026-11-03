#!/usr/bin/env python3
"""Sinh dbg_text.h: chuỗi THẬT (byte mã game) cho trang widget thử (bản harness -DMUI_DEBUG), lấy từ dữ liệu đã build ($WORK/vi_android),
cùng chuỗi mà ui_new/menu/mock_menu.py dùng cho widgets_*.png (common_text rec 1,10-12,50-52,56-57,83-88,188,273; item_14 rec 2).
Dùng: gen_dbg_text.py OUT.h"""
import os, sys
HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, os.path.join(HERE, '../../ui_new/menu'))
import viui as U                      # đặt đường dẫn tools/vi, WORK
X = U.X

def raw(fn):
    b = open(os.path.join(U.VI, 'res', fn), 'rb').read()
    recs = X.parse_dat(b); lay = X.DAT_LAYOUT.get(fn); res = {}
    for ri, r in enumerate(recs):
        for (o, ps, n) in X.walk_rec(r, lay):
            res[(ri, o)] = bytes(r[o + ps:o + ps + n])
    return res

ct = raw('c/csv/common_text.dat'); it = raw('c/csv/item_14.dat')
S = [('T_TITLE', ct[(1, 0)]) if (1, 0) in ct else None]
def first(res, ri):
    ks = sorted(k for k in res if k[0] == ri)
    return res[ks[0]]
items = [('T_TITLE', first(ct, 1)), ('T_HP', first(ct, 10)), ('T_SP', first(ct, 11)), ('T_EXP', first(ct, 12)),
         ('T_YES', first(ct, 50)), ('T_NO', first(ct, 51)), ('T_B0', first(ct, 52)), ('T_OK', first(ct, 56)), ('T_B3', first(ct, 57)),
         ('T_R0', first(ct, 83)), ('T_R1', first(ct, 84)), ('T_R2', first(ct, 86)), ('T_R3', first(ct, 87)), ('T_R4', first(ct, 88)),
         ('T_POP', first(ct, 273)), ('T_POP2', first(ct, 188)),
         ('T_NAME', it[(2, 2)]), ('T_DESC', it[(2, 15)])]
out = ['/* sinh bởi gen_dbg_text.py (không sửa tay) */']
for n, b in items:
    out.append('static const u8 %s[] = {%s};' % (n, ','.join(str(x) for x in b + b'\0')))
    out.append('#define %s_N %d' % (n, len(b)))
# bề rộng tiêu đề khối của mockup (U.sw(spx, title)+2) để bố trí dòng khối giống hệt widgets_*.png
import mock_menu as MM
TITLES = ['1. cửa sổ: khung + tiêu đề + nút < + thanh tab', '2. nút: thường / chính / nhấn / vô hiệu', '3. thanh HP / SP / EXP',
          '4. ô lưới: trống / thường / chọn / trang bị+phẩm chất / số lượng', '5. hàng danh sách + thanh cuộn',
          '6. hộp mô tả (mô tả dài nhất; cuộn khi tràn)', '7a. popup chung có/không (chuỗi popup dài nhất)', '7b. popup chỉ OK']
for tag, (W, H) in (('SD', (400, 240)), ('HD', (1200, 540))):
    spx = MM.metrics(W, H).spx
    out.append('static const short TITLEW_%s[8] = {%s};' % (tag, ','.join(str(U.sw(spx, t) + 2) for t in TITLES)))
open(sys.argv[1], 'w').write('\n'.join(out) + '\n')
print('dbg_text.h: %d chuỗi' % len(items))
