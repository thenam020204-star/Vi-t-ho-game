#!/usr/bin/env python3
"""T17c0: sinh các dòng patch ô vtable (Paint/TouchPressed/TouchMoved/TouchReleased -> thunk của dispatch.c) cho CÁC LỚP ĐÃ BẬT trong states.def.

  inject_menu.py SO_IN OUTDIR [--update-repo]

SO_IN   = .so mà patch sẽ áp lên (kiểm byte gốc của từng ô: phải đúng địa chỉ hàm gốc đã biết).
OUTDIR  = thư mục build.sh (menu.sym, menu.info). Ghi OUTDIR/menu_patches.tsv (cùng định dạng so_patches.tsv; áp bằng patch/apply_so.py).
--update-repo: thay khối '# >>> T17c menu-vtable' trong patch/so_patches.tsv + dòng 'PT_LOAD menu' trong patch/caves.tsv (CHỈ dùng với build phát hành, không --debug).
Lớp chưa có dòng trong states.def thì KHÔNG có patch (ô vtable giữ nguyên = đường gốc). Lớp mà classes.def còn để -1000 (chưa xác định trạng thái) bị từ chối."""
import os, re, struct, subprocess, sys
HERE = os.path.dirname(os.path.abspath(__file__))
REPO = os.path.abspath(os.path.join(HERE, '..', '..'))
SLOTS = [('Paint()', 0x40, 'mui_paint_'), ('TouchPressed(int, int, int)', 0x24, 'mui_tp_'),
         ('TouchMoved(int, int, int)', 0x2c, 'mui_tm_'), ('TouchReleased(int, int, int)', 0x28, 'mui_tr_')]
HERO_HOOKS = os.path.join(HERE, 'hooks.def')
START, END = '# >>> T17c menu-vtable', '# <<< T17c menu-vtable'


def v2off(d, va):
    po = struct.unpack_from('<I', d, 0x1c)[0]; n = struct.unpack_from('<H', d, 0x2c)[0]
    for i in range(n):
        t, off, vad, _, fsz = struct.unpack_from('<5I', d, po + 32 * i)
        if t == 1 and vad <= va < vad + fsz: return va - vad + off
    raise ValueError(hex(va))


