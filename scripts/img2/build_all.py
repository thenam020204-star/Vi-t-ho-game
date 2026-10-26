# -*- coding: utf-8 -*-
import sys,os,glob,csv
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from build_uires import SPEC,run as run_ui,hx
from run_anim import *
OUT=W+'/img2'
# 0. repack nguyên bản phải giống từng byte
orig={n:open(JX+n+'.bin','rb').read() for n in ('uires','animation')}
cont={n:parse(orig[n]) for n in orig}
for n in orig:
    assert build(cont[n])==orig[n],'repack gốc khác byte'; print(n,'repack nguyên bản == gốc sha1',sha1(orig[n]))
new={'uires':{}, 'animation':{}}   # idx -> bytes
rows=[]; boxes={}
for off,spec in SPEC.items():
    c,idx,P,cv,ch=run_ui('uires',off,spec,rows)
    new['uires'][idx]=P.encode(cv.rgba); boxes[('uires',idx)]=ch
# animation: hiệu ứng nâu
BR=[(301320,'Hạ gục',None),(351240,'Liên trảm',None),(358141,'Chí mạng',None),(374879,'Lên cấp',(110,200,110))]
for off,t,b in BR:
    idx,P,out,ch,info=brush(off,t,18,body=b)
    new['animation'][idx]=P.encode(out); boxes[('animation',idx)]=ch
    rows.append(['animation',off,idx,'%d,%d,%d,%d'%ch[0],'',t,'18',hx(info[2]),hx(info[3])])
# 52 + 53
idx52,P52=load_png('animation',404761); idx53,P53=load_png('animation',414838)
a=P52.rgba.copy(); amax=int(a[...,3].max()); assert amax==77
def lab(rect,text,body,outline,outer):
    x,y,w,h=rect
    a[y:y+h,x:x+w]=(0,0,0,0)
    px=text_pixels(text,18)
    sub=a[y:y+h,x:x+w]
    paint(sub,px,w//2,h//2,body,outline,amax,outer,k=3)
lab((0,0,110,47),'Đạt danh hiệu',(110,185,190),(18,12,12),(212,255,251))
lab((0,47,115,47),'Đạt thành tựu',(225,140,115),(18,12,12),(248,219,255))
rows.append(['animation',404761,idx52,'0,0,110,47','module1','Đạt danh hiệu','18','#6eb9be','#120c0c+viền ngoài #d4fffb'])
rows.append(['animation',404761,idx52,'0,47,115,47','module0','Đạt thành tựu','18','#e18c73','#120c0c+viền ngoài #f8dbff'])
new['animation'][idx52]=P52.encode(a)
m=np.zeros_like(P53.rgba); m[...,:3]=255; m[...,3]=np.where(a[...,3]>0,255,0)
new['animation'][idx53]=P53.encode(m)
rows.append(['animation',414838,idx53,'0,0,115,94','mặt nạ','(silhouette alpha>0 của ảnh 52)','-','#ffffff','-'])
boxes[('animation',idx52)]=[(0,0,115,94)]; boxes[('animation',idx53)]=[(0,0,115,94)]
# 1. dựng container
os.makedirs(OUT+'/viet/bin',exist_ok=True)
for n in orig:
    c=cont[n]
    for i,b in enumerate(c.recs):
        if i in new[n]: b.png=new[n][i]
    data=build(c); open(OUT+'/viet/bin/%s.bin'%n,'wb').write(data)
    # parse lại
    c2=parse(data); c0=cont[n]; o=parse(orig[n])
    assert c2.nbt==o.nbt and c2.nci==o.nci and c2.ci_blob==o.ci_blob
    for i,(r2,r0) in enumerate(zip(c2.recs,o.recs)):
        if i in new[n]:
            assert r2.head[:-4]==r0.head[:-4] and r2.png==new[n][i]
            p0,p2=Png(r0.png),Png(r2.png); assert (p0.w,p0.h,p0.ct)==(p2.w,p2.h,p2.ct)
            if p0.ct==3:
                assert len(p0.pal)==len(p2.pal) and r2.h==r0.h and len(r2.png)>0
                # bảng palette trong container (g*h byte) phải khớp PLTE mới
                tbl=r2.head[-4-r2.g*r2.h:-4]; plte=[d for t,d in p2.ch if t==b'PLTE'][0]
                assert tbl==plte==[d for t,d in p0.ch if t==b'PLTE'][0], 'palette đổi'
        else:
            assert r2.png==r0.png and r2.head==r0.head
    print(n,'mới',len(data),'bytes (gốc',len(orig[n]),') parse lại OK, PNG khác giống hệt byte')
# 2. ảnh ra png_viet, kiểm tra ngoài box
for n in orig:
    o=parse(orig[n]); c2=parse(open(OUT+'/viet/bin/%s.bin'%n,'rb').read())
    for i in sorted(new[n]):
        r0=o.recs[i]; r2=c2.recs[i]
        open(OUT+'/png_viet/%s_%02d_off%d.png'%(n,i,r0.png_off),'wb').write(r2.png)
        p0,p2=Png(r0.png),Png(r2.png)
        d=np.any(p0.rgba!=p2.rgba,axis=-1); ins=np.zeros_like(d)
        for x,y,w,h in boxes[(n,i)]: ins[y:y+h,x:x+w]=True
        print(n,i,'off',r0.png_off,'%dx%d ct%d'%(p2.w,p2.h,p2.ct),'đổi',int(d.sum()),'ngoài box',int((d&~ins).sum()))
        assert not (d&~ins).any()
# 3. pixeltext check
bad=0
for r in rows:
    txt=r[5].replace('/','')
    if not txt or txt.startswith('('): continue
    sz=r[6]; sz=int(sz) if sz.isdigit() else sz
    f=lib.pt.Font.load(sz); ms=f.missing(txt); am=f.ambiguous_in(txt)
    if ms or am: print('CHECK LỖI',r[5],ms,am); bad+=1
print('pixeltext check:',bad,'lỗi')
# 4. tsv
with open(OUT+'/anh_nhung.tsv','w',newline='',encoding='utf-8') as fh:
    w=csv.writer(fh,delimiter='\t'); w.writerow(['container','offset_goc','index','box','module','dich','font','mau_than','mau_vien'])
    w.writerows(rows)
