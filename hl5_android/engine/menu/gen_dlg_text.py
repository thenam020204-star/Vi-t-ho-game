#!/usr/bin/env python3
"""T16: sinh dlg_text.h: chuỗi nhãn nút "Bỏ qua" bằng byte mã game (từ điển $WORK/vi_android/dict.txt: mỗi từ = 2 byte (0xC9+k//94, 0xA1+k%94),
cùng quy tắc viui.decode / engine/menu/text.c::unit), để dialog.c vẽ bằng mui_text. Dùng: gen_dlg_text.py OUT.h"""
import os, sys
WORK = os.environ.get('WORK', os.path.expanduser('~/hl5work'))
words = open(os.path.join(WORK, 'vi_android', 'dict.txt'), encoding='utf-8').read().split('\n')
if words and words[-1] == '': words.pop()

def enc(sentence):
    out = bytearray()
    for i, w in enumerate(sentence.split(' ')):
        if i: out.append(0x20)
        k = words.index(w)                       # mục không có tiền tố \x01/\x02 (mode 0, không nén)
        out += bytes([0xC9 + k // 94, 0xA1 + k % 94])
    return bytes(out)

items = [('T_SKIP', 'Bỏ qua')]
out = ['/* sinh bởi gen_dlg_text.py (không sửa tay) */']
for n, s in items:
    b = enc(s)
    out.append('static const u8 %s[] = {%s};' % (n, ','.join(str(x) for x in b + b'\0')))
    out.append('#define %s_N %d' % (n, len(b)))
open(sys.argv[1], 'w').write('\n'.join(out) + '\n')
