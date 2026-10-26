import sys,os,glob,re
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from lib import W
from PIL import Image, ImageDraw
files=sorted(glob.glob(W+'/img/embedded/*.png'), key=lambda f:(os.path.basename(f).split('_')[0], int(re.search(r'_(\d+)_off',f).group(1))))
CW,CH=150,120
def sheet(fs,out,cols=6):
    rows=(len(fs)+cols-1)//cols
    S=Image.new('RGB',(cols*CW,rows*CH),(90,90,90)); d=ImageDraw.Draw(S)
    for i,f in enumerate(fs):
        im=Image.open(f).convert('RGBA'); 
        k=min((CW-6)/im.width,(CH-16)/im.height); k=max(1,int(k)) if k>=1 else k
        sz=(max(1,int(im.width*k)),max(1,int(im.height*k)))
        im=im.resize(sz,Image.NEAREST if k>=1 else Image.LANCZOS)
        x,y=(i%cols)*CW,(i//cols)*CH
        bg=Image.new('RGBA',sz,(255,0,255,255)); bg.alpha_composite(im)
        S.paste(bg.convert('RGB'),(x+3,y+14))
        d.text((x+3,y+1),os.path.basename(f).replace('.png','')[:22],fill=(255,255,0))
    S.save(out)
an=[f for f in files if 'animation' in f]; ui=[f for f in files if 'uires' in f]
sheet(ui,W+'/img/prev/sh_ui.png')
for i in range(0,len(an),30): sheet(an[i:i+30],W+'/img/prev/sh_an%d.png'%(i//30))
print(len(an),len(ui))
