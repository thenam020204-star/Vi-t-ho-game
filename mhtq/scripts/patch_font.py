# -*- coding: utf-8 -*-
"""T04: hook BitmapFont f13c vào game bằng sửa bytecode tĩnh. Chạy lại được.

  python3 -I mhtq/scripts/patch_font.py [--in-jar goc/MHTQ_K790.jar] [--overlay DIR ...]
        [--out-jar work/build/MHTQ_K790_font.jar] [--font f13c] [--no-preverify]

Việc làm (không đổi tên/descriptor/access flag của member có sẵn):
  class f (đo): thay THÂN 4 method, giữ descriptor
      a(Ljava/lang/String;)I  -> ViFont.sw(String)     (Font.stringWidth)
      a(C)I                   -> ViFont.cw(char)       (Font.charWidth)
      a()I                    -> ViFont.h()            (chiều cao dòng = 13, trước đây đo động từ ảnh vẽ thử)
      b()I                    -> 0                     (độ lệch dòng mực đầu = 0)
  class u (vẽ): đổi 2 lệnh invokevirtual Graphics.drawChar/drawString (u.a(CIII)V @185,
      u.a(String,III)V @185) thành invokestatic ViFont.dc/ds cùng vị trí, cùng stack (anchor luôn 20).
  Thêm: ViFont.class (src/patch/ViFont.java, ecj -source 1.3 -target 1.3), BitmapFont.class, font/f13c.bin.
  Rồi preverify (ProGuard -microedition) cả jar, jar_check với jar gốc, sinh .jad.
--overlay DIR: chồng trước các file (vd. kết quả text_pack.py) lên jar vào, tên = đường dẫn tương đối.
"""
import argparse
import glob
import os
import subprocess
import sys
import urllib.request
import zipfile

HERE = os.path.dirname(os.path.abspath(__file__))
sys.path.insert(0, HERE)
import mhtqlib as L          # noqa: E402
import jar_tools as J        # noqa: E402

ROOT = L.ROOT
TOOLS = os.path.join(ROOT, 'work', 'tools')
ECJ_URL = 'https://repo1.maven.org/maven2/org/eclipse/jdt/ecj/3.26.0/ecj-3.26.0.jar'


def find_sk():
    for p in [os.environ.get('SK', '')] + glob.glob(os.path.expanduser('~/.claude/skills/synced/*/j2me-viet-hoa')):
        if p and os.path.exists(os.path.join(p, 'scripts', 'preverify.sh')):
            return p
    raise SystemExit('Không tìm thấy $SK (thư mục skill j2me-viet-hoa)')


def clean_env():
    e = dict(os.environ)
    e.pop('JAVA_TOOL_OPTIONS', None)
    e['FJ'] = os.path.join(TOOLS, 'freej2me_plus.jar')
    e['PROGUARD'] = os.path.join(TOOLS, 'proguard.jar')
    return e


def ensure_ecj():
    jar = os.path.join(TOOLS, 'lib', 'ecj-3.26.0.jar')
    if not os.path.exists(jar):
        os.makedirs(os.path.dirname(jar), exist_ok=True)
        urllib.request.urlretrieve(ECJ_URL, jar)
    jbase = os.path.join(TOOLS, 'jbase')
    if not os.path.isdir(os.path.join(jbase, 'classes')):
        jh = os.path.dirname(os.path.dirname(os.path.realpath(subprocess.check_output(['which', 'javac']).decode().strip())))
        subprocess.check_call(['jmod', 'extract', '--dir', jbase, os.path.join(jh, 'jmods', 'java.base.jmod')],
                              env=clean_env(), stdout=subprocess.DEVNULL)
    return jar, os.path.join(jbase, 'classes')


def compile_vifont(outdir):
    ecj, jbase = ensure_ecj()
    os.makedirs(outdir, exist_ok=True)
    cp = os.pathsep.join([os.path.join(TOOLS, 'freej2me_plus.jar'), os.path.join(TOOLS, 'java')])
    cmd = ['java', '-cp', ecj, 'org.eclipse.jdt.internal.compiler.batch.Main', '-source', '1.3', '-target', '1.3',
           '-encoding', 'UTF-8', '-nowarn', '-bootclasspath', jbase, '-cp', cp, '-d', outdir,
           os.path.join(ROOT, 'src', 'patch', 'ViFont.java')]
    subprocess.check_call(cmd, env=clean_env())
    return open(os.path.join(outdir, 'ViFont.class'), 'rb').read()


def patch_f(data):
    ce = L.ClassEditor(data)
    sw = ce.methodref('ViFont', 'sw', '(Ljava/lang/String;)I')
    cw = ce.methodref('ViFont', 'cw', '(C)I')
    hh = ce.methodref('ViFont', 'h', '()I')
    inv = lambda i: bytes([0xb8, i >> 8, i & 255])
    # aload_1; invokestatic; ireturn
    ce.set_code(ce.find('a', '(Ljava/lang/String;)I'), b'\x2b' + inv(sw) + b'\xac', 1, 2)
    ce.set_code(ce.find('a', '(C)I'), b'\x1b' + inv(cw) + b'\xac', 1, 2)
    ce.set_code(ce.find('a', '()I'), inv(hh) + b'\xac', 1, 1)
    ce.set_code(ce.find('b', '()I'), b'\x03\xac', 1, 1)            # iconst_0; ireturn
    return ce.serialize()


