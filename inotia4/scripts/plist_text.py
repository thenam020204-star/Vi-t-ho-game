#!/usr/bin/env python3
"""Text UI trong res/xml/*.xml (plist: <key>K</key><array><string>EN</string><string>KO</string>...).

    plist_text.py extract <res/xml> <out.tsv>     # file, key, EN (cot vi de trong)
    plist_text.py apply   <res/xml> <in.tsv>      # thay MOI <string> trong array bang ban vi (moi ngon ngu)

Chi sua noi dung <string> trong <array> cua key co trong tsv; khong dung cau truc XML khac.
Ban dich trong tsv viet dang chua escape (&N giu nguyen); apply tu escape & < > cho XML.
"""
import html, re, sys

FILES = ['config', 'hive', 'clause', 'hubjoin']
ARR = re.compile(r'(<key>([^<]*)</key>\s*<array>)(.*?)(</array>)', re.S)
STR = re.compile(r'(<string>)(.*?)(</string>)', re.S)


def esc(t):
    return t.replace('&', '&amp;').replace('<', '&lt;').replace('>', '&gt;')


def tsv_cell(t):
    return t.replace('\\', '\\\\').replace('\t', '\\t').replace('\n', '\\n')


def un_cell(t):
    return re.sub(r'\\(.)', lambda m: {'t': '\t', 'n': '\n', '\\': '\\'}.get(m.group(1), '\\' + m.group(1)), t)


def extract(d, out):
    rows = ['file\tkey\ten\tvi']
    for f in FILES:
        s = open(f'{d}/{f}.xml', encoding='utf-8').read()
        for _, k, body, _ in ARR.findall(s):
            en = html.unescape(STR.findall(body)[0][1])
            rows.append(f'{f}\t{k}\t{tsv_cell(en)}\t')
    open(out, 'w', encoding='utf-8').write('\n'.join(rows) + '\n')
    print(f'{len(rows) - 1} muc -> {out}')


def apply(d, tsv):
    tr = {}
    for l in open(tsv, encoding='utf-8').read().splitlines()[1:]:
        f, k, en, vi = l.split('\t')
        if not vi: raise SystemExit(f'chua dich: {f} {k}')
        for c in re.findall(r'%[sd]|&[NP]', en):
            if en.count(c) != vi.count(c): raise SystemExit(f'ma lech {c}: {f} {k}')
        tr[(f, k)] = un_cell(vi)
    n = 0
    for f in FILES:
        p = f'{d}/{f}.xml'
        s = open(p, encoding='utf-8').read()
        def rep(m):
            nonlocal n
            v = tr.get((f, m.group(2)))
            if v is None: return m.group(0)
            body, c = STR.subn(lambda x: x.group(1) + esc(v) + x.group(3), m.group(3))
            n += c
            return m.group(1) + body + m.group(4)
        s2 = ARR.sub(rep, s)
        open(p, 'w', encoding='utf-8').write(s2)
    print(f'plist: thay {n} <string> ({len(tr)} key)')


if __name__ == '__main__':
    {'extract': extract, 'apply': apply}[sys.argv[1]](*sys.argv[2:4])
