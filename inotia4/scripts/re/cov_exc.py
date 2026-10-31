#!/usr/bin/env python3
"""Kiem tra bao phu ngoai le cho lenh 'mang' trong cay smali (apktool).

    python3 -I scripts/re/cov_exc.py <dec_dir> <goc1> [goc2 ...]      vd: ... /opt/work/dec4 com/com2us com/hive

Voi moi lenh invoke mo ket noi (HttpURLConnection/URLConnection/InetAddress/Socket...), xem co nam trong try co
.catch SecurityException | RuntimeException | Exception | Throwable hay khong. Neu chi co .catchall thi theo handler toi
lenh `throw` roi kiem lenh do (de quy); handler ket thuc bang return (nuot ngoai le, vd. kotlin runCatching) la an toan.
In ra cac lenh KHONG duoc bao phu. Khong xet WebView (loadUrl khong nem SecurityException). Xap xi: chi di theo goto,
bo qua nhanh if-*; ket qua 'khong bao phu' can doc tay.
"""
import os, re, sys
dec=sys.argv[1]; roots=sys.argv[2:]
DANGER=re.compile(r'invoke-\S+ \{[^}]*\}, (Ljava/net/(?:URL;->(?:openStream|getContent)|URLConnection;->(?:connect|getInputStream|getOutputStream|getHeaderField\w*|getHeaderFields|getContentLength\w*|getContentType|getContentEncoding|getResponseCode|getContent)|HttpURLConnection;->(?:connect|getResponseCode|getResponseMessage|getErrorStream|getInputStream|getOutputStream|getHeaderField\w*|getHeaderFields|getContentLength\w*|getContentType|getContentEncoding)|InetAddress;->(?:getByName|getAllByName|isReachable|getLocalHost)|Socket;->(?:<init>|connect)|DatagramSocket;->(?:<init>|connect|send|receive)|ServerSocket;->(?:<init>|bind|accept))|Ljavax/net/ssl/HttpsURLConnection;->(?:connect|getResponseCode|getInputStream|getOutputStream))\S*')
OK_TYPES={'Ljava/lang/SecurityException;','Ljava/lang/RuntimeException;','Ljava/lang/Exception;','Ljava/lang/Throwable;'}
def parse(body):
    labels={}; ins=[]
    for ln in body.split('\n'):
        s=ln.strip()
        if not s or s.startswith('#'): continue
        if s.startswith(':'):
            labels[s]=len(ins); continue
        if s.startswith('.') and not s.startswith('.catch'): continue
        ins.append(s)
    catches=[]
    for s in ins:
        mm=re.match(r'\.catch(all)? (?:(\S+) )?\{(:\w+) \.\. (:\w+)\} (:\w+)$',s)
        if mm:
            t='ALL' if mm.group(1) else mm.group(2)
            catches.append((t,labels[mm.group(3)],labels[mm.group(4)],labels[mm.group(5)]))
    return labels,ins,catches
def covered(i,ins,labels,catches,seen=None):
    seen=seen or set()
    cov=[c for c in catches if c[1]<=i<c[2]]
    if any(c[0] in OK_TYPES for c in cov): return True
    for c in cov:
        if c[0]=='ALL':
            h=c[3]
            if h in seen: continue
            seen2=seen|{h}
            # tim cac throw tu handler
            j=h; steps=0; res=None; visited=set()
            while j<len(ins) and steps<400:
                if j in visited: break
                visited.add(j); steps+=1
                s=ins[j]
                if s.startswith('throw '):
                    res=covered(j,ins,labels,catches,seen2)
                    break
                if s.startswith('return'): res=True; break
                m=re.match(r'goto(?:/\d+)? (:\w+)',s)
                if m: j=labels[m.group(1)]; continue
                j+=1
            if res: return True
    return False
meth_re=re.compile(r'^\.method ([^\n]*)\n(.*?)^\.end method$',re.S|re.M)
tot=0; bad=[]
for d in sorted(os.listdir(dec)):
    if not d.startswith('smali'): continue
    for r in roots:
        for root,_,fs in os.walk(os.path.join(dec,d,r)):
            for f in fs:
                if not f.endswith('.smali'): continue
                p=os.path.join(root,f); cls=os.path.relpath(p,os.path.join(dec,d))[:-6]
                src=open(p,encoding='utf-8').read()
                for m in meth_re.finditer(src):
                    hdr,body=m.groups()
                    if 'Ljava/net/' not in body and 'Ljavax/net' not in body: continue
                    labels,ins,catches=parse(body)
                    for i,s in enumerate(ins):
                        if DANGER.search(s):
                            tot+=1
                            if not covered(i,ins,labels,catches):
                                bad.append((cls,hdr.split()[-1],s[:110],sorted(set(c[0] for c in catches if c[1]<=i<c[2]))))
print('tong lenh mang:',tot,'khong bao phu SecurityException:',len(bad))
for b in bad: print(' ',b)
