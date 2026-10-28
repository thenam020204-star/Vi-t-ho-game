# -*- coding: utf-8 -*-
"""Mô hình "slot" chữ của MHTQ K790: mỗi slot là một chuỗi hiển thị có thể dịch.

Dùng chung cho text_extract.py và text_pack.py (cùng một nguồn sự thật để id luôn khớp).
Nguồn: thư mục chứa jar đã giải nén (mặc định work/k790_raw).

Định dạng cột giới hạn (giới_hạn): `<W>px[/dòng][ xN dòng ...]`
  - `196px/dong`  : độ rộng ngắt dòng (chuỗi được tự ngắt, chữ dài chỉ thêm dòng/trang).
  - `212px`       : một dòng, không ngắt (quá rộng sẽ tràn/cắt).
  - Có dấu `~` đứng trước số: số ước lượng (chưa suy ra chính xác từ bytecode).
Mọi số đo bằng px của màn 240x320 (K790).
"""
import os
import re
import struct

import mhtqlib as L

KEYS = [u'叶问天', u'小唯', u'婴咛']          # khoá chân dung (đồng bộ ở script/y/v/h/mate)

# (class, method, offset) -> dùng "chết"/không hiển thị: bỏ qua khi mọi nơi dùng đều chết
DEAD_USES = set()
for _o in (7, 12, 17, 22, 27, 32, 38, 44, 50, 113, 118, 123, 128, 133, 138):
    DEAD_USES.add(('m', '<clinit>', _o))             # m.b / m.a: chỉ lấy .length (nhãn thật nằm trong ảnh)
DEAD_USES.add(('m', 'a', 32))                         # chuỗi bản dựng (SVN...) của trang gỡ lỗi
# cả chuỗi bị loại (class, tiền tố)
DEAD_STR = [
    ('f', u'我是谁', u'chuỗi đo chiều cao font'),
    ('g', u'', u'tên chế độ pha màu (debug), không hiển thị'),
    ('i', u'World.getRMSDes()', u'log lỗi nội bộ'),
    ('h', u'我是提示信息', u'mảng giữ chỗ cục bộ, không dùng'),
    ('m', u'项目号：R1', u'chuỗi bản dựng/SVN của trang gỡ lỗi'),
]


class Slot(object):
    def __init__(self, sid, file, loc, orig, note, limit, kind, single=False, group=None, setter=None):
        self.id = sid
        self.file = file
        self.loc = loc
        self.orig = orig
        self.note = note
        self.limit = limit
        self.kind = kind
        self.single = single       # True: một dòng không ngắt (so px được)
        self.group = group         # tên khoá đồng bộ (KEYS) hoặc None
        self.set = setter


CODE_RE = re.compile(u'#(?:#|[0-9A-Fa-f]{6})')


def control_info(s):
    """Mô tả mã điều khiển trong chuỗi, để ghi vào ghi_chú."""
    parts = []
    nbar = s.count('|')
    if nbar:
        parts.append('|x%d' % nbar)
    codes = CODE_RE.findall(s)
    if codes:
        parts.append(' '.join(codes))
    if s.startswith(u'　'):
        parts.append(u'đầu dòng có 　(U+3000, đệm)')
    stray = len(re.sub(CODE_RE, '', s).split('#')) - 1
    if stray:
        parts.append('# lạ x%d' % stray)
    return parts


# ----------------------------------------------------------------------------- script.dat
SCRIPT_OPS = {
    2: (u'thoại', '196px/dong', 3, False),
    3: (u'dẫn truyện (1 dòng/lần, giữa màn hình)', '190px/dong', 1, False),
    20: (u'chữ cuộn từ dưới lên', '210px/dong', 1, False),
    22: (u'câu hỏi Có/Không', '140px/dong', 1, False),
    36: (u'chữ cuộn dưới ảnh CG', '220px/dong', 1, False),
}


