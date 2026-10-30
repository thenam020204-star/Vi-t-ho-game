import struct
def parse(d):
    assert d[:4]==b'\xe3\x0a\xf2\x23'
    ver,=struct.unpack('<I',d[4:8]); flag=d[8]
    n,=struct.unpack('<I',d[9:13]); H=list(struct.unpack('<%dI'%n,d[13:13+4*n]))
    p=13+4*n; pages=[]
    for h in H: pages.append(d[p:p+1024*h]); p+=1024*h
    cnt,=struct.unpack('<I',d[p:p+4]); p+=4
    G=[list(struct.unpack('<IbbbBHHHH',d[p+16*i:p+16*i+16])) for i in range(cnt)]
    p+=16*cnt; tail=d[p:]
    return dict(ver=ver,flag=flag,H=H,pages=pages,G=G,tail=tail)
def build(f):
    o=bytearray(b'\xe3\x0a\xf2\x23'+struct.pack('<I',f['ver'])+bytes([f['flag']]))
    o+=struct.pack('<I',len(f['H']))+struct.pack('<%dI'%len(f['H']),*f['H'])
    for pg in f['pages']: o+=pg
    o+=struct.pack('<I',len(f['G']))
    for g in f['G']: o+=struct.pack('<IbbbBHHHH',*g)
    o+=f['tail']; return bytes(o)
