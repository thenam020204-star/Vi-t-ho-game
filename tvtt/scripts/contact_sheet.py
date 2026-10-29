#!/usr/bin/env python3
"""Lưới ảnh (contact sheet): python3 scripts/contact_sheet.py DIR OUT_PREFIX [--max-h 900] [--w 1280]
Xếp mọi PNG trong DIR (đệ quy) vào các trang, nền caro, nhãn tên file; ảnh nhỏ phóng 2x/3x."""
import sys, glob, os, argparse
from PIL import Image, ImageDraw

def checker(w, h, s=6):
    im = Image.new('RGB', (w, h), (200, 200, 200))
    d = ImageDraw.Draw(im)
    for y in range(0, h, s):
        for x in range(0, w, s):
            if (x // s + y // s) & 1:
                d.rectangle([x, y, x + s - 1, y + s - 1], fill=(150, 150, 170))
    return im

def tile(path):
    im = Image.open(path).convert('RGBA')
    k = 3 if max(im.size) <= 40 else 2 if max(im.size) <= 110 else 1
    if k > 1:
        im = im.resize((im.width * k, im.height * k), Image.NEAREST)
    bg = checker(im.width, im.height)
    bg.paste(im, (0, 0), im)
    return bg

def main():
    ap = argparse.ArgumentParser()
    ap.add_argument('dir'); ap.add_argument('out')
    ap.add_argument('--w', type=int, default=1280); ap.add_argument('--max-h', type=int, default=900)
    ap.add_argument('--skip', default='')
    a = ap.parse_args()
    skip = set(a.skip.split(',')) if a.skip else set()
    files = sorted(f for f in glob.glob(os.path.join(a.dir, '**/*.png'), recursive=True)
                   if os.path.basename(f) not in skip)
    tiles = [(os.path.relpath(f, a.dir), tile(f)) for f in files]
    pages, cur, x, y, rowh = [], [], 4, 4, 0
    pg = 0
    def flush():
        nonlocal cur, x, y, rowh
        if cur:
            H = y + rowh + 4
            im = Image.new('RGB', (a.w, H), (40, 40, 48)); d = ImageDraw.Draw(im)
            for n, t, px, py in cur:
                im.paste(t, (px, py + 10)); d.text((px, py), os.path.basename(n)[:-4][:max(10, t.width // 6)], fill=(255, 255, 120))
            pages.append(im)
        cur, x, y, rowh = [], 4, 4, 0
    for n, t in tiles:
        w = max(t.width, 66)
        if x + w + 4 > a.w:
            x, y, rowh = 4, y + rowh + 14, 0
        if y + t.height + 14 > a.max_h and cur:
            flush()
        cur.append((n, t, x, y)); x += w + 4; rowh = max(rowh, t.height + 10)
    flush()
    for i, p in enumerate(pages):
        p.save('%s_%02d.png' % (a.out, i + 1))
    print(len(files), 'ảnh,', len(pages), 'trang')

if __name__ == '__main__':
    main()
