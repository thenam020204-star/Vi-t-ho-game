#!/usr/bin/env python3
"""T14: sinh hud_text.h: nhãn tiếng Việt của chế độ sửa bố cục (byte mã game, cùng quy tắc gen_dlg_text.py). Dùng: gen_hud_text.py OUT.h"""
import os, sys
WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))
words = open(os.path.join(WORK, 'vi_android', 'dict.txt'), encoding='utf-8').read().split('\n')
if words and words[-1] == '': words.pop()
def enc(s):
    out = bytearray()
    for i, w in enumerate(s.split(' ')):
        if i: out.append(0x20)
        k = words.index(w); out += bytes([0xC9 + k // 94, 0xA1 + k % 94])
    return bytes(out)
items = [('T_SAVE', 'Lưu'), ('T_DEF', 'Mặc định'), ('T_CANCEL', 'Huỷ'), ('T_TITLE', 'Sửa bố cục nút'), ('T_HINT', 'Kéo nút để di chuyển')]
out = ['/* sinh bởi gen_hud_text.py (không sửa tay) */']
for n, s in items:
    b = enc(s); out.append('static const u8 %s[] = {%s};' % (n, ','.join(str(x) for x in b + b'\0'))); out.append('#define %s_N %d' % (n, len(b)))
open(sys.argv[1], 'w').write('\n'.join(out) + '\n')
