#!/usr/bin/env python3 -I
"""Va anh UI co chu (T07): xoa chu goc, ve nhan tieng Viet bang pixeltext font 13c, mot mau, khong vien.
Chay tu goc repo (da `source work/tools/env.sh`):  python3 -I scripts/img_patch.py [ten_anh ...]
Dau ra: work/build/img_overlay/<ten>.xng|.png, logs/img/truoc_sau_<ten>.png, logs/img/img_patch.log, logs/img/chosen.tsv
"""
import sys, os, io, json
HERE = os.path.dirname(os.path.abspath(__file__)); ROOT = os.path.dirname(HERE)
sys.path.insert(0, HERE)
sys.path.insert(0, os.path.join(ROOT, 'work', 'tools', 'pixeltext-kit'))
import xng_codec as X
import pixeltext as pt
from PIL import Image

SRC = os.path.join(ROOT, 'work', 'k790_raw')
OUT = os.path.join(ROOT, 'work', 'build', 'img_overlay')
LOGD = os.path.join(ROOT, 'logs', 'img')
SIZE = '13c'

def rows(x, y0, w, h, n, labels, fill, align='center', dx=0):
    return [dict(box=(x, y0 + i*h, w, h), cand=labels[i], fill=fill if isinstance(fill, str) else fill[i], align=align) for i in range(n)]

SPEC = {
 'i_01': rows(0, 0, 29, 13, 3, [['Cấp:'], ['Hiệu:'], ['Môn:']], '#743b2f', 'right'),
 'i_02': rows(0, 0, 25, 13, 4, [['ATK'], ['DEF'], ['Bạo'], ['Né']], '#ffe522'),
 'i_03': rows(0, 0, 27, 15, 6, [['Chỉ số', 'Số'], ['Rèn'], ['Kỹ năng', 'Kỹ'], ['Trang bị', 'Giáp'], ['Nhiệm vụ', 'N.vụ', 'Việc'], ['Bản đồ', 'B.đồ', 'Map']], '#ffe522'),
 'i_05': [dict(box=(0,0,19,20), cand=['Tà'], fill='#ffffda'), dict(box=(19,0,19,20), cand=['Chính', 'Hiệp', 'Hảo'], fill='#ffffda'),
          dict(box=(0,20,19,20), cand=['đạo'], fill='#ffffda'), dict(box=(19,20,19,20), cand=['đạo'], fill='#ffffda')],
 'i_06': rows(0, 0, 25, 18, 3, [['Ngụy'], ['Thục'], ['Ngô']], '#f8f7d3'),
 'i_07': rows(0, 0, 13, 25, 3, [['Ác', 'Dữ'], ['Ái', 'Mật'], ['Hòa', 'Tốt']], ['#dbd7d6', '#ffe821', '#ff839f']),
 'i_18': rows(0, 0, 27, 14, 5, [['HP'], ['MP'], ['EXP'], ['Tình cảm', 'Tình'], ['Tin cậy', 'Tin']], '#ffffda'),
 'i_19': rows(1, 0, 53, 13, 8, [['Tâm Hồn Kiếm', 'Tâm Hồn'], ['Ỷ Thiên Kiếm', 'Ỷ Thiên'], ['Chủ động'], ['Bị động'], ['Nhiệm vụ'], ['Nhiệm vụ'], ['Thành tựu'], ['Danh vọng']], '#ffffda')
         + rows(56, 0, 53, 13, 8, [['Tâm Hồn Kiếm', 'Tâm Hồn'], ['Ỷ Thiên Kiếm', 'Ỷ Thiên'], ['Chủ động'], ['Bị động'], ['Nhiệm vụ'], ['Nhiệm vụ'], ['Thành tựu'], ['Danh vọng']], '#67573b'),
 'i_30': [dict(box=(0,0,45,18), cand=['Diệp Vấn Thiên', 'Vấn Thiên', 'Thiên'], fill='#fefefe')],
 'i_31': rows(0, 0, 38, 21, 2, [['Tiểu Duy', 'Duy'], ['Anh Ninh', 'Ninh']], '#f8fefe'),
 'i_38': [dict(box=(0,0,49,13), cand=['Thắng lợi'], fill='#ffe522')],
 'i_39': [dict(box=(0,0,49,13), cand=['Thất bại'], fill='#aaa477')],
 'i_55': [dict(box=(0,0,49,13), cand=['Hoàn thành'], fill='#ffe522')],
 'i_40': rows(0, 0, 47, 11, 10, [['Tép Riu'], ['Tà Khí'], ['Chính Khí'], ['Đồng Bì', 'Thiết Cốt'], ['Thái Bảo'], ['Ma Đầu'], ['Thánh Tướng', 'Thánh'], ['Hổ Uy', 'Hổ Oai'], ['Thiên Long'], ['Thiên Hành']], '#ffffff', 'right'),
 'i_41': [dict(box=(2,2,23,11), cand=['Cửa hàng', 'Tiệm', 'Shop'], fill='#ffe522')],
 'i_42': rows(0, 0, 23, 11, 3, [['Mua'], ['Bán'], ['Rời']], '#ffffff'),
 'i_96': [dict(box=(0,0,35,19), cand=['Né tránh', 'Tránh', 'Né'], fill='#f10e42')],
 'i_cost': rows(6, 0, 47, 11, 4, [['Gói đặc dược', 'Gói thuốc'], ['Hồn phục thù', 'Phục thù'], ['Gói đặc dược', 'Gói thuốc'], ['Hồn phục thù', 'Phục thù']], ['#8d7d5b', '#8d7d5b', '#ffffda', '#ffffda']),
 'c_f': rows(0, 0, 61, 17, 9, [['Trò chơi mới'], ['Tiếp tục'], ['Hướng dẫn'], ['Giới thiệu'], ['Âm thanh', 'Âm nhạc'], ['Thoát game'], ['Khu đối tác', 'Đối tác'], ['Game Sohu'], ['Game khác']],
             ['#000000']*8 + ['#fe1a38']),
}
# pad ngang 0; truoc khi ve phai chay `pixeltext check` cho tung chuoi

