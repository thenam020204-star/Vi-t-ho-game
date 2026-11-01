import struct,os,glob,collections
d=open('apk/assets/data.vfs.mp3','rb').read()
o=0;ent=[]
while o+8<=len(d):
    h,s=struct.unpack_from('<II',d,o); ent.append((h,o+8,s)); o+=8+s
def djb(s):
    h=5381
    for c in s.encode(): h=(h*33+c)&0xffffffff
    return h
H={e[0]:e for e in ent}
root='lgt5/j/res/'
files=[p for p in glob.glob(root+'**/*',recursive=True) if os.path.isfile(p)]
st=collections.Counter(); diff=collections.Counter(); named=set()
for p in files:
    rel=p[len(root):]; e=H.get(djb(rel))
    ext=rel.rsplit('.',1)[-1] if '.' in rel.split('/')[-1] else '(none)'
    if not e: st['miss']+=1; diff['miss.'+ext]+=1; continue
    named.add(e[0])
    if d[e[1]:e[1]+e[2]]==open(p,'rb').read(): st['same']+=1
    else: st['diff']+=1; diff[ext]+=1
print(st); print(diff.most_common())
print('vfs-only entries',len([e for e in ent if e[0] not in named]))
