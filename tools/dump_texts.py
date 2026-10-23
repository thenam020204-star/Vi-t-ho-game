#!/usr/bin/env python3
"""Kiem ke UI.Text trong scene (level0/1/2) cua data.unity3d + ten Sprite/Texture2D nghi co chu.
Dung: python3 -I tools/dump_texts.py <data.unity3d> <libil2cpp.so> <global-metadata.dat> <outdir>
Ra: texts.tsv (moi UI.Text), fonts.tsv (tong hop font/best-fit/overflow), images.tsv (ten anh), zdb.json (truong Z_Database level0)."""
import sys, os, struct, json, collections, csv
sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import ttgen

bundle, so, meta, outdir = sys.argv[1:5]
os.makedirs(outdir, exist_ok=True)
env, gen = ttgen.load(bundle, so, meta)
b = list(env.files.values())[0]
scripts = {pid: (o.read().m_AssemblyName, o.read().m_ClassName) for pid, o in b.files['globalgamemanagers.assets'].objects.items() if o.type.name == 'MonoScript'}
fonts = {}
for fn in ('sharedassets0.assets', 'resources.assets', 'sharedassets1.assets', 'sharedassets2.assets'):
    for pid, o in b.files[fn].objects.items():
        if o.type.name == 'Font':
            fonts[(fn, pid)] = o.read().m_Name
ext_names = {}
rows = []
zdb = {}
for lv in ('level0', 'level1', 'level2'):
    f = b.files[lv]
    ext = [getattr(e, 'path', str(e)) for e in f.externals]
    gonames = {pid: o.read().m_Name for pid, o in f.objects.items() if o.type.name == 'GameObject'}
    for pid, o in f.objects.items():
        if o.type.name != 'MonoBehaviour': continue
        raw = o.get_raw_data()
        gfid, gpid = struct.unpack_from('<iq', raw, 0)
        fid, spid = struct.unpack_from('<iq', raw, 16)
        asm, cls = scripts.get(spid, (None, None))
        if (asm, cls) == ('UnityEngine.UI', 'Text'):
            node = gen.get_nodes_up('UnityEngine.UI', 'UnityEngine.UI.Text')
            t = o.read_typetree(node)
            fd = t['m_FontData']
            ffid, fpid = fd['m_Font']['m_FileID'], fd['m_Font']['m_PathID']
            fname = fonts.get((ext[ffid - 1] if ffid > 0 else lv + '.assets', fpid), '?')
            rows.append([lv, pid, gonames.get(gpid, ''), ffid, fpid, fname, fd['m_FontSize'], fd['m_FontStyle'], fd['m_BestFit'], fd['m_MinSize'], fd['m_MaxSize'],
                         fd['m_Alignment'], fd['m_RichText'], fd['m_HorizontalOverflow'], fd['m_VerticalOverflow'], round(fd['m_LineSpacing'], 3), t['m_Text']])
        elif (asm, cls) == ('Assembly-CSharp', 'Z_Database'):
            node = gen.get_nodes_up('Assembly-CSharp', 'Z_Database')
            zdb = {'level': lv, 'path_id': pid, 'ext': ext, 'tree': o.read_typetree(node)}
hdr = ['level', 'path_id', 'go_name', 'font_fileid', 'font_pathid', 'font_name', 'font_size', 'style', 'best_fit', 'min', 'max', 'align', 'rich', 'h_overflow', 'v_overflow', 'line_spacing', 'text']
with open(os.path.join(outdir, 'texts.tsv'), 'w', encoding='utf-8', newline='') as fo:
    w = csv.writer(fo, delimiter='\t', lineterminator='\n')
    w.writerow(hdr)
    for r in rows: w.writerow([str(x).replace('\t', '\\t').replace('\n', '\\n').replace('\r', '\\r') for x in r])
json.dump(zdb, open(os.path.join(outdir, 'zdb.json'), 'w'), ensure_ascii=False, indent=1, default=str)
c = collections.Counter((r[5], r[8], r[13], r[14]) for r in rows)
with open(os.path.join(outdir, 'fonts.tsv'), 'w', encoding='utf-8') as fo:
    fo.write('font\tbest_fit\th_overflow\tv_overflow\tcount\n')
    for k, v in sorted(c.items(), key=lambda x: -x[1]): fo.write('\t'.join(map(str, k)) + '\t%d\n' % v)
with open(os.path.join(outdir, 'images.tsv'), 'w', encoding='utf-8') as fo:
    fo.write('file\ttype\tpath_id\tname\twidth\theight\n')
    for fn in ('sharedassets0.assets', 'sharedassets1.assets', 'sharedassets2.assets', 'resources.assets'):
        for pid, o in b.files[fn].objects.items():
            if o.type.name in ('Texture2D', 'Sprite'):
                d = o.read()
                fo.write('%s\t%s\t%d\t%s\t%s\t%s\n' % (fn, o.type.name, pid, d.m_Name, getattr(d, 'm_Width', ''), getattr(d, 'm_Height', '')))
print(len(rows), 'UI.Text;', 'Z_Database:', zdb.get('level'), zdb.get('path_id'))