def main():
    so, out = sys.argv[1], sys.argv[2]
    d = open(so, 'rb').read()
    info = dict(re.findall(r'(\w+)=(.*?)(?=\s\w+=|\s*$)', open(os.path.join(out, 'menu.info')).read().strip()))
    enabled = info.get('classes', '').split()
    sym = {l.split()[2]: int(l.split()[0], 16) for l in open(os.path.join(out, 'menu.sym')) if len(l.split()) == 3}
    classes = {}
    for m in re.finditer(r'^MUI_CLASS\((\w+),\s*(\w+),\s*(.+)\)\s*$', open(os.path.join(HERE, 'classes.def')).read(), re.M):
        classes[m.group(1)] = (m.group(2), m.group(3).strip())
    nm = subprocess.run(['llvm-nm', '-C', '--defined-only', so], capture_output=True, text=True).stdout.splitlines()
    def addr(name):
        for l in nm:
            p = l.split(' ', 2)
            if len(p) == 3 and p[2] == name: return int(p[0], 16)
        raise SystemExit('thiếu symbol ' + name)
    rows = []
    for c in enabled:
        cname, expr = classes[c]
        if expr == '-1000': raise SystemExit('lớp %s bật trong states.def nhưng classes.def chưa xác định trạng thái (-1000)' % c)
        vt = addr('vtable for ' + cname)
        for meth, off, pre in SLOTS:
            slot = vt + 8 + off
            orig = addr('%s::%s' % (cname, meth)); o = v2off(d, slot)
            cur = struct.unpack_from('<I', d, o)[0]
            new = sym[pre + c]
            if cur == new: pass
            elif cur != orig: raise SystemExit('ô %#x của %s::%s = %#x, mong đợi %#x' % (slot, cname, meth, cur, orig))
            rows.append('%x\t%s\t%s\tT17c: vtable %s +%#x (%s) %#x -> thunk %s%s %#x' % (
                slot, struct.pack('<I', orig).hex(), struct.pack('<I', new).hex(), cname, 8 + off, meth.split('(')[0], orig, pre, c, new))
    for m_ in re.finditer(r'^HOOK_BL\((0x[0-9a-fA-F]+),\s*(0x[0-9a-fA-F]+),\s*(\w+)\)', open(os.path.join(HERO_HOOKS)).read() if os.path.exists(HERO_HOOKS) else '', re.M):
        va, tgt, fn = int(m_.group(1), 16), int(m_.group(2), 16), m_.group(3)
        o = v2off(d, va); cur = struct.unpack_from('<I', d, o)[0]
        enc = lambda t: 0xEB000000 | (((t - (va + 8)) >> 2) & 0xFFFFFF)
        new = enc(sym[fn])
        if cur == new: pass
        elif cur != enc(tgt): raise SystemExit('lệnh tại %#x = %#x, mong đợi bl %#x (%#x)' % (va, cur, tgt, enc(tgt)))
        rows.append('%x\t%s\t%s\tT17c2: call-site %#x bl %#x -> bl %s %#x' % (va, struct.pack('<I', enc(tgt)).hex(), struct.pack('<I', new).hex(), va, tgt, fn, sym[fn]))
    # T24b: HOOK_B(vaddr, từ_lệnh_gốc, hàm): thay MỘT lệnh (thường là lệnh đầu hàm) bằng `b hàm`; kiểm từ lệnh gốc đúng như khai báo.
    for m_ in re.finditer(r'^HOOK_B\((0x[0-9a-fA-F]+),\s*(0x[0-9a-fA-F]+),\s*(\w+)\)', open(os.path.join(HERO_HOOKS)).read() if os.path.exists(HERO_HOOKS) else '', re.M):
        va, word, fn = int(m_.group(1), 16), int(m_.group(2), 16), m_.group(3)
        o = v2off(d, va); cur = struct.unpack_from('<I', d, o)[0]
        new = 0xEA000000 | (((sym[fn] - (va + 8)) >> 2) & 0xFFFFFF)
        if cur == new: pass
        elif cur != word: raise SystemExit('lệnh tại %#x = %#x, mong đợi %#x' % (va, cur, word))
        rows.append('%x\t%s\t%s\tT24b: lệnh %#x (%#x) -> b %s %#x' % (va, struct.pack('<I', word).hex(), struct.pack('<I', new).hex(), va, word, fn, sym[fn]))
    hdr = ['# sinh bởi engine/menu/inject_menu.py (T17c): vá ô vtable cho lớp đã bật trong states.def: %s' % (', '.join(enabled) or '(chưa lớp nào: đường gốc y hệt)')]
    open(os.path.join(out, 'menu_patches.tsv'), 'w', encoding='utf-8').write('\n'.join(hdr + rows) + '\n')
    print('menu_patches.tsv: %d dòng, lớp bật: %s' % (len(rows), ' '.join(enabled) or '-'))
    if '--update-repo' in sys.argv:
        if info.get('debug') != '0': raise SystemExit('--update-repo chỉ với build phát hành (không --debug)')
        t = os.path.join(REPO, 'patch/so_patches.tsv')
        old = open(t, encoding='utf-8').read().split('\n')
        if START in old:
            i, j = old.index(START), old.index(END); old = old[:i] + old[j + 1:]
        while old and old[-1] == '': old.pop()
        old += [START] + hdr + rows + [END, '']
        open(t, 'w', encoding='utf-8').write('\n'.join(old))
        c = os.path.join(REPO, 'patch/caves.tsv')
        lines = [l for l in open(c, encoding='utf-8').read().split('\n') if l and not l.startswith('PT_LOAD menu')]
        lines.append('PT_LOAD menu\t0x171000\tmemsz %s (filesz %s)\tmã+rodata+bss thư viện menu mới\tengine/menu/*\tphdr[4] PT_GNU_STACK -> PT_LOAD (patch/mk_menu_segment.py); lớp bật: %s' % (
            info['memsz'], info['filesz'], ' '.join(enabled) or '-'))
        open(c, 'w', encoding='utf-8').write('\n'.join(lines) + '\n')
        print('đã cập nhật patch/so_patches.tsv và caves.tsv')


main()
