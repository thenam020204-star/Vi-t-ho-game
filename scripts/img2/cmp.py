import sys,os,glob
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from eng import *
S=int(sys.argv[1]) if len(sys.argv)>1 else 4
for f in sorted(glob.glob(W+'/img2/png_viet/*.png')):
    g=glob.glob(W+'/img2/png_goc/'+os.path.basename(f))[0]
    a=Image.open(g).convert('RGBA'); b=Image.open(f).convert('RGBA')
    def prep(im,f=f):
        bg=Image.new('RGBA',im.size,(150,150,150,255) if 'animation' in f else (255,0,255,255)); bg.alpha_composite(im); return bg.resize((im.width*S,im.height*S),Image.NEAREST)
    pa,pb=prep(a),prep(b); gap=8
    horiz=a.width*S*2>1700
    c=Image.new('RGBA',(pa.width if horiz else pa.width*2+gap, pa.height*2+gap if horiz else pa.height),(30,30,30,255))
    c.paste(pa,(0,0)); c.paste(pb,(0,pa.height+gap) if horiz else (pa.width+gap,0))
    c.convert('RGB').save(W+'/img2/compare/'+os.path.basename(f))
