# -*- coding: utf-8 -*-
"""T06: ngắt dòng theo dấu cách + khung thoại 3 dòng/trang. Chạy lại được.

  python3 -I mhtq/scripts/patch_wrap.py --in-jar work/build/MHTQ_K790_font.jar --out-jar work/build/MHTQ_K790_m2.jar
        [--orig-jar goc/MHTQ_K790.jar]

Không đổi tên/descriptor/access flag của member có sẵn. Việc làm (bytecode, dẫn chiếu logs/text_path.md mục c):
  k.b(String,I)[String          thân -> ViWrap.wrap(s, w, 1)   (ngắt tại dấu cách, đo BitmapFont, #RRGGBB/##/|/\\n, chặn # cuối)
  j.a(String,C,I)[String        thân -> ViWrap.wrap(s, w, 0)   (j.a(String,I) gọi hàm này nên cũng ăn theo)
  m.a(String,String,w,z,Z,I,Z)V (hộp thoại): mảng/vòng 2 -> 3 (iconst_2->iconst_3 tại 135,140,146,156,378,573),
        `iinc 8,2` -> `iinc 8,3` (351, 546), các điều kiện tiến độ gõ chữ hai dòng đổi sang ViTalk.done/lt/fill/step
        (cùng độ dài, đệm nop, đích nhảy giữ nguyên).
  p.a(u,String,String[],I,I,[I)V (vẽ khung thoại): bước dòng 23 -> LINE, cao clip 22 -> LINE, lệch tâm 11 -> LINE_C
        (vòng lặp theo độ dài mảng nên tự vẽ dòng thứ 3).
  Thêm ViWrap.class, ViTalk.class (ecj -source 1.3 -target 1.3), preverify j,k,m,p + 2 class mới.
"""
import argparse
import os
import subprocess
import sys
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import jar_tools as J        # noqa: E402
import patch_font as PF      # noqa: E402

ROOT = L.ROOT
TOOLS = PF.TOOLS
LINE = 15            # bước dòng khung thoại (px); thanh thoại cao 52 từ y=268: 271, 286, 301
LINE_H = 15
LINE_C = 8           # tâm dọc chữ cách đỉnh dòng (13px chữ, anchor VCENTER)


def compile_new(outdir):
    ecj, jbase = PF.ensure_ecj()
    os.makedirs(outdir, exist_ok=True)
    cp = os.pathsep.join([os.path.join(TOOLS, 'freej2me_plus.jar'), os.path.join(TOOLS, 'java'),
                          os.path.join(ROOT, 'work', 'build', 'vifont')])
    srcs = [os.path.join(ROOT, 'src', 'patch', n) for n in ('ViWrap.java', 'ViTalk.java')]
    cmd = ['java', '-cp', ecj, 'org.eclipse.jdt.internal.compiler.batch.Main', '-source', '1.3', '-target', '1.3',
           '-encoding', 'UTF-8', '-nowarn', '-bootclasspath', jbase, '-cp', cp, '-d', outdir] + srcs
    subprocess.check_call(cmd, env=PF.clean_env())
    return dict((n, open(os.path.join(outdir, n + '.class'), 'rb').read()) for n in ('ViWrap', 'ViTalk'))


def inv(i):
    return bytes([0xb8, i >> 8, i & 255])


def br(op, here, target):
    d = target - here
    return bytes([op, (d >> 8) & 255, d & 255])


def patch_kj(kdata, jdata):
    ce = L.ClassEditor(kdata)
    wr = ce.methodref('ViWrap', 'wrap', '(Ljava/lang/String;II)[Ljava/lang/String;')
    # k.b(String,int): aload_0; iload_1; iconst_1; invokestatic; areturn
    ce.set_code(ce.find('b', '(Ljava/lang/String;I)[Ljava/lang/String;'), b'\x2a\x1b\x04' + inv(wr) + b'\xb0', 3, 2)
    k = ce.serialize()
    ce = L.ClassEditor(jdata)
    wr = ce.methodref('ViWrap', 'wrap', '(Ljava/lang/String;II)[Ljava/lang/String;')
    # j.a(String,char,int): aload_0; iload_2; iconst_0; invokestatic; areturn
    ce.set_code(ce.find('a', '(Ljava/lang/String;CI)[Ljava/lang/String;'), b'\x2a\x1c\x03' + inv(wr) + b'\xb0', 3, 3)
    return k, ce.serialize()


def check(code, off, expect):
    got = code[off:off + len(expect)]
    if got != expect:
        raise SystemExit('bytecode không như dự kiến tại %d: có %s, cần %s' % (off, got.hex(), expect.hex()))


