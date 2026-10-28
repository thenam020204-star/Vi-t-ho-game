#!/usr/bin/env python3
"""T7 (chi de chup anh): doc jar da build, thay lenh cmd 51 (chan phim huong dan) bang lenh khong-op, cap vat pham (46) va mo cua hang (48) o doan huong dan dau.
  python3 scripts/t7_test_jar.py in.jar out.jar   -- KHONG phat hanh."""
import sys, struct, zipfile
src, dst = sys.argv[1], sys.argv[2]
zin = zipfile.ZipFile(src)
zout = zipfile.ZipFile(dst, 'w', zipfile.ZIP_DEFLATED)
ids = [7,17,18,19,20,22,28,29,0,1,2,3]
n51 = 0
for it in zin.infolist():
    data = zin.read(it.filename)
    if it.filename.startswith('pak/') and it.filename.endswith('.p'):
        d = data; p = 2; cnt = d[p]; p += 1
        out = bytearray(d[:3])
        for bi in range(cnt):
            l = struct.unpack('>H', d[p:p+2])[0]; name = d[p+2:p+2+l]; p += 2 + l
            ln = struct.unpack('>h', d[p:p+2])[0]; p += 2
            blob = d[p:p+ln]; p += ln
            cmds = blob.decode('utf-8').split('^')
            k = 0
            for i, c in enumerate(cmds):
                if c.startswith('51|'):
                    n51 += 1
                    if it.filename == 'pak/k1.p' and bi == 0 and 82 <= i <= 100:
                        cmds[i] = '48|0' if i == 82 else '46|%d|12' % ids[k-1]
                        k += 1
                    else:
                        cmds[i] = '7|tt=0'
            nb = '^'.join(cmds).encode('utf-8')
            out += struct.pack('>H', l) + name + struct.pack('>h', len(nb)) + nb
        data = bytes(out)
    zout.writestr(it, data)
zout.close()
print('51 cmds:', n51)
