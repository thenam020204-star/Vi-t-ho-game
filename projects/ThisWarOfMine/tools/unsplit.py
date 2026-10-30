import struct,sys,zlib,os
d=sys.argv[1]; out=sys.argv[2]
parts=[open(os.path.join(d,n),'rb').read() for n in ['Download.z01','Download.z02','Download.z03','Download.zip']]
base=[0];
for p in parts[:-1]: base.append(base[-1]+len(p))
allb=b''.join(parts); last=parts[-1]
e=last.rfind(b'PK\x05\x06')
(sig,disk,cddisk,n1,n,cdsize,cdoff,cl)=struct.unpack('<IHHHHIIH',last[e:e+22])
print('eocd disk',disk,'cddisk',cddisk,'n',n,'cdoff',cdoff,'cdsize',cdsize)
p=base[cddisk]+cdoff
for i in range(n):
    f=struct.unpack('<IHHHHHHIIIHHHHHII',allb[p:p+46])
    meth,crc,cs,us,fl,el,cml,dk,lho=f[4],f[7],f[8],f[9],f[10],f[11],f[12],f[13],f[16]
    name=allb[p+46:p+46+fl].decode()
    extra=allb[p+46+fl:p+46+fl+el]
    print(name,meth,cs,us,dk,lho,hex(crc),extra.hex())
    a=base[dk]+lho
    h=struct.unpack('<IHHHHHIIIHH',allb[a:a+30])
    ds=a+30+h[9]+h[10]
    data=allb[ds:ds+cs]
    raw=zlib.decompress(data,-15) if meth==8 else data
    assert len(raw)==us and zlib.crc32(raw)==crc, 'bad '+name
    open(os.path.join(out,name),'wb').write(raw)
    p+=46+fl+el+cml
print('ok')
