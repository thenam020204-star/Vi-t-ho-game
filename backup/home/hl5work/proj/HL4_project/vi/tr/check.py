#!/usr/bin/env python3
"""Kiểm tra bản dịch: python3 check.py <chunk.json> <out.json>

chunk.json: list [{"id", "ko", ...}]   out.json: {"<id>": "bản dịch"}
Báo lỗi: thiếu id, mã điều khiển lệch, ký tự cấm, còn chữ Hàn, quá dài.
"""
import json, re, sys, collections

CODES = "`{}&|$^<'\"="          # mã màu/hiệu ứng + '=' (khoảng trắng hẹp): phải giữ nguyên số lượng
NEWLINE = ';'
FONT = set("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.,:!@/\\*()[]#%?~+- "
           "đĐáàảãạăắằẳẵặâấầẩẫậéèẻẽẹêếềểễệíìỉĩịóòỏõọôốồổỗộơớờởỡợúùủũụưứừửữựýỳỷỹỵ"
           "ÁÀẢÃẠĂẮẰẲẴẶÂẤẦẨẪẬÉÈẺẼẸÊẾỀỂỄỆÍÌỈĨỊÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢÚÙỦŨỤƯỨỪỬỮỰÝỲỶỸỴ…")
HANGUL = re.compile('[가-힣ㄱ-ㆎ]')


def est_bytes(s):
    """Ước lượng số byte sau khi mã hoá (mỗi âm tiết có dấu hoặc >=3 chữ = 2 byte)."""
    n = 0
    for m in re.finditer(r'[^\W\d_]+|.', s):
        w = m.group()
        if w[0].isalpha():
            n += 2 if (not w.isascii() or len(w) >= 3) else len(w)
        else:
            n += 2 if w == '…' else 1
    return n


def codes(s):
    return collections.Counter(c for c in s if c in CODES)


def main():
    chunk = json.load(open(sys.argv[1], encoding='utf-8'))
    out = json.load(open(sys.argv[2], encoding='utf-8'))
    errs = 0
    for e in chunk:
        k = str(e['id'])
        ko = e['ko']
        if k not in out:
            print('THIẾU', k, ko); errs += 1; continue
        vi = out[k]
        if not isinstance(vi, str) or (not vi.strip() and ko.strip()):
            print('RỖNG', k); errs += 1; continue
        if codes(vi) != codes(ko):
            print('MÃ LỆCH', k, dict(codes(ko)), '->', dict(codes(vi)), '|', ko, '=>', vi); errs += 1
        bad = sorted(set(c for c in vi if c not in FONT and c not in CODES and c != NEWLINE))
        if bad:
            print('KÝ TỰ CẤM', k, bad, vi); errs += 1
        if HANGUL.search(vi):
            print('CÒN CHỮ HÀN', k, vi); errs += 1
        cap = e.get('cap') or 254
        b = est_bytes(vi)
        if b > cap:
            print('QUÁ DÀI', k, b, '>', cap, vi); errs += 1
        if vi != vi.strip() and ko == ko.strip():
            print('KHOẢNG TRẮNG ĐẦU/CUỐI', k, repr(vi)); errs += 1
    extra = set(out) - set(str(e['id']) for e in chunk)
    if extra:
        print('THỪA id', sorted(extra)[:20]); errs += 1
    print('OK' if errs == 0 else '%d lỗi' % errs)


if __name__ == '__main__':
    main()