class ScriptDoc(object):
    def __init__(self, path):
        self.hdr, self.chunks = L.parse_script(open(path, 'rb').read())

    def slots(self):
        names = {}                      # tên gốc -> list Arg
        order = []
        texts = []
        for ci, ents in enumerate(self.chunks):
            for li, (op, args, jmp) in enumerate(ents):
                if op == -1:
                    continue
                for pi, a in enumerate(args):
                    if a.t in (13, 14) and a.v != '':
                        if op == 2 and pi == 0:
                            if a.v not in names:
                                names[a.v] = []
                                order.append(a.v)
                            names[a.v].append((ci, li))
                            continue
                        texts.append((ci, li, pi, op, a))
        res = []
        argmap = {}                      # tên -> list Arg
        for ci, ents in enumerate(self.chunks):
            for li, (op, args, jmp) in enumerate(ents):
                if op == 2 and args[0].t in (13, 14) and args[0].v != '':
                    argmap.setdefault(args[0].v, []).append(args[0])
        for k, nm in enumerate(order):
            cnt = len(names[nm])
            grp = nm if nm in KEYS else None
            note = u'tên người nói (thoại op2), %d lần dùng; hộp tên rộng 81px, 91px nếu >4 ký tự; đổi 1 lần áp dụng mọi lần dùng' % cnt
            if grp:
                note = u'KHOÁ chân dung (đồng bộ với y.a(String), v.a(), h, mate.dat); ' + note
            lst = argmap[nm]

            def mk(lst):
                def f(t):
                    for a in lst:
                        a.v = t
                return f
            res.append(Slot('SN%03d' % k, 'script.dat', 'name', nm, note, '91px', u'tên người nói',
                            single=True, group=grp, setter=mk(lst)))
        for ci, li, pi, op, a in texts:
            kind, lim, nl, single = SCRIPT_OPS.get(op, (u'chữ (op %d)' % op, '~196px/dong', 1, False))
            ents = self.chunks[ci]
            note = u'%s; chunk %d lệnh %d op%d' % (kind, ci, li, op)
            if op == 2:
                nm = ents[li][1][0].v
                note += u'; nói: %s; khung chân dung #%s' % (nm, ents[li][1][2].v)
                note += u'; %d dòng/trang (sau T06; hiện 2)' % nl
            elif op == 22:
                note += u'; hộp 160px, chữ rộng 140'
            ctl = control_info(a.v)
            if ctl:
                note += u'; ĐK: ' + ' '.join(ctl)

            def mk(a):
                def f(t):
                    a.v = t
                return f
            res.append(Slot('S%03d.%03d.%d' % (ci, li, pi), 'script.dat', 'c%d.l%d.p%d' % (ci, li, pi),
                            a.v, note, lim, kind, single=single, setter=mk(a)))
        return res

    def serialize(self):
        return L.serialize_script(self.hdr, self.chunks)


# ----------------------------------------------------------------------------- item/mission/skill
class ArrDoc(object):
    def __init__(self, name, path):
        self.name = name
        self.res = L.parse_arrays(name, open(path, 'rb').read())

    def slots(self):
        n = self.name
        res = []
        spec = {
            'item': [(1, 'n', u'tên item (kể cả tiền tố 称号：)', '136px', True, u'tên item hiện ở hộp 136px, dài hơn thì tự cuộn ngang; ô lưới đồ nhỏ hơn'),
                     (3, 'd', u'mô tả item', '132px/dong', False, u'mô tả 4 dòng cao 21px (clip 132x81), dài hơn tự cuộn dọc; `|` xuống dòng; đầu chuỗi có 　 là đệm')],
            'mission': [(9, 'n', u'tên nhiệm vụ', '~132px', True, u'tên nhiệm vụ (danh sách nhiệm vụ)'),
                        (10, 'd', u'mô tả nhiệm vụ', '132px/dong', False, u'mô tả nhiệm vụ 4 dòng cao 21px (clip 132x81), tự cuộn; khi hoàn thành ghép sau 任务完成：'),
                        (16, 'g', u'mục tiêu/nơi/NPC của nhiệm vụ', '~132px', True, u'tên NPC/nơi chốn mục tiêu của nhiệm vụ (thống nhất với tên trong thoại)')],
            'skill': [(0, 'n', u'tên kỹ năng', '101px', True, u'tên kỹ năng trong danh sách (hộp 101px, canh giữa)'),
                      (1, 'd', u'mô tả kỹ năng', '132px/dong', False, u'mô tả kỹ năng 4 dòng cao 21px, tự cuộn; ghép sau các nhãn 快捷按键/技能等级/技能师承'),
                      (11, 'm', u'sư thừa/nguồn học kỹ năng', '~132px', True, u'ghép vào mô tả sau nhãn 技能师承： (có thể là tên người: thống nhất với tên trong thoại)')],
        }[n]
        pfx = {'item': 'I', 'mission': 'M', 'skill': 'K'}[n]
        for idx, code, kind, lim, single, note0 in spec:
            arr = self.res[idx][1]
            assert self.res[idx][0] == 'Str'
            for i, s in enumerate(arr):
                if s == '':
                    continue
                note = u'%s #%d; %s' % (kind, i, note0)
                ctl = control_info(s)
                if ctl:
                    note += u'; ĐK: ' + ' '.join(ctl)

                def mk(arr, i):
                    def f(t):
                        arr[i] = t
                    return f
                res.append(Slot('%s.%s%02d' % (pfx, code, i), n + '.dat', 'a%d[%d]' % (idx, i), s, note, lim,
                                kind, single=single, setter=mk(arr, i)))
        return res

    def serialize(self):
        return L.serialize_arrays(self.res)


