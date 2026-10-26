# -*- coding: utf-8 -*-
import sys,os,io,struct,zlib,csv
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
sys.path.insert(0,os.path.dirname(os.path.dirname(os.path.abspath(__file__)))+'/../img/scripts')
import numpy as np
from PIL import Image
from collections import Counter
from cont import *
import lib
from ann import mods

class Cv:  # tương thích lib.draw_text
    def __init__(s,rgba): s.rgba=rgba; s.orig=rgba.copy(); s.h,s.w=rgba.shape[:2]

def chunks(png):
    p=8;o=[]
    while p<len(png):
        n=struct.unpack('>i',png[p:p+4])[0]; o.append((png[p+4:p+8],png[p+8:p+8+n])); p+=12+n
    return o
def mkchunk(t,d): return struct.pack('>i',len(d))+t+d+struct.pack('>I',zlib.crc32(t+d)&0xffffffff)

class Png:
    """đọc PNG của container (P 8-bit hoặc RGBA 8-bit không interlace)"""
    def __init__(s,b):
        s.raw=b; s.ch=chunks(b)
        ih=s.ch[0][1]; s.w,s.h,bd,ct,_,_,il=struct.unpack('>iiBBBBB',ih)
        assert bd==8 and il==0 and ct in (3,6),(bd,ct,il)
        s.ct=ct
        s.pal=None; s.trns=None
        for t,d in s.ch:
            if t==b'PLTE': s.pal=[tuple(d[i:i+3]) for i in range(0,len(d),3)]
            if t==b'tRNS': s.trns=list(d)
        idat=b''.join(d for t,d in s.ch if t==b'IDAT')
        data=zlib.decompress(idat); bpp=1 if ct==3 else 4
        s.pix=unfilter(data,s.w,s.h,bpp)
        if ct==3:
            idx=s.pix.reshape(s.h,s.w); s.idx=idx
            tr=(s.trns or [])+[255]*(len(s.pal)-len(s.trns or []))
            lut=np.array([(r,g,b,tr[i]) for i,(r,g,b) in enumerate(s.pal)],np.uint8)
            s.rgba=lut[idx]
        else:
            s.rgba=s.pix.reshape(s.h,s.w,4).copy()
    def encode(s,rgba):
        """rgba mới -> bytes PNG; giữ cấu trúc chunk, độ dài PLTE"""
        out=[b'\x89PNG\r\n\x1a\n']
        if s.ct==6:
            rows=rgba.reshape(s.h,s.w*4)
        else:
            lut={}
            for i,(r,g,b) in enumerate(s.pal):
                a=(s.trns[i] if s.trns and i<len(s.trns) else 255)
                lut.setdefault((r,g,b,a),i)
            pa=np.array([(r,g,b,(s.trns[i] if s.trns and i<len(s.trns) else 255)) for i,(r,g,b) in enumerate(s.pal)],int)
            idx=s.idx.copy()
            diff=np.any(rgba!=s.rgba,axis=-1)
            for y,x in zip(*np.nonzero(diff)):
                k=tuple(int(v) for v in rgba[y,x])
                if k not in lut:
                    if k[3]==0:
                        z=[i for i in range(len(pa)) if pa[i][3]==0]
                        lut[k]=z[0] if z else int(((pa-np.array(k))**2).sum(1).argmin())
                    else:
                        lut[k]=int(((pa-np.array(k))**2).sum(1).argmin()); print('  ! màu ngoài bảng',k,'->',pa[lut[k]])
                idx[y,x]=lut[k]
            rows=idx
        raw=b''.join(b'\x00'+bytes(r) for r in rows.reshape(s.h,-1))
        done=False
        for t,d in s.ch:
            if t==b'IDAT':
                if not done: out.append(mkchunk(b'IDAT',zlib.compress(raw,9))); done=True
            else: out.append(mkchunk(t,d))
        return b''.join(out)

def unfilter(data,w,h,bpp):
    stride=w*bpp; out=np.zeros((h,stride),np.uint8); p=0; prev=np.zeros(stride,np.int32)
    for y in range(h):
        ft=data[p]; row=np.frombuffer(data[p+1:p+1+stride],np.uint8).astype(np.int32); p+=1+stride
        cur=np.zeros(stride,np.int32)
        if ft==0: cur=row
        elif ft==1:
            for i in range(stride): cur[i]=(row[i]+(cur[i-bpp] if i>=bpp else 0))&255
        elif ft==2: cur=(row+prev)&255
        elif ft==3:
            for i in range(stride): cur[i]=(row[i]+(((cur[i-bpp] if i>=bpp else 0)+prev[i])>>1))&255
        elif ft==4:
            for i in range(stride):
                a=cur[i-bpp] if i>=bpp else 0; b=prev[i]; c=prev[i-bpp] if i>=bpp else 0
                pp=a+b-c; pa,pb,pc=abs(pp-a),abs(pp-b),abs(pp-c)
                pr=a if pa<=pb and pa<=pc else (b if pb<=pc else c)
                cur[i]=(row[i]+pr)&255
        out[y]=cur; prev=cur
    return out

