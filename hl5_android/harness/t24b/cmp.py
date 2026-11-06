import json, sys
a, b = json.load(open(sys.argv[1])), json.load(open(sys.argv[2]))
n = min(len(a['md5']), len(b['md5'])); diff = [i for i in range(n) if a['md5'][i] != b['md5'][i]]
def seg(x, i0, i1): v = x['blk'][i0:i1]; return sum(v) / max(1, len(v)) / 1e6
print('khung', n, 'khác', len(diff), 'đầu tiên', diff[:5])
for nm, i0, i1 in (('khoi_dong', 0, 450), ('tieu_de', 450, 560), ('chon_lop', 560, 880), ('hoat_canh', 880, 1300), ('tat_ca', 0, n)):
    print('%-10s base %.2f M  new %.2f M  x%.2f' % (nm, seg(a, i0, i1), seg(b, i0, i1), seg(a, i0, i1) / max(1e-9, seg(b, i0, i1))))
