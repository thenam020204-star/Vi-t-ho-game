import os,sys,zipfile
src,out=sys.argv[1],sys.argv[2]
with zipfile.ZipFile(out,'w',zipfile.ZIP_DEFLATED) as z:
    z.write(os.path.join(src,'META-INF/MANIFEST.MF'),'META-INF/MANIFEST.MF')
    for r,ds,fs in os.walk(src):
        ds.sort()
        for f in sorted(fs):
            p=os.path.relpath(os.path.join(r,f),src).replace(os.sep,'/')
            if p!='META-INF/MANIFEST.MF': z.write(os.path.join(r,f),p)
