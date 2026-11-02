"""Do do rong px theo f12 (advance that).  python3 measure.py "chuoi" ...  | -f file (moi dong 1 chuoi)"""
import sys
sys.path.insert(0, 'work/tools/pixeltext-kit')
from pixeltext import container
d = container.decode(open('work/tools/fonts/f12.bin', 'rb').read())
W = dict(zip(d['chars'], d['widths']))
sp = W.get(' ', 4)
def width(s): return sum(W.get(c, sp) for c in s)
def missing(s): return sorted(set(c for c in s if c not in W))
if __name__ == '__main__':
    items = open(sys.argv[2], encoding='utf-8').read().splitlines() if sys.argv[1] == '-f' else sys.argv[1:]
    for s in items:
        print(width(s), s, ('THIEU:' + ''.join(missing(s))) if missing(s) else '')