class MateDoc(object):
    def __init__(self, path):
        self.shorts, self.name = L.parse_mate(open(path, 'rb').read())

    def slots(self):
        def f(t):
            self.name = t
        return [Slot('A.name', 'mate.dat', 'readUTF', self.name,
                     u'tên nhân vật chính (bảng thông số p.a(u,v,e,int)); KHOÁ chân dung: dịch cùng chuỗi với 叶问天 trong script.dat/y/h; hộp 81-91px',
                     '~91px', u'tên nhân vật', single=True, group=self.name if self.name in KEYS else None,
                     setter=f)]

    def serialize(self):
        return L.serialize_mate(self.shorts, self.name)


# ----------------------------------------------------------------------------- class
def _h_role(off):
    if 437 <= off <= 579:
        return (u'tên màn/khu (h.a[String]): góc màn hình và dòng 2 ô lưu', '210px', True)
    if 1190 <= off <= 1545:
        return None                        # theo cột, xem hàm riêng
    if 1546 <= off <= 1700:
        return (u'tên quái (h.b[String], gán o.a)', '~91px', True)
    if 1880 <= off <= 1950:
        return (u'thoại đồng đội trong trận (h.b[][], qua m.b -> k.b)', '196px/dong', False)
    if 2450 <= off <= 2480:
        return (u'thoại boss (h.d[], qua m.a(String,String,w,Z))', '196px/dong', False)
    if 2481 <= off <= 2505:
        return (u'mô tả tính năng đặc biệt (h.e[], hộp m.a(g,String,String))', '120px/dong', False)
    return (u'h.clinit', '~196px/dong', False)


H_COLROLES = [
    (u'tên quái (h.a[][] cột 0, gán o.a)', '~91px', True),
    (u'khoá sprite', '', True),
    (u'tên người nói trong trận (h.a[][] cột 2)', '91px', True),
    (u'thoại trong trận (h.a[][] cột 3, qua k.b)', '196px/dong', False),
]


def _ldc_index(code, off, op):
    if op == 0x12:
        return code[off + 1]
    return struct.unpack_from('>H', code, off + 1)[0]


