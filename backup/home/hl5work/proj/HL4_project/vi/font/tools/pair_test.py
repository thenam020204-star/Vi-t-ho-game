#!/usr/bin/env python3
"""Kiểm tra không còn glyph nào bị ký tự kế tiếp vẽ đè mất nét.

Mô phỏng đúng cách engine vẽ TỪNG ký tự kèm viền 4 hướng:
    với mỗi ký tự: vẽ viền tại (x±1,y) (x,y±1) rồi vẽ nét tại (x,y); x += advance
So từng pixel nét của ký tự trước với khi vẽ riêng lẻ, cho toàn bộ cặp glyph.

Usage: pair_test.py <font.bin>
"""
import sys, os
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import fontbin

FILL, OUT = 1, 2


def load(path):
    f = fontbin.decode(path)
    off = []
    x = 0
    for w in f['widths']:
        off.append(x)
        x += w
    ink = {}
    for i, c in enumerate(f['chars']):
        s = set()
        for y in range(f['height']):
            for cx in range(f['widths'][i]):
                if f['rows'][y][off[i] + cx]:
                    s.add((cx, y))
        ink[c] = s
    return f['chars'], dict(zip(f['chars'], f['widths'])), ink


def main(path):
    chars, W, ink = load(path)
    bad = []
    for a in chars:
        ia = ink[a]
        if not ia:
            continue
        wa = W[a]
        for b in chars:
            px = {}
            for dx, dy in ((0, 1), (0, -1), (1, 0), (-1, 0)):
                for (x, y) in ia:
                    px[(x + dx, y + dy)] = OUT
            for p in ia:
                px[p] = FILL
            for dx, dy in ((0, 1), (0, -1), (1, 0), (-1, 0)):
                for (x, y) in ink[b]:
                    px[(x + wa + dx, y + dy)] = OUT
            for (x, y) in ink[b]:
                px[(x + wa, y)] = FILL
            lost = sum(1 for p in ia if px[p] != FILL)
            if lost:
                bad.append((a, b, lost))
    n = len(chars) ** 2
    print('%s: %d/%d cap bi mat net' % (os.path.basename(path), len(bad), n))
    for a, b, k in bad[:10]:
        print('   %r + %r  mat %d px' % (a, b, k))
    print('KET QUA:', 'PASS' if not bad else 'FAIL')
    return 1 if bad else 0


if __name__ == '__main__':
    sys.exit(main(sys.argv[1]))
