#!/usr/bin/env python3
"""In disasm TRUOC/SAU (capstone, 4 lenh truoc + vung va + 4 lenh sau) cho moi diem va trong mods/patches.json
va tu kiem: entry/ret, dich nhay, epilogue/stack cua nhanh `b` vao giua ham, nop thay cbz.
Dung: python3 -I tools/disasm_patch.py [--so work/il2/lib/arm64-v8a/libil2cpp.so] [--script work/il2/out/script.json] > docs/patch_disasm.txt
Thoat != 0 neu mot kiem tra tu dong that bai.
"""
import argparse, os, re, struct, sys
from capstone import Cs, CS_ARCH_ARM64, CS_MODE_ARM

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
sys.path.insert(0, os.path.join(ROOT, 'tools'))
import patch_so
import il2dis


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--so', default=patch_so.DEF_IN)
    ap.add_argument('--script', default=os.path.join(ROOT, 'work/il2/out/script.json'))
    ap.add_argument('--patches', default=patch_so.DEF_PATCHES)
    ap.add_argument('--ctx', type=int, default=4, help='so lenh ngu canh moi phia')
    a = ap.parse_args()

    d = il2dis.Dis(a.so, a.script)
    data = bytes(d.data)
    segs = patch_so.load_segments(data)
    ps = patch_so.load_patches(a.patches)
    cs = Cs(CS_ARCH_ARM64, CS_MODE_ARM)
    starts = sorted(d.meth)
    import bisect

    def func_of(pc):
        i = bisect.bisect_right(starts, pc) - 1
        return starts[i]

    def fname(pc):
        f = func_of(pc)
        if pc < starts[0] or pc - f > 0x20000:
            return '%#x (thunk/runtime)' % pc
        return '%s+0x%x' % (d.meth[f], pc - f) if pc != f else d.meth[f]

    def dis(buf, addr):
        return list(cs.disasm(buf, addr))

    def fmt(ins, mark=False, newbuf=None):
        note = ''
        if ins.mnemonic in ('b', 'bl') or ins.mnemonic.startswith('cb') or ins.mnemonic.startswith('tb'):
            try:
                t = int(ins.op_str.split('#')[-1], 16)
                note = '  ; -> ' + fname(t)
            except ValueError:
                pass
        f = func_of(ins.address)
        head = '  ; === ' + d.meth[f] if ins.address == f else ''
        return '%s %#010x: %s %-6s %-30s%s%s' % ('>>' if mark else '  ', ins.address, ins.bytes.hex(), ins.mnemonic, ins.op_str, note, head)

    fails = 0
    print('# Disasm truoc/sau cac diem va libil2cpp.so (T13). Sinh boi tools/disasm_patch.py. ">>" = lenh bi thay.')
    print('# so goc: %s\n# %d diem va\n' % (os.path.relpath(a.so, ROOT), len(ps)))
    for p in ps:
        rva, n = p['rva_i'], len(p['orig_b'])
        off = patch_so.rva2off(segs, rva, n)
        lo = rva - 4 * a.ctx
        hi = rva + n + 4 * a.ctx
        lo_off = patch_so.rva2off(segs, lo, hi - lo)
        before = data[lo_off:lo_off + (hi - lo)]
        after = bytearray(before)
        after[rva - lo:rva - lo + n] = p['new_b']
        # kiem: byte goc dung
        if before[rva - lo:rva - lo + n] != p['orig_b']:
            print('LOI: byte goc khong khop', p['id']); fails += 1
        print('=' * 100)
        print('%s | %s | RVA %s off 0x%X | %d byte' % (p['id'], p['method'], p['rva'], off, n))
        print('ly do: ' + p['reason'])
        b_ins, a_ins = dis(before, lo), dis(bytes(after), lo)
        if len(b_ins) != (hi - lo) // 4 or len(a_ins) != (hi - lo) // 4:
            print('LOI: capstone khong dich het'); fails += 1
        inr = lambda i: rva <= i.address < rva + n
        print('--- TRUOC')
        for i in b_ins: print(fmt(i, inr(i)))
        print('--- SAU')
        for i in a_ins: print(fmt(i, inr(i)))
        notes = []
        new_ins = [i for i in a_ins if inr(i)]
        old_ins = [i for i in b_ins if inr(i)]
        f = func_of(rva)
        fend = f + d.size(f)
        # --- kiem tu dong
        if new_ins[0].mnemonic == 'ret':
            ok = rva == f and old_ins[0].mnemonic in ('stp', 'str', 'sub') and 'sp' in old_ins[0].op_str
            notes.append('ret tai entry ham (RVA == dau ham %s), lenh goc la push frame (%s %s) => chua co frame/thanh ghi nao bi doi, x30 con nguyen: %s' %
                         (d.meth.get(rva, '?'), old_ins[0].mnemonic, old_ins[0].op_str, 'OK' if ok else 'SAI'))
            fails += not ok
        for i in new_ins:
            if i.mnemonic == 'b':
                t = int(i.op_str.lstrip('#'), 16)
                if t in d.meth:
                    ok = rva == f
                    notes.append('b -> %#x = dau ham %s (tail-call; x0 giu nguyen, lr=caller). Lenh thay nam o entry ham chua push frame: %s' % (t, d.meth[t], 'OK' if ok else 'SAI'))
                    fails += not ok
                    # prologue dich
                    tb = data[patch_so.rva2off(segs, t, 16):][:16]
                    notes.append('   prologue dich: ' + '; '.join('%s %s' % (x.mnemonic, x.op_str) for x in dis(tb, t)))
                else:
                    tf = func_of(t)
                    same = tf == f
                    notes.append('b -> %#x nam trong %s (%s ham)' % (t, d.meth[tf], 'CUNG' if same else 'KHAC'))
                    fails += not same
                    # stack: moi lenh doi sp trong ham
                    fbuf = data[patch_so.rva2off(segs, f, fend - f):][:fend - f]
                    sp_ops = [x for x in dis(fbuf, f) if re.search(r'\bsp\b', x.op_str) and
                              (x.mnemonic in ('sub', 'add') and x.op_str.startswith('sp,') or x.op_str.endswith('!') or '], #' in x.op_str and x.op_str.rstrip().endswith('!') or re.search(r'\[sp\], #', x.op_str))]
                    notes.append('   lenh doi sp trong ham: ' + '; '.join('%#x %s %s' % (x.address, x.mnemonic, x.op_str) for x in sp_ops))
                    ok = len(sp_ops) == 2
                    notes.append('   frame co dinh (1 push + 1 pop, khong doi giua chung): %s' % ('OK' if ok else 'XEM LAI'))
                    fails += not ok
                    # epilogue tu dich
                    tb = data[patch_so.rva2off(segs, t, 4 * 16):][:4 * 16]
                    ep = []
                    for x in dis(tb, t):
                        ep.append('%#x %s %s' % (x.address, x.mnemonic, x.op_str))
                        if x.mnemonic == 'ret': break
                    notes.append('   epilogue tai dich: ' + ' | '.join(ep))
                    # so sanh voi prologue
                    pb = data[patch_so.rva2off(segs, f, 40):][:40]
                    pro = [x for x in dis(pb, f)][:10]
                    notes.append('   prologue ham: ' + ' | '.join('%s %s' % (x.mnemonic, x.op_str) for x in pro))
                    ok = ep and ep[-1].split()[1] == 'ret' and any('add sp' in e for e in ep)
                    notes.append('   epilogue ket thuc bang ret sau add sp: %s' % ('OK' if ok else 'SAI'))
                    fails += not ok
            elif i.mnemonic in ('cbz', 'cbnz'):
                t = int(i.op_str.split('#')[-1], 16)
                tf = func_of(t)
                ok = tf == f
                notes.append('%s %s -> %#x (%s) : cung ham %s' % (i.mnemonic, i.op_str, t, fname(t), 'OK' if ok else 'SAI'))
                fails += not ok
            elif i.mnemonic == 'nop':
                o = old_ins[new_ins.index(i)]
                prev = [x for x in b_ins if x.address == i.address - 4][0]
                notes.append('nop thay %s %s; lenh ngay truoc: %s %s' % (o.mnemonic, o.op_str, prev.mnemonic, prev.op_str))
                ok = o.mnemonic in ('cbz', 'cbnz', 'tbz', 'tbnz')
                fails += not ok
        for x in notes: print('KIEM: ' + x)
        print()
    print('TONG: %d diem va, %d loi tu kiem' % (len(ps), fails))
    sys.exit(1 if fails else 0)


if __name__ == '__main__':
    main()