def classify(cls, use, cf, extra):
    """Trả (mô tả, giới hạn, single) cho một lần dùng; use=(method,desc,offset)."""
    m, d, off = use
    LNG = '225px/dong'
    TOAST = ('212px', True)
    if cls == 'b':
        return (u'ghép mô tả nhiệm vụ khi hoàn thành (+`|`+mô tả)', '132px/dong', False)
    if cls == 'n':
        return (u'nhãn ghép trong mô tả kỹ năng (+`|`)', '132px/dong', False)
    if cls == 'h':
        if extra.get('h_col') is not None:
            r = H_COLROLES[extra['h_col']]
            return r
        return _h_role(off)
    if cls == 'i':
        if m == 'run' and off == 512 or m == 'd':
            return (u'câu hỏi Có/Không (hộp m.a(String)Z)', '140px/dong', False)
        if m == 'run':
            return (u'nút Có/Không của hộp hỏi (phím trong hộp 30x30)', '~30px', True)
        if m == '<clinit>' or (m == 'a' and off == 35):
            return (u'tên ô lưu/đọc (màn lưu: 2 dòng/ô)', '210px', True)
        return (u'thông báo lỗi lưu/đọc (màn lỗi j.a, ngắt `|`)', '~225px/dong', False)
    if cls == 'j':
        return (u'phím mềm "Trở lại"', '~100px', True)
    if cls == 'l':
        if m == '<clinit>':
            return (u'mô tả gói trả phí/SMS (màn thanh toán)', LNG, False)
        if m == 'a' and off in (21, 40):
            return (u'điều khoản dịch vụ SMS (màn thanh toán)', LNG, False)
        if off in (290, 292, 467, 469):
            return (u'nút màn thanh toán (phím mềm)', '~100px', True)
        return (u'thông báo SMS/trả phí (m.a(String) toast)', '212px', True)
    if cls == 'm':
        if m == 'a' and d == '()I':
            return {467: (u'tiêu đề trang Trợ giúp', '~214px', True),
                    469: (u'nội dung trang Trợ giúp (cuộn dọc, m.a(String,String))', '214px/dong', False),
                    477: (u'tiêu đề trang Giới thiệu', '~214px', True),
                    479: (u'nội dung trang Giới thiệu (cuộn dọc)', '214px/dong', False)}.get(off, (u'm.a()', '214px/dong', False))
        if m == 'a' and d.startswith('(Ljava/lang/String;Ljava/lang/String;)'):
            return (u'phím mềm "Trở lại" của trang Trợ giúp/Giới thiệu', '~100px', True)
        if m == 'a' and off == 42:
            return (u'câu hỏi Có/Không (hộp m.a(String)Z)', '140px/dong', False)
        if m == 'a' and d == '(Z)Z':
            return (u'thông báo lưu (toast)', '212px', True)
        if m == 'a' and d == '()V':
            if off in (216, 276, 314):
                return (u'tên tính năng đặc biệt (tiêu đề hộp mô tả m.a(g,String,String))', '~150px', True)
            return (u'thông báo bật/tắt tính năng (toast)', '212px', True)
        if m == 'b' and d == '()V':
            return (u'thông báo (toast, có khi ghép số vào giữa)', '212px', True)
        if d.startswith('(Lv;I)V') or d.startswith('(Lv;'):
            return (u'thông báo chỉ số/rèn kiếm (toast, ghép với số)', '212px', True)
        if m == 'a' and d == '(I)V':
            return (u'thông báo mua/bán (toast)', '212px', True)
        if m == 'a' and d.startswith('(Lz;Lv;'):
            return (u'nhật ký chiến đấu (ghép chuỗi, ngắt `k.b` rộng 133px)', '133px/dong', False)
        if m == '<clinit>':
            return {63: (u'mục menu tạm dừng', 'auto(~160px)', True)}.get(off, (u'mục menu (hộp tự giãn theo chữ dài nhất, ~160px)', '~160px', True))
        return (u'm', '~212px', True)
    if cls == 'p':
        if m == 'a' and off == 317 or (m == 'b' and off in (313,)) or (d.startswith('(Lu;Lv;') and off == 456):
            return (u'thông báo danh sách trống (canh giữa vùng 176px)', '176px', True)
        if off in (26, 41):
            return (u'ô thông tin chọn trang bị (hộp 160px)', '160px', True)
        if off in (66, 93, 128):
            return (u'nhãn cửa hàng/số lượng (ghép với số)', '~60px', True)
        if off in (408, 410, 204, 206):
            return (u'phím mềm', '~100px', True)
        if off in (154, 156):
            return (u'nút Có/Không (hộp 160px, nút 30x30)', '~30px', True)
        if off == 165:
            return (u'nhãn "Cấp" (có 3 dấu cách đầu, ghép với số)', '~50px', True)
        if m == 'd':
            return (u'màn tải tài nguyên "Đang tải (" + số + "%)"', '~100px', True)
        return (u'p', '~212px', True)
    if cls == 'q':
        return (u'hộp tạm dừng khi mất focus', '~200px', True)
    if cls == 's':
        return (u'tên người nói/quái boss (hộp tên 91px)', '91px', True)
    if cls == 'v':
        if m == 'a' and d == '()Ljava/lang/String;':
            return (u'KHOÁ chân dung đồng đội + tên người nói/hộp tên 91px', '91px', True)
        if d.startswith('(IZ') or off in (12, 33):
            return (u'thông báo (toast)', '212px', True)
        return (u'thông báo/nhật ký trận (ghép chuỗi + số, `|`)', '~133px/dong', False)
    if cls == 'w':
        return (u'thông báo nhặt đồ (toast, ghép tên vật phẩm)', '212px', True)
    if cls == 'y':
        if d == '(Ljava/lang/String;)Ljava/lang/Object;':
            return (u'khoá so khớp chân dung trong y.a(String) (không hiển thị trực tiếp)', '-', True)
        if off == 319:
            return (u'phím mềm "Trở lại"', '~100px', True)
        return (u'thông báo (toast)', '212px', True)
    if cls == 'z':
        if m == 'a' and d.startswith('(Z)Z') or off in (115, 120, 150):
            return (u'hộp thông báo (m.a(g,String,String...), ngắt `|`, rộng 120px)', '120px/dong', False)
        return (u'thông báo trong trận (toast)', '212px', True)
    return (u'chưa phân loại', '~212px', True)


