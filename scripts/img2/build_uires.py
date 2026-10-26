# -*- coding: utf-8 -*-
import sys,os
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from eng import *
S12=[12,'13c','13s']; B=['15c',12,'13c']; G=[18,'15c']
# (offset png) -> list of dict
# m = module idx hoặc r = rect; t = ứng viên chuỗi; s = danh sách cỡ; ol = 'auto'|None
SPEC={
60706:[ # hệ thống tab + xác nhận
 dict(m=2,fit=(0,38,40,17),t=['Chọn'],s=['15c',12]),
 dict(m=3,t=['Về'],s=['15c',12]),
 dict(m=12,t=['Hệ thống'],s=B),dict(m=11,t=['Danh hiệu'],s=B),dict(m=10,t=['Tiềm năng'],s=B),
 dict(m=9,t=['Vật phẩm'],s=B),dict(m=7,t=['Chỉ số'],s=B),
],
76573:[
 dict(m=0,t=['Dược'],s=S12),dict(m=1,t=['Dược'],s=S12),
 dict(m=2,t=['Đồ'],s=S12),dict(m=3,t=['Đồ'],s=S12),
 dict(m=4,t=['Đặc'],s=S12),dict(m=5,t=['Đặc'],s=S12),
 dict(m=6,t=['Việc'],s=S12),dict(m=7,t=['Việc'],s=S12),
 dict(m=16,t=['Lv'],s=S12),dict(m=18,t=['EXP'],s=S12),dict(m=20,t=['HP'],s=S12),dict(m=22,t=['MP'],s=S12),
 dict(m=24,t=['May'],s=S12),dict(m=21,t=['ATK'],s=S12),dict(m=23,t=['DEF'],s=S12),dict(m=25,t=['Crit'],s=S12),
 dict(m=26,t=['Xu'],s=S12),dict(m=14,t=['Chiêu','Skill'],s=S12),dict(m=15,t=['Lực'],s=S12),
 dict(m=12,t=['Điểm TN'],s=S12),
 dict(m=37,t=['Mở'],s=S12),dict(m=38,t=['Ko'],s=S12),
 dict(m=27,t=['Đạt'],s=S12),dict(m=28,t=['Mô tả'],s=S12),dict(m=30,t=['Quà'],s=S12),
 dict(m=31,t=['Cá nhân'],s=S12),dict(m=32,t=['Gửi điểm','Gửi'],s=S12),
 dict(m=33,t=['Thành tựu'],s=S12),dict(m=34,t=['Đánh liền','Tự đánh'],s=S12),
 dict(m=35,t=['Nhạc'],s=S12),dict(m=36,t=['Về menu'],s=S12),dict(m=29,t=['Danh','Hiệu'],s=S12),
 dict(clear=(88,95,1,1)),
 dict(m=8,t=['Vũ'],s=S12,ol=None),dict(m=9,t=['Gi'],s=S12,ol=None),dict(m=10,t=['Hài','Hà','H'],s=S12,ol=None),dict(m=11,t=['Sức','Sứ','S'],s=S12,ol=None),
],
84345:[
 dict(m=5,t=['Bán'],s=S12),dict(m=4,t=['Bán'],s=S12),dict(m=7,t=['Khác'],s=S12),dict(m=6,t=['Khác'],s=S12),
 dict(m=11,t=['Giá'],s=S12,ol=None),dict(m=32,t=['Nâng'],s=S12),
 dict(m=0,t=['Thành tựu'],s=B),
 dict(r=(71,13,72,18),ms=[13,12],t=['Thống kê'],s=B),
 dict(m=17,t=['Combo max','Combo'],s=S12),dict(m=18,t=['Thời gian'],s=S12),dict(m=19,t=['Sát thương','Thiệt hại','Bị hại'],s=S12),
 dict(r=(49,44,24,13),ms=[20],t=['Điểm'],s=S12),dict(r=(73,44,25,13),ms=[30],t=['cuối'],s=S12),
 dict(m=21,t=['Nhận Xu'],s=S12),dict(m=22,t=['Nhận TN'],s=S12),dict(m=23,t=['Hạ gục'],s=S12),
 dict(m=25,t=['Hạng'],s=S12),dict(m=1,t=['Chọn'],s=S12),dict(m=2,t=['Về'],s=S12),
 dict(m=14,t=['Shop'],s=[18,'15c']),dict(m=24,t=['Lưu game'],s=B),
 dict(m=8,t=['Gửi'],s=S12,ol=None),dict(m=9,t=['Gửi'],s=S12,ol=None),dict(m=29,t=['Rút'],s=S12,ol=None),dict(m=10,t=['Rút'],s=S12,ol=None),
 dict(m=16,t=['Chọn tướng'],s=B),
 dict(m=31,t=['Độ khó'],s=S12),dict(m=26,t=['Dễ'],s=S12),dict(m=27,t=['Vừa'],s=S12),dict(m=28,t=['Khó'],s=S12),
 dict(m=15,lines=['Ải'],s=S12),
],
96468:[dict(m=0,t=['Chi tiết','Xem'],s=S12,ol=None),dict(m=1,t=['Dùng'],s=S12,ol=None),dict(m=2,t=['Bỏ'],s=S12,ol=None),dict(m=3,t=['Mặc'],s=S12,ol=None),
 dict(m=4,t=['Nâng'],s=S12,ol=None),dict(m=5,t=['Mở'],s=S12,ol=None),dict(m=6,t=['Luyện','Tập'],s=S12,ol=None),dict(m=7,t=['Quên'],s=S12,ol=None)],
97757:[dict(m=0,t=['Có'],s=S12),dict(m=1,t=['(c)'],s=S12),dict(m=3,t=['Đổi'],s=G)],
98480:[dict(m=0,r=(2,0,55,15),fit=(2,0,55,14),t=['Mua đồ'],s=B),dict(m=1,t=['Bán đồ'],s=B),dict(m=2,t=['Đổi liệu'],s=B),dict(m=3,t=['Gửi đồ'],s=B),dict(m=4,t=['Rút đồ'],s=B),dict(m=5,t=['Trợ giúp'],s=B)],
99549:[dict(m=0,t=['Chơi lại ải','Chơi lại'],s=S12+B),dict(m=1,t=['Ải kế tiếp','Qua ải sau','Ải sau'],s=S12+B),dict(m=2,t=['Về màn ải'],s=S12+B)],
100298:[dict(m=0,t=['Kho'],s=G),dict(m=2,t=['Đồ'],s=S12),dict(m=1,t=['Đồ'],s=S12),dict(m=4,t=['Vũ'],s=S12),dict(m=3,t=['Vũ'],s=S12),
 dict(m=6,t=['Giáp'],s=S12),dict(m=5,t=['Giáp'],s=S12),dict(m=7,t=['Mua'],s=S12,ol=None),dict(m=8,t=['Bán'],s=S12,ol=None)],
105157:[dict(m=0,t=['Hồi sinh'],s=S12)],
}

