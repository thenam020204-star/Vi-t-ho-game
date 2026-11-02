"""Kiểm bản build: giải nén lại toàn bộ, khối không thay phải trùng byte với gốc, khối thay phải trùng file thay,
font parse/round-trip được và đủ glyph tiếng Việt, text parse/round-trip được."""
import sys, os, struct
sys.path.insert(0, os.path.dirname(__file__)); import datpack, fontfmt, locfmt, fontpatch_vi
inp, out, rep = sys.argv[1:4]
h0, E0, D0 = datpack.read(inp + '/common.idx', inp + '/common.dat')
h1, E1, D1 = datpack.read(out + '/common.idx', out + '/common.dat')
assert h0 == h1 and [e[0] for e in E0] == [e[0] for e in E1] and [e[4] for e in E0] == [e[4] for e in E1]
end = 0; lua = 0; vi = set(fontpatch_vi.vi_targets()); same = chg = 0
for a, b in zip(E0, E1):
    raw = datpack.inflate(D1[b[3]:b[3] + b[1]], b[2]); end = max(end, b[3] + b[1])
    p = os.path.join(rep, '%08x.bin' % a[0])
    if os.path.exists(p):
        assert raw == open(p, 'rb').read(); chg += 1
        if raw[:4] == b'\xe3\x0a\xf2\x23':
            f = fontfmt.parse(raw); assert fontfmt.build(f) == raw
            assert vi <= {chr(g[0]) for g in f['G']}
        elif b'gLua:RegisterClass' in raw:                 # script Lua đã vá (mod/lua/patch_lua.py)
            lua += 1
        else:
            assert locfmt.build(locfmt.parse(raw)) == raw
    else:
        assert D1[b[3]:b[3] + b[1]] == D0[a[3]:a[3] + a[1]]; same += 1
assert end == len(D1)
print('VERIFY OK: khối giữ nguyên %d, khối thay %d (Lua %d), dat %d byte' % (same, chg, lua, len(D1)))
