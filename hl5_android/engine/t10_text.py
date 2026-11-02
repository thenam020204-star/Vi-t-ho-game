"""Chuỗi tiếng Việt của T10, mã hoá bằng chính từ điển của bản build (dict.txt): mỗi từ = 2 byte (lead 0xC9.., trail 0xA1..),
cách nhau bằng dấu cách ASCII (đúng như Encoder của build_vi5). Mọi từ phải có sẵn trong từ điển (dạng thường, không tiền tố \\x01/\\x02)."""

LABEL = 'Phân giải'                    # common_text rec 210 (nhãn mục Cài đặt, trước là "Hiển thị ngang")
NOTE = 'Khởi động lại để áp dụng'         # khối chuỗi T10 trong vi.bin (chuỗi 0)
LABEL_REC = 210


def load_dict(dirv):
    import os
    w = open(os.path.join(dirv, 'dict.txt'), encoding='utf-8').read().split('\n')
    if w and w[-1] == '': w.pop()
    return w


def enc(text, words):
    out = bytearray()
    for i, t in enumerate(text.split(' ')):
        if i: out.append(0x20)
        k = words.index(t)                 # ValueError nếu từ chưa có trong từ điển
        assert k < 94 * 55
        out += bytes([0xC9 + k // 94, 0xA1 + k % 94])
    return bytes(out)
