#!/usr/bin/env python3
"""Thay THAN method smali (giu nguyen chu ky) trong cay apktool da decode.

    python3 patch_smali.py <dec_dir> [--group font,lang,...]

Moi patch: (class, method header chinh xac, than moi). Patch phai khop DUNG 1 lan, lech thi dung.
Nhom 'offline' them o giai doan offline (scripts/patch_offline.py).
"""
import os, re, sys

FONT = 'Lcom/com2us/wrapper/font/CFont;'
VN = 'Lcom/namp/vh/VNFont;'


def body(locals_, *lines):
    return '    .locals %d\n\n' % locals_ + ''.join('    %s\n' % l for l in lines)


PATCHES = {
    'font': [
        ('com/com2us/wrapper/font/CFont', '.method public getStringWidth([BII)I', body(
            1,
            'invoke-static {p1, p2, p3}, %s->getStringWidth([BII)I' % VN,
            'move-result v0',
            'return v0')),
        ('com/com2us/wrapper/font/CFont', '.method public drawText([BII)I', body(
            1,
            'iget-object v0, p0, %s->mPixelbuffer:[I' % FONT,
            'invoke-static {v0, p1, p2, p3}, %s->drawText([I[BII)I' % VN,
            'move-result v0',
            'return v0')),
        ('com/com2us/wrapper/font/CFont', '.method public drawText([BII[I)I', body(
            6,
            'iget-object v0, p0, %s->mPixelbuffer:[I' % FONT,
            'iget v1, p0, %s->mTextWidth:I' % FONT,
            'move-object v2, p1',
            'move v3, p2',
            'move v4, p3',
            'move-object v5, p4',
            'invoke-static/range {v0 .. v5}, %s->drawTextPart([II[BII[I)I' % VN,
            'move-result v0',
            'return v0')),
        ('com/com2us/wrapper/font/CSGLFont', '.method public drawTextInRect([BIIIII)I', body(
            7,
            'iget-object v0, p0, %s->mPixelbuffer:[I' % FONT,
            'move-object v1, p1',
            'move v2, p2',
            'move v3, p3',
            'move v4, p4',
            'move v5, p5',
            'move/from16 v6, p6',
            'invoke-static/range {v0 .. v6}, %s->drawInRect([I[BIIIII)I' % VN,
            'move-result v0',
            'return v0')),
        ('com/com2us/wrapper/font/CSGLFont', '.method public getStringWidthHeightInRect([BIIII[I)I', body(
            6,
            'move-object v0, p1',
            'move v1, p2',
            'move v2, p3',
            'move v3, p4',
            'move v4, p5',
            'move-object/from16 v5, p6',
            'invoke-static/range {v0 .. v5}, %s->measureInRect([BIIII[I)I' % VN,
            'move-result v0',
            'return v0')),
    ],
    'lang': [
        # Luon chon slot tieng Anh (chua ban dich tieng Viet), bat ke locale may.
        ('com/com2us/wrapper/function/CFunction', '.method public static getLanguage(Ljava/lang/String;)Ljava/lang/String;', body(
            1,
            'const-string v0, "en"',
            'return-object v0')),
    ],
}


def find_class(dec, cls):
    hits = []
    for d in sorted(os.listdir(dec)):
        if d.startswith('smali'):
            p = os.path.join(dec, d, cls + '.smali')
            if os.path.exists(p):
                hits.append(p)
    if len(hits) != 1:
        raise SystemExit('class %s: tim thay %d file' % (cls, len(hits)))
    return hits[0]


def apply(dec, groups):
    total = 0
    for gname in groups:
        for cls, header, newbody in PATCHES[gname]:
            path = find_class(dec, cls)
            src = open(path, encoding='utf-8').read()
            pat = re.compile(r'^' + re.escape(header) + r'\n.*?^\.end method$', re.S | re.M)
            m = pat.findall(src)
            if len(m) != 1:
                raise SystemExit('PATCH %s %s: khop %d lan (can 1)' % (cls, header, len(m)))
            src = pat.sub(lambda _: header + '\n' + newbody + '.end method', src)
            open(path, 'w', encoding='utf-8').write(src)
            total += 1
            print('patched %s %s' % (cls, header))
    print('tong: %d method' % total)


if __name__ == '__main__':
    a = sys.argv[1:]
    groups = ['font', 'lang']
    if '--group' in a:
        groups = a[a.index('--group') + 1].split(',')
    apply(a[0], groups)
