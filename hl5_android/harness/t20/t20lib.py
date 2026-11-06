"""T20: thư viện kiểm tương đương lockstep base (không T20) vs dev (có fbcopy). WORK=~/hl5work_t20 (build bằng harness/t20/build_dev.sh)."""
import sys, os, struct; sys.path.insert(0, os.path.join(os.path.dirname(os.path.abspath(__file__)), '..', 't18'))
from t18b import *
from t17c1.c1lib import ROUTE
FBS = ('n_fast', 'n_blocks', 'n_rows', 'n_empty', 'n_orig_kind', 'n_orig_overlap')
def fbst(r):
    a = BASE + r.sym['fbcopy_st']; return dict(zip(FBS, struct.unpack('<6I', bytes(r.g.uc.mem_read(a, 24)))))
def hookaddrs(r_dev, r_base):
    """địa chỉ (offset .so) các từ 4 byte do inject_menu sinh (vtable thunk + bl hook): khác nhau giữa 2 bản vì menu đổi kích thước"""
    ex = set()
    for d in ('dev', 'base'):
        for l in open(WK + '/t16/%s/menu/menu_patches.tsv' % d):
            if l.startswith('#') or not l.strip(): continue
            a = int(l.split('\t')[0], 16); ex.add(a)
    return sorted(ex)
def regs(r):
    top = r.g.heap.top; return [('so', 0x10000000, 0x10171000), ('heap', 0x20000000, (top + 0xfff) & ~0xfff), ('misc', 0x60000000, 0x60400000), ('stack', 0x70000000, 0x70400000)]
def snap(r): return {n: np.frombuffer(bytes(r.g.uc.mem_read(b, e - b)), np.uint8) for n, b, e in regs(r)}, {n: b for n, b, e in regs(r)}
def memdiff(ra, rb, ex_words, fbrange):
    """so sánh vùng .so / heap / misc / stack; loại các từ hook (ex_words) trong .so. Trả {vùng: (số byte khác, danh sách dải đầu)}; riêng dải FB tách ra."""
    (A, bases), (B, _) = snap(ra), snap(rb); out = {}
    for n in A:
        a, b = A[n], B[n]; m = min(len(a), len(b)); idx = np.nonzero(a[:m] != b[:m])[0]
        base = bases[n]; ad = idx + base
        if n == 'so':
            keep = np.ones(len(ad), bool)
            for w in ex_words: keep &= ~((ad >= 0x10000000 + w) & (ad < 0x10000000 + w + 4))
            ad = ad[keep]
        fbm = (ad >= fbrange[0]) & (ad < fbrange[1]); nfb = int(fbm.sum()); ad = ad[~fbm]
        runs = []
        if len(ad):
            st = pv = ad[0]
            for x in ad[1:]:
                if x - pv > 8: runs.append((hex(int(st)), int(pv - st + 1))); st = x
                pv = x
            runs.append((hex(int(st)), int(pv - st + 1)))
        out[n] = (int(len(ad)), runs[:6], len(runs), 'fb:%d' % nfb)
    return out

def fbimg2(r, W, Hh):
    """FB RGB565 đúng: r.hostfb = FrameBufferInfo* (r1 của CUSTOM_grpFlushLcd); điểm ảnh ở [info+0x10] (fbimg của T18 đọc từ chính struct nên lệch 216 điểm: phát hiện ở T20)"""
    p = r.g.rd32(r.hostfb + 0x10)
    return np.frombuffer(bytes(r.g.uc.mem_read(p, W * Hh * 2)), np.uint16).reshape(Hh, W).copy()
