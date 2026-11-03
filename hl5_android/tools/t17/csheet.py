import sys,numpy as np
from PIL import Image,ImageDraw
# csheet.py out.png cols label=path ...
out=sys.argv[1]; cols=int(sys.argv[2]); items=[a.split('=',1) for a in sys.argv[3:]]
ims=[Image.open(p).convert('RGB') for _,p in items]
w,h=ims[0].size; rows=(len(ims)+cols-1)//cols
S=Image.new('RGB',(cols*w,rows*h),(40,40,40))
d=ImageDraw.Draw(S)
for i,((lab,_),im) in enumerate(zip(items,ims)):
    x,y=(i%cols)*w,(i//cols)*h; S.paste(im,(x,y)); d.rectangle([x,y,x+len(lab)*7+8,y+13],fill=(0,0,0)); d.text((x+3,y+1),lab,fill=(255,255,0))
S.save(out); print(out,S.size)
