"""Tách bản dịch đã ghép (dp merge) về từng phần + chuẩn hoá ký tự ngoài font f15c (cơ học, không dịch).

  python3 split_merged.py <final dich_tat_ca.tsv> <charset.txt> <thư mục dự án P>
Ra: P/pN/text_da_dich.tsv (resource, 7 cột như text_can_dich.tsv) và P/pN/class_da_dich.tsv,
    P/dich/chuan_hoa_bao_cao.txt (ký tự đã đổi, ký tự còn thiếu glyph).
"""
import collections, re, sys

FIX = [
    (re.compile(r'~+'), '...'), ('～', '...'),
    ('。', '.'), ('｡', '.'), ('，', ','), ('、', ','), ('！', '!'), ('？', '?'), ('：', ':'), ('；', ';'),
    ('“', '"'), ('”', '"'), ('‘', "'"), ('’', "'"), ('《', '"'), ('》', '"'), ('（', '('), ('）', ')'),
    ('—', '-'), ('–', '-'), ('·', ' '), ('　', ' '), ('©', '(c)'), ('×', 'x'), ('=', '-'),
]


# Mảnh câu game ghép với số trong code ("你正在激活" + n + "次"): dp ép khoảng trắng đầu/cuối theo gốc,
# nên thêm dấu cách ở phía nối với số. Khoá = chuỗi gốc; giá trị = (cách đầu, cách cuối).
EDGE = {
    '你正在激活': (0, 1), '你需要激活': (0, 1), '已激活': (0, 1), '次': (1, 0),
    '次,还需再激活': (1, 1), '次，你还需要激活': (1, 1),
    '你正在发送第': (0, 1), '你需要发送': (0, 1), '已发送': (0, 1), '扣费成功,已扣费': (0, 1),
    '元.': (1, 0), '元/条(不含通信费)': (1, 0), '元/条(不含通信费)\\n\\n按确定继续.': (1, 0),
    '元/条(不含通信费)\\n按确定继续。': (1, 0),
    '条,资费': (1, 1), '条,还需再发送': (1, 1), '条短信，你还需要发送': (1, 1), '条短信，资费': (1, 1),
    '消耗': (0, 1), '点体力,': (1, 1),
    '音乐': (0, 1),  # t.dat: "音乐" + "开"/"关" (tuỳ chọn nhạc lúc khởi động)
}


def edge(goc, s):
    a, b = EDGE.get(goc, (0, 0))
    if a and not s.startswith(' '):
        s = ' ' + s
    if b and not s.endswith(' '):
        s = s + ' '
    return s


def norm(s):
    for a, b in FIX:
        s = a.sub(b, s) if hasattr(a, 'sub') else s.replace(a, b)
    s = ''.join(chr(ord(c) - 0xFEE0) if 0xFF01 <= ord(c) <= 0xFF5E else c for c in s)  # chữ/số toàn khổ
    return s


def main():
    src, csf, P = sys.argv[1:4]
    cs = set(open(csf, encoding='utf-8').read()) | {'\\'}
    L = open(src, encoding='utf-8').read().split('\n')
    hdr = L[0].split('\t')
    out = collections.defaultdict(lambda: {'res': [], 'cls': []})
    changed = collections.Counter(); missing = collections.Counter(); empty = 0
    for ln in L[1:]:
        if not ln:
            continue
        c = ln.split('\t')
        d = c[4] if c[4] else c[3]          # chưa dịch / KHÔNG DỊCH -> gốc
        if not c[4]:
            empty += 1
        n = edge(c[3], norm(d)) if c[4] else norm(d)
        for ch in set(d) - set(n):
            changed[ch] += 1
        for ch in n:
            if ch not in cs and not (ch == '\\'):
                missing[ch] += 1
        c[4] = n
        part = c[7]
        kind = 'cls' if c[1].startswith('class/') else 'res'
        out[part][kind].append('\t'.join(c[:7]))
    h7 = '\t'.join(hdr[:7])
    for part, d in out.items():
        open('%s/p%s/text_da_dich.tsv' % (P, part), 'w', encoding='utf-8').write(h7 + '\n' + '\n'.join(d['res']) + '\n')
        open('%s/p%s/class_da_dich.tsv' % (P, part), 'w', encoding='utf-8').write(h7 + '\n' + '\n'.join(d['cls']) + '\n')
    rep = ['dòng dịch rỗng (giữ gốc): %d' % empty,
           'ký tự đã chuẩn hoá: ' + ' '.join('%s×%d' % kv for kv in changed.most_common()),
           'ký tự còn THIẾU glyph: ' + (' '.join('%r×%d' % kv for kv in missing.most_common(60)) or 'không')]
    open('%s/dich/chuan_hoa_bao_cao.txt' % P, 'w', encoding='utf-8').write('\n'.join(rep) + '\n')
    print('\n'.join(rep))


if __name__ == '__main__':
    main()
