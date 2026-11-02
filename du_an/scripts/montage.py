import sys
from PIL import Image, ImageDraw
out=sys.argv[1]; fs=sys.argv[2:]
ims=[Image.open(f).convert('RGB') for f in fs]
w=sum(i.width for i in ims)+6*(len(ims)-1); h=max(i.height for i in ims)+14
m=Image.new('RGB',(w,h),(60,60,60)); d=ImageDraw.Draw(m); x=0
for f,i in zip(fs,ims):
    m.paste(i,(x,14)); d.text((x,0),f.split('/')[-1],fill=(255,255,0)); x+=i.width+6
m.save(out)