def hexrgb(h):
    h = h.lstrip('#'); return tuple(int(h[i:i+2], 16) for i in (0, 2, 4)) + (255,)

def ink(text, mode):
    m = pt.layout(text, SIZE, mode, 1, None, 'left', 0, 1)
    b = m.ink_bbox(); return b[2]-b[0]+1, b[3]-b[1]+1

def choose(cell):
    w, h = cell['box'][2], cell['box'][3]
    for t in cell['cand']:
        for mode in ('metric', 'tight'):
            iw, ih = ink(t, mode)
            if iw <= w and ih <= h:
                return t, mode, iw, ih
    t = cell['cand'][-1]
    return t, 'tight', *ink(t, 'tight')

def process(name, log, chosen):
    d = open(os.path.join(SRC, name + '.xng'), 'rb').read()
    png, meta = X.decode(d)
    orig = Image.open(io.BytesIO(png))
    mode0 = orig.mode
    rgba = orig.convert('RGBA')
    pal = None
    if mode0 == 'P':
        raw = orig.getpalette(); tr = orig.info.get('transparency')
        n = len(raw)//3
        pal = {}
        for i in range(n):
            a = 255
            if isinstance(tr, bytes) and i < len(tr): a = tr[i]
            elif isinstance(tr, int) and i == tr: a = 0
            pal.setdefault((raw[3*i], raw[3*i+1], raw[3*i+2], a), i)
    img = rgba.copy()
    for cell in SPEC[name]:
        x, y, w, h = cell['box']
        t, mode, iw, ih = choose(cell)
        chk = pt.Font.load(SIZE).missing(t)
        if chk: raise SystemExit('thieu glyph %s %r' % (chk, t))
        px = img.load()
        for yy in range(y, y+h):
            for xx in range(x, x+w): px[xx, yy] = (0, 0, 0, 0)
        info = pt.draw(img, (x, y, w, h), t, size=SIZE, mode=mode, align=cell.get('align', 'center'), valign='center',
                       fill=hexrgb(cell['fill']), outline=None, shadow=None, trim_v=(h < 13), clip=True)
        flag = ''
        if info['overflow_x'] or info['overflow_y']: flag = ' TRAN(%d,%d)' % (info['overflow_x'], info['overflow_y'])
        if t != cell['cand'][0]: flag += ' RUTGON'
        line = '%s\t%s\t%d,%d,%d,%d\t%s\t%s\t%d\t%s%s' % (name, cell['cand'][0], x, y, w, h, t, mode, iw, cell['fill'], flag)
        log.append(line); chosen.append((name, cell['box'], cell['cand'][0], t, cell['fill'], flag.strip()))
    # ve lai ve palette
    if pal is not None:
        out = Image.new('P', img.size)
        out.putpalette(orig.getpalette())
        op = out.load(); ip = img.load(); tidx = None
        for k, v in pal.items():
            if k[3] == 0: tidx = v; break
        for yy in range(img.height):
            for xx in range(img.width):
                c = ip[xx, yy]
                if c[3] == 0:
                    op[xx, yy] = tidx
                else:
                    c = (c[0], c[1], c[2], 255)
                    if c not in pal: raise SystemExit('mau ngoai palette %s %s' % (name, c))
                    op[xx, yy] = pal[c]
        tr = orig.info.get('transparency')
        buf = io.BytesIO()
        out.save(buf, 'PNG', transparency=tr, bits=8)
    else:
        buf = io.BytesIO(); img.save(buf, 'PNG')
    pngb = buf.getvalue()
    xng = X.encode(pngb, meta)
    assert X.decode(xng)[0] == pngb
    os.makedirs(OUT, exist_ok=True)
    open(os.path.join(OUT, name + '.xng'), 'wb').write(xng)
    open(os.path.join(OUT, name + '.png'), 'wb').write(pngb)
    # so sanh truoc/sau
    new = Image.open(io.BytesIO(pngb)).convert('RGBA')
    k = 4 if max(orig.size) <= 160 else 2
    def comp(im):
        bg = Image.new('RGBA', im.size, (110, 110, 130, 255)); bg.alpha_composite(im); return bg.resize((im.width*k, im.height*k), Image.NEAREST).convert('RGB')
    a, b = comp(rgba), comp(new)
    sheet = Image.new('RGB', (a.width*2+12, a.height), (20, 20, 20)); sheet.paste(a, (0, 0)); sheet.paste(b, (a.width+12, 0))
    sheet.save(os.path.join(LOGD, 'truoc_sau_%s.png' % name))
    return Image.open(io.BytesIO(pngb)).size == orig.size

def main():
    os.makedirs(LOGD, exist_ok=True)
    names = sys.argv[1:] or list(SPEC)
    log = []; chosen = []
    for n in names:
        assert process(n, log, chosen), n
    txt = '\n'.join(log)
    open(os.path.join(LOGD, 'img_patch.log'), 'w').write(txt + '\n')
    print(txt)

main()
