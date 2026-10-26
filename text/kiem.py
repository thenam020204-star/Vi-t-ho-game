import csv,re,sys,collections
W='/tmp/claude-0/-home-user-Vi-t-ho-game/9a067d44-fb40-5db7-b229-544b694f82f4/scratchpad'
sys.path.insert(0,W+'/work/tools/pixeltext-kit')
from pixeltext.font import Font
f=Font.load(12)
src,dst=sys.argv[1],sys.argv[2]
def rd(p):
    return list(csv.reader(open(p,encoding='utf8'),delimiter='\t',quoting=csv.QUOTE_NONE,escapechar='\\'))
G=rd(src);D=rd(dst);h=G[0]
assert len(G)==len(D)
han=re.compile(r'[\u4e00-\u9fff]')
err=[];seen=collections.defaultdict(set)
for g,d in zip(G[1:],D[1:]):
    id_,fl,key,go,vi=g[0],g[1],g[2],g[3],d[4]
    note=g[5] if len(g)>5 else ''
    def e(m): err.append('%s: %s | %s'%(id_,m,vi[:50]))
    if not vi.strip(): e('trong'); continue
    if han.search(vi): e('con Han')
    for c in '|&':
        if go.count(c)!=vi.count(c): e('so '+c)
    if 'FONT HE THONG' not in note and go.count('：')!=vi.count('：'): e('so ：')
    if re.findall(r'\{c:\d+\}|\{/\}',go)!=re.findall(r'\{c:\d+\}|\{/\}',vi): e('marker')
    if go.startswith('#')!=vi.startswith('#'): e('#')
    if fl=='script.bin' and '：' in go and not re.match(r'^(\{c:\d+\})?[^：]+(\{/\})?：',vi): e('ten：')
    if fl=='string.bin' and re.match(r'^\d\d',go):
        if vi[:2]!=go[:2]: e('2 chu so')
        if not vi.endswith('.'): e('ky tu cuoi')
    if fl=='l1.bin' or ('S' in go and 'P' in go and 'N' in go and fl.endswith('.class')==False and False):
        for ch in 'SNP':
            if go.count(ch)!=vi.count(ch): e('placeholder '+ch)
    if 'FONT HE THONG' in note:
        if re.search(r'[^\x20-\x7e]',vi): e('font he thong phai ASCII')
    if g[1].endswith('.class') and go[:1]==' ' and vi[:1]!=' ' : e('khoang dau')
    if g[1].endswith('.class') and go[-1:]==' ' and vi[-1:]!=' ': e('khoang cuoi')
    allowed=set(c for c in go if ord(c)>127 and not han.match(c))
    for c in f.missing(vi):
        if c not in allowed and not han.match(c): e('glyph thieu %r'%c)
    for m in re.findall(r'[A-Z]',go):
        pass
    if 'FONT HE THONG' not in note: seen[go].add(vi)
for go,s in seen.items():
    if len(s)>1: err.append('LAP khac nhau: %s -> %s'%(go[:30],s))
print('\n'.join(err) if err else 'OK',len(err))