def patch_u(data):
    ce = L.ClassEditor(data)
    dc = ce.methodref('ViFont', 'dc', '(Ljavax/microedition/lcdui/Graphics;CIII)V')
    ds = ce.methodref('ViFont', 'ds', '(Ljavax/microedition/lcdui/Graphics;Ljava/lang/String;III)V')
    for name, desc, target, what in (('a', '(CIII)V', dc, 'drawChar:(CIII)V'),
                                     ('a', '(Ljava/lang/String;III)V', ds, 'drawString:(Ljava/lang/String;III)V')):
        m = ce.find(name, desc)
        code = L.ClassFile.__new__(L.ClassFile)  # chỉ để rõ ràng: không dùng
        c = ce.get_code(m)
        assert c[185] == 0xb6, (name, desc, c[185])
        idx = (c[186] << 8) | c[187]
        e = ce.cp[idx]
        nt = ce.cp[(e[3] << 8) | e[4]]
        assert ce._utf((nt[1] << 8) | nt[2]) == what.split(':')[0], 'sai lệnh gọi'
        assert c[183] == 0x10 and c[184] == 20, 'anchor không phải hằng 20'
        ce.patch_code(m, 185, bytes([0xb6, idx >> 8, idx & 255]), bytes([0xb8, target >> 8, target & 255]))
    return ce.serialize()


def main(argv=None):
    ap = argparse.ArgumentParser()
    ap.add_argument('--in-jar', default=os.path.join(ROOT, 'goc', 'MHTQ_K790.jar'))
    ap.add_argument('--out-jar', default=os.path.join(ROOT, 'work', 'build', 'MHTQ_K790_font.jar'))
    ap.add_argument('--overlay', action='append', default=[])
    ap.add_argument('--font', default='f13c')
    ap.add_argument('--no-preverify', action='store_true')
    a = ap.parse_args(argv)
    sk = find_sk()
    bdir = os.path.dirname(os.path.abspath(a.out_jar))
    os.makedirs(bdir, exist_ok=True)
    zin = zipfile.ZipFile(a.in_jar)
    files = {}
    for d in a.overlay:
        for dp, _, fns in os.walk(d):
            for fn in fns:
                p = os.path.join(dp, fn)
                files[os.path.relpath(p, d).replace(os.sep, '/')] = open(p, 'rb').read()
    get = lambda n: files[n] if n in files else zin.read(n)
    files['f.class'] = patch_f(get('f.class'))
    files['u.class'] = patch_u(get('u.class'))
    files['ViFont.class'] = compile_vifont(os.path.join(ROOT, 'work', 'build', 'vifont'))
    files['BitmapFont.class'] = open(os.path.join(TOOLS, 'java', 'BitmapFont.class'), 'rb').read()
    files['font/%s.bin' % a.font] = open(os.path.join(TOOLS, 'fonts', '%s.bin' % a.font), 'rb').read()
    pre = a.out_jar + '.pre.jar'
    J.overlay(a.in_jar, pre, files)
    if a.no_preverify:
        os.replace(pre, a.out_jar)
    else:
        # Chỉ preverify các class vừa sửa/thêm (f, u, ViFont) để những class khác giữ nguyên từng byte.
        # BitmapFont.class của bộ font đã preverify sẵn.
        mini = a.out_jar + '.mini.jar'
        mini_out = a.out_jar + '.mini_pv.jar'
        with zipfile.ZipFile(mini, 'w', zipfile.ZIP_DEFLATED) as zm:
            for n in ('f.class', 'u.class', 'ViFont.class'):
                zm.writestr(n, files[n])
        r = subprocess.run(['bash', os.path.join(sk, 'scripts', 'preverify.sh'), mini, mini_out,
                            os.path.join(TOOLS, 'freej2me_plus.jar'), a.in_jar], env=clean_env())
        if r.returncode:
            return r.returncode
        zp = zipfile.ZipFile(mini_out)
        for n in ('f.class', 'u.class', 'ViFont.class'):
            files[n] = zp.read(n)
        zp.close()
        for t in (mini, mini_out, pre):
            if os.path.exists(t):
                os.remove(t)
        J.overlay(a.in_jar, a.out_jar, files)
    J.make_jad(a.out_jar, os.path.splitext(a.out_jar)[0] + '.jad')
    r = subprocess.run(['python3', '-I', os.path.join(sk, 'scripts', 'jar_check.py'), a.in_jar, a.out_jar], env=clean_env())
    print('jar: %s (%d byte)' % (a.out_jar, os.path.getsize(a.out_jar)))
    return r.returncode


if __name__ == '__main__':
    sys.exit(main())