def auto_colors(cell):
    a=cell
    ink=a[...,3]==255
    h,w=ink.shape
    tr=~ink
    pad=np.pad(tr,1,constant_values=True)
    edge=ink&(pad[:-2,1:-1]|pad[2:,1:-1]|pad[1:-1,:-2]|pad[1:-1,2:])
    def mc(mask,excl=None):
        c=Counter(tuple(int(v) for v in p[:3]) for p in a[mask])
        if excl is not None: c.pop(excl,None)
        return c.most_common(1)[0][0] if c else None
    ol=mc(edge); body=mc(ink&~edge,ol) or mc(ink,ol) or (255,255,255)
    return body,ol

SIZES=[12,'13c','13s']
def fit_text(cands,sizes,box,outline):
    """thử lần lượt ứng viên x cỡ; trả (text,size,m)"""
    bx,by,bw,bh=box
    pad=2 if outline is not None else 0
    for t in cands:
        for s in sizes:
            m=lib.pt.layout(t,s,mode='metric',gap=1)
            px=m.pixels; xs=[p[0] for p in px]; ys=[p[1] for p in px]
            iw=max(xs)-min(xs)+1; ih=max(ys)-min(ys)+1
            if iw+pad<=bw and ih+pad<=bh: return t,s
    return None,None

def draw_text(I, text, size, box, body, outline, align='center', valign='center',
              diag=True, mode='metric', gap=1, dx=0, dy=0, clip=None, outline_alpha=255, strict_fit=True, clamp_y=True):
    """vẽ chữ 1 màu + viền 1px. box=(x,y,w,h). trả về (x,y,w,h) ink bbox (gồm viền)."""
    m = lib.pt.layout(text, size, mode=mode, gap=gap)
    f = m.font
    px = m.pixels
    xs = [p[0] for p in px]; ys = [p[1] for p in px]
    x0, x1, y0, y1 = min(xs), max(xs), min(ys), max(ys)
    bx, by, bw, bh = box
    iw = x1 - x0 + 1
    if align == 'center': ox = bx + (bw - iw) // 2
    elif align == 'left': ox = bx + (1 if outline is not None else 0)
    else: ox = bx + bw - iw - (1 if outline is not None else 0)
    if valign == 'center':
        # canh theo dải chữ hoa (cap band) để các nhãn cùng baseline
        cap_top = f.baseline - f.cap_height + 1
        oy = by + (bh - f.cap_height) // 2 - cap_top
    elif valign == 'top': oy = by - y0
    else: oy = by + bh - 1 - y1
    ox += dx - x0; oy += dy
    if clamp_y and outline is not None:   # nếu dấu/đuôi chạm mép box thì dịch cả dòng cho vừa
        pad = 1 if outline is not None else 0
        top = oy + y0 - pad; bot = oy + y1 + pad
        if top < by: oy += by - top
        elif bot > by + bh - 1: oy -= bot - (by + bh - 1)
    body_px = {(x + ox, y + oy) for (x, y) in px}
    out_px = set()
    dirs = [(-1,0),(1,0),(0,-1),(0,1)] + ([(-1,-1),(1,-1),(-1,1),(1,1)] if diag else [])
    for (x, y) in body_px:
        for a, b in dirs:
            p = (x + a, y + b)
            if p not in body_px: out_px.add(p)
    if outline is None: out_px=set()
    allp = body_px | out_px
    bb = (min(p[0] for p in allp), min(p[1] for p in allp), max(p[0] for p in allp), max(p[1] for p in allp))
    if strict_fit:
        assert bb[0] >= bx and bb[1] >= by and bb[2] < bx + bw and bb[3] < by + bh, \
            ('tràn ô', text, size, bb, box)
    cx0, cy0, cx1, cy1 = clip or (0, 0, I.w, I.h)
    for (x, y) in out_px:
        if cx0 <= x < cx1 and cy0 <= y < cy1 and outline is not None:
            I.rgba[y, x] = (*outline, outline_alpha)
    for (x, y) in body_px:
        if cx0 <= x < cx1 and cy0 <= y < cy1:
            I.rgba[y, x] = (*body, 255)
    return (bb[0], bb[1], bb[2] - bb[0] + 1, bb[3] - bb[1] + 1)