def patch_m(data):
    ce = L.ClassEditor(data)
    mt = ce.find('a', '(Ljava/lang/String;Ljava/lang/String;Lw;Lz;ZIZ)V')
    c = ce.get_code(mt)
    done = ce.methodref('ViTalk', 'done', '([I[I)Z')
    lt = ce.methodref('ViTalk', 'lt', '([II)Z')
    fill = ce.methodref('ViTalk', 'fill', '([II)V')
    step = ce.methodref('ViTalk', 'step', '([II)Z')
    ed = []   # (off, old, new)
    # hằng 2 -> 3
    for off in (135, 140, 146, 156, 378, 573):
        ed.append((off, b'\x05', b'\x06'))
    for off in (351, 546):
        ed.append((off, b'\x84\x08\x02', b'\x84\x08\x03'))

    def nop(n):
        return b'\x00' * n
    # (d) 311..332: prog[0]<w[0] -> 441 ; prog[1]<w[1] -> 441
    old = (b'\x19\x04\x03\x2e\x19\x0e\x03\x2e' + br(0xa1, 319, 441) +
           b'\x19\x04\x04\x2e\x19\x0e\x04\x2e' + br(0xa1, 330, 441))
    new = b'\x19\x04\x19\x0e' + inv(done) + br(0x99, 318, 441)
    ed.append((311, old, new + nop(len(old) - len(new))))
    # (e) 441..458: prog[0]<n2 -> 459 ; prog[1]>=n2 -> 486
    old = (b'\x19\x04\x03\x2e\x15\x05' + br(0xa1, 447, 459) +
           b'\x19\x04\x04\x2e\x15\x05' + br(0xa2, 456, 486))
    new = b'\x19\x04\x15\x05' + inv(lt) + br(0x99, 448, 486)
    ed.append((441, old, new + nop(len(old) - len(new))))
    # (f) 471..482: prog[0]=n2; prog[1]=n2
    old = b'\x19\x04\x03\x15\x05\x4f' + b'\x19\x04\x04\x15\x05\x4f'
    new = b'\x19\x04\x15\x05' + inv(fill)
    ed.append((471, old, new + nop(len(old) - len(new))))
    # (g) 486..527: tiến 15px dòng đầu chưa đủ; nhảy 633 nếu có tiến
    old = (b'\x19\x04\x03\x2e\x15\x05' + br(0xa2, 492, 507) +
           b'\x19\x04\x03\x5c\x2e\x10\x0f\x60\x4f' + br(0xa7, 504, 633) +
           b'\x19\x04\x04\x2e\x15\x05' + br(0xa2, 513, 528) +
           b'\x19\x04\x04\x5c\x2e\x10\x0f\x60\x4f' + br(0xa7, 525, 633))
    new = b'\x19\x04\x15\x05' + inv(step) + br(0x9a, 493, 633)
    ed.append((486, old, new + nop(len(old) - len(new))))
    for off, old, new in ed:
        check(c, off, old)
        assert len(old) == len(new)
        ce.patch_code(mt, off, old, new)
    return ce.serialize()


def patch_p(data):
    ce = L.ClassEditor(data)
    mt = ce.find('a', '(Lu;Ljava/lang/String;[Ljava/lang/String;II[I)V')
    c = ce.get_code(mt)
    ed = [(122, b'\x10\x17', bytes([0x10, LINE])),        # y dòng: i*23 -> i*LINE (clip)
          (131, b'\x10\x16', bytes([0x10, LINE_H])),      # cao clip 22
          (148, b'\x10\x17', bytes([0x10, LINE])),        # y dòng chữ
          (152, b'\x10\x0b', bytes([0x10, LINE_C]))]  # tâm dọc 11 -> LINE_H/2
    for off, old, new in ed:
        check(c, off, old)
        ce.patch_code(mt, off, old, new)
    return ce.serialize()


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--in-jar', default=os.path.join(ROOT, 'work', 'build', 'MHTQ_K790_font.jar'))
    ap.add_argument('--out-jar', default=os.path.join(ROOT, 'work', 'build', 'MHTQ_K790_m2.jar'))
    ap.add_argument('--orig-jar', default=os.path.join(ROOT, 'goc', 'MHTQ_K790.jar'))
    ap.add_argument('--no-preverify', action='store_true')
    a = ap.parse_args(argv)
    sk = PF.find_sk()
    zin = zipfile.ZipFile(a.in_jar)
    files = {}
    files['k.class'], files['j.class'] = patch_kj(zin.read('k.class'), zin.read('j.class'))
    files['m.class'] = patch_m(zin.read('m.class'))
    files['p.class'] = patch_p(zin.read('p.class'))
    new = compile_new(os.path.join(ROOT, 'work', 'build', 'viwrap'))
    files['ViWrap.class'] = new['ViWrap']
    files['ViTalk.class'] = new['ViTalk']
    names = ('k.class', 'j.class', 'm.class', 'p.class', 'ViWrap.class', 'ViTalk.class')
    if not a.no_preverify:
        mini = a.out_jar + '.mini.jar'
        mini_out = a.out_jar + '.mini_pv.jar'
        with zipfile.ZipFile(mini, 'w', zipfile.ZIP_DEFLATED) as zm:
            for n in names:
                zm.writestr(n, files[n])
        r = subprocess.run(['bash', os.path.join(sk, 'scripts', 'preverify.sh'), mini, mini_out,
                            os.path.join(TOOLS, 'freej2me_plus.jar'), a.in_jar], env=PF.clean_env())
        if r.returncode:
            return r.returncode
        zp = zipfile.ZipFile(mini_out)
        for n in names:
            files[n] = zp.read(n)
        zp.close()
        for t in (mini, mini_out):
            os.remove(t)
    J.overlay(a.in_jar, a.out_jar, files)
    J.make_jad(a.out_jar, os.path.splitext(a.out_jar)[0] + '.jad')
    print('jar: %s (%d byte)' % (a.out_jar, os.path.getsize(a.out_jar)))
    return 0


if __name__ == '__main__':
    sys.exit(main())
