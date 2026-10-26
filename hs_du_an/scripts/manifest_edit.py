"""Ghi đè thuộc tính manifest: manifest_edit.py <MANIFEST.MF> <override.txt>
Hiểu dòng gập (dòng tiếp nối bắt đầu bằng dấu cách), ghi lại với dòng ≤72 byte theo spec JAR."""
import sys
mf, ov = sys.argv[1], sys.argv[2]
raw = open(mf, 'rb').read().decode('utf-8').replace('\r\n', '\n')
keys, vals = [], {}
for line in raw.split('\n'):
    if line.startswith(' ') and keys:
        vals[keys[-1]] += line[1:]
    elif ': ' in line:
        k, v = line.split(': ', 1); keys.append(k); vals[k] = v
for line in open(ov, encoding='utf-8').read().split('\n'):
    if ': ' in line:
        k, v = line.split(': ', 1)
        if k not in vals:
            keys.append(k)
        vals[k] = v
order = ['Manifest-Version'] + [k for k in keys if k != 'Manifest-Version']
out = b''
for k in order:
    b = ('%s: %s' % (k, vals[k])).encode('utf-8')
    first, rest = b[:72], b[72:]
    out += first + b'\r\n'
    while rest:
        # không cắt giữa ký tự UTF-8
        cut = 71
        while cut < len(rest) and (rest[cut] & 0xC0) == 0x80:
            cut -= 1
        out += b' ' + rest[:cut] + b'\r\n'; rest = rest[cut:]
open(mf, 'wb').write(out + b'\r\n')
