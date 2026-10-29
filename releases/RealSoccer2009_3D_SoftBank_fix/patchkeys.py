# session 2: remap SoftBank/Sharp key codes in au.<clinit> to Nokia/J2ME Loader codes.
# Each entry: (field, cp index, old bipush value, new bipush value). In-place, same length.
import sys
P = [('cF(right)', 672, -5, -4), ('cG(left)', 673, -2, -3), ('cI(down)', 675, -6, -2),
     ('cJ(fire)', 676, -20, -5), ('cK(soft1)', 677, -21, -6), ('cL(soft2)', 678, -22, -7)]
d = bytearray(open(sys.argv[1], 'rb').read())
for name, idx, old, new in P:
    pat = bytes([0x10, old & 0xFF, 0xB3, idx >> 8, idx & 0xFF])
    n = d.count(pat); assert n == 1, (name, n)
    i = d.find(pat); d[i + 1] = new & 0xFF
    print(name, old, '->', new, 'at', i)
open(sys.argv[2], 'wb').write(d)
