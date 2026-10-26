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
        n = norm(d)
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