class ClassDoc(object):
    def __init__(self, name, path):
        self.name = name
        self.cf = L.ClassFile(open(path, 'rb').read())
        self.repl = {}

    def slots(self):
        cf = self.cf
        uses = cf.usages()
        # nhóm cột của h.a[][]
        h_cols = {}
        if self.name == 'h':
            code = [m for m in cf.methods if m[0] == '<clinit>'][0][2]
            seq = []
            for off, op, ln in L.iter_insns(code):
                if op in (0x12, 0x13) and 1190 <= off <= 1545:
                    idx = _ldc_index(code, off, op)
                    if cf.cp[idx][0] == 8:
                        seq.append((off, idx))
            for k, (off, idx) in enumerate(seq):
                h_cols[(idx, off)] = k % 4
        res = []
        utf_users = cf.utf_users()
        for idx in cf.string_idx:
            s = cf.string_text(idx)
            if not L.has_cjk(s):
                continue
            ulist = uses.get(idx, [])
            reason = None
            for c, pre, why in DEAD_STR:
                if c == self.name and s.startswith(pre):
                    reason = why
            if reason is None and ulist and all((self.name, u[0], u[2]) in DEAD_USES for u in ulist):
                reason = u'chỉ lấy .length hoặc dữ liệu bản dựng'
            if reason is not None:
                continue
            assert set(utf_users.get(cf.cp[idx][1], [])) == {8}, 'Utf8 dùng chung ngoài String: %s #%d' % (self.name, idx)
            descs = []
            lim = None
            single = None
            for u in ulist:
                extra = {'h_col': h_cols.get((idx, u[2]))} if self.name == 'h' else {}
                dsc, l_, sg = classify(self.name, u, cf, extra)
                if dsc not in descs:
                    descs.append(dsc)
                if lim is None:
                    lim, single = l_, sg
            if not ulist:
                descs, lim, single = [u'không có ldc (chưa rõ)'], '~212px', True
            where = ', '.join('%s.%s@%d' % (self.name, u[0], u[2]) for u in ulist[:3])
            if len(ulist) > 3:
                where += ', ...(%d)' % len(ulist)
            note = u'; '.join(descs) + u' [' + where + u']'
            grp = s if s in KEYS else None
            if grp:
                note = u'KHOÁ chân dung (đồng bộ mọi nơi; có thể là khoá của y.a(String)); ' + note
            if self.name == 'h' and any(x in (u'khoá sprite',) for x in descs):
                continue
            if re.search(u'怪物名字|关卡\\d|\\?\\?|？？？', s):
                note += u'; có vẻ là chuỗi giữ chỗ (chưa dùng/ít gặp), dịch ngắn gọn'
            ctl = control_info(s)
            if ctl:
                note += u'; ĐK: ' + ' '.join(ctl)
            if s.strip() == '':
                continue

            def mk(utf_idx):
                def f(t):
                    self.repl[utf_idx] = t
                return f
            res.append(Slot('C.%s.%d' % (self.name, idx), self.name + '.class', '#%d' % idx, s, note, lim,
                            descs[0], single=bool(single), group=grp, setter=mk(cf.cp[idx][1])))
        return res

    def serialize(self):
        return self.cf.patch(self.repl)


def build_docs(src):
    docs = []          # (relpath, doc)
    docs.append(('script.dat', ScriptDoc(os.path.join(src, 'script.dat'))))
    for n in ('item', 'mission', 'skill'):
        docs.append((n + '.dat', ArrDoc(n, os.path.join(src, n + '.dat'))))
    docs.append(('mate.dat', MateDoc(os.path.join(src, 'mate.dat'))))
    for fn in sorted(os.listdir(src)):
        if fn.endswith('.class') and '/' not in fn:
            cd = ClassDoc(fn[:-6], os.path.join(src, fn))
            if any(L.has_cjk(cd.cf.string_text(i)) for i in cd.cf.string_idx):
                docs.append((fn, cd))
    return docs


def collect(src):
    docs = []
    slots = []
    for rel, d in build_docs(src):
        sl = d.slots()
        if sl:
            docs.append((rel, d))
            slots.extend(sl)
    ids = [s.id for s in slots]
    assert len(ids) == len(set(ids)), 'id trùng'
    return docs, slots
