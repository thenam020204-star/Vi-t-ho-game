"""Match the colour mood of a picture to a reference (Reinhard transfer in CIE Lab), partial strength."""
import sys
import numpy as np
from PIL import Image

def srgb2lab(a):
    a = a / 255.0
    a = np.where(a > 0.04045, ((a + 0.055) / 1.055) ** 2.4, a / 12.92)
    M = np.array([[0.4124, 0.3576, 0.1805], [0.2126, 0.7152, 0.0722], [0.0193, 0.1192, 0.9505]])
    xyz = a @ M.T / np.array([0.95047, 1.0, 1.08883])
    f = np.where(xyz > 0.008856, np.cbrt(xyz), 7.787 * xyz + 16 / 116)
    return np.stack([116 * f[..., 1] - 16, 500 * (f[..., 0] - f[..., 1]), 200 * (f[..., 1] - f[..., 2])], -1)

def lab2srgb(l):
    fy = (l[..., 0] + 16) / 116; fx = fy + l[..., 1] / 500; fz = fy - l[..., 2] / 200
    f = np.stack([fx, fy, fz], -1)
    xyz = np.where(f ** 3 > 0.008856, f ** 3, (f - 16 / 116) / 7.787) * np.array([0.95047, 1.0, 1.08883])
    M = np.array([[3.2406, -1.5372, -0.4986], [-0.9689, 1.8758, 0.0415], [0.0557, -0.2040, 1.0570]])
    a = xyz @ M.T
    a = np.where(a > 0.0031308, 1.055 * np.clip(a, 0, None) ** (1 / 2.4) - 0.055, 12.92 * a)
    return np.clip(a * 255, 0, 255)

def match(img, ref, strength=0.7):
    s = srgb2lab(np.asarray(img.convert('RGB')).astype(float))
    r = srgb2lab(np.asarray(ref.convert('RGB')).astype(float))
    sm, ss = s.reshape(-1, 3).mean(0), s.reshape(-1, 3).std(0) + 1e-6
    rm, rs = r.reshape(-1, 3).mean(0), r.reshape(-1, 3).std(0)
    t = (s - sm) / ss * rs + rm
    out = s + (t - s) * strength
    return Image.fromarray(lab2srgb(out).astype(np.uint8))

if __name__ == '__main__':
    src, ref, out = sys.argv[1], sys.argv[2], sys.argv[3]
    k = float(sys.argv[4]) if len(sys.argv) > 4 else 0.7
    im = Image.open(src).convert('RGB').resize((290, 199), Image.LANCZOS)
    match(im, Image.open(ref), k).save(out)
