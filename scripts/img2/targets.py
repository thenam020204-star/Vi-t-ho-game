import sys,os
sys.path.insert(0,os.path.dirname(os.path.abspath(__file__)))
from cont import *
from PIL import Image
T=[('uires',o) for o in (60706,76573,84345,95350,96468,97757,98480,99549,100298,104649,105157)]+[('animation',o) for o in (301320,351240,358141,374879,404761,414838,446653)]
import glob
def path(n,o): return glob.glob(W+'/img2/png_goc/%s_*_off%d.png'%(n,o))[0]
if __name__=='__main__':
    S=4
    for k,(n,o) in enumerate(T):
        im=Image.open(path(n,o)).convert('RGBA'); bg=Image.new('RGBA',im.size,(255,0,255,255)); bg.alpha_composite(im)
        bg.resize((im.width*S,im.height*S),Image.NEAREST).save('/tmp/t_%02d.png'%k)
