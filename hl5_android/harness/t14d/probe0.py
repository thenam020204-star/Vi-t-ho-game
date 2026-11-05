import sys; sys.path.insert(0, '.'); from d8lib import *
so = sys.argv[1]; vfs = sys.argv[2]; W, Hh = int(sys.argv[3]), int(sys.argv[4])
r = boot_play(so, vfs, W, Hh)
print('hero', hex(hero(r)), pos(r), hstate(r))
for name, ev in [('UP', [(0,'D','UP'),(30,'U','UP')]), ('RIGHT', [(0,'D','RIGHT'),(30,'U','RIGHT')]),
                 ('UP+RIGHT', [(0,'D','UP'),(1,'D','RIGHT'),(30,'U','RIGHT'),(31,'U','UP')]),
                 ('RIGHT+UP', [(0,'D','RIGHT'),(1,'D','UP'),(30,'U','UP'),(31,'U','RIGHT')])]:
    t = scen(r, ev, 34)
    print(name, [tuple(a[:3]) + (a[3],a[4],a[6]) for a in t[::6]])
