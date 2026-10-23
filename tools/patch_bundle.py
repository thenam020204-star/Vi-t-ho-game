#!/usr/bin/env python3
"""Vá data.unity3d: Font 33 (JejuHallasan) + 6 TextAsset eng_Dictionary_* + UI.Text m_Text (text/vi/scene.tsv).
Dung: python3 -I tools/patch_bundle.py --font FONT.ttf --out OUT/data.unity3d [--text-dir work/build/text] [--scene text/vi/scene.tsv]
  Nguon: work/ex/assets/bin/Data/data.unity3d. Luu bang packer='original' (co 0x243 / khoi 0x2) roi mo lai kiem tra."""
import os, sys, argparse, struct, io, hashlib
ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
sys.path.insert(0, os.path.join(ROOT, 'tools'))
import UnityPy, ttgen
from fontTools.ttLib import TTFont

SRC = os.path.join(ROOT, 'work/ex/assets/bin/Data/data.unity3d')
SO = os.path.join(ROOT, 'work/il2/lib/arm64-v8a/libil2cpp.so')
META = os.path.join(ROOT, 'work/il2/assets/bin/Data/Managed/Metadata/global-metadata.dat')
TEXT_IDS = {'Name': 2855, 'Class': 2856, 'Synergy': 2867, 'Ability': 2879, 'Explain': 2882, 'MagicCom': 2887}
FONT_PID = 33


def header_flags(path):
    with open(path, 'rb') as f:
        d = f.read(300)
    b = io.BytesIO(d)
    for _ in range(1): b.readline() if False else None
    def s():
        o = b''
        while True:
            c = b.read(1)
            if c == b'\0': return o
            o += c
    s(); b.read(4); s(); s()
    size, csz, usz, flags = struct.unpack('>qIII', b.read(20))
    return size, flags


def read_scene(path):
    rows = []
    for ln in open(path, encoding='utf-8').read().split('\n')[1:]:
        if not ln.strip(): continue
        lv, pid, en, vi = ln.split('\t')
        rows.append((lv, int(pid), en, vi))
    return rows


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('--font', required=True)
    ap.add_argument('--out', required=True)
    ap.add_argument('--text-dir', default=os.path.join(ROOT, 'work/build/text'))
    ap.add_argument('--scene', default=os.path.join(ROOT, 'text/vi/scene.tsv'))
    a = ap.parse_args()
    font_bytes = open(a.font, 'rb').read()
    cmap = set(TTFont(a.font).getBestCmap())
    rows = read_scene(a.scene)
    for _, _, _, vi in rows:
        miss = [c for c in vi if ord(c) not in cmap]
        if miss: sys.exit('LOI: ky tu ngoai cmap font: %r' % miss)
    texts = {}
    for g in TEXT_IDS:
        d = open(os.path.join(a.text_dir, 'eng_Dictionary_%s.txt' % g), 'rb').read()
        assert d.startswith(b'\xef\xbb\xbf') and b'\r\n' in d, g
        s = d.decode('utf-8')
        miss = sorted({c for c in s if ord(c) not in cmap and c not in '\r\n\ufeff'})
        if miss: sys.exit('LOI: %s co ky tu ngoai cmap (%s)' % (g, ''.join(miss)))
        texts[g] = d

    env, gen = ttgen.load(SRC, SO, META)
    b = list(env.files.values())[0]
    # 1) Font
    fo = b.files['sharedassets0.assets'].objects[FONT_PID]
    f = fo.read(); assert f.m_Name == 'JejuHallasan'
    f.m_FontData = font_bytes; f.save()
    # 2) TextAsset
    for g, pid in TEXT_IDS.items():
        o = b.files['resources.assets'].objects[pid]; t = o.read()
        assert t.m_Name == 'eng_Dictionary_' + g, t.m_Name
        t.m_Script = texts[g].decode('utf-8'); t.save()
    # 3) UI.Text m_Text
    node = gen.get_nodes_up('UnityEngine.UI', 'UnityEngine.UI.Text')
    for lv, pid, en, vi in rows:
        o = b.files[lv].objects[pid]
        t = o.read_typetree(node)
        assert t['m_Text'] == en, (lv, pid, t['m_Text'], en)
        t['m_Text'] = vi
        o.save_typetree(t, node)
    os.makedirs(os.path.dirname(os.path.abspath(a.out)), exist_ok=True)
    data = b.save(packer='original')
    open(a.out, 'wb').write(data)
    del data
    # kiem tra co nen
    _, fl0 = header_flags(SRC); _, fl1 = header_flags(a.out)
    print('flags goc 0x%x moi 0x%x' % (fl0, fl1), 'size', os.path.getsize(a.out))
    if fl0 != fl1: sys.exit('LOI: co nen lech goc')
    # mo lai kiem tra noi dung
    env2 = UnityPy.load(a.out); b2 = list(env2.files.values())[0]
    print('block_info_flags', hex(b2._block_info_flags), 'dataflags', hex(b2.dataflags))
    assert b2._block_info_flags == b._block_info_flags and b2.dataflags == b.dataflags
    assert bytes(b2.files['sharedassets0.assets'].objects[FONT_PID].read().m_FontData) == font_bytes
    for g, pid in TEXT_IDS.items():
        assert b2.files['resources.assets'].objects[pid].read().m_Script.encode('utf-8') == texts[g], g
    for lv, pid, en, vi in rows:
        t = b2.files[lv].objects[pid].read_typetree(node)
        assert t['m_Text'] == vi, (lv, pid)
    print('OK: font + %d TextAsset + %d UI.Text doc lai dung' % (len(TEXT_IDS), len(rows)))


main()
