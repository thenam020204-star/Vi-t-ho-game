import sys, os; sys.path.insert(0, os.path.dirname(os.path.abspath(__file__)))
import sys; from t14lib import *
W,Hh,mode,fr=int(sys.argv[1]),int(sys.argv[2]),sys.argv[3],int(sys.argv[4])
r=mk(W,Hh,mode); to_play(r,fr); shot(r,sys.argv[5]); print(hex(r.cls()))