def hx(c): return '-' if c is None else '#%02x%02x%02x'%tuple(c)
def run(container,off,spec,rows):
    c=parse(open(JX+container+'.bin','rb').read())
    b=[r for r in c.recs if r.png_off==off][0]; idx=c.recs.index(b)
    P=Png(b.png); M=mods(b)
    cv=Cv(P.rgba.copy()); changed=[]
    for sp in spec:
        if 'clear' in sp:
            x,y,w,h=sp['clear']; cv.rgba[y:y+h,x:x+w]=(0,0,0,0); changed.append(sp['clear']); continue
        r=sp.get('r') or M[sp['m']]
        ms=sp.get('ms') or ([sp['m']] if 'm' in sp else [])
        cellsrc=M[ms[0]] if ms else r
        x,y,w,h=cellsrc
        cell=P.rgba[y:y+h,x:x+w]
        flat='ol' in sp and sp['ol'] is None
        alpha=255
        if flat:
            c2=Counter(tuple(int(v) for v in p) for p in cell.reshape(-1,4) if p[3]>0)
            (br,bg,bb,alpha),_=c2.most_common(1)[0]; body=(br,bg,bb); ol=None
        else:
            body,ol=auto_colors(cell)
        body=sp.get('body',body); ol=sp.get('olc',ol) if not flat else None
        x,y,w,h=r
        fx,fy,fw,fh=sp.get('fit',r)
        cv.rgba[y:y+h,x:x+w]=(0,0,0,0)
        before=cv.rgba.copy()
        if 'lines' in sp:
            n=len(sp['lines']); lh=h//n
            for k,t in enumerate(sp['lines']):
                bx=(x,y+k*lh,w,lh)
                tt,ss=fit_text([t],sp['s'],bx,ol); assert tt,('không vừa',t,bx)
                draw_text(cv,tt,ss,bx,body,ol)
            txt='/'.join(sp['lines']); sz=ss
        else:
            tt,ss=fit_text(sp['t'],sp['s'],(fx,fy,fw,fh),ol)
            if not tt:
                print('  KHÔNG VỪA',off,sp.get('m'),sp['t'],r); cv.rgba[y:y+h,x:x+w]=before[y:y+h,x:x+w]*0+P.rgba[y:y+h,x:x+w]; continue
            draw_text(cv,tt,ss,(fx,fy,fw,fh),body,ol); txt=tt; sz=ss
        if alpha<255:
            d=np.any(cv.rgba!=before,axis=-1); cv.rgba[d,3]=alpha
        changed.append(r)
        rows.append([container,off,idx,'%d,%d,%d,%d'%r,'(module %s)'%(sp.get('ms') or sp.get('m')),txt,str(sz),hx(body),hx(ol)])
    return c,idx,P,cv,changed
