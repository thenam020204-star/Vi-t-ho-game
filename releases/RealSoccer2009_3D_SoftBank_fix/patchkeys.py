# Remap SoftBank key codes in au.<clinit> to Nokia/J2ME Loader codes, in place (same length).
# Game logic bits (ai.a(1,16,..) menu nav; ai.java:4605 left/right):
#   cF -> bit 1 = UP, cG -> bit 16 = DOWN, cH -> bit 64 = LEFT, cI -> bit 4 = RIGHT.
# The original device runs the 800x480 game rotated on a portrait phone, so its
# key codes are rotated 90 degrees (up -5, down -2, left -1, right -6).
# Original block (au.<clinit> offset 7553..7584), cp #672..#678 = cF cG cH cI cJ cK cL:
#   bipush -5 putstatic cF | bipush -2 putstatic cG | iconst_m1 putstatic cH | bipush -6 putstatic cI
#   bipush -20 putstatic cJ | bipush -21 putstatic cK | bipush -22 putstatic cL
# cH needs -3 but is set by 1-byte iconst_m1, so swap store targets: iconst_m1 -> cF (-1 = up)
# and the first bipush -> cH (-3). Order of these constant stores does not matter.
import sys
def ps(i): return bytes([0xB3, i >> 8, i & 0xFF])
def bp(v): return bytes([0x10, v & 0xFF])
F, G, H, I, J, K, L = 672, 673, 674, 675, 676, 677, 678
old = bp(-5) + ps(F) + bp(-2) + ps(G) + b'\x02' + ps(H) + bp(-6) + ps(I) + bp(-20) + ps(J) + bp(-21) + ps(K) + bp(-22) + ps(L)
new = bp(-3) + ps(H) + bp(-2) + ps(G) + b'\x02' + ps(F) + bp(-4) + ps(I) + bp(-5) + ps(J) + bp(-6) + ps(K) + bp(-7) + ps(L)
assert len(old) == len(new)
d = open(sys.argv[1], 'rb').read()
assert d.count(old) == 1, d.count(old)
open(sys.argv[2], 'wb').write(d.replace(old, new))
print('patched at', d.find(old))
